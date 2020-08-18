package com.servicelive.orderfulfillment.jbpm;

import java.io.Serializable;

/**
 * Object that wraps another object in order to represent a
 * variable that should not be persisted in the jBPM process.
 */
public class TransientVariable implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5138750295890899697L;
	private Serializable var;

    public TransientVariable(Serializable var) {
        this.var= var;
    }

    public Serializable getObject() {
        return var;
    }

    public void setObject(Serializable object) {
        this.var = object;
    }
}
