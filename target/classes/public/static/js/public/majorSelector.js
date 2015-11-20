/**
    params ＝ {
        departmentId: "",
        callback
    }
 */
define([], function() {

    var _ = {
        params: null,

        URLs: {
            majorList: '/user/major'
        },

        /** 页面初始化
         */
        init: function( params ){
            this.params = params;

            this.initEvent();
            this.render();
        },
        /** 获取tabs 并渲染 初始化tab事件
         */
        render : function(){
            var thisPage = this;

            window.HLJ.get( this.URLs.majorList, { departmentId: thisPage.params.departmentId } ).done(function( ret ){
                window.HLJ.renderTemplate('majorList_TPL', {data: ret});
            }).fail(function( ret ){
                window.HLJ.showAlert(ret);
            });
        },

        initEvent: function(){
            var thisPage = this;

            $('.js-goback').on('click', function(){
                window.HLJ.viewGoBack();
            });

            $("#majorList").on('click', '.js-major-item', function(){
                var pageParams = {
                    major: {
                        id: this.dataset.id,
                        name: this.dataset.name
                    }
                };
                if( thisPage.params.callback ){
                    thisPage.params.callback( pageParams );
                } else {
                    window.HLJ.viewGoBack( -2, pageParams );
                }
            });
        }
    }

    return _;

});