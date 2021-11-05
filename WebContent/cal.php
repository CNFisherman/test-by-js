<?php
	$dbhost = 'localhost';  // mysql服务器主机地址
  $dbuser = 'renyouye';            // mysql用户名
  $dbpass = '123456';          // mysql用户名密码
  $conn = mysqli_connect($dbhost, $dbuser, $dbpass);
if(! $conn )
{
  die('连接失败: ');
}
mysqli_query($conn , "set names utf8");
 
   $cal_info=$_POST['info'];
   $cal_answ=$_POST['answ'];
   $cal_pic=$_POST['pic'];
   $cal_score=$_POST['score'];
   $cal_level=$_POST['level'];
   $cal_chapter=$_POST['chapter'];
 
   $sql = "insert into cal(cal_info,cal_answ,cal_pic,cal_score,cal_level,cal_chapter)values('$cal_info','$cal_answ','$cal_pic','$cal_score','$cal_level','$cal_chapter')";
 
mysqli_select_db( $conn, 'dl' );
$retval = mysqli_query( $conn, $sql );
if(! $retval )
{
  die('无法插入数据: ' . mysqli_error($conn));
}
echo "数据插入成功\n";
mysqli_close($conn);
?>