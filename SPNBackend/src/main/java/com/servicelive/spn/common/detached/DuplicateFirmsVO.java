/**
 * 
 */
package com.servicelive.spn.common.detached;
import java.io.Serializable;
import java.util.List;
import com.servicelive.spn.common.detached.DuplicateProviderVO;

public class DuplicateFirmsVO  implements Serializable {
	private Integer providerFirmId;
	private Integer originalSPNId;
	private Integer aliasSPNId;
	private List<DuplicateProviderVO> originalSPNProvList;
	private List<DuplicateProviderVO> aliasSPNProvList;
	private List<Integer> toBeRemovedFirmsList;
	private List<Integer> toBeRemovedProvidersList;
	
	public List<Integer> getToBeRemovedFirmsList() {
		return toBeRemovedFirmsList;
	}
	public void setToBeRemovedFirmsList(List<Integer> toBeRemovedFirmsList) {
		this.toBeRemovedFirmsList = toBeRemovedFirmsList;
	}
	public List<Integer> getToBeRemovedProvidersList() {
		return toBeRemovedProvidersList;
	}
	public void setToBeRemovedProvidersList(List<Integer> toBeRemovedProvidersList) {
		this.toBeRemovedProvidersList = toBeRemovedProvidersList;
	}
	public Integer getProviderFirmId() {
		return providerFirmId;
	}
	public void setProviderFirmId(Integer providerFirmId) {
		this.providerFirmId = providerFirmId;
	}
	public List<DuplicateProviderVO> getOriginalSPNProvList() {
		return originalSPNProvList;
	}
	public void setOriginalSPNProvList(List<DuplicateProviderVO> originalSPNProvList) {
		this.originalSPNProvList = originalSPNProvList;
	}
	public List<DuplicateProviderVO> getAliasSPNProvList() {
		return aliasSPNProvList;
	}
	public void setAliasSPNProvList(List<DuplicateProviderVO> aliasSPNProvList) {
		this.aliasSPNProvList = aliasSPNProvList;
	}
	public Integer getOriginalSPNId() {
		return originalSPNId;
	}
	public void setOriginalSPNId(Integer originalSPNId) {
		this.originalSPNId = originalSPNId;
	}
	public Integer getAliasSPNId() {
		return aliasSPNId;
	}
	public void setAliasSPNId(Integer aliasSPNId) {
		this.aliasSPNId = aliasSPNId;
	}	
}