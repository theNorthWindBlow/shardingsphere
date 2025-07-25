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

package org.apache.shardingsphere.sql.parser.mysql.visitor.statement.type;

import org.apache.shardingsphere.infra.database.core.type.DatabaseType;
import org.apache.shardingsphere.sql.parser.api.ASTNode;
import org.apache.shardingsphere.sql.parser.api.visitor.statement.type.DALStatementVisitor;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.AlterResourceGroupContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.AnalyzeTableContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.BinaryLogFileIndexNumberContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.BinlogContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.CacheIndexContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.CacheTableIndexListContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ChangeMasterToContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ChangeReplicationSourceToContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ChannelOptionContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.CheckTableContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ChecksumTableContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.CloneActionContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.CloneContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.CloneInstanceContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.CreateLoadableFunctionContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.CreateResourceGroupContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.DelimiterContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.DropResourceGroupContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ExplainContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ExplainableStatementContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.FlushContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.FromDatabaseContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.FromTableContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.HelpContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.IndexNameContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.InstallComponentContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.InstallPluginContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.KillContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.LoadIndexInfoContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.LoadTableIndexListContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.OptimizeTableContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.OptionTypeContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.OptionValueContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.OptionValueListContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.OptionValueNoOptionTypeContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.PartitionListContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.PartitionNameContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.RepairTableContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ResetOptionContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ResetPersistContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ResetStatementContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.RestartContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.SetCharacterContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.SetResourceGroupContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.SetVariableContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowBinaryLogsContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowBinlogEventsContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowCharacterSetContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowCharsetContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowCollationContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowColumnsContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowCreateDatabaseContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowCreateEventContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowCreateFunctionContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowCreateProcedureContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowCreateTableContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowCreateTriggerContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowCreateUserContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowCreateViewContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowDatabasesContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowEngineContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowEnginesContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowErrorsContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowEventsContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowFilterContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowFunctionCodeContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowFunctionStatusContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowGrantsContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowIndexContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowLikeContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowMasterStatusContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowOpenTablesContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowPluginsContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowPrivilegesContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowProcedureCodeContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowProcedureStatusContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowProcesslistContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowProfileContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowProfilesContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowRelaylogEventContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowReplicaStatusContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowReplicasContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowSlaveHostsContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowSlaveStatusContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowStatusContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowTableStatusContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowTablesContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowTriggersContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowVariablesContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowWarningsContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShowWhereClauseContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.ShutdownContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.StartSlaveContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.StopSlaveContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.TablesOptionContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.UninstallComponentContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.UninstallPluginContext;
import org.apache.shardingsphere.sql.parser.autogen.MySQLStatementParser.UseContext;
import org.apache.shardingsphere.sql.parser.mysql.visitor.statement.MySQLStatementVisitor;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dal.CacheTableIndexSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dal.CloneActionSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dal.CloneInstanceSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dal.FromDatabaseSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dal.FromTableSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dal.LoadTableIndexSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dal.PartitionDefinitionSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dal.PartitionSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dal.ResetMasterOptionSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dal.ResetOptionSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dal.ResetSlaveOptionSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dal.ShowFilterSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dal.ShowLikeSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dal.VariableAssignSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dal.VariableSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.ddl.index.IndexSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dml.column.ColumnSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dml.expr.ExpressionSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dml.expr.FunctionSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dml.pagination.limit.LimitSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.dml.predicate.WhereSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.generic.DatabaseSegment;
import org.apache.shardingsphere.sql.parser.statement.core.segment.generic.table.SimpleTableSegment;
import org.apache.shardingsphere.sql.parser.statement.core.statement.SQLStatement;
import org.apache.shardingsphere.sql.parser.statement.core.statement.type.dal.AnalyzeTableStatement;
import org.apache.shardingsphere.sql.parser.statement.core.statement.type.dal.ExplainStatement;
import org.apache.shardingsphere.sql.parser.statement.core.statement.type.dal.SetStatement;
import org.apache.shardingsphere.sql.parser.statement.core.value.collection.CollectionValue;
import org.apache.shardingsphere.sql.parser.statement.core.value.identifier.IdentifierValue;
import org.apache.shardingsphere.sql.parser.statement.core.value.literal.impl.NumberLiteralValue;
import org.apache.shardingsphere.sql.parser.statement.core.value.literal.impl.StringLiteralValue;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLCloneStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLCreateLoadableFunctionStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLDelimiterStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLFlushStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLHelpStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLKillStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLResetPersistStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLResetStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLRestartStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLShutdownStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLUseStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.component.MySQLInstallComponentStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.component.MySQLUninstallComponentStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.index.MySQLCacheIndexStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.index.MySQLLoadIndexInfoStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.plugin.MySQLInstallPluginStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.plugin.MySQLShowPluginsStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.plugin.MySQLUninstallPluginStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.replication.binlog.MySQLBinlogStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.replication.binlog.MySQLShowBinaryLogsStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.replication.binlog.MySQLShowBinlogEventsStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.replication.opertation.MySQLChangeMasterStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.replication.opertation.MySQLChangeReplicationSourceToStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.replication.opertation.MySQLStartReplicaStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.replication.opertation.MySQLStartSlaveStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.replication.opertation.MySQLStopSlaveStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.replication.show.MySQLShowMasterStatusStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.replication.show.MySQLShowRelayLogEventsStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.replication.show.MySQLShowReplicaStatusStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.replication.show.MySQLShowReplicasStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.replication.show.MySQLShowSlaveHostsStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.replication.show.MySQLShowSlaveStatusStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.replication.show.MySQLShowStatusStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.resource.MySQLAlterResourceGroupStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.resource.MySQLCreateResourceGroupStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.resource.MySQLDropResourceGroupStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.resource.MySQLSetResourceGroupStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.MySQLShowOtherStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.character.MySQLShowCharacterSetStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.character.MySQLShowCollationStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.column.MySQLDescribeStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.column.MySQLShowColumnsStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.database.MySQLShowCreateDatabaseStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.database.MySQLShowDatabasesStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.engine.MySQLShowEngineStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.error.MySQLShowErrorsStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.error.MySQLShowWarningsStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.event.MySQLShowCreateEventStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.event.MySQLShowEventsStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.function.MySQLShowCreateFunctionStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.function.MySQLShowFunctionCodeStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.function.MySQLShowFunctionStatusStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.index.MySQLShowIndexStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.privilege.MySQLShowCreateUserStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.privilege.MySQLShowGrantsStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.privilege.MySQLShowPrivilegesStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.procedure.MySQLShowCreateProcedureStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.procedure.MySQLShowProcedureCodeStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.procedure.MySQLShowProcedureStatusStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.process.MySQLShowProcessListStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.profile.MySQLShowProfileStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.profile.MySQLShowProfilesStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.table.MySQLShowCreateTableStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.table.MySQLShowOpenTablesStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.table.MySQLShowTableStatusStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.table.MySQLShowTablesStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.trigger.MySQLShowCreateTriggerStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.trigger.MySQLShowTriggersStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.variable.MySQLShowVariablesStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.show.view.MySQLShowCreateViewStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.table.MySQLCheckTableStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.table.MySQLChecksumTableStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.table.MySQLOptimizeTableStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.table.MySQLRepairTableStatement;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * DAL statement visitor for MySQL.
 */
