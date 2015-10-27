/**
 * 去掉字符串前后的空格
 */
function trimAll(str) {
    return str.replace(/(^s*)|(s*$)/g, '');
};

/**
 * 去掉字符串前的空格
 */
function trimLeft(str) {
    return str.replace(/^s*/g, '');
};

/**
 * 去掉字符串后的空格
 */
function trimRight(str) {
    return str.replace(/s*$/, '');
}

/**
 * 判断字符串是否为空
 */
function isEmpty(str) {
    if (str != null && str.length > 0) {
        return true;
    }
    return false;
}

/**
 * 判断两个字符串子否相同
 */
function isEquals(str1, str2) {
    if (str1 == str2) {
        return true;
    }
    return false;
}

/**
 * 忽略大小写判断字符串是否相同
 */
function isEqualsIgnoreCase(str1, str2) {
    if (str1.toUpperCase() == str2.toUpperCase()) {
        return true;
    }
    return false;
}

/**
 * 判断是否是数字
 */
function isNum(value) {
    var reg = /^[0-9]*$/;
    return reg.test(value);
}

/**
 * 判断是否是系统允许输入的合法字符
 */
function isNormalChar(str) {
    var reg = /^[A-Za-z0-9_]+$/;
    return reg.test(str);
}

/**
 * 简单验证手机号
 */
function checkMobile(str) {
   var re = /^1\d{10}$/
   return re.test(str);
}


/** 全局common 命名空间
 */
