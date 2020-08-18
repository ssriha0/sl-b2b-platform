DELIMITER $$

update template set template_source = '<html>
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
<img src=''cid:emailLogo''><br/><br/>
<div id="email_body">
	<strong><b>Thank you for registering on ServiceLive.com. We look forward to working with you and adding value to your business.</b></strong>
	<br> <br> 
	Here is the information we received from you:
	<br> <br> 
	&nbsp;&nbsp;&nbsp;&nbsp; username: ${USERNAME}<br /><br />
	&nbsp;&nbsp;&nbsp;&nbsp; password: ${PASSWORD}<br /><br /> 
	&nbsp;&nbsp;&nbsp;&nbsp; Please keep your login information in a safe place. <br /><br /> 
	We appreciate the time you spent to provide us with your primary profile information. At this point, we need you to complete your <u>Business Profile</u> and <u>User Profiles</u>. 
	<br /><br />
	Completing the application process and passing the background check will allow you to target and acquire new customers. By participating in the ServiceLive program, you will have the opportunity to become a leading provider firm in the service marketplace. And by providing world class service, you will qualify for additional business opportunities.
	<br />
	<br /> 
	As a ServiceLive Provider Firm, you can:  
	<br />
	<ul>
	<li><strong>Increase your sales opportunities.</strong> Our database includes buyers from some of the largest home service companies. Instead of spending your time looking for leads, they''ll come to you!</li>
	<li><strong>Expand your presence into other markets.</strong> Deepen your services and solutions to respond to more complex service opportunities. </li>
	<li><strong>Fill unexpected downtime quickly,</strong> without the obligation to accept work you don''t need.</li>
	<li><strong>Save money on advertising and level the playing field.</strong> As a ServiceLive Provider Firm, you''ll gain access to the same dependable service opportunities as larger competitors in your area.</li>
	<li><strong>Receive quick automatic payment.</strong> No more worrying about getting paid. Service buyers pre-pay online to post their service orders.</li>
	<li><strong>Enjoy the support of an effective sales partner. </strong> Your business can benefit from the combined reach, resources and reputation of ServiceLive and our partners. Joining our network is a great way to increase your sales opportunities, and reduce your costs. </li>
	</ul>
	<br /> 
	<u>Now that you are registered</u>, please return to the ServiceLive.com site to log in and complete the business application process and enter profiles for your service provider users. <br> Please confirm your registration and complete the application process to become a ServiceLive Provider Firm by clicking the link below or pasting it into your browser''s address bar.  
	<br />
	<br />
	<a href=''http://${WEBSITEURL}''>http://www.servicelive.com</a>
	<br />
	<br /> 
	If you have any questions about using ServiceLive.com or providing service for ServiceLive, please read our Service Provider FAQs. If you have questions concerning the use of your information by ServiceLive.com, please read our privacy statement.<br /><br />
	Thanks again for registering and welcome to ServiceLive!
</div>
</body>
</html>'
where template_id = 15$$

DELIMITER ;