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
<style type="text/css"> 
.data-grid-inbox-row td{
	word-wrap: break-word;
}
.grid-table-container td {
	border: none;
}
.data-grid-inbox-row{
  width: 100%;
  table-layout: fixed;
  
}
.treeview li {
    background: url("${staticContextPath}/images/widgets/ui/treeview/treeview-default-line.gif") no-repeat scroll 0 0 transparent;
    padding: 0px 0px 0px 15px;
}
.group-so-hdr:hover {
	background: #F4F4F4;
    border-bottom-color: #EFEFEF;
}
.grid-table-container table:hover {
	background: #F4F4F4;
    border-bottom-color: #EFEFEF;
}
.TableHdrRow td {
    border-right: 1px solid #B6D4EA;
    padding: 0 4px;
}
.capitalizeDiv{text-transform:capitalize;}
.tilteFlyOut {
	display: none;
	border-style: solid;
	background-color: #F9FDB1;
	border-color: #BBBBBB;
	max-width: 350px;
	border-width: 0px 0px 4px;
	z-index: 99999;
	position: absolute;
	height: auto;
	overflow: auto;
	-webkit-box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);
	-moz-box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
	box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
}
</style>
<script type="text/javascript">

$(document).ready(function() {
	if (navigator.userAgent.indexOf("MSIE") != -1 )
	{	
	    $("#scheduleInboxTable .omColumn1").css('border','none');
		$("#scheduleInboxTable .omColumn2").css('border','none');
		$("#scheduleInboxTable .omColumn3").css('border','none');
		$("#scheduleInboxTable .omColumn4").css('border','none');
		$("#scheduleInboxTable .omColumn5").css('border','none');
		$("#scheduleInboxTable .omColumn6").css('border','none');
		$("#scheduleInboxTable .omColumn7").css('border','none');
		$("#scheduleInboxTable .omColumn8").css('border','none');
		$("#scheduleInboxTable .omColumn9").css('border','none');
		$("#scheduleInboxTable .omColumn10").css('border','none');
		$("#scheduleInboxTable .omColumnCust").css('border','none'); 
	    $(".tilteFlyOut").css('position','absolute');
		<c:if test="${omDisplayTab == 'Confirm Appt window'}">
		$(".omColumn2").prop('width','11%');    
		$(".omColumn8").css('width','10%'); 
		$(".omColumn8").css('padding-left','5px'); 
		$(".omColumn9").prop('width','6%'); 
		$(".resheduleDiv").css('padding-left','12px'); 
		$(".alignDiv").css('padding-left','12px');
		</c:if>
		
		<c:if test="${omDisplayTab == 'Schedule'}">
		$(".omColumn10").prop('width','10%'); 
		
		<c:if test="${omTabDTO.viewOrderPricing == false}">
		  $(".omColumn10").prop('width','12%'); 
		  $(".omColumn4").prop('width','13%');  
		  $(".resheduleDiv").css('padding-left','15px'); 
		  $(".alignDiv").css('padding-left','15px');
        </c:if>
        <c:if test="${omTabDTO.viewOrderPricing == true}">
        $(".omColumn2").css('width','11%');
        $(".omColumn4").css('width','13%');
        $(".resheduleDiv").css('padding-left','15px'); 
		$(".alignDiv").css('padding-left','15px'); 
		$(".sheduleDiv").css('padding-left','10px'); 
		$(".locationDiv").css('padding-left','5px');
        </c:if>
		
		</c:if>
		
		<c:if test="${omDisplayTab == 'Resolve Problem'}">
		
		   <c:if test="${omTabDTO.viewOrderPricing == true}">
		     $(".omColumn10").prop('width','13%'); 
		   </c:if>
		   <c:if test="${omTabDTO.viewOrderPricing == false}">
		     $(".omColumn2").css('width','12%');
		     $(".omColumn3").css('width','11%');
		   </c:if>
		   
		</c:if>
		<c:if test="${omDisplayTab == 'Inbox'}">
		$(".omColumn8").prop('width','4%');    
		$(".omColumn9").prop('width','6%');     
		$(".omColumn10").css('width','13%');
		$(".sheduleDiv").css('padding-left','0px'); 
		
			<c:if test="${omTabDTO.viewOrderPricing == true}">
		    $(".omColumn2").prop('width','11%');
		    $(".resheduleDiv").css('padding-left','20px'); 
		    $(".alignDiv").css('padding-left','20px');
		    $(".locationDiv").css('padding-left','5px'); 
		    $(".flagDiv").css('padding-left','15px'); 
		 </c:if>
		 <c:if test="${omTabDTO.viewOrderPricing == false}">
		    $(".locationDiv").css('padding-left','15px'); 
		    $(".flagDiv").css('padding-left','10px'); 
		    $(".resheduleDiv").css('padding-left','25px'); 
	        $(".alignDiv").css('padding-left','25px'); 
	        $(".sheduleDiv").css('padding-left','5px'); 
	     </c:if>
		
		  
		</c:if>
		
		<c:if test="${omTabDTO.viewOrderPricing == false}">
		  <c:if test="${omDisplayTab == 'Inbox' || omDisplayTab == 'Confirm Appt window' ||omDisplayTab == 'Schedule'}">
		  $(".omColumn2").css('width','11%');
		  </c:if>
		</c:if>
		<c:if test="${omTabDTO.viewOrderPricing == true}">
		  /*R1	dding another OR condition for new revisit needed tab */
		  <c:if test="${omDisplayTab == 'Job Done' || omDisplayTab == 'Revisit Needed'}">
		   $(".omColumnCust").css('word-break','break-all');
		   </c:if>
		</c:if>
		
		
	}
	if(navigator.userAgent.indexOf("Firefox") != -1 )
	{	
		<c:if test="${omDisplayTab == 'Resolve Problem'}">
		$(".omColumn3").css('width','10%');      
		</c:if>	
		<c:choose>
		<c:when test="${omTabDTO.viewOrderPricing == true}">
		  <c:if test="${omDisplayTab == 'Schedule'}">
		    $(".omColumn2").css('width','11%');
		    $(".resheduleDiv").css('padding-left','20px'); 
			$(".alignDiv").css('padding-left','20px'); 
			$(".sheduleDiv").css('padding-left','5px'); 
			$(".locationDiv").css('padding-left','5px');
		  </c:if>
		  <c:if test="${omDisplayTab == 'Inbox'}">
	       $(".omColumn2").prop('width','11%');
	       $(".sheduleDiv").css('padding-left','5px'); 
		   $(".locationDiv").css('padding-left','5px');
		   $(".resheduleDiv").css('padding-left','15px'); 
		   $(".alignDiv").css('padding-left','15px');
	      </c:if>
	      <c:if test="${omDisplayTab == 'Respond'}">
	       $(".resheduleDiv").css('padding-left','3px'); 
		   $(".alignDiv").css('padding-left','3px');
	      </c:if>
		  /*R1	dding another OR condition for new revisit needed tab */
	      <c:if test="${omDisplayTab == 'Job Done' || omDisplayTab == 'Revisit Needed'}">
	       $(".flagDiv").css('padding-left','20px'); 
		   $(".providerDiv").css('padding-left','20px');
	      </c:if>
	      <c:if test="${omDisplayTab == 'Resolve Problem'}">
			$(".customerDiv").css('margin-left','2px');      
		  </c:if>	
	     
	   </c:when>
	   <c:otherwise>
	   <c:if test="${omDisplayTab == 'Inbox'}">
	     $(".sheduleDiv").css('padding-left','5px'); 
	     $(".locationDiv").css('padding-left','10px');
	     $(".resheduleDiv").css('padding-left','20px'); 
		 $(".alignDiv").css('padding-left','20px');
	   </c:if>
	     <c:if test="${omDisplayTab == 'Schedule'}">
	     $(".resheduleDiv").css('padding-left','20px'); 
		 $(".alignDiv").css('padding-left','20px');
	    </c:if>
	   </c:otherwise>
	   </c:choose>
	    <c:if test="${omTabDTO.viewOrderPricing == false}">
		  <c:if test="${omDisplayTab == 'Inbox' || omDisplayTab == 'Confirm Appt window' ||omDisplayTab == 'Schedule'}">
		  $(".omColumn2").css('width','11%');
		  </c:if>
		</c:if>
	}
	
		
});
</script>
</head>
<c:set var="grpStartInd" value="false" />
<body class="tundra acquity">
	<div class="grid-table-container"
		style="position: relative; width: 100%; border: 1px solid #CCCCCC;list-style: none;">
		<input type="hidden" value="${currentSOCount}" id="currentOrderCountOnSort" />
		<input type="hidden" value="${totalTabCount}" id="totalOrderCount" />
		<input type="hidden" value="${totalTabCountWithoutFilters}" id="totalTabCountWithoutFilters" />				
		<c:forEach var="soList" items="${omTabDTO.soList}" varStatus="status">
		<c:choose>
			<c:when test="${soList.parentGroupId != null || soList.groupInd == true}">
				<c:set var="so_id" value="${soList.parentGroupId}"></c:set>
				<c:set var="groupInd" value="1"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="so_id" value="${soList.soId}"></c:set>
				<c:set var="groupInd" value="0"></c:set>
			</c:otherwise>
		</c:choose>
			<div class="data-grid-inbox-row" style="">
				<table width="100%" class="data-grid-inbox-row" id="scheduleInboxTable">
					<tr>
						<c:choose>
						<c:when test="${omDisplayTab == 'Respond'}">
							<td class="omColumn1" width="8%" style="padding-left: 5px;padding-bottom: 15px">
								${soList.routedDate} &nbsp;&nbsp;&nbsp;<br />
							</td>
							<td class="omColumn2" width="10%" style="text-transform: capitalize;">
							<div style="float: left;margin-left:3px; padding-bottom: 15px;">
								<c:choose>
								<c:when test="${soList.priceModel == 'ZERO_PRICE_BID'}">
								Bid Request
								</c:when>
								<c:otherwise>
								
								${fn:toLowerCase(soList.soAttribute)}
								</c:otherwise>
								</c:choose>
								</div>
								<c:choose>
								<c:when test="${soList.parentGroupId != null}">
									<div style="float: right;padding-bottom: 10px;">
										<i id='${status.count}_plus_icon' class="icon-plus-sign"
											style="color: #736AFF;cursor: pointer;font-size: 12px;"
											onclick="displayChildOrders('${soList.followUpFlag}', this.id, '${soList.soId}','${status.count}');"></i>
									  	<i id='${status.count}_close_icon' class="icon-folder-close"
											style="color: #E9AB17;cursor: pointer;font-size: 12px;" onclick="displayChildOrders('${soList.followUpFlag}', this.id, '${soList.soId}','${status.count}');"></i>
									</div>
								</c:when>
								<c:when test="${soList.priceModel == 'ZERO_PRICE_BID'}">
									<div style="float: right;padding-bottom: 10px;">
										<i id='${status.count}_plus_icon' class="icon-plus-sign"
											style="color: #736AFF;cursor: pointer;font-size: 12px;"
											onclick="displayBidChildOrders('${soList.followUpFlag}', this.id, '${soList.soId}','${status.count}');"></i>
									  	<i id='${status.count}_close_icon' class="icon-folder-close"
											style="color: #E9AB17;cursor: pointer;font-size: 12px;" onclick="displayBidChildOrders('${soList.followUpFlag}', this.id, '${soList.soId}','${status.count}');"></i>
									</div>
								</c:when>
								</c:choose>
						
							</td>
							<td class="omColumn3" width="11%">
							<div style="padding-left:5px">
								<c:choose>
								<c:when test="${soList.parentGroupId != null}">					  				
					  				<div style="padding-right: 3px">
										<a href="javascript:void(0);" class="soTitleLink"
											id="soTitle${soList.parentGroupId}_${status.count}"
											onmouseover="showTitle(this);"
											onmouseout="hideTitle(this);" onclick="displaySODetails('${soList.parentGroupId}','1','${soList.routedResourceId}');">
											<c:choose>
											    		<c:when test="${not empty soList.parentGroupTitle && fn:length(soList.parentGroupTitle) > 12}">
											    				${fn:substring(soList.parentGroupTitle,	0, 12)}...
											    		</c:when>
											    		<c:when test="${not empty soList.parentGroupTitle  && fn:length(soList.parentGroupTitle) <= 12}">
											    				${soList.parentGroupTitle}
											    		</c:when>
											    		<c:otherwise>&nbsp;</c:otherwise>
											</c:choose>
										</a>
									</div>
									<div id="soTitleDivs_${soList.parentGroupId}_${status.count}" class="tilteFlyOut">
										<div style="padding-left: 5px; margin: 3px;" id="titleName">${soList.parentGroupTitle}
										</div>
									</div>
									${soList.parentGroupId}
					  			</c:when>
					  			<c:when test="${soList.priceModel == 'ZERO_PRICE_BID'}">									
					  				<div style="padding-right: 3px">
										<a href="javascript:void(0);" class="soTitleLink"
											id="soTitle${soList.soId}_${status.count}"
											onmouseover="showTitle(this);"
											onmouseout="hideTitle(this);" onclick="displayBidChildOrders('${soList.followUpFlag}', this.id, '${soList.soId}','${status.count}');">
											<c:choose>
											    		<c:when test="${not empty soList.soTitle && fn:length(soList.soTitle) > 12}">
											    				${fn:substring(soList.soTitle,	0, 12)}...
											    		</c:when>
											    		<c:when test="${not empty soList.soTitle && fn:length(soList.soTitle) <= 12}">
											    				${soList.soTitle}
											    		</c:when>
											    		<c:otherwise>&nbsp;</c:otherwise>
											</c:choose>
										</a>
									</div>
									<div id="soTitleDivs_${soList.soId}_${status.count}" class="tilteFlyOut">
										<div style="padding-left: 5px; margin: 3px;" id="titleName">${soList.soTitle}
										</div>
									</div>
									${soList.soId}
								</c:when>
					  			<c:otherwise>					  	 		
									<div style="padding-right: 3px">
										<a href="javascript:void(0);" class="soTitleLink"
											id="soTitle${soList.soId}_${status.count}"
											onmouseover="showTitle(this);"
											onmouseout="hideTitle(this);" onclick="displaySODetails('${soList.soId}','0','${soList.routedResourceId}');">
											<c:choose>
											    		<c:when test="${not empty soList.soTitle && fn:length(soList.soTitle) > 12}">
											    				${fn:substring(soList.soTitle,	0, 12)}...
											    		</c:when>
											    		<c:when test="${not empty soList.soTitle && fn:length(soList.soTitle) <= 12}">
											    				${soList.soTitle}
											    		</c:when>
											    		<c:otherwise>&nbsp;</c:otherwise>
											</c:choose>
										</a>
									</div>
									<div id="soTitleDivs_${soList.soId}_${status.count}" class="tilteFlyOut">
										<div style="padding-left: 5px; margin: 3px;" id="titleName">${soList.soTitle}
										</div>
									</div>
										${soList.soId}
								</c:otherwise>
							</c:choose>
								</div>
							</td>
							<td class="omColumn4" width="12%">
								<div style="padding-left:5px;">
								<c:if test="${(soList.soStatus != 110)}">
									${soList.street1}, ${soList.street2}<br />
								</c:if>
								${soList.city}<br />
								${soList.state}, ${soList.zip} <c:set var="locn"
									value="${soList.city},${soList.zip}"></c:set>
									</div>
							</td>
							<td class="omColumn5" width="12%">
								<div  class="alignDiv" style="padding-left:5px;">
								<c:choose>
								 <c:when test="${soList.appointEndDate == null || soList.appointEndDate == ''}">
										${soList.appointStartDate} ${soList.serviceTimeStart}
								 </c:when>
								 <c:otherwise>
									${soList.appointStartDate} to ${soList.appointEndDate}<br/>
									${soList.serviceTimeStart} to ${soList.serviceTimeEnd}
							     </c:otherwise>
							     </c:choose> 
								<br/>(${soList.serviceLocationTimezone})
								</div>
							</td>
							<td class="omColumn6" width="13%">
								<div style="padding-left: 10px;">
								<c:choose>
								<c:when test="${soList.parentGroupId != null}">
									Not Applicable
								</c:when>		
								<c:otherwise>			  				
									<c:set var="partStreet" value="${fn:trim(soList.partStreet1)}"/>
									<c:if test="${not empty partStreet}">
										${partStreet}<br />${soList.partStreet2},
											<div>${soList.partCity},</div>
											<div>${soList.partState} ${soList.partZip}</div>
											<c:if test="${not empty soList.availabilityDate}">
													<div>(${soList.availabilityDate})</div>
											</c:if>
									</c:if> 
									<c:if test="${empty partStreet}">
										Not Applicable
									</c:if>
								</c:otherwise>
								</c:choose>
								</div>
							</td>
						</c:when>
						<c:when test="${omDisplayTab == 'Job Done' || omDisplayTab == 'Revisit Needed'}">
							<td class="omColumn1" width="9%" style="padding-left: 5px;">
								
								<c:choose>
								<c:when test="${omDisplayTab == 'Job Done'}">
									<c:choose>
										<c:when test="${null != soList.jobDoneOn && '' != soList.jobDoneOn}">
	               			 			<fmt:formatDate value="${soList.jobDoneOn}"	pattern="MM/dd/yyyy" />&nbsp;&nbsp;&nbsp;
	               			 			</c:when>
	               			 			<c:otherwise>
	               			 			
	               			 			</c:otherwise>
               			 			</c:choose>
								</c:when>
	
								<c:when test="${omDisplayTab == 'Revisit Needed'}">
									<c:choose>
										<c:when test="${null != soList.lastTripOn && '' != soList.lastTripOn}">
		               			 			<fmt:formatDate value="${soList.lastTripOn}"	pattern="MM/dd/yyyy" />&nbsp;&nbsp;&nbsp;
		               			 		</c:when>
		               			 		<c:otherwise>
		               			 			
		               			 		</c:otherwise>
	               			 		</c:choose>
								</c:when>
								</c:choose>
								
								
								
								
							</td>
							<td class="omColumn2" width="11%" style="">
								<c:choose>
								<c:when test="${soList.parentGroupId != null}">					  				
					  				<div style="padding-right: 3px">
										<a href="javascript:void(0);" class="soTitleLink"
											id="soTitle${soList.parentGroupId}_${status.count}"
											onmouseover="showTitle(this);"
											onmouseout="hideTitle(this);" onclick="displaySODetails('${soList.parentGroupId}','1','${soList.routedResourceId}');">
											<c:choose>
											    		<c:when test="${not empty soList.parentGroupTitle && fn:length(soList.parentGroupTitle) > 12}">
											    				${fn:substring(soList.parentGroupTitle,	0, 12)}...
											    		</c:when>
											    		<c:when test="${not empty soList.parentGroupTitle  && fn:length(soList.parentGroupTitle ) <= 12}">
											    				${soList.parentGroupTitle}
											    		</c:when>
											    		<c:otherwise>&nbsp;</c:otherwise>
											</c:choose>
										</a>
									</div>
									<div id="soTitleDivs_${soList.parentGroupId}_${status.count}" class="tilteFlyOut">
										<div style="padding-left: 5px; margin: 3px;" id="titleName">${soList.parentGroupTitle}
										</div>
									</div>
									${soList.parentGroupId}
					  			</c:when>
					  			<c:otherwise>					   				
									<div style="padding-right: 3px">
										<a href="javascript:void(0);" class="soTitleLink"
											id="soTitle${soList.soId}_${status.count}"
											onmouseover="showTitle(this);"
											onmouseout="hideTitle(this);" onclick="displaySODetails('${soList.soId}','0','${soList.routedResourceId}');">
											<c:choose>
											    		<c:when test="${not empty soList.soTitle && fn:length(soList.soTitle) > 12}">
											    				${fn:substring(soList.soTitle,	0, 12)}...
											    		</c:when>
											    		<c:when test="${not empty soList.soTitle && fn:length(soList.soTitle) <= 12}">
											    				${soList.soTitle}
											    		</c:when>
											    		<c:otherwise>&nbsp;</c:otherwise>
											</c:choose>
										</a>
									</div>
									<div id="soTitleDivs_${soList.soId}_${status.count}" class="tilteFlyOut">
										<div style="padding-left: 5px; margin: 3px;" id="titleName">${soList.soTitle}
										</div>
									</div>
									${soList.soId}
								</c:otherwise>
								</c:choose>
							</td>
							<td class="omColumnCust" width="11%" style="word-wrap: break-word">
								<div style="margin-left:5px;">
									<div class="capitalizeDiv">${soList.endCustomerFirstName}&nbsp;<span class="lastNameBold">${soList.endCustomerLastName}</span></div>
									    <i class="icon-phone" rowNum="${status.count}"></i>&nbsp;${soList.endCustomerPrimaryPhoneNumber}<br/>
									      <c:if test="${not empty soList.primaryExtension}">
									       Ex.  ${soList.primaryExtension}<br/>
									       </c:if>
									 <c:if test="${not empty soList.endCustomerAlternatePhoneNumber}">
							            <i class="icon-mobile-phone" rowNum="${status.count}"></i>&nbsp;&nbsp; ${soList.endCustomerAlternatePhoneNumber}<br/>
									      <c:if test="${not empty soList.alternateExtension}">
									       Ex.  ${soList.alternateExtension}<br/>
									      </c:if>
									</c:if>
								</div>
							</td>
							<td class="omColumn4" width="12%">
							   <div class="locationDiv" style="padding-left:5px;">
								${soList.street1}, ${soList.street2}<br /> ${soList.city}<br />
								${soList.state}, ${soList.zip} <c:set var="locn"
									value="${soList.city},${soList.zip}"></c:set>
							 </div>
							</td>
							<td class="omColumn5" width="15%">
							<c:choose>
							 <c:when test="${soList.resheduleStartDateString == null || soList.resheduleStartDateString == ''}">
								<div  class="alignDiv" style="padding-left:15px;">
								<c:choose>
								 <c:when test="${soList.appointEndDate == null || soList.appointEndDate == ''}">
										     ${soList.appointStartDate} ${soList.serviceTimeStart}
								 </c:when>
								 <c:otherwise>
									         ${soList.appointStartDate} to ${soList.appointEndDate}<br/>
									         ${soList.serviceTimeStart} to ${soList.serviceTimeEnd}
							     </c:otherwise> 
							     </c:choose>
								<br/>(${soList.serviceLocationTimezone})
								</div>
							 </c:when>
							 <c:otherwise>
							     <div class="resheduleDiv" style="padding-left:15px;color:#db3330;cursor:help;" title="Reshedule Pending" alt="Reshedule Pending">
							     	<c:choose>
							          <c:when test="${soList.appointEndDate == null || soList.appointEndDate == ''}">
										     ${soList.appointStartDate} ${soList.serviceTimeStart}
								      </c:when>
								      <c:otherwise>
									         ${soList.appointStartDate} to ${soList.appointEndDate}<br/>
									         ${soList.serviceTimeStart} to ${soList.serviceTimeEnd}
							          </c:otherwise>
							          </c:choose> 
								      <br/>(${soList.serviceLocationTimezone})
							  </div>
						  </c:otherwise>
						  </c:choose>
							</td>
							<td class="omColumn6" width="9%">
							<div style="padding-left:15px;">
							<c:forEach var="scope" items="${soList.scope}" varStatus="loop">
					 				<c:out value="${fn:trim(scope.serviceType)}"></c:out><c:if test="${fn:length(soList.scope)>1 && !loop.last}">,<br/></c:if>
							</c:forEach></div></td>
						</c:when> 
						<c:when test="${omDisplayTab == 'Resolve Problem'}">
							<td class="omColumn1" width="8%" style="padding-left: 5px;">
							              &nbsp;${soList.problemReportedBy}&nbsp;
								<fmt:formatDate value="${soList.problemReportedDate}" pattern="MM/dd/yyyy" /> &nbsp;&nbsp;<br />
								                       
							</td>
							<td class="omColumn2" width="11%">${soList.problemType}</td>
							<td class="omColumn3" width="10%">
								<c:choose>
								<c:when test="${soList.parentGroupId != null}">					  			
					  				<div style="padding-right: 3px">
										<a href="javascript:void(0);" class="soTitleLink"
											id="soTitle${soList.parentGroupId}_${status.count}"
											onmouseover="showTitle(this);"
											onmouseout="hideTitle(this);" onclick="displaySODetails('${soList.parentGroupId}','1','${soList.routedResourceId}');">
											<c:choose>
											    		<c:when test="${not empty soList.parentGroupTitle && fn:length(soList.parentGroupTitle) > 12}">
											    				${fn:substring(soList.parentGroupTitle,	0, 12)}...
											    		</c:when>
											    		<c:when test="${not empty soList.parentGroupTitle  && fn:length(soList.parentGroupTitle ) <= 12}">
											    				${soList.parentGroupTitle}
											    		</c:when>
											    		<c:otherwise>&nbsp;</c:otherwise>
											</c:choose>
										</a>
									</div>
									<div id="soTitleDivs_${soList.parentGroupId}_${status.count}" class="tilteFlyOut">
										<div style="padding-left: 5px; margin: 3px;" id="titleName">${soList.parentGroupTitle}
										</div>
									</div>
										${soList.parentGroupId}
					   			</c:when> 
							   	<c:otherwise>							   	
									<div style="padding-right: 3px">
										<a href="javascript:void(0);" class="soTitleLink"
											id="soTitle${soList.soId}_${status.count}"
											onmouseover="showTitle(this);" onmouseout="hideTitle(this);" onclick="displaySODetails('${soList.soId}','0','${soList.routedResourceId}');">
											<c:choose>
											    		<c:when test="${not empty soList.soTitle && fn:length(soList.soTitle) > 12}">
											    				${fn:substring(soList.soTitle,	0, 12)}...
											    		</c:when>
											    		<c:when test="${not empty soList.soTitle && fn:length(soList.soTitle) <= 12}">
											    				${soList.soTitle}
											    		</c:when>
											    		<c:otherwise>&nbsp;</c:otherwise>
											</c:choose>
										</a>
									</div>
									<div id="soTitleDivs_${soList.soId}_${status.count}" class="tilteFlyOut">
										<div style="padding-left: 5px; margin: 3px;" id="titleName">${soList.soTitle}
										</div>
									</div>
										${soList.soId}
								</c:otherwise>
								</c:choose>
							</td>
							<td class="omColumn4" width="9%" style="word-wrap: break-word">
								<div  class="customerDiv "style="margin-left:0px;">
									<div class="capitalizeDiv">${soList.endCustomerFirstName}&nbsp;<span class="lastNameBold">${soList.endCustomerLastName}</span></div>
									    <i class="icon-phone" rowNum="${status.count}"></i>&nbsp;${soList.endCustomerPrimaryPhoneNumber}<br/>
									      <c:if test="${not empty soList.primaryExtension}">
									       Ex.  ${soList.primaryExtension}<br/>
									       </c:if>
									 <c:if test="${not empty soList.endCustomerAlternatePhoneNumber}">
							            <i class="icon-mobile-phone" rowNum="${status.count}"></i>&nbsp;&nbsp; ${soList.endCustomerAlternatePhoneNumber}<br/>
									      <c:if test="${not empty soList.alternateExtension}">
									       Ex.  ${soList.alternateExtension}<br/>
									      </c:if>
									</c:if>
								</div>
							</td>
							<td class="omColumn5" width="13%">
								<div class="locationDiv" style="padding-left:5px;">${soList.street1}, ${soList.street2}<br /> ${soList.city}<br />
								${soList.state}, ${soList.zip}
								</div>
							</td>
							<td class="omColumn6" width="13%">
							<c:choose>
								 <c:when test="${soList.resheduleStartDateString == null || soList.resheduleStartDateString == ''}">
								<div  class="alignDiv" style="padding-left:10px;">
								<c:choose>
								 <c:when test="${soList.appointEndDate == null || soList.appointEndDate == ''}">
										     ${soList.appointStartDate} ${soList.serviceTimeStart}
								 </c:when>
								 <c:otherwise>
									         ${soList.appointStartDate} to ${soList.appointEndDate}<br/>
									         ${soList.serviceTimeStart} to ${soList.serviceTimeEnd}
							     </c:otherwise> 
							     </c:choose>
								       <br/>(${soList.serviceLocationTimezone})
								</div>
							 </c:when>
							 <c:otherwise>
							    <div class="resheduleDiv" style="padding-left:10px;color:#db3330;cursor:help;" title="Reshedule Pending" alt="Reshedule Pending">
							    <c:choose>
							     <c:when test="${soList.appointEndDate == null || soList.appointEndDate == ''}">
										     ${soList.appointStartDate} ${soList.serviceTimeStart}
								 </c:when>
								 <c:otherwise>
									         ${soList.appointStartDate} to ${soList.appointEndDate}<br/>
									         ${soList.serviceTimeStart} to ${soList.serviceTimeEnd}
							     </c:otherwise>
							     </c:choose> 
								<br/>(${soList.serviceLocationTimezone})
							  </div>
							 
						   </c:otherwise>
						   </c:choose>
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
							<td class="omColumn1"
								style="padding-left:5px; width:${apptWidth}%; text-align:left;">
								${soList.soStatusString}
									<c:choose>
									<c:when test="${soList.parentGroupId != null}">
										<div style="float: right;padding-bottom: 10px;">
										<i id='${status.count}_plus_icon' class="icon-plus-sign"
											style="color: #736AFF;cursor: pointer;font-size: 12px;"
											onclick="displayChildOrders('${soList.followUpFlag}', this.id, '${soList.soId}','${status.count}');"></i>
									  	<i id='${status.count}_close_icon' class="icon-folder-close"
											style="color: #E9AB17;cursor: pointer;font-size: 12px;" onclick="displayChildOrders('${soList.followUpFlag}', this.id, '${soList.soId}','${status.count}');"></i>
										</div>
									</c:when>
									<c:when test="${soList.priceModel == 'ZERO_PRICE_BID' && soList.soStatus == 110}">
									<div style="float: right;padding-bottom: 25px;">
										<i id='${status.count}_plus_icon' class="icon-plus-sign"
											style="color: #736AFF;cursor: pointer;font-size: 12px;"
											onclick="displayBidChildOrders('${soList.followUpFlag}', this.id, '${soList.soId}','${status.count}');"></i>
									  	<i id='${status.count}_close_icon' class="icon-folder-close"
											style="color: #E9AB17;cursor: pointer;font-size: 12px;" onclick="displayBidChildOrder('${soList.followUpFlag}', this.id, '${soList.soId}','${status.count}');"></i>
									</div>
									</c:when>
									</c:choose>
								<br/>
								<div id="soSubStaus${soList.soId}_${status.count}"
											onmouseover="showSoSubStaus(this);"
											onmouseout="hideSoSubStaus(this);">
								&nbsp;&nbsp;&nbsp;
								<c:choose>
								<c:when test="${soList.acceptanceMethod == 'AUTOMATIC' && soList.soStatus == 150 && soList.soSubStatusString == null}">Auto Accepte...</c:when> 
								<c:otherwise>
								<c:choose>
											    		<c:when test="${soList.soSubStatusString!=null && not empty soList.soSubStatusString && fn:length(soList.soSubStatusString) > 12}">
											    				${fn:substring(soList.soSubStatusString,	0, 12)}...
											    		</c:when>
											    		<c:when test="${soList.soSubStatusString!=null && not empty soList.soSubStatusString && fn:length(soList.soSubStatusString) <= 12}">
											    				${soList.soSubStatusString}
											    		</c:when>
											    		<c:otherwise>&nbsp;</c:otherwise>
											</c:choose>
							
								</c:otherwise>
								</c:choose>
							</div>
								<div id="soSubStausDivs_${soList.soId}_${status.count}" class="tilteFlyOut">
										<div style="padding-left: 5px; margin: 3px;" id="SubStatusName">
										<c:choose>
										<c:when test="${soList.acceptanceMethod == 'AUTOMATIC' && soList.soStatus == 150 && soList.soSubStatusString == null}">Auto Accepted</c:when>
										<c:otherwise>${soList.soSubStatusString}</c:otherwise>
										</c:choose>
										</div>
									</div>
								 <br />
							</td>
							<td class="omColumn2" width="10%">
							<c:choose>
								<c:when test="${soList.parentGroupId != null}">
					  				
					  				<div style="padding-right: 3px">
										<a href="javascript:void(0);" class="soTitleLink"
											id="soTitle${soList.parentGroupId}_${status.count}" 
											onmouseover="showTitle(this);"
											onmouseout="hideTitle(this);" onclick="displaySODetails('${soList.parentGroupId}','1','${soList.routedResourceId}');">
											<c:choose>
											    		<c:when test="${not empty soList.parentGroupTitle && fn:length(soList.parentGroupTitle) > 12}">
											    				${fn:substring(soList.parentGroupTitle,	0, 12)}...
											    		</c:when>
											    		<c:when test="${not empty soList.parentGroupTitle  && fn:length(soList.parentGroupTitle ) <= 12}">
											    				${soList.parentGroupTitle}
											    		</c:when>
											    		<c:otherwise>&nbsp;</c:otherwise>
											</c:choose>
										</a>
									</div>
									<div id="soTitleDivs_${soList.parentGroupId}_${status.count}" class="tilteFlyOut">
										<div style="padding-left: 5px; margin: 3px;" id="titleName">${soList.parentGroupTitle}
										</div>
									</div>
									${soList.parentGroupId}
					  			</c:when>
					  			<c:when test="${soList.priceModel == 'ZERO_PRICE_BID'}">
									
					  				<div style="padding-right: 3px">
										<a href="javascript:void(0);" class="soTitleLink"
											id="soTitle${soList.soId}_${status.count}"
											onmouseover="showTitle(this);"
											onmouseout="hideTitle(this); "
											onclick="displayBidChildOrders('${soList.followUpFlag}', this.id, '${soList.soId}','${status.count}');">
											<c:choose>
											    		<c:when test="${not empty soList.soTitle && fn:length(soList.soTitle) > 12}">
											    				${fn:substring(soList.soTitle,	0, 12)}...
											    		</c:when>
											    		<c:when test="${not empty soList.soTitle && fn:length(soList.soTitle) <= 12}">
											    				${soList.soTitle}
											    		</c:when>
											    		<c:otherwise>&nbsp;</c:otherwise>
											</c:choose>
										</a>
									</div>
									<div id="soTitleDivs_${soList.soId}_${status.count}" class="tilteFlyOut">
										<div style="padding-left: 5px; margin: 3px;" id="titleName">${soList.soTitle}
										</div>
									</div>
									${soList.soId}
								</c:when>
					  			<c:otherwise>
					  	 			
									<div style="padding-right: 3px">
										<a href="javascript:void(0);" class="soTitleLink"
											id="soTitle${soList.soId}_${status.count}"
											onmouseover="showTitle(this);" onmouseout="hideTitle(this);" onclick="displaySODetails('${soList.soId}','0','${soList.routedResourceId}');">
											<c:choose>
											    		<c:when test="${not empty soList.soTitle && fn:length(soList.soTitle) > 12}">
											    				${fn:substring(soList.soTitle,	0, 12)}...
											    		</c:when>
											    		<c:when test="${not empty soList.soTitle && fn:length(soList.soTitle) <= 12}">
											    				${soList.soTitle}
											    		</c:when>
											    		<c:otherwise>&nbsp;</c:otherwise>
											</c:choose>
										</a>
									</div>
									<div id="soTitleDivs_${soList.soId}_${status.count}" class="tilteFlyOut">
										<div style="padding-left: 5px; margin: 3px;" id="titleName">${soList.soTitle}
										</div>
									</div>
									${soList.soId}
								</c:otherwise>
								</c:choose>
							</td>
							<c:if test="${omDisplayTab == 'Schedule' || omDisplayTab == 'Confirm Appt window'}">
							  <td class="omColumnCust" width="9%" style="word-wrap: break-word">
								  <div style="padding-left:2px;">
									 <div class="capitalizeDiv">${soList.endCustomerFirstName}&nbsp;<span class="lastNameBold">${soList.endCustomerLastName}</span></div>
									    <i class="icon-phone" rowNum="${status.count}"></i>&nbsp;${soList.endCustomerPrimaryPhoneNumber}<br/>
									      <c:if test="${not empty soList.primaryExtension}">
									       Ex.  ${soList.primaryExtension}<br/>
									       </c:if>
									 <c:if test="${not empty soList.endCustomerAlternatePhoneNumber}">
							            <i class="icon-mobile-phone" rowNum="${status.count}"></i>&nbsp;&nbsp;${soList.endCustomerAlternatePhoneNumber}<br/>
									      <c:if test="${not empty soList.alternateExtension}">
									       Ex.  ${soList.alternateExtension}<br/>
									      </c:if>
									 </c:if>
								 </div>
							 </td>
							</c:if>
							<td class="omColumn3" width="<c:choose><c:when test="${omDisplayTab == 'Confirm Appt window'}">11%</c:when><c:otherwise>11%</c:otherwise></c:choose>;">
								<div  class="locationDiv"style="padding-left:15px;">
								<c:if test="${(soList.soStatus != 110)}">
									${soList.street1}, ${soList.street2}<br />
								</c:if> 
								${soList.city}<br />
								${soList.state}, ${soList.zip}
								</div>
							</td>
							<td class="omColumn4" width="<c:choose><c:when test="${omDisplayTab == 'Confirm Appt window'}">13%</c:when><c:otherwise>15%</c:otherwise></c:choose>;">
							<c:choose>
							 <c:when test="${soList.resheduleStartDateString == null || soList.resheduleStartDateString == ''}">
								<div  class="alignDiv" style="padding-left:10px;">
								<c:choose>
								 <c:when test="${soList.appointEndDate == null || soList.appointEndDate == ''}">
										     ${soList.appointStartDate} ${soList.serviceTimeStart}
								 </c:when>
								 <c:otherwise>
									${soList.appointStartDate} to ${soList.appointEndDate}<br/>
									${soList.serviceTimeStart} to ${soList.serviceTimeEnd}
							     </c:otherwise>
							     </c:choose> 
								<br/>(${soList.serviceLocationTimezone})
								</div>
							 </c:when>
							 <c:otherwise>
							  <div class="resheduleDiv" style="padding-left:10px;color:#db3330;cursor:help;" title="Reshedule Pending" alt="Reshedule Pending">
							  	<c:choose>
							     <c:when test="${soList.appointEndDate == null || soList.appointEndDate == ''}">
										     ${soList.appointStartDate} ${soList.serviceTimeStart}
								 </c:when>
								 <c:otherwise>
									${soList.appointStartDate} to ${soList.appointEndDate}<br/>
									${soList.serviceTimeStart} to ${soList.serviceTimeEnd}
							     </c:otherwise>
							     </c:choose> 
								<br/>(${soList.serviceLocationTimezone})
							  </div>
						    </c:otherwise>
						    </c:choose>
							</td>
							<td class="omColumn5" width="<c:choose><c:when test="${omDisplayTab == 'Confirm Appt window'}">9%</c:when><c:otherwise>11%</c:otherwise></c:choose>;" style="word-wrap: break-word;">
								<div class="sheduleDiv" style="max-width: 100px;word-wrap:break-word; padding-left:20px;">
								
								<c:choose>
								<c:when
									test="${soList.scheduleStatus == null || soList.scheduleStatus == ''}">
								Not Applicable
								</c:when> 
								<c:otherwise>		
											${soList.scheduleStatus}
																			
								</c:otherwise>
								</c:choose>
								</div>	
								<div style="padding-left: 7px;">
								<c:if test="${soList.preCallAttemptedDate!=null && soList.scheduleStatus == 'Pre-Call Attempted'}">
									&nbsp;(<fmt:formatDate value="${soList.preCallAttemptedDate}"
										pattern="MM/dd/yyyy" />)<br />
									</c:if>	
								</div>
							</td>
							<c:if test="${omDisplayTab == 'Inbox' || omDisplayTab == 'Job Done' || omDisplayTab == 'Revisit Needed'}">
							   <td class="omColumn6" width="9%">
								   <div style="margin-left:5px;">
								      <c:forEach var="scope" items="${soList.scope}" varStatus="loop">
						 			       ${fn:trim(scope.serviceType)}<c:if test="${fn:length(soList.scope)>1 && !loop.last}">,<br /></c:if>
								      </c:forEach>
								    </div>
							   </td>
							</c:if>
							</c:otherwise>
							</c:choose>					
						<td class="omColumn7" 
							<c:choose>
							<c:when test="${omDisplayTab == 'Respond'}">
								width="11%"
							</c:when>
							<c:otherwise>
								 width="11%"
							</c:otherwise>
							</c:choose>
						>
							<!-- For recieved status  --> 
							<div class="providerDiv" style="padding-left: 10px;">
							<c:if test="${soList.soStatus == 110}">
								<c:choose>
								<c:when test="${fn:length(soList.routedProviders)>1}">
									${fn:length(soList.routedProviders)} Providers
								</c:when>
								<c:otherwise>
									<c:forEach var="routedProviders"
										items="${soList.routedProviders}">
										<a href="javascript:void(0);" style="text-decoration: none;color: black;"
												onclick="openProviderProfile('${routedProviders.id}','${soList.vendorId}');">${routedProviders.firstName} ${routedProviders.lastName}
												</a>
									</c:forEach>
								</c:otherwise>
								</c:choose>
							</c:if> 
							
							<!-- For statuses other than received  --> 
							<c:if test="${soList.soStatus > 110}">
								<c:choose>
								<c:when test="${soList.assignmentType == 'FIRM'}">
									Unassigned
									<p>
										<a href="javascript:void(0);"  id="assignProviderLink" class="assignPro"
											onclick="loadAssignProvider(null,this.id,'${soList.soId}');">Assign</a>
									</p>
								</c:when>
								<c:otherwise>
									<c:forEach var="routedProviders"
										items="${soList.routedProviders}">
										<c:if test="${routedProviders.respId == 1 && not empty soList.acceptedResourceId && soList.acceptedResourceId == routedProviders.id}">
											<a href="javascript:void(0);"  style="text-decoration: none;color: black;"
												onclick="openProviderProfile('${routedProviders.id}','${soList.vendorId}');">${routedProviders.firstName} ${routedProviders.lastName}
												</a>
											<c:if test="${fn:length(soList.routedProviders)>1}">
												<c:if test="${(soList.soStatus != 160) && (soList.soStatus != 165) && not (soList.soStatus == 155 && soList.soSubStatus == 8)}">
												<p>
													<a href="javascript:void(0);"  id="reassignProviderLink" class="assignPro"
														onclick="loadAssignProvider(null,this.id,'${soList.soId}');">Re-assign</a>
												</p>
											</c:if>
											</c:if>
										</c:if>
									</c:forEach>
								</c:otherwise>
								</c:choose>
							</c:if>
							</div>
						</td>
						<td class="omColumn8"
						<c:choose>
						  <c:when test="${omDisplayTab == 'Respond'}">
								width="5%"
							</c:when>
							<c:when test="${omDisplayTab == 'Confirm Appt window'}">
								width="11%"
							</c:when>
							<c:otherwise>
								 width="5%"
							</c:otherwise>
						</c:choose>
						>
						<c:choose>
						<c:when test="${omDisplayTab == 'Confirm Appt window'}">
								<c:set var="partStreet1" value="${fn:trim(soList.partStreet1)}"/>
								<c:choose>
								<c:when test="${not empty partStreet1}">
									${soList.partStreet1}<br />${soList.partStreet2},
									<div>${soList.partCity},</div>
									<div>${soList.partState} ${soList.partZip}</div>
									<c:if test="${not empty soList.availabilityDate}">
										<div>(${soList.availabilityDate})</div>
									</c:if>
								</c:when>
								<c:otherwise>
									Not Applicable
								</c:otherwise>
								</c:choose>
							</c:when> 
							<c:otherwise>
								<div class="flagDiv" style="padding-left: 15px;">
								<c:if test="${soList.followUpFlag=='0'}">
									<i id='flag_${status.count}' class="icon-flag off"
										onclick="setSoPriority(this.id, '${so_id}', '${groupInd}');"></i>
								</c:if>
								<c:if test="${soList.followUpFlag=='1'}">
									<i id='flag_${status.count}' class="icon-flag on"
										onclick="setSoPriority(this.id, '${so_id}', '${groupInd}');"></i>
								</c:if>
								</div>
							</c:otherwise>
							</c:choose>
						</td>
						<c:choose>
						<c:when test="${omDisplayTab == 'Confirm Appt window'}">
							<td class="omColumn9" style="width: 5%;padding-left:3px;">
								<div style="padding-left: 15px;">
									<c:if test="${soList.followUpFlag=='0'}">
											<i id='flag_${status.count}' class="icon-flag off"
												onclick="setSoPriority(this.id, '${so_id}', '${groupInd}');"></i>
									</c:if>
									<c:if test="${soList.followUpFlag=='1'}">
											<i id='flag_${status.count}' class="icon-flag on"
												onclick="setSoPriority(this.id, '${so_id}', '${groupInd}');"></i>
									</c:if>
								</div>
							</td>
						</c:when>
						<c:when test="${omTabDTO.viewOrderPricing == true}">
							<td class="omColumn9" style="width: 6%;padding-left:17px;">
							<c:choose>
							<c:when test="${soList.parentGroupId != null}">
								<fmt:formatNumber type="currency" currencySymbol="$"
											value="${soList.groupSpendLimitLabor+soList.groupSpendLimitParts}" />
							</c:when>
							<c:when test="${soList.priceModel == 'ZERO_PRICE_BID' && soList.soStatus == 110}">
							<fmt:formatNumber type="currency" currencySymbol="$"
											value="${soList.bidMinSpendLimit}" />&nbsp;-&nbsp;
											<fmt:formatNumber type="currency" currencySymbol="$"
											value="${soList.bidMaxSpendLimit}" />
							</c:when>
							<c:otherwise>
								<c:choose>
								<c:when test="${soList.soStatus == 160 || soList.soStatus == 180 || soList.soStatus == 120}">
										<fmt:formatNumber type="currency" currencySymbol="$"
											value="${soList.finalPartsPrice+soList.finalLaborPrice}" />
								</c:when>
								<c:otherwise>
										<fmt:formatNumber type="currency" currencySymbol="$"
											value="${soList.spendLimit+soList.spendLimitParts}" />									
								</c:otherwise>
								</c:choose>
							</c:otherwise>
							</c:choose>
							</td>
						</c:when>
						</c:choose>
						<td class="omColumn10" width="12%" style="padding-left: 15px;">
						<c:choose>
							<c:when test="${fn:length(soList.actions) > 1}">
								<c:set var="actionMenu" value="<a class='action dropdown'>
								<i class='icon-caret-down'></i></a><ul class='dropdown-menu' style='display: none;'>"></c:set>
								<c:set var="btnCss" value=""></c:set>
							</c:when>
							<c:when test="${fn:length(soList.actions) == 1}">
								<c:set var="actionMenu" value=""></c:set>
								<c:set var="btnCss" value="btnCss"></c:set>
							</c:when> 
							<c:otherwise>
								<c:set var="actionMenu" value=""></c:set>
								<c:set var="btnCss" value="btnCss"></c:set>
							</c:otherwise>
							</c:choose>
						
							<div class="btn-group" <c:choose><c:when test="${omDisplayTab == 'Confirm Appt window'}"> style="padding-left:10px;" </c:when><c:otherwise> style="margin-left:-6px;" </c:otherwise></c:choose>>
							<c:set var="actionCount" value="0"></c:set>
							<c:forEach var="actions" items="${soList.actions}">
							<c:set var="actionCount" value="${actionCount+1}"></c:set>
								<c:if test="${actions == 'Place Bid'}">
									<a class="action ${btnCss}" onclick="loadPlaceBid('${soList.soId}');" href="javascript:void(0);">${actions}</a>
									${actionMenu}
								</c:if>
								<c:if test="${actions == 'View Order'}">
									<c:choose>
										<c:when test="${omDisplayTab == 'Revisit Needed'}">
											<a class="action ${btnCss}" onclick="loadTimeOnSite('${soList.soId}');" href="javascript:void(0);">${actions}</a>
										${actionMenu}
										</c:when>
										<c:otherwise>
										<a class="action ${btnCss}" onclick="loadTakeAction('${soList.soId}');" href="javascript:void(0);">${actions}</a>
										${actionMenu}
										</c:otherwise>
									</c:choose>
								</c:if>
								<c:if test="${actions == 'Take Action'}">
								<c:choose>
								<c:when test="${soList.priceModel == 'ZERO_PRICE_BID' && soList.soStatus == 110}">
									<a class="action ${btnCss}" onclick="displayBidChildOrders('${soList.followUpFlag}', this.id, '${soList.soId}','${status.count}');" href="javascript:void(0);">Take Action</a>
									<a class="action dropdown" onclick="displayBidChildOrders('${soList.followUpFlag}', this.id, '${soList.soId}','${status.count}');" href="javascript:void(0);">
									<i class='icon-caret-down'></i>
									</a>
								</c:when>
								<c:otherwise>
									<a class="action ${btnCss}" onclick="loadTakeAction('${soList.soId}');" href="javascript:void(0);">${actions}</a>
										${actionMenu}
								</c:otherwise>
								</c:choose>
								</c:if>
								<c:if test="${actions == 'Assign Provider'}">
									<a class="action ${btnCss}" onclick="loadAssignProvider(null,this.id,'${soList.soId}');" href="javascript:void(0);">${actions}</a>
										${actionMenu}
								</c:if>
								<c:if test="${actions == 'Issue Resolution'}">
									<a class="action ${btnCss}" onclick="loadIssueResolution('${soList.soId}');" href="javascript:void(0);">${actions}</a>
										${actionMenu}
								</c:if>
								<c:if test="${actions == 'Completion Record'}">
									<a class="action ${btnCss}" onclick="loadCompletionRecord('${soList.soId}');" href="javascript:void(0);">${actions}</a>
										${actionMenu}
								</c:if>
								<c:if test="${actions == 'Accept Button'}">
									<a class="action ${btnCss}" onclick="loadAcceptOrder('${so_id}','${groupInd}');" href="javascript:void(0);">Accept</a>
										${actionMenu}
								</c:if>
								<!-- SL-21645 start-->
								<c:if test="${actions == 'Add Estimate'}">
									<a class="action ${btnCss}" onclick="displayAddEstimateSOPopUp('${so_id}','${soList.estimationId}');" href="javascript:void(0);">ADD ESTIMATE</a>
										${actionMenu}
								</c:if>
								
								<c:if test="${actions == 'View/Edit Estimate'}">
									<a class="action ${btnCss}" onclick="displayAddEstimateSOPopUp('${so_id}','${soList.estimationId}');" href="javascript:void(0);">EDIT ESTIMATE</a>
										${actionMenu}
								</c:if>
								<!-- SL-21645 end -->
								
								<c:if test="${actions == 'Pre-Call'}">
									<a class="action ${btnCss}" onclick="loadPreCall('${soList.soId}');" href="javascript:void(0);">${actions}</a>
										${actionMenu}
								</c:if>
								<c:if test="${actions == 'Confirm Appt Window'}">
									<a class="action ${btnCss}" onclick="loadConfirmAppointment('${soList.soId}');" href="javascript:void(0);">Confirm Appt.</a>
										${actionMenu}
								</c:if>
								<c:if test="${actions == 'Complete for Payment'}">
									<a id="completeForPayment" class="action ${btnCss}" onclick="loadCompleteForPayment('${soList.soId}');" href="javascript:void(0);">Complete Order</a>
										${actionMenu}
								</c:if>
								
								<c:if test="${actions == 'Add Note'}">
									<c:choose>
									<c:when test="${omDisplayTab == 'Inbox' && soList.soStatusString == 'Active' && (empty soList.soSubStatusString || soList.soSubStatusString != 'Job Done') && actionCount==1}">
										<a id="addNoteLink_${soList.soId}" class="action ${btnCss}" onclick="loadAddNote(event,'${soList.soId}');" href="javascript:void(0);">${actions}</a>
											${actionMenu}
									</c:when>
									<c:otherwise>
										<li>
											<a class="link" style="text-align: left;" href="javascript:void(0);"
												onclick="loadAddNote(event,'${soList.soId}');"
												id="addNoteLink_${soList.soId}">${actions}</a>
										</li>
									</c:otherwise>
									</c:choose>
								</c:if>
								<c:if test="${actions == 'Request a Reschedule'}">
									<c:choose>
									<c:when test="${soList.reSchedStartDate==null}">
									<li>
										<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadRequestaReshedule(event,'${soList.soId}');"
											id="rescheduleLink_${soList.soId}">Request a Reschedule</a>
									</li>
									</c:when>
									<c:otherwise>
									<c:choose>
									<c:when test="${soList.rescheduleRole==3}">
									<li>
											<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadRejectReSchedule('${soList.soId}','${soList.acceptedResourceId}','acceptReschedule');">Accept Reschedule Request</a>
									</li>
									<li>
											<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadRejectReSchedule('${soList.soId}','${soList.acceptedResourceId}','rejectReschedule');">Reject Reschedule Request</a>
									</li>
									</c:when>	
									<c:otherwise>
									<li>
											<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadRejectReSchedule('${soList.soId}','${soList.acceptedResourceId}','cancelReschedule');">Cancel Reschedule Request</a>
									</li>
									<li>
											<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadRequestaReshedule(event,'${soList.soId}');"
											id="rescheduleLink_${soList.soId}">Edit Reschedule Request</a>
									</li>
									</c:otherwise>
									</c:choose>
									</c:otherwise>
									</c:choose>
								</c:if>
								 
								<c:if test="${actions == 'Edit Service Location Notes'}">
									<li>
										<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadEditServiceLocationNotes(event,'${soList.soId}');"
											id="editLocnNotesLink_${soList.soId}">Edit Service Location Notes</a>
									</li>
								</c:if>
								
								<c:if test="${actions == 'Time on Site'}">
									<li>
										<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadTimeOnSite('${soList.soId}');">Time on Site</a>
									</li>
								</c:if>
								<c:if test="${actions == 'Report a Problem'}">
									<li>
										<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadReportAProblem('${soList.soId}');">Report a
											Problem</a>
									</li>
								</c:if>
								<c:if test="${actions == 'Counter offer'}">
									<li>
										<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadCounterOffer('${soList.soId}','${soList.parentGroupId}');">Counter
											Offer</a>
									</li>
								</c:if>
								<c:if test="${actions == 'Withdraw offer'}">
									<c:choose>
									<c:when test="${fn:length(soList.actions) > 1}">
										<li>
											<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadCounterOffer('${soList.soId}','${soList.parentGroupId}');">Withdraw offer</a>
										</li>
									</c:when>
									<c:otherwise>
										<a class="action btnCss" onclick="loadCounterOffer('${soList.soId}','${soList.parentGroupId}');">${actions}</a>
									</c:otherwise>
									</c:choose>
								</c:if>
								<c:if test="${actions == 'Reject Order'}">
									<c:if test="${soList.priceModel != 'ZERO_PRICE_BID'}">
									
									<li>
										<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadRejectOrder('${so_id}', '${groupInd}',0,0);">Reject
											Order</a>
									</li>
									</c:if>
								</c:if>
								<c:if test="${actions == 'Update Service Window'}">
									<li>
										<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadUpdateTime('${soList.soId}', '${groupInd}');">Update Service Window</a>
									</li>
								</c:if>
							</c:forEach>
							<c:if test="${fn:length(soList.actions) > 1}">
								  </ul>
							</c:if>
							</div>
						</td>
					</tr>
				</table>
				
				<!-- Displaying child orders -->
				<c:if test="${soList.parentGroupId != null}">
				<c:choose>
					<c:when test="${omDisplayTab == 'Respond'}">
						<c:set var="margin" value="165"></c:set>
						<c:set var="locationHdr" value="Pickup/<br />Product Location"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="margin" value="90"></c:set>
						<c:set var="locationHdr" value="Service Location"></c:set>
					</c:otherwise>
				</c:choose>
					
					<div id="childs_${status.count}" style="display: none;margin:2px 2px 2px ${margin}px;" class="child_so_main treeview">
						<ul class="">
							<li>
								<div style="padding-left: 20px;">
									<div class="group-hdr-background" style="padding: 5px; font-weight: bold;width: 549px;">Grouped Order</div>
									<table class="group-so-hdr" style="border-left:  #EEEEEE;border-right: #EEEEEE;background: #88B9E0;" >
										<tr>
											<td >
												<div style="float: left;width:150px;padding-left: 50px;">Title/SO ID</div>
												<div style="float: left;width:155px;">Service Appointment</div>
												<div style="float: left;width:200px;">${locationHdr}</div>
											</td>
										</tr>
									</table>
								</div>
							</li>
							<c:forEach items="${soList.childOrderList.childOrder}" var="childOrder" varStatus="childStat">
								<li style= <c:if test="${childStat.last}">"background-position: 0 -1755px;"</c:if>>
									<div style="float: left;padding-top: 5px;padding-top: 12px;color: #707071;">
									<i class="icon-file" style="font-size: 15px;"></i> </div>
									<div>
									<table style='table-layout: fixed;border-left: #EEEEEE;border-right: #EEEEEE;'>
										<tr class="expand_child" id="child_tr_${childOrder.soId}">
											<td style="vertical-align: top;color: #707071" width="50px" >
												<i class="icon-caret-right collapse_child" id="expand_child_so_{soList.soId}" style=";font-size: 25px;padding-left: 3px;cursor: pointer;padding-top: 5px;"></i>
											</td>
											<td width="150px" class="child_title" >
												<div style="padding-right: 3px">
													<a href="javascript:void(0);"  class="soTitleLink"
														id="soTitle${childOrder.soId}_${status.count}"
														onmouseover="showTitle(this);"
														onmouseout="hideTitle(this);" onclick="displaySODetails('${childOrder.soId}','0','${soList.routedResourceId}');">
														<c:choose>
												    		<c:when test="${not empty childOrder.soTitle && fn:length(childOrder.soTitle) > 12}">
												    				${fn:substring(childOrder.soTitle,	0, 12)}...
												    		</c:when>
												    		<c:when test="${not empty childOrder.soTitle && fn:length(childOrder.soTitle) <= 12}">
												    				${childOrder.soTitle}
												    		</c:when>
											    			<c:otherwise>&nbsp;</c:otherwise>
														</c:choose>
													</a>
												</div>
												<div id="soTitleDivs_${childOrder.soId}_${status.count}" class="tilteFlyOut">
													<div style="padding-left: 5px; margin: 3px;" id="titleName">${childOrder.soTitle}
													</div>
												</div>
												<div class="child_tilte_more" id="child_tilte_more_${childOrder.soId}" style="display: none;padding-top: 3px;">
													<c:choose>
											    		<c:when test="${not empty childOrder.soTitleDesc && fn:length(childOrder.soTitleDesc) > 100}">
											    				${fn:substring(childOrder.soTitleDesc,0,100)} <strong>...</strong><br/><strong>... more info available</strong>
											    		</c:when>
											    		<c:when test="${not empty childOrder.soTitleDesc&& fn:length(childOrder.soTitleDesc) <= 100}">
											    				${childOrder.soTitleDesc}
											    		</c:when>
											    		<c:otherwise>&nbsp;</c:otherwise>
											    	</c:choose>
												</div>
												${childOrder.soId}
											</td>
											<td width="150px">
											<c:choose>
												<c:when test="${childOrder.appointEndDate == null || childOrder.appointEndDate == ''}">
													${childOrder.appointStartDate} ${childOrder.appointStartDate}
											</c:when>
											<c:otherwise>
												${childOrder.appointStartDate} to ${childOrder.appointEndDate}<br />
												${childOrder.serviceTimeStart} to ${childOrder.serviceTimeEnd}
											</c:otherwise> 
											</c:choose>
											<br />(${childOrder.serviceLocationTimezone}) 
											</td>
											<td width="200px">
											<c:choose>
												<c:when test="${omDisplayTab == 'Respond'}">
													 <c:set var="partStreet1" value="${fn:trim(childOrder.partStreet1)}"/>
													 <c:choose>
													<c:when test="${not empty partStreet1}">
														${partStreet1}<br />${childOrder.partStreet2},
														<div>${soList.partCity},</div>
														<div>${soList.partState} ${soList.partZip}</div>
														<c:if test="${not empty soList.availabilityDate}">
															<div>(${soList.availabilityDate})</div>
														</c:if>
													</c:when>
													<c:otherwise>
														Not Applicable
													</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													${childOrder.street1}, ${childOrder.street2}<br /> 
													${childOrder.city}<br />
													${childOrder.state}, ${childOrder.zip}
												</c:otherwise>
											</c:choose>
											</td>
										</tr>
									</table>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</c:if>
