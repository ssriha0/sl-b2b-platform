<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<script type="text/javascript">

	function attach_edit_to_meetgreet_select(spnId, pfId)
	{
		$(document).ready(function() {
				
				var approvalAreaId = '#'+spnId+'_'+pfId+'_approvebuttons';
				if($(approvalAreaId).is(":visible"))
				{
					var selectButtonId = '#'+spnId+'_'+pfId+'_selectbutton_meetgreet';
					$(selectButtonId).show();
				}
		});
	}
	
	$(document).ready(function()
	{
		//$('#comments2').maxlength({ maxCharacters: 150 });
		// Show/Hide 'Send Notification' button	
		if(${meetApproved} && ${allDocsApproved})
		{
			$('#showSendNotification_${networkId}_${providerFirmId}').hide();
		}
		else
		{
			$('#showSendNotification_${networkId}_${providerFirmId}').show();
		}
		var date = '.${networkId}_${providerFirmId}_date';
		jQuery(date).datepicker({dateFormat:'yy-mm-dd', maxDate: '+1d'});
			
	});
	
			// Set the gray arrow/pointer-thingie to point to the row selected
			function meetSelectionButtonClick(obj,spnId, pfId){
			//$('.meetSelectionButton').click(function(){							
				$('tr[id^=meetRow_]').removeClass('selected');
				$(obj).parent('td').parent('tr').addClass('selected');
				
				$('#selectedMeet').attr('value', $(obj).attr('id'));
				
				//alert('meetId:' + $('#selectedMeet').val());
				
				// Cannot select an action until a MeetNGreet has been selected
				var selected_action = '.'+spnId+'_'+pfId+'_';
				$(selected_action+'selectedMeetAction').removeAttr('disabled');
				$(selected_action+'name').removeAttr('disabled');
				$(selected_action+'date').removeAttr('disabled');
				$(selected_action+'comments2').removeAttr('disabled');
				$(selected_action+'button_submit_meet').removeAttr('disabled');
			//});
			}
			
		function buttonSubmitMeetClick(spnId, pfId){
			//$('#button_submit_meet').click(function() {
		
			var selected_action = '.'+spnId+'_'+pfId+'_';
			var comments = $(selected_action+'comments2').val();
			var name = $(selected_action+'name').val();
			var date = $(selected_action+'date').val();
			var action = $(selected_action+'selectedMeetAction').val();
			var providerFirmId = pfId;
			var networkId = spnId;

			
			var errorMsg = '';
			
			if(action == '-1')
			{
				errorMsg = 'Please select an action<br>';
			}			
			if(name == null || name == '')
			{
				errorMsg = errorMsg + 'Please enter the name of the person who met with the provider.<br>';
			}			
			if(date == null || date == '')
			{
				errorMsg = errorMsg + 'Please enter the date of the meeting.<br>';
			}
			if(comments == null || comments == '')
			{
				errorMsg = errorMsg + 'Please enter a comment<br>';
			}
			
			if(errorMsg != '')			
			{
				$(selected_action+'mg_error').css('display', 'block');
				$(selected_action+'mg_error').html(errorMsg);
			}
			else
			{						
				$(selected_action+'mg_error').css('display', 'none');
				$(selected_action+'meetAjaxRefresh').load("spnAuditorMeetAndGreetTab_buttonSubmitAddAction.action", {'providerFirmId':providerFirmId, 'networkId':networkId, 'comments':comments, 'name': name, 'date': date,  'action': action});
				
			}
		
		//});
		}
		
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
		
			
</script>

<div id="meetAjaxRefresh" name="meetAjaxRefresh" class="${networkId}_${providerFirmId}_meetAjaxRefresh">

	<input type="hidden" id="networkId" name="networkId"
		value="<s:property value='networkId' />" />					
	<input type="hidden" id="providerFirmId"
		value="<s:property value='providerFirmId' />" />
	<input type="hidden" id="selectedMeet" value="-1" />

	<div class="clearfix">
		<div class="tableInner">
			<table border="0" cellpadding="0" cellspacing="0"
				style="margin-bottom: 0px;">
				<thead>
					<tr>
						<th class="tc pending" style="width: 60px;">
							Status
						</th>
						<th class="tl pending">
							<abbr title="Select Provider Network">SPN</abbr>
						</th>
						<th class="tl approved">
							Greeting Information
						</th>
						<th>
							&nbsp;
						</th>
					</tr>
				</thead>
				<tbody>

					<!-- begin loop -->
					<jsp:include page="meet_greet_row.jsp" flush="true" />
					<!-- end loop -->
					
					
				</tbody>
			</table>
		</div>

		<div class="content" style="margin-left: 450px;">
			<div id="mg_error" class="error ${networkId}_${providerFirmId}_mg_error" style="display: none">
				<s:property value="errorMessage" />
			</div>
		
			<div>An asterix (<span class="req">*</span>) indicates a required field.</div>
			<div>
				<table cellpadding="0" cellspacing="0" style="padding: 0px; width: 200px;">
					<tr>
						<td style="padding: 2px; vertical-align: top;">Action <span class="req">*</span>:</td>
						<td style="padding: 2px; vertical-align: top;">
							<s:select id="selectedMeetAction" name="selectedMeetAction"
								list="actionList" theme="simple" listKey="id"
								listValue="description" headerKey="-1" headerValue="-- Select --"
								disabled="true" cssStyle="margin: 0px;" cssClass="%{networkId}_%{providerFirmId}_selectedMeetAction"/>
						</td>
					</tr>
					<tr>
						<td style="padding: 2px; vertical-align: top;">Name <span class="req">*</span>:</td>
						<td style="padding: 2px; vertical-align: top;">
							<div>
								<s:textfield id="name" name="name" cssClass="text %{networkId}_%{providerFirmId}_name" 
									cssStyle="width: 140px; margin: 0px;" theme="simple" disabled="true" />
							</div>
							<div style="font-size: 9px;">Person who met with this firm.</div>
						</td>
					</tr>
					<tr>
						<td style="padding: 2px; vertical-align: top;">Date <span class="req">*</span>:</td>
						<td style="padding: 2px; vertical-align: top;">
							<div>
								<s:textfield id="%{networkId}_%{providerFirmId}_date" name="date" cssClass="text date %{networkId}_%{providerFirmId}_date"
									cssStyle="width: 100px; margin: 0px;" theme="simple" disabled="true" />
							</div>
							<div style="font-size: 9px;">Date of meeting or approving.</div>
						</td>
					</tr>
					<tr>
						<td style="padding: 2px;" colspan="2">
							<div>Comments <span class="req">*</span>:</div>
							<div>
								<s:textarea id="comments2" name="comments2" cssClass="text %{networkId}_%{providerFirmId}_comments2"
									cssStyle="width: 195px; margin: 0px;" theme="simple" disabled="true" 
									onkeyup="limitTextarea(this,150,'comments2Char');" 
									onkeydown="limitTextarea(this,150,'comments2Char');"/>
								<span id="comments2Char">150 characters left </span>
							</div>
						</td>
					</tr>
				</table>
				</div>
								
				<input type="submit" value="submit" id="button_submit_meet"
					name="button_submit_meet" class="action button right ${networkId}_${providerFirmId}_button_submit_meet"
					disabled="true" onclick="buttonSubmitMeetClick('${networkId}','${providerFirmId}')">
		</div>

	</div>
</div>