<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd"> 
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", -1);
%>
<html>
	<head>
	<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
		<style type="text/css">
		@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
		@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
		
		</style>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/slider.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
		<link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery.modal.min.css" type="text/css"></link>
		<style type="text/css" media="screen">
			.modal{
				padding: 0px 0px 0px 0px;
				width: 100%;
				max-width: 625px;
			}
		</style>
		

		<script language="JavaScript" src="${staticContextPath}/javascript/js_registration/tooltip.js" type="text/javascript">
		</script>
		<script language="JavaScript" src="${staticContextPath}/javascript/js_registration/formfields.js" type="text/javascript">
		</script>
		<script language="JavaScript" src="${staticContextPath}/javascript/js_registration/tooltip.js" type="text/javascript"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script> 
		<script type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script> 
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/ui.core.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/ui.tabs.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/ui.datepicker.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.mask.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.resetform.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery-form.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.tablesorter.min.js"></script>

		<c:set var="BUTTON_TYPE_EDIT" value="<%=OrderConstants.BUTTON_TYPE_EDIT%>"></c:set>
		<c:set var="BUTTON_TYPE_ADD" value="<%=OrderConstants.BUTTON_TYPE_ADD%>"></c:set>
		<c:set var="CBGLI_BTN_IND" value="<%=(String)request.getAttribute(OrderConstants.CBGLI_BTN_IND)%>"></c:set>
		<c:set var="VLI_BTN_IND" value="<%=(String)request.getAttribute(OrderConstants.VLI_BTN_IND)%>"></c:set>
		<c:set var="WCI_BTN_IND" value="<%=(String)request.getAttribute(OrderConstants.WCI_BTN_IND)%>"></c:set>
		<c:set var="FROM_MODAL_IND" value="<%=(String)session.getAttribute(OrderConstants.FROM_MODAL_IND)%>"></c:set>
		<c:set var="FROM_MODAL_IND_VAL" value="<%=(String)request.getAttribute(OrderConstants.FROM_MODAL_IND_VAL)%>"></c:set>
		<c:set var="isPA" value="${sessionScope.tabDto.tabStatus['Insurance'] != null || isFromPA}"/>
		
	</head>
	<script type="text/javascript">
		jQuery(document).ready( function ($) {
		});
	</script>	
	<!-- NEW MODULE/ WIDGET-->
	<body >
		 <div id="content_right_header_text" >
			<%@ include file="../message.jsp"%>
		</div>
		<s:form action="addInsuranceAction" name="insuranceForm"
			id="insuranceForm" theme="simple" enctype="multipart/form-data">
			<s:hidden name="cbgliBtn_type" value=""></s:hidden>
			<s:hidden name="vliBtn_type" value=""></s:hidden>
			<s:hidden name="wciBtn_type" value=""></s:hidden>

			<input type="hidden" id="insuranceInfoDto.WCI" name="insuranceInfoDto.WCI" value="${insuranceInfoDto.WCI}"/>
			<input type="hidden" id="insuranceInfoDto.VLI" name="insuranceInfoDto.VLI" value="${insuranceInfoDto.VLI}"/>
			<input type="hidden" id="insuranceInfoDto.CBGLI" name="insuranceInfoDto.CBGLI" value="${insuranceInfoDto.CBGLI}"/>
			<p><b>An asterix (<span class="req">*</span>) indicates a required field</b></p> <br/>
			<div class="darkGrayModuleHdr" >
				Insurance Requirements
			</div>
			<s:iterator id="insuranceObj" value="insuranceInfoDto.insuranceList" status="inner">
				<s:if test="%{'General Liability'  == categoryName}">
					<c:set var="CBGLIislabel" value="false" />
					<!-- status not null -->
					<c:if test = "${not empty wfStateId && wfStateId > 0 }">
					  			<c:set var="CBGLIislabel" value="true" />	
					</c:if>
				</s:if>
				
				<s:elseif test="%{'Auto Liability'  == categoryName}">
					<c:set var="VLIislabel" value="false" />
					<!-- status not null -->
					<c:if test = "${not empty wfStateId && wfStateId > 0 }">
					  			<c:set var="VLIislabel" value="true" />	
					</c:if>	
				</s:elseif>
				
				<s:elseif test="%{'Workman\\'s Compensation'  == categoryName}">
					<c:set var="WCIislabel" value="false" />
					<!-- status not null -->
					<c:if test = "${not empty wfStateId && wfStateId > 0 }">
					  			<c:set var="WCIislabel" value="true" />	
					</c:if>		
				</s:elseif>
			</s:iterator>
			<div class="grayModuleContent mainContentWell"
				style="height:450px; overflow:auto;" >
				<table class="insuranceReqsTable" cellpadding="0" cellspacing="0">
					<tr>
						<td>
						<p class="paddingBtm">
							<fmt:message bundle="${serviceliveCopyBundle}" key="insurance.details.information.msg"/>
						</p>
						</td>
					</tr>
					<%-- General Liability Insurance code  --%>
				  	<tr class="bottom">
						<td >
							<p class="title">						
								<fmt:message bundle="${serviceliveCopyBundle}" key="generalLiabilty.insurance.hdr.txt"/>
							</p>
						</td> 					
					</tr>
					<br/>					
					<tr>
						<td>
							<s:radio id="insuranceInfoDto.CBGLI" name="insuranceInfoDtoCBGLI"
								value="%{insuranceInfoDto.CBGLI}" tabindex="3"
								onclick="showCBGLIAmount();"
								list="#{1:' Yes,'}" />
								<fmt:message bundle="${serviceliveCopyBundle}" key="insurance.certificate.agreement.msg"/>
						</td>
					</tr>
					<tr>
						<td>
							<c:choose>
							<c:when
								test="${fieldErrors['insuranceInfoDto.CBGLIAmount'] == null}">
								<p class="policy" id="textCBGLI" style="display:block" >									
							</c:when> 
							<c:otherwise>
							<p class="insErrorBox" id="textCBGLI">
							</c:otherwise>
							</c:choose>
					<c:choose>	
					<c:when test ="${CBGLIislabel}">	
							<c:choose>								
							<c:when
								test="${insuranceInfoDto.CBGLI != null && insuranceInfoDto.CBGLI == '0'}">
									<strong>Amount $</strong>																	
									<label id="lblCBGLIAmount" style="width:100px;" >&nbsp;0</label>									
									<s:hidden name="insuranceInfoDto.CBGLIAmount"
									id="CBGLIAmount" value="0"  ></s:hidden>
									<s:hidden name="hdn_CBGLIAmount" value="0"></s:hidden>
							</c:when>							
							<c:when
								test="%{insuranceInfoDto.CBGLIAmount==0 or insuranceInfoDto.CBGLIAmount== null || insuranceInfoDto.CBGLIAmount== ''}">
									<strong>Amount $</strong>																	
									<label id="lblCBGLIAmount" style="width:100px;" >&nbsp;</label>									
									<s:hidden name="insuranceInfoDto.CBGLIAmount"
									id="CBGLIAmount"  ></s:hidden>
									<s:hidden name="hdn_CBGLIAmount" value=""></s:hidden>
							</c:when>
							<c:otherwise>
								<c:catch var="formatError">
									<fmt:formatNumber var="tempvar1" minFractionDigits="2"
										maxFractionDigits="2" groupingUsed="true"
										value="${insuranceInfoDto.CBGLIAmount}" pattern="#,###,###" />
										<strong>Amount $</strong>
										<label id="lblCBGLIAmount" style="width:100px;">${tempvar1}</label>	
																			
										<input type="hidden" name="insuranceInfoDto.CBGLIAmount"
										id="CBGLIAmount"  value="${tempvar1}"/>
										<input id="insuranceForm_hdn_CBGLIAmount" type="hidden" name="hdn_CBGLIAmount" value="${tempvar1}"/>
								</c:catch>
								<c:if test="${not empty  Error}">
										<strong>Amount $</strong>
										<label id="lblCBGLIAmount" style="width:100px;">${insuranceInfoDto.CBGLIAmount}</label>									
										<input type="hidden" name="insuranceInfoDto.CBGLIAmount"
										id="CBGLIAmount"  value="${insuranceInfoDto.CBGLIAmount}"/>
										<input id="insuranceForm_hdn_CBGLIAmount" type="hidden" name="hdn_CBGLIAmount" value="${insuranceInfoDto.CBGLIAmount}"/>
								</c:if>
							</c:otherwise>
							</c:choose>
					</c:when>
					<c:otherwise>
							<c:choose>
							<c:when
								test="${insuranceInfoDto.CBGLI != null && insuranceInfoDto.CBGLI == '0'}">
									<strong>Amount $</strong>
									<s:textfield name="insuranceInfoDto.CBGLIAmount"
									id="CBGLIAmount"  value="0"  disabled="true" maxlength="15" tabindex="4"
									cssStyle="width: 100px;"></s:textfield>									
									<span class="req">*</span>
									<s:hidden name="hdn_CBGLIAmount" value="0"></s:hidden>
							</c:when>
							<c:when
								test="%{insuranceInfoDto.CBGLIAmount==0 or insuranceInfoDto.CBGLIAmount== null || insuranceInfoDto.CBGLIAmount== ''}">
									<strong>Amount $</strong>
									<s:textfield name="insuranceInfoDto.CBGLIAmount"
									id="CBGLIAmount"  value="" maxlength="15" tabindex="4"
									cssStyle="width: 100px;"></s:textfield>									
									<span class="req">*</span>
									<s:hidden name="hdn_CBGLIAmount" value=""></s:hidden>
							</c:when>
							<c:otherwise>
								<c:catch var="formatError1">
									<fmt:formatNumber var="tempvar1" minFractionDigits="2"
										maxFractionDigits="2" groupingUsed="true"
										value="${insuranceInfoDto.CBGLIAmount}" pattern="#,###,###" />
										<strong>Amount $</strong>
										<input type="text" name="insuranceInfoDto.CBGLIAmount"
										id="CBGLIAmount" value="${tempvar1}" maxlength="15"
										tabindex="4" Style="width: 100px;"/>
										<span class="req">*</span>
										<input id="insuranceForm_hdn_CBGLIAmount" type="hidden" name="hdn_CBGLIAmount" value="${tempvar1}"/>
								</c:catch>
								<c:if test = "${formatError1 != null}">
								   	<strong>Amount $</strong>
									<input type="text" name="insuranceInfoDto.CBGLIAmount"
									id="CBGLIAmount" value="${insuranceInfoDto.CBGLIAmount}" maxlength="15"
									tabindex="4" Style="width: 100px;"/>
									<span class="req">*</span>
									<input id="insuranceForm_hdn_CBGLIAmount" type="hidden" name="hdn_CBGLIAmount" value="${tempvar1}"/>
								</c:if>
								<c:if test="${not empty  Error}">
										<strong>Amount $</strong>
										<s:textfield name="insuranceInfoDto.CBGLIAmount"
										id="CBGLIAmount" value="%{insuranceInfoDto.CBGLIAmount}"
										maxlength="15" tabindex="4" cssStyle="width: 100px;"></s:textfield>
										<span class="req">*</span>
										<input id="insuranceForm_hdn_CBGLIAmount" type="hidden" name="hdn_CBGLIAmount" value="${insuranceInfoDto.CBGLIAmount}"/>
								</c:if>
							</c:otherwise>
							</c:choose>
						
					</c:otherwise>
					</c:choose>
							</p>
							<s:iterator id="insuranceObj"
								value="insuranceInfoDto.insuranceList" status="inner">
								<s:if test="%{'General Liability'  == categoryName}">
									<c:set var="buttonType1" value="${buttonType}" />
									<c:set var="currentDocId1" value="${currentDocumentId}" />
									<c:set var="currentDocName1" value="${fn:replace(fn:replace(docURL,'&', '&amp;'),'\','&#39;')}" />
									<c:set var="cbgliCredId" value="${vendorCredentialId}" />
									<c:set var="cbgliCategoryName" value="${categoryName}" />
									<p class="statdoc">
										<s:if test="%{wfStateId == '13'}">
											<img src="${staticContextPath}/images/icons/incIconOn.gif"
												width="10" height="10" alt="">
										</s:if>
										<s:elseif test="%{wfStateId =='14'}">
											<img src="${staticContextPath}/images/icons/greenCheck.gif"
												width="10" height="10" alt="">
										</s:elseif>
										<s:elseif test="%{wfStateId =='25'}">
											<img src="${staticContextPath}/images/icons/errorIcon.gif"
												width="10" height="10" alt="">
										</s:elseif>
										<s:elseif test="%{wfStateId =='210'}">
											<img src="${staticContextPath}/images/icons/errorIcon.gif"
												width="10" height="10" alt="">
										</s:elseif>
										<strong>Status:</strong>
										<span id="cbgli_cred_status">${statusDesc}</span>
										&nbsp;
										<c:choose>
										<c:when test="${currentDocId1 > '0'}">
											<strong>Document:</strong>
											<span> 
												<a href="jsp/providerRegistration/processInsuranceAction_displayTheDocument.action?docId=<s:property value="currentDocumentId"/>&credId=<s:property value="vendorCredentialId"/>"
												target="blank"><s:property value="docURL" />
												</a>
											</span>
										</c:when>
										<c:otherwise>
											<strong>Document:</strong>
											<span id="cbgli_cred_status">None Attached</span>
										</c:otherwise>
										</c:choose>
									</p>
								</s:if>
							</s:iterator>
						</td>
					</tr>		
					<tr>
						<td>
							<p class="smallFont">
								<fmt:message bundle="${serviceliveCopyBundle}" key="generalLiabilty.insurance.required"/>
							</p>
						</td>
					</tr>					
					<tr>
						<td>
							<div class="attach">
								<span>
									<fmt:message bundle="${serviceliveCopyBundle}" key="attach.insurabceCertificate.msg"/>
								</span>
								<c:choose>
								<c:when test="${BUTTON_TYPE_ADD==buttonType1 && insuranceInfoDto.CBGLI != null && insuranceInfoDto.CBGLI == '1'}">
									<input type="button" value="Attach" id="CBGLIButton"
										onClick="handlePolicyModal('${insuranceInfoDto.CBGLIAmount}','${BUTTON_TYPE_ADD}',${cbgliCredId},'General Liability','','');" />
								</c:when>
								<c:when test="${BUTTON_TYPE_ADD==buttonType1 && insuranceInfoDto.CBGLI != null && insuranceInfoDto.CBGLI == '0'}">
									<input type="button" value="Attach" id="CBGLIButton" disabled="disabled"	
										onClick="handlePolicyModal('${insuranceInfoDto.CBGLIAmount}','${BUTTON_TYPE_ADD}',${cbgliCredId},'General Liability','','');" />
								</c:when>
								<c:when test="${BUTTON_TYPE_EDIT==buttonType1 && insuranceInfoDto.CBGLI != null && insuranceInfoDto.CBGLI == '0' }">
									<input type="button" value="Edit" id="CBGLIButton" disabled="disabled"										
										onClick='handlePolicyModal("${insuranceInfoDto.CBGLIAmount}","${BUTTON_TYPE_EDIT}",${cbgliCredId},"General Liability","${currentDocId1}","${currentDocName1}");' />
								</c:when>
								<c:when test="${BUTTON_TYPE_EDIT==buttonType1 && insuranceInfoDto.CBGLI != null && insuranceInfoDto.CBGLI == '1'}">
									<input type="button" value="Edit" id="CBGLIButton"
										onClick='handlePolicyModal("${insuranceInfoDto.CBGLIAmount}","${BUTTON_TYPE_EDIT}",${cbgliCredId},"General Liability","${currentDocId1}","${currentDocName1}");' />
								</c:when>
								<c:when test="${insuranceInfoDto.CBGLI == null || insuranceInfoDto.CBGLI == '0'}">
									<input type="button" value="Attach" id="CBGLIButton" disabled="disabled"										
										onclick="handlePolicyModal('0.0','${BUTTON_TYPE_ADD}',${cbgliCredId},'${cbgliCategoryName}','','');" />
								</c:when>
								</c:choose>
							</div>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:radio id="insuranceInfoDto.CBGLI" name="insuranceInfoDtoCBGLI"
								value="%{insuranceInfoDto.CBGLI}" tabindex="3"
								onclick="disableCBGLIBtn();"
								list="#{0:' No,'}" />
								<fmt:message bundle="${serviceliveCopyBundle}" key="generalLiabilty.insurance.notRequired"/>
						</td>
					</tr>
				
					<tr><td><br><hr/><br></td></tr>
					<%-- Vehicle Liability Insurance code  --%>
					<tr class="bottom">
						<td>
							<p class="title">
								<fmt:message bundle="${serviceliveCopyBundle}" key="vehicleLiabilty.insurance.hdr.txt"/>
							</p>
						</td>
					</tr>
					<br/>
					<tr>
						<td>
							<s:radio id="insuranceInfoDto.VLI" name="insuranceInfoDtoVLI"
								value="%{insuranceInfoDto.VLI}" tabindex="3"
								onclick="showVLIAmount();"
								list="#{1:' Yes,'}" />
								<fmt:message bundle="${serviceliveCopyBundle}" key="insurance.certificate.agreement.msg"/>
						</td>
					</tr>
					<tr>
						<td>
							<c:choose>
							<c:when test="${fieldErrors['insuranceInfoDto.VLIAmount'] == null}">
								<p class="policy" id="textVLI" "style=display:block" >									
							</c:when>
							<c:otherwise>
								<p class="insErrorBox" id="textVLI">
							</c:otherwise>
							</c:choose>
					
					<c:choose>		
					<c:when test = "${VLIislabel}">		
							<c:choose>							
							<c:when
								test="${insuranceInfoDto.VLI != null && insuranceInfoDto.VLI == '0'}">
									<strong>Amount $</strong>
									<label id="lblVLIAmount" style="width:100px;" >&nbsp;0</label>
									<s:hidden name="insuranceInfoDto.VLIAmount" id="VLIAmount" 
									value="" ></s:hidden>
									<s:hidden name="hdn_VLIAmount" value="0"></s:hidden>
							</c:when>
							<c:when
								test="%{insuranceInfoDto.VLIAmount==0 or insuranceInfoDto.VLIAmount== null || insuranceInfoDto.VLIAmount== ''}">
									<strong>Amount $</strong>
									<label id="lblVLIAmount" style="width:100px;" >&nbsp;</label>
									<s:hidden name="insuranceInfoDto.VLIAmount" id="VLIAmount" 
									value="" ></s:hidden>
									<s:hidden name="hdn_VLIAmount" value=""></s:hidden>
							</c:when>
							<c:otherwise>
								<c:catch var="formatError">
									<fmt:formatNumber var="tempvar1" minFractionDigits="2"
										maxFractionDigits="2" groupingUsed="true"
										value="${insuranceInfoDto.VLIAmount}" pattern="#,###,###" />
										<strong>Amount $</strong>
										<label id="lblVLIAmount" style="width:100px;" >${tempvar1}</label>
										<input type="hidden" name="insuranceInfoDto.VLIAmount" id="VLIAmount" 
										value="${tempvar1}" />
										<input type="hidden" id="insuranceForm_hdn_VLIAmount" name="hdn_VLIAmount" value="${tempvar1}"/>
								</c:catch>
								<c:if test="${not empty  Error}">
										<strong>Amount $</strong>
										<label id="lblVLIAmount" style="width:100px;" >${insuranceInfoDto.VLIAmount}</label>
										<input type="hidden" name="insuranceInfoDto.VLIAmount" id="VLIAmount" 
										value="${insuranceInfoDto.VLIAmount}" />
										<input type="hidden" id="insuranceForm_hdn_VLIAmount" name="hdn_VLIAmount" value="${insuranceInfoDto.VLIAmount}"/>
								</c:if>
							</c:otherwise>
							</c:choose>
					</c:when>
					<c:otherwise>
							<c:choose>
							<c:when
								test="${insuranceInfoDto.VLI != null && insuranceInfoDto.VLI == '0'}">
									<strong>Amount $</strong>
									<s:textfield name="insuranceInfoDto.VLIAmount" id="VLIAmount"  
									value="0" disabled="true" maxlength="15" tabindex="4" cssStyle="width: 100px;"></s:textfield>
									<span class="req">*</span>
									<s:hidden name="hdn_VLIAmount" value="0"></s:hidden>
							</c:when>
							<c:when
								test="%{insuranceInfoDto.VLIAmount==0 or insuranceInfoDto.VLIAmount== null || insuranceInfoDto.VLIAmount== ''}">
									<strong>Amount $</strong>
									<s:textfield name="insuranceInfoDto.VLIAmount" id="VLIAmount"  
									value="" maxlength="15" tabindex="4" cssStyle="width: 100px;"></s:textfield>
									<span class="req">*</span>
									<s:hidden name="hdn_VLIAmount" value=""></s:hidden>
							</c:when>
							<c:otherwise>
								<c:catch var="formatError2">
									<fmt:formatNumber var="tempvar1" minFractionDigits="2"
										maxFractionDigits="2" groupingUsed="true"
										value="${insuranceInfoDto.VLIAmount}" pattern="#,###,###" />
										<strong>Amount $</strong>
										<input type="text" name="insuranceInfoDto.VLIAmount" id="VLIAmount" 
										value="${tempvar1}" maxlength="15" tabindex="4"
										Style="width: 100px;"/>
										<span class="req">*</span>
										<input type="hidden" id="insuranceForm_hdn_VLIAmount" name="hdn_VLIAmount" value="${tempvar1}"/>
								</c:catch>
								<c:if test = "${formatError2 != null}">
								   	<strong>Amount $</strong>
									<input type="text" name="insuranceInfoDto.VLIAmount" id="VLIAmount" 
									value="${insuranceInfoDto.VLIAmount}" maxlength="15" tabindex="4"
									Style="width: 100px;"/>
									<span class="req">*</span>
									<input type="hidden" id="insuranceForm_hdn_VLIAmount" name="hdn_VLIAmount" value="${tempvar1}"/>
								</c:if>
								<c:if test="${not empty  Error}">
										<strong>Amount $</strong>
										<s:textfield name="insuranceInfoDto.VLIAmount" id="VLIAmount" 
										value="%{insuranceInfoDto.VLIAmount}" maxlength="15"
										tabindex="4" cssStyle="width: 100px;"></s:textfield>
										<span class="req">*</span>
										<input type="hidden" id="insuranceForm_hdn_VLIAmount" name="hdn_VLIAmount" value="${insuranceInfoDto.VLIAmount}"/>
								</c:if>
							</c:otherwise>
							</c:choose>
					</c:otherwise>
					</c:choose>		
							
							
							
							</p>
							<s:iterator id="insuranceObj"
								value="insuranceInfoDto.insuranceList" status="inner">
								<s:if test="%{'Auto Liability'  == categoryName}">
									<c:set var="buttonType2" value="${buttonType}" />
									<c:set var="currentDocId2" value="${currentDocumentId}" />
									<c:set var="currentDocName2" value="${fn:replace(fn:replace(docURL,'&', '&amp;'),'\','&#39;')}" /> 
									<c:set var="vliCredId" value="${vendorCredentialId}" />
									<c:set var="vliCategoryName" value="${categoryName}" />
									<p class="statdoc">
										<s:if test="%{wfStateId == '13'}">
											<img src="${staticContextPath}/images/icons/incIconOn.gif"
												width="10" height="10" alt="">
										</s:if>
										<s:elseif test="%{wfStateId =='14'}">
											<img src="${staticContextPath}/images/icons/greenCheck.gif"
												width="10" height="10" alt="">
										</s:elseif>
										<s:elseif test="%{wfStateId =='25'}">
											<img src="${staticContextPath}/images/icons/errorIcon.gif"
												width="10" height="10" alt="">
										</s:elseif>
										<s:elseif test="%{wfStateId =='210'}">
											<img src="${staticContextPath}/images/icons/errorIcon.gif"
												width="10" height="10" alt="">
										</s:elseif>
										<strong>Status:</strong>
										<span id="vli_cred_status">${statusDesc}</span>
										&nbsp;
										<c:choose>
										<c:when test="${currentDocId2 > '0'}">
											<strong>Document:</strong>
											<span> 
												<a href="jsp/providerRegistration/processInsuranceAction_displayTheDocument.action?docId=<s:property value="currentDocumentId"/>&credId=<s:property value="vendorCredentialId"/>"
												target="blank"><s:property value="docURL" />
												</a>
											</span>
										</c:when>
										<c:otherwise>
											<strong>Document:</strong>
											<span id="vli_cred_status">None Attached</span>
										</c:otherwise>
										</c:choose>
									</p>
								</s:if>
							</s:iterator>
						</td>
					</tr>
					<tr>
						<td>
							<p class="smallFont">
								<fmt:message bundle="${serviceliveCopyBundle}" key="vehicleLiabilty.insurance.required"/>
							</p>
						</td>
					</tr>
					<tr>
						<td>
							<div class="attach">
								<span>
									<fmt:message bundle="${serviceliveCopyBundle}" key="attach.insurabceCertificate.msg"/>
								</span>
								<c:choose>
								<c:when test="${BUTTON_TYPE_ADD==buttonType2 && insuranceInfoDto.VLI != null && insuranceInfoDto.VLI =='1'}">
									<input type="button" value="Attach" id="VLIButton" 
										onClick="handlePolicyModal('${insuranceInfoDto.VLIAmount}','${BUTTON_TYPE_ADD}',${vliCredId},'Auto Liability','','');" />
								</c:when>
								<c:when test="${BUTTON_TYPE_ADD==buttonType2 && insuranceInfoDto.VLI != null && insuranceInfoDto.VLI =='0'}">
									<input type="button" value="Attach" id="VLIButton" disabled="disabled"
										onClick="handlePolicyModal('${insuranceInfoDto.VLIAmount}','${BUTTON_TYPE_ADD}',${vliCredId},'Auto Liability','','');" />
								</c:when>
								<c:when test="${BUTTON_TYPE_EDIT==buttonType2 && insuranceInfoDto.VLI != null && insuranceInfoDto.VLI =='0'}">
									<input type="button" value="Edit" id="VLIButton" disabled="disabled"
										onClick='handlePolicyModal("${insuranceInfoDto.VLIAmount}","${BUTTON_TYPE_EDIT}",${vliCredId},"Auto Liability","${currentDocId2}","${currentDocName2}");' />
								</c:when>
								<c:when test="${BUTTON_TYPE_EDIT==buttonType2 && insuranceInfoDto.VLI != null && insuranceInfoDto.VLI =='1'}">
									<input type="button" value="Edit" id="VLIButton"
										onClick='handlePolicyModal("${insuranceInfoDto.VLIAmount}","${BUTTON_TYPE_EDIT}",${vliCredId},"Auto Liability","${currentDocId2}","${currentDocName2}");' />
								</c:when>
								<c:when test="${insuranceInfoDto.VLI == null || insuranceInfoDto.VLI =='0'}">
									<input type="button" value="Attach" id="VLIButton" disabled="disabled"
										onclick="handlePolicyModal('0.0','${BUTTON_TYPE_ADD}',${vliCredId},'${vliCategoryName}','','');" />
								</c:when>
								</c:choose>
							</div>
						</td>
					</tr>
					
					<tr>
						<td>
							<s:radio id="insuranceInfoDto.VLI" name="insuranceInfoDtoVLI"
								value="%{insuranceInfoDto.VLI}" tabindex="3"
								onclick="disableVLIBtn();"
								list="#{0:' No,'}" />
								<fmt:message bundle="${serviceliveCopyBundle}" key="vehicleLiabilty.insurance.notRequired"/>
						</td>
					</tr>	
					<tr><td><br><hr/><br></td></tr>
					<%-- Workers Compensation Insurance code  --%>
					<tr class="bottom">
						<td>
							<p class="title">
								<fmt:message bundle="${serviceliveCopyBundle}" key="workmansCompensation.insurance.hdr.txt"/>
							</p>
						</td>
					</tr>
					<br/>
					<tr>
						<td>
							<s:radio id="insuranceInfoDto.WCI" name="insuranceInfoDtoWCI"
								value="%{insuranceInfoDto.WCI}" tabindex="3"
								onclick="showWCIAmount();"
								list="#{1:' Yes,'}" />
								<fmt:message bundle="${serviceliveCopyBundle}" key="insurance.certificate.agreement.msg"/>
						</td>
					</tr>
					<tr>
						<td>
							<c:choose>
							<c:when test="${fieldErrors['insuranceInfoDto.WCIAmount'] == null}">
								<p class="policy" id="textWCI" "style=display:block" >
							</c:when>
							<c:otherwise>
								<p class="insErrorBox" id="textWCI">
							</c:otherwise>
							</c:choose>
					
					<c:choose>
					<c:when test ="${WCIislabel}">	
							<c:choose>								
							<c:when
								test="${insuranceInfoDto.WCI != null && insuranceInfoDto.WCI == '0'}">
									<strong>Amount $</strong>
									<label id="lblWCIAmount" style="width:100px;" >&nbsp;0</label>
									<s:hidden name="insuranceInfoDto.WCIAmount" id="WCIAmount"
									value="0" ></s:hidden>
									<s:hidden name="hdn_WCIAmount" value="0"></s:hidden>
							</c:when>
							<c:when
								test="%{insuranceInfoDto.WCIAmount==0 or insuranceInfoDto.WCIAmount== null || insuranceInfoDto.WCIAmount== ''}">
									<strong>Amount $</strong>
									<label id="lblWCIAmount" style="width:100px;" >&nbsp;</label>
									<s:hidden name="insuranceInfoDto.WCIAmount" id="WCIAmount"
									value="" ></s:hidden>
									<s:hidden name="hdn_WCIAmount" value=""></s:hidden>
							</c:when>
							<c:otherwise>
								<c:catch var="formatError">
									<fmt:formatNumber var="tempvar1" minFractionDigits="2"
										maxFractionDigits="2" groupingUsed="true"
										value="${insuranceInfoDto.WCIAmount}" pattern="#,###,###" />
										<strong>Amount $</strong>
										<label id="lblWCIAmount" style="width:100px;" >${tempvar1}</label>
										<input type="hidden" name="insuranceInfoDto.WCIAmount" id="WCIAmount" 
										value="${tempvar1}"/>
										<input type="hidden" id="insuranceForm_hdn_WCIAmount" name="hdn_WCIAmount" value="${tempvar1}"/>
								</c:catch>
								<c:if test="${not empty Error}">
										<strong>Amount $</strong>
										<label id="lblWCIAmount" style="width:100px;" >${insuranceInfoDto.WCIAmount}</label>
										<input type="hidden" name="insuranceInfoDto.WCIAmount" id="WCIAmount"
										value="${insuranceInfoDto.WCIAmount}" />
										<input type="hidden" id="insuranceForm_hdn_WCIAmount" name="hdn_WCIAmount" value="${insuranceInfoDto.WCIAmount}"/>
								</c:if>
							</c:otherwise>
							</c:choose>
					</c:when>
					<c:otherwise>
							<c:choose>
							<c:when
								test="${insuranceInfoDto.WCI != null && insuranceInfoDto.WCI == '0'}">
									<strong>Amount $</strong>
									<s:textfield name="insuranceInfoDto.WCIAmount" id="WCIAmount" 
									value="0" disabled="true" maxlength="15" tabindex="4" cssStyle="width: 100px;"></s:textfield>
									<span class="req">*</span>
									<s:hidden name="hdn_WCIAmount" value="0"></s:hidden>
							</c:when>
							<c:when
								test="%{insuranceInfoDto.WCIAmount==0 or insuranceInfoDto.WCIAmount== null || insuranceInfoDto.WCIAmount== ''}">
									<strong>Amount $</strong>
									<s:textfield name="insuranceInfoDto.WCIAmount" id="WCIAmount" 
									value="" maxlength="15" tabindex="4" cssStyle="width: 100px;"></s:textfield>
									<span class="req">*</span>
									<s:hidden name="hdn_WCIAmount" value=""></s:hidden>
							</c:when>
							<c:otherwise>
								<c:catch var="formatError3">
									<fmt:formatNumber var="tempvar1" minFractionDigits="2"
										maxFractionDigits="2" groupingUsed="true"
										value="${insuranceInfoDto.WCIAmount}" pattern="#,###,###" />
										<strong>Amount $</strong>
										<input type="text" name="insuranceInfoDto.WCIAmount" id="WCIAmount"
										value="${tempvar1}" maxlength="15" tabindex="4"
										Style="width: 100px;"/>
										<span class="req">*</span>
										<input type="hidden" id="insuranceForm_hdn_WCIAmount" name="hdn_WCIAmount" value="${tempvar1}"/>
								</c:catch>
								<c:if test = "${formatError3 != null}">
								   	<strong>Amount $</strong>
									<input type="text" name="insuranceInfoDto.WCIAmount" id="WCIAmount"
									value="${insuranceInfoDto.WCIAmount}" maxlength="15" tabindex="4"
									Style="width: 100px;"/>
									<span class="req">*</span>
									<input type="hidden" id="insuranceForm_hdn_WCIAmount" name="hdn_WCIAmount" value="${tempvar1}"/>
								</c:if>
								<c:if test="${not empty Error}">
										<strong>Amount $</strong>
										<s:textfield name="insuranceInfoDto.WCIAmount" id="WCIAmount"
										value="%{insuranceInfoDto.WCIAmount}" maxlength="15"
										tabindex="4" cssStyle="width: 100px;"></s:textfield>
										<span class="req">*</span>
										<input type="hidden" id="insuranceForm_hdn_WCIAmount" name="hdn_WCIAmount" value="${insuranceInfoDto.WCIAmount}"/>
								</c:if>
							</c:otherwise>
							</c:choose>
					</c:otherwise>
					</c:choose>
							</p>
							<s:iterator id="insuranceObj"
								value="insuranceInfoDto.insuranceList" status="inner">
								<s:if test="%{'Workman\\'s Compensation'  == categoryName}">
									<c:set var="buttonType3" value="${buttonType}" />
									<c:set var="currentDocId3" value="${currentDocumentId}" />
									<c:set var="currentDocName3" value='${fn:replace(fn:replace(docURL,"&", "&amp;"),"\'","&#39;")}' />
									<c:set var="wciCredId" value="${vendorCredentialId}" />
									<c:set var="wciCategoryName" value="${categoryName}" />
									<p class="statdoc">
										<s:if test="%{wfStateId == '13'}">
											<img src="${staticContextPath}/images/icons/incIconOn.gif"
												width="10" height="10" alt="">
										</s:if>
										<s:elseif test="%{wfStateId =='14'}">
											<img src="${staticContextPath}/images/icons/greenCheck.gif"
												width="10" height="10" alt="">
										</s:elseif>
										<s:elseif test="%{wfStateId =='25'}">
											<img src="${staticContextPath}/images/icons/errorIcon.gif"
												width="10" height="10" alt="">
										</s:elseif>
										<s:elseif test="%{wfStateId =='210'}">
											<img src="${staticContextPath}/images/icons/errorIcon.gif"
												width="10" height="10" alt="">
										</s:elseif>
										<strong>Status:</strong>
										<span id="wci_cred_status">${statusDesc}</span>
										&nbsp;
										<c:choose>
										<c:when test="${currentDocId3 > '0'}">
											<strong>Document:</strong>
											<span> 
												<a href="jsp/providerRegistration/processInsuranceAction_displayTheDocument.action?docId=<s:property value="currentDocumentId"/>&credId=<s:property value="vendorCredentialId"/>"
												target="blank"><s:property value="docURL" />
												</a>
											</span>
										</c:when>
										<c:otherwise>
											<strong>Document:</strong>
											<span id="wci_cred_status">None Attached</span>
										</c:otherwise>
										</c:choose>
									</p>
								</s:if>
							</s:iterator>
						</td>
					</tr>
					<tr>
						<td>
							<p class="smallFont">
								<fmt:message bundle="${serviceliveCopyBundle}" key="workmansCompensation.insurance.required"/>
							</p>
						</td>
					</tr>
					<tr>
						<td>
							<div class="attach">					
								<span>
									<fmt:message bundle="${serviceliveCopyBundle}" key="attach.insurabceCertificate.msg"/>
								</span>
								<c:choose>
								<c:when test="${BUTTON_TYPE_ADD==buttonType3 && insuranceInfoDto.WCI != null && insuranceInfoDto.WCI == '1'}">
									<input type="button" value="Attach" id="WCIButton"
										onClick="handlePolicyModal('${insuranceInfoDto.WCIAmount}','${BUTTON_TYPE_ADD}',${wciCredId},'Workmans Compensation','','');" />
								</c:when>
								<c:when test="${BUTTON_TYPE_ADD==buttonType3 && insuranceInfoDto.WCI != null && insuranceInfoDto.WCI == '0'}">
									<input type="button" value="Attach" id="WCIButton" disabled="disabled"
										onClick="handlePolicyModal('${insuranceInfoDto.WCIAmount}','${BUTTON_TYPE_ADD}',${wciCredId},'Workmans Compensation','','');" />
								</c:when>
								<c:when test="${BUTTON_TYPE_EDIT==buttonType3 && insuranceInfoDto.WCI != null && insuranceInfoDto.WCI == '0'}">
									<input type="button" value="Edit" id="WCIButton" disabled="disabled"
										onClick='handlePolicyModal("${insuranceInfoDto.WCIAmount}","${BUTTON_TYPE_EDIT}",${wciCredId},"Workmans Compensation","${currentDocId3}","${currentDocName3}");' />
								</c:when>
								<c:when test="${BUTTON_TYPE_EDIT==buttonType3 && insuranceInfoDto.WCI != null && insuranceInfoDto.WCI == '1'}">
									<input type="button" value="Edit" id="WCIButton"
										onClick='handlePolicyModal("${insuranceInfoDto.WCIAmount}","${BUTTON_TYPE_EDIT}",${wciCredId},"Workmans Compensation","${currentDocId3}","${currentDocName3}");' />
								</c:when>
								<c:when test="${insuranceInfoDto.WCI == null || insuranceInfoDto.WCI == '0'}">
									<input type="button" value="Attach" id="WCIButton" disabled="disabled"
										onclick="handlePolicyModal('0.0','${BUTTON_TYPE_ADD}',${wciCredId},'Workmans Compensation','','');" />
								</c:when>
								</c:choose>
							</div>
						</td>
					</tr>					
					<tr>
						<td>
							<s:radio id="insuranceInfoDto.WCI" name="insuranceInfoDtoWCI"
							value="%{insuranceInfoDto.WCI}" tabindex="3"
							onclick="javascript:showRemoveWC();"
							list="#{0:' No,'}"  />
							
							<fmt:message bundle="${serviceliveCopyBundle}" key="workmansCompensation.insurance.notRequired"/>
						</td>
					</tr>					
					<br/>	
				</table> 
			</div>
		
			<br/>
			<div class="darkGrayModuleHdr">Additional Insurance</div>
			<div class="grayModuleContent mainContentWell"
				style="height:250px; overflow:auto;">
				<b>Additional Insurance (Optional)</b><br/>
				<s:if test="%{insuranceInfoDto.additionalInsuranceListSize !=0}">
				  <table class="scrollerTableHdr licensesTableHdr" cellpadding="0" cellspacing="0" style="width:660px;">
				    <tr>
				      <td class="column1"><img src="${staticContextPath}/images/images_registration/common/spacer.gif" width="60" height="20" title="spacer" /></td>
				      <td class="column2"> Insurance Type </td>
				      <td class="column3"> Coverage Amount </td>
				      <td class="column4"> Expiration </td>
				      
				      <td class="column5">Verified by ServiceLive</td>
				    </tr>
				  </table>
				  <table class="licensesTable" cellpadding="0" cellspacing="0" style="width:660px;">
				   <s:iterator value="insuranceInfoDto.additionalInsuranceList">
				    <tr>
				      <td class="column1" style="word-wrap:break-word">
						<s:a href="processInsuranceAction_doLoadAdditional.action?credId=%{vendorCredId}&buttonType=EDIT&policyAmount=%{credAmount}" theme="simple"> 
				    		<img src="${staticContextPath}/images/images_registration/common/spacer.gif" width="48" height="20" style="background-image:url(${staticContextPath}/images/images_registration/btn/edit.gif);" class="btn20Bevel" /></td>
						</s:a>
				    	
				      <td class="column2" style="word-wrap:break-word;padding-right:5px;"><s:property value="credCategory"/></td>
				      <td class="column3" style="word-wrap:break-word"><s:property value="credAmount"/></td>
				      <td class="column4" style="word-wrap:break-word"><s:property value="expirationDate"/></td>
				      <td class="column5" style="word-wrap:break-word"><s:property value="marketplaceInd"/></td>
				    </tr>
				    </s:iterator>
				    </table>
				</s:if>
				<s:else>
					<p>Your Firm currently does not have any additional insurance recorded. If your firm has additional insurance above and beyond the 
				   required coverages, your firm may be eligible to participate in certain select provider networks which require specific types of coverages.</p>	
				   <br/>
				   <p>
				</s:else></br>
							<div class="clearfix">
				<!-- <a onclick="javascript:loadAdditionalInsurance('addInsuranceActiondoAdd.action');" class="myButton">ADD INSURANCE</a> -->
				<input type="button" class="myButton" value="ADD INSURANCE" id="addAdditionalInsurnceButton"
										onClick="javascript:loadAdditionalInsurance('addInsuranceActiondoAdd.action');" />
