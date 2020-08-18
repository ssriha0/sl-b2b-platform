<jsp:include page="/jsp/public/common/commonIncludes.jsp" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>Contact ServiceLive</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/modules.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/contact.css">
<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/nav.js"></script>
</head>
<body class="tundra">
<div id="page_margins">
  <div id="page">
    <div class="contactPopup">
      <div class="header"><img src="${staticContextPath}/images/contact/popupHdrText_contactUs.gif" alt="Contact Us" title="Contact Us" /></div>
      <div class="mainWellContent">
        <div class="colRight235">
          <!-- #INCLUDE FILE="html_sections/modules/corpHQ.html" -->
          <jsp:include page="/jsp/public/contact_us/body/sections/modules/corpHQ_top.jsp" />          
        </div>
        <p class="paddingBtm"> Share your questions or comments with ServiceLive support. We'll respond to all e-mails as soon as possible. In the meantime, most questions can be answered by visiting our <a href="">FAQ</a> page.</p>
        <table cellspacing="0" cellpadding="0">
          <tbody>
            <tr>
              <td width="200"><p>
                  <label>First Name</label>
                  <br/>
                  <input type="text" value="[First Name]" class="shadowBox grayText" onfocus="clearTextbox(this)" style="width: 150px;"/>
                </p></td>
              <td width="150"><p>
                  <label>Last Name</label>
                  <br/>
                  <input type="text" value="[Last Name]" class="shadowBox grayText" onfocus="clearTextbox(this)" style="width: 145px;"/>
                </p></td>
            </tr>
            <tr>
              <td colspan="2"><p>
                  <label>E-mail Address</label>
                  <br/>
                  <input type="text" value="[E-mail Address]" class="shadowBox grayText" onfocus="clearTextbox(this)" style="width: 345px;"/>
                </p></td>
            </tr>
            <tr>
              <td><p>Phone Number<br/>
                  <input type="text" maxlength="3" value="###" class="shadowBox grayText" onfocus="clearTextbox(this)" style="width: 35px;"/>
                  -
                  <input type="text" maxlength="3" value="###" class="shadowBox grayText" onfocus="clearTextbox(this)" style="width: 35px;"/>
                  -
                  <input type="text" maxlength="4" value="####" class="shadowBox grayText" onfocus="clearTextbox(this)" style="width: 45px;"/>
                </p></td>
              <td><p>Type<br/>
                  <select style="width: 100%; margin-bottom: 1px;" class="grayText" onclick="changeDropdown(this)">
                    <option>Select One</option>
                  </select>
                </p></td>
            </tr>
            <tr>
              <td colspan="2"><p>Category<br/>
                  <select style="width: 100%; margin-bottom: 1px;" class="grayText" onclick="changeDropdown(this)">
                    <option>Select One</option>
                  </select>
                </p></td>
            </tr>
            <tr>
              <td colspan="2"><p>
                  <label>Subject</label>
                  <br/>
                  <input type="text" value="[Subject]" class="shadowBox grayText" onfocus="clearTextbox(this)" style="width: 345px;"/>
                </p></td>
            </tr>
            <tr>
              <td colspan="2"><div class="clearfix" style="padding-top: 4px;">
                  <div class="floatLeft">
                    <label>Comments</label>
                  </div>
                  <div class="floatRight" align="right">1,000 characters left</div>
                </div>
                <textarea class="shadowBox grayText" style="width: 347px; height: 100px;" onfocus="clearTextbox(this)">[Comments]</textarea>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="clearfix" style="width: 375px;">
        <div class="formNavButtons" style="width: 150px;">
          <input width="72" type="image" height="22" class="btnBevel" style="background-image: url(${staticContextPath}/images/btn/submit.gif);" src="${staticContextPath}/images/common/spacer.gif"/>
        </div>
        <div class="bottomRightLink"><a href="">Cancel</a></div>
      </div>
    </div>
    <br>
    <br>
    <div class="contactPopup">
      <div class="header"><img src="${staticContextPath}/images/contact/popupHdrText_contactUs.gif" alt="Contact Us" title="Contact Us" /></div>
      <div class="mainWellContent clearfix">
        <div class="colRight235">
          <!-- #INCLUDE FILE="html_sections/modules/corpHQ.html" -->
        </div>
        <div class="colLeft711" style="width: 330px;">
        <p>Thank you!</p>
          <p>Your e-mail has been sent and we will be responding to it soon. We'll respond to all e-mails as soon as possible.</p>
          <p>In the meantime, most questions can be answered by visiting our <a href="">FAQ</a> page.</p>
          <p style="padding-top:140px"><a href="">Close</a>
        </div>
      </div>
    </div>
    <br>
    <br>
  </div>
  <!-- END CENTER   -->
</div>
</body>
</html>
