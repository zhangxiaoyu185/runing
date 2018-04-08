// JavaScript Document
$(function () {
	initAdd();
	//提交
	$(".submit").on("click",function(){
		checkAdd();
	});
});
//初始化
function initAdd(){
	$(".crscgKey").val("");
	$(".crscgValue").val("");
	$(".crscgDesc").val("");	
}
//检查提交
function checkAdd(){
	if($.trim($(".crscgKey").val()) == ""){
		alert("配置KEY不能为空，请填写完再提交！");
		$(".crscgKey").focus();
		return false;
	}
	if($.trim($(".crscgValue").val()) == ""){
		alert("配置值不能为空，请填写完再提交！");
		$(".crscgValue").focus();
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
	var crscgKey = $(".crscgKey").val();
	var crscgValue = $(".crscgValue").val();
	var crscgDesc = $(".crscgDesc").val();
	var str = 'crscgKey='+encodeURIComponent(crscgKey)+'&crscgValue='+encodeURIComponent(crscgValue)+'&crscgDesc='+encodeURIComponent(crscgDesc);
	getOData(str,"coreSystemConfig/add/coreSystemConfig",{
		fn:function(oData){
			window.parent.refreshList();
			alert("增加成功！");
		},
		fnerr:function(oData){
			parent.layer.close(msgObject);
		}
	});
}