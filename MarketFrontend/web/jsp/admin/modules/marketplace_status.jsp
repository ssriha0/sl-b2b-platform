<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<div dojoType="dijit.TitlePane" title="Marketplace Status" class="dijitTitlePaneSubTitle">
  <p>
    <label>Overall Company Status</label>
    <br />
    <select style="width: 200px;" onclick="changeDropdown(this)" class="grayText">
      <option>Select Status</option>
    </select>
  </p>
  <p>
    <label>Status Duration</label>
    <br />
    <input type="radio" name="r1" checked="checked" class="radioOffset" />
    Indefinite Period<br />
    <input type="radio" name="r1" class="radioOffset" />
    <select style="width: 180px;" onclick="changeDropdown(this)" class="grayText">
      <option>Select Time Period</option>
    </select>
  
  </p>
  <p><label>Notes</label><br />
<textarea class="shadowBox grayText" style="width: 200px;" onfocus="clearTextbox(this)">Enter Text</textarea>
  
  </p>
  <p align="right">
    <input width="98" type="image" height="20" src="${staticContextPath}/images/common/spacer.gif" style="background-image: url(${staticContextPath}/images/btn/updateStatus.gif);" class="btn20Bevel"/>
  </p>
</div>
