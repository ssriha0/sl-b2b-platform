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
	<form method="POST" id="addInvoiceViewDocForm" action="">
		<div style="width:500px; padding-left:50px;">
		  <c:forEach items="${InvoiceDetailsVO.invoicePartsVOs} " var="invoicePartsVOs">
			<c:forEach items="${invoicePartsVOs.invoiceDocumentList}" var="partsUploadedDocuments">
				<a onclick="deleteEditPartsDocument(${partsUploadedDocuments.documentId});" href="javascript:void(0)" style="color: #FF0000;">X</a>
				&nbsp;<a onclick="viewEditPartsDocument(${partsUploadedDocuments.documentId});" href="javascript:void(0)">${partsUploadedDocuments.fileName}</a> 
				(by ${partsUploadedDocuments.uploadedbyName} on ${partsUploadedDocuments.uploadDateTime})<br/>
			</c:forEach>
		  </c:forEach>	
		</div>
	</form>
</body>
</html>