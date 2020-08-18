/**
 * 
 */
package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.sears.os.vo.SerializableBaseVO;


/**
 * Value object for Service Order work flow  state transitions
 * 
 * @author blars04
 *
 */
public class ServiceOrderWorkflowStateTransitionVO extends SerializableBaseVO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3526677587731423631L;
	private Integer futureWorkflowState = null; 
    private ServiceOrder serviceOrder = null;
    private ArrayList<Integer> workflowStateTransitionList = new ArrayList<Integer>();
    private Timestamp updateTime = null;
    
    
    /**
     * @return the futureWorkflowState
     */
    public Integer getFutureWorkflowState() {
        return futureWorkflowState;
    }
    /**
     * @param futureWorkflowState the futureWorkflowState to set
     */
    public void setFutureWorkflowState(Integer futureWorkflowState) {
        this.futureWorkflowState = futureWorkflowState;
    }
    /**
     * @return the serviceOrder
     */
    public ServiceOrder getServiceOrder() {
        return serviceOrder;
    }
    /**
     * @param serviceOrder the serviceOrder to set
     */
    public void setServiceOrder(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }
    /**
     * @return the workflowStateTransitionList
     */
    public ArrayList<Integer> getWorkflowStateTransitionList() {
        return workflowStateTransitionList;
    }
    /**
     * @param workflowStateTransitionList the workflowStateTransitionList to set
     */
    public void setWorkflowStateTransitionList(ArrayList<Integer> workflowStateTransitionList) {
        this.workflowStateTransitionList = workflowStateTransitionList;
    }
    /**
     * @return the updateTime
     */
    public Timestamp getUpdateTime() {
        return updateTime;
    }
    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

}//end class