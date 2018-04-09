$(function () {
	initDetail();
});
//初始化
function initDetail(){
	getInfo(getQueryString("id"));
}
//获取详情
function getInfo(id){
	var str = 'bsmspUuid='+encodeURIComponent(id);
	getOData(str,"busiMonthStep/views",{fn:function(oData){
		if(oData.code == 1) {
						$(".bsmspUser").text(oData.data.bsmspUser || "");
					        	$(".bsmspCdate").text(getFormatDate(oData.data.bsmspCdate) || "");
								$(".bsmspMonth").text(oData.data.bsmspMonth || "");
								$(".bsmspStep").text(oData.data.bsmspStep || "");
					} else {
			alert(oData.errMsg);
		}
	}});
}
