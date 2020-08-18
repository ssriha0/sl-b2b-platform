<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
.ui-datepicker {
	width: 17em;
	padding: .2em .2em 0;
	display: none
}

.ui-datepicker .ui-datepicker-header {
	position: relative;
	padding: .2em 0
}

.ui-datepicker .ui-datepicker-prev,
.ui-datepicker .ui-datepicker-next {
	position: absolute;
	top: 2px;
	width: 1.8em;
	height: 1.8em
}

.ui-datepicker .ui-datepicker-prev-hover,
.ui-datepicker .ui-datepicker-next-hover {
	top: 1px
}

.ui-datepicker .ui-datepicker-prev {
	left: 2px
}

.ui-datepicker .ui-datepicker-next {
	right: 2px
}

.ui-datepicker .ui-datepicker-prev-hover {
	left: 1px
}

.ui-datepicker .ui-datepicker-next-hover {
	right: 1px
}

.ui-datepicker .ui-datepicker-prev span,
.ui-datepicker .ui-datepicker-next span {
	display: block;
	position: absolute;
	left: 30%;	
	margin-left: -8px;
	top: 50%;
	margin-top: -8px
}

.ui-datepicker .ui-datepicker-title {
	margin: 0 2.3em;
	line-height: 1.8em;
	text-align: center
}

.ui-datepicker .ui-datepicker-title select {
	font-size: 1em;
	margin: 1px 0
}

.ui-datepicker select.ui-datepicker-month,
.ui-datepicker select.ui-datepicker-year {
	width: 49%
}

.ui-datepicker table {
	width: 100%;
	font-size: .9em;
	border-collapse: collapse;
	margin: 0 0 .4em
}

.ui-datepicker th {
	padding: .7em .3em;
	text-align: center;
	font-weight: bold;
	border: 0
}

.ui-datepicker td {
	border: 0;
	padding: 1px
}

.ui-datepicker td span,
.ui-datepicker td a {
	display: block;
	padding: .2em;
	text-align: right;
	text-decoration: none
}

.ui-datepicker .ui-datepicker-buttonpane {
	background-image: none;
	margin: .7em 0 0 0;
	padding: 0 .2em;
	border-left: 0;
	border-right: 0;
	border-bottom: 0
}

.ui-datepicker .ui-datepicker-buttonpane button {
	float: right;
	margin: .5em .2em .4em;
	cursor: pointer;
	padding: .2em .6em .3em .6em;
	width: auto;
	overflow: visible
}

.ui-datepicker .ui-datepicker-buttonpane button.ui-datepicker-current {
	float: left
}

.ui-datepicker.ui-datepicker-multi {
	width: auto
}

.ui-datepicker-multi .ui-datepicker-group {
	float: left
}

.ui-datepicker-multi .ui-datepicker-group table {
	width: 95%;
	margin: 0 auto .4em
}

.ui-datepicker-multi-2 .ui-datepicker-group {
	width: 50%
}

.ui-datepicker-multi-3 .ui-datepicker-group {
	width: 33.3%
}

.ui-datepicker-multi-4 .ui-datepicker-group {
	width: 25%
}

.ui-datepicker-multi .ui-datepicker-group-last .ui-datepicker-header,
.ui-datepicker-multi .ui-datepicker-group-middle .ui-datepicker-header {
	border-left-width: 0
}

.ui-datepicker-multi .ui-datepicker-buttonpane {
	clear: left
}

.ui-datepicker-row-break {
	clear: both;
	width: 100%;
	font-size: 0
}

.ui-datepicker-rtl {
	direction: rtl
}

.ui-datepicker-rtl .ui-datepicker-prev {
	right: 2px;
	left: auto
}

.ui-datepicker-rtl .ui-datepicker-next {
	left: 2px;
	right: auto
}

.ui-datepicker-rtl .ui-datepicker-prev:hover {
	right: 1px;
	left: auto
}

