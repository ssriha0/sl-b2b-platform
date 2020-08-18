
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>


<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery.modal.min.css" type="text/css"></link>
<script src="${staticContextPath}/javascript/jquery.simplemodal.1.4.4.min.js"
      type="text/javascript"></script>

	<c:if test="${sessionScope.tabDto.tabStatus['Credentials'] != null}">
		<tags:security actionName="auditAjaxAction">
			<%@ include file="/jsp/auditor/commonCredentialApprovalWidget.jsp"%>
		</tags:security>
	</c:if>

	<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
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
        height: 100%;
      }
</style>

<s:form action="licensesAndCertActiondoLoad?type=cat" method="post"
	theme="simple" enctype="multipart/form-data" name="licensesAndCertActiondoLoad?type=cat">
	<s:hidden name="licensesAndCertDto.vendorId"
		value="%{licensesAndCertDto.vendorId}" />
	<s:hidden name="licensesAndCertDto.vendorCredId"
		value="%{licensesAndCertDto.vendorCredId}" />


	<p class="paddingBtm">
		If your company has licenses or certifications, upload them here.
		These credentials will allow you to accept orders on projects that
		require licenses and certifications. Once uploaded, your credentials
		may be verified by the ServiceLive team.
	</p><br/>
<p><b>An asterix (<span class="req">*</span>) indicates a required field</b></p> <br/>
	<!-- NEW MODULE/ WIDGET-->
	<div id="content_right_header_text">
		<%@ include file="../message.jsp"%>
	</div>

	<div class="darkGrayModuleHdr">
		Licenses &amp; Certification Information
	</div>
	<div class="grayModuleContent mainContentWell">
		<div>
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td width="220">
						<c:choose>
						<c:when
							test="${fieldErrors['licensesAndCertDto.credentialTypeId'] == null}">
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
						<s:select name="licensesAndCertDto.credentialTypeId"
							onchange="form.submit();"
							list="licensesAndCertDto.mapCredentialType"
							value="%{licensesAndCertDto.credentialTypeId}"
							headerValue="Select One" headerKey="-1" theme="simple"
							cssStyle="width:200px;" cssClass="grayText"
							onclick="changeDropdown(this)">
						</s:select>
						</p>
					</td>
					<td>

						<c:choose>
						<c:when
							test="${fieldErrors['licensesAndCertDto.categoryId'] == null}">
							<p class="paddingBtm">
						</c:when>
						<c:otherwise>
							<p class="errorBox" />
						</c:otherwise>
						</c:choose>
						<label>
							Category<span class="req">*</span>
						</label>
						<br />
						<s:if test="%{licensesAndCertDto.credentialTypeId >0}">
							<s:select name="licensesAndCertDto.categoryId"
								list="licensesAndCertDto.mapCategory"
								value="%{licensesAndCertDto.categoryId}"
								headerValue="Select One" headerKey="-1" theme="simple"
								cssStyle="width:200px;" cssClass="grayText"
								onclick="changeDropdown(this)">
							</s:select>
						</s:if>
						<s:else>
							<select name="licensesAndCertDto.categoryId"
								style="width: 200px;" class="grayText"
								onclick="changeDropdown(this)">
								<option value="-1">
									Select One
								</option>
							</select>
						</s:else>
						</p>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<c:choose>
						<c:when
							test="${fieldErrors['licensesAndCertDto.licenseName'] == null}">
							<p class="paddingBtm">
						</c:when>
						<c:otherwise>
							<p class="errorBox" />
						</c:otherwise>
						</c:choose>
						<label>
							Name of License or Certification<span class="req">*</span>
						</label>
						<br />
						<s:textfield name="licensesAndCertDto.licenseName" maxlength="100"
							value="%{licensesAndCertDto.licenseName}" theme="simple"
							cssStyle="width: 414px;" cssClass="shadowBox grayText"
							 />
						<!--<input type="text" style="width: 414px;" class="shadowBox grayText" value="[Name of License or Certification]" onfocus="clearTextbox(this)" />-->
						</p>
					</td>
				</tr>
				<tr>
					<td width="221">
						<p>
						<label>
							Issuer of Credential
						</label>
						<br />

						<s:textfield name="licensesAndCertDto.issuerOfCredential"
							value="%{licensesAndCertDto.issuerOfCredential}" theme="simple"
							cssStyle="width: 191px;" cssClass="shadowBox grayText"
							 />
						<!-- <input type="text" style="width: 191px;" class="shadowBox grayText" value="[Issuer of Credential]" onfocus="clearTextbox(this)" />-->
						</p>
					</td>
					<td>
						<c:choose>
						<c:when
							test="${fieldErrors['licensesAndCertDto.credentialNum'] == null}">
							<p class="paddingBtm">
						</c:when>
						<c:otherwise>
							<p class="errorBox" />
						</c:otherwise>
						</c:choose>
						<label>
							Credential Number(If applicable)
						</label>
						<br />
						<s:textfield name="licensesAndCertDto.credentialNum"
							value="%{licensesAndCertDto.credentialNum}" theme="simple"
							cssStyle="width: 191px;" cssClass="shadowBox grayText"
							 />
						<!-- <input type="text" style="width: 191px;" class="shadowBox grayText" value="[Credential Number]" onfocus="clearTextbox(this)" /> -->
						</p>
					</td>
				</tr>
				<tr>
					<td width="221">
						<p>
							<label>
								City (If applicable)
							</label>
							<br />
							<s:textfield name="licensesAndCertDto.city"
								value="%{licensesAndCertDto.city}" theme="simple"
								cssStyle="width: 191px;" cssClass="shadowBox grayText"
								 />
							<!-- <input type="text" style="width: 191px;" class="shadowBox grayText" value="[City]" onfocus="clearTextbox(this)" /> -->
						</p>
					</td>
					<td>
						<c:choose>
						<c:when test="${fieldErrors['licensesAndCertDto.stateId'] == null}">
							<p class="paddingBtm">
						</c:when>
						<c:otherwise>
							<p class="errorBox" />
						</c:otherwise>
						</c:choose>
						<label>
							State(If applicable)
						</label>
						<br />
						<s:select name="licensesAndCertDto.stateId"
							list="licensesAndCertDto.mapState"
							value="%{licensesAndCertDto.stateId}" theme="simple"
							cssStyle="width:150px;" cssClass="grayText" listKey="type"
							listValue="descr">
						</s:select>
						<!-- <select style="width:80px;" class="grayText" onclick="changeDropdown(this)">
            <option value="">AL</option>
          </select> -->
						</p>
					</td>
				</tr>
				<tr>
					<td width="221">
						<c:choose>
						<c:when test="${fieldErrors['licensesAndCertDto.county'] == null}">
							<p class="paddingBtm">
						</c:when>
						<c:otherwise>
							<p class="errorBox" />
						</c:otherwise>
						</c:choose>
						<label>
							County (If applicable)
						</label>
						<br />
						<s:textfield name="licensesAndCertDto.county"
							value="%{licensesAndCertDto.county}" theme="simple"
							cssStyle="width: 191px;" cssClass="shadowBox grayText"
							 />
						<!--  <input type="text" style="width: 191px;" class="shadowBox grayText" value="[County]" onfocus="clearTextbox(this)" />-->
						</p>
					</td>
				</tr>
				<tr>
					<td width="221">
						<c:choose>
						<c:when
							test="${fieldErrors['licensesAndCertDto.issueDate'] == null}">
							<p class="paddingBtm">
						</c:when>
						<c:otherwise>
							<p class="errorBox" >
						</c:otherwise>
						</c:choose>
						<label>
							Issue Date<span class="req">*</span>
						</label>
						<br />

						<input type="text" name="licensesAndCertDto.issueDate"
							dojoType="dijit.form.DateTextBox"
							constraints="{datePattern:'MM/dd/yyyy'}" class="shadowBox"
							id="modal2ConditionalChangeDate1" maxlength="10"
							value="<s:property value="%{licensesAndCertDto.issueDate}"/>" />


						</p>
					</td>
					<td>
						<c:choose>
						<c:when
							test="${fieldErrors['licensesAndCertDto.expirationDate'] == null}">
							<p class="paddingBtm">
						</c:when>
						<c:otherwise>
							<p class="errorBox" />
						</c:otherwise>
						</c:choose>
						<label>
							Expiration Date (if applicable)
						</label>
						<br />
						<input type="text" dojoType="dijit.form.DateTextBox"
							constraints="{datePattern:'MM/dd/yyyy'}"
							class="shadowBox" id="modal2ConditionalChangeDate2"
							name="licensesAndCertDto.expirationDate"
							value="<s:property value="%{licensesAndCertDto.expirationDate}"/>" />

					</td>
				</tr>
			</table>
		</div>
	</div>
	<!-- NEW MODULE -->

	<div class="darkGrayModuleHdr">
		Attach Credential Document
	</div>
	<div class="grayModuleContent mainContentWell">
		<p>
			Please attach an electronic copy of your credential. Credential
			details will be used for verification purposes and will not be part
			of your public profile. You may upload a maximum of one file per
			license or certification. Each individual file is limited to 2 MB.
		</p>
		<p class="paddingBtm">
			<label>
				Select file to upload
			</label>
			<br />
			<input type="file" class="shadowBox grayText" style="width: 300px;"
				value="[Select file]" name="licensesAndCertDto.file"
				class="btnBevel uploadBtn" />
			&nbsp;&nbsp;

			<input type="image"
				src="${staticContextPath}/images/images_registration/common/spacer.gif"
				width="72" height="20"
				style="background-image: url(${staticContextPath}/images/images_registration/btn/attach.gif);"
				onclick="javascript:attachLicDocument('jsp/providerRegistration/licensesAndCertActionattachDocument.action');"
				class="btn20Bevel" />
		</p>


		<div class="filetypes">
			<p>
				Preferred file types:
				<br />
				.JPG | .PDF | .DOC | .GIF
			</p>
		</div>

		<s:if test="%{licensesAndCertDto.vendorCredId > -1}">
			<s:if test="%{licensesAndCertDto.credentialDocumentId > 0}">

				<table border=1 style="width: 350px;" cellpadding="0"
						cellspacing="0">
						<tr align="center"
							style="color: #fff; font-weight: bold; background: #4CBCDF; height: 23px;">
							<td>
								File Name
							</td>
							<td>
								File Size
							</td>
						</tr>
						<tr style="height: 23px;" align="center">
							<td class="column2">
							<s:property value="licensesAndCertDto.credentialDocumentFileName" />
						</td>
						<td class="column3">
							<s:property value="licensesAndCertDto.credentialDocumentFileSize" />
						</td>
					</tr>
				</table>

			</s:if>
		</s:if>
		<s:if test="%{licensesAndCertDto.vendorCredId > 0}">
			<s:if test="%{licensesAndCertDto.credentialDocumentId > 0}">
				<s:set name="docId"
					value="%{licensesAndCertDto.credentialDocumentId}"></s:set>
				<s:url
					action="jsp/providerRegistration/licensesAndCertActiondisplayTheDocument.action"
					id="url">
					<s:param name="docId"
						value="%{licensesAndCertDto.credentialDocumentId}" />
				</s:url>
				<div class="clearfix">
					<p>

						<a
							href="jsp/providerRegistration/licensesAndCertActiondisplayTheDocument.action?docId=<s:property value="licensesAndCertDto.credentialDocumentId"/>"
							target="blank"> <img
								src="${staticContextPath}/images/images_registration/common/spacer.gif"
								style="background-image: url(${staticContextPath}/images/images_registration/btn/view.gif);"
								width="72" height="20" /> </a>

						<!-- 
						<input type="image"
							src="${staticContextPath}/images/common/spacer.gif"
							onclick="javascript:removeLicDocument('jsp/providerRegistration/licensesAndCertActionremoveDocument.action?method=remove');"
							width="72" height="20" class="btn20Bevel"
							style="background-image: url(${staticContextPath}/images/btn/remove.gif);" />
						-->
						
					</p>
				</div>
			</s:if>
		</s:if>
	</div>
	<div class="clearfix">
		<div class="formNavButtons">
			<input type="image"
				onclick="javascript:saveLCInformation('<s:url action='licensesAndCertActiondoSave.action' />');"
				src="${staticContextPath}/images/images_registration/common/spacer.gif"
				style="background-image: url(${staticContextPath}/images/images_registration/btn/updateCredential.gif);"
				width="120" height="20" ;
						class="btn20Bevel" />
						<input type="hidden" name="licensesAndCertDto.auditTimeLoggingIdNew" value="<%=request.getAttribute("auditTimeLoggingId")%>">
						<!-- SL-19459 Code added for new alert message for 'Remove Credential' -->
			&nbsp;
			<s:if test="%{licensesAndCertDto.vendorCredId > 0}">
				<input id="removeCredentiall" type="image"src="${staticContextPath}/images/images_registration/common/spacer.gif"
					style="background-image: url(${staticContextPath}/images/images_registration/btn/removeCredential.gif);"
					width="120" height="20" class="opendeclinemodal" onclick="javascript:removeCred(); return false;"/>
				<!--onclick="javascript:saveLCInformation('<s:url action='licensesAndCertActiondoRemove.action' />');"-->
					
			</s:if>
		</div>
		<div class="clearfix">
			<div class="bottomRightLink">
				<c:if test= "${isFromPA != true}">
				<s:a
					href="allTabView.action?tabView=tab3&nexturl=listLicenceAndCertdoViewList1.action"
					onclick="form.onsubmit=null" theme="simple">Cancel</s:a>
				</c:if>
			</div>
		</div>
		
		<!-- begin modal -->
