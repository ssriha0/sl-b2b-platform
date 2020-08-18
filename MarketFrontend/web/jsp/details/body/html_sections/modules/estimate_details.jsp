<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />

<style type="text/css">
.estimateButton {
	border: 1px solid;
	border-radius: 25px;
	padding: 6px 32px;
	background: white;
}

.laborPartItem {
	padding-right: 54px;
}

.tooltipCustom {
    position: relative;
    display: inline-block;
    border-bottom: 1px solid black;
}

.tooltipCustom .tooltipCustomtext {
    visibility: hidden;
    width: 120px;
    background-color: gray;
    color: black;
    text-align: center;
    border-radius: 6px;
    padding: 5px 0;
    position: absolute;
    z-index: 1;
    bottom: 125%;
    left: 50%;
    margin-left: -60px;
    opacity: 0;
    transition: opacity 1s;
}

.tooltipCustom .tooltipCustomtext::after {
    content: "";
    position: absolute;
    top: 100%;
    left: 50%;
    margin-left: -5px;
    border-width: 5px;
    border-style: solid;
    border-color: #555 transparent transparent transparent;
}

.tooltipCustom:hover .tooltipCustomtext {
    visibility: visible;
    opacity: 1;
}
</style>
<script type="text/javascript">
jQuery("#supportlink").load("soDetailsQuickLinks.action?soId=${estimateVO.soId}");
    function clickEstimateStatusInfo(path)
    {
        jQuery("p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("div.menugroup_body").slideToggle(300);
        var ob=document.getElementById('noteImg').src;
    	if(ob.indexOf('arrowRight')!=-1){
    	document.getElementById('noteImg').src=path+"/images/widgets/arrowDown.gif";
    	}
    	if(ob.indexOf('arrowDown')!=-1){
    	document.getElementById('noteImg').src=path+"/images/widgets/arrowRight.gif";
    	}
    }
	
    function clickEstimateInfo(path)
    {
        jQuery("div.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("div.menugroup_body").slideToggle(300);
        var ob=document.getElementById('estimateImg').src;
    	if(ob.indexOf('arrowRight')!=-1){
    	document.getElementById('estimateImg').src=path+"/images/widgets/arrowDown.gif";
    	}
    	if(ob.indexOf('arrowDown')!=-1){
    	document.getElementById('estimateImg').src=path+"/images/widgets/arrowRight.gif";
    	}
    }
    
    function clickEstimateHistoryInfo(path)
    {
        jQuery("label.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("div.menugroup_body").slideToggle(300);
        var ob=document.getElementById('estimateHistoryImg').src;
    	if(ob.indexOf('arrowRight')!=-1){
    	document.getElementById('estimateHistoryImg').src=path+"/images/widgets/arrowDown.gif";
    	}
    	if(ob.indexOf('arrowDown')!=-1){
    	document.getElementById('estimateHistoryImg').src=path+"/images/widgets/arrowRight.gif";
    	}
    }
</script>
<div class="soNote">
	<!-- START RIGHT SIDE MODULES -->
	<div id="rightsidemodules" class="colRight255 clearfix" >
         <p id="supportlink" style="color:#000;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;"><span> </span></p>
    </div>
	<!-- END RIGHT SIDE MODULES -->
	<c:if test="${empty estimateVO.estimationId}">
		<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 1}">
			<strong>Estimation is not provided for this Service order. Please
				submit estimation by clicking on Add Estimation button.</strong>
		</c:if>
		<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 5 || SERVICE_ORDER_CRITERIA_KEY.roleId == 3}">
		<strong>Estimation is not provided by the provider.</strong>
		</c:if>
	</c:if>
	<div class="contentPane">
			<div id="estimate_status" dojoType="dijit.TitlePane"
				class="contentWellPane">
					<p class="menugroup_head" onClick="clickEstimateStatusInfo('${staticContextPath}');">&nbsp;<img id="noteImg" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Estimate Status</p>
				<div class="menugroup_body">
					<div style="padding-bottom: 25px; margin-top: 15px;">
						<span style="margin-right: 70px;"> 
						<c:if test="${empty estimateVO.estimationId}">
						<strong>Waiting for estimate</strong>
						</c:if> 
						<c:if test="${not empty estimateVO.estimationId}">
						<input type="button"
							style="border: 1px solid; border-radius: 25px; padding: 6px 32px;"
							value="${estimateVO.status}" />
						</c:if>
						</span> 
						<span>Last Updated:</span>
						<table style="margin-left: 183px;">
						<tr>
						<td style="width: 165px;">${estimateVO.estimateHistoryList[0].modifiedBy}</td>
						<td style="width: 50px;">${estimateVO.estimateHistoryList[0].acceptSource}</td>
						<td><fmt:formatDate value="${estimateVO.estimateHistoryList[0].modifiedDate}" pattern="HH:mm | MM/dd/yyyy"  />
						</td>
						</tr>
						</table>
						
					</div>
					<hr />
					<%-- <div style="padding-bottom: 25px; padding-top: 15px;">
						<span> <input class="estimateButton" type="button"
							value="Edit Estimate" />
						</span> <span> <input class="estimateButton" type="button"
							value="Print Estimate" />
						</span> <span> <input class="estimateButton" type="button"
							value="Email Estimate" />
						</span> <span> <input class="estimateButton" type="button"
							value="Add Photos/attachment" />
						</span>
					</div> --%>
				</div>
			</div>
			<div id="estimate" dojoType="dijit.TitlePane" class="contentWellPane">
				<div class="menugroup_head" onClick="clickEstimateInfo('${staticContextPath}');" style="padding: 1px 1px 10px;">&nbsp;<img id="estimateImg" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Estimate</div>
				<div class="menugroup_body">
					<div>
						<span class="laborPartItem">Reference S.O #</span> <span
							class="laborPartItem">Customer Name</span> <span
							style="padding-right: 120px;">Customer Location</span> <span>Date</span>
					</div>
					<table width="100%">
						<tr>
							<td width="140px">${estimateVO.estimationRefNo}</td>
							<td width="140px">${estimateVO.serviceContact.firstName}
								${estimateVO.serviceContact.lastName}</td>
							<td width="210px">${estimateVO.serviceLocation.city}
								${estimateVO.serviceLocation.state}
								${estimateVO.serviceLocation.zip}</td>
							<td><fmt:formatDate value="${estimateVO.estimationDate}" pattern="MMM dd yyyy"  /></td>
						</tr>
					</table>
					<hr />
					<div
						style="color: black; height: 33px; background: lightgray none repeat scroll 0% 0%;">
						<span style="font-weight: bold;">LABOR ITEM</span> <span
							style="font-weight: bold; float: right;">Total Labors:$
							${estimateVO.totalLaborPrice}</span>
					</div>

					<div>
						<span style="padding-right: 14px;">S.no</span> <span
							style="padding-right: 140px;">Name</span> <span
							style="padding-right: 140px;">Description</span> <span
							style="padding-right: 30px;">Price</span> <span
							style="padding-right: 30px;">Hours</span> <span
							style="padding-right: 30px;">Additinal C</span> <span>Total</span>
					</div>

					<c:forEach var="labor" items="${estimateVO.estimateTasks}">
						<table width="100%">
							<tr>
								<td width="38px">${labor.taskSeqNumber}</td>
								<td width="170px">${labor.taskName}</td>
								<td width="200px">${labor.description}</td>
								<td width="65px">${labor.unitPrice}</td>
								<td width="65px">${labor.quantity}</td>
								<c:if test="${not empty labor.additionalDetails}">
								
								<td>
								<div class="tooltipCustom">YES
								  <span class="tooltipCustomtext">${labor.additionalDetails}</span>
								</div>
								
								</td>
   								<%-- <td width="75px">${part.additionalDetails}</td> --%>
								</c:if>
								<c:if test="${empty labor.additionalDetails}">
   								<td width="75px">${labor.additionalDetails}</td>
								</c:if>
								<td>${labor.totalPrice}</td>
							</tr>
						</table>
					</c:forEach>


					<div
						style="color: black; height: 33px; background: lightgray none repeat scroll 0% 0%;">
						<span style="font-weight: bold;">PART ITEM</span> <span
							style="font-weight: bold; float: right;">Total Parts:$
							${estimateVO.totalPartsPrice}</span>
					</div>

					<div>
						<span style="padding-right: 14px;">S.no</span> <span
							style="padding-right: 140px;">Name</span> <span
							style="padding-right: 140px;">Description</span> <span
							style="padding-right: 10px;">Number</span> <span
							style="padding-right: 10px;">Price</span> <span
							style="padding-right: 10px;">Quantity</span> <span
							style="padding-right: 11px;">Additinal C</span> <span>Total</span>
					</div>
					<c:forEach var="part" items="${estimateVO.estimateParts}">
						<table width="100%">
							<tr>
								<td width="38px">${part.partSeqNumber}</td>
								<td width="170px">${part.partName}</td>
								<td width="200px">${part.description}</td>
								<td width="50px">${part.partNumber}</td>
								<td width="55px">${part.unitPrice}</td>
								<td width="30px">${part.quantity}</td>
								<c:if test="${not empty part.additionalDetails}">
								<!-- <td width="75px">YES</td> -->
								<td>
								<div class="tooltipCustom">YES
								  <span class="tooltipCustomtext">${part.additionalDetails}</span>
								</div>
								
								</td>
   								<%-- <td width="75px">${part.additionalDetails}</td> --%>
								</c:if>
								<c:if test="${empty part.additionalDetails}">
   								<td width="75px">${part.additionalDetails}</td>
								</c:if>
								<td>${part.totalPrice}</td>
							</tr>
						</table>
					</c:forEach>
					<hr />
					<div>
						<span><input type="checkbox"  onclick="return false;"/></span> <span
							style="font-weight: bold;">DISCOUNTS</span> 
							<c:choose>
							<c:when test="${estimateVO.discountType=='AMOUNT'}">
							<span style="font-weight: bold; float: right;">${estimateVO.discountedAmount}
							:-$ ${estimateVO.discountedAmount}</span>
							</c:when>
							<c:when test="${estimateVO.discountType=='PERCENTAGE'}">
							<span style="font-weight: bold; float: right;">${estimateVO.discountedPercentage}%
							:-$ ${estimateVO.discountedAmount}</span>
							</c:when>
							<c:otherwise>
							<span style="font-weight: bold; float: right;">${estimateVO.discountedPercentage}%
							:-$ ${estimateVO.discountedAmount}</span>
							</c:otherwise>
							</c:choose>
							
							
					</div>
					<hr />
					<%-- <div>
						<span><input type="checkbox" onclick="return false;"/></span> <span
							style="font-weight: bold;">OTHER</span>
					</div>

					<div>
						<span style="padding-right: 14px;">S.no</span> <span
							style="padding-right: 140px;">Name</span> <span
							style="padding-right: 140px;">Description</span> <span
							style="padding-right: 30px;">Price</span> <span
							style="padding-right: 30px;">Quantity</span> <span>Total</span>
					</div>
					<table width="100%">
						<tr>
							<c:forEach var="other"
								items="${estimateVO.estimateOtherEstimateServices}">
								<td width="30px">${other.otherServiceSeqNumber}</td>
								<td width="178px">${other.otherServiceName}</td>
								<td width="200px">${other.description}</td>
								<td width="76px">${other.unitPrice}</td>
								<td width="60px">${other.quantity}</td>
							</c:forEach>
							<td>${estimateVO.totalOtherServicePrice}</td>
						</tr>
					</table> --%>
					<hr />
					<div>
					<div>
						<span><input type="checkbox" onclick="return false;"/></span> <span
							style="font-weight: bold;">TAX</span> <span style="float: right;">${estimateVO.taxRate}%
							$ ${estimateVO.taxPrice} </span>
					</div>
					<hr />
					<div style="padding-left: 549px;">
						Total $ ${estimateVO.totalPrice}
					</div>
					</div>
				</div>
			</div>
			
			<div id="estimate_history" dojoType="dijit.TitlePane"
				class="contentWellPane">
					<label class="menugroup_head" style="padding: 6px 563px 6px 1px;" onClick="clickEstimateHistoryInfo('${staticContextPath}');">&nbsp;<img id="estimateHistoryImg" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Estimate history log</label>
				<div class="menugroup_body">
					<table>
					<tr>
					<td style="width: 200px;">User</td>
					<td style="width: 175px;">Date/Time</td>
					<td style="width: 165px;">Estimate Status</td>
					<td>Source</td>
					</tr>
					<tr>
					<c:forEach var="history" items="${estimateVO.estimateHistoryList}">
					<td>${history.modifiedBy}</td>
					<td><fmt:formatDate value="${history.modifiedDate}" pattern="HH:mm | MM/dd/yyyy"  /></td>
					<td>${history.status}</td>
					<td>${history.acceptSource}</td>
					</tr>
					</c:forEach>
					</table>
					<hr />
				</div>

			</div>
	</div>
	</div>
</html>
