<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<html>
<head>
<style>
.picked {
	font-weight: normal;
	border: 1px solid #BBBBBB;
	width:auto;
	padding: 2px 5px;
	background: url(${staticContextPath}/images/common/multiselect.gif) no-repeat scroll #ffffff;
	background-position: right;
	zoom: 1;
	cursor: pointer;
	-webkit-border-radius:4px;
	-moz-border-radius:4px;
	border-radius:4px;
}

.picked:hover {
	background:url(${staticContextPath}/images/common/multiselect-hover.gif) no-repeat scroll #f4f4f4 ;
	background-position: right;
	cursor: pointer;
}

.picked label {
	cursor: pointer;
	padding:0px;
}

.picked label:hover {
	cursor: pointer;
	padding:0px;
}

.select-options {
	-moz-border-radius:0px!important;
	border-radius:5px;
	background:#33393C;
	color:  #FFF;
	float: right;
	padding: 10px;
	position: absolute;
	width:  auto;
	max-height: 350px;
	_height: 250px;
	overflow: auto;
	display: none;
	z-index:200;
	margin-left:70px;
	margin-top:24px;
	text-align:none;
}

.select-list {
		position: absolute;
	color:  #FFF;
	padding: 2px;
		z-index:200;
}
.select-list ul {
	list-style: none;
	color: #FFFFFF;
	background: #33393C;
	
}
.select-list ul li{
	width: 150px;
	margin-left: -20px;
    border-bottom: 1px solid #AAAAAA;
    padding: 2px;
    text-align: left;
    padding-top: 4px;
	background:#33393C;
}
.select-list ul li:hover{
    background: none repeat scroll 0 0 #3385FF;
    border-bottom: 1px solid #AAAAAA;
    color: #FFFFFF;
    cursor: pointer;
}
</style>
<script type="text/javascript">
function showBuyerDropDown() {
	hideOtherFilter();
	$("#selectBuyerOptions").show();
}

function showMemStatusDropDown() {
	hideOtherFilter();
	$("#selectMemStatusOptions").show();
}
function setDefaultBuyerValue(obj){
var cl = $(obj).attr("class");
var count = $("."+cl+"[type='checkbox']:checked").length;
if (count > 0) {
	$('#defaultBuyerValr').html(count + " selected");
	$("#filterInd").val("1");
	$("#buyerResetInd").val("0");
} else{
	var prevVal = $('#defaultBuyerValr').html();
	//To apply filter when user un checks all the selections
	if("-Select-" !== prevVal){
		$("#filterInd").val("1");
		$("#buyerResetInd").val("1");
	}
	$('#defaultBuyerValr').html("-Select-");
}
}

function setDefaultMemStatusValue(obj){
	var cl = $(obj).attr("class");

	var count = $("."+cl+"[type='checkbox']:checked").length;
	if (count > 0) {
		$('#defaultMemStatus').html(count + " selected");
		$("#filterInd").val("1");
		$("#memStatusResetInd").val("0");
	} else{
		var prevVal = $('#defaultMemStatus').html();
		//To apply filter when user un checks all the selections
		if("-Select-" !== prevVal){
			$("#filterInd").val("1");
			$("#memStatusResetInd").val("1");
		}
		$('#defaultMemStatus').html("-Select-");
	}
	}
	
