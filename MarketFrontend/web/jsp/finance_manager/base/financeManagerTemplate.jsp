<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<c:set var="currentMenu" scope="request" value="financialMgr"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />
		<link href="${staticContextPath}/javascript/confirm.css" rel="stylesheet" type="text/css" />
		
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		<style type="text/css">
		#yesButton,#noButton{
			background: url("${staticContextPath}/images/common/button-action-bg.png");
			border:1px solid #b1770b;
			color:#222;
			font-family:Arial,Tahoma,sans-serif;
			font-size:1.1em;
			font-weight:bold;
			padding:3px 10px;
			cursor: pointer;
			-moz-border-radius:5px 5px 5px 5px;
			margin-top:  -5px;
			text-align: center;
			width: 80px;
		 }
		 
		</style>		
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
		<script>
		
		jQuery(document).ready(function() {
			
			
			var tokenUrl = document.getElementById('manageAccountsTabDTO.CreditCardTokenUrl').value;
			var tokenAPICrndl = document.getElementById('manageAccountsTabDTO.CreditCardTokenAPICrndl').value;
			console.log('calling token service',tokenUrl);
			
			var authToken;
			var tokenLife;
			
			if(jQuery("#authToken").val()==null || jQuery("#authToken").val()=="" || jQuery("#tokenLife").val()<jQuery.now()){
				jQuery.ajax({ 
			    	type:'GET',
			        url: tokenUrl,
		        	headers: {
				          Authorization: tokenAPICrndl
				    },
			        success: function( response ) {
			        	authToken = parseXMLElement('ns2:token', response.documentElement)
						tokenLife = parseXMLElement('ns2:tokenLife', response.documentElement)
						jQuery('#authToken').val(authToken);
						jQuery('#tokenLife').val(tokenLife);
					    console.log('successful token: ', authToken);
					    setToken(authToken,parseInt(tokenLife)*1000 + jQuery.now());
					    
				    },
			        error: function( response ){
			          console.log('error payment: ', response);
			        }
			    });
			}
		});		   

		function setToken(authToken,tokenLife){
	    	 var setTokenURL="MarketFrontend/financeManagerController_setToken.action";

	    	jQuery.post({
		    	url: setTokenURL,
	        	data: {
				    "fmControllerModel.transferFundsDTO.authToken": authToken,
				    "fmControllerModel.transferFundsDTO.tokenLife": tokenLife
				  },
		        success: function( response ) {
					console.log('Token set in user session');
			    },
		        error: function( response ){
		          console.log('error payment: ', response);
		        }
		    });
	  	}

		

		function parseXMLElement(element, data) {
			var retVal = "";
				if (data.getElementsByTagName(element)[0]) {
					if (data.getElementsByTagName(element)[0].childNodes[0]) { 			
					retVal = data.getElementsByTagName(element)[0].childNodes[0].nodeValue;
				} else {
					retVal = null;
				}
				return retVal;
			} else {
				return null;
			}
		}
		
		
	