public final class MySQLDALStatementVisitor extends MySQLStatementVisitor implements DALStatementVisitor {
    
    public MySQLDALStatementVisitor(final DatabaseType databaseType) {
        super(databaseType);
    }
    
    @Override
    public ASTNode visitUninstallPlugin(final UninstallPluginContext ctx) {
        return new MySQLUninstallPluginStatement(getDatabaseType(), ((IdentifierValue) visit(ctx.pluginName())).getValue());
    }
    
    @Override
    public ASTNode visitShowCreateDatabase(final ShowCreateDatabaseContext ctx) {
        return new MySQLShowCreateDatabaseStatement(getDatabaseType(), ((DatabaseSegment) visit(ctx.databaseName())).getIdentifier().getValue());
    }
    
    @Override
    public ASTNode visitShowBinaryLogs(final ShowBinaryLogsContext ctx) {
        return new MySQLShowBinaryLogsStatement(getDatabaseType());
    }
    
    @Override
    public ASTNode visitShowStatus(final ShowStatusContext ctx) {
        MySQLShowStatusStatement result = new MySQLShowStatusStatement(getDatabaseType(), null == ctx.showFilter() ? null : (ShowFilterSegment) visit(ctx.showFilter()));
        result.addParameterMarkers(getParameterMarkerSegments());
        return result;
    }
    
    @Override
    public ASTNode visitShowCreateView(final ShowCreateViewContext ctx) {
        return new MySQLShowCreateViewStatement(getDatabaseType(), null);
    }
    
    @Override
    public ASTNode visitShowEngines(final ShowEnginesContext ctx) {
        return new MySQLShowOtherStatement(getDatabaseType());
    }
    
    @Override
    public ASTNode visitShowEngine(final ShowEngineContext ctx) {
        return new MySQLShowEngineStatement(getDatabaseType(), ctx.engineRef().getText());
    }
    
    @Override
    public ASTNode visitShowCharset(final ShowCharsetContext ctx) {
        return new MySQLShowOtherStatement(getDatabaseType());
    }
    
    @Override
    public ASTNode visitShowCreateEvent(final ShowCreateEventContext ctx) {
        return new MySQLShowCreateEventStatement(getDatabaseType(), ((IdentifierValue) visit(ctx.eventName())).getValue());
    }
    
    @Override
    public ASTNode visitShowCreateFunction(final ShowCreateFunctionContext ctx) {
        return new MySQLShowCreateFunctionStatement(getDatabaseType(), ((FunctionSegment) visit(ctx.functionName())).getFunctionName());
    }
    
    @Override
    public ASTNode visitShowCreateProcedure(final ShowCreateProcedureContext ctx) {
        return new MySQLShowCreateProcedureStatement(getDatabaseType(), ((IdentifierValue) visit(ctx.procedureName())).getValue());
    }
    
