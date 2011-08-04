<?php
mysql_connect("localhost","username","password");

mysql_select_db("tablename");
 
$queryresult=mysql_query("SELECT DISTINCT ailmentName FROM 
	ailment_user_remedy where moderatorApproval = 'Y' ");

while($each=mysql_fetch_assoc($queryresult))
        $output[]=$each;

print(json_encode($output));

mysql_close();
?>




