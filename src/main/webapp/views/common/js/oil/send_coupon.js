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
            if (thisul.height() > 350) {
                thisul.css({height: "350" + "px", "overflow-y": "scroll"});
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
                thisinput.val($(this).text());
                thisinput.attr("data-id", $(this).attr("data-id"));
                thisinput.css('color', '#000');
                thisul.fadeOut("100");
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
    $(".crcpnUser").find("input").val("");
    $(".crcpnUser").find(".select-item").attr("data-ids", "");
    getUser();
}

//获取下拉框里的值
function getUser() {
    var str = '';
    getOData(str, "coreUser/find/all", {
        fn: function (oData) {
            if (oData.code == 1) {
                var strhtml_selectUl = '';
                strhtml_selectUl += '<input type="text" style="width:400px;" class="searchit"></input>';
                var arrData = oData.data;
                var ln = arrData.length;
                for (var i = 0; i < ln; i++) {
                    strhtml_selectUl += '<li data-id="' + arrData[i].crusrUuid + '">' + arrData[i].crusrMobile + '(' + arrData[i].crusrName + ')</li>';
                }
                $('.crcpnUser').find('.select-ul').html(strhtml_selectUl);
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}

//检查提交
function checkAdd() {
    if ($.trim($(".crcpnUser").find(".select-item").val()) == "") {
        alert("请选择赠送用户！");
        return false;
    }

    var r = confirm("是否确认赠送？");
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
    var crcpnUuid = getQueryString("id");
    var crcpnUser = $(".crcpnUser").find(".select-item").attr("data-id");
    var str = 'crcpnUuid=' + encodeURIComponent(crcpnUuid) + '&crcpnUser=' + encodeURIComponent(crcpnUser);
    getOData(str, "coreCoupon/send/user/coreCoupon", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("赠送成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}