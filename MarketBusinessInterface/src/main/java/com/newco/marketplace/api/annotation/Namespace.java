package com.newco.marketplace.api.annotation;

import java.lang.annotation.*;

/**
 * This annotation should be used in Service classes to mark name space.
 * The Name space must match with the name space defined in XSD.
 * 
 * @author Shekhar Nirkhe
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) 
public @interface Namespace {
	String value(); 
}
