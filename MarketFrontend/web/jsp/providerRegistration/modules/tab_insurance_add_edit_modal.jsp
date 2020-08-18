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
	
<script type="text/javascript" src="${staticContextPath}/scripts/plugins/ui.datepicker.js"></script>

<script type="text/javascript">
	
	jQuery(document).ready(function($) {
		$("input.pidate").datepicker();
		$("input.pedate").datepicker();
	
	});
	
</script>



<!-- SL-21233: Document Retrieval Code Starts -->

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

.ui-widget-content {
	border: 1px solid #222222;
	background: #fffafa;
	color: #222222;
}
.ui-widget-content a {
	color: #222222;
}

li{list-style-type: none;}
</style>
<!-- SL-21233: Document Retrieval Code Ends -->
	
<s:form name="policyInformationForm"  id="policyInformationForm" theme="simple" method="post" action="insurancePolicyDetailsAction_" enctype="multipart/form-data">
	<s:hidden name="category" id="category" value=""></s:hidden>
	<s:hidden name="credId" id="credId" value=""></s:hidden>
	<s:hidden name="sameDocId" id="sameDocId"></s:hidden>
	<s:hidden name="buttonType" id="buttonType" value=""></s:hidden>
	<s:hidden name="docId" id="docId" value=""></s:hidden> 
	<div class="modalContent"> 

		<div style="margin: 10px 0pt;display:none;" class="errorBox" id="errorMessages"  >				
        </div>
        
		<!--<div class="grayModuleContent mainContentWell">	-->		
