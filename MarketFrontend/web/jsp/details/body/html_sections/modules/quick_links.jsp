<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@ page import="com.newco.marketplace.constants.Constants" %>
<%@page import="com.newco.marketplace.web.constants.QuickLinksConstants"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="CAME_FROM_WORKFLOW_MONITOR" value="<%=Constants.SESSION.CAME_FROM_WORKFLOW_MONITOR%>" />
<c:set var="BUYER_ROLEID" value="<%= new Integer(OrderConstants.BUYER_ROLEID)%>" />
<c:set var="BUYER_ROLEID" value="<%= new Integer(OrderConstants.BUYER_ROLEID)%>" />
<c:set var="PROVIDER_ROLEID" value="<%= new Integer(OrderConstants.PROVIDER_ROLEID)%>" />
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="randomNum" value="<%=""+((int)(Math.random() * 10000000))%>"/>
<c:set var="viewOrderPricing" scope="session" value="<%=session.getAttribute("viewOrderPricing")%>"></c:set>
<c:set var="theTab" value="<%=request.getAttribute("tab")%>"/>
<c:set var="role" value="${roleType}"/>		
<c:set var="fromWF" value="<%=request.getSession().getAttribute("GOTO_WORKFLOW_TAB") %>"/>
<c:set var="displayTab" value="<%=request.getAttribute("displayTab") %>"/>
<c:set var="hideInd" value="block" />
<c:set var="soDetailsTab" value="soDetailsTab"/>
<c:set var="acceptButton" scope="page" value="<%=QuickLinksConstants.GIF_ACCEPT_SERVICE_ORDER%>" />
<c:set var="orderType" value="${soOrderType}"/>

<c:set var="isCounterOfferOff" scope="session" value="<%=session.getAttribute("isCounterOfferOff")%>"></c:set>
<html>
	<head>
	</head>	
<style>
/* required to avoid jumping */
#pricingsummary {
  position: relative;
  top: 0;
  margin-top: 0px;
  padding-top: 0px;
}

