/**
    params ＝ {
        allowModify: true;
        username: thisPage.params.username
    }
 */
define([], function() {

    var _ = {
        params: null,

        URLs: {
            otherRangeList: '/team/user/setting/addr',
            selfRangeList: '/user/setting/addr',

            saveRangeList: "/user/setting/addr"
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

            // title渲染
            window.HLJ.renderTemplate('dutyRangePageTitle_TPL', thisPage.params);

            window.HLJ.get(
                (thisPage.params.username ? thisPage.URLs.otherRangeList : thisPage.URLs.selfRangeList),
                {
                    username: thisPage.params.username
                }
            ).done(function( ret ){
                var _map = {};
                ret.apartments.forEach( function( item ){
                    _map[ item.id ] = item;
                } );
                ret.selectedApartments.split(',').forEach( function( id ){
                    _map[ id ] && (_map[ id ].checked = true);
                } );
                window.HLJ.renderTemplate('rangeList_TPL', {data: ret.apartments});
            }).fail(function( ret ){
                window.HLJ.showAlert(ret);
            });
        },

        initEvent: function(){
            var thisPage = this;

            var $rangeList = $("#rangeList");
            var $statusSwitcher = $('.js-range-modify');

            $('.js-goback').on('click', function(){
                window.HLJ.viewGoBack();
            });

            $statusSwitcher.on('click', function(){
                var $thisDom = $(this);
                if( $thisDom.hasClass('checked') ){
                    thisPage.saveRangeList( thisPage.getSelectedIds() ).done( function(){
                        $thisDom.removeClass('checked');
                        $('#rangeList .item-container').removeClass('editable');
                    });

                } else {
                    $thisDom.addClass('checked');
                    $('#rangeList .item-container').addClass('editable');
                }
            });

            $rangeList.on('click', '.js-apartment-item', function(){
                if( $statusSwitcher.hasClass('checked') ){
                    $(this).toggleClass('checked');
                }
            });
        },

        getSelectedIds: function(){
            var ids = [];
            $('.js-apartment-item.checked').each( function(i, item){
                ids.push( item.dataset['id'] );
            });
            return ids;
        },

        saveRangeList: function( rangeList ){
            window.HLJ.showLoading();

            return  window.HLJ.put(
                        this.URLs.saveRangeList,
                        {
                            addrs: rangeList.join(',')
                        }
                    ).done(function( ret ){
                        window.HLJ.showNotice("保存成功！");
                    }).fail(function( ret ){
                        window.HLJ.showAlert(ret);
                    }).always( function(){
                        window.HLJ.hideLoading();
                    });
        }
    }

    return _;

});