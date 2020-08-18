// SS
var ie6 = false;
if (navigator.userAgent.indexOf("MSIE 6") > 0) ie6=true;

var _jqm_comment= null;

var _firms=       null;
var _customRefs=  null;
var _marketsZips= null;
var _specJobsMap= null;
var firmClickCount = 0;

var _q_rid= '';

jQuery(document).ready(function() {
 	jQuery("#copyRuleTrigger").click(function(){
    if(this.checked) {jQuery(".copyRule").show();}
    	else {jQuery(".copyRule").hide();}
    });
    
    jQuery(document).click(function(e){ 
		var click=jQuery(e.target);
		if((click.is("#ruleComments") || click.parents().is("#ruleComments")) && !(click.closest("a").hasClass("cancelLink"))){
			e.stopPropagation();
		}else if(click.closest("a").hasClass("commentsClick")){
			jQuery("#ruleComments").css('visibility','visible');
		}else{
			jQuery("#ruleComments").css('visibility','hidden');
		}
	});
	
    jQuery("input").click(function (e){
		var click=jQuery(e.target);
		var id = click.prop("id");
		if(id=="makeInactiveCheck"||id=="saveNDone"){
		}else{
			jQuery("#activeEditConflict").empty().html("");
		}
	 		
	});
	
    jQuery(".deleteIcon").click(function (e){
	 	jQuery("#activeEditConflict").empty().html("");
	 });
	 
	 jQuery(".deleteLink").click(function (e){
	 	jQuery("#activeEditConflict").empty().html("");
	 });
	 
	jQuery("#confirmDialog").jqm();

	jQuery("#addFirmId-button").click(function()     { 
	if(firmClickCount==0){addFirmId(); }
	});
	jQuery("#makeInactiveCheck").click(function()     {
	 	var obj =jQuery("#makeInactiveCheck");
		var state= obj.checked ? "makeInactive" : "";
		jQuery("#makeInactive").val(state);
	});
	
	jQuery("#editConflict").click(function()     {
		jQuery("#editConflict").hide();
	}); 
    jQuery("#addCustomRef-button").click(function()  { addCustomReference(); });
    jQuery("#addMarket-button").click(function()     { addMarket(); });
    jQuery("#addZip-button").click(function()        {
    addToZipList(); 
    });
    jQuery(".pageNoText").keypress(function (e){
	
  			//if the letter is not digit then display error and don't type anything
  			if( e.which!=8 && e.which!=0  && (e.which<48 || e.which>57))
  			{
   				return false;
 			}
	});
   
    jQuery("#addJobCodes-button").click(function()   {addJobCodesToList(); });
	jQuery("#priceAll").blur(function() {
  	
	      if(!isNaN(this.value) & this.value != ''){
	          var amt = parseFloat(this.value);
	          var finalAmt = parseFloat(amt).toFixed(2);
	          jQuery(this).val(finalAmt);
	         }          
	});
    jQuery(".chosenJobPrices").blur(function() {
    
	      if(!isNaN(this.value) & this.value != ''){
	          var amt = parseFloat(this.value);
	          var finalAmt = parseFloat(amt).toFixed(2);
	          jQuery(this).val(finalAmt);
	         }          
	});
	//ensures only numbers are entered
    jQuery(".chosenJobPrices").keypress(function (e){
	
  			//if the letter is not digit then display error and don't type anything
  			if( e.which!=8 && e.which!=0 && e.which!=46 && (e.which<48 || e.which>57))
  			{
   				return false;
 			}
	});
	//ensures only numbers are entered
    jQuery("#priceAll").keypress(function (e){
	
  			//if the letter is not digit then display error and don't type anything
  			if( e.which!=8 && e.which!=0 && e.which!=46 && (e.which<48 || e.which>57))
  			{
   				return false;
 			}
	});
    jQuery("#copyRule-button").click(function()      { copyRule(); });

    _q_rid= getUrlParameters().rid;

    // Select market/zip radio button when market or zip widget is used
    jQuery("#zipCode-textbox").focus(function()      { jQuery("#zipAddMethodZip").prop("checked", "zip"); });
    jQuery("#zipCodeByState-select").focus(function(){ jQuery("#zipAddMethodState").prop("checked", "state"); });

    // SL-7981: reset zipcode by state dropdown when zipcode radio is selected
    jQuery("#zipAddMethodZip").click (function()     { jQuery("#zipCodeByState-select")[0].selectedIndex=0; jQuery('#zipCode-textbox').select().focus(); });

    // when a state is selected, populate list of zips within that state
    jQuery("#zipCodeByState-select").change(function() {
        var state= jQuery(this).val();

        setCursorWait(true);
        $.getJSON ('rrJson_getZipcodesByStateAbbrev.action', {state: state}, function(data) {
            setCursorWait(false);

            var wrapperHtml= '<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Zip Codes in ' + state + '</p>'
                + '\n<div id="zipCodesInState-container" class="dropdownTableContainer" style="margin-left:85px">'
                + '\n<table id="zipCodesInState-table" class="dropdownTable" border="0" cellpadding="0" cellspacing="0" >'
                + '\n<tr><td colspan="2" style="height:15px;">&nbsp;</td></tr>';

            $.each(data, function(label, zipCityState) {
                wrapperHtml += '\n<tr><td class="checkboxTd"><input class="multiSelectCheckbox" name="zipcodeChoices-checks" value="' + zipCityState.label + '" type="checkbox"/></td><td>' + zipCityState.label + '</td></tr>';
            });
            wrapperHtml += '\n</table></div>';

            jQuery("#zipCodesInState-wrapper").empty().html(wrapperHtml).show();
            jQuery("#zipCodesInState-container").show();
        });
    });


    // Disable custom ref textbox if no custom ref is selected
    jQuery("#customRef-select").change( function() {
        if (jQuery(this).val() == '-1') 
            jQuery('#customRef-textbox').prop("disabled", "disabled");
        else jQuery('#customRef-textbox').removeAttr("disabled").select().focus();
    });


    // when a specialty is selected, populate jobcodes dropdown
    jQuery("#specialtyCode-select").change( function() {
        var spec= jQuery(this).val();

        if (spec == '-1') {
            jQuery("#jobCode-select")[0].selectedIndex= -1;
            jQuery("#jobCode-select").prop("disabled","disabled");

            jQuery("#addAllJobCodes").prop("disabled", "disabled");
            jQuery("#addAllJobCodes").prop("checked", false);
            return;
        }

        jQuery("#jobCode-select > option:eq(0)").html("Select all that apply");
        
        setCursorWait(true);
        $.getJSON ('rrJson_getJobcodesForSpecialtyCode.action', {spec: spec}, function(data) {
            setCursorWait(false);

            var tableHtml= '<tr><td colspan="2" style="height:15px;">&nbsp;</td></tr>';
            jQuery("#dropdownTableJobCode").empty();

            $.each(data, function(label, jobcode) {
                var cbval= spec + '@@' + jobcode.value;
                tableHtml += '<tr><td class="checkboxTd">'
                    + '<input type="checkbox" id="jobcodeChoice-' + cbval + '-check" class="multiSelectCheckbox" value="' + cbval + '" />'
                    + '</td><td><label for="jobcodeChoice-' + cbval + '-check">' + jobcode.value + '</label></td></tr>';
            });
            jQuery("#dropdownTableJobCode").html(tableHtml);

            var jobcodeChecks = jQuery("#dropdownTableJobCode > tbody >tr > td > .multiSelectCheckbox");
            jQuery(jobcodeChecks).click(function() {
                jQuery('#addAllJobCodes').prop("checked",updateNSelected (jobcodeChecks, jQuery("#jobCode-select > option:eq(0)")));
            });
            jQuery("#addAllJobCodes").removeAttr("disabled");
            jQuery("#jobCode-select").removeAttr("disabled");
        });
    });


	// hide dropdown checklists
	jQuery("body").click(function (e) {

		var x = e.pageX;
		var	y = e.pageY;
		var dId = null; 
		var len = jQuery(".dropdownTableContainer").length;
		
		for (i=0;i<len; i++) {
			if(jQuery(".dropdownTableContainer").eq(i).css("display") == 'block') {
				dId =	jQuery(".dropdownTableContainer").eq(i).prop('id');
			}
		}
		
		if(dId != null) {
			var dLeft = jQuery("#"+dId).position().left;
			var dTop = jQuery("#"+dId).position().top;
			dHt = jQuery("#"+dId).height();
			var targetClick = jQuery(e.target).prop('id');
	        var marketChoicesIndex = targetClick.indexOf("marketChoices");
	        var byMarketIndex = targetClick.indexOf("byMarket");
	        
	        // If the click is outside the drop down, hide the select
	        if(marketChoicesIndex == -1 && byMarketIndex == -1){
	        	byMarketMultiSelectShow(false);
	        }
			if (x < dLeft || x > dLeft +150 || y < dTop -25 || y > dTop + dHt) {
                jQuery("#zipCodesInState-wrapper").empty();
                jQuery("#zipCodeByState-select")[0].selectedIndex=0;
			}
		}
	});


	// show/hide byMarket dropdown checklist
	jQuery("#byMarket").click(function() {
		if (jQuery("#byMarketTableContainer").css("display") == "block") {
			byMarketMultiSelectShow(false);
		} else {
			byMarketMultiSelectShow(true);
		}
        jQuery("#byMarket").blur();
        jQuery("#byMarketTableContainer").focus();
	});	
	
	
	// show/hide jobcode dropdown checklist
	jQuery("#jobCode-select").click(function() {
		jobCodeMultiSelectShow(jQuery("#jobCodeTableContainer").css("display") != "block");
    	jQuery("#jobCode-select").blur();
	});


    // 'Select all jobcodes' checkbox
    jQuery("#addAllJobCodes").click(function() {
        if(jQuery(this).prop("checked")) {
            setChecked('dropdownTableJobCode',1);
        } else {
            setChecked('dropdownTableJobCode',0);
        }
        updateNSelected (
            jQuery("#dropdownTableJobCode > tbody >tr > td > .multiSelectCheckbox"),
            jQuery("#jobCode-select > option:eq(0)"));
    });	


	// display number of selected items - byMarket
	var byMarketChbx = jQuery("#dropdownTableByMarket > tbody >tr > td > .multiSelectCheckbox");
	jQuery(byMarketChbx).click(function() {
        updateNSelected (byMarketChbx, jQuery("#byMarket > option:eq(0)"));
	});


    // POST the form
    jQuery("#modalSaveDone").click(function() {
        _jqm_comment= jQuery("#ruleComment").get(0).value;
		_jqm_comment=jQuery.trim(_jqm_comment);
        // Ensure comment was entered
        if (_jqm_comment == null || _jqm_comment == '') {
            jQuery("#ruleComment").focus();
            jQuery("#ruleComment").css("border-color","red");
            return;
        }

        jQuery('input[name="firms-chosen-checks"]').prop("checked",true);
        jQuery('.customRefs-chosen-checks').prop("checked",true);
	    jQuery('input[name="marketsZips-chosen-checks"]').prop("checked",true);
        jQuery('input[name="jobcodes-chosen-checks"]').prop("checked","checked");
        jQuery('#firmId-textbox').prop("disabled",true);

        unescFormValues();

        jQuery("#modalSaveDone").prop("disabled", "disabled");
        setCursorWait(true);
    	jQuery("#createRule > #ruleComment").val(jQuery("#dialogForm > textarea").val());
    	jQuery("#save").val("save");
    	if(jQuery("#jobCodePrice")){
    		jQuery("#job_price_save").val(jQuery("#jobCodePrice").val());
    	}
        
        $('#confirmDialog').jqmHide();
        jQuery('#createRule').prop('onsubmit','return true;');
        jQuery("#createRule").submit();
        return true;
    });


    // Create objects to hold users chosen attributes
    //SL-15642 Making changes to accomodate SL status column if auto acceptance is ON
  
    if(autoAcceptRuleStatus=='true')
    	{
    	  _firms= new ChosenAttrs ('firms',       '_firms',         ['Provider Firm','SL Status','Auto Acceptance Status']);
    	  if(userAction!='VIEW')
    		  {
    	  _firms.addRowsByJSON = function(json) {
    	        $.each(json, function() {
    	        	_firms.addRow ('<a  style="color: #00A0D2;" href="companyTabAction.action?getManageRuleProviderProfileDetail=true&vendorFirmId='+this.id+'" target="_blank"><strong>' + this.businessName + '</strong></a><br /><label style="padding-left:20px;">ID #' + this.id+'</label>', this.id, [this.state.wfState],[this.autoAcceptStatusInfo.autoAcceptStatus]);
    	        });
    	    }
    		  }
    	  else
    		  {
    		  _firms.addRowsByJSON = function(json) {
      	        $.each(json, function() {
      	        	_firms.addRow ('<strong>' + this.businessName + '</strong></a><br /><label style="padding-left:20px;">ID #' + this.id+'</label>', this.id, [this.state.wfState],[this.autoAcceptStatusInfo.autoAcceptStatus]);
      	        });
      	    }
    		  }
    	}
    else{
    _firms= new ChosenAttrs ('firms',       '_firms',         ['Provider Firm','SL Status']);
    _firms.addRowsByJSON = function(json) {
        $.each(json, function() {
        	_firms.addRow ('<strong>' + this.businessName + '</strong><br /><label style="padding-left:20px;">ID #' + this.id+'</label>', this.id, [this.state.wfState]);
        });
    }
    }
   
    _customRefs= new ChosenAttrs ('customRefs',  '_customRefs',    ['Custom Reference','Value']);
    _customRefs.addRowsByJSON = function(json) {
        $.each(json, function() {
            if ('ZIP' !== this.name && 'MARKET' !== this.name) {
                _customRefs.addRow ('<strong>' + this.label + '</strong>', this.name + '@@' + this.value, [this.value]);
            }
        });
    }

    _marketsZips= new ChosenAttrs ('marketsZips', '_marketsZips',   ['Market/Zip', 'Value']);
    _marketsZips.addRowsByJSON = function(json) {
        $.each(json, function() {
            if ('ZIP' === this.name) {
                _marketsZips.addRow ('<strong>Zip Code</strong>', this.value, [this.label]);
            } else if ('MARKET' === this.name) {
                _marketsZips.addRow ('<strong>Market</strong>', this.value, [this.label]);
            }
        });
    }

    _specJobsMap= { };
    _specJobsMap.addRowsByJSON = function(json) {

        $.each(json, function() {
            var specJobs= _specJobsMap[this.specialty];
            if (specJobs == null) {
                specJobs= new ChosenSpecJobs(this.specialty, this.autopull);
                _specJobsMap[this.specialty]= specJobs;
            }
            $.each(this.jobcodes, function() {
                specJobs.addJobcode (this.jobcode, this.price);
            });
        });
    }

    _specJobsMap.isEmpty = function() {
        for (var x in _specJobsMap)
            if (x !== 'isEmpty' && x !== 'addRowsByJSON' && x != 'deleteSelected') 
                return false;
        return true;
    }

    _specJobsMap.deleteSelected = function() {
        for (var spec in _specJobsMap)
            if (spec !== 'isEmpty' && spec !== 'addRowsByJSON' && spec != 'deleteSelected') 
                _specJobsMap[spec].deleteSelected();
    }


    // Populate RHS chosen attrs
    if (userAction == 'VIEW' || userAction == 'EDIT') {
        _firms.addRowsByJSON (chosenProviderFirmsJSON);
        
    } else {
        jQuery("#ruleName").focus();
    }
	jQuery("#validCustRef").val("");
	jQuery("#validJobCode").val("");
	jQuery("#validZipCount").val("");
   
});

