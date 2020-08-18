<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/hideShow.js"></script>


<script type="text/javascript">

$(document).ready(function()
{
	// $('#comments').maxlength({ maxCharacters: 150 });
	// Show or hide the 'Send notification' button based on attribute set from the server
	// after looking at document and M&G states
	//alert('tab-audit-documents: ' + ${allDocsApproved} + ' ' + ${meetApproved});

	if(${meetApproved} && ${allDocsApproved})
	{
		$('#showSendNotification_${networkId}_${providerFirmId}').hide();
	}
	else
	{
		$('#showSendNotification_${networkId}_${providerFirmId}').show();
	}
	var approvalAreaId = '#${networkId}_${providerFirmId}_approvebuttons';
	if($(approvalAreaId).is(":visible"))
	{
		var selectButtonClass = '.${networkId}_${providerFirmId}_selectbutton';
		$(selectButtonClass).show();
	}
});
	function limitTextarea(textarea, limit, infodiv)
	 {
	 	var text = textarea.value;	
	 	var textlength = text.length;
	 	var info = document.getElementById(infodiv);

	 	if(textlength > limit)
	 	{
 		 	textarea.style.backgroundColor = "#FFCDCD";
 		 	textarea.style.border = "3px solid #D55B5B";
 		 	textarea.value = text.substr(0,limit);
	 		return false;
	 	}
	 	else
	 	{
		 	textarea.style.backgroundColor = "";
 		 	textarea.style.border = "";
	 		info.innerHTML = '<span>'+ (limit - textlength) +' character left</span>';
	 		return true;
	 	}
	 }
	 
	 // Set the gray arrow/pointer-thingie to point to the row selected
	 	function docSelectionClick(obj, spnId, pfId){
		//$('.docSelectionButton').click(function(){
			$('tr[id^=docRow_]').removeClass('selected');
			$(obj).parent('td').parent('tr').addClass('selected');
			
			//alert($(this).attr('id'));
			$('#selectedDoc').attr('value', $(obj).attr('id'));
			//alert($('#selectedDoc').val());
			// Cannot select an action until a doc has been selected
			var selected_action = '.'+spnId+'_'+pfId+'_'+'selectedAction';
			$(selected_action).removeAttr('disabled');
		//});
		}
		
		// Disable other elements if the action dropdown has not selected a valid action.
		function selectedActionChange(spnId,pfId){
		//$('#selectedAction').change(function() {
			var selected = '.'+spnId+'_'+pfId+'_';
			if( $(selected+'selectedAction').val() != "-1")
			{				
				$(selected+'numPages').removeAttr('disabled');
				$(selected+'comments').removeAttr('disabled');
				$(selected+'button_submit').removeAttr('disabled');
				$(selected+'submit_div').removeClass("hidden");
			}
			else
			{
				$(selected+'numPages').attr("disabled", "true");
				$(selected+'comments').attr("disabled", "true");
				$(selected+'button_submit').attr("disabled", "true");
				$(selected+'submit_div').addClass("hidden");
			}
		//});
		}
		
		function buttonSubmitClick(spnId,pfId){
		//$('#button_submit').click(function() {
			var selected = '.'+spnId+'_'+pfId+'_';
			var comments = $(selected+'comments').val();
			var numPages = $(selected+'numPages').val();
			var action = $(selected+'selectedAction').val();
			var networkId = spnId;
			var providerFirmId = pfId;
			var docId = $('#selectedDoc').attr('value');
			
			if (comments == '')
			{
			
				$(selected+'button_submit_error').html('Please enter comments');
				$(selected+'button_submit_error').show();
			}
			else if (action == -1)
			{
				$(selected+'button_submit_error').html('Please choose action');
				$(selected+'button_submit_error').show();
			}
			else
			{
					$(selected+'documentAjaxRefresh').load("spnAuditorDocumentsTab_buttonSubmitAddAction.action", {'providerFirmId':providerFirmId, 'networkId':networkId, 'comments':comments, 'numPages': numPages, 'action': action, 'docId': docId});
			}
		//});	
		}
		
</script>

