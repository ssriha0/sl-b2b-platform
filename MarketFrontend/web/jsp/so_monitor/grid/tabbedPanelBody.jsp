<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />



<div id="mainTabContainer" dojoType="dijit.layout.TabContainer"
	style="width: 969px; height: 569px;" >
	<c:forEach var="tab" items="${tabList}">
		<div id="${tab.id}" dojoType="dijit.layout.ContentPane"
			title="${tab.title} (${tab.tabCount})" 
			href="${contextPath}/updateTab.action?status=${tab.title}">
		</div>
	</c:forEach>
	
</div>