function showConflict(){
	jQuery("#editConflict").show();
}

// Called via SaveDone button
function validateFields() {
    jQuery("#validationError_jobCodes").html("");
	var conflict = jQuery("#activeEditConflict").html();	
	if(conflict.indexOf("makeInactiveCheck")!=-1){		
		if(jQuery("#makeInactiveCheck").prop("checked")==true){
			jQuery("#makeInactive").val("makeInactive");
			jQuery("#ruleComment").val("Make Inactive");
			jQuery("#createRule :input").prop("disabled", false);
			jQuery('input[name="firms-chosen-checks"]').prop("checked",true);
			jQuery('.customRefs-chosen-checks').prop("checked",true);
	        jQuery('input[name="marketsZips-chosen-checks"]').prop("checked",true);
	        jQuery('input[name="jobcodes-chosen-checks"]').prop("checked","checked");
	        if(jQuery("#jobCodePrice")){
    			jQuery("#job_price_save").val(jQuery("#jobCodePrice").val());
    		}
			jQuery("#save").val("save");
			setCursorWait(true);
			jQuery("#createRule").submit();
        	return true;
		
		}
		else{
			//jQuery("#cancelEdit").trigger("click");
			//var tabType = jQuery("#tabType").val();
			//setCursorWait(true);
			//window.location.href= 'rrDashboard_decision.action?tabType='+tabType;
	        return false;
		}
	}else{
	
	
		jQuery("#activeEditConflict").empty().html("");
	    var tabVal = jQuery('#tabType').val();
	    if (userAction == 'VIEW') {
	        window.location.href= 'rrDashboard_decision.action?tabType='+tabVal;
	        return false;
	    }
	
	    var errmsg= '';
	
	    // Ensure required textboxes are filled
	    $.each({   '#ruleName'      : 'Rule Name'
	              ,'#firstName'     : 'First Name'
	              ,'#lastName'      : 'Last Name'
	              ,'#email'         : 'Email' 
	              ,'#verifyEmail'   : 'Verify Email' 
	           }, 
	           function (fieldId, fieldLabel) {
	               val = jQuery(fieldId).get(0).value;
	               if (val == '' || val.length < 1) {
	                   if (errmsg == '')
	                       errmsg= 'Please complete the following required fields:<br />';
	                   errmsg += '&nbsp;&nbsp;&nbsp;&nbsp;' + fieldLabel + '<br />';
	               }
	           }
	    );
	
	    if (!isRuleNameUnique()) {
	        errmsg += 'The rule name you entered already exists<br />';
	    }
	
	    if (_firms.isEmpty())
	        errmsg += 'You must choose at least one firm ID<br />';
	        
		if (jQuery("#marketsZips-tableId").length == 0 && jQuery("#customRefs-chosen").length == 0 && jQuery("#jobcodes-chosen").length == 0){		
	        errmsg += 'You must choose at least one Custom Reference, Area, or Job Code<br />';
	    }
	
	    var email= jQuery("#email").get(0).value;
	    if (email != jQuery("#verifyEmail").get(0).value) {
	        errmsg += 'Email fields do not match<br />';
	    } else if (email != null && email != '' && !isValidEmailAddress(email)) {
	        errmsg += 'Invalid email address<br />';
	    }
	
	    jQuery('.jobCodePrice').each(function() { 
	        var price=jQuery(this).val();
	        if (!isValidPrice(price)) {
	            errmsg += 'Invalid job code price: ' + price + '<br />';
	        }
	    });
	    
		  	
	  	
	    if (errmsg == '') {
	   	var invalidPrice="";
	  	var jobPrice="";
	    if(jQuery("#jobCodePrice")){
    			jobPrice = jQuery("#jobCodePrice").val();
    	}
		$.getJSON ('rrJson_jobPriceCheck.action', {job_price:jobPrice}, function(data) {
		if(data==true){
	  	
	  		var errorHtml ='<span  class="routingCriteriaWarningBox" style="display:block;width: 340px;margin-top:5px">Invalid Price for Job Codes:'+data+'.</span';
	  		errmsg += 'Rule has missing Job Code pricing.<br/>You must add pricing for all Job Codes before you can save this rule.<br/>';
	  		showValidationError (errmsg);
		  	}else{
	  		showValidationError (false);
	        if(jQuery("#status").val()=="ACTIVE"){
	        	var ruleId = jQuery("#rid").val();
	        	$.getJSON ('rrJson_conflictCheck.action', {rid:ruleId}, function(data) {
	        	var count = 0;
	            setCursorWait(false);
				var wrapperHtml= '<div class="routingCriteriaEditWarningBox" style="display: block; width: 815px; height: 80px; margin-top: 10px; margin-left: 15px; margin-right: 15px; word-wrap: break-word;">';
				wrapperHtml+='<b>ALERT: Rule Criteria Conflict(s). Details </b><a class="commentsClick" onclick="jQuery(\'#ruleComments\').show();return false;">(+)</a><br/><br/>';
				wrapperHtml+='<div id="ruleComments" style="z-index: 9000;margin-left: 250px;width:600px">'+'<div style="text-align: right;"><a href="" class="cancelLink" onclick="jQuery(\'#ruleComments\').hide();return false;">Close</a>';
                wrapperHtml+='</div><div>';
                $.each(data, function(label, conflict) {
	            	count = count + 1;
	            	if(count!=1){
	            		wrapperHtml+='<hr/><br/>';
	            	 }
	            	  wrapperHtml+='<b>Rule Name</b><br>';
	            	  wrapperHtml+=conflict.ruleName+'<br><b>ID#</b>&nbsp;';
                      wrapperHtml+=conflict.ruleId+'<br><br>**Conflicts**<br>';
                      if(conflict.zipCodes!=null && conflict.zipCodes!=''){
                       wrapperHtml+='<b>Zip Codes:</b>&nbsp;&nbsp;&nbsp;'+conflict.zipCodes+'<br/>';
                      }
                     if(conflict.jobCodes!=null && conflict.jobCodes!=''){
                      wrapperHtml+='<b>Job Codes:</b>&nbsp;&nbsp;&nbsp;'+conflict.jobCodes+'<br/>';
                      }
                     if(conflict.pickupLocation!=null && conflict.pickupLocation!=''){
                      wrapperHtml+='<b>Pick Up Location Codes:</b>&nbsp;&nbsp;&nbsp;'+conflict.pickupLocation+'<br/>';
                      }	
                      if(conflict.markets!= null && conflict.markets!=''){
                      wrapperHtml+='<b>Markets:</b>&nbsp;&nbsp;&nbsp;'+conflict.markets+'<br/>';
                      }
                     wrapperHtml+='<br/>';
	            });
	            wrapperHtml += '</div>';
	            wrapperHtml += '</div>';
	            wrapperHtml+='Resolve the conflicting criteria in this rule and click "Save & Done" or check the box below to save the rule as inactive with the conflicts.<br/><br/>';
				wrapperHtml+='<input type="checkbox" name="makeInactiveCheck" id="makeInactiveCheck" value="" />&nbsp;&nbsp;&nbsp;<b>Saving this rule will mark it as inactive with the conflicting criteria</b>.<br/>';
				
	            if(count==0){
	            	//SL 15642 Checking if there is any provider with auto accpetance as ON to change confirmation message
	        		if((autoAcceptRuleStatus=='true')&&(userAction!='CREATE')){
	        			$.each(chosenProviderFirmsJSON, function() {
    	        			if(this.autoAcceptStatusInfo.autoAcceptStatus=='ON'){
    	        				jQuery('#autoAcceptChangeMsg').show();
    	        			}
    	        		});
	        		}
					jQuery("#confirmDialog").jqmShow();
					window.scrollTo(0,0);
				}else{
					jQuery("#activeEditConflict").empty().html(wrapperHtml).show();
					jQuery("#editConflict").hide();
	            }
	            
	        });
	        
	        }else{
	        	//SL 15642 Checking if there is any provider with auto accpetance as ON to change confirmation message
	        	if((autoAcceptRuleStatus=='true')&&(userAction!='CREATE')){
	        		$.each(chosenProviderFirmsJSON, function() {
    	        		if(this.autoAcceptStatusInfo.autoAcceptStatus=='ON'){
    	        			jQuery('#autoAcceptChangeMsg').show();
    	        		}
    	        	});
	        	}
				jQuery("#confirmDialog").jqmShow();
				window.scrollTo(0,0);
			}
			}
			});
		} else {
			showValidationError (errmsg);
	    }
	    
	  	
	    return false;
    }
}

