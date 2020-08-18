<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<!-- acquity: modified meta tag to set charset -->
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	
	<title>ServiceLive - Create A Service Order</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

	
		
		<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js"></script>

		<script language="javascript" type="text/javascript">
			var djConfig = {
				isDebug: true, 
				parseOnLoad: true
			};
		</script>
		
		<style type="text/css">
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
			@import "${staticContextPath}/dojo/dijit/themes/tundra/tundra_rtl.css";
		</style>

		<!-- acquity: escape script tags with a forward slash, make sure they all have relationship and type set -->				
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTabPane-serviceLive.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />

		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />

		<!-- acquity: here is the new stylesheet, rename to whatever you'd like -->		
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
		<!--[if lt IE 8]>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
		<![endif]-->

		<!-- acquity: make sure they all have language and type set -->		
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/tooltip.js"></script>
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>


		<!-- acquity: here is the new js, please minify/pack the toolbox and rename as you wish -->
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>	
		<script type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>	

		
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/mootools.v1.11.js"></script>
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/nav.js"></script>

<script type="text/javascript">

			function validateFileName(field, method, docType){
				var failed = false;
				var fieldVal = document.getElementById(field).value;
								
				// clearAllValidationMsgs();
				if (fieldVal == null || fieldVal.length == 0){
					if(docType == 'photo'){
						document.getElementById('photoFileNameSelectionMsg').style.display = "inline";
					}else{
						document.getElementById('fileNameSelectionMsg').style.display = "inline";
					}
					
				}else{
				
					submitForm(method);
				}
			}


			function validateDocSelection(method){
				var failedValidation = false;
				var exists = document.getElementById("documentSelection");
				
				// clearAllValidationMsgs();

					if (method=='removeDocument'){				
						if ( window.confirm('Do you really want to delete this document ?') ){
							submitForm(method);
						}else{
							return false;
						}
					}else{
						submitForm(method);
					}
				}

			function clearAllValidationMsgs(){
				document.getElementById('documentSelectionMsg').style.display = "none";
				document.getElementById('fileNameSelectionMsg').style.display = "none";
				if (document.getElementById("actionError")){
					document.getElementById("actionError").style.display='none';
				}
			}			
			
			
			function validateDocSelectionWithDocId(method, docId){
				var failedValidation = false;
				// set hidden field documentSelection - for which viewDocument Servlet looks for
				document.getElementById('documentSelection').value = docId;				
				// clearAllValidationMsgs();

					if (method=='removeDocument'){				
						if ( window.confirm('Do you really want to delete this document ?') ){
							submitForm(method);
						}else{
							return false;
						}
					}else{
						if(method=='viewImage'){
							// set hidden field imageDocId - for which viewImage mehtod looks for
							document.getElementById('imageDocId').value = docId;	
						}
						submitForm(method);
					}
				}
				
			
			function openWindow(method, docId){
				document.getElementById('imageDocId').value = docId;
			    var url =  '${contextPath}' + "/ImageView?imageDocId=" + docId;
			    
			   	window.open (url,
					"mywindow","location=1,status=1,scrollbars=1,width=500,height=500"); 

			}
			
			
			function submitForm(method){
				var loadForm = document.getElementById('csoDocumentsAndPhotosAction');
				
				if (method=='viewDocument'){
					loadForm.action = '${contextPath}' + "/SODocumentView"; 
				} else {
					loadForm.action = '${contextPath}' + "/csoDocumentsAndPhotos_" + method + ".action";
				}
				try {
				loadForm.submit();
				} catch (error) {
				alert ('An error occurred while processing your document request. Check the filename and filepath.');
				}
			}
			
</script>

<script>

	jQuery.noConflict(); 

	jQuery(document).ready(function() {
	
	jQuery('#photoUpload').jqm({modal:false, overlay:false, trigger: 'a.jqModal'});
    

	 });
