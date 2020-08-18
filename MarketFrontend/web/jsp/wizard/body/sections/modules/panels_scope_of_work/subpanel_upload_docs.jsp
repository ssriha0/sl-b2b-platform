<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%-- <h3>subpanel_upload_docs.jsp</h3> --%>

<style type="text/css">
.uploadTable1{
	width: 666px;
	cellpadding: 0px;
	cellspacing: 0px; 
	height: 25px;
  	border-style: solid;
  	border-width: 1px 1px 0px 1px;  
  	border-color: #9F9F9F;	
}
.uploadTable2{
	width: 650px;
	cellpadding: 0px;
	cellspacing: 0px; 
  	border-style: solid;
  	border-width: 0px 0px 1px 1px;
  	overflow: auto;
  	border-color: #9F9F9F;
}
.uploadTr{
	color: #FFFFFF;
	font-weight: 700;	
	background-color: #4CBCDF;
}
.col1{
	padding-top: 5px;
}
.col2{
	padding-top: 10px;
	border-bottom: 1px solid;
	border-color: #9F9F9F;
}
</style>

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

<fmt:message bundle="${serviceliveCopyBundle}"
	key="document.panel.description" />
	
<div id='doc_upload_file_sel_div'>
	<p>
	<c:if test="${roleId == 2 || roleId == 3 || roleId == 5}">
	<c:if test="${(null != refDocuments) && fn:length(refDocuments)>0}">
		<div style="color: #00A0D2;font-size: 10.5px;">
			<span id='from_computer' style="cursor: pointer;">
				<fmt:message bundle="${serviceliveCopyBundle}"	key="document.upload.from.computer" />
			</span> |  
  			<span id='from_pre_files' style="cursor: pointer;">
  				<fmt:message bundle="${serviceliveCopyBundle}"	key="document.select.previous.files" />
  			</span>
  		</div>
  		<br><br>
  	</c:if>
  	</c:if>  	
  <div id='doc_upload_from_comp'>	
		<label>
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="document.select.file" />
		</label>
		<br />
	<table style="width: 500px;">
		<tr>
			<td>
				<s:file name="document.upload" id="document.upload" />
			</td>
			<td style="align: left;">
				<b>Accepted File Types :</b>${validDisplayExtensions}
				<br />
				<b>Max. file size:</b> 5MB
			</td>
		</tr>
	</table>
	</p>

	<fmt:message bundle="${serviceliveCopyBundle}"
		key="document.file.description.instr" />
	<p>

		<label>
			<fmt:message bundle="${serviceliveCopyBundle}"
				key="document.file.description.label" />
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
  </div>
  <div id='doc_upload_previous_files'style="display:none;">
  	<table class="uploadTable1" cellpadding="0px" cellspacing="0px">
  		<tr class="uploadTr">
  			<td width="97px" align="center" class="col1">
  				<fmt:message bundle="${serviceliveCopyBundle}"	key="document.select" />
  			</td>  			
  			<td width="250px" class="col1">
  				<fmt:message bundle="${serviceliveCopyBundle}"	key="document.file.name" />
  			</td>
  			<td width="10.5px"></td>
  			<td width="195px" class="col1">
  				<fmt:message bundle="${serviceliveCopyBundle}"	key="document.buyeradmin.file.title" />
  			</td>
  			<td width="97.5px" align="center" class="col1">
  				<fmt:message bundle="${serviceliveCopyBundle}"	key="document.file.size" />
  			</td>
  			<td width="16px"></td>
  		</tr>  
  	</table>
 	<div style="overflow-x:hidden; overflow-y:scroll; height: 100px;width:666px;">
  		<table class="uploadTable2" cellpadding="0px" cellspacing="0px"> 
  			<c:forEach var="doc" items="${refDocuments}" varStatus="status">
  				<tr>
  					<td width="97px" align="center" class="col2">
  						<input type="checkbox" id="doc${status.count}" name="documentIds[${status.count}]" value="${doc.documentId}"/>
  					</td>
  					
  					<td width="250px" class="col2">
  						<strong>${doc.name}</strong>
						<p>${doc.desc}</p>
  					</td>
  					<td width="10.5px" class="col2">
  						&nbsp;
  					</td>
  					<td width="195px" class="col2">
  						${doc.title}
  					</td>
  					<td width="97.5px" align="center" class="col2">
  						${doc.size} Kb
  					</td>
  					<c:set var="count" value="${status.count}"></c:set>		
  				</tr>
  			</c:forEach>
  		</table>
	</div>
	<br><br><br>
	<input type="button" id="attachDocumentBttn"
		onclick="validateFiles('${count}','uploadDocs')"
		class="btn20Bevel"
		style="background-image: url(${staticContextPath}/images/btn/attach.gif); width: 60px; height: 20px;"
		src="${staticContextPath}/images/common/spacer.gif" />
  </div>
  <br>

	<input type="button" id="attachDocumentBtn"
		onclick="validateFileName('document.upload', 'documentUpload')"
		class="btn20Bevel"
		style="background-image: url(${staticContextPath}/images/btn/attach.gif); width: 60px; height: 20px;"
		src="${staticContextPath}/images/common/spacer.gif" />
</div>

<input type="hidden" id="docSrc" name="docSrc" value="${docSrc}"/>

<c:if test="${fileUpload_status}">
	<div id="fileUploadSuccess" class="warningBox"
		style="display: ''; color: #1F8B06; margin: 10px 0; padding: 5px; border: 3px solid #7dc012; background: #eef7df;">
		<fmt:message bundle="${serviceliveCopyBundle}"
			key="document.file.upload.success.msg" />
	</div>
</c:if>

