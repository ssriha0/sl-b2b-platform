<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="outer">
  	<div id="header">        
	<c:import url="/jsp/public/common/defaultLogo.jsp" /><%-- tagline --%>
     <div id="topNav">
      <div id="tollfreenumber" style="maring-top:10px; padding-top:10px">1-888-549-0640</div>
      <div style="maring-top:10px; padding-top:5px">
      <a href="http://community.servicelive.com/" target="_blank">Community</a> | <a href="${contextPath}/jsp/public/support/support_main.jsp" target="_blank">Support</a> | <a href="contactUsAction.action" target="_blank">Contact Us</a> </div> 
      <div style="maring-top:10px; padding-top:10px" ><a class="btn17" style="float: none; display: inline-block; width:47px; height: 17px; background-image: url('${staticContextPath}/images/buttons/login_header.gif');" href="${loginActionPath}/loginAction.action"><img src="${staticContextPath}/images/spacer.gif" width="47" height="17" alt="Login" style="margin-bottom: -5px;"></a></div>
     </div>

    <div id="mainNav" style="top: 20px;">
      <ul>
        <li class="parentNavItem"> <a id="whatIsSL"></a>
          <ul class="level2 whatIsSlnew-subnav">
            <li class="parentNavItem"> <a href="${contextPath}/jsp/public/what_is_servicelive/body/what_is_sl_landing.jsp" class="subnav subnavArrow" id="aboutUsLink">About Us</a> </li>          
            <li class="parentNavItem"> <a href="${contextPath}/jsp/public/what_is_servicelive/body/aboutServiceProviders.jsp" class="subnav">About Our Service Providers</a> </li>
            <li class="parentNavItem"> <a href="${contextPath}/jsp/public/support/support_faq.jsp" class="subnav">Frequently Asked Questions</a> </li>            
          </ul>
        </li>
        <!-- <li> <a href="${contextPath}/joinNowBuyerSimpleAction.action" id="homeowners"></a> </li>  -->
        <li> <a href="${contextPath}/joinNowBuyerAction.action" id="businesses"></a> </li>
        <li> <a href="${contextPath}/joinNowAction.action" id="serviceProviders"></a> </li>
      </ul>
    </div>
    <div id="auxNav"></div>
   </div>
</div>