$(function () {
	initDetail();
});
//初始化
function initDetail(){
	getInfo(getQueryString("id"));
}
//获取详情
function getInfo(id){
	var str = 'bsdteUuid='+encodeURIComponent(id);
	getOData(str,"busiDeptTitle/views",{fn:function(oData){
		if(oData.code == 1) {
						$(".bsdteDept").text(oData.data.bsdteDept || "");
								$(".bsdteMonth").text(oData.data.bsdteMonth || "");
								$(".bsdteTitle").text(oData.data.bsdteTitle || "");
					        	$(".bsdteCdate").text(getFormatDate(oData.data.bsdteCdate) || "");
					} else {
			alert(oData.errMsg);
		}
	}});
}
