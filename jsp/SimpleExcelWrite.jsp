<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%@ page import="java.io.*" %>
<% 
    String fname = "学校竞争力情况";
    OutputStream os = response.getOutputStream();//取得输出流
    response.reset();//清空输出流
    
    //下面是对中文文件名的处理
    response.setCharacterEncoding("UTF-8");//设置相应内容的编码格式
    fname = java.net.URLEncoder.encode(fname,"UTF-8");
    response.setHeader("Content-Disposition","attachment;filename="+new String(fname.getBytes("UTF-8"),"GBK")+".xls");
    response.setContentType("application/msexcel");//定义输出类型
    ExcelUtil sw = new ExcelUtil();
    sw.craeteExcel(os);

 %>
<html>
  <head>
    
    <title></title>

  </head>
  
  <body>
  </body>
</html>