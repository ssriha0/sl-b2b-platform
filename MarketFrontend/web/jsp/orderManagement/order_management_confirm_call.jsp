<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<script type="text/javascript">
		function saveConfirmCall(){
			$("#confirmErrorDiv").css("display","none");
			var resourceAssigned = $("#resourceAssigned").val();
			var todayInd = $("#todayInd").val();
			var notAvailableReason = $("#notAvailableReason").val();
			var errormsg = "";
			var validate = true;
			var type = $('input:radio[name=confirmCustAvailability]:checked').val();
	        if(type =='2'){
			if(notAvailableReason=="-1"){
				errormsg = errormsg +"Please select a reason for not completing the call.<br/>";
				$("#confirmErrorDiv").html(errormsg);
				validate = false;
			}
	        }
			if(resourceAssigned=="false" && todayInd=="true"){
				errormsg = errormsg + "Appointment is Today. You must Assign a provider.";
				$("#confirmErrorDiv").html(errormsg);
				validate = false;
			}
			if(!validate){
				$("#confirmErrorDiv").css("display","block");

			}
			return false;
			
	}
		
	</script>

	</head>

	<body class="tundra acquity">
		<c:set var="isToday" value="false"></c:set>
		<c:set var="dateType" value="range"></c:set>
		<c:set var="resourceAssigned" value="false"></c:set>
		<c:set var="resourceName" value="John Smith"></c:set>
		<div class="modalHead">
			<div style="float: left; font-size: 9pt;">
				Confirm Appointment Window - Call the End User to confirm the
				appointment window
				<a href="#"><img src="${staticContextPath }/images/widgets/tabClose.png"
					onclick="closeConfirmCall();" /></a>
			</div>

		</div>
		<input type="hidden" id="resourceAssigned" value="${resourceAssigned}" />
		<input type="hidden" id="todayInd" value="${isToday}" />
		<div class="modalBody" style="font-weight: normal;">

			<table style="width: 600px; padding-top: 5px">
				<tr>
					<td style="width: 50%; padding-left: 8px; padding-right: 7px;border-bottom: 0px;">

						<div class="insideBoxHdr"
							style="height: 9px; vertical-align: middle;">
							End User Info
						</div>
						<div
							style="background-color: #FFFF7B; border:1px solid black; padding-left: 8px;">
							John Smith
							<br/>
							<br/>
								Primary phone: 630 111 1111 
							<br/>
								Secondary Phone: 630 777 7777 
							<br/>
							<br/>
								1234 Beverly Lane 
							<br/>
								Hoffman Estates 
							<br/>
								Il 60103 
							<br/>
							<br/>
								Appointment: 03/21/12 
							<br/>
							<span style="padding-left: 80px;">08:15 AM (CST)</span>

						</div>
					</td>
					<td
						style="width: 50%; padding-left: 8px; padding-right: 7px; vertical-align: top; border-bottom: 0px;">
						<div class="insideBoxHdr"
							style="height: 9px; vertical-align: middle; padding-left: 8px;">
							Call History
						</div>
						<div style="background-color: #FFFF7B; padding-left: 8px;border:1px solid black;">
							* Monday 1/25 ( Could not reach out to customer)
							<br/>
								* Tuesday 1/26 ( left Voice message) 
							<br/>
						</div>
					</td>
				</tr>
			</table>
			<div style="width: 600px; padding-left: 20px;" id="confirmCust">
				<div style="padding: 10px 2px 0px 3px;">
					<input type="radio" id="1" value="1" name="confirmCustAvailability"/>
						Customer available 
				</div>
				<div style="padding: 10px 2px 0px 3px;">
					<input type="radio" id="2" value="2"
						name="confirmCustAvailability"/>
						Customer not available 
				</div>
			</div>
			<table id="confirmAvailableInfo"
				style="width: 600px; display: none; padding-top: 5px">
				<tr>
					<td style="width: 50%; padding-left: 8px; padding-right: 7px;border-bottom: 0px;">

						<div class="insideBoxHdr"
							style="height: 9px; vertical-align: middle; padding-left: 8px;">
							Product Details
						</div>
						<div
							style="background-color: #FFFF7B; border:1px solid black; padding-left: 8px;line-height: 20px;">
							Manufacturer : GE
							<br/>
								Model Number : 12345 
							<br/>
								Availability Date : May 12, 2012 
							<br/>
								Merchandise Code: ABCD 
							<br/>
								Hoffman Estates 
							<br/>
								Il 60103 
							<br/>
								Brand : GE Profile 
							<br/>
								Product Location: 1121 Columbia drive, Schaumburg, Il 60103 
							<br/>
						</div>
					</td>
					<td
						style="width: 50%; padding-left: 8px; padding-right: 7px; vertical-align: top;border-bottom: 0px;">
						<div class="insideBoxHdr"
							style="height: 9px; vertical-align: middle; padding-left: 8px;">
							Scope
						</div>
						<div style="background-color: #FFFF7B; padding-left: 8px;border:1px solid black;">
							<b>Task 1 </b>: Deliver Merchandise ( Dishwasher -> Delivery)
							<br/>
							<b>Task 2 </b>: 22100 - TV/DIGITAL (Dishwasher -> Installation)
							<br/>
							<br/>
						</div>
					</td>
				</tr>
			</table>

			<div style="display: none;padding-left: 8px;" id="availableDivConfirm">
				<table>
					<tr>
						<td style="border-bottom: 0px;">
							<b>Special Instructions:</b>
						</td>
						<td align="right" style="border-bottom: 0px;">
							<a onclick="makeEditable('specialInstructionsConfirm')" href="#">Edit</a>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="border-bottom: 0px;">
							<textarea id="specialInstructionsConfirm" style="width: 560px"
								readonly="readonly" rows="" cols="">Here</textarea>
						</td>
					</tr>
					<tr>
						<td style="border-bottom: 0px;">
							<b>Service Location Notes:</b>
						</td>
						<td align="right" style="border-bottom: 0px;">
							<a onclick="makeEditable('soLocNotes')" href="#">Edit</a>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="border-bottom: 0px;">
							<textarea id="soLocNotes" style="width: 560px"
								readonly="readonly" rows="" cols="">What</textarea>
						</td>
						
					</tr>
					<tr>
					<td>
					<hr width="115%"></hr>
					</td>
					</tr>
				</table>
			</div>

		</div>
		<div id="notAvailableReasonDivConfirm"
			style="width: 600px; padding-left: 20px;font-weight: normal;display: none;">
			<hr style="width: 560px;" class="lineBreak"/>
			<div style="margin-top: 10px;margin-bottom: 10px;">
			Select the reason for not completing the call &nbsp;&nbsp;
			<select id="notAvailableReason" name=""
				style="width: 300px; height: 20px;">
				<option value="-1">
					Please Select
				</option>
				<option>
					Customer Not Available
				</option>
				<option>
					Customer did not pickup
				</option>
			</select>
			</div>
			<hr style="width: 560px;" class="lineBreak"/>
		</div>
		<br/>
		<div id="confirmErrorDiv" class="preCallErrorBox" style="display: none;">
			Error Message: Appointment is Today. You must Assign a provider.
		</div>
		<div style="width: 600px; padding-top: 5px; padding-bottom: 12px;font-weight: normal; padding-left: 15px;padding-right: 15px;display: none;" id="apptDetailsConfirm">
			<table width="100%">
				<tr>
					<td width="60%">
						<table>
							<tr>
								<td style="border-bottom: 0px;">
									Currently assigned to :
									<span style="color: red"><c:if
											test="${resourceAssigned == 'true'}">${resourceName}&nbsp;</c:if> <c:if
											test="${resourceAssigned != 'true'}">unassigned&nbsp;</c:if>
									</span>
									<a href="#" 
										style="text-decoration: underline;"><c:if
											test="${resourceAssigned == 'true'}"><span id="reassignProviderLink" onclick="loadAssignProvider(event,this.id,null);">Reassign Provider</span></c:if>
										<c:if test="${resourceAssigned != 'true'}"><span id="assignProviderLink" onclick="loadAssignProvider(event,this.id,null);">Assign Provider</span></c:if>
									</a>
								</td>
							</tr>
							<tr>
								<td style="border-bottom: 0px;">
									<span>Appointment Time : 8:15 AM </span>
									<a href="#" id="updateTimes" style="text-decoration: underline;" onclick="loadUpdateTime(null,0);">&nbsp;Update Time</a>
								</td>
							</tr>
							<tr id="selectConfirmCustAction"
								style="display: none; padding-left: 20px; padding-top: 8px; padding-bottom: 8px;">
								<td style="border-bottom: 0px;">
									<span style="padding: 10px 2px 0px 3px;"> <input
											type="radio" id="1" value="1" name="confirmCustAction"/>
											Customer has confirmed the current appointment window. 
									</span>
									<br />
									<span style="padding: 10px 2px 0px 3px;"> <input
											type="radio" id="2" value="2" checked="checked"
											name="confirmCustAction"/>
											Set the new appointment window. 
									</span>
									<br />
									<span style="padding: 10px 2px 0px 3px;"> <input
											type="radio" id="1" value="3" name="confirmCustAction"/>
											Save this info and reschedule the appointment. 
									</span>
									<br />
								</td>


							</tr>
							<tr id="confirmSelectTime" style="display: none;">
								<td style="border-bottom: 0px;">
									Select Appointment Time*
									<s:select cssStyle="width: 90px;" id="appointmentStartTime"
						name="appointmentStartTime" list="#application['time_intervals']"
							listKey="descr" listValue="descr" multiple="false" size="1"
								theme="simple" />	
										to 
									<s:select cssStyle="width: 90px;" id="appointmentEndTime"
						name="appointmentStartTime" list="#application['time_intervals']"
							listKey="descr" listValue="descr" multiple="false" size="1"
								theme="simple" />	
								</td>
							</tr>
							<tr id="confirmETA" style="display: none;">
								<c:if test="${dateType == 'range'}">
									<td style="border-bottom: 0px;">
									<span>
										Estimated Time of Arrival (ETA):
				<s:select cssStyle="width: 90px;" id="eta_drop"
						name="appointmentStartTime" list="#application['time_intervals']"
							listKey="descr" listValue="descr" multiple="false" size="1"
								theme="simple" />										
							</span>
									</td>
								</c:if>
							</tr>
						</table>
					</td>

					<td width="30%" style="border-bottom: 0px;">
						<table>
							<tr>
								<td align="right" style="border-bottom: 0px;">

									<div id="confirmAssignProviderMsg"
										style="background-color: #FFFF7B; width: 150px; word-wrap: break-word; text-align: left;;">
										<div id="informationWindowHdr" class="informationWindowHdr" style="background-color: #CCCCCC;width:150px;height:5px;"></div>
										<p style="padding-left:5px;"><img src="${staticContextPath}/images/order_management/ideaBulb.png" /></p>
										<p style="padding-left:5px;" id="warningMsg">
										</p>
									</div>

								</td>
							</tr>
						</table>
					</td>

				</tr>
			</table>
		</div>
		
		<div style="width: 600px; padding-top: 5px; padding-bottom: 20px;display: none;padding-left: 20px;padding-right: 20px;" id="submitSectionConfirmCall">
			<hr style="width:560px;"/>
			<table style="width: 100%">
				<tr>
					<td style="width: 50%;border-bottom:0px;">
						<a href="#" onclick="closeConfirmCall();" style="color:red">Cancel</a>
					</td>


					<c:if test="${isToday == 'true'}">
						<td style="align: right; border-bottom: 0px; width: 25%">
							<input type="button" value="SAVE & RESCHEDULE"
								id="saveReschedule" class="actionButton"
								onclick="loadReschedule(event);" />
						</td>
					</c:if>
					<td style="width: 1%"></td>
					<td style="border-bottom: 0px; width: 25%">
						<input type="button" value="SAVE" id="savePreCall"
							class="actionButton" style="width: 90px; align: right"
							onclick="saveConfirmCall();" />
					</td>
				</tr>
			</table>
		</div>

	</body>

</html>