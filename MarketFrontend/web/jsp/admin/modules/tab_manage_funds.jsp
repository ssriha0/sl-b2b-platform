<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<div class="darkGrayModuleHdr">ServiceLive Administrative Account Information</div>
<div class="grayModuleContent mainWellContent clearfix">
  <table cellpadding="0" cellspacing="0">
    <tr>
      <td width="150"> Account Number: </td>
      <td width="150" align="right">SL123456789000</td>
    </tr>
    <tr>
      <td> Account Status: </td>
      <td align="right">Active</td>
    </tr>
    <tr>
      <td>Available Balance:</td>
      <td align="right">$932,160.00</td>
    </tr>
  </table>
</div>
<div class="darkGrayModuleHdr">Transfer Funds</div>
<div class="grayModuleContent mainWellContent clearfix">
  <table cellpadding="0" cellspacing="0">
    <tr>
      <td width="350"><p>
          <label>Transfer Amount</label>
          <br />
          $
          <input type="text" onfocus="clearTextbox(this)" value="[###.##]" style="width: 200px;" class="shadowBox grayText"/>
        </p></td>
      <td><p>
          <label>Transfer Reason</label>
          <br />
          <select class="grayText" onclick="changeDropdown(this)"  style="width:200px;">
            <option>Select Reason Code</option>
          </select>
        </p></td>
    </tr>
  </table>
</div>
<div class="darkGrayModuleHdr">Transfer to Member Account</div>
<div class="grayModuleContent mainWellContent clearfix">
  <p>
    <label>Member ID Number</label>
    <br />
    <input type="text" onfocus="clearTextbox(this)" value="[###.##]" style="width: 200px;" class="shadowBox grayText"/>
    <a href="">Lookup</a> </p>
</div>
<div class="clearfix">
  <div class="formNavButtons">
    <input width="111" type="image" height="20" class="btn20Bevel" style="background-image: url(${staticContextPath}/images/btn/transferFunds.gif);" src="${staticContextPath}/images/common/spacer.gif"/>
  </div>
</div>
<p>[Consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim
  ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel
  eum iriure dolor in hendrerit in vulputate velit esse.]</p>