function tokenizeCreditCard(obj) {
			var timestamp = jQuery('#transDate').val();
			 var tokenizeCreditCardUrl=document.getElementById('manageAccountsTabDTO.CreditCardAuthTokenizeUrl').value;
			 var userName = document.getElementById("manageAccountsTabDTO.userName").value;
		    var cardNumber = document.getElementById("cardNumber").value;
			var selCardTypeId = document.getElementById("CardTypeId");
			var cardTypeValue= selCardTypeId.options[selCardTypeId.selectedIndex].value;
		   
			if (validate_credit_card(cardNumber,cardTypeValue )){
		   	var responseMessage;
		   	var responseCode;
		   	var addlResponseData;
		   	var authToken = "Bearer " +jQuery('#authToken').val();
			var apiKey = document.getElementById("manageAccountsTabDTO.CreditCardAuthTokenizeXapikey").value;
			var arr={ "acctNo" : cardNumber};
			var tokenizeCardNumber;
			var maskedCardNumber;
			
			return jQuery.ajax({

					type: 'POST',
   			        url: tokenizeCreditCardUrl,
   					data:JSON.stringify(arr),
   			       
   			     headers: {
			        	'Content-Type': 'application/json',
						'Accept':'application/json',
			        	'clientID':'SLIVE',
			        	'userid':userName,
			        	'Authorization':authToken,
						'x-api-key':apiKey
			        },
			        success: function( response ) {
			          	console.log('successful payment: ', response);
						
			          	var responseCode =response.ResponseCode;

			          	if(responseCode==00){
			          		tokenizeCardNumber = response.token;
			          		maskedCardNumber = response.mask
							
							jQuery('#correlationId').val(response.CorrelationId);
							jQuery('#maskedCardNumber').val(response.mask);
							jQuery('#tokenizeCardNumber').val(response.token);
							jQuery('#responseCode').val(responseCode);
							jQuery('#responseMessage').val(response.ResponseMessage);
							document.getElementById("cardNumber").value=response.mask;
							submitForm();
			          	}else{
			          		var errorMessage =response.messages;
			          		jQuery('#creditCardErrorMessage').val(errorMessage); 
			          		submitForm();
			          	}
						
						
			        },
			        error: function( response ){
					
			          jQuery('#creditCardErrorMessage').val(response.message); 
			          console.log('HS Tokenize API issue: ', response.message);
					  submitForm();
			        }
			});
}
else{
 submitForm();

}

}
function submitForm(){
	document.getElementById("fmOverview").action='fmManageAccounts_saveCreditCardInfo.action';
	document.getElementById("fmOverview").submit();
}	
function validate_credit_card(cardNumber,cardTypeId){

	var formatError = false;
	 var creditCardErrorMessage='';
	   if (cardNumber === ""){
		   
			creditCardErrorMessage =creditCardErrorMessage+'Credit Card number is required';
			}
			
	if (cardNumber != null
				&& cardNumber.length > 0 && (cardNumber.length > 16)) {
			creditCardErrorMessage =creditCardErrorMessage+'Card Number should not exceed 16 characters.';
		}
		
		if (cardNumber != null
				&& cardNumber.length > 0 && cardNumber.length <= 16) {
			
			if(/[^0-9]+/.test(cardNumber)){
			formatError= true;
				creditCardErrorMessage =creditCardErrorMessage+'Please enter only numeric digits for credit card number without spaces or hyphens.';
			
			}
		}
		
		
		if (!formatError && cardTypeId != null && !(cardNumber === undefined || cardNumber === null || cardNumber === "")) 
		{
			var cardNumberError = false;
			
			

			// VISA
			if (cardTypeId == 6) {
				if ((cardNumber.length != 16) || parseInt(cardNumber.substring(0, 1)) != 4) {
					cardNumberError = true;
				}
			}

			// MASTER CARD
			else if (cardTypeId == 7) {
				if (cardNumber.length!= 16
						|| parseInt(cardNumber.substring(0, 2)) < 51
						|| parseInt(cardNumber.substring(0, 2)) > 55
						|| checkForSearsMasterCard(cardNumber)) {
					cardNumberError = true;
				}
			}
			// SEARS MasterCard
			else if (cardTypeId == 4 && (!checkForSearsMasterCard(cardNumber))) {
				cardNumberError = true;
			}

			

			else if ((cardTypeId == 0)) {

				if (cardNumber.length != 16
						&& cardNumber.length!= 13) {
					cardNumberError = true;
				}
				// Sears 16 digit Card
				else if ((cardNumber.length == 16)
						&& (!checkForSearsCardOf16digits(cardNumber,cardTypeId))) {
					cardNumberError = true;
				}
				// Sears White Card
				else if ((cardNumber.length == 13)
						&& (!checkForSearsWhiteCard(cardNumber,cardTypeId))) {

					cardNumberError = true;

				}

			}
			
			// SEARS MasterCard
			else if (cardTypeId == 4 && !checkForSearsMasterCard(cardNumber)) {
				cardNumberError = true;
			}
			
			// SEARS Commercial Card
			else if (cardTypeId == 3 && (cardNumber.length != 16 || !(cardNumber.substring(0, 6) === "540553" ))){
				cardNumberError = true;
			}
			
		
			if (cardNumberError) {
				creditCardErrorMessage =creditCardErrorMessage+'Please enter a valid Card Number.';
			}
		}
	 
	 if(creditCardErrorMessage != ''){
	    jQuery('#creditCardErrorMessage').val(creditCardErrorMessage); 
		creditCardErrorMessage='';
			return false;
         }    
	    return true;
	}
	    
	   
	
	function checkForSearsMasterCard(cardNumber)
	{
		if (cardNumber.length == 16 )
		{
			var searsMasterCardInitialdigits =  ["512106","512107","518537","512108,530226"];
			
			var cardInitialSixDigits = cardNumber.substring(0, 6);
	
		for (var i = 0; i < searsMasterCardInitialdigits.length;i++)
			{
				if (cardInitialSixDigits === searsMasterCardInitialdigits[i])
				{
					
					return true;
					
				}
			}
				
		}
		return false;
	}
	
	function checkForSearsWhiteCard(cardNumber, cardTypeId)
	{
		if (cardNumber.length ==  13 && cardTypeId == 0)//Sears card has card type id of 0
		{
			var searsWhiteCardInitial2digits = ["00","01","02","03","04","05","06","07","08","09","11","20","21","34","36","40","44","48","50","54","57","60","64","70","75","80","81","82","95"]
			var cardInitialTwoDigits = cardNumber.substring(0, 2);
			for (var i = 0; i < searsWhiteCardInitial2digits.length;i++)
			{
				if (cardInitialTwoDigits === searsWhiteCardInitial2digits[i])
				{
					return true;
					
				}
			}
		}
		return false;
	}
	
	
	function checkForSearsCardOf16digits(cardNumber,cardTypeId)
	{
		if (cardNumber.length== 16 && cardTypeId == 0)//Sears card has card type id of 0
		{
			var searsCardInitial6digits = ["504994","380000","381000","382000","383000"]
			var cardInitialSixDigits = cardNumber.substring(0, 6);
		for (var i = 0; i < searsCardInitial6digits.length;i++)
			{
				if (cardInitialSixDigits === searsCardInitial6digits[i])
				{
					return true;
				}
			}

		}
		return false;
	}
	
	function disableSubmitButton() {
	
		    		document.getElementById('formNavButtonsDiv').style.display = 'none';
		    // show submitting message

					document.getElementById('disabledDepositFundsDiv').style.display = 'block';
					
	}

	function enableSubmitButton() {
		document.getElementById('formNavButtonsDiv').style.display = 'block';
		document.getElementById('disabledDepositFundsDiv').style.display = 'none';
					
	}

	//SLT-2239
	function enableFileUpload() {

			var selectedWalletControlType = jQuery("input:radio[name='manageFundsTabDTO.walletControlType']:checked").val();

			
			if(selectedWalletControlType == jQuery("#walletControlType").val()){
				jQuery("#fileList").show();
				jQuery("#walletControlAmount").val(jQuery("#walletAmount").val())
			} 
			
			jQuery("#irsFileUplod").show();	
				
					
		}
	
			
		
	function displayFileUpload(){
		var walletControlType = document.getElementsByName("manageFundsTabDTO.walletControlType");

		if(jQuery("#walletControlType").val() == 'irsLevy'){
			document.getElementById("legalHoldRadioButton").disabled = true;
		}
		else if(jQuery("#walletControlType").val()== 'legalHold') {
			document.getElementById("irsLevyRadioButton").disabled = true;
		} 
		
		var isValid=false;	
		for(var count=0;count<walletControlType.length;count++)
			{
				if(walletControlType[count].checked == true)
				{
					isValid =true;
				}
			}
			if(isValid){
				jQuery("#irsFileUplod").show();	
			}
			
	}
	function removeDocumentForWalletControl(docId)

       {      

		jQuery.ajax({

              url: 'fmManageFunds_deleteDocument.action?documentID='+docId,

              type: "POST",        

              dataType : "html",

              success: function( data ) {
               jQuery("#"+docId).hide();
             },
			  error: function(xhr, status, err) {
			   console.log("error details"+err);
			  } 

       });

       }
		
	function downloadDocumentForWalletControl(docId){
		var loadForm = document.getElementById('walletControl_upload_form');
        loadForm.action = "fmManageFunds_downloadDocument.action?documentID="+docId;
        loadForm.submit();
	}
	
   function validateWalletControlForm(){
	   var message='';
	   if (jQuery('#walletControlAmount').val() == ""){
		   
			message =message+'<p>Amount is required.</p>';
	   }else{ 
		   var amount=jQuery('#walletControlAmount').val();
		   if(isNaN(amount)){
			   
			message =message+'<p>Please enter valid amount.</p>';			
			}
	   }
	    
	    var filePath = jQuery.trim(document.getElementById('manageFundsTabDTO.walletControlFiles').value);	    
	       
	    if (filePath.length == 0) {
	    	
			message =message+'<p>Please select a file and retry</p>';
		} 		
	    
	    else {		    	
	    	  var oFiles = document.getElementById("manageFundsTabDTO.walletControlFiles").files;
	    	  var nFiles = oFiles.length;  	  
	    	  
              for (var nFileId = 0; nFileId < nFiles; nFileId++) {
              var filename= oFiles[nFileId].name;	  
              var type= oFiles[nFileId].type;         
			  if (type=='') {
				
			 	message =message+'<p>File name:<font color="blue">'+filename+'</font>  Invalid file path, must have a file extension.</p>';
				
			   } 			  
			 
			  else if (type!='image/jpeg' && type!='application/pdf' && type!='application/msword'&& type!='image/gif' ){					    
					     
			   message = message+'<p>File name:<font color="blue">'+filename+'</font>  Invalid File format.Please use one of the valid formats given below.</p>';
			   } 	
					
               var numberOfBytes= oFiles[nFileId].size;               	
                   
		      if (numberOfBytes > 2097152) // 2 mb for bytes.
		         { 		        	
		        	
				message = message+'<p>File name:<font color="blue">'+filename+'</font>  Invalid file size. Please attach a file within 2MB.</p>';
		            
		         }              
			   }  
             
	         } 
	    if(message != ''){
	    	
	        var errDivForWallet = document.getElementById('errDivForWallet');	
	        errDivForWallet.style.color = 'red';
	        errDivForWallet.style.margin = '10px 0';
	        errDivForWallet.style.padding = '5px';
	        errDivForWallet.style.border = '3px solid red';
	        errDivForWallet.style.background = '#f6c1a9';
	        errDivForWallet.innerHTML = message;
			message ='';
			return false;
         }    
	    
	    jQuery("#errDivForWallet").hide();
	    return true;
   }
   function validateForReleaseWallet(){
	 if(jQuery("#walletControlType").val()!= 'irsLevy' && jQuery("#walletControlType").val()!= 'legalHold'){
				
	   var errDivForWallet = document.getElementById('errDivForWallet');	
       errDivForWallet.style.color = 'red';
       errDivForWallet.style.margin = '10px 0';
       errDivForWallet.style.padding = '5px';
       errDivForWallet.style.border = '3px solid red';
       errDivForWallet.style.background = '#f6c1a9';
       errDivForWallet.innerHTML ='<p>Account is not under hold.</p>'; 
       return false;
      }
	 
	 return true;
    }
       
   	
	function blockWallet(){		
		if(validateWalletControlForm()){			
			jQuery("#popupirs").modal({
                onOpen: modalOpen,
                onClose: modalOnClose,
                persist: true,
                containerCss: ({ display:"block",width: "auto", height: "auto",marginTop: "200px" ,border:"none" ,marginLeft:"-250px"  })
            });			
			
			jQuery(".modalCloseImg").hide();			
			jQuery("#popupirs").css("width", "520px");
			jQuery("#popupirs").css("height", "auto");
			jQuery("#popupirs").css("top", "0px");
			jQuery("#popupirs").css("zIndex", 1000);
			jQuery("#popupirs").css("background-color","#FFFFFF");
			jQuery("#popupirs").css("border-left-color","#A8A8A8");
			jQuery("#popupirs").css("border-right-color","#A8A8A8");
			jQuery("#popupirs").css("border-bottom-color","#A8A8A8");
			jQuery("#popupirs").css("border-top-color","#A8A8A8");			
			jQuery(window).scrollTop(200);
			
		}	
	}
		
		function modalOpen(dialog) {
            dialog.overlay.fadeIn('fast', function() {
            	dialog.container.fadeIn('fast', function() {
            		dialog.data.hide().slideDown('fast');
            	});
        	});
 		}
  
   		function modalOnClose(dialog) {
       		dialog.data.fadeOut('fast', function() {
            	dialog.container.slideUp('fast', function() {
            		dialog.overlay.fadeOut('fast', function() {
            			jQuery.modal.close();
            		});
          		});
       		});
    	}		
   	 
   		
	 function successForBlockWallet() {			
		 document.getElementById('manageFundsTabDTO.onHold').value = 'true';
		 var Id= jQuery("#entityWalletControlID").val();		 
		 document.getElementById('manageFundsTabDTO.entityWalletControlID').value=Id;
		
		 jQuery('#walletControl_upload_form').attr('action','${contextPath}/fmManageFunds_saveWalletControlInformation.action');
		  document.getElementById("walletControl_upload_form").submit(); 
		} 
		
		
	 jQuery('#noButton').click(function () {		 
		   jQuery('#modalContainer a.modalCloseImg').trigger('click');		   
		});
	
	//SLt-2240
		
   function releaseWallet(){
	   if(validateForReleaseWallet()){
		   
	     if(validateWalletControlForm()){
	    	
	    	jQuery("#popuprelease").modal({
                onOpen: modalOpen,
                onClose: modalOnClose,
                persist: true,
                containerCss: ({ display:"block",width: "auto", height: "auto",marginTop: "200px" ,border:"none" ,marginLeft:"-250px"  })
            });
				
	    	jQuery(".modalCloseImg").hide();
	    	jQuery("#popuprelease").css("width", "520px");
	    	jQuery("#popuprelease").css("height", "auto");
	    	jQuery("#popuprelease").css("top", "0px");
	    	jQuery("#popuprelease").css("zIndex", 1000);
	    	jQuery("#popuprelease").css("background-color","#FFFFFF");
	    	jQuery("#popuprelease").css("border-left-color","#A8A8A8");
	    	jQuery("#popuprelease").css("border-right-color","#A8A8A8");
	    	jQuery("#popuprelease").css("border-bottom-color","#A8A8A8");
	    	jQuery("#popuprelease").css("border-top-color","#A8A8A8");
	    	jQuery(window).scrollTop(200);
	    	
	      }
	     }
			
	  }
	     
		
   
   function successForReleaseWallet() {	
	   document.getElementById('manageFundsTabDTO.onHold').value = 'false';	
	   var Id= jQuery("#entityWalletControlID").val();	   
	   document.getElementById('manageFundsTabDTO.entityWalletControlID').value=Id;
	   jQuery('#walletControl_upload_form').attr('action','${contextPath}/fmManageFunds_saveWalletControlInformation.action');
	   document.getElementById("walletControl_upload_form").submit(); 
	} 
	
			
