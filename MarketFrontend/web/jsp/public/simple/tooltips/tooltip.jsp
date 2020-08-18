<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ page import="org.owasp.esapi.ESAPI"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% 
String pageNameVar=(String)request.getParameter("bundlekey");
String pageNameVarNew=ESAPI.encoder().canonicalize(pageNameVar);
String vulnPageName=ESAPI.encoder().encodeForHTML(pageNameVarNew);
%>
 <fmt:message bundle="${serviceliveCopyBundle}" key="<%=vulnPageName%>" />


