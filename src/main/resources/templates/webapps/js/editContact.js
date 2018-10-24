var month = ["January","February","March","April","May","June","July","August","September","October","November","December"];
var dayInMonth31 = [0,2,4,6,7,9,11];
var dayInMonth30 = [3,5,8,10];
var imgAvatarBase64 = "";

function onloadFunc(){
    addAvatar(Get("/contact/getaavatars/"));
    imgAvatarBase64 = "none";
    if(typeof localStorage.idForContact!=='undefined'){
        var json = JSON.parse(Get('/contact/getSolo/'+localStorage.idForContact));
        for(i in json){
            if(i=='id'){
                var text=document.getElementById("idContact");
                if(text!=null)
                    text.value = json[i];
            }
            if(i=="pathToAvatar"){
                 imgAvatarBase64 = json[i];
                 addAvatar(json[i])
                 continue;
            }
            var text=document.getElementById(i);
            if(text!=null)
                text.value = json[i];
        }
        createYears();
        createMonth();
        createdDay();
        var birthday = new Date(json["birthday"])
        document.getElementById("yearSelect").value = birthday.getYear() + 1900
        document.getElementById("monthSelect").value = birthday.getMonth()+1
        document.getElementById("daySelect").value = birthday.getDate()
    }


    fillTable();
    loadAttached();
}

function createYears(){
    var select = document.createElement("select");
    var dt = new Date();
    var i;
    select.setAttribute("onclick","createMonth()")
    select.setAttribute("id","yearSelect")
    select.setAttribute("class","selectInTableContact");
    for (i = 1940; i <= 1900+dt.getYear() ; i++) {
        var option = document.createElement("option");
        option.innerHTML = i;
        select.appendChild(option);
    }
    addLabel("Year");
    document.getElementById("birthdayDiv").appendChild(select);
}

function createMonth(){
    if( document.getElementById("monthSelect")==null){
        var select = document.createElement("select");
        var i;
        select.setAttribute("onclick","createdDay()")
        select.setAttribute("id","monthSelect")
        select.setAttribute("class","selectInTableContact");
        for (i = 0; i < 12 ; i++) {
            var option = document.createElement("option");
            var value = "";
            if(i+1<10) value = "0";
            value += (i+1)
            option.setAttribute("value",value)
            option.innerHTML = month[i];
            select.appendChild(option);
        }
        addLabel("Month");
        document.getElementById("birthdayDiv").appendChild(select);

    }
}

function createdDay(){
    if(document.getElementById("daySelect")!=null){
        var element = document. getElementById("daySelect");
        element. parentNode. removeChild(element);
    }
    var i=0;
    for(i=0;i<dayInMonth31.length;i++){

        if(document.getElementById("monthSelect").value===month[dayInMonth31[i]])
        {
            createDaySelect(31);
            return null;

        }
    }
    var i=0;
    for(i=0;i<dayInMonth30.length;i++){
        if(document.getElementById("monthSelect").value==month[dayInMonth30[i]])
        {
            createDaySelect(30);
            return null;
        }
    }
    createDaySelect(28);


}

function createDaySelect(countDay){
    var select = document.createElement("select");
    var i;
    select.setAttribute("id","daySelect")
    select.setAttribute("class","selectInTableContact");
    for (i = 1; i <= countDay ; i++) {
        var option = document.createElement("option");
        if(i<10)
            option.setAttribute("value",'0'+i)
        else
            option.setAttribute("value",i)
        option.innerHTML = i;
        select.appendChild(option);
    }
    addLabel("Day");
    document.getElementById("birthdayDiv").appendChild(select);
}

function addLabel(name){
    if(document.getElementById("label"+name)==null){
        var label = document.createElement("label");
        label.setAttribute("id","label"+name);
        label.setAttribute("class","label");
        label.innerHTML = "  " + name + "  ";
        document.getElementById("birthdayDiv").appendChild(label);
    }
}

