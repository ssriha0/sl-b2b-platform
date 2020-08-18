<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="wallet"/>
	</jsp:include>



<div id="account">
	<h2><a style="color: rgb(38, 80, 98); text-decoration: none;" href="#">ServiceLive Wallet</a></h2>
	<dl>
		<dt>Current Balance: </dt>
		<dd>${CurrentBalance}</dd>
	</dl>
	<dl>
		<dt>Available: </dt>
		<dd>${AvailableBalance}</dd>
	</dl>
</div>

