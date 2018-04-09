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
								$(".bswspStep").text(oData.data.bswspStep || "");
					} else {
			alert(oData.errMsg);
		}
	}});
}
