<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="clearfix">
	<div class="left" style="width:49%;">
		<dl>
			<dt>Campaign Created By</dt>
			<dd>${createdBy}</dd>
		</dl>
		<dl>
			<dt>Dates</dt>
			<dd>
				<fmt:formatDate value="${campaignHeader.startDate}" pattern="MM/dd/yyyy" /> -
				<fmt:formatDate value="${campaignHeader.endDate}" pattern="MM/dd/yyyy" />
			</dd>
		</dl>
		<!--R10.3 SL-19812 Introduce Primary Industry criteria for Campaign -->
		<dl>
			<dt>Primary Industry</dt>
			<dd>${primaryIndustry}</dd>
		</dl>
		<dl>
			<dt>Markets</dt>
			<dd>${markets}</dd>
		</dl>
		<dl>
			<dt>States</dt>
			<dd>${states}</dd>
		</dl>
	</div>
	<div class="right" style="width:49%;">
		<dl style="width:100%;">
			<dt class="clearfix"><span>Approval Criteria &amp; Credentials</span><span class="open"></span><span class="closed"></span></dt>
			<dd>
				<ul>
					<li>	
						<strong>Credentials</strong><br />
						Resource: ${credentials} <br/>
						Company : ${firmCredentials} 
					</li>

					<li>
						<strong>Minimum Rating</strong>
						${minimumRating}
					</li>

					<li>
						<strong>Languages: </strong>${languages}
						<br /><strong>Minimum Completed Service Orders: </strong> ${minimumServiceOrdersCompleted}
						<br /><strong>Meeting with Provider:</strong> ${meetingRequired}
					</li>

					<li>
						<strong>Insurance Minimum Coverage</strong>
						<%-- 
						<br />$10,000 Vehicle Liability
						<br />$1,000,000 Worker's Compensation
						--%>
						
						<c:if test="${autoLiabilitySelected}">
							<br /><fmt:formatNumber type="currency" value="${autoLiabilityAmount}" /> Vehicle Liability
							<c:if test="${autoLiabilityVerified}">
								, must be verified
							</c:if>
						</c:if>
						<c:if test="${generalLiabilitySelected}">
							<br /><fmt:formatNumber type="currency" value="${generalLiabilityAmount}" /> General Commercial Liability
							<c:if test="${generalLiabilityVerified}">
								, must be verified
							</c:if>
						</c:if>
						<c:if test="${workersCompensation}">
							<br /> Workers Compensation is required
						</c:if>						 						 
					</li>
					
					<li>
						<strong>Company Size</strong><br />
						${companySize}
					</li>
					
					<li>
						<strong>Annual Sales Revenue</strong><br />
						${salesVolumn}
					</li>
					
				</ul>
			</dd>
		</dl>
	</div>
</div>