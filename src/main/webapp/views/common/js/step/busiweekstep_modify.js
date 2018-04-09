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
	var str = 'bswspUuid='+encodeURIComponent(id);
	getOData(str,"busiWeekStep/views",{fn:function(oData){
		if(oData.code == 1) {
											$(".bswspUser").val(oData.data.bswspUser || "");
															$(".bswspCdate").val(oData.data.bswspCdate || "");
															$(".bswspWeek").val(oData.data.bswspWeek || "");
															$(".bswspStep").val(oData.data.bswspStep || "");
									} else {
			alert(oData.errMsg);
		}
	}});
}

//检查提交
function checkModify(){
	    if($.trim($(".bswspUser").val()) == ""){
		alert("所属用户不能为空，请填写完再提交！");
		$(".bswspUser").focus();
		return false;
    }
		    if($.trim($(".bswspCdate").val()) == ""){
		alert("创建时间不能为空，请填写完再提交！");
		$(".bswspCdate").focus();
		return false;
    }
		    if($.trim($(".bswspWeek").val()) == ""){
		alert("所属周不能为空，请填写完再提交！");
		$(".bswspWeek").focus();
		return false;
    }
		    if($.trim($(".bswspStep").val()) == ""){
		alert("步数不能为空，请填写完再提交！");
		$(".bswspStep").focus();
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
	var bswspUuid = getQueryString("id");
	    var bswspUser = $(".bswspUser").val();
		    var bswspCdate = $(".bswspCdate").val();
		    var bswspWeek = $(".bswspWeek").val();
		    var bswspStep = $(".bswspStep").val();
		var str = 'bswspUuid='+encodeURIComponent(bswspUuid)
			+'&bswspUser='+encodeURIComponent(bswspUser)
				+'&bswspCdate='+encodeURIComponent(bswspCdate)
				+'&bswspWeek='+encodeURIComponent(bswspWeek)
		    	+'&bswspStep='+encodeURIComponent(bswspStep);
		getOData(str,"busiWeekStep/update/busiWeekStep",{
		fn:function(oData){
			window.parent.refreshList();
			alert("修改成功！");
		},
		fnerr:function(oData){
			parent.layer.close(msgObject);
		}
	});
}