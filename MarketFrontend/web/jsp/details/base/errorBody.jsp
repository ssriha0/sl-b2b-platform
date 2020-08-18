<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css">
<input type="hidden" id="errorText" name="errorText" value="${errorText}"/> 
<c:if test="${not empty errorText}">
		<div class="errorBox clearfix">
			<p class="errorMsg">	${errorText}</p>
		</div>
	</c:if>