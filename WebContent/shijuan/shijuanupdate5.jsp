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

<script language="javascript" type="text/javascript">

function checkform()
{
	 var reg1 =  /^\d+$/;
	 
	

	if (document.getElementById('xzt1id').value.match(reg1) == null)
	{
		alert("简单选择题数量必须为正整数");
		return false;
	}
	if (document.getElementById('xzt2id').value.match(reg1) == null)
	{
		alert("中等选择题数量必须为正整数");
		return false;
	}
	if (document.getElementById('xzt3id').value.match(reg1) == null)
	{
		alert("复杂选择题数量必须为正整数");
		return false;
	}
	if (document.getElementById('xzt4id').value.match(reg1) == null)
	{
		alert("选择题每题分值必须为正整数");
		return false;
	}
	
	
	if (document.getElementById('pdt1id').value.match(reg1) == null)
	{
		alert("简单判断题数量必须为正整数");
		return false;
	}
	if (document.getElementById('pdt2id').value.match(reg1) == null)
	{
		alert("中等判断题数量必须为正整数");
		return false;
	}
	if (document.getElementById('pdt3id').value.match(reg1) == null)
	{
		alert("复杂判断题数量必须为正整数");
		return false;
	}
	if (document.getElementById('pdt4id').value.match(reg1) == null)
	{
		alert("判断题每题分值必须为正整数");
		return false;
	}
	
	
	
	if (document.getElementById('tkt1id').value.match(reg1) == null)
	{
		alert("简单填空题数量必须为正整数");
		return false;
	}
	if (document.getElementById('tkt2id').value.match(reg1) == null)
	{
		alert("中等填空题数量必须为正整数");
		return false;
	}
	if (document.getElementById('tkt3id').value.match(reg1) == null)
	{
		alert("复杂填空题数量必须为正整数");
		return false;
	}
	if (document.getElementById('tkt4id').value.match(reg1) == null)
	{
		alert("填空题每题分值必须为正整数");
		return false;
	}
	
	if (document.getElementById('wdt1id').value.match(reg1) == null)
	{
		alert("简单设计题数量必须为正整数");
		return false;
	}
	if (document.getElementById('wdt2id').value.match(reg1) == null)
	{
		alert("中等设计题数量必须为正整数");
		return false;
	}
	if (document.getElementById('wdt3id').value.match(reg1) == null)
	{
		alert("复杂设计题数量必须为正整数");
		return false;
	}
	if (document.getElementById('wdt4id').value.match(reg1) == null)
	{
		alert("设计题每题分值必须为正整数");
		return false;
	}
	
	if (document.getElementById('jst1id').value.match(reg1) == null)
	{
		alert("简单计算题数量必须为正整数");
		return false;
	}
	if (document.getElementById('jst2id').value.match(reg1) == null)
	{
		alert("中等计算题数量必须为正整数");
		return false;
	}
	if (document.getElementById('jst3id').value.match(reg1) == null)
	{
		alert("复杂计算题数量必须为正整数");
		return false;
	}
	if (document.getElementById('jst4id').value.match(reg1) == null)
	{
		alert("计算题每题分值必须为正整数");
		return false;
	}
	



	

	return true;
	
}


</script>

</HEAD>
<BODY style="BACKGROUND-POSITION-Y: -120px; BACKGROUND-IMAGE: url(images/bg.gif); BACKGROUND-REPEAT: repeat-x">





