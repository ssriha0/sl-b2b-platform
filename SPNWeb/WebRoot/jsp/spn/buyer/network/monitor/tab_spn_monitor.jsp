<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="SpnMessage" scope="request" value="<%=session.getAttribute("SpnMessage")%>" />
<html>

<head>
    <meta name="decorator" content="mainspndecorator" />
    <script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.dataTables.js"></script>
	
	<link href="${staticContextPath}/css/font-awesome.min.css"      rel="stylesheet" />
	<link href="${staticContextPath}/css/font-awesome-ie7.min.css"  rel="stylesheet" />
	
	<title>ServiceLive - Select Provider Network - Manage Networks</title>
	<c:import url="/jsp/spn/common_includes.jsp"></c:import>
	<style type="text/css">
		#spnDetailsTabs .left,#spnDetailsTabs .right {width:49%;}
		#spnDetailsTabs dl {width:100%}
		#spnDetailsTabsList {height:32px; border-bottom: 0;}
	</style>
<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	 .ie7 .bannerDiv{margin-left:-1150px;}
	 .ie8 .bannerDiv{margin-left:-1150px;}
	 .ie9 .bannerDiv{margin-left:-200px;}
			</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$('span.open').hide();
			
			$("tr.info").hide();
		$(".nwk-title").click(function(){
			$(this).parents('td').parents('tr').next('tr').toggle();
			$(this).children('span.open').toggle();
			$(this).children('span.closed').toggle();
		});
		
			var queryString;
			
			$('#numNetworkResultsDiv').click(function(){
				var url = '';
				if($('#numNetworkResultsDiv').html() == 'View All')
				{
					$('#numNetworkResultsDiv').html('View 2');
					url = 'spnMonitorNetwork_viewNetworkTableAjax.action';
				}
				else
				{
					$('#numNetworkResultsDiv').html('View All');
					url = 'spnMonitorNetwork_viewNetworkTableAjax.action?limitRows=true';
					$('#showingDiv').show();
				}
				var formValues = $('#spnMonitorNetwork_viewNetworkTableAjax').serializeArray();
				$("#loadDiv").jqm({modal : true,overlay : 0});
				$("#loadDiv").jqmShow();
				$.ajax({
				  	url: url,
				  	type: "POST",
			        data: formValues,
					dataType : "html",
				  	success: function( data ) {
				  		$("#loadDiv").jqmHide();
				  		$('.pickedClick').each(function() {
							presetFilters(this);	
						});
				  		$("#monitorNetworkTable").html(data);
					}
				});
			});

			$('#marketList').change(function() {
				queryString = "spnSummaryFilterVO.marketId=" + $('#marketList').val() + "&spnSummaryFilterVO.stateCd=" + $('#stateList').val();
				if($('#numNetworkResultsDiv').html() == 'View All')
				{
					$('#numNetworkResultsDiv').html('View All');
					$('#monitorNetworkTable').load('spnMonitorNetwork_viewNetworkTableAjax.action?limitRows=true&'+queryString);
				}
				else
				{
					$('#numNetworkResultsDiv').html('View 2');
					$('#monitorNetworkTable').load('spnMonitorNetwork_viewNetworkTableAjax.action?'+queryString);
					$('#showingDiv').show();
				}
			});

			$('#stateList').change(function() {
				queryString = "spnSummaryFilterVO.marketId=" + $('#marketList').val() + "&spnSummaryFilterVO.stateCd=" + $('#stateList').val();
				if($('#numNetworkResultsDiv').html() == 'View All')
				{
					$('#numNetworkResultsDiv').html('View All');
					$('#monitorNetworkTable').load('spnMonitorNetwork_viewNetworkTableAjax.action?limitRows=true&'+queryString);
				}
				else
				{
					$('#numNetworkResultsDiv').html('View 2');
					$('#monitorNetworkTable').load('spnMonitorNetwork_viewNetworkTableAjax.action?'+queryString);
					$('#showingDiv').show();
				}
			});
			<c:if test="${expIncludedInd=='true' && spnId != null}">
			var spnId = '${spnId}';
			var exceptioinInd ='${expIncludedInd}';
			setTimeout(function(){
			$('.nwk-title-'+spnId).trigger("click");
			$('#clickExceptions'+spnId).trigger('click');
			window.location.hash = '#spnExceptions'+spnId;
			 },4000);
			</c:if>
			
			$('body').unbind('click').click(function() {
				$('.select-options').hide();	
			});
			
			$('.select-options').unbind('click').click(function(event){
				event.stopPropagation();
			});
			
			$('.pickedClick').unbind('click').click(function(event) {
				openFilterOptions(this);	
				event.stopPropagation();			
			});
			

			$('[type=checkbox]').click(function() {
				onFilterSelect($(this));
			});  
			
			$('.marketCheck').click(function() {
				var count = $(".marketCheck[type='checkbox']:checked").length;
				if($(this).is(':checked') || count > 0){
					$("#applyFilterInd").val("1");
				}				
				else {					
					var prevVal = $('#marketLabel').val();
					//To apply filter when user un checks all the selections
					if("Select One" !== prevVal){
						$("#applyFilterInd").val("1");
					}
				}
			});
			
			$('.stateCheck').click(function() {
				var count = $(".stateCheck[type='checkbox']:checked").length;
				if($(this).is(':checked') || count > 0){
					$("#applyFilterInd").val("1");
				}				
				else {
					var prevVal = $('#stateLabel').val();
					//To apply filter when user un checks all the selections
					if("Select One" !== prevVal){
						$("#applyFilterInd").val("1");
					}
				}
			});
			
			$('body').unbind('click').click(function() {
				$('.select-options').hide();	
				$('.zselect-Complianceoptions').hide();
				var applyFilter = $("#applyFilterInd").val();	
				var url = '';	
				if(1 == applyFilter){
					$("#applyFilterInd").val(0);
					if($('#numNetworkResultsDiv').html() == 'View All'){
						$('#numNetworkResultsDiv').html('View All');
						url = 'spnMonitorNetwork_viewNetworkTableAjax.action?limitRows=true';
					}
					else{
						$('#numNetworkResultsDiv').html('View 2');
						url = 'spnMonitorNetwork_viewNetworkTableAjax.action';
						$('#showingDiv').show();
					}
					var formValues = $('#spnMonitorNetwork_viewNetworkTableAjax').serializeArray();
					$("#loadDiv").jqm({modal : true,overlay : 0});
					$("#loadDiv").jqmShow();
					$.ajax({
				  		url: url,
				  		type: "POST",
			        	data: formValues,
						dataType : "html",
				  		success: function( data ) {
				  			$("#loadDiv").jqmHide();
				  			$('.pickedClick').each(function() {
								presetFilters(this);	
							});
				  			$("#monitorNetworkTable").html(data);
						}
					});
				}
			});
			
			
		});
		
		function presetFilters(myThis)
		{
			var numSelected = $('#'+myThis.id+'list').children('div').children(':checked').length;
			if (numSelected > 0){
				$('#'+myThis.id).children('label').html(numSelected + ' Selected');			
				$('#'+myThis.id+'Label').val(numSelected + ' Selected');
			}
			else{				
				$('#'+myThis.id).children('label').html('Select One');
				$('#'+myThis.id+'Label').val('Select One');
			}	
		}
		
		function openFilterOptions(myThis)
		{
			var hidden = false;
			if ($('#'+myThis.id+'list:hidden').length > 0){
				hidden = true;
			}			
			$('.select-options').hide();	
			if (hidden == true){
				$('#'+myThis.id+'list').show();
			}
		}
		
		function onFilterSelect(myThis)
		{
			if (myThis.parent('div').parent('div.select-options').length == 1){
				var id = myThis.parent('div').parent('div.select-options').attr('id');
				id = id.substring(0,id.length-4);
				var numSelected = myThis.parent('div').parent('div.select-options').children('div').children(':checked').length;
				if (numSelected > 0){
					$('#'+id).children('label').html(numSelected + ' Selected');
				}
				else{					
					$('#'+id).children('label').html('Select One');
				}	
			}
		}
		
		function clickViewDetails(networkid)
		{
			//jQuery.noConflict();
			$(document).ready(function($) {
					$('#networkDetails-'+networkid).html('<img src="${staticContextPath}/images/loading.gif" width="650px" height="300px">');
					$('#networkDetails-'+ networkid).load('spnCreateNetwork_viewNetworkDetailsAjax.action?spnid=' + networkid+'&date='+ new Date().getTime());
			});
		}
		
		function clickViewRoutingTiers(networkid)
		{
			//jQuery.noConflict();
			$(document).ready(function($)
			{			
					$('#networkRoutingTiers-'+ networkid).load('spnCreateNetwork_viewRoutingTiersAjax.action?spnid=' + networkid+'&date='+ new Date().getTime());
			});
		}

		function clickNetworkHistory(networkid)
		{
			//jQuery.noConflict();
			$(document).ready(function($)
				{
					$('#networkHistory-'+networkid).load('spnMonitorNetwork_viewNetworkHistoryAjax.action?networkid=' + networkid+'&date='+ new Date().getTime());
				});
		}
		//Sl-18018
		//function to load exceptions tab
		function clickViewExceptions(networkid)
		{
			//jQuery.noConflict();
			$(document).ready(function($)
				{
					$('#spnExceptions-'+networkid).html('<img src="${staticContextPath}/images/loading.gif" width="650px" height="300px">');
					$('#spnExceptions-'+networkid).load('spnMonitorNetwork_viewExceptionsAjax.action?networkid=' + networkid+'&date='+ new Date().getTime());
				});
		}
		
		//Sl-18018
		//function to load Complaince tab
		function clickViewCompliance(networkid)
		{
			//jQuery.noConflict();
			$(document).ready(function($)
				{
				
                $('#spnCompliance-'+networkid).html('<img src="${staticContextPath}/images/loading.gif" width="572px" height="620px">');

					$('#spnCompliance-'+networkid).load('spnMonitorNetwork_viewComplianceAjax.action?networkid=' + networkid+'&date='+ new Date().getTime()+'&complianceType=firmCompliance');
				});
		}
		
		
		function goViewExceptions(networkid)
		{
			if($('#tabsSpnExceptions'+networkid).is(':hidden') == true){
				$('#clickExceptions'+networkid).trigger('click');
			}
		}
		
		function clickEditNetwork(networkid)
		{
				$.post('spnCreateNetwork_checkNetworkEditableAjax.action', {
				 'spnHeader.spnId': networkid }, 
				 function(data) {
					if (data.canEditNetwork == 0)
					{
					$('#errorEditNetwork_' + networkid).html('This network is not editable because it has already been edited. You can re-edit this network after the effective date ' + data.effectiveDate + '.');
					$('#errorEditNetwork_' + networkid).show();
					}
					else
					{
					$('#editNetworkForm_' + networkid).submit();
					}
				}, 'json');
				//});
				return false; 
		}
	</script>
