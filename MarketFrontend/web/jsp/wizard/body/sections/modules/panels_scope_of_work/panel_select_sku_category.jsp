<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />	
<c:set var="OVERVIEW_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_GENERAL_INFO_OVERVIEW_LENGTH%>" />
<c:set var="BUYER_TERM_COND_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_GENERAL_INFO_BUYER_TERM_COND_LENGTH%>" />
<c:set var="SPECIAL_INSTRUCTION_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_GENERAL_INFO_SPECIAL_INSTRUCTION_LENGTH%>" />
<c:set var="TASK_COMMENTS_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_SCOPE_OF_WORK_TASK_COMMENTS_LENGTH%>" />




<table>
   <tr>
       <td style="padding-bottom:5px;padding-left:20px;font-size:10px;padding-top:00px;">
         SKU&nbsp;&nbsp;&nbsp;<font color="red">*</font>
       </td>
       <td></td>
  </tr>
  <tr>
     <td style="padding-bottom:10px;padding-left:20px;font-size:10px;padding-top:00px;" >
         <tags:fieldError id="SKUNAME" >
			<p id="skuErr">
			<select style="font-size:10px;
				    height: 25px;
				    margin: 0 auto;width:250px;
				    display: block;" id="selectSkuNameByCategory" name="selectedSkuName" onchange="showAddOption();" disabled="disabled">
              <c:choose>
				  <c:when  test="${not empty scopeOfWorkDTO.skus}">
					  <option id="skuNameByCategory" value=" ">${ fn:length(scopeOfWorkDTO.skus)} Selected</option>
                 </c:when>
                 <c:otherwise>
			  		<option id="skuNameByCategory" value="-1" onclick="showAddOption();" >-Select One-</option>
                 </c:otherwise>
               </c:choose>
    		   <c:choose>
                   <c:when test="${not empty buyerSkuNameForSkuList}">
    					<c:forEach var="buyerSkuName" items="${buyerSkuNameForSkuList}" varStatus="dtoIndex" > 
	    					<option  value="${buyerSkuName.skuId}#${buyerSkuName.sku}@${buyerSkuName.templateId}" onclick="showAddOption();" class="skuName${buyerSkuName.skuId}">${buyerSkuName.sku}</option>  
						</c:forEach>
				   </c:when>  
				   <c:otherwise>
					    <c:forEach var="buyerSkuName" items="${selectedSkuCategory}" varStatus="dtoIndex" > 
	    				  <option  value="${buyerSkuName.skuId}#${buyerSkuName.sku}@${buyerSkuName.templateId}" onclick="showAddOption();" class="skuName${buyerSkuName.skuId}">${buyerSkuName.sku}</option>  
						</c:forEach>
				   </c:otherwise> 
			   </c:choose> 
			</select>
			<p>
        </tags:fieldError>
       <input type="hidden" name="selectedSku" id="selectedSku" value="${selectedSkuName}"/>
       <input type="hidden" name="onlySkuDeleteInd" id="onlySkuDeleteInd" value="${onlySkuDeleteInd}"/>
       <input type="hidden" name="selectedSkuCategoryId" id="selectedSkuCategoryId" value="${selectedSkuCategoryId}"/>

    </td>
    <td style="padding-bottom:10px;padding-left:20px;font-size:10px;padding-top:00px;">
        <input class="button action" id="addSKU" style="width:70px;display: block;
			font-size:11px;background-image: url('/ServiceLiveWebUtil/images/common/button-action-bg.png');
			border: 1px solid #B1770B;
			border-radius: 5px 5px 5px 5px;
			font-weight: bold;cursor: pointer;
			height: 20px;padding: 2px 2px 15px;
			font-family: Arial,Tahoma,sans-serif;margin-top: 5px;" 
			disabled="disabled" type="button" 
			value="Add SKU" onclick="tinyMCE.triggerSave();showSkuRelatedDetail();"/>
    </td>
  </tr>
 </table>
<div id="skuLogo" style="position:relative;left:50px;top:0px;"></div>
<div id="skuDetailBySkuId"  style="display:none;">
<jsp:include page="panel_sku_detail_by_skuId.jsp"/>
</div>
<table id="addSkutable" class="sku_details" style="position:relative;top:-40px;left:15px;">
		<tr><td>
				<table id="addSKuDetailRow" style="display:block;">	
			<tbody class="divForSku">
			<s:set name="skuStatus" value="0"></s:set>
			<s:iterator status="status" value="skus">
					<tr id='tablerow_${skuStatus}'>
							<td>
							<div id='skuDiv_${skuStatus}' style='background-color:white;width:570px;position:relative;top:-25px;'>
									<div class='hdrTable' style='padding-bottom:2px;padding-top:1px;padding-left:10px;font-size:10px;width:560px;margin-top:10px'>
										<p  class='menugroup_head' onclick="expandSkuDetailWidget('${staticContextPath}','newSkuDiv_${skuStatus}','skuDetail_${skuStatus}');">
										<img class='newSkuDiv_${skuStatus}' src='${staticContextPath}/images/widgets/arrowDown.gif'/>&nbsp;${scopeOfWorkDTO.skus[skuStatus].taskName} 
			<input id="skus[${skuStatus}].taskName" name="skus[${skuStatus}].taskName" value="${scopeOfWorkDTO.skus[skuStatus].taskName}" type="hidden"/>
