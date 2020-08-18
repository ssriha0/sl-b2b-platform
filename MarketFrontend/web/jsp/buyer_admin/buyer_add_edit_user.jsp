<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>ServiceLive [Add or Edit User]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
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
		<link rel="stylesheet" type="text/css" href="/ServiceLiveWebUtil/styles/plugins/public.css" 
			media="screen, projection">
			
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/serviceLiveDojoBase.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>

		<script language="JavaScript"
			src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>

		<script type="text/javascript">
		/*
					dojo.require("dijit.layout.ContentPane");
					dojo.require("dijit.layout.TabContainer");
					dojo.require("dijit.TitlePane");
					*/
		</script>

		<script type="text/javascript">
			function confirmDeleteUser()
			{
				if ( window.confirm('Do you really want to remove this user?') )
		        	return true;
		        else
					return false;
			}
		</script>

	</head>
	<body class="tundra">
	    
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="BuyerAdmin.addEditUser"/>
		</jsp:include>	
	
		<div id="page_margins">
			<div id="page">
				<div id="headerSPReg">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>

					<div id="pageHeader">
					</div>
				</div>
				<div class="colLeft711">
					<div class="content">
						<c:if test="${!editable}"><font color="red"><fmt:message bundle="${serviceliveCopyBundle}" key="mamage_users.not_editable_msg"/></font></c:if>
					</div>
					<div id="content_right_header_text">
					<jsp:include page="/jsp/buyerRegistration/message.jsp" flush="true" />
					</div>					
				</div>
				<!-- BEGIN CENTER -->
				<div class="colLeft711">
					<div class="content">
						<s:form
							action="buyerAdminAddEdit_saveUser"
							id="buyerAdminAddEdit_saveUser"
							name="buyerAdminAddEdit_saveUser"
							method="post"
							enctype="multipart/form-data" theme="simple">

							<s:hidden name="termsCondId" id="termsCondId" value="%{buyerAdminManageUsersAddEditDTO.termsCondId}"/>
							<s:hidden name="editable" id="editable" value="%{buyerAdminManageUsersAddEditDTO.editable}"/>
							
							<jsp:include page="validationMessages.jsp" />

							<jsp:include
								page="/jsp/buyer_admin/modules/panel_general_information.jsp" />
								
							<jsp:include
								page="/jsp/buyer_admin/modules/panel_marketplace_preferences.jsp" />
							<jsp:include
								page="/jsp/buyer_admin/modules/panel_terms_and_conditions.jsp" />
                              
							<div class="formNavButtons" id="passwordBT">								
								<c:if test="${editable}" >
								   <s:submit type="submit"
												method="saveUser"																								
												cssClass="button action submit" value="Save" />					       	
									<c:if test="${showRemoveUserButton}">
										<s:submit type="submit"
												method="removeUser"			
												onclick="return confirmDeleteUser()"																					
												cssClass="button action submit" value="Remove User" />
									
									  <c:choose>									
										<c:when test="${neverLoggedIn}">
											<s:submit type="submit"
												method="saveAndResenedWelcomemail"																								
												cssClass="button action submit" value="Resend Registration" />											
										</c:when>	
										<c:otherwise>										 						
											<s:submit type="submit"
												method="saveAndResetPassword"																								
												cssClass="button action submit" value="Reset Password" />											
																						
										</c:otherwise>	
									  </c:choose>	
									</c:if>										
								</c:if>
							</div>
						</s:form>

						<div class="bottomRightLink">
							<a href="buyerAdminManageUsers_execute.action">Cancel</a>
						</div>


					</div>


				</div>

				<div class="clearfix">

				</div>

			</div>

			<div class="colRight255">
			</div>
			<div class="clearfix">
			</div>

			<!-- END CENTER   -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />

		</div>
	</body>
</html>
