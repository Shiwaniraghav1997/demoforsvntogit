$(document).ready(function() {

//the ID is used in the anchor tag on the page where the dialog will be loaded, which can be used on multiple links for multiple external files
$('a#fireIframe').each(function() {
var $link = $(this);
        
//wrapping the iframe in DIV tags prevents flickering and general performance poopiness
var $dialog = $('<div id="'+$link.attr('href')+'"></div>')
.dialog({
autoOpen: false,

//set your desired width and height
width: 700,
height: 470,
close: function(event, ui) {
			$(this).html('<div id="'+$link.attr('href')+'"></div>');
		}
});

//opens the dialog on your page on click
$link.click(function() {
	$dialog.load($link.attr('href'));
	$dialog.dialog('open');
	return false;
});

});

});

