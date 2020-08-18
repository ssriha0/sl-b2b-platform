<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="ProviderRegistration.w9MainPage"/>
</jsp:include>
<c:if test="${w9Modal != true}">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<title>ServiceLive - w9 Form</title>
		<style type="text/css">
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
		</style>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/banner.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/banner.css" />
		<script type="text/javascript">
		   function fixMarginTop(){
			   if($('#bannerDiv').css('display') == 'block'){
				  $('#page_margins').css('margin-top','15px');
			      }
		   }
		</script>
   </head>
	
	<c:if test="${w9HistoryFlag == true }">
		<body>
		<!-- <body onload="displayBanner();">
		  <div id="bannerDiv" class="bannerDiv" style="display:none;width:100%;position:relative;margin-left:0px;">
	        <span class="spanText" id="spanText"></span>
	        <a href="javascript:void(0);" onclick="removeBanner();" style="text-decoration: underline;"> Dismiss </a>
         </div> -->
	</c:if>
	<c:if test="${w9HistoryFlag != true }">
		<body class="tundra acquity" onload="fixMarginTop();">
		<!--  <body class="tundra acquity" onload="displayBanner();fixMarginTop();">
		   <div id="bannerDiv" class="bannerDiv" style="display:none;width:100%;position:relative;margin-left:0px;">
	         <span class="spanText" id="spanText"></span>
	         <a href="javascript:void(0);" onclick="removeBanner();" style="text-decoration: underline;"> Dismiss </a>
           </div> -->
		<div id="page_margins">
		<div id="page">
	</c:if>
	
	<tiles:insertAttribute name="header" />
	
		<c:if test="${w9HistoryFlag == true }">
			<jsp:include page="w9RegistrationFormHistory.jsp" />
		</c:if>
		<c:if test="${w9HistoryFlag != true }">
			<jsp:include page="w9RegistrationJS.jsp" />
			<jsp:include page="w9RegistrationFormBody.jsp" />
		</c:if>

	<tiles:insertAttribute name="footer" />
	
	<c:if test="${w9HistoryFlag == true }">
		</body>
	</c:if>
	<c:if test="${w9HistoryFlag != true }">
		</div>
		</div>
		</body>
	</c:if>
</html>
</c:if>
<c:if test="${w9Modal == true}">
	<c:if test="${w9isExist == true }">
		<script language="JavaScript" type="text/javascript">
			document.getElementById('w9isExist').value = true;	
		</script>
	</c:if>
	<c:if test="${w9isExist != true }">
		<script language="JavaScript" type="text/javascript">
			document.getElementById('w9isExist').value = false;	
		</script>
	</c:if>
	<jsp:include page="w9RegistrationJS.jsp" />
	<jsp:include page="w9RegistrationFormBody.jsp" />
</c:if>