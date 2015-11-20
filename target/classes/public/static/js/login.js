$(document).ready(function() {
    var isSubmit=false;
    
    $("#login-form").ajaxForm({
        beforeSubmit: function(formData, jqForm, options){
            $("#login-form p").text("");
            var username = formData[0].value;
            if(!isEmpty(trimAll(username))){
                $("#loginform-username").next("p").text("请填写登录帐号");
                return false;
            }
            var password = formData[1].value
            if(!isEmpty(trimAll(password))){
                $("#loginform-password").next("p").text("请填写密码");
                return false;
            }
            //formData[1].value = hex_sha1( $("#loginform-password").val() );
        },
        success: function(data){  
            isSubmit=false;
            if(data.code){
                $("p.text-danger:last").text(data.body);
                return false;
            } else {
               // window.navigate(data.body);  
                window.location.href=data.body;
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