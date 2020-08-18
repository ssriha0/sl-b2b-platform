<%@page import="java.util.*"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
 
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />	
<c:set var="soId" scope="request" value="<%=request.getAttribute("SERVICE_ORDER_ID")%>" />
<!-- SL-20728 Enable Rich text editing -->
 <script src="${staticContextPath}/javascript/tinymce/tinymce.min.js"></script>
<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="wizard.tabScopeOfWork"/>
	</jsp:include>

<%-- To maintain main category id selected in task part while modifying details in SKU part.
	Main category for SOUsingTask is kept in session.
 --%>
<c:set var="taskCount" value="${fn:length(scopeOfWorkDTO.tasks) }" scope="request"></c:set>
<c:choose>
<c:when test="${actionType == 'edit'}" >
  	<c:set var="catIdSession" scope="request" value="${mainServiceCategoryId == null? -1:mainServiceCategoryId}"  ></c:set>

</c:when>
<c:when test="${actionType == 'copy'}" >
  	<c:set var="catIdSession" scope="request" value="${mainServiceCategoryId == null? -1:mainServiceCategoryId}"  ></c:set>
</c:when>
<%--SL-19820--%>
<c:otherwise>
	<c:set var="catIdSession" scope="request" value="${mainServiceCategoryId == null? -1:mainServiceCategoryId}"  ></c:set>
</c:otherwise>
</c:choose>
<c:choose>
<c:when test="${catIdSession == -1}" >
<%-- Enable 'Main Service Category' list while nothing is selected --%>
	<c:set var="disableInd" value="false" ></c:set>
</c:when>
<c:when test="${fn:length(scopeOfWorkDTO.tasks)==0 || (fn:length(scopeOfWorkDTO.tasks)==1 && scopeOfWorkDTO.tasks[0].taskName ==null) }">
<%-- Enable 'Main Service Category' list while no task has been added also when one task details form is
displayed and no details has been entered. --%>
		<c:choose>
		<c:when test="${createdWithoutTasksForInvalidJobCodes == 'true'}">
			<c:set var="disableInd" value="true" ></c:set>
		</c:when>
		<c:otherwise>
		<c:set var="disableInd" value="false" ></c:set>
		</c:otherwise>
		</c:choose>
</c:when>
<c:otherwise>
	<c:set var="disableInd" value="true" ></c:set>
