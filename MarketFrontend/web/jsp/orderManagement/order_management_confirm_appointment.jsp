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
	<link href="${contextPath}/javascript/CalendarControl.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${contextPath}/javascript/CalendarControl.js"></script>
	
	<script type="text/javascript">

		function loadAssignProvider(event,id){
			$("#assignProviderDropdown").css("display","block");
			if(id == 'assignProviderLink'){
				$("#assignProviderLink").css("display","none");
			}
			if(id == 'reassignProviderLink'){
				$("#reassignProviderLink").css("display","none");
			}
			$("#assignProviderDropdown").val('-1');
		}
		
		function showPrecallRescheduleFixedDate() {
    		//$("#rescheduleErrorMsg").html("");
    		$("#rescheduleSpecificDataAndTimeDiv").show();
    		$("#rescheduleDateRangeDiv").hide();
    	
		}

		function showPrecallRescheduleRangeDate() {
			//$("#rescheduleErrorMsg").html("");
			$("#rescheduleSpecificDataAndTimeDiv").hide();
    		$("#rescheduleDateRangeDiv").show();
			//document.getElementById('rescheduleDateRanged').style.display="none";
		}
		function viewCallHistory(e) {
			var x = e.pageX;
			if (x == undefined) {
				x = e.offsetX;
			}
			var y = e.pageY;
			if (y == undefined) {
				y = e.offsetY;
			}
			$("#precallHistoryModel").addClass("jqmWindow");
			$("#precallHistoryModel").css("width", "150px");
			$("#precallHistoryModel").css("height", "auto");
			$("#precallHistoryModel").css("marginLeft", "45px");
			$("#precallHistoryModel").css("top", y - 45);
			$("#precallHistoryModel").jqm({
				modal : true,
				overlay : 0
			});
			$("#precallHistoryModel").css("border-left-color", "#A8A8A8");
			$("#precallHistoryModel").css("border-right-color", "#A8A8A8");
			$("#precallHistoryModel").css("border-bottom-color", "#A8A8A8");
			$("#precallHistoryModel").css("border-top-color", "#A8A8A8");
			$("#precallHistoryModel").jqmShow();
		}
	
	</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
</head>
<body>
<form id="updateConfirmAppointmentForm" action="#" name="updateConfirmAppointment" >
<c:set var="isToday" value="false"></c:set>
<c:set var="resourceAssigned" value="false"></c:set>
<input id="soIdhidden" name="precallScheduleUpdateDTO.soId" type="hidden" value='${precallDetails.orderstatus.soId}'/>
<input id="assignResourceIdhidden" name="precallScheduleUpdateDTO.assignedResourceId" type="hidden" value=""/>
<input id="customerAvailableOrNothidden" name="precallScheduleUpdateDTO.customerAvailableFlag" type="hidden" value=""/>
<input id="customerNotAvailableReasonIdhidden" name="precallScheduleUpdateDTO.customerNotAvalableReasonCode" type="hidden" value=""/>
<input id="etahidden" name="precallScheduleUpdateDTO.eta" type="hidden" value=""/>
<input id="serviceLocationNotesEditFlaghidden" name="precallScheduleUpdateDTO.locationNotesFlag" type="hidden" value="false"/>
<input id="specialInstructionsEditFlaghidden" name="precallScheduleUpdateDTO.specialInstructionEditFlag" type="hidden" value="false"/>
<input id="serviceLocationNoteshidden" name="precallScheduleUpdateDTO.locationNotes" type="hidden" value=""/>
<input id="specialInstructionshidden" name="precallScheduleUpdateDTO.specialInstructions" type="hidden" value=""/>
<input id="timeWindowhidden" name="precallScheduleUpdateDTO.timeResponseWindow" type="hidden" value=""/>
<input id="fromTimehidden" name="precallScheduleUpdateDTO.startTime" type="hidden" value=""/>
<input id="toTimehidden" name="precallScheduleUpdateDTO.endTime" type="hidden" value=""/>
<input id="specificOrRangeValhidden" name="precallScheduleUpdateDTO.specificOrDateRange" type="hidden" value=""/>
<input id="rescheduleFromDatehidden" name="rescheduleDTO.newStartDate" type="hidden" value=""/>
<input id="rescheduleFromTimehidden" name="rescheduleDTO.newStartTime" type="hidden" value=""/>
<input id="rescheduleToDatehidden" name="rescheduleDTO.newEndDate" type="hidden" value=""/>
<input id="rescheduleToTimehidden" name="rescheduleDTO.newEndTime" type="hidden" value=""/>
<input id="rescheduleReasonCodehidden" name="rescheduleDTO.reasonCode" type="hidden" value=""/>
<input id="rescheduleCommenthidden" name="rescheduleDTO.rescheduleComments" type="hidden" value=""/>

