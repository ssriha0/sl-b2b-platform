<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="hasadminoffice" scope="request" value="false" />

<tags:security actionName="addServiceProAction" >
<c:set var="hasadminoffice" value="true" />
</tags:security>

<tags:security actionName="allTabView" >
<c:set var="hasadminoffice" value="true" />
</tags:security>

<!--
<c:if test="${hasadminoffice eq 'true'}"> 
	<div class="leftTileItem" id="dbTile_adminOfc">
	  <div class="titleContainer">
	    <div class="titleBar"> <img src="${staticContextPath}/images/dashboard/hdrAdminOfc.gif" width="96" height="23" align="left" class="titleImage" /> <%-- Commented out for 12/15/07 release <a href=""><img src="../images/dashboard/lnkMore.gif" width="41" height="23" border="0" align="right" /></a> --%> </div>
	  </div>
	  <div class="contentContainer">
	    <div class="content">
	      <p>Access everything you need to manage your staff - from skill assessments to scheduling.</p>
	    </div>
	  </div>
	  <div class="shadowBottom"></div>
	</div>
</c:if>
-->
