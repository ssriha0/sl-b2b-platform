//SS
var clickObjforTimeout;  //yeah, I know global variables are evil
function loadNetworksDisplay(subObj,toggledecider) {
  jQuery(document).ready(function($){
			var vendorId = $('#formAddNote').find('#vendorId').val();
			 var vendorResourceId = $('#formAddNote').find('#vendorResourceId').val();
		$("#buyerNetworksContent").load('/spn/spnProviderDetails_displayNetworksAjax.action?vendorResourceId=' + vendorResourceId + '&vendorId=' + vendorId,{},function(){
		   if(toggledecider=='true'){
		       $("#buyerNetworksContent").find('#'+ subObj).toggle('slow');
		    }
	   		networksLoaded = true;

	   		$('.expandableList > li > h4').click(function(){
	    		$(this).parent().find('.networkDetail').toggle('slow');
		    });

			$('.editNetworkStatus').click(function(){
				var obj =$(this).parent().parent();
				var networkId = jQuery(this).parent().parent().attr("id");
				var overrideInd = jQuery('#overrideInd_'+networkId).val();
				var hasAliasInd = jQuery('#hasAliasInd_'+networkId).val();
				var spnEditEffectiveDate = jQuery('#spnEditEffectiveDate_'+networkId).val();
				var spnOverrideEffectiveDate = jQuery('#spnOverrideEffectiveDate_'+networkId).val();
	   			$(obj).find('.editNetworkStatusForm').addClass('inlineEditForm');
	   			$(obj).find('.editNetworkStatusForm').html($('#changeNetworkStatusTemplate').html());
	   			/*Fix for SL-20118.*/
	   			$(obj).find('.reasons').hide();
	   			$(obj).find('.forApprovedAndInactive').hide();
	   			$(obj).find('.applyAll').hide();
	   			$(obj).find('.infoText').hide();
	   			
	   			if(hasAliasInd == 'true'){
	   				$(obj).find('#networkStatus').attr("disabled", true);
	   				$(obj).find('.addNoteArea').attr("disabled", true);
	   				$(obj).find('#submitNetworkStatusChange').attr("disabled", true);
	   				$(obj).find('#warningText').html("This SPN is in Edited Status until " + spnEditEffectiveDate + ". Please try after this period.");
	   				overrideInd = 'false';
	   			} else{
	   				$(obj).find('#networkStatus').attr("disabled", false);
	   				$(obj).find('.addNoteArea').attr("disabled", false);
	   				$(obj).find('#submitNetworkStatusChange').attr("disabled", false);
	   			}
	   			if(overrideInd == 'true'){
	   				$(obj).find('#warningText').html("Buyer has overridden the network status " + spnOverrideEffectiveDate);
	   			}
	   			$(obj).find('.editNetworkStatusForm').toggle('slow');
	   			jQuery('.errorMessage').hide();
	   			$('.cancelLink').click(function(){
	   				$(obj).find('.inlineEditForm').hide();
	   			});

				enableCharacterCount(obj,750);
			});

			var editNetworkGroupLoaded = false;

			$('.editNetworkGroup').click(function(){
				var obj = $(this);
				clickObjforTimeout = $(this).parent().parent().parent().find('.editNetworkGroupForm');

				var parentObj = $(this).parent().parent();
				var networkGroup = parentObj.parent().find('#networkGroup').val();

				$(parentObj).find('.editNetworkGroupForm').toggle('slow',function(){
						$(parentObj).find('.editNetworkGroupForm').load('/spn/spnProviderDetails_displayGroupsAjax.action?networkGroup=' + networkGroup,
						function()
						{
							var networkId = $(obj).parents(".networkDetail").attr("id");
							$(parentObj).find('.routingPriority').load("/spn/spnCreateNetwork_viewRoutingTiersAjax.action?spnid=" + networkId + "&hideEditLink=1", function(){
								$(parentObj).find('.header4').html('Routing Priority for Network');
								$(parentObj).find('.noRoutingPrioritiesAlert').html("This network doesn't have routing priorities created.");
							});

						});
						editNetworkGroupLoaded = true;
					//}
				});
				setTimeout("enableCharacterCount(clickObjforTimeout,250); jQuery('.cancelLink').click(function(){jQuery('.editNetworkGroupForm').hide();})",2500);
				
			});

			var companyReqsLoaded;
			$('.toggleCompanyRequirements').click(function(){
				var obj = $(this);
				var parentObj = $(this).parent().parent().parent().parent();
				$(parentObj).find('.companyRequirements').toggle('slow',function(){

					var networkId = $(obj).parents(".networkDetail").attr("id");
					var vendorId = $('#formAddNote').find('#vendorId').val();

					if(companyReqsLoaded != networkId)
					{
						$(parentObj).find(".companyRequirements").load('/MarketFrontend/spnMonitorCustomAction_getCompanyRequirementsList.action?&spnID=' + networkId + '&vendorId=' + vendorId,
							function()
							{
							companyReqsLoaded = networkId;
							});
					}
					if($(parentObj).find('.companyRequirements').css('display') == 'block'){
						$(obj).attr('src','/ServiceLiveWebUtil/images/common/minus-big.png');
					}else{
						$(obj).attr('src','/ServiceLiveWebUtil/images/common/plus-big.png');
					}
				});
			});

			$('.toggleProviderRequirements').click(function(){
  				var obj = $(this);
  				var parentObj = $(this).parent().parent().parent().parent();
  				$(parentObj).find('.providerRequirements').toggle('slow',function(){

					var networkId = $(obj).parents(".networkDetail").attr("id");
					var vendorId = $('#formAddNote').find('#vendorId').val();

					$(parentObj).find(".providerRequirements").load("/MarketFrontend/spnMonitorCustomAction_getProviderRequirementsList.action?&spnID=" + networkId +"&vendorId=" + vendorId,
							function()
							{
								providerReqLoad = true;
							});


					if($(parentObj).find('.providerRequirements').css('display') == 'block'){
						$(obj).attr('src','/ServiceLiveWebUtil/images/common/minus-big.png');
					}else{
						$(obj).attr('src','/ServiceLiveWebUtil/images/common/plus-big.png');
					}
				});
			});
		});
	});
}

	function fnValidateSelection(dd){
		var e = document.getElementById("networkStatus");
		var x = document.getElementById("networkStatus").selectedIndex;
		var networkId = jQuery(dd).parent().parent().parent().parent().attr("id");
		var existingStatus = jQuery('#existingNetworkStatus_'+networkId).val();
		var resourceId = jQuery('#vendorResourceId').val();
		existingStatus = formatSpace(existingStatus);
		var index = dd.selectedIndex;
		var id = e.options[index].id;
		if(id=="noStatus"){
			jQuery('.forApprovedAndInactive').hide();
			jQuery('.applyAll').hide();
			jQuery('.infoText').hide();
			jQuery(".addNoteArea").val("");
			jQuery('.reasons').hide();
			jQuery('.characterCount').html("750 characters remaining");
		}
		if(id=="inactive"){
			
   			jQuery('.forApprovedAndInactive').show();
   			jQuery('.applyAll').show();
   			//added
   			jQuery(".validityDate").val("");
   			jQuery('.infoText').hide();
   			jQuery('.applyAllProvider').attr("checked", false);
   			jQuery(".validUntil").val("");
   			jQuery(".addNoteArea").val("");
   			jQuery('.characterCount').html("750 characters remaining");
   			jQuery('.reasons').show();
   			jQuery('.reasonDropdownDiv').load('/spn/spnProviderDetails_displayReasonsAjax.action?wfState=SP%20SPN%20OUT%20OF%20COMPLIANCE&existingStatus='+existingStatus+'&spnId='+networkId+'&resourceId='+resourceId, function(){
			});
		}
		if(id=="remove"){
			jQuery('.forApprovedAndInactive').hide();
			jQuery('.applyAll').show();
   			//added
   			jQuery(".validityDate").val("No expiration date");
   			jQuery('.infoText').hide();
   			jQuery('.applyAllProvider').attr("checked", false);
   			jQuery(".validUntil").val("");
   			jQuery(".addNoteArea").val("");
   			jQuery('.characterCount').html("750 characters remaining");
   			jQuery('.reasons').show();
			jQuery('.reasonDropdownDiv').load('/spn/spnProviderDetails_displayReasonsAjax.action?wfState=SP%20SPN%20REMOVED&existingStatus='+existingStatus+'&spnId='+networkId+'&resourceId='+resourceId, function (){
			});
		}
		if(id=="approved"){
			jQuery('.forApprovedAndInactive').show();
			jQuery('.applyAll').hide();
			jQuery('.infoText').hide();
			jQuery(".validityDate").val("");
			jQuery(".validUntil").val("");
			jQuery(".addNoteArea").val("");
			jQuery('.characterCount').html("750 characters remaining");
	   		if(existingStatus=="SP%20SPN%20REMOVED"){
				jQuery('.reasons').hide();
			}else{
				jQuery('.reasons').show();
				jQuery('.reasonDropdownDiv').load('/spn/spnProviderDetails_displayReasonsAjax.action?wfState=SP%20SPN%20APPROVED&existingStatus='+existingStatus+'&spnId='+networkId+'&resourceId='+resourceId, function (){
				});
			}
		}
	}

	function displayInfo(obj){
		var classname = obj.className;
		var name = "."+classname;
		var checked = jQuery('.' + classname).is(":checked");
		if(classname=="applyAllProvider" && checked == true){
			jQuery('.infoText').show();
		}else{
			jQuery('.infoText').hide();
		}
   	}
	   	
	function fnDisplayDatePicker(){
		jQuery(".validityDate").val("");
		jQuery(".validUntil").datepicker( "destroy" );
		jQuery(".validUntil").datepicker({dateFormat:'mm/dd/yy', minDate: +1 , 
   				onSelect: function(dateText, inst) { 
      			jQuery(".validUntil").val(dateText);
      			jQuery(".validityDate").val(dateText);
				}});
		var date1 = jQuery(".validUntil").val();
		if(date1 != "") {
			jQuery(".validityDate").val(date1);
		}
	}
	   			
