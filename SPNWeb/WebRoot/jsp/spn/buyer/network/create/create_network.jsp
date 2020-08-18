<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<head>
    <meta name="decorator" content="mainspndecorator" />
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>ServiceLive - Select Provider Network - Create Networks</title>
	<c:import url="/jsp/spn/common_includes.jsp" />
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js"></script>
	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/ui.datepicker.js"></script>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/modals.css" media="screen, projection">
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/ui.datepicker.css">
	<!-- icons by Font Awesome - http://fortawesome.github.com/Font-Awesome -->
	<link href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome.min.css" rel="stylesheet" />
	<link href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome-ie7.min.css" rel="stylesheet" />
	 <script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	      .ie7 .bannerDiv{margin-left:-1150px;}
	      .ie9 .bannerDiv{margin-left:-200px;}


	.exceptionsInfo{
	border-style: solid;
	background-color: #FFFFFF!important;
	border-color: 	#000000!important;
	width:350px; float: right;border-width: 1px 1px 1px;
	font-size:small;
	display: none;
	z-index:1200;
	position: absolute;
	-webkit-box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);-moz-box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
	box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
	color: #000000;
	font-size:10px;
	font-weight: normal;
	}
	#explainer {
	display: none; 
	width:220px;
	height:auto;
	border: 3px solid #adaaaa; 
	background:#fcfae6;
	border-radius:10px;
	-moz-border-radius:10px;
	-webkit-border-radius:10px; 
	padding:10px;}
	</style>
	<script type="text/javascript">
	var isMemberAffected;		
	jQuery(document).ready(function($) {
		setupMultiSelect();
		initCreateNetworkShowHide();
		$('#saveDoneModal').jqm({modal: true, toTop: true});
		$('#effectiveDate').datepicker({dateFormat:'mm/dd/yy', minDate: +1 });
		$('#ui-datepicker-div').css('z-index','3001'); //beat the modal z-index
/* 		$("#exceptionRequired").attr("checked","checked");
 */		$('#saveDoneButton').click(function() {
			<c:if test="${empty spnHeader.spnId || hasCampaign == false}">
				$('#spnCreateNetwork_saveAndDone').submit();
			</c:if>
			<c:if test="${not empty spnHeader.spnId && hasCampaign == true}">
				$('#effectiveDateError').hide();
				$('#effectiveDateCommentsError').hide();
				var frmquery = $('#spnCreateNetwork_saveAndDone').serialize();
				$.post('spnCreateNetwork_checkMembershipStatusAffectAjax.action',frmquery, function(data) {
					isMemberAffected=data.isMemberShipAffected;
					$('#formAuditRequired').val(data.isAuditRequired);
					if(isMemberAffected == 1){
						$('#affected').show();
						$('#notAffected').hide();
					}
					else{
						$('#affected').hide();
						$('#notAffected').show();
					}
					$('#saveDoneModal').jqmShow();
			}, "json");
			</c:if>
		});
		
		$('#submitWithDateButton').click(function() {
			
			var effectiveDate = $('#effectiveDate').val();
			var effectiveComments=$('#effectiveDateComments').val();
			if(typeof String.prototype.trim !== 'function') {  
				String.prototype.trim = function() { 
				    return this.replace(/^\s+|\s+$/g, ''); 
				}
			} 
			effectiveComments=effectiveComments.trim();
			
			$('#formMemberAffected').val(isMemberAffected);
			
			if(effectiveComments.length != 0){
                
                $('#effectiveDateError').hide();
                $('#effectiveDateCommentsError').hide();
                
				if(isMemberAffected==1){
				    
					$.post('spnCreateNetwork_checkEffectiveDateAjax.action?effectiveDate=' + effectiveDate, function(data) {
						if (data == 0)
						{   
							$('#effectiveDateError').html('Invalid Date. Please select the date greater than today.');
							$('#effectiveDateError').show();
						}
						else
						{   
							$('#formEffectiveDate').val($('#effectiveDate').val());
							$('#formComments').val($('#effectiveDateComments').val());
							$('#spnCreateNetwork_saveAndDone').submit();
						}
					}, "html");
				}
				else{	
					$('#formComments').val($('#effectiveDateComments').val());
					$('#spnCreateNetwork_saveAndDone').submit();
				}
			}
			else{
			    $('#effectiveDateError').hide();
                $('#effectiveDateCommentsError').hide();

			    if(isMemberAffected==1){	
				    $.post('spnCreateNetwork_checkEffectiveDateAjax.action?effectiveDate=' + effectiveDate, function(data) {
						   if (data == 0)
							{   
								$('#effectiveDateError').html('Invalid Date. Please select the date greater than today.');
								$('#effectiveDateError').show();
							}
						}, "html");
				}		
				$('#effectiveDateCommentsError').html('Please enter comments.');
				$('#effectiveDateCommentsError').show();
			}
		});
		
		
		
		$('.profile').mouseover(function(e){
		
    		$("#explainer").css("position","absolute");
   	     	var def = "Provider must meet the minimum requirement of ServiceLive Status (Approved Market Ready) to be in any network."
   	     	$("#explainer").html(def);
    		$("#explainer").css("margin-left",205);
	    	$("#explainer").css("margin-top",-700);
	    	$("#explainer").css("font-weight","bold");
	    	$("#explainer").css("border","1px solid black");
	    	$("#explainer").css("background-color","lightyellow"); 
	    	$("#explainer").show();  
		}); 
	  $('.profile').mouseout(function(e){
	 	 $("#explainer").hide();
		}); 
		
	});
	</script>	


