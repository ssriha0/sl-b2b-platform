function presetMultiSelect(myThis)
{
	var numSelected = myThis.parent('div').children('.select-options').children('div').children(':checked').length;
	if (numSelected > 0)
	{
		myThis.children('label').html(numSelected + ' Selected');
	}
	else
	{
		myThis.children('label').html('No Selection');
	}		
}

function openMultiSelect(myThis)
{
	var hidden = false;
	if (myThis.parent('div').children('.select-options:hidden').length > 0)
	{
		hidden = true;
	}
			
	$('.select-options').hide();

	
	if (hidden == true)
	{
		myThis.parent('div').children('.select-options').show();
		
		// This little hack was done because of IE z-index bug.  The alternative solution is 'iframe shim'
		if (document.getElementById('market_select_options') != null)
		{
			if( document.getElementById('market_select_options').style.display == 'block')
			{
				$('.minimum-ratings-dropdown').hide();
			}
		}
	}
	else
	{
		// This little hack was done because of IE z-index bug.  The alternative solution is 'iframe shim'
		if (document.getElementById('market_select_options') != null)
		{
			if( document.getElementById('market_select_options').style.display == 'none')
			{
				$('.minimum-ratings-dropdown').show();
			}	
		}
	}
}

function multiCheckboxClicked(myThis)
{
	if (myThis.parent('div').parent('div.select-options').length == 1)
	{
		var numSelected = myThis.parent('div').parent('div.select-options').children('div').children(':checked').length;
		if (numSelected > 0)
		{
			myThis.parent('div').parent('div').parent('div').children('.picked').children('label').html(numSelected + ' Selected');
		}
		else
		{
			myThis.parent('div').parent('div').parent('div').children('.picked').children('label').html('No Selection');
		}	
	}
}


function setupMultiSelect()
{
	$('body').unbind('click').click(function() {
		$('.select-options').hide();
	});
		 
	$('.select-options').unbind('click').click(function(event){
		event.stopPropagation();
	});
	
	$('.pickedClick').unbind('click').click(function(event) {
		event.stopPropagation();
		openMultiSelect($(this));	
	});
	
	$('.pickedClick').each(function() {
		presetMultiSelect($(this));	
	});
	
	$('[type=checkbox]').click(function() {
		multiCheckboxClicked($(this));
	});
}

function clickMainServiceLoadSkills(n_or_c)
{
	var x = 0;
	var queryString = '';

	$("input[name='approvalItems.selectedMainServices']:checked").each(function() {
		queryString = queryString + "&approvalItems.selectedMainServices=" + $(this).val();
	});

	$("input[name='approvalItems.selectedSkills']:checked").each(function() {
		queryString = queryString + "&approvalItems.selectedSkills=" + $(this).val();
	});
		
	if (queryString == '')
	{
		queryString = "approvalItems.selectedMainServices=";
	}			
		
	if (n_or_c == 1)
	{
		$('#servicesWithSkills').load('spnCreateNetwork_getMainServicesWithSkillsAjax.action?' + queryString, function() {
			$('#servicesWithSkills').children('div.select-options').hide();
		});
	}
	else if (n_or_c == 2)
	{
		$('#servicesWithSkills').load('spnCreateCampaign_getMainServicesWithSkillsAjax.action?' + queryString, function() {
			$('#servicesWithSkills').children('div.select-options').hide();
		});
	}
}

function clickMainServiceLoadCategories(n_or_c)
{

	var queryString = '';
	$("input[name='approvalItems.selectedMainServices']:checked").each(function() {
		queryString = queryString + "&approvalItems.selectedMainServices=" + $(this).val();
	});
			    
	$("input[name='approvalItems.selectedSubServices1']:checked").each(function() {
		queryString = queryString + "&approvalItems.selectedSubServices1=" + $(this).val();
	});
			    
	$("input[name='approvalItems.selectedSubServices2']:checked").each(function() {
		queryString = queryString + "&approvalItems.selectedSubServices2=" + $(this).val();
	});
			    
	if (n_or_c == 1)
	{
		$('#subCatDropDowns').load('spnCreateNetwork_getSubCategoriesAjax.action?' + queryString);
	}
	else if (n_or_c == 2)
	{		    
		$('#subCatDropDowns').load('spnCreateCampaign_getSubCategoriesAjax.action?' + queryString);
	}
}

