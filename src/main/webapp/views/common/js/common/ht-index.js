$(function () {
    getInfo();
});

//获取统计数据
function getInfo() {
    var str = '';
    getOData(str, "total/data", {
        fn: function (oData) {
            if (oData.code == 1) {
                $("#sysYUseCount").append(oData.data.sysYUseCount);
                $("#sysYUseSum").append(oData.data.sysYUseCount);
                $("#sysKUseCount").append(oData.data.sysKUseCount);
                $("#sysKUseSum").append(oData.data.sysKUseSum);
                $("#sysWUseCount").append(oData.data.sysWUseCount);
                $("#sysWUseSum").append(oData.data.sysWUseSum);
                $("#sysYGUseCount").append(oData.data.sysYGUseCount);
                $("#sysYGUseSum").append(oData.data.sysYGUseSum);
                $("#manualYUseCount").append(oData.data.manualYUseCount);
                $("#manualYUseSum").append(oData.data.manualYUseSum);
                $("#manualKUseCount").append(oData.data.manualKUseCount);
                $("#manualKUseSum").append(oData.data.manualKUseSum);
                $("#manualWUseCount").append(oData.data.manualWUseCount);
                $("#manualWUseSum").append(oData.data.manualWUseSum);
                $("#manualYGUseCount").append(oData.data.manualYGUseCount);
                $("#manualYGUseSum").append(oData.data.manualYGUseSum);

                $("#dayRecharge").append(oData.data.dayRecharge);
                $("#monthRecharge").append(oData.data.monthRecharge);
                $("#totalRecharge").append(oData.data.totalRecharge);

                $("#dayCommission").append(oData.data.dayCommission);
                $("#monthCommission").append(oData.data.monthCommission);
                $("#totalCommission").append(oData.data.totalCommission);

                $("#dayCash").append(oData.data.dayCash);
                $("#monthCash").append(oData.data.monthCash);
                $("#totalCash").append(oData.data.totalCash);
            } else {
                alert(oData.errMsg);
            }
        }
    },true,"get");
}
