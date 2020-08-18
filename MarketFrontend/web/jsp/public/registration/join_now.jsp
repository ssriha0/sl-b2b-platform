<jsp:include page="/jsp/public/common/commonIncludes.jsp" />

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

 <jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="JoinNow"/>
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
		<script language="JavaScript"
			src="${staticContextPath}/javascript/js_registration/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
	</head>
	<body class="tundra acquity">
	
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="Join Now - registration directory"/>
		</jsp:include>	
	
		<div id="page_margins">
			<div id="page" class="clearfix">
				<!-- BEGIN HEADER -->
				<div id="headerJoinNow">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<div id="auxNav"></div>
				</div>
				<!-- BEGIN CENTER -->
				<div id="servicePro" class="heroJoinNow clearfix">
					<div id="column1" style="padding-left: 300px;">
						<p>
							<img
								src="${staticContextPath}/images/sp_firm_registration/hdr_whyJoin.png"
								alt="Why Join ServiceLive?" title="Why Join ServiceLive?" />
						</p>
						<p class="paddingBtm">
							Hire service professionals on your terms, at your price, and on your schedule. 
							ServiceLive makes it easy to find the pre-screened service provider you need to tackle 
							your tough jobs. Name your price, let service providers compete for your business, and release payment 
							only when you're satisfied, and even rate the work.-it's that simple.
						</p>

					</div>

				</div>
				<div class="clearfix" style="width: 300px; height: 400px; padding: 10px; border-right: 1px solid silver; clear: left; float: left;">
					<p>
						<img
							src="${staticContextPath}/images/sp_firm_registration/hdr_slSimple.png"
							alt="ServiceLive SIMPLE" title="ServiceLive SIMPLE" />
					</p>
					<p>
						Looking to have a flat panel TV installed in your home, or computer network set up for your company? 
						Maybe you're ready to hire help for that tough flooring or painting project? From simple repairs and 
						installations at home to help with all the service needs of small businesses, ServiceLive Home/Office 
						provides the fast, easy access to service professionals you need.
					</p>
					<p>
						Perfect for:
					</p>
					<ul style="margin: 20px;">
						<li> <strong>Home and Home Offices</strong></li>
						<li> <strong>Small Businesses</strong></li>
					</ul>
					<p style="clear: left;">
						<s:a href="csoCreateAccount_displayPage.action?fromHome=y">
							<img
								src="${staticContextPath}/images/common/spacer.gif"
								width="94" height="26"
								style="background-image: url(${staticContextPath}/images/btn/btn_join_now.gif);"
								class="btn26Bevel" />
						</s:a>
						<br /><a href="${contextPath}/joinNowBuyerSimpleAction.action">I'd like more information. </a> 
					</p>

				</div>
				<div class="clearfix" style="width: 300px; height: 400px; padding: 10px; border-right: 1px solid silver;float: left;">
					<p>
						<img
							src="${staticContextPath}/images/sp_firm_registration/hdr_slPro.png"
							alt="ServiceLive PROFESSIONAL" title="ServiceLive PROFESSIONAL" />
					</p>
					<p>
						Build an on-demand workforce with the skills you need when you need them.
					</p>
					<p>
						Whether your business just needs another set of hands or you are a services buyer for a large corporation, 
						ServiceLive can help you find service providers who have background checks, insurance and the exact set of 
						skills you need. Hire the workforce you need as you need them.
					</p>
					<p>
						Whatever your needs, ServiceLive has the service resources and a robust project management system to help you 
						streamline, manage, and grow your business.
					</p>
					<p>
						Perfect for:
					</p>
					<ul style="margin: 20px;">
						<li><strong>Professional Services Companies</strong></li>
						<li><strong>Service Contractors</strong></li>
						<li><strong>Mid-size Companies</strong></li>
						<li><strong>Enterprise Services Departments</strong></li>
					</ul>
					<p style="clear: left;">
						Ask about our OnDemand hosted solutions!
					</p>
					<p>
						<s:a href="buyerRegistrationAction.action">
							<img
								src="${staticContextPath}/images/common/spacer.gif"
								width="94" height="26"
								style="background-image: url(${staticContextPath}/images/btn/btn_join_now.gif);"
								class="btn26Bevel" />
						</s:a>
						<br /><a href="${contextPath}/joinNowBuyerAction.action">I'd like more information. </a> 
					</p>
				</div>
				<div class="clearfix" style="width: 300px; padding: 10px;float: left;">
					<p>
						<img
							src="${staticContextPath}/images/sp_firm_registration/hdr_slProviders.png"
							alt="Service Providers" title="Service Providers" />
					</p>
					<p>
						ServiceLive can help you maximize profitability while minimizing advertising expenses. Log on when work is 
						slow to connect with homeowners and other service buyers who need your specific set of skills. Accept work 
						when you need it, pass on it when you don't.
					</p>
					<p>
						<s:a href="providerRegistrationAction.action"> 
							<img
								src="${staticContextPath}/images/common/spacer.gif"
								width="94" height="26"
								style="background-image: url(${staticContextPath}/images/btn/btn_join_now.gif);"
								class="btn26Bevel" />
						</s:a> 
						<br /><a href="${contextPath}/joinNowAction.action">I'd like more information. </a>
					</p>
				</div>
			</div>

			<!-- END CENTER   -->
			<!-- START FOOTER NAV -->
				<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER NAV -->
		</div>
	</body>
</html>
