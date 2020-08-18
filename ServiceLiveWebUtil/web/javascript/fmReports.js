jQuery.noConflict();
var errorFound = false;

jQuery(document).ready(function() {
   var arr = window.document.URL.toString().split("&");
   if(arr.length > 2){
	   if(arr[2].split("=")[1] == 'so_byr' || arr[2].split("=")[1] == 'tax_byr'|| arr[2].split("=")[1] == 'so_prov' || arr[2].split("=")[1] == 'rev_prov'){
		var scrollPos =arr[1].split("=")[1];
		jQuery(window).scrollTop(scrollPos - 320);   
	   }   
   }
});

function setInputs(form){
    var formInd = jQuery(form).attr('formInd');
    var formId = 'report_'+formInd;
	//Setting buyerIds/providerIds as applicable (For SLAdmin)
	jQuery("#buyers").val(getValueByName(formId,'buyerIds'));
	jQuery("#providers").val(getValueByName(formId,'providerIds'));
	jQuery("#reportName").val(getValueByName(formId, 'form_name'));
	
	//Setting buyer ids is specific buyers selected
	var val = jQuery("#"+formId+" input[name=allBuyers_"+formInd+"]:checked").val();
	if(val === 'true'){
		jQuery("#reportForAllBuyers").val(true);
	}else if(val === "false" ){	     
		jQuery("#buyers").val(getValueByName(formId,'specificIds'));
		jQuery("#reportForSpecificBuyers").val(true);
	}
	
	//Setting provider ids is specific providers selected
	val = jQuery("#"+formId+" input[name=allProviders_"+formInd+"]:checked").val();
	if(val === 'true'){
		jQuery("#reportForAllProviders").val(true);
	}else if(val === 'false' ){
		jQuery("#providers").val(getValueByName(formId,'specificIds'));
		jQuery("#reportForSpecificProviders").val(true);
	}     

	//Setting year or date ranges
	val = jQuery("#"+formId+" input[name=yearRadio_"+formInd+"]:checked").val();
	if(val == 'true'){
		jQuery("#reportByCalendarYear").val(true);
		val = jQuery("#"+formId+" select[name=yearSelect]").val();			
		jQuery("#reportYear").val(val);
	}else if(val == 'false'){
		jQuery("#fromDate").val(getValueByName(formId,'dojoCalendarFromDate'));
		jQuery("#toDate").val(getValueByName(formId,'dojoCalendarToDate'));
		jQuery("#reportByDateRange").val(true);
	}     
	
	//Setting report by payment date/ completed date
	val = jQuery("#"+formId+" input[name=reportBy_"+formInd+"]:checked").val();
	if(val == 'true'){
		jQuery("#reportByPaymentDate").val(true);
	}else if(val == 'false'){
		jQuery("#reportByCompletedDate").val(true);
	} 
    
	/*var dispType = jQuery(form).attr("value");
	if(dispType === 'EXPORT'){
		jQuery("#export").val('true');
	}else{
		jQuery("#export").val('false');
	} */
	
    /*var elem = document.getElementById('reportInputForm').elements;	
   		 for(var i = 0; i < elem.length; i++)
        {
        	alert(elem[i].name+" : "+elem[i].value);
        } 
     */
}

function getValueByName(formId,name){
	var val = jQuery("#"+formId+" input[name="+name+"]").val();
	return((val) ? val : "");
}

function clearErrorForDownload(){
	jQuery('#errReport').html("");
	jQuery('#errReport').hide();
	jQuery('#errReportCount_so_prov').html("");
	jQuery('#errReportCount_so_prov').hide();
	jQuery('#errReportCount_rev_prov').html("");
	jQuery('#errReportCount_rev_prov').hide();
	jQuery('#errReportCount_so_byr').html("");
	jQuery('#errReportCount_so_byr').hide();
	jQuery('#errReportCount_tax_byr').html("");
	jQuery('#errReportCount_tax_byr').hide();
	jQuery('#errReportCount_pay_admn').html("");
	jQuery('#errReportCount_pay_admn').hide(); 
}

