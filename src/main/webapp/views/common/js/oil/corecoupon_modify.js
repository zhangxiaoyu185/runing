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
    var str = 'crcpnUuid=' + encodeURIComponent(id);
    getOData(str, "coreCoupon/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crcpnReduce").val(oData.data.crcpnReduce || 0);
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}

//检查提交
function checkModify() {
    if ($.trim($(".crcpnReduce").val()) == "") {
        alert("优惠金额不能为空，请填写完再提交！");
        $(".crcpnReduce").focus();
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
    var crcpnUuid = getQueryString("id");
    var crcpnReduce = $(".crcpnReduce").val();
    var str = 'crcpnUuid=' + encodeURIComponent(crcpnUuid)
        + '&crcpnReduce=' + encodeURIComponent(crcpnReduce);
    getOData(str, "coreCoupon/update/coreCoupon", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("修改成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}