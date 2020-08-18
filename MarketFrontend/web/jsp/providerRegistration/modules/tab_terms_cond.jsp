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
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/acquity.css" />
<script language="JavaScript"
	src="${staticContextPath}/javascript/js_registration/tooltip.js"
	type="text/javascript"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>


<s:form action="termsAndCondAction" theme="simple" method="post" validate="true" name="termsAndCondForm" id="termsAndCondForm">
 	<s:hidden name="termsFlag" value="%{termsFlag}" id="termsFlag"/>
	<p class="paddingBtm">
		You must agree to the following terms and conditions in order to
		complete your registration. Provider firms and service providers who fail
		to abide by these terms and conditions will be removed from the
		ServiceLive network.
	</p>

	<div id="content_right_header_text">
		<%@ include file="../message.jsp"%>
	</div>
	<p><b>An asterix (<span class="req">*</span>) indicates a required field</b></p> <br/>
	<div class="darkGrayModuleHdr">
		Provider Firm Terms & Conditions
	</div>
	<div class="grayModuleContent mainContentWell">
		<div class="inputArea" style="height: 200px;">
			<h3 align="center">
				Provider Firm Terms & Conditions
			</h3>
			<p>
				<s:property value="termsAndCondDto.termsContent" escape="false" />
			</p>
		</div>
		<div>
						
			<dl class="clearfix">			
				
							
				<dt style="float: left;">			
				<s:checkbox name="termsAndCondDto.acceptTerms" id="termsAndCondDto.acceptTerms"  value="%{termsAndCondDto.acceptTerms}"
				disabled="%{termsAndCondDto.acceptTerms}"
				 />
				</dt>
				<dd><label>&nbsp;I accept the terms of the above Service Provider Agreement<span class="req">*</span></label></dd>
				
				
			</dl> 
			<dl class="clearfix">		
				
					<dt style="float: left;">								
						<s:checkbox name="termsAndCondDto.acceptBucksTerms" id="termsAndCondDto.acceptBucksTerms"  value="%{termsAndCondDto.acceptBucksTerms}"
						disabled="%{termsAndCondDto.acceptBucksTerms}"
							/>
					</dt>
					<dd><label>&nbsp;I agree to the <a href="javascript: openBucks();" >ServiceLive Bucks Agreement</a><span class="req">*</span></label></dd>
						
				
			</dl>
			<!-- SLT-2236 -->
			<dl class="clearfix">		
				
					<dt style="float: left;">								
						<s:checkbox name="termsAndCondDto.acceptNewBucksTerms" id="termsAndCondDto.acceptNewBucksTerms"  value="%{termsAndCondDto.acceptNewBucksTerms}"
						disabled="%{termsAndCondDto.acceptNewBucksTerms}"
							/>
					</dt>
					<dd><label>&nbsp;I agree to the ServiceLive deducting amounts from my e-wallet when mandated by state or federal law, such as a court order</a><span class="req">*</span></label></dd>
						
				
			</dl>
		</div>

	</div>
	<br />
	<div class="clearfix">

		<input type="hidden" name="termsAndCondDto.acceptTermsInd" id="termsAndCondDto.acceptTermsInd" />
		<input type="hidden" name="termsAndCondDto.acceptBucksTermsInd" id="termsAndCondDto.acceptBucksTermsInd" />
		<!-- SLT-2236:New checkbox -->
		<input type="hidden" name="termsAndCondDto.acceptNewBucksTermsInd" id="termsAndCondDto.acceptNewBucksTermsInd" />
		<input type="image" id="previousID" src="${staticContextPath}/images/common/spacer.gif" width="126px"
		height="20px" class="btn20Bevel" 
		style="background-image:url(${staticContextPath}/images/btn/previous.gif);"
		onclick="setCheckBox('termsAndCondActiondoprevious.action')" />&nbsp;
		
		<input type="image" id="SaveID" src="${staticContextPath}/images/images_registration/common/spacer.gif" width="126px"
		height="20px" class="btn20Bevel" 
		style="background-image:url(${staticContextPath}/images/btn/save.gif);"
		onclick="setCheckBox('termsAndCondActiondoSave.action')" />
		<!--  
			<a href="<s:url action="addServiceProAction" includeParams="none"/>">
	            <img src="${staticContextPath}/images/simple/button-next.png"  alt="">
	        </a>
        -->
		

</div>
</s:form>
<div dojoType="dijit.Dialog" id="Bucks">
<div style="height:300px;width: 500px; overflow:auto"> ${termsAndCondDto.slBucksText} </div>
	<p align="center">
		<input type="button" value="Agree" id="providerServiceLiveBucksAgreeBtn"  onclick="agreeBucks();"/>
		&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="Cancel" id="cancelBtn" onclick="closeBucks();" />
	</p>
</div>


<br />

<!-- END TAB PANE -->
			
