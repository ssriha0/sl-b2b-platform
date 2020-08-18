<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<h3 class="paddingBtm">[Wyatt Incorporated]</h3>
<p>Use the fields below to edit your business and contact information. Some fields are locked for your
protection. If you need to make changes, contact your ServiceLive administrator.</p>
<div class="clear"></div>
<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">Business Information</div>
<div class="grayModuleContent mainWellContent clearfix">
    <div>
    	<table cellpadding="0" cellspacing="0">
        	<tr>
            	<td width="350">
                <p>
                	<label>Legal Business Name</label><br />
                    <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText lockedField" value="[Business Name]" style="width:200px;" /></p>
                </td>
               	<td><p>
                	<label>Doing Business As (DBA)</label><br />
                    <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText lockedField" value="[DBA Name]" style="width:200px;" /></p>
                </td>
             </tr>
           	 <tr>
            	<td><p>
                	<label> Business Phone</label><br />
                    <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="###" style="width:30px;" maxlength="3" /> -  <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="###" style="width:30px;" maxlength="3" /> -  <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="####" style="width:40px;" maxlength="4" /> Ext:  <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="####" style="width:40px;" maxlength="5" /></p>
                </td>
               	<td><p>
                	<label>Business Fax</label><br />
                    <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="###" style="width:30px;" maxlength="3" /> -  <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="###" style="width:30px;" maxlength="3" /> -  <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="####" style="width:40px;" maxlength="4" /> Optional</p>
                </td>
             </tr>
			 <tr>
            	<td><p>
                	<label>Taxpayer ID (EIN or SSN)</label><br />
                    <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText lockedField" value="[#########]" style="width:200px;" /></p>
                </td>
               	<td><p>
                	<label>Dun &amp; Bradstreet (DUNS) Number</label><br />
                    <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Alphanumeric Field]" style="width:200px;" /> Optional</p>
                </td>
             </tr>
			 <tr>
            	<td><p>
                	<label>Business Structure</label><br />
                    <select class="grayText lockedField" onclick="changeDropdown(this)"  style="width:208px;"><option>Select One</option></select></p>
                </td>
               	<td><p>
                	<label>Business Started <span class="req">*</span></label><br />
                    <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText lockedField" value="[mm/yyyy]" style="width:100px;" /></p>
                </td>
             </tr>
              <tr>
            	<td><p>
                	<label>Is the business foreign owned?</label><br />
                    <input type="radio" /> Yes <input type="radio" /> No</p>
                </td>
               	<td><p>
                	<label>Foreign Owned Percentage</label><br />
                    <select class="grayText" onclick="changeDropdown(this)"  style="width:208px;"><option>Select One</option></select></p>
                </td>
             </tr>
             <tr>
            	<td><p>
                	<label>Size of Company</label><br />
                    <select class="grayText" onclick="changeDropdown(this)"  style="width:208px;"><option>Select One</option></select></p>
                </td>
               	<td><p>
                	<label>Annual Sales Revenue</label><br />
                    <select class="grayText" onclick="changeDropdown(this)"  style="width:208px;"><option>Select One</option></select></p>
                </td>
             </tr>
			 <tr>
            	<td><p>
                	<label>Industry</label><br />
                    <select class="grayText" onclick="changeDropdown(this)"  style="width:208px;"><option>Select One</option></select></p>
                </td>
               	<td><p>
                	<label>Website Address</label><br />
                    <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Alphanumeric Field]" style="width:200px;" /> Optional</p>
                </td>
             </tr>
        </table>
    </div>
    <p>A well-written description of your company can be a powerful sales tool on ServiceLive. You can use up to
1,000 words to describe your company in the field below. Click 'edit' to make changes or to update information.</p>
	<textarea style="width: 600px;" class="shadowBox grayText" onfocus="clearTextbox(this)">[Example] Since 1997, ABC Company has been the city's most reliable installer of plasma screen