function process(btn){
	var reportId = jQuery(btn).attr("report");
	var formInd = jQuery(btn).attr("formInd");
	//jQuery('#reportId').val(reportId);
	jQuery("#overLay").show();
	jQuery(".successBox").html("");
	jQuery(".successBox").hide();
	clearErrors();	
	clearErrorForDownload();	
	var targetOffset = jQuery(btn).offset().top;
	var btnId = jQuery(btn).attr("id");
	if(jQuery.trim(btnId) != ""){		
		 var subStrs = btnId.split("_");
		 var operation = subStrs[0];
		 if(jQuery.trim(operation) != ""){
			 if(operation == 'delete'){
				window.location.href='fmReports_deleteReports.action?reportId='+reportId+'&formInd='+formInd+'&targetOffset='+targetOffset;
			/*	 jQuery.ajax({
						url: 'fmReports_deleteReports.action?reportId='+reportId,
						
						dataType : "json",
						success: function( data ) {
							if(data.reportErrors !== ""){	
								jQuery('#errReportCount').html("");
								jQuery.each(data.reportErrors, function(index,value){
									jQuery('#errReportCount').append('<p class="errorMsg">'+value.msg+'</p>');
									jQuery('#errReportCount').show();
									jQuery("body").css("cursor", "default");
									return false;
								});				
							}else{
								 var locationPath=window.location.pathname;		
								if(locationPath.indexOf("financeManagerController_execute.action")=="-1"){
									   	//location.reload();
									window.location.replace('fullfillmentAdminAction_execute.action?targetOffset='+targetOffset);
								}else{		
									window.location.replace('financeManagerController_execute.action?defaultTab=Reports&targetOffset='+targetOffset);
								}
								scroll(0,targetOffset-200);
							}	
							 
						}
				 }); */
			 }else if(operation =='download' ){
				 jQuery("body").css("cursor", "progress");
				/* jQuery.ajax({
						url: 'fmReports_downloadReports.action?reportId='+reportId,
						dataType : "json",
						success: function( data ) {
							if(data.reportErrors !== ""){	
								jQuery('#errReportCount_'+formInd).html("");
								jQuery.each(data.reportErrors, function(index,value){
									errCount = true;
									jQuery('#errReportCount_'+formInd).append('<p class="errorMsg">'+value.msg+'</p>');
									jQuery('#errReportCount_'+formInd).show();
									jQuery("body").css("cursor", "default");
									return false;
								});				
							} else {	
							       var locationPath=window.location.pathname;		
								   if(locationPath.indexOf("financeManagerController_execute.action")=="-1"){
								   	location.reload();
								   }else{		
									window.location.replace('financeManagerController_execute.action?defaultTab=Reports');
									}	
								   scroll(0,targetOffset-200);
							}
							jQuery("body").css("cursor", "default");	
						}
				 }); */				
				 window.location.href = 'fmReports_downloadReports.action?reportId='+reportId+'&formInd='+formInd;
				 jQuery("#overLay").hide();
				 jQuery("body").css("cursor", "default");
				 return false;
			 }else if(operation == 'display'){
				 jQuery("body").css("cursor", "progress");
				 var totalRecords = jQuery("#recCount"+reportId).val();
				 if(totalRecords > 10000){
					 jQuery('#errReportCount_'+formInd).html("");
					 jQuery('#errReportCount_'+formInd).append('<p class="errorMsg">The report you have requested contains more than 10,000 records and cannot be displayed.'+
							 'Please review the criteria to reduce the number of records in the report or download/print the report to view all records.</p>');
					 jQuery('#errReportCount_'+formInd).show();
					 jQuery("#overLay").hide();
					 jQuery("body").css("cursor", "default");
					 return false;
				 }else if(totalRecords <=0){
					 jQuery('#errReportCount_'+formInd).html("");
					 jQuery('#errReportCount_'+formInd).append('<p class="errorMsg">No records found for submitted criteria.</p>');
					 jQuery('#errReportCount_'+formInd).show();
					 jQuery("#overLay").hide();
					 jQuery("body").css("cursor", "default");
					 return false;
				 }else{
					 window.open ('fmReports_generateReports.action?reportId='+reportId+'&formInd='+formInd,'_blank','resizable=yes,menubar=no,scrollbars=yes,height=500,width=1200,left=75,top=75',false);
					 jQuery("body").css("cursor", "default");
					 jQuery("#overLay").hide();
					 return false;
				 }
				 jQuery("body").css("cursor", "default");				
			 }
		 }			
	}	
}


