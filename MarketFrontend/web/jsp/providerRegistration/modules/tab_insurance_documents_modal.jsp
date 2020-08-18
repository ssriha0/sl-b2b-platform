<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="BUTTON_TYPE_ADD" value="<%=OrderConstants.BUTTON_TYPE_ADD%>"></c:set>
<c:set var="BUTTON_TYPE" value="<%=(String)session.getAttribute(OrderConstants.BUTTON_TYPE)%>"></c:set>
<c:set var="DOC_NAME" value="<%=(String)session.getAttribute(OrderConstants.CREDENTIAL_FILE_NAME)%>"></c:set>	
<c:set var="LAST_DOC_ID" value="<%=(Integer)session.getAttribute(OrderConstants.CREDENTIAL_DOC_ID)%>"></c:set>	

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", -1);
%>

<!-- SL-21233: Document Retrieval Code Starts -->
<script type="text/javascript">
	
	function changeValue(obj,listId){
	
			if (jQuery(listId).css('display') == 'block'){
				jQuery(listId).hide();
				jQuery(obj).find('img').attr('src','/ServiceLiveWebUtil/images/icons/plusIcon.gif');
			}else{
				jQuery(listId).show();
				jQuery(obj).find('img').attr('src','/ServiceLiveWebUtil/images/icons/minusIcon.gif');
			}
	}



jQuery(document).click(function (e) {

var sp="sp";
var details="Details";
var plus="plus";
var expander_Demo="expander_Demo";
var insTbl="insTbl";
var PN="PN";
var Amt="Amt";
var CN="CN";
var AN="AN";
var AS="AS";
var AC="AC";
var IssueDate="IssueDate";
var ExpDate="ExpDate";
var row="row";
var VDH="VDH";
var scrollableDiv="scrollableDiv";

    if (e.target.id.indexOf(insTbl) > -1 || e.target.id.indexOf(expander_Demo) > -1 || e.target.id.indexOf(sp) > -1 || e.target.id.indexOf(details) > -1 || e.target.id.indexOf(plus) > -1 || e.target.id.indexOf(PN) > -1 || e.target.id.indexOf(Amt) > -1 || e.target.id.indexOf(CN) > -1 || e.target.id.indexOf(AN) > -1 || e.target.id.indexOf(AS) > -1 || e.target.id.indexOf(AC) > -1 || e.target.id.indexOf(IssueDate) > -1 || e.target.id.indexOf(ExpDate) > -1 || e.target.id.indexOf(row) > -1 || e.target.id.indexOf(VDH) > -1 || e.target.id.indexOf(scrollableDiv) > -1) {
        //do nothing
    } else if (e.target === $('.details_container1')){
		//do nothing
    }else {
		jQuery("#Doc_Hist1").hide();
		jQuery("div.ui-widget-content1").hide();
		jQuery("div.Docs").find('img').attr('src','/ServiceLiveWebUtil/images/icons/plusIcon.gif');
    }
});
	
</script>

<style type="text/css">
.innerScrollable
{
	width:570px; 
    overflow-y:scroll; 
    position:relative;
    height: 250px;
}

.ui-widget-header {
	background: #fffafa;
	color: #222222;
	font-weight: bold;
}

.ui-widget {
	font-family: Verdana,Arial,sans-serif;
	font-size: 1.1em;
}

.ui-widget input,
.ui-widget select,
.ui-widget textarea,
.ui-widget button {
	font-family: Verdana,Arial,sans-serif;
	font-size: 1em;
}

.ui-widget-content1 {
	border: 1px solid #222222;
	background: #fffafa;
	color: #222222;
}
.ui-widget-content1 a {
	color: #222222;
}

