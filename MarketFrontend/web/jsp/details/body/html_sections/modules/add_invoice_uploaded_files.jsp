<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<html>
<head></head>
<body>
	<form method="POST" id="invoicePartsViewDocForm" action="">
		<div style="width:600px; padding-left:50px;">
			<c:forEach items="${invoicePartDetails.invoiceDocuments}" var="addInvoiceUploadedFiles">
				<a onclick="deleteEditPartsDocument(${addInvoiceUploadedFiles.documentId});" href="javascript:void(0)" style="color: #FF0000;">X</a>
				&nbsp;<a onclick="viewInvoicePartsDocument(${addInvoiceUploadedFiles.documentId});" href="javascript:void(0)">${addInvoiceUploadedFiles.fileName}</a> 
				<i>(by ${addInvoiceUploadedFiles.uploadedbyName} on ${addInvoiceUploadedFiles.uploadDateTime})</i><br/>
			</c:forEach>
		</div>
	</form>
</body>
</html>