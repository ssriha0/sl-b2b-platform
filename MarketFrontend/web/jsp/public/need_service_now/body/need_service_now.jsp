<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="NeedServiceNow"/>
	</jsp:include>	


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>About ServiceLive</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/modules.css" />			
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/what_is_sl.css" />
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<script language="javascript">
			selectedNav = function (){ $("needServiceNow").addClass("selected"); } 
			window.addEvent('domready',selectedNav);
		</script>
	</head>
	<body class="tundra">
	
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="Need Service Now"/>
		</jsp:include>	
	
		<div id="page_margins">
			<div id="page">
				<div id="headerRightRail">
					<tiles:insertDefinition name="newco.base.topnav"/> 					
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav" />
					<div id="rightRail">
						<jsp:include
							page="/jsp/public/homepage/body/modules/corpHQ_top.jsp" />
						<div class="content">
							<jsp:include
								page="/jsp/public/homepage/body/modules/join_forums_narrow.jsp" />
						</div>
					</div>
				</div>
				<!-- BEGIN CENTER -->
				<div class="hero clearfix" id="needServiceHero">
					<div class="content">
						<p>
							<img src="${staticContextPath}/images/what_is_sl/hdr_needService.gif"
								alt="Need Service?" title="Need Service?" />
						</p>
						<p class="paddingBtm">
							Finding the right contractor is about to get easy - very easy.
							Our new buyer tools will let you specify the skills you need,
							when you want service performed and how much you're willing to
							pay.
						</p>
					</div>
				</div>
				<div class="landingContent" id="needServiceContent">
					<img src="${staticContextPath}/images/what_is_sl/hdr_helpCallAway.gif"
						alt="Help Is Just a Call Away" title="Help Is Just a Call Away" />
					<div class="content">
						<p>
							Finding the right service provider can be challenging and time
							consuming. That's why our programmers are hard at work building
							an online application that will give you direct access to some of
							the best service providers in your area - along with a whole
							suite of tools that will help you better manage your service
							projects.
						</p>
						<p class="paddingBtm">
							In the meantime, you can contact ServiceLive for referrals to the
							many great service providers we've already registered. If you
							represent a larger organization, be sure to ask about our turnkey
							provider search and management services. No matter how big your
							company or how small your need, we'll help you connect with
							skilled providers who can work within your schedule and budget.
						</p>
					</div>
					<img src="${staticContextPath}/images/what_is_sl/hdr_callTollFree.gif"
						alt="Call Toll Free" title="Call Toll Free" />
					<div class="content">
						<p class="paddingBtm">
							If you need help finding a qualified service provider,
							<strong>call us at 1.888.549.0640</strong> on weekdays between 7
							am and 7 pm Central Standard Time.
						</p>
						<p>
							<a href="http://community.servicelive.com" >
							<img	src="${staticContextPath}/images/images_registration/common/spacer.gif"
									style="background-image: url(${staticContextPath}/images/btn/joinOnlineForums.gif);"
									width="140" height="20" />
								
						  </a>
					</div>
				</div>
			</div>

			<!-- END CENTER   -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
		</div>
	</body>
</html>
