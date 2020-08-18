<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />	
<c:set var="OVERVIEW_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_GENERAL_INFO_OVERVIEW_LENGTH%>" />
<c:set var="BUYER_TERM_COND_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_GENERAL_INFO_BUYER_TERM_COND_LENGTH%>" />
<c:set var="SPECIAL_INSTRUCTION_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_GENERAL_INFO_SPECIAL_INSTRUCTION_LENGTH%>" />
<c:set var="TASK_COMMENTS_LENGTH" value="<%= OrderConstants.SUMMARY_TAB_SCOPE_OF_WORK_TASK_COMMENTS_LENGTH%>" />
<title>Insert title here</title>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table>
			<tr>
					<td align="left" style="padding-bottom:10px;padding-left:18px;font-size:10px">Main Service Category</td>
			</tr>
			<tr>		<c:set var="luTemplate" value="${luServiceTemplate}" /> 
					<td align="left" style="padding-bottom:40px;padding-left:18px;font-size:10px">
					<c:choose>
			   <c:when test="${(not empty mainServiceCategoryName)&&(mainServiceCategoryName!='Select One')}">


			    <input type="text" disabled="true" style="font-size:10px;
				    		height: 18px;width:250px;padding-left:0px;
				    		display: block;" disabled="disabled"
				  		value='${mainServiceCategoryName}' />
			    </c:when>
			     <c:otherwise>
						<input type="text" disabled="true" style="font-size:10px;
				    		height: 20px;width:250px;padding-left:0px;
				    		display: block;" disabled="disabled"
				  		value='${mainCatForSku}'/>
				  		</c:otherwise>
				  		</c:choose>
						<input type="hidden"  id="mainServiceCategoryName"  value='${mainCatForSku}'/>

						<input type="hidden"  id="taskCat" value='${taskCatForSku}'/>
						<input type="hidden"  id="skuEachBidPrice" value='${skuBidPrice}'/>

				  		<input type="hidden"  id="taskName" value="${buyerSkuDetailBySkuId.taskName}"/>
				  		<input type="hidden"  id="taskSubCat" value="${taskSubForSku}"/>
				  		<input type="hidden"  id="mainCatId" value="${mainCatIdForSku}"/>
				  		<input type="hidden"  id="catId" value="${CatIdForSku}"/>
				  		<input type="hidden"  id="subCatId" value="${SubCatIdForSku}"/>
				  		<input type="hidden"  id="skillId" value="${SkillIdForSku}"/>
				  		<input type="hidden"  id="templateDescr" value="${luTemplate.descr}"/>
				  		<input type="hidden"  id="taskComments" value="<c:out value="${buyerSkuDetailBySkuId.taskComments}"/>"/>
						<input type="hidden"  id="generalInfoTitle" value="${buyerTemplateDetailBySkuId.title}"/>
						<input type="hidden"  id="generalInfoOverview" value="<c:out value="${buyerTemplateDetailBySkuId.overview}"/>"/>
						<input type="hidden"  id="generalInfoTerms" value="<c:out value="${buyerTemplateDetailBySkuId.terms}"/>"/>
						<input type="hidden"  id="generalInfoSpecialInstructions" value="<c:out value="${buyerTemplateDetailBySkuId.specialInstructions}"/>"/>
						<input type="hidden"  id="partsSuppliedBy" value="${buyerTemplateDetailBySkuId.partsSuppliedBy}"/>
						<!-- SL-21355 : Added for setting buyer Logo in  so for so created using SKU -->
						<input type="hidden"  id="buyerDocumentLogo" value="${buyerTemplateDetailBySkuId.buyerDocumentLogo}"/>
						<input type="hidden"  id="selectedSkuCategoryNew" value="${selectedSkuCategoryNew}"/>
						

					</td>
			</tr>
</table>
<br><br>




</body>
</html>



