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
<!--<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />-->
<c:set var="alertActive" value="<%=RoutingRulesConstants.ROUTING_ALERT_STATUS_ACTIVE%>" />

<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/routingrules.css" />
<link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery-ui-1.7.2.custom.css" type="text/css"></link>
<link rel="stylesheet" href="${staticContextPath}/javascript/plugins/tablesorter/blue/style.css" type="text/css" media="print, projection, screen" />
<script type="text/javascript">
	jQuery(document).ready(function($){
	
	$('#searchSubmit').click(function(){
	
	 submitSearch();
	});
	$("input").keypress(function(e) {
		if ((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)) {
			
			submitSearch();
		}
	});
	

	function submitSearch() {
	
		$('#SuccessMessage').hide();
		var searchType = $('#SearchBy').val();
		var queryString="";
		var text="";
			if(searchType==7)
			{
				text= $('#searchAutoStatus').find(":selected").text();
			}
			else
			{
			var textId = '#SearchText';
			var textValue = $(textId).val();
			 text = textValue.replace(/ /g,'~'); 
			}
		 queryString+= "value=" + text	+ "&" + "column="+searchType;
		   
		var msgSelectCtgry = "Please select a search category.";
		var msgMinimumLength = "Please enter at least 3 characters.";
		var msgMaximumLength = "Please enter less than 20 characters.";
		var msgIsNumer = "Please enter numerical IDs only";
		
		
	    if($('#SearchBy > option:selected').val() == -1){

	    $('#SearchValidationError > .errorText').html(msgSelectCtgry);
		$('#SearchValidationError').show();
					
	    }
	    else
	    {
	    
	    $('#SearchValidationError').hide();
	    var textId = '#SearchText'; 
		var textValue = $(textId).val();
		var length=textValue.length;
	   if(length<3)
		{
	
		$('#SearchValidationError > .errorText').html(msgMinimumLength);
		$('#SearchValidationError').show();
		
		}
		else if(length>20)
		{
		
		$('#SearchValidationError > .errorText').html(msgMaximumLength);
		$('#SearchValidationError').show();
		}
		else
		{
		
		if($('#SearchBy > option:selected').val() == 1)
		{
	       
	    var searchType = $('#SearchBy').val();
		var textId = '#SearchText'; 
		var textValue = $(textId).val();
	
		if(isNaN(textValue))
		{
		$('#SearchValidationError > .errorText').html(msgIsNumer);
		$('#SearchValidationError').show();
		}
		else
		{
		$('#mainTable1').html('<img src="${staticContextPath}/images/loading.gif" width="800px"/>');
        $('#searchresults').load('routingRuleSearch_display.action?'+queryString,function(){
        
	   var listsize = $('#listSize1').val();
	   
	    var searchBy="";
	   var searchType = $('#SearchBy').val();
		var text = '#SearchText';
		var textIdValue = $(text).val();
		
	    if(searchType == 1) {
	    searchBy+=" Provider Firm ID";
	    }
	    else if(searchType == 2)
	    {
	    searchBy+=" Provider Firm Name";
	    }
	    else if(searchType == 3)
	    {
	    searchBy+=" RuleName";
	    }
	    else
	    	{
	    	 searchBy+=" Auto Acceptance";
	    	}
	    var searchFound="Your search found ";
	    searchFound+=listsize+" results matching"+" '' "+searchBy+": "+textIdValue+" '' ";
	   
	     $('#SuccessMessage > .successText').html(searchFound);
		$('#SuccessMessage').show();
		});
		}			
	    }
	    else
	    {
	    $('#mainTable1').html('<img src="${staticContextPath}/images/loading.gif" width="800px"/>');
	    $('#searchresults').load('routingRuleSearch_display.action?'+queryString,function(){
	    var listsize = $('#listSize1').val();
	   
	    var searchBy="";
	   var searchType = $('#SearchBy').val();
		var text = '#SearchText';
		var textIdValue = $(text).val();
		
	    if(searchType == 1) {
	    searchBy+=" Provider Firm ID";
	    }
	    else if(searchType == 2)
	    {
	    searchBy+=" Provider Firm Name";
	    }
	    else
	    {
	    searchBy+=" RuleName"; 
	    }
	    var searchFound="Your search found ";
	    searchFound+=listsize+" results matching"+" '' "+searchBy+": "+textIdValue+" '' ";
	   
	     $('#SuccessMessage > .successText').html(searchFound);
		$('#SuccessMessage').show();
	    }); 

	    }
	    
		}
		
	 }
	  
	    
	   
	  }
	});		
	  				
		</script>

	<div id="searchRules"> 
	<div id="routing rules">
		
 
			<div id="tab-content" class="clearfix">
				<h4>Search Rules <br></h4>
				<h6> 
					Search rules by Firm ID,&nbsp;Firm Name or RuleName.&nbsp;Rules are displayed in order of the most recently created or modified.&nbsp; 
				</h6>
				<input type="hidden" id="searchByViewAll" value="false" />
				<br>
			<div id="SearchValidationError" class="error errorMsg errorMessage">
				<p class="errorText"></p>
			</div>
			<div id="SuccessMessage" class="successMsg">
				<p class="successText"></p>
			</div>
			<br>
				<div id="SearchForm" class="clearfix">
					<label for="SearchBy">
						Search  
					</label>
					<select id="SearchBy">
						<option value="-1">
							- Select One -
						</option>
						<option value="1">
							Provider Firm ID
						</option>
						<option value="2">
							Provider Firm Name
						</option>
						<option value="3">
							Rule Name 
						</option>
						 
					</select>
					
						<input type="text" id="SearchText"  />
						
					
					<input id="searchSubmit" type="submit" value="SEARCH"  /> 
					<br />
					<br> 
				</div>
				

 
			  <jsp:include page="/jsp/routingrules/searchrules_results.jsp"></jsp:include>


			</div> 
		</div>
		
		   
		 
	</div> 
