<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="loginActionPath" scope="request" value="<%="https://" + request.getServerName() + ":" + System.getProperty("https.port") + request.getContextPath() + "/doLogin.action"%>"/>
<c:set var="username" scope="request"
	value="<%=request.getParameter("uname")%>" />
	<c:set var="cookieUserName" value="<%=request.getAttribute("cookieUserName")%>" />
	<c:set var="rememberId" value="<%=request.getAttribute("cookieRememberId")%>" />
<c:set var="showTags" scope="request" value="1" />



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>ServiceLive : Log in to ServiceLive</title>
		<!-- Smart App Banner 18979-->
    	<meta name="apple-itunes-app" content="app-id=899811332" />
		<meta name="google-play-app" content="app-id=com.servicelive.android.servicelivepro" />
		<meta name="msApplication-ID" content="microsoft.build.App"/>
		<meta name="msApplication-PackageFamilyName"content="microsoft.build_8wekyb3d8bbwe"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<!-- Smart App Banner 18979-->
		<tiles:insertDefinition name="blueprint.base.meta"/>
		<link rel="stylesheet" type="text/css" href="/ServiceLiveWebUtil/styles/plugins/public.css" media="screen, projection">
		<%-- include any plugin javascripts and css here --%>
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	         .ie7 .bannerDiv{margin-left:-1150px;}  
		</style>
		<!--[if lt IE 7]>
		<style type="text/css">
		form {padding:0; margin:0; border:0; height:0; float:none; clear:both;}
		</style>
		<![endif]-->
		 
		 <!-- Smart App Banner 18979-->
		<link rel="stylesheet" type="text/css" href="/ServiceLiveWebUtil/css/smart-app-banner.css" />
		<link rel="apple-touch-icon" href="/ServiceLiveWebUtil/images/icons/mobile_icon.png" />
		<link rel="android-touch-icon" href="/ServiceLiveWebUtil/images/icons/mobile_icon.png" />
		<!-- Smart App Banner 18979-->
		<%-- SS: this is needed to close spaces opened by form tags in IE6 --%>
			<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			<jsp:param name="PageName" value="login"/>
		</jsp:include>
                <meta http-equiv="Cache-Control" content="no-cache " />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
	
</head>
<body id="login" onload="readCookie();displaySmartBanner();">
<!--  <body id="login" onload="readCookie();displayBanner();"> -->
    
    <div id="wrap" class="container">
		<tiles:insertDefinition name="blueprint.base.header"/>
		<tiles:insertDefinition name="blueprint.base.navigation"/>
		<s:form action="%{#request['loginActionPath']}" method="post" enctype="multipart/form-data" theme="simple">		
		<div id="content" class="span-24 clearfix">		
				<!-- Insert your page content here -->	
			<div class="span-16 first last">
				<div class="clearfix">	
					<div class="page-title">
						<h2>Log In to ServiceLive</h2>
						<p>Please enter your information below to continue.</p>	
					</div>			
				</div>
				<div class="content">
					<div id="login-form">
						<fieldset>
							<p>Items marked with an asterisk (<span class="req">*</span>) are required.</p>
							
							<div id="content_right_header_text">
								<jsp:include page="/jsp/sl_login/common/errorMessage.jsp" flush="true" />
							</div>
							
							<p>
								<label><span class="req">*</span> User Name </label>
								<input type="text" id="username" name="username" class="text" maxlength="100" value="" autocomplete="off" tabindex="1"/>
								<%-- use autocomplete="off" per security analysts' recommendation --%>
								</p>
			
							<p>
								<label><span class="req">*</span> Password </label>
								<input type="password" id="password" name="password" class="text" autocomplete="off" tabindex="2"/>
								<%-- use autocomplete="off" per security analysts' recommendation --%> 
								<small class="forgot">
								 Forgot Your
								<a href="${contextPath}/forgetUsernameAction!loadForgetUserPage.action">
									username </a> or
								<a href="${contextPath}/resetPasswordAction!loadResetPasswordPage.action">
									password</a>?
								</small>									
									<span class="clearfix passwordmessage">Passwords are cAsE sensitive.</span>
							</p>								
								
							<p id="remember_me">
									<s:checkbox name="rememberUserId" id ="remember-me" onclick = ""  />
									 Remember my user name on this computer.							
							</p>
		
							<input type="submit" id="submit" class="button action submit" value="Submit" tabindex="3">
						</fieldset>
					</div>
				</div>
			</div>
			<!-- Added for mobile App banner -->
			<div style="padding-top: 10px;">
				<a href="http://mobile.servicelive.com/provider" target="_blank">
			    <img width="300" height="250" style="position:absolute;margin-left:0px" src="${staticContextPath}/images/mobileBanner/banner_Mobile_App2.gif" 
          	    alt="Mobile Banner" /></a>
            </div>
			<table border="0" style="width: 310px; margin-top: 280px;">
				<tbody><tr>
					<td style="text-align: right; padding-top: 5px; padding-right: 10px;">
						<strong>ServiceLive is optimized<br>for the following<br>browsers:</strong>			
					</td>
				<!-- removing IE 
					<td>
						<center>
							<img border="0" alt="" src="/ServiceLiveWebUtil/images/common/ie.png" title="IE">
							<br>
							<strong>IE 8</strong>
						</center>
					</td>-->
				<td>
						<center>
							<img border="0" alt="" src="/ServiceLiveWebUtil/images/common/firefox.png" title="Firefox">
							<br>
							<strong>Firefox</strong>
						</center>			
					</td>
					<td>
						<center>
							<img border="0" alt=""
								src="/ServiceLiveWebUtil/images/common/chrome.png"
								title="Chrome"> <br> <strong>Chrome</strong>
						</center>
					</td>
				</tr>
			</tbody></table>
		</div>			
	</s:form>				
	<tiles:insertDefinition name="blueprint.base.footer"/>
</div>
<script type="text/javascript">
	/*For SL-10675	
	function setCookie(days)
		{		
			
			var username = document.getElementById('username').value;
			if (days) {
				var date = new Date();
				date.setTime(date.getTime()+(days*24*60*60*1000));
				var expires = "; expires="+date.toGMTString();
			}
			else var expires = "";
			document.cookie = "username="+escape(username)+expires+"; path=/";
			
		}*/
		
		function readCookie() {
		//For SL-10675
		/*	var username = "username=";
			var ca = document.cookie.split(';');
			for(var i=0;i < ca.length;i++) {
				var c = ca[i];
				while (c.charAt(0)==' ') c = c.substring(1,c.length);
				if (c.indexOf(username) == 0){
					if(document.getElementById("username").value == ""){
						document.getElementById("username").value= c.substring(username.length,c.length);
					}
				}
			}*/		
			if(document.getElementById("username")){
				var actionvar = '${cookieUserName}';
				document.getElementById("username").value = actionvar;
			}
				var remembervar = '${rememberId}';
				if(remembervar == "checked")
				 	document.getElementById("remember-me").checked = true;
				else
					document.getElementById("remember-me").checked = false;
		}
		 
</script>
<!-- Smart App Banner 18979-->
<script type="text/javascript" src="/ServiceLiveWebUtil/javascript/smart-app-banner.js"></script>
<script type="text/javascript" src="/ServiceLiveWebUtil/javascript/smart_banner.js"></script>
<!-- Smart App Banner 18979-->
</body>
</html>
