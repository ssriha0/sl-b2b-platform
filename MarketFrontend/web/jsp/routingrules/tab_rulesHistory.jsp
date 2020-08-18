<%@ page language="java"
	import="java.util.*, com.servicelive.routingrulesengine.RoutingRulesConstants"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="alertActive" value="<%=RoutingRulesConstants.ROUTING_ALERT_STATUS_ACTIVE%>" />
<c:set var="archive" scope="request" value="${archiveInd}"> </c:set>

<head>
<script type="text/javascript">
var staticContextPath="${staticContextPath}";
	var curHistSortCol = "${ruleHistPagination.sortCol}"; // Default sort on 0,2 = modified date; 1 = Name, 3 = Contact, 4 = Action
	var curHistSortOrd = "${ruleHistPagination.sortOrd}";
	var archive = "${archive}";
	
	if (curHistSortCol == "" || curHistSortCol == "0") curHistSortCol = "2";
	if (curHistSortOrd == "") curHistSortOrd = "0";
	var sortColumn2 = null;
	var archiveInd= archive;
	jQuery(document).ready(function($)
	{
		switch (curHistSortCol) {
  			case "0":
  			case "2": sortColumn2 = "modifiedDate"; break; 
  			case "1": sortColumn2 = "ruleName"; break;
  			case "3": sortColumn2 = "histContact"; break;
 			case "4": sortColumn2 = "histAction"; break;
		}
		jQuery("#"+sortColumn2).html("<img src=\"" + staticContextPath + "/images/grid/arrow-" + ((curHistSortOrd == "0")?"down":"up") + "-white.gif\"/>");
		jQuery(".histPageIndex").click(function () {	
		
			jQuery("#historyTable").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
			jQuery("#tab_rulesHistory").load("rr_getRoutingRuleHdrHist.action?pageNo=" + jQuery(this).prop("id")+ "&archive=" +archiveInd , function()
		            {	
		            	if(archive==1){
		            	
		            	jQuery("#showArchiveRulesHist").prop("checked",true);
		            	}
		            }
            	);
			}
		);
		
		jQuery('#pageNoTextTopHist').keypress(function (e){
  			//if the letter is not digit then display error and don't type anything
  			if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57))
  			{
   				return false;
 			}
	});
	jQuery(document).on('click', ".commentsClick", function(e){
	
		var x = e.pageX;
		var y = e.pageY;

		var routingFileId= jQuery(this).prop("id");
		jQuery("#ruleCommentsText").html(jQuery(this).prop("title"));
		
		jQuery("#ruleComments").css("top", y - 210);
		jQuery("#ruleComments").css("left", x - 291);
		jQuery("#ruleComments").css("display", "block");
		jQuery("#ruleComments").show();
		
    });

	jQuery('#pageNoTextBottomHist').keypress(function (e){
  			//if the letter is not digit then display error and don't type anything
  			
  			if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57))
  			{
   				return false;
 			}
	});

	jQuery(".goToPageTopHist").click(function () {
		
			var totalPages=jQuery("#ruleHistTotPages").val();
	 	 	var pageNo = jQuery('#pageNoTextTopHist').val();
		 	if(pageNo == null || pageNo == "")
		 	 {
		    	return false;
		   	 }
			pageNo = parseInt(pageNo);
		   	totalPages = parseInt(totalPages);
	 	 	if(pageNo > totalPages)
			 {
			  pageNo=totalPages;
			 }
	 		if(pageNo < 1)
			{
			  pageNo=1;
			}
	   		/*validate page number entered for max no: of pages and numeric value before ajax */ 
	   
		   	jQuery("#historyTable").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
		    	 	
		   	jQuery("#tab_rulesHistory").load("rr_getRoutingRuleHdrHist.action?pageNo=" + pageNo+"&archive="+archiveInd, function()
	           {	
		           	if(archive==1)
		           	{
		           		jQuery("#showArchiveRulesHist").prop("checked",true);
		           	}
	           });
  		});
  
  	jQuery(".goToPageBottomHist").click(function () {
		
		var totalPages=jQuery("#ruleHistTotPages").val();
	 	 var pageNo = jQuery('#pageNoTextBottomHist').val();
	 	 if(pageNo == null || pageNo == "")
	 	 {
	    	return false;
	   	 }
	 	 pageNo = parseInt(pageNo);
		 totalPages = parseInt(totalPages);
	 	 if(pageNo > totalPages)
	 	{
	 	    pageNo=totalPages;
	 	}
	 	if(pageNo < 1)
	 	{
	 		pageNo=1;
	 	}
   
			/*validate page number entered for max no: of pages and numeric value before ajax */ 
			jQuery("#historyTable").html("<tr><td><img src='" + staticContextPath + "/images/loading.gif' width='800px'/></td></tr>");
   			jQuery("#tab_rulesHistory").load("rr_getRoutingRuleHdrHist.action?pageNo=" + pageNo+"&archive="+archiveInd, function()
            {	
            	if(archive==1){
            	
            	jQuery("#showArchiveRulesHist").prop("checked",true);
            	}
            });
  });

		jQuery(".sortColumn2").click(function () {
		
			jQuery("#historyTable").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
			var newSortCol = jQuery(this).prop("id").substring(7, 6);
			var newSortOrd = null;
			if (newSortCol != curHistSortCol) { // If sort column changes, reset sort order to ascending
				newSortOrd = 0;
			} else {
				newSortOrd = 1 - curHistSortOrd; // toggle between ascending and descending order
			}
			switch (newSortCol) {
	  			case "0":
	  			case "2": sortColumn2 = "modifiedDate"; break;
	  			case "1": sortColumn2 = "ruleName"; break;
	  			case "3": sortColumn2 = "histContact"; break;
	 			case "4": sortColumn2 = "histAction"; break;
			}
			
			jQuery("#tab_rulesHistory").load("rr_getRoutingRuleHdrHist.action?pageNo=1&sortCol=" + newSortCol + "&sortOrd=" + newSortOrd + "&archive="+archiveInd, function()
            {	
            	if(archive==1)
            	{
            	jQuery("#showArchiveRulesHist").prop("checked",true);
            	}
            });
			curHistSortCol = newSortCol;
			curHistSortOrd = newSortOrd;
			histCount++;
		});
		
		jQuery("#modifiedDateSortHist").click(function() 
		{
		    histCount = 0;
		    jQuery("#historyTable").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
            jQuery("#tab_rulesHistory").load("rr_getRoutingRuleHdrHist.action?pageNo=1&sortCol=0&sortOrd=0"+"&archive="+archiveInd, function()
            {	
            	if(archive==1)
            	{
            	jQuery("#showArchiveRulesHist").prop("checked",true);
            	}
            }); 
        });
        
        jQuery("#showArchiveRulesHist").click(function() 
        {
        	if(this.checked)
        	{
        	setCursorWait(true);
        	archiveInd=1;
        	histCount = 0;
        			    
		    jQuery("#historyTable").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
            setCursorWait(false);
            jQuery("#tab_rulesHistory").load("rr_getRoutingRuleHdrHist.action?archive=1", function()
            {
            	// alert("checked");	
             	jQuery("#showArchiveRulesHist").prop("checked",true);
             	
            }
            );
                        
        	}
        	
        	else
        	{
        	setCursorWait(true);
        	archiveInd=0;
        	histCount = 0;		    
		    jQuery("#historyTable").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
            jQuery("#tab_rulesHistory").load("rr_getRoutingRuleHdrHist.action?archive=0", function()
            {
            	setCursorWait(false);
            });           
            } 
        } 
        );
		
	});
