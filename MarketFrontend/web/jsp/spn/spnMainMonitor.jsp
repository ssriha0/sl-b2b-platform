<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<%@page import="com.newco.marketplace.interfaces.SPNConstants"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="selectedSPN" value="<%=(String)request.getAttribute(SPNConstants.SELECTED_SPN)%>"></c:set>   
<c:set var="BUTTON_TYPE_SELECT" value="<%= SPNConstants.SPN_BUTTON_TYPE_SELECT %>" />
<c:set var="BUTTON_TYPE_UPDATE" value="<%= SPNConstants.SPN_BUTTON_TYPE_UPDATE %>" />

<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
	<title>ServiceLive - SPN Monitor</title>
	<link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/screen.css" media="screen, projection">
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/superfish.css" media="screen, projection">
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/spn-invitation.css" media="screen, projection">
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/select-provider-network.css" media="screen, projection">
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/ui.tabs.css" media="screen, projection">
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/jqueryui/jquery-ui-1.7.2.custom.css" media="screen, projection">
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/modals.css" media="screen, projection">
	<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/superfish.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/supersubs.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery-form.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/ui.core.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.dataTables.js"></script>
				<!-- added the below css for changing the style in tab-->
		
	<style>

			.ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active, 
	a.ui-button:active, .ui-button:active, .ui-button.ui-state-active:hover {
    border: 1px solid #FFF; 
    background: #333333;
    font-weight: normal;
    color: #ffffff;
}

	.ui-tabsd {
    padding: 0.2em;
}
.ui-tabsd .ui-tabsd-nav {
    list-style: none outside none;
    padding: 0.2em 0.2em 0;
    /* position: relative; */
}
.ui-tabsd .ui-tabsd-nav li {
    border-bottom-width: 0 !important;
    float: left;
    margin: 0 0.2em -1px 0;
    padding: 0;
    position: relative;
}
.ui-tabsd .ui-tabsd-nav li a {
    float: left;
    padding: 0.5em 1em;
    text-decoration: none;
}
.ui-tabsd .ui-tabsd-nav li.ui-tabsd-selected {
    border-bottom-width: 0;
    padding-bottom: 1px;
}
.ui-tabsd .ui-tabsd-nav li.ui-tabsd-selected a, .ui-tabsd .ui-tabsd-nav li.ui-state-disabled a, .ui-tabsd .ui-tabsd-nav li.ui-state-processing a {
    cursor: text;
}
.ui-tabsd .ui-tabsd-nav li a, .ui-tabsd.ui-tabsd-collapsible .ui-tabsd-nav li.ui-tabsd-selected a {
    cursor: pointer;
}
.ui-tabsd .ui-tabsd-panel {
    background: none repeat scroll 0 0 transparent;
    border-width: 0;
    display: block;
    padding: 1em 1.4em;
}
.ui-tabsd .ui-tabsd-hide {
    display: none !important;
}



