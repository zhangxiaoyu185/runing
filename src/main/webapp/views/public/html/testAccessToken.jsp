<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="../../common.js"></script>
<title>获取accesstoken</title>
<script type="text/javascript">
	function getAccessToken(){
		$.ajax({
			url : 'weixin/getAccessToken',
			data:{
				appid:$('#appid').val(),
				secret:$('#secret').val()
			},
			dataType:'text',
			type : 'GET',
			timeout : 10000,
			error : function() {
				alert('提示','响应超时，请刷新页面！');
			},
			success : function(msg) {
				alert(msg);
				$('#accessToken').val(msg);				
			}
		});
	}
	
	function createMenu(){
		$.ajax({
			url : urlfile + 'weixin/getMenu',
			data:{
				jsonMenu:$('#menu').val(),
				token:$('#token').val()
			},
			dataType:'text',
			type : 'GET',
			timeout : 10000,
			error : function() {
				alert('提示','响应超时，请刷新页面！');
			},
			success : function(msg) {
				alert(msg);
			}
		});
	}
</script>
</head>
<body>
	输入appid：<input id="appid" type="text"/>
	输入appsecret：<input id="secret" type="text"/>
	<input type="button" value="点击获取access_token" onclick="getAccessToken();"/>
	<input id="accessToken" type="text">
	
	<h5>创建菜单</h5>
	菜单json:<input id="menu" type="text"/>
	accessToken:<input id="token" type="text"/>
	<input type="button" value="点击创建菜单" onclick="createMenu();"/>
	例：
	{  
    "button":[  
	    {     
	         "type":"click",  
	         "name":"今日歌曲",  
	         "key":"V1001_TODAY_MUSIC"  
	     }, 
		 {  
		     "type":"view",  
		     "name":"歌手简介",  
		     "url":"http://www.qq.com/"  
		 },  
	     {  
	         "type":"click",  
	         "name":"歌手简介",  
	         "key":"V1001_TODAY_SINGER"  
	     },  
	     {  
	         "name":"菜单",  
	         "sub_button":[  
	          {  
	             "type":"click",  
	             "name":"hello word",  
	             "key":"V1001_HELLO_WORLD"  
	          },  
	          {  
	             "type":"click",  
	             "name":"赞一下我们",  
	             "key":"V1001_GOOD"  
	          }]  
	     }]  
	}
</body>
</html>