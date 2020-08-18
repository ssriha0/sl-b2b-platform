<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<a href="http://community.servicelive.com">Community</a> | <a href="${contextPath}/jsp/public/support/support_faq.jsp">Support</a> | <a href="contactUsAction.action">Contact Us</a> 	<c:if test="${empty SERVICE_ORDER_CRITERIA_KEY}"> | <a href="joinNowAction.action" id="joinNowLink">Join Now</a> </c:if>
