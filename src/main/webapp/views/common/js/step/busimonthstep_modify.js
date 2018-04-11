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
	var str = 'bsmspUuid='+encodeURIComponent(id);
	getOData(str,"busiMonthStep/views",{fn:function(oData){
		if(oData.code == 1) {
											$(".bsmspUser").val(oData.data.bsmspUser || "");
															$(".bsmspCdate").val(oData.data.bsmspCdate || "");
															$(".bsmspMonth").val(oData.data.bsmspMonth || "");
															$(".bsmspStep").val(oData.data.bsmspStep || "");
															$(".bsmspScore").val(oData.data.bsmspScore || "");
															$(".bsmspOrd").val(oData.data.bsmspOrd || "");
									} else {
			alert(oData.errMsg);
		}
	}});
}

//检查提交
function checkModify(){
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
		    if($.trim($(".bsmspScore").val()) == ""){
		alert("分值不能为空，请填写完再提交！");
		$(".bsmspScore").focus();
		return false;
    }
		    if($.trim($(".bsmspOrd").val()) == ""){
		alert("序号不能为空，请填写完再提交！");
		$(".bsmspOrd").focus();
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
	var bsmspUuid = getQueryString("id");
	    var bsmspUser = $(".bsmspUser").val();
		    var bsmspCdate = $(".bsmspCdate").val();
		    var bsmspMonth = $(".bsmspMonth").val();
		    var bsmspStep = $(".bsmspStep").val();
		    var bsmspScore = $(".bsmspScore").val();
		    var bsmspOrd = $(".bsmspOrd").val();
		var str = 'bsmspUuid='+encodeURIComponent(bsmspUuid)
			+'&bsmspUser='+encodeURIComponent(bsmspUser)
				+'&bsmspCdate='+encodeURIComponent(bsmspCdate)
				+'&bsmspMonth='+encodeURIComponent(bsmspMonth)
				+'&bsmspStep='+encodeURIComponent(bsmspStep)
				+'&bsmspScore='+encodeURIComponent(bsmspScore)
		    	+'&bsmspOrd='+encodeURIComponent(bsmspOrd);
		getOData(str,"busiMonthStep/update/busiMonthStep",{
		fn:function(oData){
			window.parent.refreshList();
			alert("修改成功！");
		},
		fnerr:function(oData){
			parent.layer.close(msgObject);
		}
	});
}