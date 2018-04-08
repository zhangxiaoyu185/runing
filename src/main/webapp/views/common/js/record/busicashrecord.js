$(function () {
    $(".cur_postion").html("当前位置： [ " + sessionStorage.getItem("pmenuname") + " ] - [ " + sessionStorage.getItem("cmenuname") + " ]");

    //查询条件
    var strhtml_searchContent = '<div class="inline-block margin">'
        + '<span>手机号:</span>'
        + '<input type="text" class="inputStyle_condition bscrdMobile"/>'
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
            content: '../record/busicashrecord_detail.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
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
    //待打款
    $("body").delegate(".waitpass","click",function(){
        var r=confirm("确认通过提现申请？");
        if (r==true){
            var bscrdUuid = $(this).attr('data-id');
            var str = 'bscrdUuid='+encodeURIComponent(bscrdUuid)+'&bscrdStatus='+2;
            getOData(str,"busiCashRecord/update/busiCashRecord",{fn:function(oData){
                    refreshList();
                }}
            );
        }
    });
    //已打款
    $("body").delegate(".pass","click",function(){
        var r=confirm("确认通过提现申请？");
        if (r==true){
            var bscrdUuid = $(this).attr('data-id');
            var str = 'bscrdUuid='+encodeURIComponent(bscrdUuid)+'&bscrdStatus='+3;
            getOData(str,"busiCashRecord/update/busiCashRecord",{fn:function(oData){
                    refreshList();
                }}
            );
        }
    });
    //拒绝申请
    $("body").delegate(".refuse","click",function(){
        var r=confirm("确认拒绝提现申请？");
        if (r==true){
            var bscrdUuid = $(this).attr('data-id');
            var str = 'bscrdUuid='+encodeURIComponent(bscrdUuid)+'&bscrdStatus='+4;
            getOData(str,"busiCashRecord/update/busiCashRecord",{fn:function(oData){
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
        $(".bscrdMobile").val('');
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
        {name: "提取金额", percent: "10"},
        {name: "提取人姓名", percent: "15"},
        {name: "状态", percent: "10"},
        {name: "手机号", percent: "15"},
        {name: "银行账户名称", percent: "15"},
        {name: "银行账号", percent: "15"},
        {name: "操作", percent: "15"}];
    setTableHead(aData);
    var str = 'pageNum=' + pagenum + '&pageSize=15';
    if (obj.bscrdMobile != "") {
        str += '&bscrdMobile=' + encodeURIComponent(obj.bscrdMobile);
    }
    getOData(str, "busiCashRecord/find/by/cnd", {
            fn: function (oData) {
                var strhtml_list = "";
                var arrData = oData.data.result;
                var ln = arrData.length;
                for (var i = 0; i < ln; i++) {
                    var statusName = "";
                    var operationHtml = "";
                    if(arrData[i].bscrdStatus==1){
                        statusName="已申请";
                        operationHtml = '<a class="p-edit waitpass" data-id="'+arrData[i].bscrdUuid+'">待打款</a>'+
                            '<a class="p-edit refuse" data-id="'+arrData[i].bscrdUuid+'">驳回</a>';
                    }
                    if(arrData[i].bscrdStatus==2){
                        statusName="待打款";
                        operationHtml = '<a class="p-edit pass" data-id="'+arrData[i].bscrdUuid+'">已打款</a>'+
                            '<a class="p-edit refuse" data-id="'+arrData[i].bscrdUuid+'">驳回</a>';
                    }
                    if(arrData[i].bscrdStatus==3){statusName="已打款";}
                    if(arrData[i].bscrdStatus==4){statusName="已驳回";}
                    strhtml_list += '<tr class="trHighLight">'
                        + '<td>' + '<input type="checkbox" name="checkbox" value="checkbox" data-id="' + arrData[i].bscrdUuid + '"/>' + '</td>'
                        + '<td>' + (arrData[i].bscrdAmount || 0) + '</td>'
                        + '<td>' + (arrData[i].bscrdExtractedName || "") + '</td>'
                        + '<td>' + (statusName || "") + '</td>'
                        + '<td>' + (arrData[i].bscrdMobile || "") + '</td>'
                        + '<td>' + (arrData[i].bscrdAccountName || "") + '</td>'
                        + '<td>' + (arrData[i].bscrdAccountNo || "") + '</td>'
                        + '<td>'
                        + '<a  class="p-edit detailit" data-id="' + arrData[i].bscrdUuid + '">查看</a>'
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
    if (obj.bscrdMobile != "") {
        str += '&bscrdMobile=' + encodeURIComponent(obj.bscrdMobile);
    }
    getOData(str, "busiCashRecord/find/by/cnd", {
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
    obj.bscrdMobile = $(".bscrdMobile").val();
}
