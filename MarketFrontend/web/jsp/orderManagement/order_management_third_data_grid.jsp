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
.tilteFlyOut{display: none;border-style: solid;background-color: #F9FDB1;border-color: #BBBBBB;max-width: 350px;
    					border-width: 0px 0px 4px;z-index:99999;position: absolute;height: auto; overflow: auto;
    					 -webkit-box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);-moz-box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);}
.capitalizeDiv{text-transform:capitalize;}
    	</style>
    	 <script type="text/javascript">
$(document).ready(function() {
	if (navigator.userAgent.indexOf("MSIE") != -1)
	{	
		
		<c:if test="${omDisplayTab == 'Manage Route'}">
		   $(".omcolumn10").css('width','4%'); 
		   $(".omcolumn8").css('padding-left','10px');
		   $(".omcolumn6").css('padding-left','10px');
		   </c:if>
		   
		   <c:if test="${omTabDTO.viewOrderPricing == true}">
		   <c:if test="${omDisplayTab == 'Manage Route'}">
		   $(".omColumnCust").css('padding-left','5px');
		   $(".omcolumn6").css('padding-left','5px');
		   $(".col4").css('margin-left','0px');
		   </c:if>
		   </c:if>
		   
		   

		
		<c:if test="${omTabDTO.viewOrderPricing == true}">
		<c:if test="${omDisplayTab == 'Cancellations'}">
		$(".omcolumn2").prop('width','11%');
		$(".omColumnCust").css('padding-left','5px');
		$(".omcolumn3").css('padding-left','5px');
		$(".omcolumn6").css('padding-left','0px');
		$(".omcolumn7").css('padding-left','0px');
		$(".omcolumn8").css('padding-left','0px');
		
		</c:if>
		</c:if>
		
		<c:choose><c:when test="${omTabDTO.viewOrderPricing == false}">
		<c:if test="${omDisplayTab == 'Current Orders'}">
		   $(".omcolumn1").prop('width','9%'); 
		   $(".omcolumn2").prop('width','10%'); 
		   $(".omcolumn3").prop('width','9%'); 
		   $(".omcolumn4").prop('width','9%'); 
		   $(".omcolumn4").css('padding-left','5px'); 
		   $(".omcolumn5").prop('width','12%'); 
		   $(".omcolumn6").prop('width','10%'); 
		   $(".omcolumn8").prop('width','10%'); 
		   $(".omcolumn10").css('padding-left','5px'); 
		   
		    
		</c:if>
		
		<c:if test="${omDisplayTab == 'Cancellations'}">
		   $(".omcolumn1").prop('width','9%');
		   $(".omCompensation").css('width','4%');
		   $(".omcolumn3").css('width','12%'); 
		   $(".omcolumn3").css('padding-left','10px');
		   $(".btn-group").css('padding-left','20px');
		   
		  
		   
		</c:if>
		</c:when>
		<c:otherwise>
		<c:if test="${omDisplayTab == 'Current Orders'}">
		$(".omcolumn3").css('padding-left','5px');
		$(".omcolumn4").css('padding-left','5px');
		$(".omcolumn6").css('padding-left','5px');
		$(".omcolumn8").css('padding-left','5px');
		$(".omcolumn11").css('padding-left','15px');
		$(".omcolumn12").css('padding-left','15px');
		$(".omcolumn12").prop('width','12%');
		$(".omcolumn2").prop('width','10%');
		$(".omcolumn5").css('width','13%');
		
		</c:if>
		
		</c:otherwise>
		</c:choose>
		
	}
	
	if (navigator.userAgent.indexOf("Firefox") != -1)
	{	
		
		<c:if test="${omDisplayTab == 'Manage Route'}">
		  $(".omcolumn8").css('padding-left','5px');
		  $(".omcolumn6").css('padding-left','10px');
		  $(".capitalizeDiv").css('max-width','80%');
        </c:if>
		
		<c:choose><c:when test="${omTabDTO.viewOrderPricing == false}">
		<c:if test="${omDisplayTab == 'Current Orders'}">
		$(".omcolumn1").prop('width','9%');
		$(".omcolumn2").prop('width','11%'); 
		$(".omcolumn5").prop('width','12%'); 
		
		$(".omcolumn4").prop('width','10%'); 
		$(".omcolumn8").prop('width','8%'); 
		$(".omcolumn6").prop('width','10%');

		</c:if>
		<c:if test="${omDisplayTab == 'Cancellations'}">
		$(".omcolumn2").css('width','12%');
		$(".alignDiv").css('padding-left','20px');
		$(".col5").css('padding-left','25px');
		$(".btn-group").css('padding-left','20px');
		
		</c:if>
		</c:when>
		<c:otherwise>
		<c:if test="${omDisplayTab == 'Current Orders'}">
		$(".omcolumn1").prop('width','9%');
		 $(".omcolumn3").css('padding-left','5px');
		 $(".col9").css('margin-left','-9px');
		 $(".alignDiv").css('padding-left','5px');
		
		</c:if>
		<c:if test="${omDisplayTab == 'Manage Route'}">
		$(".capitalizeDiv").css('max-width','80%');
		</c:if>
		</c:otherwise>
		</c:choose>
	}
});

