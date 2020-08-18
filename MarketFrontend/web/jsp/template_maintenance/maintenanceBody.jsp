<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />	
	
<div id="templateMaintenance" class="clearfix">
<!-- SL-20728 Enable Rich text editing -->
 <script src="${staticContextPath}/javascript/tinymce/tinymce.min.js"></script>
 <style type="text/css">
	.mce-btn button{padding-left: 1px;}
	.mce-listbox button{padding-right: 9px;}
</style>

  <script>
  tinymce.init({ 
	 	 selector:'textarea',menubar: false,statusbar: false,
	 	 plugins: ["autolink autoresize link paste"],
		 toolbar1: '| undo redo | bold italic underline | formatselect | fontsizeselect |',
		 toolbar2: '| alignleft aligncenter alignright alignjustify | outdent indent | bullist numlist | link unlink | removeformat |',	
		 block_formats:'Paragraph=p;Heading 1=h1;Heading 2=h2;Heading 3=h3;Heading 4=h4;Heading 5=h5;Heading 6=h6',
		 autoresize_max_height: 110,
  		 setup: function (editor) {
		        editor.on('change', function () {
		            editor.save();
		        });
		  }
	  });
  </script>
	<s:form name="templateForm" id="templateForm" theme="simple" action="templateMaintenance_">
	
		<fieldset>
			<legend>Template Maintenance</legend>

	<div id="message">
	<c:choose><c:when test="${isTemplateError == 'false'}">
		<div class="success">${templateMsg}</div>	
	</c:when>
	<c:otherwise>
		<div>${templateMsg}</div>	
	</c:otherwise></c:choose>
	<a name="errorPosition"></a>
	</div>
		<div id="validationError"  style="display:none" width="100%">

	</div>

			
			
			<p>Items marked with an asterisk (<span class="req">*</span>) are required.</p>
			<table>
			<tr>
			<td >
			<div class="left">
			<table id="choose" border="0">
				<tr>
					<td colspan="2">

								<input type="radio" name="mode" id="addSoTemplate"
								<c:if test="${editTemplate == false}"> checked="checked" </c:if> 
								onclick="javascript:fnSetAddMode(this)"  />								
								<label for="addTemplate">Add A New Template</label>					
					
					</td>
				</tr>
				<tr>
					<td valign="top">
					
							<input type="radio" name="mode" id="editSoTemplate" 
							<c:if test="${editTemplate == true}"> checked="checked" </c:if>
							onclick="javascript:fnSetEditMode(this)"/> 
						<label for="editTemplate">Edit An Existing Template:</label>	
									
						<s:hidden name="editTemplate" id="editTemplate"></s:hidden>
						<s:select name="selectedBuyerSOTemplate" id="selectedBuyerSOTemplate"
							headerKey="-1" headerValue="-- Select One --" list="buyerSOTemplateList"
							size="1" theme="simple" value="selectedBuyerSOTemplate" 
							onchange="javascript:fnGetTemplateDetails(this)"/>					
				</td>
				</tr>

			</table>	
			
			
			
			
			<table id="main">
				<tr>
					<c:if test="${editTemplate == true}">
						<td>
							<label for="templateId">Template Id  &nbsp; # ${selectedBuyerSOTemplate} &nbsp; 	
							<a href="" style="vertical-align: middle;" id="buyerTemplateIdText" onclick="return false;">
							<img src="${staticContextPath}/images/icons/help_16.gif" width="16"
							height="16" border="0" style="vertical-align: absmiddle;" /> 
							</label>
						</a>							
						</td>
						</c:if>
				</tr>
				<tr>
					<td><p id="templateNameError" >
						<label for="templateName">Template Name <span class="req">*</span></label>
						<s:textfield id="templateName" name="templateName" cssClass="text" 
								theme="simple" value="%{templateName}" /></p>
					</td>
					<td><p id="mainCatError">
						<label for="mainCategory">Main Category <span class="req">*</span></label>					
						<s:select name="mainServiceCategoryId" id="mainServiceCategoryId"
							headerKey="-1" headerValue="-- Select One --" list="mainServiceCategory"
							listKey="nodeId" listValue="nodeName" 
							size="1" theme="simple" value="mainServiceCategoryId" /></p>
					</td>
				</tr>	
				
				
				<tr>
					<td><p id="partsSuppBy">
						<label for="partsSuppliedBy">Parts Supplied By <span class="req">*</span></label>
						<s:select name="partsSuppliedBy" id="partsSuppliedBy"
							headerKey="-1" headerValue="-- Select One --" list="partsSuppliedByList"
							size="1" theme="simple" value="partsSuppliedBy" /></p>
					</td>
					<td>
						<label for="alternateBuyer"">Alternate Buyer Contact</label>					 
						<s:select name="selectedAltContact" id="selectedAltContact"
							list="altBuyerContactList"
							listKey="contactId" listValue="displayName" 
							size="1" theme="simple" value="selectedAltContact" />																		
					</td>
				</tr>
				<tr>
					<td <c:if test="${isSearsBuyer != true}">colspan="2"</c:if>>
						<label for="spn">SPN</label>
						<s:select name="selectedSpn" id="selectedSpn"
							headerKey="-1" headerValue="-- Select One --" list="spnList"
							size="1" theme="simple" value="selectedSpn" />		
							
						<a href="" style="vertical-align: middle;" id="whatIsApprovedProviderCount" onclick="return false;">
							<img src="${staticContextPath}/images/icons/help_16.gif" width="16"
							height="16" border="0" style="vertical-align: absmiddle;" /> 
						</a>
					</td>
					<c:if test="${isSearsBuyer == true}"> 
					<td>
						<label for="spn">Sears SPN</label>
						<s:select name="searsSelectedSpn" id="searsSelectedSpn"
							headerKey="-1" headerValue="-- Select One --" list="searsSpnList"
							size="1" theme="simple" value="searsSelectedSpn" />												
					</td>
					</c:if>
				</tr>
				<tr>
					<td colspan="2"><p id="spnPercentageMatchError">
						<label for="spnPercentageMatch">SPN Percentage Match <span class="req">*</span></label>					
						<s:textfield id="spnPercentageMatch" name="spnPercentageMatch" cssClass="text" 
								theme="simple" value="%{spnPercentageMatch}" 
								onblur="javascript:fnCheckSpnPercentage(this)" /></p>				
					</td>
				
				</tr>

				<tr>
					<td colspan="2"><p id="buyerLogoError">
						<label for="buyerLogo">Buyer Logo<span class="req">*</span></label>
						<s:select name="selectedBuyerLogo" id="selectedBuyerLogo"
							headerKey="-1" headerValue="-- Select One --" list="buyerLogo"
							cssStyle="width: 208px;" size="1" theme="simple" value="selectedBuyerLogo" /></p>
					</td>
				</tr>						
			
				<tr>
					<td colspan="2">		
						<label>Should tech call customer to confirm appointment?</label>							
						<input type="radio" class="antiRadioOffsets"
							name="confirmServiceTime" value="1" 
							<c:if test="${confirmServiceTime == 1}"> checked ="checked" </c:if> />
						<fmt:message bundle="${serviceliveCopyBundle}" key="yes" /> &nbsp;&nbsp;&nbsp;
						<input type="radio" class="antiRadioOffsets"
							name="confirmServiceTime" value="0" 
							<c:if test="${confirmServiceTime == 0}"> checked ="checked" </c:if> />
						<fmt:message bundle="${serviceliveCopyBundle}" key="no" />	
					</td>
				</tr>
				<tr>
					<td colspan="2">

						<label>Should reschedule requests by end customer be accepted automatically?</label>							
						<input type="radio" class="antiRadioOffsets"
							name="autoAccept" id ="autoAcceptYes" value="1" 
							<c:if test="${(autoAccept == 1) || (autoAccept == null && editTemplate == false)}"> checked ="checked" </c:if> />
						<fmt:message bundle="${serviceliveCopyBundle}" key="yes" /> &nbsp;&nbsp;&nbsp;
						<input type="radio" class="antiRadioOffsets"
							name="autoAccept" id ="autoAcceptNo" value="0" 
							<c:if test="${(autoAccept == 0) || (autoAccept == null && editTemplate == true)}">checked ="checked"</c:if> />
						<fmt:message bundle="${serviceliveCopyBundle}" key="no" />
					</td>
				</tr>

			  <tr>
					<td colspan="1" width="5px"><p id="autoAcceptDaysError">
						<label for="autoAcceptDays" id="autoAcceptDaysLabel">Reschedule Limit(Days)</label>
						<c:if test="${autoAcceptDays!=null}" >
						<s:textfield size="3" id="autoAcceptDays" name="autoAcceptDays"  
								theme="simple" value= "%{autoAcceptDays}" /> </c:if>
						<c:if test="${autoAcceptDays==null}" >	
						<s:textfield size="3" id="autoAcceptDays" name="autoAcceptDays"  
								theme="simple" value= "14" /> </c:if>	</p>		
					</td>
					<td colspan="1"><p id="autoAcceptTimesError">
						<label for="autoAcceptTimes" id="autoAcceptTimesLabel">Allowed # Of Automatic Reschedules</label>
						<c:if test="${autoAcceptTimes!=null}" >
						<s:textfield size="3" id="autoAcceptTimes" name="autoAcceptTimes"  
								theme="simple" value="%{autoAcceptTimes}" /> </c:if>
						<c:if test="${autoAcceptTimes==null}" >	
						<s:textfield size="3" id="autoAcceptTimes" name="autoAcceptTimes"  
								theme="simple" value= "1" /> </c:if></p>
					</td>
			</tr>
			
			</table>
					
			</div>
			</td>
			<td >
			<div class="right" style="margin-top:-10px;">
				<table id="secondary">
									<tr>
					<td colspan="2">
						<label for="overview">Overview</label><br />
						<s:textarea id="overview" name="overview" cssClass="text"
								theme="simple" value="%{overview}"></s:textarea>
					</td>
				</tr>

				<tr>
					<td colspan="2">
						<label for="buyerTerms">Buyer Terms &amp; Conditions</label><br />
						<s:textarea id="buyerTandC" name="buyerTandC" cssClass="text" 
								theme="simple" value="%{buyerTandC}"></s:textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<label for="specialInstructions">Special Instructions</label><br />
						<s:textarea id="specialInstructions" name="specialInstructions" cssClass="text"
								theme="simple" value="%{specialInstructions}"></s:textarea>
					</td>
				</tr>
				
				</table>
		


			</div>
			</td>
			</tr>
			</table>
