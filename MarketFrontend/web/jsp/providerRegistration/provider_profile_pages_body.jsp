<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="ProviderRegistration.companyProfile"/>
	</jsp:include>

<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", -1);
%>
<c:choose>
<c:when test="${ProviderInfo.hasErrors}">
    <div style="height:1px;">
	<h3>&nbsp;&nbsp;&nbsp;We apologize that the system cannot show the information requested at this time.</h3>
	</div>
	<div id="hpWrap" class="shaded clearfix">
		<div id="hpContent" class="pdtop20">
			<div id="hpIntro" class="clearfix">
					<div class="errorBox">
						<c:forEach items="${ProviderInfo.companyPublicInfo.errorList}" var="row">
							<table border="0">
								<tr>
									<td><c:out value="${row}"/>
									</td>
								</tr>
							</table>
						</c:forEach>
					</div>
					<br>
			<h4>Please call ServiceLive Support at 888-549-0640 or email <a href='mailto:support@servicelive.com'>support@servicelive.com</a> with the above detail.</h4>
		 </div>
		</div>
	</div>
</c:when>
<c:otherwise>

				<div id="hpWrap" class="shaded clearfix">

					<div id="hpContent" class="pdtop20">

						<div id="hpIntro" class="clearfix">
							<div class="profilebox">
	              				<jsp:include page="/jsp/providerRegistration/provider_profile_info_photo.jsp" />
	              	            <jsp:include page="/jsp/providerRegistration/provider_profile_info_title.jsp" />
							</div>
							<%-- <jsp:include page="/jsp/providerRegistration/provider_profile_info_company_ratings.jsp" />
	                       	<jsp:include page="/jsp/providerRegistration/provider_profile_info_pro_ratings.jsp" /> --%>
	                  	</div>

						<div id="providerTabs">
							<c:if test="${ProviderInfo.isExternal == true}">
								<a class="sendmeSO" href="#top"><img alt="Send Me a Service Order" class="right" src="${staticContextPath}/images/btn/sendMeAServiceOrder.png" /></a>
							</c:if>
							<ul class="clearfix botbor botmg10">
								<li>
									<a href="#fragment-1" onClick="this.blur();"><span>Provider Profile</span>
									</a>
								</li>
								<li>
									<a href="#fragment-2" onClick="this.blur();"><span>Business	Profile</span>
									</a>
								</li>
								<li>
									<a href="#fragment-3" onClick="this.blur();"><span>Customer Feedback</span> </a>
								</li>
								<c:if test="${ProviderInfo.isSecuredInfoViewable != null and ProviderInfo.isSecuredInfoViewable == true }">
									<tags:security actionName="ViewMemberManager">
										<li>
											<a href="#fragment-spn" onClick="">
												<span>Network Information</span>
											</a>
										</li>
									</tags:security>
								</c:if>
							</ul>

                            <jsp:include page="/jsp/providerRegistration/provider_profile_info_tab.jsp" />
       	                    <jsp:include page="/jsp/providerRegistration/provider_profile_info_business_profile.jsp" />
		                    <jsp:include page="/jsp/providerRegistration/provider_profile_info_feedback.jsp" />
		                  	<c:if test="${ProviderInfo.isSecuredInfoViewable != null and ProviderInfo.isSecuredInfoViewable == true }">
			              		<tags:security actionName="ViewMemberManager">
					         		<jsp:include page="/jsp/providerRegistration/provider_profile_info_network_info.jsp" />
					         	</tags:security>
					         </c:if>
						</div>

						<c:if test="${ProviderInfo.isExternal == true}">
							<a class="sendmeSO" href="#top"><img alt="Send Me a Service Order" class="right" src="${staticContextPath}/images/btn/sendMeAServiceOrder.png" /></a>
						</c:if>
					</div>
					 <jsp:include page="/jsp/providerRegistration/provider_profile_info_google_map.jsp" />
				</div>
</c:otherwise>
</c:choose>