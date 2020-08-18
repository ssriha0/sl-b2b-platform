<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>


<html style="">
<head>
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dashboard.css" />
<style type="text/css">

td.buyer_parts_table {     
		padding-top: 4px;
		padding-bottom:4px;
	}
</style>


<script>

var $jQ = jQuery.noConflict();
// This function is to check if values are already set in reasons
function validateReasonCodes(){
// If the non max price already exists set the values in the page
	if($jQ("#nonMaxPriceForPartCheck").val()=="true"){
  		$jQ("#dontConfirmMaxPrice").attr('checked','true');
  	$jQ("#chooseReason").show();
  	$jQ("#finalPriceForParts").show();
  	 var pPrice = '${finalPartPrice}';
  	 if(pPrice==''){
  	   pPrice=0.00;
  	 }
  	 $jQ("#soFinalPartPrice").val(formatAsMoney(pPrice));
  		 $jQ('#enterOtherReason').val($jQ("#selectReasonForParts").val());
  			if($jQ("#selectReasonForParts").val()=="Other"){
  			    $jQ("#enterReasonHere").show();
  			    if($jQ("#otherReasonTextPart").val()!=""){
  			    $jQ("#enterReasonHere").val($jQ("#otherReasonTextPart").val());
  			    }
  			}
  		}
  		if($jQ("#nonMaxPriceForLaborCheck").val()=="true"){
  		$jQ("#claim2").attr('checked','true');
       $jQ("#finalPrice").show();
       $jQ("#dropdown").show();
       var lPrice = '${finalLaborPrice}'; 
        if(lPrice==''){
  	   		lPrice=0.00;
  	 	}
       $jQ("#soFinalLabourPrice").val(formatAsMoney(lPrice));
  		 $jQ('#selectReason').val($jQ("#selectReasonForLabor").val());
  			if($jQ("#selectReasonForLabor").val()=="Other"){
  			    $jQ("#box").show();
  			    if($jQ("#otherReasonText").val()!=""){
  			    $jQ("#box").val($jQ("#otherReasonText").val());
  			    }
  			}
  	}

}
function validatePrices(e){
// Changes for tab not working for ie
var unicode=e.keyCode? e.keyCode : e.charCode;
 if(unicode!=9){
	var message;
	var message1;
	$jQ("#errorRetailPrice").hide();
	$jQ("#errorUnitCost").hide();
	var unitCost = $jQ("#unitCost").val();
	unitCost=trim(unitCost);
	$jQ("#unitCost").val(unitCost);
	if(unitCost>999){
		message = "Unit Price should not exceed $999.00";
		$jQ("#errorUnitCost").html(message);
		$jQ("#errorUnitCost").show();
	}
	var retailPrice = $jQ("#retailPrice").val();
	retailPrice=trim(retailPrice);
	$jQ("#retailPrice").val(retailPrice);
	if(retailPrice>999){
		message1 = "Retail Price should not exceed $999.00";
		$jQ("#errorRetailPrice").html(message1);
		$jQ("#errorRetailPrice").show();
	}
 }

}

function amountRoundoffUnitPrice(){
	var unitCost = $jQ("#unitCost").val();
	 if(unitCost == 0){
	   var zero = "0.00";
	   $jQ("#unitCost").val(zero);
	 }else{
	  $jQ("#unitCost").val(addOnObject.fmtMoney(unitCost));
	 }
}

function amountRoundoffRetailPrice(){
	 var retailPrice = $jQ("#retailPrice").val();
	 if(retailPrice == 0){
	   var zero = "0.00";
	   $jQ("#retailPrice").val(zero);
	 }else{
	  $jQ("#retailPrice").val(addOnObject.fmtMoney(retailPrice));
	 }
}


function materialShow()
{
var mPrice = '${partSpLimit}';
 if(mPrice > 0)
 {
	 var matPrice=addOnObject.fmtMoney(mPrice);
	 var materialPrice=matPrice -(matPrice *0.1);
	 var materialPricing=addOnObject.fmtMoney(materialPrice);
	 $jQ('.pricewidget').show();$jQ('.pc').show();
	 $jQ(".partsPricing").html("$"+matPrice);
	 $jQ(".parstEstPricing").html("$"+materialPricing);
	calculatPricingSummary();
	 $jQ('.pricewidget').show();$jQ('.pc').show();
	

	 }
}




var panel1Def = "&ldquo;<strong>Unit Cost</strong>&rdquo; is the actual price provider paid to source and procure part.";
var panel2Def = "&ldquo;<strong>Retail Price</strong>&rdquo; is the actual retail price sold through Sears Parts Direct.";
var panel3Def = "&ldquo;<strong>Est. Net Provider Payment</strong>&rdquo; is the remaining amount after fees** have been applied.</br><Strong><i>**Fees may change without notice.</i></Strong>";
var panel4Def = "Note: The total amount must be equal to or less than the price shown. Ensure all additional services are added in the form below and money collected from the customer.";

