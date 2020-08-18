package com.newco.marketplace.westsurvey.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This interface defines the class level annotations which is used for the WestSurveyVO Bean.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelAlias {
    public String value();
    public Class<?> impl() default Void.class;
}
