var currentFile;
var fileInBase64;
var openFile = function(event) {
    var input = event.target;
    currentFile = input.files[0];
    var fileReader = new FileReader();
    fileReader.readAsDataURL(currentFile);
    fileReader.onload = function() {
        fileInBase64 = fileReader.result;

    }
};

function parseToBase64(file){
    var fileReader = new FileReader();
    fileReader.readAsDataURL(file);
    var stingInBase64 = fileReader.result;
    return stingInBase64;
//        postSend(file,stingInBase64);

}


function loadAttached(){
    fileInBase64 = "none";
    modal2 = document.getElementById('myModal2');
    modal3 = document.getElementById('myModal3');
    if(typeof localStorage.idForPhone=='undefined'){
        localStorage.setItem("idForPhone",0)
    }
    var json = (Get('/attached/get/'+localStorage.idForPhone));
    var json = JSON.parse(json);
    var newElem = document.createElement("table");
    newElem.setAttribute("id","tableForAttached");
    var createEmpty = true;
    for (i in json) {
        createEmpty = false;
        var newRow = newElem.insertRow(i);
        newRow.setAttribute('id', "attached"+json[i]["id"]);
        var checkbox = document.createElement("input");
        checkbox.setAttribute('type', 'checkbox');
        checkbox.setAttribute('name', json[i]["id"]);
        checkbox.setAttribute('class','attachedCheckbox')
        var newCell = newRow.insertCell();
        newCell.width = "100";
        newCell.appendChild(checkbox);
        for (j in json[i]) {
            var newCell = newRow.insertCell();
            newCell.width = "300";
            newCell.setAttribute("name",j);
            if(j=="id") newCell.setAttribute("hidden","true");
            if(j=="contactKey") newCell.setAttribute("hidden","true");
            if(j=="pathToFile") newCell.setAttribute("hidden","true");
            newCell.innerHTML = json[i][j];
        }
        var checkbox = document.createElement("input");
        checkbox.setAttribute('type', 'submit');
        checkbox.setAttribute('name', json[i].id);
        checkbox.setAttribute('value', 'update');
        checkbox.setAttribute('onclick', 'updateAttached('+json[i]["id"]+')');
        checkbox.setAttribute("class","updateButton")
        var newCell = newRow.insertCell();
        newCell.width = "25%";
        newCell.appendChild(checkbox);
    }
    document.getElementById("divForAttached").appendChild(newElem);
    if(createEmpty==true){
        createEmptyTableAttached()
    }
}

function createEmptyTableAttached(){
    var table =  document.getElementById("tableForAttached");
    var newRow = table.insertRow();
    newRow.setAttribute("hidden","true");
    newRow.setAttribute("id","attached0");
    newRow.insertCell();
    var newCell = newRow.insertCell();
    newCell.setAttribute("name","id");
    newCell.innerHTML = 0;
    newRow.insertCell().setAttribute("name","fileName")
    newRow.insertCell().setAttribute("name","uploadDate")
    newRow.insertCell().setAttribute("name","comments")
    newRow.insertCell().setAttribute("name","pathToFile")
    newCell = newRow.insertCell();
    newCell.setAttribute("name","contactKey");
    newCell.innerHTML = localStorage.idForPhone;
    newRow.insertCell();
}

function showAttachedForUpdate(){
   modal3.style.display = "block";
}

function showAttachedForAdd(){
    modal2.style.display = "block";
}

function showAttachedForWork(){
    modal2.style.display = "none";
    modal3.style.display = "none";
}

var row;

var modal2;
var modal3;
window.onclick = function(event) {
    if (event.target == modal ||event.target == modal2 || event.target == modal3) {
        modal.style.display = "none";
        modal2.style.display = "none";
        modal3.style.display = "none";
    }
}


function updateAttached(id){

    showAttachedForUpdate();
    row = document.getElementById("attached" + id);
    document.getElementById("fileName").value=row.cells[2].innerHTML
    document.getElementById("updateCommentsForAttached").value=row.cells[4].innerHTML
}

function updateAttachedRecord(){
    if(checkTextToRightSymbol( document.getElementById("fileName").value)===false) {
        document.getElementById("myPopupfileName").classList.toggle("show");
        document.getElementById("fileName").required = "required";
        document.getElementById("fileName").focus();
        return;
    }
    document.getElementById("myPopupfileName").setAttribute("class","popuptext");
    document.getElementById("fileName").removeAttribute("required")

    row.cells[2].innerHTML = document.getElementById("fileName").value
    row.cells[4].innerHTML = document.getElementById("updateCommentsForAttached").value
    showAttachedForWork()
}

function createAttached(){
    showAttachedForAdd();
    document.getElementById("inputFile").value=''
    document.getElementById("commentsForAttached").value=''
}


