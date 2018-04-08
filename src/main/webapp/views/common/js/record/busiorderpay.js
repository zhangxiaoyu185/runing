$(function () {
    $(".cur_postion").html("当前位置： [ " + sessionStorage.getItem("pmenuname") + " ] - [ " + sessionStorage.getItem("cmenuname") + " ]");

    //查询条件
    var strhtml_searchContent = '<div class="inline-block margin">'
        + '<span>交易单号:</span>'
        + '<input type="text" class="inputStyle_condition bsopyOrder"/>'
        + '<span>客户端地址:</span>'
        + '<input type="text" class="inputStyle_condition bsopyClientIp"/>'
        + '<span>支付渠道:</span>'
        + '<input type="text" class="inputStyle_condition bsopyPayChannel"/>'
        + '<span>交易类型:</span>'
        + '<input type="text" class="inputStyle_condition bsopyTradeType"/>'
        + '<span>用户标识:</span>'
        + '<input type="text" class="inputStyle_condition bsopyUser"/>'
        + '<span>同步通知地址:</span>'
        + '<input type="text" class="inputStyle_condition bsopyReturnUrl"/>'
        + '<span>支付返回参数（返回用于前端页面支付参数）:</span>'
        + '<input type="text" class="inputStyle_condition bsopyPayParams"/>'
        + '<span>业务结果:</span>'
        + '<input type="text" class="inputStyle_condition bsopyResultCode"/>'
        + '<span>错误代码:</span>'
        + '<input type="text" class="inputStyle_condition bsopyErrorCode"/>'
        + '<span>错误描述:</span>'
        + '<input type="text" class="inputStyle_condition bsopyErrorMsg"/>'
        + '<span>第三方单号:</span>'
        + '<input type="text" class="inputStyle_condition bsopyOutTradeNo"/>'
        + '<span>支付结果:</span>'
        + '<input type="text" class="inputStyle_condition bsopyPayResult"/>'
        + '<span>支付时间:</span>'
        + '<input type="text" class="inputStyle_condition bsopyPayDate"/>'
        + '<span>通知时间:</span>'
        + '<input type="text" class="inputStyle_condition bsopyNotifyDate"/>'
        + '<span>创建时间:</span>'
        + '<input type="text" class="inputStyle_condition bsopyCdate"/>'
        + '<span>更新时间:</span>'
        + '<input type="text" class="inputStyle_condition bsopyUdate"/>'
        + '</div>';
    $(".searchContent").html(strhtml_searchContent);
    //是否显示查询条件
    showSearchBox(true);

    var obj = {};//查询条件对象
    searchContent(obj);
    showList(obj, 1);

    //详情
    $("body").delegate('.detailit', 'click', function () {
        layer.open({
            type: 2,
            title: '详情',
            scrollbar: false,
            maxmin: true,
            shadeClose: false, //点击遮罩关闭层
            area: [widthLayer, heightLayer],
            content: '../record/busiorderpay_detail.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
        });
    });
    //新增
    $('.addit').on('click', function () {
        layer.open({
            type: 2,
            title: '新增',
            scrollbar: false,
            maxmin: true,
            shadeClose: false, //点击遮罩关闭层
            area: [widthLayer, heightLayer],
            content: '../record/busiorderpay_add.html?timestamp=' + (new Date()).valueOf()
        });
    });
    //修改
    $("body").delegate('.modifyit', 'click', function () {
        layer.open({
            type: 2,
            title: '修改',
            scrollbar: false,
            maxmin: true,
            shadeClose: false, //点击遮罩关闭层
            area: [widthLayer, heightLayer],
            content: '../record/busiorderpay_modify.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
        });
    });
    //全选
    $("body").delegate("input[name='checkboxAll']", "click", function () {
        if ($(this).attr("checked")) {
            $("input[name='checkbox']").each(function () {
                $(this).attr("checked", true);
            });
        } else {
            $("input[name='checkbox']").each(function () {
                $(this).removeAttr("checked");
            });
        }
    });

    //批量删除
    $("body").delegate(".delthese", "click", function () {
        var ids = '';
        $("input[name='checkbox']").each(function () {
            if ($(this).attr("checked")) {
                ids += $(this).attr("data-id") + "|";
            }
        });
        if (ids == "") {
            alert("未选择删除对象！");
        } else {
            var r = confirm("是否确认删除所选的记录？");
            if (r == true) {
                var str = 'bsopyUuids=' + encodeURIComponent(ids);
                getOData(str, "busiOrderPay/delete/batch", {
                        fn: function (oData) {
                            var obj = {};//查询条件对象
                            searchContent(obj);
                            pagenum = parseInt($(".curpage").text());
                            isNull(obj, pagenum);
                            alert("删除成功！");
                        }
                    }
                );
            }
        }
    });

    //删除
    $("body").delegate(".delit", "click", function () {
        var r = confirm("是否确认删除？");
        if (r == true) {
            var str = 'bsopyUuid=' + encodeURIComponent($(this).attr("data-id"));
            getOData(str, "busiOrderPay/delete/one", {
                    fn: function (oData) {
                        var obj = {};//查询条件对象
                        searchContent(obj);
                        pagenum = parseInt($(".curpage").text());
                        isNull(obj, pagenum);
                        alert("删除成功！");
                    }
                }
            );
        }
    });
    //tr高亮显示
    $("body").delegate(".trHighLight", "mouseleave", function () {
        $(this).find("td").css("background-color", "#fff");
    });
    //tr高亮显示并显示图
    $("body").delegate(".trHighLight", "mouseenter", function () {
        $(this).find("td").css("background-color", "#c1ebff");
        var imgurl = $(this).attr("data-imageurl");
        if (imgurl != "#") {
            var that = this;
            $("<img/>").attr("src", imgurl).load(function () {
                var width = 150;
                var realWidth = this.width;
                var realHeight = this.height;
                var height = realHeight / realWidth * width;
                if (realWidth >= width) {
                    var bannerimg = '<img id="bannerimg" src="' + imgurl + '" width="' + width + 'px" height="' + height + 'px" />';
                }
                else {//如果小于浏览器的宽度按照原尺寸显示
                    $("#bannerimg").css("width", realWidth + 'px').css("height", realHeight + 'px');
                    var bannerimg = '<img id="bannerimg" src="' + imgurl + '" width="' + realWidth + 'px" height="' + realHeight + 'px" />';
                }
                layer.tips(bannerimg, that, {
                    tips: 3
                });
            });
        }
    });
    //查询
    $("body").delegate(".searchBtn", "click", function () {
        var obj = {};//查询条件对象
        searchContent(obj);
        showList(obj, 1);
    });
    //重置
    $("body").delegate(".resetBtn", "click", function () {
        var obj = {};//查询条件对象
        $(".bsopyOrder").val('');
        $(".bsopyClientIp").val('');
        $(".bsopyPayChannel").val('');
        $(".bsopyTradeType").val('');
        $(".bsopyUser").val('');
        $(".bsopyReturnUrl").val('');
        $(".bsopyPayParams").val('');
        $(".bsopyResultCode").val('');
        $(".bsopyErrorCode").val('');
        $(".bsopyErrorMsg").val('');
        $(".bsopyOutTradeNo").val('');
        $(".bsopyPayResult").val('');
        $(".bsopyPayDate").val('');
        $(".bsopyNotifyDate").val('');
        $(".bsopyCdate").val('');
        $(".bsopyUdate").val('');
        searchContent(obj);
        showList(obj, 1);
    });
    //上一页
    $('.manageBox').delegate(".backpage", "click", function () {
        var obj = {};//查询条件对象
        searchContent(obj);
        pagenum = parseInt($(this).attr("data-pagenum"));
        showList(obj, pagenum);
    });
    //下一页
    $('.manageBox').delegate(".nextpage", "click", function () {
        var obj = {};//查询条件对象
        searchContent(obj);
        var pagenum = parseInt($(this).attr("data-pagenum"));
        showList(obj, pagenum);
    });
    //首页
    $('.manageBox').delegate(".firstpage", "click", function () {
        var obj = {};//查询条件对象
        searchContent(obj);
        var pagenum = parseInt($(this).attr("data-pagenum"));
        showList(obj, pagenum);
    });
    //末页
    $('.manageBox').delegate(".lastpage", "click", function () {
        var obj = {};//查询条件对象
        searchContent(obj);
        var pagenum = parseInt($(this).attr("data-pagenum"));
        showList(obj, pagenum);
    });
    //跳转至
    $('.manageBox').delegate(".jumppage", "click", function () {
        var obj = {};//查询条件对象
        searchContent(obj);
        var pagenum = parseInt($('.jumppagetext').val());
        if (pagenum > 0 && pagenum <= parseInt($(this).attr("data-pagemax"))) {
            showList(obj, pagenum);
        } else {
            alert("查无此页！");
        }
    });

});

