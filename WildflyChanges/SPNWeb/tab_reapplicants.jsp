<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
	     <meta name="decorator" content="mainspndecorator" />
		<title>ServiceLive : SPN Auditor : New Applicants</title>
		<link rel="shortcut icon"
			href="${staticContextPath}/images/favicon.ico" />

		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/styles/screen.css"
			media="screen, projection">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/styles/plugins/superfish.css"
			media="screen, projection">


		<script type="text/javascript"
			src="${staticContextPath}/scripts/toolbox.js"></script>
		<!-- end blueprint base include -->

		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/styles/plugins/spn-auditor.css"
			media="screen, projection">
		<script type="text/javascript"
			src="${staticContextPath}/scripts/plugins/ui.core.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/scripts/plugins/ui.tabs.js"></script>
		<script type="text/javascript">
		$(document).ready(function() {
			$('div.tabs > ul').tabs();
		});
	</script>
	</head>




	<div id="content" class="span-24 clearfix">

		<div id="primary" class="span-24 first last">
			<div class="content">
				<ul class="ui-tabs-nav">
					<li>
						<a href="spnAuditorApplicantsTab_displayNewApplicant.action">New Applicants
						</a>
					</li>
					<li class="ui-tabs-selected">
						<a href="spnAuditorApplicantsTab_displayReApplicant.action">Re-Applicants
						</a>
					</li>
					<li>
						<a href="spnAuditorSearchTab_display.action">Search</a>
					</li>
				</ul>
				
				
				<div class="ui-tabs-panel">
					<h1>tab_reapplicants.jsp</h1>
				</div>
		</div>
	</div>
	</div>


</html>