$jQ(".glossaryItem").mouseover(function(e){
    	$jQ("#explainers").css("position","absolute");
   	    if($jQ(this).attr("id") == "unitCostHover") {
   	     var position = $jQ("#unitCostHover").offset();
    	 $jQ("#explainers").html(panel1Def);
    	 $jQ("#explainers").css("top",position.top-383);
    	 $jQ("#explainers").css("left",100);
    	}
    	
    	if($jQ(this).attr("id") == "retailPriceHover") {
    	 var position = $jQ("#retailPriceHover").offset();
     	 $jQ("#explainers").html(panel2Def);
    	 $jQ("#explainers").css("top",position.top-383);
    	 $jQ("#explainers").css("left",100);
    	}
    	
    	if($jQ(this).attr("id") == "EstNtProvPymtHoverHover") {
    	 var position = $jQ("#EstNtProvPymtHoverHover").offset();
    	 $jQ("#explainers").html(panel3Def);
    	 $jQ("#explainers").css("top",position.top-435);
    	 $jQ("#explainers").css("left",position.left-224);
    	}
    	
    	if($jQ(this).attr("id") == "RetailPriceHover") {
       	 var position = $jQ("#RetailPriceHover").offset();
       	 $jQ("#explainers").html(panel2Def);
       	 $jQ("#explainers").css("top",position.top-435);
       	 $jQ("#explainers").css("left",position.left-224);
       	}
    	
    	if($jQ(this).attr("id") == "finalPriceGlossary") {
    	 var position = $jQ("#finalPriceGlossary").offset();
    	 $jQ("#explainers").html(panel4Def);
    	 $jQ("#explainers").css("top",position.top-440);
    	 $jQ("#explainers").css("left",position.left-100);
    	}
    	if($jQ(this).attr("id") == "finalLaborPriceGlossary") {
    	 var position = $jQ("#finalLaborPriceGlossary").offset();
    	 $jQ("#explainers").html(panel4Def);
    	 $jQ("#explainers").css("top",position.top-440);
    	 $jQ("#explainers").css("left",position.left-100);
    	}
    	
    	$jQ("#explainers").show();   
	}); 
  	$jQ(".glossaryItem").mouseout(function(e){
	  $jQ("#explainers").hide();
	}); 
  	
  	 	
  	
  	
// The load is for the displaying of table if already the invoiceparts are present in session 
// and to set the pricing summary widget


function setInputVariables(presentNetPayment,presentFinalPayment,qtyVar){

	$jQ(qtyVar).parent().siblings(".variableInputs").children(".netPaymentInput").val(presentNetPayment);
	$jQ(qtyVar).parent().siblings(".variableInputs").children(".finalPriceInput").val(presentFinalPayment);
	$jQ(qtyVar).parent().siblings(".variableInputs").children(".qtyInput").val($jQ(qtyVar).val());
}
// Java script Function to validate if total Payment is greater than maxValueForInvoicePartsHSR 
function validate(totPayment,qtyVar)
{
	
	var maxValueForInvoicePartsHSR = ${maxValueForInvoicePartsHSR};
	
  if(totPayment>maxValueForInvoicePartsHSR){
  	$jQ(qtyVar).val($jQ(qtyVar).parent().siblings(".variableInputs").children(".qtyInput").val());
	$jQ("#errorDiv").html("The quantity of this part cannot be changed as the total price for parts has exceeded $"+maxValueForInvoicePartsHSR+". Please contact the buyer for support.<br/>");
	$jQ("#errorDiv").css("display","block");
	window.location.hash = '#errorDiv';
		return false;
 	}else{
 		return true;
 	}
}
// Function to calculate the final parts price and net parts price on change of 
// Quantity
function reSet(qtyVar){
	// Getting Conversion Rate for quantity
	var netPaymentConvRate=$jQ(qtyVar).siblings(".netPaymentConvRate").html();
	var finalPaymentConvRate=$jQ(qtyVar).siblings(".finalPaymentConvRate").html();

	// Calculating To be changed payment
	var presentNetPayment=qtyVar.value*netPaymentConvRate;
	var presentFinalPayment=qtyVar.value*finalPaymentConvRate;

	// Taking present values
	var previousNetPayment=$jQ(qtyVar).parent().siblings(".currencySymbol").children(".netPayment").html();
	var previousFinalPayment=$jQ(qtyVar).parent().siblings(".finalPayment").html();

	// Taking total Payment values
	var netTotalPayment=parseFloat($jQ("#totalPayment").html());
	var finalTotalPaymentRate=parseFloat($jQ("#finalTotalPartPrice").html());

	// Setting final Payment values
	if(presentFinalPayment>previousFinalPayment){
 		subNetPayment=Math.round((previousFinalPayment-presentFinalPayment)*100)/100;
 		totPayment=Math.round((finalTotalPaymentRate-subNetPayment)*100)/100;
 		
 		// Validating the total Final Payment exceeds 1500
 		if(!validate(totPayment,qtyVar)){ 
 			return;
 		}
 		if(totPayment==''){
	 		totPayment = 0.00;
 		}
		$jQ("#finalTotalPartPrice").html(addOnObject.fmtMoney(totPayment));		
  		$jQ(".partsInPricing").html("$"+addOnObject.fmtMoney(totPayment));
  		
	}else{
		addNetPayment=Math.round((presentFinalPayment-previousFinalPayment)*100)/100;
 		totPayment=Math.round((finalTotalPaymentRate+addNetPayment)*100)/100;
 		// Validating the total Final Payment exceeds 1500
 		if(totPayment==''){
	 		totPayment = 0.00;
	 	}
 		if(!validate(totPayment,qtyVar)){
 			return;
 		}
		$jQ("#finalTotalPartPrice").html(addOnObject.fmtMoney(totPayment));
   		$jQ(".partsInPricing").html("$"+addOnObject.fmtMoney(totPayment));
	}

//Setting Net Total Payment
if(previousNetPayment>presentNetPayment){
 	subNetPayment=Math.round((previousNetPayment-presentNetPayment)*100)/100;
 	totPayment=Math.round((netTotalPayment-subNetPayment)*100)/100; 
 	if(totPayment==''){
 		totPayment = 0.00;
 	}	
	$jQ("#totalPayment").html(formatAsMoney(totPayment));
	$jQ(".partsInEstPricing").html("$"+formatAsMoney(totPayment));
}else{
	addNetPayment=Math.round((presentNetPayment-previousNetPayment)*100)/100;
 	totPayment=Math.round((netTotalPayment+addNetPayment)*100)/100;
 	if(totPayment==''){
 		totPayment = 0.00;
 	}
	$jQ("#totalPayment").html(formatAsMoney(totPayment));
	$jQ(".partsInEstPricing").html("$"+formatAsMoney(totPayment));
}
	// Setting present values of payment to table
	$jQ(qtyVar).parent().siblings(".currencySymbol").children(".netPayment").html(formatAsMoney(presentNetPayment));
	$jQ(qtyVar).parent().siblings(".finalPayment").html(addOnObject.fmtMoney(presentFinalPayment));
	calculatPricingSummary();
	setInputVariables(presentNetPayment,presentFinalPayment,qtyVar);
}

