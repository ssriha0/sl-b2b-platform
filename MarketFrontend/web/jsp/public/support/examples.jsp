<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<jsp:include page="/jsp/public/common/commonIncludes.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageTitle" scope="request" value="Example Uses" />
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive - <c:out value="${pageTitle}"/></title>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/support.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
		<!--[if lte IE 7]>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
		<![endif]-->

			
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>
		<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/nav.js"></script>
		
	</head>

	<body class="tundra acquity" onload="${onloadFunction}" >

	<div id="page_margins">
		<div id="page" >
		
			<%-- No header/logo/login/navbars for this page--%>

			<h2 style="margin-top: 30px;">
				${pageTitle}
			</h2>
			
			<p style="background: #F4F4F4; padding: 20px; margin: 10px 0; color: #666; font-size: 12px;"><span style="display: block; color: black; margin-bottom: 5px;">Working on a new project or just need routine maintenance?</span>There are thousands of ways ServiceLive can help you get things done.  Creating a Service order is fast and simple.  Here are just a few examples of Service orders placed by other homeowners and professionals, just like you.</p>
			<div class="clearfix">
				
				<div class="exampleSO left" style="width: 300px; text-align: center; margin: 10px 10px 30px 10px">
					<a href="${staticContextPath}/images/example_so/sample-1a.jpg" rel="sample1"><img style="width: 200px; height: 250px; border: 5px solid silver; padding: 5px;" alt="" src="${staticContextPath}/images/example_so/thumbnail-1.jpg" width="200" height="250" /></a>
					<h3 style="color: #7dbf10;">Garage Door Install	</h3>
					<a href="${staticContextPath}/images/example_so/sample-1b.jpg" rel="sample1"></a>
					<a href="${staticContextPath}/images/example_so/sample-1c.jpg" rel="sample1"></a>
				</div>
							
							
				<div class="exampleSO left" style="width: 300px; text-align: center; margin: 10px 10px 30px 10px">
					<a href="${staticContextPath}/images/example_so/sample-2a.jpg" rel="sample2"><img style="width: 200px; height: 250px; border: 5px solid silver; padding: 5px;" alt="" src="${staticContextPath}/images/example_so/thumbnail-2.jpg" width="200" height="250" /></a>
					<h3 style="color: #7dbf10;">Repair My Faucet	</h3>
					<a href="${staticContextPath}/images/example_so/sample-2b.jpg" rel="sample2"></a>
					<a href="${staticContextPath}/images/example_so/sample-2c.jpg" rel="sample2"></a>
				</div>

				<div class="exampleSO left" style="width: 300px; text-align: center; margin: 10px 10px 30px 10px">
					<a href="${staticContextPath}/images/example_so/sample-3a.jpg" rel="sample3"><img style="width: 200px; height: 250px; border: 5px solid silver; padding: 5px;" alt="" src="${staticContextPath}/images/example_so/thumbnail-3.jpg" width="200" height="250" /></a>
					<a href="${staticContextPath}/images/example_so/sample-3a.jpg" rel="sample3"><h3 style="color: #7dbf10;">Replace Ceiling Fan	</h3></a>
					<a href="${staticContextPath}/images/example_so/sample-3b.jpg" rel="sample3"></a>
					<a href="${staticContextPath}/images/example_so/sample-3c.jpg" rel="sample3"></a>
				</div>


				<div class="exampleSO left" style="width: 300px; text-align: center; margin: 10px 10px 30px 10px">
					<a href="${staticContextPath}/images/example_so/sample-4a.jpg" rel="sample4"><img style="width: 200px; height: 250px; border: 5px solid silver; padding: 5px;" alt="" src="${staticContextPath}/images/example_so/thumbnail-4.jpg" width="200" height="250" /></a>
					<h3 style="color: #7dbf10;">Wireless Network Install	</h3>
					<a href="${staticContextPath}/images/example_so/sample-4b.jpg" rel="sample4"></a>
					<a href="${staticContextPath}/images/example_so/sample-4c.jpg" rel="sample4"></a>
				</div>
				

				<div class="exampleSO left" style="width: 300px; text-align: center; margin: 10px 10px 30px 10px">
					<a href="${staticContextPath}/images/example_so/sample-5a.jpg" rel="sample5"><img style="width: 200px; height: 250px; border: 5px solid silver; padding: 5px;" alt="" src="${staticContextPath}/images/example_so/thumbnail-5.jpg" width="200" height="250" /></a>
					<h3 style="color: #7dbf10;">Drywall Repair	</h3>
					<a href="${staticContextPath}/images/example_so/sample-5b.jpg" rel="sample5"></a>
					<a href="${staticContextPath}/images/example_so/sample-5c.jpg" rel="sample5"></a>
				</div>

				<div class="exampleSO left" style="width: 300px; text-align: center; margin: 10px 10px 30px 10px">
					<a href="${staticContextPath}/images/example_so/sample-6a.jpg" rel="sample6"><img style="width: 200px; height: 250px; border: 5px solid silver; padding: 5px;" alt="" src="${staticContextPath}/images/example_so/thumbnail-6.jpg" width="200" height="250" /></a>
					<h3 style="color: #7dbf10;">Flat Panel TV Install	</h3>
					<a href="${staticContextPath}/images/example_so/sample-6b.jpg" rel="sample6"></a>
					<a href="${staticContextPath}/images/example_so/sample-6c.jpg" rel="sample6"></a>
				</div>				
				
			</div>		
		
		 <c:if test="${!IS_LOGGED_IN}">	
			<p style="text-align: center; font-weight: bold;font-size: 14px; border: 1px solid silver; padding: 10px;"><a href="${contextPath}/csoCreateAccount_displayPage.action?fromHome=y">Signup for a free ServiceLive account</a></p>
		</c:if>
			
			
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
		</div>
	</div>
	</body>
</html>
