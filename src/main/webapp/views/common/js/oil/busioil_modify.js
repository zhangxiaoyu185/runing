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
    var str = 'bsoilUuid=' + encodeURIComponent(id);
    getOData(str, "busiOil/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".bsoilAddrress").text(oData.data.bsoilAddrress || "");
                $(".bsoilName").text(oData.data.bsoilName || "");
                $(".bsoilMobile").text(oData.data.bsoilMobile || "");
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}

//检查提交
function checkModify() {
    if ($.trim($(".bsoilCode").val()) == "") {
        alert("油卡卡号不能为空，请填写完再提交！");
        $(".bsoilCode").focus();
        return false;
    }

    var r = confirm("是否确认注册成功？");
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
    var bsoilUuid = getQueryString("id");
    var bsoilCode = $(".bsoilCode").val();
    var str = 'bsoilUuid=' + encodeURIComponent(bsoilUuid)
        + '&bsoilCode=' + encodeURIComponent(bsoilCode)
        + '&bsoilStatus=3';
    getOData(str, "busiOil/update/busiOil", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("注册成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}