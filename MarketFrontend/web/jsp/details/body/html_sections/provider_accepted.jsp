<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>[Provider - Accepted]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1">
		<script type="text/javascript" src="../../js/prototype.js"></script><script language="JavaScript" src="../../js/tooltip.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript" src="../../js/formfields.js"></script>
		<style type="text/css">
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
		</style>
		<link rel="stylesheet" type="text/css" href="../../css/main.css" />
		<link rel="stylesheet" type="text/css" href="../../css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="../../css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="../../css/buttons.css" />
		<link rel="stylesheet" type="text/css" href="../../css/tooltips.css" />
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
		<link rel="stylesheet" type="text/css" href="../../css/dijitTitlePane-serviceLive.css">
        <link rel="stylesheet" type="text/css" href="../../css/dijitCalendar-serviceLive.css">
        <link rel="stylesheet" type="text/css" href="../../css/so_details.css">
		<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js" djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit.Dialog");
			dojo.require("dijit._Calendar");
			dojo.require("dojo.date.locale");
			dojo.require("dojo.parser");
			//dojo.require("newco.servicelive.SOMRealTimeManager");
			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
		</script>
		<script language="JavaScript" type="text/javascript">
			//_commonSOMgr = new SOMRealTimeManager('../../ajax/AjaxJSonManager.action',10000);
		</script>

	</head>
	<body class="tundra">
	
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="SOD - Provider Accepted"/>
		</jsp:include>	
	
		<div id="page_margins">
			<div id="page">
				<div dojoType="dijit.layout.ContentPane" href="html_sections/header/hdr_provider.html">
				</div>
				<div id="mainTabContainer" dojoType="dijit.layout.TabContainer"
					style="height: 4000px; width: 975px;;" class="detailsTabs">
					<div id="Summary" dojoType="dijit.layout.ContentPane" xstyle="margin: 5px 5px 5px 5px;" selected="true"
						title="Summary" href="html_sections/modules/tab_summary_provider_accepted.html">
					</div>
					<div dojoType="dijit.layout.ContentPane"
						title="Notes" href="html_sections/modules/tab_so_notes.html">
					</div>
                    <div dojoType="dijit.layout.ContentPane"
						title="Time on Site" href="html_sections/modules/tab_time_on_site.html">
					</div>
                     <div dojoType="dijit.layout.ContentPane"
						title="ServiceLive Support"	href="html_sections/modules/tab_support.html">
					</div>
				</div>
				<!-- END TAB PANE -->
				<div class="clear"></div>
				
				<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			</div>
		</div>
	</body>
</html>
