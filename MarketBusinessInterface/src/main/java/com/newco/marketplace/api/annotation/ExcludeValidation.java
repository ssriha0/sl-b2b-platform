package com.newco.marketplace.api.annotation;

import java.lang.annotation.*;

/**
 * This annotation should be used in Service classe to exclude validation.
 * 
 * @author Shekhar Nirkhe
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) 
public @interface ExcludeValidation {

}
