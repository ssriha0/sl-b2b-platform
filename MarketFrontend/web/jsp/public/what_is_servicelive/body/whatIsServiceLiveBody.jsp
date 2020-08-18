<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
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
		<script language="javascript">
	selectedNav = function (){ $("whatIsSL").addClass("selected"); } 
	window.addEvent('domready',selectedNav);
</script>
	</head>
	<body class="tundra">
		<div id="page_margins">
			<div id="page">
				<div id="headerRightRail">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<div id="rightRail">
						<img
							src="${staticContextPath}/images/buyer_registration/joinNow_biz_rightRailBg.gif"
							alt="Get Started Now" border="0" usemap="#Map"
							title="Get Started Now" />
						<map name="Map">
							<area shape="rect" coords="189,222,254,241" href="#"
								alt="Join Now">
						</map>
						<div class="content">
							<img
								src="${staticContextPath}/images/buyer_registration/promo_notBizOwner.gif"
								alt="Not a Business Owner?" border="0" usemap="#Map2"
								title="Not a Service Provider" />
							<map name="Map2">
								<area shape="rect" coords="41,41,88,55" href="#"
									alt="Not a Service Provider?">
							</map>
						</div>
					</div>
				</div>
				<!-- BEGIN CENTER -->
				<div class="hero clearfix" id="whatIsSLHero">
					<div class="content">
						<p>
							<img src="${staticContextPath}/images/what_is_sl/hdr_whatIsHero.gif"
								alt="A quick, convenient way to
bring service professionals together."
								title="A quick, convenient way to
bring service professionals together." />
						</p>
						<p class="paddingBtm">
							ServiceLive is an online marketplace that connects people who buy
							and provide home services. Buyers set their price and providers
							compete on their skills. Online tools facilitate clear,
							free-flowing communications so buyers and providers can spend
							more time working and less time negotiating and managing their
							projects
						</p>
					</div>
				</div>
				<div class="landingContent" id="whatIsSLContent">
					<div class="landingBucket marginRight">
						<img src="${staticContextPath}/images/what_is_sl/hdr_forSP.gif"
							alt="For Homeowners" title="For Homeowners" />
						<div class="content">
							<p>
								Spend less time looking for service orders and more time doing the work
								you want.
							</p>
							<ul class="inlineList bucketLinks clearfix">
								<li>
									<a href="">> Learn More</a>
								</li>
								<li>
									<a href="">> Join Now</a>
								</li>
							</ul>
						</div>
					</div>
					<div class="landingBucket marginRight">
						<img src="${staticContextPath}/images/what_is_sl/hdr_forHome.gif"
							alt="For Service Providers" title="For Service Providers" />
						<div class="content">
							<p>
								Review buyer ratings to find a provider you can trust at a price
								you can afford.
							</p>
							<ul class="inlineList bucketLinks clearfix">
								<li>
									<a href="">> Learn More</a>
								</li>
								<li>
									<a href="">> Join Now</a>
								</li>
							</ul>
						</div>
					</div>
					<div class="landingBucket">
						<img src="${staticContextPath}/images/what_is_sl/hdr_forBiz.gif"
							alt="For Businesses" title="For Businesses" />
						<div class="content">
							<p>
								Build an on-demand workforce with the skills you need, when you
								need them.
							</p>
							<ul class="inlineList bucketLinks clearfix">
								<li>
									<a href="">> Learn More</a>
								</li>
								<li>
									<a href="">> Join Now</a>
								</li>
							</ul>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="howItWorks">
						<img src="${staticContextPath}/images/what_is_sl/hdr_howItWorks.gif"
							alt="How It Works" title="How It Works" />
						<div class="content">
							<p>
								ServiceLive saves you time by automating the process of finding
								and hiring service providers. Time-consuming searches and
								negotiations on the telephone are reduced so both buyers and
								providers can be more productive. Here's how it works:
							</p>
						</div>
					</div>
					<div class="clearfix howItWorksGraph">
						<img src="${staticContextPath}/images/what_is_sl/how_it_works_graph.gif"
							alt="How It Works" title="How It Works" />
					</div>
				</div>
			</div>
			<!-- END CENTER   -->
		</div>
	</body>
</html>
