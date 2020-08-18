<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach  items="${skillList}" var="skill" >
	<li id="${skill.id}term" title="${skill.descr}" class="selectableItem" onclick="selectableItemClick(this,'skill_list');"><a href="#" onclick="return false;">${skill.descr}</a></li>
</c:forEach>