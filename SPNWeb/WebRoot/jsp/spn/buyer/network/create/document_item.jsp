<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<jsp:directive.page import="com.servicelive.spn.common.SPNBackendConstants"/>
<c:set var="DOC_TYPE_ELECTRONIC_AGREEMENT" scope="page" value="<%=com.servicelive.spn.common.SPNBackendConstants.DOC_TYPE_ELECTRONIC_AGREEMENT%>"/>

<c:if test="${uploadDocData.uploadSuccess == true}">
	{
		"error": "",
		"msg": "${uploadDocData.uploadDocId}"
	}
</c:if>
<c:if test="${uploadDocData.uploadSuccess == false}">
	{
		<c:if test="${uploadDocData.uploadDocErrorStr != ''}">
		"error": "${uploadDocData.uploadDocErrorStr}",
		</c:if>
		<c:if test="${uploadDocData.uploadDocErrorStr == ''}">
		"error": "Error uploading document!",
		</c:if>
		"msg": ""
	}
</c:if>
