package com.newco.marketplace.web.security;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * 
 * @author SGOURIS
 * @description Annotation that specifies whether a page should be
 * non-secured. Since most of the pages are supposed to be secure
 * on the site, we can annotate the exceptions as @NonSecurePage
 * 
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NonSecurePage {
	
}
