<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>
	<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", -1);
%>


	<div id="content_right_header_text">
		<%@ include file="../message.jsp"%>
	</div>
									
	<c:if test="${sessionScope.tabDto.tabStatus['Insurance'] != null || isFromPA == true}">
		<tags:security actionName="auditAjaxAction">
			<%@ include file="/jsp/auditor/commonCredentialApprovalWidget.jsp"%>
		</tags:security>
	</c:if>
	
	
	<s:form action="saveInsuranceTypeActiondoSave" theme="simple"
		enctype="multipart/form-data">

		<h3>
			Verify Your Coverage
		</h3>
		<p class="paddingBtm">
			ServiceLive verification shows buyers that your company is properly
			insured and ready to complete their service orders. Upload copies of
			your certificates of insurance and your account status will be
			upgraded to 'verified.'
		</p>

		<div class="darkGrayModuleHdr">
			[Add/Edit] Insurance Information
		</div>
		<div class="grayModuleContent mainContentWell">
			<p>
				Please provide an electronic copy of the policy, if available. A
				maximum of (1) file per policy may be uploaded. Individual file
				size is limited to 2 MB.
			</p>
			<table cellpadding="0" cellspacing="0" width="679">
				<tr>
					<td  width="340">

						<c:choose>
						<c:when
							test="${fieldErrors['insurancePolicyDto.combinedKey'] == null}">
							<p class="paddingBtm">
						</c:when>
						<c:otherwise>
							<p class="errorBox">
						</c:otherwise>
						</c:choose>
						<label>
							Policy Category
						</label>
						<br />

						<!-- <s:select name="insurancePolicyDto.combinedKey"
							value="%{insurancePolicyDto.combinedKey}"
							list="insurancePolicyDto.insuranceTypeList" headerKey=""
							headerValue="--Please Select--" listKey="combinedId"
							listValue="credCategory">
						</s:select> -->
						
						<s:hidden name="insurancePolicyDto.combinedKey" />
						<s:textfield name="insurancePolicyDto.insuranceType"
							value="%{insurancePolicyDto.insuranceType}" maxlength="99"
							size="25" cssStyle="width: 230px;" cssClass="shadowBox grayText" readonly="true"></s:textfield>

						<c:choose> 
						<c:when
							test="${fieldErrors['insurancePolicyDto.carrierName'] == null}">
							<p class="paddingBtm">
						</c:when>
						<c:otherwise>
							<p class="errorBox">
						</c:otherwise>
						</c:choose>
						<label>
							Carrier Name
						</label>
						<br />
						<s:textfield name="insurancePolicyDto.carrierName"
							value="%{insurancePolicyDto.carrierName}" maxlength="99"
							size="45" cssStyle="width: 230px;" cssClass="shadowBox grayText"></s:textfield>
						</p>
					</td>
					<c:if test="${insurancePolicyDto.displayOtherInsurance == true}">
					<td width="339">
						<c:choose>
						<c:when
							test="${fieldErrors['insurancePolicyDto.credentialCategoryDesc'] == null}">
							<p class="paddingBtm">
						</c:when>
						<c:otherwise>
							<p class="errorBox" />
						</c:otherwise>
						</c:choose>
						<label>Specify Type of Other Insurance</label>
						<br />
						<s:textfield name="insurancePolicyDto.credentialCategoryDesc" id="insurancePolicyDto.credentialCategoryDesc"
							value="%{insurancePolicyDto.credentialCategoryDesc}" maxlength="20"
							size="15" cssStyle="width: 156px;" cssClass="shadowBox grayText"></s:textfield>
						</p>
					</td>
					</c:if>
					<td width="338">

						<c:choose>
						<c:when
							test="${fieldErrors['insurancePolicyDto.policyNumber'] == null}">
							<p class="paddingBtm">
						</c:when>
						<c:otherwise>
							<p class="errorBox">
						</c:otherwise>
						</c:choose>
						<label>
							Policy Number
						</label>
						<br />
						<s:textfield name="insurancePolicyDto.policyNumber"
							value="%{insurancePolicyDto.policyNumber}" maxlength="20"
							size="15" cssStyle="width: 144px;" cssClass="shadowBox grayText"></s:textfield>
						</p>
						<c:choose>
						<c:when
							test="${fieldErrors['insurancePolicyDto.agencyName'] == null}">
							<p class="paddingBtm">
						</c:when>
						<c:otherwise>
							<p class="errorBox">
						</c:otherwise>
						</c:choose>
						<label>
							Agency Name
						</label>
						<br />
						<s:textfield name="insurancePolicyDto.agencyName"
							value="%{insurancePolicyDto.agencyName}" maxlength="99" size="45"
							cssStyle="width: 230px;" cssClass="shadowBox grayText"></s:textfield>
						</p>
					</td>
				</tr>
				<tr>
					<td width="100">
						<p>
							<c:choose>
							<c:when
								test="${fieldErrors['insurancePolicyDto.agencyState'] == null}">
								<p class="paddingBtm">
							</c:when>
							<c:otherwise>
								<p class="errorBox">
							</c:otherwise>
							</c:choose>
							<label>
								Agency State
							</label>
							<br />

							<s:select name="insurancePolicyDto.agencyState"
								value="%{insurancePolicyDto.agencyState}"
								list="#application['stateCodes']" listKey="type"
								listValue="descr"></s:select>
						</p>
					</td><p></p>
					<td width="339">
						<p>
							<c:choose>
							<c:when
								test="${fieldErrors['insurancePolicyDto.agencyCountry'] == null}">
								<div class="paddingBtm" style="margin-left: 5px;">
							</c:when>
							<c:otherwise>
								<p class="errorBox">
							</c:otherwise>
							</c:choose>
							<span>	Agency Country (Optional) </span>
							<br />
							<s:textfield name="insurancePolicyDto.agencyCountry"
								value="%{insurancePolicyDto.agencyCountry}" maxlength="15"
								size="15" cssStyle="width: 108px;margin-left: 19px;" cssClass="shadowBox grayText"></s:textfield>
						</div>
					</td>
					<td>
						<p>
							<c:choose>
							<c:when test="${fieldErrors['insurancePolicyDto.amount'] == null}">
								<div class="paddingBtm">
							</c:when>
							<c:otherwise>
								<p class="errorBox">
							</c:otherwise>
							</c:choose>
							<span style="width: 53px; padding-left: 15px;"> Amount $ (Each Occurence) </span>
							<br />
							<s:textfield name="insurancePolicyDto.amount"
								value="%{insurancePolicyDto.amount}" maxlength="15" size="15"
								cssStyle="width: 100px;" cssClass="shadowBox grayText"></s:textfield>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<p>
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
								Issue Date 
							</label>
							<br />
							<s:if test="%{insurancePolicyDto.vendorCredentialId > -1}">
								<s:if test="%{not empty insurancePolicyDto.policyIssueDate}">
									<input type="text" dojoType="dijit.form.DateTextBox"
										constraints="{datePattern:'MM/dd/yyyy'}"
										class="shadowBox" id="modal2ConditionalChangeDate2"
										name="insurancePolicyDto.policyIssueDate"
										value="<s:property value="%{insurancePolicyDto.policyIssueDate}"/>"
										theme="simple" cssStyle="width: 75px;"
										cssClass="shadowBox grayText" />
								</s:if>
								<s:else>
									<input type="text" name="insurancePolicyDto.policyIssueDate"
										dojoType="dijit.form.DateTextBox"
										constraints="{datePattern:'MM/dd/yyyy'}" class="shadowBox"
										id="modal2ConditionalChangeDate2" maxlength="10"
										value="<s:property value="%{insurancePolicyDto.policyIssueDate}"/>" />
								</s:else>
							</s:if>
							<s:else>
							<input type="text" dojoType="dijit.form.DateTextBox"
									constraints="{datePattern:'MM/dd/yyyy'}"
									class="shadowBox" id="modal2ConditionalChangeDate2"
									name="insurancePolicyDto.policyIssueDate"
									value="<s:property value="%{insurancePolicyDto.policyIssueDate}"/>"
									theme="simple" cssStyle="width: 75px;"
									cssClass="shadowBox grayText" />

							</s:else>
						</p>
					</td>
					<td>
					<p></p>
						<c:choose>
						<c:when
							test="${fieldErrors['insurancePolicyDto.policyExpirationDate'] == null}">
							<p class="paddingBtm">
						</c:when>
						<c:otherwise>
							<p class="errorBox">
						</c:otherwise>
						</c:choose>
						<label>
							Expiration Date
						</label>
						<br />
						<s:if test="%{insurancePolicyDto.vendorCredentialId > -1}">
							<s:if test="%{not empty insurancePolicyDto.policyExpirationDate}">
								<input type="text" dojoType="dijit.form.DateTextBox"
									constraints="{datePattern:'MM/dd/yyyy'}"
									class="shadowBox" id="modal2ConditionalChangeDate1"
									name="insurancePolicyDto.policyExpirationDate"
									value="<s:property value="%{insurancePolicyDto.policyExpirationDate}"/>"
									theme="simple" cssStyle="width: 75px;"
									cssClass="shadowBox grayText" />
							</s:if>
							<s:else>
								<input type="text" dojoType="dijit.form.DateTextBox"
									constraints="{datePattern:'MM/dd/yyyy'}"
									class="shadowBox" id="modal2ConditionalChangeDate1"
									name="insurancePolicyDto.policyExpirationDate"
									value="<s:property value="%{insurancePolicyDto.policyExpirationDate}"/>"
									theme="simple" cssStyle="width: 75px;"
									cssClass="shadowBox grayText" />
							</s:else>
						</s:if>
						<s:else>
							<input type="text" dojoType="dijit.form.DateTextBox"
								constraints="{datePattern:'MM/dd/yyyy'}"
								class="shadowBox" id="modal2ConditionalChangeDate1"
								name="insurancePolicyDto.policyExpirationDate"
								value="<s:property value="%{insurancePolicyDto.policyExpirationDate}"/>"
								theme="simple" cssStyle="width: 75px;"
								cssClass="shadowBox grayText" />
						</s:else>
						</p>
					</td>
				</tr>
			</table>
		</div>
		<s:hidden name="insurancePolicyDto.vendorCredentialId"
			value="%{insurancePolicyDto.vendorCredentialId}"></s:hidden>
		<div class="darkGrayModuleHdr">
			Attach Insurance Document
		</div>
		<div class="grayModuleContent mainContentWell">
			<p>
				Please attach an electronic copy of the insurance policy to upload.
				You may remove any files you add during this session, but you may
				not remove any previously uploaded files. A maximum of (1) file may
				be uploaded. Individual file size is limited to 2 MB.
			</p>
			<p class="paddingBtm">
				<label>
					Select file to upload
				</label>
				<br />
				<input type="file" class="shadowBox grayText" style="width: 300px;"
					value="[Select file]" name="insurancePolicyDto.file"
					class="btnBevel uploadBtn" />
				&nbsp;

				<input type="image"
					src="${staticContextPath}/images/images_registration/common/spacer.gif"
					width="72" height="20"
					style="background-image: url(${staticContextPath}/images/images_registration/btn/attach.gif);"
					onclick="javascript:saveInsuranceTypeMethod('jsp/providerRegistration/saveInsuranceTypeActionattachDocument.action?ss=${securityToken}');"
					class="btn20Bevel" />
			</p>


			<div class="filetypes">
				<p>
					Preferred file types:
					<br />
					.JPG | .PDF | .DOC | .GIF
				</p>
			</div>

			<s:if test="%{insurancePolicyDto.vendorCredentialId > -1}">
				<s:if test="%{insurancePolicyDto.credentialDocumentId > 0}">


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
								<s:property
									value="insurancePolicyDto.credentialDocumentFileName" />
							</td>
							<td class="column3">
								<s:property
									value="insurancePolicyDto.credentialDocumentFileSize" />
							</td>
						</tr>
					</table>
				</s:if>
			</s:if>

			<input type="hidden" name="method">
			<s:if test="%{insurancePolicyDto.vendorCredentialId > -1}">
				<s:if test="%{insurancePolicyDto.credentialDocumentId > 0}">
					<s:set name="docId"
						value="%{insurancePolicyDto.credentialDocumentId}"></s:set>
					<s:url
						action="jsp/providerRegistration/saveInsuranceTypeActiondisplayTheDocument.action"
						id="url">
						<s:param name="docId"
							value="%{insurancePolicyDto.credentialDocumentId}" />
					</s:url>
					<div class="clearfix">
						<p>
							<a
								href="jsp/providerRegistration/saveInsuranceTypeActiondisplayTheDocument.action?docId=<s:property value="insurancePolicyDto.credentialDocumentId"/>"
								target="blank"> <img
									src="${staticContextPath}/images/images_registration/common/spacer.gif"
									style="background-image: url(${staticContextPath}/images/images_registration/btn/view.gif);"
									width="72" height="20" /> </a>
							<input type="image"
								onclick="javascript:saveInsuranceTypeMethod('jsp/providerRegistration/saveInsuranceTypeActionremoveDocument.action?method=remove');"
								src="${staticContextPath}/images/images_registration/common/spacer.gif"
								width="72" height="20"
								style="background-image: url(${staticContextPath}/images/images_registration/btn/remove.gif);" />
						</p>
					</div>
				</s:if>
			</s:if>
		</div>
		<div class="clearfix">
			<div class="formNavButtons">
				<input type="image"
					src="${staticContextPath}/images/images_registration/common/spacer.gif"
					width="95" height="20"
					style="background-image: url(${staticContextPath}/images/images_registration/btn/updatePolicy.gif);"
					class="btn20Bevel" />
				<input type="hidden" name="insurancePolicyDto.auditTimeLoggingIdNew" value="<%=request.getAttribute("auditTimeLoggingId")%>">
				&nbsp;
				<s:if test="%{insurancePolicyDto.vendorCredentialId > 0}">
					<input onclick="javascript:deleteInsuranceTypeInfo();" type="image"
						src="${staticContextPath}/images/images_registration/common/spacer.gif"
						width="95" height="20"
						style="background-image: url(${staticContextPath}/images/images_registration/btn/removePolicy.gif);"
						class="btn20Bevel" />
				</s:if>
			</div>
		</div>

		<br />
		<!-- 
			<div class="formNavButtons">
				<input type="image"
					src="${staticContextPath}/images/images_registration/common/spacer.gif"
					width="72" height="20"
					style="background-image: url(${staticContextPath}/images/images_registration/btn/previous.gif);"
					class="btn20Bevel" />
				<input
					onclick="javascript:saveInsuranceTypeMethod('jsp/providerRegistration/saveInsuranceTypeActiondoNext.action?method=next');"
					type="image"
					src="${staticContextPath}/images/images_registration/common/spacer.gif"
					width="50" height="20"
					style="background-image: url(${staticContextPath}/images/images_registration/btn/next.gif);"
					class="btn20Bevel" />
			</div>
	 -->
		<div class="bottomRightLink">
			<c:if test= "${isFromPA != true}">
			<a
				href="javascript:cancelInsurance('jsp/providerRegistration/saveInsuranceTypeActiondoCancel.action');">Cancel</a>
			</c:if>
		</div>
	</s:form>

<!-- END TAB PANE -->
