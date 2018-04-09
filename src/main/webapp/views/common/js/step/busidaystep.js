$(function () {
	$(".cur_postion").html("当前位置： [ "+sessionStorage.getItem("pmenuname")+" ] - [ "+sessionStorage.getItem("cmenuname")+" ]");

	//查询条件
	var strhtml_searchContent = '<div class="inline-block margin">'
		+'<span>所属用户:</span>'
		+'<input type="text" class="inputStyle_condition bsdspUser"/>'
		+'<span>创建时间:</span>'
		+'<input type="text" class="inputStyle_condition bsdspCdate"/>'
		+'<span>所属日期:</span>'
		+'<input type="text" class="inputStyle_condition bsdspDay"/>'
		+'<span>步数:</span>'
		+'<input type="text" class="inputStyle_condition bsdspStep"/>'
		+'</div>';
	$(".searchContent").html(strhtml_searchContent);
	//是否显示查询条件
	showSearchBox(true);

	var obj = {};//查询条件对象
	searchContent(obj);
	showList(obj,1);

	//详情
	$("body").delegate('.detailit','click', function(){
		layer.open({
			type: 2,
			title: '详情',
			scrollbar: false,
			maxmin: true,
			shadeClose: false, //点击遮罩关闭层
			area : [widthLayer , heightLayer],
			content: '../step/busidaystep_detail?id='+$(this).attr("data-id")+'&timestamp='+(new Date()).valueOf()
		});
	});
	//新增
	$('.addit').on('click', function(){
		layer.open({
			type: 2,
			title: '新增',
			scrollbar: false,
			maxmin: true,
			shadeClose: false, //点击遮罩关闭层
			area : [widthLayer , heightLayer],
			content: '../step/busidaystep_add?timestamp='+(new Date()).valueOf()
		});
	});
	//修改
	$("body").delegate('.modifyit','click', function(){
		layer.open({
			type: 2,
			title: '修改',
			scrollbar: false,
			maxmin: true,
			shadeClose: false, //点击遮罩关闭层
			area : [widthLayer , heightLayer],
			content: '../step/busidaystep_modify?id='+$(this).attr("data-id")+'&timestamp='+(new Date()).valueOf()
		});
	});
	//全选
	$("body").delegate("input[name='checkboxAll']","click",function(){
		if($(this).attr("checked")){
			$("input[name='checkbox']").each(function(){
				$(this).attr("checked",true);
			});
		}else{
			$("input[name='checkbox']").each(function(){
				$(this).removeAttr("checked");
			});
		}
	});

	//批量删除
	$("body").delegate(".delthese","click",function(){
		var ids = '';
		$("input[name='checkbox']").each(function(){
			if($(this).attr("checked")){
				ids += $(this).attr("data-id")+"|";
			}
		});
		if(ids == ""){
			alert("未选择删除对象！");
		}else{
			var r=confirm("是否确认删除所选的记录？");
			if (r==true){
				var str = 'bsdspUuids='+encodeURIComponent(ids);
				getOData(str,"busiDayStep/delete/batch",{fn:function(oData){
						var obj = {};//查询条件对象
						searchContent(obj);
						pagenum = parseInt($(".curpage").text());
						isNull(obj,pagenum);
						alert("删除成功！");
					}}
				);
			}
		}
	});

	//删除
	$("body").delegate(".delit","click",function(){
		var r=confirm("是否确认删除？");
		if (r==true){
			var str = 'bsdspUuid='+encodeURIComponent($(this).attr("data-id"));
			getOData(str,"busiDayStep/delete/one",{fn:function(oData){
					var obj = {};//查询条件对象
					searchContent(obj);
					pagenum = parseInt($(".curpage").text());
					isNull(obj,pagenum);
					alert("删除成功！");
				}}
			);
		}
	});
	//tr高亮显示
	$("body").delegate(".trHighLight","mouseleave",function(){
		$(this).find("td").css("background-color","#fff");
	});
	//tr高亮显示并显示图
	$("body").delegate(".trHighLight","mouseenter",function(){
		$(this).find("td").css("background-color","#c1ebff");
		var imgurl = $(this).attr("data-imageurl");
		if(imgurl != "#"){
			var that = this;
			$("<img/>").attr("src", imgurl).load(function() {
				var width = 150;
				var realWidth = this.width;
				var realHeight = this.height;
				var height = realHeight/realWidth*width;
				if(realWidth>=width){
					var bannerimg = '<img id="bannerimg" src="'+imgurl+'" width="'+width+'px" height="'+height+'px" />';
				}
				else{//如果小于浏览器的宽度按照原尺寸显示
					$("#bannerimg").css("width",realWidth+'px').css("height",realHeight+'px');
					var bannerimg = '<img id="bannerimg" src="'+imgurl+'" width="'+realWidth+'px" height="'+realHeight+'px" />';
				}
				layer.tips(bannerimg, that ,{
					tips: 3
				});
			});
		}
	});
	//查询
	$("body").delegate(".searchBtn","click",function(){
		var obj = {};//查询条件对象
		searchContent(obj);
		showList(obj,1);
	});
	//重置
	$("body").delegate(".resetBtn","click",function(){
		var obj = {};//查询条件对象
		$(".bsdspUser").val('');
		$(".bsdspCdate").val('');
		$(".bsdspDay").val('');
		$(".bsdspStep").val('');
		searchContent(obj);
		showList(obj,1);
	});
	//上一页
	$('.manageBox').delegate(".backpage","click", function() {
		var obj = {};//查询条件对象
		searchContent(obj);
		pagenum = parseInt($(this).attr("data-pagenum"));
		showList(obj,pagenum);
	});
	//下一页
	$('.manageBox').delegate(".nextpage","click", function() {
		var obj = {};//查询条件对象
		searchContent(obj);
		var pagenum = parseInt($(this).attr("data-pagenum"));
		showList(obj,pagenum);
	});
	//首页
	$('.manageBox').delegate(".firstpage","click", function() {
		var obj = {};//查询条件对象
		searchContent(obj);
		var pagenum = parseInt($(this).attr("data-pagenum"));
		showList(obj,pagenum);
	});
	//末页
	$('.manageBox').delegate(".lastpage","click", function() {
		var obj = {};//查询条件对象
		searchContent(obj);
		var pagenum = parseInt($(this).attr("data-pagenum"));
		showList(obj,pagenum);
	});
	//跳转至
	$('.manageBox').delegate(".jumppage","click", function() {
		var obj = {};//查询条件对象
		searchContent(obj);
		var pagenum = parseInt($('.jumppagetext').val());
		if(pagenum>0 && pagenum <=parseInt($(this).attr("data-pagemax"))){
			showList(obj,pagenum);
		}else{
			alert("查无此页！");
		}
	});

});
function showList(obj,pagenum){
	var aData =[{name:"<input type='checkbox' name='checkboxAll' value='checkbox' />",percent:"5"},
				{name:"所属用户",percent:"10"},
				{name:"创建时间",percent:"10"},
				{name:"所属日期",percent:"10"},
				{name:"步数",percent:"10"},
				{name:"操作",percent:"10"}];
	setTableHead(aData);
	var str = 'pageNum='+pagenum+'&pageSize=15';
		if(obj.bsdspUser!=""){
		str += '&bsdspUser='+encodeURIComponent(obj.bsdspUser);
	}
		if(obj.bsdspCdate!=""){
		str += '&bsdspCdate='+encodeURIComponent(obj.bsdspCdate);
	}
		if(obj.bsdspDay!=""){
		str += '&bsdspDay='+encodeURIComponent(obj.bsdspDay);
	}
		if(obj.bsdspStep!=""){
		str += '&bsdspStep='+encodeURIComponent(obj.bsdspStep);
	}
		getOData(str,"busiDayStep/find/by/cnd",{fn:function(oData){
		var strhtml_list = "";
		var arrData = oData.data.result;
		var ln = arrData.length;
		for(var i=0;i<ln;i++){
			strhtml_list += '<tr class="trHighLight">'
				+'<td>'+'<input type="checkbox" name="checkbox" value="checkbox" data-id="'+arrData[i].bsdspUuid+'"/>'+'</td>'
	        		+'<td>'+(arrData[i].bsdspUser || "")+'</td>'
	        		+'<td>'+(arrData[i].bsdspCdate || "")+'</td>'
	        		+'<td>'+(arrData[i].bsdspDay || "")+'</td>'
	        		+'<td>'+(arrData[i].bsdspStep || "")+'</td>'
					+'<td>'
				+'<a  class="p-edit detailit" data-id="'+arrData[i].bsdspUuid+'">查看</a>'
				+'<a  class="p-edit modifyit" data-id="'+arrData[i].bsdspUuid+'">修改</a>'
				+'<a  class="p-edit delit" data-id="'+arrData[i].bsdspUuid+'">删除</a>'
				+'</td>'
				+'</tr>';
		}
		$(".tb-body").html(strhtml_list);
		setTableFoot(oData.data,aData.length);
		}}
	);
}
function isNull(obj,pagenum){
	var str = 'pageNum='+pagenum+'&pageSize=15';
	    if(obj.bsdspUser!=""){
    	str += '&bsdspUser='+encodeURIComponent(obj.bsdspUser);
    }
	    if(obj.bsdspCdate!=""){
    	str += '&bsdspCdate='+encodeURIComponent(obj.bsdspCdate);
    }
	    if(obj.bsdspDay!=""){
    	str += '&bsdspDay='+encodeURIComponent(obj.bsdspDay);
    }
	    if(obj.bsdspStep!=""){
    	str += '&bsdspStep='+encodeURIComponent(obj.bsdspStep);
    }
		getOData(str,"busiDayStep/find/by/cnd",{fn:function(oData){
		var arrData = oData.data.result;
		var ln = arrData.length;
		if(ln == 0){
			if (oData.data.totalCount != 0 && pagenum !=1){
				showList(obj,pagenum-1);
			}else{
				showList(obj,1);
			}
		}else{
			showList(obj,pagenum);
		}
	}});
}

function refreshList(){
	var obj = {};//查询条件对象
	searchContent(obj);
	showList(obj,parseInt($(".curpage").text()));
	layer.closeAll();
}

function searchContent(obj){
	obj.bsdspUser = $(".bsdspUser").val();
	obj.bsdspCdate = $(".bsdspCdate").val();
	obj.bsdspDay = $(".bsdspDay").val();
	obj.bsdspStep = $(".bsdspStep").val();
}
