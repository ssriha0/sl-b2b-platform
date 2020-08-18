<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="theId" scope="request"
	value="<%=request.getAttribute("tab")%>" />
<c:set var="role" value="${roleType}" />

<c:if test="${buyerId != null || vendorId != null}">
<div dojoType="dijit.TitlePane" title="Member Information"
	id="widget_member_info_${theId}"
	style="padding-top: 1px; width: 249px;" open="true">
	
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

</script>

	<span class="dijitInfoNodeInner"> <a href="#"> </a> </span>
	<c:choose>
	<c:when test="${buyerId != null}" >
	<table border="0" cellpadding="0" cellspacing="0" class="">
		<tr>
			<td>
				<b> Buyer: </b>
			</td>
			<td>
				${buyerBusinessName}
			</td>
		</tr>
		<tr>
			<td>
				<b> Buyer ID #: </b>
			</td>
			<td>
				${buyerId}
			</td>
		</tr>
		<tr>
			<td>
				<b> Administrator: </b>
			</td>
			<td>
				${buyerAdmin}
			</td>
		</tr>
		<tr>
			<td>
				<b> Location: </b>
			</td>
			<td>
				${buyerLocation}
			</td>
		</tr>
		
		
	</table>
	</c:when>
	<c:otherwise>
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
			<c:if test="${not empty vendorId}">
				<a href="#" onClick="open_history_notes(${vendorId});">View Audit History/Notes</a>
			</c:if>
		</td></tr>
	</table>
	</c:otherwise>
	</c:choose>
	
	<table height=10px>
	
	</table>
	
	
	<p class="paddingBtm">
		<!--  <a href="<s:url action="adminSearch" method="backToSearchPortal" includeParams="none"/>"> <input width="131"
				type="image" height="27"
				src="%{#request['staticContextPath']}{staticContextPath}/images/common/spacer.gif"
				style="background-image: url(${staticContextPath}/images/btn/returnToSearchPortal.gif);"
				class="btn27" /> </a>-->
		<a
			href="adminSearch_backToSearchPortal.action"> &laquo; Return
			to Search Portal </a>			
	</p>
	
	<p class="paddingBtm">
		<!--  <a href="<s:url action="adminSearch" method="backToSearchPortal" includeParams="none"/>"> <input width="131"
				type="image" height="27"
				src="${staticContextPath}/images/common/spacer.gif"
				style="background-image: url(${staticContextPath}/images/btn/returnToSearchPortal.gif);"
				class="btn27" /> </a>
				<tags:security actionName="pbController_execute">
						
				</tags:security>-->
				<a href="pbController_backToAdminWorkFlow.action">&laquo; Return to Workflow Monitor</a></li>
	</p>
	
	<table height=10px>
	</table>
</div>
</c:if>

<tags:security actionName="auditAjaxAction">
		<%@ include file="/jsp/auditor/commonCompanyProviderApprovalWidget.jsp"%>
</tags:security>
	


