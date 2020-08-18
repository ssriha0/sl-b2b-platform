<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="pageTitle" scope="request" value="ServiceLive Tutorials" />	
<c:set var="showTags" scope="request" value="1" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>${pageTitle}</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false">
		</script>
		
		<script type="text/javascript">		
			dojo.require("dojo.data.ItemFileReadStore");
			//dojo.require("dojox.image.SlideShow");
			dojo.require("dojo.parser");

			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
		</script>
		
		<style type="text/css">
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
		</style>
		
		<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
		
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/support.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dojo_slideshow.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
		<!--[if lte IE 7]>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
		<![endif]-->
		
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js" type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/swfobject_modified.js"></script>
		<script language="javascript">
			function showTutorial(x) {
				el = document.getElementById("tutorialFrame"); //$("tutorialFrame");	
				el.style.display='block';
				el.src="tutorial"+x+".jsp";
				el.className="active";
			}	
		</script>
		
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			<jsp:param name="PageName" value="Tutorial"/>
		</jsp:include>	
	</head>
	
	<c:choose>
		<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
			<body class="tundra acquity simple" onload="${onloadFunction}">
		</c:when>
		<c:otherwise>
			<body class="tundra acquity" onload="${onloadFunction}">				
		</c:otherwise>
	</c:choose>

	<div id="page_margins">
		<div id="page">
			<div id="header">
				<tiles:insertDefinition name="newco.base.topnav" />
				<tiles:insertDefinition name="newco.base.blue_nav" />
				<tiles:insertDefinition name="newco.base.dark_gray_nav" />
			</div>				
				
				<div style="padding: 10px;"><a href="support_faq.jsp">FAQ</a> | <a href="tutorial_main.jsp">Tutorials</a></div>
				
				
				<div class="colLeft711 clearfix" style="width: 745px;">
					<div class="content" style="width: 745px; padding-left: 10px;">
						<p><br /><br />
							Whether you're a service buyer or provider, the tutorials on this page will help you make the most of your 
							ServiceLive experience. Simply choose the tutorial description you're interested in and follow the navigation 
							instructions.
						</p>
						<p class="paddingBtm">
							Remember to visit this page regularly! Our tutorial library will grow along with the functionality of ServiceLive. 
							For more information, see our 
							<a title="Frequently Asked Questions" href="${contextPath}/jsp/public/support/support_faq.jsp">Frequently Asked Questions</a> 
							or contact the <a title="Contact ServiceLive" href="/MarketFrontend/contactUsAction.action">ServiceLive Support Team</a>.
						</p>





						<object id="FlashID" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="960" height="550">
						  <param name="movie" value="http://community.servicelive.com/videos/swf/main.swf" />
						  <param name="quality" value="high" />
						  <param name="wmode" value="opaque" />

						  <param name="swfversion" value="6.0.65.0" />
						  <!-- This param tag prompts users with Flash Player 6.0 r65 and higher to download the latest version of Flash Player. Delete it if you don't want users to see the prompt. -->
						  <!-- Next object tag is for non-IE browsers. So hide it from IE using IECC. -->
						  <!--[if !IE]>-->
						  <object type="application/x-shockwave-flash" data="http://community.servicelive.com/videos/swf/main.swf" width="960" height="550">
						    <!--<![endif]-->
						    <param name="quality" value="high" />
						    <param name="wmode" value="opaque" />

						    <param name="swfversion" value="6.0.65.0" />
						    <!-- The browser displays the following alternative content for users with Flash Player 6.0 and older. -->
						    <div>
						      <h4>Content on this page requires a newer version of Adobe Flash Player.</h4>
						      <p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player" width="112" height="33" /></a></p>
						    </div>
						    <!--[if !IE]>-->

						  </object>
						  <!--<![endif]-->
						</object>
						<script type="text/javascript">
						<!--
						swfobject.registerObject("FlashID");
						//-->
						</script>




					</div>
				</div>
				<div class="clearfix"></div>
			</div>
			<!-- END CENTER   -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
		</div>		
	</body>
</html>
