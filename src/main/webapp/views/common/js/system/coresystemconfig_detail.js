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
    var str = 'crscgUuid=' + encodeURIComponent(id);
    getOData(str, "coreSystemConfig/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crscgKey").text(oData.data.crscgKey || "");
                $(".crscgValue").text(oData.data.crscgValue || "");
                $(".crscgDesc").text(oData.data.crscgDesc || "");
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}
