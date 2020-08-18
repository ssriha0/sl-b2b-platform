<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<h3>Wyatt Knox</h3>
<p class="paddingBtm">Use the fields below to edit this team member's status on ServiceLive. You may remove this team member entirely by clicking 'remove user' at the bottom of the page.</p>

<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">Role in the Marketplace</div>
<div class="grayModuleContent mainWellContent clearfix">
  <p>Will this person perform service on orders from the marketplace?</p>
  <p class="paddingBtm ">
    <input type="radio" name="r1" >
    Yes
    <input type="radio" name="r1" style="margin-left:20px;">
    No </p>
  
  <div class="clear"></div>
</div>
<div class="darkGrayModuleHdr">Personal Information</div>
<div class="grayModuleContent mainWellContent clearfix">
  <p>Please enter the personal information requested below. The Social Security number, which will be encrypted for security, will be used as the unique identifier for this service pro on the ServiceLive network. </p>
  <table cellpadding="0" cellspacing="0">
    <tr>
      <td> First Name<br />
        <input type="text" style="width: 230px;" onfocus="clearTextbox(this)" class="shadowBox grayText lockedField" value="[First Name]" />
      </td>
      <td width="50"></td>
      <td> Middle Name<br />
        <input type="text" style="width: 230px;" onfocus="clearTextbox(this)" class="shadowBox grayText lockedField" value="[Middle Name]" />
        optional </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td> Last Name<br />
        <input type="text" style="width: 230px;" onfocus="clearTextbox(this)" class="shadowBox grayText lockedField" value="[Last Name]" />
      </td>
      <td></td>
      <td> Suffix (Jr., II, etc.)<br />
        <input type="text" style="width: 110px;" onfocus="clearTextbox(this)" class="shadowBox grayText lockedField" value="[Suffix]" />
        optional </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td> Social Security Number<br />
        <input type="text" style="width: 35px;" onfocus="clearTextbox(this)" class="shadowBox grayText lockedField" value="###" maxlength="3" />
        -
        <input type="text" style="width: 25px;" onfocus="clearTextbox(this)" class="shadowBox grayText lockedField" value="##" maxlength="2"/>
        -
        <input type="text" style="width: 35px;" onfocus="clearTextbox(this)" class="shadowBox grayText lockedField" value="####" maxlength="4" />
      </td>
    </tr>
  </table>
  <br clear="all" />
</div>
<div class="darkGrayModuleHdr">Job Title &amp; Role</div>
<div class="grayModuleContent mainWellContent clearfix">
	<p>What is this person's job title and role within your company? More than one role can be assigned to each
person you register.</p>
  <div style="float: left;">
      <p>Role within company (Check all that apply.)</p>
      <table cellpadding="0" cellspacing="0">
        <tr>
          <td width="169"><p>
            <input type="checkbox" /> Owner/Principal</p>
            <p>
            <input type="checkbox" /> Manager</p>
            <p>
            <input type="checkbox" /> Administrator</p>
          </td>
          <td width="169">
            <p><input type="checkbox" /> Dispatcher/Scheduler</p>
            <p><input type="checkbox" /> Service Provider</p>
            <p><input type="checkbox" /> Other</p>
          </td>
        </tr>
      </table>
  </div>
  <div>
  	<p>Job Title</p>
    <input type="text" style="width: 230px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Job Title]" /> Optional
  </div>
  <br clear="all" />
</div>
<div class="darkGrayModuleHdr">Dispatch Address &amp; Coverage Area</div>
<div class="grayModuleContent mainWellContent clearfix">
  <p>The dispatch address helps determine the service pro's distance from the service location. It does not have to
