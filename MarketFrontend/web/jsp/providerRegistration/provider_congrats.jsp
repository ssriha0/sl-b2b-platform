<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="loginActionPath" scope="request" value="<%="https://" + request.getServerName() + ":" + System.getProperty("https.port") + request.getContextPath() + "/doLogin.action"%>"/>

<c:set var="noJs" value="true" scope="request" />
<c:set var="noCss" value="true" scope="request" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>ServiceLive [Provider Registration]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		
		<script type="text/javascript">
			function loadHomepage(){
				document.forms[0].action='login.action';
				document.forms[0].submit();
			}
		</script>
		
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/contact.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />
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
  						<div> <img src="${staticContextPath}/images/images_registration/sp_firm_registration/hdr_congrats.gif" alt="Congratulations" title="Congratulations" />
 						 </div>
					</div>					
				</div>	
				<!-- BEGIN RIGHT PANE -->
				<div class="colRight255 clearfix">
					<img
						src="${staticContextPath}/images/images_registration/sp_firm_registration/promoSLbringsYou.gif"
						alt="Did You Know" title="Did You Know" />
				</div>
				<div class="colLeft711">
					<div class="content">

						<h3>
							You're about to experience a more efficient and effective way to
							connect with service buyers
						</h3>
						<p>
							Within a few minutes, you'll receive an e-mail from
							<a href="mailto:support@servicelive.com">support@servicelive.com</a>,
							which will contain a unique user account number, as well as a
							temporary password. Use this information to log in to
							ServiceLive so you can finish the registration process.
						</p>
						<p class="paddingBtm">
							If you don't receive this confirmation within a few minutes,
							please check your junk e-mail and make sure that the e-mail
							hasn't been blocked by your spam filter. If the confirmation
							e-mail doesn't appear in your general e-mail box or trash,
							contact us at
							<a href="mailto:support@servicelive.com">E-MAIL</a> to verify your account.
						</p>
						<p>
							<input type="image"
								src="${staticContextPath}/images/images_registration/common/spacer.gif"
								width="149" height="20"
								style="background-image: url(${staticContextPath}/images/images_registration/btn/returnToHomePage.gif);"
								class="btn20Bevel" onclick="location.href='http://provider.servicelive.com/login.html'" />
						</p>
					</div>
				</div>
			</div>
			<div class="clear"></div>
			<!-- Start of DoubleClick Spotlight Tag: Please do not remove-->
			<!-- Activity Name for this tag is:ServiceProviders Conversion Tag -->
			<!-- Web site URL where tag should be placed: ServiceProviders Confirmation/Thankyou Page -->
			<!-- This tag must be placed within the opening <body> tag, as close to the beginning of it as possible-->
			<!-- Creation Date:12/4/2008 -->
			<SCRIPT language="JavaScript">
				var axel = Math.random()+"";
				var a = axel * 10000000000000;
				document.write('<IFRAME SRC="<%=request.getScheme()%>://fls.doubleclick.net/activityi;src=2077836;type=conve041;cat=servi093;ord=1;num='+ a + '?" WIDTH=1 HEIGHT=1 FRAMEBORDER=0></IFRAME>');
			</SCRIPT>
			<NOSCRIPT>
				<IFRAME SRC="<%=request.getScheme()%>://fls.doubleclick.net/activityi;src=2077836;type=conve041;cat=servi093;ord=1;num=1?" WIDTH=1 HEIGHT=1 FRAMEBORDER=0></IFRAME>
			</NOSCRIPT>
			<!-- End of DoubleClick Spotlight Tag: Please do not remove -->
			<!-- START FOOTER -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER -->	
		</div>		
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			  <jsp:param name="PageName" value="ProRegCongrats"/>
		</jsp:include>
		<%-- conversion tag --%>
		<script src="https://ad.yieldmanager.com/pixel?id=730990&t=1" type="text/javascript"></script>
	</body>
</html>