function clickMainServiceNetwork()
{
	$("input[name='approvalItems.selectedMainServices']").click(function() {
		clickMainServiceLoadSkills(1);
	});
		
	$("input[name='approvalItems.selectedMainServices']").click(function() {
		clickMainServiceLoadCategories(1);	
	});
}

function clickMainServiceCampaign()
{
	$("input[name='approvalItems.selectedMainServices']").click(function() {
		clickMainServiceLoadSkills(2);
	});
		
	$("input[name='approvalItems.selectedMainServices']").click(function() {
		clickMainServiceLoadCategories(2);	
	});
}

function maskPhone(id)
{
	phoneVal = document.getElementById(id).value;
	phoneVal = phoneVal.replace(/-/g, "");
	phoneVal = phoneVal.substring(0, 10);
	phoneValNew = "";
				
	for (x = 0; x < phoneVal.length; x++)
	{
		c = phoneVal.charAt(x);
		if (x == 3 || x == 6)
		{ 
			phoneValNew = phoneValNew + "-"; 
		}
		phoneValNew = phoneValNew + c; 	
	}
				
	document.getElementById(id).value = phoneValNew;
}

function initShowHideCategories()
{
	$('.selectOptionsSub').hide();
		
	if ($('.pickedSubClick').length == 0)
	{
		$('.c-cat').hide();
		$('.c-cat span.min').show();
		$('.c-cat span.plus').hide();
	}
	else
	{
		$('.c-cat').show();
		$('.c-cat span.min').hide();
		$('.c-cat span.plus').show();
	}
}

function subCategoryClicked(myThis, n_or_c)
{
	var queryString = '';
	myThis.parent('div').parent('div').children('div').children(':checked').each(function() {
		queryString = queryString + "&approvalItems.selectedSubServices1=" + $(this).val();
	});
	
	if (queryString == '')
	{
		queryString = "&approvalItems.selectedSubServices1=-1";
	}
	
	myThis.parent('div').parent('div').parent('div.half').siblings('.subServices2').children('.half').children('.select-options').children('div').children(':checked').each(function() {
		queryString = queryString + "&approvalItems.selectedSubServices2=" + $(this).val();
	});
		
	if (n_or_c == 1)
	{	
		myThis.parent('div').parent('div').parent('div.half').siblings('.subServices2').load('spnCreateNetwork_getSub2CategoriesAjax.action?' + queryString);
	}
	else if (n_or_c == 2)
	{
		myThis.parent('div').parent('div').parent('div.half').siblings('.subServices2').load('spnCreateCampaign_getSub2CategoriesAjax.action?' + queryString);
	}
}

function setupSubCategoryNetwork()
{
	
	$("input[name='approvalItems.selectedSubServices1']").click(function() {
		subCategoryClicked($(this), 1);	
	});
}

function setupSubCategoryCampaign()
{
	$("input[name='approvalItems.selectedSubServices1']").click(function() {
		subCategoryClicked($(this), 2);	
	});
}

function initShowHideProviderCredentials()
{
	$('c-cred').hide();
	$('h5.collapse').click(function() {
		var theName = $(this).attr('title');
		if ($('div.' + theName).is(':hidden'))
		{
			$('div.' + theName).show();
		}
		else if ($('div.' + theName).is(':visible'))
		{
			$('div.' + theName).hide();
		}
	});
}

function clickProviderCredentials()
{

	$("input[name='approvalItems.selectedVendorCredTypes']").click(function() {
		clickCompanyCredentialsLoadSub();
	});
	
	$("input[name='approvalItems.selectedResCredTypes']").click(function() {
		clickResourceCredentialsLoadSub();
	});
}

