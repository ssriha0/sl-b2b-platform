<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<html style="">
	<head>
		<script>
    
  // Script to position the pricing summary widget
   $(window).scroll(function (event) {
    var pricingSummaryObj = jQuery('#ui-tabs-9 #pricingsummary');
    var orderExpressMenuObj = jQuery('#ui-tabs-9 #orderExpressMenu');
	if(pricingSummaryObj.length > 0){
		if(pricingSummaryObj.is(':visible') || pricingSummaryObj.css('display')=='block') {
			// Define the upper and lower limit
			var bottomLimit = 2530;
			if($('#submitDiv').length > 0){
				 var position = $("#submitDiv").position();		
				 bottomLimit = 	position.top;
			}
			var topLimit = 0;
			
		    // what the y position of the scroll is
		    var y = $(this).scrollTop();
		    var scrollBottom = $(this).scrollTop() + $(this).height();
		    
		    var ob = $(".orderExpress").attr("src");
		    var top = orderExpressMenuObj.offset().top;
		    var heightOfOEMenu = top + orderExpressMenuObj.height();
		    	
			// Collapsed
		    if(ob.indexOf('arrowRight')!=-1){		    
		    // If collapsed, top level is handled by margin-top. Only validate the bottom
		  		if(scrollBottom>bottomLimit){
		    			pricingSummaryObj.removeClass('fixedWithlessMarginTop');
		    			pricingSummaryObj.removeClass('fixed');	
		    			pricingSummaryObj.removeClass('relative');
		    			pricingSummaryObj.addClass('fixedwithIndex');
	    		}else if((scrollBottom<bottomLimit) && (y>300)){
		    			pricingSummaryObj.removeClass('relative');
		    			pricingSummaryObj.removeClass('fixed');	
		    			pricingSummaryObj.addClass('fixedWithlessMarginTop');
		    			pricingSummaryObj.removeClass('fixedwithIndex');
	    		}else{
		    			pricingSummaryObj.removeClass('fixedWithlessMarginTop');
		    			pricingSummaryObj.removeClass('fixedwithIndex');
		    			pricingSummaryObj.removeClass('relative');
		    			pricingSummaryObj.addClass('fixed');
	    		}
		    }
	
			// Expanded
			if(ob.indexOf('arrowDown')!=-1){				
			// if expanded, the limits are 934 and 2500			
		    	if(y<heightOfOEMenu){
		    			pricingSummaryObj.removeClass('fixedWithlessMarginTop');
		    			pricingSummaryObj.removeClass('fixed');
		    			pricingSummaryObj.addClass('relative');
		    			pricingSummaryObj.removeClass('fixedwithIndex');
		    	}else if((scrollBottom<bottomLimit) && (y > 935)){
		    			pricingSummaryObj.removeClass('relative');
		    			pricingSummaryObj.removeClass('fixed');	
		    			pricingSummaryObj.addClass('fixedWithlessMarginTop');
		    			pricingSummaryObj.removeClass('fixedwithIndex');
		    	} else if(scrollBottom>bottomLimit){
		    			pricingSummaryObj.removeClass('fixedWithlessMarginTop');
		    			pricingSummaryObj.removeClass('fixed');
	    				pricingSummaryObj.removeClass('relative');	    			
		    			pricingSummaryObj.addClass('fixedwithIndex');
		   		 }
			}
		}
	}
	});
    
    //Setting for the reason codes
   	$("#claim1").click(function(){
      		$("#nonMaxPriceForLaborCheck").val("false");
    		}); 
		  $("#claim2").click(function(){
		      $("#nonMaxPriceForLaborCheck").val("true");
		    });
		  $("#selectReason").change(function(){   
		     if ($('#claim2').attr('checked')){
		        $("#selectReasonForLabor").val($("#selectReason option:selected").val());
		        }
		   });
		  $("#box").blur(function(){
		   var otherReasonText = $("#box").val();
		   if(otherReasonText != "Explain the reason (50 chars)" && otherReasonText!=""){
		   $("#otherReasonText").val(otherReasonText);
		   }
		   }); 
