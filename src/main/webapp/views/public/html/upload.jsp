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

<title>上传文件</title>
</head>
<body>
<form name="upload" action="coreAttachment/upload2" enctype="multipart/form-data" method="post">
  <input type="file" name="thefile" /> 
  <input type="submit" value="上传文件" multiple="multiple" />
 </form>

</body>
</html>