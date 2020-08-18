 <%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive Audit Workflow</title>
		<link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/sl_admin.css" />				
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />				

<script type="text/javascript">
function open_history_notes(vendorId)
{
	if (document.openProvURL != null)
	{
		document.openProvURL.close();
	}
	var url = "powerAuditorWorkflowAction_getHistoryNotes.action?resourceID=-1&vendorID="+vendorId;
	newwindow=window.open(url,'_publicNotesHistory','resizable=yes,scrollbars=yes,status=no,height=700,width=1000');
	if (window.focus) {newwindow.focus()}
	document.openProvURL = newwindow;
}

//function to call the auditnextcredential link
function open_next_audit_link(auditTimeLoggingId)
{
	
	var cp=${'contextPath'};
	var at=auditTimeLoggingId;
	var url = cp+"/powerAuditorWorkflowControllerAction_execute.action?auditTimeLoggingId="+at;
	window.location.replace(url);
	
}
//function to be triggered when user clicks onthe close audit window 
function close_audit_window(auditTimeLoggingId)
{
	var at=auditTimeLoggingId;	
	jQuery(".closelink").load("powerAuditorWorkflowControllerAction_closeWindowAudit.action?auditTimeLoggingId="+at,function()
	{
		window.close();
	});
}

</script>

	</head>
	<body class="tundra acquity noBg">
		<div id="closeArea"></div>
		<div style="text-align: left;">
			<div id="page">
				<!-- BEGIN CENTER -->				

				<div id="newWrap" class="auditWorkflow clearfix">
					<div id="auditOptionsBack" class="clearfix"></div>
					<div id="auditOptions" class="clearfix">
						<a class="closelink" href="#" onClick="close_audit_window(${auditTimeLoggingId});">Close Audit Window</a>
						<h2>Provider: ${powerAuditorVendorInfo.businessName} (#${powerAuditorVendorInfo.vendorId}) - ${powerAuditorVendorInfo.vendorCity}, ${powerAuditorVendorInfo.vendorState}</h2>
						<h3>Administrator: ${powerAuditorVendorInfo.primaryAdminLastName}, ${powerAuditorVendorInfo.primaryAdminFirstName}</h3>
						<c:set var="myLength" value="${fn:length(powerAuditorVendorInfo.primaryAdminPhoneNumber)}" />
						
						<c:if test="${myLength > 3}">
							<c:set var="tmpStr1" value="${fn:substring(powerAuditorVendorInfo.primaryAdminPhoneNumber, 0, 3)}" />
						</c:if>
						<c:if test="${myLength > 6}">
							<c:set var="tmpStr2" value="${fn:substring(powerAuditorVendorInfo.primaryAdminPhoneNumber, 3, 6)}" />
						</c:if>
						<c:if test="${myLength >= 10}">
							<c:set var="tmpStr3" value="${fn:substring(powerAuditorVendorInfo.primaryAdminPhoneNumber, 6, -1)}" />
						</c:if>
						<h3>
						<c:if test="${myLength >= 10}">
							${tmpStr1}-${tmpStr2}-${tmpStr3}
						</c:if>
						</h3>
						<a class="auditlink" href="#" onClick="open_next_audit_link(${auditTimeLoggingId});">Audit Next Credential</a>
						<br />
						<h3>Audit Task ID: ${auditTask.auditTaskId}</h3>
						<div class="audit_history_notes_hdr">
							<a href="#" onClick="open_history_notes(${powerAuditorVendorInfo.vendorId});">View Audit History/Notes</a>
						</div>
					</div>
				</div>
			<!-- END CENTER   -->
			</div>
		</div>
	</body>
</html>
