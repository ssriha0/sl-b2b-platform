// Callbacks/

function addFirmId() {
    var firmId= jQuery('#firmId-textbox').prop('value');
    jQuery('#firmId-textbox').select().focus();

    if (firmId == null || firmId == '') {
        _firms.showValidationError ('Please enter a valid numeric Firm ID');
        return false;
    } else if (firmId.match(/^\d+$/) == null) {
        _firms.showValidationError ('Firm ID must be numeric only.');
        return false;
	} else if (_firms.isDuplicate(firmId)) {
        return false;
    }
    firmClickCount++;   
    setCursorWait(true);
    $.getJSON ('rrJson_getFirmDetails.action', {id: firmId}, function(json) {
        setCursorWait(false);

        var firm= json[0];
        if (json == null || typeof firm == 'undefined') {
            _firms.showValidationError ('No provider firms match ' + firmId + '.  Please check the information and try again.');
            firmClickCount=0;
            return false;
        } 
        if (firm['state'].wfState != 'ServiceLive Approved' && firm['state'].wfState != 'Sears Provider Approved') {
            _firms.showValidationError ('The firm ' + firmId + ' (' + firm['businessName'] + ') has status of "' + firm['state'].wfState + '". The rule requires the firm to be in "ServiceLive Approved" or "Sears Provider Approved" status. Please enter a valid firm id to continue.');
            firmClickCount=0;
            return false;
        }
        jQuery('#firmId-textbox').val('');
        if(autoAcceptRuleStatus=='true')
    	{
        	_firms.addRow ('<a  style="color: #00A0D2;" href="companyTabAction.action?getManageRuleProviderProfileDetail=true&vendorFirmId='+firm.id+'" target="_blank"><strong>' + firm.businessName + '</strong></a><br /><label style="padding-left:20px;">ID #' + firm.id+'</label>', firm.id, [firm.state.wfState],['PENDING']);
    	}
        else
        	{
        _firms.addRow ('<a  style="color: #00A0D2;" href="companyTabAction.action?getManageRuleProviderProfileDetail=true&vendorFirmId='+firm.id+'" target="_blank"><strong>' + firm.businessName + '</strong></a><br /><label style="padding-left:20px;">ID #' + firm.id+'</label>', firm.id, [firm.state.wfState]);
        	}
     firmClickCount=0;   
    });
}

///////////////////////////////////////////////////////////////////////////////////

function addCustomRef() {

    var value= jQuery('#customRef-textbox').prop('value');
    var refVal=  jQuery('#customRef-select').val();
    var refName= jQuery('#customRef-select :selected').text();
    var cbval= refVal + '@@' + value;

    _customRefs.showValidationError(false);

    if (-1 == refVal) {
        _customRefs.showValidationError ('Please select a custom reference');
        jQuery('#customRef-select').focus();
        return false;
    } else if (value == null || value == '') {
        _customRefs.showValidationError ('Please enter a value for "' + refName + '"');
        jQuery('#customRef-textbox').select().focus();
        return false;
    } else if (_customRefs.isDuplicateName(cbval) !== false) {
        _customRefs.showValidationError (refName + ' has already been added.  (Since ALL custom references must be matched for an order to match a rule, adding two custom references of the same type would create a rule that never fires).');
        jQuery('#customRef-textbox').val('').prop("disabled", "disabled");
        jQuery('#customRef-select')[0].selectedIndex=0;
        jQuery('#customRef-select').focus();
        return false;
    }

    jQuery('#customRef-textbox').val('').prop("disabled", "disabled");
    jQuery('#customRef-select')[0].selectedIndex=0;
    jQuery('#customRef-select').focus();
    _customRefs.addRow ('<strong>' + refName + '</strong>', cbval, [value]);
}

///////////////////////////////////////////////////////////////////////////////////

