<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="com.newco.marketplace.interfaces.ProviderConstants"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="noJs" value="true" scope="request" />
<c:set var="noCss" value="true" scope="request" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="other_primary_service" value="<%=ProviderConstants.OTHER_PRIMARY_SERVICES%>" />	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>ServiceLive [Provider Registration]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />
		<link href="${staticContextPath}/javascript/confirm.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		<style type="text/css">
		#yesButton,#noButton{
			background: url("${staticContextPath}/images/common/button-action-bg.png");
			border:1px solid #b1770b;
			color:#222;
			font-family:Arial,Tahoma,sans-serif;
			font-size:1.1em;
			font-weight:bold;
			padding:3px 10px;
			cursor: pointer;
			-moz-border-radius:5px 5px 5px 5px;
			margin-top:  -5px;
			text-align: center;
			width: 80px;
		 }
		 
		</style>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js" ></script>	
		<script type="text/javascript" src="${staticContextPath}/javascript/providerRegistration.js"></script>	
		<script type="text/javascript">
				function checkForOtherService(){
				primaryIndustry = document.getElementById('primaryIndustry').value;
				if(primaryIndustry == ${other_primary_service}){
					document.getElementById('otherPrimaryService').style.display = 'block';
				}else{
					document.getElementById('otherPrimaryService').style.display = 'none';
				}
			}
				
		function confirmLegalName()
		{
		
	 		var firstname=$("input[name='registrationDto.firstName']").val();
	 		var middlename=$("input[name='registrationDto.middleName']").val();
	 	    var lastname=$("input[name='registrationDto.lastName']").val();
	 		var fullName=firstname+" " +middlename+" "+lastname;
	 		
	 		 
	 		$('div#popUpConfirm b#newProviderFullName').text(fullName);
 			$("#popUpConfirm").modal({
                onOpen: modalOpen,
                onClose: modalOnClose,
                persist: true,
                containerCss: ({ display:"block",width: "auto", height: "auto",marginTop: "100px" ,border:"none"})
            });
				
 			$(".modalCloseImg").hide();
 			$("#popUpConfirm").css("width", "520px");
  			$("#popUpConfirm").css("height", "auto");
  			$("#popUpConfirm").css("top", "0px");
  			$("#popUpConfirm").css("zIndex", 1000);
  			$("#popUpConfirm").css("background-color","#FFFFFF");
  			$("#popUpConfirm").css("border-left-color","#A8A8A8");
  			$("#popUpConfirm").css("border-right-color","#A8A8A8");
  			$("#popUpConfirm").css("border-bottom-color","#A8A8A8");
  			$("#popUpConfirm").css("border-top-color","#A8A8A8");

				
		}
		
		function modalOpen(dialog) {
            dialog.overlay.fadeIn('fast', function() {
            	dialog.container.fadeIn('fast', function() {
            		dialog.data.hide().slideDown('slow');
            	});
        	});
 		}
  
   		function modalOnClose(dialog) {
       		dialog.data.fadeOut('fast', function() {
            	dialog.container.slideUp('fast', function() {
            		dialog.overlay.fadeOut('fast', function() {
            			jQuery.modal.close(); 
            		});
          		});
       		});
    	}
   		
   		
   		function successMethod(){
   		
   		    window.location.href="${contextPath}/providerDoSaveAction.action?popconfirm=1";
   		}
   		jQuery(document).ready( function ($) {
   			var indicator ='${popUp}';
   			
   			if(indicator==1)
   			{
   			 confirmLegalName();
   			}
   			});
   		
   		$('#noButton').click(function () {
				$('#modalContainer a.modalCloseImg').trigger('click');
			});
   		
   		
		</script>

	</head>
	<body class="tundra">
	    
		<div id="page_margins">
			<div id="page" class="clearfix">
				<!-- BEGIN HEADER -->
				<div id="headerSPReg">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
					<div id="pageHeader">
						<h2>Register As A Service Provider</h2>
					</div>
				</div>
				<div id="content_right_header_text">
					<%@ include file="message.jsp"%>
				</div>
				<!-- BEGIN RIGHT PANE -->
				<div class="colRight255 clearfix"></div>
				<s:form theme="simple"  action="providerRegSaveAction" id="provRegSave">
					<div class="colLeft711">
						<div class="content">
							<p class="noTopPad">
								Sign up for a provider account so you can accept work and
								provide service for members of the ServiceLive community. Enter
								your primary contact information and we'll email you a temporary
								password that you can use to build your profile on our secure
								server.
							</p>
							<p><b>An asterix (<span class="req">*</span>) indicates a required field</b></p><br/>
							<!-- NEW MODULE/ WIDGET-->
							<div class="darkGrayModuleHdr">
								Business Information
							</div>
							<div class="grayModuleContent">
								<p>
									The fields below give you an opportunity to tell buyers a
									little more about your business. Your written description will
									be included as part of your public profile
								</p>
								
								<p>An asterix (<span class="req">*</span>) indicates a required field</p>
								

								<table cellpadding="0" cellspacing="0" width="650">
									<tr>
										<td width="325">
											<c:choose><c:when
												test="${fieldErrors['registrationDto.legalBusinessName'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise></c:choose>
											<label>
											    Legal Business Name<span class="req">*</span>
											</label>
											<br />

											<s:textfield name="registrationDto.legalBusinessName"
												value="%{registrationDto.legalBusinessName}" maxlength="100"
												 cssStyle="width: 250px;"
												cssClass="shadowBox grayText"></s:textfield>
											</p>

										</td>
										<td width="325">

											<p class="paddingBtm">

												<label>
													Doing Business As (DBA)
												</label>
												<br />
												<s:textfield name="registrationDto.DBAName"
													value="%{registrationDto.DBAName}" maxlength="30"
													cssStyle="width: 250px;" cssClass="shadowBox grayText"></s:textfield>
												
											</p>
										</td>
									</tr>
									<tr>
										

											<c:choose><c:when
												test="${fieldErrors['registrationDto.mainBusiPhoneNo1'] != null or fieldErrors['registrationDto.mainBusiPhoneNo2'] != null or fieldErrors['registrationDto.mainBusiPhoneNo3'] != null}">
												<td class="errorBox">
											</c:when>
											<c:otherwise>
												<td class="paddingBtm">
											</c:otherwise></c:choose>
											<label>
												Main Business Phone <span class="req">*</span>
											</label>
											<br />
											<s:textfield name="registrationDto.mainBusiPhoneNo1"
												value="%{registrationDto.mainBusiPhoneNo1}"
												 maxlength="3"
												cssStyle="width: 30px;" cssClass="shadowBox grayText" />
											-
											<s:textfield name="registrationDto.mainBusiPhoneNo2"
												value="%{registrationDto.mainBusiPhoneNo2}"
												 maxlength="3"
												cssStyle="width: 30px;" cssClass="shadowBox grayText" />
											-

											<s:textfield name="registrationDto.mainBusiPhoneNo3"
												value="%{registrationDto.mainBusiPhoneNo3}"
												 maxlength="4"
												cssStyle="width: 45px;" cssClass="shadowBox grayText" />
												</td>
											<c:choose><c:when test="${fieldErrors['registrationDto.mainBusinessExtn'] != null }">
											<td class="errorBox">
											</c:when>
											<c:otherwise>
											<td class="paddingBtm">
												</c:otherwise></c:choose>
												<br/><label>
												Ext.
												</label>
												<s:textfield name="registrationDto.mainBusinessExtn"
												value="%{registrationDto.mainBusinessExtn}"
												 maxlength="4"
												cssStyle="width: 45px;" cssClass="shadowBox grayText" />
											
										<td><br><br></td>
									</tr>
									<tr>
										<td width="650" colspan="2">
										<c:choose><c:when
												test="${fieldErrors['registrationDto.businessFax1'] != null or fieldErrors['registrationDto.businessFax2'] != null or fieldErrors['registrationDto.businessFax3'] != null}">
												<p class="errorBox">
											</c:when>
											<c:otherwise>
												<p class="paddingBtm">
											</c:otherwise></c:choose>
											<label>
												Business Fax
											</label>
											<br />
											<s:textfield name="registrationDto.businessFax1"
												value="%{registrationDto.businessFax1}"
												 maxlength="3"
												cssStyle="width: 30px;" cssClass="shadowBox grayText" />
											-
											<s:textfield name="registrationDto.businessFax2"
												value="%{registrationDto.businessFax2}"
												 maxlength="3"
												cssStyle="width: 30px;" cssClass="shadowBox grayText" />
											-
											<s:textfield name="registrationDto.businessFax3"
												value="%{registrationDto.businessFax3}"
												 maxlength="4"
												cssStyle="width: 45px;" cssClass="shadowBox grayText" />
											
											</p>
										</td>


									</tr>
									
									<tr>
										<td width="325">
											<c:choose><c:when
												test="${fieldErrors['registrationDto.primaryIndustry'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise></c:choose>
											<label>
												Primary Industry <span class="req">*</span>
											</label>
											<br />											
											<s:select id="primaryIndustry" name="registrationDto.primaryIndustry"
												value="%{registrationDto.primaryIndustry}"
												list="registrationDto.primaryIndList" headerKey=""
												headerValue="---------- Please Select ---------"
												listKey="id" listValue="descr" cssStyle="width: 256px;"
												cssClass="grayText" onclick="changeDropdown(this);checkForOtherService();" />
											</p>
										</td>
										<td width="325">
											<c:choose><c:when test="${fieldErrors['registrationDto.otherPrimaryService'] == null}">
												<p id="otherPrimaryService" class="paddingBtm" style="display:none">
											</c:when>
											<c:otherwise>
												<p id="otherPrimaryService" class="errorBox" style="display:block">
											</c:otherwise></c:choose>										
												<label>
													Please describe your Industry <span class="req">*</span>
												</label>
												<br />
												<s:textfield name="registrationDto.otherPrimaryService"
													value="%{registrationDto.otherPrimaryService}" maxlength="30"
													cssStyle="width: 250px;" cssClass="shadowBox grayText"></s:textfield>												
												</p>
										</td>
									</tr>								
									<tr>
										<td width="650" colspan="2">
											<label>
												Website Address
											</label>
											<br />
											<s:textfield name="registrationDto.websiteAddress"
												value="%{registrationDto.websiteAddress}"
												 maxlength="255" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
											
											</p>
										</td>
									</tr>
								</table>
							</div>
							<!-- NEW MODULE/ WIDGET-->
							<div class="darkGrayModuleHdr">
								Business Address
							</div>
							<div class="grayModuleContent">
								<table cellpadding="0" cellspacing="0" width="650">
									<tr>
										<td width="325">
											<c:choose><c:when
												test="${fieldErrors['registrationDto.businessStreet1'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise></c:choose>
											<label>
												Street Name <span class="req">*</span>
											</label>
											<br />
											<s:textfield id="businessStreet1"
												name="registrationDto.businessStreet1"
												value="%{registrationDto.businessStreet1}"
												 maxlength="40" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />

											</p>
											<c:choose><c:when
												test="${fieldErrors['registrationDto.businessStreet2'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise></c:choose>
											<s:textfield id="businessStreet2"
												name="registrationDto.businessStreet2"
												value="%{registrationDto.businessStreet2}"
												 maxlength="40" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
											</p>
										</td>
										<td width="325">
											<c:choose><c:when
												test="${fieldErrors['registrationDto.businessAprt'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise></c:choose>
											<label>
												Apt. #
											</label>
											<br />
											<s:textfield id="businessAprt"
												name="registrationDto.businessAprt"
												value="%{registrationDto.businessAprt}"
												 maxlength="10" cssStyle="width: 100px;"
												cssClass="shadowBox grayText" />
											</p>
											<p style="line-height: 14px;">
												&nbsp;
											</p>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<table cellpadding="0" cellspacing="0">
												<tr>
													<td width="205">
														<c:choose><c:when
															test="${fieldErrors['registrationDto.businessCity'] == null}">
															<p class="paddingBtm">
														</c:when>
														<c:otherwise>
															<p class="errorBox">
														</c:otherwise></c:choose>
														<label>
															City <span class="req">*</span>
														</label>
														<br />
														<s:textfield id="businessCity"
															name="registrationDto.businessCity"
															value="%{registrationDto.businessCity}"
															 maxlength="30" cssStyle="width: 190px;"
															cssClass="shadowBox grayText" />
														</p>
													</td>
													<td width="110">
														<label>
															State
														</label>
														<br />
														<s:select id="businessState"
															name="registrationDto.businessState"
															value="%{registrationDto.businessState}"
															list="#application['stateCodes']" listKey="type"
															listValue="descr" cssStyle="width: 100px;"
															cssClass="shadowBox grayText">
														</s:select>
														</p>
													</td>
													<td width="75">
														<c:choose><c:when
															test="${fieldErrors['registrationDto.businessZip'] == null}">
															<p class="paddingBtm">
														</c:when>
														<c:otherwise>
															<p class="errorBox">
														</c:otherwise></c:choose>
														<label>
															ZIP <span class="req">*</span>
														</label>
														<br />
														<s:textfield id="businessZip"
															name="registrationDto.businessZip"
															value="%{registrationDto.businessZip}"
															 maxlength="10" cssStyle="width: 70px;"
															cssClass="shadowBox grayText" />
														</p>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</div>
							<!-- NEW MODULE/ WIDGET-->
							<div class="darkGrayModuleHdr">
								Mailing Address
							</div>
							<div class="grayModuleContent">
								<p>
									<s:checkbox name="registrationDto.mailAddressChk"
										onclick="copyValue();" />
									The mailing address is the same as the business address.
								</p>
								<table cellpadding="0" cellspacing="0" width="650">
									<tr>
										<td width="325">
											<c:choose><c:when
												test="${fieldErrors['registrationDto.mailingStreet1'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise></c:choose>
											<label>
												Street Name<span class="req">*</span>
											</label>
											<br />
											<s:textfield id="mailingStreet1"
												name="registrationDto.mailingStreet1"
												value="%{registrationDto.mailingStreet1}"
												 maxlength="40" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
											</p>

											<c:choose><c:when
												test="${fieldErrors['registrationDto.mailingStreet2'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise></c:choose>
											<s:textfield id="mailingStreet2"
												name="registrationDto.mailingStreet2"
												value="%{registrationDto.mailingStreet2}"
												 maxlength="40" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
											</p>
										</td>
										<td width="325">
											<c:choose><c:when
												test="${fieldErrors['registrationDto.mailingAprt'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise></c:choose>
											<label>
												Apt. #
											</label>
											<br />
											<s:textfield id="mailingAprt"
												name="registrationDto.mailingAprt"
												value="%{registrationDto.mailingAprt}"
												 maxlength="10" cssStyle="width: 100px;"
												cssClass="shadowBox grayText" />
											</p>
											<p style="line-height: 14px;">
												&nbsp;
											</p>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<table cellpadding="0" cellspacing="0">
												<tr>
													<td width="205">
														<c:choose><c:when
															test="${fieldErrors['registrationDto.mailingCity'] == null}">
															<p class="paddingBtm">
														</c:when>
														<c:otherwise>
															<p class="errorBox">
														</c:otherwise></c:choose>
														<label>
															 City<span class="req">*</span>
														</label>
														<br />
														<s:textfield id="mailingCity"
															name="registrationDto.mailingCity"
															value="%{registrationDto.mailingCity}"
															 maxlength="30" cssStyle="width: 190px;"
															cssClass="shadowBox grayText" />
														</p>
													</td>
													<td width="110">
														<p>
															<label>
																State<span class="req">*</span>
															</label>
															<br />
															<s:select id="mailingState"
																name="registrationDto.mailingState"
																value="%{registrationDto.mailingState}"
																list="#application['stateCodes']" listKey="type"
																listValue="descr" cssStyle="width: 100px;"
																cssClass="shadowBox grayText">
															</s:select>
														</p>
													</td>
													<td width="75">
														<c:choose><c:when
															test="${fieldErrors['registrationDto.mailingZip'] == null}">
															<p class="paddingBtm">
														</c:when>
														<c:otherwise>
															<p class="errorBox">
														</c:otherwise></c:choose>
														<label>
															 ZIP<span class="req">*</span>
														</label>
														<br />
														<s:textfield id="mailingZip"
															name="registrationDto.mailingZip"
															value="%{registrationDto.mailingZip}"
															 maxlength="10" cssStyle="width: 70px;"
															cssClass="shadowBox grayText" />
														</p>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</div>
							<!-- NEW MODULE/ WIDGET-->
							<div class="darkGrayModuleHdr">
								Primary Contact Information - Administrator
							</div>
							<div class="grayModuleContent">
								<p>
									By opening this account, you become the ServiceLive
									administrator for your company. You’ll be the primary contact
									for all communications with ServiceLive and you will be
									authorized to enroll additional service providers and support
									staff for your provider firm.
								</p>
								<table cellpadding="0" cellspacing="0" width="650">
									<tr>
										<td width="325">
											<c:choose><c:when
												test="${fieldErrors['registrationDto.roleWithinCom'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise></c:choose>
											<label>
												Role within Company <span class="req">*</span>
											</label>
											<br />

											<s:select name="registrationDto.roleWithinCom"
												value="%{registrationDto.roleWithinCom}"
												list="registrationDto.roleWithinCompany" headerKey=""
												headerValue="-- Please Select --" listKey="id"
												listValue="descr" cssStyle="width: 256px;"
												cssClass="grayText" onclick="changeDropdown(this);" />

											</p>
										</td>
										<td width="325">
											<p>
												<label>
													Job Title
												</label>
												<br />
												<s:textfield name="registrationDto.jobTitle"
													value="%{registrationDto.jobTitle}"
													 maxlength="50" cssStyle="width: 250px;"
													cssClass="shadowBox grayText" />
												
											</p>
										</td>
									</tr>
									
									<tr>
										<td>
											<c:choose><c:when
												test="${fieldErrors['registrationDto.firstName'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise></c:choose>
											<label>
												Legal First Name <span class="req">*</span>
											</label>
											<br />
											<!--  
											<s:textfield name="registrationDto.firstName" onfocus="clearField('provRegSave_registrationDto_firstName');"
												value="%{registrationDto.firstName == null || registrationDto.firstName == ''?'':registrationDto.firstName}"
												 maxlength="50" cssStyle="width: 250px;color: #666666;font-style: italic;"
												 onblur="checkIfEmpty('provRegSave_registrationDto_firstName','first');" cssClass="shadowBox grayText" />
											-->	 
											<s:textfield name="registrationDto.firstName" value="%{registrationDto.firstName}"
												 maxlength="50" cssStyle="width: 250px;"
											cssClass="shadowBox grayText" />
											
											</p> 
										</td>
										<td>
											<p>
												<label>
													Legal Middle Name
												</label>
												<br />
												<s:textfield name="registrationDto.middleName"
													value="%{registrationDto.middleName}"
													 maxlength="50" cssStyle="width: 250px;"
													cssClass="shadowBox grayText" />
												
											</p>
										</td>
									</tr>
									<tr>
										<td>
											<!--  p class="errorBox" -->
											<c:choose><c:when
												test="${fieldErrors['registrationDto.lastName'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise></c:choose>
											<label>
												Legal Last Name <span class="req">*</span>
											</label>
											<br />
											<!-- 
											<s:textfield name="registrationDto.lastName"  onfocus="clearField('provRegSave_registrationDto_lastName');"
												value="%{registrationDto.lastName == null || registrationDto.lastName == ''?'':registrationDto.lastName}"
												 maxlength="50" cssStyle="width: 250px;color: #666666;font-style: italic;"
												cssClass="shadowBox grayText"  onblur="checkIfEmpty('provRegSave_registrationDto_lastName','last')"/>
											-->
											<s:textfield name="registrationDto.lastName"  value="%{registrationDto.lastName}"
											 maxlength="50" cssStyle="width: 250px;"
												cssClass="shadowBox grayText"  />
											<br>
											<!-- span class="errorMsg">Please fill in the Administrator's  Last Name.</span-->
											</p>
										</td>
										<td>
											<p>
												<label>
													Suffix (Jr., II, etc.)
												</label>
												<br />
												<s:textfield name="registrationDto.nameSuffix"
													value="%{registrationDto.nameSuffix}"
													 maxlength="10" cssStyle="width: 100px;"
													cssClass="shadowBox grayText" />
												
											</p>
										</td>
									</tr>
									<tr>
										<td>
											<c:choose><c:when test="${fieldErrors['registrationDto.email'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise></c:choose>
											<label>
												E-mail Address <span class="req">*</span>
											</label>
											<br />
											<s:textfield name="registrationDto.email"
												value="%{registrationDto.email}"
												 maxlength="255" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
											</p>
										</td>
										<td>
											<c:choose><c:when
												test="${fieldErrors['registrationDto.confirmEmail'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise></c:choose>
											<label>
												Confirm E-mail Address <span class="req">*</span>
											</label>
											<br />
											<s:textfield name="registrationDto.confirmEmail"
												value="%{registrationDto.confirmEmail}"
												 maxlength="255" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
											</p>
										</td>
									</tr>
									<tr>
										<td>
											<!--  p class="errorBox"-->
											<c:choose><c:when
												test="${fieldErrors['registrationDto.altEmail'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise></c:choose>
											<label>
												Alternate E-mail Address
											</label>
											<br />
											<s:textfield name="registrationDto.altEmail"
												value="%{registrationDto.altEmail}"
												 maxlength="255" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
											<br>
											<!-- span class="errorMsg">Invalid E-mail Address.  Please try again.</span-->
											</p>
										</td>
										<td>
											<c:choose><c:when
												test="${fieldErrors['registrationDto.confAltEmail'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise></c:choose>
											<label>
												Confirm Alternate E-mail Address
											</label>
											<br />
											<s:textfield name="registrationDto.confAltEmail"
												value="%{registrationDto.confAltEmail}"
												 maxlength="255" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
											</p>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<c:choose><c:when
												test="${fieldErrors['registrationDto.userName'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise></c:choose>
											<label>
												User Name <span class="req">*</span>
											</label>
											<br />
											<s:textfield name="registrationDto.userName"
												maxlength="30"
												value="%{registrationDto.userName}"
												 cssStyle="width: 250px;"
												cssClass="shadowBox grayText"/>
												(User name must be 8 characters or more)
											</p>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<c:choose><c:when
												test="${fieldErrors['registrationDto.serviceCall'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise></c:choose>
											Will you perform service calls in the marketplace?
											<br>
											<span class="formFieldOffset"> <s:radio
													name="registrationDto.serviceCall"
													value="%{registrationDto.serviceCall}"
													list="serviceCallList"></s:radio> </span>
											</p>
										</td>
									</tr>
									
									<tr>
										<td>
											<c:choose><c:when
												test="${fieldErrors['registrationDto.howDidYouHear'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise></c:choose>
											How did you hear about ServiceLive? <span class="req">*</span>
											<br>
											<s:select id="howDidYouHear" name="registrationDto.howDidYouHear"
												value="%{registrationDto.howDidYouHear}"
												list="registrationDto.howDidYouHearList" headerKey=""
												headerValue="-- Please Select --" listKey="id"
												listValue="descr" cssStyle="width: 256px;"
												cssClass="shadowBox grayText">

											</s:select>
											</p>
										</td>
										<td>
											<c:choose><c:when
												test="${fieldErrors['registrationDto.promotionCode'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise></c:choose>
											<label>
												Referral Code
											</label>
											<br />SL- 
											<a href="jsp/public/simple/tooltips/tooltip.jsp?bundlekey=simplebuyer.referralcode.help.text&width=367" 
											id="promotionCodehelp" class="jTip" name="Helpful Information" >	
												<s:textfield id="promoCode" name="registrationDto.promotionCode"
												value="%{registrationDto.promotionCode}"
												maxlength="200" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
											</a>
											</p>
										</td>
									</tr>
									<tr>
										<td></td>
										<td><i>Enter all letters, numbers or special characters as shown.</i></td>
									</tr>
								</table>
							</div>
							<div class="clearfix">
								<div class="formNavButtons2">

									<s:submit onclick="return checkPromoCode();" type="image" id="submitPageID" 
										src="%{#request['staticContextPath']}/images/common/spacer.gif"
										cssStyle="background-image:url(%{#request['staticContextPath']}/images/btn/submitReg_whiteBg.gif);width:180px;height:20px"
										cssClass="btn20Bevel" />

								</div>
							</div>
						</div>
					</div>
				</s:form>
			</div>
			<div class="clear"></div>
			
			<!-- SL-18360  pop up for confirmation-->
			<div  id="popUpConfirm" style="display: none;  border: 3px solid #CCCCCC; color: #000000;height: auto;">
			<div style=" font-family: Arial,Helvetica,sans-serif; font-size: 12px">
			 <div style="background: none repeat scroll 0% 0% rgb(88, 88, 90); border-bottom: 2px solid black; color: rgb(255, 255, 255); text-align: left; height: 25px; padding-left: 8px; padding-top: 5px;" id="modalTitleBlack">
		<span style="font-size: 17px; font-weight: bold;">Name Validation</span> 
	</div>
	<br/>
					<span class="popText" style="padding-left ;position: relative; left: 6px; line-height: 1.5;">Verify the legal information as entered:<br>
					                    <ul style="padding-left: 15px;">
					                   <li><b>Legal Full Name:</b>&nbsp<b  id="newProviderFullName"></b><br/></li>
					                   </ul>
					                  <br/>
					                   This information will be used to conduct any background screenings. If it is incorrect, you may delay being Approved for Market.</span>
				</div>
				<br>
				
				<table style="padding-right ;position: relative; left: 8px" width="100%">
					<tr>
						<td width="60%">
							<input id="noButton" class="button simplemodal-close" type="button" value="Cancel" />
							<br><br>
						</td>
						<td width="28%">
							<input id="yesButton" type="button" value="The legal info is correct" style="width:150px"  onclick="successMethod();"/>
						</td>
					</tr>
				</table>
			</div>
			
			<!-- START FOOTER -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER -->
		</div>
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="ProRegStart"/>
		</jsp:include>
	</body>
</html>
