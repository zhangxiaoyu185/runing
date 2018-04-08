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
        }
        else {
            thisul.fadeOut("fast");
        }
    });
});

//初始化
function initAdd() {
    $(".crrolName").val("");
    $(".crrolDesc").val("");
    $(".crrolFuns").find(".select-item").val("");
    $(".crrolFuns").find(".select-item").attr("data-ids");
    getMenu([], 0, function (zNodes) {
        $.fn.zTree.init($("#selectTree"), setting, zNodes);
    });
}

function getMenu(zNodes, deleted, fn) {
    var str = '';
    getOData(str, "coreFunction/find/all", {
        fn: function (oData) {
            $.each(oData.data, function (i, item) {
                var obj = {};
                obj.id = item.crfntUuid;
                obj.pId = item.crfntFather;
                obj.name = item.crfntFunName;
                obj.open = false;
                zNodes.push(obj);
            });

            fn && fn(zNodes);
        }
    },true,"get");
}

//检查提交
function checkAdd() {
    if ($.trim($(".crrolName").val()) == "") {
        alert("角色名称不能为空，请填写完再提交！");
        $(".crrolName").focus();
        return false;
    }
    if ($.trim($(".crrolFuns").find(".select-item").val()) == "") {
        alert("功能菜单集合不能为空，请填写完再提交！");
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
    var crrolName = $(".crrolName").val();
    var crrolFuns = $(".crrolFuns").find(".select-item").attr("data-ids");
    var crrolDesc = $(".crrolDesc").val();
    var str = 'crrolName=' + encodeURIComponent(crrolName) + '&crrolFuns=' + encodeURIComponent(crrolFuns) + '&crrolDesc=' + encodeURIComponent(crrolDesc);
    getOData(str, "coreRole/add/coreRole", {
        fn: function (oData) {
            window.parent.refreshList();
            alert("增加成功！");
        },
        fnerr: function (oData) {
            parent.layer.close(msgObject);
        }
    });
}

var setting = {
    view: {
        showIcon: false,
        selectedMulti: false
    },
    check: {
        enable: true
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeCheck: beforeCheck,
        onCheck: onCheck
    }
};

function beforeCheck(treeId, treeNode) {
    return (treeNode.doCheck !== false);
}

function onCheck(e, treeId, treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj("selectTree"),
        nodes = treeObj.getCheckedNodes(true),
        v = "",
        ids = "";
    for (var i = 0; i < nodes.length; i++) {
        v += nodes[i].name + "|";
        ids += nodes[i].id + "|";
    }
    $(".crrolFuns").find(".select-item").attr("data-ids", ids);
    $(".crrolFuns").find(".select-item").val(v);
}