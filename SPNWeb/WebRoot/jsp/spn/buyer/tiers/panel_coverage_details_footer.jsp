<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>	
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<head>
</head>

<body>
	<br/>
	 <c:if test="${savedRoutingDTO.spnHdr.criteriaLevel == 'PROVIDER'}">
       Showing <span id="memberCnt">${savedRoutingDTO.providerCount}</span> total Providers
                <c:if test="${1 == savedRoutingDTO.disclaimer}">
                	<br><br><i style="font-size:11px">* No performance data is available for the Provider at this point of time</i>
                </c:if>
    </c:if>                   
       
    <c:if test="${savedRoutingDTO.spnHdr.criteriaLevel == 'FIRM'}">
        Showing <span id="memberCnt">${savedRoutingDTO.firmCount}</span> total Firms
                    (<span id="totalProv">${savedRoutingDTO.providerCount}</span> total Providers).
                <c:if test="${1 == savedRoutingDTO.disclaimer}">
                    <br><br><i style="font-size:11px">* No performance data is available for the Firm at this point of time</i>
                </c:if>
    </c:if>   
  
</body>