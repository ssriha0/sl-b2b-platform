<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/screen.css" media="screen, projection">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/superfish.css" media="screen, projection">
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.flash.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.sifr.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/scripts/plugins/superfish.js"></script>
<script type="text/javascript" src="${staticContextPath}/scripts/plugins/supersubs.js"></script>
<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.hoverIntent.minified.js"></script>
<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.bgiframe.js"></script>
<script type="text/javascript" src="${staticContextPath}/scripts/toolbox.js"></script>
