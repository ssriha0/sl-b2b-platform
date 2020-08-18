package com.newco.marketplace.api.beans.survey;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kjain on 1/2/2019.
 */
@XStreamAlias("csat")
public class CSATSurveyQuestion {
    @XStreamAlias("questions")
    CsatQuestion questions;
    @XStreamAlias("options")
    ArrayList<CsatOption> options; 	
    
	public CsatQuestion getQuestions() {
		return questions;
	}

	public void setQuestions(CsatQuestion questions) {
		this.questions = questions;
	}

	public ArrayList<CsatOption> getOptions() {
		return options;
	}

	public void setOptions(ArrayList<CsatOption> options) {
		this.options = options;
	}
   
}
