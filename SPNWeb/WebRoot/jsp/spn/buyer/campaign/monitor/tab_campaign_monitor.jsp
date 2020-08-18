<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<html>
	<head>
		<title>ServiceLive - Select Provider Network - Campaign
			Monitor</title>
		
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/visualize.css"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/visualize-dark.css"/>	
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/select-provider-network.css" media="screen, projection">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/chosen.css"/>
	
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/screen.css" media="screen, projection">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/superfish.css" media="screen, projection">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/modals.css" media="screen, projection">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/select-provider-network.css" media="screen, projection" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/jqueryui/jquery-ui-1.7.2.custom.css" />
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.sifr.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.hoverIntent.minified.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.bgiframe.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.multiselect.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/ui.datepicker.js"></script>
	 	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.maxlength-min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery-ajaxfileupload.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.wysiwyg.js"></script> 
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqueryui/jquery-ui-1.10.4.custom.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.maxlength-min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/superfish.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/supersubs.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/select_provider_network.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/visualize.jQuery.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/excanvas.js"></script>	
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.dataTables.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/chosen.jquery.min.js"></script>
        <script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		   <style type="text/css">
	        .ie7 .bannerDiv{margin-left:-1150px;}
	        .ie9 .bannerDiv{margin-left:-200px;}
	 	</style>
		<script type="text/javascript">
			$(document).ready(function() {
				$('.toggle, .vchecktoggle, .wchecktoggle, .cchecktoggle, dl.collapse dd, span.open, div.filter-option, tr.info, a.dateedit').hide();

				$('.subTabs > ul').tabs();

				$("tr.info").hide();
				$(".nwk-title").click(function(){
					$(this).parents('td').parents('tr').next('tr').toggle();
					$(this).children('span.open').toggle();
					$(this).children('span.closed').toggle();
					
					$('.subTabs').tabs( "option", "active", 0 );				
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
				
				$('.jqmWindow').jqm({modal: true, toTop: true});
				
				$('.jqMApprove').click(function() {
					var campaignId = $(this).siblings("#campaignId").val();
					$('#alert-' + campaignId).jqmShow();
				});

				$('#numCampaignResultsDiv').click(function(){
				
					
					if($('#numCampaignResultsDiv').html() == 'View All')
					{
						$('#monitorCampaignTableDiv').load('spnMonitorCampaign_viewCampaignTableAjax.action');
						//$('#numCampaignResultsDiv').html('View 30');
					}
					else
					{
						$('#monitorCampaignTableDiv').load('spnMonitorCampaign_viewCampaignTableAjax.action?limitRows=true');
						//$('#numCampaignResultsDiv').html('View All');
					}
				});


			});

		</script>
	</head>
		
	<body id="select-provider-network">
		<div id="wrap" class="container">
			<jsp:include page="/jsp/spn/common/defaultheadernew.jsp" />
			<div id="content" class="span-24 clearfix">
				<div id="primary" class="span-24 first last">
					<div class="content">
						<h2 id="page-role">
							Administrator Office
						</h2>
						<h2 id="page-title">
							Select Provider Network (SPN)
						</h2>

						<h3 class="">
							Campaign Monitor
						</h3>

						<div id="tabs">
							<c:import url="/jsp/spn/common_tabs.jsp">
								<c:param name="tabName" value="spnMonitorCampaign" />
							</c:import>
						</div>	
							<div id="tab-content" class="clearfix">

								<div id="monitorCampaignTableDiv">
									<jsp:include flush="true" page="panel_campaign_monitor_results.jsp"></jsp:include>
								</div>
							</div>
						
					</div>
				</div>
			</div>
			<jsp:include page="/jsp/spn/common/defaultfootertmp.jsp" />
		</div>
	</body>
</html>

