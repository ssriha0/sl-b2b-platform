<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd"><br>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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
<p>
Since most service orders require a home visit, to ensure continued highest quality we require that all of our Service Pro undergo a background check. Please note that <b>Administrators or dispatchers are not required to do a background check.</b>
</br><br>
  1.  Explain the process and gain approval from the Service Pro to submit their background check.
</br>
  2.  Click the Perform Background Check button to open the background check process form. Complete and submit.
</br>
  3.  Your team member's status will be automatically update when the background check results come back. Average turn around time for results is 3-5 days.
 </br>
  </p>

<p><b>Driving Record Requirement:</b> In states where required, the Motor Vehicle Report (MVR) consent forms must also be filled out
by each employee and forwarded to PlusOne Solutions via fax.The Motor Vehicle Report (MVR) form is required by the states in order
for PlusOne to order the driving history report.  Failure to submit the required form will result in your new search being reported
 to ServiceLive as Adverse Findings. <br><br> <a class="link1" href="javaScript:communitypopup()">Need Help?</a></br> </p>
<!-- NEW MODULE/ WIDGET-->
<s:form action="backgroundCheckAction" method="post" theme="simple"
validate="true">

<div class="darkGrayModuleHdr" align="center">
	Current Status: <b class="upperTextClass largeTextFont">${teamProfileDto.backgroundCheckStatus}</b>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<c:if test="${teamProfileDto.certificationDate!= null}">
		<b>|</b>Certification Date:<b class="largeTextFont"><fmt:formatDate value="${teamProfileDto.certificationDate}" pattern="MM/dd/yyyy" /></b>
	</c:if>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<c:if test="${teamProfileDto.recertificationDate!= null}">
		<b>|</b>Recertification Date: <b class="largeTextFont"><fmt:formatDate value="${teamProfileDto.recertificationDate}" pattern="MM/dd/yyyy" /></b>
	</c:if>
