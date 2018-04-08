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
    var str = 'crrolUuid=' + encodeURIComponent(id);
    getOData(str, "coreRole/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crrolName").text(oData.data.crrolName || "");
                $(".crrolFunsName").text(oData.data.crrolFunsName || "");
                $(".crrolWechatName").text(oData.data.crrolWechatName || "");
                $(".crrolDesc").text(oData.data.crrolDesc || "");
            } else {
                alert(data.errMsg);
            }
        }
    }, true, "get");
}
