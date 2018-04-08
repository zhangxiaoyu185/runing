//系统名
var headerTitle = "油卡服务管理平台";
//上传图片等待时间3分钟
var waitImgTime = 180000;
//弹框宽度
var widthLayer = '800px';
//弹框高度
var heightLayer = '550px';

var getOData = function(str,url,obj,notAsync,sendType){
	var asyncBoolean = true;
	if(notAsync==true){
		asyncBoolean = false;
	}
	if(!sendType){
		sendType = "post";
	}
	$.ajax({
		data:str,
		type : sendType,
		async : asyncBoolean,
	    url : urlfile+url,
	    dataType :'html',
	    processData:false,
	    //contentType: "application/x-www-form-urlencoded;",
		error:function(XMLHttpRequest, textStatus, errorThrown,fnErr) {
            var errorno = XMLHttpRequest.readyState;
            var oMessage = {
                "timeout": errorno+"请求超时",
                "error": errorno+"请求超时",
                "notmodified": errorno+"请求超时",
                "parsererror": errorno+"数据格式出错"
            };
            if(fnErr){
                fnErr();
                return;
            }
            if(!textStatus && errorThrown){
                alert(errorThrown);
            }
            if(textStatus){
                switch (textStatus) {
                    case "timeout":
                        alert(oMessage.timeout);
                        break;
                    case "parsererror":
                        alert(oMessage.parsererror);
                        break;
                    default:
                        break;
                }
            }
        },
		success:function(data){
			var oData = $.parseJSON(data);
			if(oData.code > 0) {
				obj.fn && obj.fn(oData);
               
			} else {
				alert("错误:"+oData.errMsg);
				obj.fnerr && obj.fnerr(oData);
			}
		}		
	}); 
};

Date.prototype.Format = function(fmt){ //日期规范化处理   
	var o = {   
		"M+" : this.getMonth()+1,                 //月份   
		"d+" : this.getDate(),                    //日   
		"h+" : this.getHours(),                   //小时   
		"m+" : this.getMinutes(),                 //分   
		"s+" : this.getSeconds(),                 //秒   
		"q+" : Math.floor((this.getMonth()+3)/3), //季度   
		"S"  : this.getMilliseconds()             //毫秒   
	};   
	if(/(y+)/.test(fmt))   
		fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
	for(var k in o)   
		if(new RegExp("("+ k +")").test(fmt))   
	  		fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
	return fmt;   
};
function getFormatDate(time){
	if(!time) {
		return ' ';
	}
	var newDate = new Date();
    newDate.setTime(time);
    return newDate.Format('yyyy-MM-dd hh:mm:ss');
} 
//字符串日期格式yyyy-MM-dd
var getDateFormat = function (str){
	var date = str.substr(0,4)+"-"+str.substr(4,2)+"-"+str.substr(6,2);
	return date;
};
//字符串时间格式 hh:mm:ss
var getTimeFormat = function (str){
	var diff = 6-str.length;
	var time = str;
	for(var j=0;j<diff;j++){
		time = "0"+time;
	}
	time =  time.substr(0,2)+":"+time.substr(2,2)+":"+time.substr(4,2);
	return time;
};
//字符串日期时间格式 yyyy-MM-dd hh:mm:ss
var getDateTimeFormat = function (str){
	var datetime =  str.substr(0,4)+"-"+str.substr(4,2)+"-"+str.substr(6,2)+' '+str.substr(8,2)+":"+str.substr(10,2)+":"+str.substr(12,2);
	return datetime;
};
//验证手机号码
var IsMobile = function (text){
    var _emp = /^\s*|\s*$/g;
    text = text.replace(_emp, "");
    var _d = /^1[3578][01379]\d{8}$/g;
    var _l = /^1[34578][01256]\d{8}$/g;
    var _y = /^(134[012345678]\d{7}|1[34578][012356789]\d{8})$/g;
    if (_d.test(text) || _l.test(text) || _y.test(text)) {
        return 1;
    }
    return 0;
};
//限制输入11位数字
var chekNum = function (obj){
    obj.val(obj.val().replace(/[^\d.]/g,""));
    obj.val(obj.val().replace(/^\./g,""));
    obj.val(obj.val().replace(/\.{2,}/g,"."));
    obj.val(obj.val().replace(".","$#$").replace(/\./g,"").replace("$#$","."));
    if(obj.val().length > 11){
    	obj.val(obj.val().substring(0,11));      
    }
};
/*限制输入字数
 * @minwidth :最小字数;
 * @maxwidth :最大字数;
 * @thistext :所选的输入框;
*/
function limitInput(minwidth,maxwidth,thistext){
	if(thistext.val().length>maxwidth){
		thistext.val(thistext.val().substring(0,maxwidth));
		alert("不能超过"+maxwidth+"个！");
	}
	if(thistext.val().length<minwidth){
		alert("不能少于"+minwidth+"个！");
		thistext.focus();
		return 0;
	}
}
/*限制输入字数
 * @maxwidth :最大字数;
 * @thistext :所选的输入框;
*/
function limitMaxInput(maxwidth,thistext){
	if(thistext.val().length>maxwidth){
		thistext.val(thistext.val().substring(0,maxwidth));
		alert("不能超过"+maxwidth+"个！");
	}
}
//获取url?后参数的值
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return decodeURI(r[2]); return null; 
}

