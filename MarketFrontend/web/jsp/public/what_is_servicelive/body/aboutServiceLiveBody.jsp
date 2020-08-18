<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<html>
	<head>
		<title>About ServiceLive</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main_what_is.css">
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
	</head>
	<body class="tundra">

				<div id="headerShort-rightRail">
					<tiles:insertDefinition name="newco.base.topnav"/> 					
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<div id="auxNav"></div>
					<div id="pageHeader">
						
						<img src="${staticContextPath}/images/what_is_sl/hdr_whatIsAboutSL.gif"
							alt="What is ServiceLive? | About ServiceLive"
							title="What is ServiceLive? | About ServiceLive" />
					</div>
					
				</div>
				<!-- BEGIN CENTER -->
				<div class="hero clearfix" id="aboutSLHero">
					<div class="content">
						<p class="paddingBtm">
							<img src="${staticContextPath}/images/what_is_sl/hdr_aboutSLHero.gif"
								alt="A New Way to Buy Service" title="A New Way to Buy Service" />
						</p>
						<p>
							ServiceLive is an enterprise-level project management application
							available over the Internet. Thousands of service providers have
							registered nationwide and some of the biggest companies in
							America are registered to buy service through ServiceLive.
						</p>
					</div>
					
				</div>
				<%-- Join a winning Parnership --%>
				<div class="landingContent">
					<img src="${staticContextPath}/images/what_is_sl/hdr_joinPartnership.gif" />
					<div class="content">
						<p>
							ServiceLive represents the next generation of e-commerce. Consumers
							have become accustomed to buying books and tickets online. They
							expect the same convenience when buying service and support.
							ServiceLive gives it to them, with tools that let them research, buy
							from and communicate with the people they choose to work with.
						</p>
						<p>
							Buyers and providers will help us evolve the ServiceLive marketplace
							over time. If you are a provider with a skill that's not currently
							offered through ServiceLive, or if you are a buyer who would purchase
							a service if it were offered, let us know when you are building your
							profile. This is your system, and we want to help you get the most
							from it.
						</p>
					</div>				
				</div>
									
