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

package org.apache.shardingsphere.proxy.backend.handler.data;

import org.apache.shardingsphere.infra.binder.context.statement.SQLStatementContext;
import org.apache.shardingsphere.infra.binder.context.statement.type.CommonSQLStatementContext;
import org.apache.shardingsphere.infra.database.core.type.DatabaseType;
import org.apache.shardingsphere.infra.hint.HintValueContext;
import org.apache.shardingsphere.infra.metadata.ShardingSphereMetaData;
import org.apache.shardingsphere.infra.metadata.database.ShardingSphereDatabase;
import org.apache.shardingsphere.infra.session.connection.ConnectionContext;
import org.apache.shardingsphere.infra.session.query.QueryContext;
import org.apache.shardingsphere.infra.spi.type.typed.TypedSPILoader;
import org.apache.shardingsphere.mode.manager.ContextManager;
import org.apache.shardingsphere.proxy.backend.connector.ProxyDatabaseConnectionManager;
import org.apache.shardingsphere.proxy.backend.connector.StandardDatabaseConnector;
import org.apache.shardingsphere.proxy.backend.context.ProxyContext;
import org.apache.shardingsphere.proxy.backend.handler.data.impl.UnicastDatabaseBackendHandler;
import org.apache.shardingsphere.proxy.backend.session.ConnectionSession;
import org.apache.shardingsphere.sql.parser.statement.core.statement.SQLStatement;
import org.apache.shardingsphere.sql.parser.statement.core.statement.type.dal.DALStatement;
import org.apache.shardingsphere.sql.parser.statement.core.statement.type.dml.SelectStatement;
import org.apache.shardingsphere.test.mock.AutoMockExtension;
import org.apache.shardingsphere.test.mock.ConstructionMockSettings;
import org.apache.shardingsphere.test.mock.StaticMockSettings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(AutoMockExtension.class)
@StaticMockSettings(ProxyContext.class)
@ConstructionMockSettings(StandardDatabaseConnector.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DatabaseBackendHandlerFactoryTest {
    
    private final DatabaseType databaseType = TypedSPILoader.getService(DatabaseType.class, "FIXTURE");
    
    @Test
    void assertNewInstanceReturnedUnicastDatabaseBackendHandlerWithDAL() {
        String sql = "DESC tbl";
        SQLStatementContext sqlStatementContext = mock(SQLStatementContext.class, RETURNS_DEEP_STUBS);
        when(sqlStatementContext.getSqlStatement()).thenReturn(mock(DALStatement.class, RETURNS_DEEP_STUBS));
        when(sqlStatementContext.getTablesContext().getDatabaseNames()).thenReturn(Collections.emptyList());
        DatabaseBackendHandler actual = DatabaseBackendHandlerFactory.newInstance(
                new QueryContext(sqlStatementContext, sql, Collections.emptyList(), new HintValueContext(), mockConnectionContext(), mock()), mock(), false);
        assertThat(actual, instanceOf(UnicastDatabaseBackendHandler.class));
    }
    
    private ConnectionContext mockConnectionContext() {
        ConnectionContext result = mock(ConnectionContext.class);
        when(result.getCurrentDatabaseName()).thenReturn(Optional.of("foo_db"));
        return result;
    }
    
    @Test
    void assertNewInstanceReturnedUnicastDatabaseBackendHandlerWithQueryWithoutFrom() {
        String sql = "SELECT 1";
        SQLStatementContext sqlStatementContext = new CommonSQLStatementContext(new SelectStatement(databaseType));
        DatabaseBackendHandler actual = DatabaseBackendHandlerFactory.newInstance(
                new QueryContext(sqlStatementContext, sql, Collections.emptyList(), new HintValueContext(), mockConnectionContext(), mock()), mock(), false);
        assertThat(actual, instanceOf(UnicastDatabaseBackendHandler.class));
    }
    
    @Test
    void assertNewInstanceReturnedSchemaAssignedDatabaseBackendHandler() {
        String sql = "SELECT 1 FROM user WHERE id = 1";
        SQLStatementContext sqlStatementContext = mockSQLStatementContext();
        ConnectionSession connectionSession = mockConnectionSession();
        ContextManager contextManager = mockContextManager();
        when(ProxyContext.getInstance().getContextManager()).thenReturn(contextManager);
        DatabaseBackendHandler actual =
                DatabaseBackendHandlerFactory.newInstance(
                        new QueryContext(sqlStatementContext, sql, Collections.emptyList(), new HintValueContext(), mockConnectionContext(), mock(ShardingSphereMetaData.class)),
                        connectionSession, false);
        assertThat(actual, instanceOf(StandardDatabaseConnector.class));
    }
    
    private SQLStatementContext mockSQLStatementContext() {
        SQLStatementContext result = mock(SQLStatementContext.class, RETURNS_DEEP_STUBS);
        when(result.getSqlStatement()).thenReturn(mock(SQLStatement.class));
        when(result.getTablesContext().getSchemaNames()).thenReturn(Collections.emptyList());
        when(result.getTablesContext().getDatabaseName()).thenReturn(Optional.empty());
        return result;
    }
    
    private ContextManager mockContextManager() {
        ContextManager result = mock(ContextManager.class, RETURNS_DEEP_STUBS);
        ShardingSphereDatabase database = mock(ShardingSphereDatabase.class);
        when(database.isComplete()).thenReturn(true);
        when(database.containsDataSource()).thenReturn(true);
        when(result.getMetaDataContexts().getMetaData().getDatabase("foo_db")).thenReturn(database);
        when(result.getMetaDataContexts().getMetaData().containsDatabase("foo_db")).thenReturn(true);
        return result;
    }
    
    private ConnectionSession mockConnectionSession() {
        ConnectionSession result = mock(ConnectionSession.class);
        when(result.getUsedDatabaseName()).thenReturn("foo_db");
        when(result.getDatabaseConnectionManager()).thenReturn(mock(ProxyDatabaseConnectionManager.class));
        when(result.getDatabaseConnectionManager().getConnectionSession()).thenReturn(result);
        return result;
    }
}
