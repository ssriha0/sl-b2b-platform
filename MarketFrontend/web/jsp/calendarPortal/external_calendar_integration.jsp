<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme() + "://" + request.getServerName()
        + ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<c:set var="fullContextPath" scope="request" value="<%=request.getScheme() + "://" + request.getServerName()
        + ":" + request.getServerPort() + request.getContextPath()%>" />

<head>
    <!-- acquity: modified meta tag to set charset -->
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />

    <title>ServiceLive - External Calendar Integration</title>
    <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
    <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
    <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
    <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
    <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />

    <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
    <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/jqueryui/jquery-ui-1.10.4.custom.min.css" />
    <script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqueryui/jquery-ui-1.10.4.custom.min.js"></script>
    <style type="text/css">
        .ie7 .bannerDiv {
            margin-left: -1020px;
        }
    </style>
    <script type="text/javascript">
    var contextPath = '${fullContextPath}';
    var authCode = '${param.code}';
    var refresh = false;
	jQuery(document).ready(function() {
        console.log('context path: '+contextPath);
        if(authCode!=null && authCode!=''){
            window.opener.location.reload(true);
            window.close();
        }        
    });
    var popupWindow=null;
    function performCronofyOauth() {
        popupWindow = window.open('https://app.cronofy.com/oauth/authorize?response_type=code&client_id=04EgkMlpYDMuX32hourcBI8GAyGqjyli&redirect_uri='+contextPath+'/calendar_portal/externalCalendarIntegration.action&scope=read_events create_event delete_event'
        ,"OAuth","directories=no, status=no, menubar=no, scrollbars=yes, resizable=no,width=560, height=800,top=50,left=200");
    }
    </script>
</head>
<body class="tundra acquity">
    <div id="page_margins">
        <div id="page">
            <div id="header">
                <tiles:insertDefinition name="newco.base.topnav" />
                <tiles:insertDefinition name="newco.base.blue_nav" />
                <tiles:insertDefinition name="newco.base.dark_gray_nav" />
                <div id="pageHeader"></div>
            </div>
            <!-- END HEADER -->
            <div id="hpWrap" class="clearfix">
                <table class="buyerUpload" border="1" cellpadding="0" cellspacing="0">
                    <tr style="width: 70%;">
                        <th style="text-align: center; width: 14%">Provider</th>
                        <th style="text-align: center; width: 14%">Email</th>
                        <!--<th style="text-align: left; width: 14%">CalendarID</th>-->
                        <th style="text-align: center; width: 14%">Synced</th>
                    </tr>
                    <s:if test="externalCalendarDTO!=null">
                    <tr style="width: 70%;">
                        <td><s:property value="externalCalendarDTO.calendarSource"/></td>
                        <td><s:property value="externalCalendarDTO.emailId"/></td>
                        <td><s:property value="externalCalendarDTO.synced"/></td>
                    </tr>
                    </s:if>
                    <!--<tr style="width: 70%;">
                        <td>Apple</td>
                        <td>malharbhuptani@icloud.com</td>
                        <td>Yes</td>
                    </tr>-->
                </table>
            </div>
            <div>
                <p>Integrate new external calendar with ServiceLive.</p>
                <button onclick="performCronofyOauth()">Integrate External Calendar</button>
            </div>
            <!--TO Show POP UP Screen  -->
            <input type="hidden" name="error" id="error" value="${runningErrorList}" />
            <div id="lms" class="jqmWindow" style="width: 775px; position: center; margin-left: -400px;">
                <div class="modal-header">
                    <a href="<s:url value='%{contextPath}/lmsFileGetAction_getLmsDetailHistory.action'/>" class="right" style="color: red;">Close</a>
                </div>
                </br>
                <div class="modal-content" style="margin-bottom: 15px;">

                    <h2 style="display: block">LMS Error History Details</h2>
                    <h3>
                        File Name:<label id="fileName2"></label>
                    </h3>
                    <div>
                        <table cellpadding="0" cellspacing="0" width="775px">
                            <thead style="overflow: auto; height: 500%; size: auto;">
                                <tr style="color: white; height: 25px; background-color: #00A0D2;">
                                    <th width="187px">Record Text</th>
                                    <th width="187px">Failure Reason</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                    <div style="overflow: scroll; height: 200px;">
                        <table border="1%" cellpadding="0" cellspacing="0" width="auto;">
                            <tbody style="text-align: left;">
                                <c:forEach items="${runningErrorList}" var="lmsError">
                                    <tr style="height: 20px; font: inherit;">
                                        <input type="hidden" name="fileName" id="fileName" value="${lmsError[0]}" />
                                        <th style="font: inherit; width: 375px;">${lmsError[1]}</th>
                                        <th style="font: inherit; width: 375px;">${lmsError[2]}</th>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <jsp:include page="/jsp/public/common/defaultFooter.jsp" />
        </div>
    </div>
</body>
</html>