<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 <c:set var="termImage" value="termImage"/>

<script type="text/javascript">

function expandTerms(id,path){
	alert(id);
	alert(path);
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
</script>

<br />
<br />
<hr />
<br />
<div style="padding-left:5px;font-family: Verdana,Arial,Helvetica,sans-serif;font-size: 10px;">		
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
  <p class="${headClass}" onclick="expandTerm('${summaryDTO.id}','${staticContextPath}')">&nbsp;<img id="${termImage}" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;ServiceLive Terms & Conditions</p>
			
	<div id="${bodyName}" class="${bodyClass}">
	<p>
		<strong>Terms &amp; Conditions</strong>
	</p>
	<div class="hdrTable" style="margin-top: 0;"></div>
	<div id="termsAndConditionsContainer" class="grayTableContainer" style="height: 250px; padding: 10px; width:643px; _width:663px;">
		<p>
			${acceptSOTermsAndCond.termsCondContent}
		</p>
	</div>
	</div>
	</div>
	<div id="hpTip" class="tips i50">
		<h2>ACCEPT THIS SERVICE ORDER</h2>
	</div>
	Please confirm your acceptance of the terms and conditions above:
	<p>
		<c:if test="${captchaError ne null}">
			<input style="vertical-align: middle;" type="checkbox"
					class="shadowbox" id="acceptCheck"
					onclick="toggleCaptcha('${vendorBuckInd}', '${primaryVendorInd}', '${summaryDTO.carSO}')"
					checked="checked" />
		</c:if>
		<c:if test="${captchaError eq null}">
			<input style="vertical-align: middle;" type="checkbox"
					class="shadowbox" id="acceptCheck"
					onclick="toggleCaptcha('${vendorBuckInd}', '${primaryVendorInd}', '${summaryDTO.carSO}')" />
		</c:if>
		<b>I accept the Terms &amp; Conditions above.</b> <br>
	</p>
					
	<div id="captcha" style="display: <c:if test="${captchaError ne null}">block</c:if><c:if test="${captchaError eq null}">none</c:if>; height:75px;">
		<c:if test="${captchaError ne null}">
			<div class="errorBox clearfix">
				<p class="errorMsg">&nbsp;&nbsp;&nbsp;&nbsp;${captchaError}</p>
			</div>
			<%
				session.removeAttribute("captchaError");
			%>
			<%
				session.removeAttribute("captchaErrorPosition");
			%>
			<%
				session.removeAttribute("numberOfOrders");
			%>
			<br />
		</c:if>
		<p class="left" style="display: block;">
			<c:if test="${captchaEnable == 'true'}">
				<strong>Enter the characters you see (with no spaces):</strong>
				<br />
				<br />

				<img style="vertical-align: top;"
						src="/MarketFrontend/jsp/public/common/captcha.jsp?soId=${SERVICE_ORDER_ID}&groupId=${groupOrderId}" /> &nbsp;&gt;&nbsp;&gt;&nbsp;&gt;&nbsp;
				<input type="text" name="securityCode" id="securityCode"
						maxlength="5" style="width: 50px; height: 20px"> &nbsp;&nbsp;
			</c:if>
			<img id="acceptButton"
					src="${staticContextPath}/images/common/spacer.gif" width="72"
					height="22"
					style="display:none; cursor:pointer; background-image: url(${staticContextPath}/images/btn/accept.gif);"
					class="btnBevel" align="middle"
					onclick="SubmitAcceptServiceOrder()" />
		</p>

		<div id="loadTimerDiv">
				<jsp:include page="panel_accept_order.jsp" />
		</div>

		<input type="hidden" id="isCar" value="${summaryDTO.carSO}">
		<input type="hidden" id="routedResourceId" value="${summaryDTO.loggedInResourceId}">
		<input type="hidden" id="notDispathcerInd" value="">
	</div>
</div>


