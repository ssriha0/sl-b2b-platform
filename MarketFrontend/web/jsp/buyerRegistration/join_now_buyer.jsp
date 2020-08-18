<jsp:include page="/jsp/public/common/commonIncludes.jsp" />
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="showTags" scope="request" value="1" />
<%-- ss: don't import CSS and JS files in the global header --%>
<c:set var="noJs" value="true" scope="request" />
<c:set var="noCss" value="true" scope="request" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>Join ServiceLive</title>
		<link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/secondary-980.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/secondary-steps.css">		

		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
    
</head>
<body class="tundra acquity">

<div id="page_margins">
	<div id="page" class="clearfix">
				<div id="header">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
				</div>


<div id="center" class="clearfix">
	<div class="heading">
		<img src="${staticContextPath}/images/join_landing/commercial.jpg" width="980" height="209" alt="Home Improvement and Repairs">
	</div>

  <div class="contentBg clearfix">
    <div class="leftContent">
    <h1>Build an on-demand, pre-screened service workforce in minutes</h1>
      <h2>Your service headaches are over!</h2>
      <p>ServiceLive.com saves you time and money and helps ease the headaches of managing services. Just select pre-screened service providers; describe your project and name the date, time and price; pay and rate providers - and manage it all online with an easy-to-use tool.<br /><br />
      Managing service needs has its challenges - from keeping track of work order status, escalating costs and scheduling mishaps to frustrated customers and poor quality work. ServiceLive.com is your solution. <br /><br />
      Build an on-demand workforce with the skills you need, when you need them - from an extra set of hands every so often to thousands of service events per day, and never cut another check.  There's no membership fee - registration is free, and you pay only a $10 fee to post an order.<br /><br />
      There are thousands of ServiceLive - approved service providers who have passed a criminal, vehicle, and civil background check - the same check required for contractors of Fortune 500 companies - and new providers signing up every day. <br /><br />
      ServiceLive.com is not a franchise opportunity. There's no fee to join ServiceLive.com or to start receiving leads.  Providers only pay 10 percent of every completed service order amount, which is assessed to cover the cost of 
      transactions in the marketplace. There's nothing for you to lose and lots of business to gain.<br /><br />
      If you require managed services or a customized back-end services solution, please contact <a href="mailto:sales@servicelive.com">sales@servicelive.com</a></p> 
       <br />
      
    </div>
    <div class="rightContent">
      <ul>
        <li>
         <a class="sbutton" href="${contextPath}/buyerRegistrationAction.action">Join Now</a></li>
        <div class="rightContentProfilepromoBlue">
								<h3>ServiceLive Commercial is Perfect For:</h3>
								<ul>
									<li>Professional Services Companies</li>
									<li>Service Contractors</li>
									<li>Mid - sized Companies</li>
									<li>Enterprise Services Departments</li>
									<li>Property Owners/Managers</li>
								</ul>
							     </div>	
        </li>
        <li id="notproviderBlue">
        <a class="notproviderBlue" href="mailto:sales@servicelive.com">If you require managed services or a customized back-end services solution, please contact us.</a>
       
        </li>
      </ul>
      
    </div>
    <div id="step" class="step clearfix">
      <h2 class="stepSpace">How It Works</h2>

      <div class="stepBox340">
        <div class="stepL"></div>
        <div class="stepM">
          <div class="stepMTop">
            <h1> Step 1 </h1>
          </div>
          <p > <strong >Create your free Commercial Buyer Account.</strong> <br /><br />
            There are no registration fees.  Commercial Buyers pay only $10 to post a service order.</p>
        </div>
        <div class="stepR"></div>
        <div class="stepLB"></div>
        <div class="stepMB"></div>
        <div class="stepRB"></div>
      </div>
      <div class="stepBox340">
        <div class="stepL"></div>
        <div class="stepM">
          <div class="stepMTop">
            <h1> Step 2 </h1>
          </div>
          <p> <strong>Search for and Select pre-screened providers.</strong> <br /><br />
           You can search for providers based on company profile, skills, customer ratings, credentials, insurance, location or other criteria.  Route your service orders to the most qualified providers!</p>
        </div>
        <div class="stepR"></div>
        <div class="stepLB"></div>
        <div class="stepMB"></div>
        <div class="stepRB"></div>
      </div>
      <div class="stepBox340">
        <div class="stepL"></div>
        <div class="stepM">
          <div class="stepMTop">
            <h1> Step 3 </h1>
          </div>
          <p> <strong>Fund your account.</strong> <br /><br />
            Enter your financial information to fund your commercial buyer account prior to posting a service order.  Fund your ServiceLive Wallet account by entering a credit card number (funds are available immediately) or by entering bank account information (funds may take up to 3 business days). You maintain control of your account at all times.</p>
        </div>
        <div class="stepR"></div>
        <div class="stepLB"></div>
        <div class="stepMB"></div>
        <div class="stepRB"></div>
      </div>
      <div class="stepBox340">
        <div class="stepL"></div>
        <div class="stepM">
          <div class="stepMTop">
            <h1> Step 4 </h1>
          </div>
          <p> <strong>Create and Post your service order.</strong> <br /><br />
            Describe your service order and name your price and preferred timing.  The service order is then routed to your selected providers.  The first provider who accepts it electronically wins the opportunity.  Providers may respond with a counter offer of a different date or price, which you can decide whether or not to accept.  The more providers you select the greater the chances of getting your requested terms.</p>
        </div>
        <div class="stepR"></div>
        <div class="stepLB"></div>
        <div class="stepMB"></div>
        <div class="stepRB"></div>
      </div>
      <div class="stepBox340">
        <div class="stepL"></div>
        <div class="stepM">
          <div class="stepMTop">
            <h1> Step 5 </h1>
          </div>
          <p> <strong>Pay and rate the provider.</strong> <br /><br />
           After you are satisfied with the completed project, just click "Close and Pay" to move funds from your ServiceLive Wallet to the provider's Wallet.  Then be sure to rate your provider - your rating will be part of the provider's lifetime rating on the platform.</p>
        </div>
        <div class="stepR"></div>
        <div class="stepLB"></div>
        <div class="stepMB"></div>
        <div class="stepRB"></div>
      </div>
      
    </div>
  </div>