<div id="documentAjaxRefresh" name="documentAjaxRefresh" class="${networkId}_${providerFirmId}_documentAjaxRefresh">
	
	<input type="hidden" id="networkId" value="<s:property value='networkId' />"/>
	<input type="hidden" id="providerFirmId" value="<s:property value='providerFirmId' />"/>
	<input type="hidden" id="selectedDoc" value="-1"/>
	

	<div class="clearfix">
		<div class="tableInner">
			<table border="0" cellpadding="0" cellspacing="0"
				style="margin-bottom: 0px;table-layout:fixed;">
				<thead>
					<tr>
						<th class="tc pending" style="width: 60px;">
							Status
						</th>
						<th class="tl pending">
							Documents
						</th>
						<th class="tl approved">
							Last Audit
						</th>
						<th>
							&nbsp;
						</th>
					</tr>
				</thead>
				<tbody>
				
					
					<s:if test="documents">
						<!-- begin loop -->
						<s:iterator status="doc" value="documents">
							<tr id="docRow_${doc.index}" >
								<td class="tc" rowspan="2">
									<c:choose>
										<c:when test="${status=='Approved'}">
											<img src="${staticContextPath}/images/common/status-green.png"
												alt="<s:property value='status'/>" />
											<br />
										</c:when>
										<c:when test="${status=='Pending Approval'}">
											<img
												src="${staticContextPath}/images/common/status-yellow.png"
												alt="<s:property value='status'/>" />
											<br />
										</c:when>
										<c:when test="${status=='Incomplete'}">
											<img
												src="${staticContextPath}/images/common/status-yellow.png"
												alt="<s:property value='status'/>" />
											<br />
										</c:when>
										<c:when test="${status=='Need for Info'}">
											<img
												src="${staticContextPath}/images/common/status-yellow.png"
												alt="<s:property value='status'/>" />
											<br />
										</c:when>
										<c:otherwise>
											<img src="${staticContextPath}/images/common/status-red.png"
												alt="<s:property value='status'/>" />
											<br />
										</c:otherwise>
									</c:choose>
	
									<small><s:property value="status" />
									</small>
								</td>
								<td class="tl nb">
									<strong>
										<a href="spnAuditorDocumentsTab_loadDocumentAjax.action?docId=<s:property value='id' />" target="_newDocWindow" style="word-wrap:break-word;color: #00A0D2;">
											<s:property value="title" />
										</a>
									</strong>
									<br/>
									<span style="font-size: 10px;word-wrap:break-word;">(<s:property value="filename" />)</span>
									<c:if test="${not empty numPages}">
										<br />Page Numbers: <s:property value="numPages" />
									</c:if>
								</td>
								<td class="nb">
									<small>
										<strong>
											<s:property value="lastAuditorName" />
										</strong>
										<s:if test="lastAuditorID != null">
											#<s:property value="lastAuditorID" />
										</s:if>
									</small>
									<br />
									<small> <fmt:formatDate value="${lastAuditDate}"
											pattern="MM-dd-yyyy HH:mm a zz" /> </small>
								</td>
								<td class="carr" rowspan="2" >
									<c:if test="${expandCriteriaVO.fromSearch == 1}">
										<input id="<s:property value='id'/>" onclick="docSelectionClick(this,'${networkId}','${providerFirmId}')"
											type="submit" class="default docSelectionButton hide ${networkId}_${providerFirmId}_selectbutton" value="select">
									</c:if>
									<c:if test="${expandCriteriaVO.fromSearch != 1}">
										<input id="<s:property value='id'/>" onclick="docSelectionClick(this,'${networkId}','${providerFirmId}')"
											type="submit" class="default docSelectionButton" value="select">
									</c:if>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<div class="comment">
										<c:if test="${not empty comments}">
											<strong><s:property value="action" />:</strong>
											<s:property value="comments" />
										</c:if>
									</div>
								</td>
							</tr>
						</s:iterator>					
						
					</s:if>
					<s:else>
						<tr height="100%">
							<td colspan="4" height="100%" style="text-align:center">
								No Documents Found
							</td>
						</tr>														
					</s:else>
					<!-- end loop -->

				</tbody>
			</table>
		</div>

		<div class="content" style="margin-left: 450px;">
			
			<s:if test="errorMessage != null && errorMessage!=''">
				<div class="error">
					<s:property value="errorMessage" />
				</div>
			</s:if>
			
			<div id="button_submit_error" class="error hide ${networkId}_${providerFirmId}_button_submit_error"></div>
		
			An asterix (
			<span class="req">*</span>) indicates a required field.
			<br />
			<br />
			<p>
				<label>
					Action
					<span class="req">*</span>:
				</label>
					<s:select id="selectedAction" name="selectedAction" cssClass="%{networkId}_%{providerFirmId}_selectedAction"
						list="actionList" theme="simple" listKey="id"
						listValue="description" headerKey="-1" headerValue="-- Select --" 
						disabled="true" onchange="selectedActionChange('%{networkId}','%{providerFirmId}')"/>
					<br />
				<label>
					Pages Numbers:
				</label>
				<s:textfield id="numPages" name="numPages" cssClass="text %{networkId}_%{providerFirmId}_numPages"
					cssStyle="width: 50px;" theme="simple" disabled="true" />
				<br />

				<label>
					Comments
					<span class="req">*</span>:
				</label>
				
				<s:textarea id="comments" name="comments" cssClass="text %{networkId}_%{providerFirmId}_comments"
					cssStyle="width: 195px;" theme="simple" disabled="true" 
					onkeyup="limitTextarea(this,150,'commentsChar');" 
					onkeydown="limitTextarea(this,150,'commentsChar');"
					/><br/>
				<span id="commentsChar">150 characters left </span>
				
				<!-- 
				 <input type="text" readonly="readonly" size="3" maxlength="3"
				value="150" name="commentsChar"	id="commentsChar" />
				characters remaining -->
				
				<s:if test="documents">
					<div id="submit_div" name="submit_div" class="hidden ${networkId}_${providerFirmId}_submit_div" >
						<input type="submit" id="button_submit" name="button_submit" class="button action right ${networkId}_${providerFirmId}_button_submit"
							value="submit" style="" disabled="disabled" onclick="buttonSubmitClick('${networkId}','${providerFirmId}')"/>
					</div>
				</s:if>
			</p>

		</div>

	</div>
</div>