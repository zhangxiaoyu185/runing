$(function () {
	initDetail();
});
//初始化
function initDetail(){
	getInfo(getQueryString("id"));
}
//获取详情
function getInfo(id){
	var str = 'bsuteUuid='+encodeURIComponent(id);
	getOData(str,"busiUserTitle/views",{fn:function(oData){
		if(oData.code == 1) {
						$(".bsuteUser").text(oData.data.bsuteUser || "");
								$(".bsuteMonth").text(oData.data.bsuteMonth || "");
								$(".bsuteTitle").text(oData.data.bsuteTitle || "");
					        	$(".bsuteCdate").text(getFormatDate(oData.data.bsuteCdate) || "");
					} else {
			alert(oData.errMsg);
		}
	}});
}