<!-- 				//bid child orders
 -->				
 <c:if test="${soList.priceModel == 'ZERO_PRICE_BID'}">
 				<c:choose>
					<c:when test="${omDisplayTab == 'Respond'}">
						<c:set var="margin" value="150"></c:set>
						<c:set var="locationHdr" value="Pickup/<br />Product Location"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="margin" value="90"></c:set>
						<c:set var="locationHdr" value="Service Location"></c:set>
					</c:otherwise>
					</c:choose>

					<div id="bidChilds_${status.count}"
						style="display: none;margin:2px 2px 2px ${margin}px;"
						class="child_so_main treeview">
						<ul class="">
							<li>
								<div style="padding-left: 20px;">
									<div style="padding: 5px; font-weight: bold; width: 695px;"
										class="group-hdr-background">Bid Order</div>
									<table class="group-so-hdr" width="705px;"
										style="border-left: #EEEEEE; border-right: #EEEEEE; background: #88B9E0">
										<tr>
											<td>
												<div style="float: left; width: 100px; padding-left:70px;">Title/SO ID</div>											</td>
											<td>
												<div style="width: 130px; padding-left:5px;">Posting Expiration</div>
											</td>
											<td>
												<div style="width: 90px; padding-left: 45px;">Provider</div>
											</td>
											<td>
												<div style="float: left; width: 75px; padding-left: 25px;">Your
													Bid</div>
											</td>
											<td>
												<div style="width: 100px; padding-left: 45px;">Actions</div>
											</td>
										</tr>
									</table>
								</div>
							</li>
							<c:forEach items="${soList.childBidSoList.childBidOrder}"
								var="childBidOrder" varStatus="childStat">
								<li
									style=<c:if test="${childStat.last}">"background-position: 0 -1755px;"</c:if>>
									<div
										style="float: left; padding-top: 5px; padding-top: 12px; color: #707071;">
										<i class="icon-file" style="font-size: 15px;"></i>
									</div>
									<div>
										<table
											style='table-layout: fixed;border-left: #EEEEEE;border-right: #EEEEEE;'>
											<tr >
												<td  class="expand_child" id="child_tr_${childBidOrder.soId}_${childStat.count+1}" style="vertical-align: top; color: #707071" width="50px">
													<i class="icon-caret-right collapse_child"
													id="expand_child_so_${childBidOrder.soId}_${childStat.count+1}"
													style="font-size: 25px; padding-left: 3px; cursor: pointer; padding-top: 5px;"></i>
												</td>
												<td width="118px" class="child_title" style="float: left;">
													<div style="padding-right: 3px">
														<a href="javascript:void(0);"  class="soTitleLink"
															id="soTitle${childBidOrder.soId}_${childStat.count+1}"
															onmouseover="showTitle(this);"
															onmouseout="hideTitle(this);"
															onclick="displaySODetails('${childBidOrder.soId}','0','${childBidOrder.resourceId}');">
															<c:choose>
																<c:when
																	test="${not empty childBidOrder.soTitle && fn:length(childBidOrder.soTitle) > 12}">
												    				${fn:substring(childBidOrder.soTitle,	0, 12)}...
												    		</c:when>
																<c:when
																	test="${not empty childBidOrder.soTitle && fn:length(childBidOrder.soTitle) <= 12}">
												    				${childBidOrder.soTitle}
												    		</c:when>
																<c:otherwise>&nbsp;</c:otherwise>
															</c:choose>
														</a>
													</div>
													<div id="soTitleDivs_${childBidOrder.soId}_${childStat.count+1}"
														class="tilteFlyOut">
														<div style="padding-left: 5px; margin: 3px;"
															id="titleName">${childBidOrder.soTitle}</div>
													</div>
													<div class="child_tilte_more"
														id="child_tilte_more_${childBidOrder.soId}_${childStat.count+1}"
														style="display: none; padding-top: 3px;">
														<c:choose>
															<c:when
																test="${not empty childBidOrder.soTitleDesc && fn:length(childBidOrder.soTitleDesc) > 100}">
											    				${fn:substring(childBidOrder.soTitleDesc,0,100)} <strong>...</strong>
																<br />
																<strong>... more info available</strong>
															</c:when>
															<c:when
																test="${not empty childBidOrder.soTitleDesc&& fn:length(childBidOrder.soTitleDesc) <= 100}">
											    				${childBidOrder.soTitleDesc}
											    		</c:when>
															<c:otherwise>&nbsp;</c:otherwise>
														</c:choose>
													</div>
													${childBidOrder.soId}
												</td>
												<td width="130px;" style="padding-left:5px;">
												<c:choose>
												<c:when  test="${childBidOrder.respId == 2}">
													<c:choose>
													<c:when test="${childBidOrder.offerExpirationDate!=null}">
													${childBidOrder.formattedExpirationDate}
													<br />(${soList.serviceLocationTimezone})
													</c:when>
													<c:otherwise>
													&nbsp;
													</c:otherwise>
													</c:choose>
													</c:when>
													<c:otherwise>
													&nbsp;
													</c:otherwise>
													</c:choose>
													</td>
												<td width="100px;" style="padding-left: 45px;">
														<a href="javascript:void(0);"  style="text-decoration: none; text-align: center;color: black;"
															onclick="openProviderProfile('${childBidOrder.resourceId}','${soList.vendorId}');">${childBidOrder.providerFirstName}
															${childBidOrder.providerLastName} </a>
												</td>
												<td width="75px;" style="float: left; padding-left: 15px;">
														<c:choose>
														<c:when test="${childBidOrder.spendLimit!=null}">
														<fmt:formatNumber type="currency" currencySymbol="$"
																value="${childBidOrder.spendLimit}" />
														</c:when>
														<c:otherwise>
														&nbsp;
														</c:otherwise>
														</c:choose>
												</td>
												<td width="120px;" style="padding-left: 25px;">
													<div class="btn-group">
														
														<c:if
															test="${childBidOrder.respId == null}">
															<a class="action ${btnCss}" style="text-decoration: none;"
																onclick="loadPlaceBid('${childBidOrder.soId}','${childBidOrder.resourceId}');">Place Bid</a>
														</c:if>
														<c:if  test="${childBidOrder.respId == 2}">
															<a class="action ${btnCss}" style="text-decoration: none;"
																onclick="loadPlaceBid('${childBidOrder.soId}','${childBidOrder.resourceId}');">Change Bid</a>
																
														</c:if>
														${actionMenu}
														<li style="background: none;"><a class="link" style="text-align: left;"
																onclick="loadRejectOrder('${so_id}', '${groupInd}','1','${childBidOrder.resourceId}');">Reject
																	Order</a></li>
														</ul>
													</div>
													</td>
												<td><a
													href="soDetailsController.action?soId=${childBidOrder.soId}&resId=${childBidOrder.resourceId}&displayTab=Received&cameFromOrderManagement=cameFromOrderManagement"
													target="_top">Add Comment</a></td>
											</tr>
										</table>
									</div>
								</li>
							</c:forEach>
						</ul>
					</div>
				</c:if>
			</div>
		</c:forEach>
		<!-- </table> -->
	</div>
	<div id="orderManagementActions" style="width: 100%; margin-left: 15%;">
		<jsp:include page="order_management_actions.jsp" />
	</div>
</body>
</html>