// This function sets the value of tempId to SetID
function setValueToId(tempId,setId){
var setIdVal ='#'+setId;
var tempVal=$(tempId).val();
$(setIdVal).val(tempVal)
} 
  
  
function calculatPricingSummary(){
	 
	var addOnPrice=$(".addOnPricing").text().replace("$", "");
	var laborNetPrice = $(".laborPricing").text().replace("$", "");
	var materialNetPrice=$(".partsPricing").text().replace("$", "");
	var partsNetPrice=$(".partsInPricing").text().replace("$", "");
	
	if(laborNetPrice ==''){
		laborNetPrice=0.00; 
	}
	if(materialNetPrice==''){
		materialNetPrice=0.00;
	}
	if(partsNetPrice==''){
		partsNetPrice=0.00;
	}
	if(addOnPrice==''){
		addOnPrice=0.00;
	}
	var laborNetPrice1 =parseFloat(laborNetPrice);
	var materialNetPrice1=parseFloat(materialNetPrice);
	var partsNetPrice1=parseFloat(partsNetPrice);
	var addOnPrice1=parseFloat(addOnPrice);
	var netTotal =laborNetPrice1+materialNetPrice1+partsNetPrice1+addOnPrice1;
	
	var net=addOnObject.fmtMoney(netTotal);
	jQuery(".totalServiceOrderPrice").html("$"+formatAsMoney(net));
	jQuery(".serviceLiveFee").html("- $"+formatAsMoney(net*0.1));
	jQuery(".netPaymentFee").html("$"+formatAsMoney(net-net*0.1));
}

      
 function hideElements()
    {
  	document.getElementById("dropdown").style.display="none";
   	document.getElementById("finalPrice").style.display="none";
   	document.getElementById("box").style.display="none";
	var lPrice = '${soInitialMaxLabor}';
	
	//Setting hidden variable values to clear the the value that was set on claiming diff maxPrice. 
	document.getElementById("finalLaborPrice").value = lPrice;

	jQuery(".laborPricing").html("$"+formatAsMoney(lPrice));
	jQuery(".laborEstPrice").html("$"+formatAsMoney(lPrice -lPrice*0.1));
	calculatPricingSummary();
	$('#selectReason').val("Select Reason");
	$('#soFinalLabourPrice').val("");
	$('#box').val("Explain the reason (50 chars)");
 }
      
      function showElements()
      {
      document.getElementById("finalPrice").style.display="inline";
      document.getElementById("dropdown").style.display="block";
      var lPrice = '${soInitialMaxLabor}'; 
      $("#soFinalLabourPrice").val("");
	  jQuery(".laborPricing").html("$"+formatAsMoney(lPrice));
	  jQuery(".laborEstPrice").html("$"+formatAsMoney(lPrice -lPrice*0.1));
	  calculatPricingSummary();

}


function expandCheckedOrderExpressMenu(path,timerId){
	// Hide the order express menu
	var imgId='orderExpress';
	var ob = $("."+imgId).attr("src");
	var menu ="";
	menu = imgId+ "Menu";
	$("."+imgId).attr("src",path+"/images/widgets/arrowRight.gif");
	$("."+menu).hide();
	
	var price = '${soInitialMaxLabor}';
	if($("#nonMaxPriceForLaborCheck").val()=="true"){
	  price = '${finalLaborPrice}'; 
	}
 	
 	if(price>=0){ 	
		jQuery("#ui-tabs-9 #pricingsummary .laborPricing").html("$"+formatAsMoney(price));
		jQuery("#ui-tabs-9 #pricingsummary .laborEstPrice").html("$"+formatAsMoney(price -price *0.1));
	}
	var mPrice = '${partSpLimit}';
	if($("#nonMaxPriceForPartCheck").val()=="true"){
		mPrice = '${finalPartPrice}';
	}
 	if(mPrice > 0){
	 	document.getElementById("partsCheck").style.display='';
	 	jQuery("#ui-tabs-9 #pricingsummary .partsPricing").html("$"+formatAsMoney(mPrice));
	 	jQuery("#ui-tabs-9 #pricingsummary .parstEstPricing").html("$"+formatAsMoney(mPrice - mPrice * 0.1));
	 	$('#ui-tabs-9 #pricingsummary .pc').show();
	}
	calculatPricingSummary();
	$('#ui-tabs-9 .pricewidget').show();$('#ui-tabs-9 #pricingsummary .laborPrice').show();
	// Clear the time interval
	if(timerId != null){
	  clearInterval(timerId);
	}
}
function amountRound(obj) 
      {
	// var finalPartPrice = document.getElementById("finalPartPrice").value;
	// document.getElementById("finalPartPrice").value = addOnObject.fmtMoney(finalPartPrice);
	
	 var    inputFinalPrice = document.getElementById("soFinalLabourPrice").value;
            inputFinalPrice = formatAsMoney(inputFinalPrice);
       var  negativeCheckVal=addOnObject.fmtMoney(inputFinalPrice); 

     if(inputFinalPrice !="" && !isNaN(inputFinalPrice ) && (negativeCheckVal!="" || inputFinalPrice == 0)){
     //If new final price is entered, update price sumary panel
     	jQuery(".laborPricing").html("$"+formatAsMoney(inputFinalPrice ));
     	jQuery(".laborEstPrice").html("$"+formatAsMoney(inputFinalPrice -inputFinalPrice *0.1));
     } 
     else{
     //if final price field is left blank, reset the pricing summary panel with initial max price values.
     	var soInitialMaxLabor = document.getElementById("soInitialMaxLabor").value;
     	jQuery(".laborPricing").html("$"+formatAsMoney(soInitialMaxLabor));
     	jQuery(".laborEstPrice").html("$"+formatAsMoney(soInitialMaxLabor -soInitialMaxLabor *0.1));     	
     }
     
     calculatPricingSummary();
    
  	}
  function displayBox(){
		if(document.getElementById("selectReason").selectedIndex==3 || document.getElementById("selectReason").val()=='Other'){
		document.getElementById("box").style.display="block";
		}
	else{
		document.getElementById("box").style.display="none";
		$('#box').val("Explain the reason (50 chars)");
		}
	}
	function clickClear(thisfield, defaulttext) {
     	if (thisfield.value == defaulttext) {
      	thisfield.value = "";
      	}
	}
	function clickRecall(thisfield, defaulttext) {
		if (thisfield.value == "") {
		thisfield.value = defaulttext;
	}
	}
	function countAreaChars(areaName,limit, evnt){
		if (areaName.value.length>limit) {
			areaName.value=areaName.value.substring(0,limit);
			alert("The field limit is " + limit + " characters.");
		
			//Stop all further events generated (Event Bubble) for the characters user has already typed in .
			//For IE
			if (!evnt) var evnt = window.event;
			evnt.cancelBubble = true;
			//For FireFox
			if (evnt.stopPropagation) evnt.stopPropagation();
		}
		}
    
   


