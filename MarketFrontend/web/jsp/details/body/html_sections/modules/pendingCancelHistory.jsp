
<%@ page language="java" import="java.util.*, com.servicelive.routingrulesengine.RoutingRulesConstants" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>

<div id="layer1_content_body" class="scroll" style="overflow-y:auto;overflow-x:hidden;">

				
<b style="padding-left: 5px;">Cancel Request History </b><br>
<b style="float:left;padding-left: 5px; display:inline;" >Price</b><b style="float:right; padding-right: 5px; display:inline;">Date/Time</b>
<br>
<hr style="margin-left:5px;width:95%;"/>

<table width="98%" style="padding-left: 3px;table-layout: fixed;" >

<c:forEach var="history" items="${pendingCancelHistory}" >
<tr>
	<td width="75px">
	<c:if test="${history.withdrawFlag != 'true' }">
		$<fmt:formatNumber value="${history.price}"		type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
	</c:if>
	</td>
	<td>
		<span style="float:right;" >
		<fmt:formatDate value="${history.modifiedDate}"	pattern="MM/dd/yy" />
												at <fmt:formatDate value="${history.modifiedDate}"
												pattern="hh:mm a" />
		</span>
	</td>


</tr>
<c:if test="${history.adminResourceId == null}">
<tr>
	<td colspan="2">
<b> Changed By:</b>  ${history.userName} (ID #${history.userId})
	</td>

</tr>
  </c:if>

<c:if test="${history.adminResourceId != null}">
<tr>
	<td colspan="2">
	<b>Changed By:</b>  ${history.adminUserName} (ID #${history.adminResourceId})
	</td> 
</tr>
<tr>
<td colspan="2">
<span style="float:center; margin-left: 30%;">	for  </span> <span style="float:center;padding-left: 5px;"> ${history.userName} (ID #${history.userId})</span>
</td>
</tr>

 </c:if>

<tr>
	<td colspan="2" width="215px;" style="word-wrap: break-word;">
	<div style="display: block; word-wrap: break-word; width:215px;">
	<b>Comments :</b>
 
 	<span style="margin-left:5px;">${history.comments}</span>
	</div></td>
</tr>
<tr>
	<td colspan="2"> 
	 <hr style="margin-left:5px;width:100%;"/>
	</td>
</tr>
</c:forEach>
		
</table>
</div>
</html>
