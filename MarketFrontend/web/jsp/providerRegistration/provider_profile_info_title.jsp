<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="isSecurityViewAvailable" value="no" scope="request" />

<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", -1);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
      <h2>${ProviderInfo.providerPublicInfo.firstName} 
         <tags:security actionName="ViewMemberManager">
       		<c:set var="isSecurityViewAvailable" value="yes" />
        </tags:security>
      
       
      <c:choose >
		<c:when test="${not empty ProviderInfo.providerPublicInfo.lastName}">
		  <c:set var="lastNameInfo" value="${fn:substring(ProviderInfo.providerPublicInfo.lastName,0,1)}" />
		  <c:if test="${isSecurityViewAvailable=='yes'}" >
		    <c:set var="lastNameInfo" value="${ProviderInfo.providerPublicInfo.lastName}" />
		  </c:if>
		   <c:out value="${lastNameInfo}"/>.
		</c:when>
	 </c:choose>
	  <span>&nbsp;ID#${ProviderInfo.providerPublicInfo.resourceId}</span></h2>
	  <h3>&nbsp;</h3>
	  <h3>ServiceLive Status: ${ProviderInfo.providerPublicInfo.slStatus}</h3>
	  <h3>&nbsp;</h3>
	
	  <c:if test="${ProviderInfo.isSecuredInfoViewable != null and ProviderInfo.isSecuredInfoViewable == true }"> 
		  <tags:security actionName="ViewMemberManager">
			 <h3>${ProviderInfo.providerPublicInfo.dispAddStreet1} &nbsp; ${ProviderInfo.providerPublicInfo.dispAddApt}</h3>
			 <h3>${ProviderInfo.providerPublicInfo.dispAddStreet2}</h3>
		  </tags:security>
	  </c:if>   
	  <h3>${ProviderInfo.providerPublicInfo.dispAddCity}, ${ProviderInfo.providerPublicInfo.dispAddState}
	  	<c:if test="${ProviderInfo.isSecuredInfoViewable != null and ProviderInfo.isSecuredInfoViewable == true }"> 
		  <tags:security actionName="ViewMemberManager"> 
		           ${ProviderInfo.providerPublicInfo.dispAddZip}
		   </tags:security>
		</c:if>           
	  </h3>    
	  <c:if test="${ProviderInfo.isSecuredInfoViewable != null and ProviderInfo.isSecuredInfoViewable == true }"> 
		  <!--<tags:security actionName="ViewMemberManager">     -->      
			 <h3>Phone: 
			 	
				<!-- format phone # as (555)123-4567 -->
				<c:choose><c:when test="${fn:length(ProviderInfo.providerPublicInfo.phoneNumber) == 10}">
					(${fn:substring(ProviderInfo.providerPublicInfo.phoneNumber, 0, 3)})
					${fn:substring(ProviderInfo.providerPublicInfo.phoneNumber, 3, 6)}-${fn:substring(ProviderInfo.providerPublicInfo.phoneNumber, 6, 10)}
				</c:when>
				<c:otherwise>
					 ${ProviderInfo.providerPublicInfo.phoneNumber}
				</c:otherwise>			 	
			 	 </c:choose>
			     <c:if test="${ProviderInfo.providerPublicInfo.phoneNumberExt != null and not empty ProviderInfo.providerPublicInfo.phoneNumberExt }">  
			             / &nbsp; Ext. ${ProviderInfo.providerPublicInfo.phoneNumberExt}
	             </c:if> 
			    <c:if test="${ProviderInfo.providerPublicInfo.alternatePhone != null and not empty ProviderInfo.providerPublicInfo.alternatePhone }">  
			             / &nbsp; Alternate: 
			             <c:choose><c:when test="${fn:length(ProviderInfo.providerPublicInfo.alternatePhone) == 10}">
			             	(${fn:substring(ProviderInfo.providerPublicInfo.alternatePhone,0,3)})
			             	${fn:substring(ProviderInfo.providerPublicInfo.alternatePhone,3,6)}-${fn:substring(ProviderInfo.providerPublicInfo.alternatePhone,6,10)}
			             </c:when>
			             <c:otherwise>
			             	${ProviderInfo.providerPublicInfo.alternatePhone}
			             </c:otherwise></c:choose>
			             
			             
			             
	             </c:if>    
	         </h3>    
	         <h3>Email: ${ProviderInfo.providerPublicInfo.email}</h3> 
		  <!--</tags:security>    -->
	  </c:if> 
	    <h3>&nbsp;</h3>
	  	  <h3>ServiceLive Rating: &nbsp;
	  	  					<c:choose>
				        		<c:when test="${fn:substring(ProviderInfo.providerPublicInfo.historicRating, 0, 1) != 0}">
				        			<img src="${staticContextPath}/images/common/stars_<c:out value="${ProviderInfo.providerPublicInfo.ratingNumber}"/>.gif" border="0" />
				        			<br>
				        			<div style="text-align:center">${fn:substring(ProviderInfo.providerPublicInfo.historicRating, 0, 5)}/5</div>
				        		</c:when>
				        		<c:otherwise>
				        			<img src="${staticContextPath}/images/common/stars_notRated.gif" border="0" />
				        		</c:otherwise>
				        	</c:choose>
				        	</h3>                          
  </body>
</html>
