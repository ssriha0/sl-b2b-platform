DELIMITER $$

update template set template_source='<html>
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
Congratulations! <br/><br/>
Thank you for completing the team member information with ServiceLive.<br/>
You now are a ServiceLive <b>&quot;Approved Market Ready&quot;</b> ServicePro.&nbsp; A ServiceLive approved provider will begin to 
receive calls from the<b> ServiceLive Open Marketplace.&nbsp; </b>Consumers within 
your area can now explore your profile and route service based upon skills sets
and credential of this team member.&nbsp;
<p>
We encourage you to add a picture on the ServicePro profile and make sure that all the proper skills and
credentials have been added and updated.&nbsp; By doing this, it allows you to 
differentiate yourself or ServicePro from the competition within your market.</p>
<p>
As you start accepting work for ServiceLive we would like to remind providers of the <b><font color="blue">5 Top Guidelines</font></b> 
that will get you automatically deactivated from ServiceLive:</p>
<ul>
<li><b>Service Orders must be completed only by the ServicePro that accepted the service order.</b></li>
<li><b>All additional services requested by the end user must go through the original ServiceLive Buyer.</b></li>
<li><b>All providers must use the IVR when arriving and leaving the customer&rsquo;s home (See &quot;Provider Playbook&quot; for further details.)</b></li>
<li><b>Poor Customer Satisfaction Rating will apply when providers do not achieve 100% satisfaction rating from the buyer.</b></li>
<li><b>ServicePros can not perform work that they are not licensed to do.</b></li>
</ul>
<br/>
<p>We have also created a &quot;Provider Playbook&quot; for your Firm and ServicePros to better assist you with your ServiceLive profile.</p>
<p><a href="http://community.servicelive.com/docs/ServiceLive_Provider_Playbook.pdf">
<b>http://community.servicelive.com/docs/ServiceLive_Provider_Playbook.pdf</b></a></p><br/>
<p>If you are interested and would like to obtain additional information please email
<a href="mailto:sears_servicelive@searshc.com"><b>sears_servicelive@searshc.com</b></a> or contact Sears ServiceLive 
Recruitment at (800) 497-4402.</p><br/>
<p>If you need an immediate help call the ServiceLive Support Team at (888) 549-0640, weekdays between 7:00 AM and 7:00 PM 
Central Standard Time or if you would like to attend one of our online facilitated training sessions please click the 
following link to see the latest schedule:</p>
<p><a href="http://community.servicelive.com/view.php?pg=training">
<b>http://community.servicelive.com/view.php?pg=training</b></a></p>
<p>Regards,</p><br/>
ServiceLive Team<br/>
3333 Beverly<br/> 
5500 Trillium<br/>
Elm Building<br/>
Hoffman Estates, IL 60192
</body>
</html>' , template_subject='ServicePro – ServiceLive Approved Market Ready!' where template_id=230$$

DELIMITER ;

DELIMITER $$

update template set template_source='<html>
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


Congratulations <strong><b>${auditEmailVO.resFirstName},</b></strong>
<br/><br/>

<p>Thank you for entering the <b>${auditEmailVO.credentialName}</b>
licenses or certifications information within ServiceLive.&nbsp;
<b>${auditEmailVO.resFirstName}&nbsp; ${auditEmailVO.resLastName}</b>&rsquo;s will now show a <b>&quot;Verified&quot;</b> ServiceLive logo within their 
profile &nbsp; <img src="cid:verifiedLogo"> </p><br/>

<p>Provided your Firm is ServiceLive Approved this license or certification will be visible to all buyers 
in the market place.  These credentials will allow you to accept orders on projects that 
require licenses and certifications.  They also prove as a great differentiator within the 
ServiceLive platform to distinguish you from the competition and demonstrate why your Firm is 
the most capable to do the job.</p><br/>

<p>Should you have any questions about the Sears Provider Network please feel free to contact Sears ServiceLive 
Recruitment at (800) 497-4402.</p><br/>

<p>If you need an immediate help call the ServiceLive Support Team at (888) 549-0640, weekdays between 7:00 AM and 7:00 PM
Central Standard Time or if you would like to attend one of our online facilitated training sessions please click 
the following link to see the latest schedule:</p><br/>

<p><a href="http://community.servicelive.com/view.php?pg=training">
<b>http://community.servicelive.com/view.php?pg=training</b></a></p><br/>



<p>Regards</p><br/>
ServiceLive Team<br/>
3333 Beverly<br/>
5500 Trillium<br/>
Elm Building<br/>
Hoffman Estates, IL 60192

</div>

</body>
</html>' , template_subject='ServicePro - Verified Credentials!' where template_id=231$$

DELIMITER ;

DELIMITER $$

update template set template_source='<html>
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
Congratulations <strong><b>${auditEmailVO.resFirstName},</b></strong>
<br/><br/>
<p>Thank you for entering the <b>${auditEmailVO.credentialName}</b>
licenses or certifications information within ServiceLive.
<b>${auditEmailVO.resFirstName} &nbsp; ${auditEmailVO.resLastName}</b> credentials are now <b>&quot;Reviewed&quot;</b>. </p><br/>
<p>Note: <b>${auditEmailVO.resFirstName}</b> will need to upload the actual license, certification,
or identification card to receive a <b>&quot;Verified&quot;</b> ServiceLive logo within their profile&quot;  </p><br/>
<p>Provided your Firm is ServiceLive Approved this license or certification will be visible
to all buyers in the market place, however it will not post a <b>&quot;Verified&quot;</b> logo beside the description.
These credentials will allow you to accept orders on projects that require licenses and certifications,
although some buyers may request <b>&quot;Verified&quot;</b> documents.
To ensure you have maximum service acceptance we recommend you add proof of the credential to allow it to be verified.
Adding the proof of documentation to the site gives the buyer a greater comfort level and acts as a great differentiator within the
ServiceLive platform to distinguish you from the competition and demonstrate why your Firm is the most capable to do the job.</p><br/>
<p>Should you have any questions about the Sears Provider Network please feel free to contact Sears ServiceLive Recruitment at (800) 497-4402.</p><br/>
<p>If you need an immediate help call the ServiceLive Support Team at (888) 549-0640, weekdays between 7:00 AM and 7:00 PM Central Standard Time or
if you would like to attend one of our online facilitated training sessions please click the following link to see the latest schedule:</p><br/>
<p><a href="http://community.servicelive.com/view.php?pg=training">
<b>http://community.servicelive.com/view.php?pg=training</b></a></p><br/>
<p>Regards</p><br/>
ServiceLive Team<br/>
3333 Beverly<br/>
5500 Trillium<br/>
Elm Building<br/>
Hoffman Estates, IL 60192
</div>
</body>
</html>'  where template_id=233$$

DELIMITER ;


