<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.newco.marketplace.web.utils.SODetailsUtils"%>
  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="NEWCO_SYSTEM" value="<%= OrderConstants.SYSTEM_ROLE %>" />
<c:set var="SYSTEM_IVR" value="<%= OrderConstants.SYSTEM_IVR %>" />
<script type="text/javascript">
      jQuery("#priceLink").load("soDetailsQuickLinks.action?soId=${SERVICE_ORDER_ID}");
       </script>
        <div class="soNote" style="height: 550px;">
          
<div id="rightsidemodules" class="colRight255 clearfix">
      <p id="priceLink" style="color:#000;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;"><span> </span></p>
</div>
<%request.setAttribute(OrderConstants.DEFAULT_TAB,SODetailsUtils.ID_PRICE_HISTORY); %>
<div class="contentPane">
  <p>ServiceLive keeps a running record of all the price changes since the creation of the service order.</p>
   <div style="color: blue">
 		<p>${msg}</p>
 		<%request.setAttribute("msg",""); %>
	</div>
  <!-- NEW MODULE/ WIDGET-->
  <!-- Start of Loading Div for so level -->
  <c:if test="${not empty priceHistTabDto}">
    <c:forEach items="${priceHistTabDto.soLevelHistoryVo}" varStatus="rowcounter" var="soLevelHistoryVo">
            <c:if test="${not empty soLevelHistoryVo.permitPriceChange}">
               <c:set   var="permitDisplay" value="true"/>
            </c:if>
            <c:if test="${not empty soLevelHistoryVo.invoicePartPriceChange}">
               <c:set  var="invoiceDisplay" value="true"/>
            </c:if>
            <c:if test="${not empty soLevelHistoryVo.addonPriceChange}">
              <c:set   var="addOnDisplay" value="true"/>
            </c:if>
    </c:forEach>
  </c:if>
<div class="grayTableContainer" id="orderHistContainer" style="width:680px;">
    <table class="globalTableLook" cellpadding="0" cellspacing="0" style="margin: 0; table-layout: fixed; width:100%">
     <tr>
      <th class="col1 first odd" width="12%">User</th>
      <th class="col2 even" width="9%">Date&Time</th>
      <th class="col3 odd" width="15%">Action</th>
      <th class="col4 even" width="12%">Labor</th>
      <th class="col5 odd " width="12%">Material</th>
       <c:if test="${invoiceDisplay eq true }">
       <th class="col6 even" width="10%">Parts</th>
       </c:if>
       <c:if test="${addOnDisplay eq true }">
       <th class="col7 odd" width="10%">Add-On</th>
       </c:if>
       <c:if test="${permitDisplay eq true }">
       <th class="col8 even last" width="10%">Permits</th>
       </c:if>
       <c:choose>
       <c:when test="${addOnDisplay eq true}">
        <th class="col9 odd" width="10%">Total</th>
       </c:when>
       <c:otherwise>
       <th class="co18 even last" width="10%">Total</th>
       </c:otherwise>
       </c:choose>
       </tr>
      
     <c:if test="${not empty priceHistTabDto}"> 
 	   <c:forEach items="${priceHistTabDto.soLevelHistoryVo}" varStatus="rowcounter" var="soLevelHistoryVo">
        <tr>
              <td class="col1 first odd" width="12%" style=" word-wrap:break-word;"> 
              		${soLevelHistoryVo.modifiedByName} <br>
              		${soLevelHistoryVo.modifiedBy}
              </td>
              <td class="col2 even" width="12%">
                    ${soLevelHistoryVo.modifiedDate}
                    
             </td> 
              <td class="col3 odd" width="15%" style=" word-wrap:break-word;">
               		${soLevelHistoryVo.action} <br>
              <c:if test="${ not empty soLevelHistoryVo.reasonComments}">
                    ${soLevelHistoryVo.reasonComments}
              </c:if>
              </td>
              <td class="col4 even" width="12%">
              		${soLevelHistoryVo.laborPriceChange }
              </td>
              <!-- Check added to display empty cells for matarial price in ie7. -->
							<c:choose>
								<c:when test="${not empty soLevelHistoryVo.partPriceChange}">
									<td class="col5 odd " width="12%">
										${soLevelHistoryVo.partPriceChange }</td>
								</c:when>
								<c:otherwise>
									<td class="col5 odd " width="12%"><span
										style="width: 0px;"></span></td>
								</c:otherwise>
							</c:choose>
							<c:choose>
              <c:when test="${ not empty soLevelHistoryVo.invoicePartPriceChange }">
                 <td class="col6 even" width="10%">
              		  ${soLevelHistoryVo.invoicePartPriceChange}
                 </td>
               </c:when>
               <c:when test="${invoiceDisplay eq true }">
                     <td class="col6 even" width="10%">-</td>
               </c:when>
               </c:choose>
               <c:choose>
               <c:when test="${not empty soLevelHistoryVo.addonPriceChange }">
                  <td class="col7 odd" width="10%">
              		  ${soLevelHistoryVo.addonPriceChange}
                  </td>
               </c:when>
               <c:when test="${addOnDisplay eq true }">
                   <td class="col7 odd" width="10%">-</td>
               </c:when>
               </c:choose>
                <c:choose>
              <c:when test="${not empty soLevelHistoryVo.permitPriceChange}">
                  <td class="col8 even last" width="10%">
              		   ${soLevelHistoryVo.permitPriceChange}
                  </td>
              </c:when>
              <c:when test="${permitDisplay eq true }">
                   <td class="col8 even last" width="10%">-</td>
              </c:when>
              </c:choose>
              <c:choose>
              <c:when test="${addOnDisplay eq true}">
                   <td class="col9 odd" width="10%">
               		    $${soLevelHistoryVo.totalPrice}
               	  </td>
              </c:when>
              <c:otherwise>
                  <td class="col8 even last" width="10%">
               		    $${soLevelHistoryVo.totalPrice}
               	  </td>
              </c:otherwise>
              </c:choose>   
              
          
	   </tr>
	 </c:forEach>
	 </c:if>
	       <c:if test="${empty priceHistTabDto}"> 
	         <tr>
	   		      <td colspan="6" class="col1 first odd">
	   			      <p> No Records Found.</p>
	   		     </td>
	   	    </tr>
	   </c:if>
   </table>
  </div>
<!-- End of Loading Div  for so level-->
 </div>
</div>

