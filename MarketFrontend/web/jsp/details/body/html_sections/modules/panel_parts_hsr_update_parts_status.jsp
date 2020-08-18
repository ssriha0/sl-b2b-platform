<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<html>

<head>
</head>
<body>
	
	<div id="updatePartsStatusDiv" style="display: none; border:1px solid grey;">
	<div class="submenu_head" style="height:auto;">Update Part Status</div>
	<div id="errorTextUpdatePartStatus"
			style="margin-left: 20px;padding-left: 30px;width: 90%;border: 2px solid #F5A9A9;display:none;padding-top:2px;
				background: url('${staticContextPath}/images/icons/50.png') no-repeat scroll 10px 1px #FBE3E4;"></div>
		<form id="updatePartsStatusForm" method="POST">
		<input type="hidden" id="partListSize" name="partListSize" value="${fn:length(updatePartDetailsInfo.invoicePartsVOs)}" />
		<div id="partsTableDiv" class="partsTableDiv">
	             <table width="99%" class="installed_parts" cellspacing="10px;">
	               <thead>
	                 <tr class="spaceUnder">
	                   <td><span class="boldSpan">Part Number</span></td>
	                   <td><span class="boldSpan">Part Name</span></td>
	                   <td align="left" style="padding-left: 20px;"><span class="boldSpan">Part Status</span></td>
	                 </tr>
	               </thead>
	               <c:forEach items="${updatePartDetailsInfo.invoicePartsVOs}" var="invoicePart" varStatus="i">
	               	<tr>
	                 <input type="hidden" id="updatePartSoId" name="soId" value="${invoicePart.soId}" />
	                     <td width="20%" style="padding-bottom: 10px;">${invoicePart.partNumber}
	                     <input type="hidden" id="addInvoiceDTO[${i.count}].partInvoiceId" name="addInvoiceDTO[${i.count}].partInvoiceId" value="${invoicePart.partInvoiceId}" />
	                     </td>
	                     <td width="40%" style="word-break: break-all">
	                     <div>${invoicePart.partDescription}</div>
	                     </td>
	                     <td width="40%" align="left" style="padding-left: 20px;"> 
	                        <select id="partStatusUpdate_${i.count}" name="addInvoiceDTO[${i.count}].partStatus">
							    <option value="select">--Select One--</option>
							      <c:if test="${invoicePart.partStatus=='Added'}">
								     	<option class="partStatusDropDown" value="Added" selected="selected">Added</option>
								  </c:if>
							     <c:forEach items="${updatePartDetailsInfo.partStatusTypes}" var="partStatus">
							      <c:choose>
								     <c:when test="${partStatus.descr == invoicePart.partStatus}">
								     	<option class="partStatusDropDown" value="${partStatus.descr}" selected="selected" >${partStatus.type}</option>
								     </c:when>
								     <c:otherwise>
					      				<option class="partStatusDropDown" value="${partStatus.descr}" >${partStatus.type}</option>
								     </c:otherwise>
								  </c:choose>
							     </c:forEach>
							     
                            </select> 
                         </td>
	                   </tr>
	                 </c:forEach>
	              </table>
	         </div>
		</form>
		<br/><hr><br/>
		<table width="90%">
	       <tr>
	       <td width="10%" style="padding-left: 20px;">
	       <a href="javascript:void(0)" onclick="clearUpdatePartsInfo();"> Cancel</a>
	       </td>
	       <td width="90%" align="right">
	      	 <button class="newbutton" onclick="updatePartStatus();" class="myButton">Update Part Status</button>
	       </td>
	       </tr>
	       </table>
	       
	</div>
</body>
</html>