<hr />

			<table id="doclist" border="0">
				<tr>
					<td>
						<label for="docList">Available Documents</label>
						<s:select name="buyerDocument" id="buyerDocument" list="buyerDocumentList" cssStyle="width: 400px;" size="5" theme="simple" multiple="true"/>
					</td>
					<td style="vertical-align: middle;text-align: center;">
						<input type="button" id="orginalDocList" class="add" value="&raquo;" style="padding: 0 4px;" 
							onclick="newco.jsutils.handleOptionSelect('buyerDocument', 'attachedList');"/>
						<br />
						<input type="button" id="selectedDocList" class="remove" value="&laquo;" style="padding: 0 4px;"
							onclick="newco.jsutils.handleOptionSelect('attachedList', 'buyerDocument');" />
					</td>
					<td>
						<label for="attachedList">Attached Documents</label>
						<s:select name="selectedBuyerDocument" id="attachedList"
							list="selectedDocuments"
							cssStyle="height: 80px; width: 400px;" 
							size="5" theme="simple" multiple="true"/>
						
					</td>
				</tr>
			</table>

			
		</fieldset>
			<table border="0" cellspacing="0" cellpadding="0" style="width: 100%; margin: 10px 0">
			<tr>
				<td>
					<a class="cancel" onclick="javascript:fnReturnToDashboard()">Cancel</a>	
				</td>
				<td style="text-align: right;">
					<c:choose><c:when test="${editTemplate == false}"> 
						<input type="submit" value="Save Template" class="action submit button"
							style="float: right;" onclick="tinyMCE.triggerSave();javascript:fnSaveTemplate(true); return false;"/>		
					</c:when>
					<c:otherwise>
	
						<input type="submit" value="Update Template" style="float: right;" class="action submit button" onclick="javascript:fnSaveTemplate(false); return false;"/>		
					</c:otherwise></c:choose>		
					
				</td>
			</tr>
		</table>		
		
	</s:form>