function addNote(obj){
	jQuery(document).ready(function($){
		var formToSubmit = $(obj).parent().parent();

		var note = $(formToSubmit).find('.addNoteArea').val();
		$(formToSubmit).find('.action').attr('disabled',true);
		//filter preventing the addition of extra long character strings
		var maxChunkLength = 50;
		if(note.length > maxChunkLength && note.indexOf(' ') <= 0) {
			var timesIn = parseInt(note.length / maxChunkLength)+1;
			for (i=0; i<timesIn;i++){
				note = note.substr(0,i*maxChunkLength) + " " + note.substr(i*maxChunkLength);
			}
		}
		//end filter

		if (note == '') {
			$(formToSubmit).find('.action').attr('disabled',false);
			$(formToSubmit).find('.errorMessage').html('Please add a note.').show();
		}else{
			var serializedForm = $(formToSubmit).serialize();

			var vendorId = $('#formAddNote').find('#vendorId').val();
			var vendorResourceId = $('#formAddNote').find('#vendorResourceId').val();
			$.post('/spn/spnProviderDetails_addNoteAjax.action?vendorId=' + vendorId + '&vendorResourceId=' + vendorResourceId + '&note=' + escape(note),serializedForm,function(data,textStatus){

		     	$('#viewNotesDisplay').load('/spn/spnProviderDetails_displayNotesAjax.action?vendorResourceId=' + vendorResourceId,{}, function(){
		     		$('#notesAndHistoryTabs >ul').tabs();
		     		$('#viewNotesDisplay').show();
		     		if(typeof document.body.style.maxHeight == 'undefined'){ //IE6
		     			$('#viewNotesDisplay').css('height','250px');
		     		}
		     		$(".noteText").truncate( 200, {
	     		        chars: /\s/,
	     		        trail: [ " (+ <a href='#' class='truncate_show'>more</a>)", " (- <a href='#' class='truncate_hide'>less</a>)" ]
	     		    });

					$('#viewNotes > h3 >img').attr('src','/ServiceLiveWebUtil/images/icons/arrowDown-darkgray.gif');
	     			$(formToSubmit).parent().hide('slow');
	     			$(formToSubmit)[0].reset();
	     			$(formToSubmit).find('.errorMessage').hide();
	     			$(formToSubmit).find('.action').attr('disabled',false);
		     	});
			});
		}
	});
}

