
jQuery(document).ready(function($) {

	var providerFirmId = $("#vendorId").val();
	var vendorName = $("#vendorName").val();
	var firstName = $("#firstName").val();
	var lastName = $("#lastName").val();
	var sharedSecret = $("#sharedSecret").val();

	vendorName = encodeURI(vendorName);
	


	$('#divNetworkInformationTab').load('/spn/spnLoginAction_ssoLogin.action?vendorId=' + providerFirmId + '&vendorName=' + vendorName + '&firstName=' + escape(firstName) + '&lastName='+ escape(lastName) + '&sharedSecret=' + sharedSecret, null, function()
	{
	    $('#addNote > h3').click(function(){
	    	$("#addNoteForm").toggle('slow');
		     enableCharacterCount();
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

				var providerFirmId = $('#formAddNote').find('#vendorId').val();
				//alert('providerFirmId=' + providerFirmId);

		     	$('#viewNotesDisplay').load('/spn/spnProviderFirmDetails_displayNotesAjax.action?providerFirmId=' + providerFirmId,{},function(){
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


function enableCharacterCount(){
	jQuery(document).ready(function($){
		$(".addNoteArea").bind("keyup blur", function() {
			var count = 750 - $(this).val().length;
			if (count >= 0) {
				if (count == 1) {
					jQuery('.characterCount').html("1 character remaining");
				} else {
					jQuery('.characterCount').html(count + " characters remaining");
				}
			} else {
				jQuery('.characterCount').html("0 characters remaining");
				$(this).val($(this).val().substr(0,750));
			}
		});
	});
}


function loadNetworksDisplay(subObj,toggledecider) {
    jQuery(document).ready(function($){
			var providerFirmId = $('#formAddNote').find('#vendorId').val();
			
		$("#buyerNetworksContent").load('/spn/spnProviderFirmDetails_displayNetworksAjax.action?providerFirmId=' + providerFirmId,{},function(){
	   		networksLoaded = true;
	   		if(toggledecider=='true'){
		       $("#buyerNetworksContent").find('#'+ subObj).toggle('slow');
		    }
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
	   			$(obj).find('.infoTextFirm').hide();
	   			
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

				enableCharacterCount();
			});

			var editNetworkGroupLoaded = false;
			$('.editNetworkGroup').click(function(){
				var obj = $(this);
				var parentObj = $(this).parent().parent();
				$(parentObj).find('.editNetworkGroupForm').toggle('slow',function(){
						$(parentObj).find('.editNetworkGroupForm').load('/spn/spnProviderDetails_displayGroupsAjax.action',
						function()
						{

						});
						editNetworkGroupLoaded = true;
					//}
				});
				setTimeout("enableCharacterCount(); jQuery('.cancelLink').click(function(){jQuery('.editNetworkGroupForm').hide();})",2500);

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


						$(parentObj).find(".companyRequirements").load('/MarketFrontend/spnMonitorCustomAction_getCompanyRequirementsList.action?spnID=' + networkId + '&vendorId=' + vendorId,
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

					$(parentObj).find(".providerRequirements").load("/MarketFrontend/spnMonitorCustomAction_getProviderRequirementsList.action?spnID=" + networkId +"&vendorId=" + vendorId,
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

		function fnDisplayDatePicker(){
			jQuery(".validityDate").val("");
			
			jQuery(".validUntil").datepicker({dateFormat:'mm/dd/yy', minDate: +1 , 
	   				onSelect: function(dateText, inst) { 
	      			jQuery(".validUntil").val(dateText);
	      			jQuery(".validityDate").val(dateText);
					}});
			var date1 = jQuery(".validUntil").val();
			if(date1 != "")
			{
			jQuery(".validityDate").val(date1);
			}
		}

		
function fnValidateSelection(dd){
	jQuery(document).ready(function($){
		var e = document.getElementById("networkStatus");
		var networkId = jQuery(dd).parent().parent().parent().parent().attr("id");
		var existingStatus = jQuery('#existingNetworkStatus_'+networkId).val();
		var vendorId = jQuery('#vendorId').val();
		existingStatus = formatSpace(existingStatus);
		var x = document.getElementById("networkStatus").selectedIndex;
		var index = dd.selectedIndex;
		var id = e.options[index].id;
		if(id=="noStatus"){
			jQuery('.forApprovedAndInactive').hide();
			jQuery('.applyAll').hide();
			jQuery('.infoTextFirm').hide();
			jQuery(".addNoteArea").val("");
			jQuery('.reasons').hide();
			jQuery('.characterCount').html("750 characters remaining");
		}
		if(id=="inactive"){
			jQuery('.forApprovedAndInactive').show();
			jQuery('.applyAll').show();
			jQuery('.infoTextFirm').hide();
   			jQuery('.applyAllFirm').attr("checked", false);
			jQuery(".validityDate").val("");
   			jQuery(".validUntil").val("");
   			jQuery(".addNoteArea").val("");
   			jQuery('.characterCount').html("750 characters remaining");
   			jQuery('.reasons').show();
			jQuery('.reasonDropdownDiv').load('/spn/spnProviderFirmDetails_displayReasonsAjax.action?wfState=PF%20FIRM%20OUT%20OF%20COMPLIANCE&existingStatus='+existingStatus+'&spnId='+networkId+'&vendorId='+vendorId);
		}
		if(id=="remove"){
			jQuery('.forApprovedAndInactive').hide();
			jQuery('.applyAll').show();
			jQuery('.infoTextFirm').hide();
   			jQuery('.applyAllFirm').attr("checked", false);
   			jQuery(".validityDate").val("No expiration date");
   			jQuery(".validUntil").val("");
   			jQuery(".addNoteArea").val("");
   			jQuery('.characterCount').html("750 characters remaining");
   			
			jQuery('.reasons').show();
			jQuery('.reasonDropdownDiv').load('/spn/spnProviderFirmDetails_displayReasonsAjax.action?wfState=PF%20SPN%20REMOVED%20FIRM&existingStatus='+existingStatus+'&spnId='+networkId+'&vendorId='+vendorId);
		}
		if(id=="approved"){
			jQuery('.forApprovedAndInactive').show();
			jQuery('.applyAll').hide();
			jQuery(".validityDate").val("");
			jQuery('.infoTextFirm').hide();
			jQuery(".addNoteArea").val("");
			jQuery('.characterCount').html("750 characters remaining");
			
			if(existingStatus=="PF%20SPN%20REMOVED%20FIRM"){
				jQuery('.reasons').hide();
			}else{
				jQuery('.reasons').show();
				jQuery('.reasonDropdownDiv').load('/spn/spnProviderFirmDetails_displayReasonsAjax.action?wfState=PF%20SPN%20MEMBER&existingStatus='+existingStatus+'&spnId='+networkId+'&vendorId='+vendorId);
			}
		}
	});
}

		function displayInfo(obj){
			var classname = obj.className;
			var name = "."+classname;
			var checked = jQuery('.' + classname).is(":checked");
			if(classname=="applyAllFirm" && checked == true){
				jQuery('.infoTextFirm').show();
			}else{
				jQuery('.infoTextFirm').hide();
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
			var providerFirmId = $('#formAddNote').find('#vendorId').val();
			$.post('/spn/spnProviderFirmDetails_addNoteAjax.action?providerFirmId=' + providerFirmId  + '&note=' + escape(note),serializedForm,function(data,textStatus){

		     	$('#viewNotesDisplay').load('/spn/spnProviderFirmDetails_displayNotesAjax.action?providerFirmId=' + providerFirmId,{}, function(){
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

function formatSpace(existingStatus){
	return existingStatus.replace(/ /g, "%20");
}

function submitNetworkChanges(obj){
	jQuery(document).ready(function($){
		var formToSubmit = jQuery(obj).parent().parent().parent();
		var reasonsSelect = jQuery(formToSubmit).find('.reasonsSelect');
		var subButtonId=$(obj).parent().parent().parent().parent().attr('id');
		var networkStatus = jQuery(formToSubmit).find('#networkStatus');
		var networkId = jQuery(obj).parent().parent().parent().parent().parent().attr("id");
		var existingStatus = jQuery('#existingNetworkStatus_'+networkId).val();
		existingStatus = formatSpace(existingStatus);
		if(jQuery(networkStatus).val() == -1){
			jQuery('.errorMessage').html('Please select a Status.').show();
		}else if(jQuery(".validityDate").val() == "" ){
			jQuery('.errorMessage').html('Please select a validity date.').show();
        }else if(jQuery(reasonsSelect).val() == -1 && (!(jQuery(networkStatus).val() == "PF%20SPN%20MEMBER" && existingStatus == "PF%20SPN%20REMOVED%20FIRM"))){
    		jQuery('.errorMessage').html('Please select a reason.').show();
		}else if(jQuery(formToSubmit).find('.addNoteArea').val().trim() == ''){
			jQuery('.errorMessage').html('Please enter comments to describe the change to membership.').show();
		}else{
			jQuery('.errorMessage').hide();
			//Fix for issue SL-20122			
			jQuery(formToSubmit).find('#submitNetworkStatusChange').attr("disabled", true);
			var comment= jQuery('.addNoteArea').val();	
			var newStatus = jQuery(networkStatus).val();
			var length = jQuery('input[name=applyAllNetworks]:checked').length;
			var applyAll = 'false';
			if(length==0){
				applyAll = 'false';
			}else{
				applyAll = 'true';
			}
			var reason = jQuery(reasonsSelect).val();
			var providerFirmId = jQuery(formToSubmit).find('#vendorId').val();
			var validityDate = jQuery(".validityDate").val();
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
			
			var postNetworkStatusChange = function(){
				$.post('/spn/spnProviderFirmDetails_changeNetworkStatusAjax.action?providerFirmId=' + providerFirmId  +'&newStatus=' + networkStatus + '&networkId=' + networkId + '&comment=' + comment + '&reason=' + reason + '&wfState=' + newStatus
						+ '&applyAll=' + applyAll+ '&validUntil=' + validityDate,serializedForm,
					function(data,textStatus){

					
					if(data.indexOf('error') != -1)
					{
						// Don't reload network panel
						// Put error message in proper location
						$(formToSubmit).find('.errorMessage').html(data);
						$(formToSubmit).find('.errorMessage').show();
					}
					else
					{
						// Reload network panel
						loadNetworksDisplay(subButtonId,'true');

						$('#viewNotesDisplay').load('/spn/spnProviderFirmDetails_displayNotesAjax.action?providerFirmId=' + providerFirmId+ '&applyAll=' + applyAll,{}, function()
						{
							$('#notesAndHistoryTabs >ul').tabs();
						});
					}

	   			});

			}

			var dCount = "unknown number of";
			var spCount = "unknown number of";
			var spns = $("#networkSize").val();
			if(spns>0){
				spCount = spns;
			}
			
			jQuery.ajax({
 	        	url: '/spn/spnProviderFirmDetails_getProviderCountAjax.action?networkId=' + networkId + '&providerFirmId=' + providerFirmId+ '&applyAll=' + applyAll,
 	        	type: "GET", 
 	        	dataType: 'json',
				success: function(data){
					if(data.providerCount > 0)
						dCount = data.providerCount;
					if(applyAll=='true'){
						if(confirm("This action will affect "+spCount+" select provider networks for "+dCount+" providers within this firm.")){
							postNetworkStatusChange();
						}
						//Fix for issue SL-20122	
						else
							{
							jQuery(formToSubmit).find('#submitNetworkStatusChange').attr("disabled", false);
							}
					}else{
						if(confirm("Warning!  You are about to allow a member who is not compliant with your network " +
								"requirements to continue receiving work. This may create a future problem for your " +
								"company. This action will affect "+dCount+" providers within this firm. Select OK to apply this change.")){
							postNetworkStatusChange();
						}
						//Fix for issue SL-20122	
						else
						{
						jQuery(formToSubmit).find('#submitNetworkStatusChange').attr("disabled", false);
						}
					}
				},
			  	error: function(xhr, status, err) {
				   console.log("error details"+err);
				  } 
			});
   		}
	});
}