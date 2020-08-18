<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
	 <jsp:param name="PageName" value="BuyerRegistration.editCompanyProfile"/>
</jsp:include>	
<html>
	<head>
		<title>ServiceLive [Edit Company Profile]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript">
		function copyValue(a)
		{	
			if(a.checked)
			{
				document.getElementById("mailingStreet1").value = document.getElementById("businessStreet1").value;
				document.getElementById("mailingStreet2").value = document.getElementById("businessStreet2").value;
				document.getElementById("mailingAprt").value = document.getElementById("businessAprt").value;
				document.getElementById("mailingCity").value = document.getElementById("businessCity").value;
				document.getElementById("mailingState").value = document.getElementById("businessState").value;
				document.getElementById("mailingZip").value = document.getElementById("businessZip").value;
			}
			else
			{
				document.getElementById("mailingStreet1").value = "";
				document.getElementById("mailingStreet2").value = "";
				document.getElementById("mailingAprt").value = "";
				document.getElementById("mailingCity").value = "";
				document.getElementById("mailingState").value = "";
				document.getElementById("mailingZip").value = "";
			}
		}
		</script>
		<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit._Calendar");
			dojo.require("dojo.date.locale");
			dojo.require("dojo.parser");
			dojo.require("newco.rthelper");
		    dojo.require("newco.jsutils");
			dojo.require("dijit.form.DateTextBox");		    
		</script>
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
			href="${staticContextPath}/css/service_order_wizard.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
			<script language="JavaScript" src="${staticContextPath}/javascript/js_registration/utils.js" type="text/javascript"></script>
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
						<div>
							<img
								src="${staticContextPath}/images/admin/hdr_editCompanyProfile.gif"
								alt="Edit Company Profile" />
						</div>
					</div>

					

				</div>

				<!-- BEGIN RIGHT PANE -->
				<div class="colRight255 clearfix"></div>
				<s:form action="buyerEditCompanyProfileAction" theme="simple" id="buyerEditCompanyProfile">
				<!-- Changes Starts for SL-20461 -->
	
	
		<c:set var="newAdminFirstName" value="%{buyerRegistrationDTO.newAdminFirstName}"  scope="request" />
		<c:set var="newAdminResourceId" value="%{buyerRegistrationDTO.newAdminResourceId}"  scope="request" />
		<c:set var="newAdminLastName" value="%{buyerRegistrationDTO.newAdminLastName}"  scope="request" />
		<c:set var="newAdminUserName" value="%{buyerRegistrationDTO.newAdminUserName}"  scope="request" />
		
		
        <input type="hidden" name="buyerRegistrationDTO.newAdminResourceId" id="newAdminResourceId"
		value="${buyerRegistrationDTO.newAdminResourceId}" />
	   <input type="hidden" name="buyerRegistrationDTO.newAdminFirstName" id="newAdminFirstName"
		value="${buyerRegistrationDTO.newAdminFirstName}" />
		<input type="hidden" name="buyerRegistrationDTO.newAdminLastName" id="newAdminLastName"
		value="${buyerRegistrationDTO.newAdminLastName}" />
     <!-- Changes Ends for SL-20461 -->
					
					<div class="colLeft711">
			
						<div class="content">
										<div style="color: blue">
 		<p>${msg}</p>
 		<%session.setAttribute("msg",""); %>
		</div>
							<p class="noTopPad">
						Use the fields below to edit your business and contact information. Some fields are locked for your protection. If you need to make changes, contact your ServiceLive administrator. 
							</p>
				<div id="content_right_header_text">
					<%@ include file="message.jsp"%>
				</div>
							<!-- NEW MODULE/ WIDGET-->
							<div class="darkGrayModuleHdr">
								Business Information
							</div>
							<div class="grayModuleContent">
								<p>
								Enter your business contact information. If you are purchasing services for yourself and not as a representative
								for a company, provide your personal contact information instead.
								</p>

								<table cellpadding="0" cellspacing="0" width="650">
									<tr>
										<td>
											<p>Business Name<br />
                   				 		<s:property value="buyerRegistrationDTO.businessName"/> </p>
										</td>
										<td width="325">
											<p>&nbsp;</p>
										</td>
										</tr>
									<tr>
									
										<td>
										<c:choose>
										<c:when
												test="${fieldErrors['buyerRegistrationDTO.phoneAreaCode'] != null or fieldErrors['buyerRegistrationDTO.phonePart1'] != null or fieldErrors['buyerRegistrationDTO.phonePart2'] != null}">
												<p class="errorBox">
											</c:when>
											<c:otherwise>
												<p class="paddingBtm">
											</c:otherwise>
										</c:choose>
											<label>
												Main Business Phone
											</label>
											<br />
											<s:textfield name="buyerRegistrationDTO.phoneAreaCode"
												value="%{buyerRegistrationDTO.phoneAreaCode}"
												 maxlength="3"
												cssStyle="width: 30px;" cssClass="shadowBox grayText" />
											-
											<s:textfield name="buyerRegistrationDTO.phonePart1"
												value="%{buyerRegistrationDTO.phonePart1}"
												 maxlength="3"
												cssStyle="width: 30px;" cssClass="shadowBox grayText" />
											-

											<s:textfield name="buyerRegistrationDTO.phonePart2"
												value="%{buyerRegistrationDTO.phonePart2}"
												 maxlength="4"
												cssStyle="width: 45px;" cssClass="shadowBox grayText" />
												<label>
												Ext.
												</label>
												<s:textfield name="buyerRegistrationDTO.phoneExtn"
												value="%{buyerRegistrationDTO.phoneExtn}"
												 maxlength="4"
												cssStyle="width: 45px;" cssClass="shadowBox grayText" />
										<td>
										<c:choose>		
										<c:when test="${fieldErrors['buyerRegistrationDTO.faxAreaCode'] != null or fieldErrors['buyerRegistrationDTO.faxPart1'] != null or fieldErrors['buyerRegistrationDTO.faxPart2'] != null}">
												<p class="errorBox">
											</c:when>
											<c:otherwise>											
												<p class="paddingBtm">
											</c:otherwise>
										</c:choose>
											<label>
												Business Fax
											</label>
											<br />
											<s:textfield name="buyerRegistrationDTO.faxAreaCode"
												value="%{buyerRegistrationDTO.faxAreaCode}"
												 maxlength="3"
												cssStyle="width: 30px;" cssClass="shadowBox grayText" />
											-
											<s:textfield name="buyerRegistrationDTO.faxPart1"
												value="%{buyerRegistrationDTO.faxPart1}"
												 maxlength="3"
												cssStyle="width: 30px;" cssClass="shadowBox grayText" />
											-
											<s:textfield name="buyerRegistrationDTO.faxPart2"
												value="%{buyerRegistrationDTO.faxPart2}"
												 maxlength="4"
												cssStyle="width: 45px;" cssClass="shadowBox grayText" />
											Optional
										</td>
									</tr>
									<tr>
										<td>
											<p class="paddingBtm">
										    <label>Business Structure</label><br />
											    <s:select name="buyerRegistrationDTO.businessStructure" 
											    headerValue="-- Please Select --" 
											    headerKey="" 
											    value="buyerRegistrationDTO.businessStructure"
												list="buyerRegistrationDTO.businessStructureList"
												listKey="id" listValue="descr" 
												theme="simple" cssStyle="width: 250px;" cssClass="grayText" disabled="true"></s:select>
										</td>
										<td> 
											<c:choose>
											<c:when test="${fieldErrors['buyerRegistrationDTO.businessStarted'] == null}">
											<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												Business Started
											</label>
											<br/>${buyerRegistrationDTO.businessStarted}</p>
											
										</td>
									</tr>
									<tr>
										<td>
											<c:choose>
											<c:when
												test="${fieldErrors['buyerRegistrationDTO.primaryIndustry'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												Primary Industry
											</label>
											<s:select name="buyerRegistrationDTO.primaryIndustry"
												value="%{buyerRegistrationDTO.primaryIndustry}"
												list="buyerRegistrationDTO.primaryIndList" headerKey=""
												headerValue="---------- Please Select ---------"
												listKey="id" listValue="descr" cssStyle="width: 256px;"
												cssClass="grayText" />
											</p>
										</td>
										<td>
										<c:choose>
										<c:when test="${fieldErrors['buyerRegistrationDTO.websiteAddress'] == null}">
											<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
										</c:choose>
											<label>
												Website Address
											</label>
											<s:textfield name="buyerRegistrationDTO.websiteAddress"
												value="%{buyerRegistrationDTO.websiteAddress}"
												 maxlength="255" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
											Optional
											</p>
										</td>
									</tr>
									<tr>
										<td>
											<c:choose>
											
											<c:when
												test="${fieldErrors['buyerRegistrationDTO.sizeOfCompany'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
											</c:choose>
											
											<label>
												Size of Company
											</label>
											<s:select name="buyerRegistrationDTO.sizeOfCompany"
												value="%{buyerRegistrationDTO.sizeOfCompany}"
												list="buyerRegistrationDTO.sizeOfCompanyList" headerKey=""
												headerValue="---------- Please Select ---------"
												listKey="id" listValue="descr" cssStyle="width: 256px;"
												cssClass="grayText"/>
											</p>
										</td>
										<td>
											<c:choose>
											<c:when
												test="${fieldErrors['buyerRegistrationDTO.annualSalesRevenue'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												Annual Sales  Revenue
											</label>
											<s:select name="buyerRegistrationDTO.annualSalesRevenue"
												value="%{buyerRegistrationDTO.annualSalesRevenue}"
												list="buyerRegistrationDTO.annualSalesRevenueList" headerKey=""
												headerValue="---------- Please Select ---------"
												listKey="id" listValue="descr" cssStyle="width: 256px;"
												cssClass="grayText"  />
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
											<c:choose>
											<c:when
												test="${fieldErrors['buyerRegistrationDTO.businessStreet1'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												Street Name
											</label>
											<br />
											<s:textfield id="businessStreet1"
												name="buyerRegistrationDTO.businessStreet1"
												value="%{buyerRegistrationDTO.businessStreet1}"
												 maxlength="40" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />

											</p>
											<c:choose>
											<c:when
												test="${fieldErrors['buyerRegistrationDTO.businessStreet2'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<s:textfield id="businessStreet2"
												name="buyerRegistrationDTO.businessStreet2"
												value="%{buyerRegistrationDTO.businessStreet2}"
												 maxlength="40" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
											</p>
										</td>
										<td width="325">
											<c:choose>
											<c:when
												test="${fieldErrors['buyerRegistrationDTO.businessAprt'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												Apt. #
											</label>
											<br />
											<s:textfield id="businessAprt"
												name="buyerRegistrationDTO.businessAprt"
												value="%{buyerRegistrationDTO.businessAprt}"
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
													<td width="230">
														<c:choose>
														<c:when
															test="${fieldErrors['buyerRegistrationDTO.businessCity'] == null}">
															<p class="paddingBtm">
														</c:when>
														<c:otherwise>
														<p class="errorBox">
														</c:otherwise>
														</c:choose>
														<label>
															City
														</label>
														<br />
														<s:textfield id="businessCity"
															name="buyerRegistrationDTO.businessCity"
															value="%{buyerRegistrationDTO.businessCity}"
															 maxlength="30" cssStyle="width: 190px;"
															cssClass="shadowBox grayText" />
														</p>
													</td>
													<td width="110">
													<c:choose>
														<c:when
															test="${fieldErrors['buyerRegistrationDTO.businessState'] == null}">
															<p class="paddingBtm">
														</c:when>
														<c:otherwise>
															<p class="errorBox">
														</c:otherwise>
													</c:choose> 
														<label>	
															State
														</label>
														<br/>
														<s:select id="businessState"
															name="buyerRegistrationDTO.businessState"
															value="%{buyerRegistrationDTO.businessState}"
															list="#application['stateCodes']" listKey="type"
															listValue="descr" cssStyle="width: 100px;"
															cssClass="shadowBox grayText">
														</s:select>
														</p>
													</td>
													<td width="110">
													<c:choose>
														<c:when
															test="${fieldErrors['buyerRegistrationDTO.businessZip'] == null}">
															<p class="paddingBtm">
														</c:when>
														<c:otherwise>
															<p class="errorBox">
														</c:otherwise>
													</c:choose> <label>
															ZIP
														</label>
														<br />
														<s:textfield id="businessZip"
															name="buyerRegistrationDTO.businessZip"
															value="%{buyerRegistrationDTO.businessZip}"
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
									<s:checkbox name="buyerRegistrationDTO.mailAddressChk"
										onclick="copyValue(this);" />
									The mailing address is the same as the business address.
								</p>
								<table cellpadding="0" cellspacing="0" width="650">
									<tr>
										<td width="325">
											<c:choose>
											<c:when
												test="${fieldErrors['buyerRegistrationDTO.mailingStreet1'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
										</c:choose>
											<label>
												Street Name
											</label>
											<br />
											<s:textfield id="mailingStreet1"
												name="buyerRegistrationDTO.mailingStreet1"
												value="%{buyerRegistrationDTO.mailingStreet1}"
												 maxlength="40" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
											</p>
											<c:choose>
											<c:when
												test="${fieldErrors['buyerRegistrationDTO.mailingStreet2'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
										</c:choose> 
										
											<s:textfield id="mailingStreet2"
												name="buyerRegistrationDTO.mailingStreet2"
												value="%{buyerRegistrationDTO.mailingStreet2}"
												 maxlength="40" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
											</p>
										</td>
										<td width="325">
											<c:choose>
											<c:when
												test="${fieldErrors['buyerRegistrationDTO.mailingAprt'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
										</c:choose> <label>
												Apt. #
											</label>
											<br />
											<s:textfield id="mailingAprt"
												name="buyerRegistrationDTO.mailingAprt"
												value="%{buyerRegistrationDTO.mailingAprt}"
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
													<td width="230">
														<c:choose>
														<c:when
															test="${fieldErrors['buyerRegistrationDTO.mailingCity'] == null}">
															<p class="paddingBtm">
														</c:when>
														<c:otherwise>
															<p class="errorBox">
														</c:otherwise>
													</c:choose> <label>
															City
														</label>
														<br />
														<s:textfield id="mailingCity"
															name="buyerRegistrationDTO.mailingCity"
															value="%{buyerRegistrationDTO.mailingCity}"
															 maxlength="30" cssStyle="width: 190px;"
															cssClass="shadowBox grayText" />
														</p>
													</td>
													<td width="110">
														<p>
															<label>
																State
															</label>
															<br />
															<s:select id="mailingState"
																name="buyerRegistrationDTO.mailingState"
																value="%{buyerRegistrationDTO.mailingState}"
																list="#application['stateCodes']" listKey="type"
																listValue="descr" cssStyle="width: 100px;"
																cssClass="shadowBox grayText">
															</s:select>
														</p>
													</td>
													<td width="110">
														<c:choose>
														<c:when
															test="${fieldErrors['buyerRegistrationDTO.mailingZip'] == null}">
															<p class="paddingBtm">
														</c:when>
														<c:otherwise>
															<p class="errorBox">
														</c:otherwise>
													</c:choose> <label>
															ZIP
														</label>
														<br />
														<s:textfield id="mailingZip"
															name="buyerRegistrationDTO.mailingZip"
															value="%{buyerRegistrationDTO.mailingZip}"
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
								<!-- changes for buyer admin change starts Sl-20461 -->
								
								 <c:choose>
                			<c:when test="${isPermissionForBuyerAdminNameChange == 'true'}"> 
                 <div class="darkGrayModuleHdr">
								Change Buyer Admin Account
							</div>
							<div class="grayModuleContent mainContentWell">
							
								<table>
								<tr>
								<td>
								<s:checkbox onclick="enableBuyerListForAdminChange(this);" id="changeAdmin" name="changeAdmin"  />
						<label>
							<b> Change Buyer Admin</b>
						</label>
								</td>
								
								</tr>
								
				<tr>
					
					<td>
					
					<s:select name="buyerRegistrationDTO.newAdminUserName"
					id="buyerListForAdminChange"
												value="------------ Please Select -------------"
												list="buyerRegistrationDTO.buyerList" headerKey=""
												headerValue="------------ Please Select -------------"
												listKey="user_name" listValue="resources" onclick="fetchNewBuyerName(this);"
												cssStyle="width: 256px;"
												cssClass="grayText" disabled="true" />
					
					
						
					</td>
				</tr>
								</table>
								</div>
								 </c:when>
              </c:choose>
								<!-- changes for buyer admin change ends Sl-20461 -->
								
								
								
							<!-- changes for SL-20536 starts -->
							<c:choose>
                			<c:when test="${showTermsAndConditionsOfBuyer == 'true'}"> 
                			<!-- changes for SL-20536 ends -->
							<div class="darkGrayModuleHdr">
								Site Terms & Conditions
							</div>
							<div class="grayModuleContent mainContentWell">
							Please read the following <a href="/MarketFrontend/termsAndConditions_displayBuyerAgreement.action" onclick="window.open(this.href,'terms','width=1040,height=640,scrollbars,resizable'); return false;">Terms and Conditions</a>. After accepting, you'll receive an e-mail confirming your account. Use the temporary password included in that e-mail to log back onto the system so you can register other users for your firm or begin creating a service order.
							<br/> <br/>
								<%--  
								<div class="inputArea" style="height: 200px; overflow: auto">
									<p>${buyerRegistrationDTO.termsAndConditions}</p>
									<br />
									<br />
									<br />
								</div>
								--%>
							<c:choose>
								<c:when
									test="${fieldErrors['buyerRegistrationDTO.termsAndCondition'] == null}">
									<p class="paddingBtm">
								</c:when>
								<c:otherwise>
									<p class="errorBox">
								</c:otherwise>
							</c:choose>

							<p>
									<s:radio id="termsAndConditionID" labelposition="top" value="0"
										list="#{'1':'I accept the Terms & Conditions', '0':'I do not accept the Terms & Conditions'}"
										name="buyerRegistrationDTO.termsAndCondition">
										<br />
									</s:radio>
								</p>
							</div>
							<!-- changes for SL-20536 starts -->
							 </c:when>
              </c:choose>
              <!-- changes for SL-20536 ends -->
							<div class="clearfix">
								<div class="formNavButtons2">

									<s:submit type="image" id="submitPageID"
										src="%{#request['staticContextPath']}/images/common/spacer.gif"
										cssStyle="background-image:url(%{#request['staticContextPath']}/images/images_registration/btn/save.gif);width:49px;height:20px;"
										cssClass="btn20Bevel" method="submit"/>

								</div>
							</div>
						</div>
					</div>
					
					
				</s:form>

			<c:if test="${SecurityContext.slAdminInd}">
				<div class="monitorTab-rightCol">
					<jsp:include page="/jsp/so_monitor/menu/widgetAdminMemberInfo.jsp"/>
				<div>
			</c:if>
								
			</div>
		
			<div class="clear"></div>
			</div>
			<!-- START FOOTER -->
			<c:import url="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER -->
			
		
		

	</body>
</html>
