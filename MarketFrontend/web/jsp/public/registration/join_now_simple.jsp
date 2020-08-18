<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value='<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>' />
<c:set var="showTags" scope="request" value="1" />
<c:set var="showYouTube" scope="request" value="0" />

<html>
	<head>
	
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<meta http-equiv="content-language" content="en" />
		<meta name="name" content="ServiceLive.com for Home, Office and Small Business Use: Service Live Home Services Improvement and Repair." />
		<meta name="description" content="ServiceLive.com is the only online services marketplace where you can name your price, your time and your way for home repairs and improvements." />
		<meta name="keywords" content="live, home, office, repairs, Improvement, Handyman Services, Installation, Computer Services, TV Installation, HDTV, TV Repair, Garage Door Openers, Appliance Installation, Appliance Repair, Kitchen, Bath, Tiling, Leaks, Windows, Doors, Flooring, Carpentry, Roofing, Siding, Kitchen, Countertops, Lawn, Garden, Automotive, Auto, Plumbing, Electrical, Heating, Cooling" />
		<title>ServiceLive.com for Home, Office and Small Business Use: Service Live Home Services Improvement and Repair</title>
		<link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<meta name="description" content="ServiceLive.com is the only online services marketplace where you can name your price, your time and your way for home repairs and improvements." />
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
			href="${staticContextPath}/css/what_is_sl.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/styles/plugins/secondary-980.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/styles/plugins/secondary-steps.css">

		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/modalVideo.css" />

		<script language="JavaScript"
			src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<script language="javascript" type="text/javascript">
			jQuery(document).ready(function($){
				$("#homeowners").addClass("selected");
			});
		</script>

		<script type="text/javascript">
		
			//modal video script
            function setModalVideo()
			{
				jQuery.noConflict();
			    jQuery(document).ready(function($) {

		     	$('#modalVideo').jqm({modal:true, toTop: true});
		     	$('#modalVideo').jqmShow();
				 });

			}
		
<!--

