<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%@ page import="java.io.*" %>
<% 
    String fname = "ѧУ���������";
    OutputStream os = response.getOutputStream();//ȡ�������
    response.reset();//��������
    
    //�����Ƕ������ļ����Ĵ���
    response.setCharacterEncoding("UTF-8");//������Ӧ���ݵı����ʽ
    fname = java.net.URLEncoder.encode(fname,"UTF-8");
    response.setHeader("Content-Disposition","attachment;filename="+new String(fname.getBytes("UTF-8"),"GBK")+".xls");
    response.setContentType("application/msexcel");//�����������
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