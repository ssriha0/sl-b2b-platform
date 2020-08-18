<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>	
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<c:set value="" var="selected"></c:set>
<c:choose>

<c:when test = "${'true' == reassign}">
 	<c:set var="pop_up_title"  value="Re-assign Provider"/>
 	<c:set var="type" value="Reassign"></c:set>
</c:when>
<c:otherwise>
 	<c:set var="pop_up_title"  value="Assign a Provider"/>
 	<c:set var="type" value="Assign"></c:set>
</c:otherwise>
</c:choose>
<body>
	<input type="hidden" id="accept_so_id" value="${soId}">
	<input type="hidden" id="groupInd" value="">
	<div id="modalTitleBlack"
		style="background: #58585A;border-bottom: 2px solid black; color: #FFFFFF; text-align: left; height: 25px; padding-left: 8px; padding-top: 5px;">
		<span style="font-size: 17px; font-weight: bold;">${pop_up_title}</span> 
		<div style="float: right;padding:5px;color: #CCCCCC;">
			<i class="icon-remove-circle" style=" font-size: 20px;position: absolute; right: 5px;cursor: pointer;top: 5px;"  onclick="closeModal('assignProviderDiv');"></i>
		</div>
	</div>
<c:if test="${omApiErrors!=null && fn:length(omApiErrors) > 0}">
			<br/>
				<div id="omApiErrorDiv" class="" style="">
				<div  class="errorBox">
				<ul>
				<c:forEach var="omApiError" items="${omApiErrors}">
				<c:if test="${omApiError!=null}">
				<li>
					${omApiError.msg}
				</li>
				</c:if>
				</c:forEach>
				</ul>
				</div>
				</div>
			<c:remove var="omApiErrors" scope="session" />
			</c:if>
	<div style="padding-left: 10px;font-size: 13px;color: #333333;">
		<div style="height: 50px;">
			<div class="acceptSOHdr"  style="word-wrap: break-word;padding-top: 5px;float: left;">
				<p style="text-align: left;line-height: 16px;">
					<span style="font-size: 15px; font-weight: bold; color: #000000;"><c:out value="${soTitle}"></c:out> </span>
					<br />
					<span style="font-size: 11px; color:#999999;">S.O. # <c:out value="${soId}"></c:out> </span>
				</p>
			</div>
		</div>
		<div  id="time-and-loc" style="">
			<div style="width: 480px;word-wrap: break-word;">
				<p style="text-align: left;">
					<span style="font-weight: bold;">When:</span>
					<span> <c:out value="${serviceTime}"></c:out> </span>
				</p>
				<p style="text-align: left;">
					<span style="font-weight: bold;">Where:</span>
					<span> <c:out value="${serviceLocation}"></c:out> </span>
				</p>
				<c:if test = "${'true' == reassign}">
					<p style="text-align: left;">
						<span style="font-weight: bold;">Provider:</span>
						<span> <c:out value="${assignProvName}"></c:out> </span>
					</p>
				</c:if>
			</div>			
		</div>
		<div style="padding: 2px 2px 0px 5px;">
				<div class="errorBox clearfix" id="assignResponseMessage"
					style="width: 450px; overflow-y: hidden; display: none;margin: 3px 0px;padding: 3px;">
				</div>
			</div>
			
		<div id="assign-div" style="padding-top: 5px;padding-bottom: 5px;">
			<div style="float: left;">
				<div style="height:25px;">
					<div style="float: left;padding: 3px 0px 3px 0px; margin-top: 5px;">
						<span style=""><b>${type} this order to:</b></span>
					</div>
				</div>
				<div id="providerList" style="padding-top: 8px;">
					<select id="selectProvider" style="width: 230px;height: 20px;font-family: Arial;font-size: 12px;margin-left:0px;">
						<option value="0"  selected='selected' style="font-family: Arial;font-size: 12px;">-Select One-</option>
						<c:forEach var="provider" items="${eligibleProviders}"
							varStatus="rowCounter">
							<option value="${provider.resourceId}" style="font-family: Arial;font-size: 12px;">
								${provider.providerFirstName} ${provider.providerLastName}&nbsp;(${provider.distancefromSOLocation} miles)</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div style="float: right;margin-top: 10px;">
				<div class="" style="border-width:6px 1px 1px;background-color: #F9FDB1;border-color: #BBBBBB;border-style: solid;width: 280px;margin-left: 10px;padding-top: 5px;margin-right: 10px;">
					<div style="float: left;padding: 5px;margin-top: -2px;">
							<i class="icon-lightbulb" style="font-size: large;"></i>
					</div>
					<div style="font-size: 12px;padding-bottom: 2px;">
						Distance in miles represents provider distance from Service Location (center zip to center zip)
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="assign-popup-footer" style="background: #EEEEEE;border-top: 1px solid #CCCCCC;width: 100%;margin-top: 80px;padding-top: 10px;padding-bottom: 10px;">
			<div style="bottom: 10px; right: 5px; margin-left: 85%;margin-top: 5px;">
				<input type="button" value="Confirm" id="assignProvider" class="actionButton"
					 onclick="assignServiceProvider('${soId}','${reassign}');" />
			</div>
	</div>
</body>
</html>