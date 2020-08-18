<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:set var="DOC_TYPE_ELECTRONIC_AGREEMENT" scope="page" value="<%=com.servicelive.spn.common.SPNBackendConstants.DOC_TYPE_ELECTRONIC_AGREEMENT%>"/>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
	
<script type="text/javascript">
	jQuery(document).ready(function($) {
		$('#uploadDocData\\.uploadDocDesc').maxlength({ maxCharacters: 255 });
	});
</script>

<script type="text/javascript">
	jQuery(document).ready(function($) {
		$("#uploadDocData.uploadDocType").change(function() {
		
			//document.getElementById("uploadDocData.uploadDocTitle").value = '';
			//document.getElementById("uploadDocData.uploadDocDesc").value = '';
			document.getElementById("photoDoc").value = '';
			
		});	
		
		$('.deleteSPNDoc').click(function() {
			deleteDoc($(this));
		});
		
		
	});
	
	function testUploadFile(fileName)
	{
		var corretFileType = false;
		var corretFileSize = true;
		
		var typeAr = ['JPG', 'PDF', 'DOC', 'GIF', 'TIFF', 'PNG', 'BMP', 'TXT'];
		
		var tmpFileName = fileName.toUpperCase();
		var brokenString = tmpFileName.split(".");
		var numInString = brokenString.length;
		var fileExt = brokenString[numInString - 1];
		
		if (numInString > 1)
		{
			var x;
			for (x in typeAr)
			{
				if (typeAr[x] == fileExt)
				{
					corretFileType = true;
					break;
				}
			}
		}
	
		if (corretFileType == false || corretFileSize == false)
		{
			if (corretFileType == false)
			{
				alert("Invalid file type. Please choose a different file and try again!");
			}
			return false;
		}
		
		return true;
	}
	
	function ajaxFileUpload(closeUpload)
	{
			var fileName = document.getElementById("photoDoc").value;
			if (!testUploadFile(fileName))
			{
				return false;
			}
			var docTitle = document.getElementById("uploadDocData.uploadDocTitle").value;
			var docTypeLabel = document.getElementById("uploadDocData.uploadDocType")[document.getElementById("uploadDocData.uploadDocType").selectedIndex].innerHTML;
			var docTypeId = document.getElementById("uploadDocData.uploadDocType").value;
			var docDescription = document.getElementById("uploadDocData.uploadDocDesc").value;
			
			if (docTitle == '')
			{
				alert("Please enter a Document Title!");
				return false;
			}
			else if (docDescription == '')
			{
				alert("Please enter a Document Description!");
				return false;
			}
			
			$.ajaxFileUpload
			(
				{
					url:'spnCreateNetworkUploadDocument_uploadDocumentAjax.action',
					secureuri:false,
					fileElementId:'photoDoc',
					fileTitle: 'uploadDocData.uploadDocTitle', 
					fileType: 'uploadDocData.uploadDocType', 
					fileDesc: 'uploadDocData.uploadDocDesc',
					dataType: 'json',				
					success: function (data, status)
					{
						if(typeof(data.error) != 'undefined')
						{
							if(data.error != '')
							{
								alert(data.error);
							}else
							{
								var docId = data.msg;
								$('#uploadDocFlag').val('1');
								updateDocumentList(docId, docTitle, docTypeLabel, docTypeId, docDescription, closeUpload);
							}
						}
					}
				}
			)
			
			return false;
	}
	
	function updateDocumentList(docId, docTitle, docTypeLabel, docTypeId, docDescription, closeUpload)
	{
		var oldDocArea = $('#documentsUploadArea').html();
		var newDocArea = "<tr><td><input type='hidden' name='docId' value='" + docId + "' /><a href='#' onclick='return false;' class='deleteDoc'><img src='${staticContextPath}/images/s_icons/cancel.png' /></a></td>" +
			"<td>" + docTitle + "</td>" +
			"<td>" + docTypeLabel + 
			"<input type='hidden' name='uploadDocData.uploadDocIdList' value='" + docId + 
			"' /><input type='hidden' name='uploadDocData.uploadDocTitleList' value='" + docTitle + 
			"' /><input type='hidden' name='uploadDocData.uploadDocTypeList' value='" + docTypeId + 
			"' /><input type='hidden' name='uploadDocData.uploadDocDescList' value='" + docDescription + "' /></td></tr>";
			
		newDocArea = oldDocArea + newDocArea;
		$('#documentsUploadArea').html(newDocArea);
			
			jQuery(document).ready(function($) {
				$('.deleteDoc').click(function() {
					deleteDoc($(this));
				});
			});
						
			document.getElementById("uploadDocData.uploadDocTitle").value = '';
			document.getElementById("uploadDocData.uploadDocDesc").value = '';
			document.getElementById("uploadDocData.uploadDocType").selectedIndex = 0;
			document.getElementById("photoDoc").value = '';
			if (closeUpload)
			{
				$('dl.uploadDoc dd').fadeOut();
				$('dl.uploadDoc span.open').hide();
				$('dl.uploadDoc span.closed').show();
			}
	}
	
	function deleteDoc(thisObj)
	{
		var docId = -1;
		var spnDocId = -1;
		if (thisObj.hasClass('deleteDoc'))
		{
			docId = thisObj.parent('td').children('[name=docId]').val();
		}
		else if (thisObj.hasClass('deleteSPNDoc'))
		{
			spnDocId = thisObj.parent('td').children('[name=spnDocId]').val();
		}
		
		var saveSelected = $('#oldDocSelAr').val();
		var loopSelected = saveSelected.split(",");
		var tmpSaveSelected = new Array();
		var index = 0;
		
		var i = 0;
		for (i = 0; i < loopSelected.length; i++)
		{
			if (loopSelected[i] != docId)
			{
				tmpSaveSelected[index] = loopSelected[i];
				index++;
			}
		}
		
		$('#oldDocSelAr').val(tmpSaveSelected);
		
		queryString = '&uploadDocData.deleteDocId=' + docId + '&uploadDocData.deleteSPNDocId=' + spnDocId;
		
		$.get('spnCreateNetworkUploadDocument_deleteSPNDocumentAjax.action?' + queryString, function(data) {
			data = (data).replace(/^\s*|\s*$/g,'');
			$('#deleteDocFlag').val('1');
			if (data == 'SUCCESS')
			{
				var x = 1;
				var length = thisObj.parent('td').parent('tr').children('td').length;
				thisObj.parent('td').parent('tr').children('td').fadeOut("slow", function() {				
					if (x == length)
					{
						$(this).parent('tr').replaceWith('');
					}
					x++;
				});
			}
			else if (thisObj.parent('td').children('[name=oldDoc]').val() == '1')
			{
				var x = 1;
				var length = thisObj.parent('td').parent('tr').children('td').length;
				thisObj.parent('td').parent('tr').children('td').fadeOut("slow", function() {				
					if (x == length)
					{
						$(this).parent('tr').replaceWith('');
					}
					x++;
				});
			}
			else
			{
				alert('Error deleting document!');
			}
			
		}, "html");
	}
	
	function addOldDoc()
	{
		var oldDocs = document.getElementById('oldDocs').value;
		if (oldDocs == -1)
		{
			return false;
		}
		
		var docTitleKey = oldDocs + '_old_doc_name';
		var docTypeIdKey = oldDocs + '_old_doc_type_id';
		var docTypeDescrKey = oldDocs + '_old_doc_type_descr';
		var docDescrKey = oldDocs + '_old_doc_descr';
		
		var docTitle = document.getElementById(docTitleKey).value;
		var docTypeId = document.getElementById(docTypeIdKey).value;
		var docTypeDescr = document.getElementById(docTypeDescrKey).value;
		var docDescription = document.getElementById(docDescrKey).value;
		
		var list_item = '#list_item_' + oldDocs;
		
		var saveSelected = $('#oldDocSelAr').val();
		var loopSelected = saveSelected.split(",");
		
		var docExists = 0;
		jQuery(document).ready(function($) {
			$('#documentsUploadArea').children('tr').children('td').children('[name=tmpDocId]').each(function() {
				if ($(this).val() == oldDocs)
				{
					docExists = 1;
				}
			});
		});
		
		var i = 0;
		for (i = 0; i < loopSelected.length; i++)
		{
			if (loopSelected[i] == oldDocs)
			{
				docExists = 1;
			}
		}
		
	
		if (docExists == 0)
		{
			$('#uploadDocFlag').val('1');
			var oldDocArea = $('#documentsUploadArea').html();
			$('#documentsUploadArea').html(oldDocArea + "<tr><td><input type='hidden' name='docId' value='" + oldDocs + "' /><input type='hidden' name='oldDoc' value='1' /><a href='#' onclick='return false;' class='deleteDoc'><img src='${staticContextPath}/images/s_icons/cancel.png' /></a></td>" +
				"<td>" + docTitle + "</td>" +
				"<td>" + docTypeDescr + 
				"<input type='hidden' name='uploadDocData.uploadDocIdList' value='" + oldDocs + 
				"' /><input type='hidden' name='uploadDocData.uploadDocTitleList' value='" + docTitle + 
				"' /><input type='hidden' name='uploadDocData.uploadDocTypeList' value='" + docTypeId + 
				"' /><input type='hidden' name='uploadDocData.uploadDocDescList' value='" + docDescription + "' /></td></tr>");
				
			jQuery(document).ready(function($) {
					$('.deleteDoc').click(function() {
						deleteDoc($(this));
					});
				});
				
			var index = 0;
			var tmpSaveSelected = new Array();
			
			if (saveSelected != "")
			{			
				tmpSaveSelected = saveSelected.split(",");
				index = tmpSaveSelected.length;
			}
			
			tmpSaveSelected[index] = oldDocs;			

			$('#oldDocSelAr').val(tmpSaveSelected);
		}
	}
	
