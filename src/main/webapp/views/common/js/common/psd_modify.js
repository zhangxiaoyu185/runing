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
    $(".accountName").text(sessionStorage.getItem("name"));
}

//检查提交
function checkModify() {
    if ($.trim($(".oldPsd").val()) == "") {
        alert("旧密码不能为空，请填写完再提交！");
        $(".oldPsd").focus();
        return false;
    }
    if ($.trim($(".newPsd").val()) == "") {
        alert("新密码不能为空，请填写完再提交！");
        $(".newPsd").focus();
        return false;
    }
    if ($.trim($(".surePsd").val()) == "") {
        alert("确认密码不能为空，请填写完再提交！");
        $(".surePsd").focus();
        return false;
    }
    if ($(".surePsd").val() != $(".newPsd").val()) {
        alert("确认密码不正确，请重新输入后再提交！");
        $(".surePsd").focus();
        return false;
    }
    var r = confirm("是否确认修改？");
    if (r == true) {
        Modify();
    }
}

//提交
function Modify() {
    var cractUuid = sessionStorage.getItem("code");
    var newPwd = $(".newPsd").val();
    var confirmPwd = $(".surePsd").val();
    var oldPwd = $(".oldPsd").val();
    var str = 'cractUuid=' + encodeURIComponent(cractUuid) + '&oldPwd=' + encodeURIComponent(oldPwd) + '&newPwd=' + encodeURIComponent(newPwd) + '&confirmPwd=' + encodeURIComponent(confirmPwd);
    getOData(str, "coreAccount/update/pwd", {
        fn: function (oData) {
            alert("修改成功！");
            sessionStorage.clear();
            top.location.href = "../login/login.html";
        }
    });
}
