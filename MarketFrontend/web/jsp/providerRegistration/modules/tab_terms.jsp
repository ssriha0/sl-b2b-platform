<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

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
	href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
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
	href="${staticContextPath}/css/registration.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/buttons.css" />
<script language="JavaScript"
	src="${staticContextPath}/javascript/js_registration/tooltip.js"
	type="text/javascript"></script>

<s:form action="termsAction" theme="simple">
<div id="content_right_header_text">
		<%@ include file="../message.jsp"%>
	</div>
<h3 class="paddingBtm">
<s:property value="termsDto.fullResoueceName"/>
(User Id# <s:property value="termsDto.resourceId"/>)
</h3>
	<p class="paddingBtm">
		You must agree to the following terms and conditions in order to
		complete your registration. Provider firms and service providers who fail
		to abide by these terms and conditions will be removed from the
		ServiceLive network.
	</p>
	<p><b>An asterix (<span class="req">*</span>) indicates a required field</b></p> <br/>
	<!-- NEW MODULE/ WIDGET-->
	
	<div class="darkGrayModuleHdr">
		Service Provider Terms & Conditions
	</div>
	<div class="grayModuleContent mainContentWell">
		<div class="inputArea" style="height: 200px;">
			<h3 align="center">
				Service Provider Terms & Conditions
			</h3>
			<p>
				<s:property value="termsDto.termsContent" escape="false" />
			</p>
		</div>
			
		<p>
			<s:radio name="termsDto.acceptTerms" value="%{termsDto.acceptTerms}"
				theme="simple" cssClass="antiRadioOffsets" list="#{1:'Yes'}"/>
			I accept the Terms & Conditions.<span class="req">*</span>
		</p>
		
		<p>
			<s:if test="%{termsDto.acceptTerms == 1}">
			<s:radio name="termsDto.acceptTerms" value="%{termsDto.acceptTerms}" theme="simple"
				cssClass="antiRadioOffsets" list="#{-1:'No'}" disabled="true"/>
			I do not accept the Terms & Conditions.<span class="req">*</span>
			</s:if>
			<s:else>
			<s:radio name="termsDto.acceptTerms" value="%{termsDto.acceptTerms}" theme="simple"
				cssClass="antiRadioOffsets" list="#{-1:'No'}"/>
			I do not accept the Terms & Conditions.<span class="req">*</span>
			</s:else>
		</p>
		
	</div>
	<div class="clearfix">

		<s:submit action="termsActiondoprevious" type="image" id="previousID"
			src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif"
			cssStyle="background-image:url(%{#request['staticContextPath']}/images/btn/previous.gif);width:126px;height:20px;"
			cssClass="btn20Bevel" onclick="setAction('Previous','termsDto');">
		</s:submit>

		<s:submit action="termsActiondoSave" type="image" id="SaveID"
			src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif"
			cssStyle="background-image:url(%{#request['staticContextPath']}/images/btn/save.gif);width:126px;height:20px;"
			cssClass="btn20Bevel" onclick="setAction('Save','termsDto');">
		</s:submit>
	</div>
	<br/>
	<div class="formNavButtons">
	<c:choose>
  	<c:when test="${sessionScope.userStatus=='editUser'}">
    	 		<c:if test="${sessionScope.resourceStatus == 'complete' and sessionScope.providerStatus == 'complete'}">
    			<s:submit action="termsActionupdateProfile" type="image"   
    			src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" 
     			cssStyle="background-image:url(%{#request['staticContextPath']}/images/btn/updateProfile.gif);width:120px;height:20px;"
				cssClass="btn20Bevel" onclick="setAction('Update','termsDto');">
				</s:submit>
      			</c:if>
      			</c:when>
      			<c:otherwise>
      			<c:if test="${sessionScope.resourceStatus == 'complete' and sessionScope.providerStatus == 'complete'}">
      			<s:submit action="termsActionupdateProfile" type="image"   
    			src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" 
     			cssStyle="background-image:url(%{#request['staticContextPath']}/images/btn/submitRegistration.gif);width:120px;height:20px;"
				cssClass="btn20Bevel" onclick="setAction('Update','termsDto');">
				</s:submit>
      			</c:if>
      			</c:otherwise>
      </c:choose>
	</div>
		
	<div class="bottomRightLink">
		<a
			href="javascript:cancelProTerms('jsp/providerRegistration/termsActiondoCancel.action');">Cancel</a>
	</div>
	<input type="hidden" id="termsDtoAction" name="termsDto.action" value="" />
</s:form>
<br />
