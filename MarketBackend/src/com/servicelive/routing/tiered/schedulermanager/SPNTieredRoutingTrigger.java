/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.servicelive.routing.tiered.schedulermanager;


import static com.servicelive.routing.tiered.constants.SPNTieredRoutingConstants.KEY_SO_ID;
import static com.servicelive.routing.tiered.constants.SPNTieredRoutingConstants.KEY_TIER_ID;
import static com.servicelive.routing.tiered.constants.SPNTieredRoutingConstants.TRG_GROUP_NAME;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleTrigger;
/**
 *
 * @author hoza
 */
public class SPNTieredRoutingTrigger  extends SimpleTrigger{
	/*@Transient
	private Logger logger = Logger.getLogger(SPNTieredRoutingTrigger.class);*/
    /**
	 * @return the tierId
	 */
	public Integer getTierId() {
		return tierId;
	}

	/**
	 * @param tierId the tierId to set
	 */
	public void setTierId(Integer tierId) {
		this.tierId = tierId;
	}



	private Integer tierId;
    private String soId;
    private Integer reasonCode;

    public String getSoId() {
    	
        return soId;
    }

    public void setSoId(String soId) {
        this.soId = soId;
    }
    /**
     * 
     * @param spnId
     * @param soId
     * @throws Exception
     */

  /*  public SPNTieredRoutingTrigger(Integer tierId,String soId) throws Exception {
        this.tierId = tierId;
        this.soId = soId;
        setUp();

    }*/
    public SPNTieredRoutingTrigger(Integer tierId,String soId, Integer reasonCode) throws Exception {
        this.tierId = tierId;
        this.soId = soId;
        this.reasonCode = reasonCode;
        setUp();

    }
    

    @Override
    public Date getNextFireTime() {
        //System.out.println("next Fire time excuted");
        return super.getNextFireTime();
    }

    @Override
    public int executionComplete(JobExecutionContext context, JobExecutionException result) {
        Integer tierId =  (Integer) context.getJobDetail().getJobDataMap().get(KEY_TIER_ID);
        String soId =  (String) context.getJobDetail().getJobDataMap().get(KEY_SO_ID);
       // logger.debug("---------->execution Complete for  tierId = " + tierId + " so id = " +  soId + "with result "+ context.getResult());
        return super.executionComplete(context, result);
    }

   

    private void setUp() throws Exception {
        System.out.println("caling set up");
        this.setName(SPNJobNameUtil.getTriggerName(tierId, soId));
        this.setGroup(TRG_GROUP_NAME);
        this.setJobName(SPNJobNameUtil.getJOBName(soId));
        this.setJobGroup(SPNJobNameUtil.getJOBGroupName(tierId, soId));
      
    }

	/**
	 * @return the reasonCode
	 */
	public Integer getReasonCode() {
		return reasonCode;
	}

	/**
	 * @param reasonCode the reasonCode to set
	 */
	public void setReasonCode(Integer reasonCode) {
		this.reasonCode = reasonCode;
	}

}
