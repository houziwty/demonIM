/**
    params ＝ {
        key: "联系人关键词"
    }
 */
define([], function() {

    var _ = {
        params: {
            key: undefined
        },

        pagination: {
            pageNum: 1,
            pageSize: 20
        },

        URLs: {
            memberList: '/user/teamMembers',

            memberDetailPage: 'team/memberDetail.html',
            addMemberPage: 'team/addMember.html'
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
            if(thisPage.params.key){
                $(".js-member-suggest").val( thisPage.params.key );
            }
            thisPage.getSuggest();
        },

        initEvent: function(){
            var thisPage = this;

            $('.js-goback').on('click', function(){
                window.HLJ.viewGoBack();
            });
            $('.js-add-member').on('click', function(){
                window.HLJ.view( thisPage.URLs.addMemberPage );
            });

            $(".js-member-suggest").on("input", function(event){ // suggest 输入事件
                var keyWords = $.trim( $(this).val() );
                keyWords = keyWords || undefined;
                if( thisPage.getSuggest.requestDelay ){
                    window.clearTimeout( thisPage.getSuggest.requestDelay );
                }
                thisPage.getSuggest.requestDelay = setTimeout(function() {
                    thisPage.params.key = keyWords;
                    thisPage.getSuggest();
                }, 600);
            });

            $("#menberList").on('click', '.js-next-page', function(){
                thisPage.getSuggest( thisPage.pagination.pageNum+1 );
            });

            $("#menberList").on('click', '.js-member-item', function(){
                window.HLJ.view( thisPage.URLs.memberDetailPage, {id: this.dataset.userid, username: this.dataset.username} );
            });
        },

        getSuggest: function( page ){
            var thisPage = this;

            thisPage.pagination.pageNum = page || 1;

            window.HLJ.get(
                this.URLs.memberList,
                {
                    name: thisPage.params.key,
                    pageNum: thisPage.pagination.pageNum,
                    pageSize: thisPage.pagination.pageSize
                }
            ).done(function( ret ){
                var firstPage = (thisPage.pagination.pageNum === 1);
                var pageContent = window.HLJ.renderTemplate(
                    'menberList_TPL',
                    {
                        data: ret.list,
                        pagination: {
                            next: parseInt(ret.total) > thisPage.pagination.pageNum*thisPage.pagination.pageSize
                        }
                    },
                    firstPage /* 是否自动添加到对应容器中 */
                );
                // 如果不是第一页   insert 到指定位置
                if( !firstPage ){
                    $('#menberList .js-next-page').replaceWith( pageContent );
                }

            }).fail(function( ret ){
                window.HLJ.showAlert( ret );
            });
        }
    }

    return _;

});