<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="loginActionPath" scope="request" value="<%="https://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/doLogin.action"%>"/>	
<%-- ss: don't import CSS and JS files in the global header --%>
<c:set var="noJs" value="true" scope="request" />
<c:set var="noCss" value="true" scope="request" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>ServiceLive [Buyer Registration]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />

<script type="text/javascript">
function loadHomepage(){
	document.forms[0].action='login.action';
	document.forms[0].submit();
}
</script>
</head>
<body class="tundra">

<div id="page_margins">
  <div id="page" class="clearfix">
    	<!-- BEGIN HEADER -->
		<div id="headerSPReg">
			<tiles:insertDefinition name="newco.base.topnav"/>
			<tiles:insertDefinition name="newco.base.blue_nav"/>
			<tiles:insertDefinition name="newco.base.dark_gray_nav"/>				
		<div id="pageHeader">
			<h2>Congratulations</h2>
		</div>
     </div>
    <!-- BEGIN RIGHT PANE -->
    <div class="colRight255 clearfix"></div>
    <div class="colLeft711">
      <div class="content">
      	<h3>
			You're about to become part of the revolutionary ServiceLive network.
		</h3>
		<p>
			Within a few minutes, you'll receive an e-mail from 
			<a href="mailto:support@servicelive.com">registration@servicelive.com</a>, 
			which will contain a temporary password. Use this information to log in to 
			ServiceLive so you can finish the registration process. 
		</p>
        <p class="paddingBtm"> 
        	If you don't receive this confirmation within a few minutes, 
        	please check your junk e-mail and make sure that the e-mail 
        	hasn't been blocked by your spam filter. If the confirmation 
        	e-mail doesn't appear in your general e-mail box or trash, 
        	contact us at <a href="mailto:support@servicelive.com">E-MAIL</a> to verify your account.
		</p>
        <p>
			<input type="image"
				src="${staticContextPath}/images/images_registration/common/spacer.gif"
				width="149" height="20"
				style="background-image: url(${staticContextPath}/images/images_registration/btn/returnToHomePage.gif);"
				class="btn20Bevel" onclick="location.href='http://business.servicelive.com/login.html'" />
		</p>
      </div>
    </div>
  </div>
  <div class="clear"></div>

<!-- Start of DoubleClick Spotlight Tag: Please do not remove-->
<!-- Activity Name for this tag is:Commercial Conversion Tag -->
<!-- Web site URL where tag should be placed: Commercial Confirmation/thankyou Page -->
<!-- This tag must be placed within the opening <body> tag, as close to the beginning of it as possible-->
<!-- Creation Date:12/4/2008 -->
<SCRIPT language="JavaScript">
var axel = Math.random()+"";
var a = axel * 10000000000000;
document.write('<IFRAME SRC="<%=request.getScheme()%>://fls.doubleclick.net/activityi;src=2077836;type=conve041;cat=comme486;ord=1;num='+ a + '?" WIDTH=1 HEIGHT=1 FRAMEBORDER=0></IFRAME>');
</SCRIPT>
<NOSCRIPT>
<IFRAME SRC="<%=request.getScheme()%>://fls.doubleclick.net/activityi;src=2077836;type=conve041;cat=comme486;ord=1;num=1?" WIDTH=1 HEIGHT=1 FRAMEBORDER=0></IFRAME>
</NOSCRIPT>
<!-- End of DoubleClick Spotlight Tag: Please do not remove-->

  <jsp:include page="/jsp/public/common/defaultFooter.jsp" />
</div>
<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
	 <jsp:param name="PageName" value="BuyerRegistration.Congrats"/>
</jsp:include>
</body>
</html>
