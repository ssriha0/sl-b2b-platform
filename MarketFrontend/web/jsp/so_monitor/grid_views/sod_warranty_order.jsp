<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css" />
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css" />
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/advanced_grid.css" />
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/global.css" />
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/sears_custom.css"/>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
            <link href="${staticContextPath}/javascript/style.css" rel="stylesheet" type="text/css" />
            <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/so_details.css"> 

</head>
<body class="tundra noBg">
     <div id="sodRestricted" class="contentPane" style="margin-left: 10px;border-style: double;">
        <jsp:include page="/jsp/details/body/html_sections/modules/panel_general_information_warranty.jsp" />
        <jsp:include page="/jsp/details/body/html_sections/modules/panel_scope_of_work_warranty.jsp" />
        <jsp:include page="/jsp/details/body/html_sections/modules/panel_invoice_parts_hsr_summary_view.jsp" />
     </div>
</body>