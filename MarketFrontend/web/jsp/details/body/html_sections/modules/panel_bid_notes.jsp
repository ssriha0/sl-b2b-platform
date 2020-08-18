<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<c:set var="BUYER_PROVIDES_PART" value="<%= OrderConstants.SOW_SOW_BUYER_PROVIDES_PART%>" />
<c:set var="PROVIDER_PROVIDES_PART" value="<%= OrderConstants.SOW_SOW_PROVIDER_PROVIDES_PART%>" />
<c:set var="PARTS_NOT_REQUIRED" value="<%= OrderConstants.SOW_SOW_PARTS_NOT_REQUIRED%>" />
<c:set var="BUYER_ROLEID" value="<%= new Integer(OrderConstants.BUYER_ROLEID)%>" />
<c:set var="PROVIDER_ROLEID" value="<%= new Integer(OrderConstants.PROVIDER_ROLEID)%>" />
<c:set var="ROUTED_STATUS" value="<%= new Integer(OrderConstants.ROUTED_STATUS)%>" />
<c:set var="CLOSED_STATUS" value="<%= new Integer(OrderConstants.CLOSED_STATUS)%>" />
<c:set var="SIMPLE_BUYER_ROLEID" value="<%= new Integer(OrderConstants.SIMPLE_BUYER_ROLEID)%>" />
<c:set var="MAX_CHARACTERS_WITHOUT_SCROLLBAR" value="<%= OrderConstants.MAX_CHARACTERS_WITHOUT_SCROLLBAR%>" />

<c:set var="divName" value="soBidNotes${summaryDTO.id}"/>
<c:set var="group" value="group"/>
<c:if test="${checkGroup==group}">
<c:set var="groupVal" value="menu_list"/>
<c:set var="bodyClass" value="menu_body"/>
<c:set var="headClass" value="menu_head"/>
</c:if>
<c:if test="${checkGroup!=group}">
<c:set var="groupVal" value="menugroup_list"/>
<c:set var="bodyClass" value="menugroup_body"/>
<c:set var="headClass" value="menugroup_head"/>
</c:if>
<c:set var="bodyName" value="${divName}_menu_body${summaryDTO.id}"/>
<c:set var="soBidNotesImage" value="soBidNotesImage${summaryDTO.id}"/>

<script type="text/javascript">
function expandBidNotes(id,path){
	var hidId="group"+id;
	var testGroup=document.getElementById(hidId).value;
	var divId="<c:out value="${divName}" />"+id;
	var bodyId="<c:out value="${divName}" />_menu_body"+id;
	if(testGroup=="menu_list"){
	jQuery("#"+divId+" p.menu_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"});
	jQuery("#"+bodyId).slideToggle(300);
	}else{
	jQuery("#"+divId+" p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"});
	jQuery("#"+bodyId).slideToggle(300);
	}
	var ob=document.getElementById('soBidNotesImage'+id).src;
	if(ob.indexOf('arrowRight')!=-1){
	document.getElementById('soBidNotesImage'+id).src=path+"/images/widgets/arrowDown.gif";
	}
	if(ob.indexOf('arrowDown')!=-1){
	document.getElementById('soBidNotesImage'+id).src=path+"/images/widgets/arrowRight.gif";
	}
}

function expandSubMenu(count,id,path){
	var divId="subName"+count+id;
	var bodyId="subBody"+count+id;
	jQuery("#"+divId+" p.submenu_head").css({backgroundImage:"url("+path+"/images/widgets/subtitleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
	var ob=document.getElementById('subsoBidNotesImage'+count+id).src;
	if(ob.indexOf('arrowRight')!=-1){
	document.getElementById('subsoBidNotesImage'+count+id).src=path+"/images/widgets/arrowDown.gif";
	}
	if(ob.indexOf('arrowDown')!=-1){
	document.getElementById('subsoBidNotesImage'+count+id).src=path+"/images/widgets/arrowRight.gif";
	}
}

jQuery(document).ready(function($) {
	
	
	

});

</script>

<div id="${divName}" class="${groupVal}">
	<p class="${headClass}"
		onclick="expandBidNotes('${summaryDTO.id}','${staticContextPath}')">
		&nbsp;<img id="${soBidNotesImage}"
			src="${staticContextPath}/images/widgets/arrowDown.gif" />
		Comments
	</p>
	<div id="${bodyName}" class="${bodyClass}">
		<div id="bidNotesDiv">
			<s:action namespace="/MarketFrontend"
				name="providerUtilsBidNotesAjax" executeResult="true"></s:action>
		</div>

	</div>
</div>