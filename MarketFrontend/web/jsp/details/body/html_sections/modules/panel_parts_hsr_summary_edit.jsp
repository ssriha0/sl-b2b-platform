<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dashboard.css" />
<style type="text/css">
#explainerToolTip {display: none; width:220px;height:auto;border: 3px solid #adaaaa; background:#fcfae6; border-radius:10px;
</style>
<script type="text/javascript">
window.onload= editLoad();
function editLoad() {
         var userAgent = navigator.userAgent.toLowerCase(); 		
		 
		 	 var panel2Def = "&ldquo;<strong>Retail Price</strong>&rdquo; is the actual retail price sold through Sears Parts Direct.";
		     var panel3Def = "&ldquo;<strong>Est. Net Provider Payment</strong>&rdquo; is the remaining amount after fees** have been applied.</br><Strong><i>**Fees may change without notice.</i></Strong>";
        	 var PartCount = '${summaryDTO.countOfParts}';
        	 var invoiceCount ='${summaryDTO.countOfInvoice}';
        	 if(PartCount == 1 || PartCount== 0){
        	   jQuery('#partsCount').html(PartCount+" Part");
        	 }else{
        	   jQuery('#partsCount').html(PartCount+" Parts");
        	 }
        	 if(invoiceCount == 1|| invoiceCount== 0){
        	    jQuery('#invoiceCount').html(invoiceCount+" Invoice");
        	 }else{
        	    jQuery('#invoiceCount').html(invoiceCount+" Invoices");
        	 }
        	 if(PartCount == 0){
        	    jQuery('#selectAll').attr("disabled", "disabled");
        	    jQuery('#addtableEdit').css("display", "none");
        	    jQuery('#countDisplayDiv').css("display", "none");
        	 }else{
        	    jQuery("#selectAll").removeAttr("disabled");
        	    jQuery("#addtableEdit").css("display","block");
        	    jQuery('#countDisplayDiv').css("display", "block");
        	 }
        	 jQuery(".glossaryItem").mouseover(function(e){
        		 jQuery("#explainerToolTip").css("position","absolute");
        	    	if(jQuery(this).attr("id") == "EstNtProvPymtHoverHoverSummaryEdit") {
        	    	 var position = jQuery("#EstNtProvPymtHoverHoverSummaryEdit").offset();
        	    	 jQuery("#explainerToolTip").html(panel3Def);
        	    	 jQuery("#explainerToolTip").css("top",position.top-343);
        	    	 jQuery("#explainerToolTip").css("left",position.left-121);
        	    	}
        	    	if(jQuery(this).attr("id") == "RetailPriceHoverSummaryEdit") {
           	    	 var position = jQuery("#RetailPriceHoverSummaryEdit").offset();
           	    	 jQuery("#explainerToolTip").html(panel2Def);
           	    	 jQuery("#explainerToolTip").css("top",position.top-343);
           	    	 jQuery("#explainerToolTip").css("left",position.left-121);
           	    	}
        	    	jQuery("#explainerToolTip").show();   
        		    }); 
        	 
        	        jQuery(".glossaryItem").mouseout(function(e){
        	        jQuery("#explainerToolTip").hide();
        		     });
        	  	
        	disableAddButton();
        	disableUpdateButton();
        	 var partExistInd= '${partExistInd}';
            var partsAvailable = '${fn:length(summaryDTO.invoiceParts)}';
           
           if(partsAvailable == 0 || partExistInd=='NO_PARTS_REQUIRED'){
        	   disableAddButton();
        	   disableUpdateButton();
        	   jQuery("#addInvoicetd").css('visibility', 'hidden');
        	   jQuery("#updatePartStatustd").css('visibility', 'hidden');
           }
           
           if(partExistInd=='NO_PARTS_REQUIRED'){
          		jQuery("#partSummaryEditDescription").html("There are no parts required for this service order.");
           }else{
        	   if(partsAvailable == 0){
             		jQuery("#partSummaryEditDescription").html("There are no parts added to the service order by provider. Parts can be added through mobile app while on-site or use 'Add Part' below.");
        		   
        	   }else{
            		jQuery("#partSummaryEditDescription").html('The parts listed below are added to the service order by provider. For a successful completion of this order,<br/>'
            				+'&nbsp;&nbsp;1. Enter missing part details (if any)<br/>'
            				+'&nbsp;&nbsp;2. Add Invoice Details and upload Proof of Invoice.<br/>'
            				+'&nbsp;&nbsp;3. Update Part Status as Installed<br/><br/>'
            				+'Note: If the added part is not installed, you can either delete part or update part status as "Not Installed".<br/>'
            				+'<a id="newPartDocumentWindow" style="text-decoration: underline; cursor: pointer; color: #00A0D2" >Click here to learn more on parts management</a>');
 
        	   }
           }
           
      
     }
     
	function showAddPart(){
		jQuery('#selectAll').removeAttr('checked');		
		hideAllDivs();
		clearErrorMsgs();
		jQuery('.invoicePartidEdit').removeAttr('checked');
		
		jQuery("#addPartDisplay").addClass("jqmWindow");
		jQuery("#addPartDisplay").css("width", "30%");
		jQuery("#addPartDisplay").css("height", "auto");
		jQuery("#addPartDisplay").css("border","3px solid lightgrey");
		jQuery("#addPartDisplay").css("marginLeft", "-350px");
		jQuery("#addPartDisplay").css("marginTop", "-50px");
		jQuery("#addPartDisplay").css("position", "fixed"); 
		jQuery("#addPartDisplay").css("zIndex",1000);
		jQuery("#addPartDisplay").css("background-color","#FFFFFF");
		jQuery("#addPartDisplay").fadeIn('slow');
		jQuery('#addPartDisplay').css('display', 'block');
		jQuery("#partNumSearchDiv").css("display","block");
		jQuery(".jqmOverlay").css("opacity",0.5);
	}
	
	function closeAddPart() {
		jQuery("#partNumber").val('');
		jQuery("#partDetails").css("display","none");
		jQuery("#errorDivParts").css("display","none");
		jQuery("#lisErrorMsg").css("display","none");
		jQuery("#manualParts").css("display","none");
		jQuery("#addPartDisplay").hide();
		jQuery.modal.close(); 
	}
    		 
        function disableCursor(){
        	jQuery("#overLay").show();
        }
		function enableCursor(){
			jQuery("#overLay").hide();
		}
	function showEditPart(invoiceId){
		hideAllDivs();
		clearErrorMsgs();
		disableCursor();
		fnWaitForResponseShow("Loading...");
		jQuery("#editPartModal").load("loadEditpart.action?invoiceId="+invoiceId+"&partSummaryMode=edit", function() {
		jQuery("#editPartsDiv").css("display","block");
		window.location.hash = '#editPartsDiv';
		jQuery('.invoicePartidEdit').removeAttr('checked');
		disableAddButton();
  		disableUpdateButton();
		jQuery('#selectAll').removeAttr('checked');
		fnWaitForResponseClose();
		enableCursor();
		});
	}
	 function searchParts() {
			jQuery("#lisErrorMsg").css("display","none");
		 	clearErrorMsgs();
			disableCursor();
			fnWaitForResponseShow("Loading...");
		 	var errorMsg = "";
			jQuery("#errorDivParts").html("");
			jQuery("#errorDivParts").css("display","none");
		    disableCursor();		    			
			var pattern = new RegExp('^[0-9.]*$');		
			var partNumber = jQuery("#partNumber").val();
			partNumber=trim(partNumber);
			jQuery("#partNumber").val(partNumber);		
			var soId = jQuery("#partSoId").val();	
			if("" == partNumber || null == partNumber){
				errorMsg = errorMsg + "Please enter a valid Part Number.<br/>";
			}

			if("" != errorMsg){
				fnWaitForResponseClose();
				enableCursor();
				jQuery("#errorDivParts").html(errorMsg);
				jQuery("#errorDivParts").css("display","block");
				window.location.hash = '#errorDivParts';
				return;
			}			

				var partNum = encodeURIComponent(partNumber);
			  	jQuery("#AddPartModal").load("searchParts.action?partNum="+partNum+"&partsSoId="+soId, function() {	  		
			  		var lisError = jQuery("#lisError").val();		
			  		if(null == lisError || ''== lisError || 'undefined'==lisError){
						jQuery("#partDetails").css("display","block");
						window.location.hash = '#partDetails';
						fnWaitForResponseClose();
						enableCursor();
			  		}
			  		else{
			  			jQuery("#errorDivParts").html(lisError);
						jQuery("#errorDivParts").css("display","block");
						window.location.hash = '#errorDivParts';
						fnWaitForResponseClose();
						enableCursor();
			  		}
				}); 
					           			          
	 }
	//Function to cleart the parts panel values
	 function clearThePartsFields(){			
	 	jQuery("#partsCoverage").val("select");
		jQuery("#source").val("select");
		jQuery("#homeDepot").hide();
		jQuery("#partNumber").val("");
		jQuery("#divisionNumber").val("");
		jQuery("#sourceNumber").val("");
		jQuery("#description").val("");
		jQuery("#homeDepot").val("Please enter the name of local part supplier.");
		jQuery("#invoiceNumber").val("");
		jQuery("#unitCost").val("");
		jQuery("#retailPrice").val("");
		jQuery("#qty").val("");
		jQuery("#partsUrl").html("");
		jQuery("#partsUrl").val("");
		jQuery("#partsUrlHidden").val("");
		/*  jQuery("#divisionNumberHidden").val("");
   		jQuery("#sourceNumberHidden").val("");
   		jQuery("#partNumberHidden").val("");  */ 
	 }
	
	function cancelForm(){
		clearThePartsFields();		
		jQuery("#errorDivParts").css("display","none");
		document.getElementById('addForm').reset();
		jQuery("#addPartDisplay").css("display","none");
		
		
	}
	//method checks if the given parameter is of type double/float
	function isNotDouble(val) {
		var isNumeric = true;
        var validChars = '0123456789';
        if(isNaN(val)){
           return true;
        }
        if ((val.indexOf(".")== 0)){
        	return true;
        }
        for(var i = 0; i < val.length; i++) {
            if(validChars.indexOf(val.charAt(i)) == -1)
                return true;
        }
        return false;
 }
	 function addPart(){
		 	var errorMsg = "";
			jQuery("#errorManualParts").html("");
			jQuery("#errorManualParts").css("display","none");
			jQuery("#lisErrorMsg").css("display","none");
		    disableCursor();
		    fnWaitForResponseShow("Saving...");
			
			var pattern = new RegExp('^[0-9.]*$');
			// var partNumberPattern = new RegExp('^[a-zA-Z0-9.]*$');		

			var partNumber = jQuery("#ManPartNumber").val();
			partNumber=trim(partNumber);
			jQuery("#ManPartNumber").val(partNumber);
			//var pn = pattern.exec(partNumber);	
			var partNumberLength = partNumber.length;
			if("" == partNumber || null == partNumber || 0 == partNumberLength){
				errorMsg = errorMsg + "Please enter a valid Part Number.<br/>";
			}
			
			if(partNumber.length > 25){
	    		errorMsg = errorMsg + "Part Number should have only 25 digits.<br/>";
	    	}
			
			var qty = jQuery("#qty").val();
			qty=trim(qty);
			 jQuery("#qty").val(qty);			 		
			if("" == qty || null == qty || 0 == qty || (isNotDouble(qty))){
				errorMsg = errorMsg + "Please enter a valid value for Quantity.<br/>";
			}
			
			var divisionNumber = jQuery("#divisionNumber").val();
			divisionNumber=trim(divisionNumber);
			jQuery("#divisionNumber").val(divisionNumber);
			var dn = pattern.exec(divisionNumber);
			var divisionNumberLength = divisionNumber.length;
			if("" == divisionNumber || null == divisionNumber || 0 == divisionNumberLength || null == dn){
				errorMsg = errorMsg + "Please enter a valid Division Number.<br/>";
			}
			if(divisionNumber.length > 4){
	    		errorMsg = errorMsg + "Division Number should have only 4 digits.<br/>";
	    	}  
			
			var sourceNumber = jQuery("#sourceNumber").val();
			sourceNumber=trim(sourceNumber);
			jQuery("#sourceNumber").val(sourceNumber);
			var sn = pattern.exec(sourceNumber);
			var sourceNumberLength = sourceNumber.length;
			if("" == sourceNumber || null == sourceNumber || 0 == sourceNumberLength || null == sn){
				errorMsg = errorMsg + "Please enter a valid Source Number.<br/>";
			}

	    	if(sourceNumber.length > 3){
	    		errorMsg = errorMsg + "Source Number should have only 3 digits.<br/>";
	    	}
	    		
			var description = jQuery("#description").val();
			description = trim(description);
			jQuery("#description").val(description);
			if("" == description || null == description){
				errorMsg = errorMsg + "Please enter a valid Part Name.<br/>";
			}
						
			var retailPrice = jQuery("#retailPrice").val();
			retailPrice=trim(retailPrice);
			jQuery("#retailPrice").val(retailPrice);
			if("" == retailPrice || null == retailPrice){
				errorMsg = errorMsg + "Please enter a valid value for Retail Price.<br/>";
			}
			else
			{
				if(retailPrice == 0.00){
					errorMsg = errorMsg + "Please enter a Retail Price greater than zero.<br/>";
				}
			}
			
					
			if("" != errorMsg){
				fnWaitForResponseClose();
				enableCursor();
				jQuery("#errorManualParts").html(errorMsg);
				jQuery("#errorManualParts").css("display","block");
				window.location.hash = '#errorManualParts';
				return;
			}
		 
		    var filterForm = jQuery("#manualPartForm").serialize();		    
		    var soId= jQuery("#partSoId").val();
		    jQuery('#AddPartModal').load('savePartDetails.action?'+filterForm+"&soId="+soId, function() {
		    	  var retailError = jQuery("#manualRetailError").val();	
		    	  if(null == retailError || '' == retailError || 'undefined'==retailError){		 
		    		  closeAddPart();
		           	  jQuery("#partSummaryEdit").load("loadPartDetails.action?soId="+soId,function() {
		           		jQuery('.invoicePartidEdit').removeAttr('checked');
		           		jQuery('#selectAll').removeAttr('checked');
		           		fnWaitForResponseClose();
		           			enableCursor();
		           			cancelForm();
							window.location.hash = '#partSummaryEdit';
			         	});
		    	  }
		    	  else{	
		    			jQuery("#lisErrorMsg").css("display","none");
		    			 jQuery("#manualParts").css("display","block");
		    		  jQuery("#errorManualParts").html(retailError);
		    		  jQuery("#errorManualParts").css("display","block");
		    		  window.location.hash = '#errorManualParts';	
		    		  enableCursor();
		    		  fnWaitForResponseClose();
		    	  }
		  
			});
	
	 }
	function showAddInvoice(){
		hideAllDivs();
		clearErrorMsgs();
		disableCursor();
		fnWaitForResponseShow("Loading...");
		jQuery("#errorDivPartsPanel").html("");
	    var isEligible = checkInvoiceInfoExists();
	    if(isEligible){
	          var invoiceId=$('.invoicePartidEdit:checked').map(function(){return this.value;}).get();
              jQuery("#AddInvoicePartModal").load("loadInvoiceOnAddInvoice.action?checkedInvoiceIds="+invoiceId,function() {
              jQuery("#addInvoice").css("display","block");
		      window.location.hash = '#addInvoice'; 
		      fnWaitForResponseClose();
		      enableCursor();
              });
		      
		   }else{
		   	  enableCursor();
		   	 fnWaitForResponseClose();
		      jQuery("#errorDivPartsPanel").css("display","block");
		      
		    }
	}
	function showAddInvoiceNo(invoiceNo){
		hideAllDivs();
		disableCursor();
		clearErrorMsgs();
		fnWaitForResponseShow("Loading...");
	    var soId= jQuery("#partSoIdInvoice").val();
	    var invoiceNo = encodeURIComponent(invoiceNo);
	    jQuery("#AddInvoicePartModal").load("loadInvoiceOnInvoiceNumber.action?invoiceNum="+invoiceNo+"&soId="+soId+"&partSummaryMode=edit",function() {
		jQuery("#addInvoice").css("display","block");
		window.location.hash = '#addInvoice';
		jQuery('.invoicePartidEdit').removeAttr('checked');
		disableAddButton();
		disableUpdateButton();
		jQuery('#selectAll').removeAttr('checked');
		fnWaitForResponseClose();
		enableCursor();
		});
	}
	function clearAddInvoiceInfo(){
	     jQuery("#errorTextAddInvoiceParts").html("");
	     jQuery("#errorTextAddInvoiceParts").css("display","none");
	     jQuery("#addInvoice").css("display","none");
	     jQuery.ajax({
	        	url: 'deletePartsDocument.action?documentId=null&invoiceId=null&source=add_invoice',
				dataType : "json",
				success: function(data) {
					
	}
		  });	
	}
	function checkInvoiceInfoExists(){
	   var invoiceId=$('.invoicePartidEdit:checked').map(function(){return this.value;}).get();
	   if(invoiceId == ''){
	      jQuery("#errorDivPartsPanel").html("Please select atleast one part to add invoice details");
	      return false;
	   }
	  var invoiceNo = $('.invoicePartidEdit:checked').map(function(){if(this.name != ''){return this.name;}}).get();
	   if(invoiceNo != ''){
	      jQuery("#errorDivPartsPanel").html("Please use part level edit to update  invoice information");
	      return false;
	   }
	   return true;
	}
	
	function showUpdatePartStatus() {
		hideAllDivs();
		clearErrorMsgs();
		disableCursor();
		fnWaitForResponseShow("Loading...");
		var errorMsg='';
		jQuery("#errorTextUpdatePartStatus").html('');
		var invoiceId = $('.invoicePartidEdit:checked').map(function() {
			return this.value;
		}).get();
		
		if(invoiceId==''){
			errorMsg = errorMsg + "Please select atleast one part<br/>";
	    }
	    if("" != errorMsg){
	    	enableCursor();
	    	fnWaitForResponseClose();
	    	jQuery("#errorDivPartsPanel").html(errorMsg);
			jQuery("#errorDivPartsPanel").css("display","block");
			window.location.hash = '#errorDivPartsPanel';
			return;
		}
		jQuery("#updatePartStatusModal")
				.load("loadUpdatePartsDetails.action?checkedInvoiceIds="+invoiceId, function() {
							fnWaitForResponseClose();
							enableCursor();
							jQuery("#updatePartsStatusDiv").css("display", "block");
							window.location.hash = '#updatePartsStatusDiv';
						});
	}
	
	function clearUpdatePartsInfo(){
	     jQuery("#updatePartsStatusDiv").css("display","none");
	}
	
	function updatePartStatus(){
		validatePartStatus();
		fnWaitForResponseShow("Updating...");
		jQuery("#errorTextUpdatePartStatus").css("display","none");
			var formData = jQuery('#updatePartsStatusForm').serialize();
			var soId = jQuery('#updatePartSoId').val();
			disableCursor();
			$.ajax({
			url: 'updatePartStatus.action',
	        type: "POST",
	        data: formData,
			dataType : "json",
           	success: function(data) {
           		jQuery("#partSummaryEdit").load("loadPartDetails.action?soId="+soId,function() {
           		jQuery('.invoicePartidEdit').removeAttr('checked');
           		jQuery('#selectAll').removeAttr('checked');
           		jQuery("#updatePartsStatusDiv").css("display","none");
				jQuery("#errorDivPartsPanel").css("display","none");
				enableCursor();
				fnWaitForResponseClose();
				window.location.hash = '#partSummaryEdit';
		}); 
           
			 }
                    
        });
	}
	
	function validatePartStatus(){
		var partNumber = jQuery("#partListSize").val();
		for(var i=1; i<=partNumber; i++)
 	    {
			var s = jQuery("#partStatusUpdate_"+i).val();
			s = jQuery.trim(s); 
			if(s == "select"){
				jQuery("#errorTextUpdatePartStatus").html('Please select a part status');
				jQuery("#errorTextUpdatePartStatus").css("display","block");
				jQuery("#"+selectBoxId).val('select');
				return;
			}
 	    }
		}
	
	function validatePartStatusTest(selectBoxId){
	var partStatus = jQuery("#"+selectBoxId).val().trim();
	if(partStatus=="Added"){
		jQuery("#errorTextUpdatePartStatus").html('Invalid Status. Please select any other status to update');
		jQuery("#errorTextUpdatePartStatus").css("display","block");
		jQuery("#"+selectBoxId).val('select');
		return;
	}
	else
		{
		jQuery("#errorTextUpdatePartStatus").html('');
		jQuery("#errorTextUpdatePartStatus").css("display","none");
		}
	}
	
	function hideAllDivs()
	{
		jQuery("#errorDivParts").css("display","none");
		jQuery("#editPartsDiv").css("display","none");
		jQuery("#addPartDisplay").css("display","none");
		jQuery("#addInvoice").css("display","none");
		jQuery("#updatePartsStatusDiv").css("display","none");
		
	}
	
	function clearErrorMsgs()
	{
		jQuery("#errorDivPartsPanel").css("display","none");
		jQuery("#errorDivParts").css("display","none");
	}
	
	function checkForInvoiceNo()
	{
		var invoiceId=$('.invoicePartidEdit:checked').map(function(){return this.value;}).get();
		var uncheckedBox=$('.invoicePartidEdit:not(:checked)').map(function(){return this.value;}).get();
		var totalNoOfChkboxes= $('.invoicePartidEdit').length;
		//if all checkboxes are uncheked, uncheck the selectall chekbox
		if(totalNoOfChkboxes==uncheckedBox.length){
			jQuery('#selectAll').attr("checked", false);	
		}
		//if all checkboxes are checked, check the selectall chekbox
		if(totalNoOfChkboxes==invoiceId.length){
			jQuery('#selectAll').attr("checked", true);	
		}
		
		if(invoiceId.length<totalNoOfChkboxes){
			jQuery('#selectAll').attr("checked", false);
		}
		//if no checkboxes are checked, uncheck the selectall checkbox and disable add invoice and update part status button
		   if(invoiceId == ''){
			   disableAddButton();
			   disableUpdateButton();
			   jQuery('#selectAll').attr("checked", false);
		}
		   else{
			   enableAddButton();
			   enableUpdateAddButton();
		   }
		 //disable the add invoice button if parts with already an invoice is selected
		var invoiceNo = $('.invoicePartidEdit:checked').map(function(){if(this.name != ''){return this.name;}}).get();
		if(invoiceNo != ''){
			disableAddButton();	
		}
 }
	
    function fnWaitForResponseShow(msg){
    	jQuery("#loadMsg").html(msg);
        jQuery("#waitPopUp").show();
 }
        function fnWaitForResponseClose(){
          jQuery("#loadMsg").html('');
         jQuery("#waitPopUp").hide();
 }
   function disableAddButton(){
	
		jQuery("#addInvoiceButton").addClass("newButton");   
		jQuery("#addInvoiceButton").css("color","grey");	
		jQuery("#addInvoiceButton").css("pointer-events","none");	
		
   }
   function disableSaveUrlButton(){
	    jQuery("#saveUrl").css("background","linear-gradient(to bottom, #F8E09E 5%, #F8E09E 100%) repeat scroll 0 0 #F8E09E");
		jQuery("#saveUrl").css("color","grey");	
		jQuery("#saveUrl").css("pointer-events","none");	  
   }
   function enableSaveUrlButton(){
	   jQuery("#saveUrl").css("color","#080108");	
	   jQuery("#saveUrl").css("pointer-events","auto");
	   jQuery("#saveUrl").css("background","linear-gradient(to bottom, #F8E09E 5%, #F9BF21 100%) repeat scroll 0 0 #F8E09E");   
   }
   function enableAddButton(){
	   
	  	jQuery("#addInvoiceButton").css("color","white");	
		jQuery("#addInvoiceButton").css("pointer-events","auto");
		jQuery("#addInvoiceButton").addClass("newButton");     
	   
	   
   }
   function disableUpdateButton(){
	  
	   	jQuery("#updatePartStatusButton").addClass("newButton");   
		jQuery("#updatePartStatusButton").css("color","grey");	
		jQuery("#updatePartStatusButton").css("pointer-events","none");	  
	   
	     
   }
   function enableUpdateAddButton(){
	  
	   	jQuery("#updatePartStatusButton").css("color","white");	
		jQuery("#updatePartStatusButton").css("pointer-events","auto");
		jQuery("#updatePartStatusButton").addClass("newButton");   
	   
	   
   }

	jQuery(document).ready( function ($) {	
		$('#newPartDocumentWindow').click(function(){
					newwindow=window.open("http://training.servicelive.com/wp-content/uploads/Provider_Summary_Tab_Parts_Management.pdf",'_blank');
					if (window.focus) {
						newwindow.focus();
						}
				});	
	           $("#selectAll").click(function () {
	               if($(this).is(":checked")) {
	                 $('.invoicePartidEdit').attr('checked','checked');
	                 enableUpdateAddButton();	
	                var invoiceNo = $('.invoicePartidEdit:checked').map(function(){if(this.name != ''){return this.name;}}).get();
	         		if(invoiceNo != ''){
	         			disableAddButton();	
	         			}
	         		else{
	         		   enableAddButton();
	         		}
	          }else{
	        	  	disableAddButton();
	          		disableUpdateButton();
	                 $('.invoicePartidEdit').removeAttr('checked');
	                }
	         });
	});
