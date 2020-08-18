<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<c:if test="${expandCriteriaVO.fromSearch != 1}">
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js"></script>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/modals.css" media="screen, projection">
</c:if>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/ui.tabs3.css" media="screen, projection">
<script type="text/javascript">
	$(document).ready(function() {
		$('div.tabs > ul').tabs();
		$("#subTabs<s:property value='firmID' />-${networkId}").tabs();

		
		// Show or hide the 'Send notification' button based on attribute set from the server
		// after looking at document and M&G states
		//alert('panel_applicant:' + ${allDocsApproved} + ' ' + ${meetApproved});
		if(${meetApproved} && ${allDocsApproved})
		{
			$('#showSendNotification_${networkId}_${providerFirmId}').hide();
		}
		else
		{
			$('#showSendNotification_${networkId}_${providerFirmId}').show();
		}

	});
	
	$(document).ready(function()
		{			
			$("#declinemodal").jqm();
			
			var nAgt = navigator.userAgent;
			if (nAgt.indexOf("Chrome") != -1) { 
				$(".membershipReviewSubmit").css("margin-top","-25px");  
			}
	});
		
		function togglePlusMinusImage(element)
		{
			$(document).ready(function() {
				if (element.hasClass('plus-image'))
				{
					element.addClass('minus-image');
					element.removeClass('plus-image');
				}
				else if (element.hasClass('minus-image'))
				{
					element.addClass('plus-image');
					element.removeClass('minus-image');
				}
			});
		}
		
		function clickCompanyRequirements(spnId, vendorId)
		{
			var companyLinkId = "#view_company_link_" + spnId + "_" + vendorId;
			togglePlusMinusImage($(companyLinkId).children('.plus-image,.minus-image'));
			
			if ($('#companyRequirements_' + spnId + '_' + vendorId).is(':hidden'))
			{
			   $('#companyRequirements_' + spnId + '_' + vendorId).load('/MarketFrontend/spnMonitorAction_getCompanyRequirementsList.action?spnID='+spnId+'&vendorId='+vendorId,function(){
					   $("#complFirmUpdated").css("margin-right","");

			   });
			//	$('#companyRequirements_' + spnId + '_' + vendorId).load('spnAuditorCriteriaAndCredentialsTab_getCompanyRequirementsListAjax.action?networkId='+spnId+'&providerFirmId='+vendorId);
					$('#companyRequirements_' + spnId + '_' + vendorId).show();

			}
			else
			{
				$('#companyRequirements_' + spnId + '_' + vendorId).hide();
			}
		}
		
		function clickProviderRequirements(spnId, vendorId)
		{
			var providerLinkId = "#view_provider_link_" + spnId + "_" + vendorId;
			togglePlusMinusImage($(providerLinkId).children('.plus-image,.minus-image'));
				
			if ($('#providerRequirements_' + spnId + '_' + vendorId).is(':hidden'))
			{   
			    $('#providerRequirements_' + spnId + '_' + vendorId).load('/MarketFrontend/spnMonitorAction_getProviderRequirementsList.action?spnID='+spnId+'&vendorId='+vendorId,function(){
						$("#complProvUpdated").css("margin-left","0px");

			    });
			// $('#providerRequirements_' + spnId + '_' + vendorId).load('spnAuditorCriteriaAndCredentialsTab_getProviderRequirementsListAjax.action?networkId='+spnId+'&providerFirmId='+vendorId);
				$('#providerRequirements_' + spnId + '_' + vendorId).show();
			}
			else
			{
				$('#providerRequirements_' + spnId + '_' + vendorId).hide();
			}
		}
	
		function showTooltip(){
	
			var toolTip = "&nbsp Use the Membership Under Review flag only when the &nbsp</br> application requires review by more than one person.&nbsp";
	
			$("#explainer").css("position","absolute");
	   	
	    	$("#explainer").css("margin-left",120);
	    	$("#explainer").css("margin-top",35);
	    	 $("#explainer").css("font-weight","bold");
	    	 $("#explainer").css("border","1px solid black");
	    	 $("#explainer").css("background-color","lightyellow");
	    		
	    	 $("#explainer").html(toolTip);
	   	 	$("#explainer").show();  
    	
		}
		 
		function hideTooltip(){
			$("#explainer").hide();
		}
		
		//SL-19982
		function editButtonClick(obj){
			var pfId = $(obj).siblings('[name=pfId]').val();
			var spnId = $(obj).siblings('[name=spnId]').val();
			var origModDate = $(obj).siblings('[name=origModDate]').val();
			
			var approve_id = "#" + spnId + "_" + pfId + "_approvebuttons";
			var edit_id = "#" + spnId + "_" + pfId + "_editbutton";
			var load_id = "#" + spnId + "_" + pfId + "_loadarea";
			var error_id = "#" + spnId + "_" + pfId + "_error";
			
			//$(load_id).load
			$.post('spnAuditorApplicantsTab_getProviderFirmSPNLockAjax.action', 
				{ 'expandCriteriaVO.spnId': spnId, 'expandCriteriaVO.providerFirmId': pfId, 'expandCriteriaVO.originalModifiedDate': origModDate },
				function(data) {
					//alert('here');
					//alert(data.isLocked);
					
					if (data.isLocked == 'false')
					{
						$(edit_id).hide();
						$(approve_id).show();
						$('#searchOrigModDate_${expandCriteriaVO.providerFirmId}_${expandCriteriaVO.spnId}').val(data.modifiedDate);
						var selectButtonClass = "." + spnId + "_" + pfId + "_selectbutton";
						$(selectButtonClass).show();
						var selectButtonId = "#" + spnId + "_" + pfId + "_selectbutton_meetgreet";
						$(selectButtonId).show();
					}
					if (data.isLocked == 'true')
					{
						$(error_id).html('This record has been locked by another user.');
						$(error_id).show();
						$(edit_id).hide();
						$(approve_id).hide();
					}
			}, 'json' );
		}
		
		function releaseClick(obj){
			var pfId = $(obj).parent().siblings().children('[name=pfId]').val();
			var spnId = $(obj).parent().siblings().children('[name=spnId]').val();
			
			var approve_id = "#" + spnId + "_" + pfId + "_approvebuttons";
			var edit_id = "#" + spnId + "_" + pfId + "_editbutton";
			var load_id = "#" + spnId + "_" + pfId + "_loadarea";
			var error_id = "#" + spnId + "_" + pfId + "_error";
			
			$(load_id).load('spnAuditorApplicantsTab_unlockProviderFirmSPNAjax.action', 
				{ 'expandCriteriaVO.spnId': spnId, 'expandCriteriaVO.providerFirmId': pfId }, function(data){
				
				$(edit_id).show();
				$(approve_id).hide();
				$(error_id).html('');
				$(error_id).hide();
				var selectButtonClass = "." + spnId + "_" + pfId + "_selectbutton";
				$(selectButtonClass).hide();
				var selectButtonId = "#" + spnId + "_" + pfId + "_selectbutton_meetgreet";
				$(selectButtonId).show();
				
			}, 'json' );
		}
		
		function buttonApproveClick(spnId, pfId, obj){
				var fromSearch = 0;
				<c:if test="${expandCriteriaVO != null && expandCriteriaVO.fromSearch != null && expandCriteriaVO.fromSearch == 1}">
					fromSearch = 1;
				</c:if>				
				if(fromSearch == 1)
				{
					var disableApprove = "#button_approve_dis_"+spnId+"_"+pfId;
					$(obj).hide();
					$(disableApprove).show();
					var selected = '.'+spnId+'_'+pfId+'_';
					var networkId = spnId;
					var providerFirmId = pfId;
					//alert(networkId + ' ' + providerFirmId);
					$.post('spnAuditorApplicantsTab_buttonAcceptAjax.action', {'networkId': networkId, 'providerFirmId' : providerFirmId, 'expandCriteriaVO.fromSearch': fromSearch}, function(data){
						$(selected+'approveDeclineErrorDoc').hide();
						$(selected+'approveDeclineErrorMG').hide();
						if (data.errorMsgDoc != '')
						{
							$(disableApprove).hide();
							$(obj).show();
							$(selected+'approveDeclineErrorDoc').html(data.errorMsgDoc);
							$(selected+'approveDeclineErrorDoc').show();
						}
						if (data.errorMsgMG != '')
						{
							$(disableApprove).hide();
							$(obj).show();
							$(selected+'approveDeclineErrorMG').html(data.errorMsgMG);
							$(selected+'approveDeclineErrorMG').show();
						}
						
						if (data.errorMsgMG == '' && data.errorMsgDoc == '')
						{
						    var status = '${spnStatus}';
						    if('PF SPN MEMBERSHIP UNDER REVIEW' == status){
						    	var membershipCount = $('.spnMemRevwCnt').html();
								membershipCount = --membershipCount;
								$('.spnMemRevwCnt').html(membershipCount);
						    }
							submitSearch();
						}
					}, 'json');
				}
			}
			
			function buttonDeclineClick(){
			
				// If decline modal textarea input is empty, show the error message.  Do not submit.
				if($('#declineComment').val() == null || $('#declineComment').val() == '')
				{
					//$('.error').removeClass('hidden');
					$('#declinemodalerror').removeClass('hidden');
					return false;
				}
				else
				{			
					// Set the hidden variable to the value from the textearea box
					$('#comment').val($('#declineComment').val());
					
					var fromSearch = 0;
					<c:if test="${expandCriteriaVO != null && expandCriteriaVO.fromSearch != null && expandCriteriaVO.fromSearch == 1}">
						fromSearch = 1;
					</c:if>
					
					//alert(fromSearch);
					if(fromSearch != 1)
					{
						// Submit the form
						$('#declineCommentsPopupForm').submit();
					}
					else
					{
						var comment = $('#declineComment').val();
						var networkId = $('#declineSpnId').val();
						var providerFirmId = $('#declinePfId').val();
						$('#declinemodal').jqmHide();
						$.post('spnAuditorApplicantsTab_buttonDeclineAjax.action', {'networkId': networkId, 'providerFirmId' : providerFirmId, 'comment': comment}, function(data){
							var status = '${spnStatus}';
							if('PF SPN MEMBERSHIP UNDER REVIEW' == status){
						    	var membershipCount = $('.spnMemRevwCnt').html();
								membershipCount = --membershipCount;
								$('.spnMemRevwCnt').html(membershipCount);
						    }
							submitSearch();
						});
					}
				}
			}
			
			function buttonSendNotificationClick(spnId, pfId){
				var fromSearch = 0;
				<c:if test="${expandCriteriaVO != null && expandCriteriaVO.fromSearch != null && expandCriteriaVO.fromSearch == 1}">
					fromSearch = 1;
				</c:if>					
				if(fromSearch == 1)
				{
					var networkId = spnId;
					var providerFirmId = pfId;
					$.post('spnAuditorApplicantsTab_buttonSendNotificationAjax.action', {'networkId': networkId, 'providerFirmId' : providerFirmId}, function(data){
						submitSearch();
					});
				}
			}
			
			function buttonMembershipUnderReviewClick(spnId, pfId, obj){
				var fromSearch = 0;
				<c:if test="${expandCriteriaVO != null && expandCriteriaVO.fromSearch != null && expandCriteriaVO.fromSearch == 1}">
					fromSearch = 1;
				</c:if>		
							
				if(fromSearch == 1)
				{
					$(obj).attr("onclick","return false");
					var networkId = spnId;
					var providerFirmId = pfId;
					$.post('spnAuditorApplicantsTab_buttonMembershipUnderReviewAjax.action', {'networkId': networkId, 'providerFirmId' : providerFirmId, 'expandCriteriaVO.fromSearch': fromSearch}, function(data){
						var membershipCount = $('.spnMemRevwCnt').html();
						membershipCount = ++membershipCount;
						$('.spnMemRevwCnt').html(membershipCount);
						submitSearch();
					});
				}
			}
			
			function opendeclineClick(spnId, pfId){
				$("#declinemodal").jqmShow();
				$('#declinemodalerror').addClass("hidden");
				$('#declineSpnId').val(spnId);
				$('#declinePfId').val(pfId);
			}