</script>	

</head>

<body class="tundra acquity" style="overflow-x: hidden">
		<div class="grid-table-container" style="position: relative;width:100%">
		<input type="hidden" value="${currentSOCount}" id="currentOrderCountOnSort" />
		<input type="hidden" value="${totalTabCountWithoutFilters}" id="totalTabCountWithoutFilters" />							
		<input type="hidden" value="${totalTabCount}" id="totalOrderCount" />				
		<table cellpadding="0px" cellspacing="0px" width="100%" class="" style="font-weight:500;table-layout: fixed;">
			<c:forEach var="soList" items="${omTabDTO.soList}" varStatus="status">
				<c:choose><c:when test="${soList.parentGroupId != null}">
					<c:set var="so_id" value="${soList.parentGroupId}"></c:set>
					<c:set var="groupInd" value="1"></c:set>
				</c:when>
				<c:otherwise>
					<c:set var="so_id" value="${soList.soId}"></c:set>
					<c:set var="groupInd" value="0"></c:set>
				</c:otherwise></c:choose>
			<c:if test="${omDisplayTab == 'Cancellations'}">
			<tr style="height:50px;" class="data-grid-tr">
				<td class="omcolumn1"  style="padding-left:5px;width:8%">
					${soList.soStatusString}<br />
					<div id="soSubStaus${soList.soId}_${status.count}"
											onmouseover="showSoSubStaus(this);"
											onmouseout="hideSoSubStaus(this);">
					&nbsp;&nbsp;&nbsp;<c:choose><c:when test="${soList.acceptanceMethod == 'AUTOMATIC' && soList.soStatus == 150 && soList.soSubStatusString == null}">Auto Accepte...</c:when> 
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
							</div></c:otherwise></c:choose>
								<div id="soSubStausDivs_${soList.soId}_${status.count}" class="tilteFlyOut">
										<div style="padding-left: 5px; margin: 3px;" id="SubStatusName">
										<c:choose><c:when test="${soList.acceptanceMethod == 'AUTOMATIC' && soList.soStatus == 150 && soList.soSubStatusString == null}">Auto Accepted</c:when>
										<c:otherwise>${soList.soSubStatusString}</c:otherwise></c:choose>
										</div>
									</div> <br />
				</td>
				<td class="omcolumn2" style="width:11%">					 
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
					<div id="soTitleDivs_${soList.soId}_${status.count}" class="tilteFlyOut">
						<div style="padding-left: 5px;margin: 3px;" id="titleName">${soList.soTitle}
						</div>
					</div>
					 ${soList.soId}
				</td>
				<td class="omColumnCust" style="word-wrap: break-word;width:11%">
				  <div class="capitalizeDiv">${soList.endCustomerFirstName}&nbsp;<span class="lastNameBold">${soList.endCustomerLastName}</span></div>
				   <i class="icon-phone" rowNum="${status.count}"></i> &nbsp;${soList.endCustomerPrimaryPhoneNumber}<br/>
				     <c:if test="${not empty soList.primaryExtension}">
					 &nbsp;&nbsp;&nbsp;Ex.  ${soList.primaryExtension}<br/>
					</c:if>
				   <c:if test="${not empty soList.endCustomerAlternatePhoneNumber}">
					 <i class="icon-mobile-phone" rowNum="${status.count}"></i>&nbsp;&nbsp;${soList.endCustomerAlternatePhoneNumber}<br/>
					 <c:if test="${not empty soList.alternateExtension}">
					  &nbsp;&nbsp;&nbsp;Ex.  ${soList.alternateExtension}<br/>
					 </c:if>
				   </c:if>
				</td>
				<td class="omcolumn3" style="width:11%">
					${soList.street1}, ${soList.street2}<br/>
					${soList.city}<br/>
					${soList.state}, ${soList.zip}
				</td>
				<td class="omcolumn4" style="width:14%;">
					<c:choose> <c:when test="${soList.resheduleStartDateString == null || soList.resheduleStartDateString == ''}">
								<div  class="alignDiv" style="padding-left:10px;">
								 <c:choose><c:when test="${soList.appointEndDate == null || soList.appointEndDate == ''}">
										${soList.appointStartDate} ${soList.serviceTimeStart}
								 </c:when>
								 <c:otherwise>
									${soList.appointStartDate} to ${soList.appointEndDate}<br/>
									${soList.serviceTimeStart} to ${soList.serviceTimeEnd}
							     </c:otherwise></c:choose> 
								<br/>(${soList.serviceLocationTimezone})
								</div>
							 </c:when>
							 <c:otherwise>
							 
							  <div class="resheduleDiv" style="padding-left:10px;color:#db3330;cursor:help;" title="Reshedule Pending" alt="Reshedule Pending">
							    <c:choose><c:when test="${soList.appointEndDate == null || soList.appointEndDate == ''}">
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
				<td class="omcolumn5" style="width:9%;" >
				<div class="col5">
				<c:forEach var="routedProviders" items="${soList.routedProviders}">
					<c:if test="${routedProviders.respId == 1}">
						<a href="javascript:void(0);"  style="color:black; text-decoration: none" onclick="openProviderProfile('${routedProviders.id}','${soList.vendorId}');">${routedProviders.firstName} ${routedProviders.lastName}</a>
					</c:if>
				</c:forEach>
				</div>
				</td>
				<td class="omcolumn6" style="width:7%" >
					<div style="padding-left:30px;">
						<c:if test="${soList.followUpFlag=='0'}">
							<i id='flag_${status.count}' class="icon-flag off" onclick="setSoPriority(this.id, '${so_id}', '${groupInd}');"></i>
						</c:if>
						<c:if test="${soList.followUpFlag=='1'}">
							<i id='flag_${status.count}' class="icon-flag on" onclick="setSoPriority(this.id, '${so_id}', '${groupInd}');"></i>
						</c:if>
					</div>
				</td>
				<c:if test="${omTabDTO.viewOrderPricing == true}">
				<td class="omcolumn7" style="width:8%;">
					<div style="padding-left:20px;">
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
				<td class="omcolumn8" style=width:14%;">
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
					<div class="btn-group" style="padding-left: 10px;">
						<c:forEach var="actions" items="${soList.actions}">
							<c:if test="${actions == 'View Order'}">
								<a class="action ${btnCss}" onclick="loadTakeAction('${soList.soId}');">${actions}</a>
												${actionMenu}
							</c:if>
							<c:if test="${actions == 'Add Note'}">
											<li>
												<a class="link" style="text-align: left;"
														onclick="loadAddNote(event,'${soList.soId}');"
														id="addNoteLink_${soList.soId}">${actions}</a>
											</li>
							</c:if>
						</c:forEach>
						<c:if test="${fn:length(soList.actions) > 1}">
							</ul>
						</c:if>
					</div>
				</td>
				<c:if test="${omTabDTO.viewOrderPricing == false}">
				<td class="omCompensation" style="width:3%"></td>
				</c:if>
			</tr>
			</c:if>
			
			<c:if test="${omDisplayTab == 'Manage Route'}">
			<tr>
				<td class="omcolumn1"  style="padding-left:5px;width:6%">
					<c:choose><c:when test="${soList.acceptedResourceId == null}">
					<!-- <img src="${staticContextPath}/images/order_management/assignProvider.jpg"></img>
					<p>Assign provider</p> -->
					<div class="assign-pro">
      				  <i class="icon-warning-sign" style="padding-left: 8px;"></i>
       					 Assign Provider
					</div>
					
					</c:when>
					<c:otherwise>
					<c:set var="proId" value="${soList.acceptedResourceId}"></c:set>
					<span class="selectedProCheckbox so${proId}" style="display: none;">
					<input type="checkbox" name="selectedProCheckbox" value="${i}" id="${i}" checked="checked" disabled="disabled" />
					</span>
					</c:otherwise></c:choose>
				</td>
				<td class="omcolumn2" style="width:9%">
				${soList.soStatusString}<br />
				<div id="soSubStaus${soList.soId}_${status.count}"
											onmouseover="showSoSubStaus(this);"
											onmouseout="hideSoSubStaus(this);">
					&nbsp;&nbsp;&nbsp;<c:choose><c:when test="${soList.acceptanceMethod == 'AUTOMATIC' && soList.soStatus == 150 && soList.soSubStatusString == null}">Auto Accepte...</c:when> 
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
							</div></c:otherwise></c:choose> 
								<div id="soSubStausDivs_${soList.soId}_${status.count}" class="tilteFlyOut">
										<div style="padding-left: 5px; margin: 3px;" id="SubStatusName"><c:choose><c:when test="${soList.acceptanceMethod == 'AUTOMATIC' && soList.soStatus == 150 && soList.soSubStatusString == null}">Auto Accepted</c:when>
										<c:otherwise>${soList.soSubStatusString}</c:otherwise></c:choose>
										</div>
									</div><br />
				</td>
				<td class="omcolumn3" style="width:11%">
				
				 	<div style="padding-left:10px">
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
					<div id="soTitleDivs_${soList.soId}_${status.count}" class="tilteFlyOut">
						<div style="padding-left: 5px;margin: 3px;" id="titleName">${soList.soTitle}
						</div>
					</div>
				<div style="padding-left:10px">
					 ${soList.soId}
					 </div>
				</td>
				
				<td class="omColumnCust"  style="word-wrap: break-word;width:10%;padding-left: 5px;">
				<div class="colCust" style="max-width:80%;">
				  <div class="capitalizeDiv" style="max-width:80%;">${soList.endCustomerFirstName}&nbsp;<span class="lastNameBold">${soList.endCustomerLastName}</span></div>
				   <i class="icon-phone" rowNum="${status.count}"></i>&nbsp;${soList.endCustomerPrimaryPhoneNumber} <br/>
				     <c:if test="${not empty soList.primaryExtension}">
					 &nbsp;&nbsp;&nbsp;Ex.  ${soList.primaryExtension}<br/>
					</c:if>
				   <c:if test="${not empty soList.endCustomerAlternatePhoneNumber}">
					 <i class="icon-mobile-phone" rowNum="${status.count}"></i>&nbsp;&nbsp;${soList.endCustomerAlternatePhoneNumber}<br/>
					 <c:if test="${not empty soList.alternateExtension}">
					  &nbsp;&nbsp;&nbsp;Ex.  ${soList.alternateExtension}<br/>
					 </c:if>
				   </c:if>
				   </div>
				</td>
				
				
				<td class="omcolumn4" style="width:10%">
				<div class="col4" style="margin-left: -5px;">
				${soList.street1}, ${soList.street2}<br/>
					${soList.city}<br/>
					${soList.state}, ${soList.zip}
				</div>
				</td>
				<td class="omcolumn5" style="width:13%" >
					<c:choose> <c:when test="${soList.resheduleStartDateString == null || soList.resheduleStartDateString == ''}">
								<div  class="alignDiv" style="padding-left:10px;">
								 <c:choose><c:when test="${soList.appointEndDate == null || soList.appointEndDate == ''}">
										${soList.appointStartDate} ${soList.serviceTimeStart}
								 </c:when>
								 <c:otherwise>
									${soList.appointStartDate} to ${soList.appointEndDate}<br/>
									${soList.serviceTimeStart} to ${soList.serviceTimeEnd}
							     </c:otherwise></c:choose> 
								<br/>(${soList.serviceLocationTimezone})
								</div>
							 </c:when>
							 <c:otherwise>
							
							  <div class="resheduleDiv" style="padding-left:10px;color:#db3330;cursor:help;" title="Reshedule Pending" alt="Reshedule Pending">
							     <c:choose><c:when test="${soList.appointEndDate == null || soList.appointEndDate == ''}">
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
				<td class="omcolumn6" style="word-wrap: break-word;width:10%">
				 	<c:choose><c:when test="${soList.scheduleStatus == null || soList.scheduleStatus == ''}">
						Not Applicable
					</c:when>
					<c:otherwise>
						<div style="word-wrap:break-word; width:90%;">
							${soList.scheduleStatus}
						</div>
						<c:if test="${soList.preCallAttemptedDate!=null && soList.scheduleStatus == 'Pre-Call Attempted'}">
								&nbsp;(<fmt:formatDate value="${soList.preCallAttemptedDate}"
									pattern="MM/dd/yyyy" />)<br />
						</c:if>
					</c:otherwise></c:choose>
				</td>
				<!--
				<td class="omcolumn7" width="8%" >
				<c:forEach var="scope" items="${soList.scope}" varStatus="loop">
					 ${scope.serviceType}<c:if test="${fn:length(soList.scope)>1 && !loop.last}">,<br /></c:if>
				</c:forEach>-->
				<!--  <input type="hidden" id = "followUp" name="followUp" value="${soList.followUpFlag}"/>-->
				<!--</td>
				-->
				<td class="omcolumn8" style="width:9%">
				<div style="margin-left:-12px; word-wrap: break-word; width:96%;">
								<c:choose><c:when test="${soList.assignmentType == 'FIRM'}">
									Unassigned
									<p>
										<a href="javascript:void(0);"  id="assignProviderLink" class="assignPro"
											onclick="loadAssignProvider(null,this.id,'${soList.soId}');">Assign</a>
									</p>
								</c:when>
								<c:otherwise>
									<c:forEach var="routedProviders"
										items="${soList.routedProviders}">
										<c:if test="${routedProviders.respId == 1}">
											<a href="javascript:void(0);"  style="color: black; text-decoration: none"
												onclick="openProviderProfile('${routedProviders.id}','${soList.vendorId}');">${routedProviders.firstName} ${routedProviders.lastName}
												</a>
											<c:if test="${fn:length(soList.routedProviders)>1}">
											<c:if test="${omDisplayTab != 'Awaiting Payment' && omDisplayTab != 'Job Done'}">
												<p>
													<a href="javascript:void(0);"  id="reassignProviderLink" class="assignPro"
														onclick="loadAssignProvider(null,this.id,'${soList.soId}');">Re-assign</a>
												</p>
												</c:if>
											</c:if>
										</c:if>
									</c:forEach>
								</c:otherwise></c:choose>
				</div>
				</td>
				<td class="omcolumn9" style="width:9%">
				<c:set var="partStreet1" value="${fn:trim(soList.partStreet1)}"/>
						<c:choose><c:when test="${not empty partStreet1 || not empty soList.partStreet2}">
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
				<td class="omcolumn10" style="width:5%">
					<div style="padding-left:10px;">
						<c:if test="${soList.followUpFlag=='0'}">
							<i id='flag_${status.count}' class="icon-flag off" onclick="setSoPriority(this.id, '${so_id}', '${groupInd}');"></i>
						</c:if>
						<c:if test="${soList.followUpFlag=='1'}">
							<i id='flag_${status.count}' class="icon-flag on" onclick="setSoPriority(this.id, '${so_id}', '${groupInd}');"></i>
						</c:if>
					</div>
				</td>
				<td class="omcolumn11" style="width:9%">
				<c:choose> <c:when test="${fn:length(soList.actions) > 1}">
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
				<div class="btn-group">
				<c:forEach var="actions" items="${soList.actions}">
					<c:if test="${actions == 'View Order'}">
						<a id="preCall" class="action ${btnCss}" onclick="loadTakeAction('${soList.soId}');">${actions}</a>
										${actionMenu}
					</c:if>
					<c:if test="${actions == 'Add Note'}">
							<li>
								<a class="link" style="text-align: left;"
											onclick="loadAddNote(event,'${soList.soId}');"
											id="addNoteLink_${soList.soId}">Add Note</a>
							</li>	
							<%-- 	<s:if test="${omDisplayTab == 'Manage Route'}">
							<a class="action ${btnCss}" onclick="loadAddNote(event,'${soList.soId}');">${actions}</a>
								${actionMenu}
						</s:if>		--%>
					</c:if>
					<c:if test="${actions == 'Update Service Window'}">
									<li>
										<a class="link" style="text-align: left;"
											onclick="loadUpdateTime('${soList.soId}', '${groupInd}');">Update Service Window</a>
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
				</c:forEach>
					<c:if test="${fn:length(soList.actions) > 1}">
						</ul>
					</c:if>
				</div>
				</td>
			</tr>			
			</c:if>
			
			<c:if test="${omDisplayTab == 'Current Orders'}">
			<tr>
				<td class="omcolumn1" width="10%" style="padding-left: 5px;">
								${soList.soStatusString}<br />
								<div id="soSubStaus${soList.soId}_${status.count}"
											onmouseover="showSoSubStaus(this);"
											onmouseout="hideSoSubStaus(this);">
					&nbsp;&nbsp;&nbsp;<c:choose><c:when test="${soList.acceptanceMethod == 'AUTOMATIC' && soList.soStatus == 150 && soList.soSubStatusString == null}">Auto Accepte...</c:when> 
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
							</div></c:otherwise></c:choose>
								<div id="soSubStausDivs_${soList.soId}_${status.count}" class="tilteFlyOut">
										<div style="padding-left: 5px; margin: 3px;" id="SubStatusName"><c:choose><c:when test="${soList.acceptanceMethod == 'AUTOMATIC' && soList.soStatus == 150 && soList.soSubStatusString == null}">Auto Accepted</c:when>
										<c:otherwise>${soList.soSubStatusString}</c:otherwise></c:choose>
										</div>
									</div> <br />
				</td>
				<td class="omcolumn2" width="9%">
 					
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
					<div id="soTitleDivs_${soList.soId}_${status.count}" class="tilteFlyOut">
						<div style="padding-left: 5px;margin: 3px;" id="titleName">${soList.soTitle}
						</div>
					</div>
					${soList.soId}
				</td>
				<td class="omcolumn3" width="10%" style="word-wrap: break-word">
				<div class="capitalizeDiv">${soList.endCustomerFirstName}&nbsp;<span class="lastNameBold">${soList.endCustomerLastName}</span></div>
				<i class="icon-phone" rowNum="${status.count}"></i>&nbsp;${soList.endCustomerPrimaryPhoneNumber} <br/>
				     <c:if test="${not empty soList.primaryExtension}">
					 &nbsp;&nbsp;&nbsp;Ex.  ${soList.primaryExtension}<br/>
					</c:if>
				<c:if test="${not empty soList.endCustomerAlternatePhoneNumber}">
					 <i class="icon-mobile-phone" rowNum="${status.count}"></i>&nbsp;&nbsp;${soList.endCustomerAlternatePhoneNumber}<br/>
					 <c:if test="${not empty soList.alternateExtension}">
					  &nbsp;&nbsp;&nbsp;Ex.  ${soList.alternateExtension}<br/>
					 </c:if>
					 
				</c:if>
				</td>
				<td class="omcolumn4" width="9%" style="word-wrap: break-word">
				${soList.street1}, ${soList.street2}<br/>
					${soList.city}<br/>
					${soList.state}, ${soList.zip}
				</td>
				<td class="omcolumn5" width="12%" >
				 <c:choose><c:when test="${soList.resheduleStartDateString == null || soList.resheduleStartDateString == ''}">
								<div  class="alignDiv" style="padding-left:10px;">
								 <c:choose><c:when test="${soList.appointEndDate == null || soList.appointEndDate == ''}">
										${soList.appointStartDate} ${soList.serviceTimeStart}
								 </c:when>
								 <c:otherwise>
									${soList.appointStartDate} to ${soList.appointEndDate}<br/>
									${soList.serviceTimeStart} to ${soList.serviceTimeEnd}
							     </c:otherwise></c:choose> 
								<br/>(${soList.serviceLocationTimezone})
								</div>
							 </c:when>
							 <c:otherwise>
							
							  <div class="resheduleDiv" style="padding-left:10px;color:#db3330;cursor:help;" title="Reshedule Pending" alt="Reshedule Pending">
							     <c:choose><c:when test="${soList.appointEndDate == null || soList.appointEndDate == ''}">
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
				<td class="omcolumn6" width="9%" >
				<c:choose> <c:when test="${soList.scheduleStatus == null || soList.scheduleStatus == ''}">
						Not Applicable
					</c:when>
					<c:otherwise>
					<div>
						<c:choose><c:when test="${fn:contains(soList.scheduleStatus, '-')}">
							<c:set var="scheduleStatusTemp" value="${fn:split(soList.scheduleStatus, '-')}" />
							<c:set var="scheduleStatusFinal" value="${fn:join(scheduleStatusTemp, '-<br />')}" />
							${scheduleStatusFinal}
						</c:when>
						<c:otherwise>
							${soList.scheduleStatus}
						</c:otherwise></c:choose>
						<c:if test="${soList.preCallAttemptedDate!=null && soList.scheduleStatus == 'Pre-Call Attempted'}">
							<div style="margin-left: 2px;">(<fmt:formatDate value="${soList.preCallAttemptedDate}"
									pattern="MM/dd/yyyy" />)
							 </div>
						</c:if>
					</div>
					</c:otherwise></c:choose>
				</td>
				<!-- 
				<td class="omcolumn7" width="8%" >
				<c:forEach var="scope" items="${soList.scope}" varStatus="loop">
					 ${scope.serviceType}<c:if test="${fn:length(soList.scope)>1 && !loop.last}">,<br /></c:if>
				</c:forEach>-->
				<!--  <input type="hidden" id = "followUp" name="followUp" value="${soList.followUpFlag}"/>-->
				<!-- 
				</td>
				-->
				<td class="omcolumn8" width="9%">
					<div style="margin-left:-5px; word-wrap: break-word; width:97%;">
					<c:choose><c:when test="${soList.assignmentType == 'FIRM'}">
									Unassigned
							<p>
							<a href="javascript:void(0);"  id="assignProviderLink_${soList.soId}" class="assignPro"
								onclick="loadAssignProvider(null,this.id,'${soList.soId}');">Assign</a>
							</p>
					</c:when>
					<c:otherwise>
					<c:forEach var="routedProviders" items="${soList.routedProviders}">
					<c:if test="${routedProviders.respId == 1}">
						<a href="javascript:void(0);"  style="color:black; text-decoration: none" 
							onclick="openProviderProfile('${routedProviders.id}','${soList.vendorId}');">${routedProviders.firstName} ${routedProviders.lastName}</a>
						<c:if test="${fn:length(soList.routedProviders)>1}">
						<c:if test="${omDisplayTab != 'Awaiting Payment' && omDisplayTab != 'Job Done'}">
							<p><a href="javascript:void(0);"  id="reassignProviderLink_${soList.soId}" class="assignPro" onclick="loadAssignProvider(null,this.id,'${soList.soId}');">Re-assign</a></p>
						</c:if>
						</c:if>
					</c:if>
				</c:forEach>
				</c:otherwise></c:choose>
				</div>
				</td>
				<td class="omcolumn9" width="8%">
				<c:set var="partStreet1" value="${fn:trim(soList.partStreet1)}"/>
					<div class="col9" style="margin-left: 5px;">
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
					</div>
				</td>
				<td class="omcolumn10" width="5%" style="text-align:left;">
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
				<td class="omcolumn11" width="5%">
						<c:choose><c:when test="${soList.soStatus < 160}">
						<fmt:formatNumber type="currency" currencySymbol="$" value="${soList.spendLimit+soList.spendLimitParts}" />
					</c:when>
					<c:otherwise>
						<fmt:formatNumber type="currency" currencySymbol="$" value="${soList.finalPartsPrice+soList.finalLaborPrice}" />
					</c:otherwise></c:choose>
				</td>
				</c:if>
				<td class="omcolumn12" width="10%">
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
				<div class="btn-group" style="padding-left: 5px;">
				<c:forEach var="actions" items="${soList.actions}">
					<c:if test="${actions == 'Add Note'}">
						<c:choose><c:when test="${omDisplayTab == 'Current Orders'}">
							<a id="addNoteLink_${soList.soId}" href="javascript:void(0);" class="action ${btnCss}" onclick="loadAddNote(event,'${soList.soId}');">${actions}</a>
								${actionMenu}
						</c:when>
						<c:otherwise>
							<li>
								<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadAddNote(event,'${soList.soId}');"
											id="addNoteLink_${soList.soId}">Add Note</a>
							</li>	
						</c:otherwise></c:choose>
					</c:if>
					<c:if test="${actions == 'Time on Site'}">
									<li>
										<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadTimeOnSite('${soList.soId}');">Time on Site</a>
									</li>					
					</c:if>
					<c:if test="${actions == 'Edit Service Location Notes'}">
									<li>
										<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadEditServiceLocationNotes(event,'${soList.soId}');"
											id="editLocnNotesLink_${soList.soId}">Edit Service Location Notes</a>
									</li>					
					</c:if>
					<c:if test="${actions == 'Update Service Window'}">
								<li>
									<a class="link" style="text-align: left;"
										onclick="loadUpdateTime('${soList.soId}', '${groupInd}');">Update Service Window</a>
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
					<c:if test="${actions == 'Report a Problem'}">
									<li>
										<a class="link" style="text-align: left;" href="javascript:void(0);"
											onclick="loadReportAProblem('${soList.soId}');">Report a
											Problem</a>
						</li>
					</c:if>
				</c:forEach>
					<c:if test="${fn:length(soList.actions) > 1}">
						</ul>
					</c:if>
				</div>
				</td>
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
