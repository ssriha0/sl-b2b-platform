<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<script type="text/javascript" djConfig="isDebug: false ,parseOnLoad: true">
	
</script>

	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="PowerBuyer.claimed"/>
	</jsp:include>	
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<s:form action="pbClaimedTab_submit" id="pbClaimedTab_submit"
	theme="simple" enctype="multipart/form-data" method="POST">
		<c:if test="${PB_WF_MESSAGE != null}">
			<p class="errorMsg">
				<fmt:message bundle="${serviceliveCopyBundle}" key="${PB_WF_MESSAGE}" />
			</p>
			<%session.removeAttribute("PB_WF_MESSAGE");%>
		</c:if>	
<s:if test="%{#request.ShowErrors == 'true'}">
	<jsp:include page="validationMessages.jsp" />
	<%session.setAttribute("ShowErrors",""); %>
</s:if>
	<div class="content">
		<h4>
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="pb.claim.grid.title" />
		</h4>
		
	<div class="grayTableContainer" style="width: 697px; height:600px; overflow-style:auto">
		<table cellpadding="0" cellspacing="0"
			class="globalTableLook">
			<tr>
				<th class="col1 odd">
					<fmt:message bundle="${serviceliveCopyBundle}"
						key="grid.label.sogroupid" />
				</th>
				<th class="col2 even">
					<fmt:message bundle="${serviceliveCopyBundle}"
						key="pb.claim.date.time" />
				</th>
				<th class="col3 odd">
					<fmt:message bundle="${serviceliveCopyBundle}"
						key="grid.label.soid" />
				</th>
				<th class="col4 even">
					<fmt:message bundle="${serviceliveCopyBundle}"
						key="grid.label.title" />
				</th>
				<th class="col5 odd">
				</th>
			</tr>
				<c:forEach items="${orderList}" var="order">
					<tr>
						<td class="col1 odd">
							${order.parentGroupId}&nbsp;
						</td>
						<td class="col2 even">
							<fmt:formatDate value="${order.dateTime}"
								pattern="MM/dd/yy hh:mm a" />
						</td>
						<td class="col3 odd">
							${order.soId}
						</td>
						<td class="col4 even">
							${order.title}
						</td>
						<td class="col5 odd">
							<c:choose>
								<c:when test='${order.destinationTab == "SOW"}'>
									<a
										href="soWizardController.action?soId=${order.soId}&action=edit&cameFromWorkflowMonitor=${cameFromWorkflowMonitor}"
										target="_top"> <fmt:message
											bundle="${serviceliveCopyBundle}" key="pb.claim.rtn.to.order" />
									</a>
								</c:when>
								<c:otherwise>
									<a href="soDetailsController.action?soId=${order.soId}&cameFromWorkflowMonitor=${cameFromWorkflowMonitor}"
										target="_top"> <fmt:message
											bundle="${serviceliveCopyBundle}" key="pb.claim.rtn.to.order" />
									</a>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>

	</div>

</s:form>
