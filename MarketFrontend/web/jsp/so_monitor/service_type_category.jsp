<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${not empty CategoriesList}">
			<c:forEach var="catList" items="${CategoriesList}" varStatus="category">
				<li id="${catList.nodeId}term" title="${catList.nodeName}" 
				onclick="loadSubSubCategoryList(this, ${catList.nodeId});">
				<img src="/ServiceLiveWebUtil/images/icons/blankListIcon.gif" />
					<span><a href="#" onclick="selectableItemClick(jQuery(this).parents('#${catList.nodeId}term'),'category_list',jQuery(this).parents('.selectableItem')); 
						return false;">${catList.nodeName}</a></span>
					<ul class="subList" onclick="stopClickPropagation(event);" />
				</li>								
			</c:forEach>
		</c:if>