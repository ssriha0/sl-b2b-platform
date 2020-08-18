package com.newco.marketplace.dto.vo.memberOffer;

import java.io.Serializable;
import java.util.List;

public class MemberOfferDetailsVO implements Serializable {
	private static final long serialVersionUID = -1527770441184997895L;
	private String staticImageOnePath;
	private String staticImageTwoPath;
	private String staticImageThreePath;
	private String mainTextOne;
	private String mainTextTwo;
	private String listHeader;
	private String targetSite;
	private String companyName;
	private String offerDetailsCompanyName;
	private String popUpCompanyName;
	private String mainTextThree;
	public String getOfferDetailsCompanyName() {
		return offerDetailsCompanyName;
	}

	public void setOfferDetailsCompanyName(String offerDetailsCompanyName) {
		this.offerDetailsCompanyName = offerDetailsCompanyName;
	}

	public String getPopUpCompanyName() {
		return popUpCompanyName;
	}

	public void setPopUpCompanyName(String popUpCompanyName) {
		this.popUpCompanyName = popUpCompanyName;
	}

	private List<String> valueList;

	public List<String> getValueList() {
		return valueList;
	}

	public void setValueList(List<String> valueList) {
		this.valueList = valueList;
	}
	

	public String getStaticImageOnePath() {
		return staticImageOnePath;
	}

	public void setStaticImageOnePath(String staticImageOnePath) {
		this.staticImageOnePath = staticImageOnePath;
	}

	public String getStaticImageTwoPath() {
		return staticImageTwoPath;
	}

	public void setStaticImageTwoPath(String staticImageTwoPath) {
		this.staticImageTwoPath = staticImageTwoPath;
	}

	public String getStaticImageThreePath() {
		return staticImageThreePath;
	}

	public void setStaticImageThreePath(String staticImageThreePath) {
		this.staticImageThreePath = staticImageThreePath;
	}

	public String getMainTextOne() {
		return mainTextOne;
	}

	public void setMainTextOne(String mainTextOne) {
		this.mainTextOne = mainTextOne;
	}

	public String getMainTextTwo() {
		return mainTextTwo;
	}

	public void setMainTextTwo(String mainTextTwo) {
		this.mainTextTwo = mainTextTwo;
	}

	public String getListHeader() {
		return listHeader;
	}

	public void setListHeader(String listHeader) {
		this.listHeader = listHeader;
	}

	public String getTargetSite() {
		return targetSite;
	}

	public void setTargetSite(String targetSite) {
		this.targetSite = targetSite;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMainTextThree() {
		return mainTextThree;
	}

	public void setMainTextThree(String mainTextThree) {
		this.mainTextThree = mainTextThree;
	}
}
