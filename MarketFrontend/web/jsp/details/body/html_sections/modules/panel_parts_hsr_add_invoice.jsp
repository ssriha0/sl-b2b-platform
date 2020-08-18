<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="partInvoiceNo" value="${invoicePartDetails.invoicePartsVOs[1].invoiceNumber}"></c:set>
<c:set var="sourceErrorExists" value="${sourceErrorExists}"></c:set>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/plugins/ajaxfileupload.js"></script>
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
     window.onload= addInvocieLoad();
      function addInvocieLoad(){
          var errorExists = '${sourceErrorExists}';
          if(null != errorExists && errorExists == 'true'){
	        jQuery('#sourceErrorMessage').css("display","block");
	        jQuery("#errorTextAddInvoiceParts").html("Duplicate records found.");
	        jQuery('#sourceHeader').css("display","block");
	        jQuery('.sources').css("display","block");

	        //disableInvoiceSaveButton();
	        jQuery("#fileSelectInvoice").css('pointer-events','none');
	      }else{
	        jQuery('#sourceErrorMessage').css("display","none");
	        enableInvoiceSaveButton();
	        jQuery("#fileSelectInvoice").css('pointer-events','auto');
	      }
	      var source = jQuery('#sourceAddInvoice').val();
	      var nonSearsSourceValue = jQuery('#addInvoicePartNonSearsSource').val();
	      if(source == 'Non Sears'){
	       jQuery("#supplierNameAddInvoice").css("display","block");
	       jQuery("#homeDepotAddInvoice").val(nonSearsSourceValue);
	      }
	      jQuery("#invoiceUploadButton").attr('disabled', true);
	      jQuery("#invoiceUploadButton").css("background","linear-gradient(to bottom, #F8E09E 5%, #F8E09E 100%) repeat scroll 0 0 #F8E09E");
		  jQuery("#invoiceUploadButton").css("color","grey");	
		  jQuery("#invoiceUploadButton").css("pointer-events","none");
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
	
	function setPartStatus(count,id){
		if(id == 'partInstalledRadio_'+count){
			jQuery('#partNotInstalledRadio_'+count).attr("checked", false);
			jQuery('#partStatus_'+count).attr("value","Installed");
			jQuery('#invociePartStatus_'+count).attr("disabled", "disabled");
			jQuery('#invociePartStatus_'+count).attr("value","select");
		}
		else if(id == 'partNotInstalledRadio_'+count){
			jQuery('#partInstalledRadio_'+count).attr("checked", false);
			jQuery('#invociePartStatus_'+count).removeAttr("disabled");
			var status = jQuery("#invociePartStatus_"+count).val();
			jQuery('#partStatus_'+count).attr("value",status);
		}
	}
	function updateInvoicePartStatus(count){
		var partStatus = jQuery('#invociePartStatus_'+count).val();
		jQuery('#partStatus_'+count).attr("value",partStatus);
		 if(partStatus == '' || partStatus == '-1' || partStatus == 'select'){
			 jQuery("#errorTextAddInvoiceParts").html('Please select a part status.');
		 }
		
	}

     function validateInvoice(){
    	 	 jQuery("#errorTextAddInvoiceUpload").html('');
             jQuery("#errorTextAddInvoiceParts").html("");
			 jQuery("#errorTextAddInvoiceParts").css("display","none");
			 disableCursor();
			 fnWaitForResponseShow("Saving...");
             var errorMessage="";
             var source = jQuery('#sourceAddInvoice').val();
              if(null != source && source!='' && source!='undefined'){
            	 source =trim(source);
             }
             jQuery('#sourceAddInvoice').val(source);
             var nonSearsSource =jQuery('#homeDepotAddInvoice').val();
              if(null != nonSearsSource && nonSearsSource!=''&& nonSearsSource!='undefined'){
            	 nonSearsSource=trim(nonSearsSource);
             }
             jQuery('#homeDepotAddInvoice').val(nonSearsSource);
           
             
             var unitCostError = 0;
             var unitCostMax = 0;
             var i;
             var qty = 0;
             var j;
             for(i = 1;i <= jQuery('#invoiceListSize').val(); i++){
            	 if(jQuery('#unitcostInvoice_'+i).val() == "0.00" || jQuery('#unitcostInvoice_'+i).val() == ''){
            		 unitCostError = 1;
            		 jQuery('#unitcostInvoice_'+i).css('color','red');
            	 }else{
            		 jQuery('#unitcostInvoice_'+i).css('color','black');
            	 }
             }
    
             for(j = 1;j <= jQuery('#invoiceListSize').val(); j++){
            	 var qtyj =0;
            	 qtyj=jQuery('#qty_'+j).val();
            	 qtyj =trim(qtyj);
            	 if(qtyj == ''|| qtyj == 0 || (isNotDouble(qtyj))){
            		 qty = 1;
            		 jQuery('#qty_'+j).css('color','red');
            	 }else{
            		 jQuery('#qty_'+j).css('color','black');
            	 }
             }            
		     if(source =='' || source =='-1'){
		        errorMessage = errorMessage + "Please select a part source.<br/>";
		     }else if(source == 'Non Sears'){
		        if(nonSearsSource == ''){
		        errorMessage =errorMessage + "Please enter the name of local part supplier.<br/>";
		     }else if(nonSearsSource == 'Please enter the name of local part supplier.'){
		        errorMessage = errorMessage + "Please enter the name of local part supplier.<br/>";
		       }
		     }
		     if(qty == 1){
		    	 errorMessage = errorMessage + "Please enter a valid value for Quantity.<br/>";
		     }
		      if(unitCostError == 1){
		    	 errorMessage = errorMessage + "Please enter Unit Cost value greater than 0.<br/>";
		     }	      
		     var invoiceNo = jQuery("#invoiceNoId").val();
		     invoiceNo=trim(invoiceNo);
		     jQuery("#invoiceNoId").val(invoiceNo);
		     if(invoiceNo == ''){
		       errorMessage =errorMessage+"Please enter invoice number</br/>";
		     }
		     var partSize = jQuery("#invoiceListSize").val();
				for(var i=0; i<partSize; i++)
		 	    {
					var s = jQuery("#invociePartStatus_"+i).val();
					s = jQuery.trim(s); 
					var partInstalledRadio = $("#partInstalledRadio_"+i).attr("checked");
					if(!partInstalledRadio && (s == "select" || s=="" || s==" " || s==null)){
						errorMessage =errorMessage+"Please select part status</br/>";
						break;
					}
		 	    }
		 	    var errorCheck = false;
				if(''== errorMessage){
					for(i = 1;i <= jQuery('#invoiceListSize').val(); i++){
		               	 if(jQuery('#unitcostInvoice_'+i).val() > 999.99){
		               		unitCostPopUp();
		               		errorCheck =true;
		               	 }
		             }
				
				}
		     if(''  != errorMessage || true == errorCheck){
				fnWaitForResponseClose();
				enableCursor();
				jQuery("#errorTextAddInvoiceParts").html(errorMessage);
				jQuery("#errorTextAddInvoiceParts").css("display","block");
				window.location.hash = '#errorTextAddInvoiceParts';
				errorCheck =false;
				return;
			  }else{
				  jQuery("#errorTextAddInvoiceParts").html('');
		 			var formData = jQuery('#addInvoiceForm').serialize();
		 			//alert(formData);
		 			var soId=jQuery('#soId').val();
		 			//alert(soId);
		 			// disableCursor();
		 			jQuery.ajax({
		 	        	url: 'saveInvoiceDetails.action',
		 	        	type: "POST",
		 	        	data: formData,
		 	        	dataType: 'json',
		 				success: function(data) {
		 					var responseMsg = data.responseMessage;		 					
			            	if(responseMsg!=null && responseMsg!='' && responseMsg!=' ' && responseMsg != '-1'){
			            		enableCursor();
			            		fnWaitForResponseClose();
			            		jQuery("#errorTextAddInvoiceParts").html($ESAPI.encoder().encodeForHTML(responseMsg));
			            		jQuery("#errorTextAddInvoiceParts").css("display","block");
			            	}
		 					// enableCursor();
		 					else{
		 					jQuery("#partSummaryEdit").load("loadPartDetails.action?soId="+soId,function() {
		 						jQuery("#addInvoice").css("display","none");
		 						window.location.hash = '#partSummaryEdit';
		 						jQuery(".invoicePartidEdit").prop("checked", false);
		 						jQuery('#selectAll').removeAttr('checked');
		 						fnWaitForResponseClose();
		 						enableCursor();
		 						});
		 					}
		 				}
		 			});
		}
   }
		     
	 function clickRecall(thisfield, defaulttext) {
	 		if (thisfield.value == "") {
	 		thisfield.value = defaulttext;
	 	   }else{
	 	 		thisfield.value = trim(thisfield.value);
	 	 	}
	 	}
	 
	 function viewInvoicePartsDocument(documentId){
	 		var loadForm = document.getElementById('invoicePartsViewDocForm');
	        loadForm.action = 'viewPartsDocument.action?editDocId='+documentId;
	        loadForm.submit();
	 	}
 	
 	function countAreaChars(areaName,limit, evnt){
 		if (areaName.value.length>limit) {
 			areaName.value=areaName.value.substring(0,limit);
 		    //Stop all further events generated (Event Bubble) for the characters user has already typed in .
 			//For IE
 			if (!evnt) var evnt = window.event;
 			evnt.cancelBubble = true;
 			//For FireFox
 			if (evnt.stopPropagation) evnt.stopPropagation();
 		     }
 		}
 	function clickClear(thisfield, defaulttext) {
      	   if (thisfield.value == defaulttext) {
       	       thisfield.value = "";
       	    }	
 	}
 	 function showTextAreaForNonSears(){
    	 var source = jQuery('#sourceAddInvoice').val();
    	 var invoiceNumber = jQuery('#invoiceNoId').val();
    	 if(source == 'Non Sears'){
    		 jQuery("#homeDepotAddInvoice").val("Please enter the name of local part supplier.");
    		 jQuery("#supplierNameAddInvoice").css("display","block");
    	 }else{
    		 jQuery("#supplierNameAddInvoice").css("display","none");
    	 }
    	 if(source == 'Truck Stock' && invoiceNumber === ""){
    		 jQuery('#invoiceNoId').val('NA');
    	 }
    	 if((source == 'Non Sears'||source == 'Sears'||source == '-1') && invoiceNumber == 'NA'){
    		 jQuery('#invoiceNoId').val('');
    	 }
     }
 	 
  	function unitCostPopUp(){
		jQuery("#addInvoiceunitCost").addClass("jqmWindow");
		jQuery("#addInvoiceunitCost").css("width", "38%");
		jQuery("#addInvoiceunitCost").css("height", "auto");
		jQuery("#addInvoiceunitCost").css("border","3px solid lightgrey");
		jQuery("#addInvoiceunitCost").css("marginLeft", "-300px");
		jQuery("#addInvoiceunitCost").css("position", "fixed"); 
		jQuery("#addInvoiceunitCost").css("zIndex",1000);
		jQuery("#addInvoiceunitCost").css("background-color","#FFFFFF");
		$("#addInvoiceunitCost").jqm({modal:true});
		jQuery("#addInvoiceunitCost").fadeIn('slow');
		jQuery('#addInvoiceunitCost').css('display', 'block');
		$("#addInvoiceunitCost").jqmShow();
		jQuery(".jqmOverlay").css("opacity",0.5);
	}
	function clearUnitCost(){
			$("#addInvoiceunitCost").jqmHide();
	      	 var zero = "0.00";
	      	 var i;
	         var j;
	       	
	        for(i = 1;i <= jQuery('#invoiceListSize').val(); i++){
	          	 if(jQuery('#unitcostInvoice_'+i).val() > 999.99){
	     	       	jQuery('#unitcostInvoice_'+i).val(zero);
	          	 }
	          } 
	}
	
	function saveUnitCost(){
		$("#addInvoiceunitCost").jqmHide();
		var errorMessage="";
		var unitCostError = 0;
        var i;
        var j;
        for(i = 1;i <= jQuery('#invoiceListSize').val(); i++){
       	 if(jQuery('#unitcostInvoice_'+i).val() > 9999.99){
       		unitCostError = 1;
       		 jQuery('#unitcostInvoice_'+i).css('color','red');
       	 }else{
       		 jQuery('#unitcostInvoice_'+i).css('color','black');
       	 }
        }
        
        if(unitCostError == 1){
	    	 errorMessage = errorMessage + "You have entered Unit Cost greater than the maximum allowed value of $9999.99 for one or more parts. Please correct the values and try again.<br/>";
	     }
        
        if(''  != errorMessage){
        	disableInvoiceSaveButton();
			fnWaitForResponseClose();
			enableCursor();
			jQuery("#errorTextAddInvoiceParts").html(errorMessage);
			jQuery("#errorTextAddInvoiceParts").css("display","block");
			window.location.hash = '#errorTextAddInvoiceParts';
			return;
		  }
		else{
			  jQuery("#errorTextAddInvoiceParts").html('');
	 			var formData = jQuery('#addInvoiceForm').serialize();
	 			//alert(formData);
	 			var soId=jQuery('#soId').val();
	 		    disableCursor();
			    fnWaitForResponseShow("Saving...");
	 			jQuery.ajax({
	 	        	url: 'saveInvoiceDetails.action',
	 	        	type: "POST",
	 	        	data: formData,
	 	        	dataType: 'json',
	 				success: function(data) {
	 					var responseMsg = data.responseMessage;		 					
		            	if(responseMsg!=null && responseMsg!='' && responseMsg!=' ' && responseMsg != '-1'){
		            		enableCursor();
		            		fnWaitForResponseClose();
		            		jQuery("#errorTextAddInvoiceParts").html($ESAPI.encoder().encodeForHTML(responseMsg));
		            		jQuery("#errorTextAddInvoiceParts").css("display","block");
		            	}
	 					// enableCursor();
	 					else{
	 					jQuery("#partSummaryEdit").load("loadPartDetails.action?soId="+soId,function() {
	 						jQuery("#addInvoice").css("display","none");
	 						window.location.hash = '#partSummaryEdit';
	 						jQuery(".invoicePartidEdit").prop("checked", false);
	 						jQuery('#selectAll').removeAttr('checked');
	 						fnWaitForResponseClose();
	 						enableCursor();
	 						});
	 					}
	 				}
	 			});
		}
	}
	function validatePrices(e,id){
	 jQuery('#unitcostInvoice_'+id).css('color','black');
	 var message="";
	 var i;
	 var j;
	 var unitCostError=0;
	 var unicode=e.keyCode? e.keyCode : e.charCode;
	 if(unicode!=9){
		var unitCost = jQuery("#unitcostInvoice_"+id).val();
		unitCost=trim(unitCost);
	
		for(i = 1;i <= jQuery('#invoiceListSize').val(); i++){
	       	 if(jQuery('#unitcostInvoice_'+i).val() > 9999.99){
	       		 unitCostError = 1;
	       		 jQuery('#unitcostInvoice_'+i).css('color','red');
	       	 }else{
	       		 jQuery('#unitcostInvoice_'+i).css('color','black');
	       	 }
	        }
	        
	        if(unitCostError == 1){
	        	message = message + "You have entered Unit Cost greater than the maximum allowed value of $9999.99 for one or more parts. Please correct the values and try again.<br/>";
		     }
	        if(''  != message){
	        	disableInvoiceSaveButton();
				fnWaitForResponseClose();
				enableCursor();
				jQuery("#errorTextAddInvoiceParts").html(message);
				jQuery("#errorTextAddInvoiceParts").css("display","block");
				return;
			  }
	        else{
	        	enableInvoiceSaveButton();
				jQuery("#errorTextAddInvoiceParts").html();
				jQuery("#errorTextAddInvoiceParts").css("display","none");
	        }
		//Ended
	}
}

		function amountRoundoffUnitPrice(id){
			var unitCost = jQuery("#unitcostInvoice_"+id).val();
			 if(unitCost == 0){
			   var zero = "0.00";
			   jQuery("#unitcostInvoice_"+id).val(zero);
			 }else{
				 jQuery("#unitcostInvoice_"+id).val(addOnObject.fmtMoney(unitCost));
			 }
			 
		}
		
		function isValidNumber(evt,id) {
			 var unitCost = jQuery("#unitcostInvoice_"+id).val();
			 var charCode = (evt.which) ? evt.which : event.keyCode;
			    if (charCode == 46 && unitCost.split('.').length>1){
			        return false;
			    }
			    if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57))
			        return false;
			    return true;
		}
		
		function isValidNumberForQty(evt,id) {
			 var qty = jQuery("#qty_"+id).val();
			 var charCode = (evt.which) ? evt.which : event.keyCode;
			 if( charCode!=8 && charCode!=0 && (charCode<49 || charCode>57)){
		   		return false;
			}
			    return true;
		}
		
		
		
		// for file upload
		
		
		function showFileNames() {
         var fileSelected = document.getElementById('fileSelectInvoice');
         if(fileSelected){
             jQuery("#invoiceUploadButton").attr('disabled', false);
	         jQuery("#invoiceUploadButton").css("color","#080108");	
			 jQuery("#invoiceUploadButton").css("pointer-events","auto");
			 jQuery("#invoiceUploadButton").css("background","linear-gradient(to bottom, #F8E09E 5%, #F9BF21 100%) repeat scroll 0 0 #F8E09E");  
         	}
    	 }
		
		
		function deleteEditPartsDocument(documentId){
			jQuery("#errorTextAddInvoiceUpload").html('');
			disableCursor();
			fnWaitForResponseShow("Removing...");
			var invoiceIds = $('.invoiceIds').map(function() {
				return this.value;
			}).get();			
			var soId1 = jQuery('#soId').val();

			jQuery.ajax({
	        	url: 'deletePartsDocument.action?documentId='+documentId+'&checkedInvoiceIds='+invoiceIds+'&source=add_invoice&soId='+soId1,
				success: function(data) {
					jQuery("#addInvoiceUploadedFiles").load("loadEditPartUploadedFilesInvoice.action", function(){
					fnWaitForResponseClose();
		        	enableCursor();
	            		
	            	});
				},
				error: function(data){
					alert("error");
					fnWaitForResponseClose();
		        	enableCursor();
				}
			});	
	 	}
		
		
		function uploadInvoiceFile(){
			//document.getElementById('editUploadButton').innerText = 'Uploading...';
			jQuery("#errorTextAddInvoiceUpload").html('');
			fnWaitForResponseShow("Uploading...");
			disableCursor();
			jQuery("#invoiceUploadButton").attr('disabled', true);
			jQuery("#invoiceUploadButton").css("background","linear-gradient(to bottom, #F8E09E 5%, #F8E09E 100%) repeat scroll 0 0 #F8E09E");
		    jQuery("#invoiceUploadButton").css("color","grey");	
		    jQuery("#invoiceUploadButton").css("pointer-events","none");
			var fieldVal = jQuery('#fileSelectInvoice').val();
			var fileNameIndex = fieldVal.lastIndexOf("\\");
			fieldVal = fieldVal.substring(fileNameIndex + 1);
			fieldVal = fieldVal.replace(/%/g, "-prcntg-");
			fieldVal = encodeURIComponent(fieldVal);
			var soId = $("#soId").val();
			var invoicePartIds=$('.invoiceIds').map(function(){return this.value;}).get();
			var formValues = jQuery('#uploadInvoiceFilesForm').serializeArray();
			if (fieldVal != null && fieldVal.length != 0){
					jQuery.ajaxFileUpload({
		            url:'uploadPartsDocumentsInvoice.action?fileName='+fieldVal+"&soId="+soId+"&invoicePartIds="+invoicePartIds,
		            secureuri:false,
		            type: "POST",
		            data: formValues,
					dataType: 'json',
					fileElementId:'fileSelectInvoice',
		            success: function(data,status){
		            	var responseMsg = data.responseMessage;
		            	if(responseMsg!=null && responseMsg!='' && responseMsg!=' ' && responseMsg != '-1'){
		            		enableCursor();
		            		fnWaitForResponseClose();
		            		jQuery("#errorTextAddInvoiceUpload").html(responseMsg);
		            	}
		            	else{
		            		jQuery("#errorTextAddInvoiceUpload").html('');
		            		jQuery("#addInvoiceUploadedFiles").load("loadEditPartUploadedFilesInvoice.action", function(){
		            			enableCursor();
			            		fnWaitForResponseClose();
				            	});
				            	jQuery("#fileSelectInvoice").val('');
		            	}
		        		//jQuery('#fileNames').html('');
		        		//document.getElementById('editUploadButton').innerText = 'Upload';
		            },
					error: function(data){
		            	var responseMsg = data.responseMessage;
		            	if(responseMsg!=null && responseMsg!='' && responseMsg!=' ' && responseMsg != '-1'){
		            		enableCursor();
		            		fnWaitForResponseClose();
		            		jQuery("#errorTextAddInvoiceUpload").html("error"+responseMsg);
		            	}
		            	else{
		            		jQuery("#errorTextAddInvoiceUpload").html('');
		            		jQuery("#addInvoiceUploadedFiles").load("loadEditPartUploadedFilesInvoice.action", function(){	
		            			enableCursor();
			            		fnWaitForResponseClose();
				            	});
				            	jQuery("#fileSelectInvoice").val('');
		            	}	
		        		
		            }
				});
			}else{
				//$("#uploadResp").html("Please select a file to upload.");
				//document.getElementById('editUploadButton').innerText = 'Upload';
        		fnWaitForResponseClose();
        		enableCursor();
			}
			jQuery("#invoiceUploadButton").attr('disabled', true);
			jQuery("#invoiceUploadButton").css("background","linear-gradient(to bottom, #F8E09E 5%, #F8E09E 100%) repeat scroll 0 0 #F8E09E");
		    jQuery("#invoiceUploadButton").css("color","grey");	
		    jQuery("#invoiceUploadButton").css("pointer-events","none");
		}
		function cancelAddInvoice(){
			 jQuery("#addInvoice").css("display","none");
			 var invoiceIds = $('.invoiceIds').map(function() {
					return this.value;
				}).get();			
			 var soId1 = jQuery('#soId').val();
			 jQuery.ajax({
		        	url: 'deletePartsDocument.action?source=cancel_add_invoice&soId='+soId1+'&checkedInvoiceIds='+invoiceIds,
					dataType : "json",
					success: function(data) {
						
					}
				});	
			 
		 }
		
		   function disableInvoiceSaveButton(){
			    jQuery("#saveInvoiceButton").css("background","linear-gradient(to bottom, #F8E09E 5%, #F8E09E 100%) repeat scroll 0 0 #F8E09E");
				jQuery("#saveInvoiceButton").css("color","grey");	
				jQuery("#saveInvoiceButton").css("pointer-events","none");	  
		   }
		   function enableInvoiceSaveButton(){
			   jQuery("#saveInvoiceButton").css("color","#080108");	
			   jQuery("#saveInvoiceButton").css("pointer-events","auto");
			   jQuery("#saveInvoiceButton").css("background","linear-gradient(to bottom, #F8E09E 5%, #F9BF21 100%) repeat scroll 0 0 #F8E09E");   
		   }
		   
		   function isQtyNumeric(e,count){	
			   
			  	 if( e.which!=8 && e.which!=0 && (e.which<=48 || e.which>57)){
			  		 if(e.which == 48){
			  			var qty = $("#qty_"+count).val();
			  			if(0 == qty){
			  				return false;
			  			}
			  			else{
			  				return true;
			  			}
			  		 }
			   		return false;
			 	} 

		   }

