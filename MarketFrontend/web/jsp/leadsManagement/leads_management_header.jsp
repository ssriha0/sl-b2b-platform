<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="omTabViewPermission" scope="session" value="<%=session.getAttribute("omTabView")%>" />
<c:set var="scheduleLeadIndicator" scope="session" value="<%=session.getAttribute("scheduleIndicator")%>"/>



<!DOCTYPE html>
<!--[if lt IE 8]> <html class="ie ie7"> <![endif]-->
<!--[if IE 8]> <html class="ie ie8"> <![endif]-->
<!--[if IE 9]> <html class="ie9"> <![endif]-->
<!--[if gt IE 9]><!--> <html ie8> <!--<![endif]-->
<head>
<meta charset="utf-8">
<title>ServiceLive Leads Management</title>
  	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />  
	<!--[if lte IE 8]>
       <meta http-equiv="X-UA-Compatible" content="IE=8" /> 
    <![endif]-->
	  <link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico">
    <meta name="description" content="Use this tool to manage the leads you've purchased from the moment you receive the lead until the job is done.">
    <!-- Compiled & Combined CSS for Production
    <link href="css/leads-dashboard.min.css" rel="stylesheet" media="screen">  -->
    <link rel='stylesheet' href='${contextPath}/assets/FrontEndAssets/css/styles.min.css' media="screen">
	<!--<link rel='stylesheet' href='${contextPath}/assets/FrontEndAssets/css/bootstrapDatePicker.css' media="screen">-->
     
	
    <!--Less.js for development only. Complile before production! 
    <link rel='stylesheet/less' type='text/css' href='${contextPath}/assets/FrontEndAssets/less/main.less'>-->
    <!-- This script compiles the LESS files in the browser, for dev only 
    <script type='text/javascript' src='${contextPath}/assets/FrontEndAssets/node_modules/less/dist/less-1.5.0.min.js'></script>-->
    
	<script type="text/javascript" src="${staticContextPath}/javascript/banner.js"></script>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/banner.css" />

    <!-- Grab Google CDN's jQuery, with a protocol relative URL; fall back to local if offline -->
	 <script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
     <script>window.jQuery || document.write('<script src="${contextPath}/javascript/jquery/jquery.min.js"><\/script>')</script>

    <!-- Full Bootstrap JS for development; optimize for production-->
  	<script src="${contextPath}/assets/FrontEndAssets/js/bootstrap.min.js"></script> 
    <!-- 
    jQuery List.js by Jonny Strömberg, @javve
    Documentation: https://github.com/javve/list.js -->
    <script src="${contextPath}/assets/FrontEndAssets/js/list.min.js"></script>
    <script src="${contextPath}/assets/FrontEndAssets/js/list.emptyView.min.js"></script>
    <script src="${contextPath}/assets/FrontEndAssets/js/list.customizations.js"></script>
    <script src="${contextPath}/assets/FrontEndAssets/js/jquery.timeago.js" charset="utf-8"></script>
    <script src="${contextPath}/assets/FrontEndAssets/js/picker.js" charset="utf-8"></script>
	<script src="${contextPath}/assets/FrontEndAssets/js/picker.date.js" charset="utf-8"></script>
	<script src="${contextPath}/assets/FrontEndAssets/js/picker.time.js" charset="utf-8"></script>
	<script src="${contextPath}/assets/FrontEndAssets/js/picker.legacy.js" charset="utf-8"></script>
	
    
    <!--Custom Scripts -->
	<script src="${contextPath}/assets/FrontEndAssets/js/main.js"></script>
	<script src="${contextPath}/assets/FrontEndAssets/js/moment.js"></script>
	<script src="${contextPath}/assets/FrontEndAssets/js/date.js"></script>
    <script>
    $(function(){
      $('.tooltip-target').tooltip(); });
    function displayScheduleModal(){
        var scheduleNeeded = '${scheduleLeadIndicator}';
    	if(scheduleNeeded == 'true'){
    	   $('#scheduleWidget').modal('show');
    	   <% session.removeAttribute("scheduleIndicator"); %>
    	}
    }
    
    </script>
    <style type="text/css">
	.buyerLeadError{
	 	color: red;
	   	margin-bottom: 15px;
	    padding: 15px;
	    background: none repeat scroll 0 0 #FFEACC;
	    border: 1px solid #FF9600;
	    text-align: left;
	    
	}

	.successBox{
		background-color:#F0FFF0;
	    color: #006400;
	    border:solid 2px #B4EEB4; padding: 5px; padding-left:5px; margin-bottom: 2px; font-weight:normal;position:relative;text-align:center;
	    width: 98.7%;
	}
	
	</style>
	<!--[if lt IE 9]> 
       <link rel='stylesheet' href='${contextPath}/assets/FrontEndAssets/css/ie8.css'>
       <script src='${contextPath}/assets/FrontEndAssets/js/html5shiv.js'></script>
       <script src='${contextPath}/assets/FrontEndAssets/js/respond.min.js'></script>
    <![endif]-->
    <!-- Mobile icons -->
    <link href="${staticContextPath}/font/apple-touch-icon.png" rel="apple-touch-icon" />
    <link href="${staticContextPath}/font/apple-touch-icon-76x76.png" rel="apple-touch-icon" sizes="76x76" />
    <link href="${staticContextPath}/font/FrontEndAssets-NEW/apple-touch-icon-120x120.png" rel="apple-touch-icon" sizes="120x120" />
    <link href="${staticContextPath}/font/apple-touch-icon-152x152.png" rel="apple-touch-icon" sizes="152x152" />

    <!-- Google Analytics-->
    <script>
      (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
      (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
      m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
      })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

      ga('create', 'UA-2653154-28', 'servicelive.com');
      ga('send', 'pageview');
    </script>
    </head>
