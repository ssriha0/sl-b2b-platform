<%@ page language="java"
	import="java.util.*,com.servicelive.routingrulesengine.RoutingRulesConstants"
	pageEncoding="UTF-8"%>
<%@page
	import="com.servicelive.routingrulesengine.vo.RoutingRulesPaginationVO"%>
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

<c:set var="autoAcceptStatusBuyer"
	value="<%=request.getSession().getAttribute("autoAcceptStatus")%>" />
	
<c:set var="alertActive"
	value="<%=RoutingRulesConstants.ROUTING_ALERT_STATUS_ACTIVE%>" />
<c:set var="archive" scope="request" value="${archiveInd}"> </c:set>
	
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/routingrules.css" />
<link rel="stylesheet"
	href="${staticContextPath}/css/jqueryui/jquery-ui.custom.min.css"
	type="text/css"></link>
<link rel="stylesheet"
	href="${staticContextPath}/javascript/plugins/tablesorter/blue/style.css"
	type="text/css" media="print, projection, screen" />
<script type="text/javascript">var staticContextPath="${staticContextPath}";
var imgSrc1;
</script>

<tags:security actionName="routingRulesAction_view">
	<c:set var="isReadOnly" value="true" scope="page" />
</tags:security>
<tags:security actionName="routingRulesAction_edit">
	<c:set var="isReadOnly" value="false" scope="page" />
</tags:security>

<%
	int sorcol1 = 0;
	int sorord1 = 0;
	RoutingRulesPaginationVO pagination = (RoutingRulesPaginationVO) session
			.getAttribute("searchRulePagination");
	if (pagination != null) {
		sorcol1 = pagination.getSortCol();
		sorord1 = pagination.getSortOrd();
	}
