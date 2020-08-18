package com.newco.marketplace.api.beans.survey;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by kjain on 1/2/2019.
 */

@XSD(name = "SurveyQuestionnaireDetailResponse.xsd", path = "/resources/schemas/mobile/")
@XmlRootElement(name = "surveyQuestionnaireDetail")
@XStreamAlias("surveyQuestionnaireDetail")
@XmlAccessorType(XmlAccessType.FIELD)
public class SurveyQuestionnaireDetailResponse {
    @XStreamAlias("buyerDetails")
    BuyerDetails buyerDetails;
    @XStreamAlias("csat")
    CSATSurveyQuestion csat;
    @XStreamAlias("nps")
    NPSSurveyQuestion nps;
    @XStreamAlias("surveyType")
    String surveyType;
    @XStreamAlias("termsURL")
    String termsURL;
    @XStreamAlias("policyURL")
    String policyURL;    

    public String getTermsURL() {
		return termsURL;
	}

	public void setTermsURL(String termsURL) {
		this.termsURL = termsURL;
	}

	public String getPolicyURL() {
		return policyURL;
	}

	public void setPolicyURL(String policyURL) {
		this.policyURL = policyURL;
	}

	public BuyerDetails getBuyerDetails() {
        return buyerDetails;
    }

    public void setBuyerDetails(BuyerDetails buyerDetails) {
        this.buyerDetails = buyerDetails;
    }

    public CSATSurveyQuestion getCsat() {
        return csat;
    }

    public void setCsat(CSATSurveyQuestion csat) {
        this.csat = csat;
    }

    public NPSSurveyQuestion getNps() {
        return nps;
    }

    public void setNps(NPSSurveyQuestion nps) {
        this.nps = nps;
    }

    public String getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(String surveyType) {
        this.surveyType = surveyType;
    }
}
