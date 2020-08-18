<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<html>
<head>


<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/jqueryui/jquery-ui-1.7.2.custom.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/filter_avilable_providers.css" />

</head>
<body>
<div id="rightsidemodules3" class="colRight255 clearfix">
	<div class="grayModuleContent">	
	<form action="refineProviderList" id="refineProviderListM" name="refineProviderListM" method="POST" enctype="multipart/form-data">	
		<div class="darkGrayModuleHdr" style="width: 225px;">Filter Available Providers</div>
		<br/>
		<label><strong>Company Reported Insurance</strong></label>
		
		<p>
			<strong>General Liability </strong>(Minimum Amount)
			<br/>
			<div style="font-size: 8px; font-weight:bold; position: relative;">
				<select style="width:45%;background-color: #ffffff;" size="1" id="generalLiabilityRating" name="generalLiabilityRating">
				<option value="0">View all</option>
				    <c:forEach var="lookupVO" items="${generalLiabilityRatingList}" >            		
	                      <c:choose>
	            			<c:when test="${lookupVO.id == providerSearchDto.generalLiabilityRating}"> 
			                    <option selected value="${lookupVO.id}">
			                         ${lookupVO.descr}
			                  	</option>
	               			</c:when>
	               			<c:otherwise>
	               				 <option value="${lookupVO.id}">
			                         ${lookupVO.descr}
			                  	</option>
	               			</c:otherwise>
	              		</c:choose>              				                             
		             </c:forEach>
				</select>
				<!-- Jira#19239 -->
				<c:choose>
	            			<c:when test="${providerSearchDto.glRatingCheckBox == true}"> 
			                    <input type="checkbox" id="glRatingCheckBox" name="glRatingCheckBox" value="true" checked="checked"/><span style="display:block; position:absolute; right:9px; top:5px;">ServiceLive&nbsp;Verified</span>
	               			</c:when>
	               			<c:otherwise>
	               				 <input type="checkbox" id="glRatingCheckBox" name="glRatingCheckBox" value="true" /><span style="display:block; position:absolute; right:9px; top:5px;">ServiceLive&nbsp;Verified</span>
	               			</c:otherwise>
	             </c:choose>  
				
			</div>	
		</p>
			
		
		<p>
			<strong>Vehicle Liability </strong>(Minimum Amount)
			<br/>
			<div style="font-size: 8px; font-weight:bold; position: relative;">
				<select style="width:45%;background-color: #ffffff;" size="1" id="vehicleLiabilityRating" name="vehicleLiabilityRating">
				<option value="0">View all</option>  
				    <c:forEach var="lookupVO" items="${vehicleLiabilityRatingList}" >            		
	                      <c:choose>
	            			<c:when test="${lookupVO.id == providerSearchDto.vehicleLiabilityRating}"> 
			                    <option selected value="${lookupVO.id}">
			                         ${lookupVO.descr}
			                  	</option>
	               			</c:when>
	               			<c:otherwise>
	               				 <option value="${lookupVO.id}">
			                         ${lookupVO.descr}
			                  	</option>
	               			</c:otherwise>
	              		 </c:choose>                    
		             </c:forEach>
				</select>
				<!-- Jira#19239 -->
				<!-- <input type="checkbox" id="vlRatingCheckBox" name="vlRatingCheckBox" value="true" /> ServiceLive Verified -->
				<c:choose>
	            			<c:when test="${providerSearchDto.vlRatingCheckBox == true}"> 
			                    <input type="checkbox" id="vlRatingCheckBox" name="vlRatingCheckBox" value="true" checked="checked"/><span style="display:block; position:absolute; right:9px; top:5px;">ServiceLive&nbsp;Verified</span>
	               			</c:when>
	               			<c:otherwise>
	               				<input type="checkbox" id="vlRatingCheckBox" name="vlRatingCheckBox" value="true"  /><span style="display:block; position:absolute; right:9px; top:5px;">ServiceLive&nbsp;Verified</span>
	               			</c:otherwise>
	             </c:choose>
			</div>				
		</p>
		
		<p>
			<strong>Worker's Compensation </strong>(Minimum Amount)
			<br/>
			<div style="font-size: 8px; font-weight:bold; width:100%; position:relative;">
				<select style="width:121px;;background-color: #ffffff; margin-right:1px;" size="1" id="workersCompensationRating" name="workersCompensationRating">
				<option value="0">View all</option>
				    <c:forEach var="lookupVO" items="${workersCompensationRatingList}" >            		
	                     <c:choose>
	            			<c:when test="${lookupVO.id == providerSearchDto.workersCompensationRating}"> 
			                    <option selected value="${lookupVO.id}">
			                         ${lookupVO.descr}
			                  	</option>
	               			</c:when>
	               			<c:otherwise>
	               				 <option value="${lookupVO.id}">
			                         ${lookupVO.descr}
			                  	</option>
	               			</c:otherwise>
	              		 </c:choose>                
		             </c:forEach>
				</select>
				<!-- Jira#19239 -->
				<%-- <input type="checkbox" id="wcRatingCheckBox" name="wcRatingCheckBox" cssStyle="margin:0; padding:0;" value="true" /><span style="display:block; position:absolute; right:-2px; top:5px;">ServiceLive&nbsp;Verified</span> --%>
				<c:choose>
	            			<c:when test="${providerSearchDto.wcRatingCheckBox == true}"> 
			                    <input type="checkbox" id="wcRatingCheckBox" name="wcRatingCheckBox" cssStyle="margin:0; padding:0;" value="true" checked="checked"/><span style="display:block; position:absolute; right:-2px; top:5px;">ServiceLive&nbsp;Verified</span>
	               			</c:when>
	               			<c:otherwise>
	               				<input type="checkbox" id="wcRatingCheckBox" name="wcRatingCheckBox" cssStyle="margin:0; padding:0;" value="true" /><span style="display:block; position:absolute; right:-2px; top:5px;">ServiceLive&nbsp;Verified</span>
	               			</c:otherwise>
	             </c:choose>
			</div>
		</p>
		
		<!-- Jira#10809 Additional Insurance -->
		<p id="additionalIns">
			<strong>Additional Insurance </strong>
			<br/>
			<div id="divAddnlIns" style="font-size: 8px; font-weight:bold; width:100%; position:relative;">
			
				<div id="selectAddnIns" class="flpicked"
					style="display: block; width: 100px; float: left; margin-left: 2px; margin-right: 5px" onclick="toggleOptions()"  >
					<c:choose>
						<c:when test="${fn:length(providerSearchDto.selectedAddnInsurances) > 0}">
							<label id="defaultValMarket" style="display: block; width: 100px; float: left; margin-left: 2px; margin-right: 5px">${fn:length(providerSearchDto.selectedAddnInsurances)}
								selected</label>
						</c:when>
						<c:otherwise>
							<label id="defaultValMarket" style="display: block; width: 100px; float: left; margin-left: 2px; margin-right: 5px">-Select-</label>
						</c:otherwise>
					</c:choose>
				</div>
				<div id="selectAddnInsOptions" class="flselect-options"
					style="display: none;font-size: 9px;" onmouseout="">
					<c:if test="${null != additionalInsuranceList}">
						<c:forEach var="addlists" items="${additionalInsuranceList}" varStatus="i">
							<c:set var="val1" value="0"></c:set>
							<c:forEach var="addnIns" items="${providerSearchDto.selectedAddnInsurances}">
								<c:if test="${addlists.id == addnIns}">
									<div style="clear: left; padding-top: 5px;">
										<div style="float: left;">
											<input type="checkbox" checked="checked"
												name="selectedAddnInsurances[${i.count}]"
												value="${addlists.id}" id="addchk_${i.count}"
												class="addnInsCheckbox"
												 />
										</div>
										<div style="padding-left: 16px; word-wrap: break-word;">${addlists.descr}
										</div>
									</div>
									<c:set var="val1" value="1"></c:set>
								</c:if>
							</c:forEach>
							<c:if test="${val1 == 0}">
								<div style="clear: left; padding-top: 5px;">
									<div style="float: left;">
										<input type="checkbox"
											name="selectedAddnInsurances[${i.count}]"
											value="${addlists.id}" id="addchk_${i.count}"
											class="addnInsCheckbox"
											 />
									</div>
									<div style="padding-left: 16px; word-wrap: break-word;">
										${addlists.descr}</div>
								</div>
							</c:if>
						</c:forEach>
					</c:if>
				</div>
			
				<c:choose>
	            			<c:when test="${providerSearchDto.addRatingCheckBox == true}"> 
			                    <input type="checkbox" id="addRatingCheckBox" name="addRatingCheckBox" style="margin:0; padding:0;" value="true" checked="checked"/><span style="display:block; position:absolute; right:-2px; top:5px;">ServiceLive&nbsp;Verified</span>
	               			</c:when>
	               			<c:otherwise>
	               				<input type="checkbox" id="addRatingCheckBox" name="addRatingCheckBox" style="margin:0; padding:0;" value="true" /><span style="display:block; position:absolute; right:-2px; top:5px;">ServiceLive&nbsp;Verified</span>
	               			</c:otherwise>
	             </c:choose>
			</div>
			<br/>
		</p>

		<p id="minimumRating" style="display: block;">
			<strong>Minimum Rating</strong>
			<br/>
			
			<select style="width:95%;background-color: #ffffff;" size="1" name="rating">
			<option value="">Select One</option> 
			    <c:forEach var="lookupVO" items="${ratingList}" >            		
                     <c:choose>
            			<c:when test="${lookupVO.dId == providerSearchDto.rating}"> 
		                    <option selected value="${lookupVO.id}">
		                         ${lookupVO.descr}
		                  	</option>
               			</c:when>
               			<c:otherwise>
               				 <option value="${lookupVO.dId}">
		                         ${lookupVO.descr}
		                  	</option>
               			</c:otherwise>
              		</c:choose>                               
	             </c:forEach>
			</select>
			
		</p>
		<!-- SLIDER -->
