<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%-- 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
--%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive - Select Provider Network - Create
			Campaigns</title>
		
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/styles/plugins/modals.css"
			media="screen, projection">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/screen.css" media="screen, projection">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/superfish.css" media="screen, projection">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/modals.css" media="screen, projection">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/select-provider-network.css" media="screen, projection" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/jqueryui/jquery-ui-1.7.2.custom.css" />
	
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.flash.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.sifr.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.hoverIntent.minified.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.bgiframe.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.multiselect.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/ui.datepicker.js"></script>
	 	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.maxlength-min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery-ajaxfileupload.js"></script>
	 	<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.wysiwyg.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqueryui/jquery-ui-1.7.2.custom.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.maxlength-min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/superfish.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/supersubs.js"></script>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/select_provider_network.js"></script>
        <script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	      .ie7 .bannerDiv{margin-left:-1150px;}
	      .ie9 .bannerDiv{margin-left:-200px;}
	 	</style>
		<script type="text/javascript">
			$(document).ready(function() {	
				setupMultiSelect();			
				initCreateCampaignHideShow();
				
				//if(typeof(${spnHeader.spnId}) != "undefined")
				//{
					//var spnId = ${spnHeader.spnId};
					//$("#provider_count_match_spn").load("spnCreateCampaign_getSPNCountsAjax.action", {"spnHeader.spnId":  spnId} );
				//}
				// Select the appropriate tab and tab contents				
				if(${showInviteByProvidersTab} == true)
				{
					displayInviteByProvidersTab();
				}
				else
				{
					displayInviteByCriteriaTab();
				}

				$('#campaignToFirms').click( function()
				{
					displayInviteByProvidersTab();
				});
				
				$('#campaignByCriteria').click( function()
				{
					displayInviteByCriteriaTab();
				});
				
				
				function displayInviteByProvidersTab() {
					//alert('campaignToFirms');
					$('#campaignToFirms').addClass("on");
					$('#campaignToFirms').removeClass("off");
					$('#campaignByCriteria').addClass("off");
					$('#campaignByCriteria').removeClass("on");
					
					$('#invitebycriteria').hide();
					$('#invitebyproviderfirmid').show();													
				};
				
				function displayInviteByCriteriaTab() {
					//alert('campaignToCriteria');
					$('#campaignToFirms').addClass("off");
					$('#campaignToFirms').removeClass("on");					
					$('#campaignByCriteria').addClass("on");
					$('#campaignByCriteria').removeClass("off");
					
					$('#invitebycriteria').show();
					$('#invitebyproviderfirmid').hide();
				};
				
				
			
			
				$('input.providerSearchButton').click(function(event)
				{
					//SL-19857-START
					//Clear the error message 'No Firms selected for campaign' on click of ADD firm button
					var errorMessageNoFirms = $('.error').html();
					if(errorMessageNoFirms == 'No Firms selected for campaign'){
						$('.error').html("");
						$('.error').hide();
					}
					//SL-19857-END

					var spnId = $('#spnList').val();
					
					var modifiedSearchString = $('#modifiedSearchString').val();
					var providerIdList = '';
					if(modifiedSearchString != null)
						providerIdList = modifiedSearchString;
					
					if($('#providerIdList').val() != null)
						providerIdList = providerIdList + $('#providerIdList').val();
						
					//alert(modifiedSearchString + ' ' + providerIdList + ' spnId=' + spnId);
					if(providerIdList != null)
					{
						providerIdList = removeSpaces(providerIdList);
						//alert(providerIdList);
						var seconds = new Date().getTime();				
						$('#providerSearchResults').load('spnInviteProvider_loadProviderSearchResultsAjax.action?providerIdList=' + providerIdList + '&spnId=' + spnId + '&randomnum=' + seconds, function(){
						});
					}	
			 	});	


				
				$('#inviteSaveAndDone').click(function(event)
				{
					var spnId = $('#spnList').val();
					var campaignName = $('#inviteCampaignName').val();
					var checkedList = $('#providerCheckboxList').val();
					var providerListString = '';
					var startDate2 = $('#startDate2').val();
					var endDate2 = $('#endDate2').val();

					//alert('saveAndDone: spnId=' + spnId);
					
					var errorMessage = "You must fill in all data fields before you can save";
					if(spnId == -1)
					{
						//alert('spnId');
						$('.error').html(errorMessage);
						$('.error').show();
						return false;
					}					
					
					if(startDate2 == '' || startDate2 == null)
					{
						//alert('startDate');
						$('.error').html(errorMessage);
						$('.error').show();
						return false;
					}
					
					if(endDate2 == '' || endDate2 == null)
					{
						//alert('endDate');
						$('.error').html(errorMessage);
						$('.error').show();
						return false;
					}

					// Create a currentDate String in the same format
					// As the values from the datepickers.
					var dateObj = new Date();
					var day = dateObj.getDate() + "";
					var month = dateObj.getMonth() + "";
					if(day.length < 2)
						day = '0' + day;
					if(month.length < 2)
						month = '0' + month;
					var currentDate = dateObj.getFullYear() + '-' + month + '-' + day;
					//alert(day.length + ' current=' + currentDate + '\n' + 'startDate=' + $('#startDate2').val() + '\n' + 'endDate=' + $('#endDate2').val());
					
					// check if start date is in the past
					if(currentDate > $('#startDate2').val())
					{
						errorMessage = "Please select valid start date. Start date cannot be in the past";
						$('.error').html(errorMessage);
						$('.error').show();
						return false;					
					}

					// check if the end date is in the past
					if(currentDate > $('#startEnd2').val())
					{
						errorMessage = "Please select valid end date. End date cannot be in the past";
						$('.error').html(errorMessage);
						$('.error').show();
						return false;					
					}
					
					if($('#startDate2').val() > $('#endDate2').val())
					{
						errorMessage = "Please select valid end date.  End date cannot be before start date";
						$('.error').html(errorMessage);
						$('.error').show();
						return false;						
					}
					
										
					if(campaignName == '' || campaignName == null)
					{
						//alert('inviteCampaignName');
						$('.error').html(errorMessage);
						$('.error').show();
						return false;
					}
					
					
										
					if($('.firmId').length == 0)
					{
						//alert('no firm results');
						$('.error').html(errorMessage);
						$('.error').show();
						return false;
					}
					//R10.3 SL-19857 fix: Throw error message if no provider is selected while save START
					var checkedList = new Array();
					$("input:checkbox[name=providerCheckboxList]:checked").each(function() {
						checkedList.push($(this).val());
					});
					if(checkedList == '' || checkedList == null){
						$('.error').html('No Firms selected for campaign');
						$('.error').show();
						return false;
					}
					//R10.3 SL-19857 fix: Throw error message if no provider is selected while save END
					
					$('.error').html("");
					$('.error').hide();

					//alert('submit');					
					return true;
					/*
					//alert($('#providerCheckboxList'));
					$.post(
						'spnInviteProvider_saveAndDone.action', 
						{ 'spnHeader.spnId': spnId, 'campaignName': campaignName,  'providerCheckboxList': null, 'startDate2': startDate2, 'endDate2': endDate2},
						null,
						"html"
						);
						*/
					
				});
				
						
				
				$('#startDate2').datepicker({dateFormat:'yy-mm-dd', minDate: new Date() });
				$('#endDate2').datepicker({dateFormat:'yy-mm-dd', minDate: new Date() });
				
				
				$("#deleteAll").click(function(){
				    $('#providerSearchResults').html('');
				    
				    var seconds = new Date().getTime();      				
					$('#providerSearchResults').load('spnInviteProvider_deleteAllAjax.action?randomnum='+seconds);
				    $.post("spnInviteProvider_deleteAllAjax.action?randomnum="+seconds);
				 });
				
				function removeSpaces(string) {
				 return string.split(' ').join('');
				}
				
			});
			
		</script>
		
		<style type="text/css">
		.customSubtabs {clear:both; margin:auto; height: 30px; width:255px; margin-bottom:-2px; background:#fff;}
		.customSubtabs .on {float:left; padding:5px; border:solid 1px #777; border-bottom:0; margin-right:1px; color:#000; background:#fff;}
		.customSubtabs .off {float:left; padding:5px; border:solid 1px #777; margin-left:1px; color:#e3e3e3; background:#333; border-bottom:0;}
		.off a, .off a:visited {color:#e3e3e3;}
		.on a, .on a:visited {color:#000;}
		#searchProviderFirmId {background: #eaeecf; border:solid 2px #cfd896; border-radius:5px; -moz-border-radius:5px; -webkit-border-radius:5px;}
		.errorAlert{background: #fbfbe5;}
		#searchProviderFirmId p {color: #4c672f;}
		.larger {font-size:115%;}
		.warningBox {background:#fbfbe5 url(<c:out value="${staticContextPath}"/>/images/icons/conditional_exclamation.gif) 
			no-repeat scroll 3px 6px; border:1px solid #caac65; display:inline; font-weight:bold; padding:5px 5px 5px 20px;
			border-radius:3px; -moz-border-radius:3px; -webkit-border-radius:3px;}
		</style>

	</head>
	<body id="select-provider-network">
		<s:form action="spnCreateCampaign_saveAndDone"
			id="spnCreateCampaign_saveAndDone"
			name="spnCreateCampaign_saveAndDone" method="post"
			enctype="multipart/form-data" theme="simple" validate="true">

			<div id="wrap" class="container">
				<jsp:include page="/jsp/spn/common/defaultheadernew.jsp" />
				<div id="content" class="span-24 clearfix">

					<div id="primary" class="span-24 first last">

						<div class="content">
							<h2 id="page-role">
								Administrator Office
							</h2>
							<h2 id="page-title">
								Select Provider Network (SPN)
							</h2>

							<h3 class="">
								Select Invitation Criteria
							</h3>

							<div id="tabs">
								<c:import url="/jsp/spn/common_tabs.jsp">
									<c:param name="tabName" value="spnCreateCampaign" />
								</c:import>						
								<div class="customSubtabs">								
									<div id="campaignByCriteria" class="on" style="cursor:pointer">
										Campaign by Criteria
									</div>
									<div id="campaignToFirms" class="off" style="cursor:pointer">
										Campaign to Firms
									</div>
								</div>
								
								<div id="tab-content" class="clearfix">

								
									<div class="information right">
										<jsp:include page="right_side_create_campaign.jsp" />
									</div>

									<div class="clearfix span-14">
										<label
											style="float: left; margin-left: 5px; margin-right: 10px; padding-top: 10px;">
											Select Provider Network
											<span class="req">*</span>
										</label>
								
										<div id="spnSelectList">
											<jsp:include page="/jsp/spn/common/spn_list.jsp"></jsp:include>
										</div>
									</div>
																				
									<div id="invitebycriteria">
										<jsp:include page="subtab_invite_by_criteria.jsp"/>
									</div>
									
									<div id="invitebyproviderfirmid" style="display: none">
										<jsp:include page="subtab_invite_by_specific_provider.jsp"/>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<jsp:include page="/jsp/spn/common/defaultfootertmp.jsp" />
			</div>
		</s:form>
	</body>
</html>
