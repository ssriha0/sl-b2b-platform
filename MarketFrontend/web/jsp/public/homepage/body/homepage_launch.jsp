<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>Welcome To ServiceLive</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/homepage.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/modules.css" />
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
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="HomePage - old"/>
	</jsp:include>	
		<div id="page_margins">
			<div id="page">
				<div id="headerRightRail">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<div id="rightRail">
						<jsp:include
							page="/jsp/public/homepage/body/modules/join_now_top2.jsp" />
						<div class="content">
							<img src="${staticContextPath}/images/modules/whosOnSL_narrow.gif"
								alt="image" title="Who's on ServiceLive" />
							<jsp:include
								page="/jsp/public/homepage/body/modules/join_forums_narrow2.jsp" />
						</div>
					</div>
				</div>

				<!-- BEGIN CENTER -->
				<div class="hero clearfix" id="hero_homepage">
					<div class="content">

						<s:form action="joinNowAction" theme="simple">
								<table>
									<tr>
										<td><s:submit type="input"
											cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/providersJoinNow.gif);  width:130px; height:25px;"
											cssClass="btn25Bevel" theme="simple" value="" />
										</td>
										<c:if test="${not empty dev_qa_application_environment}">
 											<td><p><a href="joinNowBuyerAction.action">Not a Service Provider?</a></p></td>
										</c:if>
									</tr>
								</table>
						</s:form>
					</div>
				</div>
				<div id="homepageContent" class="landingContent">
					<div id="servicePro_panel">
						<div class="content">
							<p>
								Bid on contracts, grow your customer base and market your
								expertise.
							</p>
							<p>
								<a
									href="${contextPath}/jsp/public/what_is_servicelive/body/what_is_sl_landing.jsp">
									More</a>&nbsp;&nbsp;&nbsp;
								<a href="joinNowAction.action">Join Now</a>
							</p>
						</div>
					</div>
					<div id="serviceBuyerNeeds">
						<div class="content">
							<p>
								<img
									src="${staticContextPath}/images/homepage/hdr_serviceBuyersLooking.gif"
									width="254" height="19" alt="image"
									title="Service buyers are looking for" />
							</p>
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td width="190">
										<ul class="vertBulleted">
											<li>
												Home Electronics Service
											</li>
											<li>
												HVAC
											</li>
											<li>
												Automotive
											</li>
											<li>
												Carpentry & Woodworking
											</li>
											<li>
												Appliance Installation
											</li>
										</ul>
									</td>
									<td width="190">
										<ul class="vertBulleted">
											<li>
												Delivery Services
											</li>
											<li>
												Kitchens & Countertops
											</li>
											<li>
												Computer/Network Services
											</li>
											<li>
												Handyman Service
											</li>
											<li>
												Walls & Ceilings
											</li>
										</ul>
									</td>
									<td width="150">
										<ul class="vertBulleted">
											<li>
												Product Assembly
											</li>
											<li>
												Roofing & Siding
											</li>
											<li>
												Plumbing
											</li>
											<li>
												Flooring
											</li>
											<li>
												Lawn & Garden
											</li>
										</ul>
										<a href="soWhatIsSL_services.action">> See complete list</a>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
				<!-- END CENTER   -->
				<jsp:include page="/jsp/public/common/defaultFooter.jsp" />

			</div>
	</body>
</html>
