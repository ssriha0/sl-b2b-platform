<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery-ui-personalized-1.5.2.packed.js"></script>
<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/acquity.css">		
<%-- <h3>subpanel_upload_docs.jsp</h3> --%>
	<script type="text/javascript" charset="utf-8">
					jQuery.noConflict();
				jQuery(document).ready( function ($) {
					$("#browseButton").html('<input type="file" name="document.upload" size="42px" style="width:375px;" id="document.upload" />');
					var permitWarning;
					if(parent.document.getElementById('permitWarningStatusInd')){
					permitWarning=parent.document.getElementById('permitWarningStatusInd').value;
					}
					if(parent.document.getElementById('permitInd')){
						var permitIndVal = parent.document.getElementById('permitInd').value;
						if(permitIndVal == 1 && ${doc_Source == doc_SourceVal && so_status == active_status})
							document.getElementById('autocloseWarning').style.display="block";
						else
							document.getElementById('autocloseWarning').style.display = "none";
					}
					else if ((${permitWarningStatus == 1}||permitWarning == 1)&& ${doc_Source == doc_SourceVal && so_status == active_status}){
							document.getElementById('autocloseWarning').style.display="block";
					}
						
					
					
			});
			function savePermitWarningStatus(){
				if(document.getElementById('autocloseWarning').style.display == "none"){
							if(document.getElementById('permitWarningStatus')){
							document.getElementById('permitWarningStatus').value='0';
							}
				}
				else{
							document.getElementById('permitWarningStatus').value='1';		
				}
			}
				</script>
				
<input type="hidden" id="permitWarningStatus"  name="permitWarningStatus" value="" />
<s:if test="hasActionErrors()">
	<div style="margin: 10px 0pt;" id="actionError"
		class="errorBox clearfix">
		 &nbsp;	&nbsp; &nbsp;<s:actionerror />
	</div>
</s:if>

<div style="margin: 10px 0pt; display: none;background: none repeat scroll 0 0 #FFEACC;color: #FE0000;height: 15px;padding:5px;" id="fileNameSelectionMsg"
	class="errorBox clearfix">
	<fmt:message bundle="${serviceliveCopyBundle}"
		key="document.validation.selection.attach.msg" />
</div>

<div style="margin: 10px 0pt; display: none;background: none repeat scroll 0 0 #FFEACC;color: #FE0000;height: 15px;padding:5px;" id="docCategorySelectionMsg"
	class="errorBox clearfix">
	<fmt:message bundle="${serviceliveCopyBundle}"
		key="document.file.type.required" />
</div>


<div style="margin: 10px 0pt; display: none;" id="documentSelectionMsg"
	class="errorBox clearfix">
	<fmt:message bundle="${serviceliveCopyBundle}"
		key="document.validation.selection.msg" />
</div>
<div>
<fmt:message bundle="${serviceliveCopyBundle}"
	key="required.field" />
</div>
<c:choose><c:when test="${doc_Source == doc_SourceVal}">
<fmt:message bundle="${serviceliveCopyBundle}"
	key="document.panel.description.sears.buyer" />
</c:when>

<c:otherwise>
<fmt:message bundle="${serviceliveCopyBundle}"
	key="document.panel.description.sears.buyer.summary.tab" />
</c:otherwise></c:choose>
<p style="font-weight: bold"><fmt:message bundle="${serviceliveCopyBundle}"
		key="document.file.signature.reminder.sears.buyer" /></p>
<span id="autocloseWarning"  class="warningBox" style="display:none;background: #ffc url(${staticContextPath}/images/icons/conditional_exclamation.gif) no-repeat 3px 6px; border: solid 1px #fc6; 
padding:5px 5px 5px 20px; font-weight:bold; position:relative; width:95%;color: black">This order has permits and requires a separate proof of permits document for completion.</span>

<c:if test="${(null != documentTypes) && fn:length(documentTypes)>0}">

<p><fmt:message bundle="${serviceliveCopyBundle}"
	key="document.files.required.label.sears.buyer" /></p>
<div>
<select id="docTitle" name="docTitle" onchange="setTitle();"> 
				<option value="">-Select one-</option>
				<c:forEach items="${documentTypes}" var="docType">
				<c:if test="${docType.documentTitle != 'Parts Invoice'}">
					<option value="${docType.buyerCompDocId }">${docType.documentTitle}</option>
					</c:if>
				</c:forEach>
			</select>
</div>
</c:if>

<div id='doc_upload_file_sel_div'>
	<p>
		<label>
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="document.select.file.sears.buyer" />
		</label>
		<br />
	<table style="width: 500px;">
		<tr>
			<td>
				<div id="browseButton"></div>
   				
   
			</td>
			<td style="align: left;">
				<div style="font-weight:bold">Accepted File Formats :</div><fmt:message bundle="${serviceliveCopyBundle}"
				key="document.file.accepted.file.formats.sears.buyer" />
				<br />
				<b>Max. file size: 5MB</b>
			</td>
		</tr>
	</table>
	</p>

	
	<p>


		<label>
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="document.file.description.label.sears.buyer" />
		</label>
		<br />

		<s:textarea name="description" id="description"
			cssStyle="width: 657px" cssClass="shadowBox"
			onkeydown="countAreaChars(this.form.description, this.form.doc_and_photos_desc_leftChars, 255, event);"
			onkeyup="countAreaChars(this.form.description, this.form.doc_and_photos_desc_leftChars, 255, event);"></s:textarea>
		<input type="text" name="doc_and_photos_desc_leftChars" readonly
			size="4" maxlength="4" value="255">
		<fmt:message bundle="${serviceliveCopyBundle}"
			key="wizard.scopeofwork.chars.left" />

	</p>

<input type="button" id="attachDocumentBtn"
		onclick="validateFileName('document.upload', 'documentUpload')"
		class="btn20Bevel"
		style="background-image: url(${staticContextPath}/images/btn/attach.gif); width: 60px; height: 20px;"
		src="${staticContextPath}/images/common/spacer.gif" />
</div>



<c:if test="${fileUpload_status}">
	<div id="fileUploadSuccess" class="warningBox"
		style="display: ''; color: #1F8B06; margin: 10px 0; padding: 5px; border: 3px solid #7dc012; background: #eef7df;">
		<fmt:message bundle="${serviceliveCopyBundle}"
			key="document.file.upload.success.msg" />
	</div>
</c:if>

