<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="com.newco.marketplace.security.ActivityMapper"%>
<%@page import="com.newco.marketplace.constants.Constants"%>
<%@page import="com.newco.marketplace.auth.SecurityContext"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="error" scope="session" value="<%=session.getAttribute("ErrorMessage")%>" />
<html>
	<head>
		<title>ServiceLive [SP Profile]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit.Dialog");
			dojo.require("dijit._Calendar");
			dojo.require("dojo.date.locale");
			dojo.require("dojo.parser");
			dojo.require("dijit.form.Slider");
			dojo.require("dijit.layout.LinkPane");
			//dojo.require("newco.servicelive.SOMRealTimeManager");
			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
			
			var contextPath = '${pageContext.request.contextPath}';
		</script>
		<style type="text/css">
@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css"
	;

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css"
	;
</style>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/slider.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/service_order_wizard.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />



<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
<script language="JavaScript" src="${staticContextPath}/javascript/js_registration/tooltip.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/js_registration/formfields.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/js_registration/utils.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/nav.js"></script>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/calendar.css?random=20051112" media="screen"></link>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/js_registration/calendar.js?random=20060118"></script>


		<script language="JavaScript">
	
	function editUser(resourceId){

		document.editServicePro.resourceId.value=resourceId;
		document.editServicePro.action="<s:url action="serviceProAllTab" includeParams="none" method="doLoad"/>"
		document.editServicePro.submit();		
	}
	
	/*
	function editServiceProvider(){
		var selObj = document.getElementsByName("serviceProvidersList");
	 	var val = selObj[0].options[selObj[0].selectedIndex].value;
	 	document.forms[0].resourceId.value=val;
		document.forms[0].action="<s:url action="serviceProAllTab" includeParams="none" method="load"/>"
		document.forms[0].submit();	
	 	
		}
	*/	
	function openUserSummary(resourceId, url){
		document.editServicePro.resourceId.value = resourceId;
		document.editServicePro.action=url;
		document.editServicePro.submit();
	}
	