function submitNetworkChanges(obj){
	 jQuery(document).ready(function($){
		var formToSubmit = jQuery(obj).parent().parent().parent();
		var subButtonId=jQuery(obj).parent().parent().parent().parent().parent().attr('id');
		var reasonsSelect = jQuery(formToSubmit).find('.reasonsSelect');
		var networkStatus = jQuery(formToSubmit).find('#networkStatus');
		var networkId = $(obj).parent().parent().parent().parent().attr("id");
		var existingStatus = jQuery('#existingNetworkStatus_'+networkId).val();
		existingStatus = formatSpace(existingStatus);
		if(jQuery(networkStatus).val() == -1){
			jQuery('.errorMessage').html('Please select a Status.').show();
		}else if(jQuery(".validityDate").val() == "" ){
			jQuery('.errorMessage').html('Please select a validity date.').show();
        }else if(jQuery(reasonsSelect).val() == -1 && (!(jQuery(networkStatus).val() == "SP%20SPN%20APPROVED" && existingStatus == "SP%20SPN%20REMOVED"))){
    		jQuery('.errorMessage').html('Please select a reason.').show();
		}else if(jQuery(formToSubmit).find('.addNoteArea').val().trim() == ''){
			jQuery('.errorMessage').html('Please enter comments to describe the change to membership.').show();
		}else{
			//Fix for issue SL-20122
			jQuery('.errorMessage').hide();
			jQuery(formToSubmit).find('#submitNetworkStatusChange').attr("disabled", true);
			var comment= jQuery(formToSubmit).find('.addNoteArea').val();
			var newStatus = jQuery(networkStatus).val();
			var length = jQuery('input[name=applyAllNetworks]:checked').length;
			var applyAll = 'false';
			if(length==0){
				applyAll = 'false';
			}else{
				applyAll = 'true';
			}
			var reason = reasonsSelect.val();
			var vendorId = $(formToSubmit).find('#vendorId').val();
			var vendorResourceId = $(formToSubmit).find('#vendorResourceId').val();
			var serializedForm = $(formToSubmit).serialize();
			//filter preventing the addition of extra long character strings
			var maxChunkLength = 50;
			if(comment.length > maxChunkLength && comment.indexOf(' ') <= 0) {
				var timesIn = parseInt(comment.length / maxChunkLength)+1;
				for (i=0; i<timesIn;i++){
					comment = comment.substr(0,i*maxChunkLength) + " " + comment.substr(i*maxChunkLength);
				}
			}
			comment = comment.replace(/%/g, "-prcntg-");
			comment = encodeURIComponent(comment);
			//end filter
			var postNetworkStatusChangeProvider = function(){
				$.post('/spn/spnProviderDetails_changeNetworkStatusAjax.action?vendorId=' + vendorId + '&vendorResourceId='+ vendorResourceId +'&newStatus=' + networkStatus + '&networkId=' + networkId + '&comment=' + comment + '&reason=' + reason + '&wfState=' + newStatus+ '&applyAll=' + applyAll,serializedForm,function(data,textStatus){
					loadNetworksDisplay(subButtonId,'true');
					$('#viewNotesDisplay').load('/spn/spnProviderDetails_displayNotesAjax.action?vendorResourceId=' + vendorResourceId,{}, function(){
			     		$('#notesAndHistoryTabs >ul').tabs();
			     		$('#viewNotesDisplay').show();
			     		if(typeof document.body.style.maxHeight == 'undefined'){ //IE6
			     			$('#viewNotesDisplay').css('height','250px');
			     		}
			     		$(".noteText").truncate( 200, {
		     		        chars: /\s/,
		     		        trail: [ " (+ <a href='#' class='truncate_show'>more</a>)", " (- <a href='#' class='truncate_hide'>less</a>)" ]
		     		    });
						$('#viewNotes > h3 >img').attr('src','/ServiceLiveWebUtil/images/icons/arrowDown-darkgray.gif');
						$('#notesAndHistoryTabs > ul li:eq(1) > a').click();
	
						$('#viewNotesDisplay').load('/spn/spnProviderDetails_displayNotesAjax.action?vendorResourceId=' + vendorResourceId,{}, function()
						{
							$('#notesAndHistoryTabs >ul').tabs();
						});
					});
	   			});
			}
			if(applyAll=='true'){
				var dCount = "unknown number of";
				dCount = $("#networkSize").val();
				if(confirm("This action will affect "+dCount+" select provider networks for this provider.")){
					postNetworkStatusChangeProvider();
				}
			}else{
				postNetworkStatusChangeProvider();
			}
   		}
	 });
}