<body onload= "displayScheduleModal();">
<!-- <body onload= "displayBanner();displayScheduleModal();">

<div id="bannerDiv" class="bannerDiv" style="display:none;margin-left: 0px;width: 100%;height:22px;">
	     <span class="spanText" id="spanText"></span>
	     <a href="javascript:void(0);" onclick="removeBanner();" style="text-decoration: underline;"> Dismiss </a>
</div> -->

<div class="container" >

<c:if test="${!IS_SIMPLE_BUYER}">

<div class="header" >
<div style="margin-left: 0px;width: 100%;height:10px;">&nbsp;</div>
	<a href="${contextPath}/dashboardAction.action" title="ServiceLive">
		<img src="${staticContextPath}/images/artwork/common/sl_logo_order_management.gif" alt="ServiceLive"/>
	</a>
	<c:choose>
		<c:when test="${!IS_LOGGED_IN}">
			<!-- Not logged in -->
		</c:when>
		
		<c:otherwise>
		<!-- Right Side display -->
					<div class="secondary-nav visible-md visible-lg">
						Welcome, ${SERVICE_ORDER_CRITERIA_KEY.FName}
						${SERVICE_ORDER_CRITERIA_KEY.LName}!
						<c:choose>
						<c:when test="${IS_ADMIN=='false'}">
													<em>#${SERVICE_ORDER_CRITERIA_KEY.vendBuyerResId}</em>
						</c:when>
						<c:when test="${IS_ADMIN=='true'}">
							<em>#-1</em>
						</c:when>
						</c:choose>
						<ul>
							<li>
								<a target="_blank" href="http://training.servicelive.com"
									title="Training">Training</a>
							</li>
							<li>
								<a target="_blank" href="http://community.servicelive.com">Community</a>
							</li>
							<li>
								<a target="_blank" title="Blog"
									href="http://blog.servicelive.com">Blog</a>
							</li>
							<li>
								<a target="_blank"
									href="/MarketFrontend/jsp/public/support/support_faq.jsp">Support</a>
							</li>
							<li>
								<a target="_blank" href="/MarketFrontend/contactUsAction.action">Contact
									Us</a>
							</li>
							<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 2}">
								<li>
									<a target="_blank"
										href="${contextPath}/adminSearch_execute.action">Search
										Portal</a>
								</li>
							</c:if>
							<li>
								<a href="/MarketFrontend/doLogout.action">Logout</a>
							</li>
						</ul>
					</div>
					<nav class="navbar navbar-default" role="navigation">
			            <div class="navbar-header">
			              <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
			                <span class="sr-only">Toggle navigation</span>
			                <span class="icon-bar"></span>
			                <span class="icon-bar"></span>
			                <span class="icon-bar"></span>
			              </button>
			            </div>
			            <!--  TODO This has to be re-looked and all the data for all users should be listed here-->
			            
			             <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			             <ul class="nav navbar-nav">
							<li>
								<a href="${contextPath}/MarketFrontend/dashboardAction.action"
									id="dashboard">Dashboard</a>
								<!-- Dashboard -->
							</li>
							<li>
								<a
									href="${contextPath}/MarketFrontend/serviceOrderMonitor.action"
									id="monitor"> <tags:security
										actionName="serviceOrderMonitor">Service Order Monitor</tags:security>
								</a>
							</li>
							<li>
								<a
									href="${contextPath}/MarketFrontend/financeManagerController_execute.action?ss=${securityToken}"
									id="financialMgr"> <tags:security
										actionName="financeManagerController">ServiceLive Wallet</tags:security>
								</a>
							</li>
							<li>
								<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 1 && omTabViewPermission == 'true'}">
								<c:set var="Inbox" value="Inbox"></c:set>
								<a href="${contextPath}/orderManagementController.action?omDisplayTab=${Inbox}"
									id="orderManagement">Order Management
								</a>
								</c:if>
							</li>	
							<!-- class="active visible-md visible-lg" should be given -->
							<li>
								<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 1}">
									<a class="active" href="${contextPath}/leadsManagementController.action" >Leads Management</a>
								</c:if>
							</li>
							</ul>
							
							<ul class="nav navbar-nav navbar-right">
              <li class="dropdown">
                <a id="admin-office" class="dropdown-toggle" data-toggle="dropdown" role="button"  href="${contextPath}/dashboardAction.action" >
                  Administrator Office <span class="caret"></span>
                </a>
                <ul class="dropdown-menu" role="menu" aria-labelledby="admin-office">
                <c:if test="${SecurityContext != null && SERVICE_ORDER_CRITERIA_KEY.roleId == 1}">
                	<tags:security actionName="addServiceProAction">
                 		<li><a href="${contextPath}/manageUserActiondoLoadUsers.action">Manage Team</a></li>
                	</tags:security>
                	<tags:security actionName="allTabView">
                		<li><a href="${contextPath}/allTabView.action">Edit Company	Profile</a></li>
               		</tags:security>
                  	<li><a href="  ${contextPath}/companyTabAction.action">View Company Profile</a></li>
                  	<!-- SL-20548 -->
                  	<c:choose>
                  	<c:when test ="${SecurityContext.primaryInd && SecurityContext.spnMonitorAvailable > 0}">
                  		<li><a href="${contextPath}/spnMonitorAction_loadSPNMonitor.action">SPN Monitor</a></li>
                  	</c:when>
                  	<c:otherwise>
                  	<tags:security actionName="spnManagerAction_">
                  		<li><a href="${contextPath}/spnMonitorAction_loadSPNMonitor.action">SPN Monitor</a></li>
                  	</tags:security>
                  	</c:otherwise>
        			</c:choose>
              		<tags:security actionName="w9registrationAction_">
              	 		<li><a href="<s:url value='%{contextPath}/w9registrationAction_execute.action'/>">W9 Legal Tax Information</a></li>
              		</tags:security>
              		<c:if test="${SecurityContext.unArchivedCARRulesAvailable>0}">
              			<tags:security actionName="businessinfoAction">
               				<li><a href="<s:url value='%{contextPath}/manageAutoOrderAcceptanceAction_execute.action'/>">Manage Auto Acceptance</a></li>
              			</tags:security>
              		</c:if>
                </c:if>
                </ul>
              </li>
            </ul>
			             </div>
			            </nav>
		</c:otherwise>
		</c:choose>
</div>
</c:if>
<div class="content">