function showValidationError (errmsg) {
    jQuery(".errorMsg").hide();
    if (typeof errmsg !== 'undefined' && errmsg != null && errmsg.length > 0) {
        jQuery("#validationError").html(errmsg).show();
        var x = jQuery("#validationError").position().left;
        var y = jQuery("#validationError").position().top;
        window.scrollTo(x,y);
    }
    return false;
}


function cancelModal() {
    jQuery("#confirmDialog").jqmHide();
    return false;
}


function collectMultipleChecked (tableId) {
    var values = [];
    var trs = jQuery("#"+tableId+" > tbody > tr").length;
	for (i=0; i<trs; i++) {
        if( jQuery("#"+tableId+" > tbody > tr:eq("+i+") > td:eq(0) >input").prop("checked")) {
            values.push(jQuery("#"+tableId+" > tbody > tr:eq("+i+") > td:eq(0) >input").prop("value"));
        }
	}
    return values;
}


function setCursorWait (isWait) {
    document.body.style.cursor= isWait ? 'wait' : 'default';
}


// state is 0 or 1                                   
function setChecked (tableId, state) {
	var trs = jQuery("#"+tableId+" > tbody > tr").length;
	for (i=0; i<trs; i++) {
		jQuery("#"+tableId+" > tbody > tr:eq("+i+") > td:eq(0) >input").prop("checked",state);
	}
}


