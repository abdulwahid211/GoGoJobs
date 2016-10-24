<?php

require_once('ConnectionConfiguration.php');

//collect the post request of the vacancy id 
$vacancy_id =$_POST['vacancy_id'];
//intialise empty array 
$job_seekers_details = array();

// Retrieve all job seekers which the vacancies they have applied  
$job_seekers = "SELECT * FROM JobSeeker WHERE EXISTS 
     (SELECT * FROM AppliedJobs WHERE JobSeeker.id = AppliedJobs.job_seeker_id AND vacancy_id ='$vacancy_id')";
     
// execute mySQL query to retrieve job seekers  
$result = mysql_query($job_seekers);
// if the result does exist 
if(mysql_num_rows($result))
{
// retrieve each row 
while($row=mysql_fetch_assoc($result)){
// assign each row 
$job_seekers_details['JobSeekerDetail'][]=$row;
// when all rows has been retrieved 
// output all rows in JSON format  
}
// when all rows has been retrieved 
// output all rows in JSON format
echo json_encode($job_seekers_details);
} 
// close mySQL connection 
mysql_close($connection);
?>