</div>

<!-- Start of DoubleClick Spotlight Tag: Please do not remove-->
<!-- Activity Name for this tag is:ServiceProviders Landing Tag -->
<!-- Web site URL where tag should be placed: http://www.servicelive.com/MarketFrontend/joinNowAction.action -->
<!-- This tag must be placed within the opening <body> tag, as close to the beginning of it as possible-->
<!-- Creation Date:12/4/2008 -->
<SCRIPT language="JavaScript">
var axel = Math.random()+"";
var a = axel * 10000000000000;
document.write('<IFRAME SRC="<%=request.getScheme()%>://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi767;ord=1;num='+ a + '?" WIDTH=1 HEIGHT=1 FRAMEBORDER=0></IFRAME>');
</SCRIPT>
<NOSCRIPT>
<IFRAME SRC="<%=request.getScheme()%>://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi767;ord=1;num=1?" WIDTH=1 HEIGHT=1 FRAMEBORDER=0></IFRAME>
</NOSCRIPT>
<!-- End of DoubleClick Spotlight Tag: Please do not remove-->
				
				<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
	</div>
</div>
		<!-- Start of DoubleClick Spotlight Tag: Please do not remove-->
		<!-- Activity Name for this tag is:Commercial Landing Tag -->
		<!-- Web site URL where tag should be placed: http://www.servicelive.com/MarketFrontend/joinNowBuyerAction.action -->
		<!-- This tag must be placed within the opening <body> tag, as close to the beginning of it as possible-->
		<!-- Creation Date:12/4/2008 -->
		<SCRIPT language="JavaScript">
		var axel = Math.random()+"";
		var a = axel * 10000000000000;
		document.write('<IFRAME SRC="<%=request.getScheme()%>://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=comme537;ord=1;num='+ a + '?" WIDTH=1 HEIGHT=1 FRAMEBORDER=0></IFRAME>');
		</SCRIPT>
		<NOSCRIPT>
		<IFRAME SRC="<%=request.getScheme()%>://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=comme537;ord=1;num=1?" WIDTH=1 HEIGHT=1 FRAMEBORDER=0></IFRAME>
		</NOSCRIPT>
		<!-- End of DoubleClick Spotlight Tag: Please do not remove-->
			<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
				 <jsp:param name="PageName" value="JoinNow.CommercialBuyer"/>
			</jsp:include>
	</body>
</html>