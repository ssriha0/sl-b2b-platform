<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="retlPriceInfo" value="<%= OrderConstants.RETAILPRICE_INFO%>" />
<c:set var="priceInfo" value="<%= OrderConstants.PRICETYPE_INFO%>" />
<c:set var="bidInf" value="<%= OrderConstants.BID_INFO%>" />
<c:set var="billingInf" value="<%= OrderConstants.BILLING_INFO%>" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
 <script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
 
<script type="text/javascript"
		src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
	<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/plugins/tabs_jquery.js"  
		charset="utf-8">
    </script>
<script type="text/javascript" >
// Function to rounding off the entered price 
	
</script>
	    
<style>
.mytable
{
border-top:solid thin grey;
border-right: solid thin grey;
border-left: solid thin grey;
border-bottom: solid thin grey;

padding-left:20px;
border-color:#cccccc;
 
border-width:1px;
}
.smallTable
{

border-right: solid thin grey;
border-left: solid thin grey;
border-bottom: solid thin grey;

padding-left:00px;
border-color:#cccccc;
border-style:solid; 
border-width:1px;

}
.othermytable
{
border-bottom:solid thin grey;
border-right: solid thin grey;
border-left: solid thin grey;
border-top: none;

padding-left:20px;
border-color:#cccccc;
 
border-width:1px;
}

.moreInfoStyle
{
border: 3px solid #adaaaa; 
background:#fcfae6; 
border-radius:10px;
-moz-border-radius:10px; 
-webkit-border-radius:10px; 
padding:10px;
}
 blockquote {padding-left:15px;}
</style>
<body>
<fmt:formatNumber value="${skuDetailsBySkuIdAndCategoryId.retailPrice}" var="retailPriceTemp" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" pattern="#0.00"/>
<fmt:formatNumber value="${skuDetailsBySkuIdAndCategoryId.bidMargin*100}" var="bidMarginTemp" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" pattern="#0.00"/>
<fmt:formatNumber value="${skuDetailsBySkuIdAndCategoryId.bidPrice}" var="bidPriceTemp" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" pattern="#0.00"/>
<fmt:formatNumber value="${skuDetailsBySkuIdAndCategoryId.billingMargin*100}" var="billMarginVal" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" pattern="#0.00"/>
<fmt:formatNumber value="${skuDetailsBySkuIdAndCategoryId.billingPrice}" var="billPriceVal" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" pattern="#0.00"/>


<fieldset style="width: 850px;height:720px;padding-left: 25px;padding-right: 5px;margin-left: 35px;display: inline;">
<legend style="font-size:17px;color:#00A0D2;">SKU Details</legend>
<table width="800" align="center">
<tr >
<td width="50%" style="padding-bottom:20px;padding-left:5px;padding-top:20px;font-size: 13px;">
							<strong>Main Category&nbsp;</strong><font color="red">*</font>
</td>

<td style="padding-bottom:20px;padding-left:85px;padding-top:20px;font-size: 13px;">
<strong>Template Name&nbsp;</strong><font color="red">*</font>
</td>
</tr>
						
<tr>
<td style="padding-left:5px;">

   
<select style="background-color: #FAFAFA;width: 250px;font-family:Arial;
    height: 20px;
    display: block;" disabled="disabled">
     <c:if test="${not empty mainServiceCatName}">
     <option>${mainServiceCatName}</option>
     </c:if>
</select>
</td>
<td style="padding-left:85px;">
    <select select style="background-color: #FAFAFA;width: 150px;font-family:Arial;
    height: 20px;
    display: block;" disabled="disabled">
    <c:if test="${not empty skuDetailsBySkuIdAndCategoryId}">
  <option>${skuDetailsBySkuIdAndCategoryId.buyerSoTemplate.templateName}</option>
  </c:if>
</select>
</td>
</tr>
<tr>
<td width="50%" style="padding-top:20px;padding-bottom:20px;padding-left:5px;font-size: 13px;">
							<strong>Order Item Type&nbsp;</strong><font color="red">*</font>
</td>
<td>
</td>
</tr>
<tr>


<td style="padding-left:5px;padding-bottom:20px;">
<select style="background-color: #FAFAFA;width: 250px;font-family:Arial;
    height: 20px;
    display: block;" disabled="disabled">
    <c:if test="${not empty skuDetailsBySkuIdAndCategoryId.orderitemType}">
 		 <c:if test="${skuDetailsBySkuIdAndCategoryId.orderitemType == 'ADD_ON_PARTS_AND_MATERIAL'}">
  			<option>ADD ON PARTS AND MATERIAL</option>
 		 </c:if>
  		<c:if test="${skuDetailsBySkuIdAndCategoryId.orderitemType != 'ADD_ON_PARTS_AND_MATERIAL'}">
  			<option>${skuDetailsBySkuIdAndCategoryId.orderitemType}</option>
  		</c:if>
  </c:if>
