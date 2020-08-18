/**
 * 
 */
package com.servicelive.staging.domain.hsr;

import java.io.Serializable;
import java.util.List;

/**
 * @author hoza
 * 
 */
public class HSRStageOrders implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2049842976629878360L;
	/**
	 * 
	 */
	private List<HSRStageOrder> stageOrders;
	/**
	 * 
	 */
	private String inputFilenameHoldingThisOrders;

	/**
	 * @return the stageOrders
	 */
	public List<HSRStageOrder> getStageOrders() {
		return stageOrders;
	}

	/**
	 * @param stageOrders
	 *            the stageOrders to set
	 */
	public void setStageOrders(List<HSRStageOrder> stageOrders) {
		this.stageOrders = stageOrders;
	}

	/**
	 * @return the inputFilenameHoldingThisOrders
	 */
	public String getInputFilenameHoldingThisOrders() {
		return inputFilenameHoldingThisOrders;
	}

	/**
	 * @param inputFilenameHoldingThisOrders the inputFilenameHoldingThisOrders to set
	 */
	public void setInputFilenameHoldingThisOrders(
			String inputFilenameHoldingThisOrders) {
		this.inputFilenameHoldingThisOrders = inputFilenameHoldingThisOrders;
	}

}
