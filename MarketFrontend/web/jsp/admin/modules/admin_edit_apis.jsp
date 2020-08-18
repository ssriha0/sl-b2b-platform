<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<%-- test by Carlos--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive User Management</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/admin.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">

		<link rel="stylesheet" type="text/css" href="/ServiceLiveWebUtil/styles/plugins/public.css"
			media="screen, projection">

		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/nav.js"></script>

		<script type="text/javascript">
			function submitResendEmailAction(){
	   			document.adminAddEditUser_save.action="adminAddEditUser!resendWelcomeMail.action";
				document.adminAddEditUser_save.submit();
			}
		</script>	
			 <script type="text/javascript" language="javascript">
			 jQuery( function() {
				 jQuery( '.checkAll' ).change(function() {
						var grpIndex = jQuery( this ).val();						
						jQuery( '.apiGrp'+grpIndex ).prop( 'checked', jQuery( this ).is( ':checked' ) ? 'checked' : '' );
						jQuery( this ).next().text( jQuery( this ).is( ':checked' ) ? 'Uncheck All' : 'Check All' );
					});
				});

			</script>


	</head>

<body class="tundra acquity" onload="${onloadFunction}">
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
				</div>
						<!-- NEW MODULE/ WIDGET-->

						<div class="colLeft711"><div class="content">
						
						<s:form action="adminManageAPI_modifyPermissions"							
							name="adminManageAPI_modifyPermissions"
							method="post" enctype="multipart/form-data" theme="simple">
						<div class="grayModuleHdr">
							<fmt:message bundle="${serviceliveCopyBundle}" key="label.api.activities"/>
						</div>
						<div class="grayModuleContent mainWellContent">
							<p>
								<fmt:message bundle="${serviceliveCopyBundle}" key="sl_admin.manage_apis.activities.instr"/>.
							</p>

						<c:forEach items="${apiGroupList}" var="permissionSet" varStatus="rowCounter">
							<br>
							<b>${permissionSet.groupName}</b>
							<div style="float:right;padding-right:230px">
								<input type="checkbox"   class="checkAll" value="${rowCounter.count}" /><b>Check All</b>
							</div>
						<table  id="apiGrp${rowCounter.count}" cellspacing="1" cellpadding="1" class="passwordReset" style='table-layout:fixed'>
							<col width=360>
 							<col width=70>
 							<col width=70>
 							<col width=70>
 							<col width=70>
							
							<c:forEach items="${permissionSet.permissions}" var="activity">
							<tr>
								<td>
								&nbsp;&nbsp;&nbsp;
								<ui>${activity.name}</ui>
								<br/>&nbsp;&nbsp;&nbsp;
								<ui>('${activity.url}')</ui>
								</td>
								<td>
									<c:choose>
									<c:when test="${activity.get != null}">
										<c:choose>
											<c:when test="${activity.get == true}">
												<input class="apiGrp${rowCounter.count}" type="checkbox" name="permission"  value="${activity.url}-:-get" checked/>
									    	</c:when>
									    	<c:otherwise>
									    		<input class="apiGrp${rowCounter.count}"  type="checkbox" name="permission"  value="${activity.url}-:-get"/>												
									    	</c:otherwise>
									     </c:choose>
									</c:when>
									<c:otherwise>
										<input type="checkbox" value="permission" disabled/>
									</c:otherwise>
									</c:choose>
									<ui>get</ui>
								</td>
								<td>
									<c:choose>
									<c:when test="${activity.post != null}">
										<c:choose>
											<c:when test="${activity.post == true}">
												<input class="apiGrp${rowCounter.count}"  type="checkbox" name="permission"  value="${activity.url}-:-post" checked/>
									    	</c:when>
									    	<c:otherwise>
												<input class="apiGrp${rowCounter.count}"  type="checkbox" name="permission"  value="${activity.url}-:-post" />
									    	</c:otherwise>
									     </c:choose>
									</c:when>
									<c:otherwise>
										<input type="checkbox" value="permission" disabled/>
									</c:otherwise>
									</c:choose>
									<ui>post</ui>
								</td>
								<td>
									<c:choose>
									<c:when test="${activity.put != null}">
										<c:choose>
											<c:when test="${activity.put == true}">
												<input class="apiGrp${rowCounter.count}"  type="checkbox" name="permission"  value="${activity.url}-:-put" checked/>
									    	</c:when>
									    	<c:otherwise>
												<input class="apiGrp${rowCounter.count}"  type="checkbox" name="permission"  value="${activity.url}-:-put" />
									    	</c:otherwise>
									     </c:choose>
									</c:when>
									<c:otherwise>
										<input  type="checkbox" value="permission" disabled/>
									</c:otherwise>
									</c:choose>
									<ui>put</ui>
								</td>
								<td>
									<c:choose>
									<c:when test="${activity.delete != null}">
										<c:choose>
											<c:when test="${activity.delete == true}">
												<input class="apiGrp${rowCounter.count}"  type="checkbox" name="permission"  value="${activity.url}-:-delete" checked/>
									    	</c:when>
									    	<c:otherwise>
												<input class="apiGrp${rowCounter.count}"  type="checkbox" name="permission"  value="${activity.url}-:-delete" />
									    	</c:otherwise>
									     </c:choose>
									</c:when>
									<c:otherwise>
										<input   type="checkbox" value="permission" disabled/>
									</c:otherwise>
									</c:choose>
									<ui>delete</ui>
								</td>

							</tr>
							</c:forEach>
							</table>
						</c:forEach>
						</div>

						<div class="formNavButtons" id="passwordBT">
						    <s:submit type="input"									
									cssClass="button action submit"
									theme="simple" value="Update" />
									
									
						</div>
					</s:form>					
					
				  </div>							
						<div class="bottomRightLink">
							<a href="adminManageAPI_execute.action">Cancel</a>
						</div>
					</div>
				</div>
			
			<!-- END CENTER   -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
		</div>

	</body>
</html>
