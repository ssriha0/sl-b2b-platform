<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils,org.owasp.esapi.ESAPI"%>


<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<html>
	<head>
	<%String sidVar=(String)request.getAttribute("{param.sid");
	  String sidNew=ESAPI.encoder().canonicalize(sidVar);
	  String sidEnc=ESAPI.encoder().encodeForHTML(sidNew);
	%>
		<title>Marketplace Homepage</title>
		<c:if test="${sidEnc == null}">
<META http-equiv="refresh" content="0;URL=<%=request.getContextPath()%>/homepage.action" />		
		</c:if>
		
		<c:if test="${sidEnc != null}">
<META http-equiv="refresh" content="0;URL=<%=request.getContextPath()%>/homepage.action?sid=<%=sidEnc %>" />		
		</c:if>
	

   </head>
	<body>
		

	</body>

</html>