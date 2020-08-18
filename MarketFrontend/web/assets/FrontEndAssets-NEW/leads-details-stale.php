<!-- =================================================================
The Thing About Stale Leads
Stale status leads are a bit odd, in that we're portraying them to the 
user as a distinct status, but they kind of aren't. Not really. On the 
backend, "Stale" should be more of a flag. When a New, Working or 
Scheduled lead becomes "Stale," the layout, info and options of that 
lead's original status remains exactly the same. We just change the 
Status label and add the little warning panel, asking them to complete 
or cancel the lead.
================================================================= --> 

<?php 

include 'templates/header.php';

$leadStatus = '6';
$leadStatusText = 'Stale';
include 'includes/details/hero-cust-info.php';            
  
include 'includes/details/hero-secondary-info.php';

$ctaBtn1 = '<a role="button" href="tel:+18474349986" class="btn btn-default">Call Customer</a>';
$ctaBtn2 = '<a role="button" href="#" class="btn btn-default" data-toggle="modal" data-target="#scheduleWidget">Schedule Appt</a>';
include 'includes/details/cta-buttons.php';

include 'includes/details/mobile-cust-info.php';

include 'includes/details/lead-info.php';

$scheduleHeading = 'Preferred Start Date';
include 'includes/details/schedule.php';

include 'includes/details/utility-tabs.php';




include 'includes/details/utility-content-header.php';
  $historyScheduled = '
    <tr>
      <td><time datetime="2013-11-21T13:37:17">12/23/13 10:33 AM</time></td>
      <td>Status Changed: <span class="label leadStatus-6"><i class="icon-leadStatus-6"></i> Stale</span></td>
      <td>Bob F.</td>
    </tr>
  ';

  include 'includes/details/utility-history.php';
  include 'includes/details/utility-notes.php';
  include 'includes/details/utility-attachments.php';
include 'includes/details/utility-content-footer.php';

include 'templates/footer.php'; 

?>