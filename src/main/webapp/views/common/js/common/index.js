// JavaScript Document

$(function () {
    //判断是否有权限
    if (!sessionStorage.getItem("code")) {
        window.location.href = "../login/login.html";
    }
    document.title = headerTitle;
    $(".header-title").text(headerTitle);
    //显示左侧菜单列表
    showMenuList();
    getMenuList([], 0, function (zNodes) {
        $.fn.zTree.init($("#treeMenu"), setting, zNodes);
    });
});
var setting = {
    view: {
        showLine: false,
        showIcon: false,
        selectedMulti: false,
        dblClickExpand: false,
        addDiyDom: addDiyDom
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        beforeClick: beforeClick
    }
};

function addDiyDom(treeId, treeNode) {
    var spaceWidth = 5;
    var switchObj = $("#" + treeNode.tId + "_switch"),
        icoObj = $("#" + treeNode.tId + "_ico");
}

function beforeClick(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeMenu");
    if (treeNode.getParentNode() != null) {
        sessionStorage.setItem("pmenuname", treeNode.getParentNode().name);
    }
    sessionStorage.setItem("cmenuname", treeNode.name);
    zTree.expandNode(treeNode);
    return true;
}

function getMenuList(zNodes, deleted, fn) {

    $.each($.parseJSON(sessionStorage.getItem("menuMap")), function (i, item) {
        var obj = {};
        obj.id = item.crfntUuid;
        obj.pId = item.crfntFather;
        obj.name = item.crfntFunName;
        obj.url = item.crfntResource;
        if (item.crfntResource == "#") {
            obj.target = "_self";
        } else {
            obj.target = "iframe";
        }


        obj.open = false;
        zNodes.push(obj);
    });

    fn && fn(zNodes);
}

//显示左侧菜单列表
function showMenuList() {
    var menu_obj = jQuery.parseJSON(sessionStorage.getItem("menuMap"));
    var menulist = "";
    menulist += '<li class="nav-top-li">'
        + '<p class="p-text">你好，' + sessionStorage.getItem("name") + '</p>'
        + '</li>';
    menulist += '<li class="nav-top-li">'
        + '<a class="btn-nav" href = "../common/ht-indx.html" target="iframe" >数据统计</a>'
        + '</li>';
    menulist += '<li class="nav-top-li">';
    menulist += '<ul id="treeMenu" class="menulist"></ul>';
    menulist += '</li>';
    menulist += '<li class="nav-top-li">'
        + '<a class="btn-nav" href = "../common/psd_modify.html" target="iframe" >修改密码</a>'
        + '</li>'
        + '<li class="nav-top-li">'
        + '<a class="btn-nav" href = "../login/login.html" target="_self" onclick="sessionStorage.clear();" >退出登录</a>'
        + '</li>';
    $(".nav-top").html(menulist);
}