function loadPriceSummary(){
	var r = $('input:radio[name=output]');
	
    if(r.is(':checked') == true){
    // Created the interval to look for the load of order express menu    
		var refreshIntervalId = setInterval(function () {
  		if (jQuery("#ui-tabs-9 .orderExpressMenu").length > 0) {
    			expandCheckedOrderExpressMenu('${staticContextPath}',refreshIntervalId);
  		}
		}, 100);
	}
}

$('#claim1').click(function() {
	if ($('#claim1').attr('checked')) {
        $('.pricewidget').show();$('.laborPrice').show();
	
    } 
});
$('#claim2').click(function() {
     
    if ($('#claim2').attr('checked')) {
        $('.pricewidget').show();$('.laborPrice').show();
	
    } 
});
</script>
	</head>
	<body>
		<div
			style="background-color: silver; width: 10%; padding-left: 15px; color: white; font-weight: bold;">
			Labor
			<span style="color: red">*</span>
		</div>
		<table>
			<c:choose>
				<c:when	test="${0.0 != soInitialMaxLabor && finalLaborPrice < soInitialMaxLabor}">
					<tr id="form-id">
						<td>
							<input name="output" id="claim1" onclick="hideElements();"
								src="${staticContextPath}/images/widgets/arrowDown.gif"
								value="confirm" type="radio" checked="false" on>
							I am claiming the maximum price :
							<b>$ <fmt:formatNumber value="${soInitialMaxLabor}"
									type="NUMBER" minFractionDigits="2" maxFractionDigits="2" /> </b>
						</td>
						
						<td style="padding-left: 20px;">
							<c:if test="${0.0 != soInitialMaxLabor}">
								<c:if test="${finalLaborPrice < soInitialMaxLabor}">
									<input name="output" onclick="showElements();"
										src="${staticContextPath}/images/widgets/arrowDown.gif"
										id="claim2" value="dontconfirm" type="radio" checked="true">
                                     I am claiming a different amount than the maximum price
                        		</c:if>
							</c:if>
						</td>
					</tr>
					<tr align="right">
						<td>
							&nbsp;
						</td>
						<td id="dropdown"
							style="display: block; padding-top: 10px; padding-left: 20px;"
							align="left">
							<b>Select reason</b>
							<span style="color: red;"display:none;"> * </span>
							<select style="width: 145px; margin-left: 80px;"
								name="Select Reason*" onchange="displayBox()" id="selectReason">
								<option value="Select Reason">${selectReasonForLabor}</option>
								<option value="Repair is a recall">	Repair is a recall</option>
								<option value="Agreed to a lower rate">Agreed to a lower rate</option>
								<option value="Other">Other</option>
							</select>
							<br>
						</td>
					</tr>
					
					<tr>
						<td>
							&nbsp;
						</td>
						
			 <td style="padding-left:20px;" >
			 <c:if test="${selectReasonForLabor == 'Other'}">
			
  			 <textarea id="box" name="selectOtherReason" rows="1" cols="16" style= "display:block; width: 146px; height: 42px;margin-top:10px;margin-left:172px;"    
				onblur="clickRecall(this,'Explain the reason (50 chars)')" 
				onfocus="clickClear(this,'Explain the reason (50 chars)')" 
				onkeyup="countAreaChars(this.form.box, 50, event);" 
					onkeydown="countAreaChars(this.form.box, 50, event);">${OtherReasonText}</textarea> <br>
 			</c:if>
			<div id="finalPrice" style="display: block;">
				<b>Enter <span id="finalLaborPriceGlossary" style="cursor:pointer" class="glossaryItem">Final Price(Labor)</span></b>
				<span style="color: red"> * </span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			<div style="padding-left:0px;display: inline;">$     
				<s:textfield cssStyle="width: 45px; text-align:right; font-size: 10px; font-family: verdana;"
					cssClass="text" name="finalLaborPrice" id="soFinalLabourPrice" 
						onchange="amountRound(this);amountRoundoff(this);" value="%{finalLaborPriceNew}"/> </div>
			</div> 


						<s:hidden name="soInitialMaxLabor" id="soInitialMaxLabor"
							value="%{soInitialMaxLabor}"></s:hidden>
						<s:hidden name="selectReasonForLabor" id="selectReasonForLabor"></s:hidden>
						<s:hidden name="otherReasonText" id="otherReasonText"></s:hidden>
						<s:hidden name="nonMaxPriceForLaborCheck"
							id="nonMaxPriceForLaborCheck"></s:hidden>

						</td>

					</tr>
			</c:when>
				<c:otherwise>
					<tr id="form-id">
						<td>
							<input name="output" id="claim1" onclick="hideElements();"
								src="${staticContextPath}/images/widgets/arrowDown.gif"
								value="confirm" type="radio" checked="true">
							I am claiming the maximum price :
							<b>$ <fmt:formatNumber value="${soInitialMaxLabor}"
									type="NUMBER" minFractionDigits="2" maxFractionDigits="2" /> </b>
						</td>
						<td style="padding-left: 20px;">
							<c:if test="${0.0 != soInitialMaxLabor}">
								<input name="output" onclick="showElements();"
									src="${staticContextPath}/images/widgets/arrowDown.gif"
									id="claim2" value="dontconfirm" type="radio">
                                     I am claiming a different amount than the maximum price
                                </c:if>
						</td>
					</tr>
					<tr align="right">
						<td>
							&nbsp;
						</td>
						<td id="dropdown"
							style="display: none; padding-top: 10px; padding-left: 20px;"
							align="left">

							<b>Select reason</b>
							<span style="color: red;"display:none;"> * </span>
							<select style="width: 145px; margin-left: 80px;"
								name="Select Reason*" onchange="displayBox()" id="selectReason">
								<option value="Select Reason">Select Reason	</option>
								<option value="Repair is a recall">	Repair is a recall</option>
								<option value="Agreed to a lower rate">Agreed to a lower rate</option>
								<option value="Other">Other</option>
							</select>
							<br>
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
						<td style="padding-left: 20px;">
							<textarea id="box" name="selectOtherReason" rows="1" cols="16"
								style="display: none; width: 146px; height: 42px; margin- top: 10px; margin-left: 172px;"
								onblur="clickRecall(this,'Explain the reason (50 chars)')"
								onfocus="clickClear(this,'Explain the reason (50 chars)')"
								onkeyup="countAreaChars(this.form.box, 50, event);"
								onkeydown="countAreaChars(this.form.box, 50, event);">Explain the reason (50 chars)</textarea>
							<br>

							<div id="finalPrice" style="display: none;">
								<b>Enter <span id="finalLaborPriceGlossary"
									style="cursor: pointer" class="glossaryItem">Final
										Price(Labor) </span> </b>
								<span style="color: red"> * </span>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<div style="padding-left: 0px; display: inline;">
									$
									<s:textfield
										cssStyle="width: 45px; text-align:right; font-size: 10px; font-family: verdana;"
										cssClass="text" name="finalLaborPrice" id="soFinalLabourPrice"
										onchange="amountRound(this);amountRoundoff(this);" />
								</div>
							</div>


							<s:hidden name="soInitialMaxLabor" id="soInitialMaxLabor"
								value="%{soInitialMaxLabor}"></s:hidden>
							<s:hidden name="selectReasonForLabor" id="selectReasonForLabor"></s:hidden>
							<s:hidden name="otherReasonText" id="otherReasonText"></s:hidden>
							<s:hidden name="nonMaxPriceForLaborCheck"
								id="nonMaxPriceForLaborCheck"></s:hidden>

						</td>

					</tr>
				</c:otherwise>
			</c:choose>
		</table>
		<script>
loadPriceSummary(); 
</script>
	</body>
</html>
