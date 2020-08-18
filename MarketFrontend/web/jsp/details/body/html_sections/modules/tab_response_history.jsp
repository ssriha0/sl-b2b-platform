<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery-ui-personalized-1.5.2.packed.js"></script>
<script src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js" type="text/javascript"></script>
            <jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
                  <jsp:param name="PageName" value="ServiceOrderDetails.responseHistory"/>
            </jsp:include>

<div id="page_margins">
  <div id="page">
   
<div class="colLeft711">
    
<!-- NEW MODULE/ WIDGET-->
<div >
	<div class="darkGrayModuleHdr">Response Status</div>
  <p>Review the conditional acceptances and rejections that were generated before your service order was accepted.</p>
	<table>
    	<tr>
        	<td width="150"><b>Providers Posted for</b></td>
            <td>46</td>
        </tr>
        <tr>
        	<td><b>Conditionally Accepted</b></td>
            <td>15</td>
        </tr>
        <tr>
        	<td><b>Rejected</b></td>
            <td>3</td>
        </tr>
     </table>
<div class="clear"></div>
<div class="grayTableContainer">
  <table class="globalTableLook" cellpadding="0" cellspacing="0">
    <tr>
      <th class="col1 odd first">Service Provider</th>
      <th class="col2 even">Posted Status</th>
      <th class="col3 odd last">Details</th>
    </tr>
      <tr>
        <td>
        	<table cellpadding="0" cellspacing="0">
            	<tr>
                	<td colspan="2"><a href="#">John D.</a> (ID# 435993) <img src="${staticContextPath}/images/btn/fav.gif" width="24" height="13" alt=""></td>
                </tr>
                <tr>
                	<td width="100"><b>Company ID#</b></td>
                    <td>456724</td>
                </tr>
                <tr>
                	<td><b>Rating</b></td>
                    <td>4.5/5.0 (260 ratings)</td>
                </tr>
                <tr>
                	<td><b>My Rating</b></td>
                    <td>4.9/5.0 (2 ratings)</td>
                </tr>
            </table>
        </td>
        <td width="126" >Conditional <br>Acceptance</td>
        <td class="column3">
          <p>Reschedule:<br>
          December 21 - December 22<br>
          10 AM - 2 PM</p>
         </td>
      </tr>
      <tr>
        <td  width="300">
        	<table cellpadding="0" cellspacing="0">
            	<tr>
                	<td colspan="2"><a href="#">Seth G.</a> (ID# 435994) <img src="${staticContextPath}/images/btn/fav.gif" width="24" height="13" alt=""></td>
                </tr>
                <tr>
                	<td width="100"><b>Company ID#</b></td>
                    <td>456724</td>
                </tr>
                <tr>
                	<td><b>Rating</b></td>
                    <td>4.5/5.0 (260 ratings)</td>
                </tr>
                <tr>
                	<td><b>My Rating</b></td>
                    <td>4.9/5.0 (2 ratings)</td>
                </tr>
            </table>
        </td>
        <td width="126" >Rejected</td>
        <td class="column3">
         On Vacation.
         </td>
      </tr>
      <tr>
        <td  width="300">
        	<table cellpadding="0" cellspacing="0">
            	<tr>
                	<td colspan="2"><a href="#">Maureen C.</a> (ID# 435993) <img src="${staticContextPath}/images/btn/fav.gif" width="24" height="13" alt=""></td>
                </tr>
                <tr>
                	<td width="100"><b>Company ID#</b></td>
                    <td>456724</td>
                </tr>
                <tr>
                	<td><b>Rating</b></td>
                    <td>4.5/5.0 (260 ratings)</td>
                </tr>
                <tr>
                	<td><b>My Rating</b></td>
                    <td>4.9/5.0 (2 ratings)</td>
                </tr>
            </table>
        </td>
        <td width="126" >Conditional <br>Acceptance</td>
        <td class="column3">
          <p>Reschedule &amp; Spend Increase:<br>
          December 21 - December 22<br>
          10 AM - 2 PM<br>
          $250</p>
         </td>
      </tr>
      <tr>
        <td  width="300">
        	<table cellpadding="0" cellspacing="0">
            	<tr>
                	<td colspan="2"><a href="#">Seth G.</a> (ID# 435994) <img src="${staticContextPath}/images/btn/fav.gif" width="24" height="13" alt=""></td>
                </tr>
                <tr>
                	<td width="100"><b>Company ID#</b></td>
                    <td>456724</td>
                </tr>
                <tr>
                	<td><b>Rating</b></td>
                    <td>4.5/5.0 (260 ratings)</td>
                </tr>
                <tr>
                	<td><b>My Rating</b></td>
                    <td>4.9/5.0 (2 ratings)</td>
                </tr>
            </table>
        </td>
        <td width="126" >Rejected</td>
        <td class="column3">
         Spend limit too low.
         </td>
      </tr>
    </table>
  </div>
  <div align="right" style="margin-right:50px;">
  	Show <b>25</b> | <a href="#">50</a> | <a href="#">100</a> Results per Page<br>
      <div class="floatRight"> 
    <a class="lightBlue" href="#">&lt; Previous</a> 
    <b>1</b> 
    <span class="pipes">|</span> 
    <a class="lightBlue" href="#">2</a> 
    <span class="PIPES">|</span> 
    <a class="lightBlue" href="#">3</a> 
    <span class="pipes">|</span> 
    <a class="lightBlue" href="#">4</a> 
    <span class="pipes">|</span> 
    <a class="lightBlue" href="#">5</a> of 20 
    <a class="lightBlue" href="#">Next &gt;</a> 
    &nbsp; 
</div>
  </div>

</div>


   
   
    <!-- END TAB PANE -->
   
    
    <div class="colRight255 clearfix">
   
    </div>
    <div class="clear"></div>
   
  </div>
</div>