</script>
<style type="text/css">

select, option.partStatusDropDown {
    font-family: verdana,sans serif;
    font-size: 10px;
    text-align: left;
}

.invoiceNumber {
	background-color: Lightgray;
	border: 1px solid black;
	height: 25px;
	margin-top: -10px;
	position: relative;
	width: 103%;
	margin-left: -10px;
}

.invoiceSpan {
	margin-left: 3px;
	margin-top: 5px;
	position: relative;
	font-weight: bold;
	font-size: 10px;
}

.invoicevalue {
	margin-left: 100 px;
	margin-top: 5 px;
	position: absolute;
	font-size: 10px;
}

.invoiceNoText {
	font-weight: bold;
	font-size: 10px;
}

.invoiceNoValue {
	
}

.sourceDiv {
	margin-top: 10px;
	position: relative;
}

.partSourcelabel {
	font-weight: bold;
	font-size: 10px;
}

.partSourcevalue {
	width: 204px;
}

.coverageDiv {
	font-weight: bold;
	margin-left: 53px;
	margin-top: 15px;
	position: relative;
}

.partCoverageLabel {
	font-size: 10px;
	font-weight: bold;
	margin-left: 10px;
	position: relative;
}

.partCoveragevalue {
	width: 204px;
}

.partSourcevalue {
	
}

