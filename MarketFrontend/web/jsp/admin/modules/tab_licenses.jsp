<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<h3 class="paddingBtm">Wyatt Knox</h3>
<h4>Individual Licenses &amp; Certifications</h4>
<p>While they are not always required, licenses and certifications can give your service pro a competitive
  advantage by showing that he or she has taken advanced training or has government authorizations. Add as
  many credentials as you would like or click 'next' to move to the next page. </p>
<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">Licenses &amp; Certifications on File</div>
<div class="grayModuleContent mainWellContent clearfix">
  <p>This service pro does not have any licenses or certifications on file.</p>
  <p>The credentials you've uploaded are listed below. Click 'add credential' to add more or 'edit' to make changes to the credentials you have listed. Certifications that have been verified by ServiceLive are indicated in the right column. </p>
  <table class="scrollerTableHdr licensesTableHdr" cellpadding="0" cellspacing="0">
    <tr>
      <td class="column1"><img src="${staticContextPath}/images/common/spacer.gif" width="60" height="20" title="spacer" /></td>
      <td class="column2"><a href="index.htm" class="sortGridColumnUp">Credential Type</a></td>
      <td class="column3"><a href="index.htm" class="sortGridColumnUp">Name</a></td>
      <td class="column4"><a href="index.htm" class="sortGridColumnUp">Expiration</a></td>
      <td class="column5"><a href="index.htm" class="sortGridColumnUp2">Verified by ServiceLive</a></td>
    </tr>
  </table>
  <table class="licensesTable" cellpadding="0" cellspacing="0">
    <tr>
      <td class="column1"><input type="image" src="${staticContextPath}/images/common/spacer.gif" width="48" height="20" style="background-image:url(${staticContextPath}/images/btn/edit.gif);"  class="btn20Bevel" /></td>
      <td class="column2">Certification</td>
      <td class="column3">Microsoft Certified Partner</td>
      <td class="column4">10/02/2008</td>
      <td class="column5">N/A</td>
    </tr>
    <tr>
      <td class="column1"><input type="image" src="${staticContextPath}/images/common/spacer.gif" width="48" height="20" style="background-image:url(${staticContextPath}/images/btn/edit.gif);"  class="btn20Bevel" /></td>
      <td class="column2">License</td>
      <td class="column3">Professional Engineering Firm</td>
      <td class="column4">10/02/2008</td>
      <td class="column5">N/A</td>
    </tr>
  </table>
  <p>
    <input type="checkbox" />
    I do not wish to add any licenses or certifications at this time.</p>
  <p>
    <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="103" height="20" style="background-image:url(${staticContextPath}/images/btn/addCredential.gif);"class="btn20Bevel" />
  </p>
  <div class="clear"></div>
</div>
<br clear="all" />
<div class="darkGrayModuleHdr">Licenses &amp; Certification Information</div>
<div class="grayModuleContent mainWellContent clearfix">
  <p>Where were you issued your certification and when will it expire? Any confidential information that you share will be used for verification purposes only.</p>
  <table cellpadding="0" cellspacing="0">
    <tr>
      <td width="220"><p>Type of Credential<br />
        <select class="grayText" onclick="changeDropdown(this)"  style="width:200px;">
          <option value="">Select One</option>
        </select></p>
      </td>
      <td><p>Category<br />
        <select class="grayText" onclick="changeDropdown(this)"  style="width:200px;">
          <option value="">Select One</option>
        </select></p>
      </td>
    </tr>
    <tr>
      <td colspan="2"><p> Name of License or Certification<br />
        <input type="text" style="width: 414px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Name of License or Certification]" /></p>
      </td>
    </tr>
    <tr>
      <td width="221"><p> Issuer of Credential<br />
        <input type="text" style="width: 191px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Issuer of Credential]" /></p>
      </td>
      <td><p> Credential Number<br />
        <input type="text" style="width: 191px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[Credential Number]" /></p>
      </td>
    </tr>
    <tr>
      <td width="221"><p> City (If applicable)<br />
        <input type="text" style="width: 191px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[City]" /></p>
      </td>
      <td><p> State (If applicable)<br />
        <select class="grayText" onclick="changeDropdown(this)"  style="width:40px;">
          <option value="">AL</option>
        </select></p>
      </td>
    </tr>
    <tr>
      <td width="221"><p> County (If applicable)<br />
        <input type="text" style="width: 191px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[County]" /></p>
      </td>
    </tr>
    <tr>
      <td width="221"><p> Issue Date<br />
        <input type="text" style="width: 101px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[MM/DD/YYYY]" /></p>
      </td>
      <td><p> Expiration Date (if applicable)<br />
        <input type="text" style="width: 101px;" onfocus="clearTextbox(this)" class="shadowBox grayText" value="[MM/DD/YYYY]" /></p>
      </td>
    </tr>
  </table>
</div>
<div class="darkGrayModuleHdr">Upload Credential Document</div>
<div class="grayModuleContent mainWellContent clearfix">
  <p>Please attach an electronic copy of the credential, if available. Credential details will be used for verification purposes and will not be posted online. You may upload a maximum of two files per license or certification. Each individual file is limited to 2 MB. </p>
  <div>
    <div style="float: left;">
      <p>
        <label>Select file to upload</label>
        <br />
        <input type="text" style="width: 170px;" class="shadowBox grayText" onfocus="clearTextbox(this)" value="[Select file]" />
        <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="20" style="background-image:url(${staticContextPath}/images/btn/browse.gif);"class="btnBevel uploadBtn" />
        <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="61" height="20" style="background-image:url(${staticContextPath}/images/btn/attach.gif);"class="btn20Bevel uploadBtn" />
      </p>
      <br clear="all" />
      <div class="filetypes" style="margin-right: 120px; _margin-right: 60px; ">
        <p>Preferred file types include:<br />
          JPG | PDF | DOC | GIF</p>
      </div>
      <div>
        <table style="border:1px #9f9f9f solid;" cellpadding="0" cellspacing="0" width="378">
          <tr style="background:#4cbcdf;color:#ffffff;height:22px;">
            <td style="padding-left:10px;" width="275"><strong>File Name</strong></td>
            <td><strong>File Size</strong></td>
          </tr>
          <tr>
            <td style="padding:10px 0px 10px 10px;"><input type="checkbox" />
              License01.pdf</td>
            <td>725kb</td>
          </tr>
        </table>
      </div>
      <br />
      <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="20" style="background-image:url(${staticContextPath}/images/btn/viewOff.gif);"  />
      <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="20" style="background-image:url(${staticContextPath}/images/btn/removeOff.gif);"  />
    </div>
  </div>
</div>
<div class="clearfix">
  <div class="formNavButtons">
    <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="74" height="20" style="background-image:url(${staticContextPath}/images/btn/previous.gif);"class="btn20Bevel" />
    <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="51" height="20" style="background-image:url(${staticContextPath}/images/btn/save.gif);" class="btn20Bevel" />
    <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="51" height="20" style="background-image:url(${staticContextPath}/images/btn/next.gif);"class="btn20Bevel" />
  </div>
  <div class="bottomRightLink"><a href="index.htm">Cancel</a></div>
</div>