</script>

<h3 class="collapse c-documents" title="c-documents">
	<span>Documents</span><span class="plus"></span><span class="min"></span>
</h3>

<div class="toggle c-documents">
	<fieldset>
		<p>
			Items marked with an asterix(
			<span class="req">*</span>) are required.
		</p>
		
		<p>
			<c:if test="${not empty lookupsVO.allDocuments}">
				<label>
					Choose from existing document(s)
				</label>
				<select id="oldDocs" name="selectedDocument">
					<option value="-1">No Selection</option>
					<c:forEach items="${lookupsVO.allDocuments}" var="allDocs">
						<option id="list_item_${allDocs.document.documentId}" value="${allDocs.document.documentId}">${allDocs.document.title}</option>
					</c:forEach>
				</select>
				<c:forEach items="${lookupsVO.allDocuments}" var="allDocs">
					<input type="hidden" id="${allDocs.document.documentId}_old_doc_name" value="${allDocs.document.title}" />
					<input type="hidden" id="${allDocs.document.documentId}_old_doc_type_id" value="${allDocs.docTypeId.id}" />
					<input type="hidden" id="${allDocs.document.documentId}_old_doc_type_descr" value="${allDocs.docTypeId.description}" />
					<input type="hidden" id="${allDocs.document.documentId}_old_doc_descr" value="${allDocs.document.descr}" />
				</c:forEach>
				
				<input type="submit" value="Add" class="default documentbutton" onclick="addOldDoc(); return false;" />
			</c:if>		
		</p>
		
		<div class="information main clearfix">
			<dl class="collapse uploadDoc">
				<dt>
					<span>Upload New Document(s)</span>
					<span class="open"></span>
					<span class="closed"></span>
				</dt>
				<dd class="clearfix" style="clear: left;">
					<div class="clearfix">
						<div class="half">
							<label>
								Document Title
								<span class="req">*</span>
							</label>
							<!--  <input type="text" class="text" /> -->
							<s:textfield id="uploadDocData.uploadDocTitle"
								name="uploadDocData.uploadDocTitle" value="" theme="simple"
								cssClass="text" maxlength="255" />
						</div>
						<div class="half">
							<label>
								Document Type
								<span class="req">*</span>
							</label>
							<s:select id="uploadDocData.uploadDocType"
								name="uploadDocData.uploadDocType"
								list="%{lookupsVO.docTypesList}" listKey="id"
								listValue="description" />
						</div>
					</div>

					<p>
						<label>
							Document Description
							<span class="req">*</span>
						</label>
						<s:textarea id="uploadDocData.uploadDocDesc"
							name="uploadDocData.uploadDocDesc" value="" theme="simple"
							cssClass="description showtip text" />
					</p>

					<div class="uploadform" id="browseDiv">
						<div class="clearfix">
							<div class="half" style="width: 220px;">

								<s:file name="photoDoc" id="photoDoc" />

							</div>
							<div class="half" style="width: 220px; margin-right: 0px;">
								<span class="sm"><strong>Accepted File Types:</strong> <br />.jpg
									| .pdf | .doc | .gif | .tiff | .png | .bmp | .txt</span>
								<span class="sm"><br /> <strong>Max file size:</strong>
									100MB</span>
							</div>
						</div>
						<div>
							<a href="#" class="left" style="margin-right: 10px;"
								onclick="return ajaxFileUpload('');">Upload &amp; Add More</a>
							<button onclick="return ajaxFileUpload(true);">
								Upload &amp; Close
							</button>
						</div>
					</div>					
				</dd>
			</dl>
		</div>
		<div id="document_results"></div>

		<table border="0" cellpadding="5" cellspacing="0" class="hoverEffect"
			style="clear: left;">
			<thead>
				<tr>
					<th width="30px">Delete</th>
					<th>
						Document Title
					</th>
					<th colspan="2">
						Type
					</th>
				</tr>
			</thead>
			<tbody id="documentsUploadArea">
				<c:set var="oldDocSelAr" value="" />
				<c:forEach items="${approvalItems.spnDocumentList}" var="docListItem">
					<tr>
						<td>
							<input type='hidden' name='spnDocId' value='${docListItem.spnDocId}' />
							<input type='hidden' name='tmpDocId' value='${docListItem.documentId}' />
							<a href="#" onclick='return false;' class='deleteSPNDoc'>
							<img src='${staticContextPath}/images/s_icons/cancel.png' />
						</a></td>
						<td>
							${docListItem.title}
						</td>
						<td>
							${docListItem.type}
						</td>
					</tr>
					<c:set var="oldDocSelAr" value="${oldDocSelAr}${docListItem.spnDocId}," />
				</c:forEach>
			</tbody>
		</table>
		<input type="hidden" id="oldDocSelAr" value="" />
	</fieldset>
</div>
<input type="hidden" id="uploadDocFlag" name="uploadDocData.uploadDocFlag" value="0" />
<input type="hidden" id="deleteDocFlag" name="uploadDocData.deleteDocFlag" value="0" />