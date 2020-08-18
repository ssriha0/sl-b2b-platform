<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${!empty MainCategoriesList}">
<c:forEach  items="${MainCategoriesList}" var="mainCatList" varStatus="mainCat">
	<li id="${mainCatList.nodeId}term" title="${mainCatList.nodeName}" class="selectableItem" 
		onclick="loadSubCategoryList(this, ${mainCatList.nodeId});  
		stopClickPropagation(event);">
		<img src="/ServiceLiveWebUtil/images/icons/plusIcon.gif" />
		<span><a href="#" onclick="selectableItemClick(jQuery(this).parent().parent(),'category_list'); return false;">${mainCatList.nodeName}</a></span>
		<ul class="subList" onclick="stopClickPropagation(event);"></ul>
	</li>
</c:forEach>
</c:if>