.uploadSpan {
	font-weight: bold;
	margin-left: 20px;
}

.uploadButton {
	margin-left: 20px;
	margin-top: 20px;
}

.invoiceDispalyDiv {
	height: 30px;
	margin-top: 15px;
}

.partsTableDiv {
	margin-top: 50px;
	position: relative
}

.boldSpan {
	font-weight: bold;
	font-size: 10px;
}

tr.spaceUnder>td {
	padding-bottom: 1em;
}

.fileFormatdiv {
	margin-left: 200px;
	margin-top: -60px;
}

.cancelInvoice {
	float: left;
	padding-left: 10px;
}

.saveInvoice {
	padding-left: 350px;
	position: relative;
}

.submitDiv {
	padding-top: 20px;
	position: relative;
}

.fileNameDisplay {
	margin-left: -150px;
	position: relative;
}

.invoiceCount {
	margin-left: 225px;
}

hr.addInvoiceBreak {
	border: none;
	height: 1px;
	color: #333; /* old IE */
	background-color: #333; /* Modern Browsers */
	margin-left: -200px;
}

a.atagAddEdit {
	text-decoration: underline;
}

.cancelPartsStatusUpdate {
	float: left;
	padding-left: 10px;
}

.savePartsStatusUpdate {
	padding-left: 350px;
	position: relative;
}

