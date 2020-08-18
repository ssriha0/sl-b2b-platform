<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

 <div  class="jqmWindow" id="modalUserCategory">
      <div class="modalHomepage"> <a href="#" class="jqmClose">Close</a> </div>
      <form  action="homepage_submitZip.action" method="post">
        <div class="modalContent">
          <h2>Please enter the Zip Code of the location where work will be done.</h2>
          <input type="text" name="zipCode" value="" id="zipCode" maxlength="5"/><br /><br /><br />
           <fieldset> 
         <!--  <label>
              <input name="UserType" type="radio" value="HomeOffice" onChange="setBuyerType(1);"/>
              <span id="userTypeHomeOffice">Home/Office</span> <img src="${staticContextPath}/images/homepage/acquity/modalIconHomeOffice.gif" alt="Home/Office" /><br />
              <span class="TypeDescription">For homeowners and small business owners</span> </label>
            <label>
         -->
              <input name="UserType" type="radio" value="Commercial" checked="checked" id="UserTypeCommercial" onChange="setBuyerType(2);"/>
              <span id="userTypeCommercial">Commercial</span> <img src="${staticContextPath}/images/homepage/acquity/modalIconCommercial.gif" alt="Commercial" /><br />
              <span class="TypeDescription">For larger business owners who have <strong>ongoing projects</strong> or manage <strong>multiple projects</strong> at once.</span> </label>
            <input type="image" alt="Submit" src="${staticContextPath}/images/simple/button-find-sp.png" id="input" value="Submit" class="findProv"/>
          </fieldset>
         		
         	<input id="pop_name" type="hidden" name="popularSimpleServices[0].name" value="<s:property value='name'/>" />
			<input id="mainCategoryId" type="hidden" name="popularSimpleServices[0].mainCategoryId" value="<s:property value='mainCategoryId'/>" />
			<input id="categoryId" type="hidden" name="popularSimpleServices[0].categoryId" value="<s:property value='categoryId'/>" />
			<input id="subCategoryId" type="hidden" name="popularSimpleServices[0].subCategoryId" value="<s:property value='sategoryId'/>" />
			<input id="serviceTypeTemplateId" type="hidden" name="popularSimpleServices[0].serviceTypeTemplateId" value="<s:property value='serviceTypeTemplateId'/>" />
			<input id="buyerId" type="hidden" name="popularSimpleServices[0].buyerTypeId" value="<s:property value='buyerTypeId'/>" />
	    </div>
      </form>
	</div>
	<div  class="jqmWindow" id="modalUserTypeChoose">
      <div class="modalHomepage"> <a href="#" class="jqmClose">Close</a> </div>
      <form onSubmit="return true;" action="homepage_submitZip.action" method="post" name="form2" id="form2">
			<div class="modalContent">
				<h2>What is your project for?</h2>
						  <fieldset>
							<!-- <label>
								<input name="UserType" type="radio" value="HomeOffice"  onChange="setBuyerType(1);"/>
								<span id="userTypeHomeOffice">Home/Office</span>
								<img src="${staticContextPath}/images/homepage/acquity/modalIconHomeOffice.gif" alt="Home/Office" /><br />
								<span class="TypeDescription">For homeowners and small business owners</span> 
							</label>
							 -->
							<label>
								<input name="UserType" type="radio" value="Commercial" checked="checked" id="UserTypeCommercial" onChange="setBuyerType(2);"/>
								<span id="userTypeCommercial">Commercial</span>
								<img src="${staticContextPath}/images/homepage/acquity/modalIconCommercial.gif" alt="Commercial" /><br />
								<span class="TypeDescription">For larger business owners who have <strong>ongoing projects</strong> 
								or manage <strong>multiple projects</strong> at once.</span>
							</label>
							<input type="image" alt="Submit" src="${staticContextPath}/images/simple/button-find-sp.png" id="" value="Submit" class="findProv"/>
					</fieldset>
					 
					<input id="pop_name" type="hidden" name="popularSimpleServices[0].name" value="<s:property value='name'/>" />
					<input id="mainCategoryId" type="hidden" name="popularSimpleServices[0].mainCategoryId" value="<s:property value='mainCategoryId'/>" />
					<input id="categoryId" type="hidden" name="popularSimpleServices[0].categoryId" value="<s:property value='categoryId'/>" />
					<input id="subCategoryId" type="hidden" name="popularSimpleServices[0].subCategoryId" value="<s:property value='sategoryId'/>" />
					<input id="serviceTypeTemplateId" type="hidden" name="popularSimpleServices[0].serviceTypeTemplateId" value="<s:property value='serviceTypeTemplateId'/>" />
					<input id="buyerId" type="hidden" name="popularSimpleServices[0].buyerTypeId" value="1" />
					<input type="hidden" name="zipCode" id=zipCode" />
				</div>
			</form>
    </div>

    <div  class="jqmWindow" id="modalUserZipTypeChoose">
      <div class="modalHomepage" id="modalCategory"> <a href="#" class="jqmClose">Close</a> </div>
    </div>

    <div  class="jqmWindow modalDefineTerms" id="modalDefineTermsCandidates">
      <div class="modalHomepage"> <a href="#" class="jqmClose">Close</a> </div>
      <div class="modalContent">
        <h2>&ldquo;Candidates&rdquo;</h2>
        <p>From a complete list of independent, ServiceLive-Approved service providers in your area, you will be able to select the service providers you would like to work on your project. <strong>These are your candidates.</strong> </p>
        <p>Select candidates based on: location, required skills, languages spoken, and ratings.  Also, view provider profiles and customer comments.</p>
        <p>To maintain the integrity of our ratings, only customers who have hired providers through ServiceLive are allowed to post ratings and comments.  Therefore, new providers to ServiceLive will not have any ratings.</p>
        <p><strong>Your service order will be routed only to the candidates you select.</strong> The candidate who accepts the project first, at the price and time you named, wins the project.</p>
        <p>Remember, new service providers are joining every day, so if you don't see service providers with the skill you are looking for in your area, be sure to check back soon!</p>
        <img src="${staticContextPath}/images/simple/button-close2.png" alt="Close" align="right" class="jqmClose"/><br /><br />
      </div>
    </div>

    <div  class="jqmWindow modalDefineTerms" id="modalDefinePreScreened">
      <div class="modalHomepage"> <a href="#" class="jqmClose">Close</a> </div>
      <div class="modalContent">
        <h2>Pre-screened</h2>
        <p>Service providers on ServiceLive.com have passed the same criminal, civil and vehicle background checks required by contractors of Fortune 500 companies. </p>
       <p>ServiceLive conducts these background checks on each and every ServiceLive-Approved provider you can hire through the site.  In addition, provider firms submit company insurance and background information, plus skills, credentials and profiles for each provider.</p>
        <img src="${staticContextPath}/images/simple/button-close2.png" alt="Close" align="right" class="jqmClose"/><br /><br />
      </div>
    </div>
    
   <div  class="jqmWindow modalDefineTerms" id="modalDefineTermsFundPay">
      <div class="modalHomepage"> <a href="#" class="jqmClose">Close</a> </div>
      <div class="modalContent">
        <h2>&ldquo;Fund&rdquo; and &ldquo;Pay&rdquo;</h2>
          <h3>Fund</h3>
        <p>After you determine the price you would like to pay, you will be asked to <strong>fund your account before the service order is routed</strong>  to the providers you select (your candidates).</p>
        <p>Although your credit card or bank account will be charged, <strong>you maintain control of the funds</strong> in your account.</p>
        <h3>Pay</h3>
        <p>You <strong>only pay the service provider when you are satisfied</strong>.</p>
        <p>At that point you will log back in to ServiceLive.com and <strong>&ldquo;release&rdquo; the money to the service provider</strong>.</p>
        <p><i>This protects you AND the service provider. You know your project will get done to your satisfaction. The service providers know they will get paid.</i></p>
        <small><i>*This payment service is powered by Integrated Payment Systems, Inc., which is licensed as a Money Transmitter by the Banking Department of the State of New York.</i></small>
        <img src="${staticContextPath}/images/simple/button-close2.png" alt="Close" align="right" class="jqmClose"/><br /><br />
      </div>
    </div>

 	<div  class="jqmWindow modalDefineTerms" id="modalVideo" style="background-color:#000000;width:520px;">
      <div class="modalHomepage"> <a href="#" class="jqmClose">Close</a> </div>
      <div class="modalContent" style="padding-left: 20px;">
        <object width="480" height="295"><param name="movie" value="http://www.youtube.com/v/q3T_-gaV_j8&hl=en&fs=1&rel=0"></param><param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param>
        <embed src="http://www.youtube.com/v/q3T_-gaV_j8&hl=en&fs=1&rel=0" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" 
        width="480" height="295"></embed>
        </object>
      </div>
	</div>

<div id="serviceFinder" class="jqmWindow"></div>

<div id="modal123" class="jqmWindowSteps"></div>

<div id="jqmWindowChooseType" class="jqmWindowChooseType"></div>

<div class="jqmWindowThin" id="promoTerms">
  <div class="modalHomepage"><a href="#" class="jqmClose">Close</a></div>
  <div class="modalContent">
    <center>
      <img src="${staticContextPath}/images/homepage/freePosting.png" style="margin: 10px;"/>
    </center>
    <p>Our usually low-price of $10.00 per service order Posting Fee is waived for all service orders posted between 7/22/08 thru 2/28/09 for all buyer accounts.</p>
  </div>
</div>