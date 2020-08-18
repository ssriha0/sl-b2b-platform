<%@ page language="java"
	import="java.util.*,com.servicelive.routingrulesengine.RoutingRulesConstants" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<c:set var="alertActive"
	value="<%=RoutingRulesConstants.ROUTING_ALERT_STATUS_ACTIVE%>" />
<c:set var="archive" scope="request" value="${archiveInd}"> </c:set>	
<c:set var="autoAcceptStatusBuyer"
	value="<%=request.getSession().getAttribute("autoAcceptStatus")%>" />

<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/routingrules.css" />
<link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery-ui.custom.min.css" type="text/css"></link>
<script type="text/javascript">var staticContextPath="${staticContextPath}";</script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>

 <tags:security actionName="routingRulesAction_view">
 	<c:set var="isReadOnly" value="true" scope="page" />
 </tags:security>
 <tags:security actionName="routingRulesAction_edit">
     <c:set var="isReadOnly" value="false" scope="page" />
 </tags:security>

<script type="text/javascript">      
	var curSortCol = ${pagingCriteria.sortCol}; // Default sort on 0 = modified date; 1 = Name, 2 = Contact, 3 = Action, 4 = Status;
	var curSortOrd = ${pagingCriteria.sortOrd}; // Default sort order 0 = descending; 1 = ascending;
	var archive = "${archive}";
	var archiveInd= archive;
	if(count>0){
			document.getElementById(sortColumn1).innerHTML=imgSrc;
	}
	jQuery(document).ready(function () {
		showSavedEmailId();  
	   jQuery("#ruleWarning").hide();
	   jQuery(document).click(function(e){ 
		var click=jQuery(e.target);
		if((click.closest("div").hasClass("errorComment")|| click.parents().hasClass(".errorComment")) && !(click.closest("a").hasClass("errorCancelLink"))){
			e.stopPropagation();
		}else if((click.closest("div").hasClass("ruleCommentView")||click.parents().hasClass("ruleCommentView")) && !(click.closest("a").hasClass("quickViewCancelLink"))){
			e.stopPropagation();
		}else if(click.closest("a").hasClass("errorClick")){
			jQuery(".errorComment").css('visibility','visible');
		}else if (click.closest("img").hasClass("commentsClickRule")){
			jQuery(".ruleCommentView").css('visibility','visible');	
		}
		else{
			jQuery(".errorComment").hide();
			jQuery(".ruleCommentView").hide();
		}
	});
	// for displays errors
	jQuery(document).on('click', ".errorClick", function(e){
	
		jQuery(".ruleCommentView").hide();
		jQuery(".errorComment").hide();
		
		var x = e.pageX;
		var y = e.pageY;
		jQuery("#ruleErrorTextView").css("top", y + 10);
        jQuery("#ruleErrorTextView").css("left", x + 1000);
		jQuery("#ruleErrorTextView").css("z-index",999999999);
		var routingid= jQuery(this).prop("id");
		var rrid=routingid.replace('rule','');
		jQuery("#"+routingid+"ErrorView").html('<img src="${staticContextPath}/images/loading.gif" width="572px" height="160px"');
		jQuery("#"+routingid+"ErrorView").load("rrManageTab_displayError.action?id="+rrid);
		jQuery("#"+routingid+"ErrorView").show();
		});
	jQuery(document).on('click', ".errorCancelLink", function(){
			jQuery(".errorComment").hide();
		});
		
			
	jQuery('#sortAlertManage').click(function(){
	jQuery("#mainTable").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
			var newSortCol = '5';			
			var newSortOrd = '0';				
			jQuery("#manageRules").load("rrManageTab_display.action?pageNo=1&sortCol=" + newSortCol + "&sortOrd=" + newSortOrd, function()
			{
				jQuery('#name').html("");
	        	jQuery('#contact').html("");
	        	jQuery('#status').html("");
			});
							
		});
		
	function selectionRuleAction()
	{
	   jQuery('#selectedItem').val('-1'); 
	   jQuery(".successBox").hide();
	   setCursorWait(false);
	}
	
	function selectState() {
		jQuery("#ruleWarning").html("");
		jQuery("#confirmationValidationError").html("");
		jQuery("#ruleWarning").hide();
		jQuery("#confirmationValidationError").hide();
		jQuery('#actionRule').prop("disabled", false);
		var checksDom= jQuery('input[name="activate-check"]');
		var checks= checksDom.serializeArray();
		var value="";
		var ruleValue="";
		var ruleName="";
		var ruleId="";
		var ruleIdDisplay="";
		var ruleAction=jQuery("#selectRuleAction").val();
		var ruleStatus="";
		var ruleSubstatus="";
		var toMakeActivetoArchive="";
		var toMakeConflictActive="";
		var toMakeErrorActive="";
		var inactiveConflict="";
		var archiveErrorMessage="One or more rules you selected is currently active and cannot be archived.";
		var archiveOneErrorMessage="The rule you have selected is currently active and cannot be archived. Please change the status to inactive before archiving.";
		var activeOneConflictMessage="The rule you have selected is in conflict with existing rule(s) and cannot be made active.";
		var activeConflictMessage ="One or more rules you selected is in conflict with existing rule(s) and cannot be made active."
		var errorStatusMessage="One or more rules you selected has errors and cannot be made active.";
		var miminimumCount="";
		$.each (checks, function (i, check) {
	    	value+=check.value+",";
	    });
	   	var valueArray=value.split(",");
	   	if(valueArray.length <= 2)
	    {
	    miminimumCount='true';
	    }
	   	if(value=='')
	    {
	    selectionRuleAction();
	    return;
	    }
	    $.getJSON ('rrJson_getRoutingRulesHeadersForRuleIds.action', {id:value,ruleAction:ruleAction}, function(data) {
	    $.each(data, function(i, ruleData) {
			ruleName=ruleData.ruleName;
			ruleId=ruleData.routingRuleHdrId;
			ruleIdDisplay = "ID# "+ruleId;
			ruleStatus=ruleData.ruleStatus;
			ruleSubstatus=ruleData.ruleSubstatus;
			inactiveConflict=ruleData.inactiveConflict;
			if(ruleAction=='inactive')
			{  
				ruleValue+='<table style="border:none;"><tr><td style="display:cell;border:none;word-wrap: break-word;width:10%;"><input type="checkbox"  id="ruleCheck'+ruleId+'" name="action-check" value="'+ruleId+'" checked /></td><td style="display:inline-block;border:none;word-wrap: break-word;text-align: left;width:90%;">'+ruleName+'<br>'+ruleIdDisplay+'</td></tr></table> ';	   
			}
			if(ruleAction=='active')
			{
				if(inactiveConflict=='Conflict')
				{
				toMakeConflictActive='true';
				ruleValue+='<table style="border:none;"><tr><td style="display:cell;border:none;word-wrap: break-word;width:10%;"><input type="checkbox" disabled="disabled" id="ruleCheck'+ruleId+'" name="action-check" value="'+ruleId+'" /></td><td style="display:inline-block;border:none;word-wrap: break-word;text-align: left;width:90%;">'+ruleName+'<br>'+ruleIdDisplay+'</td></tr></table> ';	   
				}
				else if(ruleSubstatus=='Error')
				{
				toMakeErrorActive='true';
				ruleValue+='<table style="border:none;"><tr><td style="display:cell;border:none;word-wrap: break-word;width:10%;"><input type="checkbox" disabled="disabled" id="ruleCheck'+ruleId+'" name="action-check" value="'+ruleId+'" /></td><td style="display:inline-block;border:none;word-wrap: break-word;text-align: left;width:90%;">'+ruleName+'<br>'+ruleIdDisplay+'</td></tr></table> ';	   
				}
				else
				{
				ruleValue+='<table style="border:none;"><tr><td style="display:cell;border:none;word-wrap: break-word;width:10%;"><input type="checkbox"  id="ruleCheck'+ruleId+'" name="action-check" value="'+ruleId+'" checked /></td><td style="display:inline-block;border:none;word-wrap: break-word;text-align: left;width:90%;">'+ruleName+'<br>'+ruleIdDisplay+'</td></tr></table> ';	   
				}
			}
			if(ruleAction=='archived')
			{
				if(ruleStatus=='ACTIVE')
				{
				toMakeActivetoArchive='true';
				ruleValue+='<table style="border:1px;"><tr><td style="display:cell;border:1px;word-wrap: break-word;width:10%;"><input type="checkbox" disabled="disabled" id="ruleCheck'+ruleId+'" name="action-check" value="'+ruleId+'" /></td><td style="display:inline-block;border: 1px;word-wrap: break-word;text-align: left;width:90%;">'+ruleName+'<br>'+ruleIdDisplay+'</td></tr></table> ';	   
				}
				else
				{
				ruleValue+='<table style="border:1px;"><tr><td style="display:cell;border: 1px;word-wrap: break-word;width:10%;"><input type="checkbox"  id="ruleCheck'+ruleId+'" name="action-check" value="'+ruleId+'" checked /></td><td style="display:inline-block;border: 1px;word-wrap: break-word;text-align: left;width:90%;">'+ruleName+'<br>'+ruleIdDisplay+'</td></tr></table> ';	   
				}
			}
	    	});
	    	if(toMakeActivetoArchive=='true')
	    	{
	    	jQuery("#confirmationValidationError").html(archiveErrorMessage);
	    	jQuery("#confirmationValidationError").show();
	    	}
	    	if(toMakeConflictActive=='true')
	    	{
	    	jQuery("#confirmationValidationError").html(activeConflictMessage);
	    	jQuery("#confirmationValidationError").show();
	    	}
	    	if(toMakeErrorActive=='true')
	    	{
	    	jQuery("#confirmationValidationError").append("<p>");
	    	jQuery("#confirmationValidationError").append(errorStatusMessage);
	    	jQuery("#confirmationValidationError").show();
	    	}

	    	jQuery("#ruleList").html(ruleValue);
	    	
	    	if(miminimumCount=='true' && toMakeActivetoArchive=='true')
	    	{
	    	jQuery("#ruleWarning").html(archiveOneErrorMessage);
	    	jQuery("#ruleWarning").addClass("warningBox");
	    	jQuery("#ruleWarning").show();
	    	selectionRuleAction();
	    	}
	    	if(miminimumCount=='true' && toMakeConflictActive=='true')
	    	{
	    	$.getJSON("rrJson_persistErrors.action", {ruleId:',',comment:'Conflicted'}, function (data) {
	    		jQuery("#ruleWarning").html(activeOneConflictMessage);
		    	jQuery("#ruleWarning").addClass("warningBox");
		    	jQuery("#ruleWarning").show();
		    	selectionRuleAction();
		    	jQuery("#mainTable").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
	            	jQuery("#manageRules").load("rrManageTab_display.action?pageNo=1&sortCol=0&sortOrd=0", function()
				{
					jQuery("#ruleWarning").html(activeOneConflictMessage);
					jQuery("#ruleWarning").addClass("warningBox");
					jQuery("#ruleWarning").show();
		    	
				});
	    	});
	    	}
	    	if(miminimumCount=='true' && toMakeErrorActive=='true')
	    	{
	    	jQuery("#ruleWarning").append(errorStatusMessage);
	    	jQuery("#ruleWarning").addClass("warningBox");
	    	jQuery("#ruleWarning").show();
	    	selectionRuleAction();
	    	}
	    	if(miminimumCount=='true' && (toMakeActivetoArchive=='true' || toMakeConflictActive=='true' || toMakeErrorActive=='true'))
	    	{
	    	
	    	}
	    	else
	    	{
			jQuery("#confirmDialog").jqmShow();
			window.scrollTo(0,0);
			setCursorWait(false);
			}
	    	});
	   }
		jQuery(document).on('click', ".activateCheckedACTIVE", function(){
		jQuery("#selectItem").val(-1);
		});
	    jQuery(document).on('click', ".activateCheckedINACTIVE", function(){
		jQuery("#selectItem").val(-1);
		});
		
		/*this function is used for action corresponding to drop down 'select'
			*/
		jQuery("#selectItem").change(function() {
			if(jQuery('#selectItem > option:selected').val() == -1){
	    	}
	    	else{
	    		if(jQuery('#selectItem > option:selected').val() == 1)
	    		{
		    	jQuery('input[class="activateCheckedACTIVE"]').prop('checked', true);
	   			jQuery('input[class="activateCheckedINACTIVE"]').prop('checked', true);
	   			 }
	    	if(jQuery('#selectItem > option:selected').val() == 2)
	   		 {
	   			 jQuery('input[class="activateCheckedACTIVE"]').prop('checked', false);
	   			 jQuery('input[class="activateCheckedINACTIVE"]').prop('checked', false);
	    	}
	    	if(jQuery('#selectItem > option:selected').val() == 3)
	   		 {
	    		jQuery('input[class="activateCheckedACTIVE"]').prop('checked', false);
	    		jQuery('input[class="activateCheckedINACTIVE"]').prop('checked', true);
	   		 }
	    	if(jQuery('#selectItem > option:selected').val() == 4)
	    	{
	    		jQuery('input[class="activateCheckedINACTIVE"]').prop('checked', false);
	    		jQuery('input[class="activateCheckedACTIVE"]').prop('checked', true);
	   		 }
	   	 }
		});
	
	/*this function is used for action corresponding to drop down 'select action'
			*/
		jQuery("#selectedItem").change(function() 
		{
		if(jQuery('#selectedItem > option:selected').val() == -1)
		{
	    }
	    else{
	    	setCursorWait(true);
	    	if(jQuery('#selectedItem > option:selected').val() == 1)
	    	{
	    	jQuery("#selectRuleAction").val('inactive');
	    	jQuery("#confirmationHeading").html("Are you sure you want to make the following rule(s) inactive? <br> Uncheck any that you want to remain active."); 
	   		selectState();
	   		}
	    	if(jQuery('#selectedItem > option:selected').val() == 2)
	    	{
	    	jQuery("#selectRuleAction").val('active');
	       	jQuery("#confirmationHeading").html("Are you sure you want to make the following rule(s) active? <br> Uncheck any that you want to remain inactive."); 
	    	selectState();
	    	}
	    	if(jQuery('#selectedItem > option:selected').val() == 3)
	    	{
	    	jQuery("#selectRuleAction").val('archived');
	  		jQuery("#confirmationHeading").html("Are you sure you want to archive this rule(s)? <br><br>Once a rule is archived, it cannot be activated or updated.<br>Uncheck any that you want to remain in the current status."); 
	    	 selectState();
	    	}
	    }
		});
	
		jQuery(".pageIndex").click(function () {
			jQuery("#mainTable").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
			jQuery("#manageRules").load("rrManageTab_display.action?pageNo=" + jQuery(this).prop("id"), function()
			{
				if(curSortCol==5)
	           	{
					jQuery('#name').html("");
		        	jQuery('#contact').html("");
		        	jQuery('#status').html("");
	        	}
	        	if(curSortCol!=1 && curSortCol!=2 && curSortCol!=4)
	            {
					jQuery('#name').html("");
			        jQuery('#contact').html("");
			        jQuery('#status').html("");
		        }
			});
		});
		
		jQuery('#pageNoTextTop').keypress(function (e){
  			//if the letter is not digit then display error and don't type anything
  			if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57))
  			{
   				return false;
 			}
		});
	
		jQuery('#pageNoTextBottom').keypress(function (e){
  			//if the letter is not digit then display error and don't type anything
  			
  			if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57))
  			{
   				return false;
 			}
		});
		
		jQuery(".goToPageTop").click(function () {
			var totalPages=jQuery("#manageRuleTotPages").val();
	 		var pageNo = jQuery('#pageNoTextTop').val();
	 		
	 		if(pageNo == null || pageNo == ""){
	   		 return false;
	   			}
	   		pageNo = parseInt(pageNo);
			totalPages = parseInt(totalPages);	
	 	 	if(pageNo > totalPages)
	 			{
	 			  pageNo=totalPages;
	 			}
	 		if(pageNo < 1)
	 			{
	 			  pageNo=1;
	 			}
	   /*validate page number entered for max no: of pages and numeric value before ajax */ 
	   
	   jQuery("#mainTable").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
	   jQuery("#manageRules").load("rrManageTab_display.action?pageNo=" + pageNo, function()
			{
			if(curSortCol==5)
	          	{
				jQuery('#name').html("");
	        	jQuery('#contact').html("");
	        	jQuery('#status').html("");
		       	}
		     if(curSortCol!=1 && curSortCol!=2 && curSortCol!=4)
		      {
				jQuery('#name').html("");
				jQuery('#contact').html("");
				jQuery('#status').html("");
			   }
			});
  	});
  
	jQuery(".goToPageBottom").click(function () {	
		var totalPages=jQuery("#manageRuleTotPages").val();
 	 	var pageNo = jQuery('#pageNoTextBottom').val();
 	 	if(pageNo == null || pageNo == ""){
   			 return false; 
   			}
 		 	pageNo = parseInt(pageNo);
		   	totalPages = parseInt(totalPages);
		   	if(pageNo > totalPages)
 			{
 			  pageNo=totalPages;
 			}
 		if(pageNo < 1)
 			{
 			  pageNo=1;
 			}
   		
   
   /*validate page number entered for max no: of pages and numeric value before ajax */ 
   
   jQuery("#mainTable").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
   jQuery("#manageRules").load("rrManageTab_display.action?pageNo=" + pageNo, function()
		{
			if(curSortCol==5)
           	{
				jQuery('#name').html("");
	        	jQuery('#contact').html("");
	        	jQuery('#status').html("");
        	}
        	if(curSortCol!=1 && curSortCol!=2 && curSortCol!=4)
	        {
				jQuery('#name').html("");
			    jQuery('#contact').html("");
			    jQuery('#status').html("");
		    }
		});
  });
	
	jQuery(".sortColumn1").click(function () {	
		
			jQuery("#mainTable").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
			var newSortCol = jQuery(this).prop("id").substring(7, 6);
			
			var newSortOrd = null;
			if (newSortCol != curSortCol) { // If sort column changes, reset sort order to ascending
				newSortOrd = 0;
			} else {
				newSortOrd = 1 - curSortOrd; // toggle between ascending and descending order
			}
			switch (newSortCol) {
	  			case "1":
					sortColumn1 = "name";
					break;
	  			case "2":
					sortColumn1 = "contact";
					break;
	 			 case "4":
					sortColumn1 = "status";
					break;
	 			case "7":sortColumn1="autoStatus";
					break;
			}
			_sortDir = (newSortOrd == 0) ? "down" : "up";
			imgSrc = "<img src=\"" + staticContextPath + "/images/grid/arrow-" + _sortDir + "-white.gif\"/>";
			jQuery("#manageRules").load("rrManageTab_display.action?pageNo=1&sortCol=" + newSortCol + "&sortOrd=" + newSortOrd);
			curSortCol = newSortCol;
			curSortOrd = newSortOrd;
			count++;
		});

		jQuery("#confirmDialog").jqm({modal:true}); 
		jQuery("#confirmDialogforForceActive").jqm({modal:true});
	 	// Show modal on deactivate only
	 	jQuery(document).on('click', ".activateCheck", function(){
			_jqm_newState = jQuery(this).is(":checked");
			_jqm_comment = "";
			_jqm_ruleId = jQuery(this).val();
			if (!_jqm_newState) {
				jQuery("#confirmDialog").jqmShow();
				window.scrollTo(0,0);
			} else {
				setRuleStatus();
			}
		});
		
		jQuery("#modalClose-img").click(function () {
			cancelModal();
		});
		
		jQuery("#modifiedDateSort").click(function() {
		    count = 0;
		    jQuery("#mainTable").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
            jQuery("#manageRules").load("rrManageTab_display.action?pageNo=1&sortCol=0&sortOrd=0"); 
        });
        
        
		jQuery(".commentsClickRule").click(function (e) {	
		
		  jQuery(".errorComment").hide();
		  jQuery(".ruleCommentView").hide();
			var x = e.pageX;
			var y = e.pageY;
		    jQuery("#ruleCommentsView").css("top", y + 10);
            jQuery("#ruleCommentsView").css("left", x + 10);
			jQuery("#ruleCommentsView").css("z-index",999999999);
			
			var routingid= jQuery(this).prop("id");
			jQuery("#"+routingid+"ruleCommentsView").html('<center><img src="${staticContextPath}/images/loading.gif" width="500px" height="160px"</center>');
			jQuery("#"+routingid+"ruleCommentsView").load("rrManageTab_displayQuickView.action?id="+routingid,function(){
				var autoAcceptStat='${autoAcceptStatusBuyer}';
					if(autoAcceptStat=='true')
					{
					jQuery(".accpetHistoryDiv").load("rrManageTab_displayAutoAcceptHistoryForBuyer.action?id="+routingid);
					}
				});
				
			jQuery("#"+routingid+"ruleCommentsView").show();
			});
			
		
		//Sl 15642 Changes to add history tab in quick view
		jQuery("#quickViewBuyer").click(function (e) {
			jQuery("#autoAcceptHistoryBuyer").css("color", "#00A0D2");
			jQuery(".accpetHistoryDiv").hide();
			jQuery(".ruleCommentsTextView").show();
			});


		jQuery("#autoAcceptHistoryBuyer").click(function (e) {
				jQuery("#quickViewBuyer").css("color", "#00A0D2");
				jQuery(".accpetHistoryDiv").show();
				jQuery(".ruleCommentsTextView").hide();
			});
		
		jQuery(".commentsClickRule").blur(function (e){
		jQuery(".ruleCommentsView").hide();
		});
		
		});
	
	//Method to validate and submit the entered email id to DB
	function submitEmailForNotify()
	{
		if(jQuery('#statusChangeNotify').prop('checked')) 
		{
			var manageRuleBuyerEmailId=jQuery('#statusChangeNotifiEmailId').val();
			if(manageRuleBuyerEmailId.length>0 && manageRuleBuyerEmailId.length<=100)
			{
				var emailValidCheck = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$/;
				if (!emailValidCheck.test(manageRuleBuyerEmailId)) 
				{
					jQuery("#emailLengthValidtaionError").hide();
					jQuery("#emailMissingError").show();
				}
				else
				{
					jQuery("#emailLengthValidtaionError").hide();
					jQuery("#emailMissingError").hide();
					var indAutoStatus=1;
					window.location.href = 'rrManageTab_configureBuyerEmailId.action?manageRuleBuyerEmailId='+manageRuleBuyerEmailId+'&indEmailNotifyRequired='+indAutoStatus;
				}
			}
			else
			{
				if(manageRuleBuyerEmailId.length>100)
					{
					jQuery("#emailMissingError").hide();
					jQuery("#emailLengthValidtaionError").show();
					}
				else
					{
					jQuery("#emailLengthValidtaionError").hide();
					jQuery("#emailMissingError").show();
					}
			}
		}
		else
			{
			var indAutoStatus=0;
			window.location.href = 'rrManageTab_configureBuyerEmailId.action?&indEmailNotifyRequired='+indAutoStatus;

			}
	}
