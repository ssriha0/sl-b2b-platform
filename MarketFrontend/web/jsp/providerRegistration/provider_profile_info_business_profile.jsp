<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", -1);
%>
<script language="javascript" type="text/javascript">
		
</script>
<div id="fragment-2" class="clearfix">
	<div class="hpDescribe clearfix">
		<div>
			<h3 style="margin: 0px;">
				Company Overview
				<span>ID#${ProviderInfo.companyPublicInfo.vendorId}</span>
			</h3>
			<p>
				${ProviderInfo.companyPublicInfo.businessDesc}
			</p>
		</div>
	</div>


	<div class="hpDescribe clearfix">
		<h2>
			Details
		</h2>
		<h3 style="margin-top: 0px;">
			Company Statistics
		</h3>
		<div class="creds clearfix">
			<div class="credlist">
				<h4>
					Company ID#${ProviderInfo.companyPublicInfo.vendorId}
				</h4>
			</div>

			<c:if test="${ProviderInfo.companyPublicInfo.businessType != null}">
				<div class="credlist">
					<h4>
						Business Structure: ${ProviderInfo.companyPublicInfo.businessType}
					</h4>
				</div>
			</c:if>

			<c:if
				test="${ProviderInfo.companyPublicInfo.businessStartDate != null}">
				<div class="credlist">
					<h4>
						<fmt:formatNumber type="number"
							value="${ProviderInfo.companyPublicInfo.yearsInBusiness}"
							maxFractionDigits="1" />
						Years in Business
					</h4>
				</div>
			</c:if>

			<c:if test="${ProviderInfo.companyPublicInfo.memberSince != null}">
				<div class="credlist">
					<h4>
						ServiceLive Member Since
						<fmt:formatDate
							value="${ProviderInfo.companyPublicInfo.memberSince}"
							pattern="MMM dd, yyyy" />
					</h4>
				</div>
			</c:if>

			<div class="credlist">
				<h4>
					<c:choose>
						<c:when
							test="${ProviderInfo.companyPublicInfo.totalSoCompleted >= 0}">
													${ProviderInfo.companyPublicInfo.totalSoCompleted }
												</c:when>
						<c:otherwise>
											 		0
										 		</c:otherwise>
					</c:choose>
					Jobs Completed with ServiceLive
				</h4>
			</div>

			<c:if
				test="${ProviderInfo.companyPublicInfo.primaryIndustry != null}">
				<div class="credlist">
					<h4>
						Primary Industry:
						${ProviderInfo.companyPublicInfo.primaryIndustry}
					</h4>
				</div>
			</c:if>

			<c:if test="${ProviderInfo.companyPublicInfo.companySize != null}">
				<div class="credlist">
					<h4>
						${ProviderInfo.companyPublicInfo.companySize}
					</h4>
				</div>
			</c:if>
		</div>

		<h3>
			Warranty Information
		</h3>

		<ul style="list-style-position: inside;">
			<c:choose>
				<c:when test="${ProviderInfo.companyPublicInfo.freeEstimate == 1}">
					<li>
						This company charges for project estimates.
					</li>
				</c:when>
				<c:otherwise>
					<li>
						This company offers free project estimates.
					</li>
				</c:otherwise>
			</c:choose>
			<c:if
				test="${ProviderInfo.companyPublicInfo.warrPeriodLabor != null}">
				<li>
					All Labor is covered for
					${ProviderInfo.companyPublicInfo.warrPeriodLabor}
				</li>
			</c:if>
			<c:if
				test="${ProviderInfo.companyPublicInfo.warrPeriodParts != null}">
				<li>
					All new parts are covered for
					${ProviderInfo.companyPublicInfo.warrPeriodParts}
				</li>
			</c:if>
		</ul>

		<h3>
			Company Reported Insurance Policies
		</h3>

		<c:choose>
			<c:when
				test="${not empty ProviderInfo.companyPublicInfo.insurancePolicies}">
				<c:forEach
					items="${ProviderInfo.companyPublicInfo.insurancePolicies}"
					var="insurancePolicy">
					<ul style="list-style-position: inside;">
						<li>
							${insurancePolicy.name} in the amount of $
							<fmt:formatNumber type="number" maxFractionDigits="2"
								minFractionDigits="2" value="${insurancePolicy.policyamount}" />
							<c:if test="${insurancePolicy.isVerified == true}">							
								<img class="verified" alt="Verified Credential"
									src="${staticContextPath}/images/common/verified.png"
									title="Verified by ServiceLive (<fmt:formatDate value="${insurancePolicy.insVerifiedDate}" type="both" pattern="MMM dd, yyyy" />)"
								/>	
															<!-- SL20014-to view provider firm uploaded documents for buyer -->
								 <c:if test="${buyerSPNViewDocPermission == true && not empty ProviderInfo.companyPublicInfo.insuranceList}">
									 <c:forEach items="${ProviderInfo.companyPublicInfo.insuranceList}" var="insurance">
										 <c:if test="${insurancePolicy.credTypeDesc==insurance.credTypeDesc}">
											 <c:if
													test="${insurance.currentDocumentID != null && insurance.currentDocumentID > 0}">
													<c:set var="docId"
														value="${insurance.currentDocumentID}">
													</c:set>
													<a target="blank"
														href="jsp/providerRegistration/saveInsuranceTypeActiondisplayTheDocument.action?docId=${docId}">
														<img
														src="${staticContextPath}/images/images_registration/icons/pdf.gif"
														title="Click to view document" width="13px;"/>
													</a>
												</c:if>
											</c:if>
									 </c:forEach>
								</c:if>
							<!-- SL20014-end -->	
							</c:if>

						</li>
					</ul>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<p>
					Currently, there are no insurance policies on file.
				</p>
			</c:otherwise>
		</c:choose>
	</div>


	<div class="hpDescribe clearfix">
		<h2>
			Service Pro's
		</h2>

		<div class="clearfix">

			<c:set var="isPopup" value="true" />
			<c:if test="${ProviderInfo.isExternal == true}">
				<c:set var="isPopup" value="false" />
			</c:if>

			<c:choose>
				<c:when
					test="${not empty ProviderInfo.companyPublicInfo.teamMembers}">
					<c:forEach items="${ProviderInfo.companyPublicInfo.teamMembers}"
						var="teamMembers">
						<c:if test="${teamMembers.resourceStateId == 6}">
							<div class="credlist clearfix" style="margin-top: 5px;">
								<iframe width="65x" scrolling="no" height="65px" marginwidth="0"
									marginheight="0" style="float: left; margin-right: 5px;"
									frameborder="0"
									src="${contextPath}/providerProfileInfoAction_loadPhoto.action?max_width=65&max_height=65&resourceId=${teamMembers.resourceId}&link=1&isPopup=${isPopup}"
									style="float: left;">
								</iframe>
								<h4>
									<a
										href="${contextPath}/providerProfileInfoAction_execute.action?resourceId=${teamMembers.resourceId}&popup=true">${teamMembers.firstName}
										<c:choose>
											<c:when test="${not empty teamMembers.lastName}">
												<c:out value="${fn:substring(teamMembers.lastName,0,1)}" />.
																	</c:when>
										</c:choose> </a>
								</h4>
								ID#${teamMembers.resourceId}
								<br />
								<br />
								${teamMembers.title}
								<br>
							</div>
						</c:if>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<p>
						Currently, there are no team members on file.
					</p>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>
