<?php 

include 'templates/header.php'; 
  
$leadStatus = '1';
$leadStatusText = 'New';
include 'includes/details/hero-cust-info.php';            
include 'includes/details/hero-secondary-info.php';

$ctaBtn1 = '<a role="button" href="#" class="btn btn-default" data-toggle="modal" data-target="#callWidget">Call Customer</a>';
$ctaBtn2 = '<a role="button" href="#" class="btn btn-default" data-toggle="modal" data-target="#scheduleWidget">Schedule Appt</a>';
include 'includes/details/cta-buttons.php';

include 'includes/details/mobile-cust-info.php';

include 'includes/details/lead-info.php';

$scheduleHeading = 'Preferred Start Date';
include 'includes/details/schedule.php';
include 'includes/details/widget-call.php';

include 'templates/footer.php'; 

?>