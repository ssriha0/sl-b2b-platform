
// To round off a field to second decimal position
	function roundToSecondDecimal(obj){
		var secDec = obj.value.indexOf('.');
			if (secDec > -1) {secDec = secDec + 2;}
			var lastChar = obj.value.length-1;
			if (secDec > -1 && lastChar > secDec){
			obj.value = obj.value.substring(0, lastChar);
			}
	} 

	//Sort By coulumn
function sortByColumn(sortColumnName, statusSortForm){
			
var windowForm = document.getElementById(statusSortForm);
            
if(windowForm.sortColumnName.value == sortColumnName){
	if(windowForm.sortOrder.value == 'ASC'){
		windowForm.sortOrder.value = 'DESC';
	} else{
		windowForm.sortOrder.value = 'ASC';
	}
}else{
	windowForm.sortOrder.value = 'ASC';
}
	windowForm.sortColumnName.value = sortColumnName;
	windowForm.submit();
}
	


//To check maxlength of textarea
function CheckMaxLength(Object, MaxLen)
{
  if(Object.value.length > MaxLen-1)
  {      
    Object.value = Object.value.substring(0, MaxLen-1);
  }
}


function clearTextbox(el) {
	if (el.defaultValue==el.value) {
		el.value='';
		
		el.className ="shadowBox"; }
}
function existJq( elementName ){
		if( $(elementName).length>0 ){
	
			return true;
		}else {
		
			return false;
}
}
var TRIM = /^\s+|\s+$/g;
function trimJq(string) {

	return String(string).replace(TRIM, "");	
}
function validateCancellationJq(){
		var strMsg = "";
				
		var blnValidate = "true";
		if (existJq('#cancelComment')){
	
			var cancelComment = $("#cancelComment").val();
			
			
			if (trimJq(cancelComment).length == 0){
				strMsg = strMsg + "<p class='errorMsg'> Enter cancellation comment.</p>";
				}
		}
		if (existJq('#cancelAmt')){
		   var cancelAmt = $("#cancelAmt").val();
		   var currentSpendLimit = $("#currentSL").val();
		   if (trimJq(cancelAmt).length == 0){
				strMsg = strMsg + "<p class='errorMsg'> Enter cancellation amount.</p>";
		   }
		   if ((trimJq(cancelAmt).length > 0)&&(isNaN(cancelAmt))){
				strMsg = strMsg + "<p class='errorMsg'> Enter cancellation amount in decimal form.</p>";
		   }
		   if (((trimJq(cancelAmt).length > 0))&&(parseFloat(cancelAmt) > parseFloat(currentSpendLimit))){
		   		strMsg = strMsg + "<p class='errorMsg'> Requested Cancellation amount should be less than current spend limit.</p>";
		   }
		}

		 if (strMsg != ""){

        	if(existJq('#cancelSOErrorMessage'))	{
        		document.getElementById('cancelSOErrorMessage').style.visibility = 'visible';
            	document.getElementById('cancelSOErrorMessage').innerHTML = strMsg;
            	strMsg = "";
            	
         }
        	 blnValidate = "false";	
         }else
         {
     
         if(existJq('#cancelSOErrorMessage'))	{
    
         	document.getElementById('cancelSOErrorMessage').innerHTML = "";
            document.getElementById('cancelSOErrorMessage').style.visibility = 'hidden';
         
          }
         
          }
     
    
		return blnValidate;
	}
function validateCancellation(){
		var strMsg = "";
		var blnValidate = "true";
		if (newco.jsutils.isExist('cancelComment')){
			var cancelComment = $("cancelComment").value;
			if (trim(cancelComment).length == 0){
				strMsg = strMsg + "<p class='errorMsg'> Enter cancellation comment.</p>";
				}
		}
		if (newco.jsutils.isExist('cancelAmt')){
		   var cancelAmt = $("cancelAmt").value;
		   var currentSpendLimit = $("currentSL").value;
		   if (trim(cancelAmt).length == 0){
				strMsg = strMsg + "<p class='errorMsg'> Enter cancellation amount.</p>";
		   }
		   if ((trim(cancelAmt).length > 0)&&(isNaN(cancelAmt))){
				strMsg = strMsg + "<p class='errorMsg'> Enter cancellation amount in decimal form.</p>";
		   }
		   if (((trim(cancelAmt).length > 0))&&(parseFloat(cancelAmt) > parseFloat(currentSpendLimit))){
		   		strMsg = strMsg + "<p class='errorMsg'> Requested Cancellation amount should be less than current spend limit.</p>";
		   }
		}
		
		 if (strMsg != ""){
        	if(newco.jsutils.isExist('cancelSOErrorMessage'))	
        		$('cancelSOErrorMessage').style.visibility = 'visible';
            	$('cancelSOErrorMessage').innerHTML = strMsg;
            	strMsg = "";
            	blnValidate = "false";	
         }else
         {
         	$('cancelSOErrorMessage').innerHTML = "";
            $('cancelSOErrorMessage').style.visibility = 'hidden';
          }
         
		return blnValidate;
	}