$(document).click(function(e) {
	var click = $(e.target);
	if (!click.hasClass("picked") && !click.parents().hasClass("picked") && !click.hasClass("select-options") && !click.parents().hasClass("select-options")){
		$(".select-options").hide();
		$(".select-list").hide();
		if($("#buyerResetInd").val()==1 || $("#memStatusResetInd").val()==1 || $("#filterInd").val()==1){
			var formData = jQuery('#spnMonitorAction_loadSPNMonitor').serialize();
            $('#monitor').html('<img src="${staticContextPath}/images/loading.gif" width="927px" height="300px">');

			$('#monitor').load('spnMonitorAction_loadMonitorAjax.action', formData, function(){
    		});
		}
	} 

}); 
/* function showMemStatusDropDown(){
	hideOtherFilter("memStatus");
	var a = $("#selectMemStatusOptions ul").html();
	if(null != a){
		if($('#defaultMemStatus').html()!="-Select-" && a.indexOf("-Select-") < 0){
			$("#selectMemStatusOptions ul").prepend("<li onclick='selectMemStatus(this);' id='mem_status_li_${countStat.count}' val='-Select-' value='-Select-'>-Select-</li>");
		}else if($('#defaultMemStatus').html()=="-Select-" && a.indexOf("-Select-") > 0){
			$("#selectMemStatusOptions ul li:first").remove();
		}
	}	
	if($("#selectMemStatusOptions").children().length>0){
		$("#selectMemStatusOptions").show();
	}
} */
function selectMemStatus(obj){
	//id or val can be used as the selected value.
	var id = $(obj).attr("id");
	var val = $("#"+id).attr("val");
	var value = $("#"+id).html();
	if(val == '-Select-'){
			$("#memStatusResetInd").val("1");
			$("#filterInd").val("1");

		}
	else{
			$("#memStatusResetInd").val("0");
			$("#filterInd").val("1");
		}
	$("#defaultMemStatus").html(value);
	$("#selectedMemStatus").val(val);
	$("#selectedFilterMemStatus").val(value);
	$("#selectMemStatusOptions").hide();

}
function hideOtherFilter(){
		$(".select-list").hide();
}



$('.expandSpnRow').click(function() {
	var spnId = $(this).children('[name=spnId]').val();
	
	togglePlusMinusImage($(this).children('.plus-image,.minus-image'));
	
	var expandAreaId = "#" + spnId + "_expandArea";
	
	$(expandAreaId).toggle();
	//alert('spnId: ' + spnId + ' expandAreaId: ' + expandAreaId);
	
	// Chris - If you want to revert to the 'old' non-Ajax way of doing things, comment out the next 3 lines.
	if ($(this).children('.minus-image').length > 0)
	{
		$(expandAreaId).load('spnMonitorAction_loadSPNAjax.action',  { 'spnId': spnId});
	}				
	return false;//so that page won't jump
});
</script>
</head>
<body>
<c:if test="${incompleteSpnInd}">
	<div class="warningBox clearfix" width="100%">
		<p class="warningMsg contentWellPane">
			<span>
				&nbsp;<fmt:message bundle="${serviceliveCopyBundle}" key="spn.membershipstatus.incomplete.warning.msg"/>		
			</span>
		</p>
	</div>
