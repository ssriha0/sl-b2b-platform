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

function expandSOAppointment(id,path){
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
});


</script>
<c:set var="divName" value="appointmentPanel"/>
 <c:set var="divName" value="${divName}${summaryDTO.id}"/>
  <c:set var="group" value="group_appointment"/>
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
<input type="hidden" id="SOIdToHideAppointment" value="${summaryDTO.id}"/>
<div id="${divName}" class="${groupVal}">
<c:set var="bodyName" value="appointement_body"/>
 <c:set var="bodyName" value="${bodyName}${summaryDTO.id}"/>
 <c:set var="appointmentPanelImage" value="appointmentPanelImage"/>
    <c:set var="appointmentPanelImage" value="${appointmentPanelImage}${summaryDTO.id}"/>
    <a id="appointment_panel"></a>
  <p class="${headClass}">&nbsp;<img id="${appointmentPanelImage}" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Service Order Appointment</p>
    <div id="${bodyName}" class="${bodyClass}" style="padding-top: 5px;">
    <div style="margin-top: 22px;">
		<fieldset style="width: 500px;padding: 10px;display:block;height: 85px;" id="appointmentDateSet">
		<legend style="font-size: 11px; color:#9F9F9F">
			&nbsp;
			<b>Select appointment date</b>&nbsp;&nbsp;
		</legend>
		<div id="divAppointValidateMessage" style="font-size: 14px;padding-top: 5px;"></div>
		<div id="select_appointment" style="display:block;">
	     	<jsp:include page="panel_so_datetime_slots_preferences.jsp"></jsp:include>
		</div>
		
		</fieldset>
	</div>


<input type="hidden" id="selectedPreferenceId" value="">
<input type="hidden" id="selectedSlotId" value="">
</div>
</div>



