<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<table id="filterpanel">
	<tr>
		<td class="paddr">
			<label>
				Filter by
			</label>
		</td>
		<td>
			<label>
				<abbr title="Select Provider Network">SPN</abbr>:
			</label>
		</td>
		<td class="paddr" colspan="3">
			<s:select id="filterBySPN" name="searchCriteriaVO.spnId" value="" list="spnList" listKey="spnId"
					listValue="spnName" headerKey="-1" headerValue="-Select One-" cssClass="filterSelect" theme="simple" cssStyle="width: 350px"/>
		</td>
		</tr>
		<tr>
		<td>&nbsp;</td>
		<td>
			<label>
				State:
			</label>
		</td>
		<td class="paddr">
			<s:select id="filterByState" name="searchCriteriaVO.stateCd" value="" list="stateList" listKey="id"
					listValue="description" headerKey="-1" headerValue="-Select One-" cssClass="filterSelect" theme="simple" cssStyle="width: 150px"/>
		</td>
		<td>
			<span id="statusLabel"><label>
				Status:
			</label></span>
		</td>
	 	<td>
			<s:select id="filterByPFStatus" name="searchCriteriaVO.providerFirmStatus" value="" list="providerFirmMembershipStatusList" listKey="id"
		 			listValue="description" headerKey="-1" headerValue="-Select One-" cssClass="filterSelect" theme="simple" cssStyle="width: 160px"/>
		</td>
	 <!-- <td>
			<span id="statusLabelBuyer"><label style="padding-left:10px;margin-left:-180px;">
				Status:			
			</label></span>
		</td>
		<td>
			<select id="filterBySpnBuyer" class="filterSelect" style="width:200px;margin-left:-120px;">
				<option value="-1" >-Select One-</option>
				<option value="PFSPNMEMBER" >SPN Member</option>
				<option value="PFFIRMOUTOFCOMPLIANCE" >Firm Out Of Compliance</option>				
			</select>
		</td>  -->	
		<td>
			<span id="memLabel"><label style="padding-left:10px;">
				Member Compliance status:				
			</label></span>
		</td>
		<td>
			<select id="memberStatus" name="memberStatus" class="filterSelect" disabled="disabled" style="width:200px;">
				<option value="-1" >-Select One-</option>
				<option value="Action" >Action Required</option>
				<option value="Attention" >Attention Needed </option>				
				<option value="Compliance" >In Compliance</option>
				<c:if test="${recertificationBuyerFeature == true}">
				<option value="bgCheckStatus" >Background Check Status</option>
				</c:if>
			</select>
		</td>
	</tr>
</table>
