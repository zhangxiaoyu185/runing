$(function () {
    initDetail();
});

//初始化
function initDetail() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'bsirnUuid=' + encodeURIComponent(id);
    getOData(str, "coreUser/invited/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".bsirnInvitedName").text(oData.data.bsirnInvitedName || "");
                $(".bsirnInvitedMobile").text(oData.data.bsirnInvitedMobile || "");
                $(".bsirnBeInvitedName").text(oData.data.bsirnBeInvitedName || "");
                $(".bsirnBeInvitedMobile").text(oData.data.bsirnBeInvitedMobile || "");
                $(".bsirnCode").text(oData.data.bsirnCode || "");
                $(".bsirnIdate").text(getFormatDate(oData.data.bsirnIdate) || "");
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}
