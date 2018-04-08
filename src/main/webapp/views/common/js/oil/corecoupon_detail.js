$(function () {
    initDetail();
});

//初始化
function initDetail() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'crcpnUuid=' + encodeURIComponent(id);
    getOData(str, "coreCoupon/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crcpnUserMobile").text(oData.data.crcpnUserMobile || "");
                $(".crcpnUserName").text(oData.data.crcpnUserName || "");
                var crcpnUseStatusCH = "已使用";
                if(oData.data.crcpnUseStatus==2) {
                    crcpnUseStatusCH = "可使用";
                }
                if(oData.data.crcpnUseStatus==3) {
                    crcpnUseStatusCH = "已过期";
                }
                if(oData.data.crcpnUseStatus==4) {
                    crcpnUseStatusCH = "未分配";
                }
                $(".crcpnUseStatus").text(crcpnUseStatusCH || "");
                $(".crcpnReduce").text(oData.data.crcpnReduce || 0);
                $(".crcpnCdate").text(getFormatDate(oData.data.crcpnCdate) || "");
                $(".crcpnSendStatus").text(oData.data.crcpnSendStatus==1?"系统发送":"手动发送");
                $(".crcpnStart").text(getFormatDate(oData.data.crcpnStart) || "");
                $(".crcpnEnd").text(getFormatDate(oData.data.crcpnEnd) || "");
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}
