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
		<title>ServiceLive [Create Report]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
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

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
</style>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/slider.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/service_provider_profile.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>

		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<script language="javascript">
	selectedNav = function (){ $("adminOfc").addClass("selected"); } 
	window.addEvent('domready',selectedNav);
</script>
	</head>
	<c:choose>
		<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
			<body class="tundra acquity simple" onload="${onloadFunction}">
		</c:when>
		<c:otherwise>
			<body class="tundra acquity" onload="${onloadFunction}">
		</c:otherwise>
	</c:choose>
	
		<div id="page_margins">
			<div id="page">
				<!-- START HEADER -->
				<div id="headerSPReg">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
					<div id="pageHeader">
						<img
							src="${staticContextPath}/images/sl_admin/hdr_admin_enterpriseReports.gif"
							alt="Service Order Reports | Create New Report"
							title="Service Order Reports | Create New Report" />
					</div>
					<!-- #INCLUDE file="${contextPath}/common_html/html/html_sections/header/date.jsp" -->
				</div>
				<!-- END HEADER -->
				<!-- START TAB PANE -->
				<div class="colLeft711">
					<div class="content">
						<!-- NEW MODULE/ WIDGET-->
						<p class="paddingBtm">
							Define criteria and specify which fields you want to populate in
							your report. Once a report is created, you can save it as a
							template to be run later.
						</p>

						<div class="darkGrayModuleHdr">
							Report Criteria
						</div>
						<div class="grayModuleContent mainWellContent clearfix">
							<div style="float: left; width: 250px;">
								Status
								<br>
								<select class="grayText" onclick="changeDropdown(this)"
									style="width: 200px;" size="5">
									<option value="">
										Status Topic A
									</option>
									<option value="">
										Status Topic B
									</option>
									<option value="">
										Status Topic C
									</option>
									<option value="">
										Status Topic D
									</option>
									<option value="">
										Status Topic E
									</option>
									<option value="">
										Status Topic F
									</option>
									<option value="">
										Status Topic G
									</option>
									<option value="">
										Status Topic H
									</option>
								</select>
							</div>
							<div>
								Sub-status
								<br>
								<select class="grayText" onclick="changeDropdown(this)"
									style="width: 200px;" size="5">
									<option value="">
										Substatus Topic A
									</option>
									<option value="">
										Substatus Topic B
									</option>
									<option value="">
										Substatus Topic C
									</option>
									<option value="">
										Substatus Topic D
									</option>
									<option value="">
										Substatus Topic E
									</option>
									<option value="">
										Substatus Topic F
									</option>
									<option value="">
										Substatus Topic G
									</option>
									<option value="">
										Substatus Topic H
									</option>
								</select>
							</div>
						</div>
						<div class="darkGrayModuleHdr">
							Dates (Check all that apply)
						</div>
						<div class="grayModuleContent mainWellContent clearfix">
							<table cellpadding="0" cellspacing="0" width="580">
								<tr>
									<td width="150">
										<p>
											<input type="checkbox">
											Created
										</p>
									</td>
									<td width="150">
										<p>
											<input type="radio">
											<select class="grayText" onclick="changeDropdown(this)"
												style="width: 100px;">
												<option>
													Select One
												</option>
											</select>
										</p>
									</td>
									<td width="130">
										<p>
											<input type="radio">
											<input type="text" style="width: 100px;"
												onfocus="clearTextbox(this)" class="shadowBox grayText"
												value="[mm/dd/yy]" />
										</p>
									</td>
									<td width="150">
										<p>
											to
											<input type="text" style="width: 100px;"
												onfocus="clearTextbox(this)" class="shadowBox grayText"
												value="[mm/dd/yy]" />
										</p>
									</td>
								</tr>
								<tr>
									<td>
										<p>
											<input type="checkbox">
											Posted/Received
										</p>
									</td>
									<td>
										<p>
											<input type="radio">
											<select class="grayText" onclick="changeDropdown(this)"
												style="width: 100px;">
												<option>
													Select One
												</option>
											</select>
										</p>
									</td>
									<td>
										<p>
											<input type="radio">
											<input type="text" style="width: 100px;"
												onfocus="clearTextbox(this)" class="shadowBox grayText"
												value="[mm/dd/yy]" />
										</p>
									</td>
									<td>
										<p>
											to
											<input type="text" style="width: 100px;"
												onfocus="clearTextbox(this)" class="shadowBox grayText"
												value="[mm/dd/yy]" />
										</p>
									</td>
								</tr>
								<tr>
									<td>
										<p>
											<input type="checkbox">
											Accepted
										</p>
									</td>
									<td>
										<p>
											<input type="radio">
											<select class="grayText" onclick="changeDropdown(this)"
												style="width: 100px;">
												<option>
													Select One
												</option>
											</select>
										</p>
									</td>
									<td>
										<p>
											<input type="radio">
											<input type="text" style="width: 100px;"
												onfocus="clearTextbox(this)" class="shadowBox grayText"
												value="[mm/dd/yy]" />
										</p>
									</td>
									<td>
										<p>
											to
											<input type="text" style="width: 100px;"
												onfocus="clearTextbox(this)" class="shadowBox grayText"
												value="[mm/dd/yy]" />
										</p>
									</td>
								</tr>
								<tr>
									<td>
										<p>
											<input type="checkbox">
											Completed
										</p>
									</td>
									<td>
										<p>
											<input type="radio">
											<select class="grayText" onclick="changeDropdown(this)"
												style="width: 100px;">
												<option>
													Select One
												</option>
											</select>
										</p>
									</td>
									<td>
										<p>
											<input type="radio">
											<input type="text" style="width: 100px;"
												onfocus="clearTextbox(this)" class="shadowBox grayText"
												value="[mm/dd/yy]" />
										</p>
									</td>
									<td>
										<p>
											to
											<input type="text" style="width: 100px;"
												onfocus="clearTextbox(this)" class="shadowBox grayText"
												value="[mm/dd/yy]" />
										</p>
									</td>
								</tr>
								<tr>
									<td>
										<p>
											<input type="checkbox">
											Closed
										</p>
									</td>
									<td>
										<p>
											<input type="radio">
											<select class="grayText" onclick="changeDropdown(this)"
												style="width: 100px;">
												<option>
													Select One
												</option>
											</select>
										</p>
									</td>
									<td>
										<p>
											<input type="radio">
											<input type="text" style="width: 100px;"
												onfocus="clearTextbox(this)" class="shadowBox grayText"
												value="[mm/dd/yy]" />
										</p>
									</td>
									<td>
										<p>
											to
											<input type="text" style="width: 100px;"
												onfocus="clearTextbox(this)" class="shadowBox grayText"
												value="[mm/dd/yy]" />
										</p>
									</td>
								</tr>
								<tr>
									<td>
										<p>
											<input type="checkbox">
											Cancelled
										</p>
									</td>
									<td>
										<p>
											<input type="radio">
											<select class="grayText" onclick="changeDropdown(this)"
												style="width: 100px;">
												<option>
													Select One
												</option>
											</select>
										</p>
									</td>
									<td>
										<p>
											<input type="radio">
											<input type="text" style="width: 100px;"
												onfocus="clearTextbox(this)" class="shadowBox grayText"
												value="[mm/dd/yy]" />
										</p>
									</td>
									<td>
										<p>
											to
											<input type="text" style="width: 100px;"
												onfocus="clearTextbox(this)" class="shadowBox grayText"
												value="[mm/dd/yy]" />
										</p>
									</td>
								</tr>
								<tr>
									<td>
										<p>
											<input type="checkbox">
											Scheduled
										</p>
									</td>
									<td>
										<p>
											<input type="radio">
											<select class="grayText" onclick="changeDropdown(this)"
												style="width: 100px;">
												<option>
													Select One
												</option>
											</select>
										</p>
									</td>
									<td>
										<p>
											<input type="radio">
											<input type="text" style="width: 100px;"
												onfocus="clearTextbox(this)" class="shadowBox grayText"
												value="[mm/dd/yy]" />
										</p>
									</td>
									<td>
										<p>
											to
											<input type="text" style="width: 100px;"
												onfocus="clearTextbox(this)" class="shadowBox grayText"
												value="[mm/dd/yy]" />
										</p>
									</td>
								</tr>
							</table>
						</div>
						<div class="darkGrayModuleHdr">
							Output Fields (Check all that apply)
						</div>
						<div class="grayModuleContent mainWellContent clearfix">
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td width="300">
										<p>
											<input type="checkbox">
											Service Order ID
										</p>
									</td>
									<td>
										<p>
											<input type="checkbox">
											Service Location City
										</p>
									</td>
								</tr>
								<tr>
									<td>
										<p>
											<input type="checkbox">
											Custom Reference Fields
										</p>
									</td>
									<td>
										<p>
											<input type="checkbox">
											Service Location State
										</p>
									</td>
								</tr>
								<tr>
									<td>
										<p>
											<input type="checkbox">
											Created By Name
										</p>
									</td>
									<td>
										<p>
											<input type="checkbox">
											Service Location ZIP
										</p>
									</td>
								</tr>
								<tr>
									<td>
										<p>
											<input type="checkbox">
											Closer Name
										</p>
									</td>
									<td>
										<p>
											<input type="checkbox">
											Provider ID
										</p>
									</td>
								</tr>
								<tr>
									<td>
										<p>
											<input type="checkbox">
											Created Date
										</p>
									</td>
									<td>
										<p>
											<input type="checkbox">
											Provider Name
										</p>
									</td>
								</tr>
								<tr>
									<td>
										<p>
											<input type="checkbox">
											Closed Date
										</p>
									</td>
									<td>
										<p>
											<input type="checkbox">
											Provider Company ID
										</p>
									</td>
								</tr>
								<tr>
									<td>
										<p>
											<input type="checkbox">
											Service Dates
										</p>
									</td>
									<td>
										<p>
											<input type="checkbox">
											Provider Rating (this SO)
										</p>
									</td>
								</tr>
								<tr>
									<td>
										<p>
											<input type="checkbox">
											Service Window
										</p>
									</td>
									<td>
										<p>
											<input type="checkbox">
											Buyer Rating (this SO)
										</p>
									</td>
								</tr>
							</table>
						</div>
						<div class="darkGrayModuleHdr">
							Save as Report Template
						</div>
						<div class="grayModuleContent mainWellContent clearfix">
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td width="290">
										<p>
											<input type="checkbox">
											Save this report as a template to be run later.
										</p>
									</td>
									<td width="220">
										<p>
											<input type="text" style="width: 200px;"
												onfocus="clearTextbox(this)" class="shadowBox grayText"
												value="[Report Name]" />
										</p>
									</td>
									<td>
										<p>
											<input type="image"
												src="${staticContextPath}/images/common/spacer.gif" width="48"
												height="20"
												style="background-image: url(${staticContextPath}/images/btn/save.gif);"
												class="btn20Bevel" />
										</p>
									</td>
								</tr>
							</table>
						</div>
						<div class="clearfix">
							<div class="formNavButtons">
								<input type="image"
									src="${staticContextPath}/images/common/spacer.gif" width="82"
									height="20"
									style="background-image: url(${staticContextPath}/images/btn/runreport.gif);"
									class="btn20Bevel" />
							</div>
						</div>
					</div>
				</div>
				<!-- END TAB PANE -->
				<div class="colRight255 clearfix">
					<input type="image" src="${staticContextPath}/images/common/spacer.gif"
						width="176" height="20"
						style="background-image: url(${staticContextPath}/images/btn/returnToEntReports.gif);"
						class="btn20Bevel" />
				</div>
				<div class="clear"></div>
			</div>
			<!-- START FOOTER -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER -->
		</div>
	</body>
</html>
