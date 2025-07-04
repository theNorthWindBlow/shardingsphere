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

package org.apache.shardingsphere.sql.parser.statement.oracle.ddl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.shardingsphere.sql.parser.statement.core.segment.ddl.type.TypeSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.generic.DataTypeSegment;
import org.apache.shardingsphere.sql.parser.statement.core.statement.type.ddl.type.CreateTypeStatement;

/**
 * Create varray type statement for Oracle.
 */
@RequiredArgsConstructor
@Getter
@Setter
public final class OracleCreateVarrayTypeStatement extends CreateTypeStatement {
    
    private final boolean isReplace;
    
    private final boolean editionable;
    
    /** default -1 means that the size is not specified. */
    private final int size;
    
    private final boolean notNull;
    
    private final boolean isPersistable;
    
    private final TypeSegment typeSegment;
    
    private final DataTypeSegment dataType;
    
}
