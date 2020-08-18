<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<c:if test="${not empty secondaryList2}">
<s:select name="searchPortalServiceProviderVO.skillCategoryId" id="ddsubcategory" value="%{#request['searchPortalServiceProviderVO'].skillCategoryId}" headerKey="-1" headerValue="Select One" cssClass="select" size="1" theme="simple" list="secondaryList2" listKey="nodeId" listValue="nodeName" />
</c:if>
<c:if test="${empty secondaryList2 }">
<select name="searchPortalServiceProviderVO.skillCategoryId" id="ddsubcategory" disabled="disabled">
<option value="-1">Select One</option>
</select>
</c:if>