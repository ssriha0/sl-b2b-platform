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

</head>
<script type="text/javascript">
$(document).ready(function() {
	
		<c:if test="${omDisplayTab == 'Confirm Appt window'}">
		$(".omColumn8").css('width','11%');     
		
		</c:if> 
/* 		 R12_0 Adding OR condition for revisit needed tab */	
		<c:if test="${omDisplayTab == 'Job Done' || omDisplayTab == 'Revisit Needed' }">
			$(".omColumn5").css('width','13%');    
		
		</c:if>
		<c:if test="${omDisplayTab == 'Inbox'}">
		$(".omColumn7").css('width','10%');    
		$(".omColumn8").css('width','4%');    
		$(".omColumn9").css('width','6%');     
		$(".omColumn10").css('width','14%');    

		</c:if>
			
});

</script>
<c:set var="colSpan" value="11"></c:set>
<body class="tundra acquity">
	<div class="grid-table-container"
		style="position: relative; width: 100%">

		<table cellpadding="0px" cellspacing="0px" width="98%"
			class="omscrollerTableHdr mainTableHdr"
			style="color: #5C5753; border: none; padding-top: 5px; padding-bottom: 5px; height: 50px; table-layout: fixed;float: left;">
			<tr class="TableHdrRow"
				style="width: 100%;font-weight: 700;">
				<c:choose>
				<c:when test="${omDisplayTab == 'Respond'}">
					<td class="omColumn1" style="width: 8%; padding-left: 5px;">
					<div id="hdrDateRecieved" class="omHdrSort" style="cursor: pointer;"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
					<fmt:message
							bundle="${serviceliveCopyBundle}"
							key="ordermanagement.so.datereceived" />
					</div>		
					</td>
					<td class="omColumn2" style="width: 10%; padding-left: 10px;text-align: left;">
						<div id="hdrOrderType" class="omHdrSort" style="cursor: pointer;"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
						<fmt:message bundle="${serviceliveCopyBundle}"
							key="ordermanagement.so.ordertype" />
						</div>
					</td>
					<td class="omColumn3" style="width: 10%">
						<div id="hdrTitle" class="omHdrSort" style="cursor: pointer;"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.soId.title" />
						</div>
					</td>
					<td class="omColumn4" style="width: 12%">
						<div id="hdrLocation" class="omHdrSort" style="cursor: pointer;"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.servicelocation" />
						</div>
					</td>
					<td class="omColumn5" style="width: 12%">
						<div id="hdrAppointmentDate" style="cursor: pointer;padding-left: 5px;"
							class="omHdrSort" sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.serviceappoitment" />
						</div>
					</td>
					<td class="omColumn6" style="width: 13%">
						<div id="hdrAvailability" style="cursor: pointer;"
							class="omHdrSort" sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<span class="productAvailabilityInfoIcon">
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
				</c:when>
	
				<c:when test="${omDisplayTab == 'Job Done' || omDisplayTab == 'Revisit Needed'}">
					<td class="omColumn1" style="width: 9%; padding-left: 5px;">
						<c:choose>
							<c:when test="${omDisplayTab == 'Job Done'}">
							<div id="hdrJobDone" style="cursor: pointer;"
								class="omHdrSort" sortOrder="asc">
								<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}" key="ordermanagement.so.jobdoneon" />
							</div>
							</c:when>
							<c:when test="${omDisplayTab == 'Revisit Needed'}">
							<div id="hdrTripOn" style="cursor: pointer;"
								class="omHdrSort" sortOrder="asc">
								<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}" key="ordermanagement.so.tripon" />
							</div>
							</c:when>
						</c:choose>
						
					</td>
					<td class="omColumn2" style="width: 11%; text-align: left: ;">
						<div id="hdrTitle" class="omHdrSort" style="cursor: pointer;"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.soId.title" />
						</div>
					</td>
					<td class="omColumn3" style="width: 11%">
						<div id="hdrCustInfo" class="omHdrSort" style="cursor: pointer;"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
							key="ordermanagement.so.customerinfo" />
						</div>		
					</td>
					<td class="omColumn4" style="width: 12%">
						<div id="hdrLocation" class="omHdrSort" style="cursor: pointer;"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.servicelocation" />
						</div>
					</td>
					<td class="omColumn5" style="width: 16%">
						<div id="hdrAppointmentDate" style="cursor: pointer;"
							class="omHdrSort" sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.serviceappoitment" />
						</div>
					</td>
					<td class="omColumn6" style="width: 9%">
						<div id="hdrScope"  class="omHdrSort" style="cursor: pointer;"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.scope" />
						</div>
					</td>
				</c:when>
				<c:when test="${omDisplayTab == 'Resolve Problem'}">
					<c:set var="colSpan" value="11"></c:set>
					<td class="omColumn1" style="width: 8%; padding-left: 5px;">
						<div id="hdrReportedBy"  class="omHdrSort" style="cursor: pointer;"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
						Reported By
						</div>
					</td>
					<td class="omColumn2 non-sortable" style="width: 11%">
						Type Of Problem
					</td>
					<td class="omColumn3" style="width: 10%; text-align: center;">
						<div id="hdrTitle"  class="omHdrSort" style="cursor: pointer;"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
						     <fmt:message bundle="${serviceliveCopyBundle}" key="ordermanagement.so.soId.title" />
						</div>
					</td>
					<td class="omColumn4" style="width: 9%">
						<div id="hdrCustInfo" class="omHdrSort" style="cursor: pointer;"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"key="ordermanagement.so.customerinfo" />
						</div>
					</td>
					<td class="omColumn5" style="width: 13%">
						<div id="hdrLocation" class="omHdrSort" style="cursor: pointer;"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.servicelocation" />
						</div>
					</td>
					<td class="omColumn6" style="width: 13%">
						<div id="hdrAppointmentDate" style="cursor: pointer;margin-right: 5px;"
							class="omHdrSort" sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.serviceappoitment" />
						</div>
					</td>
				</c:when>

				<c:otherwise>
					<c:choose>
					<c:when test="${omDisplayTab == 'Confirm Appt window'}">
						<c:set var="apptWidth" value="8"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="apptWidth" value="11"></c:set>
					</c:otherwise>
					</c:choose>
					<td class="omColumn1" style="width: ${apptWidth}%; padding-left: 5px;">
						<div id="hdrStatus" class="omHdrSort" style="cursor: pointer;"
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
					<td class="omColumn2" style="width: 11%">
						<div id="hdrtitle" class="omHdrSort" style="cursor: pointer;"
							sortOrder="asc">
							<i id="" class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.soId.title" />
						</div>
					</td>
					<c:if test="${omDisplayTab == 'Schedule' || omDisplayTab == 'Confirm Appt window'}">
					    <td class="omColumnCust" style="width: 9%">
						   <div id="hdrCustInfo" class="omHdrSort" style="cursor: pointer;"sortOrder="asc">
							  <i class="icon-sort" title="Sort"></i>
							  <fmt:message bundle="${serviceliveCopyBundle}"key="ordermanagement.so.customerinfo" />
						   </div>
					   </td>
					</c:if>
					<td class="omColumn3" style="width: 12%">
						<div id="hdrLocation" class="omHdrSort" style="cursor: pointer;"
							sortOrder="asc">
							<i id="" class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.servicelocation" />
						</div>
					</td>
					<td class="omColumn4"
						style="width:<c:choose><c:when test="${omDisplayTab == 'Confirm Appt window'}">11%</c:when><c:otherwise>13.8%</c:otherwise></c:choose>;">
						<div id="hdrAppointmentDate" style="cursor: pointer;"
							class="omHdrSort" sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.serviceappoitment" />
						</div>
					</td>
					<td class="omColumn5" style="width:<c:choose><c:when test="${omDisplayTab == 'Confirm Appt window'}">10%</c:when><c:otherwise>11%</c:otherwise></c:choose>;">
						<div id="hdrScheduleStatus" class="omHdrSort"
							style="cursor: pointer;" sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.schedulestatus" />
						</div>
					</td>
		
					<c:if test="${omDisplayTab == 'Inbox' || omDisplayTab == 'Job Done' || omDisplayTab == 'Revisit Needed'}">
					     <td class="omColumn6" style="width:<c:choose><c:when test="${omDisplayTab == 'Confirm Appt window'}">8%</c:when><c:otherwise>9%</c:otherwise></c:choose>;">
						       <div id="hdrScope" class="omHdrSort" style="cursor: pointer;"sortOrder="asc">
							        <i class="icon-sort" title="Sort"></i>
							      <fmt:message bundle="${serviceliveCopyBundle}"key="ordermanagement.so.scope" />
						      </div>
					     </td>
					</c:if>
				</c:otherwise>
				</c:choose>

				<td class="omColumn7" style="width: 11%">
					<c:if test="${omDisplayTab == 'Inbox'}">
						<div id="hdrProviders" class="omHdrSort" style="cursor: pointer;"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.providers" />
						</div>
					</c:if> 
		
					<c:if
						test="${omDisplayTab == 'Schedule' || omDisplayTab == 'Job Done' || omDisplayTab == 'Revisit Needed' || omDisplayTab == 'Resolve Problem' || omDisplayTab == 'Confirm Appt window' || omDisplayTab == 'Respond'}">
						<div id="hdrProviders" class="omHdrSort" style="cursor: pointer;"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i> Provider
						</div>
					</c:if></td>
				<td class="omColumn8"
					style="width:<c:choose><c:when test="${omDisplayTab == 'Confirm Appt window'}">12%</c:when><c:otherwise>5%</c:otherwise></c:choose>;">
					<c:choose>
					<c:when test="${omDisplayTab == 'Confirm Appt window'}">
						<div id="hdrAvailability" style="cursor: pointer;"
							class="omHdrSort" sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<span class="productAvailabilityInfoIcon">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="ordermanagement.so.productavailability" />
							</span>
						<!-- 	<sup> <a style="color: red; text-decoration: none;"
								href="#" id="productAvailabilityInfoIcon"
								class="productAvailabilityInfoIcon">?</a></sup>
								 -->
						</div>
						<div id="productAvailabilityInfo" class="productAvailabilityInfo">
							<p style="padding-left: 5px;">
							Pickup/Product Location(Availability date)
							</p>
						</div>
					</c:when>
					 <c:otherwise>
						<div id="hdrfollowup" class="omHdrSort" style="cursor: pointer;"
							sortOrder="asc">
							<i class="icon-sort" title="Sort"></i>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="ordermanagement.so.flag" />
						</div>
					</c:otherwise>
					</c:choose>
				</td>
				<c:choose>
				<c:when test="${omDisplayTab == 'Confirm Appt window'}">
					<td class="omColumn9" style="width:5%;">
						<div id="hdrfollowup" class="omHdrSort" style="cursor: pointer;"
								sortOrder="asc">
								<i class="icon-sort" title="Sort"></i>
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="ordermanagement.so.flag" />
						</div>
					</td>
				</c:when>
				<c:when test="${omTabDTO.viewOrderPricing == true}">
					<td class="omColumn9" style="width:6%;">
						<div id="hdrPrice" class="omHdrSort" style="cursor: pointer;"
								sortOrder="asc">
								<i class="icon-sort" title="Sort"></i>
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="ordermanagement.so.price" />
						</div>
					</td>
				</c:when>
				</c:choose>
				<td class="omColumn10" style="width: 12%;">
					<div style="padding-left: 20px;">
						<fmt:message
						bundle="${serviceliveCopyBundle}" key="ordermanagement.so.actions" />
					</div>
				</td>
				<td class="omColumn11" style="width: 2%">&nbsp;</td>
			</tr>
			<tr>
			<c:if test="${omTabDTO.viewOrderPricing != true && omDisplayTab != 'Confirm Appt window'}">
				<c:set var="colSpan" value="${colSpan-1}"></c:set>
			</c:if>
				<td colspan="${colSpan}">
					<div id="omDataGridMainDiv"
						style="overflow-y: scroll; overflow-x: hidden; background-color: #FFFFFF;padding-bottom: 50px;<c:if test="${omDisplayTab == 'Confirm Appt window'}">width:100%;</c:if>">
							<div id="omSubDiv" style="">
								<c:choose>
								<c:when test="${fn:length(omTabDTO.soList) == 0}">
									<span style="padding-left:400px">No items to view</span>
								</c:when>
								<c:otherwise>
									<jsp:include page="order_management_data_grid.jsp" />
								</c:otherwise>
								</c:choose>
						   </div>
						<div style="padding: 10px;">
							<c:if test="${totalTabCount > countLimit &&  currentSOCount < totalTabCount}">
									<a href="javascript:void(0);" class="viewMoreLink" id="viewMoreLink"><u>View More Service Orders</u></a>
							</c:if>
						</div>
							<div class="omFooter">
								<jsp:include page="/jsp/public/common/omDefaultFooter.jsp" />
							</div>
					</div>
					
				</td>
			</tr>
		</table>
	</div>

</body>
</html>
