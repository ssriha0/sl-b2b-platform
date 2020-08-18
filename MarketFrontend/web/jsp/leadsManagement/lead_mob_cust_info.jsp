
<!-- Visible on mobile screens -->
<section class="cust-info visible-xs">
<h2>
	Customer Info
</h2>
<p>
    <c:choose>
		<c:when test="${lmTabDTO.lead.leadStatus == 'new'}">
		        </br>
		</c:when>
		<c:otherwise>
		    ${lmTabDTO.lead.street}<br>
		</c:otherwise>
	</c:choose>
	${lmTabDTO.lead.city}, ${lmTabDTO.lead.state}&nbsp;${lmTabDTO.lead.zip}
	<br>
	${lmTabDTO.lead.formattedCustPhoneNo}
	<br>
	<a href="mailto:${lmTabDTO.lead.email}">${lmTabDTO.lead.email}</a>
</p>
</section>
<!-- /.cust-info -->