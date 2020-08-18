package com.servicelive.orderfulfillment.common;

import com.servicelive.orderfulfillment.domain.SOTask;
import org.apache.log4j.Logger;

import java.util.List;

public class DumpUtil {
    private static final Logger logger = Logger.getLogger(DumpUtil.class);

    public static void dumpTasks(List<SOTask> tasks, String sourceClass) {
        if (!logger.isDebugEnabled()) return;
        StringBuilder sb = new StringBuilder("\nTask dump at :" + sourceClass);
        if (tasks == null) {
            sb.append("\ntask list is null");
            logger.debug(sb.toString());
            return;
        }
        for (SOTask task : tasks) {
            sb.append("\ntaskId        : ").append(task.getTaskId());
            sb.append("\nexternalSku   : ").append(task.getExternalSku());
            sb.append("\nskillNodeId   : ").append(task.getSkillNodeId());
            sb.append("\nisPrimaryTask : ").append(task.isPrimaryTask());
        }
        logger.debug(sb.toString());
    }
}
