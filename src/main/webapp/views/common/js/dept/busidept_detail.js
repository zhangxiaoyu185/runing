$(function () {
	initDetail();
});
//初始化
function initDetail(){
	getInfo(getQueryString("id"));
}
//获取详情
function getInfo(id){
	var str = 'bsdetUuid='+encodeURIComponent(id);
	getOData(str,"busiDept/views",{fn:function(oData){
		if(oData.code == 1) {
						$(".bsdetName").text(oData.data.bsdetName || "");
					        	$(".bsdetCdate").text(getFormatDate(oData.data.bsdetCdate) || "");
					        	$(".bsdetUdate").text(getFormatDate(oData.data.bsdetUdate) || "");
					} else {
			alert(oData.errMsg);
		}
	}});
}
