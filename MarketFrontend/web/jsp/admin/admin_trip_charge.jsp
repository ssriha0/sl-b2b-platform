<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<html>
	<head>
		<title>Admin [Trip Charge Config]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

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
		</script>
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
		
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	      .ie7 .bannerDiv{margin-left:-1010px;}
		</style>
	</head>
	<c:choose>
		<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
			<body class="tundra acquity simple" onload="${onloadFunction};">
		</c:when>
		<c:otherwise>
			<body class="tundra acquity" onload="${onloadFunction};">
		</c:otherwise>
	</c:choose>
     <jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="Admin.tripChargeConfig"/>
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
							<h2>Trip Charge Config</h2>
						</div>
					</div>
				</div>

				<!-- END HEADER -->


				<div class="colLeft711">
					<div class="content">
						<s:form action="adminTripCharge_" >

							<jsp:include page="/jsp/buyer_admin/validationMessages.jsp" />

							<c:if test="${msg != null}">
								<div id="soSuccess" class="clearfix" style="width:600px;height:20px;color: #1F8B06; margin: 10px 0; padding: 5px; border: 3px solid #7dc012; background: #eef7df; ">
									<p>${msg}</p>
								</div>
							</c:if>


							<s:if test="%{tripCharges == null}">
								<h3>Your company does not have the 'Trip Charge' feature.</h3>
							</s:if>

							<s:if test="%{tripCharges != null}">
								<table border=0>
									<s:iterator value="tripCharges" status="status">
										<tr>
											<td>
												<%--
												<s:textfield
													name="tripCharges[%{#status.index}].mainCategoryDesc"
													id="tripCharges[%{#status.index}].mainCategoryDesc"
													value="%{tripCharges[#status.index].mainCategoryDesc}"
													cssStyle="width: 250px;" cssClass="shadowBox" theme="simple"
													disabled="true"/>
													--%>
												<s:label value="%{tripCharges[#status.index].mainCategoryDesc}" theme="simple"/>
											</td>
											<td width=10px>
												&nbsp;
											</td>
											<td>
												$<s:textfield
													name="tripCharges[%{#status.index}].tripCharge"
													id="tripCharges[%{#status.index}].tripCharge"
													value="%{tripCharges[#status.index].tripCharge}"
													cssStyle="width: 45px;" cssClass="shadowBox" theme="simple"
													disabled="false"/>

											</td>
										</tr>
									</s:iterator>
									<tr height=10px>
										&nbsp;
									</tr>
									<tr>
										<td>
									       <s:submit type="input"
											method="save"
											src="%{#request['staticContextPath']}/images/common/spacer.gif"
											cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/save.gif); width:49px; height:20px;"
											cssClass="btn20Bevel"
											theme="simple" value=" " />
										</td>
										<td>
											&nbsp;
										</td>
										<td>

										<s:reset type="input"
										  			cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/cancel.gif);width:54px; height:20px;"
										  			cssClass="btn20Bevel"
										  			theme="simple"
										  			value=""/>
										</td>
									</tr>
								</table>
							</s:if>
						</s:form>
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

		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/hideShow.js"></script>
		<script type="text/javascript">
			function confirmSave()
			{
				return window.confirm('Do you want to save changes to\n Market Adjustment Rate?');
		    }
		</script>
	
	</body>
</html>
