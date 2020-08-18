<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="sbTools" class="box">
	<h3>Marketplace Tools</h3>
	<ul>
		<li class="sb-searchportal"><a href="adminSearch_execute.action">Search
				Portal</a></li>


		<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 2}">
			<tags:security actionName="pbController_execute">
				<li class="sb-wfm"><a href="pbController_execute.action">Workflow
						Monitor</a></li>
			</tags:security>
		</c:if>
		<li class="sb-awf"><a
			href="<s:url action="powerAuditorWorkflowAction" />">Auditor
				Workflow</a></li>
		<!-- sl-21142 -->
		<tags:security actionName="lmsFileGetAction" isAdmin="true">
		<li class="LMS Integration Tool"><a
			href="<s:url value='%{contextPath}/lmsFileGetAction_getLmsDetailHistory.action'/>">
				LMS Integration Tool</a></li>
				</tags:security>
		<!-- 21142 -->
	</ul>
</div>