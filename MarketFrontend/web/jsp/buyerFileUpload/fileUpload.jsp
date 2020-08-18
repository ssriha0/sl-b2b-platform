<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="contextPath" scope="request"value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<c:set var="FILE_STATUS_PROCESSING" value="<%=OrderConstants.FILE_UPLAOD_PROCESSING%>" />
<c:set var="FILE_STATUS_COMPLETED" value="<%=OrderConstants.FILE_UPLOAD_COMPLETED %>" />
<head>
	<!-- For auto-refresh -->
	<script>window.setInterval("window.location.reload(true)", 60000);</script>
	
	<!-- acquity: modified meta tag to set charset -->
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	
	<title>ServiceLive - Service Order Import Tool</title>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />

		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
		<!--[if IE]>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
		<![endif]-->

		<!-- acquity: here is the new js, please minify/pack the toolbox and rename as you wish -->
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>	
		<script type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	          .ie7 .bannerDiv{margin-left:-1020px;}  
		</style>
<script>
function refreshUploadStatus() {
	//-- Pass buyerResourceId in the link, or try to get it from security context inside action
	var uploadToolAjaxActionURL = "UploadToolAjaxAction.action?rid=${1234}";
	//alert(uploadToolAjaxActionURL);
    $.getJSON(uploadToolAjaxActionURL,
        function(data) {
        	alert(data);
        });
}
//window.setInterval("refreshUploadStatus()", 10000); // Refresh upload status every 60 seconds
function showErrorRecords(fileId) {		
	var loadErrorForm = document.getElementById('file_upload_form');
	loadErrorForm.hidFileId.value=fileId;
	loadErrorForm.action="buyerFileUploadAction_getErrorRecordList.action";
	loadErrorForm.submit();
}
function validateForm() {
	var filePath = jQuery.trim(document.getElementById('uploadFileId').value);
	if (filePath.length == 0) {
		var errDiv = document.getElementById('errDiv');
		errDiv.style.color = 'red'; errDiv.style.margin = '10px 0'; errDiv.style.padding = '5px'; errDiv.style.border = '3px solid red'; errDiv.style.background = '#f6c1a9';
		errDiv.innerHTML = '<h4>Error</h4><p>File name is required.</p>';
		return false;
	} else {
		var lastDotIndex = filePath.lastIndexOf(".");
		if (lastDotIndex == -1) {
				var errDiv = document.getElementById('errDiv');
				errDiv.style.color = 'red'; errDiv.style.margin = '10px 0'; errDiv.style.padding = '5px'; errDiv.style.border = '3px solid red'; errDiv.style.background = '#f6c1a9';
				errDiv.innerHTML = '<h4>Error</h4><p>Invalid file path, must have a file extension ".xls"</p>';
				return false;
		} else {
			var fileExtension = filePath.substring(lastDotIndex+1);
			if (fileExtension.toLowerCase() != 'xls') {
				var errDiv = document.getElementById('errDiv');
				errDiv.style.color = 'red'; errDiv.style.margin = '10px 0'; errDiv.style.padding = '5px'; errDiv.style.border = '3px solid red'; errDiv.style.background = '#f6c1a9';
				errDiv.innerHTML = '<h4>Error</h4><p>The file must be an .xls file.</p>';
				return false;
			} else {
				return true;
			}
		}
	}
}
</script>

</head>
<body class="tundra acquity">
   
	<div id="page_margins">
		<div id="page">
			<div id="header">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
					<div id="pageHeader">
					</div>
				</div>
				_<!-- END HEADER -->
