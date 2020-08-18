<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>

<script type="text/javascript">
$(document).ready(function() {
	if (navigator.userAgent.indexOf("MSIE") != -1)
	{	
		<c:if test="${omDisplayTab == 'Manage Route'}">
		$(".column10").css('width','4%'); 
		</c:if>
		<c:if test="${omTabDTO.viewOrderPricing == false}">
		<c:if test="${omDisplayTab == 'Manage Route'}">
		$(".column4").css('width','11%');
		</c:if>
		</c:if>
		
		<c:choose>
		<c:when test="${omTabDTO.viewOrderPricing == false}">
		<c:if test="${omDisplayTab == 'Current Orders'}">
		$(".omcolumn2").css('width','10%');
		$(".column4").css('width','10%');
		$(".column5").css('width','12%');
		$(".omcolumn6").css('width','11%');
		$(".column8").css('width','11%');
		</c:if>
		<c:if test="${omDisplayTab == 'Cancellations'}">
		$(".omcolumn1").css('width','10%');
		$(".column5").css('width','12%');
		$(".omcolumn3").css('width','14%');
		$(".column8").css('width','15%');
		$(".omcolumnCust").css('width','13%');
		$(".omcolumn3").css('width','12%');
		
		</c:if>
		</c:when>
		<c:otherwise>
		<c:if test="${omDisplayTab == 'Current Orders'}">
		
		$(".column8").css('width','9%');
		</c:if>
		</c:otherwise>
		</c:choose>
		
	}
	if (navigator.userAgent.indexOf("Firefox") != -1)
	{
		<c:choose><c:when test="${omTabDTO.viewOrderPricing == false}">
		<c:if test="${omDisplayTab == 'Current Orders'}">
		$(".omcolumn2").css('width','11%');
		$(".omcolumn3").css('width','11%');
		$(".column4").css('width','11%');
		$(".omcolumn6").css('width','11%');
		</c:if>
		</c:when>
		<c:otherwise>
		<c:if test="${omDisplayTab == 'Current Orders'}">
		$(".omcolumn1").css('width','9%');
		$(".column4").css('width','10%');
		$(".column8").css('width','9%');
		</c:if>
		</c:otherwise></c:choose>
		
	}
});
</script>
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/orderManagement.css" />
</head>
<style>
.grid-table-container {
	height: auto; /*overflow: auto;*/
	width: 710px;
	position: relative; /*100% */
}
</style>
<body class="tundra acquity">
	<div class="grid-table-container"
		style="position: relative; width: 100%;">

		<table cellpadding="0px" cellspacing="0px" width="100%"
			class="omscrollerTableHdr"
			style="color: #5C5753; border: none; padding-top: 5px; padding-bottom: 5px; height: 50px;">
			<c:if test="${omDisplayTab == 'Cancellations'}">
				<tr class="TableHdrRow"
					style="height: 50px; font-weight: 700;">
					<td class="omcolumn1" style="width: 9%; padding-left: 5px;">
						<div id="hdrStatus" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" style="float: left; padding-top: 2px" title="Sort"></i>
							<div style="float: left;">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="grid.label.status" />
							</div>
							<br />
							<div style="float: left; padding-left: 10px;">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="ordermanagement.grid.label.substatus" />
							</div>
						</div>
					</td>
					<td class="omcolumn2" style="width: 12%;">
						<div id="hdrTitle" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.soId.title" />
						</div>
					</td>
					<td class="omcolumnCust" style="width: 12%">
							<div id="hdrCustInfo" style="cursor: pointer;" class="omHdrSort"
								sortOrder="asc">
								<i class="icon-sort" title="Sort"></i>
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="ordermanagement.so.customerinfo" />
							</div>
					</td>
					<td class="omcolumn3" style="width:  13%;">
						<div id="hdrLocation" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.servicelocation" />
						</div>
					</td>
					<td class="column4" style="width: 15%;">
						<div id="hdrAppointmentDate" style="cursor: pointer;"
							class="omHdrSort" sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.serviceappoitment" />
						</div>
					</td>
					<td class="column5" style="width: 10%;">
						<!--<fmt:message bundle="${serviceliveCopyBundle}" key="ordermanagement.so.provider" />-->
						<div id="hdrProviders" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>Provider
						</div>
					</td>
					<td class="omcolumn6" style="width: 7%;">
						<div id="hdrFollowUp" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.flag" />
						</div>
					</td>
					<c:if test="${omTabDTO.viewOrderPricing == true}">
					<td class="column7" style="width: 8%;">
						<div id="hdrPrice" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.price" />
						</div>
					</td>
					</c:if>
					<td class="column8" style="width: 14%;">
						<fmt:message bundle="${serviceliveCopyBundle}"
							key="ordermanagement.so.actions" />
					</td>
					<td class="column9" style="padding-right: 40px; width: 1%;">
						&nbsp;</td>
				</tr>
			</c:if>
			<c:if test="${omDisplayTab == 'Manage Route'}">
				<tr class="TableHdrRow"
					style="height: 50px; font-weight: 700;">
