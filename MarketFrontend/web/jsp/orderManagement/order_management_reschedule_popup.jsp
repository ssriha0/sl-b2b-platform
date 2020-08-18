<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script type="text/javascript">
var wasSubmitted=false;
	/*Request a Reschedule Popup: When Submit button is clicked.*/
	$(document).ready(function() {
	

		$("#rescheduleErrorMsg").html("");
		$("#rescheduleErrorMsg").removeClass("errorBox");
		$("#rescheduleDateRanged").show();
			
	});

	function checkBeforeSubmit(){
			      if(!wasSubmitted) {
			        wasSubmitted = true;
			        return wasSubmitted;
			      }
			      return false;
			    }

	/*Request a Reschedule Popup: When Submit butten is clicked.*/
	$('#rescheduleBtn').click(function(){
		
		$("#rescheduleErrorMsg").html("");
		$("#rescheduleErrorMsg").removeClass("errorBox");
		var warningMsg = "";
		var dateFlag= false;
		var byRange = $("#requestRescheduleForm input[name=rangeOfDates]:checked").val();
		if(byRange == '0' || byRange == '1'){
			if($('#rescheduleFromDate').val() == "" || (byRange == '1' && $('#rescheduleToDate').val() == "")){
				var  msg= '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.rescehdule.date" />';
				warningMsg += "<li>"+msg+"</li>";
				dateFlag = true;
			}
			if($('#rescheduleTimeFrom').val()== '0' || (byRange == '1' && $('#rescheduleTimeTo').val() == '0')){
				var  msg= '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.rescehdule.time" />';
				warningMsg += "<li>"+msg+"</li>";
			}
			if($('#reasonCodesForReschedule').val()== '0'){
				var  msg= '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.rescehdule.reason" />';
				warningMsg += "<li>"+msg+"</li>";
			}
			var comments= jQuery.trim($("#rescheduleComments").val());
			$("#rescheduleComments").val(comments);
			if(comments == ""){
				var  msg= '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.rescehdule.comments" />';
				warningMsg += "<li>"+msg+"</li>";
			}
		}else{
			warningMsg += "<li>Please select reschedule by fix date and time or date range.</li>";
		}
		if(!dateFlag){
			var  msg = validateRescheduleDate(byRange);
			if(msg != ""){
				warningMsg += "<li>"+msg+"</li>";
			}
		}
		if(warningMsg  != ""){
			warningMsg = "<ul>"+warningMsg+"</ul>";
			$("#rescheduleErrorMsg").html(warningMsg);
			$("#rescheduleErrorMsg").addClass("errorBox");
		}else{
			//getting the values 
			var rescheduleTimeFrom = $("#rescheduleTimeFrom").val();
			
			$("#newStartTime").val(rescheduleTimeFrom);
			var rescheduleTimeTo = $("#rescheduleTimeTo").val();
			$("#newEndTime").val(rescheduleTimeTo);		
			var reasonCodesForReschedule = $("#reasonCodesForReschedule").val();
			$("#reasonCode").val(reasonCodesForReschedule);
			if(byRange == '1'){
				$("#rangeOfDatesStr").val("false");
			}else{
				$("#rangeOfDatesStr").val("true");
			}
			//variable is set to true if it is not submitted and false if submitted already
			var isNotSubmitted=checkBeforeSubmit();
			if(isNotSubmitted)
				{
				var formData = jQuery('#requestRescheduleForm').serialize();
				$.ajax({
		        	url: 'updateDataForRequestReschedule.action',
		        	type: "POST",
		        	data: formData,
					dataType : "json",
					success: function(data) {
						$("#rescheduleErrorMsg").html("<ul>");
						$("#rescheduleErrorMsg").removeClass("errorBox");
						if(data.omErrors.length !== 0 ){
							$.each(data.omErrors, function(index,value){
								$("#rescheduleErrorMsg").append("<li>"+value.msg+"</li>");
							});
							$("#rescheduleErrorMsg").addClass("errorBox");
							$("#rescheduleErrorMsg").append("</ul>");
							$("#rescheduleErrorMsg").show();
						}else{
							$("#rescheduleRequest").jqmHide();
							$('.icon-repeat').trigger('click');
							$("#omSuccessDiv").html("<div  class='successBox'><p>"+data.result+"</p></div>");
							$("#omSuccessDiv").show();
							
						}	
					},
					error: function(xhr, status, err) {
						location.href ="${contextPath}/homepage.action";
			        }
			 	}); 
				}
			
		}
		
	});
			        
