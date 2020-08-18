<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<script type="text/javascript">

function open_history_notes_resource(resourceId)
{
	if (document.openProvURL != null)
	{
		document.openProvURL.close();
	}
	var url = "powerAuditorWorkflowAction_getHistoryNotes.action?vendorID=-1&resourceID="+resourceId;
	newwindow=window.open(url,'_publicNotesHistory','resizable=yes,scrollbars=yes,status=no,height=700,width=1000');
	if (window.focus) {newwindow.focus()}
	document.openProvURL = newwindow;
}

</script>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="theId" scope="request"
	value="<%=request.getAttribute("tab")%>" />
<c:set var="role" value="${roleType}" />
<div dojoType="dijit.TitlePane" title="Member Information"
	id="widget_member_info_${theId}"
	style="padding-top: 1px; width: 249px;" open="true">

	<span class="dijitInfoNodeInner"> <a href="#"> </a> </span>

	<table border="0" cellpadding="0" cellspacing="0" class="">
		<tr>
			<td>
				<b> Provider: </b>
			</td>
			<td>
				${providerCompanyName}
			</td>
		</tr>
		<tr>
			<td>
				<b> Provider ID #: </b>
			</td>
			<td>
				${vendorId}
			</td>
		</tr>
		<tr>
			<td>
				<b> Administrator: </b>
			</td>
			<td>
				${providerName}
			</td>
		</tr>
		<tr>
			<td>
				<b> Location: </b>
			</td>
			<td>
				${cityState}
			</td>
		</tr>
		<tr><td colspan=2>
			<c:if test="${not empty resourceId}">
				<a href="#" onClick="open_history_notes_resource(${resourceId});">View Audit History/Notes</a>
			</c:if>
		</td></tr>
	</table>
	<table height=10px>
	</table>
	<p class="paddingBtm">
		<!--  <a href="<s:url action="adminSearch" method="backToSearchPortal" includeParams="none"/>"> <input width="131"
				type="image" height="27"
				src="${staticContextPath}/images/common/spacer.gif"
				style="background-image: url(${staticContextPath}/images/btn/returnToSearchPortal.gif);"
				class="btn27" /> </a>-->
		<a
			href="adminSearch_backToSearchPortal.action"
			style="color: #4CBCDF; font-weight: bold; height: 23px;"> Return
			to Search Portal </a>
	</p>
	<table height=10px>
	</table>
</div>

<tags:security actionName="auditAjaxAction">
		<%@ include file="/jsp/auditor/commonServiceProApprovalWidget.jsp"%>
</tags:security>
	


