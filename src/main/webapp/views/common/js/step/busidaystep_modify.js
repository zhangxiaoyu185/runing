$(function () {
	initModify();
	//提交
	$(".submit").on("click",function(){
		checkModify();
	});
});

//初始化
function initModify(){
														getInfo(getQueryString("id"));
}

//获取详情
function getInfo(id){
	var str = 'bsdspUuid='+encodeURIComponent(id);
	getOData(str,"busiDayStep/views",{fn:function(oData){
		if(oData.code == 1) {
											$(".bsdspUser").val(oData.data.bsdspUser || "");
															$(".bsdspCdate").val(oData.data.bsdspCdate || "");
															$(".bsdspDay").val(oData.data.bsdspDay || "");
															$(".bsdspStep").val(oData.data.bsdspStep || "");
									} else {
			alert(oData.errMsg);
		}
	}});
}

//检查提交
function checkModify(){
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
	var bsdspUuid = getQueryString("id");
	    var bsdspUser = $(".bsdspUser").val();
		    var bsdspCdate = $(".bsdspCdate").val();
		    var bsdspDay = $(".bsdspDay").val();
		    var bsdspStep = $(".bsdspStep").val();
		var str = 'bsdspUuid='+encodeURIComponent(bsdspUuid)
			+'&bsdspUser='+encodeURIComponent(bsdspUser)
				+'&bsdspCdate='+encodeURIComponent(bsdspCdate)
				+'&bsdspDay='+encodeURIComponent(bsdspDay)
		    	+'&bsdspStep='+encodeURIComponent(bsdspStep);
		getOData(str,"busiDayStep/update/busiDayStep",{
		fn:function(oData){
			window.parent.refreshList();
			alert("修改成功！");
		},
		fnerr:function(oData){
			parent.layer.close(msgObject);
		}
	});
}