function formatSpace(existingStatus){
	return existingStatus.replace(/ /g, "%20");
}

function submitGroupChanges(obj){
	jQuery(document).ready(function($){
		var formToSubmit = $(obj).parent().parent();
		var subButtonId=$(obj).parent().parent().parent().parent().parent().attr('id');
		var groupSelect = $('input[name=networkGroupSelect]:checked').val();
		var comments= $(formToSubmit).find('.addNoteArea').val()

		if(groupSelect == null || groupSelect == ''){
			$(formToSubmit).find('.errorMessage').html('Please select a group.').show();
		}
		else if(comments==null || comments == ''){
			$(formToSubmit).find('.errorMessage').html('Please enter comments to describe the change to group.').show();
		}else{
			var vendorId = $('#formAddNote').find('#vendorId').val();
			var vendorResourceId = $('#formAddNote').find('#vendorResourceId').val();

			var networkId = $(obj).parents(".networkDetail").attr("id");
			var serializedForm = $(formToSubmit).serialize();
			$.post('/spn/spnProviderDetails_changeGroupsAjax.action?vendorId=' + vendorId + '&vendorResourceId='+ vendorResourceId+'&newGroup=' + groupSelect + '&networkId='+ networkId +'&comments=' + comments,serializedForm,function(data,textStatus){
				loadNetworksDisplay(subButtonId,'true');

				$('#viewNotesDisplay').load('/spn/spnProviderDetails_displayNotesAjax.action?vendorResourceId=' + vendorResourceId,{}, function()
				{
					$('#notesAndHistoryTabs >ul').tabs();
				});
   			});
   		}
   	});
}

