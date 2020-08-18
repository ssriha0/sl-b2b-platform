<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/plugins/ajaxfileupload.js"></script>
<script type="text/javascript">
 	function viewEditPartsDocument(documentId){
 		var loadForm = document.getElementById('editPartsViewDocForm');
        loadForm.action = 'viewPartsDocument.action?editDocId='+documentId;
        loadForm.submit();
 	}
 	function closeViewPart(){
 	    jQuery("#viewPartDiv").css("display","none");
 	}
</script>
<div id="viewPartDiv" class="viewPart" style="display: none;position:static;border:1px solid grey;height:auto;" width="90%">
<div class="submenu_head" style="height:auto;">Part Number ${editPartDetails.partNumber}</div>
	         <input type="hidden" id="partInvoiceIdView" name="partInvoiceId" value="${editPartDetails.partInvoiceId}" />
	         <div class="PartDispalyDiv" width="75%">
	           <span class="partNoSpan">Part Number :</span>
	           <span class="PartNoValue">${editPartDetails.partNumber}</span>
	           <span class="partNameView">Part Name :</span>
	           <span class="partNameValue">${editPartDetails.partDescription}</span>
	         </div>
	         <div class="PartStatus" >
	             <span class="partStatusView">Part Status</span><span style="margin-left:49px"><b>:</b></span>
	             <span class="partStatusValue">${editPartDetails.partStatus}</span>
	         </div>
	          <div class="RetailPriceDiv" >
	             <span class="invoiceNoTextView">Retail Price</span><span style="margin-left:49px"><b>:</b></span>
	             <span class="invoiceNoValueView">$ ${editPartDetails.retailPrice}</span>
	         </div>
	         <div id="unitCostQtyDiv" class="unitCostQtyDiv">
	             <span class="unitCostSpan">Unit Cost</span><span style="margin-left:63px"><b>:</b></span>
	             <span class="unitcostValue"> $ ${editPartDetails.unitCost}</span>
	             <span class="QtySpan"> Qty : </span>&nbsp;&nbsp;
	             <span class="qtySpanValue">${editPartDetails.qty}</span>
	         </div> 
	         <div class="invoiceNoView">
	            <span class="invoiceNoTextView">Invoice Number</span><span style="margin-left:23px"><b>:</b></span>
	            <span class="invoiceNoValueView">${editPartDetails.invoiceNumber}</span>
	         </div>
	         <div class="sourceView">
	           <span class="sourceViewName">Source</span><span style="margin-left:75px"><b>:</b></span>
	            <c:choose>
	               <c:when test="${not empty editPartDetails.partSource &&(editPartDetails.partSource =='Sears')}">
	                    <span class="sourceValue">${editPartDetails.partSource}</span>
	               </c:when>
	               <c:otherwise>
	                   <span class="sourceValue">${editPartDetails.partSource} <span>- ${editPartDetails.nonSearsSource}</span></span>
	               </c:otherwise>
	            </c:choose>
	         </div>
	         
	         <div class="DivisionNumberDiv">
	            <span class="invoiceNoTextView">Division Number</span><span style="margin-left:22px"><b>:</b></span>
	            <span class="invoiceNoValueView">${editPartDetails.divisionNumber}</span>
	         </div>
	         
	         <div class="SourceNumberDiv">
	            <span class="invoiceNoTextView">Source Number</span><span style="margin-left:29px"><b>:</b></span>
	            <span class="invoiceNoValueView">${editPartDetails.sourceNumber}</span>
	         </div>
	         
	         <div id="editPartsUploadedFiles" class="editPartsUploadedFiles" >
	             <span class="invoiceDocumentsParts">Invoice Documents :</span>
	             <c:if test="${null != editPartDetails.invoiceDocumentList}">
	             <c:choose>
	                <c:when test="${fn:length(editPartDetails.invoiceDocumentList)> 0}">
	                  <form method="POST" id="editPartsViewDocForm" action="">
		                <div style="width:500px; padding-left:50px;">
			                <c:forEach items="${editPartDetails.invoiceDocumentList}" var="partsUploadedDocuments">
				               <a onclick="viewEditPartsDocument(${partsUploadedDocuments.documentId});" href="javascript:void(0)">${partsUploadedDocuments.fileName}</a> 
				               <i>(by ${partsUploadedDocuments.uploadedbyName} on ${partsUploadedDocuments.uploadDateTime})</i><br/>
			                </c:forEach>
		                 </div>
	                 </form>
	              </c:when>
	              <c:otherwise>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No Document uploaded</c:otherwise>
	           </c:choose>
	           </c:if>
	 		</div>
	        <div style="margin-top:10px;">
	            <a class="cancelViewPart" href="javascript:void(0);" onclick="closeViewPart();">Cancel</a>
	         </div>
	         <br/>
</div>