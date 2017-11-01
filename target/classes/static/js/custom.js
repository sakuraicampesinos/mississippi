$(document).ready(function(){


  $(".submenu > a").click(function(e) {
    e.preventDefault();
    var $li = $(this).parent("li");
    var $ul = $(this).next("ul");

    if($li.hasClass("open")) {
      $ul.slideUp(350);
      $li.removeClass("open");
    } else {
      $(".nav > li > ul").slideUp(350);
      $(".nav > li").removeClass("open");
      $ul.slideDown(350);
      $li.addClass("open");
    }
  });
  
});

$(function(){
	$('.btns').click(function(){
		wn = '.' + $(this).data('tgt');
		var mW = $(wn).find('.modalBody').innerWidth() / 2;
		var mH = $(wn).find('.modalBody').innerHeight() / 2;
		$(wn).find('.modalBody').css({'margin-left':-mW,'margin-top':-mH});
		$(wn).fadeIn(300);
	});
	$('.close,.modalBK').click(function(){
		$(wn).fadeOut(100);
	});
});

$(function() {
        $('#start').timepicker({
        timeFormat: 'H:i',
        minTime: '07:00',
        maxTime: '23:30',
        'scrollDefault': 'now',
        maxHour: 23
});
});

$(function() {
        $('#end').timepicker({
        timeFormat: 'H:i',
        minTime: '07:00',
        maxTime: '23:30',
        'scrollDefault': 'now',
        maxHour: 23
});
});
