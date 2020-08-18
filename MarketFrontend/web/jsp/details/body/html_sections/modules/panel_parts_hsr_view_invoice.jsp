<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="partInvoiceNo" value="${invoicePartDetails.invoicePartsVOs[0].invoiceNumber}"></c:set>
<c:set var="partSource" value="${invoicePartDetails.invoicePartsVOs[0].partSource}"></c:set>
<c:set var="nonSearsSource" value="${invoicePartDetails.invoicePartsVOs[0].nonSearsSource}"></c:set>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/plugins/ajaxfileupload.js"></script>
<script type="text/javascript">
    jQuery(document).ready(function (){
          var errorExists = '${sourceErrorExists}';
          if(null != errorExists &&errorExists == 'true'){
   	       // Not displaying part source label and value
        	  jQuery(".sourceDiv").css("display","none");
   	      }else{
   	    	jQuery(".sourceDiv").css("display","block");  
   	        var searsource = '${partSource}';
   	        var nonSears = '${nonSearsSource}';
   	        var source ="";
   	        if(null != searsource){
   	          source = searsource;
   	        }
   	        if(null != source && source === 'Non Sears' && null != nonSears){
   	          source = 'Non Sears - '+ nonSears;
   	        }
   	        jQuery("#sourcevalue").html(source);
   	      }
	});
  function closeViewInvoice(){
 	    jQuery("#viewInvoiceDiv").css("display","none");
 	}
 	function viewInvoicePartsDocument(documentId){
 		var loadForm = document.getElementById('addInvoiceViewDocForm');
        loadForm.action = 'viewPartsDocument.action?editDocId='+documentId;
        loadForm.submit();
 	}
</script>
<div id="viewInvoiceDiv" class="viewInvoice" style="display: none;position:static;border:1px solid grey;height:auto;" width="90%">
<div class="submenu_head" style="height:auto;">Invoice Number ${partInvoiceNo}</div>
	         <div class="invoiceDispalyDiv" width="75%">
	           <span class="invoiceNoText">Invoice Number</span><span style="margin-left:23px"><b>:</b></span>
	           <span class="invoiceNoValue">${partInvoiceNo}</span>
	         </div>
	         <div class="sourceDiv" style="display:none;">
	             <span class="partSourcelabel">Source</span><span style="margin-left:75px"><b>:</b></span>
	             <span id="sourcevalue" style="margin-left:20px;"></span>
	         </div>
	    
	         <div id="partsTableViewDiv" class="partsTableDiv" style="margin-left:10px;">
	             <table width="100%" class="installed_parts" cellspacing="10px;">
	               <thead>
	                 <tr class="spaceUnder">
	                   <td  width="15%;"><span class="boldSpan">Part Number</span></td>
	                   <td 	width="25%;"><span class="boldSpan" style="margin-left:10px">Part Name</span></td>
	                   <td  width="10%;"><span class="boldSpan">Unit Cost</span></td>
	                   <td  width="10%;"><span class="boldSpan">Qty</span></td>
	                   <td  width="20%;"><span class="boldSpan">Part Status</span></td>
	                   <c:if test="${not empty sourceErrorExists && sourceErrorExists == 'true'}">
	                      <td  id="tdpartSourceLabel" width="20%;"">
	                         <span class="boldSpan">Source</span>
	                      </td>
	                   </c:if>
	                   
	                 </tr>
	               </thead>
	                 <c:forEach items="${invoicePartDetails.invoicePartsVOs}" var="invoicePart" varStatus="i">
	                    <tr>
	                     <td width="15%;" style="padding-left:5px;">${invoicePart.partNumber}</td>
	                     <td style="padding-left:10px;" width="25%;">
	                        <div style="word-wrap:break-word; width:110px;">${invoicePart.partDescription}</div>
	                     </td>
	                     <td width="10%;">
	                        $ <span>${invoicePart.unitCost}</span>
	                      </td>
	                     <td width="5%;" style="padding-left:7px;">${invoicePart.qty}</td>
	                     <td width="20%;">
	                      <div>
	                         ${invoicePart.partStatus}
	                      </div>
	                     </td> 
	                     <c:if test="${not empty sourceErrorExists && sourceErrorExists == 'true'}">  
	                         <td id="tdpartSourceValue" width="20%">
	                               ${invoicePart.partSource}
                    		    <c:if test="${invoicePart.partSource == 'Non Sears'}">- ${invoicePart.nonSearsSource}</c:if>
	                        </td> 
	                     </c:if>                
	                  </tr>
	                </c:forEach>
	              </table>
	         </div>
	         <div>
	         <div id="editPartsUploadedFiles" class="editPartsUploadedFiles">
	           <span class="invoiceDocumentsParts">Invoice Documents:</span>
	            <c:if test="${null != invoicePartDetails.invoiceDocuments}">
	              <c:choose>
	                <c:when test="${fn:length(invoicePartDetails.invoiceDocuments)> 0}">
	                  <form method="POST" id="addInvoiceViewDocForm" action="">
		                 <div style="width:500px; padding-left:50px;">
			                  <c:forEach items="${invoicePartDetails.invoiceDocuments}" var="partsUploadedDocuments">
				                      <a onclick="viewInvoicePartsDocument(${partsUploadedDocuments.documentId});" href="javascript:void(0)">${partsUploadedDocuments.fileName}</a> 
				                      <i> (by ${partsUploadedDocuments.uploadedbyName} on ${partsUploadedDocuments.uploadDateTime})</i><br/>
			                  </c:forEach>
		                      
		                 </div>
	                  </form>
	               </c:when>
	              <c:otherwise>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No Document uploaded</c:otherwise>
	             </c:choose>
	           </c:if>
	         </div>
	         </div>
	         <div style="margin-top:10px;">
	           <a class="cancelViewInvoice" href="javascript:void(0);" onclick="closeViewInvoice();"> Cancel</a>
	         </div><br/>
</div>