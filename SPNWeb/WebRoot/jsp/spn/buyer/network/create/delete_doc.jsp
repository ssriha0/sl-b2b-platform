<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:if test="${uploadDocData.uploadSuccess == true}">
SUCCESS
</c:if>
<c:if test="${uploadDocData.uploadSuccess == false}">
FAIL
</c:if>