<%@page import="com.newco.marketplace.web.utils.SecurityChecker" %> 
<%  SecurityChecker sc = new SecurityChecker();
	String path =request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort() + "/sso/secure/Login.action?targetApp=spn";
	path = sc.securityCheck(path);
	response.sendRedirect(path); %> 