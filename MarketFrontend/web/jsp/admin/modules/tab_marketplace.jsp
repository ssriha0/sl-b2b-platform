<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<h3 class="paddingBtm">Wyatt Knox</h3>
<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">ServiceLive Activities &amp; Permissions</div>
<div class="grayModuleContent mainWellContent clearfix">
	  <p>As the administrator and primary contact, you have access to all ServiceLive activities. You'll be able to define access and user preferences for each user you add to your account.</p>
	  <p>Define user access to your ServiceLive account from this page. You'll be able to define access and user preferences for each user you add to your account.</p>
      <div class="grayModuleHdr">General Service Order Activities</div>
<div class="grayModuleContent mainWellContent">
	  <p class="paddingBtm">
		Check all that apply.</p>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="200">
                <input type="checkbox" /> Accept Service Order<br />
					<br />
					<input type="checkbox" /> Release Service Order<br />
					<br />
					<input type="checkbox" /> Contact ServiceLive Support
				</td>
				<td>
					
					<input type="checkbox" /> Close Service Order<br /><br />

					<input type="checkbox" /> Reconcile Problem/Enter Issue Resolution<br />
				</td>
			</tr>
		</table>
        </div>
		
	      <div class="grayModuleHdr">Financial &amp; Banking Activities</div>
<div class="grayModuleContent mainWellContent">
	<p class="paddingBtm">
		Check all that apply.</p>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="200">
					<input type="checkbox" /> Add/Edit/Delete Bank Account<br />
					<br />
					<input type="checkbox" /> Add Funds<br />
				</td>
				<td>
					<input type="checkbox" /> Withdraw Payment<br />
					
				</td>
			</tr>
		</table></div>
	
    	      <div class="grayModuleHdr">Administrator Activities</div>
<div class="grayModuleContent mainWellContent">
	<p class="paddingBtm">
	
		Check all that apply.</p>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="200">
					<input type="checkbox" /> Add/Edit/Delete Users<br />
					<br />
					<input type="checkbox" /> Activate/Deactivate Users<br />
					<br />
					
					<input type="checkbox" /> Assign Activities to Users<br />
				</td>
				<td>
					<input type="checkbox" /> Manage Company Profile<br />
					<br />
					<input type="checkbox" /> Access Custom Service Order Reports<br />
					
				</td>
			</tr>
		</table>
        </div>
	
	  <div class="clear"></div>
</div>
<div class="darkGrayModuleHdr">Communication Preferences</div>
<div class="grayModuleContent mainWellContent clearfix">
		<p>
			Please enter contact information below for the service pro you are registering. All service providers must have at least two ways by which they can be reached.  
		</p>
		<div style="float: left;width:170px;">
			Business Phone<br />
			<input type="text" style="width: 35px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="###" maxlength="3" /> - <input type="text" style="width: 35px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="###" maxlength="3" /> - <input type="text" style="width: 45px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="####" maxlength="4" />
		</div>
		<div>
			Extension<br />
			<input type="text" style="width: 50px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="#####"  maxlength="5" />
		</div>
		<br clear="all" />
		<div style="width:200px;">
			Mobile Phone<br />
			<input type="text" style="width: 35px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="###" maxlength="3" /> - <input type="text" style="width: 35px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="###" maxlength="3" /> - <input type="text" style="width: 45px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="####" maxlength="4" />
		</div>
		<br clear="all" />
		<div style="float: left;width:230px;">
			E-mail Address<br />
			<input type="text" style="width: 200px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Email Address]" />
		</div>
		<div>
			Confirm E-mail Address<br />
			<input type="text" style="width: 200px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Confirm Email Address]" />
		</div>
		<br clear="all" />
		<div style="float: left;width:230px;">
			Alternate E-mail Address<br />
			<input type="text" style="width: 200px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Alternate Email Address]" />
		</div>
		<div>
			Confirm Alternate E-mail Address<br />
			<input type="text" style="width: 200px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Confirm Alternate Email Address]" />
		</div>
		<br clear="all" />
		<div style="float: left;width:230px;">
			SMS Address<br />
			<input type="text" style="width: 35px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="###" maxlength="3" /> - <input type="text" style="width: 35px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="###" maxlength="3" /> - <input type="text" style="width: 45px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="####" maxlength="4" /> Optional
		</div>
		<div>
			Confirm SMS Address<br />
			<input type="text" style="width: 35px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="###" maxlength="3" /> - <input type="text" style="width: 35px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="###" maxlength="3" /> - <input type="text" style="width: 45px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="####" maxlength="4" /> Optional
		</div>
		<br clear="all" />
		<p>
		    Text messages are sent whenever you receive a posted Service Order from ServiceLive buyers. Frequency of text messages
            varies based on the number of Service Orders received. Message and data rates may apply. Must be at least 18 years old to
            participate.  
		</p>
		<br clear="all" />
		<p>
			To get help by text message, text HELP or contact ServiceLive support. To stop receiving service orders by text message, text
			STOP or change your Secondary Contact Method preference below.
		</p>
		<br clear="all" />
		<p>
			Service orders will be sent to the primary e-mail address listed above. If you'd also like service orders to be sent by a secondary contact method, please select your preference from the drop down menu. 
		</p>
		<div>
			Secondary Contact Method<br />
			<select class="grayText" onclick="changeDropdown(this)"  style="width: 200px;"></select>
		</div>
		<br clear="all" />
		<p>
			What is the best method for buyers to contact the provider with questions or information about a specific order? This information will not be revealed until the provider has accepted the order.
		</p>
		<div style="float: left;width:230px;">
			Primary Contact Method<br />
			<select class="grayText" onclick="changeDropdown(this)"  style="width: 200px;"></select>
		</div>
		<div>
			Secondary Contact Method<br />
			<select class="grayText" onclick="changeDropdown(this)"  style="width: 200px;"></select>
		</div>
		<br clear="all" />
</div>
<div class="clearfix">
  <div class="formNavButtons">
	<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="74" height="20" style="background-image:url(../../images/btn/previous.gif);" class="btn20Bevel" />
	<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="51" height="20" style="background-image:url(../../images/btn/save.gif);" class="btn20Bevel" /> 
	<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="51" height="20" style="background-image:url(../../images/btn/next.gif);" class="btn20Bevel" /> </div>
    <div class="bottomRightLink"><a href="index.htm">Cancel</a></div>
</div>



