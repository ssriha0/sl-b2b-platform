<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive : Log in to ServiceLive</title>
		<tiles:insertDefinition name="blueprint.base.meta"/>
		<link rel="stylesheet" type="text/css" href="/ServiceLiveWebUtil/styles/plugins/public.css" media="screen, projection">
		<!-- include any plugin javascripts and css here -->
		<script type="text/javascript">
			$(function() {
				// insert your javascript here
			});
		</script>
	</head>

<body id="login">
<div id="wrap" class="container">
	<tiles:insertDefinition name="blueprint.base.header"/>
	<tiles:insertDefinition name="blueprint.base.navigation"/>
	<div id="content" class="span-24 clearfix">		
		<!-- Insert your page content here -->
		<div class="span-16 first last">
			<div class="clearfix">	
				<div class="page-title">
					<h2>Log In to ServiceLive</h2>
					<h3>Security Verification</h3>
					<p>Please enter your information below to continue.</p>	
				</div>			
			</div>
			<div class="content">
				<div id="login-form">
				<fieldset>
					<p>Items marked with an asterix (<span class="req">*</span>) are required.</p>
					<p class="error">Error msg</p>			
					


					<p>
						<label><span class="req">*</span> Security Question</label> 
						<span class="security-question">What is your favorite Movie?</span>
						<input type="text" class="text squestion" /> <small class="forgot"><a href="#">Forgot Your Security Question?</a></small>
					</p>



					<input type="submit" class="button action submit" value="Submit">
								
				</fieldset>
				</div>
			</div>
		</div>
		<div class="span-8">
			<div id="supportmsg" class="sideblock">
				<strong><a href="mailto: support@servicelive.com">support@servicelive.com</a></strong>
				<h3>1-888-549-0640</h3>
				<strong>We're here to help.</strong>
			</div>

			
		</div>

	</div>
	<tiles:insertDefinition name="blueprint.base.footer"/>
</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="anonymous:log in to servicelive"/>
	</jsp:include>
	</body>
</html>
