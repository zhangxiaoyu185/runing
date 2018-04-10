$(function () {
    initDetail();
});

//初始化
function initDetail() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'bsidlUuid=' + encodeURIComponent(id);
    getOData(str, "busiIntegralDetail/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".bsidlDire").text(oData.data.bsidlDire || "");
                $(".bsidlNum").text(oData.data.bsidlNum || "");
                $(".bsidlRemark").text(oData.data.bsidlRemark || "");
                $(".bsidlUser").text(oData.data.bsidlUser || "");
                $(".bsidlCdate").text(getFormatDate(oData.data.bsidlCdate) || "");
                $(".bsidlOper").text(oData.data.bsidlOper || "");
            } else {
                alert(oData.errMsg);
            }
        }
    });
}
