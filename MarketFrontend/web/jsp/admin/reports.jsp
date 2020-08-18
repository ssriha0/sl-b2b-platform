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
		<title>ServiceLive Reports</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

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
			href="${staticContextPath}/css/admin.css" />
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
	<body class="tundra">
	   
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="Admin - Reports"/>
		</jsp:include>	
	
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
							alt="Service Order Reports" title="Service Order Reports" />
					</div>
				</div>
				<!-- END HEADER -->
				<!-- START TAB PANE -->
				<div class="colLeft711">
					<div class="content">
						<!-- NEW MODULE/ WIDGET-->
						<p>
							Below is a list of reports that you have saved. To run the report
							again, select the report, choose a report output and select 'run
							report.' You may only run one report at a time. Double click on
							the report name to change parameters within the saved template.
						</p>
						<p class="paddingBtm">
							<input type="image" src="${staticContextPath}/images/common/spacer.gif"
								width="127" height="20"
								style="background-image: url(${staticContextPath}/images/btn/createreport.gif);"
								class="btn20Bevel" />
						</p>
						<div class="darkGrayModuleHdr">
							Saved Reports
						</div>
						<div class="grayModuleContent mainWellContent clearfix">
							<p>
								Below is a list of reports that you have saved as templates. To
								run the report again, select the report, choose a report output
								and select 'run report.' You may only run one report at a time.
								Double click on the report name to change parameters within the
								saved template.
							</p>
							<table cellpadding="0" cellspacing="0"
								class="scrollerTableHdr savedReportsHdr">
								<tr>
									<td class="column1"></td>
									<td class="column2">
										File Name
									</td>
									<td class="column3">
										Date
									</td>
									<td class="column4">
										Time
									</td>
								</tr>
							</table>
							<div class="grayTableContainer"
								style="width: 649px; height: 200px;">
								<table cellpadding="0" cellspacing="0"
									class="gridTable savedReports">
									<tr>
										<td class="column1">
											<input type="checkbox">
										</td>
										<td class="column2">
											<a href="#">Saved Report 01</a>
										</td>
										<td class="column3">
											June 21, 2007
										</td>
										<td class="column4">
											8:47 PM
										</td>
									</tr>
									<tr>
										<td class="column1">
											<input type="checkbox">
										</td>
										<td class="column2">
											<a href="#">Saved Report 01</a>
										</td>
										<td class="column3">
											June 21, 2007
										</td>
										<td class="column4">
											8:47 PM
										</td>
									</tr>
									<tr>
										<td class="column1">
											<input type="checkbox">
										</td>
										<td class="column2">
											<a href="#">Saved Report 01</a>
										</td>
										<td class="column3">
											June 21, 2007
										</td>
										<td class="column4">
											8:47 PM
										</td>
									</tr>
									<tr>
										<td class="column1">
											<input type="checkbox">
										</td>
										<td class="column2">
											<a href="#">Saved Report 01</a>
										</td>
										<td class="column3">
											June 21, 2007
										</td>
										<td class="column4">
											8:47 PM
										</td>
									</tr>
									<tr>
										<td class="column1">
											<input type="checkbox">
										</td>
										<td class="column2">
											<a href="#">Saved Report 01</a>
										</td>
										<td class="column3">
											June 21, 2007
										</td>
										<td class="column4">
											8:47 PM
										</td>
									</tr>
									<tr>
										<td class="column1">
											<input type="checkbox">
										</td>
										<td class="column2">
											<a href="#">Saved Report 01</a>
										</td>
										<td class="column3">
											June 21, 2007
										</td>
										<td class="column4">
											8:47 PM
										</td>
									</tr>
								</table>
							</div>
							<p>
								Report Output Type
								<br>
								<select class="grayText" onclick="changeDropdown(this)"
									style="width: 200px;">
									<option value="">
										Select One
									</option>
								</select>
							</p>
							<p>
								<input type="image"
									src="${staticContextPath}/images/common/spacer.gif" width="127"
									height="20"
									style="background-image: url(${staticContextPath}/images/btn/runreport.gif);"
									class="btn20Bevel" />
							</p>
						</div>

					</div>
				</div>
				<!-- END TAB PANE -->
				<div class="colRight255 clearfix"></div>
				<div class="clear"></div>
			</div>
			<!-- START FOOTER -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER -->
		</div>
	</body>
</html>