</script>

	<script type="text/javascript">
	dojo.require("dijit.layout.ContentPane");
	dojo.require("dijit.layout.TabContainer");
	dojo.require("dijit.TitlePane");
	dojo.require("dijit.Dialog");
	dojo.require("dijit._Calendar");
	dojo.require("dijit.form.DateTextBox");
	dojo.require("dojo.date.locale");
	dojo.require("dojo.parser");
	dojo.require("dijit.form.Slider");
	dojo.require("dijit.layout.LinkPane");
	dojo.require("newco.jsutils");
	function myHandler(id,newValue){
		console.debug("onChange for id = " + id + ", value: " + newValue);
	}
     var periodicUpdaterTimer;
    function startPeriodicUpdater(event,el)
    {
        showEinEntryDiv();
        periodicUpdaterTimer = window.setTimeout(startPeriodicUpdater,500);
    }
    
    function stopPeriodicUpdater()
    {
        if(periodicUpdaterTimer)
            clearTimeout(periodicUpdaterTimer);
    }

		function checkzipcode(accountId){
		jQuery( "#zipcodeList" ).val(accountId);
		var zipcode=jQuery( "#zipcodeList option:selected" ).text();

		jQuery( "#tokenList" ).val(accountId);

		var creditCardToken=jQuery( "#tokenList option:selected" ).text();
		
	}
