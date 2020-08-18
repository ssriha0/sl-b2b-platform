<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<c:set var="maxComSize" scope="session" value="85" />

<!DOCTYPE html>
<html>

<head>
<base href="/" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8;no-cache" />
<meta charset=UTF-8> <meta name=viewport content="width=device-width,initial-scale=1"/>
<title>ServiceLive - Buyer Survey Manager</title>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/bootstrap/4.1.3/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/public.css"/>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/buyer-logo-uploader/dist/buyer-logo-uploader/styles.css"/>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buyerSurvey/buyerSurveyManagerPage.css"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="${staticContextPath}/javascript/libs/jquery/3.3.1/jquery-3.3.1.min.js" crossorigin="anonymous"></script>
<script src="${staticContextPath}/javascript/libs/popper.js/1.14.3/umd/popper.min.js" crossorigin="anonymous"></script>
<script src="${staticContextPath}/javascript/libs/bootstrap/4.1.3/bootstrap.min.js" crossorigin="anonymous"></script>
<style>
	.ie7 .bannerDiv {
		margin-left: -1020px;
	}
	
	 img {
		min-width:60;
		max-width:250;
		min-height :40;
		max-height:100;
		padding-left: 15px;
		padding-bottom:4px;
		text-align: left; 
		border:0; 	
	}
	th, td {
		padding: 4px 0px 4px 0px;
		font-size: 1em;		
	}
	.invalid-feedback {
	  display: none;
	  width: 100%;
	  margin-top: 0.25rem;
	  font-size: 100%;
	  color: #dc3545;
	}
	pre {
		white-space: pre-wrap;       
		white-space: -moz-pre-wrap;  
		white-space: -pre-wrap;      
		white-space: -o-pre-wrap;    
		word-wrap: break-word;       
	}
