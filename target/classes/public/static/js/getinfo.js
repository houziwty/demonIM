$(document).ready(function() {
    $("#login-form").ajaxForm({
        beforeSubmit: function(formData, jqForm, options){
            $("#login-form p").text("");
            
           
            //formData[1].value = hex_sha1( $("#loginform-password").val() );
        },
        success: function(data){  

            if(!data.returnCode){

                $("p.text-danger:last").text(data.body.id+":"+data.body.userName);
                return false;
            } else {
               // window.navigate(data.body); 
               debugger;
              $("p.text-danger:last").text(data.body.id+":"+data.body.userName);
                //(window.localStorage || window.sessionStorage).setItem('token', data.msg);
                //window.location.replace("/html/main.html");
            }
        },
        dataType: 'json'
    });

    // 事件 - 回车
    document.onkeydown = function(e){
        var ev = document.all ? window.event : e;
        if(ev.keyCode==13) {
               $('#btnSubmit').click();
         }
    }
});