</script>



		<style type="text/css">
@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
</style>

		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/tooltip.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<!-- SL-20087, SL-20090 issue fix, resolving script error in Manage funds tab because of 2 versions of jQuery-->
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		 
		<script type="text/javascript"
			src="${staticContextPath}/scripts/plugins/ui.datepicker.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/Math.uuid.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/prototype.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/hideShow.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/fmReports.js"></script>


		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/slider.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/finance_mgr/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/service_provider_profile.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/admin.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/styles/plugins/ui.datepicker.css">

          <script type="text/javascript">
    			var jQuery_1_10_2 = jQuery.noConflict(true);
    			function auxNavLeadTab()
				{
				//$("#auxNav").css("z-index","-200");
				}
		</script>
		<script type="text/javascript">		
		
		
				function paginate(startIndex, endIndex, pageSize){
					var myForm = document.getElementById('pagingForm');
					document.getElementById('startIndex').value = startIndex;
					document.getElementById('endIndex').value = endIndex;
					document.getElementById('pageSize').value = pageSize;
					myForm.submit();
				}
				function selectForeignOwnedYes(){
 					document.getElementById("percentOwned").style.display='block';
 				}
 				function selectForeignOwnedNo(){
 					document.getElementById("percentOwned").style.display='none';
 				}
 				
 				function search(elementId){
 					var loadForm = document.getElementById('historySearch');
 					if(document.historySearch.radioButton[0].checked || 
 					   document.getElementById('dojoCalendarFromDate').value == null || 
 	 		 		   document.getElementById('dojoCalendarToDate').value ==null ||
 	 		 		   document.getElementById('dojoCalendarToDate').value == "" ||
 	 		 		   document.getElementById('dojoCalendarFromDate').value == ""){
 	 				   loadForm.submit();
 					}
 					else{
						var fromdate = new String(document.getElementById('dojoCalendarFromDate').value);
						fromdate = fromdate.split('/');
					    var todate = new String(document.getElementById('dojoCalendarToDate').value);
						todate = todate.split('/');
						var oneDay = 24*60*60*1000; // hours*minutes*seconds*milliseconds
					    var firstDate = new Date(fromdate[2],parseInt(fromdate[0])-1,fromdate[1]);
					    var secondDate = new Date(todate[2],parseInt(todate[0])-1,todate[1]);
					    var diffDays = Math.abs((firstDate.getTime() - secondDate.getTime())/(oneDay))+1;
						var maxNoDays=document.getElementById('noOfDays').value;
						if(document.getElementById('errorMsg')!=null)
					    document.getElementById('errorMsg').style.display = 'none';
					    if(diffDays <= maxNoDays){					    
						loadForm.submit();
					    }
					    else{
					    	document.getElementById('limitalert').style.display = 'none';
					    	document.getElementById('datelimitalert').style.display ='block';
						    document.getElementById('datelimitalertmessage').innerHTML = "The date range you choose should not exceed 1 year.";
						}
 					}  
 				}
 				
 				function hideElement(type){
	 				if (type=="interval"){
						document.getElementById("intervalDropDown").style.display='inline';
						document.getElementById("calendars").style.display='none';
	 					//dojo
	 				}else{
						document.getElementById("intervalDropDown").style.display='none';
						document.getElementById("calendars").style.display='inline';
						//document.get		 				
	 				}
 				}
 				function isCreditChecked(obj)
 				{
 					var str = obj.value;
 					var strArr = str.split(',');
 					if(strArr[1]==30)
 					{
 						if(document.getElementById("cvvCode")!=null)
 						{
 						    document.getElementById("cvvCode").style.display = "block";
	 						document.getElementById("cvvCode").value = "";
	 					}
 					}
 					else
 					{
 						if(document.getElementById("cvvCode")!=null)
 						{
 						document.getElementById("cvvCode").style.display = "none";
	 						document.getElementById("cvvCode").value = "";
	 					}
 					}
 					if(document.getElementById("depositAmount")!=null)
 					{
 						document.getElementById("depositAmount").value = "";
 					}
 				}
 
 				//Limit the text field to only numbers (with decimals)
 				function format(input){
 					var num = input.value.replace(/\,/g,'');
 					if(!isNaN(num)){
 						if(num.indexOf('.') > -1){
 							numArr = num.split('.');
 							if(numArr[1].length > 2)
 							{
 								var newnumber = Math.round(num*Math.pow(10,2))/Math.pow(10,2);
 								input.value=newnumber;
 							}
 						}
 					}
 					else 
 					{
 						input.value="0.0";		
 					}
 				}
 				
 				
 				function showEinEntryDiv(obj)
 				{
 					if(document.getElementById("manageFundsTabDTO.depositAmount")!= null)
 					{	
 						var amountVal = document.getElementById("manageFundsTabDTO.depositAmount").value;
 						if(amountVal==null || amountVal=="")
 							amountVal = 0.00;
 						var totalVal = 0.00;
 						try
 						{
 							totalVal = parseFloat(amountVal) + parseFloat(document.getElementById("buyerTotalDeposit").value);
 						}
 						catch(err)
 						{
 							totalVal=0.0;
 						}
 						
						if (document.getElementById("einStoredFlag").value != "true")
						{
							
							var buyerBitFlag=${buyerBitFlag};
							if(buyerBitFlag==true && totalVal > ${buyerThreshold})
	 				{
	 							if (document.getElementById("isSSN").value != "false"){
										jQuery("#ssn").attr("checked",true);
										
										var ssnNo = jQuery("#ssnNo").val();									
		 								jQuery("#ssnTaxPayerId").val(ssnNo);
										jQuery("#confirmSsnTaxPayerId").val(ssnNo);
										jQuery("#ssnTaxPayerIdHidden").val(ssnNo);
										jQuery('#ssnTaxPayerId').attr("disabled", true);
										jQuery('#confirmSsnTaxPayerId').attr("disabled", true);
										document.getElementById("editSSN").innerHTML ="Edit";
										jQuery('#ssnSaveInd').val('false');																					
										showSsn();
										jQuery('#editSSN').show();
										document.getElementById("ssnWarningBlock").style.display = "block";
		 								document.getElementById("einDivBlock").style.display = "block";
		 								document.getElementById("ssnBlock").style.display = "block";
		 								document.getElementById("einBlock").style.display = "none";	 							
	 							}else{
		 							jQuery("#ein").attr("checked",true);
									document.getElementById("ssnWarningBlock").style.display = "block";
		 							document.getElementById("einDivBlock").style.display = "block";
		 							document.getElementById("einBlock").style.display = "block";
		 							document.getElementById("ssnBlock").style.display = "none";
		 							document.getElementById("dateOfBirth").style.display = "none";
		 							document.getElementById("alternateIdInfoDivBlock").style.display = "none";
		 							document.getElementById("countryOfIssuanceBlock").style.display = "none";
		 							jQuery('#ssnSaveInd').val('true');	
		 							if(jQuery('#einTaxPayerId').val()==""){
										jQuery("#einTaxPayerId").unmask();
										jQuery("#einTaxPayerId").val("");
										jQuery("#einTaxPayerId").mask('99-9999999');
									}
									if(jQuery('#confirmEinTaxPayerId').val()==""){
										jQuery("#confirmEinTaxPayerId").unmask();
										jQuery("#confirmEinTaxPayerId").val("");
										jQuery("#confirmEinTaxPayerId").mask('99-9999999');
									}
	 							}
	 							jQuery('#errorMsgDiv').hide();
	 						}
	 						else
	 						{	
	 							document.getElementById("ssnWarningBlock").style.display = "none";
	 							document.getElementById("einDivBlock").style.display = "none";
	 							
	 							
	 						}
						} 
		
						if(obj!=null){
							format(obj);
						}
 					}

 				}
 				
 				function editSSN(){
					var linkValue = document.getElementById('editSSN').innerHTML;
					if(linkValue == 'Edit'){
					
						jQuery("#ssnTaxPayerId").unmask();
						jQuery("#ssnTaxPayerId").val("");
						jQuery("#ssnTaxPayerId").mask('999-99-9999');
						
						jQuery("#confirmSsnTaxPayerId").unmask();
						jQuery("#confirmSsnTaxPayerId").val("");
						jQuery("#confirmSsnTaxPayerId").mask('999-99-9999');
						
						//document.getElementById("ssnTaxPayerId").value = "";
						//document.getElementById("confirmSsnTaxPayerId").value = "";
						document.getElementById("editSSN").innerHTML = "Cancel";						
						jQuery('#ssnTaxPayerId').attr("disabled", false);
						jQuery('#confirmSsnTaxPayerId').attr("disabled", false);						
						document.getElementById("ssnSaveInd").value='true';
						
					}
					if(linkValue == 'Cancel'){
						var ssnNo = jQuery("#ssnNo").val();	
						document.getElementById("ssnTaxPayerId").value = ssnNo;
						document.getElementById("confirmSsnTaxPayerId").value = ssnNo;
						document.getElementById("editSSN").innerHTML = "Edit";						
						jQuery('#ssnTaxPayerId').attr("disabled", true);
						jQuery('#confirmSsnTaxPayerId').attr("disabled", true);						
						document.getElementById("ssnSaveInd").value='false';
					}	
				}
 				
 				//Show the div to capture alternate id info
 				function showDivAlternateIdInfo(){
 					document.getElementById("ssnDobBlock").style.display = "none";
 					document.getElementById("einBlock").style.display = "none";
 					document.getElementById("ssnBlock").style.display = "none";
 					document.getElementById("alternateIdInfoDivBlock").style.display = "block";
 					document.getElementById("dateOfBirth").style.display = "block";
 					document.getElementById("altIdDobBlock").style.display = "block";
 					document.getElementById("countryOfIssuanceBlock").style.display = "block";
 					//jQuery("#countryOfIssuanceBlock option:selected").attr("value","-1");
 					//jQuery("#alternateIdInfoDivBlock :input").val("");
 					//jQuery("#altIdDobBlock :input").val("");
 				}
 				//Show the div to capture DOB
 				function showSsn(){
 					
 					document.getElementById("altIdDobBlock").style.display = "none";
 					document.getElementById("ssnBlock").style.display = "block";
 					document.getElementById("einBlock").style.display = "none";					
					document.getElementById("dateOfBirth").style.display = "block";
					document.getElementById("ssnDobBlock").style.display = "block";
 					document.getElementById("alternateIdInfoDivBlock").style.display = "none";
 					document.getElementById("countryOfIssuanceBlock").style.display = "none";
 					
					if (jQuery("#ssnSaveInd").val()== 'false'){
					if (document.getElementById("isSSN").value != "false"){
						jQuery('#ssnTaxPayerId').attr("disabled", true);
						jQuery('#confirmSsnTaxPayerId').attr("disabled", true);
						document.getElementById("editSSN").innerHTML ="Edit";
						jQuery('#ssnDob').val("");
					}else{
						jQuery("#ssnTaxPayerId").unmask();
						jQuery("#ssnTaxPayerId").val("");
						jQuery("#ssnTaxPayerId").mask('999-99-9999');
						
						jQuery("#confirmSsnTaxPayerId").unmask();
						jQuery("#confirmSsnTaxPayerId").val("");
						jQuery("#confirmSsnTaxPayerId").mask('999-99-9999');
						
						jQuery('#ssnTaxPayerId').attr("disabled", false);
						jQuery('#confirmSsnTaxPayerId').attr("disabled", false);
						jQuery('#ssnDob').val("");
						jQuery('#editSSN').hide();
					}
					
					}
					
					
 				}
 				//Hide the DOB div and alternate TaxId div on click on EIN
 				function showEin(){
 					document.getElementById("ssnBlock").style.display = "none";
 					document.getElementById("dateOfBirth").style.display = "none";
 					document.getElementById("alternateIdInfoDivBlock").style.display = "none";
 					document.getElementById("countryOfIssuanceBlock").style.display = "none";
 					document.getElementById("einBlock").style.display = "block";
 					//jQuery("#einBlock :input").val("");
 					if(jQuery('#einTaxPayerId').val()==""){
							jQuery("#einTaxPayerId").unmask();
							jQuery("#einTaxPayerId").val("");
							jQuery("#einTaxPayerId").mask('99-9999999');
						}
					if(jQuery('#confirmEinTaxPayerId').val()==""){
							jQuery("#confirmEinTaxPayerId").unmask();
							jQuery("#confirmEinTaxPayerId").val("");
							jQuery("#confirmEinTaxPayerId").mask('99-9999999');
						}
 				}
 				
 				function maskInput(clickedId){
					jQuery('#'+clickedId).unmask();
					jQuery('#'+clickedId).val("");
					if (clickedId == "einTaxPayerId" || clickedId =="confirmEinTaxPayerId"){jQuery('#'+clickedId).mask('99-9999999');}
					if (clickedId == "ssnTaxPayerId" || clickedId == "confirmSsnTaxPayerId"){jQuery('#'+clickedId).mask('999-99-9999');}					
				}
 				function hideMsgs(){
 				
 					jQuery('#ssnWarningBlock').hide();
 					jQuery('#successMsgDiv').hide();
 					jQuery('#errorMsgDiv').hide();
 				
 				}
