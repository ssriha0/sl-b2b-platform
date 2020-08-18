<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/struts-tags" prefix="s"%>


<script type="text/javascript"
	src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
	djConfig="isDebug: false, parseOnLoad: true"></script>

<script type="text/javascript">
					dojo.require("dijit.layout.ContentPane");
					dojo.require("dijit.layout.TabContainer");
					dojo.require("dijit.TitlePane");
		</script>



<div dojoType="dijit.TitlePane" title="<fmt:message bundle="${serviceliveCopyBundle}" key="buyer.manage_users.label.MPPref"/>"
	class="contentWellPane">
		<div class="grayModuleHdr">
			<fmt:message bundle="${serviceliveCopyBundle}" key="label.activities"/>
		</div>
		<div class="grayModuleContent mainWellContent">
			<br/>
			<fmt:message bundle="${serviceliveCopyBundle}" key="buyer.manage_users.activities.instr"/>
			<br/>
			<c:forEach items="${permissionSets}" var="permissionSet">
				<br/>
				<br/>
				<b>${permissionSet.permissionSetName}</b>
				<c:forEach items="${permissionSet.activities}" var="activity">
					<br/>
					&nbsp;&nbsp;&nbsp;
					<input type="checkbox" name="activity_cb${activity.activityId}" value="${activity.activityId}"
							<c:if test="${!editable}">DISABLED</c:if>
							<c:forEach items="${buyerAdminManageUsersAddEditDTO.activitiesList}" var="activities">
								<c:if test="${activities.activityId == activity.activityId }">CHECKED</c:if>
 							</c:forEach>	
							<c:if test="${(activity.activityId == 102 || activity.activityId == 106)&& !showRemoveUserButton }">CHECKED</c:if>	
					> ${activity.activityName}

				</c:forEach>
			</c:forEach>
		</div>
	<div class="grayModuleHdr">
		<fmt:message bundle="${serviceliveCopyBundle}" key="buyer.manage_users.label.comm_pref"/>
	</div>
	<div class="grayModuleContent mainWellContent">
		<p>
			<fmt:message bundle="${serviceliveCopyBundle}" key="buyer.manage_users.comm_pref.instr"/>
		</p>
		<table cellpadding="0" cellspacing="0" width="550">
			<tr>
				<td colspan="2">
				<c:choose>
					<c:when
					test="${fieldErrors['buyerAdminManageUsersAddEditDTO.phoneAreaCode'] != null}
								|| ${fieldErrors['buyerAdminManageUsersAddEditDTO.phonePart1'] != null}
										|| ${fieldErrors['buyerAdminManageUsersAddEditDTO.phonePart2'] != null}">
					<p class="errorBox">
					</c:when>
					<c:otherwise><p></c:otherwise>
				</c:choose>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.businessPhone"/>
						</label>
						<br />
						<s:textfield id="phoneBusinessAreaCode" name="phoneBusinessAreaCode"
							cssStyle="width: 30px;" maxlength="3"
							cssClass="shadowBox grayText" theme="simple"
							value="%{buyerAdminManageUsersAddEditDTO.phoneBusinessAreaCode}"
							disabled="%{!#request['editable']}"
							/>
						-
						<s:textfield id="phoneBusinessPart1" name="phoneBusinessPart1"
							cssStyle="width: 30px;" maxlength="3"
							cssClass="shadowBox grayText" theme="simple"
							value="%{buyerAdminManageUsersAddEditDTO.phoneBusinessPart1}"
							disabled="%{!#request['editable']}"
							/>
						-
						<s:textfield id="phoneBusinessPart2" name="phoneBusinessPart2"
							cssStyle="width: 45px;" maxlength="4"
							cssClass="shadowBox grayText" theme="simple"
							value="%{buyerAdminManageUsersAddEditDTO.phoneBusinessPart2}"
							disabled="%{!#request['editable']}"
							/>
													
						<fmt:message bundle="${serviceliveCopyBundle}" key="label.extension"/>
						<s:textfield id="phoneBusinessExt" name="phoneBusinessExt"
							cssStyle="width: 45px;" maxlength="4"
							cssClass="shadowBox grayText" theme="simple"
							value="%{buyerAdminManageUsersAddEditDTO.phoneBusinessExt}"
							disabled="%{!#request['editable']}"
							/>
					</p>
					<c:choose>
					<c:when
					test="${fieldErrors['buyerAdminManageUsersAddEditDTO.faxAreaCode'] != null}
								|| ${fieldErrors['buyerAdminManageUsersAddEditDTO.faxPart1'] != null}
										|| ${fieldErrors['buyerAdminManageUsersAddEditDTO.faxPart2'] != null}">
					<p class="errorBox">
					</c:when>
					<c:otherwise><p></c:otherwise>
					</c:choose>
					 <label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.businessFax"/>&nbsp;
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.optionalWithParn"/>
						</label>
						<br />
						<s:textfield id="faxBusinessAreaCode" name="faxBusinessAreaCode"
							cssStyle="width: 30px;" maxlength="3"
							cssClass="shadowBox grayText" theme="simple"
							value="%{buyerAdminManageUsersAddEditDTO.faxBusinessAreaCode}"
							disabled="%{!#request['editable']}"
							/>
						-
						<s:textfield id="faxBusinessPart1" name="faxBusinessPart1"
							cssStyle="width: 30px;" maxlength="3"
							cssClass="shadowBox grayText" theme="simple"
							value="%{buyerAdminManageUsersAddEditDTO.faxBusinessPart1}"
							disabled="%{!#request['editable']}"
							/>
						-
						<s:textfield id="faxBusinessPart2" name="faxBusinessPart2"
							cssStyle="width: 45px;" maxlength="4"
							cssClass="shadowBox grayText" theme="simple"
							value="%{buyerAdminManageUsersAddEditDTO.faxBusinessPart2}"
							disabled="%{!#request['editable']}"
							/>
						</p>
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.mobilePhone"/>&nbsp;
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.optionalWithParn"/>
						</label>
						<br />
						<s:textfield id="phoneMobileAreaCode" name="phoneMobileAreaCode"
							cssStyle="width: 30px;" maxlength="3"
							cssClass="shadowBox grayText" theme="simple"
							value="%{buyerAdminManageUsersAddEditDTO.phoneMobileAreaCode}"
							disabled="%{!#request['editable']}"
							/>
						-
						<s:textfield id="phoneMobilePart1" name="phoneMobilePart1"
							cssStyle="width: 30px;" maxlength="3"
							cssClass="shadowBox grayText" theme="simple"
							value="%{buyerAdminManageUsersAddEditDTO.phoneMobilePart1}"
							disabled="%{!#request['editable']}"
							/>
						-
						<s:textfield id="phoneMobilePart2" name="phoneMobilePart2"
							cssStyle="width: 45px;" maxlength="4"
							cssClass="shadowBox grayText" theme="simple"
							value="%{buyerAdminManageUsersAddEditDTO.phoneMobilePart2}"
							disabled="%{!#request['editable']}"
							/>
					</p>
				</td>
			</tr>
			<tr>
				<td width="325">
				<c:choose>
					<c:when test="${fieldErrors['buyerAdminManageUsersAddEditDTO.priEmail'] == null}">
						<p>
						</c:when>
					<c:otherwise>
						<p class="errorBox">
					</c:otherwise>						
				</c:choose>	
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.pri.email"/>
						</label>
						<br />
						<s:textfield id="priEmail" name="priEmail"
							cssStyle="width: 250px;" maxlength="90"
							cssClass="shadowBox grayText" theme="simple"
							value="%{buyerAdminManageUsersAddEditDTO.priEmail}"
							disabled="%{!#request['editable']}"
							/>
					</p>
				</td>
				<td width="325">
				<c:choose>
					<c:when
						test="${fieldErrors['buyerAdminManageUsersAddEditDTO.confirmPriEmail'] == null}">
						<p>
						</c:when>
					<c:otherwise>
						<p class="errorBox">
					</c:otherwise>
				</c:choose>	
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.confirm.pri.email"/>
						</label>
						<br />
						<s:textfield id="confirmPriEmail" name="confirmPriEmail"
							cssStyle="width: 250px;" maxlength="90"
							cssClass="shadowBox grayText" theme="simple"
							value="%{buyerAdminManageUsersAddEditDTO.confirmPriEmail}"
							disabled="%{!#request['editable']}"
							/>							
					</p>
				</td>
			</tr>
			<tr>
				<td>
				<c:choose>
					<c:when
						test="${fieldErrors['buyerAdminManageUsersAddEditDTO.altEmail'] == null}">
						<p>
						</c:when>
					<c:otherwise>
						<p class="errorBox">
					</c:otherwise>	
				</c:choose>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.alt.email"/>&nbsp;
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.optionalWithParn"/>
						</label>
						<br />
						<s:textfield id="altEmail" name="altEmail"
							cssStyle="width: 250px;" maxlength="90"
							cssClass="shadowBox grayText" theme="simple"
							value="%{buyerAdminManageUsersAddEditDTO.altEmail}"
							disabled="%{!#request['editable']}"
							/>							
					</p>
				</td>
				<td>
				<c:choose>
					<c:when
						test="${fieldErrors['buyerAdminManageUsersAddEditDTO.confirmAltEmail'] == null}">
						<p>
						</c:when>
					<c:otherwise>
						<p class="errorBox">
					</c:otherwise>
				</c:choose>	
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.confirm.alt.email"/>&nbsp;
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.optionalWithParn"/>
						</label>
						<br />
						<s:textfield id="confirmAltEmail" name="confirmAltEmail"
							cssStyle="width: 250px;" maxlength="90"
							cssClass="shadowBox grayText" theme="simple"
							value="%{buyerAdminManageUsersAddEditDTO.confirmAltEmail}"
							disabled="%{!#request['editable']}"
							/>							
					</p>
				</td>
			</tr>
		</table>
	</div>
	<div class="grayModuleHdr">
		<fmt:message bundle="${serviceliveCopyBundle}" key="buyer.manage_users.label.maxspendlimit"/>
	</div>
	<div class="grayModuleContent mainWellContent">
		<p>
			<fmt:message bundle="${serviceliveCopyBundle}" key="buyer.manage_users.maxspendlimit.instr"/>
		</p>
		<c:choose>
		<c:when
			test="${fieldErrors['buyerAdminManageUsersAddEditDTO.maxSpendLimit'] == null}">
			<p>
		</c:when>
		<c:otherwise>
			<p class="errorBox">
		</c:otherwise>	
		</c:choose>
			<fmt:message bundle="${serviceliveCopyBundle}" key="buyer.manage_users.label.maxspendlimit.long"/>
			<br>
			$
			<s:textfield id="maxSpendLimit" name="maxSpendLimit"
				cssStyle="width: 80px;" maxlength="15"
				cssClass="shadowBox grayText" theme="simple"
				value="%{buyerAdminManageUsersAddEditDTO.maxSpendLimit}"
				disabled="%{!#request['editable']}"/>							
				<fmt:message bundle="${serviceliveCopyBundle}" key="buyer.manage_users.label.nolimit"/>
		</p>
	</div>
</div>
