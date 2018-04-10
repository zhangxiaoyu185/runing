$(function () {
    getInfo();
});

//获取统计数据
function getInfo() {
    var str = '';
    getOData(str, "total/data", {
        fn: function (oData) {
            if (oData.code == 1) {
                $("#daycount").html((oData.data.daycount||"")+"步");
                $("#weekcount").html((oData.data.weekcount||"")+"步");
                $("#monthcount").html((oData.data.monthcount||"")+"步");
                $("#usercount").html((oData.data.usercount||"")+"人");
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}
