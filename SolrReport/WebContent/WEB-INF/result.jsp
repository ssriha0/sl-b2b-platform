<html>
<head>
<meta content="text/html; charset=ISO-8859-1" http-equiv="content-type">
<title>Get Solr Search Report</title>
<style type="text/css" media="screen">
<!--
body {
	padding: 0;
	margin: 0;
	background-color: #666;
	color: #000;
	text-align: center;
}

#contents {
	margin-top: 10px;
	margin-bottom: 10px;
	margin-right: auto;
	margin-left: auto;
	width: 600px;
	padding: 10px;
	background-color: #FFF;
	color: #000;
	text-align: left;
}

h1 {
	color: #333;
	background-color: transparent;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 20px;
}

p {
	color: #333;
	background-color: transparent;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 0.8em;
}

.code {
	color: #339;
	background-color: transparent;
	font-family: times, serif;
	font-size: 0.9em;
	padding-left: 40px;
}
-->
</style>
<%@ page import="com.servicelive.*,java.util.*" %>
<%  
   List list = (List)session.getAttribute("list");
  
   
   if (list == null)
	   response.sendRedirect("/index.jsp");
   String welcome = (String)session.getAttribute("welcome");
   if (welcome == null)
	   welcome = "Welcome";
   
   System.out.println("Size:" + list.size());
%>
</head>
<body>
<div id="contents">
<form method="get" action="index.jsp" name="report">
<dl style="text-align: center;">
	<dt><br>
	</dt>
	
	<h1><%out.print(welcome);%></h1>
	<br>
</dl>
<table style="text-align: left; width: 554px; height: 60px;" border="1"
	cellpadding="2" cellspacing="2">
	<tbody>
		<tr>
			<th style="vertical-align: top;">Keyword<br>
			</th>
			<th style="vertical-align: top;">Count<br>
			</th>
		</tr>
		<% for( int i =0; i < list.size(); i++ ) { 
			 ReportBean bean = (ReportBean)list.get(i);
		 %>
		<tr>
			<td style="vertical-align: top;"><% out.print(bean.getName()); %></td>
			<td style="vertical-align: top;"><% out.print(bean.getCount()); %></td>
		</tr>
		<% } %>
	</tbody>
</table>
<dl style="text-align: center;">
	<br>
	<dd><br>
	</dd>
</dl>
<div style="text-align: center;">
<input type="submit" value="Home" />
<br>
</div>
</form>
</div>
</body>
</html>

