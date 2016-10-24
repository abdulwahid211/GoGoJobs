<?php

require_once('ConnectionConfiguration.php');
//collect all relevant post requests from the job seeker 
$job_seekerId = $_POST['job_seekerId'];
$vacancy_id = $_POST['vacancy_id'];
$eligableUK = $_POST['eligableUK'];
$noticePeriod = $_POST['noticePeriod'];
$preExperience = $_POST['preExperience'];
$userAnser = $_POST['userAnser'];
$date_applied = $_POST['dateApplied'];

//insert new values from the job seeker into the ApplicationForm table 
$newApplicationForm = "INSERT INTO ApplicationForm (job_seeker_id, vacancy_id, eligableUK, noticePeriod, preExperience, userAnser) VALUES('$job_seekerId', '$vacancy_id', '$eligableUK',
'$noticePeriod','$preExperience','$userAnser')";
//execute the SQL query to be inserted 
$mysqliQuery2 = mysql_query($newApplicationForm);
//insert new values from the job seeker into the AppliedJobs table 
$result = "INSERT INTO AppliedJobs(job_seeker_id, vacancy_id, date_applied) VALUES('$job_seekerId', '$vacancy_id', '$date_applied')";
//execute the SQL query to be inserted 
$mysqliQuery2 = mysql_query($newApplicationForm);

?>







