<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<script type="text/javascript">
	jQuery(document).ready(function($) {
		loadPanelNewCampaignHideShow();
		updateCampaignCount();
		<c:if test="${approvalItems.commercialGeneralLiabilitySelected == true}">
			$('.cchecktoggle').show();
		</c:if>
		<c:if test="${approvalItems.vehicleLiabilitySelected == true}">
			$('.vchecktoggle').show();
		</c:if>
		<c:if test="${approvalItems.workersCompensationSelected == true}">
			$('.wchecktoggle').show();
		</c:if>	


		$('input.cbclass').click(function(event){
	   		if(! $('input.cbclass').attr('checked'))
	   		{
	   			$('input.tfclass').val('');
	   			$('input.dcbclass').removeAttr('checked');
	   		}
	   			
	 	});

		$('input.vshcheck').click(function(event){
	   		if(! $('input.vshcheck').attr('checked'))
	   		{
	   			$('input.vchecktoggle').val('');
	   			$('input.vchecktoggle').removeAttr('checked');
	   		}
	 	});

		$('input.wshcheck').click(function(event){
	   		if(! $('input.wshcheck').attr('checked'))
	   		{
	   			$('input.wchecktoggle').removeAttr('checked');
	   		}	   			
	 	});

		$('input.cshcheck').click(function(event){
	   		if(! $('input.cshcheck').attr('checked'))
	   		{
	   			$('input.cchecktoggle').val('');
	   			$('input.cchecktoggle').removeAttr('checked');
	   		}
	 	});
	
		$('#loadSPNSpinner').jqm({modal: true, toTop: true});
		$('#showSpin').click(function() {
			$('#loadSPNSpinner').jqmShow();
		});
	if(document.getElementById('approvalItems.isAllStatesSelected').checked){
		toggleAllStates();
	}
	
	});
	
</script>

<h3 class="collapse c-general" title="c-general">
	<span>New Campaign</span><span class="plus c-general"></span><span
		class="min c-general"></span>
