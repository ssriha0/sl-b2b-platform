<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div class="clearfix">
	<div class="left" style="width:49%;">
		<dl>
			<dt>Name of <abbr title="Select Provider Network">SPN</abbr></dt>
			<dd><c:out value="${spnHeader.spnName}" /></dd>
		</dl>
		<dl>
			<dt>Contact Information</dt>
			<dd>
				Email: <c:out value="${spnHeader.contactName}" /> &lt;<a href="${spnHeader.contactEmail}"><c:out value="${spnHeader.contactEmail}" /></a>&gt;<br/>
				Phone: <c:out value="${spnHeader.contactPhone}" />
			</dd>
		</dl>
		<dl>
			<dt class="clearfix"><span>Description</span><span class="open"></span><span class="closed"></span></dt>
			<dd><c:out value="${spnHeader.spnDescription}" /></dd>
		</dl>
		<dl>
			<dt class="clearfix"><span>Special Instructions</span><span class="open"></span><span class="closed"></span></dt>
			<dd><c:out value="${spnHeader.spnInstruction}" /></dd>
		</dl><br>
	</div>
	<div class="right" style="width:49%;">
		<dl>
			<dt class="clearfix"><span>Services &amp; Skills</span><span class="open"></span><span class="closed"></span></dt>
			<dd>
				<ul>
					<c:if test="${empty approvalItems.viewServicesAndSkills}">
						<li>No services selected.</li>
					</c:if>
					<c:forEach items="${approvalItems.viewServicesAndSkills}" var="aService">
						<li>${aService}</li>
					</c:forEach>
				</ul>
				<!-- a href="#">View More &raquo;</a-->
			</dd>
		</dl>
		<dl>
			<dt class="clearfix"><span>Approval Criteria &amp; Credentials</span><span class="open"></span><span class="closed"></span></dt>
			<dd>
				<ul>
					<li>	
						<strong>Credentials</strong><br />
						Resource: <c:forEach items="${lookupsVO.resCredCategoriesWithTypes}" var="credentialIter" varStatus="s">
							${credentialIter.description} 
							
							<c:choose>
								<c:when test = "${s.last}"  >
								 &nbsp;
								</c:when>
								<c:otherwise>
								 ,
								</c:otherwise>
							</c:choose>														
							
						</c:forEach>
						<c:if test="${empty lookupsVO.resCredCategoriesWithTypes}">N/A</c:if>
						<br />
						Company:  <c:forEach items="${lookupsVO.vendorCredCategoriesWithTypes}" var="vendorCredentialIter" varStatus="s">
							${vendorCredentialIter.description} 
							
							<c:choose>
								<c:when test = "${s.last}"  >
								 &nbsp;
								</c:when>
								<c:otherwise>
								 ,
								</c:otherwise>
							</c:choose>														
							
						</c:forEach>
						<c:if test="${empty lookupsVO.vendorCredCategoriesWithTypes}">N/A</c:if>
					</li>

					<li>
						<strong>Minimum Rating:</strong>

						<c:choose>								
							<c:when test="${approvalItems.selectedMinimumRating != null}">			
								minimum ${approvalItems.selectedMinimumRating} stars
							</c:when>
							<c:otherwise>
								N/A
							</c:otherwise>
						</c:choose>
							
					</li>

					<li>
						<strong>Languages: </strong>
						<c:set var="xIter" value="0" />
					
						<c:forEach items="${lookupsVO.allLanguages}" var="langIter" varStatus="s">
							${langIter.description} 
							
							<c:choose>
								<c:when test = "${s.last}"  >
								 &nbsp;
								</c:when>
								<c:otherwise>
								 ,
								</c:otherwise>
							</c:choose>														
							
						</c:forEach>
						
						<c:if test="${empty lookupsVO.allLanguages}">N/A</c:if>
						
						<br /><strong>Minimum Completed Service Orders: </strong> 
							<c:if test="${empty approvalItems.minimumCompletedServiceOrders}">
								N/A
							</c:if>
							${approvalItems.minimumCompletedServiceOrders}
						<br /><strong>Meeting with Provider:</strong>
							<c:if test="${approvalItems.meetingRequired}">
								Required
							</c:if>
							<c:if test="${!approvalItems.meetingRequired}">
								Not Required
							</c:if>
					</li>

					<li>
						<strong>Insurance Minimum Coverage</strong>
						<c:if test="${approvalItems.vehicleLiabilitySelected}">
							<br />
							<c:if test="${approvalItems.vehicleLiabilityAmt > 0}">
								<fmt:formatNumber type="currency" value="${approvalItems.vehicleLiabilityAmt}" />
							</c:if>	
							Vehicle Liability
							<c:if test="${approvalItems.vehicleLiabilityVerified}">
								<em>Verified</em>
							</c:if>	
						</c:if>
						<c:if test="${!approvalItems.vehicleLiabilitySelected}">
							<br />Vehicle Liability: None
						</c:if>
						<c:if test="${approvalItems.workersCompensationSelected}">
							<br />
							Worker's Compensation
							<c:if test="${approvalItems.workersCompensationVerified}">
								<em>Verified</em>
							</c:if>	
						</c:if>
						<c:if test="${!approvalItems.workersCompensationSelected}">
							<br />Worker's Compensation: None
						</c:if>
						<c:if test="${approvalItems.commercialGeneralLiabilitySelected}">
							<br />
							<c:if test="${approvalItems.commercialGeneralLiabilityAmt > 0}">
								<fmt:formatNumber type="currency" value="${approvalItems.commercialGeneralLiabilityAmt}" />
							</c:if>	
							Commercial General Liability
							<c:if test="${approvalItems.commercialGeneralLiabilityVerified}">
								<em>Verified</em>
							</c:if>	
						</c:if>
						<c:if test="${!approvalItems.commercialGeneralLiabilitySelected}">
							<br />Commercial General Liability: None
						</c:if>
					</li>
				</ul>
			</dd>
		</dl>
		<c:set var="docReturn" value="0" />
		<c:set var="elecAgree" value="0" />
		<c:set var="informationOnly" value="0" />
		<dl>
			<dt class="clearfix"><span>Documents</span><span class="open"></span><span class="closed"></span></dt>
			<dd>
				<ul>
					<li><strong>Sign &amp; Return</strong><br/>
						<c:forEach items="${approvalItems.spnDocumentList}" var="docListItem"><c:if test="${docListItem.type == 'Sign & Return'}"><c:if test="${docReturn == '1'}">, </c:if>${docListItem.title}<c:set var="docReturn" value="1" /></c:if></c:forEach><c:if test="${docReturn == '0'}">None</c:if>
					</li>
					<li><strong>Electronic Signature</strong><br/>
						<c:forEach items="${approvalItems.spnDocumentList}" var="docListItem"><c:if test="${docListItem.type == 'Electronic Signature'}"><c:if test="${elecAgree == '1'}">, </c:if>${docListItem.title}<c:set var="elecAgree" value="1" /></c:if></c:forEach><c:if test="${elecAgree == '0'}">None</c:if>
					</li>
					<li><strong>Information Only</strong><br/>
						<c:forEach items="${approvalItems.spnDocumentList}" var="docListItem"><c:if test="${docListItem.type == 'Information Only'}"><c:if test="${informationOnly == '1'}">, </c:if>${docListItem.title}<c:set var="informationOnly" value="1" /></c:if></c:forEach><c:if test="${informationOnly == '0'}">None</c:if>
					</li>
				</ul>
			</dd>
		</dl>
	</div>
</div>
