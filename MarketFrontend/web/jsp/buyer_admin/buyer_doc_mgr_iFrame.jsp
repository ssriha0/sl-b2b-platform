<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		
		<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
		<c:set var="roleId" scope="request" value="${roleType}" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
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
		


<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
 
	<style type="text/css">
		@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
		@import	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
	</style>

<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/bulletinBoard/main.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/admin.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />

<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/global.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
<link rel="stylesheet" type="text/css"	href="${staticContextPath}/css/dijitTabPane-serviceLive.css"/>
<link rel="stylesheet" type="text/css" 	href="${staticContextPath}/css/dijitTitlePane-serviceLive.css"/>
<link rel="stylesheet" type="text/css" 	href="${staticContextPath}/css/sears_custom.css"/>

</head>
<body class="tundra noBg" >
<s:form action="buyerAdminDocManager" id="buyerAdminDocManager" 
        theme="simple" name="docPhotoForm"enctype="multipart/form-data" method="POST">


<!-- NEW MODULE/ WIDGET-->
		<s:if test="hasActionErrors()"> 
			<div  style="margin: 10px 0pt;" id="actionError" class="errorBox clearfix">
				<s:actionerror />
			</div>
			<script type="text/javascript">
			document.getElementById('actionError').scrollIntoView(true);
			</script>
		</s:if>
		
		<div  style="margin: 10px 0pt; display:none;" id="fileNameSelectionMsg" class="errorBox clearfix">
			<fmt:message bundle="${serviceliveCopyBundle}"  key="document.validation.selection.attach.msg" />
		</div>
		
		<div  style="margin: 10px 0pt; display:none;" id="documentSelectionMsg" class="errorBox clearfix">
			<fmt:message bundle="${serviceliveCopyBundle}"  key="document.validation.selection.msg" />
		</div>
		
		<div  style="margin: 10px 0pt; display:none;" id="titleSelectionMsg" class="errorBox clearfix">
			<fmt:message bundle="${serviceliveCopyBundle}"  key="document.validation.selection.title.msg" />
		</div>

	<fmt:message bundle="${serviceliveCopyBundle}"  key="document.buyeradmin.panel.description"/>
<div id='doc_upload_file_sel_div'>
	<p>
		<label>
			<fmt:message bundle="${serviceliveCopyBundle}"  key="document.select.file"/>
		</label>
		<br />
		<s:file name="document.upload" id="document.upload" />
		<s:checkbox name="logoDocumentInd" id = "logoDocumentInd"  value="%{logoDocumentInd}" /> 
		<fmt:message bundle="${serviceliveCopyBundle}"  key="docmanager.logo.document.upload.msg"/>
	</p>
	<p>
		<label>
			<fmt:message bundle="${serviceliveCopyBundle}"  key="document.buyeradminfile.attachment.title"/>
		</label>
		<br />
		<s:textfield name="title" id="title" maxlength="50" size="60"/>	
	</p>
	<fmt:message bundle="${serviceliveCopyBundle}"  key="document.buyeradmin.file.description.instr"/>
	<p>

		
		<label>
			<fmt:message bundle="${serviceliveCopyBundle}"  key="document.file.description.label"/>
		</label>
		<br />

		<s:textarea name="description" id="description" cssStyle="width: 657px" cssClass="shadowBox" 
	  		onkeydown="countAreaChars(this.form.description, this.form.doc_and_photos_desc_leftChars, 255, event);" 
			onkeyup="countAreaChars(this.form.description, this.form.doc_and_photos_desc_leftChars, 255, event);"></s:textarea>
			<input type="text" name="doc_and_photos_desc_leftChars" readonly size="4" maxlength="4" value=""> <fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.chars.left"/>
			</p>
	 		<input type="button" id="attachDocumentBtn" 
				  onclick="validateFileName('document.upload', 'documentUpload')"
				  class="btn20Bevel"
				  style="background-image: url(${staticContextPath}/images/btn/attach.gif);
				            width:60px; height:20px;"
				  src="${staticContextPath}/images/common/spacer.gif"/>
				 
</div>
</s:form>
<s:form action="buyerAdminDocManager" id="buyerAdminDocManagerForViewAndRemove" 
        theme="simple" name="docPhotoForm" method="POST">