function enableCharacterCount(obj,num){


	if(num == null || num ==''){
		num = 750;
	}
	jQuery(document).ready(function($){
		var countChars = function() {
			var count = num - $(this).val().length;
			if (count >= 0) {
				if (count == 1) {
					jQuery('.characterCount').html("1 character remaining");
				} else {
					jQuery('.characterCount').html(count + " characters remaining");
				}
			} else {
				jQuery('.characterCount').html("0 characters remaining");
				$(this).val($(this).val().substr(0,num));
			}
		}

		$(obj).find(".addNoteArea").bind("keyup blur focus", countChars);
		$(obj).find(".addNoteArea").focus();
	});
}

jQuery(document).ready(function($){
	var vendorId = $('#networkInfoForm').find('#vendorId').val();
	var vendorResourceId = $('#networkInfoForm').find('#vendorResourceId').val();
	var sharedSecret = $('#networkInfoForm').find('#sharedSecret').val();
	var firstName = $('#networkInfoForm').find('#firstName').val();
	var lastName = $('#networkInfoForm').find('#lastName').val();


	$('#networkInfoDiv').load('/spn/spnLoginAction_ssoLogin.action?vendorId=' + vendorId + '&vendorResourceId=' + vendorResourceId + '&firstName=' + escape(firstName) + '&lastName='+ escape(lastName) + '&sharedSecret=' + sharedSecret, function()
	{

	    $('#addNote > h3').click(function(){
	    	$("#addNoteForm").toggle('slow');
		     enableCharacterCount($("#addNoteForm"),750);
	    });

	    $('.cancelLink').click(function(){
	   		$(this).parent().parent().parent().find('form')[0].reset();
			$(this).parent().parent().parent().hide();
		});

		var notesLoaded = false;
		$('#viewNotes > h3').click(function(){
			$('#viewNotesDisplay').toggle('slow',function(){
				if(typeof document.body.style.maxHeight == 'undefined'){ //IE6
	     			$('#viewNotesDisplay').css('height','250px');
	     		}
				if(!notesLoaded){

				var vendorId = $('#formAddNote').find('#vendorId').val();
				var vendorResourceId = $('#formAddNote').find('#vendorResourceId').val();

		     	$('#viewNotesDisplay').load('/spn/spnProviderDetails_displayNotesAjax.action?vendorResourceId=' + vendorResourceId,{},function(){
		     			notesLoaded=true;
		     			$('#notesAndHistoryTabs >ul').tabs();
		     			$(".noteText").truncate( 200, {
		     		        chars: /\s/,
		     		        trail: [ " (+ <a href='#' class='truncate_show'>more</a>)", " (- <a href='#' class='truncate_hide'>less</a>)" ]
		     		    });

		     		});
		     	}
		     	if($('#viewNotes > h3 >img').attr('src').indexOf('Right')> 0){
		     		$('#viewNotes > h3 >img').attr('src','/ServiceLiveWebUtil/images/icons/arrowDown-darkgray.gif');
		     	}else{
		     		$('#viewNotes > h3 >img').attr('src','/ServiceLiveWebUtil/images/icons/arrowRight-darkgray.gif');
		     	}
		     });
		});

		loadNetworksDisplay('','false');
	});

});


