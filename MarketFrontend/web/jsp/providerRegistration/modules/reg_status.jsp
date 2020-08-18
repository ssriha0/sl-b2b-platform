<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>
<s:form  name="regDashboardAction" theme="simple">
<div class="regStatus">

    <tags:security actionName="allTabView" > 
  <div class="clearfix">  
    <div class="profileType"><a href="<s:url action="allTabView.action" includeParams="none"/>">Provider Firm Profile</a></div>
    <div class="statusIcon">
     <c:choose>
     <c:when test="${providerStatus == 'complete'}">
     	<img src="${staticContextPath}/images/images_registration/icons/completeIcon.gif" width="12" height="12" alt="">
     </c:when>
     <c:otherwise>
     	<img src="${staticContextPath}/images/images_registration/icons/incIcon.gif" width="12" height="12" alt="">
     </c:otherwise>
     </c:choose>
     </div>
  </div>
  <div class="divider"></div>
</tags:security>

    <tags:security actionName="addServiceProAction" > 
  <div class="clearfix">
    <div class="profileType"><a href="<s:url action="addServiceProAction" includeParams="none"/>">Service Pro Profile</a></div>
    <div class="statusIcon">
    <c:choose>
    <c:when test="${resourceStatus == 'complete'}">
     	<img src="${staticContextPath}/images/images_registration/icons/completeIcon.gif" width="12" height="12" alt="">
     </c:when>
     <c:otherwise>
     	<img src="${staticContextPath}/images/images_registration/icons/incIcon.gif" width="12" height="12" alt="">
     </c:otherwise>
     </c:choose>    
    </div>
  </div>
  <div class="divider"></div>
  </tags:security>
  
  <p align="center">
  	<c:choose>
  	<c:when test="${sessionScope.userStatus=='editUser'}">
    	 	<c:choose>
    	 	<c:when test="${sessionScope.resourceStatus == 'complete' and sessionScope.providerStatus == 'complete'}">
    			<input onclick="javascript:submitDashBoard('<s:url action="regDashboard" includeParams="none" method="submitRegistration"/>')" type="image" src="${staticContextPath}/images/images_registration/common/spacer.gif" width="90" height="20" 
     			style="background-image:url(${staticContextPath}/images/btn/updateProfile.gif)"/>
      		</c:when>
      		<c:otherwise>
      			<input type="image" src="${staticContextPath}/images/images_registration/common/spacer.gif" width="90" height="20" 
     			style="background-image:url(${staticContextPath}/images/btn/updateProfileOff.gif)" disabled="disabled" class="disabledBtn"/>
      		</c:otherwise>
      		</c:choose>
 	 </c:when>
   	 <c:otherwise>
   	 		<c:choose>
   			<c:when test="${sessionScope.resourceStatus == 'complete' and sessionScope.providerStatus == 'complete'}">
     			<input type="image" src="${staticContextPath}/images/images_registration/common/spacer.gif" width="127" height="20" 
     			style="background-image:url(${staticContextPath}/images/images_registration/btn/submitRegistration.gif)"  
     			title="Please Complete Your Registration to Submit"  
     			onclick="javascript:submitDashBoard('<s:url action="regDashboard" includeParams="none" method="submitRegistration"/>')"/>
 	 		</c:when>
   			<c:otherwise>
 		  		<input type="image" src="${staticContextPath}/images/images_registration/common/spacer.gif" width="127" height="20" style="background-image:url(${staticContextPath}/images/images_registration/btn/submitRegistrationOff.gif)" disabled="disabled" title="Please Complete Your Registration to Submit" class="disabledBtn" />
    		</c:otherwise>
    		</c:choose>
    </c:otherwise>
    </c:choose>
    
  </p>
</div>
 </s:form> 
