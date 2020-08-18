<%@page import="com.servicelive.routingrulesengine.vo.RoutingRulesPaginationVO,
com.servicelive.routingrulesengine.RoutingRulesConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
					
<c:set var="FILE_STATUS_SCHEDULED" value="<%=RoutingRulesConstants.FILE_STATUS_SCHEDULED%>" />
<c:set var="FILE_STATUS_PROCESSING" value="<%=RoutingRulesConstants.FILE_STATUS_PROCESSING%>" />
<c:set var="FILE_STATUS_SUCCESS" value="<%=RoutingRulesConstants.FILE_STATUS_SUCCESS%>" />
<c:set var="FILE_STATUS_FAILURE" value="<%=RoutingRulesConstants.FILE_STATUS_FAILURE%>" />
<c:set var="FILE_STATUS_ERROR" value="<%=RoutingRulesConstants.FILE_STATUS_ERROR%>" />
<c:set var="FILE_STATUS_CONFLICT" value="<%=RoutingRulesConstants.FILE_STATUS_CONFLICT%>" />

<link rel="stylesheet"
	href="${staticContextPath}/javascript/plugins/tablesorter/blue/style.css"
	type="text/css" media="print, projection, screen" />
<script type="text/javascript">var staticContextPath="${staticContextPath}";
var imgSrc2;
</script>

<%
	int sorcol2 = 0;
	int sorord2 = 0;
	RoutingRulesPaginationVO pagination = (RoutingRulesPaginationVO) session
			.getAttribute("uploadRulePagination");
	if (pagination != null) {
		sorcol2 = pagination.getSortCol();
		sorord2 = pagination.getSortOrd();
	}
%>

