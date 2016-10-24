<?php

require_once('ConnectionConfiguration.php');
//intialise empty array 
$vacancy = array();
//collect the post request of the job seeker id 
$jobSeeker_id = $_POST['jobSeeker_id'];

// 
// retrieve all vacancies that job seeker has not applied 
$vacancyDetail = "SELECT * FROM Vacancies WHERE NOT EXISTS 
   (SELECT * FROM AppliedJobs WHERE Vacancies.id = vacancy_id AND job_seeker_id ='$jobSeeker_id')";
// execute mySQL query to retrieve vacancy 
$result = mysql_query($vacancyDetail);
// if the result does exist 
if(mysql_num_rows($result))
{
// retrieve each row 
while($row=mysql_fetch_assoc($result)){
// assign each row 
$vacancy['vacancyDetail'][]=$row;
} // end while
// when all rows has been retrieved 
// output all rows in JSON format  
echo json_encode($vacancy);
} 

// close mySQL connection 
mysql_close($connection);
?>