function showList(obj, pagenum) {
    var aData = [{name: "<input type='checkbox' name='checkboxAll' value='checkbox' />", percent: "5"},
        {name: "交易单号", percent: "10"},
        {name: "客户端地址", percent: "10"},
        {name: "支付渠道", percent: "10"},
        {name: "交易类型", percent: "10"},
        {name: "用户标识", percent: "10"},
        {name: "同步通知地址", percent: "10"},
        {name: "支付返回参数（返回用于前端页面支付参数）", percent: "10"},
        {name: "业务结果", percent: "10"},
        {name: "错误代码", percent: "10"},
        {name: "错误描述", percent: "10"},
        {name: "第三方单号", percent: "10"},
        {name: "支付结果", percent: "10"},
        {name: "支付时间", percent: "10"},
        {name: "通知时间", percent: "10"},
        {name: "创建时间", percent: "10"},
        {name: "更新时间", percent: "10"},
        {name: "操作", percent: "10"}];
    setTableHead(aData);
    var str = 'pageNum=' + pagenum + '&pageSize=15';
    if (obj.bsopyOrder != "") {
        str += '&bsopyOrder=' + encodeURIComponent(obj.bsopyOrder);
    }
    if (obj.bsopyClientIp != "") {
        str += '&bsopyClientIp=' + encodeURIComponent(obj.bsopyClientIp);
    }
    if (obj.bsopyPayChannel != "") {
        str += '&bsopyPayChannel=' + encodeURIComponent(obj.bsopyPayChannel);
    }
    if (obj.bsopyTradeType != "") {
        str += '&bsopyTradeType=' + encodeURIComponent(obj.bsopyTradeType);
    }
    if (obj.bsopyUser != "") {
        str += '&bsopyUser=' + encodeURIComponent(obj.bsopyUser);
    }
    if (obj.bsopyReturnUrl != "") {
        str += '&bsopyReturnUrl=' + encodeURIComponent(obj.bsopyReturnUrl);
    }
    if (obj.bsopyPayParams != "") {
        str += '&bsopyPayParams=' + encodeURIComponent(obj.bsopyPayParams);
    }
    if (obj.bsopyResultCode != "") {
        str += '&bsopyResultCode=' + encodeURIComponent(obj.bsopyResultCode);
    }
    if (obj.bsopyErrorCode != "") {
        str += '&bsopyErrorCode=' + encodeURIComponent(obj.bsopyErrorCode);
    }
    if (obj.bsopyErrorMsg != "") {
        str += '&bsopyErrorMsg=' + encodeURIComponent(obj.bsopyErrorMsg);
    }
    if (obj.bsopyOutTradeNo != "") {
        str += '&bsopyOutTradeNo=' + encodeURIComponent(obj.bsopyOutTradeNo);
    }
    if (obj.bsopyPayResult != "") {
        str += '&bsopyPayResult=' + encodeURIComponent(obj.bsopyPayResult);
    }
    if (obj.bsopyPayDate != "") {
        str += '&bsopyPayDate=' + encodeURIComponent(obj.bsopyPayDate);
    }
    if (obj.bsopyNotifyDate != "") {
        str += '&bsopyNotifyDate=' + encodeURIComponent(obj.bsopyNotifyDate);
    }
    if (obj.bsopyCdate != "") {
        str += '&bsopyCdate=' + encodeURIComponent(obj.bsopyCdate);
    }
    if (obj.bsopyUdate != "") {
        str += '&bsopyUdate=' + encodeURIComponent(obj.bsopyUdate);
    }
    getOData(str, "busiOrderPay/find/by/cnd", {
            fn: function (oData) {
                var strhtml_list = "";
                var arrData = oData.data.result;
                var ln = arrData.length;
                for (var i = 0; i < ln; i++) {
                    strhtml_list += '<tr class="trHighLight">'
                        + '<td>' + '<input type="checkbox" name="checkbox" value="checkbox" data-id="' + arrData[i].bsopyUuid + '"/>' + '</td>'
                        + '<td>' + (arrData[i].bsopyOrder || "") + '</td>'
                        + '<td>' + (arrData[i].bsopyClientIp || "") + '</td>'
                        + '<td>' + (arrData[i].bsopyPayChannel || "") + '</td>'
                        + '<td>' + (arrData[i].bsopyTradeType || "") + '</td>'
                        + '<td>' + (arrData[i].bsopyUser || "") + '</td>'
                        + '<td>' + (arrData[i].bsopyReturnUrl || "") + '</td>'
                        + '<td>' + (arrData[i].bsopyPayParams || "") + '</td>'
                        + '<td>' + (arrData[i].bsopyResultCode || "") + '</td>'
                        + '<td>' + (arrData[i].bsopyErrorCode || "") + '</td>'
                        + '<td>' + (arrData[i].bsopyErrorMsg || "") + '</td>'
                        + '<td>' + (arrData[i].bsopyOutTradeNo || "") + '</td>'
                        + '<td>' + (arrData[i].bsopyPayResult || "") + '</td>'
                        + '<td>' + (arrData[i].bsopyPayDate || "") + '</td>'
                        + '<td>' + (arrData[i].bsopyNotifyDate || "") + '</td>'
                        + '<td>' + (arrData[i].bsopyCdate || "") + '</td>'
                        + '<td>' + (arrData[i].bsopyUdate || "") + '</td>'
                        + '<td>'
                        + '<a  class="p-edit detailit" data-id="' + arrData[i].bsopyUuid + '">查看</a>'
                        + '<a  class="p-edit modifyit" data-id="' + arrData[i].bsopyUuid + '">修改</a>'
                        + '<a  class="p-edit delit" data-id="' + arrData[i].bsopyUuid + '">删除</a>'
                        + '</td>'
                        + '</tr>';
                }
                $(".tb-body").html(strhtml_list);
                setTableFoot(oData.data, aData.length);
            }
        }, true, "get"
    );
}

