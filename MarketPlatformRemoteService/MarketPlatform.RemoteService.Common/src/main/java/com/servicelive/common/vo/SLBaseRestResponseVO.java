package com.servicelive.common.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public abstract class SLBaseRestResponseVO implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1509453694666991872L;
	private List<String> errors = new ArrayList<String>();

    @XmlElementWrapper(name = "errors")
    @XmlElement(name = "error")
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
    public List<String> getErrors() {
        return errors;
    }
    public SLBaseRestResponseVO addError(String error) {
        errors.add(error);
        return this;
    }
    public SLBaseRestResponseVO addErrors( List<String> errors) {
        errors.addAll(errors);
        return this;
    }
    public boolean isError() {
        return !errors.isEmpty();
    }

    public String getErrorMessage() {
        StringBuffer sb = new StringBuffer();
        for(String s : errors){
            sb.append(s).append("\t");
        }
        return sb.toString();
    }

    public static <T> T getInstance() {
        // not implemented
        throw new RuntimeException ("SLBaseRestResponseVO.getInstance() must be overridden.");
    }
}
