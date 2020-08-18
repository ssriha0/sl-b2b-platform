<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%-- <h3>subpanel_upload_docs2.jsp</h3> --%>

<s:if test="hasActionErrors()">
	<div style="margin: 10px 0pt;" id="actionError"
		class="errorBox clearfix">
		<s:actionerror />
	</div>
</s:if>
<div style="margin: 10px 0pt; display: none;" id="fileNameSelectionMsg"
	class="errorBox clearfix">
	<fmt:message bundle="${serviceliveCopyBundle}"
		key="document.validation.selection.attach.msg" />
</div>

<div style="margin: 10px 0pt; display: none;" id="documentSelectionMsg"
	class="errorBox clearfix">
	<fmt:message bundle="${serviceliveCopyBundle}"
		key="document.validation.selection.msg" />
</div>



<%-- START OF DOC UPLOAD SECTION --%>
<div id='doc_upload_file_sel_div'>
	<fmt:message bundle="${serviceliveCopyBundle}"
		key="document.file.description.instr" />

	<div style="float: left; width: 280px;">
		<s:file name="document.upload" id="document.upload" />
	</div>

	<div style="float: left; width: 400px;">
		<strong>Accepted File Types :</strong>
		<br />
		${validDisplayExtensions}
		<br />
		<strong>Max. file size:</strong> 5MB
	</div>

	<br style="clear: both;">
	<label for="description">
		<fmt:message bundle="${serviceliveCopyBundle}"
			key="document.file.description.label" />
	</label>
	<br />
	<fieldset style="width: 675px;">
		<s:textarea name="description" id="description"
			cssStyle="width: 657px" cssClass="shadowBox"
			onkeydown="countAreaChars(this.form.description, this.form.doc_and_photos_desc_leftChars, 255, event);"
			onkeyup="countAreaChars(this.form.description, this.form.doc_and_photos_desc_leftChars, 255, event);"></s:textarea>
		<br />

		<fieldset style="float: left;">
			<input type="text" name="doc_and_photos_desc_leftChars" readonly
				size="4" maxlength="4" value="">
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="wizard.scopeofwork.chars.left" />
		</fieldset>
		<input type="button" id="attachDocumentBtn"
			onclick="validateFileName('document.upload', 'documentUpload')"
			value="Attach" style="float: right;" />


		<input type="button" id="removeDocumentBtn"
			onclick="validateDocSelection('removeDocument')" class="btn20Bevel"
			style="background-image: url(${staticContextPath}/images/btn/remove.gif); width: 70px; height: 20px; visibility: hidden"
			src="${staticContextPath}/images/common/spacer.gif" />

	</fieldset>
</div>

<c:if test="${tab == 'review'}">
	<fmt:message bundle="${serviceliveCopyBundle}"
		key="document.review.tab.msg" />
</c:if>
<c:if test="${fileUpload_status}">
	<div id="fileUploadSuccess" class="warningBox"
		style="display: ''; color: #1F8B06; margin: 10px 0; padding: 5px; border: 3px solid #7dc012; background: #eef7df;">
		<fmt:message bundle="${serviceliveCopyBundle}"
			key="document.file.upload.success.bidtab.msg" />
	</div>
</c:if>

<%-- END OF UPLOAD SECTION--%>
