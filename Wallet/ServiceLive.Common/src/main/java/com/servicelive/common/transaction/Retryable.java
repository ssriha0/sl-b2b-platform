package com.servicelive.common.transaction;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Retryable {
	public static final boolean DEFAULT_UNLIMITED_RETRIES_ALLOWED = false;
	public static final int DEFAULT_RETRY_INTERVAL = 1000;
	public static final int DEFAULT_MAXIMUM_RETRY_ATTEMPTS = 5;
	boolean unlimitedRetriesAllowed() default DEFAULT_UNLIMITED_RETRIES_ALLOWED;
	int maximumRetryAttempts() default DEFAULT_MAXIMUM_RETRY_ATTEMPTS;
	int retryInterval() default DEFAULT_RETRY_INTERVAL;
	Class<? extends Throwable>[] retryExceptions();
}
