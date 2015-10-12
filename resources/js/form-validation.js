function clearMessage(field){
    $("#" + field).removeClass('has-error').removeClass('has-warning').removeClass('has-success');
    $("#" + field + "-help").text("");
}

function displayMessages(messages){
    for(var i = 0; i < messages.length; i++)
        displayMessage(messages[i]);
}

function displayMessage(message){
    $("#" + message.field).addClass("has-"+message.type);
    $("#" + message.field + "-help").text(message.text);
}