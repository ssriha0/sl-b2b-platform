<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="PowerBuyer"/>
	</jsp:include>	

<!-- START TAB PANE -->
					<!--  refreshOnShow="true" -->
<div id="mainTabContainer" dojoType="dijit.layout.TabContainer"
	style="height: 1200px;" cacheContent="false">
	<c:set var="tabtitle" value="${title}" />
	<c:forEach items="${tabList}" var="tab">
		<c:choose>
			<c:when test="${tab.title == tabtitle}">
				<div  id="${tab.widgetId}"
				      title="${tab.title}"
					class="${tab.className}"
					dojoType="dijit.layout.ContentPane"
					<c:if test="${tab.title == 'Search'}">
						href="${tab.action}&PB_WF_MESSAGE=${PB_WF_MESSAGE}&ShowErrors=${ShowErrors}&fromWFM=true"
					</c:if>
					<c:if test="${tab.title != 'Search'}">
						href="${tab.action}?PB_WF_MESSAGE=${PB_WF_MESSAGE}&ShowErrors=${ShowErrors}"
					</c:if>
					selected="${tab.selected}" 
					<c:if test="${tab.action == 'pbClaimedTab_execute.action'}">
						refreshOnShow="true"
					</c:if>
					>	
				</div>												
				
			</c:when>
									
			<c:otherwise>
				<div  id="${tab.widgetId}"
				     title="${tab.title}"
					class="${tab.className}"
					dojoType="dijit.layout.ContentPane" 
					<c:if test="${tab.title == 'Search'}">
						href="${tab.action}&PB_WF_MESSAGE=${PB_WF_MESSAGE}&ShowErrors=${ShowErrors}&fromWFM=true"
					</c:if>
					<c:if test="${tab.title != 'Search'}">
						href="${tab.action}?PB_WF_MESSAGE=${PB_WF_MESSAGE}&ShowErrors=${ShowErrors}"
					</c:if>
					selected="${tab.selected}" 
					<%--<s:if test="${tab.action == 'pbClaimedTab_execute.action'}">
						refreshOnShow="true"
					</s:if>--%>				
					>
					</div>				
			</c:otherwise>
		</c:choose>
	
		
	</c:forEach>
	
</div>


<!-- END TAB PANE -->