</c:otherwise>
</c:choose>
<script type="text/javascript">
	jQuery(document).ready(function() {	
				var skuIndicator='${serviceOrderTypeIndicator}';		
				var disableInd = '${disableInd}';
				var sessionCatId = '${catIdSession}';
				var sizeOfDiv=jQuery("table#addSKuDetailRow tbody.divForSku").children().length;
				var flag = '0';
				if(disableInd == 'true' && sessionCatId != -1){
					jQuery("#mainServiceCategory").attr('disabled','true');
				}else{
					var taskCount = '${taskCount}';
					if(taskCount > 0 && sessionCatId != -1){
						jQuery("#mainServiceCategory").attr('disabled','true');
					}else{
						jQuery("#mainServiceCategory").removeAttr('disabled');
					}
				}
				//to keep the sku category selected in case of error
				var skuCategoryHidden = jQuery("#skuCategoryHidden").val();
				if(skuCategoryHidden!="-1"){ 
					var selectedSKUCatClass=".categoryName"+skuCategoryHidden;
					jQuery(selectedSKUCatClass).attr('selected', true);
					jQuery('#selectSkuMainCategory').attr('disabled',true);	 
				}
				if(skuIndicator != ""){
					var skuName = jQuery("#selectedSku").val();
					if(skuName!=undefined){
						var indexOfSku = skuName.indexOf('#');
						var skuNameId=skuName.substring(0, indexOfSku );			
						var selectedSKUClass = ".skuName"+skuNameId;
						jQuery(selectedSKUClass).attr('selected', true);
						jQuery('#addSKU').removeAttr('disabled');
					}
				}
				if(skuIndicator=='SoUsingSku'){
						jQuery('#selectSkuNameByCategory').removeAttr('disabled');
						jQuery("#skuDetailBySkuId").show();
						jQuery('#addSKU').show();
						jQuery("#usingTasks").hide();
						document.getElementById('serviceOrderUsingSKU').style.display="block";
						jQuery("#soUsingSku").attr("checked",true);
						var skuindicator=jQuery("#soUsingSku").val();
//var dd='true'
//jQuery("#sowDocumentsAndPhotosDTO.skuIndicatorForDocument").val(dd);
							jQuery("#serviceOrderTypeIndicator").val(skuindicator);
							var sizeOfDiv=jQuery("table#addSKuDetailRow tbody.divForSku").children().length;
							if(sizeOfDiv==0){
								 var selectPrevSKUCat='${selectedSkuCategoryName}';
					                        // var selectPrevSKUCatClass="."+selectPrevSKUCat;
				                            // jQuery(selectPrevSKUCatClass).attr('selected', true);
								 var catId='';
				                     <c:if test="${not empty buyerSkuCategoryList}">
				                     <c:forEach var="buyerSkuCategoryList" items="${buyerSkuCategoryList}">  
    								<c:if test="${buyerSkuCategoryList.categoryName == selectedSkuCategoryName}">
  										var categoryId=${buyerSkuCategoryList.categoryId};
  										catId=categoryId;
  									</c:if>
  								</c:forEach> 
						 		 </c:if>
								/*if(catId!=''){
									var selectPrevSKUCatClass=".categoryName"+catId;
									jQuery(selectPrevSKUCatClass).attr('selected', true);
								}*/
								var mainSKUIdAfterDelete = document.getElementById("selectSkuNameByCategory");
								mainSKUIdAfterDelete.disabled=false;		      							 
								jQuery("#addSKuDetailRow").hide();
								jQuery("#skuDetailBySkuId").hide();
								jQuery("#title").val(" ");
								jQuery("#overview").val(" ");
								jQuery("#buyerTandC").val(" ");
								jQuery("#specialInstructions").val(" ");
								var onlySkuDeleteInd = jQuery("#onlySkuDeleteInd").val();
								if(skuCategoryHidden=="-1"  ||(skuCategoryHidden!="-1" && onlySkuDeleteInd == "1")){ 
								var disableMainCat=document.getElementById("selectSkuMainCategory");
									disableMainCat.disabled=false;
								}
							}else{
								 var disableMainCat=document.getElementById("selectSkuMainCategory");
								 disableMainCat.disabled=true;
							}
							var tableLen=0;
							var newSkuDiv_="newSkuDiv_";
							var divId;	var imgId;
							var skuDetail_="skuDetail_"
							jQuery("table#addSKuDetailRow tbody.divForSku").children().not(':last-child').each(function(){
								divId=newSkuDiv_+tableLen;
								imgId=skuDetail_+tableLen;
								expandOnlyLastSkuDetailWidget('${staticContextPath}',divId,imgId);
								tableLen=tableLen+1;
							});
							// Changes for SL-20703-END
							//To disable Add SKU button user selects a sku in each step
							var skuNameByCatId = jQuery("#selectSkuNameByCategory").val();
							//SL-20729 : Commenting code change to disable add sku button
							/* if(skuNameByCatId ==""||skuNameByCatId ==" "||skuNameByCatId =="-1"){
								jQuery("#addSKU").attr("disabled", "disabled");
							} */
							//SL-20729 : Reverting the code changes done for this jira
							if(skuNameByCatId ==""||skuNameByCatId ==" "||skuNameByCatId =="-1"){
								jQuery("#addSKU").attr("disabled", "disabled");
							} 
						}else if(skuIndicator=='SoUsingTask'){
							var sizeOfDiv=jQuery("table#addSKuDetailRow tbody.divForSku").children().length;
						 	if(sizeOfDiv==0){
							 	var selectPrevSKUCat='${selectedSkuCategoryName}';
				                        // var selectPrevSKUCatClass="."+selectPrevSKUCat;
			                            // jQuery(selectPrevSKUCatClass).attr('selected', true);
								var catId='';
			                     <c:if test="${not empty buyerSkuCategoryList}">
			                     <c:forEach var="buyerSkuCategoryList" items="${buyerSkuCategoryList}">  
									<c:if test="${buyerSkuCategoryList.categoryName == selectedSkuCategoryName}">
										var categoryId=${buyerSkuCategoryList.categoryId};
										catId=categoryId;
									</c:if>
								</c:forEach> 
					  			</c:if>
								/*if(catId!=''){
									var selectPrevSKUCatClass=".categoryName"+catId;
									jQuery(selectPrevSKUCatClass).attr('selected', true);
								}*/
								var mainSKUIdAfterDelete = document.getElementById("selectSkuNameByCategory");
									if(mainSKUIdAfterDelete !=null){
								mainSKUIdAfterDelete.disabled=false;
									}	
								var disableMainCat=document.getElementById("selectSkuMainCategory");
									if(disableMainCat!=null){
								disableMainCat.disabled=false;
									}
						 }else{
							 var disableMainCat=document.getElementById("selectSkuMainCategory");
							 disableMainCat.disabled=true;							 
							 jQuery("#selectSkuNameByCategory").removeAttr('disabled');
							 jQuery("#addSKU").removeAttr('disabled');
						 }
					}
					formatSubCategory();
					if(${actionType=='edit'}){
						formatSubCategory();
						var skuAndTaskCheck='${serviceOrderSkuIndicator}';
							if(skuAndTaskCheck=="true")
								{
									var mainSoSkuCategoryId='${mainServiceCategoryId}';
									jQuery("div .formNavButtons").children(".skuMainId").val(mainSoSkuCategoryId);
									jQuery("#selectSkuName").load("soLoadSkuNameByCategory.action?&categoryId="+mainSoSkuCategoryId+"&SERVICE_ORDER_ID=${soId}",
											function(){
											var disableMainCat=document.getElementById("selectSkuMainCategory");
											    disableMainCat.disabled=true;
											jQuery("#skuCreationFacilityPage").hide();
											//jQuery("#soUsingSku").attr("checked",true);
											//jQuery("#usingTasks").hide();
											//var skuindicator=jQuery("#soUsingSku").val();
											//jQuery("#serviceOrderTypeIndicator").val(skuindicator);
											//document.getElementById('serviceOrderUsingSKU').style.display="block";
											//document.getElementById('selectSkuName').style.display="block";
											//jQuery(".skuCatMenu").show();
											//jQuery("#skuDetailBySkuId").show();
											//jQuery("#skuDetailBySkuId").show();
											var tableLen=0;
												var newSkuDiv_="newSkuDiv_";
												var divId;	var imgId;
												var skuDetail_="skuDetail_"
														jQuery("table#addSKuDetailRow tbody.divForSku").children().not(':last-child').each(function(){
														divId=newSkuDiv_+tableLen;
														imgId=skuDetail_+tableLen;
														expandOnlyLastSkuDetailWidget('${staticContextPath}',divId,imgId);
														tableLen=tableLen+1;
														});
											});
								}
					
								else
								{
									jQuery("#serviceOrderTypeIndicator").val("soUsingTask");	
									jQuery("#skuCreationFacilityPage").hide();
								}
							}
					  
});

