<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<jsp:include page="../../layout/common/commonIncludes.jsp" />

<div dojoType="ContentPane" layoutAlign="top"
	style="background-color: #FFFFFF;">
	<img src="${staticContextPath}/images/artwork/common/logo.png" />
</div>