<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<section class="schedule col-md-5">

		<c:choose>
		  <c:when test="${lmTabDTO.lead.leadStatus== 'new'}">
		  	<c:set var="scheduleHdr" value="Preferred Start Date"/>
		  
		  </c:when>
		  <c:when test="${lmTabDTO.lead.leadStatus== 'working'}">
		  	<c:set var="scheduleHdr" value="Preferred Start Date"/>
		
		  </c:when>
		  <c:when test="${lmTabDTO.lead.leadStatus== 'scheduled'}">
		  	<c:set var="scheduleHdr" value="Appointment Details"/>
		  	
		  </c:when>
		  <c:when test="${lmTabDTO.lead.leadStatus== 'completed'}">
		  	<c:set var="scheduleHdr" value="Appointment Details"/>
		  
		  </c:when>
		  <c:when test="${lmTabDTO.lead.leadStatus== 'cancelled'}">
		   	<c:if test="${lmTabDTO.lead.scheduledDate ==null}">
		  		<c:set var="scheduleHdr" value="Preferred Start Date"/>
		  	</c:if>
		  	<c:if test="${lmTabDTO.lead.scheduledDate !=null}">
		  	 	<c:set var="scheduleHdr" value="Appointment Details"/>
		  	</c:if>
		  </c:when>
		  <c:when test="${lmTabDTO.lead.leadStatus== 'stale'}">
		  	<c:set var="scheduleHdr" value="Preferred Start Date"/>
		</c:when>
</c:choose>


<h2>
	${scheduleHdr}
</h2>
<c:choose>
<c:when test="${lmTabDTO.lead.leadStatus== 'scheduled'}">
<p>${lmTabDTO.lead.scheduledDate}<br/> ${lmTabDTO.lead.scheduledStartTime}&nbsp;-&nbsp;${lmTabDTO.lead.scheduledEndTime} <a href="#" data-toggle="modal" data-target="#scheduleWidget">Update Appt.</a></p>
</c:when>
<c:when test="${lmTabDTO.lead.leadStatus== 'scheduled'}">
		<c:if test="${lmTabDTO.lead.resourceAssigned != null && lmTabDTO.lead.resourceAssigned != '0'}"> 
		<p>${lmTabDTO.lead.scheduledDate}<br/> ${lmTabDTO.lead.scheduledStartTime}&nbsp;-&nbsp;${lmTabDTO.lead.scheduledEndTime}</p>
		</c:if>
 </c:when>
 <c:when test="${lmTabDTO.lead.leadStatus== 'completed'}">
		<p>${lmTabDTO.lead.scheduledDate}<br/> ${lmTabDTO.lead.scheduledStartTime}&nbsp;-&nbsp;${lmTabDTO.lead.scheduledEndTime}</p>
 </c:when>
 <c:when test="${lmTabDTO.lead.leadStatus== 'cancelled'}">
 <c:choose>
  <c:when test="${lmTabDTO.lead.scheduledDate !=null}">
    <p>${lmTabDTO.lead.scheduledDate}<br/> ${lmTabDTO.lead.scheduledStartTime}&nbsp;-&nbsp;${lmTabDTO.lead.scheduledEndTime}</p>
  </c:when>
  <c:otherwise>
  <p>
 <c:choose> 
 <c:when test="${lmTabDTO.lead.serviceDate != null}">${lmTabDTO.lead.serviceDate}<br/>
  <c:if test="${lmTabDTO.lead.serviceStartTime != null && lmTabDTO.lead.serviceEndTime != null}">${lmTabDTO.lead.serviceStartTime}&nbsp;-&nbsp;${lmTabDTO.lead.serviceEndTime}
 </c:if>
 </c:when>
 <c:otherwise>
  Not Specified
 </c:otherwise>
 </c:choose>
  </p>
  </c:otherwise>
  </c:choose>
 </c:when>
 
 <c:otherwise>
 <p>
 <c:choose> 
 <c:when test="${lmTabDTO.lead.serviceDate != null}"> 
 ${lmTabDTO.lead.serviceDate}<br/>
  <c:if test="${lmTabDTO.lead.serviceStartTime != null && lmTabDTO.lead.serviceEndTime != null}"> 
	${lmTabDTO.lead.serviceStartTime}&nbsp;-&nbsp;${lmTabDTO.lead.serviceEndTime}
 </c:if>
 </c:when>

 <c:otherwise>
  Not Specified
 </c:otherwise>
 </c:choose>
 
  </p>
 </c:otherwise>
</c:choose>
  				
 	

	<jsp:include page="lead_widget_schedule.jsp"/> 
  <c:choose>
		  <c:when test="${lmTabDTO.lead.leadStatus== 'scheduled'}">
				 <c:if test="${lmTabDTO.lead.resourceAssigned != null && lmTabDTO.lead.resourceAssigned != '0'}"> 
		 			<jsp:include page="lead_assign_pro.jsp"/> 
		 			
				</c:if>
  		  </c:when>
			<c:when test="${lmTabDTO.lead.leadStatus== 'completed'}">
				 <jsp:include page="lead_completed_info.jsp"/>
		  </c:when>
		   <c:when test="${lmTabDTO.lead.leadStatus== 'cancelled'}">
		  	 <jsp:include page="lead_cancelled_info.jsp"/>
		  </c:when>
	 			 
	</c:choose>
</section><!-- /.schedule -->
</div><!-- /.row -->
 <c:if test="${lmTabDTO.lead.leadStatus != 'cancelled' && lmTabDTO.lead.leadStatus != 'completed'}">
		  	 <jsp:include page="lead_widget_cancel.jsp"/>
</c:if>


