<?php

require_once('ConnectionConfiguration.php');

// recieve all Post request from the client application   
$title = $_POST['title'];
$salary = $_POST['salary'];
$contract = $_POST['contract'];
$employer_id = $_POST['employer_id'];
$descriptions = $_POST['descriptions'];
$postCode = $_POST['postCode'];
$posted = $_POST['posted'];
$city = $_POST['city'];
$companyName = $_POST['companyName'];
$responsibility = $_POST['responsibility'];
$employer_username = $_POST['employer_username'];
$type = $_POST['type'];

//SQL query string to insert new vacancy in Vacancies table 
$result = "INSERT INTO Vacancies(employer_id,employer_username, title,descriptions,resposibilities,companyName,salary,contract,postCode,city,posted,type) 
VALUES('$employer_id', '$employer_username','$title','$descriptions','$responsibility','$companyName','$salary','$contract','$postCode','$city','$posted','$type')";
//execute the SQL query 
$mysqliQuery3 = mysql_query($result);


?>







