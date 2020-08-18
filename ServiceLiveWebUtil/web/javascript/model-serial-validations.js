//Priority 5B changes

jQuery(document).ready(function() {
	
});

//Disable the model number field when check box is checked
function modelDisable()
{			
	if(jQuery("#modelImage").is(':checked'))  {
		jQuery("#referenceValueModel").val("");
		jQuery("#referenceValueModel").attr({disabled:"disabled"});
		jQuery("#referenceValueModel").css({background:"#e6e6e6"});
		var error = jQuery("#customRefError").html();
		if("Model Number contains invalid characters" == error){
			jQuery("#customRefError").html("");
			jQuery(".customRefError").hide();
			jQuery("#modelError").val("");
		}
	}
	else{
		jQuery("#referenceValueModel").removeAttr("disabled");
		jQuery("#referenceValueModel").css({background:""});
	}
}

//Disable the serial number field when check box is checked
function serialDisable()
{			
	if(jQuery("#serialImage").is(':checked'))  {
		jQuery("#referenceValueSerialNumber").val("");	
		jQuery("#referenceValueSerialNumber").attr({disabled:"disabled"});	
		jQuery("#referenceValueSerialNumber").css({background:"#e6e6e6"});	
		if("Serial Number contains invalid characters" == error){
			jQuery("#customRefError").html("");
			jQuery(".customRefError").hide();
			jQuery("#serialError").val("");
		}
	}
	else{
		jQuery("#referenceValueSerialNumber").removeAttr("disabled");
		jQuery("#referenceValueSerialNumber").css({background:""});	
	}
}

//show model if model/serial no. validation error exists
function showModelSerialConfirm()
{
	var modelValidated = jQuery("#modelValidated").val();
	var serialValidated = jQuery("#serialValidated").val();
	if("" == modelValidated){
		validateModel();
	}
	if("" == serialValidated){
		validateSerial();
	}
	var modelError = jQuery("#modelError").val();
	var serialError = jQuery("#serialError").val();
	var isModelSerialSame = jQuery("#isModelSerialSame").val();
	if('true' == modelError || 'true' == serialError || 'true' == isModelSerialSame){
		jQuery("#modelSerialConfirm").modal({
	          onOpen: modalOpenAddCustomer,
	          onClose: modalOnClose,
	          persist: true,
	          containerCss: ({ width: "500px", height: "auto", marginLeft: "-250px" })
	   });
		window.scrollTo(1,1);
		
	}else{
		setPermitInd();
		pop_w9modal();
	}
}

function validateYes()
{
	 if(jQuery("#continueYes").is(':checked')) {
		 jQuery("#continueError").hide();
		 setPermitInd();
		 jQuery.modal.impl.close(true);
		 pop_w9modal();
	 }
	 else {
		 jQuery("#continueError").show();
	 }
}

function hideError()
{
	 if(jQuery("#continueYes").is(':checked')) {
		 jQuery("#continueError").hide();
	 }
}

function closeConfirmation()
{
	jQuery.modal.impl.close(true);
	window.location.hash = "#customreference";
}

//Validate model number& serial number
function validateNumber(referenceType)
{
	if('Model' == referenceType){
		validateModel();
	}
	else if('SerialNumber' == referenceType){
		validateSerial();
	}
}

//Validate model number
function validateModel()
{
	jQuery("#modelValidated").val('false');
	
	var model = jQuery("#referenceValueModel").val();
	model = jQuery.trim(model);
	jQuery("#referenceValueModel").val(model);
	
	var error = false;
	var count = jQuery("#modelrulescount").val();
	if(null != model && "" != model){
		for(var i=1; i<= count; i++){
			
			var regex = jQuery("#modelregex"+i).val();
			var pattern = new RegExp(regex);
			var isMatch = model.match(pattern);
			if(null != isMatch){
				error = true;
				var message = jQuery("#modelmsg"+i).val();
				jQuery("#customRefError").html(message);
				jQuery(".customRefError").show();
				jQuery("#modelError").val("true");
				break;
			}
		}
	}

	if(false == error){
		var errorMsg = jQuery("#customRefError").html();
		if(null != errorMsg && "" != errorMsg && errorMsg.indexOf('Model') > -1){
			jQuery("#customRefError").html("");
			jQuery(".customRefError").hide();
		}
		jQuery("#modelError").val("");
		//checkIfSame();
	}

}

//Validate serial number
function validateSerial()
{
	jQuery("#serialValidated").val('true');
	
	var serial = jQuery("#referenceValueSerialNumber").val();
	serial = jQuery.trim(serial);
	jQuery("#referenceValueSerialNumber").val(serial);
	
	var error = false;
	var count = jQuery("#serialrulescount").val();
	if(null != serial && "" != serial){
		for(var i=1; i<= count; i++){
			
			var regex = jQuery("#serialregex"+i).val();
			var pattern = new RegExp(regex);
			var isMatch = serial.match(pattern);
			if(null != isMatch){
				error = true;
				var message = jQuery("#serialmsg"+i).val();
				jQuery("#customRefError").html(message);
				jQuery(".customRefError").show();
				jQuery("#serialError").val("true");
				break;
			}
		}
	}
	
	if(false == error){
		var errorMsg = jQuery("#customRefError").html();
		if(null != errorMsg && "" != errorMsg && errorMsg.indexOf('Serial') > -1){
			jQuery("#customRefError").html("");
			jQuery(".customRefError").hide();
		}
		jQuery("#serialError").val("");
		//checkIfSame();
	}
}

//check whether model and serial number are same
function checkIfSame(){
	
	var model = jQuery("#referenceValueModel").val();
	model = jQuery.trim(model);
	
	var serial = jQuery("#referenceValueSerialNumber").val();
	serial = jQuery.trim(serial);
	
	if("" != model && "undefined" != model && "" != serial && "undefined" != serial){
		if(model == serial){
			jQuery("#customRefError").html("Model Number and Serial Number should not be same");
			jQuery(".customRefError").show();
			jQuery("#isModelSerialSame").val("true");
		}
		else{
			var errorMsg = jQuery("#customRefError").html();
			if(null != errorMsg && "" != errorMsg && errorMsg.indexOf('Serial') > -1 && errorMsg.indexOf('Model') > -1){
				jQuery("#customRefError").html("");
				jQuery(".customRefError").hide();
			}
			jQuery("#isModelSerialSame").val("");
		}
	}
}

