package com.newco.marketplace.email;

import java.io.Serializable;
import java.util.Map;

public interface EmailService extends Serializable{
    
    boolean send(String eid, Map<String, String> parameters);
    public void addEmailProperty(String key , String value);

}
