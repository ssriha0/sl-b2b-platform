<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:if test="${not empty serviceOrderStatusVOList}">
	<c:forEach var="subStatusList" items="${subStatusList}" varStatus="category">
		<li id="status${subStatusList.id}term" title="${subStatusList.descr}">
				<span><a href="#" onclick="selectableItemClick(jQuery(this).parent().parent(),'status_list',jQuery(this).parent().parent().parent().parent()); return false;">${subStatusList.descr}</a></span>
		</li>								
	</c:forEach>
		</c:if>