<!-- 			<img src="${staticContextPath}/images/images_registration/common/spacer.gif" width="103" height="20" onclick="javascript:loadAdditionalInsurance('addInsuranceActiondoAdd.action');" style="background-image:url(${staticContextPath}/images/images_registration/btn/addCredential.gif);"class="btn20Bevel"/>
-->
			</div>
			</div>
			</br>
				<div class="clearfix">
				<img type="image"
					onclick="javascript:saveInsuranceTypePage('addInsuranceActionloadPrevious.action');"
					src="${staticContextPath}/images/images_registration/common/spacer.gif"
					style="background-image: url(${staticContextPath}/images/images_registration/btn/previous.gif);"
					width="72px" height="20px" class="btn20Bevel" tabindex="7" />
				<img type="image"
					onclick="javascript:saveInsuranceTypePage('<s:url action='addInsuranceActionsaveInsuranceList.action'/>');"
					src="${staticContextPath}/images/images_registration/common/spacer.gif"
					style="background-image: url(${staticContextPath}/images/images_registration/btn/save.gif);"
					width="49px" height="20px" class="btn20Bevel" tabindex="8" />
				<img type="image"
					onclick="javascript:saveInsuranceTypePage('<s:url action='addInsuranceActionloadInsuranceTypeList.action'/>');"  
					src="${staticContextPath}/images/images_registration/common/spacer.gif"
					style="background-image: url(${staticContextPath}/images/images_registration/btn/next.gif);"
					width="50px" height="20px" class="btn20Bevel" tabindex="9" />
			</div>
	<!-- Modal for confirmation popup -->

	<div id="declinemodal2" style="display: none" class="modal">
		<div style="background: none repeat scroll 0% 0% rgb(88, 88, 90); border-bottom: 2px solid black; color: rgb(255, 255, 255); text-align: left; height: 15px; font-size:11px;font-weight:bold; padding-left: 15px;padding-top: 5px;" id="modalTitleBlack">
		CONFIRMATION

		</div>	
			
		<p align="justify" style="padding: 8px;font-size:11px">
			You have previously provided details of your Worker&apos;s Compensation Insurance which may also have included attaching a copy of your 
			Worker&apos;s Compensation certificate.<b>&nbsp;By changing your declaration from "Yes, I have Worker&apos;s Compensation" to  "No, I do not have Worker&apos;s
			Compensation, the system will automatically remove the detailed credential information, including any attachments.</b>&nbsp;Do you wish to continue with changing
			your declaration from "Yes" to "No"?
		</p>


			<table
				style="position: relative; padding-top: 10px; padding-bottom: 10px"
				width="100%">
				<tr>
					<td align="right" width="80%"><input id="yesButton"
						type="button" style="width:60px" value="Yes"
						onclick="javascript:removeWCInsurance(${wciCredId});" /></td>
					<td align="center" width="20%"><input id="noButton"
						type="button" value="Cancel"
						onclick="javascript:cancelRemoveWC();" /> <br /></td>

				</tr>
			</table>
	</div>
	<input type="hidden" id="wciCredId" name="wciCredId" value="${wciCredId}"/>
		</s:form>
	</body>
</html>