</script>
<div id="addInvoice" class="addInvoice" style="display: none;position:static;border:1px solid grey;" width="90%">
<div class="submenu_head" style="height:auto;">Invoice Number ${invoicePartDetails.invoiceNumber}</div>
         <input type="hidden" id="errorMsgDoc" name="errorMsgDoc" value="${documentUploadError}" />
         <input type="hidden" id="addInvoicePartNonSearsSource" name="editPartNonSearsSource" value="${invoicePartDetails.nonSearsSource}"/>
          <input type="hidden" id="invoiceListSize" name="invoiceListSize" value="${fn:length(invoicePartDetails.invoicePartsVOs)}" />
         <form id="addInvoiceForm" name="addInvoiceForm" method="POST">
         <div id="errorTextAddInvoiceParts" style="color:#FF0000;"></div>
	         <div class="sourceDiv" >
	          <c:choose>
					     
					       <c:when test="${sourceErrorExists == true}">
					                     			   <div id="srcError" align="left" style="width: 20%;float: left;height: 27px">
					                     			 &nbsp;
					       								</div>
					       </c:when>
					       <c:otherwise>
	         <div id="srcTitle" align="left" style="width: 20%;float: left;height: 27px">
	         <span class="partSourcelabel">Source</span>
	         </div>
	         </c:otherwise>
	         </c:choose>
	         <div id="srcBody" align="left" style="width: 80%;float: right;height: 30px">
	           <c:choose>
					     
					       <c:when test="${sourceErrorExists != true}">
	            <select class="partSourcevalue" id="sourceAddInvoice" name="model.invoiceDetailsVO.partSource" onchange="showTextAreaForNonSears()">
					   
					       
					          <option value="-1">--Select One--</option>
					           <c:forEach items="${invoicePartDetails.partSourceTypes}" var="sourceType">
					             <c:choose>
					                <c:when test="${ not empty invoicePartDetails.partSource && (sourceType.partSourceValue == invoicePartDetails.partSource)}">
					     	              <option value="${sourceType.partSourceValue}" selected="selected"> ${sourceType.partSourceValue}</option>
					                </c:when>
					                <c:otherwise>
					     	             <option value="${sourceType.partSourceValue}"> ${sourceType.partSourceValue}</option>
					                </c:otherwise>
					             </c:choose>
					         </c:forEach>
				</select> 
                   </c:when>
                   <c:otherwise>
                  &nbsp;
                   </c:otherwise>
                   </c:choose>
                 <span id="supplierNameAddInvoice" style="width: 200px; display: none; padding-top: 7px;">
				<textarea
					onkeydown="countAreaChars(this.form.homeDepotAddInvoice, 50, event);"
					onkeyup="countAreaChars(this.form.homeDepotAddInvoice, 50, event);"
					onfocus="clickClear(this,'Please enter the name of local part supplier.')"
					name="invoiceDetailsVO.nonSearsSource" onblur="clickRecall(this,'Please enter the name of local part supplier.')"
					style="display: inline; resize: none; position: relative;width: 200px; overflow: hidden; height: 27px; font-size: 11px; top: 0px;margin-left: 250px;margin-top:-25px;"
					cols="24" rows="1" id="homeDepotAddInvoice" value="${invoicePartDetails.nonSearsSource}"> </textarea>
			  </span>
	         </div>
	         </div>
	         <div class="invoiceDispalyDiv" style="width: 75%;margin-top: 5px">
	         <div id="invTitle" align="left" style="width: 26%;float: left;height: 27px">
	          <span class="invoiceNoText">Invoice Number</span>
	         </div>
	         <div id="invBody" align="left" style="width: 73.5%;float: right;height: 27px">
	          <span class="invoiceNoValue">
	               <input id="invoiceNoId" class="shadowBox grayText" value="${invoicePartDetails.invoiceNumber}" type="text" maxlength="15" name="invoiceDetailsVO.invoiceNumber" style="width:200px;">
	           </span>
	         </div>
	         </div>
	        
	         
	         <div id="partsTableDiv" class="partsTableDiv">
	             <table width="100%" class="installed_parts" cellspacing="10px;">
	               <thead>
	                 <tr class="spaceUnder">
	                   <td width="15%" ><span class="boldSpan" >Part Number</span></td>
	                   <td width="20%" ><span class="boldSpan" >Part Name</span></td>
	                   <td width="10%" style="text-align:center"><span class="boldSpan" style="margin-left:2px;">Unit Cost</span></td>
	                   <td width="5%"  style="text-align:center"><span class="boldSpan">Qty</span></td>
	                   <td width="30%" style="text-align:center;">
	                     <span class="boldSpan"  >Part Status</span></td>
	                    <c:if test="${sourceErrorExists == true}">
	                     <td width="20%" style="text-align:center">
                         	<span class="boldSpan" id="sourceHeader" style="display:none;">Source</span>
                         </td>
                       </c:if>
	                 </tr>
	               </thead> 
	               <c:set var="invoiceCountNo"
						value="0"></c:set>
						
						<c:set var="soId"value=""></c:set>
	                 <c:forEach items="${invoicePartDetails.invoicePartsVOs}" var="invoicePart" varStatus="i">
	                 
	                 	<input class = "invoiceIds" type="hidden" id="checkedInvoiceIds" name="checkedInvoiceIds" value="${invoicePart.partInvoiceId}" />
	                    <input type="hidden" id="invoiceDetailsVO.invoicePartsVOs[${i.count}].partInvoiceId" name="invoiceDetailsVO.invoicePartsVOs[${invoiceCountNo}].partInvoiceId" value="${invoicePart.partInvoiceId}" />
						<input type="hidden" id="retailPrice" name="invoiceDetailsVO.invoicePartsVOs[${invoiceCountNo}].retailPrice" value="${invoicePart.retailPrice}" />
						<input type="hidden" id="claimStatus" name="invoiceDetailsVO.invoicePartsVOs[${invoiceCountNo}].claimStatus" value="${invoicePart.claimStatus}" />
						<input type="hidden" id="autoAdjudicationInd" name="invoiceDetailsVO.invoicePartsVOs[${invoiceCountNo}].autoAdjudicationInd" value="${invoicePart.autoAdjudicationInd}" />	         
	                   	<c:choose>
	                   	<c:when test="${invoicePartDetails.invoiceNumber == null || invoicePartDetails.invoiceNumber == '' || invoicePartDetails.invoiceNumber == ' ' || invoicePart.partStatus == 'Installed'}">
	                   		<input type="hidden" id ="partStatus_${invoiceCountNo}" name="invoiceDetailsVO.invoicePartsVOs[${invoiceCountNo}].partStatus" value="Installed"/>
	                   	</c:when>
	                   	<c:otherwise>
	                   		<input type="hidden" id ="partStatus_${invoiceCountNo}" name="invoiceDetailsVO.invoicePartsVOs[${invoiceCountNo}].partStatus" value="${invoicePart.partStatus}"/>
	                   	</c:otherwise>
	                   	</c:choose>
	                    <c:set var="soId" value="${invoicePart.soId}" />
	                    <tr class="spaceUnder">
	                     <td width="15%"><div style="word-wrap:break-word; width:60px;">${invoicePart.partNumber}</div></td>
	                     <td width="20%"><div style="word-wrap:break-word; width:80px;">${invoicePart.partDescription}</div></td>
	                    <td width="10%" style="text-align:center">
	                    	<div style="width:70px;">$   	                      
								<input id="unitcostInvoice_${i.count}" type="text" name="invoiceDetailsVO.invoicePartsVOs[${invoiceCountNo}].unitCost" style="width:40px" onblur="amountRoundoffUnitPrice(${i.count});validatePrices(event,${i.count});"
										onkeypress="return isValidNumber(event,${i.count});" class="shadowBox grayText"size="10" maxlength="7" value="${invoicePart.unitCost}"/></div> 
	                   	 </td>
	                     <td width="5%" style="margin-left:5px;" style="text-align:center"> 
	                      <input id="qty_${i.count}" type="text" name="invoiceDetailsVO.invoicePartsVOs[${invoiceCountNo}].qty" style="width:30px;"
										onkeypress="return isQtyNumeric(event,${i.count});" class="shadowBox grayText" size="5" maxlength="3" value="${invoicePart.qty}"/>
	                     </td>
	                     <td width="30%" style="text-align:center">
	                   		 	<table>
	                   		 	<tr>
	                   		 	<td>
	                   		 	<div style="width:70px;">
	                   		 	<c:choose>
								     <c:when test="${invoicePartDetails.invoiceNumber == null || invoicePartDetails.invoiceNumber == '' || invoicePartDetails.invoiceNumber == ' ' || invoicePart.partStatus == 'Installed'}">
	                   	 	 			<input id="partInstalledRadio_${invoiceCountNo}" name ="partInstalledRadio_${invoiceCountNo}" type="radio" value="Installed" checked="checked" onclick="setPartStatus(${invoiceCountNo},this.id)"/>Installed
	                     			</c:when>
	                     			<c:otherwise>
	                     				<input id="partInstalledRadio_${invoiceCountNo}" name ="partInstalledRadio_${invoiceCountNo}" type="radio" value="Installed" onclick="setPartStatus(${invoiceCountNo},this.id)"/>Installed
	                     			</c:otherwise>
	                     		</c:choose>
	                     		</div></td>
	                    <td>
	                    	 <c:choose>
		                     	 <c:when test="${invoicePartDetails.invoiceNumber != null && invoicePartDetails.invoiceNumber != '' && invoicePartDetails.invoiceNumber != ' ' && invoicePart.partStatus != 'Installed'}">
		                     	 	<input id="partNotInstalledRadio_${invoiceCountNo}" name ="partNotInstalledRadio_${invoiceCountNo}" type="radio" checked="checked" onclick="setPartStatus(${invoiceCountNo},this.id)"/>
		                    	</c:when>
		                    	<c:otherwise>
		                    		<input id="partNotInstalledRadio_${invoiceCountNo}" name ="partNotInstalledRadio_${invoiceCountNo}" type="radio" onclick="setPartStatus(${invoiceCountNo},this.id)"/>
		                    	</c:otherwise>
	                    	</c:choose>
