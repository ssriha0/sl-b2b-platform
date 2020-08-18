<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="dateDashboard">
	<c:if test="${empty dateString}">
		<c:set var="dateString" value="<%= com.newco.marketplace.utils.DateUtils.getHeaderDate()%>"></c:set>
	</c:if>
	<span id="date_dashboard">${dateString}</span>
</div>