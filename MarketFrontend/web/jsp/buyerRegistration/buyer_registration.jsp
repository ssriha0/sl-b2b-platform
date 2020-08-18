<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<c:set var="showTags" scope="request" value="1" />
<%-- ss: don't import CSS and JS files in the global header --%>
<c:set var="noJs" value="true" scope="request" />
<c:set var="noCss" value="true" scope="request" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	
		<title>ServiceLive [Commercial Buyer Registration]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/jqueryui/jquery-ui-1.7.2.custom.css" />
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqueryui/jquery-ui-1.10.4.custom.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqmodal/jqModal.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/buyerRegistration.js"></script>
		<script src="${staticContextPath}/scripts/plugins/jquery.mask.min.js" type="text/javascript"></script>

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
						<h2>Register</h2>
					</div>
				</div>

				<!-- BEGIN RIGHT PANE -->
				<div class="colRight255 clearfix"></div>
				<s:form action="buyerRegistrationAction" theme="simple" id="buyerRegistration">
					<div class="colLeft711">
						<div class="content">
							<p class="noTopPad">
						ServiceLive connects you to an online community of pre-screened service providers. Sign up for a buyer account to find providers who can work within your budget on your schedule. All data that you share will be handled securely. 
							</p>
							
				<c:if test="${not empty blackoutStates}">
				<div style="background: #ffffcc; font-size: 10px;">
					<p>
					<strong>We are unable to fulfill buyer requests in the following states/U.S. Territories:</strong>
					<s:iterator value="blackoutStates" status="statePos">
						<s:property/><s:if test="!#statePos.last">,</s:if>
					</s:iterator>					
					</p>
				</div>
				</c:if>
				
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
								<p>An asterix (<span class="req">*</span>) indicates a required field</p>
								<table cellpadding="0" cellspacing="0" width="650">
									<tr>
										<td>
										<c:choose>
										<c:when test="${fieldErrors['buyerRegistrationDTO.businessName'] != null}">
												<p class="errorBox">
											</c:when>
											<c:otherwise>
												<p class="paddingBtm" >
											</c:otherwise>
										</c:choose>
											<label>
													Business Name <span class="req">*</span>
											</label>
											<br />
										
												<s:textfield name="buyerRegistrationDTO.businessName"
													value="%{buyerRegistrationDTO.businessName}" maxlength="100"
													 cssStyle="width: 250px;"
													cssClass="shadowBox grayText"></s:textfield>
										
										</td>
										<td width="325">
										<p></p>
											
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
												Main Business Phone <span class="req">*</span>
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
											
										</td>
									</tr>
									<tr>
										<td>
											<c:choose>
											<c:when test="${fieldErrors['buyerRegistrationDTO.businessStructure'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												Business Structure <span class="req">*</span>
											</label>
											<br />
											<s:select name="buyerRegistrationDTO.businessStructure"
												value="%{buyerRegistrationDTO.businessStructure}"
												list="buyerRegistrationDTO.businessStructureList" headerKey=""
												headerValue="---------- Please Select ---------"
												listKey="id" listValue="descr" cssStyle="width: 256px;"
												cssClass="grayText" />
											</p>
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
												Business Started <span class="req">*</span>
											</label>
										 <input type="text" 
        							class="shadowBox" id="modal2ConditionalChangeDate1" 
									name="buyerRegistrationDTO.businessStarted" 
	 								value="<s:property value="%{buyerRegistrationDTO.businessStarted}"/>" />
											</p>
											
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
												Primary Industry <span class="req">*</span>
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
											<s:textfield name="buyerRegistrationDTO.websiteAddress" value="%{buyerRegistrationDTO.websiteAddress}"
												 maxlength="255" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
											
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
												Size of Company <span class="req">*</span>
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
												Annual Sales  Revenue <span class="req">*</span>
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
												Street Name <span class="req">*</span>
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
															City <span class="req">*</span>
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
														</c:choose>
														<label>
															ZIP <span class="req">*</span>
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
									<s:checkbox name="buyerRegistrationDTO.mailAddressChk" id = "mailAddressChk"
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
												Street Name <span class="req">*</span>
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
											</c:choose>
											<label>
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
											</c:choose>

														<label>
															City <span class="req">*</span>
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
													</c:choose>
														<label>
															ZIP <span class="req">*</span>
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
							<!-- NEW MODULE/ WIDGET-->
							<div class="darkGrayModuleHdr">
								Primary Contact Information - Administrator
							</div>
							<div class="grayModuleContent">
							<br/>
							By opening this account, you become the ServiceLive administrator for your company. You'll be the primary
							contact for all communications with ServiceLive and you will be authorized to enroll additional service
							buyers and support staff for your company.	
								<table cellpadding="0" cellspacing="0" width="650">
									<tr>
										<td width="325">
										<c:choose>
											<c:when
											test="${fieldErrors['buyerRegistrationDTO.roleWithinCom'] == null}">
											<p class="paddingBtm">
											</c:when>
											<c:otherwise>
											<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												Role within Company <span class="req">*</span>
											</label>
											<s:select name="buyerRegistrationDTO.roleWithinCom"
												value="%{buyerRegistrationDTO.roleWithinCom}"
												list="buyerRegistrationDTO.roleWithinCompanyList" headerKey=""
												headerValue="-- Please Select --" listKey="id"
												listValue="descr" cssStyle="width: 256px;"
												cssClass="grayText"  />
											</p>
										</td>
										<td width="325">
											<p>
												<label>
													Job Title
												</label>
												<br />
												<s:textfield name="buyerRegistrationDTO.jobTitle"
													value="%{buyerRegistrationDTO.jobTitle}"
													 maxlength="50" cssStyle="width: 250px;"
													cssClass="shadowBox grayText" />
												
											</p>
										</td>
									</tr>
									
									<tr>
										<td>
											<c:choose>
											<c:when
											test="${fieldErrors['buyerRegistrationDTO.firstName'] == null}">
											<p class="paddingBtm">
											</c:when>
											<c:otherwise>
											<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												First Name <span class="req">*</span>
											</label>
											<br />
											<s:textfield name="buyerRegistrationDTO.firstName"
												value="%{buyerRegistrationDTO.firstName}"
												 maxlength="50" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
											</p>
										</td>
										<td>
											<p>
												<label>
													Middle Name
												</label>
												<br />
												<s:textfield name="buyerRegistrationDTO.middleName"
													value="%{buyerRegistrationDTO.middleName}"
													 maxlength="50" cssStyle="width: 250px;"
													cssClass="shadowBox grayText" />
												
											</p>
										</td>
									</tr>
									<tr>
										<td>
											<!--  p class="errorBox" -->
											<c:choose>
											<c:when
											test="${fieldErrors['buyerRegistrationDTO.lastName'] == null}">
											<p class="paddingBtm">
											</c:when>
											<c:otherwise>
											<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												Last Name <span class="req">*</span>
											</label>
											<br />
											<s:textfield name="buyerRegistrationDTO.lastName"
												value="%{buyerRegistrationDTO.lastName}"
												 maxlength="50" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
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
												<s:textfield name="buyerRegistrationDTO.nameSuffix"
													value="%{buyerRegistrationDTO.nameSuffix}"
													 maxlength="10" cssStyle="width: 100px;"
													cssClass="shadowBox grayText" />
												
											</p>
										</td>
									</tr>
									<tr>
											<c:choose>
											<c:when
											test="${fieldErrors['buyerRegistrationDTO.busPhoneNo1'] == null}">
											<p class="paddingBtm">
											</c:when>
											<c:otherwise>
											<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												Business Phone <span class="req">*</span>
											</label>
											<br />
											<s:textfield name="buyerRegistrationDTO.busPhoneNo1"
												value="%{buyerRegistrationDTO.busPhoneNo1}"
												 maxlength="3"
												cssStyle="width: 30px;" cssClass="shadowBox grayText" />
											-
											<s:textfield name="buyerRegistrationDTO.busPhoneNo2"
												value="%{buyerRegistrationDTO.busPhoneNo2}"
												 maxlength="3"
												cssStyle="width: 30px;" cssClass="shadowBox grayText" />
											-

											<s:textfield name="buyerRegistrationDTO.busPhoneNo3"
												value="%{buyerRegistrationDTO.busPhoneNo3}"
												 maxlength="4"
												cssStyle="width: 45px;" cssClass="shadowBox grayText" />
												<label>
												Ext.
												</label>
												<s:textfield name="buyerRegistrationDTO.busExtn"
												value="%{buyerRegistrationDTO.busExtn}"
												 maxlength="4"
												cssStyle="width: 45px;" cssClass="shadowBox grayText" />
										
												<td>&nbsp;</td>
											<br/>
									</tr>
									<tr>
											<c:choose>
											<c:when
											test="${fieldErrors['buyerRegistrationDTO.mobPhoneNo1'] == null}">
											<p class="paddingBtm">
											</c:when>
											<c:otherwise>
											<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												Mobile Phone
											</label>
											<br />
											<s:textfield name="buyerRegistrationDTO.mobPhoneNo1"
												value="%{buyerRegistrationDTO.mobPhoneNo1}"
												 maxlength="3"
												cssStyle="width: 30px;" cssClass="shadowBox grayText" />
											-
											<s:textfield name="buyerRegistrationDTO.mobPhoneNo2"
												value="%{buyerRegistrationDTO.mobPhoneNo2}"
												 maxlength="3"
												cssStyle="width: 30px;" cssClass="shadowBox grayText" />
											-

											<s:textfield name="buyerRegistrationDTO.mobPhoneNo3"
												value="%{buyerRegistrationDTO.mobPhoneNo3}"
												 maxlength="4"
												cssStyle="width: 45px;" cssClass="shadowBox grayText" />
												<td>&nbsp;</td>
											<br/>
									</tr>
									
									<tr>
										<td>
											<c:choose>
											<c:when
											test="${fieldErrors['buyerRegistrationDTO.email'] == null}">
											<p class="paddingBtm">
											</c:when>
											<c:otherwise>
											<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												E-mail Address <span class="req">*</span>
											</label>
											<br />
											<s:textfield name="buyerRegistrationDTO.email"
												value="%{buyerRegistrationDTO.email}"
												 maxlength="255" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
											</p>
										</td>
										<td>
											<c:choose>
											<c:when
											test="${fieldErrors['buyerRegistrationDTO.confirmEmail'] == null}">
											<p class="paddingBtm">
											</c:when>
											<c:otherwise>
											<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												Confirm E-mail Address <span class="req">*</span>
											</label>
											<br />
											<s:textfield name="buyerRegistrationDTO.confirmEmail"
												value="%{buyerRegistrationDTO.confirmEmail}"
												 maxlength="255" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
											</p>
										</td>
									</tr>
									<tr>
										<td>
											<!--  p class="errorBox"-->
											<c:choose>
											<c:when
											test="${fieldErrors['buyerRegistrationDTO.altEmail'] == null}">
											<p class="paddingBtm">
											</c:when>
											<c:otherwise>
											<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												Alternate E-mail Address
											</label>
											<br />
											<s:textfield name="buyerRegistrationDTO.altEmail"
												value="%{buyerRegistrationDTO.altEmail}"
												 maxlength="255" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
											<br>
											<!-- span class="errorMsg">Invalid E-mail Address.  Please try again.</span-->
											</p>
										</td>
										<td>
											<c:choose>
											<c:when
											test="${fieldErrors['buyerRegistrationDTO.confAltEmail'] == null}">
											<p class="paddingBtm">
											</c:when>
											<c:otherwise>
											<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												Confirm Alternate E-mail Address
											</label>
											<br />
											<s:textfield name="buyerRegistrationDTO.confAltEmail"
												value="%{buyerRegistrationDTO.confAltEmail}"
												 maxlength="255" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
											</p>
										</td>
									</tr>
									<tr>
										<td>
										Create a unique user name that you can use to log into the system to create and post service orders.
											<c:choose>
											<c:when
											test="${fieldErrors['buyerRegistrationDTO.userName'] == null}">
											<p class="paddingBtm">
											</c:when>
											<c:otherwise>
											<p class="errorBox">
											</c:otherwise>
											</c:choose> 
											<label>
												User Name <span class="req">*</span>
											</label>
											<br />
											<s:textfield name="buyerRegistrationDTO.userName"
												maxlength="30"
												value="%{buyerRegistrationDTO.userName}"
												 cssStyle="width: 250px;"
												cssClass="shadowBox grayText"/>
											</p>
										</td>
										<td>
											<br />
											(User name must be 8 characters or more)
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<c:choose>
											<c:when
											test="${fieldErrors['buyerRegistrationDTO.confirmUserName'] == null}">
											<p class="paddingBtm">
											</c:when>
											<c:otherwise>
											<p class="errorBox">
											</c:otherwise>
											</c:choose> 
											<label>Confirm User Name <span class="req">*</span></label>
											<br />
											<s:textfield name="buyerRegistrationDTO.confirmUserName"
												maxlength="30"
												value="%{buyerRegistrationDTO.confirmUserName}"
												 cssStyle="width: 250px;"
												cssClass="shadowBox grayText"/>
											</p>
										</td>
									</tr>
									<tr>
										<td colspan = 2>We always like finding out how buyers found out about us. If you have a promotional code, please enter it here.</td>
									</tr>
									<tr>
										<td>
											<c:choose>
											<c:when
											test="${fieldErrors['buyerRegistrationDTO.howDidYouHear'] == null}">
											<p class="paddingBtm">
											</c:when>
											<c:otherwise>
											<p class="errorBox">
											</c:otherwise>
											</c:choose> 
												
											How did you hear about ServiceLive?
											<br>
											<s:select name="buyerRegistrationDTO.howDidYouHear"
												value="%{buyerRegistrationDTO.howDidYouHear}"
												list="buyerRegistrationDTO.howDidYouHearList" headerKey=""
												headerValue="-- Please Select --" listKey="id"
												listValue="descr" cssStyle="width: 256px;"
												cssClass="shadowBox grayText">

											</s:select>
											</p>
										</td>
										<td>
											<c:choose>
											<c:when
											test="${fieldErrors['buyerRegistrationDTO.promotionCode'] == null}">
											<p class="paddingBtm">
											</c:when>
											<c:otherwise>
											<p class="errorBox">
											</c:otherwise>
											</c:choose>
											
											<label>
												Referral Code
											</label>
											<br />SL- 
											<a href="jsp/public/simple/tooltips/tooltip.jsp?bundlekey=simplebuyer.referralcode.help.text&width=367" 
											id="promotionCodehelp" class="jTip" name="Helpful Information" >	
												<s:textfield name="buyerRegistrationDTO.promotionCode"
												value="%{buyerRegistrationDTO.promotionCode}"
												maxlength="10" cssStyle="width: 250px;"
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
						<div class="darkGrayModuleHdr">
								Site Terms & Conditions
							</div>
							<div class="grayModuleContent mainContentWell">
							Please read the following <a href="/MarketFrontend/termsAndConditions_displayBuyerAgreement.action" onclick="window.open(this.href,'terms','width=1040,height=640,scrollbars,resizable'); return false;">terms and conditions</a>. After accepting, you'll receive an e-mail confirming your account. Use the temporary password included in that e-mail to log back onto the system so you can register other users for your firm or begin creating a service order.
							<br/> <br/>
						<p><s:checkbox name="buyerRegistrationDTO.termsAndCondition" id="termsAndConditionID" value="%{buyerRegistrationDTO.termsAndCondition}" cssStyle="vertical-align:middle;"/>
						<label>I agree to the <a href="/MarketFrontend/termsAndConditions_displayBuyerAgreement.action" onclick="window.open(this.href,'terms','width=1040,height=640,scrollbars,resizable'); return false;">Buyer Terms and Conditions</a></label>
						</p>
						<p><s:checkbox name="buyerRegistrationDTO.serviceLiveBucksInd" id="serviceLiveBucksInd" value="%{buyerRegistrationDTO.serviceLiveBucksInd}" cssStyle="vertical-align:middle;"/>
						<label>I agree to the <a href="#" class="openBucks">Payment Services*</a> offered on ServiceLive</label>
						</p>
							</div>
							<div id="serviceLiveBucks" class="jqmWindow" style="position: relative; top: -300px;">
							<div style="margin: 10px;height:300px;overflow:auto"> ${buyerRegistrationDTO.serviceLiveBucksText}</div>
								<p align="center">
									<input type="button" value="Agree" id="providerServiceLiveBucksAgreeBtn"  onclick="agreeBucks();" class="jqmClose"/>
									&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="Cancel" id="cancelBtn" class="jqmClose" />
								</p>
							</div>
							<div class="clearfix">
								<div class="formNavButtons2">

									<s:submit type="image" id="submitPageID"
										src="%{#request['staticContextPath']}/images/common/spacer.gif"
										cssStyle="background-image:url(%{#request['staticContextPath']}/images/btn/submitReg_whiteBg.gif);width:180px;height:20px"
										cssClass="btn20Bevel" method="submit"/>

								</div>
							</div>
						</div>
					</div>
				</s:form>
				<p style="clear:both;">*<fmt:message bundle="${serviceliveCopyBundle}" key="buyer_registration_sl_bucks_powered" /></p>
			</div>
			
			<div>
			
			
			<!-- START FOOTER -->
			<jsp:include page="/jsp/public/common/blackoutFooter.jsp" />
			<!-- END FOOTER -->
		</div>
<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
	 <jsp:param name="PageName" value="BuyerRegistration"/>
</jsp:include>	

	</body>
</html>