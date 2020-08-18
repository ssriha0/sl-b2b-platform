<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<script type="text/javascript">
jQuery(document).ready(function() {
	jQuery(".pageNoText").keypress(function (e){
	
  			//if the letter is not digit then display error and don't type anything
  			if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57))
  			{
   				return false;
 			}
	});
});
function deleteCustomReference(){
	jQuery("#validationError_customRefs").html("");
	jQuery("#error_customRefs").html("");
	var rows = jQuery("#customRefs-chosen > tbody > tr").length;
	var deleteValues="";
	var newValid=jQuery("#validCustRef").val();
	for (i=rows-1; i>=0; i--) {
	    if(jQuery("#customRefs-chosen > tbody > tr:eq("+i+") > td > input").prop("checked")) {
		    var id =jQuery("#customRefs-chosen > tbody > tr:eq("+i+") > td > input").prop("name");
		    var valid =jQuery("#validCustRef").val();
	    	newValid="";
	    	var custArray = valid.split(",");
			var i;
			var len=custArray.length;
			var obj={};
			if(valid!=""){
				for (j=0;j<len;j++){
			    	if(custArray[j]==id){
			    	}else{
			    		if(newValid==""){
				    		newValid = custArray[j];
				    	}else{
				    		newValid = newValid+","+custArray[j];
				    	}
			    	}
		    	}
		    	jQuery("#validCustRef").val(newValid);			
	    	}
		    if(deleteValues==""){
		    	deleteValues = encodeURIComponent(id);
		    }else{
		    	deleteValues = deleteValues + ","+ encodeURIComponent(id);
		    }
	   	}
	} 
	
	if(deleteValues!=""){
	   jQuery("#customRefsTable").html("<img src=\"" + staticContextPath + "/images/loading.gif\" width=\"300px\"/>");
	   jQuery("#customRefsTable").load("rrCreateRuleAction_deleteCustReference.action",{deleteCustRefs: deleteValues},function(data) {
	   	jQuery("#validCustRef").val(newValid);
	   });
	   jQuery('#customRef-select').focus();	
	}
}

function paginateCust(obj){
	var pageNum = obj.id;
	var validCustRefVal = jQuery("#validCustRef").val();
	jQuery("#customRefsTable").html("<img src=\"" + staticContextPath + "/images/loading.gif\" width=\"300px\"/>");
	jQuery("#customRefsTable").load('rrCreateRuleAction_loadCustRefList.action', {pageNo: pageNum}, function(data) {
		var userAction = jQuery("#userAction").val();
		jQuery("#validCustRef").val(validCustRefVal);
		if(userAction=="VIEW"){
			jQuery(".customRefs-chosen-checks").prop("disabled", "disabled");
			jQuery("#selectAll-customRefs-check").prop("disabled", "disabled");
			jQuery(".deleteDiv").hide();
		}
	});
	
}

function quickPaginationCustRef(){
	var totalPages = 0;
	var pageNo = 1;
	if(jQuery("#totalPagesCust")&& jQuery("#totalPagesCust").val()){
		totalPages =jQuery("#totalPagesCust").val();
	}
	if(jQuery("#pageNoTextTopRef")&& jQuery("#pageNoTextTopRef").val() && jQuery("#pageNoTextTopRef").val()!=""){
 		pageNo = jQuery('#pageNoTextTopRef').val();
 	}else{
 		return false;
 	}
 	if(pageNo == null  || totalPages == 0)
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
  	var validCustRefVal = jQuery("#validCustRef").val();
   	/*validate page number entered for max no: of pages and numeric value before ajax */ 
   	jQuery("#customRefsTable").html("<img src=\"" + staticContextPath + "/images/loading.gif\" width=\"300px\"/>");
   	jQuery("#customRefsTable").load('rrCreateRuleAction_loadCustRefList.action', {pageNo: pageNo}, function(data) {
		var userAction = jQuery("#userAction").val();
		jQuery("#validCustRef").val(validCustRefVal);
		if(userAction=="VIEW"){
			jQuery(".customRefs-chosen-checks").prop("disabled", "disabled");
			jQuery("#selectAll-customRefs-check").prop("disabled", "disabled");
			jQuery(".deleteDiv").hide();
		}
	});
}


