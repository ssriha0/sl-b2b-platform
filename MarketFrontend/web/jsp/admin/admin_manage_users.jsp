<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive - Administrator Office - Manage Users</title>
		<tiles:insertDefinition name="blueprint.base.meta"/>
			
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/buttons.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/modalPassword.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/tablePassword.css" />
		<script language="JavaScript"src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js" type="text/javascript"></script>
        <script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<script language="JavaScript"src="${staticContextPath}/javascript/pwdReset.js" type="text/javascript"></script>
     <style type="text/css">
	 .ie7 .bannerDiv{margin-left:-1150px;}
			</style>
		<script>		
		var popupid;
		

		function submitResetPassword(rid){   
		  var rid = getSelectedUserId();
		  document.forms['resetPassword'+rid].action="<s:url action="adminAddEditUser_displayEditPage!resetPassword.action"/>";
		  document.forms['resetPassword'+rid].submit();
		}

	</script> 

	</head>

<body id="manage-users">
<div id="wrap" class="container">
	<tiles:insertDefinition name="blueprint.base.header"/>
	<tiles:insertDefinition name="blueprint.base.navigation"/>
	<div id="content" class="span-24 clearfix">		
			
			<div class="content padd">

			<h2 id="page-title">Manage Users</h2>

				<p>
					Manage the accounts of all of the dispatchers, administrators and providers that you have registered on ServiceLive. Clicking on a team member's name will take you to that person's profile, where you can review and edit their public and private information. Click 'add new user' to add new members to your team.				
				</p>
				
								
				
				<div class="clearfix">
					<div class="formNavButtons padd tr">
						<s:form action="adminAddEditUser_displayAddPage.action">
							<input type="submit" class="button action" value="Add A New User" />
						</s:form>
					</div>
				</div>
				
				<s:if test="hasActionErrors() or hasActionMessages()">
					<jsp:include page="/jsp/sl_login/common/errorMessage.jsp" flush="true" />
				</s:if>	
				<s:else>					
					<p class="success">
						<fmt:message bundle="${serviceliveCopyBundle}" key="manage_users.users" >
							<fmt:param value="${fn:length(usersList)}"/>
						</fmt:message>
					</p>
				</s:else>			
				
				<div class="table-wrap">
				<!-- <table class="globalTableLook" cellpadding="0" cellspacing="0"> -->
				<table cellspacing="0" cellpadding="0" class="passwordReset" id="sort3">
						<tr>
							<th class="col1 first odd action">
								<fmt:message bundle="${serviceliveCopyBundle}" key="sl_admin.manage_users.label.admin" />
							</th>
							<th class="col2 even">
								<fmt:message bundle="${serviceliveCopyBundle}" key="sl_admin.manage_users.label.name" />
							</th>
							<th class="col3 even">
								<fmt:message bundle="${serviceliveCopyBundle}" key="sl_admin.manage_users.label.role" />
							</th>
							<th class="col4 even">
								<fmt:message bundle="${serviceliveCopyBundle}" key="sl_admin.manage_users.label.title" />
							</th>
						</tr>
						<c:forEach items="${usersList}" var="user">
							<tr>
							
								<td class="col1 first even1 action textleft">
									<form name="resetPassword${user.resourceId}" action="adminAddEditUser_displayEditPage"
										id="adminAddEditUser_displayEditPage"										
										method="post"
										enctype="multipart/form-data" theme="simple">	
										<input type="hidden" name="username" value="${user.username}">
										<input type="hidden" name="resourceId" value="${user.resourceId}">
										
										<div id="tablePasswordMenu">										
									          <div id="link1"><a id="#action${user.resourceId}" 
						                      href="#action${user.resourceId}" onmouseover="pwdMenuMouseOver('l1${user.resourceId}')" onmouseout="pwdMenuMouseOut('l1${user.resourceId}')" > Take Action &gt;&gt;</a></div>									       
										      <div class="tablePasswordMenu" onmouseover="pwdMenuMouseOver('l1${user.resourceId}')" onmouseout="pwdMenuMouseOut('l1${user.resourceId}')" >
										        <ul id='l1${user.resourceId}'>
							         	    			<li><a id="promoLink" href="adminAddEditUser_displayEditPage!displayEditPage.action?username=${user.username}">Edit Profile</a></li>
							         	    			<c:choose>
															<c:when test="${user.role == 'Admin'}">
																<c:choose>
																<c:when test="${passwordResetForSLAdmin == 'true'}">
																	<li> <a id='${user.resourceId}' href='#${user.resourceId}' onClick="showResetModal(this, '${user.resourceId}');">Reset password</a> </li>
																</c:when>		
																</c:choose>
															</c:when>
															<c:otherwise>
																<c:choose>
																<c:when test="${passwordResetForAllExternalUsers == 'true'}">
																	<li> <a id='${user.resourceId}' href='javascript:void(0)' onClick="showResetModal(this, '${user.resourceId}');">Reset password</a> </li>
																</c:when>		
																</c:choose>
															</c:otherwise>
														</c:choose>							         	    			         	    											
										        </ul>
										      </div>
										    
										  
										</div>
										
										<jsp:include page="/jsp/admin/passwordModal.jsp" flush="true">
		 									<jsp:param name="modalId" value="${user.resourceId}"/>
		 									<jsp:param name="name" value="${user.name}"/>
										</jsp:include>
										   		
																					
									</form>
								</td>
								<td class="col2 even1 textleft">
									<a href="adminAddEditUser_displayEditPage.action?username=${user.username}">${user.name}</a>
									<br>
									(User Id# ${user.resourceId})
								</td>
								<td class="col3 even1 textleft">
									<c:choose>
										<c:when test="${not empty user.role}">${user.role}</c:when>
										<c:otherwise>&nbsp;</c:otherwise>
									</c:choose>
								</td>
								<td class="col4 even1 textleft">
									<c:choose>
										<c:when test="${not empty user.title}">${user.title}</c:when>
										<c:otherwise>&nbsp;</c:otherwise>
									</c:choose>								
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="clearfix">
							<div class="formNavButtons padd">
						<s:form action="adminAddEditUser_displayAddPage.action">
							<input type="submit" class="button action" value="Add A New User" />
						</s:form>
					</div>
				</div>
			</div>


	</div>
	<tiles:insertDefinition name="blueprint.base.footer"/>
</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="Admin.manageUsers"/>
	</jsp:include>
	</body>
</html>
