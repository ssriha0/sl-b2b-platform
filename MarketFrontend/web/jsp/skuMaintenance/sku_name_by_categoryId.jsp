<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/public.css"/>
<link href="${staticContextPath}/javascript/confirm.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"
		src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>	
</head>

<script type="text/javascript">
$(document).keydown(function (e){
	if (e.keyCode == 27){ 
    jQuery.modal.close();
	jQuery('#popUpModal').hide();
    jQuery('.modalOverlay').hide();
    jQuery('.modalContainer').hide();
	} 
	});
	
function trim(str) {
        return str.replace(/^\s+|\s+$/g,"");
}

// function to get the selected sku's into a list

function  delSkuModal(){
$("#successMsg").hide();
	<c:set var="skuListSize" value="${fn:length(buyerSkuNameList)}" />
    var skuListLength = "${skuListSize}"; 
	var asctdSkus = document.getElementById('skuNameByCategory');
	var selSkus = "";
	var selSkuIds = "";
	for(i=0;i<asctdSkus.options.length;i+=1){
	if(asctdSkus.options[i].selected){
	selSkus= trim(selSkus); 
	selSkuIds=trim(selSkuIds);
   if(trim(asctdSkus.options[i].value) != -1){
	selSkus = selSkus + trim(asctdSkus.options[i].innerHTML) + ",";
	selSkuIds = selSkuIds + trim(asctdSkus.options[i].value) + ",";
	}
  	}
  	}
// Removing the last splitter i.e., 'comma'
	selSkus = selSkus.substr(0,selSkus.length-1);
	selSkus= trim(selSkus);
	selSkuIds = selSkuIds.substr(0,selSkuIds.length-1);
	selSkuIds= trim(selSkuIds);
	$('#skuNme').text(selSkus);
    var skuCategoryId = $('#skuCategory').val();
    var skuCtgryName =  $("#skuCategory option:selected").text();
	  
     skuCtgryName= trim(skuCtgryName);
     
    var urlToLoad='chk_Sku_Delete.action?selSkus='+encodeURIComponent(selSkus)+'&skuCategoryId='+encodeURIComponent(skuCategoryId)+'&skuCategoryName='+encodeURIComponent(skuCtgryName)+'&countOfSku='+encodeURIComponent(skuListLength)+'&selSkuIds='+selSkuIds;
//loading the backend content into the popup modal.
		$("#loadBackEndContent").load(urlToLoad,function(){
		$("#rspnseFrmBkEnd").show();
		jQuery.noConflict();
		jQuery("#popUpModal").modal({
                onOpen: modalOpenAddCustomer,
                onClose: modalOnClose,
                persist: true,
                containerCss: ({ width: "500px", height: "130px", marginLeft: "160px", top: "210px"})
            });
            
           });          
            window.scrollTo(0,0);  
     }

function confirmDelete()
{ 
   var skuCategoryId=$('#skuCategory').val();
   var skuIdList = trim($('#inactiveSkus').text());
   var skuCtgryName =  $("#skuCategory option:selected").text();
   document.getElementById('deletingSku').action ='${contextPath}/assocSku_Delete.action?deleteSkuList='+encodeURIComponent(skuIdList)+'&skuCategoryId='+encodeURIComponent(skuCategoryId)+'&skuCategoryName='+encodeURIComponent(skuCtgryName);
   document.getElementById('deletingSku').method="POST"; 
   document.getElementById('deletingSku').submit();
}    

function showSkuNameDescription(skuDesc)
{
$("#successMsg").hide();
var categoryId=$('#skuCategory').val();
var categoryNameId=$('#skuNameByCategory').val();
if(categoryNameId == "-1"){
	$("#deleteSKUName").hide();
	$("#updateSKUByCategory").hide();
	document.getElementById("updateSKUByCategory").disabled = true;
	}
else{
	$("#deleteSKUName").show();
	$("#updateSKUByCategory").show();
}
var SkuNameLength = $("#skuNameByCategory option:selected").length;
if(SkuNameLength == 1 && categoryNameId != "-1" )
{
	$("#skuHistoryLink").show();
	$('#updateSKUByCategory').removeAttr("style");
    document.getElementById("updateSKUByCategory").style.width="60px";
    jQuery("#loadingImageDetail").html('<img src="/ServiceLiveWebUtil/images/loading.gif" width="572px" height="160px"/>');
	$("#skuNameIdDetail").hide();        
    jQuery("#loadingImageDetail").show();
   	$("#skuNameIdDetail").load("sku_maintenanceSkuNameDetailsBySkuId.action?categoryNameId="+categoryNameId+"&categoryId="+categoryId,
	function() {jQuery("#loadingImageDetail").hide();
    var skuSelectedCount = $("#skuNameByCategory option:selected").length;
    skuCategoryNameId = $('#skuNameByCategory').val();
    if (skuSelectedCount > 1) {
      jQuery("#skuNameIdDetail").hide();		       
    }
    else if(skuSelectedCount == 1 && skuCategoryNameId != "-1")
    {	
	  $("#skuNameIdDetail").show();
	  document.getElementById("updateSKUByCategory").disabled = false;
	}	
	});
}
else
{   
    $("#outputtext1").html("");	
    hideOptionsku();
    $("#skuNameIdDetail").hide();
    $('#updateSKUByCategory').css({backgroundImage:"url()"});
    $('#updateSKUByCategory').css("margin-left","3px");         
	document.getElementById("updateSKUByCategory").disabled = true;
	$("#skuHistoryLink").hide();
}
}
</script>
<style>
.skutable
{

border-right: solid thin grey;
border-left: solid thin grey;


padding-left:20px;
border-color:#cccccc;

border-width:1px;
}
</style>
<body>
<table width="800" align="center" id="skuNameDisplay">
		<tr>
			<td  align="left"   style="padding-bottom:3px;padding-left:0px;font-size:13px"> 
				<span><b>SKU</b></span>
				<span style="padding-left:  225px;"><a href="#" id="skuHistoryLink" onclick="loadSkuHistory();" style="display: none;font-size: 10px;">History</a></span>
			</td>
		
			<td align="left"    style="padding-bottom:3px;padding-left:35px;font-size:13px"> 
				<b>SKU  Description</b>
			</td>
		</tr>