</c:if> 
<br>
<div class="filters clearfix fby">
	<s:form action="spnMonitorAction_loadSPNMonitor"
			id="spnMonitorAction_loadSPNMonitor"
			name="spnMonitorAction_loadSPNMonitor" method="post"
			enctype="multipart/form-data" theme="simple" validate="true">
														<input type="hidden" id="filterInd" name="filterInd" value="" />
											<input type="hidden" id="memStatusResetInd" name="memStatusResetInd" value="" />
											<input type="hidden" id="buyerResetInd" name="buyerResetInd" value="" />
									<strong>Filter by:</strong>
									<strong>Buyer</strong> 
									<div id="buyerDropDownrs" class="picked pickedClick"
										style="width: 158px; float: left;margin-left: 0px; margin-top:-1px;height:16px; overflow-y:visible;" onclick="showBuyerDropDown();">
										<c:choose>
											<c:when test="${fn:length(selectedBuyerValues) > 0}">
												<label style="width:60px;" id="defaultBuyerValr">${fn:length(selectedBuyerValues)} selected</label>
											</c:when>
											<c:otherwise><label style="width:60px;" id="defaultBuyerValr">-Select-</label></c:otherwise>
										</c:choose>
									</div> 
									<div class="select-options" id="selectBuyerOptions"
										style="display: none;width: 150px; margin-top:26px; margin-left:110px;">
										<c:if test="${null != buyerFilter}">
										<c:forEach var="buyer" items="${buyerFilter}" varStatus="i">
											<c:set var="val2" value="0"></c:set>
											<c:forEach var="selectedBuyer" items="${selectedBuyerValues}">
												<c:if test="${buyer.descr == selectedBuyer}">
												<div style="clear:left;padding-top:5px;"> 
													<div style="float: left;"> 
													<input type="checkbox" checked="checked" class="buyerOtpions"
													name="selectedBuyerValues[${i.count}]" value="${buyer.descr}" id="${i.count}"
													onclick="setDefaultBuyerValue(this)"/>
													</div>
													<div style="padding-left:3px; word-wrap:break-word;">
														${buyer.descr}
													 </div>
												</div >
													<c:set var="val2" value="1"></c:set>
												</c:if>
											</c:forEach> 
											<c:if test="${val2 == 0}"> 
											<div style="clear:left;padding-top:5px;">
												<div style="float: left;">
												<input type="checkbox" class="buyerOtpions"
													id="${i.count}" name="selectedBuyerValues[${i.count}]" value="${buyer.descr}" class="check"
													onclick="setDefaultBuyerValue(this)" />
												</div>
													<div style="word-wrap:break-word;padding-left:3px;">${buyer.descr}
												 </div>
												</div>
											</c:if>
										</c:forEach>
										</c:if>
								</div>
									
									<strong>Membership Status</strong> 
											<div id="selectMemStatus" class="picked pickedClick!important"
										style="width: 158px; float: left;margin-left: 0px; margin-top:-1px;height:16px; overflow-y:visible;" onclick="showMemStatusDropDown();">
										<c:choose>
											<c:when test="${fn:length(selectedFilterMemStatus) > 0}">
												<label style="width:60px;" id="defaultMemStatus">${fn:length(selectedFilterMemStatus)} selected</label>
											</c:when>
											<c:otherwise><label style="width:60px;" id="defaultMemStatus">-Select-</label></c:otherwise>
										</c:choose>
									</div> 
									<div class="select-options" id="selectMemStatusOptions"
										style="display: none;width: 150px; margin-top:26px; margin-left: 407px;">
										<c:if test="${null != membershipStatusFilter}">
										<c:forEach var="membershipStatus" items="${membershipStatusFilter}" varStatus="i">
											<c:set var="val2" value="0"></c:set>
											<c:forEach var="selectedMemStatus" items="${selectedFilterMemStatus}">
												<c:if test="${membershipStatus.descr == selectedMemStatus}">
												<div style="clear:left;padding-top:5px;"> 
													<div style="float: left;"> 
													<input type="checkbox" checked="checked" class="memOtpions"
													name="selectedMemStatus[${i.count}]" value="${membershipStatus.descr}" id="${i.count}"
													onclick="setDefaultMemStatusValue(this)"/>
													</div>
													<div style="float: left;padding-left:3px;">
														${membershipStatus.descr}
													 </div>
												</div >
													<c:set var="val2" value="1"></c:set>
												</c:if>
											</c:forEach> 
											<c:if test="${val2 == 0}"> 
											<div style="clear:left;padding-top:5px;">
												<div style="float: left;">
												<input type="checkbox" class="memOtpions"
													id="${i.count}" name="selectedMemStatus[${i.count}]" value="${membershipStatus.descr}" class="check"
													onclick="setDefaultMemStatusValue(this)" />
												</div>
													<div style="float: left;padding-left:3px;">${membershipStatus.descr}<br />
												 </div>
												</div>
											</c:if>
										</c:forEach>
										</c:if>
								</div>
										
	</s:form>
				</div>