be the same as your business address. The more areas your service providers cover, the more orders you have the
potential to receive. </p>
  <table cellpadding="0" cellspacing="0">
    <tr>
      <td style="padding-bottom:8px;"> Street Name<br />
        <input type="text" style="width: 366px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Address 1]" />
      </td>
      <td width="20"></td>
      <td> Apt. #<br />
        <input type="text" style="width: 70px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Apt]" />
      </td>
    </tr>
    <tr>
      <td><input type="text" style="width: 366px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Address 2]" />
      </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
  </table>
  <table cellpadding="0" cellspacing="0">
    <tr>
      <td width="150"> City<br />
        <input type="text" style="width: 130px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[City]" />
      </td>
      <td width="82"> State<br />
        <select class="grayText" onclick="changeDropdown(this)"  style="width:70px;">
        </select>
      </td>
      <td> Zip<br />
        <input type="text" onfocus="clearTextbox(this)" value="#####-####" style="width: 80px;" class="shadowBox grayText" maxlength="10"/>
      </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td> Geographical Range<br />
        <select class="grayText" onclick="changeDropdown(this)"  style="width:125px;">
        	<option>Select One</option>
        </select>
      </td>
    </tr>
  </table>
  <br clear="all" />
</div>
<div class="darkGrayModuleHdr">Billing Information</div>
<div class="grayModuleContent mainWellContent clearfix">
  <p>Enter your service pro's hourly rate. Buyers will be able to see the preferred rate, but the service pro will still be able to accept orders that have a fixed spend limit or lower hourly rate. </p>
  Preferred Billing Rate
  <p> $
    <input type="text" style="width: 80px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[###.##]" />
    Per Hour </p>
  <br clear="all" />
</div>
<div class="darkGrayModuleHdr">Work Schedule</div>
<div class="grayModuleContent mainWellContent clearfix">
  <p>Enter the hours the service provider is available to work.</p>
  <table cellpadding="0" cellspacing="0">
    <tr height="24">
      <td width="112">Monday</td>
      <td width="117"><input type="radio" checked="checked"/>
        24 hrs/day</td>
      <td width="65"><input type="radio" />
        From</td>
      <td width="101"><select class="grayText" onclick="changeDropdown(this)"  style="width:85px;">
        </select></td>
      <td width="27">to</td>
      <td width="106"><select class="grayText" onclick="changeDropdown(this)"  style="width:85px;">
        </select></td>
      <td><input type="radio" />
        not available</td>
    </tr>
    <tr height="24">
      <td>Tuesday</td>
      <td><input type="radio"  checked="checked" />
        24 hrs/day</td>
      <td><input type="radio" />
        From</td>
      <td><select class="grayText" onclick="changeDropdown(this)"  style="width:85px;">
        </select></td>
      <td>to</td>
      <td><select class="grayText" onclick="changeDropdown(this)"  style="width:85px;">
        </select></td>
      <td><input type="radio" />
        not available</td>
    </tr>
    <tr height="24">
      <td>Wednesday</td>
      <td><input type="radio"  checked="checked" />
        24 hrs/day</td>
      <td><input type="radio" />
        From</td>
      <td><select class="grayText" onclick="changeDropdown(this)"  style="width:85px;">
        </select></td>
      <td>to</td>
      <td><select class="grayText" onclick="changeDropdown(this)"  style="width:85px;">
        </select></td>
      <td><input type="radio" />
        not available</td>
    </tr>
    <tr height="24">
      <td>Thursday</td>
      <td><input type="radio"  checked="checked" />
        24 hrs/day</td>
      <td><input type="radio" />
        From</td>
      <td><select class="grayText" onclick="changeDropdown(this)"  style="width:85px;">
        </select></td>
      <td>to</td>
      <td><select class="grayText" onclick="changeDropdown(this)"  style="width:85px;">
        </select></td>
      <td><input type="radio" />
        not available</td>
    </tr>
    <tr height="24">
      <td>Friday</td>
      <td><input type="radio"  checked="checked" />
        24 hrs/day</td>
      <td><input type="radio" />
        From</td>
      <td><select class="grayText" onclick="changeDropdown(this)"  style="width:85px;">
        </select></td>
      <td>to</td>
      <td><select class="grayText" onclick="changeDropdown(this)"  style="width:85px;">
        </select></td>
      <td><input type="radio" />
        not available</td>
    </tr>
    <tr height="24">
      <td>Saturday</td>
      <td><input type="radio"  checked="checked" />
        24 hrs/day</td>
      <td><input type="radio" />
        From</td>
      <td><select class="grayText" onclick="changeDropdown(this)"  style="width:85px;">
        </select></td>
      <td>to</td>
      <td><select class="grayText" onclick="changeDropdown(this)"  style="width:85px;">
        </select></td>
      <td><input type="radio" />
        not available</td>
    </tr>
    <tr height="24">
      <td>Sunday</td>
      <td><input type="radio"  checked="checked" />
        24 hrs/day</td>
      <td><input type="radio" />
        From</td>
      <td><select class="grayText" onclick="changeDropdown(this)"  style="width:85px;">
        </select></td>
      <td>to</td>
      <td><select class="grayText" onclick="changeDropdown(this)"  style="width:85px;">
        </select></td>
      <td><input type="radio" />
        not available</td>
    </tr>
  </table>
  <br clear="all" />
