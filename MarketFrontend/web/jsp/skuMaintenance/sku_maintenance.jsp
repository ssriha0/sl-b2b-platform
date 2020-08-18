<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<title>ServiceLive - SKU Maintenance </title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/so_details.css"/>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/skuMaintenance.css"/>
	<link href="${staticContextPath}/javascript/confirm.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery.modal.min.css" type="text/css"></link>
	<style>
		.modal {
			    max-width: 100%;
			    width: 100%;
		}
	</style>
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
	
	<script type="text/javascript" >
	$("span.moreLink").mouseenter("mouseover", function(e){
		var x = e.pageX;
		var y = e.pageY;
	    var idVal=this.id;
		$("#"+idVal+"moreDetails").css('top',y-350);
		$("#"+idVal+"moreDetails").css('left',x-269);
		var details = $("#"+idVal+"moreDetails").text().split("\n");			
		$("#"+idVal+"moreDetails").show();
	});	
	$("span.moreLink").mouseleave("mouseout", function(e){
		    var idVal=this.id;
			$("#"+idVal+"moreDetails").hide();
		});		
	</script>		
	<script type="text/javascript"
		src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
	<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/plugins/tabs_jquery.js"  
		charset="utf-8">
    </script>

<script type="text/javascript" >

function limitText(limitField,limitNum) {
        if (limitField.value.length > limitNum) {
                limitField.value = limitField.value.substring(0, limitNum);
        }
        } 
 
 function alignButton(obj)
{
var value=obj.value;
var categoryLength = $("#skuCategory option:selected").length;

if(value==-1 || categoryLength>1)
{
$("#updateSKUType").hide();
$('#updateSKUType').css({"visibility":"hidden"});
jQuery("#addSKUType").css("margin-left","50px");
}
else
{
$('#updateSKUType').css({"visibility":"visible"});
jQuery("#addSKUType").css("margin-left","10px");

}

}

function alignButtons(obj)
{
var value=obj.value;
var categoryLength = $("#skuNameByCategory option:selected").length;

if(value==-1 && categoryLength<2)
{
jQuery("#addSkuByCategory").css("margin-left","92px");
}
else
{
jQuery("#addSkuByCategory").css("margin-left","3px");
jQuery("#updateSKUByCategory").css("margin-left","10px");
}

}

function doOnclick(id) {
	if(id == 'skuCategory')
	{
	
	    var c = getSelected(id);
	    if(c == -1){
    	$("#updateSKUType").hide();
    	}
		var index = "";
		index += 'desc'+getSelected(id);
        jQuery("#skuNameIdDetail").hide();		
	}
	if(id == 'skuNameByCategory')
	{
		var index1 = "";
		index1 += 'descr'+getSelected(id);
		var skuNameLength = $("#skuNameByCategory option:selected").length;
		var selectedSKU = $("#skuNameByCategory option:selected").text();
		if(skuNameLength==1){
	    var a = getSelected(id);
	    if(a == -1){	    
	     $("#dsplyMsgeFrChkbx").hide();
	     $("#hdnMsgeFrChkbx").hide();
	    }
	    else{
	    a = a+"_msi";
	    mangeScpeIndBhvr(a,selectedSKU);
	    }

	 }
		else{
	   	$("#dsplyMsgeFrChkbx").hide();
	    $("#hdnMsgeFrChkbx").hide();
	    }
}
	if(id == 'skuCategory')
	{
  var b = getSelected(id);
	    if(b == -1){
     	$("#updateSKUType").hide();
     	}
		var val = $('#'+index ).val();
		jQuery("#outputtext").html(val);
		showSkuDescription(val);
	}
	if(id == 'skuNameByCategory')
	{
		var val1 = $('#'+index1).val();
		jQuery("#outputtext1").html(val1);
		showSkuNameDescription(val1);
	}

}
function getSelected(id) {
	var selected = "";
	selector = document.getElementById(id);
	for (var i = 0; i < selector.options.length; i++ ) {
		if (selector.options[i].selected) {
		selected += selector.options[i].value + "";
	}
}
return selected;
}
 