function col3HideUnhide(arg) {
	if(arg == 'hide') {
		jQuery(".col3").hide();
	} else if(arg == 'show') {
		jQuery(".col3").show();
	}
}


function enablePriceFields() {
    // todo: also enable priceAllSelected's
	jQuery(".jobCodePrice").prop("disabled",false).css("background","#fff");
	jQuery(".labelJcp").html("New Job Code Price");
    // jQuery("#chosenJobPrice0").focus();
}
function addToZipList(){
jQuery("#errorZipMarket").html("");
jQuery("#validationError_marketsZips").html("");
jQuery("#validationError_marketsZips").hide();
var valid =jQuery("#validZipAdded").val();


var radio= jQuery("input[name='zipAddMethod']:checked").val();
var zips="";
	if (radio == 'zip') {
       jQuery('#zipCode-textbox').select().focus();
        zips = jQuery("#zipCode-textbox").val();
    } else if (radio == 'state') {
    	zips = "";
        var checks= jQuery("input[name='zipcodeChoices-checks']:checked");

        $.each (checks, function (i, check) {
            var zip= $(this).val().split(' ')[0];
            if(zips==""){
          		zips = zip;
           	}else{
            	zips = zips + ","+zip;
           }
        });
    } 

    jQuery("#zipCodesInState-wrapper").empty();
    jQuery("#zipCodeByState-select")[0].selectedIndex=0;
	var validCount = 0;
	var len = 0;
	var length = 0;
	var validZipArray = valid.split(",");
	tmp = validZipArray.length;
	if(tmp == ""){
	tmp = 0;
	}
	if(valid==""){
		tmp = 0;
	}
	validCount = parseInt(tmp);
	var zipArray = zips.split(",");
	var i;
	var len=zipArray.length;
	var obj={};
	var errorZips = "";
	var validZips="";
	if(zips!=""){

		for (i=0;i<len;i++)
		{
			var zip = jQuery.trim(zipArray[i]);
			if (zip == null || zip == '' || (zip.match(/^\d\d\d\d\d$/) == null)) {
				if(errorZips==""){
					errorZips =zip;
				}else{
					errorZips = errorZips + "," + zip;
				}
				
			}else{
				if(validZips==""){
					validZips =zip;
				}else{
					validZips = validZips + "," + zip;
				}
			}
		}
	}else{
		jQuery("#validationError_marketsZips").html('Please enter a valid 5-digit Zip Code');
		jQuery("#validationError_marketsZips").show();
		return false;
	}
	var zipArray = validZips.split(",");
	var zipArrayWithoutDuplicates = [];
	//check for duplicates
	zipArrayWithoutDuplicates = eliminateDuplicates(zipArray);
	var length =  parseInt(validCount) + parseInt(zipArrayWithoutDuplicates.length);
	if(length>250){
		var errorHtml ='<span  class="routingCriteriaWarningBox" style="display:block;width: 320px;margin-top:5px">Please enter no more than 250 zip codes before saving.</span><br/>';
		jQuery("#errorZipMarket").html(errorHtml);
		return;
	}
	if(errorZips!=""){
		jQuery("#validationError_marketsZips").html('Please enter a valid 5-digit Zip Code for Zip Codes: "'+errorZips+'".');
		jQuery("#validationError_marketsZips").show();
		
	}
	var inValidHtml = "";
	jQuery("#marketsTable").html("<img src=\"" + staticContextPath + "/images/loading.gif\" width=\"300px\"/>");
	jQuery("#marketsTable").load('rrCreateRuleAction_addZipcodes.action', {zip: zipArrayWithoutDuplicates.toString(),validZipCount:validCount}, function(data) {
		var newValid =jQuery("#validZipAdded").val();
		if(jQuery("#validZipAdded").val()!=""){
			if(valid!=""){
				valid = valid + ","+jQuery("#validZipAdded").val();
				jQuery("#validZipAdded").val(valid);
			}
		}else{
			jQuery("#validZipAdded").val(valid);
		}
		if(jQuery("#duplicateZipMarkets") && jQuery("#duplicateZipMarkets").val()!=""){
			var single ="is";
			if(jQuery("#duplicateZipMarkets").val().indexOf(",")!=-1){
				single ="are";
			} 
		inValidHtml ='<div id="duplicateWarning"  class="routingCriteriaWarningBox" style="display:block;width: 320px;word-wrap: break-word;margin-top:5px"> Zip Code(s) "' + jQuery("#duplicateZipMarkets").val()+'" '+single+' already there.';
			
		}
		if(jQuery("#invalidZips") && jQuery("#invalidZips").val()!=""){
			var single ="was";
			if(jQuery("#invalidZips").val().indexOf(",")!=-1){
				single ="were";
			} 
			if(inValidHtml==""){
				inValidHtml ='<div  class="routingCriteriaWarningBox" style="width: 320px;margin-top:5px;word-wrap: break-word;"> Zip Code(s) "' + jQuery("#invalidZips").val()+'" '+single+' not found.';
			}else{
				inValidHtml = inValidHtml + ' Zip Code(s) "' + jQuery("#invalidZips").val()+'" ' +single+' not found.';
				}
		}
		if(inValidHtml!=""){
			inValidHtml=inValidHtml+" Please check the information and try again.</div><br/>";
		}
		jQuery("#zipCode-textbox").val("");
		jQuery("#errorZipMarket").html(inValidHtml);
	       
	});
}
   function eliminateDuplicates(arr) {
		var i;
		var len=arr.length;
		var out=[];
		var obj={};
		for (i=0;i<len;i++)
		{
			obj[arr[i]]=0;
		}
		for (i in obj) {
		
			out.push(i);
		}
		return out;
}
function addJobCodesToList(){
	jQuery("#validationError_jobCodes").html("");
	jQuery("#error_jobCodes").html("");
	jQuery("#error_jobCodes").hide();
	var jobCodes = jQuery("#jobCode-textbox").val();
	var valid =jQuery("#validJobCode").val();
	var validCount = 0;
	var len = 0;
	var length = 0;
	var validJobArray = valid.split(",");
	tmp = validJobArray.length;
	if(tmp == ""){
		tmp = 0;
	}
	if(valid==""){
		tmp = 0;
	}
	validCount = parseInt(tmp);
	var jobCodeArray = jobCodes.split(",");
	var len=jobCodeArray.length;
	var obj={};
	var errorJobCodes = "";
	var validJobCode="";
	var hasPermit="";
	if(jobCodes!=""){

		for (i=0;i<len;i++)
		{
			var job = jQuery.trim(jobCodeArray[i]);
			if (job == null || jQuery.trim(job) == '') {
				if(errorJobCodes==""){
					errorJobCodes =jobCodeArray[i];
				}else{
					errorJobCodes = errorJobCodes + "," + jobCodeArray[i];
				}
			}
			else{
				if(!isNaN(job)){
					if(job.length<5){
						var diff = 0;
						diff = 5-job.length;
						var code = "";
						for(j=0;j<diff;j++){
							code = code + "0";
						}
						job = code + job;
						
					}
				}
				if(validJobCode==""){
					validJobCode = jQuery.trim(job);
				}else{
					validJobCode = validJobCode + "," + jQuery.trim(job);
			    }
			}
		}
	}else{
		jQuery("#error_jobCodes").html('Please enter a valid Job Code');
		jQuery("#error_jobCodes").show();
		return false;
	}
	var jobCodeArrayWithoutDuplicates = [];
	if(validJobCode!=""){
		jobCodeArray = validJobCode.split(",");
		//check for duplicates
		jobCodeArrayWithoutDuplicates = eliminateDuplicates(jobCodeArray);
		len = jobCodeArray.length;
	}else{
		len=0;
	}
	length =  parseInt(validCount) + parseInt(len);
	length = parseInt(length);
	if(length>150){
	
		var errorHtml ='<span  class="routingCriteriaWarningBox" style="display:block;width: 340px;margin-top:5px">Please enter no more than 150 job codes before saving.</span';
		jQuery("#validationError_jobCodes").html(errorHtml);
		jQuery("#jobCode-textbox").val("");
		return false;
	}
	var jobCodePrice = jQuery("#jobCodePrice").val();
	var inValidHtml ="";
	jQuery("#showJobPrice").html("<img src=\"" + staticContextPath + "/images/loading.gif\" width=\"300px\"/>");
	jQuery("#showJobPrice").load('rrCreateRuleAction_addJobCodes.action', {jobCodes: jobCodeArrayWithoutDuplicates.toString(),job_price:jobCodePrice,validjobCount:validCount}, function(data) {
	if(jQuery("#duplicateJobCodes") && jQuery("#duplicateJobCodes").val()!=""){
	    var single ="is";
		if(jQuery("#duplicateJobCodes").val().indexOf(",")!=-1){
			single ="are";
	}
		inValidHtml ='<span id="duplicateWarning"  class="routingCriteriaWarningBox" style="display:block;width: 340px;word-wrap: break-word;margin-top:5px"> Job Code(s) "' + jQuery("#duplicateJobCodes").val()+'" '+single+' already there. ';
	}
	if(jQuery("#validJobCode").val()!=""){
		if(valid!=""){
			valid = valid + ","+jQuery("#validJobCode").val();
			jQuery("#validJobCode").val(valid);
		}
	}else{
		jQuery("#validJobCode").val(valid);
	}
	
	if(jQuery("#inValidJobCode") && jQuery("#inValidJobCode").val()!=""){
		var single ="was";
		if(jQuery("#inValidJobCode").val().indexOf(",")!=-1){
			single ="were";
		}
		if(inValidHtml==""){
			inValidHtml ='<span id="invalidWarning"  class="routingCriteriaWarningBox" style="width:340px;margin-top:5px;word-wrap: break-word;display:block;"> Job Code(s) "' + jQuery("#inValidJobCode").val()+'" '+single+' not found. ';
		}else{
			inValidHtml = inValidHtml + 'Job Code(s) "'+ jQuery("#inValidJobCode").val()+'" '+single+'  not found. ';
		}
	}
	//R12_2 : SL-20643
	hasPermit = jQuery("#hasPermit").val();
	if(hasPermit == 'true'){
		if(inValidHtml==""){
			inValidHtml ='<span id="invalidWarning"  class="routingCriteriaWarningBox" style="width:340px;margin-top:5px;word-wrap: break-word;display:block;"> 99888 is a permit SKU and cannot be added to a CAR rule. ';
		}else{
			inValidHtml = inValidHtml + '<br>99888 is a permit SKU and cannot be added to a CAR rule. ';
		}
	}
	if(inValidHtml!=""){
	
		inValidHtml=inValidHtml+" Please check the information and try again.</span><br/>";
		jQuery("#validationError_jobCodes").html(inValidHtml);
	}else{
		jQuery("#jobCode-textbox").val("");
		jQuery("#validationError_jobCodes").html("");
	}
	jQuery("#jobCode-textbox").val("");
	       
	});
}
function addCustomReference(){

	jQuery("#validationError_customRefs").html("");
	var customRefValues = jQuery("#customRef-textbox").val();
	var customRefName = jQuery("#customRef-select").val();
	var value= jQuery('#customRef-textbox').prop('value');
    var refVal=  jQuery('#customRef-select').val();
    var refName= jQuery('#customRef-select :selected').text();
    var cbval= refVal + '@@' + value;
	var valid =jQuery("#validCustRef").val();
	_customRefs.showValidationError(false);

    if (-1 == refVal) {

        jQuery("#error_customRefs").html('Please select a custom reference');
        jQuery("#error_customRefs").show();
        jQuery('#customRef-select').focus();
        return false;
    } else if (value == null || value == '') {
  
       jQuery("#error_customRefs").html('Please enter a value for "' + refName + '"');
       jQuery("#error_customRefs").show();
        jQuery('#customRef-textbox').select().focus();
        return false;
    }
	jQuery("#validationError_customRefs").html("");
	var custRefs = jQuery("#customRef-textbox").val();
	var validCount = 0;
	var len = 0;
	var length = 0;
	var validCustArray = valid.split(",");
	tmp = validCustArray.length;
	if(tmp == ""){
		tmp = 0;
	}
	// Either adding nothing or adding for the first time
	if(valid == "" || valid == "PICKUP LOCATION CODE@@"){
		tmp = 0;
	}
	validCount = parseInt(tmp);
	var custRefsArray = custRefs.split(",");
	var custRefsArrayWithoutDuplicates = [];
	//check for duplicates
	
	var obj={};
	var errorCustRefs = "";
	var validCustRefs = "";
	len = custRefsArray.length;
	if(custRefs!=""){
		for (i=0;i<len;i++)
		{
			var cust = jQuery.trim(custRefsArray[i]);
			if(cust!=null && cust!="" && cust!="undefined"){
				if (cust.length>30) {
				
					if(errorCustRefs==""){
						errorCustRefs =cust;
					}else{
						errorCustRefs = errorCustRefs + "," + cust;
					}
					
				}else{
					if(validCustRefs==""){
						validCustRefs =cust;
					}else{
						validCustRefs = validCustRefs + "," + cust;
					}
				}
			}
		}
	}	
	if(validCustRefs!=""){
		var custRefsArr = validCustRefs.split(",");		
		custRefsArrayWithoutDuplicates = eliminateDuplicates(custRefsArr);		
		len = custRefsArrayWithoutDuplicates.length;
	}else{
		len = 0;
	}
	if(len ==0){
		jQuery("#customRef-textbox").val("");
		jQuery("#customRef-select").val("-1");
		jQuery('#customRef-textbox').prop("disabled", "disabled");
		return false;
	}
	length =  parseInt(validCount) + parseInt(len);
	length = parseInt(length);
	if(length > 50){
		var errorHtml ='<span  class="routingCriteriaWarningBox" style="display:block;width: 340px;margin-top:5px;word-wrap: break-word;">Please enter no more than 50 values for this custom reference '+
		'before saving.</span><br/>';
		jQuery("#validationError_customRefs").html(errorHtml);
		jQuery("#customRef-textbox").val("");
		jQuery("#customRef-select").val("-1");
		jQuery('#customRef-textbox').prop("disabled", "disabled");
		return;
	}
	 var customRefValues = jQuery("#customRef-textbox").val();
	 var customRefName = jQuery("#customRef-select").val();
	 var inValidHtml = "";
	 if(errorCustRefs!=""){
		inValidHtml ='<span  class="routingCriteriaWarningBox" style="display:block;width: 340px;margin-top:5px;word-wrap: break-word;">Please enter Custom Reference value(s) with length less than 30 for "'+errorCustRefs+'"';
	 }
	 var custValues = custRefsArrayWithoutDuplicates.join(",");
	 jQuery("#customRefsTable").html("<img src=\"" + staticContextPath + "/images/loading.gif\" width=\"300px\"/>");
	 jQuery("#customRefsTable").load('rrCreateRuleAction_addCustReference.action', {custom_ref_value: custValues,custom_ref_name:customRefName,validCustRefCount:validCount}, function(data) {
	 	var newValid =jQuery("#validCustRef").val();
		if(jQuery("#validCustRef").val()!=""){			
			if(valid!=""){							
				if(valid != "PICKUP LOCATION CODE@@" && 
					newValid !="PICKUP LOCATION CODE@@"){
					valid = valid + ","+ newValid;
				}
				jQuery("#validCustRef").val(valid);
			}else{
				if(newValid !="PICKUP LOCATION CODE@@"){
					jQuery("#validCustRef").val(newValid);
				}
			}
		}else{			
			jQuery("#validCustRef").val(valid);
		}
	    if(jQuery("#duplicateCustRefs")  && jQuery("#duplicateCustRefs").val() && jQuery("#duplicateCustRefs").val()!=""){
	    	var single ="is";
			if(jQuery("#duplicateCustRefs").val().indexOf(",")!=-1){
				single ="are";
			}
	    	if(inValidHtml==""){
				inValidHtml ='<span  class="routingCriteriaWarningBox" style="display:block;width: 340px;margin-top:5px;word-wrap: break-word;"> Custom reference value(s) "' + jQuery("#duplicateCustRefs").val()+'" '+single+' already there for custom reference name '+customRefName;
			}else{
				inValidHtml = inValidHtml + 'Custom Reference value(s) ' + jQuery("#duplicateCustRefs").val()+' '+single+' already there for custom reference name '+customRefName;
			}
				
		}
		if(inValidHtml!=""){
			inValidHtml=inValidHtml+".Please check the information and try again.</span><br/>";
		}
		jQuery("#customRef-textbox").val("");
		jQuery("#customRef-select").val("-1");
		jQuery('#customRef-textbox').prop("disabled", "disabled");
		jQuery("#validationError_customRefs").html(inValidHtml);
	  });

}
// Selectors don't work with a slash in the name.
function getEsc(arg) {
    return arg.replace(/\//,'SLSLASH');
}
// ...but revert before sending to backend
function unescFormValues() {
    jQuery('input[name="specialty-chosen-checks"]').each(function() { 
        jQuery(this).val(jQuery(this).val().replace(/SLSLASH/,'/'));
    });

    jQuery('.jobCodePrice').each(function() { 
        jQuery(this).prop("name", jQuery(this).prop("name").replace(/SLSLASH/,'/'));
    });

    jQuery('.isAutoPullChecks').each(function() { 
        jQuery(this).prop("name", jQuery(this).prop("name").replace(/SLSLASH/,'/'));
    });
}

function isValidEmailAddress(emailAddress) {
    var pattern = new RegExp(/^(("[\w-\s]+")|([\w-]+(?:\.[\w-]+)*)|("[\w-\s]+")([\w-]+(?:\.[\w-]+)*))(@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$)|(@\[?((25[0-5]\.|2[0-4][0-9]\.|1[0-9]{2}\.|[0-9]{1,2}\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\]?$)/i);
    return pattern.test(emailAddress);
}


function togglePriceButton(){
    return;

	if(parseInt(jQuery("#chosenSpecialtiesWrapper").css("height")) < 120) {
		jQuery("#priceJobCodesButton").hide();
	} else {
		jQuery("#priceJobCodesButton").show();
	}
}


function byMarketMultiSelectShow(isVisible) {
    if (isVisible) {
     var  specCodeSelectVisibility='hidden';
        jQuery("#byMarketTableContainer").show();
    } else {
    	specCodeSelectVisibility='visible';
        jQuery("#byMarketTableContainer").hide();
    }

    if(ie6) { 
    	jQuery("#specialtyCode-select").css('visibility', specCodeSelectVisibility);
    	jQuery("#jobCode-select").css('visibility', specCodeSelectVisibility);
    	jQuery("#zipCodeByState-select").css('visibility', specCodeSelectVisibility);
    }
}

function jobCodeMultiSelectShow(isVisible) {
    if (isVisible)
        jQuery("#jobCodeTableContainer").show();
    else jQuery("#jobCodeTableContainer").hide();
}


// display number of selected items within a multiselect, return true if ALL
// return true if all checked
function updateNSelected(checkboxes, htmlElement) {
    var n=0;
    jQuery(checkboxes).each(function() { n += this.checked; });
    htmlElement.html (n==0 ? 'Select all that apply' : n + " selected");
    return (n === jQuery(checkboxes).length);
}


function isRuleNameUnique() {
    if (userAction != 'VIEW') {
        var chosenName= jQuery("#ruleName").val().toUpperCase();
        var copyRules= jQuery('#copyRules-select > option');
    
        for (i=0; i<copyRules.length; i++) {
            var copyRuleName= copyRules[i].text.toUpperCase();
            if (copyRuleName == chosenName) {
                return (copyRules[i].value == _q_rid);
            }
        }
    }
    return true;
}
function toggleSelectAllCheck(id){
	
 	var checks= jQuery('#selectAll-' + id + '-check');
 	var state= checks[0].checked ? 1 : 0;
    var trs = jQuery("#"+id+"-chosen > tbody > tr");
	for (i=0;i<trs.length;i++) {
    	jQuery("#" + id + "-chosen > tbody > tr:eq("+i+") > td:eq(0) >input").prop("checked",state);
    }
}

function isValidPrice(price) { return (price.match(/^\d*\.?\d{0,2}$/) != null); }


function copyRule() {

    var ruleId=  jQuery('#copyRules-select').val();
	if ('-1' === ruleId) {
        jQuery('#copyRules-select').focus();
        return false;
    }
	jQuery("#errorZipMarket").html("");
	jQuery("#validationError_marketsZips").html("");
	jQuery("#validationError_marketsZips").hide();
	jQuery("#validationError_jobCodes").html("");
	jQuery("#error_jobCodes").html("");
	jQuery("#error_jobCodes").hide();
	jQuery("#validationError_customRefs").html("");
    jQuery("#copyRules-select")[0].selectedIndex=0;
    var cwait=2;
    setCursorWait(true);
   	jQuery("#marketsTable").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"300px\"/></td></tr>");
	jQuery("#showJobPrice").html("<img src=\"" + staticContextPath + "/images/loading.gif\" width=\"300px\"/>");
	jQuery("#customRefsTable").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"300px\"/></td></tr>");
	jQuery("#marketsTable").load('rrCreateRuleAction_loadCriteriaForRule.action', {rid: ruleId}, function(data) {
    jQuery("#showJobPrice").load("rrCreateRuleAction_loadJobCodeList.action?pageNo=1");
   	jQuery("#customRefsTable").load("rrCreateRuleAction_loadCustRefList.action?pageNo=1");
   	setCursorWait(false);
    });
}


function getUrlParameters() {
    var map = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, 
        function(m,key,value) { map[key] = value; });
    return map;
}

