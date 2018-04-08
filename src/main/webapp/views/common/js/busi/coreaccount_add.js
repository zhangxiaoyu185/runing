// JavaScript Document
$(function () {
    initAdd();
    //提交
    $(".submit").on("click", function () {
        checkAdd();
    });

    //下拉选择框
    $(".select-item").unbind().on('click', function () {
        var thisinput = $(this);
        var thisul = $(this).parent().find("ul");
        if (thisul.css("display") == "none") {
            if (thisul.height() > 250) {
                thisul.css({height: "250" + "px", "overflow-y": "scroll"});
            }
            thisul.fadeIn("100");
            thisul.hover(function () {
            }, function () {
                thisul.fadeOut("100");
            });
            thisul.find(".searchit").focus();
            thisul.find('.searchit').on('keyup', function () {
                var searchText = $(this).val();
                thisul.find("li").each(function () {
                    if ($(this).text().indexOf(searchText) > -1) {
                        $(this).show();
                    }
                    else {
                        $(this).hide();
                    }
                });
            });
            thisul.find("li").unbind().click(function () {
                var ids = "";
                var str_val = "";
                if ($(this).find('input').is(':checked')) {
                    $(this).find("input").attr("checked", '');
                    $(this).find("input").removeAttr("checked");
                }
                else {
                    $(this).find("input").attr("checked", 'checked');
                }
                $(this).parent().find(":checkbox").each(function (i) {
                    if ($(this).attr("checked")) {
                        ids += $(this).parent("li").attr("data-id") + "|";
                        str_val += $(this).parent("li").text() + "|";
                    }
                });
                thisinput.val(str_val);

                thisinput.attr("data-ids", ids);
                thisinput.css('color', '#000');
                //thisul.fadeOut("100");
            }).hover(function () {
                    $(this).addClass("select-hover");
                },
                function () {
                    $(this).removeClass("select-hover");
                });
        }
        else {
            thisul.fadeOut("fast");
        }
    });
});

//初始化
function initAdd() {
    $(".cractName").val("");
    $(".cractPassword").val("");
    $(".surePsd").val("");
    $(".cractTel").val("");
    $(".cractEmail").val("");
    $(".cractRemarks").val("");
    $(".cractRoles").find("input").val("");
    $(".cractRoles").find(".select-item").attr("data-ids", "");
    getRoles();
}

//获取下拉框里的值
function getRoles() {
    var str = 'crrolWechat=' + sessionStorage.getItem("weixinUuid");
    getOData(str, "coreRole/find/all", {
        fn: function (oData) {
            if (oData.code == 1) {
                var strhtml_selectUl = '';
                strhtml_selectUl += '<input type="text" style="width:400px;" class="searchit"></input>';
                var arrData = oData.data;
                var ln = arrData.length;
                for (var i = 0; i < ln; i++) {
                    strhtml_selectUl += '<li data-id="' + arrData[i].crrolUuid + '"><input type="checkbox" name="checkbox"/>' + arrData[i].crrolName + '</li>';
                }
                $('.cractRoles').find('.select-ul').html(strhtml_selectUl);
            } else {
                alert(oData.errMsg);
            }
        }
    },true,"get");
}

//检查提交
function checkAdd() {
    if ($.trim($(".cractName").val()) == "") {
        alert("帐户名称不能为空，请填写完再提交！");
        $(".cractName").focus();
        return false;
    }
    if ($.trim($(".cractPassword").val()) == "") {
        alert("登录密码不能为空，请填写完再提交！");
        $(".cractPassword").focus();
        return false;
    }
    if ($.trim($(".surePsd").val()) == "") {
        alert("确认密码不能为空，请填写完再提交！");
        $(".surePsd").focus();
        return false;
    }
    if ($(".surePsd").val() != $(".cractPassword").val()) {
        alert("确认密码不正确，请重新输入后再提交！");
        $(".surePsd").focus();
        return false;
    }
    if ($.trim($(".cractRoles").find(".select-item").val()) == "") {
        alert("角色集合不能为空，请填写完再提交！");
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
    var cractName = $(".cractName").val();
    var cractPassword = $(".cractPassword").val();
    var cractWechat = sessionStorage.getItem("weixinUuid");
    var cractRoles = $(".cractRoles").find(".select-item").attr("data-ids");
    var cractTel = $(".cractTel").val();
    var cractEmail = $(".cractEmail").val();
    var cractRemarks = $(".cractRemarks").val();
    var str = 'cractName=' + encodeURIComponent(cractName) + '&cractPassword=' + encodeURIComponent(cractPassword) + '&cractWechat=' + encodeURIComponent(cractWechat) + '&cractRoles=' + encodeURIComponent(cractRoles) + '&cractTel=' + encodeURIComponent(cractTel) + '&cractEmail=' + encodeURIComponent(cractEmail) + '&cractRemarks=' + encodeURIComponent(cractRemarks);
    getOData(str, "coreAccount/add/coreAccount", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("增加成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}