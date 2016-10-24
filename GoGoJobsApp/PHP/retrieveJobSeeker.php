<?php

require_once('ConnectionConfiguration.php');

    

//Creating variables which will store the values of the form in the android application.
$username = $_POST['Username'];
$password = $_POST['Password'];

$EmployerLogin = array();

//password is hashed http://www.thedevfiles.com/2014/08/secure-password-hashing/   
	// http://php.net/manual/en/function.password-hash.php
	$hashPassword = sha1($password);

$EmployerDetail = "SELECT * FROM JobSeeker WHERE username ='$username'";

$result = mysql_query($EmployerDetail);


// Check connection if it the database in the igor Sever is accessable 
if (!$connection) {
	$userLogin["Access"] = "0";
    $userLogin["Message"] = "Database Fault! please try again";
    die(json_encode($userLogin));
}




if(mysql_num_rows($result))
{

while($row=mysql_fetch_assoc($result)){

$EmployerLogin["id"]=$row["id"];
$EmployerLogin["username"]=$row["username"];
$EmployerLogin["telephone"]=$row["telephone"];
$EmployerLogin["mobile"]=$row["mobile"];
$EmployerLogin["email"]=$row["email"];
$EmployerLogin["firstName"]=$row["firstName"];
$EmployerLogin["lastName"]=$row["lastName"];
$EmployerLogin["postCode"]=$row["postCode"];
}
echo "{ JobSeekerDetail: [",  json_encode($EmployerLogin), " ]}";


} // end if statement 





mysql_close($connection);
?>