function mangeScpeIndBhvr(id,selectedSKU){
      var selected = document.getElementById(id).value;
      var displayNote = 'The SKU <i>'+selectedSKU +'</i> is displayed on Manage Scope widget.';
      var hideNote = 'The SKU <i>'+selectedSKU +'</i> is not displayed on Manage Scope widget.';
     
	  $("#dsplyMsgeFrChkbx").hide();
	  $("#hdnMsgeFrChkbx").hide();
	 	if(selected == "true"){
	 		$("#dsplyMsgeFrChkbx").html(displayNote);
	  		$("#dsplyMsgeFrChkbx").show();
		}
		else if(selected == "false"){
			$("#hdnMsgeFrChkbx").html(hideNote)
			$("#hdnMsgeFrChkbx").show();
		}
	} 

function hideOptionsku() {
   $("#skuNameDescByCategory option").each(function (i) {
        if ($(this).is('option')) {
         $(this).wrap('<p>').hide();
           $("#skuNameDescByCategory").attr('disabled','true');
        }
    });
}

function showOptionsku() {
    $("#skuNameDescByCategory option").each(function (i) {
        if ($(this).is('option') && ($(this).parent().is('p'))) {
        	$(this).parent().replaceWith('')
        }
    });
}

// jQuery function to show update button initially,
// it disappers when user clicks more than one sku category



function showUpdateInProgress(){
	  jQuery("#validId").hide();	
	  $('#cancelId').replaceWith("<span><u style='color: gray;'>Cancel<u></span>");
	  
	  jQuery("input#validId").replaceWith("<div style='padding-left:420px;white-space:nowrap;'><b>Updating SKU Category...<b></div>");
	  
}
//SLT-1168
function validateCreditCardNumber(creditCardNumber){
	var isValidNumber = false;
	var isValid = false;
	var ccCheckRegExp = /[^\d ]/;
	var numberProduct;
	var numberProductDigitIndex;
	var checkSumTotal = 0;
	var cardNumber=null;
	
	var replaceNumbersThatMightContainACreditCard = creditCardNumber.replace(/[^0-9]/g, ' ');
	var validateCCRegx=replaceNumbersThatMightContainACreditCard.match("\\b(?:4[ -]*(?:\\d[ -]*){11}(?:(?:\\d[ -]*){3})?\\d|"
			+ "(?:5[ -]*[1-5](?:[ -]*\\d){2}|(?:2[ -]*){3}[1-9]|(?:2[ -]*){2}[3-9][ -]*"
			+ "\\d|2[ -]*[3-6](?:[ -]*\\d){2}|2[ -]*7[ -]*[01][ -]*\\d|2[ -]*7[ -]*2[ -]*0)(?:[ -]*"
			+ "\\d){12}|3[ -]*[47](?:[ -]*\\d){13}|3[ -]*(?:0[ -]*[0-5]|[68][ -]*\\d)(?:[ -]*"
			+ "\\d){11}|6[ -]*(?:0[ -]*1[ -]*1|5[ -]*\\d[ -]*\\d)(?:[ -]*"
			+ "\\d){12}|(?:2[ -]*1[ -]*3[ -]*1|1[ -]*8[ -]*0[ -]*0|3[ -]*5(?:[ -]*"
			+ "\\d){3})(?:[ -]*\\d){11})\\b");

    if(validateCCRegx!=null){
	  cardNumber=validateCCRegx[0];
     }
	if(cardNumber!=null){
		var cardNumberOnly=cardNumber.replace(/[^0-9]/g, "");
		isValidNumber = !ccCheckRegExp.test(cardNumberOnly);
		var cardNumberLength = cardNumberOnly.length;
		if (isValidNumber){
			for (digitCounter = cardNumberLength - 1; digitCounter >= 0; digitCounter--)
			{
				checkSumTotal += parseInt (cardNumberOnly.charAt(digitCounter));
				digitCounter--;
				numberProduct = String((cardNumberOnly.charAt(digitCounter) * 2));
				for (var productDigitCounter = 0;productDigitCounter < numberProduct.length; productDigitCounter++)
				{
					checkSumTotal += 
					parseInt(numberProduct.charAt(productDigitCounter));
				}
			}
			isValid = (checkSumTotal % 10 == 0);
		}
	}
	return isValid;
} 

// Function to make ajax call for validate

