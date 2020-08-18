<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${not empty CategoriesList}">
			<c:forEach var="catList" items="${CategoriesList}" varStatus="category">
				<li id="${catList.nodeId}term" title="${catList.nodeName}" 
				onclick="selectableItemClick(this,'category_list',jQuery(this).parent().parent(),true); ">
				<img src="/ServiceLiveWebUtil/images/icons/blankListIcon.gif" />
				<span><a href="#" onclick="return false;">${catList.nodeName}</a></span>
				</li>								
			</c:forEach>
		</c:if>