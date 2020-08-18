<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:set var="loginActionPath" scope="request" value="<%="https://" + request.getServerName() + ":" + System.getProperty("https.port") + request.getContextPath() + "/doLogin.action"%>"/>

<%@ taglib prefix="s" uri="/struts-tags" %>
<body>
<form action="${loginActionPath}" method="post" name="doLogin">
<tr>
</tr>

  <tr>
   <td colspan="2">
         <s:actionerror />
         <s:fielderror />
   </td>
  </tr>

Login name:<s:textfield name="username" label="Login name" theme="simple"/>
Password:<s:password name="password" label="Password" theme="simple"/>

<s:submit value="Login" align="center"/>


</form>

</body>
