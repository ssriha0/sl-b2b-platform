<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value='<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>' />
<c:set var="showTags" scope="request" value="1" />

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<meta http-equiv="content-language" content="en" />
		<meta name="name"
			content="ServiceLive.com About our Service Providers: Service Live Home Improvement and Repair." />
		<meta name="description"
			content="ServiceLive is your online marketplace for pre-screened, background checked home improvements and service repair technicians" />
		<meta name="keywords"
			content="live, Home Improvement, Home Repairs, Handyman Services, Installation, Computer Services, TV Installation, HDTV, TV Repair, Garage Door Openers, Appliance Installation, Appliance Repair, Kitchen, Bath, Tiling, Leaks, Windows, Doors, Flooring, Carpentry, Roofing, Siding, Kitchen, Countertops, Lawn, Garden, Automotive, Auto, Plumbing, Electrical, Heating, Cooling" />
		<title>ServiceLive.com About our Service Providers: Service
			Live Home Improvement and Repair</title>
		<link rel="shortcut icon"
			href="${staticContextPath}/images/favicon.ico" />
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
			href="${staticContextPath}/css/what_is_sl.css" />
		
			<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/styles/plugins/secondary-980.css">
			<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/styles/plugins/secondary-steps.css">
		<script language="JavaScript"
			src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<script language="javascript" type="text/javascript">
			jQuery(document).ready(function($){
				$("#whatIsSL").addClass("selected");
			});
		
		</script>
		
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			<jsp:param name="PageName" value="AboutSP"/>
		</jsp:include>
	</head>
	<body class="tundra">
	<!-- <body class="tundra" onload="displayBanner()">-->
		<div id="page_margins">
			<div id="page">
				<div id="headerRightRail">

					<tiles:insertDefinition name="newco.base.topnav" />
					<tiles:insertDefinition name="newco.base.blue_nav" />
					<div id="auxNav"></div>
				</div>
				<!-- BEGIN CENTER -->

					<div id="center" class="clearfix">
<!-- top heading> -->
  <div class="heading">
  <img src="${staticContextPath}/images/join_landing/header_WhyJoin.jpg" alt="Home Improvement and Repairs" width=980 height=209>
  </div>
  <!-- /top heading> -->
  <div class="contentBg clearfix">
    <div class="leftContent">
                         <h1>About ServiceLive.com Providers</h1>
								<p>There are thousands of local, independent service providers
									waiting to work for you on ServiceLive.com. ServiceLive has
									spent the last year recruiting and screening some of the best
									service providers in the country across a wide variety of home
									services - from handymen and home electronics pros to plumbers
									and computer experts. And more are joining every day! If you
									don't see the types of service providers you need in your area,
									please check back soon or contact us at
									<a href="mailto:support@servicelive.com">support@servicelive.com</a>
									to make a request.</p><br><br>
        
           <h2>The Background Check</h2>
								<p>ServiceLive conducts background checks on each and every
									ServiceLive-Approved provider you can hire through the site -
									the same check of criminal, civil and vehicle records required
									by contractors of Fortune 500 companies. In addition, provider
									firms submit company insurance levels and background
									information, plus skills, credentials and profiles for each
									individual provider.</p><br><br>  
       
           <h2>Ratings and Comments</h2>
								<p>Many ServiceLive-Approved providers have been rated by previous
									ServiceLive customers on everything from quality to
									cleanliness. To maintain the integrity of our ratings, only
									customers who have hired providers through ServiceLive are
									allowed to post ratings and comments. Therefore, new providers
									to ServiceLive will not have any ratings. In addition,
									Servicelive does not edit ratings and comments, so you have the
									ability to view all existing ratings for each provider.</p><br><br>
									 <small><span>*</span>If you are a service provider interested in joining ServiceLive, please <a href="/MarketFrontend/joinNowAction.action">click here</a></small><br />
                                <small><span>*</span>Service providers are not employees of ServiceLive or Sears Holdings, unless otherwise stated</small>  
      </div>
       
    <div class="rightContent">
    <!--   <ul>
        <li> <a class="sbutton" href="/MarketFrontend/csoCreateAccount_displayPage.action?fromHome=y">Join Now</a> </li>
      </ul>
      --> 
    </div>
                               
    </div> 
	</div>
				<!-- END CENTER   -->
				<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			</div>


		</div>

	</body>

</html>
