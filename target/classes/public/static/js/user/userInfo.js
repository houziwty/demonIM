/**
    同请求结果
    params ＝ {
        data: {
            "userInfo": {
                "apartment": {
                            "id": 4,
                            "name": "仲英书院东4号公寓楼",
                            "nick": "东2",
                            "persons": 0,
                            "school": {
                                    "id": 4,
                                    "name": "西安交通大学",
                                    "nick": ""
                            }
                    },
                "email": "1324124@126.com",
                "position":"校园经理",
                "id": 4,
                "level": "三道杠",
                "major": {
                    "id": 2,
                    "name": "给排水"
                },
                "idCardNo": "",
                "idCardUrl1": "",
                "idCardUrl2": "",
                "studentIdCardNo": "",
                "studentIdCardUrl1": "",
                "studentIdCardUrl2": "",
                "withdrawalPassword": "",
                "mobilePhone": "44444444444",
                "password": "7c4a8d09ca3762af61e59520943dc26494f8941b",
                "realName": "苗新国",
                "regTime": "2015-08-14T13:14:20.263",
                "sex": 1,
                "status": 1,
                "username": "44444444444",
                "withdrawalPassword": "",
                "url": "http://img1.imgtn.bdimg.com/it/u=1436401313,2171961992&fm=23&gp=0.jpg"
            },
            "balance":233,
            "salary":555
        }
    }
 */
define([], function() {

    var _ = {
        params: {},

        dataCache: {},

        URLs: {
            detail: '/user/info',
            updateUserInfo: '/user',

            modifyUserInfoPage: 'user/modifyUserInfo.html',
            certificatesViewPage: 'user/certificatesView.html',
            changePwdPage: 'user/changePwd.html',
            ViewPage: 'user/changePwd.html',
            departmentSelectorPage: "public/departmentSelector.html",

            validateLoginPwd: "/user/loginPwd",
            validateWithdrawalspwd: "/user/withdrawalPwd"
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

            if( thisPage.params.data ){
                thisPage.params.data.userInfo.gender = thisPage.params.data.userInfo.sex == '1' ? '男' : '女';
                window.HLJ.renderTemplate('myInfo_TPL', thisPage.params.data.userInfo);
            } else {
                window.HLJ.get( this.URLs.detail ).done(function( ret ){
                    thisPage.params.data = ret;
                    thisPage.params.data.userInfo.gender = thisPage.params.data.userInfo.sex == '1' ? '男' : '女';
                    window.HLJ.renderTemplate('myInfo_TPL', ret.userInfo);
                }).fail(function( ret ){
                    window.HLJ.showAlert(ret);
                });
            }
        },

        initEvent: function(){
            var thisPage = this;

            $('.js-goback').on('click', function(){
                window.HLJ.viewGoBack();
            });

            var $myInfo = $('#myInfo');

            $myInfo.on('click', '.js-idCard', function(){  // 查看 证件
                var userInfo = thisPage.params.data.userInfo;
                window.HLJ.view( thisPage.URLs.certificatesViewPage, { title: "身份证信息", cardNum: userInfo.idCardNo, pics: [userInfo.idCardUrl1, userInfo.idCardUrl2]});

            }).on('click', '.js-studentIdCard', function(){  // 查看 证件
                var userInfo = thisPage.params.data.userInfo;
                window.HLJ.view( thisPage.URLs.certificatesViewPage, { title: "学生证信息", cardNum: userInfo.studentIdCardNo, pics: [userInfo.studentIdCardUrl1, userInfo.studentIdCardUrl2]});

            }).on('click', '.js-modify-major', function(){    // 修改 专业 **特殊处理**
                var $thisDom = $(this);

                var callback = function( result ){
                    window.HLJ.put( thisPage.URLs.updateUserInfo, {"major.id": result.major.id} ).done(function(){
                        window.HLJ.showNotice("更新成功！");
                        window.HLJ.viewGoBack( -2, { data : null } );
                    }).fail(function( ret ){
                        window.HLJ.showAlert( ret );
                    }).always(function(){
                        window.HLJ.hideLoading();
                    });
                };

                window.HLJ.view( thisPage.URLs.departmentSelectorPage, { callback : callback } );

            }).on('click', '.js-modify-item', function(){    // 修改属性
                window.HLJ.view( thisPage.URLs.modifyUserInfoPage, {data : thisPage.getModifyItem(this)} );

            }).on('click', '.js-change-pwd', function(){  // 验证密码
                var validateURL = this.dataset.type == 'login' ? thisPage.URLs.validateLoginPwd : thisPage.URLs.validateWithdrawalspwd;
                var PWDtype = this.dataset.type == 'login' ? 'login' : 'withdrawals';
                var placeholder = this.dataset.type == 'login' ? '请输入登录密码' : '请输入提现密码';

                if( PWDtype == 'withdrawals' && !this.dataset.hasopwd ){
                    window.HLJ.view(
                        thisPage.URLs.changePwdPage,
                        {
                            opwd : "null",
                            type : PWDtype
                        }
                    )
                } else {
                    window.HLJ.showDialog({
                        type: "prompt",
                        title: "请验证原密码！",
                        content: '<div class="form-group"><input type="password" class="form-control" id="pwd" placeholder="'+ placeholder +'">'
                    }).done(function(){
                        var opwd = $('#pwd').val();

                        window.HLJ.showLoading();
                        window.HLJ.post( validateURL, { pwd: hex_sha1(opwd) }).done(function(){
                            window.HLJ.view(
                                thisPage.URLs.changePwdPage,
                                {
                                    opwd : opwd,
                                    type : PWDtype
                                }
                            )
                        }).fail(function( ret ){
                            window.HLJ.showAlert( ret );
                        }).always(function(){
                            window.HLJ.hideLoading();
                        });

                    });
                }
            });
        },

        getModifyItem : function( ele ){
            var ret = {};
            $.each( ele.dataset, function( k, v){
                ret[k] = v || " ";
            } );
            return ret;
        }
    }

    return _;

});