</div>
<div class="darkGrayModuleHdr">User Name</div>
<div class="grayModuleContent mainWellContent clearfix">
  <p>Create a unique name that your service pro can use to log in to ServiceLive to accept and manage orders. </p>
  <p> User Name<br />
    <input type="text" style="width: 161px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[User Name]" />
  </p>
  <br clear="all" />
</div>
<div class="darkGrayModuleHdr">Service Provider Photo</div>
<div class="grayModuleContent mainWellContent clearfix">
  <p>Please provide a photograph of the service pro, if available. This photograph will be used to identify the
provider at the service location. The photo should be no larger than 84 pixels by 123 pixels. Individual file
size is limited to 1MB. If you would like to add a new photo, remove the existing photo first.</p>
 
    <div style="float: left;"><img src="${staticContextPath}/images/sp_registration/sp_photo.gif" width="100" height="139" alt="" /></div>
    <div style="float: left; padding: 0px 0px 0px 20px;">
      <p><strong>Select file to upload</strong></p>
      <input type="text" style="width: 370px;" class="shadowBox grayText" value="[Select file]" onfocus="clearTextbox(this)" />
      <br />
      <br />
      <div>
        <table style="border:1px #9f9f9f solid;" cellpadding="0" cellspacing="0" width="378">
          <tr style="background:#4cbcdf;color:#ffffff;height:22px;">
            <td style="padding-top:5px;padding-left:10px;" width="275"><strong>File Name</strong></td>
            <td style="padding-top:5px;"><strong>File Size</strong></td>
          </tr>
          <tr>
            <td style="padding:10px 0px 10px 10px;"><input type="checkbox" />
              Dparker.jpg</td>
            <td style="padding:10px 0px 0px 0px;">725kb</td>
          </tr>
        </table>
      </div>
      <br />
      <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="20" style="background-image:url(${staticContextPath}/images/btn/view.gif);"class="btnBevel" />
      <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="20" style="background-image:url(${staticContextPath}/images/btn/remove.gif);"class="btn20Bevel" />
    </div>
    <div style="float: left;padding:16px 0px 0px 10px;">
      <p>
        <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="20" style="background-image:url(${staticContextPath}/images/btn/browse.gif);"class="btnBevel" />
        <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="61" height="20" style="background-image:url(${staticContextPath}/images/btn/attach.gif);"class="btn20Bevel" />
      </p>
      <p style="line-height: 20px;"> Preferred file types include:<br />
        JPG | PDF | GIF </p>
    </div>
  </div>

<div class="clearfix">
  <div class="formNavButtons">
    <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="50" height="20" style="background-image:url(${staticContextPath}/images/btn/save.gif);"  class="btn20Bevel" /> 

    <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="50" height="20" style="background-image:url(${staticContextPath}/images/btn/next.gif);"  class="btn20Bevel" />
  </div>
  <div class="bottomRightLink"><a href="">Cancel</a></div>
</div>
