<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'buyer_doc_upload_type_detail.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <c:if test="${not empty buyerUploadTypes}">
  
   <table  cellpadding="0" cellspacing="0" style="width: 670px;" >
		<tr class="scrollerTableHdr docMgrBuyerHdr">
		<td width="30%" >
				<fmt:message bundle="${serviceliveCopyBundle}"  key="document.file.documentselectprocess" />
			</td>
			<td width="40%"   >
				<fmt:message bundle="${serviceliveCopyBundle}"  key="document.file.documenttype"/>
			</td>
			
			<td width="30%"  >
				<fmt:message bundle="${serviceliveCopyBundle}"  key="document.file.mandatorycheck"/>
			</td>
			</tr>
	</table>
	
	<div class="grayTableContainer" style="width: 669px; height: 100px;overflow-x: hidden" >
		<table class="gridTable docMgrBuyer" style="width: 669px;overflow-x: hidden" cellpadding="0" cellspacing="0">
			<c:forEach var="newupload" items="${buyerUploadTypes}" varStatus="dtoIndex">
				<tr>
					<td width="30%" >
					${newupload.source}
					</td>
				
					<td  width="40%" >
						<strong>${newupload.documentTitle}</strong>
						<input type="hidden" class="docTypeTitle" value="${newupload.documentTitle}${newupload.source}"/>
					<c:if test="${!newupload.nonDeletable}">
					<img id="removeDocumentBtn"
									onclick="deleteDocumentType('${newupload.buyerCompDocId}')" class="btn20Bevel"
									style="visibility: visible;cursor: pointer;"
									src="${staticContextPath}/images/response_icon_red.gif" title="Delete" >
					</c:if>
					</td>
					<td  width="28%" >
					<c:choose>
					<c:when test="${newupload.mandatoryInd==1}">
						Yes
					</c:when>
					<c:otherwise>
						No
					</c:otherwise>
					</c:choose>
						
					</td>
					
				</tr>
			</c:forEach>
			
		</table>
		</div>
<div class="clear"></div>
</c:if>
  </body>
</html>
