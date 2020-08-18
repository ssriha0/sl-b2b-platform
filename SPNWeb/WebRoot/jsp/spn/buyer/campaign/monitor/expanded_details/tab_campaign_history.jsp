<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<div class="history">
	<table border="0" cellspacing="0">
		<thead>
			<tr>
				<th>
					Name
				</th>
				<th >
					Action
				</th>
				<th>
					Date
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${campaignHistoryRows}" var="row">
				<tr>
					<td>
						<b>
						${row.modifiedBy}
						<%-- <span>ServiceLiveAdmin</span> --%>
						</b>
					</td>
					<td >
						<span class="sm">${row.status}</span>
					</td>
					<td>
						<span class="sm">
							<fmt:formatDate value="${row.modifiedDate}" pattern="MMM d yyyy hh:mm a" />							
						</span>
					</td>
				</tr>
							
					<tr class="border-bottom">
						<td colspan="3" >
							<c:choose>
								<c:when test="${row.comments != null}">
									<span class="comments">
										${row.comments}
									</span>
								</c:when>
								<c:otherwise>
									&nbsp;
								</c:otherwise>							
							</c:choose>
						</td>
					</tr>
				
			</c:forEach>
		</tbody>
	</table>


</div>