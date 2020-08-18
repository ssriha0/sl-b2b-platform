<?php 

include 'templates/header.php'; 
include 'includes/details/hero-cust-info.php';            
  
$leadStatus = '2';
$leadStatusText = 'Working';
include 'includes/details/hero-secondary-info.php';

$ctaBtn1 = '<a role="button" href="#" class="btn btn-default" data-toggle="modal" data-target="#callWidget">Call Customer</a>';
$ctaBtn2 = '<a role="button" href="#" class="btn btn-default" data-toggle="modal" data-target="#scheduleWidget">Schedule Appt</a>';
include 'includes/details/cta-buttons.php';

include 'includes/details/mobile-cust-info.php';

include 'includes/details/lead-info.php';

$scheduleHeading = 'Preferred Start Date';
include 'includes/details/schedule.php';
include 'includes/details/widget-call.php';

include 'includes/details/utility-tabs.php';

include 'includes/details/utility-content-header.php';
  include 'includes/details/utility-history.php';
  include 'includes/details/utility-notes.php';
  include 'includes/details/utility-attachments.php';
include 'includes/details/utility-content-footer.php';

include 'templates/footer.php'; 

?>