$(function () {
	initDetail();
});
//初始化
function initDetail(){
	getInfo(getQueryString("id"));
}
//获取详情
function getInfo(id){
	var str = 'bswspUuid='+encodeURIComponent(id);
	getOData(str,"busiWeekStep/views",{fn:function(oData){
		if(oData.code == 1) {
						$(".bswspUser").text(oData.data.bswspUser || "");
					        	$(".bswspCdate").text(getFormatDate(oData.data.bswspCdate) || "");
								$(".bswspWeek").text(oData.data.bswspWeek || "");
								$(".bswspMonth").text(oData.data.bswspMonth || "");
								$(".bswspStep").text(oData.data.bswspStep || "");
								$(".bswspScore").text(oData.data.bswspScore || "");
								$(".bswspOrd").text(oData.data.bswspOrd || "");
					} else {
			alert(oData.errMsg);
		}
	}});
}
