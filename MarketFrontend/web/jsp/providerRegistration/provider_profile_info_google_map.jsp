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
		<div id="hpSidebar">
			<div id="providerMap" class="widget clearfix">
				<h2>
					Coverage Area
				</h2>
				<!-- google maps this -->
				<div id="drawmaps"
					style="width: 269px; height: 217px; margin-bottom: 5px; border: 1px solid silver;">
					<iframe width="269px" scrolling="no" height="217px" marginwidth="0"
						marginheight="0" frameborder="0"
						src="${contextPath}/jsp/providerRegistration/google_maps.jsp?key=${ProviderInfo.googleMapKey}&latitude=${ProviderInfo.zipData.latitude}&longitude=${ProviderInfo.zipData.longitude}&range=${ProviderInfo.providerPublicInfo.dispAddGeographicRange}"
						style="float: left;">
					</iframe>
				</div>
				<div class="zipCov">
					${ProviderInfo.providerPublicInfo.dispAddCity},
					${ProviderInfo.providerPublicInfo.dispAddState}
					${ProviderInfo.providerPublicInfo.dispAddZip}
					<span>Within
						${ProviderInfo.providerPublicInfo.dispAddGeographicRange} Miles</span>
				</div>
			</div>

			<div id="hpTip" class="widget tips feedback">
				<div class="i31 provActivity">

					<h2>
						Recent Activity
					</h2>
					<dl>
						<dt>
							Service Orders (Total)
						</dt>
						<dd>
							<c:choose>
								<c:when
									test="${ProviderInfo.providerPublicInfo.generalInfoVO.totalSoCompleted >= 0}">
											${ProviderInfo.providerPublicInfo.generalInfoVO.totalSoCompleted }
										</c:when>
								<c:otherwise>
									 		0
								 		</c:otherwise>
							</c:choose>
						</dd>
					</dl>
				</div>

				<c:choose>
					<c:when
						test="${not empty ProviderInfo.providerPublicInfo.feedbacks}">
						<div class="i30 provFeedback">
							<h2>
								Customer Feedback
							</h2>
							<c:set var="numElements" value="${ProviderInfo.numFeedback}" />
							<c:set var="loopvar" value="${ProviderInfo.maxSWFeedbackShown}" />
							<c:if test="${numElements < loopvar}">
								<c:set var="loopvar" value="${numElements}" />
							</c:if>
							<c:forEach var="i" begin="0" end="${loopvar - 1}">
								<c:set var="feedbacks"
									value="${ProviderInfo.providerPublicInfo.feedbacks[i]}" />
								<strong> ${feedbacks.ratedByFirstName}
									${feedbacks.ratedByLastName}, ${feedbacks.ratedByCity},
									${feedbacks.ratedByState} <c:if
										test="${feedbacks.overallRatingScoreImageId > 0 }">
										<img alt="rating image"
											src="${staticContextPath}/images/common/stars_${feedbacks.overallRatingScoreImageId}.gif" />
									</c:if> <c:if test="${feedbacks.overallRatingScoreImageId == -1 }">
										<img alt="rating image"
											src="${staticContextPath}/images/common/stars_notRated.gif" />
									</c:if> </strong>
								<p class="cusFeedback">
									<em> <c:set var="numChars" value="100" /> <c:set
											var="comments" value="${feedbacks.comments }" /> <c:set
											var="strLength" value="${fn:length(comments)}" /> <c:if
											test="${strLength <= numChars}">
											${comments}
										</c:if> <c:if test="${strLength > numChars}">
											<c:set var="initAfterStr"
												value="${fn:substring(comments, numChars, -1) }" />
											<c:set var="firstWhite"
												value="${fn:indexOf(initAfterStr,' ')}" />

											<c:if test="${firstWhite >= 0}">
												<c:set var="beforeString"
													value="${fn:substring(comments, 0, numChars+firstWhite)}" />
												<c:set var="afterString"
													value="${fn:substring(comments, numChars+firstWhite, -1)}" />
												${beforeString }<span id="read_more_${i}">... <a
													href="#" onClick="show_more_comments(${i});">Read More</a>
												</span>
												<span id="after_string_${i}" style="display: none;">${afterString
													}... <a href="#" onClick="show_less_comments(${i});">Read
														Less</a> </span>
											</c:if>
											<c:if test="${firstWhite < 0}">
												${comments }
											</c:if>
										</c:if> </em>
								</p>
							</c:forEach>
						</div>
					</c:when>
				</c:choose>

			</div>
		</div>
	</body>
</html>