function validateCompleteForPayment(){
			var blnValidate = "true";
				var partIdCount = $("partIdCount").value;
				var strMsg = "";
				var strPromptMsg="Are you sure you wish to complete this service order for a final price of zero?";
				
				if (trim($("resComments").value).length == 0){
					strMsg = strMsg + "<p class='errorMsg'>&nbsp;&nbsp;&nbsp;&nbsp;Enter Resolution Comment.</p>";
				}
				
				if (isNaN($("finalPartPrice").value)){
					strMsg = strMsg + "<p class='errorMsg'>&nbsp;&nbsp;&nbsp;&nbsp;Final Parts Price : Only number and dot is accepted.</p>";
				}
				
				if ((partIdCount > 0)&&($("finalPartPrice").value > 0.0) && (isNaN($("finalPartPrice").value))){
						strMsg = strMsg + "<p class='errorMsg'>&nbsp;&nbsp;&nbsp;&nbsp;Enter final part price in decimal form.</p>";
				}
				
				if (($("partsSl").value > 0.0)&& (parseFloat($("finalPartPrice").value) > parseFloat($("partsSl").value))){
						strMsg = strMsg + "<p class='errorMsg'>&nbsp;&nbsp;&nbsp;&nbsp;Final Part price can not be greater than parts spend limit.</p>";
				}
				
				if (isNaN($("finalLaborPrice").value)){
					strMsg = strMsg + "<p class='errorMsg'>&nbsp;&nbsp;&nbsp;&nbsp;Final Labor Price : Only number and dot is accepted.</p>";
				}
				
				if (($("finalLaborPrice").value > 0.0)&& isNaN($("finalLaborPrice").value)){
						strMsg = strMsg + "<p class='errorMsg'>&nbsp;&nbsp;&nbsp;&nbsp;Enter final labor price in decimal form.</p>";
				}
				
				if (($("laborSl").value > 0.0)&&(parseFloat($("finalLaborPrice").value) > parseFloat($("laborSl").value))){
						strMsg = strMsg + "<p class='errorMsg'>&nbsp;&nbsp;&nbsp;&nbsp;Final Labor price can not be greater than labor spend limit.</p>";
				}
				if(($("finalLaborPrice").value == 0.0)&&($("finalPartPrice").value == 0.0)){
					var result=window.confirm(strPromptMsg);
					if (!(result)){					   
					   var formfield=document.getElementById("finalPartPrice");
					   formfield.focus();					   
					   return false;			   
					}
				}	
				var returnCarrierId = 0;
				var returnTrackingNumber = "";
				var forPart = 0;
				for (var i=0;i<partIdCount;i++){
					forPart = i+1;
					returnCarrierId = $("returnCarrierId" + i).value;
					returnTrackingNumber = $("returnTrackingNumber" + i).value;
					if ((returnCarrierId == "-1") && (returnTrackingNumber != "")){
						strMsg = strMsg + "<p class='errorMsg'>&nbsp;&nbsp;&nbsp;&nbsp;Select the return Carrier for part: " + forPart + "</p>";
					}
					if ((returnTrackingNumber == "")&&(returnCarrierId != "-1")){
						strMsg = strMsg + "<p class='errorMsg'>&nbsp;&nbsp;&nbsp;&nbsp;Enter return tracking number for part: " + forPart + "</p>";
					}
				  }
				
				if (strMsg != "")
					blnValidate = "false";
					
				if (blnValidate == "false"){
					window.location.href="#";
					$('validationResponseMessage').style.visibility = 'visible';
					$('validationResponseMessage').innerHTML = strMsg;
					strMsg = "";
				}
					
				return blnValidate;
		}