function createServiceOrderUsingTask()
{ 
		document.getElementById('usingTasks').style.display="block";
		var taskindicator=jQuery("#soUsingTask").val();
		var indicator=jQuery("#serviceOrderTypeIndicator").val();
		jQuery("#serviceOrderTypeIndicator").val(taskindicator);
		jQuery("#serviceOrderUsingSKU").hide();
	
	if(indicator !='soUsingTask')
	{
		window.location.href='soWizardScopeOfWorkCreate_switchtoTask.action?SERVICE_ORDER_ID=${soId}';
		
		// to display general info for create service order using task.
		
		jQuery("#titleforSku").val(jQuery("#title").val());
		jQuery("#overviewforSku").val(jQuery("#overview").val());
		jQuery("#overviewCharsforSku").val(jQuery('[name=overview_leftChars]').val());
		jQuery("#buyerTandCforSku").val(jQuery("#buyerTandC").val());
		jQuery("#buyerTandCCharsforSku").val(jQuery('[name=buyerTandC_leftChars]').val());
		jQuery("#specialInstructionsforSku").val(jQuery("#specialInstructions").val());
		jQuery("#specialInstructionsCharsforSku").val(jQuery('[name=specialInstructions_leftChars]').val());
		
		
		
		
		jQuery("#title").val(jQuery("#titleforTask").val());
		jQuery("#overview").val(jQuery("#overviewforTask").val());
		jQuery('[name=overview_leftChars]').val(jQuery("#overviewCharsforTask").val());
		jQuery("#buyerTandC").val(jQuery("#buyerTandCforTask").val());
		jQuery('[name=buyerTandC_leftChars]').val(jQuery("#buyerTandCCharsforTask").val());
		jQuery("#specialInstructions").val(jQuery("#specialInstructionsforTask").val());
		jQuery('[name=specialInstructions_leftChars]').val(jQuery("#specialInstructionsCharsforTask").val());
		
	 }
}

function createServiceOrderUsingSku()
{ 
		jQuery("#usingTasks").hide();
		var skuindicator=jQuery("#soUsingSku").val();
		var indicator=jQuery("#serviceOrderTypeIndicator").val();
		jQuery("#serviceOrderTypeIndicator").val(skuindicator);
		document.getElementById('serviceOrderUsingSKU').style.display="block";	
		var sizeOfDiv=jQuery("table#addSKuDetailRow tbody.divForSku").children().length;
		if(sizeOfDiv != 0){
			document.getElementById('skuDetailBySkuId').style.display="block";
		}
			
	
		
		if(indicator!='soUsingSku')
	{
		window.location.href='soWizardScopeOfWorkCreate_switchtoSku.action?SERVICE_ORDER_ID=${soId}';
	
	// to display general info for create service order using sku.
	
		jQuery("#titleforTask").val(jQuery("#title").val());
		jQuery("#overviewforTask").val(jQuery("#overview").val());
		jQuery("#overviewCharsforTask").val(jQuery('[name=overview_leftChars]').val());
		jQuery("#buyerTandCforTask").val(jQuery("#buyerTandC").val());
		jQuery("#buyerTandCCharsforTask").val(jQuery('[name=buyerTandC_leftChars]').val());
		jQuery("#specialInstructionsforTask").val(jQuery("#specialInstructions").val());
		jQuery("#specialInstructionsCharsforTask").val(jQuery('[name=specialInstructions_leftChars]').val());
		
		
		
		jQuery("#title").val(jQuery("#titleforSku").val());
		jQuery("#overview").val(jQuery("#overviewforSku").val());
		jQuery('[name=overview_leftChars]').val(jQuery("#overviewCharsforSku").val());
		jQuery("#buyerTandC").val(jQuery("#buyerTandCforSku").val());
		jQuery('[name=buyerTandC_leftChars]').val(jQuery("#buyerTandCCharsforSku").val());
		jQuery("#specialInstructions").val(jQuery("#specialInstructionsforSku").val());
		jQuery('[name=specialInstructions_leftChars]').val(jQuery("#specialInstructionsCharsforSku").val());
		
		}
}

