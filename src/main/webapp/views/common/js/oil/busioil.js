$(function () {
    $(".cur_postion").html("当前位置： [ " + sessionStorage.getItem("pmenuname") + " ] - [ " + sessionStorage.getItem("cmenuname") + " ]");

    //查询条件
    var strhtml_searchContent = '<div class="inline-block margin">'
        + '<span>油卡卡号:</span>'
        + '<input type="text" class="inputStyle_condition bsoilCode"/>'
        + '<span>绑定姓名:</span>'
        + '<input type="text" class="inputStyle_condition bsoilName"/>'
        + '<span>绑定手机号:</span>'
        + '<input type="text" class="inputStyle_condition bsoilMobile"/>'
        + '<span>状态:</span>'
        + '<select class="inputStyle_condition bsoilStatus">'
        + '<option value="-1">全部</option>'
        + '<option value="1">等待审核</option>'
        + '<option value="2">提交中石化</option>'
        + '<option value="3">注册成功</option>'
        + '<option value="4">邮寄中</option>'
        + '<option value="5">正常使用</option>'
        + '<option value="6">审核不通过</option>'
        + '</select>'
        + '</div>';
    $(".searchContent").html(strhtml_searchContent);
    //是否显示查询条件
    showSearchBox(true);

    var obj = {};//查询条件对象
    searchContent(obj);
    showList(obj, 1);

    //导出Excel
    $('.toExcel').on('click', function () {
        var str = urlfile + '/busiOil/excel/export/oil?a=1';
        if (obj.bsoilCode != "") {
            str += '&bsoilCode=' + encodeURIComponent(obj.bsoilCode);
        }
        if (obj.bsoilName != "") {
            str += '&bsoilName=' + encodeURIComponent(obj.bsoilName);
        }
        if (obj.bsoilMobile != "") {
            str += '&bsoilMobile=' + encodeURIComponent(obj.bsoilMobile);
        }
        if (obj.bsoilStatus != "") {
            str += '&bsoilStatus=' + encodeURIComponent(obj.bsoilStatus);
        }
        window.location = str;
    });
    //详情
    $("body").delegate('.detailit', 'click', function () {
        layer.open({
            type: 2,
            title: '详情',
            scrollbar: false,
            maxmin: true,
            shadeClose: false, //点击遮罩关闭层
            area: [widthLayer, heightLayer],
            content: '../oil/busioil_detail.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
        });
    });
    //注册成功
    $("body").delegate('.registers', 'click', function () {
        layer.open({
            type: 2,
            title: '注册成功',
            scrollbar: false,
            maxmin: true,
            shadeClose: false, //点击遮罩关闭层
            area: [widthLayer, heightLayer],
            content: '../oil/busioil_modify.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
        });
    });
    //邮寄
    $("body").delegate('.mails', 'click', function () {
        layer.open({
            type: 2,
            title: '邮寄',
            scrollbar: false,
            maxmin: true,
            shadeClose: false, //点击遮罩关闭层
            area: [widthLayer, heightLayer],
            content: '../oil/busioil_mails.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
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
    //提交中石化
    $("body").delegate(".submits","click",function(){
        var r=confirm("确认提交中石化？");
        if (r==true){
            var bsoilUuid = $(this).attr('data-id');
            var str = 'bsoilUuid='+encodeURIComponent(bsoilUuid)+'&bsoilStatus='+2;
            getOData(str,"busiOil/update/busiOil",{fn:function(oData){
                    refreshList();
                }}
            );
        }
    });
    //确认收货
    $("body").delegate(".receipts","click",function(){
        var r=confirm("确认收货？");
        if (r==true){
            var bsoilUuid = $(this).attr('data-id');
            var str = 'bsoilUuid='+encodeURIComponent(bsoilUuid)+'&bsoilStatus='+5;
            getOData(str,"busiOil/update/busiOil",{fn:function(oData){
                    refreshList();
                }}
            );
        }
    });
    //审核不通过
    $("body").delegate(".refuse","click",function(){
        var r=confirm("确认审核不通过？");
        if (r==true){
            var bsoilUuid = $(this).attr('data-id');
            var str = 'bsoilUuid='+encodeURIComponent(bsoilUuid)+'&bsoilStatus='+6;
            getOData(str,"busiOil/update/busiOil",{fn:function(oData){
                    refreshList();
                }}
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
        $(".bsoilCode").val('');
        $(".bsoilName").val('');
        $(".bsoilMobile").val('');
        $(".bsoilStatus").val('-1');
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
        {name: "油卡卡号", percent: "15"},
        {name: "绑定姓名", percent: "10"},
        {name: "绑定手机号", percent: "10"},
        {name: "车牌", percent: "10"},
        {name: "行驶证号", percent: "10"},
        {name: "状态", percent: "10"},
        {name: "更新时间", percent: "10"},
        {name: "操作", percent: "20"}];
    setTableHead(aData);
    var str = 'pageNum=' + pagenum + '&pageSize=15';
    if (obj.bsoilCode != "") {
        str += '&bsoilCode=' + encodeURIComponent(obj.bsoilCode);
    }
    if (obj.bsoilName != "") {
        str += '&bsoilName=' + encodeURIComponent(obj.bsoilName);
    }
    if (obj.bsoilMobile != "") {
        str += '&bsoilMobile=' + encodeURIComponent(obj.bsoilMobile);
    }
    if (obj.bsoilStatus != "") {
        str += '&bsoilStatus=' + encodeURIComponent(obj.bsoilStatus);
    }
    getOData(str, "busiOil/find/by/cnd", {
            fn: function (oData) {
                var strhtml_list = "";
                var arrData = oData.data.result;
                var ln = arrData.length;
                for (var i = 0; i < ln; i++) {
                    //1等待审核、2提交中石化、3注册成功、4邮寄中、5确认收货[正常使用]、6审核不通过
                    var statusName = "";
                    var operationHtml = "";
                    if(arrData[i].bsoilStatus==1){
                        statusName="等待审核";
                        operationHtml = '<a class="p-edit submits" data-id="'+arrData[i].bsoilUuid+'">提交中石化</a>'+
                            '<a class="p-edit refuse" data-id="'+arrData[i].bsoilUuid+'">审核不通过</a>';
                    }
                    if(arrData[i].bsoilStatus==2){
                        statusName="提交中石化";
                        operationHtml = '<a class="p-edit registers" data-id="'+arrData[i].bsoilUuid+'">注册成功</a>'+
                            '<a class="p-edit refuse" data-id="'+arrData[i].bsoilUuid+'">审核不通过</a>';
                    }
                    if(arrData[i].bsoilStatus==3){
                        statusName="注册成功";
                        operationHtml = '<a class="p-edit mails" data-id="'+arrData[i].bsoilUuid+'">邮寄</a>';
                    }
                    if(arrData[i].bsoilStatus==4){
                        statusName="邮寄中";
                        operationHtml = '<a class="p-edit receipts" data-id="'+arrData[i].bsoilUuid+'">确认收货</a>';
                    }
                    if(arrData[i].bsoilStatus==5){statusName="正常使用";}
                    if(arrData[i].bsoilStatus==6){statusName="审核不通过";}
                    strhtml_list += '<tr class="trHighLight">'
                        + '<td>' + '<input type="checkbox" name="checkbox" value="checkbox" data-id="' + arrData[i].bsoilUuid + '"/>' + '</td>'
                        + '<td>' + (arrData[i].bsoilCode || "") + '</td>'
                        + '<td>' + (arrData[i].bsoilName || "") + '</td>'
                        + '<td>' + (arrData[i].bsoilMobile || "") + '</td>'
                        + '<td>' + (arrData[i].bsoilCarNo || "") + '</td>'
                        + '<td>' + (arrData[i].bsoilDrivingNo || "") + '</td>'
                        + '<td>' + (statusName || "") + '</td>'
                        + '<td>' + (getFormatDate(arrData[i].bsoilUdate) || "") + '</td>'
                        + '<td>'
                        + '<a  class="p-edit detailit" data-id="' + arrData[i].bsoilUuid + '">查看</a>'
                        + operationHtml
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
    if (obj.bsoilCode != "") {
        str += '&bsoilCode=' + encodeURIComponent(obj.bsoilCode);
    }
    if (obj.bsoilName != "") {
        str += '&bsoilName=' + encodeURIComponent(obj.bsoilName);
    }
    if (obj.bsoilMobile != "") {
        str += '&bsoilMobile=' + encodeURIComponent(obj.bsoilMobile);
    }
    if (obj.bsoilStatus != "") {
        str += '&bsoilStatus=' + encodeURIComponent(obj.bsoilStatus);
    }
    getOData(str, "busiOil/find/by/cnd", {
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
    obj.bsoilCode = $(".bsoilCode").val();
    obj.bsoilName = $(".bsoilName").val();
    obj.bsoilMobile = $(".bsoilMobile").val();
    obj.bsoilStatus = $(".bsoilStatus").val();
}
