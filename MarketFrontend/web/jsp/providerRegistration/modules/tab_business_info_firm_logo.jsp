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
		document.getElementById('generalInfoPictureform').submit();
		}	
		$(document).ready(function(){
			$("#descriptionErrorLogos").hide();	

   			var filename = document.getElementById("fileName").value; 
   			var vendorId = $("#vendorId").val(); 
   			if(filename!= ''){
   			$("#button_needhelp").hide();
   			$("#button_needhelp_after").show();
   			$("#info_before").hide();
			$("#info_after").show();
			$("#inner_document_grid").hide();
			var s="<iframe width='85px' scrolling='no' height='95px' marginwidth='0' marginheight='0' style='float: left; margin-right: 5px;'";
			var s1="frameborder='0'	src='${contextPath}/firmLogoAction_loadPhoto.action?max_width=85&max_height=95&vendorId="+vendorId+"' name='inner_document_grid_after' id='inner_document_grid_after' style='float: left;'></iframe>";
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
   			uploader = new AjaxUpload(btnAttach,{"name":"photoDoc",action:"firmLogoAction_pictureUpload.action?vendorId="+vendorId,onComplete:uploadComplete,onSubmit:submitAttachment});
      		var linkAttach =$("#ChangeLogo");
      		linkUploader = new AjaxUpload(linkAttach,{"name":"photoDoc",action:"firmLogoAction_pictureUpload.action?vendorId="+vendorId,onComplete:uploadCompleteLink,onSubmit:submitAttachmentLink});
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
	   		var vendorId = $("#vendorId").val();
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
				var s1="frameborder='0'	src='${contextPath}/firmLogoAction_loadPhoto.action?max_width=85&max_height=95&vendorId="+vendorId+"' name='inner_document_grid_after' id='inner_document_grid_after' style='float: left;'></iframe>";
				s=s+s1;
				$("#tempIframe").html(s);
				$("#tempIframe").show();
				$("#info_before").hide();
				$("#info_after").show();
				$("#descriptionErrorLogos").hide();	

		    }else{
		    	var errormsg=$("result",response).attr("errormsg");
				var errorHtml = "<p class='errorMsg'>" + errormsg +" </p>";
				$("#descriptionErrorLogos").html(errorHtml);
				$("#descriptionErrorLogos").show();	
	     		$("#uploading").hide();
	      		$("#inner_document_grid").show();
				$("#button_needhelp").show();
				  
	    	}
		}
		
		function uploadCompleteLink(file, response){
	   		var status=$("result",response).attr("status");
	   		var vendorId = $("#vendorId").val();
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
				var s1="frameborder='0'	src='${contextPath}/firmLogoAction_loadPhoto.action?max_width=85&max_height=95&vendorId="+vendorId+"&rand="+Math.random()+"' name='inner_document_grid_after' id='inner_document_grid_after' style='float: left;'></iframe>";
				s=s+s1;
				$("#tempIframe").html(s);
				$("#tempIframe").show();
				$("#descriptionErrorLogos").hide();	

			
		    }else{
		    	var errormsg=$("result",response).attr("errormsg");
				var errorHtml = "<p class='errorMsg'>" + errormsg +" </p>";
				$("#descriptionErrorLogos").html(errorHtml);
				$("#descriptionErrorLogos").show();	
	     		$("#uploading").hide();
	      		$("#tempIframe").show();
				$("#button_needhelp_after").show();
				
	     
	    	}
		}
		</script>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />

<div style="padding-left: 10px; padding-top: 5px;">
	<div id="descriptionErrorLogos" class="errorBox clearfix" style="width: 645px; display: none;margin-bottom: 5px;">
						
	</div>
	<table>

	<tr>
	<div id="before_upload">
	
	  <td>
		<iframe width="85px" scrolling="no" height="95px" marginwidth="0" marginheight="0" style="float: left; margin-right: 5px;"
			frameborder="0"
			src="${contextPath}/firmLogoAction_loadPhoto.action?max_width=85&max_height=95&vendorId=${vendorId}"
			name="inner_document_grid" id="inner_document_grid" style="float: left;">
		</iframe>
		
	</td>

	<td>
		
	
 		<div style="margin-left: 5px; margin-right:5px; " id="button_needhelp">
			<s:form action="firmLogoAction_pictureUpload" id="generalInfoPictureform" 
        	theme="simple" name="docPhotoForm"enctype="multipart/form-data" method="POST">

				<input type="hidden" name="vendorId" id="vendorId" value="${vendorId}"/>
				<input type="hidden" name="fileSize" id="fileSize" value="${fileSize}"/>
				<input type="hidden" name="fileName" id="fileName" value="${fileName}"/>
				
				<table cellpadding="0" cellspacing="2px">
					<tr>
						<td>
							
						</td>
					</tr>
					<tr>
						<td>
							<br><br><input type="button" id="attachDocumentBtn"
							class="submitButton" value="SELECT LOGO" />
							<br><br>
							
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
			<s:form action="firmLogoAction_pictureUpload" id="generalInfoPictureform" 
        	theme="simple" name="docPhotoForm"enctype="multipart/form-data" method="POST">

				<input type="hidden" name="vendorId" value="${vendorId}"/>
				<br>
				<table cellpadding="0" cellspacing="2px">
					<tr>
						<td>
							<div id=image_prop class="imageProp">
							</div>
						</td>
					</tr>
					<tr>
						<td>
						<br>
							<a id ="ChangeLogo" name="ChangeLogo" value="Change Logo" class="linkBlue">Change Logo</a>
						</td>
					</tr>
					<tr>
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
			
			Please upload your company logo here.
			<br><br />
			<strong>Accepted File Types:</strong> .jpg .jpeg |.gif
			<br />
			<strong>Maximum File Size:</strong>5 mb
			<br>
		</div>
		
		<div id="info_after" style="display:none;">
				
			<strong>All Logos Are Audited by ServiceLive</strong><br /> 
			<br />

		</div>
		</td>
	</td>
	</tr>
	
	</table>
  	<br clear="all" />
</div>
