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
		<div id="fragment-3" class="clearfix">
				<c:choose>
					<c:when
						test="${not empty ProviderInfo.providerPublicInfo.feedbacks}">
						<c:forEach items="${ProviderInfo.providerPublicInfo.feedbacks}"
							var="feedbacks">
							<div class="hpDescribe clearfix">
								<div class="quotes">
									<h3 style="margin: 0px;">
										${feedbacks.ratedByFirstName} ${feedbacks.ratedByLastName}, ${feedbacks.ratedByCity}, ${feedbacks.ratedByState}
										<span><fmt:formatDate value="${feedbacks.modifiedDate}"
												pattern="MMMM dd, yyyy" />
										</span>
									</h3>

									<p>
										${feedbacks.comments}
									</p>
									<div style="margin-left: 50px;">

										<h4>
											Job: ${feedbacks.feedbackPrimaryCategory}<br/>
											Service Order ID: ${feedbacks.serviceOrderId}
										</h4>
										<c:if test="${feedbacks.overallRatingScoreImageId > 0 }">
											<img alt="rating image" src="${staticContextPath}/images/common/stars_${feedbacks.overallRatingScoreImageId}.gif" />
										</c:if>
										<c:if test="${feedbacks.overallRatingScoreImageId == -1 }">
											<img alt="rating image" src="${staticContextPath}/images/common/stars_notRated.gif" />
										</c:if>
									</div>
								</div>

							</div>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<p>
							There is no customer feedback for this provider.
						</p>
					</c:otherwise>
				</c:choose>
			</div>    
  </body>
</html>
