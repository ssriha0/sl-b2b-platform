
/*
 * @param tableName - Name of tableName-tablewrap DIV
 * @param myVar - Name of the var you're assigning this instance to, used for event binding
 * @param aryColHeaders - String array containing names of each column
 */
function ChosenAttrs(tableName, varName, aryColHeaders) {
    this.tableName= tableName;
    this.tableId= '#' + tableName;
    this.myvar= varName;

    this.show = function() { jQuery(this.tableId + '-tablewrap').show(); }
    this.hide = function() { jQuery(this.tableId + '-tablewrap').hide(); }

    this.render = function(aryColHeaders) {
        var tablewrapHtml= '';

        if (userAction != 'VIEW')
            tablewrapHtml += '<img src="' + staticContextPath + '/images/icons/iconTrash.gif" class="deleteIcon pointer" onclick="' + this.myvar + '.deleteSelected();return false;"/>\n'
                          +  '<a href="" class="deleteLink" onclick="' + this.myvar + '.deleteSelected();return false;">Delete Selected</a>\n'

        tablewrapHtml += 
            '<div class="inlineTableContainer" style="max-height:150px; overflow:auto;">\n'
          + '<table id="' + this.tableName + '-chosen" cellpadding="0" cellspacing="0" border="0" class="inlineTable">\n'
          + '<thead id="' + this.tableName + '-chosen-thead">\n';

        for (var i in aryColHeaders) {
            tablewrapHtml += '<th>' + aryColHeaders[i] + '</th>\n';
        }

        tablewrapHtml += '</thead><tbody></tbody></table></div>\n';
        jQuery(this.tableId + "-tablewrap").empty().html(tablewrapHtml);
    }


    // Check box in 1st column, ary of html for following columns
    this.addRow = function(chkLabel, chkValue, aryCols, customCheckClass) {
        showValidationError (false);
        if (typeof customCheckClass == 'undefined') {
            var name=this.tableName;
            customCheckClass='';
        } else if((customCheckClass=='PENDING')||(customCheckClass=='ON')||(customCheckClass=='OFF')||(customCheckClass=='NA'))
        {var name=this.tableName;
        }
        else {var name= customCheckClass;}

        // First column is special, since we manage the checkboxes
        var tablewrapHtml= 
            '<tr><td><input type="checkbox" id="' + this.tableName + '-chosen-check-' + chkValue + '" name="' + name + '-chosen-checks" class="' + customCheckClass + ' ' + this.tableName + '-chosen-checks" '
          + 'value="' + chkValue + '" ' + DISABLED + ' />&nbsp;<label for="' + this.tableName + '-chosen-check-' + chkValue + '">' + chkLabel + '</label></td>\n';

        // Add a new <td> for each aryCols
        for (var i in aryCols) {
            tablewrapHtml += '<td>' + aryCols[i] + '</td>';
        }
        if(autoAcceptRuleStatus=='true')
        {
         //SL 15642 Adding  a new <td> only when auto acceptance is ON for each aryCols
                for (var i in customCheckClass) {
                    tablewrapHtml += '<td>' + customCheckClass[i] + '</td>';
                }
        }
        tablewrapHtml += '</tr>\n';

        // jQuery(this.tableId + '-chosen-tbody').append(tablewrapHtml);
        
        jQuery(this.tableId + '-chosen > tbody').append(tablewrapHtml);
        this.show(); 
    }


    // assumes your checkboxes have class of <tableName>-chosen-checks
    this.isDuplicate = function (value) {
        var checks= jQuery("." + this.tableName + "-chosen-checks");
        var isDuplicate= false;

        $.each (checks, function() {
            var chkValue= jQuery(this).val();

            if (chkValue === value) {
                isDuplicate= true;
            }
        });
        return (isDuplicate);
    }

    // only compare up to the @@ within the value
    this.isDuplicateName = function (value) {
        var checks= jQuery("." + this.tableName + "-chosen-checks");
        var isDuplicate= false;

        var prefix= value.match(/.*@@/);
        value= prefix[0].substring(0, prefix[0].length-2);
        
        $.each (checks, function() {
            var chkValue= jQuery(this).val();

            var chkPrefix= chkValue.match(/.*@@/);
            if (chkPrefix[0].substring(0, chkPrefix[0].length-2) === value)
                isDuplicate= true;
        });
        return (isDuplicate);
    }


    this.deleteSelected = function (tableId) {
        showValidationError (false);
        if (typeof tableId === 'undefined') {
            tableId= this.tableId;
        }

        var rows = jQuery(this.tableId + "-chosen > tbody > tr").length;
    	for (i=rows-1; i>=0; i--) {
    		if(jQuery(this.tableId + "-chosen > tbody > tr:eq("+i+") > td > input").prop("checked")) {
    			jQuery(this.tableId + "-chosen > tbody > tr:eq("+i+")").remove();		
    		}
    	}
    	if(jQuery(this.tableId + "-chosen > tbody > tr").length == 0) {
        	jQuery(this.tableId + "-chosen").parent().parent().hide();
        	try { jQuery("#priceJobCodesButton").hide(); }
            catch(e) {}
    	}
    }


    this.isEmpty = function() { 
        var checks= jQuery('.' + this.tableName + '-chosen-checks');
        return (checks.length == 0); 
    }


    this.showValidationError = function(errmsg) {
        jQuery(".errorMsg").hide();
        if (typeof errmsg !== 'undefined' && errmsg != null && errmsg.length > 0) {
            var section= (this.myvar.indexOf('_specJobsMap') >= 0) ? '_specialties' : this.myvar;
            jQuery("#validationError" + section).html(errmsg).show();
            // smh: this cause the page to submit?!  
            // jQuery("#validationError-" + section).scrollIntoView();
        } 
        return false;
    }


    this.render(aryColHeaders);
}


