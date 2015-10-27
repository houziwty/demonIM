/**
    params ＝ {
        id: "用户id",
        username: "useranem"
    }
 */
define([], function() {

    var _ = {
        params: {},

        URLs: {
            detail: '/team/user',

            rangePage: 'public/dutyRange.html',
            workDayPage: 'public/workDay.html'
        },

        /** 页面初始化
         */
        init: function( params ){
            $.extend( this.params, params);
            this.render();
            this.initEvent();
        },
        /** 获取tabs 并渲染 初始化tab事件
         */
        render : function(){
            var thisPage = this;
            window.HLJ.get( this.URLs.detail, {id: thisPage.params.id} ).done(function( ret ){
                window.HLJ.renderTemplate('menberDetail_TPL', ret);
                window.HLJ.renderTemplate('contactLink_TPL', ret);
            }).fail(function( ret ){
                window.HLJ.showAlert(ret);
            });
        },

        initEvent: function(){
            var thisPage = this;

            $('.js-goback').on('click', function(){
                window.HLJ.viewGoBack();
            });

            $('.js-member-scheduling').on('click', function(){
                window.HLJ.view( thisPage.URLs.workDayPage, {
                    allowModify: false,
                    username: thisPage.params.username
                });
            });

            $('.js-member-range').on('click', function(){
                window.HLJ.view( thisPage.URLs.rangePage, {
                    allowModify: false,
                    username: thisPage.params.username
                });
            });
        }
    }

    return _;

});