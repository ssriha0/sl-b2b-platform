<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>ServiceLive Tutorials</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
<style type="text/css">
@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
 @import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
</style>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/support.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dojo_slideshow.css" />
<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/nav.js"></script>
</head>
<body class="tundra noBg">
<img src="${staticContextPath}/images/support/hero_tutorials.jpg" width="715" height="265" />
</body>
</html>
