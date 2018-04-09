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
	var str = 'bsdteUuid='+encodeURIComponent(id);
	getOData(str,"busiDeptTitle/views",{fn:function(oData){
		if(oData.code == 1) {
											$(".bsdteDept").val(oData.data.bsdteDept || "");
															$(".bsdteMonth").val(oData.data.bsdteMonth || "");
															$(".bsdteTitle").val(oData.data.bsdteTitle || "");
															$(".bsdteCdate").val(oData.data.bsdteCdate || "");
									} else {
			alert(oData.errMsg);
		}
	}});
}

//检查提交
function checkModify(){
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
	var bsdteUuid = getQueryString("id");
	    var bsdteDept = $(".bsdteDept").val();
		    var bsdteMonth = $(".bsdteMonth").val();
		    var bsdteTitle = $(".bsdteTitle").val();
		    var bsdteCdate = $(".bsdteCdate").val();
		var str = 'bsdteUuid='+encodeURIComponent(bsdteUuid)
			+'&bsdteDept='+encodeURIComponent(bsdteDept)
				+'&bsdteMonth='+encodeURIComponent(bsdteMonth)
				+'&bsdteTitle='+encodeURIComponent(bsdteTitle)
		    	+'&bsdteCdate='+encodeURIComponent(bsdteCdate);
		getOData(str,"busiDeptTitle/update/busiDeptTitle",{
		fn:function(oData){
			window.parent.refreshList();
			alert("修改成功！");
		},
		fnerr:function(oData){
			parent.layer.close(msgObject);
		}
	});
}