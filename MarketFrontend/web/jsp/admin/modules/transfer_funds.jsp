<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<div dojoType="dijit.TitlePane" title="Transfer Funds" class="dijitTitlePaneSubTitle">
  <p>
    <label>Transfer Amount</label>
    <br/>
    <input type="text" style="width: 200px;" onclick="clearTextbox(this)" class="shadowBox grayText">
  </p>
  <p>
    <label>Transfer to Member Account
Member ID #</label><br>

   
     <input type="text" style="width: 130px;" onclick="clearTextbox(this)" class="shadowBox grayText">&nbsp;&nbsp;<a href="">Lookup</a>
     </p>
     <p><label>Transfer Reason</label>
    <select style="width: 200px;" onclick="changeDropdown(this)" class="grayText">
      <option>Select Reason Code</option>
    </select>
  </p>
  
  <p align="right"><input width="101" type="image" height="20" src="${staticContextPath}/images/common/spacer.gif" style="background-image: url(${staticContextPath}/images/btn/transferFunds.gif);" class="btn20Bevel"/></p></div>
