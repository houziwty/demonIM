/**
    params ＝ {
        date: ""
    }
 */
define([], function() {

    var _ = {
        params: {},

        pagination: {
            pageNum: 1,   // －1 表示不分页
            pageSize: 20
        },

        URLs: {
            scheduling: '/team/schedule'
        },

        /** 页面初始化
         */
        init: function( params ){
            this.params = params;
            this.params.date = this.params.date || window.HLJ.serverDate;

            this.render();
            this.initEvent();
        },
        /** 获取tabs 并渲染 初始化tab事件
         */
        render : function(){
            var thisPage = this;

            $('.js-current-date').text( this.params.date.format('yyyy-MM-dd,EE') );
            thisPage.getScheduling( this.params.date.format('yyyy-MM-dd'), 1);
        },

        initEvent: function(){
            var thisPage = this;

            $('.js-goback').on('click', function(){
                window.HLJ.viewGoBack();
            });

            $('#schedulingList').on('click', '.panel-heading', function(){
                var $thisDom = $(this);
                var target = $thisDom.data('collapseTarget');   // 缓存dom元素 实际测试 节省2～10ms
                if(!target){
                    target = $thisDom.siblings('.panel-collapse');
                    $thisDom.data('collapseTarget', target);
                }
                target.collapse('toggle');
                $thisDom.find('.caret').toggleClass('turn');
            });

            $("#schedulingList").on('click', '.js-next-page', function(){
                thisPage.getScheduling( thisPage.params.date.format('yyyy-MM-dd'), thisPage.pagination.pageNum+1 );
            });

            $('.js-pre-date').on("click", function(){
                var currentDate = thisPage.params.date;
                currentDate.setDate( currentDate.getDate()-1 );
                thisPage.switchDate('left', currentDate.format('yyyy-MM-dd,EE') );
                thisPage.getSchedulingLazy( currentDate.format('yyyy-MM-dd'), 1);
            });

            $('.js-next-date').on("click", function(){
                var currentDate = thisPage.params.date;
                currentDate.setDate( currentDate.getDate()+1 );
                thisPage.switchDate('right', currentDate.format('yyyy-MM-dd,EE') );
                thisPage.getSchedulingLazy( currentDate.format('yyyy-MM-dd'), 1);
            });

        },

        switchDate: function(direction, dateString){
            var $currentDate = $('.js-current-date');
            var animationEnd = window.HLJ.animationEnd;
            if( animationEnd ){
                var animateIn = ( direction == 'left' ) ? 'left-in' : 'right-in';
                var animateOut = ( direction == 'left' ) ? 'right-out' : 'left-out';

                $currentDate.siblings('.relace-backup').text( $currentDate.text() ).one( animationEnd, function(){
                    $(this).hide().removeClass( animateOut )
                }).show().addClass( animateOut );

                $currentDate.text( dateString ).one( animationEnd, function(){
                    $(this).removeClass( animateIn )
                }).addClass( animateIn );
            } else {
                $currentDate.text( dateString );
            }
        },

        getSchedulingLazy: (function(){
            var layzTask = null;
            return function( date, page ){
                var thisPage = this;
                if( layzTask ){
                    window.clearTimeout( layzTask );
                }

                layzTask = window.setTimeout(function() {
                    thisPage.getScheduling( date, page);
                    layzTask = null;
                }, 500);
            };

        })(),

        getScheduling: function( date, page ){
            var thisPage = this;

            window.HLJ.get(
                thisPage.URLs.scheduling,
                {
                    date: date,
                    pageNum: page || 1,
                    pageSize: thisPage.pagination.pageSize
                }
            ).done(function( ret ){
                thisPage.pagination.pageNum = page || 1;
                var isFirstPage = (thisPage.pagination.pageNum === 1);
                var pageContent = window.HLJ.renderTemplate(
                    'schedulingList_TPL',
                    {
                        data: ret.list,
                        pagination: {
                            next: parseInt(ret.total) > thisPage.pagination.pageNum*thisPage.pagination.pageSize
                        }
                    },
                    isFirstPage /* 是否自动添加到对应容器中 */
                );
                // 如果不是第一页   insert 到指定位置
                if( !isFirstPage ){
                    $('#schedulingList .js-next-page').replaceWith( pageContent );
                }

            }).fail(function( ret ){
                window.HLJ.showAlert( ret );
            });
        }
    }

    return _;

});