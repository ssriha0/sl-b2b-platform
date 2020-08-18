/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.servicelive.routing.tiered.schedulermanager;

import static com.servicelive.routing.tiered.constants.SPNTieredRoutingConstants.KEY_IS_GROUPED;
import static com.servicelive.routing.tiered.constants.SPNTieredRoutingConstants.KEY_SO_ID;
import static com.servicelive.routing.tiered.constants.SPNTieredRoutingConstants.KEY_TIER_ID;
import static com.servicelive.routing.tiered.constants.SPNTieredRoutingConstants.KEY_TR_REASON_CODE;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.servicelive.routing.tiered.services.TierRouteController;


/**
 *
 * @author hoza
 */
public class SPNTieredRoutingJob  implements StatefulJob{
	
	public static final String APPLICATION_CONTEXT_KEY = "applicationContext";
	
	protected ApplicationContext getApplicationContext(JobExecutionContext context ) throws JobExecutionException {
        ApplicationContext appCtx = null;
        try {
			appCtx = (ApplicationContext)context.getScheduler().getContext().get(APPLICATION_CONTEXT_KEY);
		} catch (SchedulerException e) {
            throw new JobExecutionException("No application context available in scheduler context for key \"" + APPLICATION_CONTEXT_KEY + "\"");
		}
        if (appCtx == null) {
            throw new JobExecutionException(
                    "No application context available in scheduler context for key \"" + APPLICATION_CONTEXT_KEY + "\"");
        }
        return appCtx;
    }
   

    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.print("Started executing job  at " + new Date(System.currentTimeMillis()));

        Integer tierId = (Integer) context.getJobDetail().getJobDataMap().get(KEY_TIER_ID);
        Integer reasonId = (Integer) context.getJobDetail().getJobDataMap().get(KEY_TR_REASON_CODE);
        String orderId = (String) context.getJobDetail().getJobDataMap().get(KEY_SO_ID);
        Boolean isGrouped = (Boolean) context.getJobDetail().getJobDataMap().get(KEY_IS_GROUPED);

        try {

            	ApplicationContext ctx = getApplicationContext(context);
            	TierRouteController controller = (TierRouteController)ctx.getBean("trRouteController");
                if(controller != null) {
                    controller.route(orderId, isGrouped,reasonId);
                }

            
        } catch (Exception ex) {
            Logger.getLogger(SPNTieredRoutingJob.class.getName()).log(Level.SEVERE, null, ex);
            throw new JobExecutionException(ex.getCause());
        }
        context.setResult("SUCCESS");
    }


}
