<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<div style="border-top: 1px solid #dddddd; padding-top: 5px;"><strong>Document Requirements</strong></div>
<div style="margin-bottom: 5px;">
	Select a document to upload or update a file that has not yet been approved. If a document you have already provided for
	another SPN is required, it will appear as "On File".
</div>
<div class="error" id="errPlaceHolder_${spnMonitorVO.spnId}" style="display:none">Please upload files for incomplete documents to continue.</div>
<c:if test="${not empty spnMonitorVO.spnSignAndReturnDocuments}">
	<div class="clearfix tableWrap" style="border-left: 0px; border-right: 0px;">
		<table border="0" cellpadding="6" cellspacing="0" class="doctable">
		<thead>
		<tr>
			<th class="tl bl" style="width: 120px">Status</th>
			<th>Required Documents</th> 
			<th class="tl br" style="width: 180px;">&nbsp;Select</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${spnMonitorVO.spnSignAndReturnDocuments}" var="buyerDocRow">
		<tr>
			<td class="tc bl" id="DocStatus_${buyerDocRow.docId}_${spnMonitorVO.spnId}">
			<c:choose>
				<c:when test="${buyerDocRow.docStateId =='DOC INCOMPLETE' || buyerDocRow.docStateId =='DOC NEED MORE INFO'}">
					<img src="${staticContextPath}/images/common/status-yellow.png" alt="Incomplete" /><br />
				</c:when>
				<c:when test="${buyerDocRow.docStateId == 'DOC PENDING APPROVAL'}">
					<img src="${staticContextPath}/images/common/status-white.png" alt="Pending Approval" /><br />
				</c:when>
				<c:when test="${buyerDocRow.docStateId =='DOC APPROVED'}">
					<img src="${staticContextPath}/images/common/status-green.png" alt="Approved" /><br />																		
				</c:when>
			</c:choose>
			<p class="sm">${buyerDocRow.docStateDesc}</p>
			</td>
			<td>
				<strong><a href="${contextPath}/spnMonitorAction_loadDocument.action?&docID=${buyerDocRow.docId}" target="_docWindow">${buyerDocRow.documentTitle}</a></strong><br/>
				<c:if test="${buyerDocRow.buyerDocFormatDescription != null}">
					<small>(${buyerDocRow.buyerDocFormatDescription})</small>
				</c:if>
				<c:if test="${not empty buyerDocRow.auditorComments && buyerDocRow.docStateId == 'DOC NEED MORE INFO'}">
					<div class="comment"><strong class="message">Auditor comments: </strong>${buyerDocRow.auditorComments} </div>
				</c:if>
				<c:choose>
				<c:when test="${buyerDocRow.docStateId =='DOC INCOMPLETE'}">
					<div id="DocMessage_${buyerDocRow.docId}_${spnMonitorVO.spnId}"></div>
				</c:when>
				<c:when test="${buyerDocRow.docStateId == 'DOC PENDING APPROVAL'||buyerDocRow.docStateId =='DOC APPROVED' || buyerDocRow.docStateId =='DOC NEED MORE INFO' }">
					<div class="nocomment"><strong>Document On File:</strong>
						<span id="OnFileDoc_${buyerDocRow.docId}_${spnMonitorVO.spnId}">
						<a href="${contextPath}/spnMonitorAction_loadDocument.action?&docID=${buyerDocRow.provFirmUplDocId}" target="_docWindow">${buyerDocRow.docFileName}</a>
						</span>
					</div>
				</c:when>  
				</c:choose>
			</td>
			<td class="tl carr br" id="DocAction_${buyerDocRow.docId}_${spnMonitorVO.spnId}">
			<c:choose>
			<c:when test="${!spnMonitorVO.isAlias && (buyerDocRow.docStateId =='DOC INCOMPLETE')}">
				<div class="DocAction_${buyerDocRow.docId}_${spnMonitorVO.spnId}">
				<c:if test="${spnMonitorVO.providerFirmState=='PF SPN INTERESTED'||spnMonitorVO.providerFirmState=='PF APPLICANT INCOMPLETE' || spnMonitorVO.providerFirmState=='PF SPN APPLICANT' || spnMonitorVO.providerFirmState=='PF INVITED TO SPN' || spnMonitorVO.providerFirmState=='PF SPN MEMBERSHIP UNDER REVIEW'}">
					<input type="hidden" name="${spnMonitorVO.spnId}_buyer_doc" value="Select" />
					<input type="button" name="${spnMonitorVO.spnId}" class="default button" value="Upload"
					onClick="showDocumentUploadModal('${buyerDocRow.documentTitle}','${spnMonitorVO.buyerId}','${buyerDocRow.docId}','Select','${buyerDocRow.provFirmUplDocId}','${buyerDocRow.docFileName}','${spnMonitorVO.spnId}')"/>
				</c:if>																		
				</div>
			</c:when>
			<c:when test="${!spnMonitorVO.isAlias && (buyerDocRow.docStateId == 'DOC PENDING APPROVAL' || buyerDocRow.docStateId =='DOC NEED MORE INFO')}">
				<c:if test="${spnMonitorVO.providerFirmState=='PF SPN INTERESTED'||spnMonitorVO.providerFirmState=='PF APPLICANT INCOMPLETE' || spnMonitorVO.providerFirmState=='PF SPN APPLICANT' || spnMonitorVO.providerFirmState=='PF SPN MEMBERSHIP UNDER REVIEW'}">
					<a class="fileedit" href="#"
					onClick="showDocumentUploadModal('${buyerDocRow.documentTitle}','${spnMonitorVO.buyerId}','${buyerDocRow.docId}','Update','${buyerDocRow.provFirmUplDocId}','${buyerDocRow.docFileName}','${spnMonitorVO.spnId}'); return false;">Update File</a>
				</c:if>																		
			</c:when>
			<c:when test="${buyerDocRow.docStateId =='DOC APPROVED'}">
				<fmt:message bundle="${serviceliveCopyBundle}" key="spn.onfile.msg"/>
			</c:when> 
			</c:choose>
			</td>
		</tr>
		</c:forEach>
		</tbody>
		</table>
	</div>
</c:if>