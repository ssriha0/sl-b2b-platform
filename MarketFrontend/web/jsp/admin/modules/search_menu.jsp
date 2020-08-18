<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<div class="rightRailWidget">
  <div class="darkGrayModuleHdr">Search Menu</div>
  <div class="grayModuleContent">
    <p>
      <label>Service Category</label>
      <select class="grayText" onclick="changeDropdown(this)" style="width: 200px;">
        <option>Select One</option>
      </select>
    </p>
    <p>
      <label>Overall Rating</label>
      <select class="grayText" onclick="changeDropdown(this)" style="width: 200px;">
        <option>Select One</option>
      </select>
    </p>
    <p>
      <label>Date Range</label>
      <select class="grayText" onclick="changeDropdown(this)" style="width: 200px;">
        <option>Select One</option>
      </select>
    </p>
    <p align="right">
      <input width="76" type="image" height="20" class="btnBevel" style="background-image: url(${staticContextPath}/images/btn/search.gif);" src="${staticContextPath}/images/common/spacer.gif"/>
    </p>
  </div>
</div>
