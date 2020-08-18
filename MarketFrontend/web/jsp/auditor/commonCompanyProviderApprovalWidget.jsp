<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:if test="${requestScope.showAdminWidget != null }">


		<div dojoType="dijit.TitlePane" title="Provider Firm Approval"
			id="widget_member_info_1" style="padding-top: 1px; width: 249px;"
			open="true">

			<span class="dijitInfoNodeInner"> <a href="#"> </a> </span>

			<table width="245" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="231">
						Current Provider Status
						<br>
						<div id="currentStatus">
							<b> ${requestScope.current_provider_status != null ?
								requestScope.current_provider_status : 'N/A'}</b>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<label>
							<s:select cssStyle="width: 200px;" id="commonSEL1"
								name="companyOrServicePro.commonSEL1" headerKey="-1" headerValue="Select One"
								list="#application.audit_selects" listKey="type"
								listValue="descr" multiple="false" size="1" theme="simple"
								onchange="newco.jsutils.handleCommonAJAXCall(this,'commonSEL1','Company Profile','innerT','ProviderCompany')" />
						</label>
					</td>
				</tr>
			</table>
			<div id="innerT" style="display:none">
				<table width="245" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							Select Reason Code
						</td>
					</tr>
					<tr>
						<td>
							<label>
								<s:select cssStyle="width: 200px;" id="commonSEL1SUB"
									name="companyOrServicePro.commonSEL1SUB" headerKey="-1" headerValue="N/A"
									list="#attr.pre_selected_audit_selects" listKey="type"
									listValue="descr" multiple="true" size="3" theme="simple" />
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label>
								
								<input type="radio" name="companyOrServicePro.sendEmailAppr" value="1"
									checked="checked" onclick="newco.jsutils.handleEmailOption(this)" id="r1" />
									Send email notice
							</label>
							<br>
							<label>
								
								<input type="radio" name="companyOrServicePro.sendEmailAppr" value="0"
									onclick="newco.jsutils.handleEmailOption(this)" id="r2" />
									Do not send email notice
							</label>
						</td>
					</tr>
					<tr>
						<td>
							<label>
								<input type="button" name="Button" value="Save Provider Status"
									onclick="newco.jsutils.handleProviderFirmAppv('commonSEL1','Company Profile')" />
							</label>
						</td>
					</tr>
				</table>
			</div>
			<table height=10px>
			</table>
		</div>

		

		<div dojoType="dijit.TitlePane" title="Add Audit Note"
			id="widget_add_note_1" style="padding-top: 1px; width: 249px;"
			open="false">
			<span class="dijitInfoNodeInner"><a href=""></a> </span>
			<div class="dijitReset">
				<div class="dijitTitlePaneContentInner">
					<!-- nested divs because wipeIn()/wipeOut() doesn't work right on node w/padding etc.  Put padding on inner div. -->
					<table cellpadding="0" cellspacing="0" border="0">
						<!-- <tr>
							<td nowrap="true" colspan=2>
								<label style="display: none; color: red" id="subjectLabelMsg">
									Subject is required
								</label>
							</td>
						</tr> -->
						<tr>
							<td nowrap="true" colspan=2>
								<label style="display: none; color: red" id="subjectLabelMsg1">
									Message is required
								</label>
							</td>
						</tr>
						<tr class="error">
							<td colspan="2" class="errMsg alignRight">
								<div id="addNoteWidgetResponseMessage1"></div>
							</td>
						</tr>
						<tr>
							<td class="labelLeft" nowrap="true" colspan=2>
								Add&nbsp;Note
							</td>
						</tr>
						<!-- <tr>
							<td class="labelLeft alignRight">
								Subject
							</td>
							<td class="alignRight">
								<input type="text" name="commonNoteSubject" id="subject"
									onblur="newco.jsutils.captureNote()" style="width: 150px;"
									class="shadowBox grayText" onfocus="newco.jsutils.resetAddNoteSubject()"
									value="[Subject]" />
							</td>
						</tr> -->
						<tr>
							<td class="labelLeft">
								Message
							</td>
							<td class="alignRight">
								<textarea style="width: 150px;" name="companyOrServicePro.commonMessageNote"
									onblur="newco.jsutils.captureNote()" id="message" class="shadowBox grayText"
									onfocus="newco.jsutils.resetAddNoteMessage()">[Message]</textarea>
							</td>
						</tr>
						<tr>
							<td colspan=2>
								&nbsp;
							</td>
						</tr>
					</table>
					<ul class="titlePaneBtns">
						<li>
							<a href="#" onclick="newco.jsutils.commonSubmitAddNote('theFormContainer')"> <img
									src="${staticContextPath}/images/spacer.gif" width="72"
									height="22"
									style="background-image: url(${staticContextPath}/images/btn/submit.gif);"
									class="btnBevel" /> </a>
						</li>
						<li>
							<a onClick="newco.jsutils.resetNoteWidget()"> <img
									src="${staticContextPath}/images/common/spacer.gif"
									width="72" height="22"
									style="background-image: url(${staticContextPath}/images/btn/cancel.gif);"
									class="btnBevel" /> </a>
						</li>
					</ul>
				</div>
			</div>
		</div>
</c:if>
