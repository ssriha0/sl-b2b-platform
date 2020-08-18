<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	<title><tiles:getAsString name="title"/></title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
	
	<!--  This tells our template we are using dojo in our pages -->
	
	<%--<s:head theme="ajax" debug="true"/>--%>
	
	<script type="text/javascript">
	var djConfig = {isDebug: true};
	//djConfig.debugAtAllCosts = true;
</script>
<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo.js"></script>
<script language="JavaScript" type="text/javascript">
	dojo.require("dojo.widget.*");
	dojo.require("dojo.lfx.*");
			dojo.require("dojo.lfx.extras");
			dojo.require("dojo.event.*");
			dojo.require("dojo.io.*");
			dojo.hostenv.writeIncludes();
			
	</script>
		<!-- Site CSS -->
		<tiles:insertAttribute name="siteCss"/>
		<!--  Common Site Scripts -->
		<tiles:insertAttribute name="siteScripts"/>
	</head>
	
	<body>
		<div style="border: 2px solid black; width: 800px; height: 500px; padding: 10px;">			
			<table border=0 width="100%">
				<tr>
					<td>
						<table width="100%">
							<tr>
								<td width="80%">
									 <tiles:insertAttribute name="siteHeader"/>
								</td>
								<td width="20%">
									<tiles:insertAttribute name="siteLogin"/>
								</td>
							</tr>
						</table>
					</td>				
				</tr>
				<tr>
					<td width="100%">
						<DIV STYLE="font: bold 10px Arial;color:white; font-weight:bold">					
						<table bgcolor="#00CED3" width="100%">
							<tr>								
								<td >
									<a href="">Dashboard </a> |
								</td>
								<td>
									<a href="">Service Order Monitor</a> |
								</td>
								<td >
									<a href="">Finance Monitor </a> |
								</td>
								<td>
									<a href="">Administrator Office </a> |
								</td>
								<td>
									<a href="">Communications Monitor</a> |
								</td>
								<td>
									<a href="">Profile/Preferences Manager</a> 
								</td>
								<%--		
								<td width=150>
									<a href="">Overall Status Monitor</a> |
								</td>
								<td width=150>
									<a href="">Community</a> |
								</td>
								--%>
								
							</tr>
						</table>
						</DIV>
					</td>
				</tr>
				
				<tr>
					<td>
						<table width="100%">
							<tr>
								<td>								 
					 		 		<tiles:insertAttribute name="siteContent"/>
					 		 	</td>
					 		 </tr>
					 	</table>
					 </td>
				<tr>
				<tr>
					<td height="10%" align="center">
		 		 		<tiles:insertAttribute name="siteFooter"/>
		 		 	</td>
		 		</tr>		 		 		 		 
			</table>
		</div>
	
	</body>
</html>
