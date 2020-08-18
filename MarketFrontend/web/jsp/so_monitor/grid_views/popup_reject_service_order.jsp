<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />


<form action="serviceOrderReject.action" method="post" id="rejectForm" target="_top">
	<input type="hidden" name="soId" value="" />
	<input type="hidden" name="resId" value="" />	
	<input type="hidden" name="reasonText" value="" />	
	<input type="hidden" name="requestFrom" id="requestFrom" value="SOD" />
	<input type="hidden" name="reasonId" id="reasonId" />
	<input type="hidden" name="groupId" value="" />	
</form>