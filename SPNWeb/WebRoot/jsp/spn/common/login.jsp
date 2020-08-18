<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" 	value="<%=request.getScheme() + "://"	+ request.getServerName() + ":" + request.getServerPort() 	+ "/ServiceLiveWebUtil"%>" />
<c:set var="cookieUserName" value="<%=request.getAttribute("cookieUserName")%>" />
<c:set var="rememberId" value="<%=request.getAttribute("cookieRememberId")%>" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<script type="text/javascript">
if (top.location.href.indexOf('spnLoginAction') <= 0){
	top.location = "/spn/spnLoginAction_display.action"; //pop out of frames or AJAX-loaded divs
}
</script>
	<title>ServiceLive : Log in to Select Provider Network</title>
	<link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/screen.css" media="screen, projection">
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/superfish.css" media="screen, projection">
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.flash.min.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.sifr.min.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/superfish.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/supersubs.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.hoverIntent.minified.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.bgiframe.js"></script>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/public.css" media="screen, projection">
	
	<script type="text/javascript">		
		jQuery(document).ready(function($) {				
				var actionvar = '${cookieUserName}';				
				if(actionvar != null && actionvar != "" ){					
					$('#userName').val(actionvar);				
				}
				var remembervar = '${rememberId}';
				if(remembervar == "checked")
				 	document.getElementById("remember-me").checked = true;
				else
					document.getElementById("remember-me").checked = false;
				
		});
	  	function validateUserInput() {
	  	var isvalidInput = true;
	  	jQuery.noConflict();
		jQuery(document).ready(function($) {
		     	if($('#userName').val() == "") {
		     		$('#errorMessageJS').html('Please enter your User Name. For help, please click "Forgot User Name or Password" below.');
		     		$('#errorMessageJS').show();
		     		isvalidInput = false;
		     	}
		     	else if ($('#credential').val() == "" ){
		     			$('#errorMessageJS').html(' Please enter your Password. For help, please click "Forgot User Name or Password" below.');
		     			$('#errorMessageJS').show();
		     			isvalidInput = false;
		     	}
		});
		return isvalidInput;
	  	}
	 </script>
</head>
<body id="login">
	<div id="wrap" class="container">
		<div id="content" class="span-24 clearfix">
			<!-- Insert your page content here -->
			<div class="span-16 first last" style="width:620px; /* needed for IE6 */">
				<div class="clearfix">
					<div class="page-title">
						<h2>Log In to ServiceLive</h2>
						<p>Please enter your information below to continue.</p>
					</div>
				</div>
				<div class="content">
					<div id="login-form">
					<s:form action="spnLoginAction_loginUser" id="spnLoginAction_loginUser"
						name="spnLoginAction_loginUser" method="post" theme="simple" onsubmit="return validateUserInput();" validate="true">
							<fieldset>
								<p>Items marked with an asterix (<span class="req">*</span>) are required.</p>
								<div>
									<jsp:include page="/jsp/spn/common/validation_messages.jsp" />
								</div>
								<p class="error hide" id="errorMessageJS"></p>
								<p>
									<label>
										<span class="req">*</span> User Name
									</label>
									<input type="text" name="userName" id="userName" class="text" autocomplete="off" />
								<%-- use autocomplete="off" per security analysts' recommendation --%>
								</p>

								<p>
									<label>
										<span class="req">*</span> Password
									</label>
									<input type="password" name="credential" id="credential"
										class="text" autocomplete="off" />
									<%-- use autocomplete="off" per security analysts' recommendation --%>
								</p>
								<p id="remember_me">
									<s:checkbox name="rememberUserId" id = "remember-me" onclick ="" />
									 Remember my user name on this computer.							
								</p>
							
								<s:hidden id="targetAction" name="targetAction" value="%{targetAction}" />
								<s:hidden id="buyerId" name="buyerId" value="%{buyerId}" />
								<input type="submit" class="button action submit" value="Log In">
							</fieldset>
						</s:form>
					</div>
				</div>
			</div>

			<div class="span-8">
				<div id="supportmsg" class="sideblock">
					<strong><a href="mailto: support@servicelive.com">support@servicelive.com</a>
					</strong>
					<h3>
						1-888-549-0640
					</h3>
					<strong>We're here to help.</strong>
				</div>
			</div>
			
		</div>
	</div>
</body>
