$(function () {
    initDetail();
});

//初始化
function initDetail() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'crcrdUuid=' + encodeURIComponent(id);
    getOData(str, "coreCommissionRecord/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crcrdUserMobile").text(oData.data.crcrdUserMobile || "");
                $(".crcrdUserName").text(oData.data.crcrdUserName || "");
                $(".crcrdFee").text(oData.data.crcrdFee || 0);
                $(".crcrdDate").text(getFormatDate(oData.data.crcrdDate) || "");
                $(".crcrdBeUserMobile").text(oData.data.crcrdBeUserMobile || "");
                $(".crcrdBeUserName").text(oData.data.crcrdBeUserName || "");
                $(".crcrdBeFee").text(oData.data.crcrdBeFee || 0);
                $(".crcrdRatio").text(oData.data.crcrdRatio || 0);
                $(".crcrdLevel").text(oData.data.crcrdLevel==1?"一级":"二级");
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}
