// JavaScript Document
$(function () {
    $(".header-title").text(headerTitle);
    if ($.cookie("rmbUser") == "true") {
        $("#ck_rmbUser").prop("checked", true);
        $("#name").val($.cookie("username"));
        $("#wechatName").val($.cookie("wechatName"));
    }
    $(".input-text").each(function () {
        if ($(this).val() == "账号" || $(this).val() == "密码") {
            $(this).css("color", "#ccc");
        }
        else {
            $(this).css("color", "#000");
        }
    });
    $(".input-text").on("focus", function () {
        $(this).css("color", "#000");
    });
    $(".input-text").on("blur", function () {
        if ($(this).val() == "账号" || $(this).val() == "密码") {
            $(this).css("color", "#ccc");
        }
        else {
            $(this).css("color", "#000");
        }

    });
    $("body").keypress(function (e) {
        if (e.which == "13") {//keyCode=13是回车键
            login();
        }
    });
});

//记住用户名密码 
function save() {
    if ($("#ck_rmbUser").prop("checked")) {
        $.cookie("rmbUser", "true", {
            expires: 7
        }); // 存储一个带7天期限的cookie
        $.cookie("username", $("#name").val(), {
            expires: 7
        });
        $.cookie("password", $("#password").val(), {
            expires: 7
        });
    } else {
        $.cookie("rmbUser", "false", {
            expire: -1
        });
        $.cookie("username", "", {
            expires: -1
        });
        $.cookie("password", "", {
            expires: -1
        });
    }
}

function login() {
    var username = $("#name").val();
    var password = $("#password").val();
    if (username == "" || username == "账号") {
        alert('请输入账号!');
        $("#name").focus();
        return;
    }
    if (password == "" || password == "密码") {
        alert('请输入密码!');
        $("#password").focus();
        return;
    }
    save();

    var str = "userName=" + encodeURIComponent(username) + "&pwd=" + encodeURIComponent(password);
    getOData(str, "coreAccount/login", {
            fn: function (data) {
                sessionStorage.setItem("code", data.data.cractUuid); //设置缓存键的值
                sessionStorage.setItem("name", data.data.cractName);
                sessionStorage.setItem("pwd", data.data.cractPassword);
                sessionStorage.setItem("roles", data.data.cractRoles);
                sessionStorage.setItem("menuMap", JSON.stringify(data.data.coreFunctions));
                sessionStorage.setItem("pushToken", data.data.pushToken);
                sessionStorage.setItem("pushMenu", data.data.pushMenuList);

                location.href = "../common/index.html";
            }
        }
    );
}

var JPlaceHolder = {
    //检测
    _check: function () {
        return 'placeholder' in document.createElement('input');
    },
    //初始化
    init: function () {
        if (!this._check()) {
            this.fix();
        }
    },
    //修复
    fix: function () {
        jQuery(':input[placeholder]').each(function (index, element) {
            var self = $(this), txt = self.attr('placeholder');
            self.wrap($('<span></span>').css({
                position: 'relative',
                zoom: '1',
                border: 'none',
                background: 'none',
                padding: 'none',
                margin: 'none'
            }));
            var pos = self.position(), h = self.outerHeight(true), paddingleft = self.css('padding-left');
            var holder = $('<span></span>').text(txt).css({
                position: 'absolute',
                left: pos.left,
                top: pos.top,
                height: h,
                lienHeight: h,
                paddingLeft: paddingleft,
                color: '#aaa'
            }).appendTo(self.parent());
            self.focusin(function (e) {
                holder.hide();
            }).focusout(function (e) {
                if (!self.val()) {
                    holder.show();
                }
            });
            holder.click(function (e) {
                holder.hide();
                self.focus();
            });
            if (self.val() != '') {
                holder.hide();
            }
        });
    }
};
//执行
jQuery(function () {
    JPlaceHolder.init();
});