function isNull(obj, pagenum) {
    var str = 'pageNum=' + pagenum + '&pageSize=15';
    if (obj.bsopyOrder != "") {
        str += '&bsopyOrder=' + encodeURIComponent(obj.bsopyOrder);
    }
    if (obj.bsopyClientIp != "") {
        str += '&bsopyClientIp=' + encodeURIComponent(obj.bsopyClientIp);
    }
    if (obj.bsopyPayChannel != "") {
        str += '&bsopyPayChannel=' + encodeURIComponent(obj.bsopyPayChannel);
    }
    if (obj.bsopyTradeType != "") {
        str += '&bsopyTradeType=' + encodeURIComponent(obj.bsopyTradeType);
    }
    if (obj.bsopyUser != "") {
        str += '&bsopyUser=' + encodeURIComponent(obj.bsopyUser);
    }
    if (obj.bsopyReturnUrl != "") {
        str += '&bsopyReturnUrl=' + encodeURIComponent(obj.bsopyReturnUrl);
    }
    if (obj.bsopyPayParams != "") {
        str += '&bsopyPayParams=' + encodeURIComponent(obj.bsopyPayParams);
    }
    if (obj.bsopyResultCode != "") {
        str += '&bsopyResultCode=' + encodeURIComponent(obj.bsopyResultCode);
    }
    if (obj.bsopyErrorCode != "") {
        str += '&bsopyErrorCode=' + encodeURIComponent(obj.bsopyErrorCode);
    }
    if (obj.bsopyErrorMsg != "") {
        str += '&bsopyErrorMsg=' + encodeURIComponent(obj.bsopyErrorMsg);
    }
    if (obj.bsopyOutTradeNo != "") {
        str += '&bsopyOutTradeNo=' + encodeURIComponent(obj.bsopyOutTradeNo);
    }
    if (obj.bsopyPayResult != "") {
        str += '&bsopyPayResult=' + encodeURIComponent(obj.bsopyPayResult);
    }
    if (obj.bsopyPayDate != "") {
        str += '&bsopyPayDate=' + encodeURIComponent(obj.bsopyPayDate);
    }
    if (obj.bsopyNotifyDate != "") {
        str += '&bsopyNotifyDate=' + encodeURIComponent(obj.bsopyNotifyDate);
    }
    if (obj.bsopyCdate != "") {
        str += '&bsopyCdate=' + encodeURIComponent(obj.bsopyCdate);
    }
    if (obj.bsopyUdate != "") {
        str += '&bsopyUdate=' + encodeURIComponent(obj.bsopyUdate);
    }
    getOData(str, "busiOrderPay/find/by/cnd", {
        fn: function (oData) {
            var arrData = oData.data.result;
            var ln = arrData.length;
            if (ln == 0) {
                if (oData.data.totalCount != 0 && pagenum != 1) {
                    showList(obj, pagenum - 1);
                } else {
                    showList(obj, 1);
                }
            } else {
                showList(obj, pagenum);
            }
        }
    }, true, "get");
}

