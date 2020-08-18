<jsp:directive.page
	import="com.newco.marketplace.interfaces.OrderConstants" />
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="org.owasp.esapi.ESAPI"%>

<c:set var="so_status" scope="request"
	value="${THE_SERVICE_ORDER_STATUS_CODE}" />
<c:set var="so" scope="request"
	value="<%=request.getAttribute("THE_SERVICE_ORDER")%>" />
<c:set var="active_status" scope="request"
	value="<%=new Integer(OrderConstants.ACTIVE_STATUS)%>" />
<c:set var="problem_status" scope="request"
	value="<%=new Integer(OrderConstants.PROBLEM_STATUS)%>" />
<c:set var="completed_status" scope="request"
	value="<%=new Integer(OrderConstants.COMPLETED_STATUS)%>" />
<c:set var="voided_status" scope="request"
	value="<%=new Integer(OrderConstants.VOIDED_STATUS)%>" />
<c:set var="canceled_status" scope="request"
	value="<%=new Integer(OrderConstants.CANCELLED_STATUS)%>" />
<c:set var="closed_status" scope="request"
	value="<%=new Integer(OrderConstants.CLOSED_STATUS)%>" />
<c:set var="accepted_status" scope="request"
	value="<%=new Integer(OrderConstants.ACCEPTED_STATUS)%>" />
<c:set var="draft_status" scope="request"
	value="<%=new Integer(OrderConstants.DRAFT_STATUS)%>" />
<c:set var="routed_status" scope="request"
	value="<%=new Integer(OrderConstants.ROUTED_STATUS)%>" />
<c:set var="expired_status" scope="request"
	value="<%=new Integer(OrderConstants.EXPIRED_STATUS)%>" />
<c:set var="doc_Source" scope="request"
	value="<%=request.getParameter("docSource")%>" />
<c:set var="doc_SourceVal" scope="request"
	value="<%=(OrderConstants.COMPLETE_FOR_PAYMENT)%>" />
<c:set var="isAutocloseOn" scope="request"
	value="<%=request.getAttribute("isAutocloseOn")%>" />
<c:choose>
	<c:when
		test="${param.priceModel == 'ZERO_PRICE_BID' || priceModel == 'ZERO_PRICE_BID' || param.priceModel == 'BULLETIN' || priceModel == 'BULLETIN'}">
		<c:set var="bidTabView" value="true" />
	</c:when>
	<c:otherwise>
		<c:set var="bidTabView" value="false" />
	</c:otherwise>
</c:choose>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<c:set var="roleId" scope="request" value="${roleType}" />
<c:set var="provider_role" scope="request"
	value="<%=new Integer(OrderConstants.PROVIDER_ROLEID)%>" />
<c:set var="fileUpload_status" scope="request"
	value="<%=request.getAttribute(OrderConstants.FILE_UPLOAD_STATUS)%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<style type="text/css">
@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css"
	;
</style>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />

		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/global.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/sears_custom.css" />
			
		<c:if test="${bidTabView}">
			<style type="text/css">
input,textarea {
	margin: 4px;
}

fieldset {
	border: 0;
}

input.action {
	width: 75px;
	border-radius: 5px;
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
	background: transparent
		url(${staticContextPath}/images/common/button-action-bg.png) repeat
		scroll 0 0;
	border: 2px solid #ccc;
	color: #222;
	cursor: pointer;
	display: block;
	font-family: Arial, Tahoma, sans-serif;
	font-size: 1em;
	font-weight: bold;
	padding: 2px 8px;
	text-align: center;
	text-transform: uppercase;
}

input.action:hover {
	background: transparent
		url(${staticContextPath}/images/common/button-action-hover.png) repeat
		scroll 0 0;
	color: #000000;
}

input.white {
	background: none;
	padding: 2px 3px;
}

input.white:hover {
	background: none;
	background-color: #f1f1f1;
	padding: 2px 3px;
}

.errorBox li {
	background: url(${staticContextPath}/images/icons/errorIcon.gif)
		no-repeat 0 0;
	list-style-type: none;
	margin: 4px 2px;
	padding-left: 15px;
}

a.removeLink:link {
	font-weight: bold;
	color: red;
	text-decoration: none;
}

