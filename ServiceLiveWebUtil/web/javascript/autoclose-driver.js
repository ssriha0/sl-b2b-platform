

jQuery(document).ready(function () {
	//corrects the max price to 2 decimal places
	$('input#maxPrice').blur(function() {
	      if(!isNaN(this.value) & this.value != ''){
	          var amt = parseFloat(this.value);
	          var changeAmt = amt.toFixed(0);
	          var finalAmt = parseFloat(changeAmt).toFixed(2);
	             $(this).val(finalAmt);//alert(finalAmt);
	         }          
	});
	
	//ensures only numbers are entered
	$('input#maxPrice').keypress(function (e){
  			//if the letter is not digit then display error and don't type anything
  			if( e.which!=8 && e.which!=0 && e.which!=46 && (e.which<48 || e.which>57))
  			{
   				return false;
 			}
	});
	
	
	//ensures only numbers are entered
	jQuery('#autocloseSearchTextId').keyup(function () {
		if($('#autocloseSearchBy > option:selected').val() == 2 || $('#autocloseSearchBy > option:selected').val() ==  4 ){
			if(this.value != ''){
			   this.value = this.value.replace(/[^0-9\;]/g,'');
			}
		}
		if($('#autocloseSearchBy > option:selected').val() == 1 || $('#autocloseSearchBy > option:selected').val() ==  3 ){
			if(this.value != ''){
			   this.value = this.value.replace(/[^A-Za-z0-9\s]/g,'');
			}
		}	
	});	

	//prefills the text area based on where it is-either search results or exclusion list
	preFillTextarea = function(isSearchResult){
		var removeText = 'Enter reason for removing from the exclusion list';
		var addText = 'Enter reason for adding to the exclusion list';	
		var resultText = 'Enter reason for excluding from auto close.';
		var prefilTxt = '';
		if(isSearchResult){
			prefilTxt = resultText;
		}else{
			prefilTxt = $('#listedIdLink').hasClass('disabled') ? removeText : addText;
		}

		return prefilTxt;
	}
	//called when update amount link is clicked
	$("#updatePrice").click(function(){
		//validates and persists the data in db
		$('#autocloseSearchValidationError').hide();
		var isError = false;
		
		if(isNaN($("#maxPrice").val()) & !isError){
			errorText = "Please enter a valid maximum price limit to apply this rule.";
			isError = true;
		}
	
		if(($.trim($("#maxPrice").val())=='') & !isError){
			errorText = "Please enter a maximum price limit to apply this rule.";
			$("#successPriceUpdate").hide();
			$('#autocloseExcludeValidationError').hide();
			$("#successMsg").hide();
			isError = true;
		}
	
		if(isError){
			$('#autocloseSearchValidationError > .errorText').html(errorText);
			$('#autocloseSearchValidationError').show();		
			return false;
		}
		var criteriaId = $("#criteriaId").val();
		var maxPrice = $("#maxPrice").val();
		var queryString="";

		queryString += "criteriaId=" + criteriaId + "&changedPrice=" + maxPrice;

		$('#layer1_content_body').load('autoClose_updateMaxprice.action?'+queryString,function(){
		
		var oldPriceCol = $('#layer1_content_body tr td')[3];
		if(oldPriceCol == undefined)
		{var oldPrice = '$1000.00';}
		else
		{var oldPrice = $(oldPriceCol).html();}

		$("#viewHistoryLink").show();// from $xx.xx to $xx.xx
		$("#successPriceUpdate").html("The <B>Max Price</B> to auto close an order has been <B>Updated</B> from <B>"+oldPrice+"</B> to <B>$"+$('#maxPrice').val()+"</B>");
		$("#successPriceUpdate").show();
		
		if($("#successPriceUpdate").show())
		{
		$('#autocloseExcludeValidationError').hide();
		$("#successMsg").hide();
		}
		
		});
		
	});
	
	$("#layer1").css('visibility','hidden');
	//.hide();
	
		
	$(document).click(function(e){ 
		var click=$(e.target);
		if(click.parents().is("#layer1")){
			e.stopPropagation();
		}else if(click.closest("div").hasClass("comHistLayer")){
			e.stopPropagation();
		}else{
			$("#layer1").css('visibility','hidden');
			$(".comHistLayer:visible").hide();
		}
	});
	
	$('#viewHistoryLink').click(function(event)
	{
	var posLeft = $(this).width() + (1.5*$(this).offset().left);
	var posTop = ($(this).offset().top -50);
		$("#layer1").css('top',posTop);	
		$("#layer1").css('left',posLeft);
		$("#layer1").css('visibility','visible');
		event.stopPropagation();
	});

	
	/*$('#viewHistoryLink').blur(function(e)
	{
		$("#layer1").css('visibility','hidden');
	});*/


	$('#close').click(function()
	{
		$("#layer1").css('visibility','hidden');
		//$("#layer1").hide();
	});
	
	$('#listedIdLink').addClass('disabled');
	$('#firmsIdLink').addClass('disabled');
	//$("#successMsg").hide();
	var txt = preFillTextarea(false);

	$('.shadowBox').val(txt);

	function loadTableData(displayCount){
	var queryString="";
	var exclusionId = "";
	var removedInd = "";
	//var displayCount = "30";	
	
		if($('#firmsIdLink').hasClass('disabled') && $('#listedIdLink').hasClass('disabled')){
		
			$('#listHead').text("Provider Firm Exclusion List");
			exclusionId = $('#firmRuleHdrId').val();
			removedInd = 0;
			queryString += "exclusionId=" + exclusionId + "&removedInd=" + removedInd +"&displayCount="+displayCount;
			$('#loadSearchSpinner').jqmShow();
			$('#exclusionList').load('autoClose_getFirmExclusionList.action?'+queryString,function(){
				$('#loadSearchSpinner').jqmHide();
			});
		}
		if($('#firmsIdLink').hasClass('disabled') && $('#removedIdLink').hasClass('disabled')){
			$('#listHead').text("Provider Firms Removed from Exclusion List");
			exclusionId = $('#firmRuleHdrId').val();
			removedInd = 1;
			queryString += "exclusionId=" + exclusionId + "&removedInd=" + removedInd +"&displayCount="+displayCount;
			$('#loadSearchSpinner').jqmShow();			
			$('#exclusionList').load('autoClose_getFirmExclusionList.action?'+queryString,function(){
				$('#loadSearchSpinner').jqmHide();
			});		
		}
		if($('#providersIdLink').hasClass('disabled') && $('#listedIdLink').hasClass('disabled')){
			$('#listHead').text("Provider Exclusion List");
			exclusionId = $('#proRuleHdrId').val();
			removedInd = 0;
			queryString += "exclusionId=" + exclusionId + "&removedInd=" + removedInd +"&displayCount="+displayCount;
			$('#loadSearchSpinner').jqmShow();			
			$('#exclusionList').load('autoClose_getProviderExclusionList.action?'+queryString,function(){
				$('#loadSearchSpinner').jqmHide();
			});	
		}
		if($('#providersIdLink').hasClass('disabled') && $('#removedIdLink').hasClass('disabled')){
			$('#listHead').text("Providers Removed from Exclusion List");
			exclusionId = $('#proRuleHdrId').val();
			removedInd = 1;
			queryString += "exclusionId=" + exclusionId + "&removedInd=" + removedInd +"&displayCount="+displayCount;
			$('#loadSearchSpinner').jqmShow();			
			$('#exclusionList').load('autoClose_getProviderExclusionList.action?'+queryString,function(){
				$('#loadSearchSpinner').jqmHide();
			});			
		}
	}
	
	$('#prevList').click(function(){$('#listedIdLink').click();});
	$('#nextList').click(function(){$('#removedIdLink').click();});
	$('#prevType').click(function(){$('#firmsIdLink').click();});
	$('#nextType').click(function(){$('#providersIdLink').click();});
				
	$('#listedIdLink').click(function()
	{
		if($('#listedIdLink').hasClass('disabled')){return false;}
		$("#successMsg").hide();
		$('#listedIdLink').addClass('disabled');
		$('#listedIdLink').next().removeClass('disabled');
		loadTableData('30');
		
	});
	$('#removedIdLink').click(function()
	{
		if($('#removedIdLink').hasClass('disabled')){return false;}
		$("#successMsg").hide();
		$('#removedIdLink').addClass('disabled');
		$('#removedIdLink').prev().removeClass('disabled');
		loadTableData('30');
	});
	$('#firmsIdLink').click(function()
	{
		if($('#firmsIdLink').hasClass('disabled')){return false;}
		$("#successMsg").hide();
		$('#firmsIdLink').addClass('disabled');
		$('#firmsIdLink').next().removeClass('disabled');
		loadTableData('30');
	});
	$('#providersIdLink').click(function()
	{
		if($('#providersIdLink').hasClass('disabled')){return false;}
		$("#successMsg").hide();
		$('#providersIdLink').addClass('disabled');
		$('#providersIdLink').prev().removeClass('disabled');
		loadTableData('30');
	});
	
	$('#viewNext').click(function()
	{
		if($('#viewNext').hasClass('disabled')){return false;}
		$("#successMsg").hide();
		loadTableData($(".scrollerTableRow").length + 30);
	});
	
	$('#viewAll').click(function()
	{
		if($('#viewAll').hasClass('disabled')){return false;}
		$("#successMsg").hide();
		loadTableData(-1);
	});			
	
	
	
	$('.shadowBox').focusin(function()//.focus(function()
	{
		var obj = $(this);
		var txt = '';
		if($(this).closest('tr').prop('id').indexOf('res') == -1){
			txt = preFillTextarea(false);
		}else{
			txt = preFillTextarea(true);
		}
		if(obj.val() == txt){
			obj.val('');
		}
	});
	
	$('.shadowBox').focusout(function()//.blur(function()
	{
		var obj = $(this);
		if(obj.val() == ''){
			var txt = '';
			if($(this).closest('tr').prop('id').indexOf('res') == -1){
				txt = preFillTextarea(false);
			}else{
				txt = preFillTextarea(true);
			}
			obj.val(txt);
		}
	});

	//Search panel functions
	
		$('#autocloseSearchBy').change(function(){
		if ($('#autocloseSearchBy > option:selected').val() >0){
			var texttoappend = $('#autocloseSearchBy > option:selected').text() + ', minimum 3 chars';
			if($('#autocloseSearchBy > option:selected').val() == 1 || $('#autocloseSearchBy > option:selected').val() ==  3 ){
				texttoappend = texttoappend.replace('Name','First or Last Name');
			}
			if($('#autocloseSearchBy > option:selected').val() == 2 || $('#autocloseSearchBy > option:selected').val() ==  4 ){
				texttoappend = 'multiples 	separated by ; up to 10';
			}
			$('#autocloseSearchInput > label >span').html(texttoappend);
		//	$('#spnMMSearchInput > input' ).prop('id','spnMMSearchText' + $('#spnMMSearchBy > option:selected').val());
			$('#autocloseSearchInput > input' ).prop('value','');

			$('#autocloseSearchInput > label').show();
		}else{
			$('#autocloseSearchInput > label').hide();
		}
	});

	if ($('#searchByType').val() != "-1") {
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
		if ($(this).val() == "3") {
			$(idName).val('-1');
		}
		$('.searchByTypeVal').hide();
		$(idName).show();
		$('.searchByTypeText').hide();
		$(idText).show();
		$('.searchSubmit').show();
		$(searchSubmit).hide();
	});

	function clearFilters() {
		$('.filterSelect').val("-1");
		$('#searchresults').html('');
		$('#successPriceUpdate').hide();
		$('#autocloseExcludeValidationError').hide();
		$("#successMsg").hide();
		
	}
	$('#searchSubmit').click(function(){
		clearFilters();
		inHomeSubmitSearch();
	});
	
	/*$('#inHomeSearchSubmit-1').click(function(){
		clearFilters();
		inHomeSubmitSearch();
	});*/

	$('#searchByType').change(function() {
		if ($(this).val() == "-1") {
			inHomeSubmitSearch();
		}
	});

	$('#loadSearchSpinner').jqm( {
		modal : true,
		toTop : true
	});

	//enable submit on Enter
	$("input").keypress(function(e) {
		if ((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)) {
			clearFilters();
			inHomeSubmitSearch();
		}
	});

	//upon loading page, submit search if the "search by" fields contain values (e.g., user clicked back button)
	if(($('#autocloseSearchBy').length && $('#autocloseSearchInput').length) /* check if fields exist */
			&& $('#autocloseSearchBy > option:selected').val() != -1
				&& $('#autocloseSearchInput > input').val() != "") {
		inHomeSubmitSearch();
	}
	
	$(".plus").click(function(){
		var posLeft = $(this).width() + (1.1*$(this).offset().left);
		var posTop = ($(this).offset().top - 50);
		$(".comLayer").css('top',posTop);	
		$(".comLayer").css('left',posLeft);	
		$(this).next(".comLayer").show();
	});           

	$(".plus").blur(function(){
		$(this).next(".comLayer").hide();
	});
	
	$(".viewComHisLink").click(function(){
		var posLeft = $(this).width() + (1.05*$(this).offset().left);
		var posTop = ($(this).offset().top - 0);
		$(".comHistLayer").css('top',posTop);	
		$(".comHistLayer").css('left',posLeft);	
		$(this).next(".comHistLayer").show();
	});           

	/*$(".viewComHisLink").live('blur', function(){
		$(this).next(".comHistLayer").hide();
	});*/	

});

