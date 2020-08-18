<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />

<head>
<!-- acquity: modified meta tag to set charset -->
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />

<title>ServiceLive - LMS Integration Tool</title>
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/main.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/iehacks.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/top-section.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/tooltips.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/buttons.css" />

<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/acquity.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/jqueryui/jquery-ui-1.10.4.custom.min.css" />
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
 <script type="text/javascript"
	src="${staticContextPath}/javascript/jquery/jqueryui/jquery-ui-1.10.4.custom.min.js"></script> 
<style type="text/css">
.ie7 .bannerDiv {
	margin-left: -1020px;
}

.showError {
	display : block!important;
	color : red;
	margin : 10px 0;
	padding : 5px;
	border : 3px solid red;
	background : #f6c1a9;
}
.ui-autocomplete {
    max-height: 50%;
    overflow-y: auto;   /* prevent horizontal scrollbar */
    overflow-x: hidden; /* add padding to account for vertical scrollbar */
    z-index:1000 !important;
}
</style>
<script type="text/javascript">
	var validBuyer = false;
	function validateForm() {
		/*  SL-21142  */
		// var buyerID = document.getElementById('buyerId1').value;
		var filePath = jQuery
				.trim(document.getElementById('lmsuploadFileId').value);
		document.getElementById('errDiv').style.display = "block";
		document.getElementById('errorMsg').style.display = "none";
		document.getElementById('successfullMsg').style.display = "none";
		<!-- SL-21142  -->
		/* if (buyerID.length == 0 || validBuyer == false) {
			var errDiv = document.getElementById('errDiv');
			errDiv.style.color = 'red';
			errDiv.style.margin = '10px 0';
			errDiv.style.padding = '5px';
			errDiv.style.border = '3px solid red';
			errDiv.style.background = '#f6c1a9';
			errDiv.innerHTML = '<h4>Error</h4><p>Please enter valid Buyer id.</p>';
			return false;
		}  else */
		if (filePath.length == 0) {
			var errDiv = document.getElementById('errDiv');
			errDiv.style.color = 'red';
			errDiv.style.margin = '10px 0';
			errDiv.style.padding = '5px';
			errDiv.style.border = '3px solid red';
			errDiv.style.background = '#f6c1a9';
			errDiv.innerHTML = '<h4>Error</h4><p>Please select a file and retry</p>';
			return false;
		} else {
			var lastDotIndex = filePath.lastIndexOf(".");
			if (lastDotIndex == -1) {
				var errDiv = document.getElementById('errDiv');

				errDiv.style.color = 'red';
				errDiv.style.margin = '10px 0';
				errDiv.style.padding = '5px';
				errDiv.style.border = '3px solid red';
				errDiv.style.background = '#f6c1a9';
				errDiv.innerHTML = '<h4>Error</h4><p>Invalid file path, must have a file extension ".csv"</p>';
				return false;
			} else {
				var fileExtension = filePath.substring(lastDotIndex + 1);
				if (fileExtension.toLowerCase() != 'csv') {
					var errDiv = document.getElementById('errDiv');
					errDiv.style.color = 'red';
					errDiv.style.margin = '10px 0';
					errDiv.style.padding = '5px';
					errDiv.style.border = '3px solid red';
					errDiv.style.background = '#f6c1a9';
					errDiv.innerHTML = '<h4>Error</h4><p>The file must be an .csv file.</p>';
					return false;
				}
			}
		}
		return true;
	}

	
	jQuery(document).ready(function() {
		jQuery('.jqmWindow').jqm({
			modal : true,
			toTop : true
		});
		var errorVarible = document.getElementById('error').value;
		if (errorVarible) {
			jQuery('#lms').jqmShow();
		}
		<!-- SL-21142  -->
		/* var res;
		$( "#buyerId1" ).autocomplete({
			source: function(request, response) {
				$.ajax({
					url : "lms_upload/ajaxBuyerIDAutoComplete.action",
					type : "GET",
					data : {
							searchTerm : request.term
					},
					dataType : "json",
					success : function(data) {
						res = data;
							response(data);
					}
			});
			},
			minLength: 3
		});
	
	$( "#buyerId1" ).focusout(function() {
		var selectedBuyer = $("#buyerId1").val();
		$.each(res, function(i,obj) {
		  if (obj.value === selectedBuyer) { validBuyer = true; return false;}
		});  
	}); */
	if(document.getElementById('fileName')!=null){
	var fileName = document.getElementById('fileName').value;
	document.getElementById('fileName2').innerHTML = fileName;
	}
});
	
	
	
