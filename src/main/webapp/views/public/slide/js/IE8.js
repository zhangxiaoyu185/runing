$(function(){
	//input placeholder
    jQuery('[placeholder]').focus(function() {
	  var input = jQuery(this);
	  if (input.val() == input.attr('placeholder')) {
	    input.val('');
	    input.removeClass('placeholder');
	  }
	  input.css("color","#555");
	}).blur(function() {
	  var input = jQuery(this);
	  if (input.val() == '' || input.val() == input.attr('placeholder')) {
	    input.addClass('placeholder');
	    input.val(input.attr('placeholder'));
	    input.css("color","#aaa");
	  }
	}).blur().parents('form').submit(function() {
	  jQuery(this).find('[placeholder]').each(function() {
	    var input = jQuery(this);
	    if (input.val() == input.attr('placeholder')) {
	      input.val('');
	    }
	  })
	});
	
});
