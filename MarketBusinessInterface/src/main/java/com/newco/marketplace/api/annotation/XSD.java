package com.newco.marketplace.api.annotation;

import java.lang.annotation.*;

/**
 * This annotation should be used in Request and Response classes to mark XSD name path path.
 * 
 * @author Shekhar Nirkhe
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) 
public @interface XSD {
	String name(); 
	 String path(); 
}