</style>
<link href="${staticContextPath}/css/font-awesome.min.css"      rel="stylesheet" />
<link href="${staticContextPath}/css/font-awesome-ie7.min.css"  rel="stylesheet" />
	<style type="text/css">
	.ie7 .bannerDiv{margin-left:-1150px;}
			</style>
	<script type="text/javascript">
		var isSubmitted = false;
		var uploadedInd = '';
		$(document).ready(function() {
			
			$('.expandSpnRow').click(function() {
				var spnId = $(this).children('[name=spnId]').val();
				
				togglePlusMinusImage($(this).children('.plus-image,.minus-image'));
				
				var expandAreaId = "#" + spnId + "_expandArea";
				
				$(expandAreaId).toggle();
				//alert('spnId: ' + spnId + ' expandAreaId: ' + expandAreaId);
				
				// Chris - If you want to revert to the 'old' non-Ajax way of doing things, comment out the next 3 lines.
				if ($(this).children('.minus-image').length > 0)
				{
					$(expandAreaId).load('spnMonitorAction_loadSPNAjax.action',  { 'spnId': spnId});
				}				
				return false;//so that page won't jump
			});
			
			$('.tabs > ul').tabs();
			
		    $('#docUploadForm').ajaxForm({
		    	dataType: 'json',
		        success: processJsonResponse
		    });
		    $().ajaxError(function(ev, opts, xhr, msg, ex) {
		        $('#uploadErrorMessages').html('Please Attach a file no larger than 30 MB');
				$('#uploadErrorMessages').show();
			});
			
			//alert('selectedSpnId:' + ${selectedSpnId});
			<c:if test="${not empty selectedSpnId}">
				$("#${selectedSpnId}_expandArea").load('spnMonitorAction_loadSPNAjax.action',  { 'spnId': ${selectedSpnId}});
				$("#${selectedSpnId}_expandArea").show();
				togglePlusMinusImage($('#${selectedSpnId}_expandLink').children('.plus-image,.minus-image'));
			</c:if>
			
			$('#uploadDocument').jqm({modal: true, toTop: true});
		});
		
		
		function processJsonResponse(data)
		{
		
			if (data.error == "") {	
		    	jQuery(document).ready(function($) {
					// Refresh current SPN, current document row
					$('#DocStatus_'+$('#uploadBuyerDocId').val()+'_'+$('#uploadSpnId').val()).html("<img src='${staticContextPath}/images/common/status-white.png' alt='Pending Approval'/><br/><p class='sm'>"+data.docStateDesc+"</p>");
					if ($('#uploadButtonType').val() == '${BUTTON_TYPE_SELECT}') {
						$('#DocMessage_'+$('#uploadBuyerDocId').val()+'_'+$('#uploadSpnId').val()).html("<div class='nocomment'><strong>Document On File:</strong><span id='OnFileDoc_"+$('#uploadBuyerDocId').val()+"_"+$('#uploadSpnId').val()+
								"'><a href='${contextPath}/spnMonitorAction_loadDocument.action?&docID="+data.provFirmUplDocId+"' target='_docWindow'>"+data.docFileName+
								"</a></span></div>");
					} else {
						$('#OnFileDoc_'+$('#uploadBuyerDocId').val()+'_'+$('#uploadSpnId').val()).html("<a href='${contextPath}/spnMonitorAction_loadDocument.action?&docID="+data.provFirmUplDocId+"' target='_docWindow'>"+data.docFileName+"</a>");
					}
					$('#DocAction_'+$('#uploadBuyerDocId').val()+'_'+$('#uploadSpnId').val()).html("<a class='fileedit' href='#' onClick='showDocumentUploadModal(\""+$('#uploadDtitle').val()+"\",\""+$('#uploadSpnBuyerId').val()+"\",\""+$('#uploadBuyerDocId').val()+"\",\"${BUTTON_TYPE_UPDATE}\",\""+data.provFirmUplDocId+"\",\""+data.docFileName+"\",\""+$('#uploadSpnId').val()+"\"); return false;'>Update File</a>");
					
					$('#uploadDocument').jqmHide();
				});
			} else {
				$('#uploadErrorMessages').html(data.error);
				$('#uploadErrorMessages').show();
				//document.getElementById("uploadErrorMessages").innerText = data.error;
			}
		}
		
		function togglePlusMinusImage(element)
		{
				if (element.hasClass('plus-image'))
				{
					element.addClass('minus-image');
					element.removeClass('plus-image');
				}
				else if (element.hasClass('minus-image'))
				{
					element.addClass('plus-image');
					element.removeClass('minus-image');
				}
		}
		
		function showDocumentUploadModal(dtitle,buyerId,buyerDocId,buttonType,proDocId,docName,spnId)
		{
			isSubmitted = false;
			jQuery(document).ready(function($) {	
				$('#uploadErrorMessages').hide();
				$('#uploadErrorMessages').val('');
				$('#fileToUpload').val('');
				
				$("#docTitle").html('Document Title : '+dtitle);
				if(buttonType == 'Update'){
					var docHTML = "<p class='paddingBtm'><strong>Document Name:</strong> <span> <a href='jsp/spn/spnMonitorAction_loadDocument.action?docID="+proDocId+"' target='_blank'>"+docName+"</a></span></p>";
					$('#docDisplay').html(docHTML);
					$('#docDisplay').show();
				}
				
				$("#uploadDtitle").val(dtitle);
				$("#uploadBuyerId").val(buyerId);
				$("#uploadBuyerDocId").val(buyerDocId);
				$("#uploadButtonType").val(buttonType);	
				$("#uploadProDocId").val(proDocId);
				$("#uploadSpnId").val(spnId);
				$("#uploadSpnBuyerId").val(buyerId);
				
				
				$('#uploadDocument').jqmShow();
			});
		}
		
		function checkExtension(filename,submitId){
			var hash = { '.jpg':1 , '.pdf':1, '.doc':1, '.gif':1, '.tiff':1, '.png':1, '.bmp':1, '.txt':1 };
		  	uploadedInd = "";
		  	
			var lastDotId = filename.lastIndexOf(".");
			if (lastDotId > -1) {
				var ext = filename.substr(lastDotId);
				ext = ext.toLowerCase();
				if (hash[ext]) {
					uploadedInd = "uploaded";
					$('#uploadErrorMessages').html('');
					$('#uploadErrorMessages').hide();
					return true;
				}
			}
			
			var docErrorMsg = "Please upload either a .jpg, .pdf, .doc, .gif, .tiff, .png, .bmp  or .txt.<br/>";
			$('#uploadErrorMessages').html(docErrorMsg);
			$('#uploadErrorMessages').show();
			return false;
		}
		
		// Code has been changed under Master JIRA Ticket SL-18757
		function validateUploadedFile()
		{
		    isSubmitted=false;
			if(!isSubmitted){
			isSubmitted = true;	
				if(uploadedInd != 'uploaded'){
					var docErrorMsg = 'Please attach your documents using the browse and upload buttons.';
					$('#uploadErrorMessages').html(docErrorMsg);
					$('#uploadErrorMessages').show();
					return false;
				}else{
					return true; 
				}
			}else{
				return false;
			}
		}
		
		function submitApplication(spnID)
		{
			jQuery(document).ready(function($) {
				var err=checkDocComplete(spnID);
				var err2=checkAgreeDocs(spnID);
				var name='errPlaceHolder_'+spnID;
				if(err == 0 && err2 == 0){
					//$('#mainMonitor-spnId').val(spnID);
					//document.mainMonitor.spnId.value=spnID;
					//alert(document.mainMonitor.spnId.value);
					//document.mainMonitor.spnId.value = spnID;
					//document.getElementById('mainMonitor-spnId').value = spnID;
					//alert($('#mainMonitor-spnId').val());
					//document.mainMonitor.action='spnSubmitBuyerAgreement.action';
					var formId = 'mainMonitor-' + spnID;
					document.getElementById(formId).submit();
					//document.mainMonitor.submit();
				} else if (err2 != 0) {
					document.getElementById(name).innerHTML = 'Please complete each Buyer Agreement to continue.';
					document.getElementById(name).style.display='block';    
				}else if (err != 0) {
					document.getElementById(name).innerHTML = 'Please upload files for incomplete documents to continue.';
					document.getElementById(name).style.display='block';    
				}
			});
		}
		
		function checkDocComplete(spnId)
		{		
			var errorInd = 0;
			var incompleteDocs = document.getElementsByName(spnId + '_buyer_doc');
			for(i = 0;i<incompleteDocs.length;i++)
			{
				var btnValue = incompleteDocs[i].value;                      
				if(btnValue == 'Select')
				{
					errorInd = 1;
					break;
				}
			}  
	            
			return errorInd;
		}
		function checkAgreeDocs(spnId)
		{
			var returnVal = 0;
			jQuery(document).ready(function($) {
				var classId = '.' + spnId + '_agree_docs';
				$(classId).each(function() {
					if ($(this).val() == '0')
					{
						returnVal = -1;
					}
				});
			});
			
			return returnVal;
		}
		
		function showProviderRequirementsPopUp(spnId)
        {         		
			document.getElementById('providerRequirements').innerHTML = 'Loading...';  
			jQuery(document).ready(function($) {                    
			$('#providerRequirements').jqm({ajax:'spnMonitorAction_getProviderRequirementsList.action?&spnID='+spnId ,toTop: true,overlay: '1'});
				$('#providerRequirements').jqmShow();   
			});
		} 
		
		function showCompanyRequirementsPopUp(spnId)
		{         		
			document.getElementById('companyRequirements').innerHTML = 'Loading...';   
			jQuery(document).ready(function($) {                		                 
				$('#companyRequirements').jqm({ajax:'spnMonitorAction_getCompanyRequirementsList.action?&spnID='+spnId ,toTop: true,modal: false,overlay: '1'});
				$('#companyRequirements').jqmShow();                  
			});
		}
		
		function hideDiv(divName){
			jQuery(document).ready(function($) { 
				$('#' + divName).jqmHide();
			});
		}
		
		function loadMonitorTab()
		{
			alert('ok');	
			jQuery('#monitor').load("spnMonitorAction_loadMonitorAjax.action"); 		
		}
		
		function loadComplianceTab()
		{
			jQuery('#complianceTab').addClass("ui-tabs-selected ui-state-active");
			jQuery('#spnTab').removeClass("ui-tabs-selected ui-state-active");
			jQuery('#backgroundTab').removeClass("ui-tabs-selected ui-state-active");
            jQuery('#monitor').html('<img src="${staticContextPath}/images/loading.gif" width="772px" height="560px"/>');
         

					jQuery('#monitor').load("spnMonitorAction_viewComplianceAjax.action?complianceType=firmCompliance"); 		
		}
		
		
		function loadBackgroundCheckProvider()
		{
			jQuery('#backgroundTab').addClass("ui-tabs-selected ui-state-active");
			jQuery('#spnTab').removeClass("ui-tabs-selected ui-state-active");
			jQuery('#complianceTab').removeClass("ui-tabs-selected ui-state-active");
              jQuery('#monitor').html('<img src="${staticContextPath}/images/loading.gif" width="772px" height="560px"/>');
              jQuery('#monitor').load("spnMonitorAction_searchBackgroundInformationCountAjax.action"); 		
		}
		
		
	function refreshParent() {
        window.opener.location.reload();
    }
		
	</script>	
