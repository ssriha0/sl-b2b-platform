<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="darkGrayModuleHdr">
	Insurance Policies on File
</div>

<div class="grayModuleContent mainWellContent clearfix">
	<c:choose>
	<c:when test="${not empty companyProfileDto.insuranceList}">
		<table class="scrollerTableHdr licensesTableHdr" cellpadding="0"
			cellspacing="0" style="table-layout: fixed; width: 100%">
			<tr>

				<td class="column1" style="width: 160px;">
					Policy Type
				</td>
				<td class="column2" style="width: 140px;">
					Carrier
				</td>
				<td class="column3" style="width: 60px;">
					Expiration
				</td>
				<td class="column4" style="width: 130px;">
					Documents
				</td>
				<td class="column5" style="width: 105px;">
					Verified by ServiceLive
				</td>
			</tr>
		</table>
		<table class="licensesTable" cellpadding="0" cellspacing="0"
			style="table-layout: fixed; width: 100%">
			<s:iterator value="companyProfileDto.insuranceList" id="credential"
				status="status">
				<tr>

					<td class="column1" style="word-wrap:break-word;width: 165px;">
						<s:property value="credTypeDesc" />
					</td>
					<td class="column2" style="word-wrap:break-word;width: 140px;">
						<s:property value="source" />
					</td>
					<td class="column3" style="width: 60px;">
						<s:property value="expirationDate" />
					</td>
					<td class="column4" style="width: 130px;">
						<c:choose>						
						<c:when test="${not empty currentDocumentID && currentDocumentID > 0}">
							<s:url
								action="jsp/providerRegistration/saveInsuranceTypeActiondisplayTheDocument.action"
								id="docUrl">
								<s:param name="docId" value="currentDocumentID" />
							</s:url>
							<a
								href="jsp/providerRegistration/saveInsuranceTypeActiondisplayTheDocument.action?docId=<s:property value="currentDocumentID"/>"
								target="blank"> <img
									src="${staticContextPath}/images/images_registration/icons/pdf.gif"
									title="Click to view document" /> </a>
						</c:when>
						<c:otherwise>
								N/A
							</c:otherwise>
						</c:choose>

					</td>
					<td class="column5" style="width: 125px;">
						<c:choose>
						<c:when test="${wfStateId == '13'}">
								Pending Approval
							</c:when>
						<c:when test="${wfStateId =='14'}">
							<img src="${staticContextPath}/images/icons/greenCheck.gif"
								width="10" height="10" alt="">
						</c:when>
						<c:when test="${wfStateId =='25'}"> 
							Out of Compliance
						</c:when>
						<c:when test="${wfStateId =='210'}">
							Reviewed
						</c:when>
						</c:choose>
					</td>

				</tr>
			</s:iterator>
		</table>
	</c:when>
	<c:otherwise>
		<p>
			Currently, there are no insurance policies on file for this provider.
		</p>
	</c:otherwise>
	</c:choose>
</div>