</h3>

	<div class="toggle c-general">
		<s:hidden name="spnHeader.spnId" id="spnHeader.spnId" value="%{spnHeader.spnId}" />
		<s:hidden name="campaignHeader.campaignId" id="campaignHeader.campaignId" value="%{campaignHeader.campaignId}" />
		<fieldset>
			<p>
				Items marked with an asterix(
				<span class="req">*</span>) are required.
			</p>


			<div class="clearfix" style="margin-top: 5px; margin-left: 10px">
				<div class="half multiselect">
					<label>
						Start Date
						<span class="req">*</span>
					</label>
					<s:textfield id="startDate" name="startDate" cssClass="text"
						cssStyle="width: 100px;" value="%{startDate}" />
				</div>
				<div class="half multiselect">
					<label>
						End Date
						<span class="req">*</span>
					</label>
					<s:textfield id="endDate" name="endDate" cssStyle="width: 100px;" cssClass="text"
						value="%{endDate}" />
				</div>
			</div>



			<h3 class="noexpand">
				Target Locations
				<span class="req">*</span>
			</h3>
			<p>
				Select or enter at least one market or state below.
			</p>



			<div class="clearfix">
				<div class="half multiselect">
					<label>
						Market
					</label>
					<div id="markets2" name="markets2" style="display: none">
						<select disabled="disabled" style="width:  180px;">
							<option> All States Selected </option>
						</select>
					</div>
					
					<div id="markets" name="markets" style="display: block">
						<div class="picked pickedClick">
							<label>
								Select All that Apply
							</label>
						</div>
						
						<div class="select-options" id="market_select_options">
							<c:forEach items="${lookupsVO.allMarkets}" var="eachMarket">
								<c:set var="checked" value="" ></c:set>
								<c:if test="${approvalItems !=  null}">
									<c:forEach items="${approvalItems.selectedMarkets}" var="selMarket" >
										<c:if test="${eachMarket.id == selMarket}">
											<c:set var="checked" value=" checked " ></c:set>
										</c:if>
									</c:forEach>
								</c:if>
								<div style="clear: left;"><input type="checkbox" name="approvalItems.selectedMarkets" value="${eachMarket.id}" ${checked}/> ${eachMarket.description}
								</div>
							</c:forEach>
						</div>
						
					</div>
					<div>
						<s:checkbox id="approvalItems.isAllMarketsSelected" name="approvalItems.isAllMarketsSelected" value="%{approvalItems.isAllMarketsSelected}" onchange="toggleAllMarkets()"/> Select all markets
					</div>
				</div>
				
				<div class="half multiselect">
					<label>
						States
					</label>
					<div id="states2" name="states2" style="display: none">
						<select disabled="disabled" style="width:  180px;">
							<option> All States Selected </option>
						</select>
					</div>
					<div id="states" name="states" style="display: block">
						<div class="picked pickedClick">
							<label>
								Select All that Apply
							</label>
						</div>
						<div class="select-options" >
							<c:forEach items="${lookupsVO.allStates}" var="eachState">
								<c:set var="checked" value="" ></c:set>
								<c:forEach items="${approvalItems.selectedStates}" var="selState" >
									<c:if test="${eachState.id == selState}">
										<c:set var="checked" value=" checked " ></c:set>
									</c:if>
								</c:forEach>
								<div style="clear: left;"><input type="checkbox" name="approvalItems.selectedStates" value="${eachState.id}" ${checked} /> ${eachState.description}</div>
							</c:forEach>
						</div>
					</div>
					
					<div>
						<s:checkbox id="approvalItems.isAllStatesSelected" name="approvalItems.isAllStatesSelected" value="%{approvalItems.isAllStatesSelected}" onchange="toggleAllStates()"/> Select all states
					</div>					
				</div>

			</div>


			<div class="results">
			
				<div class="even clearfix" id="provider_count_match_spn">
					<jsp:include page="/jsp/spn/buyer/campaign/create/provider_count_matching_spn.jsp" />				
				</div>
				
				<div class="odd clearfix" id="provider_count_match_campaign">
					<h4>
						Providers Matching Invitation:
					</h4>
					<label>
						Firms
					</label>
					<span>N/A</span>
					<label>
						Providers
					</label>
					<span>N/A</span>
										
					<input type="submit" id="buttonUpdateCampaignCount" class="button action right" value="Update Results" onclick="return false;" />
				</div>
			</div>

		<div  class="clearfix" style="height: 60px; border-style: solid">
			<div class="half multiselect" >
				<label>
					Minimum Rating
				</label>

				<div class="minimum-ratings-dropdown" >
					<s:select id="approvalItems.selectedMinimumRating"
						name="approvalItems.selectedMinimumRating"
						value="%{approvalItems.selectedMinimumRating}"
						list="lookupsVO.allMinimumRatings" listKey="id"
						listValue="description" headerKey="0" headerValue="No Selection"
						theme="simple" />
				</div>				
				<div>
					<s:checkbox id="approvalItems.isNotRated" name="approvalItems.isNotRated" value="%{approvalItems.isNotRated}"/> Include non-rated providers
				</div>
								

			</div>
			
			<div class="half multiselect">
				<label>
					Languages
				</label>
				<div class="picked pickedClick">
					<label>
						No Selection
					</label>
				</div>
				<div class="select-options">
					<c:forEach items="${lookupsVO.allLanguages}" var="eachLang">
						<c:set var="checked" value=""></c:set>
						<c:forEach items="${approvalItems.selectedLanguages}"
							var="selLang">
							<c:if test="${eachLang.id == selLang}">
								<c:set var="checked" value=" checked "></c:set>
							</c:if>
						</c:forEach>
						<div style="clear: left;">
							<input type="checkbox" name="approvalItems.selectedLanguages"
								value="${eachLang.id}" ${checked} />
							${eachLang.description}
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<br/>
		<div style="clear: left;" class=" multiselect clearfix" >
			<label style="float: left; margin-right: 5px; padding-top: 9px;">
				Minimum Completed Service Orders
			</label>
			<s:textfield id="approvalItems.minimumCompletedServiceOrders"
				name="approvalItems.minimumCompletedServiceOrders"
				value="%{approvalItems.minimumCompletedServiceOrders}"
				theme="simple" cssClass="text" cssStyle="width: 40" maxlength="3" />
		</div>

		
		<table border="0" cellpadding="5" cellspacing="0">
				<thead>
					<th colspan="2">
						Insurance
					</th>
				</thead>
				<tbody>
					<tr>
						<td>
							<div class="left checkbox">
								<s:checkbox cssClass="checkbox vshcheck" id="approvalItems.vehicleLiabilitySelected" 
									name="approvalItems.vehicleLiabilitySelected" fieldValue="true" 
									value="%{approvalItems.vehicleLiabilitySelected}" theme="simple"></s:checkbox>
							</div>
							<label>
								Vehicle Liability
							</label>
						</td>
					</tr>
					<tr>
						<td class="textleft verify">
							<label class="vchecktoggle sm">
								Minimum Amount
								<span class="req">*</span> $
							</label>
							<s:textfield id="approvalItems.vehicleLiabilityAmt" name="approvalItems.vehicleLiabilityAmt"
								value="%{approvalItems.vehicleLiabilityAmt}" theme="simple" cssClass="vchecktoggle text" cssStyle="width: 80" maxlength="9"/>
						</td>
					</tr>
					<tr>
						<td class="textleft verify">
							<s:checkbox cssClass="checkbox vchecktoggle" id="approvalItems.vehicleLiabilityVerified" 
								name="approvalItems.vehicleLiabilityVerified" fieldValue="true" 
								value="%{approvalItems.vehicleLiabilityVerified}" theme="simple"></s:checkbox>
							
							<span class="checkbox vchecktoggle">Must Be Verified<br/><br/></span>
						</td>
					</tr>
					<tr>
						<td>
							<div class="left checkbox">
								<s:checkbox cssClass="checkbox wshcheck" id="approvalItems.workersCompensationSelected" 
									name="approvalItems.workersCompensationSelected" fieldValue="true" 
									value="%{approvalItems.workersCompensationSelected}" theme="simple"></s:checkbox>
							</div>
							<label>
								Workers Compensation
							</label>
						</td>
					</tr>
					<tr>
						<td class="textleft verify">
							<s:checkbox cssClass="checkbox wchecktoggle" id="approvalItems.workersCompensationVerified" 
								name="approvalItems.workersCompensationVerified" fieldValue="true" 
								value="%{approvalItems.workersCompensationVerified}" theme="simple"></s:checkbox>
						
							<span class="checkbox wchecktoggle">Must Be Verified<br/><br/></span>
						</td>
					</tr>
					<tr>
						<td>

							<div class="left checkbox">
								<s:checkbox cssClass="checkbox cshcheck" id="approvalItems.commercialGeneralLiabilitySelected" 
									name="approvalItems.commercialGeneralLiabilitySelected" fieldValue="true" 
									value="%{approvalItems.commercialGeneralLiabilitySelected}" theme="simple"></s:checkbox>
							</div>

							<label>
								Commercial General Liability
							</label>
						</td>
					</tr>
					<tr>
						<td class="textleft verify">
							<label class="cchecktoggle sm">
								Minimum Amount
								<span class="req">*</span> $
							</label>
							<s:textfield id="approvalItems.commercialGeneralLiabilityAmt" name="approvalItems.commercialGeneralLiabilityAmt"
								value="%{approvalItems.commercialGeneralLiabilityAmt}" theme="simple" cssClass="cchecktoggle text" cssStyle="width: 80" maxlength="9"/>
						</td>
					</tr>
					<tr>
						<td class="textleft verify">
							<s:checkbox cssClass="checkbox cchecktoggle" id="approvalItems.commercialGeneralLiabilityVerified" 
								name="approvalItems.commercialGeneralLiabilityVerified" fieldValue="true" 
								value="%{approvalItems.commercialGeneralLiabilityVerified}" theme="simple"></s:checkbox>
							
							<span class="checkbox cchecktoggle">Must Be Verified<br/><br/></span>
						</td>
					</tr>
				</tbody>
			</table>



			<h3 class="noexpand">
				Maximum Firm Size
			</h3>


			<div class="clearfix" style="margin-top: 5px; margin-left: 10px">
				<div class="half multiselect" style="width: 300px;">
					<label>
						Number of Employees
					</label>
					<s:select id="approvalItems.selectedCompanySize" name="approvalItems.selectedCompanySize"
						headerKey="-1" headerValue="Select One"
						list="%{lookupsVO.allCompanySizes}" listKey="id"
						listValue="description" value="%{approvalItems.selectedCompanySize}"/>
				</div>				
				<div class="half multiselect">
					<label>
						Annual Sales Revenue
					</label>
					<s:select id="approvalItems.selectedSalesVolume" name="approvalItems.selectedSalesVolume"
						headerKey="-1" headerValue="Select One"
						list="%{lookupsVO.allSalesVolumes}" listKey="id"
						listValue="description" value="%{approvalItems.selectedSalesVolume}"/>
				</div>
			</div>

		</fieldset>
	</div>