<div id="minScore" style="display: none;">
<p>
<label style="font-weight: bold;">Minimum Score: <span id="score">perfScore</span></label>
</p>
<div id="perfSlider"></div>
</div>
<br>
<!-- SLIDER -->
			
		<c:if test="${fn:length(spnetworkList) > 0}" >
			<p>
				<strong>My Networks (SPNs)</strong>
				<br/>
				
				<select style="width:95%;background-color: #ffffff;" size="1" name="spn" id="spn" onchange="fnFetchRoutingPriorities();">
				<option value="-1">Select One</option>   
				    <c:forEach var="lookupVO" items="${spnetworkList}" >            		
	                     <c:choose>
	            			<c:when test="${lookupVO.spnId == providerSearchDto.spn}"> 
			                    <option selected value="${lookupVO.spnId}" id="${lookupVO.performanceCriteriaLevel}_${lookupVO.routingPriorityStatus}">
			                         ${lookupVO.spnName}
			                  	</option>
	               			</c:when>
	               			<c:otherwise>
	               				 <option value="${lookupVO.spnId}" id="${lookupVO.performanceCriteriaLevel}_${lookupVO.routingPriorityStatus}">
			                         ${lookupVO.spnName}
			                  	</option>
	               			</c:otherwise>
	              		</c:choose>                               
		             </c:forEach>
				</select>
				
			</p>
			<br>
			<!-- Routing Priorites display -->
			
			<div id="loadingDiv" style="display: none;"></div> 
		<fieldset class="routingPrioritySelector" style="display: none;" id="routingPrioritySelector">
			<legend style="margin-left:5px;border-color: #CCCCCC"><input type="checkbox" class="routingPriorityCheckBox" name="routingPriority" id="routingPriority"><strong> Use Priority Routing</strong></legend>
		<table cellspacing="5">
		<tr>
			<td rowspan="2">
					Priority
			</td>
			<td rowspan="2">
					<span id="critLevel"></span>
			</td>
			<td colspan="3" style="border-bottom: 0;">
					<b>Early Access Time</b>
			</td>
		</tr>
		<tr>
			<td style="border-right: 0;">
				Days
			</td>
			<td style="border-right: 0;">
				Hours
			</td>
			<td>
				Minutes
			</td>
		</tr>
		<tr>
			<td style="text-align: center;" id="priority">
			</td>
			<td id="count">
			</td>
			<td style="border-right: 0;text-align: center;" id="days">
			</td>
			<td style="border-right: 0;text-align: center;" id="hours">
			</td>
			<td style="border-right: 0;text-align: center;" id="mins">
			</td>
			</tr>	
		</table>
	</fieldset>
	
			<div id="performanceLevelsDiv" style="display:none">		
				<p>
					<strong>Group</strong> (for selected network)
					<br/>
					<c:forEach items="${perfLevelCheckboxes}" var="perfLevel">
						<c:choose>
						<c:when test="${perfLevel.selected}">
							<input type="checkbox" checked="checked" id="perfLevel_${perfLevel.value}" name="perfLevel_${perfLevel.value}" class="perfLevelClass"/>${perfLevel.label}<br>
						</c:when>
						<c:otherwise>
							<input type="checkbox" id="perfLevel_${perfLevel.value}" name="perfLevel_${perfLevel.value}" class="perfLevelClass" />${perfLevel.label}<br>						
						</c:otherwise>
						</c:choose>				
					</c:forEach>
				</p>		
				</br>
			</div>
		</c:if>
		<p>
			<%-- SLT-2546: removal of distance filter
			<div style="float:left;text-align:left;">
				<strong>Distance (miles)</strong>
			</div>			
			<div style="clear:both;"></div>
			<select style="width:95%;background-color: #ffffff;" size="1" name="distance">
				<option value="0">Select One</option>  
			    <c:forEach var="lookupVO" items="${distanceList}" >            		
                     <c:choose>
            			<c:when test="${lookupVO.id == providerSearchDto.distance}"> 
		                    <option selected value="${lookupVO.id}">
		                         ${lookupVO.descr}
		                  	</option>
               			</c:when>
               			<c:otherwise>
               				 <option value="${lookupVO.id}">
		                         ${lookupVO.descr}
		                  	</option>
               			</c:otherwise>
              		</c:choose>                              
	             </c:forEach>
			</select> --%>
		<p>		
			<strong>Credentials</strong>
			<br/>
			<select style="width:95%" id="selectedCredential" name="selectedCredential"
			onchange="javascript:fnCredentialCategory('selectedCredential','refineProviderList','${contextPath}','refineProviderList_credentialCategory')">
				<option value="">Select One</option>                                     
		             <c:forEach var="lookupVO" items="${credentails}" >
			             <c:choose>
		            			<c:when test="${lookupVO.id == providerSearchDto.selectedCredential}"> 
				                    <option selected value="${lookupVO.id}">
				                         ${lookupVO.descr}
				                  	</option>
		               			</c:when>
		               			<c:otherwise>
		               				 <option value="${lookupVO.id}">
				                         ${lookupVO.descr}
				                  	</option>
		               			</c:otherwise>
	               			</c:choose>
		             </c:forEach>
			</select>
			<div id="specifyCredentialDiv" style="display:none">
				<strong>Specify Credential</strong>
				<br/>			
				<select style="width:95%;background-color: #ffffff;"  id ="credentialCategory" name="credentialCategory">
					<option value="0">Select One</option>
					<c:forEach var="lookupVO" items="${credentailCategoryList}" >            		
	                     <c:choose>
	            			<c:when test="${lookupVO.id == providerSearchDto.credentialCategory}"> 
			                    <option selected value="${lookupVO.id}">
			                         ${lookupVO.descr}
			                  	</option>
	               			</c:when>
	               			<c:otherwise>
	               				 <option value="${lookupVO.id}">
			                         ${lookupVO.descr}
			                  	</option>
	               			</c:otherwise>
	              		</c:choose>                               
		             </c:forEach>    
				</select>
			</div>
		</p>
				
		<p>
		
			<strong>Language Spoken</strong>
			<br/>
			<select style="width:95%;background-color: #ffffff;"  id="selectedLang" name="selectedLang"  >
				<option value="0">Select One</option>
		        <c:forEach var="lookupVO" items="${languages}" varStatus="index">
                     <c:choose>
            			<c:when test="${lookupVO.id == providerSearchDto.selectedLang}">
		                    <option selected value="${lookupVO.id}">${lookupVO.descr}</option>
               			</c:when>
               			<c:otherwise>
               				 <option value="${lookupVO.id}">${lookupVO.descr}</option>
               			</c:otherwise>
              		</c:choose>
	             </c:forEach>
			</select>
 	 	</p>
	<input type="hidden" id="checkedProvidersList" name="checkedProvidersList" />	
	<input type="hidden" id="performanceScore" name="performanceScore" value="${performanceScore}"/>
	<input type="hidden" id="routingPriorityApplied" name="routingPriorityApplied" />
	<input type="hidden" id="perfCriteriaLevel" name="perfCriteriaLevel" />
	<input class="button action" type="button" id="submitButton" value="Apply Filter" style="margin-top:0; font-size:11px;"
	onclick="fnSubmitSearchCriteria('${contextPath}')" />
	<input type="hidden" id="selectedCredInSession" value="${credentialSelected}" />
	<input type="hidden" id="selectedCredCat" value="${providerSearchDto.credentialCategory}" />
	<input type="hidden" id="routingPriorityChecked" name="routingPriorityChecked" value="${providerSearchDto.routingPriorityChecked}">
		
		</form>
		
	</div>
