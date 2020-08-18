<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<script type="text/javascript">
		$(document).ready(function() {
			$('.buyerAgreeButton').unbind('click').click(function() {
		    	var docId = $(this).siblings('[name=docId]').val();
		    	var spnId = $(this).siblings('[name=spnId]').val();
		    	var docTitle = $(this).siblings('[name=docTitle]').val();
		    	var checkViewClassName = "." + docId + "_view_doc";
		    	
		    	if ($(checkViewClassName).val() != "1")
		    	{
		    		var idErrorName = "#" + spnId + "_buyerAgreementError";
		    		$(idErrorName).html('To confirm your agreement with this document, please first click to open the document and review it\'s contents.');
		    		$(idErrorName).show();
		    		return;
		    	}
		    	else
			    {
		    		var idErrorName = "#" + spnId + "_buyerAgreementError";
		    		$(idErrorName).hide();
			    }
		    	
		    	$.post('spnBuyerAgreeModal_acceptBuyerAgreementAjax.action?spnDocId=' + docId, function(data) {
		    		var colId = "." + docId + "_col_image";
		    		$(colId).children('.yellow_img').hide();
		    		$(colId).children('.green_img').show();
		    			
					var agreeDate = data;			
		    		var colId = "." + docId + "_col_select";
		    		$(colId).children('.select_button').hide();
		    		$(colId).children('.time').html('<div style="font-size: 10px;">I have read and agree on behalf of my company to be contractually bound by the ' + docTitle + '.</div><div style="font-size: 10px;">' + agreeDate + '</div>');
		    		$(colId).children('.time').show();
		    			
		    		var hiddenUpdId = "." + docId + "_agree_doc";
		    		$(hiddenUpdId).val('1');
		    	});
		    });
		});
		
		$('.clickViewDoc').unbind('click').click(function() {
		    var docId = $(this).parent().siblings('[name=docId]').val();
		    var className = "." + docId + "_view_doc";
		    $(className).val('1');
		});
</script>

<c:if test="${not empty spnMonitorVO.spnAgreeDocuments}">
<div style="border-top: 1px solid #dddddd; padding-top: 5px; margin-top: 15px;"><strong>Buyer Agreements</strong></div>
<div id="${spnMonitorVO.spnId}_buyerAgreementError" class="error hide" ></div>
<div style="margin-bottom: 5px;">View the document and click "Agree" below to confirm your agreement to this information. The buyer may update agreements at any time. You will be notified of any updates. Once you have agreed to a document, the date you agreed will be displayed below.</div>
													
<div class="clearfix tableWrap" style="border-left: 0px; border-right: 0px;">
	<table border="0" cellpadding="6" cellspacing="0" class="doctable">
	<thead>
	<tr>
		<th class="tl bl" style="width: 120px">Status</th>
		<th>Required Documents</th> 
		<th class="tl br" style="width: 330px;">&nbsp;Agreement Confirmation</th>
	</tr>
	</thead>
	<tbody>
	<c:forEach items="${spnMonitorVO.spnAgreeDocuments}" var="docList">
	<tr>
		<td class="tc bl" >
			<c:if test="${docList.docStateId == null || docList.docStateId == 'DOC INCOMPLETE'}">
				<input type="hidden" class="${docList.docId}_agree_doc ${spnMonitorVO.spnId}_agree_docs" value="0" />
				<div class="${docList.docId}_col_image">
					<div class="yellow_img">
						<img src="${staticContextPath}/images/common/status-yellow.png" alt="Incomplete" />
						<p class="sm">Incomplete</p>
					</div>
					<div class="green_img" style="display: none;">
						<img src="${staticContextPath}/images/common/status-green.png" alt="Confirmed" />
						<p class="sm">Confirmed</p>
					</div>
				</div>
			</c:if>
			<c:if test="${docList.docStateId == 'DOC APPROVED'}">
				<input type="hidden" class="${docList.docId}_agree_doc ${spnMonitorVO.spnId}_agree_docs" value="1" />
				<div class="green_img">
					<img src="${staticContextPath}/images/common/status-green.png" alt="Confirmed" />
					<p class="sm">Confirmed</p>
				</div>
			</c:if>
		</td>
		<td>
			<strong><a href="${contextPath}/spnMonitorAction_loadDocument.action?&docID=${docList.docId}" class="clickViewDoc" target="_docWindow">${docList.documentTitle}</a></strong><br/>
			<input type="hidden" class="${docList.docId}_view_doc" value="0" />
			<input type="hidden" name="docId" value="${docList.docId}" />
		</td>
		<td class="tl carr br">
			<div class="DocAction_${buyerDocRow.docId}_${spnMonitorVO.spnId}">
			<c:if test="${!spnMonitorVO.isAlias && (docList.docStateId == null || docList.docStateId == 'DOC INCOMPLETE')}">
				<div class="${docList.docId}_col_select">
					<div class="select_button">
						<div>
							<span style="font-size: 10px;">I have read and agree on behalf of my company to be contractually bound by the ${docList.documentTitle}. If your company does not agree, you should not click the AGREE button.</span>
						</div>
						<div style="text-align: right;">
							<input type="button" class="default button agreeSelectButton buyerAgreeButton" value="Agree"/>
							<input type="hidden" name="docId" value="${docList.docId}" />
							<input type="hidden" name="spnId" value="${docList.spnId}" />
							<input type="hidden" name="docTitle" value="${docList.documentTitle}" />
						</div>
						<input type="hidden" name="docId" value="${docList.docId}" />
						<input type="hidden" name="spnId" value="${docList.spnId}" />
						<input type="hidden" name="docTitle" value="${docList.documentTitle}" />
						<input type="hidden" name="buyerName" value="${spnMonitorVO.buyerName}" />
						<input type="hidden" name="spnName" value="${spnMonitorVO.spnName}" />
					</div>
					<div class="time">
					</div>
				</div>
			</c:if>
			<c:if test="${docList.docStateId == 'DOC APPROVED'}">	
				<div style="font-size: 10px;">
					I have read and agree on behalf of my company to be contractually bound by the ${docList.documentTitle}.</div>
				</div>
				<div style="font-size: 10px;">
					<fmt:formatDate value="${docList.modifiedDate}" pattern="MM-dd-yyyy hh:mm a" /> CST
				</div>		
			</c:if>
			</div>
		</td>
	</tr>
	</c:forEach>
	</tbody>
	</table>
</div>
</c:if>