function formatCurrency(num){
	num = num.toString().replace(/\$|\,/g,'');
	if(isNaN(num))
	num = "0";
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num*100+0.50000000001);
	cents = num%100;
	num = Math.floor(num/100).toString();
	if(cents<10)
	cents = "0" + cents;
	for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	num = num.substring(0,num.length-(4*i+3))+','+
	num.substring(num.length-(4*i+3));
	return (((sign)?'':'-') + '$' + num + '.' + cents);
}
function formatMoneyDecimal(num){
	num = num.toString().replace(/\$|\,/g,'');
	if(isNaN(num))
	num = "0";
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num*100+0.50000000001);
	cents = num%100;
	num = Math.floor(num/100).toString();
	if(cents<10)
	cents = "0" + cents;
	for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	num = num.substring(0,num.length-(4*i+3))+','+
	num.substring(num.length-(4*i+3));
	return (((sign)?'':'-') + num + '.' + cents);
}
function soDetailsCalcSpendLimit(){
		var strMsg = "";
		var blnValidate ="true";
		
		/*if ((newco.jsutils.isExist('increaseLimit') && $('increaseLimit').value == "") && 
		((newco.jsutils.isExist('increaseLimitParts') && $('increaseLimitParts').value == ""))){
				strMsg = strMsg + "<p class='errorMsg'> Enter increase in labor/parts spend limit amount </p>";
		}
		
		if ((newco.jsutils.isExist('increaseLimit')) && ($('increaseLimit').value != "") &&(parseFloat($('increaseLimit').value) < 0)){
           		strMsg= strMsg + " <p class='errorMsg'>Please enter positive Labor Maximum Price for Labor</p>";
       	}
       	
       	if ((newco.jsutils.isExist('increaseLimit')) && ($('increaseLimit').value != "") && (isNaN($('increaseLimit').value))){
           		strMsg = strMsg + "<p class='errorMsg'> Enter increase in labor spend limit amount in decimal form.</p>";
       	}
       	
       	if ((newco.jsutils.isExist('increaseLimitParts')) && ($('increaseLimitParts').value != "") && (parseFloat($('increaseLimitParts').value) < 0)){
           		strMsg= strMsg + " <p class='errorMsg'>Please enter positive Maximum Price for Parts</p>";
       	}
       	
       	if ((newco.jsutils.isExist('increaseLimitParts')) && ($('increaseLimitParts').value != "") && (isNaN($('increaseLimitParts').value))){
           		strMsg = strMsg + "<p class='errorMsg'> Enter increase in parts spend limit amount in decimal form.</p>";
       	}
       	
       	if (strMsg != ""){
        	if(newco.jsutils.isExist('increaseSPendLimitResponseMessage'))	
            	$('increaseSPendLimitResponseMessage').innerHTML = strMsg;
            	$('increaseSPendLimitResponseMessage').style.visibility = 'visible';
            	strMsg = "";
            	blnValidate = "false";	
         }else
         {  */	
	        $('increaseSPendLimitResponseMessage').style.visibility = 'hidden';
	        var totalAmt = 0.0;
	        var incSLLabor = parseFloat($('increaseLimit').value);
	        var incSLParts =  parseFloat($('increaseLimitParts').value);
	        if (isNaN(incSLLabor))
	        	incSLLabor = 0.0;
	        if (isNaN(incSLParts))
	        	incSLParts = 0.0;	
			/*
			totalAmt = parseFloat(newco.jsutils.removeFormatting($('currentSpendLimitLabor').value)) + 
					   incSLLabor + parseFloat(newco.jsutils.removeFormatting($('currentSpendLimitParts').value)) + 
					   incSLParts;
			*/
			totalAmt = incSLLabor + incSLParts;
					   
			if(newco.jsutils.isExist('totalAmt')){
				   $('totalAmt').innerHTML = formatCurrency(totalAmt).substring(1);
			}
			/*
			$('totalSpendLimit').value=parseFloat(newco.jsutils.removeFormatting($('currentSpendLimitLabor').value)) + 
					   incSLLabor;
			
			$('totalSpendLimitParts').value=parseFloat(newco.jsutils.removeFormatting($('currentSpendLimitParts').value)) + 
					   incSLParts;		   
			*/
			$('totalSpendLimit').value = incSLLabor;
			
			$('totalSpendLimitParts').value = incSLParts;		   
			
			$('increaseSPendLimitResponseMessage').innerHTML = "";
		//}
		
		return blnValidate;	
	}
function soDetailsCalcSpendLimitJq(){
		var strMsg = "";
		var blnValidate ="true";
	    var incSLLabor = parseFloat(document.getElementById('increaseLimit').value);
	    var incSLParts =  parseFloat(document.getElementById('increaseLimitParts').value);	
	    
	    var displayTaxObj = document.getElementById('displayTax');
	    var partsTaxPer = document.getElementById('partsTaxPer');
	    var laborTaxPer = document.getElementById('laborTaxPer');
	    
	    var displayTax = false;
	    if (null != displayTaxObj) {
	    	displayTax = displayTaxObj.value;
	    }
	    
		if (isNaN(incSLLabor)) {
				strMsg = strMsg + "<p class='errorMsg'> Enter increase in Maximum Price for Labor amount in decimal form.</p>";
		}
		if (isNaN(incSLParts)) {
				strMsg = strMsg + "<p class='errorMsg'>  Enter increase in Maximum Price for Parts amount in decimal form.</p>";
		}		
		
       	if (strMsg != ""){
        	if(existJq('#increaseSPendLimitResponseMessage'))	
            	document.getElementById('increaseSPendLimitResponseMessage').innerHTML = strMsg;
            	document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
            	strMsg = "";
            	blnValidate = "false";	
         }else
         { 
	        document.getElementById('increaseSPendLimitResponseMessage').style.visibility = 'hidden';
	        var totalAmt = 0.0;	
			/*
			totalAmt = parseFloat(newco.jsutils.removeFormatting($('currentSpendLimitLabor').value)) + 
					   incSLLabor + parseFloat(newco.jsutils.removeFormatting($('currentSpendLimitParts').value)) + 
					   incSLParts;
			*/
			totalAmt = incSLLabor + incSLParts;
			if (displayTax) {
				totalAmt = totalAmt + (null != partsTaxPer ? (isNaN(partsTaxPer.value) ? 0.00 : (partsTaxPer.value * incSLParts)) : 0.00) + (null != laborTaxPer ? (isNaN(laborTaxPer.value) ? 0.00 : (laborTaxPer.value * incSLLabor)) : 0.00);
				var maxLaborIncreaseTax = document.getElementById('maxLaborIncreaseTax');
				var maxPartsIncreaseTax = document.getElementById('maxPartsIncreaseTax');

				if (null != maxLaborIncreaseTax) {
					maxLaborIncreaseTax.innerHTML = formatCurrency(null != laborTaxPer ? (isNaN(laborTaxPer.value) ? 0.00 : (laborTaxPer.value * incSLLabor)) : 0.00).substring(1);
				}
				if (null != maxPartsIncreaseTax) {
					maxPartsIncreaseTax.innerHTML = formatCurrency(null != partsTaxPer ? (isNaN(partsTaxPer.value) ? 0.00 : (partsTaxPer.value * incSLParts)) : 0.00).substring(1);
				}
			}
					   
			if(existJq('#totalAmt')){
				    document.getElementById('totalAmt').innerHTML = formatCurrency(totalAmt).substring(1);
			}
			/*
			$('totalSpendLimit').value=parseFloat(newco.jsutils.removeFormatting($('currentSpendLimitLabor').value)) + 
					   incSLLabor;
			
			$('totalSpendLimitParts').value=parseFloat(newco.jsutils.removeFormatting($('currentSpendLimitParts').value)) + 
					   incSLParts;		   
			*/
			document.getElementById('totalSpendLimit').value = incSLLabor + (displayTax ? (null != laborTaxPer ? (isNaN(laborTaxPer.value) ? 0.00 : (laborTaxPer.value * incSLLabor)) : 0.00) : 0.00);
			
			document.getElementById('totalSpendLimitParts').value = incSLParts  + (displayTax ? (null != partsTaxPer ? (isNaN(partsTaxPer.value) ? 0.00 : (partsTaxPer.value * incSLParts)) : 0.00) : 0.00);
			
			document.getElementById('increaseSPendLimitResponseMessage').innerHTML = "";
		}
		
		return blnValidate;	
	}

