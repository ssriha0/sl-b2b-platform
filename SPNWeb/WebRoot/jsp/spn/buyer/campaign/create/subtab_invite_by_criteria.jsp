<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<s:form action="spnCreateCampaign_saveAndDone"
	id="spnCreateCampaign_saveAndDone" name="spnCreateCampaign_saveAndDone"
	method="post" enctype="multipart/form-data" theme="simple"
	validate="true">


	<div class="clearfix span-14">
		<jsp:include page="/jsp/spn/common/validation_messages.jsp" />
	</div>



	<div class="clearfix span-14">

		<div id="campaignCriterias">
			<jsp:include
				page="/jsp/spn/buyer/campaign/create/campaign_criterias.jsp"></jsp:include>

		</div>

		<div class="clearfix buttonarea">
			<a class="cancel left"
				href="${contextPath}/spnMonitorCampaign_display.action">Cancel</a>
			<s:submit type="input" method="saveAndDone"
				cssClass="button action right" theme="simple" value="Save & Done" />
		</div>
	</div>
</s:form>



