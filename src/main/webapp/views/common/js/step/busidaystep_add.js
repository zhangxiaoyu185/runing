$(function () {
	initAdd();
	//提交
	$(".submit").on("click",function(){
		checkAdd();
	});
});
//初始化
function initAdd(){
		$(".bsdspUser").val("");
		$(".bsdspCdate").val("");
		$(".bsdspDay").val("");
		$(".bsdspStep").val("");
}
//检查提交
function checkAdd(){
	    if($.trim($(".bsdspUser").val()) == ""){
		alert("所属用户不能为空，请填写完再提交！");
		$(".bsdspUser").focus();
		return false;
    }
		    if($.trim($(".bsdspCdate").val()) == ""){
		alert("创建时间不能为空，请填写完再提交！");
		$(".bsdspCdate").focus();
		return false;
    }
		    if($.trim($(".bsdspDay").val()) == ""){
		alert("所属日期不能为空，请填写完再提交！");
		$(".bsdspDay").focus();
		return false;
    }
		    if($.trim($(".bsdspStep").val()) == ""){
		alert("步数不能为空，请填写完再提交！");
		$(".bsdspStep").focus();
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
	    var bsdspUser = $(".bsdspUser").val();
		    var bsdspCdate = $(".bsdspCdate").val();
		    var bsdspDay = $(".bsdspDay").val();
		    var bsdspStep = $(".bsdspStep").val();
		var str = '';
			        str+='&bsdspUser='+encodeURIComponent(bsdspUser)
					        str+='&bsdspCdate='+encodeURIComponent(bsdspCdate)
					        str+='&bsdspDay='+encodeURIComponent(bsdspDay)
					        str+='&bsdspStep='+encodeURIComponent(bsdspStep);
				getOData(str,"busiDayStep/add/busiDayStep",{
		fn:function(oData){
			window.parent.refreshList();
			alert("增加成功！");
		},
		fnerr:function(oData){
			parent.layer.close(msgObject);
		}
	});
}