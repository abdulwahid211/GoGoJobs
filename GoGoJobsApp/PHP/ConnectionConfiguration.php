<?php
//configuration connection for the MySQL database 
//Variables for the connection to the database.
$hostName = "localhost";
$username = "ma301ma";
$password = "wahid92";
$dbName = "ma301ma_final_project2016";

//Connecting to the host and Server database.
$connection = mysql_connect($hostName, $username, $password, $dbName);

//Checking if the connection has worked.
if(!mysql_select_db($dbName))
{
	//Printing out the error.
	die("failed to connect to the database, please try again!");
}


