<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dashboard.css" />
<script type="text/javascript">
window.onload= partViewLoad();
function partViewLoad(){
	 var panel3Def = "&ldquo;<strong>Est. Net Provider Payment</strong>&rdquo; is the remaining amount after fees** have been applied.</br><Strong><i>**Fees may change without notice.</i></Strong>";	
	 jQuery(".glossaryItem").mouseover(function(e){
		 jQuery("#explainer").css("position","absolute");
	    	if(jQuery(this).attr("id") == "EstNtProvPymtHoverHoverSummaryView") {
    		 var position = jQuery("#EstNtProvPymtHoverHoverSummaryView").offset();
   	    	 jQuery("#explainer").html(panel3Def);
   	    	 jQuery("#explainer").css("top",position.top-1600);
   	    	 jQuery("#explainer").css("left",position.left-124);
	    	}
	    	 jQuery("#explainer").show();   
		    }); 
	 
	        jQuery(".glossaryItem").mouseout(function(e){
	        jQuery("#explainer").hide();
		     });
	  	
	 var PartCount = '${summaryDTO.countOfParts}';
        	   var invoiceCount ='${summaryDTO.countOfInvoice}';
        	   if(PartCount == 1 || PartCount== 0){
        	     jQuery('#partsCount').html(PartCount+ " Part");
        	     }else{
        	       jQuery('#partsCount').html(PartCount+ " Parts");
        	    }
        	    if(invoiceCount == 1|| invoiceCount== 0){
        	       jQuery('#invoiceCount').html(invoiceCount+ " Invoice");
        	    }else{
        	      jQuery('#invoiceCount').html(invoiceCount+ " Invoices");
        	   }
        	    
        	    var partExistInd= '${partExistInd}';
                var partsAvailable = '${fn:length(summaryDTO.invoiceParts)}';
                
                if(partExistInd=='NO_PARTS_REQUIRED'){
              		jQuery("#partSummaryViewDescription").html("There are no parts required for this service order.");
              		
               }else{
            	   if(partsAvailable == 0){
                 		jQuery("#partSummaryViewDescription").html("There are no parts added to the service order by provider.");
                 		
            	   }else{
                		jQuery("#partSummaryViewDescription").html("The parts listed below are added to the service order by provider.");
     
            	   }
            		   
            	 
               }
        	    
        	    
   }
   function showViewPart(invoiceId){
		disableCursor();
		fnWaitForResponseShow("Loading...");
        jQuery("#viewPartModal").load("loadEditpart.action?invoiceId="+invoiceId +"&partSummaryMode=view" , function(){
	    enableCursor();
	    fnWaitForResponseClose();
		jQuery("#viewPartDiv").css("display","block");
		jQuery("#viewInvoiceDiv").css("display","none");
		});
     }
   function showViewInvoiceNo(invoiceNo){
		disableCursor();
		fnWaitForResponseShow("Loading...");
        var soId= jQuery("#partSoIdInvoice").val();
        var invoiceNo = encodeURIComponent(invoiceNo);
       jQuery("#viewInvoiceModal").load("loadInvoiceOnInvoiceNumber.action?invoiceNum="+invoiceNo+"&soId="+soId +"&partSummaryMode=view",function() {
		enableCursor();
		fnWaitForResponseClose();
		jQuery("#viewInvoiceDiv").css("display","block");
		jQuery("#viewPartDiv").css("display","none");
		});
   }
   
   function fnWaitForResponseShow(msg){
   	jQuery("#loadMsg").html(msg);
       jQuery("#waitPopUp").show();
	}
    
    function fnWaitForResponseClose(){
         jQuery("#loadMsg").html('');
        jQuery("#waitPopUp").hide();
	}
       
    function disableCursor(){
       	jQuery("#overLay").show();
    }
	function enableCursor(){
			jQuery("#overLay").hide();
	}
       
