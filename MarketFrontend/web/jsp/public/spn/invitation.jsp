<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive - Select Provider Network - Manage Networks</title>
		<tiles:insertDefinition name="blueprint.base.meta"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/select-provider-network.css" media="screen, projection">

		<script type="text/javascript">
			$(document).ready(function() {
			});

		</script>
	</head>
	<body id="select-provider-network">
		<div id="wrap" class="container">

			<tiles:insertDefinition name="blueprint.base.header"/>
			<tiles:insertDefinition name="blueprint.base.navigation"/>
			
			<div id="content" class="span-24 clearfix">
		
				<div id="primary" class="span-24 first last">
					<div class="content">
						<h2 id="page-role">Administrator</h2>
						<h2 id="page-title">Select Provider Network (SPN)</h2>
						
						<h3 class=""><abbr title="Select Provider Network">SPN</abbr> Invitation</h3>
						
					</div>
				</div>
			</div>

			<tiles:insertDefinition name="blueprint.base.footer"/>

		</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="spn:network monitor"/>
	</jsp:include>
	</body>
</html>
			
			
