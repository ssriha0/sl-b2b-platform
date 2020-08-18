<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.newco.marketplace.web.utils.SODetailsUtils"%>
  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="NEWCO_SYSTEM" value="<%= OrderConstants.SYSTEM_ROLE %>" />
<c:set var="SYSTEM_IVR" value="<%= OrderConstants.SYSTEM_IVR %>" />

     <script type="text/javascript">
      jQuery("#checkSummary").empty();
      jQuery("#checkSOHistory").load("soDetailsQuickLinks.action?soId=${SERVICE_ORDER_ID}&resId=${routedResourceId}");
       </script>
        <div class="soNote">
          
<div id="rightsidemodules" class="colRight255 clearfix">
      <p id="checkSOHistory" style="color:#000;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;"><span> </span></p>
</div>
<%request.setAttribute(OrderConstants.DEFAULT_TAB,SODetailsUtils.ID_ORDER_HISTORY); %>
<div class="contentPane">
  <p>ServiceLive keeps a running record of all the actions taken on your service order so you don't have to. Refer
		back for a record of each spending increase, schedule change and added task.
  </p>
   <div style="color: blue">
 		<p>${msg}</p>
 		<%request.setAttribute("msg",""); %>
	</div>
  <!-- NEW MODULE/ WIDGET-->
  
  <div class="grayTableContainer" id="orderHistContainer">
    <table class="globalTableLook" cellpadding="0" cellspacing="0" style="margin: 0; table-layout: fixed; width:100%">
     <tr>
       <th class="col1 first odd" width="15%"><!--  <a href="#" class="sortGridColumnUp">-->Action</th>
      <th class="col2 even" width="10%"><!-- <a href="#" class="sortGridColumnUp">-->User Type</th>
      <th class="col3 odd" width='10%'><!-- <a href="#" class="sortGridColumnUp">-->User</th>
      <th class="col4 even" width="50%"><!-- <a href="#" class="sortGridColumnUp">-->Description</th>
      <th class="col5 odd last" width="15%"><!-- <a href="#" class="sortGridColumnUp2">-->Created Date/Time</th>
       </tr>
 	<c:forEach var="hddto" items="${hdDtoList}">     
      <tr>
        <td class="col1 first odd" width="15%" style='word-wrap:break-word;'>${hddto.actionDescription}</td>
          <td class="col2 even" width="10%">${hddto.roleName}</td> 
            
        <td class="col3 odd" width='10%' style='word-wrap:break-word;'>
        <c:choose>
        <c:when test="${fn:toUpperCase(hddto.createdByName) == SYSTEM_IVR}">
			${SYSTEM_IVR}
		 </c:when>	                	
        <c:otherwise>
      		&nbsp;
      		<c:choose>
        <c:when test="${fn:toUpperCase(hddto.createdByName) == NEWCO_SYSTEM}">
			${NEWCO_SYSTEM}
		</c:when>	                	
	 	<c:otherwise>
	 		<c:choose>
	 		<c:when test="${fn:contains(hddto.createdByName, ' ')}">
       		${fn:substringAfter(hddto.createdByName, " ")}&nbsp;${fn:substring(fn:substringBefore(hddto.createdByName, " "),0,1)}.
       		</c:when>
       		<c:otherwise>
       			${hddto.createdByName}.
        	</c:otherwise>
        	</c:choose>
       		<c:if test="${hddto.entityId != 0}">
             	<br> (User Id# ${hddto.entityId})
             </c:if> 
        </c:otherwise>
        	</c:choose>
       </c:otherwise>
        </c:choose>
        </td>
        <td class="col4 even" style="text-align: left;width: 50%;">
        <div style='word-wrap:break-word;'>
        ${hddto.chgComment}
        </div>
        </td>
        
        <td class="col5 odd last" width="15%">${hddto.createdDate} </td>
      </tr> 
	</c:forEach>     
	   <c:if test="${empty hdDtoList}"> 
	   	<tr>
	   		<td colspan="5" class="col1 first odd">
	   			<p> No Records Found.</p>
	   		</td>
	   	</tr>
	   </c:if>
   </table>
  </div>

</div>
</div>
