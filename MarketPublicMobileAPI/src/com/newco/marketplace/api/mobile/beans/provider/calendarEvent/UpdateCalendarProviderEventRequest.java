package com.newco.marketplace.api.mobile.beans.provider.calendarEvent;

import javax.xml.bind.annotation.XmlRootElement;

import com.newco.marketplace.api.annotation.XSD;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XSD(name = "updateCalendarProviderEventRequest.xsd", path = "/resources/schemas/mobile/")
@XmlRootElement(name = "updateCalendarProviderEventRequest")
@XStreamAlias("updateCalendarProviderEventRequest")
public class UpdateCalendarProviderEventRequest {



		
		@XStreamAlias("providerCalendarEventDetails")
		private ProviderCalendarEventDetails providerCalendarEventDetails;
		
		
		/**
		 * @return the providerCalendarEventDetails
		 */
		public ProviderCalendarEventDetails getProviderCalendarEventDetails() {
			return providerCalendarEventDetails;
		}

		/**
		 * @param providerCalendarEventDetails the providerCalendarEventDetails to set
		 */
		public void setProviderCalendarEventDetails(
				ProviderCalendarEventDetails providerCalendarEventDetails) {
			this.providerCalendarEventDetails = providerCalendarEventDetails;
		}

		
		
	}

