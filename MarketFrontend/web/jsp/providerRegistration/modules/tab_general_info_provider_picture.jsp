<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/providerProfile.css" />		
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/ajaxupload.js"></script>
		<script>
		function fnPopUp(){	
		/*
		alert('here');
		var retVal;	
		var url= '${contextPath}/jsp/providerRegistration/display_upload_photo_popup.jsp' ;	
		retVal=window.showModalDialog(url,'name','dialogWidth:300px;dialogHeight:150px');
		if(retVal==1){
		document.getElementById('generalInfoPictureform').submit();
		} 	
		*/
		document.getElementById('generalInfoPictureform').submit();
		}	
		$(document).ready(function(){
	
   			var filename = document.getElementById("fileName").value; 
   			var resId = $("#resourceId").val(); 
   			if(filename!= ''){
   			
   			$("#button_needhelp").hide();
   			$("#button_needhelp_after").show();
   			$("#info_before").hide();
			$("#info_after").show();
			$("#inner_document_grid").hide();
			var s="<iframe width='85px' scrolling='no' height='95px' marginwidth='0' marginheight='0' style='float: left; margin-right: 5px;'";
			var s1="frameborder='0'	src='${contextPath}/providerProfileInfoAction_loadPhoto.action?max_width=85&max_height=95&resourceId="+resId+"' name='inner_document_grid_after' id='inner_document_grid_after' style='float: left;'></iframe>";
			s=s+s1;
			$("#tempIframe").html(s);
			$("#tempIframe").show();

			var filesize = document.getElementById("fileSize").value;
			filesize=Math.round((filesize/(1024*1024))*100)/100;
			var statusHtml = "<strong>" + filename +"<br>(file size " + filesize +"mb)<br></strong>";
	    		$("#image_prop").html(statusHtml);
				$("#image_prop").show();
   			}
   			
   			  
   			var btnAttach = $("#attachDocumentBtn");   			 
   			   			
   			uploader = new AjaxUpload(btnAttach,{"name":"photoDoc",action:"generalInfoPicture_pictureUpload.action?resourceId="+resId,onComplete:uploadComplete,onSubmit:submitAttachment});
      		var linkAttach =$("#ChangePhoto");
      		linkUploader = new AjaxUpload(linkAttach,{"name":"photoDoc",action:"generalInfoPicture_pictureUpload.action?resourceId="+resId,onComplete:uploadCompleteLink,onSubmit:submitAttachmentLink});
		});	


		
		function submitAttachment(file, ext){
			
			$("#inner_document_grid").hide();
			$("#button_needhelp").hide();
			var statusHtml = "<strong> Uploading <br>" + file +" ...<br><br></strong>";
			$("#uploading").html(statusHtml);
			$("#uploading").show();
			return true;
		}
		
		function submitAttachmentLink(file, ext){
			$("#inner_document_grid").hide();
			$("#tempIframe").hide();
			$("#button_needhelp_after").hide();
			var statusHtml = "<strong> Uploading <br>" + file +" ...<br><br></strong>";
			$("#uploading").html(statusHtml);
			$("#uploading").show();
			return true;
		}
			
		function uploadComplete(file, response){
	   		var status=$("result",response).attr("status");
	   		var resId = $("#resourceId").val();
	    	var filename=$("result",response).attr("filename");
	    	var filesize=$("result",response).attr("filesize");   
	    	filesize=Math.round((filesize/(1024*1024))*100)/100;
	   
	    	if(status=="success"){
	    		$("#uploading").hide();
	    	   	var statusHtml = "<strong>" + filename +"<br>(file size" + filesize +"mb)<br></strong>";
	    		$("#image_prop").html(statusHtml);
				$("#image_prop").show();
				$("#button_needhelp_after").show();
				var s="<iframe width='85px' scrolling='no' height='95px' marginwidth='0' marginheight='0' style='float: left; margin-right: 5px;'";
				var s1="frameborder='0'	src='${contextPath}/providerProfileInfoAction_loadPhoto.action?max_width=85&max_height=95&resourceId="+resId+"' name='inner_document_grid_after' id='inner_document_grid_after' style='float: left;'></iframe>";
				s=s+s1;
				$("#tempIframe").html(s);
				$("#tempIframe").show();
				$("#info_before").hide();
				$("#info_after").show();
		    }else{
	      
	     		$("#uploading").hide();
	      		$("#inner_document_grid").show();
				$("#button_needhelp").show();
	     
	    	}
		}
		
		function uploadCompleteLink(file, response){
	   		var status=$("result",response).attr("status");
	   		var resId = $("#resourceId").val();
	    	var filename=$("result",response).attr("filename");
	    	var filesize=$("result",response).attr("filesize");   
	    	filesize=Math.round((filesize/(1024*1024))*100)/100;
	   
	    	if(status=="success"){
	    		$("#uploading").hide();
	    	   	var statusHtml = "<strong>" + filename +"<br>(file size" + filesize +"mb)<br></strong>";
	    		$("#image_prop").html(statusHtml);
				$("#image_prop").show();
				$("#button_needhelp_after").show();
				
				
				var s="<iframe width='85px' scrolling='no' height='95px' marginwidth='0' marginheight='0' style='float: left; margin-right: 5px;'";
				var s1="frameborder='0'	src='${contextPath}/providerProfileInfoAction_loadPhoto.action?max_width=85&max_height=95&resourceId="+resId+"&rand="+Math.random()+"' name='inner_document_grid_after' id='inner_document_grid_after' style='float: left;'></iframe>";
				s=s+s1;
				$("#tempIframe").html(s);
				$("#tempIframe").show();
				//alert("tempIframe should be updated here");
			
				

		    }else{
	      		
	     		$("#uploading").hide();
	      		$("#tempIframe").show();
				$("#button_needhelp_after").show();
	     
	    	}
		}
		</script>

		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />

