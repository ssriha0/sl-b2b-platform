package com.newco.marketplace.api.annotation;

import java.lang.annotation.*;

/**
 *
 * This annotation should be used in Service classes to mark Response class.
 * The Response class must use XSD annotation to annotate XSD name and path.
 *
 * @author Shekhar Nirkhe
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) 
public @interface APIResponseClass {
	Class value(); 
}
