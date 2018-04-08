$(function () {
    initDetail();
});

//初始化
function initDetail() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'bscrdUuid=' + encodeURIComponent(id);
    getOData(str, "busiCashRecord/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                var statusName = "";
                if(oData.data.bscrdStatus==1){statusName="已申请";}
                if(oData.data.bscrdStatus==2){statusName="待打款";}
                if(oData.data.bscrdStatus==3){statusName="已打款";}
                if(oData.data.bscrdStatus==4){statusName="已驳回";}
                $(".bscrdAmount").text(oData.data.bscrdAmount || 0);
                $(".bscrdExtractedName").text(oData.data.bscrdExtractedName || "");
                $(".bscrdStatus").text(statusName || "");
                $(".bscrdCdate").text(getFormatDate(oData.data.bscrdCdate) || "");
                $(".bscrdUdate").text(getFormatDate(oData.data.bscrdUdate) || "");
                $(".bscrdMobile").text(oData.data.bscrdMobile || "");
                $(".bscrdAccountName").text(oData.data.bscrdAccountName || "");
                $(".bscrdAccountNo").text(oData.data.bscrdAccountNo || "");
                $(".bscrdBankName").text(oData.data.bscrdBankName || "");
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}
