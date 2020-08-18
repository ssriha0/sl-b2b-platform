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
		<title>Select Provider Network [Criteria Builder]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
	
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit.Dialog");
			dojo.require("dojo.parser");
			dojo.require("newco.jsutils");
			newco.jsutils.setGlobalContext('${contextPath}'); 
		</script>
		<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/hideShow.js"></script>
		
		
		<style type="text/css">
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";

			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
		</style>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/service_provider_profile.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/admin.css" />
		<STYLE type="text/css">
		
			spnCriteriaCat {}
			spnCriteriaSkill {}
		
		</STYLE>
		
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
	
	</head>
	<body class="tundra">
	
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="SPN - Criteria Builder"/>
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
							<!-- <img src="${staticContextPath}/images/sl_admin/hdr_admin_manageUsers.gif" /> -->
							Select Provider Network | Criteria Builder
						</div>
					</div>
				</div>

				<!-- END HEADER -->

				<s:form action="spnBuyerCriteriaBuilderAction" id="spnBuyerCriteriaBuilderAction" theme="simple">
				<div dojoType="dijit.layout.ContentPane" title="Additional Information"
					href="" class="colRight255 clearfix" preventCache="true"
					useCache="false" cacheContent="false">
					<jsp:include page="/jsp/spn/spnFilterCriteria.jsp" />
				</div>		
				
					<div class="grayTableContainer"
								style="width: 711px; margin: 0; height: auto; ">
								<!-- content section -->
								<jsp:include page="/jsp/spn/spnserviceCategoriesAndTasks.jsp" />
					</div>
					
				</s:form>	
					
				<!-- END TAB PANE -->
			
				<div class="clear"></div>
			</div>
			<!-- START FOOTER -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER -->
		</div>
		<div dojoType="dijit.Dialog" id="spnCriteria" title="SPN Criteria Builder">
			<b>There are validation errors with you criteria</b><br>
			<div id="message"></div>
		</div>
	</body>
</html>