#pricingsummary.fixed {
  position: fixed;
  margin-top: 360px;
  width:255px;
}
#pricingsummary.fixedWithlessMarginTop {
  position: fixed;
  margin-top: 50px;
  width:255px;
}
#pricingsummary.fixedwithIndex {
  position: fixed;
  margin-top: 50px;
  width:255px;
  z-index:1000000;
}
#pricingsummary.relative {
  position: relative;
  top: 0;
  margin-top: 0px;
  padding-top: 0px;
}
</style>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
<script type="text/javascript">		
function focusOnDoc(){
	var arr = window.document.URL.toString().split("#");
	var str = arr[arr.length-1];
	if (str.toLowerCase().indexOf("documents") == 0){
		//$('#docsAndPhotos').trigger("click");
		  window.location.hash='#'+encodeURI(str);
		   //var scrollPos  = jQuery("#"+str).offset().top;
		   //alert(scrollPos);
		   // jQuery(window).scrollTop(scrollPos); 
		    //document.getElementById('tries').scrollIntoView();
	}
}
jQuery(document).ready(function (){
	focusOnDoc();
});
 function replaceAll(txt){
	for(i=0; i<3;++i){
		txt = txt.replace(',', '');
	}
	return txt;
}


		jQuery(document).ready(function(){
			<c:if test="${fromOrderManagement == true}" >
				window.location.hash = "#omCounterOffer";
			</c:if>
		});

		$(".cancellationAmount").val('');
		$(".cancelAmt").val('');
		$(".cancelComment").val('');
		
		
		function showProviderRequestDiv()
		{
		
		$(".newRequest").show();
		$(".reportAproblem").hide();
		$(".disagreeSubmit").show();
		$(".agreeSubmit").hide();
		$(".errorBox").hide();
		 //document.getElementById('cancelComntCtr').innerHTML = '600';
		$(".cancelComntCtr").html('600');
		
		$(".cancellationAmount").val('');
		$(".cancelAmt").val('');
		$(".cancelComment").val('');
		$(".errorBoxes").hide();
		
		}
		
		
		
		function hideProviderRequestDiv()
		{
		
		$(".newRequest").hide();
		$(".reportAproblem").show();
		$(".agreeSubmit").hide();
		$(".disagreeSubmit").hide();
		$(".errorBox").hide();
		$(".errorBoxes").hide();
		
		}
		
		function showRequestDiv()
		{
		
		
		$(".newRequest").show();
		$(".submit").hide();
		$(".disagreeSubmit").show();
		$(".errorBox").hide();
		//document.getElementById('cancelComntCtr').innerHTML = '600';
		$(".cancelComntCtr").html('600');
		$(".cancellationAmount").val('');
		$(".cancelAmt").val('');
		$(".cancelComment").val('');
		$(".errorBoxes").hide();
		
		}
		
		
		
		function hideRequestDiv()
		{
		
		$(".errorBox").hide();
		$(".newRequest").hide();
		$(".submit").show();
		$(".disagreeSubmit").hide();
		$(".errorBox").hide();
		$(".errorBoxes").hide();		
		}
		
		
		function showDisagreeDiv()
		{
		
		$(".providerDisagree").show();
		// $(".reportAproblem").show();
		$(".agreeSubmit").hide();
		$(".agreeSubmit").hide();
		$(".errorBox").hide();
		
		document.getElementById("disagreeComplete").checked="";
		document.getElementById("disagreeRequest").checked="";
			$(".errorBoxes").hide();
		
		
		}
		
		
		function hideDisagreeDiv()
		{
		
		$(".providerDisagree").hide();
		$(".newRequest").hide();
		$(".agreeSubmit").show();
		$(".disagreeSubmit").hide();
		$(".reportAproblem").hide();
		$(".errorBox").hide();
		$(".errorBoxes").hide();
		document.getElementById("disagreeComplete").checked="";
		document.getElementById("disagreeRequest").checked="";
		}
		
		function buyerReportProblem()
		{
		document.getElementById('action').value="buyerReportProblem";
		submitPendingCancel();
		}
		
		function providerReportProblem()
		{
		
		getTabForId('Report A Problem');
		}
		
		function buyerWithdraw()
			{
			var err=false;
			var maxSpendLimitPerSO  = ${SecurityContext.maxSpendLimitPerSO};
			var buyerPrice=document.getElementById('buyerPrice').innerHTML;
			buyerPrice=replaceAll(buyerPrice);
			buyerPrice = parseFloat(buyerPrice, 10);
			var buyerPrevAmount=document.getElementById('buyerPvsAmount').value;
	        buyerPrevAmount = replaceAll(buyerPrevAmount);
	        buyerPrevAmount = parseFloat(buyerPrevAmount, 10);
	        /*Wallet and spend limit check, only when buyer's current amount is less than the previous amount*/
	        if(buyerPrice < buyerPrevAmount){
	        	var increasedAmount=buyerPrevAmount - buyerPrice;	
				$("#errorMessageW").html("");
				if(maxSpendLimitPerSO>0 && increasedAmount>maxSpendLimitPerSO){
					$("#errorMessageW").append("The total maximum price exceeds the amount your profile allows.");
					$("#errorMessageW").show();
					err=true;
				}
				var ach=${SecurityContext.autoACH};
				var balance=document.getElementById('balance').value;
				var soAch=document.getElementById('soLevelAutoACH').value;
				balance = replaceAll(balance);
				if(soAch=='false' && balance!='' && increasedAmount > balance)
				{
			
				if(err)
				{
				$("#errorMessageW").append("\n Your wallet does not have enough funding to cover this new combined maximum.");
				$("#errorMessageW").show();
					err=true;
				}
				else
				{
				$("#errorMessageW").append("Your wallet does not have enough funding to cover this new combined maximum.");
				$("#errorMessageW").show();
					err=true;
				}
			}		
	    }else{
	          err=false;
        }
		
		if(!err)
		{
			document.getElementById('action').value="buyerWithdraw";
			submitPendingCancel();
		}
		}
		
		function buyerAgree()
		{
		var maxSpendLimitPerSO  = ${SecurityContext.maxSpendLimitPerSO};		
		var buyerPrice=document.getElementById('buyerPrice').innerHTML;
		buyerPrice=replaceAll(buyerPrice);
        var buyerPricelastRequest=document.getElementById('buyerPricelastRequest').innerHTML;
        buyerPricelastRequest = replaceAll(buyerPricelastRequest);
		var increasedAmount=buyerPrice-buyerPricelastRequest;	
		var err=false;
		$(".errorBoxes").html("");
		if(maxSpendLimitPerSO>0 && increasedAmount>maxSpendLimitPerSO)
		{
		$(".errorBoxes").append("The total maximum price exceeds the amount your profile allows.");
		$(".errorBoxes").show();
		err=true;	
		}
		var ach=${SecurityContext.autoACH};
		var balance=document.getElementById('balance').value;
		var soAch=document.getElementById('soLevelAutoACH').value;
		balance = replaceAll(balance);
		if(soAch=='false' && balance!='' && increasedAmount>balance)
		{
	
		if(err)
		{
		$(".errorBoxes").append("\n Your wallet does not have enough funding to cover this new combined maximum.");
		$(".errorBoxes").show();
			err=true;
		}
		else
		{
		$(".errorBoxes").append("Your wallet does not have enough funding to cover this new combined maximum.");
		$(".errorBoxes").show();
			err=true;
		}
		}		
		if(!err)
		{
		document.getElementById('action').value="buyerAgree";
		submitPendingCancel();
		}
		}
		
		
		function buyerDisagree()
		{
		document.getElementById('action').value="buyerDisagree";
		var validation=calculateAmountMaxSpendLimit();
		if(validation)
		{
		submitPendingCancel();
		}
		}
		function providerWithdraw()
		{
		document.getElementById('action').value="providerWithdraw";
		submitPendingCancel();
		}
		function providerAgree()
		{
		document.getElementById('action').value="providerAgree";
		submitPendingCancel();
		}
		function providerDisagree()
		{
		document.getElementById('action').value="providerDisagree";
		var validation=calculateAmount();
		
		if(validation)
		{
		submitPendingCancel();
		}
		}
		
		function setCancellationAmount(id)
		{
		var value=$(id).val();
		//$(".cancellationAmount").val(value);
		if(value != value.replace(/[^0-9]/g, '')){
			$(".cancellationAmount").val("");
		}else{
			$(".cancellationAmount").val(value);
		}		
		}
		function setCancelAmt(id)
		{
			var value=$(id).val();
			//$(".cancelAmt").val(value);
			if(value != value.replace(/[^0-9]/g, '')){
				$(".cancelAmt").val("");
			}else{
				$(".cancelAmt").val(value);
			}	
		}
		
		function setCancelComment(id)
		{
		var value=$(id).val();
		$(".cancelComment").val(value);
		}
		
		
		function textCounter( field, countfield, maxlimit ) {
	 		if ( field.value.length > maxlimit ) {
	  			field.value = field.value.substring( 0, maxlimit );
	  			field.blur();
	  			field.focus();
	  			$(".cancelComntCtr").html('0');
	  			return false;
	 		} else {	 		
	 			// document.getElementById(countfield).innerHTML = maxlimit - field.value.length;
	 			var  limit=maxlimit - field.value.length;
	 			$(".cancelComntCtr").html(limit);
	 		}
		}
		
		function calculateAmountMaxSpendLimit()
		{
		
		
		$(".errorBox").html("");
		var wholeNumberValue=document.getElementById('cancellationAmount').value;
		wholeNumberValue = replaceAll(wholeNumberValue);
		var decimalValue=document.getElementById('cancelAmt').value;
		var error=false; 
	    var comment=$("#cancelComment").val();
		comment=$.trim(comment);
		comment = escape(comment);
		var length=comment.length;
		
		if(comment!='')
		{
		if(length>600)
		{
		$(".errorBox").append("Comment length should not be greater than 600 characters.");
		$(".errorBox").show();
		error=true;
		}		
		}
		else
		{
		$(".errorBox").append("Please fill out all required fields.");
		$(".errorBox").show();
		error=true;
		}		
	
		
		if(wholeNumberValue!='' )
		{
		
			if(decimalValue ==''){
				 decimalValue = 00;
			}
		
		var decimallength=decimalValue.length;
			if(decimallength==1)
			{
			decimalValue=decimalValue * 10;
			}
			if(wholeNumberValue<1 && decimalValue<1)
			{
			if(error)
			{
			$(".errorBox").append("\n Please enter a price greater than $0.00.");
			$(".errorBox").show();
			error=true;
			}
			else
			{
			
			$(".errorBox").append("Please enter a price greater than $0.00.");
			$(".errorBox").show();
			error=true;
			}
			}
			
			
		var cancelAmount= (1 * wholeNumberValue)+ (0.01 * decimalValue);
		document.getElementById('cancelAmount').value=cancelAmount;
		var buyerPricelastRequest=document.getElementById('buyerPricelastRequest').innerHTML;
		buyerPricelastRequest = replaceAll(buyerPricelastRequest);
		var increasedAmount=cancelAmount-buyerPricelastRequest;
		var maxSpendLimitPerSO  = ${SecurityContext.maxSpendLimitPerSO};
				
		if(maxSpendLimitPerSO>0 && increasedAmount>maxSpendLimitPerSO)
		{
			if(error)
			{
			
			$(".errorBox").append("The total maximum price exceeds the amount your profile allows.");
			$(".errorBox").show();
			error=true;
			}
			else
			{
			$(".errorBox").append("\n The total maximum price exceeds the amount your profile allows.");
			$(".errorBox").show();
			error=true;
			}	
		}
		var ach=${SecurityContext.autoACH};
		var balance=document.getElementById('balance').value;
		var soAch=document.getElementById('soLevelAutoACH').value;
		balance = replaceAll(balance);
		if(soAch=='false' && balance!='' && increasedAmount>balance)
		{
		if(error)
		{
		$(".errorBox").append("\n Your wallet does not have enough funding to cover this new combined maximum.");
		$(".errorBox").show();
				error=true;
		
		}
		else
		{
		$(".errorBox").append("Your wallet does not have enough funding to cover this new combined maximum.");
		$(".errorBox").show();
				error=true;
		
		}
		}
		
		
		
		}
		else
		{
		if(error)
		{
		$(".errorBox").append("\nInvalid Input amount. Please use the format $0.00.");
		$(".errorBox").show();
		}
		else
		{
		$(".errorBox").append("Invalid Input amount. Please use the format $0.00.");
		$(".errorBox").show();
		}
		
		return false;
		}
		if(error)
		{
		return false;
		}
		
		return true;
		}
		
		function calculateAmount()
		{
		
		
		$(".errorBox").html("");
		var wholeNumberValue=document.getElementById('cancellationAmount').value;
		var decimalValue=document.getElementById('cancelAmt').value;
		 var error=false; 
	    var comment=$("#cancelComment").val();
		comment=$.trim(comment);
		comment = escape(comment);
		var length=comment.length;
		
		if(comment!='')
		{
		if(length>600)
		{
		$(".errorBox").append("Comment length should not be greater than 600 characters.");
		$(".errorBox").show();
		error=true;
		}		
		}
		else
		{
		$(".errorBox").append("Please fill out all required fields.");
		$(".errorBox").show();
		error=true;
		}		
	
		
		if(wholeNumberValue!='')
		{
		
			if(decimalValue ==''){
				 decimalValue = 00;
			}
		
		var decimallength=decimalValue.length;
			if(decimallength==1)
			{
			decimalValue=decimalValue * 10;
			}
			if(wholeNumberValue<1 && decimalValue<1)
			{
			if(error)
			{
			$(".errorBox").append("\n Please enter a price greater than $0.00.");
			$(".errorBox").show();
			error=true;
			}
			else
			{
			
			$(".errorBox").append("Please enter a price greater than $0.00.");
			$(".errorBox").show();
			error=true;
			}
			}
			
			
		var cancelAmount= (1 * wholeNumberValue)+ (0.01 * decimalValue)
		
		document.getElementById('cancelAmount').value=cancelAmount;
		
		}
		else
		{
		if(error)
		{
		$(".errorBox").append("\nInvalid Input amount. Please use the format $0.00.");
		$(".errorBox").show();
		}
		else
		{
		$(".errorBox").append("Invalid Input amount. Please use the format $0.00.");
		$(".errorBox").show();
		}
		
		return false;
		}
		if(error)
		{
		return false;
		}
		
		return true;
		}
		function submitPendingCancel()
		{
		
		 document.getElementById("frmPendingCancel").method = "post";                              
        //  document.getElementById("frmPendingCancel").action = "${contextPath}/serviceOrderPendingCancel_pendingCancelSO.action";
          document.getElementById("frmPendingCancel").submit();
                            
		}
		
		function showHistory(e)
		{
		
		var soId='<%=request.getAttribute("SERVICE_ORDER_ID")%>'
		$(".pendingCancelHistory").load("serviceOrderPendingCancelHistory_display.action?servicOrderId="+soId);
		
		
	/*	var evt = e ? e:window.event; 
		var x = 0;
		var y = 0;
		
		x=evt.screenX;
		var width = screen.width;
		if(x >= (width - 240)){
		   x= width - 250;
		}	
		if (evt.pageY) y= evt.clientY;
		else if (evt.clientY)
   		y  = evt.clientY + (document.documentElement.scrolTop ? document.documentElement.scrollTop : document.body.scrollTop);
   		
      	$(".pendingCancelHistory").css("top",y-5);
      	$(".pendingCancelHistory").css("left",x-5); */
		
		$(".pendingCancelHistory").show();
		 
		} 
		function hideHistory()
		{
		$(".pendingCancelHistory").hide();
		}
		
		function showHistoryDiv()
		{
		$(".pendingCancelHistory").show();
		
		}
		
		function showWidget()
		{
		$(".pendingCancelExpand").hide();
		$(".pendingCancelCollapse").show();
		$(".pendingCancelWidget").show();
		
		}
		function hideWidget()
		{
		$(".pendingCancelExpand").show();
		$(".pendingCancelCollapse").hide();
		$(".pendingCancelWidget").hide();
		
		}

