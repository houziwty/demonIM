/**
    params ＝ {
    }
 */
define([], function() {

    var _ = {
        params: {},

        URLs: {},

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
        },

        initEvent: function(){
            var thisPage = this;
            // 为标签页添加点击事件
            $("#teamMenu").on('click', '.list-group-item', function(){
                window.HLJ.view( this.dataset.url );
            });
        }
    }

    return _;

});