<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/modalVideo.css" />
<%-- <h3>subpanel_view_download_remove_docs2.jsp</h3> --%>

<c:choose><c:when test="${empty documents}">
	No photos or documents were included
</c:when>
<c:otherwise>

	<c:choose><c:when
		test="${ so_status != null && so_status != draft_status && so_status != routed_status && so_status != expired_status}">
		<fmt:message bundle="${serviceliveCopyBundle}"
			key="document.file.remove.msg" />
	</c:when>
	<c:otherwise>
		<fmt:message bundle="${serviceliveCopyBundle}"
			key="document.file.view.msg" />
	</c:otherwise></c:choose>


	<table id="docsAndPhotosBidTabView">
		<c:forEach items="${documents}" var="doc">
			<tr>
				<td>
					<c:choose><c:when test="${fn:containsIgnoreCase(doc.category, '8')}">
					<img src="${staticContextPath}/images/icons/video.gif"
								id="${doc.documentId}"
								onclick="showVideo('${doc.uploadFileName}');"
								style="cursor: pointer;" />
					</c:when>
					<c:otherwise>
					
					
					<c:choose>
						<c:when test="${fn:containsIgnoreCase(doc.format, 'image')}">
							<img src="${staticContextPath}/images/icons/docIcon.gif"
								id="${doc.documentId}"
								onclick="makeSelection(this.id, 'viewDocument');"
								style="cursor: pointer;" />
							<%--<img src="/MarketFrontend/soDocumentsAndPhotos_viewImageThumbnail.action?imageDocId=${doc.documentId}" >--%>
						</c:when>
						<c:otherwise>
							<img src="${staticContextPath}/images/icons/docIcon.gif"
								id="${doc.documentId}"
								onclick="makeSelection(this.id, 'viewDocument');"
								style="cursor: pointer;" />
						</c:otherwise>
					
					</c:choose>
					</c:otherwise></c:choose>
					<input type="radio" name="documentSelection" id="documentSelection"
						value="${doc.documentId}" style="display: none;"
						onclick="documentSelected(${doc.docInCurrentVisit})" />
				</td>
				<td>
					<strong> <c:choose>
							<c:when test="${fn:containsIgnoreCase(doc.format, 'image')}">Image: </c:when>
							<c:otherwise>Document: </c:otherwise>
						</c:choose> </strong>
						<c:choose><c:when test="${fn:containsIgnoreCase(doc.category, '8')}">
						<a href="#" id="${doc.documentId}"
						onclick="showVideo('${doc.uploadFileName}');">${doc.name}</a>
						</c:when>
						<c:otherwise>
						<a href="#" id="${doc.documentId}"
						onclick="makeSelection(this.id, 'viewDocument');">${doc.name}</a>
						</c:otherwise></c:choose>
				</td>
				<td>
					${doc.desc}
				</td>
				<td>
					  <c:choose><c:when test="${fn:containsIgnoreCase(doc.category, '8')}">
								N/A
					 </c:when>
					 <c:otherwise>
								${doc.size} Kb
					 </c:otherwise></c:choose>
					<br />

					<c:if
						test="${so_status == accepted_status && doc.docInCurrentVisit}">
						<a href="#" class="removeLink"
							onclick="makeSelection('${doc.documentId}','removeDocument');">x
							Remove</a>
					</c:if>


				</td>
			</tr>
		</c:forEach>
	</table>


</c:otherwise></c:choose>

<style type="text/css">
	#legacyFields {
		display: none;
	}
</style>
