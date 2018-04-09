$(function () {
	initAdd();
	//提交
	$(".submit").on("click",function(){
		checkAdd();
	});
});
//初始化
function initAdd(){
		$(".bsdteDept").val("");
		$(".bsdteMonth").val("");
		$(".bsdteTitle").val("");
		$(".bsdteCdate").val("");
}
//检查提交
function checkAdd(){
	    if($.trim($(".bsdteDept").val()) == ""){
		alert("所属部门不能为空，请填写完再提交！");
		$(".bsdteDept").focus();
		return false;
    }
		    if($.trim($(".bsdteMonth").val()) == ""){
		alert("所属月不能为空，请填写完再提交！");
		$(".bsdteMonth").focus();
		return false;
    }
		    if($.trim($(".bsdteTitle").val()) == ""){
		alert("获得称号不能为空，请填写完再提交！");
		$(".bsdteTitle").focus();
		return false;
    }
		    if($.trim($(".bsdteCdate").val()) == ""){
		alert("创建时间不能为空，请填写完再提交！");
		$(".bsdteCdate").focus();
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
	    var bsdteDept = $(".bsdteDept").val();
		    var bsdteMonth = $(".bsdteMonth").val();
		    var bsdteTitle = $(".bsdteTitle").val();
		    var bsdteCdate = $(".bsdteCdate").val();
		var str = '';
			        str+='&bsdteDept='+encodeURIComponent(bsdteDept)
					        str+='&bsdteMonth='+encodeURIComponent(bsdteMonth)
					        str+='&bsdteTitle='+encodeURIComponent(bsdteTitle)
					        str+='&bsdteCdate='+encodeURIComponent(bsdteCdate);
				getOData(str,"busiDeptTitle/add/busiDeptTitle",{
		fn:function(oData){
			window.parent.refreshList();
			alert("增加成功！");
		},
		fnerr:function(oData){
			parent.layer.close(msgObject);
		}
	});
}