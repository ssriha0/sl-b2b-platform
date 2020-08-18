<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>ServiceLive Public API</title>
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
				Welcome to Market Public API
			</h1>

			<h2>
				API URLs
			</h2>
			<pre>	
			<h3>SO APIs</h3>
		1. Create SO
			Mock - <%=basePath%>training/serviceorder/create 
			Real - <%=basePath%>serviceorder/create 
		 
		2. Get SO 
			Mock - <%=basePath%>training/serviceorder/get/141-5481-7618-46 
			Real - <%=basePath%>serviceorder/get/141-5481-7618-46 
			 
		3. Search SO 
			Mock - <%=basePath%>training/serviceorder/search 
			Real - <%=basePath%>serviceorder/search 
 
		4. Reschedule SO 
			Mock - <%=basePath%>training/serviceorder/reschedule/141-5481-7618-46 
			Real - <%=basePath%>serviceorder/reschedule/141-5481-7618-46 
 
		5. Cancel SO 
			Mock - <%=basePath%>training/serviceorder/cancel/152-7122-6820-52 
			Real - <%=basePath%>serviceorder/cancel/152-7122-6820-52 
 
		6. Post SO 
			Mock - <%=basePath%>training/serviceorder/post/152-7122-6820-52 
			Real - <%=basePath%>serviceorder/post/152-7122-6820-52 
 
		7. Add Note 
			Mock - <%=basePath%>training/serviceorder/addNote/152-7122-6820-52 
			Real - <%=basePath%>serviceorder/addNote/152-7122-6820-52 
			 
		<h3>Search APIs</h3>
		1. Providers by zip
			Mock - <%=basePath%>training/providers/zip?zip=x&amp;categoryid=x&amp;servicetype=z&amp;language=x&amp;maxdistance=x&amp;minrating=x&amp;resultmode=x&amp;pagesize=x&amp;pagenumber=x 
			Real - <%=basePath%>providers/zip?zip=x&amp;categoryid=x&amp;servicetype=z&amp;language=x&amp;maxdistance=x&amp;minrating=x&amp;resultmode=x&amp;pagesize=x&amp;pagenumber=x  
  
		2. Providers by skill  
			Mock - <%=basePath%>training/marketplace/categories?keyword=paint
			Real - <%=basePath%>marketplace/categories?keyword=paint
			
		3. Providers by Id
			Mock - <%=basePath%>training/providers?idlist=123-456
			Real - <%=basePath%>providers?idlist=123-456
		</pre> </font>
		<h1 align="center"><input type="submit" name="InvokeServlet" value="Test SO APIs">
		</h1>
</form>
	</body>
</html>
