	<%-- 
	
	DO NOT TOUCH ANY CODE IN THIS FILE WITHOUT TALKING TO KRISTIN FIRST!  Thanks!
	
	--%>
	
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
   <jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="Promotions.provider"/>
	</jsp:include>	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head> 
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<title>ServiceLive Provider Promotions</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

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
        	 <jsp:param name="PageName" value="public:promotions:provider"/>
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
					<h1>Service Provider Promotions</h1>
					
					<p>Below you can find information about promotions that run on ServiceLive.</p>
					</div>
					<div class="apromo nonsears" style="color: #222; font-size: 12px;">
					<a name="10"></a>
						<img src="${staticContextPath}/images/promotions/nonsears-fall-banner.png">
						<h2>Service Fee-Free Program<span>11/13/08 through 02/28/09</span></h2>
						<p><a href="javascript:history.go(-1)">&laquo; Back</a></p>

						<p>It's been an exciting year for ServiceLive, and there's much more to come in 2009.  In 2008 alone, our customers have already completed tens of thousands of service orders and paid millions of dollars directly to small business owners and other providers in test markets across the country.  <strong>As a way for us to say thanks, we have a special offer for you &#8212; the "Service Fee-Free" Program.</strong></p>
						<p>Here's how it works:  You can accept any consumer service orders through ServiceLive's Home/Office portal through January <strong>without paying ServiceLive's usual 10% Service Fee</strong>, and the $10 posting fee is waived for consumers.  <strong>ServiceLive will not be collecting any fees!**</strong></p>
						<p><strong>We've got big plans for 2009.</strong>  We are launching ServiceLive.com to consumers in February with a national public relations campaign featuring a nationally recognized home improvement guru and an extensive online effort, so now is the time to position your firm to get ready for more traffic to the site.  <strong>Make sure that future service orders are being routed to YOU</strong> by using this limited time offer to build up some customer ratings on the platform now and to learn how the platform works - without any cost to you.  </p>
						<p><strong>The following official rules apply to this program:</strong></p>


								<ul style="margin: 10px 80px;">
									<li>Service Orders must be created and completed between November 13, 2008, and February 28, 2009. </li>
									<li>The posting and service order fee waiver applies only to consumer (Home/Office) service orders. </li>
									<li>Fee waiver only applies to the first five service orders to you from a particular consumer buyer. </li>
									<li>All ratings must be objective &#8212; a provider may not pay a buyer in order to receive a rating or influence the buyer's rating. </li>
									<li>Routing orders to your own company is not permitted. </li>
									<li>Remember, there is a minimum service order amount of $25 for all service orders.</li>
								</ul>
							
							<p>For more information &#8212; (888) 549-0640 or support@ServiceLive.com </p>							
							
						<p><small>* Payment Platform powered by Integrated Payment Systems, Licensed as a Money Transmitter by the Banking Deparment of the State of New York</small></p>
						<p><small>** Service Orders from Enterprise or Commercial Buyers are not included in the offer.</small></p>
						
						<p><a href="javascript:history.go(-1)">&laquo; Back</a></p>
						
					</div>

					</div>
				</div>
				<jsp:include page="/jsp/public/common/blackoutFooter.jsp" />

			</div>

		</div>
	</div>

</html>