.ui-datepicker-rtl .ui-datepicker-next:hover {
	left: 1px;
	right: auto
}

.ui-datepicker-rtl .ui-datepicker-buttonpane {
	clear: right
}

.ui-datepicker-rtl .ui-datepicker-buttonpane button {
	float: left
}

.ui-datepicker-rtl .ui-datepicker-buttonpane button.ui-datepicker-current,
.ui-datepicker-rtl .ui-datepicker-group {
	float: right
}

.ui-datepicker-rtl .ui-datepicker-group-last .ui-datepicker-header,
.ui-datepicker-rtl .ui-datepicker-group-middle .ui-datepicker-header {
	border-right-width: 0;
	border-left-width: 1px
}
</style>
<script type="text/javascript"
	src="${staticContextPath}/javascript/jquery/jqueryui/jquery-ui-1.10.4.custom.min.js"></script> 

<script type="text/javascript">	
	jQuery(document).ready(function($){
	if($("#resourceAvailableForCO").val() == 'false'){
		$("#submitButton").hide();
		$("#rescheduleReasons").hide();
	}
	var resourcesPanelHeight = $("#routedResourcesPanel").height();
	
	$("#rescheduleServiceDateHeader").click(function(){
		$("#rescheduleServiceDate").prop('checked',false);
		$("#rescheduleServiceDateBody").toggle();
		if($("#rescheduleServiceDateBody").css('display') == 'block'){$("#rescheduleServiceDate").prop('checked',true);}	
		if($("#routedResourcesPanel").css('display') != 'block'){$("#routedResourcesPanel").toggle();}
		if(($("#rescheduleServiceDate").prop('checked')==false) && ($("#increaseMaxPrice").prop('checked')==false) && ($(".withdrawbutton").length==0)){		
			$("#routedResourcesPanel").css('display','none');
		}
	}); 
	
	$("#increaseMaximumPriceHeader").click(function(){
		$("#increaseMaxPrice").prop('checked',false);
		$("#increaseMaximumPriceBody").toggle();
		if($("#increaseMaximumPriceBody").css('display') == 'block'){$("#increaseMaxPrice").prop('checked',true);}	
		if($("#routedResourcesPanel").css('display') != 'block'){$("#routedResourcesPanel").toggle();}
		if(($("#rescheduleServiceDate").prop('checked')==false) && ($("#increaseMaxPrice").prop('checked')==false) && ($(".withdrawbutton").length==0)){		
			$("#routedResourcesPanel").css('display','none');
		}
		
	});
	var today = new Date();
	$("#conditionalChangeDate1").datepicker({minDate:today , dateFormat: 'yy-mm-dd'});
	$("#conditionalChangeDate2").datepicker({minDate:today , dateFormat: 'yy-mm-dd'});
	$("#specificDate").datepicker({minDate:today , dateFormat: 'yy-mm-dd'});
	$("#conditionalExpirationDate").datepicker({minDate:today , dateFormat: 'yy-mm-dd'});
	
	var fromDate;
	$("#conditionalChangeDate1").change(function(){
		if($("#conditionalChangeDate1").val()!=''){
			fromDate = $("#conditionalChangeDate1").val().split('-');
			fromDate = fromDate[1]+"/"+fromDate[2]+"/"+fromDate[0];
			fromDate = new Date(fromDate);
			$("#conditionalChangeDate2").datepicker('destroy');
			$("#conditionalChangeDate2").datepicker({minDate:fromDate , dateFormat: 'yy-mm-dd'});
		}
	});
	

	$("#rescheduleSpecificDate").click( function(){
			if($(this).prop('checked')){
				$("#rescheduleDateBySpecificDateDiv").show();
				$("#rescheduleDateByRangeDiv").hide();
				$("#conditionalChangeDate1").val('');
				$("#conditionalChangeDate2").val('');
			}
		});
			
		$("#rescheduleDateRange").click(function(){
			if($(this).prop('checked')){
				$("#rescheduleDateBySpecificDateDiv").hide();
				$("#rescheduleDateByRangeDiv").show();
				$("#specificDate").val('');
			}
		});
	
	$("#rescheduleSpecificDate").click();
	
	$("#cancelLink").click(function(){
		document.getElementById("conditionalOfferForm").reset();
		$("#rescheduleServiceDateBody").hide();
		$("#increaseMaximumPriceBody").hide();
		if($("#routedResourcesPanel").css('display') != 'block'){$("#routedResourcesPanel").toggle();}
		if(($("#rescheduleServiceDate").prop('checked')==false) && ($("#increaseMaxPrice").prop('checked')==false) && ($(".withdrawbutton").length==0)){		
			$("#routedResourcesPanel").css('display','none');
		}
	});
	
	$('#counterOfferFormHdr').click(function(){
		$("#counterOfferContent").toggle();
		if($("#counterOfferContent").css('display') == 'none'){
			$('#counterOfferHdrArrow').prop('src','/ServiceLiveWebUtil/images/widgets/arrowRight.gif');
		}else{
			$('#counterOfferHdrArrow').prop('src','/ServiceLiveWebUtil/images/widgets/arrowDown.gif');
		}
	});
	
	$("#checkAllResources").click(function(){
		var displayedPage=$('#NCOdisplayedList').val();
		var resources =  document.getElementsByName("checkBox_resource_id");
		var totalCount = resources.length;
		var limit = displayedPage*9;
		if(limit>=totalCount){limit=totalCount;}
		for(var i=0;i<limit;i++){
			resources[i].checked = true;	
		}
		
	});
	
	
	$("#unCheckAllResources").click(function(){
		var displayedPage=$('#NCOdisplayedList').val();
		var resources =  document.getElementsByName("checkBox_resource_id");
		var totalCount = resources.length;
		var limit = displayedPage*9;
		if(limit>=totalCount){limit=totalCount;}
		for(var i=0;i<limit;i++){
			resources[i].checked = false;	
		}
	});
	
	});
	 
		function viewMorePros(listType){
			var displayedPage=parseInt(document.getElementById(listType+"displayedList").value);
			var totalCount = parseInt(document.getElementById(listType+"proListSize").value);	
			var totalPages = parseInt((totalCount/9))+(totalCount%9==0?0:1);
			var nextPage = displayedPage+1;
			if(displayedPage<totalPages){
				document.getElementById(listType+"list"+nextPage).style.display= "";
				document.getElementById(listType+"displayedList").value=nextPage;
				document.getElementById(listType+"viewLessPros").style.display ="";
				document.getElementById(listType+"divider").style.display ="";
			}
			if(nextPage==totalPages){
				document.getElementById(listType+"viewMorePros").style.display ="none";
				document.getElementById(listType+"divider").style.display ="none";
			}
			var limit = nextPage*9;
		 	if(limit>=totalCount){limit=totalCount;}
			document.getElementById(listType+"rejResDispCount").innerHTML=limit;	
			
		}
		
		function viewLessPros(listType){
			var displayedPage=parseInt(document.getElementById(listType+"displayedList").value);
			var totalCount = parseInt(document.getElementById(listType+"proListSize").value);		
			var totalPages = parseInt((totalCount/9))+(totalCount%9==0?0:1);
			var previousPage = displayedPage-1;
			if(displayedPage>1){
				document.getElementById(listType+"list"+displayedPage).style.display= "none";
				document.getElementById(listType+"displayedList").value=previousPage;
				document.getElementById(listType+"viewMorePros").style.display ="";
				document.getElementById(listType+"divider").style.display ="";
			}
			if(previousPage==1){
				document.getElementById(listType+"viewLessPros").style.display ="none";
				document.getElementById(listType+"divider").style.display ="none";
			}
			var limit = previousPage*9;
		 	if(limit>=totalCount){limit=totalCount;}
			document.getElementById(listType+"rejResDispCount").innerHTML=limit;			
		}
	
	function numberOnly(obj, e, decimal)
	{
		var key;
		var keychar;
		var hasDecimal=false;
		if (obj.value.indexOf('.')>-1){
		hasDecimal = true;
		}
		
		if (window.event) {
		   key = window.event.keyCode;
		}
		else if (e) {
		   key = e.which;
		}
		else {
		   return true;
		}
		keychar = String.fromCharCode(key);
		
		if ((key==null) || (key==0) || (key==8) ||  (key==9) || (key==13) || (key==27) ) {
		   return true;
		}
		else if ((("0123456789").indexOf(keychar) > -1)) {
		   return true;
		}
		else if (decimal && (keychar == ".") && !hasDecimal) { 
		  return true;
	}
	else
	   return false;
	}



	function roundToSecondDecimal(obj){
		var secDec = obj.value.indexOf('.');
			if (secDec > -1) {secDec = secDec + 2;}
			var lastChar = obj.value.length-1;
			if (secDec > -1 && lastChar > secDec){
			obj.value = obj.value.substring(0, lastChar);
			}
	} 
	// function included to resolve SL-19366
	function loadSpecificDate(){
		$("#rescheduleDateBySpecificDateDiv").show();
		$("#rescheduleDateByRangeDiv").hide();
		$("#conditionalChangeDate1").val('');
		$("#conditionalChangeDate2").val('');
	}
	
