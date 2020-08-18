<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach  items="${stateList}" var="state" >
	<li id="${state.type}term" title="${state.descr}" class="selectableItem" onclick="selectableItemClick(this,'state_list');"><a href="#" onclick="return false;">${state.descr}</a></li>
</c:forEach>