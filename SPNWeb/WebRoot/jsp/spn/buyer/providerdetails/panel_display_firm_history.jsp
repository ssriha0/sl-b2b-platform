<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

Service Provider Change History Tab
<table id="historyTable" cellpadding="0" cellspacing="0" border="0">
	<tr>
		<th>
			Date/Change<br/>
			Entered by
		</th>
		<th>
			Network
		</th>
		<th>
			Action
		</th>
		<th>
			Reason
		</th>
	</tr>
	<c:forEach items="${firmChangeHistoryList}" var="row">
		<tr>
			<td>
				${row.dateChanged}<br/>
				{time}<br/>
				<b>${row.changeEnteredByName}</b>
				<c:if test="${row.changeEnteredByID != null && row.changeEnteredByID != ''}">
					&nbsp; (ID# ${row.changeEnteredByID})
				</c:if>
			</td>
			<td>
				${row.network}
			</td>
			<td>
				<b>${row.action}</b>
				<br/>
				${row.comment}
			</td>
			<td>
				${row.reason}
			</td>
		</tr>
	</c:forEach>
</table>