function openFirm(vendorId){
		
		if (document.openProvURL != null)
		{
			document.openProvURL.close();
		}
		var url = "/MarketFrontend/providerProfileFirmInfoAction_execute.action?vendorId=" + vendorId + "&popup=true";
		newwindow=window.open(url,'_publicproviderprofile','resizable=yes,scrollbars=yes,status=no,height=700,width=1000');
		if (window.focus) {newwindow.focus()}
		document.openProvURL = newwindow;
}

function openProvider(resourceid,companyid){
	
	if (document.openProvURL != null)
	{
		document.openProvURL.close();
	}
	var url = "/MarketFrontend/providerProfileInfoAction_execute.action?resourceId="+resourceid+ "&companyId="+ companyid+"&popup=true";
	newwindow=window.open(url,'_publicproviderprofile','resizable=yes,scrollbars=yes,status=no,height=700,width=1000');
	if (window.focus) {newwindow.focus()}
	document.openProvURL = newwindow;
}

function showCommentHistory(index){
	jQuery("#comHistLayer"+index).css('display','block');
}

//To check maxlength of textarea
function CheckMaxLength(Object, MaxLen){
	jQuery(document).ready(function($){
		if(Object.value.length > MaxLen-1)
		{      
		  Object.value = Object.value.substring(0, MaxLen-1);
		}
	});		
}

