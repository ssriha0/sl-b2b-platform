<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>  
	<link href="${staticContextPath}/javascript/CalendarControl.css" rel="stylesheet" type="text/css">
<!--  	<script type="text/javascript" src="${staticContextPath}/javascript/CalendarControl.js"></script> -->
	

	<script type="text/javascript">
		
		//$(function() {
		//	$("#confirmScheduleAccordion").accordion({
		//	autoHeight: false});
		//});
		jQuery(document).ready(function () 
		{
            $(".accordionContent").hide();
            $('#confirmScheduleDiv').hide();
            $('#confirmOrderDiv').show();
 			$('#assignProvidersDiv').css("display","none");

    		$("#confirmOrderErrDiv").hide();
    		$("#confirmServiceErrDiv").hide();
    		$("#confirmScheduleErrDiv").hide();
	   		 $('#assignProvidersDiv').css("display","block");
  			$("#custResponseDropdownDiv").css("display","block");
 			$("#timeWindowDropdown").prop("value","-1");
 			$("#custNotAvailableReasonDropdown").prop("value",-1);
 			$("#etaDropdown").prop("value",-1);
			var slNotes = $("#originalSlNotes").prop("value");
  			var splInstr = $("#originalSplInstr").prop("value");
  			$("#seviceLocationNotes").prop("value",slNotes);
  			$("#specialIntructions").prop("value",splInstr);
  			$("#serviceLocationNotesDiv").css("display","none");
			$("#splInstrDiv").css("display","none");
			$("#productInfoDiv").css("display","none");
			 var todayInd = $("#today").prop("value");
			 var resourceAssignedInd=$("#resourceAvailableInd").prop("value");
				if(todayInd=='true'){
					if(resourceAssignedInd == 'true')
						{
						$("#reminderAppointmentToday_schedule").css("display","none");
						$("#etaWarningAppointmentToday_schedule").css("display","block");
						
						}
					else
						{
		  				 $("#reminderAppointmentToday_schedule").css("display","block");
		  			    $("#reminderAppointmentNotToday").css("display","none");
				        $("#reminderAppointmentNotTodayConfirmAppt").css("display","none");
						}
  			}
  			else{
  				 $("#reminderAppointmentToday_schedule").css("display","none");
  		         var source = $("#source").prop("value");
  		         if(source == 'PreCall'){
  					 $("#reminderAppointmentNotToday").css("display","block");
 			        $("#reminderAppointmentNotTodayConfirmAppt").css("display","none");
  		         }
  		         else{
  					 $("#reminderAppointmentNotTodayConfirmAppt").css("display","block");
 			        $("#reminderAppointmentNotToday").css("display","none");
  		         }
  			} 
				 $("#reminderAppointmentToday_schedule").css("display","none");   
				 var zone = '${precallDetails.schedule.serviceLocationTimezone}';
			if(null != zone && '' != zone){
				var displayZone = zone.substring(zone.length-3);
				var actualZone = zone.substring(0, zone.length-4);
				$("#timeZoneForPreCall").val(actualZone);
				$("#timeZoneForReschedule").val(actualZone);
				$('.precallZone').html(displayZone);
			}   
				 
		});
		
		function onTimeWindowDropDownClick(id){
			if(id == 'timeWindowDropdown'){
				$("#etaDropdownhidden").prop("value",-1);
				$("#scheduleFromTimehidden").prop("value",-1);
				$("#scheduleToTimehidden").prop("value",-1);
				$("#precallRangeRescheduleFromDatehidden").prop("value","");
				$("#precallRangeRescheduleToDatehidden").prop("value","");
				$("#rescheduleRangeDateStartTimeDropdownhidden").prop("value",-1);
				$("#rescheduleRangeDateEndTimeDropdownhidden").prop("value",-1);
				$("#reasonForRescheduleDropdownhidden").prop("value",-1);
				$("#rescheduleCommentsTextareahidden").prop("value","");
			}

			var timeWindowDropdownVal = $("#timeWindowDropdown").val();
			$("#rescheduleErrorMsg").html("");
			$("#rescheduleErrorMsg").hide();
			
			if(timeWindowDropdownVal == '-1'){
				$("#scheduleTimeDiv").css("display","none");
				$("#rescheduleDiv").css("display","none");
				$("#etaDiv").css("display","none");
			}else if(timeWindowDropdownVal == '1'){
				$("#scheduleTimeDiv").css("display","none");
				$("#rescheduleDiv").css("display","none");
				var value =$("#etaDropdownhidden").prop("value");
				$("#etaDropdown").prop("value",value);
				$("#etaDiv").css("display","block");
				
			}else if(timeWindowDropdownVal == '2'){
				$("#scheduleTimeDiv").css("display","block");
				$("#rescheduleDiv").css("display","none");
				$("#etaDiv").css("display","block");
				var value =$("#etaDropdownhidden").prop("value");
				$("#etaDropdown").prop("value",value);
				var value =$("#scheduleFromTimehidden").prop("value");
				$("#scheduleFromTime").prop("value",value);
				value =$("#scheduleToTimehidden").prop("value");
				$("#scheduleToTime").prop("value",value);

				}
			if(timeWindowDropdownVal == '3'){
				$("#scheduleTimeDiv").css("display","none");
				$("#rescheduleDiv").css("display","block");
				$("#etaDiv").css("display","none");
				//setting dateRange option as default.
				$("#preCallRangeDate").prop("checked", true);
				$("#rescheduleSpecificDataAndTimeDiv").hide();
				$("#rescheduleDateRangeDiv").show();
				$("#reasonForRescheduleDropdown").prop("value",-1);
				$("#rescheduleCommentsTextarea").prop("value","");

				var value =$("#precallSpecificRescheduleFromDatehidden").prop("value");
				$("#precallSpecificRescheduleFromDate").prop("value",value);
				value =$("#precallSpecificTimeDropdownhidden").prop("value");
				$("#precallSpecificTimeDropdown").prop("value",value);
				value =$("#precallRangeRescheduleFromDatehidden").prop("value");
				$("#precallRangeRescheduleFromDate").prop("value",value);
				value =$("#precallRangeRescheduleToDatehidden").prop("value");
				$("#precallRangeRescheduleToDate").prop("value",value);
				value =$("#rescheduleRangeDateStartTimeDropdownhidden").prop("value");
				$("#rescheduleRangeDateStartTimeDropdown").prop("value",value);
				value =$("#rescheduleRangeDateEndTimeDropdownhidden").prop("value");
				$("#rescheduleRangeDateEndTimeDropdown").prop("value",value);
				value =$("#reasonForRescheduleDropdownhidden").prop("value");
				$("#reasonForRescheduleDropdown").prop("value",value);
				value =$("#rescheduleCommentsTextareahidden").prop("value");
				$("#rescheduleCommentsTextarea").prop("value",value);


			}
			return true;
		}
		
		function loadAssignProviderForPreCall(event,id){
			$("#assignProviderDropdown").css("display","block");
			$("#assignedProviderDiv").hide();
			
			/*if(id == 'assignProviderLink'){
				$("#assignProviderLink").css("display","none");
			}
			if(id == 'reassignProviderLink'){
				$("#reassignProviderLink").css("display","none");
			}*/
			$("#assignProviderDropdown").val('-1');
		}
		
		function showPrecallRescheduleFixedDate() {
    		//$("#rescheduleErrorMsg").html("");
    		$("#rescheduleSpecificDataAndTimeDiv").show();
    		$("#rescheduleDateRangeDiv").hide();
			$("#precallSpecificRescheduleFromDate").prop("value","");
			$("#precallSpecificTimeDropdown").prop("value",-1);
    		$("#reasonForRescheduleDropdown").prop("value",-1);
			$("#rescheduleCommentsTextarea").prop("value","");
    	
		}

		function showPrecallRescheduleRangeDate() {
			//$("#rescheduleErrorMsg").html("");
			$("#rescheduleSpecificDataAndTimeDiv").hide();
			
    		$("#rescheduleDateRangeDiv").show();
			$("#reasonForRescheduleDropdown").prop("value",-1);
			$("#rescheduleCommentsTextarea").prop("value","");

			//document.getElementById('rescheduleDateRanged').style.display="none";
		}
		function viewCallHistory(e) {
		
			var click = $("#viewCallHistoryLink").parent().parent();
			var offs = click.offset();
			try{
				y1 = offs.top;
				x1 = offs.left;
			}catch (e) {
				y1 = e.offsetY;
				x1 = e.offsetX;
			}
			
			var x = e.pageX;
			if (x == undefined) {
				x = e.offsetX;
			}
			var y = e.pageY;
			if (y == undefined) {
				y = e.offsetY;
			}
			
			$("#precallHistoryDiv").css("marginLeft", x1-300);
			$("#precallHistoryDiv").show();
		/*	$("#precallHistoryModel").addClass("jqmWindow");
			$("#precallHistoryModel").css("width", "160px");
			$("#precallHistoryModel").css("height", "auto");
			$("#precallHistoryModel").css("marginLeft", "-45px");
			$("#precallHistoryModel").css("top", y - 54);
			$("#precallHistoryModel").jqm({
				modal : true,
				overlay : 0
			});
			$("#precallHistoryModel").css("border-left-color", "#A8A8A8");
			$("#precallHistoryModel").css("border-right-color", "#A8A8A8");
			$("#precallHistoryModel").css("border-bottom-color", "#A8A8A8");
			$("#precallHistoryModel").css("border-top-color", "#A8A8A8");
			$("#precallHistoryModel").jqmShow(); */
		}
		
		function closePrecallHistory(){
			$("#precallHistoryDiv").hide();
		}
		$(document).on('click', ".accordionButton", function(){
     		var clickedTypeId = ($(this).prop("id"));
     		var divId = clickedTypeId.replace("Accordion","Div");

     		if($("#"+divId).is(':visible')){
    			$("#"+divId).css("display","none");
         		if(clickedTypeId == 'confirmScheduleAccordion'){
         			$('#assignProvidersDiv').css("display","none");
         			$("#custResponseDropdownDiv").css("display","none");
         			      		    $("#scheduleTimeDiv").css("display","none");
        		    $("#etaDiv").css("display","none");
        		    $("#rescheduleDiv").css("display","none");
         		}
    		}
     		else{
			$('.accordionContent').slideUp('normal');
     		$(this).next().slideDown('normal');
     		if(clickedTypeId == 'confirmScheduleAccordion'){
     	   		 $('#assignProvidersDiv').css("display","block");
     			$("#custResponseDropdownDiv").css("display","block");
    			 var todayInd = $("#today").prop("value");
    			 var resourceAssignedInd=$("#resourceAvailableInd").prop("value");
    		    if(todayInd=='true'){
    		    	if(resourceAssignedInd == 'true')
					{
    				 $("#reminderAppointmentToday_schedule").css("display","block");
    				 $("#confirmScheduleDiv div#reminderAppointmentToday_schedule").css("display","none");
    			    $("#reminderAppointmentNotToday").css("display","none");
			        $("#reminderAppointmentNotTodayConfirmAppt").css("display","none");

					}
    		    	else
        		    	{
        		    	$("#reminderAppointmentToday_schedule").css("display","block");
    		    	    }
    			}
    			else{
    				 $("#reminderAppointmentToday_schedule").css("display","none");
    				 var source = $("#source").prop("value");
      		         if(source == 'PreCall'){
      					 $("#reminderAppointmentNotToday").css("display","block");
     			        $("#reminderAppointmentNotTodayConfirmAppt").css("display","none");
      		         }
      		         else{
      					 $("#reminderAppointmentNotTodayConfirmAppt").css("display","block");
     			        $("#reminderAppointmentNotToday").css("display","none");
      		         }    			}  
    		    onTimeWindowDropDownClick('');
     		}
     		else{
     			$('#assignProvidersDiv').css("display","none");
     			$("#custResponseDropdownDiv").css("display","none");
    		    $("#reminderAppointmentNotToday").css("display","none");
		        $("#reminderAppointmentNotTodayConfirmAppt").css("display","none");
    		    $("#scheduleTimeDiv").css("display","none");
    		    $("#etaDiv").css("display","none");
    		    $("#rescheduleDiv").css("display","none");
     		}
     		}

        });
        
		function setValue(clickedId){
			  
			var hiddenId = clickedId+"hidden";
			var value = $("#"+clickedId).prop("value");
			$("#"+hiddenId).prop("value",value);
			
		  }
        function setScheduleToTime(fromId, toId){
        	var value= $("#rescheduleRangeDateStartTimeDropdown").prop("value");
        	$("#rescheduleRangeDateStartTimeDropdownhidden").prop("value",value);
        	value= $("#scheduleFromTime").prop("value");
        	$("#scheduleFromTimehidden").prop("value",value);

        	var minTime = $('#minTime').val();
        	var maxTime = $('#maxTime').val();
			var startTime = $('#'+fromId).val();
			$('#'+toId).children('option:not(:first)').remove();
			if("-1" != startTime){	
				if(null == minTime || '' == minTime){
					minTime = 0;
				}
				if(null == maxTime || '' == maxTime){
					maxTime = 0;
				}
				var limit = 0;
				var limit1 = (minTime * 4);
				var limit2 = (maxTime * 4)+1;
				var listSize = ${fn:length(time_intervals_updated)};
				if(maxTime == 0){
					limit2 = listSize;
				}
				<c:forEach var="time" items="${time_intervals_updated}" varStatus="status">
					var count = ${status.count};
					var timeDesr = "<c:out value="${time.descr}"/>";
					if(timeDesr == startTime){
						limit = count;
					}
				</c:forEach>			
				if(limit+limit2 <= listSize){
					var boundaryflag = true;
					<c:forEach var="time" items="${time_intervals_updated}" varStatus="status">
						var count = ${status.count};
						if(count>=limit+limit1 && count<limit2+limit && boundaryflag == true){
							var diff = getTimeDiff(startTime,'${time.descr}');
							$('#'+toId).append('<option value="${time.descr}">${time.descr} ('+diff+')</option>');
							if('${time.descr}' == '11:45 PM'){
								boundaryflag=false;
							}
						}
					</c:forEach>
				}
				else{
					var boundaryflag = true;
					var size = limit2-(listSize-limit);
					<c:forEach var="time" items="${time_intervals_updated}" varStatus="status">
						var count = ${status.count};
						if(count>=limit && count<=listSize && boundaryflag == true){
							var diff = getTimeDiff(startTime,'${time.descr}');
							if(count>=limit+limit1 && count<limit2+limit){
								$('#'+toId).append('<option value="${time.descr}">${time.descr} ('+diff+')</option>');
								if('${time.descr}' == '11:45 PM'){
									boundaryflag=false;
								}
							}
						}
					</c:forEach>
					<c:forEach var="time" items="${time_intervals_updated}" varStatus="status">
						var count = ${status.count};
						if(count<size && boundaryflag == true){
							var diff = getTimeDiff(startTime,'${time.descr}');
							if(count> (size-limit1-2)){
								$('#'+toId).append('<option value="${time.descr}">${time.descr} ('+diff+')</option>');
								if('${time.descr}' == '11:45 PM'){
									boundaryflag=false;
								}
							}
						}
					</c:forEach>
				}
			}
		}
		
		//to get the difference in hours b/n 2 times 
		function getTimeDiff(startTime, endTime){  
			var timeStart = new Date("01/01/2007 " + startTime).getTime();
			var timeEnd = new Date("01/01/2007 " + endTime).getTime();
			var hourDiff = (timeEnd - timeStart)/60 / 60 / 1000;
			if(hourDiff < 0){
				hourDiff = 24 + hourDiff;
			}
			if(0 == hourDiff){
				hourDiff = hourDiff + " mins";
			}else if(0.25 == hourDiff){
				hourDiff = "15 mins";
			}else if(0.50 == hourDiff){
				hourDiff = "30 mins";
			}else if(0.75 == hourDiff){
				hourDiff = "45 mins";
			}else if(1 == hourDiff){
				hourDiff = hourDiff + " hr";
			}else{
				hourDiff = hourDiff + " hrs";
			}
			return hourDiff;
		}
	
	</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<style type="text/css">
