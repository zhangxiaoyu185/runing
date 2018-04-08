<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>"> 
<title>多个上传</title>
 
<script type="text/javascript" src="<%=basePath%>views/public/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>views/public/js/jquery-form.js"></script>
<script type="text/javascript" src="../../common.js"></script>
<script type="text/javascript">
    i = 1;  
    j = 1;  
    $(document).ready(function(){          
        $("#btn_add").click(function(){  
            document.getElementById("newUpload").innerHTML+='<div id="div_'+j+'"><input  name="file_'+j+'" type="file"  /><input type="button" value="删除"  onclick="del_2('+j+')"/></div>';  
              j = j + 1;  
        });  
    });  
      
    function del_2(o){  
        document.getElementById("newUpload").removeChild(document.getElementById("div_"+o));  
    } 
    function sumbitPic() {
    	alert("1");
	    $("#userForm").ajaxSubmit({
	        //定义返回JSON数据，还包括xml和script格式
	        type: "POST",
	        dataType:'json',
	        url:urlfile + "coreAttachment/upload",
	        success: function(data) {
	            //提交成功后调用
	            alert(data.errMsg);
	        }
	    });
	}
</script>  
</head>  
<body>    
     <h1>springMVC包装类上传文件</h1>   
    <form id="userForm" name="userForm" action="coreAttachment/upload" enctype="multipart/form-data" method="post">
        <div id="newUpload">  
            <input type="file" name="file" />  
        </div>
        <input type="text" name="type" value="3" />    
        <input type="button" id="btn_add" value="增加一行" >  
        <a onclick="sumbitPic()">上传图片</a>
        <input type="submit" value="上传" >            
    </form>   
</body>  
</html>