function fnValidateReportResolution(){

    var validationResponseMessage=document.getElementById('validationResponseMessage');
	var blnResult = "true";
	var strMsg = "";
	if (document.getElementById("resComment").value == ""){
		strMsg = strMsg + "<p class='errorMsg'>&nbsp;&nbsp;&nbsp;&nbsp;Enter Problem Resolution comments</p>";
		blnResult = "false";
	}
	if(blnResult == "false"){
		//Commenting the following line as it is redirecting to dashboard.
		//window.location.href="#";
		
		validationResponseMessage.style.visibility = 'visible';
		validationResponseMessage.innerHTML = strMsg;
		strMsg = "";
		return;
	}else{
	validationResponseMessage.style.visibility = 'hidden';
	validationResponseMessage.innerHTML = "";
	}
	return blnResult;
}

function fnValidateReportProblem(){
	var blnResult = "true";
	var strMsg = "";
	if (document.getElementById("pbType").value == "-1"){
		strMsg = strMsg + "<p class='errorMsg'>&nbsp;&nbsp;&nbsp;&nbsp;Select Type Of Problem you want to enter</p>";
		blnResult = "false";
	}
	if(blnResult == "false"){
		window.location.href="#";
		$('validationResponseMessage').style.visibility = 'visible';
		$('validationResponseMessage').innerHTML = strMsg;
		strMsg = "";
		return;
	}
	
	return blnResult;
}

function changeDropdown(el) {
		
		el.className ="";
}

function ClearFieldsSupportTab(){
	document.getElementById("messageSupport").value = '[Message]';
	document.getElementById("subjectSupport").value = '[Subject]';
	document.getElementById("messageSupport").className="shadowBox grayText";
	document.getElementById("subjectSupport").className ="shadowBox grayText";
	document.getElementById("subjectLabelMsgSupport").style.display = "none";
	document.getElementById("messageLabelMsgSupport").style.display = "none";
	//Clear Error Messages 
	fnClearErrorMessage();
}

function ClearFieldsNotesTab(){
	document.getElementById("message").value = "";
	document.getElementById("subject").value = "";
	document.getElementById("message").className="shadowBox grayText";
	document.getElementById("subject").className ="shadowBox grayText";	
	//Clear Error Messages 
	fnClearErrorMessage();
}

function fnClearIncreaseSpendLimitPopUp(){
	document.getElementById('increaseSPendLimitResponseMessage').style.display = 'block';
	$('increaseSPendLimitResponseMessage').style.visibility = 'hidden';
	$('increaseSPendLimitResponseMessage').innerHTML = "";
	
	//reset the Labor and Parts limit to original values
	$('increaseLimit').value = newco.jsutils.removeFormatting($('currentSpendLimitLabor').value);
	$('increaseLimitParts').value = newco.jsutils.removeFormatting($('currentSpendLimitParts').value);
	$('totalAmt').innerHTML = "";		
			   
	closeModalWindow('modalIncSpendLimit');
}

function fnClearCancelPopUp(){
	document.getElementById('cancelSOErrorMessage').style.display = 'block';
	$('cancelSOErrorMessage').style.visibility = 'hidden';
	$('cancelSOErrorMessage').innerHTML = "";
	closeModalWindow('modalIncSpendLimit');
}
function fnClearCancelPopUpJq(){

	document.getElementById('cancelSOErrorMessage').style.display = 'block';

	document.getElementById('cancelSOErrorMessage').style.visibility = 'hidden';
	document.getElementById('cancelSOErrorMessage').innerHTML = "";

	//closeModalWindow('modalIncSpendLimit');
}
function fnClearErrorMessage(){
	document.getElementById('validationResponseMessage').style.display = 'block';
	document.getElementById('validationResponseMessage').style.visibility = 'hidden';
	document.getElementById('validationResponseMessage').innerHTML = "";
}
		

