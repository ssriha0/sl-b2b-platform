<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%-- <h3>subpanel_view_download_remove_docs.jsp</h3> --%>
<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/modalVideo.css" />
<c:choose><c:when test="${empty documents}">
	<p style="font: normal 11px Verdana, Arial, Helvetica, sans-serif; margin-top: 5px; text-indent: 5px;">
		No photos or documents were included.
	</p>
	<script type="text/javascript">
	if(${tab == 'review'}){
	top.document.getElementById('inner_document_grid1').height = '30px';
	}
	else{
	top.document.getElementById('inner_document_grid').height='300px';
	}
	</script>
	

</c:when>
<c:otherwise>



	<c:choose><c:when
		test="${so_status != null && so_status != draft_status && so_status != routed_status && so_status != expired_status}">
		<fmt:message bundle="${serviceliveCopyBundle}"
			key="document.file.remove.msg" />
	</c:when>
	<c:otherwise>
		<fmt:message bundle="${serviceliveCopyBundle}"
			key="document.file.view.msg" />
	</c:otherwise></c:choose>


	<c:if test="${tab == 'review'}">
		<fmt:message bundle="${serviceliveCopyBundle}"
			key="document.review.tab.msg" />
	</c:if>
	<c:choose>
		<c:when test="${tab != 'review'}">
			<div id="docsDiv" class="globalTableWrap"
				style="overflow-x:hidden;overflow-y:scroll; height: 190px; border-bottom: 1px solid #CCCCCC;">
		</c:when>
		<c:otherwise>
			<div class="globalTableWrap">
		</c:otherwise>
	</c:choose>
	<input type="hidden" id="categoryId" value="" />
	<input type="hidden" id="videoFileId" value="" />
	
	
	<table class="globalTableLook" cellpadding="0" cellspacing="0">
		<tr>
			<c:if test="${tab != 'review'}">
				<th class="col1 odd">
					<fmt:message bundle="${serviceliveCopyBundle}"
						key="document.select" />
				</th>
			</c:if>
			<th class="col2 even textleft">
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="document.file.name" />
			</th>
			<th class="col3 odd">
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="document.file.uploadedBy" />
			</th>
			<th class="col4 even textleft">
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="document.file.uploadedTimeStamp" />
			</th>
		</tr>

		<c:forEach var="doc" items="${documents}" varStatus="dtoIndex">
			<tr>
				<c:if test="${tab != 'review'}">
					<td class="column1">
					<c:choose><c:when test="${!viewProviderDocPermission && doc.role == 'Provider'}">
					<b>*</b>
					</c:when>
					<c:otherwise>
						<input type="radio" name="documentSelection"
							id="documentSelection" value="${doc.documentId}"
							onclick="assignCategory('${doc.category}','${doc.uploadFileName}');documentSelected(${doc.docInCurrentVisit});" />
					</c:otherwise></c:choose>
					</td>
				</c:if>
				<td class="col2 even textleft">
					<strong>${doc.name}
					<c:if test="!${fn:containsIgnoreCase(doc.category, '8')}">
					(${doc.size} Kb)</c:if></strong>
					<p>
						${doc.desc}
					</p>
				</td>
				<td class="col3 odd">
				<strong>${doc.uploadedBy}</strong>
					 	<p>(${doc.role})</p>
				</td>
				<td class="col4 even textleft">
					<c:if test="${doc.createdDate != null}">
						<fmt:formatDate value="${doc.createdDate}" type="both"
							dateStyle="medium" timeStyle="medium" />
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
	</div>
	<div class="clear"></div>
	<div class="clearfix">
		<p>

			<c:if test="${tab != 'review'}">
				<input type="button" onclick="validateVideo()"
					class="btn20Bevel"
					style="background-image: url(${staticContextPath}/images/btn/view.gif); width: 70px; height: 20px;"
					src="${staticContextPath}/images/common/spacer.gif" />



				<input type="button" id="removeDocumentBtn"
					onclick="validateDocSelection('removeDocument')" class="btn20Bevel"
					style="background-image: url(${staticContextPath}/images/btn/remove.gif); width: 70px; height: 20px; visibility: hidden"
					src="${staticContextPath}/images/common/spacer.gif" />

			</c:if>
		</p>
	</div>
	<c:if test="${viewDocCommentInd}">
	<div id="viewDocComment">
	<fmt:message bundle="${serviceliveCopyBundle}" key="document.file.permission.msg" />
	</div>
	</c:if>
	</c:otherwise></c:choose>
