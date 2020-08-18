<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>

<style>
.serviceOrderRevisitFrame {
	width: 499px;
	border: 1px solid #9f9f9f;
	background-color: #FFF;
	padding-bottom: 22px;
	text-align: left;
}

.serviceOrderRevisitFrameBody {
	width: 635px;
	padding-top: 8px;
	padding-left: 8px;
	padding-right: 8px;
}

#revTable1 td{
	padding-bottom: 8px;
	font-size: 12px;
}
#revTable2 td{
	padding-bottom: 8px;
	font-size: 12px;
}#revTable3 td{
	padding-bottom: 8px;
	font-size: 12px;
}
#revTable4 td{
    display: list-item;
    padding-left: 5px;
    width: 350px;
}#revTable4{
	border-collapse: collapse;
	margin-left: 40px;
}
}
</style>


<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<script type="text/javascript"
	src="${staticContextPath}/javascript/jquery/jqmodal/jqModal.js"></script>
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/modalVideo.css" />

<!-- icons by Font Awesome - http://fortawesome.github.com/Font-Awesome -->
<link
	href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome.min.css"
	rel="stylesheet" />

<form id="revisitNeededFrm" name="revisitNeededFrm"
	style="padding: 0px;">

	<div class="modalheader"
		style="background: none repeat scroll 0% 0% rgb(88, 88, 90); color: #FFFFFF; text-align: left; height: 25px; padding-top: 10px; padding-right: 4px; padding-left: 15px;">
		<a href="javascript:void(0)"><img
			src="${staticContextPath }/images/widgets/tabClose.png"
			style="float: right;" onclick="closeRevisitModal();" /> </a> <b>${revisitNeededInfoDTO.departureReasonHeader}</b>
	</div>

	<div class="modalheaderoutline">
		<div id="revisitMain" class="serviceOrderRevisitFrame"
			style="border: none; margin-left: 10px;">
			<div class="serviceOrderRevisitFrameBody">
				${revisitNeededInfoDTO.departureReason}details for this service order has been logged by the provider from mobile app.
				<br/><br/>
				<table id="revTable1" style="border: 0; width: 100%;">
					<tr id="revRow2">
						<td id="revR2C1"><b>Provider</b>:
							${revisitNeededInfoDTO.provider}</td>
					</tr>
					<tr id="revRow3">
						<td id="revR3C1" align="left" width="57%"><b>Appointment Date</b>:
							${revisitNeededInfoDTO.appointmentDate}</td>
						<td id="revR3C2" align="left" width="43%"><b>Checked In</b>:
							${revisitNeededInfoDTO.checkedIn}</td>
					</tr>
					<tr id="revRow4">
						<td id="revR4C1" align="left" width="57%"><b>Service Window</b>:
							${revisitNeededInfoDTO.serviceWindow}</td>
						<td id="revR4C2" align="left" width="43%"><b>Checked Out</b>:
							${revisitNeededInfoDTO.checkedOut}</td>
					</tr>
				</table>
				<table id="revTable2" style="border: 0; width: 100%;">
					<c:if test="${revisitNeededInfoDTO.departureReasonHeader == 'Revisit Needed'}">
					<tr id="revRow5">
						<td id="revR5C1" colspan="2" width="35%"><b>Next Appointment Scheduled On:</b></td>
						<td width="65%">${revisitNeededInfoDTO.nextAppointment}</td>
					</tr>
					<tr id="revRow6">
						<td id="revR6C1" colspan="2" width="35%"><b>Reason for Revisit :</b></td>
						<td width="65%">${revisitNeededInfoDTO.revisitReasonCode}</td>
					</tr>
					<!--  <tr id="revRow7" >
						<td id="revR7C1" colspan="2" width="35%"><b>Has the work started?</b></td>
						<td width="65%"><c:choose>
								<c:when test="${revisitNeededInfoDTO.workStartedInd == true}">
									Yes
									</c:when>
								<c:otherwise>
									No
									</c:otherwise>
							</c:choose></td>
					</tr>
					<tr id="revRow8">
						<td id="revR8C1" colspan="2" width="35%"><b>Is the Merchandise delivered?</b>
						<td width="65%"><c:choose>
								<c:when test="${revisitNeededInfoDTO.merchDeliveredInd == true}">
									Yes
									</c:when>
								<c:otherwise>
									No
									</c:otherwise>
							</c:choose></td>
					</tr> -->
					<tr id="revRow9">
						<td id="revR9C1" colspan="2" width="35%"><b>Comments</b></td>
						<td width="65%"><textarea 
						id="revisitComments" name="revisitNeededInfoDTO.comments" disabled
						style="font-family: 'Arial Regular','Arial';font-size: 12px;
						font-style: normal;font-weight: 400;height: 38px;left: 0;
						text-align: left;text-decoration: none; width: 310px; resize: none;">${revisitNeededInfoDTO.comments}</textarea></td>
					</tr>
					</c:if>
				</table>
				<c:if test="${!empty revisitNeededInfoDTO.changeTypes}">
				<table id="revTable3" style="border: 0; width: 100%;">	
					<tr id="revRow10">
						<td id="revR10C1" colspan="2"><b>What completion details are changed
								in this trip?</b> <br /> 
								</br>
								<table id="revTable4">
									<c:forEach var="changeType"
										items="${revisitNeededInfoDTO.changeTypes}">
										<tr>
											<td>${changeType}</td>
										</tr>
									</c:forEach>
								</table>
						</td>	
					</tr>
				</table>
				</c:if>
			</div>
		</div>
	</div>

</form>