/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.servicelive.routing.tiered.schedulermanager;

import static com.servicelive.routing.tiered.constants.SPNTieredRoutingConstants.JOB_GROUP_NAME;
import static com.servicelive.routing.tiered.constants.SPNTieredRoutingConstants.TRG_GROUP_NAME;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.impl.StdScheduler;

import com.servicelive.routing.tiered.vo.SPNTierEventInfoVO;

/**
 * This class is primarily used for managing the Events. It should not do anything with real business,
 * rather it should be contained or used by real business class like TRRouteController
 * @author hoza
 */
public class TierRoutingSchedulerManager {
    private StdScheduler tieredRouteScheduler;
    private Logger logger = Logger.getLogger(TierRoutingSchedulerManager.class);

    /**
     * This method should be used for any specific resource allocation while starting up Scheduler
     */
    public void initManager(){
    	logger.info("Starting scheduler");
         
    }
    /**
     * free up resources here
     */
    public void closeManager(){
         logger.info("Closing scheduler");
    }

    /**
     * Heart of the scheduler.  Sinature of this method is big... if yo need to add at leadt one new paramter.. refactor method to accet value Object
     * @param tierId
     * @param orderId
     * @param isGrouped
     * @param nextFireTime
     * @param reasonId
     * @throws Exception
     */
    public void scheduleJob(SPNTierEventInfoVO tierEventInfo,Boolean isGrouped,Integer reasonId) throws Exception {
    	String orderId = tierEventInfo.getSoId();
    	Integer tierId = tierEventInfo.getNextTierId();
    	Date nextFireTime = tierEventInfo.getNextFireTime();
            if (isGrouped) {
			orderId = tierEventInfo.getGroupOrderId();
		}
        SPNTieredRoutingTrigger trigger =  this.createTrigger(tierId,orderId,reasonId,nextFireTime);
               SPNTieredRoutingJobDetails job =  this.createJob(tierId,orderId,isGrouped,reasonId);
               logger.debug("Trying to Schedule Tiered JOB for " + tierId + " For SO ID = " + orderId);
               this.tieredRouteScheduler.scheduleJob(job,trigger);
               logger.debug("Schduling  COMPLETE for  Tiered JOB for " + tierId + " For SO ID = " + orderId);
    }
    
    /**
     * Stops  any running rob and remove 'em
     * @param orderId
     */
    public void stopAndRemoveJob(String orderId){
    	logger.debug("trying to stop and Remove the JOB for : "+ orderId );
    	try {
			String jobname = SPNJobNameUtil.getJOBName(orderId);
			String groupname = SPNJobNameUtil.getJOBGroupName(null, orderId);
			//tieredRouteScheduler.getCurrentlyExecutingJobs())
			this.tieredRouteScheduler.deleteJob(jobname, groupname);
			logger.debug("removed TIERED ROUTED JOB for : "+ orderId );
		} catch (Exception e) {
			logger.error("Could not remove the TIERED ROUTED JOB " + e.getMessage());
		}
    }


    /** 
     * Why this one is  public.. this is utility method.. so that anyone trying to use ths TRScheulerManager  can have access to it
     * @param tierId
     * @param orderId
     * @param reasonCode
     * @return
     * @throws Exception
     */
    public SPNTieredRoutingTrigger getTriggerForOrderId(Integer tierId,String orderId, Integer reasonCode) throws Exception{
         Object trg =  tieredRouteScheduler.getTrigger(SPNJobNameUtil.getTriggerName(tierId, orderId), TRG_GROUP_NAME);
         if(trg != null) return (SPNTieredRoutingTrigger) trg ;
         else return null;
         
    }

 
    private SPNTieredRoutingTrigger createTrigger(Integer tierId,String orderId,Integer reasonCode,Date nextFireTime) throws Exception {
        SPNTieredRoutingTrigger trigger = getTriggerForOrderId(tierId,orderId,reasonCode);
        if(trigger == null) {
            trigger = new SPNTieredRoutingTrigger(tierId,orderId,reasonCode);
        }
          trigger.setNextFireTime(nextFireTime);
          trigger.setStartTime(nextFireTime);
          
        return trigger;
    }

    public JobDetail getJob(Integer tierId, String orderId) throws Exception {
        Object trg =  tieredRouteScheduler.getJobDetail(SPNJobNameUtil.getJOBName(orderId), JOB_GROUP_NAME);
        if(trg != null) {

            return (JobDetail) trg ;
        }
        else return null;
   }

    
     private  SPNTieredRoutingJobDetails createJob(Integer tierId,String orderId,Boolean isGrouped,Integer reasonId) throws Exception {
    	 JobDetail job = getJob(tierId,orderId);
         SPNTieredRoutingJobDetails  spnjob  = null;
         if(job == null) {
             spnjob = new SPNTieredRoutingJobDetails(tierId,orderId,isGrouped,reasonId);
            return spnjob;
        }
         else {
        	 tieredRouteScheduler.deleteJob(job.getName(), job.getGroup());
        	 logger.debug(" Tiered ROUTE JOB DLETEING : exiting job : "+ job.getName());
             spnjob = new SPNTieredRoutingJobDetails(tierId,orderId,isGrouped,reasonId);
         }
        return spnjob;
    }

	/**
	 * @return the tieredRouteScheduler
	 */
	public StdScheduler getTieredRouteScheduler() {
		return tieredRouteScheduler;
	}

	/**
	 * @param tieredRouteScheduler the tieredRouteScheduler to set
	 */
	public void setTieredRouteScheduler(StdScheduler tieredRouteScheduler) {
		this.tieredRouteScheduler = tieredRouteScheduler;
	}


}
