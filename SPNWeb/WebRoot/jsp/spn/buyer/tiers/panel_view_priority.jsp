<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>	
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<head>
	
	<style type="text/css">
		.visualize .visualize-title {
    		color: #222222;
    		left: 150px;
    		font-size: 12px;
    	}
    	
    	.visualize {
		    background: none;
		    border: none;
		    margin: 60px 0 0 -30px;
		    margin-top: 320px;
		}
		
		.visualize canvas {
		    background: none;
		    border: none;
		    margin-left: -60px;
		}
		
		.visualize ul.visualize-key {
		     background: none;
			 left: 215px;
			 padding: 0;
			 position: relative;
			 width: 50%;		   
		}
		
		.visualize ul.visualize-key .visualize-key-label {
		    color: #000000;
		    font-size: 10px;
		}
		
		th, td {
		    padding: 0 0 4px;
		}
		
	 	#tierTab a:after{
	 		border:none;
	 	}
	 	#tierTab a:before{
	 		border:none;
	 	}
		
	</style>
	
	<script type="text/javascript">
		jQuery(document).ready(function($) {
		
			$('#perfTab').click(function(){
				$('#routingViewDiv').hide();
				$('#perfViewDiv').show();
				jQuery("#tierTab").removeClass('active');
				jQuery("#perfTab").addClass('active');
			});
			
			$('.perfTab1').click(function(){
				visualize();
			});
			
			$('#tierTab').click(function(){
				$('#perfViewDiv').hide();
				$('#routingViewDiv').show();
				jQuery("#perfTab").removeClass('active');
				jQuery("#tierTab").addClass('active');
			});
			
			$('body').unbind('click').click(function() {
				jQuery('#successDiv').hide();
			});	
			
			
			$('#editPriority').click(function(){
				$("#editConfirm").jqm({modal : true});
				$("#editConfirm").jqmShow();
				
			});
			
			jQuery("#viewCoverage").click( function(){
				if('View Coverage' == jQuery("#viewCoverage").attr('value')){
					jQuery("#viewCoverage").attr('value','Hide Coverage');
				}
				else{
					jQuery("#viewCoverage").attr('value','View Coverage');
				}
				jQuery("#coverageDiv").toggle();
				var coverageInd = jQuery("#coverageInd").val();
				if(0 == coverageInd){
					var seconds = new Date().getTime() / 1000;
					$('#coverageDiv').html('<center><img src="${staticContextPath}/images/loading.gif" width="500px" height="200px"/></center>');
					jQuery("#coverageDiv").load("spnReleaseTiers_buttonAddTierAjax.action?coverage=true&randomnum="+seconds,function(){
						jQuery("#coverageInd").val("1");
						jQuery('.dataTables_length').hide();
						jQuery('#tabPHdr2').trigger("click");
						jQuery('#tabPHdr2').trigger("click");
						jQuery('#tabFHdr2').trigger("click");
						jQuery('#tabFHdr2').trigger("click");
					});
				}
				else{
					jQuery('.dataTables_length').hide();
					jQuery('#tabPHdr2').trigger("click");
					jQuery('#tabPHdr2').trigger("click");
					jQuery('#tabFHdr2').trigger("click");
					jQuery('#tabFHdr2').trigger("click");
				}
			});	
			
			jQuery("#overflowInfoLink").hover(function(){
				jQuery("#overflowInfo").show();
			});		
			
			jQuery(".icon-remove").click(function(){
				jQuery("#overflowInfo").hide();
			});		
			
			<c:forEach items="${model.routingDTO.criteriaDTO}" var="criteria">
				<c:forEach items="${model.routingDTO.performanceCriteria}" var="spnCriteria">
					<c:if test="${criteria.criteriaId == spnCriteria.criteriaId}">
						$("#visualize tbody").append("<tr><th>${criteria.criteriaName}</th><td>1</td></tr>");	
					</c:if>
				</c:forEach>
			</c:forEach>	
			
			$("#perfChartDiv").show();	
			//visualize();
	
		});
		
		//to show the description for criteria
		function showDescr(count){
			jQuery('#right'+count).hide();
			jQuery('#down'+count).show();
			jQuery('#descr'+count).show();			
		}
		
		function hideDescr(count){
			jQuery('#down'+count).hide();
			jQuery('#right'+count).show();
			jQuery('#descr'+count).hide();	
		}
		
		//for visualise
		function visualize(){
			$('.visualize').remove();	
			$("#visualize")
				.visualize({type: 'pie', pieMargin: '10', height: '180px', width: '400px', 
							colors: ['#81DAF5','#58D3F7','#2ECCFA','#00BFFF','#01A9DB','#0489B1','#086A87','#0B4C5F','#0B3861']})
				.trigger('visualizeRefresh');	
			$('.visualize-label').css('margin-left', '-80px');
			if(1 == $("span.visualize-label").length){
				$('.visualize-label').css('margin-bottom', '50px');
			}
			if($('.visualize-label').css('margin-right') != 'undefined'){ 			
				$('.visualize-label').css('margin-right', '50px');	
			}	
	
		}
		
		
	</script>
	