</script>




		
</head>
<body class="noBg"><br />
<s:form action="csoDocumentsAndPhotosAction" id="csoDocumentsAndPhotosAction" 
        theme="simple" name="docPhotoForm"enctype="multipart/form-data" method="POST">&nbsp; 
        <input type="hidden" id="documentSelection" name="documentSelection"/>
        <input type="hidden" id="imageDocId" name="imageDocId"/>
        
		<s:if test="hasActionErrors()">
			<div style="margin: 10px 0pt;" id="actionError"
					class="errorBox clearfix">
				<s:actionerror />
			</div>
		</s:if>        

        <c:if test="${param.simplePageName == 'review'}">
        	<h3 style="color:#F9B020; font-size:14px;">Uploaded Project Photos</h3>
        </c:if>
        <c:if test="${!(param.simplePageName == 'review')}">
        	<h3 style="color:#F9B020; font-size:14px;">Upload Project Photos</h3>
        	<p style="margin: 0px; padding: 0px; padding-bottom: 5px; font-style:italic;">
					<span style="color: red; style-style:italic;">Note:</span>5 MB total maximum photo size</p>
        </c:if>
			<div class="defined media gallery clearfix">
				<ul>
				
				<c:forEach var="photo" items="${photoDocuments}" varStatus="dtoIndex">
					<li>
						
						<c:if test="${photo.blobBytes  == null}">
							<a> 
							<img title="No Photo Yet"  src="${staticContextPath}/images/simple/blank.png" alt="Photo 5" /></a><br />
							<c:if test="${!(param.simplePageName == 'review')}">
								<!--  <input type="button" id="addPhotoBtn" "img_${dtoIndex.count}" class="jqModal"  value="Upload"/> -->	
								<!-- <input type="button" id="addPhotoBtn_${dtoIndex.count}" class="jqModal"  value="Upload"/> --> 
								
							<a href="#" class="jqModal">Add </a>
							</c:if>	
						</c:if>
						<c:if test="${photo.blobBytes  != null}">
	        				<a href="javascript:openWindow('displayLogoDoc', ${photo.documentId})">
	        				<img src="${contextPath}/csoDocumentsAndPhotos_displayLogoDoc.action?imageDocId=${photo.documentId}"
														alt="default Image" /><br /> </a>
							<c:if test="${!(param.simplePageName == 'review')}">							
								<a onclick="javascript:validateDocSelectionWithDocId('removeDocument', ${photo.documentId});"  href="#" class="ex req" title="Remove Photo">Delete</a>
							</c:if>	
						</c:if>

					
					</li>
				</c:forEach>

				</ul>			
			</div>
			<div  style="margin: 10px 0pt; display:none;" id="fileNameSelectionMsg" class="errorBox clearfix">
				<fmt:message bundle="${serviceliveCopyBundle}"  key="document.validation.selection.attach.msg" />
			</div>
			
			<c:if test="${param.simplePageName == 'review'}">
        		<h3 style="color:#F9B020; font-size:14px; margin-top: 20px;">Uploaded Project Documents</h3>
        	</c:if>
			
			<c:if test="${!(param.simplePageName == 'review')}">
				<h3 style="color:#F9B020; font-size:14px; margin-top: 20px;">Upload Project Documents</h3>
			</c:if>

			<div class="defined dox">
				<p style="margin: 0px; padding: 0px; padding-bottom: 5px;">
					<span style="color: red;">Note:</span> <strong>The documents you upload are private.</strong><br />Only the Provider who accepts your Service Order can see them.
				</p>
			
			<c:if test="${!(param.simplePageName == 'review')}">
				<div class="clearfix" style="padding: 0 0 15px 0;">
				<!-- <input type="file"  class="shadowBox" />   -->	
					<s:file name="document.upload" id="document.upload" />
					<!--  <input type="submit" style="font-size:10px;" value="Upload"> -->
					<input type="button" id="attachDocumentBtn" 
					  onclick="validateFileName('document.upload', 'documentUpload', 'doc')"
					  value="Upload"/>
					 </div>
			</c:if>
			
			<h4 style="color:#F9B020; font-size:14px;">Uploaded Documents</h4>
			
			<ul>
			<c:forEach var="doc" items="${documents}" varStatus="dtoIndex">
				<input type="hidden" name="documentSelection${doc.documentId}" id="documentSelection${doc.documentId}"
													value="${doc.documentId}" />
					<li><a href="javascript:validateDocSelectionWithDocId('viewDocument', ${doc.documentId})">${doc.name}</a><span class="resetText">
					<c:if test="${!(param.simplePageName == 'review')}">
						(<a  href="#"  onclick="javascript:validateDocSelectionWithDocId('removeDocument', ${doc.documentId});"class="ex req">Delete</a>)
					</c:if>	
					</span>
					</li>
			</c:forEach>
			</ul>
			</div>
			
			<div class="jqmWindow"  id="photoUpload" style="width: 550px; margin-left: -275px;">
			<div class="modalHeader">
			<a href="#" class="jqmClose">Close</a>
			</div>
			<div class="modalContent">
					        
		       	<div  style="margin: 10px 0pt; display:none;" id="photoFileNameSelectionMsg" class="errorBox clearfix">
					<fmt:message bundle="${serviceliveCopyBundle}"  key="document.validation.selection.attach.msg" />
				</div>
				<div class="textcenter">
						<a href="http://community.servicelive.com/docs/ap.pdf" target="_blank">Help?</a>
						<s:file name="photoDoc.upload" id="photoDoc.upload" />
						<input type="button" id="attachDocumentBtn" 
						  onclick="validateFileName('photoDoc.upload', 'photoDocUpload', 'photo')"
						  value="Upload"/>
				</div>
			</div>
			</div>	
</s:form>




</body>
</html>
