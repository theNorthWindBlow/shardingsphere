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

package org.apache.shardingsphere.sql.parser.statement.core.statement.type.ddl.view;

import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.infra.database.core.type.DatabaseType;
import org.apache.shardingsphere.sql.parser.statement.core.segment.ddl.constraint.ConstraintDefinitionSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.generic.table.SimpleTableSegment;
import org.apache.shardingsphere.sql.parser.statement.core.statement.type.ddl.DDLStatement;
import org.apache.shardingsphere.sql.parser.statement.core.statement.type.dml.SelectStatement;

import java.util.Optional;

/**
 * Alter view statement.
 */
@Getter
@Setter
public final class AlterViewStatement extends DDLStatement {
    
    private SimpleTableSegment view;
    
    private SimpleTableSegment renameView;
    
    private ConstraintDefinitionSegment constraintDefinition;
    
    private SelectStatement select;
    
    private String viewDefinition;
    
    public AlterViewStatement(final DatabaseType databaseType) {
        super(databaseType);
    }
    
    /**
     * Get rename view.
     *
     * @return rename view
     */
    public Optional<SimpleTableSegment> getRenameView() {
        return Optional.ofNullable(renameView);
    }
    
    /**
     * Get constraint definition.
     *
     * @return constraint definition
     */
    public Optional<ConstraintDefinitionSegment> getConstraintDefinition() {
        return Optional.ofNullable(constraintDefinition);
    }
    
    /**
     * Get select statement.
     *
     * @return select statement
     */
    public Optional<SelectStatement> getSelect() {
        return Optional.ofNullable(select);
    }
    
    /**
     * Get view definition.
     *
     * @return view definition
     */
    public Optional<String> getViewDefinition() {
        return Optional.ofNullable(viewDefinition);
    }
}
