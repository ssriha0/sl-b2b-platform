<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ page import="org.owasp.esapi.ESAPI"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<%
  String varTab = request.getParameter("displayTab");
  String varTabNew=ESAPI.encoder().canonicalize(varTab);
  String vulnTab = ESAPI.encoder().encodeForHTML(varTabNew);
%>
<c:set var="role" value="${roleType}" />
<c:set var="displayTab" scope="page" value="" />
<c:set var="defaultTab" scope="page" value="Saved" />
<c:set var="tab" scope="request" value="<%=vulnTab%>" />
<c:set var="RECEIVED" value="Received" />
<c:set var="pageTitle" scope="request" value="Workflow Monitor" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/so_details.css"/>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
	   	<link href="${staticContextPath}/javascript/confirm.css"
	                        rel="stylesheet" type="text/css" />
	
	<script type="text/javascript"
		src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
	<script type="text/javascript"
		src="${staticContextPath}/javascript/jquery/jqmodal/jqModal.js"></script>
	<script type="text/javascript"
		src="${staticContextPath}/javascript/creditCardValidation.js"></script>
	
	<script type="text/javascript">
		
		function replaceAll(txt){
			for(i=0; i<3;++i){
				txt = txt.replace(',', '');
			}
			return txt;
		}
		
		
		function buyerReportProblem(tab)
		{
		document.getElementById('action'+tab).value="buyerReportProblem";
		submitPendingCancel(tab);
		}
		
		function providerReportProblem(tab)
		{
		var soId=document.getElementById('serviceOrderId'+tab).value;
		window.location.href="soDetailsController.action?soId="+soId+"&displayTab="+tab+"#ui-tabs-9";
		}
		
		function buyerWithdraw(tab)
		{
			var maxSpendLimitPerSO  = ${SecurityContext.maxSpendLimitPerSO};		
			var buyerPrice=document.getElementById('buyerPrice'+tab).innerHTML;
			buyerPrice= replaceAll(buyerPrice);
			buyerPrice = parseFloat(buyerPrice, 10);
	        var buyerPvsAmount= document.getElementById('buyerPrvsAmount'+tab).value;
	        buyerPvsAmount = replaceAll(buyerPvsAmount);
	        buyerPvsAmount = parseFloat(buyerPvsAmount, 10);
	        var err=false;
	        var errMsg="";
	        if(buyerPvsAmount > buyerPrice){
	        	var increasedAmount=buyerPvsAmount - buyerPrice;			
				if(maxSpendLimitPerSO>0 && increasedAmount>maxSpendLimitPerSO)
				{		
					errMsg=errMsg+"The total maximum price exceeds the amount your profile allows.";
					document.getElementById('errorMessageW'+tab).innerHTML=errMsg;
					document.getElementById('errorMessageW'+tab).style.display='block';
					err=true;
				}
				var ach=${SecurityContext.autoACH};
				var balance=document.getElementById('balance'+tab).value;
				var soLevelACH=document.getElementById('soLevelAutoACH'+tab).value;
				balance = replaceAll(balance);
				if(soLevelACH=='false' && balance!='' && increasedAmount>balance)
				{
					if(err)
					{
						errMsg=errMsg+"\n Your wallet does not have enough funding to cover this new combined maximum.";
						document.getElementById('errorMessageW'+tab).innerHTML=errMsg;
						document.getElementById('errorMessageW'+tab).style.display='block';
						err=true;
					}
					else
					{
					errMsg=errMsg+"Your wallet does not have enough funding to cover this new combined maximum.";
						document.getElementById('errorMessageW'+tab).innerHTML=errMsg;
						document.getElementById('errorMessageW'+tab).style.display='block';
						err=true;
					
					}
				
				}
	        }
			if(!err)
			{
				document.getElementById('action'+tab).value="buyerWithdraw";
				submitPendingCancel(tab);
			}
		
		}
		
		function buyerAgree(tab)
		{
		
		var maxSpendLimitPerSO  = ${SecurityContext.maxSpendLimitPerSO};		
		var buyerPrice=document.getElementById('buyerPrice'+tab).innerHTML;
		buyerPrice = replaceAll(buyerPrice);
        var buyerPricelastRequest=document.getElementById('buyerPricelastRequest'+tab).innerHTML;
        buyerPricelastRequest = replaceAll(buyerPricelastRequest);
		var increasedAmount=buyerPrice-buyerPricelastRequest;	
		var err=false;	
		var errMsg="";	
		if(maxSpendLimitPerSO>0 && increasedAmount>maxSpendLimitPerSO)
		{
			
			errMsg=errMsg+"The total maximum price exceeds the amount your profile allows.";
			document.getElementById('errorMsge'+tab).innerHTML=errMsg;
			document.getElementById('errorMsge'+tab).style.display='block';
			err=true;
		}
		var ach=${SecurityContext.autoACH};
		var balance=document.getElementById('balance'+tab).value;
		var soLevelACH=document.getElementById('soLevelAutoACH'+tab).value;
		balance = replaceAll(balance);
		if(soLevelACH=='false' && balance!='' && increasedAmount>balance)
		{
		if(err)
		{
		errMsg=errMsg+"\n Your wallet does not have enough funding to cover this new combined maximum.";
			document.getElementById('errorMsge'+tab).innerHTML=errMsg;
			document.getElementById('errorMsge'+tab).style.display='block';
			err=true;
		}
		else
		{
		errMsg=errMsg+"Your wallet does not have enough funding to cover this new combined maximum.";
			document.getElementById('errorMsge'+tab).innerHTML=errMsg;
			document.getElementById('errorMsge'+tab).style.display='block';
			err=true;
		
		}
		
		}
		if(!err)
		{
		document.getElementById('action'+tab).value="buyerAgree";
		document.getElementById('cancelInd').value= "agreedToCancel";
		
		submitPendingCancel(tab);
		}
		
		}
		
		
		
		
		function buyerDisagree(tab)
		{
		document.getElementById('action'+tab).value="buyerDisagree";
		var validation=calculateMaxSpendLimit(tab);
		
		if(validation)
		{
		submitPendingCancel(tab);
		}
		}
		function providerWithdraw(tab)
		{
		document.getElementById('action'+tab).value="providerWithdraw";
		submitPendingCancel(tab);
		}
		function providerAgree(tab)
		{
		document.getElementById('action'+tab).value="providerAgree";
		document.getElementById('cancelInd').value= "agreedToCancel";
		submitPendingCancel(tab);
		}
		function providerDisagree(tab)
		{
		document.getElementById('action'+tab).value="providerDisagree";
		var validation=calculateAmount(tab);
		
		if(validation)
		{
		submitPendingCancel(tab);
		}
		}
		function setCancellationAmount(id)
		{
		var value=$(id).val();
		$(".cancellationAmount").val(value);
		}
		function setCancelAmt(id)
		{
		var value=$(id).val();
		$(".cancelAmt").val(value);
		}
		function setCancelComment(id)
		{
		var value=$(id).val();
		$(".cancelComment").val(value);
		}
		
		function calculateMaxSpendLimit(tab)
		{
		
		//var errorMessage='#errorMessage'+tab;
		//alert(errorMessage);
		//$(errorMessage).html("");
		document.getElementById('errorMessage'+tab).innerHTML="";
		var wholeNumberValue=document.getElementById('cancellationAmount'+tab).value;
		wholeNumberValue = replaceAll(wholeNumberValue);
		var decimalValue=document.getElementById('cancelAmt'+tab).value;
		var error=false; 
	    var comment=document.getElementById('cancelComment'+tab).value;
		comment=jQuery.trim(comment);
		comment = escape(comment);
		var length=comment.length;
		var errMsg="";
		
		if(comment!='')
		{
		if(length>600)
		{
		//$("#errorMessage"+tab).append("Field validation error occurred.");
		//$("#errorMessage"+tab).show();
		errMsg=errMsg+"Comment length should not be greater than 600 characters.";
		document.getElementById('errorMessage'+tab).innerHTML=errMsg;
		document.getElementById('errorMessage'+tab).style.display='block';
		
		error=true;
		}		
		}
		else
		{
		errMsg=errMsg+"Please fill out all required fields.";
		document.getElementById('errorMessage'+tab).innerHTML=errMsg;
		document.getElementById('errorMessage'+tab).style.display='block';
		error=true;
		}		
	
		
		if(wholeNumberValue!='' )
		{
			if(decimalValue ==''){
				 decimalValue = 00;
			}
			var decimallength=decimalValue.length;
			if(decimallength==1)
			{
				decimalValue=decimalValue * 10;
			}
			
			if(wholeNumberValue<1 && decimalValue<1)
			{
			if(error)
			{
			errMsg=errMsg+"\n Please enter a price greater than $0.00.";
			document.getElementById('errorMessage'+tab).innerHTML=errMsg;
			document.getElementById('errorMessage'+tab).style.display='block';
			error=true;
			}
			else
			{
			errMsg=errMsg+"Please enter a price greater than $0.00.";
			document.getElementById('errorMessage'+tab).innerHTML=errMsg;
			document.getElementById('errorMessage'+tab).style.display='block';
			error=true;
			}
			}
		var cancelAmount= (1 * wholeNumberValue)+ (0.01 * decimalValue)
		document.getElementById('cancelAmount'+tab).value=cancelAmount;
		var buyerPricelastRequest=document.getElementById('buyerPricelastRequest'+tab).innerHTML;
		buyerPricelastRequest = replaceAll(buyerPricelastRequest);
		var increasedAmount=cancelAmount-buyerPricelastRequest;
		
		var maxSpendLimitPerSO  = ${SecurityContext.maxSpendLimitPerSO};		
		
		if(maxSpendLimitPerSO>0 && increasedAmount>maxSpendLimitPerSO)
		{
			if(error)
			{
			errMsg=errMsg+"\n The total maximum price exceeds the amount your profile allows.";
			document.getElementById('errorMessage'+tab).innerHTML=errMsg;
			document.getElementById('errorMessage'+tab).style.display='block';
			error=true;
			}
			else
			{
			errMsg=errMsg+"The total maximum price exceeds the amount your profile allows.";
			document.getElementById('errorMessage'+tab).innerHTML=errMsg;
			document.getElementById('errorMessage'+tab).style.display='block';
			error=true;
			}	
		}
		var ach=${SecurityContext.autoACH};
		var soLevelACH=document.getElementById('soLevelAutoACH'+tab).value;
		var balance=document.getElementById('balance'+tab).value;
		balance = replaceAll(balance);
		if(soLevelACH=='false' && balance!='' && increasedAmount>balance)
		{
		if(error)
			{
			errMsg=errMsg+"\n  Your wallet does not have enough funding to cover this new combined maximum.";
			document.getElementById('errorMessage'+tab).innerHTML=errMsg;
			document.getElementById('errorMessage'+tab).style.display='block';
			error=true;
			}
			else
			{
			errMsg=errMsg+" Your wallet does not have enough funding to cover this new combined maximum.";
			document.getElementById('errorMessage'+tab).innerHTML=errMsg;
			document.getElementById('errorMessage'+tab).style.display='block';
			error=true;
			}	
		}
		}
		else
		{
		if(error)
		{
		errMsg=errMsg+"\nInvalid Input amount. Please use the format $0.00.";
		document.getElementById('errorMessage'+tab).innerHTML=errMsg;
		document.getElementById('errorMessage'+tab).style.display='block';
		
		}
		else
		{
		errMsg=errMsg+"Invalid Input amount. Please use the format $0.00.";
		document.getElementById('errorMessage'+tab).innerHTML=errMsg;
		document.getElementById('errorMessage'+tab).style.display='block';
		}
		return false;
		}
		if(error)
		{
		return false;
		}
		
		return true;
		}
		
		
		
		function calculateAmount(tab)
		{
		
		//var errorMessage='#errorMessage'+tab;
		//alert(errorMessage);
		//$(errorMessage).html("");
		document.getElementById('errorMessage'+tab).innerHTML="";
		var wholeNumberValue=document.getElementById('cancellationAmount'+tab).value;
		var decimalValue=document.getElementById('cancelAmt'+tab).value;
		var error=false; 
	    var comment=document.getElementById('cancelComment'+tab).value;
		comment=jQuery.trim(comment);
		comment = escape(comment);
		var length=comment.length;
		var errMsg="";
		
		if(comment!='')
		{
		if(length>600)
		{
		//$("#errorMessage"+tab).append("Field validation error occurred.");
		//$("#errorMessage"+tab).show();
		errMsg=errMsg+"Comment length should not be greater than 600 characters.";
		document.getElementById('errorMessage'+tab).innerHTML=errMsg;
		document.getElementById('errorMessage'+tab).style.display='block';
		
		error=true;
		}		
		}
		else
		{
		errMsg=errMsg+"Please fill out all required fields.";
		document.getElementById('errorMessage'+tab).innerHTML=errMsg;
		document.getElementById('errorMessage'+tab).style.display='block';
		error=true;
		}		
	
		
		if(wholeNumberValue!='' )
		{
			if(decimalValue ==''){
				 decimalValue = 00;
			}
			var decimallength=decimalValue.length;
			if(decimallength==1)
			{
				decimalValue=decimalValue * 10;
			}
			
			if(wholeNumberValue<1 && decimalValue<1)
			{
			if(error)
			{
			errMsg=errMsg+"\n Please enter a price greater than $0.00.";
			document.getElementById('errorMessage'+tab).innerHTML=errMsg;
			document.getElementById('errorMessage'+tab).style.display='block';
			error=true;
			}
			else
			{
			errMsg=errMsg+"Please enter a price greater than $0.00.";
			document.getElementById('errorMessage'+tab).innerHTML=errMsg;
			document.getElementById('errorMessage'+tab).style.display='block';
			error=true;
			}
			}
		var cancelAmount= (1 * wholeNumberValue)+ (0.01 * decimalValue)
		document.getElementById('cancelAmount'+tab).value=cancelAmount;
		
		}
		else
		{
		if(error)
		{
		errMsg=errMsg+"\nInvalid Input amount. Please use the format $0.00.";
		document.getElementById('errorMessage'+tab).innerHTML=errMsg;
		document.getElementById('errorMessage'+tab).style.display='block';
		
		}
		else
		{
		errMsg=errMsg+"Invalid Input amount. Please use the format $0.00.";
		document.getElementById('errorMessage'+tab).innerHTML=errMsg;
		document.getElementById('errorMessage'+tab).style.display='block';
		}
		return false;
		}
		if(error)
		{
		return false;
		}
		
		return true;
		}
		
		function showHistory(e, tab)
		{
		var soId=document.getElementById('serviceOrderId'+tab).value;
		jQuery("#"+"history"+tab).load("serviceOrderPendingCancelHistory_display.action?servicOrderId="+soId);
		
		/* var evt = e ? e:window.event; 
		var x = 0;
		var y = 0;
		
		x=evt.screenX;
		var width = screen.width;
		if(x >= (width - 240)){
		   x= width - 250;
		}	
		if (evt.pageY) y= evt.clientY;
		else if (evt.clientY)
   		y  = evt.clientY + (document.documentElement.scrolTop ? document.documentElement.scrollTop : document.body.scrollTop);
   		
      	jQuery("#"+"history"+tab).css("top",y-5);
      	jQuery("#"+"history"+tab).css("left",x-5); */
		
		jQuery("#"+"history"+tab).show();
		
		} 
		function hideHistory(tab)
		{
		jQuery("#"+"history"+tab).hide();
		}
		
		function showHistoryDiv(tab)
		{
		jQuery("#"+"history"+tab).show();
		
		}
		
		function showWidget(tab)
		{
		jQuery("#"+"pendingCancelExpand"+tab).hide();
		jQuery("#"+"pendingCancelCollapse"+tab).show();
		jQuery("#"+"frmPendingCancel"+tab).show();
		
		}
		function hideWidget(tab)
		{
		jQuery("#"+"pendingCancelExpand"+tab).show();
		jQuery("#"+"pendingCancelCollapse"+tab).hide();
		jQuery("#"+"frmPendingCancel"+tab).hide();
		
		}
		
		function showProviderRequestDiv(tab)
		{
		document.getElementById('newRequest'+tab).style.display = 'block';
		document.getElementById('reportAproblem'+tab).style.display = 'none';
		document.getElementById('disagreeSubmit'+tab).style.display = 'block';
		document.getElementById('agreeSubmit'+tab).style.display = 'none';
		jQuery("#"+'cancellationAmount'+tab).val('');
		jQuery("#"+'cancelAmt'+tab).val('');
		jQuery("#"+'cancelComment'+tab).val('');
		document.getElementById('cancelComntCtr').innerHTML = '600';
		}
		
		
		
		function hideProviderRequestDiv(tab)
		{
		document.getElementById('newRequest'+tab).style.display = 'none';
		document.getElementById('reportAproblem'+tab).style.display = 'block';
		document.getElementById('agreeSubmit'+tab).style.display = 'none';
		document.getElementById('disagreeSubmit'+tab).style.display = 'none';
		jQuery("#"+"errorMessage"+tab).hide();		
		jQuery("#"+"errorMsge"+tab).hide();	
		
		}
		
		function showRequestDiv(tab)
		{
		
		document.getElementById('newRequest'+tab).style.display = 'block';
		document.getElementById('submit'+tab).style.display = 'none';
		document.getElementById('disagreeSubmit'+tab).style.display = 'block';
		document.getElementById('comment'+tab).style.display = 'none';
		
		document.getElementById('cancellationAmount'+tab).value = "";
		document.getElementById('cancelAmt'+tab).value = "";
		document.getElementById('cancelComment'+tab).value = "";
		document.getElementById('cancelComntCtr').innerHTML = '600';
			jQuery("#"+"errorMessage"+tab).hide();	
		jQuery("#"+"errorMsge"+tab).hide();	
			
		}
		
		
		
		function hideRequestDiv(tab)
		{
		
		document.getElementById('newRequest'+tab).style.display = 'none';
		document.getElementById('submit'+tab).style.display = 'block';
		document.getElementById('disagreeSubmit'+tab).style.display = 'none';
		jQuery("#"+"errorMessage"+tab).hide();
				jQuery("#"+"errorMsge"+tab).hide();	
						
		}
		
		function submitPendingCancel(tab)
		{
		 var formname='frmPendingCancel'+tab;                
		fnSubmit('serviceOrderPendingCancel_somPendingCancelSO.action',pendingCancelCB,null,formname);
		 // setTimeout("newco.jsutils.doIFrSubmit( newco.jsutils.getSelectedIfm() )",60000);                          
      	}
      	function showGlossaryItem(tab){	
			jQuery("#explainer"+tab).css("position","absolute");
			jQuery("#explainer"+tab).show();
		}
		
		function hideGlossaryItem(tab){
			 jQuery("#explainer"+tab).hide();
		}
		
		function showDisagreeDiv(tab)
		{
		document.getElementById('providerDisagree'+tab).style.display = 'block';
		document.getElementById('reportAproblem'+tab).style.display = 'block';
		document.getElementById('agreeSubmit'+tab).style.display = 'none';
		document.getElementById('disagreeSubmit'+tab).style.display = 'none';
		document.getElementById('comment'+tab).style.display = 'none';
		document.getElementById("disagreeComplete"+tab).checked="";
		document.getElementById("disagreeRequest"+tab).checked="";
		
        
		}
		
		
		function hideDisagreeDiv(tab)
		{
		document.getElementById('providerDisagree'+tab).style.display = 'none';
		document.getElementById('newRequest'+tab).style.display = 'none';
		document.getElementById('agreeSubmit'+tab).style.display = 'block';
		document.getElementById('disagreeSubmit'+tab).style.display = 'none';
		document.getElementById('reportAproblem'+tab).style.display = 'none';
		document.getElementById('comment'+tab).style.display = 'block';
		jQuery("#"+"errorMessage"+tab).hide();	
		jQuery("#"+"errorMsge"+tab).hide();	
		document.getElementById("disagreeComplete"+tab).checked="";
		document.getElementById("disagreeRequest"+tab).checked="";	
		
		}
		
		function textCounter( field, countfield, maxlimit ) {
	 		if ( field.value.length > maxlimit ) {
	  			field.value = field.value.substring( 0, maxlimit );
	  			document.getElementById(countfield).innerHTML = maxlimit - field.value.length;
	  			field.blur();
	  			field.focus();
	  			return false;
	 		} else {	 		
	 			 document.getElementById(countfield).innerHTML = maxlimit - field.value.length;
	 		}
		}
		
		function validateAmount(tab){
			var amtInt=	jQuery("#"+"cancellationAmount"+tab).val();
			var amtDec = jQuery("#"+"cancelAmt"+tab).val();
			if(amtInt != amtInt.replace(/[^0-9]/g, '')){
				jQuery("#"+"cancellationAmount"+tab).val("");
			}
			if(amtDec != amtDec.replace(/[^0-9]/g, '')){
				jQuery("#"+"cancelAmt"+tab).val("");
			}
		}
		
		function isNumberKey(evt)
    	{
        var charCode = (evt.which) ? evt.which : event.keyCode
        if (charCode > 31 && (charCode < 48 || charCode > 57))
           return false;
        return true;
     	}
		
		
    function modalOpenAddCustomer(dialog) {
            dialog.overlay.fadeIn('fast', function() {
            dialog.container.fadeIn('fast', function() {
            dialog.data.hide().slideDown('slow');
            });
        });
 }
  
     function modalOnClose(dialog) {
       dialog.data.fadeOut('slow', function() {
           dialog.container.slideUp('slow', function() {
               dialog.overlay.fadeOut('slow', function() {
                   jQuery.modal.close(); 
               });
           });
       });
    }    
     function saveCriteria(){
 		
 		var iFrameRef = document.getElementById("SearchmyIframe");
 		var searchForm = null;
 		var filterName = document.getElementById("filterName");
 		var selectedCriteria = document.getElementById("searchSelectionsList");
 		var selectedHTML = selectedCriteria.innerHTML;
 				
 		if(!dojo.isIE){
 			searchForm = iFrameRef.contentDocument.getElementById("searchHandler");
 		}else{
 			searchForm = iFrameRef.contentWindow.document.getElementById("searchHandler");		
 		}	
 		if( jQuery(selectedHTML).find('a').text().indexOf("[x]") != -1){
 				if(filterName && filterName.value != ''){
 				    searchCriteriaTreeNodesParser(searchForm);
 			 		
 					var filterNames = document.getElementById("filterName");
 					var filterNameData = filterNames.value;	
 					filterNameData = jQuery.trim(filterNameData);
					filterNameData=filterNameData.toLowerCase();

 					var filterExists =0;
 					var savedFilterName ='';
 					 <c:forEach var="searchFilters" items="${userSearchFilters}">  
  							savedFilterName = '${searchFilters.filterName}';
 							savedFilterName = jQuery.trim(savedFilterName);
 							savedFilterName=savedFilterName.toLowerCase();
  						if(savedFilterName == filterNameData){
 							filterExists=filterExists+1;
  						}
 					</c:forEach> 

 					if(filterExists != 0){
 						document.getElementById('saveFilterWarning').style.display="block";
 					}
 					else{
 					searchForm.searchFilterName.value = filterNameData;
 					searchForm.action="soSearch_saveSearchFilters.action";
 					searchForm.submit();
 					}
 				}else{
 					alert("Please select a Filter Name");
 				}		
 		}else{
 			alert("Please add atleast one search criteria");
 		}
 	}