</script>


	</head>
	<body class="tundra">
	    
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="ProviderRegistration.addProvider"/>
		</jsp:include>	
	
		<div id="page_margins">
			<div id="page">
				<!-- BEGIN HEADER -->
				<div id="headerSPReg">
				  <div id="header">
     
					<tiles:insertDefinition name="newco.base.topnav"/>  
					<tiles:insertDefinition name="newco.base.blue_nav"/>				                        
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>				                        
						
				</div>
				<!-- END HEADER -->				
				<!-- START TAB PANE -->
				
					
				<div class="colLeft711">									
					<div class="content">
						<c:if test="${error != null}">
							<div class="error" >
								<c:out value="${error}" />
							</div>
						</c:if>
						<h3>
							Build a Service Profile, and Start Accepting Work!
						</h3>
						<p>
							ServiceLive simplifies project management by letting your service
							providers accept and manage their own orders. That means less
							administrative work for you and your staff and more time in the
							field for your service providers.
						</p>
						<p class="paddingBtm">
							To get started, create a profile for your company and at least
							one provider who will be making service calls. This could be you,
							or it could be one of your team members. After your first
							profiles are created, you'll be able to access your service
							dashboard where you can add as many service providers as you'd
							like. The more providers you register, the more service orders
							your company will be able to receive. Begin by telling us whether
							you're adding a service provider, or if you're creating a service
							profile for yourself.
						</p>

						<p class="paddingBtm">
							<strong>Providers on the Marketplace</strong>
							<br>
							There are no service providers registered for your company.
						</p>
						<p>

							<a
								href="<s:url action="serviceProAllTab" method="doNew" includeParams="none" />">
								<img
									src="${staticContextPath}/images/images_registration/common/spacer.gif"
									width="119" height="20"
									style="background-image: url(${staticContextPath}/images/images_registration/btn/addServicePro.gif);"
									class="btn20Bevel" /> </a>
									<c:if test="${numberOfServiceProviders == 0}">&nbsp;OR&nbsp;
									<img src="${staticContextPath}/images/images_registration/common/spacer.gif" width="119" height="20" style="background-image:url(${staticContextPath}/images/images_registration/btn/iAmServicePro.gif);" class="btn20Bevel" onclick="javascrip:editUser('${SecurityContext.vendBuyerResId }');"/>
									
									</c:if>

							<c:if test="${numberOfServiceProviders > 0}">
							
								<!-- 
								<font size="3" color="#157DEC">&nbsp;OR </font>
								<br />
								<s:select name="serviceProvidersList"
									value="%{defaultServiceProvider}"
									headerValue="%{defaultServiceProvider}"
									headerKey="%{defaultServiceProviderResourceId}"
									label="Choose a team member to make him/her a Servie Pro"
									list="serviceProvidersList" />
								<a href="javascript:editServiceProvider()"><FONT size="3"
									color="#157DEC">&nbsp;Go</font>
								</a>
								-->					
		 <table  border=1 style="width: 666px; cellpadding="0" cellspacing="0">
          <tr align="center" style="color: #fff; font-weight: bold; background: #4CBCDF; height: 23px;">
            <td class="column6">Name</td>
            <td class="column2">General</td>
            <td class="column3">Prefrences<br>
              Status</td>
            <td class="column4">Skills<br>
              Status</td>
            <td class="column5"> L&amp;C </td>
            <td class="column1"> BackGround </td>
            <td class="column7">Terms</td>
          </tr>
       	
		    <s:iterator id="teamMemberObj" value="teamProfileDTO.teamMemberList" status="status">
			 <tr align="center" style="color: #4CBCDF; font-weight: bold; background: #fff; height: 23px;">
	            <td class="column6" >
		            <s:if test="%{teamProfileDTO.primaryInd}">		            	
		            	<a href="javascript:openUserSummary('${teamMemberObj.resourceId}','<s:url action='serviceProAllTab?tabView=tab1'/>')"><s:property value="firstName"/>&nbsp;<s:property value="lastName"/>
		            	(User Id# <s:property value="resourceId"/>)</a>
		            </s:if>
		            <s:else>
		            		<s:if test ="%{teamProfileDTO.adminResourceId == resourceId or teamProfileDTO.loggedResourceId == teamMemberObj.resourceId}">
		          			  <s:property value="firstName"/>&nbsp; <s:property value="lastName"/>
		          			  (User Id# <s:property value="resourceId"/>)
		          			  </s:if>
		          			  <s:else>
		          			  	<a href="javascript:openUserSummary('${teamMemberObj.resourceId}','<s:url action='serviceProAllTab?tabView=tab1'/>')"><s:property value="firstName"/>&nbsp;<s:property value="lastName"/>
		          			  	(User Id# <s:property value="resourceId"/>)</a>
		          			  </s:else>
		            </s:else>
	            </td>
	            <td class="column2">
	            	<%
	            		String generalImage = "";
	            	 %>
		           	<s:if test="%{general==1}">
		           		<%
		           			generalImage = "/images/icons/completeIconOn.gif";
		           		 %>
					</s:if>
					<s:else>
						<%
							generalImage = "/images/icons/conditional_exclamation.gif";
						 %>
					</s:else>
						
			        <s:if test="%{teamProfileDTO.primaryInd}">
						<a href="javascript:openUserSummary('${teamMemberObj.resourceId}','<s:url action='serviceProAllTab?tabView=tab1'/>')">
						<img src="${staticContextPath}<%=generalImage%>" width="10" height="10" alt=""></a>
					</s:if>
					<s:else>
            				<s:if test ="%{teamProfileDTO.adminResourceId == resourceId or teamProfileDTO.loggedResourceId == teamMemberObj.resourceId}">&nbsp;
            					<img src="${staticContextPath}<%=generalImage%>" width="10" height="10" alt="">
          					</s:if>
          					<s:else>
          			  			<a href="javascript:openUserSummary('${teamMemberObj.resourceId}','<s:url action='serviceProAllTab?tabView=tab1'/>')">
          			  			<img src="${staticContextPath}<%=generalImage%>" width="10" height="10" alt=""></a>
          					</s:else>
					</s:else>
			    </td>
			     <td class="column3">
			     	<%
			     		String marketImage = "";
			     	 %>
			     	<s:if test="%{market==1}">
		           		<%
		           			marketImage = "/images/icons/completeIconOn.gif";
		           		 %>
					</s:if>
					<s:else>
						<%
							marketImage = "/images/icons/conditional_exclamation.gif";
						 %>
					</s:else>
					
			         <s:if test="%{teamProfileDTO.primaryInd}">
						<a href="javascript:openUserSummary('${teamMemberObj.resourceId}','<s:url action='serviceProAllTab?tabView=tab2'/>')">
						<img src="${staticContextPath}<%=marketImage%>" width="10" height="10" alt=""></a>
					</s:if>
					<s:else>
         				<s:if test ="%{teamProfileDTO.adminResourceId == resourceId or teamProfileDTO.loggedResourceId == teamMemberObj.resourceId}">&nbsp;
         					<img src="${staticContextPath}<%=marketImage%>" width="10" height="10" alt="">
       					</s:if>
       					<s:else>
       			  			<a href="javascript:openUserSummary('${teamMemberObj.resourceId}','<s:url action='serviceProAllTab?tabView=tab2'/>')">
       			  			<img src="${staticContextPath}<%=marketImage%>" width="10" height="10" alt=""></a>
       					</s:else>
					</s:else>
			    </td>
			     
			     <td class="column4">
			     	<%
			     		String skillsImage = ""; 
			     	 %>
			     	 <s:if test="%{skills==1}">
		           		<%
		           			skillsImage = "/images/icons/completeIconOn.gif";
		           		 %>
					</s:if>
					<s:else>
						<%
							skillsImage = "/images/icons/conditional_exclamation.gif";
						 %>
					</s:else>
			     	
					<s:if test="%{teamProfileDTO.primaryInd}">
						<a href="javascript:openUserSummary('${teamMemberObj.resourceId}','<s:url action='serviceProAllTab?tabView=tab3'/>')">
						<img src="${staticContextPath}<%=skillsImage%>" width="10" height="10" alt=""></a>
					</s:if>
					<s:else>
         				<s:if test ="%{teamProfileDTO.adminResourceId == resourceId or teamProfileDTO.loggedResourceId == teamMemberObj.resourceId}">&nbsp;
         					<img src="${staticContextPath}<%=skillsImage%>" width="10" height="10" alt="">
       					</s:if>
       					<s:else>
       			  			<a href="javascript:openUserSummary('${teamMemberObj.resourceId}','<s:url action='serviceProAllTab?tabView=tab3'/>')">
       			  			<img src="${staticContextPath}<%=skillsImage%>" width="10" height="10" alt=""></a>
       					</s:else>
					</s:else>
			    </td>
			     <td class="column5">
			     	<%
			     		String lcImage = "";
			     	 %>
				     <s:if test="%{lc==1}">
		           		<%
		           			lcImage = "/images/icons/completeIconOn.gif";
		           		 %>
					</s:if>
					<s:else>
						<%
							lcImage = "/images/icons/conditional_exclamation.gif";
						 %>
					</s:else>
				    
				    <s:if test="%{teamProfileDTO.primaryInd}">
						<a href="javascript:openUserSummary('${teamMemberObj.resourceId}','<s:url action='serviceProAllTab?tabView=tab4'/>')">
						<img src="${staticContextPath}<%=lcImage%>" width="10" height="10" alt=""></a>
					</s:if>
					<s:else>
	     				<s:if test ="%{teamProfileDTO.adminResourceId == resourceId or teamProfileDTO.loggedResourceId == teamMemberObj.resourceId}">&nbsp;
	     					<img src="${staticContextPath}<%=lcImage%>" width="10" height="10" alt="">
	   					</s:if>
	   					<s:else>
	   			  			<a href="javascript:openUserSummary('${teamMemberObj.resourceId}','<s:url action='serviceProAllTab?tabView=tab4'/>')">
	   			  			<img src="${staticContextPath}<%=lcImage%>" width="10" height="10" alt=""></a>
	   					</s:else>
					</s:else>
			    </td>
			     <td class="column1">
			     <%
			     	String backgroundImage = "";
			      %>
			     <s:if test="%{background==1}">
	           		<%
	           			backgroundImage = "/images/icons/completeIconOn.gif";
	           		 %>
				</s:if>
				<s:else>
					<%
						backgroundImage = "/images/icons/conditional_exclamation.gif";
					 %>
				</s:else>
			     
				 <s:if test="%{teamProfileDTO.primaryInd}">
					<a href="javascript:openUserSummary('${teamMemberObj.resourceId}','<s:url action='serviceProAllTab?tabView=tab5'/>')">
					<img src="${staticContextPath}<%=backgroundImage%>" width="10" height="10" alt=""></a>
				</s:if>
				<s:else>
    				<s:if test ="%{teamProfileDTO.adminResourceId == resourceId or teamProfileDTO.loggedResourceId == teamMemberObj.resourceId}">&nbsp;
    					<img src="${staticContextPath}<%=backgroundImage%>" width="10" height="10" alt="">
  					</s:if>
  					<s:else>
  			  			<a href="javascript:openUserSummary('${teamMemberObj.resourceId}','<s:url action='serviceProAllTab?tabView=tab5'/>')">
  			  			<img src="${staticContextPath}<%=backgroundImage%>" width="10" height="10" alt=""></a>
  					</s:else>
				</s:else>
			          	
			    </td>
			    <td class="column7">
			    <%
			    	String termsImage = "";
			     %>
			     <s:if test="%{terms==1}">
	           		<%
	           			termsImage = "/images/icons/completeIconOn.gif";
	           		 %>
				</s:if>
				<s:else>
					<%
						termsImage = "/images/icons/conditional_exclamation.gif";
					 %>
				</s:else>
			    
				 <s:if test="%{teamProfileDTO.primaryInd}">
					<a href="javascript:openUserSummary('${teamMemberObj.resourceId}','<s:url action='serviceProAllTab?tabView=tab6'/>')">
					<img src="${staticContextPath}<%=termsImage%>" width="10" height="10" alt=""></a>
				</s:if>
				<s:else>
    				<s:if test ="%{teamProfileDTO.adminResourceId == resourceId or teamProfileDTO.loggedResourceId == teamMemberObj.resourceId}">&nbsp;
    				<img src="${staticContextPath}<%=termsImage%>" width="10" height="10" alt="">
  					</s:if>
  					<s:else>
  			  			<a href="javascript:openUserSummary('${teamMemberObj.resourceId}','<s:url action='serviceProAllTab?tabView=tab6'/>')">
  			  			<img src="${staticContextPath}<%=termsImage%>" width="10" height="10" alt=""></a>
  					</s:else>
				</s:else>
			          		
			    </td>
             </tr>
			</s:iterator>
        </table>
		</c:if>
		<s:form name="editServicePro" theme="simple">
			<s:hidden name="resourceId"></s:hidden>
		</s:form>
		</p>
		</div>
		</div>
		<!-- END TAB PANE -->
		<div class="colRight255 clearfix">
			<c:if test="${SecurityContext.slAdminInd}">		
				<jsp:include page="/jsp/so_monitor/menu/widgetAdminMemberInfo.jsp" />
			</c:if>			
			<jsp:include flush="true" page="modules/reg_status.jsp"></jsp:include>
		</div>
		<div class="clear">
		</div>
			</div>
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
		</div>
		</div>
	</body>
</html>
