/**
    params ＝ {
        data: {
            "sex": 1,
            "realName": "苗新国",
            "apartmentId": "",
            "apartmentName": ""
        }
    }
 */
define([], function() {

    var _ = {

        params: {},

        formValidater: null,

        URLs: {
            getApartments: '/team/apartments',

            updateUserInfo: '/user'
        },

        /** 页面初始化
         */
        init: function( params ){
            this.params = params;

            if( !this.restorePage() ){
                this.render();
                this.initValidator();
                this.initEvent();
            }
        },

        /** 获取tabs 并渲染 初始化tab事件
         */
        render : function(){
            var thisPage = this;
            if(thisPage.params.data.sex){
                thisPage.params.data.genderMale = thisPage.params.data.sex=='1';
            }
            window.HLJ.renderTemplate('modifyUserInfoForm_TPL', thisPage.params.data);

            /** 如果需要修改住址 获取宿舍楼列表 **/
            if( thisPage.params.data.apartmentId ){
                window.HLJ.get( this.URLs.getApartments ).done(function( ret ){
                    ret.apartments.forEach( function( item ){
                        if( item.id == thisPage.params.data.apartmentId ){
                            item.selected = "selected";
                            return true;
                        }
                    } );
                    window.HLJ.renderTemplate('apartmentOptions_TPL', {data: ret.apartments});
                }).fail(function( ret ){
                    window.HLJ.showAlert(ret);
                });
            }
        },

        initEvent: function(){
            var thisPage = this;

            var $body = $('body');

            $('.js-goback').on('click', function(){
                window.HLJ.viewGoBack();
            });

            $('.js-ok').on('click', function(){
                if( thisPage.formValidater.form() ){
                    thisPage.saveData( thisPage.getFormData() );
                }
            });
        },

        storePage: function(){
            this.params.pageBackup = document.createDocumentFragment();
            $('#modifyUserInfoPage').appendTo( this.params.pageBackup );
        },
        restorePage: function(){
            var ret = false;
            if( this.params.pageBackup && this.params.pageBackup.hasChildNodes() ){
                $("#modifyUserInfoPage").replaceWith( this.params.pageBackup );
                ret = true;
            }
            return ret;
        },

        initValidator: function(){
            // 表单验证
            this.formValidater = $("#modifyUserInfoForm").validate({
                ignore: ".ignore",  // 这里传入忽略验证的元素选择器  默认会忽略掉:hidden 这里重置该配置
                rules: {
                    realName: "required",
                    sex: "required",
                    mobilePhone: {
                        required: true,
                        phoneNumber: true
                    },
                    "apartment.id": "required"
                },
                messages: {
                    realName: "请输入姓名",
                    sex: "请选择性别",
                    mobilePhone: {
                        required: "请填写手机号码"
                    },
                    "apartment.id": "请选择专业"
                },
                groups: {
                    idCardPics: "idCardPics",
                    studentIdCardPics: "studentIdCardPics"
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
            $('#modifyUserInfoForm').find('input, select').each(function( i, ele){
                if( ele.value && ele.name ){
                    if( ele.type=="radio" ){
                        var $ele = $(ele);
                        if( $ele.prop('checked') ){
                            formData[ele.name] = ele.value;
                        }
                    } else {
                        formData[ele.name] = ele.value;
                    }
                }
            });
            return formData;
        },

        saveData: function( data ){
            window.HLJ.put( this.URLs.updateUserInfo, data ).done(function(){
                window.HLJ.showNotice("更新成功！");
                window.HLJ.viewGoBack(-1, {data: false});
            }).fail(function( ret ){
                window.HLJ.showAlert( ret );
            }).always(function(){
                window.HLJ.hideLoading();
            });
        }


    }

    return _;

});