</style>
	 
	<script type="text/javascript">	
		
		function emailRadioSelected(selector, value, collapseSignSelector, index) {
			var count = index-1;
			if(value) {
				$(selector).collapse('show');
				$(collapseSignSelector).html('-');	
				$("#emailSignTextArea"+index).removeAttr("disabled");		
				$("input[name='emailEventList["+count+"].statusEventOption']").removeAttr("disabled");
				$("input[name='emailEventList["+count+"].surveyOptionId']").removeAttr("disabled");
				$("input[name='emailEventList["+count+"].statusOption']").removeAttr("disabled");
			}
			else
			{
				$(selector).collapse('hide');
				$(collapseSignSelector).html('+');
				var emailSign = $("#emailSignTextArea"+index);
				emailSign.removeClass('is-invalid');
				$(emailSign).attr("disabled", "disabled");
				
				var statusEventOptions = $("#statusEventOptions"+index);
				statusEventOptions.removeClass('is-invalid');
				
				$("#completedOrderStatus").removeClass('is-invalid');
				$("#eventInvalid"+count).hide();
				
				var surveyOptionId = document.getElementsByName("emailEventList["+count+"].surveyOptionId");
				$(surveyOptionId).removeClass('is-invalid');
				$("#surveyInvalid"+count).hide();
				
				$("input[name='emailEventList["+count+"].statusEventOption']").attr("disabled", "disabled");
				$("input[name='emailEventList["+count+"].surveyOptionId']").attr("disabled", "disabled");
				$("input[name='emailEventList["+count+"].statusOption']").attr("disabled", "disabled");
			}
		}
		
		function surveyTypeRadioSelected(surveyTypeTemplateImageURI) {
			$("#surveyTypeEmailTemplateView").show();
			
			$("#csatOnlySurveyTypeEmailContent").hide();
			$("#npsOnlySurveyTypeEmailContent").hide();
			$("#csatPrioritizeSurveyTypeEmailContent").hide();
			$("#npsPrioritizeSurveyTypeEmailContent").hide();
			
			$(surveyTypeTemplateImageURI).show();
			
		}
		
		/* 
		 * This function validate buyer logo.
		 */
		function valiadateBuyerName()
		{
			
			if($.trim($("#buyername").val()).length === 0) {
				$("#buyername").addClass('is-invalid');
			}
			else
			{
            	$("#buyername").removeClass('is-invalid');
        	}
		}
		
		/* 
		 * This function validate buyer logo.
		 */
		function valiadateBuyerLogo()
		{
			
			if($("#buyerlogo").val().length === 0 ) {
				$("#buyerlogo").addClass('is-invalid');
				$("#invalidLogo").show();
			}
			else {
				$("#buyerlogo").removeClass('is-invalid');
				$("#invalidLogo").hide();
			}
		}
		
		function valiadateSignature(index)
		{
			var emailSign = $("#emailSignTextArea"+index);
		
			if($.trim(emailSign.val()).length === 0) {
				emailSign.addClass('is-invalid');
			}
			else
			{
				emailSign.removeClass('is-invalid');
			}
		}
		
		function valiadateStatusEventOptions(i,index)
		{
			var statusEventOptions = $("#statusEventOptions"+i);
			var isValid=false;		
			var statusEventOption = document.getElementsByName("emailEventList["+index+"].statusEventOption");
			for(var count=0;count<statusEventOption.length;count++)
			{
				if(statusEventOption[count].checked == true)
				{
					isValid =true;
				}			
			}
			
			if (isValid)
			{
				statusEventOptions.removeClass('is-invalid');
				$("#eventInvalid"+index).hide();
			}
			else
			{
				statusEventOptions.addClass('is-invalid');
				$("#eventInvalid"+index).show();
				
			}
		}
		
		function valiadateSurveyTypeOption(index)
		{
			var surveyOption = $("#surveyOption"+index);
			var isValid=false;		
			var surveyOptionId = document.getElementsByName("emailEventList["+index+"].surveyOptionId");
			for(var count=0;count<surveyOptionId.length;count++)
			{
				if(surveyOptionId[count].checked == true)
				{
					isValid =true;
				}			
			}
			
			if (isValid)
			{
				surveyOption.removeClass('is-invalid');
				$("#surveyInvalid"+index).hide();
			}
			else
			{
				surveyOption.addClass('is-invalid');
				$("#surveyInvalid"+index).show();
			}
		}
		
		function valiadateSurveyTypeStatus(i,index)
		{
			var orderStatus = $("#completedOrderStatus");
			var isValid=false;		
			var statusOption = document.getElementsByName("emailEventList["+index+"].statusOption");
			for(var count=0;count<statusOption.length;count++)
			{
				if(statusOption[count].checked == true)
				{
					isValid =true;
				}			
			}

			if (isValid)
			{
				orderStatus.removeClass('is-invalid');
				$("#eventInvalid"+index).hide();
			}
			else
			{
				orderStatus.addClass('is-invalid');
			}
		}
		/* 
		 * This function validate order confirmation email.
		 */
		function validateOrderConfirmationEmail() {
			var eventListSize = $("input[id='eventListSize']").val();
		
			for (var i=1; i<=eventListSize; i++){
				var index =i-1;
				var emailRadioValue = document.getElementById("emailRadioOptions"+i+"_active").checked;
				
				if(emailRadioValue) {
					valiadateSignature(i);
					valiadateStatusEventOptions(i,index);
					if($("#eventType"+i).val()=='CONSUMER_SURVEY'){
							valiadateSurveyTypeOption(index);
							valiadateSurveyTypeStatus(i,index);
					}
					
				}
			}
			
		}
		
		/* 
		 * This function validate buyer address.
		 */
		function valiadateBuyerAddress() {
			
			if($.trim($("#address").val()).length === 0) {
				$("#address").addClass('is-invalid');
			}
			else
			{
				$("#address").removeClass('is-invalid');
			}
		}
		
		function togglePreviewLink() {
			var eventListSize = $("input[id='eventListSize']").val();
			
			for (var index=1; index<=eventListSize; index++) {
			
			var signatureTextArea = document.getElementById('emailSignTextArea' + index);
			
				if(signatureTextArea)
				{
					var previewLinkElement = document.getElementById('previewLink' + index);
					if(previewLinkElement)
					{
						if ( (signatureTextArea.value.length > 0) && ($("#address").val().length > 0) && ($("#buyername").val().length > 0) ) {
							$(previewLinkElement).removeAttr("disabled");
						}
						else
						{
							$(previewLinkElement).attr("disabled", "disabled");
						}
					}
				}
				var emailRadioValue = document.getElementById("emailRadioOptions"+index+"_inactive").checked;
				
				if(emailRadioValue){
					$(signatureTextArea).attr("disabled", "disabled");
					var count=index-1;
					
					var statusEventOption = $("input[name='emailEventList["+count+"].statusEventOption']");
					$(statusEventOption).attr("disabled", "disabled");
					
					var surveyOption = $("input[name='emailEventList["+count+"].surveyOptionId']");
					$(surveyOption).attr("disabled", "disabled");
					
					var statusOption = $("input[name='emailEventList["+count+"].statusOption']");
					$(statusOption).attr("disabled", "disabled");					
					
				}
			}
			
			if($("#surveyOptionId").val()==null || $("#surveyOptionId").val()==""){
				$("#surveyEmailTemplateView").hide();
			}
			
		}
		
		function configureEmailSignatureTextAreaChanges() {
			
			for (var loop=1; loop<=6; loop++) {
				(function(index) {
					var signatureTextArea = document.getElementById('emailSignTextArea' + index);
					if(signatureTextArea)
					{
						signatureTextArea.addEventListener('input', function() {
						    
							var previewLinkElement = document.getElementById('previewLink' + index);
							if(previewLinkElement)
							{
								//console.log('previewLinkElement :: ', previewLinkElement);
								if ( (signatureTextArea.value.length > 0) && ($("#address").val().length > 0) && ($("#buyername").val().length > 0) ) {
									$(previewLinkElement).removeAttr("disabled");
								}
								else
								{
									$(previewLinkElement).attr("disabled", "disabled");
								}
							}
						});
					}
				})(loop);
			}
		}
		
		function passDataToPreviewModal() {
			$('#previewModal').on('show.bs.modal', function (event) {
				
				var button = $(event.relatedTarget) // Button that triggered the modal
				if(button)
				{
					console.log("button found");
					var emailEventName = button.data('emaileventname') // Extract info from data-* attributes
					var emailEventDescr = button.data('emaileventdescr') // Extract info from data-* attributes
					var emailEventType = button.data('emaileventtype')
					var index = button.data('index')
					var emaileventsign = $("#emailSignTextArea"+(index+1)).val();
					$("#previewSignature").val(emaileventsign)
					
					if(emailEventType=='CONSUMER_SURVEY'){
						var surveyOption = $("input:radio[name='emailEventList["+index+"].surveyOptionId']:checked").val();
						$('#surveyEventMap').val(surveyOption);
						emailEventName=$("#surveyEventMap option:selected" ).text();		 
					}
					var previewURL="MarketFrontend/buyerSurvey_getPreviewTemplate.action?sign="+emaileventsign+"&eventName="+emailEventName;
					
					console.log("emailEventName :: ", emailEventName, ', emailEventDescr:: ', emailEventDescr);
					var formInput=$('#surveyForm').serialize();
					$("#emailTemplate").html("");
					$.ajax({
							   url: previewURL,
							   type: "POST",
							   data: formInput,
							   dataType : "html",
							   success: function(data) {
								   
								   if(emailEventName=="SERVICE_COMPLETION_NPS"){
									$("#previewDialog").addClass("modal-lg");
									$("#previewDialog").css("max-width","620px");									
								   }
								   $("#emailTemplate").html(data);
							  },
							  error: function(xhr, status, err) {
							   console.log("get preview template funciton"+err);
							  }             
                           });
					
					// If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
					// Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
					var modal = $(this)
					modal.find('.modal-title').text(emailEventDescr);
					
				}
			});
		}
		
		function showEmailContent(index) {
		 var surveyOption = $("input:radio[name='emailEventList["+index+"].surveyOptionId']:checked").val();
		 $('#surveyEventMap').val(surveyOption);
		 var eventName=$("#surveyEventMap option:selected" ).text();
		 $('#templateMap').val(eventName);
		 var emailContent=$("#templateMap option:selected" ).text();
		 $("#surveyTypeEmailContent").html(emailContent);
		 $("#surveyEmailTemplateView").show();
		 if(eventName == 'SERVICE_COMPLETION_CSAT'){
			$("#surveyTypeEmailContent").css("width","500px");	
		 }else{
			 $("#surveyTypeEmailContent").css("width","580px");
		 }
		}

		jQuery(document).ready(function () 
		{
			togglePreviewLink();
			configureEmailSignatureTextAreaChanges();
			
			passDataToPreviewModal(); // This function handle show preview dialog callback function and pass custom data to the modal dialog
			
			$(".collapseSign").click(function() {
				if($(this).text() === '+')
				{
					$(this).html('-');
					$($(this).attr("data-target")).collapse('show');	
				}
				else
				{
					$(this).html('+');
					$($(this).attr("data-target")).collapse('hide');
				}
			});
			
			$("#customerSupportTooltip").tooltip({
			    placement: "right",
			    html: true,
			    title: '<div>Format</br>Phone # +1 (XXX)-XXX-XXXX</br>Email :: EMAIL@XYZ.ABC</br>Link :: http://XYZ.ABC</br></div>'
			});
			
			var form = document.querySelector('.needs-validation');
			
			form.addEventListener('submit', function(event) {
				console.log('Form submit event');
				
				valiadateBuyerName();
				valiadateBuyerLogo();
				valiadateBuyerAddress();
				validateOrderConfirmationEmail();
				
				if (form.checkValidity() && $('.is-invalid').length===0) {
					console.log('Form validity is true');
					document.forms[0].action='MarketFrontend/buyerSurvey_saveBuyerConfiguration.action';
					document.forms[0].submit();
				}else{
					console.log('Form validity is false');
					event.preventDefault();
					event.stopPropagation();
				}
				
			});
			
		})
		
	</script>