function vldtnFrExstngSkuCtgryNme(){
   jQuery("#errorSkuCatName").html("");
   jQuery("#errorSkuCatDesc").html("");
   jQuery("#errorSkuCatDesc").html("");
   var skuCategoryId   =  jQuery("#skuCatIdHid").val();
   var catgryName=jQuery.trim($('#skuCtgryNameUpdtd').val());
   var catgryDesc= jQuery.trim($('#skuDscrptnUpdtd').val());
   var catName = $("#skuCategory option:selected").text();
 //SLT-1168
   var ccValidationFlag=false;
   if((catgryDesc.length) > 0 && validateCreditCardNumber(catgryDesc)){
	   ccValidationFlag=true;
	   $("#errorTag").show();
		$("#successTag").hide();
		jQuery("#errorSkuCatDesc").html("Please don't enter the credit card number in SKU Category Description.");
		jQuery("#errorSkuCatDesc").show();	   
		jQuery("#validId").show();
   }
   if((catgryName.length) > 0 && (catgryDesc.length) > 0 && !ccValidationFlag)
   {
    var updateUrl='skuCategory_validate.action?skuCategoryName='+encodeURIComponent(catgryName)+'&skuCategoryId='+encodeURIComponent(skuCategoryId);
	jQuery("#ajaxSku").load(updateUrl,function(){   
	    var isValidate = jQuery("#validateSkuCategory").html();	 
		var catgryName=jQuery.trim($('#skuCtgryNameUpdtd').val());
        var catgryDesc= jQuery.trim($('#skuDscrptnUpdtd').val());
		isValidate=jQuery.trim(isValidate);		
		if(isValidate == 'true')
		{	 
		   showUpdateInProgress();
	       updtngSkuDtlsToDataBaseOnSubmit(catgryName,catgryDesc,skuCategoryId);
	       
	    }
	    else if(isValidate == 'fail')
	    {   	jQuery("#validId").show();	
				$("#errorTag").show(); 
				$("#successTag").hide();  
		        jQuery("#errorSkuUpdate").html("Choose different SKU Category"+
		        " name as there is already an existing SKU Category with this name");
		        jQuery("#errorSkuUpdate").show();
	     }
});
}
else
{
    var catgryNames=jQuery.trim($('#skuCtgryNameUpdtd').val());
    var catgryDescs= jQuery.trim($('#skuDscrptnUpdtd').val());
    if((catgryNames.length) == 0)	
	{ 
		$("#errorTag").show();
		$("#successTag").hide();	 
		jQuery("#errorSkuCatName").html("Please provide the SKU Category Name.");
		jQuery("#errorSkuCatName").show();
		jQuery("#validId").show();	
    }
	if((catgryDescs.length) == 0)	
	{
		$("#errorTag").show();
		$("#successTag").hide();
		jQuery("#errorSkuCatDesc").html("Please provide the SKU Category Description.");
		jQuery("#errorSkuCatDesc").show();	   
		jQuery("#validId").show();	
	}	  
}
}

//ajax call for loading sku category history
	 function loadCategoryHistory(){
	 var categId = $('#skuCategory').val();
	 $('#skuCategoryHistory').load("fetchCategoryHistory.action?categId="+categId,function() {
	$("#skuCategoryHistory").modal({      
                onOpen: modalOpenAddCustomer,
                onClose: modalOnClose,
                persist: false,
                containerCss: ({ width: "610px", height: "auto", marginLeft: "10px", top: "200px" })
            });
            });
            }

//ajax call for loading sku history
	 function loadSkuHistory(){
	 var skuHistId = $('#skuNameByCategory').val();
	 $('#skuHistory').load("fetchSkuHistory.action?skuHistId="+skuHistId,function() {
	 $("#skuHistory").modal({      
                onOpen: modalOpenAddCustomer,
				persist: false,
                containerCss: ({ width: "610px", height: "auto", marginLeft: "10px", top: "200px" })
            });
            });
            }


