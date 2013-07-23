var $allPopups ={};

$(document).ready(function() {

	$('a#openPopupLink').each(function() {
		var $link = $(this);
		var $href=$link.attr('href');
		var $target = $link.attr('target');
		var $title = $link.text();
		if( $title === ""){
			$title='';
		}
		        
		var $dialog = $('<div id="'+$target+'" title="'+$title+'">&nbsp;</div>')
		.dialog({
			autoOpen: false,
			
			width: 700,
			height: 470,
			open: function(event,ui){
				var url = $href;
				if (typeof(popupAppendFunction) == "function") {
					url = url + popupAppendFunction();
				}
				$(this).load(url);
			},
			close: function(event, ui) {
				$(this).html('&nbsp;');
			}
		});
		
		$link.click(function() {
			$dialog.dialog('open');
			return false;
		});
		
		$allPopups[$target] = $dialog;
	
	});

});