function showAddOption(){
	var skuMainCatErroId=jQuery('#selectSkuMainCategory').val();
	var indexOfSku =jQuery('#selectSkuNameByCategory').val();
	var skuErrorId= indexOfSku .indexOf('#');
		if(skuErrorId>1){				
			var skuNameId=skuMainCatErroId.substring(0, skuErrorId);
		}else{
			skuNameId=skuErrorId;
		}
		if(skuMainCatErroId==-1){}
		else{
			if(skuNameId==-1){
				jQuery('#addSKU').attr('disabled','disabled');
			}else{
				jQuery('#addSKU').removeAttr('disabled');
				jQuery('#addSKU').show();
				}
		}
 }

function showSkuNameByCategory(){
			var categoryId=jQuery('#selectSkuMainCategory').val();
			jQuery('#skuCategoryHidden').val(categoryId);
			if(categoryId==-1)
			{
				//jQuery('#errorForSkuCategory').show();

			}
				else{
				//jQuery('#errorForSkuCategory').hide();

				jQuery("#loadSkuImage").html('<img src="/ServiceLiveWebUtil/images/loading.gif" width="572px" height="160px"/>');
				jQuery("#selectSkuName").load("soLoadSkuNameByCategory.action?&categoryId="+categoryId+"&SERVICE_ORDER_ID=${soId}",
					function() {jQuery("#loadSkuImage").hide();
					jQuery("#selectSkuName").show();
					jQuery('#selectSkuNameByCategory').removeAttr('disabled');		
		           // var mainCatId=document.getElementById("selectSkuMainCategory");
			      //  mainCatId.disabled=true;
			        jQuery('#selectSkuMainCategory').attr('disabled',true);	
			        jQuery('#skuCategoryHidden').val(categoryId);
					});
			}
}


