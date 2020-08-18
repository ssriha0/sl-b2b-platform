<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<div dojoType="dijit.TitlePane" title="<fmt:message bundle="${serviceliveCopyBundle}" key="label.generalInfo"/>"
	class="contentWellPane">


	<!-- NEW MODULE/ WIDGET-->
	<div class="grayModuleHdr">
		<fmt:message bundle="${serviceliveCopyBundle}" key="buyer.manage_users.label.personal_info"/>
	</div>
	<div class="grayModuleContent mainWellContent">
		<table cellpadding="0" cellspacing="0" width="550px">
			<tr>
				<td width="325">
				<c:choose>
					<c:when
					test="${fieldErrors['buyerAdminManageUsersAddEditDTO.firstName'] != null}">
					<p class="errorBox">
					</c:when>
					<c:otherwise><p></c:otherwise>
				</c:choose>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.firstName"/>
						</label>
						<br />
						<s:textfield  id="firstName" name="firstName" value="%{buyerAdminManageUsersAddEditDTO.firstName}" 
						  cssStyle="width: 200px;" cssClass="shadowBox grayText" theme="simple" disabled="%{!#request['editable']}"/>
					</p>
				</td>
				<td width="325">
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.middleName"/>
						</label>
						<br />
						<s:textfield id="middleName" name="middleName" 
						value="%{buyerAdminManageUsersAddEditDTO.middleName}"cssStyle="width: 200px;" 
						cssClass="shadowBox grayText" theme="simple" disabled="%{!#request['editable']}"/>
						<fmt:message bundle="${serviceliveCopyBundle}" key="label.optional"/>
					</p>
				</td>
			</tr>
			<tr>
				<td width="325">
					<c:choose>
					<c:when
					test="${fieldErrors['buyerAdminManageUsersAddEditDTO.lastName'] != null}">
					<p class="errorBox">
					</c:when>
					<c:otherwise><p></c:otherwise>
					</c:choose>
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.lastName"/>
						</label>
						<br />
						<s:textfield id="lastName" name="lastName" value="%{buyerAdminManageUsersAddEditDTO.lastName}" 
						cssStyle="width: 200px;" cssClass="shadowBox grayText" theme="simple" disabled="%{!#request['editable']}"/>					
					</p>
				</td>
				<td width="325">
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.suffix"/>
						</label>
						<br />
						<s:textfield id="suffix" name="suffix" value="%{buyerAdminManageUsersAddEditDTO.suffix}" 
						cssStyle="width: 80px;" cssClass="shadowBox grayText" theme="simple" disabled="%{!#request['editable']}"/>
						<fmt:message bundle="${serviceliveCopyBundle}" key="label.optional"/>
					</p>
				</td>
			</tr>
		</table>
	</div>
	<div class="grayModuleHdr">
		<fmt:message bundle="${serviceliveCopyBundle}" key="label.jobAndRole"/>
	</div>
	<div class="grayModuleContent mainWellContent">
		<p>
			<fmt:message bundle="${serviceliveCopyBundle}" key="buyer.manage_users.jobrole.instr"/>
		</p>
		<table cellpadding="0" cellspacing="0" width="550px">
			<tr>
				<td width="425">
					<c:choose>
					<c:when
					test="${fieldErrors['buyerAdminManageUsersAddEditDTO.jobRole'] != null}">
					<p class="errorBox">
					</c:when>
					<c:otherwise><p></c:otherwise>
					</c:choose>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="buyer.manage_users.label.companyRole"/>
						</label>
						<br />
						<c:forEach items="${companyRoles}" var="companyRole" >
							<p>
								<input type="checkbox" name="companyrole${companyRole.id}" value="${companyRole.id}"
								 	<c:if test="${!editable}">DISABLED</c:if>
									<c:forEach items="${buyerAdminManageUsersAddEditDTO.jobRoleList}" var="role">
										<c:if test="${companyRole.id == role.companyRoleId }">CHECKED</c:if>
									</c:forEach> 								
								> ${companyRole.descr}
							</p>
						</c:forEach>	
					</p>
				</td>
				<td width="325">
					<p>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.jobTitle"/>
						</label>
						<br />
						<s:textfield id="jobTitle" name="jobTitle" value="%{buyerAdminManageUsersAddEditDTO.jobTitle}" 
						cssStyle="width: 200px;" cssClass="shadowBox grayText" theme="simple" disabled="%{!#request['editable']}"/>
						<fmt:message bundle="${serviceliveCopyBundle}" key="label.optional"/>
					</p>
				</td>
			</tr>
		</table>
	</div>
		
	<div class="grayModuleHdr">
		<fmt:message bundle="${serviceliveCopyBundle}" key="label.userName"/>
	</div>
	<div class="grayModuleContent mainWellContent">
		<p>
			<fmt:message bundle="${serviceliveCopyBundle}" key="buyer.manage_users.username.instr"/>
		</p>
		<table cellpadding="0" cellspacing="0" width="550">
			<tr>
				<td width="325">
					<c:choose>
					<c:when
					test="${fieldErrors['buyerAdminManageUsersAddEditDTO.userName'] != null}">
					<p class="errorBox">
					</c:when>
					<c:otherwise><p></c:otherwise>
					</c:choose>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.userName"/>
						</label>
						<br />
						<s:textfield id="userName" name="userName" value="%{buyerAdminManageUsersAddEditDTO.userName}" 
						cssStyle="width: 200px;" cssClass="shadowBox grayText" theme="simple" readonly="%{#request['readonly']}"/>
					</p>
				</td>
			</tr>
			<tr>
				<td width="325">
					<c:choose>
					<c:when
					test="${fieldErrors['buyerAdminManageUsersAddEditDTO.userNameConfirmation'] != null}">
					<p class="errorBox">
					</c:when>
					<c:otherwise><p></c:otherwise>
					</c:choose>
						<label>
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.userNameConfirmation"/>
						</label>
						<br />
						<s:textfield id="userNameConfirmation" name="userNameConfirmation" value="%{buyerAdminManageUsersAddEditDTO.userNameConfirmation}" 
						cssStyle="width: 200px;" cssClass="shadowBox grayText" theme="simple" readonly="%{#request['readonly']}"/>
					</p>
				</td>
				<td width="325">
					&nbsp;
				</td>
			</tr>
		</table>
	</div>
</div>