</select>
</td>

</tr>
</table>
<fieldset style="width: 800px;padding-left: 10px;padding-right: 5px;margin-left: 5px;">
<legend style="font-size:17px;color:#00A0D2;">Pricing & Margin</legend>	
<table width="800">
<tr>
<td style="padding-top:20px;padding-bottom: 20px;">
<table width="240px" height="102px" class="smallTable" cellspacing="10">
		
<!-- <tr >
<td style="padding-bottom:20px;padding-left:00px;">
<strong>Price Type</strong><font color="red">*</font>
</td>
<td style="padding-left:50px;">
<select style="height: 20px;width:100px;display: block;" disabled="disabled">
  <option>${skuDetailsBySkuIdAndCategoryId.priceType}</option>
  
</select>
<img id="priceTypeMainInfo"  src="${staticContextPath}/images/icons/sku_info.gif" 
	 onmouseover="displayMainInfo(this.id);" onmouseout="hideMainInfo(this.id);" 
	 style="position:relative;top:-20px;right:-110px;"/>  </a>
<div id="priceTypeMainInfoDiv" class="moreDetails moreInfoStyle" 
	style="display: none; left: 490px; top: 876px;z-index: 20;">${priceInfo}</div>
</td>
</tr> -->
<tr >
<td style="padding-bottom:10px;padding-left:10px;padding-top:10px;">
<strong>Retail Price&nbsp;($)</strong>
</td>
<td style="padding-left:10px;padding-top:5px;">
<input type="text" disabled="true" style="background-color: #FAFAFA;height: 25px;width:70px;display: block; white;font-family:Arial;font-size:10px;" 
disabled="disabled" maxlength="9"  value='${retailPriceTemp}' id="retailprice"/>

</td>
</tr>
<tr >
<td style="padding-bottom:10px;padding-left:10px;" height="28px;"></td>
<td style="padding-left:10px;" height="28px;"></td>
</tr>
</table>
  <img id="retailPriceMainInfo" src="${staticContextPath}/images/icons/sku_info.gif" 
  	onmouseover="displayMainInfo(this.id);" onmouseout="hideMainInfo(this.id);" 
  	style="position:relative;top:-108px;right:-230px;"/>  </a>
<div id="retailPriceMainInfoDiv" class="moreDetails moreInfoStyle" 
	style="display: none; left: 560px; top: 865px;z-index: 20;">${retlPriceInfo}</div>
</td>
<td style="padding-top:20px; padding-bottom: 20px;">
<table class="smallTable" width="240px" cellspacing="10">
	<tr>
<td style="padding-top:10px;">
<span style="padding-left: 2px;">
<strong>Margin&nbsp;(%)</strong></span>
</td>
<td  style="padding-top:2px;">
<input type="text" disabled="true" style="background-color: #FAFAFA;height: 25px;width:70px;display: block; font-family:Arial;font-size:10px;" 
disabled="disabled" maxlength="9" value='${bidMarginTemp}'/>
</td>
</tr>
<tr>
<td style="padding-top:10px;">
<span style="padding-left: 2px;">
<strong>Maximum Price&nbsp;($)&nbsp;<span class="req">*</span></strong>
</span>
</td>
<td style="padding-top:10px;">  
<input type="text" disabled="true" style="background-color: #FAFAFA;height: 25px;width:70px;font-family:Arial;font-size:10px;" maxlength="9" disabled="disabled"
value='${bidPriceTemp}'/>
 </td>
</tr>
</table> 
<img id="bidMainInfo" class="" src="${staticContextPath}/images/icons/sku_info.gif" 
	onmouseover="displayMainInfo(this.id);" onmouseout="hideMainInfo(this.id);" 
	style="position:relative;top:-108px;right:-230px;"/>  </a>  </span>