<div id="hpWrap" class="clearfix">
	<div id="hpContent" class="pdtop20" style="width:auto">
		<div id="hpIntro"><c:if test="${not empty soDocDTO}">
				<img class="right" src="${contextPath}/buyerFileUploadAction_buyerLogoDisplay.action" alt="Buyer Logo" style="padding: 10px;"/></c:if>
			<h2>Service Order Import Tool</h2>
			<p>The Service Order Import Tool is designed with commercial buyers in mind. This tool will allow you a simple and direct method to upload multiple service orders in to the ServiceLive marketplace in one efficient step. Whether you have project-based needs or you are gearing up for a National roll out, the tool can save you time and money.
			&nbsp;&nbsp;&nbsp;&nbsp;<a href='${helpLink}' target='soitHelp'>Help</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href='${sampleFileLink}'>View Sample File</a></p>
		</div>
		<div class="hpDescribe clearfix" style="width: 680px">
			<h2>Upload Your File</h2>
			
			<!-- begin failure/success message -->
			<c:if test="${not empty uploadStat and uploadStat == 'false'}">
				<div id="errDiv">
					<div id="soError" style="color: red; margin: 10px 0; padding: 5px; border: 3px solid red; background: #f6c1a9; ">
						<h4>Error!</h4>
						<span><s:fielderror name="uploadAuditDTO.uploadFile" theme="simple"/></span>
					</div>
				</div>
			</c:if>
			<c:if test="${not empty uploadStat and uploadStat == 'true'}">
				<div id="soSuccess" class="clearfix" style="color: #1F8B06; margin: 10px 0; padding: 5px; border: 3px solid #7dc012; background: #eef7df; " >
					<h4>Your request has been successfully submitted.</h4>
					<dl style="color: black;">
						<dt style="width: 400px; float: left;"><span style="font-weight: bold;">File Name: </span>${uploadedFileName}</dt>
					</dl>				
				</div>
			</c:if>

			<div class="clearfix">
					<div class="left">
					<s:form id="file_upload_form" name="file_upload_form" action="buyerFileUploadAction" method="POST" enctype="multipart/form-data" theme="simple">
					<s:hidden name="hidFileId"/>
					
					<table width='100%'>
						<tr>
							<td colspan="2">
								<s:file id="uploadFileId" name="uploadAuditDTO.uploadFile" size="50" />
								<em>Please upload .xls file only.</em>
							</td>
						</tr>	
								
				   </table>
								
			<br />
			<%-- <input type="checkbox" class="checkbox"> I agree to the <a href="#">Buyer Terms &amp; Conditions</a>  --%>
			<br />
			<div class="left">
			<s:submit  value="Upload Service Orders"  method="submit" onclick="return validateForm();"></s:submit>
			</div>
			</s:form>
					
					</div>
			</div>
		</div>

		<div class="hpDescribe clearfix" style="width: 960px" >
			<h2 style="display:block">Import Status</h2>
			<table class="buyerUpload" border="0" cellpadding="0" cellspacing="0">
				<tr><th class="first">Name</th><th>Date Submitted</th><th>Buyer</th><th># of Submitted</th><th class="last"># of Errors</th></tr>
				<c:choose><c:when test="${not empty runningHistoryList}">
				<c:forEach items="${runningHistoryList}" var="runningHistory">
					<tr width="100%">
						<td class="first" width="10%" nowrap="nowrap"> ${runningHistory.fileName}</td>
						<td width="25%"> ${runningHistory.createdDate}</td>
						<td width="20%"> ${runningHistory.buyerName}</td>
						<c:choose>
						<c:when test="${FILE_STATUS_PROCESSING == runningHistory.errorNo}">
							<td width="25%"><img src="${staticContextPath}/images/spinner_small.gif" alt="Queued for processing"/>&nbsp;&nbsp;Queued for processing.</td>
							<td class="last">&nbsp;</td>
						</c:when>
						<c:when test="${FILE_STATUS_COMPLETED == runningHistory.errorNo}">
							<td width="25%">${runningHistory.successNo}</td>
							<td class="last" width="25%"><span class="errored">${runningHistory.errorNo}</span></td>
						</c:when>
						<c:otherwise>
							<td width="25%">${runningHistory.successNo}</td>
							<td class="last" width="25%"><span class="errored"><a href="javascript:showErrorRecords(${runningHistory.fileId});">${runningHistory.errorNo}</a></span></td>
						</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
				</c:when>
				<c:otherwise>
					<tr><td colspan="5">No files have been uploaded in the system in past 45 days.</td></tr>
				</c:otherwise></c:choose>
				<c:if test = "${fn:length(runningHistoryList) > 27}">
				<tr>
					<th class="first">Name</th>
					<th>Date Submitted</th>
					<th>Buyer</th>
					<th># of Submitted</th>
					<th class="last"># of Errors</th>
				</tr>
				</c:if>
			</table>
		</div>
	</div>
</div>
<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
</div></div><!-- acquity: empty divs to ajax the modal content into -->
<div id="reportProblem" class="jqmWindow"></div>
<div id="serviceFinder" class="jqmWindow"></div>
<div id="modal123" class="jqmWindowSteps"></div>
<div id="zipCheck" class="jqmWindow"></div>
</body>
</html>