function showSkuRelatedDetail()
{
	//To handle possible error, on clicking Add SKU button when
	//no sku is selected in the select list (18149) 	
	var skuNameByCatId=jQuery('#selectSkuNameByCategory').val();
	if(skuNameByCatId ==""||skuNameByCatId ==" "||skuNameByCatId =="-1"){
		jQuery("#skuErr").addClass("errorBox");
		return false;
	}
	/**End of (18149) **/
	/* var selectedSkuCategoryNew = jQuery('#selectedSkuCategoryId').val(); */
	//SL-20729 Code change : to display slected sku category for multiple sku addition.
	var selectedSkuCategoryNew = jQuery('#selectedSkuCategoryId').val();
	var indexOfSku = skuNameByCatId.indexOf('#');
	var skuNameId=skuNameByCatId.substring(0, indexOfSku );
	var tempSkuId=skuNameByCatId.substring(skuNameByCatId.lastIndexOf("@") + 1);
	var skuName=skuNameByCatId.substring(skuNameByCatId.indexOf("#")+1,skuNameByCatId.indexOf("@"));
	var skuindicator=jQuery("#soUsingSku").val();
	jQuery("#serviceOrderTypeIndicator").val(skuindicator);
	jQuery("#skuLogo").show();
	jQuery("#skuLogo").html('<img src="/ServiceLiveWebUtil/images/loading.gif" width="572px" height="160px"/>');
    var oldTableLength=jQuery("table#addSKuDetailRow tbody.divForSku").children().length;
    var url = "soLoadSkuDetailsBySkuId.action?skuNameId="+skuNameId+"&templateSkuId="+tempSkuId+"&oldTableLength="+oldTableLength+"&SERVICE_ORDER_ID=${soId}";

	jQuery("#skuDetailBySkuId").load(url,
	function() {jQuery("#skuLogo").hide();
				var taskCat=jQuery('#taskCat').val();
				var taskName=jQuery('#taskName').val();
				var subCategory=jQuery('#taskSubCat').val();
				var templateDescription=jQuery('#templateDescr').val();
				var mainSkuCategoryId=jQuery('#mainCatId').val();
				var mmm=jQuery('#mainCatId').val();
				var mainSkuServiceCategoryName=jQuery('#mainServiceCategoryName').val();
				var sotypeIndicator=jQuery('#serviceOrderTypeIndicator').val();
				var skuBidPrice=jQuery('#skuEachBidPrice').val();
				var categoryId=jQuery('#catId').val();
				var subCategoryId=jQuery('#subCatId').val();
				var skillForSku=jQuery('#skillId').val();
				var taskComments=jQuery('#taskComments').val();
				//alert('Task comment in tab_scope_of_work'+ taskComments);
				//taskComments = replacenbsps(taskComments);
				//alert('Task Comments after replace'+ taskComments);
				var partsSuppliedBy =jQuery('#partsSuppliedBy').val();
				var buyerDocumentLogo = jQuery('#buyerDocumentLogo').val();
				var tableLength=jQuery("table#addSKuDetailRow tbody.divForSku").children().length;
				var length;
					if(tableLength>0)
					{
									var str=jQuery("table#addSKuDetailRow tbody.divForSku").children("tr:last").attr("id");
									var len=str.substring(9);
									length =parseInt(len)+1;
					}
					else
					{
									length=tableLength;
									//SL- 20704: Changing the genInfoTitle from category name to taskname
									var genInfoTitle=jQuery('#taskName').val();
									var genInfoOverview=jQuery('#generalInfoOverview').val();
									var genInfoTerms=jQuery('#generalInfoTerms').val();
									var genInfoSpecialInstr=jQuery('#generalInfoSpecialInstructions').val();
									jQuery("#title").val(genInfoTitle);
									jQuery("#overview").val(genInfoOverview);
									tinymce.get('overview').setContent(jQuery("#overview").val());
									jQuery("#buyerTandC").val(genInfoTerms);
									tinymce.get('buyerTandC').setContent(jQuery("#buyerTandC").val());
									jQuery("#specialInstructions").val(genInfoSpecialInstr);
									tinymce.get('specialInstructions').setContent(jQuery("#specialInstructions").val());
									var currSrc = jQuery("#inner_document_grid").attr("src");
									var skuIndicator='true';
									var newSrc=currSrc+"&skuIndicator="+skuIndicator;
									jQuery("#selectedCategoryId").val(skuNameId);
									var cc=2;
									jQuery("#mainServiceCategoryId").val(cc);
									var k=10;
									jQuery("div .formNavButtons").children(".skuMainId").val(mmm);
									jQuery("#inner_document_grid").attr("src", newSrc);
					}
					var e = document.getElementById("selectSkuNameByCategory");
					var k=e.options[0].text;
					var selectedDiv=tableLength+1;
					var totalSkuSelected=selectedDiv+"  Selected";
					jQuery('#selectSkuNameByCategory option[index=0]').text(totalSkuSelected);
					jQuery('#selectSkuNameByCategory').find('option:first').attr('selected', 'selected');	
					var lengthForInput=length-1;
	 					tableLength=tableLength+1;

					var indexForSku=length-1;
					var mainServiceId;
		if(length==0)
		{
		var selectedSkuname=jQuery("#selectSkuMainCategory option:selected").text();
		var mainServiceId="<input id='scopeOfWorkDTO.mainServiceCategoryId' name='scopeOfWorkDTO.mainServiceCategoryId' type='hidden' value='"+mmm+"'/>"+
		"<input type='hidden'  id='scopeOfWorkDTO.mainServiceCategoryName' name='scopeOfWorkDTO.mainServiceCategoryName' value='"+mainSkuServiceCategoryName+"'/>"+
		"<input type='hidden'  id='scopeOfWorkDTO.serviceOrderTypeIndicator' name='scopeOfWorkDTO.serviceOrderTypeIndicator' value='"+sotypeIndicator+"'/>"+
		"<input type='hidden'  id='scopeOfWorkDTO.selectedSkuCategoryName' name='scopeOfWorkDTO.selectedSkuCategoryName' value='"+selectedSkuname+"'/>";
		
		}
		else
		{
		mainServiceId=' ';
		}
		jQuery("#skuDetailBySkuId").show();

		var rowData="<tr id=tablerow_"+length+">"+ 
"<td><div id='skuDiv_"+length+"' style='background-color: #EDEDED;width:570px;'>"+
	"<div class='grayModuleHdr' style='padding-bottom:5px;padding-top:0px;padding-left:10px;font-size:10px;width:550px;'>"+
		"<p  class='menugroup_head' onclick='expandSkuDetailWidget(\"${staticContextPath}\",\"newSkuDiv_"+length+"\",\"skuDetail_"+length+"\")'>&nbsp;"+
			"<img class='newSkuDiv_"+length+"' src='${staticContextPath}/images/widgets/arrowDown.gif'/>&nbsp;"+taskName+""+
			"<input id='skus["+length+"].taskName' name='skus["+length+"].taskName' value='"+taskName+"' type='hidden'/>"+mainServiceId+
			"<input id='skus["+length+"].categoryId' name='skus["+length+"].categoryId' value='"+categoryId+"' type='hidden'/>"+
			"<input id='skus["+length+"].subCategoryId' name='skus["+length+"].subCategoryId' value='"+subCategoryId+"' type='hidden'/>"+
			"<input id='skus["+length+"].skillId' name='skus["+length+"].skillId' value='"+skillForSku+"' type='hidden'/>"+
			"<input id='skus["+length+"].price' name='skus["+length+"].price' value='"+skuBidPrice+"' type='hidden'/>"+
"<input id='skus["+length+"].sellingPrice' name='skus["+length+"].sellingPrice' value='"+skuBidPrice+"' type='hidden'/>"+
"<input id='skus["+length+"].finalPrice' name='skus["+length+"].finalPrice' value='"+skuBidPrice+"' type='hidden'/>"+
"<input id='skus["+length+"].skuIdForSku' name='skus["+length+"].skuIdForSku' value='"+skuNameId+"' type='hidden'/>"+
			"<input id='skus["+length+"].templateIdForSku' name='skus["+length+"].templateIdForSku' value='"+tempSkuId+"' type='hidden'/>"+
		"<input id='skus["+length+"].sku' name='skus["+length+"].sku' value='"+skuName+"' type='hidden'/>"+
			"</p>"+
	"</div>"+
	"<div class='skuDetail_"+length+"'>"+
	"<input type='hidden'  id='skuNameId_"+length+"' value='"+skuNameId+"'/><input type='hidden'  id='tempSkuId_"+length+"' value='"+tempSkuId+"'/>"+
		"<table><tr>"+
				"<td align='top' style='padding-left:10px;font-size:10px;'><b>Category</b></td>"+
				"<td></td>"+
				"<td><label>"+taskCat+"</label></td>"+
				"</tr><tr>"+
				"<td align='top' style='padding-left:10px;font-size:10px;'><b>Sub-Category</b></td>"+
				"<td></td>"+
				"<td><label>"+subCategory+"</label></td>"+
				"</tr><tr>"+
				"<td align='top' style='padding-left:10px;font-size:10px'><b>Skill</b></td>"+
				"<td></td>"+
				"<td><label>"+templateDescription+"</label></td>"+
				"</tr><tr>"+
				"<td align='top' style='padding-bottom:20px;padding-left:10px;font-size:10px;'>"+
				   		"<input id='skus["+length+"].category' name='skus["+length+"].category'  type='hidden' value='"+taskCat+"'/>"+
				"</td><td  align='top' style='padding-bottom:20px;padding-left:10px;font-size:10px'>"+
				   	"<input id='skus["+length+"].subCategory' name='skus["+length+"].subCategory'  type='hidden' value='"+subCategory+"'/>"+
				"</td><td align='top' style='padding-bottom:20px;padding-left:10px;font-size:10px'>"+	
				   	"<input id='skus["+length+"].skill' name='skus["+length+"].skill' type='hidden' value='"+templateDescription+"'/>"+
				"</td></tr></table>"+
		"<table>"+
		"<tr><td align='top' style='padding-bottom:10px;padding-left:10px;font-size:10px;'><b>Task Comments</b></td>"+
			"<td></td><td></td></tr>"+
		"<tr><td align='top' style='padding-left:10px;font-size:10px;'>"+
				"<div style='word-wrap:break-word;width:500px;'>"+taskComments+"</div>"+
					"<input  id='skus["+length+"].comments' name='skus["+length+"].comments' type='hidden' value='"+encodeURIComponent(taskComments)+"'/>"+
					"<input  id='skus["+length+"].partsSuppliedBy' name='skus["+length+"].partsSuppliedBy' type='hidden' value='"+partsSuppliedBy+"'/>"+
					"<input  id='skus["+length+"].buyerDocumentLogo' name='skus["+length+"].buyerDocumentLogo' type='hidden' value='"+buyerDocumentLogo+"'/>"+
					"<input  id='skus["+length+"].selectedSkuCategoryNew' name='skus["+length+"].selectedSkuCategoryNew' type='hidden' value='"+selectedSkuCategoryNew+"'/>"+
				"</td><td></td><td></td></tr>"+
	"</table>"+
	"</div>"+"</div><div style='font-size:10px;position: relative;left: 590px;top:-390px;width:100px;z-index:-1;background-color:white;'></td><td>"+
"<div style='padding-left:20px;padding-top:15px;'><a href='javascript:confirmDeleteSku("+length+");'>Delete Sku</a></div>"+		
	"</td>"+
	"</tr>";

					jQuery("table#addSKuDetailRow tbody.divForSku").append(rowData);
					jQuery('#addSKuDetailRow').show();
					skuFormSubmit();
	
					if(tableLength>=1)
					{
					
					var len=0;
					var newSkuDiv_="newSkuDiv_";
					var divId;	var imgId;
					var skuDetail_="skuDetail_"
					jQuery("table#addSKuDetailRow tbody.divForSku").children().not(':last-child').each(function(){
					
					
					divId=newSkuDiv_+len;
					imgId=skuDetail_+len;
					expandOnlyLastSkuDetailWidget('${staticContextPath}',divId,imgId);
					len=len+1;
					});
					}
			});	
}

