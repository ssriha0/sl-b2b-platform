<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<input type="hidden" id="vendorId" name="vendorId" value="${vendorId}" />
<input type="hidden" id="vendorName" name="vendorName" value="${vendorName}" />
<input type="hidden" id="firstName" name="firstName" value="${firstName}" />
<input type="hidden" id="lastName" name="lastName" value="${lastName}" />
<input type="hidden" id="sharedSecret" name="sharedSecret" value="${sharedSecret}" />


<div id="divNetworkInformationTab"></div>
