<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="BUTTON_TYPE_ADD" value="<%=OrderConstants.BUTTON_TYPE_ADD%>"></c:set>
<c:set var="BUTTON_TYPE" value="<%=(String)session.getAttribute(OrderConstants.BUTTON_TYPE)%>"></c:set>
<c:set var="DOC_NAME" value="<%=(String)session.getAttribute(OrderConstants.CREDENTIAL_FILE_NAME)%>"></c:set>	
<c:set var="LAST_DOC_ID" value="<%=(Integer)session.getAttribute(OrderConstants.CREDENTIAL_DOC_ID)%>"></c:set>	

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery.modal.min.css" type="text/css"></link>
<script src="${staticContextPath}/javascript/jquery.simplemodal.1.4.4.min.js"
      type="text/javascript"></script>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", -1);
%>
<style type="text/css">
#yesButton,#noButton {
	background:
		url("${staticContextPath}/images/common/button-action-bg.png");
	border: 1px solid #b1770b;
	color: #222;
	font-family: Arial, Tahoma, sans-serif;
	font-size: 1.1em;
	font-weight: bold;
	padding: 3px 10px;
	cursor: pointer;
	-moz-border-radius: 5px 5px 5px 5px;
	margin-top: -5px;
	text-align: center;
	width: 80px;
}

.modal{
        padding: 0px 0px 0px 0px;
        width: 100%;
        max-width: 100%;
      }
</style>

	<div id="content_right_header_text">
		<%@ include file="../message.jsp"%>
	</div>
	
	
<c:if test="${sessionScope.tabDto.tabStatus['Insurance'] != null || isFromPA == true}">
		<tags:security actionName="auditAjaxAction">
			<%@ include file="/jsp/auditor/commonCredentialApprovalWidget.jsp"%>
		</tags:security>
	</c:if>
<s:form name="policyInformationForm"  id="policyInformationForm" theme="simple" method="post" action="insurancePolicyDetailsAction_" enctype="multipart/form-data">
	<s:hidden name="category" id="category" value=""></s:hidden>
	<s:hidden name="credId" id="credId" value=""></s:hidden>
	<s:hidden name="sameDocId" id="sameDocId"></s:hidden>
	<s:hidden name="buttonType" id="buttonType" value=""></s:hidden>
	<s:hidden name="docId" id="docId" value=""></s:hidden>
	<s:hidden name="displayOtherIndicator" id="displayOtherIndicator" value="%{insurancePolicyDto.displayOtherInsurance}"></s:hidden> 
	<input type="hidden" name="newDocId" id="newDocId" />
	
	
	<p class="paddingBtm">If your company has additional insurance coverage(s), upload them here. These additional coverage will allow you to accept orders on projects 
that require specific types of insurance coverage. Once uploaded, your credentials may be verified by the ServiceLive team.
</p>


		<div style="margin: 10px 0pt;display:none;" class="errorBox" id="errorMessages1"  >				
        </div>
        
		<!--<div class="grayModuleContent mainContentWell">	-->		
<p><b>An asterix (<span class="req">*</span>) indicates a required field</b></p> <br/>

<div class="darkGrayModuleHdr">
			Additional Insurance
</div>
<div class="grayModuleContent mainContentWell">
<div>
			<c:choose>
			<c:when  test="${not empty LAST_DOC_ID}" >
				<c:set var="DOC_ID" value="1"></c:set>	
			</c:when>
			<c:otherwise>
				<c:set var="DOC_ID" value="0"></c:set>	
			</c:otherwise>
			</c:choose>
