$(function () {
    initDetail();
});

//初始化
function initDetail() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'crrerUuid=' + encodeURIComponent(id);
    getOData(str, "coreRechargeRecord/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crrerUserMobile").text(oData.data.crrerUserMobile || "");
                $(".crrerUserName").text(oData.data.crrerUserName || "");
                var crrerStatusCH = "成功";
                if(oData.data.crrerStatus == 0) {
                    crrerStatusCH = "失败";
                }
                if(oData.data.crrerStatus == 2) {
                    crrerStatusCH = "申请充值";
                }
                $(".crrerStatus").text(crrerStatusCH || "");
                $(".crrerFee").text(oData.data.crrerFee || 0);
                $(".crrerDate").text(getFormatDate(oData.data.crrerDate) || "");
                $(".crrerOilCode").text(oData.data.crrerOilCode || "");
                $(".crrerInvoice").text(oData.data.crrerInvoice==1?"是":"否" || "");
                $(".crrerUseCoupon").text(oData.data.crrerUseCoupon || "");
                $(".crrerGetCoupon").text(oData.data.crrerGetCoupon || "");
                $(".crrerOrder").text(oData.data.crrerOrder || "");
                $(".crrerAgent").text(oData.data.crrerAgent || "");
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}
