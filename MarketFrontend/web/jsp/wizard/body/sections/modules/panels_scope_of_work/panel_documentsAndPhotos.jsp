<jsp:directive.page
	import="com.newco.marketplace.interfaces.OrderConstants" />
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

     <script type="text/javascript">

function expandDocument(id,path){
var hidId="group"+id;
var testGroup=document.getElementById(hidId).value;
var divId="documents"+id;
var bodyId="documents_menu_body"+id;

if(testGroup=="menu_list"){
	jQuery("#"+divId+" p.menu_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
}else{
	if(jQuery("#"+divId+" p.menugroup_head")){
		jQuery("#"+divId+" p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
	}
}
var ob=document.getElementById('docImage'+id).src;
if(ob.indexOf('arrowRight')!=-1){
document.getElementById('docImage'+id).src=path+"/images/widgets/arrowDown.gif";
}
if(ob.indexOf('arrowDown')!=-1){
document.getElementById('docImage'+id).src=path+"/images/widgets/arrowRight.gif";
}
}


</script>
<c:set var="isAutocloseOn" scope="request"
	value="<%=request.getAttribute("isAutocloseOn")%>" />
<c:set var="isRequiredDocsInd" scope="request"
	value="<%=request.getAttribute("isRequiredDocsInd")%>" />
<c:set var="divName" value="documents"/>
 <c:set var="divName" value="${divName}${summaryDTO.id}"/>
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
<c:set var="doc_SourceVal" 
	value="<%=OrderConstants.COMPLETE_FOR_PAYMENT%>" />
<c:set var="active_status"
	value="<%=new Integer(OrderConstants.ACTIVE_STATUS)%>" />
<c:set var="group" value="${group}${summaryDTO.id}"/>
<input type="hidden" id="${group}" value="${groupVal}"/>
<div id="${divName}" class="${groupVal}">
<c:set var="bodyName" value="documents_menu_body"/>
 <c:set var="bodyName" value="${bodyName}${summaryDTO.id}"/>
 <c:set var="docImage" value="docImage"/>
    <c:set var="docImage" value="${docImage}${summaryDTO.id}"/>
 <c:set var="so_id" scope="request"
	value="<%=request.getAttribute("SERVICE_ORDER_ID")%>" />
<c:choose>
	<c:when test="${param.pageFrom eq 'tab_scope_of_work'}"><%-- SS: changing this to look like the other panels on the SOW page, leaving it as it was for all other sections --%>
	
		<c:choose><c:when test="${isAutocloseOn==true && docSource == doc_SourceVal && currentSO.status == active_status}">
			<a name="documents_top"></a>
			<div dojoType="dijit.TitlePane" title="Required Order Completion Documents & Photos" id="" class="contentWellPane">
		</c:when>
		<c:otherwise>
			<a name="documents_top"></a>
			<div dojoType="dijit.TitlePane" title="Documents & Photos" id="" class="contentWellPane">
		</c:otherwise></c:choose>
	</c:when>
	<c:otherwise>

		<c:choose><c:when test="${isRequiredDocsInd==true && docSource == doc_SourceVal && currentSO.status == active_status}">
			<p class="${headClass}" onclick="expandDocument('${summaryDTO.id}','${staticContextPath}')">&nbsp;<img id="${docImage}" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Required Order Completion Documents & Photos</p>
			<div id="${bodyName}" class="${bodyClass}">
			<a name="documents_top"></a>
		</c:when>
		<c:otherwise>
			<p class="${headClass}" onclick="expandDocument('${summaryDTO.id}','${staticContextPath}')">&nbsp;<img id="${docImage}" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Documents & Photos</p>
			<div id="${bodyName}" class="${bodyClass}">
			<a name="documents_top"></a>
		</c:otherwise></c:choose>
	</c:otherwise>
</c:choose>
	<iframe width="100%" height="585px" marginwidth="0" onload="if(parent.document.getElementById('inner_document_grid').height==0){parent.document.getElementById('inner_document_grid').height='585px';}" marginheight="0" frameborder="0" 
	src="/MarketFrontend/soDocumentsAndPhotos_getDocuments.action?docSource=${docSource}&serviceOrderStatus=${THE_SERVICE_ORDER_STATUS_CODE}&priceModel=${summaryDTO.priceModel}&soId=${so_id}" 
	name="inner_document_grid" id="inner_document_grid" scrolling="auto">
	</iframe>
	
</div>

</div>