function loadModal(source){
$("#successMsg").hide();
var categIdforUpdate = $('#skuCategory').val();
 var skuIdforUpdate = $('#skuNameByCategory').val();
 var a="skuCategory_add.action?source="+source+"&skuIdforUpdate="+skuIdforUpdate+"&categIdforUpdate="+categIdforUpdate;
    var skuSelectedListSize = $("#skuNameByCategory option:selected").length;
    if ((skuSelectedListSize == 1 && skuIdforUpdate != "-1") || (source != 'updateSKUByCategory')) {   
$('#addSkuCategory1').load("skuCategory_add.action?source="+source+"&skuIdforUpdate="+skuIdforUpdate+"&categIdforUpdate="+categIdforUpdate,function() {
	$("#addSkuCategory").modal({      
                onOpen: modalOpenAddCustomer,
                persist: false,
                containerCss: ({width: "860px", height: "auto", marginLeft: "20px", top: "495px", zIndex: "99999" })
            });
            if(source == 'addSKUType'){
			}
			else if(source== 'updateSKUByCategory'){
				$("#charCount").hide();
				$("#maxCountValue").hide();
			}
			else if(source== 'addSkuByCategory'){
				$("#charCount").hide();
				$("#maxCountValue").hide();
			}
            });
    }	                          
            window.scrollTo(0,0);
			}
			
// This function shows up the update pop-up when user clicks update button.

function loadUpdateSkuCategoryModal(){
$("#successMsg").hide();
    clearErrorFields();
    
    //setting selected category name and decription in update SKU Category modal
    var id = $('#skuCategory').val();
   	var catName = $("#skuCategory option:selected").text();
	$('#skuCtgryNameUpdtd').val(jQuery.trim(catName));
    var selectedCategoryDescr = $('#desc'+id).text();
    $('#skuDscrptnUpdtd').val(selectedCategoryDescr);
   
    var remainingLength = (255-selectedCategoryDescr.length);
    $("#reasonCommentd_leftChars").val(remainingLength);
    
	$("#updateSkuCategory").modal({      
                onOpen: modalOpenAddCustomer,
                onClose: modalOnClose,
                persist: false,
                containerCss:({width: "657px", height: "310px", marginLeft: "10px", top: "50px" })
            });
           window.scrollTo(0,0);            
			}
function  clearErrorFields(){
    $("#imageId").show();
	$("#errorTag").hide();
	$("#successTag").show();
	$("#errorSkuUpdate").html("");
	$("#errorSkuCatDesc").html("");
	$("#errorSkuCatName").html("");
}
function modalOpenAddCustomer(dialog) {

        dialog.overlay.fadeIn('fast', function() {
            dialog.container.fadeIn('fast', function() {
            dialog.data.hide().slideDown('slow');
            });
        });
      
}
// This function is to perform close operation of the pop-up page. 
function modalOnClose(dialog) {
       dialog.data.fadeOut('slow', function() {
       dialog.container.slideUp('slow', function() {
       dialog.overlay.fadeOut('slow', function() {    
       $.modal.close(); 
               });
           });
       });
    }
// function to update the user inputs to the database on click of submit button.
function updtngSkuDtlsToDataBaseOnSubmit(catgryName,catgryDesc,skuCategoryId)
 {
   document.getElementById('updtdSkuCtgryDtls').action ='${contextPath}/skuCategory_update.action?skuCategoryName='+encodeURIComponent(catgryName)+'&skuCategoryDescription='+encodeURIComponent(catgryDesc)+'&skuCategoryId='+encodeURIComponent(skuCategoryId)+"&updateSkuCategory=true";
   document.getElementById('updtdSkuCtgryDtls').method="POST"; 
   document.getElementById('updtdSkuCtgryDtls').submit();
} 

// function to show corresponding Sku Description when Sku category is selected from the list of sku categories.
function showSkuDescription(categoryDesc){
	$("#successMsg").hide();
    $("#skuCategoryHistoryLink").show();
    var categoryText = categoryDesc;
	$("#updateSKUType").show();

	var categoryId=$('#skuCategory').val();
    jQuery("#skuCatIdHid").val(categoryId); 
	var categoryLength = $("#skuCategory option:selected").length;
	
	if(categoryLength==1 && categoryId != -1)
	{	jQuery("#loadingImage").html('<img src="/ServiceLiveWebUtil/images/loading.gif" width="572px" height="160px" />');
		$("#skuNameDisplay").hide();	        
        jQuery("#loadingImage").show();
		$("#skuNameDisplay").load("sku_maintenanceSkuName.action?categoryId="+categoryId,
		function() {jQuery("#loadingImage").hide();
		$("#skuNameDisplay").show();
        hideOptionsku();		
		});		
	}
	else
	{
     $("#outputtext").html("");	
	 hideOption();
	 $("#skuNameIdDetail").hide();	
	 $("#skuNameDisplay").hide();
	 $("#updateSKUType").hide();
	 $("#skuCategoryHistoryLink").hide();
	}
	}
	
