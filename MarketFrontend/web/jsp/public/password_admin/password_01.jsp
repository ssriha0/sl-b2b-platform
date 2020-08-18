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
		<title>ServiceLive : Welcome to ServiceLive</title>
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
					<h2>Welcome to ServiceLive</h2>
					<h3>Create Your Password</h3>
					<p>Please create your account access information below.  Be sure to keep record of the information in a safe place.</p>	
				</div>			
			</div>
			<div id="content_right_header_text">
				<%@ include file="message.jsp"%>
				</br>
			</div>
			<div class="content">
				<div id="login-form">
				<s:form action="savePasswordAction" theme="simple">
					<fieldset>
						<p>Items marked with an asterix (<span class="req">*</span>) are required.</p>
						<p class="error">Error msg</p>			
						<p>
							<label><span class="req">*</span> Password </label> 
							<input type="password" class="text" />
						</p>
						<p>
							<label><span class="req">*</span> Confirm </label> 
							<input type="password" class="text" />
						</p>
						<c:if test="${showSecretQuestion == true}">
							<p>
								<label><span class="req">*</span> Security Question </label> 
								<s:select name="secretQuestion" headerValue="Select One"
										headerKey="" list="questionMap" theme="simple"
										cssClass="grayText">
								</s:select>
							</p>
							<p>
								<label><span class="req">*</span> Answer</label> 
								<s:textfield name="secretAnswer" cssClass="shadowBox grayText"
											cssStyle="width: 200px;" value="" onfocus="clearTextbox(this)">
								</s:textfield>
							</p>
						</c:if>	
						<input type="submit" class="button action submit" value="Log In">
					</fieldset>
				</s:form>
				</div>
			</div>
		</div>
		<div class="span-8">
			<div id="supportmsg" class="sideblock">
				<strong><a href="mailto: support@servicelive.com">support@servicelive.com</a></strong>
				<h3>1-888-549-0640</h3>
				<strong>We're here to help.</strong>
			</div>

					<div id="diymsg">
						<h3>Did you know?</h3>
						<p>Create strong passwords you can remember by making an acronym from a famous saying, or using the name of something familiar to you, such as the title of a favorite book or movie and add numbers at the beginning, end, or in between.</p>
					</div>

			
		</div>

	</div>
	<tiles:insertDefinition name="blueprint.base.footer"/>
</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="anonymous:Welcome to Servicelive"/>
	</jsp:include>
	</body>
</html>
