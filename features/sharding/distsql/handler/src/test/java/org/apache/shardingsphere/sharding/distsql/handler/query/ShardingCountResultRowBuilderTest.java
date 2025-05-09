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

package org.apache.shardingsphere.sharding.distsql.handler.query;

import org.apache.shardingsphere.distsql.handler.executor.rql.rule.CountResultRowBuilder;
import org.apache.shardingsphere.infra.merge.result.impl.local.LocalDataQueryResultRow;
import org.apache.shardingsphere.infra.spi.type.typed.TypedSPILoader;
import org.apache.shardingsphere.sharding.rule.ShardingRule;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShardingCountResultRowBuilderTest {
    
    @SuppressWarnings("unchecked")
    private final CountResultRowBuilder<ShardingRule> builder = TypedSPILoader.getService(CountResultRowBuilder.class, "SHARDING");
    
    @Test
    void assertGenerateRows() {
        List<LocalDataQueryResultRow> actual = new ArrayList<>(builder.generateRows(mockRule(), "foo_db"));
        assertThat(actual.size(), is(2));
        assertThat(actual.get(0).getCell(1), is("sharding_table"));
        assertThat(actual.get(0).getCell(2), is("foo_db"));
        assertThat(actual.get(0).getCell(3), is("1"));
        assertThat(actual.get(1).getCell(1), is("sharding_table_reference"));
        assertThat(actual.get(1).getCell(2), is("foo_db"));
        assertThat(actual.get(1).getCell(3), is("1"));
    }
    
    private ShardingRule mockRule() {
        ShardingRule result = mock(ShardingRule.class, RETURNS_DEEP_STUBS);
        when(result.getShardingTables().size()).thenReturn(1);
        when(result.getConfiguration().getBindingTableGroups().size()).thenReturn(1);
        return result;
    }
    
    @Test
    void assertGetRuleClass() {
        assertThat(builder.getRuleClass(), is(ShardingRule.class));
    }
}
