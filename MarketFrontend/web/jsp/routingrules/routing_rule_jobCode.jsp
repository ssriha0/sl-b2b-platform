<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<script type="text/javascript">
jQuery(document).ready(function() {
 	 jQuery("#priceAll").blur(function() {

	      if(!isNaN(this.value) & this.value != ''){
	          var amt = parseFloat(this.value);
	          var finalAmt = parseFloat(amt).toFixed(2);
	          jQuery(this).val(finalAmt);
	         }else{
	         	this.value ="";
	         }           
	});
    jQuery(".chosenJobPrices").blur(function() {
    
	      if(!isNaN(this.value) & this.value != ''){
	          var amt = parseFloat(this.value);
	          var finalAmt = parseFloat(amt).toFixed(2);
	          jQuery(this).val(finalAmt);
	         }else{
	         	this.value ="";
	         }          
	});
	jQuery(".pageNoTextTopJob").keypress(function (e){
	
  			//if the letter is not digit then display error and don't type anything
  			if( e.which!=8 && e.which!=0 && e.which!=46 && (e.which<48 || e.which>57))
  			{
   				return false;
 			}
	});
	
});
function deleteJobCodes(){
	jQuery("#error_jobCodes").html("");
	jQuery("#error_jobCodes").hide();
	jQuery("#validationError_jobCodes").html("");
	var rows = jQuery("#jobcodes-chosen > tbody > tr").length;
	var deleteValues="";
	var newValid=jQuery("#validJobCode").val();
	var jobPrice="";
	for (i=rows-1; i>=0; i--) {
	    if(jQuery("#jobcodes-chosen > tbody > tr:eq("+i+") > td > input").prop("checked")) {
	    var id =jQuery("#jobcodes-chosen > tbody > tr:eq("+i+") > td > input").prop("id");
	    var valid =jQuery("#validJobCode").val();
		    newValid="";
		    
	    	var jobArray = valid.split(",");
			var i;
			var len=jobArray.length;
			var obj={};
			if(valid!=""){
				for (j=0;j<len;j++){
				if(jobArray[j]==id){
			    	}else{
			    		if(newValid==""){
				    		newValid = jobArray[j];
				    	}else{
				    		newValid = newValid+","+jobArray[j];
				    	}
			    	}
		    	}
		     	jQuery("#validJobCode").val(newValid);			
	    	}
	    	if(deleteValues==""){
		    	deleteValues = id;
		    }else{
		    	deleteValues = deleteValues + ","+ id;
		    }
	    	
	    				
	    }
	} 
	var jobCodePrice = jQuery("#jobCodePrice").val();
	if(deleteValues!=""){
	   jQuery("#showJobPrice").html("<img src=\"" + staticContextPath + "/images/loading.gif\" width=\"300px\"/>");
	   jQuery("#showJobPrice").load("rrCreateRuleAction_deleteJobCodes.action",{jobCodes: deleteValues,job_price:jobCodePrice},function(data) {
	   	jQuery("#validJobCode").val(newValid);	
	   });
	   jQuery('#jobCode-textbox').focus();	
	   jQuery("#jobCodePrice").val("");
	}
}

function changeJobCodePrice(obj,jobCode){
	var jobCodePrice = jQuery("#jobCodePrice").val();
	var val = jQuery(obj).val();
	if(isNaN(val)){
		val="";
	}
	if(jobCodePrice==""){
		jobCodePrice = jobCode + "@@" + val;
	}else{
		jobCodePrice = jobCodePrice+ "," + jobCode + "@@" + val;
	}
	
	jQuery("#jobCodePrice").val(jobCodePrice);
}

