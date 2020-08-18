package com.newco.marketplace.api.beans.survey;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by kjain on 1/2/2019.
 */
@XStreamAlias("nps")
public class NPSSurveyQuestion {
    @XStreamAlias("question")
    String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
