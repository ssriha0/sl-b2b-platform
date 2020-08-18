
<jsp:directive.page
	import="com.newco.marketplace.interfaces.OrderConstants" /><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
	
<c:set var="roleId" scope="request" value="${roleType}" />
<c:set var="so_status" scope="request"
	value="${THE_SERVICE_ORDER_STATUS_CODE}" />
<c:set var="provider_role" scope="request"
	value="<%=new Integer(OrderConstants.PROVIDER_ROLEID)%>" />
<c:set var="active_status" scope="request"
	value="<%=new Integer(OrderConstants.ACTIVE_STATUS)%>" />
<c:set var="problem_status" scope="request"
	value="<%=new Integer(OrderConstants.PROBLEM_STATUS)%>" />
<c:set var="completed_status" scope="request"
	value="<%=new Integer(OrderConstants.COMPLETED_STATUS)%>" />


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />

		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/global.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/advanced_grid.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/sears_custom.css" />
		<style type="text/css">
@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
</style>

		<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
		<script type="text/javascript">
			var _documentSelectionChecked = false;
			
			function checkDocUploadDisp() {
				if (${roleId == provider_role}) {
					if (${so_status != active_status} && ${so_status != problem_status} && ${so_status != completed_status} ) {
						document.getElementById('doc_upload_file_sel_div').style.display = "none";
						document.getElementById('removeDocumentBtn').style.display = "none";
					}
				}
			}
			
			function doAction() {
				document.getElementById("docPhotoForm").submit();
			}

			function documentSelected() {
				_documentSelectionChecked = true;
				document.getElementById('documentSelectionMsg').style.display = "none";
				
			}
			
			function validateDocSelection(method){
				var failedValidation = false;
				var exists = document.getElementById("documentSelection");
				
				if (exists){
					if (_documentSelectionChecked==false){
						document.getElementById('documentSelectionMsg').style.display = "inline";
						failedValidation = true;
					}
				}else{
					document.getElementById('documentSelectionMsg').style.display = "inline";
					failedValidation = true;
				}
				
				if (failedValidation==false){
					var loadForm = document.getElementById('soDocumentsAndPhotos');
					loadForm.action = '${contextPath}' + "/soDocumentsAndPhotos_" + method + ".action";
					loadForm.submit();
				}
				
				
			}	
		</script>



	</head>
	<body onload="checkDocUploadDisp()">
		<s:form action="soDocumentsAndPhotos" id="soDocumentsAndPhotos"
			theme="simple" name="docPhotoForm" enctype="multipart/form-data"
			method="POST">


			<p>
				The following fiiles will be included with your service order. Photos will be visible to all providers who receive your order; documents will only be visible to the provider who accepts your order. Once your order has been accepted, you won't be able to remove these files. To remove files now, check the select box and then click 'remove'			
			</p>				
						


			<label style="display: none; color: red" id="documentSelectionMsg">
				Selection of a document is required.
			</label>
			<br>

			<div class="globalTableWrap">
			<table class="globalTableLook" cellpadding="0" cellspacing="0">
				<tr>
					<th class="col1 odd">
					</th>
					<th class="col2 even textleft">
						<a class="sortGridColumnUp" href="">File Name</a>
					</th>
					<th class="col3 odd last">
						<a class="sortGridColumnUp" href="">File Size</a>
					</th>
				</tr>
					<c:forEach var="doc" items="${documents}" varStatus="dtoIndex">
						<tr>
							<td class="col1 odd">
								<input type="radio" name="documentSelection"
									id="documentSelection" value="${doc.documentId}"
									onclick="documentSelected()" />
							</td>
							<td class="col2 even textleft">
								<strong>${doc.name}</strong>
								<p>
									${doc.desc}
								</p>
							</td>
							<td class="col3 odd last"> 
								<c:choose> 
								 <c:when test="${fn:containsIgnoreCase(doc.category, '8')}">
											N/A
								 </c:when>
								 <c:otherwise>
											${doc.size} Kb
								 </c:otherwise>
								</c:choose>		
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			
			<div class="clear"></div>
			<div class="clearfix">
				<p>


					<input type="button" onclick="validateDocSelection('viewDocument')"
						class="btn20Bevel"
						style="background-image: url(${staticContextPath}/images/btn/view.gif); width: 70px; height: 20px;"
						src="${staticContextPath}/images/common/spacer.gif" />

					<input type="button"
						onclick="validateDocSelection('downloadDocumentIFrame')"
						class="btn20Bevel"
						style="background-image: url(${staticContextPath}/images/btn/download.gif); width: 70px; height: 20px;"
						src="${staticContextPath}/images/common/spacer.gif" />

					<input type="button" id="removeDocumentBtn"
						onclick="validateDocSelection('removeDocument')"
						class="btn20Bevel"
						style="background-image: url(${staticContextPath}/images/btn/remove.gif); width: 70px; height: 20px;"
						src="${staticContextPath}/images/common/spacer.gif" />
				</p>
			</div>
		</s:form>
	</body>
</html>