/*On blur of ID filed.
 *This will validate the id length, number of ids for both buyer Id and provider id.
 */
function fnValidateIds(element){
	var userIds = jQuery(element).val();
	var formInd = jQuery(element).attr("formInd");
	var idType = jQuery(element).attr("idType");		
	var idMessage = "";
	var maxLength =0;		
	userIds = jQuery.trim(userIds);
	clearErrors();	
	clearErrorForDownload();
	idMessage = validateIds(jQuery(element).attr('id'));
	if(idMessage!==""){
		jQuery('#errReport_'+formInd).append('<p class="errorMsg">'+idMessage+'</p>');
		jQuery('#errReport_'+formInd).show();	
	}
}

/* On blur method for date fields
 *This will validate the format and whether user entered a valid date
 */
function validateDate(element){
	var dateTxt = jQuery.trim(jQuery(element).val());
	var formInd = jQuery(element).attr("formInd");
	clearErrors();
	clearErrorForDownload();
	if(dateTxt !==''){
		/* To clear the date field when chars other than 0-9 and / is entered.
			var regEx = /^[0-9\\]*$/;	             	
	        if(!regEx.test(userIds)){
  				jQuery(this).val("");
  				return false;
			} */
		if(!isDate(dateTxt)){
			jQuery('#errReport_'+formInd).append('<p class="errorMsg">Please provide a valid Date Range in the format MM/DD/YY.</p>');
			jQuery('#errReport_'+formInd).show();					
		}
	}
}

/*On click function for display and export button.
 * This will check for all mandatory fields and validate all entriees
 */
function validateForm(element){
	jQuery(".successBox").html("");
	jQuery(".successBox").hide();
	clearErrors();	
	clearErrorForDownload();
	jQuery("#overLay").show();
	jQuery('input[name="submit"]').attr('disabled','disabled');
		
	var formInd = jQuery(element).attr("formInd");
	var targetOffset = jQuery(element).offset().top;
	//var dispType = jQuery(element).attr("value");
	if(!validateInputs(formInd)){
		jQuery("#overLay").hide();
		jQuery('input[name="submit"]').removeAttr('disabled');
		//clearFields(formInd);
		return false;
	}	
	setInputs(element);	
	
	//TODO call the function which will set the user inputs to the hidden form
	// which has a mapping to back end DTO class
	var formData = jQuery('#reportInputForm').serialize();
	var errCount = false;
	jQuery.ajax({
		url: 'fmReports_validateInputs.action?formInd='+formInd,
		data : formData,
		dataType : "json",
		success: function( data ) {
			clearForm();
			if(data.reportErrors !== ""){				
					jQuery.each(data.reportErrors, function(index,value){
						errCount = true;
						jQuery('#errReport_'+formInd).append('<p class="errorMsg">'+value.msg+'</p>');
						jQuery('#errReport_'+formInd).show();
					});	
					jQuery("#overLay").hide();
					jQuery('input[name="submit"]').removeAttr('disabled');					
			} else {				
				clearForm();
			}
			if(!errCount){
			   clearFields(formInd);
			   var locationPath=window.location.pathname;		
			   if(locationPath.indexOf("financeManagerController_execute.action")=="-1"){
			   //	location.reload();
				   window.location.replace('fullfillmentAdminAction_execute.action?targetOffset='+targetOffset+'&formInd='+formInd);
			   }else{		
				//window.location.replace('financeManagerController_execute.action?defaultTab=Reports&targetOffset='+targetOffset);
				window.location.href = 'financeManagerController_execute.action?defaultTab=Reports&targetOffset='+targetOffset+'&formInd='+formInd;
				}			   
			}		
		}
	}); 
}


function clearErrors(){	
	jQuery("#errReport_so_prov").html("");
	jQuery("#errReport_so_prov").hide("");
	jQuery("#errReport_rev_prov").html("");
	jQuery("#errReport_rev_prov").hide("");

	jQuery("#errReport_so_byr").html("");
	jQuery("#errReport_so_byr").hide("");
	jQuery("#errReport_tax_byr").html("");
	jQuery("#errReport_tax_byr").hide("");

	jQuery("#errReport_pay_admn").html("");
	jQuery("#errReport_pay_admn").hide("");
	clearErrorForDownload();
}

