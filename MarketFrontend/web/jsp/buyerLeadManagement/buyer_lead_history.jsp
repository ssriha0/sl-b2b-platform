<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.newco.marketplace.web.utils.SODetailsUtils"%>
  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
  <div id="historyLoadingLogo" style="display: block;">
<img src="${staticContextPath}/images/loading.gif" width="442px" height="285px" style="position:relative;top:0px;left:-136px;padding-bottom: 10px;" >
</div>  
<div class="leadNote">
       
<div class="contentPane">
  <br>
  <p class="viewNotesInfo">ServiceLive keeps a running record of all the actions taken on your lead so you don't have to. Use Lead history to refer back for a record of each transition or updation of your lead. 
  </p>
  <br>
    <div class="viewNotesTable">
    <table class="leadTableLook" cellpadding="0" cellspacing="0" style="margin: 0; table-layout: fixed;width:100%">
     <tr>
       <th class="col1 first odd" width="15%">Action</th>
      <th class="col2 even" width="10%">User Type</th>
      <th class="col3 odd" width='20%'>User</th>
      <th class="col4 even" width="35%">Description</th>
      <th class="col5 odd last" width="20%">Date & Time</th>
       </tr>
 	<c:forEach var="leadHistory" items="${leadHistory}">     
      <tr>
        <td class="col1 first odd" width="15%">${leadHistory.actionName}</td>
        <td class="col2 even" width="10%">${leadHistory.roleName}</td> 
        <td class="col3 odd" style="text-align: left;width: 15%;word-break:break-all;word-wrap:break-word;" >
        	${leadHistory.createdBy} <br>
        	<c:if test="${null != leadHistory.entityId}">(User ID #${leadHistory.entityId})</c:if>
        </td>
        <td class="col4 even" width='45%'>${leadHistory.chgComment}</td>
        <td class="col5 odd last" width="15%">${leadHistory.createdActualDate} </td>
      </tr> 
	</c:forEach> 
 
 	<c:if test="${empty leadHistory}"> 
	   	<tr>
	   		<td colspan="5" class="col1 first odd">
	   			<p>No Records Found.</p>
	   		</td>
	   	</tr>
	   </c:if>
   </table>
  </div> 

</div>
</div>
