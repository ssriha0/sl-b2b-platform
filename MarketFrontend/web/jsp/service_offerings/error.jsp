<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<script type="text/javascript">
		function load() {
			if (top != self) {
				//Code mofified to avoid Client DOM Open Redirect
			 	//top.location = location
			 	top.location = encodeURI(location)
			} 
		}
	</script>
  <head>
    <title>SL Error Page</title>
  </head>
  
  <body onload="javascript:load()">
  	<fmt:message bundle="${serviceliveCopyBundle}" key="common.error.page.message"/>
  	<br/>
  	<a href="${contextPath}/dashboardAction.action">
  		<img src="${contextPath}/images/buttons/clear.gif"
		 	alt="Need a home button here" />
	</a> 
  </body>
</html>