<c:if test="${insurancePolicyDto.credentialDocumentId != null}">	
</c:if>	
	 <table cellpadding="0" cellspacing="0" width="679">	
		<tr><td colspan="2" width="340">
				<tr>
					<td width="60%">
						<c:choose>
						<c:when
							test="${fieldErrors['insurancePolicyDto.credentialCategoryId'] == null}">
							<p class="paddingBtm">
						</c:when>
						<c:otherwise>
							<p class="errorBox" />
						</c:otherwise>
						</c:choose>
						<label>
							Type of Credential<span class="req">*</span>
						</label>
						<br />
						<s:select name="insurancePolicyDto.credentialCategoryId"
							id="insurancePolicyDto.credentialCategoryId"
							list="insurancePolicyDto.mapCredentialType"
							value="%{insurancePolicyDto.credentialCategoryId}"
							headerValue="Select One" headerKey="-1" theme="simple"
							cssStyle="width:270px;" cssClass="grayText"
							onchange="javascript:checkForOther(%{insurancePolicyDto.credentialCategoryId});"
							onclick="changeDropdown(this)">
						</s:select>
						</p>
					</td>
					<c:choose>
					<c:when test="${insurancePolicyDto.displayOtherInsurance == true}">
					<td width="40%"  id="otherInsDiv">
						<c:choose>
						<c:when
							test="${fieldErrors['insurancePolicyDto.credentialCategoryDesc'] == null}">
							<p class="paddingBtm">
						</c:when>
						<c:otherwise>
							<p class="errorBox" />
						</c:otherwise>
						</c:choose>
						<label>
							Specify Type of Other Insurance<span class="req">*</span>
						</label>
						<br />
						<s:textfield name="insurancePolicyDto.credentialCategoryDesc" id="insurancePolicyDto.credentialCategoryDesc"
							value="%{insurancePolicyDto.credentialCategoryDesc}" maxlength="20"
							style="display:none;"
							size="15" cssStyle="width: 200px;" cssClass="shadowBox grayText"></s:textfield>
						</p>
					</td>
					</c:when>
					<c:otherwise>
					<td width="220"  id="otherInsDiv" style="display: none;">
						<c:choose>
						<c:when
							test="${fieldErrors['insurancePolicyDto.credentialCategoryDesc'] == null}">
							<p class="paddingBtm">
						</c:when>
						<c:otherwise>
							<p class="errorBox" />
						</c:otherwise>
						</c:choose>
						<label>
							Specify Type of Other Insurance<span class="req">*</span>
						</label>
						<br />
						<s:textfield name="insurancePolicyDto.credentialCategoryDesc" id="insurancePolicyDto.credentialCategoryDesc"
							value="%{insurancePolicyDto.credentialCategoryDesc}" maxlength="20"
							style="display:none;"
							size="15" cssStyle="width: 200px;" cssClass="shadowBox grayText"></s:textfield>
						</p>
					</td>
					</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<td>
						<c:choose>
						<c:when
							test="${fieldErrors['insurancePolicyDto.policyNumber'] == null}">
							<p>
						</c:when>
						<c:otherwise>
							<p class="errorBox">
						</c:otherwise>
						</c:choose>
						<label>
							Policy Number  <span class="insPopUpMnd">*</span>
						</label>
						<br />
						<s:hidden name="insurancePolicyDto.combinedKey" />
						<s:textfield name="insurancePolicyDto.policyNumber" id="insurancePolicyDto.policyNumber1"
							value="%{insurancePolicyDto.policyNumber}" maxlength="20"
							size="15" cssStyle="width: 100px;" cssClass="shadowBox grayText"></s:textfield>
							</p>
						</td>

					<td>
							<p>
							<c:choose>
							<c:when test="${fieldErrors['insurancePolicyDto.amount'] == null}">
								<p>
							</c:when>
							<c:otherwise>
								<p class="errorBox">
							</c:otherwise>
							</c:choose>
							<label>
								Amount  <span class="insPopUpMnd">*</span>
							</label>
							<br />
							<s:textfield name="insurancePolicyDto.amount" id="insurancePolicyDto.amount1"
								value="%{insurancePolicyDto.amount}" maxlength="15" size="15"
								cssStyle="width: 100px;" cssClass="shadowBox grayText"></s:textfield>
						</p>
					</td>
						
					</tr>
					<tr>	
						<td>
							<c:choose>
							<c:when
								test="${fieldErrors['insurancePolicyDto.carrierName'] == null}">
								<p>
							</c:when>
							<c:otherwise>
								<p class="errorBox">
							</c:otherwise>
							</c:choose>
							<label>
								Carrier Name  <span class="insPopUpMnd">*</span>
							</label>
							<br />
							<s:textfield name="insurancePolicyDto.carrierName"
								value="%{insurancePolicyDto.carrierName}" id="insurancePolicyDto.carrierName1" maxlength="99"
								size="40" cssStyle="width: 200px;" cssClass="shadowBox grayText" ></s:textfield>
							</p>
						</td>
						<td>					
							<c:choose>
							<c:when
								test="${fieldErrors['insurancePolicyDto.agencyName'] == null}">
								<p>
							</c:when>
							<c:otherwise>
								<p class="errorBox">
							</c:otherwise>
							</c:choose>
							<label>
								Agency Name  <span class="insPopUpMnd">*</span>
							</label>
							<br />
							<s:textfield name="insurancePolicyDto.agencyName" id="insurancePolicyDto.agencyName1"
								value="%{insurancePolicyDto.agencyName}" maxlength="99" size="40"
								cssStyle="width: 200px;" cssClass="shadowBox grayText"></s:textfield>
							</p>
						</td>
					</tr>
					<tr>
						<td>
								<c:choose>
								<c:when
									test="${fieldErrors['insurancePolicyDto.agencyState'] == null}">
									<p>
								</c:when>
								<c:otherwise>
									<p class="errorBox">
								</c:otherwise>
								</c:choose>
								<label>
									Agency State  <span class="insPopUpMnd">*</span>
								</label>
								<br />
	
								<s:select name="insurancePolicyDto.agencyState" id="insurancePolicyDto.agencyState1"
									value="%{insurancePolicyDto.agencyState}"
									list="#application['stateCodes']" listKey="type"
									listValue="descr"></s:select>
							</p>
						</td>
					
						<td>
								<c:choose>
								<c:when
									test="${fieldErrors['insurancePolicyDto.agencyCountry'] == null}">
									<p>
								</c:when>
								<c:otherwise>
									<p class="errorBox">
								</c:otherwise>
								</c:choose>
	
								<label>
									Agency County
								</label>
								<br />
								<s:textfield name="insurancePolicyDto.agencyCountry" id="insurancePolicyDto.agencyCountry1"
									value="%{insurancePolicyDto.agencyCountry}" maxlength="15"
									size="15" cssStyle="width: 75px;" cssClass="shadowBox grayText"></s:textfield>
							</p>
						</td>
					</tr>
					
					<tr>
						<td>
								<c:choose> 
								<c:when
									test="${fieldErrors['insurancePolicyDto.policyIssueDate'] == null}">
									<p class="paddingBtm">
								</c:when>
								<c:otherwise>
									<p class="errorBox">
								</c:otherwise>
								</c:choose>
								<label>
									Issue Date  <span class="insPopUpMnd">*</span>
								</label>
								<br />
								<c:choose>
								<c:when test="%{insurancePolicyDto.vendorCredentialId > -1}">
									<c:choose>
									<c:when test="%{not empty insurancePolicyDto.policyIssueDate}">
									<input type="text" name="insurancePolicyDto.policyIssueDate"
								dojoType="dijit.form.DateTextBox"
								constraints="{datePattern:'MM/dd/yyyy'}" class="shadowBox"
								id="modal2ConditionalChangeDateAdditionalIss" maxlength="10"
								value="<fmt:formatDate value="${insurancePolicyDto.policyIssDate}" pattern="yyyy-MM-dd" />" /> 
									</c:when>
									<c:otherwise>
									<input type="text" name="insurancePolicyDto.policyIssueDate"
								dojoType="dijit.form.DateTextBox"
								constraints="{datePattern:'MM/dd/yyyy'}" class="shadowBox"
								id="modal2ConditionalChangeDateAdditionalIss" maxlength="10"
								value="<fmt:formatDate value="${insurancePolicyDto.policyIssDate}" pattern="yyyy-MM-dd" />" />  
									</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>				
								<input type="text" name="insurancePolicyDto.policyIssueDate"
								dojoType="dijit.form.DateTextBox"
								constraints="{datePattern:'MM/dd/yyyy'}" class="shadowBox"
								id="modal2ConditionalChangeDateAdditionalIss" maxlength="10"
								value="<fmt:formatDate value="${insurancePolicyDto.policyIssDate}" pattern="yyyy-MM-dd" />" /> 						
									</c:otherwise>
								</c:choose>
							</p>
						</td>
						<td>
							<c:choose>
							<c:when
								test="${fieldErrors['insurancePolicyDto.policyExpirationDate'] == null}">
								<p>
							</c:when>
							<c:otherwise>
								<p class="errorBox">
							</c:otherwise>
							</c:choose>
							<label>
								Expiration Date  <span class="insPopUpMnd">*</span>
							</label>
							<br />
							<c:choose>
							<c:when test="%{insurancePolicyDto.vendorCredentialId > -1}">
								<c:choose>
								<c:when test="%{not empty insurancePolicyDto.policyExpirationDate}">
								<input type="text" name="insurancePolicyDto.policyExpirationDate"
								dojoType="dijit.form.DateTextBox"
								constraints="{datePattern:'MM/dd/yyyy'}" class="shadowBox"
								id="modal2ConditionalChangeDateExpAdditional" maxlength="10"
								value="<fmt:formatDate value="${insurancePolicyDto.policyExpDate}" pattern="yyyy-MM-dd" />" />  
								</c:when>
								<c:otherwise>
								<input type="text" name="insurancePolicyDto.policyExpirationDate"
								dojoType="dijit.form.DateTextBox"
								constraints="{datePattern:'MM/dd/yyyy'}" class="shadowBox"
								id="modal2ConditionalChangeDateExpAdditional" maxlength="10"
								value="<fmt:formatDate value="${insurancePolicyDto.policyExpnDate}" pattern="yyyy-MM-dd" />" />  							</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<input type="text" name="insurancePolicyDto.policyExpirationDate"
								dojoType="dijit.form.DateTextBox"
								constraints="{datePattern:'MM/dd/yyyy'}" class="shadowBox"
								id="modal2ConditionalChangeDateExpAdditional" maxlength="10"
								value="<fmt:formatDate value="${insurancePolicyDto.policyExpDate}" pattern="yyyy-MM-dd" />" />  						</c:otherwise>
							</c:choose>
							</p>
						</td>
					</tr>
			</table>
			
			<p class="paddingBtm">I certify that my Provider Firm has the type of additional insurance indicated above and in the amount set forth above. I acknowledge that 
			if the insurance coverage changes, it is my responsibility to update the information above.</p>
			</div>
		</div>
		<!-- NEW MODULE -->

	<div class="darkGrayModuleHdr">
		Attach Insurance Document
	</div>
		<div class="grayModuleContent mainContentWell">
		<h3>Get ServiceLive Verified. Attach certificate</h3>
		<p>
			Please attach an electronic copy of your certificate. Certificate
			details will be used for verification purposes and will not be part
			of your public profile. You may upload a maximum of one file per
			insurance type. Each individual file is limited to 2 MB.
		</p>
				<div id="documentUpload">			
				</div>
				<br/>
		<%-- <p> Document on file: ${DOC_NAME}</p> --%>
