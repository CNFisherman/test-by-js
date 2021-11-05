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
.gridView {
	BORDER-RIGHT: #bad6ec 1px; BORDER-TOP: #bad6ec 1px; BORDER-LEFT: #bad6ec 1px; COLOR: #566984; BORDER-BOTTOM: #bad6ec 1px; FONT-FAMILY: Courier New
}
.gridViewHeader {
	BORDER-RIGHT: #bad6ec 1px solid; BORDER-TOP: #bad6ec 1px solid; BACKGROUND-IMAGE: url(images/bg_th.gif); BORDER-LEFT: #bad6ec 1px solid; LINE-HEIGHT: 27px; BORDER-BOTTOM: #bad6ec 1px solid
}
.gridViewItem {
	BORDER-RIGHT: #bad6ec 1px solid; BORDER-TOP: #bad6ec 1px solid; BORDER-LEFT: #bad6ec 1px solid; LINE-HEIGHT: 32px; BORDER-BOTTOM: #bad6ec 1px solid; TEXT-ALIGN: center
}
.cmdField {
	BORDER-RIGHT: 0px; BORDER-TOP: 0px; BACKGROUND-IMAGE: url(images/bg_rectbtn.png); OVERFLOW: hidden; BORDER-LEFT: 0px; WIDTH: 67px; COLOR: #364c6d; LINE-HEIGHT: 27px; BORDER-BOTTOM: 0px; BACKGROUND-REPEAT: repeat-x; HEIGHT: 27px; BACKGROUND-COLOR: transparent; TEXT-DECORATION: none
}
.buttonBlue {
	BORDER-RIGHT: 0px; BORDER-TOP: 0px; BACKGROUND-IMAGE: url(images/bg_button_blue.gif); BORDER-LEFT: 0px; WIDTH: 78px; COLOR: white; BORDER-BOTTOM: 0px; BACKGROUND-REPEAT: no-repeat; HEIGHT: 21px
}
</STYLE>
<META content="MSHTML 6.00.2900.5848" name=GENERATOR>
<SCRIPT src="images/Left.files/TreeNode.js" type=text/javascript></SCRIPT>


<script type="text/javascript" src="js/jquery.min.js"></script>

<script type="text/javascript">
    var req;
   
    function shanxuan(){//当第一个下拉框的选项发生改变时调用该函数
    	var kemuid = encodeURI(encodeURI($("#kemuid").val()));
		var leixingid = encodeURI(encodeURI($("#leixingid").val()));
		var zhishidianid = encodeURI(encodeURI($("#zhishidianid").val()));
		var nanduid = encodeURI(encodeURI($("#nanduid").val()));

      var now = new Date();
      var url = "method!shanxuan.action?kemuid="+kemuid+"&leixingid="+leixingid+"&zhishidianid="+zhishidianid+"&t="+now.getTime()+"&nanduid="+nanduid;
      if(window.XMLHttpRequest){
        req = new XMLHttpRequest();
      }else if(window.ActiveXObject){
        req = new ActiveXObject("Microsoft.XMLHTTP");
      }
      if(req){
        //指定回调函数为callback
      	req.onreadystatechange = callback;
        req.open("GET",url,true);
        req.send(null);
      }
    }
    //回调函数
    function callback(){
      if(req.readyState ==4){
        if(req.status ==200){

        document.getElementById('tablevaule').innerHTML=req.responseText;
 
          //parseMessage();//解析XML文档
        }else{
          alert("不能得到描述信息:" + req.statusText);
        }
      }
    }
       


</SCRIPT>




</HEAD>
<BODY style="BACKGROUND-POSITION-Y: -120px; BACKGROUND-IMAGE: url(images/bg.gif); BACKGROUND-REPEAT: repeat-x">