///////////////////////////////////////////////////////////////////////////////////


function ChosenSpecJobs(specialty, isAutoPull) {

    this.specialty= specialty;
    this.escSpecialty= getEsc(specialty);
    this.chosenAttrs= null;

    // Create the chosenAttrs scaffolding and table
    this.render= function (isAutoPull) {
        var specialty= this.specialty;
        var escSpecialty= this.escSpecialty;
        var checked= '';
        if (isAutoPull == '1') {
            checked='CHECKED';
        }

        var html=  
               '<div id="specialtyWrapperLabel_' + escSpecialty + '" name="specialtyWrapperLabel_' + escSpecialty + '" class="specialtyWrapperLabel" onclick="_specJobsMap[\'' + specialty + '\'].toggleJobCodes();">'
            +  '\n<p class="right">Specialty Code: ' + this.specialty + ' (-)</p></div>';

        if (userAction != 'VIEW')
            html +=
               '\n<div id="priceAllSelectedDiv' + escSpecialty + '" class="priceAllSelectedDiv">'
            +  '<input type="button" name="priceAllSelected-button" onclick="_specJobsMap[\'' + specialty + '\'].priceAllSelected();" class="button right action" value="ADD"/>'
            +  '<input type="text" id="priceAllSelected@@' + escSpecialty + '" name="priceAllSelected@@' + escSpecialty + '" class="right jobCodePriceAll" maxlength="12" />'
            +  '<p class="right">Price all selected $</p>'
            +  '&nbsp;&nbsp;</div>';

        html += '\n<div id="' + escSpecialty + '-tablewrap" class="inlineTableWrapper addedSpecialtyWrapper">\n</div>';

        jQuery("#chosenSpecialtiesWrapper").prepend(html);

        var col1= (userAction == 'VIEW') 
            ? '' : 'Select All<br/><input type="checkbox" id="selectAll-' + escSpecialty + '-check" onclick="_specJobsMap[\'' + specialty + '\'].toggleChecked();" />';

        var cols= [
            col1,
            'Job Codes',
            'Job Code Price'];

        this.chosenAttrs= new ChosenAttrs (escSpecialty, '_specJobsMap[\'' + specialty + '\']', cols);

        // append checkbox
        this.tableId = this.chosenAttrs.tableId;
        
        jQuery(this.tableId + "-tablewrap").append ('\n<br /><input type="checkbox" id="isAutoPull_' + escSpecialty + '" class="isAutoPullChecks" name="isAutoPull_' + escSpecialty + '" ' + checked + ' ' + DISABLED + ' /><label for="isAutoPull_' + escSpecialty + '">Automatically add new job codes</label>');

    }


    // Add choices from dropdown checklist.  Pass in result of collectMultipleChecked()
    this.addChoices= function (choices) {
        for (var i in choices) {
            var specJobCode= choices[i].split('@@');
            var jobCode= specJobCode[1];
            this.addJobcode (jobCode);
        }
        jQuery('#specialtyCode-select')[0].selectedIndex=0;

        jQuery("#jobCode-select > option:eq(0)").html('Select all that apply');
        jQuery("#jobCode-select").prop("disabled","disabled");
        jQuery('#chosenSpecialtiesWrapper td input[type=text]:first').focus();
        jQuery('#addAllJobCodes').prop("checked",false).prop("disabled","disabled");
    }


    // add given jobcode to our chosenAttrs table
    this.addJobcode= function (jobcode, price) {
        var escSpecJobCode= this.escSpecialty + '@@' + jobcode;
        if (!this.chosenAttrs.isDuplicate(escSpecJobCode)) {
            if (typeof price == 'undefined') {
                // new rule, price DNE
                this.collapseOthers();
                price= '';
            } else if (price == 'null' || price == null) {
                // existing rule, price was never set
                price= '';
            }
            var codePrice= [ jobcode, 
                '$<input type="text" id="chosenJobPrice' + escSpecJobCode + '" name="chosenJobPrice' + escSpecJobCode 
                  + '"  value="' + price + '" class="chosenJobPrices' + this.escSpecialty + ' jobCodePrice ' + DISABLED + '" maxlength="12" ' + DISABLED  + ' />'];
            this.chosenAttrs.addRow ('&nbsp;', escSpecJobCode, codePrice, 'specialty');
        }
    }


    this.toggleJobCodes = function() {
        var elem= jQuery('#' + this.escSpecialty + '-tablewrap');
        elem.toggle();
        if (elem.is (':visible'))
            jQuery('#priceAllSelectedDiv' + this.escSpecialty).show();
        else jQuery('#priceAllSelectedDiv' + this.escSpecialty).hide();
    }
    
    
    this.priceAllSelected = function () {
        // no matches?!  var xxx= jQuery("#priceAllSelected@@" + this.escSpecialty);
        var newPriceCtrl= jQuery('input[name="priceAllSelected@@' + this.escSpecialty + '"]');
        var newPrice= newPriceCtrl.val();
        var targets= jQuery('.' + this.escSpecialty + '-chosen-checks:checked');

        if (targets.length == 0) {
            this.chosenAttrs.showValidationError('No job code selected');
        } else if (!isValidPrice(newPrice)) {
            this.chosenAttrs.showValidationError('Invalid price');
            newPriceCtrl.select().focus();
            return;
        } else {
            showValidationError (false);

            for (var i=0; i < targets.length; i++) {
                var cbval= targets[i].value;
                var jcSpecialty= cbval.split('@@')[0];
                if (jcSpecialty == this.escSpecialty) {
                    // no matches?!  jQuery('#chosenJobPrice' + cbval).val(newPrice);
                    var xin= jQuery('input[name="chosenJobPrice' + cbval + '"]');
                    xin.val(newPrice);
                }
            }
            newPriceCtrl.val('');
        }    
    }


    this.toggleChecked = function() {
        var checks= jQuery('#selectAll-' + this.escSpecialty + '-check');
        var state= checks[0].checked ? 1 : 0;
        var trs = jQuery("#" + this.escSpecialty + "-chosen > tbody > tr");
        for (i=0;i<trs.length;i++) {
            jQuery("#" + this.escSpecialty + "-chosen > tbody > tr:eq("+i+") > td:eq(0) >input").prop("checked",state);
        }
    }


    this.deleteSelected = function() {
        var tableId= '#addedJobCodes_' + this.escSpecialty;
        this.chosenAttrs.deleteSelected (tableId);
    
        var nRows= jQuery(this.chosenAttrs.tableId + "-chosen > tbody > tr").length;
    	if (nRows == 0) {
            jQuery("#specialtyWrapperLabel_" + this.escSpecialty).remove();
            jQuery('#' + this.escSpecialty + '-tablewrap').remove();
            jQuery(".priceAllSelectedDiv").remove();

            // Now, remove this so we get re-rendered next time around
            _specJobsMap[this.specialty]= null;
            delete _specJobsMap[this.specialty];

        	// try { jQuery("#priceJobCodesButton").hide(); } catch(e) {}
        }
    }


    this.collapseOthers = function() {
        jQuery(".addedSpecialtyWrapper").hide();
        jQuery(".priceAllSelectedDiv").hide();
    
        jQuery('#' + this.escSpecialty + '-tablewrap').show();
        jQuery('#priceAllSelectedDiv' + this.escSpecialty).show();
    }


    this.render(isAutoPull);
}