</script>

<s:if test="contact != null">
	<div id="auditPanel">
		<div class="clearfix titlebar">
			<div class="left">
				<strong><s:property value="businessName" /> </strong> #
				<s:property value="firmID" />
			</div>
			<div class="right">
				<strong>Auditing for: </strong>
				<s:property value="networkName" />
			</div>
		</div>
		<div class="tableWrap">

			<table id="information" border="0" cellpadding="0" cellspacing="0">
				<tbody>

					<s:if test="errorMessage != null">
						<tr>
							<td colspan=3 valign="top">
								<div class="error">
									<s:property value="errorMessage" />
								</div>
							</td>
						</tr>
					</s:if>
					
					<tr><td colspan=3><div class="error hidden ${networkId}_${providerFirmId}_approveDeclineErrorDoc" id="approveDeclineErrorDoc"></div><div class="error hidden ${networkId}_${providerFirmId}_approveDeclineErrorMG" id="approveDeclineErrorMG"></div></td></tr>

					<tr>

						<td valign="top">
							<strong>Contact Information:</strong>
							<br />
							<s:property value="businessName" />
							<br />
							<c:if test="${not empty dba}">
								<s:property value="dba" />
								<br />
							</c:if>
							
							<s:property value="contact.firstName" />
							<s:property value="contact.lastName" />
							(#<s:property value="firmID" />)
							<br />
							
							<!-- SL-19381 :Code added to display contact information of a Firm -->
							
							<s:property value="providerLocationDetails.street1" />,<s:property value="providerLocationDetails.street2" />
							<br />
							<s:property value="providerLocationDetails.city" />,<s:property value="providerLocationDetails.stateCd" /> <s:property value="providerLocationDetails.zip" />

							<br />
							<a href="mailto:<s:property value='contact.email' />">
								<s:property value="contact.email" />
							</a>
							<br />
							<s:property value="contact.phoneNo" />
						</td>
						<td valign="top">
							<strong>ServiceLive Status: <span class="approved"><s:property
										value="slStatus" /> </span> <!-- <span class="suspended">Suspended</span> -->
							</strong>
							<br />
							<br />
							<strong>Invited:</strong>
							<br />
							<s:property value="invitedDate" />
							<br />
							<br />
							<strong>Applied:</strong>
							<br />
							<s:property value="appliedDate" />
						</td>
						<td valign="top">
							&nbsp;
						</td>
					</tr>
				</tbody>
			</table>

			<div id ="subTabs<s:property value='firmID' />-${networkId}" class="content" style="font-size: 12px;
    				font-family: Helvetica Neue, Helvetica, Arial, sans-serif; border:  1px solid #CCC;">
				<ul>

					<c:if test="${displayDocumentsTab}">
						<li>
							<a href="spnAuditorDocumentsTab_viewTabAjax.action?providerFirmId=<s:property value='firmID' />&networkId=${networkId}&expandCriteriaVO.fromSearch=${expandCriteriaVO.fromSearch}">
								Documents
							</a>
						</li>
					</c:if>
					<c:if test="${displayMeetAndGreetTab}">
						<li>
							<a href="spnAuditorMeetAndGreetTab_viewTabAjax.action?providerFirmId=<s:property value='firmID' />&networkId=${networkId}&expandCriteriaVO.fromSearch=${expandCriteriaVO.fromSearch}">
								Meet &amp; Greet
							</a>
						</li>
					</c:if>

					<li>
						<a href="spnAuditorCriteriaAndCredentialsTab_viewTabAjax.action?providerFirmId=<s:property value='firmID' />&networkId=${networkId}&expandCriteriaVO.fromSearch=${expandCriteriaVO.fromSearch}">
							Criteria &amp; Credentials </a>
					</li>

				</ul>

			</div>

			<c:if
				test="${spnStatus != 'PF SPN INTERESTED' && spnStatus != 'PF SPN NOT INTERESTED' && spnStatus != 'PF INVITED TO SPN' }">
				<c:set var="editButtonsHidden" value=" " />

				<c:if
					test="${expandCriteriaVO.fromSearch == 1 && (expandCriteriaVO.lockedRecord != true || 
						(expandCriteriaVO.lockedRecord == true && expandCriteriaVO.lockedByMe == true))}">
					<div
						id="${expandCriteriaVO.spnId}_${expandCriteriaVO.providerFirmId}_error"
						class="clearfix content buttonarea hidden"
						style="display: none; color: red;">
					</div>
					<div
						id="${expandCriteriaVO.spnId}_${expandCriteriaVO.providerFirmId}_editbutton"
						class="clearfix content buttonarea" style="text-align: right;">
						<input type="submit" class="editButton default button right" onclick="editButtonClick(this)"
							value="Edit" id="${expandCriteriaVO.spnId}_${expandCriteriaVO.providerFirmId}_edit">
						<input type="hidden" name="spnId"
							value="${expandCriteriaVO.spnId}" />
						<input type="hidden" name="pfId"
							value="${expandCriteriaVO.providerFirmId}" />
						<input type="hidden" name="origModDate"
							value="${expandCriteriaVO.originalModifiedDate}" />
					</div>
					<c:set var="editButtonsHidden" value=" display: none; " />

					<div
						id="${expandCriteriaVO.spnId}_${expandCriteriaVO.providerFirmId}_approvebuttons"
						class="clearfix content buttonarea hidden"
						style="text-align: right;">

						<s:hidden name="networkId" id="networkId" value="%{#request['expandCriteriaVO'].spnId}" />
						<s:hidden name="providerFirmId" id="providerFirmId" value="%{#request['expandCriteriaVO'].providerFirmId}" />					
						
					
						<div id="showSendNotification_${networkId}_${providerFirmId}">
							<input id="button_sendnotification" class="default button right buttonSendNotification" value="send notification" type="button"
								onclick="buttonSendNotificationClick('${networkId}','${providerFirmId}')"/>
						</div>
						
						<c:if test="${spnStatus != 'PF SPN MEMBER' }">													
							<input id="button_approve" class="action button right buttonApprove" value="approve" type="button" 
								onclick="buttonApproveClick('${networkId}','${providerFirmId}',this)"/>
							<input id="button_approve_dis_${networkId}_${providerFirmId}" class="action button right" 
								value="approve" type="button" style="display:none"/>
						
						</c:if>
						<c:if test="${spnStatus == 'PF SPN APPLICANT'}">	
						<input id="button_membership_under_review" type="button" class="action button left buttonMembershipUnderReviewAjax" value="Membership Under Review" style="width:170px" 
							title= "Use the Membership Under Review flag only when the application requires review by more than one person." onclick="buttonMembershipUnderReviewClick('${networkId}','${providerFirmId}',this)"/>
						</c:if>
												
						<c:if test="${spnStatus != 'PF SPN DECLINED'}">
							<input id="button_decline" class="default button right opendeclinemodal" value="decline" 
								type="button" onclick="opendeclineClick('${networkId}','${providerFirmId}')"/>
						</c:if>
						
						<input type="submit" class="default button left release" onclick="releaseClick(this)"
							value="release" id="${expandCriteriaVO.spnId}_${expandCriteriaVO.providerFirmId}_release">
					</div>
				</c:if>
				<c:if test="${expandCriteriaVO.fromSearch != 1}">
					<div class="clearfix content buttonarea" style="text-align: right;">
							
						<div id="showSendNotification_${networkId}_${providerFirmId}">
							<s:form id="sendNotificationForm" name="sendNotificationForm"
								action="spnAuditorApplicantsTab_buttonSendNotificationAjax" theme="simple">
								<input type="hidden" name="networkId" id="networkId" value="${networkId}" />
								<input type="hidden" name="providerFirmId" id="providerFirmId" value="${providerFirmId}" />
								<input type="hidden" name="auditorTab" id="auditorTab" value="${tab}" />
								<s:submit id="button_sendnotification"
									cssClass="default button right" value="send notification"
									theme="simple" />
							</s:form>
						</div>
						
						<s:form id="approveFirmForm" name="approveFirmForm"
							action="spnAuditorApplicantsTab_buttonAcceptAjax" theme="simple">
							<input type="hidden" name="networkId" id="networkId" value="${networkId}" />
							<input type="hidden" name="providerFirmId" id="providerFirmId"
								value="${providerFirmId}" />
							<input type="hidden" name="auditorTab" id="auditorTab" value="${tab}" />
							<s:submit id="button_approve" cssClass="action button right"
								value="approve" theme="simple"/>
						</s:form>

						<s:form id="declineFirmForm" name="declineFirmForm"
							action="spnAuditorApplicantsTab_buttonDeclineAjax">
							<input type="hidden" name="networkId" id="networkId" value="${networkId}" />
							<input type="hidden" name="providerFirmId" id="providerFirmId"
								value="${providerFirmId}" />
							<input type="hidden" name="auditorTab" id="auditorTab" value="${tab}" /> 
							<input id="button_decline" class="default button right opendeclinemodal"
								value="decline" type="button" onclick="opendeclineClick('${networkId}','${providerFirmId}')"/>
															
						</s:form>
						<c:if test="${spnStatus == 'PF SPN APPLICANT'}">	
						<s:form id="memberShipUnderReviewFirmForm" name="memberShipUnderReviewFirmForm"
							action="spnAuditorApplicantsTab_buttonMembershipUnderReviewAjax" theme="simple">
							<input type="hidden" name="networkId" id="networkId" value="${networkId}" />
							<input type="hidden" name="providerFirmId" id="providerFirmId"
								value="${providerFirmId}" />
							<s:submit id="button_membership_under_review" cssClass="action button left membershipReviewSubmit"
								value="Membership Under Review" theme="simple" title= "Use the Membership Under Review flag only when the application requires review by more than one person." />
								<div id="explainer" style="z-index: 1000"></div>
						</s:form>
						</c:if>
						
					</div>
				</c:if>
				<div
					id="${expandCriteriaVO.spnId}_${expandCriteriaVO.providerFirmId}_loadarea"
					style="display: none;"></div>

			</c:if>

		</div>


	</div>