<c:forEach var="contact" items="${precallDetails.contacts.contactList}">
	<c:if test="${contact.contactLocnType=='Service'}">
		<c:set var="firstName" value="${contact.firstName}"/>
		<c:set var="lastName" value="${contact.lastName}"/>
		<c:set var="primaryPhone" value="${contact.primaryPhone.number}"/>
		<c:set var="altPhone" value="${contact.altPhone.number}"/>
	</c:if>
</c:forEach>

<div class="modal">
	
	<input type="hidden" id="resourceAssigned" value="${resourceAssigned}" />
	<input type="hidden" id="todayInd" value="${isToday}" />
	<i class="icon-remove" title="Close" id="closeConfirmAppointmentModel"></i>
	<h2 class="modal-title">Confirm Appointment Window - Call the customer</h2>
	<div class="modal-content">
		<p>
		<strong>${firstName} ${lastName} </strong> <a href="#" class="link" style="text-align: left;" onclick="viewCallHistory(event);" id="viewCallHistoryLink">View call history</a>
		</p>
		<p>
		<i class="icon-phone"></i><strong>Primary: </strong> ${primaryPhone} <br><i class="icon-phone"></i><strong>Alternate:</strong> ${altPhone}
		</p>
		<!--  Start of Precall History Popup -->
		<div id="precallHistoryModel" style="width: 75px; float: left; display:none;background:#eeeeee">
			<i class="icon-remove" title="Close" id="closePrecallHistoryModel"></i>
			<h2 class="modal-title">Pre Call History</h2>
			<c:forEach var="preCallHistory" items="${precallHistoryDetails}">
				${preCallHistory.day} ${preCallHistory.date} 
				<br> 
				${preCallHistory.scheduleStatus}
				<br>
			</c:forEach>
		</div>
		<!--  End of Precall History Popup -->
		<p>
		<input type="checkbox" name="custavailablechkbox" id="custavailablechkbox" value="" > Customer not available
		</p>
		<div id="customerNotAvailableErrorMsg" class="rescheduleErrorMsg">
			
		</div>
		<p>
		<div id="custNotAvailableReasonDropDownDiv"  style="display: none;padding-top: 10px;">
			     Select the reason for not completing the call :  
				 <select id="custNotAvailableReasonDropdown" name="custNotAvailableReasonDropdown" style="width: 200px; height: 20px;">
					<option value="-1">Select</option>
					<option value="1">Reason2</option>
					<option value="2">Reason3</option>
					<option value="3">Reason4</option>
				</select>
		</div>
		</p>
		<div class="accordionButton" id="confirmOrderAccordion">
				<h3 class="accordion-title">Confirm the Order</h3>
		
		<div id="confirmOrderDiv">
				<strong>
				${precallDetails.sectionGeneral.title}
				</strong>
				<br>
				S.O. # ${precallDetails.orderstatus.soId}
				<br>
				<br><strong>
				Service Location:
				</strong>
				<br>
				${precallDetails.serviceLocation.address1}
				<br>
				${precallDetails.serviceLocation.address2}
				<br>
				${precallDetails.serviceLocation.city} , ${precallDetails.serviceLocation.state} ${precallDetails.serviceLocation.zip}
				
				<br>
				<a href="#" class="link" style="text-align: left;" id="viewServiceLocationNotesLink">Service location notes</a>
				<br>
				<br>
				<div id="serviceLocationNotesDiv"  style="display: none;" >
					Notes : <a href="#" class="link" style="text-align: left;" id="viewLocationNotesLink">Edit</a>
					<br>
					<textarea id="seviceLocationNotes" rows="4" cols="20" name="seviceLocationNotes" style="width: 500px;" disabled>${precallDetails.serviceLocation.note}</textarea>
				</div>
		</div>
		</div>
		<div class="accordionButton" id="confirmServiceAccordion">
			<h3 class="accordion-title">Confirm the Service</h3>
		</div>
		<c:set var="countTask" value="1"/>
		<div id="confirmServiceDiv">
			
			<c:forEach var="task" items="${precallDetails.scopeOfWork.tasks.taskList}">
				<strong>Task ${countTask}</strong> <c:set var="countTask" value="${countTask+1}"/>
				<br>
				${task.taskName} (${task.categoryID} -> ${task.serviceType})
				<br>
			
			</c:forEach>
			
			<br>
			<strong>Special Instuctions</strong> <a href="#" class="link" style="text-align: left;" id="viewSpecialIntructionsLink">Edit</a>
			<br>
			<textarea rows="4" cols="20" id="specialIntructions" name="specialIntructions" style="width: 500px;" disabled>${precallDetails.sectionGeneral.specialInstructions}</textarea>
			<br>
			<a href="#" class="link" style="text-align: left;" id="viewProductInfoLink">Product Info</a>
			<br>
			<div id="productInfoDiv"  style="display: none;" >
				<c:forEach var="customReference" items="${precallDetails.customReferences.customRefList}">
					<c:if test="${customReference.name == 'MERCHANDISE CODE' || customReference.name == 'BRAND'}">			
						${customReference.name} : ${customReference.value}
						<br>
					</c:if>	
				</c:forEach>
			</div>	
		
		</div>
		<br />
		<div class="accordionButton" id="confirmScheduleAccordion"> 
			<h3 class="accordion-title">Confirm the Schedule</h3>
		</div>
		<div id="errorDiv" class="preCallErrorBox" style="display: block;">
		<i class="icon-warning-sign"></i>The appointment is today. You must confirm the time window with the customer and assign a provider.
		</div>
		<div style="width: 300px; padding-bottom: 12px;padding-left:5px; padding-right:20px;display: block;" id="confirmScheduleDiv">
			<b>Appointment&nbsp;Date:</b>&nbsp;${precallDetails.schedule.serviceDateTime1}&nbsp;-&nbsp;${precallDetails.schedule.serviceDateTime2}
			<br>
			<b>Scheduled&nbsp;Time:</b>&nbsp;${precallDetails.schedule.serviceDateTime1}&nbsp;-&nbsp;${precallDetails.schedule.serviceDateTime2}
			<br>
		</div>
		<div style="width: 300px; padding-bottom: 12px;padding-left:5px; padding-right:20px;display: block;" id="custResponseDropdownDiv">
			<select id="timeWindowDropdown" name="timeWindowDropdown" style="width: 200px; height: 20px;">
				<option value="-1">Select the customer response</option>
				<option value="1">Current data and time confirmed</option>
				<option value="2">Same date but different time</option>
				<option value="3">Different date (Reshedule)</option>
			</select>
		</div>
		<div id="scheduleTimeDiv" style="width: 300px; padding-bottom: 12px;padding-left:5px; padding-right:20px;display: none;" >
			Scheduled Time:
			<select id="scheduleFromTime" name="rescheduleDTO.startTime" style="width: 75px; height: 20px;">
				<option value="-1">Select</option>
				<option value="8:00 AM">8:00 AM</option>
			</select>
			-
			<select id="scheduleToTime" name="rescheduleDTO.endTime" style="width: 75px; height: 20px;">
				<option value="-1">Select</option>
				<option value="8:00 AM">8:00 AM</option>
			</select>
		</div>
		<div id="etaDiv" style="width: 300px; padding-bottom: 12px;padding-left:5px; padding-right:20px;display: none;" >
			ETA :
			<select id="etaDropdown" name="etaDropdown" style="width: 75px; height: 20px;">
				<option value="-1">Select</option>
				<option value="8:00 AM">8:00 AM</option>
			</select>
		</div>
		<div id="rescheduleErrorMsg" class="rescheduleErrorMsg">
		</div>
		<div id="precallErrorMsg" class="precallErrorMsg">
		</div>
		<div id="rescheduleDiv" style="width: 300px; padding-bottom: 12px;padding-left:5px; padding-right:20px;display: none;" >
			<table width="60%" style="border:1px solid black; style="border:1px solid black;display: none;">
				<tr>
					<td width="50%">
						<table>
									<tr>
										<td width="50%" colspan="2">Reschedule</td>
										<%-- <c:if test="${reschedule.rescheduleServiceDateTime1 != null}">
											<div class="darkRed"
												style="padding: 2px 2px 4px; font-weight: bold; text-align: justify;">A
												rechedule request is already pending. The current request
												will overwrite the previous one.</div>
										</c:if> --%>
									</tr>

									<tr>
								<td width="25%">
									<!-- <input type="radio" value="1" name="rescheduleRadio" />Specific Date & Time-->
									<input type="radio" id="preCallSpecificDate" name="precallRangeOfDates" checked="checked"
											value="1" onclick="javascript:showPrecallRescheduleFixedDate()" />Specific Date and Time
								</td>
								<td width="25%">
									<!-- <input type="radio" value="2" name="rescheduleRadio"/>Date Range-->
									<input type="radio" id="preCallRangeDate" name="precallRangeOfDates"
											value="2" onclick="showPrecallRescheduleRangeDate()" />Date Range
								</td>	
								</tr>
							<tr>
								<td width="50%" colspan="2">
									<div id="rescheduleSpecificDataAndTimeDiv">
										<table style="border:1px solid black;" width="100%">
											<tr>
												<td width="50%">Appointment Date:</td>
												<td width="50%">Scheduled Time:</td>
											</tr>
											<tr>
												<td width="50%">
												<!-- <input id="rescheduleSpecificDateTxt" name="rescheduleSpecificDateTxt" type="text" style="width: 90px" value=""/>-->
												<input type="text" class="rescheduleDateField shadowBox" 
													id="precallSpecificRescheduleFromDate" name="rescheduleDTO.newStartDate"
													value="" onfocus="showDatepicker(this.id);"/>
												</td>
												<td width="50%">
													<select id="precallSpecificTimeDropdown" name="rescheduleDTO.newStartTime" style="width: 150px; height: 20px;">
														<option value="-1">Select</option>
														<option value="8:00 AM">8:00 AM</option>
													</select>
												</td>
											</tr>
										</table>
									</div>		
								</td>
							</tr>
							<tr>
								<td width="50%" colspan="2">
									<div id="rescheduleDateRangeDiv" >
										<table style="border:1px solid black;" width="100%">
											<tr>
												<td width="50%">Appointment Date:</td>
												<td width="50%"></td>
											</tr>
											<tr>
												<td width="50%">
												<!-- <input id="rescheduleFromDateRangeTxt" name="rescheduleFromDateRangeTxt" type="text" style="width: 120px" />-->
												<input type="text" class="rescheduleDateField shadowBox" 
													id="precallRangeRescheduleFromDate" name="rescheduleDTO.newStartDate"
													value="" onfocus="showDatepicker(this.id);"/>
												<td width="50%">
												<!-- <input id="rescheduleToDateRangeTxt" name="rescheduleToDateRangeTxt" type="text" style="width: 120px" />-->
												<input type="text" class="rescheduleDateField shadowBox" 
													id="precallRangeRescheduleToDate" name="rescheduleDTO.newEndDate"
													value="" onfocus="showDatepicker(this.id);"/>
											    </td>
												</td>
											</tr>
											<tr>
												<td width="50%">Scheduled Time:</td>
												<td width="50%"></td>
											</tr>
											<tr>
												<td width="50%">
													<select id="rescheduleRangeDateStartTimeDropdown" name="rescheduleDTO.newStartTime" style="width: 150px; height: 20px;">
														<option value="-1">Select</option>
														<option value="8:00 AM">8:00 AM</option>
													</select>
												</td>
												<td width="50%">
													<select id="rescheduleRangeDateEndTimeDropdown" name="rescheduleDTO.newEndTime" style="width: 150px; height: 20px;">
														<option value="-1">Select</option>
														<option value="8:00 AM">8:00 AM</option>
													</select>
													<!--
													<span style="float: left;">
															<input type="text" class="shadowBox rescheduleDateField"
															id="rescheduleToDate" name="rescheduleDTO.newEndDate"
															value="" onfocus="showDatepicker(this.id);"/>
													</span>
													-->
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan='2' width="50%">Reason for rescheduling:</td>
							</tr>
							<tr>
								<td colspan='2' width="50%">
									<select id="reasonForRescheduleDropdown" name="reasonForRescheduleDropdown" style="width: 250px; height: 20px;">
										<option value="-1">Select</option>
										<c:forEach var="rescheduleReasonCodes" items="${rescheduleReasonCodesLst}">
											<option value="${rescheduleReasonCodes.id}">${rescheduleReasonCodes.descr}</option>
				 						</c:forEach>
										
									</select>
								</td>
							<tr>
								<td colspan='2' width="50%">Comments:</td>
							</tr>
							</tr>
							<tr>
								<td colspan='2' width="50%">
									<textarea id="rescheduleCommentsTextarea" name=id="rescheduleCommentsTextarea" style="width: 250px"
														rows="4" cols="10"></textarea>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>				
		</div>
		<br>
	
		
		<div id="reminderDiv" style="width: 300px; padding-bottom: 12px;padding-left:5px; padding-right:20px;display: none;">
			<div id="reminderAppointmentNotToday" class="preCallErrorBox" style="width: 200px; padding-bottom: 12px;padding-left:20px; padding-right:20px;display: none;">
				Customer will receive another confirmation call prior to service start date.
			</div>
			<div id="reminderAppointmentToday" class="preCallErrorBox" style="width: 200px; padding-bottom: 12px;padding-left:20px; padding-right:20px;display: none;font-size:11px;">
				<i class="icon-warning-sign"></i>The appointment is today. You must confirm the time window with the customer and assign a provider.
			</div>
		</div>
		<div style="width:400px; padding-bottom: 12px;padding-left:5px; padding-right:20px;display: block;" id="assignProviderDiv">
			<strong>Assigned provider :</strong>
			<span style="color:red">
				<c:if test="${resourceAssigned == 'true'}">${assignProvName}&nbsp;</c:if>
				<c:if test="${resourceAssigned != 'true'}">un-assigned&nbsp;</c:if>
			</span>
			<a href="#" style="text-decoration: underline;">
				<c:if test="${resourceAssigned == 'true'}">
					<span id="reassignProviderLink" name="reassignProviderLink" onclick="loadAssignProvider(event,this.id);">Reassign</span>
				</c:if>
				<c:if test="${resourceAssigned != 'true'}">
					<span id="assignProviderLink" name="assignProviderLink" onclick="loadAssignProvider(event,this.id);">Assign</span>
				</c:if>
			</a>	
			<select id="assignProviderDropdown" name="assignProviderDropdown" style="width: 200px;display: none;">
				<option value="-1">Select</option>
				<c:forEach var="routedAssignedProviders" items="${routedProviderList}">
					<option value="${routedAssignedProviders.resourceId}">${routedAssignedProviders.providerFirstName} ${routedAssignedProviders.providerLastName} - ${routedAssignedProviders.distancefromSOLocation} </option>
				 </c:forEach>
			</select>
		</div>

		<div id="priority"></div>
	</div>
<div class="modal-footer">
<input type="button" id="savePrecallBtn" value="Save" class="actionButton" style="float: right;margin:5px 15px 5px 5px;"/>
</div>
</form>
</body>
</html>