<?php

// Scipt for the login page
require_once('ConnectionConfiguration.php');

    
// Collect the 
// POST methods of Username and password 
$username = $_POST['Username'];
$password = $_POST['Password'];

// initliase empty array 
$userLogin = array();

// ensure the password is hashed for security reasons 
$hashPassword = sha1($password);

// Execute an mySQL query to validate if the username and 
// password matches from one of the following  
// tables Job seeker or Employer 
// SQL query to see if the username and hashed 
 // password matches from the Job seeker table  
$verifyJobSeeker = "SELECT * FROM JobSeeker WHERE username 
         ='$username' AND password='$hashPassword'";

// execute the SQL query for Job seeker 
$mysqliQueryJobSeeker = mysql_query($verifyJobSeeker);
// SQL query to see if the username and hashed password 
// matches from the Employer table  
$verifyEmployer = "SELECT * FROM Employer WHERE username 
            ='$username' AND password='$hashPassword'";
// execute the SQL query for Employer  
$mysqliQueryEmployer = mysql_query($verifyEmployer);


// Check connection if it the database in the igor Sever is accessable 
if (!$connection) {
	$userLogin["Access"] = "0";
    $userLogin["Message"] = "Database Fault! please try again";
    die(json_encode($userLogin));
}


 // if any number of row dose exist for job seeker 
if(mysql_num_rows($mysqliQueryJobSeeker)>0)
{
// if so, output JSON data to success 
$userLogin["Account_type"]="JobSeeker";
$userLogin["Message"] = "Welcome to GoGoJobs";
$userLogin["Access"] = "1";
echo "{ LoginAcess: [",  json_encode($userLogin), " ]}";
} 
 // if not then check if any 
//  number row dose exist for Employer 
elseif(mysql_num_rows($mysqliQueryEmployer)>0)
{
// if so, output JSON data to success 
$userLogin["Account_type"]="Employer";
$userLogin["Message"] = "Welcome to GoGoJobs";
$userLogin["Access"] = "1";
echo "{ LoginAcess: [",  json_encode($userLogin), " ]}";
} 
// if neither of them, this means the user login credentials 
// does not exists in the back end database 
else
{
// output JSON login credentials which are in correct 
$userLogin["Account_type"]="null";
$userLogin["Access"] = "0";
$userLogin["Message"] = "Incorrect Password or Username";
echo "{ LoginAcess: [",  json_encode($userLogin), " ]}";
}
//close MySQL 
mysql_close($connection);
?>







