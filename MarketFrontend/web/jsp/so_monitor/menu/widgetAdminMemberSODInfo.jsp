<%-- This jsp is created as part of SOD Refactoring.This is used instead of widgetAdminMemberInfo.jsp 
for SOD and included in quicklinks.jsp  --%>. 
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

<script type="text/javascript">
function collapse(obj){


    jQuery(obj).css({backgroundImage:"url(${staticContextPath}/images/titleBarBg.gif)"}).next("div.menugroupadmin_body").slideToggle(300);
  
   
   // $(this).siblings().css({backgroundImage:"url(titleBarBg.gif)"});

}
 function clickMemberInfoBuyer(path)
{
    jQuery("#adminMemberPaneBuyer p.menugroupadmin_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("div.menugroupadmin_body").slideToggle(300);
    var ob=document.getElementById('adminBuyerMemberImg').src;
	if(ob.indexOf('arrowRight')!=-1){
	document.getElementById('adminBuyerMemberImg').src=path+"/images/widgets/arrowDown.gif";
	}
	if(ob.indexOf('arrowDown')!=-1){
	document.getElementById('adminBuyerMemberImg').src=path+"/images/widgets/arrowRight.gif";
	}
    
}
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
<div id="adminMemberPaneBuyer" class="menugroupadmin_list">
  	<div dojoType="dijit.TitlePane" title="Member Information"
	id="widget_member_info_${theId}"
	style="padding-top: 1px; width: 249px;" open="true">
    <div class="menugroupadmin_body" id="adminMemberPaneBuyerId">
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
				src="${staticContextPath}/images/common/spacer.gif"
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
				
				<a href="pbController_backToAdminWorkFlow.action">&laquo; Return to Workflow Monitor</a>
	</p>
	
	<table height=10px>
	</table>
</div>
</div>
</c:if>

<tags:security actionName="auditAjaxAction">
		<%@ include file="/jsp/auditor/commonCompanyProviderApprovalWidget.jsp"%>
</tags:security>
	


