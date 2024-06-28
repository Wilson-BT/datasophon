package com.datasophon.worker.strategy;

import akka.actor.ActorRef;
import cn.hutool.core.net.NetUtil;
import com.datasophon.common.command.OlapOpsType;
import com.datasophon.common.command.OlapSqlExecCommand;
import com.datasophon.common.command.ServiceRoleOperateCommand;
import com.datasophon.common.enums.CommandType;
import com.datasophon.common.utils.ExecResult;
import com.datasophon.common.utils.HostUtils;
import com.datasophon.common.utils.ThrowableUtils;
import com.datasophon.worker.handler.ServiceHandler;
import com.datasophon.worker.utils.ActorUtils;

import java.sql.SQLException;

public class CNHandlerStrategy extends AbstractHandlerStrategy implements ServiceRoleStrategy {
    public CNHandlerStrategy(String serviceName, String serviceRoleName) {
        super(serviceName, serviceRoleName);
    }

    @Override
    public ExecResult handler(ServiceRoleOperateCommand command) throws SQLException, ClassNotFoundException {
        ExecResult startResult = new ExecResult();
        ServiceHandler serviceHandler = new ServiceHandler(command.getServiceName(), command.getServiceRoleName());

        if (command.getCommandType().equals(CommandType.INSTALL_SERVICE)) {
            logger.info("add cn to cluster");

            startResult = serviceHandler.start(command.getStartRunner(), command.getStatusRunner(),
                    command.getDecompressPackageName(), command.getRunAs());
            if (startResult.getExecResult()) {
                try {
                    OlapSqlExecCommand sqlExecCommand = new OlapSqlExecCommand();
                    sqlExecCommand.setFeMaster(command.getMasterHost());
                    // 使用IP 否则应用侧使用时需要设置host
                    sqlExecCommand.setHostName(HostUtils.getLocalHostName());
                    sqlExecCommand.setOpsType(OlapOpsType.ADD_CN);
                    ActorUtils.getRemoteActor(command.getManagerHost(), "masterNodeProcessingActor")
                            .tell(sqlExecCommand, ActorRef.noSender());
                } catch (Exception e) {
                    logger.error("add backend failed {}", ThrowableUtils.getStackTrace(e));
                }
                logger.info("slave be start success");
            } else {
                logger.error("slave be start failed");
            }
        } else {
            startResult = serviceHandler.start(command.getStartRunner(), command.getStatusRunner(),
                    command.getDecompressPackageName(), command.getRunAs());
        }
        return startResult;
    }
}
