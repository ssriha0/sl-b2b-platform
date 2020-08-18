<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
 <script type="text/javascript">
function expandTerms(id){

var divId="terms"+id;
var bodyId="terms_menu_body"+id;
$("#"+divId+" p.menu_head").css({backgroundImage:"url(titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
}


</script>
<c:set var="divName" value="terms"/>
 <c:set var="divName" value="${divName}${summaryDTO.id}"/>
<div id="${divName}" class="menu_list">
<c:set var="bodyName" value="terms_menu_body"/>
 <c:set var="bodyName" value="${bodyName}${summaryDTO.id}"/>
  <p class="menu_head" onclick="expandTerms('${summaryDTO.id}')">Terms and Conditions</p>

    <div id="${bodyName}" class="menu_body">
	<div class="grayModuleHdr">
		<fmt:message bundle="${serviceliveCopyBundle}" key="label.buyer.TandC"/>
	</div>
	<div class="grayModuleContent mainWellContent">
		<p class="paddingBtm">
			<%-- <fmt:message bundle="${serviceliveCopyBundle}" key="buyer.manage_users.TandC.instr"/> --%>
			<a href="/MarketFrontend/termsAndConditions_displayBuyerAgreement.action" onclick="window.open(this.href,'terms','width=1040,height=640,scrollbars,resizable'); return false;">Terms & Conditions</a><br/>
			You must agree to the following <a href="/MarketFrontend/termsAndConditions_displayBuyerAgreement.action" onclick="window.open(this.href,'terms','width=1040,height=640,scrollbars,resizable'); return false;">terms and conditions</a> in order to complete your registration. Service buyers who fail to abide by these terms and conditions will be removed from the ServiceLive network.
			
		</p>
		<%-- <div class="inputArea" style="height: 200px;">
			<h3 align="center">
				<fmt:message bundle="${serviceliveCopyBundle}" key="label.buyer.TandC"/>
			</h3>
				${termsContent}	
		</div>
		--%>
		<c:choose>
			<c:when test="${readOnly}">
				<p>
					<input type="radio" class="antiRadioOffsets" name="acceptTermsAndConditions" checked="checked" 
					readonly="readonly" value="1">
					<fmt:message bundle="${serviceliveCopyBundle}" key="label.TandC.accept"/>
				</p>
			</c:when>
			<c:otherwise>
				<c:choose>
				<c:when test="${fieldErrors['buyerAdminManageUsersAddEditDTO.termsAndConditions'] != null}">
					<p class="req">
				</c:when>
				<c:otherwise><p></c:otherwise>
				</c:choose>
					<input type="radio" class="antiRadioOffsets" name="acceptTermsAndConditions" value="1">
					<fmt:message bundle="${serviceliveCopyBundle}" key="label.TandC.accept"/>
				</p>
				<p>
					<input type="radio" class="antiRadioOffsets" name="acceptTermsAndConditions"
						checked="checked" value="0">
					<fmt:message bundle="${serviceliveCopyBundle}" key="label.TandC.donotaccept"/>
				</p>
			</c:otherwise>
		</c:choose>
		
	</div>
	


</div>
</div>