<input id="skus[${skuStatus}].categoryId" name="skus[${skuStatus}].categoryId" value="${scopeOfWorkDTO.skus[skuStatus].categoryId}" type="hidden"/>
<c:if test='${skuStatus==0}'>
<input id="scopeOfWorkDTO.mainServiceCategoryId" name="scopeOfWorkDTO.mainServiceCategoryId" type="hidden" value="${mainServiceCategoryId}"/>
<input type="hidden"  id="scopeOfWorkDTO.mainServiceCategoryName" name="scopeOfWorkDTO.mainServiceCategoryName" value="${scopeOfWorkDTO.mainServiceCategoryName}"/>
</c:if>
<input id="skus[${skuStatus}].subCategoryId" name="skus[${skuStatus}].subCategoryId" value="${scopeOfWorkDTO.skus[skuStatus].subCategoryId}" type="hidden"/>
<input id="skus[${skuStatus}].sku" name="skus[${skuStatus}].sku" value="${scopeOfWorkDTO.skus[skuStatus].sku}" type="hidden"/>
<input id="skus[${skuStatus}].skillId" name="skus[${skuStatus}].skillId" value="${scopeOfWorkDTO.skus[skuStatus].skillId}" type="hidden"/>
<input id="skus[${skuStatus}].price" name="skus[${skuStatus}].price" value="${scopeOfWorkDTO.skus[skuStatus].price}" type="hidden"/>
<input id="skus[${skuStatus}].sellingPrice" name="skus[${skuStatus}].sellingPrice" value="${scopeOfWorkDTO.skus[skuStatus].sellingPrice}" type="hidden"/>
<input id="skus[${skuStatus}].finalPrice" name="skus[${skuStatus}].finalPrice" value="${scopeOfWorkDTO.skus[skuStatus].finalPrice}" type="hidden"/>
<input id="skuIdForSku[${skuStatus}]" name="skus[${skuStatus}].skuIdForSku" value="${scopeOfWorkDTO.skus[skuStatus].skuIdForSku}" type="hidden"/>
<input id="templateIdForSku[${skuStatus}]" name="skus[${skuStatus}].templateIdForSku" value="${scopeOfWorkDTO.skus[skuStatus].templateIdForSku}" type="hidden"/>			
</p>
									</div>
								<div class='skuDetail_${skuStatus}' style='background-color: #EDEDED;'>
									<input type='hidden'  id='skuNameId_${skuStatus}' value='"+skuNameId+"'/><input type='hidden'  id='tempSkuId_${skuStatus}' value=''/>
									<table>
										<tr>
											<td align="top" style='padding-left:10px;font-size:10px;'><b>Category</b></td>
											<td></td>
											<td>												
											<label>${scopeOfWorkDTO.skus[skuStatus].category}</label>
											</td>
										</tr>
										<tr>
										<td align='top' style='padding-left:10px;font-size:10px;'><b>Sub-Category</b>	</td>
											<td></td>
											<td>												
											<label>${scopeOfWorkDTO.skus[skuStatus].subCategory}</label>
											</td>
										</tr>
										<tr>
											<td align='top' style='padding-left:10px;font-size:10px'><b>Skill</b></td>
											<td></td>
											<td>												
											<label>${scopeOfWorkDTO.skus[skuStatus].skill}</label>
											</td>
										</tr>
										<tr>
										
											<td align='top' style='padding-bottom:20px;padding-left:12px;font-size:10px;'>
											<input id="skus[${skuStatus}].category" name="skus[${skuStatus}].category" value="${scopeOfWorkDTO.skus[skuStatus].category}" type="hidden"/>
											</td>
											<td  align='top' style='padding-bottom:20px;padding-left:10px;font-size:10px'>
												<input id="skus[${skuStatus}].subCategory" name="skus[${skuStatus}].subCategory" value="${scopeOfWorkDTO.skus[skuStatus].subCategory}" type="hidden"/>
											</td>
											<td align='top' style='padding-bottom:20px;padding-left:10px;font-size:10px'>	
											<input id="skus[${skuStatus}].skill" name="skus[${skuStatus}].skill" value="${scopeOfWorkDTO.skus[skuStatus].skill}" type="hidden"/>
											</td>
										</tr>

										</table>

		<table>
			<tr><td align='top' style='padding-bottom:5px;padding-left:10px;font-size:10px;'><b>Task Comments</b></td>
			</tr>
			<tr>
				<td align='top' style='padding-left:10px;font-size:10px;'>												
											
											<!-- Changes for jira SL-20729 for making comments section ediatble and saving that in so_tasks table in DB -->
											<s:textarea name="skus[%{#status.index}].comments" id="skus_task_comments_%{#status.index}"
											value="%{scopeOfWorkDTO.skus[#status.index].comments}" cssClass=" taskCommentsTinyMce shadowBox"
											cssStyle="width: 620px" theme="simple" />
											
				</td>
			</tr>
		</table>
		</div></div></td><td>
	<div style='padding-left:20px;'><a href='javascript:deleteRow(${skuStatus});'>Delete SKU</a></div></div>		
		</td>
		</tr>
		
<s:set name="skuStatus" value="%{#attr['skuStatus']+1}"></s:set>
		</s:iterator>
		
				</tbody>
				</table>
				
		</td></tr>
</table>



