<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<script type="text/javascript">	
	jQuery(document).ready(function($){
	
	
		$('#widgetErrorMessageID').hide();
		
		$("#requestNewServiceDate").click( function(){
			$("#requestNewServiceDateDiv").toggle();
			if($("#requestNewServiceDateInd").val() == "false" ){
				$("#requestNewServiceDateInd").val("true");
			}else{
				$("#requestNewServiceDateInd").val("false")
			}

		});
		
		$("#radioSpecificDate").click( function(){
			
			if($(this).prop('checked')){
				$("#newDateBySpecificDateDiv").show();
				$("#newDateByRangeDiv").hide();
				$("#specificOrRange").val("specific");
			}
		});
			
		$("#radioByRange").click(function(){

			if($(this).prop('checked')){
				$("#newDateBySpecificDateDiv").hide();
				$("#newDateByRangeDiv").show();
				$("#specificOrRange").val("range");
			}
		});
		var today = new Date();
		
		var soStartDate = new Date(${startYear}, ${startMonth} -1,  ${startDay});
		$("#bidExpirationDatepicker").datepicker({minDate:today, showOn: 'both', buttonImage: '${staticContextPath}/images/icons/calendarIcon.gif', buttonImageOnly: true,yearRange: '-200:+200'});
		$("#newDateByRangeFrom").datepicker({minDate:today, showOn: 'both', buttonImage: '${staticContextPath}/images/icons/calendarIcon.gif', buttonImageOnly: true});
		$("#newDateByRangeTo").datepicker({minDate:today,  showOn: 'both', buttonImage: '${staticContextPath}/images/icons/calendarIcon.gif', buttonImageOnly: true});
		$("#newDateBySpecificDate").datepicker({minDate:today, showOn: 'both', buttonImage: '${staticContextPath}/images/icons/calendarIcon.gif', buttonImageOnly: true});
		
		var fromDate;
		$("#newDateByRangeFrom").change(function(){
			if($("#newDateByRangeFrom").val()!=''){
				fromDate = new Date($("#newDateByRangeFrom").val());
				$("#newDateByRangeTo").datepicker('destroy');
				$("#newDateByRangeTo").datepicker({minDate:fromDate, showOn: 'both', buttonImage: '${staticContextPath}/images/icons/calendarIcon.gif', buttonImageOnly: true});
				
			}
		});

		$("#newDateBySpecificDate").change(function(){
			// Carlos trying to do some validation here			
		});
		
		var totalLaborDef = "Be sure to include sales tax in your estimates. Also, a 10% service fee will be deducted from the final total price.";
		var showExplainer = true;
		$(".glossaryItem").mouseover(function(e){
			if(showExplainer) {
		      	var x = e.pageX;
		      	var y = e.pageY;
		      	$("#explainer").css("top",y-60);
		      	$("#explainer").css("left",x-270);
		
		      	$("#explainer").css("position","absolute");
		      	var explDefId = eval($(this).attr("id"));
		      	
		      	var arrow = '<div id="pointerArrow" style="position:absolute; float:left; top:30%; right:-13px; width:16px; height:19px; background: url(/ServiceLiveWebUtil/images/icons/explainerArrowRight.gif) no-repeat 0 0;" />';
		      	$("#explainer").html(arrow + explDefId);
		      	//$("#explainer").show();
		      	showExplainer = false; //only show once
     		}
     	}); 
     	
 		$(".glossaryItem").mouseout(function(e){
 		$("#explainer").hide();
 		});
 		
 		$(".glossaryItem").click(function(e){
 		$("#explainer").hide();
 		});

 		$('#bidPriceTypeFixed').click(function() {
 			$('#bidPriceType').val("fixed");
 			$('#fixedPriceBidFields').show();
 			$('#hourlyRateBidFields').hide();
 			calculateBidTotals();
 		});

 		$('#bidPriceTypeRate').click(function() {
 			$('#bidPriceType').val("rate");
 			$('#fixedPriceBidFields').hide();
 			$('#hourlyRateBidFields').show();
 			calculateBidTotals();
 		});
 		
 		$('#totalLabor').blur(function(e){
 			var aValue = $('#totalLabor').val();
 			if (aValue > 0 && aValue.indexOf('.')==-1) {
 			$('#totalLabor').val(aValue+'.00');
 			}
 			calculateBidTotals();
 		
 		});

 		$('#partsMaterials').blur(function(e){
 		
 		 	var aValue = $('#partsMaterials').val();
 			if (aValue > 0 && aValue.indexOf('.')==-1) {
 			$('#partsMaterials').val(aValue+'.00');
 			}
 			calculateBidTotals();
 		
 		});

 		$('#laborRate').blur(function(e){
 			var aValue = $('#laborRate').val();
 			if (aValue > 0 && aValue.indexOf('.')==-1) {
 			$('#laborRate').val(aValue+'.00');
 			}
 			calculateBidTotals();
 		});

 		$('#totalHours').blur(function(e){
 			calculateBidTotals();
 		});
 		
 	
 		$("#bidExpirationFieldsShow").click(function(){
			 	$("#bidExpirationFieldsContainer").toggle();
			 	if ($("#bidExpirationFieldsShowInd").val() == "false"){
			 		$("#bidExpirationFieldsShowInd").val("true");
			 	}else{
			 		$("#bidExpirationFieldsShowInd").val("false");			 	 
			 	}
 		});

 		initializeBidValues();
 		
 	});

 	function initializeBidValues() {
 		var aValue = $('#totalLabor').val();
		if (aValue == null || aValue == '') {
			$('#totalLabor').val("0.00");
		}

		aValue = $('#partsMaterials').val();
		if (aValue == null || aValue == '') {
			$('#partsMaterials').val("0.00");
		}

		aValue = $('#laborRate').val();
		if (aValue == null || aValue == '') {
			$('#laborRate').val("0.00");
		}

		aValue = $('#totalHours').val();
		if (aValue == null || aValue == '') {
			$('#totalHours').val("0");
		}
 	}

 	function calculateBidTotals() {
 		var total = 0.0;
 		if ($('#bidPriceType').val() == "fixed") {
			if($('#totalLabor').val() != null && $('#totalLabor').val() != '')
				total += parseFloat($('#totalLabor').val());
 		} else {
 	 		var totalLabor = 0.0;
 			if ($('#laborRate').val() != null && $('#laborRate').val() != ''
 	 				&& $('#totalHours').val() != null && $('#totalHours').val() != '') {
 	 			totalLabor = parseFloat($('#laborRate').val()) * parseFloat($('#totalHours').val());
 			}
 			total += totalLabor;
 			
 			if (totalLabor.toString().indexOf('.')==-1){
 				totalLabor += ".00";
 			}
 			$('#maxLaborFromRateValue').html(totalLabor);
 		}

 		if($('#partsMaterials').val() != null && $('#partsMaterials').val() != '')
			total += parseFloat($('#partsMaterials').val());
			
		if(total.toString().indexOf('.')==-1){
		 total += ".00";
		}
		$('#bidTotalValue').html(total);
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
	
	
	function validateWidgetSubmit()
	{
		$('#widgetErrorMessageID>ul').children().remove();
		var current_date = ""
			if( ${currMM} < 10 ){
				current_date += '0'+ ${currMM};
			}else{
				current_date += ${currMM};
			}
			current_date += '/';
			if( ${currDD} < 10 ){
				current_date += '0'+ ${currDD};
			}else{
				current_date += ${currDD};
			}
			current_date += '/' +  ${curryyyy}; 
		var current_hour = 0;
			current_hour =  ${currHour24} ;
		var validationSuccess = true;

		if ($('#bidPriceType').val() == "fixed") {
			var totalLabor = $('#totalLabor').val();
			if(totalLabor == null || totalLabor == '')
			{
				//$('#widgetErrorMsg').html('Please Enter Amount for Total Labor');
				$('#widgetErrorMessageID>ul').append('<li>' + 'Please Enter Amount for Total Labor' + '</li>');
				validationSuccess = false;
			} else {
				var totalLaborValue = parseFloat($('#totalLabor').val());
				if (totalLaborValue <= 0) {
					$('#widgetErrorMessageID>ul').append('<li>' + 'Please enter an amount greater than 0 for Total Labor' + '</li>');
					validationSuccess = false;
				}
			}
 		} else if ($('#bidPriceType').val() == "rate") {
 			var laborRate = $('#laborRate').val();
 			if(laborRate == null || laborRate == '')
 			{
 				$('#widgetErrorMessageID>ul').append('<li>' + 'Please Enter the Labor Rate' + '</li>');
 				validationSuccess = false;
 			} else {
				var laborRateValue = parseFloat($('#laborRate').val());
				if (laborRateValue <= 0) {
					$('#widgetErrorMessageID>ul').append('<li>' + 'Please enter an amount greater than 0 for Labor Rate' + '</li>');
					validationSuccess = false;
				}
			}

 			var totalHours = $('#totalHours').val();
 			if(totalHours == null || totalHours == '')
 			{
 				$('#widgetErrorMessageID>ul').append('<li>' + 'Please Enter the Maximum Hours' + '</li>');
 				validationSuccess = false;
 			} else {
				var totalHoursValue = parseFloat($('#totalHours').val());
				if (totalHoursValue <= 0) {
					$('#widgetErrorMessageID>ul').append('<li>' + 'Please enter an amount greater than 0 for Maximum Hours' + '</li>');
					validationSuccess = false;
				}
			}
 		} else {
 			$('#widgetErrorMessageID>ul').append('<li>' + 'Please Choose Fixed Price or Hourly Rate' + '</li>');
			validationSuccess = false;
 		}		
		
		var partsMaterials = $('#partsMaterials').val();
		if(partsMaterials == null || partsMaterials == '')
		{
			//$('#widgetErrorMsg').html('Please Enter Amount for Total Materials');
			$('#widgetErrorMessageID>ul').append('<li>' + 'Please Enter Amount for Total Materials' + '</li>');
			validationSuccess = false;
		}
		
		var bidExpirationDate = $('#bidExpirationDatepicker').val();
		//if(bidExpirationDate == null || bidExpirationDate == '')
		//{
			//$('#widgetErrorMsg').html('Please Enter Amount for Total Materials');
		//	$('#widgetErrorMessageID>ul').append('<li>' + 'Please Enter an Expiration Date for the bid' + '</li>');
		//	validationSuccess = false;
		//}
		if ($('#bidExpirationFieldsShowInd').val() == "true" ){
			if($('#bidExpirationDatepicker').val() == null || $('#bidExpirationDatepicker').val() == ""){ 
				$('#widgetErrorMessageID>ul').append('<li>' + 'Please Enter an Expiration Date for the bid' + '</li>');
				validationSuccess = false;		
			}else {
				if(!parseAndCompareDates(current_date,$('#bidExpirationDatepicker').val())){ 
					$('#widgetErrorMessageID>ul').append('<li>' + 'Expiration date and time should be future date.' + '</li>');
					validationSuccess = false;		
				}
				if($('#bidExpirationDatepicker').val() ==	current_date)
				{
					if(parseInt($('#bidExpirationHour').val(), 10) <= parseInt(current_hour,10))
					  { 
						$('#widgetErrorMessageID>ul').append('<li>' + 'Expiration date and time should be future date.' + '</li>');
						validationSuccess = false;
					  }
				}
			}
		} 
		
if ($("#requestNewServiceDateInd").val()== "true" && $("#specificOrRange").val() == "range")
{
		if(($("#newDateByRangeFrom").val() == null || $("#newDateByRangeFrom").val() == "" ) &&
			($("#newDateByRangeTo").val() == null || $("#newDateByRangeTo").val() == "" )){ 
			$('#widgetErrorMessageID>ul').append('<li>' + 'Please enter From and To Date' + '</li>');
			validationSuccess = false;		
		}
		if(!parseAndCompareDates(current_date,$("#newDateByRangeFrom").val())){ 
			$('#widgetErrorMessageID>ul').append('<li>' + 'Service window start date should be future date.' + '</li>');
			validationSuccess = false;		
		}
		if(!parseAndCompareDates($("#newDateByRangeFrom").val(),$("#newDateByRangeTo").val())){
		$('#widgetErrorMessageID>ul').append('<li>' + 'Please make sure the start date is before the end date' + '</li>');
			validationSuccess = false;		
		}
}		
		//if(!parseAndCompareDates($("#newDateByRangeTo").val(),$("#bidExpirationDatepicker").val())){
		//$('#widgetErrorMessageID>ul').append('<li>' + 'Please make sure the start date is before the bid expiration date' + '</li>');
		//	validationSuccess = false;		
		//}

		var comment = $('#comment').val().trim();
		if(comment == null || comment == '')
		{
			$('#widgetErrorMessageID>ul').append('<li>' + 'Please enter a note or question to post' + '</li>');
			validationSuccess = false;		
		}
				
		var termsAndConditions = $('#termsAndConditions').is(':checked');
		if(termsAndConditions == false)
		{
			$('#widgetErrorMessageID>ul').append('<li>' + 'Please confirm the Terms and Conditions' + '</li>');
			validationSuccess = false;		
		}
		
		// For new appointment date same as existing date, check that time is not earlier
		// than original time.
		var start_date_str = "";
		if(${startDay} < 10)
			start_date_str += "0";
		start_date_str += ${startMonth} + '/' + ${startDay} + '/' + ${startYear};	

if ($("#requestNewServiceDateInd").val() == "true" && $("#specificOrRange").val() == "specific"){
		if($("#newDateBySpecificDate").val() == null || $("#newDateBySpecificDate").val() == ""){ 
			$('#widgetErrorMessageID>ul').append('<li>' + 'Please enter specific Service date' + '</li>');
			validationSuccess = false;		
		}else {
			if(!parseAndCompareDates(current_date,$("#newDateBySpecificDate").val())){ 
				$('#widgetErrorMessageID>ul').append('<li>' + 'Service window start date and time should be future date.' + '</li>');
				validationSuccess = false;		
			}
			if($("#newDateBySpecificDate").val() ==	current_date)
			{
				if(parseInt($("#hourFrom").val(),10) < parseInt(current_hour,10))
				  { 
					$('#widgetErrorMessageID>ul').append('<li>' + 'Service window start date and time should be future date.' + '</li>');
					validationSuccess = false;
				  }
			}
			if ( parseInt($('#hourFrom').val(),10) > parseInt($('#hourTo').val(),10))
			{
				$('#widgetErrorMessageID>ul').append('<li>' + 'Please make sure the start Time is before the end Time' + '</li>');
				validationSuccess = false;
			}
		}
}
		

		        
		if(validationSuccess)
		{
			$('#widgetErrorMsg').html('');
			$('#widgetErrorMessageID').hide();
		}
		else
		{
			$('#widgetErrorMessageID').show();
			$('#bidConfirmMsg').hide();	
		}
		$('#serverFailureMessage').hide();
		
		return validationSuccess;
	}
	
	function parseAndCompareDates(date1,date2){
		var date1Year, date1Month, date1Day, date2Year, date2Month, date2Day;
		date1Array = date1.split('/');
		date2Array = date2.split('/');
		date1Year = date1Array[2];
		date1Month = date1Array[0];
		date1Day = date1Array[1];
		date2Year = date2Array[2];
		date2Month = date2Array[0];
		date2Day = date2Array[1];
		
		if (parseInt(date1Year, 10) > parseInt(date2Year, 10)) {
			return false;
		} else if(parseInt(date1Year, 10) == parseInt(date2Year, 10)) {
			if(parseInt(date1Month, 10) > parseInt(date2Month, 10)) {
			return false;
			} else if (parseInt(date1Month,10) == parseInt(date2Month,10) && parseInt(date1Day,10) > parseInt(date2Day,10)) {
			return false;
		}
		}
			return true;
		}
</script>
	
 	<div id="soBidFormHdr" class="grayModuleHdr" style="background: #58585A url(../images/titleBarBg.gif);">
 		Service Order Bid
	</div>
	
	
<div id="soBidFormContent" style="" class="grayModuleContent">

	<c:if test="${saveBidErrorMessage != null}">
	 	<div class="errorBox" id="serverFailureMessage">
	 		<ul>
	 			<li>
	 				${saveBidErrorMessage}
	 			</li>
			</ul>
	    </div>	
	</c:if>
 	<div class="errorBox" id="widgetErrorMessageID" style="display:none;">
 		<ul>
		</ul>
    </div>


	<div>
	
		<c:if test="${showBidSuccessMessage == true}">
			<div id="bidConfirmMsg" style="display:block;">Your bid has been posted for acceptance.</div>
		</c:if>
	
		<p>An asterisk (<span class="req">*</span>) indicates a required field</p>
<s:form action="soDetailsBid_submitBid.action" theme="simple">

<input type="hidden" name="bidderResourceId" id="bidderResourceId" value="${bidderResourceId}" />
<input type="hidden" name="bidPriceType" id="bidPriceType" value="fixed"/>
<input type="hidden" name="soId" id="soId" value="${SERVICE_ORDER_ID}" />
<input type="hidden" name="resId" id="resId" value="${routedResourceId}" />
<fieldset id="bidTotals">
		<div style="float:left"><input type="radio" id="bidPriceTypeFixed" name="bidPriceTypeRadio" checked="checked"></input> Fixed Price&nbsp;&nbsp;&nbsp;<input type="radio" id="bidPriceTypeRate" name="bidPriceTypeRadio"></input> Hourly Rate</div><br />
		<div id="fixedPriceBidFields" class="show">
			<label for="totalLabor"><br />Maximum Labor:<span class="req">*</span></label>
			$<s:textfield id="totalLabor" name="totalLabor" theme="simple" value="%{totalLabor}" cssClass="glossaryItem" onkeypress="return numberOnly(this, event, true)" onkeyup="roundToSecondDecimal(this);"/><br/>
		</div>
		<div id="hourlyRateBidFields" class="hide">
			<label id="hourlyRateLabel" ><br /><strong>Hourly Rate</strong><span class="req">*</span></label><br />
			<fieldset id="hourlyRateFieldset" >
				$<s:textfield id="laborRate" name="laborRate" theme="simple" value="%{laborRate}" cssClass="glossaryItem" onkeypress="return numberOnly(this, event, true)" onkeyup="roundToSecondDecimal(this);"/>
				&nbsp;<label>maximum hours</label>&nbsp;
				<s:textfield id="totalHours" name="totalHours" cssStyle="width: 30;" theme="simple" value="%{totalHours}" onkeypress="return numberOnly(this, event, false)"/><br/>
			</fieldset>
			<div style="width:100%;">
				<div id="maxLaborFromRateValue"><s:property value="maxLaborFromRate"/></div><span id="laborTotalLabel">Maximum Labor: $</span>
			</div>
			<br style="clear:both;"/>	
		</div>
		<label for="partsMaterials">Maximum Materials:<span class="req">*</span></label>
		$<s:textfield id="partsMaterials" name="partsMaterials" theme="simple" value="%{partsMaterials}" onkeypress="return numberOnly(this, event, true)" onkeyup="roundToSecondDecimal(this);"/><br/>
		<div style="width:100%;">
			<div id="bidTotalValue"><s:property value="total"/></div><span id="bidTotalTabel">Total Bid: $</span>
		</div>	
</fieldset>	
	


<fieldset id="bidExpirationModule">
	<h4 class="left">Bid Expires:&nbsp;</h4>
		<p class="left">
		<c:choose>
			<c:when test="${serviceDate2 != null}">
				<fmt:formatDate value="${serviceDate2}" type="date" dateStyle="short"/>
			</c:when>
			<c:otherwise>
				<fmt:formatDate value="${serviceDate1}" type="date" dateStyle="short"/>
			</c:otherwise>
		</c:choose>
		</p>
	<br style="clear:both;"/>
	<s:checkbox  id="bidExpirationFieldsShow" name="bidExpirationFieldsShow" cssClass="left"/>
	<input type="hidden" name="bidExpirationFieldsShowInd" id="bidExpirationFieldsShowInd" value="false"/>
	<label class="left" style="padding:0; margin-top:5px;" for="bidExpirationFieldsShow">Change Bid Expiration</label>
	<div id="bidExpirationFieldsContainer">
	<fieldset class="right noborder">
	at <s:select  id="bidExpirationHour" name="bidExpirationHour" listKey="value" listValue="label" theme="simple" cssStyle="width: 85px;"
		 size="1" list="hourDropdownList" value="%{bidExpirationHour}"/>
	</fieldset>
	<fieldset class="right noborder" style="margin-right:20px;">
<s:textfield  id="bidExpirationDatepicker" name="bidExpirationDatepicker" value="%{bidExpirationDatepicker}" theme="simple"/>
	</fieldset>
	<br style="clear:both;"/>
	</div>
<!-- 
</fieldset>
<fieldset id="serviceDatesModule">
 -->
 	<br style="clear:both;"/>
		<s:checkbox id="requestNewServiceDate" name="requestNewServiceDate" theme="simple" cssClass="left" />
		<input type="hidden" name="requestNewServiceDateInd" id="requestNewServiceDateInd" value="false"/>
		<label for="requestNewServiceDate" class="left" style="margin-top:5px;">Request New Service Date & Time</label>
		<div id="requestNewServiceDateDiv" style="display:none">
			<p>Enter a time change if you are not available for the dates  the buyer specified.</p>
			<input type="radio"  id="radioSpecificDate" name="specificDateOrRange" value = "radioSpecificDate"><label for="radioSpecificDate"> Specified Date</label>
			<input type="radio"  id="radioByRange" name="specificDateOrRange" checked="checked" value = "radioByRange"><label for="radioByRange"> Range</label>
			<input type="hidden" name="specificOrRange" id="specificOrRange" value="range"/>
			
	<div id="newDateByRangeDiv">
		<fieldset class=" right noborder">
		<s:textfield  id="newDateByRangeTo" name="newDateByRangeTo" theme="simple"/>
		</fieldset>
		<p class="right" style="margin: 0 3px">to</p> 
		<fieldset class=" right noborder">
		<s:textfield  id="newDateByRangeFrom" name="newDateByRangeFrom" theme="simple"/>
		</fieldset>
	</div>
	
	<div id="newDateBySpecificDateDiv">
		<fieldset class="noborder">
		<s:textfield  id="newDateBySpecificDate" name="newDateBySpecificDate" theme="simple"/>
		</fieldset>
		<fieldset class="noborder">		
		<s:select  id="hourFrom" name="hourFrom" listKey="value" listValue="label" theme="simple" cssStyle="width: 85px;"
		 size="1" list="hourDropdownList" /> to
		<s:select  id="hourTo" name="hourTo" listKey="value" listValue="label" theme="simple" cssStyle="width: 85px;"
		 size="1" list="hourDropdownList" />
		 </fieldset>
	</div>
		</div>
		
</fieldset>	
		
<fieldset id="bidCommentsModule">
		<h4>Comments with Bid:<span class="req">*</span></h4>
		<s:textarea id="comment" name="comment" theme="simple" cssStyle="width:95%;" value="%{comment}"/>
</fieldset>

		<s:checkbox id="termsAndConditions" name="termsAndConditions" theme="simple" cssStyle="margin-top:5px;" value="%{termsAndConditions}"/>
		I accept the <a href="#" onclick="showHideTermsAndConditions('show');return false;">Terms and Conditions</a><span class="req">*</span>
		<div>
		<c:if test="${sealedBidInd == false}">
		<p class="left" style="width:72%;">This estimate will be visible by all providers selected for the project.</p>
		</c:if>
		<s:submit theme="simple" cssClass="left" cssStyle="margin-top:10px;" value="Submit" onclick="return validateWidgetSubmit();"/>
		
		<br style="clear:both;"/>
		</div>
	</s:form>
	</div>
</div>
	<div id="buttonSOM_RejectSO" name="buttonSOM_RejectSO">
		<input width="131" height="17" type="image" class="btn17" onClick="reject();"
			style="background-image: url(/ServiceLiveWebUtil/images/btn/rejectServiceOrder.gif);"
			src="/ServiceLiveWebUtil/images/common/spacer.gif" />
	</div>
<c:set var="displayTab" value="Bid Requests" />
<c:choose>
		<c:when
			test="${(requestScope.cameFromOrderManagement != 'cameFromOrderManagement')}">
			<form id="gotoMonitor" action="serviceOrderMonitor.action?displayTab=${displayTab}">
				<input type="hidden" name="displayTab" value="${displayTab}" />
				<input width="131" height="27" type="image" class="btn27"
					style="background-image: url(${staticContextPath}/images/btn/returnSOM.gif);"
					src="${staticContextPath}/images/common/spacer.gif"/>
			</form>
		</c:when>
		<c:when
			test="${(requestScope.cameFromOrderManagement == 'cameFromOrderManagement')}">	
			<form id="gotoOM" action="orderManagementController.action" style="padding-right: 0px;">
				<input type="hidden" name="omDisplayTab" value="${omDisplayTab}" />
			<input width="131" height="27" type="image" class="btn27"
					style="background-image: url(${staticContextPath}/images/order_management/returnOrderMgmt.gif);"
					src="${staticContextPath}/images/common/spacer.gif"/>
			</form>
			</c:when>
</c:choose>