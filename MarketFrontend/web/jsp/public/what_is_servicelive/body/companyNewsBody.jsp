<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<html>
	<head>
		<title>ServiceLive News</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
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
	selectedSubNav = function (){ $("compNewsLink").addClass("selectedSub"); } 
	window.addEvent('domready',selectedSubNav);
</script>
	</head>
	<body class="tundra">
		<div id="page_margins">
			<div id="page">
				<div id="headerShort-rightRail">
					<tiles:insertDefinition name="newco.base.topnav"/> 					
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<div id="pageHeader">
						<img
							src="${staticContextPath}/images/what_is_sl/hdr_whatIsCompanyNews.gif"
							alt="What is ServiceLive? | Company News"
							title="What is ServiceLive? | Company News" />
					</div>
					<div id="rightRail">
						<img src="${staticContextPath}/images/modules/stayConnected_bleed.gif"
							alt="Stay Connected" border="0" usemap="#Map"
							title="Stay Connected" />
						<div class="content">
							<%-- Modules go here. --%>
						</div>
					</div>
				</div>
				<!-- BEGIN CENTER -->
				<div class="hero clearfix" id="compNewsHero">
					<div class="content">
						<p class="paddingBtm">
							<img src="${staticContextPath}/images/what_is_sl/hdr_slNewsHero.gif"
								alt="ServiceLive in the News" title="ServiceLive in the News" />
						</p>
						<p>
							ServiceLive has been making headlines by changing the way service
							transactions occur. Journalists interested in covering
							ServiceLive should refer media inquiries to
							<a href="mailto:support@servicelive.com">E-MAIL</a> or
							<a href="">PHONE</a>.
						</p>
					</div>
				</div>
				<div class="landingContent">
					<div id="topNewsHdr">
						Thursday, August 9, 2007
					</div>

					<table class="topNews" cellpadding="0" cellspacing="0">
						<tr>
							<td class="column1">
								10-22-07
							</td>
							<td class="column2">
								<a href="">Aenean purus. Ut porta felis. Sed eu metus vel
									tellus lacinia posuere.</a>
								<br>

								Vestibulum eget eros. Nulla ultrices. Praesent tempus dapibus
								diam. Nulla rhoncus lobortis orci. Aliquam luctus, mi ac
								faucibus commodo, lorem quam consectetuer felis, ac rutrum ipsum
								ante a tortor. Cras tellus turpis, eleifend sed, elementum sed,
								ultrices ac, sem. Suspendisse erat. Morbi in arcu in massa
								faucibus mattis.
								<p>
									<a href="">> More</a>
								</p>
							</td>
						</tr>
						<tr>
							<td class="column1">
								10-22-07
							</td>
							<td class="column2">
								<a href="">Aenean purus. Ut porta felis. Sed eu metus vel
									tellus lacinia posuere.</a>
								<br>

								Vestibulum eget eros. Nulla ultrices. Praesent tempus dapibus
								diam. Nulla rhoncus lobortis orci. Aliquam luctus, mi ac
								faucibus commodo, lorem quam consectetuer felis, ac rutrum ipsum
								ante a tortor. Cras tellus turpis, eleifend sed, elementum sed,
								ultrices ac, sem. Suspendisse erat. Morbi in arcu in massa
								faucibus mattis.
								<p>
									<a href="">> More</a>
								</p>
							</td>
						</tr>
						<tr>
							<td class="column1">
								10-22-07
							</td>
							<td class="column2">
								<a href="">Aenean purus. Ut porta felis. Sed eu metus vel
									tellus lacinia posuere.</a>
								<br>

								Vestibulum eget eros. Nulla ultrices. Praesent tempus dapibus
								diam. Nulla rhoncus lobortis orci. Aliquam luctus, mi ac
								faucibus commodo, lorem quam consectetuer felis, ac rutrum ipsum
								ante a tortor. Cras tellus turpis, eleifend sed, elementum sed,
								ultrices ac, sem. Suspendisse erat. Morbi in arcu in massa
								faucibus mattis.
								<p>
									<a href="">> More</a>
								</p>
							</td>
						</tr>
						<tr>
							<td class="column1">
								10-22-07
							</td>
							<td class="column2">
								<a href="">Aenean purus. Ut porta felis. Sed eu metus vel
									tellus lacinia posuere.</a>
								<br>

								Vestibulum eget eros. Nulla ultrices. Praesent tempus dapibus
								diam. Nulla rhoncus lobortis orci. Aliquam luctus, mi ac
								faucibus commodo, lorem quam consectetuer felis, ac rutrum ipsum
								ante a tortor. Cras tellus turpis, eleifend sed, elementum sed,
								ultrices ac, sem. Suspendisse erat. Morbi in arcu in massa
								faucibus mattis.
								<p>
									<a href="">> More</a>
								</p>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<!-- END CENTER   -->
			
		</div>
	</body>
</html>
