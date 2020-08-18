<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
	
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />

<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dashboard.css" />
	
	<script language="JavaScript" type="text/javascript"
	src="${staticContextPath}/javascript/plugins/ajaxfileupload.js"></script>
	<!-- esapi4js dependencies -->
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
<script>
	// This function is to check if values are already set in reasons

	window.onload = addPartLoad();
	function addPartLoad() {
		
		var userAgent = navigator.userAgent.toLowerCase(); 		
		
		jQuery("#explainer").css('margin-top', '-100px');
		jQuery("#explainer").css('margin-left', '95px');
	 
		var select = jQuery(".quantity");
		for (i = 1; i <= 99; i++) {
			select.append(jQuery('<option></option>').val(i).html(i))
		}

	}
	function selectParts(){
		  fnWaitForResponseShow("Saving...");
		  	var partNumPosition=$('.addLisPart:checked').map(function(){return this.value;}).get();	  	
		  	jQuery("#partNumPosition").attr("value",partNumPosition);
		  	if(partNumPosition.length > 0){
			var formData = jQuery('#searchPartForm').serialize();
			var soId = jQuery('#searchSoId').val();
			disableCursor();
			$.ajax({
			url: 'saveLisParts.action',
	        type: "POST",
	        data: formData,
			dataType : "json",
           	success: function(data) {   		
           		var responseMsg = data.responseMessage;		 					
            	if(responseMsg!=null && responseMsg!='' && responseMsg!=' ' && responseMsg != '-1'){
            		enableCursor();
            		fnWaitForResponseClose();
            		jQuery("#errorDivParts").html($ESAPI.encoder().encodeForHTML(responseMsg));
            		jQuery("#errorDivParts").css("display","block");
            		 enableCursor();
   	    		    fnWaitForResponseClose();
   	    		    window.location.hash = '#errorDivParts';
            	}
					else{
				          closeAddPart();
				        	jQuery("#partSummaryEdit").load("loadPartDetails.action?soId="+soId,function() { 
				        		enableCursor()
				   	            fnWaitForResponseClose();
				               	window.location.hash = '#partSummaryEdit';
				    		});
					}          				     
			 }                    
        });
		}
		  	else{
		  		 jQuery("#errorDivParts").html('You have to select atleast one part.');
	    		  jQuery("#errorDivParts").css("display","block");
	    		  jQuery("#partDetails").css("display","block");
	    		  enableCursor();
	    		  fnWaitForResponseClose();
	    		  window.location.hash = '#errorDivParts';
		  	}
	}
	
	
	function showMaualParts(){
		jQuery("#partNumSearchDiv").css("display","none");
		jQuery("#lisErrorMsg").css("display","none");
		jQuery("#manualParts").css("display","block");
		jQuery("#noPartAddButton").css("display","none");
	}
	
	function amountRoundoffRetailPrice(){
		 var retailPrice = jQuery("#retailPrice").val();
		 if(retailPrice == 0){
		   var zero = "0.00";
		  jQuery("#retailPrice").val(zero);
		 }else{
		  jQuery("#retailPrice").val(addOnObject.fmtMoney(retailPrice));
		 }
	}

	
	jQuery("#retailPrice").keypress(function (e){
		var retailPrice = jQuery("#retailPrice").val();
		 var charCode = (e.which) ? e.which : event.keyCode;
		    if (charCode == 46 && retailPrice.split('.').length>1){
		        return false;
		    }
		    if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57))
		        return false;
		    return true; 
	 });
	
	jQuery("#divisionNumber").keypress(function (e){
	  	if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57)){
	   		return false;
	 	}
	 });
	 
	jQuery("#sourceNumber").keypress(function (e){
	  	if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57)){
	   		return false;
	 	}
	 });
	
	 jQuery("#description").keypress(function(e) {
  		//if the letter is not digit or Alphanumeric then display error and don't type anything
		if( e.which!=8 && e.which!=0 && e.which!=32 && e.which!=44  && (e.which<48 || e.which>57) && (e.which<65 || e.which>90) 
		   && (e.which<97 || e.which>122) ){
   		   return false;
 		}
	});
  
	 var panel2Def = "&ldquo;<strong>Retail Price</strong>&rdquo; is the actual retail price sold through Sears Parts Direct.";

	 jQuery(".glossaryItem").mouseover(function(e){
	 	    	jQuery("#explainer").css("position","absolute");
	 	    	if(jQuery(this).attr("id") == "manPartPrice") {
	 	    	 var position = jQuery("#manPartPrice").offset();
	 	     	jQuery("#explainer").html(panel2Def);
	 	    
	 	    	}    	
	 	    	jQuery("#explainer").show();   
	 		}); 
	 	  	  jQuery(".glossaryItem").mouseout(function(e){
	 		  jQuery("#explainer").hide();
	 		}); 
