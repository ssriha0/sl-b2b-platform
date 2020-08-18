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
		<title>ServiceLive - Workflow Monitor</title>
		<tiles:insertDefinition name="blueprint.base.meta"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/workflow-monitor.css" media="screen, projection">
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/ui.core.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/ui.tabs.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/ui.datepicker.js"></script>
		
		<!-- include any plugin javascripts and css here -->
		<script type="text/javascript">
			$(function() {
				$('#tabs > ul').tabs();
				
				$("input#modal2ConditionalChangeDate1").datepicker();
			});
		</script>
	</head>

<body id="workflow-monitor">
	<div class="roleinfo notice clearfix" style="display: none;">
		<div class="left"><strong>Buyer</strong>: SEARS-33 (#23) | <strong>Administrator</strong>: Carpenter, Sarah | <strong>Location</strong>: Saint Paul, MN | <strong>Phone #</strong>: 1 (123) 123 - 1234</div>
		<div class="right"><strong>Return to</strong>: <a href="#">Search Portal</a> | <a href="#">Workflow Monitor</a></div>
	</div>
<div id="wrap" class="container">
	<tiles:insertDefinition name="blueprint.base.header"/>
	<tiles:insertDefinition name="blueprint.base.navigation"/>
	<div id="content" class="span-24 clearfix">	
		<div class="padding">	
			<h2 id="page-role">ServiceLive</h2>
			<h2 id="page-title">Workflow Monitor</h2>		
		
			<div id="tabs">
	            <ul>
	                <li><a href="pbWorkflowTab_execute.action"><span>Workflow</span></a></li>
	                <li><a href="pbClaimedTab_execute.action"><span>Currently Claimed</span></a></li>
	              	<li><a href="gridHolder.action?tab=Search&amp;wfFlag=1"><span>Search</span></a></li>
	            </ul>
			</div>
			
		</div>	
	</div>
	<tiles:insertDefinition name="blueprint.base.footer"/>
</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="admin:workflow monitor"/>
	</jsp:include>
	</body>
</html>