//SL 15642 JavaScript method to show aleady saved email id of buyer
function showSavedEmailId()
	{
		var emailRequiredInd='${emailRequiredInd}';
		if(emailRequiredInd=='1')
		{
		var buyerExisitingEmailId='${buyerSavedEmailId}';
		jQuery('#statusChangeNotify').prop('checked','checked');
		jQuery('#statusChangeNotifiEmailId').val(buyerExisitingEmailId);
		}

	}

</script>


<div id="tab_manageRules" style="width: auto;"  >
	<div id="manageRules">
		<form action="" id="routingRulesForm">
			<tags:security actionName="routingRulesAction_edit">
				<input type="button"
					onclick="window.location.href='rrCreateRuleAction_create.action?tabType=manageTab';"
					class="button action right" value="Create New Rule"
					id="saveDoneButton" />
			</tags:security>
			
			<div id="tabHead" style="height:70px;">
				<h3>
					Manage Rules
				</h3>			
				
					<p>
						<b style="font-size: 10px;">Rules are displayed in order of most recently created or modified.&nbsp;Only Active and Inactive Rules are shown below.</b>
					</p>
					<div class="errorMsg" id="emailMissingError" style="display: none;">Please Enter a valid Email Id.</div>
					<div class="errorMsg" id="emailLengthValidtaionError" style="display: none;">Please Enter a valid Email Id with less than 100 characters.</div>
				<c:if test="${autoAcceptBuyerAdmin==true }">
				<p style="padding-top:10px;">
				<a href="" style="" id="whatIsManageRuleNotify" onclick="return false;">
							<img src="${staticContextPath}/images/icons/help_16.gif" width="16"
							height="16" border="0" style="position: relative; top: -18px; left:" /> 
						</a>
						
				<input  type="checkbox" id="statusChangeNotify" style="position:relative;left:-12px;z-index:1;">
				<div style="font-size: 10px; position: relative; left: 10px; top: -18px;">I want the rule level notification for Auto Acceptance to be sent to </div>
				<input type="text" id="statusChangeNotifiEmailId" style="position: relative; left: 380px; top: -38px;">
					<input type="button"
					onclick="submitEmailForNotify();"
					class="action" value="Apply"
					id="statusChangeEmailNotifyDone" style="position: relative; left: 560px; top: -64px;width:60px;height:-20px;"/></p>
					</c:if>
					
			</div>
			
		    <c:if test="${msgString != 'Empty'}">
		        <div class="successBox">
			        ${msgString}
			    </div>
			</c:if>

			
			<div id="ruleWarning" >
				</div>
			
			<div id="page" align="right" style="padding-top: 5px;">
			
				<c:if test="${pagingCriteria.currentIndex>1}">
					<a class="pageIndex paginationLink" id="1"
						style="text-decoration: underline;color: #00A0D2;"><<</a>
					<a  class="pageIndex paginationLink" id="${pagingCriteria.currentIndex-1}" style="color: #00A0D2"><b><</b>
					</a>
				</c:if>
				<input type="hidden" id="manageRuleTotPages" value="${pagingCriteria.totalPages}"/>
				Page ${pagingCriteria.currentIndex} of ${pagingCriteria.totalPages}
    <c:if
					test="${pagingCriteria.currentIndex!=pagingCriteria.totalPages}">
					<a class="pageIndex paginationLink" id="${pagingCriteria.currentIndex+1}" style="color: #00A0D2"><b>></b></a>
					<a class="pageIndex paginationLink"  id="${pagingCriteria.totalPages}"
						style="text-decoration: underline;color: #00A0D2;">>></a>
				</c:if>
				<c:if test="${pagingCriteria.totalPages>1}">
				To Page <input type="text" id="pageNoTextTop" size="3"/> <input type="button" class="goToPageTop" value="GO"/>	
				</c:if>
				<div style="padding-top: 8px; padding-bottom: 4px">
				${pagingCriteria.pageComment} rules.
				</div>
			</div>
			<input type="hidden" id="selectRuleAction">
			<div id="manageSortd">
			<span id="modifiedDateSort"><a class="paginationLink" style="color: #00A0D2;float: left;padding-top: 5px ">Re-sort by Most Recent</a></span>
			<span>
			<c:if test="${!isReadOnly}">			
			<select id="selectedItem" style="float: right;">
			<option value="-1">
				<b>SELECTED ITEMS</b>
			</option>
			<option value="1">
				Make Inactive
			</option>
			<option value="2">
				Make Active
			</option>
			<option value="3">
				Archive 
			</option>
			 </select>
			 </c:if>
			 </span>
			 </div>
			
			<table id="mainTable" border="0" cellpadding="0" cellspacing="0" style="padding-top: 6px">
				<thead>

					<th id="column0" class="sortColumn1" style="display: none;">
						<span>moddate</span>
					</th>

					<th id="column1" class="sortColumn1">
						<span style="margin-left: 10px">Name </span><span id="name"></span>
					<span id="sortAlertManage" style="margin-left: 40px">Sort<img
					 style="padding: 0px 4px 0px 5px; position: relative; top: 2px;"
					 src="${staticContextPath}/images/icons/conditional_exclamation.gif" />Alerts to Top</span>
					</th>
					<th id="column2" class="sortColumn1"
						style="background-position: 60px 11px;">
						<span>Provider Firm</span><span id="contact"></span>
					</th>
					<c:if test="${autoAcceptStatus==true }">
					<th id="column7"  class="sortColumn1" style="text-align: center;width:18%">
						<span>Auto Accept Status</span><span id="autoStatus"></span>
					</th>
					</c:if>
					<!--<th id="column3" style="text-align: center">
						<span>Action</span>
					</th>-->
							
					<!-- SL-20363 Need to Add UI for Forceful CAR Activation for buyer -->
					<c:choose>
						<c:when
							test="${(carFeatureOn=='true') && (System_Property_ForceActiveInd=='true') && (UserForcefulCARActPermission=='true')}">

							<th id="column3" style="text-align: center">
								<div style="width: 75px;">
									<span>Action</span>
								</div>
							</th>

							<th>
								<div style="width: 90px;">
									<span>Forced Action</span>
								</div>
							</th>
						</c:when>
						<c:otherwise>
							<th id="column3" style="text-align: center">
								<span>Action</span>
							</th>
						</c:otherwise>
					</c:choose>
					<!-- End Forceful CAR Activation for buyer -->

					

					
					<th id="column4" class="sortColumn1" style="text-align: center;">
						<span>Status </span><span id="status"></span>
					</th>
					<c:if test="${!isReadOnly}">
					<th id="selectColumn" class="selectColumn" style="text-align: center">
						<select id="selectItem" style="float: right;">
						<option value="-1">
							<b>SELECT</b>
						</option>
						<option value="1">
							All
						</option>
						<option value="2">
							None
						</option>
						<option value="3">
							Inactive 
						</option>
						 <option value="4">
							Active 
						</option>
						</select> 
						<span id="status"></span>
					</th>
					</c:if>
				</thead>
				<tbody>
				 <c:forEach items="${listRoutingRuleHdr}" var="rule"
						varStatus="status">
						<tr id="rowid${status.count}" class="dashboardRow">
							<td style="display: none;">
								${rule.modifiedDate}
								<br />
							</td>
							<td>
							<table style="border:0px;table-layout: fixed;font-size: 11px; font-weight: normal;">
								<tr style="border:0px;">
									<td style="border:0px;width:8%;vertical-align: top;">
										<img alt="Rule Quickview" style="cursor: pointer;" title="Rule Quickview" id="${rule.routingRuleHdrId}"
										src="/ServiceLiveWebUtil/images/s_icons/magnifier.png"
										class="commentsClickRule"/>
										<div id="${rule.routingRuleHdrId}ruleCommentsView" class="ruleCommentView" style="display:none; z-index:999999999; width:866px; height:160px; overflow:auto; border: solid 1px #ccc; background: #fff;  position:absolute; padding:5px;">
										</div>
									</td>
									<td style="border:0px; width:95%">
										<div style="border:0px; word-wrap: break-word;">
										${rule.ruleName}						
										</div>
										ID#&nbsp;${rule.routingRuleHdrId}
									</td>
								</tr>	
								<c:forEach items="${rule.routingRuleAlerts}" var="alert">
									<c:if test="${alert.alertStatus == alertActive}">
										<tr style="border:0px;">
											<td style="border:0px;">			
												<img src="${staticContextPath}/images/icons/conditional_exclamation.gif">
											</td>
											<td style="border:0px;">
												ALERT:
												<c:out value="${alert.alertType.description}" />
											</td>	
											<script>('#rowid${status.count}');</script>
										</tr>
									</c:if>
								</c:forEach>
								<c:if test="${not empty rule.routingRuleError}">
									<tr style="border:0px;">
										<td style="border:0px;vertical-align: top;">
											<img src="${staticContextPath}/images/icons/conditional_exclamation.gif">
										</td>
										<td style="border:0px;vertical-align: top;">
											<b>ALERT:
											<c:out value="Rule Criteria Conflict(s). Details" /></b>
											<a href="" title="" id="${rule.routingRuleHdrId}rule" style="text-decoration:none; font-weight:bold; color:#00A0D2;"
											class="errorClick" onclick="return false;">(+)</a>
											
										</td>
										<script>('#rowid${status.count}');</script>																				
									</tr>	
								</c:if>							
							</table>
							<div id="${rule.routingRuleHdrId}ruleErrorView" class="errorComment" style="display:none; z-index:999999999; margin-left:300px; width:572px; height:160px; overflow:auto; border: solid 1px #ccc; background: #fff;  position:absolute; padding:5px;">
							</div>	
							</td>
							<td style="word-wrap: break-word;">
							<c:set value="${fn:length(rule.routingRuleVendor)}" var="vendorFirmSize"></c:set>
								<c:forEach  items="${rule.routingRuleVendor}" var="routeVendor">
								<c:set value="${routeVendor.vendor}" var="vendorVar"></c:set><div style="padding-top:5px;padding-bottom:5px;">
							<a  style="color: #00A0D2;" onclick="javascript:openProviderFirmProfile(${vendorVar.id})" href="javascript:void(0);">
							<b>${vendorVar.businessName}<b style="padding-left:8px;">(${vendorVar.id})</b></b>
							</a></div>
							
									<c:if test="${vendorFirmSize >1}"><hr class="bold"></hr>
									<c:set var="vendorFirmSize" value="${vendorFirmSize-1 }"></c:set>
									</c:if>
								</c:forEach>
							
							</td><c:if test="${autoAcceptStatus==true}">
							<td style="text-align:center;">
							
							<c:set value="${fn:length(rule.routingRuleVendor)}" var="vendorFirmSize"></c:set>

								<c:forEach  items="${rule.routingRuleVendor}" var="routeVendor"><div style="padding-top:5px;padding-bottom:5px;">
								<c:choose><c:when test="${not empty routeVendor.autoAcceptStatus}">
								<b>${routeVendor.autoAcceptStatus}
								</b>
								</c:when>
								<c:otherwise>-</c:otherwise>
								</c:choose></div>
									<c:if test="${vendorFirmSize >1}"><hr class="bold"></hr>
									<c:set var="vendorFirmSize" value="${vendorFirmSize-1 }"></c:set>
									</c:if>
								</c:forEach>
							
							</td></c:if>

							<!-- SL-20363 Need to Add UI for Forceful CAR Activation for buyer# 3000 -->
							<c:choose>
								<c:when
									test="${(carFeatureOn=='true') && (System_Property_ForceActiveInd=='true') && (UserForcefulCARActPermission=='true')}">
									<td>
										<div style="width: 75px;">
											<tags:security actionName="routingRulesAction_view">
												<a class="bold"
													href="rrCreateRuleAction_view.action?rid=${rule.routingRuleHdrId}&tabType=manageTab">View</a>
											</tags:security>
											<tags:security actionName="routingRulesAction_edit">
												<tags:security actionName="routingRulesAction_view">
													<b> |</b>
												</tags:security>
												<a class="bold"
													href="rrCreateRuleAction_edit.action?rid=${rule.routingRuleHdrId}&tabType=manageTab">Edit</a>
											</tags:security>
										</div>
									</td>

									<td>
										<div style="width: 90px;">
										<!--SL-20698 changes starts  -->
										<c:set var="carRuleName" value="${rule.ruleName}"/>
											
												<c:set var="singlequote" value="'"/>
												<c:set var="doublequote" value='"'/>
												
												<!-- change to escape single quotes starts-->
												<c:if test="${fn:contains(carRuleName, singlequote)}">
													<c:set var="replace" value="\\'" />
													<c:set var="carRuleName" value="${fn:replace(carRuleName, singlequote, replace)}"/>
												</c:if>
												
												<!-- change to escape single quotes ends-->
												
												<!-- change to escape Double quotes starts-->
												<c:if test="${fn:contains(carRuleName, doublequote)}">
													<c:set var="carRuleName" value="${fn:escapeXml(carRuleName)}"/>
												</c:if>
												<!-- change to escape Double quotes ends-->
													<!--SL-20698 changes ends  -->
											<c:choose>
												<c:when
													test="${(rule.ruleStatus == 'INACTIVE') && (rule.forceActiveInd == 'true')}">
													<!-- Changes for SL-20625 starts-->
													<a class="bold" href="#"
														onclick="javascript:showConfirmBox('${rule.routingRuleHdrId}','${carRuleName}');">Force
														Active</a>
													<!-- Changes for SL-20625 ends-->
												</c:when>
											</c:choose>
										</div>
									</td>
								</c:when>
								<c:otherwise>
									<td>
										<div style="width: 80px;">
											<tags:security actionName="routingRulesAction_view">
												<a class="bold"
													href="rrCreateRuleAction_view.action?rid=${rule.routingRuleHdrId}&tabType=manageTab">View</a>
											</tags:security>
											<tags:security actionName="routingRulesAction_edit">
												<tags:security actionName="routingRulesAction_view">
													<b> |</b>
												</tags:security>
												<a class="bold"
													href="rrCreateRuleAction_edit.action?rid=${rule.routingRuleHdrId}&tabType=manageTab">Edit</a>
											</tags:security>
										</div>
									</td>
								</c:otherwise>
							</c:choose>
							<!-- End for Forceful CAR Activation  -->






							<c:choose>
								<c:when test="${rule.ruleStatus == 'ACTIVE'}">
									<c:set var="checked" value="checked" />
									<c:remove var="ruleStatus" />
								</c:when>
								<c:otherwise>
									<c:remove var="checked" />
									<c:set var="ruleStatus" value="ruleINACTIVE" />
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test="${isReadOnly}">
									<td id="col4rule${rule.routingRuleHdrId}"
										class="column4 ${ruleStatus}">
										${rule.ruleStatus}
									</td>
								</c:when>
								<c:otherwise>
									<td id="col4rule${rule.routingRuleHdrId}"
										class="column4 ${ruleStatus}" >
										<!--<input type="checkbox"
											name="activate-check-${rule.routingRuleHdrId}"
											id="activate-check-${rule.routingRuleHdrId}"
											class="activateCheck" value="${rule.routingRuleHdrId}" ${checked} />--> 
										<br />
										<label for="activate-check-${rule.routingRuleHdrId}">
											<c:choose>
								<c:when test="${rule.ruleStatus == 'ACTIVE'}">
									Active
								</c:when>
								<c:when test="${rule.ruleStatus == 'INACTIVE'}">
									Inactive
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
										</label>
									</td>
									<td id="col5rule${rule.routingRuleHdrId}" style="padding-left: 40px">
										<input type="checkbox"
											name="activate-check"
											id="activate-checked-${rule.routingRuleHdrId}"
											class="activateChecked${rule.ruleStatus}" value="${rule.routingRuleHdrId}"  />
										<br />
									</td>
								</c:otherwise>
							</c:choose>
							
						</tr>
					</c:forEach> 	
				</tbody>
			</table>
		</form>

		


		<div id="page" align="right" style="padding-top: 5px;">
		
			<c:if test="${pagingCriteria.currentIndex>1}">
				<a class="pageIndex paginationLink" id="1"
					style="text-decoration: underline;color: #00A0D2;"><<</a>
				<a  class="pageIndex paginationLink" id="${pagingCriteria.currentIndex-1}" style="color: #00A0D2"><b><</b>
				</a>
			</c:if>
			Page ${pagingCriteria.currentIndex} of ${pagingCriteria.totalPages}
    		<c:if test="${pagingCriteria.currentIndex!=pagingCriteria.totalPages}">
				<a class="pageIndex paginationLink" id="${pagingCriteria.currentIndex+1}" style="color: #00A0D2">
				<b>></b>
				</a>
				<a class="pageIndex paginationLink" id="${pagingCriteria.totalPages}"
					style="text-decoration: underline;color: #00A0D2;">>></a>
			</c:if>
			<c:if test="${pagingCriteria.totalPages>1}">
			To Page <input type="text" id="pageNoTextBottom" size="3"/> <input type="button" class="goToPageBottom" value="GO"/>
			</c:if>
			<div style="padding-top: 10px">
				${pagingCriteria.pageComment} rules.
			</div>
		</div>
	</div>