function clickCompanyCredentialsLoadSub()
{
	
	var x = 0;
	var queryString = '';

	$("input[name='approvalItems.selectedVendorCredTypes']:checked").each(function() {
		queryString = queryString + "&approvalItems.selectedVendorCredTypes=" + this.value;
	});
			
	$("input[name='approvalItems.selectedVendorCredCategories']:checked").each(function() {
		queryString = queryString + "&approvalItems.selectedVendorCredCategories=" + this.value;
	});
	
	if (queryString == '')
	{
		queryString = "approvalItems.selectedVendorCredTypes=";
	}
			
	$('#vendorCredTypesWithCategories').load('spnCreateNetwork_getVendorCredTypesWithCategoriesAjax.action?' + queryString, function() {
		$('#vendorCredTypesWithCategories').children('div.select-options').hide();
	});
}

function clickResourceCredentialsLoadSub()
{
	var x = 0;
	var queryString = '';
	
	$("input[name='approvalItems.selectedResCredTypes']:checked").each(function() {
		queryString = queryString + "&approvalItems.selectedResCredTypes=" + this.value;
	});
	
	$("input[name='approvalItems.selectedResCredCategories']:checked").each(function() {
		queryString = queryString + "&approvalItems.selectedResCredCategories=" + this.value;
	});
	
	if (queryString == '')
	{
		queryString = "approvalItems.selectedResCredTypes=";
	}
			
	$('#resCredTypesWithCategories').load('spnCreateNetwork_getResCredTypesWithCategoriesAjax.action?' + queryString, function() {
		$('#resCredTypesWithCategories').children('div.select-options').hide();
	});
}

function initCreateNetworkShowHide()
{
	$('.toggle, .vchecktoggle, .wchecktoggle, .cchecktoggle, dl.collapse dd, span.open, div.filter-option, tr.info, a.dateedit, h3 span.min, .formtip, .select-options').hide();
		
	$('.c-general').show();
	$('.c-general span.min').show();
	$('.c-general span.plus').hide();

	$('h3.collapse').click(function() {
		var theName = $(this).attr('title');
		if ($('div.' + theName).is(':hidden'))
		{
			$('.toggle').hide();
			$('span.min').hide();	
			$('span.plus').show();	
			$(this).children('span.plus').hide();
			$(this).children('span.min').show();
			$('div.' + theName).show();
		}
		else if ($('div.' + theName).is(':visible'))
		{
			$('.toggle').hide();
			$('span.min').hide();
			$('span.plus').show();
		}
	});
	
	$('h5.c-cat').click(function() {
		if ($('h5.c-cat span.plus').is(':visible'))
		{
			$('#subCatDropDowns').hide();
		}
		else
		{
			$('#subCatDropDowns').show();
		}
		$('div.c-cat').toggle();
		$('h5.c-cat span.min').toggle();
		$('h5.c-cat span.plus').toggle();
	});

	$('dl.collapse dt').click(function() {
		$(this).parents("dl").removeClass("closed");
	},function(){
		$(this).parents("dl").addClass("closed");
	});		

	$('dl.collapse dt').click(function() {					
		$(this).parents("dl").children("dd").toggle();	
		$(this).children("span.open").toggle();		
		$(this).children("span.closed").toggle();	
	});		
		
	$('textarea.showtip').focus(function(){
		$(this).parents('div.tipwrap').children('div.formtip').show();
	});

	$('.showtip').blur(function(){
		$(this).parents('div.tipwrap').children('div.formtip').hide();
	});
}

function initCreateCampaignHideShow()
{
	$('.toggle, .vchecktoggle, .wchecktoggle, .cchecktoggle, dl.collapse dd, span.open, div.filter-option, tr.info, a.dateedit, h3 span.min, .formtip, .select-options').hide();
		
	$('dl.collapse dt').click(function() {
		$(this).parents("dl").removeClass("closed");
	},function(){
		$(this).parents("dl").addClass("closed");
	});		
		
	$('dl.collapse dt').click(function() {					
		$(this).parents("dl").children("dd").toggle();	
		$(this).children("span.open").toggle();		
		$(this).children("span.closed").toggle();	
	});
		
	$('textarea.showtip').focus(function(){
		$(this).parents('div.tipwrap').children('div.formtip').show();
	});
		
	$('.showtip').blur(function(){
		$(this).parents('div.tipwrap').children('div.formtip').hide();
	});
}

