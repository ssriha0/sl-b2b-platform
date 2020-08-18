<jsp:include page="/jsp/public/common/commonIncludes.jsp" />

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
 <jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="JoinNow.proBuyer"/>
	</jsp:include>	

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<head>
		<title>Join ServiceLive</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
		<!--[if lt IE 8]>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
		<![endif]-->
		<script language="JavaScript" src="${staticContextPath}/javascript/js_registration/tooltip.js" type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/nav.js"></script>
		<script language="javascript">
			jQuery(document).ready(function($){
				$("#businesses").addClass("selected");
			});
		</script>
	</head>
	<body class="tundra acquity">
	
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="Join Now - Pro"/>
		</jsp:include>	
	
		<div id="page_margins">
			<div id="page" class="clearfix">
				<!-- BEGIN HEADER -->
				<div id="headerJoinNow">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>

					
					<div id="rightRail">
						<img
							src="${staticContextPath}/images/buyer_registration/joinNow_biz_rightRailBg.gif"
							alt="Get Started Now" border="0" usemap="#Map"
							title="Get Started Now" />
						<map name="Map">
							<area shape="rect" coords="189,222,254,241" href="buyerRegistrationAction.action"
								alt="Join Now">
						</map>
					</div>
				</div>
				<!-- BEGIN CENTER -->
				<div id="bizOwner" class="heroJoinNow clearfix">
					<div id="column1" style="padding-left: 300px;">
						<p>
							<img
								src="${staticContextPath}/images/buyer_registration/hdr_joinNowServiceSimplified.gif"
								alt="Why Join ServiceLive?" title="Why Join ServiceLive?" />
						</p>
						<p class="paddingBtm">
							ServiceLive saves your business time and money by quickly connecting you to qualified 
							service professionals who work within your schedule and budget. Find providers based 
							on specific skills and use our online tools to automate your entire project management processes.
						</p>
							<s:a href="buyerRegistrationAction.action"> 
								<img
									src="${staticContextPath}/images/common/spacer.gif"
									width="94" height="26"
									style="background-image: url(${staticContextPath}/images/btn/btn_join_now.gif); float: left;"
									class="btn26Bevel" />
							</s:a> <a style="display: block; float: left; margin-top: 5px;" href="${contextPath}/joinNowGeneralAction.action">Not a commercial buyer?</a>
					</div>

				</div>
				<div class="joinNowBtm clearfix">
					<p>
						<img
							src="${staticContextPath}/images/buyer_registration/hdr_benefitsOfReg.gif"
							alt="The Benefits of Registering" title="The Benefits of Registering" />
					</p>
					<p>
						ServiceLive has obvious benefits to businesses of all size - from mom and pop contractors to national enterprises. 
						Here are some ways to use ServiceLive to build your business:
					</p>
					<ul>

						<li><strong>Build an on-demand workforce</strong>
							<p>Hire when needed. Ramp up for big projects or keep your company small while you're slow. </p>
						</li>
						<li><strong>Expand your service offering</strong>
							<p>Offer more services through your company. Sub-contract jobs to providers whose skills complement yours.</p>
						</li>

					</ul>
					<ul id="column2">
						<li><strong>Grow outside your market</strong>
							<p>Sell products and services in one part of the country to be delivered by providers in another.</p>
						</li>
						<li><strong>Meet warranty obligations</strong>
							<p>Keep warranty costs low by hiring providers nationwide to make repairs on an as-needed basis. </p>

						</li>

					</ul>
					<p style="clear: left;">
						<br clear="all" />
						<p>
							<s:a href="buyerRegistrationAction.action"> 
								<img
									src="${staticContextPath}/images/common/spacer.gif"
									width="94" height="26"
									style="background-image: url(${staticContextPath}/images/btn/btn_join_now.gif); float: left;"
									class="btn26Bevel" />
							</s:a> <a style="display: block; float: left; margin-top: 5px;" href="${contextPath}/joinNowGeneralAction.action">Not a commercial buyer?</a>
						</p>
					</p>
				</div>
			</div>

			<!-- END CENTER   -->
			<!-- START FOOTER NAV -->
			<s:action name="blackoutStatesAction" executeResult="true" />
			<!-- END FOOTER NAV -->
		</div>		
	</body>
</html>
