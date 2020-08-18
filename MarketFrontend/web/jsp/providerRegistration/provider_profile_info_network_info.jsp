<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!-- provider_profile_info_network_info.jsp -->
<div id="fragment-spn" style="display: none">

	<form id="networkInfoForm" name="networkInfoForm">
		<input type="hidden" id="vendorId" name="vendorId"
			value="${ProviderInfo.providerPublicInfo.vendorId}" />
			
		<input type="hidden" id="vendorResourceId" name="vendorResourceId"
			value="${ProviderInfo.providerPublicInfo.resourceId}" />
		<c:choose>
		<c:when test="${ProviderInfo.providerPublicInfo.firstName != null}">
			<input type="hidden" id="firstName" name="firstName"
				value="${ProviderInfo.providerPublicInfo.firstName}" />
		</c:when>
		<c:when test="${ProviderInfo.companyPublicInfo.firstName != null}">
			<input type="hidden" id="firstName" name="firstName"
				value="${ProviderInfo.companyPublicInfo.firstName}" />		
		</c:when>
		<c:otherwise>
			<input type="hidden" id="firstName" name="firstName"
				value="[Unknown firstName]" />		
		</c:otherwise>
		</c:choose>
		<c:choose>
		<c:when test="${ProviderInfo.providerPublicInfo.lastName != null}">			
			<input type="hidden" id="lastName" name="lastName"
				value="${ProviderInfo.providerPublicInfo.lastName}" />
		</c:when>
		<c:when test="${ProviderInfo.companyPublicInfo.lastName != null}">			
			<input type="hidden" id="lastName" name="lastName"
				value="${ProviderInfo.companyPublicInfo.lastName}" />
		</c:when>
		<c:otherwise>
			<input type="hidden" id="lastName" name="lastName"
				value="[Unknown lastName]" />		
		</c:otherwise>
		</c:choose>	
		<input type="hidden" id="sharedSecret" name="sharedSecret"
			value="${ProviderInfo.sharedSecretString}" />

		<div id="networkInfoDiv" name="networkInfoDiv">
		</div>
	</form>





</div>