$(function () {
    $(".cur_postion").html("当前位置： [ " + sessionStorage.getItem("pmenuname") + " ] - [ " + sessionStorage.getItem("cmenuname") + " ]");

    //查询条件
    var strhtml_searchContent = '<div class="inline-block margin">'
        + '<span>代理商:</span>'
        + '<select class="inputStyle_condition bsapyAgent"></select>'
        + '<span>汇款单号:</span>'
        + '<input type="text" class="inputStyle_condition bsapyOrder"/>'
        + '<span>状态:</span>'
        + '<select class="inputStyle_condition bsapyStatus">'
        + '<option value="-1">全部</option>'
        + '<option value="1">申请中</option>'
        + '<option value="2">已驳回</option>'
        + '<option value="3">已确认</option>'
        + '</select>'
        + '</div>';
    $(".searchContent").html(strhtml_searchContent);

    getOData('',"busiAgent/find/all",{fn:function(oData){
        if(oData.code == 1) {
            var strhtml_select = '<option value="">全部</option>';
            var arrData = oData.data;
            var ln = arrData.length;
            for(var i=0;i<ln;i++){
                strhtml_select += '<option value="'+arrData[i].bsaetUuid+'">'+arrData[i].bsaetCode+'</option>';
            }
            $('.bsapyAgent').append(strhtml_select);
        } else {
            alert(oData.errMsg);
        }
    }},true,"get");

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
            content: '../agent/busiagentpay_detail.html?id=' + $(this).attr("data-id") + '&timestamp=' + (new Date()).valueOf()
        });
    });
    //已确认
    $("body").delegate(".pass","click",function(){
        var r=confirm("确认通过打款申请？");
        if (r==true){
            var bsapyUuid = $(this).attr('data-id');
            var str = 'bsapyUuid='+encodeURIComponent(bsapyUuid)+'&bsapyStatus='+3;
            getOData(str,"busiAgentPay/update/busiAgentPay",{fn:function(oData){
                    refreshList();
                }}
            );
        }
    });
    //已反驳
    $("body").delegate(".refuse","click",function(){
        var r=confirm("确认反驳打款申请？");
        if (r==true){
            var bsapyUuid = $(this).attr('data-id');
            var str = 'bsapyUuid='+encodeURIComponent(bsapyUuid)+'&bsapyStatus='+2;
            getOData(str,"busiAgentPay/update/busiAgentPay",{fn:function(oData){
                    refreshList();
                }}
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
        $(".bsapyAgent").val('');
        $(".bsapyOrder").val('');
        $(".bsapyStatus").val('-1');
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
        {name: "打款金额", percent: "10"},
        {name: "代理商名称", percent: "20"},
        {name: "汇款单号", percent: "20"},
        {name: "财务姓名", percent: "15"},
        {name: "状态", percent: "10"},
        {name: "操作", percent: "20"}];
    setTableHead(aData);
    var str = 'pageNum=' + pagenum + '&pageSize=15';
    if (obj.bsapyAgent != "") {
        str += '&bsapyAgent=' + encodeURIComponent(obj.bsapyAgent);
    }
    if (obj.bsapyOrder != "") {
        str += '&bsapyOrder=' + encodeURIComponent(obj.bsapyOrder);
    }
    if (obj.bsapyStatus != "") {
        str += '&bsapyStatus=' + encodeURIComponent(obj.bsapyStatus);
    }
    getOData(str, "busiAgentPay/find/by/cnd", {
            fn: function (oData) {
                var strhtml_list = "";
                var arrData = oData.data.result;
                var ln = arrData.length;
                for (var i = 0; i < ln; i++) {
                    var statusName = "";
                    var operationHtml = "";
                    if(arrData[i].bsapyStatus==1){
                        statusName="申请中";
                        operationHtml = '<a class="p-edit pass" data-id="'+arrData[i].bsapyUuid+'">确认</a>'+
                            '<a class="p-edit refuse" data-id="'+arrData[i].bsapyUuid+'">驳回</a>';
                    }
                    if(arrData[i].bsapyStatus==2){statusName="已驳回";}
                    if(arrData[i].bsapyStatus==3){statusName="已确认";}
                    strhtml_list += '<tr class="trHighLight" data-imageurl="' + urlfile + "/coreAttachment/image/get/" + arrData[i].bsapyPic + '">'
                        + '<td>' + '<input type="checkbox" name="checkbox" value="checkbox" data-id="' + arrData[i].bsapyUuid + '"/>' + '</td>'
                        + '<td>' + (arrData[i].bsapyFee || 0) + '</td>'
                        + '<td>' + (arrData[i].bsapyAgentCode || "") + '</td>'
                        + '<td>' + (arrData[i].bsapyOrder || "") + '</td>'
                        + '<td>' + (arrData[i].bsapyFinance || "") + '</td>'
                        + '<td>' + (statusName || "") + '</td>'
                        + '<td>'
                        + '<a  class="p-edit detailit" data-id="' + arrData[i].bsapyUuid + '">查看</a>'
                        + operationHtml
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
    if (obj.bsapyAgent != "") {
        str += '&bsapyAgent=' + encodeURIComponent(obj.bsapyAgent);
    }
    if (obj.bsapyOrder != "") {
        str += '&bsapyOrder=' + encodeURIComponent(obj.bsapyOrder);
    }
    if (obj.bsapyStatus != "") {
        str += '&bsapyStatus=' + encodeURIComponent(obj.bsapyStatus);
    }
    getOData(str, "busiAgentPay/find/by/cnd", {
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
    },true,"get");
}

function refreshList() {
    var obj = {};//查询条件对象
    searchContent(obj);
    showList(obj, parseInt($(".curpage").text()));
    layer.closeAll();
}

function searchContent(obj) {
    obj.bsapyAgent = $(".bsapyAgent").val();
    obj.bsapyOrder = $(".bsapyOrder").val();
    obj.bsapyStatus = $(".bsapyStatus").val();
}
