// JavaScript Document
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
    var str = 'crscgUuid=' + encodeURIComponent(id);
    getOData(str, "coreSystemConfig/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crscgValue").val(oData.data.crscgValue || "");
                $(".crscgDesc").val(oData.data.crscgDesc || "");
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}

//检查提交
function checkModify() {
    if ($.trim($(".crscgValue").val()) == "") {
        alert("配置值不能为空，请填写完再提交！");
        $(".crscgValue").focus();
        return false;
    }

    var r = confirm("是否确认修改？");
    if (r == true) {
        var msgObject = parent.layer.msg('处理中，请等待……', {
            icon: 16,
            shade: 0.4,
            time: waitImgTime //（如果不配置，默认是3秒）
        }, function (index) {
            //do something
            parent.layer.close(index);
        });
        Modify(msgObject);
    }
}

//提交
function Modify(msgObject) {
    var crscgUuid = getQueryString("id");
    var crscgValue = $(".crscgValue").val();
    var crscgDesc = $(".crscgDesc").val();
    var str = 'crscgUuid=' + encodeURIComponent(crscgUuid) + '&crscgValue=' + encodeURIComponent(crscgValue) + '&crscgDesc=' + encodeURIComponent(crscgDesc);
    getOData(str, "coreSystemConfig/update/coreSystemConfig", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("修改成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}