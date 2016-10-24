<?php

require_once('ConnectionConfiguration.php');


$employer_Id = $_POST['employer_Id'];
//$employer_Id = "8";
$vacancy = array();

$vacancyDetail = "SELECT * FROM Vacancies WHERE employer_id='$employer_Id'";

$result = mysql_query($vacancyDetail);


if(mysql_num_rows($result))
{

while($row=mysql_fetch_assoc($result)){


$vacancy['vacancyDetail'][]=$row;

} // end while

echo json_encode($vacancy);

} // end if statement 


mysql_close($connection);
?>







