$(function () {
	initAdd();
	//提交
	$(".submit").on("click",function(){
		checkAdd();
	});
});
//初始化
function initAdd(){
		$(".bsdetName").val("");
		$(".bsdetCdate").val("");
		$(".bsdetUdate").val("");
}
//检查提交
function checkAdd(){
	    if($.trim($(".bsdetName").val()) == ""){
		alert("部门名称不能为空，请填写完再提交！");
		$(".bsdetName").focus();
		return false;
    }
		    if($.trim($(".bsdetCdate").val()) == ""){
		alert("创建时间不能为空，请填写完再提交！");
		$(".bsdetCdate").focus();
		return false;
    }
		    if($.trim($(".bsdetUdate").val()) == ""){
		alert("更新时间不能为空，请填写完再提交！");
		$(".bsdetUdate").focus();
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
	    var bsdetName = $(".bsdetName").val();
		    var bsdetCdate = $(".bsdetCdate").val();
		    var bsdetUdate = $(".bsdetUdate").val();
		var str = '';
			        str+='&bsdetName='+encodeURIComponent(bsdetName)
					        str+='&bsdetCdate='+encodeURIComponent(bsdetCdate)
					        str+='&bsdetUdate='+encodeURIComponent(bsdetUdate);
				getOData(str,"busiDept/add/busiDept",{
		fn:function(oData){
			window.parent.refreshList();
			alert("增加成功！");
		},
		fnerr:function(oData){
			parent.layer.close(msgObject);
		}
	});
}