//获取图片大小
function getImageWidthHeight(url,callback){
	var img = new Image();
	img.src = url;
	// 如果图片被缓存，则直接返回缓存数据
	if(img.complete){
	    callback(img.width, img.height);
	}else{
            // 完全加载完毕的事件
	    img.onload = function(){
	    	callback(img.width, img.height);
	    };
    }
}
//是否显示查询条件框
function showSearchBox(bool){
	if(bool){
		$(".searchBox").css("display","block");
	}else{
		$(".searchBox").css("display","none");
	}
}
//设置表头
function setTableHead(aData){
	var strhtml_head = '';
	$.each(aData, function(i, item) {
		strhtml_head += '<td width="'+item.percent+'%">'+item.name+'</td>';
	});	
	$(".tb-head").find("tr").html(strhtml_head);
}
//设置表尾
function setTableFoot(data,length){
	var strhtml_foot = '<td colspan="'+length+'">'
		+'<span>共有 </span>'+data.totalCount+'<span> 条记录，&nbsp;&nbsp;</span>'
		+'<span>当前第 </span><span class="curpage">'+data.pageNumber+'</span>/<span class="maxpage">'+data.lastPageNumber+'</span><span> 页&nbsp;</span>'
		+'<span class="float-right align-middle">'
		+'<input type="button" class="firstpage" data-pagenum="'+1+'" value="首页"/>&nbsp;&nbsp;'
		+'<input type="button" class="backpage" data-pagenum="'+data.previousPageNumber+'" value="上一页"/>&nbsp;&nbsp;'
		+'<input type="button" class="nextpage" data-pagenum="'+data.nextPageNumber+'" value="下一页"/>&nbsp;&nbsp;'
		+'<input type="button" class="lastpage" data-pagenum="'+data.lastPageNumber+'" value="末页"/>&nbsp;&nbsp;'
		+'<span> 转至第</span>'
		+'<input type="text" class="jumppagetext" style="width:30px;IME-MODE: disabled;"/>'
		+'<span>页</span>&nbsp;&nbsp;'
		+'<input type="button" class="jumppage" data-pagemax="'+data.lastPageNumber+'" value="跳转"/>'
		+'</span>'
		+'</td>';
	$(".tb-foot").find("tr").html(strhtml_foot);
	$(".jumppagetext").on("keyup",function(){
		this.value=this.value.replace(/\D/g,"");
	}).on("afterpaste",function(){
		this.value=this.value.replace(/\D/g,"");
	});
	if(!data.hasPreviousPage){
		$(".backpage").attr("disabled","disabled");
	}
	if(!data.hasNextPage){
		$(".nextpage").attr("disabled","disabled");
	}
}