<div style="padding-left: 10px; padding-top: 5px;">
	
	<table>
	
	<tr>
	<div id="before_upload">
	
	  <td>
		<iframe width="85px" scrolling="no" height="95px" marginwidth="0" marginheight="0" style="float: left; margin-right: 5px;"
			frameborder="0"
			src="${contextPath}/providerProfileInfoAction_loadPhoto.action?max_width=85&max_height=95&resourceId=${resourceId}"
			name="inner_document_grid" id="inner_document_grid" style="float: left;">
		</iframe>
		
	</td>

	<td>
		
	
 		<div style="margin-left: 5px; margin-right:5px; " id="button_needhelp">
			<s:form action="generalInfoPicture_pictureUpload" id="generalInfoPictureform" 
        	theme="simple" name="docPhotoForm"enctype="multipart/form-data" method="POST">

				<input type="hidden" name="resourceId" id="resourceId" value="${resourceId}"/>
				<input type="hidden" name="fileSize" id="fileSize" value="${fileSize}"/>
				<input type="hidden" name="fileName" id="fileName" value="${fileName}"/>
				
				<table cellpadding="0" cellspacing="2px">
					<tr>
						<td>
							<input type="button" id="attachDocumentBtn"
							class="submitButton" value="SELECT PHOTO" />
						</td>
					</tr>
					<tr>
						<td>
							<br><br><br>
						</td>
					</tr>
					<tr>
						<td>
							<a target="_blank" href="http://community.servicelive.com/docs/Provide-Profile-instructions.docx">Need help?</a>
						</td>

					</tr>
				</table>
			</s:form>
		</div>
		
	</td>
	</div>
	
	<td><div id="uploading" style="display:none;" class="tempBg"></div></td>
	
	<div id="after_upload">
	<td width="100px">
		<div id="tempIframe" style="display: none"></div>
	
	</td>
	<td>
 		<div style="margin-left: 5px; margin-right:5px; display:none;" id="button_needhelp_after">
			<s:form action="generalInfoPicture_pictureUpload" id="generalInfoPictureform" 
        	theme="simple" name="docPhotoForm"enctype="multipart/form-data" method="POST">

				<input type="hidden" name="resourceId" value="${resourceId}"/>
				<table cellpadding="0" cellspacing="2px">
					<tr>
						<td>
							<div id=image_prop class="imageProp">
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<a id ="ChangePhoto" name="ChangePhoto" value="Change Photo" class="linkBlue">Change Photo</a>
						</td>
					</tr>
					<tr>
						<td>
							<a target="_blank" href="http://community.servicelive.com/docs/Provide-Profile-instructions.docx">Need help?</a>
						</td>

					</tr>
				</table>
			</s:form>
		</div>
		
	</td>
	</div>
	
	</div>
	
	<td>
		<td><img src="${staticContextPath}/images/s_icons/information.png"></td>
		<td>
		<div id="info_before">
			
			Most buyers look for and may require a photo for identification.
			<br />
			<strong>Accepted File Types:</strong> .jpg .jpeg |.gif
			<br />
			<strong>Maximum File Size:</strong>5 mb
		</div>
		
		<div id="info_after" style="display:none;">
				
			<strong>All Photos Are Audited by ServiceLive</strong><br /> 
			Photos including company logos, signs, contact information or other
			promotional materials with a business name in the foreground or
			background will not be permitted.<br />
			<strong>Photo Use</strong><br />
			Photos of providers are displayed on the provider profile page, and may
			be included on printed materials for buyer identification purposes.

		</div>
		</td>
	</td>
	</tr>
	
	</table>

  	<br clear="all" />
</div>
