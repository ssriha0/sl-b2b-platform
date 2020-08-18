<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<meta http-equiv="Content-Language" content="en-us">
		<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
		<title>ServiceLive Inc.</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
				djConfig="isDebug: false ,parseOnLoad: true"></script>
				
		<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.TitlePane");
			dojo.require("dojo.parser");
			dojo.require("newco.rthelper");
			dojo.require("newco.jsutils");
		</script>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/grid.css"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/advanced_grid.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/global.css" />
		
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/dojo/dojo/resources/dojo.css"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-searssl.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/dojo/dijit/themes/searssl/searssl.css"/>
		
	</head>

	<body class="searssl">
		<jsp:include page="/jsp/public/common/defaultLogo.jsp" />
				
		<div align="center">
			<table border="0" cellspacing="0" cellpadding="0" width="799">
				<tr>
					<td width="2" height="2"></td>
					<td height="2" width="8"></td>
					<td height="2" width="435" colspan="2"></td>
					<td height="2" width="354" colspan="4"></td>
					<td width="1" height="2"></td>
				</tr>
				<tr>
					<td width="2"></td>
					<td bgcolor="#FFFFFF" colspan="7" width="800">&nbsp;</td>
					<td width="1"></td>
				</tr>
				<tr>
					<td width="2"></td>
					<td bgcolor="#FFFFFF">&nbsp;</td>
					<td bgcolor="#FFFFFF" colspan="2">&nbsp;</td>
					<td bgcolor="#FFFFFF" width="330" colspan="4">&nbsp;</td>
					<td width="1"></td>
				</tr>
				<tr>
					<td width="2"></td>
					<td bgcolor="#FFFFFF" colspan="7">&nbsp;</td>
					<td width="1"></td>
				</tr>
				<tr>
					<td width="2"></td>
					<td bgcolor="#FFFFFF" colspan="7">
					<p align="left">
					<font face="Arial"><br></font></td>
					<td width="1"></td>
				</tr>
				<tr>
				<td colspan="3">
				<div class="error">
				<html:errors />
				</div>
				</td>
				</tr>
				<tr>
					<td width="2"></td>
					<td bgcolor="#FFFFFF" colspan="7">
					<p align="center"><font face="Arial" size="5">We're Sorry!</font></td>
					<td width="1"></td>
				</tr>
				<tr height="10">
				    <td>
				    </td>
				</tr>
				<tr>
					<td width="2"></td>
					<td bgcolor="#FFFFFF" align="center" colspan="2">
					&nbsp;</td>
					<td bgcolor="#FFFFFF" align="center" colspan="3">
					<font face="Arial">ServiceLive.com is currently experiencing technical difficulties.&nbsp; Please try again later and sorry for any inconvenience.</font></td>
					<td bgcolor="#FFFFFF" align="center" colspan="2">
					&nbsp;</td>
					<td width="1"></td>
				</tr>
				<tr height="10">
				    <td>
				    </td>
				</tr>
				<tr>
					<td width="2" height="20"></td>
					<td bgcolor="#FFFFFF" height="20">&nbsp;</td>
					<td bgcolor="#FFFFFF" colspan="2" height="20" align="center">&nbsp;</td>
					<td bgcolor="#FFFFFF" width="354" colspan="4" height="20" align="center">
					<font face="Arial">--ServiceLive Staff </font></td>
					<td width="1" height="20"></td>
				</tr>
				<tr>
				<td colspan="6" align="center">
					<div id="footer">
			  		<p class="lightBlue"><a href="">About Us</a>|<a href="">Terms of Use</a>|<a href="https://transformco.com/privacy#_Toc31123888" target="_blank">California Privacy Rights</a>|<a href="https://transformco.com/privacy" target="_blank">Privacy Policy (Revised 06/01/2007)</a></p>
			  		<p>© 2007 ServiceLive. All rights reserved. © 2007. Sears Brand, LLC.</p>
					</div>
				</td>
				</tr>
				</table>
		</div>
		
		
	</body>
</html>
