$(function () {
    initDetail();
});

//初始化
function initDetail() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'bsaetUuid=' + encodeURIComponent(id);
    getOData(str, "busiAgent/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".bsaetFee").text(oData.data.bsaetFee || 0);
                $(".bsaetCode").text(oData.data.bsaetCode || "");
                $(".bsaetPwd").text(oData.data.bsaetPwd || "");
            } else {
                alert(oData.errMsg);
            }
        }
    },true,"get");
}