%>
<script type="text/javascript">
	
	var curSearchSortCol = '<%=sorcol1%>'; // Default sort on 0 = modified date; 1 = Name, 2 = Contact, 3 = Action, 4 = Status;
	var curSearchSortOrd = '<%=sorord1%>';// Default sort order 0 = descending; 1 = ascending;
	var archive = "${archive}";
	var archiveInd= archive;
	var alertClick = jQuery('#alertClick').val();
	
	jQuery(document).ready(function($)
	{
		jQuery("#showArchiveRules").prop("checked",true);
		if(archive==0)
       	{
       		jQuery("#showArchiveRules").prop("checked",false);
       	}
	 	if(searchCount>0)
	 	{
			document.getElementById(sortColumn).innerHTML=imgSrc1;
		}
	 	jQuery(document).on('click', ".activateCheckedSearchACTIVE", function(){
		jQuery("#selectItemSearch").val(-1);
		});
	 	jQuery(document).on('click', ".activateCheckedSearchINACTIVE", function(){
		jQuery("#selectItemSearch").val(-1);
		});
		
		 jQuery(document).click(function(e){ 
		var click=jQuery(e.target);
		if((click.closest("div").hasClass("errorCommentSearch")|| click.parents().hasClass(".errorCommentSearch")) && !(click.closest("a").hasClass("errorCancelLink"))){
			e.stopPropagation();
		}else if((click.closest("div").hasClass("ruleCommentViewSearch")||click.parents().hasClass("ruleCommentViewSearch")) && !(click.closest("a").hasClass("quickViewCancelLinkSearch"))){
			e.stopPropagation();
		}else if(click.closest("a").hasClass("errorClickSearch")){
			jQuery(".errorCommentSearch").css('visibility','visible');
		}else if (click.closest("img").hasClass("commentsClickRuleSearch")){
			jQuery(".ruleCommentViewSearch").css('visibility','visible');	
		}
		else{
			jQuery(".errorCommentSearch").hide();
			jQuery(".ruleCommentViewSearch").hide();
		}
	});
				
		/*this function is used for action corresponding to drop down 'select'
			*/
			
		jQuery("#selectItemSearch").change(function() {
			if(jQuery('#selectItemSearch > option:selected').val() == -1){
	    	}
	    	else{
	    		if(jQuery('#selectItemSearch > option:selected').val() == 1)
	    		{
		    	jQuery('input[class="activateCheckedSearchACTIVE"]').prop('checked', true);
	   			jQuery('input[class="activateCheckedSearchINACTIVE"]').prop('checked', true);
	   			 }
	    	if(jQuery('#selectItemSearch > option:selected').val() == 2)
	   		 {
	   			 jQuery('input[class="activateCheckedSearchACTIVE"]').prop('checked', false);
	   			 jQuery('input[class="activateCheckedSearchINACTIVE"]').prop('checked', false);
	    	}
	    	if(jQuery('#selectItemSearch > option:selected').val() == 3)
	   		 {
	    		jQuery('input[class="activateCheckedSearchACTIVE"]').prop('checked', false);
	    		jQuery('input[class="activateCheckedSearchINACTIVE"]').prop('checked', true);
	   		 }
	    	if(jQuery('#selectItemSearch > option:selected').val() == 4)
	    	{
	    		jQuery('input[class="activateCheckedSearchINACTIVE"]').prop('checked', false);
	    		jQuery('input[class="activateCheckedSearchACTIVE"]').prop('checked', true);
	   		 }
	   	 }
		});
		
		jQuery("#ruleWarningSearch").hide();
		
		function selectionRuleAction()
	{
	   jQuery('#selectedItemSearch').val('-1');
	   setCursorWait(false);
	}
	
	/* This function is used to perform action 
	after the user presses save button of confirmation dialog */
    
		/* this function is used to display confirmation dialog for 
	a set of ruleIds for  actions make active,inactive,archive.   */
	function selectState() {
		jQuery("#ruleWarningSearch").html("");
		jQuery("#confirmationValidationError").html("");
		jQuery("#ruleWarningSearch").hide();
		jQuery("#confirmationValidationError").hide();
		jQuery('#actionRule').prop("disabled", false);
		var checksDom= jQuery('input[name="activate-checkSearch"]');
		var checks= checksDom.serializeArray();
		var value="";
		var ruleValue="";
		var ruleName="";
		var ruleId="";
		var ruleIdDisplay="";
		var ruleAction=jQuery("#selectRuleActionSearch").val();
		var ruleStatus="";
		var ruleSubstatus="";
		var toMakeActivetoArchive="";
		var toMakeConflictActive="";
		var toMakeErrorActive="";
		var inactiveConflict="";
		var archiveErrorMessage="One or more rules you selected is currently active and cannot be archived.";
		var activeConflictMessage="One or more rules you selected is in conflict with existing rule(s) and cannot be made active.";
		var errorStatusMessage="One or more rules you selected has errors and cannot be made active.";
		var archiveOneErrorMessage="The rule you have selected is currently active and cannot be archived. Please change the status to inactive before archiving.";
		var activeOneConflictMessage="The rule you have selected is in conflict with existing rule(s) and cannot be made active.";
	
		var miminimumCount="";
		
		$.each (checks, function (i, check) {
	    	value+=check.value+",";
	    });
	   	var valueArray=value.split(",");
	   	if(valueArray.length <= 2){
	    	miminimumCount='true';
	    }
	   	if(value==''){
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
				ruleValue+='<table style="border:none;"><tr><td style="display:cell;border:none;word-wrap: break-word;width:10%"><input type="checkbox"  id="ruleCheck'+ruleId+'" name="action-check" value="'+ruleId+'" checked /></td><td style="display:inline-block;border:none;word-wrap: break-word;width:90%">'+ruleName+'<br>'+ruleIdDisplay+'</td></tr></table> ';	   
			}
			if(ruleAction=='active')
			{
				if(inactiveConflict=='Conflict')
				{
				toMakeConflictActive='true';
				ruleValue+='<table style="border:none;"><tr><td style="display:cell;border:none;word-wrap: break-word;width:10%"><input type="checkbox" disabled="disabled" id="ruleCheck'+ruleId+'" name="action-check" value="'+ruleId+'" /></td><td style="display:inline-block;border:none;word-wrap: break-word;width:90%">'+ruleName+'<br>'+ruleIdDisplay+'</td></tr></table> ';	   
				}
				else if(ruleSubstatus=='Error')
				{
				toMakeErrorActive='true';
				ruleValue+='<table style="border:none;"><tr><td style="display:cell;border:none;word-wrap: break-word;width:10%"><input type="checkbox" disabled="disabled" id="ruleCheck'+ruleId+'" name="action-check" value="'+ruleId+'" /></td><td style="display:inline-block;border:none;word-wrap: break-word;width:90%">'+ruleName+'<br>'+ruleIdDisplay+'</td></tr></table> ';	   
				}
				else
				{
				ruleValue+='<table style="border:none;"><tr><td style="display:cell;border:none;word-wrap: break-word;width:10%"><input type="checkbox"  id="ruleCheck'+ruleId+'" name="action-check" value="'+ruleId+'" checked /></td><td style="display:inline-block;border:none;word-wrap: break-word;width:90%">'+ruleName+'<br>'+ruleIdDisplay+'</td></tr></table> ';	   
				}
			}
			if(ruleAction=='archived')
			{
				if(ruleStatus=='ACTIVE')
				{
				toMakeActivetoArchive='true';
				ruleValue+='<table style="border:1px;"><tr><td style="display:cell;border:1px;word-wrap: break-word;width:10%"><input type="checkbox" disabled="disabled" id="ruleCheck'+ruleId+'" name="action-check" value="'+ruleId+'" /></td><td style="display:inline-block;border: 1px;word-wrap: break-word;width:90%">'+ruleName+'<br>'+ruleIdDisplay+'</td></tr></table> ';	   
				}
				else
				{
				ruleValue+='<table style="border:1px;"><tr><td style="display:cell;border: 1px;word-wrap: break-word;width:10%"><input type="checkbox"  id="ruleCheck'+ruleId+'" name="action-check" value="'+ruleId+'" checked /></td><td style="display:inline-block;border: 1px;word-wrap: break-word;width:90%">'+ruleName+'<br>'+ruleIdDisplay+'</td></tr></table> ';	   
				}
			}
	    	});
	    	if(toMakeActivetoArchive=='true'){
	    		jQuery("#confirmationValidationError").html(archiveErrorMessage);
	    		jQuery("#confirmationValidationError").show();
	    	}
	    	if(toMakeConflictActive=='true'){
	    		jQuery("#confirmationValidationError").html(activeConflictMessage);
	    		jQuery("#confirmationValidationError").show();
	    	}
	    	if(toMakeErrorActive=='true'){
	    		jQuery("#confirmationValidationError").append("<p>");
	    		jQuery("#confirmationValidationError").append(errorStatusMessage);
	    		jQuery("#confirmationValidationError").show();
	    	}
	    		jQuery("#ruleList").html(ruleValue);
	    	if(miminimumCount=='true' && toMakeActivetoArchive=='true'){
	    		jQuery("#ruleWarningSearch").html(archiveOneErrorMessage);
	    		jQuery("#ruleWarningSearch").show();
	    		jQuery('#SuccessMessage').hide();
	    		selectionRuleAction();
	    	}
	    	if(miminimumCount=='true' && toMakeConflictActive=='true'){
	    		$.getJSON("rrJson_persistErrors.action", {ruleId:',',comment:'Conflicted'}, function (data) {
		    		jQuery("#ruleWarningSearch").html(activeOneConflictMessage);
			    	jQuery("#ruleWarningSearch").show();
			    	jQuery('#SuccessMessage').hide();
			    	selectionRuleAction();
			    	jQuery('#mainTableforSearch').html('<tr><td><img src="${staticContextPath}/images/loading.gif" width="800px"/></td></tr>');
					jQuery("#searchresults").load("routingRuleSearch_display.action?archive=1", function(){	
		           		 jQuery('#selectedItemSearch').show();
		           		 jQuery('#SuccessMessage > .successSize').html(jQuery('#listSize1').val());
						 jQuery('#SuccessMessage').show();
						 jQuery("#ruleWarningSearch").html(activeOneConflictMessage);
			    		 jQuery("#ruleWarningSearch").show();
			    		 jQuery('#SuccessMessage').hide();
		            		if(archive==0){
		            			jQuery("#showArchiveRules").prop("checked",false);
		            		}		            		
		           		 }); 
		    		});
		    }
	    	if(miminimumCount=='true' && toMakeErrorActive=='true'){
	    		jQuery("#ruleWarningSearch").append(errorStatusMessage);
	    		jQuery("#ruleWarningSearch").show();
	    		jQuery('#SuccessMessage').hide();
	    		selectionRuleAction();
	    	}
	    	if(miminimumCount=='true' && (toMakeActivetoArchive=='true' || toMakeConflictActive=='true' || toMakeErrorActive=='true')){
	    	
	    	}else{
				jQuery("#confirmDialog").jqmShow();
				window.scrollTo(0,0);
				setCursorWait(false);
				}
	    	});
	   }
	   
		/*this function is used for action corresponding to drop down 'select action'
			*/
			
		jQuery("#selectedItemSearch").change(function() {
		
		if(jQuery('#selectedItemSearch > option:selected').val() == -1){
	    }
	    else{
	    	setCursorWait(true);
	    	if(jQuery('#selectedItemSearch > option:selected').val() == 1)
	    	{
	    	jQuery("#selectRuleActionSearch").val('inactive');
	    	jQuery("#confirmationHeading").html("Are you sure you want to make the following rule(s) inactive? <br> Uncheck any that you want to remain active."); 
	   		selectState();
	   		}
	    	if(jQuery('#selectedItemSearch > option:selected').val() == 2)
	    	{
	    	jQuery("#selectRuleActionSearch").val('active');
	       	jQuery("#confirmationHeading").html("Are you sure you want to make the following rule(s) active? <br> Uncheck any that you want to remain inactive."); 
	    	selectState();
	    	}
	    	if(jQuery('#selectedItemSearch > option:selected').val() == 3)
	    	{
	    	jQuery("#selectRuleActionSearch").val('archived');
	  		jQuery("#confirmationHeading").html("Are you sure you want to archive this rule(s)? <br><br> Once a rule is archived, it cannot be activated or updated.<br>Uncheck any that you want to remain in the current status."); 
	    	 selectState();
	    	}
	    }
		});
		
		jQuery(".errorClickSearch").click(function (e) {		
		
			jQuery(".ruleCommentViewSearch").hide();
			jQuery(".errorCommentSearch").hide();
			var x = e.pageX;
			var y = e.pageY;
			jQuery("#ruleErrorTextViewSearch").css("top", y + 10);
	        jQuery("#ruleErrorTextViewSearch").css("left", x + 1000);
			jQuery("#ruleErrorTextViewSearch").css("z-index",999999999);
			var routingid= jQuery(this).prop("id");
			var rrid=routingid.replace('ruleSearch','');
			jQuery("#"+rrid+"ErrorViewSearch").html('<img src="${staticContextPath}/images/loading.gif" width="572px" height="160px"');
			jQuery("#"+rrid+"ErrorViewSearch").load("rrManageTab_displayError.action?id="+rrid);
			jQuery("#"+rrid+"ErrorViewSearch").show();
		});
		
		
		jQuery(".commentsClickRuleSearch").click(function (e) {	
		
			jQuery(".errorCommentSearch").hide();
			jQuery(".ruleCommentViewSearch").hide();
			var x = e.pageX;
			var y = e.pageY;
		    jQuery("#ruleCommentsViewSearch").css("top", y + 250);
            jQuery("#ruleCommentsViewSearch").css("left", x + 10);
			jQuery("#ruleCommentsViewSearch").css("z-index",999999999);
			
			var routingid= jQuery(this).prop("id");
			jQuery("#"+routingid+"ruleCommentsViewSearch").html('<center><img src="${staticContextPath}/images/loading.gif" width="500px" height="160px"</center>');
			jQuery("#"+routingid+"ruleCommentsViewSearch").load("rrManageTab_displayQuickView.action?id="+routingid,function(){
				var autoAcceptStat='${autoAcceptStatusBuyer}';
				if(autoAcceptStat=='true')
				{
				jQuery(".accpetHistoryDiv").load("rrManageTab_displayAutoAcceptHistoryForBuyer.action?id="+routingid);
				}
			});
			jQuery("#"+routingid+"ruleCommentsViewSearch").show();
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
		
	
	jQuery(".searchPageIndex").click(function (e) {		
		jQuery("#mainTableforSearch").html('<tr><td><img src="' + staticContextPath + '/images/loading.gif" width="800px"/></td></tr>');
		jQuery("#searchresults").load("routingRuleSearch_display.action?pageNo=" + jQuery(this).prop("id")+ "&archive=" +archiveInd, function()
            {
            	if(archive==0)
            	{
            	jQuery("#showArchiveRules").prop("checked",false);
            	}
            	jQuery('#selectedItemSearch').show();
            	if(alertClick==5)
            	{
				jQuery('#name1').html("");
	        	jQuery('#contact1').html("");
	        	jQuery('#status1').html("");
	        	}            	
	        	if(alertClick!=1 && alertClick!=2 && alertClick!=4 )
            	{
					jQuery('#name1').html("");
		        	jQuery('#contact1').html("");
		        	jQuery('#status1').html("");
	        	}
	        	            	
            });
		
	});

	jQuery(".sortColumn").click(function (e) {			
			jQuery('#mainTableforSearch').html('<tr><td><img src="${staticContextPath}/images/loading.gif" width="800px"/></td></tr>');
			var newSortCol = jQuery(this).prop('id').substring(7,6);
			var newSortOrd = null;
			if (newSortCol != curSearchSortCol) { // If sort column changes, reset sort order to ascending
				newSortOrd = 0;
			} else {
				newSortOrd = 1 - curSearchSortOrd; // toggle between ascending and descending order
			}
			switch(newSortCol){
				case '1':sortColumn='name1';
						break;
				case '2':sortColumn='contact1';
						break;
				case '4':sortColumn='status1';
						break;
				case '7':sortColumn='autoStatus1';
						break;
			}	
				
			_sortDir = (newSortOrd == 0) ? 'down' : 'up';
			imgSrc1 ='<img src="'+staticContextPath+'/images/grid/arrow-'+_sortDir+'-white.gif"/>';
							
			jQuery('#searchresults').load("routingRuleSearch_display.action?pageNo=1&sortCol=" + newSortCol + "&sortOrd=" + newSortOrd + "&archive=" +archiveInd, function()
            {	
            	if(archive==0)
            	{
            	jQuery("#showArchiveRules").prop("checked",false);
            	}
            	jQuery('#selectedItemSearch').show();
            });
			curSearchSortCol = newSortCol;
			curSearchSortOrd = newSortOrd;
			searchCount++;
			
		});
		
		jQuery('#pageNoTextTopSearch').keypress(function (e){
  			//if the letter is not digit then display error and don't type anything
  			if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57))
  			{
   				return false;
 			}
		});
	
		jQuery('#pageNoTextBottomSearch').keypress(function (e){
  			//if the letter is not digit then display error and don't type anything
  			
  			if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57))
  			{
   				return false;
 			}
		});
		
		jQuery(".goToPageTopSearch").click(function (e) {		
		
			var totalPages=jQuery("#searchRuleTotPages").val();
	 		var pageNo = jQuery('#pageNoTextTopSearch').val();
	 		if(pageNo == null || pageNo == "")
	 	 	{
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
	   	
	   		jQuery('#mainTableforSearch').html('<tr><td><img src="${staticContextPath}/images/loading.gif" width="800px"/></td></tr>');
	   		jQuery("#searchresults").load("routingRuleSearch_display.action?pageNo=" + pageNo+ "&archive=" +archiveInd, function()
	            {	
	            	if(archive==0)
	            	{
	            	jQuery("#showArchiveRules").prop("checked",false);
	            	}
	            	jQuery('#selectedItemSearch').show();
	            	if(alertClick==5)
	            	{
						jQuery('#name1').html("");
			        	jQuery('#contact1').html("");
			        	jQuery('#status1').html("");
		        	}
		        	if(alertClick!=1 && alertClick!=2 && alertClick!=4)
	            	{
						jQuery('#name1').html("");
			        	jQuery('#contact1').html("");
			        	jQuery('#status1').html("");
		        	}
	            });
  		});
  
		
  		jQuery(".goToPageBottomSearch").click(function (e) {
			var totalPages=jQuery("#searchRuleTotPages").val();
	 		var pageNo = jQuery('#pageNoTextBottomSearch').val();
	 	 	if(pageNo == null || pageNo == "")
	 	 	{
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
		   
		   jQuery('#mainTableforSearch').html('<tr><td><img src="${staticContextPath}/images/loading.gif" width="800px"/></td></tr>');
		   jQuery("#searchresults").load("routingRuleSearch_display.action?pageNo=" + pageNo+ "&archive=" +archiveInd, function()
		            {	
		            	if(archive==0)
		            	{
		            	jQuery("#showArchiveRules").prop("checked",false);
		            	}
		            	jQuery('#selectedItemSearch').show();
		            	
		            	if(alertClick==5)
		            	{
							jQuery('#name1').html("");
				        	jQuery('#contact1').html("");
				        	jQuery('#status1').html("");
			        	} 
			        	if(alertClick!=1 && alertClick!=2 && alertClick!=4)
		            	{
							jQuery('#name1').html("");
				        	jQuery('#contact1').html("");
				        	jQuery('#status1').html("");
			        	} 
		            });          		
  		});
  
		
		jQuery('#sortAlertColumn').click(function(){
			jQuery('#mainTableforSearch').html('<tr><td><img src="${staticContextPath}/images/loading.gif" width="800px"/></td></tr>');
			var newSortCol = '5';			
			var newSortOrd = '0';
								
			jQuery('#searchresults').load("routingRuleSearch_display.action?pageNo=1&sortCol=" + newSortCol + "&sortOrd=" + newSortOrd+ "&archive=" +archiveInd, function()
            {	
            	if(archive==0)
            	{
            	jQuery("#showArchiveRules").prop("checked",false);
            	}
            	jQuery('#selectedItemSearch').show();
            	jQuery('#name1').html("");
	        	jQuery('#contact1').html("");
	        	jQuery('#status1').html("");	
            });
            
		});
		
			jQuery("#confirmDialog").jqm({modal:true}); 
	 	// Show modal on deactivate only
	 	jQuery(document).on('click', ".activateCheckSearch", function(){
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
		
		jQuery("#modifiedDateSortSearch").click(function() {
		    searchCount = 0;
		    jQuery("#mainTableforSearch").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
            jQuery("#searchresults").load("routingRuleSearch_display.action?pageNo=1&sortCol=0&sortOrd=0" + "&archive=" +archiveInd, function()
            {	
            	if(archive==0)
            	{
            	jQuery("#showArchiveRules").prop("checked",false);
            	}
            	jQuery('#selectedItemSearch').show(); 
            });
            
        });
		
		var msgString1 = jQuery('#msgString1').val();
		if(msgString1=='Empty'||msgString1=='')
		{
	       jQuery('#SuccessBox').hide();
	    }
	    else
	    {
	       jQuery('#SuccessBox').show();
	    	
	    }
	    
	    jQuery("#showArchiveRules").click(function() 
        {
        	if(this.checked)
        	{
	        	archiveInd = 1;
	        	searchCount = 0;
	        	jQuery('#name1').html("");
	        	jQuery('#contact1').html("");
	        	jQuery('#status1').html("");
			    jQuery("#mainTableforSearch").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
	            jQuery("#searchresults").load("routingRuleSearch_display.action?archive="+archiveInd, function()
	            {
	            	var successTextRes = jQuery('#SuccessMessage > .successText').html();	
	             	jQuery("#showArchiveRules").prop("checked",true);
	             	jQuery('#SuccessMessage > .successSize').html(jQuery('#listSize1').val());
	             	if(successTextRes == null || successTextRes == '')
	                {
	                	successTextRes = ' "'+jQuery('#searchCriteriaVar').val()+'"';	                	
	                	jQuery('#SuccessMessage > .successText').html(successTextRes);
	             	}
					jQuery('#SuccessMessage').show();
					jQuery('#selectedItemSearch').show();
				});            
        	}
        	
        	else
        	{	
        	    archiveInd = 0;
        	    searchCount = 0;
        	    jQuery('#name1').html("");
	        	jQuery('#contact1').html("");
	        	jQuery('#status1').html("");
			    jQuery("#mainTableforSearch").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
	            jQuery("#searchresults").load("routingRuleSearch_display.action?archive="+archiveInd, function()
	            {	
	             	jQuery("#showArchiveRules").prop("checked",false);
	             	if(jQuery('#listSize1').val()==0)
	             	{
	             		var successTextRes = jQuery('#SuccessMessage > .successText').html();
	             		jQuery('#ZeroResultsError > .successText').html(successTextRes);
	             		jQuery('#ZeroResultsError').show();
	    				jQuery('#SuccessMessage').hide();
	             	}
	             	else
	             	{
	             	var successTextRes = jQuery('#SuccessMessage > .successText').html();	
	             	jQuery('#SuccessMessage > .successSize').html(jQuery('#listSize1').val());
	             	if(successTextRes == null || successTextRes == '')
	                {
	                	successTextRes = ' "'+jQuery('#searchCriteriaVar').val()+'"';	                	
	                	jQuery('#SuccessMessage > .successText').html(successTextRes);
	             	}
					jQuery('#SuccessMessage').show();
					}
					jQuery('#selectedItemSearch').show();
	            });             
            }  
        });
        jQuery('#SearchValidationError').hide();  
	});
</script>

<div id="searchresults">

	<div id="ruleWarningSearch" class="warningBox" style="display: none;"></div>

	<input type="hidden" id="msgString1" name="msgString1"
		value="${msgString1}" />
	<input type="hidden" id="searchCriteriaVar" name="searchCriteriaVar"
		value="${routingRuleSearchCriteria.nullSearchCriteria}" />	
	<input type="hidden" id="alertClick" name="alertClick"
		value="${searchRulePagination.sortCol}" />	
	<c:if test="${fn:length(searchResultList) > 0}">
	
		<div id="page" align="right" style="padding-top: 7px; padding-right: 6px">		
		<c:if test="${searchRulePagination.totalPages>0}">
				<c:if test="${searchRulePagination.currentIndex>1}">
					<a class="searchPageIndex paginationLink" id="1"
						style="text-decoration: underline; color: #00A0D2;"> << </a>
					<a class="searchPageIndex paginationLink"
						id="${searchRulePagination.currentIndex-1}" style="color: #00A0D2">
						<b><</b> </a>
				</c:if>
		<input type="hidden" id="searchRuleTotPages" value="${searchRulePagination.totalPages}"/>
		<span style="font-size: 13px"> Page ${searchRulePagination.currentIndex} of ${searchRulePagination.totalPages}</span>
  			<c:if
			test="${searchRulePagination.currentIndex!=searchRulePagination.totalPages}">
				<a class="searchPageIndex paginationLink"
					id="${searchRulePagination.currentIndex+1}" style="color: #00A0D2"><b>></b>
				</a>
				<a class="searchPageIndex paginationLink"
					id="${searchRulePagination.totalPages}"
					style="text-decoration: underline; color: #00A0D2;">>></a>
			</c:if>
		<c:if
			test="${searchRulePagination.totalPages>1}">	
		<span style="font-size: 13px"> To Page </span> <input type="text" id="pageNoTextTopSearch" size="3"/> <input type="button" class="goToPageTopSearch" value="GO"/>			
		</c:if>
	</c:if>
		
		<div align="left" style="padding-bottom: 5px">
			<table style="border: none">
				<tr style="border: none">
					<td style="border: none;">
						<input type="checkbox" value="unchecked"   id="showArchiveRules"
							style="padding-right: 5px;"  />
						<b>Show archived rules </b>
					</td>
					<td style="border: none; text-align: right; font-size: 12px">
						<c:if test="${searchRulePagination.totalPages>0}"> 
						${searchRulePagination.pageComment} rules.
						</c:if>
					</td>
				</tr>
			</table>
		</div>						
	</div>
	
	<div id="searchSortd">		
		<input type="hidden" id="selectRuleActionSearch">
		<c:if test="${searchRulePagination.totalPages>0}">
			<span id="modifiedDateSortSearch">
			<a class="paginationLink" style="color: #00A0D2; float: left; padding-top: 5px">
			Re-sort by Most Recent
			</a>
			</span>
		</c:if>
			
		<span>
			<c:if test="${!isReadOnly}">
				<select id="selectedItemSearch" style="float: right;">
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
	
	</c:if>
			
	<table id="mainTableforSearch" border="0" cellpadding="0"
		cellspacing="0" style="padding-top: 6px">

		<c:if test="${fn:length(searchResultList) > 0}">

			<thead>

				<th id="column0" class="sortColumn" style="display: none;">
					<span>moddate</span>
				</th>

				<th id="column1" class="sortColumn">
					<span style="margin-left: 15px">Name </span><span id="name1"></span>
					<span id="sortAlertColumn" style="margin-left: 35px">Sort<img
					 style="padding: 0px 4px 0px 5px; position: relative; top: 2px;"
					 src="${staticContextPath}/images/icons/conditional_exclamation.gif" />Alerts to Top</span>						
				</th>
				<th id="column2" class="sortColumn"
					style="background-position: 60px 11px;">
					<span>Provider Firm </span><span id="contact1"></span>
				</th>
				<c:if test="${autoAcceptStatus==true }">
					<th  id="column7"  class="sortColumn" style="text-align: center;width:15%;">
						<span>Auto Accept Status</span><span id="autoStatus1"></span>
					</th>
					</c:if>
				<th id="column3" style="text-align: center">
					<span>Action</span>
				</th>
				<th id="column4" class="sortColumn" style="text-align: center">
					<span>Status </span><span id="status1"></span>
				</th>
			<c:if test="${!isReadOnly}">
			<th id="selectColumnSearch" class="selectColumn" style="text-align: center">
						<select id="selectItemSearch" style="float: right;">
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
			 <span id="statusSearch"></span>
			</th>
			</c:if>
			</thead>
			<tbody>
				<c:forEach items="${searchResultList}" var="rule" varStatus="status">
					<tr id="rowid${status.count}" class="dashboardRow" 
					style=<c:if test="${rule.ruleStatus == 'ARCHIVED'}">"background-color: #E7E7E7;"</c:if>>
						<td style="display: none;">
							${rule.modifiedDate}
						</td>
						<td>
							<table style="border:0px;table-layout: fixed;font-size: 11px; font-weight: normal;">
							<tr style="border:0px;">
								<td style="border:0px;width:5%;vertical-align: top">
									<img alt="Rule Quickview" style="cursor: pointer;" title="Rule Quickview" id="${rule.routingRuleHdrId}"
									src="/ServiceLiveWebUtil/images/s_icons/magnifier.png"
									class="commentsClickRuleSearch"/>
									<div id="${rule.routingRuleHdrId}ruleCommentsViewSearch" class="ruleCommentViewSearch" style="display:none; z-index:999999999; width:866px; height:160px; overflow:auto; border: solid 1px #ccc; background: #fff;  position:absolute; padding:5px;">
									</div>
								</td>
								<td style="border:0px; width:95%">
									<div style="border:0px; word-wrap: break-word;">
									${rule.ruleName}						
									</div>
									ID#&nbsp;${rule.routingRuleHdrId}
									<c:if test="${!empty rule.fileNames}">
										<div id="fileNameCheck" style="border:0px; word-wrap: break-word;">
											Filename(s):&nbsp; 
											<c:forEach items="${rule.fileNames}" var="fileName" varStatus="countFile">
								  				${fileName}
												<c:if test="${countFile.count< fn:length(rule.fileNames)}">	
													,&nbsp;	
												</c:if>
											</c:forEach>
										</div>
									</c:if>
								</td>
							</tr>	
							<c:forEach items="${rule.routingRuleAlerts}" var="alert">
								<c:if test="${rule.ruleStatus != 'ARCHIVED'}">
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
								</c:if>
							</c:forEach>
							<c:if test="${rule.ruleStatus != 'ARCHIVED'}">
							<c:if test="${not empty rule.routingRuleError}">
								<tr style="border:0px;">
									<td style="border:0px;vertical-align: top;">
										<img src="${staticContextPath}/images/icons/conditional_exclamation.gif">
									</td>
									<td style="border:0px;vertical-align: top;">
										<b>ALERT:
										<c:out value="Rule Criteria Conflict(s). Details" /></b>
										<a href="" title="" id="${rule.routingRuleHdrId}ruleSearch" style="text-decoration:none; font-weight:bold; color:#00A0D2;"
										class="errorClickSearch" onclick="return false;">(+)</a>
										
									</td>
									<script>('#rowid${status.count}');</script>																				
								</tr>	
							</c:if>
							</c:if>							
						</table>
						<div id="${rule.routingRuleHdrId}ErrorViewSearch" class="errorCommentSearch" style="display:none; z-index:999999999; margin-left:300px; width:572px; height:160px; overflow:auto; border: solid 1px #ccc; background: #fff;  position:absolute; padding:5px;">
						</div>
						</td>
						<td>	
							<c:set value="${fn:length(rule.routingRuleVendor)}" var="vendorFirmSize"></c:set>
								<c:forEach  items="${rule.routingRuleVendor}" var="routeVendor">
								<c:set value="${routeVendor.vendor}" var="vendorVar"></c:set><div style="padding-top:5px;padding-bottom:5px;">
							<a  style="color: #00A0D2;" href="javascript:void(0);" onclick="javascript:openProviderFirmProfile(${vendorVar.id})" ><b>${vendorVar.businessName}(${vendorVar.id})</b></a>
							</div>
									<c:if test="${vendorFirmSize >1}"><hr class="bold"></hr>
									<c:set var="vendorFirmSize" value="${vendorFirmSize-1 }"></c:set>
									</c:if>
								</c:forEach>
						</td>
						<c:if test="${autoAcceptStatus==true}">
							<td style="text-align:center;">
							
							<c:set value="${fn:length(rule.routingRuleVendor)}" var="vendorFirmSize"></c:set>
							
								<c:forEach  items="${rule.routingRuleVendor}" var="routeVendor">
								<div style="padding-top:5px;padding-bottom:5px;">
								<c:choose><c:when test="${not empty routeVendor.autoAcceptStatus}">
								<b>${routeVendor.autoAcceptStatus}
								</b>
								</c:when>
								<c:otherwise>-</c:otherwise>
								</c:choose>
								</div>
									<c:if test="${vendorFirmSize >1}"><hr class="bold"></hr>
									<c:set var="vendorFirmSize" value="${vendorFirmSize-1 }"></c:set>
									</c:if>
								</c:forEach>
							
							</td></c:if>
						<td class="column3">
							<tags:security actionName="routingRulesAction_view">
							<c:choose>
							<c:when test="${rule.ruleStatus == 'ARCHIVED'}">
                            	<a class="bold" style="display: none;"> </a>
                            </c:when>
                            <c:otherwise>
                            	<a class="bold" 
									href="rrCreateRuleAction_view.action?rid=${rule.routingRuleHdrId}&tabType=searchTab">View</a>
                            </c:otherwise>
                            </c:choose>
								
							</tags:security>
							<tags:security actionName="routingRulesAction_edit">
							<c:choose>
							<c:when test="${rule.ruleStatus == 'ARCHIVED'}">
                            <a class="bold" style="display: none;"> </a>
                            </c:when>
							<c:otherwise>
							<tags:security actionName="routingRulesAction_view">
							<b> |</b>
							</tags:security>
                            <a class="bold" 
									href="rrCreateRuleAction_edit.action?rid=${rule.routingRuleHdrId}&tabType=searchTab">Edit</a>
                            </c:otherwise>
                            </c:choose>
									
							</tags:security>
						</td>
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
									class="column4 ${ruleStatus}" style=<c:if test="${rule.ruleStatus == 'ARCHIVED'}">"background-color: #E7E7E7"</c:if> >
									${rule.ruleStatus}
								</td>
							</c:when>
							<c:otherwise>
								<td id="searchCol4rule${rule.routingRuleHdrId}"
									class="column4 ${ruleStatus}" style=<c:if test="${rule.ruleStatus == 'ARCHIVED'}">"background-color: #E7E7E7"</c:if> >
									<!--<input   type="checkbox" disabled="disabled" 
										name="search-activate-check-${rule.routingRuleHdrId}"
										id="search-activate-check-${rule.routingRuleHdrId}"
										class="activateCheckSearch" value="${rule.routingRuleHdrId}"    ${checked} />  -->
									<br />
									<label for="search-activate-check-${rule.routingRuleHdrId}">
										<c:choose>
								<c:when test="${rule.ruleStatus == 'ACTIVE'}">
									Active
								</c:when>
								<c:when test="${rule.ruleStatus == 'INACTIVE'}">
									Inactive
								</c:when>
								<c:otherwise>
									Archived
								</c:otherwise>
							</c:choose>
									</label>
								</td>
								<td id="searchcol5rule${rule.routingRuleHdrId}" style="padding-left: 40px">
								<c:if test="${rule.ruleStatus != 'ARCHIVED'}">
										<input type="checkbox" 
											name="activate-checkSearch"
											id="activate-checkedSearch-${rule.routingRuleHdrId}"
											class="activateCheckedSearch${rule.ruleStatus}" value="${rule.routingRuleHdrId}"  />
											</c:if>
										<br />
									</td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</tbody>
		</c:if>
	</table>
	<c:if test="${fn:length(searchResultList) > 0}">
		<div id="page" align="right" style="padding-top: 5px;">
			<c:if test="${searchRulePagination.totalPages>0}">
				
				<c:if test="${searchRulePagination.currentIndex>1}">
					<a class="searchPageIndex paginationLink" id="1"
						style="text-decoration: underline; color: #00A0D2;"><<</a>
					<a class="searchPageIndex paginationLink" 
						id="${searchRulePagination.currentIndex-1}" style="color: #00A0D2">
						<b><</b> </a>
				</c:if>
				<span style="font-size: 13px">Page ${searchRulePagination.currentIndex} of ${searchRulePagination.totalPages}</span>
    			<c:if
					test="${searchRulePagination.currentIndex!=searchRulePagination.totalPages}">
					<a class="searchPageIndex paginationLink"
						id="${searchRulePagination.currentIndex+1}" style="color: #00A0D2"><b>></b>
					</a>
					<a class="searchPageIndex paginationLink"
						id="${searchRulePagination.totalPages}"
						style="text-decoration: underline; color: #00A0D2;">>></a>
				</c:if>
				<c:if
				test="${searchRulePagination.totalPages>1}">
				<span style="font-size: 13px"> To Page </span> <input type="text" id="pageNoTextBottomSearch" size="3"/> <input type="button" class="goToPageBottomSearch" value="GO"/>
				</c:if>
				<div style="padding-top: 4px; font-size: 12px; padding-right: 5px ">
					${searchRulePagination.pageComment} rules.
				</div>
			</c:if>
		</div>
	</c:if>

	<input type="hidden" id="listSize1" name="listSize"
		value="${searchRulePagination.totalRecords}" />
		
	<input type="hidden" id="searchArchiveInd" value="${archiveInd}">
	<c:if test="${fn:length(searchResultList) > 0}">
		<input type="hidden" id="pageNumberSearch" value="${searchRulePagination.currentIndex}">
	</c:if>
	<c:if test="${fn:length(searchResultList) == 0}">
		<input type="hidden" id="pageNumberSearch" value="1">
	</c:if>
</div>
