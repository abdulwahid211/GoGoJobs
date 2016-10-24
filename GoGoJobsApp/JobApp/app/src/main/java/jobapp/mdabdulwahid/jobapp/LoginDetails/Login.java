
package jobapp.mdabdulwahid.jobapp.LoginDetails;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import jobapp.mdabdulwahid.jobapp.DataTransfer.HttpTransferData;
import jobapp.mdabdulwahid.jobapp.DataTransfer.TransferPackage;
import jobapp.mdabdulwahid.jobapp.EmployerMenu.EmployerMenu;
import jobapp.mdabdulwahid.jobapp.JSONparse.LoginMessageJSONParser;
import jobapp.mdabdulwahid.jobapp.JobSeekerMenu.Job_seekerMenu;
import jobapp.mdabdulwahid.jobapp.Objects.LoginMessage;
import jobapp.mdabdulwahid.jobapp.R;


// The main class that displays the login page
public class Login extends Activity {

    // url link to send request the web service script php
    private final String url = "http://doc.gold.ac.uk/~ma301ma/finalProject/Login.php";
    private EditText username_input; //username
    private EditText password_input; // password
    private Button login; // button to login
    private ImageView title; // image for the title background

    // values that needs to be posted to the php script
    private TransferPackage transferPackage;
    // an custom object that converts String of JSON data into values
    private LoginMessageJSONParser loginMessageJSONParser;
    // return
    private LoginMessage loginMessage;
    private HttpTransferData httpTransferData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // content view  for the login page
        setContentView(R.layout.login);

        // initialise all android widgets and objects
        username_input = (EditText) findViewById(R.id.username);
        password_input = (EditText) findViewById(R.id.password);
        title = (ImageView) findViewById(R.id.fonttitle);

        login = (Button) findViewById(R.id.login);
        transferPackage = new TransferPackage();
        loginMessageJSONParser = new LoginMessageJSONParser();
        loginMessage = new LoginMessage();
        //setting up the X and Y axis for android widgets
        title.setY(140);
        title.setX(50);
        username_input.setY(745);
        username_input.setX(45);
        password_input.setY(940);
        password_input.setX(45);
        login.setY(1100);
        login.setX(250);


        try {
            httpTransferData = new HttpTransferData(url, transferPackage);
        } catch (Exception e) {

        }
    }


    // the main login button method
    public void LoginButton(View view) {
        // when the user presses the login button,
        // then do the following
        // submit inputs of Username and
        // password to the
        // web service script Login.php
        // send post request of the Key and value to
        // the server side script
        transferPackage.addNewPost("Username",
                username_input.getText().toString());
        transferPackage.addNewPost("Password",
                password_input.getText().toString());
      // instantiate new attempt Login for the user's request
        AttemptLogin attemptLogin = new AttemptLogin();
        attemptLogin.execute(); // execute the request in the background
    }



    // the method is updated in the Asynctask on post execution functions
    // it returns the updated LoginMessage object
    // retrieves access the value, the message  and account type to intently direct the user
    // relevant user interface
    // the content of LoginMessage Object is retrieved from the Login.php
    public void verify() {

        // determines the success if the login are correct
        int access = loginMessage.getAccess();
        // message to alert the user
        String message = loginMessage.getMessage();
        // if the login is success, state what account type the user belongs
        String accountType = loginMessage.getAccount_type();

      // if the login access equals 1, its correct
        if (access == 1) {
            // update the message content on the users screen via Toast function
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

            // if its Job seeker
            if (accountType.equals("JobSeeker")) {
                // intent the job seeker to relevant class activity passing with their username
                Intent i = new Intent(Login.this, Job_seekerMenu.class);
                i.putExtra("username", username_input.getText().toString());
                startActivity(i); // process the activity
            }
            // if its Employer
            if (accountType.equals("Employer")) {
                // intent the Employer to relevant class activity passing with their username
                Intent i = new Intent(Login.this, EmployerMenu.class);
                i.putExtra("username", username_input.getText().toString());
                startActivity(i);// process the activity
            }


        }
        // if the login access is not 1, display the incorrect message to the user
        else {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    // Registration button
    public void RegisterButton(View view) {
        Intent i = new Intent(Login.this, chooseAccount.class);
        startActivity(i);
    }


    // inner class AsyncTask object to execute the operation in the background thread
    private class AttemptLogin extends AsyncTask<String, String, String> {

        //override method to set up values before background execution
        @Override
        protected void onPreExecute() {

        }
        // doInBackground to execute the actual operation in the background
        @Override
        protected String doInBackground(String... param) {


            // executes request to the php script
            String newContent = httpTransferData.DataTransfer();
            return newContent; // return the new response from the php web service script
        }

        @Override
        protected void onPostExecute(String result) {
            // receive the values from php response
            // the php response would contain JSON data
            // JSON data contains all login message sucess detail
            loginMessage = (LoginMessage) loginMessageJSONParser.parseFeed(result, "LoginAcess");
            verify(); //verify details if correct


        }

    }


}