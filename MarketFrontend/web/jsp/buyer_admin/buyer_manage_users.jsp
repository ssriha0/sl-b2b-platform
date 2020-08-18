<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <title>ServiceLive - Administrator Office - Manage Users</title>
        <tiles:insertDefinition name="blueprint.base.meta"/>
        <script language="JavaScript"src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js" type="text/javascript"></script>           
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/buttons.css" />
        <script type="text/javascript" src="${staticContextPath}/javascript/pwdReset.js"></script>
        <script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
        <style type="text/css">
          .ie7 .bannerDiv{margin-left:-1150px;}
        </style>
        <script type="text/javascript">
       
            function myHandler(id,newValue){
                console.debug("onChange for id = " + id + ", value: " + newValue);
            }

            var selectedName, rid;           
            var passwordMenuButtonPosition;
              function showResetModalLocal(obj, id, name) {               
                selectedName = name;
                rid = id;               
                showResetModal(obj, id);
            }               
           
              function submitResetPassword(rid){
               document.forms['resetPassword'+rid].selectedUser.value = selectedName;
               document.forms['resetPassword'+rid].action="<s:url action="buyerAdminAddEdit_displayEditPage!resetPassword.action"/>";
               document.forms['resetPassword'+rid].submit();
            }
            var timeoutID;
           
            function encodeUsername(username){
                username = username.replace(/%/g, "-prcntg-");
                username = encodeURIComponent(username);
                window.location.replace("buyerAdminAddEdit_displayEditPage!displayEditPage.action?username="+username);
            }
                   
        </script>
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/modalPassword.css" />
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/tablePassword.css" />
       
    </head>
   

