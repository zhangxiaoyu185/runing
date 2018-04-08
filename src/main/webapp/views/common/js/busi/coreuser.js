$(function () {
    $(".cur_postion").html("当前位置： [ " + sessionStorage.getItem("pmenuname") + " ] - [ " + sessionStorage.getItem("cmenuname") + " ]");

    //查询条件
    var strhtml_searchContent = '<div class="inline-block margin">'
        + '<span>帐户名称:</span>'
        + '<input type="text" class="inputStyle_condition crusrName"/>'
        + '<span>手机号码:</span>'
        + '<input type="text" class="inputStyle_condition crusrMobile"/>'
        + '<span>身份证号:</span>'
        + '<input type="text" class="inputStyle_condition crusrIdCard"/>'
        + '<span>邀请码:</span>'
        + '<input type="text" class="inputStyle_condition crusrInviteCode"/>'
        + '<span>身份:</span>'
        + '<select class="inputStyle_condition crusrType">'
        + '<option value="-1">全部</option>'
        + '<option value="1">个人</option>'
        + '<option value="2">公司</option>'
        + '<option value="3">未知</option>'
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
            content: '../busi/coreuser_detail.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
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
                var str = 'crusrUuids=' + encodeURIComponent(ids);
                getOData(str, "coreUser/delete/batch", {
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
            var str = 'crusrUuid=' + encodeURIComponent($(this).attr("data-id"));
            getOData(str, "coreUser/delete/one", {
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
        $(".crusrName").val('');
        $(".crusrMobile").val('');
        $(".crusrIdCard").val('');
        $(".crusrInviteCode").val('');
        $(".crusrType").val('-1');
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
        {name: "持卡人姓名/公司名", percent: "15"},
        {name: "手机号码", percent: "10"},
        {name: "身份证号", percent: "15"},
        {name: "解冻佣金总额", percent: "10"},
        {name: "冻结佣金总额", percent: "10"},
        {name: "邀请码", percent: "10"},
        {name: "身份", percent: "10"},
        {name: "操作", percent: "15"}];
    setTableHead(aData);
    var str = 'pageNum=' + pagenum + '&pageSize=15';
    if (obj.crusrName != "") {
        str += '&crusrName=' + encodeURIComponent(obj.crusrName);
    }
    if (obj.crusrMobile != "") {
        str += '&crusrMobile=' + encodeURIComponent(obj.crusrMobile);
    }
    if (obj.crusrIdCard != "") {
        str += '&crusrIdCard=' + encodeURIComponent(obj.crusrIdCard);
    }
    if (obj.crusrInviteCode != "") {
        str += '&crusrInviteCode=' + encodeURIComponent(obj.crusrInviteCode);
    }
    if (obj.crusrType != "-1") {
        str += '&crusrType=' + encodeURIComponent(obj.crusrType);
    }
    getOData(str, "coreUser/find/by/cnd", {
            fn: function (oData) {
                var strhtml_list = "";
                var arrData = oData.data.result;
                var ln = arrData.length;
                for (var i = 0; i < ln; i++) {
                    var crusrTypeCH = "个人";
                    if(arrData[i].crusrType == 2) {
                        crusrTypeCH = "公司";
                    } else if(arrData[i].crusrType == 3){
                        crusrTypeCH = "未知";
                    }
                    strhtml_list += '<tr class="trHighLight">'
                        + '<td>' + '<input type="checkbox" name="checkbox" value="checkbox" data-id="' + arrData[i].crusrUuid + '"/>' + '</td>'
                        + '<td>' + (arrData[i].crusrName || "") + '</td>'
                        + '<td>' + (arrData[i].crusrMobile || "") + '</td>'
                        + '<td>' + (arrData[i].crusrIdCard || "") + '</td>'
                        + '<td>' + (arrData[i].crusrEnableIncome || 0) + '</td>'
                        + '<td>' + (arrData[i].crusrFrozenIncome || 0) + '</td>'
                        + '<td>' + (arrData[i].crusrInviteCode || "") + '</td>'
                        + '<td>' + (crusrTypeCH || "") + '</td>'
                        + '<td>'
                        + '<a  class="p-edit detailit" data-id="' + arrData[i].crusrUuid + '">查看</a>'
                        + '<a  class="p-edit delit" data-id="' + arrData[i].crusrUuid + '">删除</a>'
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
    if (obj.crusrName != "") {
        str += '&crusrName=' + encodeURIComponent(obj.crusrName);
    }
    if (obj.crusrMobile != "") {
        str += '&crusrMobile=' + encodeURIComponent(obj.crusrMobile);
    }
    if (obj.crusrIdCard != "") {
        str += '&crusrIdCard=' + encodeURIComponent(obj.crusrIdCard);
    }
    if (obj.crusrInviteCode != "") {
        str += '&crusrInviteCode=' + encodeURIComponent(obj.crusrInviteCode);
    }
    if (obj.crusrType != "-1") {
        str += '&crusrType=' + encodeURIComponent(obj.crusrType);
    }
    getOData(str, "coreUser/find/by/cnd", {
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
    obj.crusrName = $(".crusrName").val();
    obj.crusrMobile = $(".crusrMobile").val();
    obj.crusrIdCard = $(".crusrIdCard").val();
    obj.crusrInviteCode = $(".crusrInviteCode").val();
    obj.crusrType = $(".crusrType").val();
}
