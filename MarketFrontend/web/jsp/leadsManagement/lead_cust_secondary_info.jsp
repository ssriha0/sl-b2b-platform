<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-sm-4 secondary-info">
      <!--<p>Service Request #${lmTabDTO.lead.leadId}</p> <!-- Lead ID -->
      <p>Service Request #${lmTabDTO.lead.leadId}</p> <!-- LMS Lead ID -->
       <c:set var="timeValueDisplay" value="${lmTabDTO.lead.createdDate}"/>
      <p><time class="timeago" datetime="${timeValueDisplay}">${lmTabDTO.lead.createdDate}</time></p> <!-- Datetime Lead Received -->
      
      <c:choose>
		  <c:when test="${lmTabDTO.lead.leadStatus== 'new'}">
		  	<c:set var="classNameValue" value="label leadStatus-1"/>
		  	<c:set var="iconNameValue" value="icon-leadStatus-1"/>
		  	<c:set var="statusValue" value="New"/>
		  	<p>
      		<span class="${classNameValue}">
      		<i class="${iconNameValue}"></i> ${statusValue}
      		</span> 
		  </c:when>
		  <c:when test="${lmTabDTO.lead.leadStatus== 'working'}">
		  	<c:set var="classNameValue" value="label leadStatus-2"/>
		  	<c:set var="iconNameValue" value="icon-leadStatus-2"/>
		  	<c:set var="statusValue" value="Working"/>
		  	<p>
             <span class="${classNameValue}">
      	     <i class="${iconNameValue}"></i> ${statusValue}
             </span> 
           </c:when>
		  <c:when test="${lmTabDTO.lead.leadStatus== 'scheduled'}">
		  	<c:set var="classNameValue" value="label leadStatus-3"/>
		  	<c:set var="iconNameValue" value="icon-leadStatus-3"/>
		  	<c:set var="statusValue" value="Scheduled"/>
		  	<p>
            <span class="${classNameValue}">
      	    <i class="${iconNameValue}"></i> ${statusValue}
            </span> 
         </c:when>
		  <c:when test="${lmTabDTO.lead.leadStatus== 'completed'}">
		  	<c:set var="classNameValue" value="label leadStatus-4"/>
		  	<c:set var="iconNameValue" value="icon-leadStatus-4"/>
		  	<c:set var="statusValue" value="Completed"/>
		  	 <p>
      			<span class="${classNameValue}">
      			<i class="${iconNameValue}"></i> ${statusValue}
     			</span> 
     		 </p>
		  </c:when>
		  <c:when test="${lmTabDTO.lead.leadStatus== 'cancelled'}">
		  	<c:set var="classNameValue" value="label leadStatus-5"/>
		  	<c:set var="iconNameValue" value="icon-leadStatus-5"/>
		  	<c:set var="statusValue" value="Cancelled"/>
		  	 <p>
              <span class="${classNameValue}">
              <i class="${iconNameValue}"></i> ${statusValue}
      		  </span> 
      		 </p>
		  </c:when>
		  <c:when test="${lmTabDTO.lead.leadStatus== 'stale'}">
		  	<c:set var="classNameValue" value="label leadStatus-6"/>
		  	<c:set var="iconNameValue" value="icon-leadStatus-6"/>
		  	<c:set var="statusValue" value="Stale"/>
		    <p>
             <span class="${classNameValue}">
      	     <i class="${iconNameValue}"></i> ${statusValue}
             </span> 
          </c:when>
	     </c:choose>
    </div>
     <!-- Commented as per SL-20893 requirments -->
    <!-- <c:if test="${not empty lmTabDTO.lead.urgency&& lmTabDTO.lead.urgency!= 'after tomorrow'&& lmTabDTO.lead.urgency!=' '}">                
    <div class="urgency">${lmTabDTO.lead.urgency}</div>
    </c:if> -->
</header>