<c:if  test="${BUTTON_TYPE == 'ADD'}" >
<p class='policyNote' style='font-size:11px'><strong>Document on file:</strong> <span> <a href='jsp/providerRegistration/processInsuranceAction_displayTheDocument.action?docId=${LAST_DOC_ID}' target='blank'>${DOC_NAME}</a></span>
<br>
<br/>

				<div id="lastDocUpload" style='background-color:#f4f2f2;display: block' ><b>Note:</b><br/><p style='margin-left: 50px;font-size:11px'>If your coverage is on the same insurance certificate,select 
Use the <br/>same insurance certificate' and verify the information below:</p>
<br/>
<input type='checkbox' id='updateDocumentInd' label='checkbox test' name='insuranceInfoDto.updateDocumentInd' tabindex='5' onchange='javascript:checkFieldEnable_addtnlInsurance(${LAST_DOC_ID});' /><label style='font-size:11px'> Use the same insurance certificate</label><br/>
<br/>
</div>	
</c:if>
			
			<c:if  test="${BUTTON_TYPE == 'EDIT'}" >
				<div id="lastDocUpload" style='background-color:#f4f2f2;display: none' ><b>Note:</b><br/><p style='margin-left: 50px;font-size:11px'>If your coverage is on the same insurance certificate,select 