function fnReturnToDashboard(){
			window.location.href = 'dashboardAction.action';
		}

function displayMainInfo(id){
			if(id == 'retailPriceMainInfo'){
			jQuery("#retailPriceMainInfoDiv").show();
		}
			if(id == 'priceTypeMainInfo'){
			jQuery("#priceTypeMainInfoDiv").show();
		}
			if(id == 'bidMainInfo'){
			jQuery("#bidMainInfoDiv").show();
		}
			if(id == 'billingMainInfo'){
			jQuery("#billingMainInfoDiv").show();
		}					
 		
		}
		function hideMainInfo(id){ 
			if(id == 'retailPriceMainInfo'){
			$("#retailPriceMainInfoDiv").hide();
		}
			if(id == 'priceTypeMainInfo'){
			$("#priceTypeMainInfoDiv").hide();
		}
			if(id == 'bidMainInfo'){
			$("#bidMainInfoDiv").hide();
		}
			if(id == 'billingMainInfo'){
			$("#billingMainInfoDiv").hide();
		}				
		}
		
function countAreaChars(areaName,counter,limit, evnt){
		if (areaName.value.length>limit) {
			areaName.value=areaName.value.substring(0,limit);
		
			//Stop all further events generated (Event Bubble) for the characters user has already typed in .
			//For IE
			if (!evnt) var evnt = window.event;
			evnt.cancelBubble = true;
			//For FireFox
			if (evnt.stopPropagation) evnt.stopPropagation();
		}
		else
		counter.value = limit - areaName.value.length;
	}
</script>
</head>
<style>
.my
{
border-top:solid thin grey;
border-right: solid thin grey;
border-left: solid thin grey;

padding-bottom:80px;
padding-left:20px;
border-color:#cccccc;

border-width:1px;
}
.longer {
width: 100%;
}
.format {
text-align: left;
}
.headLine {
    font-weight: normal;
    vertical-align: text-bottom;
}

h1 {
    color: #00A0D2;
    font-size: 2em;
    line-height: 120%;
    margin-bottom: 10px;
    margin-left: 0;
    margin-right: 10px;
    margin-top: 20px;
}

</style>
<body class="tundra acquity">
    
	<div id="page_margins">
			<div id="page">
				<!-- START HEADER -->
				<div id="headerShort">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>

					<div id="pageHeader">
						<div>
							<h2 style="padding-left:20px;"><b style="color:#00A0D2 ; font-size: 1.5em; padding-right: 15px; font-weight: normal; line-height: 100%; float: left;">Administrator Office</b>
							<b style="color: #33393C; font-size: 1.5em; padding-right: 15px; font-weight: normal; line-height: 100%; float: left;">SKU Maintenance</b></h2>
						</div>
					</div>
				</div>

				<!-- END HEADER -->
	<!-- <div align="center" style="font-size:15px;width:90px;background-color:white;color:#00A0D2;position:relative;left:70px;top:14px;z-index:1 "><b>Map SKU </b></div> -->
		<fieldset style="width: 850px;padding-left: 25px;padding-right: 5px;margin-left: 35px;">
			<legend style="font-size:17px;color:#00A0D2;">SKU Category Details</legend>
			<br />
			<div id="successMsg" class="skuSuccessMsg" style="display: none;padding-top: 5px;font-size:11px"></div>
	<table width="800"  align="center">
	<tr><td style="padding-top:10px;">
	<table width="800" align="center" id="skuCatNameDescr">
		<tr>
			<td align="left"   style="padding-bottom:3px;padding-left:00px;font-size:13px">
				<span><b>SKU Category</b></span>
				<span style="padding-left: 155px;"><a href="#" id="skuCategoryHistoryLink" onclick="loadCategoryHistory();" style="display: none; font-size: 10px;">History</a></span>
			</td>
		
			<td align="left"   style="padding-bottom:3px;padding-left:5px;font-size:13px ">
				<b>SKU Category Description</b>
			</td>
		</tr>
	<tr>	
	<td style="padding-left: 0px;padding-right:30px;">
	
    <c:choose><c:when test="${not empty buyerSkuCategoryList}" >
    <c:set var="index" value="0"></c:set>
	<select name="ActionSelect" id="skuCategory" multiple="multiple" onclick="alignButton(this);doOnclick('skuCategory');" onkeyup="alignButton(this);doOnclick('skuCategory');"
			style="text-align:left;width: 300px;overflow-y: scroll;overflow-x: scroll;border: 1px solid grey;font-family:Arial;
			height: 105px;margin: 0 auto;display: block;" >  
  	<option id="categoryName" value="-1" selected="true">Please Select</option>
    <c:forEach var="buyerSkuCategoryList" items="${buyerSkuCategoryList}" varStatus="dtoIndex" >  
    <c:set var="index" value="${index+1}"></c:set>
	<option id="categoryName" value="${buyerSkuCategoryList.categoryId}">${buyerSkuCategoryList.categoryName}</option>  
	</c:forEach> 
	</select>  
    </c:when>
    <c:otherwise>
    <select name="ActionSelect" id="selc" multiple="multiple" 
    style="font-size:15px;text-align:left;width: 300px;overflow-y: scroll;overflow-x: scroll;font-family:Arial;
    height: 100px;
    margin: 0 auto;
    display: block;">  
    <option>No SKU Category</option>
	</select>
	</c:otherwise></c:choose>

