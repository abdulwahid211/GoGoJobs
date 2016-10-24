<?php
//collect all relevant interview details 
$InterviewTime = $_POST['InterviewTime'];
$InterviewDate = $_POST['InterviewDate'];
$duration =$_POST['duration'];
$firstLineAddress =$_POST['firstLineAddress'];
$secondLineAddress =$_POST['secondLineAddress'];
$town =$_POST['town'];
$postCode =$_POST['postCode'];
$telephone = $_POST['telephone'];
$Job_seeker_email = $_POST['Job_seeker_email'];
$Employer_email = $_POST['Employer_email'];
$JobTitle = $_POST['JobTitle'];
$Job_seeker_name = $_POST['Job_seeker_name'];
$Employer_name = $_POST['Employer_name'];


// send email of the message 
// template for the message body to be send with the interview details 
$to      = $Job_seeker_email;
$subject = 'GoGo Jobs Interview Details For '.$JobTitle;
$message = 'Dear '.$Job_seeker_name. "\r\n" .'GoGo Jobs is pleased to confirm you have been invited for an interview with '.$Employer_name 
    ."\r\n".'Here is your interview details: '."\r\n".'Interview for Job: '.$JobTitle."\r\n".'Date: '.$InterviewDate."\r\n".'Time: '.$InterviewTime."\r\n".'Employer Name: '
    .$Employer_name."\r\n".$firstLineAddress."\r\n".
    $secondLineAddress."\r\n".$town."\r\n".$postCode."\r\n".'Phone: '.$telephone."\r\n".'Email: '.$Employer_email;

// email headers 
$headers = 'From: GoGoJobs' . "\r\n" .
    'Reply-To: GoGoJobs';
// function to execute the mail to the sender 
mail($to, $subject, $message, $headers);



?>