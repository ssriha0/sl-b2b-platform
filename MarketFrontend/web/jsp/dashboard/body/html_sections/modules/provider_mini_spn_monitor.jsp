<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dashboard/main.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dashboard/iehacks.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/top-section.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dashboard/dashboard.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dashboard/tooltips.css" />
<!-- begin new box for provier SPN -->
<s:form id="miniSpnMonitor" name="miniSpnMonitor">
	<s:hidden name="selectedSpnId" id="selectedSpnId" value="" />	
	<div class="leftTileItem" id="dbTile_spnMonitor">
		<div class="titleContainer">
			<div class="titleBar">
				<h2><abbr title="Select Provider Network">SPN</abbr> Monitor</h2>
				<a class="moreLink" href="${contextPath}/spnMonitorAction_loadSPNMonitor.action">View &raquo;</a>
			</div>
		</div>
		<div class="contentContainer">
			<div class="content">
				<!-- begin loop for Buyer -->			
				<c:set var="tempBuyerName" value="1"/>
				<s:iterator id="spnObj" value="miniSPNList" status="inner">								
					<c:if test="${tempBuyerName != buyerName}">	
				   	 <h4>${buyerName}</h4><br>										
					</c:if>	
					<c:set var="tempBuyerName" value="${buyerName}"/>
				
					<!-- begin loop for each spn -->
					<ul>
						<li><a href="${contextPath}/spnMonitorAction_loadSPNMonitor.action?selectedSpnId=${spnId}" title="SPN Name">${spnName}</a></li>
						<li><strong>Membership Status </strong>${membershipStatus}</li>
					</ul>
					<br>
				</s:iterator>		
				<!-- end loop for each SPN -->					
			</div>
		</div>
		<div class="shadowBottom"></div>
	</div>
</s:form>
<!-- end new box for provier SPN -->