<!--
#pre_call input, #pre_call select, #pre_call option, #pre_call table {
	font-size: 13px;
}

-->
</style>
</head>
<body>
<div id="pre_call">
<form id="updatePrecallForm" action="#" name="updatePrecall" >


<c:set var="isToday" value="${todayInd}"></c:set>
<c:set var="resourceAssigned" value="${assigned}"></c:set>
<input id="source" name="precallScheduleUpdateDTO.source" type="hidden" value=""/>
<input id="soIdhidden" name="precallScheduleUpdateDTO.soId" type="hidden" value='${precallDetails.orderstatus.soId}'/>
<input id="assignResourceIdhidden" name="precallScheduleUpdateDTO.assignedResourceId" type="hidden" value=""/>
<input id="customerAvailableOrNothidden" name="precallScheduleUpdateDTO.customerAvailableFlag" type="hidden" value=""/>
<input id="customerNotAvailableReasonIdhidden" name="precallScheduleUpdateDTO.customerNotAvalableReasonCode" type="hidden" value=""/>
<input id="etahidden" name="precallScheduleUpdateDTO.eta" type="hidden" value=""/>
<input id="serviceLocationNotesEditFlaghidden" name="precallScheduleUpdateDTO.locationNotesFlag" type="hidden" value="false"/>
<input id="specialInstructionsEditFlaghidden" name="precallScheduleUpdateDTO.specialInstructionEditFlag" type="hidden" value="false"/>
<input id="serviceLocationNoteshidden" name="precallScheduleUpdateDTO.locationNotes" type="hidden" value=""/>
<input id="specialInstructionshidden" name="precallScheduleUpdateDTO.specialInstructions" type="hidden" value=""/>
<input id="timeWindowhidden" name="precallScheduleUpdateDTO.timeResponseWindow" type="hidden" value="0"/>
<input id="fromTimehidden" name="precallScheduleUpdateDTO.startTime" type="hidden" value=""/>
<input id="toTimehidden" name="precallScheduleUpdateDTO.endTime" type="hidden" value=""/>
<input id="specificOrRangeValhidden" name="precallScheduleUpdateDTO.specificOrDateRange" type="hidden" value=""/>
<input id="rescheduleFromDatehidden" name="rescheduleDTO.newStartDate" type="hidden" value=""/>
<input id="rescheduleFromTimehidden" name="rescheduleDTO.newStartTime" type="hidden" value=""/>
<input id="rescheduleToDatehidden" name="rescheduleDTO.newEndDate" type="hidden" value=""/>
<input id="rescheduleToTimehidden" name="rescheduleDTO.newEndTime" type="hidden" value=""/>
<input id="rescheduleReasonCodehidden" name="rescheduleDTO.reasonCode" type="hidden" value=""/>
<input id="rescheduleCommenthidden" name="rescheduleDTO.rescheduleComments" type="hidden" value=""/>
<input id="rangeOfDatesStr" name="rescheduleDTO.specificDate" type="hidden" value="true"/>
<input id="originalSlNotes" name="originalSlNotes" type="hidden" value="${precallDetails.serviceLocation.note}"/>
<input id="originalSplInstr" name="originalSplInstr" type="hidden" value="${precallDetails.sectionGeneral.specialInstructions}"/>
<input id="minTime" type="hidden" value="${precallDetails.buyer.minTimeWindow}"/>
<input id="maxTime" type="hidden" value="${precallDetails.buyer.maxTimeWindow}"/>
<input id="etaDropdownhidden" name="etaDropdownhidden" type="hidden" value="-1"/>
<input id="scheduleFromTimehidden" name="scheduleFromTimehidden" type="hidden" value="-1"/>
<input id="scheduleToTimehidden" name="scheduleToTimehidden" type="hidden" value="-1"/>
<input id="precallRangeRescheduleFromDatehidden" name="precallRangeRescheduleFromDatehidden" type="hidden" value="-1"/>
<input id="precallRangeRescheduleToDatehidden" name="precallRangeRescheduleToDatehidden" type="hidden" value="-1"/>
<input id="rescheduleRangeDateStartTimeDropdownhidden" name="rescheduleRangeDateStartTimeDropdownhidden" type="hidden" value="-1"/>
<input id="rescheduleRangeDateEndTimeDropdownhidden" name="rescheduleRangeDateEndTimeDropdownhidden" type="hidden" value="-1"/>
<input id="reasonForRescheduleDropdownhidden" name="reasonForRescheduleDropdown" type="hidden" value="-1"/>
<input id="rescheduleCommentsTextareahidden" name="rescheduleCommentsTextareahidden" type="hidden" value=""/>
<input id="timeZoneForPreCall" name="precallScheduleUpdateDTO.timeZone" type="hidden" value=""/>
<input id="timeZoneForReschedule" name="rescheduleDTO.timeZone" type="hidden" value=""/>
<input id="startDateForPreCall" name="precallScheduleUpdateDTO.startDate" type="hidden" value="${fn:substring(precallDetails.schedule.serviceDateTime1,0,10)}"/>
<input id="endDateForPreCall" name="precallScheduleUpdateDTO.endDate" type="hidden" value="${fn:substring(precallDetails.schedule.serviceDateTime2,0,10)}"/>
<input id="scheduleTypeForPreCall" type="hidden" value="${precallDetails.schedule.scheduleType}"/>