    @Override
    public ASTNode visitShowBinlogEvents(final ShowBinlogEventsContext ctx) {
        return new MySQLShowBinlogEventsStatement(getDatabaseType(), null == ctx.logName() ? null : ctx.logName().getText(),
                null == ctx.limitClause() ? null : (LimitSegment) visit(ctx.limitClause()));
    }
    
    @Override
    public ASTNode visitShowErrors(final ShowErrorsContext ctx) {
        return new MySQLShowErrorsStatement(getDatabaseType(), null == ctx.limitClause() ? null : (LimitSegment) visit(ctx.limitClause()));
    }
    
    @Override
    public ASTNode visitShowWarnings(final ShowWarningsContext ctx) {
        return new MySQLShowWarningsStatement(getDatabaseType(), null == ctx.limitClause() ? null : (LimitSegment) visit(ctx.limitClause()));
    }
    
    @Override
    public ASTNode visitResetStatement(final ResetStatementContext ctx) {
        ResetPersistContext persistContext = ctx.resetPersist();
        return null == persistContext
                ? new MySQLResetStatement(getDatabaseType(),
                        ctx.resetOption().stream().filter(each -> null != each.MASTER() || null != each.SLAVE()).map(each -> (ResetOptionSegment) visit(each)).collect(Collectors.toList()))
                : visit(persistContext);
    }
    
    @Override
    public ASTNode visitResetPersist(final ResetPersistContext ctx) {
        return new MySQLResetPersistStatement(getDatabaseType(), null != ctx.ifExists(), null == ctx.identifier() ? null : new IdentifierValue(ctx.identifier().getText()));
    }
    
    @Override
    public ASTNode visitResetOption(final ResetOptionContext ctx) {
        if (null != ctx.MASTER()) {
            ResetMasterOptionSegment result = new ResetMasterOptionSegment();
            if (null != ctx.binaryLogFileIndexNumber()) {
                result.setBinaryLogFileIndexNumber(((NumberLiteralValue) visit(ctx.binaryLogFileIndexNumber())).getValue().longValue());
            }
            result.setStartIndex(ctx.start.getStartIndex());
            result.setStopIndex(ctx.stop.getStopIndex());
            return result;
        }
        ResetSlaveOptionSegment result = new ResetSlaveOptionSegment();
        if (null != ctx.ALL()) {
            result.setAll(true);
        }
        if (null != ctx.channelOption()) {
            result.setChannelOption(((StringLiteralValue) visit(ctx.channelOption())).getValue());
        }
        result.setStartIndex(ctx.start.getStartIndex());
        result.setStopIndex(ctx.stop.getStopIndex());
        return result;
    }
    
    @Override
    public ASTNode visitChannelOption(final ChannelOptionContext ctx) {
        return visit(ctx.string_());
    }
    
    @Override
    public ASTNode visitBinaryLogFileIndexNumber(final BinaryLogFileIndexNumberContext ctx) {
        return new NumberLiteralValue(ctx.getText());
    }
    
