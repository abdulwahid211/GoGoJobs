<?php

//Variables for the connection to the database.
$hostName = "localhost";
$username = "ma301ma";
$password = "wahid92";
$dbName = "ma301ma_final_project2016";

//Connecting to the host and database.
$connection = mysql_connect($hostName, $username, $password, $dbName);



//Checking if the connection has worked.a
if(!mysql_select_db($dbName))
{
	//Printing out the error.
	die("failed to connect to the database, please try again!");


}




$jb_id = $_GET["jb_id"];
$vacancy_id = $_GET["vacancy_id"];
$doc_type = $_GET['fileType'];

// if the document type is CV 
if($doc_type == "CV"){

// select that particular CV file 
$query = "SELECT name, type, size, content " .
         "FROM CV WHERE job_seeker_id = '$jb_id' AND vacancy_id = '$vacancy_id'";

// execute the MYSQL query to retrieve all content 
$result = mysql_query($query) or die('Error, query failed');

// if it does exist from the database 
if(mysql_num_rows($result)>0)

{

// assign all variables in one operation 
list($name, $type, $size, $content) =  mysql_fetch_array($result);

// output the whole content of the CV file 
// through the headers  
header("Content-length: $size");
header("Content-type: $type");
header("Content-Disposition:$name");
echo $content;
} // end inn if 

} // end CV if statment 

// if the document type is Cover letter 
if($doc_type=="Cover_Letter"){

// select that particular Cover letter file 
$query = "SELECT name, type, size, content " .
         "FROM CoverLetters WHERE job_seeker_id = '$jb_id' AND vacancy_id = '$vacancy_id'";
// execute the MYSQL query to retrieve all content
$result = mysql_query($query) or die('Error, query failed');
// if it does exist from the database 
if(mysql_num_rows($result)>0)

{

// assign all variables in one operation 
list($name, $type, $size, $content) =  mysql_fetch_array($result);
// output the whole content of the Cover letter file 
// through the headers  
header("Content-length: $size");
header("Content-type: $type");
header("Content-Disposition:$name");
echo $content;
} // end inn if 

} // end cover letter if statement 





?>
