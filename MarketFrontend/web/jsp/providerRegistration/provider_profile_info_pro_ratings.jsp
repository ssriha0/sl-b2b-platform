<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", -1);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
     <div id="provider_ratings" class="widget ratingsbox">
		<h2>
			Detailed Provider Ratings
		</h2>

		<table border="0">
				<c:forEach items="${ProviderInfo.providerPublicInfo.surveyratings}" var="rnode">
					<tr><td><c:out value="${rnode.question}"></c:out></td>
		    			<td><c:if test="${rnode.starImageId > 0 }">
			     				<img alt="rating image" src="${staticContextPath}/images/common/stars_${rnode.starImageId}.gif" />
							</c:if>
				            <c:if test="${rnode.starImageId == 0 }">
								<img alt="rating image" src="${staticContextPath}/images/common/stars_notRated.gif" />
							</c:if>
						</td>
						<td>${rnode.surveyCount } Ratings</td>
					</tr>
				</c:forEach>
		</table>
	 </div>
  </body>
</html>