</script>
		<script language="JavaScript" type="text/javascript">
			newco.jsutils.setGlobalStaticContext('${staticContextPath}');
			newco.jsutils.setGlobalContext('${contextPath}');
			var _commonSOMgr = newco.rthelper.initNewSOMRTManager('${contextPath}/monitor/refreshTabs.action', 30000, cb);
			
			var cb = function callBack( data ) {
				newco.jsutils._getResultsAsXML(data,_commonSOMgr.widgetId+"myIframe", document.getElementById("theRole").value );	
			}
				
				// registors the default tab
				if ('${tab}' != '') {
					_commonSOMgr.setSelectedWidget('${tab}', false);
				}
				else {
					_commonSOMgr.setSelectedWidget('${defaultTab}', false);
				}
				var cancelSOCB = function(data){
				      newco.jsutils.updateWithXmlData('cancelServiceOrderResponseMessage'+_commonSOMgr.widgetId,'message', data);
				      var passFailRst = newco.jsutils.handleCBData(data);
					  if(passFailRst != null ){
					  	 newco.jsutils.doIfrModalMessage( newco.jsutils.getSelectedIfm() ,
					  	 								  passFailRst.pass_fail_status,
					  	 								  passFailRst.resultMessage );
					  }
					  newco.jsutils.clearCancelData();
			                setTimeout("newco.jsutils.doIFrSubmit( newco.jsutils.getSelectedIfm() )",4000);
				}
 
				function cancelServiceOrder(){
			              newco.jsutils.doAjaxSubmit('serviceOrderCancel.action',
			              							cancelSOCB,null,
			              							'formHandler');   
			          }
			          
			          function hiddenCancelComment(){
			              $('cancelComment').value = $('cancelComment'+_commonSOMgr.widgetId).value;
			          }
			          
			         
			          
			         
			          
			          
			          function fnSubmitWithdrawCondOffer(){
			          	fnSubmit('serviceOrderWithdrawCondOffer.action',condOfferCB,null,'formHandler');
			          }
			          
			          var condOfferCB = function condOfferCB(data){
				      newco.jsutils.updateWithXmlData('condOfferResponseMessage'+_commonSOMgr.widgetId,'message', data);
				      var passFailRst = newco.jsutils.handleCBData(data);
					  if(passFailRst != null ){
					  	 newco.jsutils.doIfrModalMessage( newco.jsutils.getSelectedIfm() ,
					  	 								  passFailRst.pass_fail_status,
					  	 								  passFailRst.resultMessage );
					  }
					  newco.jsutils.clearCancelData();
			                setTimeout("newco.jsutils.doIFrSubmit( newco.jsutils.getSelectedIfm() )",4000);
			          }
				
			          function fnSubmitRejectSO(actionURL) {
			          	if (document.getElementById("reasonId").value == "" || document.getElementById("reasonId").value == "null") {
			            	document.getElementById('rejectServiceOrderResponseMessage'+_commonSOMgr.widgetId).innerHTML = "Please select reason to reject.";
			          	} else {
			            	newco.jsutils.doAjaxSubmit(actionURL, rejectCallBackFunction, null, 'formHandler');
			          	}
			          }
					  var rejectCallBackFunction = function(data) {
						var rCb = newco.jsutils.handleCBData(data);
						if(rCb != null ) {
						  	 newco.jsutils.doIfrModalMessage(newco.jsutils.getSelectedIfm(), rCb.pass_fail_status, rCb.resultMessage);
						}
					    newco.jsutils.updateWithXmlData('rejectServiceOrderResponseMessage'+_commonSOMgr.widgetId, 'message', data);
					    setTimeout("newco.jsutils.doIFrSubmit( newco.jsutils.getSelectedIfm() )",4000);
			          }
			          
			          function fnSubmitAddNote(url,cbFnc,formId,tab){
			          var radioValue = (document.getElementsByName("radioId"));
	   		          var selectedRadioValue = getCheckedValue(radioValue);
	   		       	 var ccSubjectFlag=validateCreditCardNumber(subject);
	   		    	 var ccMessageFlag=validateCreditCardNumber(message);
				     var success = false;
				     var subject = document.getElementById("subject" +tab).value;
				     var message = document.getElementById("message" +tab).value;
			         document.getElementById(tab + "subjectLabelMsg").style.display="none";
					 document.getElementById("subjectLabelMsg" + tab).style.display="none";
					 document.getElementById(tab+"subjectCCValidateLabelMsg").style.display="none";
					 document.getElementById("messageCCValidateLabelMsg" + tab).style.display="none";
			         if(subject == null || subject == "" || subject == "[subject]" || subject == "[Subject]")
					 {
			      			document.getElementById(tab+"subjectLabelMsg").style.display="";
			      			document.getElementById(tab+"subjectLabelMsg").style.color="red";
			 		 }else if (ccSubjectFlag){
							document.getElementById(tab+"subjectCCValidateLabelMsg").style.display="";
			      			document.getElementById(tab+"subjectCCValidateLabelMsg").style.color="red";
					}
			      	else if (message == null || message == "" || message == "[Message]" || message == "[message]")
					{
					    document.getElementById("subjectLabelMsg" + tab).style.display="";
					    document.getElementById("subjectLabelMsg"+tab).style.color="red";
					}else if (ccMessageFlag){
						document.getElementById("messageCCValidateLabelMsg" + tab).style.display="";
					    document.getElementById("messageCCValidateLabelMsg"+tab).style.color="red";
					}      
					else
					  {   
					    success = true;
					        url = url+"&radioSelection="+selectedRadioValue;
			                  fnSubmit(url,cbFnc,null,formId);
				      }
				      return success;
			
			                }
			                
			    function getCheckedValue(radioObj) {
			if(!radioObj)
				return "";
			var radioLength = radioObj.length; 
			if(radioLength == undefined)
				if(radioObj.checked)
					return radioObj.value;
				else
					return "";
			for(var i = 0; i < radioLength; i++) {
				if(radioObj[i].checked) {
					return radioObj[i].value;
				}
			}				
		}  
				     function cancel(tab)
					 {
					   document.getElementById("subject" + tab).value = '[Subject]';
			                 document.getElementById("message" + tab).value = '[Message]';
			                 document.getElementById(tab + "subjectLabelMsg").style.display="none";
			                 document.getElementById("subjectLabelMsg" + tab).style.display="none";
				     }
				     
				var addNoteWidgetCallBackFunction = function(data){
					//if(dojo.isIE)
					//{
					//	alert("doing ie test addNoteWidgetCallBackFunction");
					//}
					  var passFailRst = newco.jsutils.handleCBData(data);
					  if(passFailRst != null ){
					  	 newco.jsutils.doIfrModalMessage( newco.jsutils.getSelectedIfm(),
					  	 								  passFailRst.pass_fail_status,
					  	 								  passFailRst.resultMessage );
					  }
				      newco.jsutils.updateWithXmlData('addNoteWidgetResponseMessage'+_commonSOMgr.widgetId,'message',data); 
				      
				      newco.jsutils.clearNoteData();
				}
				
				function captureNote() 
				{
					 $('subject').value = $('subject'+_commonSOMgr.widgetId).value;
					 $('message').value = $('message'+_commonSOMgr.widgetId).value;
				}
				
				function resetAddNoteSubject() 
				{
					if($('subject'+_commonSOMgr.widgetId).value == '[Subject]' ){
					 	$('subject'+_commonSOMgr.widgetId).value = '';
					}
				}
				
				function resetAddNoteMessage() 
				{
					if($('message'+_commonSOMgr.widgetId).value == '[Message]'){
					 $('message'+_commonSOMgr.widgetId).value = '';
					}
				}
				
				var incSpendLimitCallBackFunction = function(data) {
					if(document.getElementById('titlePaneBtns')!=null)
						document.getElementById('titlePaneBtns').style.display = "block";
					if(document.getElementById('disabledDepositFundsDiv')!=null)
						document.getElementById('disabledDepositFundsDiv').style.display = "none";
					var incSlPF = newco.jsutils.handleCBData(data);
					if (incSlPF != null ) {
						newco.jsutils.doIfrModalMessage(newco.jsutils.getSelectedIfm(), incSlPF.pass_fail_status, incSlPF.resultMessage);
					}
					newco.jsutils.updateWithXmlData('increaseSPendLimitResponseMessage'+_commonSOMgr.widgetId,'message',data);
					if (incSlPF.pass_fail_status == 1) {
						newco.jsutils.clearSpendLimitData();
						newco.jsutils.updateOrderExpressMenu(_commonSOMgr.widgetId, incSlPF.addtional1, incSlPF.addtional2,incSlPF.addtional3);
					}
					
				}
			
				function fnCalcSpendLimit() {
					var status = newco.jsutils.calcSpendLimit();
				}
				
				function fnEnable(){
					var theTab = _commonSOMgr.widgetId;
					var reasonCode = document.getElementById('reason_widget'+theTab).value;
			    	if(reasonCode == "-2"){
			    		document.getElementById('comment_widget'+theTab).disabled = false;
			    		document.getElementById('comment_widget'+theTab).value = "";
			    		document.getElementById('comment_widget'+theTab).disabled = false;


			    	}else{
			    		document.getElementById('comment_widget'+theTab).disabled = true;
			    		document.getElementById('comment_widget'+theTab).value = "";
			    		document.getElementById('comment_widget'+theTab).style.background = '#E3E3E3';



			    	}
				}
				function limitText(limitField,limitNum) {
					if (limitField.value.length > limitNum) {
						limitField.value = limitField.value.substring(0, limitNum);
					}
					}
				function fnSubmitIncreaseSpendLimit(actionName,callBackfunction,param3,formname, textAreaId) {
					
					document.getElementById('titlePaneBtns').style.display = "none";
					document.getElementById('disabledDepositFundsDiv').style.display = "block";
					//First calculate the Maximum Price and then Submit
					var status = newco.jsutils.calcSpendLimit();
					if(status){
						var theTab = _commonSOMgr.widgetId;
						var totalAmt = 0.0;
						var totalAmtParts = 0.0;
						
						var maxSpendLimitPerSO  = ${SecurityContext.maxSpendLimitPerSO};
						
						totalAmt = parseFloat($('increaseLimit'+theTab).value);
						totalAmtParts = parseFloat($('increaseLimitParts'+theTab).value);
								
						var currentLabor=parseFloat($('currentLimitLabor'+theTab).value);
						var currentParts=parseFloat($('currentLimitParts'+theTab).value);					
						
						var totalAmtFinal = totalAmt + totalAmtParts;      
						var currentFinal = currentLabor+currentParts;
						var  increasedPrice  = totalAmtFinal - currentFinal;
				
						if(maxSpendLimitPerSO != 0 && maxSpendLimitPerSO < increasedPrice){
				     		$('increaseSPendLimitResponseMessage'+theTab).innerHTML = "You have requested a transaction amount that exceeds your limit. Please contact your administrator for further action.";        	
				        	status = false;
				        }
						var reasonCode = "";
						var reasonCodeId = "";
						  if($('reason_widget'+theTab)!= null){
						  reasonCodeId = document.getElementById('reason_widget'+theTab).value;
						  var selected_index = document.getElementById('reason_widget'+theTab).selectedIndex;
						  reasonCode = document.getElementById('reason_widget'+theTab).options[selected_index].text;
						  }
			             var reasonComment = $('comment_widget'+theTab).value;
			             reasonComment = jQuery.trim(reasonComment);
						 document.getElementById('increasedSpendLimitReasonWidget').value = reasonCode;
						 document.getElementById('increasedSpendLimitReasonIdWidget').value = reasonCodeId;
			              document.getElementById('increasedSpendLimitNotesWidget').value = reasonComment;
			              if(reasonCodeId == "-1"){
			              	$('increaseSPendLimitResponseMessage'+theTab).innerHTML = "Select a Reason for Spend Limit Increase";
			                $('increaseSPendLimitResponseMessage'+theTab).style.display= "block";
			                status = false;
			              }
			              if(reasonCodeId == "-2" && (reasonComment == null || reasonComment == "")){
			              	$('increaseSPendLimitResponseMessage'+theTab).innerHTML = "Enter Notes for Spend Limit Increase";
			                $('increaseSPendLimitResponseMessage'+theTab).style.display= "block";
			                status = false;
			              }
			              if(reasonCode == "" && (reasonComment == null || reasonComment == "")){
			            	  $('increaseSPendLimitResponseMessage'+theTab).innerHTML = "Provide a Reason for Spend Limit Increase";
			            	  $('increaseSPendLimitResponseMessage'+theTab).style.display= "block";
			                  status = false;
			                }
					}
					
					if(status == true ){
					document.getElementById(textAreaId).value='';
					fnSubmit(actionName,callBackfunction,param3,formname);
					}else{
						document.getElementById('titlePaneBtns').style.display = "block";
						document.getElementById('disabledDepositFundsDiv').style.display = "none";
				}
				}
				
				/*
				//Post the total Spend limit.			
				*/	
				function fnLoadCurrentSpendLimit(){
					newco.jsutils.updateTheNode('currentAmt', $('currentSpendLimit').value);
				}
				
				function fnSubmit(actionName,callBackfunction,param3,formname){
					newco.jsutils.doAjaxSubmit(actionName,callBackfunction,param3,formname);
				}
				
			/*	function sendData(){
					var selectedType = document.getElementById("searchType");
					var selectedTypeData = selectedType.value;
					var index = selectedType.selectedIndex;
					var selectedTypeValue = selectedType.value;
					document.getElementById('searchErrorMsg').style.display = "none";
					if (index == 0) {
						alert("Please select 'Search By'");
						return;
					}
					document.getElementById("searchValue").value = dojo.trim(document.getElementById("searchValue").value);
					var searchValue = document.getElementById("searchValue").value;
					if (searchValue.length == 0) {
						alert("Please specify 'Search Term'");
						return;
					}
					
					var iFrameRef = null;
					var searchForm = null;
									
					iFrameRef = document.getElementById("SearchmyIframe");
					
					if(!dojo.isIE){
						searchForm = iFrameRef.contentDocument.getElementById("searchHandler");
					}
					else{
						searchForm = iFrameRef.contentWindow.document.getElementById("searchHandler");		
					}
					if(selectedTypeValue=='3')
					{
						searchValue = formatSOId(searchValue);	
					}
					if(selectedTypeValue=='1')
					{
						if (searchValue.length == 12) {
							searchValue = formatPhone(searchValue);	
						}			
					}	
					if(selectedTypeValue=='4'||selectedTypeValue=='6'){
					var isNumeric = checkNumInNames(searchValue);
						if(isNumeric){
							document.getElementById('searchErrorMsg').style.display = "block";
							document.getElementById('searchErrorMsg').innerHTML = "Search by name value can only be alphabets and space.";
							return;				
						}			
					}
					searchForm.searchType.value = selectedTypeData;
					searchForm.searchValue.value = searchValue;
					searchForm.submit();
						
				}*/
				
				function formatSOId(searchValue)
				{
					var success = true;
							var pattern1 = /(^\d{3}-\d{4}-\d{4}-\d{2}$)/;
				     if (!pattern1.test(searchValue)) 
				     {
					     var pattern2 = /^(\d{3})(\d{4})(\d{4})(\d{2})$/;
					     searchValue = searchValue.replace(/-/g, "");
					     searchValue = searchValue.replace(pattern2,"$1-$2-$3-$4");
			         }
				return searchValue;
				}
				function formatPhone(searchValue)
				{
					var success = true;
					var patternhyphen = /(^\d{3}-\d{3}-\d{4}$)/;
					var patterndot = /(^\d{3}.\d{3}.\d{4}$)/;
					var pattern2 = /^(\d{3})(\d{3})(\d{4})$/;		
				     if (patternhyphen.test(searchValue)) 
				     {		     
					     searchValue = searchValue.replace(/-/g, "");
					     searchValue = searchValue.replace(pattern2,"$1$2$3");
			         }
			         if (patterndot.test(searchValue)) 
				     {		     
					     searchValue = searchValue.replace(/\./g, "");		     
					     searchValue = searchValue.replace(pattern2,"$1$2$3");
			         }
					return searchValue;
				}
				function checkNumInNames(searchValue)
				{
					var numChars = "0123456789";
					var value;
					var isNum = false;
						
					for(i=0;i<searchValue.length &&isNum == false;i++)
					{	
						Value = searchValue.charAt(i);
						if(numChars.indexOf(Value)!= -1){	
							isNum = true;
						}
					}		
					return isNum;
				}
				function clearSearchData(tabName)
				{
					var searchType = document.getElementById("searchType");
					searchType.selectedIndex = 0;
					
					var searchValue = document.getElementById("searchValue");
					searchValue.value = "";		
					
					var status = document.getElementById("statusId"+tabName);
					status.selectedIndex = 0;
					
					var subStatus = document.getElementById("subStatusId"+tabName);
					subStatus.selectedIndex = 0;
					
				}
				
				function open_popup(page)
				{
					var id = document.getElementById('soID')
					page = page + id.value;				
			    	window.open(page,'_blank','width=600,height=450,resizable=1,scrollbar=1');
			   	}
			   	
			   	function fnCopyServiceOrder(){		   		
			   		var id = document.getElementById('soID');		   		
			   		var path = "soWizardController.action?soId=";
			   		path = path + id.value;
			   		path = path + "&action=copy&tab=draft";
			   		window.location = path;
			   	}
			   	
				var p_topFilterList ;
				var p_filterList ;
			
				function getSelectedStatus(mySelStatusObj,mySelSubStatusObj,serviceProName,marketName,myiFrameWindow,tabName )
			     		{
			     		  var selObj = document.getElementById(mySelStatusObj);
			     		  var selectedStatusIndex = selObj.selectedIndex; 
				  var subList;
			      var subStatus = document.getElementById(mySelSubStatusObj);
			         selectedStatus = selObj.options[selectedStatusIndex].value;
			         if (selectedStatus != 0) 
			         {
			              subStatus.disabled = false;
			              subList = p_topFilterList[selectedStatus];
			              var boolean = 'N';
			                if (selectedStatus =='Today' || selectedStatus == 'Inactive' ) 
			                {
			                	boolean ='Y';
			                }
			              if (boolean =='N' )
						  {	
			              	subStatus.options.length = 0;
			              	var o ;
						  	if(subList.length>0){
						  		o = new Option('Show All','0',false,false);
								subStatus.options[0]=o;
						  	}
			              	for(var i=0;i<subList.length;i++)
			              	{
			                     o = new Option(subList[i].val2,subList[i].val1,false,false);
			                     subStatus.options[i+1] = o;
			              	}
			              }
			       }
			           else{
			           	 subStatus.length=0;
			           	 var f = new Option('Show All','0',false,false);
				  	 subStatus.options[0]=f;
			           }
			           
			           newco.jsutils.clearAllActionTiles();
			           
			          	 var iFrameWindow = document.getElementById(myiFrameWindow).contentWindow;
			            iFrameWindow.doStatusSubmit(selectedStatus,null);
			           
			     		}
			
			  		function getSelectedSubStatus(status, subStatus, serviceProName,marketName,myiFrameWindow, buyerRoleId){	  
			     		  var statusSelObj = document.getElementById(status); 
			     		  var subStatusSelObj = document.getElementById(subStatus); 
			     		  var selectedStatusIndex = statusSelObj.selectedIndex; 
			            var selectedStatus = statusSelObj.options[selectedStatusIndex].value;
				  var  selectedSubStatusIndex = subStatusSelObj.selectedIndex; 
				  var selectedSubStatus = subStatusSelObj.options[selectedSubStatusIndex].value; 	
			            var iFrameWindow = document.getElementById(myiFrameWindow).contentWindow;
			            
			            newco.jsutils.clearAllActionTiles();
			            
			            iFrameWindow.doStatusSubmit(selectedStatus,selectedSubStatus);
			     		}      
			     		
			     		function sortByColumn(tabName, sortColumnName, statusSortForm){
			     			sortByColumn(tabName, sortColumnName, statusSortForm, '');
			     		}
			     		function sortByColumn(tabName, sortColumnName, statusSortForm, filterId){
			     		
			     		var iFrameWindow = document.getElementById(tabName+'myIframe').contentWindow;
			            var iFrameWindowForm = iFrameWindow.document.getElementById(statusSortForm);
				
				  		var statusSelObj = document.getElementById('statusId'+tabName); 
			     		var subStatusSelObj = document.getElementById('subStatusId'+tabName); 
			     		
			     		if(statusSelObj != null && subStatusSelObj != null){
			     			var selectedStatusIndex = statusSelObj.selectedIndex; 
			            	var selectedStatus = statusSelObj.options[selectedStatusIndex].value;
				  			var  selectedSubStatusIndex = subStatusSelObj.selectedIndex; 
				  			var selectedSubStatus = subStatusSelObj.options[selectedSubStatusIndex].value; 	
				  			
				            iFrameWindowForm.status.value = selectedStatus;
				            iFrameWindowForm.subStatus.value = selectedSubStatus;
				        }

			            if(iFrameWindowForm.sortColumnName.value == sortColumnName){
			            	if(iFrameWindowForm.sortOrder.value == 'ASC'){
			            		iFrameWindowForm.sortOrder.value = 'DESC';
			            	}
			            	else{
			            		iFrameWindowForm.sortOrder.value = 'ASC';
			            	}
			            }
			            else{
			            	iFrameWindowForm.sortOrder.value = 'ASC';
			            }
			            iFrameWindowForm.sortColumnName.value = sortColumnName;
			                 
			            
			            sortImageFlip(sortColumnName, iFrameWindowForm.sortOrder.value, tabName);
			            
			            newco.jsutils.clearAllActionTiles();
			            
			            if(document.getElementById("isInitialLoad").value == 0){
							iFrameWindowForm.action = "/MarketFrontend/monitor/PBWorkflowSearch.action?pbFilterId="+filterId+"&sortOrder="+iFrameWindowForm.sortOrder.value+"&sortColumnName="+sortColumnName + "&fromWFM=true";
						}
						
				  iFrameWindow.newco.jsutils.displayModal('loadingMsg'+ _commonSOMgr.widgetId);
				  iFrameWindowForm.submit();
				}

	     		/* START FILTER BY ORDER TYPE */

	     	   function getSelectedPriceModel(mySelTypeObj,serviceProName,marketName,myiFrameWindow,tabName )
	     	   {
	     	    var selObj = document.getElementById(mySelTypeObj);
	     	    var selectedTypeIndex = selObj.selectedIndex; 
	     	    selectedType = selObj.options[selectedTypeIndex].value;
	     	    
	     	     newco.jsutils.clearAllActionTiles();
	     	     var iFrameWindow = document.getElementById(myiFrameWindow).contentWindow;
	     	    iFrameWindow.doPriceModelSubmit(selectedType);
	     	  }
	     	   
	     	   /* END FILTER BY ORDER TYPE */
				
				function sortImageFlip(sortColumnName, sortOrder, currentTab){
				  if(sortOrder == 'ASC'){
			            	$('sortBy'+sortColumnName+currentTab).src = "${staticContextPath}/images/grid/arrow-up-white.gif";
			            }
			            else{
			            	$('sortBy'+sortColumnName+currentTab).src = "${staticContextPath}/images/grid/arrow-down-white.gif";
			            }
			            
			            if(currentTab == 'Posted' || currentTab == 'Received'){
			            	$('sortBySpendLimit'+currentTab).style.display = "none";
			            	$('sortByTimeToAppointment'+currentTab).style.display = "none";
			            	$('sortByAgeOfOrder'+currentTab).style.display = "none";
			            }
			            else{
			            	$('sortByServiceDate'+currentTab).style.display = "none";
			            }
			            
			            $('sortByStatus'+currentTab).style.display = "none";
			            $('sortBySoId'+currentTab).style.display = "none";
			            
			            
			         	  $('sortBy'+sortColumnName+currentTab).style.display = "block";
			   }
				
				
			function getSummaryTabCount (tabId){
				var widgetTitle = dijit.byId(tabId).title;
				var index1 = widgetTitle.indexOf('(');
				var index2 = widgetTitle.indexOf(')');
				
				var tabCount = widgetTitle.substring(index1+1,index2);
				
				//alert ("widgettitle " + index1 + ": " + index2);
			
				//alert ("TabCount: " + tabCount);
			
				return tabCount;
				
			}
			
			function open_history_notes(vendorId)
			{
				if (document.openProvURL != null)
				{
					document.openProvURL.close();
				}
				var url = "powerAuditorWorkflowAction_getHistoryNotes.action?resourceID=-1&vendorID="+vendorId + "&" +appendUrl;
				newwindow=window.open(url,'_publicNotesHistory','resizable=yes,scrollbars=yes,status=no,height=700,width=1000');
				if (window.focus) {newwindow.focus()}
				document.openProvURL = newwindow;
			}
			
			var $resolveConflict = jQuery.noConflict();