function paginateJob(obj){
	var pageNum = obj.id;
	var jobCodePrice = jQuery("#jobCodePrice").val();
	var validJobCode = jQuery("#validJobCode").val();
	jQuery("#showJobPrice").html("<img src=\"" + staticContextPath + "/images/loading.gif\" width=\"300px\"/>");
	jQuery("#showJobPrice").load('rrCreateRuleAction_loadJobCodeList.action', {pageNo: pageNum,job_price:jobCodePrice}, function(data) {
	var userAction = jQuery("#userAction").val();
	jQuery("#validJobCode").val(validJobCode);
	if(userAction=="VIEW"){
		jQuery(".jobcodes-chosen-checks").prop("disabled", "disabled");
		jQuery("#priceAll").hide();
		jQuery("#priceAllButton").prop("disabled", "disabled");
		jQuery("#chosenJobPrices").prop("disabled", "disabled");
		jQuery("#selectAll-jobcodes-check").prop("disabled", "disabled");
		jQuery(".chosenJobPrices").prop("disabled", "disabled");
		jQuery("#priceSet").hide();
		jQuery(".deleteDiv").hide();
	}
	});
}

function quickPaginationJob(){
	var totalPages =1;
	var pageNo =1;
	if(jQuery("#totalPagesJob")&& jQuery("#totalPagesJob").val()){
		totalPages = jQuery('#totalPagesJob').val();
	}
	if(jQuery("#pageNoTextTopJob")&& jQuery("#pageNoTextTopJob").val()&& jQuery("#pageNoTextTopJob").val()!=""){
 		pageNo = jQuery('#pageNoTextTopJob').val();
 	}else{
 		return false;
 	}
 	if(pageNo == null || pageNo == "" || totalPages == 0)
 	{
    	return false;
   	}
   	pageNo = parseInt(pageNo);
   	totalPages = parseInt(totalPages);
 	var jobCodePrice = jQuery("#jobCodePrice").val();
  	if(pageNo > totalPages){
 		pageNo=totalPages;
 	}
 	if(pageNo < 1){
 		pageNo=1;
 	}
  	
   	var validJobCode = jQuery("#validJobCode").val();
   	/*validate page number entered for max no: of pages and numeric value before ajax */ 
   	jQuery("#showJobPrice").html("<img src=\"" + staticContextPath + "/images/loading.gif\" width=\"300px\"/>");
   	jQuery("#showJobPrice").load('rrCreateRuleAction_loadJobCodeList.action', {pageNo: pageNo,job_price:jobCodePrice}, function(data) {
	var userAction = jQuery("#userAction").val();
	jQuery("#validJobCode").val(validJobCode);
	if(userAction=="VIEW"){
		jQuery(".jobcodes-chosen-checks").prop("disabled", "disabled");
		jQuery("#priceAll").hide();
		jQuery("#priceAllButton").prop("disabled", "disabled");
		jQuery("#chosenJobPrices").prop("disabled", "disabled");
		jQuery("#selectAll-jobcodes-check").prop("disabled", "disabled");
		jQuery(".chosenJobPrices").prop("disabled", "disabled");
		jQuery("#priceSet").hide();
		jQuery(".deleteDiv").hide();
		
	}
	});
}