</script>
<style>

td.search_parts {     
		border-bottom: 1px solid #CCC;
		padding-top: 10px;
		padding-bottom:10px;
		background-color:#00A0D2;
        color:black;
       font-weight:bold;
	}
	
td.search_parts_body {     
		border-bottom: 1px solid #CCC;
		padding-top: 10px;
		padding-bottom:10px;
		background-color:#F4F5EC;
        color:black;
        width:400px;
	}

</style>
</head>
<body> 	
			  <input type="hidden" value="${LIS_ERROR}" id="lisError">	
		<c:choose>
		<c:when test="${fn:length(addInvoiceDTO) > 0}">			
			<div id="partDetails" style="z-index: 3000; display: none;">
			<form id="searchPartForm" name="searchPartForm" method="POST">	
				<div style="overflow:scroll;height:230px;overflow:auto"> 
				<table id="searchPartTable" width="100%" 
					cellpadding="0" 
					style="display: block; margin-top: 10px;">
					<thead height="10px;">
						<tr>
							<td class="search_parts" align="center" width="10%">Select</br>
							</td>
							<td class="search_parts" align="left" width="60%"
								style="padding-left: 4px">Part Details</td>
							<td class="search_parts" align="center" width="10%">Qty</td>
					</thead>	
					<tbody >
					    <c:set var="invoiceCountNo" value="0"/>
						<c:forEach items="${addInvoiceDTO}" var="invoicePart"
							varStatus="status">						
						 <tr id="tablerowAdd${status.count}">
						  <input type="hidden" id="partNumPosition" name="partNumPosition" />
						  <input type="hidden" id="searchSoId" name="addInvoiceDTO[${invoiceCountNo}].soId" value="${invoicePart.soId}"/>
						  <input type="hidden" id="divisionNumber_${status.count}" name="addInvoiceDTO[${invoiceCountNo}].divisionNumber" value="${invoicePart.divisionNumber}"/>
          				  <input type="hidden" id="sourceNumber_${status.count}" name="addInvoiceDTO[${invoiceCountNo}].sourceNumber" value="${invoicePart.sourceNumber}" />
          				  <input type="hidden" id="partNumber_${status.count}" name="addInvoiceDTO[${invoiceCountNo}].partNumber" value="${invoicePart.partNumber}" />
          				  <input type="hidden" id="partDescription_${status.count}" name="addInvoiceDTO[${invoiceCountNo}].partDescription" value="${invoicePart.partDescription}" />
          				  <input type="hidden" id="retailPrice_${status.count}" name="addInvoiceDTO[${invoiceCountNo}].retailPrice" value="${invoicePart.retailPrice}" />
								<td class="partAdd search_parts_body" id="selectBoxAdd_${status.count}" width="10%">
									<input style="margin-left: 15px; margin-top: 40%;"
									id="addLisPart_${status.count}" name="addInvoiceDTO[${invoiceCountNo}].partIdentifier"
									class="addLisPart" type="checkbox"
									value="${invoicePart.partIdentifier}">
								</td>
								<td class="search_parts_body" id="partAdd_${status.count}"
									style="padding-left: 4px" align="left" width="60%">
									<div id="partNumber_${status.count}" name="addInvoiceDTO[${invoiceCountNo}].partNumber"
										style='width: 100%; word-wrap: break-word'><b>Part No#: </b>${invoicePart.partNumber}</div>
									
									<div id="partDescription_${status.count}" name="addInvoiceDTO[${invoiceCountNo}].partDescription"
										style='width: 100%; word-wrap: break-word'><b>Part Name: </b>${invoicePart.partDescription}</div>
									
									<div id="retailPrice_${status.count}" name="addInvoiceDTO[${invoiceCountNo}].retailPrice"
										style='width: 100%; word-wrap: break-word'><b>Retail Price: </b>$ ${invoicePart.retailPrice}</div>
								</td>
								<td width="10%" id="qty_${status.count}" class="search_parts_body">
							   <select class="quantity" style="margin-top: 20%;" id="qty_${status.count}" name="addInvoiceDTO[${invoiceCountNo}].qty"></select>
								
								</td>
							</tr>
							 <c:set var="invoiceCountNo" value="${invoiceCountNo+1}"/> 
						</c:forEach>
					</tbody>
				</table>
				</div>
				</form>
				<td width="10%"><input type="button"  id="lisAddPart"  onclick="selectParts();" style="margin-left:75%;" class="newButton" value="ADD PART(S)" ></input></td>
			</div>		
			</c:when>
			<c:otherwise>
				 <div id="lisErrorMsg">
				  <div  style="background-color:#F4F5EC;"><font size="2px;">No matching results. Enter a valid part number to search again or add the part manually.</font></div></br>
				   	<div width="10%"><input type="button" id="noPartAddButton"  onclick="showMaualParts();" style="margin-top:-15px;" class="newButton" value="ADD PART(S)" ></input></div> 
				  </div> 
				 <div id="manualParts" style="z-index: 3000; display: none;">
				  <input type="hidden" value="${RETAIL_PRICE_ERROR}" id="manualRetailError"/>
				  <div id="errorManualParts"
					style="margin-left: 20px;padding-left: 30px;width: 90%;border: 2px solid #F5A9A9;display:none;padding-top:2px;
					background: url('${staticContextPath}/images/icons/50.png') no-repeat scroll 10px 1px #FBE3E4;"></div>	
				  <form id="manualPartForm" name="manualPartForm" method="POST">	
					<table id="manualPartTable" width="80%" class="installed_parts"
					cellpadding="0" style="display: block; margin-top: 10px;">	
					<tr>
					<td>
				  <div id=manPartNumber>Part Number:</div></td>
		  		 <td> <div style="padding-bottom: 3px">
					<input type="text" 
						name="editPartVO.partNumber" minlength="25" maxlength="25" style="margin-left:40px;" value="${editPartVO.partNumber}"
						 id="ManPartNumber"/> 
				  </div></td>
				  </tr>
				  <tr>
				  <td>
				  <div id=manPartName>Part Name:</div></td>
				  <td><div style="padding-bottom: 3px">
				  <input type="text" 
						maxlength="50"  name="editPartVO.partDescription" id="description" style="margin-left:40px;" value="${editPartVO.partDescription}"/>
					</div></td></tr>
					<tr><td>
				  <div id=manPartQty>Qty:</div></td>
				  <td><div style="padding-bottom: 3px">
				    <select class="quantity" id="qty" name="editPartVO.qty" style="margin-left:40px;"></select>
					</div></td></tr>
				  <tr><td style="cursor: pointer"><span id=manPartPrice class="glossaryItem">Part Retail Price:</span></td>
				    <td><div style="padding-bottom: 3px">
				   <input type="text" 
						maxlength="50"  name="editPartVO.retailPrice" id="retailPrice" style="margin-left:40px;" value="${editPartVO.retailPrice}" 
						onblur="amountRoundoffRetailPrice();"/>
					</div></td></tr>
				<tr><td><div id=manPartDivNo>Division No:</div></td>
				    <td><div style="padding-bottom: 3px">
				   <input type="text" 
					name="editPartVO.divisionNumber" id="divisionNumber" minlength="4" maxlength="4" style="margin-left:40px;" value="${editPartVO.divisionNumber}"/>
					</div></td></tr>
					<tr><td>
				  <div id=manPartSourceNo>Source No:</div></td>
				   <td> <div style="padding-bottom: 3px">
				   <input type="text" 
					 name="editPartVO.sourceNumber" id="sourceNumber" minlength="3" maxlength="3" style="margin-left:40px;" value="${editPartVO.sourceNumber}"/>
					</div></td></tr>
					</table>
				  </form>
				  	<div width="10%"><input type="button"  id="manualAddButton"  onclick="addPart();" style="margin-left:350px;" class="newButton" value= "ADD PART"></input></div> 	  
				  </div> 
			</c:otherwise>
	</c:choose>	
			<div id="explainer" style="z-index: 1000"></div>
	<br>
</body>
</html>
