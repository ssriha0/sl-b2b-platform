<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/hideShow.js"></script>


		<style type="text/css">
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
		</style>
			
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/contact.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/modules.css">
			
		
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js" type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/nav.js"></script>
		<script language="javascript">
			selectedNav = function (){ $("contactLink").removeAttribute("href"); } 
			window.addEvent('domready',selectedNav);
		</script>
		
	</head>

	<body class="tundra">
         
		<div id="page_margins">
		
			<div id="page">
			
				<tiles:insertAttribute name="header" />

				<tiles:insertAttribute name="body" />

			</div>
			
			<tiles:insertAttribute name="footer" />
			
		</div>

	</body>

</html>
