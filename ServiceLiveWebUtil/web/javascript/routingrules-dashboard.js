
// Globals to drive jquery modal for collecting comments when deactivating rule
// var _jqm_check= null;
jQuery(document).ready(function () {
	                            
	jQuery("body").click(function () {
		jQuery("#ruleComments").hide();
	});
	jQuery(".commentsClick").click(function (e) {
		var x = e.pageX;
		var y = e.pageY;
		if (jQuery("#ruleComments").css("display") == "block") {
			jQuery("#ruleComments").hide("slow");
		} else {
			jQuery("#ruleComments").css("top", y + 10);
			jQuery("#ruleComments").css("left", x + 10);
			jQuery("#ruleComments").show("slow");
		}
		jQuery("#ruleCommentsText").html(jQuery(this).prop("title"));
    });


	
	jQuery("#carTabs > ul > li:eq(3)> a").click(function () {
		jQuery("#tab_rulesHistory").html("<img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/>");
		jQuery("#tab_rulesHistory").load("rr_getRoutingRuleHdrHist.action");
    });


    // User confirmed they want to deactivate rule
    jQuery("#deactivateRule").click(function() {
        _jqm_comment= jQuery("#ruleComment").get(0).value;
        // Ensure req'd comment field has text
			if (_jqm_comment === null || _jqm_comment === "") {
            jQuery("#ruleComment").focus();
            return;
        }
        setRuleStatus();
    });

		
    });


function setRuleStatus () {
	setCursorWait(true);
    jQuery("#deactivateRule").disabled= true;
    // https://localhost:8443/MarketFrontend/rrJson_setRuleStatus.action?newState=ACTIVE&comment=NA&ruleId=50
    // Returns null on success, or else an error string
	$.getJSON("rrJson_setRuleStatus.action", {ruleId:_jqm_ruleId, newState:_jqm_newState, comment:_jqm_comment}, function (data) {
		setCursorWait(false);
		if (data != null) {
			//alert(data);
			window.location.reload();
        } else {
		 // red bg for inactive rules
			if (!_jqm_newState) {
				jQuery("#col4rule" + _jqm_ruleId).addClass("ruleINACTIVE");
				jQuery("#searchCol4rule" + _jqm_ruleId).addClass("ruleINACTIVE");
				jQuery("#activate-check-" + _jqm_ruleId).prop("checked", false);
				jQuery("#search-activate-check-" + _jqm_ruleId).prop("checked", false);
			} else {
				jQuery("#col4rule" + _jqm_ruleId).removeClass("ruleINACTIVE");
				jQuery("#searchCol4rule" + _jqm_ruleId).removeClass("ruleINACTIVE");
				jQuery("#activate-check-" + _jqm_ruleId).prop("checked", true);
				jQuery("#search-activate-check-" + _jqm_ruleId).prop("checked", true);
			}
			jQuery("#deactivateRule").disabled = false;
			hideModal();
		}
	});
}
function setCursorWait(isWait) {
	document.body.style.cursor = isWait ? "wait" : "default";
}
function hideModal() {
	jQuery("#ruleComment").get(0).value = "";
	jQuery("#confirmDialog").jqmHide();
	_jqm_comment = null;
	_jqm_ruleId = null;
	_jqm_newState = null;
}
function cancelModal() {
    // re-check the box - they cancelled a 'deactivate' order
    jQuery("#col4rule" + _jqm_ruleId).removeClass("ruleINACTIVE");
	jQuery("#searchCol4rule" + _jqm_ruleId).removeClass("ruleINACTIVE");
    jQuery("#activate-check-" + _jqm_ruleId).prop("checked",true);
	jQuery("#search-activate-check-" + _jqm_ruleId).prop("checked", true);
	hideModal();
}
function rowWarningHighlight(rowid) {
	jQuery(rowid).addClass("alertHighlight");
	jQuery(".alertHighlight > td:lt(2)").css("border-left", "2px solid #ffcc66");
}

