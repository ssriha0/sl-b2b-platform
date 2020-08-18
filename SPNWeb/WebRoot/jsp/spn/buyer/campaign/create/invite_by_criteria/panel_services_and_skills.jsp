<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	jQuery(document).ready(function($) {
		clickMainServiceCampaign();
		//R10.3 SL-19812 Introduce Primary Industry criteria for Campaign
		//on edit mode, if there are values, then show primaryIndustryDiv and check the check box
		var count = $('.primaryIndustryClick:checked').length;
		if(count>0){
			$("#primaryIndustryCheckBox").attr('checked', true);
		}
		
		//show/hide primaryIndustryDiv in edit mode
		if ($('#primaryIndustryCheckBox').is(":checked")){
			$("#primaryIndustryDiv").show();
		}else{
			$("#primaryIndustryDiv").hide();
		}
	});
	
	//R10.3 SL-19812 Introduce Primary Industry criteria for Campaign
	//show/hide primary industry div
	function activatePrimaryIndustryDiv(){
		if ($('#primaryIndustryCheckBox').is(":checked")){
			$("#primaryIndustryDiv").show();
		}else{
			$(".primaryIndustryClick").attr("checked", false);
			$('#primaryIndustryDiv').children('div.pickedClick').children('label').html('No Selection');
			$("#primaryIndustryDiv").hide();
		}
			
	}
	
</script>

<h3 class="collapse c-serv" title="c-serv">
	<span>Primary Industry, Services &amp; Skills</span><span class="plus c-serv"></span><span
		class="min c-serv"></span>
</h3>
<div class="toggle c-serv">
	<fieldset>
		<p>
			Items marked with an asterix(
			<span class="req">*</span>) are required fields. 
			 Main Services and Skills are required; however, in order to better target specific firms, 
			 it is recommended that you opt to include firms within one or more specific industries.
		</p>
		<!--SL-19812 CR -->
		<p>
			By Selecting "Restrict Campaign by Primary Industry", only Firms that match the Primary Industry(s) selected will be invited to join your network.
		</p>
		<div class="wider">
		<!-- R10.3 SL-19812 Introduce Primary Industry criteria for Campaign -->
			<div id = "primaryIndustryCheckBoxDiv">
				<c:set var="checked1" value="" ></c:set>
				<c:if test= "${approvalItems.isPrimaryIndustryEnabled == true}">
					<c:set var="checked1" value="checked" ></c:set>
				</c:if>	
				<input type = "checkbox"  id="primaryIndustryCheckBox" name = "approvalItems.isPrimaryIndustryEnabled" 
						onchange="activatePrimaryIndustryDiv();" value="true"  ${checked1}>
					Restrict Campaign by Primary Industry
			</div>
			<div id = "primaryIndustryDiv">
				<label>
					Primary Industry
					<span class="req">*</span>
				</label>
				<div class="picked pickedClick">
					<label>
						Select All that Apply
					</label>
				</div>
				<div class="select-options">
					<c:forEach items="${lookupsVO.primaryIndustry}" var="eachPrimaryIndustry">
						<c:set var="checked" value="" ></c:set>
						<c:forEach items="${approvalItems.selectedPrimaryIndustry}" var="selectedPrimaryIndustry" >
							<c:if test="${eachPrimaryIndustry.id == selectedPrimaryIndustry}">
								<c:set var="checked" value=" checked " ></c:set>
							</c:if>
						</c:forEach>
						<div style="clear: left;"><input class="primaryIndustryClick" type="checkbox" name="approvalItems.selectedPrimaryIndustry" value="${eachPrimaryIndustry.id}" ${checked} /> ${eachPrimaryIndustry.description}</div>
					</c:forEach>
				</div>
			</div>
			<div>
				<label>
					Main Services
					<span class="req">*</span>
				</label>
				<div class="picked pickedClick">
					<label>
						Select All that Apply
					</label>
				</div>
				<div class="select-options">
					<c:forEach items="${lookupsVO.allMainServices}" var="eachMainService">
						<c:set var="checked" value="" ></c:set>
						<c:forEach items="${approvalItems.selectedMainServices}" var="selMainService" >
							<c:if test="${eachMainService.id == selMainService}">
								<c:set var="checked" value=" checked " ></c:set>
							</c:if>
						</c:forEach>
						<div style="clear: left;"><input type="checkbox" name="approvalItems.selectedMainServices" value="${eachMainService.id}" ${checked} /> ${eachMainService.description}</div>
					</c:forEach>
				</div>
			</div>
			<div>
				<label>
					Skills
					<span class="req">*</span>
				</label>
				<div id="servicesWithSkills">
					<jsp:include
						page="/jsp/spn/common/checkbox_services_with_skills.jsp"></jsp:include>
				</div>
			</div>
		</div>

	</fieldset>
</div>

