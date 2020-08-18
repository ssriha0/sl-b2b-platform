<jsp:include page="/jsp/public/common/commonIncludes.jsp" />

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="ContactUs.thankYou"/>
	</jsp:include>	


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>Thanks from ServiceLive</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/modules.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/contact.css">
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<script language="javascript">
	selectedNav = function (){ $("contactLink").removeAttribute("href"); } 
	window.addEvent('domready',selectedNav);
</script>
	</head>
	<body class="tundra acquity" id="fixaux">
	    
		<div id="page_margins">
			<div id="page">
				<div id="headerShort-rightRail">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav" />
					<div id="pageHeader">
						<img src="${staticContextPath}/images/contact/hdr_thankYou.gif"
							alt="Thank You" title="Thank You" />

					</div>
					<div id="rightRail">
							<jsp:include flush="true" page="/jsp/public/homepage/body/modules/corpHQ_top.jsp"></jsp:include>
						<div class="content">
							<%-- Modules go here. --%>
						</div>
					</div>
				</div>
				<!-- BEGIN CENTER -->
				<div class="hero clearfix" id="thankYouHero">
					<div class="content">

						<p>
							Your e-mail has been sent and we will be responding to it soon.
							We'll respond to all e-mails as soon as possible.
						</p>
						<p>
							In the meantime, most questions can be answered by visiting our
							<a href="${contextPath}/jsp/public/support/support_faq.jsp">FAQ</a> page.
						</p>
					</div>
				</div>

			</div>
			<!-- END CENTER   -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
		</div>
	</body>
</html>