</head>

<body>
	
	<div class="tab-wrap">
	    <div id="perfTab" class="perfTab1 tab">
	        <a href="javascript:void(0)">Performance Scores</a>
	    </div>
	    <div id="tierTab" class="tab">
	        <a href="javascript:void(0)">Routing Tiers</a>
	    </div>
	</div>
	<br><br><br>
	
	<div id="perfViewDiv" style="display:none;">
		<div style="padding: 10px;">
			These criteria determine a <span class="provfirm"></span>'s Performance Score. 
			Each criteria is weighed equally. The Performance Score is a percentile average of the rates of all selected criteria.<br><br>
			
			<b>Criteria :</b>
			<b id="dataScope"
				title="Calculate these rates by looking only at the orders you've routed to this provider, or by looking at all orders routed to this provider through the ServiceLive marketplace. Statistically, a larger sample size will yield more accurate averages." >
				Data scope :</b>
				
			<hr id="frsthr1" style="width: 60%;margin-top: 5px;">
			
			<div style="height:220px;">
				<div style="float:left">
					<table cellpadding="0" cellspacing="0" border="0" style="table-layout: fixed; width: 50%">
						<c:forEach items="${model.routingDTO.criteriaDTO}" var="criteria" varStatus="status">
							<c:forEach items="${model.routingDTO.performanceCriteria}" var="spnCriteria">
								<c:if test="${criteria.criteriaId == spnCriteria.criteriaId}">
									<tr>
										<td style="word-wrap: break-word;width: 60%">
											<span id="critName${status.count}">${criteria.criteriaName }</span> 							
											<span style="color: #0066FF;font-size: 10px;padding-left:5px;">
												<span  id="right${status.count}" style="cursor: pointer;" onclick="showDescr('${status.count}');">
													details 
													<i style="font-size: 14px;" class="icon-caret-right"></i>
												</span>
												<span id="down${status.count}" style="cursor: pointer;display:none;" onclick="hideDescr('${status.count}');">
													details 
													<i style="font-size: 14px;" class="icon-caret-down"></i>
												</span>
											</span>
										</td>
										<td>
											<c:if test="${'SINGLE' == spnCriteria.criteriaScope }">
												Only my orders
											</c:if>
											<c:if test="${'ALL' == spnCriteria.criteriaScope }">
												All orders from any buyer
											</c:if>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<div id="descr${status.count}" style="color: #777777;padding-left: 20px;font-size: 11px;display: none;width:50%">
												${criteria.description }
											</div>
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</c:forEach>
					</table>
					<br>
					
				</div>
				
				<div id="perfChartDiv" class="perfChart" style="height:0px;margin-top: -350px">
					<!-- visualise -->
					<table id="visualize" style="display:none;">
						<caption><b>Performance Score Calculation</b></caption>
						<thead>
							<tr><td></td><th></th></tr>
						</thead>
						<tbody></tbody>	
					</table>				
					<!-- visualise -->
				</div>
			</div>
				
			<hr id="frsthr2" style="width: 60%;height: 1px" />
			
			<span style="position: absolute;margin-top: -10px">
				<b>Time Frame : </b>
					<c:if test="${model.routingDTO.spnHdr.criteriaTimeframe == '90DAYS'}">
						Last 90 days
					</c:if>
					<c:if test="${model.routingDTO.spnHdr.criteriaTimeframe == 'LIFETIME'}">
						Lifetime
					</c:if>
						
				
				<span style="color: #777777;margin-left: 100px;font-size: 11px;">
					Performance data last updated : <fmt:formatDate value="${model.routingDTO.modifiedDate}" pattern="hh:mm a MM/dd/yy" />
				</span>
			</span>
			
			<br>
			
		</div>
	</div>
	
	<div id="routingViewDiv" style="display:none;">
		<div style="padding: 10px;">
		
		In addition to applying the existing routing logic (which considers zip code, Provider skills, miles, etc.), the system 
		will also identify the highest rated Providers available in your network and route the orders to them as defined below.  
		If everyone in one tier rejects the order, it will be immediately routed to the subsequent tier.
				
			<br><br>
			<hr>
			
			<b style="color: #0099FF;">1.</b> First, route orders to the top 
			<b>${model.routingDTO.spnTiers[0].noOfMembers }</b>
			<span class="provfirm"></span>s in the service area and allow 
			<c:choose>
				<c:when test="${model.routingDTO.spnTiers[0].minutes != 0 }">
					<b>${model.routingDTO.spnTiers[0].minutes} minutes</b>
				</c:when>
				<c:when test="${model.routingDTO.spnTiers[0].hours != 0 }">
					<b>${model.routingDTO.spnTiers[0].hours} hours</b>
				</c:when>
				<c:when test="${model.routingDTO.spnTiers[0].days != 0 }">
					<b>${model.routingDTO.spnTiers[0].days} days</b>
				</c:when>
				<c:otherwise>
					<b>0 minutes</b>
				</c:otherwise>
			</c:choose>			
			
			<span id="span1">before routing to the next group.</span>
			
			<br><br>
			<hr>
			
			<b style="color: #0099FF;">2.</b> If no one accepts, route to the next 
			<b>${model.routingDTO.spnTiers[1].noOfMembers }</b>
			highest rated <span class="provfirm"></span>s available, and allow 
			<c:choose>
				<c:when test="${model.routingDTO.spnTiers[1].minutes != 0 }">
					<b>${model.routingDTO.spnTiers[1].minutes} minutes</b>
				</c:when>
				<c:when test="${model.routingDTO.spnTiers[1].hours != 0 }">
					<b>${model.routingDTO.spnTiers[1].hours} hours</b>
				</c:when>
				<c:when test="${model.routingDTO.spnTiers[1].days != 0 }">
					<b>${model.routingDTO.spnTiers[1].days} days</b>
				</c:when>
				<c:otherwise>
					<b>0 minutes</b>
				</c:otherwise>
			</c:choose>
			<span id="span2">before routing further.</span>
			
			<br><br>				
			<hr>
			
			<b style="color: #0099FF;">3.</b> Then if none still have accepted, route to the next 
			<b>${model.routingDTO.spnTiers[2].noOfMembers }</b>
			rated <span class="provfirm"></span>s available and wait 
			<c:choose>
				<c:when test="${model.routingDTO.spnTiers[2].minutes != 0 }">
					<b>${model.routingDTO.spnTiers[2].minutes} minutes</b>
				</c:when>
				<c:when test="${model.routingDTO.spnTiers[2].hours != 0 }">
					<b>${model.routingDTO.spnTiers[2].hours} hours</b>
				</c:when>
				<c:when test="${model.routingDTO.spnTiers[2].days != 0 }">
					<b>${model.routingDTO.spnTiers[2].days} days</b>
				</c:when>
				<c:otherwise>
					<b>0 minutes</b>
				</c:otherwise>
			</c:choose>
			<span id="span3">before routing further.</span>
			
			<br><br>				
			<hr>
			<b style="color: #0099FF;">4.</b> Finally, in the unlikely event that the order has not been accepted, 
				route to all remaining available SPN members.
			<br><br>
				
			<div style="text-align: left;" class="marketplaceOverflowContainer">
				<label style="vertical-align: middle;margin-left:10px;font-weight: normal;">
					<b>Marketplace Overflow</b>
					<c:choose>
						<c:when test="${model.routingDTO.spnHdr.marketPlaceOverFlow == 'true'}">
							Yes
						</c:when>
						<c:otherwise>
							No
						</c:otherwise>
					</c:choose>
					<span id="overflowInfoLink" style="font-size: 11px;cursor: pointer;">
						<img src="${staticContextPath}/images/icons/info.gif" width="16"
							height="16" border="0" style="vertical-align: absmiddle;cursor: pointer;" />
						&nbsp;What is Marketplace Overflow?
					</span>
				</label>
		
			</div>
			
			<div id="overflowInfo" class="overflowInfo" style="margin-left:26%">
				<div class="overflow">
					<b>Marketplace Overflow</b><i class="icon-remove" style="float: right; cursor: pointer;"></i><br>
				</div>
				<i style="margin-left:-20px;font-size:55px;position:absolute;color:#CCCCCC" class="icon-caret-left"></i>
				<div style="font-size: 11px; padding: 5px">
					If no SPN member accepts (or if all reject) an order, you can opt to route orders to the entire marketplace. 
					This is helpful when your network has lower market coverage.
				</div>
			</div>
			
			<br><br>
			<input id="viewCoverage" type="button" value="View Coverage" class="button action"
				style="height: 30px;text-transform: none;font-size: 13px;width: 130px;margin-left: 85%;"/><br>
				
			<div  id="coverageDiv" style="display: none;" ></div>
		</div>
	</div>
	
	<input type="hidden" id="routingAction" value="view"/>
	<input type="hidden" id="spnIdHidden" name="model.routingDTO.spnHdr.spnId" value="${model.routingDTO.spnHdr.spnId}"/>
	<input type="hidden" id="priorityStatus" name="model.routingDTO.spnHdr.routingPriorityStatus" value="${model.routingDTO.spnHdr.routingPriorityStatus}"/>
	<input type="hidden" id="criteriaCount" name="model.routingDTO.criteriaCount" value="${model.routingDTO.criteriaCount}"/>
	<input type="hidden" id="routingLevel" value="${model.routingDTO.spnHdr.criteriaLevel}"/>
	
	<input type="hidden" id="stateLabel" value="All States"></input>
	<input type="hidden" id="marketLabel" value="All Markets"></input>
	<input type="hidden" id="applyFilterInd" value="0"></input>
	<input type="hidden" id="resetInd" value="false"></input>
	<input type="hidden" id="marketInd" value="0"></input>
	<input type="hidden" id="coverageInd" value='0'></input>
	
	<div id="editConfirm" class="jqmWindow" style="display: none;margin-left: -250px;width: 450px;" >
	
		<img src="${staticContextPath}/images/s_icons/lightbulb.png" class="icon-lightbulb"
			style="position: absolute; font-size: 20px; margin-left: 20px; margin-top: 10px;background-color: #FFFF33;">	
			
		<div style="margin-left: 10%; padding: 10px;">
			<b>Please Note!</b>
			You'll need to complete both steps &amp; click <b>Save</b><br>
			on the <b>Routing Priorities</b> tab for changes to take effect.<br><br>
		</div>
		
		<div class="footerDiv">

			<input type="button" id="editOK" value="OK" class="edit button action" onclick="editRoutingPriority()"
			 	style="text-transform: none;font-size: 12px;">
				
		</div>
		
	</div>
	
</body>