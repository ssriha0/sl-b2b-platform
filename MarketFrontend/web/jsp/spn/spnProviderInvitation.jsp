<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<html>
<head>
	<title>ServiceLive : SPN Invitation</title>
	<!-- tiles:insertDefinition name="blueprint.base.meta"/ -->
	<link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/screen.css" media="screen, projection">
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/superfish.css" media="screen, projection">
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/select-provider-network.css" media="screen, projection">
			<style type="text/css">
.spill{
margin-left: 0px!important;
border-right: 1px solid #CCCCCC;
width:640px!important;
}
.provTable td  {
	border: 0;
	float:none!important;
}
strong {
	padding: 0px!important;
}
#companyReqsDiv,#providerReqsDiv {
	border: 1px solid #CCC;
	background: #FFF;
	width:auto!important;
}
</style>	
<!--<style type="text/css">
		.modalOverlay {
			width: 100%; height: 100%; position:absolute; z-index: 400; background: #000; top:0px; left: 0px; filter:alpha(opacity=50);
			-moz-opacity:0.5; -khtml-opacity: 0.5; opacity: 0.5; display: none;
		}
	</style>-->
	
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery-form.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.flash.min.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.sifr.min.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/superfish.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/supersubs.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.hoverIntent.minified.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.bgiframe.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/toolbox.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
	
		<script language="JavaScript"
			src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
	<!-- end blueprint base include -->

	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/spn-invitation.css" media="screen, projection">
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/ui.core.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/ui.tabs.js"></script>
	<script type="text/javascript">
	
		jQuery(document).ready(function($) {
			$('#reason').hide();
			$('.nothanks').click(function(){
				$('#reason').toggle();
			});
		});

		function submitNoThankyou(spnId){		
			document.providerInvitationForm.rejectId.value=document.providerInvitationForm.nothankuReason.value;
			var myselect=document.getElementById('nothankuReason');
			document.providerInvitationForm.rejectReason.value=document.getElementById('nothankuReason').options[document.getElementById('nothankuReason').selectedIndex].text;
			if(document.getElementById('nothankuReason').value=='OTHER'){
				document.providerInvitationForm.rejectReason.value=document.providerInvitationForm.otherReason.value;
			}
			if(document.getElementById('nothankuReason').value==0){
				document.getElementById('noInviteError').style.display='block';
				return false;
			}
		
		
			document.providerInvitationForm.action="spnProviderInvitation_submitRejectInvitation.action?&selectedSpnId="+spnId;
			document.providerInvitationForm.submit();
		}
		
		function checkNothanku(){
			if(document.getElementById('nothankuReason').value=='OTHER'){
				document.getElementById('reasonText').style.display='block';
					
			}else{
				document.getElementById('reasonText').style.display='none';
			}
			return false;
		}
		function displayTab(divName,count){
		
		var val=0;
		
		val=count;
		var leftval=val%3;
		var topval=Math.ceil(val/3);
		var top=-10;
		var value=100;
		var left=100;
		if(leftval!=0){
		left=left*leftval;
		}else if(leftval==0){
			left=left*3;
		}
		if(topval!=1){
		top=top-(-30*topval);
		}
		document.getElementById(divName).className="tabbox clearfix"; 
		document.getElementById(divName).style.width="175px";
		document.getElementById(divName).style.top=top+"px";
		document.getElementById(divName).style.left=left+"px";
		$("#"+divName).toggle();
		}
		function hideTab(divName){
			$("#"+divName).hide();
			
		}
		function showSelectedSPN(spnId){
			document.providerInvitationForm.action = "spnMonitorAction_loadSPNMonitor.action?acceptInvite=1&selectedSpnId="+spnId;
			document.providerInvitationForm.submit();
		}
		//To show the Provider Requirements Flyout
		
		function togglePlusMinusImage(element)
		{
			jQuery(document).ready(function($) {
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

		jQuery(document).ready(function($) {
			$('.companyRequirementsLink').unbind('click').click(function(e) {
				var spnId = $(this).siblings('[name=spnId]').val();
				var vendorId = $(this).siblings('[name=vendorId]').val();
				
				togglePlusMinusImage($(this).children('.plus-image,.minus-image'));
				
				if ($('#companyRequirements_' + spnId).is(':hidden'))
				{
					$('#companyRequirements_' + spnId).load('spnMonitorAction_getCompanyRequirementsList.action?&spnID='+spnId+'&vendorId='+vendorId);
					$('#companyRequirements_' + spnId).show();
				}
				else
				{
					$('#companyRequirements_' + spnId).hide();
				}
			});
			
			$('.providerRequirementsLink').unbind('click').click(function(e) {
				var spnId = $(this).siblings('[name=spnId]').val();
				var vendorId = $(this).siblings('[name=vendorId]').val();
				
				togglePlusMinusImage($(this).children('.plus-image,.minus-image'));
				
				if ($('#providerRequirements_' + spnId).is(':hidden'))
				{
					$('#providerRequirements_' + spnId).load('spnMonitorAction_getProviderRequirementsList.action?&spnID='+spnId+'&vendorId='+vendorId);
					$('#providerRequirements_' + spnId).show();
				}
				else
				{
					$('#providerRequirements_' + spnId).hide();
				}
			});
		});
	</script>
</head>
<body id="select-provider-network">

	<div id="wrap" class="container">

		<tiles:insertDefinition name="blueprint.base.header"/>
		<tiles:insertDefinition name="blueprint.base.navigation"/>		
			
		<!-- end header tile -->

		
<s:form id="providerInvitationForm" name="providerInvitationForm" theme="simple">
<input type="hidden" id="rejectId" name="rejectId" value=""/>
<input type="hidden" id="rejectReason" name="rejectReason" value=""/>
		<div id="content" class="span-24 clearfix">

			<div id="primary" class="span-24 first last">
				<div class="content">
					<div id="invitation" class="content clearfix">
						<div id="invite-header" class="clearfix">
							<img class="logo left" src="buyerlogo_getBuyerLogo.action?buyer_id=${providerInvitation.buyerId}" title="Company Logo" -->
							<div class="right contact">
								<h5>Contact Information:</h5>
							
								<ul>
									<li>${providerInvitation.contactName}</li>
									<li><a href="mailto:${providerInvitation.contactEmail}">${providerInvitation.contactEmail}</a></li>
									<li><strong>${providerInvitation.contactPhone}</strong>
								</ul>
							</div>
						</div>
						<div class="invitation-content clearfix content">
	
							<div class="envelope-wrap">
								<div class="summary">
									<h3>You are invited to join ${providerInvitation.spnName}</h3>
									<p>${providerInvitation.spnDescription}</p>
								</div>
								<div id="envelope">
								&nbsp;
								</div>
							</div>
							
							<c:if test="${not empty providerInvitation.spnInstruction}">
								<div class="instructions">
									<h4>How to Join</h4>
									<p>${providerInvitation.spnInstruction}</p>
								</div>
							<hr/>
							</c:if>
							
							<c:if test="${not empty providerInvitation.spnInfoDocuments}">
							<h4>Learn More</h4>
								<p>Read these attachments to learn more about this opportunity and how to join.  Click to view and print.</p>
							<div id="documents" class="clearfix">
							</div>
							
							<div id="documents" class="clearfix">
							<c:set var="count" value="1"/>
							<c:forEach items="${providerInvitation.spnInfoDocuments}" var="docList">
							
							<c:set var="docClass" value=""/>
							<c:set var="docClassWord" value="application/msword"/>
							<c:set var="docClassPdf" value="application/pdf"/>
							<c:set var="docClassGIF" value="image/gif"/>
							<c:set var="docClassJPEG" value="image/jpeg"/>
							<c:set var="docClassJPG" value="image/jpg"/>
							<c:set var="docClassText" value="Text"/>
							<c:set var="docClassXML" value="XML"/>
							<c:set var="docType" value=""/>
							<c:if test="${docList.format==docClassPdf}">
								<c:set var="docClass" value="pdf"/>
								<c:set var="docType" value="(Adobe Acrobat file, requires Acrobat Reader to open)"/>
							</c:if>
							<c:if test="${docList.format==docClassWord}">
								<c:set var="docClass" value="doc"/>
								<c:set var="docType" value="(Microsoft Word File)"/>
							</c:if>
							<c:if test="${docList.format==docClassGIF}">
								<c:set var="docClass" value="pic"/>
								<c:set var="docType" value="(GIF Image)"/>
							</c:if>
							<c:if test="${docList.format==docClassJPEG}">
								<c:set var="docClass" value="pic"/>
								<c:set var="docType" value="(JPEG Image)"/>
							</c:if>
							<c:if test="${docList.format==docClassJPG}">
								<c:set var="docClass" value="pic"/>
								<c:set var="docType" value="(JPG Image)"/>
							</c:if>
							<c:set var="tabClass" value="display"/>
							<c:set var="tabClass" value="${tabClass}${docList.docId}"/>
							
								<dl class="${docClass}">
									<dt><a onmouseout="hideTab('${tabClass}','${count}');" onmouseover="displayTab('${tabClass}','${count}');" href="${contextPath}/spnMonitorAction_loadDocument.action?&docID=${docList.docId}" >${docList.documentTitle}</a></dt>
									<dd>${docType}</dd>
								</dl>
								
								<div class="tabs">
								<div id="${tabClass}" class="tabbox" style="width:200px;top:-10px;left:100px">Document Description<br/>${docList.docDescr}</div>
										</div>
							
							<c:set var="count" value="${count+1}"/>
							</c:forEach>
							</div>
							</c:if>
						</div>
						
						<div id="applynow" class="invitation-content clearfix content">
							<div id="invitation-requirements" class="clearfix">
								<h3>Membership Criteria &amp; Credentials </h3>

								<p>Below are the required criteria and documentation to required for membership. Click "Apply For Membership" below to begin. </p>
								<p>An asterix (<span class="req">*</span>) indicates credentials that must be verified by ServiceLive for membership. See the <a href="http://community.servicelive.com/docs/ServiceLive-Verification-Guide.pdf" target="_slVerificationGuide">ServiceLive Verification Guide</a>.</p>


								<h4>Required Criteria</h4>
								<p>Select a criteria level below to see how you qualify for this <abbr title="Select Provider Network">SPN</abbr>.</p>
								<div class="clearfix">
										<div id="providerRequirements" class="jqmWindow" style="width:400px;"></div>
										<div id="companyRequirements" class="jqmWindow" style="width:400px;"></div>
										<a name="companyReqs" id="companyReqs"></a>
										<div class="left"> 
											<div>
												<a href="#companyReqs" class="c-cat companyRequirementsLink">
													<span>View Company Requirements</span>
													<span class="plus-image" ></span>
												</a>
												<input type="hidden" name="spnId" value="${providerInvitation.spnId}" />
												<input type="hidden" name="vendorId" value="${SecurityContext.companyId}" />
											</div> 
											<div style="clear: both;"></div>
											<div id="companyRequirements_${providerInvitation.spnId}" class="hide"></div>
											<div style="clear: both;"></div>
											<div>
												<a href="#companyReqs" class="c-cat providerRequirementsLink">
													<span>View Provider Requirements</span>
													<span class="plus-image" ></span>
												</a>
												<input type="hidden" name="spnId" value="${providerInvitation.spnId}" />
												<input type="hidden" name="vendorId" value="${SecurityContext.companyId}" />
											</div>
											<div style="clear: both;"></div>
											<div id="providerRequirements_${providerInvitation.spnId}" class="hide"></div>
										</div>
								</div>								
								<c:if test="${not empty providerInvitation.spnSignAndReturnDocuments}">
								<br />
								<h4>Documents</h4>
								<p>When you apply, you will need to provide these documents. Click to view and print.</p>
								<div class="tableWrap">
									<table id="spn-monitor" cellspacing="0" cellpadding="0">
										<thead>
											<tr>
												<th class="br textcenter" style="width: 69px">Status</th>
												<th class="textleft">Required Documents</th>
												<th>&nbsp;</th>
											</tr>
										</thead>
										<tbody>
										<c:forEach items="${providerInvitation.spnSignAndReturnDocuments}" var="signDocList">
											<tr>
												<td class="br">
													<c:choose>
													<c:when test="${signDocList.docStateId =='DOC INCOMPLETE'}">
														<img src="${staticContextPath}/images/common/status-yellow.png" alt="Incomplete" /><br />
													</c:when>
													<c:when test="${signDocList.docStateId == 'DOC PENDING APPROVAL'}">
														<img src="${staticContextPath}/images/common/status-white.png" alt="Pending Approval" /><br />
													</c:when>
													<c:when test="${signDocList.docStateId =='DOC APPROVED'}">
														<img src="${staticContextPath}/images/common/status-green.png" alt="Approved" /><br />																		
													</c:when>	
													</c:choose>		
													<p class="sm">${signDocList.docStateDesc}</p>
												</td>
												<td class="textleft">
													<strong><a href="${contextPath}/spnMonitorAction_loadDocument.action?&docID=${signDocList.docId}">${signDocList.documentTitle}</a></strong><br/>
													<c:if test="${signDocList.buyerDocFormatDescription != null}">
														<small>(${signDocList.buyerDocFormatDescription})</small>
													</c:if>
													<c:choose>
													<c:when test="${signDocList.docStateId =='DOC INCOMPLETE'}">
													</c:when>
													<c:when test="${signDocList.docStateId == 'DOC PENDING APPROVAL'||signDocList.docStateId =='DOC APPROVED' }">
														<div class="nocomment"><strong>Document On File:</strong>
															<span id="OnFileDoc_${signDocList.docId}_${signDocList.spnId}">
																<a href="${contextPath}/spnMonitorAction_loadDocument.action?&docID=${signDocList.provFirmUplDocId}">${signDocList.docFileName}</a>
															</span>
														</div>
													</c:when> 
													</c:choose>
												</td>
												<td>&nbsp;</td>
											</tr>
										</c:forEach>
										</tbody>
									</table>
								</div>
								</c:if>	
								</div>
							</div>
							
							<div class="clearfix buttonarea">
							
							<input type="submit" class="button action right" value="Apply For Membership" onclick="showSelectedSPN('${providerInvitation.spnId}');" style="display:''">
							
								<a name="nothx" class="cancel left nothanks" href="#nothx">No Thank You</a>
								<div id="reason" class="left clearfix">
									<p id="noInviteError" class="error" style="display:none" >Please select a reason you are not interested to continue.</p>
									<p><strong>Please tell us why you are not interested:</strong>
										<select id="nothankuReason" name="nothankuReason" style="float: left;" onChange="checkNothanku()">
										<option value="0">Select One</option>
										<c:forEach items="${providerInvitation.spnInvitationNoInterestVO}" var="reasonList">
										<option value="${reasonList.reasonId}">${reasonList.reason}</option>
										
										</c:forEach>
										</select>
										<input style="margin-top: 7px;" type="button" onClick="submitNoThankyou('${providerInvitation.spnId}')" class="button default" value="Submit">
										<div id="reasonText" align="left" style="display:none;">
										<textarea name="otherReason" style="align:left"></textarea>
										</div>
										
										
									
								</div>
								 
							</div>
							
					
					</div>
					</div>
				</div>
			</div>
			</s:form>
		

		<tiles:insertDefinition name="blueprint.base.footer" />
</div>

	
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		<jsp:param name="PageName" value="spn:provider invitation" />
	</jsp:include>
	
</body>
</html>


