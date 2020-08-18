<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@ page import="com.newco.marketplace.constants.Constants"%>
<%@ page import="com.newco.marketplace.web.constants.QuickLinksConstants"%>


<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<c:set var="role" value="${roleType}" />
<c:set var="soDetailsTab" value="soDetailsTab" />


<tags:security actionName="incidentTracker">
	<div id="widgetSOM_IncidentTracker" name="widgetSOM_IncidentTracker">
		<c:if test="${not empty associatedIncidents}">
			<!-- <div dojoType="dijit.TitlePane" title="Incident Tracker" id="widget_inc_tracker_${theId}"
				style="padding-top: 1px; width: 249px;" open="false">	 -->
			<div id="incidentTrackerPane" class="menugroupadmin_list">
				<p class="menugroupadmin_head" onClick="collapsePane(this);">
					<img class="l"
						src="${staticContextPath}/images/widgets/arrowDown.gif"></img>
					&nbsp;Incident Tracker
				</p>
				<div class="menugroupadmin_body" id="incidentTrackerPaneId">
					<div class="grayModuleContent">
						<span class="dijitInfoNodeInner"><a href=""></a> </span>
						<div class="dijitReset incidentTable">
							<table border="0" cellspacing="0" cellpadding="0">
								<thead>
									<th class="first">
										Age
									</th>
									<th>
										Incident
									</th>
									<th>
										Service Order
									</th>
								</thead>
								<c:forEach var="dto" items="${associatedIncidents}"
									varStatus="dtoIndex">
									<tr>
										<td>
											${dto.ageOfOrder}
										</td>
										<td>
											<a
												href='assurantEditIncident_displayPage.action?clientIncidentID=${dto.incidentId}&soID=${dto.soId}&monitorTab=${soDetailsTab}'>${dto.incidentId}</a>
										</td>
										<td>
											<a href='monitor/soDetailsController.action?soId=${dto.soId}'>${dto.soId}</a>
										</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
				</div>
			</div>
		</c:if>
	</div>
</tags:security>