</head>
<body id="select-provider-network">
		<div id="wrap" class="container">
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
							Manage Select Provider Networks
						</h3>

						<div id="tabs">
							<c:import url="/jsp/spn/common_tabs.jsp">
								<c:param name="tabName" value="spnMonitorNetwork" />
							</c:import>
							<div id="tab-content" class="clearfix">


								<%-- ss: if you need to remove the filters, comment out the <div> below 
								<div class="filters clearfix fby">
									<strong>Filter by:</strong>
									<strong>Market</strong> <select><option>Select One</option></select>
									<strong>State</strong> <select><option>Select One</option></select>
								</div>
								--%>


								<%--
								<div class="clearfix">
									<div class="left thead">
										<strong>Showing 30 out of 85</strong>
									</div>
								<div class="right thead">
								<a href="#">View All</a>
								</div>
								</div>
								--%>
								<%-- 
								<div class="left" id="showingDiv">
									<c:if test="${networkCountLimit != null}" >
										<strong>Showing ${networkCountLimit} of ${networkCountAll} (${completeSPNCount} complete / ${incompleteSPNCount} incomplete)</strong>
									</c:if>
								</div>
								--%>
				<c:if test="${!empty SpnMessage}">
				<div class="successBox clearfix">
			        ${SpnMessage}
			    </div>
			    </c:if>
