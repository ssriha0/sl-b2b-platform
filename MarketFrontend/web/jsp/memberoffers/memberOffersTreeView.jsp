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


	<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<c:forEach items="${offerList}" var="offer" varStatus="rowcounter">
				<c:if test="${rowcounter.count % 3==1}">
		</tr>
		<tr>
				</c:if>				
			<td width ="33%"  style="padding-bottom:15px;">								
				<div style="border: 1px solid #E2E5E7;padding: 8px;">					
					<div  style="width:95%;margin-left: 25px;margin-top: 10px;">
						<a onclick="viewOfferDetails(${offer.offerId});" style="cursor:pointer;">
							<img style="border: thin #ff0000;" border="1" src="${contextPath}/memberOffers_retrieveOfferImage.action?max_width=125&max_height=125&imageURL=<c:out value="${offer.offerImagePath}"/>"/>
						</a>
					</div>	
											
					<div  style="width:95%;word-wrap:break-word;margin-top: 10px;font-size: 14px;">
						<a onclick="viewOfferDetails(${offer.offerId});" style="cursor:pointer;width:70%;">${offer.companyName}</a>
					</div>
					<div  style="width:95%;word-wrap:break-word;margin-top: 3px;height: 60px;" align="justify">					
							${offer.description}
					</div>
																					
					<div  style="width:95%;word-wrap:break-word;margin-top: 10px;margin-bottom: 15px;">
						<div style="float: left;width: 100px;">
						    <c:choose>
						    <c:when test="${offer.clickCount==1}">
							<b>${offer.clickCount} view</b>
							</c:when>
							<c:otherwise>
							<b>${offer.clickCount} views</b>
							</c:otherwise>
							</c:choose>
						</div>						
						<!-- &nbsp; &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
						<div style="float: right">
							<a onclick="viewOfferDetails(${offer.offerId});" style="cursor:pointer;">More>></a>
						</div>						
					</div>								
					<div>
						&nbsp; &nbsp;
					</div>
				</div>
			</td>
							
			</c:forEach>
			<c:set var="endIndex" value="${fn:length(offerList)%3}"></c:set>
			<c:forEach begin="1" end="${endIndex+1}" step="1">
			<td width ="33%"  style="padding-bottom:5px;"></td>
			</c:forEach>
		</tr>	
	</table>