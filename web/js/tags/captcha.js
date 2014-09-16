function generateText(){
    var captcha = $("#captcha"); 
    captcha.children("img:first").remove();
    var timestamp=(new Date()).getTime();
    captcha.prepend('<img src="/laguantera/stickyImg.png?action=reset&timestamp='+timestamp+'" alt="captcha" />');
}

function changeToText(){
    var captcha = $("#captcha");
    captcha.children("audio:first").remove();
    var timestamp=(new Date()).getTime();
    captcha.prepend('<img src="/laguantera/stickyImg.png?action=reset&timestamp='+timestamp+'" alt="captcha" />');
    
    //Poner Boton cambio a audio
    var auxDiv = $("#auxDiv");
    
    //Cambio de imagen TEXT
    var buttonRecargar = $("#recargar",auxDiv);
    buttonRecargar.attr("onclick","generateText()");
    
    //Cambio Boton 
    var buttonCambio = $("#cambiar",auxDiv);
    buttonCambio.attr("onclick","changeToAud()");
    
    //Cambio img
    var img = $("img", buttonCambio);
    img.attr("src", "./img/tags/captcha/cambioAudio.png");
}

function generateAudio(){
    var captcha = $("#captcha"); 
    captcha.children("audio:first").remove();
    var timestamp=(new Date()).getTime();
    captcha.prepend('<audio type="audio/wav" src="/laguantera/audio.wav?action=reset&timestamp='+timestamp+'" controls="true" tabindex="0"></audio>');
}

function changeToAud(){
    var captcha = $("#captcha");
    captcha.children("img:first").remove();
    var timestamp=(new Date()).getTime();
    captcha.prepend('<audio type="audio/wav" src="/laguantera/audio.wav?action=reset&timestamp='+timestamp+'" controls="true" tabindex="0"></audio>');
    
    //Poner el Boton Cambiar a text
    var auxDiv = $("#auxDiv");
    
    //Cambio de imagen TEXT
    var buttonRecargar = $("#recargar",auxDiv);
    buttonRecargar.attr("onclick","generateAudio()");
    
    //Cambio Boton 
    var buttonCambio = $("#cambiar",auxDiv);
    buttonCambio.attr("onclick","changeToText()");
    
    //Cambio img
    var img = $("img", buttonCambio);
    img.attr("src", "./img/tags/captcha/cambioTexto.png");
}
