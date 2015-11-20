/**
    params ＝ {
        opwd: ''   // 原密码
        type: 'login' || 'withdrawals'
    }
 */
define([], function() {

    // 密码验证
    $.validator.addMethod("password", function( value, element ){
        element.value = $.trim(value);
        return /^[A-Za-z0-9@_]{6,}$/.test( element.value );
    }, '至少6位，只包含：数字、字母、“_”、“@”');

    var _ = {

        params: {},

        formValidater: null,

        URLs: {
            saveLogin: "/user/loginPwd",
            saveWithdrawal: "/user/withdrawalPwd"
        },

        /** 页面初始化
         */
        init: function( params ){
            this.params = params;

            if( this.params.opwd && ( this.params.type=='login' || this.params.type=='withdrawals' ) ){
                this.render();
                this.initValidator();
                this.initEvent();
            } else {
                window.HLJ.viewGoBack();
            }
        },
        /** 获取tabs 并渲染 初始化tab事件
         */
        render : function(){
            var thisPage = this;

            window.HLJ.renderTemplate('changePWDForm_TPL', thisPage.params);
        },

        initEvent: function(){
            var thisPage = this;

            var $body = $('body');

            $('.js-goback').on('click', function(){
                window.HLJ.viewGoBack();
            });

            $('.js-ok').on('click', function(){
                if( thisPage.formValidater.form() ){
                    window.HLJ.showLoading();
                    window.HLJ.put(
                        thisPage.params.type=='login' ? thisPage.URLs.saveLogin : thisPage.URLs.saveWithdrawal,
                        thisPage.getFormData()
                    ).done(function(){
                        window.HLJ.showNotice( "密码修改成功！" );
                        if( thisPage.params.type=='login' ) {
                            (window.localStorage || window.sessionStorage).removeItem('token');
                            window.location.replace('/login.html');
                        } else {
                            window.HLJ.viewGoBack(-1, {data: null});
                        }
                    }).fail(function( ret ){
                        window.HLJ.showAlert( ret );
                    }).always(function(){
                        window.HLJ.hideLoading();
                    });
                } else {
                    $body.animate({'scrollTop': $('.has-error').offset().top})
                }
            });
        },

        initValidator: function(){
            // 表单验证
            this.formValidater = $("#changePWDForm").validate({
                rules: {
                    newPwd: {
                        required: true,
                        password: true
                    },
                    reNewPwd: {
                        required: true,
                        password: true,
                        equalTo: "#newPwd"
                    }
                },
                messages: {
                    newPwd: {
                        required: "请输入新密码"
                    },
                    reNewPwd: {
                        required: "请确认新密码",
                        equalTo: "两次密码不一致！"
                    }
                },
                errorElement: 'span',
                errorPlacement: function(error, element){
                    element.parents('.form-group').find('.js-form-error').html( error );
                },
                highlight: function( element ){
                    $(element).parents('.form-group').addClass('has-error');
                },
                unhighlight: function( element ){
                    $(element).parents('.form-group').removeClass('has-error');
                }
            });
        },

        getFormData: function(){
            var formData = {};
            $('#changePWDForm').find('input').each(function( i, ele){
                if( ele.value && ele.name ){
                    ele.value!='null' && (formData[ele.name] = hex_sha1(ele.value));
                }
            });
            return formData;
        }


    }

    return _;

});