$(function () {
    initDetail();
});

//初始化
function initDetail() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'bsoilUuid=' + encodeURIComponent(id);
    getOData(str, "busiOil/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".bsoilCode").text(oData.data.bsoilCode || "");
                $(".bsoilName").text(oData.data.bsoilName || "");
                $(".bsoilAddress").text(oData.data.bsoilAddress || "");
                $(".bsoilMobile").text(oData.data.bsoilMobile || "");
                $(".bsoilCarNo").text(oData.data.bsoilCarNo || "");
                $(".bsoilDrivingNo").text(oData.data.bsoilDrivingNo || "");
                $(".bsoilExpress").text(oData.data.bsoilExpress || "");
                var statusName = "";
                if(oData.data.bsoilStatus==1){statusName="等待审核";}
                if(oData.data.bsoilStatus==2){statusName="提交中石化";}
                if(oData.data.bsoilStatus==3){statusName="注册成功";}
                if(oData.data.bsoilStatus==4){statusName="邮寄中";}
                if(oData.data.bsoilStatus==5){statusName="正常使用";}
                if(oData.data.bsoilStatus==6){statusName="审核不通过";}
                $(".bsoilStatus").text(statusName || "");
                $(".bsoilCdate").text(getFormatDate(oData.data.bsoilCdate) || "");
                $(".bsoilUdate").text(getFormatDate(oData.data.bsoilUdate) || "");
                $(".bsoilAgent").text(oData.data.bsoilAgent || "");
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}