<script type="text/javascript">

	var curFileSortCol = '<%=sorcol2%>'; // Default sort on 2 = modified date; 1 = File Name, 3 = Uploaded By;
	var curFileSortOrd = '<%=sorord2%>'; // Default sort order 0 = descending; 1 = ascending;
	jQuery(document).ready(function(){
		 if(filesCount>0){			     
			 document.getElementById(sortColumnClass).innerHTML=imgSrc2;
		 }
		 var uploadSortCol = null;
		 switch (curFileSortCol) {
  			case "1": uploadSortCol = "fileName"; break;
  			case "2": uploadSortCol = "uploadedDate"; break;
  			case "3": uploadSortCol = "processCompletedDate"; break;
		}
		jQuery("#"+uploadSortCol).html("<img src=\"" + staticContextPath + "/images/grid/arrow-" + ((curFileSortOrd == "0")?"down":"up") + "-white.gif\"/>");
		jQuery("#"+uploadSortCol).show();
	}); 
	   
	jQuery(document).on('click', ".filePageIndex", function(){
	   
		    // hide the error and success
			jQuery('#uploadValidationFrontError').hide();
			jQuery('#uploadValidationError').hide();
			jQuery('#SuccessMessage').hide();

	        filesCount = 0;
			jQuery("#mainTableforUploadResults").html('<tr><td><img src="' + staticContextPath + '/images/loading.gif" width="800px"/></td></tr>');
			jQuery("#uploadedFiles").load("routingRulesUploadTabAction_display.action?pageNo=" + jQuery(this).prop("id"));
		});
		
		jQuery('.sortColumnClass').click(function(){
			 // hide the error and success
			jQuery('#uploadValidationFrontError').hide();
			jQuery('#uploadValidationError').hide();
			jQuery('#SuccessMessage').hide();	
			jQuery('#mainTableforUploadResults').html
				('<tr><td><img src="${staticContextPath}/images/loading.gif" width="800px"/></td></tr>');
			var newSortCol = jQuery(this).prop('id').substring(11,10);			
			var newSortOrd = null;
			if (newSortCol != curFileSortCol) { // If sort column changes, reset sort order to ascending
				newSortOrd = 0;
			} else {
				newSortOrd = 1 - curFileSortOrd; // toggle between ascending and descending order
			}
			switch(newSortCol){
				case '1':sortColumnClass='fileName';
						break;
				case '2':sortColumnClass='uploadedDate';
						break;
				case '3':sortColumnClass='processCompletedDate';
						break;
			}
			_sortDir = (newSortOrd == 0) ? 'down' : 'up';
	   			
			jQuery('#uploadedFiles').load("routingRulesUploadTabAction_display.action?pageNo=1&sortCol=" + newSortCol + "&sortOrd=" + newSortOrd);
			curFileSortCol = newSortCol;
			curFileSortOrd = newSortOrd;
			filesCount++;
			
		});	
		jQuery(document).on('click', ".commentsClickFile", function(e){
	        var x = e.pageX;
			var y = e.pageY;
			var routingFileId= jQuery(this).prop("id");
		    jQuery("#"+routingFileId+"fileErrors").css("top", y - 210);
	        jQuery("#"+routingFileId+"fileErrors").css("left", x - 791);
			jQuery("#"+routingFileId+"fileErrors").css("z-index",999999999);			
			jQuery("#"+routingFileId+"fileErrors").show();
		});
		jQuery(document).on('click', ".fileErrorCancelLink", function(){
			var routingFileId= jQuery(this).prop("id");
			jQuery("#"+routingFileId+"fileErrors").hide();
			return false;
		});
	
		jQuery(".commentsClickFile").blur(function (e){
			jQuery(".fileErrors").hide();
		}); 
		
		jQuery("body").click(function () {
			jQuery(".ruleComment1").hide();
		});
		
		// Page Navigation on go to button
		jQuery(document).on('click', ".goToPageTopFileUpload", function(){
		
			// hide the error and success
			jQuery('#uploadValidationFrontError').hide();
			jQuery('#uploadValidationError').hide();
			jQuery('#SuccessMessage').hide();
			
			var totalPagesTop=${uploadRulePagination.totalPages};
 	 		var pageNoTop = jQuery('#pageNoTextTopFileUpload').val();
 	 
 	 		if(pageNoTop == null || pageNoTop == ""){
    			return false; 
   			}
 	 		if(pageNoTop > totalPagesTop ){
 				pageNoTop = totalPagesTop;
 			}else if(pageNoTop<1){
 				pageNoTop = 1;
 			}
 			
  		 	jQuery("#mainTableforUploadResults").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
   			jQuery("#uploadedFiles").load("routingRulesUploadTabAction_display.action?pageNo=" + pageNoTop);
 		 });
 		 
 		 
 		 // Page Navigation on go to button
 		 jQuery(document).on('click', ".goToPageBottomFileUpload", function(){
			
			// hide the error and success
			jQuery('#uploadValidationFrontError').hide();
			jQuery('#uploadValidationError').hide();
			jQuery('#SuccessMessage').hide();
			var totalPagesBottom = ${uploadRulePagination.totalPages};
 	 		var pageNoBottom = jQuery('#pageNoTextBottomFileUpload').val();
 	 		
 	 		if(pageNoBottom == null || pageNoBottom == ""){
    			return false; 
   			}
 	 		
 	 		if(pageNoBottom > totalPagesBottom ){
 				pageNoBottom = totalPagesBottom;
 			}else if(pageNoBottom<1){
 				pageNoBottom = 1;
 			}
 			
  		 	jQuery("#mainTableforUploadResults").html("<tr><td><img src=\"" + staticContextPath + "/images/loading.gif\" width=\"800px\"/></td></tr>");
   			jQuery("#uploadedFiles").load("routingRulesUploadTabAction_display.action?pageNo=" + pageNoBottom);
 		 });
 		 
	function rowWarningHighlightAlert(rowid) {
		jQuery(rowid).addClass("alertHighlight");
		var myClass = jQuery(rowid).prop('class');
	    jQuery(".alertHighlight > td:lt(2)").css("border-left", "2px solid #ffcc66");
	}
	
	jQuery('#pageNoTextTopFileUpload').keypress(function (e){
  			//if the letter is not digit then display error and don't type anything
  			if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57)){
   				return false;
 			}
		});
	
	jQuery('#pageNoTextBottomFileUpload').keypress(function (e){
  			//if the letter is not digit then display error and don't type anything
  			if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57)){
   				return false;
 			}
		});
	
	function loadSearchFunction(text){
		var queryString="";
		var searchType = 4; // File name search 
		var archive = 1;  // Include archived rules in the search
		
		// Remove the file extension
		var textValue=jQuery.trim(text);
		textValue = escape(textValue);
		var length=textValue.length;			
		var dotInd=textValue.indexOf(".");
		var substrInd=textValue.substring(dotInd,length);
		if(substrInd==".xls" || substrInd==".xlsx" ){
			textValue=textValue.substring(0,dotInd);
		}
				
		queryString += "value=" + textValue + "&column=" + searchType + "&archive=" + archive + "&exactSearch=true";
		
		var $tabs = jQuery('#carTabs').tabs(); // first tab selected
		$tabs.tabs( "option", "active", 2 ); // Selected the search tab
		searchTabRendered = true;
		
		// Populate the page
		jQuery('#SearchValidationError').hide();					     					
		jQuery('#ZeroResultsError').hide();
		jQuery('#SuccessMessage').hide();
		jQuery('#selectedItemSearch').hide();
		jQuery('#ruleWarningSearch').hide();
		jQuery('#mainTableforSearch').html('<tr><td><img src="${staticContextPath}/images/loading.gif" width="800px"/></td></tr>');
		
		jQuery('#searchresults').load('routingRuleSearch_display.action?'+queryString,function()
     		{
       			var listsize = jQuery('#listSize1').val();
	   			var searchBy ="Uploaded FileName";
	    		if(listsize==0){
	   				var searchFound="";
	    			searchFound+=" ''"+searchBy+": "+text+"'' ";
	     			jQuery('#ZeroResultsError > .successText').html(searchFound);
	    			jQuery('#ZeroResultsError').show();
	    			jQuery('#SearchText').val('');
	    			var SearchBy = document.getElementById('SearchBy');
					SearchBy.options[0].selected = true; 
					jQuery("#showArchiveRules").prop("checked",true); 
					jQuery('#ruleWarningSearch').hide();  
	    		}else{
	  				var searchFound="";
	   				searchFound+=" ''"+searchBy+": "+text+"''";
	   				listsize+=" ";
	     			jQuery('#SuccessMessage > .successSize').html(listsize);
	     			jQuery('#SuccessMessage > .successText').html(searchFound);
					jQuery('#SuccessMessage').show();
					jQuery('#selectedItemSearch').show();
					jQuery('#SearchText').val('');
					var SearchBy = document.getElementById('SearchBy');
					SearchBy.options[0].selected = true;  
					jQuery("#showArchiveRules").prop("checked",true);
					jQuery('#ruleWarningSearch').hide();     
				}
				jQuery('#name1').html("");
	        	jQuery('#contact1').html("");
	        	jQuery('#status1').html("");
			}
			);
	}
	
