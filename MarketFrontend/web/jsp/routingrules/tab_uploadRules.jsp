<%@ page language="java"
	import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
			
<script type="text/javascript">var staticContextPath="${staticContextPath}";</script>
 <tags:security actionName="routingRulesAction_view">
 	<c:set var="isReadOnly" value="true" scope="page" />
 </tags:security>
 <tags:security actionName="routingRulesAction_edit">
     <c:set var="isReadOnly" value="false" scope="page" />
 </tags:security>
 <%
	String refreshTiming = (String) session.getAttribute("refreshTime");
%>
<script type="text/javascript">
	 var refresh = '<%=refreshTiming%>';
     var filesCount = 0;
     var sortColumn3;     
     jQuery(document).ready(function(){
     	$('#uploadValidationFrontError').hide();
	 });  
	
	function ajaxFileUpload(){
		setCursorWait(true);
		var filePath = $('#uploadFile').val();
		
	    // hide the error and success
		$('#uploadValidationFrontError').hide();
		$('#uploadValidationError').hide();
		$('#uploadSuccessMessage').hide();
		
		var msgSelectFileToUplaod = "Please select a file to upload.";
		var msgwrongFileUploaded  = "Please upload .xls or .xlsx template only.";
		
		// Validate if the file is present		
		if( filePath == "" ||  filePath.length == 0 ){
			$('#uploadValidationFrontError > .errorText').html(msgSelectFileToUplaod);
			$('#uploadValidationFrontError').show();
			setCursorWait(false);
			return false;
		}
		
		//Validate the file for extension
		var dot = filePath.lastIndexOf(".");
		if( dot == -1 ){
			 $('#uploadValidationFrontError > .errorText').html(msgwrongFileUploaded);
			 $('#uploadValidationFrontError').show();
			 setCursorWait(false);		 
			 return false;
		} 
		
		var extension = filePath.substr(dot,filePath.length); 	
		if (extension != '.xls' &&  extension != '.xlsx'){
		 	 $('#uploadValidationFrontError > .errorText').html(msgwrongFileUploaded);
			 $('#uploadValidationFrontError').show();	
			 setCursorWait(false);	 
			 return false;
		}
		$.ajaxFileUpload({
			url:'routingRulesUploadTabAction_uploadFile.action', 
			secureuri:false,
			fileElementId:'uploadFile',
			dataType: 'json',
			success: function (data, status){
				var success = data.uploadSuccess;
				if(success == false || success == 'false'){
					$('#uploadValidationError').html(data.uploadError);
					$('#uploadValidationError').show();
				} 
				if(success == true || success == 'true'){ 
					var fileName = "\""+data.uploadedFileName+"\"";
					$('#uploadSuccessText').html(fileName);
					$('#uploadSuccessMessage').show();
					$('#uploadedFiles').load("routingRulesUploadTabAction_displayList.action");
				}
				$('#routingRulesUploadTabAction_uploadFile')[0].reset();
			},
			error: function (data, status, e){
			
			}
		})
		setCursorWait(false);
		return false;
	}
	
	function refreshPage() {
		var selected = $( "#carTabs" ).tabs( "option", "selected" );
		if(1 == selected){
			$('#uploadedFiles').load("routingRulesUploadTabAction_displayList.action?pageLoad=true");
     	}
     }
     // Refresh the page every 300 seconds
	 window.setInterval("refreshPage()", refresh);
</script>

<div id="uploadRulesTab">
	<c:choose>
		<c:when test="${isReadOnly}">
			<div id="upload_tab_content">
				<h3 style="padding-bottom: 10px;">Upload Rules</h3>	
			</div>
		</c:when>
		<c:otherwise>
		<div id="upload_tab_content">
		<h3 style="padding-bottom: 10px;">Upload Rules</h3>	
			Use the form below to upload a set of rules from an	excel spreadsheet. All uploaded excel files must match our sample file template.<br/>
			Depending on the size of the file you upload, processing can take several minutes. (View Sample Files:&nbsp;
			<span>
				<a title="Download create template" style="color: #00A0D2;" href="${downloadCreateFile}">Create</a>,
				<a title="Download update template" style="color: #00A0D2;" href="${downloadUpdateFile}">Update</a>,
				<a title="Download archive/inactivate template" style="color: #00A0D2;" href="${downloadArchiveFile}">Archive or Deactivate</a>
			</span>)
			<br/>
			<div id="uploadValidationError" class="errorMsg" style="display: none;word-wrap:break-word;"> 
			</div>
			<div id="uploadSuccessMessage" class="successMsg" style="display: none;word-wrap:break-word;">
				File name&nbsp;&nbsp;<b id="uploadSuccessText"></b>&nbsp;&nbsp;successfully uploaded and processing.
			</div>
			<div id="uploadValidationFrontError" class="error errorMsg errorMessage">
				<p class="errorText"></p>
			</div>
		</div>
		<div>
			<s:form  action="routingRulesUploadTabAction_uploadFile.action"
					id="routingRulesUploadTabAction_uploadFile" name="routingRulesUploadTabAction_uploadFile"
					method="post" enctype="multipart/form-data" theme="simple">
					<br/>
				<div align="left">
					<input type="file" id="uploadFile" name= "uploadFile" size="50" value="Browse"/>
					<em>Please upload .xls or .xlsx file only.</em>
				</div>
				<br/>
				<div align="left">
	 				<input type="button" class="button action" 
						value="Upload File" id="uploadFileButton" 
						onclick="return ajaxFileUpload();">
				</div>
			</s:form>
		</div>	
		</c:otherwise>
	</c:choose>
		
	<!--  Show the list of file list for the user -->
	 <jsp:include page="/jsp/routingrules/uploadFileList.jsp"></jsp:include>
</div>