function setAllPrice(){
	var price = jQuery('#priceAll').val();
	jQuery("#error_jobCodes").html("");
	jQuery("#error_jobCodes").hide();
	if(price=="" ||isNaN(price)){
		jQuery("#error_jobCodes").html('Invalid price');
		jQuery("#error_jobCodes").show();
		return false;
	} 
	//set the hidden variable
	var rows = jQuery("#jobcodes-chosen > tbody > tr").length;
	
	var jobCodePrice = jQuery("#jobCodePrice").val();
	var flag = "";
	for (i=rows-1; i>=0; i--) {
		if(jQuery("#jobcodes-chosen > tbody > tr:eq("+i+") > td > input").prop("checked")){
			flag = "found";
		    var jobCode =jQuery("#jobcodes-chosen > tbody > tr:eq("+i+") > td > input").prop("id");
		    jQuery("#jobcodes-chosen > tbody > tr:eq("+i+") > td > .chosenJobPrices").val(price);
		    if(jobCodePrice==""){
				jobCodePrice = jobCode + "@@" + price;
			}else{
				jobCodePrice = jobCodePrice+ "," + jobCode + "@@" + price;
			}
		}
	}
	if(flag==""){
		jQuery("#error_jobCodes").html('No job code selected');
		jQuery("#error_jobCodes").show();
		return false;
	} 	
	jQuery("#jobCodePrice").val(jobCodePrice);
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
	<input type="hidden" id="inValidJobCode" value="${inValidJobCode}"/>
	<input type="hidden" id="duplicateJobCodes" value="${duplicateJobCodes}"/>
	<input type="hidden" id="jobCodePrice"/>
	<input type="hidden" id="validJobCode" name="validJobCode" value="${validJobCode}"/>
	<input type="hidden" id="hasPermit" value="${hasPermit}"/>
		<c:if test="${!empty jobPriceList}">
			<div class="inlineTableWrapper" style="display: block">
			<c:if test="${disabled != 'DISABLED'}">
				<div style="width:280px; padding-bottom: 3px" id="priceSet">
				<table style="border: none;">
				<tr style="border: none;">
				<td style="border: none; ">
				Price all selected $
				</td> 
				<td style="border: none; ">
				<input type="text" id="priceAll" size="4" onchange="" maxlength="12" ${disabled}/>
				</td>
				&nbsp;&nbsp;
				<td style="border: none; " >
				<input type="button" value="ADD" id="priceAllButton" class="button action right" onclick="setAllPrice()" />
				</td>
				</tr>
				</table>
				</div>
			</c:if>
				
			<c:if test="${disabled != 'DISABLED'}">	
			<div class="deleteDiv">
				<img src="${staticContextPath}/images/icons/iconTrash.gif"
					class="deleteIcon pointer" onclick="deleteJobCodes();" />
				<a class="paginationLink deleteLink"  onclick="deleteJobCodes()" style="padding-bottom:3px">Delete Selected</a>
			</div>
			</c:if>
				<div class="inlineTableContainer">
					<table id="jobcodes-chosen" cellpadding="0" cellspacing="0"
						border="0" class="inlineTable" width="280px">
						<thead id="jobcodes-chosen-thead">
							<th>
								Select All
								<br />
								<input type="checkbox" id="selectAll-jobcodes-check" ${disabled}
									onclick="toggleSelectAllCheck('jobcodes')" />
							</th>
							<th>
								Job Codes
							</th>
							<th>
								Job Code Price
							</th>
						</thead>
						<tbody>
							<c:forEach var="jobPrice" items="${jobPriceList}"
								varStatus="rowCounter">
								<tr>
									<td>
										<input type="checkbox" id="${jobPrice.jobCode}" ${disabled}
											name="jobcodes-chosen-checks" class="jobcodes-chosen-checks"
											value="" />
										&nbsp;
									</td>
									<td>
										${jobPrice.jobCode}
									</td>
									<td>
										$
										<input type="text" size="6" width="10px" value="${jobPrice.price}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" onchange="changeJobCodePrice(this,'${jobPrice.jobCode}')"
											class="chosenJobPrices" maxlength="12" ${disabled}/>
									</td>
								</tr>
							</c:forEach>
					<c:if test="${jobPagination.totalPages>1}">
					<tr>
					<td colspan="3">	
					<div id="page" align="center" class="paginationCriteriaDiv">
					<input type="hidden" id="totalPagesJob" name="totalPagesJob" value="${jobPagination.totalPages}"/>
						<c:if test="${jobPagination.currentIndex>1}">
							<a class="pagination paginationLink" id="1"
							 onclick="paginateJob(this)"><<</a>
							<a  class="paginationColor paginationLink" id="${jobPagination.currentIndex-1}" onclick="paginateJob(this)" ><b><</b>
							</a>
							</c:if>
							<b>Page ${jobPagination.currentIndex} of ${jobPagination.totalPages}</b>
    					<c:if test="${jobPagination.currentIndex!=jobPagination.totalPages}">
							<a class="paginationColor paginationLink"  onclick="paginateJob(this)" id="${jobPagination.currentIndex+1}"><b>></b></a>
							<a class="pagination paginationLink" id="${jobPagination.totalPages}" onclick="paginateJob(this)"
							>>></a>
						</c:if>
						
						<div><b>To Page</b> <input type="text" id="pageNoTextTopJob" size="3" class="pageNoText"/> <input type="button" class="goToPageTop" value="GO" onclick="quickPaginationJob()"/></div>
						
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
