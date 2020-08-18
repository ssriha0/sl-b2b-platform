<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 <script type="text/javascript">

function expandTerm(id,path){
var hidId="group"+id;
var testGroup=document.getElementById(hidId).value;
var divId="terms_cond"+id;
var bodyId="terms_cond_menu_body"+id;
if(testGroup=="menu_list"){
jQuery("#"+divId+" p.menu_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
}else{
jQuery("#"+divId+" p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
}
var ob=document.getElementById('termImage'+id).src;
if(ob.indexOf('arrowRight')!=-1){
document.getElementById('termImage'+id).src=path+"/images/widgets/arrowDown.gif";
}
if(ob.indexOf('arrowDown')!=-1){
document.getElementById('termImage'+id).src=path+"/images/widgets/arrowRight.gif";
}
}

function timerExpired(){
	jQuery('#acceptButtonForCarOrders').hide();
	jQuery('#acceptButton').show();
	jQuery('#securityCode').keypress(function(event){processEnter(event);});
	jQuery('#acceptCountdownLabel').hide();
	jQuery('#acceptCountdown').hide();
}

jQuery(document).ready(
		function() {
			var isEstimateSO = document.getElementById('isEstimationRequest').value;  
			if(isEstimateSO){
				jQuery('#isEstimate').hide();
			}else{
				jQuery('#isEstimate').show();
			}
});

</script>
<c:set var="divName" value="terms_cond"/>
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
<c:set var="group" value="${group}${summaryDTO.id}"/>
<input type="hidden" id="${group}" value="${groupVal}"/>
<div id="${divName}" class="${groupVal}">
<c:set var="bodyName" value="terms_cond_menu_body"/>
 <c:set var="bodyName" value="${bodyName}${summaryDTO.id}"/>
 <c:set var="termImage" value="termImage"/>
    <c:set var="termImage" value="${termImage}${summaryDTO.id}"/>
 


	<c:if test="${showAcceptBlock}">
		<br />
	
	    <div id="captcha" style="display: <c:if test="${captchaError ne null}">block</c:if><c:if test="${captchaError eq null}">none</c:if>; height:75px;" >
	   		<c:if test="${captchaError ne null}">
				<div class="errorBox clearfix">
				<p class="errorMsg">&nbsp;&nbsp;&nbsp;&nbsp;${captchaError}</p>
				</div>
				<%session.removeAttribute("captchaError");%>
				<%session.removeAttribute("captchaErrorPosition");%>
				<%session.removeAttribute("numberOfOrders");%>
				<br />
			</c:if>
				<p  class="left">
					<c:if test="${captchaEnable == 'true'}">
			    		<strong>Enter the characters you see (with no spaces):</strong>
					    <br/>
					    <br/>
			    		<img style="vertical-align: top;" src="/MarketFrontend/jsp/public/common/captcha.jsp?soId=${SERVICE_ORDER_ID}&groupId=${groupOrderId}" /> &nbsp;&gt;&nbsp;&gt;&nbsp;&gt;&nbsp;
					    <input type="text" name="securityCode"
				    		id="securityCode" maxlength="5" style="width: 50px; height: 20px"> &nbsp;&nbsp;
					</c:if>
	    			<div id="isEstimate">
	    			<img id="acceptButton" src="${staticContextPath}/images/common/spacer.gif" width="72"
			         height="22" style="display:none; cursor:pointer; background-image: url(${staticContextPath}/images/btn/accept.gif);"
			         class="btnBevel" align="middle" onclick="SubmitAcceptServiceOrder()"/>
			        </div> 
				</p>			
			  <div id="loadTimerDiv">
			   <jsp:include page="panel_accept_order.jsp"/>
			   </div>

		
		</div>
		<img id="acceptButtonForCarOrders" src="${staticContextPath}/images/common/spacer.gif" width="72"
			         height="22" style="display:none; cursor:pointer; background-image: url(${staticContextPath}/images/btn/accept.gif);"
			         class="btnBevel" align="middle" onclick="SubmitAcceptServiceOrder()"/> 
	</c:if>
<br style="clear:both;"/>
</div>

