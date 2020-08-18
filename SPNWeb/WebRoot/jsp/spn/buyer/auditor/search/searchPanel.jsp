<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">	
	jQuery(document).ready(function($) {
		
		//SL-19387 To display Background Check details of resources
		function clickViewBackgroundInformation(spnId,stateCd,status,providerFirmName,providerFirmNumber,marketId,zipCode,districtId)
		{
					 $('#loadingbg').show();

               		 $('#loadingbg').html('<img src="${staticContextPath}/images/loading.gif" width="800px" height="400px">');

					$('#loadingbg').load('spnAuditorSearchTab_searchBackgroundInformationCountAjax.action?spnId='+spnId+'&stateCd='+stateCd+'&status='+status+'&providerFirmName='+providerFirmName+'&providerFirmNumber='+providerFirmNumber+'&marketId='+marketId+'&zipCode='+zipCode+'&districtId='+districtId);
		}
		
		
		if ($('#searchByType').val() != "-1") 
		{
			var idName = '#searchByTypeVal' + $('#searchByType').val();
			var idText = '#searchByTypeText' + $('#searchByType').val(); 
			$('.searchByTypeVal').hide();
			$(idName).show();
			$('.searchByTypeText').hide();
			$(idText).show();
			$('.searchSubmit').show();
		}
	
		$('#searchByType').change(function() {
			var searchSubmit = '#searchSubmit' + $(this).val();
			var idName = '#searchByTypeVal' + $(this).val();
			var idText = '#searchByTypeText' + $(this).val();
			$('.searchByTypeVal').val('');
			if ($(this).val() == "3") { $(idName).val('-1'); }
			$('.searchByTypeVal').hide();
			$(idName).show();
			$('.searchByTypeText').hide();
			$(idText).show();
			$('.searchSubmit').show();
			$(searchSubmit).hide();
		});
		
		

		$('#searchSubmit-1').click(function() {
			var bgStatusText = $('#memberStatus option:selected').text();
			var bgStatus = $.trim(bgStatusText);
			if('Background Check Status' == bgStatus){
				$('#searchresults').hide();
				var spnId = $('#filterBySPN').val();
				var stateCd = $('#filterByState').val();
				var status = "";
				var providerFirmName = $('#searchByTypeVal1').val();
				var providerFirmNumber = $('#searchByTypeVal2').val();
				var marketId = $('#searchByTypeVal3').val();
				var zipCode = $('#searchByTypeVal4').val();
				var districtId = $('#searchByTypeVal5').val();
				//var status = $('#filterBySpnBuyer').val();
				clickViewBackgroundInformation(spnId,stateCd,status,providerFirmName,providerFirmNumber,marketId,zipCode,districtId);
			}
		else
		{
			$('#searchresults').show();
			$('#loadingbg').hide();
			submitSearch();
		}
		});
		
		
		$('#searchByType').change(function() {
			if ($(this).val() == "-1")
			{
				var bgStatusText = $('#memberStatus option:selected').text();
				var bgStatus = $.trim(bgStatusText);
				if('Background Check Status' == bgStatus){
					$('#searchresults').hide();
					var spnId = $('#filterBySPN').val();
					var stateCd = $('#filterByState').val();
					var status = "";
					var providerFirmName = $('#searchByTypeVal1').val();
					var providerFirmNumber = $('#searchByTypeVal2').val();
					var marketId = $('#searchByTypeVal3').val();
					var zipCode = $('#searchByTypeVal4').val();
					var districtId = $('#searchByTypeVal5').val();
					//var status = $('#filterBySpnBuyer').val();
					clickViewBackgroundInformation(spnId,stateCd,status,providerFirmName,providerFirmNumber,marketId,zipCode,districtId);
				}
				else
			{
				$('#searchresults').show();
				$('#loadingbg').hide();	
				submitSearch();
			}
				}
			
		});
		
		$('.filterSelect').change(function() {			
			var status = $('#filterByPFStatus option:selected').text();
			if('SPN Member' == status){
				$('#memberStatus').prop('disabled', false);
			
			}else{
				$('#memberStatus').val('-1');
				$('#memberStatus').attr('disabled', 'disabled');
			}

			var bgStatusText = $('#memberStatus option:selected').text();
			var bgStatus = $.trim(bgStatusText);
			if('Background Check Status' == bgStatus){
				$('#searchresults').hide();
				var spnId = $('#filterBySPN').val();
				var stateCd = $('#filterByState').val();
				var status = "";
				var providerFirmName = $('#searchByTypeVal1').val();
				var providerFirmNumber = $('#searchByTypeVal2').val();
				var marketId = $('#searchByTypeVal3').val();
				var zipCode = $('#searchByTypeVal4').val();
				var districtId = $('#searchByTypeVal5').val();
				//var status = $('#filterBySpnBuyer').val();
				clickViewBackgroundInformation(spnId,stateCd,status,providerFirmName,providerFirmNumber,marketId,zipCode,districtId);
		}
		else
		{
			$('#searchresults').show();
			$('#loadingbg').hide();
			submitSearch();
		}
			});

		
		$('#loadSPNSpinner').jqm({modal: true, toTop: true});
		
		$("input").keypress(function (e) {  
			if ((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)) {  
				var bgStatusText = $('#memberStatus option:selected').text();
				var bgStatus = $.trim(bgStatusText);
				if('Background Check Status' == bgStatus){
					$('#searchresults').hide();
					var spnId = $('#filterBySPN').val();
					var stateCd = $('#filterByState').val();
					var status = "";
					var providerFirmName = $('#searchByTypeVal1').val();
					var providerFirmNumber = $('#searchByTypeVal2').val();
					var marketId = $('#searchByTypeVal3').val();
					var zipCode = $('#searchByTypeVal4').val();
					var districtId = $('#searchByTypeVal5').val();
					//var status = $('#filterBySpnBuyer').val();
					clickViewBackgroundInformation(spnId,stateCd,status,providerFirmName,providerFirmNumber,marketId,zipCode,districtId);
			}
				else{
				submitSearch();
				}
			}
		}); 
		
		var bgStatusText = $('#memberStatus option:selected').text();
		var bgStatus = $.trim(bgStatusText);
		if('Background Check Status' == bgStatus){
	
		}
		else
		{	
			submitSearch();
		}
	
	});	
	
	function submitSearch()
	{
		jQuery(document).ready(function($) {
			var providerFirmName = $('#searchByTypeVal1').val();
			var providerFirmNumber = $('#searchByTypeVal2').val();
			var marketId = $('#searchByTypeVal3').val();
			var zipCode = $('#searchByTypeVal4').val();
			var districtId = $('#searchByTypeVal5').val();
			var viewAll = $('#searchByViewAll').val();
			
			var spnId = $('#filterBySPN').val();
			var stateCd = $('#filterByState').val();
			var pfMemershipStatus = $('#filterByPFStatus').val();
			var memberStatus = $('#memberStatus').val();
			
			var queryString = "searchCriteriaVO.providerFirmName=" + providerFirmName + "&" +
							"searchCriteriaVO.providerFirmNumber=" + providerFirmNumber + "&" +
							"searchCriteriaVO.marketId=" + marketId + "&" + 
							"searchCriteriaVO.zipCode=" + zipCode + "&" +
							"searchCriteriaVO.districtId=" + districtId + "&" +
							"searchCriteriaVO.spnId=" + spnId + "&" + 
							"searchCriteriaVO.stateCd=" + stateCd + "&" +
							"searchCriteriaVO.viewAll=" + viewAll;							
				
			$('#loadSPNSpinner').jqmShow();			
			$('#searchresults').load('spnAuditorSearchTab_searchSPNAjax.action?' + queryString, {'searchCriteriaVO.providerFirmStatus': pfMemershipStatus, 'searchCriteriaVO.memberStatus': memberStatus} , function() {
				$('#loadSPNSpinner').jqmHide();
			});
		});
	}