</head>

<body id="select-provider-network">
	
<s:form action="spnCreateNetwork_saveAndDone"
	id="spnCreateNetwork_saveAndDone" name="spnCreateNetwork_saveAndDone"
	method="post" enctype="multipart/form-data" theme="simple">

	<input type="hidden" id="formEffectiveDate" name="spnHeader.effectiveDate" />
	<input type="hidden" id="formComments" name="spnHeader.comments" />
	<input type="hidden" id="formMemberAffected" name="memberStatusAffected" />
	<input type="hidden" id="formAuditRequired" name="auditRequired"/>

	
		<div class="content">
			<h2 id="page-role">Administrator Office 
			</h2>
			<h2 id="page-title">
				Select Provider Network (SPN)
			</h2>

			<h3 class="">
				Create A Select Provider Network
			</h3>
			<div id="tabs">
	<c:import url="/jsp/spn/common_tabs.jsp" ><c:param name="tabName" value="spnCreateNetwork" /></c:import>
				<div id="tab-content" class="clearfix">
					<div class="clearfix span-14">
						<h3 class="collapse c-general" title="c-general">
							<span>Name &amp; General Information</span><span
								class="plus c-general"></span><span class="min c-general"></span>
						</h3>

						<div class="toggle c-general">
							<fieldset>
								<p>
									Items marked with an asterix(
									<span class="req">*</span>) are required.
								</p>

								<div>
									<jsp:include page="/jsp/spn/common/validation_messages.jsp" />
								</div>

								<jsp:include page="/jsp/spn/buyer/network/create/create_network_header.jsp"></jsp:include>

								<div class="clearfix">
									<div class="wider">
										<label>
											Main Services
											<span class="req">*</span>
										</label>
										
										<jsp:include page="/jsp/spn/buyer/network/create/create_network_main_services.jsp"></jsp:include>
										
									</div>


									<div class="wider">
										<label>
											Skills
											<span class="req">*</span>
										</label>
										<div id="servicesWithSkills">
											<jsp:include page="/jsp/spn/common/checkbox_services_with_skills.jsp"></jsp:include>
										</div>
									</div>
								</div>
								
								<div id="subCatArea">
									<div class="clearfix">
										<h5 class="collapse c-cat" title="c-cat">
											<span>Specify Categories</span>
											<span class="plus"></span><span class="min"></span>
										</h5>
									</div>
									<div id="subCatDropDowns">
										<jsp:include page="/jsp/spn/buyer/network/create/create_network_subcategories.jsp"></jsp:include>
									</div>
								</div>
							</fieldset>
						</div>


						<h3 class="collapse c-approval" title="c-approval">
							<span>Approval Criteria &amp; Credentials</span><span
								class="plus"></span><span class="min"></span>
						</h3>
						<div class="toggle c-approval">
							<fieldset>
								<p>
									Items marked with an asterix(
									<span class="req">*</span>) are required.
								</p>

								<div class="clearfix" style="height: 60px;">
									<div class="half multiselect">
										<label>
											Minimum Rating
										</label>
											<s:select
												id="approvalItems.selectedMinimumRating"
												name="approvalItems.selectedMinimumRating"
												value="%{approvalItems.selectedMinimumRating}"
												list="lookupsVO.allMinimumRatings"
												listKey="id"
												listValue="description"
												headerKey="0"
												headerValue="No Selection"
												theme="simple"
											/>
										
										<div>
											<s:checkbox id="approvalItems.isNotRated" name="approvalItems.isNotRated" value="%{approvalItems.isNotRated}"/> Include non-rated providers
										</div>
										
									</div>
									<div class="half multiselect">
										<label>
											Languages
										</label>
										<div class="picked pickedClick">
										<label>No Selection</label>
										</div>
										<div class="select-options">
											<c:forEach items="${lookupsVO.allLanguages}" var="lookupLang">
												<c:set var="checked" value="" ></c:set>
												<c:forEach items="${approvalItems.selectedLanguages}" var="selectedLang" >
													<c:if test="${lookupLang.id == selectedLang}">
														<c:set var="checked" value=" checked " ></c:set>
													</c:if>
												</c:forEach>
												<div style="clear: left;"><input type="checkbox" name="approvalItems.selectedLanguages" value="${lookupLang.id}" ${checked} /> ${lookupLang.description}</div>
											</c:forEach>
										</div>
									</div>
								</div>
								<div class="clearfix" ></div>
								<div class="clearfix">
								<label>
										Minimum Completed Service Orders
								</label>
									<s:textfield id="approvalItems.minimumCompletedServiceOrders" name="approvalItems.minimumCompletedServiceOrders"
										value="%{approvalItems.minimumCompletedServiceOrders}" theme="simple" cssClass="text" cssStyle="width: 40" maxlength="3"/>
								</div>
								
								<table border="0" cellpadding="5" cellspacing="0">
								<thead>
									<th colspan="2">
									Provider Profile Status <span id ="explainerProfile" class="profile" style="cursor: pointer"><i class="icon-question-sign" style="font-size: 14px;"></i></span>
									</th>
								</thead>
								<tbody>
										<tr>
										<td>
											<div class="left checkbox">
												<input type="checkbox" id = "slStatus" name="slStatus" checked="checked" disabled="disabled"/>ServiceLive Status = Approved (Market Ready)
											</div>
										</td>
										</tr>
										<c:if test="${recertificationBuyerFeatureInd == true}">
											<tr>
											<td>
												<div class="left checkbox">
												<s:checkbox id="approvalItems.recertificationInd" name="approvalItems.recertificationInd" value="%{approvalItems.recertificationInd}"/> Background Check 2 Year Re-Certification Required
												</div>
											</td>
											</tr>
										</c:if>
								</tbody>
								</table>
								
								<jsp:include page="/jsp/spn/buyer/network/create/create_network_insurance.jsp"></jsp:include>
								
								<jsp:include page="/jsp/spn/buyer/network/create/create_network_provider_credentials.jsp"></jsp:include>
								
								<div class="notice clearfix" >
										<div style="width:100%">
										<div class="left checkbox">
										<s:checkbox cssClass="checkbox" id="exceptionRequired" 
														name="exceptionRequired" fieldValue="true" 
														value="%{exceptionRequired}"></s:checkbox>
							<input type="hidden" id="initialExpInd" name="initialExpInd"  value ="${exceptionRequired}" />
							<input type="hidden" name="spnHeader.criteriaLevel" value="${criteriaLevel}" />
							<input type="hidden" name="spnHeader.criteriaTimeframe" value="${criteriaTimeframe}" />
							<input type="hidden" name="spnHeader.routingPriorityStatus" value="${routingPriorityStatus}" />
							<input type="hidden" name="spnHeader.marketPlaceOverFlow" value="${marketPlaceOverFlow}" />
										</div>
										<div class="left">
											<span>&nbsp;There are one or more <span id="exceptions" style="border-bottom: 1px dotted; cursor:default;" title="Exceptions can be things like a grace period added to license expiration dates, or exempting certain states who do not require certain licenses">exceptions</span> to the above credentials.</span>
										</div>
										</div>
										<span id="brDiv">
										&nbsp;</br>
										</span>
										<div style="padding-left: 0px;">
										<div class="left"  id="meetingCheckBox" style="margin-left:-5.1%;" >
										<s:checkbox cssClass="checkbox" id="approvalItems.meetingRequired" 
														name="approvalItems.meetingRequired" fieldValue="true" 
														value="%{approvalItems.meetingRequired}"></s:checkbox>
										</div>
										<div id="meetingSpan" class="left">
											<span>&nbsp;A meeting with the Provider Firm is required for
												approval.</span>
										</div>
										</div>
								</div>
							</fieldset>
							<div class="exceptionsInfo" id="exceptionsInfo" style="display: none;margin-left: 225px;margin-top: -75px;background: none; height: 30px;">
										<p style="padding-left: 5px;">
												Exceptions can be things like a grace period added to license expiration dates, or exempting certain states who do not require certain licenses										</p>
							</div>
						</div>
						
						<jsp:include page="/jsp/spn/buyer/network/create/create_network_document.jsp"></jsp:include>
						
						<div class="clearfix buttonarea">
							<s:a cssClass="cancel left"
								href="%{#request['contextPath']}/spnMonitorNetwork_display.action">Cancel</s:a>
							<s:submit id="saveDoneButton" type="input" cssClass="button action right" theme="simple"
								value="Save & Done" onclick="return false;" />
						</div>
					</div>
				</div>
			</div>
		</div>
