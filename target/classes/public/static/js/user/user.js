/**
    params ＝ {
    }
 */
define([], function() {

    var _ = {
        params: {},

        dataCache: {},

        URLs: {
            detail: '/user/info',
            logout: '/logout',

            userInfoPage: "user/userInfo.html",

            rangePage: 'public/dutyRange.html',
            workDayPage: 'public/workDay.html',
            apartmentListPage: 'public/apartmentList.html'
        },

        /** 页面初始化
         */
        init: function( params ){
            this.params = params;
            this.render();
            this.initEvent();
        },
        /** 获取tabs 并渲染 初始化tab事件
         */
        render : function(){
            var thisPage = this;
            window.HLJ.get( this.URLs.detail ).done(function( ret ){
            	thisPage.dataCache = ret;
                ret.userInfo.url = ret.userInfo.url || '/static/img/default-m.gif';
                ret.userInfo.isBDM = !!ret.userInfo.roles && ret.userInfo.roles.some(function(role){return role.id=='2';});
                if( ret.userInfo.isBDM ){
                    $('.js-user-school').removeClass('hide');
                }
                window.HLJ.renderTemplate('userInfo_TPL', ret.userInfo);
                window.HLJ.renderTemplate('accountInfo_TPL', ret);
            }).fail(function( ret ){
                window.HLJ.showAlert(ret);
            });
        },

        initEvent: function(){
            var thisPage = this;

            $('.js-goback').on('click', function(){
                window.HLJ.viewGoBack();
            });

            $('#userInfo').on('click', function(){
                window.HLJ.view( thisPage.URLs.userInfoPage, {
                    data: thisPage.dataCache
                });
            });

            // 配送时间
            $('.js-user-scheduling').on('click', function(){
                window.HLJ.view( thisPage.URLs.workDayPage, {
                    allowModify: true,
                });
            });

            // 配送范围
            $('.js-user-range').on('click', function(){
                window.HLJ.view( thisPage.URLs.rangePage, {
                    allowModify: false
                });
            });

            // 校园信息
            $('.js-user-school').on('click', function(){
                window.HLJ.view( thisPage.URLs.apartmentListPage );
            });

            // 意见反馈
            $('.js-user-feedback').on('click', function(){
                window.HLJ.showNotice('该功能被PM吃了！');
            });

            // 退出登录
            $('.js-logout').on('click', function(){
                window.HLJ.post( thisPage.URLs.logout ).done(function( ret ){
                    (window.localStorage || window.sessionStorage).removeItem('token');
                    window.location.replace('/login.html');
                }).fail(function( ret ){
                    window.HLJ.showAlert(ret);
                });
            });
        }
    }

    return _;

});