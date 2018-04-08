$(function () {
    initDetail();
});

//初始化
function initDetail() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'bsapyUuid=' + encodeURIComponent(id);
    getOData(str, "busiAgentPay/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                var bsapyStatusCH = "申请中";
                if(oData.data.bsapyStatus == 2) {
                    bsapyStatusCH = "已驳回";
                } else if(oData.data.bsapyStatus == 3){
                    bsapyStatusCH = "已确认";
                }
                $(".bsapyFee").text(oData.data.bsapyFee || 0);
                $(".bsapyAgentCode").text(oData.data.bsapyAgentCode || "");
                $(".bsapyOrder").text(oData.data.bsapyOrder || "");
                getImageWidthHeight(urlfile + "/coreAttachment/image/get/" + oData.data.bsapyPic, function (realWidth, realHeight) {
                    var width = 0;
                    var height = 200;
                    //如果真实的宽度大于浏览器的宽度就按照200显示
                    if (realHeight >= height) {
                        width = realWidth / realHeight * height;
                        $(".bsapyPic_preimg").css("width", width).css("height", height);
                    } else {//如果小于浏览器的宽度按照原尺寸显示}
                        $(".bsapyPic_preimg").css("width", realWidth + 'px').css("height", realHeight + 'px');
                    }
                    $(".bsapyPic_preimg").attr("src", urlfile + "/coreAttachment/image/get/" + oData.data.bsapyPic);
                });
                $(".bsapyFinance").text(oData.data.bsapyFinance || "");
                $(".bsapyStatus").text(bsapyStatusCH || "");
                $(".bsapyCdate").text(getFormatDate(oData.data.bsapyCdate) || "");
                $(".bsapyUdate").text(getFormatDate(oData.data.bsapyUdate) || "");
            } else {
                alert(oData.errMsg);
            }
        }
    },true,"get");
}
