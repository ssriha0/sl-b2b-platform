<jsp:include page="/jsp/public/common/commonIncludes.jsp" />
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="noJs" value="true" scope="request" />
<c:set var="noCss" value="true" scope="request" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>Join ServiceLive</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/secondary-980.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/secondary-steps.css">
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
	</head>
	<body class="tundra acquity">
		<div id="page_margins">
			<div id="page" class="clearfix">
				<div id="header">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
				</div>
				<jsp:include page="/jsp/public/registration/join_now_provider_body.jsp" />
				<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			</div>
		</div>
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="JoinNow.provider.landing"/>
		</jsp:include>
<%-- remarketing tags --%>
<script src="http://ad.advertise.com/pixel?id=730895&t=1" type="text/javascript"></script><%-- Las Vegas, Metro-NV--Handyman Services --%>
<script src="http://ad.advertise.com/pixel?id=730908&t=1" type="text/javascript"></script><%-- Newark-Jersey, City, NJ--Handyman Services --%>
<script src="http://ad.advertise.com/pixel?id=730916&t=1" type="text/javascript"></script><%-- Portland, Vancouver, WA--Handyman Service --%>
<script src="http://ad.advertise.com/pixel?id=730903&t=1" type="text/javascript"></script><%-- Spokane, WA--Home Appliance --%>
<script src="http://ad.advertise.com/pixel?id=730923&t=1" type="text/javascript"></script><%-- Yuma, AZ--Home Appliance --%>
	</body>
</html>