<div id="bidMainInfoDiv" class="moreDetails moreInfoStyle" style="display: none; left: 840px; top: 865px;z-index: 20;">${bidInf}</div>
</td>
<td style="padding-top:20px; padding-bottom: 20px;width:240px;">
<c:if test="${SecurityContext.autoACH == false}">
<table class="smallTable" width="240px" cellspacing="10">
<tr>
<td style="padding-top:10px;">
<span style="padding-left: 12px;"><strong>Billing Margin&nbsp;(%)</strong></span>
</td>
<td style="padding-top:2px;">
<input type="text" disabled="true" style="background-color: #FAFAFA;height: 25px;width:70px;font-family:Arial;font-size:10px;" maxlength="9" disabled="disabled"
value='${billMarginVal}'/>
  
</td>
</tr>
<tr>
<td style="padding-top:10px;">
<span style="padding-left: 12px;"><strong>Billing Price&nbsp;($)</strong></span>
</td>
<td style="padding-top:10px;">
<input type="text" disabled="true" style="background-color: #FAFAFA;height: 25px;width:70px;font-family:Arial;font-size:10px;" maxlength="9" disabled="disabled"
value='${billPriceVal}'/>
 </td>
</tr>
</table>
<img id="billingMainInfo" class="" src="${staticContextPath}/images/icons/sku_info.gif" 
onmouseover="displayMainInfo(this.id);" onmouseout="hideMainInfo(this.id);" 
style="position:relative;top:-108px;right:-230px;"/>  </a>  </span>
<div id="billingMainInfoDiv" class="moreDetails moreInfoStyle" 
style="display: none; left: 780px; top: 865px;z-index: 20;">${billingInf}</div>
</c:if>
</td>



</tr>
</table>
</fieldset>
<br>
<br>
<fieldset style="width: 800px;padding-left: 10px;padding-right: 5px;margin-left: 5px;">
<legend style="font-size:17px;color:#00A0D2;">Task Management</legend>
<table width="800">
<tr>
<td style="padding-right: 0px;padding-left: 4px;padding-top:10px;">	
			<strong>Category <span class="req">*</span></strong>
			</td>
			<td style="padding-right: 0px;padding-left: 4px;padding-top:10px;">	
			<strong>Sub Category <span class="req">*</span></strong>
			</td>	
			<td style="padding-right: 0px;padding-left: 4px;padding-top:10px;" colspan="2">		
			<strong>Skill <span class="req">*</span></strong>
			</td>	
			
			<td></td>
</tr>
<tr>
<td style="padding-right: 0px;padding-left: 4px; padding-top: 3px;text-indent: 0px;padding-bottom:2px;">
<select style="background-color: #FAFAFA;width:140px;font-family:Arial;" disabled="disabled" >
  <option>${taskCatName}</option>
  
</select>
</td>
<td style="padding-right: 0px;padding-left: 4px;padding-bottom:2px;padding-top: 3px">
<select style="background-color: #FAFAFA;width:140px;font-family:Arial;" disabled="disabled">
  <option>${taskSubCatName}</option>
  
</select>
<td style="padding-right: 0px;padding-left: 4px;padding-bottom:2px;padding-top: 3px" colspan="2">
<select style="background-color: #FAFAFA;width: 140px;font-family:Arial;" disabled="disabled">
  <option>${buyerSkuTasks.luServiceTypeTemplate.descr}</option>
  
</select>
</td>

<tr height="2px">
<td>
</td>
</tr>			
<tr>
<td style="padding-left: 4px;padding-right: 0px;padding-top:5px;" colspan="4">
			<strong>Task Name <span class="req">*</span></strong>
			</td>
</tr>
<tr>
<td style="padding-left:4px;padding-bottom: 2px;padding-top: 3px" colspan="4">
<input id="taskNameDisplay"  style="background-color: #FAFAFA;font-family:Arial;font-size:10px;width:225px;" disabled="true" value='${buyerSkuTasks.taskName}'></input>
</td>
</tr>
			<tr>			
			<td style="padding-right: 0px;padding-left: 4px;padding-top:5px;" colspan="4">					
			<strong>Task Comments <span class="req">*</span></strong>
			</td>
																
			</tr>
<tr height="2px">
<td>
</td>
</tr>			
<tr>


<td style="padding-right: 0px;padding-left: 5px;padding-bottom:20px;padding-top: 3px" colspan="4">
<div id="taskCommentsArea" name="taskCommentsArea" cols="135" rows="10" style="  -moz-appearance: textfield-multiline; -webkit-appearance: textarea; border: 1px solid gray; background-color: #FAFAFA;overflow: scroll;height: 100px;width:775px;padding-left: 20px;">
${buyerSkuTasks.taskComments}</div>
</td>
</tr>

</table>
</fieldset>
<br>
</fieldset>
</body>
</html>