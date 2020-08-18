<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<style type="text/css">
.spill{
margin-left: 0px!important;
border-right: 1px solid #CCCCCC;
width:640px!important;
}
.provTable td  {
	border: 0;
	float:none!important;
}
strong {
	padding: 0px!important;
}
#providerReqsDiv{
	border: 2px solid #CCC;
	background: #FFF;
	width:auto!important;
}
#companyReqsDiv {
	border: 2px solid #CCC;
	background: #FFF;
	width:auto!important;
}

</style>
<script type="text/javascript">
		$(document).ready(function() {
			$('.companyRequirementsLink').unbind('click').click(function(e) {
				var spnId = $(this).siblings('[name=spnId]').val();
				var vendorId = $(this).siblings('[name=vendorId]').val();
				
				togglePlusMinusImage($(this).children('.plus-image,.minus-image'));
				
				if ($('#companyRequirements_' + spnId).is(':hidden'))
				{
					$('#companyRequirements_' + spnId).load('spnMonitorAction_getCompanyRequirementsList.action?&spnID='+spnId+'&vendorId='+vendorId,function(){
						$("#complFirmUpdated").css("margin-right","");
					});
					$('#companyRequirements_' + spnId).show();
				}
				else
				{
					$('#companyRequirements_' + spnId).hide();
				}
				return false;
			});
			
			$('.providerRequirementsLink').unbind('click').click(function(e) {
				var spnId = $(this).siblings('[name=spnId]').val();
				var vendorId = $(this).siblings('[name=vendorId]').val();
				
				togglePlusMinusImage($(this).children('.plus-image,.minus-image'));
				
				if ($('#providerRequirements_' + spnId).is(':hidden'))
				{
					$('#providerRequirements_' + spnId).load('spnMonitorAction_getProviderRequirementsList.action?&spnID='+spnId+'&vendorId='+vendorId);
					$('#providerRequirements_' + spnId).show();
				}
				else
				{
					$('#providerRequirements_' + spnId).hide();
				}
				return false;
			});
		});
</script>

<div><strong>Membership Criteria &amp; Credentials</strong></div>
<div>
	After submitting the documents and agreements below, you can go to your <a href="allTabView.action">ServiceLive Profile</a> to add company credentials
	Provider credentials can be updated in the individual provider profiles on ServiceLive.
</div>
<div class="clearfix">
	<div id="providerRequirements" class="jqmWindow" style="width:400px;"></div>
	<div id="companyRequirements" class="jqmWindow" style="width:400px;"></div>
	<div class="left" style="margin-top: 5px; margin-bottom: 5px;"> 
		<div>
			<a href="#" class="c-cat companyRequirementsLink">
				<span>View Company Requirements</span>
				<span class="plus-image" ></span>
			</a>
			<input type="hidden" name="spnId" value="${spnMonitorVO.spnId}" />
			<input type="hidden" name="vendorId" value="${spnMonitorVO.vendorId}" />
		</div> 
		<div style="clear: both;"></div>
		<div id="companyRequirements_${spnMonitorVO.spnId}" class="hide"></div>
		<div style="clear: both;"></div>
		<div>
			<a href="#" class="c-cat providerRequirementsLink">
				<span>View Provider Requirements</span>
				<span class="plus-image" ></span>
			</a>
			<input type="hidden" name="spnId" value="${spnMonitorVO.spnId}" />
			<input type="hidden" name="vendorId" value="${spnMonitorVO.vendorId}" />
		</div>
		<div style="clear: both;"></div>
		<div id="providerRequirements_${spnMonitorVO.spnId}" class="hide"></div>
	</div>
</div>