<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<html>
<style>
#editPartTable td {
	padding-bottom: 10px;
}
</style>
<script language="JavaScript" type="text/javascript"
	src="${staticContextPath}/javascript/plugins/ajaxfileupload.js"></script>
	<!-- esapi4js dependencies -->
<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/lib/log4js.js"></script>
<!-- esapi4js core -->
<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/esapi.js"></script>
<!-- esapi4js i18n resources -->
<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/resources/i18n/ESAPI_Standard_en_US.properties.js"></script>
<!-- esapi4js configuration -->
<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/resources/Base.esapi.properties.js"></script>

<script type="text/javascript" language="JavaScript">
    Base.esapi.properties.application.Name = "SL Application";
    // Initialize the api
    org.owasp.esapi.ESAPI.initialize();

</script>
<script type="text/javascript">

	function deletePartDetails(){
			disableCursor();
			fnWaitForResponseShow("Deleting...");
			var partInvoiceId = jQuery('#partInvoiceId').val();
			var soId = jQuery('#soId').val();
			$.ajax({
            url:'deletePart.action?partInvoiceId='+partInvoiceId+'&soId='+soId,
           	success: function(data) {
           	    jQuery("#partSummaryEdit").load("loadPartDetails.action?soId="+soId,function() {
           	    	fnWaitForResponseClose();
               		enableCursor();
					jQuery("#editPartsDiv").css("display","none");
					window.location.hash = '#partSummaryEdit';
				}); }                    
        });}
    
		var partStatus = jQuery('#editPartStatus').val();
		var qty = jQuery('#editQty').val();
		var partSource = jQuery('#editPartSource').val();
		var partCoverage = jQuery('#editPartCoverage').val();
		var editPartNonSearsSource = jQuery('#editPartNonSearsSource').val();
		jQuery("#errorTextEditParts").html('');
		jQuery("#editUploadButton").attr('disabled', true);
		jQuery("#editUploadButton").css("background","linear-gradient(to bottom, #F8E09E 5%, #F8E09E 100%) repeat scroll 0 0 #F8E09E");
		jQuery("#editUploadButton").css("color","grey");	
		jQuery("#editUploadButton").css("pointer-events","none");
	     if(partStatus == 'Added'){
	    	 // Adding Part Added status to part status dropdown when the current status of the part is 'Added'
	    	 jQuery("#partsStatus option:first").after("<option value='Added'>Added</option>");
	    	 // prepend("<option value='Added'>Part Added</option>");
    		 jQuery("select#partsStatus").val("Added");
	     }else{
	    	 jQuery("select#partsStatus").val(partStatus);
	     }
	     if(partSource == 'Non Sears'){
	    	 jQuery("#supplierName").css("display","block");
	     }
	     jQuery("#quantity").val(qty);
	     jQuery("select#invoiceSource").val(partSource);
	     //jQuery("select#coverage").val(partCoverage);
		 jQuery("#homeDepot").val(editPartNonSearsSource);	
		 
	 function cancelEditParts(){
		jQuery("#editPartsDiv").css("display","none");
	 	var invoiceId = jQuery('#partInvoiceId').val();
	 	var soId = jQuery('#soId').val();
		 jQuery.ajax({
	        	url: 'deletePartsDocument.action?source=cancel_edit_part&invoiceId='+invoiceId+'&soId='+soId,
				dataType : "json",
				success: function(data) {
					
				}
			});	
		 
	 }
     
     function showFileNames() {
         var fileSelected = document.getElementById('fileSelect');
         if(fileSelected){
        	 //var fileNameIndex = fileSelected.value.lastIndexOf("\\");
             //jQuery('#fileNames').html(fileSelected.value.substring(fileNameIndex + 1));
             jQuery("#editUploadButton").attr('disabled', false);
             jQuery("#editUploadButton").css("color","#080108");	
			 jQuery("#editUploadButton").css("pointer-events","auto");
			 jQuery("#editUploadButton").css("background","linear-gradient(to bottom, #F8E09E 5%, #F9BF21 100%) repeat scroll 0 0 #F8E09E");  
         }
     }
     
     function showDeletePartConfirm(){
    	 jQuery("#deletePartConfirm").css("display","block");
    	 jQuery("#deletePartLink").css("display","none");
     }
     
     function cancelDeletePartConfirm(){
    	 jQuery("#deletePartConfirm").css("display","none");
    	 jQuery("#deletePartLink").css("display","block");
     }
     
     /* function validateStatusSelection(){
    	 var partStatus = jQuery('#partStatus').val();
    	 var status = jQuery('#partsStatus').val();
    	 if(status == 'Added' && partStatus != 'Added'){
    		 jQuery("#statusError").css("display","block");
    	 }else{
    		 jQuery("#statusError").css("display","none");
    	 }
     } */
     
     function showTextAreaForNonSears(){
    	 var source = jQuery('#invoiceSource').val();
    	 var invoiceNumber = jQuery('#invoiceNumber').val();
    	 if(source == 'Non Sears'){
	    	 jQuery("#homeDepot").val("Please enter the name of local part supplier.");
    		 jQuery("#supplierName").css("display","block");
    	 }else{
    		 jQuery("#supplierName").css("display","none");
    	 }
    	 if(source == 'Truck Stock' && invoiceNumber === ""){
    		 jQuery('#invoiceNumber').val('NA');
    	 }
    	 if((source == 'Non Sears'||source == 'Sears'||source == '-1') && invoiceNumber == 'NA'){
    		 jQuery('#invoiceNumber').val('');
    	 }
     }
     
     /* function selectInvoiceNotAvailable(){
    	 if($('#invoiceNumberNotAvailableInd').attr('checked' ) == true){
    		 jQuery('#invoiceNumber').val('NA');
    		 jQuery("#invoiceSource").prepend("<option value='Truck Stock'>Truck Stock</option>");
    		 jQuery("select#invoiceSource").val("Truck Stock");
    		 jQuery("#supplierName").css("display","none");
    		 jQuery("#invoiceSource").attr("disabled","disabled");
    	}else{
    		jQuery('#invoiceNumber').val('');
    		jQuery("#invoiceSource option[value=Truck Stock]").remove();
    		jQuery("#invoiceSource").removeAttr("disabled");
    	}
     } */
     
     function clickClear(thisfield, defaulttext) {
      	if (thisfield.value == defaulttext) {
       	thisfield.value = "";
       	}
 	}
 	function clickRecall(thisfield, defaulttext) {
 		if (trim(thisfield.value) == "") {
 		thisfield.value = defaulttext;
 	}else{
 		thisfield.value = trim(thisfield.value);
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
 	
 	function deleteEditPartsDocument(documentId){
 		jQuery("#errorTextEditPartsUpload").html('');
 		disableCursor();
		fnWaitForResponseShow("Removing...");
 		var invoiceId = jQuery('#partInvoiceId').val();
 		var soId = jQuery('#soId').val();
		jQuery.ajax({
        	url: 'deletePartsDocument.action?documentId='+documentId+'&invoiceId='+invoiceId+'&source=edit_part&soId='+soId,
			success: function(data) {
				jQuery("#editPartsUploadedFiles").load("loadEditPartUploadedFiles.action", function() {
					fnWaitForResponseClose();
		        	enableCursor();	
            		
            	});
			},
			error: function(data){
				fnWaitForResponseClose();
	        	enableCursor();
		
			}
		});	
 	}
 	
 	function viewEditPartsDocument(documentId){
 		var loadForm = document.getElementById('editPartsViewDocForm');
        loadForm.action = 'viewPartsDocument.action?editDocId='+documentId;
        loadForm.submit();
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
 	function editPart(){
 		jQuery("#errorTextEditPartsUpload").html('');
 		var errorMsg = "";
 		var isValid = true;
 		var partStatus = jQuery('#partsStatus').val();
 		var unitCost = jQuery('#unitCost').val();
 		var quantity = jQuery('#quantity').val();
 		var invoiceNumber = jQuery('#invoiceNumber').val();
 		var invoiceSource = jQuery('#invoiceSource').val();
 		var nonSearsSource = jQuery('#homeDepot').val();
 		var divisionNumber = jQuery('#divisionNumberToDisplay').val();
 		var sourceNumber = jQuery('#sourceNumberToDisplay').val();
 		var retailPriceToDisplay = jQuery('#retailPriceToDisplay').val();
 		var partNoSource = jQuery('#partNoSourceVal').val();
 		var soId = jQuery('#soId').val();
 		
 		var pattern = new RegExp('^[0-9.]*$');
 		
 		if(partStatus == '-1'){
 			errorMsg = errorMsg + "Please select the Part Status.<br/>";
	    	isValid = false;
 		}if(unitCost == "0.00" || unitCost == ""){
 			errorMsg = errorMsg + "Please enter a Unit Cost value greater than 0.<br/>";
	    	isValid = false;
 		}if("" == quantity || null == quantity || 0 == quantity || (isNotDouble(quantity))){
 			errorMsg = errorMsg + "Please enter a valid value for Quantity.<br/>";
	    	isValid = false;
 		}if(invoiceNumber == ""){
 			errorMsg = errorMsg + "Please enter the Invoice Number.<br/>";
	    	isValid = false;
 		}if(invoiceSource == '-1'){
 			errorMsg = errorMsg + "Please select the Source.<br/>";
	    	isValid = false;
 		}if(nonSearsSource == 'Please enter the name of local part supplier.' && invoiceSource == 'Non Sears'){
 			errorMsg = errorMsg + "Please enter the name of local part supplier.<br/>";
	    	isValid = false;
 		}
 		
 		if(partNoSource == 'MANUAL'){
 			divisionNumber=trim(divisionNumber);
			jQuery('#divisionNumberToDisplay').val(divisionNumber);
			var dn = pattern.exec(divisionNumber);
			var divisionNumberLength = divisionNumber.length;
			
	 		if("" == divisionNumber || null == divisionNumber || 0 == divisionNumberLength || null == dn){
	 			errorMsg = errorMsg + "Please enter a valid Division Number.<br/>";
		    	isValid = false;
	 		}
	 		
	 		sourceNumber=trim(sourceNumber);
			jQuery('#sourceNumberToDisplay').val(sourceNumber);
			var sn = pattern.exec(sourceNumber);
			var sourceNumberLength = sourceNumber.length;
			
	 		if("" == sourceNumber || null == sourceNumber || 0 == sourceNumberLength || null == sn){
	 			errorMsg = errorMsg + "Please enter a valid Source Number.<br/>";
		    	isValid = false;
	 		}
 		}
 		var finalRetailPrice = "";
			if(jQuery('#retailPriceToDisplay').length){
				finalRetailPrice = jQuery('#retailPriceToDisplay').val();
			}else if(jQuery('#retailPriceToDisplayNonEdit').length){
				finalRetailPrice = jQuery('#retailPriceToDisplayNonEdit').val();
			}
			jQuery("#finalRetailPrice").val(finalRetailPrice);
 		if(finalRetailPrice == "0.00" || finalRetailPrice == ""){
 			errorMsg = errorMsg + "Please enter a Retail Price value greater than 0.<br/>";
	    	isValid = false;
 		}	
 		if(true == isValid){
 			if(unitCost > 999.99){
 	 			isValid = false;
 	 			unitCostPopUp();
 	 		}
 		}	
 				
 		if(true == isValid)
	    	{		
 		 	jQuery("#errorTextEditParts").html('');
 			var formData = jQuery('#editPartsForm').serialize();
		    disableCursor();
		    fnWaitForResponseShow("Saving...");
 			jQuery.ajax({
 	        	url: 'editpart.action',
 	        	type: "POST",
 	        	data: formData,
 	        	dataType : "json",
 				success: function(data) { 				
 					 var retailError = data.responseMessage;	 					
 			    	  if(null == retailError || '' == retailError){ 			    		 
 					 	jQuery("#partSummaryEdit").load("loadPartDetails.action?soId="+soId,function() {
 				        enableCursor();
 				        fnWaitForResponseClose();
 						jQuery("#editPartsDiv").css("display","none");
 						window.location.hash = '#partSummaryEdit';
 					 	});
 						}
 			    	  else{
 			    		 jQuery("#errorTextEditParts").html($ESAPI.encoder().encodeForHTML(retailError));
 			    		 jQuery("#errorTextEditParts").css("display","block");
 			    		 jQuery("#editPartsDiv").css("display","block");
 			    		// disableSaveEditButton();
 			    		 enableCursor();
 			    		 fnWaitForResponseClose();
 			    	  }
 				}
 			});
 		}
 		else{
 			errorMsg = errorMsg + "<br/>";
 			if("" != errorMsg){
 				jQuery("#errorTextEditParts").html(errorMsg);
 				enableCursor();
			    fnWaitForResponseClose();
 				return;
 			}
 		}
	}
 	
 	/* function validateAndUploadFiles(){
		var formValues = jQuery('#editPartsForm').serializeArray();
		jQuery.ajaxFileUpload({
	        url:'uploadPartsDocuments.action',
	        secureuri:false,
	        type: "POST",
	        data: formValues,
	        fileElementId:'files',
	        dataType : 'json',
	        contentType: false,
	        processData: false,
	        success: function (data){
	        	alert("success");
	        	alert(data.documentIds);
	        	//editPart();
	        },
			error: function (data){
	        	alert("error");
	        	alert(data.documentIds);
	        	//editPart();
	        }
		});
	} */
	
	function uploadPartsFile(){
		//document.getElementById('editUploadButton').innerText = 'Uploading...';
		jQuery("#errorTextEditPartsUpload").html('');
		disableCursor();
		fnWaitForResponseShow("Uploading...");
		jQuery("#editUploadButton").attr('disabled', true);
		jQuery("#editUploadButton").css("background","linear-gradient(to bottom, #F8E09E 5%, #F8E09E 100%) repeat scroll 0 0 #F8E09E");
		jQuery("#editUploadButton").css("color","grey");	
		jQuery("#editUploadButton").css("pointer-events","none");
		jQuery("#errorTextEditPartsUpload").html('');
		var fieldVal = jQuery('#fileSelect').val();
		var fileNameIndex = fieldVal.lastIndexOf("\\");
		fieldVal = fieldVal.substring(fileNameIndex + 1);
		fieldVal = fieldVal.replace(/%/g, "-prcntg-");
		fieldVal = encodeURIComponent(fieldVal);
		var formValues = jQuery('#uploadPartsFilesForm').serializeArray();
		var soId = $("#soId").val();
		var partInvoiceId = jQuery('#partInvoiceId').val();
		if (fieldVal != null && fieldVal.length != 0){
			disableCursor();
			jQuery.ajaxFileUpload({
	            url:'uploadPartsDocuments.action?fileName='+fieldVal+"&soId="+soId+"&partInvoiceId="+partInvoiceId,
	            secureuri:false,
	            type: "POST",
	            data: formValues,
	            dataType : "json",
	            fileElementId:'fileSelect',
	            success: function(data){
	            	var responseMsg = data.responseMessage;//'${responseMessage}';
	            	if(responseMsg!=null && responseMsg!='' && responseMsg!=' ' && responseMsg != '-1'){
	            		fnWaitForResponseClose();
		        		enableCursor();
	            		jQuery("#errorTextEditPartsUpload").html(responseMsg);
	            	}
	            	//alert(data.documentIds);
	            	else{
	            		jQuery("#errorTextEditPartsUpload").html('');
	            		jQuery("#editPartsUploadedFiles").load("loadEditPartUploadedFiles.action", function() {
	            			fnWaitForResponseClose();
	    	        		enableCursor();
		            	});
	            		jQuery("#fileSelect").val('');
	            	}
	        		//jQuery('#fileNames').html('');
	        		//document.getElementById('editUploadButton').innerText = 'Upload';
	            },
				error: function(data){
					//jQuery("#errorTextEditPartsUpload").html("error");
					var responseMsg = data.responseMessage;//'${responseMessage}';
					
	            	if(responseMsg!=null && responseMsg!='' && responseMsg!=' ' && responseMsg != '-1'){
	            		fnWaitForResponseClose();
		        		enableCursor();
	            		jQuery("#errorTextEditPartsUpload").html("ERROR"+responseMsg);
	            	}
	            	//alert(data.documentIds);
	            	else{
	            		jQuery("#errorTextEditPartsUpload").html('');
	            		jQuery("#editPartsUploadedFiles").load("loadEditPartUploadedFiles.action", function() {
	            			fnWaitForResponseClose();
	    	        		enableCursor();
		            	});
	            		jQuery("#fileSelect").val('');	
	            	}
	            		
	            				}
			});
		}else{
			//$("#uploadResp").html("Please select a file to upload.");
			//document.getElementById('editUploadButton').innerText = 'Upload';
		}
		jQuery("#editUploadButton").attr('disabled', true);
		jQuery("#editUploadButton").css("background","linear-gradient(to bottom, #F8E09E 5%, #F8E09E 100%) repeat scroll 0 0 #F8E09E");
		jQuery("#editUploadButton").css("color","grey");	
		jQuery("#editUploadButton").css("pointer-events","none");
	}
		
	
	function unitCostPopUp(){
		jQuery("#editPartUnitCost").addClass("jqmWindow");
		jQuery("#editPartUnitCost").css("width", "39.5%");
		jQuery("#editPartUnitCost").css("height", "auto");
		jQuery("#editPartUnitCost").css("border","3px solid lightgrey");
		jQuery("#editPartUnitCost").css("marginLeft", "-300px");
		jQuery("#editPartUnitCost").css("position", "fixed"); 
		jQuery("#editPartUnitCost").css("zIndex",1000);
		jQuery("#editPartUnitCost").css("background-color","#FFFFFF");
		$("#editPartUnitCost").jqm({modal:true});
		jQuery("#editPartUnitCost").fadeIn('slow');
		jQuery('#editPartUnitCost').css('display', 'block');
		$("#editPartUnitCost").jqmShow();
		jQuery(".jqmOverlay").css("opacity",0.5);
	}
	function clearUnitCost(){
		$("#editPartUnitCost").jqmHide();
		var unitCost = jQuery("#unitCost").val();
		var zero = "0.00";
		var isValid= 1;
		    jQuery("#unitCost").val(zero);
		    jQuery("#validUnitCost").val(isValid);
		    enableSaveEditButton();
			jQuery("#errorTextEditParts").html('');
	}
	
	function saveUnitCost(){
		$("#editPartUnitCost").jqmHide();
		var errorMsg = "";
		var isValid= 1;
		var unitCost = jQuery("#unitCost").val();
		unitCost=trim(unitCost);
		if(unitCost > 9999.99){
			errorMsg = errorMsg +"You have entered Unit Cost greater than the maximum allowed value of $9999.99 for your part. Please correct the value and try again.";
			jQuery("#unitCost").css('color','red');
			disableSaveEditButton();
			jQuery("#errorTextEditParts").html(errorMsg);
				enableCursor();
		    fnWaitForResponseClose();
				return;
	 }
		else{	
			jQuery("#unitCost").css('color','black');
	 		var soId = jQuery('#soId').val();
			jQuery("#errorTextEditParts").html('');
 			var formData = jQuery('#editPartsForm').serialize();
		    disableCursor();
		    fnWaitForResponseShow("Saving...");
 			jQuery.ajax({
 	        	url: 'editpart.action',
 	        	type: "POST",
 	        	data: formData,
 	        	dataType : "json",
 				success: function(data) { 				
 					 var retailError = data.responseMessage;	 					
 			    	  if(null == retailError || '' == retailError){ 			    		 
 					 	jQuery("#partSummaryEdit").load("loadPartDetails.action?soId="+soId,function() {
 				        enableCursor();
 				        fnWaitForResponseClose();
 						jQuery("#editPartsDiv").css("display","none");
 						window.location.hash = '#partSummaryEdit';
 					 	});
 						}
 			    	  else{
 			    		 jQuery("#errorTextEditParts").html($ESAPI.encoder().encodeForHTML(retailError));
 			    		 jQuery("#errorTextEditParts").css("display","block");
 			    		 jQuery("#editPartsDiv").css("display","block");
 			    		// disableSaveEditButton();
 			    		 enableCursor();
 			    		 fnWaitForResponseClose();
 			    	  }
 				}
 			});
 			}
	}
	
	 function validatePrices(e){
		var errorMsg = "";
		var unicode=e.keyCode? e.keyCode : e.charCode;
		 if(unicode!=9){
			var unitCost = jQuery("#unitCost").val();
			unitCost=trim(unitCost);
			//Added
			if(unitCost <= 9999.99){
				jQuery("#unitCost").css('color','black');
				enableSaveEditButton();
				jQuery("#errorTextEditParts").html('');
			}
		}
	} 
	 function validateUnitCost(){
			var errorMsg = "";
			var unitCost = jQuery("#unitCost").val();
			unitCost=trim(unitCost);
				//Added
				if(unitCost > 9999.99){
						errorMsg = errorMsg +"You have entered Unit Cost greater than the maximum allowed value of $9999.99 for your part. Please correct the value and try again.";
						jQuery("#unitCost").css('color','red');
						disableSaveEditButton();
						jQuery("#errorTextEditParts").html(errorMsg);
						enableCursor();
					    fnWaitForResponseClose();
						return;
			}
				else{
					jQuery("#unitCost").css('color','black');
				}
		} 

		function amountRoundoffUnitPrice(){
			var unitCost = jQuery("#unitCost").val();
			 if(unitCost == 0){
			   var zero = "0.00";
			   jQuery("#unitCost").val(zero);
			 }else{
				 jQuery("#unitCost").val(addOnObject.fmtMoney(unitCost));
			 }
		}
		
		
		function isNumber(evt) {
			var source = jQuery('#invoiceSource').val();
			if(source == 'Truck Stock'){
				return true;
			}
		    evt = (evt) ? evt : window.event;
		    var charCode = (evt.which) ? evt.which : evt.keyCode;
		    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		        return false;
		    }
		    return true;
		}
		
		function isValidNumber(evt) {
			var unitCost = jQuery("#unitCost").val();
			 var charCode = (evt.which) ? evt.which : event.keyCode;
			    if (charCode == 46 && unitCost.split('.').length>1){
			        return false;
			    }
			    if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57))
			        return false;
			    return true;
		}
		
		
		$("#divisionNumberToDisplay").keypress(function (e){
		  	if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57)){
		   		return false;
		 	}
		 });
		 $("#sourceNumberToDisplay").keypress(function (e){
		  	if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57)){
		   		return false;
		 	}
		 });
		 function amountRoundoffRetailPrice(){
			 var retailPrice = jQuery("#retailPriceToDisplay").val();
			 if(retailPrice == 0){
			   var zero = "0.00";
			  jQuery("#retailPriceToDisplay").val(zero);
			 }else{
			  jQuery("#retailPriceToDisplay").val(addOnObject.fmtMoney(retailPrice));
			 }
		}
		 function isValidNumberRetailPrice(evt) {
				var retailPrice = jQuery("#retailPriceToDisplay").val();
				 var charCode = (evt.which) ? evt.which : event.keyCode;
				    if (charCode == 46 && retailPrice.split('.').length>1){
				        return false;
				    }
				    if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57))
				        return false;
				    return true;
		}
		 
	$("#quantity").keypress(function (e){
		  	 if( e.which!=8 && e.which!=0 && (e.which<=48 || e.which>57)){
		  		 if(e.which == 48){
		  			var qty = $("#quantity").val();
		  			if(0 == qty){
		  				return false;
		  			}
		  			else{
		  				return true;
		  			}
		  		 }
		   		return false;
		 	} 

		 });
	
	function disableSaveEditButton(){
	    jQuery("#saveEditButton").css("background","linear-gradient(to bottom, #F8E09E 5%, #F8E09E 100%) repeat scroll 0 0 #F8E09E");
		jQuery("#saveEditButton").css("color","grey");	
		jQuery("#saveEditButton").css("pointer-events","none");	  
   }
   function enableSaveEditButton(){
	   jQuery("#saveEditButton").css("color","#080108");	
	   jQuery("#saveEditButton").css("pointer-events","auto");
	   jQuery("#saveEditButton").css("background","linear-gradient(to bottom, #F8E09E 5%, #F9BF21 100%) repeat scroll 0 0 #F8E09E");   
   }
   
 	</script>
