<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach  items="${marketList}" var="mList" >
<li id="${mList.type}term" title="${mList.descr}" class="selectableItem" onclick="selectableItemClick(this,'market_list','','',event);
	 stopClickPropagation(event);">
<a href="#" onclick="return false;">${mList.descr}</a>
</li>
</c:forEach>