</div>
<div class="grayModuleContent mainWellContent clearfix" align="center"><br/>
    <!-- <p style="color:green"><b>Current Background Check Status:</b>&nbsp;${teamProfileDto.backgroundCheckStatus}</p> -->
    <input type="hidden" id="performCheckInd" name="performheckInd"/>
    <input type="hidden" id="teamProfileDtoAction" name="teamProfileDto.action" value=""/>
   	<div align="center" style="width: 100%;">
   	
	<table>
	<tbody><tr>
		<td style="width: 40%; border: 1px solid #CCC; text-align: center;">
			<p style="text-align: center; font-weight: bold; font-size: 11px; margin-top: 10px;">Perform Background Check Now</p>
			<c:choose>
				<c:when test="${7 == teamProfileDto.bgCheckStatus || 28 == teamProfileDto.bgCheckStatus }">
					<p style="text-align: left; padding-left: 10px; padding-right: 10px;">Click below button to start Background Check now.</p>
					   <s:a cssClass="myButton" cssStyle="color:black; margin-top: 90px;" action="backgroundCheckActiondoCheck" onclick="doBackgroundCheckcertify('Recertify','teamProfileDto');clickRequestOmniture('BackgroundCheck.PerformCheckNow');
					   enableBackgroundCheckFn('%{teamProfileDto.plusOneUrl}','%{teamProfileDto.originalResourceId}','%{teamProfileDto.encryptedPlusOneKey}') " >START BACKGROUND CHECK</s:a>
				</c:when>
				<c:otherwise>
					<!--      <s:submit onclick="setAction('Check','teamProfileDto');clickRequestOmniture('BackgroundCheck.PerformCheckNow'); enableBackgroundCheckFn('%{teamProfileDto.plusOneUrl}','%{teamProfileDto.resourceId}','%{teamProfileDto.encryptedPlusOneKey}')" action="backgroundCheckActiondoCheck"  type="image" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" cssStyle="background-image:url(%{#request['staticContextPath']}/images/btn/btn-perform-background-check.gif);width:170px;height:20px;" cssClass="btn20Bevel" ></s:submit>
					-->
					<s:if test="hasActionErrors() or hasFieldErrors()">
						<p style="text-align: left; padding-left: 10px; padding-right: 10px;">Click below button to start Background Check now.</p>
						    <s:a cssClass="myButton" cssStyle="color:black; margin-top: 90px;" action="backgroundCheckActiondoCheck" onclick="doBackgroundCheckcertify('Recertify','teamProfileDto');clickRequestOmniture('BackgroundCheck.PerformCheckNow');
						    enableBackgroundCheckFn('%{teamProfileDto.plusOneUrl}','%{teamProfileDto.originalResourceId}','%{teamProfileDto.encryptedPlusOneKey}') " >START BACKGROUND CHECK</s:a>
					</s:if><s:else>
						<p style="text-align: left; padding-left: 10px; padding-right: 10px;">Click below button to start Background Check now.</p>
						  	<s:a cssClass="myButton" cssStyle="color:#74745E; margin-top: 90px;background: linear-gradient(to bottom, #F8E09E 5%, #F8E09E 100%) repeat scroll 0 0 #F8E09E;pointer-events:none;"
						    action="backgroundCheckActiondoCheck" disabled="true" onclick="return false;" >START BACKGROUND CHECK</s:a>
					</s:else>
				</c:otherwise>
			</c:choose>
		</td>
		<td style="width: 10%; text-align: center; padding-top: 60px;">
    		<p style="text-decoration:underline;font-size: 12px;">
    			<b>OR</b>
    		</p>
		</td>
		<td style="width: 50%;text-align:center; border: 1px solid #CCC; padding-left: 10px; padding-bottom: 10px;">
			<p style="font-weight: bold; font-size: 11px; margin-top: 10px;">Perform Background Check Later</p>
    		<p style="text-align: left; padding-left: 0px;">Send Background Check link to Provider.</p>
		    <table cellpadding="0" cellspacing="0" style="text-align: left;">
				<tr>
				    <td width="261">
				    <c:choose>
						<c:when test="${fieldErrors['teamProfileDto.email'] == null}">
							<p class="paddingBtm">
						</c:when>
				   		<c:otherwise>
							<p class="errorBox" style="margin-bottom: 9px;">
						</c:otherwise>
				    </c:choose>
				        Provider's Primary E-mail Address<br/>
				         <s:textfield name="teamProfileDto.email" theme="simple" cssStyle="width: 231px;" cssClass="shadowBox grayText" disabled="%{STATUS_CLEAR == teamProfileDto.bgCheckStatus}" />
				    </td>
				    <!-- <td>
				    <c:choose>
				    <c:when test="${fieldErrors['teamProfileDto.confirmEmail'] == null}">
				           <p class="paddingBtm">
				       </c:when>
				       <c:otherwise>
				        <p class="errorBox">
				    </c:otherwise>
				    </c:choose>
				        Confirm Service Provider Email Address<br/>
				     <s:textfield name="teamProfileDto.confirmEmail" theme="simple" cssStyle="width: 231px;" cssClass="shadowBox grayText" disabled="%{STATUS_CLEAR == teamProfileDto.bgCheckStatus}" />
				    </td> -->
				</tr>
				<tr>
				    <td>
				    <c:choose>
				    <c:when test="${fieldErrors['teamProfileDto.emailAlt'] == null}">
				           <p class="paddingBtm" style="width:300px;">
				       </c:when>
				       <c:otherwise>
				        <p class="errorBox" style="margin-bottom: 9px;">
				    </c:otherwise>
				    </c:choose>
				        Provider's Alternate E-mail Address ( Optional )<br />
				         <s:textfield name="teamProfileDto.emailAlt" theme="simple" cssStyle="width: 231px;" cssClass="shadowBox grayText" disabled="%{STATUS_CLEAR == teamProfileDto.bgCheckStatus}" />
				    </td>
					<!-- <td>
				    <c:choose>
				    <c:when test="${fieldErrors['teamProfileDto.confirmEmailAlt'] == null}">
				           <p class="paddingBtm">
				       </c:when>
				       <c:otherwise>
				        <p class="errorBox">
				    </c:otherwise>
				    </c:choose>
				        Confirm Service Provider Alt. Email Address<br />
				         <s:textfield name="teamProfileDto.confirmEmailAlt" theme="simple" cssStyle="width: 231px;" cssClass="shadowBox grayText" disabled="%{STATUS_CLEAR == teamProfileDto.bgCheckStatus}" />
				    </td>-->
				</tr>
			</table>
                
			<c:choose>
			<c:when test="${7 == teamProfileDto.bgCheckStatus || 28 == teamProfileDto.bgCheckStatus }">
				<s:submit value="SEND E-MAIL TO PROVIDER" onclick="setAction('Save','teamProfileDto');clickRequestOmniture('BackgroundCheck.SendRequest');" cssClass="myButton" action="backgroundCheckActiondoSave" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" ></s:submit>
			</c:when>
			<c:otherwise>
			 	<s:if test="hasActionErrors() or hasFieldErrors()">
			 		<s:submit value="SEND E-MAIL TO PROVIDER" onclick="setAction('Save','teamProfileDto');clickRequestOmniture('BackgroundCheck.SendRequest');" cssClass="myButton" action="backgroundCheckActiondoSave" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" ></s:submit>
				</s:if>
			<s:else>
				<s:submit value="SEND E-MAIL TO PROVIDER" cssStyle="pointer-events:none;" disabled="true" onclick="return false;" cssClass="myButton" action="backgroundCheckActiondoSave" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" ></s:submit>
			</s:else>
			</c:otherwise>
			</c:choose>
		</td>
	</tr></tbody>
	</table>
</div>
               
            <br/>
<div class="clear"></div>
</div>
<div class="clearfix">
<div class="formNavButtons" align="left">
    <s:submit action="backgroundCheckActiondoPrev" type="image" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif"    cssStyle="background-image:url(%{#request['staticContextPath']}/images/btn/previous.gif);width:76px;height:20px;" cssClass="btn20Bevel" onClick="setAction('Previous','teamProfileDto');"></s:submit>
    <s:submit action="backgroundCheckActiondoNext" type="image" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif"    cssStyle="background-image:url(%{#request['staticContextPath']}/images/btn/next.gif);width:49px;height:20px;" cssClass="btn20Bevel" onClick="setAction('Next','teamProfileDto');"></s:submit>
    </div>
    <br/>
    </div>
    <div class="formNavButtons" style="margin-top:20px;position:absolute;" >
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
           

        </s:form>
       
       
        <br/>

    </body>
</html>