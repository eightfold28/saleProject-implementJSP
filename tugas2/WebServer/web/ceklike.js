function likecount(itemid, token) {
    var xmlhttp = new XMLHttpRequest();
	if (!xmlhttp) {
		xmlhttp = getXMLHttpRequest( );
	}
	if (!xmlhttp) {
    	return;
    }
    var itemID = 'item_ID=' + itemid;
    var activeID = 'active_ID=' + token; 
    var url = 'like.php?' + itemID + '&' + token;
    xmlhttp.open('POST', url, true);
    xmlhttp.onreadystatechange = function() 
    {
        if(xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            document.getElementById(itemid).innerHTML = "<span style=\"color:red\">LIKED</span>";
        }
    }
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlhttp.send(itemID);
}

function unlikecount(itemid, token) {
    var xmlhttp = new XMLHttpRequest();
    if (!xmlhttp) {
        xmlhttp = getXMLHttpRequest( );
    }
    if (!xmlhttp) {
        return;
    }
    var itemID = 'item_ID=' + itemid;
    var activeID = 'active_ID=' + token; 
    var url = 'unlike.php?' + itemID + '&' + token;
    xmlhttp.open('POST', url, true);
    xmlhttp.onreadystatechange = function() 
    {
        if(xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            document.getElementById(itemid).innerHTML = "LIKE";
        }
    }
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlhttp.send(itemID);
}