</script>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<body>
	<c:set var="disabled" value="" />
	<c:if test ="${userAction=='VIEW'}">
		<c:set var="disabled" value="DISABLED" />
	</c:if>
	<input type="hidden" id="validCustRef" value="${validCustRef}"/>
	<input type="hidden" id="validCustRefCount" value="${validCustRefCount}"/>
	<input type="hidden" id="duplicateCustRefs" value="${duplicateCustRefs}"/>
		<c:if test="${!empty custRefList}">
			<div class="inlineTableWrapper" style="display: block;">
			<c:if test="${disabled != 'DISABLED'}">	
				<div class="deleteDiv">
					<img src="${staticContextPath}/images/icons/iconTrash.gif"
						class="deleteIcon pointer" onclick="deleteCustomReference();" />
					<a  id="deleteCust" class="paginationLink deleteLink" onclick="deleteCustomReference();">Delete Selected</a>
				</div>
			</c:if>
				<div class="inlineTableContainer" >
					<table id="customRefs-chosen" cellpadding="0" cellspacing="0"
						border="0" class="inlineTable" >
						<thead id="customRefs-chosen-thead">
							<th>
								Select All
								<br />
								<input type="checkbox" id="selectAll-customRefs-check" ${disabled}
									onclick="toggleSelectAllCheck('customRefs')" />
							</th>
							<th>
								Custom Reference
							</th>
						</thead>
						<tbody>
							<c:forEach var="custRef" items="${custRefList}"
								varStatus="rowCounter">
								<tr>
									<td style="word-wrap: break-word;">
										<input type="checkbox" id="${custRef['val']}" name="${custRef['val']}" ${disabled}
											name="customRefs-chosen-checks"
											class="customRefs-chosen-checks" value="" />
										&nbsp;
										
											<b>${custRef['name']}</b>
											
									</td>
									<td style="word-wrap: break-word;overflow: hidden;">
										<c:if test="${fn:length(custRef['value'])>25}">
											${fn:substring(custRef['value'],0,25)}
											<br/>
											${fn:substring(custRef['value'],25,fn:length(custRef['value']))}
										</c:if>
										<c:if test="${fn:length(custRef['value'])<=25}">
										${custRef['value']}
										</c:if>
									</td>
								</tr>
							</c:forEach>
					<c:if test="${custPagination.totalPages>1}">	
					<tr>
					<td colspan="2" >
					<div id="page" align="center" class="paginationCriteriaDiv">
					<input type="hidden" id="totalPagesCust" name="totalPagesCust" value="${custPagination.totalPages}"/>
						<c:if test="${custPagination.currentIndex>1}">
							<a class="pagination paginationLink" id="1" 
							 onclick="paginateCust(this)"><<</a>
							<a class="paginationColor paginationLink"   id="${custPagination.currentIndex-1}" onclick="paginateCust(this)" ><b><</b>
							</a>
							</c:if>
							<b>Page ${custPagination.currentIndex} of ${custPagination.totalPages}</b>
    					<c:if test="${custPagination.currentIndex!=custPagination.totalPages}">
							<a class="paginationColor paginationLink"  onclick="paginateCust(this)" id="${custPagination.currentIndex+1}"><b>></b></a>
							<a class="pagination paginationLink"   id="${custPagination.totalPages}" onclick="paginateCust(this)"
							>>></a>
						</c:if>
						
						<div><b>To Page</b> <input type="text" id="pageNoTextTopRef" size="3" class="pageNoText" /> <input type="button" value="GO" onclick="quickPaginationCustRef()"/></div>
						
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