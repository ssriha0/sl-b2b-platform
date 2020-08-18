<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="staticContextPath" value="/ServiceLiveWebUtil" />

<c:if test="${provider}">

<div id="dashboardNews">
<div id="dashboardNewsTitleBar">News From ServiceLive</div>
<div id="dashboardNewsBody"><img src="${staticContextPath}/images/icons/dashboardNewsIcon.gif" id="dashboardNewsIcon"></img>
We have launched our new Web site, which enables home and small-business owners to post service orders on our platform. What does this mean to you?<br/>
- You now have the ability to communicate with those buyers before accepting an order, and you may even bid on orders!<br/>
- You will see some changes in your Service Order Monitor and Service Order Notes on orders that are not from commercial buyers. 
<a href="http://community.servicelive.com">Read more ...</a>
</div>

</div>



</c:if>