</s:if>
<s:else>
	<div class="clearfix titlebar">
		<div class="left">
			No Applicants in Queue
		</div>
		<div class="right">
		</div>
	</div>
	<div class="tableWrap">
		&nbsp;
	</div>
</s:else>


<!-- begin modal -->
<!-- add the class of 'opendeclinemodal' to the link you want to open this modal -->

<div id="declinemodal" class="jqmWindow">
	<input type="hidden" id="declineSpnId" value=""/>
	<input type="hidden" id="declinePfId" value=""/>
	<div class="modal-header clearfix">
		<span class="left" style="font-color: white; font-size: 16px; font-weight: bold;">
			Decline Membership
		</span>
		<a href="#" class="right jqmClose">Close</a>
	</div>
	<div class="modal-content">
		<p id="declinemodalerror" class="error hidden">
			You must enter a comment when declining an Applicant.
		</p>
		<p>
			Items marked with an Asterix (
			<span class="req">*</span>) are required.
		</p>
		<p>
			<strong>Enter information to send to the provider to explain
				why you declined this provider's application to your SPN.</strong>
		</p>
		<label>
			Comments:<span class="req">*</span>
		</label>
		<textarea id="declineComment" name="declineComment"></textarea>
		<div class="clearfix">
			<a class="cancel jqmClose left" href="#">Cancel</a>
			
			<c:choose>
					<c:when test="${expandCriteriaVO.fromSearch == 1}">
						<input id="buttonDeclineDone" name="buttonDeclineDone" type="submit" class="action right buttonDeclineDone" value="Done" onclick="buttonDeclineClick()"/>
					</c:when>
			</c:choose>
			<s:form id="declineCommentsPopupForm" name="declineCommentsPopupForm" action="spnAuditorApplicantsTab_buttonDeclineAjax" theme="simple">			
				<input type="hidden" name="providerFirmId" id="providerFirmId" value="${providerFirmId}"/>
				<input type="hidden" name="networkId" id="providerFirmId" value="${networkId}"/>
				<input type="hidden" name="comment" id="comment" />
				<input type="hidden" name="auditorTab" id="auditorTab" value="${tab}" />
				<c:choose>
					<c:when test="${expandCriteriaVO.fromSearch != 1}">
						<input id="buttonDeclineDone" name="buttonDeclineDone" type="button" class="action right buttonDeclineDone" value="Done" onclick="buttonDeclineClick()"/>
					</c:when>
				</c:choose>								
			</s:form>
		</div>
	</div>
</div>
<!-- end modal -->