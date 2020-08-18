<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Assigned Provider</h2>
<div class="media">
  <a class="pull-left" href="#" onclick="openProviderProfile('${lmTabDTO.lead.resourceAssigned}','${lmTabDTO.lead.vendorId}');">
  				<script>function loadDefaultPixel(img){
					img.src = '/ServiceLiveWebUtil/images/dynamic/Person_NoPhotoAvailable.png';
				}
  			  //fn to open provider profile ina new window
  				function openProviderProfile(resouceId,vendorId){
  						var url = "/MarketFrontend/providerProfileInfoAction_execute.action?resourceId="
  								+ resouceId + "&companyId=" + vendorId + "&popup=true";
  						newwindow = window
  								.open(url, '_publicproviderprofile',
  										'resizable=yes,scrollbars=yes,status=no,height=700,width=1000');
  					if (window.focus) {
  						newwindow.focus();
  						}
  					return false;
  					
  				}
  			  </script>
<img class="media-object profile-img thm"   onerror="loadDefaultPixel(this);"

 src="${contextPath}/providerProfileInfoAction_displayPhoto.action?max_width=1000&max_height=200&resourceId=${lmTabDTO.lead.resourceAssigned}" >
</a>

  <div class="media-body">
    <ul class="no-bullets">
      <li>${lmTabDTO.lead.resFirstName}&nbsp;${lmTabDTO.lead.resLastName} <a href="#" data-toggle="modal" data-target="#assignWidget">Reassign</a></li>
      <li class="small">User ID #${lmTabDTO.lead.resourceAssigned}</li>
      <li>
      
					<c:if test="${lmTabDTO.lead.rating == 0}">
						<img src="${staticContextPath}/images/common/stars_notRated.gif"
							border="0" />
					</c:if>
					<c:if test="${lmTabDTO.lead.rating != 0}">
						<img
							src="${staticContextPath}/images/common/stars_${lmTabDTO.lead.rating}.gif"
							border="0" />
					</c:if>
				
			</li>
  </div>
</div>