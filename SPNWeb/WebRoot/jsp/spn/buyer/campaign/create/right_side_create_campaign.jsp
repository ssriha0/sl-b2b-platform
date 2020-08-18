<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />

<div id="rightSideData">
	<c:if test="${spnHeader.contactName != null}">
		<dl>
			<dt>
				Name of
				<abbr title="Select Provider Network">SPN</abbr>
			</dt>
			<dd>
				<s:property value="spnHeader.spnName" />
			</dd>
		</dl>
		<dl>
			<dt>
				Contact Information
			</dt>
			<dd>
				<s:if test="spnHeader">
					<s:property value="spnHeader.contactName" /> &lt;
				<a href="mailto:<s:property value='spnHeader.contactEmail' />"><s:property
							value="spnHeader.contactEmail" />
					</a>&gt;
			</s:if>
			</dd>
		</dl>
		<dl>
			<dt class="clearfix">
				<span>Description</span>
			</dt>
			<dd>
				<s:property value="spnHeader.spnDescription" />
				<%--  <a href="#">View More &raquo;</a> --%>
			</dd>
		</dl>
		<dl>
			<dt class="clearfix">
				<span>Special Instructions</span>
			</dt>
			<dd>
				<s:property value="spnHeader.spnInstruction" />
				<%--  <a href="#">View More &raquo;</a> --%>
			</dd>
		</dl>
		<dl>
			<dt class="clearfix">
				<span>Services &amp; Skills</span>
			</dt>
			<dd>
				<ul>
					<c:if test="${empty approvalItems.viewServicesAndSkills}">
						<li>
							No services selected.
						</li>
					</c:if>
					<c:forEach items="${approvalItems.viewServicesAndSkills}"
						var="aService">
						<li>
							${aService}
						</li>
					</c:forEach>
				</ul>
				<%-- <a href="#">View More &raquo;</a> --%>
			</dd>
		</dl>
		<dl>
			<dt class="clearfix">
				<span>Approval Criteria &amp; Credentials</span>
			</dt>
			<dd>
				<ul>
					<li>
						<strong>Credentials</strong>
						<br />
						Resource:
						<c:forEach
							items="${selectedApprovalItemsVO.resCredCategoriesWithTypes}"
							var="credentialIter" varStatus="s">
							${credentialIter.description} 
							
							<c:choose>
								<c:when test="${s.last}">
								 &nbsp;
								</c:when>
								<c:otherwise>
								 ,
								</c:otherwise>
							</c:choose>

						</c:forEach>
						<c:if
							test="${empty selectedApprovalItemsVO.resCredCategoriesWithTypes}">N/A</c:if>
						<br />
						Company:
						<c:forEach
							items="${selectedApprovalItemsVO.vendorCredCategoriesWithTypes}"
							var="vendorCredentialIter" varStatus="s">
							${vendorCredentialIter.description} 
							
							<c:choose>
								<c:when test="${s.last}">
								 &nbsp;
								</c:when>
								<c:otherwise>
								 ,
								</c:otherwise>
							</c:choose>

						</c:forEach>
						<c:if
							test="${empty selectedApprovalItemsVO.vendorCredCategoriesWithTypes}">N/A</c:if>
					</li>

					<li>
						<strong>Minimum Rating</strong>

						<c:choose>
							<c:when
								test="${spnApprovalItems.selectedMinimumRating != null || spnApprovalItems.isNotRated}">
								<c:if test="${spnApprovalItems.selectedMinimumRating != null}">
									minimum ${spnApprovalItems.selectedMinimumRating} stars
								</c:if>
								<c:if test="${spnApprovalItems.isNotRated}">
									<c:if test="${spnApprovalItems.selectedMinimumRating != null}">
									,
									</c:if>
									 Not Rated
								</c:if>
							</c:when>
							<c:otherwise>
							
								N/A
							</c:otherwise>
						</c:choose>
					</li>

					<li>
						<strong>Languages: </strong>

						<c:forEach items="${selectedApprovalItemsVO.allLanguages}"
							var="langIter" varStatus="s">
							${langIter.description} 
							
							<c:choose>
								<c:when test="${s.last}">
								 &nbsp;
								</c:when>
								<c:otherwise>
								 ,
								</c:otherwise>
							</c:choose>

						</c:forEach>

						<c:if test="${empty selectedApprovalItemsVO.allLanguages}">N/A</c:if>
						<br />

						<strong>Minimum Completed Service Orders: </strong>
						<c:if
							test="${empty spnApprovalItems.minimumCompletedServiceOrders}">
							N/A
						</c:if>
						${spnApprovalItems.minimumCompletedServiceOrders}
						<br />
						<strong>Meeting with Provider:</strong>
						<c:if test="${spnApprovalItems.meetingRequired}">
							Required
						</c:if>
						<c:if test="${!spnApprovalItems.meetingRequired}">
							Not Required
						</c:if>
					</li>

					<li>
						<strong>Insurance Minimum Coverage</strong>
						<c:if test="${spnApprovalItems.vehicleLiabilitySelected}">
							<br />
							<c:if test="${spnApprovalItems.vehicleLiabilityAmt > 0}">
								<fmt:formatNumber type="currency"
									value="${spnApprovalItems.vehicleLiabilityAmt}" />
							</c:if>	
						Vehicle Liability
						<c:if test="${spnApprovalItems.vehicleLiabilityVerified}">
								<em>Verified</em>
							</c:if>
						</c:if>
						<c:if test="${!spnApprovalItems.vehicleLiabilitySelected}">
							<br />Vehicle Liability: None
					</c:if>
						<c:if test="${spnApprovalItems.workersCompensationSelected}">
							<br />
						Worker's Compensation
						<c:if test="${spnApprovalItems.workersCompensationVerified}">
								<em>Verified</em>
							</c:if>
						</c:if>
						<c:if test="${!spnApprovalItems.workersCompensationSelected}">
							<br />Worker's Compensation: None
					</c:if>
						<c:if
							test="${spnApprovalItems.commercialGeneralLiabilitySelected}">
							<br />
							<c:if
								test="${spnApprovalItems.commercialGeneralLiabilityAmt > 0}">
								<fmt:formatNumber type="currency"
									value="${spnApprovalItems.commercialGeneralLiabilityAmt}" />
							</c:if>	
						Commercial General Liability
						<c:if
								test="${spnApprovalItems.commercialGeneralLiabilityVerified}">
								<em>Verified</em>
							</c:if>
						</c:if>
						<c:if
							test="${!spnApprovalItems.commercialGeneralLiabilitySelected}">
							<br />Commercial General Liability: None
					</c:if>
					</li>
					<c:if test="${recertificationBuyerFeatureInd == true}">
					<li>
					<strong>Background check - 2 year recertification:</strong>
							<c:if test="${spnApprovalItems.recertificationInd}">
								Required
							</c:if>
							<c:if test="${!spnApprovalItems.recertificationInd}">
								Not Required
							</c:if>
					
					</li>
					</c:if>
				</ul>
			</dd>
		</dl>

		  
				<jsp:include page="/jsp/spn/buyer/campaign/monitor/expanded_details/tab_campaign_routing_tiers.jsp" />
     
		

		<dl>
			<dt class="clearfix">
				<span>Documents</span>
			</dt>
			<dd>
				<ul>
					<li>
						<strong>Sign &amp; Return</strong>
						<br />
						<c:forEach items="${approvalItems.spnDocumentList}"
							var="docListItem">
							<c:if test="${docListItem.type == 'Sign & Return'}">
								<c:if test="${docReturn == '1'}">, </c:if>${docListItem.title}<c:set
									var="docReturn" value="1" />
							</c:if>
						</c:forEach>
						<c:if test="${docReturn == '0'}">None</c:if>
					</li>
					<li>
						<strong>Electronic Signature</strong>
						<br />
						<c:forEach items="${approvalItems.spnDocumentList}"
							var="docListItem">
							<c:if test="${docListItem.type == 'Electronic Signature'}">
								<c:if test="${elecAgree == '1'}">, </c:if>${docListItem.title}<c:set
									var="elecAgree" value="1" />
							</c:if>
						</c:forEach>
						<c:if test="${elecAgree == '0'}">None</c:if>
					</li>
					<li>
						<strong>Information Only</strong>
						<br />
						<c:forEach items="${approvalItems.spnDocumentList}"
							var="docListItem">
							<c:if test="${docListItem.type == 'Information Only'}">
								<c:if test="${informationOnly == '1'}">, </c:if>${docListItem.title}<c:set
									var="informationOnly" value="1" />
							</c:if>
						</c:forEach>
						<c:if test="${informationOnly == '0'}">None</c:if>
					</li>
				</ul>
			</dd>
		</dl>
	</c:if>
</div>
