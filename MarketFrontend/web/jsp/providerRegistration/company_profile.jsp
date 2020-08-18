<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<title>ServiceLive - Provider Profile</title>

		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
		<!--[if IE]>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
		<![endif]-->
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/memberManagement.css" />
		<link rel="stylesheet" href="${staticContextPath}/styles/plugins/ui.tabs1.css">
		
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/truncate/jquery.truncate-2.3-pack.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/memberManagementDriver.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/banner.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqueryui/jquery-ui.min.js"></script>
		
		<style type="text/css">
	         .ff3 .bannerDiv[style]{margin-left:-50px !important;}  
	         .ff2 .bannerDiv[style]{margin-left:0px;width:100%;}
	         .gecko .bannerDiv[style]{margin-left:0px;width:100%;}
	         .ie7 .bannerDiv{margin-left:-690px;}  
	         .ie9 .bannerDiv{margin-left:0px;}
		</style>	
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/banner.css" />
        <script language="JavaScript" type="text/javascript">
			/* function not_tab_two_click()
			{
				document.getElementById('company_ratings').style.display='none';
				document.getElementById('provider_ratings').style.display='';
			}
			function tab_two_click()
			{
				document.getElementById('company_ratings').style.display='';
				document.getElementById('provider_ratings').style.display='none';
			} */
			function show_more_comments(span_id)
			{
				document.getElementById("after_string_" + span_id).style.display = "inline";
				document.getElementById("read_more_" + span_id).style.display = "none";
			}
			function show_less_comments(span_id)
			{
				document.getElementById("after_string_" + span_id).style.display = "none";
				document.getElementById("read_more_" + span_id).style.display = "inline";
			}
			//function show_network_info()
			//{
			//	document.getElementById('network_info').style.display='';
			//	document.getElementById('provider_ratings').style.display='none';
			//}
			
			jQuery(document).ready(function($){
				$('#exploreMarketPlace').jqm({modal:true, toTop: true, trigger: "a.sendmeSO"});
				$( "#providerTabs" ).tabs();
			});
			
		</script>
	</head>
		<body class="acquity">
	<!-- <body class="acquity" onload="displayBanner();">
	        <div id="bannerDiv" class="bannerDiv" style="display:none;">
	           <span class="spanText" id="spanText"></span>
	          <a href="javascript:void(0);" onclick="removeBanner();"> Dismiss </a>
            </div> -->
			<a name="top"></a>
			<div id="page_margins">
				<div id="page">
					<tiles:insertAttribute name="header" />
					<tiles:insertAttribute name="body" />
					<tiles:insertAttribute name="footer" />

					<div class="jqmWindow" id="exploreMarketPlace">
						<div class="modalHomepage">
							<a href="#" class="jqmClose">Close</a>
						</div>
						<s:form name="mp" action="homepage_submitZip.action" method="POST" theme="simple">
							<div class="modalContent">
								<h2>Please enter the Zip Code of the location where work will be done.</h2>
								<s:textfield onclick="clear_enter_zip();" onblur="add_enter_zip();" name="zipCode" id="zipCode" value="Enter Zip Code" />
								<s:submit type="image" cssClass="findProv" src="%{#request['staticContextPath']}/images/simple/button-find-sp.png" />
							</div>
							<input id="pop_name" type="hidden" name="popularSimpleServices[0].name" value="<s:property value='name'/>" />
							<input id="mainCategoryId" type="hidden" name="popularSimpleServices[0].mainCategoryId" value="<s:property value='mainCategoryId'/>" />
							<input id="categoryId" type="hidden" name="popularSimpleServices[0].categoryId" value="<s:property value='categoryId'/>" />
							<input id="subCategoryId" type="hidden" name="popularSimpleServices[0].subCategoryId" value="<s:property value='sategoryId'/>" />
							<input id="serviceTypeTemplateId" type="hidden" name="popularSimpleServices[0].serviceTypeTemplateId" value="<s:property value='serviceTypeTemplateId'/>" />
							<input id="buyerId" type="hidden" name="popularSimpleServices[0].buyerTypeId" value="<s:property value='buyerTypeId'/>" />
						</s:form>
					</div>
				</div>
			</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="ProviderRegistration.companyProfile"/>
	</jsp:include>	
			
	</body>
</html>