<div class="tableWrap">
	<c:choose>
	<c:when test="${spnMonitorList!=null && fn:length(spnMonitorList) > 0}">
	<table cellpadding="6" cellspacing="0">
	<thead>
	<tr>
		<th class="thheader" style="border-left:1px solid #CCCCCC;border-right:1px solid #CCCCCC;">Membership Status</th>
		<th class="tl"  style="border-left:1px solid #CCCCCC;border-right:1px solid #CCCCCC;"><abbr title="Select Provider Network">Select Provider Networks</abbr></th> 
		<th class="thheader" style="border-left:1px solid #CCCCCC;border-right:1px solid #CCCCCC;">Buyer</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${spnMonitorList}" var="row">
	<tr>
		<td class="thheader"  style="border-left:1px solid #CCCCCC;">
			
				<c:choose>
				<c:when test="${row.membershipStatus =='Incomplete'}">
					<img src="${staticContextPath}/images/common/status-yellow.png" alt="Incomplete" />
				</c:when>
				<c:when test="${row.membershipStatus =='Pending Approval'}">
					<img src="${staticContextPath}/images/common/status-white.png" alt="Pending Approval" />
				</c:when>
				<c:when test="${row.membershipStatus =='Member'}">
					<img src="${staticContextPath}/images/common/status-green.png" alt="Member" />
				</c:when>
				<c:when test="${row.membershipStatus =='Membership Declined' || row.membershipStatus =='Declined'}">
					<img src="${staticContextPath}/images/common/status-black.png" alt="Membership Declined" />
				</c:when>									
				<c:when test="${row.membershipStatus =='Membership Inactive'}">
					<img src="${staticContextPath}/images/common/status-red.png" alt="Membership Inactive" />
				</c:when>
				<c:when test="${row.membershipStatus =='Not Interested'}">
					<img src="${staticContextPath}/images/common/status-yellow.png" alt="Not Interested" />
				</c:when>
				<c:when test="${row.membershipStatus =='Invited'}">
					<img src="${staticContextPath}/images/common/status-yellow.png" alt="Invited" />
				</c:when>	
				</c:choose>								
				<p class="sm">${row.membershipStatus }</p>
			
		</td>
		<td class="tl" style="border-left:1px solid #CCCCCC;border-right:1px solid #CCCCCC;">
			<a class="c-cat expandSpnRow" id="${row.spnId}_expandLink" href="javascript:void(0);">
				<span>${row.spnName}</span>
				<span id="${expandedImgSpn}" class="plus-image" ></span>
				<input type="hidden" name="spnId" value="${row.spnId}" />
			</a>
			<c:choose>	
			
			<c:when test="${row.providerFirmState == 'PF APPLICANT INCOMPLETE'}">
				<div class="comment"><strong class="message"><fmt:message bundle="${serviceliveCopyBundle}" key="spn.membershipstatus.pfapplicantincomplete.message1"/></strong></div>
			</c:when>
			<c:when test="${row.providerFirmState == 'PF SPN INTERESTED'}">											
			</c:when>
			<c:when test="${row.providerFirmState == 'PF SPN MEMBER'}">
			</c:when>
			<c:when test="${row.providerFirmState == 'PF SPN MEMBERSHIP UNDER REVIEW'}">
				<div class="comment"><strong class="message"><fmt:message bundle="${serviceliveCopyBundle}" key="spn.membershipstatus.pfspnmembershipunderreview.message1"/></strong> &nbsp;<fmt:message bundle="${serviceliveCopyBundle}" key="spn.membershipstatus.pfspnmembershipunderreview.message2"/>  </div>
			</c:when>
			<c:when test="${row.providerFirmState =='PF SPN APPLICANT'}">
				<div class="comment"><strong class="message"><fmt:message bundle="${serviceliveCopyBundle}" key="spn.membershipstatus.pfspnapplicant.message1"/></strong> &nbsp;<fmt:message bundle="${serviceliveCopyBundle}" key="spn.membershipstatus.pfspnapplicant.message2"/>  </div>
			</c:when>
			<c:when test="${row.providerFirmState =='PF SPN REMOVED FIRM'}">
				<div class="comment"><strong class="message"><fmt:message bundle="${serviceliveCopyBundle}" key="spn.membershipstatus.pfspnremovedfirm.message1"/></strong></div>
			</c:when>
			<c:when test="${row.providerFirmState =='PF FIRM OUT OF COMPLIANCE'}">
				<div class="comment"><strong class="message"><fmt:message bundle="${serviceliveCopyBundle}" key="spn.membershipstatus.pffirmoutofcompliance.message1"/></strong> &nbsp;<fmt:message bundle="${serviceliveCopyBundle}" key="spn.membershipstatus.pffirmoutofcompliance.message2"/>  </div>
			</c:when>
			</c:choose>
		</td>
		<td class="tl" style="padding-left: 15px;border-left:1px solid #CCCCCC;border-right:1px solid #CCCCCC;">
			<span style="color: #00A0D2;">
				${row.buyerName}
			</span>
			 <br/> ID# ${row.buyerId}
		</td>
	</tr>
	
	<c:choose>
	<c:when test="0">
		<tr class="info">
			<td colspan="3" class="hide" id="${row.spnId}_expandArea">
				<%@ include file="spnMainMonitorTableExpand.jsp" %>
			</td>
		</tr>
	</c:when>
	<c:otherwise>			
		<tr class="info">
			<td colspan="3" >
				<div id="${row.spnId}_expandArea"  class="hide">
				</div>
			</td>
		</tr>
	</c:otherwise>
	</c:choose>
	
	</c:forEach>
	</tbody>
	</table>
	</c:when>
	<c:otherwise>
	<br/>
	<strong style="margin-left:5%;">No data available for the filter applied.</strong>
	</c:otherwise>
	</c:choose>
</div>
</body>
</html>