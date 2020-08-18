<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />


<head>
	<title>ServiceLive - Select Provider Network - Create Networks</title>
	<script type="text/javascript"
		src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js"></script>
	<link rel="stylesheet" type="text/css"
		href="${staticContextPath}/styles/plugins/modals.css"
		media="screen, projection">
	 <script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	      .ie7 .bannerDiv{margin-left:-1150px;}
	      .ie9 .bannerDiv{margin-left:-200px;}
	 	</style>
	<script type="text/javascript">
			
		jQuery(document).ready(function($) {
		

		
			if(${spnBeingEdited} != null && ${spnBeingEdited} > 0 )
			{
				//alert('load a tier:' + ${preloadedSpnId});
				var spnId = ${spnBeingEdited};
				$('#spnId').val(${spnBeingEdited});
				var vf= $('#validatefail').val();
				
				
				$('.tier_table').load('spnReleaseTiers_buttonLoadTierAjax.action', {"spnId": spnId, "spnBeingEdited" : spnId, "validatefail" : vf});
				$('.tier_table').show();			
			}
			
			
			$("#buttonAddTier").live("click", function(){
				var spnId = $('#spnId').val();
				var queryString = '';
				$("#availableperfLevels .diamondWrapper").children('input:checked').each(function() {
					queryString = queryString + "&avPfCb=" + $(this).val();
				});
				if(spnId == -1)
				{
					$(".error").hide();
					$("#frontEndError").show();
					$("#frontEndError").html("Please select a network");
					return false;
				}
				
				if(queryString.length < 1)
				{
					$(".error").hide();
					$("#frontEndError").show();
					$("#frontEndError").html("Please select at least one provider group to create a routing priority");
					return false;				
				}
				$("#frontEndError").hide();
				//alert('spnId:' + spnId + '\n' + cb1 + ' ' + cb2 + ' ' + cb3 + ' ' + cb4 );
				
				queryString = "spnId=" + spnId +queryString;
				var marketplaceOverflow = $('#marketplaceOverflow').val();				
				
				var spnBeingEdited = $('#spnBeingEdited').val();

				var frmquery = $('#spnReleaseTiers_').serialize();
				$('.tier_results_ajax').load('spnReleaseTiers_buttonAddTierAjax.action?'+frmquery);			
			 });
			
			$("#buttonDeleteAll").live("click", function(){
				var spnId = $('#spnId').val();
				var queryString = "spnId=" + spnId ;
				var spnBeingEdited = $('#spnBeingEdited').val();
				if(spnBeingEdited > 0)
					queryString = queryString + "&spnBeingEdited=" + spnBeingEdited;				
				
				showDeleteConfirmationModal();
				//$('.tier_table').load('spnReleaseTiers_buttonDeleteAllAjax.action?' + queryString, null);
			});
			
			$("#buttonDeleteAllConfirm").live("click", function(){
				var spnId = $('#spnId').val();
				var queryString = "spnId=" + spnId ;
				var spnBeingEdited = $('#spnBeingEdited').val();
				if(spnBeingEdited > 0)
					queryString = queryString + "&spnBeingEdited=" + spnBeingEdited;				
				
				$('.tier_table').load('spnReleaseTiers_buttonDeleteAllAjax.action?' + queryString, null);
				cancelDeleteModal();
			});
					
			
			$("#spnId").change( function(){
				var spnId = $('#spnId').val();
				
				if(spnId != -1)
				{
					//alert('buttonLoadTierAjax:' + spnId);
					
					$('.tier_table').load('spnReleaseTiers_buttonLoadTierAjax.action', {"spnId": spnId });
					$('.tier_table').show();
				}
				else
				{
					$('.tier_table').hide();
				}
			});
			
		
		
		});
		
		
		function validateSaveTiers() {
		//SS: this needs to be decided upon and extended, the code below is just a suggestion	
			if (${error && true}) {
				$("#error1").show();
				document.getElementById("error1").scrollIntoView();
			}else {
				$("#confirmSaveDialog").jqmShow();
			}
		}
		function cancelSaveModal(){
			$("#confirmSaveDialog").jqmHide();
		}
		
		function cancelDeleteModal(){
			$("#confirmDeleteDialog").jqmHide();
		}
		
		function showSaveConfirmationModal()
		{
			var spnBeingEdited = $('#spnBeingEdited').val();
			if(spnBeingEdited > 0)
			{
				$("#confirmSaveDialog").jqm();				
				$('#confirmSaveDialog').jqmShow();
				return false;
			}
			return true;					
		}
		
		function showDeleteConfirmationModal()
		{
			var spnBeingEdited = $('#spnBeingEdited').val();
			
			var spnId = $('#spnId').val();
			
			if(spnBeingEdited > 0 || spnId > 0)
			{
				$("#confirmDeleteDialog").jqm();				
				$('#confirmDeleteDialog').jqmShow();
				return false;
			}
			return true;					
		}
		
	</script>
</head>

<body id="select-provider-network">

<s:form id="spnReleaseTiers_" name="spnReleaseTiers_" method="get"
		action="spnReleaseTiers_saveAndDone.action" >

	<div class="content">
		<h2 id="page-role">
			Administrator Office
		</h2>
		<h2 id="page-title">
			Select Provider Network (SPN)
		</h2>

		<h3 class="">
			Network Release Priorities
		</h3>

		<div id="tabs">
			<c:import url="/jsp/spn/common_tabs.jsp">
				<c:param name="tabName" value="spnRoutingTiers" />
			</c:import>
		</div>

		<div id="tab-content" class="clearfix">
			<h3>
				Routing Priority
			</h3>
			<p>
				Routing priority offers a way to set advanced release times for higher
				performing providers in a network. Begin by selecting a network.
				Then assign one or more groups to the priority level and set the
				advanced release time. Click save and done to apply the settings.
			</p>
			<p>
				(* required fields)
			</p>
			
			

			</div>
			<div class="clearfix span-14">
					<jsp:include page="/jsp/spn/common/validation_messages.jsp" />
			</div>
			<input type="hidden" id="validatefail" name="validatefail" value="${validatefail}"/>
			<div class="clearfix span-14">
				<label for="spnId" style="float: left; margin: 10px 5px 0 0;">
					Select a Network <span style="color:#ff0000">*</span>
				</label>
				<s:select id="spnId" name="spnId" headerKey="-1"
					headerValue="Select One" list="%{spnList}" listKey="value"
					theme="simple" listValue="label" value="%{spnHeader.spnId}" />


				<div class="tier_table"  style="display: none">
					<jsp:include page="panel_edit_tiers.jsp" />
				</div>
			</div>

			
			
			
		</div>
</s:form>		
</body>