/**
 * This function will set sub category as "No Sub-Categories Available"
 * when there is no sub categories for the category of an existing task.
 * This is called when an SO is being edited.
 */
function formatSubCategory(){
	 //Total number of task existing for this SO
	var taskCount = '${taskCount}';
	//countSubCat will hold the number of sub catagories for each rtask
	var countSubCat = new Array();
	var selectedCat = new Array();
	var selectedSubCat = new Array();
	<c:if test="${taskCount != 0}">
	<c:forEach var="i" begin="0" end="${taskCount-1}" step="1">
		countSubCat[${i}] = ${fn:length(scopeOfWorkDTO.tasks[i].subCategoryList)};
		selectedCat[${i}] = ${tasks[i].categoryId};
	</c:forEach>
	//For each task, if no sub category exists then set value as 'No Sub Caregory'
	for(var loop=0;loop<taskCount;loop++){
		var subcategoryList = countSubCat[loop];
		if(subcategoryList==0 && selectedCat[loop]>0){
			subCategorySelectList = document.getElementById('subCategorySelection_'+loop);
			subCategorySelectList.options[0].value= -1;
			subCategorySelectList.options[0].text = 'No Sub-Categories Available';
			subCategorySelectList.options[0].selected=true;
		}
	}
	</c:if>
}
function replacenbsps(str) {
	  return str.replace(/&nbsp;/gi, '&amp;nbsp;');
}
function skuFormSubmit()
		{
			jQuery("#soWizardScopeOfWorkCreate").attr('action','${contextPath}/soWizardScopeOfWorkCreate_addSku.action');
			jQuery("#soWizardScopeOfWorkCreate").attr("method","POST");
			jQuery("#soWizardScopeOfWorkCreate").submit();
	
	}