<br/>
<div dojoType="dijit.layout.ContentPane"
		<c:choose>
		<c:when test="${null != groupOrderId && '' != groupOrderId}">
			href="soDetailsQuickLinks.action?action=edit&groupId=${groupOrderId}" class="colRight255 clearfix">
		</c:when>
		<c:otherwise>
			href="soDetailsQuickLinks.action?action=edit&soId=${soId}" class="colRight255 clearfix">
		</c:otherwise>
		</c:choose>
</div>	
		
	
</div>	
	<div class="clearfix">
	</div>
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script> 
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.slider.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqueryui/jquery-ui.min.js"></script>
<script type="text/javascript">	

	/*To show the select/drop down on click*/
	function toggleOptions() {
		jQuery( "#selectAddnInsOptions" ).toggle();		
	
	}
		
	function fnFetchRoutingPriorities(){
			//reset previosly selected filters if any
			jQuery("#routingPrioritySelector").hide();
			jQuery("#minimumRating").show();
			jQuery("#minScore").hide();
			jQuery("#priority").html("");
			jQuery("#count").html("");
			jQuery("#days").html("");
			jQuery("#hours").html("");
			jQuery("#mins").html("");
			var id = jQuery("#spn option:selected").attr("id");
			var index = id.indexOf('_');
			var perfCriteriaLevel = id.substring(0,index);
			var status = id.substring(index+1);
			jQuery('#perfCriteriaLevel').val(perfCriteriaLevel); 
			if(perfCriteriaLevel=="FIRM"){
				jQuery('#critLevel').html("Firms");
			}else{
				jQuery('#critLevel').html("Providers");
			}
			 
			var spnId = jQuery('#spn').val();
			//to fetch routing priorities configured for spn
			if(spnId!="-1" && status == 'ACTIVE'){
				jQuery("#loadingDiv").css("display","block");
				jQuery("#loadingDiv").html('<div style="padding-left: 0px;" align="center"><img src="${staticContextPath}/images/spinner_small.gif\"/></div>');
				jQuery.ajax({
	        	url: 'fetchRoutingPriorities.action?spnId='+spnId,
	        	type: "POST",
				dataType : "json",
				success: function(data) {
					if(data.routingPrioritiesList != ""){
						var count = 0;
						jQuery.each(data.routingPrioritiesList, function(index,value){
							var v = jQuery("#priority").html();
							if(v.indexOf('4')<0){
							jQuery("#priority").append(value.tierId+"<br />");
						if(index == 0){
							jQuery("#count").append("Top "+value.noOfMembers+"<br />");
						}else if(value.tierId == 4){
							jQuery("#count").append("Remaining<br />");
						}else{
							jQuery("#count").append("Next "+value.noOfMembers+"<br />");
						}
						if(value.tierWaitDays==0){
							jQuery("#days").append("--<br />");
						}else if(value.tierWaitDays>1){
							jQuery("#days").append(value.tierWaitDays+"days<br />");
						}else{
							jQuery("#days").append(value.tierWaitDays+"day<br />");
						}
						if(value.tierWaitHours==0){
							jQuery("#hours").append("--<br />");
						}else if(value.tierWaitHours>1){
							jQuery("#hours").append(value.tierWaitHours+"hrs.<br />");
						}else{
							jQuery("#hours").append(value.tierWaitHours+"hr.<br />");
						}
						if(value.tierWaitMinutes==0){
							jQuery("#mins").append("--<br />");
						}else{
							jQuery("#mins").append(value.tierWaitMinutes+"min.<br />");

						}
							}
				  	});
						jQuery("#routingPrioritySelector").show();
						jQuery("#minimumRating").hide();
						jQuery("#routingPriority").attr("checked",true);
						jQuery("#routingPriority").trigger('click');
						jQuery("#routingPriority").attr("checked",true);
					}
					jQuery("#loadingDiv").css("display","none");
				},
				error: function(request, status, err) {
		            if(status == "timeout") {
		            	location.reload();
		            }
		        }
		 	}); 
			}else{
				jQuery("#routingPriorityApplied").val(false);
			}
			if(jQuery('#spn').val() > 0)
			{
				jQuery('#performanceLevelsDiv').show();
			}
			else
			{
				jQuery('#performanceLevelsDiv').hide();
			}
			
		}

	jQuery(document).ready(function($)
	{
		jQuery(document).click(function(e){
		var click=jQuery(e.target);
		if(!(click.parents().hasClass("flpicked") || click.parents().hasClass("flselect-options") || click.hasClass("flselect-options") || click.hasClass("flpicked")))
		{
			jQuery("#selectAddnInsOptions").hide();	
		}
		
		});
		var initStatus = $('#addchk_1').attr('checked');
		if(initStatus==true){
			$("#addchk_2").attr("disabled", true);
			$("#addchk_3").attr("disabled", true);
			$("#addchk_4").attr("disabled", true);
			$("#addchk_5").attr("disabled", true);
			$("#addchk_6").attr("disabled", true);
			$("#addchk_7").attr("disabled", true);
			$("#addchk_8").attr("disabled", true);
			$("#addchk_9").attr("disabled", true);
			$("#addchk_10").attr("disabled", true);
			$('#addRatingCheckBox').attr("checked",false);
			$('#addRatingCheckBox').attr("disabled",true);
		}
		else{
			$("#addchk_2").attr("disabled", false);
			$("#addchk_3").attr("disabled", false);
			$("#addchk_4").attr("disabled", false);
			$("#addchk_5").attr("disabled", false);
			$("#addchk_6").attr("disabled", false);
			$("#addchk_7").attr("disabled", false);
			$("#addchk_8").attr("disabled", false);
			$("#addchk_9").attr("disabled", false);
			$("#addchk_10").attr("disabled", false);
			$('#addRatingCheckBox').attr("disabled",false);
		}
		//alert($("#routingPriorityChecked").val());
		var routingPriorityChecked = $("#routingPriorityChecked").val();
		var spnId = jQuery('#spn').val();
		if(spnId != -1 && routingPriorityChecked != "false"){
			fnFetchRoutingPriorities();
		}
		
		jQuery.noConflict();

			//display slider when routing priority checkbox is checked.
			$('#routingPriority').click( function(){			
				var checked = $('#routingPriority').is(":checked");
				if(checked==true){
					$("#minScore").show();
					$("#perfSlider").slider({
						value:('${performanceScore}' || 0),
						min: 0,
						max: 100,
						step: 10
						});
						if('${performanceScore}'!=""){
							var score = '${performanceScore}';
							$("#performanceScore").val(score);
							$("#score").html(score+" %");
						}else{
							$("#score").html($("#perfSlider").slider("value")+" %");
							$("#performanceScore").val($("#perfSlider").slider("value"));
						}
						$("#routingPriorityApplied").val(true);
						$("#minimumRating").hide();
						$("#routingPriorityChecked").val(true);
				}else{
					$("#performanceScore").val(0);
					$("#routingPriorityApplied").val(false);
					$("#routingPriorityChecked").val(false);
					$("#minimumRating").show();
					$("#routingPrioritySelector").hide();
					$("#minScore").hide();
					//$("#spn").val(-1); 
                   //$("#submitButton").trigger('click');
				}
			});
			
			$('.addnInsCheckbox').click( function(){
				var status = $('#addchk_1').attr('checked');
				$('#defaultValMarket').html("-Select-");
				if(status==true){
					$('#addchk_2').attr('checked', false);
					$("#addchk_3").attr('checked', false);
					$("#addchk_4").attr('checked', false);
					$("#addchk_5").attr('checked', false);
					$("#addchk_6").attr('checked', false);
					$("#addchk_7").attr('checked', false);
					$("#addchk_8").attr('checked', false);
					$("#addchk_9").attr('checked', false);
					$("#addchk_10").attr('checked', false);
					$('#addchk_2').attr("disabled", true);
					$("#addchk_3").attr("disabled", true);
					$("#addchk_4").attr("disabled", true);
					$("#addchk_5").attr("disabled", true);
					$("#addchk_6").attr("disabled", true);
					$("#addchk_7").attr("disabled", true);
					$("#addchk_8").attr("disabled", true);
					$("#addchk_9").attr("disabled", true);
					$("#addchk_10").attr("disabled", true);
					$('#addRatingCheckBox').attr("checked",false);
					$('#addRatingCheckBox').attr("disabled",true);
				}
				else{
					$('#addchk_2').attr("disabled", false);
					$("#addchk_3").attr("disabled", false);
					$("#addchk_4").attr("disabled", false);
					$("#addchk_5").attr("disabled", false);
					$("#addchk_6").attr("disabled", false);
					$("#addchk_7").attr("disabled", false);
					$("#addchk_8").attr("disabled", false);
					$("#addchk_9").attr("disabled", false);
					$("#addchk_10").attr("disabled", false);
					$('#addRatingCheckBox').attr("disabled",false);
				}
					
				});
			
			//to set the value selected using slider
			$("#perfSlider").mousemove( function(){
					$("#score").html($("#perfSlider").slider("value")+" %");
		     		$("#performanceScore").val($("#perfSlider").slider("value"));
	     	});
			
		var glId = $('#generalLiabilityRating').val();
		var vlId = jQuery('#vehicleLiabilityRating').val();
		var wcId = jQuery('#workersCompensationRating').val();
		
		if(glId == '1'){
			$('#glRatingCheckBox').attr('disabled', 'disabled');
		}
		if(vlId == '8'){
			$('#vlRatingCheckBox').attr('disabled', 'disabled');
		}
		if(wcId == '14'){
			$('#wcRatingCheckBox').attr('disabled', 'disabled');
		}
		if($('#spn').val() != '-1' && $("#spn").val() > 0)
		{			
			$('#performanceLevelsDiv').show();
		}
		else
		{		
			$('#performanceLevelsDiv').hide();
		}

		jQuery('#generalLiabilityRating').change( function()
		{			
			if(jQuery('#generalLiabilityRating').val() == 1)
			{
				jQuery('#glRatingCheckBox').attr('disabled', 'disabled');
			}
			else
			{
				jQuery('#glRatingCheckBox').removeAttr('disabled');
			}
			
		});
		
		jQuery('#vehicleLiabilityRating').change( function()
		{			
			if(jQuery('#vehicleLiabilityRating').val() == 8)
			{
				jQuery('#vlRatingCheckBox').attr('disabled', 'disabled');
			}
			else
			{
				jQuery('#vlRatingCheckBox').removeAttr('disabled');
			}
			
		});
		
		jQuery('#workersCompensationRating').change( function()
		{			
			if(jQuery('#workersCompensationRating').val() == 14)
			{
				jQuery('#wcRatingCheckBox').attr('disabled', 'disabled');
			}
			else
			{
				jQuery('#wcRatingCheckBox').removeAttr('disabled');
			}
			
		});
		
	
	});	
</script>
</body>
</html>