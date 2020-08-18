<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>


<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />	
<c:set var="OVERVIEW_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_GENERAL_INFO_OVERVIEW_LENGTH%>" />
<c:set var="BUYER_TERM_COND_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_GENERAL_INFO_BUYER_TERM_COND_LENGTH%>" />
<c:set var="SPECIAL_INSTRUCTION_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_GENERAL_INFO_SPECIAL_INSTRUCTION_LENGTH%>" />
<c:set var="TASK_COMMENTS_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_SCOPE_OF_WORK_TASK_COMMENTS_LENGTH%>" />


<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>


<div >
<div  class="darkGrayModuleHdr" style="padding-bottom:1px;padding-top:4px;padding-left:10px; style="background-color: #58585A;background: #58585A url(../images/widgets/titleBarBg.gif)"; color: #FFFFFF;font-family: Verdana,Arial,Helvetica,sans-serif; font-weight: bolder;"> 
		    						 Service Order Creation Type

</div >
<div class="soCreationMenu">
<br><br>
		<table id="partscheck">
              
                       <tr >
                              <td>
                                    <input checked="true" id="soUsingTask"type="radio" name="priceSelectionParts" onclick="createServiceOrderUsingTask();" value="SoUsingTask">
                                     Create Service Order using Service Categories & Tasks.
                              </td>
                              <td>
                                    <input id="soUsingSku" name="priceSelectionParts" type="radio" onclick="createServiceOrderUsingSku();" value="SoUsingSku">
                                    	  Create Service Order using Service Categories & SKU

                              </td>
                        </tr>

</table>
<br><br>
<div id="serviceOrderUsingSKU"  style="display:none;">
<div class="darkGrayModuleHdr" style="padding-bottom:4px;padding-top: 1px; padding-left:2px;font-size:10px;style="background: #58585A url(../images/widgets/titleBarBg.gif)";"> 
<p  class="menugroup_head" onclick="expandSkuDetailWidget('${staticContextPath}','newSku','skuCatMenu');">&nbsp;
			<img class="newSku"  src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;
    						SKU Categories & SKU</p>
</div>
<div class="skuCatMenu">
<br><br>
<div   style="padding-left:20px;font-size:10px">
Choose a main SKU Category and associated SKU from our drop down menu and answer assessment questions to provide details about your project. 
</div>
<div id="errorForSkuCategory" style="display:none;padding-top: 10px;padding-left:20px;font-size:10px;color:red;">Please select SKU Category</div>
<div id="errorForSku" style="display:none;padding-top: 10px;padding-left:20px;font-size:10px;color:red;">Please select SKU</div>
<table>
	<tr >
		<td style="padding-bottom:5px;padding-left:20px;font-size:10px;padding-top:20px;">
							SKU Category&nbsp;&nbsp;&nbsp;<font color="red">*</font>
		</td>
<td>
</td>
	</tr>
	
	<tr >
		<td style="padding-bottom:10px;padding-left:20px;font-size:10px;padding-top:00px;" >
	
		  <c:set var="index" value="0"></c:set>
		  <tags:fieldError id="SKUCategory" >
			<select style="font-size:10px;text-align:left;width: 250px;
			    height: 20px;
			    margin: 0 auto;
			    display: block;" id="selectSkuMainCategory" onchange="showSkuNameByCategory();">
<c:choose>
			<c:when test="${(not empty selectedSkuCategoryName) && (selectedSkuCategoryName!='Select One')&& fn:length(scopeOfWorkDTO.skus)>0}">
			    <option id="skuMainCategory">${selectedSkuCategoryName}</option>
			    </c:when >
  <c:otherwise>
			  	<option id="skuMainCategory"  onclick="showSkuNameByCategory();" value="-1">-Select One-</option>
    			<c:forEach var="buyerSkuCategoryList" items="${buyerSkuCategoryList}" varStatus="dtoIndex" >  
    				<c:set var="index" value="${index+1}"></c:set>
  					<option id="skuMainCategory" class="categoryName${buyerSkuCategoryList.categoryId}"  value="${buyerSkuCategoryList.categoryId}"  onclick="showSkuNameByCategory();">${buyerSkuCategoryList.categoryName}</option>  
				</c:forEach> 
			
  </c:otherwise>
  </c:choose>
</select>
</tags:fieldError>
	<input type="hidden" name="skuCategoryHidden" id="skuCategoryHidden" value="${skuCategoryHidden}" />
		</td>

<td>
</td>
	</tr>
	
</table>
<div id="loadSkuImage" style="position:relative;left:50px;top:0px;"></div>
<div id="selectSkuName"  style="display:block;">
<jsp:include page="panel_select_sku_category.jsp"/>
</div>

</div>
</div>

</div>
</div>




