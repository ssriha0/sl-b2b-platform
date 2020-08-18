<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<h4><b>New Campaign to Firms</b></h4>

<div  class="campaignByFirm"
	style="background: #EEE;">


	<fieldset id="searchProviderFirmId">
		<s:hidden name="spnHeader.spnId" id="spnHeader.spnId"
			value="%{spnHeader.spnId}" />
		<s:hidden name="campaignHeader.campaignId"
			id="campaignHeader.campaignId" value="%{campaignHeader.campaignId}" />

		<p class="clearfix">
			<label class="larger">Search Provider Firm ID <span class="req">*</span></label>
			<s:textfield id="providerIdList" name="providerIdList"
				cssStyle="float: left; margin-right: 5px;" />
			<input type="button"
				class="providerSearchButton action submit button"
				style="margin-left: 165px; margin-top: 0px;" value="ADD" />
				<span class="larger">Separate multiple firms with a semi-colon (;)</span>
		</p>

		<div id="providerSearchResults" name="providerSearchResults">
			<table>			
			
				<c:forEach items="${providerFirmList}" var="firm">
				<tr>
					<td width="20px">					
						<input
							type="checkbox"
							id="providerCheckboxList"
							name="providerCheckboxList"
							class="firmId"
							value="${firm.firmId}"
							checked="checked"/>
					</td>

					<td>
						${firm.firmName}
					</td>

					<td>
						(ID#${firm.firmId})
					</td>

					<td>
						ServiceLive Status: ${firm.statusString}
					</td>
				</tr>
			</c:forEach>
			</table>		
		</div>

	</fieldset>
	
	
</div>