televisions. All of our service professionals are ACME certified, with an average of 7 years of professional
experience each. Courtesy is our hallmark and exceptional service is our goal.</textarea>
</div>
<div class="darkGrayModuleHdr">Business Address</div>
<div class="grayModuleContent mainWellContent clearfix">
	<table cellpadding="0" cellspacing="0">
        <tr>
            <td width="220"><p>
               Street Name<br />
               <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Address 1]" style="width:200px;" /></p>
           </td>
           <td><p>
           		Apt. #<br />
               <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Apt]" style="width:40px;" /></p>
           </td>
       </tr>
       <tr>
       		<td><p>
               <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Address 2]" style="width:200px;" /></p>
           </td>
       </tr>
       <tr>
            <td><p>
               City<br />
               <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Address 1]" style="width:200px;" /><p>
           </td>
           <td><p>
           		State<br />
               <select class="grayText" onclick="changeDropdown(this)"  style="width:40px;"><option>AL</option></select><p>
           </td>
           <td><p>
           		ZIP<br />
               <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[00000]" style="width:50px;" /><p>
           </td>
       </tr>
    </table>
</div>
<div class="darkGrayModuleHdr">Mailing Address</div>
<div class="grayModuleContent mainWellContent clearfix">
<p><input type="checkbox" /> The Mailing Address is the same as the Business Address</p>
	<table cellpadding="0" cellspacing="0">
        <tr>
            <td width="220"><p>
               Street Name<br />
               <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Address 1]" style="width:200px;" /></p>
           </td>
           <td><p>
           		Apt. #<br />
               <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Apt]" style="width:40px;" /></p>
           </td>
       </tr>
       <tr>
       		<td><p>
               <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Address 2]" style="width:200px;" /></p>
           </td>
       </tr>
       <tr>
            <td><p>
               City<br />
               <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Address 1]" style="width:200px;" /></p>
           </td>
           <td><p>
           		State<br />
               <select class="grayText" onclick="changeDropdown(this)"  style="width:40px;"><option>AL</option></select></p>
           </td>
           <td><p>
           		ZIP<br />
               <input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[00000]" style="width:50px;" /></p>
           </td>
       </tr>
    </table>
</div>
<div class="darkGrayModuleHdr">Primary Contact Information - Administrator</div>
 <div class="grayModuleContent mainWellContent clearfix">
 	<p>The following person is registered as the ServiceLive administrator for your company. Contact
ServiceLive support if you would like to make another person your administrator.</p>
	<table cellpadding="0" cellspacing="0">
    	<tr>
        	<td width="350">
				<label>Role Within Company (Check all that apply)</label>
                <table cellpadding="0" cellspacing="0">
                	<tr>
                    	<td width="150"><p>
                        	<input type="checkbox" /> Owner/Principal</p>
                            <p><input type="checkbox" /> Manager</p>
                            <p><input type="checkbox" /> Administrator</p>
                        </td>
                        <td>
                        	<p><input type="checkbox" /> Dispatcher/Scheduler</p>
                            <p><input type="checkbox" /> Service Provider</p>
                            <p><input type="checkbox" /> Other</p>
                        </td>
                    </tr>
                </table>
			</td>
            <td><p>
            	<label>Job Title</label><br />
				<input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Job Title]" style="width:200px;" /></p>
            </td>
        </tr>
        <tr><td>&nbsp;</td></tr>
        <tr>
        	<td><p>
				<label>First Name</label><br />
				<input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText lockedField" value="[First Name]" style="width:200px;" /></p>
			</td>
            <td><p>
				<label>Last Name</label><br />
				<input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText lockedField" value="[Last Name]" style="width:200px;" /></p>
			</td>
        </tr>
        <tr>
        	<td><p>
				<label>E-mail</label><br />
				<input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[E-mail Address]" style="width:200px;" /></p>
			</td>
			<td><p>
				<label>Confirm E-mail</label><br />
				<input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[E-mail Address]" style="width:200px;" /></p>
			</td>
        </tr>
       <tr>
        	<td><p>
				<label>Alternate E-mail Address</label><br />
				<input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Alternate E-mail Address]" style="width:200px;" /></p>
			</td>
			<td><p>
				<label>Confirm Alternate E-mail Address</label><br />
				<input type="text" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Alternate E-mail Address]" style="width:200px;" /></p>
			</td>
        </tr>
    </table>
</div>

<div class="clearfix">
        <div class="formNavButtons">
         <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="50" height="20" style="background-image:url(${staticContextPath}/images/btn/save.gif);"class="btn20Bevel" />
        </div>
        <div class="bottomRightLink"><a href="">Cancel</a></div>
      </div>