</td>

<td style="padding-left:5px;padding-right:100px;"><textarea  disabled="disabled" id="outputtext" rows="50" cols="38" style="background-color: #FAFAFA;font-size:10px; overflow-y: scroll; height: 100px; width: 380px;font-family:Arial; 
	border: 1px solid grey;"></textarea>
	<c:set var="index" value="0"></c:set>
	<select disabled="disabled" name="ActionSelect" id="skuName" multiple="multiple" 
	style="background-color: #FAFAFA;font-size:10px;text-align:left;width: 400px;overflow-y: scroll;overflow-x: scroll;font-family:Arial;
    height: 100px;
    margin: 0 auto;
    display: none;">  
	<c:forEach var="buyerSkuCategoryList" items="${buyerSkuCategoryList}" varStatus="dtoIndex" >  
    <c:set var="index" value="${index+1}"></c:set>
  
	<option class="Skudesc"  id="desc${buyerSkuCategoryList.categoryId}" 
	value="${buyerSkuCategoryList.categoryDescr}">${buyerSkuCategoryList.categoryDescr}</option>  
	</c:forEach> 
	</select>
	 </td>	
	</tr>
	
	<script type="text/javascript">
	hideOption();
	function hideOption() {		
	$("#skuName option").each(function (i) {
	        if ($(this).is('option')) {
	         $(this).wrap('<p>').hide();
	         $("#skuName").attr('disabled','true');
	        }
	    });
	}
	</script>	
	
	<tr ><td style="padding-bottom:13px;"></td><td>
	</td></tr>
	<tr>
	<td>
	<div style="padding-left:110px;">
	<table>
	<tr/>
	<tr>
	<td>
	<input class="button action" id="updateSKUType" style="margin-top:0px;width:60px;display: none;" 
	type="button" value="Update" onclick="loadUpdateSkuCategoryModal();"/>
	&nbsp;&nbsp;&nbsp;
	</td>
	<td/>
	<td>
	<input class="button action" id="addSKUType" style="margin-top:0px;width:60px;" type="button" 
	value="Add" onclick="loadModal(this.id); "/>
	</td>
	</tr>
	</table>
	</div>
	</td>
	<td style=" padding-left:200px;">
	</td>
	<td>
	</td>
	</tr>

	</table>
	</td>
	</tr>
	<tr><td>
	</td></tr>
	</table>
	
	<div id="loadingImage" style="left:150px;top:-80px;"></div>
		<div id="skuNameDisplay" style="display: none;">
				<jsp:include page="sku_name_by_categoryId.jsp"/>
			
	</div>
	</fieldset>
	</div>
	<br>
<div id="skuNameIdDetail" style="display:none;">
	<jsp:include page="sku_detail_sku_Id.jsp"/>