<div id="explainer" style="z-index: 1000"></div>
</s:form>



<div id="saveDoneModal" class="jqmWindow">
      <div class="modal-header clearfix"><a href="#" class="right jqmClose">Close</a><span class="left" style="font-size: 15px; font-weight: bold;">Edit Network Confirmation</span></div>
      <div class="modal-content">
            <div id="effectiveDateError" class="error hide">Test Error</div>
            <div id="effectiveDateCommentsError" class="error hide">Test Error</div>
            <div style="text-align: left;">
                  (<font color="red">*</font> required)
            </div>
            <div id="affected">
            <div style="text-align: left;">
                  <b>The changes made do affect membership requirements. Select an effective date for this change.</b>
            </div>
                 
            <table>
                  <tr>
                  
                  <td style="font-weight: bold"><div style="width:100px" align="center">Effective Date:<font color="red">*</font></div></td>
                  
                  
                  <td><input type="text" id="effectiveDate" name="effectiveDate" value="" style="width: 100px;text-align: left" /></td>
                  <td>
                
                 <SPAN style="font-weight: bold"> TIP</SPAN>: When changing membership criteria,set the date 10-20 days in advance to allow providers time to update and remain active members.
                    </td>
                  </tr>
            </table>
            </div>
            <div id="notAffected" style="text-align: left;display: block">
                  <b>The changes made do not affect membership requirements.</b>
            </div>
            
            <table>
                  <tr><td style="font-weight: bold"><div style="width:90px" align="center">Comments:<font color="red">*</font></div></td>
                  <td><textarea id="effectiveDateComments" name="comments"></textarea></td></tr>
            </table>
            
            <div class="clearfix">
                  <a class="cancel jqmClose left" href="#">Cancel</a>
                  <s:submit id="submitWithDateButton" type="input" cssClass="button action right" theme="simple" value="Submit" onclick="return false;" />
            </div>
      </div>
</div>

</body>
