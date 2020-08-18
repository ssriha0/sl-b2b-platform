<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<%-- test by Carlos--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive User Management</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/admin.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
			
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/public.css" 
			media="screen, projection">
			
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>

		<script type="text/javascript">
					dojo.require("dijit.layout.ContentPane");
					dojo.require("dijit.layout.TabContainer");
					dojo.require("dijit.TitlePane");
		</script>
		
		<script type="text/javascript">
			function confirmDeleteUser()
			{
				if ( window.confirm('Do you really want to remove this user?') )
		        	return true;
		        else
					return false;
			}
			
			function confirmResetPassword()
			{
				if ( window.confirm('Do you really want to reset password?') )
		        	return true;
		        else
					return false;
			}
			
			function submitResendEmailAction(){
	   			document.adminAddEditUser_save.action="adminAddEditUser!resendWelcomeMail.action";		
				document.adminAddEditUser_save.submit();
			}
		</script>

	</head>
	<c:choose>
		<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
			<body class="tundra acquity simple" onload="${onloadFunction}">
		</c:when>
		<c:otherwise>
			<body class="tundra acquity" onload="${onloadFunction}">
		</c:otherwise>
	</c:choose>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="Admin.addEditUser"/>
	</jsp:include>	
	
		<div id="page_margins">
			<div id="page">
				<div id="headerSPReg">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>							
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
				</div>
				<div class="colLeft711">
					<div class="content">					
						<c:if test="${!editable}"><font color="red"><fmt:message bundle="${serviceliveCopyBundle}" key="mamage_users.not_editable_msg"/></font></c:if>
						<jsp:include page="/jsp/buyer_admin/validationMessages.jsp" />						
					</div>
				</div>
						<!-- NEW MODULE/ WIDGET--> 
						 
							<div class="colLeft711"><div class="content">
						<s:form
							action="adminAddEditUser_save"
							id="adminAddEditUser_save"
							name="adminAddEditUser_save"
							method="post"
							enctype="multipart/form-data" theme="simple"><div class="grayModuleHdr">
						</div>
						<div class="grayModuleContent mainWellContent">
							<table cellpadding="0" cellspacing="0" width="550px">
								<tr>
									<td width="325">
										<p>
											<label><fmt:message bundle="${serviceliveCopyBundle}" key="label.firstName"/></label>
											<br />
											<s:textfield id="firstName" name="firstName"
												cssStyle="width: 200px;" cssClass="shadowBox grayText"
												value="%{adminAddEditUserDTO.firstName}"
												theme="simple" disabled="%{!#request['editable']}"/>
										</p>
									</td>
									<td width="325">
										<p>
											<label><fmt:message bundle="${serviceliveCopyBundle}" key="label.middleName"/></label>
											<br />
											<s:textfield id="middleName" name="middleName"
												cssStyle="width: 200px;" cssClass="shadowBox grayText"
												theme="simple"
												value="%{adminAddEditUserDTO.middleName}" disabled="%{!#request['editable']}"/>
											<fmt:message bundle="${serviceliveCopyBundle}" key="label.optional"/>
										</p>
									</td>
								</tr>
								<tr>
									<td>
										<p>
											<label><fmt:message bundle="${serviceliveCopyBundle}" key="label.lastName"/></label>
											<br />
											<s:textfield id="lastName" name="lastName" cssStyle="width: 200px;"
												cssClass="shadowBox grayText" theme="simple"
												value="%{adminAddEditUserDTO.lastName}" disabled="%{!#request['editable']}"/>
										</p>
									</td>
									<td>
										<p>
											<label><fmt:message bundle="${serviceliveCopyBundle}" key="label.suffix"/></label>
											<br />
											<s:textfield id="suffix" name="suffix"
												cssStyle="width: 80px;" cssClass="shadowBox grayText"
												theme="simple" value="%{adminAddEditUserDTO.suffix}" disabled="%{!#request['editable']}"/>
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
							<table cellpadding="0" cellspacing="0" width="550">
								<tr>
									<td width="325">
										<p>
											<label><fmt:message bundle="${serviceliveCopyBundle}" key="sl_admin.manage_users.label.companyRole"/></label>
											<br/>
											<s:select list="#attr.jobRoleList" headerKey="-1"
												headerValue="Select One" listKey="id" listValue="descr"
												theme="simple" cssStyle="width: 256px;" cssClass="grayText"
												disabled="%{!#request['editable']}" value="%{adminAddEditUserDTO.jobRole}"
												id="jobRole" name="jobRole"/>
										</p>
									</td>
									<td width="325">
										<p>
											<label><fmt:message bundle="${serviceliveCopyBundle}" key="label.jobTitle"/></label>
											<br />
											<s:textfield id="jobTitle" name="jobTitle" cssStyle="width: 200px;"
												cssClass="shadowBox grayText" theme="simple"
												value="%{adminAddEditUserDTO.jobTitle}" disabled="%{!#request['editable']}"/>
											<fmt:message bundle="${serviceliveCopyBundle}" key="label.optional"/>
										</p>
									</td>
								</tr>
							</table>
						</div>
						<div class="grayModuleHdr">
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.activities"/>
						</div>
						<div class="grayModuleContent mainWellContent">
							<p>
								<fmt:message bundle="${serviceliveCopyBundle}" key="sl_admin.manage_users.activities.instr"/>.
							</p>

						<c:forEach items="${permissionSets}" var="permissionSet">
							<br>
							<b>${permissionSet.permissionSetName}</b>						
							<c:forEach items="${permissionSet.activities}" var="activity">
								<br>
								&nbsp;&nbsp;&nbsp;
								<input type="checkbox" value="${activity.activityId}" 
								       name="activity_cb${activity.activityId}" 
								    <c:if test="${!editable}">DISABLED</c:if>
									<c:forEach items="${adminAddEditUserDTO.activitiesList}" var="activities">
										<c:if test="${activities.activityId == activity.activityId }">CHECKED</c:if>
									</c:forEach> 
								/> ${activity.activityName}
							</c:forEach>
						</c:forEach>	
						</div>
						<div class="grayModuleHdr">
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.userName"/>
						</div>
						<div class="grayModuleContent mainWellContent">
							<p>
								<fmt:message bundle="${serviceliveCopyBundle}" key="sl_admin.manage_users.username.instr"/>.
							</p>
							<table cellpadding="0" cellspacing="0" width="550">
								<tr>
									<td width="325">
										<p>
											<label><fmt:message bundle="${serviceliveCopyBundle}" key="label.userName"/></label>
											<br />
											<s:textfield required="true" id="username" name="username" cssStyle="width: 200px;" cssClass="shadowBox grayText" theme="simple" value="%{adminAddEditUserDTO.username}" readonly="%{#request['readOnly']}"/>
										</p>
									</td>
									<td width="325">&nbsp;</td>
								</tr>
								<tr>
									<td width="325">
										<p>
											<label><fmt:message bundle="${serviceliveCopyBundle}" key="label.email"/></label>
											<br />
											<s:textfield id="email" name="email" cssStyle="width: 200px;" cssClass="shadowBox grayText" theme="simple" value="%{adminAddEditUserDTO.email}" disabled="%{!#request['editable']}"/>
										</p>
									</td>
									<td width="325">
										<p>
											<label>
												<fmt:message bundle="${serviceliveCopyBundle}" key="label.confirm.email"/>
											</label>
											<br />
											<s:textfield id="emailConfirm" name="emailConfirm" cssStyle="width: 200px;" cssClass="shadowBox grayText" theme="simple" value="%{adminAddEditUserDTO.email}" disabled="%{!#request['editable']}"/>
										</p>
									</td>
									<!-- 
									<c:if test="${neverLoggedIn}">
									<td width="325">
										<p>	<br>	
											<a href="javascript:submitResendEmailAction()">									
												<fmt:message bundle="${serviceliveCopyBundle}" key="label.resend.registration.email"/>
											</a>																						
										</p>
									</td>
									</c:if>
									 -->
								</tr>
							</table>
						</div>

						<div class="formNavButtons" id="passwordBT"> 
								<c:if test="${editable}" >
							       <s:submit type="input"
									method="save"
									cssClass="button action submit"
									theme="simple" value="Save" />								   
									<c:if test="${showRemoveUserButton}">
								       <s:submit
								        onclick="return confirmDeleteUser()"
										method="removeUser"
										cssClass="button action submit"
										theme="simple" value="Remove User" />
									</c:if>
									<c:choose> 
									  <c:when test="${adminAddEditUserDTO.jobRole == '21'}">  <!-- Role Admin is 21 -->
										   <c:if test="${passwordResetForSLAdmin}">
											 <c:choose>
											  <c:when test="${adminAddEditUserDTO.neverLoggedIn == true}">
											    <s:submit								        
												method="resendWelcomeMail"
												cssClass="button action submit"
												theme="simple" value="Resend Registration" />
											  </c:when>
											  <c:otherwise>
											    <s:submit		
											    onclick="return confirmResetPassword()"							        
												method="saveAndResetPassword"
												cssClass="button action submit"
												theme="simple" value="Reset Password" />
											  </c:otherwise>
											 </c:choose>
										    </c:if>
									  </c:when>
									  <c:otherwise>
										  <c:if test="${passwordResetForAllExternalUsers}">
											 <c:choose>
											  <c:when test="${adminAddEditUserDTO.neverLoggedIn == true}">
											    <s:submit								        
												method="resendWelcomeMail"
												cssClass="button action submit"
												theme="simple" value="Resend Registration " />
											  </c:when>
											  <c:otherwise>
											    <s:submit						
											    onclick="return confirmResetPassword()"		        
												method="saveAndResetPassword" 
												cssClass="button action submit"
												theme="simple" value="Reset Password" />
											  </c:otherwise>
											 </c:choose>
										    </c:if>
									    </c:otherwise>									  
									</c:choose>									
								</c:if>
							</div>
						</s:form>

						<div class="bottomRightLink">
							<a href="adminManageUsers_execute.action">Cancel</a>
						</div>
					</div>
				</div>
			</div>
			<!-- END CENTER   -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />

		</div>
		<script language="JavaScript"
			src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
	</body>
</html>
