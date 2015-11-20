/**
    params ＝ {
        callback: callback
    }
 */
define([], function() {

    var _ = {
        params: null,

        URLs: {
            departmentList: '/user/department',

            majorSelectorPage: "public/majorSelector.html"
        },

        /** 页面初始化
         */
        init: function( params ){
            this.params = params;

            if( this.params.pageBackup && this.params.pageBackup.hasChildNodes() ){
                $("#departmentSelectorPage").replaceWith( this.params.pageBackup );
            } else {
                this.render();
                this.initEvent();
            }

        },
        /** 获取tabs 并渲染 初始化tab事件
         */
        render : function(){
            var thisPage = this;

            window.HLJ.get( this.URLs.departmentList ).done(function( ret ){
                window.HLJ.renderTemplate('departmentList_TPL', {data: ret});
            }).fail(function( ret ){
                window.HLJ.showAlert(ret);
            });
        },

        initEvent: function(){
            var thisPage = this;

            $('.js-goback').on('click', function(){
                window.HLJ.viewGoBack();
            });

            $("#departmentList").on('click', '.js-department-item', function(){
                // 页面backup 用于备份当前页面 用于跳入二级选项页面后返回本页 恢复dom
                thisPage.params.pageBackup = document.createDocumentFragment();
                $('#departmentSelectorPage').appendTo( thisPage.params.pageBackup );

                window.HLJ.view( thisPage.URLs.majorSelectorPage, {
                    departmentId: this.dataset.id,
                    callback: thisPage.params ? thisPage.params.callback : null
                } );
            });
        }
    }

    return _;

});