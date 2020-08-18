<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />



<div id="primary" class="span-24 first last">
	<div class="content">
		<table width="100%" border=1>
			<tr>
				<td width="60%">
					<a href="${contextPath}" title="ServiceLive">
						<img
							src="${staticContextPath}/images/artwork/common/logo.png"
							alt="ServiceLive" />
					</a>
				</td>
				<td width="40%">
					<c:if test="${not empty USER_OBJECT_IN_SESSION.username}">
						Welcome <c:out value="${USER_OBJECT_IN_SESSION.username}"></c:out>! #<c:out value="${USER_OBJECT_IN_SESSION.userId}"></c:out>
					</c:if>
					<br/>
					<%-- 
					<a href="http://community.servicelive.com"
						onclick="window.open(this.href); return false;"
						onkeypress="window.open(this.href); return false;">Community</a> 	|
						<a href="http://blog.servicelive.com" title="Blog">Blog</a> | 
					<a href="${contextPath}/jsp/public/support/support_main.jsp">Support</a> 	|
					<a href="${contextPath}/contactUsAction.action">Contact Us</a> 		|
					--%>
					<a href="${contextPath}/spnLoginAction_logoutUser.action">Logout</a>					
				</td>
			</tr>
		</table>
	</div>	
</div>