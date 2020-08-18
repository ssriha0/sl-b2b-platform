<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive [SL Admin] Add/Edit User</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

		<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/service_provider_profile.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/admin.css" />

		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript"
			src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
	</head>
	<body class="tundra">
	     
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="Admin - Manage Users"/>
		</jsp:include>	
	
		<div id="page_margins">
			<div id="page">
				<!-- START HEADER -->
				<div id="headerSPReg">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
					<div id="pageHeader">
						<img src="${staticContextPath}/images/sl_admin/hdr_admin_addUser.gif"
							width="287" height="14" alt="Administrator Office | Manage Users"
							title="Administrator Office | Manage Users" />
					</div>
					<!-- #INCLUDE file="${contextPath}/common_html/html/html_sections/header/date.jsp" -->
				</div>
				<!-- END HEADER -->
				<div class="colLeft711">
					<div class="content">
						<!-- NEW MODULE/ WIDGET-->
						<div class="darkGrayModuleHdr">
							Personal Information
						</div>
						<div class="grayModuleContent mainWellContent clearfix">
							<p>
								Please enter the personal information requested below. The
								Social Security number, which will be encrypted for security,
								will be used as the unique identifier for this service pro on
								the ServiceLive network.
							</p>
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td width="320">
										First Name
										<br />
										<input type="text" style="width: 230px;"
											onfocus="clearTextbox(this)" class="shadowBox grayText"
											value="[First Name]" />
									</td>

									<td>
										Middle Name
										<br />
										<input type="text" style="width: 230px;"
											onfocus="clearTextbox(this)" class="shadowBox grayText"
											value="[Middle Name]" />
										optional
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										Last Name
										<br />
										<input type="text" style="width: 230px;"
											onfocus="clearTextbox(this)" class="shadowBox grayText"
											value="[Last Name]" />
									</td>

									<td>
										Suffix (Jr., II, etc.)
										<br />
										<input type="text" style="width: 110px;"
											onfocus="clearTextbox(this)" class="shadowBox grayText"
											value="[Suffix]" />
										optional
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
								</tr>
							</table>
						</div>
						<div class="darkGrayModuleHdr">
							Job Title, Role, &amp; Permissions
						</div>
						<div class="grayModuleContent mainWellContent clearfix">
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td width="320">
										Administrator Role
										<br />
										<select style="width: 230px;" onclick="changeDropdown(this)"
											class="grayText">
											<option>
												Select One
											</option>
										</select>
									</td>

									<td>
										Job Title
										<br />
										<input type="text" style="width: 230px;"
											onfocus="clearTextbox(this)" class="shadowBox grayText"
											value="[Job Title]" />
										optional
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										<input type="checkbox" checked />
										Manage Finances
										<ul class="vertBulleted" style="margin-left: 16px;">
											<li>
												Access to Administrative ServiceLive Wallet
											</li>
											<li>
												Issue refunds or credits to members
											</li>
										</ul>
									</td>

									<td>
										<input type="checkbox" checked />
										Manage Admin Users
										<ul class="vertBulleted" style="margin-left: 16px;">
											<li>
												View, add, edit or disable administrative user accounts
											</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										<input type="checkbox" checked />
										Marketplace Administration
										<ul class="vertBulleted" style="margin-left: 16px;">
											<li>
												Access to member portal to control member accounts
											</li>
											<li>
												Market monitor access
											</li>
											<li>
												Ratings - view/audit
											</li>
										</ul>
									</td>

									<td>
										<input type="checkbox" checked />
										Member Audit
										<ul class="vertBulleted" style="margin-left: 16px;">
											<li>
												Access provider and buyer audit functions
											</li>
										</ul>
									</td>
								</tr>
								<tr>
									<td>
										&nbsp;
									</td>
								</tr>
							</table>

						</div>
						<div class="darkGrayModuleHdr">
							User Name
						</div>
						<div class="grayModuleContent mainWellContent clearfix">
							<p>
								Please create a user name login for this buyer.
							</p>
							<p>
								User Name
								<br />
								<input type="text" style="width: 161px;"
									onfocus="clearTextbox(this)" class="shadowBox grayText"
									value="[User Name]" />
							</p>
							<br clear="all" />
						</div>
						<div class="clearfix">
							<div class="formNavButtons">
								<input type="image"
									src="${staticContextPath}/images/common/spacer.gif" width="50"
									height="20"
									style="background-image: url(${staticContextPath}/images/btn/save.gif);"
									class="btn20Bevel" />
								<input type="image"
									src="${staticContextPath}/images/common/spacer.gif" width="88"
									height="20"
									style="background-image: url(${staticContextPath}/images/btn/removeUser.gif);"
									class="btn20Bevel" />
							</div>
							<div class="bottomRightLink">
								<a href="">Cancel</a>
							</div>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				<!-- START FOOTER -->
				<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
				<!-- END FOOTER -->
			</div>
		</div>
	</body>
</html>
