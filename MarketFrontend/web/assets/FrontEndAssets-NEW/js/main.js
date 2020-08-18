$(function(){

	// Add a Note Widget
	//===============================
	var addNote = $( '#widget-add-note' );
	addNote.hide();
	// Hide the "Add Note" button and show the form
	$(' .btn-add-note ').click(function() {
	  $( this ).hide();
	  addNote.show();
	  $( 'html,body' ).animate({ scrollTop: addNote.offset().top - 20 }, 300)
	  return false;
	});
	// Bring back the "Add Note" button on submit
	$( '#widget-add-note button[type="submit"]' ).click(function() {
	  //addNote.hide();
	  //$(' .btn-add-note ').show();
	 var leadId = $('#addNoteLeadId').val();
	 var leadStatus = $('#addNoteLeadStatus').val();	
	 var sub =  $('#note_subject').val();
	 var msg =  $('#note_message').val();
	 var errMsg = "";
	 if(jQuery.trim(sub) == ""){
		 errMsg = errMsg + "Please enter Subject.</br>";
	 }if(jQuery.trim(msg) == ""){
		 errMsg = errMsg + "Please enter Message.</br>"; 
	 }
	 if(errMsg!=""){
		 $('#responseMsg').html(errMsg);
		 return false;
	 }
	 else{
		 $('#responseMsg').html("");
		 $('.btn-default').hide();
		 $('#addingNote').html("Adding Note...");
		 var updateUrl='leadsManagementController_addNote.action?noteSubject='+encodeURIComponent(sub)+'&noteMessage='+encodeURIComponent(msg)+'&leadId='+leadId+'&leadStatus='+leadStatus;
		 $.ajax({
				url: updateUrl,
		  		success: function( data ) {
		  		alert("done!");
		  		//$('#addingNote').html("");
		  		//$(' .btn-add-note ').show();
		  		window.location.href = '/MarketFrontend/leadsManagementController_viewLeadDetails.action?leadId='+leadId;
		  		}
		  		});	 
	 }
	  return false;
	});

	// Modals
	//===============================
	var viewportWidth = $(window).width();
	var viewportHeight = $(window).height();
/*
	// Makes modals full screen height on mobile
	if (viewportWidth < '700') {
		// Show modal so we can get the dimensions
		$('.modal').css({
			//position: 	"absolute", 
			visibility: "hidden",
			display: 	"block"
		});
		// Get the dimensions & calculate the height of the modal-body section
		var modalHeight = $( '.modal-dialog' ).height();
		var modalBodyHeight = $( '.modal-body' ).height();
		var offset = (viewportHeight - modalHeight);
		var newBodyHeight = (offset + modalBodyHeight - 30); // -30 extra "mystery" pixels
		// Set the modal-body height
		$( '.modal-body' ).height(newBodyHeight);

		// Set the modal back to hidden
		$('.modal').css({
			//position: 	"fixed", 
			visibility: "visible",
			display: 	"none"
		});
	}*/

	// Attachments Widget
	//===============================
	// 
	// This code just displays the visual interaction of prototype, will need to update for prod using Ajax.
	$('#widget-add-attachment form').submit( function() { 
		// Disable submit button while file uploads
		$('button[type="submit"]', this).attr('disabled','disabled');
		// Set progress bar width to 0
		$(' #widget-add-attachment .progress-bar ').css('width', '0%');
		// Fade in progress bar
		$(' #widget-add-attachment .progress ').fadeIn( 
			'fast', 
			// Animate as it uploads files
			function() {
				$(' #widget-add-attachment .progress-bar ').css(
					'width',
					'100%'
				);
    }
		);
		// wait a sec, then fade out the progress bar
		$(' #widget-add-attachment .progress ').delay( 800 ).fadeOut(
			'fast',
			function() {
				// Enable submit button
				$('#widget-add-attachment button[type="submit"]').removeAttr('disabled');
	}
		);
		// Just for prototype, will need to update for prod.
		return false; 
	});

	// Cancel Order Widget
	//===============================
	$( '#cancelWidget' ).on( 'show.bs.modal', function (e) {
		$('#cancel-reason-other').parents('.row').hide();
		//$('.cancel-reason').hide();
	});

	// Select box
	var sel = $( '#reasonCode' );
	/*
	// All reasons
	var opts = sel.find( 'option' );
	// Blank option
	var blankOpt = opts.filter( '[value=""]' );
	// Firm cancelation reasons
	var firmOpts = opts.filter( '[class*="firm-rsn"]' );
	// Customer cancelation reasons
	var custOpts = opts.filter( '[class*="cust-rsn"]' );

	$( 'input[name="leadCancelInitiatedBy"]' ).change(function() {	

		if ( $(this).val() == '2' ) { 
			sel.empty().append(blankOpt).append(firmOpts);
		}
		else {
			sel.empty().append(blankOpt).append(custOpts);
		}

		// Sets the selected option to ""
		sel.val("");

		// Checks if 'Other' was selected & hides the extra input
		hiddenInput();

		// Show the followup questions
		$('.cancel-reason').show();
	});*/

	// "Other" open text field
	function hiddenInput() {
	
		if ( $( '#reasonCode option:selected' ).hasClass('other') ) {
			$('#cancel-reason-other').parents('.row').show();
			$('#cancel-reason-other').focus();
		}
		else {
			$('#cancel-reason-other').parents('.row').hide();
		}
	}

	sel.change( function() {
		hiddenInput();
	});


	// Call Customer Widget
	//===============================
	$( '#callWidget' ).on( 'show.bs.modal', function (e) {
		// hides additional questions
		$('.cust-avail-rsn').hide();
		// hides 'Other' fields & labels
		$('#not-avail-rsn-other').parents('.row').hide();
		$('#avail-rsn-other').parents('.row').hide();
	});

	$( '#callWidget form > .row > .col-sm-8 .btn' ).click(function() {
		$( '#callWidget form > .row > .col-sm-8 .btn' ).removeClass( 'active' );
		$(this).addClass( 'active' );
	});

	// Select box
	var sel2 = $( '#cust-avail-rsn' );
	// All reasons
	var opts = sel2.find( 'option' );
	// Blank option
	var blankOpt2 = opts.filter( '[value=""]' );
	// Firm cancelation reasons
	var wonOpts = opts.filter( '[class*="won-rsn"]' );
	// Customer cancelation reasons
	var lostOpts = opts.filter( '[class*="lost-rsn"]' );
	// Selected Option
	//var selectedOpt = $(select.options[select.selectedIndex];

	$( 'input[name="cust-interest"]' ).change(function() {	

		if ( $(this).val() == 'cust-interest-yes' ) { 
			sel2.empty().append(blankOpt2).append(wonOpts);
		}
		else {
			sel2.empty().append(blankOpt2).append(lostOpts);
		}

		// Sets the selected option to ""
		sel2.val("");

		// Checks if 'Other' was selected & hides the extra input
		hiddenInput2();

		// Show the followup questions
		$('.cust-avail-rsn').show();
	});

	
	// "Other" open text field
	function hiddenInput2() {
	
		if ( $( '#cust-avail-rsn option:selected' ).hasClass('other') ) {
			$('#avail-rsn-other').parents('.row').show();
			$('#avail-rsn-other').focus();
		}
		else {
			$('#avail-rsn-other').parents('.row').hide();
		}
	}
	// Checks if Other was selected when switching between tabs
	sel2.change( function() {
		hiddenInput2();
	});

	// Not Available, 'Other' hidden input
	$( '#cust-not-avail-rsn' ).change(function() {
		if ( $( '#cust-not-avail-rsn option:selected' ).hasClass('other') ) {
			$('#not-avail-rsn-other').parents('.row').show();
			$('#not-avail-rsn-other').focus();
		}
		else {
			$('#not-avail-rsn-other').parents('.row').hide();
		}
	});	


	//Plugin Initializations 
	//===============================
	// Date Picker
	$('.datepicker').pickadate({
		format: 'm/d/yy',
		formatSubmit: 'mm/dd/yyyy',
		clear: '',
		today: ''
	});
	$('.timepicker').pickatime({
		interval: 30,
		clear: '',
		min: [8,0],
    	max: [7,30]
	});
	// Bootstrap Tooltips
	$('.tooltip-target').tooltip();
	// ERROR exists in this code, leave at bottom until fix is implemented or it breaks everything after
	$('time.timeago').timeago();


});