</script>

<div id="uploadedFiles">
<!-- The top pagination links -->

	<c:if test="${fn:length(uploadedFileList) > 0}">
		<div id="pagination" align="right" style="padding-top: 5px;">
			<c:if test="${uploadRulePagination.totalPages>0}">			
				<c:if test="${uploadRulePagination.currentIndex>1}">
					<a class="filePageIndex paginationLink" id="1"
						style="text-decoration: underline;color: #00A0D2"><<
					</a>
					<a class="filePageIndex paginationLink"
						id="${uploadRulePagination.currentIndex-1}" style="color: #00A0D2"><b><</b>
					 </a>
				</c:if>
				Page ${uploadRulePagination.currentIndex} of ${uploadRulePagination.totalPages}
				
				<c:if test="${uploadRulePagination.currentIndex!=uploadRulePagination.totalPages}">
					<a class="filePageIndex paginationLink"
						id="${uploadRulePagination.currentIndex+1}" style="color: #00A0D2"><b>></b>
					</a>
					<a class="filePageIndex paginationLink" id="${uploadRulePagination.totalPages}"
						style="text-decoration: underline;color: #00A0D2">>>
				   </a>
			   </c:if>
			   <c:if test="${uploadRulePagination.totalPages>1}">
				   <span style="font-size: 13px"> To Page </span> 
				   <input type="text" id="pageNoTextTopFileUpload" size="3" /> 
				   <input type="button" class="goToPageTopFileUpload" value="GO"/>
			   </c:if>
			</c:if>
			<div style="padding-top: 8px">
				<span style="float: left;">Uploaded files are displayed in order of the most recently added.</span>
				${uploadRulePagination.pageComment} files.
			</div>			
		</div>
	</c:if> 
	
	<div>
		<!--  Display the uploaded files -->
		<table id="mainTableforUploadResults" border="0" cellpadding="0" cellspacing="0" style="padding-top: 6px;word-wrap:break-word;">
			<c:if test="${fn:length(uploadedFileList) > 0}">
				<thead>	
					<th id="sortColumn1" width="40%" class="sortColumnClass" >
						<span>Uploaded File Name</span><span id="fileName" style="display: none;"></span>
					</th>
					<th id="sortColumn2" width="25%" class="sortColumnClass" style="text-align: left">
						<span>Upload Info </span><span id="uploadedDate" style="display: none;"></span>
					</th>
					<th id="sortColumn3" width="35%" class="sortColumnClass" style="text-align: left">
						<span>Processed Rules</span><span id="processCompletedDate" style="display: none;"></span>
					</th>
				</thead>
				
				<tbody>
					<c:forEach items="${uploadedFileList}" var="uploadedFile" varStatus="status">
					  <tr id="rowid1${status.count}" class="dashboardRow">
					  		<!-- File Name -->
					  		<td>
								<div id="uploadedFileName"
									<c:if test="${fn:length(uploadedFile.uploadedFileName) > 60}">
										 style="overflow-x:auto; width:400px;"</c:if>>
										<c:choose>
											<c:when test="${(FILE_STATUS_SUCCESS == uploadedFile.fileStatus)
												or (FILE_STATUS_CONFLICT == uploadedFile.fileStatus)}" >
												<a href="#" title="${uploadedFile.uploadedFileName}" 
												    id="${uploadedFile.uploadedFileName}" 
													onclick ="loadSearchFunction(this.id);" style="color: #00A0D2;"
													class="searchFileRules" >
													<b>${uploadedFile.uploadedFileName}</b>
												</a>
										 	</c:when> 
										 	<c:otherwise>
											 	<b>${uploadedFile.uploadedFileName}</b>
										 	</c:otherwise>
									 	</c:choose>
								</div>
								
							</td>
							
							<!-- Uploaded Info(user id and the time of upload)-->
							<td>
								<span><b>${uploadedFile.uploadedBy}</b> ${uploadedFile.uploadedUserId} <br/>
								${uploadedFile.createdDate}</span>
							</td>
							
							<!-- Processed Records -->
							<td>
								<c:choose>
								 	
									<c:when test="${FILE_STATUS_SCHEDULED == uploadedFile.fileStatus}" >
											<!--  Scheduled -->	
											<img src="${staticContextPath}/images/spinner_small.gif" 
												alt="Queued"/> Queued
									 </c:when>
									
									 <c:when test="${FILE_STATUS_PROCESSING == uploadedFile.fileStatus}" >
											 <!--  Processing -->	
											<img src="${staticContextPath}/images/spinner_small.gif" 
												alt="Processing"/> Processing
									 </c:when>
									
									 <c:when test="${FILE_STATUS_SUCCESS == uploadedFile.fileStatus}">
									     <!--  Success -->	
									    <b>Successful</b>
									   	<c:choose>
									   
									    	<c:when test="${not empty uploadedFile.numNewRules}">
									    		<!-- Ideally only any one of these details will be available in the table -->
									    			<b>:</b> <font color="#008000"> ${uploadedFile.numNewRules} </font>
									    	</c:when>
									    	<c:when test="${not empty uploadedFile.numUpdateRules}">
									    			<b>:</b> <font color="#008000"> ${uploadedFile.numUpdateRules} </font>
									    	</c:when>
									    </c:choose>
									 </c:when>
									 
									 
									 <c:when test="${FILE_STATUS_CONFLICT == uploadedFile.fileStatus}">
										<!--  Conflict -->
										 <b>Conflicts</b>
										 <c:if test="${not empty uploadedFile.numConflicts}">
										 	<b>:</b><font color="#FFA500">${uploadedFile.numConflicts}</font>
										 </c:if>
									 </c:when>
									 
									 
									 <c:when test="${FILE_STATUS_ERROR == uploadedFile.fileStatus}">
									 <!--  Error -->
									  <b>Errors</b>
									  	<c:if test="${not empty uploadedFile.numErrors}">
										 	<b>:</b><font color="#FF0000">${uploadedFile.numErrors}</font>
										 </c:if>
										
									    <c:if test="${not empty uploadedFile.routingRuleFileErrors}">
									    <br/>
											<div class="warningBoxForUpload">
												<img src="${staticContextPath}/images/icons/conditional_exclamation.gif">
												&nbsp;<b>ALERT: File Problem(s).Details</b>
												<a href="#" title="File Problem(s)"
													class="commentsClickFile" id="${uploadedFile.routingRuleFileHdrId}"
													onclick="return false;" style="color: #00A0D2;"><b>(+)</b></a>											
												<div id="${uploadedFile.routingRuleFileHdrId}fileErrors" 
													class = "ruleComment1"
													style="display:none; z-index:999999999;
													width:600px; height:160px; overflow:auto; 
													border: solid 1px #ccc; background: #fff;  position:absolute; 
													padding:5px;">
													
													<div style="text-align: right;">
														<a href="" id="${uploadedFile.routingRuleFileHdrId}" class="fileErrorCancelLink"><b>Close
														</b></a>
													</div>
													<div id="fileErrorsText">
														<b>File Name </b> <br/>
														${uploadedFile.uploadedFileName}<br/><br/>
														
														<c:if test="${not empty uploadedFile.uploadAction}">
															<b>Action :</b> ${uploadedFile.uploadAction}<br/><br/>
														</c:if>
														<b>**Problems** </b><br>
														<c:forEach var="errors" 
															items="${uploadedFile.routingRuleFileErrors}" > 
															<c:if test="${not empty errors}">
																<c:out value="${errors}" /> <br/>
															</c:if>	
														</c:forEach>
													</div>
												</div>
											</div>
									    </c:if> 
									 </c:when>
									 
									
									 <c:when test="${FILE_STATUS_FAILURE == uploadedFile.fileStatus}">
									  <!-- Failure(System errors) TODO is it required-->
									 	<b>Processing Failed</b>
									 </c:when>
									 <c:when test="${not empty uploadedFile.processCompletedTime}">
										 <br>
										<b>Completed:</b> ${uploadedFile.processCompletedTime}
									  </c:when>
								</c:choose>
							</td>
					  </tr>
					</c:forEach>
				</tbody>
			</c:if>
		</table>
		
		</div>

