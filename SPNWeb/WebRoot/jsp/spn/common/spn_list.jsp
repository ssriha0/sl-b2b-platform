<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	jQuery(document).ready(function($) {

		var spnId = $('#spnList').val();
		if(spnId != '-1'){
			var queryStringSPN = "spnHeader.spnId=" +spnId;
			$('#provider_count_match_spn').load('spnCreateCampaign_getSPNCountsAjax.action?' + queryStringSPN);
		}

		$('#spnList').change(function() {
			var queryString = "spnHeader.spnId=" + $(this).val();
			var spnId =  $(this).val();
			
			if ($(this).val() != '-1')
			{
				var spnAction = "Please wait while we load the SPN:";
				var spnName = $('#spnList').children('[value=' + $(this).val() + ']').html();
				$('#spinSPNName').html(spnName);
				$('#spinSPNAction').html(spnAction);
				$('#loadSPNSpinner').jqmShow();
				$('#inviteSaveAndDone').show();
			}
			else if ($('#spinSPNName').html() != "")
			{
				var spnAction = "Please wait while we clear the SPN:";
				$('#spinSPNAction').html(spnAction);
				$('#loadSPNSpinner').jqmShow();
				$('#inviteSaveAndDone').hide();
			}

			$('#campaignCriterias').load('spnCreateCampaign_loadSelectedSpnDataAjax.action?' + queryString, function() {
				$('#loadSPNSpinner').jqmHide();
				$('#spnId').attr('value',spnId);
				$('#provider_count_match_spn').load('spnCreateCampaign_getSPNCountsAjax.action?' + queryString);
			});
			$('#rightSideData').load('spnCreateCampaign_updateRightSideAjax.action?' + queryString);
		});
	});
</script>

<div id="loadSPNSpinner" class="jqmWindow">
	<div class="modal-header clearfix"><a href="#" class="right jqmClose">Close</a></div>
	<div class="modal-content">
		<label><span id="spinSPNAction">Please wait while we load the SPN:</span> <span id="spinSPNName"></span></label>
		<div>
			<img src="${staticContextPath}/images/simple/searchloading.gif" />
		</div>
		<div class="clearfix">
			<a class="cancel jqmClose left" href="#">Cancel</a>
		</div>
	</div>
</div>

<s:select id="spnList" name="spnList" headerKey="-1"
		headerValue="Select One" list="%{spnList}" listKey="spnId"
		theme="simple" listValue="spnName" value="%{spnHeader.spnId}"/>
