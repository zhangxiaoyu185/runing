$(function () {
    initModify();
    //提交
    $(".submit").on("click", function () {
        checkModify();
    });
});

//初始化
function initModify() {
    getInfo();
}

//获取详情
function getInfo() {
    var str = '';
    getOData(str, "coreWechat/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crwctName").val(oData.data.crwctName || "");
                $(".crwctAppid").val(oData.data.crwctAppid || "");
                $(".crwctAppsecret").val(oData.data.crwctAppsecret || "");
                $(".crwctPartner").val(oData.data.crwctPartner || "");
                $(".crwctPartnerkey").val(oData.data.crwctPartnerkey || "");
                $(".crwctNotifyurl").val(oData.data.crwctNotifyurl || "");
                $(".crwctRemarks").val(oData.data.crwctRemarks || "");
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}

//检查提交
function checkModify() {
    if ($.trim($(".crwctName").val()) == "") {
        alert("小程序名称不能为空，请填写完再提交！");
        $(".crwctName").focus();
        return false;
    }
    if ($.trim($(".crwctAppid").val()) == "") {
        alert("appid不能为空，请填写完再提交！");
        $(".crwctAppidStore").focus();
        return false;
    }
    if ($.trim($(".crwctAppsecret").val()) == "") {
        alert("appsecret不能为空，请填写完再提交！");
        $(".crwctAppsecretStore").focus();
        return false;
    }

    var r = confirm("是否确认修改？");
    if (r == true) {
        var crwctName = $(".crwctName").val();
        var crwctAppid = $(".crwctAppid").val();
        var crwctAppsecret = $(".crwctAppsecret").val();
        var crwctPartner = $(".crwctPartnerStore").val();
        var crwctPartnerkey = $(".crwctPartnerkeyStore").val();
        var crwctRemarks = $(".crwctRemarksStore").val();
        var str = 'crwctName=' + encodeURIComponent(crwctName) + '&crwctAppid=' + encodeURIComponent(crwctAppid) + '&crwctAppsecret=' + encodeURIComponent(crwctAppsecret)
            + '&crwctPartner=' + encodeURIComponent(crwctPartner) + '&crwctPartnerkey=' + encodeURIComponent(crwctPartnerkey) + '&crwctRemarks=' + encodeURIComponent(crwctRemarks);
        getOData(str, "coreWechat/update/coreWechat", {
            fn: function (oData) {
                alert("修改成功！");
            },
            fnerr: function (oData) {
                alert("修改失败！");
            }
        });
    }
}
