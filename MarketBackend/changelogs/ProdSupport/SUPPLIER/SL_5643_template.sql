DELIMITER $$

update template set template_source ='
<html>
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
Congratulations <strong><b>${PrimeContact},</b></strong>
<br/><br/>
<p>Thank you for entering the <b>${auditEmailVO.credentialName}</b>
licenses or certifications information within ServiceLive.
Your company&rsquo;s credentials are now <b>&quot;Reviewed&quot;</b> and will show up in your profile.  </p>
<p><font color="blue">&quot;You will need to upload the actual license, certification,
or identification card to receive a <b>&quot;Reviewed&quot;</b> ServiceLive logo within your company&rsquo;s profile&quot;. </font></p>
<p>Provided your Firm is ServiceLive Approved this license or certification will be visible
to all buyers in the market place, however it will not post a <b>&quot;Verified&quot;</b> logo beside the description.
These credentials will allow you to accept orders on projects that require licenses and certifications,
although some buyers may request <b>&quot;Verified&quot;</b> documents.
To ensure you have maximum service acceptance we recommend you add proof of the credential to allow it to be verified.
Adding the proof of documentation to the site gives the buyer a greater comfort level and acts as a great differentiator within the
ServiceLive platform to distinguish you from the competition and demonstrate why your Firm is the most capable to do the job.</p>
<p>Should you have any questions about the Sears Provider Network please feel free to contact Sears ServiceLive Recruitment at (800) 497-4402.</p>
<p>If you need an immediate help call the ServiceLive Support Team at (888) 549-0640, weekdays between 7:00 AM and 7:00 PM Central Standard Time or if you would like to attend one of our online facilitated training sessions please click the following link to see the latest schedule:</p>
<p><a href="http://community.servicelive.com/view.php?pg=training">
<b>http://community.servicelive.com/view.php?pg=training</b></a></p>
<p>Regards</p>
ServiceLive Team<br/>
3333 Beverly<br/>
5500 Trillium<br/>
Elm Building<br/>
Hoffman Estates, IL 60192
</div>
</body>
</html>',template_subject = 'Provider Firm - Reviewed Credentials!'
where template_id=234
$$

DELIMITER ;

DELIMITER $$

update template set template_source ='
<html>
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


Congratulations <strong><b>${PrimeContact},</b></strong>
<br/><br/>

<p>Thank you for entering the <b>${auditEmailVO.credentialName}</b>
licenses or certifications information with ServiceLive.&nbsp;
Your company&rsquo;s profile will now show a <b> &quot;Verified&quot;</b> ServiceLive logo within their profile&quot; &nbsp; <img src="cid:verifiedLogo"> </p>

<p>Provided your Firm is ServiceLive Approved this license or certification will be visible to all buyers 
in the market place.  These credentials will allow your Firm to accept orders on projects that 
require licenses and certifications.  They also prove as a great differentiator within the 
ServiceLive platform to distinguish you from the competition and demonstrate why your Firm is 
the most capable to do the job.</p>

<p>Should you have any questions about the Sears Provider Network please feel free to contact Sears ServiceLive Recruitment at (800) 497-4402.</p>

<p>If you need an immediate help call the ServiceLive Support Team at (888) 549-0640, weekdays between 7:00 AM and 7:00 PM Central Standard Time or if you would like to attend one of our online facilitated training sessions please click the following link to see the latest schedule:</p>

<p><a href="http://community.servicelive.com/view.php?pg=training">
<b>http://community.servicelive.com/view.php?pg=training</b></a></p>



<p>Regards</p>
ServiceLive Team<br/>
3333 Beverly<br/>
5500 Trillium<br/>
Elm Building<br/>
Hoffman Estates, IL 60192

</div>

</body>
</html>',template_subject = 'Provider Firm - Verified Credentials!'
where template_id=232
$$

DELIMITER ;

DELIMITER $$

update template set template_source ='
<html>
<head>
<style type="text/css">
	#email_body {font-family:Arial; 
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

<p>Congratulations!</p>

<p>Your Provider Firm is now <b>&quot;ServiceLive Approved&quot;</b>.  A ServiceLive 
approved Firm you will begin to receive calls from the <b>ServiceLive 
Open Marketplace</b>.  Consumers within your area can now explore your 
profile and route service based upon skills sets and credential of this team member.</p>

<p>We would like to introduce your Firm to the <b>ServiceLive Community 
Forum</b> which allows you to interact with other providers in the community,
read the latest news about ServiceLive, engage in discussions, 
and share industry experiences with other providers in your service 
category. By becoming a <b>&quot;ServiceLive Approved&quot;</b> firm you will have access
to the <b>Provider Only Private Forum</b> and become part of an elite group 
with other service providers. Registration is easy and only takes about 
one minute to complete.</p>

<p><a href="http://community.servicelive.com/">
<b>http://community.servicelive.com/</b></a></p>

<p>We have also created a &quot;Provider Playbook&quot; for your Firm and ServicePros to better assist you with your ServiceLive profile.</p>

<p><a href="http://community.servicelive.com/docs/ServiceLive_Provider_Playbook.pdf">
<b>http://community.servicelive.com/docs/ServiceLive_Provider_Playbook.pdf</b></a></p>

<p>Also we would like to offer you this opportunity to partner with one
of our preferred buyers, <b>Sears Holding Corporation</b>. Only service 
providers that are designated as being in the <b>Sears Select Provider Network</b>
will receive notification of jobs related to this buyer.</p>

<p>Benefits of Sears Provider Network </p>
<ul>
<li>First right of refusal on Sears service orders</li>
<li>Potential Increase of Order Volume based on skillet set coverage</li>
<li>Working with one of the largest installation companies</li>
<li>Online Order management through ServiceLive</li>
</ul>

<p>Criteria Requirements</p>
<ul>
<li>Must have commercial auto and commercial general liability insurance</li>
<li>Must be able to passed background checks on technician level</li>
<li>Must hold active state licenses required for selected skill sets</li>
</ul>

<p>If you are interested and would like to obtain additional information please email 
<a href="mailto:sears_servicelive@searshc.com">
<b>sears_servicelive@searshc.com</b></a> or contact Sears ServiceLive Recruitment at (800) 497-4402.</p>

<p>If you need an immediate help call the ServiceLive Support Team at 
(888) 549-0640, weekdays between 7:00 AM and 7:00 PM Central Standard Time
or if you would like to attend one of our online facilitated training 
sessions please click the following link to see the latest schedule:</p>

<p><a href="http://community.servicelive.com/view.php?pg=training">
<b>http://community.servicelive.com/view.php?pg=training</b></a></p>


<p>Regards</p>
ServiceLive Team<br/>
3333 Beverly<br/>
5500 Trillium<br/>
Elm Building<br/> 
Hoffman Estates, IL 60192<br/><br/>
</div>
</body>
</html>',template_subject = 'Provider Firm - ServiceLive Approved!'
where template_id=18
$$

DELIMITER ;