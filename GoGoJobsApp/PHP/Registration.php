<?php

require_once('ConnectionConfiguration.php');


// fucntion to send registration details to the new 
// Employer via email  
function SendRegisterEmailToEmployer() { 
// email address 
$to      = $_POST['Email'];
// send subject of the email 
$subject = 'Thank you for Registering with GoGo Jobs';
// send email of the message 
// template for the message body to be send with the registartion details 
$message = 'Dear '.$_POST['FirstName']." ".$_POST['LastName']."\r\n" 
.'Thank you for Registering with GoGo Jobs'
."\r\n".'Here is your copy of your registration details: '
."\r\n".'Username: '
.$_POST['Username']."\r\n".'Password: '.$_POST['Password']
."\r\n".'FirstName: '
.$_POST['FirstName']."\r\n".'LastName: '
.$_POST['LastName']."\r\n"."Telephone: ".$_POST['Telephone']."\r\n"
."Mobile: " .
$_POST['Mobile']."\r\n"."Email: ".$_POST['Email']."\r\n".
"PostCode: ".$_POST['PostCode']
."\r\n".'FirstLineAddress: '.$_POST['FirstLineAddress']
."\r\n".'LastLineAddress: '.$_POST['LastLineAddress']."\r\n".
'CompanyName : '.$_POST['CompanyName']."\r\n".'Town : '
.$_POST['Town'];

// email headers 
$headers = 'From: GoGoJobs@igor.ac.uk'. "\r\n" .
'Reply-To: GoGoJobs@igor.ac.uk'. "\r\n";

// function to execute the mail to the sender 
mail($to, $subject, $message, $headers);
}

// fucntion to send registration details to the new 
// Job seeker via email  
function SendRegisterEmailToJobSeeker() {
   
// email address 
$to      = $_POST['Email'];
// send subject of the email 
$subject = 'Thank you for Registering with GoGo Jobs';
// send email of the message 
// template for the message body to be send with the registartion details 
$message = 'Dear '.$_POST['FirstName']." ".$_POST['LastName']."\r\n" .'Thank you for Registering with GoGo Jobs'
    ."\r\n".'Here is your copy of your registration details: '."\r\n".'Username: '
    .$_POST['Username']."\r\n".'Password: '.$_POST['Password']."\r\n".'FirstName: '.$_POST['FirstName']."\r\n".'LastName: '
    .$_POST['LastName']."\r\n"."Telephone: ".$_POST['Telephone']."\r\n"."Mobile: " .
    $_POST['Mobile']."\r\n"."Email: ".$_POST['Email']."\r\n"."PostCode: ".$_POST['PostCode']; 
// email headers 
$headers = 'From: GoGoJobs@igor.ac.uk'."\r\n" .
    'Reply-To: GoGoJobs@igor.ac.uk';
// function to execute the mail to the sender 
mail($to, $subject, $message, $headers);


}



    
// check if the form has been submited with the important details 
if(isset($_POST)){



//store variables values from the post method send by andorid application 
$username = $_POST['Username'];
$password = $_POST['Password'];
$firstName = $_POST['FirstName'];
$lastName = $_POST['LastName'];
$telephone = $_POST['Telephone'];
$mobile = $_POST['Mobile'];
$email = $_POST['Email'];
$account = $_POST['Type'];
$postCode = $_POST['PostCode'];
$firstlineAdress = $_POST['FirstLineAddress'];
$secondlineAdress = $_POST['LastLineAddress'];
$companyName = $_POST['CompanyName'];
$town = $_POST['Town'];



// http://php.net/manual/en/function.password-hash.php


// ensure the password is hashed for security reasons 
$hashPassword = sha1($password);


//validate if the Username is unique and available 
//for the new user to register 
// SQL queries to see if the username exist in 
// the Job seeker or Employer's table 
// on the backend database 
$verifyJobSeekerQuery = "SELECT username FROM JobSeeker 
      WHERE username ='$username'";

$verifyEmployerQuery = "SELECT username FROM Employer 
      WHERE username ='$username'";

// Execute the mySQL query 
$mysqliQueryJobSeeker = mysql_query($verifyJobSeekerQuery);
$mysqliQueryEmployer = mysql_query($verifyEmployerQuery);

// if the account variable is job seeker 
// and The Username does not exist in Job seeker's 
// table and employers table 
if($account=="JobSeeker" &&  mysql_num_rows($mysqliQueryJobSeeker)<1 
    && mysql_num_rows($mysqliQueryEmployer)<1 )
{
// then insert the new details in the backend database Job seeker  table 
$newJobSeeker = "INSERT INTO JobSeeker(username, password, firstName,
lastName,telephone,mobile,email,postCode) 
VALUES('$username', '$hashPassword', '$firstName','$lastName',
'$telephone','$mobile','$email','$postCode')";
// Execute the mySQL query 
 mysql_query($newJobSeeker);
 // send email to the Job Seeker 
SendRegisterEmailToJobSeeker(); 
// output the sucess message in JSON format 
$JobSeekerUpdate["Access"] = "1";
$JobSeekerUpdate["Message"] = 
"Sucessfully Registered";
echo "{ RegJb: [",  json_encode($JobSeekerUpdate), " ]}";
}
// if the account variable is Employer 
// and The Username does not exist in Job seeker's table and employers table
if($account=="Employer" &&  mysql_num_rows($mysqliQueryJobSeeker)<1 && mysql_num_rows($mysqliQueryEmployer)<1)
{
// then insert the new details in the backend database Employer table 
$newEmployer = "INSERT INTO Employer(username, password, companyName,firstName,secondName,telephone,mobile,
email,firstLineAddress,lastLineAddress,town,postCode) 
VALUES('$username', '$hashPassword','$companyName','$firstName','$lastName','$telephone','$mobile','$email',
'$firstlineAdress','$secondlineAdress','$town','$postCode')";
// Execute the mySQL query 
 mysql_query($newEmployer);
  // send email to the Employer 
SendRegisterEmailToEmployer();
// output the sucess message in JSON format 
$EmployerUpdate["Access"] = "1";
$EmployerUpdate["Message"] = "Sucessfully Registered";
echo "{ RegJb: [",  json_encode($EmployerUpdate), " ]}";
}
// if the Username does exist or not availble 
// let the user know 
// output the warning message in JSON format 
else
{
$userUpdate["Access"] = "0";
$userUpdate["Message"] = "Username is already in use, please again!";
echo "{ RegJb: [",  json_encode($userUpdate), " ]}";
}


} // end Isset on Username 





?>