Use the <br/>same insurance certificate' and verify the information below:</p>
<br/>
<input type='checkbox' id='updateDocumentInd' label='checkbox test' name='insuranceInfoDto.updateDocumentInd' tabindex='5' onclick='javascript:checkFieldEnable_addtnlInsurance(${LAST_DOC_ID});' /><label style='font-size:11px'> Use the same insurance certificate</label><br/>
<br/>
</div>
			</c:if>

	<div id="uploadFile1">
		<p class="paddingBtm">
			<label>
				Select file to upload
			</label>
			<br />
			<input type="file" id="insurancePolicyDto.file"
						class="insuranceshadowBox grayText" value="[Select file]"
						name="insurancePolicyDto.file" class="btnBevel uploadBtn"
						size="40" style="width: 300px;" onchange="checkExtension(this.value,'upload')" />
						
		<a onclick="attachAdditionalInsForm('insurancePolicyDetailsAction_attachDocument.action'); return false;" class="myButton">ATTACH</a>
			
		</p>


		<div class="filetypes">
			<p>
				Preferred file types:
				<br />
				.JPG | .PDF | .DOC | .GIF
			</p>
		</div>
			<s:if test="%{insurancePolicyDto.vendorCredentialId > 0}">
				<s:if test="%{insurancePolicyDto.credentialDocumentId > 0}">
					<table border=1 style="width: 350px;border-collapse:collapse" cellpadding="0"
						cellspacing="0">
						<tr align="center"
							style="color: #fff; font-weight: bold; background: #4CBCDF; height: 23px;">
							<td>File Name</td>
							<td>File Size</td>
						</tr>
						<tr style="height: 23px;" align="center">
							<td class="column2"><s:property
									value="insurancePolicyDto.credentialDocumentFileName" /></td>
							<td class="column3"><s:property
									value="insurancePolicyDto.credentialDocumentFileSize" /></td>
						</tr>
					</table>
				</s:if>
			</s:if>
			<s:if test="%{insurancePolicyDto.vendorCredentialId > 0}">
				<s:if test="%{insurancePolicyDto.credentialDocumentId > 0}">
					<s:set name="docId"
						value="%{insurancePolicyDto.credentialDocumentId}"></s:set>

					<div class="clearfix">
						<p>
							<a
								href="jsp/providerRegistration/processInsuranceAction_displayTheDocument.action?docId=<s:property value="insurancePolicyDto.credentialDocumentId"/>"
								target="blank"> <img
								src="${staticContextPath}/images/images_registration/common/spacer.gif"
								style="background-image: url(${staticContextPath}/images/images_registration/btn/view.gif);"
								width="72" height="20" /> </a>
						</p>
					</div>
				</s:if>
			</s:if>
		</div>
	</div>
		

			<div class="clearfix">
			<div class="formNavButtons">
			<c:choose>
			<c:when test="${insurancePolicyDto.vendorCredentialId > 0}">
					<!-- <input type="image"
					src="${staticContextPath}/images/images_registration/common/spacer.gif"
					width="95" height="20"
					onclick="javascript:saveInsurance('insurancePolicyDetailsAction_doSave.action'); return false;"
					style="background-image: url(${staticContextPath}/images/images_registration/btn/updatePolicy.gif);"
					class="btn20Bevel" />
		
					<input id="removeAdditionalIns" type="image"src="${staticContextPath}/images/images_registration/common/spacer.gif"
					style="background-image: url(${staticContextPath}/images/images_registration/btn/removeCredential.gif);"
					width="120" height="20" class="opendeclinemodal" onclick="javascript:removeCred(); return false;"/>
					--->
					<a onclick="javascript:saveInsurance('insurancePolicyDetailsAction_doSave.action'); return false;" class="myButton">UPDATE INSURANCE</a>
				
					<!--Code added as part of Jira SL-20645 -To capture time while auditing insurance -->				
					<input type="hidden" name="insurancePolicyDto.auditTimeLoggingIdNew" value="<%=request.getAttribute("auditTimeLoggingId")%>">
					&nbsp;
					<a id="removeAdditionalIns" onclick="javascript:removeCred(); return false;" class="myButton">REMOVE INSURANCE</a>
			</c:when>
			<c:otherwise>
			<input type="image"
					src="${staticContextPath}/images/images_registration/common/spacer.gif"
					onclick="javascript:saveInsurance('insurancePolicyDetailsAction_doSave.action'); return false;"
					style="background-image: url(${staticContextPath}/images/images_registration/btn/save.gif);"
					width="49px" height="20px" class="btn20Bevel" />
					<input type="hidden" name="insurancePolicyDto.auditTimeLoggingIdNew" value="<%=request.getAttribute("auditTimeLoggingId")%>">
			</c:otherwise>
			</c:choose>
			</div>
		</div>
			<div class="bottomRightLink">
			<a
				href="javascript:closeAdditionalInsForm('allTabView.action?tabView=tab4');">Cancel</a>
			</div>
					<!-- begin modal -->
