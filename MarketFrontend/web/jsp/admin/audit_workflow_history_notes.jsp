<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<!-- acquity: modified meta tag to set charset -->
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />

	<title>Auditor History</title>


		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/ui.tabs1.css" media="screen, projection">
		<!--[if IE]>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
		<![endif]-->

		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/banner.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqueryui/jquery-ui-1.10.4.custom.min.js"></script>
		<style type="text/css">
		.ff3 .bannerDiv{margin-left:0px;}  
		.ff2 .bannerDiv{margin-left:0px;}
		.ie7 .bannerDiv{margin-left:0px;position: relative;}
		</style>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/banner.css" />
		
</head>
	<body class="tundra acquity noBg">
	<!-- <body class="tundra acquity noBg" onload="displayBanner();"> -->
	<!-- SL-18907 Code changes to fix Browser compatibility message displayed in not 100% in View Audit History/Notes Page-->
  <!-- <div id="bannerDiv" class="bannerDiv" style="display:none;width: 100%;">
	     <span class="spanText" id="spanText"></span>
	     <a href="javascript:void(0);" onclick="removeBanner();" style="text-decoration: underline;"> Dismiss </a>
  </div> -->
  		<script type="text/javascript">
			jQuery(document).ready(function() {
				jQuery("#providerTabs").tabs();
			});
		</script>
		<div id="newWrap">
			 <div id="providerTabs">
				<ul class="clearfix botbor botmg10">
					<li><a href="#fragment-1"><span>Audit History</span></a></li>
					<li><a href="#fragment-2"><span>Audit Notes</span></a></li>
				</ul>
				<div id="fragment-1" class="clearfix">
					<table  cellpadding="0" cellspacing="0" class="auditWorkflow auditHistory">
						<tr>
							<th>Date/Time</th>
							<th>Type</th>
							<th>Status</th>
							<th>Reason Code</th>
							<th>Review Comments</th>
							<th>Auditor</th>
						</tr>

						<c:set var="iter" value="1" />

						<c:choose><c:when test="${not empty historyData}">
						<c:forEach items="${historyData}" var="auditHistory">
							<c:choose>
							<c:when test="${iter == 1}">
								<tr>
								<c:set var="iter" value="0" />
							</c:when>
							<c:when test="${iter == 0}">
								<tr class="alt">
								<c:set var="iter" value="1" />
							</c:when>
							</c:choose>
								<td><fmt:formatDate value="${auditHistory.modifiedDate}" pattern="M/d/yyyy h:mm a" /></td>
								<td>${auditHistory.type}</td>
								<td>${auditHistory.status}</td>
								<td>${auditHistory.reasonCode}</td>
								<td>${auditHistory.reviewComments}</td>
								<td>${auditHistory.auditor}</td>
							</tr>
						</c:forEach>
						</c:when>
						<c:otherwise>
							<tr><td colspan=6>
								<div style="margin-top: 20px; margin-bottom: 20px; text-align: center;">There is no audit history for this provider</div>
							</td></tr>
						</c:otherwise>
						</c:choose>
					</table>
					</div>
				</div>
				<div id="fragment-2" class="clearfix">
					<table cellpadding="0" cellspacing="0" class="auditWorkflow auditNotes">
						<tr>
							<th>Date/Time</th>
							<th>Auditor</th>
							<th>Description</th>
						</tr>

						<c:set var="iter" value="1" />

						<c:choose><c:when test="${not empty notesData}">
						<c:forEach items="${notesData}" var="note">
							<c:choose>
							<c:when test="${iter == 1}">
								<tr>
								<c:set var="iter" value="0" />
							</c:when>
							<c:when test="${iter == 0}">
								<tr class="alt">
								<c:set var="iter" value="1" />
							</c:when>
							</c:choose>
								<td><fmt:formatDate value="${note.modifiedDate}" pattern="M/d/yyyy h:mm a" /></td>
								<td>${note.auditor}</td>
								<td>${note.description}</td>
							</tr>
						</c:forEach>
						</c:when>
						<c:otherwise>
							<tr><td colspan=3>
								<div style="margin-top: 20px; margin-bottom: 20px; text-align: center;">There aren't any notes for this provider</div>
							</td></tr>
						</c:otherwise>
						</c:choose>
					</table>
				</div>
			</div>
		</div>
	</body>
</html>