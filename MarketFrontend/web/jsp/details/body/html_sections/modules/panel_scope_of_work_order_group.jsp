<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>

<c:set var="BUYER_PROVIDES_PART"
	value="<%=OrderConstants.SOW_SOW_BUYER_PROVIDES_PART%>" />
<c:set var="PROVIDER_PROVIDES_PART"
	value="<%=OrderConstants.SOW_SOW_PROVIDER_PROVIDES_PART%>" />
<c:set var="PARTS_NOT_REQUIRED"
	value="<%=OrderConstants.SOW_SOW_PARTS_NOT_REQUIRED%>" />

<c:set var="BUYER_ROLEID"
	value="<%=new Integer(OrderConstants.BUYER_ROLEID)%>" />
<c:set var="PROVIDER_ROLEID"
	value="<%=new Integer(OrderConstants.PROVIDER_ROLEID)%>" />
<c:set var="ROUTED_STATUS"
	value="<%=new Integer(OrderConstants.ROUTED_STATUS)%>" />
<c:set var="CLOSED_STATUS"
	value="<%=new Integer(OrderConstants.CLOSED_STATUS)%>" />
<c:set var="SIMPLE_BUYER_ROLEID"
	value="<%=new Integer(OrderConstants.SIMPLE_BUYER_ROLEID)%>" />


<c:set var="role" value="${roleType}" />



<c:set var="MAX_CHARACTERS_WITHOUT_SCROLLBAR"
	value="<%=OrderConstants.MAX_CHARACTERS_WITHOUT_SCROLLBAR%>" />

<c:forEach items="${serviceOrders}" var="so">
	<!-- NEW MODULE/ WIDGET-->
	<div dojoType="dijit.TitlePane" title="Scope of Work: ${so.id}" 
		class="contentWellPane" open="false" >
		
		
		<p>		
			<c:if
				test="${roleType == BUYER_ROLEID || (roleType == PROVIDER_ROLEID && summaryDTO.status != ROUTED_STATUS && summaryDTO.status != CLOSED_STATUS)}">
				<strong>Service Location Notes</strong>
				<br />
				${so.locationNotes}
			</c:if>
		</p>
		<br />
		
		
		
		
	<c:if test="${groupOrderId != null}">
		<c:choose>
			<c:when
				test="${role == BUYER_ROLEID || (role == PROVIDER_ROLEID && so.status != ROUTED_STATUS)}">
				<hr />
				<table cellpadding="0" cellspacing="0"
					class="adjustedTableRowPadding">
					<c:if test="${so.selByerRefDTO != null}">
						<tr>
							<td colspan="2">
								<fmt:message bundle='${serviceliveCopyBundle}'
									key='details.buyer.ref' />
							</td>
						</tr>
						<tr>
							<td>

								<table cellpadding="0" cellspacing="0" class>
									<c:forEach var="byerRef"
										items="${so.selByerRefDTO}"
										varStatus="rowCounter">
										<c:if test="${rowCounter.count % 2 == 1}">
											<tr>
												<td width="150">
													<strong>${byerRef.refType}</strong>
												</td>
												<td width="80">
													${byerRef.refValue}
												</td>
											</tr>
										</c:if>
									</c:forEach>
								</table>

							</td>
							<td width="150"></td>
							<td>


								<table cellpadding="0" cellspacing="0" class>
									<c:forEach var="byerRef"
										items="${so.selByerRefDTO}"
										varStatus="rowCounter">
										<c:if test="${rowCounter.count % 2 == 0}">
											<tr>
												<td width="150">
													<strong>${byerRef.refType}</strong>
												</td>
												<td width="80">
													${byerRef.refValue}
												</td>
											</tr>
										</c:if>
									</c:forEach>
								</table>

							</td>
						</tr>

					</c:if>
				</table>
			</c:when>
		</c:choose>
	</c:if>		
		
		
		
		
		<strong>Job Information</strong>
		<hr />
		<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
			<tr>
				<td width="200">
					<strong>Main Service Category</strong>
				</td>
				<td width="300">
					${so.mainServiceCategory}
				</td>
			</tr>

		</table>

		<c:if test="${so.partsSupplier==PROVIDER_PROVIDES_PART}">
			<p>
				${so.jobInfo}
			</p>
			<br />
		</c:if>


		<c:if test="${so.partsSupplier==BUYER_PROVIDES_PART}">
			<p>
				${so.jobInfoOptional1}
			</p>
			<br />
		</c:if>


		<c:if test="${so.partsSupplier==PARTS_NOT_REQUIRED}">
			<p>
				${so.jobInfoOptional2}
			</p>
			<br />
		</c:if>

		<br />

		<!-- NEW NESTED MODULE - Task List -->
		<c:forEach var="task" items="${so.taskList}">
			<div dojoType="dijit.TitlePane" title="${task.title}" id=""
				class="dijitTitlePaneSubTitle">
				<table cellpadding="0" cellspacing="0"
					class="adjustedTableRowPadding">
					<tr>
						<td width="200">
							<strong>Category</strong>
						</td>
						<td width="300">
							${task.category}
						</td>
					</tr>
					<tr>
						<td>
							<strong>Sub-Category</strong>
						</td>
						<td>
							${task.subCategory}
						</td>
					</tr>
					<tr>
						<td>
							<strong>Skill</strong>
						</td>
						<td>
							${task.skill}
						</td>
					</tr>
				</table>
				<table cellpadding="0" cellspacing="0">
					<c:forEach var="question" items="{task.questionList}">
						<tr>
							<td width="400">
								<%-- ${question.label} --%>
							</td>
							<td width="200">
								<%-- ${question.value} --%>
							</td>
						</tr>
					</c:forEach>
				</table>
				<p>
					<strong>Task Comments</strong>
					<br />
					<%-- 
					<c:choose>
						<c:when
							test="${fn:length(task.comments) > MAX_CHARACTERS_WITHOUT_SCROLLBAR}">
							<div class="inputArea"
								style="height: 200px; width: 600px; background: #EDEDED">
								${task.comments}
							</div>
						</c:when>
						<c:otherwise>
						${task.comments}
					</c:otherwise>
				</c:choose>
				--%>
			</p>
			</div>
		</c:forEach>
	</div>
</c:forEach>