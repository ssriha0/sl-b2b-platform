<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<jsp:include page="/jsp/public/common/commonIncludes.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive [California Privacy Policy]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />


			
		<style type="text/css">
		@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
		@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
</style>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<script language="javascript">
	//selectedNav = function (){ $("profileAndPrefs").addClass("selected"); } 
	//window.addEvent('domready',selectedNav);
</script>

			<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			       <jsp:param name="PageName" value="CaPrivacyPolicy"/>
			</jsp:include>
	</head>
	<body class="tundra">
	<!--  <body class="tundra" onload="displayBanner()">-->
		<div id="page_margins">
			<div id="page">
				<!-- START HEADER -->
				<div id="header">
					<tiles:insertDefinition name="newco.base.topnav"/> 					
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
					<div id="pageHeader">
						<h1>California Privacy Policy</h1>
					</div>
				</div>
				<!-- END HEADER -->
				<div>
					<div class="content" style="padding: 0 20px; color: #000;">
						<p>
							If you are a California resident and ServiceLive customer you have the right to request information from ServiceLive regarding the manner in which ServiceLive shares certain categories of your personal information with third parties, for the third parties' direct marketing purposes. California law provides that you have the right to submit a request to ServiceLive at its designated address and receive the following information: 1) the categories of information ServiceLive disclosed to third parties for the third parties' direct marketing purposes during the preceding calendar year; 2) the names and addresses of third parties that received such information; and 3) if the nature of a third party's business cannot be reasonably determined from the third party's name, examples of the products or services marketed. You are entitled to receive a copy of this information in a standardized format and the information will not be specific to you individually. ServiceLive's designated email address for such requests is support@ServiceLive.com.
						</p>
						<p style="font-weight: bold;">Effective Date: November 6, 2008</p>
							
							
							
							
						<h2>Contact</h2>
							
						<p style="font-size: 12px;">
							In the event that you have any questions about ServiceLive.com's Privacy Statement or if you have reason to believe that ServiceLive.com may have failed to adhere to this Privacy Statement, you may contact us at:
							</p>
							<p style="font-size: 12px;">
							<br />
							<strong>Transform ServiceLive LLC.
							<br />
							Attention: ServiceLive.com Team</strong>
							<br />
							3333 Beverly Rd, B6-244A
							<br />
							Hoffman Estates, Illinois 60179
							<br />
						</p>
					</div>
				</div>
				<div class="colRight255">

				</div>
				<div class="clear"></div>
			</div>


			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />

		</div>
	</body>
</html>
