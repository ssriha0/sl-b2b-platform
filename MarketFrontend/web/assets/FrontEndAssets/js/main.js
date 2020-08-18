$(function(){

	// Add a Note Widget
//===============================
var addNote = $( '#widget-add-note' );
addNote.hide();
// Hide the "Add Note" button and show the form
//display the add note div
 $(' .btn-add-note ').click(function() {
	$( this ).hide();
	addNote.show();
	$( 'html,body' ).animate({ scrollTop: addNote.offset().top - 20 }, 300)
	return false;
 });
//submit note
 $( '.submitNote' ).click(function() {

	 var sub ;
	 var msg ;
	 var action = this.id;
	 var leadId = $('#addNoteLeadId').val();
	 var leadNoteId = $('#leadNoteId').val();	
	 
	 if(action == 'addNote'){//add note
		sub =  $('#note_subject').val();
		msg =  $('#note_message').val();
		leadNoteId = null;
	 }else{//edit mode
		var index = action.indexOf('_');
		var leadNoteId = action.substring(index+1);
		sub =  $('#note_subject_edit_'+leadNoteId).val();
		msg =  $('#note_message_edit_'+leadNoteId).val();
	 }
	
	 var errMsg = "";
	 if(jQuery.trim(sub) == ""){
		 errMsg = errMsg + "Please enter Subject.<br>";
	 }if(jQuery.trim(msg) == ""){
		 errMsg = errMsg + "Please enter Message.<br>"; 
	 }
	 if(errMsg!=""){
		 $('#responseMsg').html(errMsg);
		  $('#responseMsg').show();
		 return false;
	 }
	 else{
		 $('#responseMsg').html("");
		  $('#responseMsg').hide();
		 $('.btn-default').hide();
		 if(action == 'addNote'){
		 	$('#addingNote').show();
		 }else{
		 	$('#updatingNote_'+leadNoteId).show();
		 }
		 var updateUrl='leadsManagementController_addNote.action?noteSubject='+encodeURIComponent(sub)+'&noteMessage='+encodeURIComponent(msg)+'&leadId='+leadId+'&leadNoteId='+leadNoteId;
		 $.ajax({
				url: updateUrl,
				type: "POST",
		  		success: function( data ) {
		  		window.location.href = '/MarketFrontend/leadsManagementController_viewLeadDetails.action?leadId='+leadId;
		  		}
		  		});	 
	 }
	  return false;
 });

//edit note
 $(' .edit-note ').click(function() {
 
	$('.editNotes').hide();
	$('#widget-add-note').hide();
	$('.viewNotes').show();
	
	var divId = this.id;
	var index = divId.indexOf('_');
	var noteId = divId.substring(index+1);
	
	$('#viewNoteDiv_'+noteId).hide();
	$('#editNoteDiv_'+noteId).show();
	$('.btn-add-note').hide();
	
	var areavalue='';
	areavalue=$('#note_message_edit_'+noteId).val();
	var length=0;

	if(areavalue!=''){
	length=areavalue.length;
	}
	
	length=750-length;
	
	$('#comment_leftChars_edit_'+noteId).val(length);
	  
	return false;
 });

//cancel note
 $('.cancel').click(function() {
 
	var divId = this.id;
	var index = divId.indexOf('_');
	var noteId = divId.substring(index+1);
	
	$('#viewNoteDiv_'+noteId).show();
	$('#editNoteDiv_'+noteId).hide();
	$('#responseMsg').html("");
	$('#responseMsg').hide();
	$('.btn-add-note').show();
	  
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
		$('#comments').parents('.row').hide();
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
			$('#comments').parents('.row').show();
			$('#comments').focus();
		}
		else {
			$('#comments').parents('.row').hide();
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
	$('#cust-not-avail-rsn').change(function() {
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
	// Date Picker - min is now only for schedule/reschedule - Need to check for complete modal
	$('.datepickerSchedule').pickadate({
		min: new Date(),
		format: 'mm/dd/yyyy',
		formatSubmit: 'mm/dd/yyyy',
		clear: '',
		today: ''
	});
	$('.datepickerComplete').pickadate({
	    format: 'mm/dd/yyyy ',
		formatSubmit: 'mm/dd/yyyy',
		max:new Date(),
		clear: '',
		today: ''
	});
	$('.timepicker').pickatime({
		interval: 30,
		clear: '',
		min: [8,0],
    	max: [7,30],
		formatSubmit:'hh:i A'
	});
	// Bootstrap Tooltips
	$('.tooltip-target').tooltip();
	// ERROR exists in this code, leave at bottom until fix is implemented or it breaks everything after
	$('time.timeago').timeago();


});