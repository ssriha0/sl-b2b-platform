<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/slider.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/main.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/iehacks.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/top-section.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/tooltips.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/service_order_wizard.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/registration.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/buttons.css" />

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<s:form action="spnMemberManager_intiPageView" id="spnMemberManagerForm"
	name="spnMemberManagerForm" theme="simple"
	enctype="multipart/form-data">
	<input type="hidden" name="documentSelection" id="documentSelection" />
	<c:choose>
		<c:when test="${hasResults == false}">
			<div style="padding-top: 15px; padding-left: 15px">
				There are providers in this Select Provider Network that meet the
				search criteria.
			</div>
		</c:when>
		<c:otherwise>

			<table width="100%" border=0>
				<tr>
					<td colspan=2>
						Market Filter:
						<s:select list="{{'Chicago'},{'Dallas'}}" theme="simple">
						</s:select>
					</td>
				</tr>

				<tr>
					<td colspan=2>
						<table class="scrollerTableHdr spnTableHdr" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="column1">
									Select
								</td>
								<td class="column2" style="padding-left: 5px">
									Provider
								</td>
								<td class="column3">
									Status
								</td>
								<td class="column4">
									Attachment
								</td>
								<td class="column5">
									Total Orders
								</td>
							</tr>
						</table>
						<table class="spnTable" cellpadding="0" cellspacing="0">
							<c:forEach items="${results}" var="row">
								<tr>
									<td class="column1">
										<s:checkbox name="selectedCheckbox" theme="simple" />
									</td>

									<td class="column2" style="padding-left: 5px">
										${row.firstName} ${row.lastName} (ID# ${row.resourceId})
										<br />
										<b>Company ID#</b> ${row.vendorId}
									</td>
									<td class="column3">
										<fmt:message bundle="${serviceliveCopyBundle}"
											key="spn.status.${row.spnStatusId}" />
									</td>
									<td class="column4">
										<div id="docs_hidden_${row.resourceId}" style="display: block" >
											<a onclick="hideDivsEndingWith('hidden_' + ${row.resourceId}),showDivsEndingWith('shown_' + ${row.resourceId})">
												<b>view docs</b>
											</a>
										</div>
										<div id="docs_shown_${row.resourceId}" style="display: none" >
											<a onclick="hideDivsEndingWith('shown_' + ${row.resourceId}), showDivsEndingWith('hidden_' + ${row.resourceId})">
												<b>hide docs</b>
											</a>
											<br>
											<ul>
												<c:forEach items="${documents}" var="doc">
													<input type="hidden" name="documentSelection${doc.id}" id="documentSelection${doc.id}"
														value="${doc.id}" />
													<li>
														<a href="javascript:validateDocSelection('viewDocument', ${doc.id});">
															${doc.name} </a>
													</li>
												</c:forEach>
											</ul>

										</div>
									</td>
									<td class="column5">
										${row.servideOrdersCompleted}
									</td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>

				<tr>
					<td colspan=2>
						Show 25 ......etc
					</td>
				</tr>
				<tr>
					<td align="center">
						<a href="http://www.google.com"> Remove Selected Members</a>
					</td>
					<td align="center">
						<a href="http://www.google.com"> Approve Selected Members</a>
					</td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
</s:form>