function SubmitAddNote(actionPath){
	
		var submitForm = document.getElementById("noteForm");
		submitForm.submit();
	
}

function SubmitReportProblem(){
		var submitForm = document.getElementById("frmReportProblem");
		submitForm.submit();
}
function SubmitReassignForm(){
	    var submitForm = document.getElementById("reassignForm");
	    submitForm.submit();
}
function  SubmitReleaseSOModal(){
	    var submitForm = document.getElementById("releaseSoForm");
	    submitForm.submit();
}
function SubmitAddNoteSupport(actionPath){
	
		var submitForm = document.getElementById("supportNoteForm");
		submitForm.submit();
	
}
	function toggleAltTaskDisabled(divID,mainCategory)
			{				
			
				var selectElement = document.getElementById(mainCategory);
				
				var doc = document.getElementsByTagName('div');
				for (var i = 0; i < doc.length; i=i+1)
				{
					if(doc[i].id == divID.toString())
					{
				   		if(selectElement.selectedIndex != 0)
				   		{
				   			doc[i].style.display = "block";
				   		}
				   		else 
				   		{
				   			doc[i].style.display = "none";
				   		}
				   	}
				}			
			}	
function loadSkillsAndCategories(elementId,formId,contextPath){
///*action url*/ url, /*call back method*/  callBackFunction, pHolder, formId
	var selectElement = document.getElementById(elementId);
	if(selectElement !=null && selectElement.selectedIndex >0 ){	
		var url = contextPath + "/"+ formId +
			"_loadSkillsAndCategories.action?mainServiceCategoryId=" + selectElement.options[selectElement.selectedIndex].value;
		selectElement.headerValue = "Loading...";
		newco.jsutils.doAjaxURLSubmit(url,skillsAndCategoriesAjaxResponse);
		
	}
}
//SL-19820
function loadSkillsAndCategories(elementId,formId,contextPath,soId){
	///*action url*/ url, /*call back method*/  callBackFunction, pHolder, formId
		var selectElement = document.getElementById(elementId);
		if(selectElement !=null && selectElement.selectedIndex >0 ){	
			var url = contextPath + "/"+ formId +
				"_loadSkillsAndCategories.action?SERVICE_ORDER_ID="+soId+"&mainServiceCategoryId=" + selectElement.options[selectElement.selectedIndex].value;
			selectElement.headerValue = "Loading...";
			newco.jsutils.doAjaxURLSubmit(url,skillsAndCategoriesAjaxResponse);
		}
	}
function loadSubcategories(elementId,hiddenElement,formId,contextPath, taskIndex){
	var selectElement = document.getElementById(elementId);
	var hiddenSelectId = document.getElementById(hiddenElement);
	
	if(selectElement !=null && selectElement.selectedIndex != -1 && hiddenSelectId != null){	
		hiddenSelectId.value = selectElement.options[selectElement.selectedIndex].value;
		
		var url = contextPath + "/"+ formId + "_loadSubcategories.action?selectedCategoryId=" +
			selectElement.options[selectElement.selectedIndex].value + 
			"&taskIndex=" + taskIndex;
		newco.jsutils.doAjaxURLSubmit(url,subCategoriesAjaxResponse);
		
	}
}

function skillsAndCategoriesAjaxResponse (xml){
	var mainServiceCategory = $('mainServiceCategory');
	mainServiceCategory.disabled = true;
	var categorySelectList = $( 'categorySelection_0');
	var skillSelectList = $('skillSelection_0');
	categorySelectList.disabled = false;
	skillSelectList.disabled = false;
	rootElement = xml.documentElement;
	//remove old options from categoryList
	categorySelectList.empty();
	//remove old options from categoryList
	skillSelectList.empty();
	var optionElement = null;
	var skillList = xml.getElementsByTagName('skill');
	var categoryList = xml.getElementsByTagName('cat');
	for(i=0; i<skillList.length;i++){
		id = skillList[i].childNodes[0].firstChild.nodeValue;
		text = skillList[i].childNodes[1].firstChild.nodeValue;
		try{
			skillSelectList.append(new Option(text, id));
		} catch(ex){
			console.log(ex);
			skillSelectList.add(new Option(text, id));
		}
	}
	
	for(i=0; i<categoryList.length; i++){
		id = categoryList[i].childNodes[0].firstChild.nodeValue;
		text = categoryList[i].childNodes[1].firstChild.nodeValue;
		try{
			categorySelectList.append(new Option(text, id));
		} catch(ex){
			console.log(ex);
			categorySelectList.add(new Option(text, id));
		}
	}
	
	
}