<%-- 					<td class="omcolumn1" style="width: 6%; padding-left: 5px;"><fmt:message
							bundle="${serviceliveCopyBundle}"
							key="ordermanagement.so.selected" /><br /></td> --%>
					<td class="omcolumn1 non-sortable" style="width: 6%; padding-left: 5px;"></td>
					<td class="omcolumn2" style="width: 9%;">
						<div id="hdrStatus" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" style="float: left; padding-top: 2px" title="Sort"></i>
							<div style="float: left;">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="grid.label.status" />
							</div>
							<br />
							<div style="float: left; padding-left: 10px;">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="ordermanagement.grid.label.substatus" />
							</div>
						</div>
					</td>
					<td class="omcolumn3" style="width: 10%;">
						<div id="hdrTitle" style="cursor: pointer;margin-left: 4px;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.soId.title" />
						</div>
					</td>
					<td class="omcolumnCust" style="width: 9%">
							<div id="hdrCustInfo" style="cursor: pointer;" class="omHdrSort"
								sortOrder="asc">
								<i class="icon-sort" title="Sort"></i>
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="ordermanagement.so.customerinfo" />
							</div>
					</td>
					<td class="column4" style="width: 10%">
						<div id="hdrLocation" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.servicelocation" />
						</div>
					</td>
					<td class="column5" style="width: 12%">
						<div id="hdrAppointmentDate" style="cursor: pointer;"
							class="omHdrSort" sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.serviceappoitment" />
						</div>
					</td>
					<td class="omcolumn6" style="width: 9%;padding-left: px;">
						<div id="hdrScheduleStatus" style="cursor: pointer;"
							class="omHdrSort" sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.schedulestatus" />
						</div>
					</td>
					<!--
					<td class="omcolumn7" style="width: 9%">
						<div id="hdrScope" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.scope" />
						</div>
					</td>
					-->
					<td class="column8" style="width: 8%">
						<div id="hdrProviders" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>Provider
						</div>
					</td>
					<td class="column9" style="width: 9%">
						<div id="hdrAvailability" style="cursor: pointer;"
							class="omHdrSort" sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<span class="productAvailabilityInfoIcon"><fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.productavailability" />
							</span>
							<!-- <sup><a style="color: red; text-decoration: none;" href="#"
								id="productAvailabilityInfoIcon"
								class="productAvailabilityInfoIcon">?</a></sup> --->
						</div>
						<div id="productAvailabilityInfo" class="productAvailabilityInfo">
							<p style="padding-left: 5px;">
							Pickup/Product Location(Availability date)
							</p>
						</div>
					</td>
					<td class="column10" style="width: 5%">
						<div id="hdrFollowUp" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.flag" />
						</div>
					</td>
					<td class="column11"
						style="width: 9%;"><fmt:message
							bundle="${serviceliveCopyBundle}"
							key="ordermanagement.so.actions" />
					</td>
					<td class="column12" style="width: 1%;">&nbsp;</td>
				</tr>
			</c:if>

			<c:if test="${omDisplayTab == 'Current Orders'}">
				<tr class="TableHdrRow"
					style="height: 50px; font-weight: 700;">
					<td class="omcolumn1" style="width: 10%; padding-left: 5px;">
						<div id="hdrStatus" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" style="float: left; padding-top: 2px" title="Sort"></i>
							<div style="float: left;">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="grid.label.status" />
							</div>
								<br />
							<div style="float: left; padding-left: 10px;">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="ordermanagement.grid.label.substatus" />
							</div>
						</div>
					</td>
					<td class="omcolumn2" style="width: 10%">
						<div id="hdrTitle" style="cursor: pointer;padding-left: 5px;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.soId.title" />
						</div>
					</td>
					<td class="omcolumn3" style="width: 10%">
						<div id="hdrCustInfo" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
						<fmt:message
							bundle="${serviceliveCopyBundle}"
							key="ordermanagement.so.customerinfo" />
						</div>		
					</td>
					<td class="column4" style="width: 9%">
						<div id="hdrLocation" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.servicelocation" />
						</div>
					</td>
					<td class="column5" style="width: 12%">
						<div id="hdrAppointmentDate" style="cursor: pointer;padding-left: 5px;"
							class="omHdrSort" sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.serviceappoitment" />
						</div>
					</td>
					<td class="omcolumn6" style="width: 9%">
						<div id="hdrScheduleStatus" style="cursor: pointer;"
							class="omHdrSort" sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.schedulestatus" />
						</div>
					</td>
					<!-- 
					<td class="omcolumn7" style="width: 8%">
						<div id="hdrScope" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.scope" />
						</div>
					</td>
					-->
					<td class="column8" style="width: 8%">
						<!--<fmt:message bundle="${serviceliveCopyBundle}" key="ordermanagement.so.provider" />-->
						<div id="hdrProviders" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>Provider
						</div>
					</td>
					<td class="column9" style="width: 9%">
						<div id="hdrAvailability" style="cursor: pointer;"
							class="omHdrSort" sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<span class="productAvailabilityInfoIcon" style="border-bottom: 1px dotted;">
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.productavailability" />
							</span>
						<!-- 	<sup><a style="color: red; text-decoration: none;" href="#"
								id="productAvailabilityInfoIcon"
								class="productAvailabilityInfoIcon">?</a></sup>  -->
						</div>
						<div id="productAvailabilityInfo" class="productAvailabilityInfo">
							<p style="padding-left: 5px;">
							Pickup/Product Location(Availability date)
							</p>
						</div>
					</td>
					<td class="column10" style="width: 5%">
						<div id="hdrFollowUp" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.flag" />
						</div>
					</td>
					<c:if test="${omTabDTO.viewOrderPricing == true}">
					<td class="omColumn11" style="width: 5%">
						<div id="hdrPrice" style="cursor: pointer;margin-left: 7px;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.price" />
						</div>
					</td>
					</c:if>
					<td class="omColumn12" style="width: 10%"><div style="padding-left:5px;"><fmt:message
							bundle="${serviceliveCopyBundle}"
							key="ordermanagement.so.actions" />
							</div>
					</td>
					<td class="omColumn13" style="width: 4%">&nbsp;</td>
				</tr>
			</c:if>

			<tr>
					<c:choose><c:when test="${omTabDTO.viewOrderPricing == true}">
						<c:set var="colspanValueCanc" value="11"></c:set>
						<c:set var="colspanValueCurr" value="13"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="colspanValueCanc" value="10"></c:set>
						<c:set var="colspanValueCurr" value="12"></c:set>
					</c:otherwise></c:choose>

				<td
					<c:if test="${omDisplayTab == 'Cancellations'}">colspan=${colspanValueCanc}</c:if>
					<c:choose><c:when test="${omDisplayTab == 'Manage Route'}">colspan="12"</c:when>
					<c:otherwise>colspan=${colspanValueCurr}</c:otherwise></c:choose>>
					<div id="omDataGridMainDiv"
						style="overflow-y: scroll; overflow-x: hidden; background-color: #FFFFFF;">
							<div id="omSubDiv" style="">
								<c:choose><c:when test="${fn:length(omTabDTO.soList) == 0}">
									<span style="padding-left:400px">No items to view</span>
								</c:when>
								<c:otherwise>
									<jsp:include page="order_management_third_data_grid.jsp" />
								</c:otherwise></c:choose>
						 	</div>
						<div style="padding: 10px;">
							<c:if test="${totalTabCount > countLimit &&  currentSOCount < totalTabCount}">
									<a href="javascript:void(0);" class="viewMoreLink" id="viewMoreLink"><u>View More Service Orders</u></a>
							</c:if>
						</div>
						<div>
							<jsp:include page="/jsp/public/common/omDefaultFooter.jsp" />
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
