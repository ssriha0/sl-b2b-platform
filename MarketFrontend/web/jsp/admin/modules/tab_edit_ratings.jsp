<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<!-- START RIGHT SIDE MODULES -->

<div id="rightsidemodules" class="colRight255 clearfix">
  <a href="html_sections/modules/member_info2.jsp" dojoType="dijit.layout.LinkPane"></a>
  <div dojoType="dijit.layout.ContentPane"
	href="${contextPath}/release_20071005_service_order_details/html/html_sections/modules/quick_links_prov_accepted.jsp"></div>
  </div>
<!-- END RIGHT SIDE MODULES -->
<div class="contentPane">
  <!-- NEW MODULE/ WIDGET-->
  <div class="darkGrayModuleHdr">Ratings</div>
  <div class="grayModuleContent mainWellContent clearfix">
    <div class="grayModuleHdr">
      <div class="floatLeft">Service Buyer</div>
      <div class="floatRight"><strong>3.8 Stars</strong></div>
    </div>
    <table cellpadding="0" cellspacing="0" class="viewRatingTable">
      <tr>
        <td class="column1"><p><b>Quality</b></p>
          <p>Workmanship, attention to detail and quality of materials used</p></td>
        <td class="column2"><p>Highly Statisfied</p>
          <p> <span class="RATE" onmouseout="popUp(event,'userRatings')" onmouseover="popUp(event,'userRatings')"><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/half_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /></span></p></td>
      </tr>
      <tr class="alt">
        <td class="column1"><p><b>Communication</b></p>
          <p>Accessibility through phone and email and commitment to keeping you informed of your project status</p></td>
        <td class="column2"><p>Somewhat Dissatisfied</p>
          <p> <span class="RATE" onmouseout="popUp(event,'userRatings')" onmouseover="popUp(event,'userRatings')"><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/half_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /></span></p></td>
      </tr>
      <tr>
        <td class="column1"><p><b>Timeliness </b></p>
          <p>Promptness and adherence to agreed-upon timeframe</p></td>
        <td class="column2"><p>Highly Statisfied</p>
          <p> <span class="RATE" onmouseout="popUp(event,'userRatings')" onmouseover="popUp(event,'userRatings')"><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/half_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /></span></p></td>
      </tr>
      <tr class="alt">
        <td class="column1"><p><b>Professionalism</b></p>
          <p>Politeness, accountability and competence in completing the tasks in the service order</p></td>
        <td class="column2"><p>Highly Statisfied</p>
          <p> <span class="RATE" onmouseout="popUp(event,'userRatings')" onmouseover="popUp(event,'userRatings')"><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/half_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /></span></p></td>
      </tr>
      <tr>
        <td class="column1"><p><b>Value </b></p>
          <p> The quality of work in relation to the amount you were charged</p></td>
        <td class="column2"><p>Highly Statisfied</p>
          <p> <span class="RATE" onmouseout="popUp(event,'userRatings')" onmouseover="popUp(event,'userRatings')"><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/half_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /></span></p></td>
      </tr>
      <tr class="darkGray">
      <td><p><b>Overall</b></p></td>
      <td class="column2"><p><strong>3.8 Stars</strong></p></td>
    </tr>
       <tr>
        <td colspan="2"><p><strong>Additional Comments</strong><br>
           <textarea style="width: 99%; height: 75px;" class="shadowBox lockedField" onfocus="clearTextbox(this)">
           Quibus dignissim occuro tincidunt saluto antehabeo quia iriure eum at persto. Quibus
dignissim occuro tincidunt saluto antehabeo quia iriure eum at persto. Quibus dignissim
occuro tincidunt saluto antehabeo quia iriure eum at persto.
           </textarea></p></td>
      </tr>
    </table>
    <div class="grayModuleHdr">
      <div class="floatLeft">Service Provider</div>
      <div class="floatRight"><strong>3.8 Stars</strong></div>
    </div>
    <table cellpadding="0" cellspacing="0" class="viewRatingTable">
      <tr>
        <td class="column1"><p><b>Quality</b></p>
          <p>Workmanship, attention to detail and quality of materials used</p></td>
        <td class="column2"><p>Highly Statisfied</p>
          <p> <span class="RATE" onmouseout="popUp(event,'userRatings')" onmouseover="popUp(event,'userRatings')"><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/half_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /></span></p></td>
      </tr>
      <tr class="alt">
        <td class="column1"><p><b>Communication</b></p>
          <p>Accessibility through phone and email and commitment to keeping you informed of your project status</p></td>
        <td class="column2"><p>Somewhat Dissatisfied</p>
          <p> <span class="RATE" onmouseout="popUp(event,'userRatings')" onmouseover="popUp(event,'userRatings')"><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/half_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /></span></p></td>
      </tr>
      <tr>
        <td class="column1"><p><b>Timeliness </b></p>
          <p>Promptness and adherence to agreed-upon timeframe</p></td>
        <td class="column2"><p>Highly Statisfied</p>
          <p> <span class="RATE" onmouseout="popUp(event,'userRatings')" onmouseover="popUp(event,'userRatings')"><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/half_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /></span></p></td>
      </tr>
      <tr class="alt">
        <td class="column1"><p><b>Professionalism</b></p>
          <p>Politeness, accountability and competence in completing the tasks in the service order</p></td>
        <td class="column2"><p>Highly Statisfied</p>
          <p> <span class="RATE" onmouseout="popUp(event,'userRatings')" onmouseover="popUp(event,'userRatings')"><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/half_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /></span></p></td>
      </tr>
      <tr>
        <td class="column1"><p><b>Value </b></p>
          <p> The quality of work in relation to the amount you were charged</p></td>
        <td class="column2"><p>Highly Statisfied</p>
          <p> <span class="RATE" onmouseout="popUp(event,'userRatings')" onmouseover="popUp(event,'userRatings')"><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/half_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /></span></p></td>
      </tr>
      <tr class="alt">
        <td class="column1"><p><b>Cleanliness </b></p>
          <p>Respect for your personal property and ability to keep work site contained</p></td>
        <td class="column2"><p>Highly Statisfied</p>
          <p> <span class="RATE" onmouseout="popUp(event,'userRatings')" onmouseover="popUp(event,'userRatings')"><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/half_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /></span></p></td>
      </tr>
      <tr class="darkGray">
      <td><p><b>Overall</b></p></td>
      <td class="column2"><p><strong>3.8 Stars</strong></p></td>
    </tr>
      <tr>
        <td colspan="2"><p><strong>Additional Comments</strong><br>
           <textarea style="width: 99%; height: 75px;" class="shadowBox lockedField" onfocus="clearTextbox(this)">
           Quibus dignissim occuro tincidunt saluto antehabeo quia iriure eum at persto. Quibus
dignissim occuro tincidunt saluto antehabeo quia iriure eum at persto. Quibus dignissim
occuro tincidunt saluto antehabeo quia iriure eum at persto.
           </textarea></p></td>
      </tr>
    </table>
  </div>
  <div class="clearfix">
  <div class="floatLeft">
  <p>
    <input width="72" type="image" height="20" src="${staticContextPath}/images/common/spacer.gif" style="background-image: url(${staticContextPath}/images/btn/submit.gif);" class="btnBevel"/>
  </p>
  </div>
  <div class="floatRight">
  <p>
    <a href="">Cancel</a>
  </p>
  </div>
</div>
</div>

<!-- END TAB PANE -->
