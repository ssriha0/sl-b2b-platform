<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<script type="text/javascript">

function deleteZipCodes(){
	$("#errorZipMarket").html("");
	var rows = $("#marketsZips-chosen > tbody > tr").length;
	var deleteValues="";
	var newValid=$("#validZipAdded").val();
	for (i=rows-1; i>=0; i--) {
	    if($("#marketsZips-chosen > tbody > tr:eq("+i+") > td > input").prop("checked")) {
	    	var id =$("#marketsZips-chosen > tbody > tr:eq("+i+") > td > input").prop("id");
	    	deleteValues = deleteValues + ","+ id;
	    	var valid =$("#validZipAdded").val();
	    	newValid="";
	    	var zipArray = valid.split(",");
			var i;
			var len=zipArray.length;
			var obj={};
			var errorZips = "";
			var validZips="";
			if(valid!=""){
				for (j=0;j<len;j++){
				
			    	if("zip"+zipArray[j]==id){
				    	
			    	}else{
			    		if(newValid==""){
				    		newValid = zipArray[j];
				    	}else{
				    		newValid = newValid+","+zipArray[j];
				    	}
			    	}
		    	}
		    	$("#validZipAdded").val(newValid);		
		    	
	    	}
		}	
	}   	
	if(deleteValues!=""){
		$("#marketsTable").html("<img src=\"" + staticContextPath + "/images/loading.gif\" width=\"300px\"/>");
	   $("#marketsTable").load('rrCreateRuleAction_deleteZipMarkets.action', {deleteZipMarkets: deleteValues}, function(data) {
	   		$("#validZipAdded").val(newValid);
	   });
	}

}

function paginateZip(obj){
	var pageNum = obj.id;
	var valid =$("#validZipAdded").val();
	$("#marketsTable").html("<img src=\"" + staticContextPath + "/images/loading.gif\" width=\"300px\"/>");
	$("#marketsTable").load('rrCreateRuleAction_loadZipCodeList.action', {pageNo: pageNum}, function(data) {
	$("#validZipAdded").val(valid);
		var userAction = $("#userAction").val();
		if(userAction=="VIEW"){
			$(".marketsZips-chosen-checks").prop("disabled", "disabled");
			$("#selectAll-marketsZips-check").prop("disabled", "disabled");
			$(".deleteDiv").hide();
		}
	});
}

function quickPagination(){
	var totalPages =1;
	var pageNo = 1;
 	if($("#totalPagesZip")&& $("#totalPagesZip").val()){
		totalPages = $('#totalPagesZip').val();
	}
	if($("#pageNoTextTop")&& $("#pageNoTextTop").val()&& $("#pageNoTextTop").val()!=""){
 		pageNo = $('#pageNoTextTop').val();
 	}else{
 		return false;
 	}
 	if(pageNo == null || pageNo == "" || totalPages == 0)
 	{
    	return false;
   	}
   	pageNo = parseInt(pageNo);
   	totalPages = parseInt(totalPages);
 	if(pageNo > totalPages){
 		pageNo=totalPages;
 	}
 	if(pageNo < 1){
 		pageNo=1;
 	}
   
   	/*validate page number entered for max no: of pages and numeric value before ajax */ 
   	var valid =$("#validZipAdded").val();
   	$("#marketsTable").html("<img src=\"" + staticContextPath + "/images/loading.gif\" width=\"300px\"/>");
   	$("#marketsTable").load('rrCreateRuleAction_loadZipCodeList.action', {pageNo: pageNo}, function(data) {
		var userAction = $("#userAction").val();
		$("#validZipAdded").val(valid);
		if(userAction=="VIEW"){
			$(".marketsZips-chosen-checks").prop("disabled", "disabled");
			$("#selectAll-marketsZips-check").prop("disabled", "disabled");
			$(".deleteDiv").hide();
		}
	});
}


</script>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>

<c:set var="disabled" value="" />
<c:if test ="${userAction=='VIEW'}">
	<c:set var="disabled" value="DISABLED" />
</c:if>
	<head>
	<body>
	<input type="hidden" id="validZipAdded" value="${validZipAdded}"/>
	<input type="hidden" id="invalidZips" value="${invalidZips}"/>
	<input type="hidden" id="duplicateZipMarkets" value="${duplicateZipMarkets}"/>
	<input type="hidden" id="validZipCount" value="${validZipCount}"/>
		<c:if test="${!empty zipList}">
			<div id="marketsZips-tableId" class="inlineTableWrapper"
				style="display: block">
				<c:if test="${disabled != 'DISABLED'}">	
				<div class="deleteDiv">
				<img src="${staticContextPath}/images/icons/iconTrash.gif"
					class="deleteIcon pointer" href="#" onclick="deleteZipCodes();" />
				<a id="deleteZip" class="paginationLink deleteLink" onclick="deleteZipCodes();">Delete Selected</a>
				</div>
				</c:if>
				<div class="inlineTableContainer">
					<table id="marketsZips-chosen" cellpadding="0" cellspacing="0"
						border="0" class="inlineTable">
						<thead id="marketsZips-chosen-thead">
							<th>
								Select All
								<br />
								<input type="checkbox" id="selectAll-marketsZips-check" ${disabled} 
									onclick="toggleSelectAllCheck('marketsZips')" />
							</th>
							<th>
								Zip Code or Market
							</th>
						</thead>
						<tbody>
							<c:set var="label1" value="label" />
							<c:forEach var="zipMarket" items="${zipList}"
								varStatus="rowCounter">
								<tr>
									<td>
										<input type="checkbox" id="${zipMarket['value']}"
											name="marketsZips-chosen-checks"  ${disabled}
											class="marketsZips-chosen-checks" value="" />
										&nbsp;
										<label>
											<b>${zipMarket['name']}</b>
										<label>
									</td>
									<td>
											${zipMarket['label']}
										
									</td>
								</tr>
							</c:forEach>
						<c:if test="${zipMarketPagination.totalPages>1}">
						<tr>
							<td colspan="2" >
							<div id="page" align="center" class="paginationCriteriaDiv">
							<input type="hidden" id="totalPagesZip" name="totalPagesZip" value="${zipMarketPagination.totalPages}"/>
								<c:if test="${zipMarketPagination.currentIndex>1}">
									<a href="#zipfocus" class="pagination paginationLink" id="1"
									 onclick="paginateZip(this)"><<</a>
									<a href="#zipfocus" class="paginationColor paginationLink" id="${zipMarketPagination.currentIndex-1}" onclick="paginateZip(this)" ><b><</b>
									</a>
									</c:if>
									<b>Page ${zipMarketPagination.currentIndex} of ${zipMarketPagination.totalPages}</b>
		    					<c:if test="${zipMarketPagination.currentIndex!=zipMarketPagination.totalPages}">
									<a href="#zipfocus" class="paginationColor paginationLink"  onclick="paginateZip(this)" id="${zipMarketPagination.currentIndex+1}"><b>></b></a>
									<a href="#zipfocus" class="pagination paginationLink"  id="${zipMarketPagination.totalPages}" onclick="paginateZip(this)"
									>>></a>
								</c:if>
								
								<div><b>To Page</b> <input type="text" id="pageNoTextTop" size="3" class="pageNoText"/> <input type="button" class="goToPageTop" value="GO" onclick="quickPagination()"/></div>
								
							</div>
							</td>
					</tr>
					</c:if>
					</tbody>
					</table>
				</div>
			</div>
		</c:if>
	</body>
</html>