</script>

<input type="hidden" id="searchByViewAll" value="false" />

<table>
	<tr>
		<td>
			<label>
				Search by:
			</label>
		</td>
		<td class="paddr">
			<select name="searchByType" id="searchByType">
				<option value="-1">
					-Select One-
				</option>
				<option value="1">
					Provider Firm Name
				</option>
				<option value="2">
					Provider Firm Number
				</option>
				<option value="3">
					Market
				</option>
				<option value="4">
					Zip Code
				</option>
				<option value="5">
					District
				</option>
				</select>
		</td>
		<td>
			<input type="text" class="text searchByTypeVal" name="searchCriteriaVO.providerFirmName" id="searchByTypeVal1" style="display: none; width: 175px;" />
			<input type="text" class="text searchByTypeVal" name="searchCriteriaVO.providerFirmNumber" id="searchByTypeVal2" style="display: none; width: 175px;" />
			<s:select id="searchByTypeVal3" name="searchCriteriaVO.marketId" value="" list="marketList" listKey="id"
					listValue="description" headerKey="-1" headerValue="-Select One-" theme="simple" cssClass="searchByTypeVal" cssStyle="display: none;" />
			<input type="text" class="text searchByTypeVal" name="searchCriteriaVO.zipCode" maxlength="5" id="searchByTypeVal4" style="display: none; width: 175px;" />
			<s:select id="searchByTypeVal5" name="searchCriteriaVO.districtId" value="" list="districtList" listKey="id"
					listValue="description" headerKey="-1" headerValue="-Select One-" theme="simple" cssClass="searchByTypeVal" cssStyle="display: none;" />
			
		</td>
		<td style="padding-left: 5px;">
			<input type="submit" id="searchSubmit-1" class="button default searchSubmit" value="Go" style="display: none; margin: 0px;">
		</td>
	</tr>
	<tr>
		<td colspan="2">
			&nbsp;
		</td>
		<td>
			<em id="searchByTypeText1" class="searchByTypeText" style="display: none;"><small>Enter Provider Firm Name</small></em>
			<em id="searchByTypeText2" class="searchByTypeText" style="display: none;"><small>Enter Provider Firm Number</small></em>
			<em id="searchByTypeText3" class="searchByTypeText" style="display: none;"><small>Select a market from the list</small></em>
			<em id="searchByTypeText4" class="searchByTypeText" style="display: none;"><small>Enter 5-digit Zip Code</small></em>
		</td>
		<td>
			&nbsp;
		</td>
	</tr>
</table>