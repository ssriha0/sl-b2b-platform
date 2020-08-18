<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<link href="${staticContextPath}/css/ui-15642.css" rel="stylesheet"/>
<style>

.histDetailsDiv{
 border: 3px solid #A8A8A8!important; 
  border-color:#A8A8A8 #A8A8A8 #A8A8A8 #A8A8A8!important;
 -webkit-border-radius: 6px 6px 6px 6px;
	   -moz-border-radius: 6px 6px 6px 6px;
	        border-radius: 6px 6px 6px 6px;
width:230px;
height: auto;
display:none; 
background: #FFFFFF;
z-index: 99999; 
margin-left:12%;
margin-top: -4%;
position: absolute;
font-family: Verdana; font-size: 10px; font-weight: normal; font-style: normal; text-decoration: none;
}	
.arrowAddNoteDetails{
    			    border-color: transparent #A8A8A8 transparent transparent !important;
 					border: 10px solid; 
				    height: 0;
				    width:0;
				    left: 80%;
				    margin-top: -1.5%;
				    margin-left:-18% !important;
				    z-index: 99999; 
				    display: none;	
				    position: absolute;			    
		}
a:hover,a:active,a:link,a:visited{color:#00a0d2;text-decoration: none;}

</style>
<script>
function showHistory(e,count,type,spnId) {
	$(".histDetailsDiv").css("display","none");
	$(".arrowAddNoteDetails").css("display","none");
	var lastId = $("#countD_"+spnId).attr("value");
	if(lastId!=""&& lastId!='undefine'){
		$("#expDetails"+type+"_"+lastId).css("display","none");
		$("#arrow"+type+"_"+lastId).css("display","none");
	}
	var click = $("#exp"+type+"_"+spnId+"_"+count);
	var x = e.pageX;
	var y = e.pageY;
	if (jQuery.browser.mozilla)	{
		var offs = click.position();
		try{
			x= offs.left;
			y = offs.top;
		}catch (e) {
			x = e.offsetX;
			y=e.offsetY;
		} 
	}
	if (jQuery.browser.msie){
		var offs = click.position();
		try{
			x= offs.left+(click.width());
			x1 = click.width();
			y = click.height();
		}catch (e) {
			x = e.offsetX;
			y = e.offsetY;
		}
	}
	$("#countD_"+spnId).attr("value",spnId+"_"+count);

		
	
/*	$("#expDetails_"+count).css("height", "auto");
/* 	$("#expDetails_"+count+" .arrowAddNote").css("border-bottom-color","#A8A8A8");
	$("#expDetails_"+count+" .arrowAddNote").css({"left": "80%"});
	var ht = $("body").height();
	var diff = ht-y;
		$("#expDetails_"+count).css({"top" : ht-462,"marginLeft":"-15px"});
/* 		$("#expDetails_"+count+" .arrowAddNote").css("border-left-color","#A8A8A8");
 */		// Assuming the height of the widget is 253 
/* 		$("#expDetails_"+count+" .arrowAddNote").css({"left": "100%","margin-left":"5px","top":"-55px"}); */
if (jQuery.browser.mozilla){
 	// $("#expDetails"+type+"_"+spnId+"_"+count).css("margin-left",x-675);
	// $("#arrow"+type+"_"+spnId+"_"+count).css("margin-left", x-1080);
	/*$(".histDetailsDiv").css("margin-top",y-620);
	$(".arrowAddNote").css("margin-top",y-620); */
	}
 if (jQuery.browser.msie)
	{	$("#expDetails"+type+"_"+spnId+"_"+count).css("left",x-50);
		$("#expDetails"+type+"_"+spnId+"_"+count).css("margin-top",y-35);
		$("#arrow"+type+"_"+spnId+"_"+count).css("left",x-40);
		//$("#arrow"+type+"_"+spnId+"_"+count).css("margin-left",x-8);
		//$("#arrow"+type+"_"+spnId+"_"+count).css("margin-top","-20px");
		$(".arrowAddNoteDetails").css("border-color","transparent");
		$(".arrowAddNoteDetails").css("border-bottom-color","transparent");
		$(".arrowAddNoteDetails").css("border-top-color","transparent");
		$(".arrowAddNoteDetails").css("border-right-color","#A8A8A8");
	} 

	$("#expDetails"+type+"_"+spnId+"_"+count).show();
	$("#arrow"+type+"_"+spnId+"_"+count).show();
}
function closeHistory(count,type,spnId){
	$("#expDetails"+type+"_"+spnId+"_"+count).css("display","none");
	$("#arrow"+type+"_"+spnId+"_"+count).css("display","none");
}
</script>

<div class="clearfix">
				<input id="countD_${networkHeader.spnId}" name="countD_${networkHeader.spnId}" type="hidden" value=""/>

	<div class="left" style="width:49%;">
		<dl>
			<dt>Name of <abbr title="Select Provider Network">SPN</abbr></dt>
			<dd>${networkHeader.spnName}</dd>
		</dl>
		<dl>
			<dt>Contact Information</dt>
			<dd>${networkHeader.contactName} &lt;<a href="mailto:${networkHeader.contactEmail}">${networkHeader.contactEmail}</a>&gt;</dd>
		</dl>
		<dl>
			<dt class="clearfix"><span>Description</span><span class="open"></span><span class="closed"></span></dt>
			<dd>
			${networkHeader.spnDescription}
			</dd>
		</dl>
		<dl>
			<dt class="clearfix"><span>Special Instructions</span><span class="open"></span><span class="closed"></span></dt>
			<dd>
			${networkHeader.spnInstruction}
			</dd>
		</dl>
	</div>
	<div class="right" style="width:49%;">
		<dl>
			<dt class="clearfix"><span>Services &amp; Skills</span><span class="open"></span><span class="closed"></span></dt>
			<dd>
				<ul>
					<c:if test="${empty approvalItems.viewServicesAndSkills}">
						<li>No services selected.</li>
					</c:if>
					<c:forEach items="${approvalItems.viewServicesAndSkills}" var="aService">
						<li>${aService}</li>
					</c:forEach>
				</ul>
				<%-- <a href="#">View More &raquo;</a> --%>
			</dd>
		</dl>
		<dl>
		<c:forEach items="${selectedApprovalItems.resCredCategoriesWithTypes}" var="resCredCatIter" varStatus="s">
			<c:if test="${resCredCatIter.history!=null && resCredCatIter.exceptionInd==true}"> <c:set var="exceptionAvailable1" value="YES"/></c:if>
		</c:forEach>
		<c:forEach items="${selectedApprovalItems.vendorCredCategoriesWithTypes}" var="vendorCredCatIter" varStatus="s">
			<c:if test="${vendorCredCatIter.history!=null && vendorCredCatIter.exceptionInd==true}"> <c:set var="exceptionAvailable2" value="YES"/></c:if>
		</c:forEach>
			<dt class="clearfix"><span>Approval Criteria &amp; Credentials <c:if test="${exceptionAvailable1!=null||exceptionAvailable2!=null}"> 
			 <span style="color:#00a0d2;font-size:10px;float: right">* Exception Applied</span></c:if>
			 </span><span class="open"></span><span class="closed"></span></dt>
			<dd>
				<ul>
					<li>	
						<strong>Credentials</strong><br />
						Resource: <c:forEach items="${selectedApprovalItems.resCredCategoriesWithTypes}" var="resCredCatIter" varStatus="s">
							<span <c:if test="${resCredCatIter.history!=null && resCredCatIter.exceptionInd==true}">style = "border-bottom: 1px dotted;"   onmouseover="showHistory(event,${s.count},'r',${networkHeader.spnId})" onmouseout="closeHistory(${s.count},'r',${networkHeader.spnId})"</c:if>> ${resCredCatIter.description} 
							</span>
							<c:if test="${resCredCatIter.history!=null && resCredCatIter.exceptionInd==true}">
							<span id="expr_${networkHeader.spnId}_${s.count}" style="color:#00a0d2;font-weight: bolder;" >*</span>
									<div class="arrowAddNoteDetails" id="arrowr_${networkHeader.spnId}_${s.count}"></div>
									<div class="histDetailsDiv" id="expDetailsr_${networkHeader.spnId}_${s.count}">
										<div class="preCallHistoryHead"
											style="height: 20px; width: 100% px; background: #EEEEEE; -moz-border-radius: 8px 8px 0 0;">
											<span class="" style="float:left;"><b>&nbsp;&nbsp;Exception Details</b> </span> <span
												style="font-family: Arial; float: right; font-size: 13px; font-weight: bold; font-style: normal; text-decoration: none; color: rgb(51, 51, 51); cursor: pointer;"
												onclick="closeHistory(${s.count},'r',${networkHeader.spnId})" id="cache1">X</span>

										</div>
										<div
											style="margin-bottom: 5px; padding-left: 8px; border-top: 1px solid #CCCCCC; padding-top: 5px;">
											<div style="margin-top: 3px; padding-top: 5px;">
												<span><b>${resCredCatIter.description}</b></span>
											</div>
											<c:forEach items="${resCredCatIter.history}" var="resCredCatHistIter">
											<div style="">
												<span style="font-weight: bolder;">.&nbsp;</span><span style="word-wrap:break-word;">${resCredCatHistIter}</span>
											</div>
											</c:forEach>
										</div>
									</div>
								</c:if>
							<c:choose>
								<c:when test = "${s.last}"  >
								 &nbsp;
								</c:when>
								<c:otherwise>
								 ,
								</c:otherwise>
							</c:choose>														
							
						</c:forEach>
						<c:if test="${empty selectedApprovalItems.resCredCategoriesWithTypes}">N/A</c:if>
												
						<br />
						<br />
						Company: <c:forEach items="${selectedApprovalItems.vendorCredCategoriesWithTypes}" var="vendorCredCatIter" varStatus="s">
							
							<span <c:if test="${vendorCredCatIter.history!=null && vendorCredCatIter.exceptionInd==true}">style = "border-bottom: 1px dotted;"  onmouseover="showHistory(event,${s.count},'v',${networkHeader.spnId})" onmouseout="closeHistory(${s.count},'v',${networkHeader.spnId})"</c:if>> ${vendorCredCatIter.description} 
							</span>
							<c:if test="${vendorCredCatIter.history!=null && vendorCredCatIter.exceptionInd==true}">
							<span id="expv_${networkHeader.spnId}_${s.count}" style="color:#00a0d2;font-weight: bolder;" >*</span>
									<div class="arrowAddNoteDetails" id="arrowv_${networkHeader.spnId}_${s.count}"></div>
									<div class="histDetailsDiv" id="expDetailsv_${networkHeader.spnId}_${s.count}">
										<div class="preCallHistoryHead"
											style="height: 20px; width: 100% px; background: #EEEEEE; -moz-border-radius: 8px 8px 0 0;">
											<span class="" style="float:left;"><b>&nbsp;&nbsp;Exception Details</b> </span> <span
												style="font-family: Arial; float: right; font-size: 13px; font-weight: bold; font-style: normal; text-decoration: none; color: rgb(51, 51, 51); cursor: pointer;"
												onclick="closeHistory(${s.count},'v',${networkHeader.spnId})" id="cache1">X</span>

										</div>
										<div
											style="margin-bottom: 5px; padding-left: 8px; border-top: 1px solid #CCCCCC; padding-top: 5px;">
											<div style="margin-top: 3px; padding-top: 5px;">
												<span><b>${vendorCredCatIter.description}</b></span>
											</div>
											<c:forEach items="${vendorCredCatIter.history}" var="vendCredCatHistIter">
											<div style="">
												<span style="font-weight: bolder;">.&nbsp;</span><span style="word-wrap:break-word;">${vendCredCatHistIter}</span>
											</div>
											</c:forEach>
										</div>
									</div>
								</c:if>
								<c:choose>
								<c:when test = "${s.last}"  >
								 &nbsp;
								</c:when>
								<c:otherwise>
								 ,
								</c:otherwise>
							</c:choose>														
							
						</c:forEach>
						<c:if test="${empty selectedApprovalItems.vendorCredCategoriesWithTypes}">N/A</c:if>
								
						
					</li>

					<li>
						<strong>Minimum Rating</strong>
						<c:choose>								
							<c:when test="${approvalItems.selectedMinimumRating != null}">			
								minimum ${approvalItems.selectedMinimumRating} stars
							</c:when>
							<c:otherwise>
								N/A
							</c:otherwise>
						</c:choose>
						
					</li>

					<li>
						<strong>Languages: </strong>
						<c:set var="xIter" value="0"  scope="request"/>
						<c:forEach items="${selectedApprovalItems.allLanguages}" var="langIter" varStatus="s">
							${langIter.description} 
							
							<c:choose>
								<c:when test = "${s.last}"  >
								 &nbsp;
								</c:when>
								<c:otherwise>
								 ,
								</c:otherwise>
							</c:choose>														
							
						</c:forEach>
						
						<c:if test="${empty selectedApprovalItems.allLanguages}">N/A</c:if>
						<br /><strong>Minimum Completed Service Orders: </strong> 
							<c:if test="${empty approvalItems.minimumCompletedServiceOrders}">
								N/A
							</c:if>
							${approvalItems.minimumCompletedServiceOrders}
						<br /><strong>Meeting with Provider:</strong>
							<c:if test="${approvalItems.meetingRequired}">
								Required
							</c:if>
							<c:if test="${!approvalItems.meetingRequired}">
								Not Required
							</c:if>
					</li>

					<li>
						<strong>Insurance Minimum Coverage</strong>
						<c:if test="${approvalItems.vehicleLiabilitySelected}">
							<br />
							<c:if test="${approvalItems.vehicleLiabilityAmt > 0.0}">
								<fmt:formatNumber type="currency" value="${approvalItems.vehicleLiabilityAmt}" />
							</c:if>	
							Vehicle Liability
							<c:if test="${approvalItems.vehicleLiabilityVerified}">
								<em>Verified</em>
							</c:if>	
						</c:if>
						<c:if test="${!approvalItems.vehicleLiabilitySelected}">
							<br />Vehicle Liability: None
						</c:if>
						<c:if test="${approvalItems.workersCompensationSelected}">
							<br />
							Worker's Compensation
							<c:if test="${approvalItems.workersCompensationVerified}">
								<em>Verified</em>
							</c:if>	
						</c:if>
						<c:if test="${!approvalItems.workersCompensationSelected}">
							<br />Worker's Compensation: None
						</c:if>
						<c:if test="${approvalItems.commercialGeneralLiabilitySelected}">
							<br />
							<c:if test="${approvalItems.commercialGeneralLiabilityAmt > 0.0}">
								<fmt:formatNumber type="currency" value="${approvalItems.commercialGeneralLiabilityAmt}" />
							</c:if>	
							Commercial General Liability
							<c:if test="${approvalItems.commercialGeneralLiabilityVerified}">
								<em>Verified</em>
							</c:if>	
						</c:if>
						<c:if test="${!approvalItems.commercialGeneralLiabilitySelected}">
							<br />Commercial General Liability: None
						</c:if>
					</li>
					<c:if test="${recertificationBuyerFeatureInd == true}">
					<li>
					<strong>Background check - 2 year recertification:</strong>
							<c:if test="${approvalItems.recertificationInd}">
								Required
							</c:if>
							<c:if test="${!approvalItems.recertificationInd}">
								Not Required
							</c:if>
					
					</li>
					</c:if>
				</ul>
			</dd>
		</dl>


		<c:set var="docReturn" value="0" scope="request"/>
		<c:set var="elecAgree" value="0" scope="request"/>
		<c:set var="informationOnly" value="0" scope="request" />

		<dl>
			<dt class="clearfix"><span>Documents</span><span class="open"></span><span class="closed"></span></dt>
			<dd>
				<ul>
					<li><strong>Sign &amp; Return</strong><br/>
						<c:forEach items="${approvalItems.spnDocumentList}" var="docListItem"><c:if test="${docListItem.type == 'Sign & Return'}"><c:if test="${docReturn == '1'}">, </c:if>${docListItem.title}<c:set var="docReturn" value="1" scope="request"/></c:if></c:forEach><c:if test="${docReturn == '0'}">None</c:if>
					</li>
					<li><strong>Electronic Signature</strong><br/>
						<c:forEach items="${approvalItems.spnDocumentList}" var="docListItem"><c:if test="${docListItem.type == 'Electronic Signature'}"><c:if test="${elecAgree == '1'}">, </c:if>${docListItem.title}<c:set var="elecAgree" value="1" scope="request"/></c:if></c:forEach><c:if test="${elecAgree == '0'}">None</c:if>
					</li>
					<li><strong>Information Only</strong><br/>
						<c:forEach items="${approvalItems.spnDocumentList}" var="docListItem"><c:if test="${docListItem.type == 'Information Only'}"><c:if test="${informationOnly == '1'}">, </c:if>${docListItem.title}<c:set var="informationOnly" value="1" scope="request"/></c:if></c:forEach><c:if test="${informationOnly == '0'}">None</c:if>
					</li>
				</ul>
			</dd>
		</dl>
	</div>
</div>
