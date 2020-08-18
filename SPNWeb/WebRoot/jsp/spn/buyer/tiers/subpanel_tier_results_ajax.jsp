<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />

	<!-- 
	<script type="text/javascript">
					
		jQuery(document).ready(function($) {
				
			$("#spinnerCEG").spinner();
		});
	</script>
	-->

					
<div id="frontEndError" class="error hide"></div>
<c:if test="${atLeastOnePerfLevelAvail}">
	<p style="margin: 20px 0 10px 0;">
		<span class="bold">Assign Groups to a Release&nbsp;<span
			title="A network may have 1 or more Routing Priorities up to 4.  Routing is released to the assigned Provider Groups according to the interval times set for each priority."
			style="border-bottom: 1px dotted">Priority</span> &nbsp;<span
			style="color: #ff0000">*</span>
		</span> (Group levels shown high to low)
	</p>
</c:if>

			<div id="availableperfLevels">
				<s:iterator value="#session['showPerfLevelList']"
					status="perflevelSts" id="perfLevel">

					<div class="diamondWrapper" style="float:left">
				<span title="<s:property value='%{#perfLevel.label}'/>">
							<img style="margin:auto" src="${staticContextPath}/images/spn/priority_<s:property value='%{#perfLevel.value}'/>.png">
				</span>
						<br>
				<input type="checkbox" id="avPfCb" name="avPfCb" value="<s:property value='%{#perfLevel.value}'/>" />
					</div>
				</s:iterator>
			</div>

			<c:if test="${atLeastOnePerfLevelAvail}">
				<div class="diamondWrapper">
					<input type="submit" onclick="return false;" class="button action"
				style="width: 100px; padding: 3px; margin-top: 20px;"
				value="Assign Priority" id="buttonAddTier" name="buttonAddTier" />
				</div>
			</c:if>

<br style="clear: both;" />





