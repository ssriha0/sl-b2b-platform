<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>ServiceLive Provider API</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="ServiceLive Public API">
	</head>

	<body>
	<form name="edit" action="Result" method="get">
		<font color="#0080c0">

			<h1 align="center">
				Welcome to Market Provider API
			</h1>
		</pre> </font>
		<h1 align="center"><input type="submit" name="InvokeServlet" value="Test SO APIs">
		</h1>
</form>
	</body>
</html>
