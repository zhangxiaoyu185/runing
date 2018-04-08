$(function () {
    initModify();
    //提交
    $(".submit").on("click", function () {
        checkModify();
    });
});

//初始化
function initModify() {
    getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id) {
    var str = 'crrerUuid=' + encodeURIComponent(id);
    getOData(str, "coreRechargeRecord/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crrerUser").val(oData.data.crrerUser || "");
                $(".crrerStatus").val(oData.data.crrerStatus || "");
                $(".crrerFee").val(oData.data.crrerFee || "");
                $(".crrerDate").val(oData.data.crrerDate || "");
                $(".crrerOil").val(oData.data.crrerOil || "");
                $(".crrerInvoice").val(oData.data.crrerInvoice || "");
                $(".crrerUseCoupon").val(oData.data.crrerUseCoupon || "");
                $(".crrerGetCoupon").val(oData.data.crrerGetCoupon || "");
                $(".crrerPayType").val(oData.data.crrerPayType || "");
                $(".crrerOrder").val(oData.data.crrerOrder || "");
                $(".crrerAgent").val(oData.data.crrerAgent || "");
                $(".crrerCallbackUrl").val(oData.data.crrerCallbackUrl || "");
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}

//检查提交
function checkModify() {
    if ($.trim($(".crrerUser").val()) == "") {
        alert("充值用户不能为空，请填写完再提交！");
        $(".crrerUser").focus();
        return false;
    }
    if ($.trim($(".crrerStatus").val()) == "") {
        alert("充值状态:1成功,0失败,2申请充值不能为空，请填写完再提交！");
        $(".crrerStatus").focus();
        return false;
    }
    if ($.trim($(".crrerFee").val()) == "") {
        alert("充值金额不能为空，请填写完再提交！");
        $(".crrerFee").focus();
        return false;
    }
    if ($.trim($(".crrerDate").val()) == "") {
        alert("充值时间不能为空，请填写完再提交！");
        $(".crrerDate").focus();
        return false;
    }
    if ($.trim($(".crrerOil").val()) == "") {
        alert("所属油卡不能为空，请填写完再提交！");
        $(".crrerOil").focus();
        return false;
    }
    if ($.trim($(".crrerInvoice").val()) == "") {
        alert("是否带发票（1是，2否）不能为空，请填写完再提交！");
        $(".crrerInvoice").focus();
        return false;
    }
    if ($.trim($(".crrerUseCoupon").val()) == "") {
        alert("使用的优惠券合集不能为空，请填写完再提交！");
        $(".crrerUseCoupon").focus();
        return false;
    }
    if ($.trim($(".crrerGetCoupon").val()) == "") {
        alert("可获得的优惠券不能为空，请填写完再提交！");
        $(".crrerGetCoupon").focus();
        return false;
    }
    if ($.trim($(".crrerPayType").val()) == "") {
        alert("支付方式（1微信支付，2银联支付）不能为空，请填写完再提交！");
        $(".crrerPayType").focus();
        return false;
    }
    if ($.trim($(".crrerOrder").val()) == "") {
        alert("交易单号不能为空，请填写完再提交！");
        $(".crrerOrder").focus();
        return false;
    }
    if ($.trim($(".crrerAgent").val()) == "") {
        alert("代理商平台(1本平台)不能为空，请填写完再提交！");
        $(".crrerAgent").focus();
        return false;
    }
    if ($.trim($(".crrerCallbackUrl").val()) == "") {
        alert("代理商回调url不能为空，请填写完再提交！");
        $(".crrerCallbackUrl").focus();
        return false;
    }

    var r = confirm("是否确认修改？");
    if (r == true) {
        var msgObject = parent.layer.msg('处理中，请等待……', {
            icon: 16,
            shade: 0.4,
            time: waitImgTime //（如果不配置，默认是3秒）
        }, function (index) {
            parent.layer.close(index);
        });
        Modify(msgObject);
    }
}

//提交
function Modify(msgObject) {
    var crrerUuid = getQueryString("id");
    var crrerUser = $(".crrerUser").val();
    var crrerStatus = $(".crrerStatus").val();
    var crrerFee = $(".crrerFee").val();
    var crrerDate = $(".crrerDate").val();
    var crrerOil = $(".crrerOil").val();
    var crrerInvoice = $(".crrerInvoice").val();
    var crrerUseCoupon = $(".crrerUseCoupon").val();
    var crrerGetCoupon = $(".crrerGetCoupon").val();
    var crrerPayType = $(".crrerPayType").val();
    var crrerOrder = $(".crrerOrder").val();
    var crrerAgent = $(".crrerAgent").val();
    var crrerCallbackUrl = $(".crrerCallbackUrl").val();
    var str = 'crrerUuid=' + encodeURIComponent(crrerUuid)
        + '&crrerUser=' + encodeURIComponent(crrerUser)
        + '&crrerStatus=' + encodeURIComponent(crrerStatus)
        + '&crrerFee=' + encodeURIComponent(crrerFee)
        + '&crrerDate=' + encodeURIComponent(crrerDate)
        + '&crrerOil=' + encodeURIComponent(crrerOil)
        + '&crrerInvoice=' + encodeURIComponent(crrerInvoice)
        + '&crrerUseCoupon=' + encodeURIComponent(crrerUseCoupon)
        + '&crrerGetCoupon=' + encodeURIComponent(crrerGetCoupon)
        + '&crrerPayType=' + encodeURIComponent(crrerPayType)
        + '&crrerOrder=' + encodeURIComponent(crrerOrder)
        + '&crrerAgent=' + encodeURIComponent(crrerAgent)
        + '&crrerCallbackUrl=' + encodeURIComponent(crrerCallbackUrl);
    getOData(str, "coreRechargeRecord/update/coreRechargeRecord", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("修改成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}