    @Override
    public ASTNode visitShowReplicas(final ShowReplicasContext ctx) {
        return new MySQLShowReplicasStatement(getDatabaseType());
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public ASTNode visitRepairTable(final RepairTableContext ctx) {
        return new MySQLRepairTableStatement(getDatabaseType(), ((CollectionValue<SimpleTableSegment>) visit(ctx.tableList())).getValue());
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public ASTNode visitAnalyzeTable(final AnalyzeTableContext ctx) {
        return new AnalyzeTableStatement(getDatabaseType(), ((CollectionValue<SimpleTableSegment>) visit(ctx.tableList())).getValue());
    }
    
    @Override
    public ASTNode visitCacheIndex(final CacheIndexContext ctx) {
        IdentifierValue name = null == ctx.DEFAULT() ? (IdentifierValue) visit(ctx.identifier()) : new IdentifierValue(ctx.DEFAULT().getText());
        Collection<CacheTableIndexSegment> tableIndexes = null == ctx.cacheTableIndexList()
                ? Collections.emptyList()
                : ctx.cacheTableIndexList().stream().map(each -> (CacheTableIndexSegment) visit(each)).collect(Collectors.toList());
        PartitionDefinitionSegment partitionDefinition = null == ctx.partitionList()
                ? null
                : new PartitionDefinitionSegment(ctx.tableName().getStart().getStartIndex(), ctx.partitionList().getStop().getStopIndex(),
                        (SimpleTableSegment) visit(ctx.tableName()), ((CollectionValue<PartitionSegment>) visit(ctx.partitionList())).getValue());
        return new MySQLCacheIndexStatement(getDatabaseType(), name, tableIndexes, partitionDefinition);
    }
    
    @Override
    public ASTNode visitCacheTableIndexList(final CacheTableIndexListContext ctx) {
        CacheTableIndexSegment result = new CacheTableIndexSegment(ctx.getStart().getStartIndex(), ctx.getStop().getStopIndex(), (SimpleTableSegment) visit(ctx.tableName()));
        for (IndexNameContext each : ctx.indexName()) {
            result.getIndexes().add((IndexSegment) visitIndexName(each));
        }
        return result;
    }
    
    @Override
    public ASTNode visitPartitionList(final PartitionListContext ctx) {
        CollectionValue<PartitionSegment> result = new CollectionValue<>();
        for (PartitionNameContext each : ctx.partitionName()) {
            result.getValue().add((PartitionSegment) visit(each));
        }
        return result;
    }
    
    @Override
    public ASTNode visitPartitionName(final PartitionNameContext ctx) {
        return new PartitionSegment(ctx.getStart().getStartIndex(), ctx.getStop().getStopIndex(), (IdentifierValue) visit(ctx.identifier()));
    }
    
    @Override
    public ASTNode visitChecksumTable(final ChecksumTableContext ctx) {
        return new MySQLChecksumTableStatement(getDatabaseType(), ((CollectionValue<SimpleTableSegment>) visit(ctx.tableList())).getValue());
    }
    
    @Override
    public ASTNode visitFlush(final FlushContext ctx) {
        if (null != ctx.tablesOption()) {
            return visit(ctx.tablesOption());
        }
        return new MySQLFlushStatement(getDatabaseType(), Collections.emptyList(), false);
    }
    
    @Override
    public ASTNode visitTablesOption(final TablesOptionContext ctx) {
        return new MySQLFlushStatement(getDatabaseType(), ctx.tableName().stream().map(each -> (SimpleTableSegment) visit(each)).collect(Collectors.toList()), true);
    }
    
    @Override
    public ASTNode visitKill(final KillContext ctx) {
        return new MySQLKillStatement(getDatabaseType(), null == ctx.AT_() ? ctx.IDENTIFIER_().getText() : ctx.AT_().getText() + ctx.IDENTIFIER_().getText(), getScope(ctx));
    }
    
    private String getScope(final KillContext ctx) {
        if (null != ctx.QUERY()) {
            return ctx.QUERY().getText();
        }
        if (null != ctx.CONNECTION()) {
            return ctx.CONNECTION().getText();
        }
        return null;
    }
    
    @Override
    public ASTNode visitLoadIndexInfo(final LoadIndexInfoContext ctx) {
        return new MySQLLoadIndexInfoStatement(getDatabaseType(), ctx.loadTableIndexList().stream().map(each -> (LoadTableIndexSegment) visit(each)).collect(Collectors.toList()));
    }
    
    @Override
    public ASTNode visitLoadTableIndexList(final LoadTableIndexListContext ctx) {
        LoadTableIndexSegment result = new LoadTableIndexSegment(ctx.getStart().getStartIndex(), ctx.getStop().getStopIndex(), (SimpleTableSegment) visit(ctx.tableName()));
        if (null != ctx.indexName()) {
            for (IndexNameContext each : ctx.indexName()) {
                result.getIndexes().add((IndexSegment) visitIndexName(each));
            }
        }
        if (null != ctx.partitionList()) {
            result.getPartitions().addAll(((CollectionValue<PartitionSegment>) visit(ctx.partitionList())).getValue());
        }
        return result;
    }
    
    @Override
    public ASTNode visitInstallPlugin(final InstallPluginContext ctx) {
        return new MySQLInstallPluginStatement(getDatabaseType(), ((IdentifierValue) visit(ctx.pluginName())).getValue());
    }
    
    @Override
    public ASTNode visitClone(final CloneContext ctx) {
        return new MySQLCloneStatement(getDatabaseType(), (CloneActionSegment) visit(ctx.cloneAction()));
    }
    
    @Override
    public ASTNode visitCloneAction(final CloneActionContext ctx) {
        CloneActionSegment result = new CloneActionSegment(ctx.start.getStartIndex(), ctx.stop.getStopIndex());
        if (null != ctx.cloneInstance()) {
            CloneInstanceContext cloneInstance = ctx.cloneInstance();
            CloneInstanceSegment cloneInstanceSegment = new CloneInstanceSegment(cloneInstance.start.getStartIndex(), cloneInstance.stop.getStopIndex());
            cloneInstanceSegment.setUsername(((StringLiteralValue) visitUsername(cloneInstance.username())).getValue());
            cloneInstanceSegment.setHostname(((StringLiteralValue) visit(cloneInstance.hostname())).getValue());
            cloneInstanceSegment.setPort(new NumberLiteralValue(cloneInstance.port().NUMBER_().getText()).getValue().intValue());
            cloneInstanceSegment.setPassword(((StringLiteralValue) visit(ctx.string_())).getValue());
            if (null != ctx.SSL() && null == ctx.NO()) {
                cloneInstanceSegment.setSslRequired(true);
            }
            result.setCloneInstance(cloneInstanceSegment);
        }
        if (null != ctx.cloneDir()) {
            result.setCloneDir(((StringLiteralValue) visit(ctx.cloneDir())).getValue());
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public ASTNode visitOptimizeTable(final OptimizeTableContext ctx) {
        return new MySQLOptimizeTableStatement(getDatabaseType(), ((CollectionValue<SimpleTableSegment>) visit(ctx.tableList())).getValue());
    }
    
    @Override
    public ASTNode visitUse(final UseContext ctx) {
        return new MySQLUseStatement(getDatabaseType(), ((DatabaseSegment) visit(ctx.databaseName())).getIdentifier().getValue());
    }
    
    @Override
    public ASTNode visitExplain(final ExplainContext ctx) {
        return null == ctx.tableName()
                ? new ExplainStatement(getDatabaseType(), getExplainableSQLStatement(ctx).orElse(null))
                : new MySQLDescribeStatement(getDatabaseType(), (SimpleTableSegment) visit(ctx.tableName()), getColumnWildcard(ctx));
    }
    
    private Optional<SQLStatement> getExplainableSQLStatement(final ExplainContext ctx) {
        if (null != ctx.explainableStatement()) {
            return Optional.of((SQLStatement) visit(ctx.explainableStatement()));
        }
        if (null != ctx.select()) {
            return Optional.of((SQLStatement) visit(ctx.select()));
        }
        if (null != ctx.delete()) {
            return Optional.of((SQLStatement) visit(ctx.delete()));
        }
        if (null != ctx.update()) {
            return Optional.of((SQLStatement) visit(ctx.update()));
        }
        if (null != ctx.insert()) {
            return Optional.of((SQLStatement) visit(ctx.insert()));
        }
        return Optional.empty();
    }
    
    private ColumnSegment getColumnWildcard(final ExplainContext ctx) {
        if (null != ctx.columnRef()) {
            return (ColumnSegment) visit(ctx.columnRef());
        }
        if (null != ctx.textString()) {
            return (ColumnSegment) visit(ctx.textString());
        }
        return null;
    }
    
    @Override
    public ASTNode visitExplainableStatement(final ExplainableStatementContext ctx) {
        if (null != ctx.select()) {
            return visit(ctx.select());
        }
        if (null != ctx.delete()) {
            return visit(ctx.delete());
        }
        if (null != ctx.insert()) {
            return visit(ctx.insert());
        }
        if (null != ctx.replace()) {
            return visit(ctx.replace());
        }
        return visit(ctx.update());
    }
    
    @Override
    public ASTNode visitShowProcedureCode(final ShowProcedureCodeContext ctx) {
        return new MySQLShowProcedureCodeStatement(getDatabaseType(), (FunctionSegment) visit(ctx.functionName()));
    }
    
    @Override
    public ASTNode visitShowProfile(final ShowProfileContext ctx) {
        return new MySQLShowProfileStatement(getDatabaseType(), null == ctx.limitClause() ? null : (LimitSegment) visit(ctx.limitClause()));
    }
    
    @Override
    public ASTNode visitShowProfiles(final ShowProfilesContext ctx) {
        return new MySQLShowProfilesStatement(getDatabaseType());
    }
    
    @Override
    public ASTNode visitShowDatabases(final ShowDatabasesContext ctx) {
        MySQLShowDatabasesStatement result = new MySQLShowDatabasesStatement(getDatabaseType(), null == ctx.showFilter() ? null : (ShowFilterSegment) visit(ctx.showFilter()));
        result.addParameterMarkers(getParameterMarkerSegments());
        return result;
    }
    
    @Override
    public ASTNode visitShowEvents(final ShowEventsContext ctx) {
        MySQLShowEventsStatement result = new MySQLShowEventsStatement(getDatabaseType(),
                null == ctx.fromDatabase() ? null : (FromDatabaseSegment) visit(ctx.fromDatabase()), null == ctx.showFilter() ? null : (ShowFilterSegment) visit(ctx.showFilter()));
        result.addParameterMarkers(getParameterMarkerSegments());
        return result;
    }
    
    @Override
    public ASTNode visitShowTables(final ShowTablesContext ctx) {
        MySQLShowTablesStatement result = new MySQLShowTablesStatement(getDatabaseType(), null == ctx.fromDatabase() ? null : (FromDatabaseSegment) visit(ctx.fromDatabase()),
                null == ctx.showFilter() ? null : (ShowFilterSegment) visit(ctx.showFilter()), null != ctx.FULL());
        result.addParameterMarkers(getParameterMarkerSegments());
        return result;
    }
    
    @Override
    public ASTNode visitShowTriggers(final ShowTriggersContext ctx) {
        MySQLShowTriggersStatement result = new MySQLShowTriggersStatement(
                getDatabaseType(), null == ctx.fromDatabase() ? null : (FromDatabaseSegment) visit(ctx.fromDatabase()), null == ctx.showFilter() ? null : (ShowFilterSegment) visit(ctx.showFilter()));
        result.addParameterMarkers(getParameterMarkerSegments());
        return result;
    }
    
    @Override
    public ASTNode visitShowWhereClause(final ShowWhereClauseContext ctx) {
        return new WhereSegment(ctx.getStart().getStartIndex(), ctx.getStop().getStopIndex(), (ExpressionSegment) visit(ctx.expr()));
    }
    
    @Override
    public ASTNode visitShowTableStatus(final ShowTableStatusContext ctx) {
        MySQLShowTableStatusStatement result = new MySQLShowTableStatusStatement(
                getDatabaseType(), null == ctx.fromDatabase() ? null : (FromDatabaseSegment) visit(ctx.fromDatabase()), null == ctx.showFilter() ? null : (ShowFilterSegment) visit(ctx.showFilter()));
        result.addParameterMarkers(getParameterMarkerSegments());
        return result;
    }
    
    @Override
    public ASTNode visitShowColumns(final ShowColumnsContext ctx) {
        MySQLShowColumnsStatement result = new MySQLShowColumnsStatement(getDatabaseType(), null == ctx.fromTable() ? null : ((FromTableSegment) visit(ctx.fromTable())).getTable(),
                null == ctx.fromDatabase() ? null : (FromDatabaseSegment) visit(ctx.fromDatabase()), null == ctx.showFilter() ? null : (ShowFilterSegment) visit(ctx.showFilter()));
        result.addParameterMarkers(getParameterMarkerSegments());
        return result;
    }
    
    @Override
    public ASTNode visitShowFilter(final ShowFilterContext ctx) {
        ShowFilterSegment result = new ShowFilterSegment(ctx.getStart().getStartIndex(), ctx.getStop().getStopIndex());
        if (null != ctx.showLike()) {
            result.setLike((ShowLikeSegment) visit(ctx.showLike()));
        }
        if (null != ctx.showWhereClause()) {
            result.setWhere((WhereSegment) visit(ctx.showWhereClause()));
        }
        return result;
    }
    
    @Override
    public ASTNode visitShowIndex(final ShowIndexContext ctx) {
        return new MySQLShowIndexStatement(getDatabaseType(), null == ctx.fromTable() ? null : ((FromTableSegment) visitFromTable(ctx.fromTable())).getTable(),
                null == ctx.fromDatabase() ? null : (FromDatabaseSegment) visit(ctx.fromDatabase()));
    }
    
    @Override
    public ASTNode visitShowCreateTable(final ShowCreateTableContext ctx) {
        return new MySQLShowCreateTableStatement(getDatabaseType(), (SimpleTableSegment) visit(ctx.tableName()));
    }
    
    @Override
    public ASTNode visitShowCreateTrigger(final ShowCreateTriggerContext ctx) {
        return new MySQLShowCreateTriggerStatement(getDatabaseType(), ((IdentifierValue) visit(ctx.triggerName())).getValue());
    }
    
    @Override
    public ASTNode visitShowRelaylogEvent(final ShowRelaylogEventContext ctx) {
        return new MySQLShowRelayLogEventsStatement(getDatabaseType(), null == ctx.logName() ? null : ((StringLiteralValue) visit(ctx.logName().stringLiterals())).getValue(),
                null == ctx.limitClause() ? null : (LimitSegment) visit(ctx.limitClause()), null == ctx.channelName() ? null : ((IdentifierValue) visit(ctx.channelName())).getValue());
    }
    
    @Override
    public ASTNode visitShowFunctionCode(final ShowFunctionCodeContext ctx) {
        return new MySQLShowFunctionCodeStatement(getDatabaseType(), ((FunctionSegment) visit(ctx.functionName())).getFunctionName());
    }
    
    @Override
    public ASTNode visitShowGrants(final ShowGrantsContext ctx) {
        return new MySQLShowGrantsStatement(getDatabaseType());
    }
    
    @Override
    public ASTNode visitShowMasterStatus(final ShowMasterStatusContext ctx) {
        return new MySQLShowMasterStatusStatement(getDatabaseType());
    }
    
    @Override
    public ASTNode visitShowSlaveHosts(final ShowSlaveHostsContext ctx) {
        return new MySQLShowSlaveHostsStatement(getDatabaseType());
    }
    
    @Override
    public ASTNode visitShowReplicaStatus(final ShowReplicaStatusContext ctx) {
        return new MySQLShowReplicaStatusStatement(getDatabaseType(), null == ctx.channelName() ? null : ((IdentifierValue) visit(ctx.channelName())).getValue());
    }
    
    @Override
    public ASTNode visitShowSlaveStatus(final ShowSlaveStatusContext ctx) {
        return new MySQLShowSlaveStatusStatement(getDatabaseType(), null == ctx.channelName() ? null : ((IdentifierValue) visit(ctx.channelName())).getValue());
    }
    
    @Override
    public ASTNode visitCreateResourceGroup(final CreateResourceGroupContext ctx) {
        return new MySQLCreateResourceGroupStatement(getDatabaseType(), ((IdentifierValue) visit(ctx.groupName())).getValue());
    }
    
    @Override
    public ASTNode visitBinlog(final BinlogContext ctx) {
        return new MySQLBinlogStatement(getDatabaseType(), ((StringLiteralValue) visit(ctx.stringLiterals())).getValue());
    }
    
    @Override
    public ASTNode visitFromTable(final FromTableContext ctx) {
        FromTableSegment result = new FromTableSegment();
        result.setTable((SimpleTableSegment) visit(ctx.tableName()));
        return result;
    }
    
    @Override
    public ASTNode visitShowVariables(final ShowVariablesContext ctx) {
        MySQLShowVariablesStatement result = new MySQLShowVariablesStatement(getDatabaseType(), null == ctx.showFilter() ? null : (ShowFilterSegment) visit(ctx.showFilter()));
        result.addParameterMarkers(getParameterMarkerSegments());
        return result;
    }
    
    @Override
    public ASTNode visitShowCharacterSet(final ShowCharacterSetContext ctx) {
        MySQLShowCharacterSetStatement result = new MySQLShowCharacterSetStatement(getDatabaseType(), null == ctx.showFilter() ? null : (ShowFilterSegment) visit(ctx.showFilter()));
        result.addParameterMarkers(getParameterMarkerSegments());
        return result;
    }
    
    @Override
    public ASTNode visitShowCollation(final ShowCollationContext ctx) {
        MySQLShowCollationStatement result = new MySQLShowCollationStatement(getDatabaseType(), null == ctx.showFilter() ? null : (ShowFilterSegment) visit(ctx.showFilter()));
        result.addParameterMarkers(getParameterMarkerSegments());
        return result;
    }
    
    @Override
    public ASTNode visitShowFunctionStatus(final ShowFunctionStatusContext ctx) {
        MySQLShowFunctionStatusStatement result = new MySQLShowFunctionStatusStatement(getDatabaseType(), null == ctx.showFilter() ? null : (ShowFilterSegment) visit(ctx.showFilter()));
        result.addParameterMarkers(getParameterMarkerSegments());
        return result;
    }
    
    @Override
    public ASTNode visitShowProcedureStatus(final ShowProcedureStatusContext ctx) {
        MySQLShowProcedureStatusStatement result = new MySQLShowProcedureStatusStatement(getDatabaseType(), null == ctx.showFilter() ? null : (ShowFilterSegment) visit(ctx.showFilter()));
        result.addParameterMarkers(getParameterMarkerSegments());
        return result;
    }
    
    @Override
    public ASTNode visitShowOpenTables(final ShowOpenTablesContext ctx) {
        MySQLShowOpenTablesStatement result = new MySQLShowOpenTablesStatement(
                getDatabaseType(), null == ctx.fromDatabase() ? null : (FromDatabaseSegment) visit(ctx.fromDatabase()), null == ctx.showFilter() ? null : (ShowFilterSegment) visit(ctx.showFilter()));
        result.addParameterMarkers(getParameterMarkerSegments());
        return result;
    }
    
    @Override
    public ASTNode visitShowPlugins(final ShowPluginsContext ctx) {
        return new MySQLShowPluginsStatement(getDatabaseType());
    }
    
    @Override
    public ASTNode visitShowPrivileges(final ShowPrivilegesContext ctx) {
        return new MySQLShowPrivilegesStatement(getDatabaseType());
    }
    
    @Override
    public ASTNode visitShutdown(final ShutdownContext ctx) {
        return new MySQLShutdownStatement(getDatabaseType());
    }
    
    @Override
    public ASTNode visitShowProcesslist(final ShowProcesslistContext ctx) {
        return new MySQLShowProcessListStatement(getDatabaseType(), null != ctx.FULL());
    }
    
    @Override
    public ASTNode visitShowCreateUser(final ShowCreateUserContext ctx) {
        return new MySQLShowCreateUserStatement(getDatabaseType(), ((IdentifierValue) visit(ctx.username())).getValue());
    }
    
    @Override
    public ASTNode visitSetVariable(final SetVariableContext ctx) {
        return new SetStatement(getDatabaseType(), getVariableAssigns(ctx.optionValueList()));
    }
    
    private List<VariableAssignSegment> getVariableAssigns(final OptionValueListContext ctx) {
        List<VariableAssignSegment> result = new LinkedList<>();
        result.add(null == ctx.optionValueNoOptionType() ? getVariableAssignSegment(ctx) : getVariableAssignSegment(ctx.optionValueNoOptionType()));
        for (OptionValueContext each : ctx.optionValue()) {
            result.add(getVariableAssignSegment(each));
        }
        return result;
    }
    
    private VariableAssignSegment getVariableAssignSegment(final OptionValueContext ctx) {
        if (null != ctx.optionValueNoOptionType()) {
            return getVariableAssignSegment(ctx.optionValueNoOptionType());
        }
        VariableSegment variable = new VariableSegment(
                ctx.internalVariableName().start.getStartIndex(), ctx.internalVariableName().stop.getStopIndex(), ctx.internalVariableName().getText(), ctx.optionType().getText());
        return new VariableAssignSegment(ctx.start.getStartIndex(), ctx.stop.getStopIndex(), variable, ctx.setExprOrDefault().getText());
    }
    
    private VariableAssignSegment getVariableAssignSegment(final OptionValueListContext ctx) {
        VariableSegment variable = new VariableSegment(
                ctx.internalVariableName().start.getStartIndex(), ctx.internalVariableName().stop.getStopIndex(), ctx.internalVariableName().getText(), ctx.optionType().getText());
        return new VariableAssignSegment(ctx.start.getStartIndex(), ctx.setExprOrDefault().stop.getStopIndex(), variable, ctx.setExprOrDefault().getText());
    }
    
    private VariableAssignSegment getVariableAssignSegment(final OptionValueNoOptionTypeContext ctx) {
        return new VariableAssignSegment(ctx.start.getStartIndex(), ctx.stop.getStopIndex(), getVariableSegment(ctx), getAssignValue(ctx));
    }
    
    private VariableSegment getVariableSegment(final OptionValueNoOptionTypeContext ctx) {
        if (null != ctx.NAMES()) {
            // TODO Consider setting all three system variables: character_set_client, character_set_results, character_set_connection
            return new VariableSegment(ctx.NAMES().getSymbol().getStartIndex(), ctx.NAMES().getSymbol().getStopIndex(), "character_set_client");
        }
        if (null != ctx.internalVariableName()) {
            return new VariableSegment(ctx.internalVariableName().start.getStartIndex(), ctx.internalVariableName().stop.getStopIndex(), ctx.internalVariableName().getText());
        }
        if (null != ctx.userVariable()) {
            return new VariableSegment(ctx.userVariable().start.getStartIndex(), ctx.userVariable().stop.getStopIndex(), ctx.userVariable().getText());
        }
        if (null != ctx.setSystemVariable()) {
            VariableSegment result = new VariableSegment(
                    ctx.setSystemVariable().start.getStartIndex(), ctx.setSystemVariable().stop.getStopIndex(), ctx.setSystemVariable().internalVariableName().getText());
            OptionTypeContext optionType = ctx.setSystemVariable().optionType();
            result.setScope(null == optionType ? "SESSION" : optionType.getText());
            return result;
        }
        return null;
    }
    
    private String getAssignValue(final OptionValueNoOptionTypeContext ctx) {
        if (null != ctx.NAMES()) {
            return ctx.charsetName().getText();
        } else if (null != ctx.internalVariableName()) {
            return ctx.setExprOrDefault().getText();
        } else if (null != ctx.userVariable()) {
            return ctx.expr().getText();
        } else if (null != ctx.setSystemVariable()) {
            return ctx.setExprOrDefault().getText();
        }
        return null;
    }
    
    @Override
    public ASTNode visitSetCharacter(final SetCharacterContext ctx) {
        int startIndex = null == ctx.CHARSET() ? ctx.CHARACTER().getSymbol().getStartIndex() : ctx.CHARSET().getSymbol().getStartIndex();
        int stopIndex = null == ctx.CHARSET() ? ctx.SET(1).getSymbol().getStopIndex() : ctx.CHARSET().getSymbol().getStopIndex();
        // TODO Consider setting all three system variables: character_set_client, character_set_results, character_set_connection
        String variableName = (null == ctx.CHARSET()) ? "character_set_client" : ctx.CHARSET().getText();
        VariableSegment variable = new VariableSegment(startIndex, stopIndex, variableName);
        String assignValue = (null == ctx.DEFAULT()) ? ctx.charsetName().getText() : ctx.DEFAULT().getText();
        return new SetStatement(getDatabaseType(), Collections.singletonList(new VariableAssignSegment(startIndex, stopIndex, variable, assignValue)));
    }
    
    @Override
    public ASTNode visitFromDatabase(final FromDatabaseContext ctx) {
        return new FromDatabaseSegment(ctx.getStart().getStartIndex(), (DatabaseSegment) visit(ctx.databaseName()));
    }
    
    @Override
    public ASTNode visitShowLike(final ShowLikeContext ctx) {
        StringLiteralValue literalValue = (StringLiteralValue) visit(ctx.stringLiterals());
        return new ShowLikeSegment(ctx.getStart().getStartIndex(), ctx.getStop().getStopIndex(), literalValue.getValue());
    }
    
    @Override
    public ASTNode visitCreateLoadableFunction(final CreateLoadableFunctionContext ctx) {
        return new MySQLCreateLoadableFunctionStatement(getDatabaseType());
    }
    
    @Override
    public ASTNode visitInstallComponent(final InstallComponentContext ctx) {
        return new MySQLInstallComponentStatement(getDatabaseType(), ctx.componentName().stream().map(each -> ((StringLiteralValue) visit(each.string_())).getValue()).collect(Collectors.toList()));
    }
    
    @Override
    public ASTNode visitUninstallComponent(final UninstallComponentContext ctx) {
        return new MySQLUninstallComponentStatement(getDatabaseType(), ctx.componentName().stream().map(each -> ((StringLiteralValue) visit(each.string_())).getValue()).collect(Collectors.toList()));
    }
    
    @Override
    public ASTNode visitRestart(final RestartContext ctx) {
        return new MySQLRestartStatement(getDatabaseType());
    }
    
    @Override
    public ASTNode visitSetResourceGroup(final SetResourceGroupContext ctx) {
        return new MySQLSetResourceGroupStatement(getDatabaseType(), ((IdentifierValue) visit(ctx.groupName())).getValue());
    }
    
    @Override
    public ASTNode visitCheckTable(final CheckTableContext ctx) {
        return new MySQLCheckTableStatement(getDatabaseType(), ((CollectionValue<SimpleTableSegment>) visit(ctx.tableList())).getValue());
    }
    
    @Override
    public ASTNode visitDropResourceGroup(final DropResourceGroupContext ctx) {
        return new MySQLDropResourceGroupStatement(getDatabaseType(), ((IdentifierValue) visit(ctx.groupName())).getValue());
    }
    
    @Override
    public ASTNode visitAlterResourceGroup(final AlterResourceGroupContext ctx) {
        return new MySQLAlterResourceGroupStatement(getDatabaseType(), ((IdentifierValue) visit(ctx.groupName())).getValue());
    }
    
    @Override
    public ASTNode visitChangeMasterTo(final ChangeMasterToContext ctx) {
        return new MySQLChangeMasterStatement(getDatabaseType());
    }
    
    @Override
    public ASTNode visitStartSlave(final StartSlaveContext ctx) {
        return new MySQLStartSlaveStatement(getDatabaseType());
    }
    
    @Override
    public ASTNode visitStopSlave(final StopSlaveContext ctx) {
        return new MySQLStopSlaveStatement(getDatabaseType());
    }
    
    @Override
    public ASTNode visitChangeReplicationSourceTo(final ChangeReplicationSourceToContext ctx) {
        return new MySQLChangeReplicationSourceToStatement(getDatabaseType());
    }
    
    @Override
    public ASTNode visitStartReplica(final MySQLStatementParser.StartReplicaContext ctx) {
        return new MySQLStartReplicaStatement(getDatabaseType());
    }
    
    @Override
    public ASTNode visitDelimiter(final DelimiterContext ctx) {
        return new MySQLDelimiterStatement(getDatabaseType(), ctx.delimiterName().getText());
    }
    
    @Override
    public ASTNode visitHelp(final HelpContext ctx) {
        return new MySQLHelpStatement(getDatabaseType(), ctx.textOrIdentifier().getText());
    }
}
