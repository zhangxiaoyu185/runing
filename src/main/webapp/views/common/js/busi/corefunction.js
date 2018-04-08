// JavaScript Document
$(function () {
    $(".cur_postion").html("当前位置： 菜单管理");

    //查询条件
    var strhtml_searchContent = '<div class="inline-block margin">'
        + '<span>菜单名称:</span>'
        + '<input type="text" class="inputStyle_condition crfntFunName"/>'
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
            content: '../busi/corefunction_detail.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
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
            content: '../busi/corefunction_add.html?timestamp=' + (new Date()).valueOf()
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
            content: '../busi/corefunction_modify.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
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
                var str = 'crfntUuids=' + encodeURIComponent(ids);
                getOData(str, "coreFunction/delete/batch", {
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
            var str = 'crfntUuid=' + encodeURIComponent($(this).attr("data-id"));
            getOData(str, "coreFunction/delete/one", {
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

    //启用禁用
    $("body").delegate(".enablelit", "click", function () {
        if ($(this).attr("data-status") == 0) {
            var r = confirm("是否确认禁用？");
        } else if ($(this).attr("data-status") == 1) {
            var r = confirm("是否确认启用？");
        }

        if (r == true) {
            var str = 'crfntUuid=' + encodeURIComponent($(this).attr("data-id")) + '&crfntStatus=' + encodeURIComponent($(this).attr("data-status"));
            getOData(str, "coreFunction/enable", {
                    fn: function (oData) {
                        var obj = {};//查询条件对象
                        searchContent(obj);
                        pagenum = $(".curpage").text();
                        showList(obj, pagenum);
                        alert("操作成功！");
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
        $(".crfntFunName").val('');
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
        {name: "功能项名称", percent: "20"},
        {name: "资源请求地址", percent: "30"},
        {name: "状态", percent: "10"},
        {name: "上级菜单", percent: "20"},
        {name: "操作", percent: "15"}];
    setTableHead(aData);
    var str = 'pageNum=' + pagenum + '&pageSize=15';
    if (obj.crfntFunName != "") {
        str += '&crfntFunName=' + encodeURIComponent(obj.crfntFunName);
    }
    getOData(str, "coreFunction/find/by/cnd", {
            fn: function (oData) {
                var strhtml_list = "";
                var arrData = oData.data.result;
                var ln = arrData.length;
                for (var i = 0; i < ln; i++) {
                    var crfntStatusCH = "启用";
                    if (arrData[i].crfntStatus == 0) {
                        crfntStatusCH = "禁用";
                    }
                    strhtml_list += '<tr class="trHighLight">'
                        + '<td>' + '<input type="checkbox" name="checkbox" value="checkbox" data-id="' + arrData[i].crfntUuid + '"/>' + '</td>'
                        + '<td>' + (arrData[i].crfntFunName || "") + '</td>'
                        + '<td>' + (arrData[i].crfntResource || "") + '</td>'
                        + '<td>' + (crfntStatusCH || "") + '</td>'
                        + '<td>' + (arrData[i].crfntFatherName || "") + '</td>'
                        + '<td>'
                        + '<a  class="p-edit detailit" data-id="' + arrData[i].crfntUuid + '">查看</a>'
                        + '<a  class="p-edit modifyit" data-id="' + arrData[i].crfntUuid + '">修改</a>'
                        + '<a  class="p-edit delit" data-id="' + arrData[i].crfntUuid + '">删除</a>';
                    if (arrData[i].crfntStatus == 0) {
                        strhtml_list += '<a  class="p-edit enablelit" data-id="' + arrData[i].crfntUuid + '" data-status="1">启用</a>';
                    } else if (arrData[i].crfntStatus == 1) {
                        strhtml_list += '<a  class="p-edit enablelit" data-id="' + arrData[i].crfntUuid + '" data-status="0">禁用</a>';
                    }
                    strhtml_list += '</td></tr>';
                }
                $(".tb-body").html(strhtml_list);
                setTableFoot(oData.data, aData.length);
            }
        }, true, "get"
    );
}

function isNull(obj, pagenum) {
    var str = 'pageNum=' + pagenum + '&pageSize=15';
    if (obj.crfntFunName != "") {
        str += '&crfntFunName=' + encodeURIComponent(obj.crfntFunName);
    }
    getOData(str, "coreFunction/find/by/cnd", {
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
        }, true, "get"
    );
}

function refreshList() {
    var obj = {};//查询条件对象
    searchContent(obj);
    showList(obj, parseInt($(".curpage").text()));
    layer.closeAll();
}

function searchContent(obj) {
    obj.crfntFunName = $(".crfntFunName").val();
}
