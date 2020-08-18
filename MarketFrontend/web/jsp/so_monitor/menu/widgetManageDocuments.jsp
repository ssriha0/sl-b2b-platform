<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="theId" scope="request"
	value="<%=request.getAttribute("tab")%>" />
<c:set var="role" value="${roleType}" />

<s:form action="soDocumentsAndPhotos" id="soDocumentsAndPhotos" theme="simple" name="docPhotoForm"enctype="multipart/form-data" method="POST">

<div dojoType="dijit.TitlePane" title="Manage Documents" id="widget_manage_documents_${theId}"
	style="padding-top: 1px; color: #FFFFFF; width: 249px;" open="false">
	
	<span class="dijitInfoNodeInner">
		<a href=""></a>
	</span>
	
	<div class="inputArea" id="widget_doc_div_${theId}" style="width:94%;"></div>
	<div id="widget_doc_btns_div_${theId}"></div>
<br style="clear:both;"/>
</div>
<input type="hidden" name="manageDocsFromSOM" id="manageDocsFromSOM" value="fromDocumentSOMWidget" />	
</s:form> 