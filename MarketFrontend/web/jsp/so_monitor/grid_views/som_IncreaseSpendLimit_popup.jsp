<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.newco.marketplace.auth.SecurityContext"%>
<c:set var="currentTab" value="${tab}" scope="session" />
<c:set var="currentSO" value="${data}" scope="session" />
<c:set var="taskLevelPriceInd" value="${taskLevelPriceInd}"
    scope="session" />
<c:set var="msg" value="${msg}" scope="session" />
<c:set var="staticContextPath" scope="request"
    value="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                                        + "/ServiceLiveWebUtil"%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link href="${staticContextPath}/javascript/confirm.css"
    rel="stylesheet" type="text/css" />
<link rel="stylesheet"
    href="${staticContextPath}/css/jqueryui/jquery.modal.min.css"
    type="text/css"></link>
<script type="text/javascript"
    src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript"
    src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
<script type="text/javascript"
    src="${staticContextPath}/javascript/formfields.js"></script>

<style type="text/css">
.priceHistory_head {
    padding: 0px;
    font-family: arial;
    font-size: 11px;
    font-weight: bold;
}

.priceHistory_body {
    padding: 5px;
    font-family: arial;
    font-size: 10px;
    /*font-weight:bold;*/
}
.modal {
    max-width: 100%;
    width: 100%;
    padding: 0px 0px;
}
</style>
</head>
<body>
    <div class="modalheader"
        style="background: none repeat scroll 0% 0% rgb(88, 88, 90); color: #FFFFFF; text-align: left; height: 25px; padding-top: 10px; padding-right: 4px; padding-left: 15px;">
        <b>Increase Price</b> 
        <a href="javascript: void(0)"
            class="btnBevel simplemodal-close" style="color: white;float:right" >Close</a>
    </div>

    <form id="frmIncreaseSpendLimit" name="frmIncreaseSpendLimit"
        action="incSpendLimitAction.action">
        <div id="checkspend" class="divContainerUp" style="visibility: hidden">
        </div>
        <div align="center">

            <div class="modalheaderoutline">
                <div class="rejectServiceOrderFrame"
                    style="width: 350px; border: 0px">

                    <div class="rejectServiceOrderFrameBody" style="width: 300px;">

                        <!-- Display validation message -->
                        <div class="errorBox clearfix"
                            id="increaseSPendLimitResponseMessage"
                            style="width: 300px; overflow-y: hidden; display: none;"></div>

                        Your current spend limit is shown below. Enter the new amount,
                        then click "submit". You may provide a reason for the increase. <br />
                        <input type="hidden" name="buyerId" id="buyerId" value="${currentSO.buyerId}" /> 
                        <input type="hidden" name="selectedSO" id="selectedSO" value="${data.soId}" /> 
                        <input type="hidden" name="taskLevelPriceInd" id="taskLevelPriceInd"
                            value="${taskLevelPriceInd}" /> 
                        <input type="hidden" name="currentTab" id="currentTab" value="${currentTab}" /> 
                        <input type="hidden" name="increasedSpendLimitReason"
                            id="increasedSpendLimitReason" value="" /> 
                        <input type="hidden" name="increasedSpendLimitReasonId"
                            id="increasedSpendLimitReasonId" value="" /> 
                        <input type="hidden" name="increasedSpendLimitNotes" id="increasedSpendLimitNotes"
                            value="" /> <b>&nbsp;&nbsp;Increase Current Maximum Price:</b>
                        <table>
                            <br />
                            <tr></tr>


                        </table>
                        <div style="overflow-y: auto; overflow-x: hidden; height: 150px">
                            <table style="border-bottom: 1px solid #ccc;">
                                <c:set var="taskNumber" value="0" />
                                <c:set var="totalTaskPrice" value="0" />
                                <c:set var="seqNum" value="-1" />
                                <c:set var="index" value="0" />
                                <c:forEach var="task" items="${data.tasks}"
                                    varStatus="rowCounter">
                                    <c:if
                                        test="${!( task.taskStatus!=null && (task.taskStatus=='CANCELED' ||task.taskStatus=='DELETED'))}">
                                        <c:set var="count" value="${count+1}" />
                                        <input type="hidden" value="${task.sku}"
                                            id="taskList[${index}].sku" name="taskList[${index}].sku" />
                                        <input type="hidden" value="${task.taskId}"
                                            id="taskList[${index}].taskId"
                                            name="taskList[${index}].taskId" />
                                        <input type="hidden" value="${task.taskType}"
                                            id="taskList[${index}].taskType"
                                            name="taskList[${index}].taskType" />
                                        <input type="hidden" value="${task.sequenceNumber}"
                                            id="taskList[${index}].sequenceNumber"
                                            name="taskList[${index}].sequenceNumber" />
                                        <c:if
                                            test="${task.sequenceNumber == 0 || task.sequenceNumber == seqNum}">
                                            <input type="hidden" id="taskList[${index}].finalPrice"
                                                name="taskList[${index}].finalPrice" value="0.00" />
                                        </c:if>
                                        <c:if
                                            test="${task.sequenceNumber != 0 && task.sequenceNumber != seqNum}">
                                            <tr style="height: 20px">
                                                <c:set var="taskNumber" value="${taskNumber+1}" />
                                                <td
                                                    style="align: left; width: 190px; padding-bottom: 15px; padding-left: 8px">
                                                    Task ${taskNumber}: ${task.taskName}</td>
                                                <td width="10" style="padding-bottom: 15px;"></td>
                                                <td style="padding-bottom: 15px;">$</td>

                                                <td width="10" style="padding-bottom: 15px;"></td>
                                                <td style="padding-bottom: 15px;"><c:if
                                                        test="${task.taskType == 1}">
                                                        <td><fmt:formatNumber value="${task.sellingPrice}"
                                                                type="NUMBER" minFractionDigits="2"
                                                                maxFractionDigits="2" /> <input type="hidden"
                                                            id="taskList[${index}].finalPrice"
                                                            name="taskList[${index}].finalPrice"
                                                            value="${task.sellingPrice}" /></td>
                                                    </c:if> <c:if test="${task.taskType == 0}">
                                                        <td><input type="text"
                                                            onFocus="findPos(this,'checkspend')"
                                                            onblur="findMaxLabor(this);"
                                                            id="taskList[${index}].finalPrice" size="9"
                                                            value="<fmt:formatNumber value="${task.finalPrice}" type="NUMBER"  minFractionDigits="2" maxFractionDigits="2" />"
                                                            name="taskList[${index}].finalPrice"
                                                            class="textbox-60 alignRight shadowBox" /></td>
                                                        <c:set var="totalTaskPrice"
                                                            value="${totalTaskPrice + task.finalPrice}" />
                                                    </c:if></td>

                                                <td width="10"><c:if
                                                        test="${fn:length(task.priceHistoryList) > 1}">
                                                        <a id="${count}" class="priceHistoryIcon"
                                                            href="javascript:void(0);"><img
                                                            src="${staticContextPath}/images/widgets/dollaricon.gif" /></a>
                                                    </c:if></td>

                                            </tr>
                                        </c:if>
                                        <c:if test="${! empty task.sequenceNumber }">
                                            <c:set var="seqNum" value="${task.sequenceNumber}" />
                                        </c:if>
                                        <c:set var="index" value="${index+1}" />
                                    </c:if>
                                </c:forEach>

                                <input type="hidden" value="${count}" id="taskCount" />

                            </table>
                        </div>
                    </div>
                    <c:set var="seqNum" value="-1" />
                    <c:forEach var="task1" items="${data.tasks}" varStatus="status">
                        <c:if
                            test="${!( task1.taskStatus!=null && (task1.taskStatus=='CANCELED' ||task1.taskStatus=='DELETED'))}">
                            <c:set var="countHist" value="${countHist+1}" />
                            <c:if
                                test="${task1.sequenceNumber != 0 && task1.sequenceNumber != seqNum}">
                                <c:set var="taskNumberHis" value="${taskNumberHis+1}" />

                                <div id="${countHist}priceHistory" class="priceHistory"
                                    style="position: absolute; left: 400px; top: 800px; width: 215px; background-color: white; border: 4px outset grey; z-index: 999999;">
                                    <div class="priceHistory_head" style="float: left;">
                                        <table cellspacing="0">
                                            <tr>
                                                <th colspan="3">Price History for Task ${taskNumberHis}</th>
                                            </tr>
                                            <tr>
                                                <td width=60px; style="border-bottom: 1px solid #ccc;">
                                                    Price</td>
                                                <td width=80px; style="border-bottom: 1px solid #ccc;">
                                                    Date</td>
                                                <td width=90px; style="border-bottom: 1px solid #ccc;">
                                                    Changed By</td>
                                            </tr>
                                            <c:forEach var="history" items="${task1.priceHistoryList}">
                                                <tr>
                                                    <td width=60px;>$<fmt:formatNumber
                                                            value="${history.price}" type="NUMBER"
                                                            minFractionDigits="2" maxFractionDigits="2" />
                                                    </td>

                                                    <td width=80px;>${history.modifiedDateFmt}</td>

                                                    <td width=90px;>${history.modifiedByName} <c:if
                                                            test="${history.modifiedByName!='SYSTEM'}">
                                                         (ID#${history.modifiedBy})
                                                        </c:if>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${! empty task1.sequenceNumber }">
                                <c:set var="seqNum" value="${task1.sequenceNumber}" />
                            </c:if>
                        </c:if>
                    </c:forEach>
                    <input type="hidden" value="" id="taskErrorCount"
                        name="taskErrorCount" /> <input type="hidden"
                        id="currentLimitLaborAmt" name="currentLimitLaborAmt"
                        value="${currentSO.spendLimitLabor}" />
                    <table style="border-bottom: #ccc 1px solid;" width="300">
                        <tr>
                            <td width="190" align="right"
                                style="padding-bottom: 15px; padding-left: 8px;">Maximum
                                Labor:</td>
                            <td width="10" style="padding-bottom: 15px;"></td>
                            <td>$</td>
                            <td width="10" style="padding-bottom: 15px;"></td>
                            <td style="padding-bottom: 15px;">
                                <div id="maxLabor">
                                    <fmt:formatNumber value="${totalTaskPrice}" type="NUMBER"
                                        minFractionDigits="2" maxFractionDigits="2" />
                                </div>
                            </td>
                            <td width="10"><input type="hidden"
                                name="totalSpendLimitAmt" id="totalSpendLimitAmt" /></td>

                        </tr>
                        </br>
                        <tr>
                            <td width="190" align="right" style="padding-bottom: 15px;">
                                Maximum Materials:</td>
                            <td width="10" style="padding-bottom: 15px;"></td>
                            <td style="padding-bottom: 15px;">$</td>
                            <td width="10" style="padding-bottom: 15px;"></td>
                            <td style="padding-bottom: 15px;"><input type="hidden"
                                id="totalSpendLimitParts" name="totalSpendLimitParts" /> <input
                                type="text" onFocus="findPos(this,'checkspend')"
                                onblur="findMaximumLabor(this);" id="increaseLimitParts"
                                size="9" name="increaseLimitParts"
                                value="<fmt:formatNumber value="${data.spendLimitParts}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />"
                                class="textbox-60 alignRight shadowBox" /></td>
                            <td width="10"><input type="hidden"
                                id="currentLimitPartsAmt" name="currentLimitPartsAmt"
                                value="${currentSO.spendLimitParts}" /></td>
                        </tr>
                    </table>
                    <table style="border-bottom: 1px solid #ccc; width: 300px;">
                        <tr>
                            <td width="190" align="right" style="padding-bottom: 15px;">
                                New Combined Maximum:</td>
                            <td width="2" style="padding-bottom: 15px;"></td>
                            <td style="padding-bottom: 15px;">$</td>
                            <td width="5" style="padding-bottom: 15px;"></td>

                            <td style="padding-bottom: 15px;">
                                <div id="totalAmt">
                                    <fmt:formatNumber
                                        value="${currentSO.spendLimitLabor+currentSO.spendLimitParts}"
                                        type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
                                </div>
                            </td>
                            <td width="10"></td>
                        </tr>
                    </table>
                    <br /> <b>Reason <font color="red">*</font> :
                    </b> <br>
                    <c:choose>
                        <c:when test="${fn:length(spendLimitReasonCodes) > 0}">
                            <br>
                            <select name="reason_som" id="reason_som"
                                style="width: 300px; border: 2px solid #CCCCCC"
                                onchange="fnEnable();">
                                <option id="Select" value="-1">- Select a reason -</option>

                                <c:forEach var="reasonCode" items="${spendLimitReasonCodes}">
                                    <option id="reasonCode" value="${reasonCode.reasonCodeId}">
                                        ${reasonCode.reasonCode}</option>
                                </c:forEach>
                                <option id="Other" value="-2">Other</option>
                            </select>
                            <br />
                            <br />
                            <b>Notes </b>
                            <i>(Required if 'Other' is selected above)</i> :
                                                              <br />
                            <br />
                            <textarea id="comment_som" name="comment_som" class="shadowBox"
                                disabled="disabled" style="width: 300px; background: #E3E3E3;"
                                onkeyup="limitText(this,255);" onkeydown="limitText(this,255);"></textarea>
                            <br />
                        </c:when>
                        <c:otherwise>
                            <br />
                            <br />
                            <textarea id="comment_som" name="comment_som" class="shadowBox"
                                style="width: 300px;" onkeyup="limitText(this,255);"
                                onkeydown="limitText(this,255);"></textarea>
                            <br />
                        </c:otherwise>
                    </c:choose>
                    <br /> <img id="submitIncSpendLimitBtn"
                        src="${staticContextPath}/images/common/spacer.gif" width="72"
                        height="22"
                        style="background-image: url(${staticContextPath}/images/btn/submit.gif); float: right;"
                        class="btnBevel" /> <a href="#"
                        style="float: left; padding-right: 18px; color: red;"
                        class="btnBevel simplemodal-close"> Cancel </a>
                    <div style="clear: both;"></div>

                </div>
            </div>
        </div>
    </form>
</body>
<script type="text/javascript">

      var updateOrderExpressMenuWithPrice = function(data){
        var passfail = data.getElementsByTagName('pass_fail')[0].childNodes[0].nodeValue;
        if(passfail==1||passfail=='1'){
                var rowIndex = document.getElementById('selectedRowIndex').value;
                var spendLimit = data.getElementsByTagName('addtional_02')[0].childNodes[0].nodeValue;
                var spendLimitParts = data.getElementsByTagName('addtional_03')[0].childNodes[0].nodeValue;
            newco.jsutils.updateOrderExpressMenu(_commonSOMgr.widgetId, rowIndex, spendLimit, spendLimitParts);
            jQuery.modal.impl.close(true);
        }else if(passfail==0||passfail=='0'){
            var errorMsg = data.getElementsByTagName('message')[0].childNodes[0].nodeValue;
            document.getElementById('increaseSPendLimitResponseMessage').innerHTML = errorMsg;
            document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
        }else{
            jQuery.modal.impl.close(true);
        }
        
    }

    jQuery(document).ready(function ($) {
            document.getElementById('increaseSPendLimitResponseMessage').style.display= "none";
            var requestSubmitted = false;
            $('#submitIncSpendLimitBtn').click(function(){
                requestSubmitted = false;
                if(!requestSubmitted){
                    var success = true;
                    var currentLimitPartsAmt = parseFloat($('#currentLimitPartsAmt').val());
                    var currentLimitLaborAmt = parseFloat($('#currentLimitLaborAmt').val());
                    var maxLabor = parseFloat(document.getElementById('totalSpendLimitAmt').value);
                    var maxParts = parseFloat(document.getElementById('increaseLimitParts').value);
                    var reasonCode = "";
                    var reasonCodeId = "";
                    if($('#reason_som').val()!= null){
                     reasonCodeId = document.getElementById('reason_som').value;
                     var selected_index = document.getElementById('reason_som').selectedIndex;
                     reasonCode = document.getElementById('reason_som').options[selected_index].text;
                    }               
                    var reasonComment=document.getElementById('comment_som').value;
                    reasonComment = jQuery.trim(reasonComment);
                    document.getElementById('increasedSpendLimitReason').value = reasonCode;
                    document.getElementById('increasedSpendLimitReasonId').value = reasonCodeId;
                    document.getElementById('increasedSpendLimitNotes').value = reasonComment;
                    if(maxLabor < 0 || maxParts <0){
                        success = false;
                    }
                    if(reasonCodeId == "-1"){
                        document.getElementById('increaseSPendLimitResponseMessage').innerHTML = "Select a Reason for Spend Limit Increase";
                        document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
                        success = false;
                    }
                    if(reasonCodeId == "-2" && (reasonComment == null || reasonComment == "")){
                        document.getElementById('increaseSPendLimitResponseMessage').innerHTML = "Enter Notes for Spend Limit Increase";
                        document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
                        success = false;
                    }
                    if(reasonCode == "" && (reasonComment == null || reasonComment == "")){
                        document.getElementById('increaseSPendLimitResponseMessage').innerHTML = "Provide a Reason for Spend Limit Increase";
                        document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
                        success = false;
                    }
                    currentPrice = currentLimitPartsAmt+currentLimitLaborAmt;
                    totalAmount = maxLabor+maxParts;
                    if(isNaN(totalAmount) || (totalAmount <= currentPrice)){
                        document.getElementById('increaseSPendLimitResponseMessage').innerHTML = "New maximum price should be greater than current maximum price";
                        document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
                        success = false;
                     }
                     if(totalAmount > 9999999.99){
                        document.getElementById('increaseSPendLimitResponseMessage').innerHTML = "The price entered is invalid";
                        document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
                        success = false;
                     }
                    
                     var maxSpendLimitPerSO  = ${SecurityContext.maxSpendLimitPerSO};
                     if(success){
                         requestSubmitted = true;
                        success = newco.jsutils.isTransactionPriceAboveMaxSpendLimit(maxSpendLimitPerSO,currentPrice,totalAmount);                  
                     } 
                 
                    if(success){
                        var form = $('#frmIncreaseSpendLimit').serialize();
                        $.post('incSpendLimitAction.action',form,function(data) {
                           var passfail = data.getElementsByTagName('pass_fail')[0].childNodes[0].nodeValue;
                            if(passfail==1||passfail=='1'){
                                    var rowIndex = document.getElementById('selectedRowIndex').value;
                                    var spendLimit = data.getElementsByTagName('addtional_02')[0].childNodes[0].nodeValue;
                                    var spendLimitParts = data.getElementsByTagName('addtional_03')[0].childNodes[0].nodeValue;
                                newco.jsutils.updateOrderExpressMenu(_commonSOMgr.widgetId, rowIndex, spendLimit, spendLimitParts);
                                jQuery.modal.impl.close(true);
                            }else if(passfail==0||passfail=='0'){
                                var errorMsg = data.getElementsByTagName('message')[0].childNodes[0].nodeValue;
                                document.getElementById('increaseSPendLimitResponseMessage').innerHTML = errorMsg;
                                document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
                            }else{
                                jQuery.modal.impl.close(true);
                            }

                        },"xml");

                    }
                }
              
             });
             
                    $(".priceHistory").hide();
        
        $('.priceHistoryIcon').mouseover(function(e)
            {
            
            var x = e.pageX;
            var y = e.pageY;
            var idVal=this.id;
                $("#"+idVal+"priceHistory").css('top',y-360);
                $("#"+idVal+"priceHistory").css('left',x-400);
                $("#"+idVal+"priceHistory").show();
            });
            
        $('.priceHistoryIcon').mouseout(function(e){
            $(".priceHistory").hide();
        });
        

            
           
     });
    //initially hide the textbox   
    function fnEnable(){
        var reasonCode = document.getElementById('reason_som').value;
        if(reasonCode == "-2"){
            document.getElementById('comment_som').disabled = false;
            document.getElementById('comment_som').value = "";
            document.getElementById('comment_som').style.background = '#FFFFFF';


        }else{
            document.getElementById('comment_som').disabled = true;
            document.getElementById('comment_som').value = "";
            document.getElementById('comment_som').style.background = '#E3E3E3';
        }
    }
    
         
  function findPos(obj,id)
{
var val=obj.value;

var curleft = curtop = 0;
if (obj.offsetParent) {
curleft = obj.offsetLeft;
curtop = obj.offsetTop;
while (obj = obj.offsetParent) {
curleft += obj.offsetLeft;
curtop += obj.offsetTop;
}
}

}
    
        function limitText(limitField,limitNum) {
             if (limitField.value.length > limitNum) {
                limitField.value = limitField.value.substring(0, limitNum);
            }
        }
function findMaxLabor(obj)
      {     
      obj.value = fmtMoney(obj.value);
      fnCalcMaxLabor();
      }
      
      function findMaximumLabor(obj)
      {
      obj.value = fmtMoney(obj.value);
      fnCalcTotalAmt();
      }
      
   function fmtMoney(mnt) 
      {
          mnt -= 0;
          mnt = (Math.round(mnt*100))/100;
          var x = (mnt == Math.floor(mnt)) ? mnt + '.00' 
                    : ( (mnt*10 == Math.floor(mnt*10)) ? 
                             mnt + '0' : mnt);
                             
          if( x > 0 )
            return x;
               return "0.00";
      }    
  function fnCalcMaxLabor(){
        var count= document.getElementById("taskCount").value;
        var taskPrice =0;
        var maxLabor = 0;
        var valid = true;
        var errorCount = 0;
        var prepaidPermitPrice = 0;
        document.getElementById('taskErrorCount').value=0;
        for( var i = 0; i < count; i++ ){
        var taskType = document.getElementById('taskList[' + i + '].taskType').value;
        var sequenceNumber = document.getElementById('taskList[' + i + '].sequenceNumber').value;
                                        var taskPrice =document.getElementById('taskList[' + i + '].finalPrice').value;

                                        if(taskType!=1){
                                                        maxLabor = maxLabor + taskPrice *1;
                                          }
                                        if(taskType==1){
                                               prepaidPermitPrice = prepaidPermitPrice + taskPrice *1;
                                        }
                                        if(taskPrice < 0 || taskPrice == undefined){
                                            document.getElementById('increaseSPendLimitResponseMessage').innerHTML="Enter a valid amount for task";
                                            document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
                                            valid = false;
                                            errorCount++;
                                            if(document.getElementById('taskErrorCount')){
                                            document.getElementById('taskErrorCount').value=errorCount;
                                            }
                                            if(document.getElementById('maxLabor')&& document.getElementById('totalAmt')){
                                            document.getElementById('maxLabor').style.display="none";
                                            document.getElementById('totalAmt').style.display="none";
                                            }   
                                        }
                                        if(errorCount==0){
                                        document.getElementById('increaseSPendLimitResponseMessage').style.display= "none";
                                        valid = true;
                                        if(document.getElementById('maxLabor')&& document.getElementById('totalAmt')){
                                            document.getElementById('maxLabor').style.display="block";
                                            document.getElementById('totalAmt').style.display="block";
                                            }
                                        }
                                        }                                                     
                                        var priceLength=maxLabor.toString().length;
                                        
                                        if(isNaN(maxLabor)){
                                        alert("isNaN");
                                        document.getElementById('increaseSPendLimitResponseMessage').innerHTML = "Enter increase in Maximum Price for Labor amount in decimal form";
                                        document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
                                        valid = false;
                                        }
                                if(newco.jsutils.isExist('maxLabor')){
                                    document.getElementById('maxLabor').innerHTML = formatCurrency(maxLabor).substring(1);
                        }
                      
                        var totalSpendLimit = prepaidPermitPrice + maxLabor;
                        document.getElementById('totalSpendLimitAmt').value = parseFloat(totalSpendLimit);
                        if(valid){
                        fnCalcTotalAmt();
                        }

        }
      
        function fnCalcTotalAmt(){
                var currentLimitLaborAmt = parseFloat(document.getElementById('currentLimitLaborAmt').value);
                var maxLabor = parseFloat(document.getElementById('totalSpendLimitAmt').value);
                var maxParts = parseFloat(document.getElementById('increaseLimitParts').value);
                var valid = true;
                var partsPrice=document.getElementById('increaseLimitParts').value;
                var partsPriceLength=partsPrice.length;
                if(partsPriceLength>10){
                    document.getElementById('increaseSPendLimitResponseMessage').innerHTML="The amount entered exceeds the maximum permitted amount";
                    document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";                                    
                    valid = false;
                    }                
                if(maxParts < 0){
                                document.getElementById('increaseSPendLimitResponseMessage').innerHTML="Enter a valid amount for Materials";
                                document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
                                valid = false;
                                if(document.getElementById('maxLabor')&& document.getElementById('totalAmt')){
                                            document.getElementById('maxLabor').style.display="none";
                                            document.getElementById('totalAmt').style.display="none";           
                            }
                        }
                else if (document.getElementById('taskErrorCount').value==0){
                                document.getElementById('increaseSPendLimitResponseMessage').style.display= "none";
                                valid = true;
                                if(document.getElementById('maxLabor')&& document.getElementById('totalAmt')){
                                            document.getElementById('maxLabor').style.display="block";
                                            document.getElementById('totalAmt').style.display="block";
                            }
                        }
                else if (document.getElementById('taskErrorCount').value>0){
                document.getElementById('increaseSPendLimitResponseMessage').innerHTML="Enter a valid amount for task";
                document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
                valid=false;
                if(existJq('#maxLabor')&&existJq('#totalAmt')){
                                            document.getElementById('maxLabor').style.display="none";
                                            document.getElementById('totalAmt').style.display="none";
                                            }   
                }
                else{
                valid=false;
                }
                if(valid){
                if(maxParts < 0)
                    {
                    document.getElementById('increaseSPendLimitResponseMessage').innerHTML = "Enter increase in Maximum Price for Labor amount in decimal form.";
                    document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
                    }
             
                if(isNaN(maxLabor)){
                var totalAmt = currentLimitLaborAmt + maxParts ;
                 if(newco.jsutils.isExist('maxLabor')){
                    document.getElementById('maxLabor').innerHTML = formatCurrency(currentLimitLaborAmt).substring(1); 
                   }
                 document.getElementById('totalSpendLimitAmt').value = parseFloat(currentLimitLaborAmt);
                }
                else{
                var totalAmt = maxLabor + maxParts ;
                }
                 if(isNaN(totalAmt)){
                     document.getElementById('increaseSPendLimitResponseMessage').innerHTML = "Enter increase in Maximum Price for Parts amount in decimal form";
                     document.getElementById('increaseSPendLimitResponseMessage').style.display= "block";
                   }
                if(newco.jsutils.isExist('totalAmt')){
                        document.getElementById('totalAmt').innerHTML = formatCurrency(totalAmt).substring(1);
                }
                document.getElementById('totalSpendLimitParts').value = parseFloat(maxParts);
                return "true";
                }
        }
</script>
</html>
