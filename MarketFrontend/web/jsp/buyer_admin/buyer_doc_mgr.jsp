<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

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
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/service_provider_profile.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/admin.css" />

	</head>
	<body class="tundra">
	   
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="Buyer Admin - Doc Manager"/>
		</jsp:include>	
	
		<div id="page_margins">
			<div id="page">
				<!-- START HEADER -->
				<div id="headerShort">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
					
					<div id="pageHeader">
						<div>
							<img src="${staticContextPath}/images/admin/hdr_adminOfcDocMgr.gif"
								alt="Administrative Office | Document Manager"
								title="Administrative Office | Document Manager" />
						</div>
					</div>
					<!-- #INCLUDE file="${contextPath}/common_html/html/html_sections/header/date_balance.jsp" -->
				</div>
				<!-- END HEADER -->

				<div class="colLeft711">
					<div class="content">
						<%--
						<iframe width="100%" height="520px" marginwidth="0" marginheight="0"
							frameborder="0"
							src="/MarketFrontend/soDocumentsAndPhotos_getDocuments.action"
							name="inner_document_grid" id="inner_document_grid" scrolling="no">
						</iframe>
						--%>
						<iframe width="100%" height="820px" marginwidth="0" marginheight="0"
							frameborder="0"
							src="/MarketFrontend/buyerAdminDocManager_getDocuments.action"
							name="inner_document_grid" id="inner_document_grid" scrolling="no">
						</iframe>
						
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
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<%-- Commented script as the below code is for adding events for "Administrator Office" menu items, but those won't show up for buyer. Those are only for
		provider admin.
		<script language="javascript">
			selectedNav = function (){ $("adminOfc").addClass("selected"); } 
			window.addEvent('domready',selectedNav);
		</script>
		--%>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
	</body>
</html>