function subCategoriesAjaxResponse (xml){
	rootElement = xml.documentElement;
	var selectedCategoryId = rootElement.childNodes[0].childNodes[0].nodeValue;
	var taskIndex = rootElement.childNodes[1].childNodes[0].nodeValue;
	var skillSelectList = $('skillSelection_'+taskIndex);
	skillSelectList.disabled = false;
	//remove old options from skillSelectList
	while(skillSelectList.length>0){skillSelectList.remove(0);}
	//remove old options from subCategorySelect
	var subCategorySelectList = $('subCategorySelection_'+taskIndex); 
	subCategorySelectList.disabled = false;
	while(subCategorySelectList.length>0){subCategorySelectList.remove(0);}
	
	var skillList = xml.getElementsByTagName('skill');
	var optionElement = null;//document.createElement('option');
	for(i=0; i<skillList.length;i++){

		id = skillList[i].childNodes[0].firstChild.nodeValue;
		text = skillList[i].childNodes[1].firstChild.nodeValue;
		
		try{
			skillSelectList.append(new Option(text, id));
		} catch(ex){
			console.log(ex);
			skillSelectList.append(new Option(text, id));
		}
	}

	
	var subcategoryList = xml.getElementsByTagName('subcat');

	if(subcategoryList != null && subCategorySelectList != null){

		if(subcategoryList.length < 1){
			subCategorySelectList.append(new Option("No Sub-Categories Available", "-1"));
			subCategorySelectList.val("-1");
		}
		for(i=0; i<subcategoryList.length;i++){
			id = subcategoryList[i].childNodes[0].firstChild.nodeValue;
			text = subcategoryList[i].childNodes[1].firstChild.nodeValue;
			
			try{
				subCategorySelectList.append(new Option(text, id));
			} catch(ex){
				subCategorySelectList.append(new Option(text, id));
			}
		}
		if(subcategoryList.length > 0){
			subCategorySelectList.disabled = false;
		}
	}

}
//SL-19820
function loadSubcategories(elementId,hiddenElement,formId,contextPath, taskIndex,soId){
	var selectElement = document.getElementById(elementId);
	var hiddenSelectId = document.getElementById(hiddenElement);
	
	if(selectElement !=null && selectElement.selectedIndex != -1 && hiddenSelectId != null){	
		hiddenSelectId.value = selectElement.options[selectElement.selectedIndex].value;
		
		var url = contextPath + "/"+ formId + "_loadSubcategories.action?SERVICE_ORDER_ID="+soId+"&selectedCategoryId=" +
			selectElement.options[selectElement.selectedIndex].value + 
			"&taskIndex=" + taskIndex;
		newco.jsutils.doAjaxURLSubmit(url,subCategoriesAjaxResponse);
		
	}
}

function loadSubcategoriesSkills(elementId,hiddenElement,formId,contextPath, taskIndex){
	
	var selectElement = document.getElementById(elementId);
	var hiddenSelectId = document.getElementById(hiddenElement);
	
	if(selectElement !=null && selectElement.selectedIndex != -1 && hiddenSelectId != null){	
		hiddenSelectId.value = selectElement.options[selectElement.selectedIndex].value;
		
		var url = contextPath + "/"+ formId + "_loadSubCategoriesSkills.action?selectedCategoryId=" +
			selectElement.options[selectElement.selectedIndex].value + 
			"&taskIndex=" + taskIndex;
		newco.jsutils.doAjaxURLSubmit(url,newco.jsutils.subCategoriesSkillsAjaxResponse);
		
	}
}
//Sl-19820
function loadSubcategoriesSkills(elementId,hiddenElement,formId,contextPath, taskIndex,soId){
	
	var selectElement = document.getElementById(elementId);
	var hiddenSelectId = document.getElementById(hiddenElement);
	
	if(selectElement !=null && selectElement.selectedIndex != -1 && hiddenSelectId != null){	
		hiddenSelectId.value = selectElement.options[selectElement.selectedIndex].value;
		if(null!= hiddenSelectId.value && hiddenSelectId.value != -1){
		    var url = contextPath + "/"+ formId + "_loadSubCategoriesSkills.action?SERVICE_ORDER_ID="+soId+"&selectedCategoryId=" +
			          selectElement.options[selectElement.selectedIndex].value + 
			          "&taskIndex=" + taskIndex;
		    newco.jsutils.doAjaxURLSubmit(url,newco.jsutils.subCategoriesSkillsAjaxResponse);
		}
	}
}

//SL-21373
function loadCatSubcatSkills(elementCatId,elementSubCatId,elementSkillId,formId,contextPath,taskIndex,soId){
			var selectElement1 = document.getElementById(elementCatId);
			var selectElement2 = document.getElementById(elementSubCatId);
			var selectElement3 = document.getElementById(elementSkillId);
			
		    var url = contextPath + "/"+ formId + "_loadCatSubcatSkills.action?SERVICE_ORDER_ID="+soId+"&catId=" +
		    selectElement1.options[selectElement1.selectedIndex].value +"&subCatId=" +
		    selectElement2.options[selectElement2.selectedIndex].value +"&skillsId=" +
		    selectElement3.options[selectElement3.selectedIndex].value + 
	          "&taskIndex=" + taskIndex;;
		    newco.jsutils.doAjaxURLSubmit(url,newco.jsutils.subCategoriesSkillsAjaxResponse);
}

