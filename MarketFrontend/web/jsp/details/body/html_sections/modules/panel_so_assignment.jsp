<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style type="text/css">
<!--

#assign_to_firm a:HOVER {
	padding-left: 0px;
	margin-left: 0px;
	border: none;
}
.sortProv{
cursor: pointer;
}

#nameId  a:HOVER{
	padding-left: 0px;
	margin-left: 0px;
	border: none;
}
-->
</style> 
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

function toggleAcceptButton(){
	var soId = '${SERVICE_ORDER_ID}';
	var groupId = '${groupOrderId}';
	var isCarSo = jQuery('#isCar').val();
	jQuery('#loadTimerDiv').hide();
	document.getElementById('showTermsAndConditions').style.display='';
	if(isCarSo=='false'){
			document.getElementById('captcha').style.display = "block";
			jQuery('#acceptButton').hide();
			jQuery('#loadTimerDiv').load("loadTimerAjax.action?assignee=typeFirm&soId="+soId+"&groupOrderId="+groupId, function(){	
			    loadTimer();	
				jQuery('#loadTimerDiv').show(); 
			});	
	}else{
			jQuery('#acceptButtonForCarOrders').show();	
	}
}
jQuery(document).ready(function () {
	
	toggleAcceptButton();	
	
	
	/*By default check the checkbox*/
	// jQuery('#soUsingSku').prop('checked', true);

	jQuery('#providerFieldSet').css('display','block');
    jQuery('#assign_to_provider').css('display','block');
	jQuery('#distanceArrowDown').css('display','block');
	jQuery('#captcha').hide();
	jQuery('#acceptButtonForCarOrders').hide();
	
	
	
	
	jQuery('input[name=assignmentType]').click(function(){
		//var type = jQuery('input:radio[name=assignmentType]:checked').val();
		if(jQuery(this).is(':checked')){
			jQuery('#providerFieldSet').css('display','block');
			jQuery('#assign_to_provider').css('display','block');
			jQuery('#distanceArrowDown').css('display','block');
		}else{
			jQuery('#assign_to_provider').css('display','none');
			jQuery('#providerFieldSet').css('display','none');
			jQuery('#assign_to_firm').css('display','block');
			jQuery('input:radio[name=routedProvider]').attr("checked", false);
			toggleAcceptButton();	
		}
	/*	if(type =='typeProvider'){
			jQuery('#providerFieldSet').css('display','block');
			jQuery('#assign_to_provider').css('display','block');
			jQuery('#distanceArrowDown').css('display','block');
			
		}else{
			jQuery('#assign_to_provider').css('display','none');
			jQuery('#providerFieldSet').css('display','none');
			jQuery('#assign_to_firm').css('display','block');
		} */
	});
	var isEstimateSO = document.getElementById('isEstimationRequest').value; 
	var SOIdToHide = document.getElementById('SOIdToHideAssignement').value; 
	//hide the assignment of provider it it is estimation so order
	if(isEstimateSO){
		jQuery('#providerPanel'+SOIdToHide).hide();
	}else{
		jQuery('#providerPanel'+SOIdToHide).show();
	}
});
//fn to open terms and conditions in a new window
function openTermsAndConditions(){
	var url =  "/MarketFrontend/termsAndConditions_displayBuyerAgreement.action?paramTermsandCond=true";
	newwindow=window.open(url,'terms','width=1040,height=640,scrollbars,resizable');
	if (window.focus) {
		newwindow.focus();
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
<input type="hidden" id="SOIdToHideAssignement" value="${summaryDTO.id}"/>
<div id="${divName}" class="${groupVal}">
<c:set var="bodyName" value="providerPanel_body"/>
 <c:set var="bodyName" value="${bodyName}${summaryDTO.id}"/>
 <c:set var="providerPanelImage" value="providerPanelImage"/>
    <c:set var="providerPanelImage" value="${providerPanelImage}${summaryDTO.id}"/>
    <a id="provider_panel"></a>
  <p class="${headClass}" onclick="expandProviderPanel('${summaryDTO.id}','${staticContextPath}')">&nbsp;<img id="${providerPanelImage}" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Service Order Assignment</p>
    <div id="${bodyName}" class="${bodyClass}" style="padding-top: 5px;">
    <div><p>You must assign a provider to this order prior to the service start date. You can do this either now or later.</p></div>
  	 <div style="">
  	 	<div style="float: left;padding-top: 3px;">
			<!-- <input id="" type="radio" name="assignmentType" value ="typeFirm" id ="assignmentTypeFirm" onclick="selectFirm('${summaryDTO.carSO}')"/> -->
			<input id="soUsingSku" name="assignmentType" type="checkbox" value ="typeProvider" id="assignmentTypeProvider" onclick="assignProvider('${summaryDTO.carSO}')" checked="checked"/> 
		</div>
		<div style="float: left;padding-left: 5px;">	
			Assign a Provider Now
		</div>
	</div>
	<!-- 
	<div style="float: left;padding-left: 200px;">
		<div style="float: left;padding-top: 3px;">
			 <input id="soUsingSku" name="assignmentType" type="radio" value ="typeProvider" id="assignmentTypeProvider" onclick="assignProvider('${summaryDTO.carSO}')" /> 
			<input id="soUsingSku" name="assignmentType" type="checkbox" value ="typeProvider" id="assignmentTypeProvider" onclick="assignProvider('${summaryDTO.carSO}')" /> 
		</div>
		<div style="float: left;padding-left: 5px;">
			Assign a Provider Now
		</div>
	</div>
	-->
	<div style="margin-top: 22px;">
		<fieldset style="width: 500px;padding: 10px;display:none" id="providerFieldSet">
		<legend style="font-size: 11px; color:#9F9F9F">
			&nbsp;
			<b>Select a Provider</b>&nbsp;&nbsp;
		</legend>
		<div id="assign_to_provider" style="display:none;">
	     	<jsp:include page="panel_provider_firm_assigned.jsp"></jsp:include>
		</div>
		</fieldset>
	</div>

<div id="assign_to_firm" style="padding-top: 15px;">
		By clicking the Accept button, you are confirming your acceptance of the <a onclick="openTermsAndConditions();" style="color:#00A0D2;cursor:pointer;">ServiceLive Terms &amp; Conditions</a>
</div>
<input type="hidden" id="selectedResourceId" value="">
<input type="hidden" id="isCar" value="${summaryDTO.carSO}">
</div>
</div>



