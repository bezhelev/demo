var url = window.location.origin + location.pathname.substr(0,location.pathname.length-1);
var numberRecord = 0 ;
var countRecordOnPage = 10;

function prev(){
    clearTable();
    numberRecord = parseInt( numberRecord ) - countRecordOnPage;
    if (numberRecord<0)
        numberRecord=0;
    fillTable();
}

function next(){
    clearTable();
    numberRecord = parseInt( numberRecord ) + countRecordOnPage;
    var ans=fillTable();
    if(ans=="null"){
        numberRecord=0;
        fillTable();
    }
}

function Get(url) {
	var Httpreq = new XMLHttpRequest(); // a new request
	Httpreq.open("get", url, false);
	Httpreq.setRequestHeader("type", "get");
	Httpreq.send(null);
	return Httpreq.responseText;
}



function clearTable(){
    var element = document. getElementById('form');
    element.innerHTML = " ";
}






