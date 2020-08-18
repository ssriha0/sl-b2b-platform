<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<s:iterator value="scopeOfWorkTabList">
	<!-- NEW MODULE/ WIDGET-->
	<a name="documents_top"></a>
	<div dojoType="dijit.TitlePane" title="Documents & Photos" id="" class="contentWellPane">
		<iframe width="100%" height="520px" marginwidth="0" marginheight="0"
			frameborder="0"
			src="/MarketFrontend/soDocumentsAndPhotos_getDocuments.action"
			name="inner_document_grid" id="inner_document_grid" scrolling="no">
		</iframe>
	</div>
</s:iterator>