a.removeLink:visited {
	font-weight: bold;
	color: red;
	text-decoration: none;
}

a.removeLink:hover {
	font-weight: bold;
	color: red;
	text-decoration: underline;
}

a.removeLink:active {
	font-weight: bold;
	color: red;
	text-decoration: none;
}
</style>
		</c:if>

	<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
	<script type="text/javascript">
		jQuery.noConflict(); //reassign "$" to prototype
	</script>
	<script type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
	
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqmodal/jqModal.js"></script>
	<script type="text/javascript">
            //jQuery.noConflict();
            dojo.require("newco.jsutils");
			var _documentSelectionChecked = false;
			function checkDocUploadDisp() {
				if (${roleId == provider_role}) {
					if (${so_status != active_status} && ${so_status != problem_status} && ${so_status != completed_status}&& 
						${so_status != closed_status}&& ${so_status != accepted_status}&& ${so_status != canceled_status} ) {
						document.getElementById('doc_upload_file_sel_div').style.display = "none";
						document.getElementById('removeDocumentBtn').style.display = "none";
					}
					}
				}
		   dojo.addOnLoad(function(){
                if(document.getElementById("docsDiv")){
                     document.getElementById("docsDiv").style.scroll= true;
			}
           });
			function doAction() {
				document.getElementById("docPhotoForm").submit();
			}

			function documentSelected(docInCurrentVisit) {				
				_documentSelectionChecked = true;				
				 if(docInCurrentVisit == false){ 
                 	document.getElementById("removeDocumentBtn").style.visibility = 'hidden'; 
                 }else if (!<c:out value="${bidTabView}"/>){ 
                    document.getElementById("removeDocumentBtn").style.visibility = 'visible'; 
                 }
				clearAllValidationMsgs();
			}

			function makeSelection(arg, arg2){
				var dForm = document.getElementById('soDocumentsAndPhotos');
				var index = document.getElementById("soDocumentsAndPhotos").elements.length;

				for (i=1; i < index; i++){
					var dElement = document.getElementById("soDocumentsAndPhotos").elements[i];
					console.log(dElement.type);
						if (dElement.type == 'radio' && dElement.value == arg){
						
							//dElement.checked = true;
							jQuery(dElement).click();
						}
					}
				validateDocSelection(arg2);
				}
			function validateVideo(){			
				if(document.getElementById("categoryId").value==8){				
				var videoId = document.getElementById("videoFileId").value;						
				//document.getElementById("srcId").src="http://www.youtube.com/v/"+videoId+"&hl=en&fs=1&rel=0";
				//document.getElementById("movieId").value="http://www.youtube.com/v/"+videoId+"&hl=en&fs=1&rel=0";
				showVideo(videoId);
					
				}else{
					
					validateDocSelection('viewDocument');
				}
			
			}
			
	   
			function showVideo(videoId){
				window.parent.showVideoTop(videoId);
			}
			
			function assignCategory(catId,fileName){				
				document.getElementById("categoryId").value=catId;
				document.getElementById("videoFileId").value=fileName;
				
			}
			function assignFileName(catId){				
				document.getElementById("videoFileId").value=catId;				
			}
			function validateDocSelection(method){
				var failedValidation = false;
				var exists = document.getElementById("documentSelection");
				
				clearAllValidationMsgs();
				
				if(!${so.buyerID=='1000'||so.buyerID=='3000'}){
                	if(!${(null != documentTypes) && fn:length(documentTypes)>0}){
                    	if (exists){
                        	if (_documentSelectionChecked==false){
                            	document.getElementById('documentSelectionMsg').style.display = "inline";
                                failedValidation = true;
                            }
                        }else{
                         	document.getElementById('documentSelectionMsg').style.display = "inline";
                            failedValidation = true;
                    	}
                	}                              
                } 

				if (failedValidation==false){
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
			}	
			
			function submitForm(method){
			<% String docSourceVar= (String) request.getParameter("docSource");
					 String docSourceNew=ESAPI.encoder().canonicalize(docSourceVar);
	 				 String docSourceVuln=ESAPI.encoder().encodeForHTML(docSourceNew);%>
				var loadForm = document.getElementById('soDocumentsAndPhotos');
				var secToken = document.getElementById("ss").value;				
				var docSource = "<%=docSourceVuln%>";
				
				if (method=='viewDocument'){
					loadForm.action = '${contextPath}' + "/SODocumentView"; 
				} else {
					
					//if(document.getElementById("docCategoryId"))
					//var docCategoryId = document.getElementById("docCategoryId").value;
					loadForm.action = '${contextPath}' + "/soDocumentsAndPhotos_" + method + ".action?docSource=" + docSource + "&ss=" + secToken;
				}
				try {
				loadForm.submit();
				} catch (error) {
				alert ('An error occurred while processing your document request. Check the filename and filepath.');
				}
			}
			
			function validateFileName(field, method){
				var failed = false;
				var fieldVal = document.getElementById(field).value;
				clearAllValidationMsgs();
				if(${(null != documentTypes) && fn:length(documentTypes)>0}){
					if(document.getElementById("docTitle")){
							var docCategory=document.getElementById("docTitle").value;
							if(docCategory == -1 || docCategory.length == 0){
							document.getElementById('docCategorySelectionMsg').style.display = "";
							}
							 else if (fieldVal == null || fieldVal.length == 0){
							 document.getElementById('fileNameSelectionMsg').style.display = "";
							}else{
							submitForm(method);
							}
					}
					else{
					 		 if (fieldVal == null || fieldVal.length == 0){
							 document.getElementById('fileNameSelectionMsg').style.display = "";
							}else{
							submitForm(method);
							}
					}
				}
				else{
					if (fieldVal == null || fieldVal.length == 0){
						document.getElementById('fileNameSelectionMsg').style.display = "";
					}else{
						submitForm(method);
					}
				}
			}
			
			function validateFiles(count,method){
				clearAllValidationMsgs();
				var flag = 0;
				for(i=1; i<=count; i++){
					if(document.getElementById('doc'+i).checked == true){
					 	flag = 1;
					 	break;
					}
				}if(0 == flag){
					document.getElementById('fileNameSelectionMsg').style.display = "";
				 }else{
				 	submitForm(method);
				 }
			}
			
			
		function assignValueFordocumentSelection(docId){
			document.getElementById("documentSelection").value=docId;
			}
			
			
		function clearAllValidationMsgs(){
				if (document.getElementById("documentSelectionMsg")){
					document.getElementById('documentSelectionMsg').style.display = "none";
				}
				if (document.getElementById("fileNameSelectionMsg")){	
					document.getElementById('fileNameSelectionMsg').style.display = "none";
				}	
				if (document.getElementById("actionError")){
					document.getElementById("actionError").style.display='none';
				}
				if(document.getElementById("docCategorySelectionMsg")){
					document.getElementById('docCategorySelectionMsg').style.display = "none";
				}
			}
			
		function setTitle(){
			var title = document.getElementById('docTitle').options[document.getElementById('docTitle').selectedIndex].text;
			document.getElementById('documentTitle').value=title;
		
		}

			jQuery(document).ready(function($){
				if(${tab != 'review'}){
				top.document.getElementById('inner_document_grid').height = $(document.body).height();
			  //var docSrc = document.getElementById('docSrc').value;	
			  //changed to remove script error in IE8.
			    var docSrc = jQuery('#docSrc').val();	
				if(docSrc == 'files'){
					jQuery('#doc_upload_previous_files').show();
					jQuery('#doc_upload_from_comp').hide();		
					jQuery('#attachDocumentBtn').hide();
					jQuery('#from_computer').css('text-decoration', 'underline');
					jQuery('#from_pre_files').css('text-decoration', 'none');	
				}
				else if(docSrc == 'comp'){
					jQuery('#doc_upload_from_comp').show();
					jQuery('#doc_upload_previous_files').hide();
					jQuery('#attachDocumentBtn').show();
					jQuery('#from_pre_files').css('text-decoration', 'underline');
					jQuery('#from_computer').css('text-decoration', 'none');
				}
				else{
					jQuery('#from_computer').css('text-decoration', 'none');
					jQuery('#from_pre_files').css('text-decoration', 'underline');
					jQuery('#docSrc').val("comp");
				}
				jQuery('#from_computer').click(function()
				{
					jQuery('#doc_upload_from_comp').show();
					jQuery('#doc_upload_previous_files').hide();
					jQuery('#attachDocumentBtn').show();
					jQuery('#from_pre_files').css('text-decoration', 'underline');
					jQuery('#from_computer').css('text-decoration', 'none');
					jQuery('#docSrc').val("comp");
				});
				jQuery('#from_pre_files').click(function()
				{
					jQuery('#doc_upload_previous_files').show();
					jQuery('#doc_upload_from_comp').hide();		
					jQuery('#attachDocumentBtn').hide();	
					jQuery('#from_computer').css('text-decoration', 'underline');
					jQuery('#from_pre_files').css('text-decoration', 'none');	
					jQuery('#docSrc').val("files");				
				});
				}
				
		});
	</script>
	</head>
	<body class="tundra noBg" id="documentsAndPhotosBody" style="color: #222222">
		<s:form action="soDocumentsAndPhotos" id="soDocumentsAndPhotos"
			theme="simple" name="docPhotoForm" enctype="multipart/form-data"
			method="POST">			
			<input type="hidden" name="serviceOrderStatus" value="${so_status}"/>
			<input type="hidden" name="serviceId" value="${SERVICE_ORDER_ID}"/>
			<s:hidden name="documentTitle" value="title" id="documentTitle"></s:hidden>
			<input type="hidden" name="ss" id="ss" value="${securityToken}">
			<c:choose>
				<c:when test="${bidTabView}">
					<c:if test="${tab != 'review'}">
							<c:choose>	
							<c:when test="${roleId == 2 || roleId == 3 || roleId == 5}">
							  	<jsp:include page="subpanel_upload_docs.jsp" /> 
							</c:when>
							<c:otherwise>
								<c:if test="${so_status != routed_status && so_status != draft_status && so_status != voided_status}">
									<c:choose><c:when test="${(null != documentTypes) && fn:length(documentTypes)>0}">
									   <jsp:include page="subpanel_upload_docs_type.jsp" />
									</c:when>
									<c:otherwise>
									<jsp:include page="subpanel_upload_docs.jsp" />
									</c:otherwise></c:choose>
								</c:if>
							</c:otherwise>
						</c:choose>

						<%-- Do not show the upload  if in routed/draft/voided status --%>
						<%--<c:if test="${so_status != routed_status && so_status != draft_status && so_status != voided_status}">							
						  	<jsp:include page="subpanel_upload_docs2.jsp" /> <%-- subpanel_upload_docs2.jsp --%> 
						<%--</c:if>--%> 

					</c:if>
					

					<%-- View/Download/Remove Docs --%>
					 <%--<jsp:include page="subpanel_view_download_remove_docs2.jsp" /> <%-- subpanel_view_download_remove_docs2.jsp --%> 
					<c:choose> <c:when test="${(null != documentTypes) && fn:length(documentTypes)>0}">
					<jsp:include page="subpanel_view_download_remove_docs_type.jsp" />
					</c:when>
					<c:otherwise>
					<jsp:include page="subpanel_view_download_remove_docs.jsp" />
					</c:otherwise></c:choose>
				</c:when>
				<c:otherwise>
					<c:if test="${tab != 'review'}">
						<c:choose>	
							<c:when test="${roleId == 2 || roleId == 3 || roleId == 5}">
							  	<jsp:include page="subpanel_upload_docs.jsp" /> 
							</c:when>
							<c:otherwise>
								<c:if test="${so_status != routed_status && so_status != draft_status && so_status != voided_status}">
									<c:choose><c:when test="${(null != documentTypes) && fn:length(documentTypes)>0}">
									   <jsp:include page="subpanel_upload_docs_type.jsp" />
									</c:when>
									<c:otherwise>
									<jsp:include page="subpanel_upload_docs.jsp" />
									</c:otherwise></c:choose>
								</c:if>
							</c:otherwise>
						</c:choose>
					</c:if>
					
					<c:choose><c:when test="${(null != documentTypes) && fn:length(documentTypes)>0}">
					<jsp:include page="subpanel_view_download_remove_docs_type.jsp" />
					</c:when>
					<c:otherwise>
					<jsp:include page="subpanel_view_download_remove_docs.jsp" />
					</c:otherwise></c:choose>
				</c:otherwise>
			</c:choose>

		</s:form>
	</body>
</html>
