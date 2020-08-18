<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="theId" scope="request"
	value="<%=request.getAttribute("tab")%>" />
<c:set var="role" value="${roleType}" />


<script type="text/javascript">

function collapse(obj){
jQuery(obj).css({backgroundImage:"url(${staticContextPath}/images/titleBarBg.gif)"}).next("div.menugroupadmin_body").slideToggle(300);
}

</script>


<div id="adminMemberPaneBuyer" class="menugroupadmin_list">
  	<p class="menugroupadmin_head" onClick="collapse(this);"><img class="l" src="${staticContextPath}/images/widgets/arrowDown.gif"></img>&nbsp;Orders for Today - Provider | Firm</p>
    <div class="menugroupadmin_body" id="adminMemberPaneBuyerId" >
	<span class="dijitInfoNodeInner " style="border:0px;"> <a href="#"> </a> </span>
		<c:if test="${widgetProResultsDTOObj.showProgressBar == true}"> 
				 <table width ="250px" "border="0" cellpadding="0" cellspacing="0">
				<tr width="100%">
					<td width="100%" colspan="3" style="padding:6px;border:0px;">
					<b>Current SO (${widgetProResultsDTOObj.soId})&nbsp;Progress </b>
					</td>
				</tr>
				<tr width="100%">
					<td width ="65px" style="border:0px;">${widgetProResultsDTOObj.serviceTimeStart}</td>
					<td width ="120px"id="progressBarContainer" style="border:0px;" >
						<div id="progressbar_test" style="border:solid 1px gray;display:block;position:relative;z-index:100;">
						<span style="width:1px;height:30px;background-color:black;left:50%;top:-5px;z-index:99;position:absolute;">&nbsp;</span>
						<div style="height:20px;width:${widgetProResultsDTOObj.percentage}%;border:1px #000000;Top:0;Left:0;background-color:${widgetProResultsDTOObj.color};position:relative;z-index:100;">&nbsp;						</div>
						</div>
					</td> 
					<td width ="65px"align="right" style="padding-right:2px;border:0px;">${widgetProResultsDTOObj.serviceTimeEnd}</td>
				</tr>	
				</table>
	
		</c:if>
	<table width ="100% "border="0" cellpadding="0" cellspacing="0">
		<tr width="100%">
			<td colspan="2" style="border:0px;">
				<b>Orders remaining in schedule today:</b>
			</td>
		</tr>
		<tr width="80%">
			<td align="left" width="70%" style="padding-left:30px;border:0px;">
				<b> This Provider : </b>
			</td>
			<c:choose>
			<c:when test="${widgetProResultsDTOObj.providerCount == 'N/A'}" >
				<td align="left" width="30%" style="padding-left:10px;border:0px;" >
					${widgetProResultsDTOObj.providerCount}
				</td>
			</c:when>
			<c:otherwise>
				<td style="border:0px;">
					<a href="/MarketFrontend/pbController_execute.action?wfmSodFlag=Provider&pbFilterName=Provider [${widgetProResultsDTOObj.providerName}|${widgetProResultsDTOObj.providerID}] service orders for today&soId=${widgetProResultsDTOObj.soId}">${widgetProResultsDTOObj.providerCount}</a>
				</td>
			</c:otherwise>
			</c:choose>
		</tr>
		<tr width="80%">
			<td align="left" width="70%" style="padding-left:30px;border:0px;">
				<b> This Provider Firm : </b>
			</td>
			<c:choose>
			<c:when test="${widgetProResultsDTOObj.firmProviderCount == 'N/A'}" >
				<td align="left" width="30%" style="padding-left:10px;border:0px;">
					${widgetProResultsDTOObj.firmProviderCount}
				</td>
			</c:when>
			<c:otherwise>
				<td style="border:0px;">
					<a href="/MarketFrontend/pbController_execute.action?wfmSodFlag=Firm&pbFilterName=Provider Firm [${widgetProResultsDTOObj.providerFirmName}|${widgetProResultsDTOObj.providerFirmId}] service orders for today&soId=${widgetProResultsDTOObj.soId}">${widgetProResultsDTOObj.firmProviderCount}</a>
				</td>
			</c:otherwise>
			</c:choose>
		</tr>
		
	</table>	
	<table height=10px>
	</table>
</div>
</div>


	


