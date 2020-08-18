<%@ page language="java" import="java.util.*, com.servicelive.routingrulesengine.RoutingRulesConstants" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
			<div id="layer1_content_body" class="scroll">

				<table>
<c:forEach var="criteria" items="${criteriaHistory}">
<tr>
	<td style="width:70px;padding: 2px;" valign="top" >$${criteria.autoCloseRuleCriteriaValue}</td>
	<td style="width:70px;padding: 2px;" valign="top">${criteria.modifiedDateFormatted}</td>
	<td style="width:100px;padding: 2px;" valign="top">${criteria.modifiedBy}</td>
</tr>
</c:forEach>
					</table>
			<c:if test="${fn:length(criteriaHistory) > 10 }">
				<script>$("#layer1_content_body").height(300);</script>
			</c:if>					

			</div>
