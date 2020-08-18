
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%@ attribute name="action" required="true"%>
<%@ attribute name="paginationVO" required="true"%>
<%@ attribute name="resultSet" required="true"%>

<c:if test="${resultSet != null && resultSet[0] != null}">
	<b>
		50 Results per page. 1|2|3| previous next
	</b>
</c:if>



