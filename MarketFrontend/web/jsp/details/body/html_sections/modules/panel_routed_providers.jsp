<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 
 <script type="text/javascript">

function expandProviderPanel(id,path){
var hidId="group"+id;
var testGroup=document.getElementById(hidId).value;
var divId="providerPanel"+id;
var bodyId="providerPanel_body"+id;
if(testGroup=="menu_list"){
jQuery("#"+divId+" p.menu_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
}else{
jQuery("#"+divId+" p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
}
var ob=document.getElementById('providerPanelImage'+id).src;
if(ob.indexOf('arrowRight')!=-1){
document.getElementById('providerPanelImage'+id).src=path+"/images/widgets/arrowDown.gif";
}
if(ob.indexOf('arrowDown')!=-1){
document.getElementById('providerPanelImage'+id).src=path+"/images/widgets/arrowRight.gif";
}
}

</script>
<c:set var="divName" value="providerPanel"/>
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
<c:set var="group" value="${group}${summaryDTO.id}"/>
<input type="hidden" id="${group}" value="${groupVal}"/>
<div id="${divName}" class="${groupVal}">
<c:set var="bodyName" value="providerPanel_body"/>
 <c:set var="bodyName" value="${bodyName}${summaryDTO.id}"/>
 <c:set var="providerPanelImage" value="providerPanelImage"/>
    <c:set var="providerPanelImage" value="${providerPanelImage}${summaryDTO.id}"/>
  <p class="${headClass}" onclick="expandProviderPanel('${summaryDTO.id}','${staticContextPath}')">&nbsp;<img id="${providerPanelImage}" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Choose a Service Provider from the list below</p>
    <div id="${bodyName}" class="${bodyClass}">
    <a id="provider_panel"></a>
	<table cellspacing="10px">
	<tr style="padding: 10px">
	<c:forEach var="routedResource" items="${summaryDTO.routedProvExceptCounterOffer}" varStatus="rowCounter" >
		<c:set var="index" value="${rowCounter.count}"/> 
		<td width="250px" class="tt">
		<span class="tooltip"><span class="middle"><b>${routedResource.distanceFromBuyer} Miles</b><br>
		Provider distance from Service Location (Center zip to center zip)</span></span>
		<input type="radio" id="${routedResource.resourceId}" name="routedProvider"
		 onclick="selectProvider('${routedResource.resourceId}')"/>&nbsp;${routedResource.providerFirstName} ${routedResource.providerLastName} (${routedResource.resourceId})
		 </td>
		<c:if test="${(index) %3==0}"> </tr><tr style="padding: 10px"> </c:if>
	</c:forEach>
	</tr>
	</table>
	<input type="hidden" id="selectedResourceId" value="">
<br style="clear:both;"/>
</div>
</div>

