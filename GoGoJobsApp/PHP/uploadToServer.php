<?php
//Variables for the connection to the database.
$hostName = "localhost";
$username = "ma301ma";
$password = "wahid92";
$dbName = "ma301ma_final_project2016";

//Connecting to the host and database.
$connection = mysql_connect($hostName, $username, $password, $dbName);

$jobID = $_GET["jobID"];
$vacID = $_GET["vacID"];

//Checking if the connection has worked.
if(!mysql_select_db($dbName))
{
//Printing out the error.
die("failed to connect to the database, please try again!");

}

// double check if the temporary  CV filename of the file 
// which was uploaded and does exist.
if (is_uploaded_file($_FILES['cv']['tmp_name'])) {
// if so 
// original name of the file on the sent from the android phone.
$fileName = $_FILES['cv']['name'];
//  temporary filename of the file 
$tmpName  = $_FILES['cv']['tmp_name'];
//the size of the file 
$fileSize = $_FILES['cv']['size'];
// the type of the file 
$fileType = $_FILES['cv']['type'];
// the fopen function opens the file url to be uploaded 
$fp      = fopen($tmpName, 'r');
// reads the open file 
// the first parameter read the document from the open file 
// the second paraeter reads the maximum number of bytes 
$content = fread($fp, filesize($tmpName));
// add lashes to the variable 
// ensures its safe to be inserted in the database 
// specifies the string to be escaped
$content = addslashes($content);
fclose($fp); // close the open file fucntion 
// insert all the contents of file cv to the backend database 
$query = "INSERT INTO CV (name, size, type, content,
job_seeker_id, vacancy_id  ) ".
"VALUES ('$fileName', '$fileSize', '$fileType',
'$content', '$jobID', '$vacID')";
// execute the mySQL query to be inserted 
mysql_query($query) or die('Error, query failed'); 
}
// double check if the temporary  Cover letter filename of the file 
// which was uploaded and does exist.
if (is_uploaded_file($_FILES['coverletter']['tmp_name'])) {

// original name of the file on the sent from the android phone.
$fileName = $_FILES['coverletter']['name'];
//  temporary filename of the file 
$tmpName  = $_FILES['coverletter']['tmp_name'];
//the size of the file 
$fileSize = $_FILES['coverletter']['size'];
// the type of the file 
$fileType = $_FILES['coverletter']['type'];


// collect the characterics of the file 
// the fopen function opens the file url to be uploaded 
$fp      = fopen($tmpName, 'r');
// reads the open file 
// the first parameter read the document from the open file 
// the second paraeter reads the maximum number of bytes 
$content = fread($fp, filesize($tmpName));
// add lashes to the variable 
$content = addslashes($content);
fclose($fp); // close the open file fucntion 

// insert all the contents of file cover letter to the backend database 
$query = "INSERT INTO CoverLetters (name, size, type, content, job_seeker_id, vacancy_id  ) ".
"VALUES ('$fileName', '$fileSize', '$fileType', '$content', '$jobID', '$vacID')";
// execute the mySQL query to be inserted 
mysql_query($query) or die('Error, query failed'); 



}


?>