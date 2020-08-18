<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="ProviderRegistration.resetPassword"/>
	</jsp:include>	
	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>ServiceLive [Reset Password]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/contact.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/login.css" />
		<script language="JavaScript"
			src="${staticContextPath}/javascript/js_registration/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/formfields.js"></script>
	</head>
	<body class="tundra">
	    
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
        	 <jsp:param name="PageName" value="Reset Password"/>
		</jsp:include>
		<div id="page_margins">
			<div id="page">
				<!-- START HEADER -->
				<div id="headerSPReg">
					<jsp:include page="/jsp/public/common/header/sl_logo.jsp" />
				</div>
				<!-- END HEADER -->
				<s:form action="forgotPasswordAction.do" theme="simple">
					<div class="colLeft711" style="padding-bottom: 100px;">
						<div class="content">
							<h3>
								Reset Password
							</h3>
							<p>
								If you have forgotten your password, you can reset it. Just type
								your e-mail address and ServiceLive user name below.
							</p>
							<div class="mainWellContent">
								<p>
									<label>
										E-mail Address
									</label>
									<br>
									<s:textfield name="email" onfocus="clearTextbox(this)"
										value="[E-mail Address]" cssStyle="width: 200px;"
										cssClass="shadowBox grayText" />
								</p>
							</div>
							<div class="mainWellContent">
								<p>
									<label>
										ServiceLive User Name
									</label>
									<br>
									<s:textfield name="userName" value="[User Name]"
										onfocus="clearTextbox(this)" value="[User Name]"
										cssStyle="width: 200px;" cssClass="shadowBox grayText" />
									<small class="forgot"> Forgot your
									<a href="#">user name</a>? </small>
								</p>
							</div>
							<div class="clearfix">
								<div class="formNavButtons">
									<input width="70" type="image" height="22"
										class="btnBevel inlineBtn"
										style="background-image: url(${staticContextPath}/images/images_registration/btn/submit.gif);"
										src="${staticContextPath}/images/images_registration/common/spacer.gif" />
									&nbsp;&nbsp;&nbsp;&nbsp;
									<a href="">Cancel</a>
								</div>
							</div>
						</div>
					</div>
				</s:form>
			</div>
		</div>
		<div class="clear"></div>
		<!-- START FOOTER -->
		<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
		<!-- END FOOTER -->
	</body>
</html>
