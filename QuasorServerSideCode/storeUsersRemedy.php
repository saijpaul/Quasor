<?php
mysql_connect("localhost","username","password");

mysql_select_db("tablename");

$name = $_REQUEST[ailmentN];
$remedy = $_REQUEST[remedyD];
$submittedBy = $_REQUEST[submittedBy];

$sql = "INSERT INTO `ailment_user_remedy`(`ailmentName`, `remedyDescription`,`submittedBy`) VALUES ('$name','$remedy','$submittedBy')";

$q=mysql_query($sql);

mysql_close();
?>






