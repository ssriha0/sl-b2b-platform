<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="pageTitle" scope="request" value="ServiceLive Home" />
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="/ServiceLiveWebUtil" />
<c:set var="loginActionPath" scope="request" value="<%="https://" + request.getServerName() + ":" + System.getProperty("https.port") + request.getContextPath()%>" />
<c:set var="showTags" scope="request" value="1" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>ServiceLive : Home Improvement & Repair</title>
<meta http-equiv="Cache-Control" content="no-cache " />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="verify-v1" content="R6L9lmtKlkFFBG+QUzk8AXqe2f+zZ+SEBsnd0++R/bo=" />
<meta name="description" content="Visit ServiceLive.com today to find service providers for your home improvement projects. ServiceLive is the only online marketplace for one-stop home improvements and repairs.">
<meta name="keywords" content="Home Improvement,Home Repairs,Handyman Services,Installation,Computer Services,TV Installation,HDTV,TV Repair,Garage Door Openers,Appliance Installation,Appliance Repair,Kitchen,Bath,Tiling,Leaks,Windows,Doors,Flooring,Carpentry,Roofing,Siding,Kitchen,Countertops,Lawn,Garden,Automotive,Auto,Plumbing,Electrical,Heating,Cooling">

<meta name="apple-itunes-app" content="app-id=899811332" />
<meta name="google-play-app" content="app-id=com.servicelive.android.servicelivepro" />
<meta name="msApplication-ID" content="microsoft.build.App"/>
<meta name="msApplication-PackageFamilyName"content="microsoft.build_8wekyb3d8bbwe"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css"/>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-homeupdates.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/home-cat.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/global.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/homepage-re.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/homepage-redesign.css" />

<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/smart-app-banner.css" />
<link rel="apple-touch-icon" href="${staticContextPath}/images/icons/mobile_icon.png" />
<link rel="android-touch-icon" href="${staticContextPath}/images/icons/mobile_icon.png" />

<!--[if lt IE 8]>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
<![endif]-->

<!-- Commented for removing important notice text in home page -->
<!--<style type="text/css">
#newnotice { background: #FBE3E4; padding: 10px;
	color:#8A1F11;
	padding-left:30px;
	border-bottom:2px solid #FBC2C4;
	margin-bottom:1em;
	}
#newnotice a {
	color: #8A1F11;
	text-decoration: underline;
	cursor: pointer;
}

#nnmoreinfotext {
	width: 960px;
	margin: 10px auto;
	text-align: left;
	padding: 10px;
	background: #FFF;
	font-size: 12px;
	display: none;
}

#newnotice ul {
	margin: 10px 20px;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	$("a.nnmoreinfo").click(function(){
		$("#nnmoreinfotext").slideDown();
	});
	$("a.nncloseinfo").click(function(){
		$("#nnmoreinfotext").slideUp();
	});
});
</script>-->
</head>
<body class="tundra acquity" onload="displaySmartBanner();">
<!-- <body class="tundra acquity" onload="displayBanner();"> 
<jsp:include page="/jsp/public/blueprint/homePageBanner.jsp"></jsp:include>-->
<!-- Commented for removing important notice text in home page -->
	<!-- <div id="newnotice">
		<h2>Important Notice about ServiceLive. <a class="nnmoreinfo">Click for more information</a>.</h2>

		<div id="nnmoreinfotext">
			<p>Our new consumer website launches on 1/30 at 6 am.</p>
				<ul><li><strong>If you are a Service Provider on the Servicelive platform you will need to login from <a href="http://provider.servicelive.com">http://provider.servicelive.com</a>.</strong></li>
					<li><strong>If you are a Commercial Buyer you can login from <a href="http://commercial.servicelive.com">http://commercial.servicelive.com</a>.</strong></li>
					<li><strong>Existing Home/Office users can continue to login from <a href="http://www.servicelive.com">http://www.servicelive.com</a>.</strong></li>
				</ul>
			<p>Please bookmark these links for future reference. You can reach us at 1-888-549-0640 if you have any questions.</p>
			<p><a class="nncloseinfo">Close This Message</a></p>

		</div>
	</div>-->
<div id="page_margins">
<div id="page">
	<c:import url="_homepageHeader.jsp" />
 	<div id="hpWrap" class="clearfix homeFont">
    	<c:import url="_homepageContent.jsp" /><%-- /#hpContent --%>
    	<c:import url="_homepageSidebar.jsp" /><%-- /#hpSidebar --%>
	</div>
	<c:import url="_homepageFooter.jsp" />
</div>

<c:import url="_homepageModals.jsp" />  
<c:import url="_homepageTracking.jsp" />
</div>
<script type="text/javascript" src="${staticContextPath}/javascript/smart-app-banner.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqmodal/jqModal.js"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/swfobject_modified.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/homepageFunctions.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/smart_banner.js"></script>
</body>	
</html>
