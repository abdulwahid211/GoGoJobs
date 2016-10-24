package jobapp.mdabdulwahid.jobapp.Register;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import jobapp.mdabdulwahid.jobapp.DataTransfer.HttpTransferData;
import jobapp.mdabdulwahid.jobapp.DataTransfer.TransferPackage;
import jobapp.mdabdulwahid.jobapp.JSONparse.RegisterMessageJSONParser;
import jobapp.mdabdulwahid.jobapp.LoginDetails.Login;
import jobapp.mdabdulwahid.jobapp.R;

/**
 *
 * Register_JobSeeker is sub class of Register that helps to process the JobSeeker registration details
 */
public class Register_Job_seeker extends Register {

    //onCreate method to initialise all object and display the content
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_job_seeker);
        actionBar = getSupportActionBar();
        actionBar.setTitle("     Registration For Job Seeker");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .rgb(255, 204, 127)));
        usernameInput = (EditText) findViewById(R.id.jbtitle);
        actionBar.setDisplayHomeAsUpEnabled(true); //
        passwordInput = (EditText) findViewById(R.id.type);
        firstName = (EditText) findViewById(R.id.firstname);
        lastName = (EditText) findViewById(R.id.lastname);
        telephone = (EditText) findViewById(R.id.telephone);
        mobile = (EditText) findViewById(R.id.mobile);
        email = (EditText) findViewById(R.id.email);
        postCode = (EditText) findViewById(R.id.postcode);
        transferPackage = new TransferPackage();
        registerMessageJSONParser = new RegisterMessageJSONParser();

        try {
            // set up the webservice request object
            //  allows to post values and receive response
            httpTransferData = new HttpTransferData(url, transferPackage);
        } catch (Exception e) {

        }

    } // end onCreate method


    public void SubmitButton(View view) {
        SubmitData();


    }

    public void SubmitData() {

        // validation check to ensure the user has filled all required forms
        if (usernameInput.getText().toString().length() > 0 && passwordInput.getText().toString().length() > 0 &&
                firstName.getText().toString().length() > 0 && lastName.getText().toString().length() > 0
                && telephone.getText().toString().length() > 0 && mobile.getText().toString().length() > 0 &&
                email.getText().toString().length() > 0 && postCode.getText().toString().length() > 0) {


            transferPackage.addNewPost("Username", usernameInput.getText().toString());
            transferPackage.addNewPost("Password", passwordInput.getText().toString());
            transferPackage.addNewPost("FirstName", firstName.getText().toString());
            transferPackage.addNewPost("LastName", lastName.getText().toString());
            transferPackage.addNewPost("Telephone", telephone.getText().toString());
            transferPackage.addNewPost("Mobile", mobile.getText().toString());
            transferPackage.addNewPost("Email", email.getText().toString());
            transferPackage.addNewPost("PostCode", postCode.getText().toString());
            transferPackage.addNewPost("Type", "JobSeeker");

            // execute the Async task in background to send the details of the job seeker
            AttemptRegister attemptRegister = new AttemptRegister();
            attemptRegister.execute(); //execute background thread
        } else {
            Toast.makeText(getApplicationContext(), "Please fill in all required forms", Toast.LENGTH_SHORT).show();
        }




    }
    //registration message update
    public void updateMessage() {

        String message = registerMessageList.getMessage();
        int access = registerMessageList.getAccess();
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

        //if the registration is a success for job seeker
        if (access == 1) {
            //return back to the login page
            Intent i = new Intent(Register_Job_seeker.this, Login.class);
            startActivity(i);
        }


    }


    @Override // method for the action bar return back to the previous activity
    public boolean onOptionsItemSelected(MenuItem item) {
// TODO Auto-generated method stub
        onBackPressed();
        return true;
    }


}
