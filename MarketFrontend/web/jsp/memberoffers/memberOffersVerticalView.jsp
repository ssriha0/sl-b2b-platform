<%@ page language="java" import="java.util.*, com.servicelive.routingrulesengine.RoutingRulesConstants" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>




<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<html>
	<body>
		<table width="100%">
			<c:forEach items="${offerList}" var="offer" varStatus="rowcounter">				
				<tr >
				
							<td width="30%">
								<div>
									<a onclick="viewOfferDetails(${offer.offerId});" style="cursor:pointer;">
										<img border="1" src="${contextPath}/memberOffers_retrieveOfferImage.action?max_width=125&max_height=125&imageURL=<c:out value="${offer.offerImagePath}"/>"/>
									</a>
								</div>								
								<div style="margin-top: 15px;">
								<c:choose>
						                <c:when test="${offer.clickCount==1}">
							               <b>${offer.clickCount} view</b>
							            </c:when>
							            <c:otherwise>
							               <b>${offer.clickCount} views</b>
							            </c:otherwise>
							    </c:choose>	
								</div>								
							</td>
							<td style="vertical-align: top;" width="58%">
							<div>
								<h3><a onclick="viewOfferDetails(${offer.offerId});" style="cursor:pointer;">${offer.companyName}</a></h3>	
							</div>
							<div align="justify">
										${offer.description}
							</div>								
							</td>
							<td width="12%" style="vertical-align:bottom;">
							<a onclick="viewOfferDetails(${offer.offerId});" style="cursor:pointer;">More >></a>
							</td>
						
				</tr>
				<tr>
					<td colspan="3">						
						<hr style="background-color: #E2E5E7;" />
					</td>
				</tr>
			</c:forEach>
		</table>
	</body>
</html>