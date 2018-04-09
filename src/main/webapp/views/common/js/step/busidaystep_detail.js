$(function () {
	initDetail();
});
//初始化
function initDetail(){
	getInfo(getQueryString("id"));
}
//获取详情
function getInfo(id){
	var str = 'bsdspUuid='+encodeURIComponent(id);
	getOData(str,"busiDayStep/views",{fn:function(oData){
		if(oData.code == 1) {
						$(".bsdspUser").text(oData.data.bsdspUser || "");
					        	$(".bsdspCdate").text(getFormatDate(oData.data.bsdspCdate) || "");
								$(".bsdspDay").text(oData.data.bsdspDay || "");
								$(".bsdspStep").text(oData.data.bsdspStep || "");
					} else {
			alert(oData.errMsg);
		}
	}});
}