.countDisplayDiv {
	margin-top: -30px;
	position: absolute;
}

.sourceErrorMessage{
                    color: red;
                    font-size: 11px;
                    margin-left: 230px;
                    margin-top: -20px;
                           }  
@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css"
	;

.myButton {
    background: linear-gradient(to bottom, #F8E09E 5%, #F9BF21 100%) repeat scroll 0 0 #F8E09E;
    border: 1px solid #F8C373;
    border-radius: 3px 3px 3px 3px;
    box-shadow: 3px 3px 5px #CCCCCC;
    color: #080108;
    cursor: pointer;
    display: inline-block;
    font-family: arial;
    font-size: 11px;
    font-weight: bold;
    padding: 2px 10px;
    text-decoration: none;
    text-shadow: 0 1px 0 #F8C373;
}
.newButton {
	background-color:#33bdef;
	display:inline-block;
	cursor:pointer;
	color:#ffffff;
	font-family:Arial;
	font-size:11px;
	padding:8px 12px;
	text-decoration:none;
	border-style:none;
	font-weight: bold;
}
.newButton:hover {
	background-color:#019ad2;
	color:#ffffff;
}

.waitLayer{
			display: none;
			z-index: 999999999;
			height: 40px; 
			overflow: auto; 
			position: fixed;
			top: 250px;
			left: 45%;
			border-style:double;
			background-color: #EEEEEE;
			border-color: #BBBBBB;
			width: 125px;
			border-width: 4px;
			-webkit-box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);
			-moz-box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);
			box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);
			}
