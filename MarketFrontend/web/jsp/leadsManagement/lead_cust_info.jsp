<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="details-hero">
				<c:if test="${lmTabDTO.lead.leadStatus== 'stale'}"> 
		 			<jsp:include page="lead_stale_info.jsp"/> 
				</c:if>
	<div class="col-sm-8">

		<h1><c:choose><c:when test="${not empty lmTabDTO.lead.custFirstName || not empty lmTabDTO.lead.custLastName}">${lmTabDTO.lead.custFirstName}&nbsp;${lmTabDTO.lead.custLastName}
						</c:when>
						<c:otherwise>Not specified </c:otherwise></c:choose></h1>
		              
		<!-- hidden on mobile screens -->
		<div class="cust-info hidden-xs">
		<p>
		<c:choose>
		<c:when test="${lmTabDTO.lead.leadStatus == 'new'}">
		        </br>
		</c:when>
		<c:otherwise>
		    ${lmTabDTO.lead.street}<br>
		</c:otherwise>
		</c:choose>
		${lmTabDTO.lead.city},  ${lmTabDTO.lead.state}&nbsp;${lmTabDTO.lead.zip}
		<br/>
		<c:choose><c:when test="${not empty lmTabDTO.lead.formattedCustPhoneNo}">${lmTabDTO.lead.formattedCustPhoneNo}
						</c:when>
						<c:otherwise>Not specified </c:otherwise></c:choose>
		<br>
		<c:choose><c:when test="${not empty lmTabDTO.lead.email}"><a href="mailto:${lmTabDTO.lead.email}">${lmTabDTO.lead.email}</a>
						</c:when>
						<c:otherwise>Not specified </c:otherwise></c:choose>
		</p>
		</div><!--/ .cust-info -->
	</div><!--/ .col-sm-8 -->