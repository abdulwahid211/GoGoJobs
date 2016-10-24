<?php

require_once('ConnectionConfiguration.php');
    
//collect post request of the job seekers username 
$username = $_POST['Username'];

//initialise empty array 
$EmployerLogin = array();
//sql query to retrieve all deatils of the employer
$EmployerDetail = "SELECT * FROM Employer WHERE username ='$username'";
//execute the SQL query from the database 
$result = mysql_query($EmployerDetail);
 // if any number of row do exist for Employer
if(mysql_num_rows($result))
{

while($row=mysql_fetch_assoc($result)){
//collect all relevant attributes 
$EmployerLogin["id"]=$row["id"];
$EmployerLogin["username"]=$row["username"];
$EmployerLogin["companyName"]=$row["companyName"];
$EmployerLogin["firstName"]=$row["firstName"];
$EmployerLogin["secondName"]=$row["secondName"];
$EmployerLogin["telephone"]=$row["telephone"];
$EmployerLogin["mobile"]=$row["mobile"];
$EmployerLogin["email"]=$row["email"];
$EmployerLogin["firstLineAddress"]=$row["firstLineAddress"];
$EmployerLogin["lastLineAddress"]=$row["lastLineAddress"];
$EmployerLogin["town"]=$row["town"];
$EmployerLogin["postCode"]=$row["postCode"];
}
// if so, output JSON data of the employer 
echo "{ EmployerDetail: [",  json_encode($EmployerLogin), " ]}";
} // end if statement 
//close MySQL 
mysql_close($connection);
?>