function loadCampaign(actionName, queryString)
{
	$('#campaignCriterias').load('spnCreateCampaign_loadSelectedCampaignDataAjax.action?' + queryString);
	$('#spnSelectList').load('spnCreateCampaign_loadSpnListDataAjax.action?' + queryString);				
	$('#rightSideData').load('spnCreateCampaign_updateRightSideAjax.action?' + queryString);
}

function loadPanelNewCampaignHideShow()
{
	$('#subCatArea, .toggle, .vchecktoggle, .wchecktoggle, .cchecktoggle, dl.collapse dd, span.open, div.filter-option, tr.info, a.dateedit, h3 span.min, .formtip, .select-options').hide();
	$('.c-general').show();
	$('.c-general span.min').show();
	$('.c-general span.plus').hide();
	$('.c-saved span.min').hide();
	$('.c-saved span.plus').show();
	$('#startDate').datepicker({dateFormat:'yy-mm-dd', minDate: new Date() });
	$('#endDate').datepicker({dateFormat:'yy-mm-dd', minDate: new Date() });
		
	$('h3.collapse').click(function() {
		var theName = $(this).attr('title');
		if ($('div.' + theName).is(':hidden'))
		{
			$('.toggle').hide();
			$('span.min').hide();	
			$('span.plus').show();	
			$(this).children('span.plus').hide();
			$(this).children('span.min').show();
			$('div.' + theName).show();
		}
		else if ($('div.' + theName).is(':visible'))
		{
			$('.toggle').hide();
			$('span.min').hide();
			$('span.plus').show();
		}
	});
		
	$('input.vshcheck').click(function() {
		$('.vchecktoggle').toggle();				
	});


	$('input.wshcheck').click(function() {
		$('.wchecktoggle').toggle();				
	});

	$('input.cshcheck').click(function() {
		$('.cchecktoggle').toggle();				
	});
}

function toggleDiv(divID)
{
	var doc = document.getElementsByTagName('div');
	for (var i = 0; i < doc.length; i=i+1)
	{
		if(doc[i].id == divID.toString())
		{
	   		if(doc[i].style.display == "block")
	   			doc[i].style.display = "none";
	   		else
	   			doc[i].style.display = "block";
	   	}
	}
}	
	
function toggleAllStates()
{
	toggleDiv('states');
	toggleDiv('states2');
}
	
function toggleAllMarkets()
{
	toggleDiv('markets');
	toggleDiv('markets2');
}

