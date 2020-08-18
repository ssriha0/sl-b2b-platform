<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<script type="text/javascript">

function selectAppointmentPreference(slotId,prefId){
	$("#selectedSlotId").val(slotId);
	$("#selectedPreferenceId").val(prefId);
	document.getElementById("divAppointValidateMessage").innerHTML = "";
}
</script>

<c:set var="height" value="50"></c:set>
<div>
	<c:forEach var="serviceDatetimeSlot"
		items="${summaryDTO.serviceDatetimeSlots}"
		varStatus="rowCounter">
		<div style="height: 25px;">
			<div style="float: left;padding-top: 3px;">
					<input type="radio" id="${serviceDatetimeSlot.slotId}"
						name="serviceDatetimeSlot"
						onclick="selectAppointmentPreference('${serviceDatetimeSlot.slotId}','${serviceDatetimeSlot.preferenceInd}')" />
				</div>
				<div style="padding-left: 15px;word-wrap: break-word;">
					<b>Preference ${serviceDatetimeSlot.preferenceInd}:</b>  ${serviceDatetimeSlot.appointmentDateTime}
		
			</div>
		
	</c:forEach>
</div>