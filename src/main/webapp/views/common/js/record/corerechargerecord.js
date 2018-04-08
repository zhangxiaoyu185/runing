$(function () {
    $(".cur_postion").html("当前位置： [ " + sessionStorage.getItem("pmenuname") + " ] - [ " + sessionStorage.getItem("cmenuname") + " ]");

    //查询条件
    var strhtml_searchContent = '<div class="inline-block margin">'
        + '<span>用户手机号:</span>'
        + '<input type="text" class="inputStyle_condition mobile"/>'
        + '<span>充值状态:</span>'
        + '<select class="inputStyle_condition crrerStatus">'
        + '<option value="-1">全部</option>'
        + '<option value="1">成功</option>'
        + '<option value="0">失败</option>'
        + '<option value="2">申请充值</option>'
        + '</select>'
        + '<span>交易单号:</span>'
        + '<input type="text" class="inputStyle_condition crrerOrder"/>'
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
            content: '../record/corerechargerecord_detail.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
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
            content: '../record/corerechargerecord_modify.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
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
        $(".crrerStatus").val('-1');
        $(".crrerOrder").val('');
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
        {name: "充值状态", percent: "10"},
        {name: "充值金额", percent: "10"},
        {name: "充值时间", percent: "15"},
        {name: "油卡卡号", percent: "15"},
        {name: "交易单号", percent: "15"},
        {name: "操作", percent: "15"}];
    setTableHead(aData);
    var str = 'pageNum=' + pagenum + '&pageSize=15';
    if (obj.mobile != "") {
        str += '&mobile=' + encodeURIComponent(obj.mobile);
    }
    if (obj.crrerStatus != "" && obj.crrerStatus > 0) {
        str += '&crrerStatus=' + encodeURIComponent(obj.crrerStatus);
    }
    if (obj.crrerOrder != "") {
        str += '&crrerOrder=' + encodeURIComponent(obj.crrerOrder);
    }
    getOData(str, "coreRechargeRecord/find/by/cnd", {
            fn: function (oData) {
                var strhtml_list = "";
                var arrData = oData.data.result;
                var ln = arrData.length;
                for (var i = 0; i < ln; i++) {
                    var crrerStatusCH = "成功";
                    if(arrData[i].crrerStatus == 0) {
                        crrerStatusCH = "失败";
                    }
                    if(arrData[i].crrerStatus == 2) {
                        crrerStatusCH = "申请充值";
                    }
                    strhtml_list += '<tr class="trHighLight">'
                        + '<td>' + '<input type="checkbox" name="checkbox" value="checkbox" data-id="' + arrData[i].crrerUuid + '"/>' + '</td>'
                        + '<td>' + (arrData[i].crrerUserMobile || "") + '</td>'
                        + '<td>' + (crrerStatusCH || "") + '</td>'
                        + '<td>' + (arrData[i].crrerFee || 0) + '</td>'
                        + '<td>' + (getFormatDate(arrData[i].crrerDate) || "") + '</td>'
                        + '<td>' + (arrData[i].crrerOilCode || "") + '</td>'
                        + '<td>' + (arrData[i].crrerOrder || "") + '</td>'
                        + '<td>'
                        + '<a  class="p-edit detailit" data-id="' + arrData[i].crrerUuid + '">查看</a>'
                        // + '<a  class="p-edit modifyit" data-id="' + arrData[i].crrerUuid + '">修改</a>'
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
    if (obj.crrerStatus != "" && obj.crrerStatus > 0) {
        str += '&crrerStatus=' + encodeURIComponent(obj.crrerStatus);
    }
    if (obj.crrerOrder != "") {
        str += '&crrerOrder=' + encodeURIComponent(obj.crrerOrder);
    }
    getOData(str, "coreRechargeRecord/find/by/cnd", {
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
    obj.crrerStatus = $(".crrerStatus").val();
    obj.crrerOrder = $(".crrerOrder").val();
}
