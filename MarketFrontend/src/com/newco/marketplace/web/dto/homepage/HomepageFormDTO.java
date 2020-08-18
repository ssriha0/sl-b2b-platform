package com.newco.marketplace.web.dto.homepage;

import java.util.List;

import com.newco.marketplace.dto.vo.sitestatistics.PopularServicesVO;
import com.newco.marketplace.web.dto.SerializedBaseDTO;

/**
 * 
 * @author Chris Carlevato
 *
 * $Revision: 1.5 $ $Author: groma $ $Date: 2008/05/23 00:54:05 $
 */
public class HomepageFormDTO extends SerializedBaseDTO{

	private static final long serialVersionUID = 6722402660179605365L;
	private List<PopularServicesVO> popularProServices;
	private List<PopularServicesVO> popularSimpleServices;
	private List<String> blackoutStates; 
	private String zipCode;
	private String nodeId;
	private String percentSatisfaction;
	private String numProviders;
	private boolean isValidZip;
	
//	@Override
//	public void validate() {
//		if(getSimpleZip() == null){
//		   addError(getTheResourceBundle().getString("Zip"),
//				    getTheResourceBundle().getString("Zip_Validation_Missing"),
//				    OrderConstants.SOW_TAB_ERROR);
//		}
//		if(!isValidZip){
//			   addError(getTheResourceBundle().getString("Zip"),
//					    getTheResourceBundle().getString("Zip_Validation"),
//					    OrderConstants.SOW_TAB_ERROR);
//		}
//	}

	
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public List<PopularServicesVO> getPopularProServices() {
		return popularProServices;
	}
	public void setPopularProServices(List<PopularServicesVO> popularProServices) {
		this.popularProServices = popularProServices;
	}
	public List<PopularServicesVO> getPopularSimpleServices() {
		return popularSimpleServices;
	}
	public void setPopularSimpleServices(
			List<PopularServicesVO> popularSimpleServices) {
		this.popularSimpleServices = popularSimpleServices;
	}
	public String getPercentSatisfaction() {
		return percentSatisfaction;
	}
	public void setPercentSatisfaction(String percentSatisfaction) {
		this.percentSatisfaction = percentSatisfaction;
	}
	public String getNumProviders() {
		return numProviders;
	}
	public void setNumProviders(String numProviders) {
		this.numProviders = numProviders;
	}
	public boolean isValidZip() {
		return isValidZip;
	}

	public void setValidZip(boolean isValidZip) {
		this.isValidZip = isValidZip;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public List<String> getBlackoutStates() {
		return blackoutStates;
	}
	public void setBlackoutStates(List<String> blackoutStates) {
		this.blackoutStates = blackoutStates;
	}
	
}