<div class="filters clearfix fby">
	<s:form action="spnMonitorNetwork_viewNetworkTableAjax"
			id="spnMonitorNetwork_viewNetworkTableAjax"
			name="spnMonitorNetwork_viewNetworkTableAjax" method="post"
			enctype="multipart/form-data" theme="simple" validate="true">
									<strong>Filter by:</strong>
									
									<strong>Market</strong> 
									<div id="market" class="picked pickedClick" style="width:185px;margin-left:120px">
										<label style="width:175px;">Select One</label>
									</div>	
									<div id="marketlist" class="select-options" style="display:none;margin-left:120px;z-index: 900">
										<c:forEach items="${marketList}" var="market" varStatus="status">
											<c:set var="checked" value="" ></c:set>
											<div style="clear: left;">
												<input class="marketCheck" type="checkbox" name="spnSummaryFilterVO.marketIds[${status.count}]" value="${market.id }" /> 
													${market.description}
											</div>
										</c:forEach>
									</div>					
									
									<strong style="margin-left:320px;margin-top:-30px">State</strong> 
									<div id="state" class="picked pickedClick" style="width:185px;margin-left:370px;margin-top:-33px">
										<label style="width:175px;">Select One</label>
									</div>
									<div id="statelist" class="select-options" style="display:none;margin-left:370px;z-index: 900">
										<c:forEach items="${stateList}" var="state" varStatus="status">
											<c:set var="checked" value="" ></c:set>
											<div style="clear: left;">
												<input class="stateCheck" type="checkbox" name="spnSummaryFilterVO.stateCds[${status.count}]" value="${state.id}" /> 
													${state.description}
											</div>
										</c:forEach>
									</div>
	</s:form>
</div>
								<c:if test="${networkCountAll > fn:length(spnSummary)}">
									<div id="numNetworkResultsDiv" class="right">View All</div>
								</c:if>
								<div id="monitorNetworkTable" class="tableWrap">
									<jsp:include flush="true" page="panel_network_monitor_results.jsp"></jsp:include>
								</div>
								
								<input type="hidden" id="stateLabel" value="Select One"></input>
								<input type="hidden" id="marketLabel" value="Select One"></input>
								<input type="hidden" id="applyFilterInd" value="0"></input>
								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	<div id="loadDiv" style="display: none;" class="jqmWindow">
		<div style="padding: 10px;text-align: center;">
			<span>Gathering search results, please wait...</span>
			<div>
				<img src="${staticContextPath}/images/simple/searchloading.gif" />
			</div>
			<div class="clearfix">
				<a class="cancel jqmClose left" href="#">Cancel</a>
			</div>
		</div>
	</div>
	</body>
</html>
<% session.removeAttribute("SpnMessage"); %>