<!-- SL-10809
Code added for new alert message for 'Remove Insurance' -->

<div id="declinemodal" class="modal" style="display: none" >
	<div style="background: none repeat scroll 0% 0% rgb(88, 88, 90); border-bottom: 2px solid black; color: rgb(255, 255, 255); text-align: left; height: 15px; padding-left: 8px; padding-top: 5px;font-size:11px;font-weight:bold" id="modalTitleBlack;">
	CONFIRMATION
	</div>	
		<p style="padding: 8px;font-size:11px">
	<strong>		You are about to remove this additional insurance type.   Once you remove this item it will no longer 
			be accessible within the ServiceLive Platform.</strong>
		</p>
	
		<p style="padding: 8px;font-size:11px">
			You will need to add this additional insurance type again if 
			you wish to include it in your Firm&apos;s profile in the future.
		</p>


		 <table style="padding-right ;position: relative; left: 8px;padding-top:10px;padding-bottom:10px" width="100%">
			<tr>
				<td width="80%" align="right">
					<input id="yesButton" type="button" value="OK" style="width:90px;margin-left:50px" onclick="javascript:removeAdditionalInsurance();"/>	
					<br/>
				</td>
				<td width="20%" align="center">
					<input id="noButton" type="button" value="Cancel" onclick = "jQuery.modal.impl.close(true);"/>
				</td>
			</tr>
		</table>
	</div>
		<input type="hidden"  name="insurancePolicyDto.vendorCredentialId" id="vendorCredentialId1"
			value="${insurancePolicyDto.vendorCredentialId}" />
	</s:form>
<!-- END MODAL -->
