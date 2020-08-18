<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", -1);
%>
<html>
	<head>
		<title>ServiceLive [Provider Admin]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

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
			var contextPath = '${pageContext.request.contextPath}';
			
		
			
		</script>

		<style type="text/css">
@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css"
	;

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css"
	;
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
			href="${staticContextPath}/css/service_order_wizard.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />

		<script type="text/javascript"
			src="${staticContextPath}/javascript/prototype.js"></script>
		<script language="JavaScript"
			src="${staticContextPath}/javascript/js_registration/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/formfields.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/utils.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/calendar.css?random=20051112"
			media="screen"></link>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/calendar.js?random=20060118"></script>


	</head>
	<body>

		<div id="content_right_header_text">
			<%@ include file="../message.jsp"%>
		</div>
		<s:form id="companyProfileAction" name="companyProfileAction" action="companyProfileAction_doEdit" theme="simple">
			<s:hidden name="resourceId" value=""></s:hidden>
			<!-- NEW MODULE/ WIDGET-->

			<div class="grayModuleContent mainWellContent clearfix">
				<h3>
					<s:property value="companyProfileDto.businessName" />
				</h3>

				<p>
					Your complete provider firm profile is below. Some of this
					information is private and cannot be seen by buyers. To view your
					public profile, click on the 'public profile' tab above.
				</p>
				
				<jsp:include
					page="/jsp/providerRegistration/modules/panel_business_info.jsp" />									
				<jsp:include
					page="/jsp/providerRegistration/modules/panel_company_overview.jsp" />
				<jsp:include
					page="/jsp/providerRegistration/modules/panel_business_address.jsp" />
				<jsp:include
					page="/jsp/providerRegistration/modules/panel_primary_contact_info.jsp" />
				<jsp:include
					page="/jsp/providerRegistration/modules/panel_warranty_info.jsp" />
				<jsp:include
					page="/jsp/providerRegistration/modules/panel_workplace_policy_info.jsp" />
				<jsp:include
					page="/jsp/providerRegistration/modules/panel_licenses_certifications.jsp" />
				<jsp:include
					page="/jsp/providerRegistration/modules/panel_insurance_policies.jsp" />



			</div>



			<s:submit value="" action="companyProfileAction_doEdit" type="image"
				src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif"
				theme="simple"
				cssStyle="background-image:url(%{#request['staticContextPath']}/images/images_registration/btn/editProfile.gif);width:90px;height:20px;"
				cssClass="btn20Bevel" />
				
			<!--
			<div class="grayModuleContent mainWellContent clearfix">
				<div class="darkGrayModuleHdr">
					Vital Statistics
				</div>
							
			</div>
			
			<div class="grayModuleContent mainWellContent clearfix">
				<div class="darkGrayModuleHdr">Buyer Relations</div>
			</div> -->

		</s:form>

	</body>
</html>