</script>
<style type="text/css">
    .partNoView{ 
                background-color: Lightgray;
			    border: 1px solid black;
				height: 25px;
				position: relative;
				width: 100%;

               }
    .PartSpan{ 
               font-size: 10px;
               font-weight: bold;
               margin-left: 10px;
              }
     .partNoSpan{margin-left: 5px; margin-top: 10px;font-weight:bold;font-size: 10px;
                 }
     .PartNovalue{ 
                 font-size: 10px;
                 font-weight: bold;
                 }
     .PartNoValue{
     }
     .partNameValue{
     }
    .PartDispalyDiv{
                    margin-left: 5px;
                    margin-top: 15px;
                    position: relative;
                    }
    .partNameView{ 
                   font-size: 10px;
                   font-weight: bold;
                   margin-left: 100px;
                   position: relative;}
    .PartStatus{
                 margin-left: 10px;
                 margin-top: 30px;
                 }
    .partStatusView{
                    font-size: 10px;
                 font-weight: bold;}
    .partStatusValue{font-size: 11px;
                    margin-left: 20px;}
    .unitCostQtyDiv{margin-left: 10px;
                   margin-top: 20px;
    }
    .unitcostValue{margin-left: 20px;
                  }
    .unitCostSpan{font-size: 10px;
                 font-weight: bold;}
    .QtySpan{
               font-size: 10px;
               font-weight: bold;
               margin-left: 56px;
         }
    .qtySpanValue{}
    .invoiceNoView{
                margin-top: 10px;
                position: relative;
				   }
    .invoiceNoTextView{ font-size: 10px;
				    font-weight: bold;
				    margin-left: 10px;}
    .invoiceNoValueView{margin-left: 20px;}
    .sourceView{     margin-top: 10px;}
    .sourceViewName{ font-size: 10px;
                     font-weight: bold;
                     margin-left: 10px;
        }
    .sourceValue{ margin-left: 20px;}   
    .editPartsUploadedFiles{margin-top:20px}
    .invoiceDocumentsParts{ font-size: 10px;
                           font-weight: bold;
                           margin-left: 10px;}
    a.cancelViewPart{margin-left:10px;}
    .invoiceNumber{
                    background-color: Lightgray;
				    border: 1px solid black;
					height: 25px;
					margin-top: -10px;
					position: relative;
					width: 103%;
				    margin-left: -10px;
                   }
     .invoiceNumberInvoice{
                    background-color: Lightgray;
				    border: 1px solid black;
					height: 25px;
					position: relative;
					width: 100%;
                   }
    .invoiceSpan{
				    font-size: 10px;
				    font-weight: bold;
				    margin-left: 5px;}
    .invoicevalue{font-size: 10px;}
    .invoiceDispalyDiv{margin-left: 10px;
                       margin-top: 10px;
                      }
    .invoiceNoText{
				    font-size: 10px;
				    font-weight: bold;}
    .invoiceNoValue{ margin-left: 20px;}
    .sourceDiv{
			    margin-left: 10px;
			    margin-top: 10px;
			    position: relative;}
    .partSourcelabel{
               font-size: 10px;
               font-weight: bold;}
     .partsTableDiv{
                        margin-top:30px;margin-left:20px;
     }
    .invoiceDocumentsParts{margin-left:10px;}
     a.cancelViewInvoice{
                        margin-left:10px;
                        text-decoration: underline;
     }
    .boldSpan{
               font-weight: bold;
               font-size: 10px;
                    }   
     tr.spaceUnder > td {
                          padding-bottom: 1em;
                         } 
    .countDisplayDiv {
	              margin-top: -30px;
	              position: absolute;
                     }
   a.atagAddEdit {
	text-decoration: underline;
	}
	.waitLayer{
			display: none;
			z-index: 999999999;
			height: 40px; 
			overflow: auto; 
			position: fixed;
			top: 250px;
			left: 45%;
			border-style:double;
			background-color: #EEEEEE;
			border-color: #BBBBBB;
			width: 125px;
			border-width: 4px;
			-webkit-box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);
			-moz-box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);
			box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);
			}
.refreshMsgIcon{
			float: right;
			font-size: 15px;
			padding: 3px;
			cursor: pointer;
			line-height: 20px;}
.refreshMsg{
			background-image: url(${staticContextPath}/images/ajax-loader.gif);
			background-position: 20px center;
			background-repeat: no-repeat;
			padding-left: 50px; 
			padding-top: 5px;
			height: 20px;
			}
td.buyer_parts_table {     
		padding-top: 4px;
		padding-bottom:4px;
	}
</style>
</head>
<body>
<div class="viewmodeparts" style="position:relative;width=100%;height:auto;">
		<div id="waitPopUp" class="waitLayer">
		<div style="padding-left: 0px; padding-top: 5px; color: #222222;">
			<div class="refreshMsg">
				<span id="loadMsg">Loading...</span>
			</div>
		</div>
		</div>