</script>
</head>
<body class="tundra acquity">
	<div id="page_margins">
		<div id="page">
			<div id="header">
				<tiles:insertDefinition name="newco.base.topnav" />
				<tiles:insertDefinition name="newco.base.blue_nav" />
				<tiles:insertDefinition name="newco.base.dark_gray_nav" />
				<div id="pageHeader"></div>
			</div>
			<!-- END HEADER -->
			<div id="hpWrap" class="clearfix">
				<div id="hpContent" class="pdtop20" style="width: auto">
				<div id="hpIntro">
						<h2>Import Training Credentials</h2>
					</div> 
					<div class="hpDescribe clearfix" style="width: 98%">
						<h2>Upload Your File</h2>

						<p>
							<b>An asterix (<span class="req">*</span>) indicates a
								required field
							</b>
						</p>
						<br />
						<!-- begin failure/success message -->
							<div id="errDiv" style="display: none;"
								style="color: red; margin: 10px 0; padding: 5px; border: 3px solid red; background: #f6c1a9;">
								<span><s:fielderror name="uploadAuditDTO.uploadFile"
										theme="simple" /> </span>
							</div>
						<div id="errorMsg">
							<c:if test="${not empty uploadStat and uploadStat == 'false'}">
								<div id="err">
									<div id="soError"
										style="color: red; margin: 10px 0; padding: 5px; border: 3px solid red; background: #f6c1a9;">
										<h4>Error!</h4>
										<span><s:fielderror name="uploadFile" theme="simple" />
										</span>
									</div>
								</div>
							</c:if>
						</div>
						<div id="successfullMsg">
							<c:if test="${not empty uploadStat and uploadStat == 'true'}">
								<div id="soSuccess" class="clearfix"
									style="color: #1F8B06; margin: 10px 0; padding: 5px; border: 3px solid #7dc012; background: #eef7df;">
									<h4>Your request has been successfully submitted.</h4>
									<dl style="color: black;">
										<dt style="width: 400px; float: left;">
											<span style="font-weight: bold;">File Name: </span>${uploadedFileFileName}
										</dt>
									</dl>
								</div>
							</c:if>
						</div>
						<div class="clearfix">
							<div class="left">
								<s:form id="file_upload_form" name="file_upload_form"
									action="lmsFileUploadAction.action" method="POST"
									enctype="multipart/form-data" theme="simple">
									<!-- SL-21142  -->
									<%-- <p>
										Buyer/Issuer ID<span class="req">*</span><input
											type="text" name="buyerTextField" id="buyerId1" style="width: 55%;"/>
									</p>
									<br />
									<br /> --%>
									<table width='100%'>
										<tr>
											<p>
												Select file to upload<span class="req">*</span>
												<td colspan="2"><s:file id="lmsuploadFileId"
														name="uploadFile" size="50"/> <em>Please
														upload .csv file only.</em></td>
											</p>
										</tr>
									</table>
									<br />
									<br />
									<div class="left">
										<s:submit value="Upload" onclick="return validateForm();"></s:submit>
									</div>
								</s:form>

							</div>
						</div>
					</div>
					<div class="hpDescribe clearfix" style="width: 960px">
						<h2 style="display: block">File Status</h2>
						<table class="buyerUpload" border="0" cellpadding="0"
							cellspacing="0">
							<tr style="width: 100%;" >
								<th  style="text-align: left; width: 14%">Uploaded By</th>
								<!-- <th  style="text-align: left; width: 14%" >Issuer Detail</th> -->
								<th style="text-align: left; width: 14%">File Name</th>
								<th style="text-align: left; width: 14%">Date Submitted</th>
								<th  style="width: 14%"># of Submitted</th>
								<th  style="text-align: left; width: 14%">File Status</th>
								<th  style="width: 14%"># of Errors</th>
							</tr>
							<c:choose>
								<c:when test="${not empty runningLmsList}">
									<c:forEach items="${runningLmsList}" var="lmsHistory">
										<tr style="width: 100%;">
										    <td style="text-align: left;width: 14%">${lmsHistory[6]}</td>
											<%-- <td style="text-align: left;width: 14%">${lmsHistory[6]}</td> --%>
											<td style="text-align: left;width: 14%"><a
												href="<s:url action='%{contextPath}downloadFile'>
														 <s:param name="docId">${lmsHistory[5]}</s:param>
														</s:url>">
													${lmsHistory[0]}</a></td>
											<td style="text-align: left;width: 14%">${lmsHistory[1]}</td>
											<td style="width: 14%">${lmsHistory[2]}</td>
											<td style="text-align: left;width: 14%">${lmsHistory[4]}</td>
											<c:choose>
												<c:when test="${lmsHistory[3]==0}">
													<td style=" width: 14%">${lmsHistory[3]}</td>
												</c:when>
												<c:otherwise>
													<td style="width: 14%" class="jqMApprove"><a
														href="<s:url action='%{contextPath}lmsGetErrorAction'>
														 <s:param name="fileId">${lmsHistory[5]}</s:param>
														</s:url>">
															${lmsHistory[3]} </a></td>
												</c:otherwise>
											</c:choose>
										</tr>
									</c:forEach>
								</c:when>
							</c:choose>
						</table>
					</div>
				</div>
			</div>
			<!--TO Show POP UP Screen  -->
			<input type="hidden" name="error" id="error"
				value="${runningErrorList}" />
			<div id="lms" class="jqmWindow"
				style="width: 775px; position: center; margin-left: -400px;">
				<div class="modal-header">
					<a
						href="<s:url value='%{contextPath}/lmsFileGetAction_getLmsDetailHistory.action'/>"
						class="right" style="color: red;">Close</a> 
				</div>
				</br>
				<div class="modal-content" style="margin-bottom: 15px;">

					<h2 style="display: block">LMS Error History Details</h2>
					<h3>
						File Name:<label id="fileName2"></label>
					</h3>
					<div>
						<table cellpadding="0" cellspacing="0" width="775px">
							<thead style="overflow: auto; height: 500%; size: auto;">
								<tr
									style="color: white; height: 25px; background-color: #00A0D2;">
									<th width="187px">Record Text</th>
									<th width="187px">Failure Reason</th>
								</tr>
							</thead>
						</table>
					</div>
					<div style="overflow: scroll; height: 200px;">
						<table border="1%" cellpadding="0" cellspacing="0" width="auto;">
							<tbody style="text-align: left;">
								<c:forEach items="${runningErrorList}" var="lmsError">
									<tr style="height: 20px; font: inherit;">
										<input type="hidden" name="fileName" id="fileName"
											value="${lmsError[0]}" />
										<th style="font: inherit; width: 375px;">${lmsError[1]}</th>
										<th style="font: inherit; width: 375px;">${lmsError[2]}</th>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>

			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
		</div>
	</div>
</body>
</html>
