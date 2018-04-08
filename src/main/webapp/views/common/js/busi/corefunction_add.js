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
    $(".crfntFunName").val("");
    $(".crfntResource").val("");
    $(".crfntOrd").val("");
    $(".crfntFather").find("input").val("");
    $(".crfntFather").find(".select-item").attr("data-ids", "");
    getFatherMenu();
}

//获取下拉框里的值
function getFatherMenu() {
    var str = '';
    getOData(str, "coreFunction/top/menu", {
        fn: function (oData) {
            if (oData.code == 1) {
                var strhtml_selectUl = '';
                strhtml_selectUl += '<input type="text" style="width:400px;" class="searchit"></input>';
                var arrData = oData.data;
                var ln = arrData.length;
                for (var i = 0; i < ln; i++) {
                    strhtml_selectUl += '<li data-id="' + arrData[i].crfntUuid + '">' + arrData[i].crfntFunName + '</li>';
                }
                $('.crfntFather').find('.select-ul').html(strhtml_selectUl);
            } else {
                alert(oData.errMsg);
            }
        }
    }, true, "get");
}

//检查提交
function checkAdd() {
    if ($.trim($(".crfntFunName").val()) == "") {
        alert("功能项名称不能为空，请填写完再提交！");
        $(".crfntFunName").focus();
        return false;
    }
    if ($.trim($(".crfntFather").find(".select-item").val()) == "") {
        alert("上级菜单不能为空，请填写完再提交！");
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
    var crfntFunName = $(".crfntFunName").val();
    var crfntResource = $(".crfntResource").val();
    var crfntOrd = $(".crfntOrd").val();
    var crfntFather = $(".crfntFather").find(".select-item").attr("data-id");
    var str = 'crfntFunName=' + encodeURIComponent(crfntFunName) + '&crfntResource=' + encodeURIComponent(crfntResource) + '&crfntOrd=' + encodeURIComponent(crfntOrd) + '&crfntFather=' + encodeURIComponent(crfntFather);
    getOData(str, "coreFunction/add/coreFunction", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("增加成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}