</script >

</head>
	<body>
<div id="tab_rulesHistory">
<div>
		<tr>
        <td>
			<b style="padding-right: 5px; font-size: large" >Rule History</b>  
		</td>
        <td>
        <input type="checkbox" value="unchecked"  id="showArchiveRulesHist"  style="padding-right: 5px" /> <b>Show archived rules </b> 
        </td>
        </tr>
</div>
	
	
	<div>	
	<c:if test="${fn:length(listRoutingRuleHdrHist) > 0}">
	<div id="page" align="right" style="padding-top: 5px;">
		
		<c:if test="${ruleHistPagination.currentIndex>1}">
			<a  class="histPageIndex paginationLink" id="1"
				style="text-decoration: underline;color: #00A0D2" onclick="return false;" ><<</a>
			<a  class="histPageIndex paginationLink"
				id="${ruleHistPagination.currentIndex-1}" style="color: #00A0D2" onclick="return false;"><b><</b> </a>
		</c:if>
		<input type="hidden" id="ruleHistTotPages" value="${ruleHistPagination.totalPages}"/>
		Page ${ruleHistPagination.currentIndex} of ${ruleHistPagination.totalPages}
		<c:if
			test="${ruleHistPagination.currentIndex!=ruleHistPagination.totalPages}">
			<a class="histPageIndex paginationLink"
				id="${ruleHistPagination.currentIndex+1}" style="color: #00A0D2" onclick="return false;"><b>></b> </a>
			<a class="histPageIndex paginationLink" id="${ruleHistPagination.totalPages}"
				style="text-decoration: underline;color: #00A0D2" onclick="return false;">>></a>
		</c:if>
		&nbsp;To Page 
		<input type="text" id="pageNoTextTopHist" size="3"/> <input type="button" class="goToPageTopHist" value="GO"/>
		<div style="padding-top: 8px">
		<span id="modifiedDateSortHist"><a href="#" style="color: #00A0D2;float: left;">Re-sort by Most Recent</a></span>
			${ruleHistPagination.pageComment}
		</div>
	</div>
	</c:if>
	<div id="rulesHistory">
		<table id="historyTable" border="0" cellpadding="0" cellspacing="0" style="padding-top: 6px">
			<c:if test="${fn:length(listRoutingRuleHdrHist) > 0}">
			<thead>
				<th id="column1" class="sortColumn2" style="width: 26%">
					<span>Rule </span><span id="ruleName"></span>
				</th>
				<th id="column2" class="sortColumn2" style="width: 16%">
					<span>Date </span><span id="modifiedDate"></span>
				</th>
				<th id="column3" class="sortColumn2" style="width: 20%" >
					<span>Name </span><span id="histContact"></span>
				</th>
				<th id="column4" class="sortColumn2" style="width: 38%" >
					<span>Action </span><span id="histAction"></span>
				</th>
			</thead>
			<tbody>
				<c:forEach items="${listRoutingRuleHdrHist}" var="rule"
					varStatus="status">
					<tr>
						<td>
							<div style="overflow-x: auto; width: 210px;word-wrap: break-word;">
								${rule.ruleName}
								<br>
								ID#&nbsp;${rule.routingRuleHdrId}
							</div>
						</td>
						<td>
							<span class="bold">${rule.modifiedDate}</span>
						</td>
						<td class="column3" style="word-wrap: break-word" >
							<span class="bold">${rule.modifiedBy}</span>
							<c:if test="${not empty rule.ruleComment}">
								<br />
								<img title="${rule.ruleComment}"
									src="/ServiceLiveWebUtil/images/icons/iconComments.gif"
									class="commentsClick" />
	                   Comments<a href="" title="${rule.comment}" id="${rule.routingRuleHdrId}"
									class="commentsClick" onclick="return false;">(+)</a>
							</c:if>
						</td>
						<td class="column4" style="word-wrap: break-word">
						${rule.ruleAction}
						<c:if test="${rule.fileName!=null}">
							<span>by Upload File
							 <b>${rule.fileName}</b>
							 </span>
						</c:if>
							
						</td>
					</tr>
				</c:forEach>
			</tbody>
			</c:if>
		</table>

		<div id="ruleComments">
			<div style="text-align: right;">
				<a href="" class="cancelLink"
					onclick="jQuery('#ruleComments').hide('slow');return false;">Close</a>
			</div>
			<div id="ruleCommentsText"></div>
		</div>
	</div>

    <c:if test="${fn:length(listRoutingRuleHdrHist) > 0}">
	<div id="page" align="right" style="padding-top: 5px;">
		
		<c:if test="${ruleHistPagination.currentIndex>1}">
			<a class="histPageIndex paginationLink" id="1"
				style="text-decoration: underline; color: #00A0D2" onclick="return false;"><<</b>
			</a>
			<a  class="histPageIndex paginationLink"
				id="${ruleHistPagination.currentIndex-1}" style="color: #00A0D2" onclick="return false;"><b><</b> </a>
		</c:if>
		
		Page ${ruleHistPagination.currentIndex} of ${ruleHistPagination.totalPages}
		<c:if
			test="${ruleHistPagination.currentIndex!=ruleHistPagination.totalPages}">
			<a class="histPageIndex paginationLink" 
				id="${ruleHistPagination.currentIndex+1}" style="color: #00A0D2" onclick="return false;"><b>></b> </a>
			<a class="histPageIndex paginationLink" id="${ruleHistPagination.totalPages}"
				style="text-decoration: underline;color: #00A0D2" onclick="return false;">>></a>
		</c:if>
		&nbsp;To Page 
		<input type="text" id="pageNoTextBottomHist" size="3"/> <input type="button" class="goToPageBottomHist" value="GO"/>
		<div style="padding-top: 10px">
			${ruleHistPagination.pageComment}
		</div>
	</div>
	</c:if>
</div>
</div>	

	

</body>