<c:if test="${fn:length(tiersToSave) > 0}">

	<p style="margin: 20px 0 10px 0;">
		<span class="bold">Set <span title="Routing Priority is ranked. Routing is releaseed for each level after the previous level interval time has expired" style="border-bottom:1px dotted">Advanced Release Times</span> for each Priority<span style="color:#ff0000">*</span></span>
		<c:choose>
			<c:when test="${TR_MIN_DELAY_TIME == 1}">
				(minimum ${TR_MIN_DELAY_TIME} minute)
			</c:when>
			<c:when test="${TR_MIN_DELAY_TIME > 1}">
				(minimum ${TR_MIN_DELAY_TIME} minutes)
			</c:when>
		</c:choose>
		
	</p>

	<h1>
		<s:property value="" />
	</h1>

	<table style="clear: both; width: 700px;" >
		<tr>
			<td>

				<table id="tierReleaseTable" cellpadding="0" cellspacing="0"
					border="0" style ="width: 530px;">
		<thead>
			<th style ="width: 75px;">
							Priority
			</th>
			<th style ="width: 230px;">
							Group
			</th>
			<th style ="width: 75px;">
				Days
			</th>
			<th style ="width: 75px;">
				Hours
			</th>
			<th style ="width: 75px;">
				Minutes
			</th>
		</thead>
		<tbody>



			<s:iterator value="tiersToSave" status="status">
				<tr>
					<td style ="width: 75px;">
									<s:property value='%{tiersToSave[#status.index].tierId}' />
						<s:hidden name="tiersToSave[%{#status.index}].tierId"
							value="%{tiersToSave[#status.index].tierId}" />
					</td>
					<td style ="width: 230px;">
						<s:iterator value="tierPerformanceLevels" status="perIndex" id="pflevel">

							
							<s:hidden id="pf_id" value="%{#pflevel}"/>
							<s:if test="%{#pflevel == 1}">
								<img title="Gold" src="${staticContextPath}/images/spn/priority_<s:property value='%{#pflevel}'/>.png">								
							</s:if>							
							<s:if test="%{#pflevel == 2}">
								<img title="Silver" src="${staticContextPath}/images/spn/priority_<s:property value='%{#pflevel}'/>.png">								
							</s:if>							
							<s:if test="%{#pflevel == 3}">
								<img title="Bronze" src="${staticContextPath}/images/spn/priority_<s:property value='%{#pflevel}'/>.png">								
							</s:if>							
							<s:if test="%{#pflevel == 4}">
								<img title="Select" src="${staticContextPath}/images/spn/priority_<s:property value='%{#pflevel}'/>.png">								
										</s:if>

							<s:hidden
								name="tiersToSave[%{#status.index}].tierPerformanceLevels[%{#perIndex.index}]"
								value="%{#pflevel}" />
						</s:iterator>
					</td>
					<td style ="width: 75px;">
						<s:select
							id="tiersToSave[%{#status.index}].tierMinute.advancedDays"
							name="tiersToSave[%{#status.index}].tierMinute.advancedDays"
							list="dayOptions" listKey="label" listValue="value"
							theme="simple"
							value="%{tiersToSave[#status.index].tierMinute.advancedDays}" />
					</td>
					<td style ="width: 75px;">
						<s:select
							id="tiersToSave[%{#status.index}].tierMinute.advancedHours"
							name="tiersToSave[%{#status.index}].tierMinute.advancedHours"
							list="hourOptions" listKey="label" listValue="value"
							theme="simple"
							value="%{tiersToSave[#status.index].tierMinute.advancedHours}" />
					</td>
					<td style ="width: 75px;">
						<s:select
							id="tiersToSave[%{#status.index}].tierMinute.advancedMinutes"
							name="tiersToSave[%{#status.index}].tierMinute.advancedMinutes"
							list="minuteOptions" listKey="label" listValue="value"
							theme="simple"
										value="%{tiersToSave[#status.index].tierMinute.advancedMinutes}" />
					</td>
				</tr>
			</s:iterator>

		</tbody>
	</table>
			</td>
			<td valign="top" style="clear:both; width: 170px;">
				<c:if test="${fn:length(tierList) > 0}">
					<div class="diamondWrapper">
						<input type="submit" onclick="return false;" class="button action"
							style="width: 105px; padding: 3px; margin-top: 20px; background-color:#ffffff; background: none"
							value="Delete Priorities" id="buttonDeleteAll"
							name="buttonDeleteAll" />
					</div>
				</c:if>

			</td>
		</tr>
	</table>

	<div style="text-align: left; width: 100%">
	
	<label for="marketplaceOverflow" style="vertical-align: middle;">
		Marketplace Overflow
	</label>
								
		<s:select
		id="marketplaceOverflow" name="marketplaceOverflow"
		list="yesNoOptions" listKey="value" listValue="label"
		theme="simple"
		value="%{marketplaceOverflow}" />
	
		<a href="" style="vertical-align: middle;" id="whatisMplcOverflow" onclick="return false;"><img
				src="${staticContextPath}/images/icons/info.gif" width="16"
				height="16" border="0" style="vertical-align: absmiddle;" /> </a> What
		is Marketplace Overflow?
	</div>
	
	<%-- ss: this is the code for the Marketplace Overflow explainer popup --%>
	<script type="text/javascript">
		jQuery(document).ready(function($){
		$("#whatisMplcOverflow").mouseover(function(e){
		      var x = e.pageX;
		      var y = e.pageY;
		      $("#explainer-marketplaceoverflow").css("top",y-20);
		      $("#explainer-marketplaceoverflow").css("left",x+20);
		      $("#explainer-marketplaceoverflow").show();
	
		 	}); 
		 	$("#whatisMplcOverflow").mouseout(function(e){
		 		$("#explainer-marketplaceoverflow").hide();
		 	});
		});
	</script>
	<div id="explainer-marketplaceoverflow"
		style="display: none; position: absolute; width: 205px; height: 125px; padding: 10px 20px; line-height: 130%; font-size: 11px; background: url(${staticContextPath}/images/common/explainerBg.gif) no-repeat;">
		<h5>
			Marketplace Overflow
		</h5>
		<p>Allows orders to route outside of your network AFTER each member has received the order. 
		This is helpful where your network has lower market coverage.</p>
	</div>
	<%-- /end explainer code --%>



	<s:div
		cssStyle="width:95%; margin:auto; border-top: 2px solid #ccc; padding-top:20px; clear:both;">
		<s:div cssClass="left">
			<s:a href="spnMonitorNetwork_display.action" >Cancel</s:a>
		</s:div>
		
		<c:if test="${fn:length(tiersToSave) > 0}">
			<div class="right saveAndDoneButtonDiv" style="padding-top:10px;">
				<input type="hidden" id="spnBeingEdited" name="spnBeingEdited" value="${spnBeingEdited}" />
				
				<s:submit type="input"
					id="buttonSaveAndDone" name="buttonSaveAndDone"
					cssClass="button action right" onclick="return showSaveConfirmationModal(${spnBeingEdited});"
					value="Save & Done"  theme="simple" />
					
					
				<p class="right">
					Click "Save & Done" to save the Routing Priority&nbsp;&nbsp;
				</p>
			</div>
		</c:if>
	</s:div>
	
			<%-- Delete Confirmation Dialog Start --%>
			<div class="jqmWindow jqmID1" id="confirmDeleteDialog"
				style="z-index: 3000; display: none;">
				<div class="dialogHeader">
					<img onclick="cancelDeleteModal();"
						src="${staticContextPath}/images/icons/modalCloseX.gif" />
					<h3 style="color:#ffffff">
						Confirm Delete of all tiers
					</h3>
				</div>
				<s:form id="dialogForm">
					<p>
						Are you sure you want to delete the Routing Priorities?
					</p>

					<div class="dialogFooter">
						<div style="float: left; padding-top: 10px;">
							<a class="cancelLink" onclick="cancelDeleteModal(); return false;"
								href="">Cancel</a>
						</div>
						<input type="button" id="buttonDeleteAllConfirm" value="Delete Priorities" class="button action right" />
	
					</div>
				</s:form>
			</div>
			<%-- Confirmation Dialog End--%>
	
	
			<%-- Save Confirmation Dialog Start--%>
			<div class="jqmWindow jqmID1" id="confirmSaveDialog"
				style="z-index: 3000; display: none;">
				<div class="dialogHeader">
					<img onclick="cancelSaveModal();"
						src="${staticContextPath}/images/icons/modalCloseX.gif" />
					<h3 style="color:#ffffff">
						Confirm Updates
					</h3>
				</div>
					<p>
						The changes you've entered will override the settings for this
						rule. Are you sure you want to save these settings?
					</p>

					<div class="dialogFooter">
						<div style="float: left; padding-top: 10px;">
							<a class="cancelLink" onclick="cancelSaveModal(); return false;"
								href="">Cancel</a>
						</div>
						<input type="submit" value="Save Update" class="button action right" />
						
					</div>
			</div>
			<%-- Confirmation Dialog End--%>
	

</c:if>