function readInfo(){
    if(validateInputs()===false) return;
    var textArray = document.getElementsByClassName("text");
    var objToSend= {};
    for(i in textArray){
        if(textArray[i].id=="idContact"){
            objToSend["id"]=textArray[i].value
            continue;
        }
        objToSend[textArray[i].id]=textArray[i].value
        if(textArray[i].id== "postOfficeIndex") break;
    }
    objToSend["sex"]=document.getElementById("sex").value;

    var string=""
    if( document.getElementById("yearSelect")!=null){
        string +=document.getElementById("yearSelect").value
        if(document.getElementById("yearSelect").value=='')    string +=1900;
        if( document.getElementById("monthSelect")!=null){
            string +="-"+ document.getElementById("monthSelect").value
            if(document.getElementById("monthSelect").value=='')    string +="01";
            if(document.getElementById("daySelect")!=null){

                string +="-"+ document.getElementById("daySelect").value
                if(document.getElementById("daySelect").value=='')string +="01"
            }
            else{
                 string +="-01"
            }
        }
        else{
            string +="-01-01"
        }
    } else{
        string +="1900-01-01"
    }
    objToSend["birthday"] = string;
    objToSend["pathToAvatar"] = imgAvatarBase64;
    console.log(objToSend)
    if(typeof  localStorage.idForContact!=='undefined'){
        postSend("/contact/update/",objToSend);
    }
    else{
        postSend("/contact/add/",objToSend);
    }
    saveAll();
    saveAttachedInfo()
    clearInfo();
    window.location.href='/contact/';
}

function postSend(url,obj){
    var Httpreq = new XMLHttpRequest(); // a new request
    Httpreq.open("post",url, false);
    Httpreq.setRequestHeader("type", "post");
    Httpreq.setRequestHeader("Content-type", "application/json");
    Httpreq.send(JSON.stringify(obj));
}

function clearInfo(){
    localStorage.clear();
    var textArray = document.getElementsByClassName("text");
    for(i in textArray){
        textArray[i].value = ""
    }
    document.getElementById("idContact").value=0;
    var element = document. getElementById("daySelect");
    if(element!=null)
        element. parentNode. removeChild(element);
    var element = document. getElementById("monthSelect");
    if(element!=null)
        element. parentNode. removeChild(element);
    var element = document. getElementById("yearSelect");
    if(element!=null)
        element. parentNode. removeChild(element);
    var element = document. getElementById("labelDay");
        if(element!=null)
            element. parentNode. removeChild(element);
    var element = document. getElementById("labelMonth");
        if(element!=null)
            element. parentNode. removeChild(element);
    var element = document. getElementById("labelYear");
        if(element!=null)
            element. parentNode. removeChild(element);
}

function addAvatar(fileInBase64){
    var image = new Image();
    image.src = fileInBase64;
    document.getElementById("avatarImage").innerHTML="";
    document.getElementById("avatarImage").appendChild(image);
}

function openAvatarDialogWindow(){
    document.getElementById("avatarImageFile").click();
}

var openAvatar = function(event) {
    var input = event.target;
    var cFile= input.files[0];
    var fileReader = new FileReader();
    fileReader.readAsDataURL(cFile);
    fileReader.onload = function() {
        imgAvatarBase64 = fileReader.result;
        addAvatar(imgAvatarBase64);
    }
};


function download(data, filename, type) {
    var file = new Blob([data], {'type': type});
    if (window.navigator.msSaveOrOpenBlob) // IE10+
        window.navigator.msSaveOrOpenBlob(file, filename);
    else { // Others
        var a = document.createElement("a"),
                url = URL.createObjectURL(file);
        a.href = url;
        a.download = filename;
        document.body.appendChild(a);
        a.click();
        setTimeout(function() {
            document.body.removeChild(a);
            window.URL.revokeObjectURL(url);
        }, 0);
    }
}


function isNumberKey(evt){
    var charCode = (evt.which) ? evt.which : evt.keyCode
    return !(charCode > 31 && (charCode < 48 || charCode > 57));
}



function validateInputs(){
    var textArray = document.getElementsByClassName("text");
    var i=1;
    for(i;i<textArray.length;i++){
        var popup = document.getElementById("myPopup"+textArray[i].id);
        popup.setAttribute("class","popuptext");
        textArray[i].removeAttribute("required")
    }
    i=1;
    for(i;i<textArray.length;i++){
        if(textArray[i].id=="email") continue;
        if(checkTextToRightSymbol(textArray[i].value)===false){
            var popup = document.getElementById("myPopup"+textArray[i].id);
            textArray[i].required = "required";
            textArray[i].focus();
            popup.classList.toggle("show");
            return false;
        }
    }
    document.getElementById("email").removeAttribute("required")
    if(validateEmail(document.getElementById("email").value)==false) {
        var popup = document.getElementById("myPopupemail");
        popup.classList.toggle("show");
        document.getElementById("email").required = "required";
        document.getElementById("email").focus();
        return false;
    }
    return true;
}

function checkTextToRightSymbol(newValue){
    return /^[a-zA-Zа-яА-Я0-9]+$/.test(newValue);
}



function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}