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
    getOData(str, "coreSystemSet/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".crsstDayContent").text(oData.data.crsstDayContent || "");
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}

//检查提交
function checkModify() {
    if ($.trim($(".crsstDayContent").val()) == "") {
        alert("每日一言内容不能为空，请填写完再提交！");
        $(".crsstDayContent").focus();
        return false;
    }
    if ($.trim($(".crsstDayContent").val()).length > 60) {
        alert("每日一言内容长度请控制在60字以内！");
        $(".crsstDayContent").focus();
        return false;
    }
    var r = confirm("是否确认修改？");
    if (r == true) {
        Modify();
    }
}

//提交
function Modify() {
    var crsstDayContent = $(".crsstDayContent").val();
    var str = 'crsstDayContent=' + encodeURIComponent(crsstDayContent);
    getOData(str, "coreSystemSet/update/coreSystemSet", {
        fn: function (oData) {
            aler("修改成功！");
        },
        fnerr: function (oData) {
            alert("修改失败！");
        }
    });
}