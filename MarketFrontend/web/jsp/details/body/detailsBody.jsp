<%@page import="com.newco.marketplace.web.dto.ServiceOrderDTO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%-- [Back to top] button links to line below --%>
<a name="details_top"></a>

 <script type="text/javascript">
 
    function modalOpenAddCustomer(dialog) {


        dialog.overlay.fadeIn('fast', function() {

            dialog.container.fadeIn('fast', function() {

                dialog.data.hide().slideDown('slow');

            });
        });
     
    }
   
   function modalOnClose(dialog) {
       dialog.data.fadeOut('slow', function() {
           dialog.container.slideUp('slow', function() {
               dialog.overlay.fadeOut('slow', function() {
                   $.modal.close(); 
               });
           });
       });
    } 
   
   function toTop(x_coord,y_coord)
	{
		window.scrollTo(x_coord, y_coord);
	}
                                   
</script>
<%
String hiddenVar=request.getParameter("hiddenInd");
request.setAttribute("tab",hiddenVar);

%>

<script type="text/javascript">
function getCookie(cookie_name)
{
	var i,x,y,ARRcookies=document.cookie.split(";");
	for (i=0;i<ARRcookies.length;i++)
	{
		x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
		y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
		x=x.replace(/^\s+|\s+$/g,"");
		if (x==cookie_name)
		{
			return unescape(y);
		}
	}
}

	var $ta;
	jQuery(document).ready(function($){  
		document.getElementById('rescheduleErrorMessage').style.display="block"; 
		var estimate=document.getElementById('estimateDetails').value;
		var defaultTab = '<%=request.getParameter("defaultTab")%>';

		var index = 0;
		var i=0;
		jQuery('#tabs ul li a').tabs().each(function() {
				if(jQuery.trim(jQuery(this).text()) == defaultTab) {
					index=i;
			    	return i; 
			    }
			    i++;
			});
			
		jQuery('#tabs').tabs({ cache: true});
		if(estimate === "cameFromOrderManagementui-tabs-5"){
			$ta = jQuery('#tabs').tabs(document.getElementById('estimateTab').click());  
			jQuery(" #summaryTab1 em").text("Summary");
			jQuery(" #summaryTab2 em").text("Summary");
		}else{
			$ta =	jQuery('#tabs').tabs( "option", "active", index );
			jQuery('.ui-tabs-selected').removeClass('ui-tabs-selected');
		}

		jQuery('#order').click(function() { 
			var id=document.getElementById("Notes").value;
			var numId=0;
			numId=parseInt(id);
    		$ta.tabs("select",4); 
			return false;
		});
		
   });
    
    var displayDiv = null;
	setCookie('Selectedsubtab','Summary',1);  
	
    function setCookie(c_name,value,expiredays){
    	//Deleting existing cookie to avoid any duplicates.
    	delete_cookie(c_name);
		var exdate=new Date();
		exdate.setDate(exdate.getDate()+expiredays);
		document.cookie=c_name+ "=" +escape(value)+
		((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
	}
    
    function delete_cookie (cookie_name) {
        var cookie_date = new Date ( );  // current date & time
        cookie_date.setTime ( cookie_date.getTime() - 1 );
        document.cookie = cookie_name += "=; expires=" + cookie_date.toGMTString();
    }
	
	

	function getTabForId(parameterId) { 
		if(parameterId.length >= 7 && parameterId.substring(0,7)=='Summary'){
			var soId = parameterId.substring(17,34);
			displayDiv   = parameterId.substring(7,parameterId.length);
			parameterId  = 'Summary';
			setCookie('Selectedsubtab','Summary',1);
			
		}else{
			setCookie('Selectedsubtab','',1);
			
		}	
		
		var id=document.getElementById(parameterId).value;
		var numId=0;
		numId=parseInt(id);
		$ta.tabs( "option", "active", numId );
 		return false;
	}
	
	function goToDocumentsAndPhotos(){
	var hiddenTabVar = jQuery("#hiddenTabInd").val();
	if(hiddenTabVar == "Doc"){
		jQuery("#hiddenTabInd").val("");
	moveToDocuments('${SERVICE_ORDER_ID}');
		}
		
    }

	
	function goToParts(){
		var PartIndVar = jQuery("#PartInd").val();
		if(PartIndVar == "Part"){
			jQuery("#PartInd").val("");
		moveToPart('${SERVICE_ORDER_ID}');
			}
			
	    }
	
	
	
	
	
	function checkProviderPanel(){
		var providerPanelInd = jQuery("#providerPanelInd").val();
		if(providerPanelInd == "provider"){
			jQuery("#providerPanelInd").val("");
			moveToProviderPanelDiv();
			}
			
	    }

      
	function goToDocumentSection(soId){
		
		var selected=$ta.tabs('option', 'selected');
		// 0 is the summary tab
		if("0" == selected){
			moveToDocuments('${SERVICE_ORDER_ID}');
			return;
		}else{
			
			jQuery("#hiddenTabInd").val("Doc");
						
		}
		$ta.tabs( "option", "active", 0 );	
 		return false;
	}
	
	
	
	
	
      function moveToProviderPanel(){
		var selected=$ta.tabs('option', 'active');
		// 0 is the summary tab
		
		if("0" == selected){
			moveToProviderPanelDiv();
			return;
		}else{
			
			jQuery("#providerPanelInd").val("provider");
						
		}
		$ta.tabs( "option", "active", 0 );
 		return false;
	}
	
	
	
	function setDocIndicator()
	{
		jQuery("#hiddenTabInd").val("");
		
		jQuery("#providerPanelInd").val("");
		window.location.hash='';

	
	}
	
	function moveToDocuments(soId){
		
		var urlVal=location.href;     		
		var ind= urlVal.indexOf("#");     	
		
		if(ind!=-1){     				
			urlVal = urlVal.substring(0,ind);     		 
 		location.href = encodeURI(urlVal+"#documents"+soId); 					
	 
		}else{     		
			encodeURI(location.href += "#documents"+soId);	
		}
		
	 }
	
	
function moveToPart(soId){
		
		var urlVal=location.href;     		
		var ind= urlVal.indexOf("#");     	
		
		if(ind!=-1){     				
			urlVal = urlVal.substring(0,ind);     		 
 		location.href = encodeURI(urlVal+"#parts"+soId); 					
	 
		}else{     		
			encodeURI(location.href += "#parts"+soId);	
		}
		
	 }
	
	
	
    jQuery('#tabs').tabs({
        load: function(e, ui) {
        jQuery('a.link', ui.panel).click(
            function() {
				jQuery('#tabs a').addClass('usual');
                jQuery(ui.panel).load(this.href);
                return false;
             });
        }
     });

      
      </script>
	<div id="rescheduleErrorMessage" style="color: red;display: none" >
	 ${rescheduleMsg}	 
	</div>
     <% int count=0; %>  
	
   <div id="tabs" class="widget" style="width: 100%;">
             <ul class="tabnav">
         <c:forEach var="tab" items="${tabList}">
            <input type="hidden" id="${tab.id}" value="<%=count%>"/>
            <input type="hidden" id="estimateDetails" value="<%=request.getAttribute("cameFromOrderManagement")%>"/>
          <li class="${tab.selClass}">
          	<c:choose>
          		<c:when test="${tab.id=='Summary'}">
          			<c:if test="${not empty groupOrderId}">
          				<a onClick="clearBinding();setCookie('Selectedsubtab','Summary',1);setDocIndicator();" id="summaryTab1" 
          					href="${tab.action}&resId=${SOD_ROUTED_RES_ID}" >${tab.id}</a>
          			</c:if>
          			<c:if test="${not empty SERVICE_ORDER_ID}">
          				<a onClick="clearBinding();setCookie('Selectedsubtab','Summary',1);setDocIndicator();" id="summaryTab2"
          					href="${tab.action}?soId=${SERVICE_ORDER_ID}&resId=${SOD_ROUTED_RES_ID}" >${tab.id}</a>
          			</c:if>
          		</c:when>
          		<c:when test="${tab.id=='Support' || tab.id=='Notes'}">
          			<c:if test="${not empty groupOrderId}">
          				<a onClick="clearBinding();setCookie('Selectedsubtab','',1);setDocIndicator();" 
          					href="${tab.action}&groupId=${GROUP_ID}&resId=${SOD_ROUTED_RES_ID}" >${tab.id}</a>
          			</c:if>
          			<c:if test="${not empty SERVICE_ORDER_ID}">
          				<a onClick="clearBinding();setCookie('Selectedsubtab','',1);setDocIndicator();" 
          					href="${tab.action}&soId=${SERVICE_ORDER_ID}&resId=${SOD_ROUTED_RES_ID}" >${tab.id}</a>
          			</c:if>
          		</c:when>
          		
          		<c:when test="${tab.id=='Estimate Details'}">
          				<a onClick="clearBinding();setCookie('Selectedsubtab','',1);setDocIndicator();" id="estimateTab"
          					href="${tab.action}?soId=${SERVICE_ORDER_ID}&resId=${SOD_ROUTED_RES_ID}&estimationId=${tab.estimationId}">${tab.id}</a>
          		</c:when>
          		
          		<c:otherwise>
          			<c:if test="${not empty groupOrderId}">
          				<a onClick="clearBinding();setCookie('Selectedsubtab','',1);setDocIndicator();" 
          					href="${tab.action}&groupId=${GROUP_ID}&resId=${SOD_ROUTED_RES_ID}&status=${THE_SERVICE_ORDER_STATUS_CODE}" >${tab.id}</a>
          			</c:if>
          			<c:if test="${not empty SERVICE_ORDER_ID}">
	          			<a onClick="clearBinding();setCookie('Selectedsubtab','',1);setDocIndicator();"  
	          				href="${tab.action}?soId=${SERVICE_ORDER_ID}&resId=${SOD_ROUTED_RES_ID}&status=${THE_SERVICE_ORDER_STATUS_CODE}" >
	          					${tab.id}</a>
          			</c:if>
          		</c:otherwise>
          	</c:choose>		
          </li>
         <%count=count+1; %>
          </c:forEach>
       </ul>
       
       

            <input type="hidden" id=hiddenTabInd value=" "/>
             <input type="hidden" id=providerPanelInd value=" "/>
            
                        <input type="hidden" id=PartInd value=" "/>
            
     </div>
	 

<script>

<c:if test="${tabList == null}">
var t = setTimeout("refreshSOD()",5000);
</c:if>

function refreshSOD(){
location.reload(true);
}
function clearBinding(){
jQuery(window).unbind('scroll');
}

</script> 