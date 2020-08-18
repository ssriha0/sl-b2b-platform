<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

				<div id="header">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
										
					<div id="pageHeader">
						<tiles:insertDefinition name="newco.base.headerTitle"> 
						<tiles:putAttribute name="headerTitleImage" value="${staticContextPath}/images/so_wizard/sowHdr_scopeOfWork.gif"></tiles:putAttribute>
						<tiles:putAttribute name="headerTitleAlt" value="Create Service Order"></tiles:putAttribute>
						</tiles:insertDefinition>					
					</div>
					<tiles:insertDefinition name="newco.base.headerDate"/>

<!--<div id="pageHeader">
	 <div>
		<img src="${staticContextPath}/images/so_wizard/sowHdr_scopeOfWork.gif"
			alt="Create Service Order" />
		<p class="templateSelect">
			<select>
				<option>
					Template Name
				</option>
			</select>
			<a href="">Load Template</a> 
	</div>

</div>
-->
<div id="date" align="right">
	<p id="accountBalance">
		Available Balance:
		<span id ="available_balance">$ Retrieving...</span>
		<br />
		<c:if test="${roleType eq 3}">
		Current Balance: <span id ="current_balance">$ Retrieving...</span>
		</c:if>
	</p>
</div>
