<?php 

include 'templates/header.php'; 
include 'includes/details/hero-cust-info.php';            
  
$leadStatus = '4';
$leadStatusText = 'Completed';

include 'includes/details/hero-secondary-info.php';

include 'includes/details/mobile-cust-info.php';

include 'includes/details/lead-info.php';

$scheduleHeading = 'Appointment Details';
$assignedPro = '<br><b>Provider</b>: Tim Taylor';

include 'includes/details/schedule.php';

include 'includes/details/utility-tabs.php';

include 'includes/details/utility-content-header.php';
  $historyScheduled = '
    <tr>
      <td><time datetime="2013-11-21T13:37:17">12/05/13 1:25 AM</time></td>
      <td>Status Changed: <span class="label leadStatus-4"><i class="icon-leadStatus-4"></i> Completed</span></td>
      <td>Tim T.</td>
    </tr>
    <tr>
      <td><time datetime="2013-11-21T13:37:17">12/04/13 11:15 AM</time></td>
      <td>Provider Assigned: Tim Taylor</td>
      <td>Bob F.</td>
    </tr>
    <tr>
      <td><time datetime="2013-11-21T13:37:17">12/02/13 10:33 AM</time></td>
      <td>Status Changed: <span class="label leadStatus-3"><i class="icon-leadStatus-3"></i> Scheduled</span></td>
      <td>Bob F.</td>
    </tr>
  ';
  include 'includes/details/utility-history.php';
  include 'includes/details/utility-notes.php';
  include 'includes/details/utility-attachments.php';
include 'includes/details/utility-content-footer.php';

include 'templates/footer.php';

 ?>