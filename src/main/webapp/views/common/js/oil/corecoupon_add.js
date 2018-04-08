$(function () {
    initAdd();
    //提交
    $(".submit").on("click", function () {
        checkAdd();
    });
});

//初始化
function initAdd() {
    $(".crcpnReduce").val("");
    $(".count").val("");
    $(".crcpnStart").val("");
    $(".crcpnEnd").val("");
}

//检查提交
function checkAdd() {
    if ($.trim($(".crcpnReduce").val()) == "") {
        alert("优惠金额不能为空，请填写完再提交！");
        $(".crcpnReduce").focus();
        return false;
    }
    if ($.trim($(".count").val()) == "") {
        alert("生成数量不能为空，请填写完再提交！");
        $(".count").focus();
        return false;
    }
    if ($.trim($(".crcpnStart").val()) == "") {
        alert("请选择生效时间！");
        $(".crcpnStart").focus();
        return false;
    }
    if ($.trim($(".crcpnEnd").val()) == "") {
        alert("请选择截止时间！");
        $(".crcpnEnd").focus();
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
    var crcpnReduce = $(".crcpnReduce").val();
    var count = $(".count").val();
    var crcpnStart = $(".crcpnStart").val();
    var crcpnEnd = $(".crcpnEnd").val();
    var str = '';
    str += '&crcpnReduce=' + encodeURIComponent(crcpnReduce)
    str += '&count=' + encodeURIComponent(count)
    str += '&crcpnStart=' + encodeURIComponent(crcpnStart)
    str += '&crcpnEnd=' + encodeURIComponent(crcpnEnd);
    getOData(str, "coreCoupon/add/coreCoupon", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("增加成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}