</head>
<body class="tundra acquity">
	<div id="page_margins">
		<div id="page">
			<!-- START HEADER -->
			<div id="header">
				<tiles:insertDefinition name="newco.base.topnav" />
				<tiles:insertDefinition name="newco.base.blue_nav" />
				<tiles:insertDefinition name="newco.base.dark_gray_nav" />
				<div id="pageHeader"></div>
			</div>
			<!-- END HEADER -->

			<!-- start main content -->
	
		<div id="buyerSurveyPage" class="span-24 clearfix">
		
			<br>
			
			<c:if test="${message != null}">
				<div id="message">
					<c:choose>
						<c:when test="${isSaved}">
							<div class="success">${message}</div>	
						</c:when>
						<c:otherwise>
							<div class="error">${message}</div>	
						</c:otherwise>
					</c:choose>
				</div>
			</c:if>		
			<div class="container-fluid">
				<h1 class="main-heading">Email Notification &amp; Configuration Setup</h1>
				<p class="section-msg">
					We have set up configurable email templates that let you stay connected with your customer throughout the lifecycle of their Service event. Our system sends your email notifications in a format that lets you enter you Company <b>Name & Logo</b> so your customer knows it’s an important message, enter a <b>Signature</b> for each template, and configure a <b>Footer</b> with links to your social media pages!
					<br/>
					<br/>
					Required fields must be filled out before changes can be saved, and each template must be set to ‘Active’ for the system to know which message(s) you want to send out.
		    	</p>
				
				<form action="MarketFrontend/buyerSurvey_saveBuyerConfiguration.action" class="myform needs-validation" novalidate id="surveyForm">
				
					<input type="hidden" id="buyerId" name ="buyerSurveyDTO.buyerId" value="${buyerSurveyDTO.buyerId}"/>
					<input type="hidden" id="previewSignature" name ="buyerSurveyDTO.previewSignature"/>
					
					<h2 class="sub-heading"><span>1. Email Header: Buyer Name &amp; Logo </span></h2>
					
					<p class="section-msg">Let your customers know who is reaching out to them! The <b>Name & Logo</b> configured in this section will display in your email as the “sender”. You can also promote your brand by uploading your logo!</p>
					
			        <div class="form-group row">
			            <label for="buyername" class="offset-1 col-3 col-form-label">Buyer Name <font color="red">*</font></label>
			            <div class="col-4">
			            	<input type="text" class="form-control form-control-sm" name="buyerSurveyDTO.buyerName" id="buyername" placeholder="Enter Buyer Name ..." required value="${buyerSurveyDTO.buyerEmailDataMap.get('BUYER_NAME')}">
			            	<div class="invalid-feedback">Buyer name should not be empty</div>
			            </div>
			        </div>
			        
			        <div class="form-group row">
			            <label for="buyerlogo" class="offset-1 col-3 col-form-label">Upload Buyer Logo <font color="red">*</font></label>
			            <div class="col-4">
							<input type="hidden" name="buyerLogoBaseURL" id="buyerLogoBaseURL" value="${buyerSurveyDTO.buyerLogoBaseURL}" />
			            	<input type="hidden" name="buyerSurveyDTO.buyerLogo" id="buyerlogo" value="${buyerSurveyDTO.buyerEmailDataMap.get('BUYER_LOGO')}">
			            	<buyer-logo-uploader logo-path="${buyerSurveyDTO.buyerLogoBaseURL}" current-logo="${buyerSurveyDTO.buyerEmailDataMap.get('BUYER_LOGO')}"></buyer-logo-uploader>
			            	<script type="text/javascript" src="${staticContextPath}/buyer-logo-uploader/dist/buyer-logo-uploader/buyer-logo-uploader.js" crossorigin="anonymous"></script>
			            	<div class="invalid-feedback" id="invalidLogo">Buyer logo should be selected</div>
			            </div>
			        </div>
			        
			        <h2 class="sub-heading"><span>2. Email Notifications: Email Events &amp; Content</span></h2>
			        <input type="hidden" id="eventListSize" value="${buyerSurveyDTO.emailEventList.size()}" />
			        
					
			        <p class="section-msg">
			        	Select the notification(s) you want to send – Set each template you’ll be using to ‘Active’ and enter a <b>Signature</b> for each one!
			        	<br/>
						<br/>
						Some templates let you decide when the message is triggered, and you can see how your message will display by clicking on the ‘Preview’ link.
			        </p>
			       
					<s:select list="buyerSurveyDTO.surveyEventMap" listKey="key" listValue="value"
								theme="simple" cssStyle="display:none"
								value="buyerSurveyDTO.surveyEventMap.value"
								id="surveyEventMap"/>
								
					<s:select list="buyerSurveyDTO.templateMap" listKey="key" listValue="value"
								theme="simple" cssStyle="width: 256px;display:none"
								value="buyerSurveyDTO.templateMap.value"
								id="templateMap"/>
								
					<div class="accordion" id="emailEventAccordion" aria-multiselectable="true">
						<c:forEach items="${buyerSurveyDTO.emailEventList}" var="emailEvent" varStatus="rowCounter">
						<c:set var="count" value="${rowCounter.count}" />
						<c:set var="index" value="${count-1}" />
						
							<div class="card">
								<div class="card-header" id="surveyEmailHeading${count}">
									<div class="row">
										<h5 class="col-6 mb-0">
											<div id="emailCollapseSign${count}" class="collapseSign" data-toggle="collapse" aria-expanded="true" aria-controls="emailView${count}" data-target="#emailView${count}">+</div>
											<div class="headingBtn">${emailEvent.eventDescr}</div>
										</h5>
										<div class="col-6 mb-0 statusRadios">
											<div class="form-check form-check-inline">
											  <input type="hidden" name="emailEventList[${index}].eventId" value="${emailEvent.eventId}" />
											  <input type="hidden" id="eventType${count}" name="emailEventList[${index}].eventType" value="${emailEvent.eventType}"/>
											  <input class="form-check-input" ${emailEvent.surveyFeature} type="radio" name="emailEventList[${index}].active" id="emailRadioOptions${count}_active" value="true" <c:if test='${emailEvent.active}'>checked</c:if> onchange="emailRadioSelected('#emailView${count}', true, '#emailCollapseSign${count}',${count})">
											  <label class="form-check-label" for="emailRadioOptions${count}_active">Active</label>
											</div>
											<div class="form-check form-check-inline">
											  <input class="form-check-input" ${emailEvent.surveyFeature} type="radio" name="emailEventList[${index}].active" id="emailRadioOptions${count}_inactive" value="false" <c:if test='${!emailEvent.active}'>checked</c:if> onchange="emailRadioSelected('#emailView${count}', false, '#emailCollapseSign${count}',${count})">
											  <label class="form-check-label" for="emailRadioOptions${count}_inactive">Inactive</label>
											</div>
											<div class="previewArea">
												<button id="previewLink${count}" disabled type="button" class="btn btn-link btn-sm preview-link" data-toggle="modal" data-target="#previewModal" data-emailEventName="${emailEvent.eventName}" data-emailEventDescr="${emailEvent.eventDescr}" data-emailEventSign="${emailEvent.signature}" 
												data-index="${index}" data-emailEventType="${emailEvent.eventType}">Preview</button>
											</div>
										</div>
									</div>
								</div>
								<div id="emailView${count}" class="collapse" aria-labelledby="surveyEmailHeading${count}" data-accordionindex="${count}">
									<div class="card-body">
										<div class="row">
											<c:if test="${emailEvent.surveyOptionList != null}">
												<div class="offset-1 col-8 survey-type-selection">
													<label>Survey Type <font color="red">*</font></label>
													<c:forEach items="${emailEvent.surveyOptionList}" var="surveyOption">
														<div class="form-check">
														<input type="hidden" name="emailEventList[${index}].eventName" value="${emailEvent.eventName}" />
														<input type="hidden" name="emailEventList[${index}].surveyType" value="${surveyOption.type}" />
														  <input class="form-check-input" ${emailEvent.surveyFeature} type="radio" name="emailEventList[${index}].surveyOptionId" onchange="showEmailContent(${index})"
															id="surveyOption${index}" value="${surveyOption.id}" <c:if test='${emailEvent.surveyOptionId == surveyOption.id}'>checked</c:if>>
															<label class="form-check-label" for="surveyOption${surveyOption.id}">
																${surveyOption.description}
															</label>
														</div>
													</c:forEach>
													<div class="invalid-feedback" id="surveyInvalid${index}">Survey Type should not be empty</div>
												</div>
												<div class="w-100"></div>
											</c:if>
											<c:if test="${(emailEvent.surveyOptionList == null)}">
												<div class="offset-1 col-6 form-group" id="emailTemplateView">
												<label>Email Content (Not Editable): </label>
													<div id="emailContent" class="emailContent">
															${buyerSurveyDTO.templateMap.get(emailEvent.eventName)}
													</div>
												</div>		
											</c:if>
											<c:if test="${(emailEvent.surveyOptionList != null) }">
											<input type="hidden" id="surveyOptionId" value="${emailEvent.surveyOptionId}" />
											<div class="offset-1 col-6 form-group" id="surveyEmailTemplateView">
											<label>Email Content (Not Editable): </label>
													<div id="surveyTypeEmailContent" class="emailContent" style="width:580px">
														${buyerSurveyDTO.templateMap.get(buyerSurveyDTO.surveyEventMap.get(emailEvent.surveyOptionId))}
													</div>
											</div>
											</c:if>
											
											<div class="w-100"></div>
											<div class="offset-1 col-8 form-group">
												<label for="surveyEmailSignTextArea">Signature <font color="red">*</font></label>
												<textarea class="form-control" ${emailEvent.surveyFeature} name="emailEventList[${index}].signature" id="emailSignTextArea${count}" rows="3" maxlength="750" >${emailEvent.signature}</textarea>
												<div class="invalid-feedback">Signature should not be empty</div>
											</div>
											
											<div class="w-100"></div>
											<c:if test="${(emailEvent.statusEvent != null) && (emailEvent.surveyOptionList == null)}">
												<div class="offset-1 col-6 survey-type-selection">
													<label>Service Order status event for triggering email<font color="red">*</font></label>
													<c:forEach items="${emailEvent.statusEvent}" var="sEvent" varStatus="statusCounter">
													<c:set var="statusCount" value="${statusCounter.count}" />
													<c:set var="statusIndex" value="${statusCount-1}" />
														<div class="form-check">
														  <input type="hidden" name="emailEventList[${index}].statusEvent[${statusIndex}].eventId" value="${sEvent.eventId}" />
														  <input type="hidden" name="emailEventList[${index}].statusEvent[${statusIndex}].eventName" value="${sEvent.eventName}" />
														  <input class="form-check-input" type="radio" name="emailEventList[${index}].statusEventOption" id="statusEventOptions${count}" value="${sEvent.eventId}" <c:if test='${sEvent.active}'>checked</c:if>>
														  <label class="form-check-label" for="statusEventOptions${count}">
															${sEvent.eventName}
														  </label>
														  
														</div>
													</c:forEach>
													<div class="invalid-feedback" id="eventInvalid${index}">Service Order status event for triggering email should not be empty</div>
												</div>
											</c:if>	
											<c:if test="${emailEvent.surveyOptionList != null}">
												<div class="offset-1 col-6 survey-type-selection">
													<label id="eventLabel">Service Order status event for triggering email<font color="red">*</font></label>
													<div class="form-check">
													  <input class="form-check-input" ${emailEvent.surveyFeature} type="radio" name="emailEventList[${index}].statusOption" id="completedOrderStatus" value="Completed" <c:if test='${emailEvent.statusOption =="Completed"}'>checked</c:if>>
													  <label class="form-check-label" for="completedOrderStatus">
														Completed
													  </label>
													</div>
													<div class="form-check">
													  <input class="form-check-input" ${emailEvent.surveyFeature} type="radio" name="emailEventList[${index}].statusOption" id="closedOrderStatus" value="Closed" <c:if test='${emailEvent.statusOption =="Closed"}'>checked</c:if>>
													  <label class="form-check-label" for="closedOrderStatus">
														Closed
													  </label>
													</div>
													<div class="invalid-feedback" id="eventInvalid${index}">Service Order status event for triggering email should not be empty</div>
												</div>
											</c:if>	
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
			        
			        <h2 class="sub-heading"><span>3. Email Footer: Links and Information</span></h2>
			        
			        <p class="section-msg">
			        	Let your customers stay connected with you by including links to your social media and customer support pages in the <b>Footer!</b> You can also include Terms & Conditions and a Privacy Policy!
			        </p>
			        
			        <div class="form-group row">
			            <label for="facebook" class="offset-1 col-2 col-form-label">Facebook</label>
			            <div class="col-4">
			            	<input type="text" class="form-control form-control-sm" name="emailFooterDTO.facebookLink" id="facebook" placeholder="Enter Link to Facebook Page ..." value="${buyerSurveyDTO.buyerEmailDataMap.get('FACEBOOK_LINK')}">
			            </div>
			        </div>
			        
			        <div class="form-group row">
			            <label for="twitter" class="offset-1 col-2 col-form-label">Twitter</label>
			            <div class="col-4">
			            	<input type="text" class="form-control form-control-sm" name="emailFooterDTO.twitterLink" id="twitter" placeholder="Enter Link to Twitter Page ..." value="${buyerSurveyDTO.buyerEmailDataMap.get('TWITTER_LINK')}">
			            </div>
			        </div>
			        
			        <div class="form-group row">
			            <label for="instagram" class="offset-1 col-2 col-form-label">Instagram</label>
			            <div class="col-4">
			            	<input type="text" class="form-control form-control-sm" name="emailFooterDTO.instagramLink" id="instagram" placeholder="Enter Link to Instagram Page ..." value="${buyerSurveyDTO.buyerEmailDataMap.get('INSTAGRAM_LINK')}">
			            </div>
			        </div>
			        
			        <div class="form-group row">
			            <label for="pinterest" class="offset-1 col-2 col-form-label">Pinterest</label>
			            <div class="col-4">
			            	<input type="text" class="form-control form-control-sm" name="emailFooterDTO.pinterestLink" id="pinterest" placeholder="Enter Link to Pinterest Page ..." value="${buyerSurveyDTO.buyerEmailDataMap.get('PINTEREST_LINK')}">
			            </div>
			        </div>
			        
			        <div class="form-group row">
			            <label for="google" class="offset-1 col-2 col-form-label">Google+</label>
			            <div class="col-4">
			            	<input type="text" class="form-control form-control-sm" name="emailFooterDTO.googlePlusLink" id="google" placeholder="Enter Link to Google+ Page ..." value="${buyerSurveyDTO.buyerEmailDataMap.get('GOOGLE_LINK')}">
			            </div>
			        </div>
			        
			        <div class="form-group row">
			            <label for="company" class="offset-1 col-2 col-form-label">Company</label>
			            <div class="col-4">
			            	<input type="text" class="form-control form-control-sm" name="emailFooterDTO.companyLink" id="company" placeholder="Enter Link to Company website ..." value="${buyerSurveyDTO.buyerEmailDataMap.get('COMPANY_LINK')}">
			            </div>
			        </div>
			        
			        <div class="form-group row">
			            <label for="support" class="offset-1 col-2 col-form-label">Customer Support</label>
			            <div class="col-4">
			            	<input type="text" class="form-control form-control-sm" name="emailFooterDTO.customerSupportLink" id="support" placeholder="Enter Customer Support Link, Email or Phone Number" value="${buyerSurveyDTO.buyerEmailDataMap.get('SUPPORTPAGE_LINK')}">
			            </div>
			            <i id="customerSupportTooltip" class="fa fa-question-circle help-tooltip" data-toggle="tooltip"></i>
			        </div>
					
					<div class="form-group row">
			            <label for="terms" class="offset-1 col-2 col-form-label">Store Page</label>
			            <div class="col-4">
			            	<input type="text" class="form-control form-control-sm" name="emailFooterDTO.storePageLink" id="storePage" placeholder="Enter Link to Store Page ..." value="${buyerSurveyDTO.buyerEmailDataMap.get('STOREPAGE_LINK')}">
			            </div>
			        </div>
			        
			        <div class="form-group row">
			            <label for="terms" class="offset-1 col-2 col-form-label">Terms &amp; Conditions</label>
			            <div class="col-4">
			            	<input type="text" class="form-control form-control-sm" name="emailFooterDTO.termsLink" id="terms" placeholder="Enter Link to Terms & Conditions ..." value="${buyerSurveyDTO.buyerEmailDataMap.get('TERMSANDCONDITIONS_LINK')}">
			            </div>
			        </div>
			        
			        <div class="form-group row">
			            <label for="privacypolicy" class="offset-1 col-2 col-form-label">Privacy Policy</label>
			            <div class="col-4">
			            	<input type="text" class="form-control form-control-sm" name="emailFooterDTO.privacyPolicyLink" id="privacypolicy" placeholder="Enter Link to Privacy Policy ..." value="${buyerSurveyDTO.buyerEmailDataMap.get('PRIVACYPOLICY_LINK')}">
			            </div>
			        </div>
			        
			        <div class="form-group row">
			            <label for="address" class="offset-1 col-2 col-form-label">Address <font color="red">*</font></label>
			            <div class="col-8">
			            	<input type="text" required class="form-control form-control-sm" name="emailFooterDTO.address" id="address" placeholder="Enter Company Address ..." required value="${buyerSurveyDTO.buyerEmailDataMap.get('ADDRESS')}">
			            	<div class="invalid-feedback">Address should not be empty</div>
			            </div>
			        </div>
			        
			    	<div class="container-fluid">
			    		<input id="addButtonReasonCode" class="button action submit text-center" style="text-transform: none;" type="submit" value="Submit" formmethod="post">
			    	</div>
			    </form>
				
			</div>
			
			<!-- Preview Modal -->
			<div class="modal fade" id="previewModal" tabindex="-1" role="dialog" aria-labelledby="previewModalLabel" aria-hidden="true">
			  <div class="modal-dialog modal-dialog-centered" role="document" id="previewDialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="previewModalLabel">{{Email Event Name}}</h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
			          <span aria-hidden="true">&times;</span>
			        </button> 
			      </div>
			     
				  <div class="modal-body form-group">
					  <div class="emailContent" id="emailTemplate">
						<jsp:include page="/jsp/skuMaintenance/get_json.jsp" flush="true">
							<jsp:param name="PageName" value="Admin.addEditUser"/>
						</jsp:include>
					  </div>
				  </div>
			      <!--<div class="modal-footer">
			        <button type="button" class="btn btn-primary">Save changes</button>
			        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			      </div>-->
			    </div>
			  </div>
			</div>
		</div>
			<!-- end main content -->

			<!-- start common footer -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- end common footer -->
		</div>
	</div>
	
</body>
</html>