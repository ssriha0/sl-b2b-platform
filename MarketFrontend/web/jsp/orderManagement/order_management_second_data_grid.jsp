<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>	
	<style type="text/css">
		.tilteFlyOut{display: none;border-style: solid;background-color: #F9FDB1;border-color: #BBBBBB;max-width:350px;
    					 border-width: 0px 0px 4px;z-index:99999;position: absolute;height: auto; overflow: auto;
    					 -webkit-box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);-moz-box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);}
.capitalizeDiv{text-transform:capitalize;}					 
    </style>
	<script type="text/javascript">
$(document).ready(function() {
	if (navigator.userAgent.indexOf("MSIE") != -1)
	{ 	
		<c:choose>		
		<c:when test="${omTabDTO.viewOrderPricing == true}">
		   <c:if test="${omDisplayTab == 'Awaiting Payment'}">
		     $(".resheduleDiv").css('padding-left','0px'); 
			 $(".alignDiv").css('padding-left','0px'); 
			 $(".resheduleDiv").css('margin-left','-10px'); 
			 $(".alignDiv").css('margin-left','-10px'); 
			 $(".providerDiv").css('padding-left','5px');
			 $(".customerDiv").css('margin-left','8px'); 
			 $(".locationDiv").css('margin-left','-10px');
			 $(".titleDiv").css('padding-left','5px'); 
			 $(".customerDiv").css('max-width','80%'); 
			 $(".locationDiv").css('max-width','80%');
			 
			 
		  </c:if>
		   <c:if test="${omDisplayTab == 'Assign Provider'}">
		   $(".omcolumn2").css('width','13%');
		   $(".titleDiv").css('padding-left','5px'); 
		   $(".customerDiv").css('margin-left','8px'); 
		   </c:if>
		</c:when>
		<c:otherwise>
		 <c:if test="${omDisplayTab == 'Awaiting Payment'}">
		    $(".resheduleDiv").css('margin-left','-20px'); 
		    $(".alignDiv").css('margin-left','-20px'); 
		    $(".omCompensation").css('width','7%'); 
		    $(".titleDiv").css('padding-left','5px');
		 </c:if>
		</c:otherwise>
		</c:choose>
		   <c:if test="${omDisplayTab == 'Assign Provider'}">
		   	<c:choose>
		     <c:when test="${omTabDTO.viewOrderPricing == false}">
		       $(".omCompensation").css('width','5%'); 
		       $(".resheduleDiv").css('padding-left','0px'); 
			   $(".alignDiv").css('padding-left','0px'); 
	         </c:when>
	         <c:otherwise>
	           $(".omCompensation").css('width','0%'); 
	         </c:otherwise>
		    </c:choose>
		  </c:if>
		
	}
	if (navigator.userAgent.indexOf("Firefox") != -1)
	{ 
		<c:if test="${omTabDTO.viewOrderPricing == true}">
		<c:if test="${omDisplayTab == 'Awaiting Payment'}">
		  $(".omcolumn6").css('width','14%'); 
		  $(".omcolumn9").css('width','15%'); 
		  $(".omcolumn2").css('width','13%');
		  $(".resheduleDiv").css('padding-left','0px'); 
		  $(".alignDiv").css('padding-left','0px');
		  $(".resheduleDiv").css('margin-left','-10px'); 
		  $(".alignDiv").css('margin-left','-10px'); 
		  $(".locationDiv").css('margin-left','-10px');
		  $(".titleDiv").css('padding-left','5px'); 
		  $(".customerDiv").css('max-width','80%'); 
		</c:if>
		<c:if test="${omDisplayTab == 'Assign Provider'}">
		 $(".omcolumn9").css('padding-left','0px');
		 $(".resheduleDiv").css('padding-left','0px'); 
		 $(".alignDiv").css('padding-left','0px');
		 $(".titleDiv").css('padding-left','5px'); 
		 </c:if>
		</c:if>
		
		<c:if test="${omTabDTO.viewOrderPricing == false}">
        <c:if test="${omDisplayTab == 'Awaiting Payment'}">
		  $(".omcolumn9").prop('width','15%'); 
		  $(". omcolumn4").css('width','13%'); 
		  $(".titleDiv").css('padding-left','5px');
		  $(".providerDiv").css('padding-left','10px');
		  $(".resheduleDiv").css('margin-left','-15px'); 
		  $(".alignDiv").css('margin-left','-15px'); 
	    </c:if>
         <c:if test="${omDisplayTab == 'Assign Provider'}">
		   $(".omcolumn3").css('padding-left','5px');
		   $(".resheduleDiv").css('padding-left','5px'); 
		   $(".alignDiv").css('padding-left','5px'); 
		  </c:if>
		</c:if>
	}
});

