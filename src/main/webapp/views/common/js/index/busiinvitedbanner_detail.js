$(function () {
    initDetail();
});

//初始化
function initDetail() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'bsaetUuid=' + encodeURIComponent(id);
    getOData(str, "busiInvitedBanner/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".bsaetLink").text(oData.data.bsaetLink || "");
                $(".bsaetOrd").text(oData.data.bsaetOrd || 0);
                $(".bsaetDesc").text(oData.data.bsaetDesc || "");
                getImageWidthHeight(urlfile + "/coreAttachment/image/get/" + oData.data.bsaetPic, function (realWidth, realHeight) {
                    var width = 750;
                    var height = 350;
                    //如果真实的宽度大于浏览器的宽度就按照200显示
                    if (realHeight >= height) {
                        width = realWidth / realHeight * height;
                        $(".bsaetPic_preimg").css("width", width).css("height", height);
                    } else {//如果小于浏览器的宽度按照原尺寸显示}
                        $(".bsaetPic_preimg").css("width", realWidth + 'px').css("height", realHeight + 'px');
                    }
                    $(".bsaetPic_preimg").attr("src", urlfile + "/coreAttachment/image/get/" + oData.data.bsaetPic);
                });
                $(".bsaetCdate").text(getFormatDate(oData.data.bsaetCdate) || "");
                $(".bsaetUdate").text(getFormatDate(oData.data.bsaetUdate) || "");
            } else {
                alert(oData.errMsg);
            }
        }
    },true,"get");
}