function clearFormFields(form){
	jQuery(form).children('input, select, textarea').val('');
	jQuery(form).children('input[type=radio], input[type=checkbox]').each(function()
			{
		//this.checked = false;
		jQuery(this).attr('checked', false);
			});
}

function clearForm(){
	jQuery("#reportYear").val("");
	jQuery("#fromDate").val("");
	jQuery("#export").val("");
	jQuery("#toDate").val("");
	jQuery("#providers").val("");
	jQuery("#buyers").val("");
	jQuery("#reportName").val("");
	jQuery("#reportByCalendarYear").val("");
	jQuery("#reportByDateRange").val("");
	jQuery("#reportByPaymentDate").val("");
	jQuery("#reportByCompletedDate").val("");
	jQuery("#reportForSpecificBuyers").val("");
	jQuery("#reportForSpecificProviders").val("");
	jQuery("#reportForAllBuyers").val("");
	jQuery("#reportForAllProviders").val("");
}

function clearFields(formInd) {
	jQuery('#specificIds_'+formInd).val("");
	jQuery('#providerIds_'+formInd).val("");
	jQuery('#buyerIds_'+formInd).val("");
	jQuery('#fromDate_'+formInd).val("");
	jQuery('#toDate_'+formInd).val("");
	jQuery('#yearSelect_'+formInd+" option[value='0']").attr("selected","selected");
}
/**
This funcction will validate all the user entries and will display appropreat error
messages when error detected.
 */
function validateInputs(formInd){
	errorFound = false;
	var message = "";
	var idType ="";

	//Validates buyer ID/ Provider ID (For SLAdmin)
	message += mandatoryCheckForIds('buyerIds_', formInd);
	message += mandatoryCheckForIds('providerIds_',formInd);	
	if(message !== ''){
		errorFound = true;
	}		

	//Validates buyer IDs/Provider IDs 
	if(jQuery('#specific_'+formInd).attr('checked')){
		var ids = jQuery('#specificIds_'+formInd).val();
		var spec_id_msg = mandatoryCheckForIds('specificIds_',formInd);
		if(spec_id_msg !== ''){
			errorFound = true;
			message +=spec_id_msg;
		}
	} 
	
	var calendarRadio ="";
	//for provider
	if(formInd == 'so_prov'){
		calendarRadio = jQuery("input:radio[name='yearRadio_so_prov']:checked").val();
	}else if(formInd == 'rev_prov'){
		calendarRadio = jQuery("input:radio[name='yearRadio_rev_prov']:checked").val();
	}
	//for buyer
	if(formInd == 'tax_byr'){
		calendarRadio = jQuery("input:radio[name='yearRadio_tax_byr']:checked").val();
	}else if(formInd == 'so_byr'){
		calendarRadio = jQuery("input:radio[name='yearRadio_so_byr']:checked").val();
	}
	//for admin payments report
	if(formInd == 'pay_admn'){
		calendarRadio = jQuery("input:radio[name='yearRadio_pay_admn']:checked").val();
	}
	
   if(calendarRadio=='true'){
	  //Checks whether year is selected	
		var year = jQuery('#yearSelect_'+formInd).val();
		if(year == '0'){
			errorFound = true;			
			message  += '<p class="errorMsg">Please select a value for Calendar Year.</br></p>';
		}		
	}else{
		var fromDate = jQuery('#fromDate_'+formInd).val();
		var toDate = jQuery('#toDate_'+formInd).val();
		if(jQuery.trim(fromDate)===''||jQuery.trim(toDate)===''){
			//When date raange is not entered.
			errorFound = true;
			message += '<p class="errorMsg">Please provide a valid Date Range in the format MM/DD/YY.</br></p>';
		}else{
			//Validate dates entered.
			if(!isDate(fromDate) || !isDate(toDate)){
				errorFound = true;
				message += '<p class="errorMsg">Please provide a valid Date Range in the format MM/DD/YY.</br></p>';
			}else{
				//Validate date RANGE
				var dtMsg = validateDateRange(fromDate, toDate);
				if(dtMsg !== ''){
					errorFound = true;
					message += dtMsg;
				}		  	
			}
		}
	}
	//Displays error messages
	if(errorFound){
		jQuery('#errReport_'+formInd).append(message);
		jQuery('#errReport_'+formInd).show();
		errorFound = false;
		return false;
	}else{
		clearErrors();
	}	
	return true;
}

