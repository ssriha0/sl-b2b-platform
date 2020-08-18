<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="BuyerAdmin.userProfile.marketPlacePrefs"/>
		</jsp:include>	


<div class="darkGrayModuleHdr">
	ServiceLive Activities & Permissions
</div>
<div class="grayModuleContent">
	<div class="grayModuleHdr">
		Service Order Activities
	</div>
	<div class="grayModuleContent mainWellContent">
		<p>
			Check all that apply.
		</p>
		<p>
			<input type="checkbox" />
			Create Service Orders
		</p>
		<ul class="vertBulleted leftOffset">
			<li>
				Create new service order
			</li>
			<li>
				Copy service orders
			</li>
			<li>
				Create continuation service orders
			</li>
			<li>
				Add notes
			</li>
			<li>
				Accept/Reject offers
			</li>
			<li>
				Reschedule service orders
			</li>
			<li>
				Revise price
			</li>
			<li>
				Re-route service orders
			</li>
		</ul>
	</div>
	<div class="grayModuleHdr">
		Financial & Banking Activities
	</div>
	<div class="grayModuleContent mainWellContent">
		<p>
			Check all that apply.
		</p>
		<p>
			<input type="checkbox" />
			Add/Edit/Delete Bank Account
		</p>
		<p>
			<input type="checkbox" />
			Add Funds
		</p>
		<p>
			<input type="checkbox" />
			Withdraw Payment
		</p>
	</div>
	<div class="grayModuleHdr">
		Administrative Activities
	</div>
	<div class="grayModuleContent mainWellContent">
		<p>
			Check all that apply.
		</p>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="300">
					<p>
						<input type="checkbox" />
						Manage Company Profile
					</p>
					<ul class="vertBulleted leftOffset">
						<li>
							Update company information
						</li>
						<li>
							Update branding information
						</li>
						<li>
							Modify global service order attachments
						</li>
					</ul>
				</td>
				<td width="300">
					<p>
						<input type="checkbox" />
						Manage Users
					</p>
					<ul class="vertBulleted leftOffset">
						<li>
							View ServiceLive user listing
						</li>
						<li>
							Add/delete/disable users
						</li>
						<li>
							Change permissions for users
						</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td>
					<p>
						<input type="checkbox" />
						Access Custom Service Order Reports
					</p>
					<ul class="vertBulleted leftOffset">
						<li>
							Allows users to generate custom reports
						</li>
					</ul>
				</td>
				<td>
					<p>
						<input type="checkbox" />
						Manage Service Order Templates
					</p>
					<ul class="vertBulleted leftOffset">
						<li>
							Add/delete/edit service order templates
						</li>
				</td>
			</tr>
		</table>
	</div>
</div>
<div class="darkGrayModuleHdr">
	Communication Preferences
</div>
<div class="grayModuleContent mainWellContent">
	<p>
		Please enter contact information below for the buyer you are
		registering.
	</p>
	<table cellpadding="0" cellspacing="0" width="650">
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
			<td width="325">
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
			<td width="325">
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
<div class="darkGrayModuleHdr">
	Maximum Price
</div>
<div class="grayModuleContent mainWellContent">
	<p>
		Set the limit this buyer can spend on each service order. The buyer
		will not be able to exceed this limit without your authorization.
	</p>
	<p>
		Maximum Price per Service Order
		<br>
		$
		<input type="text" onfocus="clearTextbox(this)" value="###.##"
			style="width: 80px;" class="shadowBox grayText" />
	</p>
</div>
<div class="clearfix">
	<div class="formNavButtons">
		<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72"
			height="20"
			style="background-image: url(${staticContextPath}/images/btn/previous.gif);"
			class="btn20Bevel" />
		<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="49"
			height="20" style="background-image: url(${staticContextPath}/images/btn/save.gif);"
			class="btn20Bevel" />
		<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="50"
			height="20" style="background-image: url(${staticContextPath}/images/btn/next.gif);"
			class="btn20Bevel" />
	</div>
	<div class="bottomRightLink">
		<a href="">Cancel</a>
	</div>
</div>