<c:forEach var="contact" items="${precallDetails.contacts.contactList}">
	<c:if test="${contact.contactLocnType=='Service'}">
		<c:set var="firstName" value="${contact.firstName}"/>
		<c:set var="lastName" value="${contact.lastName}"/>
		<c:set var="primaryPhone" value="${contact.primaryPhone.number}"/>
		<c:set var="altPhone" value="${contact.altPhone.number}"/>
	</c:if>
</c:forEach>

<!-- <div class="example" style="background-color:#404040; background-color:rgba(0,0,0,0.75); padding:1em;overflow:auto; position:relative; min-height:900px;">-->
			<div id="pre_call" class="modal" style="font-size: 13px;">

				<input type="hidden" id="resourceAssigned"
					value="${resourceAssigned}"
					name="precallScheduleUpdateDTO.reAssign" /> 
				<input type="hidden"
					id="today" value="${isToday}" /> 
				<input type="hidden"
					id="resourceAvailableInd" value="${resourceAssigned}"> <i
					class="icon-remove" title="Close" id="closePrecallModel"></i>
				<h2 class="modal-title" style="font-family: Arial;">
					<div id="title"></div>
				</h2>
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
				<div class="modal-content" style="color: #666666;overflow-x: hidden;padding-bottom: 0px;">
				<div id="pre-call-content" style="font-family: Arial;">
					<div>
						<div class="precall-title-bold" style="float: left;">${firstName} ${lastName} </div>
						<c:if test="${fn:length(precallHistoryDetails)>0}">
						<div style="float: left;margin-left: 5px;margin-top: 2px;">	
							<a href="javascript:void(0);" class="link precall-link" style="text-align: left;"
								onclick="viewCallHistory(event);" id="viewCallHistoryLink">View
								call history</a>
						</div>
						</c:if>
					</div>
					<br />
					<div>
						<div style="padding-top:5px;">
							<div style="float: left;" class="precall-title-bold"><i class="icon-phone"></i></div>
							<div style="float: left;font-weight: normal;padding-left:3px;width: 75px;" class="precall-title-bold">Primary: </div>
							<div style="float: left;" class="precall-title-bold">							
							<c:if test="${null != primaryPhone && '' != primaryPhone}">
								(${fn:substring(primaryPhone, 0, 3)}) ${fn:substring(primaryPhone, 4, 12)}
							</c:if>
							</div>
						</div>
						<c:if test="${altPhone!=null && altPhone!=''}">
							<div style="padding-top:5px;width:600px;float:left;padding-bottom:5px;">
								<div style="float: left;" class="precall-title-bold"><i class="icon-phone"></i></div>
								<div style="float: left;font-weight: normal;padding-left:3px;width: 75px;" class="precall-title-bold">Alternate:</div>
								<div style="float: left;" class="precall-title-bold">
									<c:if test="${null != altPhone && '' != altPhone}">
								(${fn:substring(altPhone, 0, 3)}) ${fn:substring(altPhone, 4, 12)}
							</c:if>
								
								</div>
							</div>
						</c:if>
					</div>
					<br />
					
					<!--  Start of Precall History Popup -->
					<div id="precallHistoryDiv" style="width: 260px;display: none;">
						<div id="precallHistoryModel"
							style="float: left; background: #FFFFFF;height: auto;overflow: hidden;position: absolute; top: 0px;width: 233px;-moz-border-radius: 8px;background: none repeat scroll 0 0 #FFFFFF;border: 3px solid #CCCCCC;">
							<div class="preCallHistoryHead" style="height: 31px;width: 100%px;background: #EEEEEE;-moz-border-radius: 8px 8px 0 0;">
								<i class="icon-remove" title="Close" id="closePrecallHistoryModel" onclick="closePrecallHistory();" style="color: #BBBBBB;cursor: pointer;position: absolute;right: 1px;top: 7px;"></i>
								<div class="" style="padding-top:10px;margin-left: 3px;">Call History</div>
							</div>
							<div class="" style="border-color: transparent #CCCCCC transparent transparent; left: -20px;; top: 10px;position: absolute;border-width: 10px;border-style: solid;"></div>
							<div style="margin-bottom: 5px;padding-left: 8px;border-top: 1px solid #CCCCCC;padding-top: 5px;">
								<c:forEach var="preCallHistory" items="${precallHistoryDetails}">
									<div style="margin-top: 3px;padding-top: 5px;">
								 		<span class="pre-call-common-hd"> ${preCallHistory.formattedDate}</span>
								 		<span>${preCallHistory.formattedTime}</span>
								 	</div>
								 	<div style="">
								 		${preCallHistory.reason}
								 	</div>
								</c:forEach>
							</div>
						</div>
					</div>
					<!--  End of Precall History Popup -->
					
					<div style="padding-top:5px;">
						<p>
							<input type="checkbox" name="custavailablechkbox"
								id="custavailablechkbox" value="">
								<span style="color:#707071;font-size:11px;"> Customer not available</span>
						</p>
					</div>
					<div id="customerNotAvailableErrorMsg" class="errorBox"
						style="display: none;"></div>

					<div id="preCallErrDiv" class="errorBox" style="display: none;">
					</div>
					<div id="custNotAvailableReasonDropDownDiv" style="display: none;padding-top: 10px;">
						Select the reason for not completing the call : 
						<select
							id="custNotAvailableReasonDropdown"
							name="custNotAvailableReasonDropdown"
							style="width: 450px;margin-top:10px;">
							<option value="-1">Select</option>
							<c:forEach items="${preCallReasonCodesLst}" var="preCallRsn">
								<option value="${preCallRsn.id}">${preCallRsn.descr}</option>
							</c:forEach>
						</select>
					</div>
					<div class="accordionButton" id="confirmOrderAccordion">
						<h3 class="accordion-title">Confirm the Order</h3>
					</div>
					<div id="confirmOrderDiv" class="accordionContent"
						style="display: block;">
						<div id="confirmOrderDivError" class="errorBox" style="display: none;margin-top: 10px;">
						</div>
						<div style="margin-top: 15px;"> <span class="pre-call-common-hd"> ${precallDetails.sectionGeneral.title} </span>
							<div style="font-size: 11px;">
							S.O. # ${precallDetails.orderstatus.soId}
							</div>
						</div>
						<div style="margin-top: 15px;">
							<span class="pre-call-common-hd">Service Location: </span> 
							<div style="">
							<p style="">
							<span style="">
								${precallDetails.serviceLocation.address1}<br>
								${precallDetails.serviceLocation.address2}<br>
								${precallDetails.serviceLocation.city},&nbsp;${precallDetails.serviceLocation.state} ${precallDetails.serviceLocation.zip}
							</span>
							</p>	
							</div> 
						</div>
						
						<div  style="float: left;">
							<div id="viewServiceLocationNotesLinkdownArrow"
								class="icon-caret-down"
								style="display: none; white-space: nowrap; margin-left: 0px; margin-bottom: -11px;color: #00A0D2;float: left;"></div>
							<div id="viewServiceLocationNotesLinkrightArrow"
								class="icon-caret-right"
								style="display: block; white-space: nowrap; margin-left: 0px; margin-bottom: -11px;color: #00A0D2;float: left;"></div>
							<div style="float: left;"><a href="javascript:void(0);" class="link precall-link" style="margin-left: 5px;"
								id="viewServiceLocationNotesLink">Service location notes</a>
							</div> 
						</div>	
						<br /><br />
						<div id="serviceLocationNotesDiv" style="display: none;margin-bottom: 10px;">
							<div style="float: left;">
								<div style="float: left;">
									<span class="pre-call-common-hd">Notes: </span>
								</div>
								<div style="float: left;">
								<a href="javascript:void(0);" class="link precall-link"
									style="margin-left: 5px;padding-top:2px;"
									id="viewLocationNotesLink">Edit</a>
								</div>
							</div>
							<br />
							<div id="slNotes" style="word-wrap:break-word;width: 520px;">
								<p>
								${precallDetails.serviceLocation.note}
								</p>
							</div>
							<textarea id="seviceLocationNotes" rows="4" cols="20"
								name="seviceLocationNotes" style="width: 500px; display: none;height: 60px;">${precallDetails.serviceLocation.note}</textarea>
							<br />
						</div>
					</div>
					<div class="accordionButton" id="confirmServiceAccordion">
						<h3 class="accordion-title">Confirm the Service</h3>
					</div>
					<div id="confirmServiceDiv" class="accordionContent"
						style="display: none;">
						<c:set var="countTask" value="1"></c:set>
						<div id="confirmServiceDivError" class="errorBox" style="display: none;margin-top: 10px;">
						</div>
						<div>
							<c:forEach var="task" items="${precallDetails.scopeOfWork.tasks.taskList}">
								<div style="margin-top: 8px;">
									<div>
										<span class="pre-call-common-hd">Task ${countTask}:</span>
									</div>
										<c:set var="countTask" value="${countTask+1}" />
									<div style="">
										<p style="">
										<span style="">
										${task.taskName} (<c:if test="${not empty task.subCategoryName}">${task.subCategoryName} -> </c:if>${task.serviceType})
										</span>
										</p>
									</div>
								</div>
							</c:forEach>
						</div>
						<br/>
							<div style="float: left;">
								<div style="float: left;">
									<span class="pre-call-common-hd">Special Instuctions: </span>
								</div>
								<div style="float: left;margin-top: 2px;">
									<a href="javascript:void(0);" class="link precall-link"
										style="margin-left: 5px; margin-top: 2px;"
										id="viewIntructionsLink">Edit</a>
								</div>
							</div>
							<br/>
							<div id="splInstr" style="word-wrap:break-word;width: 520px;">
								<p>
								${precallDetails.sectionGeneral.specialInstructions}
								</p>
							</div>
							<div style="">
							<textarea rows="4" cols="20" id="specialIntructions"
								name="specialIntructions" style="width: 500px; display: none;height: 60px;">${precallDetails.sectionGeneral.specialInstructions}</textarea>
						</div>
						<div>
						<div style="margin-top: 5px;">
							<div style="">
								<div style="">
									<div id="viewProductInfoLinkdownArrow" class="icon-caret-down"
										style="display: none; float:left; white-space: nowrap; margin-left: 0px; margin-bottom: -11px;color: #00A0D2;"></div>
									<div id="viewProductInfoLinkrightArrow" class="icon-caret-right"
										style="display: block;float:left; white-space: nowrap; margin-left: 0px; margin-bottom: -11px;color: #00A0D2;"></div>
									<div style="float: left;">
										<a href="javascript:void(0);" class="link precall-link" style="margin-left: 5px;"
										id="viewProductInfoLink">Product Info</a>
									</div>
								</div>
								<br />
								<div id="productInfoDiv" style="">
									<table>
										<tr>
											<td>
												<div>													
														<c:if
														test="${precallDetails.parts != null && precallDetails.parts.partList!=null}">
																	<c:forEach var="part"
														items="${precallDetails.parts.partList}">
														<c:if
															test="${(not empty part.manufacturer)}">
																<div style="float: left;width: 100%;">
																	<div style="float: left;width: 200px;">
																		<p style="">
																		<span class="pre-call-common-hd" style="">Manufacturer:</span>
																		</p>
																	</div>
															  		<div style="float: left;">
															  		<p style="font-size: 13px;width: 300px;word-wrap: break-word;">
															  		${part.manufacturer}
															  		</p>
															  		</div>
															  	</div>
															  </c:if>
															 <c:if
															test="${(not empty part.model)}">
															  	<div style="float: left;width: 100%;">
																	<div style="float: left;width: 200px;">
																		<p style="">
																		<span class="pre-call-common-hd" style="">Model:</span>
																		</p>
																	</div>
															  		<div style="float: left;">
															  		<p style="font-size: 13px;width: 300px;word-wrap: break-word;">
															  		${part.model}
															  		</p>
															  		</div>
															  	</div>
															  </c:if>
														</c:forEach>
												</c:if>
												<c:forEach var="customReference"
														items="${precallDetails.customReferences.customRefList}">
														<c:if
															test="${customReference.name == 'MERCHANDISE CODE' || customReference.name == 'BRAND' || 
														customReference.name == 'Model Number'|| customReference.name == 'Merchandise Availability Date'}">
															<c:if
																test="${customReference.value != null && customReference.value != ''}">
																<div style="float: left;width: 100%;">
																	<div style="float: left;width: 200px;">
																		<p style="">
																		<span class="pre-call-common-hd" style="text-transform:capitalize;">${fn:toLowerCase(customReference.name)}:</span>
																		</p>
																	</div>
															  		<div style="float: left;">
															  		<p style="font-size: 13px;width: 300px;word-wrap: break-word;">
															  		${customReference.value}
															  		</p>
															  		</div>
															  	</div>
															</c:if>
														</c:if>
													</c:forEach>
													<c:if
														test="${precallDetails.product != null && precallDetails.product.productStreet1!=null && precallDetails.product.productStreet1!=''}">
														<div style="float: left;width: 100%">
															<div style="float: left;width: 200px;"><span class="pre-call-common-hd">Product Location:</span></div>
															<div style="float: left;">
																<p style="font-size: 13px;word-wrap: break-word;">${precallDetails.product.productStreet1}
																<br>${precallDetails.product.productStreet2},<br>${precallDetails.product.city}, ${precallDetails.product.state}&nbsp;${precallDetails.product.zip}</p>
															</div>
														</div>
												</c:if>
											</div>
											</td>
										</tr>
									</table>
								</div>
							</div>
						</div>
						<br />
						</div>
					</div>
					<div class="accordionButton" id="confirmScheduleAccordion">
						<h3 class="accordion-title">Confirm the Schedule</h3>
					</div>
					<div class="accordionContent"
						style="padding-bottom: 12px;display: block;"
						id="confirmScheduleDiv">
						<div id="confirmScheduleDivError" class="errorBox" style="display: none;margin-top: 8px;">
						</div>
						<div style="margin-top: 10px;"> 
							<span class="pre-call-common-hd">Appointment Date: </span>
							${precallDetails.schedule.formattedServiceDate1}
							<c:if test="${precallDetails.schedule.formattedServiceDate2!=null}">
									&nbsp;-&nbsp;${precallDetails.schedule.formattedServiceDate2}
							</c:if>
						</div>
						<div style="margin-top: 10px;"> 
						<span class="pre-call-common-hd">
						<c:choose><c:when test="${precallDetails.schedule.serviceTime2!=null}">Service Window:
						</c:when><c:otherwise>Service Time:</c:otherwise></c:choose></span>&nbsp;${precallDetails.schedule.serviceTime1}
						<c:if test="${precallDetails.schedule.serviceTime2!=null}">
							&nbsp;-&nbsp;${precallDetails.schedule.serviceTime2}
						</c:if>
						(<span class="precallZone"></span>)
						</div>
						<div
							style="width: 300px; padding-bottom: 12px; padding-right: 20px; display: block;margin-top: 10px;"
							id="custResponseDropdownDiv">
							<select id="timeWindowDropdown" name="timeWindowDropdown"
								onchange="onTimeWindowDropDownClick(this.id);"
								style="width: 260px;margin-left:0px;">
								<option value="-1">Select the customer response</option>
								<c:forEach items="${customerResponseCodesLst}" var="custResp">
									<option value="${custResp.id}">${custResp.descr}</option>
								</c:forEach>
							</select>
						</div>

						<div id="reminderAppointmentNotToday" class="infoWindow"
							style="width: 25%; padding-bottom: 2px; padding-left: 0px; display: block; float: right; margin-top: -30px;height: auto;width: 150px;border-color: #BBBBBB;border-style: solid;border-width: 6px 1px 1px;margin-right: 0px;">
							<div style="margin: 5px;">
								<i class="icon-lightbulb"
									style="font-size: large; background-color: #FFFF33;"></i>
							</div>
							<div style="margin-top: -30px; margin-left: 20px;font-size: 11px;">Customer
								will receive another confirmation call prior to service start
								date.</div>
						</div>
						
						<div id="reminderAppointmentNotTodayConfirmAppt" class="infoWindow"
							style="width: 25%; padding-bottom: 2px; padding-left: 0px; display: block; float: right; margin-top: -30px;height: 45px;width: 150px;border-color: #BBBBBB;border-style: solid;border-width: 6px 1px 1px;margin-right: 0px;">
							<div style="margin: 5px;">
								<i class="icon-lightbulb"
									style="font-size: large; background-color: #FFFF33;"></i>
							</div>
							<div style="margin-top: -30px; margin-left: 20px;font-size: 11px;">You may submit reschedule if customer prefers a different date.</div>
						</div>

						<div id="reminderAppointmentToday_schedule" class="infoWindow"
							style="padding-bottom: 12px; padding-left: 0px; padding-right: 10px; display: none; float: right; margin-top: 0px;height: 69px;width: 140px;">
							<div style="margin-top: 20px;">
								<i style="font-size: medium; color: #FF0000;"
									class="icon-warning-sign"></i>
							</div>
							<div style="margin-top: -30px; margin-left: 20px;font-size: 11px;">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="error.ordermanagement.so.precall.assign.eta" />
							</div>
						</div>

						<div id="etaWarningAppointmentToday_schedule" class="infoWindow"
							style="width: 25%; padding-bottom: 2px; padding-left: 0px; padding-right: 20px; display: none; float: right; margin-top: -50px;border-color: #BBBBBB;border-style: solid;border-width: 6px 1px 1px;">
							<div style="margin-top: 20px;">
								<i style="font-size: medium; color: #FF0000;"
									class="icon-warning-sign"></i>
							</div>
							<div style="margin-top: -30px; margin-left: 20px;font-size: 11px;">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="error.ordermanagement.so.precall.eta" />
							</div>
						</div>

					</div>
					<div id="scheduleTimeDiv"
						style="width: 400px; padding-bottom: 20px;padding-right: 20px; display: none;">
						<div style="float: left;">
							<span class="pre-call-common-hd">
							Service Window: 
							</span>
						</div>
						<div style="float: left;">
							<select id="scheduleFromTime"
								name="rscheduleFromTime" style="height: 20px;"
								onchange="setScheduleToTime('scheduleFromTime','scheduleToTime');">
								<option value="-1">-Select-</option>
								<c:forEach var="time" items="${time_intervals_updated_precall_new}">
									<option value="${time.descr}">${time.descr}</option>
								</c:forEach>
							</select> - 
							<select id="scheduleToTime" name="scheduleFromTime"
								style="width: 135px; height: 20px;" onchange="setValue(this.id);">
								<option value="-1">-Select-</option>
							</select>
						</div>
					</div>
					<div id="timeDiv" style="display: none;margin-top:5px;">
						<div style="padding-top: 5px;">
							<span class="pre-call-common-hd">Appointment Date:</span>&nbsp;
							${precallDetails.schedule.formattedServiceDate1}
							<c:if test="${precallDetails.schedule.formattedServiceDate2!=null}">
									&nbsp;-&nbsp;${precallDetails.schedule.formattedServiceDate2}
							</c:if>
						</div> 
						<div style="padding-top: 5px;">
							<span class="pre-call-common-hd"><c:choose><c:when test="${precallDetails.schedule.serviceTime2!=null}">Service Window:
							</c:when><c:otherwise>Service Time:</c:otherwise></c:choose></span>&nbsp;${precallDetails.schedule.serviceTime1}
							<c:if test="${precallDetails.schedule.serviceTime2!=null}">
								&nbsp;-&nbsp;${precallDetails.schedule.serviceTime2}
							</c:if>
							(<span class="precallZone"></span>) 
						</div>
						<div id="reminderAppointmentToday" class="infoWindow"
							style="width: 25%; padding-bottom: 7px; padding-left: 10px; padding-right: 20px; display: block; float: right; margin-top: -25px; background: #FFFFCC;">
							<div style="margin-top: 20px;">
								<i style="font-size: medium; color: #FF0000;"
									class="icon-warning-sign"></i>
							</div>
							<div style="margin-top: -30px; margin-left: 20px;font-size: 11px;">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="ordermanagement.so.precall.warning.message" />
							</div>
						</div>
						<div id="warningAppointmentToday" class="infoWindow"
							style="width: 25%; padding-bottom: 12px; padding-left: 10px; padding-right: 20px; display: none; float: right; margin-top: -25px; background: #FFFFCC;">
							<div style="margin-top: 20px;">
								<i class="icon-warning-sign" style="font-size: medium; color:red;"></i>
							</div>
							<div style="margin-top: -30px; margin-left: 20px;font-size: 11px">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="error.ordermanagement.so.precall.assign.eta" />
							</div>
						</div>
						<div id="assignWarningAppointmentToday" class="infoWindow"
							style="width: 25%; padding-bottom: 12px; padding-left: 10px; padding-right: 20px; display: none; float: right; margin-top: -25px; background: #FFFFCC;">

							<div style="margin-top: 20px;">
								<i class="icon-warning-sign"
									style="font-size: medium; color: #FF0000;"></i>
							</div>
							<div style="margin-top: -30px; margin-left: 20px;font-size: 11px">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="error.ordermanagement.so.precall.assign" />
							</div>
						</div>
						<div id="etaWarningAppointmentToday" class="infoWindow"
							style="width: 25%; padding-bottom: 12px; padding-left: 5px; padding-right: 20px; display: none; float: right; margin-top: -25px; background: #FFFFCC;">
							<div style="margin-top: 20px;">
								<i class="icon-warning-sign"
									style="font-size: medium; color: #FF0000;"></i>
							</div>
							<div style="margin-left: 20px; margin-top: -20px; font-size: 11px;">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="error.ordermanagement.so.precall.eta" />
							</div>
						</div>

					</div>
					<div id="etaDiv"
						style="width: 300px; padding-bottom: 15px; padding-top: 10px; display: none;">
						<div style="float: left;margin-top:2px;">
							<span class="pre-call-common-hd preCallEtaToolTipInfoIcon" style="font-weight:bold">ETA</span>
						</div>
						<div style="float: left;margin-left: 10px;">
							<select
								id="etaDropdown" name="etaDropdown"
								style="width: 92px; height: 20px;" onchange="setValue(this.id);">
								<option value="-1">-Select-</option>
								<c:forEach var="time" items="${time_intervals_updated}">
									<option value="${time.descr}">${time.descr}</option>
								</c:forEach>
							</select>
						</div>
						<div id="preCallEtaToolTip" class="preCallEtaToolTipInfo" style="margin-top: 25px;">
							<p style="padding-left: 5px;font-size:12px;">
							Estimated time of arrival at service location
							</p>
						</div>
					</div>

					<div id="rescheduleDiv"
						style="width: 300px; padding-bottom: 12px; padding-left: 5px; padding-right: 20px; display: none;">
						<table width="60%" style="border: 1px solid #BBBBBB;">
							<tr>
								<td width="50%">
									<table>
										<tr>
											<td width="50%" colspan="2">
												<div style="margin-top: 3px;margin-bottom: 7px;">
													<span class="pre-call-common-hd" style="color:black;">Reschedule</span>
												</div>
												<!-- 
												<c:if test="${reschedule.rescheduleServiceDateTime1 != null}">
												<div class="darkRed" style="padding:2px 2px 4px;font-weight: bold;text-align: justify;">A
															rechedule request is already pending. The current request
															will overwrite the previous one.</div>
												</c:if>
												 -->
											</td>
										</tr>
										<tr>
											<td width="25%" height="25px;">
												<div style="float: left;">
													<input type="radio" id="preCallRangeDate"
												name="precallRangeOfDates" checked="checked" value="2"
												onclick="showPrecallRescheduleRangeDate()" />
												</div>
												<div style="float: left;margin-left: 5px;">
													Date Range
												</div>
											</td>
											<td width="25%">
											<div style="float: left;">
													<input type="radio" id="preCallSpecificDate"
												name="precallRangeOfDates"  value="1"
												onclick="javascript:showPrecallRescheduleFixedDate()" />
												</div>
												<div style="float: left;margin-left: 5px;">
													Specific
												Date and Time
												</div>
											</td>
										</tr>
										<tr>
											<td width="50%" colspan="2">
												<div id="rescheduleSpecificDataAndTimeDiv">
													<table style="" width="100%">
														<tr>
															<td width="50%" height="20px"><span class="pre-call-common-hd">Appointment Date: </span></td>
															<td width="50%"><span class="pre-call-common-hd">Service Time: </span></td>
														</tr>
														<tr>
															<td width="50%" height="25px;">
																<!-- <input id="rescheduleSpecificDateTxt" name="rescheduleSpecificDateTxt" type="text" style="width: 90px" value=""/>-->
																<input type="text" class="rescheduleDateField shadowBox" style="height: 16px;width: 145px;"
																id="precallSpecificRescheduleFromDate"
																name="precallSpecificRescheduleFromDate" value=""
																onfocus="showDatepicker(this.id);"
																onchange="setValue(this.id);" />
															</td>
															<td width="50%"><select
																id="precallSpecificTimeDropdown"
																name="precallSpecificTimeDropdown"
																style="width: 150px; height: 20px;"
																onchange="setValue(this.id);">
																	<option value="-1">Select</option>
																	<c:forEach var="time"
																		items="${time_intervals_updated}">
																		<option value="${time.descr}">${time.descr}</option>
																	</c:forEach>
															</select></td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
										<tr>
											<td width="50%" colspan="2">
												<div id="rescheduleDateRangeDiv">
													<table style="border: " width="100%">
														<tr>
															<td width="50%"><span class="pre-call-common-hd">Appointment Date:</span></td>
															<td width="50%"></td>
														</tr>
														<tr>
															<td width="50%" height="35px;">
																<!-- <input id="rescheduleFromDateRangeTxt" name="rescheduleFromDateRangeTxt" type="text" style="width: 120px" />-->
																<input type="text" class="rescheduleDateField shadowBox" style="width: 145px;"
																id="precallRangeRescheduleFromDate"
																name="precallRangeRescheduleFromDate" value=""
																onfocus="showDatepicker(this.id);"
																onchange="setValue(this.id);" />
															<td width="50%">
																<input type="text" class="rescheduleDateField shadowBox" style="width:145px;margin-left:5px"
																id="precallRangeRescheduleToDate"
																name="precallRangeRescheduleToDate" value=""
																onfocus="showDatepicker(this.id);"
																onchange="setValue(this.id);" />
															</td>
														</tr>
														<tr>
															<td width="50%"><span class="pre-call-common-hd">Service Window:</span> </td>
															<td width="50%"></td>
														</tr>
														<tr>
															<td width="50%"><select
																id="rescheduleRangeDateStartTimeDropdown"
																name="rescheduleRangeDateStartTimeDropdown"
																style="width: 150px; height: 20px;"
																onchange="setScheduleToTime('rescheduleRangeDateStartTimeDropdown','rescheduleRangeDateEndTimeDropdown');">
																	<option value="-1">-Select-</option>
																	<c:forEach var="time"
																		items="${time_intervals_updated}">
																		<option value="${time.descr}">${time.descr}</option>
																	</c:forEach>
															</select></td>
															<td width="50%"><select
																id="rescheduleRangeDateEndTimeDropdown"
																name="rescheduleRangeDateEndTimeDropdown"
																style="width: 150px; height: 20px;"
																onchange="setValue(this.id);">
																	<option value="-1">-Select-</option>
															</select> <!--
													<span style="float: left;">
															<input type="text" class="shadowBox rescheduleDateField"
															id="rescheduleToDate" name="rescheduleDTO.newEndDate"
															value="" onfocus="showDatepicker(this.id);"/>
													</span>
													--></td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
										<tr>
											<td colspan='2' width="50%" height="">
												<div style="height: 20px;padding-top: 15px;">
											<span class="pre-call-common-hd">Reason for
													rescheduling:</span>
												</div>	
													</td>
										</tr>
										<tr>
											<td colspan='2' width="50%" height="25px;"><select
												id="reasonForRescheduleDropdown"
												name="reasonForRescheduleDropdown"
												style="width: 250px; height: 20px;"
												onchange="setValue(this.id);">
													<option value="-1">Select</option>
													<c:forEach var="rescheduleReasonCodes"
														items="${rescheduleReasonCodesLst}">
														<option value="${rescheduleReasonCodes.id}">${rescheduleReasonCodes.descr}</option>
													</c:forEach>

											</select></td>
										<tr>
											<td colspan='2' width="50%" height="20px;"><span class="pre-call-common-hd">Comments:</span> </td>
										</tr>
										<tr>
											<td colspan='2' width="50%"><textarea
													id="rescheduleCommentsTextarea"
													name="rescheduleCommentsTextarea"
													id="rescheduleCommentsTextarea" style="width: 250px;height: 45px;"
													rows="4" cols="10" onchange="setValue(this.id);"></textarea>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</div>
										
					<div style="width: 70%; display: none;margin-top: 15px;margin-bottom: 5px;padding-bottom: 15px;"
						id="assignProvidersDiv">
						<div>
							<div style="float: left;">
								<span class="pre-call-common-hd">Assigned provider :</span> 
							</div>
							<div id="assignedProviderDiv" style="">
								<div style="float: left;padding-left: 3px;">
								<c:if test="${resourceAssigned == 'true'}">
								<span style="color: black">
								${assignProvName}&nbsp;
								</span>
								</c:if>
								<c:if test="${resourceAssigned != 'true'}">
								
									<span>
										un-assigned&nbsp;
									</span> 
								</c:if>
								</div>
								<div style="margin-left: 5px;padding-top: 2px;">
									<a href="javascript:void(0);" class="link precall-link"> <c:if
											test="${resourceAssigned == 'true' && (routedProviderList!=null && (fn:length(routedProviderList)>0))}">
											<span id="reassignProviderLink" name="reassignProviderLink"
												onclick="loadAssignProviderForPreCall(event,this.id);">Re-assign</span>
										</c:if> <c:if test="${resourceAssigned != 'true'}">
											<span id="assignProviderLink" name="assignProviderLink"
												onclick="loadAssignProviderForPreCall(event,this.id);">Assign</span>
										</c:if>
									</a> 
								</div>	
							</div>
						</div>
						<div style="float: left;">
						<select id="assignProviderDropdown"
							name="assignProviderDropdown"
							style="width: 210px; display: none;margin-bottom: 10px;">
							<option value="-1">-Select-</option>
							<c:forEach var="routedAssignedProviders"
								items="${routedProviderList}">
								<option value="${routedAssignedProviders.resourceId}">${routedAssignedProviders.providerFirstName}
									${routedAssignedProviders.providerLastName}&nbsp;(${routedAssignedProviders.distancefromSOLocation} mi)</option>
							</c:forEach>
						</select>
						</div> 
					</div>
					<div id="priority"></div>
				</div>
				</div>
				<div class="modal-footer" style="font-family: Arial;margin-top: 0px;">
						<input type="button" id="savePrecallBtn" value="Save"
							class="actionButton"
							style="float: right; margin: 5px 15px 5px 5px; display: block;" />
				</div>
			</div>
		</form>
</div>
</body>
</html>