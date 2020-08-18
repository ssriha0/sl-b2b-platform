package com.newco.marketplace.api.beans.so.price;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("taskPriceHistory")
public class TaskLevelPriceHistory {
	
	@XStreamImplicit(itemFieldName="taskPriceHistoryRecord")
	private List<TaskLevelPriceHistoryRecord> taskLevelPriceHistoryRecord;

	public List<TaskLevelPriceHistoryRecord> getTaskLevelPriceHistoryRecord() {
		return taskLevelPriceHistoryRecord;
	}

	public void setTaskLevelPriceHistoryRecord(
			List<TaskLevelPriceHistoryRecord> taskLevelPriceHistoryRecord) {
		this.taskLevelPriceHistoryRecord = taskLevelPriceHistoryRecord;
	}

}