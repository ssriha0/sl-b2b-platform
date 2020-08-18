<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="viewOrderPricing" scope="session" value="<%=session.getAttribute("viewOrderPricing")%>"></c:set>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
	 <jsp:param name="PageName" value="Dashboard"/>
</jsp:include>	

<%--
commented this out because the following html is already contained in a <body> tag
<body>--%>
		
			<div id="page_margins">
		
				<%-- Dynamic List of Modules --%>
				<div class="dashboardLeftColumn">
					<c:forEach var="module" items="${moduleList}">
						<jsp:include page="html_sections/modules/${module}" />
					</c:forEach>
				</div>
				
				
				
				<div class="dashboardRightColumn">
					<jsp:include page="html_sections/center/vital_stats.jsp" />
<%-- Commented out for 12/15/07 release
COMMENT START -					
					<div class="dashboardCommMonitorHeader">
						<a href=""><img align="right"
								src="${staticContextPath}/images/dashboard/more.gif" /> </a>
					</div>
					<div class="dashboardCommMonitorBody"
						style="height: 352px; _height: 345px;">
						<table cellpadding="0px" cellspacing="0px" class="SCROLLER_HOLDER">
							<thead>
								<tr class="dashboardGridHdrs">
									<td class="dashboardGridFrom">
										&nbsp;&nbsp;
										<a href="" class="sortGridColumnUp"
											onclick="alert('Sort By From')">From</a>
									</td>
									<td class="dashboardGridSubject">
										<a href="" class="sortGridColumnUp"
											onclick="alert('Sort By Subject')">Subject</a>
									</td>
									<td class="dashboardGridDate">
										<a href="" class="sortGridColumnUp"
											onclick="alert('Sort By Date')">Date / Time</a>
									</td>
									<td width="17">
										<br>
										<br>
									</td>
								</tr>
							</thead>
						</table>
						 
						<iframe src="${contextPath}/jsp/dashboard/body/DBCOMM_table.jsp" width="695px" height="290px" scrolling="auto" frameborder="0">
						</iframe>
						
						
						<div class="dashboardPaganationCtrl">
							<div class="floatLeft">
								&nbsp;Show
								<b>25</b>
								<span class="pipes">|</span>
								<a class="lightBlue">50</a>
								<span class="pipes">|</span>
								<a class="lightBlue">100</a> Results per Page
							</div>
							<div class="floatRight">
								<a class="lightBlue">&lt; Previous</a>
								<b>11</b>
								<span class="pipes">|</span>
								<a class="lightBlue">12</a>
								<span class="PIPES">|</span>
								<a class="lightBlue">13</a>
								<span class="pipes">|</span>
								<a class="lightBlue">14</a>
								<span class="pipes">|</span>
								<a class="lightBlue">15</a> of 25
								<a class="lightBlue">Next &gt;</a> &nbsp;
							</div>
							<div class="BREAK"></div>
						</div>
					</div>
COMMENT END -					
--%>					



				</div>
				<div style="clear: both;">
				</div>
			
			</div>
			
<%--	</body>--%>
