$(function () {
    initDetail();
});

//初始化
function initDetail() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'crmceUuid=' + encodeURIComponent(id);
    getOData(str, "coreShortMessage/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crmceMobile").text(oData.data.crmceMobile || "");
                $(".crmceContent").text(oData.data.crmceContent || "");
                $(".crmceTime").text(getFormatDate(oData.data.crmceTime) || "");
                $(".crmceTimeout").text(getFormatDate(oData.data.crmceTimeout) || "");
                if (1 == oData.data.crmceStatus) {
                    $(".crmceStatus").text("成功");
                }
                if (0 == oData.data.crmceStatus) {
                    $(".crmceStatus").text("失败");
                }
                if (1 == oData.data.crmceType) {
                    $(".crmceType").text("验证码");
                }
                if (0 == oData.data.crmceType) {
                    $(".crmceType").text("其它");
                }
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}
