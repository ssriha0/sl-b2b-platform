	<%-- 
	
	DO NOT TOUCH ANY CODE IN THIS FILE WITHOUT TALKING TO KRISTIN FIRST!  Thanks!
	
	--%>
	
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ page import="com.newco.marketplace.util.PropertiesUtils" %>
<%@ page import="com.newco.marketplace.constants.Constants" %>
<% String waivePostingFee = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.WAIVE_POSTING_FEE); %>


<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
  
  <jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="Promotions.buyer"/>
	</jsp:include>	
  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head> 
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<title>ServiceLive Promotions</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

		<link rel="shortcut icon" 
		href="${staticContextPath}/images/favicon.ico" />			
		<link rel="stylesheet" type="text/css" 
		href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" 
		href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
		
		
		<!--[if lt IE 8]>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
		<![endif]-->

		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/nav.js"></script>

		<script language="JavaScript" type="text/javascript">
			function setHiddenFields(name, mainCategoryId, categoryId, subCategoryId, serviceTypeTemplateId, buyerTypeId)
			{
				document.getElementById('pop_name').value=name;
				document.getElementById('mainCategoryId').value=mainCategoryId;
				document.getElementById('categoryId').value=categoryId;
				document.getElementById('subCategoryId').value=subCategoryId;
				document.getElementById('serviceTypeTemplateId').value=serviceTypeTemplateId;
				document.getElementById('buyerId').value=buyerTypeId;

				jQuery.noConflict();
			    jQuery(document).ready(function($) {
		     		$('#exploreMarketPlace').jqm({modal:true, toTop: true});
		     		$('#exploreMarketPlace').jqmShow();
				 });
		
			}
		</script>
		
		
		<style type="text/css">
			.pageHeader {
				padding: 20px 10px;
			}
		
			#promotions h1 {
				color: #00A0D2;
			}

			#promotions h2 {
				background: #000;
				padding: 5px 10px;	
				color: #CCC;			
			}
			
			#promotions h2 span {
				color: #999;
				font-size: 10px;
				font-weight: normal;
				padding-left: 15px;
			}
			
			#promotions ul {
				margin: 20px 40px;
				font-size: 12px; 
			}

			#promotions ul li {
				padding-bottom: 10px;
			}
			
			.apromo {
				border: 1px solid silver;
				width: 974px;
			}
			
			.apromo p {
				margin: 20px 40px;
			}
			
		</style>
	</head>
	<body class="tundra acquity">
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
   	 		<jsp:param name="PageName" value="public:promotions:buyer"/>
		</jsp:include>
	
		<div id="page_margins">
			<div id="page">
				<div id="header">
					<tiles:insertDefinition name="newco.base.topnav" />
					<tiles:insertDefinition name="newco.base.blue_nav" />
					<tiles:insertDefinition name="newco.base.dark_gray_nav" />
				</div>
				<div id="hpWrap" class="clearfix">
					<div id="promotions" style="margin: 20px 0px;">
					<div class="pageHeader">
					<h1>ServiceLive Promotions</h1>
					
					<p>Below you can find information about promotions that run on ServiceLive.</p>
					</div>
					<div class="apromo nonsears">
					<a name="10"></a>
						<img src="${staticContextPath}/images/homepage/freePosting.png" style="margin: 5px;">
						<h2>Free Posting Introductory Offer <span><%=waivePostingFee%></span></h2>
						<ul>
							<li>Our usually low-price of $10.00 per service order Posting Fee is waived for all service orders posted <%=waivePostingFee%> for all buyer accounts.</li>
						</ul>
					</div>

					</div>
				</div>
				<jsp:include page="/jsp/public/common/blackoutFooter.jsp" />

			</div>

		</div>
	</div>

</html>

