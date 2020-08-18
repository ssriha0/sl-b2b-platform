<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="loginActionPath" scope="request" value="<%="https://" + request.getServerName() + ":" + System.getProperty("https.port") + request.getContextPath() + "/doLogin.action"%>"/>

<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="slLogin.loginError"/>
	</jsp:include>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive Error</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/modules.css" />
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
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
	</head>
	<body id="fixaux" class="tundra acquity">
	    
		<div id="page_margins">
			<div id="page">
				<div id="headerRightRail">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav" />
					<div id="rightRail">
						<jsp:include
							page="/jsp/public/homepage/body/modules/corpHQ_top.jsp" />
					</div>
				</div>
				<!-- BEGIN CENTER -->
				<div class="clearfix" id="">
					<table width="500">
						<tr>
							<td style="height: 35px">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td>
								<div id="homepageContent" style="padding-bottom: 100px; margin-left: 75px;">
									<div class="colLeft711" style="margin-top: 50px; width: 500px;">
										<form action="${loginActionPath}" method="post" name="doLogin">
											<h3>
												<s:actionerror />
												<s:fielderror />
											</h3>
											<p>
												Please re-enter your user name and password and then click
												'login.' If you have forgotten your user name or password,
												click on the appropriate link below. Missing information
												will be sent to the e-mail address you entered when
												establishing your ServiceLive account.
											</p>

											<div class="mainWellContent errorBox" style="width: 500px;">
												<table cellpadding="0" cellspacing="0">
													<tr>
														<td width="200">
															<label>
																User Name
															</label>
															<br>
															<s:textfield name="username" label="Login name"
																theme="simple" />
														</td>
														<td>
															<label>
																Password
															</label>
															<br>
															<s:password name="password" label="Password"
																theme="simple" />
														</td>
													</tr>
												</table>
											</div>
											<c:if test="${not empty destination}">
												<input type="hidden" name="destination" value="${destination}"/>
											</c:if>
											<div class="clearfix">
												<div class="formNavButtons">
													<span style="float: left;"> <input width="70"
															type="image" height="20" class="btn20Bevel"
															style="background-image: url(${staticContextPath}/images/btn/login.gif);"
															src="${staticContextPath}/images/common/spacer.gif" /> </span> Forgot
													your
													<a href="<s:url action="forgetUsernameAction" method="loadForgetUserPage" includeParams="none"/>">user name</a> or
													<a href="<s:url action="resetPasswordAction" method="loadResetPasswordPage" includeParams="none"/>">password</a>?

										</form>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>

			<!-- END CENTER   -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
		</div>
	</body>
</html>
