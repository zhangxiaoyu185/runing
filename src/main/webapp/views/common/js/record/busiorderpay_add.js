$(function () {
    initAdd();
    //提交
    $(".submit").on("click", function () {
        checkAdd();
    });
});

//初始化
function initAdd() {
    $(".bsopyOrder").val("");
    $(".bsopyClientIp").val("");
    $(".bsopyPayChannel").val("");
    $(".bsopyTradeType").val("");
    $(".bsopyUser").val("");
    $(".bsopyReturnUrl").val("");
    $(".bsopyPayParams").val("");
    $(".bsopyResultCode").val("");
    $(".bsopyErrorCode").val("");
    $(".bsopyErrorMsg").val("");
    $(".bsopyOutTradeNo").val("");
    $(".bsopyPayResult").val("");
    $(".bsopyPayDate").val("");
    $(".bsopyNotifyDate").val("");
    $(".bsopyCdate").val("");
    $(".bsopyUdate").val("");
}

//检查提交
function checkAdd() {
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

    var r = confirm("是否确认增加？");
    if (r == true) {
        var msgObject = parent.layer.msg('处理中，请等待……', {
            icon: 16,
            shade: 0.4,
            time: waitImgTime //（如果不配置，默认是3秒）
        }, function (index) {
            //do something
            parent.layer.close(index);
        });
        Add(msgObject);
    }
}

//提交
function Add(msgObject) {
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
    var str = '';
    str += '&bsopyOrder=' + encodeURIComponent(bsopyOrder)
    str += '&bsopyClientIp=' + encodeURIComponent(bsopyClientIp)
    str += '&bsopyPayChannel=' + encodeURIComponent(bsopyPayChannel)
    str += '&bsopyTradeType=' + encodeURIComponent(bsopyTradeType)
    str += '&bsopyUser=' + encodeURIComponent(bsopyUser)
    str += '&bsopyReturnUrl=' + encodeURIComponent(bsopyReturnUrl)
    str += '&bsopyPayParams=' + encodeURIComponent(bsopyPayParams)
    str += '&bsopyResultCode=' + encodeURIComponent(bsopyResultCode)
    str += '&bsopyErrorCode=' + encodeURIComponent(bsopyErrorCode)
    str += '&bsopyErrorMsg=' + encodeURIComponent(bsopyErrorMsg)
    str += '&bsopyOutTradeNo=' + encodeURIComponent(bsopyOutTradeNo)
    str += '&bsopyPayResult=' + encodeURIComponent(bsopyPayResult)
    str += '&bsopyPayDate=' + encodeURIComponent(bsopyPayDate)
    str += '&bsopyNotifyDate=' + encodeURIComponent(bsopyNotifyDate)
    str += '&bsopyCdate=' + encodeURIComponent(bsopyCdate)
    str += '&bsopyUdate=' + encodeURIComponent(bsopyUdate);
    getOData(str, "busiOrderPay/add/busiOrderPay", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("增加成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}