DELIMITER $$

update template set template_source ='<html>
<head>
<style type="text/css">
	#email_body {
		font-family: Arial;
		color: #505050;
		font-size: 8.5pt;
		font-weight: normal;
		font-style: normal;
		text-align: left;
		background-color: #FFFFFF;
	}
</style>
</head>
<body>
<img src="cid:emailLogo"><br/><br/>
<div id="email_body">
<strong><b>Hello ${PrimeContact},</b></strong>
<br/><br/><strong>Thank you for completing your application on ServiceLive.com</strong> <br/><br/>

We appreciate the time you spent to provide us with your business and team member information. We have begun our review of your application and have found that the following company item(s) require action on your part before your company will be eligible to receive service orders from ServiceLive.<br/><br/><br/>

Credential Type:   	&nbsp ${auditEmailVO.credentialType}<br/>
Name of Credential:   	&nbsp ${auditEmailVO.credentialName}<br/>
Credential Number:   	&nbsp ${auditEmailVO.credentialNumber}<br/>
Status:		    	&nbsp ${auditEmailVO.targetStateName}<br/><br/><br/>

Reason Explanation:<br/>
${auditEmailVO.reasonDescription}<br/><br/>
                   
If you have any questions about the outstanding items or about becoming a ServiceLive Service Provider, please contact us at:<br/><br/><br/>


Email: contact@servicelive.com<br/>
ServiceLive, Inc <br/>
1560 Cable Ranch Rd <br/>
Building A <br/> 
San Antonio, TX78245 <br/><br/>

Thank you! We look forward to doing business with you.<br/>
</div>
</body>
</html>' where template_id=2$$
$$
DELIMITER ;

DELIMITER $$
update template set template_source ='<html>
<head>
<style type="text/css">
            #email_body {
                        font-family:Arial;
                        color: #505050;
                        font-size: 8.5pt;
                        font-weight: normal;
                        font-style: normal;
                        text-align: left;
                        background-color: #FFFFFF;
            }



a:link

          {color:blue;
          text-decoration:underline;
          text-underline:single;}
</style>
</head>



<body>
<img src="cid:emailLogo"><br/><br/>
<div id="email_body">


<strong><b>Congratulations ${PrimeContact},</b></strong>
<br/><br/>

<p>Thank you for entering the <b>${auditEmailVO.credentialName}</b>
licenses or certifications information with ServiceLive.&nbsp;
<b>${PrimeContact}</b>  will now show a <b>"Verified"</b> ServiceLive logo within their profile &nbsp; <img src="cid:verifiedLogo"> </p>

<p>Provided your Firm is ServiceLive Approved this license or certification will be visible to all buyers 
in the market place.  These credentials will allow you to accept orders on projects that 
require licenses and certifications.  They also prove as a great differentiator within the 
ServiceLive platform to distinguish you from the competition and demonstrate why your Firm is 
the most capable to do the job.</p>

<p>Should you have any questions about the Sears Provider Network please feel free to contact Sears ServiceLive Recruitment at (800) 497-4402.</p>

<p>If you need an immediate help call the ServiceLive Support Team at (888) 549-0640, weekdays between 7:00 AM and 7:00 PM Central Standard Time or if you would like to attend one of our online facilitated training sessions please click the following link to see the latest schedule:</p>

<p><a href="http://community.servicelive.com/docs/webcast.htm">
http://community.servicelive.com/docs/webcast.htm</a></p>



<p>Regards</p>
ServiceLive, Inc <br/>
1560 Cable Ranch Rd <br/>
Building A <br/> 
San Antonio, TX78245 <br/><br/>
</div>

</body>
</html>' where template_id=232;
$$
DELIMITER ;

DELIMITER $$
update template set template_source ='<html>
<head>
<style type="text/css">
            #email_body {
                        font-family:Arial;
                        color: #505050;
                        font-size: 8.5pt;
                        font-weight: normal;
                        font-style: normal;
                        text-align: left;
                        background-color: #FFFFFF;
            }
a:link
          {color:blue;
          text-decoration:underline;
          text-underline:single;} 
</style>
</head>
<body>
<img src="cid:emailLogo"><br/><br/>
<div id="email_body">
<strong><b>Congratulations ${PrimeContact},</b></strong>
<br/><br/>
<p>Thank you for entering the ${auditEmailVO.credentialName}
licenses or certifications information within ServiceLive.
Your companys credentials are now <b>Reviewed</b> and will show up in your profile.  </p>
<p>You will need to upload the actual license, certification,
or identification card to receive a <b>Reviewed</b> ServiceLive logo within your companys profile. </p>
<p>Provided your Firm is ServiceLive Approved this license or certification will be visible
to all buyers in the market place, however it will not post a <b>Verified</b> Logo beside the description.
These credentials will allow you to accept orders on projects that require licenses and certifications,
although some buyers may request <b>verified</b> documents.
To ensure you have maximum service acceptance we recommend you add proof of the credential to allow it to be verified.
Adding the proof of documentation to the site gives the buyer a greater comfort level and acts as a great differentiator within the
ServiceLive platform to distinguish you from the competition and demonstrate why your Firm is the most capable to do the job.</p>
<p>Should you have any questions about the Sears Provider Network please feel free to contact Sears ServiceLive Recruitment at (800) 497-4402.</p>
<p>If you need an immediate help call the ServiceLive Support Team at (888) 549-0640, weekdays between 7:00 AM and 7:00 PM Central Standard Time or if you would like to attend one of our online facilitated training sessions please click the following link to see the latest schedule:</p>
<p><a href="http://community.servicelive.com/docs/webcast.htm">
http://community.servicelive.com/docs/webcast.htm</a></p>
<p>Regards</p>
ServiceLive, Inc <br/>
1560 Cable Ranch Rd <br/>
Building A <br/> 
San Antonio, TX78245 <br/><br/>
</div>
</body>
</html>' where  template_id=234
$$
DELIMITER ;
