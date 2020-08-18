/* Feedback Related Scripts */
jQuery(document).ready(function() {
	
	$('#fdbk_tab').click(function(event){
	
		$("#feedBackForm").load("displayFeedbackPopup.action",function(data){
			$("#feedBackForm").jqm({
				modal : true
			});
			//$("#feedBackForm").fadeIn('slow');
			$("#feedBackForm").jqmShow();
		});

	});
	
	$(document).on('click', ".icon-remove-circle,#closeFbkThanks", function(event){
		$(event.target).closest('.jqmWindow').jqmHide();			
	});
	
	
	$('#feedBackComments').on("keyup input paste",function(event){
		//SL-18927 CR for Feedback Tab:Increasing the total character from 255 to 750
	    var maxLen = 750;
	    var Length = $("#feedBackComments").val().length;
	   	if(Length >= maxLen){
	        if (event.which != 8) {
	        var feedBackComments=$('#feedBackComments').val();
	    	var limitedFeedBackComment=feedBackComments.substring(0,maxLen);
	    	$('#feedBackComments').val(limitedFeedBackComment);
	    	$("#comment_leftChars").val(0);
	            return false;
	        }
	    }
	   	else
	   		{
	   		var charRemaining=maxLen-Length;
	   		$("#comment_leftChars").val(charRemaining);
	   		}
	});	
	
	$(document).on('click', "#submitFeedbackbtn", function(event){
		$("#submitFeedbackbtn").prop("disabled",true);
		$("#submitFeedbackbtn").removeClass("feedBackActionButtonBackGround");
		var clickInd=$("#clickedInd").val();
			if(clickInd=='false')
			{
			$("#clickedInd").val('true');
			loadFeedbackThanks();
			}
		});
	
	function loadFeedbackThanks(){
		$("#feedbackResponseMessage").html("");
		$("#submitFeedbackbtn").prop("disabled",true);
		$("#submitFeedbackbtn").removeClass("feedBackActionButtonBackGround");
		var warnMsg = "";
		var sourceURL = window.location.href;
		var pageName = "OrderManagement";
		var categoryId =1;//SL 18927 Setting default category id as 1 for feedback
		var feedbackComments =$.trim($('#feedBackComments').val());
		
		if(feedbackComments == "" || feedbackComments == null){
	      	warnMsg += '<p style="margin-left:10px;font-size: 12px;">Please enter comments.</p>';
	    }
		//alert('feedbackComments'+feedbackComments);
		var encodedFeedbackComments=encodeURIComponent(feedbackComments);
		//alert('encodedFeedbackComments'+encodedFeedbackComments);
		if(warnMsg != ""){
			$("#feedbackResponseMessage").append(warnMsg);
	      	$("#feedbackResponseMessage").show();
	      	$("#submitFeedbackbtn").prop("disabled",false);
        	$("#submitFeedbackbtn").addClass("feedBackActionButtonBackGround");
			$("#clickedInd").val("false");
	      	return false;
		}else{	
			$("#feedbackResponseMessage").hide();
			$("#submitFeedbackbtn").prop("disabled",true);
			$("#submitFeedbackbtn").removeClass("feedBackActionButtonBackGround");
		  $.ajaxFileUpload
	        (
	            {
	                url:'saveFeedBack_saveFeedback.action?pageName='+pageName+'&categoryId='+categoryId+'&feedbackComments='+encodedFeedbackComments+'&sourceURL='+sourceURL,
	                secureuri:false,
	                fileElementId:'uploadFeedback',
	                dataType : 'json',
	                success: function (data)
	                {
	                			if(data == "success"){

			                						closeModal('feedBackForm');
			                						$("#feedBackThanks").addClass("jqmWindow");
			                						$("#feedBackThanks").css("width", "515px");
			                						$("#feedBackThanks").css("height", "150px");
			                						$("#feedBackThanks").css("marginLeft", "-250px");
			                						$("#feedBackThanks").jqm({
			                							modal : true
			                						});
			                						$("#feedBackThanks").fadeIn('slow');
			                						//$('#feedBackThanks').css('display', 'block');		
			                						$("#feedBackThanks").jqmShow();

	                			}else{
	                				$("#feedbackResponseMessage").empty();
									$("#clickedInd").val("false");
	                				$("#feedbackResponseMessage").hide();
	                				var totalErrorSize=data.lastIndexOf('.');
	                				var fileSizeError ="";
	                				var fileTypeError ="";
	                				if(totalErrorSize>100)
	                					{
	                					fileSizeError = data.substring(0,59);
	                					fileTypeError = data.substring(59,128);
	                					}
	                				else
	                					{
	                					if(totalErrorSize>58)
										{
	                						fileTypeError = data;
										}
										else
										{
											fileSizeError = data;
										}

	                					}
	        	                	$("#submitFeedbackbtn").prop("disabled",false);
	        	                	$("#submitFeedbackbtn").addClass("feedBackActionButtonBackGround");
	                				if(fileSizeError.length>0)
		                				{
		                				fileSizeError='<p style="margin-left:10px;font-size: 12px;">'+fileSizeError+'</p>';
		                				$("#feedbackResponseMessage").append(fileSizeError);
		                				}
	                				if(fileTypeError.length>0)
		                				{
	                					fileTypeError='<p style="margin-left:10px;font-size: 12px;">'+fileTypeError+'</p>';
	                					$("#feedbackResponseMessage").append(fileTypeError);
		                				}
		                			$("#feedbackResponseMessage").show();
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
