$(function () {
    //提交
    $(".submit").on("click",function(){
        checkModify();
    });
});

//检查提交
function checkModify(){
    if($.trim($(".bsidlDire").val()) == ""){
        alert("请选择增减方向！");
        return false;
    }
    if($.trim($(".bsidlNum").val()) == ""){
        alert("数值不能为空，请填写完再提交！");
        $(".bsidlNum").focus();
        return false;
    }
    if($.trim($(".bsidlRemark").val()) == ""){
        alert("备注不能为空，请填写完再提交！");
        $(".bsidlRemark").focus();
        return false;
    }

    var r=confirm("是否确认修改？");
    if (r==true){
        var msgObject = parent.layer.msg('处理中，请等待……', {
            icon: 16,
            shade: 0.4,
            time: waitImgTime //（如果不配置，默认是3秒）
        }, function(index){
            parent.layer.close(index);
        });
        Modify(msgObject);
    }
}

//提交
function Modify(msgObject){
    var bsidlDire = $(".bsidlDire").val();
    var bsidlNum = $(".bsidlNum").val();
    var bsidlRemark = $(".bsidlRemark").val();
    var bsidlUser = getQueryString("id");
    var bsidlOper = sessionStorage.getItem("code");;
    var str = 'bsidlDire='+encodeURIComponent(bsidlDire)
        +'&bsidlNum='+encodeURIComponent(bsidlNum)
        +'&bsidlRemark='+encodeURIComponent(bsidlRemark)
        +'&bsidlUser='+encodeURIComponent(bsidlUser)
        +'&bsidlOper='+encodeURIComponent(bsidlOper);
    getOData(str,"busiIntegralDetail/add/busiIntegralDetail",{
        fn:function(oData){
            window.parent.refreshList();
            alert("修改积分成功！");
        },
        fnerr:function(oData){
            parent.layer.close(msgObject);
        }
    });
}