function fnCredentialCategory(elementId,formId,contextPath,actionName)
{	
	var selectElement = document.getElementById(elementId);
	if(selectElement !=null && selectElement.selectedIndex != -1){	
	var url = contextPath + "/"+actionName+".action?credentialId="+selectElement.value;
	newco.jsutils.doAjaxURLSubmit(url,newco.jsutils.credentialCategoryAjaxResponse);

	}
}	
	
function deletePart(formId,contextPath,part, soId){
		var loadForm = document.getElementById(formId);
		if (part != null) {
			var viewPart = part + 1;
			loadForm.deletePartIndex= part;
			window.location.href = contextPath + '/'+ formId + '_deletePart.action?SERVICE_ORDER_ID='+soId+'&deletePartIndex=' + part;
			return true;
 		}
		else
		{
		 	return false;
		}

}

function enableDisableOtherCareer(formId,elementId,career){
	var loadForm = document.getElementById(formId);
	var selectElement = document.getElementById(elementId);
	var otherCareer = document.getElementById(career);
		if(selectElement !=null && selectElement.selectedIndex == 5){
			otherCareer.disabled = false;
		}
		else
		{
			otherCareer.value = ""; 
		  	otherCareer.disabled = true;
		}
}
function setCustomRef(element, formId, contextPath){
	if(document.getElementById(elementId) !=null){	
		var loadForm = document.getElementById(formId);
		loadForm.action = contextPath + "/"+ formId + "_setCustomRef.action";
		loadForm.submit();
	}	
}


function enableLocation( idx ){
	if($('serviceLocation'+idx)){
		if($('serviceLocation'+idx).style.display == "block"){
			  $('serviceLocation'+idx).style.display = "none";
		}
		else {
			  $('serviceLocation'+idx).style.display = "block";
		}
	}
}


/*
 * Copy SELECT value to a hidden field
 *Update an element's value property dynamically. This can be called from the select element's events(onchange, etc)
 *Parameters: updateElementValue(elementToUpdate, elementValueComingFrom)
 * */
function updateElementValue(elementToUpdate, elementValueComingFrom, subCategoryId, subSelectionId) {
	updatedElement = document.getElementById(elementToUpdate);
	elementValueToCopy = document.getElementById(elementValueComingFrom);
	subCategory = document.getElementById(subCategoryId);
	subSelectedCategory = document.getElementById(subSelectionId);
		if(updatedElement != null && elementValueToCopy != null){
			updatedElement.value = elementValueToCopy.options[elementValueToCopy.selectedIndex].text;
			if(subCategory != null){
				
				subCategory.value = "";
				
			}
			if(subSelectedCategory != null){
				subSelectedCategory.selectedIndex = -1;		
				if(subSelectedCategory.selectedIndex < 0 &&
			 	   subSelectedCategory.options.length == 1){
			 	   // auto select "Not Applicable"
			 		subSelectedCategory.options[0].selected = true;
			 }
			}
	}
}

function checkSelectType(selectBoxId, elementId) {

	 this.theValue = document.getElementById(selectBoxId).options[document.getElementById(selectBoxId).selectedIndex].value;
	
	if(this.theValue != 3)
	{
	
	

		document.getElementById(elementId).value = "";
		document.getElementById(elementId).style.display = 'none';
		return;
	}
	else
	{
		document.getElementById(elementId).value = "";
		
		document.getElementById(elementId).style.display = 'block'
		return;
	}
}

function countAreaChars(areaName,counter,limit, evnt)
{
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
	else
		counter.value = limit - areaName.value.length;
}
//Summary & Details Tab - Sub Status Change button	
function displaySubStatuses(){
var subStatusDrop=document.getElementById('subStatusDropdown');
subStatusDrop.style.display="block";
var subStatusStr=document.getElementById('subStatusString');
var subStatusBtn=document.getElementById('subStatusBtn');
subStatusStr.style.display="none";
subStatusBtn.style.display="none";
/*
	$('subStatusDropdown').style.display="block";
	$('subStatusString').style.display="none";  */
}

function submitSummarySubStatus(){
var subStatusIndex=document.getElementById('subStatusId');
var subStatusInd=subStatusIndex.selectedIndex;
var subStatusValue=document.getElementById('subStatusId');
var subStatusVal=subStatusValue.options[subStatusInd].value;
Uvd('subStatusIdChanged',subStatusVal); 
var summarySubStatusChange=document.getElementById('summarySubStatusChange');
	summarySubStatusChange.submit();
	/*
	var subStatusIndex = $('subStatusId').selectedIndex;
	var subStatusValue = $('subStatusId').options[subStatusIndex].value;
		newco.jsutils.Uvd('subStatusIdChanged',subStatusValue); 
	$('summarySubStatusChange').submit();
	
	*/
}

function cancelSummarySubStatus(){
var subStatusDrop=document.getElementById('subStatusDropdown');
subStatusDrop.style.display="none";
var subStatusStr=document.getElementById('subStatusString');
var subStatusBtn=document.getElementById('subStatusBtn');
subStatusStr.style.display="block";
subStatusBtn.style.display="block";
	}
	
function hideSavePanel(rowCounter){
	document.getElementById('editCustRef'+rowCounter).style.display="inline";
	document.getElementById('savePanel'+rowCounter).style.display="none";
	document.getElementById('refValLbl'+rowCounter).style.display="inline";
	document.getElementById('refVal'+rowCounter).style.display="none";
	document.getElementById('refVal'+rowCounter).value=document.getElementById('refValLbl'+rowCounter).innerHTML;
}                                
	
