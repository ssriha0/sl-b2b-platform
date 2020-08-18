<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="role" value="${SERVICE_ORDER_CRITERIA_KEY.roleId}" />
<c:if test="${!empty serviceOrderStatusVOList}">
<c:forEach  items="${serviceOrderStatusVOList}" var="statusSubstatusList" varStatus="mainCat">
	<c:if test="${!isNonFundedBuyer || statusSubstatusList.statusId != 165}">
	   <li id="status${statusSubstatusList.statusId}term" title="<fmt:message bundle='${serviceliveCopyBundle}' key='workflow.state.${role}.${statusSubstatusList.statusId}'/>" class="selectableItem" 
		onclick="loadSubStatusList(this, ${statusSubstatusList.statusId},${role }); stopClickPropagation(event);">
	  <img src="/ServiceLiveWebUtil/images/icons/plusIcon.gif" />
	  <span><a href="#" onclick="selectableItemClick(jQuery(this).parent().parent(),'status_list'); return false;"><fmt:message bundle='${serviceliveCopyBundle}' key='workflow.state.${role}.${statusSubstatusList.statusId}'/></a></span>
		<ul class="subList" onclick="stopClickPropagation(event);"></ul>
	  </li>
	</c:if>
</c:forEach>
</c:if>