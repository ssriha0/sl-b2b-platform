<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

				<div id="header">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
					
					<div id="pageHeader">
						<tiles:insertDefinition name="newco.base.headerTitle"> 
						<tiles:putAttribute name="headerTitleImage" value="${staticContextPath}/images/so_details/SODhdrSummary.gif"></tiles:putAttribute>
						<tiles:putAttribute name="headerTitleAlt" value="SODhdrSummary.gif"></tiles:putAttribute>
						</tiles:insertDefinition>					
					</div>
					<tiles:insertDefinition name="newco.base.headerDate"/>				
				</div>
<!-- 
<div id="header">

	<div id="pageHeader">
			<img src="${staticContextPath}/images/so_details/SODhdrSummary.gif" alt="SODhdrSummary.gif" />
	</div>
	<div id="date">
		Monday, September 24, 2007 10:51 AM
	</div>
</div>

 -->



