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
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/orderManagement.css" />
<script type="text/javascript">
$(document).ready(function() {
	if (navigator.userAgent.indexOf("Firefox") != -1)
	{ 
		<c:if test="${omDisplayTab == 'Print Paperwork'}">
		$(".column11").css('width','2%');    
		</c:if>
		
		<c:if test="${omTabDTO.viewOrderPricing == false}">

		<c:if test="${omDisplayTab == 'Awaiting Payment'}">
	
		</c:if>
		</c:if>
	}
	
	if (navigator.userAgent.indexOf("MSIE") != -1)
	{
		<c:if test="${omDisplayTab == 'Print Paperwork'}">
		$(".column11").css('width','2%');    
		</c:if>	
		<c:if test="${omTabDTO.viewOrderPricing == false}">
		<c:if test="${omDisplayTab == 'Assign Provider'}">
		$(".column10").css('width','8%');
		</c:if>	
		</c:if>	
	}
});
</script>
<style type="text/css">
.grid-table-container {
	height: auto; /*overflow: auto;*/
	width: 710px;
	position: relative; /*100% */
}
</style>
</head>

<body class="tundra acquity">
	<div class="grid-table-container"
		style="position: relative; width: 100%;">

		<table cellpadding="0px" cellspacing="0px" width="100%"
			class="omscrollerTableHdr"
			style="color: #5C5753; border: none; padding-top: 5px; padding-bottom: 5px; height: 50px;table-layout: fixed;">
			<c:if
				test="${omDisplayTab == 'Assign Provider' || omDisplayTab == 'Awaiting Payment'}">
				
					<c:choose><c:when test="${omTabDTO.viewOrderPricing == true}">
						<c:set var="titleWidth" value="12"></c:set>
						<c:set var="locationWidth" value="12"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="titleWidth" value="12"></c:set>
						<c:set var="locationWidth" value="14"></c:set>
					</c:otherwise></c:choose>
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
					<td class="omcolumn2" style="width: ${titleWidth}%">
						<div id="hdrTitle" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.soId.title" />
						</div>
					</td>
					<td class="omcolumnCust" style="width:10%">
							<div id="hdrCustInfo" style="cursor: pointer;" class="omHdrSort"
								sortOrder="asc">
								<i class="icon-sort" title="Sort"></i>
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="ordermanagement.so.customerinfo" />
							</div>
					</td>
					<td class="omcolumn3" style="width: ${locationWidth}%">
						<div id="hdrLocation" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.servicelocation" />
						</div>
					</td>
					<td class="column4" style="width: 12%">
						<div id="hdrAppointmentDate" style="cursor: pointer;"
							class="omHdrSort" sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.serviceappoitment" />
						</div>
					</td>
					<c:choose><c:when test="${omDisplayTab == 'Assign Provider'}">
						<td class="column5" style="width: 14%">
							<div id="hdrScheduleStatus" style="cursor: pointer;"
								class="omHdrSort" sortOrder="asc">
								<i class="icon-sort" title="Sort"></i>
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="ordermanagement.so.schedulestatus" />
							</div>
						</td>
					</c:when>
					<c:otherwise>
						<td class="omcolumn6" style="width: 14%">
							<div id="hdrProviders" style="cursor: pointer;" class="omHdrSort"
								sortOrder="asc">
								<i class="icon-sort" title="Sort"></i>Provider
							</div>
						</td>
					</c:otherwise></c:choose>
					<td class="omcolumn7" style="width: 7%">
						<div id="hdrFollowUp" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.flag" />
						</div>
					</td>
					<c:if test="${omTabDTO.viewOrderPricing == true}">
					<td class="column8" style="width: 7%">
						<div id="hdrPrice" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.price" />
						</div>
					</td>
					</c:if>
					<td class="column9" style="width: 14%"><fmt:message
							bundle="${serviceliveCopyBundle}"
							key="ordermanagement.so.actions" /></td>
					<c:if test="${omTabDTO.viewOrderPricing == true}">
					<td class="column10" style="width: 3%">&nbsp;</td>
					</c:if>
					<c:if test="${omDisplayTab == 'Assign Provider' && omTabDTO.viewOrderPricing != true}">
					<td class="column10" style="width: 2%">&nbsp;</td>
					</c:if>
					<c:if test="${omDisplayTab == 'Awaiting Payment' && omTabDTO.viewOrderPricing != true}">
						<td class="column10" style="width: 6%">&nbsp;</td>
					</c:if>
				</tr>
			</c:if>

			<c:if test="${omDisplayTab == 'Print Paperwork'}">
				<tr class="TableHdrRow"	style="height: 50px; font-weight: 700;">
					<td class="omcolumn1 non-sortable"
						style="width: 8%; padding-left: 7px; padding-bottom: 0px;">
						<fmt:message bundle="${serviceliveCopyBundle}"
							key="ordermanagement.so.selectall" /><br />
						<p style="text-align: center;">
							<input type="checkbox" name="selectAll" id="selectAll" />
						</p>
					</td>
					<td class="omcolumn2" style="width: 11%">
						<div id="hdrStatus" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" style="float: left; padding-top: 2px" title="Sort"></i>
							<div style="float: left;">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="grid.label.status" />
							</div>
							<br />
							<div style="float: left; padding-left: 12px;">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="ordermanagement.grid.label.substatus" />
							</div>
						</div>
					</td>
					<td class="omcolumn3" style="width: 10%">
						<div id="hdrTitle" style="cursor: pointer;" class="omHdrSort"
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
					<td class="column4" style="width: 12%">
						<div id="hdrLocation" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.servicelocation" />
						</div>
					</td>
					<td class="column5" style="width: 14%">
						<div id="hdrAppointmentDate" style="cursor: pointer;"
							class="omHdrSort" sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.serviceappoitment" />
						</div>
					</td>
					<td class="omcolumn6" style="width: 10%">
						<div id="hdrScheduleStatus" style="cursor: pointer;"
							class="omHdrSort" sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.schedulestatus" />
						</div>
					</td>
					<td class="column8" style="width: 10%;">
						<!--<fmt:message bundle="${serviceliveCopyBundle}" key="ordermanagement.so.provider" />-->
						<div id="hdrProviders" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>Provider
						</div>
					</td>
					<td class="column9" style="width: 10%">
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
						<div id="productAvailabilityInfo" class="productAvailabilityInfo" style="right: 15px;">
							<p style="padding-left: 5px;">
							Pickup/Product Location(Availability date)
							</p>
						</div>
					</td>
					<td class="column10" style="width: 4%">
						<div id="hdrFollowUp" style="cursor: pointer;" class="omHdrSort"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.flag" />
						</div>
					</td>
					<td class="column11" style="width: 2%">&nbsp;</td>
				</tr>
			</c:if>

			<tr>

				<td
					<c:choose><c:when test="${omDisplayTab == 'Assign Provider' || omDisplayTab == 'Awaiting Payment'}">colspan="10"</c:when>
					<c:otherwise>colspan="11"</c:otherwise></c:choose>>
					<div id="omDataGridMainDiv"
						style="overflow-y: scroll; overflow-x: hidden; background-color: #FFFFFF;">
							<div id="omSubDiv" style="">
								<c:choose><c:when test="${fn:length(omTabDTO.soList) == 0}">
									<span style="padding-left:400px">No items to view</span>
								</c:when>
								<c:otherwise>
									<jsp:include page="order_management_second_data_grid.jsp" />
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