li{list-style-type: none;}
</style>

	
<s:form name="policyInformationForm"  id="policyInformationForm" theme="simple" method="post" action="insurancePolicyDetailsAction_" enctype="multipart/form-data">
	<s:hidden name="category" id="category" value=""></s:hidden>
	<s:hidden name="credId" id="credId" value=""></s:hidden>
	<s:hidden name="sameDocId" id="sameDocId"></s:hidden>
	<s:hidden name="buttonType" id="buttonType" value=""></s:hidden>
	<s:hidden name="docId1" id="docId1" value=""></s:hidden> 
	<div class="modalContent"> 

		<div style="margin: 10px 0pt;display:none;" class="errorBox" id="errorMessages"  >				
        </div>
        
		<!--<div class="grayModuleContent mainContentWell">	-->		
			<c:choose>
			<c:when  test="${not empty LAST_DOC_ID}" >
				<c:set var="DOC_ID" value="1"></c:set>	
			</c:when>
			<c:otherwise>
				<c:set var="DOC_ID" value="0"></c:set>	
			</c:otherwise>
			</c:choose>
			
				<div id="documentUpload1">			
				</div>
				<br/><br/>	
				
				<table id="insTbl" cellpadding="0" cellspacing="0">	
		<tr><td style="border: 0px;"></td></tr>
				<tr>
					<td>
						<c:choose>
						<c:when
							test="${fieldErrors['insurancePolicyDto.policyNumber1'] == null}">
							<p>
						</c:when>
						<c:otherwise>
							<p class="errorBox">
						</c:otherwise>
						</c:choose>
						<label>
							Policy Number  <span class="insPopUpMnd">*</span>
						</label>
						<br />
						<s:hidden name="insurancePolicyDto.combinedKey" />
						<s:textfield name="insurancePolicyDto.policyNumber" id="insurancePolicyDto.policyNumber1"
							value="%{insurancePolicyDto.policyNumber1}" maxlength="20"
							size="15" cssStyle="width: 100px;" cssClass="shadowBox grayText" readonly="true"></s:textfield>
							</p>
						</td>

					<td>
							<p>
							<c:choose>
							<c:when test="${fieldErrors['insurancePolicyDto.amount1'] == null}">
								<p>
							</c:when>
							<c:otherwise>
								<p class="errorBox">
							</c:otherwise>
							</c:choose>
							<label>
								Amount  <span class="insPopUpMnd">*</span>
							</label>
							<br />
							<s:textfield name="insurancePolicyDto.amount" id="insurancePolicyDto.amount1"
								value="%{insurancePolicyDto.amount1}" maxlength="15" size="15"
								cssStyle="width: 100px;" cssClass="shadowBox grayText" readonly="true"></s:textfield>
						</p>
					</td>
						
					</tr>
					<tr>	
						<td>
						<c:choose>
						<c:when
							test="${fieldErrors['insurancePolicyDto.carrierName1'] == null}">
							<p>
						</c:when>
						<c:otherwise>
							<p class="errorBox">
						</c:otherwise>
						</c:choose>
						<label>
							Carrier Name  <span class="insPopUpMnd">*</span>
						</label>
						<br />
						<s:textfield name="insurancePolicyDto.carrierName"
							value="%{insurancePolicyDto.carrierName1}" id="insurancePolicyDto.carrierName1" maxlength="99"
							size="45" cssStyle="width: 230px;" cssClass="shadowBox grayText" readonly="true"></s:textfield>
						</p>
					</td>
					<td>
					
						<c:choose>
						<c:when
							test="${fieldErrors['insurancePolicyDto.agencyName1'] == null}">
							<p>
						</c:when>
						<c:otherwise>
							<p class="errorBox">
						</c:otherwise>
						</c:choose>
						<label>
							Agency Name  <span class="insPopUpMnd">*</span>
						</label>
						<br />
						<s:textfield name="insurancePolicyDto.agencyName" id="insurancePolicyDto.agencyName1"
							value="%{insurancePolicyDto.agencyName1}" maxlength="99" size="45"
							cssStyle="width: 230px;" cssClass="shadowBox grayText" readonly="true"></s:textfield>
						</p>
					</td>
				</tr>
				<tr>
					<td>
							<c:choose>
							<c:when
								test="${fieldErrors['insurancePolicyDto.agencyState1'] == null}">
								<p>
							</c:when>
							<c:otherwise>
								<p class="errorBox">
							</c:otherwise>
							</c:choose>
							<label>
								Agency State  <span class="insPopUpMnd">*</span>
							</label>
							<br />

							<s:select name="insurancePolicyDto.agencyState" id="insurancePolicyDto.agencyState1"
								value="%{insurancePolicyDto.agencyState1}"
								list="#application['stateCodes']" listKey="type"
								listValue="descr" disabled="true"></s:select>
						</p>
					</td>
				
					<td>
							<c:choose>
							<c:when
								test="${fieldErrors['insurancePolicyDto.agencyCountry1'] == null}">
								<p>
							</c:when>
							<c:otherwise>
								<p class="errorBox">
							</c:otherwise>
							</c:choose>

							<label>
								Agency County
							</label>
							<br />
							<s:textfield name="insurancePolicyDto.agencyCountry" id="insurancePolicyDto.agencyCountry1"
								value="%{insurancePolicyDto.agencyCountry1}" maxlength="15"
								size="15" cssStyle="width: 75px;" cssClass="shadowBox grayText" readonly="true"></s:textfield>
						</p>
					</td>
				</tr>
				
				<tr>
					<td>
							<c:choose> 
							<c:when
								test="${fieldErrors['insurancePolicyDto.policyIssueDate1'] == null}">
								<p class="paddingBtm">
							</c:when>
							<c:otherwise>
								<p class="errorBox">
							</c:otherwise>
							</c:choose>
							<label>
								Issue Date  <span class="insPopUpMnd">*</span>
							</label>
							<br />
							<c:choose>
							<c:when test="%{insurancePolicyDto.vendorCredentialId > -1}">
								<c:choose>
								<c:when test="%{not empty insurancePolicyDto.policyIssueDate1}">
									<input name="insurancePolicyDto.policyIssueDate1" id="modal2ConditionalChangeDate21" value="<fmt:formatDate value="${insurancePolicyDto.policyIssueDate1}" pattern="MM/dd/yyyy" />" type="text" class="short text date pidate1" disabled> 
								</c:when>
								<c:otherwise>
									<input name="insurancePolicyDto.policyIssueDate1" id="modal2ConditionalChangeDate21" value="<fmt:formatDate value="${insurancePolicyDto.policyIssueDate1}" pattern="MM/dd/yyyy" />" type="text" class="short text date pidate1" disabled> 
								</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>				
								<input name="insurancePolicyDto.policyIssueDate1" id="modal2ConditionalChangeDate21" value="<fmt:formatDate value="${insurancePolicyDto.policyIssueDate1}" pattern="MM/dd/yyyy" />" type="text" class="short text date pidate1" disabled> 
							</c:otherwise>
							</c:choose>
						</p>
					</td>
					<td>
						<c:choose>
						<c:when
							test="${fieldErrors['insurancePolicyDto.policyExpirationDate1'] == null}">
							<p>
						</c:when>
						<c:otherwise>
							<p class="errorBox">
						</c:otherwise>
						</c:choose>
						<label>
							Expiration Date  <span class="insPopUpMnd">*</span>
						</label>
						<br />
						<c:choose>
						<c:when test="%{insurancePolicyDto.vendorCredentialId > -1}">
							<c:choose>
							<c:when test="%{not empty insurancePolicyDto.policyExpirationDate1}">
								<input name="insurancePolicyDto.policyExpirationDate1" id="modal2ConditionalChangeDate11" value="<fmt:formatDate value="${insurancePolicyDto.policyExpirationDate1}" pattern="MM/dd/yyyy" />" type="text" class="short text date pedate1" disabled> 
							</c:when>
							<c:otherwise>
								<input name="insurancePolicyDto.policyExpirationDate1" id="modal2ConditionalChangeDate11" value="<fmt:formatDate value="${insurancePolicyDto.policyExpirationDate1}" pattern="MM/dd/yyyy" />" type="text" class="short text date pedate1" disabled> 
							</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
								<input name="insurancePolicyDto.policyExpirationDate1" id="modal2ConditionalChangeDate11" value="<fmt:formatDate value="${insurancePolicyDto.policyExpirationDate1}" pattern="MM/dd/yyyy" />" type="text" class="short text date pedate1" disabled> 
						</c:otherwise>
						</c:choose>
						</p>
					</td>
				</tr>
				<tr>
				<td>
					<!--Code added as part of Jira SL-20645 -To capture time while auditing insurance -->
					<input type="hidden" id="insurancePolicyDto.auditTimeLoggingIdNew1" name="insurancePolicyDto.auditTimeLoggingIdNew1" value="">		
				</td>
				</tr>
			</table>
			
			<br/><br/>
				<hr>
				<br/><br/>
	<input type="button" name="VDH1" value="View Document History" id="VDH1"
		onclick="jQuery('#Doc_Hist1').toggle('fast');">
	<br/>
	<div class="ui-widget modalContent">
		<div class="details_container1" id="Doc_Hist1" style="display: none">
			<div id="expander_Details1" class="ui-widget-header">
			</div>
			</div>
			</div>
		</div>
	<!-- SL-21233: Document Retrieval Code Ends -->
		<s:hidden name="insurancePolicyDto.vendorCredentialId"
			value="%{insurancePolicyDto.vendorCredentialId}"></s:hidden>
				<input type="hidden" name="method">
	</s:form>
<!-- END MODAL -->
