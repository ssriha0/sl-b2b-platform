<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<s:form action="spnInviteProvider_saveAndDone"
	id="spnInviteProvider_saveAndDone" name="spnInviteProvider_saveAndDone"
	method="post" enctype="multipart/form-data" theme="simple"
	validate="false">


	<div class="clearfix span-14">
		<jsp:include page="/jsp/spn/common/validation_messages.jsp" />
	</div>




	<div class="clearfix span-14">

		<div id="campaignCriteriasx">

			<div class="error" style="display:none">
			</div>

		
			<div class="clearfix" style="margin-top: 5px; margin-left: 10px">
				<div class="half multiselect">
					<label>
						Start Date
						<span class="req">*</span>
					</label>
					<s:textfield id="startDate2" name="startDate2" cssClass="text"
						cssStyle="width: 100px;" value="%{startDate2}" />
				</div>
				<div class="half multiselect">
					<label>
						End Date
						<span class="req">*</span>
					</label>
					<s:textfield id="endDate2" name="endDate2" cssStyle="width: 100px;"
						cssClass="text" value="%{endDate2}" />
				</div>
			</div>
		
		
		
			<jsp:include
				page="/jsp/spn/buyer/campaign/create/invite_by_specific_provider/panel_new_campaign_by_invite.jsp" />
			<jsp:include
				page="/jsp/spn/buyer/campaign/create/invite_by_specific_provider/panel_name_new_campaign.jsp" />

		</div>

		<div class="clearfix buttonarea">
			<a class="cancel left"
				href="${contextPath}/spnMonitorCampaign_display.action">Cancel</a>
			
			<c:set var="campaignId" value="${campaignHeader.campaignId}"/>
			<input type="hidden" id="campaignId" name="campaignId" value="${campaignHeader.campaignId}" />
			<input type="hidden" id="spnId" name="spnId" value="${spnHeader.spnId}" />
			
			 
			<s:submit type="input" method="saveAndDone"
				id="inviteSaveAndDone"
				name="inviteSaveAndDone"
				cssClass="button action right" theme="simple" value="Save & Done" />
			
			
			<%--  
			<input id="inviteSaveAndDone" name="inviteSaveAndDone" type="button" class="button action right" value="Save & Done" />
			--%>
			
			
		</div>
	</div>


</s:form>
