$(function () {
	initAdd();
	//提交
	$(".submit").on("click",function(){
		checkAdd();
	});
});
//初始化
function initAdd(){
		$(".bsuteUser").val("");
		$(".bsuteMonth").val("");
		$(".bsuteTitle").val("");
		$(".bsuteCdate").val("");
}
//检查提交
function checkAdd(){
	    if($.trim($(".bsuteUser").val()) == ""){
		alert("所属用户不能为空，请填写完再提交！");
		$(".bsuteUser").focus();
		return false;
    }
		    if($.trim($(".bsuteMonth").val()) == ""){
		alert("所属月不能为空，请填写完再提交！");
		$(".bsuteMonth").focus();
		return false;
    }
		    if($.trim($(".bsuteTitle").val()) == ""){
		alert("获得称号不能为空，请填写完再提交！");
		$(".bsuteTitle").focus();
		return false;
    }
		    if($.trim($(".bsuteCdate").val()) == ""){
		alert("创建时间不能为空，请填写完再提交！");
		$(".bsuteCdate").focus();
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
	    var bsuteUser = $(".bsuteUser").val();
		    var bsuteMonth = $(".bsuteMonth").val();
		    var bsuteTitle = $(".bsuteTitle").val();
		    var bsuteCdate = $(".bsuteCdate").val();
		var str = '';
			        str+='&bsuteUser='+encodeURIComponent(bsuteUser)
					        str+='&bsuteMonth='+encodeURIComponent(bsuteMonth)
					        str+='&bsuteTitle='+encodeURIComponent(bsuteTitle)
					        str+='&bsuteCdate='+encodeURIComponent(bsuteCdate);
				getOData(str,"busiUserTitle/add/busiUserTitle",{
		fn:function(oData){
			window.parent.refreshList();
			alert("增加成功！");
		},
		fnerr:function(oData){
			parent.layer.close(msgObject);
		}
	});
}