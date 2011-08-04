<?php
mysql_connect("localhost","username","password");

mysql_select_db("tablename");

$ailmentname = $_REQUEST[aN];

$sql = "SELECT * FROM ailment_user_remedy WHERE moderatorApproval = 'Y' and 
	ailmentName = '$ailmentname'";

$queryresult=mysql_query($sql);

while($each=mysql_fetch_assoc($queryresult))
        $output[]=$each;

print(json_encode($output));
mysql_close();
?>





