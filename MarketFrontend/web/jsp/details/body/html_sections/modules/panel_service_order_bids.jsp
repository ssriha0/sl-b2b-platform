<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<c:set var="BUYER_PROVIDES_PART" value="<%= OrderConstants.SOW_SOW_BUYER_PROVIDES_PART%>" />
<c:set var="PROVIDER_PROVIDES_PART" value="<%= OrderConstants.SOW_SOW_PROVIDER_PROVIDES_PART%>" />
<c:set var="PARTS_NOT_REQUIRED" value="<%= OrderConstants.SOW_SOW_PARTS_NOT_REQUIRED%>" />
<c:set var="BUYER_ROLEID" value="<%= new Integer(OrderConstants.BUYER_ROLEID)%>" />
<c:set var="PROVIDER_ROLEID" value="<%= new Integer(OrderConstants.PROVIDER_ROLEID)%>" />
<c:set var="ROUTED_STATUS" value="<%= new Integer(OrderConstants.ROUTED_STATUS)%>" />
<c:set var="CLOSED_STATUS" value="<%= new Integer(OrderConstants.CLOSED_STATUS)%>" />
<c:set var="SIMPLE_BUYER_ROLEID" value="<%= new Integer(OrderConstants.SIMPLE_BUYER_ROLEID)%>" />
<c:set var="MAX_CHARACTERS_WITHOUT_SCROLLBAR" value="<%= OrderConstants.MAX_CHARACTERS_WITHOUT_SCROLLBAR%>" />
<c:set var="divName" value="soBids${summaryDTO.id}"/>
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
<c:set var="bodyName" value="${divName}_menu_body"/>
<c:set var="bodyName" value="${bodyName}${summaryDTO.id}"/>
<c:set var="soBidsImage" value="soBidsImage"/>
<c:set var="soBidsImage" value="${soBidsImage}${summaryDTO.id}"/>
	    
