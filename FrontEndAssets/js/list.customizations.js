$(function(){
  // Affixes list controls
  /* BUG - in iOS
  $('list-heading').affix({
    offset: {
      top: function () {
        return (this.top = $('.header').height())
      }
    }
  })*/

  // List.js functions; search, filter, etc.
  //==================================================================================

  // initializes list with emptyView plugin
  var options = {
    valueNames: [ 
      'leadStatus',
      'skill',
      'projectType',
      'urgency',
      'fname',
      'lname',
      'phone',
      'city',
      'zip'
    ],
    plugins: [
        [ 'emptyView', {
            emptyViewId: "emptyView"
        }]
    ]
  };

  var featureList = new List('leads-list', options);

  // Sets up filter buttons
  // CF - this code needs to be optimized!
  //var leadStatus = item.values().leadStatus.toLowerCase()

  $('#filter-2').click(function() {
    featureList.filter(function(item) {
      if (item.values().leadStatus.toLowerCase() == "new" ||
          item.values().leadStatus.toLowerCase() == "completed") {
        return true;
      } else {
        return false;
      }
    });
    //return false;
  });

  $('#filter-3').click(function() {
    featureList.filter(function(item) {
      if (item.values().leadStatus.toLowerCase() == "working" ||
          item.values().leadStatus.toLowerCase() == "cancelled") {
      return true;
      } else {
      return false;
      }
    });
    //return false;
  });

  $('#filter-4').click(function() {
    featureList.filter(function(item) {
      if (item.values().leadStatus.toLowerCase() == "scheduled" ||
          item.values().leadStatus.toLowerCase() == "stale") {
      return true;
      } else {
      return false;
      }
    });
    //return false;
  });

  $('#filter-all').click(function() {
    featureList.filter();
    //return false;
  });


  // Collapsed search bar 
  //==================================================================================

  // toggles icon on search collapse
  $('.list-title .glyphicon-search').click(function() {
    $(this).toggleClass('glyphicon-chevron-down');
  });

  // BUG - Focus brought to search bar on expand -- iOS Bug makes this not work :(
  $('#search-wrap').on('shown.bs.collapse', function(){
    $('input',this).focus();
  });

  // Swaps search icon for X close icon
  var searchInput = $('#search-wrap .search');
  var searchIcon = searchInput.next();
  function icon_swap(){
    if (searchInput.val() == '') {
      searchIcon.removeClass('glyphicon-remove-circle');
    }
    else {
      searchIcon.addClass('glyphicon-remove-circle');
    }
  }
  // when typing in the search field
  searchInput.keyup(icon_swap);
  // when clicking the X icon
  searchIcon.click(function() {
    if (searchInput.val() != '') {
      searchInput.val('');
      icon_swap();
    }
    // BUG - NEED TO DEBUG THIS, LIST IS NOT UPDATED WHEN YOU CLEAR THE SEARCH FIELD.
    featureList.update();
  });

  // Mobile LIs - Make each li a link target (but also let regular <a> links do their thing)
  //==================================================================================
  var viewportWidth = $(window).width();
  //var viewportHeight = $(window).height();

  // check width of screen (maybe wanna use Modernizr to check for touch  capability)
  if (viewportWidth < '700') {
    
    $('#leads-list li.list-group-item').on('click', function(event) {
      // If the target was a link, 
      if (event.target.nodeName == 'A') {
        //stop the event from bubbling up the DOM
        event.stopPropagation();
      }
      // If target was anything else
      else {
        //alert($(this).attr('data-leadid'));
        var leadId = $(this).attr('data-leadid');
        
        // load the lead details page
        window.location.href = 'lead-' + leadId + '-details.html';
      
      }
    });

  }



});