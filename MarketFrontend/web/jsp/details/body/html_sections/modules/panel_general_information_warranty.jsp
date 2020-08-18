<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<html>
<head>
<c:set var="BUYER_ROLEID" value="<%= new Integer(OrderConstants.BUYER_ROLEID)%>" />
<c:set var="PROVIDER_ROLEID" value="<%= new Integer(OrderConstants.PROVIDER_ROLEID)%>" />
<c:set var="ROUTED_STATUS" value="<%= new Integer(OrderConstants.ROUTED_STATUS)%>" />
<c:set var="COMPLETED_STATUS" value="<%= new Integer(OrderConstants.COMPLETED_STATUS)%>" />
<c:set var="CLOSED_STATUS" value="<%= new Integer(OrderConstants.CLOSED_STATUS)%>" />

<c:set var="role" value="${roleType}" />

<c:set var="MAX_CHARACTERS_WITHOUT_SCROLLBAR" value="1200" />

<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
<script type="text/javascript">
function expand(id,path){
    var divId="general"+id;
    var testGroup=document.getElementById(divId).value;
    var bodyId="general_menu_body"+id;
    if(testGroup=="menu_list"){
        jQuery("#"+divId+" p.menu_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
     }else{
       jQuery("#"+divId+" p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
     }
      var ob=document.getElementById('generalInfoImage'+id).src;
         if(ob.indexOf('arrowRight')!=-1){
            document.getElementById('generalInfoImage'+id).src=path+"/images/widgets/arrowDown.gif";
            }
          if(ob.indexOf('arrowDown')!=-1){
            document.getElementById('generalInfoImage'+id).src=path+"/images/widgets/arrowRight.gif";
            }
       }

jQuery(document).ready(function($){
	jQuery("#docLogo").on('error', function(){
        jQuery(this).attr('src','<c:out value="${staticContextPath}"/>/images/pixel.gif');
    });
   
});

</script>

</head>
<body>

<c:set var="divName" value="general"/>
     <c:set var="divName" value="${divName}${summaryDTO.id}"/>
      <c:set var="group" value="group"/>
      <c:if test="${checkGroup==group}">
        <c:set var="groupVal" value="menu_list"/>
        <c:set var="bodyClass" value="menu_body"/>
        <c:set var="headClass" value="menu_head"/>
    </c:if>
    <c:if test="${checkGroup!=group}">
        <c:set var="groupVal" value="menugroup_list"/>
        <c:set var="bodyClass" value="menugroup_body"/>
        <c:set var="headClass" value="menugroup_head"/>
    </c:if>
<div id="${divName}" class="${groupVal}">
      <c:set var="bodyName" value="general_menu_body"/>
      <c:set var="bodyName" value="${bodyName}${summaryDTO.id}"/>
      <c:set var="generalInfoImage" value="generalInfoImage"/>
      <c:set var="generalInfoImage" value="${generalInfoImage}${summaryDTO.id}"/>
      <p class="${headClass }" onclick="expand('${summaryDTO.id}','${staticContextPath}')">&nbsp;<img id="${generalInfoImage}" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;General Information</p>
      <div id="${bodyName}" class="${bodyClass}">
      <table cellpadding="0" cellspacing="0" class="noMargin">
        <tr>
            <td width="300">
                <p>
                    <c:choose>
                    <%-- Show Buyer Logo to buyer all the time regardless of the order status. Show Buyer Logo to Provider only
                         after provider accepted the order (meaning Order Status is not Posted/Routed and is one of
                         Accepted/Active/Complete/Cancelled/Voided/Closed/Problem/Expired). SO in Draft status is only visible
                         to Buyers.--%>
                        <c:when test="${role == BUYER_ROLEID || (role == PROVIDER_ROLEID && summaryDTO.status != ROUTED_STATUS)}">
                            <c:if test="${summaryDTO.logodocumentId != null}">    
                                <img src="${contextPath}/displayBuyerDocument.action?docId=${summaryDTO.logodocumentId}" alt="image" id="docLogo" />
                            </c:if>
                        </c:when>
                        <c:when test="${role == PROVIDER_ROLEID && summaryDTO.status == ROUTED_STATUS}">
                        <br>
                        <br>
                            <img src="${staticContextPath}/images/artwork/common/logo_notagline.png" alt="ServiceLive"/>
                        </c:when>
                    </c:choose>
                </p>
                <table cellpadding="0" cellspacing="0"
                    class="adjustedTableRowPadding">
                    <tr>
                        <td width="120" class="labelRight">
                            <fmt:message bundle='${serviceliveCopyBundle}' key='details.so.id'/>:
                        </td>
                        <td width="160">
                            ${summaryDTO.id}
                        </td>
                    </tr>
                    <tr>
                        <td class="labelRight">
                            <fmt:message bundle='${serviceliveCopyBundle}' key='details.primary.status'/>:
                        </td>
                        <td>
                            <fmt:message bundle='${serviceliveCopyBundle}' key='workflow.state.${SERVICE_ORDER_CRITERIA_KEY.roleId}.${summaryDTO.status}'/>
                            <c:if test="${not empty summaryDTO.status && summaryDTO.status==110 }">                            
                                <c:if test="${not empty summaryDTO.methodOfRouting && summaryDTO.methodOfRouting=='EXCLUSIVE'}">
                                    (Exclusive)
                                </c:if>
                                <c:if test="${not empty summaryDTO.methodOfRouting && summaryDTO.methodOfRouting=='GENERAL'}">
                                    (General)
                                </c:if>                                
                            </c:if>
                            
                            <c:if test="${not empty methodOfClosure}">                            
                            	<c:if test="${methodOfClosure == 'PENDING AUTO CLOSE'}">
                                    (Pending Auto Close)
                                </c:if>
                                <c:if test="${methodOfClosure == 'MANUAL REVIEW'}">
                                    (Manual Review)
                                </c:if>
                            </c:if>
                        </td>
                    </tr>
                    
                    <tr>
                            <td class="labelRight">
                                <fmt:message bundle='${serviceliveCopyBundle}' key='details.primary.substatus'/>:
                            </td>
                            <td>
                            <div id="subStatusString" style="display:block">
                                <c:if test="${not empty summaryDTO.subStatus}">
                                    <fmt:message bundle="${serviceliveCopyBundle}"
                                        key="workflow.substate.${summaryDTO.subStatus}" />
                                </c:if>
                            </div>
                        </td>
                    </tr>
                </table>
            </td>
            <td>
                <table cellpadding="0" cellspacing="0"
                    class="adjustedTableRowPadding">
                    <c:forEach var="so" items="${summaryDTO.statusAndDateList}">
                        <tr>
                            <td class="labelRight"><strong>${so.label}:</strong></td>
                            <td>${so.value}</td>
                        </tr>
                    </c:forEach>
                    <tr><td colspan="2">&nbsp;</td></tr>
                    <tr>
                        <td valign="top" class="labelRight"><fmt:message bundle='${serviceliveCopyBundle}' key='details.appt.dates'/>:</td>
                        <td>${summaryDTO.appointmentDates}</td>
                    </tr>
                    <tr>
                        <td class="labelRight"><fmt:message bundle='${serviceliveCopyBundle}' key='details.service.window'/>:</td>
                        <td>${summaryDTO.serviceWindow}</td>
                    </tr>
                    <c:if test="${not empty summaryDTO.eta}">
                    <tr>
                    <td class="labelRight"><fmt:message bundle='${serviceliveCopyBundle}' key='details.service.eta'/>:</td>
                        <td>${summaryDTO.eta}</td>
                    </tr>
                    </c:if>
                    <c:if test="${not empty summaryDTO.scheduleStatus}">
                    <tr>
                        <td class="labelRight"><fmt:message bundle='${serviceliveCopyBundle}' key='details.schedule.status'/>:</td>
                        <td>${summaryDTO.scheduleStatus}
                        <c:if test="${summaryDTO.scheduleHistory != null && fn:length(summaryDTO.scheduleHistory)>0}">
                        <span id="scheduleHistoryIcon" class="scheduleHistoryIcon">
                            <img src="${staticContextPath}/images/s_icons/magnifier.png" / style="border: none;height: 10px; width: 12px; cursor: pointer;">
                            </span></td>
                        </c:if>
                    </tr>
                     </c:if>
                    <tr>
                    <td></td>
                    </tr>
                    <c:if test="${summaryDTO.serviceWindowComment != null}" >
                    <c:if test="!${fn:contains(summaryDTO.scheduleStatus,'Confirmed')}">
                        <tr>
                            <td colspan="2">[ ${summaryDTO.serviceWindowComment} ]</td>
                        </tr>
                    </c:if>
                    </c:if>
                    
                    <c:if test="${summaryDTO.rescheduleDates != null}">
                    <tr>
                        <td valign="top" class="darkRed labelRight"><fmt:message bundle='${serviceliveCopyBundle}' key='details.reschedule.dates'/>:</td>
                        <td class="darkRed">${summaryDTO.rescheduleDates}</td>
                    </tr>
                    <tr>
                        <td class="darkRed labelRight"><fmt:message bundle='${serviceliveCopyBundle}' key='details.reschedule.window'/>:</td>
                        <td class="darkRed">${summaryDTO.rescheduleServiceWindow}</td>
                    </tr>                    
                    </c:if>
                    
                    
                        
                        <tr><td colspan="2">&nbsp;</td></tr>
                   
                    
                    
                    
                    
                    <c:if test="${summaryDTO.continuationOrderID != null}">
                        <tr>
                            <td class="labelRight"><fmt:message bundle='${serviceliveCopyBundle}' key='details.continuation.soid'/>:</td>
                            <td><a href="#">${summaryDTO.continuationOrderID}</a></td>
                        </tr>
                        <tr>
                            <td class="labelRight"><fmt:message bundle='${serviceliveCopyBundle}' key='details.reason'/>:</td>
                            <td>${summaryDTO.continuationReason}</td>
                        </tr>
                    </c:if>
               </table>
            </td>
        </tr>
    </table>
    <p style="word-wrap: break-word;">
        <fmt:message bundle='${serviceliveCopyBundle}' key='details.title'/>
        <br />
        ${summaryDTO.title}
    </p>
    
    <c:if test="${role == PROVIDER_ROLEID && summaryDTO.status == 110 && showBidNotes}">
        <p>
            <strong>Comments:</strong> ${commentCnt } <a href="javascript:void(0)" onclick="bidcomments('${summaryDTO.id}')">Add Comment</a><br />
            <c:if test="${(summaryDTO.priceModel == 'ZERO_PRICE_BID' && summaryDTO.sealedBidInd == false) || SecurityContext.slAdminInd == true}">
                <strong>Bids:</strong> ${allBidsDTO.numberOfBids} <fmt:formatNumber type="currency" currencySymbol="$" value="${allBidsDTO.lowBid}" /> - <fmt:formatNumber type="currency" currencySymbol="$" value="${allBidsDTO.highBid}" />
                <c:if test="${allBidsDTO.numberOfBids > 0}"><a href="#viewBids">View Bids</a></c:if>
            </c:if>
        </p>
    </c:if>
    <c:if test="${role == PROVIDER_ROLEID && summaryDTO.status == 150}">
        <p>
            <strong>Comments:</strong> ${commentCnt } <br />
            <c:if test="${commentCnt > 0}"><a href="#viewComments">View Comment History</a></c:if>
            <c:if test="${(summaryDTO.priceModel == 'ZERO_PRICE_BID' && summaryDTO.sealedBidInd == false) || SecurityContext.slAdminInd == true}">
                <strong>Bids:</strong> ${allBidsDTO.numberOfBids} <fmt:formatNumber type="currency" currencySymbol="$" value="${allBidsDTO.lowBid}" /> - <fmt:formatNumber type="currency" currencySymbol="$" value="${allBidsDTO.highBid}" />
                <c:if test="${allBidsDTO.numberOfBids > 0}"><a href="#viewBids">View Bid History</a></c:if>
            </c:if>
        </p>
    </c:if>
    <p>
        <fmt:message bundle='${serviceliveCopyBundle}' key='details.overview'/>
        <br />
        <c:choose>
            <c:when test="${fn:length(summaryDTO.overview) > MAX_CHARACTERS_WITHOUT_SCROLLBAR}">
                <div class="inputArea" style="height: 200px;width: 600px" >
                    ${summaryDTO.overview}
                </div>
            </c:when>
            <c:otherwise>
                ${summaryDTO.overview}
            </c:otherwise>
        </c:choose>
    </p>
    <p>
        <fmt:message bundle='${serviceliveCopyBundle}' key='details.buyer.term.conditions'/>
        <br />
        <c:choose>
            <c:when test="${fn:length(summaryDTO.buyersTerms) > MAX_CHARACTERS_WITHOUT_SCROLLBAR}">
                <div class="inputArea" style="height: 200px;width: 600px" >
                    ${summaryDTO.buyersTerms}
                </div>
            </c:when>
            <c:otherwise>
                ${summaryDTO.buyersTerms}
            </c:otherwise>
        </c:choose>
    </p>
    <c:if test="${!(summaryDTO.status == 110 && roleType == 1)}">
    <p>
        <fmt:message bundle='${serviceliveCopyBundle}' key='details.special.instructions'/>
        <br />
        <c:choose>
            <c:when test="${fn:length(summaryDTO.specialInstructions) > MAX_CHARACTERS_WITHOUT_SCROLLBAR}">
                <div class="inputArea" style="height: 200px;width: 600px" >
                    ${summaryDTO.specialInstructions}
                </div>
            </c:when>
            <c:otherwise>
                ${summaryDTO.specialInstructions}
            </c:otherwise>
        </c:choose>
    </p>
    </c:if>
    <c:choose>
    <c:when test="${role == BUYER_ROLEID || (role == PROVIDER_ROLEID && summaryDTO.status != ROUTED_STATUS)}">                    
    <hr />
        <table cellpadding="0" cellspacing="0"
                class="adjustedTableRowPadding" >
                  <c:if test="${summaryDTO.selByerRefDTO != null}">    
                    <tr>
                             <td colspan="2">
                            <fmt:message bundle='${serviceliveCopyBundle}' key='details.buyer.ref'/>
                            </td>
                    </tr>
                   <tr>
                           <td>                            
                              <table cellpadding="0" cellspacing="4">
                                <c:forEach var="byerRef" items="${summaryDTO.selByerRefDTO}" varStatus="rowCounter">
                                    <c:if test="${role == BUYER_ROLEID || (role == PROVIDER_ROLEID && !byerRef.privateInd)}">
                                        <c:if test="${rowCounter.count % 2 == 1}">    
                                            <tr>
                                                <td width="150">       
                                                    <strong><label id="refTypeLbl${rowCounter.count}">${byerRef.refType}</label></strong>
                                                </td>
                                             <td width="150"  style="vertical-align:middle" >
                                                    <label id="refValLbl${rowCounter.count}">${byerRef.refValue}</label>
                                                    <input type="text" id="refVal${rowCounter.count}" value="${byerRef.refValue}" size="15" style="display: none;"/>                                                       
                                                       <c:if test="${byerRef.editable==1 && role == BUYER_ROLEID}">
                                                        <a  href="javascript: void(0);" id="editCustRef${rowCounter.count}" onclick="showSavePanel(${rowCounter.count})">  Edit</a><br>
                                                       </c:if>    
                                               </td>        
                                            </tr>
                                            <tr id="savePanel${rowCounter.count}" style="display: none;" >
                                                    <td width="150"></td>
                                                    <td width="150">
                                                        <input type="button" value="Save" class="saveBtn"
                                                        onclick="submitBuyerReference(${rowCounter.count})" />
                                                        <a href="javascript: void(0);" id="cancelCustRef${rowCounter.count}" onclick="hideSavePanel(${rowCounter.count})"> Cancel</a>
                                                    </td>
                                            </tr>
                                         </c:if>
                                     </c:if>        
                                 </c:forEach>
                             </table>                             
                        </td>
                        <td width="50"></td>
                        <td>                    
                            <table cellpadding="0" cellspacing="4">
                                <c:forEach var="byerRef" items="${summaryDTO.selByerRefDTO}" varStatus="rowCounter">
                                    <c:if test="${role == BUYER_ROLEID || (role == PROVIDER_ROLEID && !byerRef.privateInd)}">       
                                        <c:if test="${rowCounter.count % 2 == 0}">
                                               <tr>        
                                                  <td width="150">             
                                                      <strong><label id="refTypeLbl${rowCounter.count}">${byerRef.refType}</label></strong>
                                                  </td>
                                                   <td width="150" style="vertical-align:middle">
                                                    <label id="refValLbl${rowCounter.count}">${byerRef.refValue}</label>
                                                    <input type="text" id="refVal${rowCounter.count}" value="${byerRef.refValue}" size="15" style="display: none;"/>
                                                    <c:if test="${byerRef.editable==1 && role == BUYER_ROLEID}">
                                                           <a  href="javascript: void(0);" id="editCustRef${rowCounter.count}" onclick="showSavePanel(${rowCounter.count})">  Edit</a><br>
                                                       </c:if>    
                                               </td>        
                                            </tr>
                                            <tr id="savePanel${rowCounter.count}" style="display: none;">
                                                    <td width="150"></td>
                                                    <td width="150">
                                                        <input type="button" value="Save" class="saveBtn"
                                                        onclick="submitBuyerReference(${rowCounter.count})" />
                                                        <a href="javascript: void(0);" id="cancelCustRef${rowCounter.count}" onclick="hideSavePanel(${rowCounter.count})"> Cancel</a>
                                                    </td>
                                            </tr>
                                          </c:if>
                                      </c:if>
                                  </c:forEach>
                               </table>                                
                    </td>
                </tr>                                
            </c:if>
        </table>
        </c:when>
        </c:choose>
        
        <c:choose>
            <c:when test="${summaryDTO.status == CLOSED_STATUS || summaryDTO.status == COMPLETED_STATUS}">                    
        </c:when>
        <c:otherwise>
            <c:if test="${summaryDTO.showUpsellInfo}">
                <!-- any status except for closed/completed -->
                <p>
                    <strong>Add-on Service Opportunities</strong>
                    <br/>
                    This Service Order qualifies for additional authorized add-on services. Merchandise installation covers basic installation, however, the selling associate never actually sees where the merchandise is to be installed...YOU, our Service Provider, are the only one who will have that opportunity. If, for any reason, you are unable to complete the scope of work described on this service order, thoroughly explain why to the customer, and then quote a selling price for additional work to be done. To arrive at the selling price, simply refer to the Authorized Add-On menu attached to every applicable Service Order.  
                </p>
    
                
                <table class="globalTableLook" cellspacing="0" cellpadding="0" border="0" width="95%" style="margin-bottom: 0px;">
                    <thead>
                        <tr>
                            <th class="col1 odd first" style="border-left: 1px solid silver;">SKU#</th>
                            <th class="col2 even textleft">Description</th>
                            <th class="col3 odd">Provider Paid</th>
                            <th class="col4 even" style="border-right: 1px solid silver;">End Customer Charge</th>
                        </tr>
                    </thead>
                    <tbody>                    
                        <c:forEach var="addonServiceRowDTO" items="${summaryDTO.upsellInfo.addonServicesList}" varStatus="rowCounter">
                            <c:set var="fontWeight" value=""/>
                            <c:if test="${addonServiceRowDTO.coverage == 'CC'}">
                                <c:set var="fontWeight" value="font-weight:bold"/>
                            </c:if>
                            <tr>
                                <td class="col1 odd first" style="border-left: 1px solid silver;${fontWeight}">${addonServiceRowDTO.sku}</td>
                                <td class="col2 even textleft" style="${fontWeight}">${addonServiceRowDTO.description}</td>
                                <c:if test="${addonServiceRowDTO.misc}">
                                    <td class="col3 odd" style="text-align: right;${fontWeight}">${100-100*addonServiceRowDTO.margin}%</td>
                                    <td class="col4 even" style="border-right: 1px solid silver; text-align: right;${fontWeight}"></td>
                                </c:if>
                                <c:if test="${!addonServiceRowDTO.misc}">
                                    <td class="col3 odd" style="text-align: right;${fontWeight}">&nbsp;
                                        
                                        <c:choose>
                                        <c:when test="${addonServiceRowDTO.skipReqAddon}">
                                            <s>
                                               <fmt:formatNumber value="${addonServiceRowDTO.providerPaid}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"  />
                                            </s>
                                            <br/>  (Included in Maximum Price)
                                        </c:when>
                                        
                                        <c:otherwise>
                                            <fmt:formatNumber value="${addonServiceRowDTO.providerPaid}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"  />
                                        </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="col4 even" style="border-right: 1px solid silver; text-align: right;${fontWeight}">&nbsp;<fmt:formatNumber value="${addonServiceRowDTO.endCustomerCharge}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>            
            </c:if>    
        </c:otherwise>
    </c:choose>    
</div>
</div>
</body>
</html>