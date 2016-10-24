<?php

require_once('ConnectionConfiguration.php');


$vacancy = array();

$jobseekerId = $_POST['jobseeker_id'];

$vacancy_id = $_POST['vacancy_id'];


$vacancyDetail = "SELECT * FROM ApplicationForm where job_seeker_id ='$jobseekerId' AND vacancy_id ='$vacancy_id'" ;

$result = mysql_query($vacancyDetail);


if(mysql_num_rows($result))
{

while($row=mysql_fetch_assoc($result)){


$vacancy['application_form'][]=$row;

} // end while

echo json_encode($vacancy);

} // end if statement 


mysql_close($connection);
?>







