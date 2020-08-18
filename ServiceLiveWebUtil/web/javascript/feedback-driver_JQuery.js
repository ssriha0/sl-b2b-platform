/* Feedback Related Scripts */
jQuery(document).ready(function() {
	
	jQuery('#fdbk_tab').click(function(event){
	
		jQuery("#feedBackForm").load("displayFeedbackPopup.action",function(data){
			jQuery("#feedBackForm").jqm({
				modal : true
			});
			//jQuery("#feedBackForm").fadeIn('slow');
			jQuery("#feedBackForm").jqmShow();
		});

	});
	jQuery('#submitFeedbackbtn').live('click', function(event){
		loadFeedbackThanks();			
	});
	
	function loadFeedbackThanks(){
		var contactIndicator = jQuery('#contactInd').attr('checked');

		jQuery("#feedbackResponseMessage").html("");
		var warnMsg = "";
		var sourceURL = window.location.href;
		var pageName = "OrderManagement";
		var categoryId = jQuery('#feedBackCategory option:selected').val();
		var feedbackComments =jQuery.trim(jQuery('#feedBackComments').val());
		
		if(categoryId == -1){
	    	warnMsg += '<p style="margin-left:10px;font-size: 12px;">Please select a category.</p>';
	    }
		if(feedbackComments == "" || feedbackComments == null){
	      	warnMsg += '<p style="margin-left:10px;font-size: 12px;">Please enter comments.</p>';
	    }
		if(warnMsg != ""){
			jQuery("#feedbackResponseMessage").append(warnMsg);
	      	jQuery("#feedbackResponseMessage").show();
	      	return false;
		}else{	
			jQuery("#feedbackResponseMessage").hide();		
		  jQuery.ajaxFileUpload
	        (
	            {
	                url:'saveFeedBack_saveFeedback.action?contactIndicator='+contactIndicator+'&pageName='+pageName+'&categoryId='+categoryId+'&feedbackComments='+feedbackComments+'&sourceURL='+sourceURL,
	                secureuri:false,
	                fileElementId:'uploadFeedback',
	                dataType : 'json',
	                success: function (data)
	                {
	                			if(data == "success"){

			                						
			                						jQuery("#feedBackForm").jqmHide();			                						
			                						jQuery("#feedBackThanks").addClass("jqmWindow");
			                						jQuery("#feedBackThanks").css("width", "515px");
			                						jQuery("#feedBackThanks").css("height", "150px");
			                						jQuery("#feedBackThanks").css("marginLeft", "-250px");
			                						jQuery("#feedBackThanks").jqm({
			                							modal : true
			                						});
			                						jQuery("#feedBackThanks").fadeIn('slow');
			                						//jQuery('#feedBackThanks').css('display', 'block');		
			                						jQuery("#feedBackThanks").jqmShow();

	                			}else{
	                				jQuery("#feedbackResponseMessage").empty();
	                				jQuery("#feedbackResponseMessage").hide();
		                         	var fileSizeError = data.split('.')[0];
	                				var fileTypeError = data.split('.')[1];
	                				if(fileSizeError.length>0)
		                				{
		                				fileSizeError='<p style="margin-left:10px;font-size: 12px;">'+fileSizeError+'.</p>';
		                				jQuery("#feedbackResponseMessage").append(fileSizeError);
		                				}
	                				if(fileTypeError.length>0)
		                				{
	                					fileTypeError='<p style="margin-left:10px;font-size: 12px;">'+fileTypeError+'.</p>';
	                					jQuery("#feedbackResponseMessage").append(fileTypeError);
		                				}
		                			jQuery("#feedbackResponseMessage").show();
	                			}
	                	
	                   
	                },
	                error: function (data, status, e)
	                {
	                }
	                
	            }
	        )
		}
		
	}	
	
});
