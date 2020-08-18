<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="Admin.searchBody"/>
	</jsp:include>	

<!-- START TAB PANE -->
					
<div id="mainTabContainer" dojoType="dijit.layout.TabContainer"
	style="height: 2500px;">
	<c:set var="tabtitle" value="${title}" />
	<c:forEach items="${tabList}" var="tab">
		<c:choose>
			<c:when test="${tab.title == tabtitle}">
				<div  id="${tab.widgetId}"
				       title="${tab.title}"
					class="${tab.className}"
					dojoType="dijit.layout.ContentPane"
					href="${tab.action}"
					selected="${tab.selected}"
					refreshOnShow="true"
					>
							
				</div>
			</c:when>
									
			<c:otherwise>
				<div  id="${tab.widgetId}"
				     title="${tab.title}"
					class="${tab.className}"
					dojoType="dijit.layout.ContentPane"
					href="${tab.action}"
					selected="${tab.selected}"
					refreshOnShow="true"
					>
					</div>				
			</c:otherwise>
		</c:choose>
	
		
	</c:forEach>
	
</div>

<
<!-- END TAB PANE -->