/**
 *Validates IDs
 */
function mandatoryCheckForIds(id, formInd){	
	id += formInd;
	if(jQuery('#'+id).length === 0){
		return '';
	}
	var ids = jQuery('#'+id).val();
	var msg="";
	if(jQuery.trim(ids)===''){
		errorFound = true;			
		idType = jQuery('#'+id).attr("idType");
		if(idType === 'buyer'){
			msg += '<p class="errorMsg">Please enter at least one valid Buyer ID.</br></p>';
		}else{
			msg += '<p class="errorMsg">Please enter at least one valid Provider ID.</br></p>';
		}
	}else{
		var idMessage = validateIds(id);
		if(idMessage !==''){
			errorFound = true;
			msg +=idMessage;
		}
	}
	return msg;

}

function validateIds(id){
	//var errMsg =" Invalid IDs are: ";
	var errMsg ="";
	var formInd = jQuery('#'+id).attr('formInd');
	var idType = jQuery('#'+id).attr("idType");
	var maxLength =0;
	var idMessage ="";
	var errorMsg ="";
	if(idType =="buyer"){
		idMessage = "Buyer";
		maxLength = 10;
	}else{
		idMessage = "Provider";
		maxLength = 10;
	}
	userIds = jQuery('#'+id).val();
	userIds = jQuery.trim(userIds);		
	if(userIds !==''){
		var regEx = /^[0-9\,]*$/;	             	
		if(!regEx.test(userIds)){
			jQuery('#'+id).val("");
			errorMsg = '<p class="errorMsg">The '+idMessage+' ID is numeric and can only contain numerals 0 to 9.</p>';
			return errorMsg;
		}
		var listOfIds = userIds.split(",");
		//Remove empty strings from list
		listOfIds = jQuery.grep(listOfIds, function (a) { return a !== ""; });
		//set formatted value to id field
		jQuery('#'+id).val(listOfIds.toString());
		if(listOfIds.length > 15){
			errorMsg = '<p class="errorMsg">The number of '+idMessage+' IDs entered exceeds the maximum allowed limit. Please enter up to 15 '+idMessage+' IDs only.</br></p>';
		}
		var duplicateIds= "";
		var flagErr = false;
		//Remove duplicate ids from the list
		var tempList = [];
		for(var idLoop=0;idLoop < listOfIds.length;++idLoop){
			var userId = listOfIds[idLoop];
			if (!Array.indexOf) {
				Array.prototype.indexOf = function (obj, start) {
				   for (var i = (start || 0); i < this.length; i++) {
				      if (this[i] == obj) {
				        return i;
				      }
				   }
				    return -1;
				}
			}
			if(tempList.indexOf(userId) < 0){
				tempList.push(userId);
			}
		}
		listOfIds = tempList;
		jQuery('#'+id).val(listOfIds.toString());
		for(var loopIds=0; loopIds < listOfIds.length;++loopIds){
			var userId = ""+jQuery.trim(listOfIds[loopIds]);			
			if(userId.length>maxLength){
				if (errMsg != ""){
					errMsg += ", "+userId;
				}else{ 
					errMsg +=userId;
				}
				flagErr = true;
			}
			var count =0;
		/* To show error message on multiple ids ara present
			for(var loop=0; loop < listOfIds.length;++loop){				
				if(jQuery.trim(listOfIds[loop]) == userId){
					++count;
				}
			}
			if(count>1){				
				if (duplicateIds != "") {
					duplicateIds += ", "+userId;
				}else{
					duplicateIds +=userId;
				}				
				//flagErr = true;
			} */
		}
	/*	if(duplicateIds != ""){
			errorMsg +='<p class="errorMsg">You have entered one more '+idMessage+' Ids multiple times.</br></p>';
		} 
		*/
		if(flagErr){
			//errorMsg = '<p class="errorMsg">One or more '+idMessage+' Ids entered is wrong. Maximum length of '+idMessage+' id is '+maxLength+'.</br>'+errMsg+'</p>';
			errorMsg += '<p class="errorMsg">The '+idMessage+' ID can contain a maximum of '+maxLength+' digits.'+'</br> Please correct the following '+idMessage+' IDs: {'+errMsg+'}</p>';
		}
	}
	return errorMsg;
}


