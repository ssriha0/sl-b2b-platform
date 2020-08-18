
package com.newco.marketplace.api.beans.search.providerProfile;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.search.vo.ProviderSearchResponseVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a bean class for storing all information of Insurance.
 * @author Infosys
 *
 */
@XStreamAlias("insurances")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyInsurances {
	

	@OptionalParam
	@XStreamImplicit(itemFieldName="insurance")
	private List<CompanyInsurance> insurancesList;
	
	public CompanyInsurances(){
		
	}
	
	public CompanyInsurances(ProviderSearchResponseVO providerSearchResponseVO) {
		this.insurancesList = new ArrayList<CompanyInsurance>();
		//reviews.setFilter("");
		CompanyInsurance workmake = new CompanyInsurance("workman",
				providerSearchResponseVO.isInsVerifiedWorker(),
				providerSearchResponseVO.getInsVerificationDateWorker(),
				providerSearchResponseVO.getWorkersCompInsurance());
		CompanyInsurance auto = new CompanyInsurance("auto",
				providerSearchResponseVO.isInsVerifiedAuto(),
				providerSearchResponseVO.getInsVerificationDateAuto(),
				providerSearchResponseVO.getVehicalInsurance());
		CompanyInsurance gen = new CompanyInsurance("general",
				providerSearchResponseVO.isInsVerifiedGen(),
				providerSearchResponseVO.getInsVerificationDateGen(),
				providerSearchResponseVO.getGeneralInsurance());
		this.insurancesList.add(gen);
		this.insurancesList.add(workmake);	
		this.insurancesList.add(auto);	
	}

	public List<CompanyInsurance> getInsurancesList() {
		return insurancesList;
	}

	public void setInsurancesList(List<CompanyInsurance> insurancesList) {
		this.insurancesList = insurancesList;
	}



}