var objForSaveInfoAboutFile = []
function addAttachedRecord(){
    if(fileInBase64 ==="none") {
        document.getElementById("myPopupinputFile").classList.toggle("show");
        document.getElementById("inputFile").required = "required";
        document.getElementById("inputFile").focus();
        return;
    }
    document.getElementById("myPopupinputFile").setAttribute("class","popuptext");
    document.getElementById("inputFile").removeAttribute("required")
    showAttachedForWork();
    alert("Add")
    var newElem = document.getElementById("tableForAttached");
    var idForAttached = parseInt(newElem.rows[newElem.rows.length-1].cells[1].innerHTML)+1

    var newRow = newElem.insertRow();
    newRow.setAttribute("id","attached"+idForAttached)

    var checkbox = document.createElement("input");
    checkbox.setAttribute('type', 'checkbox');
    checkbox.setAttribute('name', idForAttached);
    checkbox.setAttribute('class','attachedCheckbox')
    var newCell = newRow.insertCell();
    newCell.width = "100";
    newCell.appendChild(checkbox);

    newCell = newRow.insertCell();
    newCell.width = "300";
    newCell.setAttribute("name","id");
    newCell.setAttribute("hidden","true");
    newCell.innerHTML = idForAttached;

    newCell = newRow.insertCell()
    newCell.width = "300";
    newCell.setAttribute("name","fileName");
    newCell.innerHTML = currentFile.name.split(".")[0];

    newCell = newRow.insertCell()
    newCell.width = "300";
    newCell.setAttribute("name","uploadDate")
    newCell.innerHTML = getCurrentDate();

    newCell=newRow.insertCell()
    newCell.width = "300";
    newCell.setAttribute("name","comments")
    newCell.innerHTML = document.getElementById("commentsForAttached").value;

    newCell = newRow.insertCell()
    newCell.width = "300";
    newCell.setAttribute("name","pathToFile")
    newCell.setAttribute("hidden","true");
    newCell.innerHTML = currentFile.name.split(".")[1];
    var obj = {
        id : idForAttached,
        encodedFile : fileInBase64};
    objForSaveInfoAboutFile.push(obj)

    newCell = newRow.insertCell();
    newCell.width = "300";
    newCell.setAttribute("name","contactKey");
    newCell.setAttribute("hidden","true");
    newCell.innerHTML=localStorage.idForPhone

    newCell = newRow.insertCell();
    newCell.width = "25%";
    var submit = document.createElement("input");
    submit.setAttribute('type', 'submit');
    submit.setAttribute('value', 'update');
    submit.setAttribute('onclick', 'updateAttached('+idForAttached+')');
    submit.setAttribute("class","updateButton")
    newCell.appendChild(submit)
}

function getCurrentDate(){
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1;
    var yyyy = today.getFullYear();
    if(dd<10) {
        dd = '0'+dd
    }
    if(mm<10) {
        mm = '0'+mm
    }
    today =  yyyy+ '-' + mm + '-' + dd;
    return today;
}



function saveAttachedInfo(){
    var table = document.getElementById("tableForAttached")
    var objArray = [];

    for(var i=0;i<table.rows.length;i++){
        if(table.rows[i].cells[1].innerHTML==0){
            continue;
        }
        var singleObj = {}
        for(var j=1;j<7;j++){

            if(table.rows[i].cells[j].getAttribute('name')=="contactKey")table.rows[i].cells[j].innerHTML = localStorage.idForPhone;
            singleObj[table.rows[i].cells[j].getAttribute('name')]=table.rows[i].cells[j].innerHTML;
        }
        objArray.push(singleObj)
    }
    var fileToServer = {
        attachedFileList:objArray,
        fileDataList:objForSaveInfoAboutFile
    }
    var Httpreq = new XMLHttpRequest(); // a new request
    Httpreq.open("post", "/attached/process", false);
    Httpreq.setRequestHeader("type", "post");
    Httpreq.setRequestHeader("Content-type", "application/json");
    Httpreq.send(JSON.stringify(fileToServer));
    if(Httpreq.responseText=="true") alert("Saved successfully")
}

function deleteAttached(){
    if (confirm("Are you sure you want to delete these contacts?")) {
        if (confirm("Really?")) {
            var checkbox =  document.getElementsByClassName("attachedCheckbox");
            for(i in checkbox){
                if(checkbox[i]["checked"] == true){
                    Get("/attached/delete/" + checkbox[i].name)
                    var elem = document.getElementById("attached"+checkbox[i].name);
                    elem.parentNode. removeChild(elem);
                }
            }

        }
    }

}





function decodeBase64Image(dataString) {
  var matches = dataString.match(/^data:([A-Za-z-+\/]+);base64,(.+)$/),
    response = {};

  if (matches.length !== 3) {
    return new Error('Invalid input string');
  }

  response.type = matches[1];
  response.data = new Buffer(matches[2], 'base64');

  download(new Buffer(matches[2], 'base64'), "filegsd",matches[1])
  return response;
}