</script>


<style type="text/css">
<!--  
#requestReschedule input,#requestReschedule select,#requestReschedule option
	{
	font-size: 12px;
}
#requestReschedule table:hover {background: none;}


-->
</style>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />

<div id="requestReschedule" class="requestReschedule"
	style="width: 500px; float: left; color: #000000;">
	<div class="arrowAddNote"></div>
	<div id="modalTitleGrey_Reschedule">
		<c:choose>
			<c:when test="${rescheduleDate != ''}">
				<b>Edit Reschedule Request</b>
			</c:when>
			<c:otherwise>
				<b>Request A Reschedule</b>
			</c:otherwise>
		</c:choose>
		<a href="#" class="close"><img
			src="${staticContextPath }/images/widgets/tabClose.png"
			style="float: right;" onclick="closeModal('rescheduleRequest');" />
		</a>
	</div>
	<c:if test="${omApiErrors!=null && fn:length(omApiErrors) > 0}">
		<br />
		<div id="omApiErrorDiv" class="" style="">
			<div class="errorBox">
				<ul>
					<c:forEach var="omApiError" items="${omApiErrors}">
						<c:if test="${omApiError!=null}">
							<li>${omApiError.msg}</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
		<c:remove var="omApiErrors" scope="session" />
	</c:if>
	<div id="reschdule_descr" class="reschdule_descr">If you need to
		reschedule your visit, you may offer a fixed date or range of dates
		for an alternate visit. If the other party does not accept your
		change, you will need to honor your original agreement</div>
	<div id="appointmentBox" class="appointmentBox">
		<div id="appointmentBoxIn" style="margin: 4px;">
			<strong><fmt:message bundle="${serviceliveCopyBundle}"
					key="popup.service.current.appt" />:</strong> <br />
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="popup.service.dates" />
			: &nbsp;${serviceDate} <br />
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="popup.service.request.date.window" />
			:&nbsp;${serviceTime}
		</div>
	</div>
	
	<c:if test="${rescheduleDate != ''}">
	<div class="darkRed reschdule_descr" style="padding-top: 10px; padding-bottom: 10px">
			This service order already has a pending reschedule request for
			${rescheduleDate}. If you submit a new request, it will replace the existing reschedule request.
	</div>
	</c:if>
	<form id="requestRescheduleForm" action="#" name="requestReschedule">
		<div style="height: 300px; overflow-y: auto; overflow-x: hidden;">
			<div id="rescheduleErrorMsg" class="rescheduleErrorMsg"></div>
			<input id="rescheduleSoId" name="rescheduleDTO.soId" type="hidden"
				value="" /> <input id="newStartTime"
				name="rescheduleDTO.newStartTime" type="hidden" value="" /> <input
				id="newEndTime" name="rescheduleDTO.newEndTime" type="hidden"
				value="" /> <input id="reasonCode" name="rescheduleDTO.reasonCode"
				type="hidden" value="" /> <input id="rangeOfDatesStr"
				name="rescheduleDTO.specificDate" type="hidden" value="true" />

			<table cellpadding="0px" cellspacing="0px"
				style="border-bottom: none;">
				<tr>
					<td style="padding-left: 10px"><strong style="color: #357EC7">Reschedule
							by:&nbsp;&nbsp; </strong> <br /></td>
					<td><input type="radio" id="rangeOfDates" name="rangeOfDates"
						checked="checked" value="1" onclick="showRescheduleRangeDate()" />
						<br /></td>
					<td width="2px"><br /></td>
					<td style="padding-left: 2px; padding-top: 3px;"><fmt:message
							bundle="${serviceliveCopyBundle}" key="popup.service.date.range" />
						<br /></td>
					<td width="10px"><br /></td>
					<td><input type="radio" id="specificDate" name="rangeOfDates"
						value="0" onclick="javascript:showRescheduleFixedDate()" /> <br />
					</td>
					<td style="padding-left: 4px; padding-top: 3px;"><fmt:message
							bundle="${serviceliveCopyBundle}" key="popup.service.date.fixed" />
						<br /></td>
				</tr>
			</table>
			<br />
			<table cellspacing="0px" width="350px" align="center"
				style="border-bottom: none;">
				<tr>
				<td width="50%" height="20px"><span class="pre-call-common-hd" style="padding-left:15px;">Appointment Date: </span></td>
				<td width="50%"><span id="serviceWinPrecall"  class="pre-call-common-hd" style="padding-left:30px;" >Service Window: </span>
				<span id="serviceTimePrecall"  class="pre-call-common-hd" style="display:none;padding-left:35px;">Service Time: </span></td>
				</tr>
				<tr>
					<td width="60%"><span style="float: left; padding-left: 10px;">
							<input type="text" class="rescheduleDateField shadowBox"
							id="rescheduleFromDate" name="rescheduleDTO.newStartDate"
							value="" onfocus="showDatepicker(this.id);" />
					</span></td>
					<td><span style="vertical-align: middle; float: right;">
							<select id="rescheduleTimeFrom"
							style="width: 110px; height: 23px;" onchange="">
								<option value="0">Select</option>
								<c:forEach var="time" items="${time_intervals_updated}">
									<option value="${time.descr}">${time.descr}</option>
								</c:forEach>
						</select>
					</span></td>
				</tr>
				<tr id="rescheduleDateRanged" style="display: none;">
					<td width="60%" style="padding-left: 10px;"><span
						id="dateField"> <span
							style="float: left; padding-left: 10px; padding-bottom: 5px;">
								<fmt:message bundle="${serviceliveCopyBundle}" key="popup.to" />
						</span> <br /> <span style="float: left;"> <input type="text"
								class="shadowBox rescheduleDateField" id="rescheduleToDate"
								name="rescheduleDTO.newEndDate" value=""
								onfocus="showDatepicker(this.id);" />
						</span>
					</span></td>
					<td><span id="timeField"> <span
							style="float: left; padding-left: 35px;"> <fmt:message
									bundle="${serviceliveCopyBundle}" key="popup.to" />
						</span> <br /> <span style="float: right; padding-top: 5px"> <select
								id="rescheduleTimeTo" style="width: 110px; height: 23px;"
								onchange="">
									<option value="0">Select</option>
									<c:forEach var="time" items="${time_intervals_updated}">
										<option value="${time.descr}">${time.descr}</option>
									</c:forEach>
							</select>
						</span>
					</span></td>
				</tr>
			</table>
			<br />
			<div id="reasonCodeListDiv"
				style="text-align: left; padding-left: 5px;">
				<select id="reasonCodesForReschedule" style="width: 370px;"
					onchange="">
					<option value="0">Select a Reason for Rescheduling</option>
					<c:forEach var="reasonCodes"
						items="${rescheduleDetails.reasonCodes.reasonCode}">
						<option  value="${reasonCodes.id}">${reasonCodes.descr}</option>
					</c:forEach>
				</select>
			</div>
			<br />
			<span style="text-align: left; padding-left: 10px;">
				<span
				style="padding: 4px;"><strong>Comments:</strong><font
					color="red">*</font></span><br/>
				<span
				style="float: left; padding-left: 10px; margin-top: 10px;">
					<textarea
						id="rescheduleComments" name="rescheduleDTO.rescheduleComments"
						style="font-family: sans-serif; font-weight: lighter; height: 100px; overflow: scroll; width: 370px; font-size: 13px;"></textarea>
				</span>
		  </span>
		</div>
		<span style="padding: 10px; margin-bottom: 10px">
		  <input
			type="button" id="rescheduleBtn" value="SUBMIT" class="actionButton"
			style="float: right; margin: 5px 15px 5px 5px;" />
		</span>
	</form>
</div>
<!-- Request A Reshedule End -->