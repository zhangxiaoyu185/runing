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
	var str = 'bsuteUuid='+encodeURIComponent(id);
	getOData(str,"busiUserTitle/views",{fn:function(oData){
		if(oData.code == 1) {
											$(".bsuteUser").val(oData.data.bsuteUser || "");
															$(".bsuteMonth").val(oData.data.bsuteMonth || "");
															$(".bsuteTitle").val(oData.data.bsuteTitle || "");
															$(".bsuteCdate").val(oData.data.bsuteCdate || "");
									} else {
			alert(oData.errMsg);
		}
	}});
}

//检查提交
function checkModify(){
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
	var bsuteUuid = getQueryString("id");
	    var bsuteUser = $(".bsuteUser").val();
		    var bsuteMonth = $(".bsuteMonth").val();
		    var bsuteTitle = $(".bsuteTitle").val();
		    var bsuteCdate = $(".bsuteCdate").val();
		var str = 'bsuteUuid='+encodeURIComponent(bsuteUuid)
			+'&bsuteUser='+encodeURIComponent(bsuteUser)
				+'&bsuteMonth='+encodeURIComponent(bsuteMonth)
				+'&bsuteTitle='+encodeURIComponent(bsuteTitle)
		    	+'&bsuteCdate='+encodeURIComponent(bsuteCdate);
		getOData(str,"busiUserTitle/update/busiUserTitle",{
		fn:function(oData){
			window.parent.refreshList();
			alert("修改成功！");
		},
		fnerr:function(oData){
			parent.layer.close(msgObject);
		}
	});
}