function showSavePanel(rowCounter){
	document.getElementById('editCustRef'+rowCounter).style.display="none";
	document.getElementById('savePanel'+rowCounter).style.display=""; // "table-row" doesn't work in IE7, "inline" cuases alignment issue in FireFox 
	document.getElementById('refValLbl'+rowCounter).style.display="none";
	document.getElementById('refVal'+rowCounter).style.display="inline";
	document.getElementById('refVal'+rowCounter).focus();
		
} 
	
function submitBuyerReference(rowCount){
	var refType = document.getElementById('refTypeLbl'+rowCount).innerHTML;
	var refVal = document.getElementById('refVal'+rowCount).value;
	var refValOld = document.getElementById('refValLbl'+rowCount).innerHTML;
	if(refVal != refValOld){
	document.getElementById('refType').value = refType;
	document.getElementById('refVal').value = refVal;
	document.getElementById('refValOld').value = refValOld;
	var summaryBuyerReferenceChange=document.getElementById('summaryBuyerReferenceChange');
	summaryBuyerReferenceChange.submit();
	}
}	

function editPartsShipping(partCount,divIndex){

var partsShippingEdit;
var partsShippingInfo;

	for (var count=0 ; count < partCount; count++) {
	
	partsShippingEdit=document.getElementById('partsShippingEdit'+divIndex+'[' + count + ']');
	partsShippingEdit.style.display="block";
	partsShippingInfo=document.getElementById('partsShippingInfo'+divIndex+'[' + count + ']');
	partsShippingInfo.style.display="none";

	/*
		$('partsShippingEdit[' + count + ']').style.display="block";
		$('partsShippingInfo[' + count + ']').style.display="none";
		*/
	}

	var savePartsShipping=document.getElementById('savePartsShipping'+divIndex);
	var editPartsShipping=document.getElementById('editPartsShipping'+divIndex);
	savePartsShipping.style.display="block";
	editPartsShipping.style.display="none";

}

function submitPartsShippingInfo(divIndex){
var editPartsShipping=document.getElementById('editPartsShippingInfo'+divIndex);

	editPartsShipping.submit();
}

function cancelEditPartsShipping(partCount,divIndex){
var partsShippingEdit;
var partsShippingInfo;
for (var count=0 ; count < partCount; count++) {
partsShippingEdit=document.getElementById('partsShippingEdit'+divIndex+'[' + count + ']');
	partsShippingEdit.style.display="none";
	partsShippingInfo=document.getElementById('partsShippingInfo'+divIndex+'[' + count + ']');
	partsShippingInfo.style.display="block";
		/*$('partsShippingEdit[' + count + ']').style.display="none";
		$('partsShippingInfo[' + count + ']').style.display="block";*/
	}
	var savePartsShipping=document.getElementById('savePartsShipping'+divIndex);
	var editPartsShipping=document.getElementById('editPartsShipping'+divIndex);
	savePartsShipping.style.display="none";
	editPartsShipping.style.display="block";
	/*$('savePartsShipping').style.display="none";
	$('editPartsShipping').style.display="block";*/
}




function openProviderProfile(resourceid, companyid, project)
{	
	if (document.openProvURL != null)
	{
		document.openProvURL.close();
	}
	//var project = '/MarketFrontend/'; // TODO get this as a parameter
	var url =project+"providerProfileInfoAction_execute.action?resourceId="+resourceid+ "&companyId="+ companyid+"&popup=true";
	newwindow=window.open(url,'_publicproviderprofile','resizable=yes,scrollbars=yes,status=no,height=700,width=1000');
	if (window.focus) {newwindow.focus()}
	document.openProvURL = newwindow;
}


function openProviderFirmProfile(vendorId) 
{
	if (document.openProvURL != null)
	{
		document.openProvURL.close();
	}
	var url = "/MarketFrontend/providerProfileFirmInfoAction_execute.action?vendorId=" + vendorId + "&popup=true";
	newwindow=window.open(url,'_publicproviderprofile','resizable=yes,scrollbars=yes,status=no,height=700,width=1000');
	if (window.focus) {newwindow.focus()}
	document.openProvURL = newwindow;
}



function reset_edit_account(email, pp, sp)
{
	document.edit_account.email.value=email;
	document.edit_account.primaryPhone.value=pp;
	document.edit_account.secondaryPhone.value=sp;
}
	// updateTheNode:
	//		A registry to make contextual calling/searching easier.
	// description:
Uvd = function(/* hidden val id*/ hvid, /*node value*/ nodeVal){
		if(isExist(hvid) ){
		document.getElementById(hvid).value = nodeVal;
	 	}
	}
// isExist:
	//		A registry to make contextual calling/searching easier.
	// description:
	//		Objects of this class keep list of arrays in the form [name, check,
	//		wrap, directReturn] that are used to determine what the contextual
	//		result of a set of checked arguments is. All check/wrap functions
	//		in this registry should be of the same arity.
isExist = function( elementName ){
		if( $(elementName) ){
			return true;
		}else {
			return false;
}}