//SPN MM tab functions
jQuery(document).ready(function($){
	$('#spnMMSearchBy').change(function(){
	    // for hiding error msg after entering valid values
	    $('#spnMMSearchValidationError').hide(); 
	    $('#spnMMSearchError').hide();
		if ($('#spnMMSearchBy > option:selected').val() >0){
			var texttoappend = $('#spnMMSearchBy > option:selected').text() + ', minimum 3 chars';
			if($('#spnMMSearchBy > option:selected').val() == 2 || $('#spnMMSearchBy > option:selected').val() ==  4 ){
				texttoappend = 'multiples 	separated by ; up to 10';
			}
			$('#spnMMSearchInput > label >span').html(texttoappend);
		//	$('#spnMMSearchInput > input' ).attr('id','spnMMSearchText' + $('#spnMMSearchBy > option:selected').val());
			$('#spnMMSearchInput > input' ).attr('value','');

			$('#spnMMSearchInput > label').show();
		}else{
			$('#spnMMSearchInput > label').hide();
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
	}
	$('#searchSubmit-1').click(function(){
		clearFilters();
		submitSearch();
	});

	$('#searchByType').change(function() {
		if ($(this).val() == "-1") {
			submitSearch();
		}
	});

	$('#loadSPNSpinner').jqm( {
		modal : true,
		toTop : true
	});

	//enable submit on Enter
	$("input").keypress(function(e) {
		if ((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)) {
			clearFilters();
			submitSearch();
		}
	});

	//upon loading page, submit search if the "search by" fields contain values (e.g., user clicked back button)
	if(($('#spnMMSearchBy').length && $('#spnMMSearchInput').length) /* check if fields exist */
			&& $('#spnMMSearchBy > option:selected').val() != -1
				&& $('#spnMMSearchInput > input').val() != "") {
		submitSearch();
	}

});
     function trim(myString){
		return myString.replace( /^\s+/g,'').replace(/\s+$/g,''); 
	}

function submitSearch() {
 jQuery(document).ready(function($){
        $('#spnMMSearchError').hide();
		var runSearch = function(){
			//run search and display results; call this after validation
			var viewAll = $('#searchByViewAll').val();
			var searchType = $('#spnMMSearchBy').val();
					var textId = '#spnMMSearchText';  //+searchType;
			var textValue = $(textId).val();
			var spnId = $('#spnList').val();
			var stateCd = $('#stateList').val();
			if(stateCd == 'undefined' || stateCd == null) stateCd = -1;
			var queryString = "";
			if(searchType == 1) {
				queryString += "searchCriteriaVO.serviceProviderName="	+ textValue	+ "&";
			}
			else if (searchType == 2){
				queryString += "searchCriteriaVO.serviceProviderIdsStr=" + textValue + "&";
			}
			else if (searchType == 3) {
			 	queryString += "searchCriteriaVO.providerFirmName="	+ textValue	+ "&";
			}
			else if (searchType  == 4) {
				queryString += "searchCriteriaVO.providerFirmIdsStr=" + textValue + "&";
			}

			queryString += "searchCriteriaVO.filterCriteria.spnId=" + spnId	+ "&" + "searchCriteriaVO.filterCriteria.stateCode=";
			queryString += stateCd + "&" + "searchCriteriaVO.isViewAll="	+ viewAll + "&"	+ "searchCriteriaVO.searchByType=" + searchType;
			var encodedAction = encodeURI('spnMMSearchTabAction_searchMembersAjax.action?' + queryString);

			$('#loadSPNSpinner').jqmShow();
				$('#searchresults').load(encodedAction,function(){
				//loginPage set in login.jsp
				if(typeof loginPage == 'boolean' && loginPage == true){
					top.location = "/spn/spnLoginAction_display.action"; //prevent the login page from appearing in a div
				}
					$('#loadSPNSpinner').jqmHide();
					$('#spnMMSearchResultsTable').tablesorter({headers:{2:{sorter:false}, 3:{sorter:false}}});
					$('#spnMMSearchBy').css('background','#fff');
					$('#spnMMSearchInput > input').css('background','#fff');
				});
		}

				//input validation
			var maxIDs = 10;
			var maxIdLength=10;
			var msgIdLength="Please enter maximum of   "  + maxIdLength + " digits for an Id ";
			var msgValidCriteria = "Please enter at least 3 characters.";
			var msgIdLimit = "Please provide up to " + maxIDs + " IDs.";
			var msgNumbersOnly = "Please use numeric IDs only, separated by semicolons (;)";
			var msgAlphabetsOnly="Percentage is not allowed in the search field";
			var msgSelectCtgry = "Please select a search category.";

				if($('#spnMMSearchBy > option:selected').val() == -1){
                    
					$('#spnMMSearchValidationError > .errorText').html(msgSelectCtgry);
					$('#spnMMSearchError').hide();
					$('#spnMMSearchValidationError').show();
					$('#spnMMSearchBy').focus();
					$('#spnMMSearchBy').css('background','#fff8bf');
				} else if($('#spnMMSearchInput > input').val().length < 3){
					$('#spnMMSearchValidationError').hide();
					$('#spnMMSearchError').hide();
					$('#spnMMSearchValidationError > .errorText').html(msgValidCriteria);
					$('#spnMMSearchValidationError').show();
					$('#spnMMSearchInput > input').focus();
					$('#spnMMSearchInput > input').css('background','#fff8bf');
					$('#spnMMSearchBy').css('background','#fff');

				}else if ($('#spnMMSearchBy > option:selected').val() == 2 || $('#spnMMSearchBy > option:selected').val() == 4){
					//check IDs
					$('#spnMMSearchValidationError').hide();
					$('#spnMMSearchInput > input').val($('#spnMMSearchInput > input').val().replace(/,/g,';').replace(/ /g,''));
					var idArray = $('#spnMMSearchInput > input').val().split(';');
					if(idArray.length > maxIDs){
						$('#spnMMSearchValidationError').hide();
						$('#spnMMSearchError').hide();
						$('#spnMMSearchValidationError > .errorText').html(msgIdLimit);
						$('#spnMMSearchValidationError').show();

					}
					else{
						var numbersOnly = true;
						var maxIdlengthInd=true;
						var inputStr='';
						for(i=0; i<idArray.length; i++){
							if(isNaN(idArray[i])) {
								numbersOnly = false;
								break;
							}
							if(idArray[i].length > maxIdLength){
							   maxIdlengthInd=false;
							   continue;
							}
							else{
								if(inputStr==''){
									inputStr=idArray[i];
									
								}
								else{
									inputStr= inputStr+';'+ idArray[i];
									
									}
								}
						}
						if(!numbersOnly){
							$('#spnMMSearchValidationError').hide();
							$('#spnMMSearchValidationError > .errorText').html(msgNumbersOnly);
							$('#spnMMSearchError').hide();
							$('#spnMMSearchValidationError').show();


						}else if(maxIdlengthInd){
				            $('#spnMMSearchValidationError').hide();
				            $('#spnMMSearchError').hide();
						    $('#spnMMSearchText').val(inputStr);
						    runSearch();
						}
						else{
							$('#spnMMSearchValidationError').hide();
							$('#spnMMSearchError').hide();
							$('#spnMMSearchText').val(inputStr);
							runSearch();
						}
					}

				}else {
					$('#spnMMSearchValidationError').hide();
					$('#spnMMSearchError').hide();
					runSearch();
				}
	});
}
function showHistory(e,obj) {
	jQuery(document).ready(function($){
	jQuery(".histDetailsDiv").css("display","none");
	jQuery(".arrowAddNoteDetails").css("display","none");
	var formToSubmit = jQuery(obj).parent().parent();
	var form = jQuery(obj).parent();
	var click = jQuery(formToSubmit).find(".dash");
	var x = 0;
	var y = 0;
	
	jQuery(".histDetailsDiv").offset({ top: y, left: x});
	jQuery(".arrowAddNoteDetails").offset({ top: y, left: x});
	jQuery(".histDetailsDiv").css("margin-left",x-22+'px');
	jQuery(".arrowAddNoteDetails").css("margin-left", x-783+'px');
 	jQuery(".histDetailsDiv").show();
 	jQuery(".arrowAddNoteDetails").show();
	});
}
function closeHistory(){
	jQuery(document).ready(function($){
	$(".histDetailsDiv").css("display","none");
	$(".arrowAddNoteDetails").css("display","none");
	});
}