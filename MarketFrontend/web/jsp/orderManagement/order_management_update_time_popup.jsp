<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
	<c:forEach var="contact" items="${timeDetails.contacts.contactList}">
		<c:if test="${contact.contactLocnType=='Provider'}">
			<c:set var="firstName" value="${contact.firstName}"/>
			<c:set var="lastName" value="${contact.lastName}"/>
			<c:set var="primaryPhone" value="${contact.primaryPhone.number}"/>
			<c:set var="altPhone" value="${contact.altPhone.number}"/>
		</c:if>
	</c:forEach>
	
	<c:set var="serviceDate1" value="${timeDetails.schedule.serviceDateTime1}"/>
	<c:if test="${fn:substring(serviceDate1,12,14)>0 && fn:substring(serviceDate1,12,14)<15}">
		<c:set var="serviceDate1" value="${fn:replace(serviceDate1,fn:substring(serviceDate1,12,14),'00')}"/>
	</c:if>
	<c:if test="${fn:substring(serviceDate1,12,14)>15 && fn:substring(serviceDate1,12,14)<30}">
		<c:set var="serviceDate1" value="${fn:replace(serviceDate1,fn:substring(serviceDate1,12,14),'15')}"/>
	</c:if>
	<c:if test="${fn:substring(serviceDate1,12,14)>30 && fn:substring(serviceDate1,12,14)<45}">
		<c:set var="serviceDate1" value="${fn:replace(serviceDate1,fn:substring(serviceDate1,12,14),'30')}"/>
	</c:if>
	<c:if test="${fn:substring(serviceDate1,12,14)>45}">
		<c:set var="serviceDate1" value="${fn:replace(serviceDate1,fn:substring(serviceDate1,12,14),'45')}"/>
	</c:if>
	
	<c:set var="serviceDate2" value="${timeDetails.schedule.serviceDateTime2}"/>
	<c:if test="${fn:substring(serviceDate2,12,14)>0 && fn:substring(serviceDate2,12,14)<15}">
		<c:set var="serviceDate2" value="${fn:replace(serviceDate2,fn:substring(serviceDate2,12,14),'00')}"/>
	</c:if>
	<c:if test="${fn:substring(serviceDate2,12,14)>15 && fn:substring(serviceDate2,12,14)<30}">
		<c:set var="serviceDate2" value="${fn:replace(serviceDate2,fn:substring(serviceDate2,12,14),'15')}"/>
	</c:if>
	<c:if test="${fn:substring(serviceDate2,12,14)>30 && fn:substring(serviceDate2,12,14)<45}">
		<c:set var="serviceDate2" value="${fn:replace(serviceDate2,fn:substring(serviceDate2,12,14),'30')}"/>
	</c:if>
	<c:if test="${fn:substring(serviceDate2,12,14)>45}">
		<c:set var="serviceDate2" value="${fn:replace(serviceDate2,fn:substring(serviceDate2,12,14),'45')}"/>
	</c:if>					
					
