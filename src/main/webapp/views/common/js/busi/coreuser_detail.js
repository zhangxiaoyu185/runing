$(function () {
    initDetail();
});

//初始化
function initDetail() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'crusrUuid=' + encodeURIComponent(id);
    getOData(str, "coreUser/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crusrName").text(oData.data.crusrName || "");
                $(".crusrMobile").text(oData.data.crusrMobile || "");
                $(".crusrCdate").text(getFormatDate(oData.data.crusrCdate) || "");
                $(".crusrUdate").text(getFormatDate(oData.data.crusrUdate) || "");
                if(2 == oData.data.crusrGender) {
                    $(".crusrGender").text("女");
                }
                if(1 == oData.data.crusrGender) {
                    $(".crusrGender").text("男");
                }
                if(0 == oData.data.crusrGender) {
                    $(".crusrGender").text("未知");
                }
                $(".crusrRemarks").text(oData.data.crusrRemarks || "");
                $(".crusrOpenid").text(oData.data.crusrOpenid || "");
                $(".crusrWxHeadimgurl").attr("src", oData.data.crusrWxHeadimgurl);
                $(".crusrUnionid").text(oData.data.crusrUnionid || "");
                $(".crusrLastTime").text(getFormatDate(oData.data.crusrLastTime) || "");
                $(".crusrDeptName").text(oData.data.crusrDeptName || "");
                $(".crusrIntegral").text(oData.data.crusrIntegral || 0);
                $(".crusrTitle").text(oData.data.crusrTitle || "");
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}
