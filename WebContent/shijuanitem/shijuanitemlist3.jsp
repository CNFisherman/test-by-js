<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html> 
<head>  
<meta Content-type='text/html' charset="utf-8"> 

</head>

<body> 



 
<div align="center">
<span style="color: red;">
卷名：${bean.juanming }
&nbsp;&nbsp;&nbsp;&nbsp;
总分：${bean.zongfen }
&nbsp;&nbsp;&nbsp;&nbsp;
考试时间：${bean.kaoshishijian }
&nbsp;&nbsp;&nbsp;&nbsp;
科目：${bean.kemu.name }
&nbsp;&nbsp;&nbsp;&nbsp;
出卷人：${bean.user.truename }
&nbsp;&nbsp;&nbsp;&nbsp;
难度：${bean.nandu }
&nbsp;&nbsp;&nbsp;&nbsp;
</span>

<br/>

选择题${fenzhi1 }分
<table>
  <tr>
   <th width="150">序号</th>
    <th width="150">问题</th>
    <th width="150">分值</th>
    <th width="150">A选项</th>
    <th width="150">B选项</th>
    <th width="150">C选项</th>
    <th width="150">D选项</th>
    <th width="150">备注</th>
    
  </tr>
  <c:forEach items="${list1}" var="bean" varStatus="v">
  <tr>
  <td>第${v.index+1 }题</td>
    <td>${bean.shiti.wenti }</td>
     <td>${bean.fenzhi }</td>
    <td>${bean.shiti.a }</td>
    <td>${bean.shiti.b }</td>
    <td>${bean.shiti.c }</td>
    <td>${bean.shiti.d }</td>
    <td>${bean.shiti.shizhidian }</td>

   
  </tr>
  </c:forEach>
  
</table>

<br/>
<br/>
<br/>
判断题${fenzhi2 }分
<table>
  <tr>
   <th width="150">序号</th>
    <th width="150">问题</th>
     <th width="150">分值</th>
     <th width="150">备注</th>
 </tr>
  <c:forEach items="${list2}" var="bean" varStatus="v">
  <tr>
   <td>第${v.index+1 }题</td>
    <td>${bean.shiti.wenti }</td>
 <td>${bean.fenzhi }</td>
 <td>${bean.shiti.shizhidian }</td>


    
  </tr>
  </c:forEach>
  
</table>
<br/>
<br/>
<br/>
填空题${fenzhi3 }分
<table>
  <tr>
   <th width="150">序号</th>
    <th width="150">问题</th>
 <th width="150">分值</th>
 <th width="150">备注</th>

   
  </tr>
  <c:forEach items="${list3}" var="bean" varStatus="v">
  <tr>
   <td>第${v.index+1 }题</td>
    <td>${bean.shiti.wenti }</td>
 <td>${bean.fenzhi }</td>
  <td>${bean.shiti.shizhidian }</td>


   
  </tr>
  </c:forEach>
  
</table>



<br/>
<br/>
<br/>
设计题${fenzhi4 }分
<table>
  <tr>
   <th width="150">序号</th>
    <th width="150">问题</th>
     <th width="150">分值</th>
     <th width="150">备注</th>


   
  </tr>
  <c:forEach items="${list4}" var="bean" varStatus="v">
  <tr>
   <td>第${v.index+1 }题</td>
    <td>${bean.shiti.wenti }</td>
      <td>${bean.fenzhi }</td>
      <td>${bean.shiti.shizhidian }</td>



   
  </tr>
  </c:forEach>
  
</table>

<br/>
<br/>
<br/>
计算题${fenzhi5 }分
<table>
  <tr>
   <th width="150">序号</th>
    <th width="150">问题</th>
     <th width="150">分值</th>
     <th width="150">备注</th>


   
  </tr>
  <c:forEach items="${list5}" var="bean" varStatus="v">
  <tr>
   <td>第${v.index+1 }题</td>
    <td>${bean.shiti.wenti }</td>
      <td>${bean.fenzhi }</td>
      <td>${bean.shiti.shizhidian }</td>



   
  </tr>
  </c:forEach>
  
</table>

<table>
  <tr>
    <th width="150" colspan="2">
   <input  type="button"  value="关闭窗口"  onclick="aa()" > 
    
    </th>

  </tr>
 
  
</table>
</div>

 
</form>
 
</BODY> 
</html>
<script type="text/javascript">
function aa(){

window.close();
}
</script>


