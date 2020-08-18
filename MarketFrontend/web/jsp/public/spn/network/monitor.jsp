<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<title>ServiceLive - Select Provider Network - Manage Networks</title>
	<tiles:insertDefinition name="blueprint.base.meta"/>
	<link rel="stylesheet" type="text/css" href="${static_context_root}/styles/plugins/select-provider-network.css" media="screen, projection">
	<script type="text/javascript" src="${static_context_root}/scripts/plugins/ui.core.js"></script>
	<script type="text/javascript" src="${static_context_root}/scripts/plugins/ui.tabs.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$('span.open').hide();

			$("tr.info").hide();
			$(".nwk-title").click(function(){
				$(this).parents('td').parents('tr').next('tr').toggle();
				$(this).children('span.open').toggle();
				$(this).children('span.closed').toggle();
			});
			$('.subTabs > ul').tabs();
			
		});
	</script>
</head>
<body id="select-provider-network">
	<div id="wrap" class="container">

		<tiles:insertDefinition name="blueprint.base.header"/>
		<tiles:insertDefinition name="blueprint.base.navigation"/>

		<div id="content" class="span-24 clearfix">

			<div id="primary" class="span-24 first last">
				<div class="content">
					<h2 id="page-role">Administrator</h2>
					<h2 id="page-title">Select Provider Network (SPN)</h2>
					<h3 class="">Manage Select Provider Networks</h3>

					<div id="tabs">
						<ul class="tabsmenu">
							<li class="selected"><a href="/MarketFrontend/jsp/public/spn/network/monitor.jsp"><span><abbr title="Select Provider Network">SPN</abbr> Monitor</span></a></li>
							<li><a href="/MarketFrontend/jsp/public/spn/network/create.jsp"><span>Create Network</span></a></li>
							<li><a href="/MarketFrontend/jsp/public/spn/campaign/monitor.jsp"><span>Campaign Monitor</span></a></li>
							<li><a href="/MarketFrontend/jsp/public/spn/campaign/create.jsp"><span>Create Campaigns</span></a></li>
						</ul>
						<div id="tab-content" class="clearfix">
							
							<%--

							<div class="filters clearfix fby">
								<strong>Filter by:</strong>
								<strong>Market:</strong> <select><option>Select One</option></select>
								<strong>State:</strong> <select><option>Select One</option></select>
							</div>
							
							--%>
							

							<div class="clearfix">
							<div class="left thead">
							<strong>Showing 30 out of 85</strong>
							</div>
							<div class="right thead">
								<a href="#">View All</a>
							</div>
							</div>
							<div class="tableWrap">
							<table id="spn-monitor" border="0" cellpadding="0" cellspacing="0">
								<thead>
									<tr>
										<th class="textleft" rowspan="2"><abbr title="Select Provider Network">SPN</abbr></th>
										<th class="blr" rowspan="2">Total Active<br />Campaigns</th>
										<th class="br" colspan="4">Invited Firms/Providers</th>
										<th class="br" colspan="2">Member Firms/Providers</th>
										<!-- if a user has rights to be an admin -->
										<th class="admin" rowspan="2">Delete</th>
										<!-- endif -->
									</tr>						
									<tr>
										<th class="highlight">Invited</th>
										<th class="highlight">Applied</th>
										<th class="highlight">Interested</th>
										<th class="br highlight">Not<br />Interested</th>
										<th><abbr title="Out Of Compliance">OOC</abbr></th>
										<th class="br">Active</th>
									</tr>									
								</thead>
								<tbody>
									<jsp:include page="monitor-table-loop.jsp" />
									<jsp:include page="monitor-table-loop2.jsp" />
									<jsp:include page="monitor-table-loop3.jsp" />
									<jsp:include page="monitor-table-loop2.jsp" />
									<jsp:include page="monitor-table-loop.jsp" />
									<jsp:include page="monitor-table-loop.jsp" />
									<jsp:include page="monitor-table-loop3.jsp" />
									<jsp:include page="monitor-table-loop.jsp" />
									<jsp:include page="monitor-table-loop2.jsp" />	
									<jsp:include page="monitor-table-loop3.jsp" />
									<jsp:include page="monitor-table-loop2.jsp" />
									<jsp:include page="monitor-table-loop2.jsp" />																	
								</tbody>
							</table>
							</div>
														
						</div>
					</div>
				</div>
			</div>
		</div>

		<tiles:insertDefinition name="blueprint.base.footer"/>

	</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
	<jsp:param name="PageName" value="spn:network monitor"/>
</jsp:include>
</body>
</html>