//设置表尾
function setTableFoot2(pagenum,length){
	var strhtml_foot = '<td colspan="'+length+'">'
		+'<span>当前第  </span><span class="curpage">'+pagenum+'</span> 页&nbsp;</span>'
		+'<span class="float-right align-middle">'
		+'<input type="button" class="backpage" data-pagenum="'+(pagenum-1)+'" value="上一页"/>&nbsp;&nbsp;'
		+'<input type="button" class="nextpage" data-pagenum="'+(pagenum+1)+'" value="下一页"/>&nbsp;&nbsp;'
		+'</td>';
	$(".tb-foot").find("tr").html(strhtml_foot);
	if(pagenum == 1){
		$(".backpage").attr("disabled","disabled");
	}
	$(".nextpage").attr("disabled","disabled");
	
}

//给url加时间戳
function timestamp(url){
    //  var getTimestamp=Math.random();
      var getTimestamp=new Date().toLocaleString( );
      if(url.indexOf("timestamp")>-1){
    	  changeURLPar(url,'timestamp',getTimestamp); 
      }else{
    	  if(url.indexOf("?")>-1){
    	       url=url+"&timestamp="+getTimestamp;
    	     }else{
    	       url=url+"?timestamp="+getTimestamp;
    	  }
      }
      $(".rightBox").find("iframe[name='iframe']").attr("src",url);
   }

function changeURLPar(destiny, par, par_value){
	var pattern = par+'=([^&]*)';
	var replaceText = par+'='+par_value;
	if (destiny.match(pattern)){
		var tmp = '/\\'+par+'=[^&]*/';
		tmp = destiny.replace(eval(tmp), replaceText);
		return (tmp);
	}else{
		if (destiny.match('[\?]')){
			return destiny+'&'+ replaceText;
		}else{
			return destiny+'?'+replaceText;
		}
	}
	return destiny+'\n'+par+'\n'+par_value;
}
//预览本地图片，注意！！！---→图片class="preimg" 获取图片文件class="uploadFile"
function preImg() { 
	var files = $('.uploadFile').prop('files');
	var url= window.URL.createObjectURL(files[0]); 
	getImageWidthHeight(url,function(realWidth,realHeight){
		var width = 0;
		var height = 200;
		//如果真实的宽度大于浏览器的宽度就按照200显示
		if(realHeight>=height){
			width = realWidth/realHeight*height;
			$(".preimg").css("width",width).css("height",height);
		}
		else{//如果小于浏览器的宽度按照原尺寸显示
			$(".preimg").css("width",realWidth+'px').css("height",realHeight+'px');
		}
		$(".preimg").attr("src",url);
		var arr = $(".imgurl").val().split("\\");
		$(".preimg").attr("data-filename",arr[arr.length-1]);
	}); 
}

//传入this对象即可[数字和小数点]
function keyPress(ob) {
	if (!ob.value.match(/^[\+\-]?\d*?\.?\d*?$/))
		ob.value = ob.t_value;
	else
		ob.t_value = ob.value;
	if (ob.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))
		ob.o_value = ob.value;
}
function keyUp(ob) {
	if (!ob.value.match(/^[\+\-]?\d*?\.?\d*?$/))
		ob.value = ob.t_value;
	else
		ob.t_value = ob.value;
	if (ob.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))
		ob.o_value = ob.value;
	
    //响应鼠标事件，允许左右方向键移动 
    event = window.event || event;
    if (event.keyCode == 37 | event.keyCode == 39) {
        return;
    }
    //最多两位小数
    $(ob).val($(ob).val().replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));

}
function onBlur(ob) {
	if (!ob.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))
		ob.value = ob.o_value;
	else {
		if (ob.value.match(/^\.\d+$/))
			ob.value = 0 + ob.value;
		if (ob.value.match(/^\.$/))
			ob.value = 0;
		ob.o_value = ob.value;
	}
	var amountInput = $(ob);
    //最后一位是小数点的话，移除
    amountInput.val((amountInput.val().replace(/\.$/g, "")));
	
}