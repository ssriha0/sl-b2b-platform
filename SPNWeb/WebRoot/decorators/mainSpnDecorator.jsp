<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<!DOCTYPE html>
<html>
	<head>
		<title><decorator:title default="Welcome" /></title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Expires" content="-1">
		<meta http-equiv="Cache-Control" content="no-store">
		<meta rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/visualize.css"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/visualize-dark.css"/>	
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/select-provider-network.css" media="screen, projection">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/chosen.css"/>
	
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/screen.css" media="screen, projection">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/superfish.css" media="screen, projection">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/modals.css" media="screen, projection">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/select-provider-network.css" media="screen, projection" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/jqueryui/jquery-ui-1.7.2.custom.css" />
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.sifr.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.hoverIntent.minified.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.bgiframe.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.multiselect.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/ui.datepicker.js"></script>
	 	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.maxlength-min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery-ajaxfileupload.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.wysiwyg.js"></script> 
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqueryui/jquery-ui-1.10.4.custom.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.maxlength-min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/superfish.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/supersubs.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/select_provider_network.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/visualize.jQuery.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/excanvas.js"></script>	
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.dataTables.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/chosen.jquery.min.js"></script>
					

		<decorator:head />
	</head>

	<body id="select-provider-network" class="tundra">
				<div id="wrap" class="container">
				<jsp:include page="/jsp/spn/common/defaultheadernew.jsp" />
				<div id="content" class="span-24 clearfix">	
				<decorator:body />
				</div>
				<jsp:include page="/jsp/spn/common/defaultfootertmp.jsp" />
				</div>
	</body>
</html>