/*
 *Function to check whether a date is a valid one.
 * Can be replaaced if a function already exists
 */
function isDate(txtDate)
{
	var currVal = txtDate;
	if(currVal === ''){
		return false;
	} 

	var rxDatePattern = /^(\d{1,2})(\/)(\d{1,2})(\/)(\d{2})$/; 
	var dtArray = currVal.match(rxDatePattern); 
	if (dtArray == null){
		return false;
	}  

	dtDay = dtArray[3];
	dtMonth= dtArray[1];
	dtYear = 20+dtArray[5];

	if (dtMonth < 1 || dtMonth > 12)
		return false;
	else if (dtDay < 1 || dtDay> 31)
		return false;
	else if ((dtMonth==4 || dtMonth==6 || dtMonth==9 || dtMonth==11) && dtDay ==31)
		return false;
	else if (dtMonth == 2)
	{
		var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
		if (dtDay> 29 || (dtDay ==29 && !isleap))
			return false;
	}
	return true;
}

function isLeapYear(year){
	var isleap = false;	
	isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
	return isleap;
}

/**
 *Validates date range
 */
function validateDateRange(fromDate,toDate){
	var dateMsg ="";
	var curDate = new Date();
	var fromDt = txtToDate(fromDate);
	if(fromDt == null)
		return '<p class="errorMsg">Please provide a valid Date Range in the format MM/DD/YY.</br></p>';
	var toDt = txtToDate(toDate);
	if(toDt == null)
		return '<p class="errorMsg">Please provide a valid Date Range in the format MM/DD/YY.</br></p>';
	if(fromDt>toDt){
		dateMsg += '<p class="errorMsg">Please provide a valid Date Range. Start Date should not be greater than End Date.</br></p>';	
	}else if(fromDt>curDate || toDt > curDate){
		dateMsg += '<p class="errorMsg">Please provide a valid Date Range. The Date Range should not extend beyond today\'s date.</br></p>';
	}else{
		if(!isValidMonthDiff(fromDt, toDt)){
			dateMsg += '<p class="errorMsg">Please provide a valid Date Range. The maximum allowed timespan for the Date Range is 13 months.</br></p>';
		}
	}
	return dateMsg;
}

/**
 *Function to convert date in string to date object.
 *Returns null when invalid format encountered.
 */
function txtToDate(txtDate){
	var rxDatePattern = /^(\d{1,2})(\/)(\d{1,2})(\/)(\d{2})$/; 
	var dtArray = txtDate.match(rxDatePattern); 
	if (dtArray == null){
		return null;
	} 
	var date = new Date(20+dtArray[5], dtArray[1]-1, dtArray[3]);
	return date;
} 

/**
 *Function which returns true if diff between two dates is less than or equal to 13 months.<b> 
 */
function isValidMonthDiff(date1, date2) {
	var year = date1.getFullYear();
	var month = date1.getMonth();
	var date = date1.getDate();
	var lastDate = new Date();
	lastDate.setDate(date1);
	lastDate.setFullYear(year+1, month+1, date-1);
	if(date2>lastDate){
		return false;
	}else{
		return true;
	}
	/* var monthsDiff= -1;
	monthsDiff = (date2.getFullYear() - date1.getFullYear()) * 12;
	monthsDiff -= date1.getMonth() + 1;
	monthsDiff += date2.getMonth() + 1;	
	var daysPerMonth = 30.4368;	
	if(isLeapYear(date2.getFullYear()) || isLeapYear(date1.getFullYear())){
		daysPerMonth = 30.5;
	}
	monthsDiff = Math.ceil((date2.getTime()-date1.getTime())/(1000*60*60*24*daysPerMonth));	
	return monthsDiff;*/	
}


function isNumberAndComma(evt){
	var charCode = (evt.which) ? evt.which : evt.keyCode
			if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode != 44 ){
				return false;
			}
	return true;
} 