<body id="manage-users">
<div id="wrap" class="container">
    <tiles:insertDefinition name="blueprint.base.header"/>
    <tiles:insertDefinition name="blueprint.base.navigation"/>
    <div id="content" class="span-24 clearfix">       
           
            <div class="content padd">
               
            <h2 id="page-title">Manage Users</h2>
               
               
                <div class="colLeft711">
                    <div class="content">
                        <p class="paddingBtm">
                            <fmt:message bundle="${serviceliveCopyBundle}" key="buyer.manage_users.instruction"/>
                            <br>
                        </p>

                        <s:if test="hasActionErrors() or hasActionMessages()">
                            <jsp:include page="/jsp/sl_login/common/errorMessage.jsp" flush="true" />
                        </s:if>   
                        <s:else>                   
                            <p class="success">
                                <fmt:message bundle="${serviceliveCopyBundle}" key="manage_users.users" >
                                    <fmt:param value="${fn:length(usersList)}"/>
                                </fmt:message>
                            </p>
                        </s:else>
                        

                        <div class="tablePasswordwrap" style="height: auto; overflow: visible;">
                            <table cellspacing="0" cellpadding="0" class="passwordReset" id="sort3">                           
                            <tr>
                                <th class="col1 first odd action">
                                    <fmt:message bundle="${serviceliveCopyBundle}" key="buyer.manage_users.label.admin" />
                                </th>                               
                                <th class="col2 even">
                                    <fmt:message bundle="${serviceliveCopyBundle}" key="buyer.manage_users.label.name" />
                                </th>
                                <th class="col3 even">
                                    <fmt:message bundle="${serviceliveCopyBundle}" key="buyer.manage_users.label.title" />
                                </th>
                                <th class="col4 even">
                                    <fmt:message bundle="${serviceliveCopyBundle}" key="buyer.manage_users.label.maxspendlimit" />
                                </th>
                            </tr>                           
                            <tbody>
                            <c:forEach items="${usersList}" var="user">
                                <tr>
                                    <td class="col1 first even action textleft">
                                   
                                        <div id="tablePasswordMenu">
                                            <s:form action="buyerAdminAddEdit_displayEditPage">
                                              <input type="hidden" name="username" value="${user.username}" />   
                                                                                                                                       
                                              <div id="link1"><a id="#action${user.resourceId}"
                                              href="#action${user.resourceId}" onmouseover="pwdMenuMouseOver('l1${user.resourceId}')" onmouseout="pwdMenuMouseOut('l1${user.resourceId}')"> Take Action &gt;&gt;</a></div>                                             
                                              <div class="tablePasswordMenu" onmouseover="pwdMenuMouseOver('l1${user.resourceId}')" onmouseout="pwdMenuMouseOut('l1${user.resourceId}')">
                                                <ul id='l1${user.resourceId}'>
                                                  <li><a id="promoLink" style="cursor: pointer;" onclick="encodeUsername('${user.username}')">Edit Profile</a></li>
                                                     <li> <a id='${user.resourceId}' href='javascript:void(0)' onclick="javascript:showResetModalLocal(this, '${user.resourceId}', '${user.username}');">Reset password</a> </li>
                                                                 </ul>
                                              </div>                                                                                      
                                            </s:form>
                                        </div>
                                       
                                        <form name="resetPassword${user.resourceId}" action="resetPassword">
                                        <s:hidden name="selectedUser" value="23" />                                          
                                            <jsp:include page="/jsp/admin/passwordModal.jsp" flush="true">
                                                 <jsp:param name="modalId" value="${user.resourceId}"/>
                                                 <jsp:param name="name" value="${user.name}"/>
                                            </jsp:include>
                                       </form>
            
                                    </td>
                                    <td class="col2 even textleft">
                                        <a style="cursor: pointer;" onclick="encodeUsername('${user.username}')">${user.name}</a><br><br>                                       
                                    </td>
                                    <td class="col3 even textleft">                                       
                                        <c:choose>
                                            <c:when test="${not empty user.title}">${user.title}</c:when>
                                        </c:choose>                                       
                                    </td>
                                    <td class="col4 even textleft">
                                        <c:choose>
                                          <c:when test="${user.maxSpendLimit==0.0}">
                                            <fmt:message bundle="${serviceliveCopyBundle}" key="buyer.manage_users.value.nolimit" />
                                          </c:when>
                                          <c:otherwise>
                                            $<fmt:formatNumber value="${user.maxSpendLimit}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
                                          </c:otherwise>
                                        </c:choose>
                                    </td>   
                                </tr>
                            </c:forEach>
                            </tbody>
                            <!-- 
                            <c:forEach items="${usersList}" var="user">
                                <tr>
                                    <td class="column1" style="vertical-align: middle;">
                                        <s:form action="buyerAdminAddEdit_displayEditPage">
                                            <input type="hidden" name="username" value="${user.username}" />   
                                            <s:submit type="input" method="displayEditPage" theme="simple" value="Edit Profile" />               
                                        </s:form>
                                       
                                        <s:form action="buyerAdminAddEdit_displayEditPage">
                                            <input type="hidden" name="username" value="${user.username}" />   
                                            <s:submit type="input" method="displayEditPage" theme="simple" value="Reset Password" />               
                                        </s:form>
                                    </td>
                                    <td class="">
                                        <a href="buyerAdminAddEdit_displayEditPage.action?username=${user.username}">${user.name}</a>
                                        <c:choose>
                                            <c:when test="${not empty user.title}"><br /><strong>Title:</strong> ${user.title}</c:when>
                                        </c:choose>
                                       
                                    </td>
                                    <td class="column4">
                                        <c:choose>
                                          <c:when test="${user.maxSpendLimit==0.0}">
                                            <fmt:message bundle="${serviceliveCopyBundle}" key="buyer.manage_users.value.nolimit" />
                                          </c:when>
                                          <c:otherwise>
                                            $<fmt:formatNumber value="${user.maxSpendLimit}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
                                          </c:otherwise>
                                        </c:choose>
                                    </td>   
                                </tr>
                            </c:forEach>
                               -->
                            </table>
                        </div>
                        <div class="clearfix" style="height:35px;">
                                <s:form action="buyerAdminAddEdit_displayAddPage.action">
                            <input type="submit" class="button action" style="margin-top:5px;" value="Add A New User" />
                                </s:form>
                        </div>
                    </div>           
                </div>
               
                <!-- END TAB PANE -->
                <div class="colRight255 clearfix">
                    <c:if test="${SecurityContext.slAdminInd}">
                            <jsp:include page="/jsp/so_monitor/menu/widgetAdminMemberInfo.jsp"/>
                    </c:if>
                </div>

    </div>
    <tiles:insertDefinition name="blueprint.base.footer"/>
</div>

        <jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
             <jsp:param name="PageName" value="BuyerAdmin.manageUsers"/>
        </jsp:include>   
       
    </body>
</html>