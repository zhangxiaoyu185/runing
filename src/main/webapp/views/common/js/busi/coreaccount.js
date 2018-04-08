// JavaScript Document
$(function () {
    $(".cur_postion").html("当前位置： 账号管理");

    //查询条件
    var strhtml_searchContent = '<div class="inline-block margin">'
        + '<span>帐户名称:</span>'
        + '<input type="text" class="inputStyle_condition cractName"/>'
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
            content: '../busi/coreaccount_detail.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
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
            content: '../busi/coreaccount_add.html?timestamp=' + (new Date()).valueOf()
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
            content: '../busi/coreaccount_modify.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
        });
    });
    //重置密码
    $("body").delegate('.modifypsd', 'click', function () {
        var r = confirm("是否确认重置密码？");
        if (r == true) {
            var cractUuid = $(this).attr("data-id");
            var str = 'cractUuid=' + encodeURIComponent(cractUuid);
            getOData(str, "coreAccount/reset/pwd", {
                    fn: function (oData) {
                        alert("重置密码为123456！");
                    }
                }
            );
        }
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
                var str = 'cractUuids=' + encodeURIComponent(ids);
                getOData(str, "coreAccount/delete/batch", {
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
            var str = 'cractUuid=' + encodeURIComponent($(this).attr("data-id"));
            getOData(str, "coreAccount/delete/one", {
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
        $(".cractName").val('');
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
        {name: "帐户名称", percent: "20"},
        {name: "角色集合", percent: "40"},
        {name: "联系方式", percent: "15"},
        {name: "操作", percent: "20"}];
    setTableHead(aData);
    var str = 'pageNum=' + pagenum + '&pageSize=15';
    if (obj.cractName != "") {
        str += '&cractName=' + encodeURIComponent(obj.cractName);
    }
    getOData(str, "coreAccount/find/by/cnd", {
            fn: function (oData) {
                var strhtml_list = "";
                var arrData = oData.data.result;
                var ln = arrData.length;
                for (var i = 0; i < ln; i++) {
                    strhtml_list += '<tr class="trHighLight">'
                        + '<td>' + '<input type="checkbox" name="checkbox" value="checkbox" data-id="' + arrData[i].cractUuid + '"/>' + '</td>'
                        + '<td>' + (arrData[i].cractName || "") + '</td>'
                        + '<td>' + (arrData[i].cractRolesName || "") + '</td>'
                        + '<td>' + (arrData[i].cractTel || "") + '</td>'
                        + '<td>'
                        + '<a  class="p-edit detailit" data-id="' + arrData[i].cractUuid + '">查看</a>'
                        + '<a  class="p-edit modifyit" data-id="' + arrData[i].cractUuid + '">修改</a>'
                        + '<a  class="p-edit modifypsd" data-id="' + arrData[i].cractUuid + '" data-psd="' + arrData[i].cractPassword + '">重置密码</a>'
                        + '<a  class="p-edit delit" data-id="' + arrData[i].cractUuid + '">删除</a>'
                        + '</td>'
                        + '</tr>';
                }
                $(".tb-body").html(strhtml_list);
                setTableFoot(oData.data, aData.length);
            }
        },true,"get"
    );
}

function isNull(obj, pagenum) {
    var str = 'pageNum=' + pagenum + '&pageSize=15';
    if (obj.cractName != "") {
        str += '&cractName=' + encodeURIComponent(obj.cractName);
    }
    getOData(str, "coreAccount/find/by/cnd", {
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
        },true,"get"
    );
}

function refreshList() {
    var obj = {};//查询条件对象
    searchContent(obj);
    showList(obj, parseInt($(".curpage").text()));
    layer.closeAll();
}

function searchContent(obj) {
    obj.cractName = $(".cractName").val();
}
