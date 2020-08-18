<%@ page language="java"
	import="java.util.*, com.servicelive.routingrulesengine.RoutingRulesConstants"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<c:set var="alertActive" value="<%=RoutingRulesConstants.ROUTING_ALERT_STATUS_ACTIVE%>" />

<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/routingrules.css" />
<link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery-ui.custom.min.css" type="text/css"></link>
<link rel="stylesheet" href="${staticContextPath}/javascript/plugins/tablesorter/blue/style.css" type="text/css" media="print, projection, screen" />

<script type="text/javascript">
	
	jQuery(document).ready(function($){
			jQuery('#minShow').hide();
			jQuery('#ZeroResultsError').hide();
			jQuery('#SuccessBox').hide();
			jQuery('#ruleWarningSearch').hide();
			jQuery('#searchSubmit').click(function()
			{
				submitSearch();
			});
			
			
				jQuery("input").keypress(function(e) {
					if ((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)) {
						var targetIs = jQuery(e.target).prop('id');
						if("searchSubmit"!=targetIs && targetIs == "SearchText"){
							submitSearch();
						}
					}
				});
			
			
			function submitSearch() 
			{
				jQuery('#SuccessMessage').hide();
				jQuery('#ZeroResultsError').hide();
				jQuery('#ruleWarningSearch').hide();
				 
				var searchType = jQuery('#SearchBy').val();
				var textId = '#SearchText';
				var textValue = jQuery(textId).val();
				textValue=jQuery.trim(textValue);
				var queryString="";
				var text = escape(textValue);
				var length=textValue.length;
				var archive = 1;	
				
				var msgSelectCtgry = "Please select a search category.";
				var msgMinimumLength = "Please enter at least 3 characters.";
				var msgMinimumLengthRuleId = "Please enter at least 1 character for rule id.";
				var msgMaximumLength = "Please enter less than 100 characters.";
				var msgIsNumer = "Please enter valid Provider Firm ID";
				var msgCustRefLength = "Please enter less than 30 characters.";
				var msgMaximum = "Please enter up to 10 digits only";
				var msgInvalidId = "Please enter a valid Id";
				var msgMin = "Please enter atleast one digit";  
				var msgMinChar = "Please enter atleast one character"; 
				var msgvalidFileName = "Please enter a valid file name";    
				
			    if(jQuery('#SearchBy > option:selected').val() == -1){
			   		jQuery('#SearchValidationError > .errorText').html(msgSelectCtgry);
					jQuery('#SearchValidationError').show();
			    }
			    
			    else{
			    
			   		jQuery('#SearchValidationError').hide();
					if(length>100)
					{
						jQuery('#SearchValidationError > .errorText').html(msgMaximumLength);   
						jQuery('#SearchValidationError').show();
						return;
					}
		
					if(jQuery('#SearchBy > option:selected').val() == 5)
					{ // Search for Rule Id, Firm Id
						if(!textValue.match(new RegExp("^[0-9]+$")))
						{ // Allow only digits
							jQuery('#SearchValidationError > .errorText').html(msgInvalidId);
							jQuery('#SearchValidationError').show();
							return;
						}
						else if(length>10){ 
							jQuery('#SearchValidationError > .errorText').html(msgMaximum);
							jQuery('#SearchValidationError').show();
							return;
						}
						else if(length<1){ 
							jQuery('#SearchValidationError > .errorText').html(msgMin);
							jQuery('#SearchValidationError').show();
							return;
						}	
					}
					
					if(jQuery('#SearchBy > option:selected').val() == 1)
					{ // Search for Rule Id, Firm Id
						if(!textValue.match(new RegExp("^[0-9]+$")))
						{ // Allow only digits
							jQuery('#SearchValidationError > .errorText').html(msgInvalidId);
							jQuery('#SearchValidationError').show();
							return;
						}
						else if(length>10){ 
							jQuery('#SearchValidationError > .errorText').html(msgMaximum);
							jQuery('#SearchValidationError').show();
							return;
						}
						// else if(length<3)
						// {
				  		// jQuery('#SearchValidationError > .errorText').html(msgMinimumLength);
						// jQuery('#SearchValidationError').show();
						// return;
						// }	
					}
					
					if((jQuery('#SearchBy > option:selected').val() == 2) || (jQuery('#SearchBy > option:selected').val() == 3)) 
					{ // Search for Rule Name, Provider Firm Name, Upload File Name
						if(length<3)
					{
			  			jQuery('#SearchValidationError > .errorText').html(msgMinimumLength);
						jQuery('#SearchValidationError').show();
						return;
					}			
					}
					
					if(jQuery('#SearchBy > option:selected').val() == 6) 
					{ // Search for Process Id
						if(length<1)
					{
			  			jQuery('#SearchValidationError > .errorText').html(msgMin);
						jQuery('#SearchValidationError').show();
						return;
					}
					else if(length>30)
					{
						jQuery('#SearchValidationError > .errorText').html(msgCustRefLength);   
						jQuery('#SearchValidationError').show();
						return;
					}			
					}
					
					if(jQuery('#SearchBy > option:selected').val() == 4) { 
						var uploadName = text;		
						var dotInd=uploadName.indexOf(".");
						var substrInd=text.substring(dotInd,uploadName.length);
					
						if(substrInd==".xls" || substrInd==".xlsx" ){
							uploadName=uploadName.substring(0,dotInd);
						}
						text=uploadName;
						
						// Search for Uploadfile Name
						if(length<1){
			  				jQuery('#SearchValidationError > .errorText').html(msgMinChar);
							jQuery('#SearchValidationError').show();
							return;
						}else if(uploadName=="" || uploadName.length == 0){
							jQuery('#SearchValidationError > .errorText').html(msgvalidFileName);
							jQuery('#SearchValidationError').show();
							return;
						}			
					}	
					//If user selected auto accept to search
					if(jQuery('#SearchBy > option:selected').val() == 7) {
						var autoAcceptSearchOption=jQuery('#searchAutoStatus option:selected').text();
						text=autoAcceptSearchOption;
					}
					// If all validations are successful, then create the query string
					queryString += "value=" + text + "&column=" + searchType + "&archive=" + archive;
					
					jQuery('#mainTableforSearch').html('<tr><td><img src="${staticContextPath}/images/loading.gif" width="800px"/></td></tr>');
		     		jQuery('#searchresults').load('routingRuleSearch_display.action?'+queryString,function()
		     		{
		       			var listsize = jQuery('#listSize1').val();
			   			var searchBy="";
			   			var searchType = jQuery('#SearchBy').val();
						var text = '#SearchText';
						var textIdValue = jQuery(text).val();
			    		if(searchType == 1) 
			    		{
			   				searchBy+="Provider Firm ID";
			    		}
			   			else if(searchType == 2)
			   			{
			   				searchBy+="Provider Firm Name";
			   			}
			   			else if(searchType == 3){
			   				searchBy+="Rule Name";
			   			}
			   			else if(searchType == 4)
			   			{
			   				searchBy+="Uploaded File Name";
			   			}
			   			else if (searchType == 5)
			   			{
			   			
			 				searchBy+="Rule Id"; 
			 			}
			 			else if (searchType == 6)
			 			{
			 			
			 				searchBy+="Process Id";
			 			}	
			 			else if (searchType == 7)
			 			{
			 				searchBy+="Auto Accept Status ";
			 				var autoAcceptSearchOption=jQuery('#searchAutoStatus option:selected').text();
			 				textIdValue=autoAcceptSearchOption;
			 			}
			    		
			    		if(listsize==0){
			   				var searchFound="";
			    			searchFound+=" ''"+searchBy+" : "+textIdValue+"'' ";
			     			jQuery('#ZeroResultsError > .successText').html(searchFound);
			    			jQuery('#ZeroResultsError').show();
			    			jQuery('#SearchText').val('');
			    			var SearchBy = document.getElementById('SearchBy');
			    			SearchBy.options[0].selected = true;		
			    			jQuery('#minShow').hide();
			    		} 
			  			else{
			  				var searchFound="";
			   				searchFound+=" ''"+searchBy+": "+textIdValue+"''";
			   				listsize+=" ";
			     			jQuery('#SuccessMessage > .successSize').html(listsize);
			     			jQuery('#SuccessMessage > .successText').html(searchFound);
							jQuery('#SuccessMessage').show();
							jQuery('#selectedItemSearch').show();
							jQuery('#SearchText').val('');
							var SearchBy = document.getElementById('SearchBy');
							SearchBy.options[0].selected = true;	
							jQuery('#minShow').hide();     
						}
						jQuery('#name1').html("");
			        	jQuery('#contact1').html("");
			        	jQuery('#status1').html("");
			        	searchTabRendered = true;
					}
					);
				}
			}
			
			
			jQuery("#carTabs > ul > li:eq(0)> a").click(function()
			{
				jQuery("#mainTable").html('<tr><td><img src="' + staticContextPath + '/images/loading.gif" width="800px"/></td></tr>');
			    jQuery("#manageRules").load("rrManageTab_display.action");
			});     
			
			jQuery("#carTabs > ul > li:eq(2)> a").click(function()
			{	
				jQuery('#ZeroResultsError').hide();
		    	jQuery('#SuccessMessage').hide();
		    	jQuery('#SearchText').val('');
				var SearchBy = document.getElementById('SearchBy');
				SearchBy.options[0].selected = true;
				if(searchTabRendered == true)
				{
					var archiveInd = jQuery('#searchArchiveInd').val();
					var pageNo = jQuery('#pageNumberSearch').val();
					jQuery("#mainTableforSearch").html('<tr><td><img src="' + staticContextPath + '/images/loading.gif" width="800px"/></td></tr>');
			   		jQuery("#searchresults").load("routingRuleSearch_display.action?archive=" +archiveInd+"&pageNo="+pageNo,function()
			   		{
			   			
			   			if(jQuery('#listSize1').val()==0)
		             	{
		             		var successTextRes = jQuery('#ZeroResultsError > .successText').html();
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
			   		});
			   	}
			});
		});		
	  	function fnSearchBy()
	  	{
	  		var searchType = jQuery('#SearchBy').val();
			if ((searchType == 2) || (searchType == 3)) 
			{
				jQuery('#minShow').show();
				var normalSearchOtion='<input type="text" id="SearchText" style="min-width: 300px;"  maxlength="101" />';
		  		jQuery("#searchAutoStatus").replaceWith(normalSearchOtion);
			}
			else
			if(searchType==7)
				{
				jQuery('#minShow').hide();
				var autoAcceptSearchOption='<select id="searchAutoStatus"  style=""><option value="1">ON</option><option value="2">OFF'+
				'</option><option value="3">PENDING</option></select>';
				jQuery("#SearchText").replaceWith(autoAcceptSearchOption);
				
				}
			else
				{
				jQuery('#minShow').hide();	
				var normalSearchOtion='<input type="text" id="SearchText" style="min-width: 300px;"  maxlength="101" />';
		  		jQuery("#searchAutoStatus").replaceWith(normalSearchOtion);
				}
		}	
	  	
		</script>

	<div id="searchRules"> 
	<div id="routing rules">
		
 
			<div id="tab-content" class="clearfix">
				<h3 style="padding-bottom: 7px;">Search Rules</h3>
				<b style="font-size: 10px;"> 
					Search rules by Provider, Firm, Rule, Process or Uploaded File. Rules are displayed in order of the most recently created or modified. 
				</b>
				<br>
				<input type="hidden" id="searchByViewAll" value="false" />
				<br>
			<div id="SearchValidationError" class="error errorMsg errorMessage">
				<p class="errorText"></p>
			</div>
			
			<div id="SuccessBox" class="successBox">
			   ${msgString1}
	        </div>
			<div id="SuccessMessage" class="successMsg" style="word-wrap: break-word;">
				Your search found&nbsp;<b class="successSize"></b>&nbsp;results matching<b class="successText"></b>.
			</div>
			<div id="ZeroResultsError" class="warningBox" style="font-weight:normal;">
			Your search for<b class="successText"></b>&nbsp;found  <b>0</b> results. Check your information and try again.
		</div>  
			<br>    
				<div id="SearchForm" class="clearfix" style="padding-bottom: 2px">
					<label for="SearchBy">
						Search  
					</label>
					<select id="SearchBy" onchange="fnSearchBy();">
						<option value="-1">
							--Select One--
						</option>
						<option value="1">
							Provider Firm ID
						</option>
						<option value="2">
							Provider Firm Name
						</option>
						<option value="6">
							Process ID
						</option>
						<option value="5">
							Rule ID  
						</option>
						<option value="3">
							Rule Name 
						</option>
						<option value="4">
							Uploaded File Name  
						</option>
						<c:if test="${autoAcceptStatus==true }">
						<option value="7">
							Auto Acceptance
						</option>	
						</c:if>
					</select>
					
						<input type="text" id="SearchText" style="min-width: 300px;"  maxlength="101" />
						<input id="searchSubmit" type="submit" value="SEARCH"/> 
					<br />
					<p id="minShow" style="padding-left: 214px; font-style: italic; font-size: x-small; "> 
						<b>Minimum 3 characters</b>
					</p> 
				</div>
				

 
			  <jsp:include page="/jsp/routingrules/searchrules_results.jsp"></jsp:include>


			</div> 
		</div>
		
		   
		 
	</div> 
