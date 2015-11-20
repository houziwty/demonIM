/**
    params ＝ {
        action: "modify",
        apartmentInfo: {
            id: this.dataset.id,
            name: this.dataset.name,
            nick: this.dataset.nick,
            persons: this.dataset.persons,
            attr: this.dataset.attr
        }
    }
 */
define([], function() {

    var _ = {

        params: {},

        formValidater: null,

        URLs: {
            save: '/request/maintenance'
        },

        /** 页面初始化
         */
        init: function( params ){
            this.params = params;

            this.render();
            this.initValidator();
            this.initEvent();
        },
        /** 获取tabs 并渲染 初始化tab事件
         */
        render : function(){
            var thisPage = this;

            var title = thisPage.params.action == 'add' ? '添加楼栋' : '修改楼栋';
            $('#editApartmentTitle').text( title );

            window.HLJ.renderTemplate('editApartmentForm_TPL', thisPage.params.apartmentInfo || {});
        },

        initEvent: function(){
            var thisPage = this;

            var $body = $('body');

            $('.js-goback').on('click', function(){
                window.HLJ.viewGoBack();
            });

            $('.js-edit-ok').on('click', function(){
                if( thisPage.formValidater.form() ){
                    window.HLJ.showLoading();
                    var method = (thisPage.params.action == 'add') ? 'post' : 'put';
                    window.HLJ[ method ](
                        thisPage.URLs.save,
                        thisPage.getFormData()
                    ).done(function(){
                        var noticeStr = ((thisPage.params.action == 'add') ? '添加' : '修改') + "成功！";
                        window.HLJ.showNotice( noticeStr );
                        window.HLJ.viewGoBack();
                    }).fail(function( ret ){
                        window.HLJ.showAlert( ret );
                    }).always(function(){
                        window.HLJ.hideLoading();
                    });
                }
            })

        },

        initValidator: function(){
            // 表单验证
            this.formValidater = $("#editApartmentForm").validate({
                ignore: ".ignore",  // 这里传入忽略验证的元素选择器  默认会忽略掉:hidden 这里重置该配置
                rules: {
                    name: "required",
                    persons: {
                        digits: true
                    }
                },
                messages: {
                    name: "请输入名称",
                    persons: {
                        digits: "只能为正整数",
                        number: "只能为正整数"
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
            $('#editApartmentForm').find('input, select').each(function( i, ele){
                if( ele.value && ele.name ){
                    formData[ele.name] = ele.value;
                }
            });
            return formData;
        }


    }

    return _;

});