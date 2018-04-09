$(function () {
	initAdd();
	//提交
	$(".submit").on("click",function(){
		checkAdd();
	});
});
//初始化
function initAdd(){
		$(".bsmspUser").val("");
		$(".bsmspCdate").val("");
		$(".bsmspMonth").val("");
		$(".bsmspStep").val("");
}
//检查提交
function checkAdd(){
	    if($.trim($(".bsmspUser").val()) == ""){
		alert("所属用户不能为空，请填写完再提交！");
		$(".bsmspUser").focus();
		return false;
    }
		    if($.trim($(".bsmspCdate").val()) == ""){
		alert("创建时间不能为空，请填写完再提交！");
		$(".bsmspCdate").focus();
		return false;
    }
		    if($.trim($(".bsmspMonth").val()) == ""){
		alert("所属月不能为空，请填写完再提交！");
		$(".bsmspMonth").focus();
		return false;
    }
		    if($.trim($(".bsmspStep").val()) == ""){
		alert("步数不能为空，请填写完再提交！");
		$(".bsmspStep").focus();
		return false;
    }
	
	var r=confirm("是否确认增加？");
	if (r==true){
		var msgObject = parent.layer.msg('处理中，请等待……', {
			icon: 16,
			shade: 0.4,
			time: waitImgTime //（如果不配置，默认是3秒）
		}, function(index){
			//do something
			parent.layer.close(index);
		});
		Add(msgObject);
	}
}
//提交
function Add(msgObject){
	    var bsmspUser = $(".bsmspUser").val();
		    var bsmspCdate = $(".bsmspCdate").val();
		    var bsmspMonth = $(".bsmspMonth").val();
		    var bsmspStep = $(".bsmspStep").val();
		var str = '';
			        str+='&bsmspUser='+encodeURIComponent(bsmspUser)
					        str+='&bsmspCdate='+encodeURIComponent(bsmspCdate)
					        str+='&bsmspMonth='+encodeURIComponent(bsmspMonth)
					        str+='&bsmspStep='+encodeURIComponent(bsmspStep);
				getOData(str,"busiMonthStep/add/busiMonthStep",{
		fn:function(oData){
			window.parent.refreshList();
			alert("增加成功！");
		},
		fnerr:function(oData){
			parent.layer.close(msgObject);
		}
	});
}