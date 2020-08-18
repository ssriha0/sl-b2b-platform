<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", -1);
%>

    	<h2>${ProviderInfo.companyPublicInfo.businessName} 
		<h3>&nbsp;</h3>
		<h3>ServiceLive Status: ${ProviderInfo.companyPublicInfo.serviceLiveStatus}</h3>
		<h3>&nbsp;</h3>
		
		<h3>${ProviderInfo.companyPublicInfo.firstName} &nbsp; ${ProviderInfo.companyPublicInfo.lastName}
		     <c:if test="${ProviderInfo.companyPublicInfo.title != null and not empty ProviderInfo.companyPublicInfo.title }"> 
		              &nbsp; (${ProviderInfo.companyPublicInfo.title})
		     </c:if>          
		</h3>
		<c:if test="${ProviderInfo.isSecuredInfoViewable != null and ProviderInfo.isSecuredInfoViewable == true }"> 
			<tags:security actionName="ViewMemberManager">
				<h3>${ProviderInfo.companyPublicInfo.busStreet1} </h3>
				<h3>${ProviderInfo.companyPublicInfo.busStreet2}</h3>
			</tags:security>
		</c:if>
		<h3>${ProviderInfo.companyPublicInfo.busCity}, ${ProviderInfo.companyPublicInfo.busStateCd}
		           ${ProviderInfo.companyPublicInfo.busZip}</h3>
		<c:if test="${ProviderInfo.isSecuredInfoViewable != null and ProviderInfo.isSecuredInfoViewable == true }"> 
			 <tags:security actionName="ViewMemberManager">
				<h3>
					Phone:
					<!-- format phone # as (555)123-4567 -->
					<c:choose><c:when test="${fn:length(ProviderInfo.companyPublicInfo.businessPhone) == 10}">
						(
						${fn:substring(ProviderInfo.companyPublicInfo.businessPhone, 0, 3)}
						)
						${fn:substring(ProviderInfo.companyPublicInfo.businessPhone, 3, 6)}
						-
						${fn:substring(ProviderInfo.companyPublicInfo.businessPhone, 6, 10)}
					</c:when>
					<c:otherwise>
						 ${ProviderInfo.companyPublicInfo.businessPhone}
					</c:otherwise></c:choose>
				</h3>  
				<h3>Email: ${ProviderInfo.companyPublicInfo.email}</h3>   
			</tags:security>
		</c:if>