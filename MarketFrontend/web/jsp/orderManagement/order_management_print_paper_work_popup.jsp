<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<head>				
	<script type="text/javascript" >
		$(document).ready(function() {
			$('#viewCustomerCopy').prop('checked', true); 
			$('#viewProviderCopy').prop('checked', true); 
	   		 $('#printPaperworkMessage').hide();
	       	 //$('#printPaperworkDropDown').hide();
        	 //$('#printPaperworkCountMessage').hide();
		});
		$('#selectSoOption').click(function() {
				$("#selectSoOptions").show();
			});
		function onSelectSos(obj){
			var startValue = ($(obj).attr("data-value"));
			var text = $(obj).text();
			$('#start_s').prop('value',startValue);
			
			var checkedSoIds=[];
			var soIds = $('#checkedSos').prop("value");
			//checkedSoIds = $(checkedSos).val();
			checkedSoIds = soIds.split(",");
			var length = checkedSoIds.length;
			checkedSoIds = checkedSoIds.slice(startValue,((((startValue+20)>length))? length:startValue+20));
			$("#splitSos").prop('value',checkedSoIds);
			$("#defaultSoOption").html(text);
			$("#selectSoOptions").hide();

			
		}
		
		//validation and submission for VIEW PDF
		function viewAsPDF(){
			 var checkedOptions=[];
			 $("#printPaperWorkDiv").find("input:checked").each(function() {
				 checkedOptions.push(this.id);
		        });
			var checkedSoIds=[];
			var splitSos = [];
			/* actual code
			 var checkedSoId;
		       $('.selectPdf:checked').each(function() {
		        	checkedSoId = this.id;
		        	checkedSoId = checkedSoId.substring(10);
		        	 checkedSoIds= checkedSoIds+","+checkedSoId;
		       }); */
		       
		    //for testing
			var soIds;
		    soIds  = $('#checkedSos').prop("value");
			//checkedSoIds = $(checkedSos).val();
			checkedSoIds = soIds.split(",");	    
			var maxCount=0;
		    maxCount= '<fmt:message bundle="${serviceliveCopyBundle}" key="ordermanagement.so.printpaperwork.Print.count" />';
		    
		    if(checkedSoIds.length > maxCount){
				soIds = $('#splitSos').prop("value");
				splitSos = soIds.split(",");	

			}
			else{
				splitSos = checkedSoIds;
			}
			//checkedSoIds = $(checkedSos).val();)
			
	        var  msg;
	        var countEnd = $('#start_s').prop("value");
	        
	        if((checkedSoIds.length>maxCount)&& ((countEnd ==null) || (countEnd =="")|| (countEnd == -1))){
					msg=  '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.printpaperwork.Print.selection" />';

			 		$('#printPaperworkMessage').html(msg);
	        	 $('#printPaperworkMessage').show();
			}
	        else if(checkedOptions.length == 0 || checkedOptions == ""){
				msg= '<fmt:message bundle="${serviceliveCopyBundle}" key="error.ordermanagement.so.printpaperwork.Print.message" />';
	        	 
			 	$('#printPaperworkMessage').html(msg);
				$('#printPaperworkMessage').show();
			 }
			
			else{
	 			$('#printPaperworkMessage').hide();
				var url =  "/MarketFrontend/viewAsPDF.action?checkedSoIds="+splitSos+"&checkedOptions="+checkedOptions;
				newwindow=window.open(url,'_blank','width=600,height=450,resizable=1,scrollbar=1');
				if (window.focus) {
					newwindow.focus();
					}
		 		
	 		}
	 		
				return false;
				}
	</script>	
</head>
<body>
	<div id="printPaperWork" style="width: 300px; float: left;">
		<div id="modalTitleBlack" style="background:#58585A; border-bottom: 2px solid black; color:#FFFFFF ; text-align:left;height:25px;padding-left: 8px;padding-top: 5px;font-size: 18px;" 
			class="assignProHdr">Print Paper work		
		</div>
		<div style="float: right;padding:5px;color: #CCCCCC;">
			<i class="icon-remove-circle" style=" font-size: 20px;position: absolute; right: 5px;cursor: pointer;top: 5px;"  onclick="closeModal('printPaperWorkDiv');"></i>
		</div>
        <p style="color:#000000;text-align: left;padding-left: 5px;padding-right: 5px;">
		<i><fmt:message bundle="${serviceliveCopyBundle}" key="ordermanagement.so.printpaperwork.Print.message"/></i>
		</p>
        <div class="clearfix" id="printPaperworkMessage"
                     style="width: 300px; overflow-y: hidden; display: none;color: red;padding-left: 5px;padding-right: 5px;" >
        </div>
       <br/>
        <div style="padding-left: 10px;color:#000000;text-align: left;padding-left: 15px;padding-right: 5px;">
 			<input type="checkbox" name="viewCustomerCopy" value="0" id="viewCustomerCopy" /> <b><fmt:message bundle="${serviceliveCopyBundle}" key="ordermanagement.so.printpaperwork.Print.customercopy"/></b><br />
 			<input type="checkbox" name="viewCustomerCopy" value="1" id="viewProviderCopy" /> <b><fmt:message bundle="${serviceliveCopyBundle}" key="ordermanagement.so.printpaperwork.Print.providercopy"/></b><br />
          </div>
          <br />
         <div class="clearfix" id="printPaperworkCountMessage"
                     style="width: 300px; overflow-y: hidden; display: none;color: red;padding-left: 5px;padding-right: 5px;" >
        </div>
        <br>
        
         <div id="printPaperworkDropDown" style="display: none;">
         <input type = "hidden" name = "start_s" id = "start_s" value = ""></input>
         <input type = "hidden" name = "splitSos" id = "splitSos" value = ""></input>
          <input type = "hidden" name = "checkedSos" id = "checkedSos" value = ""></input>

         
         <div id="selectSoOption" class="picked pickedClick"
								style="width:34%; float: left; margin-left: 20px">
								<label id="defaultSoOption"> -Select- </label>
		</div>
        <div class="select-list select-options" id="selectSoOptions"
								style="display: none; width:37%; float: left; margin-left: 20px; margin-top:0px;">
									<ul id="selectSo" style="overflow:scroll;overflow-x: hidden;overflow-y: scroll; width:100%;">
									<c:forEach items="${soOptions}" var="soOption">
									<li onclick="onSelectSos(this)" data-value="${soOption.value}" id="selectSo_${soOption.value}">${soOption.desc}</li>
									</c:forEach>
									</ul>
							</div>
         </div>
         <br />
		<div style="text-align: center;padding-left:90px;" id="viewPDFs">
			<input type="button" value="View as PDF" id="viewAsPDF" class="actionButton" onclick="viewAsPDF();"/><br/>
		</div>
		</div>
</body>