<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:choose>
<c:when test="${spnProvUploadedDocsVO.spnBuyerDocId != '0'}">
	{ "error": "", "docFileName": "${spnProvUploadedDocsVO.docFileName}", "provFirmUplDocId" : "${spnProvUploadedDocsVO.provFirmUplDocId}",
		"docStateDesc" : "${spnProvUploadedDocsVO.docStateDesc}" }
</c:when>
<c:otherwise>
	{ "error": "Error uploading document!" }
</c:otherwise>
</c:choose>