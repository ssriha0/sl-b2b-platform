package com.servicelive.domain.spn.detached;

public class SPNPerformanceCriteria {
	
	private Integer spnId;
	private Integer criteriaId;
	private String criteriaScope;
	private String timeFrame;
	private Integer buyerId;
	
	public Integer getSpnId() {
		return spnId;
	}
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	public Integer getCriteriaId() {
		return criteriaId;
	}
	public void setCriteriaId(Integer criteriaId) {
		this.criteriaId = criteriaId;
	}
	public String getCriteriaScope() {
		return criteriaScope;
	}
	public void setCriteriaScope(String criteriaScope) {
		this.criteriaScope = criteriaScope;
	}
	public String getTimeFrame() {
		return timeFrame;
	}
	public void setTimeFrame(String timeFrame) {
		this.timeFrame = timeFrame;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	
	 @Override
	 public boolean equals(Object obj) {
		 if (obj == this) {
			 return true;
		 }
		 if (obj == null || obj.getClass() != this.getClass()) {
			 return false;
		 }

		 SPNPerformanceCriteria newObj = (SPNPerformanceCriteria) obj;
		 return criteriaId.intValue() == newObj.criteriaId.intValue()
		 	&& criteriaScope.equals(newObj.criteriaScope);
	 }

	
}