function confirmDeleteSku(idx,primarySkuInd,onlySkuDeleteInd) {

		window.location.href='soWizardScopeOfWorkCreate_deleteSku.action?delIndex='+idx+'&primarySkuInd='+primarySkuInd+'&onlySkuDeleteInd='+onlySkuDeleteInd+'&SERVICE_ORDER_ID=${soId}';
	
}
function deleteRow(len){
		var rowToBeDeleted="#tablerow_"+len;
				
		
		var size=jQuery("table#addSKuDetailRow tbody.divForSku").children().length;

			var str=jQuery("table#addSKuDetailRow tbody.divForSku").children("tr:first").attr("id");
			var tempSkuId=str.substring(str.indexOf("_") + 1);
			if (confirm("Are you sure you want to delete SKU?")) 
		{
			jQuery(rowToBeDeleted).remove();
			var sizeAfterDelete=size-1;
//var e = document.getElementById("selectSkuNameByCategory");
//var k=e.options[0].text;
//var totalSkuSelected=sizeAfterDelete+"  Selected";
//jQuery('#selectSkuNameByCategory option[index=0]').text(totalSkuSelected);
	       if(sizeAfterDelete==0)
		      { 
				var mainSKUId=document.getElementById("selectSkuNameByCategory");
			    mainSKUId.disabled=true;		      
				jQuery("#addSKuDetailRow").hide();
				jQuery("#skuDetailBySkuId").hide();
				
				// jQuery('#selectSkuNameByCategory option[index=0]').text('-Please Select-');	
				jQuery("#title").val(" ");
				jQuery("#overview").val(" ");
				jQuery("#buyerTandC").val(" ");
				jQuery("#specialInstructions").val(" ");
				jQuery("#onlySkuDeleteInd").val("1");
				confirmDeleteSku(len,1,1);
			}
		else
			{
					if(tempSkuId==len)
						{
								var str=jQuery("table#addSKuDetailRow tbody.divForSku").children("tr:first").attr("id");
								var tempSkuId=str.substring(str.indexOf("_") + 1);
							
								var lenForSku;var temp;
								 lenForSku='skuIdForSku['+tempSkuId+']';
								
								temp='templateIdForSku['+tempSkuId+']';
								var latestSkuNameId=document.getElementById(lenForSku).value;
								var latestTempId=document.getElementById(temp).value;
 								var oldTableLength='0';
								var url = "soLoadSkuDetailsBySkuId.action?skuNameId="+latestSkuNameId+"&templateSkuId="+latestTempId+"&oldTableLength="+oldTableLength+"&SERVICE_ORDER_ID=${soId}";
								jQuery("#skuDetailBySkuId").load(url,
								function() {
										var genInfoTitle=jQuery('#generalInfoTitle').val();
										var genInfoOverview=jQuery('#generalInfoOverview').val();
										var genInfoTerms=jQuery('#generalInfoTerms').val();
										var genInfoSpecialInstr=jQuery('#generalInfoSpecialInstructions').val();
										jQuery("#title").val(genInfoTitle);
										jQuery("#overview").val(genInfoOverview);
										jQuery("#buyerTandC").val(genInfoTerms);
										jQuery("#specialInstructions").val(genInfoSpecialInstr);
										var currSrc = jQuery("#inner_document_grid").attr("src");
										var skuIndicator='true';
										var newSrc=currSrc+"&skuIndicator="+skuIndicator;
										
										jQuery("#inner_document_grid").attr("src", newSrc);
										jQuery("#onlySkuDeleteInd").val("0");
										confirmDeleteSku(len,oldTableLength,0);

									});

						}
					else
						{
						jQuery("#onlySkuDeleteInd").val("0");
						confirmDeleteSku(len,1,0);
						}
				}	
			}
}

function expandOnlyLastSkuDetailWidget(path,imgId,obj)
{	
	var ob = jQuery("."+imgId).attr("src");
	var menu ="";
	menu =obj;
	if(ob.indexOf('arrowDown')!=-1){
		jQuery("."+imgId).attr("src",path+"/images/widgets/arrowRight.gif");
		jQuery("."+menu).slideToggle("slow");
	}

}


function expandSkuDetailWidget(path,imgId,obj)
{	
	
	var ob = jQuery("."+imgId).attr("src");
	var menu ="";
	menu =obj;
	if(ob.indexOf('arrowRight')!=-1){
		jQuery("."+imgId).attr("src",path+"/images/widgets/arrowDown.gif");
		jQuery("."+menu).slideToggle("slow");
	}
	if(ob.indexOf('arrowDown')!=-1){
		jQuery("."+imgId).attr("src",path+"/images/widgets/arrowRight.gif");
		jQuery("."+menu).slideToggle("slow");
	}


}

