<%@ page language="java" import="java.util.*, com.servicelive.routingrulesengine.RoutingRulesConstants" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="alertActive" value="<%=RoutingRulesConstants.ROUTING_ALERT_STATUS_ACTIVE%>" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
    	<title>ServiceLive - Conditional Auto Routing</title>
		<tiles:insertDefinition name="blueprint.base.meta"/>


  <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/routingrules.css" />
  <link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery-ui.custom.min.css" type="text/css"></link>
  <link rel="stylesheet" href="${staticContextPath}/javascript/plugins/tablesorter/blue/style.css" type="text/css" media="print, projection, screen" />
  <script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
  <script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqueryui/jquery-ui-1.10.4.custom.min.js"></script>
  <script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqmodal/jqModal.js"></script>
  <script type="text/javascript" src="${staticContextPath}/scripts/plugins/superfish.js"></script>
  <script type="text/javascript" src="${staticContextPath}/scripts/plugins/supersubs.js"></script>
  <script type="text/javascript" src="${staticContextPath}/javascript/plugins/jquery.tablesorter.min.js"></script>
  <script type="text/javascript" src="${staticContextPath}/javascript/plugins/ajaxfileupload.js"></script>
  <script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	 .ie7 .bannerDiv{margin-left:-1150px;} 
	 </style> 
        <script type="text/javascript">
        	var staticContextPath="${staticContextPath}";
        	var searchCount=0;
        	var count=0;
        	var histCount=0;
			var sortColumn1;
			var sortColumn;
        	var _sortDir = 'down';
			var imgSrc ='<img src="'+staticContextPath+'/images/grid/arrow-'+_sortDir+'-white.gif"/>';
			var _jqm_ruleId = null;
			var _jqm_newState = null;
			var _jqm_comment = null;
			var filesCount=0;
			var sortColumnClass;
			var searchTabRendered = false;
			var selectRuleAction = '';
		</script>
  <script type="text/javascript" src="${staticContextPath}/javascript/routingrules-dashboard.js"></script>
    <%-- Determine CAR authorization --%>
        <c:set var="isReadOnly" value="true" scope="page" />
        <tags:security actionName="routingRulesAction_edit">
            <c:set var="isReadOnly" value="false" scope="page" />
        </tags:security>
    </head>
    <body>
	<div id="wrap" class="container">
		<tiles:insertDefinition name="blueprint.base.header"/>
		<tiles:insertDefinition name="blueprint.base.navigation"/>
			<div id="content" class="span-24 clearfix">		
        
        <div id="wrapper">
            <h1>Administrator Office</h1>
            <h2>Conditional Routing</h2>
