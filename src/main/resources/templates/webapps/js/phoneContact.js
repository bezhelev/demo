function fillTable() {
    modal = document.getElementById('myModal');
    if(typeof localStorage.idForPhone=='undefined'){
        localStorage.setItem("idForPhone",0)
    }
    var json = (Get('/phonecontact/get/'+localStorage.idForPhone));
	var json = JSON.parse(json);
	var newElem = document.createElement("table");
	newElem.setAttribute("id","tableForInput");
	for (i in json) {
	    var newRow = newElem.insertRow(i);
	    newRow.setAttribute('id', json[i]["id"]);
		var checkbox = document.createElement("input");
		checkbox.setAttribute('type', 'checkbox');
		checkbox.setAttribute('name', json[i]["id"]);
		checkbox.setAttribute('class','checkbox')
		var newCell = newRow.insertCell();
		newCell.width = "100";
		newCell.appendChild(checkbox);
		for (j in json[i]) {

			var newCell = newRow.insertCell();
			newCell.width = "300";
            newCell.setAttribute("name",j);
			if(j=="id") newCell.setAttribute("hidden","true");
			if(j=="contactKey") newCell.setAttribute("hidden","true");
			if(j=="phoneType") {
			    if(json[i][j]==1)
			        newCell.innerHTML ="home";
                else
			        newCell.innerHTML ="mobile"
			}
			else
			    newCell.innerHTML = json[i][j];
		}
        var checkbox = document.createElement("input");
        checkbox.setAttribute('type', 'submit');
        checkbox.setAttribute('name', json[i].id);
        checkbox.setAttribute('value', 'update');
        checkbox.setAttribute("class","updateButton")
        checkbox.setAttribute('onclick', 'updatePhone('+json[i]["id"]+')');
        var newCell = newRow.insertCell();
        newCell.width = "100";
        newCell.appendChild(checkbox);

	}

	document.getElementById("form").appendChild(newElem);
    createEmptyTable();
}

function createEmptyTable(){
    document.getElementById("id").value=0;
    var newElem = document.getElementById("tableForInput");
    var newRow = newElem.insertRow();
    newRow.setAttribute('hidden', "true");
    newRow.setAttribute('id', "0");
    var checkbox = document.createElement("input");
    checkbox.setAttribute('type', 'checkbox');
    checkbox.setAttribute('name', "0");
    checkbox.setAttribute('class','checkbox')
    newRow.insertCell().appendChild(checkbox);
    newRow.insertCell().setAttribute("name","id");
    newRow.insertCell().setAttribute("name","countryCode");
    newRow.insertCell().setAttribute("name","operatorCode");
    newRow.insertCell().setAttribute("name","phoneNumber");
    newRow.insertCell().setAttribute("name","phoneType");
    newRow.insertCell().setAttribute("name","comments");
    newRow.insertCell().setAttribute("name","contactKey");
    newRow.insertCell();
}

function showDiv(){
    document.getElementById("buttonDiv").hidden = true
    document.getElementById("form").hidden = true
    document.getElementById("addDiv").hidden = false

}

var modal;
function createPhone(){
//    cancelClear();
//     showDiv();
    modal.style.display = "block";
}

function cancelClear(){
//    document.getElementById("buttonDiv").hidden = false
//    document.getElementById("form").hidden = false
//    document.getElementById("addDiv").hidden = true
    var textA= document.getElementsByClassName("textPhone");
    modal.style.display = "none";
    for(i in textA)
        textA[i].value=''
}


function updatePhone(id){
    var rowsId = document.getElementById(id);
    var i=1;
    for(i;i<8;i++){
        document.getElementById(rowsId.cells[i].getAttribute('name')).value =rowsId.cells[i].innerHTML
        if(rowsId.cells[i].getAttribute('name')=="phoneType") {
            if(rowsId.cells[i].innerHTML=='mobile')
                document.getElementById(rowsId.cells[i].getAttribute('name')).value =2
            else
                document.getElementById(rowsId.cells[i].getAttribute('name')).value =1
        }
    }
    modal.style.display = "block";
}

function addInfo(){
    if(document.getElementById('id').value>0) updateInfoInTable();
    else addToTable();

}

