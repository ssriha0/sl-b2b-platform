<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:form  name="submitRegAction" theme="simple">
  	<c:choose>
  	<c:when test="${sessionScope.userStatus=='editUser'}">
    	 	<c:if test="${sessionScope.resourceStatus == 'complete' and sessionScope.providerStatus == 'complete'}">
    			<input onclick="javascript:submitRegistration('<s:url action="regDashboard" includeParams="none" method="submitRegistration"/>')" type="image" src="${staticContextPath}/images/images_registration/common/spacer.gif" width="100" height="20" 
     			style="background-image:url(${staticContextPath}/images/btn/updateProfile.gif)"/>
      		</c:if>
      		
 	 </c:when>
   	 <c:otherwise>
   			<c:if test="${sessionScope.resourceStatus == 'complete' and sessionScope.providerStatus == 'complete'}">
     			<input type="image" src="${staticContextPath}/images/images_registration/common/spacer.gif" width="127" height="20" 
     			style="background-image:url(${staticContextPath}/images/images_registration/btn/submitRegistration.gif)"  
     			title="Please Complete Your Registration to Submit"  
     			onclick="javascript:submitRegistration('<s:url action="regDashboard" includeParams="none" method="submitRegistration"/>')"/>
 	 		</c:if>
   			
    </c:otherwise>
    </c:choose>
    </s:form>
  
