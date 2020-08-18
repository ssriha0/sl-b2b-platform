<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<html>
<head>
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
	border: 1px solid #CCC;
	background: #FFF;
	width:auto!important;
}
#companyReqsDiv {
	border: 1px solid #CCC;
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
				
				if ($('#companyRequirements_' + spnId + '_' + vendorId).is(':hidden'))
				{
					$('#companyRequirements_' + spnId + '_' + vendorId).load('spnAuditorCriteriaAndCredentialsTab_getCompanyRequirementsListAjax.action?networkId='+spnId+'&providerFirmId='+vendorId,function(){
						$("#complFirmUpdated").css("margin-left","0px");
					});
					$('#companyRequirements_' + spnId + '_' + vendorId).show();
				}
				else
				{
					$('#companyRequirements_' + spnId + '_' + vendorId).hide();
				}
			});
			
			$('.providerRequirementsLink').unbind('click').click(function(e) {
				var spnId = $(this).siblings('[name=spnId]').val();
				var vendorId = $(this).siblings('[name=vendorId]').val();
				
				togglePlusMinusImage($(this).children('.plus-image,.minus-image'));
				
				if ($('#providerRequirements_' + spnId + '_' + vendorId).is(':hidden'))
				{
					$('#providerRequirements_' + spnId + '_' + vendorId).load('spnAuditorCriteriaAndCredentialsTab_getProviderRequirementsListAjax.action?networkId='+spnId+'&providerFirmId='+vendorId, function() {
						$("#complProvUpdated").css("margin-left","0px");
					});
					$('#providerRequirements_' + spnId + '_' + vendorId).show();
				}
				else
				{
					$('#providerRequirements_' + spnId + '_' + vendorId).hide();
				}
			});
		});
</script>
</head>
<body>


<div class="clearfix">
	<div class="requirements" style="background: #FFF; padding: 10px;">
		<div>
			<a  class="c-cat" id="view_company_link_${networkId}_${providerFirmId}" onClick="clickCompanyRequirements(${networkId},${providerFirmId});">
				<span>View Company Requirements</span>
				<span class="plus-image" ></span>
			</a>
		</div> 
		<div style="clear: both;"></div>
		<a id="anchor_companyRequirements_${networkId}_${providerFirmId}" name="anchor_companyRequirements_${networkId}_${providerFirmId}" />
		<div id="companyRequirements_${networkId}_${providerFirmId}" class="hide"></div>
		<div style="clear: both; margin-top: 5px;"></div>
		<div>
			<a  class="c-cat" id="view_provider_link_${networkId}_${providerFirmId}" onClick="clickProviderRequirements(${networkId},${providerFirmId});">
				<span>View Provider Requirements</span>
				<span class="plus-image" ></span>
			</a>
		</div>
		<div style="clear: both;"></div>
		<a id="anchor_providerRequirements_${networkId}_${providerFirmId}" name="anchor_providerRequirements_${networkId}_${providerFirmId}" />
		<div id="providerRequirements_${networkId}_${providerFirmId}" class="hide"></div>
	</div>
</div>

</body>
</html>