</script>

		<script language="JavaScript" type="text/javascript">
	
	
	function showDatepicker(clickedId){
 				jQuery('#'+clickedId).datepicker({dateFormat:'mm/dd/yy', minDate: new Date(1900, 12 - 1, 1), yearRange: '-200:+200', maxDate: new Date() }).datepicker( "show" );
 				}
	function closeFAQ()
					{
						jQuery('#popUpfaq').jqmHide();
					}
					
					function showFirstAnswer()
					{
					
					      jQuery("#firstAnswer").slideToggle("slow"); 
					      jQuery("#firstCloseArrow").hide();
					      jQuery("#firstOpenArrow").show();
					      jQuery("#firstQuestion").hide();
					      jQuery("#firstQuestionOpen").show();
					
					}
					function showFirstAnswerOpen()
					{
					
					      jQuery("#firstAnswer").slideToggle("slow"); 
					      jQuery("#firstOpenArrow").hide();
					      jQuery("#firstCloseArrow").show();
					      jQuery("#firstQuestionOpen").hide();
					      jQuery("#firstQuestion").show();
					      
					
					}
					function showSecondAnswer()
					{
					
					      jQuery("#secondAnswer").slideToggle("slow");
					      jQuery("#secondCloseArrow").hide();
					      jQuery("#secondOpenArrow").show();
					      jQuery("#secondQuestion").hide();
					      jQuery("#secondQuestionOpen").show(); 
					      
					
					}
					function showSecondAnswerOpen()
					{
					
					      jQuery("#secondAnswer").slideToggle("slow");
					      jQuery("#secondOpenArrow").hide();
					      jQuery("#secondCloseArrow").show();
					      jQuery("#secondQuestionOpen").hide();
					      jQuery("#secondQuestion").show(); 
					
					}
					
					
					
					
					function showPopUpFAQ()
					{
					jQuery("#firstOpenArrow").hide();
					      jQuery("#firstCloseArrow").show();
					      jQuery("#firstQuestionOpen").hide();
					      jQuery("#firstQuestion").show();
					      jQuery("#secondOpenArrow").hide();
					      jQuery("#secondCloseArrow").show();
					      jQuery("#secondQuestionOpen").hide();
					      jQuery("#secondQuestion").show(); 
					      jQuery("#firstAnswer").hide();
					      jQuery("#secondAnswer").hide();
					      
					      
								jQuery('#popUpfaq').jqm({modal:true, toTop: true});
								jQuery('#popUpfaq').jqmShow();
								
					}

	
	function pop_w9modal() {
		
		if(jQuery("#walletControlId").val()==3 || jQuery("#walletControlId").val()==4){
			
			jQuery("#popupwalletnotice").modal({
                onOpen: modalOpen,
                onClose: modalOnClose,
                persist: true,
                containerCss: ({ display:"block",width: "auto", height: "auto",marginTop: "70px" ,border:"none" ,marginLeft:"-250px"  })
            });
				
			jQuery(".modalCloseImg").hide();
			jQuery("#popupwalletnotice").css("width", "520px");
			jQuery("#popupwalletnotice").css("height", "auto");
			jQuery("#popupwalletnotice").css("top", "0px");
			jQuery("#popupwalletnotice").css("zIndex", 1000);
			jQuery("#popupwalletnotice").css("background-color","#FFFFFF");
			jQuery("#popupwalletnotice").css("border-left-color","#A8A8A8");
			jQuery("#popupwalletnotice").css("border-right-color","#A8A8A8");
			jQuery("#popupwalletnotice").css("border-bottom-color","#A8A8A8");
			jQuery("#popupwalletnotice").css("border-top-color","#A8A8A8");
			jQuery(window).scrollTop(70);
		}else {
			
		getNonceValue();
		jQuery(document).ready(function($) {
			$('#fillWithW9').load("w9registrationAction_isW9exist.action", function() {
				if (document.getElementById('w9isExist').value == "true" && document.getElementById('w9isExistWithSSNInd').value == "true"
						&& document.getElementById('w9isExistWithValidVal').value == "true")
				{
					 // hide submit
		    		document.getElementById('submitDiv').style.display = 'none';
		    		// show submitting message

		    		document.getElementById('disabledSubmitDiv').style.display = 'block';
					document.fmWithdrawFunds.submit();
				}
				else
				{
						$('#w9modal').jqm({modal:true, toTop: true});
						$('#w9modal').jqmShow();
					
				}
			});
		});
		}
	}
	function fnReturnFocus(){
	window.location.href = "#ein";
	}	
			
	jQuery('#okButton').click(function () {
		jQuery('#modalContainer a.modalCloseImg').trigger('click');
	});
	