<span  class="partSummaryViewDescription" id="partSummaryViewDescription" style="width:100%" ></span>
   <c:if test = "${null != summaryDTO.invoiceParts && not empty summaryDTO.invoiceParts}">
   <input type="hidden" value="${SERVICE_ORDER_ID}" id="partSoIdInvoice">
   <table id="addtable" width="99%" class="installed_parts" cellpadding="0" border="1" bordercolor="grey" style="display: block">
         <thead>
			<tr>
				<td class="installed_parts_odd" align="left" width="12%" style="padding-left:4px">Part Number</td>
				<td class="installed_parts_odd" align="center" width="12%">Part Name</td>
				<td class="installed_parts_odd" align="center" width="14%">Part Status</td>
				<td class="installed_parts_odd" align="center" width="12%">Invoice#</td>
				<td class="installed_parts_odd" align="center" width="10%">Proof Of Invoice</td>
				<td class="installed_parts_odd" align="center" width="10%">Qty</td>
				<td class="installed_parts_odd" align="right" width="10%" style="padding-right: 4px;">Unit Cost</td>
				<td class="installed_parts_odd" align="right" width="10%">Retail Price</td>
				<td class="installed_parts_odd" align="right" width="10%" style="cursor: pointer; background-color: #00A0D2;padding-right: 4px;">
				    <span id="EstNtProvPymtHoverHoverSummaryView" class="glossaryItem" style="color: white; font: 10px/20px Verdana, Arial, Helvetica, sans-serif;">Est.&nbsp;&nbsp;Net
						Provider Payment
					</span>
				</td>	
			</tr>
		</thead>
		<tbody>
		   <c:forEach items="${summaryDTO.invoiceParts}" var="invoicePart" varStatus="status" >
		    <tr id="tablerowEdit${status.count}">
				<td class="buyer_parts_table" id="partNoEdit_${status.count}" style="padding-left:4px" width="12%" align="left">
				     <a class="atagAddEdit" href="javascript:void(0)" onclick="showViewPart(${invoicePart.invoicePartId});"> ${invoicePart.partNo}</a>
				</td>
				<td class="buyer_parts_table" id="partnameEdit_${status.count}" align="center" width="12%">
				<div id="partEditPart_${status.count}" style='width: 100px; word-wrap: break-word'>
					<c:choose>
						<c:when test="${not empty invoicePart.description && fn:length(invoicePart.description) > 10}">
								${fn:substring(invoicePart.description,0,10)} <strong>...</strong>
						</c:when>
						<c:when test="${not empty invoicePart.description && fn:length(invoicePart.description) <= 10}">
								${invoicePart.description}
						</c:when>
						<c:otherwise>&nbsp;</c:otherwise>
					</c:choose>
				</div>
				</td>
				<td class="buyer_parts_table" id="partStatusEdit_${status.count}" align="center" width="14%">
				  <div style="word-wrap: break-word">
						 ${invoicePart.partStatus}
				  </div>
				</td>
				<td class="buyer_parts_table" id="invoiceNoEdit_${status.count}" align="center" width="12%">
				     <div style='width: 70px; word-wrap: break-word'>
						<a class="atagAddEdit" href="javascript:void(0)" onclick="showViewInvoiceNo('${invoicePart.invoiceNo}');">${invoicePart.invoiceNo}</a>
					</div>
				</td>
				<td class="buyer_parts_table_edit" id="invoiceProof_${status.count}" align="center" width="10%">
				     <div>
				        <c:choose>
				          <c:when test="${not empty invoicePart.invoiceDocExists &&(invoicePart.invoiceDocExists== 'true')}">
						       <img src="${staticContextPath}/images/common/status-green.png"/>
					      </c:when>
					      <c:otherwise>
					           <img src="${staticContextPath}/images/common/status-red.png"/>
					      </c:otherwise>
					    </c:choose>
					</div>
				</td>
				<td class="buyer_parts_table" id="qtyEdit_${status.count}" align="center" width="10%" >
				     <div style='width: 70px; word-wrap: break-word'>
							${invoicePart.qty}
					</div>	
				</td>
				<td class="buyer_parts_table unitCost" id="unitCost_${status.count}" align="right" width="10%" style="padding-right: 4px;">
				    <c:if test="${invoicePart.unitCost != '0.00'}">
				    	$ ${invoicePart.unitCost}
				    </c:if>
				</td>
				<td class="buyer_parts_table_edit retailPrice" id="retailPrice_${status.count}" align="right"  width="10%;" style="padding-top:5px;padding-bottom:5px;padding-right: 4px;">
				      $ ${invoicePart.retailPrice}
				</td>
				<td class="buyer_parts_table_edit estPayement" id="estPayement_${status.count}" align="right"  width="10%;" style="padding-top:5px;padding-bottom:5px;padding-right: 4px;">
				  <c:choose> 
				     <c:when test="${invoicePart.partStatus == 'Installed'}">
				                $ ${invoicePart.estProviderPartsPayment}
				     </c:when>
				     <c:otherwise>NA</c:otherwise>
				  </c:choose>
				</td>
			</tr>
		</c:forEach>
	   </tbody>
	   <tfoot>
			  <tr>
				<td colspan="8" style="padding-left: 500px;padding-top:5px;padding-bottom:5px;"><b>Sub Total for Parts</b></td>
				<td colspan="1" align="right" style="padding-top:5px;padding-bottom:5px;padding-right: 4px;" >
				  <b style="padding-left: 5px;">
				      $ <span class="payment" id="totalPayment" bgcolor="#FFFFFF"; align="left;"> 
				          <fmt:formatNumber value="${summaryDTO.totalEstproviderPayment}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
				       </span>
				  </b>
				</td>
			 </tr>
		   </tfoot>
	</table>
	 <div class="countDisplayDiv" style="margin-top:-10px;">
	       <span id="partsCount" class="partsCount" style="margin-left:20px;"></span>
		   <span id="invoiceCount" class="invoiceCount" style="position:relative;margin-left:215px;"></span>
	  </div>	
	<br/>
	<div id="viewPartModal"></div>
	<div id="viewInvoiceModal"></div>
	<div id="overLay" class="overLay"
		style="display: none; z-index: 1000; width: 100%; height: 100%; position: fixed; opacity: 0.2; filter: alpha(opacity =   20); 
		top: 0px; left: 0px; background-color: #E8E8E8; cursor: wait;">
	</div>
	 <div id="explainer" style="z-index: 1000;"></div>
</c:if>
</div>
</body>
</html>