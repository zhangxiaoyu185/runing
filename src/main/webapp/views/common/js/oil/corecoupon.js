$(function () {
    $(".cur_postion").html("当前位置： [ " + sessionStorage.getItem("pmenuname") + " ] - [ " + sessionStorage.getItem("cmenuname") + " ]");

    //查询条件
    var strhtml_searchContent = '<div class="inline-block margin">'
        + '<span>用户手机号:</span>'
        + '<input type="text" class="inputStyle_condition mobile"/>'
        + '<span>使用状态:</span>'
        + '<select class="inputStyle_condition crcpnUseStatus">'
        + '<option value="-1">全部</option>'
        + '<option value="1">已使用</option>'
        + '<option value="2">可使用</option>'
        + '<option value="3">已过期</option>'
        + '<option value="4">未分配</option>'
        + '</select>'
        + '<span>发送状态:</span>'
        + '<select class="inputStyle_condition crcpnSendStatus">'
        + '<option value="-1">全部</option>'
        + '<option value="1">系统发送</option>'
        + '<option value="2">手动发送</option>'
        + '</select>'
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
            content: '../oil/corecoupon_detail.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
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
            content: '../oil/corecoupon_add.html?timestamp=' + (new Date()).valueOf()
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
            content: '../oil/corecoupon_modify.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
        });
    });
    //赠送
    $("body").delegate('.sendcouponit', 'click', function () {
        layer.open({
            type: 2,
            title: '赠送',
            scrollbar: false,
            maxmin: true,
            shadeClose: false, //点击遮罩关闭层
            area: [widthLayer, heightLayer],
            content: '../oil/send_coupon.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
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
                var str = 'crcpnUuids=' + encodeURIComponent(ids);
                getOData(str, "coreCoupon/delete/batch", {
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
            var str = 'crcpnUuid=' + encodeURIComponent($(this).attr("data-id"));
            getOData(str, "coreCoupon/delete/one", {
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
        $(".mobile").val('');
        $(".crcpnUseStatus").val('-1');
        $(".crcpnSendStatus").val('-1');
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
        {name: "用户手机号", percent: "15"},
        {name: "使用状态", percent: "10"},
        {name: "优惠金额", percent: "10"},
        {name: "添加时间", percent: "15"},
        {name: "生效时间", percent: "15"},
        {name: "截止时间", percent: "15"},
        {name: "操作", percent: "15"}];
    setTableHead(aData);
    var str = 'pageNum=' + pagenum + '&pageSize=15';
    if (obj.mobile != "") {
        str += '&mobile=' + encodeURIComponent(obj.mobile);
    }
    if (obj.crcpnUseStatus != "" && obj.crcpnUseStatus > 0) {
        str += '&crcpnUseStatus=' + encodeURIComponent(obj.crcpnUseStatus);
    }
    if (obj.crcpnSendStatus != "" && obj.crcpnSendStatus > 0) {
        str += '&crcpnSendStatus=' + encodeURIComponent(obj.crcpnSendStatus);
    }
    getOData(str, "coreCoupon/find/by/cnd", {
            fn: function (oData) {
                var strhtml_list = "";
                var arrData = oData.data.result;
                var ln = arrData.length;
                for (var i = 0; i < ln; i++) {
                    var opertionCH = "";
                    var crcpnUseStatusCH = "已使用";
                    if(arrData[i].crcpnUseStatus==2) {
                        crcpnUseStatusCH = "可使用";
                    }
                    if(arrData[i].crcpnUseStatus==3) {
                        crcpnUseStatusCH = "已过期";
                    }
                    if(arrData[i].crcpnUseStatus==4) {
                        crcpnUseStatusCH = "未分配";
                        opertionCH = '<a  class="p-edit sendcouponit" data-id="' + arrData[i].crcpnUuid + '">赠送</a>';
                    }
                    strhtml_list += '<tr class="trHighLight">'
                        + '<td>' + '<input type="checkbox" name="checkbox" value="checkbox" data-id="' + arrData[i].crcpnUuid + '"/>' + '</td>'
                        + '<td>' + (arrData[i].crcpnUserMobile || "") + '</td>'
                        + '<td>' + (crcpnUseStatusCH || "") + '</td>'
                        + '<td>' + (arrData[i].crcpnReduce || 0) + '</td>'
                        + '<td>' + (getFormatDate(arrData[i].crcpnCdate) || "") + '</td>'
                        + '<td>' + (getFormatDate(arrData[i].crcpnStart) || "") + '</td>'
                        + '<td>' + (getFormatDate(arrData[i].crcpnEnd) || "") + '</td>'
                        + '<td>'
                        + '<a  class="p-edit detailit" data-id="' + arrData[i].crcpnUuid + '">查看</a>'
                        + '<a  class="p-edit modifyit" data-id="' + arrData[i].crcpnUuid + '">修改</a>'
                        + opertionCH
                        + '<a  class="p-edit delit" data-id="' + arrData[i].crcpnUuid + '">删除</a>'
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
    if (obj.mobile != "") {
        str += '&mobile=' + encodeURIComponent(obj.mobile);
    }
    if (obj.crcpnUseStatus != "" && obj.crcpnUseStatus > 0) {
        str += '&crcpnUseStatus=' + encodeURIComponent(obj.crcpnUseStatus);
    }
    if (obj.crcpnSendStatus != "" && obj.crcpnSendStatus > 0) {
        str += '&crcpnSendStatus=' + encodeURIComponent(obj.crcpnSendStatus);
    }
    getOData(str, "coreCoupon/find/by/cnd", {
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
    obj.mobile = $(".mobile").val();
    obj.crcpnUseStatus = $(".crcpnUseStatus").val();
    obj.crcpnSendStatus = $(".crcpnSendStatus").val();
}
