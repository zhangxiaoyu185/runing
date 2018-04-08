// JavaScript Document
$(function () {
    initDetail();
});

//初始化
function initDetail() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'crfntUuid=' + encodeURIComponent(id);
    getOData(str, "coreFunction/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                var crfntStatusCH = "启用";
                if (oData.data.crfntStatus == 0) {
                    crfntStatusCH = "禁用";
                }
                $(".crfntFunName").text(oData.data.crfntFunName || "");
                $(".crfntResource").text(oData.data.crfntResource || "");
                $(".crfntWechatName").text(oData.data.crfntWechatName || "");
                $(".crfntStatus").text(crfntStatusCH || "");
                $(".crfntCdate").text(oData.data.crfntCdate || "");
                $(".crfntUdate").text(oData.data.crfntUdate || "");
                $(".crfntOrd").text(oData.data.crfntOrd || "");
                $(".crfntFatherName").text(oData.data.crfntFatherName || "");
            } else {
                alert(data.errMsg);
            }
        }
    }, true, "get");
}