</div>
<div id="addSkuCategory1" name="addSkuCategory1" style="display: none; overflow: scroll;"></div>
	<br>
	<!-- <table width="900"  align="center" >
	<tr align="left">
		<td style="padding-left:5%">
		<hr align="center" width="102%" size="4" color="#cccccc">
		</td>
	</tr>
	<tr align="left">
    <td style="padding-left:5%">
    	<a onclick="javascript:fnReturnToDashboard()" class="cancel" href="#"><u style="color: red;"><b>Cancel</b></u></a>	
	</td>
	</tr>
	</table> -->
	<form id="updtdSkuCtgryDtls" name="updtdSkuCtgryDtls">
	<input type="hidden" name="existingSkuCtgryName" id ="existingSkuCtgryName" value="" />
	<input type="hidden" name="skuCategoryId" id ="skuCategoryId" value="" />
	<div id="ajaxSku" style="display: none"></div>
	 <div id="updateSkuCategory" name="updateSkuCategory" class="modal" style="padding: 0px 0px;">
	                <div class="modalHomepage" style="padding: 15px">
	                <div style="float:left;display: block;" id="successTag" >Update SKU Category</div>
	                <div style="float: left; display: none;" id="errorTag">Error - Update SKU Category</div>
	                <a href="javascript: void(0)" id="closeButton"
						class="btnBevel simplemodal-close" style="color: white; float: right;">
						<img src="${staticContextPath}/images/widgets/tabClose.png" alt="X">
					</a>
	                </div>
	                <br>
	                <div id="errorSkuUpdate"  align="left" style="display: none; color: red; position: relative; left: 20px;"></div>
	                <div id="errorSkuCatName" align="left" style="display: none; color: red; position: relative; left: 20px;"></div>
	                <div id="errorSkuCatDesc" align="left" style="display: none; color: red; position: relative; left: 20px;"></div>
	                <div class="modalheaderoutline" style="border-style: none;">
	                <div class="rejectServiceOrderFrame" style="width: 100%; border: 0px;padding: 10px 10px">
					<div class="rejectServiceOrderFrameBody" style="width: 100%;padding-top: 0px">
	                                                        
	           <b>SKU Category Name</b><span style="color: red;"> * </span>&nbsp;
	   		 	<input maxlength="30" type="text" name="skuCategoryName" id="skuCtgryNameUpdtd" style="font-family:Arial;font-size:10px;"/><br><br>
	            <b>SKU Category Description</b><span style="color: red;" > * </span><br><br>
	   
	    <textarea rows="5" cols="67" id="skuDscrptnUpdtd" name="skuCategoryDescription" 
	    		  style="overflow: scroll;height:7em;font-family:Arial;font-size:10px;" 
	    		  onkeyup="limitText(this,255);countAreaChars(this, document.getElementById('reasonCommentd_leftChars'), 255, event);"
	    		  onkeydown="limitText(this,255);countAreaChars(this, document.getElementById('reasonCommentd_leftChars'), 255, event);"
	           	  onfocus="countAreaChars(this, document.getElementById('reasonCommentd_leftChars'), 255, event);">
	    </textarea>
	    
	    <input type="text" id="reasonCommentd_leftChars" name="updateSkuCat_leftChars" value="255" 
	    		maxlength="3" size="3" readonly="readonly" style="font-family:Arial;font-size:10px;border:none;color:grey;font-style:italic"><i>characters remaining</i>
	    <input type="hidden" id="skuCatIdHid"></input>
		
		<div style="padding-top:13px" >
		<hr size="4" color="#cccccc" width="610px"></div>
		
		<table height="10px">
	    <tr>
	    <td>
	    	  <a href="#" id="cancelId">
	    	  <u style="color: red;" class="btnBevel simplemodal-close"><b>Cancel</b></u></a>	    	    
	    </td>
	   <td><div style='padding-left:220px;white-space:nowrap;display:none;' id="upd"><b>Updating SKU Category...<b></div></td>
        <td style="align:right;padding-right: 15px;">
        <input height="20" width="72" type="image" onclick="vldtnFrExstngSkuCtgryNme();" id="validId" src="${staticContextPath}/images/common/spacer.gif" style="margin-left:500px;background-image: url(&quot;${staticContextPath}/images/btn/submit.gif&quot;);" class="btnBevel">
        <div></div>
	    </td>
	    </tr>