</head>	
	
<body>

<div id="wrap" class="spnmonitorpage">
<tiles:insertDefinition name="blueprint.base.header" />
<tiles:insertDefinition name="blueprint.base.navigation" />
<div class="modalOverlay"></div>
<div id="content" class="span-24 clearfix">
<div id="primary" class="span-24 first last">
<div class="content">
	<h2 class="pageTitle"><p style="text-align:left;"><span style="font-family:Arial;font-size:23px;font-weight:normal;font-style:normal;text-decoration:none;color:#0099FF;">Administrator Office</span><span style="font-family:Arial;font-size:23px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;"> Select Provider Network (SPN)</span></p></h2>
	
	
	
	<div id="tabs">
	<div style="border-bottom:0px;" class="ui-tabsd ui-widget ui-widget-content ui-corner-all">
						<ul class="ui-tabsd-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
							<li id="spnTab" style="cursor: pointer;" class="ui-state-default ui-corner-top ui-tabsd-selected ui-state-active"><a href="${contextPath}/spnMonitorAction_loadSPNMonitor.action"><span><abbr title="Select Provider Network">SPN</abbr> Monitor</span></a></li>
							<li id="complianceTab" class="ui-state-default ui-corner-top" ><a onclick="loadComplianceTab();"><span>Member Compliance Statistics</span></a></li>
							<li id="backgroundTab" class="ui-state-default ui-corner-top" ><a onclick="loadBackgroundCheckProvider();"><span>Background Check Status</span></a></li>
						</ul>
						</div>
						<div id="monitor">
	<jsp:include page="spnMainMonitorTable.jsp"></jsp:include>
	</div>
	<div id="complianceTab">
	</div>
	
	