function refreshList() {
    var obj = {};//查询条件对象
    searchContent(obj);
    showList(obj, parseInt($(".curpage").text()));
    layer.closeAll();
}

function searchContent(obj) {
    obj.bsopyOrder = $(".bsopyOrder").val();
    obj.bsopyClientIp = $(".bsopyClientIp").val();
    obj.bsopyPayChannel = $(".bsopyPayChannel").val();
    obj.bsopyTradeType = $(".bsopyTradeType").val();
    obj.bsopyUser = $(".bsopyUser").val();
    obj.bsopyReturnUrl = $(".bsopyReturnUrl").val();
    obj.bsopyPayParams = $(".bsopyPayParams").val();
    obj.bsopyResultCode = $(".bsopyResultCode").val();
    obj.bsopyErrorCode = $(".bsopyErrorCode").val();
    obj.bsopyErrorMsg = $(".bsopyErrorMsg").val();
    obj.bsopyOutTradeNo = $(".bsopyOutTradeNo").val();
    obj.bsopyPayResult = $(".bsopyPayResult").val();
    obj.bsopyPayDate = $(".bsopyPayDate").val();
    obj.bsopyNotifyDate = $(".bsopyNotifyDate").val();
    obj.bsopyCdate = $(".bsopyCdate").val();
    obj.bsopyUdate = $(".bsopyUdate").val();
}