function showPendingCancelWid(obj, suffix,status){

			if(status==165)
			{
			var pl='paneBuyer'+suffix;
			var t = document.getElementById(pl);
			t.style.display = 'block';	
				
			}
			}
function hidePendingCancelWid(obj, suffix,status){

			if(status==165)
			{
			var pl='paneBuyer'+suffix;
			var t = document.getElementById(pl);
			t.style.display = 'none';	
				
			}
			}		
		
function isNumberKey(evt)
   		 {
        var charCode = (evt.which) ? evt.which : event.keyCode
        if (charCode > 31 && (charCode < 48 || charCode > 57))
           return false;
        return true;
    	 }

function expandWidget(path,imgId){
 	var pricingSummaryObj = $('#ui-tabs-9 #pricingsummary');	
	var ob = $("."+imgId).attr("src");
	var menu ="";
	menu = imgId+ "Menu";
	
	if(ob.indexOf('arrowRight')!=-1){
		$("."+imgId).attr("src",path+"/images/widgets/arrowDown.gif");
		$("."+menu).slideToggle("slow");		
		if(pricingSummaryObj.length > 0){	
			if(pricingSummaryObj.is(':visible') || pricingSummaryObj.css('display')=='block') {
	    		pricingSummaryObj.removeClass('fixed');
	    		pricingSummaryObj.addClass('relative');
		    	pricingSummaryObj.removeClass('fixedWithlessMarginTop');
		    	pricingSummaryObj.removeClass('fixedwithIndex');
		    	
		}
	}
	}
	if(ob.indexOf('arrowDown')!=-1){
		$("."+imgId).attr("src",path+"/images/widgets/arrowRight.gif");
		$("."+menu).slideToggle("slow");
		if(pricingSummaryObj.length > 0){	
			if(pricingSummaryObj.is(':visible') || pricingSummaryObj.css('display')=='block') {
	    		pricingSummaryObj.removeClass('relative');
	    		pricingSummaryObj.addClass('fixed');
		    	pricingSummaryObj.removeClass('fixedWithlessMarginTop');
		    	pricingSummaryObj.removeClass('fixedwithIndex');
		}
	 }
	}
	
		
}
function collapsePane(obj){
    jQuery(obj).css({backgroundImage:"url(titleBarBg.gif)"}).next("div.menugroupadmin_body").slideToggle(300);
}
	
	/** The value for the displayDiv was set in detailsBody.jsp **/
   if(displayDiv!=null && displayDiv!='null'){
		document.location.href = displayDiv;
	}
	
	function submitCounterOffer()
	{
		
		$('#widgetErrorMessageID>ul').children().remove();
		
		var checkedDate = $('#rescheduleServiceDate').prop('checked');
		var checkedPrice = $('#increaseMaxPrice').prop('checked');
		//alert(checkedDate + ' ' + checkedPrice);
		
		var validationSuccess = true;
		if(checkedDate == false &&  checkedPrice == false)
		{
			//$('#widgetErrorMsg').html('Please Enter Amount for Total Labor');
			//SL-19728: Modifying for non funded buyer
			var nonFunded = '${isNonFunded}';
			if(nonFunded == 'false')
			{
				$('#widgetErrorMessageID>ul').append('<li>' + 'Please select Reschedule or Increase Max Price' + '</li>');
				validationSuccess = false;
			}
			if(nonFunded == 'true')
			{
				$('#widgetErrorMessageID>ul').append('<li>' + 'Please select Reschedule' + '</li>');
				validationSuccess = false;
			}
			
		}
		$("input[name='approvalItems.selectedSubServices1']:checked")
		
		//var reasons = $( "input[ @id = 'reason' ][@checked]" );
		
		if(checkedDate)
		{
			var rd1 = $('#reason_date_1').prop('checked');
			var rd2 = $('#reason_date_2').prop('checked');
			var rd3 = $('#reason_date_3').prop('checked');
			if(!rd1 && !rd2 && !rd3)
			{
				$('#widgetErrorMessageID>ul').append('<li>' + 'Choose at least one reason for rescheduling date' + '</li>');
				validationSuccess = false;			
			}
			//alert( rd1 + ' ' + rd2 + ' ' + rd3);
		}
		
		if(checkedPrice)
		{
			var rd4 = $('#reason_price_4').prop('checked');
			var rd5 = $('#reason_price_5').prop('checked');
			var rd6 = $('#reason_price_6').prop('checked');
			if(!rd4 && !rd5 && !rd6)
			{
				$('#widgetErrorMessageID>ul').append('<li>' + 'Choose at least one reason for price increase' + '</li>');
				validationSuccess = false;			
			}
			//alert( rd1 + ' ' + rd2 + ' ' + rd3);
		}
		
		if(checkedDate)
		{
		
			//alert($("#rescheduleSpecificDate").attr('checked'));
			if($("#rescheduleSpecificDate").prop('checked'))
			{
				if($('#specificDate').val() == '')
				{
					$('#widgetErrorMessageID>ul').append('<li>' + ' Reschedule Date is required' + '</li>');
					validationSuccess = false;						
				}
			}
			else
			{
				if($('#conditionalChangeDate1').val() == '')
				{
					$('#widgetErrorMessageID>ul').append('<li>' + 'Start date is required' + '</li>');
					validationSuccess = false;										
				}
				if($('#conditionalChangeDate2').val() == '')
				{
					$('#widgetErrorMessageID>ul').append('<li>' + 'End date is required' + '</li>');
					validationSuccess = false;										
				}
			}
		}
		
		if(checkedPrice)
		{
			var price = $('#conditionalSpendLimit').val();
			if(price == '' || price == 0 || price == 0.0)
			{
				$('#widgetErrorMessageID>ul').append('<li>' + 'Max price is required' + '</li>');
				validationSuccess = false;													
			}
			else
			{
				if(price <= ${spendLimit})
				{
					$('#widgetErrorMessageID>ul').append('<li>' + 'Counter Offer spend limit should be greater than existing price' + '</li>');
					validationSuccess = false;				
				}
			}		
		}
		
		var providerAdmin = $('#isProviderAdmin').val();
		var dispatchInd = $('#isDispatchInd').val();
		if(providerAdmin == true || providerAdmin == 'true' || dispatchInd == true || dispatch == 'true'){
			
			if($("input[name='checkBox_resource_id']:checked").size() == 0){
		  		$('#widgetErrorMessageID>ul').append('<li>' + 'Please choose at least one provider from your firm first' + '</li>');
				validationSuccess = false;	
			}
		}
		
		if($('#conditionalExpirationDate').val() == '')
		{
			$('#widgetErrorMessageID>ul').append('<li>' + 'Expiration date is required' + '</li>');
			validationSuccess = false;															
		}else{
			var now = new Date();
			var expDate = new Date($('#conditionalExpirationDate').val());
			if (expDate < now) {
	  			$('#widgetErrorMessageID>ul').append('<li>' + 'Please enter an expiration date in the future' + '</li>');
				validationSuccess = false;
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
		}
		if(!validationSuccess){
			$('#conditionalOfferForm').prop('onsubmit','return false;');
		}
		
			
		return validationSuccess;
	}
	
	

</script>




<c:choose>
	<c:when
		test="${requestScope.cameFromWorkflowMonitor == CAME_FROM_WORKFLOW_MONITOR }">
		<c:set var="hideIndlink" value="none" />
		<c:set var="hideIndNote" value="block" />

	</c:when>
	<c:otherwise>

		<c:set var="hideIndlink" value="block" />
		<c:set var="hideIndNote" value="none" />
	</c:otherwise>
</c:choose>


<div >

	<div>	
		<c:if test="${SecurityContext.slAdminInd || SecurityContext.buyerLoggedInd}">
			<c:if test="${requestScope.cameFromWorkflowMonitor == CAME_FROM_WORKFLOW_MONITOR }">
				<jsp:include page="/jsp/so_monitor/menu/AgentVisibilityWidget.jsp" />
			</c:if>
		</c:if>
	</div>
	
	<div>	
		<c:if test="${SecurityContext.slAdminInd}">
			<jsp:include page="/jsp/so_monitor/menu/widgetAdminMemberSODInfo.jsp" />
		</c:if>
	</div>
	
	
	<c:if test="${requestScope.cameFromWorkflowMonitor == CAME_FROM_WORKFLOW_MONITOR }">
			  <div class="quickLinksTabs">
			  <ul class="quickLinksTabsNav">
			  		
			        	<li><span id='queueNotes${randomNum}' onClick="showHideLinksNotes(this,'${randomNum}');hidePendingCancelWid(this,'${randomNum}','${THE_SERVICE_ORDER_STATUS_CODE }')" class='selected'>queue Notes</span></li>
			        
			        	<li><span id='quickLinks${randomNum}'   onClick="showHideLinksNotes(this,'${randomNum}');showPendingCancelWid(this,'${randomNum}','${THE_SERVICE_ORDER_STATUS_CODE }')"> quick Links</span></li>
			  </ul>
			  </div>
	
		  	<div id='panequeueNotes${randomNum}' style="display:${hideIndNote}" class="grayModuleContent">
				<div>
					<jsp:include page="queue_notes.jsp" />		
				</div>
			</div>
			
	 </c:if>
	
	<br/>
	
		<c:if test="${role == PROVIDER_ROLEID && THE_SERVICE_ORDER_STATUS_CODE == 165 && viewOrderPricing==true}">
		
	<div id='paneBuyer${randomNum}' style="background: #F2F5A9;display:${hideIndlink}">
 

<div id="heading " style="height:25px;background:#FFBF00;background-image:url(${staticContextPath}/images/reason_code_manager/pending_cancel_hdr.png);">
	<table width="100%">
		<tr>
			<td align="center"><SPAN style="padding-top: 4px">
				
							&nbsp;<img alt="Collapse" src="${staticContextPath}/images/grid/down-arrow.gif" onclick="hideWidget('${theTab}');" class="pendingCancelCollapse" style="padding-bottom: 2px;" id="pendingCancelCollapse${theTab}">
							&nbsp;<img alt="Expand"
								src="${staticContextPath}/images/grid/right-arrow.gif"
								style="display: none;padding-top: 4px;" onclick="showWidget('${theTab}');"
								class="pendingCancelExpand" id="pendingCancelExpand${theTab}">
				</SPAN>
			</td>
			<td>
				<b style="float:center;display: inline-table;">Pending Cancellation</b>
			</td>
			<td>
				<a id = "icon" style="float:right;padding-right: 1px;padding-bottom: 2px;padding-top:0px; display: inline-table;"  onmouseover="showHistory(event);" onmouseout="hideHistory();" class="priceHistoryIcon" href="javascript:void(0);"><img src="${staticContextPath}/images/reason_code_manager/dollar_icon.png"/></a>
			</td>
		</tr>
	</table>
</div>
       
		<div id="orderExpressMenu">
			<jsp:include page="panel_PendingCancel_Provider.jsp"></jsp:include>
		</div>
			
		</div>	
				<br>
		
	</c:if>
	
	
	<c:if test="${role == BUYER_ROLEID && THE_SERVICE_ORDER_STATUS_CODE == 165}">
	<div id='paneBuyer${randomNum}' style="background: #F2F5A9;display:${hideIndlink}">
	<div id="heading " style="height:25px;background:#FFBF00;background-image:url(${staticContextPath}/images/reason_code_manager/pending_cancel_hdr.png);">
		<table width="100%">
			<tr>
				<td align="center">
								&nbsp;<img alt="Collapse" src="${staticContextPath}/images/grid/down-arrow.gif" onclick="hideWidget('${theTab}');" class="pendingCancelCollapse" style="padding-bottom: 2px;" id="pendingCancelCollapse${theTab}">
								&nbsp;<img alt="Expand"
									src="${staticContextPath}/images/grid/right-arrow.gif"
									style="display: none;padding-top: 4px;" onclick="showWidget('${theTab}');"
									class="pendingCancelExpand" id="pendingCancelExpand${theTab}">				
				</td>
				<td>
					<b style="float:center;display: inline-table;">Pending Cancellation</b>
				</td>
				<td>
					<a id = "icon" style="float:right;padding-right: 1px;padding-bottom: 2px;padding-top:0px; display: inline-table;"  onmouseover="showHistory(event);" onmouseout="hideHistory();" class="priceHistoryIcon" href="javascript:void(0);"><img src="${staticContextPath}/images/reason_code_manager/dollar_icon.png"/></a>
				</td>
			</tr>
		</table>
	</div>
		<div id="PendingCancel_Buyer">
			<jsp:include page="panel_PendingCancel_Buyer.jsp"></jsp:include>
		</div>
		
		</div>
				<br>	
		
	</c:if>
			
	

	<%-- Start of Order Express Menu--%>
	
	<div id='panequickLinks${randomNum}' style="display:${hideIndlink};">
	 	<div id="orderMenu" class="grayModuleHdr" style="background: #58585A url(${staticContextPath}/images/titleBarBg.gif);">
	 	<p  class="menugroup_head" onclick="expandWidget('${staticContextPath}','orderExpress');">&nbsp;<img class="orderExpress" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;
				<c:choose>
				<c:when test="${empty action}">
					<fmt:message bundle="${serviceliveCopyBundle}" key="widget.title.order.quick.links"/>
				</c:when>
				<c:otherwise>
					Order Express Menu 
				</c:otherwise>
				</c:choose>
		</p>
		</div>
		<div class="orderExpressMenu grayModuleContent" id="orderExpressMenu">
			<jsp:include page="panel_order_express_menu.jsp"></jsp:include>
	
		<br/>
		<s:include value="panel_ql_buttons.jsp" />		
	</div>
</div>	
	
	<%-- End of Order Express Menu --%>
<%-- Start of Pricing Summary --%>
<c:if test="${buyerID=='3000'}">
	<c:choose>
	<c:when test="${THE_SERVICE_ORDER_STATUS_CODE == 160 && closePayTab==true}"> 

	<div 	id='pricingsummary'  style="display:block;width:255px;" class="pricewidget1">
<script type="javascript" >
       var imgId='orderExpress';
	var ob = $("."+imgId).attr("src");
	var menu ="";
	menu = imgId+ "Menu";


	var path='${staticContextPath}';
	if(ob.indexOf('arrowDown')!=-1){
	$("."+imgId).attr("src",path+"/images/widgets/arrowRight.gif");		
	$("."+menu).slideToggle("slow");
	}
 </script>
	<div class="grayModuleHdr" id='pricingsummary'  style="background: #58585A url(${staticContextPath}/images/titleBarBg.gif);"  >
	<p  class="menugroup_head" onclick="expandWidget('${staticContextPath}','pricingSummary')">&nbsp;<img class="pricingSummary" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;
    						Price Summary </p>
		</div>
			<div class="pricingSummaryMenu grayModuleContent" style="padding:0px">
			<jsp:include page="panel_order_Completion_ClosePay_Pricing_Summary.jsp"></jsp:include>
		</div>		
		<br/>
	</div>
</c:when>
<c:otherwise>

	<div id="pricingsummary"  class="pricewidget fixed"  style="display:none">
	<div class="grayModuleHdr"   style="background: #58585A url(${staticContextPath}/images/titleBarBg.gif);"  >
	<p  class="menugroup_head" onclick="expandWidget('${staticContextPath}','pricingSummary')">&nbsp;<img class="pricingSummary" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;
    						Price Summary</p>
		</div>
			<div class="pricingSummaryMenu grayModuleContent" style="padding:0px">
			<jsp:include page="panel_order_Pricing_Summary.jsp"></jsp:include>
		</div>		
		<br/>
	</div>
	</c:otherwise>
	</c:choose>
</c:if>
	<%-- End of Pricing Summary--%>
	
<div id="omCounterOffer"></div>		
<c:if test="${role == PROVIDER_ROLEID && THE_SERVICE_ORDER_STATUS_CODE == 110 && orderType != 2  && !(carSO == true && (buyerID=='1000'||buyerID=='3000'))&& !(isCounterOfferOff==true)}">
		<c:if test="${originalSoId == null || originalSoId==''|| recallProvider == false}">
		   <s:include value="panel_counter_offer.jsp" />
		</c:if>
		
</c:if>

	
	<br/>
	
	<s:include value="panel_incident_tracker.jsp" />	
	
</div>
</html>