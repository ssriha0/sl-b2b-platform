<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" import="com.newco.marketplace.web.validator.sow.SOWSessionFacility" %>
<%@ page language="java" import="com.newco.marketplace.interfaces.OrderConstants" %>
<%@ page import="com.newco.marketplace.web.dto.SOWScopeOfWorkTabDTO"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
	
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="so_id" scope="request" value="<%=request.getAttribute("SERVICE_ORDER_ID")%>" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="wizard.wizardTemplate"/>
	</jsp:include>
		
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/serviceLiveDojoBase.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/CalendarControl.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/wfm.css" />
        
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
		.ie7 .bannerDiv{margin-left:-50px;} 
		.ie8 .bannerDiv{margin-left:-50px;} 
		.ie9 .bannerDiv{margin-left:0px;}
		.ff3 .bannerDiv{margin-left:0px;} 
		.ff2 .bannerDiv{margin-left:0px;}
		.gecko .bannerDiv[style]{margin-left:0px;width:100%;}		
	   	@media screen and (-webkit-min-device-pixel-ratio:0) {
        #bannerDiv{margin-left:-50px;}
		</style>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/banner.css" />
		<!--<jsp:include page="/jsp/public/blueprint/browserCompatibilityBanner.jsp"></jsp:include>-->
		

<!-- Start:Done as a part of checkmarx -->
<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/lib/log4js.js"></script>

<!-- esapi4js core -->
<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/esapi.js"></script>

<!-- esapi4js i18n resources -->
<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/resources/i18n/ESAPI_Standard_en_US.properties.js"></script>

<!-- esapi4js configuration -->
<script type="text/javascript" language="JavaScript" src="${staticContextPath}/esapi4js/resources/Base.esapi.properties.js"></script>

<script type="text/javascript" language="JavaScript">
    Base.esapi.properties.application.Name = "SL Application";
    // Initialize the api
    org.owasp.esapi.ESAPI.initialize();
</script>
<!-- End:Done as a part of checkmarx -->



		<style type="text/css">
@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
</style>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
		<link rel="stylesheet" type="text/css"	href="${staticContextPath}/css/slider.css" />
		<link rel="stylesheet" type="text/css"	href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"	href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"	href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"	href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"	href="${staticContextPath}/css/service_order_wizard.css" />
		<link rel="stylesheet" type="text/css"	href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/jqueryui/jquery-ui-1.7.2.custom.css" />
	</head>

	<body class="tundra" onload="newco.jsutils.jumpToAnchor('${theTab}','${theAnchor}')">

		<form action="serviceOrderReject.action" method="post" id="rejectForm">
			<input type="hidden" name="requestFrom" id="requestFrom" value="SOD" />
			<input type="hidden" name="reasonId" id="reasonId" />
			<input type="hidden" id="SERVICE_ORDER_ID" name="SERVICE_ORDER_ID" value="${so_id}" />
		</form>




		<s:form action="soWizardReviewCreate_updateSubStatus" id="summarySubStatusChange" method="updateSubStatus">
			<input type="hidden" name="subStatusIdChanged"
				id="subStatusIdChanged" />
			<input type="hidden" name="next"
				id="next" value="tab6" />	
			<input type="hidden" id="SERVICE_ORDER_ID" name="SERVICE_ORDER_ID" value="${SERVICE_ORDER_ID}" />
		</s:form>
		
		<div id="page_margins" class="clearfix">
			<div id="page" class="clearfix">
				<div class="clearfix">
				<tiles:insertAttribute name="header" />
				<tiles:insertAttribute name="body" />
				<tiles:insertAttribute name="footer" />
				</div>
			</div>
		</div>
		
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
	 		<jsp:param name="PageName" value="Service Order Wizard"/>
	 	</jsp:include>
	<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/hideShow.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script> 
<script type="text/javascript" src="${staticContextPath}/javascript/tooltip.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>          
<script type="text/javascript" src="${staticContextPath}/javascript/basicAjax.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/dateTime/dateTime.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery-form.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>  
<script type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/nav.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/plugins/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/CalendarControl.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/banner.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqueryui/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript">
	var sortStatusProv=null;
	var sortStatusDist=null;
	jQuery.noConflict();
	jQuery(document).ready(function($){
			
			if($('#selectedCredential').val() > 0)
			  	{
			  		if($('#selectedCredential').val()!=$('#selectedCredInSession').val()){
			  			fnCredentialCategory('selectedCredential','refineProviderList','${contextPath}','refineProviderList_credentialCategory');
				}
			  			$('#specifyCredentialDiv').show();						
					
				};
				$('#selectedCredential').change(function() {
					
					if($('#selectedCredential').val() > 0)
					{
						$('#specifyCredentialDiv').show();
					}
					else
					{
						$('#specifyCredentialDiv').hide();
					}
				});
				//sort %Match column
			$(document).on("click", "#matchPercentage",function() {		
				if (sortStatusProv == null || sortStatusProv == 'up'){sortStatusProv = 'down';}
				else {sortStatusProv = 'up';}
				$("#matchSort").removeClass("icon-sort");
				$("#provSort").addClass("icon-sort");
				$("#distSort").addClass("icon-sort");
				var routingPriorityApplied = '${routingPriorityApplied}';
				if(routingPriorityApplied=="true"){
				$("#ratingSort").addClass("icon-sort");
				}
				$("#providerListHeader>tbody>tr>td:eq(1)").css("background","url(/ServiceLiveWebUtil/images/grid/arrow-"+sortStatusProv+"-white.gif) no-repeat 62px 14px");
				$("#providerListHeader>tbody>tr>td:eq(2)").css("background","none");
				$("#providerListHeader>tbody>tr>td:eq(5)").css("background","none");
				$("#providerListHeader>tbody>tr>td:eq(3)").css("background","none");
				 $("#providerListTable").tablesorter({ 
        			headers: { 1: { sorter:'digit' } } 
				}); 
				$("#providerListTable>thead>tr>th:eq(1)").click();
			});
			//sort Rating column
			$(document).on("click", "#rating",function() {	
				var routingPriorityApplied = '${routingPriorityApplied}';
				if(routingPriorityApplied=="true"){
				if (sortStatusProv == null || sortStatusProv == 'down'){sortStatusProv = 'up';}
				else {sortStatusProv = 'down';}
				$("#ratingSort").removeClass("icon-sort");
				$("#provSort").addClass("icon-sort");
				$("#distSort").addClass("icon-sort");
				$("#matchSort").addClass("icon-sort");
				$("#providerListHeader>tbody>tr>td:eq(3)").css("background","url(/ServiceLiveWebUtil/images/grid/arrow-"+sortStatusProv+"-white.gif) no-repeat 120px 14px");
				$("#providerListHeader>tbody>tr>td:eq(2)").css("background","none");
				$("#providerListHeader>tbody>tr>td:eq(5)").css("background","none");
				$("#providerListHeader>tbody>tr>td:eq(1)").css("background","none");
				  $.tablesorter.addParser({
    				id: 'score',
    			    is: function(s) { 
					// return false so this parser is not auto detected 
    				return false; 
    				}, 
    				format: function(s) { 
				    // format your data for normalization 
				    var val = s.replace(/%/,"")
				    		   .replace("SPN Performance Score:","");
     				return parseFloat(val); 
    				}, 
    				// set type, either numeric or text 
    				type: 'digit' 
				});
				$("#providerListTable").tablesorter({ 
        			headers: { 3: { sorter:'score' } } 
				});  
				//$("#providerListTable").tablesorter();
				$("#providerListTable>thead>tr>th:eq(3)").click();
				}
			});
			//sort Provider column	
			$(document).on("click", "#providerHdr",function() {
				if (sortStatusProv == null || sortStatusProv == 'up'){sortStatusProv = 'down';}
				else {sortStatusProv = 'up';}
				$("#provSort").removeClass("icon-sort");
				$("#distSort").addClass("icon-sort");
				var routingPriorityApplied = '${routingPriorityApplied}';
				if(routingPriorityApplied=="true"){
				$("#ratingSort").addClass("icon-sort");
				}
				$("#matchSort").addClass("icon-sort");
				$("#providerListHeader>tbody>tr>td:eq(2)").css("background","url(/ServiceLiveWebUtil/images/grid/arrow-"+sortStatusProv+"-white.gif) no-repeat 130px 14px");
				$("#providerListHeader>tbody>tr>td:eq(5)").css("background","none");
				$("#providerListHeader>tbody>tr>td:eq(1)").css("background","none");
				$("#providerListHeader>tbody>tr>td:eq(3)").css("background","none");
				$("#providerListTable").tablesorter();
				$("#providerListTable>thead>tr>th:eq(2)").click();
			});
			//sort Distance & Locn column
			$(document).on("click", "#distance",function() {		
				if (sortStatusDist == null || sortStatusDist == 'up'){sortStatusDist = 'down';}
				else {sortStatusDist = 'up';}
				$("#distSort").removeClass("icon-sort");
				$("#provSort").addClass("icon-sort");
				var routingPriorityApplied = '${routingPriorityApplied}';
				if(routingPriorityApplied=="true"){
				$("#ratingSort").addClass("icon-sort");
				}
				$("#matchSort").addClass("icon-sort");
				$("#providerListHeader>tbody>tr>td:eq(5)").css("background","url(/ServiceLiveWebUtil/images/grid/arrow-"+sortStatusDist+"-white.gif) no-repeat 80px 14px");
				$("#providerListHeader>tbody>tr>td:eq(2)").css("background","none");
				$("#providerListHeader>tbody>tr>td:eq(1)").css("background","none");
				$("#providerListHeader>tbody>tr>td:eq(3)").css("background","none");
			
				 // add parser to convert distance field and sort as numeric 
	    			$.tablesorter.addParser({
	    				id: 'dist',
	    			    is: function(s) { 
    					// return false so this parser is not auto detected 
        				return false; 
        				}, 
        				format: function(s) { 
					    // format your data for normalization 
         				return parseFloat(s); 
        				}, 
        				// set type, either numeric or text 
        				type: 'numeric' 
    				}); 
				$("#providerListTable").tablesorter({ 
        			headers: { 5: { sorter:'dist' } } 
				});
				jQuery("#providerListTable>thead>tr>th:eq(5)").click();
			});

		    $("#radioNYP").on('click',checkNamePrice);
		    $("#radioBid").on('click',checkBid);
		    
		    //R12_4
		    //SL-20702 : submit the zip code on pressing enter key
		    jQuery('#serviceLocationZipCode').keypress(function(e) {
		        var code = e.keyCode || e.which;
		        if(code === 13) {
		        	zipcodeContinue();
		        }
		    });
		    
		    jQuery('#zipContinue').keypress(function(e) {
		        var code = e.keyCode || e.which;
		        if(code === 13) {
		        	zipcodeContinue();
		        }
		    });
		    
		    //SL-20702 : submit the zip code on pressing enter key during 'copy SO'
		    jQuery('#editZipCode').keypress(function(e) {
		        var code = e.keyCode || e.which;
		        if(code === 13) {
		        	zipcodeContinueCopy();
		        }
		    });
		    
		    jQuery('#zipContinueCopy').keypress(function(e) {
		        var code = e.keyCode || e.which;
		        if(code === 13) {
		        	zipcodeContinueCopy();
		        }
		    });
		    
		    //SL-20702 : cancel pop up
		    jQuery('#zipCancel').keypress(function(e) {
		        var code = e.keyCode || e.which;
		        if(code === 13) {
		        	zipcodeCancel();
		        }
		    });
		    
		    jQuery('#zipCancelCopy').keypress(function(e) {
		        var code = e.keyCode || e.which;
		        if(code === 13) {
		        	zipcodeCancel();
		        }
		    });

		    $('#conditionalStartTime').datepicker(
	                {dateFormat: "yy-mm-dd", changeMonth:true, changeYear:true,
	                onSelect: function() {jQuery("#conditionalStartTime").css('color','black');}});
	});
	
	function checkNamePrice() {
		jQuery("#projectTypeNYP .soWizardPriceDetails").show();
		jQuery("#projectTypeBid .soWizardPriceDetails").hide();
	}
	function checkBid() {
		jQuery("#projectTypeNYP .soWizardPriceDetails").hide();
		jQuery("#projectTypeBid .soWizardPriceDetails").show();
	}

	function togglePartsSubpanel(obj){
		var dObjId =  jQuery(obj).attr('id');
		jQuery('#'+ dObjId+'+ div').toggle();
		if(jQuery('#'+ dObjId+'+ div').css('display')=='none'){
			jQuery(obj).css('background-image','url(${staticContextPath}/images/icons/arrowRight-darkgray.gif)');
			jQuery(obj).css('background-position','0 2px');
		}else{
			jQuery(obj).css('background-image','url(${staticContextPath}/images/icons/arrowDown-darkgray.gif)');
			jQuery(obj).css('background-position','0 5px');
		}
	}

	function getTrackingInformation(carrierElementName, trackElementName){
		var upsUrl="http://wwwapps.ups.com/WebTracking/track?HTMLVersion=5.0&loc=en_US&saveNumbers=null&track.x=Track&trackNums=";
		var fedexUrl="http://www.fedex.com/Tracking?action=track&language=english&cntry_code=us&initial=x&tracknumbers=";
		var uspsUrl="http://trkcnfrm1.smi.usps.com/PTSInternetWeb/InterLabelInquiry.do?strOrigTrackNum=";
		var trackUrl;
		/*
		var trackElementName = "parts[" + partsIndex + "]." + elementSuffix;
		var carrierElementName = "parts[" + partsIndex + "]." + shippingCarrierId";*/
		var trackingNo = document.getElementById(trackElementName).value;
		var carrierId =  document.getElementById(carrierElementName).value;
		
		if (trackingNo != "" && carrierId != "") {
			if (carrierId == 1) {
				trackUrl =upsUrl + trackingNo;
			} 
			else if (carrierId == 2){
				trackUrl = fedexUrl + trackingNo;
			}
			else if (carrierId == 5){
				trackUrl = uspsUrl + trackingNo;
			}
			else{
				alert("This carrier is not supported for tracking information");
				return;
			}
			window.open(trackUrl,'welcome', 'width=800,height=500, scrollbars=yes');
		}
		return;
	}
	
		function checkCarrierType(carrierTypeFieldId, otherCarrierFieldId, shippingStatusFieldId){
			var carrierId =  document.getElementById(carrierTypeFieldId).value;
			if (carrierId != "") {
				if (carrierId == 1 || carrierId == 2 || carrierId == 5) {
					document.getElementById(shippingStatusFieldId).disabled=false;
				}
				else{
					document.getElementById(shippingStatusFieldId).disabled=true;
				}
				checkSelectType(carrierTypeFieldId,otherCarrierFieldId);
				return true;
			}
			return false;		
		
		}			
		function stopRKey(evt) { 
		
		 var flg=0;
		
		 var textAreas = document.getElementsByTagName('textarea'); 
				  
	               var evt = (evt) ? evt : ((event) ? event : null);
	               var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null);  
		                      
	              
		            for (var i = 0; i < textAreas.length; i=i+1)
                      {
                         if(textAreas[i].id == node.id)
                          {
                          flg=1;                          
                          }
                      }
                
                            
		   if(flg !=1)
		   {			 
		  if ((evt.keyCode == 13))  {return false;} 
		   }
		   
		
		} 
		function cancelSO(){
		if (confirm("Are you sure you want to cancel?")) {
				var y = document.forms[1];
				y.submit();
			}
		}		
		document.onkeypress = stopRKey; 

			_actvController = new ACTVController(/*enable free tabbing*/ true);
			
			function fnSetBackgroundData(checkedBox){
				var selectedCheckBox = $F(checkedBox);
				var backgroundCheck = $('backgroundCheck');
				if(selectedCheckBox == 'Yes')
					backgroundCheck.value = 1;
				else if(selectedCheckBox == 'No')
					backgroundCheck.value = 2;
				else if(selectedCheckBox == 'Not')
					backgroundCheck.value = 0;		
			}
			
			function fnInsuranceData(checkedBox){
				var insuranceCheckedBox = $F(checkedBox);
				var insuranceFlag = $('insuranceFlag');	
					
				if(insuranceCheckedBox == 'Yes')
					insuranceFlag.value = 1;
				else if(insuranceCheckedBox == 'No')
					insuranceFlag.value = 2;
				else if(insuranceCheckedBox == 'Not')
					insuranceFlag.value = 0;					
			}			
			
			function fnSelectTopProviders(elementId,formId,contextPath)
			{						
				var selectedElement = document.getElementById(elementId);
				
				if(selectedElement !=null && selectedElement.selectedIndex != 0){
					var loadForm = document.getElementById(formId);	
					
					loadForm.action = contextPath + "/soWizardProviders_selectTopProviders.action?next=tab4&SERVICE_ORDER_ID=${SERVICE_ORDER_ID}";
					loadForm.submit();
				}
			}

			function fnSubmitSearchCriteria(contextPath){
			
				var providerListDivTag = $("providerList");
				var divElements = providerListDivTag.getElementsByTagName("input");
				var checkedList = "";
				for(var i=0;i<divElements.length;i++)
				{
					if($(divElements[i].getAttribute("id")) == null)
					{
						continue;
					}
					if($(divElements[i].getAttribute("id")).checked == true)
					{
						checkedList = checkedList + divElements[i].getAttribute("id")+"$true"+',';
					}
					else
					{
						checkedList = checkedList + divElements[i].getAttribute("id")+"$false"+',';
					}
				}
				$("checkedProvidersList").value = encodeURI(checkedList);		
				
				var loadForm = document.getElementById("refineProviderListM");	
					
				loadForm.action = contextPath + "/refineProviderList_refineSearch.action?next=tab4&SERVICE_ORDER_ID=${SERVICE_ORDER_ID}";
				loadForm.submit();			
			}

				dojo.require("dijit._Calendar");
				dojo.require("dijit.form.DateTextBox");

 				function selectRangeDate(){
 					document.getElementById("rangeDate").style.visibility='visible';
 					document.getElementById("rangeDate").style.display='block';
 				
 				}
 				function selectFixedDate(){
 					document.getElementById("rangeDate").style.visibility='hidden';
 				}
 				
 				function fnShowOrHidePartsPanel(){
					if(document.getElementById('PartsPanels')){
						if (document.getElementById("partsSuppliedBy3").checked  || document.getElementById("partsSuppliedBy2").checked){
							document.getElementById('PartsPanels').style.display="none";
							document.getElementById("addPartBtn").style.display="none";
						}else{
						   showPartPanel();
						}
					}		
					return;			
				}
				
			function showPartPanel(){
				 document.getElementById("addPartBtn").style.display="block";
				 document.getElementById('PartsPanels').style.display="block";
				 return;
			}
			
			function toggleAltContactDisabled(divID)
			{
				
				var doc = document.getElementsByTagName('div');
				for (var i = 0; i < doc.length; i=i+1)
				{
					if(doc[i].id == divID.toString())
					{
				   		if(doc[i].style.display == "block")
				   		{
				   			doc[i].style.display = "none";
				   		}
				   		else
				   		{
				   			doc[i].style.display = "block";
				   		}
				   	}
				}				
			}
			
 			function showZipCode(){
 				if(document.getElementById('sameLoc').checked){
 					document.getElementById('editZip').style.visibility='hidden';
 					document.getElementById('zipHelpText').style.visibility='hidden';
 				}else{
 					document.getElementById('editZip').style.visibility='visible';
 					document.getElementById('zipHelpText').style.visibility='visible'; 					
 				}
 			
 			}
 			
 			function toggleAltTaskDisabled(divID,mainCategory)
			{				
				var selectElement = document.getElementById(mainCategory);
				
				var doc = document.getElementsByTagName('div');
				for (var i = 0; i < doc.length; i=i+1)
				{
					if(doc[i].id == divID.toString())
					{
				   		if(selectElement.selectedIndex != 0)
				   		{
				   			doc[i].style.display = "block";
				   		}
				   		else 
				   		{
				   			doc[i].style.display = "none";
				   		}
				   	}
				}			
			}
			
			<%-- JavaScript functions for Zipcode dialog handling --%>
			
			var hidingAfterSuccess = false;  // Used to avoid infinite recursion between hide and close events
			
			function showZipcodeModalDialog() {
			<%
			String zip = null;
			try{
			//SL-19820
			//SOWScopeOfWorkTabDTO modelScopeDTO = 
				//(SOWScopeOfWorkTabDTO)
							//SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_SOW_TAB);
			String soId = (String) request.getAttribute("SERVICE_ORDER_ID");
			SOWScopeOfWorkTabDTO modelScopeDTO = 
				(SOWScopeOfWorkTabDTO)
							SOWSessionFacility.getInstance().getTabDTO(OrderConstants.SOW_SOW_TAB, soId);	
				if (modelScopeDTO == null){
					zip="";
				}
				else{
					zip = modelScopeDTO.getServiceLocationContact().getZip();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			%>
			var isCopy=document.getElementById("isCopy").value;
			var startCopy=document.getElementById("startCopy").value;
			if((isCopy=='copy') && (startCopy!='true')){	
				newco.jsutils.displayModal('zipcodeCopyModal');
			}else{
			var zip = '<%=zip%>';
				if (zip == 'null' || zip == "") {
					newco.jsutils.displayModal('zipcodeModal');
					dojo.connect(dijit.byId('zipcodeModal'), "hide", function(e) { zipcodeCancel(); hidingAfterSuccess = false; });
					if(document.getElementById('serviceLocationZipCode')){
						document.getElementById('serviceLocationZipCode').focus();
					}
				}
			}
			}
			
			function zipcodeContinueCopy() {
				if(document.getElementById('diffLoc').checked){
					
				    var zipTrim = dojo.trim(document.getElementById('editZipCode').value);
					var zipcodeValue = zipTrim;
					var zipcodeLabel = document.getElementById("errLabelCopy");
					zipcodeLabel.innerHTML = "";
					zipcodeLabel.style.visibility = 'hidden';
					if (zipcodeValue.length == 0) {
						zipcodeLabel.innerHTML = "<p class='errorMsg'>Please enter a valid zip code where you want service.</p>";
						zipcodeLabel.style.visibility = 'visible';
						document.getElementById('editZipCode').focus();
					} else {
						// Validate zipcode using ajax call
						var validateZipAjaxURL = 'sowAjaxAction.action?zipcode=' + zipcodeValue+'&SERVICE_ORDER_ID=${SERVICE_ORDER_ID}';
		                newco.jsutils.doAjaxURLSubmit(validateZipAjaxURL, validateZipCodeCBCopy);
						//alert("Submitted ajax call for URL:\n" + validateZipAjaxURL);
					}
				}else{
					dijit.byId('zipcodeCopyModal').hide();
					var validateZipAjaxURL = 'sowAjaxAction.action?startCopy=true&SERVICE_ORDER_ID=${SERVICE_ORDER_ID}';
		            newco.jsutils.doAjaxURLSubmit(validateZipAjaxURL, dummy);
					document.getElementById("startCopy").value = "true";
				}
			}
			
			var dummy = function(data) {
			}
			
			function zipcodeContinue() {
			
			<%
			String soId = (String) request.getAttribute("SERVICE_ORDER_ID");
			%>
				var soId = '<%=soId%>';
			    var zipTrim = dojo.trim(document.getElementById('serviceLocationZipCode').value);
				var zipcodeValue = zipTrim;
				var zipcodeLabel = document.getElementById("errLabel");
				zipcodeLabel.innerHTML = "";
				zipcodeLabel.style.visibility = 'hidden';
				if($("#mainServiceCategory")!=null){
					document.getElementById('mainServiceCategory').value= -1;
				}
				
				if (zipcodeValue.length == 0) {
					zipcodeLabel.innerHTML = "<p class='errorMsg'>Please enter a valid zip code where you want service.</p>";
					zipcodeLabel.style.visibility = 'visible';
					document.getElementById('serviceLocationZipCode').focus();
				} else {
					// Validate zipcode using ajax call
					var validateZipAjaxURL = 'sowAjaxAction.action?SERVICE_ORDER_ID='+soId+'&zipcode=' + zipcodeValue;
					newco.jsutils.doAjaxURLSubmit(validateZipAjaxURL, validateZipCodeCB);
					//alert("Submitted ajax call for URL:\n" + validateZipAjaxURL);
				}
			}
			
			var validateZipCodeCBCopy = function(data) {
				var passFailResult = newco.jsutils.handleCBData(data);
				if (passFailResult.pass_fail_status == "1") { // Success
					// Display state and zipcode values as labels on scope screen
					$("serviceLocationContact.state").value = passFailResult.addtional1;
					$("startCopy").value='true';
					$("serviceLocationContact.stateLabel").innerHTML = "<b>"+passFailResult.addtional2+"</b>";
					$("serviceLocationContact.zipLabel").innerHTML = "<b>"+document.getElementById('editZipCode').value+"</b>";
					$("serviceLocationContact.zip").value = document.getElementById('editZipCode').value;
					
					$("serviceLocationContact.businessName").value ='';
					$("serviceLocationContact.firstName").value ='';
					$("serviceLocationContact.lastName").value ='';
					
					$("serviceLocationContact.streetName1").value ='';
					$("serviceLocationContact.aptNo").value ='';
					$("serviceLocationContact.streetName2").value ='';
					$("serviceLocationContact.city").value ='';
					$("serviceLocationContact.zip4").value ='';
					$("serviceLocationContact.email").value ='';
					if($("serviceLocationContact.phones[0].areaCode")!=null){
					$("serviceLocationContact.phones[0].areaCode").value ='';
					
					$("serviceLocationContact.phones[0].areaCode").value ='';
					$("serviceLocationContact.phones[0].phonePart1").value ='';
					$("serviceLocationContact.phones[0].phonePart2").value ='';
					$("serviceLocationContact.phones[0].ext").value ='';
					document.getElementById('phoneClass1').value='-1';
					}if($("serviceLocationContact.phones[1].areaCode")!=null){
					$("serviceLocationContact.phones[1].areaCode").value ='';
					$("serviceLocationContact.phones[1].phonePart1").value ='';
					$("serviceLocationContact.phones[1].phonePart2").value ='';					
					$("serviceLocationContact.phones[1].ext").value ='';
					document.getElementById('phoneClass2').value='-1';
					}if($("serviceLocationContact.phones[2].areaCode")!=null){
					$("serviceLocationContact.phones[2].areaCode").value ='';
					$("serviceLocationContact.phones[2].phonePart1").value ='';
					$("serviceLocationContact.phones[2].phonePart2").value ='';
					}
					$("serviceLocationContact.serviceLocationNote").value ='';
					
					
										
					retrieveXmlData(passFailResult.addtional3);
					hidingAfterSuccess = true;
					dijit.byId('zipcodeCopyModal').hide();
				} else {
					var zipcodeLabel = document.getElementById("errLabelCopy");
					zipcodeLabel.innerHTML = "<p class='errorMsg'>"+passFailResult.resultMessage+"</p>";
					zipcodeLabel.style.visibility = 'visible';
					document.getElementById('editZipCode').focus();
				}
			}
			
			var validateZipCodeCB = function(data) {
				
				var passFailResult = newco.jsutils.handleCBData(data);
				
				if (passFailResult.pass_fail_status == "1") { // Success
					// Display state and zipcode values as labels on scope screen
					$("serviceLocationContact.state").value = passFailResult.addtional1;
					$("serviceLocationContact.stateLabel").innerHTML = "<b>"+passFailResult.addtional2+"</b>";
					$("serviceLocationContact.zipLabel").innerHTML = "<b>"+document.getElementById('serviceLocationZipCode').value+"</b>";
					$("serviceLocationContact.zip").value = document.getElementById('serviceLocationZipCode').value;
					retrieveXmlData(passFailResult.addtional3);
					hidingAfterSuccess = true;
					dijit.byId('zipcodeModal').hide();
					
				} else {
					var zipcodeLabel = document.getElementById("errLabel");
					zipcodeLabel.innerHTML = "<p class='errorMsg'>"+passFailResult.resultMessage+"</p>";
					zipcodeLabel.style.visibility = 'visible';
					document.getElementById('serviceLocationZipCode').focus();
				}
			}
			
			function retrieveXmlData(data){
				var temp = new Array();
				temp = data.split(';');
				var mainServiceCategory = $('mainServiceCategory');
				//var mainCatselectedIndex=$("#mainServiceCategory option:selected").index();
				mainServiceCategory.empty();
				mainServiceCategory.append(new Option("-- Select One --", "-1"));
				var temp1 = new Array();
				for(i=0; i<(temp.size()-1);i++){ 
					temp1=temp[i].split("|"); 
					id = temp1[0];
					text =temp1[1];  
					try{
						mainServiceCategory.append(new Option(text, id));

					} catch(ex){
						console.log(ex);
						mainServiceCategory.append(new Option(text, id));
					}
				}
				
			}
			
			function zipcodeCancel() {
				if (!hidingAfterSuccess) {
					// This will take user back to where he came from (Dashboard or SOM)
					document.getElementById('zipcodeDialogCancelForm').submit();
				}
			}
			
	
	var cb =  function cbFucn (data ) {		
		newco.jsutils.updateWithXmlData("available_balance", "available_balance", data);
		newco.jsutils.updateWithXmlData("current_balance", "current_balance", data);
	}
	newco.jsutils.setGlobalContext('${contextPath}'); 
	
</script>

	</body>

</html>