function updateCampaignCount()
{
	$('#buttonUpdateCampaignCount').unbind('click').click(function() {
		var queryString = '';

		$("input[name='approvalItems.selectedMarkets']:checked").each(function() {
			queryString = queryString + '&' + 'approvalItems.selectedMarkets=' + $(this).val();
		});

		$("input[name='approvalItems.selectedStates']:checked").each(function() {
			queryString = queryString + '&' + 'approvalItems.selectedStates=' + $(this).val();
		});

		$("input[name='approvalItems.selectedLanguages']:checked").each(function() {
			queryString = queryString + '&' + 'approvalItems.selectedLanguages=' + $(this).val();
		});
		queryString = queryString + '&' + 'approvalItems.selectedMinimumRating=' + 
						document.getElementById('approvalItems.selectedMinimumRating').value;	
		queryString = queryString + '&' + 'approvalItems.minimumCompletedServiceOrders=' + 
						document.getElementById('approvalItems.minimumCompletedServiceOrders').value;
		queryString = queryString + '&' + 'approvalItems.vehicleLiabilitySelected=' + 
						document.getElementById('approvalItems.vehicleLiabilitySelected').checked;
		queryString = queryString + '&' + 'approvalItems.vehicleLiabilityAmt=' + 
						document.getElementById('approvalItems.vehicleLiabilityAmt').value;
		queryString = queryString + '&' + 'approvalItems.vehicleLiabilityVerified=' + 
						document.getElementById('approvalItems.vehicleLiabilityVerified').checked;
		queryString = queryString + '&' + 'approvalItems.workersCompensationSelected=' + 
						document.getElementById('approvalItems.workersCompensationSelected').checked;
		queryString = queryString + '&' + 'approvalItems.workersCompensationVerified=' + 
						document.getElementById('approvalItems.workersCompensationVerified').checked;
		queryString = queryString + '&' + 'approvalItems.commercialGeneralLiabilitySelected=' + 
						document.getElementById('approvalItems.commercialGeneralLiabilitySelected').checked;
		queryString = queryString + '&' + 'approvalItems.commercialGeneralLiabilityAmt=' + 
						document.getElementById('approvalItems.commercialGeneralLiabilityAmt').value;
		queryString = queryString + '&' + 'approvalItems.commercialGeneralLiabilityVerified=' + 
						document.getElementById('approvalItems.commercialGeneralLiabilityVerified').checked;
		queryString = queryString + '&' + 'approvalItems.selectedCompanySize=' + 
						document.getElementById('approvalItems.selectedCompanySize').value;
		queryString = queryString + '&' + 'approvalItems.selectedSalesVolume=' + 
						document.getElementById('approvalItems.selectedSalesVolume').value;
		queryString = queryString + '&' + 'approvalItems.isNotRated=' + 
						document.getElementById('approvalItems.isNotRated').checked;
		queryString = queryString + '&' + 'approvalItems.isAllMarketsSelected=' + 
						document.getElementById('approvalItems.isAllMarketsSelected').checked;
		queryString = queryString + '&' + 'approvalItems.isAllStatesSelected=' + 
						document.getElementById('approvalItems.isAllStatesSelected').checked;

		$("input[name='approvalItems.selectedVendorCredTypes']:checked").each(function() {
			queryString = queryString + '&' + 'approvalItems.selectedVendorCredTypes=' + $(this).val();
		});
		$("input[name='approvalItems.selectedVendorCredCategories']:checked").each(function() {
			queryString = queryString + '&' + 'approvalItems.selectedVendorCredCategories=' + $(this).val();
		});
		$("input[name='approvalItems.selectedResCredTypes']:checked").each(function() {
			queryString = queryString + '&' + 'approvalItems.selectedResCredTypes=' + $(this).val();
		});
		$("input[name='approvalItems.selectedResCredCategories']:checked").each(function() {
			queryString = queryString + '&' + 'approvalItems.selectedResCredCategories=' + $(this).val();
		});
		$("input[name='approvalItems.selectedMainServices']:checked").each(function() {
			queryString = queryString + '&' + 'approvalItems.selectedMainServices=' + $(this).val();
		});
		$("input[name='approvalItems.selectedSkills']:checked").each(function() {
			queryString = queryString + '&' + 'approvalItems.selectedSkills=' + $(this).val();
		});
		$("input[name='approvalItems.selectedSubServices1']:checked").each(function() {
			queryString = queryString + '&' + 'approvalItems.selectedSubServices1=' + $(this).val();
		});
		$("input[name='approvalItems.selectedSubServices2']:checked").each(function() {
			queryString = queryString + '&' + 'approvalItems.selectedSubServices2=' + $(this).val();
		});
		//R10.3 SL-19812 Introduce Primary Industry criteria for Campaign
		//Add primary industry criteria to query string for firm count
		$("input[name='approvalItems.selectedPrimaryIndustry']:checked").each(function() {
			queryString = queryString + '&' + 'approvalItems.selectedPrimaryIndustry=' + $(this).val();
		});
		
		document.getElementById('buttonUpdateCampaignCount').value='Loading...';
		$('#provider_count_match_campaign').load('spnCreateCampaign_getCampaignCountsAjax.action?' + queryString);
	});
}
