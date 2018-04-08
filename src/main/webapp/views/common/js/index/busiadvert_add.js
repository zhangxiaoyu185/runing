// JavaScript Document
$(function () {
    //提交
    $(".submit").on("click", function () {
        checkAdd();
    });

    var upImg = upImgItem.init('input_filt');
    $('.input_filt').on('change', function () {
        upImg.show(this,
            {
                url: urlfile + "coreAttachment/stream/upload",
                width: $(this).parent().attr('data-width'),
                height: $(this).parent().attr('data-height'),
                param: {
                    cratmType: $(this).parent().attr('data-cratmType'),
                    cratmDir: $(this).parent().attr('data-cratmDir')
                }
            },
            function (returnImagData, ajaxResult, widget) {//图片数据， 上传图片后返回的ajax数据
                if (ajaxResult.success) {
                    widget.attr('data-uuid', ajaxResult.data);
                } else {
                    alert(ajaxResult.errMsg);
                }
            }, function (widget) {
                var str = "cratmUuid=" + widget.attr('data-uuid');
                getOData(str, "coreAttachment/delete", {
                    fn: function (oData) {
                        widget.attr('data-uuid', "");
                    },
                    fnerr: function (oData) {
                        alert(oData.errMsg);
                    }
                });
            }
        );
    });
});

//检查提交
function checkAdd() {
    if ($.trim($(".bsaetLink").val()) == "") {
        alert("链接不能为空，请填写完再提交！");
        $(".bsaetLink").focus();
        return false;
    }
    if ($.trim($(".bsaetPic").attr('data-uuid')) == "") {
        alert("封面图不能为空，请填写完再提交！");
        $(".bsaetPic").focus();
        return false;
    }
    if ($.trim($(".bsaetOrd").val()) == "") {
        alert("顺序不能为空，请填写完再提交！");
        $(".bsaetOrd").focus();
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
    var bsaetLink = $(".bsaetLink").val();
    var bsaetPic = $(".bsaetPic").attr('data-uuid');
    var bsaetOrd = $(".bsaetOrd").val();
    var bsaetDesc = $(".bsaetDesc").val();
    var str = '';
    str += '&bsaetLink=' + encodeURIComponent(bsaetLink)
    str += '&bsaetPic=' + encodeURIComponent(bsaetPic)
    str += '&bsaetOrd=' + encodeURIComponent(bsaetOrd)
    str += '&bsaetDesc=' + encodeURIComponent(bsaetDesc);
    getOData(str, "busiAdvert/add/busiAdvert", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("增加成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}