<DIV>
  <TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
    
    <TBODY>
      <TR 
  style="BACKGROUND-IMAGE: url(images/bg_header.gif); BACKGROUND-REPEAT: repeat-x" 
  height=47>
  
        <TD width=10>
        <SPAN style="FLOAT: left; BACKGROUND-IMAGE: url(images/main_hl.gif); WIDTH: 15px; BACKGROUND-REPEAT: no-repeat; HEIGHT: 47px"></SPAN></TD>
        <TD>
        <SPAN style="FLOAT: left; BACKGROUND-IMAGE: url(images/main_hl2.gif); WIDTH: 15px; BACKGROUND-REPEAT: no-repeat; HEIGHT: 47px"></SPAN>
        <SPAN style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; FLOAT: left; BACKGROUND-IMAGE: url(images/main_hb.gif); PADDING-BOTTOM: 10px; COLOR: white; PADDING-TOP: 10px; BACKGROUND-REPEAT: repeat-x; HEIGHT: 47px; TEXT-ALIGN: center; 0px: ">
        ${title } </SPAN>
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
            
            
            <form action="${url }?id=${bean.id }" method="post" onsubmit="return checkform()">
            <TABLE class=gridView id=ctl00_ContentPlaceHolder2_GridView1 
      style="WIDTH: 100%; BORDER-COLLAPSE: collapse" cellSpacing=0 rules=all 
      border=1>
              <TBODY>
               <TR>
                  <TH class=gridViewHeader style="WIDTH: 50px" scope=col>&nbsp;</TH>
                  <TH class=gridViewHeader scope=col width="200">考试科目</TH>
                  <TH class=gridViewHeader scope=col>
                   ${bean.kemu.name }
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
                  <TH class=gridViewHeader scope=col>选择题选项</TH>
                  <TH class=gridViewHeader scope=col>
                      题目数量:{简单<input  type="text" name="xzt1"  id='xzt1id'  size="8"  />道
                			中等<input  type="text" name="xzt2"  id='xzt2id'  size="8"  />道
                	 		复杂<input  type="text" name="xzt3"  id='xzt3id'  size="8"  />道}
     				     每题分值:<input  type="text" name="xzt4"  id='xzt4id'  size="8"  />
                  </TH>

                </TR>
                
                <TR>
                  <TH class=gridViewHeader style="WIDTH: 50px" scope=col>&nbsp;</TH>
                  <TH class=gridViewHeader scope=col>判断题选项</TH>
                  <TH class=gridViewHeader scope=col>
                      题目数量:{简单<input  type="text" name="pdt1"  id='pdt1id'  size="8"  />道
                			中等<input  type="text" name="pdt2"  id='pdt2id'  size="8"  />道
                	 		复杂<input  type="text" name="pdt3"  id='pdt3id'  size="8"  />道}
     				     每题分值:<input  type="text" name="pdt4"  id='pdt4id'  size="8"  />
                  </TH>

                </TR>
                
                 <TR>
                  <TH class=gridViewHeader style="WIDTH: 50px" scope=col>&nbsp;</TH>
                  <TH class=gridViewHeader scope=col>填空题选项</TH>
                  <TH class=gridViewHeader scope=col>
                      题目数量:{简单<input  type="text" name="tkt1"  id='tkt1id'  size="8"  />道
                			中等<input  type="text" name="tkt2"  id='tkt2id'  size="8"  />道
                	 		复杂<input  type="text" name="tkt3"  id='tkt3id'  size="8"  />道}
     				     每题分值:<input  type="text" name="tkt4"  id='tkt4id'  size="8"  />
                  </TH>

                </TR>
                
                 <TR>
                  <TH class=gridViewHeader style="WIDTH: 50px" scope=col>&nbsp;</TH>
                  <TH class=gridViewHeader scope=col>设计题选项</TH>
                  <TH class=gridViewHeader scope=col>
                      题目数量:{简单<input  type="text" name="wdt1"  id='wdt1id'  size="8"  />道
                			中等<input  type="text" name="wdt2"  id='wdt2id'  size="8"  />道
                	 		复杂<input  type="text" name="wdt3"  id='wdt3id'  size="8"  />道}
     				     每题分值:<input  type="text" name="wdt4"  id='wdt4id'  size="8"  />
                  </TH>

                </TR>
                
                <TR>
                  <TH class=gridViewHeader style="WIDTH: 50px" scope=col>&nbsp;</TH>
                  <TH class=gridViewHeader scope=col>计算题选项</TH>
                  <TH class=gridViewHeader scope=col>
                      题目数量:{简单<input  type="text" name="jst1"  id='jst1id'  size="8"  />道
                			中等<input  type="text" name="jst2"  id='jst2id'  size="8"  />道
                	 		复杂<input  type="text" name="jst3"  id='jst3id'  size="8"  />道}
     				     每题分值:<input  type="text" name="jst4"  id='jst4id'  size="8"  />
                  </TH>

                </TR>
                
                
                
                
                
                <TR>
                  <TH class=gridViewHeader style="WIDTH: 50px" scope=col>&nbsp;</TH>
                  <TH class=gridViewHeader scope=col>操作</TH>
                 <TH class=gridViewHeader scope=col align="center" >
                  <input type="submit" value="开始自动组卷" style="width: 120px" />
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input  onclick="javascript:history.go(-1);" style="width: 60px" type="button" value="返回" />
                  </TH>
          
                 
                </TR>
               
                
             
              </TBODY>
            </TABLE>
            </form>
            
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

</BODY>
</HTML>



