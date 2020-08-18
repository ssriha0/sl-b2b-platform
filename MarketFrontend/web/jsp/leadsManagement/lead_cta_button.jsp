<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
	<div class="cta btn-group btn-group-justified">
		<!-- Format the phone number in +1[10Digitnumber] -->
		<c:choose>
			<c:when test="${lmTabDTO.lead.leadStatus== 'stale'}">
	 			<a role="button" href="#" class="btn btn-default" data-toggle="modal" data-target="#callWidget">Call Customer</a>
	 			<!--[if IE]>
					<div style="border-right: solid 1px #00a0d2; width: 1px;"></div>
				<![endif]-->
		    	<a role="button" href="#" class="btn btn-default" data-toggle="modal" data-target="#scheduleWidget">Schedule Appt</a>
		  	</c:when>
		 	<c:when test="${lmTabDTO.lead.leadStatus== 'new'}">
		 		<a role="button" href="#" class="btn btn-default" data-toggle="modal" data-target="#callWidget">Call Customer</a>
			 	<!--[if IE]>
					<div style="border-right: solid 1px #00a0d2; width: 1px;"></div>
				<![endif]-->
		    	<a role="button" href="#" class="btn btn-default" data-toggle="modal" data-target="#scheduleWidget">Schedule Appt</a>
		 	</c:when>
		  	<c:when test="${lmTabDTO.lead.leadStatus== 'working'}">
		    	<a role="button" href="#" class="btn btn-default" data-toggle="modal" data-target="#callWidget">Call Customer</a>
		    	<!--[if IE]>
					<div style="border-right: solid 1px #00a0d2; width: 1px;"></div>
				<![endif]-->
		    	<a role="button" href="#" class="btn btn-default" data-toggle="modal" data-target="#scheduleWidget">Schedule Appt</a>
		 	 </c:when>
			<c:when test="${lmTabDTO.lead.leadStatus== 'scheduled'}">
				<c:if  test="${lmTabDTO.lead.resourceAssigned != null && lmTabDTO.lead.resourceAssigned != '0'}">
				<a role="button" href="#" class="btn btn-default full" data-toggle="modal" data-target="#completeWidget">Complete Order</a>
				</c:if>
				<c:if  test="${lmTabDTO.lead.resourceAssigned == null || lmTabDTO.lead.resourceAssigned == '0'}">
				<a role="button" href="#" class="btn btn-default" data-toggle="modal" data-target="#assignWidget">Assign Provider</a>
				<!--[if IE]>
					<div style="border-right: solid 1px #00a0d2; width: 1px;"></div>
				<![endif]-->
				<a role="button" href="#" class="btn btn-default" data-toggle="modal" data-target="#completeWidget">Complete Order</a>
			</c:if>
			</c:when>
			
	
		</c:choose>
		
				
	</div>
</div>
