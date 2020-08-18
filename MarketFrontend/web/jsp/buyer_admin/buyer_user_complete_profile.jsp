<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
	 <jsp:param name="PageName" value="BuyerAdmin.viewCompleteProfile"/>
</jsp:include>	
<html>
	<head>
		<title>ServiceLive [Buyer Admin]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit.Dialog");
			dojo.require("dijit._Calendar");
			dojo.require("dojo.date.locale");
			dojo.require("dojo.parser");
			dojo.require("dijit.form.Slider");
			dojo.require("dijit.layout.LinkPane");
			//dojo.require("newco.servicelive.SOMRealTimeManager");
			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
		</script>
		<style type="text/css">
@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";

@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
</style>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/slider.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/service_provider_profile.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
	</head>
	<body class="tundra">
	    
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="Buyer Admin - Complete Profile"/>
		</jsp:include>	
	
		<div id="page_margins">
			<div id="page">
				<!-- START HEADER -->
				<div id="header">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
					
					<div id="pageHeader">
						<div>
							<img src="${staticContextPath}/images/admin/hdr_manageUsersIndProfile.gif" />
						</div>
					</div>
					<!-- #INCLUDE file="${contextPath}/common_html/html/html_sections/header/date_balance.jsp" -->
				</div>
				<!-- END HEADER -->
				<!-- START TAB PANE -->
				<div class="colLeft711">
					<div class="content">
						<h3 class="paddingBtm">
							Victor Papanek
						</h3>
						<!-- NEW MODULE/ WIDGET-->
						<div class="darkGrayModuleHdr">
							Identification
						</div>
						<div class="grayModuleContent mainWellContent clearfix">
							<div>
								<table cellpadding="0" cellspacing="0">
									<tr height="35">
										<td width="250">
											<strong>User ID#</strong>
											<br />
											8888888888
										</td>
										<td width="250">
											<strong>User Name</strong>
											<br />
											RealWorldGuy
										</td>
										<td>
											<strong>Job Title</strong>
											<br />
											Assistant Manager
										</td>
									</tr>
								</table>
							</div>
							<div class="grayModuleHdr">
								Roles
							</div>
							<div class="grayModuleContent mainWellContent clearfix">
								-Associate
							</div>
						</div>
						<div class="darkGrayModuleHdr">
							ServiceLive Activities &amp; Permissions
						</div>
						<div class="grayModuleContent mainWellContent clearfix">
							<table cellpadding="0" cellspacing="0">
								<tr height="35">
									<td width="350">
										<strong>Service Order Activities</strong>
										<br />
										Create Service Orders
									</td>
								</tr>
								<tr height="35">
									<td>
										<strong>Financial &amp; Banking Activities</strong>
										<br />
										None
									</td>
								</tr>
								<tr height="35">
									<td>
										<strong>Administrator Activities</strong>
										<br />
										Manage Service Order Templates
									</td>
								</tr>
							</table>
						</div>
						<div class="darkGrayModuleHdr">
							Contact Information
						</div>
						<div class="grayModuleContent mainWellContent clearfix">
							<table cellpadding="0" cellspacing="0" class="contactInfoTable">
								<tr height="20">
									<td class="column1">
										Work Phone
									</td>
									<td>
										404-555-8747 Ext. 22
									</td>
								</tr>
								<tr height="20">
									<td class="column1">
										Mobile Phone
									</td>
									<td>
										404-555-2232
									</td>
								</tr>
								<tr height="20">
									<td class="column1">
										E-mail
									</td>
									<td>
										<a href="">victor.papanek@companyname.com</a>
									</td>
								</tr>
								<tr height="20">
									<td class="column1">
										Alternate E-mail
									</td>
									<td>
										<a href="">vpapanek@realworld.com</a>
									</td>
								</tr>
							</table>
						</div>
						<div class="darkGrayModuleHdr">
							Maximum Price
						</div>
						<div class="grayModuleContent mainWellContent clearfix">
							$2,500
						</div>
						<div class="clearfix">
							<div class="formNavButtons">
								<input type="image" src="${staticContextPath}/images/common/spacer.gif"
									width="72" height="20"
									style="background-image: url(${staticContextPath}/images/btn/previous.gif);"
									class="btn20Bevel" />
								<input type="image" src="${staticContextPath}/images/common/spacer.gif"
									width="86" height="20"
									style="background-image: url(${staticContextPath}/images/btn/editProfile.gif);"
									class="btn20Bevel" />

							</div>
						</div>
					</div>
				</div>
				<!-- END TAB PANE -->
				<div class="colRight255 clearfix">
				</div>
				<div class="clear"></div>
			</div>
			<!-- START FOOTER -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER -->
		</div>
	</body>
</html>
