<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="org.owasp.esapi.ESAPI"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>WYMeditor Test</title>
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery.wymeditor.pack.js"></script>
	<script type="text/javascript">
	jQuery(function() {
	    jQuery('.wymeditor').wymeditor();
	});
	</script>
</head>
<body>
	<h1>WYMeditor Test in ServiceLive!</h1>
	<p><a href="http://www.wymeditor.org/">WYMeditor</a> is a web-based XHTML WYSIWYM editor.</p>
	<% String docDataVar = request.getParameter("param.docData");
	String docDataNew=ESAPI.encoder().canonicalize(docDataVar);
	String docDataVuln=ESAPI.encoder().encodeForHTML(docDataNew);%>
	<c:set var="docData" scope="request" value="<%=docDataVuln%>" />	
	<c:if test="${empty docData}">
		<p>Submit some rich text in text area below.</p>
		<form method="post" action="">
			<textarea class="wymeditor" name="docData"></textarea>
			<input type="submit" class="wymupdate" />
		</form>
	</c:if>
	<c:if test="${not empty docData}">
		<p>This is what you've submitted</p>
		<hr/>
		<p>${docData}</p>
		<hr/>
	</c:if>
</body>
</html>