</div>
</div>
</div>

<tiles:insertDefinition name="blueprint.base.footer" />
</div>

<div id="uploadDocument" class="jqmWindow">
<div class="documentsModal">
	<div class="modal-header">
		<h5>Upload Documents</h5>
	</div>
	<div class="content clearfix" style="text-align: left;">		
		<div id="uploadErrorMessages" class="error" style='display:none'></div>
		
	<form id="docUploadForm" action="spnMonitorAction_uploadDocument.action" method="POST" enctype="multipart/form-data">
	<div class="clearfix">
		<div id="docTitle" style="display:block;"></div>
		<div id="docDisplay" style="display:none;"></div>
			<input type="hidden" name="buyerId" id="uploadBuyerId" value=""/>
			<input type="hidden" name="dtitle" id="uploadDtitle" value=""/>
			<input type="hidden" name="buyerDocId" id="uploadBuyerDocId" value=""/>
			<input type="hidden" name="proDocId" id="uploadProDocId" value=""/>
			<input type="hidden" name="buttonType" id="uploadButtonType" value=""/>
			<input type="hidden" name="spnId" id="uploadSpnId" value=""/>
			<input type="hidden" name="spnBuyerId" id="uploadSpnBuyerId" value=""/>
		<p>
		    <input id="fileToUpload" type="file" name="fileToUpload" onchange="checkExtension(this.value,'upload')">
			<input type="hidden" id="uploadFileTitle" value=""/> 
			<input type="hidden" id="uploadFileType" value=""/>
			<input type="hidden" id="uploadFileDesc" value=""/>
		</p>		
		<p>	<small>Only files that have not been approved may be updated.<br/>
			Uploading a new file will automatically replace an existing one.</small>
		</p>
		<p class="note">
			<small><strong>Accepted File Types:</strong>.jpg | .pdf | .doc | .gif | .tiff | .png | .bmp | .txt<br/>
			<strong>Max. file size:</strong> 30 MB</small>
		</p>
	</div>
	<div class="clearfix" style="margin-top: 20px;">
		<a href="#" class="cancel jqmClose left"  style="color:#8a1f11">Cancel</a>
		<input type="submit" class="button action right" id="upload" value="Upload" onclick="return validateUploadedFile();"/>
	</div>
	</form>
	</div>
</div>
</div>

<div id="providerRequirements" class="jqmWindow popup_msg"></div>
<div id="companyRequirements" class="jqmWindow popup_msg"></div>

</body>
</html>
