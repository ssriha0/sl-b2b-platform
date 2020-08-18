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
			
			<th class="col1  textleft">
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="document.file.name" />
			</th>
			
			<th class="col2  textleft">
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="document.file.category" />
			</th>
			<th class="col3">
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="document.file.uploadedBy" />
			</th>
			<th class="col4 textleft">
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="document.file.uploadedTimeStamp" />
			</th>
		</tr>

<input type="hidden" id="documentSelection" name="documentSelection" value="">
		<c:forEach var="doc" items="${documents}" varStatus="dtoIndex">
			<tr>
				
				<td class="col1  textleft">
					<c:choose><c:when test="${(!viewProviderDocPermission && doc.role == 'Provider')||tab == 'review'}">
					${doc.name}
					<c:if test="!${fn:containsIgnoreCase(doc.category, '8')}">
					<b>(${doc.size} Kb)</b></c:if>
					
					</c:when>
					<c:otherwise>
							<a href="#" style="text-decoration:underline" onclick="assignValueFordocumentSelection('${doc.documentId}');assignCategory('${doc.category}','${doc.uploadFileName}');validateVideo()"> ${doc.name}
							</a><c:if test="!${fn:containsIgnoreCase(doc.category, '8')}">
							<b>(${doc.size} Kb)</b></c:if>
								<c:if test="${doc.docInCurrentVisit==true}">
									<c:choose>
										<c:when test="${doc.role == 'Buyer' && roleId!=1}">
											<img id="removeDocumentBtn"
											onclick="assignValueFordocumentSelection('${doc.documentId}');validateDocSelection('removeDocument')" class="btn20Bevel"
											style="visibility: visible;cursor: pointer;"
											src="${staticContextPath}/images/response_icon_red.gif" title="Delete" >
										</c:when>
										<c:otherwise>
											<c:if test="${doc.role == 'Provider' && roleId==1}">
											<img id="removeDocumentBtn"
												onclick="assignValueFordocumentSelection('${doc.documentId}');validateDocSelection('removeDocument')" class="btn20Bevel"
												style="visibility: visible;cursor: pointer;"
												src="${staticContextPath}/images/response_icon_red.gif" title="Delete" >
											</c:if>										
										</c:otherwise>
									</c:choose>
								</c:if>
					</c:otherwise></c:choose>
				<p><c:if test="${not empty doc.desc}"><b>File description </b>
				${doc.desc}
				</c:if>
				</p>
				</td>
				
				<td class="col2 textleft">
				<c:choose><c:when test="${doc.category==9||doc.category==10||doc.category==11}">
					 	<fmt:message bundle="${serviceliveCopyBundle}"
					key="document.file.${doc.category}" />
				</c:when>
				<c:otherwise>
					${doc.documentTitle}
				</c:otherwise></c:choose>
				
				</td>
				
				<td class="col3">
				<strong>${doc.uploadedBy}</strong>
					 	<p>(${doc.role})</p>
				</td>
				<td class="col4 textleft">
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
	
	</c:otherwise></c:choose>

