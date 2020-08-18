<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="BuyerAdmin.companyProfile.businessInfo"/>
		</jsp:include>	


<h3 class="paddingBtm">
	[Papanek Inc.]
</h3>
<p class="paddingBtm">
	Use the fields below to edit your business and contact information.
	Some fields are locked for your protection. If you need to make
	changes, contact your ServiceLive administrator.
</p>
<!-- NEW MODULE/ WIDGET-->
<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">
	Business Information
</div>
<div class="grayModuleContent mainWellContent">
	<table cellpadding="0" cellspacing="0" width="679">
		<tr>
			<td width="350">
				<p>
					<strong>Business Name</strong>
					<br />
					[Previously entered company name]
				</p>
			</td>
			<td>
				<p>
					<strong>Doing Business As (DBA)</strong>
					<br />
					[Previously entered DBA name]
				</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>
					<label>
						Main Business Phone
					</label>
					<br />
					<input type="text" class="shadowBox grayText" style="width: 30px;"
						value="###" onfocus="clearTextbox(this)" maxlength="3" />
					-
					<input type="text" class="shadowBox grayText" style="width: 30px;"
						value="###" onfocus="clearTextbox(this)" maxlength="3" />
					-
					<input type="text" class="shadowBox grayText" style="width: 45px;"
						value="####" onfocus="clearTextbox(this)" maxlength="4" />
					Ext.
					<input type="text" class="shadowBox grayText" style="width: 45px;"
						value="####" onfocus="clearTextbox(this)" maxlength="5" />
				</p>
			</td>
			<td>
				<p>
					<label>
						Business Fax
					</label>
					<br />
					<input type="text" class="shadowBox grayText" style="width: 30px;"
						value="###" onfocus="clearTextbox(this)" maxlength="3" />
					-
					<input type="text" class="shadowBox grayText" style="width: 30px;"
						value="###" onfocus="clearTextbox(this)" maxlength="3" />
					-
					<input type="text" class="shadowBox grayText" style="width: 45px;"
						value="####" onfocus="clearTextbox(this)" maxlength="4" />
					Optional
				</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>
					<strong>Taxpayer ID (EIN or SSN)</strong>
					<br />
					[000-00-0000]
				</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>
					<strong>Business Structure</strong>
					<br />
					[Limited Liability Company (LLC)]
				</p>
			</td>
			<td>
				<p>
					<strong>Business Started</strong>
					<br />
					July 2002
				</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>
					<label>
						Primary Industry
					</label>
					<br />
					<select style="width: 256px;" class="grayText"
						onclick="changeDropdown(this)">
						<option>
							Select One
						</option>
					</select>
				</p>
			</td>
			<td>
				<p>
					<label>
						Website Address
					</label>
					<br />
					<input type="text" class="shadowBox grayText" style="width: 250px;"
						value="http://www.webaddress.com" onfocus="clearTextbox(this)" />
					Optional
				</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>
					<label>
						Size of Company
					</label>
					<br />
					<select style="width: 156px;" class="grayText"
						onclick="changeDropdown(this)">
						<option>
							Select One
						</option>
					</select>
				</p>
			</td>
			<td>
				<p>
					<label>
						Annual Sales Revenue
					</label>
					<br />
					<select style="width: 256px;" class="grayText"
						onclick="changeDropdown(this)">
						<option>
							Select One
						</option>
					</select>
				</p>


			</td>
		</tr>
		<tr>
			<td>
				<p>
					Is the business foreign owned?
					<br />
					<span class="formFieldOffset"> <input type="radio" name="r1"
							class="antiRadioOffsets" /> Yes <input type="radio" checked=""
							name="r1" class="antiRadioOffsets" /> No</span>
				</p>
			</td>
			<td>
				<p>
					<label>
						Foreign Owned Percentage
					</label>
					<br />
					<select style="width: 256px;" class="grayText"
						onclick="changeDropdown(this)">
						<option>
							Select One
						</option>
					</select>
				</p>
			</td>
		</tr>
	</table>

</div>
<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">
	Business Address
</div>
<div class="grayModuleContent mainWellContent">
	<table cellpadding="0" cellspacing="0" width="650">
		<tr>
			<td width="325">
				<p>
					<label>
						Street Name
					</label>
					<br />
					<input type="text" onfocus="clearTextbox(this)" value="[Address 1]"
						style="width: 250px;" class="shadowBox grayText" />
				</p>
				<p>
					<input type="text" onfocus="clearTextbox(this)" value="[Address 1]"
						style="width: 250px;" class="shadowBox grayText" />
				</p>
			</td>
			<td width="325">
				<p>
					<label>
						Apt. #
					</label>
					<br />
					<input type="text" onfocus="clearTextbox(this)" value="[Apt]"
						style="width: 100px;" class="shadowBox grayText" />
				</p>
				<p style="line-height: 14px;">
					&nbsp;
				</p>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td width="205">
							<p>
								<label>
									City
								</label>
								<br />
								<input type="text" class="shadowBox grayText"
									style="width: 190px;" value="[City]"
									onfocus="clearTextbox(this)" />
							</p>
						</td>
						<td width="55">
							<p>
								<label>
									State
								</label>
								<br />
								<select style="width: 50px;" class="grayText"
									onclick="changeDropdown(this)">
									<option>
										AL
									</option>
								</select>
							</p>
						</td>
						<td width="130">
							<p>
								<label>
									ZIP
								</label>
								<br />
								<input type="text" onfocus="clearTextbox(this)"
									value="#####-####" style="width: 80px;"
									class="shadowBox grayText" maxlength="10" />
							</p>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">
	Mailing Address
