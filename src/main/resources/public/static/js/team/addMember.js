/**
    params ＝ {
        pageBackup : document.createDocumentFragment(),
        major: {
            id:
            name:
        }
    }
 */
define([], function() {
    // 是否支持fileReader
    var isFileReaderSupport = !!window.FileReader;

    // 添加图片的表单验证
    $.validator.addMethod("addMemberPics", function( value, element ){
        var ret = false;
        $("input[name="+element.name+"]").each(function( i, ele ){
           if( !!ele.value ){
                ret = true;
                return true;
           }
        });
        return ret;
    }, '请添图片');

    $.validator.addMethod("fileSize5M", function( value, element ){
        var ret = true;
        $("input[name="+element.name+"]").each(function( i, ele ){
           if( ele.files[0] && ele.files[0].size > 5242880){
                // 5242880 = 1024*1024*5  5兆
                ret = false;
                return true;
            }
        });

        return ret;
    }, '图片大小必须小于5M');

    var _ = {

        params: {},

        formValidater: null,

        URLs: {
            getApartments: '/team/apartments',
            addMember: '/user',

            departmentSelectorPage: "public/departmentSelector.html"
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

            // 渲染专业
            if( this.params.major ){
                $('#majorName .js-major-name').text( this.params.major.name );
                $('input[name="major.id"]').val( this.params.major.id )
            }
            // 获取住所列表
            if( $('#apartmentId').children().size() <= 1 ){
                window.HLJ.get( this.URLs.getApartments ).done(function( ret ){
                    $('.js-add-member-school').text( ret.schoolName );
                    window.HLJ.renderTemplate('apartmentOptions_TPL', {data: ret.apartments});
                }).fail(function( ret ){
                    window.HLJ.showAlert(ret);
                });
            }
        },
        /** 获取tabs 并渲染 初始化tab事件
         */
        render : function(){
            var thisPage = this;

            // 身份证录入模版
            var idCardPics = {
                isFileReaderSupport: isFileReaderSupport,
                name: "idCardPics",
                ids: ["idCardPic1", "idCardPic2"]
            };
            var idCardPics_TPL = window.HLJ.renderTemplate('fileSelect_TPL', idCardPics, false);
            $('#idCardPics').html( idCardPics_TPL );

            // 学生证录入模版
            var studentIdCardPics = {
                isFileReaderSupport: isFileReaderSupport,
                name: "studentIdCardPics",
                ids: ["studentIdCardPic1", "studentIdCardPic2"]
            };
            var studentIdCardPics_TPL = window.HLJ.renderTemplate('fileSelect_TPL', studentIdCardPics, false);
            $('#studentIdCardPics').html( studentIdCardPics_TPL );

        },

        initEvent: function(){
            var thisPage = this;

            var $body = $('body');

            $('.js-goback').on('click', function(){
                window.HLJ.viewGoBack();
            });

            $('#majorName').on('click', function(){
                // 页面backup 用于备份当前页面 用于跳入二级选项页面后返回本页 恢复dom
                thisPage.storePage();
                // 跳转
                window.HLJ.view( thisPage.URLs.departmentSelectorPage );
            });

            $('input[type=file]').on('change', function(){
                var $thisDom = $(this);
                if( isFileReaderSupport ){
                    if(this.files.length){
                        var fr = new window.FileReader();
                        fr.onload = function( event ){
                            $thisDom.siblings('img').attr('src', event.target.result);
                        };
                        fr.onerror = function( event ){
                            window.HLJ.showAlert("图片加载失败了！");
                            $thisDom.siblings('img').attr('src', "");
                        };
                        fr.readAsDataURL( this.files[0] );
                    } else {
                        $thisDom.siblings('img').attr('src', "");
                    }
                }
                thisPage.formValidater.element( this );
            });

            $('.js-add-member-ok').on('click', function(){
                if( thisPage.formValidater.form() ){
                    window.HLJ.showLoading();
                    window.HLJ.ajax(
                        thisPage.URLs.addMember,
                        {
                            method: "post",
                            data: thisPage.getFormData(),
                            processData: false,
                            contentType : false
                        }
                    ).done(function(){
                        window.HLJ.showNotice( "小伙伴添加成功！" );
                        window.HLJ.viewGoBack();
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

        storePage: function(){
            this.params.pageBackup = document.createDocumentFragment();
            $('#addMemberPage').appendTo( this.params.pageBackup );
        },
        restorePage: function(){
            var ret = false;
            if( this.params.pageBackup && this.params.pageBackup.hasChildNodes() ){
                $("#addMemberPage").replaceWith( this.params.pageBackup );
                ret = true;
            }
            return ret;
        },

        initValidator: function(){
            // 表单验证
            this.formValidater = $("#addMemberForm").validate({
                ignore: ".ignore",  // 这里传入忽略验证的元素选择器  默认会忽略掉:hidden 这里重置该配置
                rules: {
                    realName: "required",
                    mobilePhone: {
                        required: true,
                        phoneNumber: true
                    },
                    idCardNo: {
                        required: true,
                        idCardNo: true
                    },
                    idCardPics: {
                        addMemberPics: true,
                        fileSize5M: true
                    },
                    studentIdCardNo: {
                        required: true,
                        studentIdCardNo: true
                    },
                    studentIdCardPics: {
                        addMemberPics: true,
                        fileSize5M: true
                    },
                    "apartment.id": "required"
                },
                messages: {
                    realName: "请输入姓名",
                    mobilePhone: {
                        required: "请填写手机号码"
                    },
                    idCardNo: {
                        required: "请填写身份证号"
                    },
                    idCardPics: {
                        addMemberPics: "请上传身份证照片"
                    },
                    studentIdCardNo: "请填写学生证号",
                    studentIdCardPics: {
                        addMemberPics: "请上传学生证照片"
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
            var formData = new FormData();
            $('#addMemberForm').find('input, select').each(function( i, ele){
                if( ele.value && ele.name ){
                    if( ele.type=="file" ){
                        ele.files.length && formData.append( ele.name, ele.files[0]);
                    } else {
                        formData.append( ele.name, ele.value);
                    }
                }
            });
            return formData;
        }


    }

    return _;

});