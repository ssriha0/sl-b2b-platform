<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/hideShow.js"></script>

<style type="text/css">
@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
</style>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/slider.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/service_order_wizard.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
<script type="text/javascript" src="${staticContextPath}/javascript/tooltip.js" ></script>
<script type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
<script language="JavaScript" type="text/javascript">
	function setHiddenFields(name, mainCategoryId, categoryId, subCategoryId, serviceTypeTemplateId, buyerTypeId)
	{
		document.getElementById('mod_pop_name').value=name;
		document.getElementById('mod_mainCategoryId').value=mainCategoryId;
		document.getElementById('mod_categoryId').value=categoryId;
		document.getElementById('mod_subCategoryId').value=subCategoryId;
		document.getElementById('mod_serviceTypeTemplateId').value=serviceTypeTemplateId;
		document.getElementById('mod_buyerId').value=buyerTypeId;

		jQuery.noConflict();
	    jQuery(document).ready(function($) {
     		$('#dialog').jqm({modal:true, toTop: true});
     		$('#dialog').jqmShow();
		 });

	}
</script>
</head>

<c:choose>
	<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
		<body class="tundra acquity simple" onload="${onloadFunction}">
	</c:when>
	<c:otherwise>
		<body class="tundra acquity" onload="${onloadFunction}">
	</c:otherwise>
</c:choose>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="What is ServiceLive"/>
	</jsp:include>	
	<div id="page_margins">
		<div id="page" class="clearfix">
			<tiles:insertAttribute name="body"/>
			<tiles:insertAttribute name="footer"/> 		 
		</div>
	</div>			
</body>

</html>