<tr >
	<td style="padding-bottom:0px;padding-left:00px;">
<c:set var="index" value="0"></c:set>
<select name="ActionSKUName" id="skuNameByCategory" onclick="alignButtons(this);doOnclick('skuNameByCategory');" onkeyup="alignButtons(this);doOnclick('skuNameByCategory');" multiple="multiple" 
style="text-align:left;width: 300px;border: 1px solid grey; overflow-y: scroll;overflow-x: scroll; font-family:Arial;
    height: 105px;
    margin: 0 auto;
    display: block;">  
  <option id="SkuNameOptionByCategory" value="-1" selected="true">Please Select</option>
    <c:forEach var="buyerSkuNameList" items="${buyerSkuNameList}" varStatus="dtoIndex" >  
   <c:set var="index" value="${index+1}"></c:set>

<option id="SkuByCategory"  value="${buyerSkuNameList.skuId}">${buyerSkuNameList.sku}</option>  
</c:forEach> 

</select>  
<div style="display: none">
 <c:forEach var="buyerSkuNameList" items="${buyerSkuNameList}" varStatus="dtoIndex" >  
    <input value="${buyerSkuNameList.manageScopeInd}" id="${buyerSkuNameList.skuId}_msi"/>
</c:forEach> 
</div>
</td>
<td style="padding-bottom:0px;padding-left:35px;padding-right:100px;">

<textarea disabled="disabled" id="outputtext1" rows="50" cols="38" style="background-color: #FAFAFA;font-size:10px;overflow-y: scroll; font-family:Arial;
	height: 100px; width: 380px; border: 1px solid grey;">
</textarea>

<c:set var="index" value="0"></c:set>
<select name="ActionSKUName" id="skuNameDescByCategory" multiple="multiple" style="font-size:15px;text-align:left;width: 400px;overflow-y: scroll;overflow-x: scroll;font-family:Arial;
    height: 100px;
    margin: 0 auto;
    display: none;">  
  <c:forEach var="buyerSkuNameList" items="${buyerSkuNameList}" varStatus="dtoIndex" >  
  
    <c:set var="index" value="${index+1}"></c:set>
	<option id="descr${buyerSkuNameList.skuId}"  value="${buyerSkuNameList.skuDescription}">${buyerSkuNameList.skuDescription}</option>
	</c:forEach> 

</select>  
</td>	
</tr>
<tr ><td style="padding-bottom:5px;;padding-left:3px;">
<div id="dsplyMsgeFrChkbx" style="display: none;" ></div>
	<div id="hdnMsgeFrChkbx" style="display: none;" ></div>
	</td>
	<td>
</td></tr>
<tr>
<td style="padding-left:37px;padding-bottom:20px;">
<table cellspacing="10">
<tr>
<td>
<input class="button action" id="deleteSKUName" style="display:none;width:60px" type="button" value="Delete" onclick="delSkuModal();"/>
&nbsp;&nbsp;&nbsp;
</td>
<td>
<input class="button action" id="updateSKUByCategory" style="width:60px;  display:none;" type="button" value="Update" onclick="loadModal(this.id);" disabled="disabled"/>
&nbsp;&nbsp;&nbsp;
</td>
<td>
<input align="left" class="button action" id="addSkuByCategory" style="width:60px;margin-left:92px;" type="button" value="Add" onclick="loadModal(this.id);"/>
</td>
</tr>
</table>
</td>
<td style=" padding-left:50px;">

	

	
</td>
<td>
</td>
</tr>

</table>
<div id="loadingImageDetail" style="left:150px;top:-80px;"></div>

<form id="deletingSku">
</form>		
	<div id="loadBackEndContent" style="display: none;"></div>
	<div id="skuNme" style="display: none;"></div>
</body>
</html>
