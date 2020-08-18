<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${SecurityContext.SLAdminInd}">
		<br>

		<div align="center">
			<div dojoType="dijit.form.DropDownButton">

			<span>Approve This Credential </span>
			<span class="dijitTooltipContainer" style="background-color:#FFFFFF;">
				<div dojoType="dijit.TooltipDialog" id="tooltipDlg" title="Enter Login information">
				<s:form id="credForm" name="credForm">
					<input type="hidden" id="credentialRequest.currentKey" name="credentialRequest.currentKey" />
					<input type="hidden" id="credentialRequest.currentVal" name="credentialRequest.currentVal" />
					<input type="hidden" id="credentialRequest.selectType" name="credentialRequest.selectType" />
					<input type="hidden" id="credentialRequest.sendEmail" name="credentialRequest.sendEmail" value="1" />
					<input type="hidden" id="credentialRequest.subSelectName" name="credentialRequest.subSelectName" />
					<input type="hidden" id="credentialRequest.actionSubmitType" name="actionSubmitType" value="VendorCredential"/>
					<input type="hidden" id="credentialRequest.commonReviewNote" name="credentialRequest.commonReviewNote" value="" />
					<input type="hidden" id="credentialRequest.credentialKey" name="credentialRequest.credentialKey" value="${requestScope.credId}"/>
					<input type="hidden" id="vendorCredId" name="vendorCredId" value="0"/>
					<input type="hidden" id="ss" name="ss" value="${securityToken}"/>
				<table width="245px"  border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFCC">
					<tr>
						<td width="231">
							Credential Status 
						</td>
					</tr>
					<tr>
						<td>
							<label>
								<s:select cssStyle="width: 200px;" id="commonSEL2"
									name="credentialRequest.commonSEL2" headerKey="-1" headerValue="Select One"
									list="#attr.audit_cred_selects" listKey="type"
									listValue="descr" multiple="false" size="1" theme="simple"
									onchange="newco.jsutils.handleCommonAJAXCall(this,'commonSEL2','Company Credential','innerT1','CredForm','VendorCredential')" />

							</label>
						</td>
					</tr>
				</table>
				<div name="innerT1" id="innerT1" style="display:none;" >
					<table width="245" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFCC">
						<tr>
							<td>
								Select Reason Code
							</td>
						</tr>
						<tr>
							<td>
								<label>
									<s:select cssStyle="width: 200px;" id="commonSEL2SUB"
										name="credentialRequest.commonSEL2SUB" headerKey="-1" headerValue="N/A"
										list="#attr.pre_selected_audit_selects"
										listKey="type" listValue="descr" multiple="false" size="3"
										theme="simple" />
								</label>
							</td>
						</tr>
						<tr>
							<td>
								<label>

									<input type="radio" name="holder1" value="1"
										onclick="newco.jsutils.handleEmailOption(this,'CredForm')" checked="checked" id="r3">
										Send email notice
								</label>
								<br>
								<label>
									<input type="radio" name="holder1" value="0"
										onclick="newco.jsutils.handleEmailOption(this,'CredForm')" id="r4" />
										Do not send email notice
								</label>
							</td>
						</tr>
						<tr>
						
							<td style="text-align:left">
							
							<input type="hidden" id="credentialRequest.auditTimeLoggingIdNew" name="credentialRequest.auditTimeLoggingIdNew" value="${requestScope.auditTimeLoggingId}"/>
							
							<c:set var="auditTime" value="${requestScope.auditTimeLoggingId}"></c:set>
							<c:if test="${auditTime ==null || auditTime==''}">
							<c:set var="auditTime" value="0"></c:set>
							</c:if>
							
								Review Comments :

							<br/>
								<textarea style="width: 250px;"
									name="commonReviewComment"
									id="commonReviewComment"
									class="shadowBox grayText"
									onblur="newco.jsutils.captureReviewComments()"
									onKeyUp="newco.jsutils.limit_characters_255()"></textarea>
							</td>
						</tr>
						<tr>
							<td>
								<label>
						
										<input type="button" name="Button" value="Save Credential Status"
										onclick="newco.jsutils.handleCredentialAppv('commonSEL2','Company Credential', ${requestScope.credId},${auditTime})" />
								</label>
							</td>
						</tr>
					</table>
				</div>
				<table height=10px>
				</table>
				</s:form>
			</div>
			</span>
		</div><br>
		
		<c:choose>
		<c:when test="${current_provider_credential_status == 'Approved' && current_provider_credential_reason_code !='Verified'}">
		<b><div id="provider_cred_status" class="cred_status">Current status: ${current_provider_credential_status}-${current_provider_credential_reason_code}</div></b>
		</c:when>
		<c:otherwise>
		<b><div id="provider_cred_status" class="cred_status">Current status: ${current_provider_credential_status}</div></b>
		</c:otherwise>
		</c:choose>
		
		</div>

		</c:if>