package com.newco.batch.calendar;

import java.util.List;

import com.newco.marketplace.exception.BusinessServiceException;

public interface ExternalCalendarEventMapper<T, V> {
	
	public List<T> mapToExternalEvents(List<V> slEventList) throws BusinessServiceException;
	public List<T> mapToSLEvents(List<V> externalEventList) throws BusinessServiceException;;

}
