<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="loginActionPath" scope="request" value="<%="https://" + request.getServerName() + ":" + System.getProperty("https.port") + request.getContextPath() + "/doLogin.action"%>"/>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
			
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			<jsp:param name="PageName" value="ProRegCongrats"/>
		</jsp:include>	
	</head>
	<body class="tundra">
	    
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
        		 <jsp:param name="PageName" value="Provider Registration - Confirmation"/>
 				<jsp:param name="eventName" value="event1"/>
		</jsp:include>
		<form action="${loginActionPath}" method="post" name="doLogin">
			<div id="page_margins">
				<div id="page" class="clearfix">

					<!-- BEGIN HEADER -->
					<div id="headerSPReg">
						<tiles:insertDefinition name="newco.base.topnav"/>
						<tiles:insertDefinition name="newco.base.blue_nav"/>
						<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
							
						<div id="pageHeader">
  							<div> <img src="${staticContextPath}/images/images_registration/sp_firm_registration/hdr_congrats.gif" alt="Congratulations" title="Congratulations" />
 						 		</div>
							</div>
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
								Congratulations! <br/>
							</h3>
							<p> You have completed your provider profile and your application is in the process of being reviewed. 
							We will contact you when that process has completed. Please be sure to complete the background check from the link provided in your email. Once ServiceLive has approved your profile, you will be able to receive service orders. At this time you can add additional team members and enhance your ServiceLive skill footprint.</p>

							
							<p>
								<a href="${contextPath}/manageUserActiondoLoadUsers.action">Manage My Team</a>
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
