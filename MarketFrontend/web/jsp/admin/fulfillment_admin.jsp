<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive Fulfillment Admin Tool</title>
		<link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	      .ie7 .bannerDiv{margin-left:-1010px;}
		</style>
		
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		
		<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit._Calendar");
			dojo.require("dojo.date.locale");
			dojo.require("dojo.parser");
			dojo.require("newco.rthelper");
		    dojo.require("newco.jsutils");
			dojo.require("dijit.form.DateTextBox");		    
		</script>	    
		
		<style type="text/css">
			@import
				"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css"
				;
			
			@import
				"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css"
				;
			</style>
					<link rel="stylesheet" type="text/css"
						href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
					<link rel="stylesheet" type="text/css"
						href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
					<link rel="stylesheet" type="text/css"
						href="${staticContextPath}/css/slider.css" />
					<link rel="stylesheet" type="text/css"
						href="${staticContextPath}/css/main.css" />
					<link rel="stylesheet" type="text/css"
						href="${staticContextPath}/css/iehacks.css" />
					<link rel="stylesheet" type="text/css"
						href="${staticContextPath}/css/top-section.css" />
					<link rel="stylesheet" type="text/css"
						href="${staticContextPath}/css/tooltips.css" />
					<link rel="stylesheet" type="text/css"
						href="${staticContextPath}/css/service_order_wizard.css" />
					<link rel="stylesheet" type="text/css"
						href="${staticContextPath}/css/registration.css" />
					<link rel="stylesheet" type="text/css"
						href="${staticContextPath}/css/buttons.css" />
					<link rel="stylesheet" type="text/css"
						href="${staticContextPath}/css/acquity.css" />
	</head>
	<c:choose>
		<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
			<body class="tundra acquity simple" onload="${onloadFunction};">
		</c:when>
		<c:otherwise>
			<body class="tundra acquity" onload="${onloadFunction};">
		</c:otherwise>
	</c:choose>
	
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="Admin - Fulfillment Admin"/>
	</jsp:include>
	<c:choose>	
		<c:when test="${success}">
			<c:set var="colorVar" value="blue" />
		</c:when>
		<c:when test="${failure}">
			<c:set var="colorVar" value="red" />
		</c:when>
	</c:choose>
		<div id="page_margins">
			<div id="page">
				<div id="headerSPReg">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>							
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
				</div>
						<!-- NEW MODULE/ WIDGET--> 
				 
					<div class="colLeft900"><div class="content">
						<div class="colLeft900">
							<div>					
								<c:if test="${failedLookup}"><b><font color="red">${dto.failedMsgLookup}</font></b></c:if>
							</div>
						</div>
						<s:form
							action="fullfillmentAdminAction_lookup"
							id="fullfillmentAdminAction_lookup"
							name="fullfillmentAdminAction_lookup"
							method="post" theme="simple">
							<div class="grayModuleHdr">Lookup Fulfillment Record(s)(space delimited) by Id or Group Id</div>
						
						<div class="grayModuleContent mainWellContent">
							<table width="900">
								
								<c:if test="${lookUpMessage != ''}">
									<tr>
										<td width="900"><b><font color="${colorVar}">${dto.lookUpMessage}</font></b></td>
									</tr>
									<tr><td>&nbsp;</td></tr>
								</c:if>
								
								<tr>
									<td>
										<p>
											<label>Fulfillment Entry or Group Id(s) (space delimited) to lookup</label>
											<br /><br />
											<s:textarea id="fulfillmentEntryId" name="fulfillmentEntryId"
												cssStyle="width: 200px;" cssClass="shadowBox grayText"
												value="%{dto.fulfillmentEntryId}"
												theme="simple" />
											<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Group id's?</label>

											<s:checkbox id="groupId" name="groupId"
												value="%{dto.groupId}"
												theme="simple"/>
												
										</p>
									</td>
								</tr>
								<tr><td>&nbsp;</td></tr>
								<tr>
								  <td>								
									<div>
								       <input type="submit"
										method="lookup"
										theme="simple" value="Find FulFillment Record(s)" />
									</div>
									</td>
								</tr>
							</table>
					<c:if test="${lookup}">	
					<div class="grayTableContainer" style="width: 100%; height: 130px;WORD-BREAK:BREAK-ALL;">
						<table cellpadding="0" cellspacing="0" style="width: 100%; height: 20px;">
							<tr bgcolor="#4CBCDF">
								<td>Fulfillment Id</td>
								<td>Group Id</td>
								<td>Entry Date</td>
								<td>Entity Id</td>
								<td>Entry Type</td>
								<td>Entity Id</td>
								<td>Trans Amt</td>
								<td>Reconciled Ind</td>
								<td>PTD Reconciled</td>
								<td>SoId</td>
								<td>Bus Trans Id</td>
								<td>VL Balance</td>
								
							</tr>
							<s:iterator value="valueLinkEntryVOs" status="counter">
     							<s:if test="#counter.odd">
	    		             		<tr>
								</s:if>
					            <s:else>
					                 <tr bgcolor="#ededed">
					        	</s:else>
					        		<td width="1">
						        		<c:choose>
							        		<c:when test="${fullfillmentEntryId != null}">${fullfillmentEntryId}</c:when> 
							        		<c:otherwise>&nbsp;</c:otherwise>
						        		</c:choose>
					        		</td>
					        		<td width="1">
						        		<c:choose>
							        		<c:when test="${fullfillmentGroupId != null}">${fullfillmentGroupId}</c:when> 
							        		<c:otherwise>&nbsp;</c:otherwise>
						        		</c:choose>
					        		</td>
					        		<td width="1">
						        		<c:choose>
						        			<c:when test="${entryDate != null}">${entryDate}</c:when> 
						        			<c:otherwise>&nbsp;</c:otherwise>
						        		</c:choose>
					        		</td>
					        		<td width="1">
						        		<c:choose>
						        			<c:when test="${ledgerEntityId != null}">${ledgerEntityId}</c:when> 
						        			<c:otherwise>&nbsp;</c:otherwise>
						        		</c:choose>
					        		</td>
					        		<td width="1">
						        		<c:choose>
						        			<c:when test="${entryTypeId != null}">${entryTypeId}</c:when> 
						        			<c:otherwise>&nbsp;</c:otherwise>
						        		</c:choose>
					        		</td>
					        		<td width="1">
						        		<c:choose>
						        			<c:when test="${entityTypeId != null}">${entityTypeId}</c:when> 
						        			<c:otherwise>&nbsp;</c:otherwise>
						        		</c:choose>
					        		</td>
					        		<td width="1">
						        		<c:choose>
						        			<c:when test="${transAmount != null}">${transAmount}</c:when> 
						        			<c:otherwise>&nbsp;</c:otherwise>
						        		</c:choose>
					        		</td>
									<td width="1">
										<c:choose>
											<c:when test="${reconsiledInd != null}">${reconsiledInd}</c:when> 
											<c:otherwise>null</c:otherwise>
										</c:choose>
									</td>
					        		<td width="1">
						        		<c:choose>
						        			<c:when test="${ptdReconsiledInd != null}">${ptdReconsiledInd}</c:when> 
						        			<c:otherwise>null</c:otherwise>
						        		</c:choose>
					        		</td>
									<td width="1">
										<c:choose>
											<c:when test="${soId != null}"><a href="soDetailsController.action?soId=${soId}">${soId}</a></c:when> 
											<c:otherwise>&nbsp;</c:otherwise>
										</c:choose>
									</td>
					        		<td width="1">
						        		<c:choose>
						        			<c:when test="${busTransId != null}">${busTransId}</c:when> 
						        			<c:otherwise>&nbsp;</c:otherwise>
						        		</c:choose>
					        		</td>
					        		<td width="1">
						        		<c:choose>
						        			<c:when test="${vlBalance != null}">${vlBalance}</c:when> 
						        			<c:otherwise>&nbsp;</c:otherwise>
						        		</c:choose>
					        		</td>
					        		
								</tr>
							</s:iterator>
						</table>
					</div>	
					</c:if>
							
						</div>
					</s:form>

						<s:form
							action="fullfillmentAdminAction_adjust"
							id="fullfillmentAdminAction_adjust"
							name="fullfillmentAdminAction_adjust"
							method="post" theme="simple">
						<div class="grayModuleHdr">Resend Fulfillment Record(s)(space delimited) by Id</div>
						<div class="grayModuleContent mainWellContent">
							<table cellpadding="0" cellspacing="0" width="810px">
								
								<c:if test="${adjustFulfillmentMessage != ''}">
									<tr>
										<td width="800"><b><font color="${colorVar}">${dto.adjustFulfillmentMessage}</font></b></td>
									</tr>
									<tr><td>&nbsp;</td></tr>
								</c:if>
								
								<tr>
									<td width="800">
										<label> Enter one or more Fulfillment Entry Id(s) to be adjusted.  If multiple entries, please leave a space in between them. </label>
									</td>
									<td width></td>
								</tr>
								<tr><td>&nbsp;</td></tr>
								<tr>
									<td width="800">
										<label>Fulfillment Entry Id(s)</label>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<label>Comments</label>
									</td>
								</tr>
								<tr>
									<td width="800" align="left">
										<s:textarea id="fulfillmentEntryId" name="fulfillmentEntryId" 
											cssStyle="width: 200px;" cssClass="shadowBox grayText"
											value="%{dto.fulfillmentEntryId}"
											theme="simple"/>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<s:textarea id="comments" name="adjustComments"
											cssStyle="width: 200px;" cssClass="shadowBox grayText"
											value="%{dto.adjustComments}"
											theme="simple"/>
									</td>
								</tr>
								<tr><td>&nbsp;<td></tr>
								<tr>
								  <td>								
									<div>
								       <input type="button"
										onclick="javascript:confirmSendTransaction('adjust')"
										theme="simple" value="Submit Adjustment" />
									</div>
									</td>
								</tr>
							</table>
						</div>
					</s:form>

					<s:form
							action="fullfillmentAdminAction_create"
							id="fullfillmentAdminAction_create"
							name="fullfillmentAdminAction_create"
							method="post" theme="simple">
						<div class="grayModuleHdr">Submit a Fulfillment Record</div>
						<div class="grayModuleContent mainWellContent">
							<table cellpadding="0" cellspacing="0" width="810px">
								
								<c:if test="${createFulfillmentMessage != ''}">
									<tr>
										<td width="800"><b><font color="${colorVar}">${dto.createFulfillmentMessage}</font></b></td>
									</tr>
									<tr><td>&nbsp;</td></tr>
								</c:if>
								
								<tr>
									<td width="800">
											<label>Enter a new fulfillment record here that is a copy of another type of entry with a different amount.</label>
									</td>
								</tr>
								<tr><td>&nbsp;<td></tr>
								<tr>
									<td width="800">
										<label>Fulfillment Entry Id to copy</label>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<label>New amount</label>
									</td>
								</tr>
								<tr>
									<td width="800">
										<s:textfield id="fulfillmentEntryId" name="fulfillmentEntryId"
												cssStyle="width: 200px;" cssClass="shadowBox grayText"
												value="%{dto.fulfillmentEntryId}"
												theme="simple"/>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<s:textfield id="amount" name="amount"
												cssStyle="width: 200px;" cssClass="shadowBox grayText"
												value="%{dto.amount}"
												theme="simple"/>
									</td>										
								</tr>
								<tr><td>&nbsp;<td></tr>
								<tr>
									<td width="800">
										<label>Comments</label>
									</td>
								</tr>
								<tr>
									<td width="800">
										<s:textarea id="comments" name="copyComments"
											cssStyle="width: 200px;" cssClass="shadowBox grayText"
											value="%{dto.copyComments}"
											theme="simple"/>
									</td>
								</tr>
								<tr><td>&nbsp;<td></tr>
								<tr>
								  <td>								
									<div>
								       <input type="button"
										onclick = "javascript:confirmSendTransaction('create')"
										theme="simple" value="Submit New FulFillment" />
									</div>
									</td>
								</tr>
							</table>
						</div>
					</s:form>
					<s:form
							action="fullfillmentAdminAction_resend"
							id="fullfillmentAdminAction_resend"
							name="fullfillmentAdminAction_resend"
							method="post" theme="simple">
						<div class="grayModuleHdr">Resend a group of Fulfillment Records</div>
						<div class="grayModuleContent mainWellContent">
							<table cellpadding="0" cellspacing="0" width="810px">
								<c:if test="${groupResendMessage != ''}">
									<tr>
										<td width="800"><b><font color="${colorVar}">${dto.groupResendMessage}</font></b></td>
									</tr>
									<tr><td>&nbsp;</td></tr>
								</c:if>
								<tr>
									<td width="800">
											<label>Enter the Fulfillment Group Id that has to be resent:</label>
									</td>
								</tr>
								<tr><td>&nbsp;<td></tr>
								<tr>
									<td width="800">
											<label>Fulfillment Group Id to resend</label>
									</td>
								</tr>
								<tr>
									<td valign="top">
											<s:textfield id="fulfillmentEntryId" name="fulfillmentEntryId"
												cssStyle="width: 200px;" cssClass="shadowBox grayText"
												value="%{dto.fulfillmentEntryId}"
												theme="simple" />
									</td>
								</tr>
								<tr><td>&nbsp;<td></tr>
								<tr>
									<td width="800">
											<label>Comments</label>
									</td>
								</tr>
								<tr>
									<td>
										<s:textarea id="comments" name="resendComments"
										cssStyle="width: 200px;" cssClass="shadowBox grayText"
										value="%{dto.resendComments}"
										theme="simple"/>
									</td>
								</tr>
								<tr><td>&nbsp;<td></tr>
								<tr>
								  <td>								
									<div>
								       <input type="button"
											onclick="javascript:confirmSendTransaction('resend')"
											theme="simple" value="Resend Group" />
									</div>
									</td>
								</tr>
							</table>
						</div>
					</s:form>
				<s:form	action="fullfillmentAdminAction_runGLProcess"
							id="fullfillmentAdminAction_runGLProcess"
							name="fullfillmentAdminAction_runGLProcess"
							method="post" theme="simple">
							
						<div class="grayModuleHdr">Generate GL File for specific dates</div>
						<div class="grayModuleContent mainWellContent">
							<div id="errorDivGL"
										style="color:red; display:none; margin-left:5px">
							</div>
							<table cellpadding="0" cellspacing="0" width="810px">
								<c:if test="${glRunMessage != ''}">
									<tr>
										<td id="errorGL" width="800"><b><font color="${colorVar}">${dto.glRunMessage}</font></b></td>
									</tr>
								</c:if>
								<tr><td>&nbsp;</td></tr>
								<tr>
									<td width="800">
											<label>Please enter the date for which GL file needs to be generated</label>
									<input type="text" dojoType="dijit.form.DateTextBox"
        							class="shadowBox" id="glDate"
									name="glDate" value="%{dto.glDate}"
	 								theme="simple" cssStyle="width: 75px;" cssClass="shadowBox grayText" constraints="{max: '${todaysDate}'}"/>
									</td>
								</tr>
								<tr><td>&nbsp;<td></tr>
								
								<tr>
								  <td>								
									<div>
								       <input type="button"
										onclick="javascript:confirmRunGL()"
										theme="simple" value="Run GL" />
									</div>
									</td>
								</tr>
							</table>
						</div>	
				</s:form>
				<!--  Nacha process block starts -->
				<s:form	action="fullfillmentAdminAction_runNachaProcess"
							id="fullfillmentAdminAction_runNachaProcess"
							name="fullfillmentAdminAction_runNachaProcess"
							method="post" theme="simple">
						<div class="grayModuleHdr">Generate Nacha File for specific dates</div>
						<div class="grayModuleContent mainWellContent">
							<div id="errorDivNacha"
										style="color:red; display:none; margin-left:5px">
							</div>
							<table cellpadding="0" cellspacing="0" width="810px">
								<c:if test="${nachaRunMessage != ''}">
									<tr>
										<td id="errorNacha" width="800"><b><font color="${colorVar}">${dto.nachaRunMessage}</font></b></td>
									</tr>
								</c:if>
								<tr><td>&nbsp;</td></tr>
								<tr>
									<td width="800">
											<label>Please enter the date for which Nacha file needs to be generated</label>
									<input type="text" dojoType="dijit.form.DateTextBox"
        							class="shadowBox" id="nachaDate"
									name="nachaDate" value="%{dto.nachaDate}"
	 								theme="simple" cssStyle="width: 75px;" cssClass="shadowBox grayText" constraints="{max: '${todaysDate}'}"/>
									</td>
								</tr>
								<tr><td>&nbsp;<td></tr>
								
								<tr>
								  <td>								
									<div>
									       <input type="button"
											onclick="javascript:confirmRunNacha()"
											theme="simple" value="Run Nacha" />
									</div>
									</td>
								</tr>
							</table>
						</div>	
				</s:form>
				<!--  Nacha process block ends -->
				
				<!--  Balance Inquiry block starts -->
				<form  action="<s:url action="fullfillmentAdminAction_sendBalanceInquiry" />"
							id="fullfillmentAdminAction_sendBalanceInquiry"
							name="fullfillmentAdminAction_sendBalanceInquiry"
							method="post" theme="simple">
						<div class="grayModuleHdr">Run Balance Inquiry for a Company</div>
						<div class="grayModuleContent mainWellContent">
							<div id="errorDivBalance"
										style="color:red; display:none; margin-left:5px">
							</div>
							<table cellpadding="0" cellspacing="0" width="810px">
								<c:if test="${balanceInquiryRunMessage != null}">
									<tr>
										<td id="errorBalance" width="800"><b><font color="${colorVar}">${dto.balanceInquiryRunMessage}</font></b></td>
									</tr>
								</c:if>
								<tr><td>&nbsp;</td></tr>
								<tr>
									<td width="800">
										<label>Please enter the Company/Entity Id for which balance inquiry has to be sent</label>
										<s:textfield id="vendorId" name="vendorId"
												cssStyle="width: 200px;" cssClass="shadowBox grayText"
												value="%{dto.vendorId}"
												theme="simple"/>
									</td>
								</tr>
								<tr><td>&nbsp;<td></tr>
								
								<tr>
								  <td>								
									<div>
									       <input type="button"
											onclick="javascript:confirmSendBalanceInquiry()"
											theme="simple" value="Send Balance Inquiry" />
									</div>
									</td>
								</tr>
							</table>
						</div>	
				</form>
				<!--  Balance Inquiry block ends -->
				<div class="grayModuleHdr">Cancel Pending Wallet Transaction</div>
					<div class="grayModuleContent mainWellContent">
						<div class="grayTableContainer" style="width: 100%; height: 130px;WORD-BREAK:BREAK-ALL;">
							<table cellpadding="0" cellspacing="0" style="width: 100%; height: 20px;">
								<tr bgcolor="#4CBCDF">
									<td>&nbsp;</td>
									<td style="padding-left: 20px;">Service Order Id</td>
									<td style="padding-left: 20px;">JMS Message</td>
									<td style="padding-left: 20px;">Status</td>
								</tr>
								<s:iterator value="dto.pendingServiceOrders" status="counter">
	     							<s:if test="#counter.odd">
		    		             		<tr>
									</s:if>
						            <s:else>
			                 			<tr bgcolor="#ededed">
						        	</s:else>
						        		<td>
						        			<s:form action="fullfillmentAdminAction_cancelPendingWalletTransaction"
						        				id="fullfillmentAdminAction_cancelPendingWalletTransaction"
						        				name="fullfillmentAdminAction_cancelPendingWalletTransaction"
						        				method="post"
						        				onsubmit="return confirm('Before proceeding, you must confirm that message %{#attr['jmsId']} has been removed from the queue.');"
						        				>
						        				<input type="hidden" name="soId" value="${soId}" />
						        				<s:submit value="cancel" />
						        			</s:form>
						        		</td>	
						        		<td style="white-space: nowrap; padding-left: 20px;">
							        		<c:choose>
							        			<c:when test="${soId != null}">${soId}</c:when> 
							        			<c:otherwise>&nbsp;</c:otherwise>
							        		</c:choose>
						        		</td>
						        		<td style="white-space: nowrap; padding-left: 20px;">
							        		<c:choose>
							        			<c:when test="${serviceOrderProcess.jmsMessageId != null}">${serviceOrderProcess.jmsMessageId}</c:when> 
							        			<c:otherwise>&nbsp;</c:otherwise>
							        		</c:choose>
						        		</td>
						        		<td style="white-space: nowrap; padding-left: 20px;" width="100%">Pending Wallet Confirmation</td>				        		
									</tr>
								</s:iterator>
							</table>
						</div>
					</div>
				</div>
				<!-- Buyer - provider reports -->
				<div style="display: none;">
				<jsp:include page="/jsp/finance_manager/body/tabs/report_input_form.jsp" />
				</div>
				<jsp:include page="/jsp/admin/buyer_prov_reports.jsp" />
				<!-- Admin Payment and misc reports -->
				<jsp:include page="/jsp/admin/admin_misc_reports.jsp" />
				
			</div>
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
		</div>
				
			</div>
			<!-- END CENTER   -->
			

		<script type="text/javascript">
			var $jQ = jQuery.noConflict();
			$jQ("#errorDivGL").html("");
			$jQ("#errorDivGL").css("display","none");
			$jQ("#errorDivNacha").html("");
			$jQ("#errorDivNacha").css("display","none");
			$jQ("#errorDivBalance").html("");
			$jQ("#errorDivBalance").css("display","none");
			function confirmSendTransaction(actionName)
			{
				if (window.confirm("Has it been 48 hours since this entry has been reconciled?  You are about to resend the transactions to VL.  Click OK to continue and Cancel to stop resend.") )
				{
					if(actionName == 'adjust')
						document.fullfillmentAdminAction_adjust.submit();
					else if(actionName == 'create')
						document.fullfillmentAdminAction_create.submit();
					else if(actionName == 'resend')
						document.fullfillmentAdminAction_resend.submit();
					else
						return false;
		        	return true;
		        }
		        else
					return false;
			}
			
			function confirmRunGL()
			{	
				$jQ("#errorGL").hide();
				$jQ("#errorNacha").hide();
				$jQ("#errorBalance").hide();
				$jQ("#errorDivGL").html("");
				$jQ("#errorDivGL").css("display","none");
				$jQ("#errorDivNacha").html("");
				$jQ("#errorDivNacha").css("display","none");
				$jQ("#errorDivBalance").html("");
				$jQ("#errorDivBalance").css("display","none");
				var date = document.getElementById('glDate').value;
				if(date && isValidDate(date)){
					if (window.confirm("You are about to generate a GL file for the date "+ date))
					{
						document.fullfillmentAdminAction_runGLProcess.submit();
		        	}
				}
				else{
					$jQ("#errorDivGL").html("Please enter a valid date.");
					$jQ("#errorDivGL").css("display","block");
				}
			}
			
			function confirmRunNacha()
			{	
				$jQ("#errorGL").hide();
				$jQ("#errorNacha").hide();
				$jQ("#errorBalance").hide();
				$jQ("#errorDivGL").html("");
				$jQ("#errorDivGL").css("display","none");
				$jQ("#errorDivNacha").html("");
				$jQ("#errorDivNacha").css("display","none");
				$jQ("#errorDivBalance").html("");
				$jQ("#errorDivBalance").css("display","none");
				var date = document.getElementById('nachaDate').value;
				if(date && isValidDate(date)){
					if (window.confirm("You are about to generate a Nacha file for the date "+ date))
					{
						document.fullfillmentAdminAction_runNachaProcess.submit();
		    		}
				}
				else{
					$jQ("#errorDivNacha").html("Please enter a valid date.");
					$jQ("#errorDivNacha").css("display","block");
				}
		    }

			function confirmSendBalanceInquiry()
			{	
				$jQ("#errorGL").hide();
				$jQ("#errorNacha").hide();
				$jQ("#errorBalance").hide();
				$jQ("#errorDivGL").html("");
				$jQ("#errorDivGL").css("display","none");
				$jQ("#errorDivNacha").html("");
				$jQ("#errorDivNacha").css("display","none");
				$jQ("#errorDivBalance").html("");
				$jQ("#errorDivBalance").css("display","none");
				if(isNumber(document.fullfillmentAdminAction_sendBalanceInquiry.vendorId.value)){
					if (window.confirm("You are about to send a Balance Inquiry for Entity Id "+ document.fullfillmentAdminAction_sendBalanceInquiry.vendorId.value))
					{
						document.fullfillmentAdminAction_sendBalanceInquiry.submit();
					}
				}
				else{
					$jQ("#errorDivBalance").html("Please enter a valid Company/Entity Id.");
					$jQ("#errorDivBalance").css("display","block");
				}
			}
			
			function isNumber(n) {
				  return !isNaN(parseFloat(n)) && isFinite(n);
			}
			// Validates that the input string is a valid date formatted as "mm/dd/yy"
			function isValidDate(dateString)
			{	
				var today = new Date();
				
			    // First check for the pattern
			    if(!/^\d{1,2}\/\d{1,2}\/\d{2}$/.test(dateString))
			        return false;

			    // Parse the date parts to integers
			    var parts = dateString.split("/");
			    var day = parseInt(parts[1], 10);
			    var month = parseInt(parts[0], 10);
			    var year = parseInt(parts[2], 10);
			    year = "20" + year;
			    dateString = month + "/" + day + "/" + year;
			    var myDate = new Date(dateString);

			    // Check the ranges of month
			    if(month == 0 || month > 12)
			        return false;

			    var monthLength = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];

			    // Adjust for leap years
			    if(year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))
			        monthLength[1] = 29;

			    // Check the range of the day
			    if(day < 0 && day > monthLength[month - 1]){
			    	return false;
			    }
			    if (myDate > today){
			    	return false;
			    }
			    return true;
			};
		</script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/toolbox.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/vars.js"></script>
		<script language="JavaScript"
			src="${staticContextPath}/javascript/tooltip.js" type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/fmReports.js"></script>
</body>
</html>
