package com.newco.marketplace.api.beans.so;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing information for a list of routed providers.
 * @author Infosys
 *
 */
@XStreamAlias("routedProviders")
public class RoutedProviders {
	
	@XStreamImplicit(itemFieldName="routedProvider")
	private List<RoutedProvider> routedProviders;

	public List<RoutedProvider> getRoutedProviders() {
		return routedProviders;
	}

	public void setRoutedProviders(List<RoutedProvider> routedProviders) {
		this.routedProviders = routedProviders;
	}

	
	
}
