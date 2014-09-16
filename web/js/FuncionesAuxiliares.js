function peticionAjax(url,tagParam,divCarga,param){
    jQuery(document).ready(function(){
         var valorParam = jQuery('#'+tagParam).val();
         var urlfinal = url+"?"+param+"="+valorParam;
         jQuery('#'+divCarga).load(urlfinal);
    });
}

function dialogoConfirmacionBorrar(mensajeMostrar,tituloVentana,idTag,attrName,url,valueTrue,valueFalse){
    jConfirm(mensajeMostrar,tituloVentana, function(confirmacion) {
        if(confirmacion){
            url+=valueTrue;
            location.href=url;
        }
    });
}

function dialogoFormBorrar(idTag,urlLoad,tituloVentana,idTagFormulario){
    jQuery(idTag).attr("href","#");

    jQuery(idTagFormulario).dialog({
        disable:true,
        autoOpen: true,
        modal: false,
        draggable: true,
        resizable: false,
        width:550,
        show:'slide',
        position: 'top',
        title: tituloVentana
    });

    jQuery(idTagFormulario).load(urlLoad);
    jQuery(idTagFormulario).dialog("open");

    return false;
}

function seleccionarFecha(){
    jQuery('.date-pick').datePicker({clickInput:true})
}