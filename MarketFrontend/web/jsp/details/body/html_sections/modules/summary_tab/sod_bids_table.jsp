<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<table width="100%" cellpadding="0" cellspacing="0" border="0">
	<thead>
		<tr>
			<th colspan="3">${bidTableTitle}</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${bidList}" var="bid">
			<tr>
				<td class="col1">
					<strong>${bid.bidder}:</strong> ${bid.dateOfBid}
				</td>
				<td class="col2">
					Expires: ${bid.bidExpirationDatepicker}
				</td>
				<td class="col3">
					<fmt:formatNumber type="currency" currencySymbol="$">${bid.total}</fmt:formatNumber>
				</td>
			</tr>
			<c:if test="${showBidTableComment}">
				<tr>
					<td colspan="3" class="soBidComment">
						${bid.comment}
					</td>
				</tr>
			</c:if>
		</c:forEach>
	</tbody>
</table>