</div>
<script type="text/javascript">
jQuery(document).ready(function($){
var isAutoAcceptedYesChecked=true;
var isAutoAcceptedYesChecked=$("#autoAcceptYes").attr("checked");
if(!isAutoAcceptedYesChecked)
{
	$("#autoAcceptDaysLabel").hide();
	$("#autoAcceptDays").hide();
	$("#autoAcceptTimesLabel").hide();
	$("#autoAcceptTimes").hide();
}


$("#whatIsApprovedProviderCount").mouseover(function(e){
      var x = e.pageX;
      var y = e.pageY;
      $("#explainer-approvedProviderCount").css("top",y-20);
      $("#explainer-approvedProviderCount").css("left",x+20);
      $("#explainer-approvedProviderCount").show();
     
 	}); 
 	$("#whatIsApprovedProviderCount").mouseout(function(e){
 		$("#explainer-approvedProviderCount").hide();
 	});

$("#buyerTemplateIdText").mouseover(function(e){
      var x = e.pageX;
      var y = e.pageY;
      $("#explainer-buyerTemplateIdText").css("top",y-40);
      $("#explainer-buyerTemplateIdText").css("left",x+20);
      $("#explainer-buyerTemplateIdText").show();
     
 	}); 
 	$("#buyerTemplateIdText").mouseout(function(e){
 		$("#explainer-buyerTemplateIdText").hide();
 	});
$("#autoAcceptYes").click(function(e){
	$("#autoAcceptDaysLabel").show();
	$("#autoAcceptDays").show();
	$("#autoAcceptTimesLabel").show();
	$("#autoAcceptTimes").show();
	$("#autoAcceptDays").val(14);
	$("#autoAcceptTimes").val(1);

});
$("#autoAcceptNo").click(function(e){
	$("#autoAcceptDaysLabel").hide();
	$("#autoAcceptDays").hide();
	$("#autoAcceptTimesLabel").hide();
	$("#autoAcceptTimes").hide();
});
$("#autoAcceptDays").keypress(function (e)
{
  //if the letter is not digit then display error and don't type anything
  if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57))
  {
    //display error message
    //$("#errmsg").html("Digits Only").show().fadeOut("slow");
    return false;
  }
});
$("#autoAcceptTimes").keypress(function (e)
{
  //if the letter is not digit then display error and don't type anything
  if( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57))
  {
    //display error message
    //$("#errmsg").html("Digits Only").show().fadeOut("slow");
    return false;
  }
});

});
</script>
<div id="explainer-approvedProviderCount" style="display:none; position:absolute; width:205px; height:125px; padding:10px 20px; line-height: 130%; font-size:11px;
 background: url(${staticContextPath}/images/common/explainerBg.gif) no-repeat;">
		<h5>Helping Information</h5>
		<p>The number indicates the total count of Approved providers who are eligible for Service Order posting.</p>
</div>

<div id="explainer-buyerTemplateIdText" style="display:none; position:absolute; width:205px; height:58px; padding:10px 20px; line-height: 130%; font-size:11px;
 background: url(${staticContextPath}/images/common/explainerBg.gif) no-repeat;">
		<h5>Helping Information</h5>
		<p>Template Id is a system generated value and is displayed for reference only.</p>
</div>