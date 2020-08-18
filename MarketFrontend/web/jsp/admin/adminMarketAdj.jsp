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

	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="Admin.marketAdjustment"/>
	</jsp:include>	


<html>
	<head>
		<title>Market Adjustment [Maintenance Panel]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

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
		 <jsp:param name="PageName" value="Admin - Market Adjustment"/>
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
							<%-- <img src="${staticContextPath}/images/sl_admin/hdr_admin_manageUsers.gif" /> --%>
							<h2>Market Adjustment Rate</h2>
						</div>
					</div>
				</div>

				<!-- END HEADER -->


				<div class="colLeft711">
					<div class="content">
					
						<jsp:include page="/jsp/buyer_admin/validationMessages.jsp" />						


						<input type="radio" style="margin-right:5px; vertical-align: middle" name="mode" checked="checked" onclick="showHideDivsEndingWith('view', 'edit')"/>View Mode
						<input type="radio" style="margin-right: 5px; margin-left: 10px; vertical-align: middle;" name="mode" onclick="showHideDivsEndingWith('edit', 'view')"/>Edit Mode
						<s:form action="adminMarketAdj_save.action" theme="simple">
						<div id="header_view" style="display:block">
							<table cellpadding="0" cellspacing="0" class="scrollerTableHdr adminMarketAdjHdr" border="0">
								<tr>
									<td class="column1">
										<s:url id="url" action="adminMarketAdj_displayPage.action">
										<s:param name="sortColumn">marketId</s:param>
										<c:choose>
											<c:when test="${sortOrder == null || (sortOrder == 'ASC' && sortColumn == 'marketId')}">
												<img src="${staticContextPath}/images/grid/arrow-up-white.gif"/>
												<s:param name="sortOrder">DESC</s:param>
											</c:when>
											<c:when test="${sortOrder == 'DESC' && sortColumn == 'marketId'}">
												<img src="${staticContextPath}/images/grid/arrow-down-white.gif"/>
												<s:param name="sortOrder">ASC</s:param>
											</c:when>
										</c:choose>
										</s:url>
										<s:a href="%{url}" cssStyle="color:white">Market ID</s:a>
									</td>
									<td class="column2">
										<s:url id="url" action="adminMarketAdj_displayPage.action">
										<s:param name="sortColumn">marketName</s:param>
										<c:choose>
											<c:when test="${sortOrder == 'ASC' && sortColumn == 'marketName'}">
												<img src="${staticContextPath}/images/grid/arrow-up-white.gif"/>
												<s:param name="sortOrder">DESC</s:param>
											</c:when>
											<c:when test="${sortOrder == 'DESC' && sortColumn == 'marketName'}">
												<img src="${staticContextPath}/images/grid/arrow-down-white.gif"/>
												<s:param name="sortOrder">ASC</s:param>
											</c:when>
										</c:choose>
										</s:url>
										<s:a href="%{url}" cssStyle="color:white">Market</s:a>
									</td>
									<td class="column3">
										<s:url id="url" action="adminMarketAdj_displayPage.action">
										<s:param name="sortColumn">stateName</s:param>
										<c:choose>
											<c:when test="${sortOrder == 'ASC' && sortColumn == 'stateName'}">
												<img src="${staticContextPath}/images/grid/arrow-up-white.gif"/>
												<s:param name="sortOrder">DESC</s:param>
											</c:when>
											<c:when test="${sortOrder == 'DESC' && sortColumn == 'stateName'}">
												<img src="${staticContextPath}/images/grid/arrow-down-white.gif"/>
												<s:param name="sortOrder">ASC</s:param>
											</c:when>
										</c:choose>
										</s:url>
										<s:a href="%{url}" cssStyle="color:white">State</s:a>
									</td>
									<td class="column4">
										<s:url id="url" action="adminMarketAdj_displayPage.action">
										<s:param name="sortColumn">adjustment</s:param>
										<c:choose>
											<c:when test="${sortOrder == 'ASC' && sortColumn == 'adjustment'}">
												<img src="${staticContextPath}/images/grid/arrow-up-white.gif"/>
												<s:param name="sortOrder">DESC</s:param>
											</c:when>
											<c:when test="${sortOrder == 'DESC' && sortColumn == 'adjustment'}">
												<img src="${staticContextPath}/images/grid/arrow-down-white.gif"/>
												<s:param name="sortOrder">ASC</s:param>
											</c:when>
										</c:choose>
										</s:url>
										<s:a href="%{url}" cssStyle="color:white">Adjustment Rate</s:a>
									</td>
								</tr>
							</table>
						</div>
						<div id="header_edit" style="display:none">
							<table cellpadding="0" cellspacing="0"
								class="scrollerTableHdr adminMarketAdjHdr" border=0>
								<tr>
									<td class="column1">
										Market ID
									</td>
									<td class="column2">
										Market
									</td>
									<td class="column3">
										State
									</td>
									<td class="column4">
										Adjustment Rate
									</td>
								</tr>
							</table>
						</div>
						
						<div class="grayTableContainer"	style="width: 100%; height: 350px;">
							<table cellpadding="0" cellspacing="0" class="gridTable adminMarketAdj" border=0>
								<c:forEach items="${marketList}" var="market">
									<tr>
										<td class="column1">
											${market.marketId}
										</td>
										<td class="column2">
											${market.marketName}										
										</td>
										<td class="column3">
											${market.stateName}
										</td>
										<td class="column4">
											<div id="adj_rate_${market.marketId}_view" name="adj_rate_${market.marketId}_view" style="display:block">
												${market.adjustment}
											</div>				
											<div id="adj_rate_${market.marketId}_edit" name="adj_rate_${market.marketId}_edit" style="display:none">
												<input type="textfield" id="adj_rate_${market.marketId}_edit" name="adj_rate_${market.marketId}_edit" size="5" style="width:40px"  value="${market.adjustment}"/>												
											</div>							
										</td>	
									</tr>
								</c:forEach>

							</table>
						</div>
						<div class="clearfix">
							<div id="save_edit" name="save_edit" style="display:none"> 						
								<div class="formNavButtons">
								       <s:submit type="input" 
										method="save"
										onclick="return confirmSave()"
										src="%{#request['staticContextPath']}/images/common/spacer.gif"
										cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/save.gif); width:49px; height:20px;"
										cssClass="btn20Bevel" 
										theme="simple" value=" " />
										<s:reset type="reset" 
										cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/reset.gif); width:49px; height:20px;"
										cssClass="btn20Bevel" 
										theme="simple" value="" />
																		
								</div>
							</div>
							<div id="save_view" name="save_view" style="display:block"> 						
								<div class="formNavButtons">
									&nbsp;
								</div>
							</div>
						</div>
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
