package com.servicelive.orderfulfillment.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Yunus Burhani
 * Date: Sep 23, 2010
 * Time: 1:23:14 PM
 */
public class ServiceOrderValidationException extends ServiceOrderException{

    private List<String> validationErrors = new ArrayList<String>();

    public ServiceOrderValidationException(List<String> validationErrors){
        super("Validation errors occurred.");
        this.validationErrors = validationErrors;
    }

    public void addValidationError(String validError){
        validationErrors.add(validError);
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public String getValidationErrorString(){
        StringBuffer sb = new StringBuffer();
        for(String s : validationErrors){
            sb.append(s).append(" \n ");
        }
        return sb.toString();
    }
}