function expandInPartsOnOrderExpressMenu(path)
{
var imgId='orderExpress';
var ob = $jQ("."+imgId).attr("src");
	var menu ="";
	menu = imgId+ "Menu";
	if(ob.indexOf('arrowDown')!=-1){
		$jQ("."+imgId).attr("src",path+"/images/widgets/arrowRight.gif");
		$jQ("."+menu).slideToggle("slow");
	}
}

function calculatPricingSummary(){
	
	var addOnPrice=$jQ(".addOnPricing").text().replace("$", "");
	var laborNetPrice = $jQ(".laborPricing").text().replace("$", "");
	var materialNetPrice= $jQ(".partsPricing").text().replace("$", "");
	var partsNetPrice=$jQ(".partsInPricing").text().replace("$", "");
	
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
	var netTotal = parseFloat(laborNetPrice) + parseFloat(materialNetPrice) +
		parseFloat(partsNetPrice) + parseFloat(addOnPrice);
	var net=formatAsMoney(netTotal);
	if(net==''){	
		net=0.00;
	}
     var serviceLivePayement=formatAsMoney(net*0.9);
	 serviceLiveFee=formatAsMoney(net-serviceLivePayement);	
	 $jQ(".totalServiceOrderPrice").html("$"+formatAsMoney(net));
	 $jQ(".serviceLiveFee").html("- $"+serviceLiveFee);
	 $jQ(".netPaymentFee").html("$"+serviceLivePayement);
	//jQuery(".totalServiceOrderPrice").html("$"+formatAsMoney(net));
	//jQuery(".serviceLiveFee").html("- $"+formatAsMoney(net*0.1));
	//jQuery(".netPaymentFee").html("$"+formatAsMoney(net-net*0.1));
}

	
	 
	// $jQ("#partNumber").keypress(function(e) {
  		//if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57) && (e.which<65 || e.which>90) && (e.which<97 || e.which>122)){
	   		//return false;
	 	//}
	 //});
	
	
	 /* jQuery("#partNumber").live("keypress",function (e){	
	 if( e.which == 32 ){
   			return false;
 			}
 		}); 
 	jQuery("#description").live("keypress",function (e){
	
		  //if the letter is not digit or Alphanumeric then display error and don't type anything
		  if( e.which!=8 && e.which!=0 && e.which!=32 && e.which!=44  && (e.which<48 || e.which>57) && (e.which<65 || e.which>90) 
		   && (e.which<97 || e.which>122) ){
   			return false;
 			}
 		});*/
 		
    function showHide() {
	if (document.getElementById("installedparts").checked == true){
	
		document.getElementById("partsforhsr").style.display="block";
	}else{
	    clearThePartsFields();
	    
		document.getElementById("partsforhsr").style.display="none";
		$jQ('table#addtable tbody').children().each(function () {
 			$jQ(this).remove(); 
		});
		$jQ(".partsInPricing").empty();
		$jQ(".partsInEstPricing").empty();
		calculatPricingSummary();
		$jQ('.pVoice').hide();
		$jQ("#addtable").hide();
		$jQ("#errorDiv").hide();
		$jQ("#totalPayment").html("");
		$jQ("#finalTotalPartPrice").html("");
		}	
	    }    
	    
	function displayExample(){
		if(document.getElementById("source").selectedIndex==2){
			document.getElementById("homeDepot").style.display="inline";
		}
		else{
			document.getElementById("homeDepot").style.display="none";
		}
	}
	
	 // Function to rounding off the entered price 
	 function amountRoundoff(obj){
	     var val = obj.value;
	     if(val == 0){
	        var zero = "0.00";
	     	obj.value = zero;
	      }else{
	      	obj.value = addOnObject.fmtMoney(val);
	      }
 	  }
	function formatAsMoney(mnt)
       {
          mnt -= 0;
          mnt = (Math.round(mnt*100))/100;
          return (mnt == Math.floor(mnt)) ? mnt + '.00' 
                : ( (mnt*10 == Math.floor(mnt*10)) ? 
                     mnt + '0' : mnt);
       }
       
	function amountRoundoffPartsPricing(obj)
	{	
	   var finalPartPrice='${partSpLimit}';
	      finalPartPrice=formatAsMoney(finalPartPrice);
	     var soFinalPartPrice = document.getElementById("soFinalPartPrice").value;
		 if(soFinalPartPrice!=""){	
		 	$jQ(".partsPricing").html("$"+(soFinalPartPrice ));
		 	$jQ(".parstEstPricing").html("$"+formatAsMoney(soFinalPartPrice -soFinalPartPrice *0.1));
		 }
		 else{
		 	$jQ(".partsPricing").html("$"+finalPartPrice);
		 	$jQ(".parstEstPricing").html("$"+formatAsMoney(finalPartPrice-finalPartPrice*0.1));
		 }
		
	}
	function optionOneForParts()
      {
    // document.getElementById("dropdown").style.display="none";

      document.getElementById("finalPriceForParts").style.display="none";
      document.getElementById("partsCheck").style.display='';
      document.getElementById("enterReasonHere").style.display="none";
      document.getElementById("chooseReason").style.display="none";
      $jQ('#enterOtherReason').val("Select Reason");
	  $jQ('#finalPriceForParts').val("");
  	  var pPrice = '${partSpLimit}';
  	  if(pPrice==''){
  	  	pPrice = 0.00;
  	  }
	  $jQ(".partsPricing").html("$"+addOnObject.fmtMoney(pPrice));
	  $jQ(".parstEstPricing").html("$"+addOnObject.fmtMoney(pPrice -pPrice*0.1));
	  //Setting finalPrice hidden field value on cclicking first check box.
	  $jQ("#finalPartPrice").val(pPrice);
      calculatPricingSummary();
      }
      
       function optionTwoForParts()
      {
      document.getElementById("chooseReason").style.display="block";
      document.getElementById("finalPriceForParts").style.display="block";
      document.getElementById("partsCheck").style.display='';
      $jQ("#soFinalPartPrice").val("");
	  var pPrice = '${partSpLimit}';
	  if(pPrice==''){
  	  	pPrice = 0.00;
  	  }
  	  $jQ(".partsPricing").html("$"+addOnObject.fmtMoney(pPrice ));
	  $jQ(".parstEstPricing").html("$"+addOnObject.fmtMoney(pPrice -pPrice *0.1));
      calculatPricingSummary();
    	 }
	
	

	// Function to delete the decrease the totals when row deleted
	function decreaseTotals(rowToBeDeleted){
     	var netPartPayment=parseFloat($jQ(rowToBeDeleted).children(".currencySymbol").children(".netPayment").html());
     	var finalPartPayment=parseFloat($jQ(rowToBeDeleted).children(".finalPayment").html());
     	var totalNetPayment=parseFloat($jQ("#totalPayment").html());
     	var finalPayment=parseFloat($jQ("#finalTotalPartPrice").html());
     	totalNetPayment=formatAsMoney(totalNetPayment-netPartPayment);
     	if(totalNetPayment==''){
  	  		totalNetPayment = 0.00;
  	 	}
     	finalPayment=addOnObject.fmtMoney(finalPayment-finalPartPayment);
     	if(finalPayment==''){
  	  		finalPayment = 0.00;
  	 	}
     	$jQ("#totalPayment").html(totalNetPayment);
     	$jQ("#finalTotalPartPrice").html(finalPayment);
     	$jQ(".partsInPricing").html("$"+formatAsMoney(finalPayment));
	 	$jQ(".partsInEstPricing").html("$"+formatAsMoney(totalNetPayment));
	 	calculatPricingSummary();
	 }           

	


	
	function expandOrderExpressMenu(path,imgId)
{
	var ob = $jQ("."+imgId).attr("src");
	var menu ="";
	menu = imgId+ "Menu";
	if(ob.indexOf('arrowDown')!=-1){
		$jQ("."+imgId).attr("src",path+"/images/widgets/arrowRight.gif");
		$jQ("."+menu).slideToggle("slow");
	}

}

	$jQ('#confirmMaxPrice').click(function() {
    if ($jQ('#confirmMaxPrice').attr('checked')) {
        $jQ('.pricewidget').show();$jQ('.pc').show();
        // Added for Reason Code 
       $jQ("#nonMaxPriceForPartCheck").val("false");
       } 
}); 
        $jQ('#dontConfirmMaxPrice').click(function() {
    if ($jQ('#dontConfirmMaxPrice').attr('checked')) {
        $jQ('.pricewidget').show();$jQ('.pc').show();
         // Added for Reason Code
        $jQ("#nonMaxPriceForPartCheck").val("true");

        } 
});
        $jQ('#chooseUrl').click(function() {
        	if ($jQ('#chooseUrl').attr('checked')) {
	        	$jQ("#divisionNumber").val("");
	        	$jQ("#sourceNumber").val("");
	        	$jQ("#partNumber").val("");
	        	$jQ("#partsUrl").removeAttr("disabled");
	        	$jQ("#partsUrl").css("background-color" , "#fff");
	        	$jQ("#saveUrl").removeAttr("disabled");
	        	$jQ("#partNumber").attr("disabled", "disabled");
	        	$jQ("#partNumber").css("background-color" , "#E8E8E8");
	        	$jQ("#divisionNumber").attr("disabled", "disabled");
	        	$jQ("#divisionNumber").css("background-color" , "#E8E8E8");
	        	$jQ("#sourceNumber").attr("disabled", "disabled");
	        	$jQ("#sourceNumber").css("background-color" , "#E8E8E8");
        	} 
        });
        	   
        
	$jQ('#dontChooseUrl').click(function() {
		if ($jQ('#dontChooseUrl').attr('checked')) {
			$jQ("#partNumber").removeAttr("disabled");
			$jQ("#partNumber").removeAttr("style");
			$jQ("#divisionNumber").removeAttr("disabled");
			$jQ("#divisionNumber").removeAttr("style");
			$jQ("#sourceNumber").removeAttr("disabled");
			$jQ("#sourceNumber").removeAttr("style");
			$jQ("#partsUrl").val("");
			$jQ("#divisionNumber").val("");
			$jQ("#sourceNumber").val("");
			$jQ("#partNumber").val("");
			$jQ("#partsUrl").attr("disabled", "disabled");
			$jQ("#partsUrl").css("background-color" , "#E8E8E8");
			$jQ("#saveUrl").attr("disabled", "disabled");
			$jQ("#errorDivParts").html("");
			$jQ("#errorDivParts").hide();
		} 
	});
        
 function enterReason(){
		if(document.getElementById("enterOtherReason").selectedIndex==3){
		document.getElementById("enterReasonHere").style.display="block";
		}
	else{
		document.getElementById("enterReasonHere").style.display="none";
		$jQ('#enterReasonHere').val("Explain the reason (50 chars)");
		}
	}
 
 function goToSummaryPages(){
		var selected=$ta.tabs('option', 'selected');
		// 0 is the summary tab
		if("0" == selected){
			moveToDocuments('${SERVICE_ORDER_ID}');
			return;
		}else{
			
			jQuery("#PartInd").val("Part");
						
		}
		$ta.tabs( "option", "active", 0 );	
		return false;
		
	}
	
	function expandCompInvParts(path)
	{
	jQuery("#compInvParts p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#compInvPartsid").slideToggle(300);

	var ob=document.getElementById('compInvPartsImg').src;
	if(ob.indexOf('arrowRight')!=-1){
	document.getElementById('compInvPartsImg').src=path+"/images/widgets/arrowDown.gif";
	}
	if(ob.indexOf('arrowDown')!=-1){
	document.getElementById('compInvPartsImg').src=path+"/images/widgets/arrowRight.gif";
	}
	}
 
 
	</script>