<DIV>
  <TABLE height="70%" cellSpacing=0 cellPadding=0 width="100%" border=0>
    
    <TBODY>
      <TR 
  style="BACKGROUND-IMAGE: url(images/bg_header.gif); BACKGROUND-REPEAT: repeat-x" 
  height=47>
  
        <TD width=10>
        <SPAN style="FLOAT: left; BACKGROUND-IMAGE: url(images/main_hl.gif); WIDTH: 15px; BACKGROUND-REPEAT: no-repeat; HEIGHT: 47px"></SPAN></TD>
        <TD>
        <SPAN style="FLOAT: left; BACKGROUND-IMAGE: url(images/main_hl2.gif); WIDTH: 15px; BACKGROUND-REPEAT: no-repeat; HEIGHT: 47px"></SPAN>
        <SPAN style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; FLOAT: left; BACKGROUND-IMAGE: url(images/main_hb.gif); PADDING-BOTTOM: 10px; COLOR: white; PADDING-TOP: 10px; BACKGROUND-REPEAT: repeat-x; HEIGHT: 47px; TEXT-ALIGN: center; 0px: ">
       手动组卷
        </SPAN>
        <SPAN 
      style="FLOAT: left; BACKGROUND-IMAGE: url(images/main_hr.gif); WIDTH: 60px; BACKGROUND-REPEAT: no-repeat; HEIGHT: 47px"></SPAN></TD>
        <TD 
    style="BACKGROUND-POSITION: 80% bottom; BACKGROUND-IMAGE: url(images/main_rc.gif)" 
    width=10></TD>
      </TR>
      <TR>
        <TD style="BACKGROUND-IMAGE: url(images/main_ls.gif)">&nbsp;</TD>
        <TD 
    style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; COLOR: #566984; PADDING-TOP: 10px; BACKGROUND-COLOR: white" 
    vAlign=top align=middle>
          <DIV>
            
            

            <TABLE class=gridView id=ctl00_ContentPlaceHolder2_GridView1 
      style="WIDTH: 100%; BORDER-COLLAPSE: collapse" cellSpacing=0 rules=all 
      border=1>
              <TBODY>
                <TR>
                  <TH class=gridViewHeader style="WIDTH: 50px" scope=col>&nbsp;</TH>
                  <TH class=gridViewHeader scope=col width="200">考试科目</TH>
                  <TH class=gridViewHeader scope=col>
                   ${bean.kemu.name }
                   <input type="hidden" id="kemuid" value="${bean.kemu.id }">
                  </TH>

                </TR>
                
                
                
                <TR>
                  <TH class=gridViewHeader style="WIDTH: 50px" scope=col>&nbsp;</TH>
                  <TH class=gridViewHeader scope=col>卷名</TH>
                  <TH class=gridViewHeader scope=col>
                 ${bean.juanming }
                  </TH>

                </TR>
                <TR>
                  <TH class=gridViewHeader style="WIDTH: 50px" scope=col>&nbsp;</TH>
                  <TH class=gridViewHeader scope=col>考试时间</TH>
                  <TH class=gridViewHeader scope=col>
                 ${bean.kaoshishijian }
                  </TH>
                </TR>
                
                <TR>
                  <TH class=gridViewHeader style="WIDTH: 50px" scope=col>&nbsp;</TH>
                  <TH class=gridViewHeader scope=col>试卷难度</TH>
                  <TH class=gridViewHeader scope=col>
             			 ${bean.nandu }
                  </TH>

                </TR>
                
                 <TR>
                  <TH class=gridViewHeader style="WIDTH: 50px" scope=col>&nbsp;</TH>
                  <TH class=gridViewHeader scope=col>题型</TH>
                  <TH class=gridViewHeader scope=col>
             	<select name="leixing"  id="leixingid">
				<option value="">所有选项</option>
				<option value="填空题">填空题</option>
				<option value="设计题">设计题</option>
				<option value="选择题">选择题</option>
				<option value="判断题">判断题</option>
				<option value="计算题">计算题</option>
				</select>
                  </TH>

                </TR>
                
                <TR>
                  <TH class=gridViewHeader style="WIDTH: 50px" scope=col>&nbsp;</TH>
                  <TH class=gridViewHeader scope=col>难度</TH>
                  <TH class=gridViewHeader scope=col>
             	<select name="nandu"  id="nanduid">
				<option value="">所有选项</option>
				<option value="简单">简单</option>
				<option value="中等">中等</option>
				<option value="复杂">复杂</option>

				</select>
                  </TH>

                </TR>
                
                
                <TR>
                  <TH class=gridViewHeader style="WIDTH: 50px" scope=col>&nbsp;</TH>
                  <TH class=gridViewHeader scope=col>知识点</TH>
                  <TH class=gridViewHeader scope=col>
                  <input type="text"  name="zhishidian" id="zhishidianid" size="30">
                  </TH>

                </TR>
                
                <TR>
                  <TH class=gridViewHeader style="WIDTH: 50px" scope=col>&nbsp;</TH>
                  <TH class=gridViewHeader scope=col>操作</TH>
                 <TH class=gridViewHeader scope=col align="center" >
                  <input type="button" value="筛选试题" onclick="shanxuan()"/> 
                  </TH>
          
                 
                </TR>
              </TBODY>
            </TABLE>
   
            
          </DIV>
        </TD>
        <TD style="BACKGROUND-IMAGE: url(images/main_rs.gif)"></TD>
      </TR>
      <TR 
  style="BACKGROUND-IMAGE: url(images/main_fs.gif); BACKGROUND-REPEAT: repeat-x" 
  height=10>
        <TD style="BACKGROUND-IMAGE: url(images/main_lf.gif)"></TD>
        <TD style="BACKGROUND-IMAGE: url(images/main_fs.gif)"></TD>
        <TD 
style="BACKGROUND-IMAGE: url(images/main_rf.gif)"></TD>
      </TR>
    </TBODY>
  </TABLE>
</DIV>

<form method="post" action="method!shijuanitemadd2.action" >
注意：打钩表示选择该题目，选择该题目后请填写分值框，否则会报错
<input type="hidden" name="shijuanid" value="${shijuanid }"/> 
 <div id="tablevaule">
			
				</div>
<input type="submit" value="提&nbsp;交" size="20" />				
				
</form>				

</BODY>
</HTML>



