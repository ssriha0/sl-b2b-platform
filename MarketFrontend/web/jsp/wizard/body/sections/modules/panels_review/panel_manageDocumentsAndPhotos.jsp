<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		
		<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<!-- NEW MODULE/ WIDGET-->
<a name="documents_top"></a>
<div dojoType="dijit.TitlePane" title="Documents & Photos" id=""
	class="contentWellPane">
   			<iframe width="100%" 
					height="200px"
					marginwidth="0"
					marginheight="0"
					frameborder="0"
					src="/MarketFrontend/soDocumentsAndPhotos_getDocuments.action?tab=review&soId=${SERVICE_ORDER_ID}"
					name="inner_document_grid1"
					id="inner_document_grid1">
   			</iframe>
   	</div>