(function(){

    window.HLJ = {

        pageContainer: $("#content"),
        pageAnimateBackup: $("#contentAnimateBackup"),

        /** 全局loading
         */
        loading: (function(){
            var $loading = $(".global-loading");
            return $loading;
        })(),
        showLoading: function(){
            this.loading.show();
        },
        hideLoading: function(){
            this.loading.hide();
        },
        /**
         *  该属性 获取最后一次服务器请求的 header date  （只能确定当前日期的服务器时间）
         */
        serverDate: new Date(),

        /** 渲染模版
         * @param domID 模版id
         * @param data 数据
         * @param autoAppend  自动添加到对应容器  default : true
         */
        renderTemplate: function(domID, data, autoAppend){
            var append = typeof(autoAppend)==='boolean' ? autoAppend : true;
            var $template = $('#'+domID);
            if( $template.size() ){
                var rendered = Mustache.render($template.html(), data);
                var parentID = $template.data('parent');
                if(parentID && append){
                    $('#'+parentID).html(rendered);
                }
                return rendered;
            }
        },

        /** 全局提示
         */
        globalNotice: (function(){
            var $globalNotice = $('#hlj_global_notice');
            return $globalNotice;
        })(),
        showAlert: function( msg ){
            var alertDom = this.globalNotice.find('.alert-danger');
            var self = this;
            alertDom.html( msg ).fadeIn();
            if(self.alertTimer){
                window.clearTimeout(self.alertTimer);
            }
            self.alertTimer = window.setTimeout(function(){
                alertDom.html( msg ).fadeOut();
                self.alertTimer = null;
            }, 2000);
        },
        showNotice: function( msg ){
            var alertDom = this.globalNotice.find('.alert-success');
            var self = this;
            alertDom.text( msg ).fadeIn();
            if(self.noticeTimer){
                window.clearTimeout(self.noticeTimer);
            }
            self.noticeTimer = window.setTimeout(function(){
                alertDom.text( msg ).fadeOut();
                self.noticeTimer = null;
            }, 2000);
        },

        /** 获取viewStack  临时代替路由功能  保持页面状态
         * return [{
                        url: 'sss/sss',
                        params: {}
                   }]
         */
        getViewStack: function(){
            this.viewStack = window.HLJ.viewStack || [];
            return this.viewStack;
        },

        viewClearAll: function(){
            this.getViewStack().length = 0;
        },

        view: function(viewUrl, params){
            var self = this;

            var view = {
                url: viewUrl,
                params: params || {},
                animate: 'right'
            };
            this.getViewStack().push(view);
            if(window.location.hash.slice(1) == view.url){
                this.loadView(null, view.url, view.params);
            } else {
                window.location.assign('#'+view.url);
            }
        },

        /**
         *   回退
         *   @params index  history数 必须为负数
         */
        viewGoBack: function( index, params ){
            var self = this;

            if( typeof(index)!='number' || index >= 0 ){
                index = -1;
            }
            var viewStack = this.getViewStack();
            if( viewStack.length > Math.abs(index) ){
                viewStack.length += index;
                var view = viewStack[viewStack.length-1];
                view.animate = 'left';
                params && $.extend(true, view.params, params);
                window.history.back();
            }

        },

        /** 加载一个page
           @param  container  加载到目标父容器
           @param  viewUrl    url.html
           @param  params 加载时的参数
           @param  animate  ‘left’ 'right'  当没有指定container时 执行切换动画效果
         */
        loadView: function( container, viewUrl, params, animate ){
            var self = this;
            var dependencies = [
                "text!/html/" + viewUrl,
                viewUrl.replace(".html", '')
            ];
            if( container ){
                require(dependencies, function( html, page ){
                    $(container).html( html );
                    page.init( params );
                }, function( err ){
                    var failedId = err.requireModules && err.requireModules[0];
                    requirejs.undef(failedId);
                    self.showAlert("啊哦！页面加载出错了！<br>" + failedId);
                });
            } else {
                var animateIn = ( animate == 'left' ) ? 'left-in' : 'right-in';
                var animateOut = ( animate == 'left' ) ? 'right-out' : 'left-out';

                self.showLoading();

                // 页面结束动画
                // 如果支持 webkit 和 原生动画
                if( self.animationEnd ){
                    self.pageContainer.children().appendTo( self.pageAnimateBackup );
                    self.pageAnimateBackup.one( self.animationEnd, function(){
                        self.pageAnimateBackup.hide().empty().removeClass( animateOut );
                    }).show().addClass( animateOut );
                }

                require(dependencies, function( html, page ){
                    // 判断动画是否支持
                    if( self.animationEnd ){
                        self.pageContainer.html( html );
                        self.pageContainer.one( self.animationEnd, function(){
                            self.pageContainer.removeClass( animateIn );
                            self.hideLoading();
                        }).addClass( animateIn );
                    } else {
                        self.pageContainer.html( html );
                        self.hideLoading();
                    }
                    page.init( params );
                }, function( err ){
                    var failedId = err.requireModules && err.requireModules[0];
                    requirejs.undef(failedId);
                    self.hideLoading();
                    self.showAlert("啊哦！页面加载出错了！<br>" + failedId);
                });
            }
        },

        getViewParams: function(){
            var viewStack = this.getViewStack();
            return viewStack[viewStack.length-1].params;
        },

        animationEnd: (function(){
            var t;
            var el = document.createElement('fakeelement');
            var transitions = {
              'animation':'animationend',
              'webkitAnimation':'webkitAnimationEnd'
            }

            for(t in transitions){
                if( el.style[t] !== undefined ){
                    var ret = transitions[t];
                    return ret;
                }
            }
        })(),

        /**
            根据业务封装的ajax请求
         */
        get: function(url, params){
            return this.ajax(url, {method: 'GET', data: params});
        },

        post: function(url, params){   // get 和 post 分开写。如果出现特殊post处理 该方法可修改 而不影响get
            return this.ajax(url, {method: 'POST', data: params});
        },

        put: function(url, params){   // get 和 post 分开写。如果出现特殊post处理 该方法可修改 而不影响get
            return this.ajax(url, {method: 'PUT', data: params});
        },

        ajax: function(url, settings){
            var HLJ = this;
            var HLJAPP = this;
            var deffered = $.Deferred();
            var _settings = {};
            if( settings.method.toLowerCase == 'get'){
                _settings.timeout = 3000;
            }

            url = url + '?' + $.param({ token: (window.localStorage || window.sessionStorage).getItem('token') });

            $.ajax(url, $.extend(true, _settings, settings) ).done(function( ret, textStatus, jqXHR ){
                HLJAPP.serverDate = new Date( jqXHR.getResponseHeader('date') );
                if( ret.success ){
                    deffered.resolve( ret.msg, jqXHR );
                } else {
                    if( ret.code && ret.code== '401'){
                        HLJ.showDialog({content: "登录状态失效，请重新登录！"}).always(function(){
                            window.location.replace('/login.html');
                        });
                    }
                    deffered.reject( ret.msg, jqXHR );
                }
            }).fail(function( ret ){
                deffered.reject( ret.statusText );
            })
            return deffered.promise();
        },

        /** 全局弹窗
            options = {
                type: "confirm/prompt",
                title: "xxxx",
                content: ""
            }
         */
        showDialog: (function(){
            var $globalDialog = $('#hlj_global_dialog');

            return function( options ){
                var deffered = $.Deferred();
                var _options = {
                    type: "confirm",
                    title: "确认该操作吗？",
                    content: ""
                };
                $.extend(_options, options);
                this.renderTemplate("hlj_global_dialog_"+_options.type+"_TPL", _options);
                $globalDialog.one('click', '.js-dialog-ok, .js-dialog-cencel', function( event ){
                    if( event.target.classList.contains('js-dialog-ok') ){
                        deffered.resolveWith( $globalDialog );
                    } else {
                        deffered.rejectWith( $globalDialog );
                    }
                });
                $globalDialog.modal('show');
                return deffered.promise();
            };
        })()
    };

    // hash 路由页面
    window.addEventListener('hashchange', function(){
        var viewUrl = this.location.hash.slice(1);
        var viewStatck = this.HLJ.getViewStack();
        var index = viewStatck.length;
        var view = null
        while(--index>=0){
            if(viewStatck[ index ].url == viewUrl){
                view = viewStatck[ index ];
                break;
            }
        }
        if(view){
            viewStatck.length = (index+1);
            this.HLJ.loadView(null, view.url, view.params, view.animate);
        } else {
            window.location.assign('#'+viewStatck[viewStatck.length-1].url);
        }
    });

    // 添加表单验证方法
    $.validator.addMethod("phoneNumber", function( value, element ){
        element.value = $.trim( value );
        return /^1\d{10}$/.test( element.value );
    }, '请填写正确的手机号！');

    $.validator.addMethod("idCardNo", function( value, element ){
        element.value = $.trim( value );
        return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test( element.value );
    }, '请填写正确的身份证号！');

    $.validator.addMethod("studentIdCardNo", function( value, element ){
        element.value = $.trim( value );
        return /^[0-9a-zA-Z]*$/.test( element.value );
    }, '只能录入数字和字母！');

    /** 日期格式化处理函数
     */
    Date.prototype.format=function(fmt) {
        var o = {
            "M+" : this.getMonth()+1, //月份
            "d+" : this.getDate(), //日
            "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时
            "H+" : this.getHours(), //小时
            "m+" : this.getMinutes(), //分
            "s+" : this.getSeconds(), //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S" : this.getMilliseconds() //毫秒
        };
        var week = {
            "0" : "日",
            "1" : "一",
            "2" : "二",
            "3" : "三",
            "4" : "四",
            "5" : "五",
            "6" : "六"
        };
        if(/(y+)/.test(fmt)){
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        if(/(E+)/.test(fmt)){
            fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "星期" : "周") : "")+week[this.getDay()+""]);
        }
        for(var k in o){
            if(new RegExp("("+ k +")").test(fmt)){
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            }
        }
        return fmt;
    }
})();




