<%@ page import="org.owasp.esapi.ESAPI;" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

	<% String pageNameVar=(String)request.getParameter("simplePageName");
   		String securePageNameVar1 = ESAPI.encoder().canonicalize(pageNameVar);
   		String securePageNameVar = ESAPI.encoder().encodeForHTML(securePageNameVar1);
   		%>
	<c:set var="pageName" scope="page" value="<%=securePageNameVar%>"/>
	<iframe width="98%" height="380px" marginwidth="0" marginheight="0"
		frameborder="0"
		src="${contextPath}/csoDocumentsAndPhotos_getDocumentsAndPhotos.action?simplePageName=${pageName}"
		name="inner_document_grid" id="inner_document_grid" >
	</iframe>
	
	<!-- src="${contextPath}/csoDocumentsAndPhotos_getDocumentsAndPhotos.action?editable=${param.editable != null && param.editable == false ? false : true}"  -->