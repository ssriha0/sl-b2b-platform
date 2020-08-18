<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<style type="text/css">
.filterTable select {
	height: 21px;
	border: 1px solid #BBBBBB;
	vertical-align: middle;
	background: white url(../images/common/multiselect.gif) no-repeat scroll;
	background-position: right;
}

.filterTable td {
	vertical-align: bottom;
}
.unassignedProv_subdiv{
	border-top: 1px solid #BBBBBB;
    clear: left;
    display: none;
    margin-top: 20px;
    padding-top: 5px;
}
</style>

<script type="text/javascript">
	var unassignedInitVal = '${omModel.includeUnassigned}';
	$(document).ready(function() {
		//to get the total count for active tab
		var activeTab = getActiveTab();
		var totalActiveTabCount = getTotalTabCount(activeTab);
		if(totalActiveTabCount <= 0){
			$('#selectApptDateOptions').html('');
		}
		
		/*To show the select/derop down on click*/
		$('#selectMarkets').click(function() {
			hideAllFilter();
			if($("#selectMarketsOptions").children().length>0){
				$("#selectMarketsOptions").show();
			}
		});
		
		$('#selectJobDoneSubStatus').click(function() {
			hideAllFilter();
				if($("#selectJobDoneSubStatusOptions").children().length>0){
				$("#selectJobDoneSubStatusOptions").show();
			}
		});
		
		$('#selectCurrentOrdersSubStatus').click(function() {
			hideAllFilter();
				if($("#selectCurrentOrdersSubStatusOptions").children().length>0){
				$("#selectCurrentOrdersSubStatusOptions").show();
			}
		});
		
		//R12.0 Sprint3 cancellation substatus filter
		
		$('#selectCancellationsSubStatus').click(function() {
			hideAllFilter();
				if($("#selectCancellationsSubStatusOptions").children().length>0){
				$("#selectCancellationsSubStatusOptions").show();
			}
		});
		
	//R12.0 Sprint4 revisit substatus filter
		
		$('#selectRevisitSubStatus').click(function() {
			hideAllFilter();
				if($("#selectRevisitSubStatusOptions").children().length>0){
				$("#selectRevisitSubStatusOptions").show();
			}
		});


		$('#selectProviders').click(function() {
			hideAllFilter();
			if($("#selectProvidersOptions").children().length>0){
				if($.trim($("#defaultValProvider").html()) !== '-Select-'){
					$("#unassignedProv_subdiv").show();
				}
				$("#selectProvidersOptions").show();
			}
		});
		
		$('#selectRoutedProviders').click(function() {
			hideAllFilter();
			if($("#selectRoutedProvidersOptions").children().length>0){
				$("#selectRoutedProvidersOptions").show();
			}
		});
		
		$('#selectStatus').click(function() {
			hideAllFilter();
			var type='status';
			var a = $("#selectStatusOptions ul").html();
			if(null != a){
				if($('#defaultValStatus').html()!="-Select-" && a.indexOf("-Select-") < 0){
					$("#selectStatusOptions ul").prepend("<li onclick='selectStatus(this, type);' id='status_li_${countStat.count}' val='-Select-' value='-Select-'>-Select-</li>");
				}else if($('#defaultValStatus').html()=="-Select-" && a.indexOf("-Select-") > 0){
					$("#selectStatusOptions ul li:first").remove();
				}
			}	
			if($("#selectStatusOptions").children().length>0){
				$("#selectStatusOptions").show();
			}
		});
		
		$('#providerSelect').click(function() {
			hideAllFilter();
			var a = $("#selectProviderOptions ul").html();
			if($('#defaultValProvider').html()!="-Select-" && a.indexOf("-Select-") < 0){
				$("#selectProviderOptions ul").prepend("<li onclick='selectProvider(this);' id='provider_li_${countStat.count}' val='-Select-' value='${providerLists.id}' class='provListClass'>-Select-</li>");
			}else if($('#defaultValProvider').html()=="-Select-" && a.indexOf("-Select-") > 0){
				$("#selectProviderOptions ul li:first").remove();
			}
			if($("#selectProviderOptions").children().length>0){
				$("#selectProviderOptions").show();
			}
		});
		$('#selectScheduleStatus').click(function() {
			hideAllFilter();
			var type='Schedule';
			var a = $("#selectScheduleStatusOptions ul").html();
			if(null != a){
				if($('#defaultScheduleValStatus').html()!="-Select-" && a.indexOf("-Select-") < 0){
					$("#selectScheduleStatusOptions ul").prepend("<li onclick='selectStatus(this, \"Schedule\");' id='status_li_${countStat.count}' val='-Select-' value='-Select-'>-Select-</li>");
				}else if($('#defaultScheduleValStatus').html()=="-Select-" && a.indexOf("-Select-") > 0){
					$("#selectScheduleStatusOptions ul li:first").remove();
				}
			}
			if($("#selectScheduleStatusOptions").children().length>0){
				$("#selectScheduleStatusOptions").show();
			}
		});
		$('#selectSubStatus').click(function() {
			hideAllFilter();
			var type='Sub';
			var a = $("#selectSubStatusOptions ul").html();
			if(null != a){
				if($('#defaultSubValStatus').html()!="-Select-" && a.indexOf("-Select-") < 0){
					$("#selectSubStatusOptions ul").prepend("<li onclick='selectSubStatus(this,\"Sub\");' id='sub_status_li_${countStat.count}' val='-Select-' value='-Select-'>-Select-</li>");
				}else if($('#defaultSubValStatus').html()=="-Select-" && a.indexOf("-Select-") > 0){
					$("#selectSubStatusOptions ul li:first").remove();
				}
			}	
			if($("#selectSubStatusOptions").children().length>0){
				$("#selectSubStatusOptions").show();
			}
		});

		$('#selectApptDate').click(function() {
			hideAllFilter();
			var a = $("#selectApptDateOptions ul").html();
			if(null != a){
				if($('#defaultApptDate').html()!="-Select-" && a.indexOf("-Select-") < 0){
					$("#selectApptDateOptions ul").prepend("<li onclick='selectApptDate(this);' id='app_dt_0' val='-Select-' value='-Select-'>-Select-</li>");
				}else if($('#defaultApptDate').html()=="-Select-" && a.indexOf("-Select-") > 0){
					$("#selectApptDateOptions ul li:first").remove();
				}
			}	
			if($("#selectApptDateOptions").children().length>0){
				$("#selectApptDateOptions").show();
			}
		});
		$(document).on('click', ".marketCheckbox", function(){
			var count = $(".marketCheckbox[type='checkbox']:checked").length;
			if($(this).is(':checked') || count > 0){
				$("#applyFilterInd").val("1");
			}
			
			if (count > 0) {
				$('#defaultValMarket').html(count + " selected");
				$("#applyFilterInd").val("1");
				$("#marketResetInd").val("0");
			} else {
				var prevVal = $('#defaultValMarket').html();
				//To apply filter when user un checks all the selections
				if("-Select-" !== prevVal){
					$("#applyFilterInd").val("1");
					$("#marketResetInd").val("1");
				}
				$('#defaultValMarket').html("-Select-");
			}
		});
		
		$(document).on('click', ".jobDoneCheckbox", function(){
			var count = $(".jobDoneCheckbox[type='checkbox']:checked").length;
			if($(this).is(':checked') || count > 0){
				$("#applyFilterInd").val("1");
			}
			
			if (count > 0) {
				$('#defaultJobDoneSubStatus').html(count + " selected");
				$("#applyFilterInd").val("1");
				$("#jobDoneSubStatusResetInd").val("0");
			} else {
				var prevVal = $('#defaultJobDoneSubStatus').html();
				//To apply filter when user un checks all the selections
				if("-Select-" !== prevVal){
					$("#applyFilterInd").val("1");
					$("#jobDoneSubStatusResetInd").val("1");
				}
				$('#defaultJobDoneSubStatus').html("-Select-");
			}
		});
		
		$(document).on('click', ".currentOrdersCheckbox", function(){
			var count = $(".currentOrdersCheckbox[type='checkbox']:checked").length;
			if($(this).is(':checked') || count > 0){
				$("#applyFilterInd").val("1");
			}
			
			if (count > 0) {
				$('#defaultCurrentOrdersSubStatus').html(count + " selected");
				$("#applyFilterInd").val("1");
				$("#currentOrdersSubStatusResetInd").val("0");
			} else {
				var prevVal = $('#defaultCurrentOrdersSubStatus').html();
				//To apply filter when user un checks all the selections
				if("-Select-" !== prevVal){
					$("#applyFilterInd").val("1");
					$("#currentOrdersSubStatusResetInd").val("1");
				}
				$('#defaultCurrentOrdersSubStatus').html("-Select-");
			}
		});
		
		//R12.0 Sprint3 Cancellations substatus filter
		
		$(document).on('click', ".cancellationsCheckbox", function(){
			var count = $(".cancellationsCheckbox[type='checkbox']:checked").length;
			if($(this).is(':checked') || count > 0){
				$("#applyFilterInd").val("1");
			}
			
			if (count > 0) {
				$('#defaultCancellationsSubStatus').html(count + " selected");
				$("#applyFilterInd").val("1");
				$("#cancellationsSubStatusResetInd").val("0");
			} else {
				var prevVal = $('#defaultCancellationsSubStatus').html();
				//To apply filter when user un checks all the selections
				if("-Select-" !== prevVal){
					$("#applyFilterInd").val("1");
					$("#cancellationsSubStatusResetInd").val("1");
				}
				$('#defaultCancellationsSubStatus').html("-Select-");
			}
		});
		
   //R12.0 Sprint4 Revisit substatus filter
		
   		$(document).on('click', ".revisitCheckbox", function(){
			var count = $(".revisitCheckbox[type='checkbox']:checked").length;
			if($(this).is(':checked') || count > 0){
				$("#applyFilterInd").val("1");
			}
			
			if (count > 0) {
				$('#defaultRevisitSubStatus').html(count + " selected");
				$("#applyFilterInd").val("1");
				$("#revisitSubStatusResetInd").val("0");
			} else {
				var prevVal = $('#defaultRevisitSubStatus').html();
				//To apply filter when user un checks all the selections
				if("-Select-" !== prevVal){
					$("#applyFilterInd").val("1");
					$("#revisitSubStatusResetInd").val("1");
				}
				$('#defaultRevisitSubStatus').html("-Select-");
			}
		});
		
		
		$("#includeUnassigned").click(function() {
			if($(this).is(':checked')){
				$(this).val(true);
			}else{
				$(this).val(false);
			}
			if((unassignedInitVal !== $("#includeUnassigned").val())){
				$("#applyFilterInd").val("1");
			}
		});
		
		$(document).on('click', ".providerCheckbox", function(){
			var count = $(".providerCheckbox[type='checkbox']:checked").length;
			if($(this).is(':checked') || count > 0){
				$("#applyFilterInd").val("1");
				if($("#includeUnassigned").is(':checked')){
					$("#includeUnassigned").val(true);
				}else{
					$("#includeUnassigned").val(false);
				}
				$("#unassignedProv_subdiv").show();
			}else{
				$("#unassignedProv_subdiv").hide();
				$("#includeUnassigned").removeAttr("checked");
				$("#includeUnassigned").val(false);
			}
			
			if (count > 0) {
				$('#defaultValProvider').html(count + " selected");
				$("#applyFilterInd").val("1");
				$("#providerResetInd").val("0");
			} else{
				var prevVal = $('#defaultValProvider').html();
				if("-Select-" !== prevVal){
					//To apply filter when user un checks all the selections
					$("#applyFilterInd").val("1");
					$("#providerResetInd").val("1");
				}
				$('#defaultValProvider').html("-Select-");
			}
		});
		
		$(document).on('click', ".routedCheckbox", function(){
			var count = $(".routedCheckbox[type='checkbox']:checked").length;
			if($(this).is(':checked') || count > 0){
				$("#applyFilterInd").val("1");
			}
			
			if (count > 0) {
				$('#defaultValRoutedProvider').html(count + " selected");
				$("#applyFilterInd").val("1");
				$("#routedProviderResetInd").val("0");
			} else{
				var prevVal = $('#defaultValRoutedProvider').html();
				if("-Select-" !== prevVal){
					//To apply filter when user un checks all the selections
					$("#applyFilterInd").val("1");
					$("#routedProviderResetInd").val("1");
				}
				$('#defaultValRoutedProvider').html("-Select-");
			}
		});
		
		
		//display of Provider filter availability info div 
		$('.providerFilterInfoIcon').mouseover(function(){
			$('#providerFilterInfo').show();
		});
		$('.providerFilterInfoIcon').mouseout(function(){
			$('#providerFilterInfo').hide();
		});
		
		//display of Routed To filter availability info div 
		$('.routedToFilterInfoIcon').mouseover(function(){
			$('#routedToFilterInfo').show();
		});
		$('.routedToFilterInfoIcon').mouseout(function(){
			$('#routedToFilterInfo').hide();
		});
		
   		 $('#omErrorDiv').hide();

		<c:if test="${omModel.appointmentType == 'range'}">
			$('#tdApptDate').width("300px");
			$('#selectApptDate').hide();
			$('#apptDateRangeDiv').show();
		</c:if>
		<c:if test="${omModel.includeUnassigned == true}">
		 $("#includeUnassigned").prop("checked", "checked");
		 $("#unassignedProv_subdiv").show();
		</c:if>

	});

	//to display disabled checkbox on provider selection in manage route tab
	function selectProvider(obj){
		var id = $(obj).prop("id");
		var value = $("#"+id).prop("value");
		var val = $("#"+id).prop("val");
		$('.selectedProCheckbox').hide();
		$('#defaultValProvider').html(val);
		var selectedPro = value;
		var checkboxClass = "so" + selectedPro;
		$('.' + checkboxClass).show();
		hideAllFilter();
	}
	
	/*On click method for Status filter and Appointment Date filter*/
	function selectStatus(obj,type){
		//id or val can be used as the selected value.
		if(type == 'status'){
			type = '';
		}
		var id = $(obj).attr("id");
		var val = $("#"+id).attr("val");
		if(type=='Schedule'){
			val = val.substring(0,15);
			if(val == '-Select-'){
				$("#scheduleResetInd").val("1");
			}
			else{
				$("#scheduleResetInd").val("0");
			}
		}
		if(type==''){
			if(val == '-Select-'){
				$("#statusResetInd").val("1");
			}
			else{
				$("#statusResetInd").val("0");
			}
		}
		
		$("#default"+type+"ValStatus").html(val);
		var value = $("#"+id).prop("value");
		$("#selected"+type+"Status").val(value);
		$("#selectedFilter"+type+"Status").val(val);
		if(val == '-Select-'){
			$("#selectInd").val('true');
		}
		else{
			$("#selectInd").val('');	
		}
		$("#applyFilterInd").val("1");
		hideAllFilter();
		loadAfterAssignProvider();
	}
	
	function selectSubStatus(obj,type){
		//id or val can be used as the selected value.
		var id = $(obj).prop("id");
		var val = $("#"+id).prop("val");
		if(type=='Sub'){
			if(val == '-Select-'){
				$("#subStatusResetInd").val("1");
			}
			else{
				$("#subStatusResetInd").val("0");
			}
		}
		$("#default"+type+"ValStatus").html(val);
		$("#selected"+type+"Status").val(val);
		$("#selectedFilter"+type+"Status").val(val);
		if(val == '-Select-'){
			$("#selectInd").val('true');
		}
		else{
			$("#selectInd").val('');	
		}
		$("#applyFilterInd").val("1");
		hideAllFilter();
		loadAfterAssignProvider();
	}
	
	function selectApptDate(obj) {
		var val = $(obj).attr("val");
		var value = $(obj).html();
		$("#defaultApptDate").html(value);
		$("#selectedApptDate").val(val);
		$('#fromDate').val("");
		$('#toDate').val("");
		if(val == '-Select-'){
			$("#selectInd").val('true');
		}
		else{
			$("#selectInd").val('');	
		}
		hideAllFilter();
		$("#applyFilterInd").val("1");
		if(val != 'range'){
			loadAfterAssignProvider();
		}
	}

	/*Hides all the filter drop downs..*/
	function hideAllFilter() {
		$("#selectMarketsOptions").hide();
		$("#selectProvidersOptions").hide();
		$("#selectRoutedProvidersOptions").hide();
		$("#selectStatusOptions").hide();
		$("#selectScheduleStatusOptions").hide();
		$("#selectProviderOptions").hide();
		$("#selectSubStatusOptions").hide();
		$("#selectApptDateOptions").hide();
		$("#selectJobDoneSubStatusOptions").hide();
		$("#selectCurrentOrdersSubStatusOptions").hide();
		//R12.0 Sprint3 cancellation substatus
		$("#selectCancellationsSubStatusOptions").hide();
		//R12.0 Sprint4 revisit substatus
		$("#selectRevisitSubStatusOptions").hide();
	}
	
	function checkIfChecked(obj){
		/* var count = $("."+obj+"Checkbox[type='checkbox']:checked").length;
		if(count>0){
			loadAfterAssignProvider();
		} */
	}
