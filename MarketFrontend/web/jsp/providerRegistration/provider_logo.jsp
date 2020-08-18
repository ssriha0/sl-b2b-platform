<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<c:if test="${not empty link_back}">
	<a href="${link_back}">
</c:if>
<c:choose>
<c:when test="${firmLogo.docDetails.blobBytes != null && max_height < 100}">
	<table width=100% height="100%" cellspacing=0 cellpadding=0><tr><td valign=center align=center>
	
	
	
	<img border=1 class="left" 
		src="${contextPath}/firmLogoAction_displayPhoto.action?max_width=${max_width}&max_height=${max_height}&vendorId=${vendorId}" 
		alt="" />
	</td></tr></table>
</c:when>
<c:when test="${firmLogo.docDetails.blobBytes != null}">
	<table width=100% height="100%" cellspacing=0 cellpadding=0><tr><td style="text-align: center; vertical-align: center;">
	
	<img border=1 class="left" 
		src="${contextPath}/firmLogoAction_displayPhoto.action?max_width=${max_width}&max_height=${max_height}&vendorId=${vendorId}"
		alt="" />
	</td></tr></table>
</c:when>
<c:when test="${max_height < 100}">
	<table width=100% height="100%" cellspacing=0 cellpadding=0><tr><td style="text-align: center; vertical-align: center;">
	<img height="80" width="80" class="left" 
		src="https://provider.servicelive.com/sl_image/logo/no_logo.gif" 
		alt="" />
	</td></tr></table>
</c:when>
<c:otherwise>
	<table width=100% height="100%" cellspacing=0 cellpadding=0><tr><td style="text-align: center; vertical-align: center;">
	<img height="80" width="80" class="left" src="https://provider.servicelive.com/sl_image/logo/no_logo.gif" alt="" />
	</td></tr></table>
</c:otherwise>
</c:choose>
<c:if test="${not empty link_back}">
	</a>
</c:if>