//gets the comments were checkboxes are checked in the search results 
function getCommentsArrayId(checkList){
  var idList = new Array();
  //find all ids from array of check box values  
  jQuery("textarea[name=" + checkList + "]").each
  (function(){
  	var txt = getPreFillTextarea(true);
  	if(($(this).val() != txt) && ($.trim($(this).val()) != '')){
		idList.push($(this).val().replace(',','~*#'));	    			    	
  	}
  });
  return idList;//.serializeArray();
}	

//gets the ids of the check boxes selected in the search results  
function getIdsToRemove(checkList){
  var idList = new Array();
  var ctr = 0;
  //find all ids from array of check box values  
  jQuery("input[name=" + checkList + "]:checked").each
  (
    function()
    {
      //fill the array with the values -- [ctr] = 
      if($(this).val().indexOf('resFirm') != -1 ){
      	idList.push(new String($(this).val().replace('resFirm','')));	        
      }
      if($(this).val().indexOf('resProv') != -1 ){
      	idList.push(new String($(this).val().replace('resProv','')));	        
      }	        

    }
  );
  return idList;//.serializeArray();
}

/* This function is called when 'Remove from Exclusion List', 
	'Add to Exclusion List' and 'Add selected' button is clicked */
	
function updateExclusionList(obj){

	$("#successMsg").hide();
	$('#autocloseExcludeValidationError').hide();
	$('#autocloseSearchValidationError').hide();
	$('#successPriceUpdate').hide();	
	var queryString="";
	var ruleAssocId = 0;
	var removedAssocInd = 0;
	var exclusionId = "";
	var	commentsArray = new Array();
	var firmIdsArray = new Array();
	var provIdsArray = new Array();
	var myArray = new Array();
		if(obj.id.indexOf('Firm') != -1){
			exclusionId = $('#firmRuleHdrId').val();		
			if(obj.id.indexOf('res') != -1){
				firmIdsArray = getIdsToRemove('firmChk');
				commentsArray = getCommentsArrayId('commentresFirm');
				if(firmIdsArray.length == 0){
					$('#autocloseSearchValidationError > .errorText').html('Please select a search result to add.');
					$('#autocloseSearchValidationError').show();					
					return;
				}
				if((commentsArray.length == 0) || (commentsArray.length != firmIdsArray.length)){
					$('#autocloseSearchValidationError > .errorText').html('Please enter the reason for excluding from auto close.');
					$('#autocloseSearchValidationError').show();
					return;
				}
				
			}else{
				if(obj.id.indexOf('remove') != -1){
					ruleAssocId = obj.id.replace('removeFirm','');
					removedAssocInd = 1;
				}else{
					ruleAssocId = obj.id.replace('addFirm','');
					removedAssocInd = 0;			
				}
				if(($.trim($('#comment'+ruleAssocId).val())!='') && ($.trim($('#comment'+ruleAssocId).val())!=getPreFillTextarea(false)) ){
					commentsArray.push($('#comment'+ruleAssocId).val());
				}
				if((commentsArray.length == 0)){
					$('#autocloseExcludeValidationError > .errorText').html('Please'+getPreFillTextarea(false).replace('Enter',' enter')+'.');
					$('#autocloseExcludeValidationError').show();
					return;
				}				

			}
			var encodedAction = encodeURI('autoClose_updateFirmExclusionList.action?idsToAdd='+firmIdsArray);//+'&comment='+commentsArray);
			$('#loadSearchSpinner').jqmShow();
			$('#exclusionList').load(encodedAction,
			{
				'ruleAssocId':ruleAssocId,
				//'idsToAdd':firmIdsArray, 
				'removedAssocInd':removedAssocInd, 
				'exclusionId':exclusionId,
				'comment':encodeURIComponent(commentsArray)
			},
			function(){
				//removing results msg and add selected button if only all the results are added
				if($('[id*="chkresFirm"]').length == firmIdsArray.length){
					$('#firmSearchResults').html('');
				}else{//removing the selected results one by one after adding
					for(var ctr=0;ctr<firmIdsArray.length;ctr++){
						$("#txtresFirm"+firmIdsArray[ctr]).remove();
						$("#chkresFirm"+firmIdsArray[ctr]).remove();
					}				
				}
							
				if(removedAssocInd == 0){
					$("#successMsg").html('The selected <B>Provider Firm</B> has been <B>added</B> to the Auto Close Exclusion List.');						
				}else{
					$("#successMsg").html('The selected <B>Provider Firm</B> has been <B>Removed</B> from the Auto Close Exclusion List.');						
				}$('#loadSearchSpinner').jqmHide();
				$("#successMsg").show();
			});			
		}

		if(obj.id.indexOf('Prov') != -1){
			exclusionId = $('#proRuleHdrId').val();		
			if(obj.id.indexOf('res') != -1){
				provIdsArray = getIdsToRemove('provChk');
				commentsArray = getCommentsArrayId('commentresProv');
				if(provIdsArray.length == 0){
					$('#autocloseSearchValidationError > .errorText').html('Please select a search result to add.');
					$('#autocloseSearchValidationError').show();					
					return;
				}
				if((commentsArray.length == 0) || (commentsArray.length != provIdsArray.length)){
					$('#autocloseSearchValidationError > .errorText').html('Please enter the reason for excluding from auto close.');
					$('#autocloseSearchValidationError').show();
					return;
				}				
			}else{
				if(obj.id.indexOf('remove') != -1){
					ruleAssocId = obj.id.replace('removeProv','');
					removedAssocInd = 1;
				}else{
					ruleAssocId = obj.id.replace('addProv','');
					removedAssocInd = 0;
				}
				if(($.trim($('#comment'+ruleAssocId).val())!='') && ($.trim($('#comment'+ruleAssocId).val())!=getPreFillTextarea(false))){
					commentsArray.push($('#comment'+ruleAssocId).val());
				}				
				if((commentsArray.length == 0)){
					$('#autocloseExcludeValidationError > .errorText').html('Please'+getPreFillTextarea(false).replace('Enter',' enter')+'.');
					$('#autocloseExcludeValidationError').show();
					$('#successPriceUpdate').hide();
					
					return;
				}					
			}
			var encodedAction = encodeURI('autoClose_updateProviderExclusionList.action?idsToAdd='+provIdsArray);//+'&comment='+commentsArray);
			$('#loadSearchSpinner').jqmShow();
			$('#exclusionList').load(encodedAction,//+'&comment='+commentsArray,
			{
				'ruleAssocId':ruleAssocId,
				//'idsToAdd':provIdsArray, 
				'removedAssocInd':removedAssocInd, 
				'exclusionId':exclusionId,
				'comment':encodeURIComponent(commentsArray)
			},
			function(){
				//removing results msg and add selected button if only all the results are added
				if($('[id*="chkresProv"]').length == provIdsArray.length){
					$('#provSearchResults').html('');
				}else{//removing the selected results one by one after adding
					for(var ctr=0;ctr<provIdsArray.length;ctr++){
						$("#txtresProv"+provIdsArray[ctr]).remove();
						$("#chkresProv"+provIdsArray[ctr]).remove();
					}				
				}			

				if(removedAssocInd == 0){
					$("#successMsg").html('The selected <B>Provider</B> has been <B>added</B> to the Auto Close Exclusion List.');						
				}else{
					$("#successMsg").html('The selected <B>Provider</B> has been <B>Removed</B> from the Auto Close Exclusion List.');						
				}$('#loadSearchSpinner').jqmHide();
				$("#successMsg").show();				
			});				
		}
}

