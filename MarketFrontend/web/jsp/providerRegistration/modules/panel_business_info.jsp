<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="darkGrayModuleHdr">
	Business Information
</div>

<div class="grayModuleContent mainWellContent">


	<table cellpadding="0" cellspacing="0" width="679">
		<tr>
			<td width="340">
				<p>
					<b>Legal Business Name</b>
					<br />
					<s:property value="companyProfileDto.businessName" />
				</p>
			</td>
			<td width="339">
				<p>
					<b>Doing Business As (DBA)</b>
					<br />
					<s:property value="companyProfileDto.dbaName" />
				</p>
			</td>
		</tr>
		<tr>
			<td width="340">
				<p>
					<b>Main Business Phone</b>
					<br />
					<s:property value="companyProfileDto.businessPhone" />
				</p>
			</td>
			<td width="339">
				<p>
					<b>Business Fax</b>
					<br />
					<s:property value="companyProfileDto.businessFax" />
				</p>
			</td>
		</tr>
		<tr>
			<td width="340">
				<p>
					<b>Taxpayer ID (EIN or SSN)</b>
					<br />
					<s:property value="companyProfileDto.einNo" />
				</p>
			</td>
			<td width="339">
				<p>
					<b>Dun & BradStreet (DUNS) Number</b>
					<br />
					<s:property value="companyProfileDto.dunsNo" />
				</p>
			</td>
		</tr>
		<tr>
			<td width="340">
				<p>
					<b>Business Structure</b>
					<br />
					<s:property value="companyProfileDto.businessType" />
				</p>
			</td>
			<td width="339">
				<p>
					<b>Business Started</b>
					<br />
					<s:property value="companyProfileDto.businessStartDate" />
				</p>
			</td>
		</tr>
		<tr>
			<td width="340">
				<p>
					<b>Primary Industry</b>
					<br />
					<s:property value="companyProfileDto.primaryIndustry" />
				</p>
			</td>
			<td width="339">
				<p>
					<b>Website Address</b>
					<br />
					<s:property value="companyProfileDto.webAddress" />
				</p>
			</td>
		</tr>
		<tr>
			<td width="340">
				<p>
					<b>Size of the Company</b>
					<br />
					<s:property value="companyProfileDto.companySize" />
				</p>
			</td>
			<td width="339">
				<p>
					<b>Annual Sales Revenue</b>
					<br />
					<s:property value="companyProfileDto.annualSalesVolume" />
				</p>
			</td>
		</tr>
		<tr>
			<td width="340">
				<p>
					<b>Is the business foreign owned?</b>
					<br />
					<s:if test="%{companyProfileDto.foreignOwnedInd == 1}">
                			 Yes
                			 </s:if>
					<s:else>
                			 No
                			 </s:else>

				</p>
			</td>
			<td width="339">
				<p>
					<b>Foreign Owned Percentage</b>
					<br />
					<s:if test="%{companyProfileDto.foreignOwnedInd == 1}">
						<s:property value="companyProfileDto.foreignOwnedPct" />
					</s:if>
				</p>
			</td>
		</tr>
	</table>
</div>