function clearIds(formInd){
	 //var formInd = jQuery(radBtn).attr('formInd');
	 jQuery('#specificIds_'+formInd).val("");
	 jQuery('#specificIds_'+formInd).attr('disabled', 'disabled');	 
}

function enableIds(formInd){
	 //var formInd = jQuery(radBtn).attr('formInd');
	 jQuery('#specificIds_'+formInd).removeAttr('disabled')
}
function disableYear(formInd){
	 //jQuery('yearSelect_'+formInd).val("");
	jQuery('#yearSelect_'+formInd).attr('disabled', 'disabled');
	jQuery('#yearSelect_'+formInd+" option[value='0']").attr("selected","selected");
	jQuery('#toDate_'+formInd).removeAttr('disabled')
	jQuery('#fromDate_'+formInd).removeAttr('disabled')
}
function clearCalendar(formInd){
	 //jQuery('yearSelect_'+formInd).val("");
	jQuery('#yearSelect_'+formInd).removeAttr('disabled')
	jQuery('#fromDate_'+formInd).val("");
	jQuery('#toDate_'+formInd).val("");
	jQuery('#fromDate_'+formInd).attr('disabled', 'disabled');
	jQuery('#toDate_'+formInd).attr('disabled', 'disabled');	
}
/**
 *Function to display the flyout showing the report parameters
 */
function displayCriteria(obj){
	//var targetOffsetTop = jQuery(obj).offset().top;
	//var targetOffsetLeft = jQuery(obj).offset().left;
	var divId = obj.id.replace("link","");	
	//jQuery("#div"+divId).offset({ top: targetOffsetTop , left: targetOffsetLeft });
	jQuery("#div"+divId).show();
}

function hideCriteria(obj){
	var divId = obj.id.replace("link","");
	jQuery("#div"+divId).hide();
}

/**
 *Function to display the report status definition
 */
function displayStatus(obj){
	//var targetOffsetTop = jQuery(obj).offset().top;
	//var targetOffsetLeft = jQuery(obj).offset().left;
	var divId = obj.id.replace("span","");
	if(jQuery.trim(obj.innerHTML) == 'Completed'){
		jQuery("#line"+divId).html("The request has been successfully completed. You should be able to view," +
				" download or delete the report by clicking the respective buttons.");
	}else if(jQuery.trim(obj.innerHTML) == 'Pending'){
		jQuery("#line"+divId).html("The request has been queued for processing." +
				" If needed, you can still delete this request..");
	}else if(jQuery.trim(obj.innerHTML) == 'In Process'){
		jQuery("#line"+divId).html("The request is currently being processed. " +
				"You cannot take any action at this point of time.");
	}else if(jQuery.trim(obj.innerHTML) == 'Failed'){
		jQuery("#line"+divId).html("The request processing failed due to a technical issue. Please create a new request.  " +
				"If the report should fail a second time please call 888-549-0640 to report the problem to our Support Center.");
	}
	//jQuery("#status"+divId).offset({ top: targetOffsetTop , left: targetOffsetLeft });
	jQuery("#status"+divId).show();
}

function hideStatus(obj){
	var divId = obj.id.replace("span","");
	jQuery("#status"+divId).hide();
}

function displayDelete(obj){	
	jQuery("#overLay").show();
	jQuery("body").append('<div class="modalOverlay"></div>');
	//var targetOffsetTop = jQuery(obj).offset().top;
	//var targetOffsetLeft = jQuery(obj).offset().left;
	var divId = obj.id.replace("deleteBtn","");
	//jQuery("#deletePopUp"+divId).offset({ top: targetOffsetTop , left: targetOffsetLeft });
	jQuery("#deletePopUp"+divId).show();
}

function deleteReport(obj){
	var divId = obj.id.replace("delete","");
	jQuery("#overLay").hide();
	jQuery("#deletePopUp"+divId).hide();
	return process(obj);
}
function closePopUp(obj){	
	var divId = obj.id.replace("close","");
	jQuery("#overLay").hide();
	jQuery("#deletePopUp"+divId).hide();
}
function closeBtn(obj){	
	var divId = obj.id.replace("no","");
	jQuery("#overLay").hide();
	jQuery("#deletePopUp"+divId).hide();
}   
