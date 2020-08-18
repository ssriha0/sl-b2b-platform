<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />

<body>
	<div id="overLay" class="overLay"
		style="display: none; z-index: 1000; width: 100%; height: 100%; position: fixed; opacity: 0.2; filter: alpha(opacity =   20); 
		top: 0px; left: 0px; background-color: #E8E8E8; cursor: wait;"></div>
	<div id="waitPopUp" class="waitLayer">
		<div style="padding-left: 0px; padding-top: 5px; color: #222222;">
			<div class="refreshMsg">
				<span>Loading...</span>
			</div>
		</div>
	</div>
</body>