</head>
<body>
	<div id="panel_1"
		style="position: absolute; z-index: 20; display: none; padding-left: 100px; padding-top: 270px; height: 10px; fixed-width: 100px; word-wrap: break-word;">
		<p style="background: yellow;">
			&ldquo;Unit Cost&rdquo; is the actual price provider paid to source
			and<br>&nbsp;&nbsp;procure part.
		</p>
	</div>
	<div id="panel_2"
		style="position: absolute; z-index: 20; display: none; padding-left: 110px; padding-top: 310px; height: 10px; fixed-width: 100px; word-wrap: break-word;">
		<p style="background: yellow;">
			&ldquo;Retail Price&rdquo; is the actual retail price sold through
			Sears<br>&nbsp;&nbsp;Parts Direct.
		</p>
	</div>
	<div id="panel_3"
		style="position: absolute; z-index: 20; display: none; padding-left: 650px; padding-top: 420px; height: 10px; fixed-width: 80px; word-wrap: break-word;">
		<p style="background: yellow;">
			&ldquo;Est. Net Provider Payment&rdquo; is the remaining amount after<br>&nbsp;&nbsp;fees**
			have been applied.<br>
			<br>
			<font size="2">**Fees may change without notice.</font>
		</p>
	</div>
	<s:set name="partsSpendLimit" value="%{partSpLimit}" />
	<c:choose>
		<c:when test="${partSpLimit > 0}">
			<table id="partscheck" style="border-top: solid grey 1px;">

				<tbody>
					<tr>
						<td>
							<div
								style="background-color: silver; width: 30%; padding-left: 15px; color: white; font-weight: bold; height: 22px;">
								Parts<span style="color: red"> * </span>
							</div>
						</td>
						<td>&nbsp;</td>
					</tr>
					<tr id="form-parts-id">
						<td><input name="priceSelectionParts" id="confirmMaxPrice"
							onclick="optionOneForParts();expandOrderExpressMenu('${staticContextPath}','orderExpress')"
							src="${staticContextPath}/images/widgets/arrowDown.gif"
							value="confirm" type="radio" checked="checked"> I am
							claiming the <span id="maxPriceForParts">maximum price : <SPAN
								style="font-weight: bold;">$<fmt:formatNumber
										value="${partSpLimit}" type="NUMBER" minFractionDigits="2"
										maxFractionDigits="2" /></SPAN></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						<td><input name="priceSelectionParts"
							id="dontConfirmMaxPrice"
							onclick="optionTwoForParts();expandOrderExpressMenu('${staticContextPath}','orderExpress')"
							src="${staticContextPath}/images/widgets/arrowDown.gif"
							value="dontconfirm" type="radio"> I am claiming a
							different amount than the maximum price</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td id="chooseReason"
							style="display: none; padding-top: 10px; padding-left: 20px;"
							align="left"><b>Select reason</b> <span style="color: red;"display:none;">
								* </span> <select style="width: 145px; margin-left: 83px;"
							name="Select Reason*" onchange="enterReason()"
							id="enterOtherReason">
								<option value="Select Reason">Select Reason</option>
								<option value="Repair is a recall">Repair is a recall</option>
								<option value="Agreed to a lower rate">Agreed to a
									lower rate</option>
								<option value="Other">Other</option>
						</select><br></td>
					</tr>
					<tr>
						<td>&nbsp;</td>

						<td style="padding-left: 20px;"><textarea
								id="enterReasonHere" rows="1" cols="16"
								style="display: none; width: 146px; height: 42px; margin-top: 10px; margin-left: 155px;"
								onblur="clickRecall(this,'Explain the reason (50 chars)')"
								onfocus="clickClear(this,'Explain the reason (50 chars)')"
								onkeyup="countAreaChars(this.form.enterReasonHere, 50, event);"
								onkeydown="countAreaChars(this.form.enterReasonHere, 50, event);">Explain the reason (50 chars)</textarea>
							<br>


							<div id="finalPriceForParts" style="display: none;">
								<b>Enter <span id="finalPriceGlossary"
									style="cursor: pointer" class="glossaryItem">Final Price
										(Materials)</span></b> <span style="color: red"> * </span> &nbsp; <span
									style="padding-left: 0px;">$ <s:textfield
										cssStyle="width: 45px; text-align:right; font-size: 10px; font-family: verdana;"
										cssClass="text" name="finalPartPrice" id="soFinalPartPrice"
										value="%{finalPartPrice}"
										onchange="amountRoundoff(this);amountRoundoffPartsPricing(this);calculatPricingSummary();" />
								</span>
							</div></td>

					</tr>
					<tr>
				       <td colspan="2">
					      <hr />
				      </td>
			        </tr>
			</table>
			<script>
		  
		  //Setting for the reason codes
    	    	 $jQ("#enterOtherReason").change(function(){   
		     if ($jQ('#dontConfirmMaxPrice').attr('checked')){
		        $jQ("#selectReasonForParts").val($jQ("#enterOtherReason option:selected").val());
		        }
		    });
		  $jQ("#enterReasonHere").blur(function(){
		    var otherReasonTextParts = $jQ("#enterReasonHere").val();
		    if( otherReasonTextParts != "Explain the reason (50 chars)" && otherReasonTextParts != ""){
		    $jQ("#otherReasonTextParts").val(otherReasonTextParts);
		   }
		   });  
		  </script>
		</c:when>
		<c:otherwise>

			<hr color="black"  style="width:640px;"/>
			<div style="background-color: silver; width: 10%; padding-left: 15px; color: white; font-weight: bold;">
			   Parts
			</div>
			<br/>
			<hr color="black"  style="width:640px;"/>
		</c:otherwise>
	</c:choose>
	
