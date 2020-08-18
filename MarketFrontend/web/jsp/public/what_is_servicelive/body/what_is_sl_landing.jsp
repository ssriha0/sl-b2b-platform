
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
		<meta name="description"
			content="ServiceLive is the only online marketplace for one-stop home improvements and repairs" />
		<meta name="name"
			content="ServiceLive.com About Us: Service Live Home Services Improvement and Repair." />
		<meta name="keywords"
			content="live, Home Improvement, Home Repairs, Handyman Services, Installation, Computer Services, TV Installation, HDTV, TV Repair, Garage Door Openers, Appliance Installation, Appliance Repair, Kitchen, Bath, Tiling, Leaks, Windows, Doors, Flooring, Carpentry, Roofing, Siding, Kitchen, Countertops, Lawn, Garden, Automotive, Auto, Plumbing, Electrical, Heating, Cooling" />
		<title>ServiceLive.com About Us: Service Live Home Services
			Improvement and Repair</title>
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
		<jsp:param name="PageName" value="AboutUs"/>
	</jsp:include>

	</head>
	<body class="tundra">
	<!--  <body class="tundra" onload="displayBanner()"> -->
		<div id="page_margins">
			<div id="page">
				<div id="headerRightRail">

					<tiles:insertDefinition name="newco.base.topnav" />
					<tiles:insertDefinition name="newco.base.blue_nav" />
					<div id="auxNav"></div>
				</div>
				<!-- BEGIN CENTER -->

<div id="center" class="clearfix">
  <!-- insert your content here new example template-->
  
  <!-- top heading> -->
  <div class="heading">
  <img src="${staticContextPath}/images/join_landing/header_What-is.jpg" width="980" height="209" alt="Home Improvement and Repairs">
  
  </div>
  <!-- /top heading> -->
  <div class="contentBg clearfix">
    <div class="leftContent">
    <h1>About ServiceLive.com</h1>
      <h2>It's About Time - Home Improvement and Repairs on Your Terms </h2>
      <p> ServiceLive.com is the only online services marketplace where
		  you can name your price for home repairs and improvements. We
		  help you to take care of your home or office on your terms.</p>
      <p>Founded in 2007 as a wholly owned subsidiary of Sears Holdings
		Corporation, ServiceLive has spent the last year recruiting and
		conducting background checks on the best service providers in
		the country across a wide variety of home services - from
		handymen and home electronics pros to plumbers and computer
		experts.</p>
      <p>Now thousands of ServiceLive-Approved service providers are
		ready to work for you. Just enter your zip code to review and
		select service providers; describe your project; name the date,
		time and price; and upload funds to your account. After the
		work is completed to your satisfaction, you pay and rate your
		provider - all online. </p><br /><br />
    </div>
    <div class="rightContent">
      <!--  <ul>
        <li> <a class="sbutton" href="/MarketFrontend/csoCreateAccount_displayPage.action?fromHome=y">Join Now</a></li>
      </ul>
       -->
    </div>
    <div id="step" class="step clearfix">
      <h2 class="stepSpace"> How it works </h2>
      <h3 class="stepSpace"> Start checking home projects off of your "to-do" list now - completed projects are literally just a few clicks away!</h3>
      <div class="stepBox270">
        <div class="stepL"></div>
        <div class="stepM">
          <div class="stepMTop">
            <h1> Step 1 </h1>
          </div>
          <p> <strong>Enter your zip code to view and select pre-screened providers in your area</strong> <br /><br />
            Click on names to view profiles, insurance information and ratings.</p>
        </div>
        <div class="stepR"></div>
        <div class="stepLB"></div>
        <div class="stepMB"></div>
        <div class="stepRB"></div>
      </div>
      <div class="stepBox270">
        <div class="stepL"></div>
        <div class="stepM">
          <div class="stepMTop">
            <h1> Step 2 </h1>
          </div>
          <p> <strong>Describe your project, set your schedule and name your price</strong> <br /><br />
            You can even upload photos and instructions to help providers understand your project. </p>
        </div>
        <div class="stepR"></div>
        <div class="stepLB"></div>
        <div class="stepMB"></div>
        <div class="stepRB"></div>
      </div>
      <div class="stepBox270">
        <div class="stepL"></div>
        <div class="stepM">
          <div class="stepMTop">
            <h1> Step 3 </h1>
          </div>
          <p> <strong>Create and Fund your account</strong> <br /><br />
            Enter your contact information and upload the amount you named
			for your project into your ServiceLive Wallet* to let
			providers know your project is real. You maintain control of
			your funds until the project is completed to your
			satisfaction.</p>
        </div>
        <div class="stepR"></div>
        <div class="stepLB"></div>
        <div class="stepMB"></div>
        <div class="stepRB"></div>
      </div>
      <div class="stepBox270">
        <div class="stepL"></div>
        <div class="stepM">
          <div class="stepMTop">
            <h1> Step 4 </h1>
          </div>
          <p> <strong>Post your order</strong> <br /><br />
            Your selected providers compete for your business - the first
			one to accept your terms wins! A provider also may respond
			with a counter offer for a different time or price. Remember,
			the more providers you select, the greater chance that you
			will receive your price and time.</p>
        </div>
        <div class="stepR"></div>
        <div class="stepLB"></div>
        <div class="stepMB"></div>
        <div class="stepRB"></div>
      </div>
      <div class="stepBox270">
        <div class="stepL"></div>
        <div class="stepM">
          <div class="stepMTop">
            <h1> Step 5 </h1>
          </div>
          <p> <strong>Pay provider from your ServiceLive account and rate provider</strong> <br /><br />
            Once the provider has completed the project to
			your satisfaction, just click "Close and Pay" to move funds
			from your ServiceLive Wallet to the provider's Wallet. Then
			rate the provider, and you are ready for your next ServiceLive
			project!</p>
        </div>
        <div class="stepR"></div>
        <div class="stepLB"></div>
        <div class="stepMB"></div>
        <div class="stepRB"></div>
      </div><br>
      
    </div>
  </div>
</div>

<div style=" float:left"> <small>*Payment powered by Integrated Payment Systems,
								which is licensed as a money transmitter by the banking
								department of the State of New York.</small></div>
				<!-- END CENTER   -->
				<div>
					<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
				</div>
			</div>
		</div>
	</body>

</html>
