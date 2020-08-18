
package com.newco.marketplace.api.beans.search.providerProfile;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.search.types.CustomerFeedback;
import com.newco.marketplace.search.vo.ProviderSearchResponseVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a bean class for storing all information of Insurance.
 * @author Shekhar Nirkhe
 *
 */
@XStreamAlias("insurances")
public class Insurances {
	

	@OptionalParam
	@XStreamImplicit(itemFieldName="insurance")
	private List<Insurance> insurancesList;
	
	public Insurances(){
		
	}
	
	public Insurances(ProviderSearchResponseVO providerSearchResponseVO) {
		this.insurancesList = new ArrayList<Insurance>();
		//reviews.setFilter("");
		Insurance workmake = new Insurance("workman",
				providerSearchResponseVO.isInsVerifiedWorker(),
				providerSearchResponseVO.getInsVerificationDateWorker(),
				providerSearchResponseVO.getWorkersCompInsurance());
		Insurance auto = new Insurance("auto",
				providerSearchResponseVO.isInsVerifiedAuto(),
				providerSearchResponseVO.getInsVerificationDateAuto(),
				providerSearchResponseVO.getVehicalInsurance());
		Insurance gen = new Insurance("general",
				providerSearchResponseVO.isInsVerifiedGen(),
				providerSearchResponseVO.getInsVerificationDateGen(),
				providerSearchResponseVO.getGeneralInsurance());
		this.insurancesList.add(gen);
		this.insurancesList.add(workmake);	
		this.insurancesList.add(auto);	
	}

	public List<Insurance> getInsurancesList() {
		return insurancesList;
	}

	public void setInsurancesList(List<Insurance> insurancesList) {
		this.insurancesList = insurancesList;
	}

}