</script>
	
 	<div id="counterOfferFormHdr" class="grayModuleHdr"><img alt="" src="/ServiceLiveWebUtil/images/widgets/arrowDown.gif" id="counterOfferHdrArrow">
 		Counter Offer
	</div>
	
<input type="hidden" id="isProviderAdmin" value="${isProviderAdmin}"/>
<input type="hidden" id="isDispatchInd" value="${isDispatchInd}"/>
<a name="providerConditionalOfferPanel" id="providerConditionalOfferPanel"></a>	
<div id="counterOfferContent">
<s:form
	action="providerConditionalOffer.action"
	id="conditionalOfferForm"
	name="conditionalOfferForm"
	method="post"
	enctype="multipart/form-data" theme="simple">

 	<div class="errorBox" id="widgetErrorMessageID" style="display:none;">
 		<ul>
		</ul>
    </div>
    <input type="hidden" id="soId" name="soId" value="${SERVICE_ORDER_ID}" />
    <input type="hidden" id="groupId" name="groupId" value="${groupOrderId}" />
    <input type="hidden" id="resId" name="resId" value="${routedResourceId}" />
    <c:if test="${isProviderAdmin == true || isDispatchInd == true}">
    <div id="counterOfferedRoutedResourcesPanel" style=<c:if test="${hasCounterOffer != true}">"display: none;"</c:if>>
    <b>Current Offers(${fn:length(counteredResources)})</b>
    <c:set var="listNo" value="1"/>
    <table id="COlist${listNo}">
		<tr style="padding-top: 10px;">		
    		<c:forEach var="COproviderFirmResource" items="${counteredResources}" varStatus="firmResourcesCount">
						<c:set var="index" value="${firmResourcesCount.count}" />
						<td width="10%" align="center" class="tt">
							<span class="tooltipleft"><span class="left">
							<table cellpadding="0" cellspacing="0" >
							<tr>
								<td>Counter Offer:</td> 
								<td>
									<table cellpadding="0" cellspacing="0">
									<c:choose>
									<c:when test="${not empty COproviderFirmResource.serviceDate2}">
									<tr><td>
								 		${COproviderFirmResource.serviceDate1} - ${COproviderFirmResource.serviceDate2} <br>
								 		${COproviderFirmResource.conditionalStartTime} - ${COproviderFirmResource.conditionalEndTime}
								 	</td></tr>
									</c:when>
									<c:otherwise>
									<c:if test="${not empty COproviderFirmResource.serviceDate1}">
									<tr><td>
										 ${COproviderFirmResource.serviceDate1} ${COproviderFirmResource.conditionalStartTime} 
									</td></tr>
									</c:if>
									</c:otherwise>
									</c:choose>
									
									<c:choose>
									<c:when test="${not empty COproviderFirmResource.groupCondIncrSpendLimit}">
									<tr><td>
								 		<fmt:formatNumber value="${COproviderFirmResource.groupCondIncrSpendLimit}" type="currency"/> 
									</td></tr>
									</c:when>
									<c:otherwise>
									<c:if test="${not empty COproviderFirmResource.incrSpendLimit}">
									<tr><td>
										<fmt:formatNumber value="${COproviderFirmResource.incrSpendLimit}"
	                  						 	type="currency"/> 
									</td></tr>
									</c:if>	
									</c:otherwise>
									</c:choose>
									</table>
								</td>
							</tr>
							<tr>
							<td colspan="2">Expires: ${COproviderFirmResource.offerExpirationDateString}</td>
							</tr>
							<tr><td colspan="2">Acceptance Status: Pending</td></tr>
							</table>
							</span></span>
							<img alt="" src="/ServiceLiveWebUtil/images/s_icons/magnifier.png" id="magnifier">
						</td>
						<td width="90%">
							${COproviderFirmResource.providerLastName} ${COproviderFirmResource.providerFirstName} #${COproviderFirmResource.resourceId} (${COproviderFirmResource.distanceFromBuyer} Miles)
							<div id="withdrawCounterOfferButton" class="withdrawbutton" style="cursor: pointer;" onclick="withDrawCounterOffer('${COproviderFirmResource.resourceId}','${fromOrderManagement}')"><a href="javascript:void(0);">Withdraw</a></div>
						</td>
						</tr>
						<c:if test="${firmResourcesCount.count%9==0}">
							<c:set var="listNo" value="${listNo+1}"/>
							</table><table id="COlist${listNo}" style="display: none;">
						</c:if><tr style="padding: 10px">
				</c:forEach>
			</tr>
		</table>
	
		<input type="hidden" id="COdisplayedList" value="1"> 
		<input type="hidden" id="COproListSize" value="${index}"> 
		<b id="COdisplayCount" <c:if test="${index<=9}">style="display: none;"</c:if>>
				Showing <c:choose><c:when test="${fn:length(counteredResources)>=9}">
							<span id="COrejResDispCount">9</span>
						</c:when> 
						<c:otherwise>${fn:length(counteredResources)}</c:otherwise>
						</c:choose>
				of ${fn:length(counteredResources)}</b>
		<a href="javascript:void(0);" id="COviewMorePros" onclick="viewMorePros('CO')" <c:if test="${index<=9}">style="display: none;"</c:if>>View More</a>
		<span id="COdivider" style="display: none;"> | </span>
		<a href="javascript:void(0);" id="COviewLessPros" onclick="viewLessPros('CO')" style="display: none;">View Less</a>
	</div>