</script>
</head>

<body class="tundra acquity">
	<div class="errorBox clearfix" id="selectApptDateOptionsErrorMessage"
		style="width: 250px; display: none; z-index: 3000; position: relative;">
	</div>
	<div style="">
	<form id="filterForm" action="orderManagementControllerAction">
	<input type="hidden" id="applyFilterInd" name="applyFilterInd" value="0" />
	<input type="hidden" id="marketResetInd" name="marketResetInd" value="${omTabDTO.marketResetInd}"/>
	<input type="hidden" id="providerResetInd" name="providerResetInd" value="${omTabDTO.providerResetInd}" />
	<input type="hidden" id="scheduleResetInd" name="scheduleResetInd" value="${omTabDTO.scheduleResetInd}" />
	<input type="hidden" id="statusResetInd" name="statusResetInd" value="${omTabDTO.statusResetInd}" />
	<input type="hidden" id="subStatusResetInd" name="subStatusResetInd" value="${omTabDTO.subStatusResetInd}" />
	<input type="hidden" id="routedProviderResetInd" name="routedProviderResetInd" value="${omTabDTO.routedProviderResetInd}" />
	<input type="hidden" id="displayTabFrm" name="displayTab" value="${omTabDTO.displayTab}" /> 
	<input type="hidden" id="criteriaFrm" name="criteria" value="${omTabDTO.criteria }" /> 
	<input type="hidden" id="sortOrderFrm" name="sortOrder" value="${omTabDTO.sortOrder}" /> 
	<input type="hidden" id="currentOrderCount" name="currentOrderCount" value="${currentSOCount}" /> 
	<input type="hidden" id="resetFilterInd" name="resetInd" value="${omTabDTO.resetInd}" />
	<input type="hidden" id="jobDoneSubStatusResetInd" name="jobDoneSubStatusResetInd" value="${omTabDTO.jobDoneSubStatusResetInd}" />
	<input type="hidden" id="currentOrdersSubStatusResetInd" name="currentOrdersSubStatusResetInd" value="${omTabDTO.currentOrdersSubStatusResetInd}" />
	<input type="hidden" id="cancellationsSubStatusResetInd" name="cancellationsSubStatusResetInd" value="${omTabDTO.cancellationsSubStatusResetInd}" />
	<input type="hidden" id="revisitSubStatusResetInd" name="revisitSubStatusResetInd" value="${omTabDTO.revisitSubStatusResetInd}" />
	
	<table width="100%" border="0" class="filterTable">
		<tr>
			<td>
				<table class="filterTable" width="100%;">
					<tr>
						<c:forEach var="filter" items="${filterNames}">
							<c:if test="${fn:contains(filter, 'Market(s)')}">
								<td width="190px;" class="marketCheckBox">
									<div style="float: left;padding-top: 2px;padding-left:0px;">
										<label><b>Market(s):</b></label>
									</div>
									<div id="selectMarkets" class="picked pickedClick"
										style="width: 100px; float: left;margin-left: 2px;">
										<c:choose>
										<c:when test="${fn:length(omModel.selectedMarkets) > 0}">
											<label id="defaultValMarket">${fn:length(omModel.selectedMarkets)} selected</label>
										</c:when>
										<c:otherwise><label id="defaultValMarket">-Select-</label></c:otherwise>
										</c:choose>
									</div>
									<div class="select-options" id="selectMarketsOptions"
										style="display: none;" onmouseout="">
										<c:if test="${null != marketList}">
										<c:forEach var="marketlists" items="${marketList}" varStatus="i">
											<c:set var="val1" value="0"></c:set>
											<c:forEach var="markets" items="${omModel.selectedMarkets}">
												<c:if test="${marketlists.id == markets}">
													<div style="clear:left;padding-top:5px;">
														<div style="float: left;"> <input type="checkbox" checked="checked"
														name="omTabDTO.selectedMarkets[${i.count}]" value="${marketlists.id}" id="${i.count}"
														class="marketCheckbox" /> </div>
														<div style="padding-left:16px; word-wrap: break-word;">${marketlists.descr}
														</div>
													</div>
													<c:set var="val1" value="1"></c:set>
												</c:if>
											</c:forEach>
											<c:if test="${val1 == 0}">
												<div style="clear:left;padding-top:5px;">
													<div style="float: left;">  
														<input type="checkbox"
														name="omTabDTO.selectedMarkets[${i.count}]" value="${marketlists.id}" id="${i.count}"
														class="marketCheckbox" />
													</div>
													<div style="padding-left:16px; word-wrap: break-word;">
													 ${marketlists.descr}
													 </div>
												</div>
											</c:if>
										</c:forEach>
										</c:if>
									</div>
								</td>
							</c:if>
							
							
							
							<c:if test="${fn:contains(filter, 'Provider(s)')}">
								<td width="200px" class="proCheckBox">
									<div style="float: left;padding-top: 2px;padding-left: 3px;">
										<span class="providerFilterInfoIcon" style="border-bottom: 1px dotted;"><b>Provider(s)</b></span><b>:</b>
									</div>
									<div class="productAvailabilityInfo" id="providerFilterInfo" style="display: none;margin-left: 35px;margin-top: 30px;">
										<p style="padding-left: 5px;">
											The listed providers have one or more service orders routed to them.
										</p>
									</div>
									<div id="selectProviders" class="picked pickedClick"
										style="width: 100px; float: left;margin-left: 2px;">
										<c:choose><c:when test="${fn:length(omModel.selectedProviders) > 0}">
											<label id="defaultValProvider">${fn:length(omModel.selectedProviders)} selected</label>
										</c:when>
										<c:otherwise><label id="defaultValProvider">-Select-</label></c:otherwise>
										</c:choose>
									</div>
									<div class="select-options" id="selectProvidersOptions"
										style="display: none; margin-left: 80px; width: 180px;" onmouseout="">
										<c:if test="${null != providerList}">
										<c:forEach var="providerLists" items="${providerList}" varStatus="i">
											<c:set var="val2" value="0"></c:set>
											<c:forEach var="providers" items="${omModel.selectedProviders}">
												<c:if test="${providerLists.id == providers}">
												<div style="clear:left;padding-top:5px;"> 
													<div style="float: left;"> 
													<input type="checkbox" checked="checked"
													name="omTabDTO.selectedProviders[${i.count}]" value="${providerLists.id}" id="${i.count}"
													class="providerCheckbox" />
													</div>
													<div style="padding-left:16px; word-wrap: break-word;">
														${providerLists.descr}
													 </div>
												</div >
													<c:set var="val2" value="1"></c:set>
												</c:if>
											</c:forEach>
											<c:if test="${val2 == 0}">
											<div style="clear:left;padding-top:5px;">
												<div style="float: left;">
												<input type="checkbox"
													name="omTabDTO.selectedProviders[${i.count}]" value="${providerLists.id}"
													class="providerCheckbox" />
												</div>
													<div style="padding-left:16px; word-wrap: break-word;">${providerLists.descr}<br />
												 </div>
												</div>
											</c:if>
										</c:forEach>
										</c:if>
										<div id="unassignedProv_subdiv" class="unassignedProv_subdiv" style="border-top: 1px solid #BBBBBB; clear: left;display: none;margin-top: 20px;padding-top: 5px;">
											<div style="float: left;">
											<input type="checkbox" name="omTabDTO.includeUnassigned" value="${omModel.includeUnassigned}" id="includeUnassigned"
													class="unassignedCheckbox"/>
											</div>
											<div style="float: left;padding-left:6px;max-width: 130px;">
												Include unassigned orders routed to selected providers(s)
											</div>
										</div>
										
									</div>
								</td>
							</c:if>
							<c:if test="${fn:contains(filter, 'Provider:')}">
								<td width="220px" class="proCheckBox">
									<div style="float: left;padding-top: 2px;">
										<span><b>Provider: </b></span>
									 </div>
									 <div>
									 <div id="providerSelect" class="picked pickedClick"
											style="width: 130px; float: left;margin-left: 2px;" onclick="hideOtherFilters();">
											<c:choose>
											<c:when test="${null != omModel.filterStatus && '' != omModel.filterStatus}">
												<label id="defaultValProvider"> ${omModel.filterStatus} </label>
											</c:when>
											<c:otherwise>
												<label id="defaultValProvider">-Select-</label>
											</c:otherwise>
											</c:choose>
										</div>
										<div class="select-list select-options" id="selectProviderOptions"
											style="display: none; margin-left: 60px;width:135px;">
												<ul>
												<c:if test="${null != providerList}">
												<c:forEach var="providerLists" items="${providerList}" varStatus="countStat">
										 			<li onclick="selectProvider(this);" id="provider_li_${countStat.count}" val="${providerLists.descr}" value="${providerLists.id}" class="selectProClass">${providerLists.descr}</li>
												</c:forEach>
												</c:if>
												</ul>
										</div>
									</div>
								</td>
							</c:if>
							
							<c:if test="${fn:contains(filter, 'Routed To')}">
								<td width="200px" class="proCheckBox">
									<div style="float: left;padding-top: 2px;padding-left: 3px;">
										<span class="routedToFilterInfoIcon" style="border-bottom: 1px dotted;"><b>Routed To</b></span><b>:</b>
									</div>
									<div class="productAvailabilityInfo" id="routedToFilterInfo" style="display: none; margin-left: 50px;margin-top: 35px;">
										<p style="padding-left: 5px;">
											The listed providers have one or more service orders routed to them.
										</p>
									</div>
									<div id="selectRoutedProviders" class="picked pickedClick"
										style="width: 100px; float: left;margin-left: 2px;">
										<c:choose>
										<c:when test="${fn:length(omModel.selectedRoutedProviders) > 0}">
											<label id="defaultValProvider">${fn:length(omModel.selectedRoutedProviders)} selected</label>
										</c:when>
										<c:otherwise><label id="defaultValRoutedProvider">-Select-</label></c:otherwise>
										</c:choose>
									</div>
									<div class="select-options" id="selectRoutedProvidersOptions"
										style="display: none; margin-left: 80px;" onmouseout="">
										<c:if test="${null != routedProviderList}">
										<c:forEach var="routedProviderLists" items="${routedProviderList}" varStatus="i">
											<c:set var="val2" value="0"></c:set>
											<c:forEach var="providers" items="${omModel.selectedRoutedProviders}">
												<c:if test="${routedProviderLists.id == providers}">
												<div style="clear:left;padding-top:5px;"> 
													<div style="float: left;"> 
													<input type="checkbox" checked="checked"
													name="omTabDTO.selectedRoutedProviders[${i.count}]" value="${routedProviderLists.id}" id="${i.count}"
													class="routedCheckbox" />
													</div>
													<div style="float: left;padding-left:3px;">
														${routedProviderLists.descr}
													 </div>
												</div >
													<c:set var="val2" value="1"></c:set>
												</c:if>
											</c:forEach>
											<c:if test="${val2 == 0}">
											<div style="clear:left;padding-top:5px;">
												<div style="float: left;">
												<input type="checkbox"
													name="omTabDTO.selectedRoutedProviders[${i.count}]" value="${routedProviderLists.id}"
													class="routedCheckbox" />
												</div>
													<div style="float: left;padding-left:3px;">${routedProviderLists.descr}<br />
												 </div>
												</div>
											</c:if>
										</c:forEach>
										</c:if>
									</div>
								</td>
							</c:if>
							
							<c:if test="${fn:contains(filter, 'Status')}">
								<c:choose>
								<c:when test="${fn:contains(filter, 'Schedule')}">
									<td width="222px">
										<div style="float: left;padding-top: 2px;padding-left:0px;">
											<label><b>Schedule Status: </b></label>
									 	</div>
									 	<div id="selectScheduleStatus" class="picked pickedClick"
											style="width:100px; height:12px; float: left;margin-left: 2px;" onclick="hideOtherFilters();">
											<c:choose><c:when test="${null != omModel.filterScheduleStatus && '' != omModel.filterScheduleStatus}">
												<label id="defaultScheduleValStatus"> ${fn:substring(omModel.filterScheduleStatus,0,15)} </label>
											</c:when>
											<c:otherwise>
												<label id="defaultScheduleValStatus">-Select-</label>
											</c:otherwise>
											</c:choose>
									   	</div>
										<div class="select-list select-options" id="selectScheduleStatusOptions"
											style="display: none; margin-left: 105px;width:115px;">
												<ul>
												<c:if test="${null != scheduleStatusList}">
												<c:forEach var="scheduleStatusLists" items="${scheduleStatusList}" varStatus="countStat">
													<li onclick="selectStatus(this,'Schedule');" id="status_li_${countStat.count}" val="${scheduleStatusLists.descr}" value="${scheduleStatusLists.id}">${scheduleStatusLists.descr}</li>
												</c:forEach>
												</c:if>
												</ul>
												<input type="hidden" id="selectedScheduleStatus" name="omTabDTO.selectedScheduleStatus[0]" value="${omModel.selectedScheduleStatus[0]}"/>
												<input type="hidden" id="selectedFilterScheduleStatus" name="omTabDTO.filterScheduleStatus" value="${omModel.filterScheduleStatus}"/>
										</div>
									</td>
								</c:when>
								<c:when test="${fn:contains(filter, 'Sub')}">
									<td width="200px">
										<div style="float: left;padding-top: 2px;">
											<label><b>Order Type: </b></label>
									 	</div>
									 	<div id="selectSubStatus" class="picked pickedClick"
											style="width:100px; float: left;margin-left: 2px;" onclick="hideOtherFilters();">
											<c:choose><c:when test="${null != omModel.filterSubStatus && '' != omModel.filterSubStatus}">
												<label id="defaultSubValStatus"> ${omModel.filterSubStatus} </label>
											</c:when>
											<c:otherwise>
												<label id="defaultSubValStatus">-Select-</label>
											</c:otherwise>
											</c:choose>
									   	</div>
										<div class="select-list select-options" id="selectSubStatusOptions"
											style="display: none; margin-left: 75px;width:135px;">
												<ul>
												<c:if test="${null != subStatusList}">
												<c:forEach var="subStatusLists" items="${subStatusList}" varStatus="countStat">
										 			<li onclick="selectSubStatus(this,'Sub');" id="sub_status_li_${countStat.count}" val="${subStatusLists.descr}">${subStatusLists.descr}</li>
												</c:forEach>
												</c:if>
												</ul>
												<input type="hidden" id="selectedSubStatus" name="omTabDTO.selectedSubStatus[0]" value="${omModel.filterSubStatus}"/>
												<input type="hidden" id="selectedFilterSubStatus" name="omTabDTO.filterSubStatus" value="${omModel.filterSubStatus}"/>
										</div>
									 
									</td>
								</c:when>
								<c:when test="${fn:contains(filter, 'Job Done')}">
									<td width="190px" class="jobDoneCheckbox">
										<div style="float: left;padding-top: 2px;">
											<label><b>Sub Status: </b></label>
									 	</div>
									 	<div id="selectJobDoneSubStatus" class="picked pickedClick"
											style="width:100px; float: left;margin-left: 2px;">
											<c:choose>
											<c:when test="${fn:length(omModel.selectedJobDoneSubStatus)>0}">
												<label id="defaultJobDoneSubStatus"> ${fn:length(omModel.selectedJobDoneSubStatus)} selected </label>
											</c:when>
											<c:otherwise>
												<label id="defaultJobDoneSubStatus">-Select-</label>
											</c:otherwise>
											</c:choose>
									   	</div>
										<div class="select-options" id="selectJobDoneSubStatusOptions"
											style="display: none;" onmouseout="">
												
										<c:if test="${null != jobDoneSubStatusList}">
											<c:forEach var="jobDoneSubStatusLists" items="${jobDoneSubStatusList}" varStatus="i">
												<c:set var="val1" value="0"></c:set>
													<c:forEach var="jobDoneSubStatus" items="${omModel.selectedJobDoneSubStatus}">
												<c:if test="${jobDoneSubStatusLists.id == jobDoneSubStatus}">
													<div style="clear:left;padding-top:5px;">
														<div style="float: left;"> <input type="checkbox" checked="checked"
														name="omTabDTO.selectedJobDoneSubStatus[${i.count}]" value="${jobDoneSubStatusLists.id }" id="${i.count}"
														class="jobDoneCheckbox" /> </div>
														<div style="float: left;padding-left:3px;">${jobDoneSubStatusLists.descr}
														</div>
													</div>
													<c:set var="val1" value="1"></c:set>
												</c:if>	
												</c:forEach>
												<c:if test="${val1 == 0}">
												<div style="clear:left;padding-top:5px;">
													<div style="float: left;">  
														<input type="checkbox"
														name="omTabDTO.selectedJobDoneSubStatus[${i.count}]" value="${jobDoneSubStatusLists.id}" id="${i.count}"
														class="jobDoneCheckbox" />
													</div><div style="float: left;padding-left:3px;">
													 ${jobDoneSubStatusLists.descr}</div>
													</div>
											</c:if>
										</c:forEach>
										</c:if>
										</div>
								</td>
							</c:when>
							<c:when test="${fn:contains(filter, 'Current Orders')}">
									<td width="190px" class="currentOrdersCheckbox">
										<div style="float: left;padding-top: 2px;">
											<label><b>Sub Status: </b></label>
									 	</div>
									 	<div id="selectCurrentOrdersSubStatus" class="picked pickedClick"
											style="width:100px; float: left;margin-left: 2px;">
											<c:choose>
											<c:when test="${fn:length(omModel.selectedCurrentOrdersSubStatus)>0}">
												<label id="defaultCurrentOrdersSubStatus"> ${fn:length(omModel.selectedCurrentOrdersSubStatus)} selected </label>
											</c:when>
											<c:otherwise>
												<label id="defaultCurrentOrdersSubStatus">-Select-</label>
											</c:otherwise>
											</c:choose>
									   	</div>
										<div class="select-options" id="selectCurrentOrdersSubStatusOptions"
											style="display: none;" onmouseout="">
												
										<c:if test="${null != currentOrdersSubStatusList}">
											<c:forEach var="currentOrdersSubStatusList" items="${currentOrdersSubStatusList}" varStatus="i">
												<c:set var="val1" value="0"></c:set>
													<c:forEach var="currentOrdersSubStatus" items="${omModel.selectedCurrentOrdersSubStatus}">
												<c:if test="${currentOrdersSubStatusList.id == currentOrdersSubStatus}">
													<div style="clear:left;padding-top:5px;">
														<div style="float: left;"> <input type="checkbox" checked="checked"
														name="omTabDTO.selectedCurrentOrdersSubStatus[${i.count}]" value="${currentOrdersSubStatusList.id }" id="${i.count}"
														class="currentOrdersCheckbox" /> </div>
														<div style="float: left;padding-left:3px;">${currentOrdersSubStatusList.descr}
														</div>
													</div>
													<c:set var="val1" value="1"></c:set>
												</c:if>	
												</c:forEach>
												<c:if test="${val1 == 0}">
												<div style="clear:left;padding-top:5px;">
													<div style="float: left;">  
														<input type="checkbox"
														name="omTabDTO.selectedCurrentOrdersSubStatus[${i.count}]" value="${currentOrdersSubStatusList.id}" id="${i.count}"
														class="currentOrdersCheckbox" />
													</div><div style="float: left;padding-left:3px;">
													 ${currentOrdersSubStatusList.descr}</div>
													</div>
											</c:if>
										</c:forEach>
										</c:if>
										</div>
								</td>
							</c:when>
							
							<c:when test="${fn:contains(filter, 'Cancellations')}">
									<td width="190px" class="cancellationsCheckbox">
										<div style="float: left;padding-top: 2px;" >
											<label><b>Sub Status: </b></label>
									 	</div>
									 	<div id="selectCancellationsSubStatus" class="picked pickedClick"
											style="width:100px; float: left;margin-left: 2px;">
											<c:choose>
											<c:when test="${fn:length(omModel.selectedCancellationsSubStatus)>0}">
												<label id="defaultCancellationsSubStatus"> ${fn:length(omModel.selectedCancellationsSubStatus)} selected </label>
											</c:when>
											<c:otherwise>
												<label id="defaultCancellationsSubStatus">-Select-</label>
											</c:otherwise>
											</c:choose>
									   	</div>
										<div class="select-options" id="selectCancellationsSubStatusOptions"
											style="display: none;" onmouseout="">
												
										<c:if test="${null != cancellationsSubStatusList}">
											<c:forEach var="cancellationsSubStatusList" items="${cancellationsSubStatusList}" varStatus="i">
												<c:set var="val1" value="0"></c:set>
													<c:forEach var="cancellationsSubStatus" items="${omModel.selectedCancellationsSubStatus}">
												<c:if test="${cancellationsSubStatusList.id == cancellationsSubStatus}">
													<div style="clear:left;padding-top:5px;">
														<div style="float: left;"> <input type="checkbox" checked="checked"
														name="omTabDTO.selectedCancellationsSubStatus[${i.count}]" value="${cancellationsSubStatusList.id }" id="${i.count}"
														class="cancellationsCheckbox" /> </div>
														<div style="float: left;padding-left:3px;">${cancellationsSubStatusList.descr}
														</div>
													</div>
													<c:set var="val1" value="1"></c:set>
												</c:if>	
												</c:forEach>
												<c:if test="${val1 == 0}">
												<div style="clear:left;padding-top:5px;">
													<div style="float: left;">  
														<input type="checkbox"
														name="omTabDTO.selectedCancellationsSubStatus[${i.count}]" value="${cancellationsSubStatusList.id}" id="${i.count}"
														class="cancellationsCheckbox" />
													</div><div style="float: left;padding-left:3px;">
													 ${cancellationsSubStatusList.descr}</div>
													</div>
											</c:if>
										</c:forEach>
										</c:if>
										</div>
								</td>
							</c:when>
							
							<c:when test="${fn:contains(filter, 'Revisit')}">
									<td width="190px" class="revisitCheckbox">
										<div style="float: left;padding-top: 2px;" >
											<label><b>Sub Status: </b></label>
									 	</div>
									 	<div id="selectRevisitSubStatus" class="picked pickedClick"
											style="width:100px; float: left;margin-left: 2px;">
											<c:choose>
											<c:when test="${fn:length(omModel.selectedRevisitSubStatus)>0}">
												<label id="defaultRevisitSubStatus"> ${fn:length(omModel.selectedRevisitSubStatus)} selected </label>
											</c:when>
											<c:otherwise>
												<label id="defaultRevisitSubStatus">-Select-</label>
											</c:otherwise>
											</c:choose>
									   	</div>
										<div class="select-options" id="selectRevisitSubStatusOptions"
											style="display: none;" onmouseout="">
												
										<c:if test="${null != revisitSubStatusList}">
											<c:forEach var="revisitSubStatusList" items="${revisitSubStatusList}" varStatus="i">
												<c:set var="val1" value="0"></c:set>
													<c:forEach var="revisitSubStatus" items="${omModel.selectedRevisitSubStatus}">
												<c:if test="${revisitSubStatusList.id == revisitSubStatus}">
													<div style="clear:left;padding-top:5px;">
														<div style="float: left;"> <input type="checkbox" checked="checked"
														name="omTabDTO.selectedRevisitSubStatus[${i.count}]" value="${revisitSubStatusList.id }" id="${i.count}"
														class="revisitCheckbox" /> </div>
														<div style="float: left;padding-left:3px;">${revisitSubStatusList.descr}
														</div>
													</div>
													<c:set var="val1" value="1"></c:set>
												</c:if>	
												</c:forEach>
												<c:if test="${val1 == 0}">
												<div style="clear:left;padding-top:5px;">
													<div style="float: left;">  
														<input type="checkbox"
														name="omTabDTO.selectedRevisitSubStatus[${i.count}]" value="${revisitSubStatusList.id}" id="${i.count}"
														class="revisitCheckbox" />
													</div><div style="float: left;padding-left:3px;">
													 ${revisitSubStatusList.descr}</div>
													</div>
											</c:if>
										</c:forEach>
										</c:if>
										</div>
								</td>
							</c:when>
								<c:otherwise>
									<td width="170px">
										<div style="float: left;padding-top: 2px;">
											<label style="margin-left: 2px;"><b> Status: </b></label>
										</div>
										<div id="selectStatus" class="picked pickedClick"
											style="width: 100px; float: left;margin-left: 2px;" onclick="hideOtherFilters();">
											<c:choose><c:when test="${null != omModel.filterStatus && '' != omModel.filterStatus}">
												<label id="defaultValStatus"> ${omModel.filterStatus} </label>
											</c:when>
											<c:otherwise>
												<label id="defaultValStatus">-Select-</label>
											</c:otherwise>
											</c:choose>
										</div>
										<div class="select-list select-options" id="selectStatusOptions"
											style="display: none; margin-left: 50px;width:110px;">
												<ul>
												<c:if test="${null != statusList}">
												<c:forEach var="statusLists" items="${statusList}" varStatus="countStatus">
													<li onclick="selectStatus(this,'');" id="status_li_${countStatus.count}" val="${statusLists.descr}" value="${statusLists.id}">${statusLists.descr}</li>
												</c:forEach>
												</c:if>
												</ul>
												<input type="hidden" id="selectedStatus" name="omTabDTO.selectedStatus[0]" value="${omModel.selectedStatus[0]}"/>
												<input type="hidden" id="selectedFilterStatus" name="omTabDTO.filterStatus" value="${omModel.filterStatus}"/>
										</div>
									 	
									</td>
								</c:otherwise>
								</c:choose>
							</c:if>
			
							<c:if test="${fn:contains(filter, 'Appointment Date')}">
								<td width="240px" id="tdApptDate">
									<div style="width: 130px; float: left;padding-top: 2px;">
										<label><b>Appointment Date: </b></label>
									</div>
									<div id="selectApptDate" class="picked pickedClick"
											style="width:90px; float: left;" onclick="hideOtherFilters();">
											<c:choose>
											<c:when test="${null != omModel.appointmentType && '' != omModel.appointmentType}">
												<c:choose><c:when test="${omModel.appointmentType == '2days'}">
													<label id="defaultApptDate">Next 2 days</label>
												</c:when>
												<c:when test="${omModel.appointmentType == '3days'}">
													<label id="defaultApptDate">Next 3 days</label>
												</c:when>
												<c:otherwise><label id="defaultApptDate">${omModel.appointmentType}</label></c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise>
												<label id="defaultApptDate">-Select-</label>
											</c:otherwise>
											</c:choose>
									   	</div>
									   	<div class="select-list select-options" id="selectApptDateOptions"
											style="display: none; margin-left: 130px;width:95px;">
												<ul>
										 			<li onclick="selectApptDate(this);" id="app_dt_1" val="Today">Today</li>
													<li onclick="selectApptDate(this);" id="app_dt_2" val="Tomorrow">Tomorrow</li>
													<li onclick="selectApptDate(this);" id="app_dt_3" val="2days">Next 2 days</li>
													<li onclick="selectApptDate(this);" id="app_dt_4" val="3days">Next 3 days</li>
													<li onclick="selectApptDate(this);showApptDateRange(this);" id="app_dt_5" val="range">Enter Range</li>
												</ul>
												<input type="hidden" id="selectedApptDate" name="omTabDTO.appointmentType" value="${omModel.appointmentType}"/>
										</div>
									<div id="apptDateRangeDiv" style="float: left;display: none;"> 
										<input type="text" id="fromDate"
											name="omTabDTO.appointmentStartDate"
											style="width: 65px;"
											onblur="checkApptDateError(this.id);"
											onchange="checkApptDateError(this.id);checkFilterSubmit();"
											onfocus="hideOtherFilters();"
											constraints="{min: '${todaysDate}'}" 
											value="${omModel.appointmentStartDate}"/>
										<span id="To" style="">to</span>
										    <input type="text" id="toDate" 
											style="width: 65px;"
											name="omTabDTO.appointmentEndDate"
											onblur="checkApptDateError(this.id);"
											onchange="checkApptDateError(this.id);checkFilterSubmit();"
											onfocus="hideOtherFilters();" constraints="{min: '${fromDate}'}" 
											value="${omModel.appointmentEndDate }"/>
									</div>
								</td>
							</c:if>
						</c:forEach>
					</tr>
				</table>
			</td>
			<td>	
								<div style="float: right;padding-left:12px;">
									<a href="#" id="resetLink" style="text-decoration: underline;"><b>Reset
										Filters</b></a>
								</div>
							</td>
							<c:if test="${omDisplayTab == 'Print Paperwork'}">
								<td align="center" width="100px;">
									<div id="printPaperWork" style="cursor: pointer; display: none">
										<a href="#" class="actionButton largePDF right split"
											style="text-decoration: none; color: #000000; text-align: center;"
											id="viewPDF"><span style="cursor:pointer;">View PDF </span><img
											src="${staticContextPath}/images/order_management/pdficon_small.png" style="cursor:pointer;" width="10px;" height="10px;"/>
										</a>
									</div>
								</td>
							</c:if>
							<td>
								<div style="float: right;padding-left:8px">
									<span id="XofYDisplay">Showing ${currentSOCount} of ${totalTabCount}</span>
									<a href="#" id="gridNavigator" class="viewMoreLink" style="text-decoration: none;">
										<c:if test="${totalTabCount > countLimit &&  currentSOCount < totalTabCount}">
											<i class="icon-double-angle-right" style="font-size: 14px;"></i>
										</c:if>
									</a>
								</div>
							</td>
		</tr>
	</table>
	<input type="hidden" id="selectInd" value=""/>
	</form>
	</div>
	<div>
		<div id="omErrorDiv" class="errorBox" style="width:300px;display: none;">
		</div>
		<div id="omSuccessDiv" class="successBox" style="width:300px;display: none;">
		</div>
			<c:if test="${not empty omTabDTO.result}">
				<div id="omSuccessDiv" class="successBox" style="width:300px;display: none;">
					<p><c:out value="${omTabDTO.result}"></c:out> </p>
				</div>
			</c:if>
	</div>
</body>
</html>
