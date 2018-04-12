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
    var str = 'bsdetUuid=' + encodeURIComponent(id);
    getOData(str, "busiDept/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".bsdetName").val(oData.data.bsdetName || "");
            } else {
                alert(oData.errMsg);
            }
        }
    });
}

//检查提交
function checkModify() {
    if ($.trim($(".bsdetName").val()) == "") {
        alert("部门名称不能为空，请填写完再提交！");
        $(".bsdetName").focus();
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
    var bsdetUuid = getQueryString("id");
    var bsdetName = $(".bsdetName").val();
    var str = 'bsdetUuid=' + encodeURIComponent(bsdetUuid)
        + '&bsdetName=' + encodeURIComponent(bsdetName);
    getOData(str, "busiDept/update/busiDept", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("修改成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}