<c:set var="serviceEndTime" value="${fn:substring(serviceDate2, 9, 17)}"></c:set>
<head>
	<style type="text/css">
	 #updateTime select option {
	font-family: Arial;font-size: 12px;
	}	
	</style>
	
	<script type="text/javascript">
	var timeIntervalArray = [];
	
	<c:forEach var="time" items="${time_intervals_updated}" varStatus="status">
		timeIntervalArray.push('${time.descr}');
	</c:forEach>	
	var serviceEndTime = '${serviceEndTime}';
	var startTime = $('#apptTime1').val();
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
	
	function setScheduleToUpdateTime(fromId, toId){
        	var minTime = $('#minTimeUpdateTime').val();
        	var maxTime = $('#maxTimeUpdateTime').val();
			
			var startTime = $('#'+fromId).val();
			$('#'+toId).children().remove();
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
				$("#"+toId).append("<option value='-1'>-Select-</option>");
						
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
						if(count<size  && boundaryflag == true){
							//alert(count);
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
	
	var diff = getTimeDiff(startTime,serviceEndTime);
	$("#apptTime2").html("<option value='"+serviceEndTime+"' selected='selected'>"+serviceEndTime+" ("+diff+")</option>");
		$(document).ready(function() {
			var zone = '${timeDetails.schedule.serviceLocationTimezone}';
			if(null != zone && '' != zone){
				var displayZone = zone.substring(zone.length-3);
				var actualZone = zone.substring(0, zone.length-4);
				$("#zone").html(actualZone);
				$('#displayZone').html(displayZone);
			}
			
		/*	$("#apptTime1").change( function(){
			
				setScheduleToUpdateTime('apptTime1', 'apptTime2');
			});*/
		});
	
	</script>
</head>
	
<body>	
	<div id="updateTime" style="width: 500px; float: left;font-family: Arial;font-size: 13px;">
		<div id="modalTitleBlack"
			style="background: #000000; color: #FFFFFF; text-align: left; height: 25px; padding-left: 8px; padding-top: 5px; font-size: 14px;">
			<b>Update Service Window </b><a href="#"><img
				src="${staticContextPath }/images/widgets/tabClose.png"
				style="float: right;" onclick="closeModal('updateApptTime');" /></a>
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
		<input id="minTimeUpdateTime" type="hidden" value="${MINTIME_WINDOW}"/>
		<input id="maxTimeUpdateTime" type="hidden" value="${MAXTIME_WINDOW}"/>
		<input id="scheduleType" type="hidden" value="${timeDetails.schedule.scheduleType}"/>
		<br>
		<div class="errorBox clearfix" id="updateTimeResponseMessage"
			style="width: 300px; overflow-y: hidden; display: none; margin-left:10px; padding-left:5px;"></div>
		<br />
		<div style="color: #000000; margin-left: 15px;">
			<div id="infoWindow" class="infoWindow"
				style="background-color: #f7fc92; width: 230px; float: right; margin-right: 15px; text-align: left; position: relative; left: -10px; bottom: -130px; float: right;">
				<div style="float:left"><img src="${staticContextPath}/images/order_management/ideaBulb.png" /></div>
				<div style="padding-left: 5px;float:right;width:200px;">You may submit reschedule if the
								customer prefers a different date.</div>
			</div> 
			<div
				style="text-align: left; position: relative;float: left;bottom: 50px;">
				<p><b>Appointment Date :</b> <span id="startDate">${fn:substring(timeDetails.schedule.serviceDateTime1, 0, 8)}</span>
				<c:if test="${timeDetails.schedule.scheduleType == 'range'}">
					 - <span id="endDate">${fn:substring(timeDetails.schedule.serviceDateTime2, 0, 8)}</span></c:if></p>
				<p><c:choose><c:when test="${timeDetails.schedule.scheduleType == 'range'}">
					<b>Service</b><br>
					<b>Window :</b>
					</c:when>
					<c:otherwise>
					<b>Service</b><br>
					<b>Time :</b> 
					</c:otherwise></c:choose>
					<span style="margin-left:55px;bottom: 7px;position: relative;"><select id="apptTime1" onchange="setScheduleToUpdateTime('apptTime1', 'apptTime2');" style="height: 20px;font-family: Arial;font-size: 12px;" >
						<c:forEach var="time" items="${time_intervals_updated_new}">
							<c:choose><c:when test="${fn:substring(serviceDate1, 9, 17) == time.descr}">
								<option value="${time.descr}" selected="selected" style="font-family: Arial;font-size: 12px;">${time.descr}</option>
							</c:when>
							<c:otherwise>
								<option value="${time.descr}" style="font-family: Arial;font-size: 12px;">${time.descr}</option>
							</c:otherwise></c:choose>
						</c:forEach>
						</select> <c:if test="${timeDetails.schedule.scheduleType == 'range'}"> -<select id="apptTime2" style="height: 20px;font-family: Arial;font-size: 12px;width:140px;">
						
						</select></c:if> <span id="zone" style="display:none;"></span><span id="displayZone"></span></span>
				</p>
				<p>
				<c:if test="${timeDetails.schedule.scheduleType == 'range'}">
				    <span class="updateTimeEtaToolTipInfoIcon" style="font-weight:bold">ETA</span>
					<select id="eta" style="height: 20px;font-family: Arial;font-size: 12px;">
						<option value="0" style="font-family: Arial;font-size: 12px;">-Select-</option>
						<c:forEach var="time" items="${time_intervals_updated}">
							<option value="${time.descr}" style="font-family: Arial;font-size: 12px;">${time.descr}</option>
						</c:forEach>
					</select>
					<div id="updateTimeEtaToolTip" class="updateTimeEtaToolTipInfo">
							<p style="padding-left: 5px;font-size:12px;">
							Estimated time of arrival at service location
							</p>
					</div>
				</c:if>
				</p>

				<br />
				<div style="color: #000000; text-align: left;">
					<input type="checkbox" name="selectedProviders" value="true"
						id="confirmUpdateTime" /> The new time has been confirmed with the
					customer.
				</div>
				<br />
				<div id="infoWindow">
					<div id="infoWindowDetails">
						<b>${firstName} ${lastName}</b>
						<br />
						<p><i class="icon-phone"></i>Primary: <b style="padding-left:7px;">
							<c:if test="${null != primaryPhone && '' != primaryPhone}">
								(${fn:substring(primaryPhone, 0, 3)}) ${fn:substring(primaryPhone, 4, 12)}
							</c:if>
						</b> <br>
						<i class="icon-phone"></i>Alternate: 
						<b>
							<c:if test="${null != altPhone && '' != altPhone}">
								(${fn:substring(altPhone, 0, 3)}) ${fn:substring(altPhone, 5, 7)}${fn:substring(altPhone, 8, 14)}
							</c:if>
						</b><br />
						<p>${timeDetails.serviceLocation.address1}<br>
						<c:if test="${null != timeDetails.serviceLocation.address2 && '' != timeDetails.serviceLocation.address2}">
							${timeDetails.serviceLocation.address2}<br>
						</c:if>
						<c:if test="${null != timeDetails.serviceLocation.city && '' != timeDetails.serviceLocation.city}">
							${timeDetails.serviceLocation.city}<br>
						</c:if>
						<c:if test="${null != timeDetails.serviceLocation.state && '' != timeDetails.serviceLocation.state}">
							${timeDetails.serviceLocation.state},
						</c:if>
						${timeDetails.serviceLocation.zip}
						<br /><br>
					</div>
				</div>
			</div>
			<br />
		</div>
		<div style="height: 42px;background-color: #F2F2F2;position: relative;border-top: 1px solid #cccccc;bottom: -255px;">
			 <span
				style="float: right;margin-right:10px;position: relative;bottom: 20px;"> <input
				type="button" value="Save" id="saveUpdateTime" style="text-transform:none;font-size:1em;"
				class="actionButton" onclick="saveUpdateTime('${timeDetails.orderstatus.soId}');" />
			</span>
		</div>
	</div>
	<div id="timeUpdate"></div>
</body>
</html> 