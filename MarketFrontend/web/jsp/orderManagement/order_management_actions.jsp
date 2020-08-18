<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>	
	<style type="text/css">
		input {cursor: pointer;}
		select {cursor: pointer;margin-left: 5px;}
		a {cursor: pointer;}
		#assignFirmInfo{padding: 4px;background-color: #FFFFFF;}
		#chooseProvider{padding: 4px;background-color: #FFFFFF;}
		#modalTitleGrey_Reschedule{background:#ddddde;color:#000000 ;font-weight:700;text-align:left;height:25px;font-size: 17px;padding:10px;}
		.reschdule_descr{text-align: justify;;padding-left: 10px;padding-right: 10px;font-weight: bold;padding-top: 4px;}
		.appointmentBox{background-color: #f7fc92;border:1px solid black;text-align: left;margin-left: 8px;margin-right: 5px;margin-top:8px;font-size: 11px;}
		input.rescheduleDateField{width: 180px;height:20px;position: relative;background-image: url('${staticContextPath}/images/icons/date.gif');background-repeat: no-repeat;background-position: right;}
		.rescheduleErrorMsg, .preCallErrMsg {margin: 5px; color: red; text-align: left;}
		.rescheduleErrorMsg ul, .preCallErrMsg ul, {list-style: none outside none; padding-left : 15px;}
		.editNotesResponseMessage{width: 300px; overflow-y: hidden; display: none;margin-top:4px;padding:3px;}
		#requestRescheduleForm table td {border-bottom: none;}
		.acceptSOHdr div {padding: 2px 2px;color:#000000;}
		.arrowAddNote{
    			    border-color: transparent transparent #A8A8A8;
				    border-style: solid;
				    border-width: 25px;
				    height: 0;
				    width:0;
				    left: 80%;
				    margin-left: -10px;
				    position: absolute;
				    top: -55px;
		}
		#addNote table:hover {background: none;}
		#addNote td {border-bottom: none;}
		#requestReschedule input,#requestReschedule select,#requestReschedule option{
			font-size: 12px;
		}
		#requestReschedule table:hover {background: none;}
        .boldClass{font-weight:bold;}
        
   .soDetailsHeader {
    float: left;
    width: 150px;
    height: 75px;
    margin: 10px;
    border-right: 1px solid ;  
}
 
	</style>
	<script language="JavaScript"src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js" type="text/javascript"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/ui.datepicker.js"></script>
	<script type="text/javascript" >
	$(document).ready(function() {
	
	//select All checkbox -- Print Paperwork
		$('#selectAll').click(function(){
			if($('#selectAll').prop('checked')==true){
				$('.selectPdf').prop('checked',true);
				$("#printPaperWork").css("display","block");
			}else{
				$('.selectPdf').prop('checked',false);
				$("#printPaperWork").css("display","none");
			}
		});
		
	

	/*Accept SO Pop up: When service provider is selected*/
	$("#serviceProviders").change(function(){
		$('#chooseProvider').css("background","#FFFFFF");
		var provider = $("#serviceProviders").val();
		if(provider !='0' && provider != 'undefined'){
			$('#chooseProvider').html("The order will be assigned to the provider. You may reassign prior to service start.");
		}else{
			$('#chooseProvider').html('You must Select a Service Provider<font color="red">*</font>');
		}
	});
	
	
	
	//View more functionality
	$(document).on('click', ".viewMoreLink", function(){
	
		var omDisplayTab = getActiveTab();
		//var currentCount = '${currentSOCount}';
		var currentCount = $("#currentOrderCountOnSort").val(); 
		fnWaitForResponseShow();
		var sortOrder  = $("#sortOrderFrm").val();
		var targetTitleId = "hdr"+$("#criteriaFrm").val();
		var criteria  = targetTitleId.substring(3);
		var filterForm = $("#filterForm").serialize();
		$.ajax({
        	url: "loadTabData.action?omDisplayTab="+encodeURIComponent(omDisplayTab)+"&count="+currentCount,
        	type: "POST",
        	data: filterForm,
			dataType : "html",
			success: function(data) {
				$("#orderManagementFiltersAndGrid").html(data);
				//code to make lastName bold if customerInfo is sorted
				if(criteria == 'CustInfo'){
				      $(".lastNameBold").addClass("boldClass");
				 }
				$("table.omscrollerTableHdr .active").removeClass("active");
				$("#"+targetTitleId).parent().addClass("active");
				if(targetTitleId != 'undefined' || targetTitleId !=="" || targetTitleId !== "hdr"){
					$(".icon-sort-up").addClass("icon-sort");
					$(".icon-sort-down").addClass("icon-sort");
					$(".icon-sort").removeClass("icon-sort-up").removeClass("icon-sort-down");
					if(sortOrder == 'asc'){
						$("#"+targetTitleId+" .icon-sort").addClass("icon-sort-up");
						$("#"+targetTitleId+" .icon-sort-up").removeClass("icon-sort");
						$("#"+targetTitleId).prop("sortOrder", "desc");
					}else if(sortOrder == 'desc'){
						$("#"+targetTitleId+" .icon-sort").addClass("icon-sort-down");
						$("#"+targetTitleId+" .icon-sort-down").removeClass("icon-sort");
						$("#"+targetTitleId).prop("sortOrder", "asc");
					}
				}				
				fnWaitForResponseClose();
			},
			error: function(xhr, status, err) {
				location.href ="${contextPath}/homepage.action";
	        }
		});
		
	});
	
	//display of product availability info div 
	$('.productAvailabilityInfoIcon').mouseover(function(){
		$('#productAvailabilityInfo').show();
	});
	$('.productAvailabilityInfoIcon').mouseout(function(){
		$('#productAvailabilityInfo').hide();
	});
	
	//display of ETA tool tip div 
	$('.updateTimeEtaToolTipInfoIcon').mouseover(function(){
		$('#updateTimeEtaToolTip').show();
	});
	$('.updateTimeEtaToolTipInfoIcon').mouseout(function(){
		$('#updateTimeEtaToolTip').hide();
	});
	
	//display of ETA tool tip div 
	$('.preCallEtaToolTipInfoIcon').mouseover(function(){
		$('#preCallEtaToolTip').show();
	});
	$('.preCallEtaToolTipInfoIcon').mouseout(function(){
		$('#preCallEtaToolTip').hide();
	});
	

		
	// To display View More Service Orders Link only if there are more Service Orders Available than those being displayed
		var totalTabCount = '${totalTabCount}';
		var currentCount = '${currentSOCount}';
		var remainingOrders = totalTabCount - currentCount;
		if(remainingOrders>0){
			if($('#viewMoreLink')){
				//$('#viewMoreLink',window.parent.document).css('display','block');
			}
		}else{
			if($('#viewMoreLink')){
			//$('#viewMoreLink',window.parent.document).css('display','none');
			}
		}

	//edit notes validation and submission
	$('#editNotesBtn').click(function(){
		$("#editNotesResponseMessage").html("");
		var slNotesMessage = $('#sl_notes').val();
		var soId = $('#soId').val();
		slNotesMessage = jQuery.trim(slNotesMessage);
		if(slNotesMessage == "" || slNotesMessage == null){
			var messageError = '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.editNotes.message" />';    	
			$("#editNotesResponseMessage").html(messageError);
	      	$("#editNotesResponseMessage").show();
	    }else{
			$.ajax({
	        	url: 'updateSOLocationNotes.action?locnNotes='+encodeURIComponent(slNotesMessage)+'&soId='+soId,
	        	type: "POST",
				dataType : "json",
				success: function(data) {
					if(data.omErrors.length !== 0 ){
						$.each(data.omErrors, function(index,value){
							$("#editNotesResponseMessage").append(value.msg);
						});	
						$("#editNotesResponseMessage").show();
					}else{
						$('#editNotes').jqmHide();
						$("#omSuccessDiv").html("<div  class='successBox'>"+"Successfully updated service order location notes for #"+soId+"</div>");
					}
				},
			error: function(xhr, status, err) {
				location.href ="${contextPath}/homepage.action";
	        }
			});
	    }
	});
	
	//deleting the default text on clicking the textfield
	$('#subject').click(function(){
		var sub = $('#subject').val();
		if(sub === "enter Subject*"){
			$('#subject').val("");
		}
	});
	
	$('#subject').focus(function(){
		var sub = $('#subject').val();
		if(sub === "enter Subject*"){
			$('#subject').val("");
		}
	});
	//to repopulate the default text on focus out
	$('#subject').blur(function(){
		if($('#subject').val()==""){
		 $('#subject').val("enter Subject*");
		}
	});
	
	//deleting the default text on clicking the textfield
	$('#message').focus(function(){
		var msg = $('#message').val();
		if(msg === "enter Message*"){
			$('#message').val("");
		}

	});
	$('#message').click(function(){
		var msg = $('#message').val();
		if(msg === "enter Message*"){
			$('#message').val("");
		}
	});

	$('#message').blur(function(){
		if($('#message').val()==""){
			$('#message').val("enter Message*");
		}
	});

	//Add notes validation and submission
	$('#submitNotesBtn').click(function(){
		var notesSubject = $('#subject').val();
		var notesMessage = $('#message').val();
		$("#addNotesResponseMessage").html("");
      	$("#addNotesResponseMessage").hide();
		var warnMsg = "";
		notesSubject = jQuery.trim(notesSubject);
		notesMessage = jQuery.trim(notesMessage);
		if(notesSubject == "" || notesSubject == null || notesSubject == "enter Subject*"){
	      	warnMsg += "<p>Error: Please enter Subject.</p>";
	    }
		if(notesMessage == "" || notesMessage == null || notesMessage == "enter Message*"){
			warnMsg += "<p>Error: Please enter Message.</p>";
	    }
		if(warnMsg != ""){
			$("#addNotesResponseMessage").append(warnMsg);
	      	$("#addNotesResponseMessage").show();
	      	return false;
		}else{
			var privateNote = false;
			if(document.getElementById('markPrivate').checked == true){
				privateNote = true;
			}
			var soId = $('#soId').val();
			notesSubject = encodeURIComponent(notesSubject);
			notesMessage = encodeURIComponent(notesMessage);
			
			$("#addNotesResponseMessage").hide();
			$.ajax({
	        	url: 'addNotes.action?subject='+notesSubject+'&message='+notesMessage+'&private='+privateNote+'&soId='+soId,
	        	type: "POST",
				dataType : "json",
				success: function(data) {
					if(data.omErrors.length !== 0 ){
						$.each(data.omErrors, function(index,value){
							$("#addNotesResponseMessage").append(value.msg);
						});	
						$("#addNotesResponseMessage").show();
					}else{
						$('#subject').val("");
						$('#message').val("");
						$("#markPrivate").prop('checked',"");
						$("#addNote").jqmHide();
						$("#omSuccessDiv").html("<div  class='successBox'>"+"Note added succesfully for #"+soId+"</div>");
					}
				},
				error: function(xhr, status, err) {
					location.href ="${contextPath}/homepage.action";
		        }
			});
		}
	});

	});
	
		function displaySODetails(soId,groupInd,resId){
			if(groupInd == '0'){
				window.location.href="${contextPath}/soDetailsController.action?soId="+soId+"&resId="+resId+"&displayTab=Summary&cameFromOrderManagement=cameFromOrderManagement";
			}else{
				window.location.href="${contextPath}/soDetailsController.action?groupId="+soId+"&resId="+resId+"&displayTab=Summary&cameFromOrderManagement=cameFromOrderManagement";
			}
		}
		
		

		function modalOpenAddNote(dialog) {
			$('.modalOverlay').remove();
			dialog.container.fadeIn('fast', function() {
				dialog.data.hide().slideDown('slow');
			});
		}

		function modalOpenAddCustomer(dialog) {
			dialog.overlay.css({
				height : "205%",
				position : "absolute"
			});
			dialog.overlay.fadeIn('fast', function() {
				dialog.container.fadeIn('fast', function() {
					dialog.data.hide().slideDown('slow');
				});
			});
		}
		// This function is to perform close operation of the pop-up page. 
		function modalOnClose(dialog) {
			dialog.data.fadeOut('slow', function() {
				dialog.container.slideUp('slow', function() {
					dialog.overlay.fadeOut('slow', function() {
						$.modal.close();
					});
				});
			});
		}

		//to display the add note pop up
		function loadAddNote(e, soId) {
			var isButton = false;
			var click = $("#addNoteLink_"+soId);
			if($("#addNoteLink_"+soId).hasClass("action")){
				isButton = true;
			}else{
				click = $("#addNoteLink_"+soId).parent().parent();
			}
			$("#addNotesResponseMessage").hide();
			var y = e.pageY; 
			var offs = click.offset();
			try{
				y = offs.top;
			}catch (e) {
				y = e.offsetY;
			}
			$("#subject").val("enter Subject*");
			$("#message").val("enter Message*");
			$("#markPrivate").prop('checked',"");
			$("#addNote").addClass("jqmWindow");
			$("#addNote").css("width", "400px");
			$("#addNote").css("height", "auto");
			$("#addNote").css("position", "absolute");
			$("#addNote").css("marginLeft", "75px");
			$("#addNote .arrowAddNote").css("border-color","transparent");
			$("#addNote .arrowAddNote").css("border-bottom-color","#A8A8A8");
			$("#addNote .arrowAddNote").css({"left": "80%","margin-left":"-10px","top":"-55px"});
			var ht = $("body").height();
			var diff = ht-y;
			if(diff<=265){
				$("#addNote").css({"top" : ht-462,"marginLeft":"-15px"});
				$("#addNote .arrowAddNote").css("border-color","transparent");
				$("#addNote .arrowAddNote").css("border-left-color","#A8A8A8");
				// Assuming the height of the widget is 253 
				$("#addNote .arrowAddNote").css({"left": "100%","margin-left":"5px","top":253-diff});
			}else if(isButton){
				$("#addNote").css("top", y - 130);
			}else{
				$("#addNote").css("top", y - 156);
			}
			$("#addNote").jqm({
				modal : true,
				overlay : 0
			});
			$("#soId").val(soId);
			$("#addNote").jqmShow();
		}

		//to display the reschedule pop up
	function loadRequestaReshedule(e,soId){
		fnWaitForResponseShow();
			var click = $("#rescheduleLink_"+soId).parent().parent();
			var offs = click.offset();
			var y = e.pageY;
			try{
				y = offs.top;
			}catch (e) {
				y = e.offsetY;
			}
			$("#rescheduleRequest").load("loadDataForRequestReschedule.action?soId="+soId, function() {
				fnWaitForResponseClose();
				$("#rescheduleRequest").addClass("jqmWindow");
	   			$("#rescheduleRequest").css("width", "500px");
	   			$("#rescheduleRequest").css("height", "auto");
	   			$("#rescheduleRequest").css("marginLeft", "75px");
	   			$("#rescheduleRequest .arrowAddNote").css("border-color","transparent");
				$("#rescheduleRequest .arrowAddNote").css("border-bottom-color","#A8A8A8");
				$("#rescheduleRequest .arrowAddNote").css({"left": "80%","margin-left":"-10px","top":"-55px"});
	   			var ht = $("body").height();
				var diff = ht-y;
				if(diff<=500){
					$("#rescheduleRequest").css({"top":ht-765,"marginLeft":"-15px"});
					$("#rescheduleRequest .arrowAddNote").css("border-color","transparent");
					$("#rescheduleRequest .arrowAddNote").css("border-left-color","#A8A8A8");
					// Assuming the height of the widget is 500
					$("#rescheduleRequest .arrowAddNote").css({"left": "100%","margin-left":"0px","top":560-diff});
				}else{
					$("#rescheduleRequest").css("top", y-150);
				}
	   			$("#rescheduleRequest").jqm({modal:true,overlay:0});
				$("#rescheduleRequest").css("border-left-color","#A8A8A8");
	   			$("#rescheduleRequest").css("border-right-color","#A8A8A8");
	   			$("#rescheduleRequest").css("border-bottom-color","#A8A8A8");
	   			$("#rescheduleRequest").css("border-top-color","#A8A8A8");
	   			$("#rescheduleSoId").val(soId);
	   			$("#rescheduleRequest").jqmShow();
			});
		}

		//to display the edit location notes pop up
		function loadEditServiceLocationNotes(e,soId) {
			var y = e.pageY;
			var click = $("#editLocnNotesLink_"+soId).parent().parent();
			var offs = click.offset();
			try{
				y = offs.top;
			}catch (e) {
				y = e.offsetY;
			}
			fnWaitForResponseShow();
			$('#editNote').load("loadDataForEditNotes.action?soId=" + soId,
					function() {
						$("#editNotesResponseMessage").hide();
						fnWaitForResponseClose();
						var notes = jQuery("#noteList").html();
						$('#sl_notes').val(notes);
						$('#soId').val(soId);
						$("#editNotes").addClass("jqmWindow");
						$("#editNotes").css("width", "400px");
						$("#editNotes").css("height", "auto");
						$("#editNotes").css("marginLeft", "75px");
						$("#editNotes .arrowAddNote").css("border-color","transparent");
						$("#editNotes .arrowAddNote").css("border-bottom-color","#A8A8A8");
						$("#editNotes .arrowAddNote").css({"left": "80%","margin-left":"-10px","top":"-55px"});
						var ht = $("body").height();
						var diff = ht-y;
						if(diff<=265){
							$("#editNotes").css({"top":ht-462,"margin-left":"0px"});
							$("#editNotes .arrowAddNote").css("border-color","transparent");
							$("#editNotes .arrowAddNote").css("border-left-color","#A8A8A8");
							$("#editNotes .arrowAddNote").css({"left": "100%","margin-left":"0px","top":253-diff});
						}else{
							$("#editNotes").css("top", y - 150);
						}
						
						$("#editNotes").jqm({
							modal : true,
							overlay : 0
						});
						$("#editNotes").fadeIn('slow');
						$('#editNotes').css('display', 'block');
						$("#editNotes").css("border-left-color", "#A8A8A8");
						$("#editNotes").css("border-right-color", "#A8A8A8");
						$("#editNotes").css("border-bottom-color", "#A8A8A8");
						$("#editNotes").css("border-top-color", "#A8A8A8");
						$("#editNotes").jqmShow();
					});
		}

		//to change the color of priority flag on click
		function setSoPriority(clickedId, soId, groupInd) {
			fnWaitForResponseShow();
			//var currentStatus = $('#'+clickedId).prop("src");
			$('#priority').load('updateSOPriority.action?soId=' + soId + "&groupInd=" + groupInd,
					function() {
						fnWaitForResponseClose();
						if ($('#' + clickedId).hasClass('on')) {
							$('#' + clickedId).removeClass('on');
							$('#' + clickedId).addClass('off');
						} else {
							$('#' + clickedId).removeClass('off');
							$('#' + clickedId).addClass('on');
						}
			/*if(currentStatus.indexOf('white') >= 0){
				$('#'+clickedId).prop("src", '${staticContextPath}/images/order_management/flag-red.JPG');
			}else{
				$('#'+clickedId).prop("src", '${staticContextPath}/images/order_management/flag-white.JPG');
			}*/	
		});		
	}
	
    function showRescheduleRangeDate() {
    	$("#rescheduleErrorMsg").html("");
    	$("#rescheduleErrorMsg").removeClass("errorBox");
    	$("#rescheduleDateRanged").show();
    	$("#serviceTimePrecall").hide();
    	$("#serviceWinPrecall").show();
    	
    	
	}


		function showRescheduleFixedDate() {
			$("#rescheduleErrorMsg").html("");
			$("#rescheduleErrorMsg").removeClass("errorBox");
			$("#rescheduleDateRanged").hide();
			//document.getElementById('rescheduleDateRanged').style.display="none";
			$("#serviceWinPrecall").hide();
	    	$("#serviceTimePrecall").show();
		}
		


		//to display the Accept Order pop up
		function loadAcceptOrder(soId, groupInd) {
			fnWaitForResponseShow();
			$("#assignOrder").load("displayAcceptSOPopUp.action?soId=" +soId+"&groupInd="+groupInd, 
				function(data) {
					$('#loadTimerDiv').hide();
					$("#acceptOrder").hide();
					var routeMethod = $("#routeMethod").val();
					if('true' == routeMethod){
     					$("#acceptOrder").show();
     				}else{
     					jQuery('#loadTimerDiv').load("loadTimer.action?assignee=typeFirm&soId="+soId+"&groupInd="+groupInd, 
     						function(){	
     				    		loadTimer();	
     							$('#loadTimerDiv').show(); 
     					});	
     				}
								fnWaitForResponseClose();
								$("#accept_so_id").val(soId);
								$("#groupInd").val(groupInd);
								$("#assignOrder").addClass("jqmWindow");
								$("#assignOrder").css("width", "515px");
								$("#assignOrder").css("height", "auto");
								$("#assignOrder").css("marginLeft", "-300px");
								$("#assignOrder").css("top", "-50px");
								$("#assignOrder").jqm({
									modal : true
								});
								$("#assignOrder").fadeIn('slow');
								$('#assignOrder').css('display', 'block');
								$("#assignOrder").jqmShow();
				});
		}
		//SL-21645 
		function displayAddEstimateSOPopUp(soId,estimationId){
			fnWaitForResponseShow();
			
			$("#addEstimate").load("displayAddEstimateSOPopUp.action?soId=" +soId+"&estimationId="+estimationId+"&sodPage=false" , 
					function(data) {
						
						
									fnWaitForResponseClose();
								/* 	$("#accept_so_id").val(soId);
									$("#groupInd").val(groupInd); */
									$("#addEstimate").addClass("jqmWindow");
									$("#addEstimate").css("width", "850px");
									$("#addEstimate").css("height", "auto");
									$("#addEstimate").css("marginLeft", "-450px");
									$("#addEstimate").css("top", "-50px");
									$("#addEstimate").jqm({
										modal : true
									});
									$("#addEstimate").fadeIn('slow');
									$('#addEstimate').css('display', 'block');
									$("#addEstimate").jqmShow();
					});
		}

		//Accept SO Popup: fn to display the next actions to be performed when user selects a radio button
		function fnShowNext() {
			var checkedOption = $("#assignOrder input[type='radio']:checked").val();
			if (checkedOption == '0') {
				var msg = '<fmt:message bundle="${serviceliveCopyBundle}" key="ordermanagement.so.acceptSO.message" />';
				$('#assignFirmInfo').html("<b>" + msg + "</b>");
				$('#assignFirmInfo').css("background", "#DCA9B9");
				$('#selectProviderName').hide();
			} else {
				var msgAssignPro = '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.acceptSO.assignProvider" />';
				$('#chooseProvider').html(msgAssignPro + '<font color="red">*</font>');
				$('#assignFirmInfo').html("You must assign the Service Order<font color='red'>*</font>");
				$('#assignFirmInfo').css("background", "#FFFFFF");
				$('#chooseProvider').css("background", "#FFFFFF");
				$("#serviceProviders").val(0);
				$('#selectProviderName').show();
			}
		}

		//submission of accept order
		function submitAcceptOrder() {
			$("#acceptSOResponseMessage").html("");
			$("#acceptSOResponseMessage").hide();
			var checkedOption = $("#assignOrder input[type='radio']:checked").val();
			var soId = $("#accept_so_id").val();
			if (checkedOption == '0') {
				acceptServiceOrder(soId, "");
			} else if (checkedOption == '1') {
				var provider = $("#serviceProviders").val();
				if (provider == '0' || provider == 'undefined') {
					$('#chooseProvider').css("background", "#DCA9B9");
				} else {
					$('#chooseProvider').css("background", "#FFFFFF");
					acceptServiceOrder(soId, provider);
				}
			} else {
				$('#assignFirmInfo').css("background", "#DCA9B9");
			}
		}

		function submitAcceptSO() {
			$('#acceptSOResponseMessage').html("");
			$('#acceptSOResponseMessage').hide();
			var soId = $("#accept_so_id").val();
			if($('#assignToProvider').is(':checked')){
				var provider = $("#serviceProviders").val();
				if (provider == '0' || provider == 'undefined') {
					$('#acceptSOResponseMessage').html("<p>Please select a provider.</p>");
					$('#acceptSOResponseMessage').show();
				} else {
					$('#chooseProvider').css("background", "#FFFFFF");
					acceptServiceOrder(soId, provider);
				}
			}else{
				acceptServiceOrder(soId, "");
			}
		}
		
		function acceptServiceOrder(soId, provider) {
			var groupInd = $("#groupInd").val();
			if(groupInd !== '1' || groupInd != 1){
				groupInd = 'false';
			}
			$.ajax({
				url : 'acceptServiceOrder.action',
				type : "POST",
				data : {
					soId : soId,
					provider : provider,
					groupInd : groupInd
				},
				dataType : "json",
				success : function(data) {
					if (data.omErrors.length !== 0) {
						$.each(data.omErrors, function(index, value) {
							$("#acceptSOResponseMessage").append(value.msg);
						});
						$("#acceptSOResponseMessage").show();
					} else {
						$("#assignOrder").jqmHide();
						$('.icon-repeat').trigger('click');
						$("#omSuccessDiv").html("<div  class='successBox'><p>"+$ESAPI.encoder().encodeForHTML(data.result)+"</p></div>");
						$("#omSuccessDiv").show();
					}
				},
				error: function(xhr, status, err) {
					location.href ="${contextPath}/homepage.action";
		        }
			});
		}

		//to display the Assign Provider pop up
		function loadAssignProvider(e, id, soId) {
		
			// Check if reassing or not
			// SL-18646 - UAT defect
			// Why ? There are some places where the 
			// so_id is appended to reassignProviderLink (id of the re-assing link) 
		
			var reassign = false;
			if (id.indexOf('reassignProviderLink') !==-1) {
				reassign = true;
			}else{
				reassign = false;
			}
			fnWaitForResponseShow();
			$("#assignProviderDiv").load("loadAssignProviderWidget.action?soId=" + soId
							+ "&reassign=" + reassign,
					function(data) {
						fnWaitForResponseClose();
						$("#assignProviderDiv").addClass("jqmWindow");
						$("#assignProviderDiv").css("height", "auto");
						$("#assignProviderDiv").css("marginLeft", "-27%");
						$("#assignProviderDiv").css("top", "-40px");
						$("#assignProviderDiv").css("zIndex", 1000);
						$("#assignProviderDiv").jqm({
							modal : true
						});
						$("#assignProviderDiv").fadeIn('slow');
						$('#assignProviderDiv').css('display', 'block');
						$("#assignProviderDiv").jqmShow();

					});
		}

		//to submit the Assign Service Provider pop up
		function assignServiceProvider(soId, reassign) {
			$('#assignResponseMessage').html("");
			$('#assignResponseMessage').hide();
			var selectedProId = $('#selectProvider option:selected').val();
			if (selectedProId == '0') {
				$('#assignResponseMessage').html("Please select a Provider from the list below.");
				$('#assignResponseMessage').show();
			} else {
				$('#assignResponseMessage').html("");
				$("#assignResponseMessage").hide();
				$.ajax({
		        	url: 'assignProvider.action?soId=' + soId + '&provider='
					+ selectedProId + '&reassign=' + reassign,
		        	type: "POST",
					dataType : "json",
					success: function(data) {
							if (data.omErrors.length !== 0) {
								$.each(data.omErrors, function(index, value) {
									$("#assignResponseMessage").append(value.msg);
								});
								$("#assignResponseMessage").show();
							} else {
								$("#assignProviderDiv").jqmHide();
								$('.icon-repeat').trigger('click');
								$("#omSuccessDiv").html("<div  class='successBox'><p>"+$ESAPI.encoder().encodeForHTML(data.result)+"</p></div>");
								$("#omSuccessDiv").show();
							}
					},
					error: function(xhr, status, err) {
						location.href ="${contextPath}/homepage.action";
			        }
				});
				
			/*	$('#priority').load('assignProvider.action?soId=' + soId + '&provider='
								+ selectedProId + '&reassign=' + reassign,
						function() {
							reLoadAfterAssignProvider();
							$('#assignProviderDiv').jqmHide();
						}); */
			}
		}

		//to display the Update Time window pop up
		function loadUpdateTime(soId, groupInd) {
			/*  $("#updateTime").modal({      
			     onOpen: modalOpenAddCustomer,
			     onClose: modalOnClose,
					persist: false,
			     containerCss: ({ width: "600px", height: "auto", marginLeft: "-300px", top: "200px" })
			 }); */
			fnWaitForResponseShow();
			$("#updateApptTime").load("loadUpdateTimeWidget.action?soId=" + soId + "&groupInd=" + groupInd, function(data) {
				fnWaitForResponseClose();
				$("#updateApptTime").addClass("jqmWindow");
				$("#updateApptTime").css("width", "500px");
				$("#updateApptTime").css("height", "400px");
				$("#updateApptTime").css("marginLeft", "-370px");
				$("#updateApptTime").css("top", "-80px");
				$("#updateApptTime").css("zIndex", 1900);
				$("#updateApptTime").jqm({
					modal : true
				});
				$("#updateApptTime").fadeIn('slow');
				$('#updateApptTime').css('display', 'block');
				$("#updateApptTime").css("background-color", "#FFFFFF");
				$("#updateApptTime").css("border-left-color", "#A8A8A8");
				$("#updateApptTime").css("border-right-color", "#A8A8A8");
				$("#updateApptTime").css("border-bottom-color", "#A8A8A8");
				$("#updateApptTime").css("border-top-color", "#A8A8A8");
				$("#updateApptTime").jqmShow();
			});
	}
	
		//to save updated time
		function saveUpdateTime(soId){
			var error = "";		
			$("#updateTimeResponseMessage").html("");
      		$("#updateTimeResponseMessage").hide();
			var startTime = jQuery('#apptTime1').val();
			var endTime = jQuery('#apptTime2').val();
			var eta = jQuery('#eta').val();
			var confirmInd = jQuery('#confirmUpdateTime').prop('checked');
			var zone = jQuery('#zone').text();
			var startDate = jQuery('#startDate').text();
			var endDate = jQuery('#endDate').text();
			if(null == startTime || undefined == startTime || '0' == startTime){
				error = '<p>Please select a start time</p>';
			}
			var scheduleType = $("#scheduleType").prop("value");
			if(scheduleType == 'range'){
				if(null == endTime || undefined == endTime || '0' == endTime || '-1' == endTime){
					error = error + '<p>Please select an end time</p>';
				}
			}
			if(error != ""){
				$("#updateTimeResponseMessage").append(error);
	      		$("#updateTimeResponseMessage").show();
	      		return false;
			}else{
				if('0' == endTime ||'-1' == endTime || undefined == endTime){
					endTime = "";
					endDate = "";
				}
				if('0' == eta || undefined == eta){
					eta = "";
				}
				/*jQuery('#timeUpdate').load('updateTime.action?soId='+soId+'&startTime='+encodeURIComponent(startTime)+
					'&endTime='+encodeURIComponent(endTime)+'&eta='+encodeURIComponent(eta)+'&confirmInd='+confirmInd,
					 function(){
					 closeModal('updateApptTime');
					 
				});*/
				$.ajax({
		        	url: 'updateTime.action?soId='+soId+'&startTime='+encodeURIComponent(startTime)+
					'&endTime='+encodeURIComponent(endTime)+'&eta='+encodeURIComponent(eta)+'&confirmInd='+confirmInd+
					'&zone='+zone+'&startDate='+startDate+'&endDate='+endDate,
		        	type: "POST",
					dataType : "json",
					success: function(data) {
							if (data.omErrors.length !== 0) {
								$.each(data.omErrors, function(index, value) {
									$("#updateTimeResponseMessage").append(value.msg);
								});
								$("#updateTimeResponseMessage").show();
							} else {
								$("#updateApptTime").jqmHide();
								$('.icon-repeat').trigger('click');
								$("#omSuccessDiv").html("<div  class='successBox'><p>"+$ESAPI.encoder().encodeForHTML(data.result)+"</p></div>");
								$("#omSuccessDiv").show();
							}
					},
					error: function(xhr, status, err) {
						location.href ="${contextPath}/homepage.action";
			        }
				});
			 }
		}
	
	//to display the View PDF pop up
	
		$('#viewPDF', window.parent.document)
				.click(
						function() {
	
		 var checkedSoIds=[];
		 var checkedSoIdsCount;
			// actual code
			 var checkedSoId;
		       $('.selectPdf:checked').each(function() {
		        	checkedSoId = this.value;
		            checkedSoIds.push(checkedSoId);
		       });
		
		 checkedSoIdsCount = checkedSoIds.length;
		 var msg;

		
		 if(checkedSoIdsCount >=1){
        
		 $('#omErrorDiv').hide();
		 var maxCount=0;
		    maxCount= '<fmt:message bundle="${serviceliveCopyBundle}" key="ordermanagement.so.printpaperwork.Print.count" />';
		 $("#printPaperWorkDiv").load("loadPrintPaperWorkPopUp.action?checkedSoIdsCount="+checkedSoIdsCount+"&maxPermissibleCount="+maxCount,function(data) {
				
	   			/*  $("#assignPro").modal({      
		                onOpen: modalOpenAddCustomer,
		                onClose: modalOnClose,
						persist: false,
		                containerCss: ({ width: "350px", height: "auto", marginLeft: "-150px;", top: top })
	   			 }); */
	   			 
			 
				$("#printPaperWorkDiv").addClass("jqmWindow");
				$("#printPaperWorkDiv").css("width", "300px");
				$("#printPaperWorkDiv").css("height", "auto");
				$("#printPaperWorkDiv").css("marginLeft", "-150px");
				$("#printPaperWorkDiv").css("top", "0px");
				$("#printPaperWorkDiv").jqm({modal:true});
				$("#printPaperWorkDiv").fadeIn('slow');
				$('#printPaperWorkDiv').css('display','block');
				$("#printPaperWorkDiv").jqmShow();
		        

		        msg = '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.printpaperwork.Print" />';
		        if(checkedSoIdsCount>maxCount){
			    	 $('#printPaperworkCountMessage').html(msg);
		        	 $('#printPaperworkCountMessage').show();
		        	 $('#printPaperworkDropDown').show();
		        	 document.getElementById('viewPDFs').style.padding="10px";

		        	 document.getElementById('viewPDFs').style.paddingLeft="150px"; 
				} 
			    else {
		       	 $('#printPaperworkCountMessage').hide();

		       	 $('#printPaperworkDropDown').hide();
		       	document.getElementById('viewPDFs').style.paddingLeft="95px"; 
			    }
				$("#checkedSos").prop('value',checkedSoIds);
			});
	    
		} else {
		     msg = '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.printpaperwork.Print.so" />';
			 $('#omErrorDiv').html(msg);
        	 $('#omErrorDiv').show();
		 }
	 });
	

	
	//fn to open provider profile ina new window
	function openProviderProfile(resouceId,vendorId){
			var url = "/MarketFrontend/providerProfileInfoAction_execute.action?resourceId="
					+ resouceId + "&companyId=" + vendorId + "&popup=true";
			newwindow = window
					.open(url, '_publicproviderprofile',
							'resizable=yes,scrollbars=yes,status=no,height=700,width=1000');
		if (window.focus) {
			newwindow.focus();
			}
		return false;
		
	}
	
	//fn to open terms and conditions in a new window
	function openTermsAndConditions(){
		var url =  "/MarketFrontend/termsAndConditions_displayBuyerAgreement.action?paramTermsandCond=true";
			newwindow = window.open(url, 'terms',
					'width=1040,height=640,scrollbars,resizable');
		if (window.focus) {
			newwindow.focus();
			}
	}
	
	//fn to direct the user to place bid page
	function loadPlaceBid(soId,resId){
		window.location.href = "${contextPath}/soDetailsController.action?soId="
			+ soId +"&resId="+resId+"&defaultTab=Summary&cameFromOrderManagement=cameFromOrderManagement";
	}
	
	function closeConfirmCall(){
			$("#confirmCallDiv").jqmHide();
	}
	//Load data for precall popup
	function loadPreCall(soId) {
		fnWaitForResponseShow();
		$("#preCallModal").load("omLoadPreCall.action?soId="+soId+"&source=PreCall", function(data) {
			fnWaitForResponseClose();
			$("#preCallModal").addClass("jqmWindow");
			$("#preCallModal").css("border", "none");
			$("#preCallModal").css("width", "600px");
			$("#preCallModal").css("height", "auto");
			$("#preCallModal").css("marginLeft", "-280px");
			$("#preCallModal").css("top", "10px");
			$("#preCallModal").jqm({
				modal : true
			});
			$("#preCallModal").fadeIn('slow');
			$("#preCallModal").jqmShow();
			$('#notAvailableReasonDiv').css('display','none');
            $('#apptDetails').css('display','none'); 
            $('#submitSectionPreCall').css('display','none');
            $('#availableInfo').css('display','none');
            $('#availableDiv').css('display','none');
            $("#errorDiv").css("display","none"); 
            $("#rescheduleSpecificDataAndTimeDiv").css("display","block");
	        $("#rescheduleDateRangeDiv").css("display","none");
	        $("#source").prop("value","PreCall");

	        $("#title").html("Pre Call - Call the customer to confirm the service details");
	        $(".accordionContent").hide();
            $('#confirmScheduleDiv').hide();
            
            $('#confirmOrderDiv').show();
			var todayInd = $("#today").prop("value");
			var resourceAssignedInd=$("#resourceAvailableInd").prop("value");
			if(todayInd=='true'){
				if(resourceAssignedInd == 'true')
				{	
					$("#reminderAppointmentToday_schedule").css("display","none");
					$("#etaWarningAppointmentToday_schedule").css("display","block");
					$("#etaWarningAppointmentToday_schedule div#informationWindowHdr").css("display","none");
				}
				else
					{
					$("#reminderAppointmentToday_schedule").css("display","block");
					$("#reminderAppointmentToday").css("display","block");
			        $("#reminderAppointmentNotToday").css("display","none");
			        $("#reminderAppointmentNotTodayConfirmAppt").css("display","none");
					}
			    
			}
			else{
		        $("#reminderAppointmentToday").css("display","none");
		        $("#reminderAppointmentNotToday").css("display","block");
		        $("#reminderAppointmentNotTodayConfirmAppt").css("display","none");
			}
    		$("#confirmOrderErrDiv").hide();
    		$("#confirmServiceErrDiv").hide();
    		$("#confirmScheduleErrDiv").hide();
	   		$('#assignProvidersDiv').css("display","none");
  			$("#custResponseDropdownDiv").css("display","block");
 			$("#timeWindowDropdown").prop("value","-1");
 			$("#custNotAvailableReasonDropdown").prop("value",-1);
 			$("#etaDropdown").prop("value",-1);
			$("#splInstrDiv").css("display","none");

$("#reminderAppointmentToday_schedule").css("display","none");
		});
	}
	//Load data for confirm appointment popup
   	//function loadConfirmAppointment(soId) {
    // 	alert("loadConfirmAppointment : " + soId )	;
	//	$("#preCallModal").load("omLoadConfirmAppointment.action?soId="+soId, function(data) {
	//		$("#preCallModal").addClass("jqmWindow");
	//		$("#preCallModal").css("width", "600px");
	//		$("#preCallModal").css("height", "auto");
	//		$("#preCallModal").css("marginLeft", "-280px");
	//		$("#preCallModal").css("top", "10px");
	//		$("#preCallModal").jqm({
	//			modal : true
	//		});
	//		$("#preCallModal").fadeIn('slow');
	//		$("#preCallModal").jqmShow();
	//		$('#notAvailableReasonDiv').css('display','none');
    //       $('#apptDetails').css('display','none'); 
    //       $('#submitSectionPreCall').css('display','none');
    //        $('#availableInfo').css('display','none');
    //        $('#availableDiv').css('display','none');
    //        $("#errorDiv").css("display","none");
    //        $("#rescheduleSpecificDataAndTimeDiv").css("display","block");
	//        $("#rescheduleDateRangeDiv").css("display","none");
	//	});
	//}
	
	//Load data for confirm appointment popup
   	function loadConfirmAppointment(soId) {
		fnWaitForResponseShow();
		$("#preCallModal").load("omLoadPreCall.action?soId="+soId+"&source=ConfirmAppointment", function(data) {
			fnWaitForResponseClose();
			$("#preCallModal").addClass("jqmWindow");
			$("#preCallModal").css("border", "none");
			$("#preCallModal").css("width", "600px");
			$("#preCallModal").css("height", "auto");
			$("#preCallModal").css("marginLeft", "-280px");
			$("#preCallModal").css("top", "10px");
			$("#preCallModal").jqm({
				modal : true
			});
			$("#preCallModal").fadeIn('slow');
			$("#preCallModal").jqmShow();
			$('#notAvailableReasonDiv').css('display','none');
            $('#apptDetails').css('display','none'); 
            $('#submitSectionPreCall').css('display','none');
            $('#availableInfo').css('display','none');
            $('#availableDiv').css('display','none');
            $("#errorDiv").css("display","none");
            $("#rescheduleSpecificDataAndTimeDiv").css("display","block");
	        $("#rescheduleDateRangeDiv").css("display","none");
	        $("#source").prop("value","ConfirmAppointment");
	        var todayInd = $("#today").prop("value");
	        var resourceAssignedInd=$("#resourceAvailableInd").prop("value");
			if(todayInd=='true'){
				if(resourceAssignedInd == 'true')
				{	
					$("#reminderAppointmentToday_schedule").css("display","none");
					$("#etaWarningAppointmentToday_schedule").css("display","block");
					$("#etaWarningAppointmentToday_schedule div#informationWindowHdr").css("display","none");
				}
				else
					{
					$("#reminderAppointmentToday_schedule").css("display","block");
					$("#reminderAppointmentToday").css("display","block");
			        $("#reminderAppointmentNotTodayConfirmAppt").css("display","none");
			        $("#reminderAppointmentNotToday").css("display","none");
					}
			}
			else{
	        $("#reminderAppointmentToday").css("display","none");
				 $("#reminderAppointmentToday_schedule").css("display","none");
		        $("#reminderAppointmentNotTodayConfirmAppt").css("display","block");
		        $("#reminderAppointmentNotToday").css("display","none");
			}
			$("#title").html("Confirm Appointment Window - Call the customer");
	        $(".accordionContent").hide();
            $('#confirmScheduleDiv').hide();
            $('#confirmOrderDiv').show();

    		$("#confirmOrderErrDiv").show();
    		$("#confirmServiceErrDiv").hide();
    		$("#confirmScheduleErrDiv").hide();
 			$('#assignProvidersDiv').css("display","none");
  			$("#custResponseDropdownDiv").css("display","block");
 			$("#timeWindowDropdown").prop("value","-1");
 			$("#custNotAvailableReasonDropdown").prop("value",-1);
 			$("#etaDropdown").prop("value",-1);
			$("#splInstrDiv").css("display","none");
		});
	}
	
	function loadConfirmCall(e){
			$("#confirmCallDiv").addClass("jqmWindow");
			$("#confirmCallDiv").css("width", "600px");
			$("#confirmCallDiv").css("height", "auto");
			$("#confirmCallDiv").css("marginLeft", "-280px");
			$("#confirmCallDiv").css("top", "10px");
			$("#confirmCallDiv").jqm({
				modal : true
			});
			$("#confirmCallDiv").fadeIn('slow');
			$('#confirmCust').css('display','block');
			$("#confirmCallDiv").jqmShow();
			$('#notAvailableReasonDivConfirm').css('display','none');
            $('#submitSectionConfirmCall').css('display','none');
            $('#apptDetailsConfirm').css('display','none');
            $('#confirmAvailableInfo').css('display','none');
            $('#availableDivConfirm').css('display','none');
            $("input:radio").removeAttr("checked");
            $("#confirmErrorDiv").css("display","none");
	}
	
	//to display the Reject Service Order pop up
	 function loadRejectOrder(soId, groupInd,bidInd,resourceId){
			fnWaitForResponseShow();
	 	
			$("#rejectOrder").load("omDisplayRejectSo.action?soId=" + soId
					+ "&groupInd=" + groupInd, function(data) {
			fnWaitForResponseClose();
			$("#rejectOrder").addClass("jqmWindow");
			$("#rejectOrder").css("width", "580px");
			$("#rejectOrder").css("height", "auto");
			$("#rejectOrder").css("marginLeft", "-350px");
			$("#rejectOrder").css("top", "-50px");
			$("#rejectOrder").jqm({
									modal : true
								});
			$("#rejectOrder").fadeIn('slow');
			$('#rejectOrder').css('display','block');
			$("#rejectOrder").css("border-left-color","#A8A8A8");
			$("#rejectOrder").css("border-right-color","#A8A8A8");
			$("#rejectOrder").css("border-bottom-color","#A8A8A8");
			$("#rejectOrder").css("border-top-color","#A8A8A8");
			$("#groupInd").val(groupInd);
			//SL-19976
			if('1' == groupInd){
				$("#rejectGrpMsg").show();
			}else{
				$("#rejectGrpMsg").hide();
			}
			if(bidInd==1){
				$("#resourceId").prop("value",resourceId);
				$("#bidInd").prop("value",bidInd);
				$('#notBid1').css('display','none');
				$('#notBid2').css('display','none');
				$('#bid').css('display','block');
			}
			else{
				$('#notBid1').css('display','block');
				$('#notBid2').css('display','block');
				$('#bid').css('display','none');
				$("#bidInd").prop("value",0);
			
			}
			$("#rejectOrder").jqmShow();
			});
	}
	
	function checkAll(so_id){
         
			var rejResources = document.getElementsByName(so_id
					+ "_reject_resource");
		 var totalCount = rejResources.length;	
			var displayedPage = document
					.getElementById(so_id + "displayedPage").value;
		 var limit = displayedPage*9;
			if (limit >= totalCount) {
				limit = totalCount;
			}
			for(var i = 0; i < limit; i++){
				rejResources[i].checked = true;
			}
          }
          
	 	function clearAll(so_id){
			var rejResources = document.getElementsByName(so_id
					+ "_reject_resource");
         var totalCount = rejResources.length;	
			var displayedPage = document
					.getElementById(so_id + "displayedPage").value;
		 var limit = displayedPage*9;
			if (limit >= totalCount) {
				limit = totalCount;
			}
		 for(var i = 0; i < limit; i++){
			rejResources[i].checked = false;
		 }         	   
        }
        
	 
	//to display date picker
	
	$("#fromDate").click(function(){
			$(this).datepicker({
				minDate : new Date(2008, 12 - 1, 1),
				dateFormat : 'mm/dd/yy',
				yearRange : '-50:+50',
				numberOfMonths : 3
			});
			$(this).focus();
		    });
		$("#toDate").click(function(){
		    var fromDate = new Date();
		    if($("#fromDate").val() != "") {
		    	fromDate = $("#fromDate").val();
   			    fromDate = new Date(fromDate);
		    }
		    	
			$(this).datepicker({
				minDate : fromDate,
				dateFormat : 'mm/dd/yy',
				yearRange : '-50:+50',
				numberOfMonths : 3
			});
			$(this).focus();
		});
		$("#fromDate").change(function() {
			$("#toDate").datepicker('destroy'); //need to re-initialize the datepicker for field 2
		});
		function showDatepicker(clickedId) {
			$('#' + clickedId).datepicker({
				dateFormat : 'mm/dd/yy',
				minDate : new Date(),
				yearRange : '-50:+50',
				numberOfMonths : 2
			}).datepicker("show");
		}

		//validation for number of characters that can be entered ina textarea
		function limitText(limitField, limitNum) {
			if (limitField.value.length > limitNum) {
				limitField.value = limitField.value.substring(0, limitNum);
			}
		}

		function makeEditable(id) {
			$("#" + id).removeAttr("readonly");
		}

		function loadTakeAction(soId) {
			window.location.href = "${contextPath}/soDetailsController.action?soId="
					+ soId + "&cameFromOrderManagement=cameFromOrderManagement";
		}

		function loadIssueResolution(soId) {
			window.location.href = "${contextPath}/soDetailsController.action?soId="
					+ soId + "&defaultTab=Issue%20Resolution&cameFromOrderManagement=cameFromOrderManagement";
		}

		function loadCompleteForPayment(soId) {
			window.location.href = "${contextPath}/soDetailsController.action?soId="
					+ soId + "&defaultTab=Complete%20for%20Payment&cameFromOrderManagement=cameFromOrderManagement";
		}

		function loadTimeOnSite(soId) {
			window.location.href = "${contextPath}/soDetailsController.action?soId="
					+ soId + "&defaultTab=Time%20On%20Site&cameFromOrderManagement=cameFromOrderManagement";
		}

		function loadReportAProblem(soId) {
			window.location.href = "${contextPath}/soDetailsController.action?soId="
					+ soId + "&defaultTab=Report%20A%20Problem&cameFromOrderManagement=cameFromOrderManagement";
		}
		function loadRejectReSchedule(soId,acceptedResourceId,action) {
			fnWaitForResponseShow();

				$.ajax({
					url : 'cancelReschedule.action',
					type : "POST",
					data : {
						soId : soId,
						resourceId : acceptedResourceId,
						method :action
					},
					dataType : "json",
					success : function(data) {
						if (data.omErrors.length !== 0) {
							$.each(data.omErrors, function(index,value){
								$("#omSuccessDiv").html("<div  class='errorBox'><p>"+value.msg+"</p></div>");
								$("#omSuccessDiv").show();
							});	
						} else {
							fnWaitForResponseShow();
							$('.icon-repeat').trigger('click');
							$("#omSuccessDiv").html("<div  class='successBox'><p>"+$ESAPI.encoder().encodeForHTML(data.result.replace('.', ''))+" for soId:"+soId+"</p></div>");
							$("#omSuccessDiv").show();
						}
					},
					error: function(xhr, status, err) {
						location.href ="${contextPath}/homepage.action";
			        }
				});
		}
		
		function loadCounterOffer(soId,groupId) {
			var groupId = groupId;
			var soId = soId;
			if(soId == groupId){
				window.location.href = "${contextPath}/soDetailsController.action?groupId="
					+ groupId + "&defaultTab=Summary&fromOrderManagement=true&cameFromOrderManagement=cameFromOrderManagement"; 
			}else{
				window.location.href = "${contextPath}/soDetailsController.action?soId="
					+ soId + "&defaultTab=Summary&fromOrderManagement=true&cameFromOrderManagement=cameFromOrderManagement"; 
			}
		}
		
		function loadCompletionRecord(soId) {
			window.location.href = "${contextPath}/soDetailsController.action?soId="
					+ soId + "&defaultTab=Completion%20Record&cameFromOrderManagement=cameFromOrderManagement";
		}

		$(document).click(function(e) {
							var click = $(e.target);
							if(click.hasClass("overLay")){
								e.stopPropagation();
								return;
							}else if(!click.parents().hasClass("responseClass") || click.hasClass("responseClass")){
								$("#omErrorDiv > div").html("");
								$("#omErrorDiv > div").hide();
								$("#omSuccessDiv > div").html("");
								$("#omSuccessDiv > div").hide();
							}
							
							if("range"==$("#selectedApptDate").val()){
								if(click.prop("id") !== "app_dt_5"){
									checkApptDateError("fromDate");
								}
							}
							if (!click.hasClass("dropdown-menu") && !click.parents().hasClass("dropdown-menu") && !click.hasClass("dropdown") && !click.parents().hasClass("dropdown")){
								$(".dropdown-menu").hide();
								$(".btn-group").removeClass("open");
							} 
							if (click.hasClass("select-options") || click.parents().hasClass("select-options") || click.parents().hasClass("picked") || click.hasClass("picked")) {

							} else {
								$('.select-options').hide();
								var applyFilter = $("#applyFilterInd").val();
								if(applyFilter == 1 && validateFilterSelection()){
									fnApplyFilter();
								}
							}
							//for collapsing the list of providers on clicking out side the drop down.


							//for closing add notes modal on clicking outside modal
							var addNoteClicked = click.closest("a").is(
									'#addNoteLink');
							var editNoteClicked = click.closest("a").is(
									'#editLocnNotesLink');
							var rescheduleClicked = click.closest("a").is(
									'#rescheduleLink');
							if (addNoteClicked == false
									&& editNoteClicked == false
									&& rescheduleClicked == false) {
								if ((click.hasClass("jqmOverlay"))){
									if (!$('#addNote').is(':hidden')) {
										$("#addNote").jqmHide();
									}
									if (!$('#editNotes').is(':hidden')) {
										$("#editNotes").jqmHide();
									}
									if (!$('#requestReschedule').is(':hidden')) {
										//$("#requestReschedule").jqmHide();
										if(( typeof $('#rescheduleFromDate').val() !== 'undefined' && $('#rescheduleFromDate').val() != "")
												|| ( typeof $('#rescheduleToDate').val() !== 'undefined' && $('#rescheduleToDate').val() != "")
												|| ( typeof $('#reasonCodesForReschedule').val() !== 'undefined' && $('#reasonCodesForReschedule').val() != '0')){
											e.stopPropagation();
											return;
										}else{
											$("#rescheduleRequest").jqmHide();
										}
									}
								}else{
									e.stopPropagation();
									return;
								}
							}

						});

		function closeModal(id) {
			$("#" + id).jqmHide();
		}
	</script>		
<style>
</style>
</head>

<body class="tundra acquity">

<!-- Add Notes Start -->
	<div id="addNote" style="width: 400px; float: left; display:none;background:#eeeeee;border-color: #A8A8A8;">
		<div class="arrowAddNote"></div>
		<div id="modalTitleGrey_Add_Notes" style="background:#ddddde;color:#000000 ;text-align:left;height:25px;padding-left: 8px;padding-top: 5px;font-size: 15px;">
		<b>Add Note</b>
		<a><img src="${staticContextPath }/images/widgets/tabClose.png"
					style="float:right;" onclick="closeModal('addNote');" class="close" /></a>
		</div>
	 	<div class="errorBox clearfix" id="addNotesResponseMessage"
                     style="width: 300px; overflow-y: hidden; display: none;text-align: left;" >
        </div>
		<table style="border-bottom: 1px solid #ccc; width: 50px;padding-top: 10px;padding-left: 4px;background-color: #EFEFEF;" align="left">
		<tr align="left">
		<td>
		<textarea id="subject" name="subject" class="shadowBox" 
            	style="width: 200px; height: 20px;padding-left: 4px;">
        </textarea>
         </td>
         </tr>
        <tr align="char">
        <td>
         <textarea id="message" name="message" class="shadowBox" 
          	style="width: 370px;overflow:scroll;height: 100px;padding-left: 4px;">
        </textarea>
        </td>
        </tr>
        <tr align="left"><td>
        <br></br>
        <input type="checkbox" id="markPrivate" name="markPrivate"/> <span style="color:#000000"> Mark as Private</span> 
		</td></tr>
		<tr align="right"><td>
		<input type="hidden" value="" id="soId"/>
		<input type="button" value="SUBMIT" id="submitNotesBtn" class="actionButton"/>
        </td></tr>                                                                      
        
		</table>
	</div>
	<!-- Add Notes End -->

	<!-- Edit Location Notes Start -->
	<div id="editNote" style="display:none;"></div>
	<div id="editNotes" style="width: 400px; float: left; display:none;background:#eeeeee">
		<div class="arrowAddNote"></div>
		<div id="modalTitleGrey_Edit_Loc" style="background:#ddddde;color:#000000 ;font-weight:700;text-align:left;height:25px;padding-left: 8px;padding-top: 5px;font-size: 15px;">
		<b>Edit Service Location Notes</b>
		<a class="close"><img src="${staticContextPath }/images/widgets/tabClose.png"
					style="float:right;" onclick="closeModal('editNotes');" /></a>
		</div>
		
		<table style="border-bottom: 1px solid #ccc; width: 50px;padding-left: 4px;background-color: #EFEFEF;" align="left">
		<tr align="char"><td>
	 	<div class="errorBox clearfix editNotesResponseMessage" id="editNotesResponseMessage">
        </div>
        </td></tr>
        <tr align="char">
        <td>
       <c:if test="${omApiErrors!=null && fn:length(omApiErrors) > 0}">
			<br/>
				<div id="omApiErrorDiv" class="" style="">
				<div  class="errorBox">
				<ul>
				<c:forEach var="omApiError" items="${omApiErrors}">
				<c:if test="${omApiError!=null}">
				<li>
					${omApiError.msg}
				</li>
				</c:if>
				</c:forEach>
				</ul>
				</div>
				</div>
			<c:remove var="omApiErrors" scope="session" />
			</c:if>
        </td>
        </td>
        </tr>
        <tr align="char">
        <td>
        <br></br>
        <textarea id="sl_notes" name="notes" class="shadowBox" 
          	style="width: 370px;overflow:scroll;height: 100px;" onkeyup="limitText(this,750);" onkeydown="limitText(this,750);" >
        </textarea>
        </td>
        </tr>
		<tr align="right"><td>
		<br />
		<br />
		<br />
	      <input type="hidden" id="soId"/>
	    <input type="button" value="SUBMIT"  id="editNotesBtn" class="actionButton" style="float: right;" />
        </td></tr>                                                                      
		</table>
	</div>
	<!-- Edit Location Notes End -->
	
	<!-- Request A Reshedule Start -->
	<div id="rescheduleRequest"></div>
	<!-- Request A Reshedule End -->
	
	<!-- Accept Service Order Start -->
	<div id="assignOrder" style="width: 500px; float: left; display:none; border: 1px solid #A8A8A8;font-family: Arial;">
	</div>	
	<!-- add estimate Service Order Start -->
	<div id="addEstimate" style="width: 850px; float: left; display:none; border: 1px solid #A8A8A8;font-family: Arial;">
	</div>

	<!-- Accept Service Order End -->
	
	<!-- Assign/Reassign Provider Start -->
		<div id="assignProviderDiv" style="width: 550px; float: left; display:none; border: 1px solid #A8A8A8;font-family: Arial;"></div>
	<!-- Assign/Reassign Provider End -->

	<!-- Update Time Start -->
		<div id="updateApptTime"></div>
	<!-- Update Time End -->

	<!-- Print Paper Work Start -->
	<div id="printPaperWorkDiv"></div>

	<!-- Print Paper Work End -->

	<!-- Reject Service Order Start-->
	<div id="rejectOrder" style="display: none;"></div>
	<!-- Reject Service Order End -->
	<div id="priority"></div>
</body>

</html>
