/**
    params ＝ {
    }
 */
define([], function() {

    var _ = {
        params: {},

        URLs: {
            apartmentList: '/user/address',

            editApartmentPage: 'public/editApartment.html'
        },

        /** 页面初始化
         */
        init: function( params ){
            this.params = params;
            this.render();
            this.initEvent();
        },

        render : function(){
            var thisPage = this;

            window.HLJ.get( this.URLs.apartmentList, {pageNum: -1, pageSize: -1} ).done(function( ret ){
                window.HLJ.renderTemplate('apartmentList_TPL', ret);
            }).fail(function( ret ){
                window.HLJ.showAlert(ret);
            });
        },

        initEvent: function(){
            var thisPage = this;

            $('.js-goback').on('click', function(){
                window.HLJ.viewGoBack();
            });

            $('.js-add-apartment').on('click', function(){
                window.HLJ.view( thisPage.URLs.editApartmentPage, {
                    action: "add",
                });
            });

            $('#apartmentList').on('click', '.js-modify-apartment', function(){
                window.HLJ.view( thisPage.URLs.editApartmentPage, {
                    action: "modify",
                    apartmentInfo: {
                        id: this.dataset.id,
                        name: this.dataset.name,
                        nick: this.dataset.nick,
                        persons: this.dataset.persons,
                        attr: this.dataset.attr
                    }
                });
            });
        }
    }

    return _;

});