</script>	

</head>

<body class="tundra acquity" style="overflow-x: hidden">
		<div class="grid-table-container" style="position: relative;width:100%;">
		<input type="hidden" value="${currentSOCount}" id="currentOrderCountOnSort" />
		<input type="hidden" value="${totalTabCountWithoutFilters}" id="totalTabCountWithoutFilters" />						
		<input type="hidden" value="${totalTabCount}" id="totalOrderCount" />		
		<table width="100%" cellpadding="0px" cellspacing="0px" width="800px" class="" style="font-weight:500;">
			<c:forEach var="soList" items="${omTabDTO.soList}" varStatus="status">
				<c:choose>
				<c:when test="${soList.parentGroupId != null}">
					<c:set var="so_id" value="${soList.parentGroupId}"></c:set>
					<c:set var="groupInd" value="1"></c:set>
				</c:when>
				<c:otherwise>
					<c:set var="so_id" value="${soList.soId}"></c:set>
					<c:set var="groupInd" value="0"></c:set>
				</c:otherwise>
				</c:choose>
			
			<c:if test="${omDisplayTab == 'Assign Provider' || omDisplayTab == 'Awaiting Payment'}">
			        <c:choose>
			        <c:when test="${omTabDTO.viewOrderPricing == true}">
						<c:set var="titleWidth" value="12"></c:set>
						<c:set var="locationWidth" value="12"></c:set>
					</c:when>
					<c:otherwise>
						<c:choose>
					    <c:when test="${omDisplayTab == 'Assign Provider'}">
						   <c:set var="titleWidth" value="12"></c:set>
						   <c:set var="locationWidth" value="14"></c:set>
						</c:when>
						<c:otherwise>
						   <c:set var="titleWidth" value="13"></c:set>
						   <c:set var="locationWidth" value="14"></c:set>
						</c:otherwise>
						</c:choose>
					</c:otherwise>
					</c:choose>
			<tr style="height:50px;" class="data-grid-tr">
				<td class="omcolumn1" style="padding-left:5px;width:10%">
				  
					${soList.soStatusString}<br />
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
							</div>
					</c:otherwise>
					</c:choose>
								<div id="soSubStausDivs_${soList.soId}_${status.count}" class="tilteFlyOut">
										<div style="padding-left: 5px; margin: 3px;" id="SubStatusName"><c:choose><c:when test="${soList.acceptanceMethod == 'AUTOMATIC' && soList.soStatus == 150 && soList.soSubStatusString == null}">Auto Accepted</c:when>
										<c:otherwise>${soList.soSubStatusString}</c:otherwise></c:choose>
										</div>
									</div> <br />
				</td>
				<td class="omcolumn2" style="width:${titleWidth}%">
					 
					<div class="titleDiv" style="padding-right:3px">
					<a href="javascript:void(0);"  class="soTitleLink" id="soTitle${soList.soId}_${status.count}" onmouseover="showTitle(this);" onmouseout="hideTitle(this);" rowNum="${status.count}" onclick="displaySODetails('${soList.soId}','0','${soList.routedResourceId}');">
						<c:choose>
								<c:when test="${not empty soList.soTitle && fn:length(soList.soTitle) > 12}">
										${fn:substring(soList.soTitle,	0, 12)}...
								</c:when>
								<c:when test="${not empty soList.soTitle && fn:length(soList.soTitle) <= 12}">
										${soList.soTitle}
								</c:when>
								<c:otherwise>&nbsp;</c:otherwise>
						</c:choose>
					</a></div>
					<div id="soTitleDivs_${soList.soId}_${status.count}" class="tilteFlyOut">
						<div style="padding-left: 5px;margin: 3px;" id="titleName">${soList.soTitle}
						</div>
					</div>
					 &nbsp;${soList.soId}
				</td>
				<td class="omColumnCust"style="word-wrap:break-word;width:10%">
					     <div class="customerDiv">
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
				<td class="omcolumn3" style="width:${locationWidth}%;padding-left: 5px;">
					<div class="locationDiv">${soList.street1}, ${soList.street2}<br/>
					${soList.city}<br/>
					${soList.state}, ${soList.zip}
					</div>
				</td>
				<td class="omcolumn4" width="12%">
							<c:choose><c:when
								test="${soList.resheduleStartDateString == null || soList.resheduleStartDateString == ''}">
								<div class="alignDiv" style="padding-left: 10px;">
									<c:choose><c:when
										test="${soList.appointEndDate == null || soList.appointEndDate == ''}">
										     ${soList.appointStartDate} ${soList.serviceTimeStart}
								 </c:when>
									<c:otherwise>
									${soList.appointStartDate} to ${soList.appointEndDate}<br />
									${soList.serviceTimeStart} to ${soList.serviceTimeEnd}
							     </c:otherwise></c:choose>
									<br />(${soList.serviceLocationTimezone})
								</div>
							</c:when> 
							<c:otherwise>
							  <div class="resheduleDiv" style="padding-left:10px;color:#db3330;cursor:help;" title="Reshedule Pending" alt="Reshedule Pending">
							    <c:choose> <c:when test="${soList.appointEndDate == null || soList.appointEndDate == ''}">
										     ${soList.appointStartDate} ${soList.serviceTimeStart}
								 </c:when>
								 <c:otherwise>
									${soList.appointStartDate} to ${soList.appointEndDate}<br/>
									${soList.serviceTimeStart} to ${soList.serviceTimeEnd}
							     </c:otherwise></c:choose> 
								<br/>(${soList.serviceLocationTimezone})
							  </div>
						</c:otherwise></c:choose>
				</td>
				<c:choose>
				<c:when test="${omDisplayTab == 'Assign Provider'}">
					<td class="omcolumn5" width="14%" >
						<div style="margin-left:15px;">
						 <c:choose><c:when test="${soList.scheduleStatus == null || soList.scheduleStatus == ''}">
							Not Applicable
						</c:when>
						<c:otherwise>
							<div style="max-width: 100px;word-wrap:break-word;">
								${soList.scheduleStatus}
							</div>
							<c:if test="${soList.preCallAttemptedDate!=null && soList.scheduleStatus == 'Pre-Call Attempted'}">
								&nbsp;(<fmt:formatDate value="${soList.preCallAttemptedDate}"
									pattern="MM/dd/yyyy" />)<br />
							</c:if>
						</c:otherwise></c:choose>
					</div>
					</td>
				</c:when>
			    <c:otherwise>
					<td class="omcolumn6" width="14%">
						<div  class="providerDiv"style="word-wrap: break-word; width:150px;">
						<c:forEach var="routedProviders" items="${soList.routedProviders}">
							<c:if test="${routedProviders.respId == 1}">
								<a href="javascript:void(0);"  style="color:black; text-decoration: none" onclick="openProviderProfile('${routedProviders.id}','${soList.vendorId}');">${routedProviders.firstName} ${routedProviders.lastName}</a>
								<c:if test="${fn:length(soList.routedProviders)>1}">
									<c:if test="${omDisplayTab != 'Awaiting Payment' && omDisplayTab != 'Job Done'}">
										<p><a href="javascript:void(0);"  id="reassignProviderLink" class="assignPro" onclick="loadAssignProvider(null,this.id, '${soList.soId}');">Re assign</a></p>
									</c:if>
								</c:if>
							</c:if>
						</c:forEach>
						</div>
					</td>
				</c:otherwise>
				</c:choose>				
				<td class="omcolumn7" width="7%" >
					<div style="padding-left: 15px;">
						<c:if test="${soList.followUpFlag=='0'}">
							<i id='flag_${status.count}' class="icon-flag off" onclick="setSoPriority(this.id, '${so_id}', '${groupInd}');"></i>
						</c:if>
						<c:if test="${soList.followUpFlag=='1'}">
							<i id='flag_${status.count}' class="icon-flag on" onclick="setSoPriority(this.id, '${so_id}', '${groupInd}');"></i>
						</c:if>
					</div>
				</td>
				<c:if test="${omTabDTO.viewOrderPricing == true}">
				<td class="omcolumn8" width="7%">
					<div style="padding-left: 15px;">
						<c:choose><c:when test="${soList.soStatus == 160 || soList.soStatus == 180 || soList.soStatus == 120}">
							<fmt:formatNumber type="currency" currencySymbol="$"
								value="${soList.finalPartsPrice+soList.finalLaborPrice}" />
						</c:when>
						<c:otherwise>
							<fmt:formatNumber type="currency" currencySymbol="$"
								value="${soList.spendLimit+soList.spendLimitParts}" />									
						</c:otherwise></c:choose>
					</div>
				</td>
				</c:if>
				<td class="omcolumn9"width="14%" style="padding-left:10px;" >
					<c:choose><c:when test="${fn:length(soList.actions) > 1}">
								<c:set var="actionMenu" value="<a class='action dropdown'><i class='icon-caret-down'></i></a><ul class='dropdown-menu' style='display: none;'>"></c:set>
								<c:set var="btnCss" value=""></c:set>
					</c:when>
					<c:when test="${fn:length(soList.actions) == 1}">
								<c:set var="actionMenu" value=""></c:set>
								<c:set var="btnCss" value="btnCss"></c:set>
					</c:when> 
					<c:otherwise>
								<c:set var="actionMenu" value=""></c:set>
								<c:set var="btnCss" value="btnCss"></c:set>
					</c:otherwise></c:choose>
			<div class="btn-group" style="padding-left:5px;">
				<c:forEach var="actions" items="${soList.actions}">
					<c:if test="${actions == 'View Order'}">
						<a class="action ${btnCss}" onclick="loadTakeAction('${soList.soId}');" href="javascript:void(0);">${actions}</a>
										${actionMenu}
					</c:if>
					<c:if test="${actions == 'Place Bid'}">
						<a class="action ${btnCss}" onclick="loadPlaceBid('${soList.soId}');" href="javascript:void(0);">${actions}</a>
						${actionMenu}
					</c:if>
					<c:if test="${actions == 'Take Action'}">
						<a class="action ${btnCss}" onclick="loadTakeAction('${soList.soId}');" href="javascript:void(0);">${actions}</a>
										${actionMenu}
					</c:if>
					<c:if test="${actions == 'Assign Provider'}">
							<a class="action ${btnCss}" onclick="loadAssignProvider(null,this.id,'${soList.soId}');" href="javascript:void(0);">${actions}</a>
							${actionMenu}
					</c:if>
					<c:if test="${actions == 'Confirm Appt Window'}">
						<a id="confirmApptWindow" class="action ${btnCss}" onclick="loadConfirmWindow();" href="javascript:void(0);">${actions}</a>
										Confirm Appt.
					</c:if>
					<c:if test="${actions == 'Issue Resolution'}">
						<a id="issueResolution" class="action ${btnCss}" onclick="loadIssueResolution('${soList.soId}');" href="javascript:void(0);">${actions}</a>
							${actionMenu}
					</c:if>
					<c:if test="${actions == 'Completion Record'}">
						<a id="completionRecord" class="action ${btnCss}" onclick="loadCompletionRecord('${soList.soId}');" href="javascript:void(0);">${actions}</a>
							${actionMenu}
					</c:if>
					<c:if test="${actions == 'Accept Button'}">
						<a class="action ${btnCss}" onclick="loadAcceptOrder('${so_id}','${groupInd}');" href="javascript:void(0);">Accept</a>
										${actionMenu}
					</c:if>
					<c:if test="${actions == 'Pre-Call'}">
						<a id="preCall" class="action ${btnCss}" onclick="loadConfirmAppointment('${soList.soId}');" href="javascript:void(0);">${actions}</a>
										${actionMenu}
					</c:if>
					<c:if test="${actions == 'Add Note'}">
							<li>
										<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadAddNote(event,'${soList.soId}');"
											id="addNoteLink_${soList.soId}">Add Note</a>
							</li>	
					</c:if>
					<c:if test="${actions == 'Request a Reschedule'}">
									<c:choose><c:when test="${soList.reSchedStartDate==null}">
									<li>
										<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadRequestaReshedule(event,'${soList.soId}');"
											id="rescheduleLink_${soList.soId}">Request a Reschedule</a>
									</li>
									</c:when>
									<c:otherwise>
									<c:choose><c:when test="${soList.rescheduleRole==3}">
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
									</c:otherwise></c:choose>
									</c:otherwise></c:choose>				
					</c:if>
					<c:if test="${actions == 'Edit Service Location Notes'}">
									<li>
										<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadEditServiceLocationNotes(event,'${soList.soId}');"
											id="editLocnNotesLink_${soList.soId}">Edit Service Location Notes</a>
									</li>					</c:if>
					<c:if test="${actions == 'Time on Site'}">
									<li>
										<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadTimeOnSite('${soList.soId}');">Time on Site</a>
									</li>					</c:if>
					<c:if test="${actions == 'Report a Problem'}">
									<li>
										<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadReportAProblem('${soList.soId}');">Report a
											Problem</a>
									</li>					</c:if>
					<c:if test="${actions == 'Counter offer'}">
									<li>
										<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadCounterOffer('${soList.soId}','${soList.parentGroupId}');">Counter
											Offer</a>
									</li>					</c:if>
					<c:if test="${actions == 'Reject Order'}">
									<li>
										<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadRejectOrder('${so_id}', '${groupInd}',0,0);">Reject
											Order</a>
									</li>					</c:if>
					<c:if test="${actions == 'Update Service Window'}">
									<li>
										<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadUpdateTime('${soList.soId}', '${groupInd}');">Update Service Window</a>
									</li>
					</c:if>
					<c:if test="${actions == 'Complete For payment' }">
						<li>
							<a class="actionButton" style="text-decoration: none; color: #000000" href="javascript:void(0);"
								id="completeForPayment"	onclick="loadCompleteForPayment('${soList.soId}');">Complete Order
							</a>
						</li>
					</c:if>
				</c:forEach>
					<c:if test="${fn:length(soList.actions) > 1}">
					</ul>
					</c:if>
					</div>
				</td>
				<c:if test="${omTabDTO.viewOrderPricing == true}">
					<td class="omCompensation" style="width: 1%">&nbsp;</td>
					</c:if>
					<c:if test="${omDisplayTab == 'Assign Provider' && omTabDTO.viewOrderPricing != true}">
					<td class="omCompensation" style="width: 1%">&nbsp;</td>
					</c:if>
					<c:if test="${omDisplayTab == 'Awaiting Payment' && omTabDTO.viewOrderPricing != true}">
						<td class="omCompensation" style="width: 5%">&nbsp;</td>
					</c:if>
			</tr>
			</c:if>
			
			<c:if test="${omDisplayTab == 'Print Paperwork'}">
			<tr>
				<td class="omcolumn1"  style="text-align: center; padding-left:0px;width:9%">
					<input type="checkbox" name="selectPDF" value="${soList.soId}" id="selectPDF_${soList.soId}" class="selectPdf" onclick="displayPdfButton();"/>
				</td>
				<td class="omcolumn2" style="width:11%">
				${soList.soStatusString}<br />
				<div id="soSubStaus${soList.soId}_${status.count}"
											onmouseover="showSoSubStaus(this);"
											onmouseout="hideSoSubStaus(this);">
					&nbsp;&nbsp;&nbsp;
					<c:choose><c:when test="${soList.acceptanceMethod == 'AUTOMATIC' && soList.soStatus == 150 && soList.soSubStatusString == null}">Auto Accepte...</c:when> 
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
							</div>
					</c:otherwise></c:choose>
								<div id="soSubStausDivs_${soList.soId}_${status.count}" class="tilteFlyOut">
										<div style="padding-left: 5px; margin: 3px;" id="SubStatusName">
										<c:choose><c:when test="${soList.acceptanceMethod == 'AUTOMATIC' && soList.soStatus == 150 && soList.soSubStatusString == null}">Auto Accepted</c:when>
										<c:otherwise>${soList.soSubStatusString}</c:otherwise></c:choose>
										</div>
									</div> <br />
				</td>
				<td class="omcolumn3" style="width:10%">
					<div style="padding-left:2px;">
						
						 <div style="padding-right:3px">
							 <a href="javascript:void(0);"  class="soTitleLink" id="soTitle${soList.soId}_${status.count}" onmouseover="showTitle(this);" onmouseout="hideTitle(this);" rowNum="${status.count}" onclick="displaySODetails('${soList.soId}','0','${soList.routedResourceId}');">
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
					</div>
					<div id="soTitleDivs_${soList.soId}_${status.count}" class="tilteFlyOut">
						<div style="padding-left: 5px;margin: 3px;" id="titleName">${soList.soTitle}
						</div>
					</div>
					 ${soList.soId}
				</td>
				<td class="omColumnCust"  style="width:9%;word-wrap:break-word;word-break: break-all;">
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
				</td>
				<td class="omcolumn4" style="width:12%">
					<div>
						${soList.street1}, ${soList.street2}<br/>
						${soList.city}<br/>
						${soList.state}, ${soList.zip}
					</div>
				</td>
				<td class="omcolumn5" style="width:14%" > 
					<c:choose> <c:when test="${soList.resheduleStartDateString == null || soList.resheduleStartDateString == ''}">
								<div  class="alignDiv">
								 <c:choose><c:when test="${soList.appointEndDate == null || soList.appointEndDate == ''}">
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
							
							  <div class="resheduleDiv" style="color:#db3330;cursor:help;" title="Reshedule Pending" alt="Reshedule Pending">
							     <c:choose><c:when test="${soList.appointEndDate == null || soList.appointEndDate == ''}">
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
				<td class="omcolumn6" style="width:10%">
				 	<c:choose><c:when test="${soList.scheduleStatus == null || soList.scheduleStatus == ''}">
						Not Applicable
					</c:when>
					<c:otherwise>
						<c:choose><c:when test="${fn:contains(soList.scheduleStatus, '-')}">
							<c:set var="scheduleStatusTemp" value="${fn:split(soList.scheduleStatus, '-')}" />
							<c:set var="scheduleStatusFinal" value="${fn:join(scheduleStatusTemp, '-<br />')}" />
							${scheduleStatusFinal}
						</c:when>
						<c:otherwise>
							${soList.scheduleStatus}
						</c:otherwise></c:choose>
						<c:if test="${soList.preCallAttemptedDate!=null && soList.scheduleStatus == 'Pre-Call Attempted'}">
								&nbsp;&nbsp;(<fmt:formatDate value="${soList.preCallAttemptedDate}"
									pattern="MM/dd/yyyy" />)<br />
						</c:if>
					</c:otherwise>
					</c:choose>
				</td>
				<td class="omcolumn8" style="width:10%">
				<div style="margin-left:-5px; word-wrap: break-word; width:110px;">
				<c:choose><c:when test="${soList.assignmentType == 'FIRM'}">
									Unassigned
									<p>
										<a href="javascript:void(0);"  id="assignProviderLink" class="assignPro"
											onclick="loadAssignProvider(null,this.id,'${soList.soId}');">Assign</a>
									</p>
				</c:when>
				<c:otherwise>
				<c:forEach var="routedProviders" items="${soList.routedProviders}">
					<c:if test="${routedProviders.respId == 1}">
						<a href="javascript:void(0);"  style="color:black; text-decoration: none" onclick="openProviderProfile('${routedProviders.id}','${soList.vendorId}');">${routedProviders.firstName} ${routedProviders.lastName}</a>
						<c:if test="${fn:length(soList.routedProviders)>1}">
						<c:if test="${omDisplayTab != 'Awaiting Payment' && omDisplayTab != 'Job Done'}">
							<p><a href="javascript:void(0);"  id="reassignProviderLink" class="assignPro" onclick="loadAssignProvider(null,this.id,'${soList.soId}');">Re assign</a></p>
						</c:if>
						</c:if>
					</c:if>
				</c:forEach>
				</c:otherwise></c:choose>
				</div>
				</td>
				<td class="omcolumn9" style="width:10%">
				<c:set var="partStreet1" value="${fn:trim(soList.partStreet1)}"/>
						<c:choose><c:when test="${not empty partStreet1}">
							${soList.partStreet1}<br/>${soList.partStreet2},
							<div>${soList.partCity},</div>
							<div>${soList.partState} ${soList.partZip}</div>
						<c:if test="${not empty soList.availabilityDate}">
							<div>(${soList.availabilityDate})</div>
						</c:if>
						</c:when>
						<c:otherwise>
							Not Applicable
						</c:otherwise></c:choose>
				</td>
				<td class="omcolumn10" style="width:4%">
					<div style="padding-left:15px;">
						<c:if test="${soList.followUpFlag=='0'}">
							<i id='flag_${status.count}' class="icon-flag off" onclick="setSoPriority(this.id, '${so_id}', '${groupInd}');"></i>
						</c:if>
						<c:if test="${soList.followUpFlag=='1'}">
							<i id='flag_${status.count}' class="icon-flag on" onclick="setSoPriority(this.id, '${so_id}', '${groupInd}');"></i>
						</c:if>
					</div>
				</td>
				<td class="omCompensation" style="width:1%">&nbsp;</td>
				</tr>
			</c:if>
			</c:forEach>
		</table>
	</div>
	<div id="orderManagementActions" style="width: 100%; margin-left: 15%;">
				<jsp:include page="order_management_actions.jsp"/>
		</div>
</body>
</html>