<br style="clear:both"/>
            <div id="content">
                <div id="carTabs">
                    <ul>
                        <li>
                            <a href="#tab_manageRules">Manage Rules</a>
                        </li>
                         <li>
       						 <a href="routingRulesUploadTabAction_display.action?pageLoad=true" style="cursor: pointer">Upload Rules</a>
						 </li>
                        <li>
                            <a href="#searchRules">Search Rules</a>
                        </li>
                        <li>
                            <a href="#tab_rulesHistory">Rules History</a>
                        </li>
                    </ul>
                   
                    <jsp:include page="/jsp/routingrules/tab_manageRules.jsp"></jsp:include>
					<jsp:include page="/jsp/routingrules/tab_searchRules.jsp"></jsp:include>
					<jsp:include page="/jsp/routingrules/tab_rulesHistory.jsp"></jsp:include>  
					
					<input type="hidden" id="tabToDisplay" name="tabToDisplay" value="${tabToDisplay}" />
                </div>
            </div>
            <script type="text/javascript">
               var tabToDisplay = jQuery('#tabToDisplay').val();
               if(tabToDisplay == 'searchRules'){
	              jQuery('#carTabs').tabs({ active : 2 });
	              searchTabRendered = true;
	           }else{         
	              jQuery('#carTabs').tabs();
	           }
	           jQuery( "#carTabs" ).tabs( "option", "cache", false ); 
            </script>
            <script type="text/javascript">
            function selectionAction()
			{
	    		jQuery('#selectedItem').val('-1');
	    		jQuery('#selectedItemSearch').val('-1');
			}
			
			function selectionsRuleAction()
	{
	   jQuery('#selectedItem').val('-1'); 
	   jQuery(".successBox").hide();
	   setCursorWait(false);
	}
	
	function confirm(){
		jQuery('#actionRule').prop("disabled", true);
    	var tabSelected=jQuery('#carTabs').tabs('option','active');
    	_jqm_comment= jQuery("#ruleComment").get(0).value;
    	
    	// Make sure that comment is entered
    	_jqm_comment=jQuery.trim(_jqm_comment);
		if (_jqm_comment === null || _jqm_comment === "") {
        	jQuery("#ruleComment").focus();
        	jQuery('#actionRule').prop("disabled", false);
           	return;
        }
        var checksDom= jQuery('input[name="action-check"]');
		var checks= checksDom.serializeArray();
		var value='';
		$.each (checks, function (i, check) {
	    	value+=check.value+",";
	    });
	    _jqm_ruleId = value;
	    
	    if(value=='' && tabSelected==0){
	    	// When you reach here, remember there are no rules to activate
	    	setCursorWait(true);
	    	selectRuleAction=jQuery("#selectRuleAction").val();
	    	_jqm_newState=selectRuleAction;
	    	if(_jqm_newState=='active'){
        	 	$.getJSON("rrJson_persistErrors.action", {ruleId:',', comment:_jqm_comment}, function (data) {
        	 	setCursorWait(false);
        		jQuery("#mainTable").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
           	    jQuery("#manageRules").load("rrManageTab_display.action?pageNo=1&sortCol=0&sortOrd=0", function(){
            		jQuery('#name').html("");
		        	jQuery('#contact').html("");
		        	jQuery('#status').html("");
            	}); 
        	});
        	//$.getJSON("rrJson_setRuleStatus.action", {ruleId:_jqm_ruleId, newState:_jqm_newState, comment:_jqm_comment}, function (data) {});
        	}
	        selectionsRuleAction();
	    	hideModal();
	    	return false;
	    }else if(value=='' && tabSelected == 2){
	    	// When you reach here, remember there are no rules to activate
	    	setCursorWait(true);
	    	selectRuleAction = jQuery("#selectRuleActionSearch").val();
	    	_jqm_newState=selectRuleAction;
	    	if(_jqm_newState=='active'){
        	 	$.getJSON("rrJson_persistErrors.action", {ruleId:',', comment:_jqm_comment}, function (data) {
        	 	setCursorWait(false);
        		jQuery('#mainTableforSearch').html('<tr><td><img src="${staticContextPath}/images/loading.gif" width="800px"/></td></tr>');
				jQuery("#searchresults").load("routingRuleSearch_display.action?archive="+archiveInd, function(){	
           		 jQuery('#selectedItemSearch').show();
           		// jQuery('#SuccessMessage > .successSize').html(jQuery('#listSize1').val());
				// jQuery('#SuccessMessage').show();
				// No need to show the success message
				//jQuery('#SuccessMessage').html('');
				jQuery('#SuccessMessage').hide();
            	if(archive==0){
            		jQuery("#showArchiveRules").prop("checked",false);
            	}
            	jQuery('#name1').html("");
	        	jQuery('#contact1').html("");
	        	jQuery('#status1').html("");	
           		});
        	});
        	// $.getJSON("rrJson_setRuleStatus.action", {ruleId:_jqm_ruleId, newState:_jqm_newState, comment:_jqm_comment}, function (data) {});
        	 }
	    	 jQuery('#selectedItemSearch').val('-1');
	    	 setCursorWait(false);
	    	 hideModal();
	    	 return false;
	    	}
	    	
        	
        	if(tabSelected==0){
			 	setCursorWait(true);	
     		 	selectRuleAction=jQuery("#selectRuleAction").val();
     		}
     		
     		if(tabSelected==2){
				setCursorWait(true);	
				selectRuleAction = jQuery("#selectRuleActionSearch").val();
			}
        	_jqm_newState=selectRuleAction;
        	if(_jqm_newState=='active'){
        		$.getJSON("rrJson_persistErrors.action", {ruleId:_jqm_ruleId, comment:_jqm_comment}, function (data) {
        			$.getJSON("rrJson_setRuleStatus.action", {ruleId:_jqm_ruleId, newState:_jqm_newState, comment:_jqm_comment}, function (data) {			
				setCursorWait(false);
				jQuery("#actionRule").disabled = false;
				hideModal();
				if(tabSelected==0){
					jQuery("#mainTable").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
            		jQuery("#manageRules").load("rrManageTab_display.action?pageNo=1&sortCol=0&sortOrd=0", function(){
            			setCursorWait(false);
            			jQuery('#name').html("");
		        		jQuery('#contact').html("");
		        		jQuery('#status').html("");
            		}); 
				}
				if(tabSelected==2){
					jQuery('#mainTableforSearch').html('<tr><td><img src="${staticContextPath}/images/loading.gif" width="800px"/></td></tr>');
					jQuery("#searchresults").load("routingRuleSearch_display.action?archive=" +archiveInd, function(){
           		 	setCursorWait(false);
           		 	// if(jQuery('#listSize1').val()==0)
             	 	// {
             	 	//	var successText = jQuery('#SuccessMessage > .successText').html();
             	 	// jQuery('#ZeroResultsError > .successText').html(successText);
             	 	//	jQuery('#ZeroResultsError').show();
    			 	//	jQuery('#SuccessMessage').hide();
             	 	// }
             	 	// else
             	 	// {
           		 	// jQuery('#SuccessMessage > .successSize').html(jQuery('#listSize1').val());
				 	// jQuery('#SuccessMessage').show();
				 	// }
				 	// No need to show the success message
				 	//jQuery('#SuccessMessage').html('');
					 jQuery('#SuccessMessage').hide();
				 	jQuery('#selectedItemSearch').show();
            		if(archive==0){
            			jQuery("#showArchiveRules").prop("checked",false);
            		}
            	 	jQuery('#name1').html("");
	        	 	jQuery('#contact1').html("");
	        	 	jQuery('#status1').html("");	
           		 });  
			   }		
			});
        		
        		});
        	}else{
			$.getJSON("rrJson_setRuleStatus.action", {ruleId:_jqm_ruleId, newState:_jqm_newState, comment:_jqm_comment}, function (data) {			
				setCursorWait(false);
				jQuery("#actionRule").disabled = false;
				hideModal();
				if(tabSelected==0){
					jQuery("#mainTable").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
            		jQuery("#manageRules").load("rrManageTab_display.action?pageNo=1&sortCol=0&sortOrd=0", function(){
            			setCursorWait(false);
            			jQuery('#name').html("");
		        		jQuery('#contact').html("");
		        		jQuery('#status').html("");
            		}); 
				}
				if(tabSelected==2){
					jQuery('#mainTableforSearch').html('<tr><td><img src="${staticContextPath}/images/loading.gif" width="800px"/></td></tr>');
					jQuery("#searchresults").load("routingRuleSearch_display.action?archive=" +archiveInd, function(){
           		 	setCursorWait(false);
           		 	// if(jQuery('#listSize1').val()==0)
             	 	// {
             	 	//	var successText = jQuery('#SuccessMessage > .successText').html();
             	 	// jQuery('#ZeroResultsError > .successText').html(successText);
             	 	//	jQuery('#ZeroResultsError').show();
    			 	//	jQuery('#SuccessMessage').hide();
             	 	// }
             	 	// else
             	 	// {
           		 	// jQuery('#SuccessMessage > .successSize').html(jQuery('#listSize1').val());
				 	// jQuery('#SuccessMessage').show();
				 	// }
				 	// No need to show the success message
				 	//jQuery('#SuccessMessage').html('');
					 jQuery('#SuccessMessage').hide();
				 	jQuery('#selectedItemSearch').show();
            		if(archive==0){
            			jQuery("#showArchiveRules").prop("checked",false);
            		}
            	 	jQuery('#name1').html("");
	        	 	jQuery('#contact1').html("");
	        	 	jQuery('#status1').html("");	
           		 });  
			   }		
			});
			}
		}
	
	
               </script>
            <div id="confirmDialog" class="jqmWindow" style="left: 58%; top: 30%;background-color: white; height:auto;">
                <div class="dialogHeader">
                    <img id="modalClose-img" onclick="selectionAction();" src="${staticContextPath}/images/icons/modalCloseX.gif" />
                    <h3>Confirmation</h3>
                </div>
                <form id="dialogForm">
			<div id="confirmationValidationError" class="warningBox"></div>
			<p class="errorText"></p>
                   <div id="confirmationHeading">  </div>
                   <br>
                
	             <div  id="ruleList" style="border: 1px solid;height:75px; margin-left: 40px; overflow:auto;overflow-x: hidden;  width:260px;"> </div>
	                 
	  				  <p>(
                        <span class="req">*</span>
					required)</p>
                    <label for="ruleComment" class="left bold">Comments 
                        <span class="req"><b style="font-size: small; font-weight:  100">*</b></span>
                    </label>
                    <br></br>
                  <textarea name="ruleComment" id="ruleComment" rows="2" cols="80" style=" margin-left: 3px;resize:none;"></textarea>
                    <div class="dialogFooter">
                        <div style="float:left;padding-top:10px;">
                            <a href="" onclick="selectionAction(); cancelModal(); return false;" class="cancelLink">Cancel</a>
                        </div>
                        <input type="button" id="actionRule" class="button action right" value="Confirm" onclick="confirm();"/><br>
                       </div>
                </form>
            </div>
            
            <!-- SL-20363 Need to Add UI for Forceful CAR Activation for buyer# 3000 -->
            			<div id="confirmDialogforForceActive" class="jqmWindow"
			style="width: 580px; left: 50%; top: 30%; background-color: white;">
			<div class="dialogHeader">
				<img src="${staticContextPath}/images/icons/modalCloseX.gif"
					onclick="return hideConfirmBox();" />
				<h4 style="color: white;">
					Confirm Activation
				</h4>
			</div>
			<div id="dialogContent" style="height: 130px; width: 540px;">
				<form method="post" action="rrCreateRuleActionForceActive.action">
					<input type="hidden" name="CarRuleId" id="CarRuleId" />
					<input type="hidden" name="CarRuleName" id="CarRuleName" />
					<p>
						Are you sure you want to force Rule ID#
						<span id="selectedRuleId" name="selectedRuleId"></span> active without a
						conflict check ?
					</p>
					<P></P>
					<div>
						<span style="color: red" id="errorMessageforRuleId"></span>
					</div>
					<label for="ruleComment" class="left bold">
						Comments
						<span class="req">*</span>
					</label>
					<textarea name="ruleComment" id="forceRuleComment"
						style="height: 80px; width: 350px; resize: none;"></textarea>
					<div class="dialogFooter">
						<div style="float: left; padding-top: 8px; padding-bottom: 3px;">

							<input type="button" value="Cancel" class="cancelLink"
								onclick="return hideConfirmBox();" />
						</div>
						<div style="float: right; padding-top: 8px; padding-bottom: 3px;">
							<input type="submit"
								onclick="return validateForceActiveComment();"
								class="cancelLink" value="Confirm">
							</input>
						</div>

					</div>
				</form>
			</div>
		</div>
		<!-- End for Forceful CAR Activation  -->
            
        </div>
       </div>
       <tiles:insertDefinition name="blueprint.base.footer"/>
      </div>
    </body>
</html>

