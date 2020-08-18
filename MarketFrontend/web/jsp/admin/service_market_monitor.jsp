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
		<title>ServiceLive [Service Order Monitor]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
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
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
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
			href="${staticContextPath}/css/so_monitor.css" />
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
	selectedNav = function (){ $("mktplaceTools").addClass("selected"); } 
	window.addEvent('domready',selectedNav);
</script>
	</head>
	<body class="tundra">
	   
		<div id="page_margins">
			<div id="page">
				<div id="mapLocationInfo" class="tipLeft">
					<div class="tipContents">
						<span class="closeTip"><a href="javascript:;"
							onclick="popUp(event,'mapLocationInfo')" title="Close"></a>
						</span>
						<h4>
							Service Order Information
						</h4>
						<table cellpadding="0" cellspacing="0" class="mapInfo">
							<tr>
								<td class="column1">
									ID#
								</td>
								<td class="column2">
									<a href="">123-456-789-000</a>
								</td>
							</tr>
							<tr>
								<td class="column1">
									Title
								</td>
								<td>
									Maecenas malesuada lobortis
								</td>
							</tr>
							<tr>
								<td class="column1">
									Service Window
								</td>
								<td>
									September 30, 2007, 1pm - 3pm
								</td>
							</tr>
							<tr>
								<td class="column1">
									Status
								</td>
								<td>
									Accepted
								</td>
							</tr>
							<tr>
								<td class="column1">
									Sub-status
								</td>
								<td>
									Parts Shipped
								</td>
							</tr>
							<tr>
								<td class="column1">
									Maximum Price
								</td>
								<td>
									$105.00
								</td>
							</tr>
							<tr>
								<td class="column1">
									Buyer
								</td>
								<td>
									Bob Valentine (ID# 111111123)
								</td>
							</tr>
							<tr>
								<td class="column1">
									Buyer Support
								</td>
								<td>
									Karl Jones
								</td>
							</tr>
							<tr>
								<td class="column1">
									Buyer Company
								</td>
								<td>
									ABC Inc. (ID#
									<a href="">234552349</a>)
								</td>
							</tr>
							<tr>
								<td class="column1">
									Customer
								</td>
								<td>
									Barbara Haberman
								</td>
							</tr>
							<tr>
								<td class="column1">
									Service Pro
								</td>
								<td>
									Amir Jafarian (ID#
									<a href="">123456789</a>)
								</td>
							</tr>
							<tr>
								<td class="column1">
									Provider Firm
								</td>
								<td>
									EJAT Inc. (ID#
									<a href="">986752349</a>)
								</td>
							</tr>
						</table>
					</div>
				</div>
				<!-- START HEADER -->
				<div id="headerSPReg">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>

					<div id="pageHeader">
						<img
							src="${staticContextPath}/images/sl_admin/hdr_mktplace_soMonitor.gif"
							width="334" height="17" />
						<div>
							<a href="">Create Service Order</a> |
							<a href="">View Service Order Templates</a>
						</div>
					</div>
				</div>
				<!-- END HEADER -->
				<!-- START TAB PANE -->
				<div id="mainTabContainer" dojoType="dijit.layout.TabContainer"
					class="somTabs" style="height: 1500px;">
					<!-- TAB 1   -->
					<a dojoType="dijit.layout.LinkPane"
						href="html_sections/modules/tab_som_buyer_posted.jsp"
						selected="true"><span class="tabNormal">Posted (100)</span>
					</a>

					<!-- TAB 2 -->
					<a dojoType="dijit.layout.LinkPane" href=""><span
						class="tabNormal">Expired (100)</span>
					</a>
					<!-- TAB 3 -->
					<a dojoType="dijit.layout.LinkPane" href=""><span
						class="tabNormal">Problem (100)</span>
					</a>
					<!-- TAB 4 -->
					<a dojoType="dijit.layout.LinkPane" href=""><span
						class="tabWide">Accepted (100)</span>
					</a>
					<!-- TAB 5 -->
					<a dojoType="dijit.layout.LinkPane" href=""><span
						class="tabNormal">Active (100)</span>
					</a>
					<!-- TAB 6  -->
					<a dojoType="dijit.layout.LinkPane" href=""><span
						class="tabNormal">Completed (1000)</span>
					</a>
					<!-- TAB 7 -->
					<a dojoType="dijit.layout.LinkPane"
						href="${contextPath}/jsp/admin/modules/tab_event_map.jsp"><span
						class="tabNormal">Event Map</span>
					</a>

					<!-- TAB 8 -->
					<a dojoType="dijit.layout.LinkPane" href=""><span
						class="tabNormal">Search</span>
					</a>
					<!-- END TAB PANE -->
					<div class="clear"></div>
				</div>
				<!-- START FOOTER -->
				<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
				<!-- END FOOTER -->
			</div>
		</div>
	</body>
</html>
