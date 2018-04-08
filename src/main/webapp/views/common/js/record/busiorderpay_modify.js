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
    var str = 'bsopyUuid=' + encodeURIComponent(id);
    getOData(str, "busiOrderPay/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".bsopyOrder").val(oData.data.bsopyOrder || "");
                $(".bsopyClientIp").val(oData.data.bsopyClientIp || "");
                $(".bsopyPayChannel").val(oData.data.bsopyPayChannel || "");
                $(".bsopyTradeType").val(oData.data.bsopyTradeType || "");
                $(".bsopyUser").val(oData.data.bsopyUser || "");
                $(".bsopyReturnUrl").val(oData.data.bsopyReturnUrl || "");
                $(".bsopyPayParams").val(oData.data.bsopyPayParams || "");
                $(".bsopyResultCode").val(oData.data.bsopyResultCode || "");
                $(".bsopyErrorCode").val(oData.data.bsopyErrorCode || "");
                $(".bsopyErrorMsg").val(oData.data.bsopyErrorMsg || "");
                $(".bsopyOutTradeNo").val(oData.data.bsopyOutTradeNo || "");
                $(".bsopyPayResult").val(oData.data.bsopyPayResult || "");
                $(".bsopyPayDate").val(oData.data.bsopyPayDate || "");
                $(".bsopyNotifyDate").val(oData.data.bsopyNotifyDate || "");
                $(".bsopyCdate").val(oData.data.bsopyCdate || "");
                $(".bsopyUdate").val(oData.data.bsopyUdate || "");
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}

//检查提交
function checkModify() {
    if ($.trim($(".bsopyOrder").val()) == "") {
        alert("交易单号不能为空，请填写完再提交！");
        $(".bsopyOrder").focus();
        return false;
    }
    if ($.trim($(".bsopyClientIp").val()) == "") {
        alert("客户端地址不能为空，请填写完再提交！");
        $(".bsopyClientIp").focus();
        return false;
    }
    if ($.trim($(".bsopyPayChannel").val()) == "") {
        alert("支付渠道不能为空，请填写完再提交！");
        $(".bsopyPayChannel").focus();
        return false;
    }
    if ($.trim($(".bsopyTradeType").val()) == "") {
        alert("交易类型不能为空，请填写完再提交！");
        $(".bsopyTradeType").focus();
        return false;
    }
    if ($.trim($(".bsopyUser").val()) == "") {
        alert("用户标识不能为空，请填写完再提交！");
        $(".bsopyUser").focus();
        return false;
    }
    if ($.trim($(".bsopyReturnUrl").val()) == "") {
        alert("同步通知地址不能为空，请填写完再提交！");
        $(".bsopyReturnUrl").focus();
        return false;
    }
    if ($.trim($(".bsopyPayParams").val()) == "") {
        alert("支付返回参数（返回用于前端页面支付参数）不能为空，请填写完再提交！");
        $(".bsopyPayParams").focus();
        return false;
    }
    if ($.trim($(".bsopyResultCode").val()) == "") {
        alert("业务结果不能为空，请填写完再提交！");
        $(".bsopyResultCode").focus();
        return false;
    }
    if ($.trim($(".bsopyErrorCode").val()) == "") {
        alert("错误代码不能为空，请填写完再提交！");
        $(".bsopyErrorCode").focus();
        return false;
    }
    if ($.trim($(".bsopyErrorMsg").val()) == "") {
        alert("错误描述不能为空，请填写完再提交！");
        $(".bsopyErrorMsg").focus();
        return false;
    }
    if ($.trim($(".bsopyOutTradeNo").val()) == "") {
        alert("第三方单号不能为空，请填写完再提交！");
        $(".bsopyOutTradeNo").focus();
        return false;
    }
    if ($.trim($(".bsopyPayResult").val()) == "") {
        alert("支付结果不能为空，请填写完再提交！");
        $(".bsopyPayResult").focus();
        return false;
    }
    if ($.trim($(".bsopyPayDate").val()) == "") {
        alert("支付时间不能为空，请填写完再提交！");
        $(".bsopyPayDate").focus();
        return false;
    }
    if ($.trim($(".bsopyNotifyDate").val()) == "") {
        alert("通知时间不能为空，请填写完再提交！");
        $(".bsopyNotifyDate").focus();
        return false;
    }
    if ($.trim($(".bsopyCdate").val()) == "") {
        alert("创建时间不能为空，请填写完再提交！");
        $(".bsopyCdate").focus();
        return false;
    }
    if ($.trim($(".bsopyUdate").val()) == "") {
        alert("更新时间不能为空，请填写完再提交！");
        $(".bsopyUdate").focus();
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
    var bsopyUuid = getQueryString("id");
    var bsopyOrder = $(".bsopyOrder").val();
    var bsopyClientIp = $(".bsopyClientIp").val();
    var bsopyPayChannel = $(".bsopyPayChannel").val();
    var bsopyTradeType = $(".bsopyTradeType").val();
    var bsopyUser = $(".bsopyUser").val();
    var bsopyReturnUrl = $(".bsopyReturnUrl").val();
    var bsopyPayParams = $(".bsopyPayParams").val();
    var bsopyResultCode = $(".bsopyResultCode").val();
    var bsopyErrorCode = $(".bsopyErrorCode").val();
    var bsopyErrorMsg = $(".bsopyErrorMsg").val();
    var bsopyOutTradeNo = $(".bsopyOutTradeNo").val();
    var bsopyPayResult = $(".bsopyPayResult").val();
    var bsopyPayDate = $(".bsopyPayDate").val();
    var bsopyNotifyDate = $(".bsopyNotifyDate").val();
    var bsopyCdate = $(".bsopyCdate").val();
    var bsopyUdate = $(".bsopyUdate").val();
    var str = 'bsopyUuid=' + encodeURIComponent(bsopyUuid)
        + '&bsopyOrder=' + encodeURIComponent(bsopyOrder)
        + '&bsopyClientIp=' + encodeURIComponent(bsopyClientIp)
        + '&bsopyPayChannel=' + encodeURIComponent(bsopyPayChannel)
        + '&bsopyTradeType=' + encodeURIComponent(bsopyTradeType)
        + '&bsopyUser=' + encodeURIComponent(bsopyUser)
        + '&bsopyReturnUrl=' + encodeURIComponent(bsopyReturnUrl)
        + '&bsopyPayParams=' + encodeURIComponent(bsopyPayParams)
        + '&bsopyResultCode=' + encodeURIComponent(bsopyResultCode)
        + '&bsopyErrorCode=' + encodeURIComponent(bsopyErrorCode)
        + '&bsopyErrorMsg=' + encodeURIComponent(bsopyErrorMsg)
        + '&bsopyOutTradeNo=' + encodeURIComponent(bsopyOutTradeNo)
        + '&bsopyPayResult=' + encodeURIComponent(bsopyPayResult)
        + '&bsopyPayDate=' + encodeURIComponent(bsopyPayDate)
        + '&bsopyNotifyDate=' + encodeURIComponent(bsopyNotifyDate)
        + '&bsopyCdate=' + encodeURIComponent(bsopyCdate)
        + '&bsopyUdate=' + encodeURIComponent(bsopyUdate);
    getOData(str, "busiOrderPay/update/busiOrderPay", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("修改成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}