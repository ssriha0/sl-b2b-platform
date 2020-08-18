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
		<title>ServiceLive - Select Provider Network - Campaign Monitor</title>
		<tiles:insertDefinition name="blueprint.base.meta"/>
		<link rel="stylesheet" type="text/css" href="${static_context_root}/styles/plugins/select-provider-network.css" media="screen, projection">
		<script type="text/javascript" src="${static_context_root}/scripts/plugins/ui.core.js"></script>
		<script type="text/javascript" src="${static_context_root}/scripts/plugins/ui.tabs.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$('.toggle, .vchecktoggle, .wchecktoggle, .cchecktoggle, dl.collapse dd, span.open, div.filter-option, tr.info, a.dateedit').hide();

				$('.subTabs > ul').tabs();

				$("tr.info").hide();
				$(".nwk-title").click(function(){
					$(this).parents('td').parents('tr').next('tr').toggle();
					$(this).children('span.open').toggle();
					$(this).children('span.closed').toggle();
				});

				$('a.afilter').click(function() {
					$('div.filter-option').hide();
					$('a.afilter').removeClass('active');
					$(this).addClass('active');									
				});
				
				
				$('a.filter-market').click(function() {
					$('div.filter-market').show();
				});

				$('a.filter-date').click(function() {
					$('div.filter-date').show();
				});

				$('a.filter-spn').click(function() {
					$('div.filter-spn').show();
				});

				$('a.filter-status').click(function() {
					$('div.filter-status').show();
				});

				$('a.filter-creator').click(function() {
					$('div.filter-creator').show();
				});


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
						
						<h3 class="">Campaign Monitor</h3>
						
		      	  		<div id="tabs">
		       	     		<ul class="tabsmenu">
		          	      		<li><a href="/MarketFrontend/jsp/public/spn/network/monitor.jsp"><span><abbr title="Select Provider Network">SPN</abbr> Monitor</span></a></li>
		        	       		<li><a href="/MarketFrontend/jsp/public/spn/network/create.jsp"><span>Create Network</span></a></li>
		        	       		<li class="selected"><a href="/MarketFrontend/jsp/public/spn/campaign/monitor.jsp"><span>Campaign Monitor</span></a></li>
		        	       		<li><a href="/MarketFrontend/jsp/public/spn/campaign/create.jsp"><span>Create Campaigns</span></a></li>
		            		</ul>
							<div id="tab-content" class="clearfix">

								<%-- 
								<form>
								<div class="filters clearfix">
									<strong>Filter by:</strong>
									<a href="#" class="filter-market afilter">Market</a>
									<a href="#" class="filter-date afilter">Date</a>
									<a href="#" class="filter-spn afilter">Network</a>
									<a href="#" class="filter-status afilter">Status</a>
									<a href="#" class="filter-creator afilter">Created By</a>
								</div>

								<div class="filter-market filter-option clearfix">
									<label>Select a Market: </label>
										<select id="mkt" name="mkt" title="mkt" >
											<option>Market One</option>
											<option>Market Two</option>
											<option>Market Three</option>
											<option>Market Four</option>
											<option>Market Five</option>
											<option>Market Six</option>
											<option>Market Seven</option>
										</select>
								</div>
								<div class="filter-date filter-option clearfix">
									<label>Start Date: </label>
										<select id="sdate" name="sdate" title="sdate">
											<option>January 2008</option>
											<option>February 2008</option>
											<option>March 2008</option>
											<option>April 2008</option>
											<option>May 2008</option>
											<option>June 2008</option>
											<option>July 2008</option>
											<option>August 2008</option>
											<option>September 2008</option>
											<option>October 2008</option>
											<option>November 2008</option>
											<option>December 2008</option>
										</select>
									<label>End Date: </label>
										<select id="edate" name="edate" title="edate">
											<option>January 2008</option>
											<option>February 2008</option>
											<option>March 2008</option>
											<option>April 2008</option>
											<option>May 2008</option>
											<option>June 2008</option>
											<option>July 2008</option>
											<option>August 2008</option>
											<option>September 2008</option>
											<option>October 2008</option>
											<option>November 2008</option>
											<option>December 2008</option>
										</select>
								</div>
								<div class="filter-spn filter-option clearfix">
									<label>Select Provider Network: </label>
										<select id="spn" name="spn" title="spn">
											<option>General Electrician</option>
											<option>Plumbing &amp; Bathrooms</option>
											<option>Flat Panel Installation</option>
										</select>
								</div>
								<div class="filter-status filter-option clearfix">
									<label>Status: </label>
										<select id="stat" name="stat" title="stat">
											<option>Active</option>
											<option>Expired</option>
											<option>Waiting For Deployment</option>
										</select>
								</div>
								<div class="filter-creator filter-option clearfix">
									<label>Network Creator: </label>
										<select id="nwk" name="nwk" title="nwk">
											<option>Paris Hilton</option>
											<option>Lindsay Lohan</option>
											<option>Aubrey ODay</option>
										</select>
								</div>

								
								<div class="applied-filters clearfix">
									<label>Filters: </label>
									<div class="applied">
									Start Date: October 2008 <a href="#" title="Remove this filter">x</a>
									</div>

									<div class="applied">
									Creator: Paris Hilton <a href="#" title="Remove this filter">x</a>
									</div>

									<div class="applied">
									SPN: General Electrician <a href="#" title="Remove this filter">x</a>
									</div>

									<div class="applied">
									Status: Expired <a href="#" title="Remove this filter">x</a>
									</div>

									<div class="applied">
									Market: 1234567890 <a href="#" title="Remove this filter">x</a>
									</div>

								</div>
								</form>
								--%>
								
								<div class="clearfix">
								<div class="left thead">
								<strong>Showing 30 out of 85</strong>
								<span>(35 Active / 45 Inactive)</span>
								</div>
								<div class="right thead">
									<a href="#">View All</a>
								</div>
								</div>
								<div class="tableWrap">
								<table id="spn-monitor" border="0" cellpadding="0" cellspacing="0">
									<thead>
									<tr>
										<th rowspan="2" class="br textcenter">Status<br />
											<a href="#" title="Sort"><img src="/ServiceLiveWebUtil/images/common/filter-green.png" alt="Sort" /></a><a href="#" title="Sort"><img src="/ServiceLiveWebUtil/images/common/filter-yellow.png" alt="Sort" /></a><a href="#" title="Sort"><img src="/ServiceLiveWebUtil/images/common/filter-red.png" alt="Sort" /></a>
										</th>
										<th rowspan="2" class="br textleft">Campaign</th>
										<th rowspan="2" class="br"><abbr title="Select Provider Network">SPN</abbr></th>
										<th colspan="4" class="br textcenter">Invited Firms / Providers</th>
										<th rowspan="2" class="textcenter admin">Delete</th>
									</tr>
										<th class="highlight">Invited</th>
										<th class="highlight">Applied</th>
										<th class="highlight">Interested</th>
										<th class="br highlight">Not Interested</th>
									<tr>
										
									</tr>
									</thead>
									<tbody>
										<jsp:include page="monitor-table-loop.jsp" />
										<jsp:include page="monitor-table-loop2.jsp" />
										<jsp:include page="monitor-table-loop3.jsp" />
										<jsp:include page="monitor-table-loop2.jsp" />
										<jsp:include page="monitor-table-loop.jsp" />
										<jsp:include page="monitor-table-loop3.jsp" />
										<jsp:include page="monitor-table-loop.jsp" />
										<jsp:include page="monitor-table-loop2.jsp" />
										<jsp:include page="monitor-table-loop.jsp" />
										<jsp:include page="monitor-table-loop3.jsp" />
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
		 <jsp:param name="PageName" value="spn:campaign monitor"/>
	</jsp:include>
	</body>
</html>

