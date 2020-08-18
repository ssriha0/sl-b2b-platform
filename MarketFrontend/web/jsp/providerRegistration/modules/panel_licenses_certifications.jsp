<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="darkGrayModuleHdr">
	Licenses &amp; Certifications on File
</div>
	
<div class="grayModuleContent mainWellContent clearfix">	
	<c:choose>
	<c:when test="${not empty companyProfileDto.licensesList}">
		<table class="scrollerTableHdr licensesTableHdr" cellpadding="0"
			cellspacing="0" style="width:627px;">
			<tr>
				<td class="column2">
					Credential Type
				</td>
				<td class="column3">
					Name
				</td>
				<td class="column4">
					Expiration
				</td>
				<td class="column5">
					Verified by ServiceLive
				</td>
			</tr>
		</table>
		<table class="licensesTable" cellpadding="0" cellspacing="0" style="table-layout:fixed; width:625px;">
			<s:iterator value="companyProfileDto.licensesList" status="status">

				<tr>
					<td class="column2" style="word-wrap:break-word">
						<s:property value="credTypeDesc" />
					</td>
					<td class="column3" style="word-wrap:break-word">
						<s:property value="licenseName" />
					</td>
					<td class="column4" style="word-wrap:break-word">
						<s:property value="expirationDate" />
					</td>
					<td class="column5" style="word-wrap:break-word">
						<c:choose>
						<c:when test="${wfStateId =='13'}">
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
			Currently, there are no licenses or certificates on file for this
			provider.
		</p>
	</c:otherwise>
	</c:choose>
</div>

