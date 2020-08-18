<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="clearfix">
	<div class="left" style="width:49%;">
		<dl>
			<dt>Campaign Created By</dt>
			<dd>${campaignHeader.modifiedBy}</dd>
		</dl>
		<dl>
			<dt>Dates</dt>
			<dd>
				<fmt:formatDate value="${campaignHeader.startDate}" pattern="MM/dd/yyyy" /> -
				<fmt:formatDate value="${campaignHeader.endDate}" pattern="MM/dd/yyyy" />
			</dd>
		</dl>
		<dl>
			<dt>Specific Provider Firms Invited</dt>
			<dd>
				<c:forEach items="${providerList}" var="provider">
					 ${provider.label} (ID#${provider.value})<br/>
				</c:forEach>
			</dd>
		</dl>
	</div>
	<div class="right" style="width:49%;">
		<dl style="width:100%;">
			&nbsp;
		</dl>
	</div>
</div>