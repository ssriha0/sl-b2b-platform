<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<div class="rightRailWidget">
	<div class="darkGrayModuleHdr">
		Member Information
	</div>
	<div class="grayModuleContent">
		<p>
			<strong>Provider:</strong> ${provider}
			<br />
			<strong>Provider ID#:</strong> ${providerId}
			<br />			
			<strong>Administrator:</strong> ${administrator}
			<br />
			<%-- 
			<strong>Account Balance:</strong> $30,122.00
			<br />
			<strong>Open Service Orders:</strong> 12
			<br />
			<strong>Close Service Orders:</strong> 3,342
			<br />
			<strong>Location:</strong> Plano, TX 75006
			<br />
			<strong>Ratings:</strong>
			<img border="0" src="${staticContextPath}/images/common/full_star_gbg.gif" />
			<img border="0" src="${staticContextPath}/images/common/full_star_gbg.gif" />
			<img border="0" src="${staticContextPath}/images/common/full_star_gbg.gif" />
			<img border="0" src="${staticContextPath}/images/common/full_star_gbg.gif" />
			<img border="0" src="${staticContextPath}/images/common/full_star_gbg.gif" />
			--%>
		</p>