</td><td>	                        <select id="invociePartStatus_${invoiceCountNo}" name="partStatus" onchange="updateInvoicePartStatus(${invoiceCountNo});"
	                        <c:if test="${invoicePartDetails.invoiceNumber == null || invoicePartDetails.invoiceNumber == '' || invoicePartDetails.invoiceNumber == ' ' || invoicePart.partStatus == 'Installed'}">disabled="disbaled"</c:if>>
							    <option value="select">--Select One--</option>
							       <c:if test="${not empty invoicePart.partStatus &&(invoicePart.partStatus =='Added')}">
					                   <option value="Added" selected="selected">Added</option>
					              </c:if>
							     <c:forEach items="${invoicePartDetails.partStatusTypes}" var="partStatus">
							      <c:if test="${(partStatus.descr) != 'Installed'}">
							      <c:choose>
								     <c:when test="${not empty invoicePart.partStatus &&( partStatus.descr == invoicePart.partStatus)}">
								     	<option value="${partStatus.descr}" selected="selected"> ${partStatus.type}</option>
								     </c:when>
								     <c:otherwise>
					      				<option value="${partStatus.descr}">${partStatus.type}</option>
								     </c:otherwise>
								  </c:choose>
							     </c:if>
							     </c:forEach>
							   
                            </select></td>
	                   		 	</tr>
	                   		 	</table>
                         </td>
                         <c:if test="${sourceErrorExists == true}">
                            <td width="20%" style="text-align:center;">
                    		  <div class="sources" style="display:none;">
                    		     ${invoicePart.partSource}
                    		     <c:if test="${invoicePart.partSource == 'Non Sears'}">- ${invoicePart.nonSearsSource}</c:if>
                    		  </div>
                            </td>
                         </c:if>
	                  </tr>
	                   <c:set var="invoiceCountNo"
						value="${invoiceCountNo+1}"></c:set>
	                </c:forEach>
	                <input type="hidden" id="soId" name="soId" value="${soId}" />
	                
	              </table>
	         </div>
	       </form>
	                        
             <span id="errorTextAddInvoiceUpload" style="color:#FF0000; margin-left: 13px;"></span>
			<form id="uploadInvoiceFilesForm" enctype="multipart/form-data" method="POST">
				<table>
					<tbody>
						<tr>
							<td width="16%" align="left"><b>Upload&nbsp;Invoice</b></td>
							<td width="34%">
								<input type="file" id="fileSelectInvoice" name="fileSelect" size="20" onchange="showFileNames();">
							</td>
							
					        <td width="13%">
                            	<input type="button" id="invoiceUploadButton" onclick="uploadInvoiceFile();" value="Upload" class="myButton"/>
							</td>
							<td width="33%" align="center">Accepted File Formats :<br>.doc.|docx|.pdf|.gif|.jpeg|.jpg|.pjpeg|.png|.tiff<br>Max.
								file size: 5MB
							</td>
						</tr>
					</tbody>
				</table>
			</form>   
                            
	                   
	         <div id="addInvoiceUploadedFiles">
				<jsp:include page="add_invoice_uploaded_files.jsp" />
	 		</div>
	 	<br/><hr><br/>
	       <table width="80%">
	       <tr>
	       <td width="10%" style="padding-left: 20px;">
	       <a href="javascript:void(0)" onclick="cancelAddInvoice();"> Cancel</a>
	       </td>
	       <td width="90%" align="right">
	      	 <button id="saveInvoiceButton" onclick="validateInvoice();" class="newbutton">SAVE</button>
	       </td>
	       </tr>
	       </table>
	       
</div>

<div id="addInvoiceunitCost" style="z-index: 3000; display: none;"
		class="jqmWindow jqmID1">	
		   <div class="add_part_head"
				style="border-width: 1px; border-style: solid; border-color: rgb(128, 128, 128); position: relative; width: 98%; height:18px;">
			</div></br>
		   <div id="unitCostValue" style="font-size:12px; text-align:left;">
		   <p style="padding-left:10px;">You have entered an unusually high Unit Cost (greater than $999.99) for one or more parts.Please confirm if you want to continue.</p>
		   </div></br>
			<div style="margin-left:75%;">		
			<input id="unitCostYes" class="newButton" type="button" value="YES" onclick="saveUnitCost();">&nbsp;&nbsp;&nbsp;&nbsp;
			<input id="unitCostNo"  class="newButton" type="button" value="NO" onclick="clearUnitCost();">
			</div>
			</br>
	</div>