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
            otherWorkDays: '/team/user/setting/time',
            selfWorkDays: '/user/setting/time',

            save: "/user/setting/time"
        },

        dataCache: [],

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
            window.HLJ.renderTemplate('workDayPageTitle_TPL', thisPage.params);

            window.HLJ.get(
                (thisPage.params.username ? thisPage.URLs.otherWorkDays : thisPage.URLs.selfWorkDays),
                {
                    username: thisPage.params.username
                }
            ).done(function( ret ){
                var $datePickerList = $('#datePickerList');
                thisPage.dataCache = ret;
                ret.forEach( function( workMonth ){
                    $datePickerList.append( thisPage.renderDatePicker(workMonth.year, workMonth.month, workMonth.days.split(',')) );
                } );
            }).fail(function( ret ){
                window.HLJ.showAlert(ret);
            });
        },

        initEvent: function(){
            var thisPage = this;

            var $datePickerList = $("#datePickerList");
            var $statusSwitcher = $('.js-range-modify');

            $('.js-goback').on('click', function(){
                window.HLJ.viewGoBack();
            });

            $statusSwitcher.on('click', function(){
                var $thisDom = $(this);
                if( $thisDom.hasClass('checked') ){
                    thisPage.save( thisPage.getSaveParams() ).done( function(){
                        $thisDom.removeClass('checked');
                        $('#datePickerList').removeClass('editable');
                    });

                } else {
                    $thisDom.addClass('checked');
                    $('#datePickerList').addClass('editable');
                }
            });

            $datePickerList.on('click', '.js-edit-date', function(){
                var $thisDaom = $(this);
                if( $statusSwitcher.hasClass('checked') && !$thisDaom.hasClass('disabled') ){
                    $thisDaom.toggleClass('date-checked');
                }
            });
        },

        getSaveParams: function(){
            this.dataCache.map( function( workMonth ){
                var days = [];
                $('#date' + workMonth.year + workMonth.month + " .date-checked").each(function( i, ele ){
                    days.push( ele.dataset["date"] );
                })
                workMonth.days = days.join(',');
                return workMonth;
            } );
            return this.dataCache;
        },

        save: function( data ){
            window.HLJ.showLoading();

            return  window.HLJ.ajax(
                        this.URLs.save,
                        {
                            method: "put",
                            data: JSON.stringify( data ),
                            contentType : "application/json; charset=utf-8"
                        }
                    ).done(function( ret ){
                        window.HLJ.showNotice("保存成功！");
                    }).fail(function( ret ){
                        window.HLJ.showAlert(ret);
                    }).always( function(){
                        window.HLJ.hideLoading();
                    });
        },

        renderDatePicker: function( year, month, selectedDays ){
            var currentDate = window.HLJ.serverDate;
            var splitDay = 0
                ,currentMonth = currentDate.getMonth()+1;

            if( currentMonth==month ) {
                splitDay = (currentDate.getDate() + 1);
            } else if( currentMonth>month ) {
                splitDay = 32;
            }

            var selectedDaysMap = {};             // 用于快速查找哪些日期选中 不用多次数组遍历
            selectedDays.forEach(function( day ){
                selectedDaysMap[day+""] = true;
            });

            var datePickerData = [];
            var week = [];
            // 开始 和 结束 日期
            var startDate = new Date(year, month-1, 1)
                ,endDate = new Date(year, month, 0);

            var days = endDate.getDate();

            var startWeek = (startDate.getDay() || 7);   // 获取该月1日为星期几  并改为数组下标

            for(var dateIndex = 1; dateIndex<=days; dateIndex++, startWeek%=7, startWeek++){
                week[ startWeek-1 ] = {
                    date: dateIndex,
                    checked: selectedDaysMap[dateIndex+""] || false,
                    disabled: (dateIndex <= splitDay) ? "disabled" : ""
                };

                if( startWeek == 7 ){
                    datePickerData.push( week );
                    week = [];
                }
            }
            // 补全之后的日期
            if( week.length > 0 ){
                week[6] = null;
                datePickerData.push( week );
            }
            return window.HLJ.renderTemplate('datePicker_TPL', {
                dateList: datePickerData,
                year: year,
                month: month
            });
        }
    }

    return _;

});