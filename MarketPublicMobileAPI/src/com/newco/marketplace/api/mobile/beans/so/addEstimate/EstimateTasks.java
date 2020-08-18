package com.newco.marketplace.api.mobile.beans.so.addEstimate;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("estimateTasks")
public class EstimateTasks {
	
	@XStreamImplicit(itemFieldName="estimateTask")
	private List<EstimateTask> estimateTask;

	public List<EstimateTask> getEstimateTask() {
		return estimateTask;
	}

	public void setEstimateTask(List<EstimateTask> estimateTask) {
		this.estimateTask = estimateTask;
	}
	

	
}
