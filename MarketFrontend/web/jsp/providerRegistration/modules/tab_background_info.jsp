<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd"><br>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>

<c:set var="TEAM_BACKGROUND_PENDING_SUBMISSION" value="<%= new Integer(OrderConstants.TEAM_BACKGROUND_PENDING_SUBMISSION)%>" />
<c:set var="STATUS_NOT_STARTED" value="<%=new Integer(OrderConstants.TEAM_BACKGROUND_NOT_STARTED) %>" />
<c:set var="STATUS_CLEAR" value="<%=new Integer(OrderConstants.TEAM_BACKGROUND_CLEAR) %>" />
<c:set var="STATUS_IN_PROCESS" value="<%=new Integer(OrderConstants.TEAM_BACKGROUND_IN_PROCESS) %>" />
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", -1);
%>

<html>
<head>

		<style type="text/css">
@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css"
	;

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css"
	;
</style>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${contextPath}/dijitTabPane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/slider.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/service_provider_profile.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/admin.css" />
		<script language="JavaScript"
			src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>

		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
			</head>
			<body>
		<jsp:include page="/jsp/public/common/omitInclude.jsp" />


<div id="content_right_header_text">
   	<%@ include file="../message.jsp"%>
</div>
<h3 class="paddingBtm">
<s:property value="teamProfileDto.firstName"/> <s:property value=" "/> <s:property value="teamProfileDto.lastName"/>
(User Id# <s:property value="teamProfileDto.resourceId"/>)
</h3>

<p>Our records indicate that there is already a background check verification on-file with us. If this is correct, please click <b>Next</b> to confirm and continue. If you need further assistance, please call ServiceLive Compliance Team at 1-888-549-0640 and select option 2.</p>
<p> <br> <a class="link1" href="javaScript:communitypopup()">Need Help?</a></br>

	<div class="darkGrayModuleHdr" align="center">
		Current Status: <b class="upperTextClass largeTextFont">${teamProfileDto.backgroundCheckStatus}</b>
		<c:if test="${teamProfileDto.certificationDate!= null}">
			<b>&nbsp;|&nbsp;</b> Certification Date:<b class="largeTextFont"><fmt:formatDate value="${teamProfileDto.certificationDate}"	pattern="MM/dd/yyyy" /></b>
		</c:if>
	 	&nbsp;  
	    <c:if test="${teamProfileDto.recertificationDate!= null}">
	 		<b>|</b> Recertification Date: <b class="largeTextFont"><fmt:formatDate value="${teamProfileDto.recertificationDate}" pattern="MM/dd/yyyy" /></b>
	 	</c:if>
	</div>
</p>
<!-- NEW MODULE/ WIDGET-->
<s:form action="backgroundCheckAction" method="post" theme="simple"
validate="true">
	<!-- <p style="color:green"><b>Current Background Check Status:</b>&nbsp;${teamProfileDto.backgroundCheckStatus}</p> -->
	<input type="hidden" id="performCheckInd" name="performheckInd"/>
	<input type="hidden" id="teamProfileDtoAction" name="teamProfileDto.action" value=""/>
	
	
<div class="clearfix"><div class="formNavButtons" align="left">
	<s:submit action="backgroundCheckActiondoPrev" type="image" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif"	cssStyle="background-image:url(%{#request['staticContextPath']}/images/btn/previous.gif);width:76px;height:20px;" cssClass="btn20Bevel" onClick="setAction('Previous','teamProfileDto');"></s:submit>
	<s:submit action="backgroundCheckActiondoNext" type="image" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif"	cssStyle="background-image:url(%{#request['staticContextPath']}/images/btn/next.gif);width:49px;height:20px;" cssClass="btn20Bevel" onClick="setAction('ShareNext','teamProfileDto');"></s:submit>
	</div>
	<br/>
	<div class="formNavButtons">
		<c:choose>
	  	<c:when test="${sessionScope.userStatus=='editUser'}">
    	 		<c:if test="${sessionScope.resourceStatus == 'complete' and sessionScope.providerStatus == 'complete'}">
    			<s:submit action="backgroundCheckActionupdateProfile" type="image"
    			src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif"
     			cssStyle="background-image:url(%{#request['staticContextPath']}/images/btn/updateProfile.gif);width:120px;height:20px;"
				cssClass="btn20Bevel" onClick="setAction('Update','teamProfileDto');">
				</s:submit>
      			</c:if>
      			</c:when>
      			<c:otherwise>
      			<c:if test="${sessionScope.resourceStatus == 'complete' and sessionScope.providerStatus == 'complete'}">
      			<s:submit action="backgroundCheckActionupdateProfile" type="image"
    			src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif"
     			cssStyle="background-image:url(%{#request['staticContextPath']}/images/btn/submitRegistration.gif);width:120px;height:20px;"
				cssClass="btn20Bevel" onClick="setAction('Update','teamProfileDto');">
				</s:submit>
      			</c:if>
      			</c:otherwise>
      	</c:choose>
			</div>
			</div>

		</s:form>
		<br/>

	</body>
</html>
