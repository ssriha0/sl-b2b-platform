<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="loginActionPath" scope="request" value="<%="https://" + request.getServerName() + ":" + System.getProperty("https.port") + request.getContextPath() + "/doLogin.action"%>"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive [Provider Registration]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit.Dialog");
			dojo.require("dojo.parser");
		dojo.require("dijit.layout.LinkPane");
			//dojo.require("newco.servicelive.SOMRealTimeManager");
			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
		</script>
		<script>
function loadHomepage(){
	document.forms[0].action='login.action';
	document.forms[0].submit();
}
</script>
		<style type="text/css">
@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css"
	;

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css"
	;
</style>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/contact.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/login.css" />
		<script language="JavaScript"
			src="${staticContextPath}/javascript/js_registration/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/formfields.js"></script>
	</head>
	<body class="tundra">
	    
		<form action="${loginActionPath}" method="post" name="doLogin">
			<div id="page_margins">
				<div id="page" class="clearfix">
					<!-- BEGIN HEADER -->
					<div id="headerSPReg">
						<tiles:insertDefinition name="newco.base.topnav"/>
						<tiles:insertDefinition name="newco.base.blue_nav"/>

						<jsp:include flush="true" page="header/reg_hdr_congrats.jsp"></jsp:include>
						<jsp:include flush="true" page="header/verisign_logo.jsp"></jsp:include>
					</div>
					<!-- BEGIN RIGHT PANE -->
					<div class="colRight255 clearfix">
						<img
							src="${staticContextPath}/images/images_registration/sp_firm_registration/promoSLbringsYou.gif"
							alt="Did You Know" title="Did You Know" />
					</div>
					<div class="colLeft711">
						<div class="content">

							<h3>
								Your ServiceLive Provider account has been created! We are currently in our Beta launch phase until early January 2008.
							</h3>
							<p>
								Since we are in our Beta launch, we are only allowing providers in the Beta launch market to complete their profile and gain access the website.  
								We encourage you to participate in our <a href="http://community.servicelive.com">community forums</a> to get the latest news 
								about ServiceLive.
							</p>
							<h3>
								Want to know more?
							</h3>
							<p>
								Please visit our <a href="tutorial_main.jsp" onClick="return (false);">tutorials</a> to get a better understanding of how ServiceLive will work for you.
							</p>
							<p>
								<input type="image"
									src="${staticContextPath}/images/images_registration/common/spacer.gif"
									width="149" height="20"
									style="background-image: url(${staticContextPath}/images/images_registration/btn/returnToHomePage.gif);"
									class="btn20Bevel" onclick="location.href='http://provider.servicelive.com/login.html'" />
							</p>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				<!-- START FOOTER -->
				<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
				<!-- END FOOTER -->
			</div>
		</form>
	</body>
</html>