<p><b>An asterix (<span class="req">*</span>) indicates a required field</b></p> <br/>
			<c:choose>
			<c:when  test="${not empty LAST_DOC_ID}" >
				<c:set var="DOC_ID" value="1"></c:set>	
			</c:when>
			<c:otherwise>
				<c:set var="DOC_ID" value="0"></c:set>	
			</c:otherwise>
			</c:choose>
			
				<div id="documentUpload">			
				</div>
				<br/>
				<div id="uploadFile" >
					<p class="paddingBtm">
						<label>
						Select new certificate to upload
						</label>
						<br />			
						<table style="width: 600px;">
						<tr><td>
						<input type="file" class="insuranceshadowBox grayText" 
						value="[Select file]" name="insurancePolicyDto.file" 
						class="btnBevel uploadBtn" size="40" onchange="checkExtension(this.value,'upload')" />
						</td>
						<td style="align:left;"><b>Accepted File Types :</b> .jpg|.pdf|.doc|.gif<br/>
						<b>Max. file size:</b> 2MB	
						</td>
						</tr>
						</table>			
					</p>
				</div>
				<br/><br/>
				<hr>
				<br/><br/>	
				
				<table id="insTbl" cellpadding="0" cellspacing="0">	
		<tr><td style="border: 0px;"></td></tr>
				<tr>
					<td>
						<c:choose>
						<c:when
							test="${fieldErrors['insurancePolicyDto.policyNumber'] == null}">
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
						<s:textfield name="insurancePolicyDto.policyNumber" id="insurancePolicyDto.policyNumber"
							value="%{insurancePolicyDto.policyNumber}" maxlength="20"
							size="15" cssStyle="width: 100px;" cssClass="shadowBox grayText"></s:textfield>
							</p>
						</td>

					<td>
							<p>
							<c:choose>
							<c:when test="${fieldErrors['insurancePolicyDto.amount'] == null}">
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
							<s:textfield name="insurancePolicyDto.amount" id="insurancePolicyDto.amount"
								value="%{insurancePolicyDto.amount}" maxlength="15" size="15"
								cssStyle="width: 100px;" cssClass="shadowBox grayText"></s:textfield>
						</p>
					</td>
						
					</tr>
					<tr>	
						<td>
						<c:choose>
						<c:when
							test="${fieldErrors['insurancePolicyDto.carrierName'] == null}">
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
							value="%{insurancePolicyDto.carrierName}" id="insurancePolicyDto.carrierName" maxlength="99"
							size="45" cssStyle="width: 230px;" cssClass="shadowBox grayText" ></s:textfield>
						</p>
					</td>
					<td>
					
						<c:choose>
						<c:when
							test="${fieldErrors['insurancePolicyDto.agencyName'] == null}">
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
						<s:textfield name="insurancePolicyDto.agencyName" id="insurancePolicyDto.agencyName"
							value="%{insurancePolicyDto.agencyName}" maxlength="99" size="45"
							cssStyle="width: 230px;" cssClass="shadowBox grayText"></s:textfield>
						</p>
					</td>
				</tr>
				<tr>
					<td>
							<c:choose>
							<c:when
								test="${fieldErrors['insurancePolicyDto.agencyState'] == null}">
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

							<s:select name="insurancePolicyDto.agencyState" id="insurancePolicyDto.agencyState"
								value="%{insurancePolicyDto.agencyState}"
								list="#application['stateCodes']" listKey="type"
								listValue="descr"></s:select>
						</p>
					</td>
				
					<td>
							<c:choose>
							<c:when
								test="${fieldErrors['insurancePolicyDto.agencyCountry'] == null}">
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
							<s:textfield name="insurancePolicyDto.agencyCountry" id="insurancePolicyDto.agencyCountry"
								value="%{insurancePolicyDto.agencyCountry}" maxlength="15"
								size="15" cssStyle="width: 75px;" cssClass="shadowBox grayText"></s:textfield>
						</p>
					</td>
				</tr>
				
				<tr>
					<td>
							<c:choose> 
							<c:when
								test="${fieldErrors['insurancePolicyDto.policyIssueDate'] == null}">
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
								<c:when test="%{not empty insurancePolicyDto.policyIssueDate}">
									<input name="insurancePolicyDto.policyIssueDate" id="modal2ConditionalChangeDate2" value="<fmt:formatDate value="${insurancePolicyDto.policyIssueDate}" pattern="MM/dd/yyyy" />" type="text" class="short text date pidate"> 
								</c:when>
								<c:otherwise>
									<input name="insurancePolicyDto.policyIssueDate" id="modal2ConditionalChangeDate2" value="<fmt:formatDate value="${insurancePolicyDto.policyIssueDate}" pattern="MM/dd/yyyy" />" type="text" class="short text date pidate"> 
								</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>				
								<input name="insurancePolicyDto.policyIssueDate" id="modal2ConditionalChangeDate2" value="<fmt:formatDate value="${insurancePolicyDto.policyIssueDate}" pattern="MM/dd/yyyy" />" type="text" class="short text date pidate"> 
							</c:otherwise>
							</c:choose>
						</p>
					</td>
					<td>
						<c:choose>
						<c:when
							test="${fieldErrors['insurancePolicyDto.policyExpirationDate'] == null}">
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
							<c:when test="%{not empty insurancePolicyDto.policyExpirationDate}">
								<input name="insurancePolicyDto.policyExpirationDate" id="modal2ConditionalChangeDate1" value="<fmt:formatDate value="${insurancePolicyDto.policyExpirationDate}" pattern="MM/dd/yyyy" />" type="text" class="short text date pedate"> 
							</c:when>
							<c:otherwise>
								<input name="insurancePolicyDto.policyExpirationDate" id="modal2ConditionalChangeDate1" value="<fmt:formatDate value="${insurancePolicyDto.policyExpirationDate}" pattern="MM/dd/yyyy" />" type="text" class="short text date pedate"> 
							</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
								<input name="insurancePolicyDto.policyExpirationDate" id="modal2ConditionalChangeDate1" value="<fmt:formatDate value="${insurancePolicyDto.policyExpirationDate}" pattern="MM/dd/yyyy" />" type="text" class="short text date pedate"> 
						</c:otherwise>
						</c:choose>
						</p>
					</td>
				</tr>
				<tr>
				<td><a href="#" class="cancel jqmCancel" >Cancel</a> </td>  
				<td>
					<!--Code added as part of Jira SL-20645 -To capture time while auditing insurance -->
					<input type="hidden" id="insurancePolicyDto.auditTimeLoggingIdNew" name="insurancePolicyDto.auditTimeLoggingIdNew" value="">
					
					
					
					<input type="button" name="save" value="Save" id="save" onclick="saveModal(this);return false;">			
				</td>
				</tr>
			</table>
			
			<br/><br/>
				<hr>
				<br/><br/>
	<!-- SL-21233: Document Retrieval Code Starts -->
	<input type="button" name="VDH" value="View Past Documents" id="VDH"
		onclick="jQuery('#Doc_Hist').toggle('fast');">
	<br/>
	<div class="ui-widget modalContent">
		<div class="details_container" id="Doc_Hist" style="display: none">
			<div id="expander_Details" class="ui-widget-header">
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