function addToTable(){
    if(validateInputsPhones()==false) return;
    var newElem = document.getElementById("tableForInput");
    var newRow = newElem.insertRow();
    var checkboxArray=document.getElementsByClassName('checkbox')
    newRow.setAttribute("id",parseInt(checkboxArray[checkboxArray.length-1].name)+1)
    var checkbox = document.createElement("input");
    checkbox.setAttribute('type', 'checkbox');
    checkbox.setAttribute('name', parseInt(checkboxArray[checkboxArray.length-1].name)+1);
    checkbox.setAttribute('class','checkbox')
    var newCell = newRow.insertCell();
    newCell.width = "100";
    newCell.appendChild(checkbox);
    var textElement = document.getElementsByClassName("textPhone");
    for (j in textElement) {
        var newCell = newRow.insertCell();
        newCell.width = "300";
        newCell.innerHTML = textElement[j].value;
        newCell.setAttribute('name',textElement[j].id);
        if(textElement[j].id=="phoneType") {
            if(textElement[j].value==1)
                newCell.innerHTML = "home";
            else
                newCell.innerHTML = "mobile"
        }
        if(textElement[j].id=="id") {
            newCell.setAttribute("hidden","true");
            newCell.innerHTML = parseInt(checkboxArray[checkboxArray.length-1].name)
        }
        if(textElement[j].id=="contactKey") {
            newCell.setAttribute("hidden","true");
            newCell.innerHTML = localStorage.idForPhone
            break;
        }


    }
    var checkbox = document.createElement("input");
    checkbox.setAttribute('type', 'submit');
    checkbox.setAttribute('name',parseInt(checkboxArray[checkboxArray.length-1].name));
    checkbox.setAttribute('value', 'update');
    checkbox.setAttribute('onclick', 'updatePhone('+parseInt(checkboxArray[checkboxArray.length-1].name) + ')');
    checkbox.setAttribute("class","updateButton")
    var newCell = newRow.insertCell();
    newCell.width = "100";
    newCell.appendChild(checkbox);
    cancelClear();
    alert("confirm");
}

function updateInfoInTable(){
    if(validateInputsPhones()==false) return;
    var textElement = document.getElementsByClassName("textPhone");
    var rowsId = document.getElementById(document.getElementById("id").value);
    var i=1;
    for(i;i<8;i++){
        rowsId.cells[i].innerHTML=textElement[i-1].value
        if(textElement[i-1].id=="phoneType"){
            if(textElement[i-1].value==1){
                rowsId.cells[i].innerHTML='home'
            }else{
                rowsId.cells[i].innerHTML='mobile'
            }
        }
    }
    cancelClear();
    alert("confirm");
}


function saveAll(){

    var table = document.getElementById("tableForInput");
    var i=0
    var string="[";
    if(localStorage.idForPhone==0){
         localStorage.setItem("idForPhone",Get("getLastId/"));
    }
    var arrayObj = []
    for(i;i<table.rows.length;i++){
        var j =1;
        string+="{";
        const obj = {};
        for(j;j<8;j++){
            if(table.rows[i].cells[j].getAttribute('name')=='contactKey'){
                table.rows[i].cells[j].innerHTML = localStorage.idForPhone
            }
            obj[table.rows[i].cells[j].getAttribute('name')] = table.rows[i].cells[j].innerHTML;
        }
        arrayObj.push(obj);
    }

    var Httpreq = new XMLHttpRequest(); // a new request
    Httpreq.open("post", "/phonecontact/add/", false);
    Httpreq.setRequestHeader("type", "post");
    Httpreq.setRequestHeader("Content-type", "application/json");
    Httpreq.send(JSON.stringify(arrayObj));
}

function deletePhone(){
    if (confirm("Are you sure you want to delete these contacts?")) {
        if (confirm("Really?")) {
            var checkbox =  document.getElementsByClassName("checkbox");
            for(i in checkbox){
             if(checkbox[i]["checked"] == true){
                    Get("/phonecontact/delete/" + checkbox[i].name + "," +localStorage.idForPhone)
                    var elem = document.getElementById(checkbox[i].name);
                    elem.parentNode. removeChild(elem);
             }
        }

    }
    }
}

function checkTextToNumbers(newValue){
    return /^[0-9]+$/.test(newValue);
}


function validateInputsPhones(){
    var textArray = document.getElementsByClassName("textPhone");
    var i=1;
    for(i;i<textArray.length-3;i++){
        var popup = document.getElementById("myPopup"+textArray[i].id);
        popup.setAttribute("class","popuptext");
        textArray[i].removeAttribute("required")
    }
    i=1;
    for(i;i<textArray.length-3;i++){
        if(checkTextToNumbers(textArray[i].value)===false){
            var popup = document.getElementById("myPopup"+textArray[i].id);
            textArray[i].required = "required";
            textArray[i].focus();
            popup.classList.toggle("show");
            return false;
        }
    }

    return true;
}

function isNumberKey(evt){
    var charCode = (evt.which) ? evt.which : evt.keyCode
    return !(charCode > 31 && (charCode < 48 || charCode > 57));
}

