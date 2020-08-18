<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="dashboardCommMonitorHeader">
	<a href=""><img align="right"
			src="${staticContextPath}/images/dashboard/more.gif" /> </a>
</div>
<div class="dashboardCommMonitorBody" style="height: auto; border-bottom: 0px;">
	<table cellpadding="0px" cellspacing="0px" class="globalTableLook">
		<thead>
			<tr class="">
				<th class="col1 first odd">
					From
				</th>
				<th class="col2 even textleft">
					Subject
				</th>
				<th class="col3 odd textleft">
					Date / Time
				</th>
			</tr>
		</thead>
		<c:forEach items="${commMonitorList}" var="row">
			<tr>
				<td class="col1 first odd">
					<c:choose>
						<c:when test="${row.from.value != null}">
															&nbsp;&nbsp;<a href="${row.from.value}">${row.from.label}</a>
						</c:when>
						<c:otherwise>
															&nbsp;&nbsp;${row.from.label}
														</c:otherwise>
					</c:choose>
				</td>
				<td class="col2 even textleft">
					<c:choose>
						<c:when test="${row.subject.value != null}">
							<a href="${row.subject.value}">${row.subject.label}&nbsp;</a>
						</c:when>
						<c:otherwise>
															${row.subject.label}&nbsp;
														</c:otherwise>
					</c:choose>
				</td>
				<td class="col3 last odd">
					 <fmt:formatDate value="${row.dateTime}" pattern ="MM/dd/yyyy"/>
					 <%-- 
						<br/>
						<fmt:formatDate value="${row.dateTime}" pattern ="hh:mm"/>
					--%>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
