<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="util.Util"%>
<%
Util.init(request);
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
if(session.getAttribute("user")==null){

response.sendRedirect("login.jsp");
	return;
}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Frameset//EN">
<HTML>
<HEAD>
<TITLE>题库管理系统</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<META content="MSHTML 6.00.2900.5848" name=GENERATOR>
</HEAD><FRAMESET id=index 
border=0 frameSpacing=0 rows=120,* frameBorder=no><FRAME id=topFrame 
name=topFrame src="top.jsp" noResize scrolling=no>
<FRAMESET border=0 frameSpacing=0 frameBorder=no cols=20%,*>
<FRAME id=leftFrame name=leftFrame src="left.jsp" noResize scrolling=no>
<FRAME 
id=mainFrame name=mainFrame src="main.jsp" noResize 
scrolling=yes></FRAMESET></FRAMESET><noframes></noframes></HTML>

