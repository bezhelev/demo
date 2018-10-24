function onloadCreateTable(){
    var json = (Get('/contact/get/'+numberRecord+','+countRecordOnPage));
    modal = document.getElementById("myModal")
    document.getElementById("selectInTable").value="Patterns"
    fillTable(json)
}

function fillTable(json) {
    if(json==''){
	    return "null";
	}
	var json = JSON.parse(json);
	var newElem = document.createElement("table");
	for (i in json) {
	    var newRow = newElem.insertRow(i);
	    newRow.setAttribute("id","row"+json[i].id);
		var checkbox = document.createElement("input");
		checkbox.setAttribute('type', 'checkbox');
		checkbox.setAttribute('name', json[i].id);
		checkbox.setAttribute('class','checkbox')
		var newCell = newRow.insertCell();
		newCell.width = "100";
		newCell.appendChild(checkbox);
		for (j in json[i]) {
		    if(j=="id") continue;
			var newCell = newRow.insertCell();
			newCell.width = "300";
			if(j=="email") newCell.setAttribute("hidden","true");
			if(j=="fullName") {
			    newCell.setAttribute("onclick","redirect("+json[i]["id"]+")");
			}
			newCell.innerHTML = json[i][j];
		}
	}
	document.getElementById("form").appendChild(newElem);

}

function getCountRecord(){
    var select = document.getElementById("countRecordSelect");
    countRecordOnPage =select.value;
    numberRecord=0;
    var form = document.getElementById("idForPageNumberButton");
    form.innerHTML = " ";
    clearTable();
    getNumberPage();
}


function redirect(id){
    window.location.href='/contact/edit/';

    if(id=='AAA'){
        localStorage.clear();
        return null;
    }
    localStorage.setItem("idForPhone",id)
    localStorage.setItem("idForContact",id)
}

function getNumberPage(){
    var form = document.getElementById("idForPageNumberButton");
    var vid = document.createElement("div");
    vid.setAttribute("id","idForPageNumberButtonForDelete");
    var number = Get("getCount/");
    var i=0;
    var remainder = number%countRecordOnPage;
    number=parseInt(number/countRecordOnPage)
    if(remainder == 0 ) number--;
    for(i;i<=number;i++){
        var submit = document.createElement("button");
        submit.setAttribute('name', i+1);
        submit.setAttribute('class','submit')
        submit.innerHTML = i+1
        submit.setAttribute('id','submit'+i)
        submit.setAttribute('onclick',"getRecord("+i+")")
        vid.appendChild(submit);
    }
    form.appendChild(vid);
    onloadCreateTable()
}

function getRecord(countBySubmit){
    var submitArr = document.getElementsByClassName("submit")
    for(var i=0;i<submitArr.length;i++)
    {
        submitArr[i].disabled = false
    }
    numberRecord = countBySubmit*countRecordOnPage;
    clearTable();
    onloadCreateTable();

}

var flag =false;
function getFiltered(){
    flag=true;
    var textArray = document.getElementsByClassName("text");
    const obj = {}
    for(var i=0;i<textArray.length;i++){

        obj[textArray[i].id] = textArray[i].value
        if(textArray[i].id=="birthday"){
            if(textArray[i].value==='') obj[textArray[i].id] ="0000-01-01"
        }
    }

    var date = document.getElementById("tillData").value
    if(date==='')date="9999-12-31"

    var Httpreq = new XMLHttpRequest(); // a new request
    Httpreq.open("post", "/contact/getfiltered/" + date, false);
    Httpreq.setRequestHeader("type", "post");
    Httpreq.setRequestHeader("Content-type", "application/json");
    Httpreq.send(JSON.stringify(obj));
    var json = Httpreq.responseText
    clearTable();
    fillTable(json)
    document.getElementById("filteredDiv").hidden = true;
    document. getElementById("idForPageNumberButtonForDelete").hidden=true;
    document. getElementById("hideFilteredButton").hidden=false;
}

function cancelFiltered(){
    document.getElementById("filteredDiv").hidden = true;
    document. getElementById("idForPageNumberButtonForDelete").hidden=false;
    document. getElementById("hideFilteredButton").hidden=true;
    clearFiltered()
}

function showFiltered(){
    document.getElementById("filteredDiv").hidden = false;
}

function clearFiltered(){
    clearTable();
    onloadCreateTable();

}

var stringArray = [];

function showEmail(){
    modal.style.display = "block";
    stringArray = [];
    var checkboxArray = document.getElementsByClassName("checkbox");
    var selectedArray = [];
    for(var i=0;i<checkboxArray.length;i++){
        if(checkboxArray[i]["checked"]== true){
            selectedArray.push(checkboxArray[i].name);
        }
    }
    if(selectedArray.length>0){
        var toWhomInput = document.getElementById("toWhomSend");
        toWhomInput.value = "";
        for(var i=0;i<selectedArray.length;i++){
            stringArray.push( document.getElementById("row" +selectedArray[i]).cells[4].innerHTML);
            toWhomInput.value = toWhomInput.value + stringArray[i] + "; ";
        }

    }
    else {
        alert("contacts doesn't selected");
        cancelSend();
    }
}

function deleteChosen(url){
    if (confirm("Are you sure you want to delete these contacts?")) {
        if (confirm("Really?")) {
            var checkbox =  document.getElementsByClassName("checkbox");
            for(i in checkbox){
            if(checkbox[i]["checked"] == true){
                Get(url+"delete/" + checkbox[i].name)
            }

        }

        var element = document. getElementById("idForPageNumberButtonForDelete");
        element. parentNode. removeChild(element);
        getNumberPage();
        var number = Get("getCount/");
        if(number%countRecordOnPage==0){
            numberRecord=numberRecord-countRecordOnPage;
        }
        if(numberRecord<0){
             numberRecord=0;
        }
        clearTable();
        onloadCreateTable()
        }
    }
}


function sendEmail(){
    var textArr = document.getElementsByClassName("myTextOut");
    for(var i=0;i<2;i++){
        if(textArr[i].value==""){
            alert(textArr[i].id + "didn't fill")
            return;
        }
    }
    for(var i=0;i<2;i++){
        stringArray.push(textArr[i].value)
    }

    var Httpreq = new XMLHttpRequest(); // a new request
    Httpreq.onreadystatechange = function() {
        if (Httpreq.readyState == XMLHttpRequest.DONE) {   // XMLHttpRequest.DONE == 4
           if (Httpreq.status == 200) {
               alert("messages sent to users")
           }
           else if (Httpreq.status == 400) {
              alert('There was an error 400');
           }

        }
    };
    Httpreq.open("post", "/contact/sendemail/", true);
    Httpreq.setRequestHeader("type", "post");
    Httpreq.setRequestHeader("Content-type", "application/json");
    Httpreq.send(JSON.stringify(stringArray));
    cancelSend();

}

function cancelSend(){
    stringArray = [];
    modal.style.display = "none";
    var textArray = document.getElementsByClassName("myTextOut");
    for(var i=0;i<textArray.length;i++){
        textArray[i].value="";
    }
}

var modal;
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

function setPatternToText(){
    var selected = document.getElementById("selectInTable");
    var textArray = document.getElementsByClassName("myTextOut");
    if(selected.value=="New Year") {
        textArray[0].value = "New Year"
        textArray[1].value = "Happy New Year!!!"
    }
    if(selected.value=="Hello") {
        textArray[0].value = "Hello, my friend"
        textArray[1].value = "Hello, my friend, how are you?"
    }
}