//-->
</script>

		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="JoinNow.simple"/>
		</jsp:include>

	</head>
	<body class="tundra">
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
						<img
							src="${staticContextPath}/images/join_landing/Header_home-office.jpg" alt="Home Improvement and Repairs" width=980 height=209 />
					</div>
					<!-- /top heading> -->
					<div class="contentBg clearfix">
						<div class="leftContent">
							<a href="http://www.nfafn.org" target="new"><img src="${staticContextPath}/images/banners/ffw-sl-banner.jpg" alt="Fix for Free Giveaway" style="border: 0px; float: left; margin: 0px 10px 10px 0px" /></a> <h1>
								ServiceLive.com Home and Office
							</h1>
							<h2>
								It's About Time - Home and Office Improvement and Repairs on
								Your Terms
							</h2>
							<p>
								Hiring people to service your home can be frustrating, expensive
								and time-consuming. It's time to take control of your home and
								your wallet with ServiceLive.com.
							</p>
							<p>
								ServiceLive.com is the only online services marketplace where
								you can name your price, your time and your way for home repairs
								and improvements.
							</p>
							<p>
								Find thousands of service providers across the country - from
								handymen and home electronics pros to plumbers and computer
								experts, and manage the entire process with the click of a
								mouse.
							</p>
							<p>
								Just enter your zip code to review and select service providers;
								describe your project; name the date, time and price; and upload
								funds to your account. After the work is completed to your
								satisfaction, you pay and rate your provider - all online.
							</p>
							<p>
							<br />
							<br />
								More questions?
								<a href="${contextPath}/jsp/public/support/support_faq.jsp">
									Click here </a>to view Frequently Asked Questions.
							</p>
							
							
							<br />
							<br />
						</div>
						<div class="rightContent">
							<ul>
								<li>
									<a class="sbutton"
										href="/MarketFrontend/csoCreateAccount_displayPage.action?fromHome=y">Join
										Now</a>
								</li>
							</ul>
							<c:if test="${showYouTube == 1}"> 
							<ul>
								<li>
									<div class="videoBox">
										<a href="#" onclick="setModalVideo()" title="" class=""><img
												src="${staticContextPath}/images/buttons/carters.png" alt="ServiceLive, live, home, office, repairs, Improvement, Handyman Services, Installation, Computer Services, TV Installation, HDTV, TV Repair, Garage Door Openers, Appliance Installation, Appliance Repair, Kitchen, Bath, Tiling, Leaks, Windows, Doors, Flooring, Carpentry, Roofing, Siding, Kitchen, Countertops, Lawn, Garden, Automotive, Auto, Plumbing, Electrical, Heating, Cooling" width="321" height="157" /> </a>
									</div>
								</li>
							</ul>
							</c:if>								
							<ul>
								<li id="hofAboutServiceProviders">
									<div class="hofGreyBoxBotL hofSidebarBox">
										<div class="hofGreyBoxBotR hofSidebarBox">
											<div class="hofGreyBoxHeaderTopL hofSidebarBox">
												<div class="hofGreyBoxHeaderTopR"
													id="hofAboutServiceProvidersInside">
													<h5>
														<a
															href="${contextPath}/jsp/public/what_is_servicelive/body/aboutServiceProviders.jsp">About
															our Service Providers</a>
													</h5>
													<ul>
														<li id="hofBackgroundCheck">
															<!-- <h6>Background Checks</h6> -->
															<div>
																<img
																	src="${staticContextPath}/images/homepage/acquity/BGcheckSilhouette.gif"
																	alt="Prescreened Service Providers" height="28" />
																All service providers have passed criminal, civil and
																vehicle background checks.
															</div>
															<div class="clearfix"></div>
														</li>
														<li id="hofAverageRating">
															<h6>
																Average rating
															</h6>
															<div>
																Average rating 4.6/5!
															</div>
															<div>
																<img
																	src="${staticContextPath}/images/homepage/acquity/aveRatingStars_19.gif"
																	alt="Average rating" />
															</div>
														</li>
														<li id="hofAboutNumOfProviders">
															<h6>
																Number of Service Providers
															</h6>
															<div>
																20,000+ Providers
															</div>
															<div>
																<img
																	src="${staticContextPath}/images/homepage/acquity/NumOfProvidersIcons.gif"
																	alt="Average rating" />
															</div>
														</li>
													</ul>
													<div class="clearfix" style="clear: both;"></div>
												</div>
												<!-- /.greyBoxHeaderTopL #hpAboutServiceProvidersInside -->
											</div>
											<!-- /.greyBoxHeaderTopR -->
										</div>
										<!-- /.greyBoxBotR -->
									</div>
									<!-- /.greyBoxBotL -->
								</li>
							</ul>
						</div>
						<div id="step" class="step clearfix">
							<h2 class="stepSpace">
								How it works
							</h2>
							<h3 class="stepSpace">
								Start checking home projects off of your "to-do" list now -
								completed projects are literally just a few clicks away!
							</h3>
							<div class="stepBox270">
								<div class="stepL"></div>
								<div class="stepM">
									<div class="stepMTop">
										<h1>
											Step 1
										</h1>
									</div>
									<p>
										<strong>Enter your zip code to view and select
											<a href="${contextPath}/jsp/public/what_is_servicelive/body/aboutServiceProviders.jsp">pre-screened providers</a> in your area</strong>
										<br /><br />
										Click on names to view profiles, insurance information and
										ratings.
									</p>
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
										<h1>
											Step 2
										</h1>
									</div>
									<p>
										<strong>Describe your project, set your schedule and
											name your price</strong>
										<br /><br />
										You can even upload photos and instructions to help providers
										understand your project.
									</p>
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
										<h1>
											Step 3
										</h1>
									</div>
									<p>
										<strong>Create and Fund your account</strong>
										<br /><br />
										Enter your contact information and upload the amount you named
										for your project into your ServiceLive Wallet* to let
										providers know your project is real. You maintain control of
										your funds until the project is completed to your
										satisfaction.
									</p>
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
										<h1>
											Step 4
										</h1>
									</div>
									<p>
										<strong>Post your order</strong>
										<br /><br />
										Your selected providers compete for your business - the first
										one to accept your terms wins! A provider also may respond
										with a counter offer for a different time or price. Remember,
										the more providers you select, the greater chance that you
										will receive your price and time.
									</p>
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
										<h1>
											Step 5
										</h1>
									</div>
									<p>
										<strong>Pay provider from your ServiceLive account
											and rate provider</strong>
										<br /><br />
										Once the provider has completed the project to
										yoursatisfaction, just click "Close and Pay" to move funds
										from your ServiceLive Wallet to the provider's Wallet. Then
										rate the provider, and you are ready for your next ServiceLive
										project!
									</p>
								</div>
        <div class="stepR"></div>
        <div class="stepLB"></div>
        <div class="stepMB"></div>
        <div class="stepRB"></div>
							</div>
							<br>
							
						</div>
						</div>
						<div style="float: left; padding-left:20px;">
								<small>*Payment powered by Integrated
									Payment Systems, which is licensed as a money transmitter by
									the banking department of the State of New York.</small>
							</div>
					
				</div>
				<!-- END CENTER   -->
				<div>
					<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
				</div>
			</div>
		</div>

    <!-- FRONT END DEV NOTE 12/12: new modal - "video" -->
    <div  class="jqmWindow modalDefineTerms" id="modalVideo" style="background-color:#000000;width:520px;">
      <div class="modalHomepage"> <a href="#" class="jqmClose">Close</a> </div>
      <div class="modalContent" style="padding-left: 20px;">

        <object width="480" height="295"><param name="movie" value="http://www.youtube.com/v/q3T_-gaV_j8&hl=en&fs=1&rel=0"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param>
        <embed src="http://www.youtube.com/v/q3T_-gaV_j8&hl=en&fs=1&rel=0" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" 
        width="480" height="295"></embed>
        </object>


      </div>
      <!-- /.modalContent -->
 </div>
 
    <!-- FRONT END DEV NOTE 12/12: new modal - "video" -->
		
	</body>

</html>