<!-- The bottom pagination links -->

<c:if test="${fn:length(uploadedFileList) > 0}">
		<div id="pagination" align="right" style="padding-top: 5px;">
			<c:if test="${uploadRulePagination.totalPages>0}">			
				<c:if test="${uploadRulePagination.currentIndex>1}">
					<a class="filePageIndex paginationLink" id="1"
						style="text-decoration: underline;color: #00A0D2"><<
					</a>
					<a class="filePageIndex paginationLink"
						id="${uploadRulePagination.currentIndex-1}" style="color: #00A0D2"><b><</b>
					 </a>
				</c:if>
				Page ${uploadRulePagination.currentIndex} of ${uploadRulePagination.totalPages}
				
				<c:if test="${uploadRulePagination.currentIndex!=uploadRulePagination.totalPages}">
					<a class="filePageIndex paginationLink"
						id="${uploadRulePagination.currentIndex+1}" style="color: #00A0D2"><b>></b>
					</a>
					<a class="filePageIndex paginationLink" id="${uploadRulePagination.totalPages}"
						style="text-decoration: underline;color: #00A0D2">>>
				   </a>
			   </c:if>
			   <c:if test="${uploadRulePagination.totalPages>1}">
				   <span style="font-size: 13px"> To Page </span> 
			  	   <input type="text" id="pageNoTextBottomFileUpload" size="3"/>
			   	   <input type="button" class="goToPageBottomFileUpload" value="GO"/>
			   </c:if> 
			</c:if>
			<div style="padding-top: 8px">
				${uploadRulePagination.pageComment} files
			</div>
		</div>		
	</c:if> 
</div>