<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<%--<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/main.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/iehacks.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/top-section.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/grid.css" />

--%>
<%-- Commented for 12/15/07 release
Note - This form tag is not required and is causing alignment issues in IE for the header section
COMMENT START-
<s:form action="http://www.sears.com" method="POST">
COMMENT END-
--%>

<script language="JavaScript" type="text/javascript" >
	function updateDashboardOrders(orderFilter)
	{
		var doc = document.getElementById("iframeID").contentWindow;
		doc.doSubmit(orderFilter, orderFilter);
	}
</script>

<div id="pageHeader">
<tiles:insertDefinition name="newco.base.headerTitle"> 
<tiles:putAttribute name="headerTitleImage" value="${staticContextPath}/images/dashboard/dashboard_header.gif"></tiles:putAttribute>
<tiles:putAttribute name="headerTitleAlt" value="Dashboard"></tiles:putAttribute>
</tiles:insertDefinition>
<%-- Commented for 12/15/07 release
COMMENT START- 		
		<div>
			<input type="radio" class="radioOffsets" name="orderFilter" CHECKED onchange="updateDashboardOrders('All');"/>
			<span class="antiRadioOffsets">All Orders</span>
			<input type="radio" class="radioOffsets" name="orderFilter" onchange="this.form.submit();"/>
			<span class="antiRadioOffsets">My Orders</span>
		</div>
COMMENT END-		
--%>		
</div>
<tiles:insertDefinition name="newco.base.headerDate"/> 
<%-- Commented for 12/15/07 release
COMMENT START- 		
</s:form>
COMMENT END-		
--%>
