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
		<script language="JavaScript"src="${staticContextPath}/javascript/pwdReset.js" type="text/javascript"></script>
		<script language="JavaScript"src="${staticContextPath}/javascript/popupDialog.js" type="text/javascript"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>

		<script>
		$(document).ready(function() {
		    $("#userId").keydown(function(event) {
		        if ( event.keyCode == 46 || event.keyCode == 8 ) { } //for delete & backspace
		        else {// ignores other than number
		           if (event.keyCode < 48 || event.keyCode > 57 ) { event.preventDefault(); }       
		        }
		    });
		});
	var popupid;



	function submitRemove(userId, consumerKey){
	  document.forms['mangeUser'+userId].action="adminManageAPI_removeUser.action";
	  document.forms['mangeUser'+userId].submit();
	}

	function submitAddUser(consumerKey){        
		var form = document.forms['addNewUserForm'];
		    form.action="adminManageAPI_addUser.action";		     		
	 	var arr = document.getElementsByName("userId");
	  	var obj = null;
	  	if (arr.length > 0) {		  
	          obj = arr.item(0);
	          form.userId.value=obj.value;
		}	  		  	  

	  	form.consumerKey.value=consumerKey;
		form.submit();
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
					Manage applications users here.
				</p>


				<div class="clearfix">
					<div class="formNavButtons padd">
						<input type="button" class="button action" onclick="showModal(this, 'modalAddUser')" value="Add A New User" />
					</div>
				</div>
				<div id="modalAddUser" class="jqmWindowPasswordReset">
  					<div class="jqmWindowPasswordResetTop">
  					<div href="#" class="jqmWindowPasswordResetTopLeft">Add a new User</div>
	  				<a href="#" class="jqmClose"><img src="${staticContextPath}/images/btn/x.gif" border="0"/></a> </div>
						<div class="jqmWindowPasswordResetContent">
		  					<h2>Enter user Id 
		  					<s:textfield id="userId" name="userId" theme="simple"/> </h2>

							<div class="jqmWindowPasswordResetBtm">
							<div  class="jqmWindowPasswordResetBtmLeft cancel"> <a href="javascript:hideModal('modalAddUser')">Cancel</a></div>
							<div class="jqmWindowPasswordResetBtmRight">
								<img src="${staticContextPath}/images/btn/spacer.gif" alt=""  class="jqmClose btnPSContinue continue"
								onClick="javascript:submitAddUser('${consumerKey}')"/>
							</div>
						</div>

					</div>
				</div>	  <!-- modalPasswordReset -->

				<s:if test="hasActionErrors() or hasActionMessages()">
					<jsp:include page="/jsp/sl_login/common/errorMessage.jsp" flush="true" />
				</s:if>
				<s:else>
					<p class="success">
						<fmt:message bundle="${serviceliveCopyBundle}" key="manage_users.users" >
							<fmt:param value="${fn:length(userList)}"/>
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
								<fmt:message bundle="${serviceliveCopyBundle}" key="label.api.user" />
							</th>							
						</tr>
						<c:forEach items="${userList}" var="user">
							<tr>

								<td class="col1 first even1 action textleft">
									<s:form name="mangeUser%{#attr.user}" action="adminManageAPI_mangeUser"
										id="adminManageAPI_mangeUser"
										method="post"
										enctype="multipart/form-data" theme="simple">
										<input type="hidden" name="consumerKey" value="${consumerKey}" />
										<input type="hidden" name="userId" value="${user}" />
										<div id="tablePasswordMenu">
									          <div id="link1"><a id="#action-${user}"
						                      href="#action${user}" onmouseover="pwdMenuMouseOver('l1${user}')" onmouseout="pwdMenuMouseOut('l1${user}')" > Take Action &gt;&gt;</a></div>
										      <div class="tablePasswordMenu" onmouseover="pwdMenuMouseOver('l1${user}')" onmouseout="pwdMenuMouseOut('l1${user}')" >
										        <ul id='l1${user}'>
							         	    		 <li><a id='${user}' href='javascript:void(0)' onClick="showModal(this, 'modalRemove-' + '${user}');">Remove</a></li>						         	    		
							         	    	</ul>	
										      </div>
										</div>
										<!-- model to confirm removal start -->
										<div id="modalRemove-${user}" class="jqmWindowPasswordReset">
  											<div class="jqmWindowPasswordResetTop">
  											<div class="jqmWindowPasswordResetTopLeft">Remove Application</div>
											 <a href="#" class="jqmClose"><img src="${staticContextPath}/images/btn/x.gif" border="0"/></a> </div>
										
												<div class="jqmWindowPasswordResetContent">
												  <h2>Are you sure you want to remove user ${user}?</h2>
										
													<div class="jqmWindowPasswordResetBtm">
														<div class="jqmWindowPasswordResetBtmLeft cancel"> <a href="javascript:hideModal('modalRemove-${user}')">Cancel</a></div>
														<div class="jqmWindowPasswordResetBtmRight">															    
															<img src="${staticContextPath}/images/btn/spacer.gif" alt=""  class="jqmClose btnPSContinue continue"
																onClick="javascript:submitRemove('${user}', '${consumerKey}')"/>
															
														</div>
													</div>
												</div>
										</div>	  <!-- model to confirm removal end -->

									</s:form>
								</td>
								<td class="col2 even1 textleft">
									${user}
								</td>								
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
									
			<div style="float:left;padding-left:15px" class="bottomRightLink">
				<a href="adminManageAPI_execute.action">Cancel</a>				
			</div>

	</div>
	<s:form name="addNewUserForm" id="addNewUserForm" action="adminManageAPI_addUser">
		<s:hidden name="userId" value="null" id="userIdNew"/>
		<s:hidden name="consumerKey" value="null" id="consumerKey"/>
	</s:form>	
	<tiles:insertDefinition name="blueprint.base.footer"/>
</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="Admin.manageApplications"/>
	</jsp:include>
	
		
	</body>
</html>
