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
		
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	      .ie7 .bannerDiv{margin-left:-1150px;}
		</style>
	</head>

<body id="manage-users">
<div id="wrap" class="container">
	<tiles:insertDefinition name="blueprint.base.header"/>
	<tiles:insertDefinition name="blueprint.base.navigation"/>
	<div id="content" class="span-24 clearfix">

			<div class="content padd">

			<h2 id="page-title">Manage Applications</h2>

				<p>
					Manage all applications here.
				</p>


				<div class="clearfix">
					<div class="formNavButtons padd">
						<input type="button" class="button action" onclick="showModal(this, 'modalAddApplication')" value="Add A New Application" />
					</div>
				</div>
				<div id="modalAddApplication" class="jqmWindowPasswordReset">
  					<div class="jqmWindowPasswordResetTop">
  					<div href="#" class="jqmWindowPasswordResetTopLeft">Add a new Application</div>
	  				<a href="#" class="jqmClose"><img src="${staticContextPath}/images/btn/x.gif" border="0"/></a> </div>
						<div class="jqmWindowPasswordResetContent">
		  					<h2>Enter name of the Application
		  					<s:textfield id="newId" name="appName" theme="simple"/> </h2>

							<div class="jqmWindowPasswordResetBtm">
							<div class="jqmWindowPasswordResetBtmLeft cancel"> <a href="javascript:hideModal('modalAddApplication')">Cancel</a></div>
							<div class="jqmWindowPasswordResetBtmRight">
								<img src="${staticContextPath}/images/btn/spacer.gif" alt=""  class="jqmClose btnPSContinue continue"
								onClick="javascript:submitFormAddApplication()"/>
							</div>
						</div>

					</div>
				</div>	  <!-- modalPasswordReset -->
				<s:form name="addNewApplicationForm" id="addNewApplicationForm" action="adminManageAPI_addApplication">
					<s:hidden name="name" value="null" id="nameId"/>
				</s:form>
				<s:if test="hasActionErrors() or hasActionMessages()">
					<jsp:include page="/jsp/sl_login/common/errorMessage.jsp" flush="true" />
				</s:if>
				<s:else>
					<p class="success">
						<fmt:message bundle="${serviceliveCopyBundle}" key="manage_users.applications" >
							<fmt:param value="${fn:length(list)}"/>
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
								<fmt:message bundle="${serviceliveCopyBundle}" key="sl_admin.manage_apis.label.key" />
							</th>
							<th class="col4 even">
								<fmt:message bundle="${serviceliveCopyBundle}" key="sl_admin.manage_apis.label.pass" />
							</th>
						</tr>
						<c:forEach items="${list}" var="user">
							<tr>

								<td class="col1 first even1 action textleft">
									<form name="resetPassword${user.consumerKey}" action="adminManageAPI_displayEditPage"
										id="adminManageAPI_displayEditPage"
										method="post"
										enctype="multipart/form-data" theme="simple">
										<input type="hidden" name="consumerKey" value="${user.consumerKey}" >
										<input type="hidden" name="consumerName" value="${user.consumerName}" >
										<div id="tablePasswordMenu">
									          <div id="link1"><a id="#action-${user.consumerKey}"
						                      href="#action${user.consumerKey}" onmouseover="pwdMenuMouseOver('l1${user.consumerKey}')" onmouseout="pwdMenuMouseOut('l1${user.consumerKey}')" > Take Action &gt;&gt;</a></div>
										      <div class="tablePasswordMenu" onmouseover="pwdMenuMouseOver('l1${user.consumerKey}')" onmouseout="pwdMenuMouseOut('l1${user.consumerKey}')" >
										        <ul id='l1${user.consumerKey}'>
							         	    		<li><a id="promoLink" href="adminManageAPI_displayEditPage!displayEditPage.action?consumerKey=${user.consumerKey}">Edit</a></li>							         	    		
							         	    		<li> <a id='${user.consumerKey}' href='#${user.consumerKey}' onClick="showResetModal(this, '${user.consumerKey}');">Reset password</a> </li>							         	    		 
							         	    		<c:if test="${user.internalConsumer == false}">							         	    		   
							         	    		   <li><a id='${user.consumerKey}' href="adminManageAPI_displayManageUserPage.action?consumerKey=${user.consumerKey}">Manage Users</a></li>
							         	    		   <li><a id='${user.consumerKey}' href='javascript:void(0)' onClick="showModal(this, 'modalRemoveApp' + '${user.consumerKey}');">Remove</a></li>
							         	    		</c:if> 
							         	    	</ul>	
										      </div>


										</div>

										<jsp:include page="/jsp/admin/passwordAPIModal.jsp" flush="true">
		 									<jsp:param name="modalId" value="${user.consumerKey}"/>
		 									<jsp:param name="name" value="${user.consumerName}"/>
										</jsp:include>


									</form>
								</td>
								<td class="col2 even1 textleft">
									<a href="adminManageAPI_displayEditPage.action?consumerKey=${user.consumerKey}">${user.consumerName}</a>
								</td>
								<td class="col3 even1 textleft">
									<c:choose>
										<c:when test="${not empty user.consumerKey}"><div style="word-wrap:break-word;width:300px">${user.consumerKey}</div></c:when>
										<c:otherwise>&nbsp;</c:otherwise>
									</c:choose>
								</td>
								<td class="col4 even1 textleft">
									<c:choose>
										<c:when test="${not empty user.consumerPassword}"><div style="word-wrap:break-word;width:300px">${user.consumerPassword}</div></c:when>
										<c:otherwise>&nbsp;</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<!--
				<s:form name="addNewApplicationForm" action="adminManageAPI_addApplication">
				  <div class="clearfix">
					<div class="formNavButtons padd">
						<input type="button" class="button action" onclick="showAddAppModal(this)" value="Add A New Application" />
					</div>
				  </div>
				</s:form>
				 -->
			</div>

	</div>

	<tiles:insertDefinition name="blueprint.base.footer"/>
</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="Admin.manageApplications"/>
	</jsp:include>
	
		<script language="JavaScript"src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js" type="text/javascript"></script>
		<script language="JavaScript"src="${staticContextPath}/javascript/pwdReset.js" type="text/javascript"></script>
		<script language="JavaScript"src="${staticContextPath}/javascript/popupDialog.js" type="text/javascript"></script>
		<script>
			var popupid;
		
			function submitResetPassword(rid){
			  document.forms['resetPassword'+rid].action="adminManageAPI_displayEditPage!resetPassword.action";
			  document.forms['resetPassword'+rid].submit();
			}
		
			function submitRemoveApplication(rid){
				  document.forms['resetPassword'+rid].action="adminManageAPI_removeApplication.action";
				  document.forms['resetPassword'+rid].submit();
			}
		
		
			function submitFormAddApplication(){
			  var form = document.addNewApplicationForm;
		 	  var arr = document.getElementsByName("appName");
		  	  var obj = null;
		  	  if (arr.length > 0) {
		        obj = arr.item(0);
			    document.getElementById("nameId").value=obj.value;
			    form.submit();
			   }
			}
		</script>
		
	</body>
</html>