</script>

	</head>

	<c:choose>
		<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
			<body class="tundra acquity simple" onload="${onloadFunction};">
		</c:when>
		<c:otherwise>
			<body class="tundra acquity" onload="${onloadFunction};auxNavLeadTab();">
		</c:otherwise>
	</c:choose>
   
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		<jsp:param name="PageName" value="FM - Main Page" />
	</jsp:include>
    
	<div id="page_margins">
	    
		<div id="page">

			<!-- START HEADER -->
			<div id="header">
				<tiles:insertDefinition name="newco.base.topnav" />
				<tiles:insertDefinition name="newco.base.blue_nav" />
				<tiles:insertDefinition name="newco.base.dark_gray_nav" />

				<tiles:insertAttribute name="pageHeader" />
			</div>

			<tiles:insertAttribute name="body" />

			<tiles:insertAttribute name="footer" />

			<s:hidden name="creditCardTokenUrl" id="manageAccountsTabDTO.CreditCardTokenUrl" value = "%{#session.CreditCardTokenUrl}" />
			<s:hidden name="creditCardTokenAPICrndl" id="manageAccountsTabDTO.CreditCardTokenAPICrndl" value = "%{#session.CreditCardTokenAPICrndl}" />
			<input type="hidden" name="fmControllerModel.transferFundsDTO.authToken" id="authToken" value="${authToken}" />
			<input type="hidden" name="fmControllerModel.transferFundsDTO.tokenLife" id="tokenLife" value="${tokenLife}" />
			<input type="hidden" name="maskedCardNumber" id="maskedCardNumber" value="${maskedCardNumber}" />
			<input type="hidden" name="tokenizeCardNumber" id="tokenizeCardNumber" value="${tokenizeCardNumber}" />
			<input type="hidden" name="responseCode" id="responseCode" value="${responseCode}" />
			<input type="hidden" name="responseMessage" id="responseMessage" value="${responseMessage}" />
			<input type="hidden" name="creditCardErrorMessage" id="creditCardErrorMessage" value="${creditCardErrorMessage}" />
			
			<s:hidden name="creditCardTokenizeAuthUrl" id="manageAccountsTabDTO.CreditCardTokenizeAuthUrl" value = "%{#session.CreditCardTokenizeAuthUrl}" />
			
			<s:hidden name="accountList" id="manageFundsTabDTO.accountList" value = "%{#session.accountList}" />

		</div>
	</div>
	<script type="text/javascript">
	function getNonceValue() { 
		document.getElementById('nonce').value = Math.uuidFast();
    }
    </script>
	</body>

</html>
