<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!-- Right pane/ rail -->
<div class="colRight255" style="padding: 0;">

<a href="html_sections/modules/order_express_menu.jsp" dojoType="dijit.layout.LinkPane"></a>

</div>
<div class="monitorTab-leftCol" style="padding: 0;">
  <p class="paddingBtm"> Status Filter:
    <select style="width: 100px;">
      <option> Accepted </option>
      <option> Rejected </option>
      <option> Completed </option>
    </select>
    &nbsp;&nbsp;&nbsp;&nbsp;Sub-status Filter:
    <select>
      <option> Unqualified to Complete Work </option>
    </select>
    &nbsp;&nbsp;
    <input type="radio" class="antiRadioOffsets" name="x" checked />
    <span>All Orders</span>
    <input type="radio" class="antiRadioOffsets" name="x"/>
    <span>My Orders</span></p>
  <div class="clearfix">
    <div class="floatLeft">
      <p>Show <b>25</b> <span class="pipes">|</span> <a href="#" class="lightBlue">50</a> <span class="pipes">|</span> <a href="#" class="lightBlue">100</a> Results per Page</p>
    </div>
    <div class="floatRight">
      <p> <a href="#">< Previous</a> <b>11</b> <span class="pipes">|</span> <a href="#">12</a> <span class="pipes">|</span> <a href="#">13</a> <span class="pipes">|</span> <a href="#">14</a> <span class="pipes">|</span> <a href="#">15</a> of 25 <a href="#">Next ></a></p>
    </div>
  </div>
  <table cellpadding="0" cellspacing="0" class="scrollerTableHdr recdOrdersHdr">
    <thead>
      <tr>
        <td class="column1"> View Detail </td>
        <th class="column2"><a href="" class="sortGridColumnUp">Status</a></th>
        <th class="column3"><a href="" class="sortGridColumnUp">Service Order #</a></th>
        <th class="column4"><a href="" class="sortGridColumnUp">Title</a></th>
        <th class="column5"><a href="" class="sortGridColumnUp2">Maximum Price</a> </th>
        <th class="column6"><a href="" class="sortGridColumnUp2">Time to Appointment</a> </th>
        <th class="column7"><a href="" class="sortGridColumnUp2">Age of Order</a> </th>
      </tr>
    </thead>
  </table>
  <iframe src="${contextPath}/release_20070914_service_order_monitor/html/som_table_recd.jsp" scrolling="auto" frameborder="0" class="grayTableContainer" style="width: 701px; height: 400px;"></iframe>
  <div class="clearfix">
    <div class="floatLeft">
      <p>Show <b>25</b> <span class="pipes">|</span> <a href="#" class="lightBlue">50</a> <span class="pipes">|</span> <a href="#" class="lightBlue">100</a> Results per Page</p>
    </div>
    <div class="floatRight">
      <p><a href="#">< Previous</a> <b>11</b> <span class="pipes">|</span> <a href="#">12</a> <span class="pipes">|</span> <a href="#">13</a> <span class="pipes">|</span> <a href="#">14</a> <span class="pipes">|</span> <a href="#">15</a> of 25 <a href="#">Next ></a></p>
    </div>
  </div>
</div>