function addMarket() {
    _marketsZips.showValidationError (false);
	jQuery("#errorZipMarket").html("");
	jQuery("#validationError_marketsZips").html("");
	jQuery("#validationError_marketsZips").hide();
    var checksDom= jQuery('input[name="marketChoices-checks"]');
    var checks= checksDom.serializeArray();
	var markets ="";
    $.each (checks, function (i, check) {
        var label= jQuery('#marketChoices-label' + check.value).text();

        if (!_marketsZips.isDuplicate(check.value)) {
         if(markets == ""){
         	markets = markets + "market" + check.value;
         }else{
            markets = markets + "," + "market" + check.value;
         }
        }
    });
    addMarketsToList(markets);
    checksDom.prop("checked",false);
    jQuery("#byMarket > option:eq(0)").html('Select all that apply');
}
function addMarketsToList(markets){
	var valid =jQuery("#validZipAdded").val();
	jQuery("#marketsTable").load('rrCreateRuleAction_addZipcodes.action', {markets: markets}, function(data) {
	     jQuery("#validZipAdded").val(valid);      
	});
}

function addZip() {
    _marketsZips.showValidationError (false);
    var radio= jQuery("input[name='zipAddMethod']:checked").val();

    if (radio == 'zip') {
        var zip= jQuery('#zipCode-textbox').prop('value');
        jQuery('#zipCode-textbox').select().focus();
        addZipcode (zip);
    } else if (radio == 'state') {
        var checks= jQuery('input[name="zipcodeChoices-checks"]').serializeArray();
        $.each (checks, function (i, check) {
            var zip= jQuery(this).val().split(' ')[0];
            addZipcode (zip, jQuery(this).val());
        });
    } 

    jQuery("#zipCodesInState-wrapper").empty();
    jQuery("#zipCodeByState-select")[0].selectedIndex=0;
}


function addZipcode(zip, zipCityState) {

    if (zip == null || zip == '' || (zip.match(/^\d\d\d\d\d$/) == null)) {
        _marketsZips.showValidationError ('Please enter a valid 5-digit Zip Code');
        return false;
    } else if (_marketsZips.isDuplicate('zip' + zip)) {
        return false;
    }

    if (zipCityState==null || zipCityState=='') {
        setCursorWait(true);
        $.getJSON ('rrJson_getCityByZipcode.action', {zip: zip}, function(data) {
            setCursorWait(false);
            if (data != null) {
                zipCityState= data;
                addChosenZip(zip, zipCityState);
                jQuery('#zipCode-textbox').val('');
            } else {
                _marketsZips.showValidationError ('Please enter a valid 5-digit Zip Code');
                return false;
            }
        });
    } else {
        addChosenZip(zip, zipCityState);
    }
    return true;
}


function addChosenZip(zip, zipCityState) {
    _marketsZips.addRow ('<strong>Zip Code</strong>', 'zip' + zip, [zipCityState]);
}


function showMarketZipDiv() {
    if (userAction != 'VIEW') {
        jQuery('#zipInlineForm').toggle('slow');
    }
}


function cancelCreateAreaByZip() {
    jQuery('#zipInlineForm').hide('slow');
    jQuery("#zipCodesInState-wrapper").empty();
    jQuery("#zipCodeByState-select")[0].selectedIndex=0;
    jQuery('#zipCode-textbox').val('');
    jQuery("#errorZipMarket").html("");
	jQuery("#validationError_marketsZips").html("");
    _marketsZips.showValidationError(false);
}


///////////////////////////////////////////////////////////////////////////////////

function addJobCodes() {
    if (jQuery('#jobCode-select').prop("disabled"))
       return false;
    
    var choices= collectMultipleChecked('dropdownTableJobCode');
    if (choices.length) {
        var specialty= jQuery("#specialtyCode-select").val();
        var specJobs= _specJobsMap[specialty];
        if (specJobs == null) {
            specJobs= new ChosenSpecJobs(specialty);
            _specJobsMap[specialty]= specJobs;
        }
        specJobs.addChoices (choices);
    }
}



