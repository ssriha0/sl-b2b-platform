     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     <c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
     
     <script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/plugins/ajaxfileupload.js"></script>
     		<script type="text/javascript">

    function viewDocument(docId,docCategoryId,docPath)
    		{
    			 if(8 == docCategoryId){                                                                  
                     window.parent.showVideoTop(docPath);
              		}
    			 else{
                     var loadForm = document.getElementById('docDownload');
                     loadForm.action = "leadsManagementController_viewDocument.action?docId="+docId;
                     loadForm.submit();
              		}
    		}
  	function removeDocument(docId)
		{
				
		var leadId='${lmTabDTO.lead.leadId}';
    	var formValues = $('#docDownload').serializeArray();
    	$.ajax({
	  		url: 'leadsManagementController_deleteDocument.action?docId='+docId+'&leadId='+leadId,
			type: "POST",
			data: formValues,
			dataType : "json",
	  		success: function( data ) {
				window.location.href = '/MarketFrontend/leadsManagementController_viewLeadDetails.action?leadId='+leadId;
				}
		});
		}
		
		function validateFileName(field){
			$("#uploadButton").hide();
			$("#uploadButton").attr("disabled", true);
			var leadId='${lmTabDTO.lead.leadId}';
			var fieldVal = document.getElementById(field).value;
			fieldVal = fieldVal.replace(/%/g, "-prcntg-");
			fieldVal = encodeURIComponent(fieldVal);
			var formValues = $('#documentsForm').serializeArray();
			if (fieldVal != null && fieldVal.length != 0){
				// Set progress bar width to 0
				$(' #widget-add-attachment .progress-bar ').css('width', '0%');
				// Fade in progress bar
				$(' #widget-add-attachment .progress ').fadeIn( 
					1000, 
					// Animate as it uploads files
					function() {
						$(' #widget-add-attachment .progress-bar ').css(
							'width',
							'100%'
						);
		    		}
				);
				jQuery.ajaxFileUpload
				        (
				            {
				                url:'leadsManagementController_uploadDocument.action?fileName='+fieldVal+'&leadId='+leadId,
				                secureuri:false,
				                type: "POST",
				                data: formValues,
				                fileElementId:'fileSelect',
				                dataType : 'json',
				                success: function (data)
				                {
				                	// alert("json result1:"+data.responseMessage);
				                	if(data.responseMessage == 'Document Uploaded Succesfully'){
				                		window.location.href = '/MarketFrontend/leadsManagementController_viewLeadDetails.action?leadId='+leadId;
				                	}else{
				                		$("#uploadResp").html(data.responseMessage);
				                		$("#uploadResp").show();
				                		$('#widget-add-attachment .progress').fadeOut();
				                		$("#uploadButton").show();
				                	}
				           		 },
				                error: function (data, status, e){
				                	$('#uploadResp').html('An error occured while uploading document. <br>Please try again with a file no larger than 10 MB.');
									$('#uploadResp').show();
									$('#widget-add-attachment .progress').fadeOut();
				                	$("#uploadButton").show();
				                	//window.location.href = '/MarketFrontend/leadsManagementController_viewLeadDetails.action?leadId='+leadId;
				                }
				        });

		}else{
			$("#uploadResp").html("Please select a file to upload.");
			$("#uploadResp").show();
			$("#uploadButton").show();
		}
			$("#uploadButton").attr("disabled", false);
		}
		
			</script>
     
      <div class="tab-pane" id="attachments">
					 <section>
					<!--<a href="#" class="btn btn-default full btn-add-attachment" role="button"><i class="glyphicon glyphicon-file"></i> Add Attachment</a>-->
					<div class="row" id="widget-add-attachment">
						<div id="uploadResp" style="margin-left:5px;width: 56%;margin-left:20px;display: none;" class="buyerLeadError" ></div>
					  <form class="col-xs-12" method="POST" id="documentsForm" enctype="multipart/form-data">
					    
						<small class="pull-right">10 MB maximum file size</small>
					    <label for="fileSelect"><i class="glyphicon glyphicon-paperclip"></i> Attach Files</label>
					    <input type="file" id="fileSelect" name="fileSelect"></input>
						<!-- <span>.doc|.pdf|.xls|.xlsx|.docx|.zip|.gif|.jpeg|.jpg|.pjpeg|.png|.tiff|.bmp|.txt|.xml</span> -->
					    <div class="row">
					    	<div class="col-sm-1">
								<button type="button" name="uploadButton" id ="uploadButton" class="btn btn-default full margin-top" onclick="validateFileName('fileSelect')">Upload</button>
							</div>
							<div class="col-sm-11">
								<div class="progress progress-striped active">
								  <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">
								  </div>
								</div>
							</div>
						</div>

					  </form>
					</div><!-- /#widget-add-attachment -->
				</section>
 				<form method="POST" id="docDownload">
				<c:if test="${lmTabDTO.lead.attachments!=null}">
               <c:forEach items="${lmTabDTO.lead.attachments.attachmentList}" var="attachment">
				<!--   <div class="file" style="border-top: 1px solid #DDDDDD;margin-top:10px; padding-left: 20px;"> -->
				<ul>
					<li class="file"  style="border-bottom:1px solid #DDDDDD">
						<a href="#" onclick="removeDocument('${attachment.documentId}')" class="update-target tooltip-target" data-placement="left" data-original-title="Delete File">
					 <i class="glyphicon glyphicon-trash"></i>
					</a>
						<h3><a href="#" onclick="viewDocument('${attachment.documentId}','${attachment.docCategoryId}','${attachment.docPath}')">${attachment.documentName}</a></h3>
						<div class="secondary-info">
							<span class="pull-right">Posted by ${attachment.firstName} ${attachment.lastName}</span>
							<b>${attachment.documentSize} KB</b> 
							<time>${attachment.createdDate}</time>
						</div>  
				</li>
				</ul>
						</c:forEach>
				</c:if>
				<c:if test="${empty lmTabDTO.lead.attachments}"> 
				<h3>No Attachments Found</h3>
				</c:if>
		</form>
		</div><!-- #attachments -->
		