function calculateSpendLimit(){
	var soId = document.getElementById('IncSLSOid').value;
	var tab = document.getElementById('tab').value;
			$resolveConflict('#increaseSpendLimit').load("serviceOrderMonitorAction_loadDataForIncSL.action?soId="+soId+"&wfFlag=1",function() {
 	 		$resolveConflict("#increaseSpendLimit").modal({
               			 onOpen: modalOpenAddCustomer,
               			 onClose: modalOnClose,
                		 persist: true,
               			 containerCss: ({ width: "380px", height: "auto", marginLeft: "-200px", top: "250px"})
            		});
            					window.scrollTo(1,1);
});	    		   
}
		
		
		 var pendingCancelCB = function pendingCancelCB(data){
	      newco.jsutils.updateWithXmlData('voidServiceOrderResponseMessage'+_commonSOMgr.widgetId,'message', data);
	      var passFailRst = newco.jsutils.handleCBData(data);
		  if(passFailRst != null ){
		  	 newco.jsutils.doIfrModalMessage( newco.jsutils.getSelectedIfm() ,
		  	 								  passFailRst.pass_fail_status,
		  	 								  passFailRst.resultMessage );
	    	
	    	jQuery(document).ready(function($) {
				$.post("dashboardPeriodicRefresh.action", function(data) {
					var availBalance = data.getElementsByTagName('available_balance')[0].childNodes.item(0).data;
					var currBalance = data.getElementsByTagName('current_balance')[0].childNodes.item(0).data;
					$('#available_balance').html(availBalance);
					$('#current_balance').html(currBalance);
				}, 'xml');
			});
		  }
		  newco.jsutils.hideDiv('rightMenu_');
	 	 newco.jsutils.hideDivInParent('rightMenu_');
		  newco.jsutils.clearCancelData();
               setTimeout("newco.jsutils.doIFrSubmit( newco.jsutils.getSelectedIfm() )",4000);
                               jQuery('#pendingCancelInd').val('success');
               
          }
 		function fnSubmitSOMDeleteDraft(){
			           var tab = _commonSOMgr.widgetId;
			           var soId = document.getElementById('soid'+tab).innerHTML;
			           var staticContextPath='${staticContextPath}';
			  	        $resolveConflict("#cancellationDiv").html("<img src=\"" +  staticContextPath+"/images/loading.gif\" width=\"200px\"/>");
			  			$resolveConflict('#cancellationDiv').load("serviceOrderMonitorAction_loadDataForCancellation.action?soId="+soId,function() {
			  			$resolveConflict("#cancelHeading").html("Delete Service Order");
			  			$resolveConflict("#cancelButtonDiv> input:button").val("Delete Service Order");
			  			$resolveConflict("#action").val("delete");
			  			
	  	 	 			$resolveConflict("#cancellationDiv").modal({
	  		            onOpen: modalOpenAddCustomer,
	  		            onClose: modalOnClose,
	  		            persist: true,
	  		            close: false,
	  		            containerCss: ({ width: "650px", height: "450px", marginLeft: "-300px" })
	  	            });
	              	window.scrollTo(1,1);
	  			});	    	
		}
		function submitCancellation(){
        	var errorMsg = fnValidateCancellation();
			if(errorMsg!=""){
				return false;
			}
	        var action = $resolveConflict("#action").val();
	        var reason = $resolveConflict("#reasonCode :selected").text();
	        $resolveConflict("#reason").val(reason);
	        if(action=="delete"){
	        	 fnSubmit('somDeleteDraft.action',deleteDraftCB,null,'frmCancelFromSOM');
	        }else{
	        	 fnSubmit('serviceOrderVoid.action',voidCB,null,'frmCancelFromSOM');
	        }
                
        } 
        function closeModalPopup(){
			$resolveConflict.modal.impl.close(true);
		}
		var deleteDraftCB = function deleteDraftCB(data){
					      //alert("test");
					      $resolveConflict.modal.impl.close(true);
					      newco.jsutils.updateWithXmlData('deleteDraftResponseMessage'+_commonSOMgr.widgetId,'message', data);
					      var passFailRst = newco.jsutils.handleCBData(data);
						  if(passFailRst != null ){
						  	 newco.jsutils.doIfrModalMessage( newco.jsutils.getSelectedIfm() ,
						  	 								  passFailRst.pass_fail_status,
						  	 								  passFailRst.resultMessage );
						  }
						  newco.jsutils.clearCancelData();
			              setTimeout("newco.jsutils.doIFrSubmit( newco.jsutils.getSelectedIfm() )",2000);
			          }
			           var voidCB = function voidSOCB(data){
			          $resolveConflict.modal.impl.close(true);
				      newco.jsutils.updateWithXmlData('voidServiceOrderResponseMessage'+_commonSOMgr.widgetId,'message', data);
				      var passFailRst = newco.jsutils.handleCBData(data);
					  if(passFailRst != null ){
					  	 newco.jsutils.doIfrModalMessage( newco.jsutils.getSelectedIfm() ,
					  	 								  passFailRst.pass_fail_status,
					  	 								  passFailRst.resultMessage );
				    	$resolveConflict.post("dashboardPeriodicRefresh.action", function(data) {
								var availBalance = data.getElementsByTagName('available_balance')[0].childNodes.item(0).data;
								var currBalance = data.getElementsByTagName('current_balance')[0].childNodes.item(0).data;
								$resolveConflict('#available_balance').html(availBalance);
								$resolveConflict('#current_balance').html(currBalance);
							}, 'xml');
						
					  }
					  newco.jsutils.clearCancelData();
			                setTimeout("newco.jsutils.doIFrSubmit( newco.jsutils.getSelectedIfm() )",4000);
			          }
        function fnValidateCancellation(){
				var errorMsg = "";
				$resolveConflict("#cancelE1").html("");
				$resolveConflict("#cancelE1").css("display","none");
				var reasonCode = $resolveConflict("#reasonCode").val();
				if(reasonCode=="-1"){
					errorMsg = "Please select a reason for canceling this order.<br/>";
				}
				var comments = $resolveConflict("#comments").val();
				if(comments==""){
					errorMsg = errorMsg + "Please enter comments describing why you are canceling this order.<br/>";
				}
				
				if(errorMsg!=""){
					$resolveConflict("#cancelE1").html(errorMsg);
					$resolveConflict("#cancelE1").css("display","block");
				}				
				return errorMsg;
			}
			function fnSubmitVoidSO(){
	          	var tab = _commonSOMgr.widgetId;
	          	var soId = document.getElementById('soid'+tab).innerHTML;
	          	$resolveConflict("#cancellationDiv").html("");
	          	$resolveConflict("#cancellationDiv").modal({
			            onOpen: modalOpenAddCustomer,
			            onClose: modalOnClose,
			            persist: false,
			            close: false,
			            containerCss: ({ width: "650px", height: "450px", marginLeft: "-300px" })
		            });
		        $resolveConflict('#cancellationDiv').load("serviceOrderMonitorAction_loadDataForCancellation.action?soId="+soId,function() {
		          	jQuery("#cancelHeading").html("Void Service Order");
		          	jQuery("#cancelButtonDiv> input:button").val("Void Service Order");
					jQuery("#action").val("void");
	            	window.scrollTo(1,1);
				});	    	
          }
				
			
</script>
<div id="cancellationDiv" name="cancellationDiv" style="display:none"></div>
					<input type="hidden" id="pendingCancelInd" name="pendingCancelInd" value="none" />
					<input type="hidden" id="cancelInd" name="cancelInd" value="none"/>
					
