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
    var str = 'bsaetUuid=' + encodeURIComponent(id);
    getOData(str, "busiInvitedBanner/views", {
        fn: function (oData) {
            if (oData.code == 1) {
                $(".bsaetLink").val(oData.data.bsaetLink || "");
                $(".bsaetOrd").val(oData.data.bsaetOrd || 0);
                $(".bsaetDesc").val(oData.data.bsaetDesc || "");
                upImgItem.changeShow('img_upload_pic', 'bsaetPic', oData.data.bsaetPic, 750, 350, '13', '13', '750X350', function (widget) {
                    var str = "cratmUuid=" + $(widget).attr('data-uuid');
                    getOData(str, "coreAttachment/delete", {
                        fn: function (oData) {
                            widget.attr('data-uuid', "");
                        },
                        fnerr: function (oData) {
                            alert(oData.errMsg);
                        }
                    });
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
            } else {
                alert(oData.errMsg);
            }
        }
    },true,"get");
}

//检查提交
function checkModify() {
    if ($.trim($(".bsaetLink").val()) == "") {
        alert("链接不能为空，请填写完再提交！");
        $(".bsaetLink").focus();
        return false;
    }
    if ($.trim($(".bsaetPic").attr('data-uuid')) == "") {
        alert("封面图不能为空，请填写完再提交！");
        return false;
    }
    if ($.trim($(".bsaetOrd").val()) == "") {
        alert("顺序不能为空，请填写完再提交！");
        $(".bsaetOrd").focus();
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
    var bsaetUuid = getQueryString("id");
    var bsaetLink = $(".bsaetLink").val();
    var bsaetPic = $(".bsaetPic").attr('data-uuid');
    var bsaetOrd = $(".bsaetOrd").val();
    var bsaetDesc = $(".bsaetDesc").val();
    var str = 'bsaetUuid=' + encodeURIComponent(bsaetUuid)
        + '&bsaetLink=' + encodeURIComponent(bsaetLink)
        + '&bsaetPic=' + encodeURIComponent(bsaetPic)
        + '&bsaetOrd=' + encodeURIComponent(bsaetOrd)
        + '&bsaetDesc=' + encodeURIComponent(bsaetDesc);
    getOData(str, "busiInvitedBanner/update/busiInvitedBanner", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("修改成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}