<c:if test="${not empty invoiceParts}">	
<div id="compInvParts">
<p onclick="expandCompInvParts('${staticContextPath}')" style="background-color: silver;width:13%;padding-left: 15px; color: white; font-weight: bold; height: 12px;">&nbsp;Invoice Parts</p>
<div id="compInvPartsid">
The parts listed below are added to the service order by provider. To add additional parts, add invoice and update part status, go to 
<a style="text-decoration: underline; margin-left: 0px;color:#0033FF; padding-left: 0px; border: 0px;cursor:pointer" onclick="goToSummaryPages()">Parts </a> section in Summary.



	<table id="addtable" width="99%" class="installed_parts"
		cellpadding="0" border="1" bordercolor="grey" >

		<thead>
			<tr>
				<td class="installed_parts_odd" align="left" width="12%" style="padding-left:4px">Part Number</td>
				<td class="installed_parts_odd" align="center" width="12%">Part Name</td>
				<td class="installed_parts_odd" align="center" width="14%">Part Status</td>
				<td class="installed_parts_odd" align="center" width="12%">Invoice #</td>
				<td class="installed_parts_odd" align="center" width="10%">Proof Of Invoice</td>
				<td class="installed_parts_odd" align="center" width="10%">Qty</td>
				<td class="installed_parts_odd" align="right" width="10%" style="padding-right: 4px;">Unit Cost</td>
				<td class="installed_parts_odd" align="right" width="10%" style="padding-right: 4px;">
				<span id="RetailPriceHover" class="glossaryItem"
					    style="color: white; font: 10px/20px Verdana, Arial, Helvetica, sans-serif;">
				Retail Price
				</span>
				</td>
				<td align="right" width="10%" style="cursor: pointer; background-color: #00A0D2;padding-right: 4px;" colspan="2">
				   <span id="EstNtProvPymtHoverHover" class="glossaryItem"
					    style="color: white; font: 10px/20px Verdana, Arial, Helvetica, sans-serif;">Est.&nbsp;&nbsp;Net
						Provider Payment
				   </span>
				</td>			
				
			</tr>
		</thead>
		<tbody>
			<c:set var="totalNetPartPayment"
						value="0.00"></c:set>
						<c:set var="totalFinalPartPayment"
						value="0.00"></c:set>
			<s:iterator status="status" value="invoiceParts">
				<tr id='tablerow${status.index+1}'>
				
					
					<td class='buyer_parts_table'  id="partsNumber_${#status.index+1}"   
						align="left" width="12%" style="padding-left:4px">${partNo}</td>
						
						
						
					<td id='description_${status.index+1}'   class='buyer_parts_table'
						align="center" width="12%">
						<div id='descriptionPart_${status.index+1}' style='word-wrap: break-word'>
						<c:choose>
						<c:when test="${not empty description && fn:length(description) > 10}">
											    						${fn:substring(description,0,10)} <strong>...</strong>
											    					</c:when>
											    					<c:when test="${not empty description && fn:length(description) <= 10}">
											    						${description}
											    					</c:when>
											    					<c:otherwise>&nbsp;</c:otherwise>
						
					</c:choose>
							
							</div>
					</td>
						
					<td id='partStatus_${status.index+1}' class='buyer_parts_table'
						align="center" width="14%">
						<div style='word-wrap: break-word'>
							${partStatus}</div>
					</td>
					
					<td id='invoiceNumber_${status.index+1}' class='buyer_parts_table' 
						align="center" width="12%">
						<div style='word-wrap: break-word'>
							${invoiceNo}</div>
					</td>
					<td class="buyer_parts_table_edit" id="invoiceProof_${status.index}" align="center" width="10%">
				     <div>
				        <c:choose>
				          <c:when test="${not empty invoiceDocExists &&(invoiceDocExists== 'true')}">
						       <img src="${staticContextPath}/images/common/status-green.png"/>
					      </c:when>
					      <c:otherwise>
					           <img src="${staticContextPath}/images/common/status-red.png"/>
					      </c:otherwise>
					    </c:choose>
					</div>
				  </td>
					
						<td id='quantity_${status.index}' class='buyer_parts_table'  align="center" width="10%">
							<div style='word-wrap: break-word'>
							${qty}</div>			
					</td>
					
					

					
					<td id='unitCost_${status.index}' 
						class='buyer_parts_table' align="right" width="10%" style="padding-right: 4px;">
						<c:if test="${unitCost != '0.00'}">
				    	$${unitCost}
				   		</c:if></td>
						
					<td class="buyer_parts_table_edit retailPrice" id="retailPrice_${status.index}" align="right"  width="10%;" style="padding-right: 4px;">
				         $${retailPrice}
				   </td>	
						
						<td colspan="3" class="buyer_parts_table"  align="right"
						id="currencysymbol_${#status.index+1} width="10%" style="padding-right: 4px;">
						<c:if test="${partStatus == 'Installed'}">
						$
						<span id='netPayment_${#status.index+1}' class='netPayment'
						bgcolor="#FFFFFF" ; > <fmt:formatNumber
								value="${estProviderPartsPayment}" type="NUMBER"
								minFractionDigits="2" maxFractionDigits="2" />
					</span>
					<c:set var="totalNetPartPayment"
						value="${totalNetPartPayment+estProviderPartsPayment}"></c:set>
					</c:if>
					
										<c:if test="${partStatus != 'Installed'}">
								<span id='netPayment_${#status.index+1}' class='netPayment'
					align="center"	> NA
					</span>
										
					</c:if>
					
					</td>
					
						
					<!-- End of table  -->
					
					<!-- Start of unwanted section..to be deleted??  -->
					
					 <span id='finalPaymentConvRate_${#status.index+1}'
						style="display: none" class='finalPaymentConvRate'> <fmt:formatNumber
								value="${finalPrice/qty}" type="NUMBER" minFractionDigits="2"
								maxFractionDigits="2" />
					</span> <span id='netPaymentConvRate_${#status.index+1}'
						style="display: none" class='netPaymentConvRate'> <fmt:formatNumber
								value="${estProviderPartsPayment/qty}" type="NUMBER"
								minFractionDigits="2" maxFractionDigits="2" />
					</span>
					
					
					<td id='finalPayment_${#status.index+1}' class='finalPayment'
						style='display: none;' align="center"><fmt:formatNumber
							value="${finalPrice}" type="NUMBER" minFractionDigits="2"
							maxFractionDigits="2" /></td>
																
							
							
					<td style="display: none" class="variableInputs"><input
						type="hidden" id="invoiceParts[${status.index}].finalPrice"
						value="${finalPrice}"
						
						cssClass="finalPriceInput" />
						
						
						
						
						 <input type="hidden"
						id="invoiceParts[${status.index}].estProviderPartsPayment"
						value="${estProviderPartsPayment}"
						
						cssClass="netPaymentInput" /> 
						
						<input type="hidden"
						id="invoiceParts[${status.index}].qty" value="${qty}"
						cssClass="qtyInput" />
						 <input
						type="hidden"
						id="invoiceParts[${status.index}].retailCostToInventory"
						value="${retailCostToInventory}"
						 /> 
						 
						 <input
						type="hidden"
						id="invoiceParts[${status.index}].retailReimbursement"
						value="${retailReimbursement}"
						 /> 
						 
						 <input
						type="hidden"
						id="invoiceParts[${status.index}].retailPriceSLGrossUp"
						value="${retailPriceSLGrossUp}"
						/>
						
						 <input
						type="hidden" id="invoiceParts[${status.index}].retailSLGrossUp"
						value="${retailSLGrossUp}"
						 />
						 
						 
						 </td>






					<c:if test="${partStatus == 'Installed'}">
					<c:set var="totalFinalPartPayment"
						value="${totalFinalPartPayment+finalPrice}"></c:set>
						</c:if> 
			</s:iterator>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="8" style="padding-left: 500px;padding-top:5px;padding-bottom:5px;"><b>Sub Total
						for Parts</b></td>
				<td colspan="1" align="right" style="padding-top:5px;padding-bottom:5px;padding-right: 4px;" ><b>$ <span
						class="payment" id="totalPayment" bgcolor="#FFFFFF"> <fmt:formatNumber
								value="${totalNetPartPayment}" type="NUMBER"
								minFractionDigits="2" maxFractionDigits="2" /></span></b></td>
			</tr>
		</tfoot>
	</table>
	<div id="loadingImage"></div>
	<div id="reImbursementRate" style="visibility: hidden;"></div>
	<div id="finalTotalPartPrice" style="visibility: hidden;">
		<fmt:formatNumber value="${totalFinalPartPayment}" type="NUMBER"
			minFractionDigits="2" maxFractionDigits="2" />
				
			
	</div>
</div>
</div>	
</c:if>
	
	
	

	<input type="hidden" name="selectReasonForParts"
		id="selectReasonForParts" />
	<input type="hidden" name="otherReasonTextParts"
		id="otherReasonTextParts" />
	<input type="hidden" name="nonMaxPriceForPartCheck"
		id="nonMaxPriceForPartCheck" />
	<div id="explainers" style="z-index: 1000"></div>
	<script type="text/javascript">

// Can somebody please explain why this time out is set?
// Commenting this so that the screen will render without any delay!

// var myMaterial;
// myMaterial=setTimeout(function(){materialShow()},5000);

// I have no idea why we need this validation after the page is loaded completely?
// Can someone help me to understand the 'logic' behind this?
// This function on validaiton fail to set the reason codes to its defaults
validateReasonCodes();
</script>
</body>
</html>