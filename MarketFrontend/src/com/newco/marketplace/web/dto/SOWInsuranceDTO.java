package com.newco.marketplace.web.dto;


public class SOWInsuranceDTO extends SOWBaseTabDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2490095475187221994L;
	private boolean generalLiability = false;
	private boolean automotive = false;
	private boolean workersCompensation = false;
	private boolean slVerified = false;	

	public boolean isGeneralLiability() {
		return generalLiability;
	}

	public void setGeneralLiability(boolean generalLiability) {
		this.generalLiability = generalLiability;
	}

	public boolean isAutomotive() {
		return automotive;
	}

	public void setAutomotive(boolean automotive) {
		this.automotive = automotive;
	}

	public boolean isWorkersCompensation() {
		return workersCompensation;
	}

	public void setWorkersCompensation(boolean workersCompensation) {
		this.workersCompensation = workersCompensation;
	}

	public boolean isSlVerified() {
		return slVerified;
	}

	public void setSlVerified(boolean slVerified) {
		this.slVerified = slVerified;
	}

	@Override
	public String getTabIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		//return null;
	}

}
