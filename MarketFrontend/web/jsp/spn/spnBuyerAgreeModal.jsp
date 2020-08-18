<%@ page language="java"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Buyer Agreement Modal 'spnBuyerAgreeModal.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/superfish.css" media="screen, projection">
	
	<!-- end blueprint base include -->

	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/spn-invitation.css" media="screen, projection">
	
	
 
  </head>
 
  <body>
  <form name='buyeragreemodal'>
  <div id="errMsg"></div>  	
  <input type="hidden" name="firmId" value="${SecurityContext.companyId}"/>
  	<c:set var="countdocs" value="0"/>
    <c:set var="count" value="0"/>
    <c:set var="spnID" value=" "/>
    <c:forEach items="${documenList}" var="docList">
    	<c:set var="count" value="${count+1}"/>
    	<c:set var="spnID" value="${docList.spnId}"/>
    </c:forEach>
    <c:choose>
    <c:when test="${count!=0}">
    <c:forEach items="${documenList}" var="docList">
		<div id="${countdocs}" style="display:none">
	 		<c:if test="${countdocs ==0}">
	 			<script>
	 			document.getElementById('${countdocs}').style.display='block';
	 			</script>
	 		</c:if>
			<c:set var="countdocs" value="${countdocs+1}"/>
			<div class="modal-header">
			<h5 class="left">${docList.buyerName}&nbsp;&nbsp;${docList.spnName}&nbsp;&nbsp;Agreements</h5>
			<h5 class="right">${countdocs} of ${count}</h5>
			</div>
			<div class="modal-content">
			 <c:if test="${countdocs ==count}">
			<div class="error" id="error" style="display:none">
			</div>
			</c:if>
			<c:set var="frameName" value="frameDoc"/>
	        <c:set var="frameName" value="${frameName}${countdocs}"/>
			<div class="clearfix">
				<h3 class="left"><b>${docList.documentTitle}</b></h3>
				<a href="#printlink" class="right print" onClick="printdoc('${frameName}');">Print Agreement</a>
			</div>
			<iframe name="${frameName}" src ="spnShowDocument.action?&docID=${docList.docId}" width="100%" height="300px" frameborder="1">
				<p>Your browser does not support iframes.</p>
			</iframe>
			<div align="left" class="clearfix agreementcheck">
		<input type="checkbox" class="checkbox left" name="agree" id="${docList.documentTitle}"><label>I have read and agree with the ${docList.documentTitle}</label>
	</div>
	<div class="clearfix buttonarea">	
		<input class="default left jqmClose" value="Save &amp; Complete Later" type="button">
		<!-- <input class="action right" value="Submit Application" type="submit"> -->
		<c:if test="${countdocs <count}">
		<input class="action right" value="Next &raquo;" type="button" onClick="goToNext('${countdocs}','${count}');">
		</c:if>
		<c:if test="${countdocs ==count}">
	    <input type="button" class="action right" value="SUBMIT APPLICATION" onClick="checkIfAgreed('${count}');"/>
	    </c:if>
		<c:if test="${countdocs !=1}">
		<input class="action right rpad" value="&laquo; Previous" type="button" onClick="goToNext('${countdocs-2}','${count}');">
		</c:if>
	</div>
	</div>
	</div>
	</c:forEach>
	</c:when>
	<c:otherwise>
		<div class="error" >There are no Buyer Agreements</div>
	</c:otherwise>
	</c:choose>
	<input type="hidden" name="spnId" value="${spnID}"/>
	</form>
  	</body>
</html>
