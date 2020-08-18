<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<style type="text/css"> 
.exceptionsHeader {
	width: 100%;
	border:1px #D8D8D8 solid !important;
}

.exceptionsHeader tr{
	border:1px #D8D8D8 solid !important;
}
.exceptionsHeader td{
	border: 0 !important;
	background:#F2F2F2 !important; 
}
.credentials{
	background:#CCCCCC; 
	float: left;
	text-align: left;
}
.detailHdr{
	width: 100%;
	border:1px #D8D8D8 solid !important;
	border-bottom:0px !important;
}
.detailHdr tr{
	border-bottom:1px #D8D8D8 solid !important;
}
.detailHdr td{
	text-align: left!important;
	border-bottom:1px #D8D8D8 solid !important;
	background:#FFFFFF!important; 
}
.credentialsEdit{
font-size: small;
}
.credentialsDone{
font-size: small;
}
.expValue{
font-size: small;
}
.expDropDown{
font-size: small;
}
.expStatus{
font-size: small;
}
.expButton{
font-size: small;
}
.expUpdated{
font-size: small;
}
.expSave{
font-size: small;
}
.errorBox { background: #ffeacc; color: #FE0000; border: 1px solid #FF9600;text-align: left; display: none;}
.pickedExceptions{
	font-weight: normal;
	border: 1px solid #BBBBBB;
	width:auto;
	padding: 2px 5px;
	background: url(${staticContextPath}/images/common/multiselect.gif) no-repeat scroll #ffffff;
	background-position: right;
	zoom: 1;
	cursor: pointer;
	-webkit-border-radius:4px;
	-moz-border-radius:4px;
	border-radius:4px;
}

.pickedExceptions:hover{
	background:url(${staticContextPath}/images/common/multiselect-hover.gif) no-repeat scroll #f4f4f4 ;
	background-position: right;
	cursor: pointer;
}

.pickedExceptions label{
	cursor: pointer;
	padding:0px;
}

.pickedExceptions label:hover {
	cursor: pointer;
	padding:0px;
}
.expselect-options {
	-moz-border-radius:5px;
	background:#33393C;
	color:  #FFF;
	padding: 10px;
	position: absolute;
	width:  165px;
	max-height: 250px;
	_height: 250px;
	overflow: auto;
}

.expselect-options input {
	float: left;
}

.expselect-options label {
	font-weight: normal;
}

.wider {
	width: 300px;
}

.wider .expselect-options {
	width:  280px;
}

</style>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/toggle-btn.css"/>	
<link href="${staticContextPath}/css/ui-15642.css" rel="stylesheet"/>
 <link href="${staticContextPath}/css/font-awesome.min.css"
	rel="stylesheet" />
	<link href="${staticContextPath}/css/font-awesome-ie7.min.css"
	rel="stylesheet" />
	<script
	src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"
	type="text/javascript"></script>
<%-- 	<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jqmodal/jqModal.js"></script> --%>
<script type="text/javascript">
$(document).ready(function() {
	$('.successBox').css("display","none");
	
});
$(document).click(function(e) {
	$(".successBox").css("display","none");
	var click = $(e.target);
	if (!click.hasClass("pickedExceptions") && !click.parents().hasClass("pickedExceptions") && !click.hasClass("expselect-options") && !click.parents().hasClass("expselect-options")){
		$(".expselect-options").hide();
	} 

}); 
$('.expselect-options').unbind('click').click(function(event){
	event.stopPropagation();
});

$('.pickedExceptionsClick').unbind('click').click(function(event) {
	event.stopPropagation();			
});

function onExceptionropDownClick(id){
	
	var value = $("#"+id).attr("value");
	var count = id.replace("spnExceptionsDropDown_","");
	$("#expError_"+count).html('');
	$("#expError_"+count).css("display","none");
	if(value == 1){
		$('#graceExceptionDetails_'+count).css("display","block");
		$('#stateExceptionDetails_'+count).css("display","none");
		$('#exceptionTypeIdHidden_'+count).attr("value",value);
		$('#gracePeriod_'+count).css("display","block");
		$('#states_'+count).css("display","none");
	}
	else if(value==2){
		$('#graceExceptionDetails_'+count).css("display","none");
		$('#stateExceptionDetails_'+count).css("display","block");
		$('#exceptionTypeIdHidden_'+count).attr("value",value);
		$('#gracePeriod_'+count).css("display","none");
		$('#states_'+count).css("display","block");
	}
	else{
		$('#stateExceptionDetails_'+count).css("display","none");
		$('#graceExceptionDetails_'+count).css("display","none");
		$('#exceptionTypeIdHidden_'+count).attr("value","");
	}

}
$("input[type='radio']").click(function() {
	var spnId = $(this).attr("class");
	spnId = spnId.replace("l_","");
	if($(this).is(':checked'))  {
			var name1 = $(this).attr('name');   
			$('input[name=' + name1 + ']:radio').next('label').removeClass('checked');
		    $(this).next('label').addClass('checked');
	}
	var value = $(this).val();
	
	if(value == 'Off'){
		$("#activeIndhidden_"+spnId).val(false);
	}else{
		$("#activeIndhidden_"+spnId).val(true);
		//$(this).siblings("input").val("ON");
	}
});
function setExceptionValue(obj,spnId){
	var id =($(obj).attr("id"));
	var exceptionValue = $("#"+id).attr("value");
	$("#exceptionValuehidden_"+spnId).attr("value",exceptionValue);
}


	
function confirmExceptions(typeId,categoryId,exceptionCategory,spnId,count){
	$("#expError_"+spnId).html('');
	$("#expError_"+spnId).css("display","none");
	
	$("#credentialTypeIdhidden_"+spnId).attr("value",typeId);
	$("#credentialCategoryIdhidden_"+spnId).attr("value",categoryId);
	$("#spnIdHidden_"+spnId).attr("value",spnId);
	$("#exceptionCredentialTypeHidden_"+spnId).attr("value",exceptionCategory);
	var value = $("#activeIndhidden_"+spnId).attr("value");
	var type="";
	var exceptionTypeIdHidden = $("#exceptionTypeIdHidden_"+spnId).attr("value");
	var expValue = "";
	$("#exceptionValuehidden_"+spnId).attr("value",expValue);
	var category="";
	if(exceptionCategory == 'vendor'){
		category ='c';
	}
	else if(exceptionCategory == 'resource'){
		category ='r';
	}
	if(exceptionTypeIdHidden==1){
		type ='g';
		expValue = $("#expValueDropDown"+category+type+"_"+spnId+"_"+count+" option:selected").attr("value");
		$("#exceptionValuehidden_"+spnId).attr("value",expValue);
		$("#exceptionGraceValue_"+spnId).html($("#exceptionValuehidden_"+spnId).attr("value")+" days");
	}
	else if(exceptionTypeIdHidden==2){
		type ='s';
		expValue="";
		 var checkedStates=[];
		 var checkedStatesCount;
			// actual code
			 var checkedState;
		       $('.check_'+category+'_'+spnId+'_'+count+':checked').each(function() {
		    	   checkedState = this.value;
		    	   checkedStates.push(checkedState);
		       });
		       checkedStatesCount = checkedStates.length;
			 if(checkedStatesCount >=1){
				 expValue = checkedStates;
			 }
			 if(checkedStatesCount >19){
				 $("#exceptionStateValue_"+spnId).css("padding-left","0px");
			 }
			 else{
				 $("#exceptionStateValue_"+spnId).css("padding-left","10px");
			 }
		$("#exceptionValuehidden_"+spnId).attr("value",expValue);
		$("#exceptionStateValue_"+spnId).html($("#exceptionValuehidden_"+spnId).attr("value"));
	}
	if(value == null || value =="" || value==" "|| value=='undefined'){
		value = $("#"+exceptionCategory+"_"+type+"_"+spnId+"_"+count+"_init_val").attr("value");
	}
	
	var credentialType = $("#credentialType_"+category+type+"_"+spnId+"_"+count+"_init_val").attr("value");
	var credentialCategory = $("#credentialCategory_"+category+type+"_"+spnId+"_"+count+"_init_val").attr("value");
	$("#credentialTypeHidden_"+spnId).attr("value",credentialType);
	$("#credentialCategoryHidden_"+spnId).attr("value",credentialCategory);
	$("#activeIndhidden_"+spnId).attr("value",value);
	if(value == 'true'){
		$("#expStatus_"+spnId).html("Active");
	}
	else if(value == 'false'){
		$("#expStatus_"+spnId).html("Inactive");
	}
	if(credentialCategory!="" && credentialCategory!=" " && credentialCategory!='undefined'){
		$("#credentials_"+spnId).html(credentialType+" > "+credentialCategory);
	}
	else{
		$("#credentials_"+spnId).html(credentialType);
	}
	var warningMsg = "";
	var errorOccured = false;
	if(value == null || value =="" || value==" " || value=='undefined'){
		warningMsg += "<li>"+"Please Select Exception Status"+"</li>";
		errorOccured =true;
	}
	if(expValue == -1 || expValue == "" || expValue == " " || expValue=='undefined'){
		if(type == 'g'){
			warningMsg += "<li>"+"Please select a Grace Period"+"</li>";
		}
		else if(type == 's'){
			warningMsg += "<li>"+"Please select a State"+"</li>";
		}
		errorOccured =true;
	}
	if(errorOccured){
		warningMsg = "<ul>"+warningMsg+"</ul>";
		$("#expError_"+spnId).html('');
		$("#expError_"+spnId).html(warningMsg);
		$("#expError_"+spnId).css("display","block");
	}
	else{
	$("#confirmExceptionsPopUp_"+spnId).jqm({
		modal : true,
		overlay : 50
	});
	$("#confirmExceptionsPopUp_"+spnId).fadeIn('slow');
	$("#confirmExceptionsPopUp_"+spnId).css('display', 'block');
	$("#confirmExceptionsPopUp_"+spnId).css("border-left-color", "#A8A8A8");
	$("#confirmExceptionsPopUp_"+spnId).css("border-right-color", "#A8A8A8");
	$("#confirmExceptionsPopUp_"+spnId).css("border-bottom-color", "#A8A8A8");
	$("#confirmExceptionsPopUp_"+spnId).css("border-top-color", "#A8A8A8");
	$("#confirmExceptionsPopUp_"+spnId).jqmShow();
	/* jQuery("#confirmExceptionsPopUp").modal({
	    onOpen: modalOpen,
	    onClose: modalOnClose,
	    persist: true,
	    containerCss: ({ width: "530px", height: "auto", marginLeft: "250px",marginTop: "900px"})
	});
	jQuery.modal.defaults = {
			overlay: 50,
			overlayId: 'modalOverlay',
			overlayCss: {},
			containerId: 'modalContainer',
			containerCss: {},
			close: false,
			closeTitle: 'Close',
			closeClass: 'modalClose',
			persist: false,
			onOpen: null,
			onShow: null,
			onClose: null
			};
	function modalOpen(dialog) {
	    dialog.overlay.fadeIn('fast', function() {
	    	dialog.container.fadeIn('fast', function() {
	    		dialog.data.hide().slideDown('slow');
	    	});
		});
		}

		function modalOnClose(dialog) {
			dialog.data.fadeOut('fast', function() {
	    	dialog.container.slideUp('fast', function() {
	    		dialog.overlay.fadeOut('fast', function() {
	    			jQuery.modal.close(); 
	    		});
	  		});
			});
	} */
}
}
function saveExceptions(spnId){
	var expType = $("#exceptionTypeIdHidden_"+spnId).attr("value");
	var expCredential = $("#credentials_"+spnId).html();
	var formData = jQuery('#updateExceptionsForm_'+spnId).serialize();
/* 	$('#spnExceptions-'+spnId).load('spnMonitorNetwork_saveExceptions.action?networkid=' + spnId,formData, function(data) {
		$("#confirmExceptionsPopUp").jqmHide();
	}); */

	$.ajax({
    	url: 'spnMonitorNetwork_saveExceptions.action',
    	type: "POST",
    	data: formData,
		dataType : "json",
		success: function(data) {
			$("#confirmExceptionsPopUp_"+spnId).jqmHide();
			$('#spnExceptions-'+spnId).html('<img src="${staticContextPath}/images/loading.gif" width="850px" height="300px">');
			$('#spnExceptions-'+spnId).load('spnMonitorNetwork_viewExceptionsAjax.action?networkid=' + spnId+'&date='+ new Date().getTime(), function() {
				$('#spnExceptionsDropDown_'+spnId).val(expType).attr("selected", "selected");
				$('#spnExceptionsDropDown_'+spnId).trigger('change');
				$('#tabSpnExpMsg_'+spnId).html('Exception is saved successfully for credential: '+expCredential);	
				$('#tabSpnExpMsg_'+spnId).css("display","block");
			});
			}
 	});  
	
	
}
// on Add exceptions
function clickAdd(obj,credentialType,spnId){
	var lastId = $("#lastId_"+spnId).attr("value");
	if(lastId!=''&& lastId!= ' '&& lastId!='undefined'){
		$("#"+lastId).css('display', 'block');
	}	$("#expError_"+spnId).html('');
	$("#expError_"+spnId).css("display","none");
	var clickedTypeId = ($(obj).attr("id"));
	var closeId = clickedTypeId.replace("_img_edit","_edit");
	var openId =  clickedTypeId.replace("_img_edit","_show");
	$(".credentialsShow_"+spnId).css('display', 'none');
	$("#"+closeId).css('display', 'none');
	$("#"+openId).css('display', 'block');
	$("#lastId_"+spnId).attr("value",closeId);
	var valueDivId = clickedTypeId.replace("_img_edit","_exceptionValue"+credentialType);
	var dropDownId = clickedTypeId.replace("_img_edit","_exceptionStates"+credentialType);
	$(".expDropDown_"+spnId).css('display', 'none');
	$(".expValue_"+spnId).css('display', 'block');
	$("#"+valueDivId).css('display', 'none');
	var statusId = clickedTypeId.replace("_img_edit","_status_div"+credentialType);
	var btnId =  clickedTypeId.replace("_img_edit","_btn_div"+credentialType);
	$(".btn-pill_"+spnId).css('display', 'none');
	$(".expStatus_"+spnId).css('display', 'block');
	$("#"+statusId).css('display', 'none');
	$("#"+btnId).css('display', 'block');
	var modifyDateId = clickedTypeId.replace("_img_edit","_modifyDate_div"+credentialType);
	var saveId =  clickedTypeId.replace("_img_edit","_save_div"+credentialType);
	$(".expSave_"+spnId).css('display', 'none');
	$(".expUpdated_"+spnId).css('display', 'block');
	$("#"+modifyDateId).css('display', 'none');
	$("#"+saveId).css('display', 'block');
/* 	alert($("#"+dropDownId).next('select').attr("id"));
	$('div[name=' + dropDownId + ']').next('select').val(14).attr("selected", "selected"); */
	$("#"+dropDownId).css('display', 'block');
	$("#activeIndhidden_"+spnId).attr("value",'');
	$("#exceptionValuehidden_"+spnId).attr("value",'');

}

// on edit exceptions
function clickEdit(obj,credentialType,spnId){
	var lastId = $("#lastId_"+spnId).attr("value");
	if(lastId!=''&& lastId!= ' '&& lastId!='undefined'){
		$("#"+lastId).css('display', 'block');
	}	
	$("#expError_"+spnId).html('');
	$("#expError_"+spnId).css("display","none");
	var clickedTypeId = ($(obj).attr("id"));
	var closeId = clickedTypeId.replace("_img_done","_done");
	var openId =  clickedTypeId.replace("_img_done","_show");
	$(".credentialsShow_"+spnId).css('display', 'none');
	$("#"+closeId).css('display', 'none');
	$("#"+openId).css('display', 'block');
	$("#lastId_"+spnId).attr("value",closeId);
	var valueDivId = clickedTypeId.replace("_img_done","_exceptionValue"+credentialType);
	var dropDownId = clickedTypeId.replace("_img_done","_exceptionStates"+credentialType);
	$(".expDropDown_"+spnId).css('display', 'none');
	$(".expValue_"+spnId).css('display', 'block');
	$("#"+valueDivId).css('display', 'none');
	var statusId = clickedTypeId.replace("_img_done","_status_div"+credentialType);
	var btnId =  clickedTypeId.replace("_img_done","_btn_div"+credentialType);
	$(".btn-pill_"+spnId).css('display', 'none');
	$(".expStatus_"+spnId).css('display', 'block');
	$("#"+statusId).css('display', 'none');
	$("#"+btnId).css('display', 'block');
	var modifyDateId = clickedTypeId.replace("_img_done","_modifyDate_div"+credentialType);
	var saveId =  clickedTypeId.replace("_img_done","_save_div"+credentialType);
	$(".expSave_"+spnId).css('display', 'none');
	$(".expUpdated_"+spnId).css('display', 'block');
	$("#"+modifyDateId).css('display', 'none');
	$("#"+saveId).css('display', 'block');
/* 	alert($("#"+dropDownId).next('select').attr("id"));
	$('div[name=' + dropDownId + ']').next('select').val(14).attr("selected", "selected"); */
	$("#"+dropDownId).css('display', 'block');
	$("#activeIndhidden_"+spnId).attr("value",'');
	$("#exceptionValuehidden_"+spnId).attr("value",'');

}
function closeConfirmExpModal(spnId) {
	$("#confirmExceptionsPopUp_"+spnId).jqmHide();
}
function showStatesDropDown(spnId,count,type) {
		$("#selectStatesOptions_"+type+"_"+spnId+"_"+count).show();
}
function setDefaultStateValue(obj,type){
	var cl = $(obj).attr("class");

	var count = $("."+cl+"[type='checkbox']:checked").length;
	cl= cl.replace("check_"+type,"defaultExpVal"+type+"s");
	if (count > 0) {
		$('#'+cl).html(count + " selected");
	} else{
		$('#'+cl).html("-Select-");
	}
}

function cancelEdit(spnId,type,count,exceptionCategory){
	var id = "excpEdit_"+type+"_"+spnId+"_"+count;
	var val= $("#"+id).attr("value");
	var lastId = $("#lastId_"+spnId).attr("value");
	if(lastId!=''&& lastId!= ' '&& lastId!='undefined'){
		$("#"+lastId).css('display', 'block');
	}
	var expType = type.substring(1,2);
	var credType = type.substring(0,1);
	var btnValue = $("#"+exceptionCategory+"_"+spnId+"_"+count+"_init_val").attr("value");
	if(btnValue == 'true'){
		$("#off-"+type+"-"+spnId+"-"+count).removeAttr('checked');
		$("#on-"+type+"-"+spnId+"-"+count).attr('checked','checked');
		$("#on-"+type+"-"+spnId+"-"+count).trigger('click');
	}
	else {
		$('#on-'+type+'-'+spnId+'-'+count).removeAttr('checked');
		$('#off-'+type+'-'+spnId+'-'+count).attr('checked','checked');
		$("#off-"+type+"-"+spnId+"-"+count).trigger('click');
	}
	if(expType == 'g'){
		var expValue = $("#exclusions"+type+"_"+spnId+"_"+count+"_value_init_val").attr("value");
		$("#expValueDropDown"+type+"_"+spnId+"_"+count).val(expValue).attr("selected", "selected");
	}
	else if(expType == 's'){
		var expValue = $("#exclusions"+type+"_"+spnId+"_"+count+"_value_init_val").attr("value");
		var expValues=[];
		//checkedSoIds = $(checkedSos).val();
		expValue = expValue.replace("[","");
		expValue = expValue.replace("]","");
		expValues = expValue.split(",");
		$(".check_"+credType+"_"+spnId+"_"+count).attr("checked","");
		$.each(expValues, function(index1,value1){
			$(".check_"+credType+"_"+spnId+"_"+count).each( function(index,value) {
					var value2 = $(this).val();
					value1=$.trim(value1);
					value2=$.trim(value2);
					if(value2==value1){
						this.checked = "checked";
					}
			      });
	      });
		var expValueCount = $(".check_"+credType+"_"+spnId+"_"+count+"[type='checkbox']:checked").length;
		var cl= "defaultExpVal"+type+"_"+spnId+"_"+count;
		if (expValueCount > 0) {
			$('#'+cl).html(expValueCount + " selected");
		} else{
			$('#'+cl).html("-Select-");
		}
	}
	$('.expselect-options').val("-1").attr("selected", "selected");
	$("#expError_"+spnId).html('');
	$("#expError_"+spnId).css("display","none");
	$(".credentialsShow_"+spnId).css('display', 'none');
	$(".expDropDown_"+spnId).css('display', 'none');
	$(".expValue_"+spnId).css('display', 'block');
	$(".btn-pill_"+spnId).css('display', 'none');
	$(".expStatus_"+spnId).css('display', 'block');
	$(".expSave_"+spnId).css('display', 'none');
	$(".expUpdated_"+spnId).css('display', 'block');
	$("#activeIndhidden_"+spnId).attr("value",'');
	$("#exceptionValuehidden_"+spnId).attr("value",'');
	$("input[id='spnExceptionsDropDown_"+spnId+"']").focus();

}
</script>
</head>
<body class="tundra acquity">
<form id="updateExceptionsForm_${spnId}" action="#" name="updateExceptionsForm" >
<input id="credentialTypeIdhidden_${spnId}" name="exclusionsVO.credentialTypeId" type="hidden" value="0"/>
<input id="exceptionValuehidden_${spnId}" name="exclusionsVO.exceptionValue" type="hidden" value=""/>
<input id="activeIndhidden_${spnId}" name="exclusionsVO.activeInd" type="hidden" value=""/>
<input id="credentialCategoryIdhidden_${spnId}" name="exclusionsVO.credentialCategoryId" type="hidden" value="0"/>
<input id="exceptionTypeIdHidden_${spnId}" name="exclusionsVO.exceptionTypeId" type="hidden" value=''/>
<input id="spnIdHidden_${spnId}" name="exclusionsVO.spnId" type="hidden" value=''/>
<input id="exceptionCredentialTypeHidden_${spnId}" name="exclusionsVO.exceptionCredentialType" type="hidden" value=''/>
<input id="credentialTypeHidden_${spnId}" name="exclusionsVO.credentialType" type="hidden" value=''/>
<input id="credentialCategoryHidden_${spnId}" name="exclusionsVO.credentialCategory" type="hidden" value=''/>
<input id="lastId_${spnId}" name="lastId_${spnId}" type="hidden" value=""/>


<div style="border: none">
	<c:if test="${exclusionLists.companyExclusions != null || exclusionLists.resourceExclusions != null}">
	<div style="text-align: left;"><strong>Exception Type:</strong></div>
	<div style="text-align: left;">
	<select id="spnExceptionsDropDown_${spnId}"
							name="spnExceptionsDropDown_${spnId}"
							style="width: 240px;" onchange="onExceptionropDownClick(this.id);">
							<option value="-1">-Select-</option>
							<c:forEach var="exceptionType"
								items="${exceptionTypes}">
								<option value="${exceptionType.id}">${exceptionType.descr}</option>
							</c:forEach>
	</select>
	</div>
	</c:if>
	<div  class="successBox" style="display:none;" id="tabSpnExpMsg_${spnId}">
	</div>
	<c:if test="${exclusionLists.companyExclusions == null && exclusionLists.resourceExclusions == null}">
	<div style="text-align: left;"><strong>No credentials available.</strong></div>
	</c:if>
	<div id="expError_${spnId}" class="errorBox" style="height: 20px;"></div>
	<div id="graceExceptionDetails_${spnId}" style="display: none;">

	<%-- <fmt:message bundle="${serviceliveCopyBundle}"
									key="text.spn.exceptions.gracePeriod" /> --%>
	<div id="text" style="text-align: left;">In many states it can take several days or weeks to issue new licenses to providers. Add a grace period to prevent providers from being deemed out of compliance while they are waiting to receive new documents. Adding exceptions <b>will affect</b> the membership of the network.	
	</div>
	
	<table class="exceptionsHeader">
	<tr id="gracePeriodExceptionsHeader">
	<td width=35%><div style="float:left;"><strong>Credentials</strong>
	</div>
	</td>
	<td width=17%>
	<div style="float:left important!;cursor:text; border-bottom:1px dotted; width:96px;" title="This will extend the expiration date of a given credential. Ex: if a business license must be updated annually on Jan 1, a 60 day grace period would give the provider until Mar 2 to be in compliance."><strong>Grace Period</strong>
	</div>
	</td>
	<td width=20%>
	<div style="float:center;"><strong>Exception Status</strong>
	</div>
	</td>
	<td width=50%>
	<div style="float:center;"><strong>Last Updated</strong>
	</div>
	</td>
	</tr>
	</table>
	<c:if test="${exclusionLists.companyExclusions != null}">		
	<div id="companyCredentials" class="credentials" style="width: 100%">
	<strong>&nbsp;Company Credentials</strong>
	</div>
	<table class="detailHdr">
	<c:forEach items="${exclusionLists.companyExclusions}" var ="companyCredentialExclusion" varStatus="status">
	<c:if test="${(null!=companyCredentialExclusion.credentialExceptionId && 1==companyCredentialExclusion.exceptionTypeId)||(null==companyCredentialExclusion.credentialExceptionId) || (null!=companyCredentialExclusion.credentialExceptionId && 2==companyCredentialExclusion.exceptionTypeId && 1 == companyCredentialExclusion.expCount)}">
	<tr>
	<td width="35%">
	<c:if test="${(null!=companyCredentialExclusion.credentialExceptionId && 1==companyCredentialExclusion.exceptionTypeId)}">
	<div id="exclusionscg_${spnId}_${status.count}_done" class="credentialsDone_${spnId}">
	<i class="icon-pencil"  style="cursor: pointer;"  title="Edit this exception" id="exclusionscg_${spnId}_${status.count}_img_done"
							onclick="clickEdit(this,'c',${spnId})"/>&nbsp;${companyCredentialExclusion.credentialType}<c:if test="${null!=companyCredentialExclusion.credentialCategory}">
							 > ${companyCredentialExclusion.credentialCategory}
							 </c:if>
	</div>	
	<input id="excpEdit_cg_${spnId}_${status.count}_init_val" type="hidden" value="edit" />
	</c:if>
	<c:if test="${(null==companyCredentialExclusion.credentialExceptionId) || (null!=companyCredentialExclusion.credentialExceptionId && 2==companyCredentialExclusion.exceptionTypeId && 1 == companyCredentialExclusion.expCount)}">
	<div id="exclusionscg_${spnId}_${status.count}_edit" class="credentialsEdit_${spnId}">
	<i class="icon-plus" style="cursor: pointer;" title="Add an exception" id="exclusionscg_${spnId}_${status.count}_img_edit"
								onclick="clickAdd(this,'c',${spnId})" />&nbsp;${companyCredentialExclusion.credentialType}<c:if test="${null!=companyCredentialExclusion.credentialCategory}">
							 > ${companyCredentialExclusion.credentialCategory}
							 </c:if>
	</div>
		<input id="excpEdit_cg_${spnId}_${status.count}_init_val" type="hidden" value="add" />
	</c:if>
	<div id="exclusionscg_${spnId}_${status.count}_show" style="display: none;" class="credentialsShow_${spnId}">
	&nbsp;${companyCredentialExclusion.credentialType} <c:if test="${null!=companyCredentialExclusion.credentialCategory}">
							 > ${companyCredentialExclusion.credentialCategory}
							 </c:if>
	</div>
	 <input id="credentialType_cg_${spnId}_${status.count}_init_val" type="hidden" value="${companyCredentialExclusion.credentialType}" />
	 <input id="credentialCategory_cg_${spnId}_${status.count}_init_val" type="hidden" value="${companyCredentialExclusion.credentialCategory}" />
	</td>
	<td width=17%>
	<div id="exclusionscg_${spnId}_${status.count}_exceptionValuec" class="expValue_${spnId}" style="padding-left:25px;">
	<c:if test="${null!=companyCredentialExclusion.credentialExceptionId && 1==companyCredentialExclusion.exceptionTypeId}">
		${companyCredentialExclusion.exceptionValue}&nbsp;days
	</c:if>
	<c:if test="${null==companyCredentialExclusion.credentialExceptionId || 1!=companyCredentialExclusion.exceptionTypeId}">
	&nbsp;
	</c:if>
	</div>
	<div id="exclusionscg_${spnId}_${status.count}_exceptionStatesc" style="display: none;" class="expDropDown_${spnId}">
			<select id="expValueDropDowncg_${spnId}_${status.count}" class="pickedExceptions pickedExceptionsClick"
							name="expValueDropDowncg_${spnId}_${status.count}"
							style="width: 100px;" onchange="setExceptionValue(this,${spnId})">
							<c:if test="${null!=companyCredentialExclusion.credentialExceptionId && 1==companyCredentialExclusion.exceptionTypeId}">
								<option value="-1">-Select-</option>
							</c:if>
							<c:if test="${null==companyCredentialExclusion.credentialExceptionId || 1!=companyCredentialExclusion.exceptionTypeId}">
								<option selected="selected" value="-1">-Select-</option>
							</c:if>
							<c:forEach var="gracePeriod"
								items="${gracePeriods}">
								<c:if test="${null!=companyCredentialExclusion.credentialExceptionId && 1==companyCredentialExclusion.exceptionTypeId && gracePeriod.descr!=companyCredentialExclusion.exceptionValue}">
																	<option value="${gracePeriod.descr}">${gracePeriod.descr}&nbsp;days</option>
									
								</c:if>
								<c:if test="${null!=companyCredentialExclusion.credentialExceptionId && 1==companyCredentialExclusion.exceptionTypeId && gracePeriod.descr==companyCredentialExclusion.exceptionValue}">
																	<option selected="selected" value="${gracePeriod.descr}">${gracePeriod.descr}&nbsp;days</option>
									
								</c:if>
								<c:if test="${null==companyCredentialExclusion.credentialExceptionId || 1!=companyCredentialExclusion.exceptionTypeId}">
																	<option value="${gracePeriod.descr}">${gracePeriod.descr}&nbsp;days</option>
								</c:if>
							</c:forEach>
			</select>
						    						    <input id="exclusionscg_${spnId}_${status.count}_value_init_val" type="hidden" value="${companyCredentialExclusion.exceptionValue}" />
		</div>
	</td>
	<td width=21% style="text-align: center!important;">
	<div id="exclusionscg_${spnId}_${status.count}_status_divc" class="expStatus_${spnId}">
	<c:if test="${null!=companyCredentialExclusion.credentialExceptionId}">
	<c:if test="${null!=companyCredentialExclusion.credentialExceptionId && 1==companyCredentialExclusion.exceptionTypeId}">
	<c:if test="${true == companyCredentialExclusion.activeInd}">
		<img  title="Buyer override active" src="${staticContextPath}/images/common/status-blue.png"
								alt="Active" />&nbsp;Active
	</c:if>
	<c:if test="${false == companyCredentialExclusion.activeInd}">
	Inactive
	</c:if>
	</c:if>
	<c:if test="${null==companyCredentialExclusion.credentialExceptionId || 1!=companyCredentialExclusion.exceptionTypeId}">
	&nbsp;
	</c:if>
	</c:if>
	<c:if test="${null==companyCredentialExclusion.credentialExceptionId}">
	&nbsp;
	</c:if>
	</div>
	<div class="btn-pill_${spnId}" style="display: none;" id="exclusionscg_${spnId}_${status.count}_btn_divc" >
	<div class="btn-pill" >
					<c:if test="${null!=companyCredentialExclusion.credentialExceptionId && 1==companyCredentialExclusion.exceptionTypeId}">
						<c:if test="${true == companyCredentialExclusion.activeInd}">
						   <input type="radio" value="On" name="on-off-cg-${spnId}-${status.count}" id="on-cg-${spnId}-${status.count}" class="l_${spnId}">
						    <label for="on-cg-${spnId}-${status.count}" class="checked">Active</label> 
						    <input type="radio" value="Off" name="on-off-cg-${spnId}-${status.count}" id="off-cg-${spnId}-${status.count}"  class="l_${spnId}">
						    <label for="off-cg-${spnId}-${status.count}">Inactive</label>
												 	 <input id="vendor_g_${spnId}_${status.count}_init_val" type="hidden" value="${companyCredentialExclusion.activeInd}" />
						</c:if>
						<c:if test="${false == companyCredentialExclusion.activeInd}">
						   <input type="radio" value="On" name="on-off-${spnId}-${status.count}" id="on-cg-${spnId}-${status.count}"  class="l_${spnId}">
						    <label for="on-cg-${spnId}-${status.count}">Active</label> 
						    <input type="radio" value="Off" name="on-off-${spnId}-${status.count}" id="off-cg-${spnId}-${status.count}"  class="l_${spnId}">
						    <label for="off-cg-${spnId}-${status.count}" class="checked">Inactive</label>
						    						 	 <input id="vendor_g_${spnId}_${status.count}_init_val" type="hidden" value="${companyCredentialExclusion.activeInd}" />
						    
						 </c:if>
						 <c:if test="${true != companyCredentialExclusion.activeInd && false!= companyCredentialExclusion.activeInd}">
						   <input type="radio" value="On" name="on-off-cg-${spnId}-${status.count}" id="on-cg-${spnId}-${status.count}"  class="l_${spnId}">
						    <label for="on-cg-${spnId}-${status.count}" class="checked">Active</label> 
						    <input type="radio" value="Off" name="on-off-cg-${spnId}-${status.count}" id="off-cg-${spnId}-${status.count}" class="l_${spnId}">
						    <label for="off-cg-${spnId}-${status.count}">Inactive</label>
						    						    <input id="vendor_g_${spnId}_${status.count}_init_val" type="hidden" value="true" />
						    
						 </c:if>
						</c:if>
						<c:if test="${null==companyCredentialExclusion.credentialExceptionId || 1!=companyCredentialExclusion.exceptionTypeId}">
							<input type="radio" value="On" name="on-off-cg-${spnId}-${status.count}" id="on-cg-${spnId}-${status.count}" class="l_${spnId}">
						    <label for="on-cg-${spnId}-${status.count}" class="checked">Active</label> 
						    <input type="radio" value="Off" name="on-off-cg-${spnId}-${status.count}" id="off-cg-${spnId}-${status.count}" class="l_${spnId}">
						    <label for="off-cg-${spnId}-${status.count}">Inactive</label>
						    <input id="vendor_g_${spnId}_${status.count}_init_val" type="hidden" value="true" />
						</c:if>
						</div>
						</div>
	</td>
	<td width=50% style="text-align: center!important;">
		<div id="exclusionscg_${spnId}_${status.count}_modifyDate_divc" class="expUpdated_${spnId}">
		<c:if test="${null!=companyCredentialExclusion.credentialExceptionId && null!=companyCredentialExclusion.modifiedDate && 1==companyCredentialExclusion.exceptionTypeId}">
		<fmt:formatDate value="${companyCredentialExclusion.modifiedDate}"
									pattern="EEE MM/dd/yy hh:mm aa z"/> 
		</c:if>
		<c:if test="${null==companyCredentialExclusion.credentialExceptionId || null==companyCredentialExclusion.modifiedDate || 1!=companyCredentialExclusion.exceptionTypeId}">
		&nbsp;
		</c:if>
		</div>
		<div id="exclusionscg_${spnId}_${status.count}_save_divc" style="display:none; padding-left:90px;" class="expSave_${spnId}">
		<span>
		<c:if test="${null!=companyCredentialExclusion.credentialCategory}">
		<input id="companyCredentialSaveButton" class="actionButton" type="button" value="Save" style="border:none;float: left; margin-left: 15px; text-transform: capitalize;" onclick="confirmExceptions(${companyCredentialExclusion.credentialTypeId},${companyCredentialExclusion.credentialCategoryId},'vendor',${companyCredentialExclusion.spnId},${status.count})"/>
		</c:if>
		<c:if test="${null==companyCredentialExclusion.credentialCategory}">
		<input id="companyCredentialSaveButton" class="actionButton" type="button" value="Save" style="border:none; float: left; margin-left: 15px; text-transform: capitalize;" onclick="confirmExceptions(${companyCredentialExclusion.credentialTypeId},' ','vendor',${companyCredentialExclusion.spnId},${status.count})"/>
		</c:if>
		</span>
		<span>	<a id="exclusionscg_${spnId}_${status.count}_cancel_divc" class="cancel" href="#" style="text-decoration: underline;  text-transform: capitalize; float: center;  color: grey;" onclick="cancelEdit(${spnId},'cg',${status.count},'vendor_g')">Cancel</a>
		</span>	
		</div>
	</td>
	</tr>
	</c:if>
	</c:forEach>
	</table>
	<br/>
	</c:if>
	<c:if test="${exclusionLists.resourceExclusions != null}">
	<div id="resourceCredentials" class="credentials" style="width: 100%">
	<strong>&nbsp;Resource Credentials</strong>
	</div>
		<table class="detailHdr">
	<c:forEach items="${exclusionLists.resourceExclusions}" var ="resourceCredentialExclusion" varStatus="status">
	<c:if test="${(null!=resourceCredentialExclusion.credentialExceptionId && 1==resourceCredentialExclusion.exceptionTypeId)||(null==resourceCredentialExclusion.credentialExceptionId)|| (null!=resourceCredentialExclusion.credentialExceptionId && 2==resourceCredentialExclusion.exceptionTypeId && 1 == resourceCredentialExclusion.expCount)}">
	<tr>
	<td width="35%">
	<c:if test="${(null!=resourceCredentialExclusion.credentialExceptionId && 1==resourceCredentialExclusion.exceptionTypeId)}">
	<div id="exclusionsrg_${spnId}_${status.count}_done" class="credentialsDone_${spnId}">
	<i class="icon-pencil"  style="cursor: pointer;" title="Edit this exception" id="exclusionsrg_${spnId}_${status.count}_img_done" 
							onclick="clickEdit(this,'r',${spnId})" />&nbsp;${resourceCredentialExclusion.credentialType}
							<c:if test="${null!=resourceCredentialExclusion.credentialCategory}">
							 > ${resourceCredentialExclusion.credentialCategory}
							 </c:if>
	</div>
		<input id="excpEdit_rg_${spnId}_${status.count}_init_val" type="hidden" value="edit" />
	</c:if>
	<c:if test="${(null==resourceCredentialExclusion.credentialExceptionId)|| (null!=resourceCredentialExclusion.credentialExceptionId && 2==resourceCredentialExclusion.exceptionTypeId && 1 == resourceCredentialExclusion.expCount)}">
	<div id="exclusionsrg_${spnId}_${status.count}_edit" class="credentialsEdit_${spnId}">
	<i class="icon-plus" style="cursor: pointer;" title="Add an exception"  id="exclusionsrg_${spnId}_${status.count}_img_edit"
								onclick="clickAdd(this,'r',${spnId})" />&nbsp;${resourceCredentialExclusion.credentialType} <c:if test="${null!=resourceCredentialExclusion.credentialCategory}">
							 > ${resourceCredentialExclusion.credentialCategory}
							 </c:if>
	</div>
		<input id="excpEdit_rg_${spnId}_${status.count}_init_val" type="hidden" value="add" />
	</c:if>	
	<div id="exclusionsrg_${spnId}_${status.count}_show" class="credentialsShow_${spnId}" style="display: none;">
	&nbsp;${resourceCredentialExclusion.credentialType} <c:if test="${null!=resourceCredentialExclusion.credentialCategory}">
							 > ${resourceCredentialExclusion.credentialCategory}
							 </c:if>
	</div>
	<input id="credentialType_rg_${spnId}_${status.count}_init_val" type="hidden" value="${resourceCredentialExclusion.credentialType}" />
	 <input id="credentialCategory_rg_${spnId}_${status.count}_init_val" type="hidden" value="${resourceCredentialExclusion.credentialCategory}" />
	</td>
	<td width="17%">
	<div id="exclusionsrg_${spnId}_${status.count}_exceptionValuer" class="expValue_${spnId}" style="padding-left:25px;">
	<c:if test="${null!=resourceCredentialExclusion.credentialExceptionId && 1==resourceCredentialExclusion.exceptionTypeId}">
		${resourceCredentialExclusion.exceptionValue} days
	</c:if>
	<c:if test="${null==resourceCredentialExclusion.credentialExceptionId || 1!=resourceCredentialExclusion.exceptionTypeId}">
	&nbsp;
	</c:if>
	</div>
	<div id="exclusionsrg_${spnId}_${status.count}_exceptionStatesr" style="display: none; padding-left:5px;" class="expDropDown_${spnId}">
			<select id="expValueDropDownrg_${spnId}_${status.count}"
							name="expValueDropDownrg_${spnId}_${status.count}"
							style="width: 100px;" onchange="setExceptionValue(this,${spnId})">
							<c:if test="${null!=resourceCredentialExclusion.credentialExceptionId && 1==resourceCredentialExclusion.exceptionTypeId}">
								<option value="-1">-Select-</option>
							</c:if>
							<c:if test="${null==resourceCredentialExclusion.credentialExceptionId || 1!=resourceCredentialExclusion.exceptionTypeId}">
								<option selected="selected" value="-1">-Select-</option>
							</c:if>
							<c:forEach var="gracePeriod"
								items="${gracePeriods}">
								<c:if test="${null!=resourceCredentialExclusion.credentialExceptionId && 1==resourceCredentialExclusion.exceptionTypeId && gracePeriod.descr!=resourceCredentialExclusion.exceptionValue}">
																	<option value="${gracePeriod.descr}">${gracePeriod.descr}&nbsp;days</option>
									
								</c:if>
								<c:if test="${null!=resourceCredentialExclusion.credentialExceptionId && 1==resourceCredentialExclusion.exceptionTypeId && gracePeriod.descr==resourceCredentialExclusion.exceptionValue}">
																	<option selected="selected" value="${gracePeriod.descr}">${gracePeriod.descr}&nbsp;days</option>
									
								</c:if>
								<c:if test="${null==resourceCredentialExclusion.credentialExceptionId || 1!=resourceCredentialExclusion.exceptionTypeId}">
																	<option value="${gracePeriod.descr}">${gracePeriod.descr}&nbsp;days</option>
								</c:if>
							</c:forEach>
			</select>
									    						    <input id="exclusionsrg_${spnId}_${status.count}_value_init_val" type="hidden" value="${resourceCredentialExclusion.exceptionValue}" />
			
		</div>
	</td>
	<td width="21%" style="text-align: center!important;">
	<div id="exclusionsrg_${spnId}_${status.count}_status_divr" class="expStatus_${spnId}">
	<c:if test="${null!=resourceCredentialExclusion.credentialExceptionId}">
	<c:if test="${null!=resourceCredentialExclusion.credentialExceptionId && 1==resourceCredentialExclusion.exceptionTypeId}">
	<c:if test="${true == resourceCredentialExclusion.activeInd}">
		<img  title="Buyer override active" src="${staticContextPath}/images/common/status-blue.png"
								alt="Active" />&nbsp;Active
	</c:if>
	<c:if test="${false == resourceCredentialExclusion.activeInd}">
	Inactive
	</c:if>
	</c:if>
	<c:if test="${null==resourceCredentialExclusion.credentialExceptionId || 1!=resourceCredentialExclusion.exceptionTypeId}">
	&nbsp;
	</c:if>
	</c:if>
	<c:if test="${null==resourceCredentialExclusion.credentialExceptionId}">
	&nbsp;
	</c:if>
	</div>
	<div class="btn-pill_${spnId}"  id="exclusionsrg_${spnId}_${status.count}_btn_divr" style="display: none;">
	<div class="btn-pill">
		<c:if test="${null!=resourceCredentialExclusion.credentialExceptionId && 1==resourceCredentialExclusion.exceptionTypeId}">
						<c:if test="${true == resourceCredentialExclusion.activeInd}">
						   <input type="radio" value="On" name="on-off-rg-${spnId}-${status.count}" id="on-rg-${spnId}-${status.count}" class="l_${spnId}">
						    <label for="on-rg-${spnId}-${status.count}" class="checked">Active</label> 
						    <input type="radio" value="Off" name="on-off-rg-${spnId}-${status.count}" id="off-rg-${spnId}-${status.count}" class="l_${spnId}">
						    <label for="off-rg-${spnId}-${status.count}">Inactive</label>
												 <input id="resource_g_${spnId}_${status.count}_init_val" type="hidden" value="${resourceCredentialExclusion.activeInd}" />
						</c:if>
						<c:if test="${false == resourceCredentialExclusion.activeInd}">
						   <input type="radio" value="On" name="on-off-rg-${spnId}-${status.count}" id="on-rg-${spnId}-${status.count}" class="${spnId}">
						    <label for="on-rg-${spnId}-${status.count}">Active</label> 
						    <input type="radio" value="Off" name="on-off-rg-${spnId}-${status.count}" id="off-rg-${spnId}-${status.count}" class="${spnId}">
						    <label for="off-rg-${spnId}-${status.count}" class="checked">Inactive</label>
						  						 <input id="resource_g_${spnId}_${status.count}_init_val" type="hidden" value="${resourceCredentialExclusion.activeInd}" />
						    
						 </c:if>
						  <c:if test="${true != resourceCredentialExclusion.activeInd && false!= resourceCredentialExclusion.activeInd}">
						   <input type="radio" value="On" name="on-off-rg-${spnId}-${status.count}" id="on-rg-${spnId}-${status.count}" class="l_${spnId}">
						    <label for="on-rg-${spnId}-${status.count}" class="checked">Active</label> 
						    <input type="radio" value="Off" name="on-off-rg-${spnId}-${status.count}" id="off-rg-${spnId}-${status.count}" class="l_${spnId}">
						    <label for="off-rg-${spnId}-${status.count}">Inactive</label>
						    						 <input id="resource_g_${spnId}_${status.count}_init_val" type="hidden" value="true" />
						    
						 </c:if>
			</c:if>
			<c:if test="${null==resourceCredentialExclusion.credentialExceptionId || 1!=resourceCredentialExclusion.exceptionTypeId}">
				 <input type="radio" value="On" name="on-off-rg-${spnId}-${status.count}" id="on-rg-${spnId}-${status.count}" class="l_${spnId}">
				 <label for="on-rg-${spnId}-${status.count}" class="checked">Active</label> 
				 <input type="radio" value="Off" name="on-off-rg-${spnId}-${status.count}" id="off-rg-${spnId}-${status.count}" class="l_${spnId}">
				 <label for="off-rg-${spnId}-${status.count}">Inactive</label>
				<input id="resource_g_${spnId}_${status.count}_init_val" type="hidden" value="true" />
			</c:if>
						</div>
						</div>
	</td>
	<td width="50%" style="text-align: center!important;">
		<div id="exclusionsrg_${spnId}_${status.count}_modifyDate_divr" class="expUpdated_${spnId}">
		<c:if test="${null!=resourceCredentialExclusion.credentialExceptionId && null!=resourceCredentialExclusion.modifiedDate && 1==resourceCredentialExclusion.exceptionTypeId}">
		<fmt:formatDate value="${resourceCredentialExclusion.modifiedDate}"
									pattern="EEE MM/dd/yy hh:mm aa z" /> 
		</c:if>
		<c:if test="${null==resourceCredentialExclusion.credentialExceptionId || null==resourceCredentialExclusion.modifiedDate || 1!=resourceCredentialExclusion.exceptionTypeId}">
		&nbsp;
		</c:if>
		</div>
		<div id="exclusionsrg_${spnId}_${status.count}_save_divr" style="display:none;padding-left: 60px;" class="expSave_${spnId}">
		<span>
		<c:if test="${null!=resourceCredentialExclusion.credentialCategory}">
		<input id="resourceCredentialSaveButton" class="actionButton" type="button" value="Save" style="border:none;float: left; margin-left: 15px; text-transform: capitalize;" onclick="confirmExceptions(${resourceCredentialExclusion.credentialTypeId},${resourceCredentialExclusion.credentialCategoryId},'resource',${resourceCredentialExclusion.spnId},${status.count})"/>
		</c:if>
		<c:if test="${null==resourceCredentialExclusion.credentialCategory}">
		<input id="resourceCredentialSaveButton" class="actionButton" type="button" value="Save" style="border:none; float: left; margin-left: 15px; text-transform: capitalize;" onclick="confirmExceptions(${resourceCredentialExclusion.credentialTypeId},' ','resource',${resourceCredentialExclusion.spnId},${status.count})"/>
		</c:if>	
		</span>
		<span>	<a id="exclusionsrg_${spnId}_${status.count}_cancel_divr" class="cancel" href="#" style="text-decoration: underline;  text-transform: capitalize; float: center;  color: grey;" onclick="cancelEdit(${spnId},'rg',${status.count},'resource_g')">Cancel</a>
		</span>		
		</div>
	</td>
	</tr>
	</c:if>
	</c:forEach>
	</table>
	<br/>
	<br/>
</c:if>
	</div>
	
<!-- 	State Exception Details
 -->	
 	<div id="stateExceptionDetails_${spnId}" style="display: none;">

	<%-- <fmt:message bundle="${serviceliveCopyBundle}"
									key="text.spn.exceptions.state" /> --%>
	<div id="text" style="text-align: left;">Some states do not require certain licenses. You may want to waive requirements in these states so providers who are not legally bound in their dispatch location don't have to jump through hoops to remain in the network. Adding exceptions <strong>will affect</strong> the membership of the network.
	</div>
	
	<table class="exceptionsHeader">
	<tr id="gracePeriodExceptionsHeader">
	<td width=35%><div style="float:left;"><strong>Credentials</strong>
	</div>
	</td>
	<td width=17%>
	<div style="float:center;cursor:text;border-bottom:1px dotted; width:110px;" title="States refer to dispatch location of the provider firm, not service location."><strong>Exempt States</strong>
	</div>
	<!-- <div style=" border-bottom:1px dotted; width:70%; margin-left:25px;" align="right">
	</div> -->
	</td>
	<td width=20%>
	<div style="float:center;"><strong>Exception Status</strong>
	</div>
	</td>
	<td width=50%>
	<div style="float:center;"><strong>Last Updated</strong>
	</div>
	</td>
	</tr>
	</table>
	<c:if test="${exclusionLists.companyExclusions != null}">	
	<div id="companyCredentials" class="credentials" style="width: 100%">
	<strong>&nbsp;Company Credentials</strong>
	</div>
	<table class="detailHdr">
	<c:forEach items="${exclusionLists.companyExclusions}" var ="companyCredentialExclusion" varStatus="status">
	<c:if test="${(null!=companyCredentialExclusion.credentialExceptionId && 2==companyCredentialExclusion.exceptionTypeId)||(null==companyCredentialExclusion.credentialExceptionId)|| (null!=companyCredentialExclusion.credentialExceptionId && 1==companyCredentialExclusion.exceptionTypeId && 1 == companyCredentialExclusion.expCount)}">
	<tr>
	<td width=35%>
	<c:if test="${(null!=companyCredentialExclusion.credentialExceptionId && 2==companyCredentialExclusion.exceptionTypeId)}">
	<div id="exclusionscs_${spnId}_${status.count}_done"  class="credentialsDone_${spnId}">
	<i class="icon-pencil"  style="cursor: pointer;" title="Edit this exception" id="exclusionscs_${spnId}_${status.count}_img_done"
							 onclick="clickEdit(this,'c',${spnId})" />&nbsp;${companyCredentialExclusion.credentialType}<c:if test="${null!=companyCredentialExclusion.credentialCategory}">
							 > ${companyCredentialExclusion.credentialCategory}
							 </c:if>
	</div>
		<input id="excpEdit_cs_${spnId}_${status.count}_init_val" type="hidden" value="edit" />
	</c:if>
	<c:if test="${(null==companyCredentialExclusion.credentialExceptionId)|| (null!=companyCredentialExclusion.credentialExceptionId && 1==companyCredentialExclusion.exceptionTypeId && 1 == companyCredentialExclusion.expCount)}">
	<div id="exclusionscs_${spnId}_${status.count}_edit" class="credentialsEdit_${spnId}">
	<i class="icon-plus" style="cursor: pointer;" title="Add an exception"  id="exclusionscs_${spnId}_${status.count}_img_edit"
								onclick="clickAdd(this,'c',${spnId})" />&nbsp;${companyCredentialExclusion.credentialType}<c:if test="${null!=companyCredentialExclusion.credentialCategory}">
							 > ${companyCredentialExclusion.credentialCategory}
							 </c:if>
	</div>
		<input id="excpEdit_cs_${spnId}_${status.count}_init_val" type="hidden" value="add" />
	</c:if>
	<div id="exclusionscs_${spnId}_${status.count}_show" class="credentialsShow_${spnId}" style="display: none;">
	&nbsp;${companyCredentialExclusion.credentialType} <c:if test="${null!=companyCredentialExclusion.credentialCategory}">
							 > ${companyCredentialExclusion.credentialCategory}
							 </c:if>
	</div>
	<input id="credentialType_cs_${spnId}_${status.count}_init_val" type="hidden" value="${companyCredentialExclusion.credentialType}" />
	 <input id="credentialCategory_cs_${spnId}_${status.count}_init_val" type="hidden" value="${companyCredentialExclusion.credentialCategory}" />
	</td>
	<td width=17%>
	<div id="exclusionscs_${spnId}_${status.count}_exceptionValuec" class="expValue_${spnId}">
	<c:if test="${null!=companyCredentialExclusion.credentialExceptionId && 2==companyCredentialExclusion.exceptionTypeId}">
		${companyCredentialExclusion.selectedStates}<c:if test="${null != companyCredentialExclusion.selectedStatesLeft}">
		<span style="border-bottom: 1px dotted; cursor:default;" title="${companyCredentialExclusion.selectedStatesLeft}">and ${companyCredentialExclusion.remainingStatesCount} more</span>
		</c:if>
	</c:if>
	<c:if test="${null==companyCredentialExclusion.credentialExceptionId || 2!=companyCredentialExclusion.exceptionTypeId}">
	&nbsp;
	</c:if>
	</div>
	<div id="exclusionscs_${spnId}_${status.count}_exceptionStatesc" style="display: none;" class="expDropDown_${spnId}">
			<div id="expValueDropDowncs_${spnId}_${status.count}" class="pickedExceptions pickedExceptionsClick"
										style="width: 100px; float: left;margin-left: 2px; height:17px; overflow: hidden;" onclick="showStatesDropDown(${spnId},${status.count},'c')">
									   	<c:choose>
									   	<c:when test="${fn:length(companyCredentialExclusion.selectedStatesValues) > 0}">
											<label id="defaultExpValcs_${spnId}_${status.count}">${fn:length(companyCredentialExclusion.selectedStatesValues)} selected</label>
										</c:when>
										<c:otherwise><label id="defaultExpValcs_${spnId}_${status.count}">-Select-</label></c:otherwise>
										</c:choose>
									</div>
									<div class="expselect-options" id="selectStatesOptions_c_${spnId}_${status.count}"
										style="display: none;width: 95px; margin-top: 36px;">
										<c:if test="${null != exceptionStates}">
										<c:forEach var="exceptionState" items="${exceptionStates}" varStatus="i">
											<c:set var="val2" value="0"></c:set>
											<c:forEach var="selectedState" items="${companyCredentialExclusion.selectedStatesValues}">
												<c:if test="${exceptionState == selectedState}">
												<div style="clear:left;padding-top:5px;"> 
													<div style="float: left;"> 
													<input type="checkbox" checked="checked" class="check_c_${spnId}_${status.count}"
													name="companyCredentialExclusion.selectedStatesValues[${i.count}]" value="${exceptionState}" id="${i.count}"
													onclick="setDefaultStateValue(this,'c')"/>
													</div>
													<div style="float: left;padding-left:3px;">
														${exceptionState}
													 </div>
												</div >
													<c:set var="val2" value="1"></c:set>
												</c:if>
											</c:forEach>
											<c:if test="${val2 == 0}">
											<div style="clear:left;padding-top:5px;">
												<div style="float: left;">
												<input type="checkbox"
													name="companyCredentialExclusion.selectedStatesValues[${i.count}]" class="check_c_${spnId}_${status.count}" value="${exceptionState}"
													onclick="setDefaultStateValue(this,'c')"/>
												</div>
													<div style="float: left;padding-left:3px;">${exceptionState}<br />
												 </div>
												</div>
											</c:if>
										</c:forEach>
										</c:if>
									</div>
															    						    <input id="exclusionscs_${spnId}_${status.count}_value_init_val" type="hidden" value="${companyCredentialExclusion.selectedStatesValues}" />
									
		</div>
	</td>
	<td width=21% style="text-align: center!important;">
	<div id="exclusionscs_${spnId}_${status.count}_status_divc" class="expStatus_${spnId}">
	<c:if test="${null!=companyCredentialExclusion.credentialExceptionId}">
	<c:if test="${null!=companyCredentialExclusion.credentialExceptionId && 2==companyCredentialExclusion.exceptionTypeId}">
	<c:if test="${true == companyCredentialExclusion.activeInd}">
		<img  title="Buyer override active" src="${staticContextPath}/images/common/status-blue.png"
								alt="Active" />&nbsp;Active
	</c:if>
	<c:if test="${false == companyCredentialExclusion.activeInd}">
	Inactive
	</c:if>
	</c:if>
	<c:if test="${null==companyCredentialExclusion.credentialExceptionId || 2!=companyCredentialExclusion.exceptionTypeId}">
	&nbsp;
	</c:if>
	</c:if>
	<c:if test="${null==companyCredentialExclusion.credentialExceptionId}">
	&nbsp;
	</c:if>
	</div>
	<div class="btn-pill_${spnId}" id="exclusionscs_${spnId}_${status.count}_btn_divc" style="display: none;">
	<div class="btn-pill" >
			<c:if test="${null!=companyCredentialExclusion.credentialExceptionId && 2==companyCredentialExclusion.exceptionTypeId}">
						<c:if test="${true == companyCredentialExclusion.activeInd}">
						   <input type="radio" value="On" name="on-off-cs-${spnId}-${status.count}" id="on-cs-${spnId}-${status.count}" class="l_${spnId}">
						    <label for="on-cs-${spnId}-${status.count}" class="checked">Active</label> 
						    <input type="radio" value="Off" name="on-off-cs-${spnId}-${status.count}" id="off-cs-${spnId}-${status.count}" class="l_${spnId}">
						    <label for="off-cs-${spnId}-${status.count}">Inactive</label>
												 <input id="vendor_s_${spnId}_${status.count}_init_val" type="hidden" value="${companyCredentialExclusion.activeInd}" />
						</c:if>
						<c:if test="${false == companyCredentialExclusion.activeInd}">
						   <input type="radio" value="On" name="on-off-cs-${spnId}-${status.count}" id="on-cs-${spnId}-${status.count}" class="l_${spnId}">
						    <label for="on-cs-${spnId}-${status.count}">Active</label> 
						    <input type="radio" value="Off" name="on-off-cs-${spnId}-${status.count}" id="off-cs-${spnId}-${status.count}" class="l_${spnId}">
						    <label for="off-cs-${spnId}-${status.count}" class="checked">Inactive</label>
						    						 <input id="vendor_s_${spnId}_${status.count}_init_val" type="hidden" value="${companyCredentialExclusion.activeInd}" />
						    
						 </c:if>
						 <c:if test="${true != companyCredentialExclusion.activeInd && false!= companyCredentialExclusion.activeInd}">
						   <input type="radio" value="On" name="on-off-cs-${spnId}-${status.count}" id="on-cs-${spnId}-${status.count}" class="l_${spnId}">
						    <label for="on-cs-${spnId}-${status.count}" class="checked">Active</label> 
						    <input type="radio" value="Off" name="on-off-cs-${spnId}-${status.count}" id="off-cs-${spnId}-${status.count}" class="l_${spnId}">
						    <label for="off-cs-${spnId}-${status.count}">Inactive</label>
						   						 <input id="vendor_s_${spnId}_${status.count}_init_val" type="hidden" value="true" />
						    
						 </c:if>
			</c:if>
			<c:if test="${null==companyCredentialExclusion.credentialExceptionId || 2!=companyCredentialExclusion.exceptionTypeId}">
				<input type="radio" value="On" name="on-off-cs-${spnId}-${status.count}" id="on-cs-${spnId}-${status.count}" class="l_${spnId}">
				<label for="on-cs-${spnId}-${status.count}" class="checked">Active</label> 
				<input type="radio" value="Off" name="on-off-cs-${spnId}-${status.count}" id="off-cs-${spnId}-${status.count}" class="l_${spnId}">
				<label for="off-cs-${spnId}-${status.count}">Inactive</label>
				<input id="vendor_s_${spnId}_${status.count}_init_val" type="hidden" value="true" />
			</c:if>
		</div>
	</div>
	</td>
	<td width=50% style="text-align: center!important;">
		<div id="exclusionscs_${spnId}_${status.count}_modifyDate_divc" class="expUpdated_${spnId}">
		<c:if test="${null!=companyCredentialExclusion.credentialExceptionId && null!=companyCredentialExclusion.modifiedDate && 2==companyCredentialExclusion.exceptionTypeId}">
		<fmt:formatDate value="${companyCredentialExclusion.modifiedDate}"
									pattern="EEE MM/dd/yy hh:mm aa z" /> 
		</c:if>
		<c:if test="${null==companyCredentialExclusion.credentialExceptionId || null==companyCredentialExclusion.modifiedDate || 2!=companyCredentialExclusion.exceptionTypeId}">
		&nbsp;
		</c:if>
		</div>
		<div id="exclusionscs_${spnId}_${status.count}_save_divc" style="display:none; padding-left: 90px;" class="expSave_${spnId}">
		<span>
		<c:if test="${null!=companyCredentialExclusion.credentialCategory}">
		<input id="companyCredentialSaveButton" class="actionButton" type="button" value="Save" style="border:none; float: left; margin-left: 15px; text-transform: capitalize;" onclick="confirmExceptions(${companyCredentialExclusion.credentialTypeId},${companyCredentialExclusion.credentialCategoryId},'vendor',${companyCredentialExclusion.spnId},${status.count})"/>
		</c:if>
		<c:if test="${null==companyCredentialExclusion.credentialCategory}">
		<input id="companyCredentialSaveButton" class="actionButton" type="button" value="Save" style="border:none;  float: left; margin-left: 15px; text-transform: capitalize;" onclick="confirmExceptions(${companyCredentialExclusion.credentialTypeId},' ','vendor',${companyCredentialExclusion.spnId},${status.count})"/>
		</c:if>
		</span>
		<span>	<a id="exclusionscs_${spnId}_${status.count}_cancel_divc" class="cancel" href="#" style="text-decoration: underline;  text-transform: capitalize; float: center; color: grey;" onclick="cancelEdit(${spnId},'cs',${status.count},'vendor_s')">Cancel</a>
		</span>				
		</div>
	</td>
	</tr>
	</c:if>
	</c:forEach>
	</table>
	<br/>
	</c:if>
	<c:if test="${exclusionLists.resourceExclusions != null}">
	<div id="resourceCredentials" class="credentials" style="width: 100%">
	<strong>&nbsp;Resource Credentials</strong>
	</div>
		<table class="detailHdr">
	<c:forEach items="${exclusionLists.resourceExclusions}" var ="resourceCredentialExclusion" varStatus="status">
	<c:if test="${(null!=resourceCredentialExclusion.credentialExceptionId && 2==resourceCredentialExclusion.exceptionTypeId)||(null==resourceCredentialExclusion.credentialExceptionId)||(null==resourceCredentialExclusion.credentialExceptionId)|| (null!=resourceCredentialExclusion.credentialExceptionId && 1==resourceCredentialExclusion.exceptionTypeId && 1 == resourceCredentialExclusion.expCount)}">
	<tr>
	<td width=35%>
	<c:if test="${(null!=resourceCredentialExclusion.credentialExceptionId && 2==resourceCredentialExclusion.exceptionTypeId)}">
	<div id="exclusionsrs_${spnId}_${status.count}_done" class="credentialsDone_${spnId}">
	<i class="icon-pencil" style="cursor: pointer;" title="Edit this exception"  id="exclusionsrs_${spnId}_${status.count}_img_done"
							onclick="clickEdit(this,'r',${spnId})"/>&nbsp;${resourceCredentialExclusion.credentialType}
							<c:if test="${null!=resourceCredentialExclusion.credentialCategory}">
							 > ${resourceCredentialExclusion.credentialCategory}
							 </c:if>
	</div>
		<input id="excpEdit_rs_${spnId}_${status.count}_init_val" type="hidden" value="edit" />
	</c:if>
	<c:if test="${(null==resourceCredentialExclusion.credentialExceptionId)||(null==resourceCredentialExclusion.credentialExceptionId)|| (null!=resourceCredentialExclusion.credentialExceptionId && 1==resourceCredentialExclusion.exceptionTypeId && 1 == resourceCredentialExclusion.expCount)}">
	<div id="exclusionsrs_${spnId}_${status.count}_edit" class="credentialsEdit_${spnId}">
	<i class="icon-plus" style="cursor: pointer;" title="Add an exception"  id="exclusionsrs_${spnId}_${status.count}_img_edit" 
								 onclick="clickAdd(this,'r',${spnId})" />&nbsp;${resourceCredentialExclusion.credentialType} <c:if test="${null!=resourceCredentialExclusion.credentialCategory}">
							 > ${resourceCredentialExclusion.credentialCategory}
							 </c:if>
	</div>
		<input id="excpEdit_rs_${spnId}_${status.count}_init_val" type="hidden" value="add" />
	</c:if>
	<div id="exclusionsrs_${spnId}_${status.count}_show" class="credentialsShow_${spnId}" style="display: none;">
	&nbsp;${resourceCredentialExclusion.credentialType} <c:if test="${null!=resourceCredentialExclusion.credentialCategory}">
							 > ${resourceCredentialExclusion.credentialCategory}
							 </c:if>
	</div>
	<input id="credentialType_rs_${spnId}_${status.count}_init_val" type="hidden" value="${resourceCredentialExclusion.credentialType}" />
	 <input id="credentialCategory_rs_${spnId}_${status.count}_init_val" type="hidden" value="${resourceCredentialExclusion.credentialCategory}" />
	</td>
	<td width=17%>
	<div id="exclusionsrs_${spnId}_${status.count}_exceptionValuer" class="expValue_${spnId}">
	<c:if test="${null!=resourceCredentialExclusion.credentialExceptionId && 2==resourceCredentialExclusion.exceptionTypeId}">
		${resourceCredentialExclusion.selectedStates}<c:if test="${null != resourceCredentialExclusion.selectedStatesLeft}">
		<span style="border-bottom: 1px dotted; cursor:default;" title="${resourceCredentialExclusion.selectedStatesLeft}">and ${resourceCredentialExclusion.remainingStatesCount} more</span>
		</c:if>
	</c:if>
	<c:if test="${null==resourceCredentialExclusion.credentialExceptionId || 2!=resourceCredentialExclusion.exceptionTypeId}">
	&nbsp;
	</c:if>
	</div>
	<div id="exclusionsrs_${spnId}_${status.count}_exceptionStatesr" style="display: none;" class="expDropDown_${spnId}">
			<div id="expValueDropDownrs_${spnId}_${status.count}" class="pickedExceptions pickedExceptionsClick"
										style="width: 100px; float: left;margin-left: 2px; height:20px; overflow-y:visible;" onclick="showStatesDropDown(${spnId},${status.count},'r')">
										<c:choose>
										<c:when test="${fn:length(resourceCredentialExclusion.selectedStatesValues) > 0}">
											<label id="defaultExpValrs_${spnId}_${status.count}">${fn:length(resourceCredentialExclusion.selectedStatesValues)} selected</label>
										</c:when>
										<c:otherwise><label id="defaultExpValrs_${spnId}_${status.count}">-Select-</label></c:otherwise>
										</c:choose>
									</div>
									<div class="expselect-options" id="selectStatesOptions_r_${spnId}_${status.count}"
										style="display: none;width: 95px; margin-top: 36px;">
										<c:if test="${null != exceptionStates}">
										<c:forEach var="exceptionState" items="${exceptionStates}" varStatus="i">
											<c:set var="val2" value="0"></c:set>
											<c:forEach var="selectedState" items="${resourceCredentialExclusion.selectedStatesValues}">
												<c:if test="${exceptionState == selectedState}">
												<div style="clear:left;padding-top:5px;"> 
													<div style="float: left;"> 
													<input type="checkbox" checked="checked" class="check_r_${spnId}_${status.count}"
													name="resourceCredentialExclusion.selectedStatesValues[${i.count}]" value="${exceptionState}" id="${i.count}"
													onclick="setDefaultStateValue(this,'r')"/>
													</div>
													<div style="float: left;padding-left:3px;">
														${exceptionState}
													 </div>
												</div >
													<c:set var="val2" value="1"></c:set>
												</c:if>
											</c:forEach>
											<c:if test="${val2 == 0}">
											<div style="clear:left;padding-top:5px;">
												<div style="float: left;">
												<input type="checkbox"
													name="resourceCredentialExclusion.selectedStatesValues[${i.count}]" value="${exceptionState}" class="check_r_${spnId}_${status.count}"
													onclick="setDefaultStateValue(this,'r')"/>
												</div>
													<div style="float: left;padding-left:3px;">${exceptionState}<br />
												 </div>
												</div>
											</c:if>
										</c:forEach>
										</c:if>
										<input id="exclusionsrs_${spnId}_${status.count}_value_init_val" type="hidden" value="${resourceCredentialExclusion.selectedStatesValues}" />
									</div>
		</div>
	</td>
	<td width=21% style="text-align: center!important;">
	<div id="exclusionsrs_${spnId}_${status.count}_status_divr" class="expStatus_${spnId}">
	<c:if test="${null!=resourceCredentialExclusion.credentialExceptionId}">
	<c:if test="${null!=resourceCredentialExclusion.credentialExceptionId && 2==resourceCredentialExclusion.exceptionTypeId}">
	<c:if test="${true == resourceCredentialExclusion.activeInd}">
		<img  title="Buyer override active" src="${staticContextPath}/images/common/status-blue.png"
								alt="Active" />&nbsp;Active
	</c:if>
	<c:if test="${false == resourceCredentialExclusion.activeInd}">
	Inactive
	</c:if>
	</c:if>
	<c:if test="${null==resourceCredentialExclusion.credentialExceptionId || 2!=resourceCredentialExclusion.exceptionTypeId}">
	&nbsp;
	</c:if>
	</c:if>
	<c:if test="${null==resourceCredentialExclusion.credentialExceptionId}">
	&nbsp;
	</c:if>
	</div>
	<div class="btn-pill_${spnId}"  id="exclusionsrs_${spnId}_${status.count}_btn_divr" style="display: none;">
	<div class="btn-pill">
				<c:if test="${null!=resourceCredentialExclusion.credentialExceptionId && 2==resourceCredentialExclusion.exceptionTypeId}">
						<c:if test="${true == resourceCredentialExclusion.activeInd}">
						   <input type="radio" value="On" name="on-off-rs-${spnId}-${status.count}" id="on-rs-${spnId}-${status.count}" class="l_${spnId}">
						    <label for="on-rs-${spnId}-${status.count}" class="checked">Active</label> 
						    <input type="radio" value="Off" name="on-off-rs-${spnId}-${status.count}" id="off-rs-${spnId}-${status.count}" class="l_${spnId}">
						    <label for="off-rs-${spnId}-${status.count}">Inactive</label>
												    						 <input id="resource_s_${spnId}_${status.count}_init_val" type="hidden" value="${resourceCredentialExclusion.activeInd}" />
						</c:if>
						<c:if test="${false == resourceCredentialExclusion.activeInd}">
						   <input type="radio" value="On" name="on-off-rs-${spnId}-${status.count}" id="on-rs-${spnId}-${status.count}" class="l_${spnId}">
						    <label for="on-rs-${spnId}-${status.count}">Active</label> 
						    <input type="radio" value="Off" name="on-off-rs-${spnId}-${status.count}" id="off-rs-${spnId}-${status.count}" class="l_${spnId}">
						    <label for="off-rs-${spnId}-${status.count}" class="checked">Inactive</label>
						    						    						 <input id="resource_s_${spnId}_${status.count}_init_val" type="hidden" value="${resourceCredentialExclusion.activeInd}" />
						    
						 </c:if>
						  <c:if test="${true != resourceCredentialExclusion.activeInd && false!= resourceCredentialExclusion.activeInd}">
						   <input type="radio" value="On" name="on-off-rs-${spnId}-${status.count}" id="on-rs-${spnId}-${status.count}" class="l_${spnId}">
						    <label for="on-rs-${status.count}" class="checked">Active</label> 
						    <input type="radio" value="Off" name="on-off-rs-${spnId}-${status.count}" id="off-rs-${spnId}-${status.count}" class="l_${spnId}">
						    <label for="off-rs-${spnId}-${status.count}">Inactive</label>
						    						 <input id="resource_s_${spnId}_${status.count}_init_val" type="hidden" value="true" />
						 </c:if>
				</c:if>
				<c:if test="${null==resourceCredentialExclusion.credentialExceptionId || 2!=resourceCredentialExclusion.exceptionTypeId}">
					<input type="radio" value="On" name="on-off-rs-${spnId}-${status.count}" id="on-rs-${spnId}-${status.count}" class="l_${spnId}">
					<label for="on-rs-${spnId}-${status.count}" class="checked">Active</label> 
					<input type="radio" value="Off" name="on-off-rs-${spnId}-${status.count}" id="off-rs-${spnId}-${status.count}" class="l_${spnId}">
					<label for="off-rs-${spnId}-${status.count}">Inactive</label>
					<input id="resource_s_${spnId}_${status.count}_init_val" type="hidden" value="true" />
				</c:if>
						</div>
	</div>
	</td>
	<td width=50% style="text-align: center!important;">
		<div id="exclusionsrs_${spnId}_${status.count}_modifyDate_divr" class="expUpdated_${spnId}">
		<c:if test="${null!=resourceCredentialExclusion.credentialExceptionId && null!=resourceCredentialExclusion.modifiedDate && 2==resourceCredentialExclusion.exceptionTypeId}">
		<fmt:formatDate value="${resourceCredentialExclusion.modifiedDate}"
									pattern="EEE MM/dd/yy hh:mm aa z" /> 
		</c:if>
		<c:if test="${null==resourceCredentialExclusion.credentialExceptionId || null==resourceCredentialExclusion.modifiedDate || 2!=resourceCredentialExclusion.exceptionTypeId}">
		&nbsp;
		</c:if>
		</div>
		<div id="exclusionsrs_${spnId}_${status.count}_save_divr" style="display:none; padding-left: 90px;" class="expSave_${spnId}">
		<span><c:if test="${null!=resourceCredentialExclusion.credentialCategory}">
		<input id="resourceCredentialSaveButton" class="actionButton" type="button" value="Save" style="border:none;float: left; margin-left: 15px; text-transform: capitalize;" onclick="confirmExceptions(${resourceCredentialExclusion.credentialTypeId},${resourceCredentialExclusion.credentialCategoryId},'resource',${resourceCredentialExclusion.spnId},${status.count})"/>
		</c:if>
		<c:if test="${null==resourceCredentialExclusion.credentialCategory}">
		<input id="resourceCredentialSaveButton" class="actionButton" type="button" value="Save" style="border:none;float: left; margin-left: 15px; text-transform: capitalize;" onclick="confirmExceptions(${resourceCredentialExclusion.credentialTypeId},' ','resource',${resourceCredentialExclusion.spnId},${status.count})"/>
		</c:if>
		</span>
		<span>	<a id="exclusionsrs_${spnId}_${status.count}_cancel_divr" class="cancel" href="#" style="text-decoration: underline;  text-transform: capitalize; float: center; color: grey;" onclick="cancelEdit(${spnId},'rs',${status.count},'resource_s')">Cancel</a>
		</span>			
		</div>
	</td>
	</tr>
	</c:if>
	</c:forEach>
	</table>
	</c:if>
	</div>
</div>
</form>
<div id="confirmExceptionsPopUp_${spnId}" class="jqmWindow" style="overflow: hidden; display: none; -webkit-border-radius:4px;
	-moz-border-radius:4px;
	border-radius:4px;">
					<jsp:include page="exception_confirm_popup.jsp" />

		</div> 
</body>
</html>