<head>
</head>
<body>
	<div id="editPartsDiv" style="display: none; border: 1px solid grey;">

		<input type="hidden" value="${RETAIL_PRICE_ERROR}" id="retailError" />

		<div class="submenu_head" style="height: auto;">Edit Part Number
			${editPartDetails.partNumber}</div>
		<form id="editPartsForm" method="POST">
			<input type="hidden" id="editPartStatus" name="editPartStatus"
				value="${editPartDetails.partStatus}" /> <input type="hidden"
				id="editQty" name="qty" value="${editPartDetails.qty}" /> <input
				type="hidden" id="editPartSource" name="partSource"
				value="${editPartDetails.partSource}" /> <input type="hidden"
				id="editPartNonSearsSource" name="editPartNonSearsSource"
				value="${editPartDetails.nonSearsSource}" /> <input type="hidden"
				id="editPartCoverage" name="partCoverage"
				value="${editPartDetails.partCoverage}" /> <input type="hidden"
				id="claimStatus" name="editPartVO.claimStatus"
				value="${editPartDetails.claimStatus}" /> <input type="hidden"
				id="autoAdjudicationInd" name="editPartVO.autoAdjudicationInd"
				value="${editPartDetails.autoAdjudicationInd}" /> <input
				type="hidden" id="soId" name="soId" value="${editPartDetails.soId}" />
			<input type="hidden" id="claimStatus" name="editPartVO.partInvoiceId"
				value="${editPartDetails.partInvoiceId}" /> <input type="hidden"
				id="partInvoiceId" name="partInvoiceId"
				value="${editPartDetails.partInvoiceId}" /> <input type="hidden"
				id="partNoSourceVal" name="partNoSourceVal"
				value="${editPartDetails.partNoSource}" /> <span
				id="errorTextEditParts" style="color: #FF0000;"></span>
			<table width="700px">
				<tbody>
					<tr>
						<td width="25%"><b>Part Number:</b>
							${editPartDetails.partNumber}</td>
						<td width="75%"><b>Part Name:</b>
							${editPartDetails.partDescription}</td>
					</tr>
				</tbody>
			</table>
			<br>
			<table id="editPartTable">
				<tbody>
					<tr>
						<td width="20%" align="left"><b>Part Status<span
								style="margin-left: 3.1%; position: absolute;">:</span></b></td>
						<td width="30%"><select style="width: 200px;"
							id="partsStatus" name="editPartVO.partStatus">
								<option value="-1">-- Select One --</option>
								<c:forEach items="${editPartDetails.partStatusTypes}"
									var="partStatusTypes">
									<option value="${partStatusTypes.descr}">${partStatusTypes.type}</option>
								</c:forEach>
						</select></td>
						<td>
							<%-- <div id="statusError"
								style="width: 200px; color: red; display: none;">Please
								select a status other than Part Added.</div> --%>
						</td>
					</tr>

					<tr>
						<td width="20%" align="left"><b>Retail Price:<span
								style="margin-left: 2.4%; position: absolute;">$</span></b></td>
						<td width="40%"><input type="hidden" id="finalRetailPrice"
							name="editPartVO.retailPrice"
							value="${editPartDetails.retailPrice}" /> <c:choose>
								<c:when
									test="${editPartDetails.partNoSource == 'MANUAL' || editPartDetails.retailPrice == '0.00'}">
									<input type="text" value="${editPartDetails.retailPrice}"
										id="retailPriceToDisplay" maxlength="9" style="width: 193px"
										class="shadowBox grayText"
										onblur="amountRoundoffRetailPrice();"
										onkeypress="return isValidNumberRetailPrice(event);">
								</c:when>
								<c:otherwise>
									<input type="text" value="${editPartDetails.retailPrice}"
										id="retailPriceToDisplayNonEdit" disabled="disabled"
										style="width: 192px; color: grey" class="shadowBox grayText">
								</c:otherwise>
							</c:choose></td>
						<td width="40%">
					</tr>

					<tr>
						<td width="20%" align="left"><b>Unit Cost:<span
								style="margin-left: 3.8%; position: absolute;">$</span></b></td>
						<td width="40%">
							<table style="margin_bottom: 0px">
								<tbody>
									<tr>
										<td><input type="text" id="unitCost"
											value="${editPartDetails.unitCost}"
											name="editPartVO.unitCost" style="width: 82px"
											onblur="amountRoundoffUnitPrice();validateUnitCost();"
											onkeypress="return isValidNumber(event);"
											onkeyup="validatePrices(event)";
											class="shadowBox grayText" size="10" maxlength="7"></td>
										<td style="padding-left: 10px;"><b>Qty:</b></td>
										<td style="padding-left: 16px;"><input id="quantity"
											class="shadowBox grayText" type="text" name="editPartVO.qty"
											value="${editPartDetails.qty}" size="5" maxlength="3"></td>
										</td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td width="20%" align="left"><b>Source<span
								style="margin-left: 6%; position: absolute;">:</span></b></td>
						<td width="40%"><select name="editPartVO.partSource"
							id="invoiceSource" style="width: 200px;"
							onchange="showTextAreaForNonSears()">
								<option value="-1">-- Select One --</option>
								<c:forEach items="${editPartDetails.sourceTypes}"
									var="partSourceTypes">
									<option value="${partSourceTypes.partSourceValue}">${partSourceTypes.partSourceValue}</option>
								</c:forEach>
						</select></td>
						<td width="40%">
							<div id="supplierName" style="width: 200px; display: none;">
								<textarea
									onkeydown="countAreaChars(this.form.homeDepot, 50, event);"
									onkeyup="countAreaChars(this.form.homeDepot, 50, event);"
									onfocus="clickClear(this,'Please enter the name of local part supplier.')"
									name="editPartVO.nonSearsSource"
									onblur="clickRecall(this,'Please enter the name of local part supplier.')"
									style="display: inline; resize: none; position: relative; right: -40px; width: 180px; overflow: hidden; height: 27px; font-size: 11px; top: 0px;"
									cols="24" rows="1" id="homeDepot"
									value="${editPartDetails.nonSearsSource}"></textarea>
							</div>
						</td>
					</tr>
					<tr>
						<td width="20%" align="left"><b>Invoice Number<span
								style="margin-left: 0.6%; position: absolute;">:</span></b></td>
						<td width="40%"><input type="text"
							name="editPartVO.invoiceNumber"
							value="${editPartDetails.invoiceNumber}" id="invoiceNumber"
							maxlength="15" style="width: 192px" class="shadowBox grayText"
							onblur="trimInvoiceNumber();"></td>
						<td width="40%"></td>
					</tr>


					<tr>
						<td width="20%" align="left"><b>Division Number<span
								style="margin-left: 0.4%; position: absolute;">:</span></b></td>
						<td width="40%"><c:choose>
								<c:when test="${editPartDetails.partNoSource == 'MANUAL'}">
									<input type="text" name="editPartVO.divisionNumber"
										value="${editPartDetails.divisionNumber}"
										id="divisionNumberToDisplay" minlength="4" maxlength="4"
										style="width: 192px" class="shadowBox grayText">
								</c:when>
								<c:otherwise>
									<input type="text" name="editPartVO.divisionNumber"
										value="${editPartDetails.divisionNumber}"
										id="divisionNumberToDisplayNonEdit" disabled="disabled"
										style="width: 192px; color: grey" class="shadowBox grayText">
								</c:otherwise>
							</c:choose></td>
						<td width="40%">
					</tr>
					<tr>
						<td width="20%" align="left"><b>Source Number<span
								style="margin-left: 1%; position: absolute;">:</span></b></td>
						<td width="40%"><c:choose>
								<c:when test="${editPartDetails.partNoSource == 'MANUAL'}">
									<input type="text" name="editPartVO.sourceNumber"
										value="${editPartDetails.sourceNumber}"
										id="sourceNumberToDisplay" minlength="3" maxlength="3"
										style="width: 192px" class="shadowBox grayText">
								</c:when>
								<c:otherwise>
									<input type="text" name="editPartVO.sourceNumber"
										value="${editPartDetails.sourceNumber}"
										id="sourceNumberToDisplayNonEdit" disabled="disabled"
										style="width: 192px; color: grey" class="shadowBox grayText">
								</c:otherwise>
							</c:choose></td>
						<td width="40%">
					</tr>
				</tbody>
			</table>
		</form>
		<div>
			<span id="errorTextEditPartsUpload"
				style="color: #FF0000; margin-left: 13px;"></span>
			<form id="uploadPartsFilesForm" enctype="multipart/form-data"
				method="POST">
				<input type="hidden" id="docSoId" name="soId"
					value="${editPartDetails.soId}" />
				<table style="margin-bottom: 0px;">
					<tbody>
						<tr>
							<td width="20%" align="left"><b>Upload&nbsp;Invoice</b></td>
							<td width="34%"><input type="file" id="fileSelect"
								name="fileSelect" size="20" onchange="showFileNames();">
							</td>
							<%-- <td width="13%" align="center">
								<div style="width: 80px; word-wrap: break-word;">
									<span id="fileNames" style="font-size:11px"></span>
								</div>
							</td> --%>
							<td width="13%"><input type="button" id="editUploadButton"
								onclick="uploadPartsFile();" value="Upload" class="myButton" />
							</td>
							<td width="33%" align="center">Accepted File Formats :<br>.doc|docx|.pdf|.gif|.jpeg|.jpg|.pjpeg|.png|.tiff<br>Max.
								file size: 5MB
							</td>
						</tr>
					</tbody>
				</table>
			</form>
			<div id="editPartsUploadedFiles">
				<jsp:include page="edit_parts_uploaded_files.jsp" />
			</div>
			<br />
			<table>
				<tbody>
					<tr>
						<td width="30%" style="padding-left: 10px"><a
							onclick="cancelEditParts()" href="javascript:void(0)">Cancel</a>
						</td>
						
						<td width="30%" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <button class="newbutton"
							id="saveEditButton" onclick="editPart();">SAVE</button>
						</td>
						<td align="center" width="670px" width="40%">
							<div id="deletePartLink" style="display: block;">
								<a onclick="showDeletePartConfirm()" href="javascript:void(0)">Delete
									This Part</a>
							</div>
							<div id="notAbleToDeleteInfo" style="display: none;">
								<i>This part can not be deleted. Update the part status
									instead.</i>
							</div>
							<div id="deletePartConfirm" style="display: none;">
								<b>Are you sure you want to delete this part?</b><br> <a
									onclick="cancelDeletePartConfirm();" href="javascript:void(0);">Cancel</a>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="deletePartDetails();"
									class="newbutton">Delete This Part</button>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div id="editPartUnitCost" style="z-index: 3000; display: none;"
		class="jqmWindow jqmID1">
			<div class="add_part_head"
				style="border-width: 1px; border-style: solid; border-color: rgb(128, 128, 128); position: relative; width: 98%; height:18px;">
			</div></br>
			<div id="unitCostValue" style="font-size:12px; text-align:left;">
			<p style="padding-left:10px;">You have entered an unusually high Unit Cost (greater than $999.99) for your part.Please confirm if you want to continue.</p>
			</div></br>
			<div style="margin-left:75%;">
			<input id="unitCostYes" class="newButton" type="button" value="YES" onclick="saveUnitCost();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="unitCostNo" class="newButton" type="button" value="NO" onclick="clearUnitCost();">
			</div>
			</br>
	</div>
</body>
</html>