<script type="text/javascript">
  function expandServiceOrderBids(id,path){
var hidId="group"+id;
var testGroup=document.getElementById(hidId).value;
var divId="<c:out value="${divName}" />"+id;
var bodyId="<c:out value="${divName}" />_menu_body"+id;
if(testGroup=="menu_list"){
jQuery("#"+divId+" p.menu_head").css('background-image',"url("+path+"/images/widgets/titleBarBg.gif)");
jQuery("#"+bodyId).slideToggle(300);
}else{
jQuery("#"+divId+" p.menugroup_head").css('background-image',"url("+path+"/images/widgets/titleBarBg.gif)");
jQuery("#"+bodyId).slideToggle(300);
}
var ob=document.getElementById('soBidsImage'+id).src;
if(ob.indexOf('arrowRight')!=-1){
document.getElementById('soBidsImage'+id).src=path+"/images/widgets/arrowDown.gif";
}
if(ob.indexOf('arrowDown')!=-1){
document.getElementById('soBidsImage'+id).src=path+"/images/widgets/arrowRight.gif";
}
}
function expandSubMenu(count,id,path){
var divId="subName"+count+id;
var bodyId="subBody"+count+id;
jQuery("#"+divId+" p.submenu_head").css({backgroundImage:"url("+path+"/images/widgets/subtitleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
var ob=document.getElementById('subsoBidsImage'+count+id).src;
if(ob.indexOf('arrowRight')!=-1){
document.getElementById('subsoBidsImage'+count+id).src=path+"/images/widgets/arrowDown.gif";
}
if(ob.indexOf('arrowDown')!=-1){
document.getElementById('subsoBidsImage'+count+id).src=path+"/images/widgets/arrowRight.gif";
}
}

jQuery(document).ready(function($){
jQuery('#soBidsModule >table:eq(0) >thead >tr >th').click(function(){
var bgImage = jQuery('#soBidsModule >table:eq(0) >thead>tr>th').css('background-image');
var direction = 'Down';
if(bgImage.indexOf('Down')>0){
direction = 'Right';
}

jQuery('#soBidsModule >table:eq(0) >thead>tr>th').css('background-image','url(/ServiceLiveWebUtil/images/icons/arrow'+direction+'-darkgray.gif)');
jQuery('#soBidsModule >table:eq(0) >tbody').toggle();
});

jQuery('#soBidsModule >table:eq(1) >thead >tr >th').click(function(){
var bgImage = jQuery('#soBidsModule >table:eq(1) >thead>tr>th').css('background-image');
var direction = 'Down';
if(bgImage.indexOf('Down')>0){
direction = 'Right';
}
jQuery('#soBidsModule >table:eq(1) >thead>tr>th').css('background-image','url(/ServiceLiveWebUtil/images/icons/arrow'+direction+'-darkgray.gif)');
jQuery('#soBidsModule >table:eq(1) >tbody >tr').toggle();
});

jQuery('#showHidePreviousBid').click(function(){

var dText = jQuery('#showHidePreviousBid').text();
	jQuery('#soBidsYourPreviousBid').toggle();
	if(dText.indexOf('Show')!=-1){
		dText = dText.replace(/Show/, 'Hide');
		dText = dText.replace(/\+/,'-');
	}else if (dText.indexOf('Hide')!=-1){
		dText = dText.replace(/Hide/, 'Show');
		dText = dText.replace(/\-/,'+');
	}
	jQuery('#showHidePreviousBid').text(dText);
	return false;
});

});

</script>

<div id="${divName}" class="${groupVal}">
	 <p class="${headClass}" onclick="expandServiceOrderBids('${summaryDTO.id}','${staticContextPath}')">&nbsp;<img id="${soBidsImage}" src="${staticContextPath}/images/widgets/arrowDown.gif"/>
	 	&nbsp;<a name="viewBids">Service Order Bids</a>
	 </p>
	    <div id="${bodyName}" class="${bodyClass}" style="padding-left:0;">
				
				<div id="soBidsModule">
				<div id="soBidsModuleHeader">
				
				<c:choose>
				<c:when test="${SecurityContext.roleId == PROVIDER_ROLEID && summaryDTO.sealedBidInd == true  && SecurityContext.slAdminInd == false }">
					<div class="sealedBidNote">This order is receiving Sealed bids, so you can only see pricing for your own bid, and for other providers within your
						company. Providers within other companies who can bid on this order will not see your bid.
					</div>
					<c:if test="${allBidsDTO.myCurrentBid == null}">
				 		<span id="msgAddYourBid">Add your bid using the form on the right.</span>
					</c:if>
				</c:when>
				
				<c:otherwise>
				<h2 id="hdrTotalBids">
					Total Bids: ${allBidsDTO.numberOfBids}
					<c:if test="${allBidsDTO.myCurrentBid == null}">
							<span id="msgAddYourBid">Add your bid using the form on the right.</span>						
					</c:if>					
				</h2>

				<c:if test="${allBidsDTO.numberOfBids > 0}">
					<h2 id="hdrRange">Range: <span style="font-weight:normal;"><fmt:formatNumber type="currency" currencySymbol="$" value="${allBidsDTO.lowBid}" /> - <fmt:formatNumber type="currency" currencySymbol="$" value="${allBidsDTO.highBid}" /></span></h2>
				</c:if>
				<br style="clear:both;"/>
				</c:otherwise>
				</c:choose>
				</div>

			<div id="soBidsYourBid">
				<c:if test="${allBidsDTO.myCurrentBid != null}">
				<div class="firstCol">
					<h3>
						Your bid:
						<span style="font-weight: normal;"><fmt:formatDate
								value="${allBidsDTO.myCurrentBid.dateOfBid}"
								pattern="MM/dd/yy hh:mm aa" />
						</span>
					</h3>
					<div class="item">
						<p class="value">
						<c:choose>
							<c:when test="${allBidsDTO.myCurrentBid.laborRateIsHourly}">
								<fmt:formatNumber type="currency" currencySymbol="$">${allBidsDTO.myCurrentBid.calculatedHourlyRate}</fmt:formatNumber>
							</c:when>
							<c:otherwise>
								N/A
							</c:otherwise>						
						</c:choose>
						</p>
						<p class="label">
							Hourly Rate:
						</p>
					</div>
					<div class="item">
						<p class="value">
						<c:choose>
							<c:when test="${allBidsDTO.myCurrentBid.laborRateIsHourly}">
								<fmt:formatNumber type="number"
									value="${allBidsDTO.myCurrentBid.totalHours}" />
								Hours
							</c:when>
							<c:otherwise>
								N/A
							</c:otherwise>						
						</c:choose>							
						</p>
						<p class="label">
							Estimated Time to Complete:
						</p>
					</div>
					<div class="item">
						<p class="value">
							<fmt:formatNumber type="currency" currencySymbol="$">${allBidsDTO.myCurrentBid.totalLabor}</fmt:formatNumber>
						</p>
						<p class="label">
							Total Labor:
						</p>
					</div>
					<div class="item">
						<p class="value">
							<fmt:formatNumber type="currency" currencySymbol="$">${allBidsDTO.myCurrentBid.partsMaterials}</fmt:formatNumber>
						</p>
						<p class="label">
							Materials Estimate:
						</p>
					</div>
					<div class="item">
						<p class="value" style="font-weight: bold;">
							<fmt:formatNumber type="currency" currencySymbol="$">${allBidsDTO.myCurrentBid.total}</fmt:formatNumber>
						</p>
						<p class="label">
							Total Job Cost:
						</p>
					</div>
				</div>

				<div class="secondCol">
					<h3>
					<c:choose>
						<c:when test="${allBidsDTO.myCurrentBid.bidExpired }">Expired on </c:when>
						<c:otherwise>Expires: </c:otherwise>
					</c:choose>	
						<span style="font-weight: normal;">
						
						<c:choose>
						<c:when test="${allBidsDTO.myCurrentBid.bidExpirationDatepicker != null}">
							${allBidsDTO.myCurrentBid.bidExpirationDatepicker}
						</c:when>
							<c:otherwise>
							<fmt:formatDate value="${summaryDTO.serviceDate1}" pattern="MM/dd/yy" />
						</c:otherwise>
					</c:choose>	
					
						</span>
					</h3>
					<c:if
						test="${allBidsDTO.myCurrentBid.newDateByRangeFrom != null || allBidsDTO.myCurrentBid.newDateBySpecificDate != null}">
						<p>
							<strong>New Service Date Request:</strong>
							<c:if
								test="${allBidsDTO.myCurrentBid.newDateByRangeFrom != null && allBidsDTO.myCurrentBid.newDateByRangeTo != null}">
						${allBidsDTO.myCurrentBid.newDateByRangeFrom} to ${allBidsDTO.myCurrentBid.newDateByRangeTo}
					</c:if>
							<c:if
								test="${allBidsDTO.myCurrentBid.newDateBySpecificDate != null}">
						${allBidsDTO.myCurrentBid.newDateBySpecificDate}
					</c:if>
						</p>
					</c:if>
					
				</div>


				<div class="thirdCol">
					<h2 style="text-align: right; width: 100%;">
						<fmt:formatNumber type="currency" currencySymbol="$">${allBidsDTO.myCurrentBid.total}</fmt:formatNumber>
					</h2>
				</div>

				<p style="overflow: hidden; clear: left;">
					${allBidsDTO.myCurrentBid.comment}
				</p>

				<c:if test="${allBidsDTO.myPreviousBid != null}">
					<p style="clear: left;">
						<a href="#" id="showHidePreviousBid">Show Your Previous Bid +</a>
					</p>
				</c:if>
				</c:if>
			</div>
		

		<c:if test="${allBidsDTO.myPreviousBid != null}">
			<div id="soBidsYourPreviousBid">
				<div class="firstCol">
					<h3>
						Your previous bid:
						<span style="font-weight: normal;"><fmt:formatDate
									value="${allBidsDTO.myPreviousBid.dateOfBid}"
									pattern="MM/dd/yy hh:mm aa" />
						</span>
					</h3>
					<div class="item">
						<p class="value">
						<c:choose>
							<c:when test="${allBidsDTO.myPreviousBid.laborRateIsHourly}">
								<fmt:formatNumber type="currency" currencySymbol="$">${allBidsDTO.myPreviousBid.calculatedHourlyRate}</fmt:formatNumber>
							</c:when>
							<c:otherwise>
								N/A
							</c:otherwise>
						</c:choose>
						</p>
						<p class="label">
							Hourly Rate:
						</p>
					</div>
					<div class="item">
						<p class="value">
						<c:choose>
							<c:when test="${allBidsDTO.myPreviousBid.laborRateIsHourly}">
								<fmt:formatNumber type="number" value="${allBidsDTO.myPreviousBid.totalHours}" />
								Hours
							</c:when>
							<c:otherwise>
								N/A
							</c:otherwise>
						</c:choose>
						</p>
						<p class="label">
							Estimated Time to Complete:
						</p>
					</div>
					<div class="item">
						<p class="value">
							<fmt:formatNumber type="currency" currencySymbol="$">${allBidsDTO.myPreviousBid.totalLabor}</fmt:formatNumber>
						</p>
						<p class="label">Total Labor:</p>
					</div>
					<div class="item">
						<p class="value">
							<fmt:formatNumber type="currency" currencySymbol="$">${allBidsDTO.myPreviousBid.partsMaterials}</fmt:formatNumber>
						</p>
						<p class="label">Materials Estimate:</p>
					</div>
					<div class="item">
						<p class="value" style="font-weight: bold;">
							<fmt:formatNumber type="currency" currencySymbol="$">${allBidsDTO.myPreviousBid.total}</fmt:formatNumber>
						</p>
						<p class="label">Total Job Cost:</p>
					</div>
				</div>

				<div class="secondCol">
					<h3>
					<c:choose>
						<c:when test="${allBidsDTO.myPreviousBid.bidExpired }">Expired on </c:when>
						<c:otherwise>Expires: </c:otherwise>
					</c:choose>
						<span style="font-weight: normal;">${allBidsDTO.myPreviousBid.bidExpirationDatepicker}</span>
					</h3>
					<c:if test="${allBidsDTO.myPreviousBid.newDateByRangeFrom != null || allBidsDTO.myPreviousBid.newDateBySpecificDate != null}">
						<p><strong>New Service Date Request:</strong>
							<c:if test="${allBidsDTO.myPreviousBid.newDateByRangeFrom != null && allBidsDTO.myPreviousBid.newDateByRangeTo != null}">
						${allBidsDTO.myPreviousBid.newDateByRangeFrom} to ${allBidsDTO.myPreviousBid.newDateByRangeTo}
							</c:if>
						<c:if test="${allBidsDTO.myPreviousBid.newDateBySpecificDate != null}">
							${allBidsDTO.myPreviousBid.newDateBySpecificDate}
						</c:if>
						</p>
					</c:if>
				</div>
				<div class="thirdCol">
					<h2 style="text-align: right; width: 100%;">
						<fmt:formatNumber type="currency" currencySymbol="$">${allBidsDTO.myPreviousBid.total}</fmt:formatNumber>
					</h2>
				</div>
				<br style="clear: both;" />
			</div> 
		</c:if>



			<%-- Bid from my Company--%>
				<c:if test="${fn:length(allBidsDTO.otherBidsFromMyCompany) > 0}">				
					<c:set var="bidTableTitle" value="Other Bids from Your Company" scope="request"/>
					<c:set var="showBidTableComment" value="false" scope="request"/>
					<c:set var="bidList" value="${allBidsDTO.otherBidsFromMyCompany}" scope="request"/>
					<jsp:include page="/jsp/details/body/html_sections/modules/summary_tab/sod_bids_table.jsp"></jsp:include>
				</c:if>
				
				
				<%-- All other bids --%>
				<c:if test="${(SecurityContext.roleId == PROVIDER_ROLEID && SecurityContext.slAdminInd == true) || summaryDTO.sealedBidInd == false}">
				<c:if test="${fn:length(allBidsDTO.allOtherBids) > 0}">								
					<c:set var="bidTableTitle" value="All Other Bids" scope="request"/>
					<c:set var="showBidTableComment" value="true" scope="request"/>
					<c:set var="bidList" value="${allBidsDTO.allOtherBids}" scope="request"/>
					<jsp:include page="/jsp/details/body/html_sections/modules/summary_tab/sod_bids_table.jsp"></jsp:include>				
				</c:if>
				</c:if>
				</div>
		
		</div>
	</div>