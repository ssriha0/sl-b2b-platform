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
		<title><fmt:message bundle="${serviceliveCopyBundle}" key="spn.buyer.landing.label.title"/></title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

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
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/service_provider_profile.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/admin.css" />
		
	</head>
	<body class="tundra">
	
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="SPN - Buyer Landing"/>
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
							<h2><fmt:message bundle="${serviceliveCopyBundle}" key="spn.buyer.landing.label.header"/></h2>
						</div>
					</div>
				</div>

				<!-- END HEADER -->


				<div class="" style="padding-left:15px ; padding-right:15px">
					<div class="content">
						<table cellpadding="0" cellspacing="0"
							class="scrollerTableHdr spnLandingHdr">
							<tr>
								<td class="column1">
									&nbsp;
								</td>
								<td class="column2">
									&nbsp;
								</td>
								<td class="column3">
									<fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.network_name"/>
								</td>
								<td class="column4">
									<fmt:message bundle="${serviceliveCopyBundle}" key="spn.buyer.landing.label.sp_matches"/>
								</td>
								<td class="column5">
									<fmt:message bundle="${serviceliveCopyBundle}" key="spn.buyer.landing.label.members"/>
								</td>
								<td class="column6">
									<fmt:message bundle="${serviceliveCopyBundle}" key="spn.buyer.landing.label.inactive"/>
								</td>
								<td class="column7">
									<fmt:message bundle="${serviceliveCopyBundle}" key="spn.buyer.landing.label.applicant"/>
								</td>
							</tr>
						</table>
						<div class="grayTableContainer"
							style="width: 798px; height: 200px;">
							<table cellpadding="0" cellspacing="0"
								class="gridTable spnLanding">
								<c:forEach items="${buyerSPNList}" var="network">
									<tr>
										<td class="column1">
											<a href="spnBuyerBuilderAction_displayPage.action?spnID=${network.id}">Edit</a>
										</td>
										<td class="column2">
											<a href="spnMemberManager_intiPageView.action?spnId=${network.id}">Manage Members</a> 
										</td>
										<td class="column3">
											${network.name}
										</td>
										<td class="column4">
											${network.numMatches}
										</td>	
										<td class="column5">
											${network.numMembers}
										</td>	
										<td class="column6">
											${network.numInactive}
										</td>	
										<td class="column7">
											${network.numApplicants}
										</td>	
										
									</tr>
								</c:forEach>

							</table>
						</div>
						<div class="clearfix">
							<div class="formNavButtons">
								<s:form action="spnBuyerBuilderAction_displayPage.action">
									<input width="37" type="image" height="20" class="btn20Bevel"
										style="background-image: url(${staticContextPath}/images/btn/add.gif);"
										src="${staticContextPath}/images/common/spacer.gif"/>
								</s:form>
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
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
	</body>
</html>