</div>
<script>
// <!-- SL-20363 Need to Add UI for Forceful CAR Activation for buyer# 3000 -->
function showConfirmBox(id, name){
document.getElementById('selectedRuleId').innerHTML = id;
document.getElementById('CarRuleId').value=id;
document.getElementById('CarRuleName').value=name;
document.getElementById('errorMessageforRuleId').innerHTML ="";
jQuery("#confirmDialogforForceActive").jqmShow();
window.scrollTo(0,0);
}

function hideConfirmBox(){
    jQuery("#confirmDialogforForceActive").jqmHide();
    return false;
}

function validateForceActiveComment(){
var comment = document.getElementById('forceRuleComment').value;
if(jQuery.trim(comment) == ''){
document.getElementById('errorMessageforRuleId').innerHTML ="You must enter a comment to activate this rule";
return false;
}
else{
document.getElementById('errorMessageforRuleId').innerHTML ="";
return true;
}
}
// End for Forceful CAR Activation
jQuery("#whatIsManageRuleNotify").mouseover(function(e){
    var x = e.pageX;
    var y = e.pageY;
    jQuery("#explainer-manageRuleNotifyInfo").css("top",y-20);
    jQuery("#explainer-manageRuleNotifyInfo").css("left",x+20);
    jQuery("#explainer-manageRuleNotifyInfo").show();
   
	}); 
	jQuery("#whatIsManageRuleNotify").mouseout(function(e){
		jQuery("#explainer-manageRuleNotifyInfo").hide();
	});
</script>
<div id="explainer-manageRuleNotifyInfo" style="display:none; position:fixed; width:450px; height:125px; padding:10px 20px; line-height: 130%; font-size:10px;">
		<p style="background-color:#F5F6CE; ;border: 1px black solid;">Auto Acceptance is a feature to enable automation of Service Order Acceptance.<br>
		<h style="padding-left:10px;">An email will be sent if provider saves the Auto Acceptance status as OFF.</h></p>
</div>