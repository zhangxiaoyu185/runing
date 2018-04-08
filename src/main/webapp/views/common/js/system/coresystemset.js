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
                $(".crsstInviteLink").val(oData.data.crsstInviteLink || "");
                $(".crsstFirstIncome").val(oData.data.crsstFirstIncome || 0);
                $(".crsstSecondIncome").val(oData.data.crsstSecondIncome || 0);
                $(".crsstCoupon").val(oData.data.crsstCoupon || 0);
                $(".crsstMessagePath").val(oData.data.crsstMessagePath || "");
                $(".crsstMessageLoginname").val(oData.data.crsstMessageLoginname || "");
                $(".crsstMessagePwd").val(oData.data.crsstMessagePwd || "");
                $(".crsstMessageKey").val(oData.data.crsstMessageKey || "");
                $(".crsstMessageDomain").val(oData.data.crsstMessageDomain || "");
                $(".crsstAttachmentDir").val(oData.data.crsstAttachmentDir || "");
            } else {
                alert(data.errMsg);
            }
        }
    }, true, "get");
}

//检查提交
function checkModify() {
    if ($.trim($(".crsstInviteLink").val()) == "") {
        alert("邀请链接前缀不能为空，请填写完再提交！");
        $(".crsstInviteLink").focus();
        return false;
    }
    if ($.trim($(".crsstFirstIncome").val()) == "") {
        alert("一级佣金比不能为空，请填写完再提交！");
        $(".crsstFirstIncome").focus();
        return false;
    }
    if ($.trim($(".crsstSecondIncome").val()) == "") {
        alert("二级佣金比不能为空，请填写完再提交！");
        $(".crsstSecondIncome").focus();
        return false;
    }
    if ($.trim($(".crsstCoupon").val()) == "") {
        alert("返还优惠券比例不能为空，请填写完再提交！");
        $(".crsstCoupon").focus();
        return false;
    }
    if ($.trim($(".crsstMessagePath").val()) == "") {
        alert("短信接口路径不能为空，请填写完再提交！");
        $(".crsstMessagePath").focus();
        return false;
    }
    if ($.trim($(".crsstMessageLoginname").val()) == "") {
        alert("短信账户名不能为空，请填写完再提交！");
        $(".crsstMessageLoginname").focus();
        return false;
    }
    if ($.trim($(".crsstMessagePwd").val()) == "") {
        alert("短信密码不能为空，请填写完再提交！");
        $(".crsstMessagePwd").focus();
        return false;
    }
    if ($.trim($(".crsstMessageKey").val()) == "") {
        alert("短信key不能为空，请填写完再提交！");
        $(".crsstMessageKey").focus();
        return false;
    }
    if ($.trim($(".crsstMessageDomain").val()) == "") {
        alert("项目域名不能为空，请填写完再提交！");
        $(".crsstMessageDomain").focus();
        return false;
    }
    if ($.trim($(".crsstAttachmentDir").val()) == "") {
        alert("附件存放目录不能为空，请填写完再提交！");
        $(".crsstAttachmentDir").focus();
        return false;
    }

    var r = confirm("是否确认修改？");
    if (r == true) {
        Modify();
    }
}

//提交
function Modify() {
    var crsstInviteLink = $(".crsstInviteLink").val();
    var crsstFirstIncome = $(".crsstFirstIncome").val();
    var crsstSecondIncome = $(".crsstSecondIncome").val();
    var crsstCoupon = $(".crsstCoupon").val();
    var crsstMessagePath = $(".crsstMessagePath").val();
    var crsstMessageLoginname = $(".crsstMessageLoginname").val();
    var crsstMessagePwd = $(".crsstMessagePwd").val();
    var crsstMessageKey = $(".crsstMessageKey").val();
    var crsstMessageDomain = $(".crsstMessageDomain").val();
    var crsstAttachmentDir = $(".crsstAttachmentDir").val();
    var str = 'crsstInviteLink=' + encodeURIComponent(crsstInviteLink)
        + '&crsstFirstIncome=' + encodeURIComponent(crsstFirstIncome)
        + '&crsstSecondIncome=' + encodeURIComponent(crsstSecondIncome)
        + '&crsstCoupon=' + encodeURIComponent(crsstCoupon)
        + '&crsstMessagePath=' + encodeURIComponent(crsstMessagePath)
        + '&crsstMessageLoginname=' + encodeURIComponent(crsstMessageLoginname)
        + '&crsstMessagePwd=' + encodeURIComponent(crsstMessagePwd)
        + '&crsstMessageKey=' + encodeURIComponent(crsstMessageKey)
        + '&crsstMessageDomain=' + encodeURIComponent(crsstMessageDomain)
        + '&crsstAttachmentDir=' + encodeURIComponent(crsstAttachmentDir);
    getOData(str, "coreSystemSet/update/coreSystemSet", {
        fn: function (oData) {
            aler("修改成功！");
        },
        fnerr: function (oData) {
            alert("修改失败！");
        }
    });
}