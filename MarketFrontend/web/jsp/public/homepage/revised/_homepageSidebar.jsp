<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="hpSidebar">
    <%-- start the promo here --%>
	<%--  c:if test="${PromoDto.promoActive == true}" --%>
	<c:if test="${false}">
      <div id="hpPromo"><a id="promoLink" href="#"> <img src="${staticContextPath}/images/homepage/free-posting-promo.png" alt="Free posting!" /> </a> </div>
    </c:if>
      
     <%-- start video --%>
     <ul>
        <li>
          <div><a href="#" onclick="setModalVideo()" title="" class=""><img src="${staticContextPath}/images/buttons/carters.png" 
          alt="Home Improvement and Repairs" width="321" height="157" /></a></div>
        </li>      
      </ul>  
      <%-- end video --%>
  	  <!-- For displaying Mobile App Banner -->
      <ul>
        <li id="hpMobileBanner" style="padding-top: 10px;">
                  <div><a href="http://mobile.servicelive.com/provider" target="_blank"><img src="${staticContextPath}/images/mobileBanner/banner_MarketFrontend.gif" 
          alt="Mobile Banner" width="321" height="157" /></a></div>
        </li>      
       </ul>  
      <!-- Adding "display:none" to block below testimonials for adding  Mobile App Banner -->  
     <%--<ul>
        <li id="hpTestimonials" style="padding-top: 10px;display :none;">
          <div class="greyBoxBotL hpSidebarBox">
            <div class="greyBoxBotR hpSidebarBox">
              <div class="greyBoxTopL hpSidebarBox" >
                <div class="greyBoxTopR hpSidebarBox" id="hpTestimonialsInside">
                  <!-- <h5>Testimonials</h5> -->
                  <ul>
                    <li>
                      <div>Service live is a great service and we will use it for other projects in our house.</div>
                      <div class="hpTestimonialsAuthor">&mdash; Vladimir D, Chicago, IL </div>
                      <div class="hpTestimonialsStars"><img src="${staticContextPath}/images/common/stars_19.gif" alt="Customer rating of 4.6 stars for Mike D." /></div>
                    </li>
                    <li>
                      <div>Nice job done. Will call for other jobs. Will recommend to others. </div>
                      <div class="hpTestimonialsAuthor">&mdash; Michael A, Algonquin, IL</div>
                      <div class="hpTestimonialsStars"><img src="${staticContextPath}/images/common/stars_20.gif" alt="Customer rating of 4.6 stars for Mike D." /></div>
                    </li>
                    <li>
                      <div>Outstanding service. He was fast, professional, helpful, and explained everything so that I could understand it clearly.  I <c:out escapeXml="false" value="couldn't"></c:out> be happier!</div>
                      <div class="hpTestimonialsAuthor">&mdash; Sheila P. Rosemont, IL</div>
                      <div class="hpTestimonialsStars"><img src="${staticContextPath}/images/common/stars_20.gif" alt="Customer rating of 4.6 stars for Mike D." /></div>
                    </li>
                    <li>
                      <div>Allen B. was an amazing provider with his attention to detail. Thank you for all of your support! </div>
                      <div class="hpTestimonialsAuthor">&mdash; Arlington, TX </div>
                      <div class="hpTestimonialsStars"><img src="${staticContextPath}/images/common/stars_20.gif" alt="Customer rating of 4.6 stars for Mike D." /></div>
                    </li>
                    <li>
                      <div>Karl was great! Very nice, and helpful. A pleasure to work with. We will definitely work with him... Read More again.</div>
                      <div class="hpTestimonialsAuthor">&mdash; Erika K, Chicago, IL </div>
                      <div class="hpTestimonialsStars"><img src="${staticContextPath}/images/common/stars_20.gif" alt="Customer rating of 4.6 stars for Mike D." /></div>
                    </li>
                    <li>
                      <div>Very nice and thorough job. Is a professional.</div>
                      <div class="hpTestimonialsAuthor">&mdash; Will D, Atlanta, GA </div>
                      <div class="hpTestimonialsStars"><img src="${staticContextPath}/images/common/stars_20.gif" alt="Customer rating of 4.6 stars for Mike D." /></div>
                    </li>
                  </ul>
                  <div class="clearfix"></div>
                </div>
              </div>
            </div>
          </div>
          </li>
       </ul>  --%>
       
       <ul>
        <li id="hpJoinServiceProvider">
          <div id="hpJoinServiceProviderInside">
            <h5><a href="joinNowAction.action" title="Service Providers Join Now!" style="color:#000;">Are you a Service Provider?</a></h5>
            <div class="hpJoinServiceProviderContent">Handymen, plumbers, information 
              technologists, and other home services professionals...<br />
              <strong><a href="joinNowAction.action" title="Service Providers Join Now!">Grow your business!</a></strong></div>
            <div class="hpJoinServiceProviderContent">
            
            <a href="providerRegistrationAction.action" title="Service Providers Join Now!">
            <img src="${staticContextPath}/images/homepage/acquity/providersJoinNow.gif" alt="Get more work" />
            </a>
            </div>
          </div>
        </li>
        <li id="hpAboutServiceProviders">
          <div class="greyBoxBotL hpSidebarBox">
            <div class="greyBoxBotR hpSidebarBox">
              <div class="greyBoxHeaderTopL hpSidebarBox">
                <div class="greyBoxHeaderTopR"  id="hpAboutServiceProvidersInside">
                  <h5><a href="${contextPath}/jsp/public/what_is_servicelive/body/aboutServiceProviders.jsp" title="About our Service Providers" style="color:#000;">About our Service Providers</a></h5>
                      <div style="width:100%; height:50px; margin-top:20px; border-bottom: solid 1px #D1D1D1; padding-bottom:10px;">
                      <img src="${staticContextPath}/images/homepage/acquity/BGcheckSilhouette.gif" 
                      		alt="Prescreened Service Providers" style="float:left; margin-right:10px;" />
                      <p>All service providers have passed  criminal, civil and vehicle 
                        background checks.</p></div>
                    <ul style="background:none;">
                    <li id="hpAverageRating">
                      <div>94.7% Customer Satisfaction Rate</div>
                      <div><img src="${staticContextPath}/images/homepage/acquity/aveRatingStars_19.gif" alt="Average rating" /></div>
                    </li>
                    <li id="hpAboutNumOfProviders">
                      <div>35,000+ Providers</div>
                      <div><img src="${staticContextPath}/images/homepage/acquity/NumOfProvidersIcons.gif" alt="Average rating" /></div>
                    </li>
                  </ul>
                  <div class="clearfix" style="clear:both;"></div>
                </div>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </div>