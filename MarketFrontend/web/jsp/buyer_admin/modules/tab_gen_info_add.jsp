<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="BuyerAdmin.userProfile.generalInfoAdd"/>
		</jsp:include>	


<p class="paddingBtm">
	Complete the fields below to create a new buyer profile.
</p>
<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">
	Personal Information
</div>
<div class="grayModuleContent mainWellContent"
	<table cellpadding="0" cellspacing="0" width="650">
    <tr>
      <td width="325"><p>
          <label>First Name</label>
          <br />
          <input type="text" onfocus="clearTextbox(this)" value="[First Name]" style="width: 250px;" class="shadowBox grayText"/>
        </p></td>
      <td width="325"><p>
          <label>Middle Name</label>
          <br />
          <input type="text" onfocus="clearTextbox(this)" value="[First Name]" style="width: 250px;" class="shadowBox grayText"/>
          Optional </p></td>
    </tr>
    <tr>
      <td><p>
          <label>Last Name</label>
          <br />
          <input type="text" onfocus="clearTextbox(this)" value="[Last Name]" style="width: 250px;" class="shadowBox grayText"/>
          </p></td>
      <td><p>
          <label>Suffix (Jr., II, etc.)</label>
          <br />
          <input type="text" onfocus="clearTextbox(this)" value="[Jr]" style="width: 100px;" class="shadowBox grayText"/>
          Optional </p></td>
    </tr>
  </table></div>
<div class="darkGrayModuleHdr">
	Job Title & Role
</div>
<div class="grayModuleContent mainWellContent">
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
	</table>
</div>
<div class="darkGrayModuleHdr">
	Job Title & Role
</div>
<div class="grayModuleContent mainWellContent">
	<table cellpadding="0" cellspacing="0" width="650">
		<tr>
			<td width="325">
				<p>
					<label>
						User Name
					</label>
					<br />
					<input type="text" onfocus="clearTextbox(this)" value="[User Name]"
						style="width: 250px;" class="shadowBox grayText" />
				</p>
			</td>
		</tr>
	</table>
</div>
<div class="clearfix">
	<div class="formNavButtons">
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