</c:if>
<div  <c:if test="${(isProviderAdmin == true || isDispatchInd ==true)&& (firmResource == null || fn:length(firmResource)<=0)}">style="display: none"</c:if>>
	<div id="rescheduleReasons">	
	<div id="rescheduleServiceDateHeader">	 
		<s:checkbox id="rescheduleServiceDate" name="rescheduleServiceDate" />Reschedule Service Date
	</div>

	
		<div id="rescheduleServiceDateBody">
		
		<table cellspacing="0" cellpadding="0" style="margin-bottom:10px;">
			<tbody>
				<tr>
					<!-- start loop -->
					<c:forEach items="${rescheduleDateReasons}"
						var="counterOfferReason">
						<tr>
							<td style="width: 5%;">
								<input type="checkbox"
									id="reason_date_${counterOfferReason.counterOfferReasonId}"
									name="reason_date_${counterOfferReason.counterOfferReasonId}"									
									/>
							</td>
							<td style="font: 9px Arial, sans-serif; vertical-align:middle; text-indent:2px;">
								${counterOfferReason.counterOfferReasonDesc}
							</td>
						</tr>
					</c:forEach>
					<!-- end loop -->
			</tbody>
		</table>
		
		
		<input type="radio" name="rescheduleServiceDateRadio" id="rescheduleSpecificDate" checked="checked"/>
		<label for="rescheduleSpecificDate" style="margin-right:20px;">Specific Date</label> 
		<input type="radio" name="rescheduleServiceDateRadio" id="rescheduleDateRange"/>
		<label for="rescheduleDateRange">Date Range</label>
	
		<h4>Requested Date/Time</h4>
	
		<div id="rescheduleDateByRangeDiv">
			Between<br/>
			<fieldset class="left noborder">
			<s:textfield  id="conditionalChangeDate1" name="conditionalChangeDate1" theme="simple" cssStyle="width: 90px;"/>&<br/>
			<s:select cssStyle="width: 90px;" id="conditionalStartTime"
				name="conditionalStartTime"
				list="#application['time_intervals']" listKey="descr"
				listValue="descr" multiple="false" size="1" theme="simple" />
				&nbsp;&
			</fieldset>
			<fieldset class="left noborder" style="padding-left:5px;">
			<s:textfield  id="conditionalChangeDate2" name="conditionalChangeDate2" theme="simple" cssStyle="width: 90px;"/><br/>
			<s:select cssStyle="width: 90px;" id="conditionalEndTime"
				name="conditionalEndTime" list="#application['time_intervals']"
				listKey="descr" listValue="descr" multiple="false" size="1"
				theme="simple" />
			</fieldset>
			
	
			<br style="clear:both;"/>
		</div>
	
		<div id="rescheduleDateBySpecificDateDiv" style="display:block;">
			<fieldset class="noborder">
			<s:textfield id="specificDate" name="specificDate" cssClass="left" cssStyle="margin-right:10px;width: 90px" theme="simple"/>
			<s:select cssStyle="width: 90px;" id="specificTime"
				name="specificTime"
				list="#application['time_intervals']" listKey="descr"
				listValue="descr" multiple="false" size="1" theme="simple" />
			</fieldset>
			 <br style="clear:both;"/>
		</div>
	</div>
	
	
	<c:if test='${isNonFunded==true}'>

	<div id="increaseMaximumPriceHeader" style="display: none;">
		<s:checkbox id="increaseMaxPrice" name="increaseMaxPrice"/> Increase Maximum Price
	</div>
	</c:if>
	
	<c:if test='${isNonFunded==false}'>

	<div id="increaseMaximumPriceHeader">
		<s:checkbox id="increaseMaxPrice" name="increaseMaxPrice"/> Increase Maximum Price
	</div>
	</c:if>
	
	<div id="increaseMaximumPriceBody">
	
		<table cellspacing="0" cellpadding="0" style="margin-bottom:10px;" border="0">
			<tbody>
				<tr>
					<!-- start loop -->
					<c:forEach items="${increaseMaxPriceReasons}"
						var="counterOfferReason">
						<tr>
							<td style="width: 5%;">
								<input type="checkbox"
									id="reason_price_${counterOfferReason.counterOfferReasonId}"
									name="reason_price_${counterOfferReason.counterOfferReasonId}"
									/>
							</td>
							<td style="font: 9px Arial, sans-serif; vertical-align:middle; text-indent:2px;">
								${counterOfferReason.counterOfferReasonDesc}
							</td>
						</tr>
					</c:forEach>
					<!-- end loop -->
			</tbody>
		</table>
	
	
		<h4>Increase to:</h4>
		<s:textfield id="conditionalSpendLimit" name="conditionalSpendLimit" onkeypress="return numberOnly(this, event, true)" onkeyup="roundToSecondDecimal(this);"/>
	</div>
