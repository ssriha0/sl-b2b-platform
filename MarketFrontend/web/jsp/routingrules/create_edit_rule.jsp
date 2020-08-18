<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="autoAcceptRuleStatus"
    value="<%=request.getSession().getAttribute("autoAcceptStatus")%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
        <tiles:insertDefinition name="blueprint.base.meta"/>
        
        <link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery-ui.custom.min.css" type="text/css" />
        <link rel="stylesheet" href="${staticContextPath}/css/jqmodal/jqModal.css" type="text/css"></link>
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/routingrules.css" />
        
        <!--[if IE 6]>
        <style type="text/css">
        #addedSpecialtiesWrapper {margin-top:0px;}
        </style>
        <![endif]-->
        <script type="text/javascript">
                autoAcceptRuleStatus = '${autoAcceptRuleStatus}';
        </script>
        <script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
        <script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqueryui/jquery-ui-1.10.4.custom.min.js"></script>
        <script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqmodal/jqModal.js"></script>
        <script type="text/javascript" src="${staticContextPath}/scripts/plugins/superfish.js"></script>
        <script type="text/javascript" src="${staticContextPath}/scripts/plugins/supersubs.js"></script>
        
        <script type="text/javascript" src="${staticContextPath}/javascript/routingrules-create.js"></script>
        <script type="text/javascript" src="${staticContextPath}/javascript/routingrules-create-callbacks.js"></script>
        <script type="text/javascript" src="${staticContextPath}/javascript/routingrules-create-widgets.js"></script>
        <script type="text/javascript">

            var staticContextPath="${staticContextPath}";
            var userAction= '${model.userAction}';   // VIEW, EDIT, CREATE
            
            if ('CREATE' !== userAction) {
                <%-- The action sets some JSON to the model for us to populate RHS choices with --%>
                if ('' !== '${model.chosenProviderFirmsJson}') var chosenProviderFirmsJSON= eval('${model.chosenProviderFirmsJson}');
                if ('' !== '${model.chosenCriterionJson}') var chosenCriterionJSON= "";
                if ('' !== '${model.chosenSpecsJobsJson}') var chosenSpecsJobsJSON= "";
               
            }
            if ('VIEW' == userAction)
                var DISABLED='DISABLED';
            else var DISABLED='';

        </script>
        <%-- Determine CAR authorization --%>
        <c:set var="hasViewPerms" value="false" scope="page" />
        <tags:security actionName="routingRulesAction_view">
            <c:set var="hasViewPerms" value="true" scope="page" />
        </tags:security>
        <c:set var="hasEditPerms" value="false" scope="page" />
        <tags:security actionName="routingRulesAction_edit">
            <c:set var="hasEditPerms" value="true" scope="page" />
        </tags:security>
        <c:if test="${hasViewPerms != 'true' && hasEditPerms != 'true'}">            
            <script type="text/javascript">alert ('You don't have permission to view this rule');</script>
            <c:redirect url="rrDashboard_display.action" />
        </c:if>
        <c:if test="${userAction != 'VIEW' && hasEditPerms != 'true'}">            
            <script type="text/javascript">alert ('You don't have permission to edit this rule');</script>
            <c:redirect url="rrDashboard_display.action" />
        </c:if>
        <c:set var="status" value="" />
        <c:choose>
            <c:when test="${userAction == 'VIEW'}">
                <c:set var="titleAction" value="View Rule" />
                <c:set var="DISABLED" value="DISABLED" scope="page" />
                <c:set var="isDisabled" value="true" scope="page" />
            </c:when>
            <c:when test="${userAction == 'EDIT'}">
                    <c:set var="titleAction" value="Edit Rule" />
                <c:set var="DISABLED" value="" scope="page" />
                <c:set var="isDisabled" value="" scope="page" />
                 <c:set var="status" value="${model.ruleStatus}" />
            </c:when>
            <c:when test="${userAction == 'CREATE'}">
                <c:set var="titleAction" value="Create New Rule" />
                <c:set var="DISABLED" value="" scope="page" />
                <c:set var="isDisabled" value="" scope="page" />
            </c:when>
        </c:choose>
        <title>ServiceLive - Conditional Auto Routing - ${titleAction}</title>
    </head>
    <body id="theBody">
        <input type="hidden" id="status" name="status" value="${status}"/>
        <input type="hidden" id="userAction" name="userAction" value="${userAction}"/>
        <div id="wrap" class="container">
        <tiles:insertDefinition name="blueprint.base.header"/>
        <tiles:insertDefinition name="blueprint.base.navigation"/>
            <div id="content" class="span-24 clearfix">     

        <div id="wrapper">

            <h1>Administrator Office</h1>
            <h2>Conditional Routing</h2>

            <div class="contentWrapper">
                <div id="innerContent">
                    <h3 style="position:relative;">${titleAction}</h3> <%-- the inline position:relative is needed for IE --%>
                    <c:if test="${userAction != 'VIEW'}">
                        <p style="position:relative;">Create filters by selecting the information below.</p>
                        <p style="position:relative;">(<span class="req">*</span>required)</p>
                    </c:if>
                    <c:if test="${not empty model.ruleConflictDisplayVO}">
                                    <div class="routingCriteriaWarningBox" style="display: block; width: 820px; height: 20px; margin-top: 10px; margin-bottom: 10px; word-wrap: break-word;">
                                    
                                        <b>ALERT:
                                        <c:out value="Rule Criteria Conflict(s). Details" /></b>
                                        <a href="" title="" id="${rule.routingRuleHdrId}rule" style="text-decoration:none; font-weight:bold; color:#00A0D2;"
                                    class="commentsClick" onclick="jQuery('#ruleComments').show('fast');return false;">(+)</a>
                                    </div>
                   
                                        <div id="ruleComments" style="z-index: 50;margin-left: 250px; word-wrap: break-word; width:600px;">
                                                <div style="text-align: right;">
                                                <a href="" class="cancelLink"
                                                onclick="jQuery('#ruleComments').hide();return false;">Close</a>
                                                </div>
                                                <div>
                                                        <c:forEach items="${model.ruleConflictDisplayVO}" var="conflict">
                                                        <c:if test="${model.ruleConflictDisplayVO[0] != conflict}">
                                                        <hr/><br/>
                                                        </c:if>
                                                        <b>Rule Name</b><br>
                                                        ${conflict.ruleName}
                                                        <br><c:if test="${not empty conflict.ruleId}">
                                                        <b>ID#</b> 
                                                        ${conflict.ruleId}
                                                        </c:if><br><br>
                                                        **Conflicts**<br>
                                                        <c:if test="${conflict.zipCodes!=null && conflict.zipCodes!=''}">
                                                        <b>Zip Codes:</b>&nbsp;&nbsp;&nbsp;${conflict.zipCodes}<br>
                                                        </br>
                                                        </c:if>
                                                        <c:if test="${conflict.jobCodes!=null &&conflict.jobCodes!=''}">
                                                        <b>Job Codes:</b>&nbsp;&nbsp;&nbsp;${conflict.jobCodes}<br>
                                                        </br>
                                                        </c:if>
                                                        <c:if test="${conflict.pickupLocation!=null && conflict.pickupLocation!=''}">
                                                        <b>Pick Up Location Codes:</b>&nbsp;&nbsp;&nbsp;${conflict.pickupLocation}<br>
                                                        </br>
                                                        </c:if>
                                                         <c:if test="${conflict.markets!=null && conflict.markets!=''}">
                                                        <b>Markets:</b>&nbsp;&nbsp;&nbsp;${conflict.markets}<br><br>
                                                        </br>
                                                        </c:if>
                                                        <br/>                                    
                                                        </c:forEach>
                                                </div>
                    </div> 
                    </c:if>
                    <form id="createRule" onsubmit="validateFields(); return false;" action="rrCreateRuleAction_savedone.action" method="post">
                        <input type="hidden" name="rid" id="rid" value="${model.ruleId}" />
                        <input type="hidden" name="save" id="save" value="" />
                        <input type="hidden" name="makeInactive" id="makeInactive" value="" />
                        <input type="hidden" id="job_price_save" name="job_price_save" value=""/>
                        <a name="err" id="validationErrorAnchor"></a>
                        <div id="validationError" class="errorMsg"></div>
                        <fieldset class="ruleName">
                            <label for="ruleName">&nbsp;Rule Name
                                <span class="req">*</span>
                            </label>
                            <!-- Changes starts for SL-20715 -->
                            <input type="text" id="ruleName" name="ruleName" class="${DISABLED}" value="${fn:escapeXml(rulename)}" maxlength="90" ${DISABLED} />
                                  <!-- Changes ends for SL-20715 -->
                            <p class="explainer">The name should be unique and descriptive of the purpose.</p>
                        </fieldset>
                         <c:if test="${userAction == 'VIEW'|| userAction == 'EDIT'}">
                        <fieldset class="ruleId">
                            <label for="ruleId">
                            <span style="font-size:100%">Rule ID:</span>
                            <span style="color:#666;font-size:100%">${model.ruleId}</span>
                            </label>
                        </fieldset>
                        </c:if>
                        <h5 style="position:relative;">Contact Information <span class="normal">(Used for Alerts)</span></h5> 
                        <fieldset class="contactInfo">
                            <fieldset class="names">
                                <div>
                                <label for="firstName">First Name<span class="req">*</span></label>
                                <input type="text" id="firstName" name="firstName" class="${DISABLED}" value="${firstname}" maxlength="50" ${DISABLED} />
                                </div>
                                <div><label for="lastName">Last Name<span class="req">*</span></label>
                                <input type="text" id="lastName" name="lastName" class="${DISABLED}" value="${lastname}" maxlength="50" ${DISABLED} />
                                </div>
                            </fieldset>
                            <fieldset class="emails">
                                <div><label for="email">Email<span class="req">*</span></label>
                                <input type="text" id="email" name="email" class="${DISABLED}" value="${email}" maxlength="255" ${DISABLED} />
                                </div>
                                <div><label for="verifyEmail">Verify Email<span class="req">*</span></label>
                                <input type="text" id="verifyEmail" name="verifyEmail" class="${DISABLED}" value="${email}" maxlength="255" ${DISABLED} />
                                </div>
                            </fieldset>
                        </fieldset>
                        <hr noshade="noshade"/>

                        <h4>Select a Provider Firm</h4>
                            <div class="lhsTableWrapper">
                                <div id="validationError_firms" class="errorMsg"></div>
                                <fieldset class="firmId">
                                    <label for="firmId-textbox">Firm ID
                                        <span class="req">*</span>
                                    </label>
                                    <input type="text" id="firmId-textbox" name="firmId-textbox" value="" maxlength="11" ${DISABLED} />
                                    <c:if test="${userAction != 'VIEW'}">
                                        <input type="button" class="button action" value="Add" id="addFirmId-button" />
                                    </c:if>
                                </fieldset>
                            </div>

                        <div class="inlineTableWrapper" id="firms-tablewrap" style="width:500px;">
                        </div>

                        <hr noshade="noshade"/>
                    <div> <%-- ss: need this div for IE6 - dont' ask me why ;) --%>
                        <div id="setRulePreferences">
                            <h4>Set Rule Preferences
                                <span class="req">*</span>
                            </h4>
                            <p>Select at least <span class="bold">ONE</span> of the attributes below to create the filters for this rule.</p>
                        
                        <div id="copyRuleContainer">
                        <c:if test="${userAction != 'VIEW'}">
                        <div style="height:20px; vertical-align:middle;">
                        <input type="checkbox" id="copyRuleTrigger" style="float:left;" />
                        <p style="float:left; font-weight:bold; margin:4px;">Copy from existing rule</p>
                        </div>
                            <fieldset class="copyRule">
                            <s:select name="copyRule" id="copyRules-select" headerKey="-1" headerValue="Select One" 
                                        list="buyerRules" listKey="value" listValue="label"  />
                                <input type="button" class="button action" value="Copy" id="copyRule-button"/>
                            </fieldset>
                        </c:if>
                        </div>
                        
                        </div>

                        <div class="rulePreferences" id="rulePreferences">
                            <h5>Select a Custom Reference</h5>
                            <div class="lhsTableWrapper">
                             <div id="error_customRefs" class="errorMsg" style="word-wrap: break-word;"></div>
                              <div id="validationError_customRefs" ></div>
                              
                              <fieldset class="customReference">
                                    <div style="float:left; padding-top:5px;width: 125px;">
                                      <label for="customReference">Custom Reference</label>
                                  </div>
                                  <div style="float:left;">
  
                                      <s:select id="customRef-select" name="customRef-select"
                                          headerKey="-1" headerValue="--Select One--"
                                          list="customReferenceChoices" listKey="value" cssStyle="width:225px" listValue="label" disabled="%{isDisabled}" /></div>
                                   <table style="padding: 0px;border: none;">
                                    <tr style="border: none;">
                                        <td style="padding: 0px; width:125px;border: none;"></td>
                                        <td style="padding: 0px; width:225px;border: none;">
                                              <textarea name="customRef-textbox" id="customRef-textbox" rows="" style="width:225px;resize:none;"  cols="" DISABLED></textarea>
                                         </td>
                                        <td style="vertical-align: bottom;border: none;" align="left">
                                          <c:if test="${userAction != 'VIEW'}">
                                                <input type="button" onclick="return false;" class="button action" value="Add" id="addCustomRef-button"/>
                                          </c:if>
                                         </td>
                                    </tr>
                                    <tr style="border: none;">
                                        <td style="border: none;"></td>
                                        <td style="padding: 0px;width:225px;border: none;"><p>Separate multiples with a comma (,) up to 50.</p></td>
                                        <td style="border: none;"></td>
                                    </tr>
                                 </table>
                              </fieldset>
                            </div>

                            <div  id="customRefsTable">
                                <jsp:include page="routing_rule_custom_reference.jsp" />
                            </div>

                            <hr noshade="noshade"/>

                            <div id="zipfocus"><h5>Create an Area by Market or Zip Code</h5></div>
                                
                            <div class="lhsTableWrapper">
                                <div id="validationError_marketsZips" class="errorMsg" style="word-wrap: break-word;"></div>
                                <fieldset class="byMarket" style="margin-left: 60px">
                                    <label for="byMarket" class="left">Market</label>
    
                                    <div class="left">
                                        <select id="byMarket" name="byMarket" ${DISABLED}>
                                            <option>Select all that apply</option>
                                        </select>
                                        <br/>
                                        <div class="dropdownTableContainer" id="byMarketTableContainer">
                                            <table class="dropdownTable" id="dropdownTableByMarket" border="0" cellpadding="0" cellspacing="0">
                                                <tr>
                                                    <td colspan="2" style="height:15px;">&nbsp;</td>
                                                </tr>
                                                <s:iterator value="marketsChoices">
                                                    <tr>
                                                        <td class="checkboxTd">
                                                            <input class="multiSelectCheckbox" type="checkbox" name="marketChoices-checks" value="${value}" id="marketChoices-value${value}" />
                                                        </td>
                                                        <td>
                                                            <label id="marketChoices-label${value}" for="marketChoices-value${value}">${label}</label>
                                                        </td>
                                                    </tr>
                                                </s:iterator>
                                            </table>
                                        </div>
                                    </div>
    
                                    <c:if test="${userAction != 'VIEW'}">
                                        <input type="button" onclick="return false;" class="button action right" value="Add" id="addMarket-button" />
                                    </c:if>
                                </fieldset>

                                <h5 style="margin-left:10px;">OR Create an Area by Zip Code
                                    <img src="${staticContextPath}/images/icons/iconPencil.gif" style="vertical-align:middle;"
                                        onclick="showMarketZipDiv()" class="pointer"/>
                                </h5>

                                <%-- dropdown for 'create an area by zip' --%>
                                <div id="zipInlineForm">
                                    <b>Enter list of Zip codes or look up by state</b>
                                    <div id="errorZipMarket"></div>
                                    <fieldset>
                                        <table style="padding: 0px; border: none;">
                                    <tr style="padding: 0px; border: none;" >
                                        <td style="vertical-align: top; padding: 0px; width:90px; border: none;" align="right">
                                        <input type="radio" name="zipAddMethod" id="zipAddMethodZip" class="left zipRadio" value="zip" />
                                            <label for="zipAddMethodZip" style="float:left;" class="left">&nbsp;Zip Code</label>
                                        </td>
                                        <td style="padding: 0px; width:175px; border: none;">
                                             <textarea name="zipCode-textbox" cols="50" style="width:175px;resize:none;" id="zipCode-textbox"></textarea>
                                         </td>
                                        <td style="vertical-align: bottom; border: none;padding: 2px;" align="left">
                                        <c:if test="${userAction != 'VIEW'}">
                                                 <input type="button" class="button action" value="Add" id="addZip-button"/>
                                        </c:if>
                                         </td>
                                    </tr>
                                    <tr style="padding: 0px; border: none;">
                                        <td style="padding: 0px; border: none;"></td>
                                        <td style="padding: 0px;width:175px; border: none;"><p>Separate multiples with a comma (,) up to 250.</p></td>
                                        <td style="padding: 0px; border: none;"></td>
                                    </tr>
                                 </table>                                  
                                    </fieldset>
                                    <fieldset>
                                        <input type="radio" name="zipAddMethod" id="zipAddMethodState" class="left zipRadio" value="state" />
                                        <label for="zipAddMethodState" style="float:left;" class="left">&nbsp;State&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
    
                                        <s:select id="zipCodeByState-select" name="zipCodeByState-select"
                                                  headerKey="-1" headerValue="Select One" 
                                                  list="usStates" listKey="value" listValue="label" disabled="%{isDisabled}" cssStyle="margin-left:18px;"/>

                                        <div id="zipCodesInState-wrapper">
                                      
                                        </div>

                                    </fieldset>
                                    <div>
                                        <a href="#" onclick="cancelCreateAreaByZip();return false;" class="cancelLink right">Cancel</a>
                                    </div>
                                </div>
                            </div>

                            <%-- RHS for zips/markets --%>
                            <div id="marketsTable">
                             <jsp:include page="routing_rule_zipcodes.jsp" />
                            </div>


                            <hr noshade="noshade"/>
                            <h5>Enter Job Codes</h5>
                            <br/>
                            <div class="lhsTableWrapper">
                             <div id="error_jobCodes" class="errorMsg" style="word-wrap: break-word;"></div>
                              <div id="validationError_jobCodes" ></div>
                              <fieldset class="customReference">
                                 <table style="padding: 0px; border: none;">
                                    <tr style="padding: 0px; border: none;" >
                                        <td style="vertical-align: top; padding: 0px; width:100px; border: none;" align="right">
                                      <label for="customReference">Job Codes</label>
                                        </td>
                                        <td style="padding: 0px;width:235px; border: none;">
                                             <textarea name="jobCode-textbox" id="jobCode-textbox"  rows="" class="${DISABLED}" style="width:235px;height:75px;resize:none;" cols="" ${DISABLED}></textarea>
                                         </td>
                                        <td style="vertical-align: bottom; border: none;" align="left">
                                          <c:if test="${userAction != 'VIEW'}">
                                                <input type="button" class="button action" value="Add" id="addJobCodes-button" />
                                          </c:if>
                                         </td>
                                    </tr>
                                    <tr style="padding: 0px; border: none;">
                                        <td style="padding: 0px; border: none;"></td>
                                        <td style="padding: 0px;width:235px; border: none;"><p>Separate multiples with a comma (,) up to 150.</p></td>
                                        <td style="padding: 0px; border: none;"></td>
                                    </tr>
                                 </table>
                              </fieldset>
                            </div>
                            
                            <div id="showJobPrice" class="right">
                                <jsp:include page="routing_rule_jobCode.jsp" />
                            </div>
                            <%-- Only show price button when editing an EXISTING rule; otherwise, prices are ON by default.
                            <c:if test="${userAction != 'VIEW'}">
                                <input type="button" id="priceJobCodesButton" class="button action left" value="Price Job Codes" onclick="enablePriceFields();" />
                            </c:if>
                            --%>
                            <br style="clear:both;"/>
                        </div>
                        </div>
                        <input type="hidden" id="tabType" name="tabType" value="${tabType}" />
                        <div id="activeEditConflict">
                            
                        </div>
                        <div style="margin-top:10px;">
                            <div style="float:left;">
                                <c:choose>
                                <c:when test="${tabType == 'searchTab'}">
                                <c:set var="searchSession" scope="session" value="true" />
                                </c:when>
                                <c:otherwise>
                                <c:set var="searchSession" scope="session" value="false" />
                                </c:otherwise>
                                </c:choose>
                                <a href="rrDashboard_decision.action?tabType=${tabType}&searchSession=${searchSession}" id="cancelEdit" class="cancelLink">Cancel</a>
                                
                            </div>
                            <c:if test="${hasEditPerms == 'true' &&( userAction == 'EDIT' || userAction == 'CREATE')}"> 
                                <input type="submit" id="saveNDone" class="button action right" value="Save & Done" />
                            </c:if>
                        </div>

                        <div id="confirmDialog" class="jqmWindow" style="left: 58%; top: 30%; background-color: white;">
                            <div class="dialogHeader">
                                <img src="${staticContextPath}/images/icons/modalCloseX.gif" onclick="return cancelModal();" />
                                <h3>Confirmation</h3>
                            </div>
                            <div id="dialogContent">                                
                                <c:if test="${userAction == 'EDIT'}">
                                <p>The changes you've entered will override the settings for this rule.</p>
                                </c:if>
                                <div id="autoAcceptChangeMsg" style="display: none;padding-left:5px;"><strong style="color: red;">
                                The Auto acceptance status will be reset from "On" to "Pending" for Provider firm's response.
                                </strong></div>
                            <p>Are you sure you want to save these settings?</p>
                                <p>(
                                    <span class="req">*</span> required)</p>
                                <label for="ruleComment" class="left bold">Comments
                                    <span class="req">*</span>
                                </label>
                                <textarea name="ruleComment" id="ruleComment" rows="2" cols="80" style=resize:none;"></textarea>
                                <div class="dialogFooter">
                                    <div style="float:left;padding-top:10px;">
                                        <a href="" onclick="return cancelModal();" class="cancelLink">Cancel</a>
                                    </div>
                                    <c:if test="${userAction != 'VIEW'}">
                                        <input type="button" id="modalSaveDone" class="button action right" value="Save" />
                                        
                                    </c:if>
                                </div>
                            </div>
                        </div>

                    </form>
                </div>
            </div>

            <script type="text/javascript">
                jQuery('#carTabs').tabs();
            </script>
        </div>
        
               </div>
               
       <tiles:insertDefinition name="blueprint.base.footer"/>
      </div>

      <%-- <s:debug /> --%>
<!-- ${model.ruleId} -->

    </body>
</html>

<%--
<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.cancel" />
 --%>