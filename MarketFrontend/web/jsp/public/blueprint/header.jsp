<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<style type="text/css">
   div.contain{
   font-size: 10px;
}
</style>
<script type="text/javascript" src="${staticContextPath}/javascript/banner.js"></script>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/banner.css" />
<!--<jsp:include page="/jsp/public/blueprint/browserCompatibilityBanner.jsp"></jsp:include> -->
<div id="header" class="span-24 clearfix">
	<div id="title" class="span-6 first">
		<h1><a href="${contextPath}" title="ServiceLive">ServiceLive</a></h1>
	</div>	
	<div id="utilities" class="span-18 last">
		<c:if test="${SecurityContext == null}">
			<div id="tollfreenumber">1-888-549-0640</div>
		</c:if>
		<div class="right contain">
			<c:if test="${SecurityContext != null}">
				Welcome, ${SERVICE_ORDER_CRITERIA_KEY.FName} 
				${sessionScope.SERVICE_ORDER_CRITERIA_KEY.LName}! 
				<em>#${SERVICE_ORDER_CRITERIA_KEY.vendBuyerResId}</em>
				<c:choose>
					<c:when test="${lifetimeRatingStarsNumber == null}"></c:when>
					<c:when test="${lifetimeRatingStarsNumber == 0}">Not Rated</c:when>
					<c:otherwise>
						<img
							src="${staticContextPath}/images/common/stars_<c:out value="${lifetimeRatingStarsNumber}"/>.gif"
							alt="" border="0" />
					</c:otherwise>
				</c:choose>
			<br/>
			</c:if>	
					
			<c:if test="${SecurityContext != null}">
				<a title="Training" href="http://training.servicelive.com" target="_blank">Training</a> |
			</c:if>
				<a title="Community Forums" href="http://community.servicelive.com" target="_blank">Community</a> |
				<a href="http://blog.servicelive.com" title="Blog" target="_blank">Blog</a> |
				<a title="Support" href="${contextPath}/jsp/public/support/support_faq.jsp" target="_blank">Support</a> |
				<a title="Contact ServiceLive" href="${contextPath}/contactUsAction.action" target="_blank">Contact Us</a> 
				<c:if test="${SecurityContext != null && SERVICE_ORDER_CRITERIA_KEY.roleId == 2}">
					| <a href="${contextPath}/adminSearch_execute.action">Search Portal</a>
				</c:if>
				<c:if test="${SecurityContext != null}">
					| <a title="Logout of your Account" href="${contextPath}/doLogout.action">Logout</a>
				</c:if>
			</div>
		</div>	
</div>