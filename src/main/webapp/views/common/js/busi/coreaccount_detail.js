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
    var str = 'cractUuid=' + encodeURIComponent(id);
    getOData(str, "coreAccount/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                var cractStatusCH = "启用";
                if (oData.data.cractStatus == 0) {
                    cractStatusCH = "禁用";
                }
                $(".cractName").text(oData.data.cractName || "");
                $(".cractStatus").text(cractStatusCH || "");
                $(".cractRolesName").text(oData.data.cractRolesName || "");
                $(".cractCdate").text(oData.data.cractCdate || "");
                $(".cractUdate").text(oData.data.cractUdate || "");
                $(".cractTel").text(oData.data.cractTel || "");
                $(".cractEmail").text(oData.data.cractEmail || "");
                $(".cractRemarks").text(oData.data.cractRemarks || "");
            } else {
                alert(data.errMsg);
            }
        }
    },true,"get");
}
