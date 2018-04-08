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
                $(".crusrUnionid").text(oData.data.crusrUnionid || "");
                $(".crusrIdCard").text(oData.data.crusrIdCard || "");
                $(".crusrAccountName").text(oData.data.crusrAccountName || "");
                $(".crusrAccountNo").text(oData.data.crusrAccountNo || "");
                $(".crusrBankName").text(oData.data.crusrBankName || "");
                $(".crusrWxHeadimgurl").attr("src", oData.data.crusrWxHeadimgurl);
                getImageWidthHeight(urlfile + "/coreAttachment/image/get/" + oData.data.crusrDrivingPic, function (realWidth, realHeight) {
                    var width = 350;
                    var height = 200;
                    //如果真实的宽度大于浏览器的宽度就按照200显示
                    if (realHeight >= height) {
                        width = realWidth / realHeight * height;
                        $(".crusrDrivingPic_preimg").css("width", width).css("height", height);
                    } else {//如果小于浏览器的宽度按照原尺寸显示}
                        $(".crusrDrivingPic_preimg").css("width", realWidth + 'px').css("height", realHeight + 'px');
                    }
                    $(".crusrDrivingPic_preimg").attr("src", urlfile + "/coreAttachment/image/get/" + oData.data.crusrDrivingPic);
                });
                getImageWidthHeight(urlfile + "/coreAttachment/image/get/" + oData.data.crusrDrivingPicOther, function (realWidth, realHeight) {
                    var width = 350;
                    var height = 200;
                    //如果真实的宽度大于浏览器的宽度就按照200显示
                    if (realHeight >= height) {
                        width = realWidth / realHeight * height;
                        $(".crusrDrivingPicOther_preimg").css("width", width).css("height", height);
                    } else {//如果小于浏览器的宽度按照原尺寸显示}
                        $(".crusrDrivingPicOther_preimg").css("width", realWidth + 'px').css("height", realHeight + 'px');
                    }
                    $(".crusrDrivingPicOther_preimg").attr("src", urlfile + "/coreAttachment/image/get/" + oData.data.crusrDrivingPicOther);
                });
                getImageWidthHeight(urlfile + "/coreAttachment/image/get/" + oData.data.crusrInvitedCode, function (realWidth, realHeight) {
                    var width = 200;
                    var height = 200;
                    //如果真实的宽度大于浏览器的宽度就按照200显示
                    if (realHeight >= height) {
                        width = realWidth / realHeight * height;
                        $(".crusrInvitedCode_preimg").css("width", width).css("height", height);
                    } else {//如果小于浏览器的宽度按照原尺寸显示}
                        $(".crusrDrivingPicOther_preimg").css("width", realWidth + 'px').css("height", realHeight + 'px');
                    }
                    $(".crusrInvitedCode_preimg").attr("src", urlfile + "/coreAttachment/image/get/" + oData.data.crusrInvitedCode);
                });
                $(".crusrWeekIncome").text(oData.data.crusrWeekIncome || 0);
                $(".crusrTotalIncome").text(oData.data.crusrTotalIncome || 0);
                $(".crusrEnableIncome").text(oData.data.crusrEnableIncome || 0);
                $(".crusrFrozenIncome").text(oData.data.crusrFrozenIncome || 0);
                $(".crusrDownCount").text(oData.data.crusrDownCount || 0);
                $(".crusrInviter").text(oData.data.crusrInviter || "");
                $(".crusrLastTime").text(getFormatDate(oData.data.crusrLastTime) || "");
                $(".crusrInviteCode").text(oData.data.crusrInviteCode || "");
                $(".crusrAgent").text(oData.data.crusrAgent || "");
                $(".crusrCarNo").text(oData.data.crusrCarNo || "");
                $(".crusrDrivingNo").text(oData.data.crusrDrivingNo || "");
                $(".crusrBusinessNo").text(oData.data.crusrBusinessNo || "");
                var crusrTypeCH = "个人";
                if(oData.data.crusrType == 2) {
                    crusrTypeCH = "公司";
                } else if(oData.data.crusrType == 3){
                    crusrTypeCH = "未知";
                }
                $(".crusrType").text(crusrTypeCH || "");
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}
