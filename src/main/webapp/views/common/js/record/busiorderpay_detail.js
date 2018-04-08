$(function () {
    initDetail();
});

//初始化
function initDetail() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'bsopyUuid=' + encodeURIComponent(id);
    getOData(str, "busiOrderPay/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".bsopyOrder").text(oData.data.bsopyOrder || "");
                $(".bsopyClientIp").text(oData.data.bsopyClientIp || "");
                $(".bsopyPayChannel").text(oData.data.bsopyPayChannel || "");
                $(".bsopyTradeType").text(oData.data.bsopyTradeType || "");
                $(".bsopyUser").text(oData.data.bsopyUser || "");
                $(".bsopyReturnUrl").text(oData.data.bsopyReturnUrl || "");
                $(".bsopyPayParams").text(oData.data.bsopyPayParams || "");
                $(".bsopyResultCode").text(oData.data.bsopyResultCode || "");
                $(".bsopyErrorCode").text(oData.data.bsopyErrorCode || "");
                $(".bsopyErrorMsg").text(oData.data.bsopyErrorMsg || "");
                $(".bsopyOutTradeNo").text(oData.data.bsopyOutTradeNo || "");
                $(".bsopyPayResult").text(oData.data.bsopyPayResult || "");
                $(".bsopyPayDate").text(oData.data.bsopyPayDate || "");
                $(".bsopyNotifyDate").text(oData.data.bsopyNotifyDate || "");
                $(".bsopyCdate").text(oData.data.bsopyCdate || "");
                $(".bsopyUdate").text(oData.data.bsopyUdate || "");
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}
