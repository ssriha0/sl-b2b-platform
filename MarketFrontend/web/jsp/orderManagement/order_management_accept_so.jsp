<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<script type="text/javascript">
$("#providerList").show();

function checkAcceptMinutes(){
	var routeMethod = $("#routeMethod").val();
	var groupInd = $("#groupInd").val();
	var soId = $("#accept_so_id").val();
	var provider = $("#serviceProviders").val();
	if('true' == routeMethod){
     	$('#loadTimerDiv').hide();	
     	$("#acceptOrder").show();
     }else{
     	var assignee = 'typeProvider';
     	if(0 == provider){
     		assignee = 'typeFirm';
     	}
     	jQuery('#loadTimerDiv').load("loadTimer.action?provider="+provider+"&assignee="+assignee+"&soId="+soId+"&groupInd="+groupInd, 
     	function(){
     		var val = $("#timerVal").val();
     		if(0 != val){	
     			$("#acceptOrder").hide();
     			loadTimer();	
     			$('#loadTimerDiv').show();
     		}else{
     			$('#loadTimerDiv').hide();
     		}
     	});	
     }
}
</script>
<body>
	<input type="hidden" id="accept_so_id" value="${soId}">
	<input type="hidden" id="groupInd" value="">
	<input type="hidden" id="routeMethod" value="${routeMethod}">
	<div id="modalTitleBlack"
		style="background: #58585A;border-bottom: 2px solid black; color: #FFFFFF; text-align: left; height: 25px; padding-left: 8px; padding-top: 5px;">
		<span style="font-size: 17px; font-weight: bold;">
		Accept Service Order</span> 
		<div style="float: right;padding:5px;color: #CCCCCC;">
			<i class="icon-remove-circle" style=" font-size: 20px;position: absolute; right: 5px;cursor: pointer;top: 5px;"  onclick="closeModal('assignOrder');"></i>
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
		<div style="float: left;height: 60px;">
			<div class="acceptSOHdr"  style="word-wrap: break-word;padding-top: 5px;width: 325px;float: left;">
				<p style="text-align: left;line-height: 16px;">
					<span style="font-size: 15px; font-weight: bold; color: #000000;"><c:out value="${soTitle}"></c:out> </span>
					<br />
					<span style="font-size: 11px; color:#999999;">S.O. # <c:out value="${soId}"></c:out> </span>
				</p>
			</div>
			<div style="float: right;height: 45px;width: 180px;margin-top:0px;background-color: #DAFFB6;color: #468847;text-align: center;vertical-align: middle;">
				<div style="padding-top: 12px;">
					<span style="font-weight: bold;">Maximum Price: </span>
					<span><fmt:formatNumber type="currency" currencySymbol="$" value="${maxPrice}"/></span>
				</div>
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
			</div>
			
			<div style="padding-top: 15px;padding-bottom: 5px;">
				<p style="text-align: left;">
					<span style="font-weight: bold;">NOTE:</span>
					<span> You must assign a provider to this order prior to the service start date. You can do this either now or later.</span>
				</p>
			</div>
		</div>
		<div style="padding: 2px 2px 0px 5px;">
				<div class="errorBox clearfix" id="acceptSOResponseMessage"
					style="width: 450px; overflow-y: hidden; display: none;">
				</div>
			</div>
		<div id="assign-div" style="padding-top: 5px;padding-bottom: 5px;">
			<div style="height:25px">
				<div style="float: left;padding: 3px 5px 3px 3px;">
					<input type="checkbox"  id="assignToProvider" value="prov" checked="checked"/>
				</div>
				<div style="float: left;padding: 3px 0px 3px 3px; ">
					<span style=""> Assign a Provider Now</span>
				</div>
			</div>
			<div id="providerList" style="padding-top: 2px;display: none;">
				<select id="serviceProviders" style="width: 230px;height: 20px;font-family: Arial;font-size: 12px;"
				 onchange="checkAcceptMinutes()">
					<option value="0" selected="selected">-Select One-</option>
					<c:forEach var="provider" items="${eligibleProviders}"
						varStatus="rowCounter">
						<option value="${provider.resourceId}" style="font-family: Arial;font-size: 12px;">
							${provider.providerFirstName} ${provider.providerLastName}(
							${provider.distancefromSOLocation} miles)</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>
	<div id="accept-popup-footer" style="float: left;background: #EEEEEE;border-top: 1px solid #CCCCCC;width: 100%;margin-top: 20px;">
			<div style="padding: 10px;width: 290px;word-wrap: break-word;">
				<p style="text-align: left;font-size: 11px;">
					<span style="color: rgb(51, 51, 51);">By accepting this order, you are confirming your acceptance of the </span>
					<a id="termsAndCond" href="#" onclick="openTermsAndConditions();" style="">
						<span style="color: rgb(0, 160, 210);">ServiceLive Terms &amp; Conditions</span>
					</a>
				</p>
			</div>
			<div style="position: absolute;bottom: 10px; right: 5px;">
				<input type="button" value="ACCEPT SERVICE ORDER" id="acceptOrder" class="actionButton"
					onclick="submitAcceptSO();" />
			</div>
			<div id="loadTimerDiv" style="display:none;">
			   	<jsp:include page="order_management_panel_accept.jsp"/>
			</div>
	</div>
</body>
</html>