<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
	
	<fmt:formatDate var="todaysDate" value="<%=java.util.Calendar.getInstance().getTime() %>" pattern="yyyy-MM-dd"/>

<html>
	<head>
		<title>Select Provider Network [Admin Campaign Manager]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
	
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript">
			dojo.require("dijit.form.DateTextBox");
			dojo.require("dijit.Dialog");
			dojo.require("dojo.parser");
			dojo.require("dojo.date.locale");
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
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/service_order_wizard.css" />
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
			 <jsp:param name="PageName" value="SPN - Campaign Manager"/>
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
							SPN Campaign Manager
						</div>
					</div>
				</div>

				<!-- END HEADER -->

				<s:form action="spnAdminAction" id="spnAdminAction" theme="simple">
				<input type="hidden" name="theCurrentSpnId" id="theCurrentSpnId"> 
				
				 <s:hidden name="newCampaign.spnId" id="spid"/>
				 <s:hidden name="newCampaign.campaignStateDate" id="startDate"/>
	  			 <s:hidden name="newCampaign.campaignEndDate" id="endDate"/>
				<div class="darkGrayModuleHdr" style="width: 328px;">Select Providers Networks by Buyer</div>
				
					<s:select name="selectedSPN" id="selectedSPN" headerKey="-1"
					headerValue="-- Select One --" list="#attr.all_serviceProviderNetworks"
					listKey="spnId" listValue="spnName" size="4" theme="simple"
					cssStyle="width: 327px; background-color: #ffffff;"
					onchange="newco.jsutils.loadCampaigns('spnAdminAction','loadCampaigns','spnAdminAction',this)"
					disabled="false" />
				
				 <br> <br> <br>
				 <div class="darkGrayModuleHdr" style="width: 642px;"><div id="innerMessage">No Campaign Selected -</div> </div>
				 <table id="" style="margin: 0pt; width: 645px;" class="provSearchHdr">
					
						<tr>
							<td width="15px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td width="39px">ID</td>
							<td width="150px" align="center">Market</td>
							<td width="150px" align="center">Dates</td>
							<td width="120px">Service Pro Matches</td>
						</tr>
					
				</table>
				
				 <div class="grayTableContainer" style="margin: 0pt; width: 645px; height: 185px;">
					<table id="spnTb1" border="0" style="width: 620px;"/>
					
					</table>
				</div>	
				
				<table >
					<tr>
						<td><b>Available Markets</b></td>
						<td><b>From Date</b></td>
						<td><b>To Date</b></td> 
					</tr>
					<tr>
						<td width="220px">
							<s:select name="newCampaign.marketId" id="marketId" headerKey="-1"
								headerValue="-- All Markets --" list="#attr.all_markets"
								listKey="type" listValue="descr" size="1" theme="simple"
								cssStyle="width: 200px; background-color: #ffffff;" disabled="false" />
							
						</td>
						<td width="100px">
						
						
						<input type="text" dojoType="dijit.form.DateTextBox"
							class="shadowBox" style="width: 90px; position: relative"
							name="newCampaign.campaignStateDate" 
							id="dojoDate1" 
							value=""
							constraints="{min: '${todaysDate}'}"
						  	required="true" lang="en-us" 
						  	onChange="dojo.byId('startDate').value=dojo.date.locale.format(arguments[0],{selector:'date',datePattern:'MM/dd/yyyy'});"/>
						  	
						</td>
						<td width="100px">
							
							<input type="text" dojoType="dijit.form.DateTextBox"
							class="shadowBox" style="width: 90px; position: relative"
							name="newCampaign.campaignEndDate" 
							id="dojoDate2" 
							value=""
							constraints="{min: '${todaysDate}'}"
						  	required="true" lang="en-us" 
						  	onChange="dojo.byId('endDate').value=dojo.date.locale.format(arguments[0],{selector:'date',datePattern:'MM/dd/yyyy'});"/>
						  	
						  	
						</td> 
					</tr>
					<tr>
						<td></td>
						<td><input type="button" name="test" 
								   value="Add Campaign" 
								   onclick="newco.jsutils.createCampaign('spnAdminAction','createNewSPNCampaign','spnAdminAction')"></td>
						<td><input type="button" name="test" value="Cancel"></td> 
					</tr>
				</table>		
				</s:form>	
					
				<!-- END TAB PANE -->
			
				<div class="clear"></div>
			</div>
			<!-- START FOOTER -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER -->
		</div>
		<div dojoType="dijit.Dialog" id="spnCampaignMgr" title="SPN Campaign Manager">
			<b>There are validation errors with you criteria</b><br>
			<div id="message"></div>
		</div>
	</body>
</html>
