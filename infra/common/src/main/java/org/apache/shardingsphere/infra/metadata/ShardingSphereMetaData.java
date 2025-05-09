/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.infra.metadata;

import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.shardingsphere.infra.config.props.ConfigurationProperties;
import org.apache.shardingsphere.infra.config.props.temporary.TemporaryConfigurationProperties;
import org.apache.shardingsphere.infra.database.core.type.DatabaseType;
import org.apache.shardingsphere.infra.database.core.type.DatabaseTypeRegistry;
import org.apache.shardingsphere.infra.datasource.pool.destroyer.DataSourcePoolDestroyer;
import org.apache.shardingsphere.infra.metadata.database.ShardingSphereDatabase;
import org.apache.shardingsphere.infra.metadata.database.ShardingSphereDatabaseFactory;
import org.apache.shardingsphere.infra.metadata.database.resource.ResourceMetaData;
import org.apache.shardingsphere.infra.metadata.database.rule.RuleMetaData;
import org.apache.shardingsphere.infra.metadata.database.schema.builder.GenericSchemaBuilder;
import org.apache.shardingsphere.infra.metadata.database.schema.builder.GenericSchemaBuilderMaterial;
import org.apache.shardingsphere.infra.metadata.database.schema.model.ShardingSphereSchema;
import org.apache.shardingsphere.infra.metadata.identifier.ShardingSphereIdentifier;
import org.apache.shardingsphere.infra.rule.ShardingSphereRule;
import org.apache.shardingsphere.infra.rule.attribute.datasource.StaticDataSourceRuleAttribute;
import org.apache.shardingsphere.infra.rule.scope.GlobalRule;
import org.apache.shardingsphere.infra.rule.scope.GlobalRule.GlobalRuleChangedType;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * ShardingSphere meta data.
 */
@Getter
public final class ShardingSphereMetaData implements AutoCloseable {
    
    @Getter(AccessLevel.NONE)
    private final Map<ShardingSphereIdentifier, ShardingSphereDatabase> databases;
    
    private final ResourceMetaData globalResourceMetaData;
    
    private final RuleMetaData globalRuleMetaData;
    
    private final ConfigurationProperties props;
    
    private final TemporaryConfigurationProperties temporaryProps;
    
    public ShardingSphereMetaData() {
        this(Collections.emptyList(), new ResourceMetaData(Collections.emptyMap()), new RuleMetaData(Collections.emptyList()), new ConfigurationProperties(new Properties()));
    }
    
    public ShardingSphereMetaData(final Collection<ShardingSphereDatabase> databases, final ResourceMetaData globalResourceMetaData,
                                  final RuleMetaData globalRuleMetaData, final ConfigurationProperties props) {
        this.databases = new ConcurrentHashMap<>(databases.stream().collect(Collectors.toMap(each -> new ShardingSphereIdentifier(each.getName()), each -> each)));
        this.globalResourceMetaData = globalResourceMetaData;
        this.globalRuleMetaData = globalRuleMetaData;
        this.props = props;
        temporaryProps = new TemporaryConfigurationProperties(props.getProps());
    }
    
    /**
     * Get all databases.
     *
     * @return all databases
     */
    public Collection<ShardingSphereDatabase> getAllDatabases() {
        return databases.values();
    }
    
    /**
     * Judge contains database from meta data or not.
     *
     * @param databaseName database name
     * @return contains database from meta data or not
     */
    public boolean containsDatabase(final String databaseName) {
        return databases.containsKey(new ShardingSphereIdentifier(databaseName));
    }
    
    /**
     * Get database.
     *
     * @param databaseName database name
     * @return meta data database
     */
    public ShardingSphereDatabase getDatabase(final String databaseName) {
        return databases.get(new ShardingSphereIdentifier(databaseName));
    }
    
    /**
     * Add database.
     *
     * @param databaseName database name
     * @param protocolType protocol database type
     * @param props configuration properties
     */
    public void addDatabase(final String databaseName, final DatabaseType protocolType, final ConfigurationProperties props) {
        ShardingSphereDatabase database = ShardingSphereDatabaseFactory.create(databaseName, protocolType, props);
        Map<String, ShardingSphereSchema> schemas = buildDefaultSchema(databaseName, protocolType, props);
        schemas.entrySet().stream().filter(entry -> !database.containsSchema(entry.getKey())).forEach(entry -> database.addSchema(entry.getValue()));
        databases.put(new ShardingSphereIdentifier(database.getName()), database);
        globalRuleMetaData.getRules().forEach(each -> ((GlobalRule) each).refresh(databases.values(), GlobalRuleChangedType.DATABASE_CHANGED));
    }
    
    private Map<String, ShardingSphereSchema> buildDefaultSchema(final String databaseName, final DatabaseType protocolType, final ConfigurationProperties props) {
        try {
            return new ConcurrentHashMap<>(GenericSchemaBuilder.build(protocolType,
                    new GenericSchemaBuilderMaterial(Maps.newHashMap(), Collections.emptyList(), props, new DatabaseTypeRegistry(protocolType).getDefaultSchemaName(databaseName))));
        } catch (final SQLException ignored) {
        }
        return Maps.newHashMap();
    }
    
    /**
     * Put database.
     *
     * @param database database
     */
    public void putDatabase(final ShardingSphereDatabase database) {
        databases.put(new ShardingSphereIdentifier(database.getName()), database);
    }
    
    /**
     * Drop database.
     *
     * @param databaseName database name
     */
    public void dropDatabase(final String databaseName) {
        cleanResources(databases.remove(new ShardingSphereIdentifier(databaseName)));
    }
    
    @SneakyThrows(Exception.class)
    private void cleanResources(final ShardingSphereDatabase database) {
        globalRuleMetaData.getRules().forEach(each -> ((GlobalRule) each).refresh(databases.values(), GlobalRuleChangedType.DATABASE_CHANGED));
        for (ShardingSphereRule each : database.getRuleMetaData().getRules()) {
            if (each instanceof AutoCloseable) {
                ((AutoCloseable) each).close();
            }
        }
        database.getRuleMetaData().getAttributes(StaticDataSourceRuleAttribute.class).forEach(StaticDataSourceRuleAttribute::cleanStorageNodeDataSources);
        Optional.ofNullable(database.getResourceMetaData())
                .ifPresent(optional -> optional.getStorageUnits().values().forEach(each -> new DataSourcePoolDestroyer(each.getDataSource()).asyncDestroy()));
    }
    
    @SneakyThrows(Exception.class)
    @Override
    public void close() {
        for (ShardingSphereRule each : getAllRules()) {
            if (each instanceof AutoCloseable) {
                ((AutoCloseable) each).close();
            }
        }
    }
    
    private Collection<ShardingSphereRule> getAllRules() {
        Collection<ShardingSphereRule> result = new LinkedList<>(globalRuleMetaData.getRules());
        getAllDatabases().stream().map(each -> each.getRuleMetaData().getRules()).forEach(result::addAll);
        return result;
    }
}