</div>
<div class="grayModuleContent mainWellContent">
	<p>
		<input type="checkbox" />
		The mailing address is the same as the business address.
	</p>
	<table cellpadding="0" cellspacing="0" width="650">
		<tr>
			<td width="325">
				<p>
					<label>
						Street Name
					</label>
					<br />
					<input type="text" onfocus="clearTextbox(this)" value="[Address 1]"
						style="width: 250px;" class="shadowBox grayText" />
				</p>
				<p>
					<input type="text" onfocus="clearTextbox(this)" value="[Address 1]"
						style="width: 250px;" class="shadowBox grayText" />
				</p>
			</td>
			<td width="325">
				<p>
					<label>
						Apt. #
					</label>
					<br />
					<input type="text" onfocus="clearTextbox(this)" value="[Apt]"
						style="width: 100px;" class="shadowBox grayText" />
				</p>
				<p style="line-height: 14px;">
					&nbsp;
				</p>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td width="205">
							<p>
								<label>
									City
								</label>
								<br />
								<input type="text" class="shadowBox grayText"
									style="width: 190px;" value="[City]"
									onfocus="clearTextbox(this)" />
							</p>
						</td>
						<td width="55">
							<p>
								<label>
									State
								</label>
								<br />
								<select style="width: 50px;" class="grayText"
									onclick="changeDropdown(this)">
									<option>
										AL
									</option>
								</select>
							</p>
						</td>
						<td width="130">
							<p>
								<label>
									ZIP
								</label>
								<br />
								<input type="text" onfocus="clearTextbox(this)"
									value="#####-####" style="width: 80px;"
									class="shadowBox grayText" maxlength="10" />
							</p>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">
	Primary Contact Information - Administrator
</div>
<div class="grayModuleContent mainWellContent">
	<p>
		The following person is registered as the ServiceLive administrator
		for your company. Contact ServiceLive support if you would like to
		make another person your administrator.
	</p>
	<p>
		<strong>Name</strong>
		<br />
		Taylor Bennett
	</p>
	<table cellpadding="0" cellspacing="0" width="650">
		<tr>
			<td width="325">
				<p>
					<label>
						Role within Company
					</label>
					<br />
					<select style="width: 256px;" class="grayText"
						onclick="changeDropdown(this)">
						<option>
							Select One
						</option>
					</select>
				</p>
			</td>
			<td width="325">
				<p>
					<label>
						Job Title
					</label>
					<br />
					<input type="text" onfocus="clearTextbox(this)" value="[Job Title]"
						style="width: 250px;" class="shadowBox grayText" />
					Optional
				</p>
			</td>
		</tr>

		<tr>
			<td colspan="2">
				<p>
					<label>
						Business Phone
					</label>
					<br />
					<input type="text" class="shadowBox grayText" style="width: 30px;"
						value="###" onfocus="clearTextbox(this)" maxlength="3" />
					-
					<input type="text" class="shadowBox grayText" style="width: 30px;"
						value="###" onfocus="clearTextbox(this)" maxlength="3" />
					-
					<input type="text" class="shadowBox grayText" style="width: 45px;"
						value="####" onfocus="clearTextbox(this)" maxlength="4" />
					Ext.
					<input type="text" class="shadowBox grayText" style="width: 45px;"
						value="####" onfocus="clearTextbox(this)" maxlength="4" />
				</p>
				<p>
					<label>
						Mobile Phone
					</label>
					<br />
					<input type="text" class="shadowBox grayText" style="width: 30px;"
						value="###" onfocus="clearTextbox(this)" maxlength="3" />
					-
					<input type="text" class="shadowBox grayText" style="width: 30px;"
						value="###" onfocus="clearTextbox(this)" maxlength="3" />
					-
					<input type="text" class="shadowBox grayText" style="width: 45px;"
						value="####" onfocus="clearTextbox(this)" maxlength="4" />
				</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>
					<label>
						E-mail Address
					</label>
					<br />
					<input type="text" onfocus="clearTextbox(this)"
						value="[E-mail Address]" style="width: 250px;"
						class="shadowBox grayText" />
				</p>
			</td>
			<td>
				<p>
					<label>
						Confirm E-mail Address
					</label>
					<br />
					<input type="text" onfocus="clearTextbox(this)"
						value="[E-mail Address]" style="width: 250px;"
						class="shadowBox grayText" />
				</p>
			</td>
		</tr>
		<tr>
			<td>
				<p>
					<label>
						Alternate E-mail Address
					</label>
					<br />
					<input type="text" onfocus="clearTextbox(this)"
						value="[Alternate E-mail Address]" style="width: 250px;"
						class="shadowBox grayText" />
				</p>
			</td>
			<td>
				<p>
					<label>
						Confirm Alternate E-mail Address
					</label>
					<br />
					<input type="text" onfocus="clearTextbox(this)"
						value="[Alternate E-mail Address]" style="width: 250px;"
						class="shadowBox grayText" />
				</p>
			</td>
		</tr>

	</table>
</div>
<div class="clearfix">
	<div class="formNavButtons">
		<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="49"
			height="20" style="background-image: url(${staticContextPath}/images/btn/save.gif);"
			class="btn20Bevel"/>

	</div>
	<div class="bottomRightLink">
		<a href="">Cancel</a>
	</div>
</div>
<!-- END TAB PANE -->