//prefills the text area based on where it is-either search results or exclusion list
function getPreFillTextarea(isSearchResult){
	var removeText = 'Enter reason for removing from the exclusion list';
	var addText = 'Enter reason for adding to the exclusion list';	
	var resultText = 'Enter reason for excluding from auto close.';
	var prefilTxt = '';
	if(isSearchResult){
		prefilTxt = resultText;
	}else{
		prefilTxt = $('#listedIdLink').hasClass('disabled') ? removeText : addText;
	}

	return prefilTxt;
}

function autoCloseCheck(obj){
	if($(obj).is(':checked')){
		$('#txt'+$(obj).val()).show();
	}else{
		var txt = '';
		if($(obj).val().indexOf('res') == -1){
			txt = getPreFillTextarea(false);
		}else{
			txt = getPreFillTextarea(true);
		}
		$('#txt'+$(obj).val()).hide();
		if($(obj).prop('name') == ''){
			$('#comment'+$(obj).val()).val(txt);
		}else if($(obj).prop('name') == 'firmChk'){
			$('#commentresFirm'+$(obj).val()).val(txt);
		}else if($(obj).prop('name') == 'provChk'){
			$('#commentresProv'+$(obj).val()).val(txt);
		}
	}

}

function inHomeSubmitSearch() {
	jQuery(document).ready(function($){

		var runSearch = function(){
			//run search and display results; call this after validation

			var searchType = $('#autocloseSearchBy').val();
			var textId = '#autocloseSearchTextId';
			var textValue = $.trim($(textId).val());//alert('textValue:'+textValue);
			//var spnId = $('#spnList').val();alert('spnId:'+spnId);
			//var stateCd = $('#stateList').val();alert('stateCd:'+stateCd);
			//if(stateCd == 'undefined' || stateCd == null) stateCd = -1;
			var queryString = "";
			if(searchType == 1) {
				queryString += "serviceProviderName="	+ textValue	+ "&";
			}
			else if (searchType == 2){
				queryString += "serviceProviderIdsStr=" + textValue + "&";
			}
			else if (searchType == 3) {
			 	queryString += "providerFirmName="	+ textValue	+ "&";
			}
			else if (searchType  == 4) {
				queryString += "providerFirmIdsStr=" + textValue + "&";
			}
			
			//queryString += "searchCriteriaVO.filterCriteria.spnId=" + 1	+ "&" + "searchCriteriaVO.filterCriteria.stateCode=";
			queryString += "isViewAll="+ 3 + "&"	+ "searchByType=" + searchType;
			var encodedAction = encodeURI('autoClose_searchMembers.action?' + queryString);

			$('#loadSearchSpinner').jqmShow();
				$('#searchresults').load(encodedAction,function(){
				//loginPage set in login.jsp
				/*if(typeof loginPage == 'boolean' && loginPage == true){
					top.location = "/spn/spnLoginAction_display.action"; //prevent the login page from appearing in a div
				}*/
					$('#loadSearchSpinner').jqmHide();
					$('#autocloseSearchResultsTable').tablesorter({headers:{2:{sorter:false}, 3:{sorter:false}}});
					$('#autocloseSearchBy').css('background','#fff');
					$('#autocloseSearchInput > input').css('background','#fff');
				});
		}

				//input validation
			var maxIDs = 10;
			var msgValidCriteria = "Please enter a minimum of three characters to search.";
			var msgNoCriteria = "Please enter a search term to search.";			
			var msgIdLimit = "Please provide up to " + maxIDs + " IDs.";
			var msgNumbersOnly = "Please use numeric IDs only, separated by semicolons (;)";
			var msgValidOnly = "Please enter valid IDs only, separated by semicolons (;)";
			var msgSelectCtgry = "Please select an option to search by.";

				if($('#autocloseSearchBy > option:selected').val() == -1){

					$('#autocloseSearchValidationError > .errorText').html(msgSelectCtgry);
					$('#autocloseSearchValidationError').show();
					$('#autocloseSearchBy').focus();
					$('#autocloseSearchBy').css('background','#fff8bf');
				} else if($.trim($('#autocloseSearchInput > input').val()).length == 0){
					$('#autocloseSearchValidationError').hide();
					$('#autocloseSearchValidationError > .errorText').html(msgNoCriteria);
					$('#autocloseSearchValidationError').show();
					$('#autocloseSearchInput > input').focus();
					$('#autocloseSearchInput > input').css('background','#fff8bf');
					$('#autocloseSearchBy').css('background','#fff');
				}else if($('#autocloseSearchInput > input').val().length < 3){
					$('#autocloseSearchValidationError').hide();
					$('#autocloseSearchValidationError > .errorText').html(msgValidCriteria);
					$('#autocloseSearchValidationError').show();
					$('#autocloseSearchInput > input').focus();
					$('#autocloseSearchInput > input').css('background','#fff8bf');
					$('#autocloseSearchBy').css('background','#fff');

				}else if ($('#autocloseSearchBy > option:selected').val() == 2 || $('#autocloseSearchBy > option:selected').val() == 4){
					//check IDs
					$('#autocloseSearchValidationError').hide();
					$('#autocloseSearchInput > input').val($('#autocloseSearchInput > input').val().replace(/,/g,';').replace(/ /g,''));
					var idArray = $('#autocloseSearchInput > input').val().split(';');
					if(idArray.length > maxIDs){
						$('#autocloseSearchValidationError').hide();
						$('#autocloseSearchValidationError > .errorText').html(msgIdLimit);
						$('#autocloseSearchValidationError').show();

					}else{
						var numbersOnly = true;
						var valid = true;
						for(i=0; i<idArray.length; i++){
							if(isNaN(idArray[i]) || $.trim(idArray[i]) == '') {
								if(i != (idArray.length -1)){
								numbersOnly = false;
									break;								
								}
							}
							if($.trim(idArray[i]).length > 10){
								valid = false; 
								break;
							}
						}
						if(!numbersOnly){
							$('#autocloseSearchValidationError').hide();
							$('#autocloseSearchValidationError > .errorText').html(msgNumbersOnly);
							$('#autocloseSearchValidationError').show();
						}else if(!valid){
							$('#autocloseSearchValidationError').hide();
							$('#autocloseSearchValidationError > .errorText').html(msgValidOnly);
							$('#autocloseSearchValidationError').show();						
						}else{
							$('#autocloseSearchValidationError').hide();
							runSearch();
						}
					}

				}else {
					$('#autocloseSearchValidationError').hide();
					runSearch();
				}
	});
}