.refreshMsgIcon{
			float: right;
			font-size: 15px;
			padding: 3px;
			cursor: pointer;
			line-height: 20px;}
.refreshMsg{
			background-image: url(${staticContextPath}/images/ajax-loader.gif);
			background-position: 20px center;
			background-repeat: no-repeat;
			padding-left: 50px; 
			padding-top: 5px;
			height: 20px;
			}
td.buyer_parts_table_edit {     
		padding-top: 3px;
		padding-bottom:3px; 
	}

</style>


<span  class="partSummaryEditDescription" id="partSummaryEditDescription" style="width:100%" >


</span>
<div class="partSummaryEdit" id="partSummaryEdit" style="width:100%" >
	<div id="waitPopUp" class="waitLayer">
		<div style="padding-left: 0px; padding-top: 5px; color: #222222;">
			<div class="refreshMsg">
				<span id="loadMsg">Loading...</span>
			</div>
		</div>
	</div>
	<div id="errorDivPartsPanel"
			style="padding-left: 30px;width: 94%;border: 2px solid #F5A9A9;display:none;padding-top:5px;height:30px;
				background: url('${staticContextPath}/images/icons/50.png') no-repeat scroll 5px 5px #FBE3E4;"></div>
	 
    <input type="hidden" value="${SERVICE_ORDER_ID}" id="partSoIdInvoice">
    <table id="addtableEdit" width="99%" class="installed_parts" cellpadding="0" border="1" bordercolor="grey" style="display: block;margin-top:10px;">
         <thead>
             <tr>
				<td class="installed_parts_odd"  width="8%">
				        Select All</br>
				   <input id="selectAll" type="checkbox" style="margin-left:15px;">
				</td>
				<td class="installed_parts_odd" align="left" width="12%" style="padding-left:4px">Part Number</td>
				<td class="installed_parts_odd" align="center" width="13%">Part Name</td>
				<td class="installed_parts_odd" align="center" width="15%">Part Status</td>
				<td class="installed_parts_odd" align="center" width="12%">Invoice#</td>
				<td class="installed_parts_odd" align="center" width="8%">Proof Of Invoice</td>
				<td class="installed_parts_odd" align="center" width="5%">Qty</td>
				<td class="installed_parts_odd" align="right" width="9%" style="padding-right: 4px;">Unit Cost</td>
				<td class="installed_parts_odd" align="right" width="9%" style="padding-right: 2px;">
				<span id="RetailPriceHoverSummaryEdit" class="glossaryItem" style="color: white; font: 10px/20px Verdana, Arial, Helvetica, sans-serif;">
				Retail Price
				</span>
				</td>
				<td class="installed_parts_odd" align="right" width="9%" style="cursor: pointer; background-color: #00A0D2;padding-right: 4px;">
				    <span id="EstNtProvPymtHoverHoverSummaryEdit" class="glossaryItem" style="color: white; font: 10px/20px Verdana, Arial, Helvetica, sans-serif;">Est.&nbsp;&nbsp;Net
						Provider Payment
					</span>
				</td>		
		</thead>
		<tbody>
		  <c:forEach items="${summaryDTO.invoiceParts}" var="invoicePart" varStatus="status" >
		    <tr id="tablerowEdit${status.count}">
				<td class="partEdit" id="selectBoxEdit_${status.count}" style="" width="8%">
				    <input  onclick="checkForInvoiceNo();" style="margin-left: 15px;margin-top: 8px;" id="_${status.count}"  name="${invoicePart.invoiceNo}" class="invoicePartidEdit "type="checkbox" value="${invoicePart.invoicePartId}" >
				</td>
				<td class="buyer_parts_table_edit" id="partNoEdit_${status.count}"  style="padding-left:4px" align="left" width="12%">
				 <div style="width: 65px; word-wrap: break-word">
				     <a class="atagAddEdit" href="javascript:void(0)" onclick="showEditPart('${invoicePart.invoicePartId}');"> ${invoicePart.partNo}</a>
				     </div>
				</td>
				<td class="buyer_parts_table_edit" id="partnameEdit_${status.count}" align="center" width="13%">
				<div id="partEditPart_${status.count}" style='width: 100px; word-wrap: break-word'>
					<c:choose>
						<c:when test="${not empty invoicePart.description && fn:length(invoicePart.description) > 10}">
								${fn:substring(invoicePart.description,0,10)} <strong>...</strong>
						</c:when>
						<c:when test="${not empty invoicePart.description && fn:length(invoicePart.description) <= 10}">
								${invoicePart.description}
						</c:when>
						<c:otherwise>&nbsp;</c:otherwise>
					</c:choose>
				</div>
				</td>
				<td class="buyer_parts_table_edit" id="partStatusEdit_${status.count}" align="center" width="15%">
				  <div style='width: 100px; word-wrap: break-word'>
							${invoicePart.partStatus}
				  </div>
				</td>
				<td class="buyer_parts_table_edit" id="invoiceNoEdit_${status.count}" align="center" width="12%">
				     <div style='width: 70px; word-wrap: break-word'>
				        <c:choose>
				          <c:when test="${not empty invoicePart.invoiceNo &&(invoicePart.invoiceNo== 'NA')}">
						     ${invoicePart.invoiceNo} 
					      </c:when>
					      <c:otherwise>
					         <a class="atagAddEdit" href="javascript:void(0)" onclick="showAddInvoiceNo('${invoicePart.invoiceNo}');">${invoicePart.invoiceNo}</a>
					      </c:otherwise>
					    </c:choose>
					</div>
				</td>
				<td class="buyer_parts_table_edit" id="invoiceProof_${status.count}" align="center" width="8%">
				     <div>
				        <c:choose>
				          <c:when test="${not empty invoicePart.invoiceDocExists &&(invoicePart.invoiceDocExists== 'true')}">
						       <img src="${staticContextPath}/images/common/status-green.png"/>
					      </c:when>
					      <c:otherwise>
					           <img src="${staticContextPath}/images/common/status-red.png"/>
					      </c:otherwise>
					    </c:choose>
					</div>
				</td>
				<td class="buyer_parts_table_edit" id="qtyEdit_${status.count}" align="center" width="5%">
				     ${invoicePart.qty}
				</td>
				<td class="buyer_parts_table_edit unitCost" id="unitCost_${status.count}" align="right" width="9%" style="padding-right: 4px;">
				    <c:if test="${invoicePart.unitCost != '0.00'}">
				    	$ ${invoicePart.unitCost}
				    </c:if>
				</td>
				<td class="buyer_parts_table_edit retailPrice" id="retailPrice_${status.count}" align="right"  width="9%;" style="padding-right: 4px;">
				      $ ${invoicePart.retailPrice}
				</td>
				<td class="buyer_parts_table_edit estPayement" id="estPayement_${status.count}" align="right"  width="9%;" style="padding-right: 4px;">
				  <c:choose> 
				     <c:when test="${invoicePart.partStatus == 'Installed'}">
				                $ ${invoicePart.estProviderPartsPayment}
				     </c:when>
				     <c:otherwise>NA</c:otherwise>
				  </c:choose>
				</td>
			</tr>
			</c:forEach>
	  </tbody>
	  <tfoot>
			  <tr>
				<td colspan="9" style="padding-left: 500px;padding-top:5px;padding-bottom:5px;"><b>Sub Total for Parts</b></td>
				<td colspan="1" align="right" style="padding-top:5px;padding-bottom:5px;padding-right: 4px;" >
				  <b>$ <span class="payment" id="totalPayment" bgcolor="#FFFFFF"> 
				          <fmt:formatNumber value="${summaryDTO.totalEstproviderPayment}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
				       </span>
				  </b>
				</td>
			 </tr>
		   </tfoot>
	  </table>
	  <br/>
	  <div id="countDisplayDiv" class="countDisplayDiv">
	       <span id="partsCount" class="partsCount" style="margin-left:70px;"></span>
		   <span id="invoiceCount" class="invoiceCount"></span>
	  </div>	  
	  <br/><hr><br/> 
	  <table width="99%">
	  <tr>
	  <td width="10%" id="addInvoicetd"><input type="button" id="addInvoiceButton" onclick="showAddInvoice();" class="newButton" value="ADD INVOICE"></input></td>
	  <td width="50%" id="updatePartStatustd"><input type="button" id="updatePartStatusButton"  onclick="showUpdatePartStatus();" class="newButton" value="UPDATE PART STATUS" ></input></td>
	  <td width="10%"><input type="button" id="addPartButton"  onclick="showAddPart();" class="newButton" value="ADD PART" ></input></td>
	  </tr>
	  </table>
     <div id="addPartDiv" style="width:105%">
		<jsp:include page="panel_add_part.jsp" />
	 </div>
	 
	  <div id="editPartModal"></div>
	  <div id="AddInvoicePartModal"></div> 
	  <div id="updatePartStatusModal"></div> 
	  <div id="overLay" class="overLay"
		style="display: none; z-index: 1000; width: 100%; height: 100%; position: fixed; opacity: 0.2; filter: alpha(opacity =   20); 
		top: 0px; left: 0px; background-color: #E8E8E8; cursor: wait;">
	  </div>
	  <div id="explainerToolTip" style="z-index: 1000"></div>
</div>
