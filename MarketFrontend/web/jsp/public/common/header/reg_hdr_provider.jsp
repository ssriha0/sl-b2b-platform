<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<div id="mainNav">
  <ul>
    <li><a href="" id="whatIsSL"></a></li>
    <li class="divider"></li>
    <li><a href="" id="serviceProviders"></a></li>
    <li class="divider"></li>
    <!-- <li><a href="" id="homeowners"></a></li>  -->
    <li class="divider"></li>
    <li><a href="" id="businesses"></a></li>
    <li class="divider"></li>
    <li><a href="" id="exploreMktplace"></a></li>
    <li class="divider"></li>
  </ul>
</div>
<div id="pageHeader">
  <div> <img src="${staticContextPath}/images/sp_firm_registration/hdr_register.gif" alt="Register" /> </div>
</div>