</script>


<s:form action="soWizardScopeOfWorkCreate_cancel" id="soWizardScopeOfWorkCreate" theme="simple" enctype="multipart/form-data" method="POST">

<input type="hidden" id="SERVICE_ORDER_ID" name="SERVICE_ORDER_ID" value="${soId}" />
<jsp:include page="validationMessages.jsp" />

<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.description"/>
<!--  <p><label>Reason for Continuation Order</label></p>
<p class="paddingBtm">
  <select>
    <option>Select One</option>
  </select>
</p>
-->

<div class="clear">
</div>
<div style="color: blue">
 		<p>${msg}</p>
 		<%session.setAttribute("msg",""); %>
</div>
<div id="skuCreationFacilityPage">
<c:if test="${showSoCreationSku==true}">
<jsp:include page="panels_scope_of_work/panel_service_order_creation.jsp"/>
</c:if>
</div>
<div id="usingTasks" style="display: block;position: relative;">
<jsp:include page="panels_scope_of_work/panel_serviceCategoriesAndTasks.jsp" />
</div>
<jsp:include page="panels_scope_of_work/panel_serviceLocationAndContactInfo.jsp" />
<jsp:include page="panels_scope_of_work/panel_generalInformation.jsp" />
<jsp:include page="panels_scope_of_work/panel_schedule.jsp" />

<c:import url="panels_scope_of_work/panel_documentsAndPhotos.jsp">
	<c:param name="pageFrom" value="tab_scope_of_work"/>
</c:import>

<div class="clearfix">
	<div class="formNavButtons">

	<c:if test="${requestScope.entryTab != 'today'}">
			
<c:if test="${requestScope.THE_SERVICE_ORDER_STATUS_CODE!= '110' && requestScope.THE_SERVICE_ORDER_STATUS_CODE!= '130'}">

<input type="hidden" id="serviceOrderTypeIndicator" name="serviceOrderTypeIndicator" value="SoUsingTask"></input >


<input type="hidden" id="titleforSku" name="titleforSku" value=""></input >
<input type="hidden" id="overviewforSku" name="overviewforSku" value=""></input >
<input type="hidden" id="overviewCharsforSku" name="overviewCharsforSku" value="2500"></input >
<input type="hidden" id="buyerTandCforSku" name="buyerTandCforSku" value=""></input >
<input type="hidden" id="buyerTandCCharsforSku" name="buyerTandCCharsforSku" value="5000"></input >
<input type="hidden" id="specialInstructionsforSku" name="specialInstructionsforSku" value=""></input >
<input type="hidden" id="specialInstructionsCharsforSku" name="specialInstructionsCharsforSku" value="5000"></input >

<input type="hidden" id="titleforTask" name="titleforTask" value=""></input >
<input type="hidden" id="overviewforTask" name="overviewforTask" value=""></input >
<input type="hidden" id="overviewCharsforTask" name="overviewCharsforTask" value="2500"></input >
<input type="hidden" id="buyerTandCforTask" name="buyerTandCforTask" value=""></input >
<input type="hidden" id="buyerTandCCharsforTask" name="buyerTandCCharsforTask" value="5000"></input >
<input type="hidden" id="specialInstructionsforTask" name="specialInstructionsforTask" value=""></input >
<input type="hidden" id="specialInstructionsCharsforTask" name="specialInstructionsCharsforTask" value="5000"></input >




			
		<input type="button" id="saveAsDraft" 
				  		onclick="tinyMCE.triggerSave();newco.jsutils.doFormSubmit('soWizardScopeOfWorkCreate_saveAsDraft.action','soWizardScopeOfWorkCreate')"
				  		class="btn20Bevel"
						style="background-image: url(${staticContextPath}/images/btn/saveAsDraft.gif); width:89px; height:20px;"
				  		src="${staticContextPath}/images/common/spacer.gif"
			/>
	</c:if>
	</c:if>
<!--  
		<s:submit type="input" src="%{#request['staticContextPath']}/images/common/spacer.gif"
			
			cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/saveAsTemplate.gif);width:106px; height:20px;"
			cssClass="btn20Bevel"
			method="saveAsTemplate"
			value="" />
-->
		<input type="button" id="next" name="next"
				  		onclick="tinyMCE.triggerSave();javascript:nextButton('soWizardScopeOfWorkCreate_next.action','soWizardScopeOfWorkCreate','tab2');"
				  		class="btn20Bevel"
						style="background-image: url(${staticContextPath}/images/btn/next.gif); width:50px; height:20px;"
				  		src="${staticContextPath}/images/common/spacer.gif"
			/>
			
		<s:if test="#session.showReviewBtn == 'true'">
			<s:submit type="input" 
				cssClass="btn20Bevel" 
				method="review"
				theme="simple"
				value="Go to review"/>
		</s:if>	
			
	</div>
	<div class="bottomRightLink">
		<a href="#" onclick="newco.jsutils.formSubmit('soWizardScopeOfWorkCreate')">
		<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.cancel"/></a>
	</div>
	


</div>
</s:form>

