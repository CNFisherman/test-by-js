<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE></TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<STYLE type=text/css> 
{
	FONT-SIZE: 12px
}
#menuTree A {
	COLOR: #566984; TEXT-DECORATION: none
}
</STYLE>
<SCRIPT src="images/Left.files/TreeNode.js" type=text/javascript></SCRIPT>
<SCRIPT src="images/Left.files/Tree.js" type=text/javascript></SCRIPT>
<META content="MSHTML 6.00.2900.5848" name=GENERATOR>
</HEAD>
<BODY 
style="BACKGROUND-POSITION-Y: -120px; BACKGROUND-IMAGE: url(images/bg.gif); BACKGROUND-REPEAT: repeat-x">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%">
  <TBODY>
    <TR>
      <TD width=10 height=29><IMG src="images/Left.files/bg_left_tl.gif"></TD>
      <TD 
    style="FONT-SIZE: 18px; BACKGROUND-IMAGE: url(images/bg_left_tc.gif); COLOR: white; FONT-FAMILY: system">Main 
        Menu</TD>
      <TD width=10><IMG src="images/Left.files/bg_left_tr.gif"></TD>
    </TR>
    <TR>
      <TD style="BACKGROUND-IMAGE: url(images/bg_left_ls.gif)"></TD>
      <TD id=menuTree 
    style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px; HEIGHT: 100%; BACKGROUND-COLOR: white" 
    vAlign=top></TD>
      <TD style="BACKGROUND-IMAGE: url(images/bg_left_rs.gif)"></TD>
    </TR>
    <TR>
      <TD width=10><IMG src="images/Left.files/bg_left_bl.gif"></TD>
      <TD style="BACKGROUND-IMAGE: url(images/bg_left_bc.gif)"></TD>
      <TD width=10><IMG 
src="images/Left.files/bg_left_br.gif"></TD>
    </TR>
  </TBODY>
</TABLE>
<SCRIPT type=text/javascript>

<c:if test="${user.role==1}">
var tree = null;

var root = new TreeNode('系统菜单');




var fun1 = new TreeNode('常规管理');
var fun2 = new TreeNode('教师用户管理', 'method!userlist.action', 'tree_node.gif', null, 'tree_node.gif', null);
fun1.add(fun2);
var fun3 = new TreeNode('科目管理', 'method!kemulist.action', 'tree_node.gif', null, 'tree_node.gif', null);
fun1.add(fun3);

root.add(fun1);

var fun4 = new TreeNode('题库管理');
var fun5 = new TreeNode('选择题管理', 'method!shitilist.action', 'tree_node.gif', null, 'tree_node.gif', null);
fun4.add(fun5);
var fun6 = new TreeNode('判断题管理', 'method!shitilist10.action', 'tree_node.gif', null, 'tree_node.gif', null);
fun4.add(fun6);
var fun7 = new TreeNode('填空题管理', 'method!shitilist100.action', 'tree_node.gif', null, 'tree_node.gif', null);
fun4.add(fun7);
var fun8 = new TreeNode('设计题管理', 'method!shitilist1000.action', 'tree_node.gif', null, 'tree_node.gif', null);
fun4.add(fun8);
var fun11 = new TreeNode('计算题管理', 'method!shitilist10000.action', 'tree_node.gif', null, 'tree_node.gif', null);
fun4.add(fun11);

root.add(fun4);

var fun9 = new TreeNode('查询管理');
var fun10 = new TreeNode('试题库查询', 'method!shitilist2.action', 'tree_node.gif', null, 'tree_node.gif', null);
var fun22 = new TreeNode('试卷库查询', 'method!shijuanlist6.action', 'tree_node.gif', null, 'tree_node.gif', null);
fun9.add(fun22);
fun9.add(fun10);

root.add(fun9);



tree = new Tree(root);tree.show('menuTree');
</c:if>





<c:if test="${user.role==0}">
var tree = null;

var root = new TreeNode('系统菜单');



var fun4 = new TreeNode('题库管理');
var fun5 = new TreeNode('选择题管理', 'method!shitilist.action', 'tree_node.gif', null, 'tree_node.gif', null);
fun4.add(fun5);
var fun6 = new TreeNode('判断题管理', 'method!shitilist10.action', 'tree_node.gif', null, 'tree_node.gif', null);
fun4.add(fun6);
var fun7 = new TreeNode('填空题管理', 'method!shitilist100.action', 'tree_node.gif', null, 'tree_node.gif', null);
fun4.add(fun7);
var fun8 = new TreeNode('设计题管理', 'method!shitilist1000.action', 'tree_node.gif', null, 'tree_node.gif', null);
fun4.add(fun8);
var fun11 = new TreeNode('计算题管理', 'method!shitilist10000.action', 'tree_node.gif', null, 'tree_node.gif', null);
fun4.add(fun11);


root.add(fun4);

var fun1 = new TreeNode('查询管理');
var fun2 = new TreeNode('试题库查询', 'method!shitilist2.action', 'tree_node.gif', null, 'tree_node.gif', null);
fun1.add(fun2);
var fun22 = new TreeNode('试卷库查询', 'method!shijuanlist6.action', 'tree_node.gif', null, 'tree_node.gif', null);
fun1.add(fun22);

root.add(fun1);

var fun3 = new TreeNode('试卷管理');
var fun4 = new TreeNode('试卷管理', 'method!shijuanlist.action', 'tree_node.gif', null, 'tree_node.gif', null);
fun3.add(fun4);

root.add(fun3);

var fun5 = new TreeNode('组卷管理');
var fun6 = new TreeNode('自动组卷管理', 'method!shijuanlist2.action', 'tree_node.gif', null, 'tree_node.gif', null);
fun5.add(fun6);
var fun7 = new TreeNode('手动组卷管理', 'method!shijuanlist3.action', 'tree_node.gif', null, 'tree_node.gif', null);
fun5.add(fun7);
var fun8 = new TreeNode('完成组卷管理', 'method!shijuanlist4.action', 'tree_node.gif', null, 'tree_node.gif', null);
fun5.add(fun8);

root.add(fun5);


var fun9 = new TreeNode('打印管理');
var fun10 = new TreeNode('试卷打印管理', 'method!shijuanlist5.action', 'tree_node.gif', null, 'tree_node.gif', null);
fun9.add(fun10);

root.add(fun9);








tree = new Tree(root);tree.show('menuTree');
</c:if>


</SCRIPT>
</BODY>
</HTML>