<!-- SL-19459
Code added for new alert message for 'Remove Credential' -->

<div id="declinemodal" class="modal" style="display: none">
	<div style="background: none repeat scroll 0% 0% rgb(88, 88, 90); border-bottom: 2px solid black; color: rgb(255, 255, 255); text-align: left; height: 15px; padding-left: 8px; padding-top: 5px;font-size:11px;font-weight:bold" id="modalTitleBlack;">
	CONFIRMATION
	</div>	
		<p style="padding: 8px;font-size:11px">
			<strong>		
				Warning: Once you remove this credential, it will no longer be visible to ServiceLive buyers and could negatively impact your membership in certain Select Provider Networks (SPN's).
			</strong>
		</p>
	
		 <table style="padding-right ;position: relative; left: 8px;padding-top:10px;padding-bottom:10px" width="100%">
			<tr>
				<td width="72%">
					<input id="noButton" type="button" value="Cancel" style="margin-left: 10px;margin-top: 5px;" onclick = "jQuery.modal.impl.close(true);"/>
					<br/>
				</td>
				<td width="28%">
					<input id="yesButton" type="button" value="OK" style="width:90px;margin-left:50px;margin-top: 5px;" onclick="javascript:saveLCInformation('<s:url action='licensesAndCertActiondoRemove.action' />');"/>	
				</td>
			</tr>
		</table>
	<!--	<div style="background-color: lightgray;height: 50px;text-align:right;">
		
			
			

						<input type="submit" onclick="javascript:saveLCInformation('<s:url action='licensesAndCertActiondoRemove.action' />');" value="OK" style="margin-top:10px;margin-right:25px;width:60px">	
						<input type="button" style="margin-right:25px;margin-top:10px;" value="Cancel" class="action right buttonDeclineDone" onclick="jQuery.modal.impl.close(true);">					
		
		</div>-->
	</div>
</div>
<!-- end modal -->
		
		
		
</s:form>
