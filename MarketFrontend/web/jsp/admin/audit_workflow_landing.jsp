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
		<title>ServiceLive - Search Portal</title>
		<tiles:insertDefinition name="blueprint.base.meta"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/admin/auditor-workflow.css" media="screen, projection">
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" media="screen, projection">
		
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/admin/search-portal.css" media="screen, projection">
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	      .ie7 .bannerDiv{margin-left:-1150px;}
	      
	      .refreshMsg{
			background-image: url(${staticContextPath}/images/ajax-loader.gif);
			background-position: 20px center;
			background-repeat: no-repeat;
			padding-left: 50px; 
			padding-top: 5px;
			height: 20px;
			}
			
			.waitLayer{
			display: none;
			z-index: 999999999;
			height: 40px; 
			overflow: auto; 
			position: fixed;
			top: 250px;
			left: 45%;
			border-style:double;
			background-color: #EEEEEE;
			border-color: #BBBBBB;
			width: 125px;
			border-width: 4px;
			-webkit-box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);
			-moz-box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);
			box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);
			}
		</style>
		<script type="text/javascript">
		
			function disableCursor(){
        	jQuery("#overLay").show();
       		 }
			
			function enableCursor(){
			jQuery("#overLay").hide();
			}

		    function fnWaitForResponseShow(msg){
		    	jQuery("#loadMsg").html(msg);
		        jQuery("#waitPopUp").show();
		 }
		        function fnWaitForResponseClose(){
		          jQuery("#loadMsg").html('');
		         jQuery("#waitPopUp").hide();
		 }
			function resetField()
			{
				document.getElementById('firmId').value = "";
				jQuery("#errorMessage").css("display","none");
			}
		
			function updateQueue()
			{
				document.getElementById('firmId').value = "";
				jQuery("#errorMessage").css("display","none");
				var prim_list = document.getElementById('primary_industry').value;
				var cred_list = document.getElementById('credential_list').value;
				var cat_list = document.getElementById('category_list').value;
				var role_list = document.getElementById('role_list').value;
				
				$("#post_area").load("powerAuditorWorkflowAction_getGridData.action?primary_industry_id=" + prim_list + "&role_id=" + role_list + "&credential_id=" + cred_list + "&category_id=" + cat_list);
			}
			
			
			function firmIdKeyPress(e)
			{
				var firm_id = jQuery("#firmId").val();
				 if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57)){
	   			return false;
	 			}
			 };
			
			function updateQueueFirmId(id)
			{
				jQuery("#errorMessage").css("display","none");
				var pattern = new RegExp('^[0-9]*$');
				var firmId = jQuery("#firmId").val();
				firmId=$.trim(firmId);
				var dn = pattern.exec(firmId);
				var firmId = firmId.length;
				if(('Search'==id) && ("" == firmId || null == firmId || 0 == firmId || null == dn)){
					jQuery("#errorMessage").css("display","block");
					fnWaitForResponseClose();
					enableCursor();
					return;
				}
				
				disableCursor();
				fnWaitForResponseShow("Loading...");
				var prim_list = document.getElementById('primary_industry').value;
				var cred_list = document.getElementById('credential_list').value;
				var cat_list = document.getElementById('category_list').value;
				var role_list = document.getElementById('role_list').value;
				var firm_id = document.getElementById('firmId').value;
				
				$("#post_area").load("powerAuditorWorkflowAction_getGridData.action?primary_industry_id=" + prim_list + "&role_id=" + role_list + "&credential_id=" + cred_list + "&category_id=" + cat_list +"&firm_id=" + firm_id, function(data){
					
					var firmError = jQuery("#firmError").val();	
			  		if('Invalid Firm Id' == firmError && 'Search'==id){
						jQuery("#errorMessage").css("display","block");
						fnWaitForResponseClose();
						enableCursor();
			  		}else{
			  			jQuery("#errorMessage").css("display","none");
			  			fnWaitForResponseClose();
						enableCursor();
			  		}
				});
			}
			
			function updateRole()
			{
				var role_list = document.getElementById('role_list').value;
				if (role_list == '-1')
				{
					document.getElementById('credential_list').disabled = true;
					document.getElementById('category_list').disabled = true;
				}
				else
				{
					document.getElementById('credential_list').disabled = false;
					document.getElementById('category_list').disabled = true;
				}
				
				document.getElementById('category_list').value = -1;
				document.getElementById('credential_list').value = -1;
				
				$("#credential_list").load("powerAuditorWorkflowAction_credentialByRole.action?role_id=" + role_list);
				$("#category_list").load("powerAuditorWorkflowAction_credentialByCat.action?credential_id=-1&role_id=" + role_list);
			}

			function updateCat()
			{
				var cred_list = document.getElementById('credential_list').value;
				var role_list = document.getElementById('role_list').value;
				if (document.getElementById('credential_list').value == '-1')
				{
					document.getElementById('category_list').disabled = true;
				}
				else
				{
					document.getElementById('category_list').disabled = false;
				}

				document.getElementById('category_list').value = -1;
				
				$("#category_list").load("powerAuditorWorkflowAction_credentialByCat.action?role_id=" + role_list + "&credential_id=" + cred_list);
			}
		</script>
		</head>

	<body id="auditor-workflow">

		<div id="wrap" class="container">
			<tiles:insertDefinition name="blueprint.base.header"/>
			<tiles:insertDefinition name="blueprint.base.navigation"/>
			<div id="content" class="span-24 clearfix">		
				<div id="sidebar" class="span-5">
					<jsp:include page="/jsp/admin/modules/sb_tools.jsp" />
					<jsp:include page="/jsp/admin/modules/sb_office.jsp" />
					<jsp:include page="/jsp/admin/modules/sb_resources.jsp" />
					<jsp:include page="/jsp/admin/modules/sb_helplinks.jsp" />
				</div>
				<div id="primary" class="span-19 last">
					<h2 id="page-role">Administrator</h2>
					<h2 id="page-title">Auditor Workflow</h2>


						<div class="post">
							<strong class="title">Filter the Audit Queues by selecting an option below</strong>
							<table class="filters">
								<tr>
									<td style="border-right: 1px solid silver; vertical-align: top;" rowspan="3">
										<label>Primary Industry</label>
										<select id="primary_industry" onChange="updateQueue();">
											<option value="-1">Select a Primary Industry</option>
											<c:choose><c:when test="${primaryIndustries != null}">
												<c:forEach  items="${primaryIndustries}" var="pIndustry" >
													<c:if test="${powerAuditorSessionFilter.primaryIndustry == pIndustry.id}">
														<c:set var="optSelect" value="selected" />
													</c:if>
													<c:if test="${powerAuditorSessionFilter.primaryIndustry != pIndustry.id}">
														<c:set var="optSelect" value="" />
													</c:if>
													<option value="${pIndustry.id}" ${optSelect}>${pIndustry.descr}</option>
												</c:forEach>
											</c:when></c:choose>
										</select>
									</td>
									<td style="padding-left: 10px; border-bottom: 0px solid white;">
										<label>Role</label><br/>
										<select id="role_list" onChange="updateRole();updateQueue();">
											<option value="-1">Select Role</option>
											<c:if test="${powerAuditorSessionFilter.roleType == 1 }">
												<option value="1" selected>Provider Firm</option>
											</c:if>
											<c:if test="${powerAuditorSessionFilter.roleType != 1 }">
												<option value="1">Provider Firm</option>
											</c:if>
											<c:if test="${powerAuditorSessionFilter.roleType == 2 }">
												<option value="2" selected>Service Provider</option>
											</c:if>
											<c:if test="${powerAuditorSessionFilter.roleType != 2 }">
												<option value="2">Service Provider</option>
											</c:if>
										</select>
									</td>
										<td style="padding-left: 10px; border-bottom: 0px solid white;">
										<div id="errorMessage" style="display: none;color: red;font-size: 13px;margin-bottom: 8px;margin-right: 35px; ">Invalid Firm Id.</div>
										<label>Firm Id</label><br/>
										
										<input type="text" id="firmId" style="width:150px;" value="${powerAuditorSessionFilter.firmId}" onkeypress="return firmIdKeyPress(event);"/>
											
									</td>
																			
																		
									</tr>
									<tr>
									<td></td>
									<td>
										<input class="myButton" type="button"  value="Search" onclick="updateQueueFirmId('Search');" />
										<input class="myButton" type="button"  style="margin-left:25px;margin-bottom:7px" value="Reset" onclick="resetField();updateQueueFirmId('Reset')" />	
									</td>
								
									</tr>
									
									<tr>
									<td style="padding-left: 10px;">
										<label>Type of Credential</label>
										<c:if test="${powerAuditorSessionFilter.roleType != -1}">
											<c:set var="credDis" value="" />
										</c:if>
										<c:if test="${powerAuditorSessionFilter.roleType == -1}">
											<c:set var="credDis" value="disabled" />
										</c:if>
										<select id="credential_list" ${credDis} onChange="updateCat();updateQueue();">
											<option value="-1">Select a Credential</option>
												<c:forEach  items="${credentialTypes}" var="cType" >
													<c:if test="${powerAuditorSessionFilter.credentialType == cType.id}">
														<c:set var="optSelect" value="selected" />
													</c:if>
													<c:if test="${powerAuditorSessionFilter.credentialType != cType.id}">
														<c:set var="optSelect" value="" />
													</c:if>
													<option value="${cType.id}" ${optSelect}>${cType.descr}</option>
												</c:forEach>
										</select>
									</td>
									<td>
										<label>Category </label>
										<c:if test="${powerAuditorSessionFilter.credentialType > 0}">
											<c:set var="catDis" value="" />
										</c:if>
										<c:if test="${powerAuditorSessionFilter.credentialType < 0}">
											<c:set var="catDis" value="disabled" />
										</c:if>
										<select class="text" id="category_list" ${catDis} onChange="updateQueue();">
											<option value="-1">Select a Category</option>
												<c:forEach  items="${credentialCategory}" var="credCat" >
													<c:if test="${powerAuditorSessionFilter.categoryOfCredential == credCat.id}">
														<c:set var="optSelect" value="selected" />
													</c:if>
													<c:if test="${powerAuditorSessionFilter.categoryOfCredential != credCat.id}">
														<c:set var="optSelect" value="" />
													</c:if>
													<option value="${credCat.id}" ${optSelect}>${credCat.descr}</option>
												</c:forEach>
										</select>
									</td>
								</tr>
							</table>

						</div>
						
					<div id="waitPopUp" class="waitLayer">
					<div style="padding-left: 0px; padding-top: 5px; color: #222222;">
					<div class="refreshMsg">
					<span id="loadMsg">Loading...</span>
				</div>
			</div>
			</div>

						<div id="overLay" class="overLay"
		style="display: none; z-index: 1000; width: 100%; height: 100%; position: fixed; opacity: 0.2; filter: alpha(opacity =   20); 
		top: 0px; left: 0px; background-color: #E8E8E8; cursor: wait;">
	 				 	</div>
	 				 	
						<div class="post" id="post_area">
							<jsp:include page="audit_workflow_queue_grid.jsp"></jsp:include>
						</div>


		</div>
	</div>
	<tiles:insertDefinition name="blueprint.base.footer"/>
</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="auditor.workflow"/>
	</jsp:include>
	</body>
</html>