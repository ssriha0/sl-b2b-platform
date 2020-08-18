<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
jQuery(document).ready(function (){
	//Clearing form values
	$("#service").val("");	
	$("#estimation").val("");
	$("#scheduledDate").val("");
	$("#scheduledStartTime").val("");
	$("#scheduledEndTime").val("");
	$("#ErrorMessage").hide();
});

function submitSchedule(){
	if(validateScheduleLead()==true){
	   $("#ErrorMessage").hide();
	  
       var formValues = $('#RescheduleLead').serializeArray();
       var leadId = '${lmTabDTO.lead.leadId}';
       var url = 'leadsManagementController_scheduleLead.action?leadId='+leadId;
       $.ajax({
            url: url,
            type: "POST",
            data: formValues,
            success: function(data) {
            	if(data.responseMessage == 'Invalid Scheduled Start Time'){
            		$("#ErrorMessage").html("Schedule start time must be greater than current time");
            		$("#ErrorMessage").show();
            		$("#scheduleSave").attr("disabled", false);
            	}else if(data.responseMessage=='Invalid Scheduled Start End Time'){
            		$("#ErrorMessage").html("Schedule start time cannot be  greater than end time");
            		$("#ErrorMessage").show();
            		$("#scheduleSave").attr("disabled", false);
            	}else if(data.responseMessage=='Your appointment has been saved.'){
            	   window.location.href = '/MarketFrontend/leadsManagementController_viewLeadDetails.action?leadId='+leadId;
            	}else{
            		
            		$("#scheduleSave").attr("disabled", false);
            	}
            },
            error: function(xhr, status, err) {
            	$("#sucessDiv").html("<div  class='successBox'><p  style='text-align:right'>Unable To Schedule/Reschedule the Lead </p></div>");
			    $("#sucessDiv").show();
           }             
        });
     }else{
    	 $("#scheduleSave").attr("disabled", false);
     }
}
function validateScheduleLead(){
	$("#scheduleSave").attr("disabled", true);
	$("#ErrorMessage").html("");
	var scheduledDate = $("#scheduledDate").val();	
	if(scheduledDate == '' || scheduledDate == null){
		$("#ErrorMessage").html("Please select a date");
		$("#ErrorMessage").show();
		return false;
	}
	var startTime = $("#scheduledStartTime").val();	
	if(startTime == '' || startTime == null){
		$("#ErrorMessage").html("Please select a start Time");
		$("#ErrorMessage").show();
		return false;
	}
	
	var endTime = $("#scheduledEndTime").val();
	if(endTime == '' || endTime == null){
		$("#ErrorMessage").html("Please select a end Time");
		$("#ErrorMessage").show();
		return false;
	}
	var reason1 = $("#service").val();
	var reason2 = $("#estimation").val();
    /*Validation of reasoncodes selected is not working sometimes and it shows the value as "on"
    Hence changing the code to incorporate it*/
    if((reason1 == '' || reason1  == null || reason1=='on')
        &&(reason2 == '' || reason2  == null || reason2=='on') ){
		$("#ErrorMessage").html("Please select a reason");
		$("#ErrorMessage").show();
		return false;		
	}
	
	return true;
}

function checkToday(scheduledDate){
	var inputDate = new Date(scheduledDate);
	var todaysDate = new Date();
	return inputDate.getDate() == todaysDate.getDate() && inputDate.getMonth() == todaysDate.getMonth() && inputDate.getFullYear() == todaysDate.getFullYear();
}
//Setting reason properly
function changeReason(reason){
	if(reason == 'service'){
	  $("#service").val("Service");	
	  $("#estimation").val("");
	}else if(reason== 'estimation'){
	  $("#estimation").val("Estimation");
	  $("#service").val("");	
	}else{
		$("#service").val("");	
		$("#estimation").val("");
	}
}

</script>
<style type="text/css">
.datepickerSchedule{
  background-color: #FFFFFF !important;
  cursor: default !important;
}
</style>
<c:set var="modalID" value="scheduleWidget" />
<c:set var="modalAria" value="scheduleWidgetTitle"/>
<c:set var="modalBtnText" value="Save"/>
<c:choose>
		<c:when test="${lmTabDTO.lead.leadStatus== 'scheduled'}">
		  	<c:set var="modalTitle" value="Update Appointment"/>
		 </c:when>
		 <c:otherwise>
			 <c:set var="modalTitle" value="Schedule a Service Appointment"/>
		 </c:otherwise>
</c:choose>
		<div class="modal fade" id="${modalID}" tabindex="-1" role="dialog" aria-labelledby="${modalAria}" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="clearScheduleForm();">&times;</button>
			        <h3 class="modal-title" id="${modalAria}" >${modalTitle}</h3>
			      </div>
			  <div class="modal-body">
                <div id="ErrorMessage" class="buyerLeadError" style="display: none;"></div>
				<form id="RescheduleLead">
				      <div class="row">
				        <div class="col-sm-4">
				      	   <label for="date">
				      	     Date
				      	   </label>
				      	</div>
				      	<div class="col-sm-8">
				      	 <input type="text" class="datepickerSchedule" id="scheduledDate" name="scheduledDate">
				      	</div>
				      	</div><!-- /.row -->
				      	<div class="row padding-top">
			      			<div class="col-sm-4">
								<label for="startTime">Time Window</label>
				      		</div>
				      		<div class="col-sm-8">
				      			<div class="row">
				      				<div class="col-xs-6 hyphen">
				      					<input type="text" class="timepicker" id="scheduledStartTime" name="scheduledStartTime">
				      				</div>
				      				<div class="col-xs-6">
				      					<input type="text" class="timepicker" id="scheduledEndTime" name="scheduledEndTime">
				      				</div>
				      			</div><!-- /.row -->
				      		</div>
				      	</div><!-- /.row -->
						<div class="row padding-top">
			      			<div class="col-sm-4">
				      	        <label >What is the reason for the site visit?</label>
				      	    </div>
	                       <div class="col-sm-8">
			                    <div class="btn-group btn-group-justified" data-toggle="buttons">
	                                <label class="btn btn-default" for="estimation" onclick="changeReason('estimation');">
	                                   <input type="radio" name="reason" id="estimation" >Estimation
	                               </label>
	                               <label class="btn btn-default" for="service" onclick="changeReason('service');">
	                                   <input type="radio" name="reason" id="service">Service
	                               </label>
	                           </div>
	                       </div>    
	                   </div><!-- /.row -->
	               </form>
	              		</div><!-- /.modal-body -->
			      <div class="modal-footer">
			        <button type="button" id="scheduleSave" class="btn btn-default" type="submit" onclick="submitSchedule();">${modalBtnText}</button>
			      </div>
			    </div><!-- /.modal-content -->
			  </div><!-- /.modal-dialog -->
			</div><!-- /.modal -->
			
<script type="text/javascript">
$('#scheduleWidget').on('hidden.bs.modal', function (e) {
	clearScheduleForm();
});

function clearScheduleForm(){
	$("#service").val("");	
	$("#estimation").val("");
	$("#scheduledDate").val("");
	$("#scheduledStartTime").val("");
	$("#scheduledEndTime").val("");
	$("#ErrorMessage").hide();
	if($("label[for='estimation']").hasClass('active')){
    	$("label[for='estimation']").removeClass('active');
    }
    if($("label[for='service']").hasClass('active')){
    	$("label[for='service']").removeClass('active');
    }
}
</script>