<s:hidden id="docTitle" name="docTitle" />
<s:hidden id="documetType" name="documetType" />
<fmt:message bundle="${serviceliveCopyBundle}"  key="document.buyeradmin.file.view.msg"/>
	<p>
		
		${documentUsageMessage}
		
	</p>


	<table class="scrollerTableHdr docMgrBuyerHdr" cellpadding="0" cellspacing="0">
		<tr>
			<td class="column1">
				<fmt:message bundle="${serviceliveCopyBundle}"  key="document.select"/>
			</td>
			<td class="column2">
				&nbsp;
			</td>
			<td class="column3">
				<fmt:message bundle="${serviceliveCopyBundle}"  key="document.file.name"/>
			</td>
			<td class="column6">
				<fmt:message bundle="${serviceliveCopyBundle}"  key="document.file.type"/>
			</td>
			<td class="column7">
				&nbsp;
			</td>
			<td class="column4">
				<fmt:message bundle="${serviceliveCopyBundle}"  key="document.buyeradmin.file.title"/>
			</td>
			<td class="column5">
				<fmt:message bundle="${serviceliveCopyBundle}"  key="document.file.size"/>
			</td>
		</tr>
	</table>
	
	<div class="grayTableContainer" style="width: 669px; height: 100px;">
		<table class="gridTable docMgrBuyer" cellpadding="0" cellspacing="0">
			<c:forEach var="doc" items="${documents}" varStatus="dtoIndex">
				<tr>
					<td class="column1">
						<input type="radio" name="documentSelection" id="documentSelection"value="${doc.documentId}" onclick="documentSelected('${doc.title}','${doc.documentType}')"/>
					</td>
					<td class="column2">
				    	 &nbsp;
					</td>
					<td class="column3">
						<strong>${doc.name}</strong>
						<p>
						
						${doc.desc}
						</p>
					</td>
					<td class="column6">
					
						${doc.documentType}
					</td>
					<td class="column7">
				    	 &nbsp;
					</td>
					<td class="column4">
						${doc.title}
					</td>
					<td class="column5">
						${doc.size} Kb
					</td>
				</tr>
			</c:forEach>
			
		</table>
	</div>
	<div class="clear"></div>
	<div class="clearfix">
		<p>
			<input type="button" 
				onclick="validateDocSelection('viewDocument')"
				class="btn20Bevel"
				style="background-image: url(${staticContextPath}/images/btn/view.gif);
			          width:70px; height:20px;"
				src="${staticContextPath}/images/common/spacer.gif"
			/>
		
			<input type="button" id="removeDocumentBtn" 
				onclick="validateDocSelection('removeDocument')"
				class="btn20Bevel"
				style="background-image: url(${staticContextPath}/images/btn/remove.gif);
			          width:70px; height:20px;"
				src="${staticContextPath}/images/common/spacer.gif"
			/>
		</p>
	</div>
	
	<p>
		<label>
			<fmt:message bundle="${serviceliveCopyBundle}"  key="document.buyeradminfile.attachment.documenttitle"/>
		</label>
		<br />
		
								<div class="errorBox clearfix"
									id="mandatoryFieldMissingErrorMessage"
									style="width: 380px; overflow-y: hidden;">
								</div>
								
		<br>						
		<select id="buyerDropDown" name ="buyerDropDown">
					<option value="-1">Select Process Step</option>
					<c:forEach var="lookupTypes" items="${buyerLookUpTypes}">
							<option value="${lookupTypes.id}">
							${lookupTypes.descr}
							</option>
					</c:forEach>	
		</select>
	<br>	<div>
		<table>
		<tr>
		<td width="40%">
	      <input name="doctitle" class="gridTable docMgrBuyer text"  style="font-family:verdana,sans serif;font-size:12px" onfocus="clearText(this)" onblur="clearText(this)" value="Enter Document Type" id="documenttype" type="text"  maxlength="75" size="60"/>
             </td><br>
		<td align="right" width="5%">
		<s:checkbox name="mandatoryFieldInd" id = "mandatoryFieldInd"  value="%{mandatoryFieldInd}" />
		</td>
		<td width="55%" align="left" >
		<fmt:message bundle="${serviceliveCopyBundle}"  key="document.buyeradminfile.attachment.checkmandatory"/>
		</td>
		</tr>

</table>
		 </div>
		</p>

<input class="button action" id="addDocumenType" style="width:60px" type="button" value="Add" onclick="addDocumentType()"/>
<br/>
		<div id="documentTypeDiv">
		
			<jsp:include page="buyer_doc_upload_type_detail.jsp"/>
		
		</div>