</div>
<c:set var="listNo" value="1"/>
<c:set var="checkBoxAvailable" value="false" />
<c:if test="${(isProviderAdmin == true || isDispatchInd == true)&& firmResource != null && fn:length(firmResource)>0}">
	<div id="routedResourcesPanel" style="display: none;">
		<div id="routedResources">
		<b style="padding-left: 5px">Select Provider(s) for this Offer:</b><br>
		<div align="left" style="margin-bottom: 5px;margin-left: 10px;margin-top: 5px">
			<a href="javascript:void(0);" id="checkAllResources">Select All</a> | <a href="javascript:void(0);" id="unCheckAllResources">Clear All</a><br>
		</div>
		<table id="NCOlist${listNo}">
			<tr>			
				<c:forEach var="providerFirmResource" items="${firmResource}" varStatus="firmResourcesCount">
						<c:set var="checkBoxAvailable" value="true" />
						<c:set var="index" value="${firmResourcesCount.count}" />
						<td width="10%" align="center">
							<input type="checkbox" id="resource_id_${providerFirmResource.resourceId}" name="checkBox_resource_id" value="${providerFirmResource.resourceId}">
						</td>
						<td width="90%">
							${providerFirmResource.providerLastName} ${providerFirmResource.providerFirstName} #${providerFirmResource.resourceId} (${providerFirmResource.distanceFromBuyer} Miles)
						</td>
						</tr>
						<c:if test="${firmResourcesCount.count%9==0}">
							<c:set var="listNo" value="${listNo+1}"/>
							</table><table id="NCOlist${listNo}" style="display: none;">
						</c:if>
						<tr>
				</c:forEach>
			</tr>
		</table>
			<b style="margin-left: 10px">
				Showing 
				<c:choose>
				<c:when test="${fn:length(firmResource)>=9}">
							<span id="NCOrejResDispCount">9</span>
				</c:when> 
				<c:otherwise>${fn:length(firmResource)}</c:otherwise>
				</c:choose>
				of ${fn:length(firmResource)}  </b>
		<input type="hidden" id="NCOdisplayedList" value="1"> 
		<input type="hidden" id="NCOproListSize" value="${index}"> 
		<a href="javascript:void(0);" id="NCOviewMorePros" onclick="viewMorePros('NCO')" <c:if test="${index<=9}">style="display: none;"</c:if>>View More</a>
		<span id="NCOdivider" style="display: none;"> | </span>
		<a href="javascript:void(0);" id="NCOviewLessPros" onclick="viewLessPros('NCO')" style="display: none;">View Less</a>
		</div>
		<input type="hidden" value="${checkBoxAvailable}" id="resourceAvailableForCO">
	</div>
</c:if>

<div id="submitButton">
	<div style="margin: 4px">	
	<h4>Offer Expiration:</h4>
	<fieldset class="noborder">
		<s:textfield  id="conditionalExpirationDate" name="conditionalExpirationDate"				
			cssClass="left" cssStyle="margin-right:10px; width: 90px" theme="simple"/>
		
		<s:select cssStyle="width: 90px;" id="conditionalExpirationTime"
			name="conditionalExpirationTime"
			list="#application['time_intervals']" listKey="descr"
			listValue="descr" multiple="false" size="1" theme="simple" />
	</fieldset>
	</div>
	
	<fieldset class="noborder" id="counterOfferButtons">
		<%-- <input type="button" value="SUBMIT" class="button action left"/> --%>
		<input type="hidden" name="fromOrderManagement" value="${fromOrderManagement}"/>
		<s:submit id="counterOfferButtonID" name="counterOfferButtonID" value="SUBMIT"  cssClass="button action left" theme="simple" onclick="return submitCounterOffer();"/>
		<a id="cancelLink" class="left" onclick="loadSpecificDate();">Cancel</a>
	</fieldset>
</div>
</div>
</s:form>
</div>
<br/>