</table>	    
	</div>
	</div>
	</div>
	</div>
	<script type="text/javascript">
	$("#updateSkuCategory").hide();
	</script>
	</form>
	<!-- div for displaying sku category History -->
          
          	<div id="skuCategoryHistory" class="modal" style="display: none; padding: 0px 0px;"></div>
		  	<div id="skuHistory" class="modal" style="display: none; padding: 0px 0px;"></div>
			<div class="span-24 clearfix">
			<!-- START FOOTER -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER -->
			</div>
		</div>
	
</body>
<script type="text/javascript">
	  jQuery(document).keydown(function(e){        
		if (e.keyCode == 27){ 
		 jQuery.modal.close();     
	    }   
	  });
      
      jQuery(document).ready(function() {
      jQuery("#addSKUType").css("margin-left","57px");
      jQuery("#addSkuByCategory").css("margin-left","92px");
      var updateSkuCategory = '${updatedSKUCategoryId}';
      if('${addSkuCategory}' == 'true'){
      	  var newCategoryName = '${newCategoryName}';
      	  var newSkuName = '${newSkuName}';
           //to highlight the newly created sku category      
     	 $("#skuCategory option").each(function() {
     			 if(jQuery.trim($(this).text()) == 'Please Select') {
   							 $(this).removeAttr('selected');            
  								}
  						if(jQuery.trim($(this).text()) == jQuery.trim(newCategoryName)) {
   							 $(this).attr('selected', 'selected'); 
   							 //to display sku description 							 
   							 var index = "";
								index += 'desc'+getSelected('skuCategory');
									var val = $('#'+index ).val();
									jQuery("#outputtext").html(val);   
									$("#skuCategoryHistoryLink").show();         
  								}                        
							});
			var categoryId = $("#skuCategory").val();			
				$("#skuNameDisplay").load("sku_maintenanceSkuName.action?categoryId="+categoryId,
					function() {
					//to highlight the newly added sku
					$("#skuNameByCategory option").each(function() {					
						if(jQuery.trim($(this).text()) == 'Please Select') {
   							 $(this).removeAttr('selected');            
  								}
  						if(jQuery.trim($(this).text()) == jQuery.trim(newSkuName)) {
   							 $(this).attr('selected', 'selected');            
  								}                        
							});
					$("#skuNameDisplay").show();
       					 hideOptionsku();	
       					 $("#skuNameByCategory").trigger('click');	
       					 $("#successMsg").html("The changes made have been successfully updated.<br>Please note that it may take up to 24 hours for these changes to propagate through the system and come into effect.");
						 $("#successMsg").show();
					});
					$("#updateSKUType").show();
					$("#addSKUType").css("margin-left","10px");
					var categoryId=$('#skuCategory').val();					
    				$("#skuCatIdHid").val(categoryId); 					
      }        
      else if('${updateSkuCategory}' == 'true'){
   			var updatedSKUCategoryId = '${updatedSKUCategoryId}';
      		if(updatedSKUCategoryId!=""){
	      		jQuery('#skuCategory').val(updatedSKUCategoryId).attr('selected',true);
				doOnclick('skuCategory');
				obj=document.getElementById('skuCategory');
				alignButton(obj);
				$("#successMsg").html("The changes made have been successfully updated.<br>Please note that it may take up to 24 hours for these changes to propagate through the system and come into effect.");
				$("#successMsg").show();
			}
      }
		$("#updateSkuCategory").hide();
		
		$('#skuCategory').change(function() {
	   	if ($('#skuCategory').val() == 0){
	         $("#updateSKUType").hide();
	         $("#skuCategoryHistoryLink").hide();
	     } 
	    else {
	         $("#updateSKUType").show();
	        // $("#skuCategoryHistoryLink").show();
	    }
	       var count=0;
	       $("#skuCategory option:selected").each(function () {
	         count = count+1;
	        });
	    if(count>1){
	       $("#updateSKUType").hide();
	       $("#skuCategoryHistoryLink").hide();
	     	}});
	    $("#updateSKUType").click(function() {
		});
	});
</script>
</html>
