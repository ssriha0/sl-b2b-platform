<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	jQuery(document).ready(function($) {
		$('#loadSPNSpinner').jqm({modal: true, toTop: true});
		
		$('#spnList').change(function() {
			var queryString = "spnHeader.spnId=" + $(this).val();
			
			if ($(this).val() != '-1')
			{
				var spnAction = "Please wait while we load the SPN:";
				var spnName = $('#spnList').children('[value=' + $(this).val() + ']').html();
				$('#spinSPNName').html(spnName);
				$('#spinSPNAction').html(spnAction);
				$('#loadSPNSpinner').jqmShow();
			}
			else if ($('#spinSPNName').html() != "")
			{
				var spnAction = "Please wait while we clear the SPN:";
				$('#spinSPNAction').html(spnAction);
				$('#loadSPNSpinner').jqmShow();
			}

			$('#rightSideData').load('spnCreateCampaign_updateRightSideAjax.action?' + queryString, function() {
				$('#loadSPNSpinner').jqmHide();
			});
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
		theme="simple" listValue="spnName" value="%{spnHeader.spnId}" cssStyle="width: 100%"/>
