/**
    params ＝ {
        title: "",
        cardNum: "",
        pics: [url, url]
    }
 */
define([ '/static/photoSwipe/photoswipe.min.js', '/static/photoSwipe/photoswipe-ui-default.min.js'], function( PhotoSwipe, PhotoSwipeUI_Default ) {

    var _ = {
        params: {},

        URLs: {},

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
            $('.js-header-text').text( this.params.title||'证件查看' );
            $('.js-cardNum').text( this.params.cardNum||'无' );
            this.params.pics.map( function( i, item ){
                item.index = i;
                return item;
            } );
            window.HLJ.renderTemplate('pics_TPL', this.params.pics);
        },

        initEvent: function(){
            var thisPage = this;

            $('.js-goback').on('click', function(){
                window.HLJ.viewGoBack();
            });

            $('#pics').on('click', 'a', function(){
                thisPage.openPhotoSwipe( this.dataset['index'] );
            });
        },

        openPhotoSwipe: function( index ){
            var pswpElement = document.querySelectorAll('.pswp')[0];
                // build items array
                var items = [];
                $('.js-img').each(function( i, ele ){
                    items.push(
                        {
                            src: ele.src,
                            w:  ele.width,
                            h:  ele.height
                        }
                    );
                });

                // define options (if needed)
                var options = {
                    index: index || 0,
                    // history & focus options are disabled on CodePen
                    history: false,
                    focus: false,

                    showAnimationDuration: 100,
                    hideAnimationDuration: 100

                };

                var gallery = new PhotoSwipe( pswpElement, PhotoSwipeUI_Default, items, options);
                gallery.init();
        }
    }

    return _;

});