</s:form>
<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript">
	jQuery.noConflict();
	jQuery(document).ready(function($) {
		document.getElementById('mandatoryFieldMissingErrorMessage').style.display= "none";
    });
	var _documentSelectionChecked = false;
	function doAction() {
		document.getElementById("docPhotoForm").submit();
	}
	function documentSelected(docTitle,documentType) {
		_documentSelectionChecked = true;
		document.getElementById('docTitle').value = docTitle;
		document.getElementById('documetType').value = documentType;
		clearAllValidationMsgs();
	}
	function clearText(field){
			if (field.defaultValue == field.value) field.value = '';
			else if (field.value == '') field.value = field.defaultValue;
	}
	function deleteDocumentType(docTypeId)
	{
		jQuery("#documentTypeDiv").load("buyerAdminDocManager_deleteDocumentType.action?docTypeId=" + docTypeId);
	}			
	
	function addDocumentType() {
		document.getElementById('mandatoryFieldMissingErrorMessage').innerHTML = "";
           document.getElementById('mandatoryFieldMissingErrorMessage').style.display= "none";
           
		var errorMSg="";
		var success= true;
		var documentType = jQuery.trim(jQuery("#documenttype").val());
		var documentSource = document.getElementById('buyerDropDown').value;
		var chcked = jQuery('#mandatoryFieldInd').attr('checked');
		var docSource = jQuery.trim(jQuery("#buyerDropDown :selected").text());
		if (documentSource ==-1)
		{
			errorMSg ="Please select the Process Step</br>";
	   		success = false;
		}	
		
		if ((documentType == "") || (documentType == "Enter Document Type"))
		{
			errorMSg=errorMSg+"Please enter the Document Type";
	   		success = false;
		}
		jQuery('.docTypeTitle').each(function() {
			   	
			var v=jQuery.trim(jQuery(this).val());
			v = v.toLowerCase();
			var c=documentType+docSource;
			c = c.toLowerCase();	
			if(c==v && success == true){
				errorMSg=errorMSg+"Document Type is already entered for the selected Process Step";
		    		success = false;
				
				}
			});
	
		if(success == false){
			document.getElementById('mandatoryFieldMissingErrorMessage').innerHTML = errorMSg;
	           document.getElementById('mandatoryFieldMissingErrorMessage').style.display= "block";
	           return (false);
	          }
	          else{
			if(chcked == true){
				var mandatoryInd=1;
			}
			else{
				var mandatoryInd=0;
			}
			jQuery("#buyerDropDown").val("-1");
			jQuery("#documenttype").val("Enter Document Type");
			var code = documentType.replace(/%/g, "-prcntg-");
			var documentTypecheck = encodeURIComponent(code);
			jQuery("#documentTypeDiv").load("buyerAdminDocManager_addDocumentTypeDetail.action?documentType="+documentTypecheck+"&mandatoryInd="+mandatoryInd+"&documentSource="+documentSource);
		}
	}
	function validateDocSelection(method){
		var failedValidation = false;
		var exists = document.getElementById("documentSelection");
		
		clearAllValidationMsgs();
		
		if (exists){
			if (_documentSelectionChecked==false){
				document.getElementById('documentSelectionMsg').style.display = "inline";
				failedValidation = true;
			}
		}else{
			document.getElementById('documentSelectionMsg').style.display = "inline";
			failedValidation = true;
		}

		var loadForm = document.getElementById('buyerAdminDocManagerForViewAndRemove');
		//loadForm.enctype = ""; // Do not use multipart enctype for View / Remove action

		if (failedValidation==false){
			if (method=='removeDocument'){				
				if ( window.confirm('Do you really want to delete this document ?') ){
					submitForm(method,loadForm);
				}else{
					return false;
				}
			}else{
				submitForm(method,loadForm);
			}
		}
		
	}	
	
	function submitForm(method,loadForm){
		//var loadForm = document.getElementById('buyerAdminDocManager');
		
		if (method=='viewDocument'){
			loadForm.action = '${contextPath}' + "/BuyerDocumentView"; 
		} else {
			loadForm.action = '${contextPath}' + "/buyerAdminDocManager_" + method + ".action";
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
		var docTitle = document.getElementById('title').value;
		 var docDescription = document.getElementById('description').value;
		var textArea = document.createElement('textarea');
  		textArea.innerHTML = $ESAPI.encoder().encodeForHTML(docDescription);
		$('description').value=textArea.innerHTML;
		 
		var loadFormForAttach = document.getElementById('buyerAdminDocManager');
		clearAllValidationMsgs();
		if (fieldVal == null || fieldVal.length == 0){
			document.getElementById('fileNameSelectionMsg').style.display = "inline";
		} else if (docTitle == null || docTitle.length == 0){
			document.getElementById('titleSelectionMsg').style.display = "inline";
		} else {
			submitForm(method,loadFormForAttach);
		}
	}
	
	function clearAllValidationMsgs(){
		document.getElementById('documentSelectionMsg').style.display = "none";
		document.getElementById('fileNameSelectionMsg').style.display = "none";
		document.getElementById('titleSelectionMsg').style.display = "none";
		if (document.getElementById("actionError")){
			document.getElementById("actionError").style.display='none';
		}
	}
	
</script>
</body>
</html>
